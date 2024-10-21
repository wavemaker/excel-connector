package com.wavemaker.connector.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavemaker.connector.excel.utils.NamingUtils;

@Service
@Primary
public class ExcelConnectorImpl implements ExcelConnector {

    @Override
    public List<Map<String, Object>> readExcelAsMap(InputStream inputStream, boolean convertHeadersToFieldNames) throws IOException {
        List<Map<String, Object>> resultantList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        for (int i = 0;i < workbook.getNumberOfSheets();i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            Iterator<Row> rowIterator = sheet.rowIterator();
            List<String> headerParams = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Iterator<Cell> cellIterator = rowIterator.next().cellIterator();
                if (headerParams.isEmpty()) {
                    resolveHeaderRow(cellIterator, headerParams, convertHeadersToFieldNames);
                } else {
                    resolveDataRow(cellIterator, headerParams, resultantList);
                }
            }
        }
        return resultantList;
    }

    @Override
    public List<Map<String, Object>> readExcelAsMap(File file, boolean convertHeadersToFieldNames) throws IOException {
        return readExcelAsMap(new FileInputStream(file), convertHeadersToFieldNames);
    }

    @Override
    public <T> List<T> readExcelAsObject(InputStream inputStream, Class<T> t) throws IOException {
        List<Map<String, Object>> listOfEntries = readExcelAsMap(inputStream, true);
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType type = objectMapper.getTypeFactory().
                constructCollectionType(List.class, t);
        return objectMapper.convertValue(listOfEntries, type);

    }

    @Override
    public <T> List<T> readExcelAsObject(File file, Class<T> t) throws IOException {
        return readExcelAsObject(new FileInputStream(file), t);

    }

    private void resolveDataRow(Iterator<Cell> cellIterator, List<String> headerParams, List<Map<String, Object>> resultantList) {
        int index = 0;
        Map<String, Object> entry = new HashMap<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            while (cell.getColumnIndex() != index) {
                entry.put(headerParams.get(index), null);
                index++;
            }
            switch (cell.getCellType()) {
                case NUMERIC:
                    entry.put(headerParams.get(index), resolveNumericType(cell));
                    break;
                case STRING:
                    entry.put(headerParams.get(index), cell.getStringCellValue());
                    break;
                case BOOLEAN:
                    entry.put(headerParams.get(index), cell.getBooleanCellValue());
                    break;
                default:
                    entry.put(headerParams.get(index), "");
                    break;
            }
            index++;
        }
        resultantList.add(entry);
    }

    private void resolveHeaderRow(Iterator<Cell> cellIterator, List<String> headerParams, boolean convertHeadersToFieldNames) {
        while (cellIterator.hasNext()) {
            headerParams.add(convertHeadersToFieldNames ? NamingUtils.toJavaIdentifier(cellIterator.next().getStringCellValue()) :
                    cellIterator.next().getStringCellValue());
        }
    }

    private Object resolveNumericType(Cell cell) {
        return DateUtil.isCellDateFormatted(cell) ? cell.getDateCellValue() : cell.getNumericCellValue();
    }
}