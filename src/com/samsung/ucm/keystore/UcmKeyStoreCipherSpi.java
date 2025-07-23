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

/* loaded from: classes6.dex */
public abstract class UcmKeyStoreCipherSpi extends CipherSpi {
    private static final String AES_ALGORITHM = "AES";
    private static final String GCM_MODE = "GCM";
    static final int INVALID_PADDING = -1;
    static final int ISO9797_M2 = 4;
    private static String KEY_EXTRA_AAD = "extra_aad";
    private static String KEY_EXTRA_IV = "extra_iv";
    private static String KEY_EXTRA_TAG_LEN = "extra_tag_length";
    static final int NO_PADDING = 1;
    static final int PKCS1_OAEP_PADDING = 3;
    static final int PKCS1_PADDING = 2;
    private static final String RSA_ALGORITHM = "RSA";
    private static final String TAG = "UcmKeyStoreCipherSpi";
    private final String mAlgorithm;
    private int mTagLength;
    private UcmKeyStoreGenericCipher mUcmGenericCipher;
    private boolean mIsDoFinalCalled = false;
    boolean mEncrypting = false;
    private byte[] mIV = null;
    private byte[] mAAD = new byte[0];

    void doCryptoInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException, InvalidKeyException {
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    UcmKeyStoreCipherSpi(int i, String str) {
        this.mAlgorithm = str;
        String upperCase = str.split("/")[0].toUpperCase();
        if ("AES".equals(upperCase)) {
            this.mUcmGenericCipher = new UcmKeyStoreAESCipherSpi(i);
        } else if ("RSA".equals(upperCase)) {
            this.mUcmGenericCipher = new UcmKeyStoreRSACipherSpi(i);
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
        this.mUcmGenericCipher.setMode(str);
    }

    @Override // javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        this.mUcmGenericCipher.setPadding(str);
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetOutputSize(int i) {
        return this.mUcmGenericCipher.getModulusSize();
    }

    boolean isInitialized() {
        return this.mUcmGenericCipher.getKey() != null;
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        if (!this.mIsDoFinalCalled && this.mEncrypting) {
            throw new UnsupportedOperationException("getIV can be supported after performing doFinal");
        }
        return this.mIV;
    }

    void engineInitInternal(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        parseEncryptionMode(i);
        if (key == null) {
            throw new InvalidKeyException("Key is null");
        }
        if (algorithmParameterSpec != null) {
            parseParameterSpec(algorithmParameterSpec);
        }
        this.mUcmGenericCipher.init(key);
    }

    private void parseParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        byte[] iv;
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
        } else {
            iv = algorithmParameterSpec instanceof GCMParameterSpec ? ((GCMParameterSpec) algorithmParameterSpec).getIV() : null;
        }
        if (this.mEncrypting) {
            if (iv != null && iv.length > 0) {
                throw new InvalidAlgorithmParameterException("Caller-provided IV not permitted");
            }
        } else {
            this.mIV = iv;
            if (algorithmParameterSpec instanceof GCMParameterSpec) {
                this.mTagLength = ((GCMParameterSpec) algorithmParameterSpec).getTLen();
            }
        }
    }

    @Override // javax.crypto.CipherSpi
    public int engineGetKeySize(Key key) throws InvalidKeyException {
        throw new UnsupportedOperationException();
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            parseEncryptionMode(i);
            engineInitInternal(i, key, null);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException("Algorithm parameters rejected when none supplied", e);
        }
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        parseEncryptionMode(i);
        engineInitInternal(i, key, algorithmParameterSpec);
    }

    @Override // javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameters != null) {
            throw new InvalidAlgorithmParameterException("unknown param type: " + algorithmParameters.getClass().getName());
        }
        parseEncryptionMode(i);
        engineInitInternal(i, key, null);
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        this.mUcmGenericCipher.update(bArr, i, i2);
        return new byte[0];
    }

    @Override // javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        engineUpdate(bArr, i, i2);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    public void engineUpdateAAD(byte[] bArr, int i, int i2) {
        this.mAAD = Arrays.copyOfRange(bArr, i, i2);
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) throws IllegalBlockSizeException, BadPaddingException {
        byte[] ucmDecrypt;
        if (bArr != null) {
            engineUpdate(bArr, i, i2);
        }
        byte[] doFinal = this.mUcmGenericCipher.doFinal();
        IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
        if (service == null) {
            throw new IllegalBlockSizeException("failed to connect ucm service");
        }
        Bundle bundle = new Bundle();
        if (!this.mEncrypting) {
            bundle.putByteArray(KEY_EXTRA_IV, this.mIV);
        }
        if ("GCM".equals(this.mAlgorithm.split("/")[1].toUpperCase())) {
            bundle.putByteArray(KEY_EXTRA_AAD, this.mAAD);
            bundle.putInt(KEY_EXTRA_TAG_LEN, this.mTagLength);
        }
        boolean equals = "AES".equals(this.mAlgorithm.split("/")[0].toUpperCase());
        UcmKeyStoreKey key = this.mUcmGenericCipher.getKey();
        try {
            if (this.mEncrypting) {
                ucmDecrypt = service.ucmEncrypt(key.getAlias(), doFinal, this.mAlgorithm, bundle);
                if (equals && ucmDecrypt != null) {
                    ucmDecrypt = parseEncryptedMessage(ucmDecrypt);
                }
            } else {
                ucmDecrypt = service.ucmDecrypt(key.getAlias(), doFinal, this.mAlgorithm, bundle);
                if (equals && ucmDecrypt != null) {
                    ucmDecrypt = parseDecryptedMessage(ucmDecrypt);
                }
            }
            if (ucmDecrypt == null) {
                throw new IllegalBlockSizeException("output is null");
            }
            this.mIsDoFinalCalled = true;
            return ucmDecrypt;
        } catch (RemoteException e) {
            Log.e(TAG, "Remote Exception " + e);
            throw new IllegalBlockSizeException("RemoteException");
        }
    }

    private byte[] parseEncryptedMessage(byte[] bArr) {
        if (bArr[0] == 1) {
            int i = bArr[1];
            byte[] bArr2 = new byte[i];
            this.mIV = bArr2;
            System.arraycopy(bArr, 2, bArr2, 0, i);
            int i2 = (bArr[2 + i] & 255) << 8;
            int i3 = i2 | (bArr[i + 3] & 255);
            byte[] bArr3 = new byte[i3];
            System.arraycopy(bArr, i + 4, bArr3, 0, i3);
            return bArr3;
        }
        this.mIV = new byte[0];
        return new byte[0];
    }

    private byte[] parseDecryptedMessage(byte[] bArr) {
        int i = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 2, bArr2, 0, i);
        return bArr2;
    }

    @Override // javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        byte[] engineDoFinal = engineDoFinal(bArr, i, i2);
        int length = engineDoFinal.length + i3;
        if (length > bArr2.length) {
            throw new ShortBufferException("output buffer is too small " + bArr2.length + " < " + length);
        }
        System.arraycopy(engineDoFinal, 0, bArr2, i3, engineDoFinal.length);
        return engineDoFinal.length;
    }

    @Override // javax.crypto.CipherSpi
    public byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        try {
            byte[] encoded = key.getEncoded();
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            IllegalBlockSizeException illegalBlockSizeException = new IllegalBlockSizeException();
            illegalBlockSizeException.initCause(e);
            throw illegalBlockSizeException;
        }
    }

    @Override // javax.crypto.CipherSpi
    public Key engineUnwrap(byte[] bArr, String str, int i) throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            byte[] engineDoFinal = engineDoFinal(bArr, 0, bArr.length);
            if (i == 1) {
                return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(engineDoFinal));
            }
            if (i == 2) {
                return KeyFactory.getInstance(str).generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
            }
            if (i == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            throw new UnsupportedOperationException("wrappedKeyType == " + i);
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeyException(e);
        } catch (BadPaddingException e2) {
            throw new InvalidKeyException(e2);
        } catch (IllegalBlockSizeException e3) {
            throw new InvalidKeyException(e3);
        }
    }

    private void parseEncryptionMode(int i) throws InvalidParameterException {
        if (i == 1 || i == 3) {
            this.mEncrypting = true;
        } else if (i == 2 || i == 4) {
            this.mEncrypting = false;
        } else {
            throw new InvalidParameterException("Unsupported opmode " + i);
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
