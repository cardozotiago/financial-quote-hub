/**
 * 
 */
package br.com.pagseguro.financialquotehub.dto;

/**
 * @author tiago
 * @date 2021-04-13
 */
public class QuoteWrapperDTO {

	private QuoteResponseDTO quoteResponse;

	/**
	 * @return the quoteResponse
	 */
	public QuoteResponseDTO getQuoteResponse() {
		return quoteResponse;
	}

	/**
	 * @param quoteResponse the quoteResponse to set
	 */
	public void setQuoteResponse(QuoteResponseDTO quoteResponse) {
		this.quoteResponse = quoteResponse;
	}

}
