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
        Assert.assertEquals("[{Sno=1.0, Email address=a@gmail.com, Date=Wed Mar 27 00:00:00 IST 2019, Name=a}, {Sno=2.0, Email address=b@gmail.com, Date= , Name=b}, {Sno=3.0, Email address=c@mail.com, Date=1994-11-05T08:15:30-05:00, Name=c}, {Sno=4.0, Email address=d@mail.com, Date=1994-11-05T13:15:30Z, Name=d}]", result.toString());
    }

    @Test
    public void readExcelAsMap1() throws IOException {
        List<Map<String, Object>> employeeList = connectorInstance.readExcelAsMap(new File(getClass().getClassLoader().getResource("sample.xlsx").getPath()),
                true);
        Assert.assertEquals(4, employeeList.size());
        Assert.assertEquals("c@mail.com", employeeList.get(2).get("emailAddress"));
        Assert.assertEquals("[{date=Wed Mar 27 00:00:00 IST 2019, emailAddress=a@gmail.com, sno=1.0, name=a}, {date= , emailAddress=b@gmail.com, sno=2.0, name=b}, {date=1994-11-05T08:15:30-05:00, emailAddress=c@mail.com, sno=3.0, name=c}, {date=1994-11-05T13:15:30Z, emailAddress=d@mail.com, sno=4.0, name=d}]", employeeList.toString());
    }

    @Test
    public void readExcelAsClass() throws IOException {
        List<Employee> employeeList = connectorInstance.readExcelAsObject(getClass().getClassLoader().getResourceAsStream("sample.xlsx"),
                Employee.class);
        Assert.assertEquals(4, employeeList.size());
        Assert.assertEquals("c@mail.com", employeeList.get(2).getEmailAddress());
        Assert.assertEquals(4, employeeList.get(3).getSno());
        Assert.assertEquals("Wed Mar 27 00:00:00 IST 2019", employeeList.get(0).getDate().toString());
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