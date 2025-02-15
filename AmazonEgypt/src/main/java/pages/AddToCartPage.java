package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class AddToCartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public AddToCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Step 7: Verify all selected products are added to the cart
    public void verifyAllProductsAddedToCart() {
        driver.get("https://www.amazon.eg/gp/cart/view.html"); // Open cart page

        List<WebElement> cartItems = driver.findElements(By.cssSelector(".sc-list-item")); // Get all cart items
        if (cartItems.isEmpty()) {
            throw new AssertionError("Cart is empty! Products were not added.");
        }
    }

    // Step 8: Add Address & Choose Cash on Delivery
    public void addAddressAndChooseCashOnDelivery() {
        driver.get("https://www.amazon.eg/gp/buy/addressselect/handlers/display.html"); // Navigate to address page

        try {
            WebElement useThisAddressBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Use this address')]")));
            useThisAddressBtn.click();
        } catch (NoSuchElementException e) {
            System.out.println("No saved address found. Adding a new one...");
            WebElement addNewAddressBtn = driver.findElement(By.xpath("//a[contains(text(),'Add a new address')]"));
            addNewAddressBtn.click();

            // Fill Address Form (Modify XPaths based on actual fields)
            driver.findElement(By.id("address-ui-widgets-enterAddressFullName")).sendKeys("Test User");
            driver.findElement(By.id("address-ui-widgets-enterAddressPhoneNumber")).sendKeys("01012345678");
            driver.findElement(By.id("address-ui-widgets-enterAddressLine1")).sendKeys("123 Test Street");
            driver.findElement(By.id("address-ui-widgets-enterAddressCity")).sendKeys("Cairo");
            driver.findElement(By.id("address-ui-widgets-use-as-my-default")).click();
            driver.findElement(By.id("address-ui-widgets-form-submit-button")).click();
        }

        // Choose Payment Method (Cash on Delivery)
        driver.get("https://www.amazon.eg/gp/buy/payselect/handlers/display.html");
        WebElement cashOnDelivery = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Cash on Delivery']")));
        cashOnDelivery.click();

        WebElement continueBtn = driver.findElement(By.xpath("//input[@value='Continue']"));
        continueBtn.click();

    }

    public void verifyTotalAmountWithShipping() {
        driver.get("https://www.amazon.eg/gp/buy/shipoptionselect/handlers/display.html"); // Navigate to checkout

        // Get Subtotal
        WebElement subtotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#subtotals-marketplace-table .a-size-medium")));
        String subtotalText = subtotalElement.getText().replace("EGP", "").replace(",", "").trim();
        subtotalText = subtotalText.replaceAll("[^0-9.]", ""); // Remove non-numeric characters except dot
        double subtotal = Double.parseDouble(subtotalText); // Convert to double

        System.out.println("Extracted Subtotal: " + subtotal);

        // Get Shipping Fees (If available)
        double shipping = 0.0;
        try {
            WebElement shippingElement = driver.findElement(By.xpath("//span[contains(text(),'Shipping')]/following-sibling::span"));
            String shippingText = shippingElement.getText().replace("EGP", "").replace(",", "").trim();
            shippingText = shippingText.replaceAll("[^0-9.]", ""); // Clean text
            shipping = shippingText.isEmpty() ? 0.0 : Double.parseDouble(shippingText);
        } catch (NoSuchElementException e) {
            System.out.println("No shipping fees detected. Assuming free shipping.");
        }

        System.out.println("Extracted Shipping: " + shipping);

        // Locate the parent span that contains the "Order total" text
        WebElement totalContainer = driver.findElement(By.xpath("//span[contains(@class, 'a-list-item') and contains(., 'Order total')]"));

// Get the text from its child div
        String totalText = totalContainer.getText().replace("Order total", "").replace("EGP", "").replace(",", "").trim();
        totalText = totalText.replaceAll("[^0-9.]", ""); // Remove non-numeric characters except dot

// Convert to double
        double actualTotal = Double.parseDouble(totalText);
        System.out.println("Total Amount: " + actualTotal);



        // Expected Total Calculation
        double expectedTotal = subtotal + shipping;

        // Assert total amount is correct
        if (Math.abs(expectedTotal - actualTotal) > 0.01) {
            throw new AssertionError("Total amount mismatch! Expected: " + expectedTotal + " | Actual: " + actualTotal);
        }

        System.out.println("Total amount verified successfully!");
    }

}
