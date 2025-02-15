package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.PageBase;

public class HomePage extends PageBase{
	public  WebDriverWait wait;
    private By signInButton = By.id("nav-link-accountList");

    By allMenu = By.id("nav-hamburger-menu");
    By seeAll = By.xpath("//div[contains(text(),'See all')]");
    By videoGames = By.xpath("//div[text()='Video Games']");
    By allVideoGames = By.xpath("//a[contains(text(),'All Video Games')]");
    private By languageSwitcher = By.id("icp-nav-flyout");
    private By englishLanguageOption = By.xpath("//span[contains(text(),'English')]"); 
    private By saveChangesButton = By.id("icp-save-button");
    
	public HomePage(WebDriver driver) {
		super(driver);
	}
	public void signIn() {
        // Click on the sign-in button
	   click(signInButton);

      //  wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
     
    }
	     
	public void openAllMenu() {
	         click(allMenu);
	      
	     }
	public void seeAll() {

		        // Click "See All"
		        WebElement seeAllElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'See all')]")));
		        seeAllElement.click();
		  
	     }
	     
	  public void selectVideoGames() {
	     
	   // Click "Video Games"
			        WebElement videoGamesElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Video Games']")));
			        videoGamesElement.click();
	
	 // Click "AllVideo Games"
			        WebElement allVideoGamesElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'All Video Games')]")));
			        allVideoGamesElement.click();
			        // Scroll into view (to prevent interception)
			        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allVideoGamesElement);

			        // Click using JavaScript Executor (to avoid interception issues)
			        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allVideoGamesElement);
	     
	     }
	  
	    public void changeLanguageToEnglish() throws InterruptedException {
	        click(languageSwitcher);
	        click(englishLanguageOption);
	        click(saveChangesButton);
	    }
	  
}
