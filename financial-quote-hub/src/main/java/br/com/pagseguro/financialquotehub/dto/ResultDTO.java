/**
 * 
 */
package br.com.pagseguro.financialquotehub.dto;

/**
 * @author tiago
 * @date 2021-04-13
 */
public class ResultDTO {

	private Double regularMarketPrice;
	private String longName;
	private String symbol;

	/**
	 * @return the regularMarketPrice
	 */
	// @JsonIgnoreProperties(ignoreUnknown = true)
	public Double getRegularMarketPrice() {
		return regularMarketPrice;
	}

	/**
	 * @param regularMarketPrice the regularMarketPrice to set
	 */
	public void setRegularMarketPrice(Double regularMarketPrice) {
		this.regularMarketPrice = regularMarketPrice;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
