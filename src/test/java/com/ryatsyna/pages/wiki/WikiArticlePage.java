package com.ryatsyna.pages.wiki;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

@DefaultUrl("https://ru.wikipedia.org/wiki/")
@At("#HOST/wiki/(.*)")
@NamedUrls(
        {@NamedUrl(name = "wiki.article", url = "https://ru.wikipedia.org/wiki/{1}")}
)
public class WikiArticlePage extends PageObject {
    @FindBy(id = "firstHeading")
    private WebElementFacade firstHeading;

    @FindBy(id = "searchInput")
    private WebElementFacade searchInput;

    @FindBy(id = "searchButton")
    private WebElementFacade searchButton;

    public void articleIsCorrect(String title) {
        assertThat(firstHeading.getText(), is(equalTo(title)));
    }

    public void findArticle(String query) {
        searchInput.type(query);
        searchButton.click();
    }
}
