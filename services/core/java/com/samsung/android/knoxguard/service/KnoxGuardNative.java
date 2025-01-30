package com.samsung.android.knoxguard.service;

import android.os.Bundle;
import android.util.Slog;
import com.samsung.android.knoxguard.service.KnoxGuardSeService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/* loaded from: classes2.dex */
public abstract class KnoxGuardNative {
    public static int KGTA_FAILED = -1000;
    public static int KGTA_PARAM_DEFAULT = 0;
    public static String TAG = "KnoxGuardTANative";

    public static native KgErrWrapper tz_generateHotpDhRequest(int i);

    public static native KgErrWrapper tz_getClientData(int i);

    public static native KgErrWrapper tz_getHotpChallenge(int i);

    public static native KgErrWrapper tz_getKGID(int i);

    public static native KgErrWrapper tz_getKGPolicy(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_getLockAction(int i);

    public static native KgErrWrapper tz_getLockObject(int i);

    public static native KgErrWrapper tz_getNonce(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_getTAInfo(int i);

    public static native KgErrWrapper tz_getTAState(int i);

    public static native KgErrWrapper tz_lockScreen(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_provisionCert(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public static native KgErrWrapper tz_resetRPMB(int i, byte[] bArr);

    public static native KgErrWrapper tz_setClientData(int i, byte[] bArr);

    public static native KgErrWrapper tz_unlockScreen(int i);

    public static native KgErrWrapper tz_userChecking(int i);

    public static native KgErrWrapper tz_verifyCompleteToken(int i, byte[] bArr);

    public static native KgErrWrapper tz_verifyHOTPPin(int i, byte[] bArr);

    public static native KgErrWrapper tz_verifyHOTPsecret(int i, byte[] bArr);

    public static native KgErrWrapper tz_verifyHotpDhChallenge(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    public static native KgErrWrapper tz_verifyKgRot(int i);

    public static native KgErrWrapper tz_verifyPolicy(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_verifyRegistrationInfo(int i, byte[] bArr, byte[] bArr2);

    public static int getTAState() {
        KgErrWrapper tAStateRefactor = getTAStateRefactor();
        if (tAStateRefactor == null) {
            return KGTA_FAILED;
        }
        int i = tAStateRefactor.err;
        return i == 0 ? tAStateRefactor.result : i;
    }

    public static KgErrWrapper verifyHOTPsecretRefactor(String str) {
        if (str == null) {
            Slog.e(TAG, "verifyHOTPsecret input string is null");
            return null;
        }
        return tz_verifyHOTPsecret(KGTA_PARAM_DEFAULT, s2b(str));
    }

    public static KgErrWrapper getTAStateRefactor() {
        return tz_getTAState(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper getKGPolicyRefactor() {
        PolicyStorageManager policyStorageManager = PolicyStorageManager.getInstance();
        int readData = policyStorageManager.readData();
        if (readData != PolicyStorageManager.SUCCESS) {
            Slog.e(TAG, "readData failed error " + readData);
            return null;
        }
        String policyRes = policyStorageManager.getPolicyRes();
        String signature = policyStorageManager.getSignature();
        if (policyRes == null || signature == null) {
            Slog.e(TAG, "GetKG Policy : policy or signature  null ");
            return null;
        }
        return tz_getKGPolicy(KGTA_PARAM_DEFAULT, s2b(policyRes), s2b(signature));
    }

    public static KgErrWrapper verifyCompleteTokenRefactor(String str) {
        if (str == null) {
            Slog.e(TAG, "verifyCompleteToken input string is null");
            return null;
        }
        return tz_verifyCompleteToken(KGTA_PARAM_DEFAULT, s2b(str));
    }

    public static KgErrWrapper generateHotpDHRequestRefactor() {
        return tz_generateHotpDhRequest(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper verifyHotpDHChallengeRefactor(String str, String str2, String str3) {
        if (str == null || str2 == null || str3 == null) {
            Slog.e(TAG, "verifyHotpDHChallenge failed: input null");
            return null;
        }
        return tz_verifyHotpDhChallenge(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2), s2b(str3));
    }

    public static KgErrWrapper getTAInfo(int i) {
        return tz_getTAInfo(i);
    }

    public static KgErrWrapper provisionCert(String str, String str2, String str3, String str4) {
        return tz_provisionCert(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2), s2b(str3), s2b(str4));
    }

    public static KgErrWrapper getHotpChallengeRefactor() {
        return tz_getHotpChallenge(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper verifyHOTPPinRefactor(String str) {
        if (str == null) {
            Slog.e(TAG, "verifyHotpPin fail, input null");
            return null;
        }
        return tz_verifyHOTPPin(KGTA_PARAM_DEFAULT, s2b(str));
    }

    public static KgErrWrapper verifyRegistrationInfoRefactor(String str, String str2) {
        if (str == null || str2 == null) {
            Slog.e(TAG, "verifyRegistrationInfo failed input null ");
            return null;
        }
        return tz_verifyRegistrationInfo(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2));
    }

    public static KgErrWrapper verifyPolicyRefactor(String str, String str2) {
        if (str == null || str2 == null) {
            Slog.e(TAG, "verifyPolicy failed, empty input");
            return null;
        }
        KgErrWrapper tz_verifyPolicy = tz_verifyPolicy(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2));
        if (tz_verifyPolicy == null) {
            Slog.e(TAG, "verifyPolicy failed, empty return from TA");
            return tz_verifyPolicy;
        }
        String b2s = b2s(tz_verifyPolicy.data);
        if (PolicyStorageManager.getInstance().saveData(str, str2) == PolicyStorageManager.SUCCESS) {
            return tz_verifyPolicy;
        }
        Slog.e(TAG, "store the policy to EFS failed =" + b2s);
        return null;
    }

    public static KgErrWrapper unlockScreenRefactor() {
        return tz_unlockScreen(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper lockScreenRefactor(String str, String str2, String str3, String str4, String str5, boolean z, boolean z2, Bundle bundle) {
        byte[] serialize = serialize(new KnoxGuardSeService.KGLockscreenInfo(str2, str3, str4, str5, z, z2, bundle));
        if (str == null) {
            Slog.e(TAG, "lockScreen: empty actionName");
            return null;
        }
        if (serialize == null) {
            Slog.e(TAG, "lockScreen: empty serialzeObj");
            return null;
        }
        return tz_lockScreen(KGTA_PARAM_DEFAULT, s2b(str), serialize);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x004b, code lost:
    
        if (r4 == null) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0053 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] serialize(Object obj) {
        ObjectOutputStream objectOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        if (obj == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        r2 = null;
        byte[] bArr = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    try {
                        objectOutputStream.writeObject(obj);
                        bArr = byteArrayOutputStream.toByteArray();
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException unused) {
                            Slog.e(TAG, "Serialize failed IO exception");
                        }
                    } catch (IOException e) {
                        e = e;
                        e.printStackTrace();
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (IOException unused2) {
                                Slog.e(TAG, "Serialize failed IO exception");
                            }
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    if (byteArrayOutputStream2 != null) {
                        try {
                            byteArrayOutputStream2.close();
                        } catch (IOException unused3) {
                            Slog.e(TAG, "Serialize failed IO exception");
                        }
                    }
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.close();
                            throw th;
                        } catch (IOException unused4) {
                            Slog.e(TAG, "Serialize outstream failed IO exception");
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                objectOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                objectOutputStream = null;
                byteArrayOutputStream2 = byteArrayOutputStream;
                if (byteArrayOutputStream2 != null) {
                }
                if (objectOutputStream != null) {
                }
            }
        } catch (IOException e3) {
            e = e3;
            byteArrayOutputStream = null;
            objectOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            objectOutputStream = null;
            if (byteArrayOutputStream2 != null) {
            }
            if (objectOutputStream != null) {
            }
        }
        try {
            objectOutputStream.close();
        } catch (IOException unused5) {
            Slog.e(TAG, "Serialize outstream failed IO exception");
        }
        return bArr;
    }

    public static KgErrWrapper getLockActionRefactor() {
        return tz_getLockAction(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper getLockObjectRefactor() {
        return tz_getLockObject(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper getClientDataRefactor() {
        return tz_getClientData(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper getKGIDRefactor() {
        return tz_getKGID(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper verifyKgRotRefactor() {
        return tz_verifyKgRot(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper resetRPMBRefactor(String str) {
        return tz_resetRPMB(KGTA_PARAM_DEFAULT, s2b(str));
    }

    public static KgErrWrapper userCheckingRefactor() {
        return tz_userChecking(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper setClientDataRefactor(String str) {
        if (str == null) {
            Slog.e(TAG, "setClientData fail: empty input");
        }
        return tz_setClientData(KGTA_PARAM_DEFAULT, s2b(str));
    }

    public static KgErrWrapper getNonceRefactor(String str, String str2) {
        return tz_getNonce(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2));
    }

    public static String b2s(byte[] bArr) {
        if (bArr != null) {
            return new String(bArr);
        }
        return null;
    }

    public static byte[] s2b(String str) {
        if (str == null) {
            return null;
        }
        return str.getBytes();
    }

    public class PolicyStorageManager {
        public static int SUCCESS;
        public static PolicyStorageManager instance;
        public String ans_policy = null;
        public String ans_signature = null;

        public static synchronized PolicyStorageManager getInstance() {
            PolicyStorageManager policyStorageManager;
            synchronized (PolicyStorageManager.class) {
                if (instance == null) {
                    instance = new PolicyStorageManager();
                }
                policyStorageManager = instance;
            }
            return policyStorageManager;
        }

        public final void cleanState() {
            this.ans_policy = null;
            this.ans_signature = null;
        }

        public final void storeDataReady(String str, String str2) {
            this.ans_policy = str;
            this.ans_signature = str2;
        }

        public synchronized int saveData(String str, String str2) {
            cleanState();
            Slog.e("KGTAPolicy", "use TA to store policy, skipping the EFS...");
            return SUCCESS;
        }

        public synchronized String getPolicyRes() {
            return this.ans_policy;
        }

        public synchronized String getSignature() {
            return this.ans_signature;
        }

        public synchronized int readData() {
            cleanState();
            Slog.e("KGTAPolicy", "use TA to store policy, skipping the EFS...");
            storeDataReady(" ", " ");
            return SUCCESS;
        }
    }
}
