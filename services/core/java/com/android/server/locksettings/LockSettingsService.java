package com.android.server.locksettings;

import android.R;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteLockscreenValidationResult;
import android.app.RemoteLockscreenValidationSession;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.admin.DeviceStateCache;
import android.app.admin.PasswordMetrics;
import android.app.trust.IStrongAuthTracker;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.authsecret.IAuthSecret;
import android.hardware.biometrics.BiometricManager;
import android.hardware.face.Face;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IProgressListener;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.PasswordPolicy;
import android.security.AndroidKeyStoreMaintenance;
import android.security.Authorization;
import android.security.keystore.KeyProtection;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.RecoveryCertPath;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.security.keystore2.AndroidKeyStoreProvider;
import android.service.gatekeeper.IGateKeeperService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.IWindowManager;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.IUpdateVerifierCallback;
import com.android.internal.widget.IUpdateVerifierInterface;
import com.android.internal.widget.IWeakEscrowTokenActivatedListener;
import com.android.internal.widget.IWeakEscrowTokenRemovedListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.RebootEscrowListener;
import com.android.internal.widget.RemoteLockInfo;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.LocalServices;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.enterprise.plm.LockDetectionTracker;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.DarUtil;
import com.android.server.knox.dar.SecureUtil;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.ddar.DualDarDoPolicyChecker;
import com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.knox.dar.sdp.SdpManagerInternal;
import com.android.server.locksettings.LockSettingsService;
import com.android.server.locksettings.LockSettingsStorage;
import com.android.server.locksettings.RebootEscrowManager;
import com.android.server.locksettings.SyntheticPasswordManager;
import com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.utils.Slogf;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.bio.face.SemBioFace;
import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.dar.StreamCipher;
import com.samsung.android.knox.dar.VirtualLockUtils;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.DualDarAuthUtils;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.fsm.StateMachine;
import com.samsung.android.knox.devicesecurity.IPasswordPolicy;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import libcore.util.HexEncoding;

/* loaded from: classes2.dex */
public class LockSettingsService extends ILockSettings.Stub {
    public static final boolean DEBUG_DUMP;
    public static final int[] SYSTEM_CREDENTIAL_UIDS;
    public static final String[] UNPROTECTED_SETTINGS;
    public final IActivityManager mActivityManager;
    protected byte[] mAuthSecret;
    public IAuthSecret mAuthSecretService;
    public final BiometricDeferredQueue mBiometricDeferredQueue;
    public final BroadcastReceiver mBroadcastReceiver;
    public HashMap mCallbacks;
    public final Context mContext;
    public final DarLockSettings mDarLockSettings;
    public DarManagerService mDarManagerService;
    public final DarVirtualLock mDarVirtualLock;
    public final DeviceProvisionedObserver mDeviceProvisionedObserver;
    public final DualDarAuthUtils mDualDarAuthUtils;
    public final DualDarLockSettings mDualDarLockSettings;
    public SparseIntArray mEarlyCreatedUsers;
    public SparseIntArray mEarlyRemovedUsers;
    public int mFirstApiLevel;
    public IGateKeeperService mGateKeeperService;
    public final LongSparseArray mGatekeeperPasswords;
    protected final Handler mHandler;
    protected boolean mHasSecureLockScreen;
    protected final Object mHeadlessAuthSecretLock;
    public final Injector mInjector;
    public final KeyStore mJavaKeyStore;
    public int mLockTypeForPasswordCheck;
    public Consumer mMaintenanceModeCallback;
    public final UserManagerInternal.MaintenanceModeLifecycleListener mMaintenanceModeListener;
    public ManagedProfilePasswordCache mManagedProfilePasswordCache;
    public final NotificationManager mNotificationManager;
    public byte[] mPassword;
    public final SparseArray mPendingVerifiedResults;
    public final RebootEscrowManager mRebootEscrowManager;
    public final RecoverableKeyStoreManager mRecoverableKeyStoreManager;
    public IRemoteCallback mRemoteCallback;
    public final Runnable mResetDebugLevel;
    public final SdpLockSettings mSdpLockSettings;
    public final Object mSeparateChallengeLock;
    public IRemoteCallback mShellCommandCallback;
    public boolean mShouldUnbind;
    public final SyntheticPasswordDump mSpDump;
    public final BroadcastReceiver mSpDumpReceiver;
    public final SyntheticPasswordManager mSpManager;
    protected final LockSettingsStorage mStorage;
    public final IStorageManager mStorageManager;
    public final LockSettingsStrongAuth mStrongAuth;
    public final SynchronizedStrongAuthTracker mStrongAuthTracker;
    public boolean mThirdPartyAppsStarted;
    public final Object mUserCreationAndRemovalLock;
    public final UserManager mUserManager;
    public HashMap mUserManagerCache;
    public final SparseArray mUserPasswordMetrics;

    public final int redactActualQualityToMostLenientEquivalentQuality(int i) {
        int i2 = IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
        if (i != 131072 && i != 196608) {
            i2 = 262144;
            if (i != 262144 && i != 327680 && i != 393216) {
                return i;
            }
        }
        return i2;
    }

    public void setCarrierLockEnabled(int i) {
    }

    public void updateSdpMdfppForSystem(int i, long j) {
    }

    static {
        DEBUG_DUMP = "userdebug".equals(SystemProperties.get("ro.build.type")) || "eng".equals(SystemProperties.get("ro.build.type"));
        SYSTEM_CREDENTIAL_UIDS = new int[]{1016, 0, 1000};
        UNPROTECTED_SETTINGS = new String[]{"lock_pattern_autolock", "lock_pattern_visible_pattern", "lock_pattern_tactile_feedback_enabled"};
    }

    public final class Lifecycle extends SystemService {
        public LockSettingsService mLockSettingsService;

        public Lifecycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.locksettings.LockSettingsService] */
        @Override // com.android.server.SystemService
        public void onStart() {
            AndroidKeyStoreProvider.install();
            ?? lockSettingsService = new LockSettingsService(getContext());
            this.mLockSettingsService = lockSettingsService;
            publishBinderService("lock_settings", lockSettingsService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            super.onBootPhase(i);
            if (i == 550) {
                this.mLockSettingsService.migrateOldDataAfterSystemReady();
                this.mLockSettingsService.loadEscrowData();
            }
            if (i == 520) {
                onBootPhaseForEnterprise();
                if (SemPersonaManager.getSecureFolderId(getContext()) > 0) {
                    onBootPhaseForSecureFolder();
                }
            }
        }

        public final void onBootPhaseForEnterprise() {
            this.mLockSettingsService.checkWeaverStatus();
            this.mLockSettingsService.refreshWeaverSlots();
            if (this.mLockSettingsService.mInjector.isDualDAREnabled()) {
                this.mLockSettingsService.getDarManagerService().ifPresent(new Consumer() { // from class: com.android.server.locksettings.LockSettingsService$Lifecycle$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DarManagerService) obj).isDarSupported();
                    }
                });
            }
        }

        public final void onBootPhaseForSecureFolder() {
            this.mLockSettingsService.getDarManagerService().ifPresent(new Consumer() { // from class: com.android.server.locksettings.LockSettingsService$Lifecycle$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DarManagerService) obj).registerUserSwitchObserver();
                }
            });
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(SystemService.TargetUser targetUser) {
            this.mLockSettingsService.onStartUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(SystemService.TargetUser targetUser) {
            this.mLockSettingsService.onUnlockUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(SystemService.TargetUser targetUser) {
            this.mLockSettingsService.onCleanupUser(targetUser.getUserIdentifier());
        }
    }

    public class SynchronizedStrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public SynchronizedStrongAuthTracker(Context context) {
            super(context);
        }

        public void handleStrongAuthRequiredChanged(int i, int i2) {
            synchronized (this) {
                super.handleStrongAuthRequiredChanged(i, i2);
            }
        }

        public int getStrongAuthForUser(int i) {
            int strongAuthForUser;
            synchronized (this) {
                strongAuthForUser = super.getStrongAuthForUser(i);
            }
            return strongAuthForUser;
        }

        public void register(LockSettingsStrongAuth lockSettingsStrongAuth) {
            lockSettingsStrongAuth.registerStrongAuthTracker(getStub());
        }
    }

    public final LockscreenCredential generateRandomProfilePassword() {
        byte[] randomBytes = SecureRandomUtils.randomBytes(40);
        char[] encode = HexEncoding.encode(randomBytes);
        byte[] bArr = new byte[encode.length];
        for (int i = 0; i < encode.length; i++) {
            bArr[i] = (byte) encode[i];
        }
        LockscreenCredential createManagedPassword = LockscreenCredential.createManagedPassword(bArr);
        Arrays.fill(encode, (char) 0);
        Arrays.fill(bArr, (byte) 0);
        Arrays.fill(randomBytes, (byte) 0);
        return createManagedPassword;
    }

    public final void tieProfileLockIfNecessary(int i, LockscreenCredential lockscreenCredential) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        SDPLog.m43i("Tie lock if necessary for user " + i);
        SDPLog.m46p("profileUserId", Integer.valueOf(i), "profileUserPassword", lockscreenCredential);
        if (!isCredentialSharableWithParent(i) || getSeparateProfileChallengeEnabledInternal(i) || this.mStorage.hasChildProfileLock(i) || this.mDualDarLockSettings.isDualDARUser(i)) {
            return;
        }
        int i2 = this.mUserManager.getProfileParent(i).id;
        if (!isUserSecure(i2) && !lockscreenCredential.isNone()) {
            Slogf.m96i("LockSettingsService", "Clearing password for profile user %d to match parent", Integer.valueOf(i));
            setLockCredentialInternal(LockscreenCredential.createNone(), lockscreenCredential, i, true);
            return;
        }
        try {
            long secureUserId = getGateKeeperService().getSecureUserId(i2);
            if (secureUserId == 0) {
                return;
            }
            LockscreenCredential generateRandomProfilePassword = generateRandomProfilePassword();
            try {
                setLockCredentialInternal(generateRandomProfilePassword, lockscreenCredential, i, true);
                tieProfileLockToParent(i, i2, generateRandomProfilePassword);
                this.mManagedProfilePasswordCache.storePassword(i, generateRandomProfilePassword, secureUserId);
                if (isProfileWithUnifiedLock(i)) {
                    doVerifyCredential(generateRandomProfilePassword, i, null, 0);
                }
                if (generateRandomProfilePassword != null) {
                    generateRandomProfilePassword.close();
                }
            } catch (Throwable th) {
                if (generateRandomProfilePassword != null) {
                    try {
                        generateRandomProfilePassword.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException e) {
            Slog.e("LockSettingsService", "Failed to talk to GateKeeper service", e);
        }
    }

    public class Injector {
        public Context mContext;
        public Handler mHandler;
        public ServiceThread mHandlerThread;
        public PersonaManagerService mPersonaManagerService;

        public Injector(Context context) {
            this.mContext = context;
        }

        public Context getContext() {
            return this.mContext;
        }

        public ServiceThread getServiceThread() {
            if (this.mHandlerThread == null) {
                ServiceThread serviceThread = new ServiceThread("LockSettingsService", 10, true);
                this.mHandlerThread = serviceThread;
                serviceThread.start();
            }
            return this.mHandlerThread;
        }

        public Handler getHandler(ServiceThread serviceThread) {
            if (this.mHandler == null) {
                this.mHandler = new Handler(serviceThread.getLooper());
            }
            return this.mHandler;
        }

        public LockSettingsStorage getStorage() {
            final LockSettingsStorage lockSettingsStorage = new LockSettingsStorage(this.mContext);
            lockSettingsStorage.setDatabaseOnCreateCallback(new LockSettingsStorage.Callback() { // from class: com.android.server.locksettings.LockSettingsService.Injector.1
                @Override // com.android.server.locksettings.LockSettingsStorage.Callback
                public void initialize(SQLiteDatabase sQLiteDatabase) {
                    if (SystemProperties.getBoolean("ro.lockscreen.disable.default", false)) {
                        lockSettingsStorage.writeKeyValue(sQLiteDatabase, "lockscreen.disabled", "1", 0);
                    }
                }
            });
            return lockSettingsStorage;
        }

        public LockSettingsStrongAuth getStrongAuth() {
            return new LockSettingsStrongAuth(this.mContext);
        }

        public SynchronizedStrongAuthTracker getStrongAuthTracker() {
            return new SynchronizedStrongAuthTracker(this.mContext);
        }

        public IActivityManager getActivityManager() {
            return ActivityManager.getService();
        }

        public NotificationManager getNotificationManager() {
            return (NotificationManager) this.mContext.getSystemService("notification");
        }

        public UserManager getUserManager() {
            return (UserManager) this.mContext.getSystemService("user");
        }

        public UserManagerInternal getUserManagerInternal() {
            return (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }

        public DevicePolicyManager getDevicePolicyManager() {
            return (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }

        public IEnterpriseDeviceManager getEdmService() {
            return IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        }

        public DeviceStateCache getDeviceStateCache() {
            return DeviceStateCache.getInstance();
        }

        public RecoverableKeyStoreManager getRecoverableKeyStoreManager() {
            return RecoverableKeyStoreManager.getInstance(this.mContext);
        }

        public IStorageManager getStorageManager() {
            IBinder service = ServiceManager.getService("mount");
            if (service != null) {
                return IStorageManager.Stub.asInterface(service);
            }
            return null;
        }

        public SyntheticPasswordManager getSyntheticPasswordManager(LockSettingsStorage lockSettingsStorage) {
            return new SyntheticPasswordManager(getContext(), lockSettingsStorage, getUserManager(), new PasswordSlotManager());
        }

        public RebootEscrowManager getRebootEscrowManager(RebootEscrowManager.Callbacks callbacks, LockSettingsStorage lockSettingsStorage) {
            return new RebootEscrowManager(this.mContext, callbacks, lockSettingsStorage, getHandler(getServiceThread()));
        }

        public boolean isGsiRunning() {
            return SystemProperties.getInt("ro.gsid.image_running", 0) > 0;
        }

        public FingerprintManager getFingerprintManager() {
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
                return (FingerprintManager) this.mContext.getSystemService("fingerprint");
            }
            return null;
        }

        public FaceManager getFaceManager() {
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
                return (FaceManager) this.mContext.getSystemService("face");
            }
            return null;
        }

        public BiometricManager getBiometricManager() {
            return (BiometricManager) this.mContext.getSystemService("biometric");
        }

        public KeyStore getJavaKeyStore() {
            try {
                KeyStore keyStore = KeyStore.getInstance(SyntheticPasswordCrypto.androidKeystoreProviderName());
                keyStore.load(new AndroidKeyStoreLoadStoreParameter(SyntheticPasswordCrypto.keyNamespace()));
                return keyStore;
            } catch (Exception e) {
                throw new IllegalStateException("Cannot load keystore", e);
            }
        }

        public ManagedProfilePasswordCache getManagedProfilePasswordCache(KeyStore keyStore) {
            return new ManagedProfilePasswordCache(keyStore);
        }

        public boolean isHeadlessSystemUserMode() {
            return UserManager.isHeadlessSystemUserMode();
        }

        public boolean isMainUserPermanentAdmin() {
            return Resources.getSystem().getBoolean(R.bool.config_focusScrollContainersInTouchMode);
        }

        public Optional getPersonaService() {
            if (this.mPersonaManagerService == null) {
                this.mPersonaManagerService = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
            }
            return Optional.ofNullable(this.mPersonaManagerService);
        }

        public DarManagerService getDarManagerService() {
            return (DarManagerService) ServiceManager.getService("dar");
        }

        public boolean isDualDAREnabled() {
            return PersonaServiceHelper.isDualDAREnabled();
        }
    }

    public LockSettingsService(Context context) {
        this(new Injector(context));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LockSettingsService(Injector injector) {
        this.mSeparateChallengeLock = new Object();
        this.mDeviceProvisionedObserver = new DeviceProvisionedObserver();
        this.mUserCreationAndRemovalLock = new Object();
        this.mEarlyCreatedUsers = new SparseIntArray();
        this.mEarlyRemovedUsers = new SparseIntArray();
        this.mUserPasswordMetrics = new SparseArray();
        this.mHeadlessAuthSecretLock = new Object();
        this.mUserManagerCache = new HashMap();
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        this.mMaintenanceModeCallback = null;
        this.mMaintenanceModeListener = new UserManagerInternal.MaintenanceModeLifecycleListener() { // from class: com.android.server.locksettings.LockSettingsService.1
            @Override // com.android.server.pm.UserManagerInternal.MaintenanceModeLifecycleListener
            public void onPostprocessing(Consumer consumer) {
                Slog.i("LockSettingsService", "removeUser for MaintenanceMode");
                LockSettingsService.this.mMaintenanceModeCallback = consumer;
                LockSettingsService.this.removeUserState(77);
            }
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.locksettings.LockSettingsService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.USER_ADDED".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    AndroidKeyStoreMaintenance.onUserAdded(intExtra);
                    if (SemPersonaManager.isDarDualEncryptionEnabled(intExtra)) {
                        Slog.d("LockSettingsService", "OnUserAdded(): Setup SeparateCredential for DualDAR User : " + intExtra);
                        LockSettingsService.this.setSeparateProfileChallengeEnabled(intExtra, true, null);
                        Slog.d("LockSettingsService", "OnUserAdded(): Successfully added OLK to vold for DualDAR User : " + intExtra);
                        return;
                    }
                    return;
                }
                if ("android.intent.action.USER_STARTING".equals(intent.getAction())) {
                    LockSettingsService.this.mStorage.prefetchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                    LockSettingsService.this.updateActivatedEncryptionNotifications("locale changed");
                    return;
                }
                if (DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED.equals(intent.getAction())) {
                    int identifier = ((UserHandle) intent.getExtra("android.intent.extra.USER")).getIdentifier();
                    if (SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
                        Slog.d("LockSettingsService", "reportEnabledTrustAgentsChanged for DualDAR User : " + identifier);
                        ((TrustManager) LockSettingsService.this.mContext.getSystemService(TrustManager.class)).reportEnabledTrustAgentsChanged(identifier);
                    }
                }
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.server.locksettings.LockSettingsService.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.DUMP_SYNTHETIC_PASSWORD".equals(intent.getAction())) {
                    Log.d("LockSettingsService.SDP", "onReceive :: android.intent.action.DUMP_SYNTHETIC_PASSWORD");
                    LockSettingsService.this.mSpDump.dump(intent.getStringExtra("cred"), intent.getStringExtra(KnoxCustomManagerService.SPCM_KEY_TOKEN), intent.getIntExtra("urid", -1));
                    return;
                }
                if ("android.intent.action.DUMP_DUALDAR_PASSWORD".equals(intent.getAction())) {
                    DDLog.m33d("LockSettingsService.DUALDAR", "onReceive :: android.intent.action.DUMP_DUALDAR_PASSWORD", new Object[0]);
                    DualDARDaemonProxy.getInstance(LockSettingsService.this.mContext).dumpSecret(intent.getIntExtra("urid", -1), intent.getStringExtra("path"));
                    return;
                }
                if ("android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES".equals(intent.getAction())) {
                    DDLog.m33d("LockSettingsService.DUALDAR", "onReceive :: android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES", new Object[0]);
                    DualDarDoPolicyChecker.getInstance(LockSettingsService.this.mContext).checkDualDarDoPolicy(intent.getIntExtra("urid", -1));
                }
            }
        };
        this.mSpDumpReceiver = broadcastReceiver2;
        this.mPendingVerifiedResults = new SparseArray();
        this.mCallbacks = new HashMap();
        this.mResetDebugLevel = new Runnable() { // from class: com.android.server.locksettings.LockSettingsService.9
            @Override // java.lang.Runnable
            public void run() {
                LockSettingsService.this.setSecurityDebugLevel(0);
                Slog.d("LockSettingsService", "!@ Reset SecurityDebugLevel = low");
            }
        };
        this.mInjector = injector;
        Context context = injector.getContext();
        this.mContext = context;
        KeyStore javaKeyStore = injector.getJavaKeyStore();
        this.mJavaKeyStore = javaKeyStore;
        this.mRecoverableKeyStoreManager = injector.getRecoverableKeyStoreManager();
        Handler handler = injector.getHandler(injector.getServiceThread());
        this.mHandler = handler;
        LockSettingsStrongAuth strongAuth = injector.getStrongAuth();
        this.mStrongAuth = strongAuth;
        this.mActivityManager = injector.getActivityManager();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_STARTING");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
        injector.getContext().registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
        LockSettingsStorage storage = injector.getStorage();
        this.mStorage = storage;
        this.mNotificationManager = injector.getNotificationManager();
        this.mUserManager = injector.getUserManager();
        this.mStorageManager = injector.getStorageManager();
        SynchronizedStrongAuthTracker strongAuthTracker = injector.getStrongAuthTracker();
        this.mStrongAuthTracker = strongAuthTracker;
        strongAuthTracker.register(strongAuth);
        this.mGatekeeperPasswords = new LongSparseArray();
        SyntheticPasswordManager syntheticPasswordManager = injector.getSyntheticPasswordManager(storage);
        this.mSpManager = syntheticPasswordManager;
        this.mManagedProfilePasswordCache = injector.getManagedProfilePasswordCache(javaKeyStore);
        this.mBiometricDeferredQueue = new BiometricDeferredQueue(syntheticPasswordManager, handler);
        this.mRebootEscrowManager = injector.getRebootEscrowManager(new RebootEscrowCallbacks(), storage);
        if (DEBUG_DUMP) {
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.DUMP_SYNTHETIC_PASSWORD");
            intentFilter2.addAction("android.intent.action.DUMP_DUALDAR_PASSWORD");
            intentFilter2.addAction("android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES");
            injector.getContext().registerReceiver(broadcastReceiver2, intentFilter2);
        }
        this.mSpDump = new SyntheticPasswordDump(context, syntheticPasswordManager, this);
        this.mDarLockSettings = new DarLockSettings(this);
        this.mSdpLockSettings = new SdpLockSettings(this);
        this.mDualDarLockSettings = new DualDarLockSettings(this);
        this.mDarVirtualLock = new DarVirtualLock();
        this.mDualDarAuthUtils = new DualDarAuthUtils(context);
        this.mFirstApiLevel = SystemProperties.getInt("ro.product.first_api_level", 0);
        LocalServices.addService(LockSettingsInternal.class, new LocalService());
    }

    public final boolean isKnoxAdminActivated(int i) {
        IEnterpriseDeviceManager edmService = this.mInjector.getEdmService();
        if (edmService == null) {
            return false;
        }
        try {
            return edmService.isMdmAdminPresentAsUser(i);
        } catch (RemoteException e) {
            Log.w("LockSettingsService", "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public final void updateActivatedEncryptionNotifications(String str) {
        for (UserInfo userInfo : this.mUserManager.getUsers()) {
            int i = 0;
            StatusBarNotification[] activeNotifications = ((NotificationManager) this.mContext.createContextAsUser(UserHandle.of(userInfo.id), 0).getSystemService("notification")).getActiveNotifications();
            int length = activeNotifications.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (activeNotifications[i].getId() == 9) {
                    maybeShowEncryptionNotificationForUser(userInfo.id, str);
                    break;
                }
                i++;
            }
        }
    }

    public final void maybeShowEncryptionNotificationForUser(int i, String str) {
        UserInfo profileParent;
        UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (userInfo.isManagedProfile() && !isUserKeyUnlocked(i)) {
            UserHandle userHandle = userInfo.getUserHandle();
            if (!isUserSecure(i) || this.mUserManager.isUserUnlockingOrUnlocked(userHandle) || (profileParent = this.mUserManager.getProfileParent(i)) == null || !this.mUserManager.isUserUnlockingOrUnlocked(profileParent.getUserHandle()) || this.mUserManager.isQuietModeEnabled(userHandle)) {
                return;
            }
            showEncryptionNotificationForProfile(userHandle, str);
        }
    }

    public final void showEncryptionNotificationForProfile(UserHandle userHandle, String str) {
        if (SemDualAppManager.isDualAppId(userHandle.getIdentifier())) {
            return;
        }
        if (SemPersonaManager.isSecureFolderId(userHandle.getIdentifier()) && Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "automatic_data_decryption", 0, userHandle.getIdentifier()) == 1) {
            return;
        }
        String knoxEncryptionNotificationTitle = getKnoxEncryptionNotificationTitle(userHandle.getIdentifier());
        String knoxEncryptionNotificationMessage = getKnoxEncryptionNotificationMessage(userHandle.getIdentifier());
        String knoxEncryptionNotificationDetail = getKnoxEncryptionNotificationDetail(userHandle.getIdentifier());
        Intent createConfirmDeviceCredentialIntent = ((KeyguardManager) this.mContext.getSystemService("keyguard")).createConfirmDeviceCredentialIntent(null, null, userHandle.getIdentifier());
        if (createConfirmDeviceCredentialIntent != null && StorageManager.isFileEncrypted()) {
            createConfirmDeviceCredentialIntent.setFlags(276824064);
            PendingIntent activity = PendingIntent.getActivity(this.mContext, userHandle.getIdentifier(), createConfirmDeviceCredentialIntent, 167772160);
            Slogf.m88d("LockSettingsService", "Showing encryption notification for user %d; reason: %s", Integer.valueOf(userHandle.getIdentifier()), str);
            showEncryptionNotification(userHandle, knoxEncryptionNotificationTitle, knoxEncryptionNotificationMessage, knoxEncryptionNotificationDetail, activity);
        }
    }

    public final String getKnoxEncryptionNotificationTitle(final int i) {
        if (SemPersonaManager.isSecureFolderId(i)) {
            return this.mContext.getResources().getString(17042346);
        }
        if (((Boolean) this.mInjector.getPersonaService().map(new Function() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$getKnoxEncryptionNotificationTitle$1;
                lambda$getKnoxEncryptionNotificationTitle$1 = LockSettingsService.lambda$getKnoxEncryptionNotificationTitle$1(i, (PersonaManagerService) obj);
                return lambda$getKnoxEncryptionNotificationTitle$1;
            }
        }).orElse(Boolean.TRUE)).booleanValue()) {
            String str = SystemProperties.get("ro.build.characteristics");
            if (str != null && str.contains("tablet")) {
                return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_TITLE", new Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda12
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        String lambda$getKnoxEncryptionNotificationTitle$2;
                        lambda$getKnoxEncryptionNotificationTitle$2 = LockSettingsService.this.lambda$getKnoxEncryptionNotificationTitle$2();
                        return lambda$getKnoxEncryptionNotificationTitle$2;
                    }
                });
            }
            return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_TITLE", new Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getKnoxEncryptionNotificationTitle$3;
                    lambda$getKnoxEncryptionNotificationTitle$3 = LockSettingsService.this.lambda$getKnoxEncryptionNotificationTitle$3();
                    return lambda$getKnoxEncryptionNotificationTitle$3;
                }
            });
        }
        return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_TITLE", new Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda14
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getKnoxEncryptionNotificationTitle$4;
                lambda$getKnoxEncryptionNotificationTitle$4 = LockSettingsService.this.lambda$getKnoxEncryptionNotificationTitle$4();
                return lambda$getKnoxEncryptionNotificationTitle$4;
            }
        });
    }

    public static /* synthetic */ Boolean lambda$getKnoxEncryptionNotificationTitle$1(int i, PersonaManagerService personaManagerService) {
        return Boolean.valueOf(!personaManagerService.getPersonaUserHasBeenShutdownBefore(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getKnoxEncryptionNotificationTitle$2() {
        return this.mContext.getString(17043210);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getKnoxEncryptionNotificationTitle$3() {
        return this.mContext.getString(17043209);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getKnoxEncryptionNotificationTitle$4() {
        return this.mContext.getString(17042350);
    }

    public final String getKnoxEncryptionNotificationDetail(final int i) {
        if (!SemPersonaManager.isSecureFolderId(i) && ((Boolean) this.mInjector.getPersonaService().map(new Function() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$getKnoxEncryptionNotificationDetail$6;
                lambda$getKnoxEncryptionNotificationDetail$6 = LockSettingsService.lambda$getKnoxEncryptionNotificationDetail$6(i, (PersonaManagerService) obj);
                return lambda$getKnoxEncryptionNotificationDetail$6;
            }
        }).orElse(Boolean.TRUE)).booleanValue()) {
            return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_DETAIL", new Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getKnoxEncryptionNotificationDetail$7;
                    lambda$getKnoxEncryptionNotificationDetail$7 = LockSettingsService.this.lambda$getKnoxEncryptionNotificationDetail$7();
                    return lambda$getKnoxEncryptionNotificationDetail$7;
                }
            });
        }
        return null;
    }

    public static /* synthetic */ Boolean lambda$getKnoxEncryptionNotificationDetail$6(int i, PersonaManagerService personaManagerService) {
        return Boolean.valueOf(!personaManagerService.getPersonaUserHasBeenShutdownBefore(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getKnoxEncryptionNotificationDetail$7() {
        return this.mContext.getString(17042340);
    }

    public final String getKnoxEncryptionNotificationMessage(final int i) {
        if (SemPersonaManager.isSecureFolderId(i)) {
            return this.mContext.getResources().getString(17042342);
        }
        if (((Boolean) this.mInjector.getPersonaService().map(new Function() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$getKnoxEncryptionNotificationMessage$9;
                lambda$getKnoxEncryptionNotificationMessage$9 = LockSettingsService.lambda$getKnoxEncryptionNotificationMessage$9(i, (PersonaManagerService) obj);
                return lambda$getKnoxEncryptionNotificationMessage$9;
            }
        }).orElse(Boolean.TRUE)).booleanValue()) {
            return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_MESSAGE", new Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getKnoxEncryptionNotificationMessage$10;
                    lambda$getKnoxEncryptionNotificationMessage$10 = LockSettingsService.this.lambda$getKnoxEncryptionNotificationMessage$10();
                    return lambda$getKnoxEncryptionNotificationMessage$10;
                }
            });
        }
        return this.mInjector.getDevicePolicyManager().getResources().getString("Core.PROFILE_ENCRYPTED_MESSAGE", new Supplier() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getKnoxEncryptionNotificationMessage$11;
                lambda$getKnoxEncryptionNotificationMessage$11 = LockSettingsService.this.lambda$getKnoxEncryptionNotificationMessage$11();
                return lambda$getKnoxEncryptionNotificationMessage$11;
            }
        });
    }

    public static /* synthetic */ Boolean lambda$getKnoxEncryptionNotificationMessage$9(int i, PersonaManagerService personaManagerService) {
        return Boolean.valueOf(!personaManagerService.getPersonaUserHasBeenShutdownBefore(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getKnoxEncryptionNotificationMessage$10() {
        return this.mContext.getString(17042344);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getKnoxEncryptionNotificationMessage$11() {
        return this.mContext.getString(17042348);
    }

    public final void showEncryptionNotification(UserHandle userHandle, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, PendingIntent pendingIntent) {
        if (SemPersonaManager.isSecureFolderId(userHandle.getIdentifier())) {
            this.mNotificationManager.notifyAsUser(null, 9, new Notification.Builder(this.mContext, SystemNotificationChannels.ALERTS).setSmallIcon(R.drawable.ic_test_badge_experiment).setWhen(0L).setOngoing(true).setTicker(charSequence).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence3).setVisibility(1).setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(charSequence2)).build(), userHandle);
        } else {
            this.mNotificationManager.notifyAsUser(null, 9, new Notification.Builder(this.mContext, SystemNotificationChannels.DEVICE_ADMIN).setSmallIcon(R.drawable.ic_test_badge_experiment).setWhen(0L).setOngoing(true).setTicker(charSequence).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence3).setVisibility(1).setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(charSequence2)).build(), userHandle);
        }
    }

    public final void hideEncryptionNotification(UserHandle userHandle) {
        Slogf.m88d("LockSettingsService", "Hiding encryption notification for user %d", Integer.valueOf(userHandle.getIdentifier()));
        this.mNotificationManager.cancelAsUser(null, 9, userHandle);
    }

    public void onCleanupUser(int i) {
        hideEncryptionNotification(new UserHandle(i));
        requireStrongAuth(LockPatternUtils.StrongAuthTracker.getDefaultFlags(this.mContext), i);
        synchronized (this) {
            this.mUserPasswordMetrics.remove(i);
        }
    }

    public final void onStartUser(int i) {
        maybeShowEncryptionNotificationForUser(i, "user started");
    }

    public final void removeStateForReusedUserIdIfNecessary(int i, int i2) {
        int i3;
        if (i == 0 || (i3 = this.mStorage.getInt("serial-number", -1, i)) == i2) {
            return;
        }
        if (i3 != -1) {
            Slogf.m96i("LockSettingsService", "Removing stale state for reused userId %d (serial %d => %d)", Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(i2));
            removeUserState(i);
        }
        this.mStorage.setInt("serial-number", i2, i);
    }

    public final void addListenerForMaintenanceMode(int i) {
        if (i != 77) {
            Slog.i("LockSettingsService", "Not MaintenanceMode");
        } else {
            this.mInjector.getUserManagerInternal().addMaintenanceModeLifecycleListener(this.mMaintenanceModeListener);
            Slog.i("LockSettingsService", "addListener for MaintenanceMode");
        }
    }

    public final void onUnlockUser(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService.2
            @Override // java.lang.Runnable
            public void run() {
                LockSettingsService.this.hideEncryptionNotification(new UserHandle(i));
                if (LockSettingsService.this.isCredentialSharableWithParent(i)) {
                    LockSettingsService.this.tieProfileLockIfNecessary(i, LockscreenCredential.createNone());
                }
                LockSettingsService.this.addListenerForMaintenanceMode(i);
                LockSettingsService.this.unlockSdpOrSecureFolder(i);
            }
        });
    }

    public final void unlockSdpOrSecureFolder(int i) {
        if (this.mDualDarLockSettings.isDualDARUser(i)) {
            Log.d("LockSettingsService.SDP", "Unlocking user - Dualdar user " + i);
            return;
        }
        if (i == 0 && StorageManager.isFileEncryptedNativeOrEmulated() && !isUserSecure(i)) {
            for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
                if (userInfo.isSecureFolder()) {
                    UnlockSecureFolderIfAutoDataDecryption(userInfo);
                }
            }
        }
    }

    public final void UnlockSecureFolderIfAutoDataDecryption(UserInfo userInfo) {
        if (userInfo.isSecureFolder()) {
            int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "automatic_data_decryption", 0, userInfo.id);
            if (!this.mUserManager.isUserRunning(userInfo.id)) {
                SDPLog.m38d("Secure folder user " + userInfo + " is not running yet when on unlock system user");
                return;
            }
            if (this.mUserManager.isUserUnlockingOrUnlocked(userInfo.id)) {
                return;
            }
            if (!isUserSecure(userInfo.id) || intForUser == 1) {
                SDPLog.m38d("Unlock secure folder user " + userInfo.id);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        if (this.mInjector.getDarManagerService() != null) {
                            this.mInjector.getDarManagerService().unlockSecureFolderWithToken(userInfo.id);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public void systemReady() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") != 0) {
            EventLog.writeEvent(1397638484, "28251513", Integer.valueOf(ILockSettings.Stub.getCallingUid()), "");
        }
        checkWritePermission();
        this.mHasSecureLockScreen = this.mContext.getPackageManager().hasSystemFeature("android.software.secure_lock_screen");
        migrateOldData();
        getGateKeeperService();
        getAuthSecretHal();
        this.mDeviceProvisionedObserver.onSystemReady();
        LockPatternUtils.invalidateCredentialTypeCache();
        this.mStorage.prefetchUser(0);
        this.mBiometricDeferredQueue.systemReady(this.mInjector.getFingerprintManager(), this.mInjector.getFaceManager(), this.mInjector.getBiometricManager());
        migratePasswordDataForKnox();
    }

    public final void loadEscrowData() {
        this.mRebootEscrowManager.loadRebootEscrowDataIfAvailable(this.mHandler);
    }

    public final void getAuthSecretHal() {
        IAuthSecret asInterface = IAuthSecret.Stub.asInterface(ServiceManager.waitForDeclaredService(IAuthSecret.DESCRIPTOR + "/default"));
        this.mAuthSecretService = asInterface;
        if (asInterface != null) {
            Slog.i("LockSettingsService", "Device implements AIDL AuthSecret HAL");
            return;
        }
        try {
            this.mAuthSecretService = new AuthSecretHidlAdapter(android.hardware.authsecret.V1_0.IAuthSecret.getService(true));
            Slog.i("LockSettingsService", "Device implements HIDL AuthSecret HAL");
        } catch (RemoteException e) {
            Slog.w("LockSettingsService", "Failed to get AuthSecret HAL(hidl)", e);
        } catch (NoSuchElementException unused) {
            Slog.i("LockSettingsService", "Device doesn't implement AuthSecret HAL");
        }
    }

    public final void migrateOldData() {
        boolean migrateKeyNamespace;
        if (getString("migrated_keystore_namespace", null, 0) == null) {
            synchronized (this.mSpManager) {
                migrateKeyNamespace = this.mSpManager.migrateKeyNamespace() & true;
            }
            if (migrateProfileLockKeys() & migrateKeyNamespace) {
                setString("migrated_keystore_namespace", "true", 0);
                Slog.i("LockSettingsService", "Migrated keys to LSS namespace");
            } else {
                Slog.w("LockSettingsService", "Failed to migrate keys to LSS namespace");
            }
        }
    }

    public final void migratePasswordDataForKnox() {
        if (SystemProperties.getInt("ro.product.first_api_level", 0) >= 34 || !needMdfppPwdDataMigration()) {
            return;
        }
        List users = this.mUserManager.getUsers();
        for (int i = 0; i < users.size(); i++) {
            migrateMdfppPwdData(((UserInfo) users.get(i)).id);
        }
        for (int i2 : getVirtualUsers()) {
            migrateMdfppPwdData(i2);
        }
        setBoolean("migrated_mdfpp_pwd_data", true, 0);
    }

    public final boolean needMdfppPwdDataMigration() {
        boolean z = getBoolean("migrated_mdfpp_pwd_data", false, 0);
        StringBuilder sb = new StringBuilder();
        sb.append("Need pwdData migration ? ");
        sb.append(z ? "No" : "YES");
        Log.d("LockSettingsService", sb.toString());
        return !z;
    }

    public final void migrateMdfppPwdData(int i) {
        synchronized (this.mSpManager) {
            if (isSyntheticPasswordBasedCredentialLocked(i)) {
                this.mSpManager.migratePwdDataForKnox(getCurrentLskfBasedProtectorId(i), i);
            }
        }
    }

    public final int[] getVirtualUsers() {
        return new VirtualLockUtils().getVirtualUsers();
    }

    public void migrateOldDataAfterSystemReady() {
        if (LockPatternUtils.frpCredentialEnabled(this.mContext) && !getBoolean("migrated_frp2", false, 0)) {
            migrateFrpCredential();
            setBoolean("migrated_frp2", true, 0);
        }
        migrateSecurityLog();
    }

    public final void migrateSecurityLog() {
        this.mStorage.migrateLssLog();
        String string = getString("lockscreen.pwdata.ver", "", 0);
        String str = LockSettingsServiceLog.SECURITY_LOG_VERSION;
        if (!str.equals(string)) {
            if (TextUtils.isEmpty(string)) {
                string = "empty";
            }
            String str2 = "SecurityLog ver updated! " + string + " -> " + str;
            this.mStorage.addLog(0, str2);
            List users = this.mUserManager.getUsers();
            int size = users.size();
            int i = 0;
            while (i < size) {
                int i2 = ((UserInfo) users.get(i)).id;
                String str3 = ("User " + i2 + " list updated!\n") + this.mStorage.getPWFilelist(i2);
                setString("lockscreen.token", "", i2);
                i++;
                str2 = str3;
            }
            setString("lockscreen.pwdata.ver", LockSettingsServiceLog.SECURITY_LOG_VERSION, 0);
            this.mStorage.addLog(0, str2);
        }
        try {
            if (IWindowManager.Stub.asInterface(ServiceManager.getService("window")).isSafeModeEnabled()) {
                Log.d("LockSettingsService", "!@safe mode on");
                setSecurityDebugLevel(1);
            } else {
                Log.d("LockSettingsService", "!@safe mode off");
            }
        } catch (Exception e) {
            Log.e("LockSettingsService", "SAFEMODE Exception occurs! " + e.getMessage());
        }
    }

    public final void showLockState() {
        if (this.mStorage.getSecurityDebugLevel() >= 1) {
            Slog.i("LockSettingsService", "!@ User 0 lock settings state:");
            Slog.i("LockSettingsService", "!@ " + String.format("SP Handle: %x", Long.valueOf(getCurrentLskfBasedProtectorId(0))));
            Slog.i("LockSettingsService", "!@ " + String.format("Last changed: %s (%x)", timestampToString(getLong("sp-handle-ts", 0L, 0)), Long.valueOf(getLong("prev-sp-handle", 0L, 0))));
            Slog.i("LockSettingsService", "!@ CredentialType: " + LockPatternUtils.credentialTypeToString(getCredentialTypeInternal(0)));
            try {
                Slog.i("LockSettingsService", "!@ FMM is " + haveFMMPassword(0) + ", CarrierLock is " + getCarrierLock(0));
            } catch (RemoteException e) {
                Log.e("LockSettingsService", "RemoteException occurs! " + e.getMessage());
            }
            Slog.i("LockSettingsService", "!@ failed attempt = " + getCurrentFailedPasswordAttempts(0));
        }
    }

    public final void migrateFrpCredential() {
        LockSettingsStorage.PersistentData readPersistentDataBlock = this.mStorage.readPersistentDataBlock();
        if (readPersistentDataBlock == LockSettingsStorage.PersistentData.NONE || readPersistentDataBlock.isBadFormatFromAndroid14Beta()) {
            for (UserInfo userInfo : this.mUserManager.getUsers()) {
                if (LockPatternUtils.userOwnsFrpCredential(this.mContext, userInfo) && isUserSecure(userInfo.id)) {
                    synchronized (this.mSpManager) {
                        this.mSpManager.migrateFrpPasswordLocked(getCurrentLskfBasedProtectorId(userInfo.id), userInfo, redactActualQualityToMostLenientEquivalentQuality((int) getLong("lockscreen.password_type", 0L, userInfo.id)));
                    }
                    return;
                }
            }
        }
    }

    public final boolean migrateProfileLockKeys() {
        List users = this.mUserManager.getUsers();
        int size = users.size();
        boolean z = true;
        for (int i = 0; i < size; i++) {
            UserInfo userInfo = (UserInfo) users.get(i);
            if (isCredentialSharableWithParent(userInfo.id) && !getSeparateProfileChallengeEnabledInternal(userInfo.id)) {
                z = z & SyntheticPasswordCrypto.migrateLockSettingsKey("profile_key_name_encrypt_" + userInfo.id) & SyntheticPasswordCrypto.migrateLockSettingsKey("profile_key_name_decrypt_" + userInfo.id);
            }
        }
        return z;
    }

    public final void onThirdPartyAppsStarted() {
        synchronized (this.mUserCreationAndRemovalLock) {
            for (int i = 0; i < this.mEarlyRemovedUsers.size(); i++) {
                int keyAt = this.mEarlyRemovedUsers.keyAt(i);
                Slogf.m96i("LockSettingsService", "Removing locksettings state for removed user %d now that boot is complete", Integer.valueOf(keyAt));
                removeUserState(keyAt);
            }
            this.mEarlyRemovedUsers = null;
            for (int i2 = 0; i2 < this.mEarlyCreatedUsers.size(); i2++) {
                int keyAt2 = this.mEarlyCreatedUsers.keyAt(i2);
                removeStateForReusedUserIdIfNecessary(keyAt2, this.mEarlyCreatedUsers.valueAt(i2));
                Slogf.m96i("LockSettingsService", "Creating locksettings state for user %d now that boot is complete", Integer.valueOf(keyAt2));
                initializeSyntheticPassword(keyAt2);
            }
            this.mEarlyCreatedUsers = null;
            if (getString("migrated_all_users_to_sp_and_bound_ce", null, 0) == null) {
                for (UserInfo userInfo : this.mUserManager.getAliveUsers()) {
                    removeStateForReusedUserIdIfNecessary(userInfo.id, userInfo.serialNumber);
                    if (this.mDualDarLockSettings.isDualDARUser(userInfo.id) && !DualDarManager.isOnDeviceOwner(userInfo.id) && !isUserSecure(userInfo.id)) {
                        setString("migrated_dualdar_user_to_sp_and_bound_ce", "false", userInfo.id);
                        DDLog.m33d("LockSettingsService", "need to migrate unsecured DualDar user.", new Object[0]);
                    } else {
                        synchronized (this.mSpManager) {
                            migrateUserToSpWithBoundCeKeyLocked(userInfo.id);
                        }
                    }
                }
                setString("migrated_all_users_to_sp_and_bound_ce", "true", 0);
            }
            this.mThirdPartyAppsStarted = true;
        }
    }

    public final void migrateUserToSpWithBoundCeKeyLocked(int i) {
        if (isUserSecure(i)) {
            Slogf.m88d("LockSettingsService", "User %d is secured; no migration needed", Integer.valueOf(i));
            return;
        }
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        if (currentLskfBasedProtectorId == 0) {
            Slogf.m96i("LockSettingsService", "Migrating unsecured user %d to SP-based credential", Integer.valueOf(i));
            initializeSyntheticPassword(i);
            return;
        }
        Slogf.m96i("LockSettingsService", "Existing unsecured user %d has a synthetic password; re-encrypting CE key with it", Integer.valueOf(i));
        SyntheticPasswordManager.SyntheticPassword syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, LockscreenCredential.createNone(), i, null).syntheticPassword;
        if (syntheticPassword == null) {
            Slogf.wtf("LockSettingsService", "Failed to unwrap synthetic password for unsecured user %d", Integer.valueOf(i));
        } else {
            setUserKeyProtection(i, syntheticPassword.deriveFileBasedEncryptionKey());
        }
    }

    public final void enforceFrpResolved() {
        int mainUserId = this.mInjector.getUserManagerInternal().getMainUserId();
        if (mainUserId < 0) {
            Slog.d("LockSettingsService", "No Main user on device; skipping enforceFrpResolved");
            return;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, mainUserId) == 0;
        boolean z2 = Settings.Global.getInt(contentResolver, "secure_frp_mode", 0) == 1;
        if (z && z2) {
            throw new SecurityException("Cannot change credential in SUW while factory reset protection is not resolved yet");
        }
    }

    public final void checkWritePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsWrite");
    }

    public final void checkPasswordReadPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsRead");
    }

    public final void checkPasswordHavePermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") != 0) {
            EventLog.writeEvent(1397638484, "28251513", Integer.valueOf(ILockSettings.Stub.getCallingUid()), "");
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsHave");
    }

    public final void checkDatabaseReadPermission(String str, int i) {
        if (ArrayUtils.contains(UNPROTECTED_SETTINGS, str) || hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE")) {
            return;
        }
        throw new SecurityException("uid=" + ILockSettings.Stub.getCallingUid() + " needs permission android.permission.ACCESS_KEYGUARD_SECURE_STORAGE to read " + str + " for user " + i);
    }

    public final void checkBiometricPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC", "LockSettingsBiometric");
    }

    public final boolean hasPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    public final void checkManageWeakEscrowTokenMethodUsage() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEAK_ESCROW_TOKEN", "Requires MANAGE_WEAK_ESCROW_TOKEN permission.");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalArgumentException("Weak escrow token are only for automotive devices.");
        }
    }

    public boolean hasSecureLockScreen() {
        return this.mHasSecureLockScreen;
    }

    public boolean getSeparateProfileChallengeEnabled(int i) {
        checkDatabaseReadPermission("lockscreen.profilechallenge", i);
        return getSeparateProfileChallengeEnabledInternal(i);
    }

    public final boolean getSeparateProfileChallengeEnabledInternal(int i) {
        boolean z;
        synchronized (this.mSeparateChallengeLock) {
            z = this.mStorage.getBoolean("lockscreen.profilechallenge", false, i);
        }
        return z;
    }

    public void setSeparateProfileChallengeEnabled(int i, boolean z, LockscreenCredential lockscreenCredential) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(z ? "Enable" : "Disable");
        sb.append(" separate challenge for user ");
        sb.append(i);
        SDPLog.m43i(sb.toString());
        SDPLog.m46p("userId", Integer.valueOf(i), "enabled", Boolean.valueOf(z), "profileUserPassword", lockscreenCredential);
        checkWritePermission();
        if (!this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
            throw new UnsupportedOperationException("This operation requires secure lock screen feature.");
        }
        synchronized (this.mSeparateChallengeLock) {
            if (lockscreenCredential == null) {
                lockscreenCredential = LockscreenCredential.createNone();
            }
            setSeparateProfileChallengeEnabledLocked(i, z, lockscreenCredential);
        }
        notifySeparateProfileChallengeChanged(i);
    }

    public final void setSeparateProfileChallengeEnabledLocked(int i, boolean z, LockscreenCredential lockscreenCredential) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        boolean z2 = getBoolean("lockscreen.profilechallenge", false, i);
        setBoolean("lockscreen.profilechallenge", z, i);
        notifyPasswordPolicyOneLockChanged(!z, i);
        try {
            if (z) {
                this.mStorage.removeChildProfileLock(i);
                removeKeystoreProfileKey(i);
            } else {
                tieProfileLockIfNecessary(i, lockscreenCredential);
            }
        } catch (IllegalStateException e) {
            setBoolean("lockscreen.profilechallenge", z2, i);
            throw e;
        }
    }

    public final void notifyPasswordPolicyOneLockChanged(boolean z, int i) {
        PasswordPolicy passwordPolicy = EnterpriseDeviceManager.getInstance().getPasswordPolicy();
        if (passwordPolicy != null) {
            passwordPolicy.notifyPasswordPolicyOneLockChanged(z, i);
        }
    }

    public final void notifySeparateProfileChallengeChanged(final int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                LockSettingsService.lambda$notifySeparateProfileChallengeChanged$13(i);
            }
        });
    }

    public static /* synthetic */ void lambda$notifySeparateProfileChallengeChanged$13(int i) {
        DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null) {
            devicePolicyManagerInternal.reportSeparateProfileChallengeChanged(i);
        }
    }

    public void setBoolean(String str, boolean z, int i) {
        checkWritePermission();
        Objects.requireNonNull(str);
        this.mStorage.setBoolean(str, z, i);
    }

    public void setLong(String str, long j, final int i) {
        checkWritePermission();
        Objects.requireNonNull(str);
        this.mStorage.setLong(str, j, i);
        if (i == 0 || !SemPersonaManager.isKnoxId(i) || SemPersonaManager.isSecureFolderId(i) || !TextUtils.equals("lockscreen.samsung_biometric", str)) {
            return;
        }
        this.mInjector.getPersonaService().ifPresent(new Consumer() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((PersonaManagerService) obj).unsetTwoFactorValueIfNeeded(i);
            }
        });
    }

    public void setString(String str, String str2, int i) {
        checkWritePermission();
        Objects.requireNonNull(str);
        this.mStorage.setString(str, str2, i);
    }

    public boolean getBoolean(String str, boolean z, int i) {
        checkDatabaseReadPermission(str, i);
        return this.mStorage.getBoolean(str, z, i);
    }

    public long getLong(String str, long j, int i) {
        checkDatabaseReadPermission(str, i);
        return this.mStorage.getLong(str, j, i);
    }

    public String getString(String str, String str2, int i) {
        checkDatabaseReadPermission(str, i);
        return this.mStorage.getString(str, str2, i);
    }

    public final int getKeyguardStoredQuality(int i) {
        return (int) this.mStorage.getLong("lockscreen.password_type", 0L, i);
    }

    public int getPinLength(int i) {
        checkPasswordHavePermission();
        PasswordMetrics userPasswordMetrics = getUserPasswordMetrics(i);
        if (userPasswordMetrics != null && userPasswordMetrics.credType == 3) {
            return userPasswordMetrics.length;
        }
        synchronized (this.mSpManager) {
            long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
            if (currentLskfBasedProtectorId == 0) {
                return -1;
            }
            return this.mSpManager.getPinLength(currentLskfBasedProtectorId, i);
        }
    }

    public boolean refreshStoredPinLength(int i) {
        checkPasswordHavePermission();
        synchronized (this.mSpManager) {
            PasswordMetrics userPasswordMetrics = getUserPasswordMetrics(i);
            if (userPasswordMetrics != null) {
                return this.mSpManager.refreshPinLengthOnDisk(userPasswordMetrics, getCurrentLskfBasedProtectorId(i), i);
            }
            Log.w("LockSettingsService", "PasswordMetrics is not available");
            return false;
        }
    }

    public int getCredentialType(int i) {
        checkPasswordHavePermission();
        return getCredentialTypeInternal(i);
    }

    public final int getCredentialTypeInternal(int i) {
        if (i == -9999) {
            return getFrpCredentialType();
        }
        if (i == -9998 && isSupportForgotPassword()) {
            return getPrevCredentialType(0);
        }
        synchronized (this.mSpManager) {
            long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
            if (currentLskfBasedProtectorId == 0) {
                return -1;
            }
            int credentialType = this.mSpManager.getCredentialType(currentLskfBasedProtectorId, i);
            if (credentialType != 2) {
                return credentialType;
            }
            return pinOrPasswordQualityToCredentialType(getKeyguardStoredQuality(i));
        }
    }

    public final int getFrpCredentialType() {
        LockSettingsStorage.PersistentData readPersistentDataBlock = this.mStorage.readPersistentDataBlock();
        int i = readPersistentDataBlock.type;
        if (i != 1 && i != 2) {
            return -1;
        }
        int frpCredentialType = SyntheticPasswordManager.getFrpCredentialType(readPersistentDataBlock.payload);
        return frpCredentialType != 2 ? frpCredentialType : pinOrPasswordQualityToCredentialType(readPersistentDataBlock.qualityForUi);
    }

    public final int getPrevCredentialType(int i) {
        synchronized (this.mSpManager) {
            long backupLskfBasedProtectorId = getBackupLskfBasedProtectorId(i);
            if (backupLskfBasedProtectorId == 0) {
                return -1;
            }
            int credentialType = this.mSpManager.getCredentialType(backupLskfBasedProtectorId, i);
            if (credentialType != 2) {
                return credentialType;
            }
            return -1;
        }
    }

    public static int pinOrPasswordQualityToCredentialType(int i) {
        if (LockPatternUtils.isQualitySmartCard(i)) {
            return 6;
        }
        if (LockPatternUtils.isQualityAlphabeticPassword(i)) {
            return 4;
        }
        if (LockPatternUtils.isQualityNumericPin(i)) {
            return 3;
        }
        throw new IllegalArgumentException("Quality is neither Pin nor password: " + i);
    }

    public final boolean isUserSecure(int i) {
        return getCredentialTypeInternal(i) != -1;
    }

    public void setKeystorePassword(byte[] bArr, int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        int onUserPasswordChanged = AndroidKeyStoreMaintenance.onUserPasswordChanged(i, bArr);
        this.mStorage.addLog(0, "setKeystorePassword, userhandle = " + i + ", ret = " + onUserPasswordChanged);
    }

    public final void unlockKeystore(byte[] bArr, int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        int onLockScreenEvent = Authorization.onLockScreenEvent(false, i, bArr, (long[]) null);
        this.mStorage.addLog(2, "Authorization.onLockScreenEvent, userhandle = " + i + ", ret = " + onLockScreenEvent);
        if (onLockScreenEvent != 0) {
            this.mStorage.uploadLogFile(2);
        }
    }

    public LockscreenCredential getDecryptedPasswordForTiedProfile(int i) {
        Slogf.m88d("LockSettingsService", "Decrypting password for tied profile %d", Integer.valueOf(i));
        byte[] readChildProfileLock = this.mStorage.readChildProfileLock(i);
        if (readChildProfileLock == null) {
            throw new FileNotFoundException("Child profile lock file not found");
        }
        byte[] copyOfRange = Arrays.copyOfRange(readChildProfileLock, 0, 12);
        byte[] copyOfRange2 = Arrays.copyOfRange(readChildProfileLock, 12, readChildProfileLock.length);
        SecretKey secretKey = (SecretKey) this.mJavaKeyStore.getKey("profile_key_name_decrypt_" + i, null);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, secretKey, new GCMParameterSpec(128, copyOfRange));
        byte[] doFinal = cipher.doFinal(copyOfRange2);
        LockscreenCredential createManagedPassword = LockscreenCredential.createManagedPassword(doFinal);
        Arrays.fill(doFinal, (byte) 0);
        try {
            this.mManagedProfilePasswordCache.storePassword(i, createManagedPassword, getGateKeeperService().getSecureUserId(this.mUserManager.getProfileParent(i).id));
        } catch (RemoteException e) {
            Slogf.m103w("LockSettingsService", "Failed to talk to GateKeeper service", e);
        }
        return createManagedPassword;
    }

    public final void unlockChildProfile(int i) {
        try {
            doVerifyCredential(getDecryptedPasswordForTiedProfile(i), i, null, 0);
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            if (e instanceof FileNotFoundException) {
                Slog.i("LockSettingsService", "Child profile key not found");
            } else {
                Slog.e("LockSettingsService", "Failed to decrypt child profile key", e);
            }
        }
    }

    /* renamed from: unlockUser, reason: merged with bridge method [inline-methods] */
    public final void lambda$setLockCredentialWithToken$21(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        Slogf.m96i("LockSettingsService", "!@BOOT: Unlocking user %d", Integer.valueOf(i));
        boolean isUserUnlockingOrUnlocked = this.mUserManager.isUserUnlockingOrUnlocked(i);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            this.mActivityManager.unlockUser2(i, new IProgressListener.Stub() { // from class: com.android.server.locksettings.LockSettingsService.4
                public void onStarted(int i2, Bundle bundle) {
                    Slog.d("LockSettingsService", "unlockUser started");
                }

                public void onProgress(int i2, int i3, Bundle bundle) {
                    Slog.d("LockSettingsService", "unlockUser progress " + i3);
                }

                public void onFinished(int i2, Bundle bundle) {
                    Slog.d("LockSettingsService", "unlockUser finished");
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await(15L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            if (isCredentialSharableWithParent(i)) {
                if (hasUnifiedChallenge(i)) {
                    return;
                }
                this.mBiometricDeferredQueue.processPendingLockoutResets();
                return;
            }
            for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
                int i2 = userInfo.id;
                if (i2 != i && isCredentialSharableWithParent(i2)) {
                    if (hasUnifiedChallenge(userInfo.id)) {
                        if (this.mUserManager.isUserRunning(userInfo.id)) {
                            unlockChildProfile(userInfo.id);
                        } else {
                            try {
                                getDecryptedPasswordForTiedProfile(userInfo.id);
                            } catch (IOException | GeneralSecurityException e) {
                                Slog.d("LockSettingsService", "Cache work profile password failed", e);
                            }
                        }
                    }
                    if (StorageManager.isFileEncryptedNativeOrEmulated() && userInfo.isSecureFolder()) {
                        UnlockSecureFolderIfAutoDataDecryption(userInfo);
                    }
                    if (isUserUnlockingOrUnlocked) {
                        continue;
                    } else {
                        long clearCallingIdentity = ILockSettings.Stub.clearCallingIdentity();
                        try {
                            maybeShowEncryptionNotificationForUser(userInfo.id, "parent unlocked");
                        } finally {
                            ILockSettings.Stub.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
            }
            this.mBiometricDeferredQueue.processPendingLockoutResets();
        } catch (RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    public final boolean hasUnifiedChallenge(int i) {
        return !getSeparateProfileChallengeEnabledInternal(i) && this.mStorage.hasChildProfileLock(i);
    }

    public final Map getDecryptedPasswordsForAllTiedProfiles(int i) {
        if (UserManager.isVirtualUserId(i) || isCredentialSharableWithParent(i)) {
            return null;
        }
        ArrayMap arrayMap = new ArrayMap();
        List profiles = this.mUserManager.getProfiles(i);
        int size = profiles.size();
        for (int i2 = 0; i2 < size; i2++) {
            UserInfo userInfo = (UserInfo) profiles.get(i2);
            if (isCredentialSharableWithParent(userInfo.id)) {
                int i3 = userInfo.id;
                if (!getSeparateProfileChallengeEnabledInternal(i3)) {
                    try {
                        arrayMap.put(Integer.valueOf(i3), getDecryptedPasswordForTiedProfile(i3));
                    } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                        Slog.e("LockSettingsService", "getDecryptedPasswordsForAllTiedProfiles failed for user " + i3, e);
                    }
                }
            }
        }
        return arrayMap;
    }

    public final void synchronizeUnifiedWorkChallengeForProfiles(int i, Map map) {
        if (UserManager.isVirtualUserId(i) || isCredentialSharableWithParent(i)) {
            return;
        }
        boolean isUserSecure = isUserSecure(i);
        List profiles = this.mUserManager.getProfiles(i);
        int size = profiles.size();
        SDPLog.m43i("Synchronize challenge along with user " + i);
        SDPLog.m38d(String.format("Feasible for profiles(%d) ? %b", Integer.valueOf(size), Boolean.valueOf(isUserSecure)));
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = ((UserInfo) profiles.get(i2)).id;
            if (isCredentialSharableWithParent(i3) && !getSeparateProfileChallengeEnabledInternal(i3)) {
                if (isUserSecure) {
                    tieProfileLockIfNecessary(i3, LockscreenCredential.createNone());
                } else if (map != null && map.containsKey(Integer.valueOf(i3))) {
                    SDPLog.m38d("Profile(" + i3 + ") credential found! Clear profile credential.");
                    setLockCredentialInternal(LockscreenCredential.createNone(), (LockscreenCredential) map.get(Integer.valueOf(i3)), i3, true);
                    this.mStorage.removeChildProfileLock(i3);
                    removeKeystoreProfileKey(i3);
                } else {
                    SDPLog.m38d("Profile(" + i3 + ") credential not found... Clear profile credential.");
                    Slog.wtf("LockSettingsService", "Attempt to clear tied challenge, but no password supplied.");
                }
            }
        }
    }

    public final boolean isProfileWithUnifiedLock(int i) {
        return isCredentialSharableWithParent(i) && !getSeparateProfileChallengeEnabledInternal(i);
    }

    public final void sendCredentialsOnUnlockIfRequired(LockscreenCredential lockscreenCredential, int i) {
        if (UserManager.isVirtualUserId(i) || i == -9999 || lockscreenCredential.isNone() || isProfileWithUnifiedLock(i)) {
            return;
        }
        Iterator it = getProfilesWithSameLockScreen(i).iterator();
        while (it.hasNext()) {
            this.mRecoverableKeyStoreManager.lockScreenSecretAvailable(lockscreenCredential.getType(), lockscreenCredential.getCredential(), ((Integer) it.next()).intValue());
        }
    }

    public final void sendCredentialsOnChangeIfRequired(LockscreenCredential lockscreenCredential, int i, boolean z) {
        if (z) {
            return;
        }
        byte[] credential = lockscreenCredential.isNone() ? null : lockscreenCredential.getCredential();
        Iterator it = getProfilesWithSameLockScreen(i).iterator();
        while (it.hasNext()) {
            this.mRecoverableKeyStoreManager.lockScreenSecretChanged(lockscreenCredential.getType(), credential, ((Integer) it.next()).intValue());
        }
    }

    public final Set getProfilesWithSameLockScreen(int i) {
        ArraySet arraySet = new ArraySet();
        for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            int i2 = userInfo.id;
            if (i2 == i || (userInfo.profileGroupId == i && isProfileWithUnifiedLock(i2))) {
                arraySet.add(Integer.valueOf(userInfo.id));
            }
        }
        return arraySet;
    }

    public boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
        return setLockCredentialWithIgnoreNotifyIfNeeded(lockscreenCredential, lockscreenCredential2, i, false);
    }

    public boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) {
        boolean z2;
        String str;
        StringBuilder sb;
        if (!this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
            throw new UnsupportedOperationException("This operation requires secure lock screen feature");
        }
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS") && (!hasPermission("android.permission.SET_INITIAL_LOCK") || !lockscreenCredential2.isNone())) {
            throw new SecurityException("setLockCredential requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        if (i == -9998) {
            z2 = true;
            i = 0;
        } else {
            z2 = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            enforceFrpResolved();
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "n_digits_pin_enabled", 0, i);
            if (!lockscreenCredential2.isNone() && isProfileWithUnifiedLock(i)) {
                verifyCredential(lockscreenCredential2, this.mUserManager.getProfileParent(i).id, 0);
                lockscreenCredential2.zeroize();
                lockscreenCredential2 = LockscreenCredential.createNone();
            }
            boolean isSdpNotSupportedSecureFolder = this.mUserManager.getUserInfo(i).isSdpNotSupportedSecureFolder();
            if (isSdpNotSupportedSecureFolder) {
                SDPLog.m38d(String.format("User %d identified as sdp not-supported secure folder user", Integer.valueOf(i)));
            }
            if (isEnterpriseUser(i)) {
                lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
                lockscreenCredential2 = StreamCipher.decryptStream(lockscreenCredential2);
            }
            if (isSEPLiteSecureFolder(i) || isSdpNotSupportedSecureFolder) {
                SDPLog.m38d(String.format("SEP-Lite User %d identified as SecureFolder user", Integer.valueOf(i)));
                if (this.mDarLockSettings.setSecureFolderLockCredential(lockscreenCredential, lockscreenCredential2, i).getResponseCode() != 1) {
                    SDPLog.m38d("sdp not supported : setSecureFolderLockCredential success");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    str = "User " + i + " enrolled!\n";
                    sb = new StringBuilder();
                    sb.append(str);
                    sb.append(this.mStorage.getPWFilelist(i));
                    this.mStorage.addLog(0, sb.toString());
                    this.mStorage.uploadLogFile(0);
                    return true;
                }
            }
            synchronized (this.mSeparateChallengeLock) {
                if (!setLockCredentialInternal(lockscreenCredential, lockscreenCredential2, z2 ? -9998 : i, false)) {
                    scheduleGc();
                    return false;
                }
                setSeparateProfileChallengeEnabledLocked(i, true, null);
                if (!z || !isEnterpriseUser(i)) {
                    notifyPasswordChanged(lockscreenCredential, i);
                }
                if (isCredentialSharableWithParent(i)) {
                    setDeviceUnlockedForUser(i);
                }
                notifySeparateProfileChallengeChanged(i);
                onPostPasswordChanged(lockscreenCredential, i);
                Settings.Secure.putLongForUser(this.mContext.getContentResolver(), "ppp_enroll_timestamp", System.currentTimeMillis(), i);
                if (isEnterpriseUser(i)) {
                    lockscreenCredential.zeroize();
                    lockscreenCredential2.zeroize();
                }
                scheduleGc();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                str = "User " + i + " enrolled!\n";
                sb = new StringBuilder();
                sb.append(str);
                sb.append(this.mStorage.getPWFilelist(i));
                this.mStorage.addLog(0, sb.toString());
                this.mStorage.uploadLogFile(0);
                return true;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mStorage.addLog(0, ("User " + i + " enrolled!\n") + this.mStorage.getPWFilelist(i));
            this.mStorage.uploadLogFile(0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0152 A[Catch: all -> 0x01fe, TryCatch #4 {all -> 0x01fe, blocks: (B:16:0x00eb, B:18:0x00f1, B:21:0x00f7, B:22:0x0119, B:25:0x0121, B:26:0x014c, B:28:0x0152, B:30:0x0159, B:33:0x0161, B:35:0x0167, B:36:0x017a, B:38:0x017c, B:39:0x0191, B:40:0x0192, B:41:0x01a5, B:43:0x01a7, B:47:0x01b1, B:48:0x01b4, B:50:0x01bd, B:53:0x01c5, B:55:0x01cb, B:69:0x0137, B:74:0x00fd, B:72:0x010b), top: B:15:0x00eb, inners: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01a7 A[Catch: all -> 0x01fe, TryCatch #4 {all -> 0x01fe, blocks: (B:16:0x00eb, B:18:0x00f1, B:21:0x00f7, B:22:0x0119, B:25:0x0121, B:26:0x014c, B:28:0x0152, B:30:0x0159, B:33:0x0161, B:35:0x0167, B:36:0x017a, B:38:0x017c, B:39:0x0191, B:40:0x0192, B:41:0x01a5, B:43:0x01a7, B:47:0x01b1, B:48:0x01b4, B:50:0x01bd, B:53:0x01c5, B:55:0x01cb, B:69:0x0137, B:74:0x00fd, B:72:0x010b), top: B:15:0x00eb, inners: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean setLockCredentialInternal(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) {
        int i2;
        boolean z2;
        Object obj;
        LockscreenCredential lockscreenCredential3;
        long currentLskfBasedProtectorId;
        SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector;
        SyntheticPasswordManager.SyntheticPassword syntheticPassword;
        int i3;
        Objects.requireNonNull(lockscreenCredential);
        Objects.requireNonNull(lockscreenCredential2);
        if (i == -9998) {
            Slog.e("LockSettingsService", "!@ set credential with prev!!!");
            i2 = 0;
            z2 = true;
        } else {
            i2 = i;
            z2 = false;
        }
        if (UserManager.isVirtualUserId(i2)) {
            SDPLog.m43i("Set credential for user virtual " + i2 + " credentialType:" + lockscreenCredential.getType());
            obj = "credential";
            SDPLog.m46p("credential", lockscreenCredential.getCredential(), "credentialType", Integer.valueOf(lockscreenCredential.getType()), "savedCredential", lockscreenCredential2.getCredential(), "savedCredentialType", Integer.valueOf(lockscreenCredential2.getType()), "userId", Integer.valueOf(i2), "isLockTiedToParent", Boolean.valueOf(z));
            if (this.mDarVirtualLock.setLockCredentialInternalForVirtualUser(lockscreenCredential, lockscreenCredential2, i2, z)) {
                return true;
            }
        }
        if (isEnterpriseUser(i2)) {
            SDPLog.m43i("Set credential for user enterprise " + i2 + " credentialType:" + lockscreenCredential.getType());
            obj = "credential";
            SDPLog.m46p("credential", lockscreenCredential.getCredential(), "credentialType", Integer.valueOf(lockscreenCredential.getType()), "savedCredential", lockscreenCredential2.getCredential(), "savedCredentialType", Integer.valueOf(lockscreenCredential2.getType()), "userId", Integer.valueOf(i2), "isLockTiedToParent", Boolean.valueOf(z));
            this.mDarLockSettings.checkLockMaterialsTraced(lockscreenCredential, lockscreenCredential2, i2);
        }
        SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
        synchronized (syntheticPasswordManager) {
            try {
                try {
                    if (lockscreenCredential2.isNone() && isProfileWithUnifiedLock(i2)) {
                        try {
                            lockscreenCredential3 = getDecryptedPasswordForTiedProfile(i2);
                        } catch (FileNotFoundException e) {
                            Slog.i("LockSettingsService", "Child profile key not found");
                            SDPLog.m41e("Child profile key not found", e);
                        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e2) {
                            Slog.e("LockSettingsService", "Failed to decrypt child profile key", e2);
                            SDPLog.m41e("Failed to decrypt child profile key", e2);
                        }
                        if (!isSupportForgotPassword() && z2) {
                            currentLskfBasedProtectorId = getBackupLskfBasedProtectorId(i2);
                            unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, lockscreenCredential3, i2, null);
                        } else {
                            currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i2);
                            unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, lockscreenCredential3, i2, null);
                        }
                        VerifyCredentialResponse verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
                        syntheticPassword = unlockLskfBasedProtector.syntheticPassword;
                        if (syntheticPassword != null) {
                            SDPLog.m38d("Untrusted credential reset not acceptable");
                            if (verifyCredentialResponse != null && verifyCredentialResponse.getResponseCode() != -1) {
                                if (verifyCredentialResponse.getResponseCode() == 1) {
                                    Slog.w("LockSettingsService", "Failed to enroll: rate limit exceeded.");
                                    this.mStorage.addLog(0, "Failed to enroll: rate limit exceeded.");
                                    this.mStorage.uploadLogFile(0);
                                    return false;
                                }
                                this.mStorage.addLog(0, "password change failed");
                                this.mStorage.uploadLogFile(0);
                                throw new IllegalStateException("password change failed");
                            }
                            Slog.w("LockSettingsService", "Failed to enroll: incorrect credential.");
                            this.mStorage.addLog(0, "Failed to enroll: incorrect credential.");
                            this.mStorage.uploadLogFile(0);
                            return false;
                        }
                        if (isSupportForgotPassword() && !z2 && i2 == 0) {
                            expirePreviousData();
                        }
                        onSyntheticPasswordUnlocked(i2, syntheticPassword);
                        if (isSupportForgotPassword() && !isSupportWeaver() && i2 == 0 && !lockscreenCredential3.isNone() && !lockscreenCredential.isNone()) {
                            obj = syntheticPasswordManager;
                            i3 = i2;
                            setLockCredentialWithSpLocked(lockscreenCredential, lockscreenCredential3, currentLskfBasedProtectorId, syntheticPassword, i2);
                        } else {
                            obj = syntheticPasswordManager;
                            i3 = i2;
                            setLockCredentialWithSpLocked(lockscreenCredential, syntheticPassword, i3);
                        }
                        sendCredentialsOnChangeIfRequired(lockscreenCredential, i3, z);
                        this.mDarLockSettings.saveEscrowDataIfNeededLocked(syntheticPassword, i3);
                        if (this.mInjector.isDualDAREnabled()) {
                            try {
                                this.mDualDarLockSettings.performDualDARPasswordChange(lockscreenCredential, lockscreenCredential3, i3, z);
                            } catch (RemoteException e3) {
                                e3.printStackTrace();
                            }
                        }
                        return true;
                    }
                    lockscreenCredential3 = lockscreenCredential2;
                    if (!isSupportForgotPassword()) {
                    }
                    currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i2);
                    unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, lockscreenCredential3, i2, null);
                    VerifyCredentialResponse verifyCredentialResponse2 = unlockLskfBasedProtector.gkResponse;
                    syntheticPassword = unlockLskfBasedProtector.syntheticPassword;
                    if (syntheticPassword != null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                obj = syntheticPasswordManager;
                throw th;
            }
        }
    }

    public final void onPostPasswordChanged(LockscreenCredential lockscreenCredential, int i) {
        if (lockscreenCredential.isPattern()) {
            setBoolean("lockscreen.patterneverchosen", true, i);
        }
        updatePasswordHistory(lockscreenCredential, i);
        ((TrustManager) this.mContext.getSystemService(TrustManager.class)).reportEnabledTrustAgentsChanged(i);
        updateVerifier(lockscreenCredential.getCredential());
    }

    public final void updateVerifierIfNeeded(byte[] bArr) {
        boolean z = SystemProperties.getBoolean("persist.escrowvault.sa.signed", false);
        Slog.i("LockSettingsService", "isSaSignedIn : " + z);
        if (z) {
            updateVerifier(bArr);
        }
    }

    public final void updateVerifier(final byte[] bArr) {
        Slog.i("LockSettingsService", "updateVerifier");
        final Intent intent = new Intent("com.samsung.android.kmxservice.escrowvault.UPDATE_VERIFIER");
        intent.setPackage("com.samsung.android.kmxservice");
        intent.putExtra("service_name", "KmxService");
        new Thread(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                LockSettingsService.this.lambda$updateVerifier$15(intent, bArr);
            }
        }, "KMX_UPDATE_VERIFIER_SERVICE_CONNECTION").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateVerifier$15(Intent intent, byte[] bArr) {
        this.mShouldUnbind = this.mContext.bindService(intent, new UpdateVerifierServiceConnection(bArr), 1);
    }

    public class UpdateVerifierServiceConnection implements ServiceConnection {
        public byte[] mCredential;

        /* renamed from: g */
        public final BigInteger f1713g = BigInteger.valueOf(5);

        /* renamed from: N */
        public final BigInteger f1712N = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A93AD2CAFFFFFFFFFFFFFFFF", 16);

        public UpdateVerifierServiceConnection(byte[] bArr) {
            this.mCredential = bArr;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Slog.i("LockSettingsService", "onServiceConnected");
            final IUpdateVerifierInterface asInterface = IUpdateVerifierInterface.Stub.asInterface(iBinder);
            if (asInterface == null) {
                Slog.i("LockSettingsService", "bind failed");
                unbindService();
            } else {
                try {
                    asInterface.requestSaGuid(new IUpdateVerifierCallback.Stub() { // from class: com.android.server.locksettings.LockSettingsService.UpdateVerifierServiceConnection.1
                        public void onReceiveSaGuid(String str) {
                            if (TextUtils.isEmpty(str)) {
                                UpdateVerifierServiceConnection.this.unbindService();
                                return;
                            }
                            byte[] makeSalt = UpdateVerifierServiceConnection.this.makeSalt();
                            UpdateVerifierServiceConnection updateVerifierServiceConnection = UpdateVerifierServiceConnection.this;
                            asInterface.updateVerifier(updateVerifierServiceConnection.makeVerifier(str, updateVerifierServiceConnection.mCredential, makeSalt), makeSalt);
                            UpdateVerifierServiceConnection.this.unbindService();
                        }
                    });
                } catch (RemoteException | SecurityException unused) {
                    Slog.e("LockSettingsService", "RemoteException");
                    unbindService();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Slog.i("LockSettingsService", "onServiceDisconnected");
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            Slog.i("LockSettingsService", "onBindingDied");
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Slog.i("LockSettingsService", "onNullBinding");
            unbindService();
        }

        public final void unbindService() {
            if (LockSettingsService.this.mShouldUnbind) {
                LockSettingsService.this.mContext.unbindService(this);
                LockSettingsService.this.mShouldUnbind = false;
            }
        }

        public final byte[] makeSalt() {
            return SecureRandomUtils.randomBytes(32);
        }

        public final BigInteger hash(byte[]... bArr) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                for (byte[] bArr2 : bArr) {
                    messageDigest.update(bArr2);
                }
                return new BigInteger(BigInteger.ONE.signum(), messageDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                Slog.e("LockSettingsService", "hash error = " + e);
                return null;
            }
        }

        public final byte[] makeVerifier(String str, byte[] bArr, byte[] bArr2) {
            try {
                return this.f1713g.modPow(hash(bArr2, hash(str.getBytes(), XmlUtils.STRING_ARRAY_SEPARATOR.getBytes(), SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(new PBEKeySpec((str + new String(bArr, StandardCharsets.UTF_8)).toCharArray(), str.getBytes(), 100000, 256)).getEncoded()).toByteArray()), this.f1712N).toByteArray();
            } catch (Exception e) {
                Slog.e("LockSettingsService", "exception to make verifier = " + e);
                return null;
            }
        }
    }

    public final void updatePasswordHistory(LockscreenCredential lockscreenCredential, int i) {
        if (lockscreenCredential.isNone() || lockscreenCredential.isPattern()) {
            return;
        }
        String string = getString("lockscreen.passwordhistory", null, i);
        String str = "";
        if (string == null) {
            string = "";
        }
        int requestedPasswordHistoryLength = getRequestedPasswordHistoryLength(i);
        if (requestedPasswordHistoryLength != 0) {
            Slogf.m88d("LockSettingsService", "Adding new password to password history for user %d", Integer.valueOf(i));
            str = lockscreenCredential.passwordToHistoryHash(getSalt(i).getBytes(), getHashFactor(lockscreenCredential, i));
            if (str == null) {
                Slog.e("LockSettingsService", "Failed to compute password hash; password history won't be updated");
                return;
            }
            if (!TextUtils.isEmpty(string)) {
                String[] split = string.split(",");
                StringJoiner stringJoiner = new StringJoiner(",");
                stringJoiner.add(str);
                for (int i2 = 0; i2 < requestedPasswordHistoryLength - 1 && i2 < split.length; i2++) {
                    stringJoiner.add(split[i2]);
                }
                str = stringJoiner.toString();
            }
        }
        setString("lockscreen.passwordhistory", str, i);
    }

    public final String getSalt(int i) {
        long j = getLong("lockscreen.password_salt", 0L, i);
        if (j == 0) {
            j = SecureRandomUtils.randomLong();
            setLong("lockscreen.password_salt", j, i);
        }
        return Long.toHexString(j);
    }

    public final int getRequestedPasswordHistoryLength(int i) {
        if (this.mDualDarAuthUtils.isInnerAuthUserForDo(i)) {
            i = this.mDualDarAuthUtils.getMainUserId(i);
        }
        return this.mInjector.getDevicePolicyManager().getPasswordHistoryLength(null, i);
    }

    public final UserManager getUserManagerFromCache(int i) {
        UserHandle of = UserHandle.of(i);
        if (this.mUserManagerCache.containsKey(of)) {
            return (UserManager) this.mUserManagerCache.get(of);
        }
        try {
            UserManager userManager = (UserManager) this.mContext.createPackageContextAsUser("system", 0, of).getSystemService(UserManager.class);
            this.mUserManagerCache.put(of, userManager);
            return userManager;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Failed to create context for user " + of, e);
        }
    }

    public boolean isCredentialSharableWithParent(int i) {
        return getUserManagerFromCache(i).isCredentialSharableWithParent();
    }

    public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mSpManager.registerWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mSpManager.unregisterWeakEscrowTokenRemovedListener(iWeakEscrowTokenRemovedListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long addWeakEscrowToken(byte[] bArr, int i, final IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        Objects.requireNonNull(iWeakEscrowTokenActivatedListener, "Listener can not be null.");
        LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback = new LockPatternUtils.EscrowTokenStateChangeCallback() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda2
            public final void onEscrowTokenActivated(long j, int i2) {
                LockSettingsService.lambda$addWeakEscrowToken$16(iWeakEscrowTokenActivatedListener, j, i2);
            }
        };
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return addEscrowToken(bArr, 1, i, escrowTokenStateChangeCallback);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static /* synthetic */ void lambda$addWeakEscrowToken$16(IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener, long j, int i) {
        try {
            iWeakEscrowTokenActivatedListener.onWeakEscrowTokenActivated(j, i);
        } catch (RemoteException e) {
            Slog.e("LockSettingsService", "Exception while notifying weak escrow token has been activated", e);
        }
    }

    public boolean removeWeakEscrowToken(long j, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return removeEscrowToken(j, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWeakEscrowTokenActive(long j, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return isEscrowTokenActive(j, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSpManager) {
                if (!this.mSpManager.hasEscrowData(i)) {
                    Slog.w("LockSettingsService", "Escrow token is disabled on the current user");
                    return false;
                }
                if (this.mSpManager.unlockWeakTokenBasedProtector(getGateKeeperService(), j, bArr, i).syntheticPassword == null) {
                    Slog.w("LockSettingsService", "Invalid escrow token supplied");
                    return false;
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void tieProfileLockToParent(int i, int i2, LockscreenCredential lockscreenCredential) {
        Slogf.m96i("LockSettingsService", "Tying lock for profile user %d to parent user %d", Integer.valueOf(i), Integer.valueOf(i2));
        try {
            getGateKeeperService().getSecureUserId(i2);
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(new SecureRandom());
                SecretKey generateKey = keyGenerator.generateKey();
                try {
                    this.mJavaKeyStore.setEntry("profile_key_name_encrypt_" + i, new KeyStore.SecretKeyEntry(generateKey), new KeyProtection.Builder(1).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
                    this.mJavaKeyStore.setEntry("profile_key_name_decrypt_" + i, new KeyStore.SecretKeyEntry(generateKey), new KeyProtection.Builder(2).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setCriticalToDeviceEncryption(true).build());
                    SecretKey secretKey = (SecretKey) this.mJavaKeyStore.getKey("profile_key_name_encrypt_" + i, null);
                    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                    cipher.init(1, secretKey);
                    byte[] doFinal = cipher.doFinal(lockscreenCredential.getCredential());
                    byte[] iv = cipher.getIV();
                    if (iv.length != 12) {
                        throw new IllegalArgumentException("Invalid iv length: " + iv.length);
                    }
                    this.mStorage.writeChildProfileLock(i, ArrayUtils.concat(new byte[][]{iv, doFinal}));
                } finally {
                    this.mJavaKeyStore.deleteEntry("profile_key_name_encrypt_" + i);
                }
            } catch (InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                throw new IllegalStateException("Failed to encrypt key", e);
            }
        } catch (RemoteException e2) {
            throw new IllegalStateException("Failed to talk to GateKeeper service", e2);
        }
    }

    public final void setUserKeyProtection(int i, byte[] bArr) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mStorageManager.setUserKeyProtection(i, this.mDualDarLockSettings.getChangedStorageSecretIfDualDAR(i, bArr));
            } catch (RemoteException e) {
                throw new IllegalStateException("Failed to protect CE key for user " + i, e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isUserKeyUnlocked(int i) {
        try {
            return this.mStorageManager.isUserKeyUnlocked(i);
        } catch (RemoteException e) {
            Slog.e("LockSettingsService", "failed to check user key locked state", e);
            return false;
        }
    }

    public final void unlockUserKey(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        if (isUserKeyUnlocked(i)) {
            Slogf.m88d("LockSettingsService", "CE storage for user %d is already unlocked", Integer.valueOf(i));
            return;
        }
        UserInfo userInfo = this.mUserManager.getUserInfo(i);
        String str = isUserSecure(i) ? "secured" : "unsecured";
        byte[] changedStorageSecretIfDualDAR = this.mDualDarLockSettings.getChangedStorageSecretIfDualDAR(i, syntheticPassword.deriveFileBasedEncryptionKey());
        try {
            try {
                VerifyCredentialResponse verifyChallenge = this.mSpManager.verifyChallenge(getGateKeeperService(), syntheticPassword, 0L, i);
                if (isTokenUser(i) && verifyChallenge != null) {
                    byte[] gatekeeperHAT = verifyChallenge.getGatekeeperHAT();
                    if (gatekeeperHAT == null) {
                        throw new IllegalArgumentException("Empty payload verifying a credential we just set");
                    }
                    this.mStorageManager.unlockUserKey(i, userInfo.serialNumber, gatekeeperHAT, changedStorageSecretIfDualDAR);
                } else {
                    this.mStorageManager.unlockUserKey(i, userInfo.serialNumber, (byte[]) null, changedStorageSecretIfDualDAR);
                }
                Slogf.m96i("LockSettingsService", "!@Unlocked CE storage for %s user %d", str, Integer.valueOf(i));
            } catch (RemoteException e) {
                Slogf.wtf("LockSettingsService", e, "Failed to unlock CE storage for %s user %d", str, Integer.valueOf(i));
            }
        } finally {
            Arrays.fill(changedStorageSecretIfDualDAR, (byte) 0);
        }
    }

    public final boolean isTokenUser(int i) {
        if (this.mFirstApiLevel > 30) {
            return false;
        }
        if (i != 0) {
            return isEnterpriseUser(i) && !SemPersonaManager.isSecureFolderId(i);
        }
        return true;
    }

    public final void unlockUserKeyIfUnsecured(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        if (Objects.equals(getString("migrated_dualdar_user_to_sp_and_bound_ce", "true", i), "false")) {
            synchronized (this.mSpManager) {
                DDLog.m33d("LockSettingsService", "Migrating unsecured DualDar user.", new Object[0]);
                migrateUserToSpWithBoundCeKeyLocked(i);
            }
            DDLog.m33d("LockSettingsService", "Migrated unsecured DualDar user.", new Object[0]);
            setString("migrated_dualdar_user_to_sp_and_bound_ce", "true", i);
        }
        synchronized (this.mSpManager) {
            if (isUserKeyUnlocked(i)) {
                Slogf.m88d("LockSettingsService", "CE storage for user %d is already unlocked", Integer.valueOf(i));
                return;
            }
            if (isUserSecure(i)) {
                Slogf.m88d("LockSettingsService", "Not unlocking CE storage for user %d yet because user is secured", Integer.valueOf(i));
                return;
            }
            if (!this.mDualDarLockSettings.unlockDualDarUserKeyIfUnsecured(i)) {
                Slogf.m88d("LockSettingsService", "unlocking CE storage for dualdar user %d is failed", Integer.valueOf(i));
                return;
            }
            Slogf.m96i("LockSettingsService", "Unwrapping synthetic password for unsecured user %d", Integer.valueOf(i));
            SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), LockscreenCredential.createNone(), i, null);
            SyntheticPasswordManager.SyntheticPassword syntheticPassword = unlockLskfBasedProtector.syntheticPassword;
            if (syntheticPassword == null) {
                Slogf.wtf("LockSettingsService", "Failed to unwrap synthetic password for unsecured user %d", Integer.valueOf(i));
            } else {
                onSyntheticPasswordUnlocked(i, syntheticPassword);
                unlockUserKey(i, unlockLskfBasedProtector.syntheticPassword);
            }
        }
    }

    public void resetKeyStore(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        checkWritePermission();
        Slogf.m88d("LockSettingsService", "Resetting keystore for user %d", Integer.valueOf(i));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            if (isCredentialSharableWithParent(userInfo.id) && !getSeparateProfileChallengeEnabledInternal(userInfo.id) && this.mStorage.hasChildProfileLock(userInfo.id)) {
                try {
                    arrayList2.add(getDecryptedPasswordForTiedProfile(userInfo.id));
                    arrayList.add(Integer.valueOf(userInfo.id));
                } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                    Slog.e("LockSettingsService", "Failed to decrypt child profile key", e);
                }
            }
        }
        int i2 = 0;
        try {
            for (int i3 : this.mUserManager.getProfileIdsWithDisabled(i)) {
                int length = SYSTEM_CREDENTIAL_UIDS.length;
                for (int i4 = 0; i4 < length; i4++) {
                    AndroidKeyStoreMaintenance.clearNamespace(0, UserHandle.getUid(i3, r8[i4]));
                }
            }
            if (this.mUserManager.getUserInfo(i).isPrimary()) {
                AndroidKeyStoreMaintenance.clearNamespace(2, 102L);
            }
        } finally {
            while (i2 < arrayList.size()) {
                int intValue = ((Integer) arrayList.get(i2)).intValue();
                LockscreenCredential lockscreenCredential = (LockscreenCredential) arrayList2.get(i2);
                if (intValue != -1 && lockscreenCredential != null) {
                    tieProfileLockToParent(intValue, i, lockscreenCredential);
                }
                if (lockscreenCredential != null) {
                    lockscreenCredential.zeroize();
                }
                i2++;
            }
        }
    }

    public VerifyCredentialResponse checkCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        VerifyCredentialResponse doVerifyCredential;
        checkPasswordReadPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isEnterpriseUser(i)) {
                doVerifyCredential = doVerifyCredentialForEnterpriseUser(lockscreenCredential, i, iCheckCredentialProgressCallback);
            } else {
                doVerifyCredential = doVerifyCredential(lockscreenCredential, i, iCheckCredentialProgressCallback, 0);
            }
            return doVerifyCredential;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    public final VerifyCredentialResponse doVerifyCredentialForEnterpriseUser(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        try {
            lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
            return doVerifyCredential(lockscreenCredential, i, iCheckCredentialProgressCallback, 0);
        } finally {
            if (lockscreenCredential != null) {
                lockscreenCredential.zeroize();
            }
        }
    }

    public VerifyCredentialResponse verifyCredential(LockscreenCredential lockscreenCredential, int i, int i2) {
        VerifyCredentialResponse doVerifyCredential;
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("verifyCredential requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isEnterpriseUser(i)) {
                doVerifyCredential = doVerifyCredentialForEnterpriseUser(lockscreenCredential, i, i2);
            } else {
                doVerifyCredential = doVerifyCredential(lockscreenCredential, i, null, i2);
            }
            return doVerifyCredential;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    public final VerifyCredentialResponse doVerifyCredentialForEnterpriseUser(LockscreenCredential lockscreenCredential, int i, int i2) {
        try {
            lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
            return doVerifyCredential(lockscreenCredential, i, null, i2);
        } finally {
            if (lockscreenCredential != null) {
                lockscreenCredential.zeroize();
            }
        }
    }

    public VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) {
        byte[] bArr;
        VerifyCredentialResponse verifyChallengeInternal;
        checkPasswordReadPermission();
        synchronized (this.mGatekeeperPasswords) {
            bArr = (byte[]) this.mGatekeeperPasswords.get(j);
        }
        synchronized (this.mSpManager) {
            if (bArr == null) {
                Slog.d("LockSettingsService", "No gatekeeper password for handle");
                verifyChallengeInternal = VerifyCredentialResponse.ERROR;
            } else {
                verifyChallengeInternal = this.mSpManager.verifyChallengeInternal(getGateKeeperService(), bArr, j2, i);
            }
        }
        return verifyChallengeInternal;
    }

    public void removeGatekeeperPasswordHandle(long j) {
        checkPasswordReadPermission();
        synchronized (this.mGatekeeperPasswords) {
            this.mGatekeeperPasswords.remove(j);
        }
    }

    public final VerifyCredentialResponse doVerifyCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback, int i2) {
        int i3;
        long j;
        boolean z;
        if (lockscreenCredential == null || lockscreenCredential.isNone()) {
            throw new IllegalArgumentException("Credential can't be null or empty");
        }
        SDPLog.m43i("Verify credential for user " + i);
        SDPLog.m46p("credential", lockscreenCredential.getCredential(), "credentialType", Integer.valueOf(lockscreenCredential.getType()), "userId", Integer.valueOf(i));
        if (UserManager.isVirtualUserId(i)) {
            return this.mDarVirtualLock.doVerifyCredential(lockscreenCredential, i, iCheckCredentialProgressCallback);
        }
        if (i == -9999 && Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
            Slog.e("LockSettingsService", "FRP credential can only be verified prior to provisioning.");
            return VerifyCredentialResponse.ERROR;
        }
        Slogf.m96i("LockSettingsService", "Verifying lockscreen credential for user %d", Integer.valueOf(i));
        try {
            VerifyCredentialResponse doVerifyCredentialForDualDAR = this.mDualDarLockSettings.doVerifyCredentialForDualDAR(lockscreenCredential, i);
            if (doVerifyCredentialForDualDAR.getResponseCode() != 0) {
                Slog.e("LockSettingsService", "verifyChallenge for DualDAR failed.");
                LockDetectionTracker.getInstance(this.mContext).reportLockDetection(doVerifyCredentialForDualDAR, lockscreenCredential.getType());
                return doVerifyCredentialForDualDAR;
            }
        } catch (RemoteException e) {
            Slog.e("LockSettingsService", "RemoteException : " + e.getMessage());
        }
        synchronized (this.mSpManager) {
            if (i == -9999) {
                return this.mSpManager.verifyFrpCredential(getGateKeeperService(), lockscreenCredential, iCheckCredentialProgressCallback);
            }
            long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
            if (isSupportForgotPassword() && i == -9998) {
                Slog.e("LockSettingsService", "!@ try unlock with prevCredential!!!");
                j = getBackupLskfBasedProtectorId(0);
                i3 = 0;
                z = true;
            } else {
                i3 = i;
                j = currentLskfBasedProtectorId;
                z = false;
            }
            SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), j, lockscreenCredential, i3, iCheckCredentialProgressCallback);
            VerifyCredentialResponse verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
            if (verifyCredentialResponse.getResponseCode() == 0) {
                this.mBiometricDeferredQueue.addPendingLockoutResetForUser(i3, unlockLskfBasedProtector.syntheticPassword.deriveGkPassword());
                verifyCredentialResponse = this.mSpManager.verifyChallenge(getGateKeeperService(), unlockLskfBasedProtector.syntheticPassword, 0L, i3);
                if (verifyCredentialResponse.getResponseCode() != 0) {
                    Slog.wtf("LockSettingsService", "verifyChallenge with SP failed.");
                    return VerifyCredentialResponse.ERROR;
                }
            }
            if (verifyCredentialResponse.getResponseCode() == 0) {
                Slogf.m96i("LockSettingsService", "Successfully verified lockscreen credential for user %d", Integer.valueOf(i3));
                onCredentialVerified(unlockLskfBasedProtector.syntheticPassword, z ? null : PasswordMetrics.computeForCredential(lockscreenCredential), i3);
                if (isDualDarAuthUserId(i3)) {
                    this.mDualDarLockSettings.activateEscrowTokensForDualDAR(unlockLskfBasedProtector.syntheticPassword, i3, lockscreenCredential.getCredential());
                }
                if ((i2 & 1) != 0) {
                    verifyCredentialResponse = new VerifyCredentialResponse.Builder().setGatekeeperPasswordHandle(storeGatekeeperPasswordTemporarily(unlockLskfBasedProtector.syntheticPassword.deriveGkPassword())).build();
                }
                sendCredentialsOnUnlockIfRequired(lockscreenCredential, i3);
                if (DualDarManager.isOnDeviceOwner(i3)) {
                    DualDarManager.getInstance(this.mContext).cancelDataLock(i3);
                }
                int i4 = this.mStorage.getInt("last-failed-count", 0, i3);
                if (i4 >= 5) {
                    this.mStorage.addLog(1, "Verify Success, History of " + i4 + " failures.\n");
                }
                this.mStorage.setInt("last-failed-count", 0, i3);
                updateVerifierIfNeeded(lockscreenCredential.getCredential());
            } else if (verifyCredentialResponse.getResponseCode() == 1) {
                int currentFailedPasswordAttempts = getCurrentFailedPasswordAttempts(i3) + 1;
                this.mStorage.setInt("last-failed-count", currentFailedPasswordAttempts, i3);
                this.mStorage.addLog(1, "Too many failed, fail count = " + currentFailedPasswordAttempts + ", timeout = " + verifyCredentialResponse.getTimeout());
                this.mStorage.uploadLogFile(1);
                if (verifyCredentialResponse.getTimeout() > 0) {
                    requireStrongAuth(8, i3);
                }
            }
            LockDetectionTracker.getInstance(this.mContext).reportLockDetection(verifyCredentialResponse, lockscreenCredential.getType());
            return verifyCredentialResponse;
        }
    }

    public VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential lockscreenCredential, int i, int i2) {
        checkPasswordReadPermission();
        Slogf.m96i("LockSettingsService", "Verifying tied profile challenge for user %d", Integer.valueOf(i));
        if (!isProfileWithUnifiedLock(i)) {
            throw new IllegalArgumentException("User id must be managed/clone profile with unified lock");
        }
        VerifyCredentialResponse doVerifyCredential = doVerifyCredential(lockscreenCredential, this.mUserManager.getProfileParent(i).id, null, i2);
        if (doVerifyCredential.getResponseCode() != 0) {
            return doVerifyCredential;
        }
        try {
            try {
                return doVerifyCredential(getDecryptedPasswordForTiedProfile(i), i, null, i2);
            } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                Slog.e("LockSettingsService", "Failed to decrypt child profile key", e);
                throw new IllegalStateException("Unable to get tied profile token");
            }
        } finally {
            scheduleGc();
        }
    }

    public final void setUserPasswordMetrics(LockscreenCredential lockscreenCredential, int i) {
        synchronized (this) {
            this.mUserPasswordMetrics.put(i, PasswordMetrics.computeForCredential(lockscreenCredential));
        }
    }

    public PasswordMetrics getUserPasswordMetrics(int i) {
        PasswordMetrics passwordMetrics;
        if (!isUserSecure(i)) {
            return new PasswordMetrics(-1);
        }
        synchronized (this) {
            passwordMetrics = (PasswordMetrics) this.mUserPasswordMetrics.get(i);
        }
        return passwordMetrics;
    }

    public final PasswordMetrics loadPasswordMetrics(SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        synchronized (this.mSpManager) {
            if (!isUserSecure(i)) {
                return null;
            }
            return this.mSpManager.getPasswordMetrics(syntheticPassword, getCurrentLskfBasedProtectorId(i), i);
        }
    }

    public final void notifyPasswordChanged(final LockscreenCredential lockscreenCredential, final int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        if (isEnterpriseUser(i)) {
            final PasswordMetrics computeForCredential = PasswordMetrics.computeForCredential(lockscreenCredential);
            this.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    LockSettingsService.this.lambda$notifyPasswordChanged$17(computeForCredential, i);
                }
            });
        } else {
            this.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    LockSettingsService.this.lambda$notifyPasswordChanged$18(lockscreenCredential, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPasswordChanged$17(PasswordMetrics passwordMetrics, int i) {
        this.mInjector.getDevicePolicyManager().reportPasswordChanged(passwordMetrics, i);
        ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class)).reportPasswordChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPasswordChanged$18(LockscreenCredential lockscreenCredential, int i) {
        this.mInjector.getDevicePolicyManager().reportPasswordChanged(PasswordMetrics.computeForCredential(lockscreenCredential), i);
        ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class)).reportPasswordChanged(i);
    }

    public final void createNewUser(int i, int i2) {
        synchronized (this.mUserCreationAndRemovalLock) {
            if (!this.mThirdPartyAppsStarted) {
                Slogf.m96i("LockSettingsService", "Delaying locksettings state creation for user %d until third-party apps are started", Integer.valueOf(i));
                this.mEarlyCreatedUsers.put(i, i2);
                this.mEarlyRemovedUsers.delete(i);
            } else {
                removeStateForReusedUserIdIfNecessary(i, i2);
                initializeSyntheticPassword(i);
            }
        }
    }

    public void notifyPasswordChangedForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) {
        if (isEnterpriseUser(i)) {
            notifyPasswordChanged(lockscreenCredential, i);
        }
    }

    public final void removeUser(int i) {
        synchronized (this.mUserCreationAndRemovalLock) {
            if (!this.mThirdPartyAppsStarted) {
                Slogf.m96i("LockSettingsService", "Delaying locksettings state removal for user %d until third-party apps are started", Integer.valueOf(i));
                if (this.mEarlyCreatedUsers.indexOfKey(i) >= 0) {
                    this.mEarlyCreatedUsers.delete(i);
                } else {
                    this.mEarlyRemovedUsers.put(i, -1);
                }
                return;
            }
            Slogf.m96i("LockSettingsService", "Removing state for user %d", Integer.valueOf(i));
            removeUserState(i);
        }
    }

    public final void removeUserState(int i) {
        removeBiometricsForUser(i);
        this.mSpManager.removeUser(getGateKeeperService(), i);
        this.mStrongAuth.removeUser(i);
        AndroidKeyStoreMaintenance.onUserRemoved(i);
        this.mManagedProfilePasswordCache.removePassword(i);
        gateKeeperClearSecureUserId(i);
        removeKeystoreProfileKey(i);
        this.mStorage.removeUser(i);
    }

    public final void removeKeystoreProfileKey(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        String str = "profile_key_name_encrypt_" + i;
        String str2 = "profile_key_name_decrypt_" + i;
        try {
            if (this.mJavaKeyStore.containsAlias(str) || this.mJavaKeyStore.containsAlias(str2)) {
                Slogf.m96i("LockSettingsService", "Removing keystore profile key for user %d", Integer.valueOf(i));
                this.mJavaKeyStore.deleteEntry(str);
                this.mJavaKeyStore.deleteEntry(str2);
            }
        } catch (KeyStoreException e) {
            Slogf.m93e("LockSettingsService", e, "Error removing keystore profile key for user %d", Integer.valueOf(i));
        }
    }

    public void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
        checkPasswordReadPermission();
        this.mStrongAuth.registerStrongAuthTracker(iStrongAuthTracker);
    }

    public void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
        checkPasswordReadPermission();
        this.mStrongAuth.unregisterStrongAuthTracker(iStrongAuthTracker);
    }

    public void requireStrongAuth(int i, int i2) {
        checkWritePermission();
        this.mStrongAuth.requireStrongAuth(i, i2);
    }

    public void reportSuccessfulBiometricUnlock(boolean z, int i) {
        checkBiometricPermission();
        this.mStrongAuth.reportSuccessfulBiometricUnlock(z, i);
    }

    public void scheduleNonStrongBiometricIdleTimeout(int i) {
        checkBiometricPermission();
        this.mStrongAuth.scheduleNonStrongBiometricIdleTimeout(i);
    }

    public void userPresent(int i) {
        checkWritePermission();
        this.mStrongAuth.reportUnlock(i);
        if (isSupportForgotPassword() && i == 0) {
            updateExpireTimeForPrev(true);
        }
    }

    public int getStrongAuthForUser(int i) {
        checkPasswordReadPermission();
        return this.mStrongAuthTracker.getStrongAuthForUser(i);
    }

    public final boolean isCallerShell() {
        int callingUid = Binder.getCallingUid();
        return callingUid == 2000 || callingUid == 0;
    }

    public final void enforceShell() {
        if (!isCallerShell()) {
            throw new SecurityException("Caller must be shell");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        enforceShell();
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        Object[] objArr = new Object[3];
        objArr[0] = ArrayUtils.isEmpty(strArr) ? "" : strArr[0];
        objArr[1] = Integer.valueOf(callingPid);
        objArr[2] = Integer.valueOf(callingUid);
        Slogf.m96i("LockSettingsService", "Executing shell command '%s'; callingPid=%d, callingUid=%d", objArr);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            LockSettingsShellCommand lockSettingsShellCommand = new LockSettingsShellCommand(new LockPatternUtils(this.mContext), this.mContext, callingPid, callingUid);
            lockSettingsShellCommand.setCallback(this.mShellCommandCallback);
            lockSettingsShellCommand.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void initRecoveryServiceWithSigFile(String str, byte[] bArr, byte[] bArr2) {
        this.mRecoverableKeyStoreManager.initRecoveryServiceWithSigFile(str, bArr, bArr2);
    }

    public KeyChainSnapshot getKeyChainSnapshot() {
        return this.mRecoverableKeyStoreManager.getKeyChainSnapshot();
    }

    public void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) {
        this.mRecoverableKeyStoreManager.setSnapshotCreatedPendingIntent(pendingIntent);
    }

    public void setServerParams(byte[] bArr) {
        this.mRecoverableKeyStoreManager.setServerParams(bArr);
    }

    public void setRecoveryStatus(String str, int i) {
        this.mRecoverableKeyStoreManager.setRecoveryStatus(str, i);
    }

    public Map getRecoveryStatus() {
        return this.mRecoverableKeyStoreManager.getRecoveryStatus();
    }

    public void setRecoverySecretTypes(int[] iArr) {
        this.mRecoverableKeyStoreManager.setRecoverySecretTypes(iArr);
    }

    public int[] getRecoverySecretTypes() {
        return this.mRecoverableKeyStoreManager.getRecoverySecretTypes();
    }

    public byte[] startRecoverySessionWithCertPath(String str, String str2, RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, List list) {
        return this.mRecoverableKeyStoreManager.startRecoverySessionWithCertPath(str, str2, recoveryCertPath, bArr, bArr2, list);
    }

    public Map recoverKeyChainSnapshot(String str, byte[] bArr, List list) {
        return this.mRecoverableKeyStoreManager.recoverKeyChainSnapshot(str, bArr, list);
    }

    public void closeSession(String str) {
        this.mRecoverableKeyStoreManager.closeSession(str);
    }

    public void removeKey(String str) {
        this.mRecoverableKeyStoreManager.removeKey(str);
    }

    public String generateKey(String str) {
        return this.mRecoverableKeyStoreManager.generateKey(str);
    }

    public String generateKeyWithMetadata(String str, byte[] bArr) {
        return this.mRecoverableKeyStoreManager.generateKeyWithMetadata(str, bArr);
    }

    public String importKey(String str, byte[] bArr) {
        return this.mRecoverableKeyStoreManager.importKey(str, bArr);
    }

    public String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) {
        return this.mRecoverableKeyStoreManager.importKeyWithMetadata(str, bArr, bArr2);
    }

    public String getKey(String str) {
        return this.mRecoverableKeyStoreManager.getKey(str);
    }

    public RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        return this.mRecoverableKeyStoreManager.startRemoteLockscreenValidation(this);
    }

    public RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) {
        return this.mRecoverableKeyStoreManager.validateRemoteLockscreen(bArr, this);
    }

    public class GateKeeperDiedRecipient implements IBinder.DeathRecipient {
        public GateKeeperDiedRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            LockSettingsService.this.mGateKeeperService.asBinder().unlinkToDeath(this, 0);
            LockSettingsService.this.mGateKeeperService = null;
        }
    }

    public final synchronized IGateKeeperService getGateKeeperService() {
        IGateKeeperService iGateKeeperService = this.mGateKeeperService;
        if (iGateKeeperService != null) {
            return iGateKeeperService;
        }
        IBinder service = ServiceManager.getService("android.service.gatekeeper.IGateKeeperService");
        if (service != null) {
            try {
                service.linkToDeath(new GateKeeperDiedRecipient(), 0);
            } catch (RemoteException e) {
                Slog.w("LockSettingsService", " Unable to register death recipient", e);
            }
            IGateKeeperService asInterface = IGateKeeperService.Stub.asInterface(service);
            this.mGateKeeperService = asInterface;
            return asInterface;
        }
        Slog.e("LockSettingsService", "Unable to acquire GateKeeperService");
        return null;
    }

    public final void gateKeeperClearSecureUserId(int i) {
        try {
            getGateKeeperService().clearSecureUserId(i);
        } catch (RemoteException e) {
            Slog.w("LockSettingsService", "Failed to clear SID", e);
        }
    }

    public final void onSyntheticPasswordCreated(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        onSyntheticPasswordKnown(i, syntheticPassword, true);
    }

    public final void onSyntheticPasswordUnlocked(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        onSyntheticPasswordKnown(i, syntheticPassword, false);
    }

    public final void onSyntheticPasswordKnown(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        if (this.mInjector.isGsiRunning()) {
            Slog.w("LockSettingsService", "Running in GSI; skipping calls to AuthSecret and RebootEscrow");
        } else {
            this.mRebootEscrowManager.callToRebootEscrowIfNeeded(i, syntheticPassword.getVersion(), syntheticPassword.getSyntheticPassword());
            callToAuthSecretIfNeeded(i, syntheticPassword, z);
        }
    }

    public final void callToAuthSecretIfNeeded(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        UserInfo userInfo;
        byte[] readVendorAuthSecret;
        byte[] bArr;
        byte[] bArr2;
        if (this.mAuthSecretService == null || (userInfo = this.mInjector.getUserManagerInternal().getUserInfo(i)) == null) {
            return;
        }
        if (this.mInjector.isHeadlessSystemUserMode()) {
            if (!this.mInjector.isMainUserPermanentAdmin() || !userInfo.isFull()) {
                return;
            }
            if (z) {
                if (userInfo.isMain()) {
                    Slog.i("LockSettingsService", "Generating new vendor auth secret and storing for user: " + i);
                    bArr2 = SecureRandomUtils.randomBytes(32);
                    synchronized (this.mHeadlessAuthSecretLock) {
                        this.mAuthSecret = bArr2;
                    }
                } else {
                    synchronized (this.mHeadlessAuthSecretLock) {
                        bArr = this.mAuthSecret;
                    }
                    if (bArr == null) {
                        Slog.e("LockSettingsService", "Creating non-main user " + i + " but vendor auth secret is not in memory");
                        return;
                    }
                    bArr2 = bArr;
                }
                this.mSpManager.writeVendorAuthSecret(bArr2, syntheticPassword, i);
                readVendorAuthSecret = bArr2;
            } else {
                readVendorAuthSecret = this.mSpManager.readVendorAuthSecret(syntheticPassword, i);
                if (readVendorAuthSecret == null) {
                    Slog.e("LockSettingsService", "Unable to read vendor auth secret for user: " + i);
                    return;
                }
                synchronized (this.mHeadlessAuthSecretLock) {
                    this.mAuthSecret = readVendorAuthSecret;
                }
            }
        } else if (i != 0) {
            return;
        } else {
            readVendorAuthSecret = syntheticPassword.deriveVendorAuthSecret();
        }
        Slog.i("LockSettingsService", "Sending vendor auth secret to IAuthSecret HAL as user: " + i);
        try {
            this.mAuthSecretService.setPrimaryUserCredential(readVendorAuthSecret);
        } catch (RemoteException e) {
            Slog.w("LockSettingsService", "Failed to send vendor auth secret to IAuthSecret HAL", e);
        }
    }

    public SyntheticPasswordManager.SyntheticPassword initializeSyntheticPassword(int i) {
        SyntheticPasswordManager.SyntheticPassword newSyntheticPassword;
        SDPLog.m43i("Initialize sp for user " + i);
        synchronized (this.mSpManager) {
            Slogf.m96i("LockSettingsService", "Initializing synthetic password for user %d", Integer.valueOf(i));
            Preconditions.checkState(getCurrentLskfBasedProtectorId(i) == 0, "Cannot reinitialize SP");
            newSyntheticPassword = this.mSpManager.newSyntheticPassword(i);
            setCurrentLskfBasedProtectorId(this.mSpManager.createLskfBasedProtector(getGateKeeperService(), LockscreenCredential.createNone(), newSyntheticPassword, i), i);
            setUserKeyProtection(i, newSyntheticPassword.deriveFileBasedEncryptionKey());
            onSyntheticPasswordCreated(i, newSyntheticPassword);
            Slogf.m96i("LockSettingsService", "Successfully initialized synthetic password for user %d", Integer.valueOf(i));
        }
        return newSyntheticPassword;
    }

    public long getCurrentLskfBasedProtectorId(int i) {
        return getLong("sp-handle", 0L, i);
    }

    public final void setCurrentLskfBasedProtectorId(long j, int i) {
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        setLong("sp-handle", j, i);
        setLong("prev-sp-handle", currentLskfBasedProtectorId, i);
        setLong("sp-handle-ts", System.currentTimeMillis(), i);
    }

    public final boolean isSupportForgotPassword() {
        String str = SystemProperties.get("ro.organization_owned", (String) null);
        return str == null || !str.equals("true");
    }

    public boolean isSupportWeaver() {
        return SystemProperties.getBoolean("security.securehw.available", false);
    }

    public final long getBackupLskfBasedProtectorId(int i) {
        return getLong("backup-protector-id", 0L, i);
    }

    public final void setBackupLskfBasedProtectorId(long j, int i) {
        Slog.e("LockSettingsService", String.format("!@ setBackupLskfBasedProtectorId : %016x", Long.valueOf(j)));
        if (getBackupLskfBasedProtectorId(i) == j) {
            return;
        }
        setLong("backup-protector-id", j, i);
        setLong("backup-protector-ts", System.currentTimeMillis(), i);
        if (0 == j) {
            setLong("backup-expiration-ts", 0L, i);
        } else {
            setLong("backup-expiration-ts", System.currentTimeMillis() + 259200000, i);
        }
        setLong("prev.attempts.count", 0L, 0);
    }

    public final long updateExpireTimeForPrev(boolean z) {
        if (0 == getBackupLskfBasedProtectorId(0)) {
            return 0L;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long j = getLong("backup-expiration-ts", 0L, 0);
        if (!z || currentTimeMillis < j) {
            return j;
        }
        expirePreviousData();
        return 0L;
    }

    public long getExpireTimeForPrev() {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("expirePreviousData requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        return updateExpireTimeForPrev(false);
    }

    public void expirePreviousData() {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("expirePreviousData requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        long backupLskfBasedProtectorId = getBackupLskfBasedProtectorId(0);
        if (0 == backupLskfBasedProtectorId) {
            return;
        }
        synchronized (this.mSpManager) {
            setBackupLskfBasedProtectorId(0L, 0);
            this.mSpManager.destroyLskfBasedProtector(backupLskfBasedProtectorId, 0);
        }
        LockPatternUtils.invalidateCredentialTypeCache();
    }

    public final long storeGatekeeperPasswordTemporarily(byte[] bArr) {
        final long j;
        synchronized (this.mGatekeeperPasswords) {
            j = 0;
            while (true) {
                if (j != 0) {
                    if (this.mGatekeeperPasswords.get(j) == null) {
                        this.mGatekeeperPasswords.put(j, bArr);
                    }
                }
                j = SecureRandomUtils.randomLong();
            }
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                LockSettingsService.this.lambda$storeGatekeeperPasswordTemporarily$19(j);
            }
        }, 600000L);
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$storeGatekeeperPasswordTemporarily$19(long j) {
        synchronized (this.mGatekeeperPasswords) {
            if (this.mGatekeeperPasswords.get(j) != null) {
                Slogf.m88d("LockSettingsService", "Cached Gatekeeper password with handle %016x has expired", Long.valueOf(j));
                this.mGatekeeperPasswords.remove(j);
            }
        }
    }

    public final void onCredentialVerified(SyntheticPasswordManager.SyntheticPassword syntheticPassword, PasswordMetrics passwordMetrics, int i) {
        if (UserManager.isVirtualUserId(i)) {
            this.mSdpLockSettings.migrateWithAuthToken(syntheticPassword, i);
            return;
        }
        if (passwordMetrics != null) {
            synchronized (this) {
                this.mUserPasswordMetrics.put(i, passwordMetrics);
            }
        }
        unlockKeystore(syntheticPassword.deriveKeyStorePassword(), i);
        unlockUserKey(i, syntheticPassword);
        lambda$setLockCredentialWithToken$21(i);
        if (!this.mDualDarLockSettings.isDualDARUser(i) || DualDarManager.isOnDeviceOwner(i)) {
            activateEscrowTokens(syntheticPassword, i);
        }
        if (syntheticPassword.getSecureFolderAuthToken()) {
            SDPLog.m38d("onCredentialVerified : unlockSecureFolderWithToken is true.");
            onSyntheticPasswordUnlocked(i, syntheticPassword);
            return;
        }
        if (isCredentialSharableWithParent(i)) {
            if (getSeparateProfileChallengeEnabledInternal(i)) {
                if (isPwdChangeRequested(i)) {
                    Log.d("LockSettingsService", "Password change requested so avoid setDeviceLockedForUser false");
                } else {
                    setDeviceUnlockedForUser(i);
                }
            } else {
                this.mStrongAuth.reportUnlock(i);
            }
        }
        this.mStrongAuth.reportSuccessfulStrongAuthUnlock(i);
        onSyntheticPasswordUnlocked(i, syntheticPassword);
    }

    public final boolean isPwdChangeRequested(int i) {
        try {
            IPasswordPolicy asInterface = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
            if (asInterface != null) {
                return asInterface.isChangeRequestedAsUser(i) > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final void setDeviceUnlockedForUser(int i) {
        ((TrustManager) this.mContext.getSystemService(TrustManager.class)).setDeviceLockedForUser(i, false);
    }

    public final long setLockCredentialWithSpLocked(LockscreenCredential lockscreenCredential, SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        return setLockCredentialWithSpLocked(lockscreenCredential, null, 0L, syntheticPassword, i);
    }

    public final long setLockCredentialWithSpLocked(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, long j, SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        Slogf.m96i("LockSettingsService", "Changing lockscreen credential of user %d; newCredentialType=%s\n", Integer.valueOf(i), LockPatternUtils.credentialTypeToString(lockscreenCredential.getType()));
        int credentialTypeInternal = getCredentialTypeInternal(i);
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        long createLskfBasedProtector = this.mSpManager.createLskfBasedProtector(getGateKeeperService(), lockscreenCredential2, j, lockscreenCredential, syntheticPassword, i);
        Map map = null;
        if (!lockscreenCredential.isNone()) {
            if (this.mSpManager.hasSidForUser(i)) {
                this.mSpManager.verifyChallenge(getGateKeeperService(), syntheticPassword, 0L, i);
            } else {
                this.mSpManager.newSidForUser(getGateKeeperService(), syntheticPassword, i);
                this.mSpManager.verifyChallenge(getGateKeeperService(), syntheticPassword, 0L, i);
                setKeystorePassword(syntheticPassword.deriveKeyStorePassword(), i);
            }
        } else {
            Map decryptedPasswordsForAllTiedProfiles = getDecryptedPasswordsForAllTiedProfiles(i);
            this.mSpManager.clearSidForUser(i);
            gateKeeperClearSecureUserId(i);
            unlockUserKey(i, syntheticPassword);
            unlockKeystore(syntheticPassword.deriveKeyStorePassword(), i);
            setKeystorePassword(null, i);
            removeBiometricsForUser(i);
            map = decryptedPasswordsForAllTiedProfiles;
        }
        setCurrentLskfBasedProtectorId(createLskfBasedProtector, i);
        LockPatternUtils.invalidateCredentialTypeCache();
        synchronizeUnifiedWorkChallengeForProfiles(i, map);
        setUserPasswordMetrics(lockscreenCredential, i);
        this.mManagedProfilePasswordCache.removePassword(i);
        if (credentialTypeInternal != -1) {
            this.mSpManager.destroyAllWeakTokenBasedProtectors(i);
        }
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                ((LockscreenCredential) ((Map.Entry) it.next()).getValue()).zeroize();
            }
        }
        if (isSupportForgotPassword() && credentialTypeInternal != -1 && lockscreenCredential.getType() != -1 && i == 0 && getBackupLskfBasedProtectorId(i) == 0) {
            setBackupLskfBasedProtectorId(currentLskfBasedProtectorId, i);
        } else {
            this.mSpManager.destroyLskfBasedProtector(currentLskfBasedProtectorId, i);
        }
        Slogf.m96i("LockSettingsService", "Successfully changed lockscreen credential of user %d", Integer.valueOf(i));
        return createLskfBasedProtector;
    }

    public final void removeBiometricsForUser(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LockSettingsService.this.lambda$removeBiometricsForUser$20(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeBiometricsForUser$20(int i) {
        removeAllFingerprintForUser(i);
        removeAllFaceForUser(i);
        Consumer consumer = this.mMaintenanceModeCallback;
        if (consumer != null) {
            consumer.accept(Boolean.TRUE);
            this.mMaintenanceModeCallback = null;
        }
    }

    public final void removeAllFingerprintForUser(int i) {
        FingerprintManager fingerprintManager = this.mInjector.getFingerprintManager();
        if (fingerprintManager != null && fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints(i)) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            fingerprintManager.removeAll(i, fingerprintManagerRemovalCallback(countDownLatch));
            try {
                countDownLatch.await(10000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Slog.e("LockSettingsService", "Latch interrupted when removing fingerprint", e);
            }
        }
    }

    public final void removeAllFaceForUser(int i) {
        SemBioFaceManager createInstance;
        FaceManager faceManager = this.mInjector.getFaceManager();
        if (faceManager != null && faceManager.isHardwareDetected()) {
            if (faceManager.hasEnrolledTemplates(i)) {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                faceManager.removeAll(i, faceManagerRemovalCallback(countDownLatch));
                try {
                    countDownLatch.await(10000L, TimeUnit.MILLISECONDS);
                    return;
                } catch (InterruptedException e) {
                    Slog.e("LockSettingsService", "Latch interrupted when removing face", e);
                    return;
                }
            }
            return;
        }
        if (this.mContext.getPackageManager().hasSystemFeature("com.samsung.android.bio.face") && SemPersonaManager.isKnoxId(i) && (createInstance = SemBioFaceManager.createInstance(this.mContext)) != null && createInstance.isHardwareDetected() && createInstance.hasEnrolledFaces(i)) {
            createInstance.setActiveUser(i);
            CountDownLatch countDownLatch2 = new CountDownLatch(1);
            createInstance.remove(new SemBioFace(new Face((CharSequence) null, 0, 0L)), i, semBioFaceManagerRemovalCallback(countDownLatch2));
            try {
                countDownLatch2.await(10000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                Slog.e("LockSettingsService", "Latch interrupted when removing face", e2);
            }
        }
    }

    public final FingerprintManager.RemovalCallback fingerprintManagerRemovalCallback(final CountDownLatch countDownLatch) {
        return new FingerprintManager.RemovalCallback() { // from class: com.android.server.locksettings.LockSettingsService.5
            public void onRemovalError(Fingerprint fingerprint, int i, CharSequence charSequence) {
                Slog.e("LockSettingsService", "Unable to remove fingerprint, error: " + ((Object) charSequence));
                countDownLatch.countDown();
            }

            public void onRemovalSucceeded(Fingerprint fingerprint, int i) {
                if (i == 0) {
                    countDownLatch.countDown();
                }
            }
        };
    }

    public final FaceManager.RemovalCallback faceManagerRemovalCallback(final CountDownLatch countDownLatch) {
        return new FaceManager.RemovalCallback() { // from class: com.android.server.locksettings.LockSettingsService.6
            public void onRemovalError(Face face, int i, CharSequence charSequence) {
                Slog.e("LockSettingsService", "Unable to remove face, error: " + ((Object) charSequence));
                countDownLatch.countDown();
            }

            public void onRemovalSucceeded(Face face, int i) {
                if (i == 0) {
                    countDownLatch.countDown();
                }
            }
        };
    }

    public final SemBioFaceManager.RemovalCallback semBioFaceManagerRemovalCallback(final CountDownLatch countDownLatch) {
        return new SemBioFaceManager.RemovalCallback() { // from class: com.android.server.locksettings.LockSettingsService.7
            public void onRemovalError(SemBioFace semBioFace, int i, CharSequence charSequence) {
                Slog.e("LockSettingsService", String.format("Can't remove face %d. Reason: %s", Integer.valueOf(semBioFace.getFaceId()), charSequence));
                countDownLatch.countDown();
            }

            public void onRemovalSucceeded(SemBioFace semBioFace) {
                countDownLatch.countDown();
            }
        };
    }

    public byte[] getHashFactor(LockscreenCredential lockscreenCredential, int i) {
        checkPasswordReadPermission();
        try {
            Slogf.m88d("LockSettingsService", "Getting password history hash factor for user %d", Integer.valueOf(i));
            if (isEnterpriseUser(i)) {
                lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
            }
            if (isProfileWithUnifiedLock(i)) {
                lockscreenCredential = getDecryptedPasswordForTiedProfile(i);
            }
            LockscreenCredential lockscreenCredential2 = lockscreenCredential;
            synchronized (this.mSpManager) {
                SyntheticPasswordManager.SyntheticPassword syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), lockscreenCredential2, i, null).syntheticPassword;
                if (syntheticPassword == null) {
                    Slog.w("LockSettingsService", "Current credential is incorrect");
                    return null;
                }
                return syntheticPassword.derivePasswordHashFactor();
            }
        } catch (Exception e) {
            Slog.e("LockSettingsService", "Failed to get work profile credential", e);
            return null;
        } finally {
            scheduleGc();
        }
    }

    public final long addEscrowToken(byte[] bArr, int i, int i2, LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        SyntheticPasswordManager.SyntheticPassword syntheticPassword;
        long addPendingToken;
        SDPLog.m43i("add escrow token for user " + i2);
        SDPLog.m46p(KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "userId", Integer.valueOf(i2));
        if (UserManager.isVirtualUserId(i2)) {
            SDPLog.m43i("add escrow token for virtual user " + i2);
            return this.mDarVirtualLock.addEscrowToken(bArr, i, i2, escrowTokenStateChangeCallback);
        }
        Slogf.m96i("LockSettingsService", "Adding escrow token for user %d", Integer.valueOf(i2));
        synchronized (this.mSpManager) {
            if (isUserSecure(i2)) {
                syntheticPassword = null;
            } else {
                syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i2), LockscreenCredential.createNone(), i2, null).syntheticPassword;
                SDPLog.m43i("addEscrowToken: saveEscrowDataIfNeededLocked");
                this.mDarLockSettings.saveEscrowDataIfNeededLocked(syntheticPassword, i2);
            }
            disableEscrowTokenOnNonManagedDevicesIfNeeded(i2);
            SDPLog.m43i("addEscrowToken: restoreEscrowDataIfNeededLocked");
            this.mDarLockSettings.restoreEscrowDataIfNeededLocked(i2);
            if (!this.mSpManager.hasEscrowData(i2)) {
                throw new SecurityException("Escrow token is disabled on the current user");
            }
            addPendingToken = this.mSpManager.addPendingToken(bArr, i, i2, escrowTokenStateChangeCallback);
            if (syntheticPassword == null) {
                Slogf.m96i("LockSettingsService", "Escrow token %016x will be activated when user is unlocked", Long.valueOf(addPendingToken));
            } else if (isDualDarAuthUserId(i2)) {
                byte[] pendingTokenForDualDAR = this.mDualDarLockSettings.getPendingTokenForDualDAR(i2, addPendingToken);
                if (this.mSpManager.createTokenBasedProtector(addPendingToken, syntheticPassword, i2)) {
                    this.mDualDarLockSettings.activateEscrowTokenForDualDAR(addPendingToken, i2, pendingTokenForDualDAR, null);
                }
            } else {
                Slogf.m96i("LockSettingsService", "Immediately activating escrow token %016x", Long.valueOf(addPendingToken));
                this.mSpManager.createTokenBasedProtector(addPendingToken, syntheticPassword, i2);
            }
        }
        return addPendingToken;
    }

    public final void activateEscrowTokens(SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        synchronized (this.mSpManager) {
            disableEscrowTokenOnNonManagedDevicesIfNeeded(i);
            Iterator it = this.mSpManager.getPendingTokensForUser(i).iterator();
            while (it.hasNext()) {
                long longValue = ((Long) it.next()).longValue();
                Slogf.m96i("LockSettingsService", "Activating escrow token %016x for user %d", Long.valueOf(longValue), Integer.valueOf(i));
                this.mSpManager.createTokenBasedProtector(longValue, syntheticPassword, i);
            }
        }
    }

    public final boolean isEscrowTokenActive(long j, int i) {
        boolean protectorExists;
        synchronized (this.mSpManager) {
            protectorExists = this.mSpManager.protectorExists(j, i);
        }
        return protectorExists;
    }

    public boolean hasPendingEscrowToken(int i) {
        boolean z;
        checkPasswordReadPermission();
        synchronized (this.mSpManager) {
            z = !this.mSpManager.getPendingTokensForUser(i).isEmpty();
        }
        return z;
    }

    public final boolean removeEscrowToken(long j, int i) {
        synchronized (this.mSpManager) {
            if (j == getCurrentLskfBasedProtectorId(i)) {
                Slog.w("LockSettingsService", "Escrow token handle equals LSKF-based protector ID");
                return false;
            }
            if (this.mSpManager.removePendingToken(j, i)) {
                return true;
            }
            if (!this.mSpManager.protectorExists(j, i)) {
                return false;
            }
            this.mSpManager.destroyTokenBasedProtector(j, i);
            if (isDualDarAuthUserId(i)) {
                Slog.d("LockSettingsService", "Clearing activated reset pwd token for DualDAR user: " + i);
                DualDARController.getInstance(this.mContext).clearResetPasswordToken(this.mDualDarAuthUtils.getMainUserId(i), j);
            }
            return true;
        }
    }

    public final boolean setLockCredentialWithToken(LockscreenCredential lockscreenCredential, long j, byte[] bArr, final int i) {
        SDPLog.m43i("Set lock credential with token for user " + i);
        SDPLog.m46p("credential", lockscreenCredential.getCredential(), "type", Integer.valueOf(lockscreenCredential.getType()), "tokenHandle", Long.valueOf(j), KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "userId", Integer.valueOf(i));
        if (UserManager.isVirtualUserId(i)) {
            return this.mDarVirtualLock.setLockCredentialWithToken(lockscreenCredential, j, bArr, i);
        }
        synchronized (this.mSpManager) {
            this.mDarLockSettings.restoreEscrowDataIfNeededLocked(i);
            if (!this.mSpManager.hasEscrowData(i)) {
                throw new SecurityException("Escrow token is disabled on the current user");
            }
            if (!isEscrowTokenActive(j, i)) {
                Slog.e("LockSettingsService", "Unknown or unactivated token: " + Long.toHexString(j));
                return false;
            }
            boolean lockCredentialWithTokenInternalLocked = setLockCredentialWithTokenInternalLocked(lockscreenCredential, j, bArr, i);
            if (lockCredentialWithTokenInternalLocked) {
                synchronized (this.mSeparateChallengeLock) {
                    setSeparateProfileChallengeEnabledLocked(i, true, null);
                }
                if (lockscreenCredential.isNone()) {
                    this.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            LockSettingsService.this.lambda$setLockCredentialWithToken$21(i);
                        }
                    });
                }
                notifyPasswordChanged(lockscreenCredential, i);
                notifySeparateProfileChallengeChanged(i);
            }
            SDPLog.m38d(String.format("Result of setting credential with token for user %d : %s", Integer.valueOf(i), Boolean.valueOf(lockCredentialWithTokenInternalLocked)));
            return lockCredentialWithTokenInternalLocked;
        }
    }

    public final boolean setLockCredentialWithTokenInternalLocked(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
        Slogf.m96i("LockSettingsService", "Resetting lockscreen credential of user %d using escrow token %016x", Integer.valueOf(i), Long.valueOf(j));
        if (isDualDarAuthUserId(i)) {
            try {
                if (!this.mDualDarLockSettings.setLockCredentialWithTokenInternalForDualDAR(lockscreenCredential, j, bArr, i)) {
                    Log.e("LockSettingsService", "Dual DAR Client failed to reset password with token for user: " + i);
                    return false;
                }
            } catch (RemoteException e) {
                DDLog.m34e("LockSettingsService", "Exception : " + e.getMessage(), new Object[0]);
            }
        }
        SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(getGateKeeperService(), j, bArr, i);
        if (unlockTokenBasedProtector.syntheticPassword == null) {
            Slog.w("LockSettingsService", "Invalid escrow token supplied");
            return false;
        }
        if (unlockTokenBasedProtector.gkResponse.getResponseCode() != 0) {
            Slog.e("LockSettingsService", "Obsolete token: synthetic password decrypted but it fails GK verification.");
            return false;
        }
        onSyntheticPasswordUnlocked(i, unlockTokenBasedProtector.syntheticPassword);
        setLockCredentialWithSpLocked(lockscreenCredential, unlockTokenBasedProtector.syntheticPassword, i);
        return true;
    }

    public final boolean unlockUserWithToken(long j, byte[] bArr, int i) {
        synchronized (this.mSpManager) {
            Slogf.m96i("LockSettingsService", "Unlocking user %d using escrow token %016x", Integer.valueOf(i), Long.valueOf(j));
            if (!this.mSpManager.hasEscrowData(i)) {
                Slogf.m105w("LockSettingsService", "Escrow token support is disabled on user %d", Integer.valueOf(i));
                return false;
            }
            SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(getGateKeeperService(), j, bArr, i);
            if (unlockTokenBasedProtector.syntheticPassword == null) {
                Slog.w("LockSettingsService", "Invalid escrow token supplied");
                return false;
            }
            if (i != 0 && SemPersonaManager.isSecureFolderId(i)) {
                unlockTokenBasedProtector.syntheticPassword.setSecureFolderAuthToken(true);
            }
            Slogf.m96i("LockSettingsService", "Unlocked synthetic password for user %d using escrow token", Integer.valueOf(i));
            SyntheticPasswordManager.SyntheticPassword syntheticPassword = unlockTokenBasedProtector.syntheticPassword;
            onCredentialVerified(syntheticPassword, loadPasswordMetrics(syntheticPassword, i), i);
            return true;
        }
    }

    public boolean tryUnlockWithCachedUnifiedChallenge(int i) {
        checkPasswordReadPermission();
        LockscreenCredential retrievePassword = this.mManagedProfilePasswordCache.retrievePassword(i);
        if (retrievePassword == null) {
            if (retrievePassword != null) {
                retrievePassword.close();
            }
            return false;
        }
        try {
            boolean z = doVerifyCredential(retrievePassword, i, null, 0).getResponseCode() == 0;
            retrievePassword.close();
            return z;
        } catch (Throwable th) {
            try {
                retrievePassword.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void removeCachedUnifiedChallenge(int i) {
        checkWritePermission();
        this.mManagedProfilePasswordCache.removePassword(i);
    }

    public static String timestampToString(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(j));
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "LockSettingsService", printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                dumpInternal(printWriter);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void dumpInternal(PrintWriter printWriter) {
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("Current lock settings service state:");
        indentingPrintWriter.println();
        indentingPrintWriter.println(String.format("DO Enabled: %b", Boolean.valueOf(isDeviceOwner(0))));
        indentingPrintWriter.println();
        indentingPrintWriter.println("User State:");
        indentingPrintWriter.increaseIndent();
        List users = this.mUserManager.getUsers();
        for (int i = 0; i < users.size(); i++) {
            int i2 = ((UserInfo) users.get(i)).id;
            indentingPrintWriter.println("User " + i2);
            indentingPrintWriter.increaseIndent();
            synchronized (this.mSpManager) {
                indentingPrintWriter.println(TextUtils.formatSimple("LSKF-based SP protector ID: %016x", new Object[]{Long.valueOf(getCurrentLskfBasedProtectorId(i2))}));
                indentingPrintWriter.println(TextUtils.formatSimple("LSKF last changed: %s (previous protector: %016x)", new Object[]{timestampToString(getLong("sp-handle-ts", 0L, i2)), Long.valueOf(getLong("prev-sp-handle", 0L, i2))}));
                indentingPrintWriter.println(TextUtils.formatSimple("Backup protector: %016x (set : %s, expire : %s)", new Object[]{Long.valueOf(getLong("backup-protector-id", 0L, i2)), timestampToString(getLong("backup-protector-ts", 0L, i2)), timestampToString(getLong("backup-expiration-ts", 0L, i2))}));
            }
            indentingPrintWriter.println(String.format("Secure Mode: %d", Integer.valueOf(this.mDarLockSettings.getSecureMode(i2))));
            try {
                indentingPrintWriter.println(TextUtils.formatSimple("SID: %016x", new Object[]{Long.valueOf(getGateKeeperService().getSecureUserId(i2))}));
            } catch (RemoteException unused) {
            }
            indentingPrintWriter.println("Quality: " + getKeyguardStoredQuality(i2));
            indentingPrintWriter.println("CredentialType: " + LockPatternUtils.credentialTypeToString(getCredentialTypeInternal(i2)));
            indentingPrintWriter.println("SeparateChallenge: " + getSeparateProfileChallengeEnabledInternal(i2));
            Object[] objArr = new Object[1];
            objArr[0] = getUserPasswordMetrics(i2) != null ? "known" : "unknown";
            indentingPrintWriter.println(TextUtils.formatSimple("Metrics: %s", objArr));
            indentingPrintWriter.println("sp-handle-try = " + getSyntheticPasswordTryHandleLocked(i2));
            indentingPrintWriter.println("failed attempt = " + getCurrentFailedPasswordAttempts(i2));
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Keys in namespace:");
        indentingPrintWriter.increaseIndent();
        dumpKeystoreKeys(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Storage:");
        indentingPrintWriter.increaseIndent();
        this.mStorage.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("StrongAuth:");
        indentingPrintWriter.increaseIndent();
        this.mStrongAuth.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("RebootEscrow:");
        indentingPrintWriter.increaseIndent();
        this.mRebootEscrowManager.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("PasswordHandleCount: " + this.mGatekeeperPasswords.size());
        synchronized (this.mUserCreationAndRemovalLock) {
            indentingPrintWriter.println("ThirdPartyAppsStarted: " + this.mThirdPartyAppsStarted);
        }
        indentingPrintWriter.println("LSSLog:");
        indentingPrintWriter.increaseIndent();
        this.mStorage.getLssLog().dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    public final void dumpKeystoreKeys(IndentingPrintWriter indentingPrintWriter) {
        try {
            Enumeration<String> aliases = this.mJavaKeyStore.aliases();
            while (aliases.hasMoreElements()) {
                indentingPrintWriter.println(aliases.nextElement());
            }
        } catch (KeyStoreException e) {
            indentingPrintWriter.println("Unable to get keys: " + e.toString());
            Slog.d("LockSettingsService", "Dump error", e);
        }
    }

    public final void disableEscrowTokenOnNonManagedDevicesIfNeeded(int i) {
        if (UserManager.isVirtualUserId(i)) {
            Slog.i("LockSettingsService.SDP", "Virtual user can have escrow token");
            return;
        }
        if (this.mSpManager.hasAnyEscrowData(i)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
                    if (userManagerInternal.isUserManaged(i)) {
                        if (!this.mDualDarLockSettings.isDualDARUser(i)) {
                            Slog.i("LockSettingsService", "Managed profile can have escrow token");
                            return;
                        } else {
                            if (DualDARController.getInstance(this.mContext).isResetPasswordSupported(i)) {
                                Slog.i("LockSettingsService", "Dual DAR enabled Managed profile can have escrow token");
                                return;
                            }
                            Slog.i("LockSettingsService", "Dual DAR enabled Managed profile doesn't support reset password. Disable escrow");
                        }
                    }
                    if (userManagerInternal.isDeviceManaged()) {
                        Slog.i("LockSettingsService", "Corp-owned device can have escrow token");
                        return;
                    }
                } else if (this.mInjector.getDeviceStateCache().isUserOrganizationManaged(i)) {
                    if (!this.mDualDarLockSettings.isDualDARUser(i)) {
                        Slog.i("LockSettingsService", "Organization managed users can have escrow token");
                        return;
                    } else {
                        if (DualDARController.getInstance(this.mContext).isResetPasswordSupported(i)) {
                            Slog.i("LockSettingsService", "Dual DAR enabled Organization Managed profile can have escrow token");
                            return;
                        }
                        Slog.i("LockSettingsService", "Dual DAR enabled Organization Managed profile doesn't support reset password. Disable escrow");
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (isKnoxAdminActivated(i)) {
                    Slog.i("LockSettingsService", "User with knox admin can have escrow token");
                    return;
                }
                if (!this.mInjector.getDeviceStateCache().isDeviceProvisioned()) {
                    Slog.i("LockSettingsService", "Postpone disabling escrow tokens until device is provisioned");
                } else {
                    if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                        return;
                    }
                    Slogf.m96i("LockSettingsService", "Permanently disabling support for escrow tokens on user %d", Integer.valueOf(i));
                    this.mSpManager.destroyEscrowData(i);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void scheduleGc() {
        BackgroundThread.getHandler().postDelayed(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                LockSettingsService.lambda$scheduleGc$22();
            }
        }, 2000L);
    }

    public static /* synthetic */ void lambda$scheduleGc$22() {
        System.gc();
        System.runFinalization();
        System.gc();
    }

    public class DeviceProvisionedObserver extends ContentObserver {
        public final Uri mDeviceProvisionedUri;
        public boolean mRegistered;

        public DeviceProvisionedObserver() {
            super(null);
            this.mDeviceProvisionedUri = Settings.Global.getUriFor("device_provisioned");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            if (this.mDeviceProvisionedUri.equals(uri)) {
                updateRegistration();
                if (isProvisioned()) {
                    Slog.i("LockSettingsService", "Reporting device setup complete to IGateKeeperService");
                    reportDeviceSetupComplete();
                    clearFrpCredentialIfOwnerNotSecure();
                }
            }
        }

        public void onSystemReady() {
            if (LockPatternUtils.frpCredentialEnabled(LockSettingsService.this.mContext)) {
                updateRegistration();
            } else {
                if (isProvisioned()) {
                    return;
                }
                Slog.i("LockSettingsService", "FRP credential disabled, reporting device setup complete to Gatekeeper immediately");
                reportDeviceSetupComplete();
            }
        }

        public final void reportDeviceSetupComplete() {
            try {
                LockSettingsService.this.getGateKeeperService().reportDeviceSetupComplete();
            } catch (RemoteException e) {
                Slog.e("LockSettingsService", "Failure reporting to IGateKeeperService", e);
            }
        }

        public final void clearFrpCredentialIfOwnerNotSecure() {
            for (UserInfo userInfo : LockSettingsService.this.mUserManager.getUsers()) {
                if (LockPatternUtils.userOwnsFrpCredential(LockSettingsService.this.mContext, userInfo)) {
                    if (LockSettingsService.this.isUserSecure(userInfo.id)) {
                        return;
                    }
                    Slogf.m88d("LockSettingsService", "Clearing FRP credential tied to user %d", Integer.valueOf(userInfo.id));
                    LockSettingsService.this.mStorage.writePersistentDataBlock(0, userInfo.id, 0, null);
                    return;
                }
            }
        }

        public final void updateRegistration() {
            boolean z = !isProvisioned();
            if (z == this.mRegistered) {
                return;
            }
            if (z) {
                LockSettingsService.this.mContext.getContentResolver().registerContentObserver(this.mDeviceProvisionedUri, false, this);
            } else {
                LockSettingsService.this.mContext.getContentResolver().unregisterContentObserver(this);
            }
            this.mRegistered = z;
        }

        public final boolean isProvisioned() {
            return Settings.Global.getInt(LockSettingsService.this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
        }
    }

    public final class LocalService extends LockSettingsInternal {
        public LocalService() {
        }

        public void onThirdPartyAppsStarted() {
            LockSettingsService.this.onThirdPartyAppsStarted();
        }

        public void unlockUserKeyIfUnsecured(int i) {
            LockSettingsService.this.unlockUserKeyIfUnsecured(i);
        }

        public void createNewUser(int i, int i2) {
            LockSettingsService.this.createNewUser(i, i2);
        }

        public void removeUser(int i) {
            LockSettingsService.this.removeUser(i);
        }

        public long addEscrowToken(byte[] bArr, int i, LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
            return LockSettingsService.this.addEscrowToken(bArr, 0, i, escrowTokenStateChangeCallback);
        }

        public boolean removeEscrowToken(long j, int i) {
            return LockSettingsService.this.removeEscrowToken(j, i);
        }

        public boolean isEscrowTokenActive(long j, int i) {
            return LockSettingsService.this.isEscrowTokenActive(j, i);
        }

        public boolean setLockCredentialWithToken(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
            if (!LockSettingsService.this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
                throw new UnsupportedOperationException("This operation requires secure lock screen feature.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Settings.Secure.putIntForUser(LockSettingsService.this.mContext.getContentResolver(), "n_digits_pin_enabled", 0, i);
            } catch (Exception unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!LockSettingsService.this.setLockCredentialWithToken(lockscreenCredential, j, bArr, i)) {
                return false;
            }
            LockSettingsService.this.onPostPasswordChanged(lockscreenCredential, i);
            return true;
        }

        public boolean unlockUserWithToken(long j, byte[] bArr, int i) {
            return LockSettingsService.this.unlockUserWithToken(j, bArr, i);
        }

        public PasswordMetrics getUserPasswordMetrics(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (LockSettingsService.this.isProfileWithUnifiedLock(i)) {
                    Slog.w("LockSettingsService", "Querying password metrics for unified challenge profile: " + i);
                }
                return LockSettingsService.this.getUserPasswordMetrics(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean prepareRebootEscrow() {
            if (!LockSettingsService.this.mRebootEscrowManager.prepareRebootEscrow()) {
                return false;
            }
            LockSettingsService.this.mStrongAuth.requireStrongAuth(64, -1);
            return true;
        }

        public void setRebootEscrowListener(RebootEscrowListener rebootEscrowListener) {
            LockSettingsService.this.mRebootEscrowManager.setRebootEscrowListener(rebootEscrowListener);
        }

        public boolean clearRebootEscrow() {
            if (!LockSettingsService.this.mRebootEscrowManager.clearRebootEscrow()) {
                return false;
            }
            LockSettingsService.this.mStrongAuth.noLongerRequireStrongAuth(64, -1);
            return true;
        }

        public int armRebootEscrow() {
            return LockSettingsService.this.mRebootEscrowManager.armRebootEscrowIfNeeded();
        }

        public void refreshStrongAuthTimeout(int i) {
            LockSettingsService.this.mStrongAuth.refreshStrongAuthTimeout(i);
        }

        public int getCredentialType(int i) {
            return LockSettingsService.this.getCredentialType(i);
        }

        public int getSecureMode(int i) {
            return LockSettingsService.this.mDarLockSettings.getSecureMode(i);
        }

        public void clearStorageForUser(int i) {
            LockSettingsService.this.mDarLockSettings.clearStorageForUser(i);
        }
    }

    public class RebootEscrowCallbacks implements RebootEscrowManager.Callbacks {
        public RebootEscrowCallbacks() {
        }

        @Override // com.android.server.locksettings.RebootEscrowManager.Callbacks
        public boolean isUserSecure(int i) {
            return LockSettingsService.this.isUserSecure(i);
        }

        @Override // com.android.server.locksettings.RebootEscrowManager.Callbacks
        public void onRebootEscrowRestored(byte b, byte[] bArr, int i) {
            SyntheticPasswordManager.SyntheticPassword syntheticPassword = new SyntheticPasswordManager.SyntheticPassword(b);
            syntheticPassword.recreateDirectly(bArr);
            synchronized (LockSettingsService.this.mSpManager) {
                LockSettingsService.this.mSpManager.verifyChallenge(LockSettingsService.this.getGateKeeperService(), syntheticPassword, 0L, i);
            }
            Slogf.m96i("LockSettingsService", "Restored synthetic password for user %d using reboot escrow", Integer.valueOf(i));
            LockSettingsService lockSettingsService = LockSettingsService.this;
            lockSettingsService.onCredentialVerified(syntheticPassword, lockSettingsService.loadPasswordMetrics(syntheticPassword, i), i);
        }
    }

    public final boolean isDeviceOwner(int i) {
        return i == 0 && getLong("knox.device_owner", 0L, i) != 0;
    }

    public final boolean isSEPLiteSecureFolder(int i) {
        return SemPersonaManager.isSecureFolderId(i);
    }

    public final Optional getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = this.mInjector.getDarManagerService();
        }
        return Optional.ofNullable(this.mDarManagerService);
    }

    public final boolean isEnterpriseUser(int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserInfo userInfo = this.mInjector.getUserManagerInternal().getUserInfo(i);
            if (userInfo != null) {
                if (DarUtil.isEnterpriseUser(userInfo)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isDualDarAuthUserId(int i) {
        if (VirtualLockUtils.isVirtualUserId(i)) {
            return this.mDualDarAuthUtils.isInnerAuthUserForDo(i);
        }
        return SemPersonaManager.isDarDualEncryptionEnabled(i) && !SemPersonaManager.isDoEnabled(i);
    }

    public final void checkWeaverStatus() {
        synchronized (this.mSpManager) {
            this.mSpManager.checkWeaverStatus();
        }
    }

    public final void refreshWeaverSlots() {
        SDPLog.m38d("Refresh weaver slots");
        synchronized (this.mSpManager) {
            this.mSpManager.refreshActiveSlots();
        }
    }

    public final boolean isSyntheticPasswordBasedCredentialLocked(int i) {
        return getCurrentLskfBasedProtectorId(i) != 0;
    }

    public final boolean shouldMigrateToSyntheticPasswordLocked(int i) {
        return !isSyntheticPasswordBasedCredentialLocked(i);
    }

    public final class DarVirtualLock {
        public DarVirtualLock() {
        }

        public VerifyCredentialResponse doVerifyCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
            SyntheticPasswordManager.AuthenticationResult authenticationResult;
            if (lockscreenCredential == null || lockscreenCredential.isNone()) {
                throw new IllegalArgumentException("Credential can't be null or empty");
            }
            VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.OK;
            if (LockSettingsService.this.isSyntheticPasswordBasedCredentialLocked(i)) {
                authenticationResult = LockSettingsService.this.mSpManager.unlockLskfBasedProtector(LockSettingsService.this.getGateKeeperService(), LockSettingsService.this.getCurrentLskfBasedProtectorId(i), lockscreenCredential, i, iCheckCredentialProgressCallback);
                verifyCredentialResponse = authenticationResult.gkResponse;
            } else {
                SDPLog.m38d("Sp not yet applied to user " + i);
                authenticationResult = null;
            }
            if (verifyCredentialResponse == null) {
                verifyCredentialResponse = VerifyCredentialResponse.ERROR;
            }
            SDPLog.m38d(String.format("Result of verification for user %d : %s", Integer.valueOf(i), verifyCredentialResponse));
            if (!verifyCredentialResponse.isMatched()) {
                onCredentialMismatchedForInner(i);
            } else if (!onCredentialVerifiedForInner(lockscreenCredential, i)) {
                verifyCredentialResponse = VerifyCredentialResponse.ERROR;
            }
            if (verifyCredentialResponse.isMatched() && authenticationResult != null && LockSettingsService.this.isDualDarAuthUserId(i)) {
                LockSettingsService.this.mDualDarLockSettings.activateEscrowTokensForDualDAR(authenticationResult.syntheticPassword, i, lockscreenCredential.getCredential());
            }
            return verifyCredentialResponse;
        }

        public boolean setLockCredentialInternalForVirtualUser(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) {
            Objects.requireNonNull(lockscreenCredential);
            Objects.requireNonNull(lockscreenCredential2);
            if (lockscreenCredential.isNone()) {
                clearLock(i);
                SDPLog.m44i("LockSettingsService.DarVirtualLock", "credential is NONE");
                return true;
            }
            synchronized (LockSettingsService.this.mSpManager) {
                if (!LockSettingsService.this.isSyntheticPasswordBasedCredentialLocked(i)) {
                    prepare(i);
                    initializeSyntheticPasswordForVirtualUser(i);
                }
            }
            SDPLog.m44i("LockSettingsService.DarVirtualLock", "progressing to set credential for virtual user");
            return false;
        }

        public boolean setLockCredentialInternalForDualDarDo(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
            if (!isInnerAuthUserForDualDarDo(i)) {
                return false;
            }
            boolean onPassword2Change = DualDARController.getInstance(LockSettingsService.this.mContext).onPassword2Change(LockSettingsService.this.mDualDarAuthUtils.getMainUserId(i), lockscreenCredential2.getCredential(), lockscreenCredential.getCredential());
            if (!onPassword2Change) {
                return onPassword2Change;
            }
            LockSettingsService.this.setUserPasswordMetrics(lockscreenCredential, i);
            return onPassword2Change;
        }

        public void clearLock(int i) {
            SDPLog.m43i("Clear virtual lock for user " + i);
            long currentLskfBasedProtectorId = LockSettingsService.this.getCurrentLskfBasedProtectorId(i);
            if (currentLskfBasedProtectorId != 0) {
                SDPLog.m38d(String.format("Handle(%d) detected at the moment of clearing lock for user %d", Long.valueOf(currentLskfBasedProtectorId), Integer.valueOf(i)));
            }
            LockSettingsService.this.mSpManager.clearSidForUser(i);
            LockSettingsService.this.mSpManager.removeUser(LockSettingsService.this.getGateKeeperService(), i);
            LockSettingsService.this.gateKeeperClearSecureUserId(i);
            LockSettingsService.this.mStorage.removeUser(i);
        }

        public boolean prepare(int i) {
            File file = new File(new File(Environment.getDataSystemDirectory(), "users"), Integer.toString(i));
            if (file.exists()) {
                return true;
            }
            if (!file.mkdir()) {
                Log.e("LockSettingsService.DarVirtualLock", "prepare - failed to create sp state path for user " + i);
                return false;
            }
            FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            return true;
        }

        public long addEscrowToken(byte[] bArr, int i, int i2, LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
            SyntheticPasswordManager.SyntheticPassword syntheticPassword;
            long addPendingToken;
            synchronized (LockSettingsService.this.mSpManager) {
                if (LockSettingsService.this.isUserSecure(i2)) {
                    syntheticPassword = null;
                } else if (LockSettingsService.this.shouldMigrateToSyntheticPasswordLocked(i2)) {
                    prepare(i2);
                    syntheticPassword = initializeSyntheticPasswordForVirtualUser(i2);
                } else {
                    syntheticPassword = LockSettingsService.this.mSpManager.unlockLskfBasedProtector(LockSettingsService.this.getGateKeeperService(), LockSettingsService.this.getCurrentLskfBasedProtectorId(i2), LockscreenCredential.createNone(), i2, null).syntheticPassword;
                }
                addPendingToken = LockSettingsService.this.mSpManager.addPendingToken(bArr, i, i2, escrowTokenStateChangeCallback);
                if (syntheticPassword != null) {
                    if (LockSettingsService.this.mDualDarAuthUtils.isInnerAuthUserForDo(i2)) {
                        byte[] pendingTokenForDualDAR = LockSettingsService.this.mDualDarLockSettings.getPendingTokenForDualDAR(i2, addPendingToken);
                        if (LockSettingsService.this.mSpManager.createTokenBasedProtector(addPendingToken, syntheticPassword, i2)) {
                            LockSettingsService.this.mDualDarLockSettings.activateEscrowTokenForDualDAR(addPendingToken, i2, pendingTokenForDualDAR, null);
                        }
                    } else {
                        LockSettingsService.this.mSpManager.createTokenBasedProtector(addPendingToken, syntheticPassword, i2);
                    }
                }
            }
            return addPendingToken;
        }

        public boolean setLockCredentialWithToken(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
            boolean z;
            if (isInnerAuthUserForDualDarDo(i)) {
                try {
                    z = LockSettingsService.this.mDualDarLockSettings.setLockCredentialWithTokenInternalForDualDAR(lockscreenCredential, j, bArr, i);
                } catch (RemoteException e) {
                    DDLog.m34e("LockSettingsService.DarVirtualLock", "Exception : " + e.getMessage(), new Object[0]);
                    z = false;
                }
                if (!z) {
                    DDLog.m34e("LockSettingsService.DarVirtualLock", "Dual DAR Client failed to reset password with token for user: " + i, new Object[0]);
                    return false;
                }
            }
            synchronized (LockSettingsService.this.mSpManager) {
                SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = LockSettingsService.this.mSpManager.unlockTokenBasedProtector(LockSettingsService.this.getGateKeeperService(), j, bArr, i);
                if (unlockTokenBasedProtector.syntheticPassword == null) {
                    SDPLog.m38d("Invalid escrow token supplied");
                    return false;
                }
                long currentLskfBasedProtectorId = LockSettingsService.this.getCurrentLskfBasedProtectorId(i);
                LockSettingsService.this.setLockCredentialWithSpLocked(lockscreenCredential, unlockTokenBasedProtector.syntheticPassword, i);
                LockSettingsService.this.mSpManager.destroyLskfBasedProtector(currentLskfBasedProtectorId, i);
                return true;
            }
        }

        public SyntheticPasswordManager.SyntheticPassword initializeSyntheticPasswordForVirtualUser(int i) {
            SyntheticPasswordManager.SyntheticPassword newSyntheticPassword;
            SDPLog.m43i("Initialize sp for virtual user " + i);
            synchronized (LockSettingsService.this.mSpManager) {
                newSyntheticPassword = LockSettingsService.this.mSpManager.newSyntheticPassword(i);
                LockSettingsService.this.setCurrentLskfBasedProtectorId(LockSettingsService.this.mSpManager.createLskfBasedProtector(LockSettingsService.this.getGateKeeperService(), LockscreenCredential.createNone(), newSyntheticPassword, i), i);
                LockSettingsService.this.onSyntheticPasswordCreated(i, newSyntheticPassword);
                SDPLog.m43i("Successfully initialized sp for virtual user " + i);
            }
            return newSyntheticPassword;
        }

        public final boolean isInnerAuthUserForDualDarDo(int i) {
            return DualDarManager.isOnDeviceOwnerEnabled() && LockSettingsService.this.mDualDarAuthUtils.isInnerAuthUserForDo(i);
        }

        public void onCredentialMismatchedForInner(int i) {
            DualDARCallback dualDARCallback;
            if (isInnerAuthUserForDualDarDo(i)) {
                synchronized (LockSettingsService.this.mPendingVerifiedResults) {
                    PendingVerifiedResult pendingVerifiedResult = (PendingVerifiedResult) LockSettingsService.this.mPendingVerifiedResults.get(i);
                    if (pendingVerifiedResult != null && (dualDARCallback = pendingVerifiedResult.mDualDARCallback) != null) {
                        try {
                            IDualDarAuthProgressCallback iDualDarAuthProgressCallback = dualDARCallback.get();
                            if (iDualDarAuthProgressCallback != null) {
                                iDualDarAuthProgressCallback.onInnerLayerUnlockFailed();
                            }
                        } catch (Exception e) {
                            SDPLog.m42e("LockSettingsService.DarVirtualLock", "Callback might be dead...", e);
                        }
                    }
                }
            }
        }

        public boolean onCredentialVerifiedForInner(LockscreenCredential lockscreenCredential, int i) {
            SparseArray sparseArray;
            if (!isInnerAuthUserForDualDarDo(i)) {
                return true;
            }
            int mainUserId = LockSettingsService.this.mDualDarAuthUtils.getMainUserId(i);
            if (DualDARController.getInstance(LockSettingsService.this.mContext).onPassword2Auth(mainUserId, lockscreenCredential.isNone() ? null : lockscreenCredential.getCredential())) {
                Log.d("LockSettingsService.DarVirtualLock", "Inner-layer authenticated with auth user " + i);
                LockSettingsService.this.setUserPasswordMetrics(lockscreenCredential, i);
                synchronized (LockSettingsService.this.mPendingVerifiedResults) {
                    PendingVerifiedResult pendingVerifiedResult = (PendingVerifiedResult) LockSettingsService.this.mPendingVerifiedResults.get(mainUserId);
                    if (pendingVerifiedResult != null) {
                        LockSettingsService.this.onCredentialVerified(pendingVerifiedResult.mSyntheticPassword, pendingVerifiedResult.mPasswordMetrics, mainUserId);
                        StateMachine.processEvent(mainUserId, Event.DEVICE_AUTH_SUCCESS);
                        StateMachine.processEvent(mainUserId, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
                        DualDARCallback dualDARCallback = pendingVerifiedResult.mDualDARCallback;
                        if (dualDARCallback != null) {
                            try {
                                try {
                                    IDualDarAuthProgressCallback iDualDarAuthProgressCallback = dualDARCallback.get();
                                    if (iDualDarAuthProgressCallback != null) {
                                        iDualDarAuthProgressCallback.onInnerLayerUnlocked();
                                    }
                                    dualDARCallback.dispose();
                                    sparseArray = LockSettingsService.this.mPendingVerifiedResults;
                                } catch (Exception e) {
                                    SDPLog.m42e("LockSettingsService.DarVirtualLock", "Callback might be dead...", e);
                                    dualDARCallback.dispose();
                                    sparseArray = LockSettingsService.this.mPendingVerifiedResults;
                                }
                                sparseArray.remove(mainUserId);
                            } catch (Throwable th) {
                                dualDARCallback.dispose();
                                LockSettingsService.this.mPendingVerifiedResults.remove(mainUserId);
                                throw th;
                            }
                        }
                    }
                }
                return true;
            }
            DDLog.m34e("LockSettingsService.DarVirtualLock", "Failed in inner-layer authentication with auth user " + i, new Object[0]);
            return false;
        }
    }

    public class DarLockSettings {
        public LockSettingsService service;

        public DarLockSettings(LockSettingsService lockSettingsService) {
            this.service = lockSettingsService;
        }

        public void saveEscrowDataIfNeededLocked(SyntheticPasswordManager.SyntheticPassword syntheticPassword, final int i) {
            boolean z = false;
            boolean isSdpMdfppMode = syntheticPassword != null ? syntheticPassword.isSdpMdfppMode() : false;
            boolean z2 = LockSettingsService.this.isNeedToEnableSdpMdfppModeForSystem() || LockSettingsService.this.isSdpMdfppModeEnabledForSystem();
            if (LockSettingsService.this.isEnterpriseUser(i) && !SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                z = true;
            }
            if (isSdpMdfppMode || z2 || z || i != 0) {
                return;
            }
            SDPLog.m38d("saveEscrowDataIfNeededLocked for system");
            this.service.getDarManagerService().ifPresent(new Consumer() { // from class: com.android.server.locksettings.LockSettingsService$DarLockSettings$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((DarManagerService) obj).saveSecuredEscrowData(i);
                }
            });
        }

        public void restoreEscrowDataIfNeededLocked(int i) {
            if (i == 0 && !this.service.mSpManager.hasEscrowData(i)) {
                if (SemPersonaManager.isDoEnabled(i) || this.service.isKnoxAdminActivated(i)) {
                    Bundle securedEscrowData = this.service.getDarManagerService().isPresent() ? this.service.mDarManagerService.getSecuredEscrowData(i) : null;
                    if (securedEscrowData == null) {
                        return;
                    }
                    byte[] byteArray = securedEscrowData.getByteArray("e0");
                    byte[] byteArray2 = securedEscrowData.getByteArray("p1");
                    if (byteArray != null && byteArray2 != null) {
                        this.service.mSpManager.saveEscrowData(byteArray, byteArray2, i);
                    }
                    SDPLog.m38d(String.format("Escrow data for user %d got restored [ Res : %b ]", Integer.valueOf(i), Boolean.valueOf(this.service.mSpManager.hasEscrowData(i))));
                }
            }
        }

        public VerifyCredentialResponse setSecureFolderLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
            VerifyCredentialResponse verifyCredentialResponse;
            SDPLog.m43i("Set credential for secure folder user " + i);
            Object[] objArr = new Object[8];
            objArr[0] = "credential";
            objArr[1] = lockscreenCredential == null ? null : lockscreenCredential.getCredential();
            objArr[2] = "credentialType";
            objArr[3] = lockscreenCredential == null ? null : Integer.valueOf(lockscreenCredential.getType());
            objArr[4] = "savedCredential";
            objArr[5] = lockscreenCredential2 != null ? lockscreenCredential2.getCredential() : null;
            objArr[6] = "userId";
            objArr[7] = Integer.valueOf(i);
            SDPLog.m46p(objArr);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            VerifyCredentialResponse verifyCredentialResponse2 = VerifyCredentialResponse.ERROR;
            try {
                if (lockscreenCredential != null) {
                    try {
                    } catch (Exception e) {
                        SDPLog.m41e("Unexpected exception while set sf credential", e);
                    }
                    if (!lockscreenCredential.isNone() && (lockscreenCredential2 == null || lockscreenCredential2.isNone())) {
                        if (this.service.isSyntheticPasswordBasedCredentialLocked(i)) {
                            SDPLog.m38d("Secure Folder already has sp based credential");
                            if (this.service.mDarLockSettings.setSecureFolderLockViaProtector(lockscreenCredential, i)) {
                                verifyCredentialResponse = VerifyCredentialResponse.OK;
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            SDPLog.m38d(String.format("Result of setting credential for secure folder user %d : %s", Integer.valueOf(i), verifyCredentialResponse2.toString()));
                            return verifyCredentialResponse2;
                        }
                        if (this.service.shouldMigrateToSyntheticPasswordLocked(i)) {
                            SDPLog.m38d("Secure Folder seems to need sp initialization first");
                            verifyCredentialResponse = VerifyCredentialResponse.SKIP;
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        SDPLog.m38d(String.format("Result of setting credential for secure folder user %d : %s", Integer.valueOf(i), verifyCredentialResponse2.toString()));
                        return verifyCredentialResponse2;
                        verifyCredentialResponse2 = verifyCredentialResponse;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        SDPLog.m38d(String.format("Result of setting credential for secure folder user %d : %s", Integer.valueOf(i), verifyCredentialResponse2.toString()));
                        return verifyCredentialResponse2;
                    }
                }
                SDPLog.m38d("Seems normal case, skip handling");
                verifyCredentialResponse = VerifyCredentialResponse.SKIP;
                verifyCredentialResponse2 = verifyCredentialResponse;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                SDPLog.m38d(String.format("Result of setting credential for secure folder user %d : %s", Integer.valueOf(i), verifyCredentialResponse2.toString()));
                return verifyCredentialResponse2;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean setSecureFolderLockViaProtector(LockscreenCredential lockscreenCredential, int i) {
            boolean lockCredentialWithToken;
            SDPLog.m43i("Set credential via trusted domain for SecureFolder user " + i);
            SDPLog.m46p("credential,", DarUtil.getSafe(lockscreenCredential).getCredential(), "credentialType", Integer.valueOf(DarUtil.getSafe(lockscreenCredential).getType()), "userId", Integer.valueOf(i));
            long secureFolderTokenHandleViaProtector = this.service.getDarManagerService().isPresent() ? this.service.mDarManagerService.getSecureFolderTokenHandleViaProtector(i) : 0L;
            if (secureFolderTokenHandleViaProtector == 0) {
                SDPLog.m38d("Failed to get SecureFolder token handle");
            } else {
                byte[] secureFolderResetTokenViaProtector = this.service.mDarManagerService.getSecureFolderResetTokenViaProtector(i);
                if (SecureUtil.isEmpty(secureFolderResetTokenViaProtector)) {
                    SDPLog.m38d("Failed to get SecureFolder reset token");
                } else {
                    lockCredentialWithToken = this.service.setLockCredentialWithToken(lockscreenCredential, secureFolderTokenHandleViaProtector, secureFolderResetTokenViaProtector, i);
                    SecureUtil.clear(secureFolderResetTokenViaProtector);
                    SDPLog.m38d(String.format("Result of setting credential via protector for SecureFolder user %d : [ res : %s ]", Integer.valueOf(i), Boolean.valueOf(lockCredentialWithToken)));
                    return lockCredentialWithToken;
                }
            }
            lockCredentialWithToken = false;
            SDPLog.m38d(String.format("Result of setting credential via protector for SecureFolder user %d : [ res : %s ]", Integer.valueOf(i), Boolean.valueOf(lockCredentialWithToken)));
            return lockCredentialWithToken;
        }

        public int getSecureMode(int i) {
            int i2;
            synchronized (this.service.mSpManager) {
                if (this.service.isSyntheticPasswordBasedCredentialLocked(i)) {
                    i2 = this.service.mSpManager.getSecureMode(this.service.getCurrentLskfBasedProtectorId(i), i);
                } else {
                    i2 = -1;
                }
            }
            return i2;
        }

        public void clearStorageForUser(int i) {
            if (SemPersonaManager.isSecureFolderId(i) || this.service.isEnterpriseUser(i)) {
                this.service.mStorage.removeUser(i);
            }
        }

        public void checkLockMaterialsTraced(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
            SDPLog.m38d(String.format("Check accessor - UID : %d, PID : %d", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid())));
            Object[] objArr = new Object[5];
            objArr[0] = (lockscreenCredential == null || lockscreenCredential.isNone()) ? "Empty" : "Hidden";
            objArr[1] = (lockscreenCredential2 == null || lockscreenCredential2.isNone()) ? "Empty" : "Hidden";
            objArr[2] = this.service.isProfileWithUnifiedLock(i) ? "Yes" : "No";
            objArr[3] = Integer.valueOf(lockscreenCredential != null ? lockscreenCredential.getType() : -1);
            objArr[4] = Integer.valueOf(i);
            SDPLog.m38d(String.format("Check requisites - Given : %s, Saved : %s, Unified : %s, Type : %d, User : %d", objArr));
        }
    }

    public VerifyCredentialResponse verifyToken(byte[] bArr, long j, int i) {
        SyntheticPasswordManager.SyntheticPassword syntheticPassword;
        SDPLog.m43i("Verify token for user " + i);
        SDPLog.m46p(KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "tokenHandle", Long.valueOf(j), "userId", Integer.valueOf(i));
        VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
        try {
            synchronized (this.mSpManager) {
                if (!this.mSpManager.hasEscrowData(i)) {
                    throw new SecurityException("Escrow token is disabled on the current user");
                }
                SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(getGateKeeperService(), j, bArr, i);
                if (unlockTokenBasedProtector != null && (syntheticPassword = unlockTokenBasedProtector.syntheticPassword) != null) {
                    this.mSdpLockSettings.migrateWithAuthToken(syntheticPassword, i);
                    verifyCredentialResponse = new VerifyCredentialResponse.Builder().build();
                    verifyCredentialResponse.setSecret(unlockTokenBasedProtector.syntheticPassword.deriveSdpMasterKey());
                }
                SDPLog.m38d("Failed due to invalid escrow token supplied");
            }
        } catch (Exception e) {
            SDPLog.m41e("Unexpected exception while verify token", e);
        }
        SDPLog.m38d("Result of token verification : " + verifyCredentialResponse.toString());
        return verifyCredentialResponse;
    }

    public boolean changeToken(byte[] bArr, long j, byte[] bArr2, long j2, int i) {
        SyntheticPasswordManager.SyntheticPassword syntheticPassword;
        SDPLog.m43i("Change token for user " + i);
        SDPLog.m46p("newToken", bArr, "newHandle", Long.valueOf(j), ", oldToken : " + bArr2 + ", oldHandle : " + j2);
        boolean z = false;
        try {
            synchronized (this.mSpManager) {
                if (!this.mSpManager.hasEscrowData(i)) {
                    throw new SecurityException("Excrow token is disabled for current user");
                }
                SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(getGateKeeperService(), j2, bArr2, i);
                if (unlockTokenBasedProtector != null && (syntheticPassword = unlockTokenBasedProtector.syntheticPassword) != null) {
                    z = this.mSpManager.createTokenBasedProtector(j, syntheticPassword, i);
                    if (!z) {
                        SDPLog.m38d("Failed in new token activation");
                    }
                    if (!z || !removeEscrowToken(j2, i)) {
                        SDPLog.m38d("Failed in old token elimination");
                    }
                }
                SDPLog.m38d("Failed due to invalid token");
            }
        } catch (Exception e) {
            SDPLog.m41e("Unexpected exception while change token", e);
        }
        SDPLog.m38d("Final result of change token : " + z);
        return z;
    }

    public final boolean isSdpMdfppModeEnabledForSystem() {
        return getLong("sdp-mdfppmode-for-system", 0L, 0) >= 2;
    }

    public final boolean isNeedToEnableSdpMdfppModeForSystem() {
        return getLong("sdp-mdfppmode-for-system", 0L, 0) == 1;
    }

    public class SdpLockSettings {
        public LockSettingsService service;

        public SdpLockSettings(LockSettingsService lockSettingsService) {
            this.service = lockSettingsService;
        }

        public Optional getSdpManagerInternal() {
            return Optional.ofNullable((SdpManagerInternal) LocalServices.getService(SdpManagerInternal.class));
        }

        public final boolean isSdpUser(int i) {
            return UserManager.isVirtualUserId(i) || (this.service.isEnterpriseUser(i) && !this.service.mDualDarLockSettings.isDualDARUser(i));
        }

        public void migrateWithAuthToken(final SyntheticPasswordManager.SyntheticPassword syntheticPassword, final int i) {
            if (syntheticPassword != null && isSdpUser(i) && this.service.mSpManager.isWeaverSupported()) {
                getSdpManagerInternal().ifPresent(new Consumer() { // from class: com.android.server.locksettings.LockSettingsService$SdpLockSettings$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        LockSettingsService.SdpLockSettings.lambda$migrateWithAuthToken$0(i, syntheticPassword, (SdpManagerInternal) obj);
                    }
                });
            }
        }

        public static /* synthetic */ void lambda$migrateWithAuthToken$0(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword, SdpManagerInternal sdpManagerInternal) {
            if (sdpManagerInternal.getMasterKeyVersion(i) == 0) {
                SDPLog.m39d("LockSettingsService", "MK migration required for user " + i);
                if (sdpManagerInternal.updateMasterKey(syntheticPassword.deriveSdpMasterKey(), syntheticPassword.deriveSdpMasterKeyPersonalized(), i)) {
                    sdpManagerInternal.setMasterKeyVersion(1, i);
                    SDPLog.m39d("LockSettingsService", "MK migration success!");
                }
            }
        }
    }

    public class DualDarLockSettings {
        public LockSettingsService service;

        public DualDarLockSettings(LockSettingsService lockSettingsService) {
            this.service = lockSettingsService;
        }

        public final byte[] deriveTokenForDualDAR(byte[] bArr) {
            return SyntheticPasswordMdfpp.deriveResetTokenForDualDAR(bArr);
        }

        public byte[] getPendingTokenForDualDAR(int i, long j) {
            byte[] pendingTokenForDualDar;
            synchronized (this.service.mSpManager) {
                pendingTokenForDualDar = this.service.mSpManager.getPendingTokenForDualDar(i, j);
            }
            if (this.service.mDarLockSettings.getSecureMode(i) > 0) {
                return pendingTokenForDualDar;
            }
            int length = pendingTokenForDualDar != null ? pendingTokenForDualDar.length - 64 : 0;
            if (length <= 0) {
                return pendingTokenForDualDar;
            }
            byte[] bArr = new byte[length];
            System.arraycopy(pendingTokenForDualDar, 0, bArr, 0, length);
            return deriveTokenForDualDAR(bArr);
        }

        public void activateEscrowTokensForDualDAR(SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i, byte[] bArr) {
            DDLog.m33d("LockSettingsService", "activateEscrowTokens: user=" + i, new Object[0]);
            synchronized (this.service.mSpManager) {
                this.service.disableEscrowTokenOnNonManagedDevicesIfNeeded(i);
                Iterator it = this.service.mSpManager.getPendingTokensForUser(i).iterator();
                while (it.hasNext()) {
                    long longValue = ((Long) it.next()).longValue();
                    DDLog.m35i("LockSettingsService", String.format("activateEscrowTokens: %x %d ", Long.valueOf(longValue), Integer.valueOf(i)), new Object[0]);
                    byte[] pendingTokenForDualDAR = getPendingTokenForDualDAR(i, longValue);
                    if (this.service.mSpManager.createTokenBasedProtector(longValue, syntheticPassword, i)) {
                        activateEscrowTokenForDualDAR(longValue, i, pendingTokenForDualDAR, bArr);
                    }
                }
            }
        }

        public boolean isDualDARUser(int i) {
            UserInfo userInfo = this.service.mInjector.getUserManagerInternal().getUserInfo(i);
            return userInfo != null && (userInfo.flags & 100663296) > 0;
        }

        public final VerifyCredentialResponse onPassword2Auth(int i, byte[] bArr) {
            Slog.d("LockSettingsService", "onPassword2Auth()");
            if (!isDualDARUser(i)) {
                Slog.d("LockSettingsService", "User is not DualDAR eligible. so no need to verify DualDAR Passwords" + i);
                return VerifyCredentialResponse.OK;
            }
            if (!DualDARController.getInstance(this.service.mContext).onPassword2Auth(i, bArr)) {
                Slog.e("LockSettingsService", "Authentication Failure by dual dar client");
                return VerifyCredentialResponse.ERROR;
            }
            Slog.d("LockSettingsService", "onPassword2Auth completed sucessfully");
            StateMachine.processEvent(i, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
            return VerifyCredentialResponse.OK;
        }

        public VerifyCredentialResponse doVerifyCredentialForDualDAR(LockscreenCredential lockscreenCredential, int i) {
            if (!isDualDARUser(i) || !SemPersonaManager.isDarDualEncrypted(i) || DualDarManager.isOnDeviceOwnerEnabled()) {
                return VerifyCredentialResponse.OK;
            }
            synchronized (this.service.mSpManager) {
                if (!this.service.isSyntheticPasswordBasedCredentialLocked(i)) {
                    return null;
                }
                SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.service.mSpManager.unlockLskfBasedProtector(this.service.getGateKeeperService(), this.service.getCurrentLskfBasedProtectorId(i), lockscreenCredential, i, null);
                if (this.service.getCredentialType(i) != lockscreenCredential.getType()) {
                    DDLog.m34e("LockSettingsService", "Credential type mismatch.", new Object[0]);
                    return VerifyCredentialResponse.ERROR;
                }
                VerifyCredentialResponse verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
                if (verifyCredentialResponse.getResponseCode() == 0) {
                    verifyCredentialResponse = onPassword2Auth(i, lockscreenCredential.getCredential());
                    if (verifyCredentialResponse.getResponseCode() != 0) {
                        DDLog.m34e("LockSettingsService", "verifyChallenge for DualDAR failed.", new Object[0]);
                        return VerifyCredentialResponse.ERROR;
                    }
                }
                if (verifyCredentialResponse.isMatched()) {
                    return !this.service.mDarVirtualLock.onCredentialVerifiedForInner(lockscreenCredential, i) ? VerifyCredentialResponse.ERROR : verifyCredentialResponse;
                }
                this.service.mDarVirtualLock.onCredentialMismatchedForInner(i);
                return verifyCredentialResponse;
            }
        }

        public final boolean onPassword2Change(int i, byte[] bArr, byte[] bArr2) {
            Slog.d("LockSettingsService", "onPassword2Change()");
            boolean onPassword2Change = DualDARController.getInstance(this.service.mContext).onPassword2Change(i, bArr, bArr2);
            if (onPassword2Change) {
                Slog.d("LockSettingsService", "Authentication Change to DualDAR Client Successful");
            } else {
                Slog.e("LockSettingsService", "Authentication Change Failure by dual dar client");
            }
            return onPassword2Change;
        }

        public void performDualDARPasswordChange(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) {
            boolean z2 = false;
            int i2 = 0;
            z2 = false;
            DDLog.m33d("LockSettingsService", "performDualDARPasswordChange ", new Object[0]);
            if (UserManager.isVirtualUserId(i)) {
                if (this.service.mDarVirtualLock.setLockCredentialInternalForDualDarDo(lockscreenCredential, lockscreenCredential2, i)) {
                    return;
                }
                DDLog.m34e("LockSettingsService", "Unexpected failure while change password", new Object[0]);
                return;
            }
            if (i == 0) {
                if (DualDarManager.isOnDeviceOwner(i)) {
                    DDLog.m33d("LockSettingsService", "Skipped in DualDAR on DO case", new Object[0]);
                    return;
                }
                DualDARController dualDARController = DualDARController.getInstance(this.service.mContext);
                if (lockscreenCredential != null && !lockscreenCredential.isNone()) {
                    z2 = true;
                }
                dualDARController.onPassword1Change(i, z2);
                return;
            }
            if (isDualDARUser(i)) {
                if (onPassword2Change(i, lockscreenCredential2.isNone() ? null : lockscreenCredential2.getCredential(), lockscreenCredential.isNone() ? null : lockscreenCredential.getCredential())) {
                    return;
                }
                DDLog.m34e("LockSettingsService", "Change profile password failed by DualDAR Client", new Object[0]);
                try {
                    i2 = LockPatternUtils.credentialTypeToPasswordQuality(lockscreenCredential2.getType());
                } catch (IllegalStateException e) {
                    DDLog.m33d("LockSettingsService", "IllegalStateException : get credential quality : " + e.getMessage(), new Object[0]);
                }
                synchronized (this.service.mSpManager) {
                    this.service.setLong("lockscreen.password_type", i2, i);
                    this.service.setLockCredentialInternal(lockscreenCredential2, lockscreenCredential, i, z);
                }
                throw new RemoteException("Change profile password failed by DualDAR Client");
            }
        }

        public void activateEscrowTokenForDualDAR(long j, int i, byte[] bArr, byte[] bArr2) {
            if (this.service.isDualDarAuthUserId(i)) {
                if (DualDARController.getInstance(this.service.mContext).setResetPasswordToken(this.service.mDualDarAuthUtils.getMainUserId(i), bArr2, j, bArr)) {
                    DDLog.m33d("LockSettingsService", "Successfully activated reset pwd token for Dual DAR user: " + i, new Object[0]);
                    return;
                }
                DDLog.m34e("LockSettingsService", "Failed to activate escrow token for Dual DAR user: " + i + " because client failed to activate reset pwd token", new Object[0]);
                this.service.removeEscrowToken(j, i);
            }
        }

        public boolean setLockCredentialWithTokenInternalForDualDAR(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
            if (!this.service.isDualDarAuthUserId(i)) {
                return true;
            }
            synchronized (this.service.mSpManager) {
                if (this.service.mSpManager.unlockTokenBasedProtector(this.service.getGateKeeperService(), j, bArr, i).syntheticPassword == null) {
                    DDLog.m37w("LockSettingsService", "Invalid escrow token supplied", new Object[0]);
                    return false;
                }
                byte[] activeTokenForDualDar = this.service.mSpManager.getActiveTokenForDualDar(i, bArr);
                if (activeTokenForDualDar == null) {
                    DDLog.m34e("LockSettingsService", "Dual DAR Client failed to get reset token for user: " + i, new Object[0]);
                    return false;
                }
                if (!DualDARController.getInstance(this.service.mContext).resetPasswordWithToken(this.service.mDualDarAuthUtils.getMainUserId(i), lockscreenCredential.getCredential(), j, activeTokenForDualDar)) {
                    DDLog.m34e("LockSettingsService", "Dual DAR Client failed to reset password with token for user: " + i, new Object[0]);
                    return false;
                }
                DDLog.m33d("LockSettingsService", "Dual DAR Client successfully reset password with token for user: " + i, new Object[0]);
                return true;
            }
        }

        public byte[] getChangedStorageSecretIfDualDAR(int i, byte[] bArr) {
            Slog.d("LockSettingsService", "getChangedStorageSecretIfDualDAR");
            if (!isDualDARUser(i)) {
                return bArr;
            }
            if (DualDarManager.isOnDeviceOwner(i)) {
                Log.d("LockSettingsService", "Do not substitute outer-layer-key in case of DualDAR on DO!");
                return bArr;
            }
            return fetchOuterLayerKey(i);
        }

        public final byte[] fetchOuterLayerKey(int i) {
            Slog.d("LockSettingsService", "fetchOuterLayerKey()");
            byte[] fetchOuterLayerKey = DualDARController.getInstance(this.service.mContext).fetchOuterLayerKey(i);
            Slog.e("LockSettingsService", "fetchOuterLayerKey Finished");
            return fetchOuterLayerKey;
        }

        public boolean unlockDualDarUserKeyIfUnsecured(int i) {
            if (!isDualDARUser(i) || DualDarManager.isOnDeviceOwner(i)) {
                return true;
            }
            if (StateMachine.getCurrentState(i) == State.DEVICE_UNLOCK_DATA_UNLOCK) {
                DDLog.m33d("LockSettingsService", "Password2Auth has already been completed for: " + i, new Object[0]);
                return true;
            }
            DDLog.m34e("LockSettingsService", "dualdar data is not unlocked yet!", new Object[0]);
            return false;
        }
    }

    public VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) {
        checkPasswordReadPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return doVerifyCredentialForDualDarDo(lockscreenCredential, i, i2, iDualDarAuthProgressCallback, 0);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    public final VerifyCredentialResponse doVerifyCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback, int i3) {
        SDPLog.m43i("Verify credential for dual-dar user " + i);
        Object[] objArr = new Object[8];
        objArr[0] = "credential";
        objArr[1] = lockscreenCredential != null ? lockscreenCredential.getCredential() : "null";
        objArr[2] = "credentialType";
        objArr[3] = Integer.valueOf(lockscreenCredential != null ? lockscreenCredential.getType() : -1);
        objArr[4] = "userId";
        objArr[5] = Integer.valueOf(i);
        objArr[6] = "opiotn";
        objArr[7] = Integer.valueOf(i2);
        SDPLog.m46p(objArr);
        if (lockscreenCredential == null || lockscreenCredential.isNone()) {
            throw new IllegalArgumentException("Credential can't be null or empty");
        }
        Slogf.m96i("LockSettingsService", "Verifying lockscreen credential for user %d", Integer.valueOf(i));
        synchronized (this.mSpManager) {
            SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), lockscreenCredential, i, null);
            VerifyCredentialResponse verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
            if (verifyCredentialResponse.getResponseCode() == 0) {
                this.mBiometricDeferredQueue.addPendingLockoutResetForUser(i, unlockLskfBasedProtector.syntheticPassword.deriveGkPassword());
                verifyCredentialResponse = this.mSpManager.verifyChallenge(getGateKeeperService(), unlockLskfBasedProtector.syntheticPassword, 0L, i);
                if (verifyCredentialResponse == null || verifyCredentialResponse.getResponseCode() != 0) {
                    Slog.wtf("LockSettingsService", "verifyChallenge with SP failed.");
                    return VerifyCredentialResponse.ERROR;
                }
            }
            if (verifyCredentialResponse.getResponseCode() == 0) {
                Slogf.m96i("LockSettingsService", "Successfully verified lockscreen credential for user %d", Integer.valueOf(i));
                if ((i2 & 1) != 0) {
                    postponeOnCredentialVerified(unlockLskfBasedProtector.syntheticPassword, PasswordMetrics.computeForCredential(lockscreenCredential), i, iDualDarAuthProgressCallback);
                } else {
                    onCredentialVerified(unlockLskfBasedProtector.syntheticPassword, PasswordMetrics.computeForCredential(lockscreenCredential), i);
                }
                if ((i3 & 1) != 0) {
                    verifyCredentialResponse = new VerifyCredentialResponse.Builder().setGatekeeperPasswordHandle(storeGatekeeperPasswordTemporarily(unlockLskfBasedProtector.syntheticPassword.deriveGkPassword())).build();
                }
                sendCredentialsOnUnlockIfRequired(lockscreenCredential, i);
                if (DualDarManager.isOnDeviceOwner(i)) {
                    DualDarManager.getInstance(this.mContext).cancelDataLock(i);
                }
            } else if (verifyCredentialResponse.getResponseCode() == 1 && verifyCredentialResponse.getTimeout() > 0) {
                requireStrongAuth(8, i);
            }
            return verifyCredentialResponse;
        }
    }

    public final void postponeOnCredentialVerified(SyntheticPasswordManager.SyntheticPassword syntheticPassword, PasswordMetrics passwordMetrics, int i, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) {
        if (syntheticPassword == null) {
            return;
        }
        SDPLog.m38d("Postpone credential verified event for user " + i);
        synchronized (this.mPendingVerifiedResults) {
            this.mPendingVerifiedResults.put(i, new PendingVerifiedResult(syntheticPassword, passwordMetrics, new DualDARCallback(iDualDarAuthProgressCallback)));
        }
        int innerAuthUserId = this.mDualDarAuthUtils.getInnerAuthUserId(i);
        if (isUserSecure(innerAuthUserId)) {
            return;
        }
        SDPLog.m38d("Auth user " + innerAuthUserId + " has no credential");
        this.mDarVirtualLock.onCredentialVerifiedForInner(LockscreenCredential.createNone(), innerAuthUserId);
    }

    public class PendingVerifiedResult {
        public final DualDARCallback mDualDARCallback;
        public final PasswordMetrics mPasswordMetrics;
        public final SyntheticPasswordManager.SyntheticPassword mSyntheticPassword;

        public PendingVerifiedResult(SyntheticPasswordManager.SyntheticPassword syntheticPassword, PasswordMetrics passwordMetrics, DualDARCallback dualDARCallback) {
            this.mSyntheticPassword = syntheticPassword;
            this.mPasswordMetrics = passwordMetrics;
            this.mDualDARCallback = dualDARCallback;
        }
    }

    public final class DualDARCallback implements IBinder.DeathRecipient {
        public IDualDarAuthProgressCallback mCallback;

        public DualDARCallback(IDualDarAuthProgressCallback iDualDarAuthProgressCallback) {
            this.mCallback = iDualDarAuthProgressCallback;
            Optional.ofNullable(asBinder()).ifPresent(new Consumer() { // from class: com.android.server.locksettings.LockSettingsService$DualDARCallback$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LockSettingsService.DualDARCallback.this.lambda$new$0((IBinder) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(IBinder iBinder) {
            try {
                iBinder.linkToDeath(this, 0);
            } catch (Exception e) {
                SDPLog.m42e("LockSettingsService.DDAR", "Failed to link death listener...", e);
            }
        }

        public IDualDarAuthProgressCallback get() {
            return this.mCallback;
        }

        public IBinder asBinder() {
            IDualDarAuthProgressCallback iDualDarAuthProgressCallback = this.mCallback;
            if (iDualDarAuthProgressCallback == null) {
                return null;
            }
            return iDualDarAuthProgressCallback.asBinder();
        }

        public void dispose() {
            this.mCallback = null;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Optional.ofNullable(asBinder()).ifPresent(new Consumer() { // from class: com.android.server.locksettings.LockSettingsService$DualDARCallback$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LockSettingsService.DualDARCallback.this.lambda$binderDied$1((IBinder) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$1(IBinder iBinder) {
            try {
                iBinder.unlinkToDeath(this, 0);
            } catch (Exception e) {
                SDPLog.m42e("LockSettingsService.DDAR", "Failed to unlink death listener", e);
                e.printStackTrace();
            }
            this.mCallback = null;
        }
    }

    public void registerRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) {
        byte[] bArr;
        int checkRemoteLockPassword;
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("registerRemoteLockCallback requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        Log.e("LockSettingsService", "registerRemoteLockCallback!!");
        this.mCallbacks.put(Integer.valueOf(i), iRemoteLockMonitorCallback);
        if (this.mRemoteCallback == null || (bArr = this.mPassword) == null || this.mLockTypeForPasswordCheck != i) {
            return;
        }
        if (iRemoteLockMonitorCallback != null) {
            try {
                checkRemoteLockPassword = iRemoteLockMonitorCallback.checkRemoteLockPassword(bArr);
            } catch (RemoteException e) {
                Log.d("LockSettingsService", "failed checkRemoteLockPassword callback!! after register");
                e.printStackTrace();
                return;
            }
        } else {
            checkRemoteLockPassword = -1;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, checkRemoteLockPassword);
        try {
            this.mRemoteCallback.sendResult(bundle);
        } catch (RemoteException e2) {
            Log.d("LockSettingsService", "failed sendResult callback!! after register");
            e2.printStackTrace();
        }
        this.mRemoteCallback = null;
        this.mPassword = null;
    }

    public void unregisterRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("registerRemoteLockCallback requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        if (this.mCallbacks.containsKey(Integer.valueOf(i))) {
            this.mCallbacks.remove(Integer.valueOf(i));
            return;
        }
        Log.e("LockSettingsService", i + " hasn't been registered!!");
    }

    public boolean setKnoxGuard(int i, RemoteLockInfo remoteLockInfo) {
        checkWritePermission();
        try {
            return applyRemoteLock(i, remoteLockInfo);
        } catch (RemoteException unused) {
            Log.d("LockSettingsService", "failed applyRemoteLock!");
            return false;
        }
    }

    public void setRemoteLock(int i, RemoteLockInfo remoteLockInfo) {
        checkWritePermission();
        try {
            applyRemoteLock(i, remoteLockInfo);
        } catch (RemoteException unused) {
            Log.d("LockSettingsService", "failed applyRemoteLock!");
        }
    }

    public final boolean applyRemoteLock(int i, RemoteLockInfo remoteLockInfo) {
        String str;
        RemoteLockInfo remoteLockInfo2;
        boolean z;
        boolean z2;
        int i2 = remoteLockInfo.lockType;
        boolean z3 = remoteLockInfo.lockState;
        String str2 = (String) remoteLockInfo.message;
        String str3 = (String) remoteLockInfo.phoneNumber;
        boolean z4 = remoteLockInfo.enableEmergencyCall;
        String str4 = (String) remoteLockInfo.clientName;
        String str5 = (String) remoteLockInfo.emailAddress;
        long j = remoteLockInfo.allowFailCount;
        long j2 = remoteLockInfo.lockTimeOut;
        long j3 = remoteLockInfo.permanentBlockCount;
        boolean z5 = remoteLockInfo.skipPinContainer;
        boolean z6 = remoteLockInfo.skipSupportContainer;
        Bundle bundle = remoteLockInfo.bundle;
        if (bundle != null) {
            String str6 = bundle.getCharSequence("customer_app_name") != null ? (String) bundle.getCharSequence("customer_app_name") : null;
            r19 = bundle.getCharSequence("customer_package_name") != null ? (String) bundle.getCharSequence("customer_package_name") : null;
            str = str6;
        } else {
            str = null;
        }
        String str7 = str;
        if (z3) {
            setBoolean(i2 + "locked", z3, i);
            setString(i2 + "message", str2, i);
            setString(i2 + "phonenumber", str3, i);
            setBoolean(i2 + "enableemergencycall", z4, i);
            setString(i2 + "clientname", str4, i);
            setString(i2 + "emailaddress", str5, i);
            setLong(i2 + "allowfailcount", j, i);
            setLong(i2 + "locktime", j2, i);
            setLong(i2 + "permanentblockcount", j3, i);
            setBoolean(i2 + "skippin", z5, i);
            setBoolean(i2 + "skipsupport", z6, i);
            remoteLockInfo2 = remoteLockInfo;
            if (remoteLockInfo2.bundle != null) {
                setString(i2 + "appname", str7, i);
                setString(i2 + "packagename", r19, i);
            }
            z = false;
        } else {
            remoteLockInfo2 = remoteLockInfo;
            z = false;
            setBoolean(i2 + "locked", false, i);
            setLong(i2 + "remotelockscreen.lockoutdeadline", 0L, i);
            setLong(i2 + "remotelockscreen.failedunlockcount", 0L, i);
        }
        IRemoteLockMonitorCallback iRemoteLockMonitorCallback = (IRemoteLockMonitorCallback) this.mCallbacks.get(Integer.valueOf(i2));
        if (iRemoteLockMonitorCallback != null) {
            try {
                iRemoteLockMonitorCallback.changeRemoteLockState(remoteLockInfo2);
            } catch (RemoteException e) {
                Log.d("LockSettingsService", "failed changeRemoteLockState callback!");
                e.printStackTrace();
                z2 = z;
            }
        }
        z2 = true;
        IRemoteLockMonitorCallback iRemoteLockMonitorCallback2 = (IRemoteLockMonitorCallback) this.mCallbacks.get(4);
        if (iRemoteLockMonitorCallback2 != null) {
            try {
                iRemoteLockMonitorCallback2.changeRemoteLockState(remoteLockInfo2);
                z = z2;
            } catch (RemoteException e2) {
                Log.d("LockSettingsService", "!@ failed changeRemoteLockState callback!");
                e2.printStackTrace();
            }
        } else {
            Log.d("LockSettingsService", "!@ NULL Callback for changeRemoteLockState!");
        }
        IRemoteLockMonitorCallback iRemoteLockMonitorCallback3 = (IRemoteLockMonitorCallback) this.mCallbacks.get(5);
        if (iRemoteLockMonitorCallback3 != null) {
            try {
                iRemoteLockMonitorCallback3.changeRemoteLockState(remoteLockInfo2);
            } catch (RemoteException e3) {
                Log.d("LockSettingsService", "failed changeRemoteLockState callback!");
                e3.printStackTrace();
            }
        }
        return z;
    }

    public void checkRemoteLockPassword(int i, byte[] bArr, int i2, IRemoteCallback iRemoteCallback) {
        int checkRemoteLockPassword;
        checkPasswordReadPermission();
        IRemoteLockMonitorCallback iRemoteLockMonitorCallback = (IRemoteLockMonitorCallback) this.mCallbacks.get(Integer.valueOf(i));
        if (iRemoteLockMonitorCallback != null) {
            try {
                checkRemoteLockPassword = iRemoteLockMonitorCallback.checkRemoteLockPassword(bArr);
            } catch (RemoteException e) {
                this.mContext.sendBroadcast(new Intent("com.samsung.remotelock.CLIENT_WAKEUP"), "com.samsung.android.permission.REMOTELOCK");
                this.mRemoteCallback = iRemoteCallback;
                this.mLockTypeForPasswordCheck = i;
                this.mPassword = bArr;
                Log.d("LockSettingsService", "failed checkRemoteLockPassword callback!");
                e.printStackTrace();
                return;
            }
        } else {
            checkRemoteLockPassword = -1;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(KnoxCustomManagerService.SPCM_KEY_RESULT, checkRemoteLockPassword);
        if (checkRemoteLockPassword > 0) {
            bundle.putLong("timeout", getLong(i + "locktime", 0L, i2));
        }
        try {
            iRemoteCallback.sendResult(bundle);
        } catch (RemoteException e2) {
            Log.d("LockSettingsService", "failed sendResult callback!");
            e2.printStackTrace();
        }
    }

    public void requestRemoteLockInfo(int i) {
        int i2;
        checkPasswordReadPermission();
        boolean z = false;
        int i3 = 0;
        while (i3 < 4) {
            Bundle bundle = new Bundle();
            boolean z2 = getBoolean(i3 + "locked", z, i);
            if (z2) {
                String string = getString(i3 + "message", null, i);
                String string2 = getString(i3 + "phonenumber", null, i);
                boolean z3 = getBoolean(i3 + "enableemergencycall", z, i);
                String string3 = getString(i3 + "clientname", null, i);
                String string4 = getString(i3 + "emailaddress", null, i);
                int i4 = (int) getLong(i3 + "allowfailcount", 0L, i);
                long j = getLong(i3 + "locktime", 0L, i);
                StringBuilder sb = new StringBuilder();
                i2 = i3;
                sb.append(i2);
                sb.append("permanentblockcount");
                int i5 = (int) getLong(sb.toString(), 0L, i);
                boolean z4 = getBoolean(i2 + "skippin", false, i);
                boolean z5 = getBoolean(i2 + "skipsupport", false, i);
                bundle.putCharSequence("customer_app_name", getString(i2 + "appname", null, i));
                bundle.putCharSequence("customer_package_name", getString(i2 + "packagename", null, i));
                RemoteLockInfo build = new RemoteLockInfo.Builder(i2, z2).setMessage(string).setPhoneNumber(string2).setEnableEmergencyCall(z3).setClientName(string3).setEmailAddress(string4).setAllowFailCount(i4).setLockTimeOut(j).setBlockCount(i5).setSkipPinContainer(z4).setSkipSupportContainer(z5).setBundle(bundle).build();
                IRemoteLockMonitorCallback iRemoteLockMonitorCallback = (IRemoteLockMonitorCallback) this.mCallbacks.get(4);
                if (iRemoteLockMonitorCallback != null) {
                    try {
                        iRemoteLockMonitorCallback.changeRemoteLockState(build);
                    } catch (RemoteException e) {
                        Log.d("LockSettingsService", "failed changeRemoteLockState callback!");
                        e.printStackTrace();
                    }
                }
                IRemoteLockMonitorCallback iRemoteLockMonitorCallback2 = (IRemoteLockMonitorCallback) this.mCallbacks.get(5);
                if (iRemoteLockMonitorCallback2 != null) {
                    try {
                        iRemoteLockMonitorCallback2.changeRemoteLockState(build);
                    } catch (RemoteException e2) {
                        Log.d("LockSettingsService", "failed changeRemoteLockState callback!");
                        e2.printStackTrace();
                    }
                }
            } else {
                i2 = i3;
            }
            i3 = i2 + 1;
            z = false;
        }
    }

    public void setLockFMMPassword(byte[] bArr, int i) {
        checkWritePermission();
        this.mStorage.writeFMMPasswordHash(passwordToHash(bArr, i), i);
    }

    public boolean haveFMMPassword(int i) {
        return this.mStorage.hasFMMPassword(i);
    }

    public boolean checkFMMPassword(byte[] bArr, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(bArr, i);
        byte[] readFMMPasswordHash = this.mStorage.readFMMPasswordHash(i);
        if (readFMMPasswordHash == null) {
            return true;
        }
        return Arrays.equals(passwordToHash, readFMMPasswordHash);
    }

    public boolean getCarrierLock(int i) {
        checkWritePermission();
        return this.mStorage.getCarrierLock();
    }

    public boolean updateCarrierLock(int i) {
        checkWritePermission();
        return this.mStorage.updateCarrierLock();
    }

    public void setLockCarrierPassword(byte[] bArr, int i) {
        checkWritePermission();
        this.mStorage.writeCarrierPasswordHash(passwordToHash(bArr, i), i);
    }

    public boolean haveCarrierPassword(int i) {
        return this.mStorage.hasCarrierPassword(i);
    }

    public boolean checkCarrierPassword(byte[] bArr, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(bArr, i);
        byte[] readCarrierPasswordHash = this.mStorage.readCarrierPasswordHash(i);
        if (readCarrierPasswordHash == null) {
            return true;
        }
        return Arrays.equals(passwordToHash, readCarrierPasswordHash);
    }

    public byte[] passwordToHash(byte[] bArr, int i) {
        String legacyPasswordToHash = LockscreenCredential.legacyPasswordToHash(bArr, getSalt(i).getBytes());
        if (legacyPasswordToHash == null) {
            return null;
        }
        return legacyPasswordToHash.getBytes(StandardCharsets.UTF_8);
    }

    public void setShellCommandCallback(IRemoteCallback iRemoteCallback) {
        checkPasswordReadPermission();
        this.mShellCommandCallback = iRemoteCallback;
    }

    public void setSecurityDebugLevel(int i) {
        Slog.d("LockSettingsService", "!@ setSecurityDebugLevel = " + i);
        this.mStorage.setSecurityDebugLevel(i);
        if (i >= 1) {
            showLockState();
            this.mHandler.removeCallbacks(this.mResetDebugLevel);
            this.mHandler.postDelayed(this.mResetDebugLevel, BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
        }
    }

    public void addLog(int i, String str) {
        this.mStorage.addLog(i, str);
    }

    public final int getCurrentFailedPasswordAttempts(int i) {
        return this.mInjector.getDevicePolicyManager().getCurrentFailedPasswordAttempts(i);
    }

    public final String getSyntheticPasswordTryHandleLocked(int i) {
        return getString("sp-handle-try", "", i);
    }

    public void setLockModeChangedCallback(IRemoteCallback iRemoteCallback) {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("setLockModeChangedCallback requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        this.mStorage.setSecureLockModeChangedCallback(iRemoteCallback);
    }

    public void sendLockTypeChangedInfo(int i) {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("sendLockTypeChangedInfo requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        this.mStorage.sendLockTypeChangedInfo(i);
    }

    public void setAppLockPin(String str, int i) {
        checkWritePermission();
        this.mStorage.writeAppLockPinHash(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), i);
    }

    public void setAppLockPassword(String str, int i) {
        checkWritePermission();
        this.mStorage.writeAppLockPasswordHash(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), i);
    }

    public void setAppLockPattern(String str, int i) {
        checkWritePermission();
        this.mStorage.writeAppLockPatternHash(LockPatternUtils.patternToByteArray(LockPatternUtils.byteArrayToPattern(str.getBytes(StandardCharsets.UTF_8))), i);
    }

    public void setAppLockBackupPin(String str, int i) {
        checkWritePermission();
        this.mStorage.writeAppLockBackupPinHash(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), i);
    }

    public void setAppLockFingerprintPassword(String str, int i) {
        checkWritePermission();
        this.mStorage.writeAppLockFingerprintPasswordHash(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), i);
    }

    public boolean checkAppLockPin(String str, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(str.getBytes(StandardCharsets.UTF_8), i);
        byte[] readAppLockPinHash = this.mStorage.readAppLockPinHash(i);
        if (readAppLockPinHash == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readAppLockPinHash);
        return (equals || readAppLockPinHash.length != 40) ? equals : Arrays.equals(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), readAppLockPinHash);
    }

    public boolean checkAppLockPassword(String str, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(str.getBytes(StandardCharsets.UTF_8), i);
        byte[] readAppLockPasswordHash = this.mStorage.readAppLockPasswordHash(i);
        if (readAppLockPasswordHash == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readAppLockPasswordHash);
        return (equals || readAppLockPasswordHash.length != 40) ? equals : Arrays.equals(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), readAppLockPasswordHash);
    }

    public boolean checkAppLockPatternWithHash(String str, int i, byte[] bArr) {
        checkPasswordReadPermission();
        byte[] patternToByteArray = LockPatternUtils.patternToByteArray(LockPatternUtils.byteArrayToPattern(str.getBytes(StandardCharsets.UTF_8)));
        byte[] readAppLockPatternHash = this.mStorage.readAppLockPatternHash(i);
        return readAppLockPatternHash == null || Arrays.equals(patternToByteArray, readAppLockPatternHash) || Arrays.equals(bArr, readAppLockPatternHash);
    }

    public boolean checkAppLockBackupPin(String str, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(str.getBytes(StandardCharsets.UTF_8), i);
        byte[] readAppLockBackupkPinHash = this.mStorage.readAppLockBackupkPinHash(i);
        if (readAppLockBackupkPinHash == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readAppLockBackupkPinHash);
        return (equals || readAppLockBackupkPinHash.length != 40) ? equals : Arrays.equals(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), readAppLockBackupkPinHash);
    }

    public boolean checkAppLockFingerprintPassword(String str, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(str.getBytes(StandardCharsets.UTF_8), i);
        byte[] readAppLockFingerprintPasswordHash = this.mStorage.readAppLockFingerprintPasswordHash(i);
        if (readAppLockFingerprintPasswordHash == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readAppLockFingerprintPasswordHash);
        return (equals || readAppLockFingerprintPasswordHash.length != 40) ? equals : Arrays.equals(passwordToHash(str.getBytes(StandardCharsets.UTF_8), i), readAppLockFingerprintPasswordHash);
    }

    public boolean haveAppLockPin(int i) {
        return this.mStorage.haveAppLockPin(i);
    }

    public boolean haveAppLockPassword(int i) {
        return this.mStorage.haveAppLockPassword(i);
    }

    public boolean haveAppLockPattern(int i) {
        return this.mStorage.haveAppLockPattern(i);
    }

    public boolean haveAppLockBackupPin(int i) {
        return this.mStorage.haveAppLockBackupPin(i);
    }

    public boolean haveAppLockFingerprintPassword(int i) {
        return this.mStorage.haveAppLockFingerprintPassword(i);
    }
}
