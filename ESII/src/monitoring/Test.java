package monitoring;

import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Test {
	public static WebDriver driver = null;
	private static String Username = "roo";
	private static String Password = "root";
	private static String Website = "http://192.168.99.100:8000/";

	public static void main(String[] args) throws InterruptedException{

		System.setProperty("webdriver.chrome.driver","C:\\Users\\rp\\eclipse-workspace\\ESII\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		checkWebsite();
		Login();

	}
	public static void checkWebsite() {
		try {
		driver.navigate().to(Website);
		driver.manage().window().maximize();
		String title = driver.getTitle();
		System.out.println(title);	
		} catch (Exception e) {
			System.out.println("Website is down");
			try {
				errorOcurred("Website is down");
			} catch (EmailException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public static void Login() {
		try {
			Actions act = new Actions(driver);
			WebElement element = driver.findElement(By.linkText("Log in"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			Thread.sleep(1000);
			act.moveToElement(driver.findElement(By.linkText("Log in"))).click().perform();
			Thread.sleep(1000);
			WebElement username = driver.findElement(By.id("user_login"));
			username.sendKeys(Username);
			WebElement password = driver.findElement(By.id("user_pass"));
			password.sendKeys(Password);
			driver.findElement(By.id("wp-submit")).click();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Log in not possible");
			try {
				errorOcurred("Log in not possible");
			} catch (EmailException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public static void errorOcurred(String error) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("esiigrupo28@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo("goncalosilva99@hotmail.com");
		email.send();
	}
}
