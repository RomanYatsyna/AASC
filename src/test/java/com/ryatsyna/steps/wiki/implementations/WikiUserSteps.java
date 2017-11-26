package com.ryatsyna.steps.wiki.implementations;


import com.ryatsyna.pages.wiki.WikiArticlePage;
import com.ryatsyna.pages.wiki.WikiMainPage;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getPages;

public class WikiUserSteps {
    WikiMainPage mainPage;
    WikiArticlePage articlePage;

    @Step
    public void is_at_the_main_page() {
        mainPage.open();
    }

    @Step
    public void enters(String keyword) {
        mainPage.enterKeywords(keyword);
    }

    @Step
    public void starts_search() {
        mainPage.clickSearch();
    }

    @Step
    public void looks_for(String keyword) {
        enters(keyword);
        starts_search();
    }

    @Step
    public void title_is_correct(String title) {
        articlePage.articleIsCorrect(title);
    }

    @Step
    public void user_is_on_the_article_page() {
        getPages().currentPageAt(WikiArticlePage.class);
    }

    @Step
    public void is_at_the_article_page(String article) {
        articlePage.open("wiki.article", PageObject.withParameters(article));
    }

    @Step
    public void looks_fot_at_the_article_page(String query) {
        articlePage.findArticle(query);
    }
}