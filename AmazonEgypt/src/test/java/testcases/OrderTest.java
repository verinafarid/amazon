package testcases;

import org.testng.annotations.Test;

import base.TestBase;
import pages.AddToCartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.VideoGamesPage;


public class OrderTest extends TestBase{
	
HomePage homePageObject;
LoginPage loginhomePageObject;
//String mob = Utilities.LoadProperties.userData.getProperty("mobile");
//String pass = Utilities.LoadProperties.userData.getProperty("password");	

	@Test 
	public void testflow() throws InterruptedException {
        driver.get("https://www.amazon.eg/");
        
        HomePage homePage = new HomePage(driver);
   
        VideoGamesPage videoGamesPage = new VideoGamesPage(driver);
        
        homePage.changeLanguageToEnglish();
        Thread.sleep(2000); // Wait for language change

        homePage.signIn();
        Thread.sleep(2000);
        LoginPage loginPage =new LoginPage(driver);
        
        loginPage.myAccountLoginClick();
        
        homePage.openAllMenu();
        Thread.sleep(2000); // 
        
        homePage.seeAll();
        Thread.sleep(2000); // 
        
        homePage.selectVideoGames();
        Thread.sleep(2000); // 
        
        videoGamesPage.applyFilters();
        Thread.sleep(2000); // Allow filters to be applied

        videoGamesPage.sortByHighToLow();
        Thread.sleep(2000);

        videoGamesPage.addProductsBelow15K();
        Thread.sleep(2000);


        AddToCartPage addToCartPage=new AddToCartPage(driver);

        addToCartPage.verifyAllProductsAddedToCart();
        Thread.sleep(2000);

        addToCartPage.verifyTotalAmountWithShipping();
        Thread.sleep(2000);

//        addToCartTest.addAddressAndChooseCashOnDelivery();
//        Thread.sleep(2000);

        System.out.println("Test Completed Successfully!");

      
    }
}
