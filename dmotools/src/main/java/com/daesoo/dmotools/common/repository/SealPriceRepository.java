package com.daesoo.dmotools.common.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daesoo.dmotools.common.dto.ServerType;
import com.daesoo.dmotools.common.entity.Seal;
import com.daesoo.dmotools.common.entity.SealPrice;

public interface SealPriceRepository extends JpaRepository<SealPrice, Long>{
	
	List<SealPrice> findByPriceBetween(int minPrice, int maxPrice);

	List<SealPrice> findBySealAndPriceBetween(Seal seal, int minPrice, int maxPrice);
    
//    @Query(value = "SELECT sp.* FROM seal_price sp " +
//            "INNER JOIN (SELECT seal_id, MAX(modified_at) AS max_modified_at FROM seal_price GROUP BY seal_id) grouped_sp " +
//            "ON sp.seal_id = grouped_sp.seal_id AND sp.modified_at = grouped_sp.max_modified_at",
//    nativeQuery = true)
    
//    @Query(value = "SELECT sp.* FROM seal_price sp " +
//            "INNER JOIN (SELECT seal_id, MAX(modified_at) AS max_modified_at FROM seal_price GROUP BY seal_id) grouped_sp " +
//            "ON sp.seal_id = grouped_sp.seal_id AND sp.modified_at = grouped_sp.max_modified_at " +
//            "ORDER BY sp.seal_id ASC",
//    nativeQuery = true)
//    List<SealPrice> findLatestSealPrices(@Param("serverType") ServerType serverType);
	
	@Query(value = "SELECT * FROM (" +
            "    SELECT sp.*, " +
            "           ROW_NUMBER() OVER (PARTITION BY sp.seal_id ORDER BY sp.modified_at DESC) AS rn " +
            "    FROM seal_price sp " +
            "    WHERE sp.server_type = :serverType" +
            ") sp " +
            "WHERE sp.rn = 1",
    nativeQuery = true)
	List<SealPrice> findLatestSealPricesByServerType(@Param("serverType") String serverType);
    
//    @Query(value = "SELECT sp.* FROM seal_price sp " +
//            "INNER JOIN (" +
//            "    SELECT seal_id, MAX(modified_at) AS max_modified_at " +
//            "    FROM seal_price " +
//            "    WHERE (seal_id, reg_count) IN (" +
//            "        SELECT seal_id, MAX(reg_count) " +
//            "        FROM seal_price " +
//            "        GROUP BY seal_id" +
//            "    ) " +
//            "    GROUP BY seal_id, reg_count" +
//            ") grouped_sp " +
//            "ON sp.seal_id = grouped_sp.seal_id AND sp.modified_at = grouped_sp.max_modified_at " +
//            "ORDER BY sp.seal_id, sp.reg_count DESC, sp.modified_at DESC",
//    nativeQuery = true)
    
//    @Query(value = "SELECT sp.* FROM seal_price sp " +
//            "INNER JOIN (" +
//            "    SELECT seal_id, MAX(modified_at) AS max_modified_at " +
//            "    FROM seal_price " +
//            "    WHERE (seal_id, reg_count) IN (" +
//            "        SELECT seal_id, MAX(reg_count) " +
//            "        FROM seal_price " +
//            "        GROUP BY seal_id" +
//            "    ) " +
//            "    AND modified_at >= NOW() - INTERVAL 3 DAY " +
//            "    GROUP BY seal_id, reg_count" +
//            ") grouped_sp " +
//            "ON sp.seal_id = grouped_sp.seal_id AND sp.modified_at = grouped_sp.max_modified_at " +
//            "ORDER BY sp.seal_id, sp.reg_count DESC, sp.modified_at DESC",
//    nativeQuery = true)
    
    @Query(value = "WITH RankedSealPrices AS (" +
            "    SELECT sp.*, " +
            "           ROW_NUMBER() OVER (" +
            "               PARTITION BY sp.seal_id " +
            "               ORDER BY sp.reg_count DESC, sp.modified_at DESC" +
            "           ) AS rn " +
            "    FROM seal_price sp " +
            "    WHERE sp.modified_at >= NOW() - INTERVAL 3 DAY" +
            ") " +
            "SELECT * " +
            "FROM RankedSealPrices " +
            "WHERE rn = 1 " +
            "ORDER BY seal_id ASC",
    nativeQuery = true)
    List<SealPrice> findLatestSealPricesOrderedByRegCount();

	Page<SealPrice> findAllBySeal(Pageable pageable, Seal seal);
    
    
}
