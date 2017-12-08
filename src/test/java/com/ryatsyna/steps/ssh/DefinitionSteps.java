package com.ryatsyna.steps.ssh;

import com.ryatsyna.ssh.Connection;
import com.ryatsyna.ssh.Connections;
import com.ryatsyna.steps.ssh.implementations.SshSteps;
import cucumber.api.java.ru.*;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

import java.io.IOException;

public class DefinitionSteps {
    private Connection firstShell;
    private Connection secondShell;

    @Steps
    SshSteps remoteMachine;

    @Дано("^установлено соединение с alpha$")
    public void givenSshConnectionWithAlpha() throws IOException {
        firstShell = Connections.getConnection("alpha");
        remoteMachine.connect(firstShell);
    }

    @Дано("^установлено соединение с beta$")
    public void givenSshConnectionWithBeta() throws IOException {
        secondShell = Connections.getConnection("beta");
        remoteMachine.connect(secondShell);
    }

    @Если("^удаленно создать в домашней директории alpha тестовый каталог (.*)$")
    public void createTestFolderKey(String dirName) throws IOException {
        remoteMachine.makeDirectory(firstShell, dirName);
        Serenity.setSessionVariable("TestFolderKey").to(dirName);
    }

    @И("^в этот каталог загрузить файл (.*)$")
    public void uploadTestFileIntoTestFolderKey(String fileName) throws IOException {
        remoteMachine.uploadFileToADirectory(
                firstShell,
                fileName,
                Serenity.sessionVariableCalled("TestFolderKey"));
        Serenity.setSessionVariable("TestFileKey").to(fileName);
    }

    @То("^файл (.*) успешно загружается в тестовую директорию alpha$")
    public void fileIsUploadedToAlpha(String fileName) throws IOException {
        remoteMachine.checkDirectoryExists(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.goTo(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.checkFileExists(firstShell, fileName);
    }

    @Если("^удалить у alpha и beta тестовый файл (.*) вместе с тестовой директорией$")
    public void deleteTestData(String fileName) throws IOException {
        remoteMachine.removeFile(firstShell, fileName);
        remoteMachine.goHome(firstShell);
        remoteMachine.removeDirectory(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.removeFile(secondShell, fileName);
        remoteMachine.goHome(secondShell);
        remoteMachine.removeDirectory(secondShell, Serenity.sessionVariableCalled("TestFolderKey"));
    }

    @То("^тестовые данные успешно удалены у alpha$")
    public void testDataAlphaWasDeleted() throws IOException {
        remoteMachine.goTo(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.checkFileNotExists(firstShell, Serenity.sessionVariableCalled("TestFileKey"));
        remoteMachine.goHome(firstShell);
        remoteMachine.removeDirectory(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
    }

    @И("^тестовые данные успешно удалены у beta$")
    public void testDataBetaWasDeleted() throws IOException {
        remoteMachine.goTo(secondShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.checkFileNotExists(secondShell, Serenity.sessionVariableCalled("TestFileKey"));
        remoteMachine.goHome(secondShell);
        remoteMachine.removeDirectory(secondShell, Serenity.sessionVariableCalled("TestFolderKey"));
    }

    @То("^файл загружен с корректным содержимым$")
    public void testFileIsEqualToExample(String example) throws IOException {
        remoteMachine.readAndCompareWithExample(
                firstShell,
                Serenity.sessionVariableCalled("TestFileKey"),
                example
        );
    }

    @Если("^сохранить загруженный alpha тестовый файл$")
    public void saveTestFileFromAlpha() throws IOException {
        remoteMachine.getFileFromADirectory(firstShell, Serenity.sessionVariableCalled("TestFileKey"));
    }


    @И("^загрузить этот файл beta в тестовую директорию")
    public void uploadTestFileToBeta() throws IOException {
        remoteMachine.makeDirectory(secondShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.uploadFileToADirectory(
                secondShell,
                Serenity.sessionVariableCalled("TestFileKey"),
                Serenity.sessionVariableCalled("TestFolderKey"));
    }


    @То("^файл успешно копируется beta$")
    public void fileIsUploadedToBeta() throws IOException {
        remoteMachine.goTo(secondShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.checkFileExists(secondShell, Serenity.sessionVariableCalled("TestFileKey"));
    }
}
