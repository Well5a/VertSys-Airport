package com.airport.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.airport.model.Airplane;

@NamedQuery(name="airplanearrivallog.findAll", query="select a from AirplaneArrivalLog a order by a.id")

@Entity
public class AirplaneArrivalLog {

	@Id
	@GeneratedValue
	private long id;

	private Timestamp estimatedArrivalTime;
	
	private Timestamp realArrivalTime;
	
	@OneToOne
	private Airplane airplane;
	
	@OneToOne
	private Runway runway;

	@OneToOne
	private ParkingPosition parkingPosition;
	
	public AirplaneArrivalLog() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}

	public void setEstimatedArrivalTime(Timestamp estimatedArrivalTime) {
		this.estimatedArrivalTime = estimatedArrivalTime;
	}

	public Timestamp getRealArrivalTime() {
		return realArrivalTime;
	}

	public void setRealArrivalTime(Timestamp realArrivalTime) {
		this.realArrivalTime = realArrivalTime;
	}
	
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}

	public ParkingPosition getParkingPosition() {
		return parkingPosition;
	}

	public void setParkingPosition(ParkingPosition parkingPosition) {
		this.parkingPosition = parkingPosition;
	}
}
