package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import java.sql.Timestamp;

@NamedQuery(name="airplane.findAll", query="select a from Airplane a order by a.name")

@Entity
public class Airplane {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String icao;
	
	@ManyToOne
	private Airline airline;
	
	private String name;
	
	private Timestamp estimatedArrivalTime;
	
	public Timestamp getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}


	public void setEstimatedArrivalTime(Timestamp estimatedArrivalTime) {
		this.estimatedArrivalTime = estimatedArrivalTime;
	}


	public Airplane() {
		super();
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
