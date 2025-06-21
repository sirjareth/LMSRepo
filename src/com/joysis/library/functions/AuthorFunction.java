/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysis.library.functions;

import com.joysis.library.util.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthorFunction {

    private final DbConnection dbConnection; // composition

    // constructor injection
    public AuthorFunction(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // CRUD Operation / Functionality
    // Read Operation
    public void diplayAuthors() {
        String query = "SELECT * FROM tblauthors WHERE isArchived = 0";
        // try with resources

        try (Connection connnection = dbConnection.connect();
                Statement state = connnection.createStatement();
                ResultSet result = state.executeQuery(query);) {

            System.out.println("ID\tNAME\t\t\tADDRESS\t\t\tCONTACT"); // table header
            while (result.next()) {
                int authorId = result.getInt("author_id");
                String authorName = result.getString("author_name");
                String authorAddress = result.getString("author_address");
                String authorContact = result.getString("author_contact");

                // connection to view
                System.out.println(authorId + "\t" + authorName + "\t\t" + authorAddress + "\t\t\t" + authorContact);
            }
        } catch (SQLException e) {
            System.out.println("Display Authors: " + e.getMessage());
        }

    }
    
    public void diplayAuthorsById() {
//        String query = "SELECT * FROM tblauthors WHERE athorId = ?";
//        // try with resources
//
//        try (Connection connnection = dbConnection.connect();
//                Statement state = connnection.createStatement();
//                ResultSet result = state.executeQuery(query);) {
//
//            System.out.println("ID\tNAME\t\t\tADDRESS\t\t\tCONTACT"); // table header
//            while (result.next()) {
//                int authorId = result.getInt("author_id");
//                String authorName = result.getString("author_name");
//                String authorAddress = result.getString("author_address");
//                String authorContact = result.getString("author_contact");
//
//                // connection to view
//                System.out.println(authorId + "\t" + authorName + "\t\t" + authorAddress + "\t\t\t" + authorContact);
//            }
//        } catch (SQLException e) {
//            System.out.println("Display Authors: " + e.getMessage());
//        }

    }
    

    public void createAuthor(String authorName, String authorAddress, String authorContact) {
        String query = "INSERT INTO tblauthors (author_name, author_address, author_contact) "
                + "VALUES (?,?,?)"; // Anti SQL Injection / parameterized query

        try (Connection connection = dbConnection.connect();
                PreparedStatement prep =  connection.prepareStatement(query);){
            
            // parameter = wild cards
            prep.setString(1, authorName);
            prep.setString(2, authorAddress);
            prep.setString(3, authorContact);
            
            prep.executeUpdate();
            System.out.println("Author " + authorName + " added successfully!\n");
            // synchorinization
            diplayAuthors();
        } catch (SQLException e) {
            System.out.println("Create Author: " + e.getMessage());
        }

        
    }
    
    public void updateAuthor(String authorName, int authorId) {
        String query = "UPDATE tblauthors SET author_name = ? WHERE author_id = ?"; // Anti SQL Injection / parameterized query

        try (Connection connection = dbConnection.connect();
                PreparedStatement prep = connection.prepareStatement(query);) {

            // parameter = wild cards
            prep.setString(1, authorName);
            prep.setInt(2, authorId);
           
            prep.executeUpdate();
            System.out.println("Author " + authorName + " updated successfully!\n");
            // synchorinization
            diplayAuthors();
        } catch (SQLException e) {
            System.out.println("Create Author: " + e.getMessage());
        }
    }
    
    public void deleteAuthor(int authorID){
        String query = "DELETE FROM tblauthors WHERE author_id = ?";
        
        try (Connection connection = dbConnection.connect();
                PreparedStatement prep = connection.prepareStatement(query);){
            
            prep.setInt(1, authorID);
            
            prep.executeUpdate();
            System.out.println("Author " + authorID + " deleted successfully!\n");
            diplayAuthors();
        } catch (SQLException e) {
            System.out.println("Delete Author Method: " + e.getMessage());
        }
    }
    
    public void archiveAuthor(int authorID){
        String query = "UPDATE tblauthors SET isArchived = 1 WHERE author_id = ?";
        
        try (Connection connection = dbConnection.connect();
                PreparedStatement prep = connection.prepareStatement(query);){
            
            prep.setInt(1, authorID);
            
            prep.executeUpdate();
            System.out.println("Author " + authorID + " archived successfully!\n");
            diplayAuthors();
        } catch (SQLException e) {
            System.out.println("Archive Author Method: " + e.getMessage());
        }
        
    }
    
    public void restoreAuthor(int authorID){
        String query = "UPDATE tblauthors SET isArchived = 0 WHERE author_id = ?";
        
        try (Connection connection = dbConnection.connect();
                PreparedStatement prep = connection.prepareStatement(query);){
            
            prep.setInt(1, authorID);
            
            prep.executeUpdate();
            System.out.println("Author " + authorID + " restored successfully!\n");
            diplayAuthors();
        } catch (SQLException e) {
            System.out.println("Restore Author Method: " + e.getMessage());
        }
        
    }
    
    public void searchAuthor(String authorNameKW){
        String query = "SELECT * FROM tblauthors WHERE author_name LIKE ?";
        
        // try with resources
        try (Connection connection = dbConnection.connect();
                PreparedStatement prep = connection.prepareStatement(query);) {

            prep.setString(1, "%" + authorNameKW + "%");
            
            ResultSet result = prep.executeQuery();
            
            System.out.println("ID\tNAME\t\t\tADDRESS\t\t\tCONTACT"); // table header
            while (result.next()) {
                int authorId = result.getInt("author_id");
                String authorName = result.getString("author_name");
                String authorAddress = result.getString("author_address");
                String authorContact = result.getString("author_contact");

                // connection to view
                System.out.println(authorId + "\t" + authorName + "\t\t" + authorAddress + "\t\t\t" + authorContact);
            }
        } catch (SQLException e) {
            System.out.println("Display Authors: " + e.getMessage());
        }
    }
    
    // For Books Table
    public void diplayBooksUsingInnerJoin() {
        String query = "SELECT book_title, author_name, pub_name, book_edition, book_pages "
                + "FROM tblbooks "
                + "INNER JOIN tblauthors "
                + "ON tblbooks.author_id = tblauthors.author_id "
                + "INNER JOIN tblpublishers "
                + "ON tblbooks.pub_id = tblpublishers.pub_id";
        // try with resources

        try (Connection connnection = dbConnection.connect();
                Statement state = connnection.createStatement();
                ResultSet result = state.executeQuery(query);) {

            System.out.println("TITLE\t\t\tAUTHOR\t\tPUBLISHER\t\tEDITION\t\tPAGES"); // table header
            while (result.next()) {
                String bookTitle = result.getString("book_title");
                String authorName = result.getString("author_name");
                String pubName = result.getString("pub_name");
                String bookEdition = result.getString("book_edition");
                int bookPages = result.getInt("book_pages");

                // connection to view
                System.out.println(bookTitle + "\t" + authorName + "\t" + pubName + "\t" +  bookEdition + "\t\t" + bookPages);
            }
        } catch (SQLException e) {
            System.out.println("Display Authors: " + e.getMessage());
        }

    }
    
    
    
    
}
