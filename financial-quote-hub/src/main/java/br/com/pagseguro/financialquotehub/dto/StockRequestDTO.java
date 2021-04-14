/**
 * 
 */
package br.com.pagseguro.financialquotehub.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author tiago
 * @date 2021-04-13
 */
public class StockRequestDTO {

	@NotEmpty(message = "Code is mandatory")
	private String code;

	@NotEmpty(message = "Name is mandatory")
	private String name;

	@NotNull(message = "Float Shares is mandatory")
	private Long floatShares;

	@NotNull(message = "Short Ratio is mandatory")
	private BigDecimal shortRatio;

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
