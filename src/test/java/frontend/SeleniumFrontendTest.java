package frontend;

import services.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by trizalio on 5/24/14.
 */
public class SeleniumFrontendTest {
    private static final String START_URL = "http://localhost:8090";
    private static final String LOGIN_NAME = "login";
    private static final String REGISTER_NAME = "register";
    private static final String TIMER_NAME = "timer";
    private static final String QUIT_NAME = "logout";

    private static final String AUTH_NAME = "auth_done";
    private static final String AUTH_DONE = "Авторизован";
    private static final String AUTH_NONE = "Не авторизован";

    private static Map<String, String> pages = new HashMap<>();
    private static WebDriver driver = new HtmlUnitDriver(true);

    @Before
    public void init(){
        AccountService.initBase();
        pages.put("", "Главная страница");
        pages.put(LOGIN_NAME, "Авторизация");
        pages.put(REGISTER_NAME, "Регистрация");
        pages.put(TIMER_NAME, "Таймер");
    }
    @Test
    public void checkAllPages(){
        System.out.append("SeleniumFrontendTest: checkAllPages\n");
        for (Map.Entry<String, String> entry: pages.entrySet()){

            getPage(entry.getKey());

            /*System.out.append(driver.getCurrentUrl()).append("\n");
            System.out.append(driver.getPageSource());
            System.out.append(driver.getTitle()).append("\n");
            System.out.append(entry.getValue()).append("\n");*/

            boolean pageFailed = (driver.getTitle() == (entry.getValue()));

            System.out.append(START_URL).append("/")
                    .append(entry.getKey());
            if(pageFailed){
                System.out.append(" - fail").append(".\n");
            }else{
                System.out.append("- ok").append(".\n");
            }

            Assert.assertFalse(pageFailed);
        }
    }
    @Test
    public void checkLoginAndRegister(){
        System.out.append("SeleniumFrontendTest: checkLoginAndRegister\n");

        //Assert.assertFalse(testLogin("tully", "hello"));
        Assert.assertFalse(testLogin("Test_user", "Test_pass"));
        Assert.assertTrue(testRegister("Test_user", "Test_pass"));
        Assert.assertFalse(testQuit());
        Assert.assertFalse(testRegister("Test_user", "Test_pass"));
        Assert.assertTrue(testLogin("Test_user", "Test_pass"));
    }
    public void quit (){
        driver.quit();
    }

    private void getPage(String url){
        driver.get(START_URL + "/" + url);
        (new WebDriverWait(driver, 4)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                System.out.append(d.getTitle()).append(".\n");
                return (d.getTitle().length() > 0);
            }
        });
    }

    private boolean testLogin(String login, String pass){
        System.out.append("\n").append("SeleniumFrontendTest: testLogin - login: ").append(login)
                .append(", pass:").append(pass).append(" ");

        getPage(LOGIN_NAME);
        fillAndSubmitData(login, pass);
        System.out.append(driver.getTitle()).append(".\n");
        return authDone();
    }
    private boolean testRegister(String login, String pass){
        System.out.append("\n").append("SeleniumFrontendTest: testRegister - login: ").append(login)
                .append(", pass:").append(pass).append(" ");

        getPage(REGISTER_NAME);
        fillAndSubmitData(login, pass);
        System.out.append(driver.getTitle()).append(".\n");
        return authDone();
    }
    public boolean testQuit(){
        System.out.append("\n").append("SeleniumFrontendTest: testQuit").append(" ");

        getPage(QUIT_NAME);
        System.out.append(driver.getTitle()).append(".\n");
        return authDone();
    }

    private String getStatus(){
        return driver.findElement(By.id("status")).getText();
    }
    private String getAuth(){
        return driver.findElement(By.id(AUTH_NAME)).getText();
    }
    private boolean authDone(){
        System.out.append("SeleniumFrontendTest: authDone ? ");
        System.out.append("!").append(getAuth()).append("?").append(AUTH_DONE).append(" = ");
        if(getAuth().equals(AUTH_DONE)){
            System.out.append("Авторизован.\n");
            return true;
        }else{
            System.out.append("Не авторизован.\n");
            return false;
        }
    }

    private void fillAndSubmitData(String login, String pass){

        //System.out.append(driver.getCurrentUrl()).append(".\n");
        //System.out.append(driver.getPageSource()).append(".\n");

        WebElement loginField = driver.findElement(By.name("login"));
        loginField.sendKeys(login);

        WebElement passField = driver.findElement(By.name("pass"));
        passField.sendKeys(pass);

        passField.submit();
    }

}
