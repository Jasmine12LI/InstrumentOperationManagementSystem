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
@Table(name = "role")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	/**
	 * 角色名字
	 */
	@Column
	private String name;

	/**
	 * 角色状态：有效（1）或无效（0）
	 */
	@Column
	private Boolean status;
	
//	@ManyToMany(mappedBy = "roles")
//	@JsonBackReference(value = "role.users")
//	Set<User> users = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonBackReference(value = "role.accesses")
	@JoinTable(name = "role_access", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "access_id"))
	private Set<Access> accesses = new HashSet<>();

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				", status=" + status +
				'}';
	}
}