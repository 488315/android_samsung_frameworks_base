package android.security.identity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;

/* loaded from: classes3.dex */
public class Util {
    private static final String TAG = "Util";

    static byte[] stripLeadingZeroes(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        while (i2 < bArr.length && bArr[i2] == 0) {
            i2++;
        }
        byte[] bArr2 = new byte[bArr.length - i2];
        while (i2 < bArr.length) {
            bArr2[i] = bArr[i2];
            i++;
            i2++;
        }
        return bArr2;
    }

    static byte[] publicKeyEncodeUncompressedForm(PublicKey publicKey) {
        ECPoint w = ((ECPublicKey) publicKey).getW();
        BigInteger affineX = w.getAffineX();
        BigInteger affineY = w.getAffineY();
        if (affineX.compareTo(BigInteger.ZERO) < 0) {
            throw new RuntimeException("X is negative");
        }
        if (affineY.compareTo(BigInteger.ZERO) < 0) {
            throw new RuntimeException("Y is negative");
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(4);
            byte[] stripLeadingZeroes = stripLeadingZeroes(affineX.toByteArray());
            if (stripLeadingZeroes.length > 32) {
                throw new RuntimeException("xBytes is " + stripLeadingZeroes.length + " which is unexpected");
            }
            for (int i = 0; i < 32 - stripLeadingZeroes.length; i++) {
                byteArrayOutputStream.write(0);
            }
            byteArrayOutputStream.write(stripLeadingZeroes);
            byte[] stripLeadingZeroes2 = stripLeadingZeroes(affineY.toByteArray());
            if (stripLeadingZeroes2.length > 32) {
                throw new RuntimeException("yBytes is " + stripLeadingZeroes2.length + " which is unexpected");
            }
            for (int i2 = 0; i2 < 32 - stripLeadingZeroes2.length; i2++) {
                byteArrayOutputStream.write(0);
            }
            byteArrayOutputStream.write(stripLeadingZeroes2);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004e A[Catch: InvalidKeyException -> 0x005c, LOOP:0: B:8:0x003c->B:10:0x004e, LOOP_END, TryCatch #0 {InvalidKeyException -> 0x005c, blocks: (B:20:0x000e, B:23:0x0012, B:7:0x0029, B:8:0x003c, B:10:0x004e, B:12:0x0057, B:6:0x001b), top: B:19:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0057 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] computeHkdf(java.lang.String r4, byte[] r5, byte[] r6, byte[] r7, int r8) {
        /*
            javax.crypto.Mac r0 = javax.crypto.Mac.getInstance(r4)     // Catch: java.security.NoSuchAlgorithmException -> L6e
            int r1 = r0.getMacLength()
            int r1 = r1 * 255
            if (r8 > r1) goto L65
            if (r6 == 0) goto L1b
            int r1 = r6.length     // Catch: java.security.InvalidKeyException -> L5c
            if (r1 != 0) goto L12
            goto L1b
        L12:
            javax.crypto.spec.SecretKeySpec r1 = new javax.crypto.spec.SecretKeySpec     // Catch: java.security.InvalidKeyException -> L5c
            r1.<init>(r6, r4)     // Catch: java.security.InvalidKeyException -> L5c
            r0.init(r1)     // Catch: java.security.InvalidKeyException -> L5c
            goto L29
        L1b:
            javax.crypto.spec.SecretKeySpec r6 = new javax.crypto.spec.SecretKeySpec     // Catch: java.security.InvalidKeyException -> L5c
            int r1 = r0.getMacLength()     // Catch: java.security.InvalidKeyException -> L5c
            byte[] r1 = new byte[r1]     // Catch: java.security.InvalidKeyException -> L5c
            r6.<init>(r1, r4)     // Catch: java.security.InvalidKeyException -> L5c
            r0.init(r6)     // Catch: java.security.InvalidKeyException -> L5c
        L29:
            byte[] r5 = r0.doFinal(r5)     // Catch: java.security.InvalidKeyException -> L5c
            byte[] r6 = new byte[r8]     // Catch: java.security.InvalidKeyException -> L5c
            javax.crypto.spec.SecretKeySpec r1 = new javax.crypto.spec.SecretKeySpec     // Catch: java.security.InvalidKeyException -> L5c
            r1.<init>(r5, r4)     // Catch: java.security.InvalidKeyException -> L5c
            r0.init(r1)     // Catch: java.security.InvalidKeyException -> L5c
            r4 = 0
            byte[] r5 = new byte[r4]     // Catch: java.security.InvalidKeyException -> L5c
            r1 = 1
            r2 = r4
        L3c:
            r0.update(r5)     // Catch: java.security.InvalidKeyException -> L5c
            r0.update(r7)     // Catch: java.security.InvalidKeyException -> L5c
            byte r5 = (byte) r1     // Catch: java.security.InvalidKeyException -> L5c
            r0.update(r5)     // Catch: java.security.InvalidKeyException -> L5c
            byte[] r5 = r0.doFinal()     // Catch: java.security.InvalidKeyException -> L5c
            int r3 = r5.length     // Catch: java.security.InvalidKeyException -> L5c
            int r3 = r3 + r2
            if (r3 >= r8) goto L57
            int r3 = r5.length     // Catch: java.security.InvalidKeyException -> L5c
            java.lang.System.arraycopy(r5, r4, r6, r2, r3)     // Catch: java.security.InvalidKeyException -> L5c
            int r3 = r5.length     // Catch: java.security.InvalidKeyException -> L5c
            int r2 = r2 + r3
            int r1 = r1 + 1
            goto L3c
        L57:
            int r8 = r8 - r2
            java.lang.System.arraycopy(r5, r4, r6, r2, r8)     // Catch: java.security.InvalidKeyException -> L5c
            return r6
        L5c:
            r4 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.String r6 = "Error MACing"
            r5.<init>(r6, r4)
            throw r5
        L65:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.String r5 = "size too large"
            r4.<init>(r5)
            throw r4
        L6e:
            r5 = move-exception
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "No such algorithm: "
            r7.<init>(r8)
            r7.append(r4)
            java.lang.String r4 = r7.toString()
            r6.<init>(r4, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.identity.Util.computeHkdf(java.lang.String, byte[], byte[], byte[], int):byte[]");
    }

    private Util() {
    }
}
