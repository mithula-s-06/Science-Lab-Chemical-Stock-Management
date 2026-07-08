package com.lab.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lab.bean.ChemicalTransaction;
import com.lab.util.DBUtil;
public class ChemicalTransactionDAO {
    public int generateTransactionID() {
        int id = 820001;
        String sql = "SELECT NVL(MAX(TRANSACTION_ID),820000)+1 FROM CHEMICAL_TXN_TBL";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
    public boolean insertTransaction(ChemicalTransaction txn) {
        String sql = "INSERT INTO CHEMICAL_TXN_TBL VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, txn.getTransactionID());
            ps.setString(2, txn.getChemicalID());
            ps.setString(3, txn.getTransactionType());
            ps.setDate(4, txn.getTransactionDate());
            ps.setBigDecimal(5, txn.getQuantity());
            ps.setString(6, txn.getIssuedToOrUsedBy());
            ps.setString(7, txn.getPurposeOrReference());
            ps.setBigDecimal(8, txn.getRunningBalanceHint());
            ps.setString(9, txn.getRemarks());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public BigDecimal calculateCurrentStock(String chemicalID) {
        BigDecimal stock = BigDecimal.ZERO;
        String sql =
                "SELECT NVL(SUM(" +
                "CASE " +
                "WHEN TRANSACTION_TYPE IN ('OPENING','RETURN','ADJUSTMENT') THEN QUANTITY " +
                "WHEN TRANSACTION_TYPE='ISSUE' THEN -QUANTITY " +
                "ELSE 0 END),0) " +
                "FROM CHEMICAL_TXN_TBL " +
                "WHERE CHEMICAL_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, chemicalID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stock = rs.getBigDecimal(1);
                if (stock == null)
                    stock = BigDecimal.ZERO;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }
    public ArrayList<ChemicalTransaction> findTransactionsByChemical(String chemicalID) {
        ArrayList<ChemicalTransaction> list = new ArrayList<>();
        String sql =
                "SELECT * FROM CHEMICAL_TXN_TBL " +
                "WHERE CHEMICAL_ID=? " +
                "ORDER BY TRANSACTION_DATE, TRANSACTION_ID";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, chemicalID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChemicalTransaction txn = new ChemicalTransaction();
                txn.setTransactionID(rs.getInt("TRANSACTION_ID"));
                txn.setChemicalID(rs.getString("CHEMICAL_ID"));
                txn.setTransactionType(rs.getString("TRANSACTION_TYPE"));
                txn.setTransactionDate(rs.getDate("TRANSACTION_DATE"));
                txn.setQuantity(rs.getBigDecimal("QUANTITY"));
                txn.setIssuedToOrUsedBy(rs.getString("ISSUED_TO_OR_USED_BY"));
                txn.setPurposeOrReference(rs.getString("PURPOSE_OR_REFERENCE"));
                txn.setRunningBalanceHint(rs.getBigDecimal("RUNNING_BALANCE_HINT"));
                txn.setRemarks(rs.getString("REMARKS"));
                list.add(txn);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<ChemicalTransaction> findTransactionsByDateRange(Date fromDate,
                                                                       Date toDate) {
        ArrayList<ChemicalTransaction> list = new ArrayList<>();
        String sql =
                "SELECT * FROM CHEMICAL_TXN_TBL " +
                "WHERE TRANSACTION_DATE BETWEEN ? AND ? " +
                "ORDER BY TRANSACTION_DATE, TRANSACTION_ID";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, fromDate);
            ps.setDate(2, toDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChemicalTransaction txn = new ChemicalTransaction();
                txn.setTransactionID(rs.getInt("TRANSACTION_ID"));
                txn.setChemicalID(rs.getString("CHEMICAL_ID"));
                txn.setTransactionType(rs.getString("TRANSACTION_TYPE"));
                txn.setTransactionDate(rs.getDate("TRANSACTION_DATE"));
                txn.setQuantity(rs.getBigDecimal("QUANTITY"));
                txn.setIssuedToOrUsedBy(rs.getString("ISSUED_TO_OR_USED_BY"));
                txn.setPurposeOrReference(rs.getString("PURPOSE_OR_REFERENCE"));
                txn.setRunningBalanceHint(rs.getBigDecimal("RUNNING_BALANCE_HINT"));
                txn.setRemarks(rs.getString("REMARKS"));
                list.add(txn);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}