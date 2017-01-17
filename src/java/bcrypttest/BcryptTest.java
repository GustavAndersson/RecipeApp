/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcrypttest;

import nu.te4.support.BCrypt;

/**
 *
 * @author guan97005
 */
public class BcryptTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String password1 = "Egonbog";
        String password2 = "Någonting annat!";
        
        String hash = hashPassword(password1); //sparas i databasen
        System.out.println(hash);
        if(BCrypt.checkpw(password1, hash)){
            System.out.println("Lösenordet stämmer!");
        }
    }
    
    public static String hashPassword(String password){
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password, salt);
        return hash;
    }
    
    
}
