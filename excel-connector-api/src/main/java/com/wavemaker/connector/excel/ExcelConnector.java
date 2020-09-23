package com.wavemaker.connector.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.wavemaker.runtime.connector.annotation.WMConnector;


@WMConnector(name = "excel-connector",
        description = "A simple connector excel-connector that can be used in WaveMaker application")
public interface ExcelConnector {

    /**
     * This method will convert excel file inputStream to List of Map <String, Object> objects
     * example:
     * excelInput:
     * sno  name    EmailAddress
     * 1    a       a@mail.com
     * 2    b       b@mail.com
     *
     * output:
     * [{emailAddress=a@gmail.com, sno=1.0, name=a}, {emailAddress=b@gmail.com, sno=2.0, name=b}] if convertHeadersToFieldNames is true
     * [{Sno=1.0, Email address=a@gmail.com, Name=a}, {Sno=2.0, Email address=b@gmail.com, Name=b}] if convertHeadersToFileNames is false
     *
     * @param inputStream                excel file inputs stream
     * @param convertHeadersToFieldNames if true it will convert headers in the excel sheet to FieldNames eg: Email address -> emailAddress
     *
     * @return List<Map < String, Object>>
     *
     * @throws IOException if input stream cannot be opened, read, or closed
     */
    List<Map<String, Object>> readExcelAsMap(InputStream inputStream, boolean convertHeadersToFieldNames) throws IOException;

    /**
     * this method will convert excel file to List of Map <String, Object> objects
     *
     * @param file                       java.io.File Object
     * @param convertHeadersToFieldNames if true it will convert headers in the excel sheet to FieldNames eg: Email address -> emailAddress
     *
     * @return List<Map < String, Object>>
     *
     * @throws IOException if input file cannot be opened, read, or closed
     */
    List<Map<String, Object>> readExcelAsMap(File file, boolean convertHeadersToFieldNames) throws IOException;

    /**
     * this method will convert excel inputStream to List<Class Objects>
     * example:
     * Input:
     * sno  name    EmailAddress
     * 1    a       a@mail.com
     * 2    b       b@mail.com
     * cls Employee
     * output: [Employee1,Employee2]
     *
     * @param inputStream excel file inputStream
     * @param cls         to which entities has to be casted
     * @param <T>
     *
     * @return [classObject1, classObject2]
     *
     * @throws IOException if input stream cannot be opened, read, or closed
     */
    <T> List<T> readExcelAsObject(InputStream inputStream, Class<T> cls) throws IOException;

    /**
     * this method will convert excel file to List<Class Objects>
     *
     * @param file excel file
     * @param cls  target Class to which entities has to be casted
     * @param <T>
     *
     * @return [classObject1, classObject2]
     *
     * @throws IOException if input file cannot be opened, read, or closed
     */
    <T> List<T> readExcelAsObject(File file, Class<T> cls) throws IOException;

}