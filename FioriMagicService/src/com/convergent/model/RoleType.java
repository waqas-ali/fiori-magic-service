package com.convergent.model;

import java.io.Serializable;
import java.util.Date;

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
	@Table(name = "RoleType")
	public class RoleType implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Basic(optional = false)
		private Long roleTypeId;
		
		private String roleName;

		public Long getRoleTypeId() {
			return roleTypeId;
		}


		public void setRoleTypeId(Long roleTypeId) {
			this.roleTypeId = roleTypeId;
		}


		public String getRoleName() {
			return roleName;
		}


		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		@Override
		public String toString() {
			return "RoleType [roleName=" + roleName + ", roleTypeId=" + roleTypeId + "]";
		}
		
}
