package com.ryatsyna.steps.ssh.implementations;

import com.ryatsyna.ssh.Connection;
import net.thucydides.core.annotations.Step;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SshSteps {

    @Step("Установлено соединение с: {0}")
    public void connect(Connection connection) throws IOException {
        connection.connect();
        executeCommandAndCheckResult(connection, "echo hi", "hi\n");
    }

    @Step("Переход в каталог: '{1}'")
    public void goTo(Connection connection, String path) throws IOException {
        connection.changeDirectory(path);
    }

    @Step("Выполнить команду: {1}")
    public void executeCommand(Connection connection, String command) throws IOException {
        connection.executeCommand(command);
    }

    @Step("По выполнению команды '{1}' на консоль выводится: {2}")
    public void executeCommandAndCheckResult(Connection connection, String command, String stdOut) throws IOException {
        assertThat(connection.executeCommand(command), equalTo(stdOut));
    }

    @Step("Файл '{1}' успешно загружен в каталог: {2}")
    public void uploadFileToADirectory(Connection connection, String fileName, String destinationFolder) throws IOException {
        connection.uploadFile(
                System.getProperty("user.dir") + "/src/test/resources/testData/" + fileName,
                destinationFolder);
        assertThat(
                connection.executeCommand("cd " + destinationFolder + "/ && ls"),
                containsString(fileName));
    }

    @Step("Временный файл '{1}' успешно загружен в каталог: {2}")
    public void uploadTempFileToADirectory(Connection connection, String fileName, String destinationFolder) throws IOException {
        connection.uploadFile(
                System.getProperty("user.dir") + "/target/" + fileName,
                destinationFolder);
        assertThat(
                connection.executeCommand("cd " + destinationFolder + "/ && ls"),
                containsString(fileName));
    }

    @Step("Каталог '{1}' успешно создан")
    public void makeDirectory(Connection connection, String dirPath) throws IOException {
        connection.executeCommand("mkdir -p " + dirPath);
    }

    @Step("Каталог '{1}' успешно удален")
    public void removeDirectory(Connection connection, String dirPath) throws IOException {
        connection.executeCommand("rm -rf " + dirPath);
    }

    @Step("Файл '{1}' успешно удален")
    public void removeFile(Connection connection, String fileName) throws IOException {
        connection.executeCommand("rm -f " + fileName);
    }

    @Step("Переход в домашнюю директорию")
    public void goHome(Connection connection) throws IOException {
        connection.changeToUserHomeDirectory();
    }

    @Step("Файл '{1}' найден")
    public void checkFileExists(Connection connection, String fileName) throws IOException {
        assertThat(
                connection.executeCommand("find . -type f -name " + fileName),
                containsString(fileName));
    }

    @Step("Файл '{1}' не найден")
    public void checkFileNotExists(Connection connection, String fileName) throws IOException {
        assertThat(
                connection.executeCommand("find . -type f -name " + fileName),
                not(containsString(fileName)));
    }

    @Step("Директория '{1}' найдена")
    public void checkDirectoryExists(Connection connection, String dirName) throws IOException {
        assertThat(
                connection.executeCommand("find . -type d -name " + dirName),
                containsString(dirName));
    }

    @Step("Директория '{1}' не найдена")
    public void checkDirectoryNotExists(Connection connection, String dirName) throws IOException {
        assertThat(
                connection.executeCommand("find . -type d -name " + dirName),
                not(containsString(dirName)));
    }

    @Step("Cодержимое файла соответствует образцу")
    public void readAndCompareWithExample(Connection connection, String filePath, String example) throws IOException {
        assertThat(
                connection.executeCommand("cat " + filePath)
                        .replace(" ", "")
                        .replace("\n", "")
                        .replace("\r", ""),
                is(equalTo(example
                        .replace(" ", "")
                        .replace("\n", "")
                        .replace("\r", "")))
        );
    }

    @Step("Файл '{1}' скачан во временную директорию")
    public void getFileFromADirectory(Connection connection, String fileName) {
        connection.downloadFile(fileName);
    }
}