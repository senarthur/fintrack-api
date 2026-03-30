package com.arthursena.fin_track.model.dto;

import java.util.List;

public record PageTransactionResponse(
    List<TransactionResponse> transactions,
    int pageNumber,
    int pageSize,
    long totalElements
) {
    
    public PageTransactionResponse(List<TransactionResponse> transactions, int pageNumber, int pageSize, long totalElements) {
        this.transactions = transactions;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
}
