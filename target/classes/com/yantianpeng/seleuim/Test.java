package com.yantianpeng.seleuim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {


    public static void main(String[] args) throws Exception {
        testGooled();
       // test02();
    }

    public static void testGooled() throws Exception{
        WebDriver webDriver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
        webDriver.get("https://mail.163.com");
        Thread.sleep(3000);
        WebElement iframe = webDriver.findElement(By.tagName("iframe"));
        String id = iframe.getAttribute("id");
        if(id!=null){
            webDriver.switchTo().frame(id);
        }
        WebElement elementByXPath = ((ChromeDriver) webDriver).findElementByXPath("//input[@class=\"j-inputtext dlemail\"]");

      //  WebElement email = ((ChromeDriver) webDriver).findElementByName("email");
        elementByXPath.sendKeys("hkdyantianpeng");
        WebElement password = ((ChromeDriver) webDriver).findElementByName("password");
        password.sendKeys("ytp10141832");

        webDriver.findElement(By.id("dologin")).click();

    }

    public static void test02()throws Exception{
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
        driver.get("https://mail.163.com");
        //调整高度
        ((ChromeDriver) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        //获取网址
        ((ChromeDriver) driver).get("http://epub.cnki.net/KNS/brief/result.aspx?dbprefix=CMFD");
        //高级搜索
       // WebElement high = driver.findElement(By.xpath("//*[@id=\"1_3\"]/a"));
        //high.click();
        Thread.sleep(1000);
        WebElement in = ((ChromeDriver) driver).findElementByName("txt_1_value1");
        //定义搜索内容
        String searchWord = "";
        searchWord = "基因芯片";
        //发送搜索内容
        in.sendKeys(searchWord);
        ((ChromeDriver) driver).findElementByXPath("//*[@id='ddSubmit']/span").click();
        ((ChromeDriver) driver).findElementByXPath("//*[@id='btnSearch']").click();
        Thread.sleep(2000);
        //清除分类获得所有
        ((ChromeDriver) driver).findElementByXPath("//*[@id='XuekeNavi_Div']/div[1]/input[1]").click();
        ((ChromeDriver) driver).findElementByXPath("//*[@id='B']/span/img[1]").click();
        Thread.sleep(2000);
    }

}
