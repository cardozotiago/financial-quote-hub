package br.com.pagseguro.financialquotehub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import br.com.pagseguro.financialquotehub.controller.FinancialQuoteHubController;
import br.com.pagseguro.financialquotehub.dto.StockRequestDTO;
import br.com.pagseguro.financialquotehub.dto.StockResponseDTO;
import br.com.pagseguro.financialquotehub.mapper.DataMapper;
import br.com.pagseguro.financialquotehub.models.Stock;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
class FinancialQuoteHubApplicationTests {

	@Autowired
	FinancialQuoteHubController financialQuoteHubController;

	@Autowired
	DataMapper dataMapper;

	@Test
	public void createStock() {
		StockRequestDTO stockDTO = new StockRequestDTO();
		stockDTO.setCode("AB58");
		stockDTO.setName("Name AB58");
		stockDTO.setFloatShares(45789522l);
		stockDTO.setShortRatio(new BigDecimal(17.1));
		ResponseEntity<StockResponseDTO> resp = financialQuoteHubController.createStock(stockDTO);
		assertThat(resp.getStatusCode().equals(HttpStatus.CREATED));
	}

	@Test
	public void deleteStock() {
		String code = "AB58";
		ResponseEntity<StockResponseDTO> resp = financialQuoteHubController.deleteStock(code);
		assertThat(resp.getStatusCode().equals(HttpStatus.OK));
	}

	@Test
	public void findAllStock() {
		ResponseEntity<List<Stock>> allStocks = financialQuoteHubController.getAllStocks();
		List<HttpStatus> codes = new ArrayList<HttpStatus>();
		codes.add(HttpStatus.OK);
		codes.add(HttpStatus.NOT_FOUND);
		assertTrue(codes.contains(allStocks.getStatusCode()));
	}
	
	@Test
	public void getStockByCode() {
		String code = "BBSE3";
		List<HttpStatus> codes = new ArrayList<HttpStatus>();
		codes.add(HttpStatus.FOUND);
		codes.add(HttpStatus.NOT_FOUND);
		ResponseEntity<Stock> stock = financialQuoteHubController.getStockByCode(code);
		assertTrue(codes.contains(stock.getStatusCode()));
	}
	
	@Test
	public void getStockByName() {
		String name = "BRADESCO ON EJ N1";
		List<HttpStatus> codes = new ArrayList<HttpStatus>();
		codes.add(HttpStatus.FOUND);
		codes.add(HttpStatus.NOT_FOUND);
		ResponseEntity<Stock> stock = financialQuoteHubController.getStockByCode(name);
		assertTrue(codes.contains(stock.getStatusCode()));
	}

}
