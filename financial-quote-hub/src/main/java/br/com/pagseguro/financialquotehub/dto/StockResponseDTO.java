/**
 * 
 */
package br.com.pagseguro.financialquotehub.dto;

/**
 * @author tiago
 * @date 2021-04-13
 */
public class StockResponseDTO extends StockRequestDTO {

	private Long id;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
