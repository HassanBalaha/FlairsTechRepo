package com.example.flairstechassistment0;

import dev.failsafe.internal.util.Durations;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;
import java.lang.String;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.Assertion;


public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private Cookie siteCookieList;
    private  WebDriverWait wait;
    private String addUserName="hassanB20";
    private long numberOfRecordsVar;
    @BeforeMethod
    public void setUp() {
        long noOfSeconds = 5;
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        mainPage = new MainPage(driver);


    }

//    @AfterMethod
//    public void tearDown() {
//        driver.quit();
//    }

    @Test(priority = 1)
    public void logIn() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        mainPage.userName.sendKeys("Admin");
        mainPage.passWord.sendKeys("admin123");
        mainPage.submitButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

        var siteCookieList1 = driver.manage().getCookies();
        for (Cookie cookie:siteCookieList1){
            siteCookieList =new Cookie(cookie.getName(),cookie.getValue(),cookie.getPath());
        }
        System.out.println(siteCookieList);
    }
    @Test(priority = 2)
    public void addingNewUser() throws InterruptedException {

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
        driver.manage().addCookie(siteCookieList);
//        newCookie =new Cookie(siteCookieList.getName(),siteCookieList.getValue(),siteCookieList.getPath());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.navigate().refresh();

//navigation to admin tab
//        var pageVisible = wait.until(ExpectedConditions.
//                presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[1]/span/h6")));
//        Assert.assertNotNull(pageVisible);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.adminTab.click();
        numberOfRecordsVar=numberOfRecords();
        System.out.println("number of records before adding new user =>  "+numberOfRecordsVar);


//adding new user
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.addNewUserButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.userRoleDropDown.click();
        mainPage.userRoleDropDownAdmin.click();

        mainPage.statusDropDown.click();
        mainPage.statusDropDownEnabled.click();

        mainPage.employeeName.sendKeys("hassan");
        Thread.sleep(2500);
        mainPage.employeeNameHint.click();

        mainPage.addUserName.sendKeys(addUserName);

        mainPage.addPassWord.sendKeys("a12345678");
        mainPage.addConfirmPassWord.sendKeys("a12345678");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.addSaveButton.click();
        Thread.sleep(1000);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        if (numberOfRecords()>numberOfRecordsVar){System.out.println("the number of records increased after adding new user");};
        System.out.println("number of records after adding new user =>  "+numberOfRecords());

    }
    @Test(priority = 3)
    public void deletingNewUser() throws InterruptedException {

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
        driver.manage().addCookie(siteCookieList);
//        newCookie =new Cookie(siteCookieList.getName(),siteCookieList.getValue(),siteCookieList.getPath());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.navigate().refresh();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.adminTab.click();

        numberOfRecordsVar=numberOfRecords();
//searching for the new user
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.searchBarUserName.sendKeys(addUserName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.searchButton.click();
//Deleting new user
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        mainPage.deleteButton.click();
        Thread.sleep(500);
        mainPage.deleteConfirmButton.click();
//checking for the deleted record
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        if (numberOfRecords()<numberOfRecordsVar){System.out.println("the number of records decreased after deleting new user");};

        System.out.println("number of records after deleting new user =>  "+numberOfRecords());
    }

    public long numberOfRecords() throws InterruptedException {
        Thread.sleep(300);
        var noOFRec =driver.findElements(
                        By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div/div")).size()-1;

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        return noOFRec;
    }
}
