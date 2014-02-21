package CacheSystemTestCases;

import org.junit.Test;

import CacheSystem.CacheSystem;
import static org.junit.Assert.*;

public class TestCache {
	
	@Test
	public void testCache() throws Exception {
		CacheSystem cache = new CacheSystem(1000);
		cache.add("a", new String("Hallo"), 2000);
		cache.add("b", new String("Welt"), 4000);
		Thread.sleep(10);
		assertNotNull(cache.get("a"));
		assertNotNull(cache.get("b"));
		Thread.sleep(3000);
		assertNotNull(cache.get("b"));
		try {
			cache.get("a");
			fail("Expected Exception");
		} catch (Exception e) {	}
		Thread.sleep(2000);
		try {
			cache.get("b");
			fail("Expected Exception");
		} catch (Exception e) {} 
	}

}
