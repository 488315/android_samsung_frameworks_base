package com.samsung.android.service.RemoteLockControl.KnoxGuard;

import android.app.ActivityManager;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Bundle;
import android.p009os.FactoryTest;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.SystemProperties;
import android.security.keystore.KeyProperties;
import android.util.Log;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.RemoteLockInfo;
import com.samsung.android.service.vaultkeeper.VaultKeeperManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public final class KnoxGuardVaultManager {
    private static final String BLINK_STATE = "Blink";
    private static final String CHECKING_STATE = "Checking";
    private static final String COMPLETED_STATE = "Completed";
    private static final int KGV_AES256_IV_SIZE = 16;
    private static final int KGV_AES256_KEY_SIZE = 32;
    private static final int KGV_ERR_CERTIFICATE = -3;
    private static final int KGV_ERR_CRYPTO_FUNCTION = -8;
    private static final int KGV_ERR_GENERAL = -1;
    private static final int KGV_ERR_INVALID_ARGUMENT = -2;
    private static final int KGV_ERR_INVALID_PASSCODE_FORMAT = -11;
    private static final int KGV_ERR_INVALID_TOKEN = -4;
    private static final int KGV_ERR_LOCKSCREEN = -9;
    private static final int KGV_ERR_MAX_FAILURE_COUNT_REACHED = -7;
    private static final int KGV_ERR_SERIALIZATION = -10;
    private static final int KGV_ERR_SERVER_RESULT_FAIL = -6;
    private static final int KGV_ERR_VAULTKEEPER = -5;
    private static final int KGV_FAILCOUNT_FOR_DELAY = 1;
    private static final int KGV_ID_SIZE = 40;
    private static final int KGV_KEY_SIZE = 32;
    private static final int KGV_MAX_LIFE_TIME = 9999;
    private static final int KGV_NONCE_FLAG_VERIFY = 2;
    private static final int KGV_NONCE_FLAG_WRITE = 1;
    private static final int KGV_NONCE_SIZE = 32;
    private static final int KGV_SHA256_SIZE = 32;
    private static final int KGV_SUCCESS = 0;
    private static final String LOCKED_STATE = "Locked";
    private static final String NORMAL_STATE = "Normal";
    private static final int OTP_LENGTH = 8;
    private static final String PRENORMAL_STATE = "Prenormal";
    private static final int SECURITY_ENHANCE_API_LEVEL_Q = 29;
    private static final String TAG = "KgvManager";
    private static boolean mCompleteUnlockingDone = false;
    private static Context mContext = null;
    private static CryptoManager mCrypto = null;
    private static final String mKgVaultName = "KnoxGuard";
    private static final String mKgVaultName2 = "KnoxGuard2";
    private static IKnoxGuardVaultListener mKnoxGuardVaultListener;
    private static ILockSettings mLockSettingsService;
    private static byte[] mServerCert;
    private static VaultKeeperManager mVkm;
    private static VaultKeeperManager mVkm2;
    IRemoteLockMonitorCallback mRemoteLockMonitorCallback = new IRemoteLockMonitorCallback.Stub() { // from class: com.samsung.android.service.RemoteLockControl.KnoxGuard.KnoxGuardVaultManager.1
        @Override // com.android.internal.widget.IRemoteLockMonitorCallback
        public void changeRemoteLockState(RemoteLockInfo data) throws RemoteException {
            Log.m94d(KnoxGuardVaultManager.TAG, "changeRemoteLockState data = " + data.lockType);
        }

        @Override // com.android.internal.widget.IRemoteLockMonitorCallback
        public int checkRemoteLockPassword(byte[] pass) {
            Log.m98i(KnoxGuardVaultManager.TAG, "checkRemoteLockPassword");
            String password = new String(pass, StandardCharsets.UTF_8);
            int failureCount = 0;
            try {
                if (KnoxGuardVaultManager.mIsSupportKg2) {
                    failureCount = KnoxGuardVaultManager.this.completeUnlocking(password);
                } else {
                    int failureCount2 = KnoxGuardVaultManager.this.getFailureCount();
                    failureCount = failureCount2 + 1;
                    if (KnoxGuardVaultManager.this.verifyteHOTPPasscode(password)) {
                        Log.m98i(KnoxGuardVaultManager.TAG, "[HOTP] passcode is correct!");
                        failureCount = KnoxGuardVaultManager.this.completeUnlocking(null);
                    } else {
                        Log.m96e(KnoxGuardVaultManager.TAG, "[HOTP] passcode is wrong!!! current failure count (" + failureCount + NavigationBarInflaterView.KEY_CODE_END);
                    }
                    if (!KnoxGuardVaultManager.this.setFailureCount(failureCount)) {
                        Log.m96e(KnoxGuardVaultManager.TAG, "Failed setFailureCount");
                    }
                }
                Log.m94d(KnoxGuardVaultManager.TAG, "KGvK failureCount : " + failureCount);
                if (failureCount == 0) {
                    if (KnoxGuardVaultManager.mKnoxGuardVaultListener != null) {
                        KnoxGuardVaultManager.mKnoxGuardVaultListener.onUnlockedByPasscode();
                    } else {
                        Log.m96e(KnoxGuardVaultManager.TAG, "KnoxGuardVaultListener is null, can't call onUnlockedByPasscode()");
                    }
                } else {
                    KnoxGuardVaultManager.this.setRemoteLockToLockscreen();
                }
            } catch (KnoxGuardVaultException e) {
                e.printStackTrace();
                if (!KnoxGuardVaultManager.mCompleteUnlockingDone) {
                    throw new RuntimeException("Error in KGV Manager internally");
                }
            }
            return failureCount;
        }
    };
    private static final int first_api_level = SystemProperties.getInt("ro.product.first_api_level", 0);
    private static boolean mIsSupportKg2 = false;
    private static byte[] mKgvKey = new byte[32];
    private static byte[] mKgvId = new byte[40];
    private static byte[] mNonceDev = new byte[32];
    private static byte[] mNonceDev2 = new byte[32];
    private static byte[] mNonceSvr = new byte[32];

    public KnoxGuardVaultManager(Context context) {
        setIsSupportKg2();
        setContext(context);
        setServerCert(null);
        if (mVkm == null) {
            setVkm(VaultKeeperManager.getInstance(mKgVaultName));
        }
        if (mIsSupportKg2 && mVkm2 == null) {
            setVkm2(VaultKeeperManager.getInstance(mKgVaultName2));
        }
        if (mCrypto == null) {
            setCrypto(new CryptoManager());
        }
        setLockSettingsService(null);
    }

    public KnoxGuardVaultManager(Context context, IKnoxGuardVaultListener kgvListener) {
        setIsSupportKg2();
        setContext(context);
        setServerCert(null);
        setVkm(VaultKeeperManager.getInstance(mKgVaultName));
        if (mIsSupportKg2 && mVkm2 == null) {
            setVkm2(VaultKeeperManager.getInstance(mKgVaultName2));
        }
        setCrypto(new CryptoManager());
        setKnoxGuardVaultListener(kgvListener);
        setLockSettingsService(null);
    }

    private void clearKgvData() {
        Arrays.fill(mKgvKey, (byte) 0);
        Arrays.fill(mKgvId, (byte) 0);
        Arrays.fill(mNonceDev, (byte) 0);
        Arrays.fill(mNonceDev2, (byte) 0);
        Arrays.fill(mNonceSvr, (byte) 0);
        byte[] bArr = mServerCert;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
            setServerCert(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void throwException(int kgvErrorCode, String msg) throws KnoxGuardVaultException {
        Log.m96e(TAG, NavigationBarInflaterView.SIZE_MOD_START + kgvErrorCode + NavigationBarInflaterView.SIZE_MOD_END + msg);
        throw new KnoxGuardVaultException(kgvErrorCode, msg);
    }

    private void parameterChecking(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        if (serverCert == null) {
            throwException(-2, "serverCert is null");
        } else {
            if (!mCrypto.verifyCertChain(serverCert)) {
                throwException(-3, "Failed to verify Certificate Chain");
            }
            setServerCert((byte[]) serverCert.clone());
        }
        if (nonceSvr != null && nonceSvr.length != 32) {
            throwException(-2, "nonceSvr size is wrong(" + nonceSvr.length + "), it should be 32");
        }
        if (kgvId != null && kgvId.length != 40) {
            throwException(-2, "kgvId size is wrong(" + kgvId.length + "), it should be 40");
        }
        if (nonceSvr != null) {
            System.arraycopy(nonceSvr, 0, mNonceSvr, 0, 32);
        }
        if (kgvId != null) {
            System.arraycopy(kgvId, 0, mKgvId, 0, 40);
        }
    }

    private byte[] makeDeviceMsg(byte[] serverCert, byte[] kgvKey, byte[] kgvId, byte[] nonceSvr, byte[] nonceDev, byte[] extra) throws KnoxGuardVaultException {
        int msgLength = kgvKey != null ? 0 + kgvKey.length : 0;
        if (kgvId != null) {
            msgLength += kgvId.length;
        }
        if (nonceSvr != null) {
            msgLength += nonceSvr.length;
        }
        if (nonceDev != null) {
            msgLength += nonceDev.length;
        }
        if (extra != null) {
            msgLength += extra.length;
        }
        byte[] msg = new byte[msgLength];
        int offset = 0;
        if (kgvKey != null) {
            try {
                System.arraycopy(kgvKey, 0, msg, 0, kgvKey.length);
                offset = 0 + kgvKey.length;
            } catch (KnoxGuardVaultException e) {
                throw e;
            }
        }
        if (kgvId != null) {
            System.arraycopy(kgvId, 0, msg, offset, kgvId.length);
            offset += kgvId.length;
        }
        if (nonceSvr != null) {
            System.arraycopy(nonceSvr, 0, msg, offset, nonceSvr.length);
            offset += nonceSvr.length;
        }
        if (nonceDev != null) {
            System.arraycopy(nonceDev, 0, msg, offset, nonceDev.length);
            offset += nonceDev.length;
        }
        if (extra != null) {
            System.arraycopy(extra, 0, msg, offset, extra.length);
            int length = offset + extra.length;
        }
        byte[] deviceMsg = encryptData(msg, serverCert);
        return deviceMsg;
    }

    private byte[] makeDeviceMsg(byte[] serverCert, byte[] kgvKey, byte[] kgvId, byte[] nonceSvr, byte[] nonceDev, byte[] extra, byte[] nonceDev2) throws KnoxGuardVaultException {
        int msgLength = kgvKey != null ? 0 + kgvKey.length : 0;
        if (kgvId != null) {
            msgLength += kgvId.length;
        }
        if (nonceSvr != null) {
            msgLength += nonceSvr.length;
        }
        if (nonceDev != null) {
            msgLength += nonceDev.length;
        }
        if (extra != null) {
            msgLength += extra.length;
        }
        if (nonceDev2 != null) {
            msgLength += nonceDev2.length;
        }
        byte[] msg = new byte[msgLength];
        int offset = 0;
        if (kgvKey != null) {
            try {
                System.arraycopy(kgvKey, 0, msg, 0, kgvKey.length);
                offset = 0 + kgvKey.length;
            } catch (KnoxGuardVaultException e) {
                throw e;
            }
        }
        if (kgvId != null) {
            System.arraycopy(kgvId, 0, msg, offset, kgvId.length);
            offset += kgvId.length;
        }
        if (nonceSvr != null) {
            System.arraycopy(nonceSvr, 0, msg, offset, nonceSvr.length);
            offset += nonceSvr.length;
        }
        if (nonceDev != null) {
            System.arraycopy(nonceDev, 0, msg, offset, nonceDev.length);
            offset += nonceDev.length;
        }
        if (extra != null) {
            System.arraycopy(extra, 0, msg, offset, extra.length);
            offset += extra.length;
        }
        if (nonceDev2 != null) {
            System.arraycopy(nonceDev2, 0, msg, offset, nonceDev2.length);
            int length = offset + nonceDev2.length;
        }
        byte[] deviceMsg = encryptData(msg, serverCert);
        return deviceMsg;
    }

    private byte[] makeResultDev() throws KnoxGuardVaultException {
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] state = mVkm.read(1);
            if (state == null) {
                throwException(-5, "Error from VaultKeeper (readState)");
            }
            byte[] resultDev = makeDeviceMsg(mServerCert, null, mKgvId, mNonceSvr, null, state);
            return resultDev;
        } catch (KnoxGuardVaultException e) {
            throw e;
        } catch (Exception e2) {
            e2.printStackTrace();
            throwException(-1, "Exception");
            return null;
        }
    }

    public byte[] encryptData(byte[] data, byte[] serverCert) throws KnoxGuardVaultException {
        Log.m98i(TAG, "encryptClientData");
        byte[] key = new byte[32];
        byte[] iv = new byte[16];
        byte[] keyAndIv = new byte[48];
        if (data == null || data.length == 0) {
            throwException(-2, "Invalid clientData");
        }
        try {
            try {
                parameterChecking(serverCert, null, null);
                mCrypto.getRandom(iv);
                mCrypto.getRandom(key);
                System.arraycopy(key, 0, keyAndIv, 0, 32);
                System.arraycopy(iv, 0, keyAndIv, 32, 16);
                byte[] cipherOfKeyAndIV = mCrypto.ecryptWithServerPubKey(keyAndIv, serverCert);
                byte[] cipherOfClientData = mCrypto.ecryptWithAES256CBC(data, key, iv);
                if (cipherOfKeyAndIV == null || cipherOfClientData == null) {
                    throwException(-8, "encryptData");
                }
                byte[] ciphertext = new byte[cipherOfKeyAndIV.length + cipherOfClientData.length];
                System.arraycopy(cipherOfKeyAndIV, 0, ciphertext, 0, cipherOfKeyAndIV.length);
                System.arraycopy(cipherOfClientData, 0, ciphertext, cipherOfKeyAndIV.length, cipherOfClientData.length);
                return ciphertext;
            } catch (KnoxGuardVaultException e) {
                throw e;
            }
        } finally {
            Arrays.fill(key, (byte) 0);
            Arrays.fill(iv, (byte) 0);
            Arrays.fill(keyAndIv, (byte) 0);
        }
    }

    public int setPolicy(byte[] policy, byte[] token) throws KnoxGuardVaultException {
        Log.m98i(TAG, "setPolicy");
        if (!mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        if (mVkm2 == null) {
            throwException(-5, "Error from VaultKeeper Manager(2) is null object");
        }
        try {
            int vaultResult = mVkm2.write(2, policy, token);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper2 (write/ " + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
            }
            return vaultResult;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public byte[] getPolicy() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getPolicy");
        if (!mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        if (mVkm2 == null) {
            throwException(-5, "Error from VaultKeeper Manager(2) is null object");
        }
        try {
            byte[] policy = mVkm2.read(2);
            if (policy == null) {
                throwException(-5, "Error from VaultKeeper (readData from Vault2)");
            }
            if (policy.length != 0) {
                return policy;
            }
            Log.m102w(TAG, "No policy in Vault2");
            return null;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public byte[] prepareRegistering(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "requestRegistering");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            parameterChecking(serverCert, nonceSvr, kgvId);
            mCrypto.getRandom(mKgvKey);
            byte[] nonce = mVkm.sensitiveBox(1);
            if (nonce == null) {
                throwException(-5, "Error from VaultKeeper (getNonce)");
            }
            System.arraycopy(nonce, 0, mNonceDev, 0, 32);
            byte[] deviceMsg = makeDeviceMsg(serverCert, mKgvKey, kgvId, nonceSvr, mNonceDev, null);
            return deviceMsg;
        } catch (KnoxGuardVaultException e) {
            clearKgvData();
            throw e;
        }
    }

    public byte[] completeRegistering(boolean resultSvr, byte[] token, byte[] sign) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeRegistering");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        if (!resultSvr) {
            try {
                try {
                    throwException(-6, "resultSvr is fail");
                } catch (KnoxGuardVaultException e) {
                    throw e;
                }
            } catch (Throwable th) {
                clearKgvData();
                throw th;
            }
        }
        byte[] input = new byte["Normal".getBytes(StandardCharsets.UTF_8).length + 32];
        System.arraycopy("Normal".getBytes(), 0, input, 0, "Normal".getBytes().length);
        System.arraycopy(mNonceDev, 0, input, "Normal".getBytes().length, 32);
        byte[] hmac = mCrypto.hmacSha256(input, mKgvKey);
        if (!Arrays.equals(token, hmac)) {
            throwException(-4, "Invalid token");
        }
        int vaultResult = mVkm.initialize(mKgvKey, "Normal".getBytes(), mServerCert, sign);
        if (vaultResult != 0) {
            throwException(-5, "Error from VaultKeeper (initialization/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
        }
        byte[] resultDev = makeResultDev();
        clearKgvData();
        return resultDev;
    }

    private byte[] prepareProcessing(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "prepareProcessing");
        if (!mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        if (mVkm2 == null) {
            throwException(-5, "Error from VaultKeeper Manager2 is null object");
        }
        try {
            parameterChecking(serverCert, nonceSvr, kgvId);
            byte[] nonce = mVkm.sensitiveBox(1);
            if (nonce == null) {
                throwException(-5, "Error from VaultKeeper (getNonce)");
            }
            System.arraycopy(nonce, 0, mNonceDev, 0, 32);
            byte[] nonce2 = mVkm2.sensitiveBox(1);
            if (nonce2 == null) {
                throwException(-5, "Error from VaultKeeper2 (getNonce)");
            }
            System.arraycopy(nonce2, 0, mNonceDev2, 0, 32);
            byte[] deviceMsg = makeDeviceMsg(serverCert, null, kgvId, nonceSvr, mNonceDev, null, mNonceDev2);
            return deviceMsg;
        } catch (KnoxGuardVaultException e) {
            clearKgvData();
            throw e;
        }
    }

    public byte[] prepareCompleting(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "requestCompleting");
        if (mIsSupportKg2) {
            return prepareProcessing(serverCert, nonceSvr, kgvId);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            parameterChecking(serverCert, nonceSvr, kgvId);
            byte[] nonce = mVkm.sensitiveBox(1);
            if (nonce == null) {
                throwException(-5, "Error from VaultKeeper (getNonce)");
            }
            System.arraycopy(nonce, 0, mNonceDev, 0, 32);
            byte[] deviceMsg = makeDeviceMsg(serverCert, null, kgvId, nonceSvr, mNonceDev, null);
            return deviceMsg;
        } catch (KnoxGuardVaultException e) {
            clearKgvData();
            throw e;
        }
    }

    public byte[] completeCompleting(boolean resultSvr, byte[] token) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeCompleting");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            if (!resultSvr) {
                try {
                    throwException(-6, "resultSvr is fail");
                } catch (KnoxGuardVaultException e) {
                    throw e;
                }
            }
            int vaultResult = mVkm.write(1, COMPLETED_STATE.getBytes(StandardCharsets.UTF_8), token);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
            }
            setRemoteLockToLockscreen();
            byte[] resultDev = makeResultDev();
            return resultDev;
        } finally {
            clearKgvData();
        }
    }

    public byte[] prepareLocking(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "requestLocking");
        if (mIsSupportKg2) {
            return prepareProcessing(serverCert, nonceSvr, kgvId);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            parameterChecking(serverCert, nonceSvr, kgvId);
            byte[] nonce = mVkm.sensitiveBox(1);
            if (nonce == null) {
                throwException(-5, "Error from VaultKeeper (getNonce)");
            }
            System.arraycopy(nonce, 0, mNonceDev, 0, 32);
            byte[] deviceMsg = makeDeviceMsg(serverCert, null, kgvId, nonceSvr, mNonceDev, null);
            return deviceMsg;
        } catch (KnoxGuardVaultException e) {
            clearKgvData();
            throw e;
        }
    }

    public byte[] completeLocking(boolean resultSvr, byte[] passcode, byte[] token, String noticeMsg, String phoneNumber, String requesterName, String emailAddress) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeLocking");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            if (!resultSvr) {
                try {
                    throwException(-6, "resultSvr is fail");
                } catch (KnoxGuardVaultException e) {
                    throw e;
                } catch (Throwable th) {
                    e = th;
                    clearKgvData();
                    throw e;
                }
            }
            if (passcode.length != 32) {
                throwException(-2, "passcode hash length is wrong(" + passcode.length + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (noticeMsg == null) {
                throwException(-2, "noticeMsg is null");
            }
            if (noticeMsg.length() == 0) {
                throwException(-2, "noticeMsg has nothing");
            }
            if (phoneNumber != null && phoneNumber.length() == 0) {
                throwException(-2, "phoneNumber has nothing");
            }
            if (emailAddress != null && emailAddress.length() == 0) {
                throwException(-2, "emailAddress has nothing");
            }
            if (requesterName == null) {
                throwException(-2, "requesterName is null");
            }
            if (requesterName.length() == 0) {
                throwException(-2, "requesterName has nothing");
            }
            try {
                int vaultResult = mVkm.write(1, LOCKED_STATE.getBytes(StandardCharsets.UTF_8), passcode, token);
                if (vaultResult != 0) {
                    throwException(-5, "Error from VaultKeeper (write with passcode/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
                }
                if (!setLockscreenData(noticeMsg, phoneNumber, requesterName, emailAddress, false, true, null)) {
                    throwException(-5, "setLockscreenData");
                }
                bindToLockScreen();
                byte[] resultDev = makeResultDev();
                clearKgvData();
                return resultDev;
            } catch (KnoxGuardVaultException e2) {
                throw e2;
            }
        } catch (Throwable th2) {
            e = th2;
        }
    }

    public byte[] completeLocking(boolean resultSvr, byte[] passcode, byte[] token, String noticeMsg, String phoneNumber, String requesterName, String emailAddress, boolean skipPin) throws KnoxGuardVaultException {
        return completeLocking(resultSvr, passcode, token, noticeMsg, phoneNumber, requesterName, emailAddress, skipPin, true);
    }

    public byte[] completeLocking(boolean resultSvr, byte[] passcode, byte[] token, String noticeMsg, String phoneNumber, String requesterName, String emailAddress, boolean skipPin, boolean skipSupport) throws KnoxGuardVaultException {
        return completeLocking(resultSvr, passcode, token, noticeMsg, phoneNumber, requesterName, emailAddress, skipPin, skipSupport, null);
    }

    public byte[] completeLocking(boolean resultSvr, byte[] passcode, byte[] token, String noticeMsg, String phoneNumber, String requesterName, String emailAddress, boolean skipPin, boolean skipSupport, Bundle bundle) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeLocking");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        if (!resultSvr) {
            try {
                throwException(-6, "resultSvr is fail");
            } catch (KnoxGuardVaultException e) {
                throw e;
            } catch (Throwable th) {
                e = th;
                clearKgvData();
                throw e;
            }
        }
        if (noticeMsg == null) {
            throwException(-2, "noticeMsg is null");
        }
        if (noticeMsg.length() == 0) {
            throwException(-2, "noticeMsg has nothing");
        }
        if (phoneNumber != null && phoneNumber.length() == 0) {
            throwException(-2, "phoneNumber has nothing");
        }
        if (emailAddress != null && emailAddress.length() == 0) {
            throwException(-2, "emailAddress has nothing");
        }
        if (requesterName == null) {
            throwException(-2, "requesterName is null");
        }
        if (requesterName.length() == 0) {
            throwException(-2, "requesterName has nothing");
        }
        try {
            try {
                int vaultResult = mVkm.write(1, LOCKED_STATE.getBytes(StandardCharsets.UTF_8), passcode, token);
                if (vaultResult != 0) {
                    throwException(-5, "Error from VaultKeeper (write with passcode/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
                }
                if (!setLockscreenData(noticeMsg, phoneNumber, requesterName, emailAddress, skipPin, skipSupport, bundle)) {
                    throwException(-5, "setLockscreenData");
                }
                byte[] resultDev = makeResultDev();
                clearKgvData();
                return resultDev;
            } catch (KnoxGuardVaultException e2) {
                throw e2;
            }
        } catch (Throwable th2) {
            e = th2;
            clearKgvData();
            throw e;
        }
    }

    public boolean completeLocking(String noticeMsg, String phoneNumber, String requesterName, String emailAddress, boolean skipPin) throws KnoxGuardVaultException {
        return completeLocking(noticeMsg, phoneNumber, requesterName, emailAddress, skipPin, true);
    }

    public boolean completeLocking(String noticeMsg, String phoneNumber, String requesterName, String emailAddress, boolean skipPin, boolean skipSupport) throws KnoxGuardVaultException {
        return completeLocking(noticeMsg, phoneNumber, requesterName, emailAddress, skipPin, skipSupport, (Bundle) null);
    }

    public boolean completeLocking(String noticeMsg, String phoneNumber, String requesterName, String emailAddress, boolean skipPin, boolean skipSupport, Bundle bundle) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeLocking");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        if (noticeMsg == null) {
            try {
                try {
                    throwException(-2, "noticeMsg is null");
                } catch (KnoxGuardVaultException e) {
                    throw e;
                }
            } finally {
                clearKgvData();
            }
        }
        if (noticeMsg.length() == 0) {
            throwException(-2, "noticeMsg has nothing");
        }
        if (phoneNumber != null && phoneNumber.length() == 0) {
            throwException(-2, "phoneNumber has nothing");
        }
        if (emailAddress != null && emailAddress.length() == 0) {
            throwException(-2, "emailAddress has nothing");
        }
        if (requesterName == null) {
            throwException(-2, "requesterName is null");
        }
        if (requesterName.length() == 0) {
            throwException(-2, "requesterName has nothing");
        }
        int vaultResult = mVkm.write(1, LOCKED_STATE.getBytes(StandardCharsets.UTF_8), null, null);
        if (vaultResult != 0) {
            throwException(-5, "Error from VaultKeeper (write with passcode/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (!setLockscreenData(noticeMsg, phoneNumber, requesterName, emailAddress, skipPin, skipSupport, bundle)) {
            throwException(-5, "setLockscreenData");
        }
        return true;
    }

    public byte[] prepareBlinking(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "requestBlinking");
        if (mIsSupportKg2) {
            return prepareProcessing(serverCert, nonceSvr, kgvId);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            parameterChecking(serverCert, nonceSvr, kgvId);
            byte[] nonce = mVkm.sensitiveBox(1);
            if (nonce == null) {
                throwException(-5, "Error from VaultKeeper (getNonce)");
            }
            System.arraycopy(nonce, 0, mNonceDev, 0, 32);
            byte[] deviceMsg = makeDeviceMsg(serverCert, null, kgvId, nonceSvr, mNonceDev, null);
            return deviceMsg;
        } catch (KnoxGuardVaultException e) {
            clearKgvData();
            throw e;
        }
    }

    public byte[] completeBlinking(boolean resultSvr, byte[] passcode, byte[] token) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeBlinking");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            if (!resultSvr) {
                try {
                    throwException(-6, "resultSvr is fail");
                } catch (KnoxGuardVaultException e) {
                    throw e;
                }
            }
            int vaultResult = mVkm.write(1, BLINK_STATE.getBytes(StandardCharsets.UTF_8), passcode, token);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write blink with passcode/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
            }
            unbindFromLockScreen();
            byte[] resultDev = makeResultDev();
            return resultDev;
        } finally {
            clearKgvData();
        }
    }

    public byte[] prepareUnlocking(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "requestUnlocking");
        if (mIsSupportKg2) {
            return prepareProcessing(serverCert, nonceSvr, kgvId);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            parameterChecking(serverCert, nonceSvr, kgvId);
            byte[] nonce = mVkm.sensitiveBox(1);
            if (nonce == null) {
                throwException(-5, "Error from VaultKeeper (getNonce)");
            }
            System.arraycopy(nonce, 0, mNonceDev, 0, 32);
            byte[] deviceMsg = makeDeviceMsg(serverCert, null, kgvId, nonceSvr, mNonceDev, null);
            return deviceMsg;
        } catch (KnoxGuardVaultException e) {
            clearKgvData();
            throw e;
        }
    }

    public byte[] completeUnlocking(boolean resultSvr, byte[] token) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeUnlocking");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            if (!resultSvr) {
                try {
                    throwException(-6, "resultSvr is fail");
                } catch (KnoxGuardVaultException e) {
                    throw e;
                }
            }
            int vaultResult = mVkm.write(1, "Normal".getBytes(StandardCharsets.UTF_8), token);
            if (vaultResult == 0) {
                if (!setFailureCount(0)) {
                    Log.m96e(TAG, "Failed setFailureCount");
                }
            } else {
                throwException(-5, "Error from VaultKeeper (write/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
            }
            setRemoteLockToLockscreen();
            byte[] resultDev = makeResultDev();
            return resultDev;
        } finally {
            clearKgvData();
        }
    }

    public int completeUnlocking(String passcode) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeUnlocking(passcode)");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            try {
                setCompleteUnlockingDone(false);
                byte[] hmac = null;
                int failureCount = getFailureCount() + 1;
                if (passcode != null && !passcode.equals("")) {
                    if (mIsSupportKg2) {
                        try {
                            int intPasscode = Integer.parseInt(passcode);
                            if (intPasscode == 0) {
                                throw new NumberFormatException("0 is not permitted as passcode");
                            }
                            hmac = integerToByteArray(intPasscode);
                        } catch (NumberFormatException e) {
                            Log.m96e(TAG, "[-11]passcode is invalid format");
                            if (!setFailureCount(failureCount)) {
                                Log.m96e(TAG, "Failed setFailureCount");
                            }
                            return failureCount;
                        }
                    } else {
                        byte[] nonce = mVkm.sensitiveBox(1);
                        if (nonce == null) {
                            throwException(-5, "Error from VaultKeeper (getNonce)");
                        }
                        System.arraycopy(nonce, 0, mNonceDev, 0, 32);
                        byte[] secondaryKey = mCrypto.sha256(passcode);
                        byte[] input = new byte["Normal".getBytes(StandardCharsets.UTF_8).length + 32];
                        System.arraycopy("Normal".getBytes(), 0, input, 0, "Normal".getBytes().length);
                        System.arraycopy(mNonceDev, 0, input, "Normal".getBytes().length, 32);
                        hmac = mCrypto.hmacSha256(input, secondaryKey);
                    }
                }
                int vaultResult = mVkm.write(1, "Normal".getBytes(), hmac);
                if (vaultResult == 0) {
                    failureCount = 0;
                } else {
                    Log.m96e(TAG, "Incorrect passcode(VaultKeeper-write/" + vaultResult + "), current failure count (" + failureCount + NavigationBarInflaterView.KEY_CODE_END);
                }
                setCompleteUnlockingDone(true);
                if (!setFailureCount(failureCount)) {
                    Log.m96e(TAG, "Failed setFailureCount");
                }
                return failureCount;
            } catch (KnoxGuardVaultException e2) {
                throw e2;
            }
        } finally {
            clearKgvData();
        }
    }

    public byte[] prepareDestroying(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "requestDestroying");
        if (mIsSupportKg2) {
            return prepareProcessing(serverCert, nonceSvr, kgvId);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            parameterChecking(serverCert, nonceSvr, kgvId);
            byte[] nonce = mVkm.sensitiveBox(1);
            if (nonce == null) {
                throwException(-5, "Error from VaultKeeper (getNonce)");
            }
            System.arraycopy(nonce, 0, mNonceDev, 0, 32);
            byte[] deviceMsg = makeDeviceMsg(serverCert, null, kgvId, nonceSvr, mNonceDev, null);
            return deviceMsg;
        } catch (KnoxGuardVaultException e) {
            clearKgvData();
            throw e;
        }
    }

    public byte[] completeDestroying(boolean resultSvr, byte[] sign) throws KnoxGuardVaultException {
        Log.m98i(TAG, "completeDestroying");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            if (!resultSvr) {
                try {
                    throwException(-6, "resultSvr is fail");
                } catch (KnoxGuardVaultException e) {
                    throw e;
                }
            }
            int vaultResult = mVkm.destroy(mServerCert, sign);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (destroy/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
            }
            byte[] resultDev = makeResultDev();
            return resultDev;
        } finally {
            clearKgvData();
        }
    }

    public int setRestrictedDevice() throws KnoxGuardVaultException {
        Log.m98i(TAG, "setRestrictedDevice");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            String state = query();
            if (!PRENORMAL_STATE.equals(state)) {
                throwException(-1, "Cannot set Checking state in current state(" + state + NavigationBarInflaterView.KEY_CODE_END);
            }
            int vaultResult = mVkm.write(1, CHECKING_STATE.getBytes(StandardCharsets.UTF_8));
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write checking/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
                return 0;
            }
            return 0;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public int setKGTargetDevice() throws KnoxGuardVaultException {
        Log.m98i(TAG, "setKGTargetdevice");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            String state = query();
            if (!"".equals(state) && !COMPLETED_STATE.equals(state) && !CHECKING_STATE.equals(state)) {
                throwException(-1, "Cannot set KGV Prenormal state in current state(" + state + NavigationBarInflaterView.KEY_CODE_END);
            }
            int vaultResult = mVkm.write(1, PRENORMAL_STATE.getBytes(StandardCharsets.UTF_8));
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write prenormal/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
                return 0;
            }
            return 0;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public byte[] query(byte[] serverCert, byte[] nonceSvr, byte[] kgvId) throws KnoxGuardVaultException {
        Log.m98i(TAG, "query");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            try {
                parameterChecking(serverCert, nonceSvr, kgvId);
                byte[] state = mVkm.read(1);
                if (state == null) {
                    throwException(-5, "Error from VaultKeeper (readState)");
                }
                byte[] deviceMsg = makeDeviceMsg(serverCert, null, kgvId, nonceSvr, null, state);
                return deviceMsg;
            } catch (KnoxGuardVaultException e) {
                throw e;
            }
        } finally {
            clearKgvData();
        }
    }

    public String query() throws KnoxGuardVaultException {
        Log.m98i(TAG, "query(void)");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] state = mVkm.read(1);
            if (state == null) {
                throwException(-5, "Error from VaultKeeper (readState)");
            }
            return new String(state, StandardCharsets.UTF_8);
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public boolean isKgTurnedOn() throws KnoxGuardVaultException {
        Log.m98i(TAG, "isKgTurnedOn(void)");
        if (FactoryTest.isFactoryBinary()) {
            return false;
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] state = mVkm.read(1);
            if (state == null) {
                throwException(-5, "Error from VaultKeeper (readState)");
            }
            String rlcState = new String(state, StandardCharsets.UTF_8);
            return ("".equalsIgnoreCase(rlcState) || PRENORMAL_STATE.equalsIgnoreCase(rlcState) || CHECKING_STATE.equalsIgnoreCase(rlcState) || COMPLETED_STATE.equalsIgnoreCase(rlcState)) ? false : true;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRemoteLockToLockscreen() throws KnoxGuardVaultException {
        Log.m98i(TAG, "setRemoteLockToLockscreen");
        long kgvDelayTime = 0;
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                throwException(-9, "getKGVaultData return null");
            }
            int failureCount = getFailureCount();
            if (failureCount == 6) {
                kgvDelayTime = 60000;
            } else if (failureCount == 7) {
                kgvDelayTime = 60000 * 5;
            } else if (failureCount == 8) {
                kgvDelayTime = 60000 * 15;
            } else if (failureCount == 9) {
                kgvDelayTime = 60000 * 60;
            } else if (failureCount >= 10) {
                kgvDelayTime = 60000 * 1440;
            }
            RemoteLockInfo remoteLockInfo = new RemoteLockInfo.Builder(3, LOCKED_STATE.equals(query())).setClientName(kvd.getRequesterName()).setPhoneNumber(kvd.getPhoneNumber()).setEmailAddress(kvd.getEmailAddress()).setMessage(kvd.getNoticeMessage()).setAllowFailCount(1).setLockTimeOut(kgvDelayTime).setBlockCount(0).setSkipPinContainer(kvd.getSkipPin()).setSkipSupportContainer(kvd.getSkipSupport()).setBundle(kvd.getBundle()).build();
            mLockSettingsService.setRemoteLock(ActivityManager.getCurrentUser(), remoteLockInfo);
        } catch (RemoteException e) {
            e.printStackTrace();
            throwException(-9, "Runtime Exception from Lockscreen");
        } catch (KnoxGuardVaultException e2) {
            throw e2;
        }
    }

    public void bindToLockScreen() throws KnoxGuardVaultException {
        Log.m98i(TAG, "bindToLockScreen");
        try {
            if (mLockSettingsService == null) {
                setLockSettingsService(ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings")));
            }
            mLockSettingsService.registerRemoteLockCallback(3, this.mRemoteLockMonitorCallback);
            setRemoteLockToLockscreen();
        } catch (RemoteException e) {
            e.printStackTrace();
            throwException(-9, "Runtime Exception from Lockscreen");
        } catch (KnoxGuardVaultException e2) {
            throw e2;
        }
    }

    public void unbindFromLockScreen() throws KnoxGuardVaultException {
        Log.m98i(TAG, "unbindFromLockScreen");
        try {
            if (mLockSettingsService == null) {
                setLockSettingsService(ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings")));
            }
            mLockSettingsService.unregisterRemoteLockCallback(3, this.mRemoteLockMonitorCallback);
            setRemoteLockToLockscreen();
        } catch (RemoteException e) {
            e.printStackTrace();
            throwException(-9, "Runtime Exception from Lockscreen");
        } catch (KnoxGuardVaultException e2) {
            throw e2;
        }
    }

    public boolean isSupportHOTP() {
        return true;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x002a -> B:13:0x004a). Please report as a decompilation issue!!! */
    private byte[] serialize(Object obj) throws KnoxGuardVaultException {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        byte[] ret = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (obj == null) {
            return new byte[0];
        }
        try {
            try {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                ret = bos.toByteArray();
                try {
                    bos.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                oos.close();
            } catch (Exception e3) {
                e3.printStackTrace();
                throwException(-10, "Error serialize");
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                if (oos != null) {
                    oos.close();
                }
            }
            return ret;
        } finally {
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x0027 -> B:11:0x0047). Please report as a decompilation issue!!! */
    private <T> T deserialize(byte[] bArr, Class<T> cls) throws KnoxGuardVaultException {
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        T t = null;
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        try {
            try {
                try {
                    byteArrayInputStream = new ByteArrayInputStream(bArr);
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    t = (T) objectInputStream.readObject();
                    try {
                        byteArrayInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    objectInputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    throwException(-10, "Error deserialize");
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            return t;
        } finally {
        }
    }

    private KGVaultData getKGVaultData() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getKGVaultData");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] bytes = mVkm.read(2);
            if (bytes == null) {
                throwException(-5, "Error from VaultKeeper (readData)");
            }
            if (bytes.length == 0) {
                Log.m102w(TAG, "No data in Vault");
                return null;
            }
            KGVaultData obj = (KGVaultData) deserialize(bytes, KGVaultData.class);
            return obj;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public String getPhoneNumber() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getPhoneNumber");
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return "";
            }
            if (kvd.getPhoneNumber().length() == 0) {
                Log.m102w(TAG, "No phone number in Vault");
                return "";
            }
            String phoneNumber = kvd.getPhoneNumber();
            return phoneNumber;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public String getEmailAddress() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getEmailAddress");
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return "";
            }
            if (kvd.getEmailAddress().length() == 0) {
                Log.m102w(TAG, "No Email Address in Vault");
                return "";
            }
            String emailAddress = kvd.getEmailAddress();
            return emailAddress;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public String getNoticeMessage() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getNoticeMessage");
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return "";
            }
            if (kvd.getNoticeMessage().length() == 0) {
                Log.m102w(TAG, "No notice message in Vault");
                return "";
            }
            String noticeMsg = kvd.getNoticeMessage();
            return noticeMsg;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public String getRequesterName() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getRequesterName");
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return "";
            }
            if (kvd.getNoticeMessage().length() == 0) {
                Log.m102w(TAG, "No requester name in Vault");
                return "";
            }
            String requesterName = kvd.getRequesterName();
            return requesterName;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public boolean setLockscreenData(String noticeMsg, String phoneNumber, String requesterName, String emailAddress, boolean skipPin, boolean skipSupport, Bundle bundle) throws KnoxGuardVaultException {
        KGVaultData kvd;
        Log.m98i(TAG, "setLockscreenData");
        if (noticeMsg == null && requesterName == null) {
            throwException(-2, "One of paratemers should not be null");
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] bytes = mVkm.read(2);
            if (bytes == null) {
                throwException(-5, "Error from VaultKeeper (readData)");
            }
            if (bytes.length != 0) {
                kvd = (KGVaultData) deserialize(bytes, KGVaultData.class);
                if (kvd == null) {
                    throwException(-10, "Error deserialize");
                }
                if (noticeMsg != null) {
                    kvd.setNoticeMessage(noticeMsg);
                }
                if (requesterName != null) {
                    kvd.setRequesterName(requesterName);
                }
                try {
                    kvd.setPhoneNumber(phoneNumber);
                    try {
                        kvd.setEmailAddress(emailAddress);
                        kvd.setSkipPin(skipPin);
                    } catch (KnoxGuardVaultException e) {
                        e = e;
                        throw e;
                    }
                } catch (KnoxGuardVaultException e2) {
                    e = e2;
                    throw e;
                }
                try {
                    kvd.setSkipSupport(skipSupport);
                    try {
                        kvd.setBundle(bundle);
                    } catch (KnoxGuardVaultException e3) {
                        throw e3;
                    }
                } catch (KnoxGuardVaultException e4) {
                    e = e4;
                    throw e;
                }
            } else {
                kvd = new KGVaultData(0, noticeMsg, phoneNumber, emailAddress, requesterName, "");
                kvd.setSkipPin(skipPin);
            }
            byte[] serializedObj = serialize(kvd);
            int vaultResult = mVkm.write(2, serializedObj);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
                return true;
            }
            return true;
        } catch (KnoxGuardVaultException e5) {
            e = e5;
        }
    }

    public String getClientData() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getClientData");
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return "";
            }
            if (kvd.getClientData().length() == 0) {
                Log.m102w(TAG, "No client data in Vault");
                return "";
            }
            String clientData = kvd.getClientData();
            return clientData;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public String setClientData(String clientData) throws KnoxGuardVaultException {
        KGVaultData kvd;
        Log.m98i(TAG, "setClientData");
        String oldClientData = "";
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] bytes = mVkm.read(2);
            if (bytes == null) {
                throwException(-5, "Error from VaultKeeper (readData)");
            }
            if (bytes.length == 0) {
                kvd = new KGVaultData(0, "", "", "", "", "");
            } else {
                kvd = (KGVaultData) deserialize(bytes, KGVaultData.class);
                if (kvd == null) {
                    throwException(-10, "Error deserialize");
                }
                oldClientData = kvd.getClientData();
                kvd.setClientData(clientData);
            }
            byte[] serializedObj = serialize(kvd);
            int vaultResult = mVkm.write(2, serializedObj);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write/ " + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
            }
            return oldClientData;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public int getFailureCount() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getFailureCount");
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return 0;
            }
            int failureCount = kvd.getFailureCount();
            return failureCount;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public boolean setFailureCount(int failureCount) throws KnoxGuardVaultException {
        KGVaultData kvd;
        Log.m98i(TAG, "setFailureCount");
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] bytes = mVkm.read(2);
            if (bytes == null) {
                throwException(-5, "Error from VaultKeeper (readData)");
            }
            if (bytes.length == 0) {
                kvd = new KGVaultData(0, "", "", "", "", "");
            } else {
                kvd = (KGVaultData) deserialize(bytes, KGVaultData.class);
                if (kvd == null) {
                    throwException(-10, "Error deserialize");
                }
            }
            kvd.setFailureCount(failureCount);
            byte[] serializedObj = serialize(kvd);
            int vaultResult = mVkm.write(2, serializedObj);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
                return true;
            }
            return true;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public String getOtpKey() throws KnoxGuardVaultException {
        Log.m98i(TAG, "getOtpKey");
        if (mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return "";
            }
            if (kvd.getOtpKey() != null && kvd.getOtpKey().length() != 0) {
                String otpKey = kvd.getOtpKey();
                return otpKey;
            }
            Log.m102w(TAG, "No otpKey in Vault");
            return "";
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public boolean setOtpKey(String otpKey) throws KnoxGuardVaultException {
        KGVaultData kvd;
        Log.m98i(TAG, "setOtpKey");
        if (mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] bytes = mVkm.read(2);
            if (bytes == null) {
                throwException(-5, "Error from VaultKeeper (readData)");
            }
            if (bytes.length == 0) {
                kvd = new KGVaultData(0, "", "", "", "", "");
            } else {
                kvd = (KGVaultData) deserialize(bytes, KGVaultData.class);
                if (kvd == null) {
                    throwException(-10, "Error deserialize");
                }
                kvd.setOtpKey(otpKey);
            }
            byte[] serializedObj = serialize(kvd);
            int vaultResult = mVkm.write(2, serializedObj);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write/ " + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
                return true;
            }
            return true;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    private String generateHOTPPasscode() throws KnoxGuardVaultException {
        Log.m98i(TAG, "[HOTP] generateHOTPPasscode");
        if (mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        try {
            KGVaultData kvd = getKGVaultData();
            if (kvd == null) {
                Log.m102w(TAG, "No data in Vault");
                return null;
            }
            String otpKey = getOtpKey();
            int otpCount = kvd.getOtpCount();
            byte[] sharedSecretBytes = Base64.getDecoder().decode(otpKey.getBytes(StandardCharsets.UTF_8));
            byte[] movingFactorBytes = Integer.toBinaryString(otpCount).getBytes(StandardCharsets.UTF_8);
            byte[] sha256Bytes = mCrypto.hmacSha256(movingFactorBytes, sharedSecretBytes);
            int binary = ((sha256Bytes[0] & Byte.MAX_VALUE) << 24) | ((sha256Bytes[0 + 1] & 255) << 16) | ((sha256Bytes[0 + 2] & 255) << 8) | (sha256Bytes[0 + 3] & 255);
            int otp = binary % ((int) Math.pow(10.0d, 8.0d));
            String pin = String.format("%8s", Integer.valueOf(otp)).replace(' ', '0');
            return pin;
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public String generateHOTPChallenge() throws KnoxGuardVaultException {
        Log.m98i(TAG, "[HOTP] generateHOTPChallenge");
        try {
            try {
                if (mIsSupportKg2) {
                    if (mVkm == null) {
                        throwException(-5, "Error from VaultKeeper Manager is null object");
                    }
                    int challenge = mVkm.generateHotpCode();
                    return String.format("%8s", Integer.valueOf(challenge)).replace(' ', '0');
                }
                KGVaultData kvd = getKGVaultData();
                if (kvd == null) {
                    Log.m102w(TAG, "No data in Vault");
                    return null;
                }
                String otpKey = getOtpKey();
                int otpCount = kvd.getOtpCount();
                byte[] sharedSecretBytes = Base64.getDecoder().decode(otpKey.getBytes(StandardCharsets.UTF_8));
                byte[] movingFactorBytes = Integer.toBinaryString(otpCount).getBytes(StandardCharsets.UTF_8);
                byte[] sha256Bytes = mCrypto.hmacSha256(movingFactorBytes, sharedSecretBytes);
                int offset = sha256Bytes.length - 4;
                int binary = ((sha256Bytes[offset] & Byte.MAX_VALUE) << 24) | ((sha256Bytes[offset + 1] & 255) << 16) | ((sha256Bytes[offset + 2] & 255) << 8) | (sha256Bytes[offset + 3] & 255);
                int challenge2 = binary % ((int) Math.pow(10.0d, 8.0d));
                String otpChallenge = String.format("%8s", Integer.valueOf(challenge2)).replace(' ', '0');
                return otpChallenge;
            } catch (KnoxGuardVaultException e) {
                throw e;
            }
        } catch (KnoxGuardVaultException e2) {
            throw e2;
        }
    }

    public boolean verifyteHOTPPasscode(String pin) throws KnoxGuardVaultException {
        Log.m94d(TAG, "[HOTP] verifyteHOTPPasscode");
        if (mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        String passcode = generateHOTPPasscode();
        if (passcode == null) {
            return false;
        }
        return passcode.equals(pin);
    }

    public void increaseHOTPcount() throws KnoxGuardVaultException {
        KGVaultData kvd;
        int OTPcount;
        Log.m98i(TAG, "[HOTP] increaseHOTPcount");
        if (mIsSupportKg2) {
            throwException(-1, "must not be called in first_api_level : " + first_api_level);
        }
        if (mVkm == null) {
            throwException(-5, "Error from VaultKeeper Manager is null object");
        }
        try {
            byte[] bytes = mVkm.read(2);
            if (bytes == null) {
                throwException(-5, "Error from VaultKeeper (readData)");
            }
            if (bytes.length == 0) {
                kvd = new KGVaultData(0, "", "", "", "", "");
            } else {
                kvd = (KGVaultData) deserialize(bytes, KGVaultData.class);
                if (kvd == null) {
                    throwException(-10, "Error deserialize");
                }
            }
            int OTPcount2 = kvd.getOtpCount();
            Log.m94d(TAG, "[HOTP] getOTPCount : " + OTPcount2);
            if (OTPcount2 < 9999) {
                OTPcount = OTPcount2 + 1;
            } else {
                OTPcount = 0;
            }
            kvd.setOtpCount(OTPcount);
            Log.m94d(TAG, "[HOTP] setOTPCount : " + OTPcount);
            byte[] serializedObj = serialize(kvd);
            int vaultResult = mVkm.write(2, serializedObj);
            if (vaultResult != 0) {
                throwException(-5, "Error from VaultKeeper (write/" + vaultResult + NavigationBarInflaterView.KEY_CODE_END);
            }
        } catch (KnoxGuardVaultException e) {
            throw e;
        }
    }

    public int getReadErrorCode() {
        try {
            query();
            try {
                getKGVaultData();
            } catch (KnoxGuardVaultException e) {
                Log.m96e(TAG, "getErrorCodeOnRead - data");
            }
            return mVkm.getErrorCode();
        } catch (KnoxGuardVaultException e2) {
            Log.m96e(TAG, "getErrorCodeOnRead - state");
            return mVkm.getErrorCode();
        }
    }

    public int getErrorCode(int vkNum) throws KnoxGuardVaultException {
        Log.m98i(TAG, "getErrorCode");
        switch (vkNum) {
            case 1:
                if (mVkm == null) {
                    throwException(-1, "vk1 is null");
                }
                int retError = mVkm.getErrorCode();
                return retError;
            case 2:
                if (mVkm2 == null) {
                    throwException(-1, "vk2 is null");
                }
                int retError2 = mVkm2.getErrorCode();
                return retError2;
            default:
                throwException(-2, String.format("vkNum must be 1 or 2. but %d", Integer.valueOf(vkNum)));
                return 0;
        }
    }

    private byte[] integerToByteArray(int value) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(value);
        return buf.array();
    }

    private void setIsSupportKg2() {
        mIsSupportKg2 = first_api_level >= 29;
    }

    private static void setVkm(VaultKeeperManager vkm) {
        mVkm = vkm;
    }

    private static void setVkm2(VaultKeeperManager vkm2) {
        mVkm2 = vkm2;
    }

    private static void setCrypto(CryptoManager crypto) {
        mCrypto = crypto;
    }

    private static void setContext(Context context) {
        mContext = context;
    }

    private static void setServerCert(byte[] serverCert) {
        mServerCert = serverCert;
    }

    private static void setLockSettingsService(ILockSettings lockSettings) {
        mLockSettingsService = lockSettings;
    }

    private static void setKnoxGuardVaultListener(IKnoxGuardVaultListener kgvListener) {
        mKnoxGuardVaultListener = kgvListener;
    }

    private static void setCompleteUnlockingDone(boolean completeUnlockingDone) {
        mCompleteUnlockingDone = completeUnlockingDone;
    }

    static class KGVaultData implements Serializable {
        static final long serialVersionUID = 1986081920160627777L;
        private String mClientData;
        private String mCustomerAppName;
        private String mCustomerAppPackageName;
        private String mEmailAddress;
        private int mFailureCount;
        private String mNoticeMessage;
        private int mOTPCount;
        private String mOTPKey;
        private String mPhoneNumber;
        private String mRequesterName;
        private boolean mSkipPin;
        private boolean mSkipSupport;

        KGVaultData(int failureCount, String noticeMessage, String phoneNumber, String emailAddress, String requesterName, String clientData) {
            this.mFailureCount = failureCount;
            this.mNoticeMessage = noticeMessage;
            this.mClientData = clientData;
            this.mRequesterName = requesterName;
            this.mPhoneNumber = phoneNumber;
            this.mEmailAddress = emailAddress;
        }

        public Bundle getBundle() {
            Bundle bundle = new Bundle();
            bundle.putCharSequence(RemoteLockInfo.CUSTOMER_PACKAGE_NAME, this.mCustomerAppPackageName);
            bundle.putCharSequence(RemoteLockInfo.CUSTOMER_APP_NAME, this.mCustomerAppName);
            return bundle;
        }

        public void setBundle(Bundle bundle) {
            if (bundle == null) {
                this.mCustomerAppPackageName = null;
                this.mCustomerAppName = null;
                return;
            }
            CharSequence packageName = bundle.getCharSequence(RemoteLockInfo.CUSTOMER_PACKAGE_NAME);
            if (packageName != null) {
                this.mCustomerAppPackageName = packageName.toString();
            } else {
                this.mCustomerAppPackageName = null;
            }
            CharSequence appName = bundle.getCharSequence(RemoteLockInfo.CUSTOMER_APP_NAME);
            if (appName != null) {
                this.mCustomerAppName = appName.toString();
            } else {
                this.mCustomerAppName = null;
            }
        }

        public boolean getSkipSupport() {
            return this.mSkipSupport;
        }

        public void setSkipSupport(boolean skipSupport) {
            this.mSkipSupport = skipSupport;
        }

        public boolean getSkipPin() {
            return this.mSkipPin;
        }

        public void setSkipPin(boolean skipPin) {
            this.mSkipPin = skipPin;
        }

        public String getOtpKey() {
            return this.mOTPKey;
        }

        public void setOtpKey(String otpKey) {
            this.mOTPKey = otpKey;
        }

        public int getOtpCount() {
            return this.mOTPCount;
        }

        public void setOtpCount(int otpCount) {
            this.mOTPCount = otpCount;
        }

        public int getFailureCount() {
            return this.mFailureCount;
        }

        public void setFailureCount(int failureCount) {
            this.mFailureCount = failureCount;
        }

        public String getNoticeMessage() {
            return this.mNoticeMessage;
        }

        public void setNoticeMessage(String noticeMessage) {
            this.mNoticeMessage = noticeMessage;
        }

        public String getPhoneNumber() {
            return this.mPhoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
        }

        public String getRequesterName() {
            return this.mRequesterName;
        }

        public void setRequesterName(String requesterName) {
            this.mRequesterName = requesterName;
        }

        public String getClientData() {
            return this.mClientData;
        }

        public void setClientData(String clientData) {
            this.mClientData = clientData;
        }

        public String getEmailAddress() {
            return this.mEmailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.mEmailAddress = emailAddress;
        }
    }

    class CryptoManager {
        CryptoManager() {
        }

        public boolean verifyCertChain(byte[] serverCert) {
            if (KnoxGuardVaultManager.mVkm == null) {
                Log.m96e(KnoxGuardVaultManager.TAG, "Error from VaultKeeper Manager is null object");
                return false;
            }
            return KnoxGuardVaultManager.mVkm.verifyCertificate(serverCert);
        }

        public byte[] ecryptWithServerPubKey(byte[] plaintext, byte[] serverCert) throws KnoxGuardVaultException {
            try {
                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                InputStream in = new ByteArrayInputStream(serverCert);
                X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
                PublicKey key = cert.getPublicKey();
                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
                cipher.init(1, key);
                byte[] ciphertext = cipher.doFinal(plaintext);
                return ciphertext;
            } catch (Exception e) {
                e.printStackTrace();
                KnoxGuardVaultManager.this.throwException(-8, "ecryptWithServerPubKey");
                return null;
            }
        }

        public byte[] ecryptWithAES256CBC(byte[] plaintext, byte[] key, byte[] iv) throws KnoxGuardVaultException {
            try {
                SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(1, keySpec, new IvParameterSpec(iv));
                byte[] ciphertext = cipher.doFinal(plaintext);
                return ciphertext;
            } catch (Exception e) {
                e.printStackTrace();
                KnoxGuardVaultManager.this.throwException(-8, "ecryptWithAES256CBC");
                return null;
            }
        }

        public byte[] hmacSha256(byte[] data, byte[] key) throws KnoxGuardVaultException {
            try {
                Mac mac = Mac.getInstance(KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
                mac.init(new SecretKeySpec(key, KeyProperties.KEY_ALGORITHM_HMAC_SHA256));
                byte[] result = mac.doFinal(data);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                KnoxGuardVaultManager.this.throwException(-8, "hmacSha256");
                return null;
            }
        }

        public byte[] sha256(String input) throws KnoxGuardVaultException {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(input.getBytes(StandardCharsets.UTF_8));
                byte[] output = md.digest();
                return output;
            } catch (Exception e) {
                e.printStackTrace();
                KnoxGuardVaultManager.this.throwException(-8, "sha256");
                return null;
            }
        }

        public void getRandom(byte[] buf) {
            SecureRandom srand = new SecureRandom();
            srand.nextBytes(buf);
        }
    }
}
