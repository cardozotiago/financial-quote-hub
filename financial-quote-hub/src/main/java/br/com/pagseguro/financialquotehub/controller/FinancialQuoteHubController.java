/**
 * 
 */
package br.com.pagseguro.financialquotehub.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pagseguro.financialquotehub.dto.QuoteResponseDTO;
import br.com.pagseguro.financialquotehub.dto.QuoteWrapperDTO;
import br.com.pagseguro.financialquotehub.dto.ResultDTO;
import br.com.pagseguro.financialquotehub.dto.StockRequestDTO;
import br.com.pagseguro.financialquotehub.dto.StockResponseDTO;
import br.com.pagseguro.financialquotehub.dto.SymbolWrapperDTO;
import br.com.pagseguro.financialquotehub.mapper.DataMapper;
import br.com.pagseguro.financialquotehub.models.Stock;
import br.com.pagseguro.financialquotehub.service.StockService;

/**
 * @author tiago
 * @date 2021-04-12
 */
@RestController
@Validated
public class FinancialQuoteHubController {

	@Autowired
	StockService stockService; 

	@Autowired
	DataMapper dataMapper;

	@PostConstruct
	public void initializeDataMapperFile() {
		dataMapper.doMapper();
	}

	@GetMapping("/stocks")
	public ResponseEntity<List<Stock>> getAllStocks() {
		Iterable<Stock> stocksList = stockService.findAll();

		if (stocksList == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Stock>>((List<Stock>) stocksList, HttpStatus.OK);
		}
	}

	@GetMapping("/stocks/code/{code}")
	public ResponseEntity<Stock> getStockByCode(@PathVariable(value = "code") String code) {
		Stock stock = stockService.getByCode(code);
		if (stock == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Stock>(stock, HttpStatus.FOUND);
		}
	}

	@GetMapping("/stocks/name/{name}")
	public ResponseEntity<Stock> getStockByName(@PathVariable(value = "name") String name) {
		Stock stock = stockService.getByName(name);
		if (stock == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Stock>(stock, HttpStatus.FOUND);
		}
	}

	@GetMapping("/stocks/quotes")
	public QuoteWrapperDTO getTopThenStocks() throws IOException, InterruptedException {
		List<Stock> stocksList = stockService.getTopTenStocks();
		if (stocksList == null) {
			return null;
		} else {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < stocksList.size(); i++) {
				SymbolWrapperDTO symbol = getSymbolExact(stocksList.get(i).getCode());
				if (symbol != null && symbol.getQuotes() != null && !symbol.getQuotes().isEmpty()) {
					builder.append(symbol.getQuotes().get(0).getSymbol());
					if (i <= stocksList.size() - 2) {
						builder.append("%2C");
					}
				}
			}
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(
							"https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=BR&symbols="
									+ builder.toString()))
					.header("x-rapidapi-key", "fa048d7ddcmshe2da4a53307fe09p11e2eajsna0bf65d0292a")
					.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
					.method("GET", HttpRequest.BodyPublishers.noBody()).build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			QuoteWrapperDTO quoteWrapper = dataMapper.jsonToQuoteWrapper(response.body());
			QuoteResponseDTO quoteResponse = new QuoteResponseDTO();
			List<ResultDTO> resultsList = new ArrayList<ResultDTO>();
			if (quoteWrapper != null && quoteWrapper.getQuoteResponse() != null
					&& quoteWrapper.getQuoteResponse().getResult() != null
					&& !quoteWrapper.getQuoteResponse().getResult().isEmpty()) {
				ResultDTO rt;
				for (ResultDTO r : quoteWrapper.getQuoteResponse().getResult()) {
					rt = new ResultDTO();
					rt.setLongName(r.getLongName());
					rt.setSymbol(r.getSymbol());
					rt.setRegularMarketPrice(r.getRegularMarketPrice());
					resultsList.add(rt);
				}
				quoteWrapper = new QuoteWrapperDTO();
				quoteResponse.setResult(resultsList);
				quoteWrapper.setQuoteResponse(quoteResponse);
			}
			return quoteWrapper;
		}
	}

	public SymbolWrapperDTO getSymbolExact(String name) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(
						"https://apidojo-yahoo-finance-v1.p.rapidapi.com/auto-complete?q=" + name + "&region=US"))
				.header("x-rapidapi-key", "fa048d7ddcmshe2da4a53307fe09p11e2eajsna0bf65d0292a")
				.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		return dataMapper.jsonToSymbolWrapper(response.body());
	}

	@PostMapping("/stocks")
	public ResponseEntity<StockResponseDTO> createStock(@Valid @RequestBody StockRequestDTO stockRequestDTO) {
		Stock entity = dataMapper.mapTo(stockRequestDTO, Stock.class);
		Stock foundObj = stockService.getByCode(entity.getCode());
		if (foundObj != null) {
			return new ResponseEntity<StockResponseDTO>(new StockResponseDTO(), HttpStatus.FOUND);
		}
		Stock savedObj = stockService.save(entity);
		StockResponseDTO savedDto = dataMapper.mapTo(entity, StockResponseDTO.class);
		if (savedObj != null) {
			return new ResponseEntity<StockResponseDTO>(savedDto, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<StockResponseDTO>(new StockResponseDTO(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/stocks/{code}")
	public ResponseEntity<StockResponseDTO> deleteStock(@PathVariable(value = "code") String code) {
		Stock foundObj = stockService.getByCode(code);
		if (foundObj != null) {
			stockService.delete(foundObj);
			foundObj = stockService.getByCode(code);
		}

		if (foundObj != null) {
			StockResponseDTO dto = dataMapper.mapTo(foundObj, StockResponseDTO.class);
			return new ResponseEntity<StockResponseDTO>(dto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<StockResponseDTO>(new StockResponseDTO(), HttpStatus.OK);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
