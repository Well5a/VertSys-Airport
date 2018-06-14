package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@NamedQueries({
	@NamedQuery(name="airplane.findAll", query="select a from Airplane a order by a.name"),
	@NamedQuery(name="airplane.updatebyid", query="UPDATE Airplane a SET a.estimatedArrivalTime= param1 WHERE a.id= :param2"), //, resultSetMapping = "updateResult")
	@NamedQuery(name="airplane.findByStatus", query="select a from Airplane a where a.status = :status")
})

@Entity
public class Airplane {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String icao;
	
	@ManyToOne
	private Airline airline;
	
	private String airlineName;

	private String name;
	
	private String estimatedArrivalTime = "0";
	
	private Status status = Status.AIRBORNE;
	
	public Airplane() {
		super();
	}
	
	public String getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}

	public void setEstimatedArrivalTime(String estimatedArrivalTime) {
		this.estimatedArrivalTime = estimatedArrivalTime;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}
	
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status{
		AIRBORNE,
		LANDING,
		PARKING
	}
}
