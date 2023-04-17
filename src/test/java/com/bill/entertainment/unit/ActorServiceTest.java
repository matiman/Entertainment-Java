package com.bill.entertainment.unit;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
public class ActorServiceTest {

    @Test
    void testGetActorByIdWithInvalidId() {
        String str = "Hello, world!";
        assertEquals("Hello, world!", str);
         }

}