package com.samsung.ucm.keystore;

import android.os.Bundle;
import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;

import com.samsung.android.security.mdf.MdfUtils;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class UcmKeyStoreCipherSpi extends CipherSpi {
    private static final String AES_ALGORITHM = "AES";
    private static final String GCM_MODE = "GCM";
    static final int INVALID_PADDING = -1;
    static final int ISO9797_M2 = 4;
    static final int NO_PADDING = 1;
    static final int PKCS1_OAEP_PADDING = 3;
    static final int PKCS1_PADDING = 2;
    private static final String RSA_ALGORITHM = "RSA";
    private static final String TAG = "UcmKeyStoreCipherSpi";
    private final String mAlgorithm;
    private int mTagLength;
    private UcmKeyStoreGenericCipher mUcmGenericCipher;
    private static String KEY_EXTRA_IV = "extra_iv";
    private static String KEY_EXTRA_AAD = "extra_aad";
    private static String KEY_EXTRA_TAG_LEN = "extra_tag_length";
    private boolean mIsDoFinalCalled = false;
    boolean mEncrypting = false;
    private byte[] mIV = null;
    private byte[] mAAD = new byte[0];

    UcmKeyStoreCipherSpi(int padding, String algorithm) {
        this.mAlgorithm = algorithm;
        String parsedAlgorithm = algorithm.split("/")[0].toUpperCase();
        if ("AES".equals(parsedAlgorithm)) {
            this.mUcmGenericCipher = new UcmKeyStoreAESCipherSpi(padding);
        } else if ("RSA".equals(parsedAlgorithm)) {
            this.mUcmGenericCipher = new UcmKeyStoreRSACipherSpi(padding);
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineSetMode(String mode) throws NoSuchAlgorithmException {
        this.mUcmGenericCipher.setMode(mode);
    }

    @Override // javax.crypto.CipherSpi
    public void engineSetPadding(String padding) throws NoSuchPaddingException {
        this.mUcmGenericCipher.setPadding(padding);
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetOutputSize(int inputLen) {
        return this.mUcmGenericCipher.getModulusSize();
    }

    boolean isInitialized() {
        return this.mUcmGenericCipher.getKey() != null;
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        if (!this.mIsDoFinalCalled && this.mEncrypting) {
            throw new UnsupportedOperationException(
                    "getIV can be supported after performing doFinal");
        }
        return this.mIV;
    }

    @Override // javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    void doCryptoInit(AlgorithmParameterSpec spec)
            throws InvalidAlgorithmParameterException, InvalidKeyException {}

    void engineInitInternal(int opmode, Key key, AlgorithmParameterSpec spec)
            throws InvalidKeyException, InvalidAlgorithmParameterException {
        parseEncryptionMode(opmode);
        if (key == null) {
            throw new InvalidKeyException("Key is null");
        }
        if (spec != null) {
            parseParameterSpec(spec);
        }
        this.mUcmGenericCipher.init(key);
    }

    private void parseParameterSpec(AlgorithmParameterSpec spec)
            throws InvalidAlgorithmParameterException {
        byte[] iv = null;
        if (spec instanceof IvParameterSpec) {
            iv = ((IvParameterSpec) spec).getIV();
        } else if (spec instanceof GCMParameterSpec) {
            iv = ((GCMParameterSpec) spec).getIV();
        }
        if (this.mEncrypting) {
            if (iv != null && iv.length > 0) {
                throw new InvalidAlgorithmParameterException("Caller-provided IV not permitted");
            }
        } else {
            this.mIV = iv;
            if (spec instanceof GCMParameterSpec) {
                this.mTagLength = ((GCMParameterSpec) spec).getTLen();
            }
        }
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetKeySize(Key key) throws InvalidKeyException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int opmode, Key key, SecureRandom random) throws InvalidKeyException {
        try {
            parseEncryptionMode(opmode);
            engineInitInternal(opmode, key, null);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException("Algorithm parameters rejected when none supplied", e);
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random)
            throws InvalidKeyException, InvalidAlgorithmParameterException {
        parseEncryptionMode(opmode);
        engineInitInternal(opmode, key, params);
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random)
            throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (params != null) {
            throw new InvalidAlgorithmParameterException(
                    "unknown param type: " + params.getClass().getName());
        }
        parseEncryptionMode(opmode);
        engineInitInternal(opmode, key, null);
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        this.mUcmGenericCipher.update(input, inputOffset, inputLen);
        return new byte[0];
    }

    @Override // javax.crypto.CipherSpi
    public int engineUpdate(
            byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
            throws ShortBufferException {
        engineUpdate(input, inputOffset, inputLen);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    public void engineUpdateAAD(byte[] src, int offset, int len) {
        this.mAAD = Arrays.copyOfRange(src, offset, len);
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
            throws IllegalBlockSizeException, BadPaddingException {
        byte[] output;
        if (input != null) {
            engineUpdate(input, inputOffset, inputLen);
        }
        byte[] tmpBuf = this.mUcmGenericCipher.doFinal();
        IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
        if (lService == null) {
            throw new IllegalBlockSizeException("failed to connect ucm service");
        }
        Bundle params = new Bundle();
        if (!this.mEncrypting) {
            params.putByteArray(KEY_EXTRA_IV, this.mIV);
        }
        if ("GCM".equals(this.mAlgorithm.split("/")[1].toUpperCase())) {
            params.putByteArray(KEY_EXTRA_AAD, this.mAAD);
            params.putInt(KEY_EXTRA_TAG_LEN, this.mTagLength);
        }
        boolean isAES = "AES".equals(this.mAlgorithm.split("/")[0].toUpperCase());
        UcmKeyStoreKey key = this.mUcmGenericCipher.getKey();
        try {
            if (this.mEncrypting) {
                output = lService.ucmEncrypt(key.getAlias(), tmpBuf, this.mAlgorithm, params);
                if (isAES && output != null) {
                    output = parseEncryptedMessage(output);
                }
            } else {
                output = lService.ucmDecrypt(key.getAlias(), tmpBuf, this.mAlgorithm, params);
                if (isAES && output != null) {
                    output = parseDecryptedMessage(output);
                }
            }
            if (output == null) {
                throw new IllegalBlockSizeException("output is null");
            }
            this.mIsDoFinalCalled = true;
            return output;
        } catch (RemoteException re) {
            Log.e(TAG, "Remote Exception " + re);
            throw new IllegalBlockSizeException("RemoteException");
        }
    }

    private byte[] parseEncryptedMessage(byte[] bArr) {
        int offset = 0 + 1;
        if (bArr[0] == 1) {
            int offset2 = offset + 1;
            int i = bArr[offset];
            this.mIV = new byte[i];
            System.arraycopy(bArr, offset2, this.mIV, 0, i);
            int offset3 = offset2 + i;
            int offset4 = offset3 + 1;
            int messageLength = ((bArr[offset3] & 255) << 8) | (bArr[offset4] & 255);
            byte[] cipherResult = new byte[messageLength];
            System.arraycopy(bArr, offset4 + 1, cipherResult, 0, messageLength);
            return cipherResult;
        }
        this.mIV = new byte[0];
        return new byte[0];
    }

    private byte[] parseDecryptedMessage(byte[] message) {
        int offset = 0 + 1;
        int offset2 = offset + 1;
        int messageLength = ((message[0] & 255) << 8) | (message[offset] & 255);
        byte[] cipherResult = new byte[messageLength];
        System.arraycopy(message, offset2, cipherResult, 0, messageLength);
        return cipherResult;
    }

    @Override // javax.crypto.CipherSpi
    public int engineDoFinal(
            byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
            throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        byte[] b = engineDoFinal(input, inputOffset, inputLen);
        int lastOffset = b.length + outputOffset;
        if (lastOffset > output.length) {
            throw new ShortBufferException(
                    "output buffer is too small " + output.length + " < " + lastOffset);
        }
        System.arraycopy(b, 0, output, outputOffset, b.length);
        return b.length;
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        try {
            byte[] encoded = key.getEncoded();
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            IllegalBlockSizeException newE = new IllegalBlockSizeException();
            newE.initCause(e);
            throw newE;
        }
    }

    @Override // javax.crypto.CipherSpi
    public Key engineUnwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType)
            throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            byte[] encoded = engineDoFinal(wrappedKey, 0, wrappedKey.length);
            switch (wrappedKeyType) {
                case 1:
                    KeyFactory keyFactory = KeyFactory.getInstance(wrappedKeyAlgorithm);
                    return keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
                case 2:
                    KeyFactory keyFactory2 = KeyFactory.getInstance(wrappedKeyAlgorithm);
                    return keyFactory2.generatePrivate(new PKCS8EncodedKeySpec(encoded));
                case 3:
                    return new SecretKeySpec(encoded, wrappedKeyAlgorithm);
                default:
                    throw new UnsupportedOperationException("wrappedKeyType == " + wrappedKeyType);
            }
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeyException(e);
        } catch (BadPaddingException e2) {
            throw new InvalidKeyException(e2);
        } catch (IllegalBlockSizeException e3) {
            throw new InvalidKeyException(e3);
        }
    }

    private void parseEncryptionMode(int mode) throws InvalidParameterException {
        if (mode == 1 || mode == 3) {
            this.mEncrypting = true;
        } else {
            if (mode == 2 || mode == 4) {
                this.mEncrypting = false;
                return;
            }
            throw new InvalidParameterException("Unsupported opmode " + mode);
        }
    }

    public static class PKCS1Padding extends UcmKeyStoreCipherSpi {
        public PKCS1Padding() {
            super(2, "RSA/ECB/PKCS1Padding");
        }
    }

    public static class OAEPWithSHA1AndMGF1Padding extends UcmKeyStoreCipherSpi {
        public OAEPWithSHA1AndMGF1Padding() {
            super(3, "RSA/ECB/OAEPPadding");
        }
    }

    public static class OAEPWithSHA224AndMGF1Padding extends UcmKeyStoreCipherSpi {
        public OAEPWithSHA224AndMGF1Padding() {
            super(3, "RSA/ECB/OAEPWithSHA-224AndMGF1Padding");
        }
    }

    public static class OAEPWithSHA256AndMGF1Padding extends UcmKeyStoreCipherSpi {
        public OAEPWithSHA256AndMGF1Padding() {
            super(3, "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        }
    }

    public static class OAEPWithSHA384AndMGF1Padding extends UcmKeyStoreCipherSpi {
        public OAEPWithSHA384AndMGF1Padding() {
            super(3, "RSA/ECB/OAEPWithSHA-384AndMGF1Padding");
        }
    }

    public static class OAEPWithSHA512AndMGF1Padding extends UcmKeyStoreCipherSpi {
        public OAEPWithSHA512AndMGF1Padding() {
            super(3, "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        }
    }

    public static class AesCbcNoPadding extends UcmKeyStoreCipherSpi {
        public AesCbcNoPadding() {
            super(1, "AES/CBC/NoPadding");
        }
    }

    public static class AesCbcIso9797M2 extends UcmKeyStoreCipherSpi {
        public AesCbcIso9797M2() {
            super(4, "AES/CBC/ISO9797-M2");
        }
    }

    public static class AesGcmNoPadding extends UcmKeyStoreCipherSpi {
        public AesGcmNoPadding() {
            super(1, MdfUtils.MDF_CIPHER_MODE);
        }
    }
}
