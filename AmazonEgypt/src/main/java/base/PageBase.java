package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PageBase {
	
	public WebDriver driver;
	public  WebDriverWait wait;

	//Super constructor
	public PageBase(WebDriver driver) {
	
		this.driver = driver; 
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	public void click(By button) {	//button
		
	WebElement btnElement= driver.findElement(button); 
	if (btnElement.isDisplayed() ) {
		driver.findElement(button).click();											
	}
	}	
	

	public void type(By txt, String textToSend    ) {
		WebElement typeElement = driver.findElement(txt);
		if (typeElement.isDisplayed()) {
			typeElement.clear();
			typeElement.sendKeys(textToSend);
		}
		
	}

	public void selectListByText (By list, String value) {
		Select listTxt = new Select(driver.findElement(list));
		listTxt.selectByVisibleText(value);

	}


}
