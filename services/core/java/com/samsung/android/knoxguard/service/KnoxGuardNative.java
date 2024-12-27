package com.samsung.android.knoxguard.service;

import android.os.Bundle;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class KnoxGuardNative {
    public static int KGTA_FAILED = -1000;
    public static int KGTA_PARAM_DEFAULT = 0;
    public static int KG_CMD_CHECKING = 18;
    public static int KG_CMD_GENERATE_DH_REQUEST = 4;
    public static int KG_CMD_GENERATE_HOTP_CHANLLENGE = 3;
    public static int KG_CMD_GET_CLIENT_DATA = 15;
    public static int KG_CMD_GET_KGID = 16;
    public static int KG_CMD_GET_LOCK_ACTION = 13;
    public static int KG_CMD_GET_LOCK_OBJECT = 12;
    public static int KG_CMD_GET_NONCE = 21;
    public static int KG_CMD_GET_POLICY = 8;
    public static int KG_CMD_GET_STATUS = 1;
    public static int KG_CMD_GET_TA_INFO = 22;
    public static int KG_CMD_LOCK = 9;
    public static int KG_CMD_PROCESS_KG_ROT = 20;
    public static int KG_CMD_PROVISION_CERT = 23;
    public static int KG_CMD_RPMB_RESET = 17;
    public static int KG_CMD_SET_CLIENT_DATA = 14;
    public static int KG_CMD_UNLOCK = 10;
    public static int KG_CMD_VERIFY_COMPLETETOKEN = 11;
    public static int KG_CMD_VERIFY_DH_RESPONSE = 5;
    public static int KG_CMD_VERIFY_HOTP_SECRET = 6;
    public static int KG_CMD_VERIFY_POLICY = 7;
    public static int KG_CMD_VERIFY_REGISTRATION_INFO = 2;
    public static String TAG = "KnoxGuardTANative";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyStorageManager {
        public static int DATA_READ_ERROR = -3;
        public static int FILE_MISSING_CANNOT_CREATE = -2;
        public static final String KGTA_POLICY_PATH = "/efs/kgtapolicy";
        public static int PARSING_ERROR = -4;
        public static int STORAGE_ERROR = -1;
        public static int SUCCESS = 0;
        public static final String TAG = "KGTAPolicy";
        public static final boolean USE_TA_STORAGE = true;
        public static PolicyStorageManager instance;
        public String ans_policy = null;
        public String ans_signature = null;

        public static synchronized PolicyStorageManager getInstance() {
            PolicyStorageManager policyStorageManager;
            synchronized (PolicyStorageManager.class) {
                try {
                    if (instance == null) {
                        instance = new PolicyStorageManager();
                    }
                    policyStorageManager = instance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return policyStorageManager;
        }

        public final synchronized void clean() {
            cleanState();
        }

        public final void cleanState() {
            this.ans_policy = null;
            this.ans_signature = null;
        }

        public final String constructTheSavingContent(String str, String str2) {
            if (str == null) {
                str = "";
            }
            if (str2 == null) {
                str2 = "";
            }
            return AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "||", str2);
        }

        public final void createPolicyFile() {
            try {
                new File(KGTA_POLICY_PATH).createNewFile();
            } catch (IOException unused) {
                Slog.e(TAG, "create new policy file failed, error or already created");
            }
        }

        public final synchronized String getPolicyRes() {
            return this.ans_policy;
        }

        public final synchronized String getSignature() {
            return this.ans_signature;
        }

        public final boolean isFileExists() {
            return BatteryService$$ExternalSyntheticOutline0.m45m(KGTA_POLICY_PATH);
        }

        public final int parseDataString(String str) {
            String[] split = str.split("\\|\\|", 3);
            if (split == null || split.length != 2) {
                Slog.e(TAG, "parsing data issue occurred");
                return PARSING_ERROR;
            }
            String str2 = split[0];
            String str3 = split[1];
            this.ans_policy = str2;
            this.ans_signature = str3;
            return 0;
        }

        public final synchronized int readData() {
            cleanState();
            Slog.e(TAG, "use TA to store policy, skipping the EFS...");
            this.ans_policy = " ";
            this.ans_signature = " ";
            return SUCCESS;
        }

        public final synchronized int saveData(String str, String str2) {
            cleanState();
            Slog.e(TAG, "use TA to store policy, skipping the EFS...");
            return SUCCESS;
        }

        public final int savetoFileExternal(String str) {
            BufferedWriter bufferedWriter;
            Throwable th;
            BufferedWriter bufferedWriter2 = null;
            try {
                try {
                    try {
                        bufferedWriter = new BufferedWriter(new FileWriter(KGTA_POLICY_PATH));
                        try {
                            bufferedWriter.write(str);
                            bufferedWriter.close();
                            return 0;
                        } catch (IOException unused) {
                            bufferedWriter2 = bufferedWriter;
                            int i = STORAGE_ERROR;
                            if (bufferedWriter2 != null) {
                                bufferedWriter2.close();
                            }
                            return i;
                        } catch (Throwable th2) {
                            th = th2;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException unused2) {
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        bufferedWriter = bufferedWriter2;
                        th = th3;
                    }
                } catch (IOException unused3) {
                }
            } catch (IOException unused4) {
                return STORAGE_ERROR;
            }
        }

        public final void storeDataReady(String str, String str2) {
            this.ans_policy = str;
            this.ans_signature = str2;
        }
    }

    public static String b2s(byte[] bArr) {
        if (bArr != null) {
            return new String(bArr);
        }
        return null;
    }

    public static String generateHotpDHRequest() {
        KgErrWrapper tz_generateHotpDhRequest = tz_generateHotpDhRequest(KGTA_PARAM_DEFAULT);
        if (tz_generateHotpDhRequest == null) {
            return null;
        }
        return b2s(tz_generateHotpDhRequest.data);
    }

    public static KgErrWrapper generateHotpDHRequestRefactor() {
        return tz_generateHotpDhRequest(KGTA_PARAM_DEFAULT);
    }

    public static String getClientData() {
        KgErrWrapper tz_getClientData = tz_getClientData(KGTA_PARAM_DEFAULT);
        if (tz_getClientData == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("getClientData - errwrapper = "), tz_getClientData.err, TAG);
        return b2s(tz_getClientData.data);
    }

    public static KgErrWrapper getClientDataRefactor() {
        return tz_getClientData(KGTA_PARAM_DEFAULT);
    }

    public static String getHotpChallenge() {
        KgErrWrapper tz_getHotpChallenge = tz_getHotpChallenge(KGTA_PARAM_DEFAULT);
        if (tz_getHotpChallenge == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("getHotpChallenge - errwrapper = "),
                tz_getHotpChallenge.err,
                TAG);
        return b2s(tz_getHotpChallenge.data);
    }

    public static KgErrWrapper getHotpChallengeRefactor() {
        return tz_getHotpChallenge(KGTA_PARAM_DEFAULT);
    }

    public static String getKGID() {
        KgErrWrapper tz_getKGID = tz_getKGID(KGTA_PARAM_DEFAULT);
        if (tz_getKGID == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("getKGID - errwrapper = "), tz_getKGID.err, TAG);
        return b2s(tz_getKGID.data);
    }

    public static KgErrWrapper getKGIDRefactor() {
        return tz_getKGID(KGTA_PARAM_DEFAULT);
    }

    public static String getKGPolicy() {
        KgErrWrapper kGPolicyRefactor = getKGPolicyRefactor();
        if (kGPolicyRefactor == null) {
            return null;
        }
        return kGPolicyRefactor.getStr();
    }

    public static KgErrWrapper getKGPolicyRefactor() {
        PolicyStorageManager policyStorageManager = PolicyStorageManager.getInstance();
        int readData = policyStorageManager.readData();
        if (readData != PolicyStorageManager.SUCCESS) {
            NandswapManager$$ExternalSyntheticOutline0.m(readData, "readData failed error ", TAG);
            return null;
        }
        String policyRes = policyStorageManager.getPolicyRes();
        String signature = policyStorageManager.getSignature();
        if (policyRes != null && signature != null) {
            return tz_getKGPolicy(KGTA_PARAM_DEFAULT, s2b(policyRes), s2b(signature));
        }
        Slog.e(TAG, "GetKG Policy : policy or signature  null ");
        return null;
    }

    public static String getLockAction() {
        KgErrWrapper tz_getLockAction = tz_getLockAction(KGTA_PARAM_DEFAULT);
        if (tz_getLockAction == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("getLockAction - errwrapper = "), tz_getLockAction.err, TAG);
        return b2s(tz_getLockAction.data);
    }

    public static KgErrWrapper getLockActionRefactor() {
        return tz_getLockAction(KGTA_PARAM_DEFAULT);
    }

    public static byte[] getLockObject() {
        KgErrWrapper tz_getLockObject = tz_getLockObject(KGTA_PARAM_DEFAULT);
        if (tz_getLockObject == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("getLockObject - errwrapper = "), tz_getLockObject.err, TAG);
        return tz_getLockObject.data;
    }

    public static KgErrWrapper getLockObjectRefactor() {
        return tz_getLockObject(KGTA_PARAM_DEFAULT);
    }

    public static String getNonce(String str, String str2) {
        KgErrWrapper nonceRefactor = getNonceRefactor(str, str2);
        if (nonceRefactor == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("getNonce - errwrapper = "), nonceRefactor.err, TAG);
        return b2s(nonceRefactor.data);
    }

    public static KgErrWrapper getNonceRefactor(String str, String str2) {
        return tz_getNonce(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2));
    }

    public static KgErrWrapper getSfPolicy() {
        return tz_getSfPolicy(KGTA_PARAM_DEFAULT);
    }

    public static KgErrWrapper getTAInfo(int i) {
        return tz_getTAInfo(i);
    }

    public static int getTAState() {
        KgErrWrapper tz_getTAState = tz_getTAState(KGTA_PARAM_DEFAULT);
        if (tz_getTAState == null) {
            return KGTA_FAILED;
        }
        int i = tz_getTAState.err;
        return i == 0 ? tz_getTAState.result : i;
    }

    public static KgErrWrapper getTAStateRefactor() {
        return tz_getTAState(KGTA_PARAM_DEFAULT);
    }

    public static int lockScreen(
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            boolean z,
            boolean z2,
            Bundle bundle) {
        KgErrWrapper lockScreenRefactor =
                lockScreenRefactor(str, str2, str3, str4, str5, z, z2, bundle);
        if (lockScreenRefactor == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("lockScreen - errwrapper = "), lockScreenRefactor.err, TAG);
        int i = lockScreenRefactor.err;
        return i == 0 ? lockScreenRefactor.result : i;
    }

    public static KgErrWrapper lockScreenRefactor(
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            boolean z,
            boolean z2,
            Bundle bundle) {
        byte[] serialize =
                serialize(
                        new KnoxGuardSeService.KGLockscreenInfo(
                                str2, str3, str4, str5, z, z2, bundle));
        if (str == null) {
            Slog.e(TAG, "lockScreen: empty actionName");
            return null;
        }
        if (serialize != null) {
            return tz_lockScreen(KGTA_PARAM_DEFAULT, s2b(str), serialize);
        }
        Slog.e(TAG, "lockScreen: empty serialzeObj");
        return null;
    }

    public static KgErrWrapper provisionCert(String str, String str2, String str3, String str4) {
        return tz_provisionCert(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2), s2b(str3), s2b(str4));
    }

    public static int resetRPMB() {
        return resetRPMB(null);
    }

    public static int resetRPMB(String str) {
        KgErrWrapper resetRPMBRefactor = resetRPMBRefactor(str);
        if (resetRPMBRefactor == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("resetRPMB - errwrapper = "), resetRPMBRefactor.err, TAG);
        int i = resetRPMBRefactor.err;
        return i == 0 ? resetRPMBRefactor.result : i;
    }

    public static KgErrWrapper resetRPMBRefactor(String str) {
        return tz_resetRPMB(KGTA_PARAM_DEFAULT, s2b(str));
    }

    public static byte[] s2b(String str) {
        if (str == null) {
            return null;
        }
        return str.getBytes();
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x004e, code lost:

       if (r4 == null) goto L35;
    */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0054 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] serialize(java.lang.Object r5) {
        /*
            java.lang.String r0 = "Serialize outstream failed IO exception"
            java.lang.String r1 = "Serialize failed IO exception"
            if (r5 != 0) goto La
            r5 = 0
            byte[] r5 = new byte[r5]
            return r5
        La:
            r2 = 0
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L3a java.io.IOException -> L3d
            r3.<init>()     // Catch: java.lang.Throwable -> L3a java.io.IOException -> L3d
            java.io.ObjectOutputStream r4 = new java.io.ObjectOutputStream     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L37
            r4.<init>(r3)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L37
            r4.writeObject(r5)     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L32
            byte[] r2 = r3.toByteArray()     // Catch: java.lang.Throwable -> L2f java.io.IOException -> L32
            r3.close()     // Catch: java.io.IOException -> L20
            goto L25
        L20:
            java.lang.String r5 = com.samsung.android.knoxguard.service.KnoxGuardNative.TAG
            android.util.Slog.e(r5, r1)
        L25:
            r4.close()     // Catch: java.io.IOException -> L29
            goto L51
        L29:
            java.lang.String r5 = com.samsung.android.knoxguard.service.KnoxGuardNative.TAG
            android.util.Slog.e(r5, r0)
            goto L51
        L2f:
            r5 = move-exception
        L30:
            r2 = r3
            goto L52
        L32:
            r5 = move-exception
            goto L40
        L34:
            r5 = move-exception
            r4 = r2
            goto L30
        L37:
            r5 = move-exception
            r4 = r2
            goto L40
        L3a:
            r5 = move-exception
            r4 = r2
            goto L52
        L3d:
            r5 = move-exception
            r3 = r2
            r4 = r3
        L40:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L2f
            if (r3 == 0) goto L4e
            r3.close()     // Catch: java.io.IOException -> L49
            goto L4e
        L49:
            java.lang.String r5 = com.samsung.android.knoxguard.service.KnoxGuardNative.TAG
            android.util.Slog.e(r5, r1)
        L4e:
            if (r4 == 0) goto L51
            goto L25
        L51:
            return r2
        L52:
            if (r2 == 0) goto L5d
            r2.close()     // Catch: java.io.IOException -> L58
            goto L5d
        L58:
            java.lang.String r2 = com.samsung.android.knoxguard.service.KnoxGuardNative.TAG
            android.util.Slog.e(r2, r1)
        L5d:
            if (r4 == 0) goto L68
            r4.close()     // Catch: java.io.IOException -> L63
            goto L68
        L63:
            java.lang.String r1 = com.samsung.android.knoxguard.service.KnoxGuardNative.TAG
            android.util.Slog.e(r1, r0)
        L68:
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.knoxguard.service.KnoxGuardNative.serialize(java.lang.Object):byte[]");
    }

    public static int setClientData(String str) {
        KgErrWrapper clientDataRefactor = setClientDataRefactor(str);
        if (clientDataRefactor == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("setClientData - errwrapper = "), clientDataRefactor.err, TAG);
        int i = clientDataRefactor.err;
        return i == 0 ? clientDataRefactor.result : i;
    }

    public static KgErrWrapper setClientDataRefactor(String str) {
        if (str == null) {
            Slog.e(TAG, "setClientData fail: empty input");
        }
        return tz_setClientData(KGTA_PARAM_DEFAULT, s2b(str));
    }

    public static native KgErrWrapper tz_generateHotpDhRequest(int i);

    public static native KgErrWrapper tz_getClientData(int i);

    public static native KgErrWrapper tz_getHotpChallenge(int i);

    public static native KgErrWrapper tz_getKGID(int i);

    public static native KgErrWrapper tz_getKGPolicy(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_getLockAction(int i);

    public static native KgErrWrapper tz_getLockObject(int i);

    public static native KgErrWrapper tz_getNonce(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_getSfPolicy(int i);

    public static native KgErrWrapper tz_getTAInfo(int i);

    public static native KgErrWrapper tz_getTAState(int i);

    public static native KgErrWrapper tz_lockScreen(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_provisionCert(
            int i, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public static native KgErrWrapper tz_resetRPMB(int i, byte[] bArr);

    public static native KgErrWrapper tz_setClientData(int i, byte[] bArr);

    public static native KgErrWrapper tz_unlockScreen(int i);

    public static native KgErrWrapper tz_userChecking(int i);

    public static native KgErrWrapper tz_verifyCompleteToken(int i, byte[] bArr);

    public static native KgErrWrapper tz_verifyHOTPPin(int i, byte[] bArr);

    public static native KgErrWrapper tz_verifyHOTPsecret(int i, byte[] bArr);

    public static native KgErrWrapper tz_verifyHotpDhChallenge(
            int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    public static native KgErrWrapper tz_verifyKgRot(int i);

    public static native KgErrWrapper tz_verifyPolicy(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_verifyRegistrationInfo(int i, byte[] bArr, byte[] bArr2);

    public static native KgErrWrapper tz_verifySfPolicy(int i, byte[] bArr, byte[] bArr2);

    public static int unlockScreen() {
        KgErrWrapper tz_unlockScreen = tz_unlockScreen(KGTA_PARAM_DEFAULT);
        if (tz_unlockScreen == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("unlockScreen - errwrapper = "), tz_unlockScreen.err, TAG);
        int i = tz_unlockScreen.err;
        return i == 0 ? tz_unlockScreen.result : i;
    }

    public static KgErrWrapper unlockScreenRefactor() {
        return tz_unlockScreen(KGTA_PARAM_DEFAULT);
    }

    public static int userChecking() {
        KgErrWrapper tz_userChecking = tz_userChecking(KGTA_PARAM_DEFAULT);
        if (tz_userChecking == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("userChecking - errwrapper = "), tz_userChecking.err, TAG);
        int i = tz_userChecking.err;
        return i == 0 ? tz_userChecking.result : i;
    }

    public static KgErrWrapper userCheckingRefactor() {
        return tz_userChecking(KGTA_PARAM_DEFAULT);
    }

    public static int verifyCompleteToken(String str) {
        KgErrWrapper verifyCompleteTokenRefactor = verifyCompleteTokenRefactor(str);
        if (verifyCompleteTokenRefactor == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("verifyCompleteToken - errwrapper = "),
                verifyCompleteTokenRefactor.err,
                TAG);
        int i = verifyCompleteTokenRefactor.err;
        return i == 0 ? verifyCompleteTokenRefactor.result : i;
    }

    public static KgErrWrapper verifyCompleteTokenRefactor(String str) {
        if (str != null) {
            return tz_verifyCompleteToken(KGTA_PARAM_DEFAULT, s2b(str));
        }
        Slog.e(TAG, "verifyCompleteToken input string is null");
        return null;
    }

    public static int verifyHOTPPin(String str) {
        KgErrWrapper verifyHOTPPinRefactor = verifyHOTPPinRefactor(str);
        if (verifyHOTPPinRefactor == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("verifyHOTPPin - errwrapper = "), verifyHOTPPinRefactor.err, TAG);
        int i = verifyHOTPPinRefactor.err;
        return i == 0 ? verifyHOTPPinRefactor.result : i;
    }

    public static KgErrWrapper verifyHOTPPinRefactor(String str) {
        if (str != null) {
            return tz_verifyHOTPPin(KGTA_PARAM_DEFAULT, s2b(str));
        }
        Slog.e(TAG, "verifyHotpPin fail, input null");
        return null;
    }

    public static int verifyHOTPsecret(String str) {
        if (str == null) {
            Slog.e(TAG, "verifyHOTPsecret input string is null");
            return KGTA_FAILED;
        }
        KgErrWrapper verifyHOTPsecretRefactor = verifyHOTPsecretRefactor(str);
        if (verifyHOTPsecretRefactor == null) {
            return KGTA_FAILED;
        }
        int i = verifyHOTPsecretRefactor.err;
        return i == 0 ? verifyHOTPsecretRefactor.result : i;
    }

    public static KgErrWrapper verifyHOTPsecretRefactor(String str) {
        if (str != null) {
            return tz_verifyHOTPsecret(KGTA_PARAM_DEFAULT, s2b(str));
        }
        Slog.e(TAG, "verifyHOTPsecret input string is null");
        return null;
    }

    public static int verifyHotpDHChallenge(String str, String str2, String str3) {
        KgErrWrapper verifyHotpDHChallengeRefactor = verifyHotpDHChallengeRefactor(str, str2, str3);
        if (verifyHotpDHChallengeRefactor == null) {
            return KGTA_FAILED;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("verifyHotpDHChallenge - errwrapper = "),
                verifyHotpDHChallengeRefactor.err,
                TAG);
        int i = verifyHotpDHChallengeRefactor.err;
        return i == 0 ? verifyHotpDHChallengeRefactor.result : i;
    }

    public static KgErrWrapper verifyHotpDHChallengeRefactor(String str, String str2, String str3) {
        if (str != null && str2 != null && str3 != null) {
            return tz_verifyHotpDhChallenge(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2), s2b(str3));
        }
        Slog.e(TAG, "verifyHotpDHChallenge failed: input null");
        return null;
    }

    public static String verifyKgRot() {
        KgErrWrapper tz_verifyKgRot = tz_verifyKgRot(KGTA_PARAM_DEFAULT);
        if (tz_verifyKgRot == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("verifyKgRot - errwrapper = "), tz_verifyKgRot.err, TAG);
        return b2s(tz_verifyKgRot.data);
    }

    public static KgErrWrapper verifyKgRotRefactor() {
        return tz_verifyKgRot(KGTA_PARAM_DEFAULT);
    }

    public static String verifyPolicy(String str, String str2) {
        KgErrWrapper verifyPolicyRefactor = verifyPolicyRefactor(str, str2);
        if (verifyPolicyRefactor == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("verifyPolicy - errwrapper = "), verifyPolicyRefactor.err, TAG);
        return b2s(verifyPolicyRefactor.data);
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
        if (PolicyStorageManager.getInstance().saveData(str, str2)
                == PolicyStorageManager.SUCCESS) {
            return tz_verifyPolicy;
        }
        BootReceiver$$ExternalSyntheticOutline0.m("store the policy to EFS failed =", b2s, TAG);
        return null;
    }

    public static String verifyRegistrationInfo(String str, String str2) {
        KgErrWrapper verifyRegistrationInfoRefactor = verifyRegistrationInfoRefactor(str, str2);
        if (verifyRegistrationInfoRefactor == null) {
            return null;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("verifyRegistrationInfo - errwrapper = "),
                verifyRegistrationInfoRefactor.err,
                TAG);
        return b2s(verifyRegistrationInfoRefactor.data);
    }

    public static KgErrWrapper verifyRegistrationInfoRefactor(String str, String str2) {
        if (str != null && str2 != null) {
            return tz_verifyRegistrationInfo(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2));
        }
        Slog.e(TAG, "verifyRegistrationInfo failed input null ");
        return null;
    }

    public static KgErrWrapper verifySfPolicy(String str, String str2) {
        if (str != null && str2 != null) {
            return tz_verifySfPolicy(KGTA_PARAM_DEFAULT, s2b(str), s2b(str2));
        }
        Slog.e(TAG, "verifySfPolicy failed, empty input");
        return null;
    }
}
