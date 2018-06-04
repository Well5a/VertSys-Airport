package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
@NamedQueries ({
	@NamedQuery(name="parkingposition.findAll", query="select a from ParkingPosition a order by a.name"),
	@NamedQuery(name="parkingposition.findByName", query="select a from ParkingPosition a where a.name = :name"),
	@NamedQuery(name="parkingposition.findFree", query="select a from ParkingPosition a where a.isLocked = false")
})
@Entity
public class ParkingPosition {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private boolean isLocked;
	
	@OneToOne
	private Airplane airplane;
	
	public ParkingPosition() {
		super();
	}
	
	public ParkingPosition(long id, boolean isLocked, String name) {
		super();
		this.id = id;
		this.isLocked = isLocked;
		this.name = name;
	}

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
