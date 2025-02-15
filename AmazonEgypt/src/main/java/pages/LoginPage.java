package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

public class LoginPage extends PageBase{
    private WebDriver driver;
    private WebDriverWait wait;
    

    private By MobileTxT 	 = 	By.id("ap_email");
    private By continueBtn	 =	By.id("continue");
    private By passwordTXT	 =	By.id("ap_password");
    private By submitBtn	 =	By.id("signInSubmit");
    private By accountIdentifier = By.id("nav-link-accountList-nav-line-1");
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void myAccountLoginClick()
	{
	//
		type(MobileTxT,"01200802273");
		click(continueBtn);
		
		//driver.findElement(By.id("ap_email")).sendKeys("01200802273");
		// click(continueBtn);
        // Enter phone number
      // wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_email"))).sendKeys("01200802273");
     //  driver.findElement(continueBtn).click();


        // Enter password
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordTXT)).sendKeys("Marina@12");
        driver.findElement(submitBtn).click();

        // Validate if sign-in was successful
        try {
            WebElement accountName = wait.until(ExpectedConditions.visibilityOfElementLocated(accountIdentifier));
            System.out.println("Successfully signed in as: " + accountName.getText());
        } catch (TimeoutException e) {
            throw new AssertionError("Sign-in failed. Please check credentials.");
        }
	}

}
