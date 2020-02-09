package com.scsse.workflow.entity.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "access")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Access {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column
	private String url;

	@Column
	private String title;

	@ManyToMany(mappedBy = "accesses")
	@JsonBackReference(value = "access.roles")
	Set<Role> roles = new HashSet<>();
}