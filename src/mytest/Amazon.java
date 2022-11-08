package mytest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Amazon {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		System.setProperty("Webdriver.chrome.driver", "C:\\Selenium\\selenium-server-4.6.0.exe");
		driver = new ChromeDriver();
		// Navigate to amazon.com
		driver.get("https://www.amazon.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Test
	public void AmazonBasicsProducts() throws InterruptedException {
		// identify element
		WebElement searchResults = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		// enter text with sendKeys()
		searchResults.sendKeys("Amazon Basics");
		// Explicit wait condition for search results then apply submit()
		driver.findElement(By.id("nav-search-submit-button")).click();

		// Validate Checkbox isSelected and click
		List<WebElement> list = driver.findElements(By
				.xpath("//body/div[@id='a-page']/div[@id='search']/span[1]/div[1]/h1[1]/div[1]/div[1]/div[1]/div[1]"));

		for (WebElement webElement : list) {
			System.out.println(webElement.getText());
			if (!"Amazon Brands".equals(webElement.getAttribute("class"))) {
				webElement.click();
			}
		}

		// Select item and verify that the product page is displayed
		driver.findElement(By.xpath("//span[contains(text(),'Amazon Basics 18-Sheet Cross-Cut Paper, CD, and Cr')]"))
				.click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='dp']")).isDisplayed());

		// Click on 'Add to cart" button
		driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();

		// Verify item added to cart
		String actualText = driver.findElement(By.xpath("//span[contains(text(),'Added to Cart')]")).getText();
		System.out.println(actualText);
		String expectedText = "Added to Cart";

		Assert.assertEquals(actualText, expectedText);

		// Verify cart shows count as '1'
		String count = driver.findElement(By.id("nav-cart-count")).getText();
		String expectedCount = "1";

		Assert.assertEquals(count, expectedCount);

		Thread.sleep(10000);
	}

	//
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}