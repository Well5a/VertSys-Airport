package com.airport.web.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Iterator;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.airport.model.*;
import com.airport.model.Airplane.Status;
import com.airport.session.AirportEJB;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	private Airline airline;
	
	private Runway runway;

	private ParkingPosition parkingPosition;
	
	private AirplaneArrivalLog airplaneArrivalLog;

	
	//For SelectOneMenu
	private long selectedRunway;	
	private long selectedAirplane;	
	private long selectedParkingAirplane;
	private long selectedParkingPosition;
	private long landingAirplane;	
	private long parkingAirplane;
	private long startingAirplane;	
	private String estimatedArrivalTime;
	private String estimatedArrivalTimeStart;
	private String realArrivalTime;
	
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
		runway = new Runway();
	}
		
	@PostConstruct
	private void init() {
		airplane = new Airplane();
		
		storeRunway(new Runway(1, false, "north"));
		storeRunway(new Runway(2, false, "west"));
		storeRunway(new Runway(3, false, "south"));
		storeRunway(new Runway(4, false, "east"));
		
		storeParkingPosition(new ParkingPosition(10, false, "a"));
		storeParkingPosition(new ParkingPosition(20, false, "b"));
		storeParkingPosition(new ParkingPosition(30, false, "c"));
		storeParkingPosition(new ParkingPosition(40, false, "d"));
		storeParkingPosition(new ParkingPosition(50, false, "e"));
		storeParkingPosition(new ParkingPosition(60, false, "f"));
		storeParkingPosition(new ParkingPosition(70, false, "g"));
		storeParkingPosition(new ParkingPosition(80, false, "h"));
		
		storeAirline(new Airline(100, "Air Berlin"));
		storeAirline(new Airline(200, "Niki"));
		storeAirline(new Airline(300, "Monarch Airlines"));
		storeAirline(new Airline(400, "Alitalia"));
	}
	
	
	// -------------------------- Store Methods ------------------------------------
	
	public void store() {
		AirplaneArrivalLog aal = new AirplaneArrivalLog();
		aal.setEstimatedArrivalTime(estimatedArrivalTime);
		airportEJB.storeAirplaneArrivalLog(aal);
		airplane.setAirplaneArrivalLog(aal);
		
		String name = airplane.getAirlineName();
		System.out.println(name);
		List<Airline> airlines = getAirlines();
		Iterator<Airline> airlineIter = airlines.iterator();
		Airline airline = new Airline();
		while(airlineIter.hasNext()) {
			airline = airlineIter.next();
			System.out.println(airline.getName());
			if(airline.getName().equals(name)) 
			{
				System.out.println("true");
				break;
			}
		}	
		airplane.setAirline(airline);
			
		airportEJB.store(airplane);
		
		aal.setAirplane(airplane);
		airportEJB.storeAirplaneArrivalLog(aal);
				
		airplane = new Airplane();
	}
	
	public void storeAirline(Airline airline) {
		airportEJB.storeAirline(airline);
		System.out.println("Store Airline: " + airline.getName());
	}
	
	public void storeRunway(Runway runway) {
		airportEJB.storeRunway(runway);
		System.out.println("Store Runway: " + runway.getName());
	}
	
	public void storeParkingPosition(ParkingPosition parkingPosition) {
		airportEJB.storeParkingPosition(parkingPosition);
		System.out.println("Store ParkingPosition: " + parkingPosition.getName());
	}
	

	// -------------------------- Airport Control Methods ------------------------------------
	
	public void assignRunway() {
		//Check if another runway has this airplane already assigned
		Runway otherRunway = runwayByPlane(selectedAirplane);
		if(otherRunway != null) {
			otherRunway.setAirplane(null);
			otherRunway.setIsLocked(false);
			airportEJB.storeRunway(otherRunway);
		}
				
		Runway runway = runwayById(selectedRunway);		
		Airplane airplane = airplaneById(selectedAirplane);
		
		runway.setAirplane(airplane);
		runway.setIsLocked(true);
		
		airportEJB.storeRunway(runway);
		
		AirplaneArrivalLog aal = airplane.getAirplaneArrivalLog();
		aal.setRunway(runway);
		airportEJB.storeAirplaneArrivalLog(aal);
	}	
	
	public void land() {
		//if no Runway was already assigned
		if(runwayByPlane(landingAirplane).getAirplane() == null) {
			return;
		}
		
		Airplane airplane = airplaneById(landingAirplane);
				
		airplane.setStatus(Status.LANDING);		
		airportEJB.store(airplane);
		
		AirplaneArrivalLog aal = airplane.getAirplaneArrivalLog();
		aal.setRealArrivalTime(realArrivalTime);
		airportEJB.storeAirplaneArrivalLog(aal);
	}
	
	public void assignParkingPosition() {	
		//Check if another parking position has this airplane already assigned
		ParkingPosition otherParkingPosition = parkingPositionByPlane(selectedParkingAirplane);
		if(otherParkingPosition != null) {
			otherParkingPosition.setAirplane(null);
			otherParkingPosition.setIsLocked(false);
			airportEJB.storeParkingPosition(otherParkingPosition);
		}
		
		ParkingPosition parkingPosition = parkingPositionById(selectedParkingPosition);		
		parkingPosition.setIsLocked(true);
		
		Airplane airplane = airplaneById(selectedParkingAirplane);
		parkingPosition.setAirplane(airplane);			
		
		airportEJB.storeParkingPosition(parkingPosition);
		
		AirplaneArrivalLog aal = airplane.getAirplaneArrivalLog();
		aal.setParkingPosition(parkingPosition);
		airportEJB.storeAirplaneArrivalLog(aal);
	}
	
	public void park() {
		//if no ParkingPosition was already assigned
		if(parkingPositionByPlane(parkingAirplane).getAirplane() == null) {
			return;
		}
		
		Airplane airplane = airplaneById(parkingAirplane);
		Runway runway = runwayByPlane(parkingAirplane);
		
		runway.setIsLocked(false);
		runway.setAirplane(null);
		airportEJB.storeRunway(runway);
		
		airplane.setStatus(Status.PARKING);
		airportEJB.store(airplane);
	}
	
	public void start() {		
		Airplane airplane = airplaneById(startingAirplane);
		ParkingPosition parkingPosition = parkingPositionByPlane(startingAirplane);
		
		parkingPosition.setIsLocked(false);
		parkingPosition.setAirplane(null);
		airportEJB.storeParkingPosition(parkingPosition);	
				
		airplane.setStatus(Status.AIRBORNE);		
		airportEJB.store(airplane);
		
		AirplaneArrivalLog aal = airplane.getAirplaneArrivalLog();
		aal.setEstimatedArrivalTime(estimatedArrivalTimeStart);
		aal.setRealArrivalTime(null);
		aal.setRunway(null);
		aal.setParkingPosition(null);
		airportEJB.storeAirplaneArrivalLog(aal);
	}
	

	
	// -------------------------- Get Methods by ID ------------------------------------
	
	private Airplane airplaneById(long planeId) {
		List<Airplane> airplanes = getAirplanes();
		Iterator<Airplane> airplaneIter = airplanes.iterator();
		Airplane airplane = new Airplane();
		while(airplaneIter.hasNext())
		{
			airplane = airplaneIter.next();
			if(airplane.getId() == planeId)
			{
				return airplane;
			}
		}
		return null;
	}
	
	private Runway runwayById(long runwayId) {
		List<Runway> runways = getFreeRunways();
		Iterator<Runway> runwayIter = runways.iterator();
		Runway runway = new Runway();
		while(runwayIter.hasNext()) {
			runway = runwayIter.next();
			if(runway.getId() == runwayId) 
			{
				return runway;
			}
		}
		return null;
	}
	
	private ParkingPosition parkingPositionById(long pId) {
		List<ParkingPosition> parkingPositions = getFreeParkingPositions();
		Iterator<ParkingPosition> parkingPositionIter = parkingPositions.iterator();
		ParkingPosition parkingPosition = new ParkingPosition();
		while(parkingPositionIter.hasNext()) {
			parkingPosition = parkingPositionIter.next();
			if(parkingPosition.getId() == pId) 
			{
				return parkingPosition;
			}
		}
		return null;
	}
	
	private Runway runwayByPlane(long planeId) {
		List<Runway> runways = airportEJB.getRunways();
		Runway runway = new Runway();
		Iterator<Runway> runwayIter = runways.iterator();
		
		while (runwayIter.hasNext()) {
			runway = runwayIter.next();
			if(runway.getAirplane() != null && 
					runway.getAirplane().getId() == planeId)
				break;
		}
		
		return runway;
	}
	
	private ParkingPosition parkingPositionByPlane(long planeId) {
		List<ParkingPosition> parkingPositions = airportEJB.getParkingPositions();
		ParkingPosition parkingPosition = new ParkingPosition();
		Iterator<ParkingPosition> parkingPositionIter = parkingPositions.iterator();
		
		while (parkingPositionIter.hasNext()) {
			parkingPosition = parkingPositionIter.next();
			if(parkingPosition.getAirplane() != null && 
					parkingPosition.getAirplane().getId() == planeId)
				break;
		}
		
		return parkingPosition;
	}
	
	
	// -------------------------- Getters and Setters ------------------------------------
	
	//Airplane
	public Airplane getAirplane() {
		return airplane;
	}
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	public List<Airplane> getAirplanes() {
		return airportEJB.getAirplanes();
	}
	public List<Airplane> getAirplaneByStatus(String status) {
		return airportEJB.getAirplaneByStatus(Status.valueOf(status));
	}
	
	//Airline
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}	
	public List<Airline> getAirlines() {
		return airportEJB.getAirlines();
	}	
	
	//Runway
	public Runway getRunway() {
		return runway;
	}
	public void setRunway(Runway runway) {
		this.runway = runway;
	}
	public List<Runway> getRunways() {
		return airportEJB.getRunways();
	}	
	public List<Runway> getFreeRunways() {
		return airportEJB.getFreeRunways();
	}
	
	//ParkingPosition	
	public ParkingPosition getParkingPosition() {
		return parkingPosition;
	}
	public void setParkingPosition(ParkingPosition parkingPosition) {
		this.parkingPosition = parkingPosition;
	}
	public List<ParkingPosition> getParkingPositions() {
		return airportEJB.getParkingPositions();
	}	
	public List<ParkingPosition> getFreeParkingPositions() {
		return airportEJB.getFreeParkingPositions();
	}
	
	//AirplaneArrivalLog	
	public AirplaneArrivalLog getAirplaneArrivalLog() {
		return airplaneArrivalLog;
	}
	public void setAirplaneArrivalLog(AirplaneArrivalLog airplaneArrivalLog) {
		this.airplaneArrivalLog = airplaneArrivalLog;
	}
	public List<AirplaneArrivalLog> getAirplaneArrivalLogs() {
		return airportEJB.getAirplaneArrivalLogs();
	}
	
	
	//SelectOneMenu
	public long getSelectedParkingAirplane() {
		return selectedParkingAirplane;
	}
	public void setSelectedParkingAirplane(long selectedParkingAirplane) {
		this.selectedParkingAirplane = selectedParkingAirplane;
	}

	public long getSelectedAirplane() {
		return selectedAirplane;
	}
	public void setSelectedAirplane(long selectedAirplane) {
		this.selectedAirplane = selectedAirplane;
	}
	
	public long getSelectedRunway() {
		return selectedRunway;
	}
	public void setSelectedRunway(long selectedRunway) {
		this.selectedRunway = selectedRunway;
	}

	public long getSelectedParkingPosition() {
		return selectedParkingPosition;
	}
	public void setSelectedParkingPosition(long selectedParkingPosition) {
		this.selectedParkingPosition = selectedParkingPosition;
	}

	public long getLandingAirplane() {
		return landingAirplane;
	}
	public void setLandingAirplane(long landingAirplane) {
		this.landingAirplane = landingAirplane;
	}

	public long getParkingAirplane() {
		return parkingAirplane;
	}
	public void setParkingAirplane(long parkingAirplane) {
		this.parkingAirplane = parkingAirplane;
	}

	public long getStartingAirplane() {
		return startingAirplane;
	}
	public void setStartingAirplane(long startingAirplane) {
		this.startingAirplane = startingAirplane;
	}

	public String getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}
	public void setEstimatedArrivalTime(String estimatedArrivalTime) {
		this.estimatedArrivalTime = estimatedArrivalTime;
	}

	public String getRealArrivalTime() {
		return realArrivalTime;
	}
	public void setRealArrivalTime(String realArrivalTime) {
		this.realArrivalTime = realArrivalTime;
	}

	public String getEstimatedArrivalTimeStart() {
		return estimatedArrivalTimeStart;
	}
	public void setEstimatedArrivalTimeStart(String estimatedArrivalTimeStart) {
		this.estimatedArrivalTimeStart = estimatedArrivalTimeStart;
	}	
}
