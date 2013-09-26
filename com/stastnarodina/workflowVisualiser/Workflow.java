package com.stastnarodina.workflowVisualiser;

import java.util.HashSet;
import java.util.Set;

public class Workflow {
	
	private String id;
	private String description;
	private String name;
	
	private Set<Resolution> resolutions = new HashSet<Resolution>();
	private Set<Action> actions = new HashSet<Action>();
	private Set<State> states = new HashSet<State>();
	
	private Action startAction;
	private Action reopenAction;
	private Action resolveAction;
	
	
	/**
	 * Resolution by id. Null if it does not exist.
	 * @param id Resolution id
	 * @return Resolution by id. Null if it does not exist.
	 */
	public Resolution getResolutionById(String id) {
		for(Resolution res: resolutions) {
			if(res.getId().equals(id)) {
				return res;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Action by id. Null if it does not exist.
	 * @param id Action id
	 * @return Action by id. Null if it does not exist.
	 */
	public Action getActionById(String id) {
		for(Action action: actions) {
			if(action.getId().equals(id)) {
				return action;
			}
		}
		return null;
	}
	
	/**
	 * State by id. Null if it does not exist.
	 * @param id State id
	 * @return State by id. Null if it does not exist.
	 */
	public State getStateById(String id) {
		for(State state: states) {
			if(state.getId().equals(id)){
				return state;
			}
		}
		
		return null;
	}
	
	public Set<State> getStatesByGroup(Group group) {
		Set<State> out = new HashSet<State>();
		for(State state: states) {
			if(state.getGroup() == group) {
				out.add(state);
			}
		}
		
		return out;
	}


	/**
	 * @return the resolutions
	 */
	public Set<Resolution> getResolutions() {
		return resolutions;
	}


	/**
	 * @param resolutions the resolutions to set
	 */
	public void setResolutions(Set<Resolution> resolutions) {
		this.resolutions = resolutions;
	}


	/**
	 * @return the actions
	 */
	public Set<Action> getActions() {
		return actions;
	}


	/**
	 * @param actions the actions to set
	 */
	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}


	/**
	 * @return the states
	 */
	public Set<State> getStates() {
		return states;
	}


	/**
	 * @param states the states to set
	 */
	public void setStates(Set<State> states) {
		this.states = states;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the startAction
	 */
	public Action getStartAction() {
		return startAction;
	}


	/**
	 * @param startAction the startAction to set
	 */
	public void setStartAction(Action startAction) {
		this.startAction = startAction;
	}


	/**
	 * @return the reopenAction
	 */
	public Action getReopenAction() {
		return reopenAction;
	}


	/**
	 * @param reopenAction the reopenAction to set
	 */
	public void setReopenAction(Action reopenAction) {
		this.reopenAction = reopenAction;
	}


	/**
	 * @return the resolveAction
	 */
	public Action getResolveAction() {
		return resolveAction;
	}


	/**
	 * @param resolveAction the resolveAction to set
	 */
	public void setResolveAction(Action resolveAction) {
		this.resolveAction = resolveAction;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Workflow)) {
			return false;
		}
		Workflow other = (Workflow) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	

}
