package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name="parkingposition.findAll", query="select a from ParkingPosition a order by a.name")

@Entity
public class ParkingPosition {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private boolean isLocked;
	
	private Airplane airplane;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
}
