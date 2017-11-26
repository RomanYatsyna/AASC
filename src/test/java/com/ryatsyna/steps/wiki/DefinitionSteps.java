package com.ryatsyna.steps.wiki;

import com.ryatsyna.steps.wiki.implementations.WikiUserSteps;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Тогда;
import net.thucydides.core.annotations.Steps;

public class DefinitionSteps {
    @Steps
    WikiUserSteps user;

    @Пусть("^пользователь находится на главной странице википедии$")
    public void givenUserAtTheMainPage() {
        user.is_at_the_main_page();
    }

    @Когда("^пользователь ищет на главной странице: (.*)")
    public void whenUserSearches(String query) {
        user.looks_for(query);
    }

    @Тогда("^открывается страница со статьей на тему \"([^\"]*)\"")
    public void thenOpensArticlePage(String title) {
        user.user_is_on_the_article_page();
        user.title_is_correct(title);
    }

    @Пусть("^пользователь находится на странице статьи: (.*)$")
    public void givenUserAtTheArticlePage(String article) {
        user.is_at_the_article_page(article);
    }

    @Когда("^пользователь ищет со страницы статьи: (.*)$")
    public void whenEntersQueryFromArticlePage(String query) {
        user.looks_fot_at_the_article_page(query);
    }
}
