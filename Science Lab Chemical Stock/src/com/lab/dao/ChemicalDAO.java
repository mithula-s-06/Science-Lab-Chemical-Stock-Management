package com.lab.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lab.bean.Chemical;
import com.lab.util.DBUtil;
public class ChemicalDAO {
    public Chemical findChemical(String chemicalID) {
        Chemical chemical = null;
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM CHEMICAL_TBL WHERE CHEMICAL_ID = ?")) {
            ps.setString(1, chemicalID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chemical = new Chemical();
                chemical.setChemicalID(rs.getString("CHEMICAL_ID"));
                chemical.setChemicalName(rs.getString("CHEMICAL_NAME"));
                chemical.setCategory(rs.getString("CATEGORY"));
                chemical.setUnitOfMeasure(rs.getString("UNIT_OF_MEASURE"));
                chemical.setReorderLevel(rs.getBigDecimal("REORDER_LEVEL"));
                chemical.setHazardLevel(rs.getString("HAZARD_LEVEL"));
                chemical.setShelfLocation(rs.getString("SHELF_LOCATION"));
                chemical.setStatus(rs.getString("STATUS"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chemical;
    }
    public ArrayList<Chemical> viewAllChemicals() {
        ArrayList<Chemical> list = new ArrayList<>();
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM CHEMICAL_TBL ORDER BY CHEMICAL_ID")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chemical chemical = new Chemical();
                chemical.setChemicalID(rs.getString("CHEMICAL_ID"));
                chemical.setChemicalName(rs.getString("CHEMICAL_NAME"));
                chemical.setCategory(rs.getString("CATEGORY"));
                chemical.setUnitOfMeasure(rs.getString("UNIT_OF_MEASURE"));
                chemical.setReorderLevel(rs.getBigDecimal("REORDER_LEVEL"));
                chemical.setHazardLevel(rs.getString("HAZARD_LEVEL"));
                chemical.setShelfLocation(rs.getString("SHELF_LOCATION"));
                chemical.setStatus(rs.getString("STATUS"));
                list.add(chemical);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean insertChemical(Chemical chemical) {
        String sql = "INSERT INTO CHEMICAL_TBL VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, chemical.getChemicalID());
            ps.setString(2, chemical.getChemicalName());
            ps.setString(3, chemical.getCategory());
            ps.setString(4, chemical.getUnitOfMeasure());
            ps.setBigDecimal(5, chemical.getReorderLevel());
            ps.setString(6, chemical.getHazardLevel());
            ps.setString(7, chemical.getShelfLocation());
            ps.setString(8, chemical.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateChemicalStatus(String chemicalID, String newStatus) {
        String sql = "UPDATE CHEMICAL_TBL SET STATUS=? WHERE CHEMICAL_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, chemicalID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}