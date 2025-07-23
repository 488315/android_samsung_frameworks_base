package com.samsung.ucm.keystore;

import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.security.keystore.KeyProperties;
import android.util.Log;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.MacSpi;

/* loaded from: classes6.dex */
public class UcmKeyStoreMacSpi extends MacSpi {
    private static final String TAG = "UcmKeyStoreMacSpi";
    private final String mAlgorithm;
    private byte[] mInput;
    private UcmKeyStoreSecretKey mSecretKey;

    @Override // javax.crypto.MacSpi
    public int engineGetMacLength() {
        return 0;
    }

    UcmKeyStoreMacSpi(String str) {
        this.mAlgorithm = str;
    }

    @Override // javax.crypto.MacSpi
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (key == null) {
            throw new InvalidKeyException("Key is null");
        }
        if (!(key instanceof UcmKeyStoreSecretKey)) {
            throw new InvalidKeyException("Key type is not supported");
        }
        this.mSecretKey = (UcmKeyStoreSecretKey) key;
    }

    @Override // javax.crypto.MacSpi
    public void engineUpdate(byte b) {
        engineUpdate(new byte[]{b}, 0, 1);
    }

    @Override // javax.crypto.MacSpi
    public void engineUpdate(byte[] bArr, int i, int i2) {
        this.mInput = new byte[i2];
        this.mInput = Arrays.copyOfRange(bArr, i, i2 + i);
    }

    @Override // javax.crypto.MacSpi
    public void engineReset() {
        this.mInput = null;
    }

    @Override // javax.crypto.MacSpi
    public byte[] engineDoFinal() {
        IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
        if (service == null) {
            Log.e(TAG, "Failed to connect UCM service");
            return null;
        }
        try {
            return service.ucmMac(this.mSecretKey.getAlias(), this.mInput, this.mAlgorithm);
        } catch (RemoteException e) {
            Log.e(TAG, "Remote Exception " + e);
            return null;
        }
    }

    public static class HmacMD5 extends UcmKeyStoreMacSpi {
        public HmacMD5() {
            super("HmacMD5");
        }
    }

    public static class HmacSHA1 extends UcmKeyStoreMacSpi {
        public HmacSHA1() {
            super(KeyProperties.KEY_ALGORITHM_HMAC_SHA1);
        }
    }

    public static class HmacSHA224 extends UcmKeyStoreMacSpi {
        public HmacSHA224() {
            super(KeyProperties.KEY_ALGORITHM_HMAC_SHA224);
        }
    }

    public static class HmacSHA256 extends UcmKeyStoreMacSpi {
        public HmacSHA256() {
            super(KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
        }
    }

    public static class HmacSHA384 extends UcmKeyStoreMacSpi {
        public HmacSHA384() {
            super(KeyProperties.KEY_ALGORITHM_HMAC_SHA384);
        }
    }

    public static class HmacSHA512 extends UcmKeyStoreMacSpi {
        public HmacSHA512() {
            super(KeyProperties.KEY_ALGORITHM_HMAC_SHA512);
        }
    }
}
