package com.wavemaker.connector.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wavemaker.connector.excel.models.Employee;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExcelConnectorTestConfiguration.class)
public class ExcelConnectorTest {

    @Autowired
    private ExcelConnector connectorInstance;

    @Test
    public void readExcelAsMap() throws IOException {

        List<Map<String, Object>> result = connectorInstance.readExcelAsMap(getClass().getClassLoader().getResourceAsStream("sample.xlsx"),
                false);
        Assert.assertEquals(4, result.size());
        Assert.assertEquals("[{Sno=1.0, Email address=a@gmail.com, Name=a}, {Sno=2.0, Email address=b@gmail.com, Name=b}, {Sno=3.0, Email address=c@mail.com, Name=c}, {Sno=4.0, Email address=d@mail.com, Name=d}]", result.toString());
    }

    @Test
    public void readExcelAsMap1() throws IOException {
        List<Map<String, Object>> employeeList = connectorInstance.readExcelAsMap(new File(getClass().getClassLoader().getResource("sample.xlsx").getPath()),
                true);
        Assert.assertEquals(4, employeeList.size());
        Assert.assertEquals("c@mail.com", employeeList.get(2).get("emailAddress"));
        Assert.assertEquals("[{emailAddress=a@gmail.com, sno=1.0, name=a}, {emailAddress=b@gmail.com, sno=2.0, name=b}, {emailAddress=c@mail.com, sno=3.0, name=c}, {emailAddress=d@mail.com, sno=4.0, name=d}]", employeeList.toString());
    }

    @Test
    public void readExcelAsClass() throws IOException {
        List<Employee> employeeList = connectorInstance.readExcelAsObject(getClass().getClassLoader().getResourceAsStream("sample.xlsx"),
                Employee.class);
        employeeList.forEach(employee -> {

        });
        Assert.assertEquals(4, employeeList.size());
        Assert.assertEquals("c@mail.com", employeeList.get(2).getEmailAddress());
        Assert.assertEquals(4, employeeList.get(3).getSno());
    }

    @Test
    public void readExcelAsClass1() throws IOException {
        List<Employee> employeeList = connectorInstance.readExcelAsObject(new File(getClass().getClassLoader().getResource("sample.xlsx").getPath()),
                Employee.class);
        Assert.assertEquals(4, employeeList.size());
        Assert.assertEquals("b@gmail.com", employeeList.get(1).getEmailAddress());
        Assert.assertEquals(3, employeeList.get(2).getSno());
    }
}