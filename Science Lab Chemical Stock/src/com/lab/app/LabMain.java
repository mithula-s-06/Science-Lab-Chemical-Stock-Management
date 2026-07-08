package com.lab.app;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import com.lab.bean.Chemical;
import com.lab.bean.ChemicalTransaction;
import com.lab.service.LabInventoryService;
import com.lab.util.ActiveStockExistException;
import com.lab.util.LowStockException;
import com.lab.util.ValidationException;
public class LabMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabInventoryService service = new LabInventoryService();
        while (true) {
            System.out.println("SCHOOL SCIENCE LAB INVENTORY");
            System.out.println("1. Register New Chemical");
            System.out.println("2. View Chemical Details");
            System.out.println("3. View All Chemicals");
            System.out.println("4. Issue Chemical");
            System.out.println("5. Return/Adjust Chemical");
            System.out.println("6. View Transactions by Chemical");
            System.out.println("7. View Transactions by Date Range");
            System.out.println("8. Deactivate Chemical");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            try {
                switch (choice) {
                case 1:
                    Chemical chemical = new Chemical();
                    System.out.print("Chemical ID: ");
                    chemical.setChemicalID(sc.nextLine());
                    System.out.print("Chemical Name: ");
                    chemical.setChemicalName(sc.nextLine());
                    System.out.print("Category: ");
                    chemical.setCategory(sc.nextLine());
                    System.out.print("Unit Of Measure: ");
                    chemical.setUnitOfMeasure(sc.nextLine());
                    System.out.print("Reorder Level: ");
                    chemical.setReorderLevel(
                            new BigDecimal(sc.nextLine()));
                    System.out.print("Hazard Level: ");
                    chemical.setHazardLevel(sc.nextLine());
                    System.out.print("Shelf Location: ");
                    chemical.setShelfLocation(sc.nextLine());
                    chemical.setStatus("ACTIVE");
                    System.out.print("Opening Quantity: ");
                    BigDecimal openingQty =
                            new BigDecimal(sc.nextLine());
                    Date openingDate =
                            new Date(System.currentTimeMillis());
                    if (service.registerNewChemicalWithOpeningStock(
                            chemical,
                            openingQty,
                            openingDate)) {
                        System.out.println(
                                "Chemical Registered Successfully.");
                    } else {
                        System.out.println(
                                "Registration Failed.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Chemical ID: ");
                    String chemicalID = sc.nextLine();
                    Chemical ch =
                            service.viewChemicalDetails(chemicalID);
                    if (ch == null) {
                        System.out.println(
                                "Chemical Not Found.");
                    } else {
                        System.out.println("Chemical ID : "
                                + ch.getChemicalID());
                        System.out.println("Name        : "
                                + ch.getChemicalName());
                        System.out.println("Category    : "
                                + ch.getCategory());
                        System.out.println("Unit        : "
                                + ch.getUnitOfMeasure());
                        System.out.println("Reorder     : "
                                + ch.getReorderLevel());
                        System.out.println("Hazard      : "
                                + ch.getHazardLevel());
                        System.out.println("Shelf       : "
                                + ch.getShelfLocation());
                        System.out.println("Status      : "
                                + ch.getStatus());
                    }
                    break;
                case 3:
                    ArrayList<Chemical> chemicals =
                            service.viewAllChemicals();
                    if (chemicals.isEmpty()) {
                        System.out.println(
                                "No Chemicals Found.");
                    } else {
                        for (Chemical c : chemicals) {
                            System.out.println("Chemical ID : "
                                    + c.getChemicalID());
                            System.out.println("Name        : "
                                    + c.getChemicalName());
                            System.out.println("Category    : "
                                    + c.getCategory());
                            System.out.println("Unit        : "
                                    + c.getUnitOfMeasure());
                            System.out.println("Reorder     : "
                                    + c.getReorderLevel());
                            System.out.println("Hazard      : "
                                    + c.getHazardLevel());
                            System.out.println("Shelf       : "
                                    + c.getShelfLocation());
                            System.out.println("Status      : "
                                    + c.getStatus());
                        }
                    }
                    break;
                case 4:
                    System.out.print("Chemical ID: ");
                    chemicalID = sc.nextLine();
                    System.out.print("Issue Quantity: ");
                    BigDecimal issueQty =
                            new BigDecimal(sc.nextLine());
                    System.out.print("Issued To: ");
                    String issuedTo = sc.nextLine();
                    System.out.print("Purpose: ");
                    String purpose = sc.nextLine();
                    if (service.issueChemical(
                            chemicalID,
                            issueQty,
                            new Date(System.currentTimeMillis()),
                            issuedTo,
                            purpose)) {
                        System.out.println(
                                "Chemical Issued Successfully.");
                    } else {
                        System.out.println("Issue Failed.");
                    }
                    break;
                case 5:
                    System.out.print("Chemical ID: ");
                    chemicalID = sc.nextLine();
                    System.out.print(
                            "Transaction Type (RETURN/ADJUSTMENT): ");
                    String txnType = sc.nextLine();
                    System.out.print("Quantity: ");
                    BigDecimal qty =
                            new BigDecimal(sc.nextLine());
                    System.out.print("Reference: ");
                    String reference = sc.nextLine();
                    System.out.print("Remarks: ");
                    String remarks = sc.nextLine();
                    if (service.returnOrAdjustChemical(
                            chemicalID,
                            txnType,
                            qty,
                            new Date(System.currentTimeMillis()),
                            reference,
                            remarks)) {
                        System.out.println(
                                "Transaction Recorded.");
                    } else {
                        System.out.println(
                                "Operation Failed.");
                    }
                    break;
                case 6:
                    System.out.print("Chemical ID: ");
                    chemicalID = sc.nextLine();
                    ArrayList<ChemicalTransaction> txns =
                            service.listTransactionsByChemical(chemicalID);
                    if (txns.isEmpty()) {
                        System.out.println("No Transactions Found.");
                    } else {
                        for (ChemicalTransaction t : txns) {
                            System.out.println("Transaction ID : "
                                    + t.getTransactionID());
                            System.out.println("Chemical ID    : "
                                    + t.getChemicalID());
                            System.out.println("Type           : "
                                    + t.getTransactionType());
                            System.out.println("Date           : "
                                    + t.getTransactionDate());
                            System.out.println("Quantity       : "
                                    + t.getQuantity());
                            System.out.println("Issued To      : "
                                    + t.getIssuedToOrUsedBy());
                            System.out.println("Purpose        : "
                                    + t.getPurposeOrReference());
                            System.out.println("Balance        : "
                                    + t.getRunningBalanceHint());
                            System.out.println("Remarks        : "
                                    + t.getRemarks());
                        }
                    }
                    break;
                case 7:
                    System.out.print("From Date (yyyy-mm-dd): ");
                    Date fromDate = Date.valueOf(sc.nextLine());
                    System.out.print("To Date (yyyy-mm-dd): ");
                    Date toDate = Date.valueOf(sc.nextLine());
                    ArrayList<ChemicalTransaction> list =
                            service.listTransactionsByDateRange(fromDate,
                                    toDate);
                    if (list.isEmpty()) {
                        System.out.println(
                                "No Transactions Found.");
                    } else {
                        for (ChemicalTransaction t : list) {
                            System.out.println("------------------------------------");
                            System.out.println("Transaction ID : "
                                    + t.getTransactionID());
                            System.out.println("Chemical ID    : "
                                    + t.getChemicalID());
                            System.out.println("Type           : "
                                    + t.getTransactionType());
                            System.out.println("Date           : "
                                    + t.getTransactionDate());
                            System.out.println("Quantity       : "
                                    + t.getQuantity());
                        }
                    }
                    break;
                case 8:
                    System.out.print("Chemical ID: ");
                    chemicalID = sc.nextLine();
                    if (service.deactivateChemical(chemicalID))
                        System.out.println(
                                "Chemical Deactivated Successfully.");
                    else
                        System.out.println(
                                "Unable to Deactivate Chemical.");
                    break;
                case 9:
                    System.out.println("Thank You!");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice.");
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            } catch (LowStockException e) {
                System.out.println(e.getMessage());
            } catch (ActiveStockExistException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}