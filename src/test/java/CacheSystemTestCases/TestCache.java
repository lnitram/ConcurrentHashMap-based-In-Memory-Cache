package CacheSystemTestCases;

import org.junit.Test;

import CacheSystem.CacheSystem;
import static org.junit.Assert.*;

public class TestCache {

	@Test
	public void testTTL() throws Exception {
		CacheSystem cache = new CacheSystem(100);
		cache.add("a", new String("Hallo"), 200);
		cache.add("b", new String("Welt"), 400);

		assertEquals(2, cache.size());
		assertEquals("Hallo", cache.get("a"));
		assertEquals("Welt", cache.get("b"));

		Thread.sleep(300);
		assertEquals(1, cache.size());
		assertEquals("Welt", cache.get("b"));
		assertNull(cache.get("a"));

		Thread.sleep(200);
		assertEquals(0, cache.size());
		assertNull(cache.get("b"));
	}

	@Test
	public void testAdd() throws Exception {
		CacheSystem cache = new CacheSystem(1000);
		cache.add("a", "Alpha", 1000);
		cache.add("b", "Bravo", 1000);

		assertEquals(2, cache.size());
		assertEquals("Alpha", cache.get("a"));
		assertEquals("Bravo", cache.get("b"));
	}

	@Test
	public void testRemove() throws Exception {
		CacheSystem cache = new CacheSystem(1000);
		cache.add("a", "merica", 1000);
		cache.add("b", "elgium", 1000);

		cache.delete("a");

		assertEquals(1, cache.size());
		assertEquals("elgium", cache.get("b"));
		assertNull(cache.get("a"));
	}

	@Test
	public void testContains() throws Exception {
		CacheSystem cache = new CacheSystem(10);
		cache.add("a", "Hallo", 30);
		cache.add("b", null, 100);

		assertEquals(2, cache.size());
		assertTrue(cache.contains("a"));
		assertTrue(cache.contains("b"));
		assertFalse(cache.contains("c"));

		Thread.sleep(50);
		assertFalse(cache.contains("a"));
	}
}
