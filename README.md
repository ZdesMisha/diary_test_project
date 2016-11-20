Excel manager application


How to run it:


1. Install database and tables using src/main/postgresql/db_create.sql.
2. Go to project main directory (/diary_test_project)
3. mnv clean install
4. mvn spring-boot:run (from unix console) or use spring-boot plugin with "run" command.
5. check application working at localhost:8080/home
6. try to upload files /excel_files/test.xlsx and test2.xls
7. In case of any errors see error stack in /log/diary.log log files.