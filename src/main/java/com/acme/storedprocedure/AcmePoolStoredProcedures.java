/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.storedprocedure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Juneau
 */
public class AcmePoolStoredProcedures {

    public static void obtainCustomerPoolInformation(long customerId,
            String[] poolInformation) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            // "jdbc:default:connection" tells the DriverManager to use the existing connection.
            conn = DriverManager.getConnection("jdbc:default:connection");

            // prepare the query
            String sql = "SELECT STYLE, SHAPE, LENGTH, WIDTH, RADIUS, GALLONS " +
                    "FROM POOL P, CUSTOMER CUST " +
                    "WHERE P.ID = CUST.POOL_ID " +
                    "AND CUST.CUSTOMER_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, customerId);
            res = stmt.executeQuery();

            poolInformation[0] = (res.next()) ? res.getString(1) : "N/A";
            poolInformation[1] = (res.next()) ? res.getString(2) : "N/A";
            poolInformation[2] = (res.next()) ? res.getString(3) : "N/A";
            poolInformation[3] = (res.next()) ? res.getString(4) : "N/A";
            poolInformation[4] = (res.next()) ? res.getString(5) : "N/A";
            poolInformation[5] = (res.next()) ? res.getString(6) : "N/A";


        } finally {
            if (res != null) {
                // close the result set
                res.close();
            }

            if (stmt != null) {
                // close the statement
                stmt.close();
            }

            if (conn != null) {
                // close the db connection
                conn.close();
            }
        }
    }
}
