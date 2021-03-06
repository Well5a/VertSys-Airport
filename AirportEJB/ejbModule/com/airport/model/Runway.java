package com.airport.model;

import java.io.Serializable;
import javax.persistence.*;
@NamedQueries ({
	@NamedQuery(name="runway.findAll", query="select a from Runway a order by a.name"),
	@NamedQuery(name="runway.findByName", query="select a from Runway a where a.name = :name"),
	@NamedQuery(name="runway.findFree", query="select a from Runway a where a.isLocked = false")

})

/**
 * Entity implementation class for Entity: Runway
 *
 */
@Entity
public class Runway implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	private boolean isLocked;
	
	private String name;
	
	@OneToOne
	private Airplane airplane;
	
	public Runway() {
		super();
	}
	
	public Runway(long id, boolean isLocked, String name) {
		super();
		this.id = id;
		this.isLocked = isLocked;
		this.name = name;
	}
	
	
	
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(boolean isUsed) {
		this.isLocked = isUsed;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
   
}
