package com.frequentflyer.cms.models.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import com.frequentflyer.cms.models.ScheduledRoute;

public class SortedScheduledRoutes {
	
	private HashMap<Integer, ArrayList<ScheduledRoute>> sortedMap;
	
	

	public SortedScheduledRoutes() {
		super();
		this.sortedMap = new HashMap<>();
	}

	public SortedScheduledRoutes(HashMap<Integer, ArrayList<ScheduledRoute>> sortedMap) {
		super();
		this.sortedMap = sortedMap;
	}

	public HashMap<Integer, ArrayList<ScheduledRoute>> getSortedMap() {
		return sortedMap;
	}

	public void setSortedMap(HashMap<Integer, ArrayList<ScheduledRoute>> sortedMap) {
		this.sortedMap = sortedMap;
	}
	
	public void insertRoute(Integer key, ScheduledRoute route) {
		if (this.sortedMap == null) {
			this.sortedMap = new HashMap<>();
		}
		if (this.sortedMap.containsKey(key)) {
			ArrayList<ScheduledRoute> existingList = this.sortedMap.get(key);
			existingList.add(route);
			this.sortedMap.replace(key, existingList);
		} else {
			ArrayList<ScheduledRoute> newList = new ArrayList<ScheduledRoute>();
			newList.add(route);
			this.sortedMap.put(key, newList);
		}
	}

}
