package com.convergent.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author susanta
 */
@Entity
@Table(name = "UserRole")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long UserRoleId;

	private Long userId;

	private Long roleTypeId;

	public Long getUserRoleId() {
		return UserRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		UserRoleId = userRoleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserRole [UserRoleId=" + UserRoleId + ", roleTypeId=" + roleTypeId + ", userId=" + userId + "]";
	}

}
