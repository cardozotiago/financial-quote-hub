/**
 * 
 */
package br.com.pagseguro.financialquotehub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pagseguro.financialquotehub.models.Stock;

/**
 * @author tiago
 * @date 2021-04-12
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	@Query("SELECT s FROM Stock s WHERE s.code = :code")
	Stock getByCode(@Param("code") String code);

	@Query("SELECT s FROM Stock s WHERE s.name = :name")
	Stock getByName(@Param("name") String name);

	@Query(value = "SELECT * FROM STOCK ORDER BY short_ratio DESC LIMIT 10", nativeQuery = true)
	List<Stock> getTopTenStocks();

}
