package nl.saxion.internettech.server.main.encoding;

import java.util.Base64;

/**
 * @author jarnowitjes on 2019-02-18
 */
public class Encoder {

    private Encoder()
    {}

    public static String encode64MD5(String input) {
        return Base64.getEncoder().encodeToString(MD5.getMD5(input));
    }
}