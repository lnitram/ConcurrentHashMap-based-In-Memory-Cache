package CacheSystem.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class CacheCleaner extends Thread {

	private ConcurrentHashMap<String, ValueObj> dataStore;
	private TreeMap<Long, ArrayList<String>> expiryIndex;
	private Map.Entry<Long, ArrayList<String>> indexElement;
	private int cleanupFrequency = 30000;

	public CacheCleaner(ConcurrentHashMap<String, ValueObj> dataStore, TreeMap<Long, ArrayList<String>> expiryIndex) {
		setDataStore(dataStore);
		setExpiryIndex(expiryIndex);
		this.run();
	}

	public CacheCleaner() {

	}

	public void setDataStore(ConcurrentHashMap<String, ValueObj> dataStore) {
		this.dataStore = dataStore;
	}

	public void setExpiryIndex(TreeMap<Long, ArrayList<String>> expiryIndex) {
		this.expiryIndex = expiryIndex;
	}

	public void setCleanupFrequency(int cleanupFrequency) {
		this.cleanupFrequency = cleanupFrequency;
	}

	public void run() {
		long currentTime;
		while (true) {
			indexElement = null;
			currentTime = System.currentTimeMillis();

			indexElement = expiryIndex.floorEntry(currentTime);

			while (indexElement != null) {
				Iterator<String> keyIterator = indexElement.getValue().iterator();
				while (keyIterator.hasNext()) {
					String key = keyIterator.next();
					dataStore.remove(key);
				}
				expiryIndex.remove(indexElement.getKey());
				indexElement = expiryIndex.floorEntry(currentTime);
			}

			try {
				synchronized (this) {
					wait(cleanupFrequency);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
