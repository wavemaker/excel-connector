## Connector  Introduction

Connector is a Java based backend extension for WaveMaker applications. Connectors are built as Java modules & exposes java based SDK to interact with the connector implementation.
Each connector is built for a specific purpose and can be integrated with one of the external services. Connectors are imported & used in the WaveMaker application. Each connector runs on its own container thereby providing the ability to have it’s own version of the third party dependencies.

## Features of Connectors

1. Connector is a java based extension which can be integrated with external services and reused in many Wavemaker applications.
1. Each connector can work as an SDK for an external system.
1. Connectors can be imported once in a WaveMaker application and used many times in the applications by creating multiple instances.
1. Connectors are executed in its own container in the WaveMaker application, as a result there are no dependency version conflict issues between connectors.

## About Excel Connector

## Excel Connector Introduction
Excel connector provides apis to parse the Excel files with tabular structures & returns the table data as java objects. 
The return response can be directly mapped to entity classes that enable it to easily persist the entities into database tables directly.

## Build
You can build this connector using following command
```
mvn clean install
```

## Deploy
You can import connector dist/excel-connector.zip artifact in WaveMaker Application using file upload option.

## Using Excel Connector in WaveMaker Application

This connector will be exposing the following four api's 
##### readExcelAsMap(InputStream inputStream, boolean convertHeadersToFieldNames)
- This method will convert file inputStream to ``List<Map<String, Object>>`` 
- convertHeaderToFieldNames if true it will convert headers in the Excel sheet to FieldNames 
    ``eg: Email address -> emailAddress``
```
example:
      excelInput:
      sno  name    EmailAddress
      1    a       a@mail.com
      2    b       b@mail.com
     
      output:
        if convertHeadersToFieldNames is true
            [{emailAddress=a@gmail.com, sno=1.0, name=a}, {emailAddress=b@gmail.com, sno=2.0, name=b}] 
        if convertHeadersToFileNames is false
            [{Sno=1.0, Email address=a@gmail.com, Name=a}, {Sno=2.0, Email address=b@gmail.com, Name=b}] 
        we can observe that when convertHeadersToFieldNames is true we will be convering headers to fieldNames eg:Email address -> emailAddress
     
```
```
Invocation snippet:

List<Map<String, Object>> result = excelConnector.readExcelAsMap(getClass().getClassLoader().getResourceAsStream("sample.xlsx"),
                false);
```

##### readExcelAsMap(File file, boolean convertHeadersToFieldNames)
- This method  is same as the previous method except that it takes ```Java.io.File``` as an input instead of ```Java.io.InputStream```
```
Invocation snippet:

List<Map<String, Object>> employeeList = excelConnector.readExcelAsMap(new File(getClass().getClassLoader().getResource("sample.xlsx").getPath()),
                true);
```
##### readExcelAsObject(InputStream inputStream, Class<T> cls)
- This method will convert file inputStream to ``List<clsObjects>`` 
- cls to target object to which entities to be casted
```
example:
      excelInput:
      sno  name    EmailAddress
      1    a       a@mail.com
      2    b       b@mail.com
      
      cls Employee
      output: [Employee1,Employee2]
          
```
```
Invocation snippet:

List<Employee> employeeList = excelConnector.readExcelAsObject(getClass().getClassLoader().getResourceAsStream("sample.xlsx"),
                Employee.class);
```
##### readExcelAsObject(File file, Class<T> cls)
- This method  is same as the previous method except that it takes ```Java.io.File``` as an input instead of ```Java.io.InputStream```
```
Invocation snippet:

List<Employee> employeeList = excelConnector.readExcelAsObject(new File(getClass().getClassLoader().getResource("sample.xlsx").getPath()),
                Employee.class);
```