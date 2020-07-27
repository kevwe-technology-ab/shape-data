package net.lulli.shape.data.utils;

import net.lulli.utils.PropertiesManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class PropertiesManagerTest
{
    @Test
    public void constructorTest(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("database.properties").getFile());
        PropertiesManager propertiesManager =  PropertiesManager.fromFile(file);
        Assert.assertNotNull(propertiesManager.getProperties().getProperty("database.sqlite.JDBC_URL"));
        Assert.assertNotNull(propertiesManager.getProperties().getProperty("database.sqlite.DRIVER_CLASS_NAME"));
    }
}
