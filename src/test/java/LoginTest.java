import app.frontend.screens.*;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static core.utils.PropertyLoader.getProperty;
import static org.junit.Assert.*;


public class LoginTest extends BaseTest{

    private LoginScreen login;
    private MainScreen main;

    @DataProvider(name="login")
    public Object[][] dataProviderMethod() {
        return new Object[][] {
                {getProperty("user.email"),"qwerty",login.Invalid_password},
                {"notexistedemail@test.com","qwerty",login.Invalid_e_mail_address},
                {getProperty("user.email"),getProperty("user.password"),main.selected_group}
        };
    }

    @Test
    public void openLoginScreen() {
        StartScreen start = new StartScreen(driver);
        start.openScreen();
        start.clickLoginButton();
        login = new LoginScreen(driver);
        assertTrue(login.waitIsScreenLoaded());
        main = new MainScreen(driver);
    }

    @Test(dependsOnMethods = {"openLoginScreen"},dataProvider = "login")
    public void makeLogin(String email, String password, AndroidElement element) {
        login.waitClickClearEnterDataInField(login.Email, email);
        login.waitClickClearEnterDataInField(login.Password, password);
        login.waitGetClickableElement(login.Login).click();
        assertTrue(main.waitIsElementVisible(element));
    }
}