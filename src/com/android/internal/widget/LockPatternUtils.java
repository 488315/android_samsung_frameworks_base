package com.android.internal.widget;

import android.Manifest;
import android.app.PropertyInvalidatedCache;
import android.app.RemoteLockscreenValidationResult;
import android.app.RemoteLockscreenValidationSession;
import android.app.admin.DevicePolicyManager;
import android.app.admin.PasswordMetrics;
import android.app.trust.IStrongAuthTracker;
import android.app.trust.TrustManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.hardware.input.InputManagerGlobal;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.InputDevice;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.security.Flags;
import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternView;
import com.android.server.LocalServices;
import com.google.android.collect.Lists;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.StreamCipher;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback;
import com.samsung.android.lock.LsConstants;
import com.samsung.android.lock.LsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/* loaded from: classes6.dex */
public class LockPatternUtils implements LsConstants {
    private static final String APP_LOCK_FINGERPRINT_LOCKSCREEN_KEY = "lockscreen.applock_fingerprint";
    public static final String AUTO_PIN_CONFIRM = "lockscreen.auto_pin_confirm";
    private static final String CREDENTIAL_TYPE_API = "getCredentialType";
    public static final int CREDENTIAL_TYPE_NONE = -1;
    public static final int CREDENTIAL_TYPE_PASSWORD = 4;
    public static final int CREDENTIAL_TYPE_PASSWORD_OR_PIN = 2;
    public static final int CREDENTIAL_TYPE_PATTERN = 1;
    public static final int CREDENTIAL_TYPE_PIN = 3;
    public static final int CREDENTIAL_TYPE_SMARTCARDNUMERIC = 6;
    public static final String CURRENT_LSKF_BASED_PROTECTOR_ID_KEY = "sp-handle";
    public static final String DISABLE_LOCKSCREEN_KEY = "lockscreen.disabled";
    public static final int DUAL_DAR_DO_OPT_PENDING_UNLOCK = 1;
    private static final String ENABLED_TRUST_AGENTS = "lockscreen.enabledtrustagents";
    public static final byte[] ENCRYPTED_REMOTE_CREDENTIALS_HEADER = "encrypted_remote_credentials".getBytes(StandardCharsets.UTF_8);
    public static final int FAILED_ATTEMPTS_BEFORE_WIPE_GRACE = 5;
    public static final long FAILED_ATTEMPT_COUNTDOWN_INTERVAL_MS = 1000;
    public static final String FLAG_ENABLE_AUTO_PIN_CONFIRMATION = "AutoPinConfirmation__enable_auto_pin_confirmation";
    private static final boolean FRP_CREDENTIAL_ENABLED = true;
    private static final String GSI_RUNNING_PROP = "ro.gsid.image_running";
    private static final String IS_TRUST_USUALLY_MANAGED = "lockscreen.istrustusuallymanaged";
    private static final String KNOWN_TRUST_AGENTS = "lockscreen.knowntrustagents";
    public static final String KNOX_DEVICE_OWNER_KEY = "knox.device_owner";
    public static final String LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS = "lockscreen.power_button_instantly_locks";

    @Deprecated
    public static final String LOCKSCREEN_WIDGETS_ENABLED = "lockscreen.widgets_enabled";
    public static final String LOCK_PASSWORD_SALT_KEY = "lockscreen.password_salt";
    private static final String LOCK_PIN_ENHANCED_PRIVACY = "pin_enhanced_privacy";
    public static final String LOCK_SCREEN_DEVICE_OWNER_INFO = "lockscreen.device_owner_info";
    public static final String LOCK_SCREEN_OWNER_INFO = "lock_screen_owner_info";
    public static final String LOCK_SCREEN_OWNER_INFO_ENABLED = "lock_screen_owner_info_enabled";
    public static final String MIGRATED_MDFPP_PWD_DATA = "migrated_mdfpp_pwd_data";
    public static final int MIN_AUTO_PIN_REQUIREMENT_LENGTH = 6;
    public static final int MIN_LOCK_PASSWORD_SIZE = 4;
    public static final int MIN_LOCK_PATTERN_SIZE = 4;
    public static final int MIN_PATTERN_REGISTER_FAIL = 4;
    public static final String PASSWORD_HISTORY_DELIMITER = ",";
    public static final String PASSWORD_HISTORY_KEY = "lockscreen.passwordhistory";

    @Deprecated
    public static final String PASSWORD_TYPE_ALTERNATE_KEY = "lockscreen.password_type_alternate";
    public static final String PASSWORD_TYPE_KEY = "lockscreen.password_type";
    public static final int PIN_LENGTH_UNAVAILABLE = -1;
    public static final String SDP_MDFPPMODE_ENABLED_FOR_SYSTEM_KEY = "sdp-mdfppmode-for-system";
    private static final String TAG = "LockPatternUtils";
    public static final int USER_FRP = -9999;
    public static final int USER_REPAIR_MODE = -9998;
    public static final int VERIFY_FLAG_REQUEST_GK_PW_HANDLE = 1;
    public static final int VERIFY_FLAG_WRITE_REPAIR_MODE_PW = 2;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final PropertyInvalidatedCache<Integer, Integer> mCredentialTypeCache;
    private final PropertyInvalidatedCache.QueryHandler<Integer, Integer> mCredentialTypeQuery;
    private IDarManagerService mDarManagerService;
    private DevicePolicyManager mDevicePolicyManager;
    private final Handler mHandler;
    private Boolean mHasSecureLockScreen;
    private LockPatternUtilForDualDarDo mLockPatternUtilForDualDarDo;
    private ILockSettings mLockSettingsService;
    private final SparseLongArray mLockoutDeadlines;
    private UserManager mUserManager;

    public interface CheckCredentialProgressCallback {
        void onEarlyMatched();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CredentialType {
    }

    public interface DualDarAuthProgressCallback {
        void onInnerLayerUnlockFailed();

        void onInnerLayerUnlocked();
    }

    public interface EscrowTokenStateChangeCallback {
        void onEscrowTokenActivated(long j, int i);
    }

    public enum SecAppLockType {
        None,
        Pattern,
        Password,
        PIN,
        BackupPin,
        FingerPrint
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VerifyFlag {
    }

    public static boolean isAutoPinConfirmFeatureAvailable() {
        return true;
    }

    public static boolean isQualityAlphabeticPassword(int i) {
        return i >= 262144;
    }

    public static boolean isQualityNumericPin(int i) {
        return i == 131072 || i == 196608;
    }

    public static boolean isQualitySmartCard(int i) {
        return i == 458752;
    }

    private boolean isSdpSupportedSecureFolder(int i) {
        return false;
    }

    public boolean isRMMLockEnabled(int i) {
        return false;
    }

    public static String credentialTypeToString(int i) {
        if (i == -1) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "PATTERN";
        }
        if (i == 6) {
            return "SMARTCARDNUMERIC";
        }
        if (i == 3) {
            return "PIN";
        }
        if (i == 4) {
            return "PASSWORD";
        }
        return "UNKNOWN_" + i;
    }

    public boolean isTrustUsuallyManaged(int i) {
        if (!(this.mLockSettingsService instanceof ILockSettings.Stub)) {
            throw new IllegalStateException("May only be called by TrustManagerService. Use TrustManager.isTrustUsuallyManaged()");
        }
        try {
            return getLockSettings().getBoolean(IS_TRUST_USUALLY_MANAGED, false, i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setTrustUsuallyManaged(boolean z, int i) {
        try {
            getLockSettings().setBoolean(IS_TRUST_USUALLY_MANAGED, z, i);
        } catch (RemoteException unused) {
        }
    }

    public void userPresent(int i) {
        try {
            getLockSettings().userPresent(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class RequestThrottledException extends Exception {
        private int mTimeoutMs;

        public RequestThrottledException(int i) {
            this.mTimeoutMs = i;
        }

        public int getTimeoutMs() {
            return this.mTimeoutMs;
        }
    }

    public static byte[] newNonMovableByteArray(int i) {
        if (!Flags.secureArrayZeroization()) {
            return new byte[i];
        }
        return ArrayUtils.newNonMovableByteArray(i);
    }

    public static char[] newNonMovableCharArray(int i) {
        if (!Flags.secureArrayZeroization()) {
            return new char[i];
        }
        return ArrayUtils.newNonMovableCharArray(i);
    }

    public static void zeroize(byte[] bArr) {
        if (Flags.secureArrayZeroization()) {
            ArrayUtils.zeroize(bArr);
        } else if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
        }
    }

    public static void zeroize(char[] cArr) {
        if (Flags.secureArrayZeroization()) {
            ArrayUtils.zeroize(cArr);
        } else if (cArr != null) {
            Arrays.fill(cArr, (char) 0);
        }
    }

    public DevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
            this.mDevicePolicyManager = devicePolicyManager;
            if (devicePolicyManager == null) {
                Log.e(TAG, "Can't get DevicePolicyManagerService: is it running?", new IllegalStateException("Stack trace:"));
            }
        }
        return this.mDevicePolicyManager;
    }

    private UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    private TrustManager getTrustManager() {
        TrustManager trustManager = (TrustManager) this.mContext.getSystemService(Context.TRUST_SERVICE);
        if (trustManager == null) {
            Log.e(TAG, "Can't get TrustManagerService: is it running?", new IllegalStateException("Stack trace:"));
        }
        return trustManager;
    }

    public LockPatternUtils(Context context) {
        this(context, null);
    }

    public LockPatternUtils(Context context, ILockSettings iLockSettings) {
        this.mLockoutDeadlines = new SparseLongArray();
        PropertyInvalidatedCache.QueryHandler<Integer, Integer> queryHandler = new PropertyInvalidatedCache.QueryHandler<Integer, Integer>() { // from class: com.android.internal.widget.LockPatternUtils.1
            @Override // android.app.PropertyInvalidatedCache.QueryHandler
            public Integer apply(Integer num) {
                try {
                    return Integer.valueOf(LockPatternUtils.this.getLockSettings().getCredentialType(num.intValue()));
                } catch (RemoteException e) {
                    Log.e(LockPatternUtils.TAG, "failed to get credential type", e);
                    return -1;
                }
            }

            @Override // android.app.PropertyInvalidatedCache.QueryHandler
            public boolean shouldBypassCache(Integer num) {
                return LockPatternUtils.isSpecialUserId(num.intValue());
            }
        };
        this.mCredentialTypeQuery = queryHandler;
        this.mCredentialTypeCache = new PropertyInvalidatedCache<>(4, "system_server", CREDENTIAL_TYPE_API, CREDENTIAL_TYPE_API, queryHandler);
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        Looper looperMyLooper = Looper.myLooper();
        this.mHandler = looperMyLooper != null ? new Handler(looperMyLooper) : null;
        this.mLockSettingsService = iLockSettings;
    }

    public ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    public int getRequestedMinimumPasswordLength(int i) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(i)) {
            return getLockPatternUtilForDualDarDo().getPasswordMinimumLengthForInner();
        }
        return getDevicePolicyManager().getPasswordMinimumLength(null, i);
    }

    public int getMaximumPasswordLength(int i) {
        return getDevicePolicyManager().getPasswordMaximumLength(i);
    }

    public PasswordMetrics getRequestedPasswordMetrics(int i) {
        return getRequestedPasswordMetrics(i, false);
    }

    public PasswordMetrics getRequestedPasswordMetrics(int i, boolean z) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(i)) {
            PasswordMetrics passwordMinimumMetrics = getDevicePolicyManager().getPasswordMinimumMetrics(0, z);
            passwordMinimumMetrics.length = getLockPatternUtilForDualDarDo().getPasswordMinimumLengthForInner();
            return passwordMinimumMetrics;
        }
        return getDevicePolicyManager().getPasswordMinimumMetrics(i, z);
    }

    private int getRequestedPasswordHistoryLength(int i) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(i)) {
            return getDevicePolicyManager().getPasswordHistoryLength(null, 0);
        }
        return getDevicePolicyManager().getPasswordHistoryLength(null, i);
    }

    public int getRequestedPasswordComplexity(int i) {
        return getRequestedPasswordComplexity(i, false);
    }

    public int getRequestedPasswordComplexity(int i, boolean z) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(i)) {
            return getDevicePolicyManager().getAggregatedPasswordComplexityForUser(0, z);
        }
        return getDevicePolicyManager().getAggregatedPasswordComplexityForUser(i, z);
    }

    public void reportFailedPasswordAttempt(int i) {
        int failureCount;
        if (isSpecialUserId(this.mContext, i, true)) {
            return;
        }
        if (i == -9899) {
            int i2 = ((int) getLong(LsConstants.PREV_ATTEMPTS_COUNT, 0L, 0)) + 1;
            if (i2 < 3) {
                setLong(LsConstants.PREV_ATTEMPTS_COUNT, i2, 0);
                return;
            }
            return;
        }
        try {
            failureCount = getLockSettings().getFailureCount(i);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to getFailureCount", e);
            failureCount = 0;
        }
        Log.w(TAG, "getFailureCount = " + failureCount);
        if (failureCount > 0) {
            getDevicePolicyManager().reportFailedPasswordAttemptWithFailureCount(i, failureCount);
        }
        if (Flags.shouldTrustManagerListenForPrimaryAuth()) {
            return;
        }
        getTrustManager().reportUnlockAttempt(false, i);
    }

    public void reportSuccessfulPasswordAttempt(int i) {
        if (isSpecialUserId(this.mContext, i, true)) {
            return;
        }
        if (i == -9899) {
            setLong(LsConstants.PREV_ATTEMPTS_COUNT, 0L, 0);
            return;
        }
        getDevicePolicyManager().reportSuccessfulPasswordAttempt(i);
        if (Flags.shouldTrustManagerListenForPrimaryAuth()) {
            return;
        }
        getTrustManager().reportUnlockAttempt(true, i);
    }

    public void reportPasswordLockout(int i, int i2) {
        if (isSpecialUserId(this.mContext, i2, true) || i2 == -9899) {
            return;
        }
        getTrustManager().reportUnlockLockout(i, i2);
    }

    public int getCurrentFailedPasswordAttempts(int i) {
        if (isSpecialUserId(this.mContext, i, true)) {
            return 0;
        }
        if (i == -9899) {
            return (int) getLong(LsConstants.PREV_ATTEMPTS_COUNT, 0L, 0);
        }
        return getDevicePolicyManager().getCurrentFailedPasswordAttempts(i);
    }

    public int getMaximumFailedPasswordsForWipe(int i) {
        if (isSpecialUserId(this.mContext, i, true) || i == -9899) {
            return 0;
        }
        return getDevicePolicyManager().getMaximumFailedPasswordsForWipe(null, i);
    }

    public boolean writeRepairModeCredential(int i) {
        throwIfCalledOnMainThread();
        try {
            return getLockSettings().writeRepairModeCredential(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to write repair mode credential", e);
            return false;
        }
    }

    public VerifyCredentialResponse verifyCredential(LockscreenCredential lockscreenCredential, int i, int i2) {
        throwIfCalledOnMainThread();
        LsLog.verifyRequest(i, Process.myPid(), this.mContext.getPackageName());
        try {
            if (isEnterpriseUser(i)) {
                return verifyCredentialForEnterpriseUser(lockscreenCredential, i, i2);
            }
            VerifyCredentialResponse verifyCredentialResponseVerifyCredential = getLockSettings().verifyCredential(lockscreenCredential, i, i2);
            return verifyCredentialResponseVerifyCredential == null ? VerifyCredentialResponse.ERROR : verifyCredentialResponseVerifyCredential;
        } catch (RemoteException e) {
            Log.e(TAG, "failed to verify credential", e);
            return VerifyCredentialResponse.ERROR;
        }
    }

    private VerifyCredentialResponse verifyCredentialForEnterpriseUser(LockscreenCredential lockscreenCredential, int i, int i2) throws Throwable {
        LockscreenCredential lockscreenCredentialEncryptStream;
        try {
            lockscreenCredentialEncryptStream = StreamCipher.encryptStream(lockscreenCredential);
            try {
                VerifyCredentialResponse verifyCredentialResponse = (VerifyCredentialResponse) Objects.requireNonNullElse(getLockSettings().verifyCredential(lockscreenCredentialEncryptStream, i, i2), VerifyCredentialResponse.ERROR);
                if (lockscreenCredentialEncryptStream != null) {
                    lockscreenCredentialEncryptStream.zeroize();
                }
                return verifyCredentialResponse;
            } catch (Throwable th) {
                th = th;
                if (lockscreenCredentialEncryptStream != null) {
                    lockscreenCredentialEncryptStream.zeroize();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            lockscreenCredentialEncryptStream = null;
        }
    }

    public VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) {
        try {
            VerifyCredentialResponse verifyCredentialResponseVerifyGatekeeperPasswordHandle = getLockSettings().verifyGatekeeperPasswordHandle(j, j2, i);
            return verifyCredentialResponseVerifyGatekeeperPasswordHandle == null ? VerifyCredentialResponse.ERROR : verifyCredentialResponseVerifyGatekeeperPasswordHandle;
        } catch (RemoteException e) {
            Log.e(TAG, "failed to verify gatekeeper password", e);
            return VerifyCredentialResponse.ERROR;
        }
    }

    public void removeGatekeeperPasswordHandle(long j) {
        try {
            getLockSettings().removeGatekeeperPasswordHandle(j);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to remove gatekeeper password handle", e);
        }
    }

    public boolean checkCredential(LockscreenCredential lockscreenCredential, int i, CheckCredentialProgressCallback checkCredentialProgressCallback) throws RequestThrottledException {
        throwIfCalledOnMainThread();
        LsLog.verifyRequest(i, Process.myPid(), this.mContext.getPackageName());
        try {
            if (isEnterpriseUser(i)) {
                return checkCredentialForEnterpriseUser(lockscreenCredential, i, checkCredentialProgressCallback);
            }
            VerifyCredentialResponse verifyCredentialResponseCheckCredential = getLockSettings().checkCredential(lockscreenCredential, i, wrapCallback(checkCredentialProgressCallback));
            Log.w(TAG, "checkCredential : return result");
            if (verifyCredentialResponseCheckCredential == null) {
                return false;
            }
            if (verifyCredentialResponseCheckCredential.getResponseCode() == 0) {
                return true;
            }
            if (verifyCredentialResponseCheckCredential.getResponseCode() != 1) {
                return false;
            }
            throw new RequestThrottledException(verifyCredentialResponseCheckCredential.getTimeout());
        } catch (RemoteException e) {
            Log.e(TAG, "failed to check credential", e);
            return false;
        }
    }

    private boolean checkCredentialForEnterpriseUser(LockscreenCredential lockscreenCredential, int i, CheckCredentialProgressCallback checkCredentialProgressCallback) throws RequestThrottledException, RemoteException {
        LockscreenCredential lockscreenCredentialEncryptStream = null;
        try {
            try {
                lockscreenCredentialEncryptStream = StreamCipher.encryptStream(lockscreenCredential);
                VerifyCredentialResponse verifyCredentialResponseCheckCredential = getLockSettings().checkCredential(lockscreenCredentialEncryptStream, i, wrapCallback(checkCredentialProgressCallback));
                if (verifyCredentialResponseCheckCredential == null) {
                    if (lockscreenCredentialEncryptStream != null) {
                        lockscreenCredentialEncryptStream.zeroize();
                    }
                    return false;
                }
                if (verifyCredentialResponseCheckCredential.getResponseCode() == 0) {
                    if (lockscreenCredentialEncryptStream != null) {
                        lockscreenCredentialEncryptStream.zeroize();
                    }
                    return true;
                }
                if (verifyCredentialResponseCheckCredential.getResponseCode() == 1) {
                    throw new RequestThrottledException(verifyCredentialResponseCheckCredential.getTimeout());
                }
                if (lockscreenCredentialEncryptStream != null) {
                    lockscreenCredentialEncryptStream.zeroize();
                }
                return false;
            } catch (RuntimeException e) {
                if (!SemPersonaManager.isKnoxId(i)) {
                    throw e;
                }
                e.printStackTrace();
                if (lockscreenCredentialEncryptStream != null) {
                    lockscreenCredentialEncryptStream.zeroize();
                }
                return false;
            }
        } catch (Throwable th) {
            if (lockscreenCredentialEncryptStream != null) {
                lockscreenCredentialEncryptStream.zeroize();
            }
            throw th;
        }
    }

    public VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential lockscreenCredential, int i, int i2) {
        throwIfCalledOnMainThread();
        try {
            VerifyCredentialResponse verifyCredentialResponseVerifyTiedProfileChallenge = getLockSettings().verifyTiedProfileChallenge(lockscreenCredential, i, i2);
            return verifyCredentialResponseVerifyTiedProfileChallenge == null ? VerifyCredentialResponse.ERROR : verifyCredentialResponseVerifyTiedProfileChallenge;
        } catch (RemoteException e) {
            Log.e(TAG, "failed to verify tied profile credential", e);
            return VerifyCredentialResponse.ERROR;
        }
    }

    public byte[] getPasswordHistoryHashFactor(LockscreenCredential lockscreenCredential, int i) {
        LsLog.verifyRequest(i, Process.myPid(), this.mContext.getPackageName());
        try {
            if (isEnterpriseUser(i)) {
                return getPasswordHistoryHashFactorForEnterpriseUser(lockscreenCredential, i);
            }
            return getLockSettings().getHashFactor(lockscreenCredential, i);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to get hash factor", e);
            return null;
        }
    }

    private byte[] getPasswordHistoryHashFactorForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) throws Throwable {
        LockscreenCredential lockscreenCredentialEncryptStream;
        try {
            lockscreenCredentialEncryptStream = StreamCipher.encryptStream(lockscreenCredential);
            try {
                byte[] hashFactor = getLockSettings().getHashFactor(lockscreenCredentialEncryptStream, i);
                if (lockscreenCredentialEncryptStream != null) {
                    lockscreenCredentialEncryptStream.zeroize();
                }
                return hashFactor;
            } catch (Throwable th) {
                th = th;
                if (lockscreenCredentialEncryptStream != null) {
                    lockscreenCredentialEncryptStream.zeroize();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            lockscreenCredentialEncryptStream = null;
        }
    }

    public boolean checkPasswordHistory(byte[] bArr, byte[] bArr2, int i) throws NoSuchAlgorithmException {
        int requestedPasswordHistoryLength;
        if (bArr == null || bArr.length == 0) {
            Log.e(TAG, "checkPasswordHistory: empty password");
            return false;
        }
        String string = getString(PASSWORD_HISTORY_KEY, i);
        if (TextUtils.isEmpty(string) || (requestedPasswordHistoryLength = getRequestedPasswordHistoryLength(i)) == 0) {
            return false;
        }
        byte[] bytes = getSalt(i).getBytes();
        String strLegacyPasswordToHash = LockscreenCredential.legacyPasswordToHash(bArr, bytes);
        String strPasswordToHistoryHash = LockscreenCredential.passwordToHistoryHash(bArr, bytes, bArr2);
        String[] strArrSplit = string.split(",");
        for (int i2 = 0; i2 < Math.min(requestedPasswordHistoryLength, strArrSplit.length); i2++) {
            if (strArrSplit[i2].equals(strLegacyPasswordToHash) || strArrSplit[i2].equals(strPasswordToHistoryHash)) {
                return true;
            }
        }
        return false;
    }

    public int getPinLength(int i) {
        if (i == -9899) {
            return -1;
        }
        try {
            return getLockSettings().getPinLength(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not fetch PIN length " + e);
            return -1;
        }
    }

    public boolean refreshStoredPinLength(int i) {
        try {
            return getLockSettings().refreshStoredPinLength(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not store PIN length on disk " + e);
            return false;
        }
    }

    public int getActivePasswordQuality(int i) {
        return getKeyguardStoredPasswordQuality(i);
    }

    public void resetKeyStore(int i) {
        try {
            getLockSettings().resetKeyStore(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't reset keystore " + e);
        }
    }

    public void setLockScreenDisabled(boolean z, int i) {
        setBoolean("lockscreen.disabled", z, i);
    }

    public boolean isLockScreenDisabled(int i) throws Resources.NotFoundException {
        if (isSecure(i)) {
            return false;
        }
        boolean z = this.mContext.getResources().getBoolean(R.bool.config_disableLockscreenByDefault);
        UserInfo userInfo = getUserManager().getUserInfo(i);
        return getBoolean("lockscreen.disabled", false, i) || z || (UserManager.isDeviceInDemoMode(this.mContext) && userInfo != null && userInfo.isDemo());
    }

    public void setAutoPinConfirm(boolean z, int i) {
        setBoolean(AUTO_PIN_CONFIRM, z, i);
    }

    public boolean isAutoPinConfirmEnabled(int i) {
        return getBoolean(AUTO_PIN_CONFIRM, false, i);
    }

    public static int credentialTypeToPasswordQuality(int i) {
        if (i == -1) {
            return 0;
        }
        if (i == 1) {
            return 65536;
        }
        if (i == 6) {
            return 458752;
        }
        if (i == 3) {
            return 131072;
        }
        if (i == 4) {
            return 262144;
        }
        throw new IllegalStateException("Unknown type: " + i);
    }

    public static int pinOrPasswordQualityToCredentialType(int i) {
        if (isQualitySmartCard(i)) {
            return 6;
        }
        if (isQualityAlphabeticPassword(i)) {
            return 4;
        }
        if (isQualityNumericPin(i)) {
            return 3;
        }
        throw new IllegalArgumentException("Quality is neither Pin nor password: " + i);
    }

    public boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
        return setLockCredential(lockscreenCredential, lockscreenCredential2, i, false);
    }

    public boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) {
        if (!hasSecureLockScreen() && lockscreenCredential.getType() != -1) {
            throw new UnsupportedOperationException("This operation requires the lock screen feature.");
        }
        LsLog.enrollRequest(i, Process.myPid(), this.mContext.getPackageName());
        LsLog.enroll(String.format("Enroll [User %d %s][%s:%d]\n%s", Integer.valueOf(i), credentialTypeToString(lockscreenCredential.getType()), this.mContext.getPackageName(), Integer.valueOf(Process.myPid()), Debug.getCallers(10, "    ")));
        try {
            if (isEnterpriseUser(i)) {
                if (!setLockCredentialForEnterpriseUser(lockscreenCredential, lockscreenCredential2, i, z)) {
                    return false;
                }
            } else if (!getLockSettings().setLockCredential(lockscreenCredential, lockscreenCredential2, i)) {
                return false;
            }
            if (lockscreenCredential.isNone()) {
                clearBiometricAndLockState(i);
            }
            try {
                getLockSettings().sendLockTypeChangedInfo(lockscreenCredential.isNone() ? 1 : 2);
            } catch (Exception e) {
                Log.e(TAG, "sendLockTypeChangedInfo Failed!", e);
            }
            return true;
        } catch (RemoteException e2) {
            throw new RuntimeException("Unable to save lock password", e2);
        }
    }

    private boolean setLockCredentialForEnterpriseUser(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) throws Throwable {
        LockscreenCredential lockscreenCredential3;
        LockscreenCredential lockscreenCredentialEncryptStream;
        LockscreenCredential lockscreenCredentialEncryptStream2 = null;
        try {
            lockscreenCredentialEncryptStream = StreamCipher.encryptStream(lockscreenCredential);
        } catch (Throwable th) {
            th = th;
            lockscreenCredential3 = null;
        }
        try {
            lockscreenCredentialEncryptStream2 = StreamCipher.encryptStream(lockscreenCredential2);
            if (!getLockSettings().setLockCredentialWithIgnoreNotifyIfNeeded(lockscreenCredentialEncryptStream, lockscreenCredentialEncryptStream2, i, z)) {
                if (lockscreenCredentialEncryptStream != null) {
                    lockscreenCredentialEncryptStream.zeroize();
                }
                if (lockscreenCredentialEncryptStream2 != null) {
                    lockscreenCredentialEncryptStream2.zeroize();
                }
                return false;
            }
            if (lockscreenCredentialEncryptStream != null) {
                lockscreenCredentialEncryptStream.zeroize();
            }
            if (lockscreenCredentialEncryptStream2 == null) {
                return true;
            }
            lockscreenCredentialEncryptStream2.zeroize();
            return true;
        } catch (Throwable th2) {
            th = th2;
            LockscreenCredential lockscreenCredential4 = lockscreenCredentialEncryptStream2;
            lockscreenCredentialEncryptStream2 = lockscreenCredentialEncryptStream;
            lockscreenCredential3 = lockscreenCredential4;
            if (lockscreenCredentialEncryptStream2 != null) {
                lockscreenCredentialEncryptStream2.zeroize();
            }
            if (lockscreenCredential3 != null) {
                lockscreenCredential3.zeroize();
            }
            throw th;
        }
    }

    public void notifyPasswordChangedForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) {
        try {
            getLockSettings().notifyPasswordChangedForEnterpriseUser(lockscreenCredential, i);
        } catch (RemoteException unused) {
            Log.e(TAG, "Couldn't notify password changed for enterprise user");
        }
    }

    public boolean isVisiblePatternDisabledByMDM() {
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance();
        if (enterpriseDeviceManager == null || enterpriseDeviceManager.getPasswordPolicy() == null) {
            return false;
        }
        return !enterpriseDeviceManager.getPasswordPolicy().isScreenLockPatternVisibilityEnabled();
    }

    public boolean isVisiblePatternDisabledByMDMAsUser(int i) {
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance();
        if (enterpriseDeviceManager == null || enterpriseDeviceManager.getPasswordPolicy() == null) {
            return false;
        }
        return !enterpriseDeviceManager.getPasswordPolicy().isScreenLockPatternVisibilityEnabledAsUser(i);
    }

    public boolean isLockPasswordEnabledNoCache(int i) {
        long j = (int) getLong(PASSWORD_TYPE_KEY, 0L, i);
        long j2 = (int) getLong(PASSWORD_TYPE_ALTERNATE_KEY, 0L, i);
        return havePasswordNoMDMCache(i) && (((j > 262144L ? 1 : (j == 262144L ? 0 : -1)) == 0 || (j > 131072L ? 1 : (j == 131072L ? 0 : -1)) == 0 || (j > 196608L ? 1 : (j == 196608L ? 0 : -1)) == 0 || (j > 327680L ? 1 : (j == 327680L ? 0 : -1)) == 0 || (j > 393216L ? 1 : (j == 393216L ? 0 : -1)) == 0 || (j > 458752L ? 1 : (j == 458752L ? 0 : -1)) == 0) || (getKeyguardStoredPasswordQuality(i) == 32768 && ((j2 > 262144L ? 1 : (j2 == 262144L ? 0 : -1)) == 0 || (j2 > 131072L ? 1 : (j2 == 131072L ? 0 : -1)) == 0 || (j2 > 196608L ? 1 : (j2 == 196608L ? 0 : -1)) == 0 || (j2 > 327680L ? 1 : (j2 == 327680L ? 0 : -1)) == 0 || (j2 > 393216L ? 1 : (j2 == 393216L ? 0 : -1)) == 0)));
    }

    private boolean havePasswordNoMDMCache(int i) {
        int credentialType;
        ILockSettings iLockSettingsAsInterface = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        if (iLockSettingsAsInterface != null) {
            try {
                credentialType = iLockSettingsAsInterface.getCredentialType(i);
            } catch (Exception unused) {
                Log.e(TAG, "Unable to reach LockSettingsService");
            }
        } else {
            credentialType = -1;
        }
        if (credentialType == -1) {
            Log.d(TAG, "havePasswordNoMDMCache() : no password in User " + i);
        }
        return credentialType != -1;
    }

    public void setOwnerInfo(String str, int i) {
        setString("lock_screen_owner_info", str, i);
    }

    public void setOwnerInfoEnabled(boolean z, int i) {
        setBoolean("lock_screen_owner_info_enabled", z, i);
    }

    public String getOwnerInfo(int i) {
        return getString("lock_screen_owner_info", i);
    }

    public boolean isOwnerInfoEnabled(int i) {
        return getBoolean("lock_screen_owner_info_enabled", false, i);
    }

    public void setDeviceOwnerInfo(String str) {
        if (str != null && str.isEmpty()) {
            str = null;
        }
        setString(LOCK_SCREEN_DEVICE_OWNER_INFO, str, 0);
    }

    public String getDeviceOwnerInfo() {
        return getString(LOCK_SCREEN_DEVICE_OWNER_INFO, 0);
    }

    public boolean isDeviceOwnerInfoEnabled() {
        return getDeviceOwnerInfo() != null;
    }

    public static boolean isDeviceEncryptionEnabled() {
        return StorageManager.isEncrypted();
    }

    public static boolean isFileEncryptionEnabled() {
        return StorageManager.isFileEncrypted();
    }

    @Deprecated
    public int getKeyguardStoredPasswordQuality(int i) {
        return credentialTypeToPasswordQuality(getCredentialTypeForUser(i));
    }

    public void setSeparateProfileChallengeEnabled(int i, boolean z, LockscreenCredential lockscreenCredential) {
        if (isCredentialShareableWithParent(i)) {
            try {
                getLockSettings().setSeparateProfileChallengeEnabled(i, z, lockscreenCredential);
                reportEnabledTrustAgentsChanged(i);
            } catch (RemoteException unused) {
                Log.e(TAG, "Couldn't update work profile challenge enabled");
            }
        }
    }

    public boolean isSeparateProfileChallengeEnabled(int i) {
        return isCredentialShareableWithParent(i) && hasSeparateChallenge(i);
    }

    public boolean isProfileWithUnifiedChallenge(int i) {
        return isCredentialShareableWithParent(i) && !hasSeparateChallenge(i);
    }

    public boolean isManagedProfileWithUnifiedChallenge(int i) {
        return isManagedProfile(i) && !hasSeparateChallenge(i);
    }

    private boolean hasSeparateChallenge(int i) {
        try {
            return getLockSettings().getSeparateProfileChallengeEnabled(i);
        } catch (RemoteException unused) {
            Log.e(TAG, "Couldn't get separate profile challenge enabled");
            return false;
        }
    }

    private boolean isManagedProfile(int i) {
        UserInfo userInfo = getUserManager().getUserInfo(i);
        return userInfo != null && userInfo.isManagedProfile();
    }

    private boolean isCredentialShareableWithParent(int i) {
        try {
            return getUserManager().getUserProperties(UserHandle.of(i)).isCredentialShareableWithParent();
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public static List<LockPatternView.Cell> byteArrayToPattern(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (byte b : bArr) {
            byte b2 = (byte) (b - 49);
            arrayListNewArrayList.add(LockPatternView.Cell.of(b2 / 3, b2 % 3));
        }
        return arrayListNewArrayList;
    }

    public static byte[] patternToByteArray(List<LockPatternView.Cell> list) {
        if (list == null) {
            return new byte[0];
        }
        int size = list.size();
        byte[] bArrNewNonMovableByteArray = newNonMovableByteArray(size);
        for (int i = 0; i < size; i++) {
            LockPatternView.Cell cell = list.get(i);
            bArrNewNonMovableByteArray[i] = (byte) ((cell.getRow() * 3) + cell.getColumn() + 49);
        }
        return bArrNewNonMovableByteArray;
    }

    private String getSalt(int i) {
        long jNextLong = getLong(LOCK_PASSWORD_SALT_KEY, 0L, i);
        if (jNextLong == 0) {
            try {
                jNextLong = SecureRandom.getInstance("SHA1PRNG").nextLong();
                setLong(LOCK_PASSWORD_SALT_KEY, jNextLong, i);
                Log.v(TAG, "Initialized lock password salt for user: " + i);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("Couldn't get SecureRandom number", e);
            }
        }
        return Long.toHexString(jNextLong);
    }

    public static final void invalidateCredentialTypeCache() {
        PropertyInvalidatedCache.invalidateCache("system_server", CREDENTIAL_TYPE_API);
    }

    public int getCredentialTypeForUser(int i) {
        return this.mCredentialTypeCache.query(Integer.valueOf(i)).intValue();
    }

    public boolean isSecure(int i) {
        return getCredentialTypeForUser(i) != -1 || isRemoteLock(i);
    }

    public boolean isLockPasswordEnabled(int i) {
        int credentialTypeForUser = getCredentialTypeForUser(i);
        return credentialTypeForUser == 4 || credentialTypeForUser == 3 || credentialTypeForUser == 6;
    }

    public boolean isLockPatternEnabled(int i) {
        return getCredentialTypeForUser(i) == 1;
    }

    private boolean hasActivePointerDeviceAttached() {
        return !getEnabledNonTouchInputDevices(2).isEmpty();
    }

    public boolean isVisiblePatternEnabled(int i) {
        boolean zHasActivePointerDeviceAttached = com.android.internal.widget.flags.Flags.hideLastCharWithPhysicalInput() ? true ^ hasActivePointerDeviceAttached() : true;
        if (isVisiblePatternDisabledByMDMAsUser(i)) {
            Log.d(TAG, "pattern visibility disabled by MDM for user : " + i);
            return false;
        }
        return getBoolean("lock_pattern_visible_pattern", zHasActivePointerDeviceAttached, i);
    }

    public void setVisiblePatternEnabled(boolean z, int i) {
        if (isVisiblePatternDisabledByMDMAsUser(i) && z) {
            Log.e(TAG, "setVisiblePatternEnabled() : Could not enable visible pattern by MDM admin. user : " + i);
            return;
        }
        setBoolean("lock_pattern_visible_pattern", z, i);
    }

    public boolean isVisiblePatternEverChosen(int i) {
        return getString("lock_pattern_visible_pattern", i) != null;
    }

    private List<InputDevice> getEnabledNonTouchInputDevices(int i) {
        InputManagerGlobal inputManagerGlobal = InputManagerGlobal.getInstance();
        int[] inputDeviceIds = inputManagerGlobal.getInputDeviceIds();
        ArrayList arrayList = new ArrayList();
        for (int i2 : inputDeviceIds) {
            InputDevice inputDevice = inputManagerGlobal.getInputDevice(i2);
            if (inputDevice.isEnabled() && !inputDevice.supportsSource(4098) && !inputDevice.isVirtual() && inputDevice.supportsSource(i)) {
                arrayList.add(inputDevice);
            }
        }
        return arrayList;
    }

    private boolean hasPhysicalKeyboardActive() {
        Iterator<InputDevice> it = getEnabledNonTouchInputDevices(257).iterator();
        while (it.hasNext()) {
            if (it.next().isFullKeyboard()) {
                return true;
            }
        }
        return false;
    }

    public boolean isPinEnhancedPrivacyEnabled(int i) {
        return getBoolean(LOCK_PIN_ENHANCED_PRIVACY, com.android.internal.widget.flags.Flags.hideLastCharWithPhysicalInput() ? hasPhysicalKeyboardActive() : false, i);
    }

    public void setPinEnhancedPrivacyEnabled(boolean z, int i) {
        setBoolean(LOCK_PIN_ENHANCED_PRIVACY, z, i);
    }

    public boolean isPinEnhancedPrivacyEverChosen(int i) {
        return getString(LOCK_PIN_ENHANCED_PRIVACY, i) != null;
    }

    public long setLockoutAttemptDeadline(int i, int i2) {
        long j = i2;
        long jElapsedRealtime = SystemClock.elapsedRealtime() + j;
        if (i == -9999) {
            return jElapsedRealtime;
        }
        if (i == -9899) {
            return 0L;
        }
        setLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, j, i);
        setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, jElapsedRealtime, i);
        return jElapsedRealtime;
    }

    public long getLockoutAttemptDeadline(int i) {
        long j = getLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, 0L, i);
        long j2 = getLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, i);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (j < jElapsedRealtime && j != 0) {
            setLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, i);
            setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, 0L, i);
            return 0L;
        }
        long j3 = jElapsedRealtime + j2;
        if (j <= j3) {
            return j;
        }
        setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, j3, i);
        return j3;
    }

    private boolean getBoolean(String str, boolean z, int i) {
        try {
            return getLockSettings().getBoolean(str, z, i);
        } catch (RemoteException unused) {
            return z;
        }
    }

    private void setBoolean(String str, boolean z, int i) {
        try {
            getLockSettings().setBoolean(str, z, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't write boolean " + str + e);
        }
    }

    private long getLong(String str, long j, int i) {
        try {
            return getLockSettings().getLong(str, j, i);
        } catch (RemoteException unused) {
            return j;
        }
    }

    private void setLong(String str, long j, int i) {
        try {
            getLockSettings().setLong(str, j, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't write long " + str + e);
        }
    }

    private String getString(String str, int i) {
        try {
            return getLockSettings().getString(str, null, i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    private void setString(String str, String str2, int i) {
        try {
            getLockSettings().setString(str, str2, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't write string " + str + e);
        }
    }

    public void setPowerButtonInstantlyLocks(boolean z, int i) {
        setBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, z, i);
    }

    public boolean getPowerButtonInstantlyLocks(int i) {
        return getBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, true, i);
    }

    public boolean isPowerButtonInstantlyLocksEverChosen(int i) {
        return getString(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, i) != null;
    }

    public void setEnabledTrustAgents(Collection<ComponentName> collection, int i) {
        setString(ENABLED_TRUST_AGENTS, serializeTrustAgents(collection), i);
        getTrustManager().reportEnabledTrustAgentsChanged(i);
    }

    public List<ComponentName> getEnabledTrustAgents(int i) {
        return deserializeTrustAgents(getString(ENABLED_TRUST_AGENTS, i));
    }

    public void setKnownTrustAgents(Collection<ComponentName> collection, int i) {
        setString(KNOWN_TRUST_AGENTS, serializeTrustAgents(collection), i);
    }

    public List<ComponentName> getKnownTrustAgents(int i) {
        return deserializeTrustAgents(getString(KNOWN_TRUST_AGENTS, i));
    }

    private String serializeTrustAgents(Collection<ComponentName> collection) {
        StringBuilder sb = new StringBuilder();
        for (ComponentName componentName : collection) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(componentName.flattenToShortString());
        }
        return sb.toString();
    }

    private List<ComponentName> deserializeTrustAgents(String str) {
        if (TextUtils.isEmpty(str)) {
            return new ArrayList();
        }
        String[] strArrSplit = str.split(",");
        ArrayList arrayList = new ArrayList(strArrSplit.length);
        for (String str2 : strArrSplit) {
            if (!TextUtils.isEmpty(str2)) {
                arrayList.add(ComponentName.unflattenFromString(str2));
            }
        }
        return arrayList;
    }

    public void requireCredentialEntry(int i) {
        requireStrongAuth(4, i);
    }

    public void requireStrongAuth(int i, int i2) {
        try {
            getLockSettings().requireStrongAuth(i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Error while requesting strong auth: " + e);
        }
    }

    private void reportEnabledTrustAgentsChanged(int i) {
        if (VirtualLockUtils.isVirtualUserId(i)) {
            return;
        }
        getTrustManager().reportEnabledTrustAgentsChanged(i);
    }

    private void throwIfCalledOnMainThread() {
        if (Looper.getMainLooper().isCurrentThread()) {
            throw new IllegalStateException("should not be called from the main thread.");
        }
    }

    public void registerStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        try {
            getLockSettings().registerStrongAuthTracker(strongAuthTracker.getStub());
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void unregisterStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        try {
            getLockSettings().unregisterStrongAuthTracker(strongAuthTracker.getStub());
        } catch (RemoteException e) {
            Log.e(TAG, "Could not unregister StrongAuthTracker", e);
        }
    }

    public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        try {
            return getLockSettings().registerWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not register WeakEscrowTokenRemovedListener.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        try {
            return getLockSettings().unregisterWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not register WeakEscrowTokenRemovedListener.");
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportSuccessfulBiometricUnlock(boolean z, int i) {
        try {
            getLockSettings().reportSuccessfulBiometricUnlock(z, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not report successful biometric unlock", e);
        }
    }

    public void scheduleNonStrongBiometricIdleTimeout(int i) {
        try {
            getLockSettings().scheduleNonStrongBiometricIdleTimeout(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not schedule non-strong biometric idle timeout", e);
        }
    }

    public int getStrongAuthForUser(int i) {
        try {
            return getLockSettings().getStrongAuthForUser(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not get StrongAuth", e);
            return StrongAuthTracker.getDefaultFlags(this.mContext);
        }
    }

    public boolean isCredentialsDisabledForUser(int i) {
        return getDevicePolicyManager().getPasswordQuality(null, i) == 524288;
    }

    public boolean isTrustAllowedForUser(int i) {
        return getStrongAuthForUser(i) == 0;
    }

    public boolean isBiometricAllowedForUser(int i) {
        return (getStrongAuthForUser(i) & (-781)) == 0 && !isUCMLockEnabled(i);
    }

    public boolean isUserInLockdown(int i) {
        return (getStrongAuthForUser(i) & 32) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class WrappedCallback extends ICheckCredentialProgressCallback.Stub {
        private CheckCredentialProgressCallback mCallback;
        private Handler mHandler;

        WrappedCallback(Handler handler, CheckCredentialProgressCallback checkCredentialProgressCallback) {
            this.mHandler = handler;
            this.mCallback = checkCredentialProgressCallback;
        }

        @Override // com.android.internal.widget.ICheckCredentialProgressCallback
        public void onCredentialVerified() throws RemoteException {
            if (this.mHandler == null) {
                Log.e(LockPatternUtils.TAG, "Handler is null during callback");
            }
            this.mHandler.post(new Runnable() { // from class: com.android.internal.widget.LockPatternUtils$WrappedCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCredentialVerified$0();
                }
            });
            this.mHandler = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCredentialVerified$0() {
            this.mCallback.onEarlyMatched();
            this.mCallback = null;
        }
    }

    private ICheckCredentialProgressCallback wrapCallback(CheckCredentialProgressCallback checkCredentialProgressCallback) {
        if (checkCredentialProgressCallback == null) {
            return null;
        }
        if (this.mHandler == null) {
            throw new IllegalStateException("Must construct LockPatternUtils on a looper thread to use progress callbacks.");
        }
        return new WrappedCallback(this.mHandler, checkCredentialProgressCallback);
    }

    private LockSettingsInternal getLockSettingsInternal() {
        LockSettingsInternal lockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
        if (lockSettingsInternal != null) {
            return lockSettingsInternal;
        }
        throw new SecurityException("Only available to system server itself");
    }

    public long addEscrowToken(byte[] bArr, int i, EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        return getLockSettingsInternal().addEscrowToken(bArr, i, escrowTokenStateChangeCallback);
    }

    public long addWeakEscrowToken(byte[] bArr, int i, IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) {
        try {
            return getLockSettings().addWeakEscrowToken(bArr, i, iWeakEscrowTokenActivatedListener);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not add weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeEscrowToken(long j, int i) {
        return getLockSettingsInternal().removeEscrowToken(j, i);
    }

    public boolean removeWeakEscrowToken(long j, int i) {
        try {
            return getLockSettings().removeWeakEscrowToken(j, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not remove the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isEscrowTokenActive(long j, int i) {
        return getLockSettingsInternal().isEscrowTokenActive(j, i);
    }

    public boolean isWeakEscrowTokenActive(long j, int i) {
        try {
            return getLockSettings().isWeakEscrowTokenActive(j, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not check the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) {
        try {
            return getLockSettings().isWeakEscrowTokenValid(j, bArr, i);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not validate the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setLockCredentialWithToken(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
        if (!hasSecureLockScreen() && lockscreenCredential.getType() != -1) {
            throw new UnsupportedOperationException("This operation requires the lock screen feature.");
        }
        LsLog.enroll(String.format("Enroll [User %d %s][%s]\n%s", Integer.valueOf(i), credentialTypeToString(lockscreenCredential.getType()), this.mContext.getPackageName(), Debug.getCallers(10, "    ")));
        return getLockSettingsInternal().setLockCredentialWithToken(lockscreenCredential, j, bArr, i);
    }

    public boolean unlockUserWithToken(long j, byte[] bArr, int i) {
        return getLockSettingsInternal().unlockUserWithToken(j, bArr, i);
    }

    public static class StrongAuthTracker {
        private static final int ALLOWING_BIOMETRIC = 780;
        public static final int KNOX_STRONG_AUTH_REQUIRED_AFTER_BIOMETRIC_LOCKOUT = 4096;
        public static final int KNOX_STRONG_AUTH_REQUIRED_AFTER_FACE_CHANGE = 32768;
        public static final int KNOX_STRONG_AUTH_REQUIRED_AFTER_FINGERPRINT_CHANGE = 16384;
        public static final int KNOX_STRONG_AUTH_REQUIRED_AFTER_LOCK = 8192;
        public static final int KNOX_STRONG_AUTH_REQUIRED_NON_STRONG_IDLE_TIMEOUT = 65536;
        public static final int SOME_AUTH_REQUIRED_AFTER_ADAPTIVE_AUTH_REQUEST = 512;
        public static final int SOME_AUTH_REQUIRED_AFTER_TRUSTAGENT_EXPIRED = 256;
        public static final int SOME_AUTH_REQUIRED_AFTER_USER_REQUEST = 4;
        public static final int STRONG_AUTH_NOT_REQUIRED = 0;
        public static final int STRONG_AUTH_REQUIRED_AFTER_BOOT = 1;
        public static final int STRONG_AUTH_REQUIRED_AFTER_DPM_LOCK_NOW = 2;
        public static final int STRONG_AUTH_REQUIRED_AFTER_LOCKOUT = 8;
        public static final int STRONG_AUTH_REQUIRED_AFTER_NON_STRONG_BIOMETRICS_TIMEOUT = 128;
        public static final int STRONG_AUTH_REQUIRED_AFTER_TIMEOUT = 16;
        public static final int STRONG_AUTH_REQUIRED_AFTER_USER_LOCKDOWN = 32;
        public static final int STRONG_AUTH_REQUIRED_FOR_UNATTENDED_UPDATE = 64;
        private final boolean mDefaultIsNonStrongBiometricAllowed;
        private final int mDefaultStrongAuthFlags;
        private final H mHandler;
        private final SparseBooleanArray mIsNonStrongBiometricAllowedForUser;
        private final SparseIntArray mStrongAuthRequiredForUser;
        private final IStrongAuthTracker.Stub mStub;

        @Retention(RetentionPolicy.SOURCE)
        public @interface StrongAuthFlags {
        }

        public void onIsNonStrongBiometricAllowedChanged(int i) {
        }

        public void onStrongAuthRequiredChanged(int i) {
        }

        public StrongAuthTracker(Context context) {
            this(context, Looper.myLooper());
        }

        public StrongAuthTracker(Context context, Looper looper) {
            this.mStrongAuthRequiredForUser = new SparseIntArray();
            this.mIsNonStrongBiometricAllowedForUser = new SparseBooleanArray();
            this.mDefaultIsNonStrongBiometricAllowed = true;
            this.mStub = new IStrongAuthTracker.Stub() { // from class: com.android.internal.widget.LockPatternUtils.StrongAuthTracker.1
                @Override // android.app.trust.IStrongAuthTracker
                public void onStrongAuthRequiredChanged(int i, int i2) {
                    StrongAuthTracker.this.mHandler.obtainMessage(1, i, i2).sendToTarget();
                }

                @Override // android.app.trust.IStrongAuthTracker
                public void onIsNonStrongBiometricAllowedChanged(boolean z, int i) {
                    StrongAuthTracker.this.mHandler.obtainMessage(2, z ? 1 : 0, i).sendToTarget();
                }
            };
            this.mHandler = new H(looper);
            this.mDefaultStrongAuthFlags = getDefaultFlags(context);
        }

        public static int getDefaultFlags(Context context) {
            return context.getResources().getBoolean(R.bool.config_strongAuthRequiredOnBoot) ? 1 : 0;
        }

        public int getStrongAuthForUser(int i) {
            return this.mStrongAuthRequiredForUser.get(i, this.mDefaultStrongAuthFlags);
        }

        public boolean isTrustAllowedForUser(int i) {
            return getStrongAuthForUser(i) == 0;
        }

        public boolean isBiometricAllowedForUser(boolean z, int i) {
            boolean z2 = (getStrongAuthForUser(i) & (-781)) == 0;
            return !z ? isNonStrongBiometricAllowedAfterIdleTimeout(i) & z2 : z2;
        }

        public boolean isNonStrongBiometricAllowedAfterIdleTimeout(int i) {
            return this.mIsNonStrongBiometricAllowedForUser.get(i, true);
        }

        protected void handleStrongAuthRequiredChanged(int i, int i2) {
            if (i != getStrongAuthForUser(i2)) {
                if (i == this.mDefaultStrongAuthFlags) {
                    this.mStrongAuthRequiredForUser.delete(i2);
                } else {
                    this.mStrongAuthRequiredForUser.put(i2, i);
                }
                onStrongAuthRequiredChanged(i2);
            }
        }

        protected void handleIsNonStrongBiometricAllowedChanged(boolean z, int i) {
            if (z != isNonStrongBiometricAllowedAfterIdleTimeout(i)) {
                if (z) {
                    this.mIsNonStrongBiometricAllowedForUser.delete(i);
                } else {
                    this.mIsNonStrongBiometricAllowedForUser.put(i, z);
                }
                onIsNonStrongBiometricAllowedChanged(i);
            }
        }

        public IStrongAuthTracker.Stub getStub() {
            return this.mStub;
        }

        private class H extends Handler {
            static final int MSG_ON_IS_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED = 2;
            static final int MSG_ON_STRONG_AUTH_REQUIRED_CHANGED = 1;

            public H(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    StrongAuthTracker.this.handleStrongAuthRequiredChanged(message.arg1, message.arg2);
                } else {
                    if (i != 2) {
                        return;
                    }
                    StrongAuthTracker.this.handleIsNonStrongBiometricAllowedChanged(message.arg1 == 1, message.arg2);
                }
            }
        }
    }

    public void saveAppLockPassword(String str, SecAppLockType secAppLockType, int i) {
        try {
            if (secAppLockType == SecAppLockType.PIN) {
                getLockSettings().setAppLockPin(str, i);
                return;
            }
            if (secAppLockType == SecAppLockType.Password) {
                getLockSettings().setAppLockPassword(str, i);
            } else if (secAppLockType == SecAppLockType.Pattern) {
                getLockSettings().setAppLockPattern(str, i);
            } else if (secAppLockType == SecAppLockType.BackupPin) {
                getLockSettings().setAppLockBackupPin(str, i);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to save lock " + secAppLockType + " :: " + e);
        }
    }

    public boolean checkAppLockPassword(String str, SecAppLockType secAppLockType, int i) {
        try {
            if (secAppLockType == SecAppLockType.PIN) {
                return getLockSettings().checkAppLockPin(str, i);
            }
            if (secAppLockType == SecAppLockType.Password) {
                return getLockSettings().checkAppLockPassword(str, i);
            }
            if (secAppLockType == SecAppLockType.Pattern) {
                return getLockSettings().checkAppLockPatternWithHash(str, i, null);
            }
            if (secAppLockType == SecAppLockType.BackupPin) {
                return getLockSettings().checkAppLockBackupPin(str, i);
            }
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to Check applock password :: " + e);
            return false;
        }
    }

    public boolean checkAppLockPassword(String str, SecAppLockType secAppLockType, int i, byte[] bArr) {
        try {
            if (secAppLockType == SecAppLockType.PIN) {
                return getLockSettings().checkAppLockPin(str, i);
            }
            if (secAppLockType == SecAppLockType.Password) {
                return getLockSettings().checkAppLockPassword(str, i);
            }
            if (secAppLockType == SecAppLockType.Pattern) {
                return getLockSettings().checkAppLockPatternWithHash(str, i, bArr);
            }
            if (secAppLockType == SecAppLockType.BackupPin) {
                return getLockSettings().checkAppLockBackupPin(str, i);
            }
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to Check applock password :: " + e);
            return false;
        }
    }

    public boolean savedAppLockPasswordExists(SecAppLockType secAppLockType, int i) {
        if (secAppLockType == SecAppLockType.PIN) {
            return getLockSettings().haveAppLockPin(i);
        }
        if (secAppLockType == SecAppLockType.Password) {
            return getLockSettings().haveAppLockPassword(i);
        }
        if (secAppLockType == SecAppLockType.Pattern) {
            return getLockSettings().haveAppLockPattern(i);
        }
        if (secAppLockType == SecAppLockType.BackupPin) {
            return getLockSettings().haveAppLockBackupPin(i);
        }
        return false;
    }

    public void setAppLockFingerPrintLockscreen(boolean z, int i) {
        setBoolean(APP_LOCK_FINGERPRINT_LOCKSCREEN_KEY, z, i);
    }

    public boolean isAppLockFingerPrintLockscreen(int i) {
        return getBoolean(APP_LOCK_FINGERPRINT_LOCKSCREEN_KEY, false, i);
    }

    public boolean hasPendingEscrowToken(int i) {
        try {
            return getLockSettings().hasPendingEscrowToken(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean hasSecureLockScreen() {
        if (this.mHasSecureLockScreen == null) {
            try {
                this.mHasSecureLockScreen = Boolean.valueOf(getLockSettings().hasSecureLockScreen());
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return this.mHasSecureLockScreen.booleanValue();
    }

    public static boolean userOwnsFrpCredential(Context context, UserInfo userInfo) {
        return userInfo != null && userInfo.isMain() && userInfo.isAdmin() && frpCredentialEnabled(context);
    }

    public static boolean frpCredentialEnabled(Context context) {
        return context.getResources().getBoolean(R.bool.config_enableCredentialFactoryResetProtection);
    }

    public static boolean isRepairModeSupported(Context context) {
        return context.getResources().getBoolean(R.bool.config_repairModeSupported);
    }

    public static boolean isRepairModeActive(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.REPAIR_MODE_ACTIVE, 0) > 0;
    }

    public static boolean canUserEnterRepairMode(Context context, UserInfo userInfo) {
        return userInfo != null && userInfo.isAdmin() && isRepairModeSupported(context);
    }

    public static boolean isGsiRunning() {
        return SystemProperties.getInt(GSI_RUNNING_PROP, 0) > 0;
    }

    public static boolean isSpecialUserId(int i) {
        return isSpecialUserId(null, i, false);
    }

    private static boolean isSpecialUserId(Context context, int i, boolean z) {
        if (i == -9999) {
            if (z) {
                return frpCredentialEnabled(context);
            }
            return true;
        }
        if (i != -9998) {
            return false;
        }
        if (z) {
            return isRepairModeSupported(context);
        }
        return true;
    }

    public boolean tryUnlockWithCachedUnifiedChallenge(int i) {
        try {
            return getLockSettings().tryUnlockWithCachedUnifiedChallenge(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void removeCachedUnifiedChallenge(int i) {
        try {
            getLockSettings().removeCachedUnifiedChallenge(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public boolean isDevicePasswordSimple(int i) {
        return Settings.Secure.getIntForUser(this.mContentResolver, "is_smpw_key", 0, i) == 1;
    }

    public void unlockUserKeyIfUnsecured(int i) {
        LsLog.verifyRequest(i, Process.myPid(), this.mContext.getPackageName());
        try {
            getLockSettings().unlockUserKeyIfUnsecured(i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void createNewUser(int i, int i2) {
        getLockSettingsInternal().createNewUser(i, i2);
    }

    public void removeUser(int i) {
        getLockSettingsInternal().removeUser(i);
    }

    public RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        try {
            return getLockSettings().startRemoteLockscreenValidation();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) {
        try {
            return getLockSettings().validateRemoteLockscreen(bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLockoutAttemptTimeout(int i) {
        return getLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, i);
    }

    public void clearLockoutAttemptDeadline(int i) {
        setLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, i);
        setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, 0L, i);
    }

    public void backupLockSettingsDB() {
        setLong(LsConstants.LOCKSETTINGS_DB_BACKUP, System.currentTimeMillis(), 0);
    }

    public void restoreLockSettingsDB() {
        setLong(LsConstants.LOCKSETTINGS_DB_RESTORE, System.currentTimeMillis(), 0);
    }

    public void setPasswordHint(String str, int i) {
        if (str != null && str.isEmpty()) {
            str = null;
        }
        setString(LsConstants.PASSWORD_HINT_KEY, str, i);
    }

    public String getPasswordHint(int i) {
        return getString(LsConstants.PASSWORD_HINT_KEY, i);
    }

    public long addFailedFMMUnlockAttempt(int i) {
        long failedFMMUnlockAttempt = getFailedFMMUnlockAttempt(i) + 1;
        setLong(LsConstants.FMM_FAIELD_ATTEMPT_KEY, failedFMMUnlockAttempt, i);
        return failedFMMUnlockAttempt;
    }

    public long getFailedFMMUnlockAttempt(int i) {
        return getLong(LsConstants.FMM_FAIELD_ATTEMPT_KEY, 0L, i);
    }

    public void clearFailedFMMUnlockAttempt(int i) {
        setLong(LsConstants.FMM_FAIELD_ATTEMPT_KEY, 0L, i);
    }

    public boolean isCarrierLockEnabled(int i) {
        try {
            return getLockSettings().getCarrierLock(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean updateCarrierLock(int i) {
        try {
            return getLockSettings().updateCarrierLock(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable updateCarrierLock " + e);
            return false;
        }
    }

    public long setCarrierLockoutAttemptDeadline(int i) {
        long jCurrentTimeMillis = System.currentTimeMillis() + LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT;
        setLong(LsConstants.SKT_LOCKOUT_ATTEMPT_DEADLINE, jCurrentTimeMillis, i);
        return jCurrentTimeMillis;
    }

    public long getCarrierLockoutAttemptDeadline(int i) {
        long j = getLong(LsConstants.SKT_LOCKOUT_ATTEMPT_DEADLINE, 0L, i);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (j <= jCurrentTimeMillis) {
            return 0L;
        }
        long j2 = j - jCurrentTimeMillis;
        if (j2 <= LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT) {
            return j;
        }
        Log.e(TAG, "getCarrierLockoutAttemptDeadline : Need to adjust deadline " + j2 + " to 600000");
        return setCarrierLockoutAttemptDeadline(i);
    }

    public void saveRemoteLockPassword(int i, byte[] bArr, int i2) {
        if (i == 0) {
            try {
                getLockSettings().setLockFMMPassword(bArr, 0);
                return;
            } catch (RemoteException e) {
                Log.e(TAG, "Unable to save lock FMM Password " + e);
                return;
            }
        }
        if (i != 1) {
            return;
        }
        try {
            getLockSettings().setLockCarrierPassword(bArr, i2);
        } catch (RemoteException e2) {
            Log.e(TAG, "Unable to save lock Carrier Password " + e2);
        }
    }

    public boolean checkRemoteLockPassword(int i, byte[] bArr, int i2) {
        try {
            if (i == 0) {
                return getLockSettings().checkFMMPassword(MessageDigest.getInstance("SHA-1").digest(bArr), i2);
            }
            if (i == 1) {
                return getLockSettings().checkCarrierPassword(bArr, i2);
            }
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to save lock (" + i + ") Password " + e);
            return false;
        } catch (NoSuchAlgorithmException unused) {
            Log.w(TAG, "Failed to encode string because of missing algorithm: SHA-1");
            return false;
        }
    }

    public boolean isCarrierPasswordSaved(int i) {
        try {
            return getLockSettings().haveCarrierPassword(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isFMMLockEnabled(int i) {
        try {
            return getLockSettings().haveFMMPassword(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean isKnoxguardLockEnabled(int i) {
        return getBoolean("3locked", false, i);
    }

    private boolean isRemoteLock(int i) {
        try {
            return getLockSettings().isRemoteLock(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public long getExpireTimeForPrev() {
        try {
            return getLockSettings().getExpireTimeForPrev();
        } catch (RemoteException unused) {
            Log.e(TAG, "!@getExpireTimeForPrev failed");
            return 0L;
        }
    }

    public boolean expirePreviousData() {
        try {
            getLockSettings().expirePreviousData();
            return true;
        } catch (RemoteException unused) {
            Log.e(TAG, "!@expirePreviousData failed");
            return false;
        }
    }

    public boolean isSupportWeaver() {
        try {
            return getLockSettings().isSupportWeaver();
        } catch (RemoteException unused) {
            Log.e(TAG, "!@isSupportWeaver failed");
            return false;
        }
    }

    public void setSecurityDebugLevel(int i) {
        try {
            getLockSettings().setSecurityDebugLevel(i);
        } catch (RemoteException unused) {
            Log.e(TAG, "!@setSecurityDebugLevel set failed");
        }
    }

    public int getBiometricType(int i) {
        boolean z = Settings.System.getIntForUser(this.mContentResolver, Settings.System.SEM_ULTRA_POWERSAVING_MODE, 0, i) != 0;
        boolean z2 = Settings.System.getIntForUser(this.mContentResolver, Settings.System.SEM_EMERGENCY_MODE, 0, i) != 0;
        if (z || z2) {
            return 0;
        }
        return (int) getLong(LsConstants.BIOMETRIC_LOCKSCREEN_KEY, 0L, i);
    }

    public int getBiometricState(int i, int i2) {
        return (getBiometricType(i2) & i) != 0 ? 1 : 0;
    }

    public void setBiometricState(int i, int i2, int i3) {
        int biometricType = getBiometricType(i3);
        int i4 = i2 == 1 ? biometricType | i : (~i) & biometricType;
        Log.d(TAG, "setBiometricState ( oldValue = " + Integer.toHexString(biometricType) + " , newValue = " + Integer.toHexString(i4) + " )");
        setLong(LsConstants.BIOMETRIC_LOCKSCREEN_KEY, (long) i4, i3);
        reportAuditLog(i, i2 == 1, i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void reportAuditLog(int i, boolean z, int i2) {
        int i3;
        if (z) {
            i3 = i != 1 ? i != 256 ? -1 : 80 : 79;
        } else if (i == 1) {
            i3 = 81;
        } else if (i == 256) {
            i3 = 82;
        }
        AuditLog.logEventAsUser(i2, i3, new Object[0]);
    }

    public long setBiometricAttemptDeadline(int i, int i2) {
        long j = i2;
        long jElapsedRealtime = SystemClock.elapsedRealtime() + j;
        setLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, j, i);
        setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, jElapsedRealtime, i);
        return jElapsedRealtime;
    }

    public long getBiometricAttemptDeadline(int i) {
        long j = getLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, 0L, i);
        long j2 = getLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, i);
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (j != 0 && j < jElapsedRealtime) {
            setLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, i);
            setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, 0L, i);
            return 0L;
        }
        if (j2 != 0) {
            long j3 = jElapsedRealtime + j2;
            if (j > j3) {
                setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, j3, i);
                return j3;
            }
        }
        return j;
    }

    public long getBiometricAttemptTimeout(int i) {
        return getLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, i);
    }

    public void clearBiometricAttemptDeadline(int i) {
        setLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, i);
        setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, 0L, i);
    }

    private void clearBiometricAndLockState(int i) {
        try {
            this.mContext.enforceCallingOrSelfPermission(Manifest.permission.ACCESS_KEYGUARD_SECURE_STORAGE, "LockSettingsWrite");
            setBiometricState(257, 0, i);
            clearBiometricAttemptDeadline(i);
            clearLockoutAttemptDeadline(i);
            setPasswordHint(null, i);
            clearFailedFMMUnlockAttempt(i);
            try {
                getLockSettings().setLockFMMPassword(null, 0);
            } catch (RemoteException e) {
                Log.e(TAG, "setLockFMMPassword error = ", e);
            }
        } catch (SecurityException e2) {
            Log.e(TAG, "Failed to clearBiometricAndLockState =", e2);
        }
    }

    public void setFolderInstantlyLocks(boolean z, int i) {
        setBoolean(LsConstants.LOCKSCREEN_FOLDER_INSTANTLY_LOCKS, z, i);
    }

    public boolean getFolderInstantlyLocks(int i) {
        return getBoolean(LsConstants.LOCKSCREEN_FOLDER_INSTANTLY_LOCKS, true, i);
    }

    public void setBiometricStrongAuthTimeout(String str, long j, int i) {
        setLong(str, j, i);
    }

    public long getBiometricStrongAuthTimeout(String str, int i) {
        return getLong(str, 0L, i);
    }

    public boolean isUCMLockEnabled(int i) {
        return getCredentialTypeForUser(i) == 6 && getBiometricState(1, i) == 0 && getBiometricState(256, i) == 0;
    }

    private Optional<IDarManagerService> getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        }
        return Optional.ofNullable(this.mDarManagerService);
    }

    private boolean isEnterpriseUser(int i) {
        if (!SemPersonaManager.isKnoxId(i)) {
            return SemPersonaManager.isDoEnabled(i);
        }
        if (SemPersonaManager.isSecureFolderId(i)) {
            return isSdpSupportedSecureFolder(i);
        }
        return true;
    }

    public void setDeviceOwner(int i) {
        if (i != 0) {
            return;
        }
        setLong(KNOX_DEVICE_OWNER_KEY, 1L, i);
    }

    public boolean isDeviceOwner(int i) {
        return i == 0 && getLong(KNOX_DEVICE_OWNER_KEY, 0L, i) != 0;
    }

    private static /* synthetic */ Boolean lambda$isSdpSupportedSecureFolder$0(int i, IDarManagerService iDarManagerService) {
        try {
            return Boolean.valueOf(iDarManagerService.isSdpSupportedSecureFolder(i));
        } catch (Exception e) {
            Log.e(TAG, "failed to check sdp support for secure folder", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSdpMdfppModeEnabledForSystem() {
        return getLong(SDP_MDFPPMODE_ENABLED_FOR_SYSTEM_KEY, 0L, 0) >= 2;
    }

    public boolean isNeedToEnableSdpMdfppModeForSystem() {
        return getLong(SDP_MDFPPMODE_ENABLED_FOR_SYSTEM_KEY, 0L, 0) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class WrappedCallbackForDualDar extends IDualDarAuthProgressCallback.Stub {
        private DualDarAuthProgressCallback mCallback;
        private Handler mHandler;

        WrappedCallbackForDualDar(Handler handler, DualDarAuthProgressCallback dualDarAuthProgressCallback) {
            this.mHandler = handler;
            this.mCallback = dualDarAuthProgressCallback;
        }

        @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
        public void onInnerLayerUnlocked() throws RemoteException {
            if (this.mHandler == null) {
                Log.e(LockPatternUtils.TAG, "Handler is null during callback");
            }
            this.mHandler.post(new Runnable() { // from class: com.android.internal.widget.LockPatternUtils$WrappedCallbackForDualDar$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onInnerLayerUnlocked$0();
                }
            });
            this.mHandler = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInnerLayerUnlocked$0() {
            this.mCallback.onInnerLayerUnlocked();
            this.mCallback = null;
        }

        @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
        public void onInnerLayerUnlockFailed() throws RemoteException {
            this.mCallback.onInnerLayerUnlockFailed();
            this.mCallback = null;
        }
    }

    private IDualDarAuthProgressCallback wrapCallbackForDualDar(DualDarAuthProgressCallback dualDarAuthProgressCallback) {
        if (dualDarAuthProgressCallback == null) {
            return null;
        }
        if (this.mHandler == null) {
            throw new IllegalStateException("Must construct LockPatternUtils on a looper thread to use progress callbacks.");
        }
        return new WrappedCallbackForDualDar(this.mHandler, dualDarAuthProgressCallback);
    }

    public synchronized LockPatternUtilForDualDarDo getLockPatternUtilForDualDarDo() {
        if (this.mLockPatternUtilForDualDarDo == null) {
            this.mLockPatternUtilForDualDarDo = new LockPatternUtilForDualDarDo(this.mContext);
        }
        return this.mLockPatternUtilForDualDarDo;
    }

    public final class LockPatternUtilForDualDarDo {
        static final int OPT_PENDING_UNLOCK = 1;
        private final Context mContext;
        private DualDarAuthUtils mDualDarAuthUtils;

        private LockPatternUtilForDualDarDo(Context context) {
            this.mContext = context;
        }

        private synchronized DualDarAuthUtils getAuthUtils() {
            if (this.mDualDarAuthUtils == null) {
                this.mDualDarAuthUtils = new DualDarAuthUtils(this.mContext);
            }
            return this.mDualDarAuthUtils;
        }

        public int getInnerAuthUserForDo() {
            return getAuthUtils().getInnerAuthUserForDo();
        }

        public boolean isInnerAuthUserForDo(int i) {
            return getAuthUtils().isInnerAuthUserForDo(i);
        }

        protected boolean checkCredential(LockscreenCredential lockscreenCredential, int i, int i2, DualDarAuthProgressCallback dualDarAuthProgressCallback) throws RequestThrottledException {
            return LockPatternUtils.this.checkCredentialForDualDarDo(lockscreenCredential, i, i2, dualDarAuthProgressCallback);
        }

        public int getPasswordMinimumLengthForInner() {
            return getAuthUtils().getPasswordMinimumLengthForInner();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, DualDarAuthProgressCallback dualDarAuthProgressCallback) throws RequestThrottledException {
        throwIfCalledOnMainThread();
        try {
            VerifyCredentialResponse verifyCredentialResponseCheckCredentialForDualDarDo = getLockSettings().checkCredentialForDualDarDo(lockscreenCredential, i, i2, wrapCallbackForDualDar(dualDarAuthProgressCallback));
            if (verifyCredentialResponseCheckCredentialForDualDarDo.getResponseCode() == 0) {
                return true;
            }
            if (verifyCredentialResponseCheckCredentialForDualDarDo.getResponseCode() != 1) {
                return false;
            }
            throw new RequestThrottledException(verifyCredentialResponseCheckCredentialForDualDarDo.getTimeout());
        } catch (RemoteException | RuntimeException e) {
            Log.e(TAG, "failed to check dualdar do credential", e);
            return false;
        }
    }
}
