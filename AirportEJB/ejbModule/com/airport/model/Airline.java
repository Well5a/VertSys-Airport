package com.airport.model;

import java.io.Serializable;
import javax.persistence.*;
@NamedQueries({
	@NamedQuery(name="airline.findAll", query="select a from Airline a order by a.name"),
	@NamedQuery(name="airline.findByName", query="select a from Airline a where a.name = :name")
})
/**
 * Entity implementation class for Entity: Airline
 *
 */
@Entity
public class Airline implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long Id;
	private String name;

	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Airline() {
		super();
	}
	
	public Airline(long Id, String name) {
		super();
		this.Id = Id;
		this.name = name;
	}
}
