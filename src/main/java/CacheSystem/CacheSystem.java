package CacheSystem; 

import java.util.ArrayList;
import CacheSystem.util.ValueObj;
import CacheSystem.util.CacheCleaner;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class CacheSystem {
	ConcurrentHashMap<String, ValueObj> dataStore;
	TreeMap<Long, ArrayList<String>> expiryIndex;
	
	CacheCleaner cleaner;
	
	public CacheSystem(int clearInterval) {
		this();
		cleaner.setCleanupFrequency(clearInterval);
	}
	
	public CacheSystem (int initialCapacity, float loadFactor) {
		dataStore = new ConcurrentHashMap<String, ValueObj>(initialCapacity, loadFactor);
		initCache();
	}
	
	public CacheSystem () {
		dataStore   = new ConcurrentHashMap<String, ValueObj> ();
		initCache();
	}
	
	private void initCache() {
		expiryIndex = new TreeMap<Long, ArrayList<String>>  ();
		cleaner = new CacheCleaner ();
		cleaner.setDataStore(dataStore);
		cleaner.setExpiryIndex(expiryIndex);
		cleaner.setDaemon(true);
		cleaner.start();
	}
	
	public Object get (String key) throws Exception {
		if (dataStore.isEmpty()) {
			throw (new Exception ("No data stored at this time."));
		}
		else {
			ValueObj vo = dataStore.get(key); 
			if (vo!=null && !vo.isExpired()) {
				return vo.getObject();
			}
			else
				throw (new Exception ("Object does not exist/expired."));
		}
	}
	
	public synchronized void delete (String key) throws Exception {
		if (dataStore.isEmpty())	// Fixed by mlvtr (https://github.com/mlvtr) 
		{
			throw (new Exception ("No data stored at this time."));
		}
		else {
			dataStore.remove(key);
		}
	}
	
	public synchronized void add (String key, Object obj, long inputTTL) throws Exception {
		long timeToLive = System.currentTimeMillis() + (inputTTL);
	
		// Convert input TTL (in seconds) to milliseconds.
		ValueObj vo = new ValueObj(obj, timeToLive); 
		dataStore.put (key, vo);
		ArrayList<String> keys = expiryIndex.get (timeToLive);
		
		if (keys == null)
			keys = new ArrayList<String> (); 

		keys.add(key);
		expiryIndex.put (timeToLive, keys);
	}
	
	public int size() {
		return dataStore.size();
	}
}
