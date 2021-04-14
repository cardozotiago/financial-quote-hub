/**
 * 
 */
package br.com.pagseguro.financialquotehub.dto;

import java.util.List;

/**
 * @author tiago
 * @date 2021-04-13
 */
public class SymbolWrapperDTO {

	private List<QuotesDTO> quotes;

	/**
	 * @return the quotes
	 */
	public List<QuotesDTO> getQuotes() {
		return quotes;
	}

	/**
	 * @param quotes the quotes to set
	 */
	public void setQuotes(List<QuotesDTO> quotes) {
		this.quotes = quotes;
	}

}
