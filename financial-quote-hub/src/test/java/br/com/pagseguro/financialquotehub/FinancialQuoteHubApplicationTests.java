package br.com.pagseguro.financialquotehub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import br.com.pagseguro.financialquotehub.controller.FinancialQuoteHubController;
import br.com.pagseguro.financialquotehub.dto.StockRequestDTO;
import br.com.pagseguro.financialquotehub.mapper.DataMapper;
import br.com.pagseguro.financialquotehub.models.Stock;
import br.com.pagseguro.financialquotehub.service.StockService;

@SpringBootTest
class FinancialQuoteHubApplicationTests {

	@InjectMocks
	FinancialQuoteHubController financialQuoteHubController;

	@Mock
	StockService stockService;

	@Spy
	DataMapper dataMapper;

	@Test
	public void testcreateStock() {

		StockRequestDTO stockDTO = new StockRequestDTO();
		stockDTO.setCode("AB58");
		stockDTO.setName("Name AB58");
		stockDTO.setFloatShares(45789522l);
		stockDTO.setShortRatio(new BigDecimal(17.1));
		Stock entity = dataMapper.mapTo(stockDTO, Stock.class);
		assertThat(entity.getCode().equals(stockDTO.getCode()));
		assertThat(entity.getName().equals(stockDTO.getName()));
		assertThat(entity.getFloatShares() == stockDTO.getFloatShares());

	}

	@Test
	public void testFindAll() {
		Stock stock1 = new Stock();
		stock1.setCode("BA25");
		stock1.setName("Name BA25");
		stock1.setFloatShares(12558402l);
		stock1.setShortRatio(new BigDecimal(12.5));

		Stock stock2 = new Stock();
		stock2.setCode("AB58");
		stock2.setName("Name AB58");
		stock2.setFloatShares(45789522l);
		stock2.setShortRatio(new BigDecimal(17.1));

		Stock stock3 = new Stock();
		stock3.setCode("CHJ897");
		stock3.setName("Name CHJ894 S/A");
		stock3.setFloatShares(1258743l);
		stock3.setShortRatio(new BigDecimal(8.4));

		List<Stock> list = new ArrayList<Stock>();
		list.addAll(Arrays.asList(stock1, stock2, stock3));

		when(stockService.findAll()).thenReturn(list);

		ResponseEntity<List<Stock>> allStocks = financialQuoteHubController.getAllStocks();

		assertThat(allStocks.getBody().size()).isEqualTo(3);

		assertThat(allStocks.getBody().get(0).getCode()).isEqualTo(stock1.getCode());

		assertThat(allStocks.getBody().get(1).getCode()).isEqualTo(stock2.getCode());

		assertThat(allStocks.getBody().get(2).getCode()).isEqualTo(stock3.getCode());
	}

}
