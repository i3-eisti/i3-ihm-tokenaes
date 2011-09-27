package org.blackpanther.aestokenizer.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

/**
 * @author MACHIZAUD Andréa
 * @version 9/22/11
 */
public final class Cryptography {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Cryptography.class.getCanonicalName());

    private static final byte[] SECRET_KEY = new byte[]{
            -52,
            109,
            -112,
            -31,
            51,
            12,
            -54,
            21,
            -17,
            -81,
            -127,
            -110,
            -25,
            -72,
            89,
            75
    };

    private static final Cipher CRYPTOGRAPHER = buildCryptographer();

    /*=========================================================================
                       INITIALIZATION
      =========================================================================*/

    private static Cipher buildCryptographer() {
        try {
            SecretKeySpec superSecretKey = new SecretKeySpec(SECRET_KEY, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, superSecretKey);

            return cipher;
        } catch (Exception e) {
            handleError(e);
            throw new Error("Cannot instantiate this application because AES cryptographer cannot be initialized", e);
        }
    }

    /**
     * TODO Handle exception visually
     */
    private static void handleError(Exception e) {
        e.printStackTrace();
    }

    /*=========================================================================
                     OPERATIONS
      =========================================================================*/

    public static final byte[] NO_CRYPT = new byte[]{};

    public static byte[][] crypt(String[] dataArray) {
        final byte[][] result = new byte[dataArray.length][];
        for (int i = result.length; i-- > 0; ) {
            result[i] = crypt(dataArray[i]);
        }
        return result;
    }

    public static byte[] crypt(Object data) {
        return data == null
                ? NO_CRYPT
                : crypt(data.toString());
    }

    public static byte[] crypt(String data) {
        return data == null
                ? NO_CRYPT
                : crypt(data.getBytes(Charset.forName("UTF-8")));
    }

    public static byte[] crypt(byte[] data) {
        try {
            return Cryptography.CRYPTOGRAPHER.doFinal(data);
        } catch (Exception e) {
            handleError(e);
            return NO_CRYPT;
        }
    }

    /**
     * Turns array of bytes into string
     *
     * @param buf Array of bytes to convert to hex string
     * @return Generated hex string
     */
    public static String cryptedAsHexString(byte buf[]) {
        StringBuilder strbuf = new StringBuilder(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)
                strbuf.append("0");

            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }
}
