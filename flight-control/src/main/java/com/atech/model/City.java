package com.atech.model;

import javax.persistence.Column;
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
@Table(name = "tbl_city")
public class City{	

	@Id
	@SequenceGenerator(name= "seq_city_id", sequenceName = "seq_city_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city_id")
	@ApiModelProperty(value = "Code of city registred", example = "4")
	private Long id;
	
	@NotNull
	@Size(min=3,max=50)
	@ApiModelProperty(value = "Name of city", example = "Campinas-SP")
	private String name;
	
	@NotNull
	@Size(min=3,max=30)
	@Column(name = "name_country")
	@ApiModelProperty(value = "Name of country", example = "Brasil")
	private String nameCountry;
}
