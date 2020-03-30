/**
 * 
 */
package com.fb.in.app.reporting.model.auth;

import java.io.Serializable;
import java.util.List;

/**
 * @author SKumar7
 *
 */
public class DataSecurityPayload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -759440647842383746L;

	private String server;
	private Boolean allMembers;
	private String elasticube;
	private String table;
	private String column;
	private String datatype;
	private List<Share> shares = null;
	private List<String> members = null;

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * @return the allMembers
	 */
	public Boolean getAllMembers() {
		return allMembers;
	}

	/**
	 * @param allMembers the allMembers to set
	 */
	public void setAllMembers(Boolean allMembers) {
		this.allMembers = allMembers;
	}

	/**
	 * @return the elasticube
	 */
	public String getElasticube() {
		return elasticube;
	}

	/**
	 * @param elasticube the elasticube to set
	 */
	public void setElasticube(String elasticube) {
		this.elasticube = elasticube;
	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return the datatype
	 */
	public String getDatatype() {
		return datatype;
	}

	/**
	 * @param datatype the datatype to set
	 */
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	/**
	 * @return the shares
	 */
	public List<Share> getShares() {
		return shares;
	}

	/**
	 * @param shares the shares to set
	 */
	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

	/**
	 * @return the members
	 */
	public List<String> getMembers() {
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(List<String> members) {
		this.members = members;
	}

}
