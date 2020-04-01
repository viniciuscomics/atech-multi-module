package com.atech.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
@Table(name = "tbl_user")
public class User {

	@Id
	@SequenceGenerator(name= "seq_user_id", sequenceName = "seq_user_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_id")
	private Long id;
	
	@NotNull
	@Size(min=6,max=50)
	private String username;
	
	@NotNull
	@Size(min=3,max=50)
	private String email;
	
	@NotNull
	@Size(min=6,max=12)
	private String password;
	
	private boolean active;
	
	@ManyToMany
    @JoinTable(
            name = "tbl_user_authority",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private Set<Authority> authorities;

}
