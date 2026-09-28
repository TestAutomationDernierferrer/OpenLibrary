import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
baseUrl = "https://openlibrary.org/"
cacheDriver = false
waiting {
    timeout = 15
    retryInterval = 0.5
}
driver = { new ChromeDriver() }
environments {
    chrome {
        driver = { new ChromeDriver() }
    }
    chromeHeadless {
        driver = {
            ChromeOptions options = new ChromeOptions()
            options.addArguments("--headless=new", "--window-size=1400,1000")
            new ChromeDriver(options)
        }
    }
}