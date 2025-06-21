/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysis.library.main;

import com.joysis.library.functions.AuthorFunction;
import com.joysis.library.util.DbConnection;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Main {
    
    public static void main(String[] args) {
        DbConnection db = new DbConnection();
        Scanner scanner = new Scanner(System.in);
        AuthorFunction author = new AuthorFunction(db);
        
        System.out.println("*** Display All Books ***\n");
//        System.out.print("Enter author Name that you want to search: ");
//        String authorName = scanner.nextLine();
//        System.out.print("Author Contact: ");
//        String authorContact = scanner.nextLine();
        
        author.diplayBooksUsingInnerJoin();
    }
}
