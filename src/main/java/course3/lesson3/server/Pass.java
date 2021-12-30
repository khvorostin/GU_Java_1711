package course3.lesson3.server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Pass {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        String pass = "pass1";

        byte[] salt = getSalt();
        byte[] hashedPassword = getHashedPassword(pass, salt);

//        for (byte h : hashedPassword) {
//            System.out.println("** " + h);
//        }

        int shift = 176;

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            char c = (char)(shift + b);
            sb.append(c);
        }

        System.out.println("--- " + sb.toString());

        String slt = salt.toString();
        String slt2 = new String(salt);

        String hp = new String(hashedPassword);


        System.out.println("1: [ " + slt + " ]");
        System.out.println("2: [ " + slt2 + " ]");
        System.out.println("[ " + hp + " ]");
//        System.out.println(slt2.toString());

//        String hp = new String(hashedPassword, StandardCharsets.UTF_8);

//        System.out.println(pass + " " + slt + " " + hp);
    }

    private static byte[] getSalt()
    {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] getHashedPassword(String password, byte[] salt)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(salt);
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }

}
