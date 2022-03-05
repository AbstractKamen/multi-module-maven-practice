package it.controllers;

import com.abstractkamen.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class UserControllerIT {

    @Test
    public void testControllerIsWorking() throws IOException {
        final URLConnection urlConnection = new URL("http://localhost:8080/user").openConnection();
        try (final InputStream inputStream = urlConnection.getInputStream();
             final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final String readLine = reader.readLine();
            Assertions.assertFalse(readLine.isEmpty());
            System.out.println(readLine);
        }
    }

    @Test
    public void testUserControllerSavesAndGets() throws IOException {
        final URLConnection urlConnection = new URL("http://localhost:8080/user/save").openConnection();
        urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setDoOutput(true);

        final String newUserJsonString = String.format("{%s,%s,%s}",
                                                       getJsonAssignment("firstName", "Ivan"),
                                                       getJsonAssignment("lastName", "Ivanov"),
                                                       getJsonAssignment("email", "kurzaputin@putinidinahui.com"));
        // do post
        try (OutputStream os = urlConnection.getOutputStream()) {
            byte[] input = newUserJsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        // receive post result
        try (final InputStream inputStream = urlConnection.getInputStream();
             final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final String postResult = reader.readLine();
            final User postResultUser = new ObjectMapper().readValue(postResult, User.class);
            assertPostResultIsPersisted(postResultUser);
        }
    }

    private void assertPostResultIsPersisted(User postResultUser) throws IOException {
        final URLConnection urlConnection = new URL("http://localhost:8080/user/" + postResultUser.getId()).openConnection();
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setDoOutput(true);
        // do get
        try (final InputStream inputStream = urlConnection.getInputStream();
             final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final String readLine = reader.readLine();
            final User getResultUser = new ObjectMapper().readValue(readLine, User.class);
            Assertions.assertEquals(postResultUser.getId(), getResultUser.getId());
            Assertions.assertEquals(postResultUser.getEmail(), getResultUser.getEmail());
            Assertions.assertEquals(postResultUser.getFirstName(), getResultUser.getFirstName());
            Assertions.assertEquals(postResultUser.getLastName(), getResultUser.getLastName());
            System.out.println(getResultUser);
        }
    }

    private String getJsonAssignment(String fieldName, String value) {
        return String.format("%s:%s", getJsonName(fieldName), getJsonName(value));
    }

    private String getJsonName(String value) {
        final String jsonName = "\"%s\"";
        return String.format(jsonName, value);
    }
}
