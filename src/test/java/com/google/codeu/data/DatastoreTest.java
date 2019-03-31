package com.google.codeu.data;


import com.google.appengine.api.datastore.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;


@RunWith(JUnit4.class)
public class DatastoreTest {

    private DatastoreService datastoreService;

    private Datastore dataStore;

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        datastoreService = DatastoreServiceFactory.getDatastoreService();
        dataStore = new Datastore(datastoreService);
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testGetMesssagesWithNullRecipient_ExpectEmptyList() {
        assertTrue(dataStore.getMessages(null).isEmpty());
    }

    @Test
    public void testGetMesssagesWithRecipient_ExpectNonEmptyList() {
        Entity entity1 = new Entity("Message", "38400000-8cf0-11bd-b23e-10b96e4ef00d");
        entity1.setProperty("user", "user1");
        entity1.setProperty("text", "text1");
        entity1.setProperty("timestamp", 1);
        entity1.setProperty("recipient", "recipient");

        Entity entity2 = new Entity("Message", "38400000-8cf0-11bd-b23e-10b96e4ef00e");
        entity2.setProperty("user", "user2");
        entity2.setProperty("text", "text2");
        entity2.setProperty("timestamp", 2);
        entity2.setProperty("recipient", "recipient");

        datastoreService.put(entity1);
        datastoreService.put(entity2);

        List<Message> messages = dataStore.getMessages("recipient");
        assertEquals(2, messages.size());
        assertTrue(messages.get(0).equals(new Message(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00e"), "user2", "text2", 2, "recipient")));
        assertTrue(messages.get(1).equals(new Message(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), "user1", "text1", 1, "recipient")));
    }


}
