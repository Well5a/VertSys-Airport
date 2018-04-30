package com.airport.model;

import java.io.Serializable;
import javax.persistence.*;

@NamedQuery(name="runway.findAll", query="select a from Runway a order by a.name")


/**
 * Entity implementation class for Entity: Runway
 *
 */
@Entity
public class Runway implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long Id;
	
	private boolean isUsed;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Runway() {
		super();
	}
   
}
