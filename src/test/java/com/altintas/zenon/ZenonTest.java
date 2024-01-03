package com.altintas.zenon;

import junit.framework.TestCase;

public class ZenonTest extends TestCase {

    Request request;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        request = Zenon.createRequest("https://jsonplaceholder.typicode.com/todos", RequestType.GET);
    }

    public void testCreateRequest() {
        assertEquals("https://jsonplaceholder.typicode.com/todos", request.getUrl());
        assertEquals(RequestType.GET, request.getType());
        assertNull(request.getError());
    }

    public void testSendRequestWithLambda() {
        request.send(result -> {
            assertEquals(200, result.getStatusCode());
            assertNull(result.getError());
            assertTrue(result.isSuccess());
            assertNotNull(result.getData());
            assertNotNull(result.getJsonArray());
        });
    }

    public void testSendRequestWithVariable(){
        Result result = request.send();
        assertEquals(200, result.getStatusCode());
        assertNull(result.getError());
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertNotNull(result.getJsonArray());
    }

}