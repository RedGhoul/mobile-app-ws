package com.somethingsblog.app.ws.shared;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @InjectMocks
    Utils utils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(30);
        assertNotNull(userId);
        assertEquals(30,userId.length());
    }

    @Test
    void generateUserIdCreatesUniqueIds() {
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);
        assertNotNull(userId);
        assertNotNull(userId2);
        assertNotEquals(userId2,userId);
    }

    @Test
    void generateAddressId() {
    }

    @Test
    void testGenerateUserId() {
    }

    @Test
    void testGenerateAddressId() {
    }
}