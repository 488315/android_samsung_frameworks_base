package android.security.identity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
            byte[] bArrStripLeadingZeroes = stripLeadingZeroes(affineX.toByteArray());
            if (bArrStripLeadingZeroes.length > 32) {
                throw new RuntimeException("xBytes is " + bArrStripLeadingZeroes.length + " which is unexpected");
            }
            for (int i = 0; i < 32 - bArrStripLeadingZeroes.length; i++) {
                byteArrayOutputStream.write(0);
            }
            byteArrayOutputStream.write(bArrStripLeadingZeroes);
            byte[] bArrStripLeadingZeroes2 = stripLeadingZeroes(affineY.toByteArray());
            if (bArrStripLeadingZeroes2.length > 32) {
                throw new RuntimeException("yBytes is " + bArrStripLeadingZeroes2.length + " which is unexpected");
            }
            for (int i2 = 0; i2 < 32 - bArrStripLeadingZeroes2.length; i2++) {
                byteArrayOutputStream.write(0);
            }
            byteArrayOutputStream.write(bArrStripLeadingZeroes2);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    public static byte[] computeHkdf(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, int i) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Mac mac = Mac.getInstance(str);
            if (i > mac.getMacLength() * 255) {
                throw new RuntimeException("size too large");
            }
            if (bArr2 != null) {
                try {
                    if (bArr2.length == 0) {
                        mac.init(new SecretKeySpec(new byte[mac.getMacLength()], str));
                    } else {
                        mac.init(new SecretKeySpec(bArr2, str));
                    }
                } catch (InvalidKeyException e) {
                    throw new RuntimeException("Error MACing", e);
                }
            } else {
                mac.init(new SecretKeySpec(new byte[mac.getMacLength()], str));
            }
            byte[] bArr4 = new byte[i];
            mac.init(new SecretKeySpec(mac.doFinal(bArr), str));
            byte[] bArrDoFinal = new byte[0];
            int i2 = 1;
            int length = 0;
            while (true) {
                mac.update(bArrDoFinal);
                mac.update(bArr3);
                mac.update((byte) i2);
                bArrDoFinal = mac.doFinal();
                if (bArrDoFinal.length + length < i) {
                    System.arraycopy(bArrDoFinal, 0, bArr4, length, bArrDoFinal.length);
                    length += bArrDoFinal.length;
                    i2++;
                } else {
                    System.arraycopy(bArrDoFinal, 0, bArr4, length, i - length);
                    return bArr4;
                }
            }
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("No such algorithm: " + str, e2);
        }
    }

    private Util() {
    }
}
