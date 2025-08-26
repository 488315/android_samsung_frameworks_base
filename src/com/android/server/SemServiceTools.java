package com.android.server;

import android.security.keystore.KeyProperties;
import android.text.format.DateFormat;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;

/* loaded from: classes6.dex */
public class SemServiceTools {
    private static final String TAG = "SEC_ESE_ServiceTools";
    public static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', DateFormat.DAY, 'F'};
    private static final byte[] x_cord = {71, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, SprAttributeBase.TYPE_SHADOW, Byte.MAX_VALUE, -117, -100, 18, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, Byte.MIN_VALUE, -115, 82, 102, MidiConstants.STATUS_NOTE_ON, -39, 70, 106, 5, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 60, 2, -88, 62, 85, 57, MidiConstants.STATUS_PITCH_BEND, MidiConstants.STATUS_NOTE_ON, 21, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -114, -115, -9, 110};
    private static final byte[] y_cord = {18, 15, -54, -43, 4, 126, MidiConstants.STATUS_MIDI_TIME_CODE, -95, -43, 106, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, 78, -85, -30, -124, -16, 111, -40, -45, -104, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, 25, -81, -52, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 117, 100, -61, -83, -109, 56};

    public static String getHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getHexString(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(Integer.toString((bArr[i3 + i] & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    public static String bytesToHex(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            char[] cArr2 = HEX_CHARS;
            byte b = bArr[i];
            cArr[i2] = cArr2[(b & 240) >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    public static String byteToHex(byte b) {
        char[] cArr = HEX_CHARS;
        return new String(new char[]{cArr[(b >> 4) & 15], cArr[b & 15]});
    }

    public static byte[] hexToBytes(String str) {
        if (str == null || str.length() < 2) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static String readFileBytes(Path path) {
        try {
            return bytesToHex(Files.readAllBytes(path));
        } catch (IOException e) {
            Log.e(TAG, "IOException : " + e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "Exception : " + e2);
            return null;
        }
    }

    public static boolean ccmVerify(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, InvalidAlgorithmParameterException {
        boolean zVerify = false;
        try {
            Log.i(TAG, "verify start");
        } catch (NoClassDefFoundError e) {
            Log.e(TAG, "NCDFE " + e);
        } catch (InvalidAlgorithmParameterException e2) {
            Log.e(TAG, "IAPE " + e2);
        } catch (InvalidKeyException e3) {
            Log.e(TAG, "IKE " + e3);
        } catch (NoSuchAlgorithmException e4) {
            Log.e(TAG, "NSAE " + e4);
        } catch (SignatureException e5) {
            Log.e(TAG, "SE " + e5);
        } catch (InvalidKeySpecException e6) {
            Log.e(TAG, "IKSE " + e6);
        }
        if (bArr != null && bArr.length >= 1) {
            if (bArr2 != null && bArr2.length == 64) {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC);
                keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
                ECPublicKey eCPublicKeyDecodeECPublicKey = decodeECPublicKey(((ECPublicKey) keyPairGenerator.generateKeyPair().getPublic()).getParams(), x_cord, y_cord);
                Signature signature = Signature.getInstance("SHA256withECDSA");
                byte[] asnSignature = getAsnSignature(bArr2);
                signature.initVerify(eCPublicKeyDecodeECPublicKey);
                signature.update(bArr);
                zVerify = signature.verify(asnSignature);
                Log.i(TAG, "verify end : " + zVerify);
                return zVerify;
            }
            Log.e(TAG, "signature is invalid");
            return false;
        }
        Log.e(TAG, "message is invalid");
        return false;
    }

    private static ECPublicKey decodeECPublicKey(ECParameterSpec eCParameterSpec, byte[] bArr, byte[] bArr2) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return (ECPublicKey) KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC).generatePublic(new ECPublicKeySpec(new ECPoint(new BigInteger(1, bArr), new BigInteger(1, bArr2)), eCParameterSpec));
    }

    private static byte[] getAsnSignature(byte[] bArr) {
        byte[] bArr2 = new byte[32];
        byte[] bArr3 = new byte[32];
        try {
            System.arraycopy(bArr, 0, bArr2, 0, 32);
            System.arraycopy(bArr, 32, bArr3, 0, 32);
            int i = 0;
            while (bArr2[i] == 0) {
                i++;
            }
            int i2 = 32 - i;
            byte[] bArrConcatenate = new byte[i2];
            System.arraycopy(bArr2, i, bArrConcatenate, 0, i2);
            int i3 = 0;
            while (bArr3[i3] == 0) {
                i3++;
            }
            int i4 = 32 - i3;
            byte[] bArrConcatenate2 = new byte[i4];
            System.arraycopy(bArr3, i3, bArrConcatenate2, 0, i4);
            if ((bArrConcatenate[0] & 255) > 127) {
                bArrConcatenate = concatenate((byte) 0, bArrConcatenate);
            }
            if ((bArrConcatenate2[0] & 255) > 127) {
                bArrConcatenate2 = concatenate((byte) 0, bArrConcatenate2);
            }
            byte[] bArrConcatenate3 = concatenate(concatenate(concatenate(concatenate(concatenate((byte) 2, (byte) bArrConcatenate.length), bArrConcatenate), (byte) 2), (byte) bArrConcatenate2.length), bArrConcatenate2);
            byte[] bArrConcatenate4 = concatenate(SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, concatenate((byte) bArrConcatenate3.length, bArrConcatenate3));
            Log.d(TAG, "raw: " + bytesToHex(bArr));
            Log.d(TAG, "encoded: " + bytesToHex(bArrConcatenate4));
            return bArrConcatenate4;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    private static byte[] concatenate(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        if (bArr2 == null) {
            bArr2 = new byte[0];
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        if (bArr.length > 0) {
            System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        }
        if (bArr2.length > 0) {
            System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        }
        return bArr3;
    }

    private static byte[] concatenate(byte[] bArr, byte b) {
        if (bArr == null) {
            bArr = new byte[0];
        }
        byte[] bArr2 = {b};
        byte[] bArr3 = new byte[bArr.length + 1];
        if (bArr.length > 0) {
            System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        }
        System.arraycopy(bArr2, 0, bArr3, bArr.length, 1);
        return bArr3;
    }

    private static byte[] concatenate(byte b, byte[] bArr) {
        byte[] bArr2 = {b};
        if (bArr == null) {
            bArr = new byte[0];
        }
        byte[] bArr3 = new byte[bArr.length + 1];
        System.arraycopy(bArr2, 0, bArr3, 0, 1);
        if (bArr.length > 0) {
            System.arraycopy(bArr, 0, bArr3, 1, bArr.length);
        }
        return bArr3;
    }

    private static byte[] concatenate(byte b, byte b2) {
        byte[] bArr = {b};
        byte[] bArr2 = {b2};
        byte[] bArr3 = new byte[2];
        System.arraycopy(bArr, 0, bArr3, 0, 1);
        System.arraycopy(bArr2, 0, bArr3, 1, 1);
        return bArr3;
    }

    public static int checkLength(byte[] bArr, int i) {
        int i2;
        byte b;
        byte b2 = bArr[i];
        if ((b2 & 255) < 128) {
            return b2;
        }
        if (b2 == -127) {
            return bArr[i + 1] & 255;
        }
        if (b2 == -126) {
            i2 = (bArr[i + 1] & 255) << 8;
            b = bArr[i + 2];
        } else if (b2 == -125) {
            i2 = ((bArr[i + 1] & 255) << 16) + ((bArr[i + 2] & 255) << 8);
            b = bArr[i + 3];
        } else {
            Log.e(TAG, "Script Size Check error : -1");
            return -1;
        }
        return i2 + (b & 255);
    }
}
