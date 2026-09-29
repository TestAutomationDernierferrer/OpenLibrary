import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.logging.Level
import java.util.logging.Logger

// Silenciar el aviso "Unable to find CDP implementation": el test no usa CDP
Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF)
Logger.getLogger("org.openqa.selenium.chromium").setLevel(Level.OFF)
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