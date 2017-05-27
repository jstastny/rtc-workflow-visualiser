package com.stastnarodina.workflowVisualiser;

public enum Group {
	
	IN_PROGRESS("inprogress"),
	OPEN("open"),
	CLOSED("closed"),
	NONE("none");
	
	private String name;
	
	Group(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static Group getByName(String name) {
		if("inprogress".equals(name)) {
			return Group.IN_PROGRESS;
		} else if("open".equals(name)) {
			return Group.OPEN;
		} else if("closed".equals(name)) {
			return Group.CLOSED;
		}
		return Group.NONE;
			
	}
	
	
	
}
