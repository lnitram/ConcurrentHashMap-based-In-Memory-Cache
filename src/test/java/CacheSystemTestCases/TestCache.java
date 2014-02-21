package CacheSystemTestCases;

import org.junit.Test;

import CacheSystem.CacheSystem;
import static org.junit.Assert.*;

public class TestCache {
	
	@Test
	public void testCache() throws Exception {
		CacheSystem cache = new CacheSystem();
		cache.add("a", new String("Hallo"), 1);
		cache.add("b", new String("Welt"), 40);
		Thread.sleep(10);
		assertNotNull(cache.get("a"));
		assertNotNull(cache.get("b"));
		Thread.sleep(31000);
		assertNotNull(cache.get("b"));
		boolean exceptionCaught = false;
		try {
			cache.get("a");
		} catch (Exception e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);

	}

}
