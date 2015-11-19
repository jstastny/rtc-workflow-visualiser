package stastnarodina.workflowVisualiser;

import java.util.HashSet;
import java.util.Set;

public class Action {
	private String id;
	private String name;
	private State targetState;
	
	private Set<Resolution> resolutions = new HashSet<>();
	
	
	public Action() {
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
	 * @return the targetState
	 */
	public State getTargetState() {
		return targetState;
	}


	/**
	 * @param targetState the targetState to set
	 */
	public void setTargetState(State targetState) {
		this.targetState = targetState;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Action [id=" + id + ", name=" + name + "]";
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
		if (!(obj instanceof Action)) {
			return false;
		}
		Action other = (Action) obj;
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
