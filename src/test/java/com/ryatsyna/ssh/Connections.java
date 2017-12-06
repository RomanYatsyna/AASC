package com.ryatsyna.ssh;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class Connections {
    private static Map<String, Connection> coonections;

    static {
        Type itemsMapType = new TypeToken<Map<String, Connection>>() {}.getType();
        //Todo: убрать хардкод. перенести путь в конфигурационный файл
        String configPath = readConfig("src/test/resources/config/SshConfig.json");
        coonections = new Gson().fromJson(configPath, itemsMapType);
    }

    public static Connection getConnection(String shellName) {
        return coonections.get(shellName);
    }

    private static String readConfig(String path) {
        StringBuilder builder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
