package com.atech.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.atech.model.Flight;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Flights Paginator")
public class FlightPageable extends RepresentationModel<FlightPageable>{	

	@ApiModelProperty(value = "List of flights registred")
	private List<Flight> listFlight;	

	@ApiModelProperty(value = "Current page", example = "1")
	private Integer pageNumber;	
	
	@ApiModelProperty(value = "Total elements on the page", example = "5")
    private Integer pageSize;	
	
	@ApiModelProperty(value = "Total elements", example = "15")
    private Integer totalCount;	
	
	@ApiModelProperty(value = "Total page", example = "3")
    private Integer totalPage;    
    
}
