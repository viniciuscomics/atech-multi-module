package com.atech.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_pilot")
public class Pilot{

	@Id
	@SequenceGenerator(name= "seq_pilot_id", sequenceName = "seq_pilot_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pilot_id")
	@ApiModelProperty(value = "Code of pilot registred", example = "2")
	private Long id;
	
	@NotNull
	@Size(min=3,max=50)
	@ApiModelProperty(value = "Name of pilot", example = "Jose Silva")
	private String name;
	
	@NotNull
	@ApiModelProperty(value = "Age of pilot", example = "50")
	private int age;
	
	@NotNull
	@Size(min=6,max=14)
	@ApiModelProperty(value = "Document of pilot", example = "12365478")
	private String document;
	
}
