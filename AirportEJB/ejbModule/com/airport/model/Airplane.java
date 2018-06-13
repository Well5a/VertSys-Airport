package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;


@NamedQueries({
	@NamedQuery(name="airplane.findAll", query="select a from Airplane a order by a.name"),
	@NamedQuery(name="airplane.updatebyid", query="UPDATE Airplane a SET a.estimatedArrivalTime= param1 WHERE a.id= :param2") //, resultSetMapping = "updateResult")
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
	
	@OneToOne
	private Runway runway;
	
	private String runwayName;

	private String name;
	
	private String estimatedArrivalTime = "0";
	
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

	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}

	public String getRunwayName() {
		return runwayName;
	}

	public void setRunwayName(String runwayName) {
		this.runwayName = runwayName;
	}	
}
