/**
 * 
 */
package br.com.pagseguro.financialquotehub.dto;

import java.util.List;

/**
 * @author tiago
 * @date 2021-04-13
 */
public class QuoteResponseDTO {

	private List<ResultDTO> result;

	/**
	 * @return the result
	 */
	public List<ResultDTO> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<ResultDTO> result) {
		this.result = result;
	}

}
