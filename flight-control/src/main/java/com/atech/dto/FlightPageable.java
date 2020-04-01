package com.atech.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.atech.model.Flight;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightPageable extends RepresentationModel<FlightPageable>{

	private List<Flight> listFlight;
	private Integer pageNumber;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;    
    
}
