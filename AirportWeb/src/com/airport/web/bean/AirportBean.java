package com.airport.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.airport.model.*;
import com.airport.session.AirportEJB;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	private String runway;
	
	private ParkingPosition parkingPosition;
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
	}
	
	@PostConstruct
	private void init() {
		airplane = new Airplane();
		parkingPosition = new ParkingPosition();
	}
	
	public String getRunway() {
		return runway;
	}

	public void setRunway(String runway) {
		this.runway = runway;
		// if runway != null
		
		System.out.println("Hallo, Welt!");
	}

	public List<Airplane> getAirplanes() {
		return airportEJB.getAirplanes();
	}
	
	public List<Runway> getRunways() {
		return airportEJB.getRunways();
	}
	
	public List<Runway> getFreeRunways() {
		return airportEJB.getFreeRunways();
	}
	
	public List<ParkingPosition> getParkingPositions() {
		return airportEJB.getParkingPositions();
	}
	
	public List<ParkingPosition> getFreeParkingPositions() {
		return airportEJB.getFreeParkingPositions();
	}
	
	public Airplane getAirplane() {
		return airplane;
	}
	
	public void store() {
		airportEJB.store(airplane);
		airplane = new Airplane();
	}
	
	public void store(Airplane airplane) {
		airportEJB.store(airplane);
		
		System.out.println("ID:" + airplane.getId() + " Name:" + airplane.getName() + " Runway:" + airplane.getRunway() + " ETA:" + airplane.getEstimatedArrivalTime());
		System.out.println(this.airplane.getEstimatedArrivalTime());
		airplane = new Airplane();
	}
	
	// wird nicht aufgerufen?
	public String getEstimatedArrivalTime() {
		return "sdfasdfasdfasdf";
	}
	
	public void setEstimatedArrivalTime(String time) {
		System.out.println("asdfasdfasdfasdfasdf");
		airplane.setEstimatedArrivalTime(time);		
	}
}
