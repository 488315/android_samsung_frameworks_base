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
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Binder;
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
import android.sec.enterprise.auditlog.AuditEvents;
import android.sec.enterprise.auditlog.AuditLog;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.android.internal.R;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LockPatternUtils implements LsConstants {
    private static final String APP_LOCK_FINGERPRINT_LOCKSCREEN_KEY =
            "lockscreen.applock_fingerprint";
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
    public static final byte[] ENCRYPTED_REMOTE_CREDENTIALS_HEADER =
            "encrypted_remote_credentials".getBytes(StandardCharsets.UTF_8);
    public static final int FAILED_ATTEMPTS_BEFORE_WIPE_GRACE = 5;
    public static final long FAILED_ATTEMPT_COUNTDOWN_INTERVAL_MS = 1000;
    public static final String FLAG_ENABLE_AUTO_PIN_CONFIRMATION =
            "AutoPinConfirmation__enable_auto_pin_confirmation";
    private static final boolean FRP_CREDENTIAL_ENABLED = true;
    private static final String GSI_RUNNING_PROP = "ro.gsid.image_running";
    private static final String IS_TRUST_USUALLY_MANAGED = "lockscreen.istrustusuallymanaged";
    private static final String KNOWN_TRUST_AGENTS = "lockscreen.knowntrustagents";
    public static final String KNOX_DEVICE_OWNER_KEY = "knox.device_owner";
    public static final String LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS =
            "lockscreen.power_button_instantly_locks";

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
    private HashMap<UserHandle, UserManager> mUserManagerCache;

    public interface CheckCredentialProgressCallback {
        void onEarlyMatched();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CredentialType {}

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
    public @interface VerifyFlag {}

    public static String credentialTypeToString(int credentialType) {
        switch (credentialType) {
            case -1:
                return KeyProperties.DIGEST_NONE;
            case 0:
            case 2:
            case 5:
            default:
                return "UNKNOWN_" + credentialType;
            case 1:
                return "PATTERN";
            case 3:
                return "PIN";
            case 4:
                return "PASSWORD";
            case 6:
                return "SMARTCARDNUMERIC";
        }
    }

    public boolean isTrustUsuallyManaged(int userId) {
        if (!(this.mLockSettingsService instanceof ILockSettings.Stub)) {
            throw new IllegalStateException(
                    "May only be called by TrustManagerService. Use"
                        + " TrustManager.isTrustUsuallyManaged()");
        }
        try {
            return getLockSettings().getBoolean(IS_TRUST_USUALLY_MANAGED, false, userId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setTrustUsuallyManaged(boolean managed, int userId) {
        try {
            getLockSettings().setBoolean(IS_TRUST_USUALLY_MANAGED, managed, userId);
        } catch (RemoteException e) {
        }
    }

    public void userPresent(int userId) {
        try {
            getLockSettings().userPresent(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class RequestThrottledException extends Exception {
        private int mTimeoutMs;

        public RequestThrottledException(int timeoutMs) {
            this.mTimeoutMs = timeoutMs;
        }

        public int getTimeoutMs() {
            return this.mTimeoutMs;
        }
    }

    public DevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager =
                    (DevicePolicyManager)
                            this.mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (this.mDevicePolicyManager == null) {
                Log.e(
                        TAG,
                        "Can't get DevicePolicyManagerService: is it running?",
                        new IllegalStateException("Stack trace:"));
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

    private UserManager getUserManager(int userId) {
        UserHandle userHandle = UserHandle.of(userId);
        if (this.mUserManagerCache.containsKey(userHandle)) {
            return this.mUserManagerCache.get(userHandle);
        }
        try {
            Context userContext = this.mContext.createPackageContextAsUser("system", 0, userHandle);
            UserManager userManager = (UserManager) userContext.getSystemService(UserManager.class);
            this.mUserManagerCache.put(userHandle, userManager);
            return userManager;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Failed to create context for user " + userHandle, e);
        }
    }

    private TrustManager getTrustManager() {
        TrustManager trust = (TrustManager) this.mContext.getSystemService(Context.TRUST_SERVICE);
        if (trust == null) {
            Log.e(
                    TAG,
                    "Can't get TrustManagerService: is it running?",
                    new IllegalStateException("Stack trace:"));
        }
        return trust;
    }

    public LockPatternUtils(Context context) {
        this(context, null);
    }

    public LockPatternUtils(Context context, ILockSettings lockSettings) {
        this.mLockoutDeadlines = new SparseLongArray();
        this.mUserManagerCache = new HashMap<>();
        this.mCredentialTypeQuery =
                new PropertyInvalidatedCache.QueryHandler<
                        Integer,
                        Integer>() { // from class: com.android.internal.widget.LockPatternUtils.1
                    @Override // android.app.PropertyInvalidatedCache.QueryHandler
                    public Integer apply(Integer userHandle) {
                        try {
                            return Integer.valueOf(
                                    LockPatternUtils.this
                                            .getLockSettings()
                                            .getCredentialType(userHandle.intValue()));
                        } catch (RemoteException re) {
                            Log.e(LockPatternUtils.TAG, "failed to get credential type", re);
                            return -1;
                        }
                    }

                    @Override // android.app.PropertyInvalidatedCache.QueryHandler
                    public boolean shouldBypassCache(Integer userHandle) {
                        return LockPatternUtils.isSpecialUserId(userHandle.intValue());
                    }
                };
        this.mCredentialTypeCache =
                new PropertyInvalidatedCache<>(
                        4,
                        "system_server",
                        CREDENTIAL_TYPE_API,
                        CREDENTIAL_TYPE_API,
                        this.mCredentialTypeQuery);
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        Looper looper = Looper.myLooper();
        this.mHandler = looper != null ? new Handler(looper) : null;
        this.mLockSettingsService = lockSettings;
    }

    public ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            ILockSettings service =
                    ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            this.mLockSettingsService = service;
        }
        ILockSettings service2 = this.mLockSettingsService;
        return service2;
    }

    public int getRequestedMinimumPasswordLength(int userId) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(userId)) {
            return getLockPatternUtilForDualDarDo().getPasswordMinimumLengthForInner();
        }
        return getDevicePolicyManager().getPasswordMinimumLength(null, userId);
    }

    public int getMaximumPasswordLength(int quality) {
        return getDevicePolicyManager().getPasswordMaximumLength(quality);
    }

    public PasswordMetrics getRequestedPasswordMetrics(int userId) {
        return getRequestedPasswordMetrics(userId, false);
    }

    public PasswordMetrics getRequestedPasswordMetrics(int userId, boolean deviceWideOnly) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(userId)) {
            PasswordMetrics metrics =
                    getDevicePolicyManager().getPasswordMinimumMetrics(0, deviceWideOnly);
            metrics.length = getLockPatternUtilForDualDarDo().getPasswordMinimumLengthForInner();
            return metrics;
        }
        return getDevicePolicyManager().getPasswordMinimumMetrics(userId, deviceWideOnly);
    }

    private int getRequestedPasswordHistoryLength(int userId) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(userId)) {
            return getDevicePolicyManager().getPasswordHistoryLength(null, 0);
        }
        return getDevicePolicyManager().getPasswordHistoryLength(null, userId);
    }

    public int getRequestedPasswordComplexity(int userId) {
        return getRequestedPasswordComplexity(userId, false);
    }

    public int getRequestedPasswordComplexity(int userId, boolean deviceWideOnly) {
        if (getLockPatternUtilForDualDarDo().isInnerAuthUserForDo(userId)) {
            return getDevicePolicyManager()
                    .getAggregatedPasswordComplexityForUser(0, deviceWideOnly);
        }
        return getDevicePolicyManager()
                .getAggregatedPasswordComplexityForUser(userId, deviceWideOnly);
    }

    public void reportFailedPasswordAttempt(int userId) {
        if (isSpecialUserId(this.mContext, userId, true)) {
            return;
        }
        if (userId == -9899) {
            int count = ((int) getLong(LsConstants.PREV_ATTEMPTS_COUNT, 0L, 0)) + 1;
            if (count < 3) {
                setLong(LsConstants.PREV_ATTEMPTS_COUNT, count, 0);
                return;
            }
            return;
        }
        int count2 = 0;
        try {
            count2 = getLockSettings().getFailureCount(userId);
        } catch (RemoteException re) {
            Log.e(TAG, "failed to getFailureCount", re);
        }
        Log.w(TAG, "getFailureCount = " + count2);
        if (count2 > 0) {
            getDevicePolicyManager().reportFailedPasswordAttemptWithFailureCount(userId, count2);
        }
        getTrustManager().reportUnlockAttempt(false, userId);
    }

    public void reportSuccessfulPasswordAttempt(int userId) {
        if (isSpecialUserId(this.mContext, userId, true)) {
            return;
        }
        if (userId == -9899) {
            setLong(LsConstants.PREV_ATTEMPTS_COUNT, 0L, 0);
        } else {
            getDevicePolicyManager().reportSuccessfulPasswordAttempt(userId);
            getTrustManager().reportUnlockAttempt(true, userId);
        }
    }

    public void reportPasswordLockout(int timeoutMs, int userId) {
        if (isSpecialUserId(this.mContext, userId, true) || userId == -9899) {
            return;
        }
        getTrustManager().reportUnlockLockout(timeoutMs, userId);
    }

    public int getCurrentFailedPasswordAttempts(int userId) {
        if (isSpecialUserId(this.mContext, userId, true)) {
            return 0;
        }
        if (userId == -9899) {
            return (int) getLong(LsConstants.PREV_ATTEMPTS_COUNT, 0L, 0);
        }
        return getDevicePolicyManager().getCurrentFailedPasswordAttempts(userId);
    }

    public int getMaximumFailedPasswordsForWipe(int userId) {
        if (isSpecialUserId(this.mContext, userId, true) || userId == -9899) {
            return 0;
        }
        return getDevicePolicyManager().getMaximumFailedPasswordsForWipe(null, userId);
    }

    public VerifyCredentialResponse verifyCredential(
            LockscreenCredential credential, int userId, int flags) {
        throwIfCalledOnMainThread();
        LsLog.verifyRequest(userId, Process.myPid(), this.mContext.getPackageName());
        try {
            if (isEnterpriseUser(userId)) {
                return verifyCredentialForEnterpriseUser(credential, userId, flags);
            }
            VerifyCredentialResponse response =
                    getLockSettings().verifyCredential(credential, userId, flags);
            if (response == null) {
                return VerifyCredentialResponse.ERROR;
            }
            return response;
        } catch (RemoteException re) {
            Log.e(TAG, "failed to verify credential", re);
            return VerifyCredentialResponse.ERROR;
        }
    }

    private VerifyCredentialResponse verifyCredentialForEnterpriseUser(
            LockscreenCredential credential, int userId, int flags) throws RemoteException {
        LockscreenCredential streamCredential = null;
        try {
            streamCredential = StreamCipher.encryptStream(credential);
            VerifyCredentialResponse response =
                    getLockSettings().verifyCredential(streamCredential, userId, flags);
            return (VerifyCredentialResponse)
                    Objects.requireNonNullElse(response, VerifyCredentialResponse.ERROR);
        } finally {
            if (streamCredential != null) {
                streamCredential.zeroize();
            }
        }
    }

    public VerifyCredentialResponse verifyGatekeeperPasswordHandle(
            long gatekeeperPasswordHandle, long challenge, int userId) {
        try {
            VerifyCredentialResponse response =
                    getLockSettings()
                            .verifyGatekeeperPasswordHandle(
                                    gatekeeperPasswordHandle, challenge, userId);
            if (response == null) {
                return VerifyCredentialResponse.ERROR;
            }
            return response;
        } catch (RemoteException e) {
            Log.e(TAG, "failed to verify gatekeeper password", e);
            return VerifyCredentialResponse.ERROR;
        }
    }

    public void removeGatekeeperPasswordHandle(long gatekeeperPasswordHandle) {
        try {
            getLockSettings().removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to remove gatekeeper password handle", e);
        }
    }

    public boolean checkCredential(
            LockscreenCredential credential,
            int userId,
            CheckCredentialProgressCallback progressCallback)
            throws RequestThrottledException {
        throwIfCalledOnMainThread();
        LsLog.verifyRequest(userId, Process.myPid(), this.mContext.getPackageName());
        try {
            if (isEnterpriseUser(userId)) {
                return checkCredentialForEnterpriseUser(credential, userId, progressCallback);
            }
            VerifyCredentialResponse response =
                    getLockSettings()
                            .checkCredential(credential, userId, wrapCallback(progressCallback));
            if (response == null) {
                return false;
            }
            if (response.getResponseCode() == 0) {
                return true;
            }
            if (response.getResponseCode() != 1) {
                return false;
            }
            if (response.getTimeout() > 0) {
                reportPasswordThrottleAuditLog(userId);
            }
            throw new RequestThrottledException(response.getTimeout());
        } catch (RemoteException re) {
            Log.e(TAG, "failed to check credential", re);
            return false;
        }
    }

    private boolean checkCredentialForEnterpriseUser(
            LockscreenCredential credential,
            int userId,
            CheckCredentialProgressCallback progressCallback)
            throws RemoteException, RequestThrottledException {
        LockscreenCredential streamCredential = null;
        try {
            try {
                streamCredential = StreamCipher.encryptStream(credential);
                VerifyCredentialResponse response =
                        getLockSettings()
                                .checkCredential(
                                        streamCredential, userId, wrapCallback(progressCallback));
                if (response == null) {
                    if (streamCredential != null) {
                        streamCredential.zeroize();
                    }
                    return false;
                }
                if (response.getResponseCode() == 0) {
                    if (streamCredential != null) {
                        streamCredential.zeroize();
                    }
                    return true;
                }
                if (response.getResponseCode() == 1) {
                    if (response.getTimeout() > 0) {
                        reportPasswordThrottleAuditLog(userId);
                    }
                    throw new RequestThrottledException(response.getTimeout());
                }
                if (streamCredential != null) {
                    streamCredential.zeroize();
                }
                return false;
            } catch (RuntimeException re) {
                if (!SemPersonaManager.isKnoxId(userId)) {
                    throw re;
                }
                re.printStackTrace();
                if (streamCredential != null) {
                    streamCredential.zeroize();
                }
                return false;
            }
        } catch (Throwable th) {
            if (streamCredential != null) {
                streamCredential.zeroize();
            }
            throw th;
        }
    }

    public VerifyCredentialResponse verifyTiedProfileChallenge(
            LockscreenCredential credential, int userId, int flags) {
        throwIfCalledOnMainThread();
        try {
            VerifyCredentialResponse response =
                    getLockSettings().verifyTiedProfileChallenge(credential, userId, flags);
            if (response == null) {
                return VerifyCredentialResponse.ERROR;
            }
            return response;
        } catch (RemoteException re) {
            Log.e(TAG, "failed to verify tied profile credential", re);
            return VerifyCredentialResponse.ERROR;
        }
    }

    public byte[] getPasswordHistoryHashFactor(LockscreenCredential currentPassword, int userId) {
        LsLog.verifyRequest(userId, Process.myPid(), this.mContext.getPackageName());
        try {
            if (isEnterpriseUser(userId)) {
                return getPasswordHistoryHashFactorForEnterpriseUser(currentPassword, userId);
            }
            return getLockSettings().getHashFactor(currentPassword, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "failed to get hash factor", e);
            return null;
        }
    }

    private byte[] getPasswordHistoryHashFactorForEnterpriseUser(
            LockscreenCredential currentPassword, int userId) throws RemoteException {
        LockscreenCredential streamCredential = null;
        try {
            streamCredential = StreamCipher.encryptStream(currentPassword);
            return getLockSettings().getHashFactor(streamCredential, userId);
        } finally {
            if (streamCredential != null) {
                streamCredential.zeroize();
            }
        }
    }

    public boolean checkPasswordHistory(byte[] passwordToCheck, byte[] hashFactor, int userId) {
        int passwordHistoryLength;
        if (passwordToCheck == null || passwordToCheck.length == 0) {
            Log.e(TAG, "checkPasswordHistory: empty password");
            return false;
        }
        String passwordHistory = getString(PASSWORD_HISTORY_KEY, userId);
        if (TextUtils.isEmpty(passwordHistory)
                || (passwordHistoryLength = getRequestedPasswordHistoryLength(userId)) == 0) {
            return false;
        }
        byte[] salt = getSalt(userId).getBytes();
        String legacyHash = LockscreenCredential.legacyPasswordToHash(passwordToCheck, salt);
        String passwordHash =
                LockscreenCredential.passwordToHistoryHash(passwordToCheck, salt, hashFactor);
        String[] history = passwordHistory.split(",");
        for (int i = 0; i < Math.min(passwordHistoryLength, history.length); i++) {
            if (history[i].equals(legacyHash) || history[i].equals(passwordHash)) {
                return true;
            }
        }
        return false;
    }

    public int getPinLength(int userId) {
        if (userId == -9899) {
            return -1;
        }
        try {
            return getLockSettings().getPinLength(userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not fetch PIN length " + e);
            return -1;
        }
    }

    public boolean refreshStoredPinLength(int userId) {
        try {
            return getLockSettings().refreshStoredPinLength(userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not store PIN length on disk " + e);
            return false;
        }
    }

    public int getActivePasswordQuality(int userId) {
        return getKeyguardStoredPasswordQuality(userId);
    }

    public void resetKeyStore(int userId) {
        try {
            getLockSettings().resetKeyStore(userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't reset keystore " + e);
        }
    }

    public void setLockScreenDisabled(boolean disable, int userId) {
        setBoolean("lockscreen.disabled", disable, userId);
    }

    public boolean isLockScreenDisabled(int userId) {
        if (isSecure(userId)) {
            return false;
        }
        boolean disabledByDefault =
                this.mContext.getResources().getBoolean(R.bool.config_disableLockscreenByDefault);
        UserInfo userInfo = getUserManager().getUserInfo(userId);
        boolean isDemoUser =
                UserManager.isDeviceInDemoMode(this.mContext)
                        && userInfo != null
                        && userInfo.isDemo();
        return getBoolean("lockscreen.disabled", false, userId) || disabledByDefault || isDemoUser;
    }

    public void setAutoPinConfirm(boolean enabled, int userId) {
        setBoolean(AUTO_PIN_CONFIRM, enabled, userId);
    }

    public boolean isAutoPinConfirmEnabled(int userId) {
        return getBoolean(AUTO_PIN_CONFIRM, false, userId);
    }

    public static boolean isAutoPinConfirmFeatureAvailable() {
        return true;
    }

    public static boolean isQualityAlphabeticPassword(int quality) {
        return quality >= 262144;
    }

    public static boolean isQualityNumericPin(int quality) {
        return quality == 131072 || quality == 196608;
    }

    public static int credentialTypeToPasswordQuality(int credentialType) {
        switch (credentialType) {
            case -1:
                return 0;
            case 0:
            case 2:
            case 5:
            default:
                throw new IllegalStateException("Unknown type: " + credentialType);
            case 1:
                return 65536;
            case 3:
                return 131072;
            case 4:
                return 262144;
            case 6:
                return 458752;
        }
    }

    public static int pinOrPasswordQualityToCredentialType(int quality) {
        if (isQualitySmartCard(quality)) {
            return 6;
        }
        if (isQualityAlphabeticPassword(quality)) {
            return 4;
        }
        if (isQualityNumericPin(quality)) {
            return 3;
        }
        throw new IllegalArgumentException("Quality is neither Pin nor password: " + quality);
    }

    public boolean setLockCredential(
            LockscreenCredential newCredential,
            LockscreenCredential savedCredential,
            int userHandle) {
        return setLockCredential(newCredential, savedCredential, userHandle, false);
    }

    public boolean setLockCredential(
            LockscreenCredential newCredential,
            LockscreenCredential savedCredential,
            int userHandle,
            boolean ignoreNotifyPasswordChanged) {
        if (!hasSecureLockScreen() && newCredential.getType() != -1) {
            throw new UnsupportedOperationException(
                    "This operation requires the lock screen feature.");
        }
        LsLog.enrollRequest(userHandle, Process.myPid(), this.mContext.getPackageName());
        LsLog.enroll(
                String.format(
                        "Enroll [User %d %s][%s:%d]\n%s",
                        Integer.valueOf(userHandle),
                        credentialTypeToString(newCredential.getType()),
                        this.mContext.getPackageName(),
                        Integer.valueOf(Process.myPid()),
                        Debug.getCallers(10, "    ")));
        try {
            if (isEnterpriseUser(userHandle)) {
                if (!setLockCredentialForEnterpriseUser(
                        newCredential, savedCredential, userHandle, ignoreNotifyPasswordChanged)) {
                    return false;
                }
            } else if (!getLockSettings()
                    .setLockCredential(newCredential, savedCredential, userHandle)) {
                return false;
            }
            if (newCredential.isNone()) {
                clearBiometricAndLockState(userHandle);
            }
            try {
                getLockSettings().sendLockTypeChangedInfo(newCredential.isNone() ? 1 : 2);
            } catch (Exception e) {
                Log.e(TAG, "sendLockTypeChangedInfo Failed!", e);
            }
            return true;
        } catch (RemoteException e2) {
            throw new RuntimeException("Unable to save lock password", e2);
        }
    }

    private boolean setLockCredentialForEnterpriseUser(
            LockscreenCredential newCredential,
            LockscreenCredential savedCredential,
            int userHandle,
            boolean ignoreNotifyPasswordChanged)
            throws RemoteException {
        LockscreenCredential streamNewCredential = null;
        LockscreenCredential streamSavedCredential = null;
        try {
            streamNewCredential = StreamCipher.encryptStream(newCredential);
            streamSavedCredential = StreamCipher.encryptStream(savedCredential);
            if (!getLockSettings()
                    .setLockCredentialWithIgnoreNotifyIfNeeded(
                            streamNewCredential,
                            streamSavedCredential,
                            userHandle,
                            ignoreNotifyPasswordChanged)) {}
            if (streamNewCredential != null) {
                streamNewCredential.zeroize();
            }
            if (streamSavedCredential != null) {
                streamSavedCredential.zeroize();
                return true;
            }
            return true;
        } finally {
            if (streamNewCredential != null) {
                streamNewCredential.zeroize();
            }
            if (streamSavedCredential != null) {
                streamSavedCredential.zeroize();
            }
        }
    }

    public void notifyPasswordChangedForEnterpriseUser(
            LockscreenCredential newCredential, int userId) {
        try {
            getLockSettings().notifyPasswordChangedForEnterpriseUser(newCredential, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't notify password changed for enterprise user");
        }
    }

    public boolean isVisiblePatternDisabledByMDM() {
        EnterpriseDeviceManager edm = EnterpriseDeviceManager.getInstance();
        if (edm != null && edm.getPasswordPolicy() != null) {
            return !edm.getPasswordPolicy().isScreenLockPatternVisibilityEnabled();
        }
        return false;
    }

    public boolean isVisiblePatternDisabledByMDMAsUser(int userId) {
        EnterpriseDeviceManager edm = EnterpriseDeviceManager.getInstance();
        if (edm != null && edm.getPasswordPolicy() != null) {
            return !edm.getPasswordPolicy().isScreenLockPatternVisibilityEnabledAsUser(userId);
        }
        return false;
    }

    public boolean isLockPasswordEnabledNoCache(int userId) {
        long mode = (int) getLong(PASSWORD_TYPE_KEY, 0L, userId);
        long backupMode = (int) getLong(PASSWORD_TYPE_ALTERNATE_KEY, 0L, userId);
        boolean passwordEnabled =
                mode == 262144
                        || mode == 131072
                        || mode == 196608
                        || mode == 327680
                        || mode == 393216
                        || mode == 458752;
        boolean backupEnabled =
                backupMode == 262144
                        || backupMode == 131072
                        || backupMode == 196608
                        || backupMode == 327680
                        || backupMode == 393216;
        if (havePasswordNoMDMCache(userId)) {
            return passwordEnabled
                    || (getKeyguardStoredPasswordQuality(userId) == 32768 && backupEnabled);
        }
        return false;
    }

    private boolean havePasswordNoMDMCache(int userId) {
        ILockSettings lockSettingsService =
                ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        int ret = -1;
        if (lockSettingsService != null) {
            try {
                ret = lockSettingsService.getCredentialType(userId);
            } catch (Exception e) {
                Log.e(TAG, "Unable to reach LockSettingsService");
                ret = -1;
            }
        }
        if (ret == -1) {
            Log.d(TAG, "havePasswordNoMDMCache() : no password in User " + userId);
        }
        return ret != -1;
    }

    public void setOwnerInfo(String info, int userId) {
        setString("lock_screen_owner_info", info, userId);
    }

    public void setOwnerInfoEnabled(boolean enabled, int userId) {
        setBoolean("lock_screen_owner_info_enabled", enabled, userId);
    }

    public String getOwnerInfo(int userId) {
        return getString("lock_screen_owner_info", userId);
    }

    public boolean isOwnerInfoEnabled(int userId) {
        return getBoolean("lock_screen_owner_info_enabled", false, userId);
    }

    public void setDeviceOwnerInfo(String info) {
        if (info != null && info.isEmpty()) {
            info = null;
        }
        setString(LOCK_SCREEN_DEVICE_OWNER_INFO, info, 0);
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
    public int getKeyguardStoredPasswordQuality(int userHandle) {
        return credentialTypeToPasswordQuality(getCredentialTypeForUser(userHandle));
    }

    public void setSeparateProfileChallengeEnabled(
            int userHandle, boolean enabled, LockscreenCredential profilePassword) {
        if (!isCredentialSharableWithParent(userHandle)) {
            return;
        }
        try {
            getLockSettings()
                    .setSeparateProfileChallengeEnabled(userHandle, enabled, profilePassword);
            reportEnabledTrustAgentsChanged(userHandle);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't update work profile challenge enabled");
        }
    }

    public boolean isSeparateProfileChallengeEnabled(int userHandle) {
        return isCredentialSharableWithParent(userHandle) && hasSeparateChallenge(userHandle);
    }

    public boolean isProfileWithUnifiedChallenge(int userHandle) {
        return isCredentialSharableWithParent(userHandle) && !hasSeparateChallenge(userHandle);
    }

    public boolean isManagedProfileWithUnifiedChallenge(int userHandle) {
        return isManagedProfile(userHandle) && !hasSeparateChallenge(userHandle);
    }

    private boolean hasSeparateChallenge(int userHandle) {
        try {
            return getLockSettings().getSeparateProfileChallengeEnabled(userHandle);
        } catch (RemoteException e) {
            Log.e(TAG, "Couldn't get separate profile challenge enabled");
            return false;
        }
    }

    private boolean isManagedProfile(int userHandle) {
        UserInfo info = getUserManager().getUserInfo(userHandle);
        return info != null && info.isManagedProfile();
    }

    private boolean isCredentialSharableWithParent(int userHandle) {
        return getUserManager(userHandle).isCredentialSharableWithParent();
    }

    public static List<LockPatternView.Cell> byteArrayToPattern(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        List<LockPatternView.Cell> result = Lists.newArrayList();
        for (byte b : bytes) {
            byte b2 = (byte) (b - 49);
            result.add(LockPatternView.Cell.of(b2 / 3, b2 % 3));
        }
        return result;
    }

    public static byte[] patternToByteArray(List<LockPatternView.Cell> pattern) {
        if (pattern == null) {
            return new byte[0];
        }
        int patternSize = pattern.size();
        byte[] res = new byte[patternSize];
        for (int i = 0; i < patternSize; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) ((cell.getRow() * 3) + cell.getColumn() + 49);
        }
        return res;
    }

    private String getSalt(int userId) {
        long salt = getLong(LOCK_PASSWORD_SALT_KEY, 0L, userId);
        if (salt == 0) {
            try {
                salt = SecureRandom.getInstance("SHA1PRNG").nextLong();
                setLong(LOCK_PASSWORD_SALT_KEY, salt, userId);
                Log.v(TAG, "Initialized lock password salt for user: " + userId);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("Couldn't get SecureRandom number", e);
            }
        }
        return Long.toHexString(salt);
    }

    public static final void invalidateCredentialTypeCache() {
        PropertyInvalidatedCache.invalidateCache("system_server", CREDENTIAL_TYPE_API);
    }

    public int getCredentialTypeForUser(int userHandle) {
        return this.mCredentialTypeCache.query(Integer.valueOf(userHandle)).intValue();
    }

    public boolean isSecure(int userId) {
        int type = getCredentialTypeForUser(userId);
        return type != -1 || isRemoteLock(userId);
    }

    public boolean isLockPasswordEnabled(int userId) {
        int type = getCredentialTypeForUser(userId);
        return type == 4 || type == 3 || type == 6;
    }

    public boolean isLockPatternEnabled(int userId) {
        int type = getCredentialTypeForUser(userId);
        return type == 1;
    }

    public boolean isVisiblePatternEnabled(int userId) {
        if (isVisiblePatternDisabledByMDMAsUser(userId)) {
            Log.d(TAG, "pattern visibility disabled by MDM for user : " + userId);
            return false;
        }
        return getBoolean("lock_pattern_visible_pattern", true, userId);
    }

    public void setVisiblePatternEnabled(boolean enabled, int userId) {
        if (isVisiblePatternDisabledByMDMAsUser(userId) && enabled) {
            Log.e(
                    TAG,
                    "setVisiblePatternEnabled() : Could not enable visible pattern by MDM admin."
                        + " user : "
                            + userId);
        } else {
            setBoolean("lock_pattern_visible_pattern", enabled, userId);
        }
    }

    public boolean isVisiblePatternEverChosen(int userId) {
        return getString("lock_pattern_visible_pattern", userId) != null;
    }

    public boolean isPinEnhancedPrivacyEnabled(int userId) {
        return getBoolean(LOCK_PIN_ENHANCED_PRIVACY, false, userId);
    }

    public void setPinEnhancedPrivacyEnabled(boolean enabled, int userId) {
        setBoolean(LOCK_PIN_ENHANCED_PRIVACY, enabled, userId);
    }

    public boolean isPinEnhancedPrivacyEverChosen(int userId) {
        return getString(LOCK_PIN_ENHANCED_PRIVACY, userId) != null;
    }

    public long setLockoutAttemptDeadline(int userId, int timeoutMs) {
        long deadline = SystemClock.elapsedRealtime() + timeoutMs;
        if (userId == -9999) {
            return deadline;
        }
        if (userId == -9899) {
            return 0L;
        }
        setLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, timeoutMs, userId);
        setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, deadline, userId);
        return deadline;
    }

    public long getLockoutAttemptDeadline(int userId) {
        long deadline = getLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, 0L, userId);
        long timeoutMs = getLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, userId);
        long now = SystemClock.elapsedRealtime();
        if (deadline < now && deadline != 0) {
            setLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, userId);
            setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, 0L, userId);
            return 0L;
        }
        if (deadline > now + timeoutMs) {
            long deadline2 = now + timeoutMs;
            setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, deadline2, userId);
            return deadline2;
        }
        return deadline;
    }

    private boolean getBoolean(String secureSettingKey, boolean defaultValue, int userId) {
        try {
            return getLockSettings().getBoolean(secureSettingKey, defaultValue, userId);
        } catch (RemoteException e) {
            return defaultValue;
        }
    }

    private void setBoolean(String secureSettingKey, boolean enabled, int userId) {
        try {
            getLockSettings().setBoolean(secureSettingKey, enabled, userId);
        } catch (RemoteException re) {
            Log.e(TAG, "Couldn't write boolean " + secureSettingKey + re);
        }
    }

    private long getLong(String secureSettingKey, long defaultValue, int userHandle) {
        try {
            return getLockSettings().getLong(secureSettingKey, defaultValue, userHandle);
        } catch (RemoteException e) {
            return defaultValue;
        }
    }

    private void setLong(String secureSettingKey, long value, int userHandle) {
        try {
            getLockSettings().setLong(secureSettingKey, value, userHandle);
        } catch (RemoteException re) {
            Log.e(TAG, "Couldn't write long " + secureSettingKey + re);
        }
    }

    private String getString(String secureSettingKey, int userHandle) {
        try {
            return getLockSettings().getString(secureSettingKey, null, userHandle);
        } catch (RemoteException e) {
            return null;
        }
    }

    private void setString(String secureSettingKey, String value, int userHandle) {
        try {
            getLockSettings().setString(secureSettingKey, value, userHandle);
        } catch (RemoteException re) {
            Log.e(TAG, "Couldn't write string " + secureSettingKey + re);
        }
    }

    public void setPowerButtonInstantlyLocks(boolean enabled, int userId) {
        setBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, enabled, userId);
    }

    public boolean getPowerButtonInstantlyLocks(int userId) {
        return getBoolean(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, true, userId);
    }

    public boolean isPowerButtonInstantlyLocksEverChosen(int userId) {
        return getString(LOCKSCREEN_POWER_BUTTON_INSTANTLY_LOCKS, userId) != null;
    }

    public void setEnabledTrustAgents(Collection<ComponentName> activeTrustAgents, int userId) {
        setString(ENABLED_TRUST_AGENTS, serializeTrustAgents(activeTrustAgents), userId);
        getTrustManager().reportEnabledTrustAgentsChanged(userId);
    }

    public List<ComponentName> getEnabledTrustAgents(int userId) {
        return deserializeTrustAgents(getString(ENABLED_TRUST_AGENTS, userId));
    }

    public void setKnownTrustAgents(Collection<ComponentName> knownTrustAgents, int userId) {
        setString(KNOWN_TRUST_AGENTS, serializeTrustAgents(knownTrustAgents), userId);
    }

    public List<ComponentName> getKnownTrustAgents(int userId) {
        return deserializeTrustAgents(getString(KNOWN_TRUST_AGENTS, userId));
    }

    private String serializeTrustAgents(Collection<ComponentName> trustAgents) {
        StringBuilder sb = new StringBuilder();
        for (ComponentName cn : trustAgents) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(cn.flattenToShortString());
        }
        return sb.toString();
    }

    private List<ComponentName> deserializeTrustAgents(String serializedTrustAgents) {
        if (TextUtils.isEmpty(serializedTrustAgents)) {
            return new ArrayList();
        }
        String[] split = serializedTrustAgents.split(",");
        ArrayList<ComponentName> trustAgents = new ArrayList<>(split.length);
        for (String s : split) {
            if (!TextUtils.isEmpty(s)) {
                trustAgents.add(ComponentName.unflattenFromString(s));
            }
        }
        return trustAgents;
    }

    public void requireCredentialEntry(int userId) {
        requireStrongAuth(4, userId);
    }

    public void requireStrongAuth(int strongAuthReason, int userId) {
        try {
            getLockSettings().requireStrongAuth(strongAuthReason, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Error while requesting strong auth: " + e);
        }
    }

    private void reportPasswordThrottleAuditLog(int userId) {
        int failedAttempts = getCurrentFailedPasswordAttempts(userId);
        long token = Binder.clearCallingIdentity();
        try {
            AuditLog.logAsUser(
                    5,
                    1,
                    true,
                    Process.myPid(),
                    getClass().getSimpleName(),
                    String.format(
                            AuditEvents.AUDIT_INCORRECT_AUTHENTICATION_ATTEMPTS_LIMIT_REACHED,
                            Integer.valueOf(failedAttempts)),
                    userId);
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    private void reportEnabledTrustAgentsChanged(int userHandle) {
        if (VirtualLockUtils.isVirtualUserId(userHandle)) {
            return;
        }
        getTrustManager().reportEnabledTrustAgentsChanged(userHandle);
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
            throw new RuntimeException("Could not register StrongAuthTracker");
        }
    }

    public void unregisterStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        try {
            getLockSettings().unregisterStrongAuthTracker(strongAuthTracker.getStub());
        } catch (RemoteException e) {
            Log.e(TAG, "Could not unregister StrongAuthTracker", e);
        }
    }

    public boolean registerWeakEscrowTokenRemovedListener(
            IWeakEscrowTokenRemovedListener listener) {
        try {
            return getLockSettings().registerWeakEscrowTokenRemovedListener(listener);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not register WeakEscrowTokenRemovedListener.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unregisterWeakEscrowTokenRemovedListener(
            IWeakEscrowTokenRemovedListener listener) {
        try {
            return getLockSettings().unregisterWeakEscrowTokenRemovedListener(listener);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not register WeakEscrowTokenRemovedListener.");
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportSuccessfulBiometricUnlock(boolean isStrongBiometric, int userId) {
        try {
            getLockSettings().reportSuccessfulBiometricUnlock(isStrongBiometric, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not report successful biometric unlock", e);
        }
    }

    public void scheduleNonStrongBiometricIdleTimeout(int userId) {
        try {
            getLockSettings().scheduleNonStrongBiometricIdleTimeout(userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not schedule non-strong biometric idle timeout", e);
        }
    }

    public int getStrongAuthForUser(int userId) {
        try {
            return getLockSettings().getStrongAuthForUser(userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not get StrongAuth", e);
            return StrongAuthTracker.getDefaultFlags(this.mContext);
        }
    }

    public boolean isCredentialsDisabledForUser(int userId) {
        return getDevicePolicyManager().getPasswordQuality(null, userId) == 524288;
    }

    public boolean isTrustAllowedForUser(int userId) {
        return getStrongAuthForUser(userId) == 0;
    }

    public boolean isBiometricAllowedForUser(int userId) {
        return (getStrongAuthForUser(userId) & (-781)) == 0 && !isUCMLockEnabled(userId);
    }

    public boolean isUserInLockdown(int userId) {
        return (getStrongAuthForUser(userId) & 32) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class WrappedCallback extends ICheckCredentialProgressCallback.Stub {
        private CheckCredentialProgressCallback mCallback;
        private Handler mHandler;

        WrappedCallback(Handler handler, CheckCredentialProgressCallback callback) {
            this.mHandler = handler;
            this.mCallback = callback;
        }

        @Override // com.android.internal.widget.ICheckCredentialProgressCallback
        public void onCredentialVerified() throws RemoteException {
            if (this.mHandler == null) {
                Log.e(LockPatternUtils.TAG, "Handler is null during callback");
            }
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.internal.widget.LockPatternUtils$WrappedCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LockPatternUtils.WrappedCallback.this.lambda$onCredentialVerified$0();
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

    private ICheckCredentialProgressCallback wrapCallback(
            CheckCredentialProgressCallback callback) {
        if (callback == null) {
            return null;
        }
        if (this.mHandler == null) {
            throw new IllegalStateException(
                    "Must construct LockPatternUtils on a looper thread to use progress"
                        + " callbacks.");
        }
        return new WrappedCallback(this.mHandler, callback);
    }

    private LockSettingsInternal getLockSettingsInternal() {
        LockSettingsInternal service =
                (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
        if (service == null) {
            throw new SecurityException("Only available to system server itself");
        }
        return service;
    }

    public long addEscrowToken(byte[] token, int userId, EscrowTokenStateChangeCallback callback) {
        return getLockSettingsInternal().addEscrowToken(token, userId, callback);
    }

    public long addWeakEscrowToken(
            byte[] token, int userId, IWeakEscrowTokenActivatedListener callback) {
        try {
            return getLockSettings().addWeakEscrowToken(token, userId, callback);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not add weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeEscrowToken(long handle, int userId) {
        return getLockSettingsInternal().removeEscrowToken(handle, userId);
    }

    public boolean removeWeakEscrowToken(long handle, int userId) {
        try {
            return getLockSettings().removeWeakEscrowToken(handle, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not remove the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isEscrowTokenActive(long handle, int userId) {
        return getLockSettingsInternal().isEscrowTokenActive(handle, userId);
    }

    public boolean isWeakEscrowTokenActive(long handle, int userId) {
        try {
            return getLockSettings().isWeakEscrowTokenActive(handle, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not check the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWeakEscrowTokenValid(long handle, byte[] token, int userId) {
        try {
            return getLockSettings().isWeakEscrowTokenValid(handle, token, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not validate the weak token.");
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setLockCredentialWithToken(
            LockscreenCredential credential, long tokenHandle, byte[] token, int userHandle) {
        if (!hasSecureLockScreen() && credential.getType() != -1) {
            throw new UnsupportedOperationException(
                    "This operation requires the lock screen feature.");
        }
        LsLog.enroll(
                String.format(
                        "Enroll [User %d %s][%s]\n%s",
                        Integer.valueOf(userHandle),
                        credentialTypeToString(credential.getType()),
                        this.mContext.getPackageName(),
                        Debug.getCallers(10, "    ")));
        LockSettingsInternal localService = getLockSettingsInternal();
        return localService.setLockCredentialWithToken(credential, tokenHandle, token, userHandle);
    }

    public boolean unlockUserWithToken(long tokenHandle, byte[] token, int userId) {
        return getLockSettingsInternal().unlockUserWithToken(tokenHandle, token, userId);
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
        public @interface StrongAuthFlags {}

        public StrongAuthTracker(Context context) {
            this(context, Looper.myLooper());
        }

        public StrongAuthTracker(Context context, Looper looper) {
            this.mStrongAuthRequiredForUser = new SparseIntArray();
            this.mIsNonStrongBiometricAllowedForUser = new SparseBooleanArray();
            this.mDefaultIsNonStrongBiometricAllowed = true;
            this.mStub =
                    new IStrongAuthTracker
                            .Stub() { // from class:
                                      // com.android.internal.widget.LockPatternUtils.StrongAuthTracker.1
                        @Override // android.app.trust.IStrongAuthTracker
                        public void onStrongAuthRequiredChanged(int strongAuthFlags, int userId) {
                            StrongAuthTracker.this
                                    .mHandler
                                    .obtainMessage(1, strongAuthFlags, userId)
                                    .sendToTarget();
                        }

                        @Override // android.app.trust.IStrongAuthTracker
                        public void onIsNonStrongBiometricAllowedChanged(boolean z, int i) {
                            StrongAuthTracker.this
                                    .mHandler
                                    .obtainMessage(2, z ? 1 : 0, i)
                                    .sendToTarget();
                        }
                    };
            this.mHandler = new H(looper);
            this.mDefaultStrongAuthFlags = getDefaultFlags(context);
        }

        public static int getDefaultFlags(Context context) {
            return context.getResources().getBoolean(R.bool.config_strongAuthRequiredOnBoot)
                    ? 1
                    : 0;
        }

        public int getStrongAuthForUser(int userId) {
            return this.mStrongAuthRequiredForUser.get(userId, this.mDefaultStrongAuthFlags);
        }

        public boolean isTrustAllowedForUser(int userId) {
            return getStrongAuthForUser(userId) == 0;
        }

        public boolean isBiometricAllowedForUser(boolean isStrongBiometric, int userId) {
            boolean allowed = (getStrongAuthForUser(userId) & (-781)) == 0;
            if (!isStrongBiometric) {
                return allowed & isNonStrongBiometricAllowedAfterIdleTimeout(userId);
            }
            return allowed;
        }

        public boolean isNonStrongBiometricAllowedAfterIdleTimeout(int userId) {
            return this.mIsNonStrongBiometricAllowedForUser.get(userId, true);
        }

        public void onStrongAuthRequiredChanged(int userId) {}

        public void onIsNonStrongBiometricAllowedChanged(int userId) {}

        protected void handleStrongAuthRequiredChanged(int strongAuthFlags, int userId) {
            int oldValue = getStrongAuthForUser(userId);
            if (strongAuthFlags != oldValue) {
                if (strongAuthFlags == this.mDefaultStrongAuthFlags) {
                    this.mStrongAuthRequiredForUser.delete(userId);
                } else {
                    this.mStrongAuthRequiredForUser.put(userId, strongAuthFlags);
                }
                onStrongAuthRequiredChanged(userId);
            }
        }

        protected void handleIsNonStrongBiometricAllowedChanged(boolean allowed, int userId) {
            boolean oldValue = isNonStrongBiometricAllowedAfterIdleTimeout(userId);
            if (allowed != oldValue) {
                if (allowed) {
                    this.mIsNonStrongBiometricAllowedForUser.delete(userId);
                } else {
                    this.mIsNonStrongBiometricAllowedForUser.put(userId, allowed);
                }
                onIsNonStrongBiometricAllowedChanged(userId);
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
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        StrongAuthTracker.this.handleStrongAuthRequiredChanged(msg.arg1, msg.arg2);
                        break;
                    case 2:
                        StrongAuthTracker.this.handleIsNonStrongBiometricAllowedChanged(
                                msg.arg1 == 1, msg.arg2);
                        break;
                }
            }
        }
    }

    public void saveAppLockPassword(String password, SecAppLockType lockType, int userId) {
        try {
            if (lockType == SecAppLockType.PIN) {
                getLockSettings().setAppLockPin(password, userId);
            } else if (lockType == SecAppLockType.Password) {
                getLockSettings().setAppLockPassword(password, userId);
            } else if (lockType == SecAppLockType.Pattern) {
                getLockSettings().setAppLockPattern(password, userId);
            } else if (lockType == SecAppLockType.BackupPin) {
                getLockSettings().setAppLockBackupPin(password, userId);
            }
        } catch (RemoteException re) {
            Log.e(TAG, "Unable to save lock " + lockType + " :: " + re);
        }
    }

    public boolean checkAppLockPassword(String password, SecAppLockType lockType, int userId) {
        try {
            if (lockType == SecAppLockType.PIN) {
                return getLockSettings().checkAppLockPin(password, userId);
            }
            if (lockType == SecAppLockType.Password) {
                return getLockSettings().checkAppLockPassword(password, userId);
            }
            if (lockType == SecAppLockType.Pattern) {
                return getLockSettings().checkAppLockPatternWithHash(password, userId, null);
            }
            if (lockType == SecAppLockType.BackupPin) {
                return getLockSettings().checkAppLockBackupPin(password, userId);
            }
            return true;
        } catch (RemoteException re) {
            Log.e(TAG, "Unable to Check applock password :: " + re);
            return false;
        }
    }

    public boolean checkAppLockPassword(
            String password, SecAppLockType lockType, int userId, byte[] hash) {
        try {
            if (lockType == SecAppLockType.PIN) {
                return getLockSettings().checkAppLockPin(password, userId);
            }
            if (lockType == SecAppLockType.Password) {
                return getLockSettings().checkAppLockPassword(password, userId);
            }
            if (lockType == SecAppLockType.Pattern) {
                return getLockSettings().checkAppLockPatternWithHash(password, userId, hash);
            }
            if (lockType == SecAppLockType.BackupPin) {
                return getLockSettings().checkAppLockBackupPin(password, userId);
            }
            return true;
        } catch (RemoteException re) {
            Log.e(TAG, "Unable to Check applock password :: " + re);
            return false;
        }
    }

    public boolean savedAppLockPasswordExists(SecAppLockType lockType, int userId) {
        try {
            if (lockType == SecAppLockType.PIN) {
                return getLockSettings().haveAppLockPin(userId);
            }
            if (lockType == SecAppLockType.Password) {
                return getLockSettings().haveAppLockPassword(userId);
            }
            if (lockType == SecAppLockType.Pattern) {
                return getLockSettings().haveAppLockPattern(userId);
            }
            if (lockType != SecAppLockType.BackupPin) {
                return false;
            }
            return getLockSettings().haveAppLockBackupPin(userId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void setAppLockFingerPrintLockscreen(boolean enabled, int userId) {
        setBoolean(APP_LOCK_FINGERPRINT_LOCKSCREEN_KEY, enabled, userId);
    }

    public boolean isAppLockFingerPrintLockscreen(int userId) {
        return getBoolean(APP_LOCK_FINGERPRINT_LOCKSCREEN_KEY, false, userId);
    }

    public boolean hasPendingEscrowToken(int userId) {
        try {
            return getLockSettings().hasPendingEscrowToken(userId);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean hasSecureLockScreen() {
        if (this.mHasSecureLockScreen == null) {
            try {
                this.mHasSecureLockScreen =
                        Boolean.valueOf(getLockSettings().hasSecureLockScreen());
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return this.mHasSecureLockScreen.booleanValue();
    }

    public static boolean userOwnsFrpCredential(Context context, UserInfo info) {
        return info != null && info.isMain() && info.isAdmin() && frpCredentialEnabled(context);
    }

    public static boolean frpCredentialEnabled(Context context) {
        return context.getResources()
                .getBoolean(R.bool.config_enableCredentialFactoryResetProtection);
    }

    public static boolean isRepairModeSupported(Context context) {
        return context.getResources().getBoolean(R.bool.config_repairModeSupported);
    }

    public static boolean isRepairModeActive(Context context) {
        return Settings.Global.getInt(
                        context.getContentResolver(), Settings.Global.REPAIR_MODE_ACTIVE, 0)
                > 0;
    }

    public static boolean canUserEnterRepairMode(Context context, UserInfo info) {
        return info != null && info.isAdmin() && isRepairModeSupported(context);
    }

    public static boolean isGsiRunning() {
        return SystemProperties.getInt(GSI_RUNNING_PROP, 0) > 0;
    }

    public static boolean isSpecialUserId(int userId) {
        return isSpecialUserId(null, userId, false);
    }

    private static boolean isSpecialUserId(
            Context context, int userId, boolean checkDeviceSupported) {
        switch (userId) {
            case USER_FRP /* -9999 */:
                if (checkDeviceSupported) {
                    return frpCredentialEnabled(context);
                }
                return true;
            case USER_REPAIR_MODE /* -9998 */:
                if (checkDeviceSupported) {
                    return isRepairModeSupported(context);
                }
                return true;
            default:
                return false;
        }
    }

    public boolean tryUnlockWithCachedUnifiedChallenge(int userId) {
        try {
            return getLockSettings().tryUnlockWithCachedUnifiedChallenge(userId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public void removeCachedUnifiedChallenge(int userId) {
        try {
            getLockSettings().removeCachedUnifiedChallenge(userId);
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
        }
    }

    public boolean isDevicePasswordSimple(int userId) {
        return Settings.Secure.getIntForUser(this.mContentResolver, "is_smpw_key", 0, userId) == 1;
    }

    public void unlockUserKeyIfUnsecured(int userId) {
        LsLog.verifyRequest(userId, Process.myPid(), this.mContext.getPackageName());
        try {
            getLockSettings().unlockUserKeyIfUnsecured(userId);
        } catch (RemoteException re) {
            re.rethrowFromSystemServer();
        }
    }

    public void createNewUser(int userId, int userSerialNumber) {
        getLockSettingsInternal().createNewUser(userId, userSerialNumber);
    }

    public void removeUser(int userId) {
        getLockSettingsInternal().removeUser(userId);
    }

    public RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        try {
            return getLockSettings().startRemoteLockscreenValidation();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] encryptedCredential) {
        try {
            return getLockSettings().validateRemoteLockscreen(encryptedCredential);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getLockoutAttemptTimeout(int userId) {
        return getLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, userId);
    }

    public void clearLockoutAttemptDeadline(int userId) {
        setLong(LsConstants.LOCKOUT_ATTEMPT_TIMEOUT_MS, 0L, userId);
        setLong(LsConstants.LOCKOUT_ATTEMPT_DEADLINE, 0L, userId);
    }

    public void backupLockSettingsDB() {
        setLong(LsConstants.LOCKSETTINGS_DB_BACKUP, System.currentTimeMillis(), 0);
    }

    public void restoreLockSettingsDB() {
        setLong(LsConstants.LOCKSETTINGS_DB_RESTORE, System.currentTimeMillis(), 0);
    }

    public void setPasswordHint(String hint, int userId) {
        if (hint != null && hint.isEmpty()) {
            hint = null;
        }
        setString(LsConstants.PASSWORD_HINT_KEY, hint, userId);
    }

    public String getPasswordHint(int userId) {
        return getString(LsConstants.PASSWORD_HINT_KEY, userId);
    }

    public long addFailedFMMUnlockAttempt(int userId) {
        long count = getFailedFMMUnlockAttempt(userId) + 1;
        setLong(LsConstants.FMM_FAIELD_ATTEMPT_KEY, count, userId);
        return count;
    }

    public long getFailedFMMUnlockAttempt(int userId) {
        return getLong(LsConstants.FMM_FAIELD_ATTEMPT_KEY, 0L, userId);
    }

    public void clearFailedFMMUnlockAttempt(int userId) {
        setLong(LsConstants.FMM_FAIELD_ATTEMPT_KEY, 0L, userId);
    }

    public boolean isCarrierLockEnabled(int userId) {
        try {
            return getLockSettings().getCarrierLock(userId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean updateCarrierLock(int userId) {
        try {
            return getLockSettings().updateCarrierLock(userId);
        } catch (RemoteException re) {
            Log.e(TAG, "Unable updateCarrierLock " + re);
            return false;
        }
    }

    public long setCarrierLockoutAttemptDeadline(int userId) {
        long deadline =
                System.currentTimeMillis() + LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT;
        setLong(LsConstants.SKT_LOCKOUT_ATTEMPT_DEADLINE, deadline, userId);
        return deadline;
    }

    public long getCarrierLockoutAttemptDeadline(int userId) {
        long deadline = getLong(LsConstants.SKT_LOCKOUT_ATTEMPT_DEADLINE, 0L, userId);
        long now = System.currentTimeMillis();
        if (deadline <= now) {
            return 0L;
        }
        if (deadline - now > LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT) {
            Log.e(
                    TAG,
                    "getCarrierLockoutAttemptDeadline : Need to adjust deadline "
                            + (deadline - now)
                            + " to "
                            + LsConstants.SKT_LOCKOUT_ATTEMPT_DEFAULT_TIMEOUT);
            return setCarrierLockoutAttemptDeadline(userId);
        }
        return deadline;
    }

    public void saveRemoteLockPassword(int remoteLockType, byte[] password, int userId) {
        switch (remoteLockType) {
            case 0:
                try {
                    getLockSettings().setLockFMMPassword(password, 0);
                    break;
                } catch (RemoteException re) {
                    Log.e(TAG, "Unable to save lock FMM Password " + re);
                }
            case 1:
                try {
                    getLockSettings().setLockCarrierPassword(password, userId);
                    break;
                } catch (RemoteException re2) {
                    Log.e(TAG, "Unable to save lock Carrier Password " + re2);
                    return;
                }
        }
    }

    public boolean checkRemoteLockPassword(int remoteLockType, byte[] password, int userHandle) {
        try {
            if (remoteLockType == 0) {
                byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(password);
                return getLockSettings().checkFMMPassword(sha1, userHandle);
            }
            if (remoteLockType != 1) {
                return true;
            }
            return getLockSettings().checkCarrierPassword(password, userHandle);
        } catch (RemoteException re) {
            Log.e(TAG, "Unable to save lock (" + remoteLockType + ") Password " + re);
            return false;
        } catch (NoSuchAlgorithmException e) {
            Log.w(TAG, "Failed to encode string because of missing algorithm: SHA-1");
            return false;
        }
    }

    public boolean isCarrierPasswordSaved(int userId) {
        try {
            return getLockSettings().haveCarrierPassword(userId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean isFMMLockEnabled(int userId) {
        try {
            return getLockSettings().haveFMMPassword(userId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean isRMMLockEnabled(int userId) {
        return false;
    }

    public boolean isKnoxguardLockEnabled(int userId) {
        return getBoolean("3locked", false, userId);
    }

    private boolean isRemoteLock(int userId) {
        try {
            return getLockSettings().isRemoteLock(userId);
        } catch (RemoteException e) {
            return false;
        }
    }

    public long getExpireTimeForPrev() {
        try {
            return getLockSettings().getExpireTimeForPrev();
        } catch (RemoteException e) {
            Log.e(TAG, "!@getExpireTimeForPrev failed");
            return 0L;
        }
    }

    public boolean expirePreviousData() {
        try {
            getLockSettings().expirePreviousData();
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "!@expirePreviousData failed");
            return false;
        }
    }

    public boolean isSupportWeaver() {
        try {
            return getLockSettings().isSupportWeaver();
        } catch (RemoteException e) {
            Log.e(TAG, "!@isSupportWeaver failed");
            return false;
        }
    }

    public void setSecurityDebugLevel(int level) {
        try {
            getLockSettings().setSecurityDebugLevel(level);
        } catch (RemoteException e) {
            Log.e(TAG, "!@setSecurityDebugLevel set failed");
        }
    }

    public int getBiometricType(int userId) {
        boolean isUPSM =
                Settings.System.getIntForUser(
                                this.mContentResolver,
                                Settings.System.SEM_ULTRA_POWERSAVING_MODE,
                                0,
                                userId)
                        != 0;
        boolean isEMC =
                Settings.System.getIntForUser(
                                this.mContentResolver,
                                Settings.System.SEM_EMERGENCY_MODE,
                                0,
                                userId)
                        != 0;
        if (isUPSM || isEMC) {
            return 0;
        }
        return (int) getLong(LsConstants.BIOMETRIC_LOCKSCREEN_KEY, 0L, userId);
    }

    public int getBiometricState(int biometricType, int userId) {
        if ((getBiometricType(userId) & biometricType) != 0) {
            return 1;
        }
        return 0;
    }

    public void setBiometricState(int biometricType, int state, int userId) {
        int oldValue = getBiometricType(userId);
        int newValue = state == 1 ? oldValue | biometricType : (~biometricType) & oldValue;
        Log.d(
                TAG,
                "setBiometricState ( oldValue = "
                        + Integer.toHexString(oldValue)
                        + " , newValue = "
                        + Integer.toHexString(newValue)
                        + " )");
        setLong(LsConstants.BIOMETRIC_LOCKSCREEN_KEY, newValue, userId);
        reportAuditLog(biometricType, state == 1, userId);
    }

    private void reportAuditLog(int type, boolean enable, int userId) {
        String logMessage = "";
        if (enable) {
            switch (type) {
                case 0:
                    logMessage = AuditEvents.AUDIT_BIOMETRIC_AUTHENTICATION_ENABLED_NONE;
                    break;
                case 1:
                    logMessage = AuditEvents.AUDIT_BIOMETRIC_AUTHENTICATION_ENABLED_FINGERPRINT;
                    break;
                case 256:
                    logMessage = AuditEvents.AUDIT_BIOMETRIC_AUTHENTICATION_ENABLED_FACE;
                    break;
            }
        } else {
            switch (type) {
                case 0:
                    logMessage = AuditEvents.AUDIT_BIOMETRIC_AUTHENTICATION_DISABLED_NONE;
                    break;
                case 1:
                    logMessage = AuditEvents.AUDIT_BIOMETRIC_AUTHENTICATION_DISABLED_FINGERPRINT;
                    break;
                case 256:
                    logMessage = AuditEvents.AUDIT_BIOMETRIC_AUTHENTICATION_DISABLED_FACE;
                    break;
            }
        }
        AuditLog.logAsUser(
                5, 1, true, Process.myPid(), getClass().getSimpleName(), logMessage, userId);
    }

    public long setBiometricAttemptDeadline(int userId, int timeoutMs) {
        long deadline = SystemClock.elapsedRealtime() + timeoutMs;
        setLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, timeoutMs, userId);
        setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, deadline, userId);
        return deadline;
    }

    public long getBiometricAttemptDeadline(int userId) {
        long deadline = getLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, 0L, userId);
        long timeoutMs = getLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, userId);
        long now = SystemClock.elapsedRealtime();
        if (deadline != 0 && deadline < now) {
            setLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, userId);
            setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, 0L, userId);
            return 0L;
        }
        if (timeoutMs != 0 && deadline > now + timeoutMs) {
            long deadline2 = now + timeoutMs;
            setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, deadline2, userId);
            return deadline2;
        }
        return deadline;
    }

    public long getBiometricAttemptTimeout(int userId) {
        return getLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, userId);
    }

    public void clearBiometricAttemptDeadline(int userId) {
        setLong(LsConstants.BIOMETRIC_ATTEMPT_TIMEOUT_MS, 0L, userId);
        setLong(LsConstants.BIOMETRIC_ATTEMPT_DEADLINE, 0L, userId);
    }

    private void clearBiometricAndLockState(int userHandle) {
        try {
            this.mContext.enforceCallingOrSelfPermission(
                    Manifest.permission.ACCESS_KEYGUARD_SECURE_STORAGE, "LockSettingsWrite");
            setBiometricState(257, 0, userHandle);
            clearBiometricAttemptDeadline(userHandle);
            clearLockoutAttemptDeadline(userHandle);
            setPasswordHint(null, userHandle);
            clearFailedFMMUnlockAttempt(userHandle);
            try {
                getLockSettings().setLockFMMPassword(null, 0);
            } catch (RemoteException e) {
                Log.e(TAG, "setLockFMMPassword error = ", e);
            }
        } catch (SecurityException e2) {
            Log.e(TAG, "Failed to clearBiometricAndLockState =", e2);
        }
    }

    public void setFolderInstantlyLocks(boolean enabled, int userId) {
        setBoolean(LsConstants.LOCKSCREEN_FOLDER_INSTANTLY_LOCKS, enabled, userId);
    }

    public boolean getFolderInstantlyLocks(int userId) {
        return getBoolean(LsConstants.LOCKSCREEN_FOLDER_INSTANTLY_LOCKS, true, userId);
    }

    public void setBiometricStrongAuthTimeout(String bioTimeoutKey, long timeoutMs, int userId) {
        setLong(bioTimeoutKey, timeoutMs, userId);
    }

    public long getBiometricStrongAuthTimeout(String bioTimeoutKey, int userId) {
        return getLong(bioTimeoutKey, 0L, userId);
    }

    public boolean isUCMLockEnabled(int userId) {
        return getCredentialTypeForUser(userId) == 6
                && getBiometricState(1, userId) == 0
                && getBiometricState(256, userId) == 0;
    }

    public static boolean isQualitySmartCard(int quality) {
        return quality == 458752;
    }

    private Optional<IDarManagerService> getDarManagerService() {
        if (this.mDarManagerService == null) {
            IDarManagerService service =
                    IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
            this.mDarManagerService = service;
        }
        IDarManagerService service2 = this.mDarManagerService;
        return Optional.ofNullable(service2);
    }

    private boolean isEnterpriseUser(int userId) {
        if (!SemPersonaManager.isKnoxId(userId)) {
            return SemPersonaManager.isDoEnabled(userId);
        }
        if (SemPersonaManager.isSecureFolderId(userId)) {
            return isSdpSupportedSecureFolder(userId);
        }
        return true;
    }

    public void setDeviceOwner(int userId) {
        if (userId != 0) {
            return;
        }
        setLong(KNOX_DEVICE_OWNER_KEY, 1L, userId);
    }

    public boolean isDeviceOwner(int userId) {
        return userId == 0 && getLong(KNOX_DEVICE_OWNER_KEY, 0L, userId) != 0;
    }

    private static /* synthetic */ Boolean lambda$isSdpSupportedSecureFolder$0(
            int userId, IDarManagerService s) {
        try {
            return Boolean.valueOf(s.isSdpSupportedSecureFolder(userId));
        } catch (Exception e) {
            Log.e(TAG, "failed to check sdp support for secure folder", e);
            e.printStackTrace();
            return false;
        }
    }

    private boolean isSdpSupportedSecureFolder(int userId) {
        return false;
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

        WrappedCallbackForDualDar(Handler handler, DualDarAuthProgressCallback callback) {
            this.mHandler = handler;
            this.mCallback = callback;
        }

        @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
        public void onInnerLayerUnlocked() throws RemoteException {
            if (this.mHandler == null) {
                Log.e(LockPatternUtils.TAG, "Handler is null during callback");
            }
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.internal.widget.LockPatternUtils$WrappedCallbackForDualDar$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LockPatternUtils.WrappedCallbackForDualDar.this
                                    .lambda$onInnerLayerUnlocked$0();
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

    private IDualDarAuthProgressCallback wrapCallbackForDualDar(
            DualDarAuthProgressCallback callback) {
        if (callback == null) {
            return null;
        }
        if (this.mHandler == null) {
            throw new IllegalStateException(
                    "Must construct LockPatternUtils on a looper thread to use progress"
                        + " callbacks.");
        }
        return new WrappedCallbackForDualDar(this.mHandler, callback);
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

        public boolean isInnerAuthUserForDo(int userId) {
            return getAuthUtils().isInnerAuthUserForDo(userId);
        }

        protected boolean checkCredential(
                LockscreenCredential credential,
                int userId,
                int option,
                DualDarAuthProgressCallback progressCallback)
                throws RequestThrottledException {
            return LockPatternUtils.this.checkCredentialForDualDarDo(
                    credential, userId, option, progressCallback);
        }

        public int getPasswordMinimumLengthForInner() {
            return getAuthUtils().getPasswordMinimumLengthForInner();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkCredentialForDualDarDo(
            LockscreenCredential credential,
            int userId,
            int option,
            DualDarAuthProgressCallback progressCallback)
            throws RequestThrottledException {
        throwIfCalledOnMainThread();
        try {
            VerifyCredentialResponse response =
                    getLockSettings()
                            .checkCredentialForDualDarDo(
                                    credential,
                                    userId,
                                    option,
                                    wrapCallbackForDualDar(progressCallback));
            if (response.getResponseCode() == 0) {
                return true;
            }
            if (response.getResponseCode() != 1) {
                return false;
            }
            if (response.getTimeout() > 0) {
                reportPasswordThrottleAuditLog(userId);
            }
            throw new RequestThrottledException(response.getTimeout());
        } catch (RemoteException | RuntimeException re) {
            Log.e(TAG, "failed to check dualdar do credential", re);
            return false;
        }
    }
}
