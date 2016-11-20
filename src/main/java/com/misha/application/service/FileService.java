package com.misha.application.service;

import com.misha.application.entity.Product;
import com.misha.application.exception.FileValidationException;
import com.misha.application.exception.ProductValidationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Created by misha on 18.11.16.
 */
@Service
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    @Value("${file.max.size}")
    private int fileMaxSize;

    @Autowired
    private ProductService productService;

    @Autowired
    private Validator validator;

    private static final String XLSX_FORMAT = "xlsx";
    private static final String XLS_FORMAT = "xls";

    private static final int TABLE_COLUMNS = 4;
    private static final int CODE_COLUMN = 0;
    private static final int NAME_COLUMN = 1;
    private static final int PRICE_COLUMN = 2;
    private static final int DATE_COLUMN = 3;

    private static final int FIRST_SHEET = 0;


    public void parseFile(MultipartFile file) {
        validateFile(file);
        List<Product> list = new ArrayList<>();
        try {
            Workbook workbook;
            String fileName = file.getOriginalFilename();
            if (fileName.endsWith(XLS_FORMAT)) {
                workbook = WorkbookFactory.create(file.getInputStream());
            } else if (fileName.endsWith(XLSX_FORMAT)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new FileValidationException("Wrong file format");
            }
            Sheet sheet = workbook.getSheetAt(FIRST_SHEET);
            for (Row row : sheet) {
                Product product = parseRow(row);
                list.add(product);
            }
            productService.saveAll(list);
        } catch (InvalidFormatException ex) {
            throw new FileValidationException("Cannot process this file type");
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private Product parseRow(Row row) {
        Product product = new Product();
        for (int i = 0; i < TABLE_COLUMNS - 1; i++) {
            product.setCode((int) row.getCell(CODE_COLUMN).getNumericCellValue());
            product.setName(row.getCell(NAME_COLUMN).getStringCellValue());
            product.setPrice(row.getCell(PRICE_COLUMN).getNumericCellValue());
            product.setP_date(row.getCell(DATE_COLUMN).getDateCellValue());
        }
        validateProduct(product);
        return product;
    }

    private void validateFile(MultipartFile file) {
        if (file.getSize() > fileMaxSize) {
            throw new FileValidationException("Max file size (50Mb) is exceeded.");
        }
    }

    private void validateProduct(Product product) {
        validator.validate(product);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Product> violation : violations) {
                errors.add(violation.getMessage());
            }
            throw new ProductValidationException(errors);
        }
    }

}
