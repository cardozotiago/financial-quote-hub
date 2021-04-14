/**
 * 
 */
package br.com.pagseguro.financialquotehub.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tiago
 * @date 2021-04-12
 */
@Entity
@Table(name = "STOCK")
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "floatShares")
	private Long floatShares;

	@Column(name = "shortRatio")
	private BigDecimal shortRatio;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the floatShares
	 */
	public Long getFloatShares() {
		return floatShares;
	}

	/**
	 * @param floatShares the floatShares to set
	 */
	public void setFloatShares(Long floatShares) {
		this.floatShares = floatShares;
	}

	/**
	 * @return the shortRatio
	 */
	public BigDecimal getShortRatio() {
		return shortRatio;
	}

	/**
	 * @param shortRatio the shortRatio to set
	 */
	public void setShortRatio(BigDecimal shortRatio) {
		this.shortRatio = shortRatio;
	}

}
