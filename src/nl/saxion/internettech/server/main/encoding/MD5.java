package nl.saxion.internettech.server.main.encoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jarnowitjes on 2019-02-18
 */
public class MD5 {

    private MD5()
    {}

    public static byte[] getMD5(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(input.getBytes());
        } catch (NoSuchAlgorithmException nsa) {
            throw new RuntimeException(nsa);
        }
    }
}