package com.training.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.training.entity.PurchaseEntity;

public interface ReportsDAO extends JpaRepository<PurchaseEntity, Integer> {

    @Query("SELECT p FROM PurchaseEntity p " +
           "WHERE p.vendorName = :vendorName " +
           "AND (:fromDate IS NULL OR p.purchaseDate >= :fromDate) " +
           "AND (:toDate IS NULL OR p.purchaseDate <= :toDate) " +
           "ORDER BY p.purchaseDate DESC, p.purchaseId DESC")
    List<PurchaseEntity> getVendorWisePurchaseDetails(
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("vendorName") String vendorName);

}