package com.atech.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_flight")
public class Flight {	

	@Id
	@SequenceGenerator(name= "seq_flight_id", sequenceName = "seq_flight_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flight_id")
	@ApiModelProperty(value = "code of flight registred", example = "123")
	private Long id;
	
	@NotNull
	@ApiModelProperty(value = "Travel date", example = "2020-04-02 19:10:25-07")
	private LocalDateTime departure;
	
	@NotNull
	@ApiModelProperty(value = "Arrival date", example = "2020-04-03 19:10:25-07")
	private LocalDateTime arrive;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_airplane")
	@ApiModelProperty(value = "Code of airplane registred", example = "1")
	private Airplane airplane;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_pilot")
	@ApiModelProperty(value = "Code of pilot registred", example = "2")
	private Pilot pilot;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_city_source")
	@ApiModelProperty(value = "Code of city source", example = "2")
	private City citySource;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "id_city_destiny")
	@ApiModelProperty(value = "Code of city destination", example = "3")
	private City cityDestiny;
	
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status_flight")
	@ApiModelProperty(value = "Status of flight", example = "0=CANCELED,1=READY, 2=IN FLIGHT, 3=FINISH")
	private StatusFlight status;
	
}
