package com.airport.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.airport.model.Airline;
import com.airport.model.Airplane;
import com.airport.model.Airplane.Status;
import com.airport.model.AirplaneArrivalLog;
import com.airport.model.ParkingPosition;
import com.airport.model.Runway;


@Stateless
public class AirportEJB {

	@PersistenceContext(unitName="airport")
	private EntityManager entityManager;
	
	// -------------------------- Airplanes  ------------------------------------
	public List<Airplane> getAirplanes() {
		Query query = entityManager.createNamedQuery("airplane.findAll");
		
		@SuppressWarnings("unchecked")
		List<Airplane> airplanes = query.getResultList();
		return airplanes;
	}

	public void store(Airplane airplane) {
		if (airplane.getId() == 0) {
			entityManager.persist(airplane);
		} else {
			entityManager.merge(airplane);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Airplane> getAirplaneByStatus(Status status) {
		Query query = entityManager.createNamedQuery("airplane.findByStatus").setParameter("status", status);
		return query.getResultList();	
	}
		
	// -------------------------- Runways ------------------------------------
	@SuppressWarnings("unchecked")
	public List<Runway> getRunways() {
		Query query = entityManager.createNamedQuery("runway.findAll");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Runway> getFreeRunways() {
		Query query = entityManager.createNamedQuery("runway.findFree");
		return query.getResultList();	
	}
	
	
	public void lockRunway(Runway runway) {
		runway.setIsLocked(true);
		entityManager.persist(runway);
	}
	
	public void unlockRunway(Runway runway) {
		runway.setIsLocked(false);
		entityManager.persist(runway);
	}
	
	public void storeRunway(Runway runway) {
		if (runway.getId() == 0) {
			entityManager.persist(runway);
		} else {
			entityManager.merge(runway);
		}
	}
	
	
	// -------------------------- Parking Positions ------------------------------------
	@SuppressWarnings("unchecked")
	public List<ParkingPosition> getParkingPositions() {
		Query query = entityManager.createNamedQuery("parkingposition.findAll");
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ParkingPosition> getFreeParkingPositions() {
		Query query = entityManager.createNamedQuery("parkingposition.findFree");
		return query.getResultList();	
	}
	
	public void lockParkingPosition(ParkingPosition parkingPosition) {
		parkingPosition.setIsLocked(true);
		entityManager.persist(parkingPosition);
	}
	
	public void unlockParkingPosition(ParkingPosition parkingPosition) {
		parkingPosition.setIsLocked(false);
		entityManager.persist(parkingPosition);
	}
	
	public void storeParkingPosition(ParkingPosition parkingPosition) {
		if (parkingPosition.getId() == 0) {
			entityManager.persist(parkingPosition);
		} else {
			entityManager.merge(parkingPosition);
		}
	}
	
	public Runway getRunway(long selectedRunway) {
		return entityManager.find(Runway.class, selectedRunway);
	}
	
	// -------------------------- Airline ------------------------------------
	
	public List<Airline> getAirlines() {
		Query query = entityManager.createNamedQuery("airline.findAll");
		
		@SuppressWarnings("unchecked")
		List<Airline> airlines = query.getResultList();
		return airlines;
	}

	public void storeAirline(Airline airline) {
		if (airline.getId() == 0) {
			entityManager.persist(airline);
		} else {
			entityManager.merge(airline);
		}
	}	
	
	// -------------------------- Airplane Arrival Log ------------------------------------
	
	public List<AirplaneArrivalLog> getAirplaneArrivalLogs() {
		Query query = entityManager.createNamedQuery("airplanearrivallog.findAll");
		
		@SuppressWarnings("unchecked")
		List<AirplaneArrivalLog> aals = query.getResultList();
		return aals;
	}

	public void storeAirplaneArrivalLog(AirplaneArrivalLog aal) {
		if (aal.getId() == 0) {
			entityManager.persist(aal);
		} else {
			entityManager.merge(aal);
		}
	}
}
