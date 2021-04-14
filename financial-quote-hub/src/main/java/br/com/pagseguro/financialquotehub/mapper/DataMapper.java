package br.com.pagseguro.financialquotehub.mapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pagseguro.financialquotehub.dto.QuoteWrapperDTO;
import br.com.pagseguro.financialquotehub.dto.SymbolWrapperDTO;
import br.com.pagseguro.financialquotehub.models.Stock;
import br.com.pagseguro.financialquotehub.service.StockService;
import br.com.pagseguro.financialquotehub.utils.ValidationUtils;

/**
 * 
 */

/**
 * @author tiago
 * @date 2021-04-12
 */
@Component
public class DataMapper {

	@Autowired
	StockService stockService;

	static final String FILE_PATH = "config.properties";

	public void doMapper() {

		String filePath = getFilePath();

		if (filePath != null) {
			try {
				fillStocksData(filePath);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error to mapper data");
			}
		} else {
			System.out.println("Sorry, unable to find config.properties");
		}
	}

	/**
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void fillStocksData(String filePath) throws FileNotFoundException, IOException {
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(filePath));
		String line = reader.readLine();
		while (line != null) {
			String[] data = line.split(";");
			if (isValidData(data)) {
				Stock stock = new Stock();
				stock.setCode(data[0]);
				stock.setName(data[1]);
				stock.setFloatShares(Long.valueOf(data[2]));
				stock.setShortRatio(new BigDecimal(data[3]));
				stockService.save(stock);
			}
			line = reader.readLine();
		}
		reader.close();
	}

	public Boolean isValidData(String[] data) {
		Boolean valid = false;
		if (data == null || data.length != 4) {
			return valid;
		} else {
			if (ValidationUtils.isNotContainsSpecialCharacter(data[0])) {
				valid = true;
			} else {
				return false;
			}
			if (ValidationUtils.isNumeric(data[2])) {
				valid = true;
			} else {
				return false;
			}
			if (ValidationUtils.isNumeric(data[3])) {
				valid = true;
			} else {
				return valid;
			}
		}
		return true;
	}

	/**
	 * 
	 */
	private static String getFilePath() {
		try (InputStream input = DataMapper.class.getClassLoader().getResourceAsStream(FILE_PATH)) {

			Properties prop = new Properties();

			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return null;
			}

			prop.load(input);

			return prop.getProperty("file.path");

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public <S, D> D mapTo(S source, Class<D> destClass) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(source, destClass);
	}

	public <S, D> List<D> toList(List<S> source, Type destClass) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(source, destClass);
	}

	public SymbolWrapperDTO jsonToSymbolWrapper(String data) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		String jsonInString = data;
		try {
			return mapper.readValue(jsonInString, SymbolWrapperDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println("Error on convert to SymbolWrapper");
			return null;
		}
	}

	public QuoteWrapperDTO jsonToQuoteWrapper(String data) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		String jsonInString = data;
		try {
			return mapper.readValue(jsonInString, QuoteWrapperDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println("Error on convert to QuoteWrapper");
			return null;
		}
	}

}
