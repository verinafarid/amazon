package base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public WebDriver driver;


	@BeforeMethod
	public void startDriver()  
	{

		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();

		String url = "https://www.amazon.eg/";
		driver.navigate().to(url);
		System.out.println("driver navigated to " + url ) ;

	}
	@AfterMethod
	public void CloseDriver( ) {

		//driver.close();


	}

	

	}