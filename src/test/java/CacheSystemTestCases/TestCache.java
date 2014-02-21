package CacheSystemTestCases;

import org.junit.Test;

import CacheSystem.CacheSystem;
import static org.junit.Assert.*;

public class TestCache {

	@Test
	public void testTTL() throws Exception {
		CacheSystem cache = new CacheSystem(1000);
		cache.add("a", new String("Hallo"), 2000);
		cache.add("b", new String("Welt"), 4000);
		Thread.sleep(10);

		assertEquals(2, cache.size());
		assertEquals("Hallo", cache.get("a"));
		assertEquals("Welt", cache.get("b"));

		Thread.sleep(3000);
		assertEquals(1,cache.size());
		assertEquals("Welt",cache.get("b"));

		try {
			cache.get("a");
			fail("Expected Exception");
		} catch (Exception e) {	}

		Thread.sleep(2000);
		assertEquals(0,cache.size());

		try {
			cache.get("b");
			fail("Expected Exception");
		} catch (Exception e) {} 
	}
	
	@Test
	public void testAdd() throws Exception {
		CacheSystem cache = new CacheSystem(1000);
		cache.add("a", "Alpha", 1000);
		cache.add("b", "Bravo", 1000);
		Thread.sleep(100);
		assertEquals(2,cache.size());
		assertEquals("Alpha", cache.get("a"));
		assertEquals("Bravo", cache.get("b"));
	}
	
	@Test
	public void testRemove() throws Exception {
		CacheSystem cache = new CacheSystem(1000);
		cache.add("a","merica" , 1000);
		cache.add("b", "elgium", 1000);
		Thread.sleep(100);
		cache.delete("a");
		assertEquals(1, cache.size());
		assertEquals("elgium",cache.get("b"));
		try {
			cache.get("a");
			fail("Expected Exception");
		} catch (Exception e) { }


	}
}
