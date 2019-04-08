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
	public void testGetMarkers_ExpectNonEmptyList() {
		Entity markerEntity1 = new Entity("UserMarker");
		markerEntity.setProperty("lat", "38.8999");
		markerEntity.setProperty("lng", "-77.0484");
		markerEntity.setProperty("content", "GW");
		datastore.put(markerEntity1);

		Entity markerEntity1 = new Entity("UserMarker");
		markerEntity.setProperty("lat", "38.9851");
		markerEntity.setProperty("lng", "-77.0949");
		markerEntity.setProperty("content", "Bethesda");
		datastore.put(markerEntity1);

		List<UserMarker> markers = dataStore.getMarkers();
		assertEquals(2, markers.size());
		assertTrue(markers.get(0).equals(new UserMarker(lat.fromString("38.8999"), lng.fromString("-77.0484"), "GW")) );
		assertTrue(markers.get(1).equals(new UserMarker(lng.fromString("38.9851"), lng.fromString("-77.0949"), "Bethesda")));
	}

}
