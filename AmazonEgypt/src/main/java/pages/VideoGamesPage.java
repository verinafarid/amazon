package pages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

public class VideoGamesPage extends PageBase {
	 private WebDriver driver;

	    private By freeShippingFilter = By.xpath("//span[text()='Free Shipping']");
	    private By newConditionFilter = By.xpath("//span[text()='New']");
	    
	public VideoGamesPage(WebDriver driver) {
		super(driver);
	}
	  public void applyFilters() throws InterruptedException {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Wait until the checkbox is present in the DOM
	        WebElement freeShippingCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("//span[contains(text(),'Free Shipping')]/ancestor::label/input")
	        ));

	        // Scroll into view to avoid hidden elements
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", freeShippingCheckbox);

	        // Click using JavaScriptExecutor (avoids interception issues)
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", freeShippingCheckbox);
	        driver.findElement(newConditionFilter).click();
	    }

	    public void sortByHighToLow() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        // Wait for the sort dropdown to be clickable
	        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(
	                By.id("s-result-sort-select") // The ID might change, inspect the element if needed
	        ));

	        // Select "Price: High to Low"
	        Select select = new Select(sortDropdown);
	        select.selectByVisibleText("Price: High to Low");
	    }


	    public void addProductsBelow15K() {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        while (true) { // Keep searching until we find at least one valid product
	            boolean productFound = false; // Flag to check if we added any products

	            // Get all product price elements on the current page
	            List<WebElement> priceElements = driver.findElements(By.cssSelector(".a-price-whole"));

	            for (WebElement priceElement : priceElements) {
	                try {
	                    String priceText = priceElement.getText().replace(",", "").trim();
	                    int price = Integer.parseInt(priceText);

	                    // If price is below 15K EGP, add to cart
	                    if (price < 15000) {
	                        WebElement addToCartButton = priceElement.findElement(By.xpath("./ancestor::div[contains(@class, 's-result-item')]//button[contains(text(),'Add to cart')]"));

	                        // Wait until the button is clickable and click
	                        WebElement clickableButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
	                        clickableButton.click();

	                        // Small delay to avoid bot detection
	                        Thread.sleep(1000);

	                        productFound = true; // At least one product was found on this page
	                    }
	                } catch (Exception e) {
	                    System.out.println("Skipping item with missing price or Add to Cart button.");
	                }
	            }

	            // If we found at least one product, break the loop (stay on this page)
	            if (productFound) {
	                System.out.println("Added all eligible items on this page.");
	                break;
	            }

	            // If no items below 15K were found, move to the next page
	            try {
	                WebElement nextPage = driver.findElement(By.xpath("//a[contains(@class,'s-pagination-next')]"));
	                nextPage.click();

	                // Wait for the next page to load
	                Thread.sleep(3000);
	            } catch (NoSuchElementException | InterruptedException e) {
	                break;
	            }
	        }
	    }
	

}
