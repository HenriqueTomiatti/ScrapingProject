package WebScrapingGMaps.WebScrapingGMaps.service;

import WebScrapingGMaps.WebScrapingGMaps.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScrapingService {
    final static Logger log = LoggerFactory.getLogger(ScrapingService.class);

    public void scrapeData(String word) {
        System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features-AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);
        options.addArguments("--start-maximized");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");

        WebDriver driver = new EdgeDriver(options);
        driver.get("https://www.google.com.br/maps/preview");

        WebElement inputResearch = driver.findElement(By.xpath("//input[@id=\"searchboxinput\"]"));
        inputResearch.sendKeys(word);
        inputResearch.submit();
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"searchbox-searchbutton\"]/span"));
        searchButton.click();
        waitForIt(5000L);

        ArrayList<Product> products = new ArrayList<>();

        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"QA0Szd\"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[1]/div[1]/div[position()]/div/a"));
        String baseXPath = "//*[@id=\"QA0Szd\"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[1]/div[1]/div[%d]/div/a";
        int start = 3;
        int end = 1001;
        for (int i = start; i <= end; i += 2) {
            String xpath = String.format(baseXPath, i);
            try {
                WebElement elementScroll = driver.findElement(By.xpath(xpath));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementScroll);
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        waitForIt(5000L);
        log.info("elementos: " + elements.size());
        elements.forEach(element -> {
            element.click();
            waitForIt(5000L);
            List<WebElement> establishmentName = driver.findElements(By.xpath("//*[@id=\"QA0Szd\"]/div/div/div[1]/div[3]/div/div[1]/div/div/div[2]/div[2]/div/div[1]/div[1]/h1[position()]"));
            List<WebElement> establishmentRating = driver.findElements(By.xpath("//*[@id=\"QA0Szd\"]/div/div/div[1]/div[3]/div/div[1]/div/div/div[2]/div[2]/div/div[1]/div[2]/div/div[1]/div[2]/span[1]/span[1][position()]"));
            List<WebElement> establishmentStreet = driver.findElements(By.xpath("//*[@id=\"QA0Szd\"]/div/div/div[1]/div[3]/div/div[1]/div/div/div[2]/div[9]/div[3]/button/div/div[2]/div[1][position()]"));
            List<WebElement> establishmentType = driver.findElements(By.xpath("//*[@id=\"QA0Szd\"]/div/div/div[1]/div[3]/div/div[1]/div/div/div[2]/div[2]/div/div[1]/div[2]/div/div[2]/span/span/button[position()]"));
            List<WebElement> establishmentNumber = driver.findElements(By.xpath("//*[@id=\"QA0Szd\"]/div/div/div[1]/div[3]/div/div[1]/div/div/div[2]/div[9]/div[6]/button/div/div[2]/div[1]"));

            log.info("Item capturado");

            for (int i = 0; i < establishmentName.size(); i++) {
                System.out.println("Index: " + i);
                String name = i < establishmentName.size() ? establishmentName.get(i).getText() : "N/A";
                String rating = i < establishmentRating.size() ? establishmentRating.get(i).getText() : "N/A";
                String street = i < establishmentStreet.size() ? establishmentStreet.get(i).getText() : "N/A";
                String type = i < establishmentType.size() ? establishmentType.get(i).getText() : "N/A";
                String number = i < establishmentNumber.size() ? establishmentNumber.get(i).getText() : "N/A";
                products.add(new Product(name, rating, street, type, number));
            }
        });
        driver.quit();
        SheetCreator.creatSheet(products);
    }
    private static void waitForIt(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}




