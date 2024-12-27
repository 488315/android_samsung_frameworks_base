package android.security.identity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;

public class Util {
    private static final String TAG = "Util";

    static byte[] stripLeadingZeroes(byte[] value) {
        int n = 0;
        while (n < value.length && value[n] == 0) {
            n++;
        }
        int newLen = value.length - n;
        byte[] ret = new byte[newLen];
        int m = 0;
        while (n < value.length) {
            ret[m] = value[n];
            m++;
            n++;
        }
        return ret;
    }

    static byte[] publicKeyEncodeUncompressedForm(PublicKey publicKey) {
        ECPoint w = ((ECPublicKey) publicKey).getW();
        BigInteger x = w.getAffineX();
        BigInteger y = w.getAffineY();
        if (x.compareTo(BigInteger.ZERO) < 0) {
            throw new RuntimeException("X is negative");
        }
        if (y.compareTo(BigInteger.ZERO) < 0) {
            throw new RuntimeException("Y is negative");
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(4);
            byte[] xBytes = stripLeadingZeroes(x.toByteArray());
            if (xBytes.length > 32) {
                throw new RuntimeException("xBytes is " + xBytes.length + " which is unexpected");
            }
            for (int n = 0; n < 32 - xBytes.length; n++) {
                baos.write(0);
            }
            baos.write(xBytes);
            byte[] yBytes = stripLeadingZeroes(y.toByteArray());
            if (yBytes.length > 32) {
                throw new RuntimeException("yBytes is " + yBytes.length + " which is unexpected");
            }
            for (int n2 = 0; n2 < 32 - yBytes.length; n2++) {
                baos.write(0);
            }
            baos.write(yBytes);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] computeHkdf(
            java.lang.String r8, byte[] r9, byte[] r10, byte[] r11, int r12) {
        /*
            r0 = 0
            javax.crypto.Mac r1 = javax.crypto.Mac.getInstance(r8)     // Catch: java.security.NoSuchAlgorithmException -> L74
            r0 = r1
            int r1 = r0.getMacLength()
            int r1 = r1 * 255
            if (r12 > r1) goto L6b
            if (r10 == 0) goto L1e
            int r1 = r10.length     // Catch: java.security.InvalidKeyException -> L62
            if (r1 != 0) goto L15
            goto L1e
        L15:
            javax.crypto.spec.SecretKeySpec r1 = new javax.crypto.spec.SecretKeySpec     // Catch: java.security.InvalidKeyException -> L62
            r1.<init>(r10, r8)     // Catch: java.security.InvalidKeyException -> L62
            r0.init(r1)     // Catch: java.security.InvalidKeyException -> L62
            goto L2c
        L1e:
            javax.crypto.spec.SecretKeySpec r1 = new javax.crypto.spec.SecretKeySpec     // Catch: java.security.InvalidKeyException -> L62
            int r2 = r0.getMacLength()     // Catch: java.security.InvalidKeyException -> L62
            byte[] r2 = new byte[r2]     // Catch: java.security.InvalidKeyException -> L62
            r1.<init>(r2, r8)     // Catch: java.security.InvalidKeyException -> L62
            r0.init(r1)     // Catch: java.security.InvalidKeyException -> L62
        L2c:
            byte[] r1 = r0.doFinal(r9)     // Catch: java.security.InvalidKeyException -> L62
            byte[] r2 = new byte[r12]     // Catch: java.security.InvalidKeyException -> L62
            r3 = 1
            r4 = 0
            javax.crypto.spec.SecretKeySpec r5 = new javax.crypto.spec.SecretKeySpec     // Catch: java.security.InvalidKeyException -> L62
            r5.<init>(r1, r8)     // Catch: java.security.InvalidKeyException -> L62
            r0.init(r5)     // Catch: java.security.InvalidKeyException -> L62
            r5 = 0
            byte[] r6 = new byte[r5]     // Catch: java.security.InvalidKeyException -> L62
        L3f:
            r0.update(r6)     // Catch: java.security.InvalidKeyException -> L62
            r0.update(r11)     // Catch: java.security.InvalidKeyException -> L62
            byte r7 = (byte) r3     // Catch: java.security.InvalidKeyException -> L62
            r0.update(r7)     // Catch: java.security.InvalidKeyException -> L62
            byte[] r7 = r0.doFinal()     // Catch: java.security.InvalidKeyException -> L62
            r6 = r7
            int r7 = r6.length     // Catch: java.security.InvalidKeyException -> L62
            int r7 = r7 + r4
            if (r7 >= r12) goto L5b
            int r7 = r6.length     // Catch: java.security.InvalidKeyException -> L62
            java.lang.System.arraycopy(r6, r5, r2, r4, r7)     // Catch: java.security.InvalidKeyException -> L62
            int r7 = r6.length     // Catch: java.security.InvalidKeyException -> L62
            int r4 = r4 + r7
            int r3 = r3 + 1
            goto L3f
        L5b:
            int r7 = r12 - r4
            java.lang.System.arraycopy(r6, r5, r2, r4, r7)     // Catch: java.security.InvalidKeyException -> L62
            return r2
        L62:
            r1 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = "Error MACing"
            r2.<init>(r3, r1)
            throw r2
        L6b:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "size too large"
            r1.<init>(r2)
            throw r1
        L74:
            r1 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "No such algorithm: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3, r1)
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.security.identity.Util.computeHkdf(java.lang.String, byte[],"
                    + " byte[], byte[], int):byte[]");
    }

    private Util() {}
}
