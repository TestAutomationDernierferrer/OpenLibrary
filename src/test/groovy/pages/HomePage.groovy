package pages
import geb.Page

class HomePage extends Page {
    static url = ""
    static at = { $("footer ul.locale-options").displayed }
    static content = {
        englishLanguageLink { $("footer ul.locale-options a[data-lang-id=en]") }
    }
    void setLanguageToEnglish() {
        scrollIntoViewAndWait(englishLanguageLink)
        englishLanguageLink.click()
        waitFor { $("html").attr("lang") == "en" }
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
