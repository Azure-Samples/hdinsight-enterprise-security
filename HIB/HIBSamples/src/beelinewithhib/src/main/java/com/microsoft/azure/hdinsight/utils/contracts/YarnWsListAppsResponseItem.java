package com.microsoft.azure.hdinsight.utils.contracts;

import java.util.*;

public class YarnWsListAppsResponseItem {
	private String id;
	private String user;
	
	public String getId() {
		return this.id;
	}

	public void setId(String _id) {
		this.id = _id;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String _user) {
		this.id = _user;
	}
}
