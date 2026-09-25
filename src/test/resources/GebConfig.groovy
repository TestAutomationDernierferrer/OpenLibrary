import org.openqa.selenium.chrome.ChromeDriver
baseUrl = "https://openlibrary.org/"
waiting {
    timeout = 15
    retryInterval = 0.5
}
// Chrome con ventana visible
driver = { new ChromeDriver() }