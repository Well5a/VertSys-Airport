package com.airport.model;

import javax.ejb.EJB;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.sql.Timestamp;
import javax.persistence.SqlResultSetMapping;

import com.airport.session.AirportEJB;

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
	
	//@EJB
	//private AirportEJB airportEJB;
	
	@ManyToOne
	private Airline airline;
	
	private String runway;
	
	private String name;
	
	private String estimatedArrivalTime = "0";
	
	public String getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}


	public void setEstimatedArrivalTime(String estimatedArrivalTime) {
		//airportEJB.updateEAT(this.id, estimatedArrivalTime);
		System.out.println("Hallo" + estimatedArrivalTime);
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
	
	public void setRunway(String runway) {
		this.runway = runway;
	}
	
	public String getRunway()
	{
		return runway;
	}
}
