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

    @Steps
    SshSteps remoteMachine;

    @Пусть("^установлено соединение по SSH с alpha$")
    public void givenSshConnectionEstablished() throws IOException {
        firstShell = Connections.getConnection("beta");
        remoteMachine.connect(firstShell);
    }

    @Если("^удаленно создать в домашней директории тестовый каталог (.*)$")
    public void createTestFolderKey(String dirName) throws IOException {
        remoteMachine.makeDirectory(firstShell, dirName);
        Serenity.setSessionVariable("TestFolderKey").to(dirName);
    }

    @И("^в этот каталог загрузить файл (.*)$")
    public void uploadTestFileIntoTestFolderKey(String fileName) throws IOException {
        remoteMachine.uploadFileFoADirectory(
                firstShell,
                fileName,
                Serenity.sessionVariableCalled("TestFolderKey"));
        Serenity.setSessionVariable("TestFileKey").to(fileName);
    }

    @То("^файл (.*) успешно загружается в тестовую директорию$")
    public void fileIsUploaded(String fileName) throws IOException {
        remoteMachine.checkDirectoryExists(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.goTo(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.checkFileExists(firstShell, fileName);
    }

    @Если("^удалить с сервера тестовый файл (.*) вместе с тестовой директорией$")
    public void deleteTestData(String fileName) throws IOException {
        remoteMachine.removeFile(firstShell, fileName);
        remoteMachine.goHome(firstShell);
        remoteMachine.removeDirectory(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
    }

    @То("^тестовые данные успешно удалены с сервера$")
    public void testDataWasDeleted() throws IOException {
        remoteMachine.goTo(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
        remoteMachine.checkFileNotExists(firstShell, Serenity.sessionVariableCalled("TestFileKey"));
        remoteMachine.goHome(firstShell);
        remoteMachine.removeDirectory(firstShell, Serenity.sessionVariableCalled("TestFolderKey"));
    }

    @То("^файл загружен с корректным содержимым$")
    public void testFileIsEqualToExample(String example) throws IOException {
        remoteMachine.readAndCompareWithExample(
                firstShell,
                Serenity.sessionVariableCalled("TestFileKey"),
                example
        );
    }
}
