package pages

import geb.Page
import geb.waiting.WaitTimeoutException

class HomePage extends Page {

    static url = ""
    static at = { $("footer ul.locale-options").displayed }

    static content = {
        englishLanguageLink { $("footer ul.locale-options a[data-lang-id=en]") }
    }

    void setLanguageToEnglish() {
         for (int attempt = 1; attempt <= 3; attempt++) {
            waitFor { js.exec("return document.readyState") == "complete" }
            scrollIntoViewAndWait(englishLanguageLink)
            englishLanguageLink.click()
            try {
                waitFor(10) { $("html").attr("lang") == "en" }
                return
            } catch (WaitTimeoutException ignored) {
                println ">>> Website language is still '${$("html").attr("lang")}' after clicking 'English' (attempt $attempt of 3)"
            }
        }
        assert $("html").attr("lang") == "en" : "Website language did not change to English after 3 clicks"
    }

    private void scrollIntoViewAndWait(def element) {
        waitFor {
            js.exec(element.firstElement(), """
                arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});
                var r = arguments[0].getBoundingClientRect();
                return r.top >= 0 && r.bottom <= window.innerHeight;
            """)
        }
    }
}
