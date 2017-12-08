package com.ryatsyna.ssh;

import com.jcabi.log.Logger;
import com.jcabi.ssh.Shell;
import com.jcabi.ssh.Ssh;
import com.jcabi.ssh.SshByPassword;
import org.cactoos.io.DeadInput;

import java.io.*;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class Connection {
    private String name;
    private Shell shell;
    private String host;
    private int port;
    private String login;
    private String password;
    private String currentDirectory;

    Connection(String name, String host, int port, String login, String password) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private String getCurrentDirectory() {
        return currentDirectory
                .replace("\n", "") + "/";
    }

    public void connect() throws UnknownHostException {
        shell = new SshByPassword(host, port, login, password);
    }

    public void changeDirectory(String path) throws IOException {
        currentDirectory = executeCommand("cd /home/" + login + "/" + path + " && pwd");
    }

    public void changeToUserHomeDirectory() throws IOException {
        currentDirectory = executeCommand("cd /home/" + login + " && pwd");
    }

    public String executeCommand(String command) throws IOException {
        if (currentDirectory == null)
            return new Shell.Plain(shell).exec(command);
        else
            return new Shell.Plain(shell).exec("cd " + getCurrentDirectory()
                    + " && " + command);
    }

    public void uploadFile(String sourcePath) throws IOException {
        File file = new File(sourcePath);
        shell.exec(
                String.format(
                        "cat > " + currentDirectory + "/%s",
                        Ssh.escape(file.getName())
                ),
                new FileInputStream(file),
                Logger.stream(Level.INFO, true),
                Logger.stream(Level.WARNING, true)
        );
    }

    public void uploadFile(String sourcePath, String destinationPath) throws IOException {
        File file = new File(sourcePath);
        shell.exec(
                String.format(
                        "cat > /home/" + login + "/" + destinationPath + "/%s",
                        Ssh.escape(file.getName())
                ),
                new FileInputStream(file),
                Logger.stream(Level.INFO, true),
                Logger.stream(Level.WARNING, true)
        );
    }

    public void downloadFile(String fileName) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream outputStream = new FileOutputStream("target/" + fileName);

            shell.exec(
                    "cat " + getCurrentDirectory() + fileName,
                    new DeadInput().stream(),
                    baos,
                    Logger.stream(Level.WARNING, this)
            );
            baos.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}