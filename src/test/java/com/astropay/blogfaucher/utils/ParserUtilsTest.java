package com.astropay.blogfaucher.utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserUtilsTest {

    @Test
    void testMapToJsonString() {
        Map<String, Object> map = Map.of("id", 1, "name", "Agustin");
        String expected = "{\"id\":1,\"name\":\"Agustin\"}";
        assertEquals(ParserUtils.mapToJsonString(map), expected);
    }
}