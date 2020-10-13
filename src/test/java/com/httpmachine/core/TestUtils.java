package com.httpmachine.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestUtils {
    public static String readAsString(InputStream payload) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(payload));
        return reader.lines()
                .collect(Collectors.joining("\n"));
    }
}
