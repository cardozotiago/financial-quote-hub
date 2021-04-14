/**
 * 
 */
package br.com.pagseguro.financialquotehub.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import br.com.pagseguro.financialquotehub.models.Stock;
import br.com.pagseguro.financialquotehub.repositories.StockRepository;

/**
 * @author tiago
 * @date 2021-04-12
 */
@Service
@Configurable
public class StockService {

	@Autowired
	StockRepository stockRepository;

	@Transactional
	public Stock save(Stock stock) {
		Stock stRepo = stockRepository.getByCode(stock.getCode());
		if (stRepo == null) {
			Stock obj = stockRepository.save(stock);
			if (obj != null) {
				return obj;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public Stock getByCode(String code) {
		return stockRepository.getByCode(code);
	}

	public Stock getByName(String name) {
		return stockRepository.getByName(name);
	}

	public List<Stock> getTopTenStocks() {
		return stockRepository.getTopTenStocks();
	}

	public void delete(Stock stock) {
		stockRepository.delete(stock);
	}

	public Iterable<Stock> findAll() {
		return stockRepository.findAll();
	}

}
