package com.misha.application.controller;

import com.misha.application.service.FileService;
import com.misha.application.service.ProductService;
import com.misha.application.utils.json.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import static com.misha.application.utils.json.JsonUtils.buildJsonResponse;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by misha on 17.11.16.
 */
@RestController
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/product-table", method = RequestMethod.GET)
    @ResponseStatus(OK)
    public JsonResponse getAll(@RequestParam int page) {
        int checkedPage = page < 0 ? 0 : page;
        LOG.info("Requesting for products. Page {}", page);
        return buildJsonResponse(productService.getAll(checkedPage));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(OK)
    public JsonResponse upload(MultipartFile file) {
        LOG.info("Uploading file {}, size: {}", file.getName(), file.getSize());
        fileService.parseFile(file);
        return buildJsonResponse("File uploaded");
    }
}
