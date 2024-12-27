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
import java.security.KeyPair;
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

/* loaded from: classes5.dex */
public class SemServiceTools {
    private static final String TAG = "SEC_ESE_ServiceTools";
    public static final char[] HEX_CHARS = {
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        DateFormat.CAPITAL_AM_PM,
        'B',
        'C',
        'D',
        DateFormat.DAY,
        'F'
    };
    private static final byte[] x_cord = {
        71,
        SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT,
        SprAttributeBase.TYPE_SHADOW,
        Byte.MAX_VALUE,
        -117,
        -100,
        18,
        SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT,
        Byte.MIN_VALUE,
        -115,
        82,
        102,
        MidiConstants.STATUS_NOTE_ON,
        -39,
        70,
        106,
        5,
        SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33,
        60,
        2,
        -88,
        62,
        85,
        57,
        MidiConstants.STATUS_PITCH_BEND,
        MidiConstants.STATUS_NOTE_ON,
        21,
        SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60,
        -114,
        -115,
        -9,
        110
    };
    private static final byte[] y_cord = {
        18,
        15,
        -54,
        -43,
        4,
        126,
        MidiConstants.STATUS_MIDI_TIME_CODE,
        -95,
        -43,
        106,
        SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33,
        SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN,
        78,
        -85,
        -30,
        -124,
        -16,
        111,
        -40,
        -45,
        -104,
        SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT,
        25,
        -81,
        -52,
        SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN,
        117,
        100,
        -61,
        -83,
        -109,
        56
    };

    public static String getHexString(byte[] in) {
        StringBuilder sb = new StringBuilder();
        for (byte b : in) {
            sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getHexString(byte[] in, int startPoint, int index) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index; i++) {
            sb.append(Integer.toString((in[i + startPoint] & 255) + 256, 16).substring(1));
        }
        return sb.toString();
    }

    public static String bytesToHex(byte[] data) {
        if (data != null) {
            char[] chars = new char[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                chars[i * 2] = HEX_CHARS[(data[i] & 240) >>> 4];
                chars[(i * 2) + 1] = HEX_CHARS[data[i] & 15];
            }
            return new String(chars);
        }
        return null;
    }

    public static String byteToHex(byte data) {
        char[] array = {HEX_CHARS[(data >> 4) & 15], HEX_CHARS[data & 15]};
        return new String(array);
    }

    public static byte[] hexToBytes(String str) {
        if (str == null || str.length() < 2) {
            return null;
        }
        int len = str.length() / 2;
        byte[] buffer = new byte[len];
        for (int i = 0; i < len; i++) {
            buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
        }
        return buffer;
    }

    public static String readFileBytes(Path paths) {
        try {
            byte[] fileData = Files.readAllBytes(paths);
            return bytesToHex(fileData);
        } catch (IOException e) {
            Log.e(TAG, "IOException : " + e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "Exception : " + e2);
            return null;
        }
    }

    public static boolean ccmVerify(byte[] message, byte[] rawSignature) {
        boolean ret = false;
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
        if (message != null && message.length >= 1) {
            if (rawSignature != null && rawSignature.length == 64) {
                KeyPairGenerator kGen =
                        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC);
                ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
                kGen.initialize(ecSpec);
                KeyPair remoteKeyPair = kGen.generateKeyPair();
                ECPublicKey pubKey =
                        decodeECPublicKey(
                                ((ECPublicKey) remoteKeyPair.getPublic()).getParams(),
                                x_cord,
                                y_cord);
                Signature ecdsa = Signature.getInstance("SHA256withECDSA");
                byte[] signature = getAsnSignature(rawSignature);
                ecdsa.initVerify(pubKey);
                ecdsa.update(message);
                ret = ecdsa.verify(signature);
                Log.i(TAG, "verify end : " + ret);
                return ret;
            }
            Log.e(TAG, "signature is invalid");
            return false;
        }
        Log.e(TAG, "message is invalid");
        return false;
    }

    private static ECPublicKey decodeECPublicKey(ECParameterSpec params, byte[] x1, byte[] y1)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger x = new BigInteger(1, x1);
        BigInteger y = new BigInteger(1, y1);
        ECPoint w = new ECPoint(x, y);
        ECPublicKeySpec otherKeySpec = new ECPublicKeySpec(w, params);
        KeyFactory keyFactory = KeyFactory.getInstance(KeyProperties.KEY_ALGORITHM_EC);
        return (ECPublicKey) keyFactory.generatePublic(otherKeySpec);
    }

    private static byte[] getAsnSignature(byte[] rawSig) {
        byte[] rawSig_r = new byte[32];
        byte[] rawSig_s = new byte[32];
        try {
            System.arraycopy(rawSig, 0, rawSig_r, 0, 32);
            System.arraycopy(rawSig, 32, rawSig_s, 0, 32);
            int numOfPrefixZeroBytes = 0;
            while (rawSig_r[numOfPrefixZeroBytes] == 0) {
                numOfPrefixZeroBytes++;
            }
            byte[] sig_r = new byte[rawSig_r.length - numOfPrefixZeroBytes];
            System.arraycopy(
                    rawSig_r,
                    numOfPrefixZeroBytes,
                    sig_r,
                    0,
                    rawSig_r.length - numOfPrefixZeroBytes);
            int numOfPrefixZeroBytes2 = 0;
            while (rawSig_s[numOfPrefixZeroBytes2] == 0) {
                numOfPrefixZeroBytes2++;
            }
            byte[] sig_s = new byte[rawSig_s.length - numOfPrefixZeroBytes2];
            System.arraycopy(
                    rawSig_s,
                    numOfPrefixZeroBytes2,
                    sig_s,
                    0,
                    rawSig_s.length - numOfPrefixZeroBytes2);
            if ((sig_r[0] & 255) > 127) {
                sig_r = concatenate((byte) 0, sig_r);
            }
            if ((sig_s[0] & 255) > 127) {
                sig_s = concatenate((byte) 0, sig_s);
            }
            byte[] sig =
                    concatenate(
                            concatenate(
                                    concatenate(
                                            concatenate(
                                                    concatenate((byte) 2, (byte) sig_r.length),
                                                    sig_r),
                                            (byte) 2),
                                    (byte) sig_s.length),
                            sig_s);
            byte[] sig2 =
                    concatenate(
                            SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
                            concatenate((byte) sig.length, sig));
            Log.d(TAG, "raw: " + bytesToHex(rawSig));
            Log.d(TAG, "encoded: " + bytesToHex(sig2));
            return sig2;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    private static byte[] concatenate(byte[] a, byte[] b) {
        byte[] a2;
        byte[] b2;
        if (a == null) {
            a2 = new byte[0];
        } else {
            a2 = a;
        }
        if (b == null) {
            b2 = new byte[0];
        } else {
            b2 = b;
        }
        byte[] c = new byte[a2.length + b2.length];
        if (a2.length > 0) {
            System.arraycopy(a2, 0, c, 0, a2.length);
        }
        if (b2.length > 0) {
            System.arraycopy(b2, 0, c, a2.length, b2.length);
        }
        return c;
    }

    private static byte[] concatenate(byte[] a, byte b) {
        byte[] a2;
        if (a == null) {
            a2 = new byte[0];
        } else {
            a2 = a;
        }
        byte[] b2 = {b};
        byte[] c = new byte[a2.length + b2.length];
        if (a2.length > 0) {
            System.arraycopy(a2, 0, c, 0, a2.length);
        }
        System.arraycopy(b2, 0, c, a2.length, b2.length);
        return c;
    }

    private static byte[] concatenate(byte a, byte[] b) {
        byte[] b2;
        byte[] a2 = {a};
        if (b == null) {
            b2 = new byte[0];
        } else {
            b2 = b;
        }
        byte[] c = new byte[a2.length + b2.length];
        System.arraycopy(a2, 0, c, 0, a2.length);
        if (b2.length > 0) {
            System.arraycopy(b2, 0, c, a2.length, b2.length);
        }
        return c;
    }

    private static byte[] concatenate(byte a, byte b) {
        byte[] a2 = {a};
        byte[] b2 = {b};
        byte[] c = new byte[a2.length + b2.length];
        System.arraycopy(a2, 0, c, 0, a2.length);
        System.arraycopy(b2, 0, c, a2.length, b2.length);
        return c;
    }

    public static int checkLength(byte[] ccmData, int offset) {
        if ((ccmData[offset] & 255) < 128) {
            int finalSize = ccmData[offset];
            return finalSize;
        }
        if (ccmData[offset] == -127) {
            int finalSize2 = ccmData[offset + 1] & 255;
            return finalSize2;
        }
        if (ccmData[offset] == -126) {
            int finalSize3 = ((ccmData[offset + 1] & 255) << 8) + (ccmData[offset + 2] & 255);
            return finalSize3;
        }
        if (ccmData[offset] == -125) {
            int finalSize4 =
                    ((ccmData[offset + 1] & 255) << 16)
                            + ((ccmData[offset + 2] & 255) << 8)
                            + (ccmData[offset + 3] & 255);
            return finalSize4;
        }
        Log.e(TAG, "Script Size Check error : -1");
        return -1;
    }
}
