package com.lab.service;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import com.lab.bean.Chemical;
import com.lab.bean.ChemicalTransaction;
import com.lab.dao.ChemicalDAO;
import com.lab.dao.ChemicalTransactionDAO;
import com.lab.util.ActiveStockExistException;
import com.lab.util.LowStockException;
import com.lab.util.ValidationException;
public class LabInventoryService {
    private ChemicalDAO chemicalDAO = new ChemicalDAO();
    private ChemicalTransactionDAO transactionDAO = new ChemicalTransactionDAO();
    public Chemical viewChemicalDetails(String chemicalID)
            throws ValidationException {
        if (chemicalID == null || chemicalID.trim().isEmpty()) {
            throw new ValidationException("Chemical ID cannot be empty.");
        }
        return chemicalDAO.findChemical(chemicalID);
    }
    public ArrayList<Chemical> viewAllChemicals() {
        return chemicalDAO.viewAllChemicals();
    }
    public boolean registerNewChemicalWithOpeningStock(
            Chemical chemical,
            BigDecimal openingQty,
            Date openingDate)
            throws ValidationException {
        if (chemical.getChemicalID() == null ||
                chemical.getChemicalID().trim().isEmpty()) {
            throw new ValidationException("Chemical ID cannot be empty.");
        }
        if (chemical.getChemicalName() == null ||
                chemical.getChemicalName().trim().isEmpty()) {
            throw new ValidationException("Chemical Name cannot be empty.");
        }
        if (chemical.getUnitOfMeasure() == null ||
                chemical.getUnitOfMeasure().trim().isEmpty()) {
            throw new ValidationException("Unit Of Measure cannot be empty.");
        }
        if (openingQty.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(
                    "Opening quantity must be greater than zero.");
        }
        if (openingDate == null) {
            throw new ValidationException("Opening date cannot be null.");
        }
        if (chemicalDAO.findChemical(chemical.getChemicalID()) != null) {
            throw new ValidationException(
                    "Chemical ID already exists.");
        }
        if (chemical.getStatus() == null ||
                chemical.getStatus().trim().isEmpty()) {
            chemical.setStatus("ACTIVE");
        }
        boolean inserted = chemicalDAO.insertChemical(chemical);
        if (!inserted) {
            return false;
        }
        ChemicalTransaction txn = new ChemicalTransaction();
        txn.setTransactionID(transactionDAO.generateTransactionID());
        txn.setChemicalID(chemical.getChemicalID());
        txn.setTransactionType("OPENING");
        txn.setTransactionDate(openingDate);
        txn.setQuantity(openingQty);
        txn.setIssuedToOrUsedBy(null);
        txn.setPurposeOrReference("Opening Stock");
        txn.setRunningBalanceHint(openingQty);
        txn.setRemarks("Initial Stock");
        return transactionDAO.insertTransaction(txn);
    }
    public boolean issueChemical(
            String chemicalID,
            BigDecimal issueQty,
            Date txnDate,
            String issuedTo,
            String purpose)
            throws ValidationException, LowStockException {
        if (chemicalID == null ||
                chemicalID.trim().isEmpty()) {
            throw new ValidationException(
                    "Chemical ID cannot be empty.");
        }
        if (issueQty == null ||
                issueQty.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(
                    "Issue quantity must be greater than zero.");
        }
        if (issuedTo == null ||
                issuedTo.trim().isEmpty()) {
            throw new ValidationException(
                    "Issued To cannot be empty.");
        }
        Chemical chemical = chemicalDAO.findChemical(chemicalID);
        if (chemical == null) {
            throw new ValidationException(
                    "Chemical not found.");
        }
        if (!chemical.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new ValidationException(
                    "Chemical is inactive.");
        }
        BigDecimal currentStock =
                transactionDAO.calculateCurrentStock(chemicalID);
        System.out.println("Current Stock : " + currentStock);
        if (currentStock.compareTo(issueQty) < 0) {
            throw new LowStockException("Insufficient stock.");
        }
        ChemicalTransaction txn = new ChemicalTransaction();
        txn.setTransactionID(transactionDAO.generateTransactionID());
        txn.setChemicalID(chemicalID);
        txn.setTransactionType("ISSUE");
        txn.setTransactionDate(txnDate);
        txn.setQuantity(issueQty);
        txn.setIssuedToOrUsedBy(issuedTo);
        txn.setPurposeOrReference(purpose);
        txn.setRunningBalanceHint(currentStock.subtract(issueQty));
        txn.setRemarks("Chemical Issued");
        return transactionDAO.insertTransaction(txn);
    }
    public boolean returnOrAdjustChemical(
            String chemicalID,
            String txnType,
            BigDecimal qty,
            Date txnDate,
            String reference,
            String remarks)
            throws ValidationException {
        if (chemicalID == null || chemicalID.trim().isEmpty()) {
            throw new ValidationException("Chemical ID cannot be empty.");
        }
        if (qty == null || qty.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantity must be greater than zero.");
        }
        if (!(txnType.equalsIgnoreCase("RETURN")
                || txnType.equalsIgnoreCase("ADJUSTMENT"))) {
            throw new ValidationException("Invalid transaction type.");
        }
        Chemical chemical = chemicalDAO.findChemical(chemicalID);
        if (chemical == null) {
            throw new ValidationException("Chemical not found.");
        }
        BigDecimal currentStock =
                transactionDAO.calculateCurrentStock(chemicalID);
        ChemicalTransaction txn = new ChemicalTransaction();
        txn.setTransactionID(transactionDAO.generateTransactionID());
        txn.setChemicalID(chemicalID);
        txn.setTransactionType(txnType.toUpperCase());
        txn.setTransactionDate(txnDate);
        txn.setQuantity(qty);
        txn.setIssuedToOrUsedBy(reference);
        txn.setPurposeOrReference(reference);
        txn.setRunningBalanceHint(currentStock.add(qty));
        txn.setRemarks(remarks);
        return transactionDAO.insertTransaction(txn);
    }
    public ArrayList<ChemicalTransaction> listTransactionsByChemical(
            String chemicalID)
            throws ValidationException {
        if (chemicalID == null || chemicalID.trim().isEmpty()) {
            throw new ValidationException("Chemical ID cannot be empty.");
        }
        return transactionDAO.findTransactionsByChemical(chemicalID);
    }
    public ArrayList<ChemicalTransaction> listTransactionsByDateRange(
            Date fromDate,
            Date toDate)
            throws ValidationException {
        if (fromDate == null || toDate == null) {
            throw new ValidationException("Dates cannot be null.");
        }
        if (fromDate.after(toDate)) {
            throw new ValidationException(
                    "From date cannot be after To date.");
        }
        return transactionDAO.findTransactionsByDateRange(fromDate, toDate);
    }
    public boolean deactivateChemical(String chemicalID)
            throws ValidationException, ActiveStockExistException {
        if (chemicalID == null || chemicalID.trim().isEmpty()) {
            throw new ValidationException("Chemical ID cannot be empty.");
        }
        Chemical chemical = chemicalDAO.findChemical(chemicalID);
        if (chemical == null) {
            throw new ValidationException("Chemical not found.");
        }
        BigDecimal currentStock =
                transactionDAO.calculateCurrentStock(chemicalID);
        if (currentStock.compareTo(BigDecimal.ZERO) > 0) {
            throw new ActiveStockExistException(
                    "Chemical still has stock available.");
        }
        return chemicalDAO.updateChemicalStatus(
                chemicalID,
                "INACTIVE");
    }
    }