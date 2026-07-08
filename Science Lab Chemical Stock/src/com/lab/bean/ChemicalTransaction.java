package com.lab.bean;
import java.math.BigDecimal;
import java.sql.Date;
public class ChemicalTransaction {
    private int transactionID;
    private String chemicalID;
    private String transactionType;
    private Date transactionDate;
    private BigDecimal quantity;
    private String issuedToOrUsedBy;
    private String purposeOrReference;
    private BigDecimal runningBalanceHint;
    private String remarks;
    public ChemicalTransaction() {
    }
    public ChemicalTransaction(int transactionID, String chemicalID,String transactionType, Date transactionDate,BigDecimal quantity,
    String issuedToOrUsedBy,String purposeOrReference,BigDecimal runningBalanceHint,String remarks) {
        this.transactionID = transactionID;
        this.chemicalID = chemicalID;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.issuedToOrUsedBy = issuedToOrUsedBy;
        this.purposeOrReference = purposeOrReference;
        this.runningBalanceHint = runningBalanceHint;
        this.remarks = remarks;
    }
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getChemicalID() {
        return chemicalID;
    }

    public void setChemicalID(String chemicalID) {
        this.chemicalID = chemicalID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getIssuedToOrUsedBy() {
        return issuedToOrUsedBy;
    }

    public void setIssuedToOrUsedBy(String issuedToOrUsedBy) {
        this.issuedToOrUsedBy = issuedToOrUsedBy;
    }

    public String getPurposeOrReference() {
        return purposeOrReference;
    }

    public void setPurposeOrReference(String purposeOrReference) {
        this.purposeOrReference = purposeOrReference;
    }

    public BigDecimal getRunningBalanceHint() {
        return runningBalanceHint;
    }

    public void setRunningBalanceHint(BigDecimal runningBalanceHint) {
        this.runningBalanceHint = runningBalanceHint;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Transaction ID : " + transactionID +
               "\nChemical ID : " + chemicalID +
               "\nType : " + transactionType +
               "\nDate : " + transactionDate +
               "\nQuantity : " + quantity +
               "\nIssued To / Used By : " + issuedToOrUsedBy +
               "\nPurpose / Reference : " + purposeOrReference +
               "\nRunning Balance : " + runningBalanceHint +
               "\nRemarks : " + remarks;
    }
}