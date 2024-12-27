package com.android.server.locksettings;

import android.R;
import android.app.ActivityManager;
import android.app.IActivityManager;
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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.authsecret.IAuthSecret;
import android.hardware.authsecret.IAuthSecret$Stub$Proxy;
import android.hardware.authsecret.V1_0.IAuthSecret$Proxy;
import android.hardware.biometrics.BiometricManager;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IProgressListener;
import android.os.IRemoteCallback;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.ShellCallback;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.ICeStorageLockEventListener;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.PasswordPolicy;
import android.security.AndroidKeyStoreMaintenance;
import android.security.Flags;
import android.security.KeyStoreAuthorization;
import android.security.Scrypt;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProtection;
import android.security.keystore.UserNotAuthenticatedException;
import android.security.keystore.recovery.KeyChainSnapshot;
import android.security.keystore.recovery.RecoveryCertPath;
import android.security.keystore2.AndroidKeyStoreLoadStoreParameter;
import android.security.keystore2.AndroidKeyStoreProvider;
import android.service.gatekeeper.GateKeeperResponse;
import android.service.gatekeeper.IGateKeeperService;
import android.service.notification.StatusBarNotification;
import android.service.persistentdata.PersistentDataBlockManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.IWindowManager;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.HexDump;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.IUpdateVerifierCallback;
import com.android.internal.widget.IUpdateVerifierInterface;
import com.android.internal.widget.IWeakEscrowTokenActivatedListener;
import com.android.internal.widget.IWeakEscrowTokenRemovedListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockSettingsStateListener;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.RebootEscrowListener;
import com.android.internal.widget.RemoteLockInfo;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.security.SecureBox;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.DarUtil;
import com.android.server.knox.dar.EnterprisePartitionManager;
import com.android.server.knox.dar.SecureUtil;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.knox.dar.ddar.DualDarDoPolicyChecker;
import com.android.server.knox.dar.ddar.nativedaemon.DualDARDaemonProxy;
import com.android.server.knox.dar.ddar.nativedaemon.NativeDaemonEvent;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.knox.dar.sdp.SdpManagerInternal;
import com.android.server.knox.dar.sdp.security.BytesUtil;
import com.android.server.locksettings.BiometricDeferredQueue;
import com.android.server.locksettings.LockSettingsStorage;
import com.android.server.locksettings.LockSettingsStrongAuth;
import com.android.server.locksettings.RebootEscrowManager;
import com.android.server.locksettings.SyntheticPasswordManager;
import com.android.server.locksettings.recoverablekeystore.InsecureUserException;
import com.android.server.locksettings.recoverablekeystore.KeySyncTask;
import com.android.server.locksettings.recoverablekeystore.PlatformKeyManager;
import com.android.server.locksettings.recoverablekeystore.RecoverableKeyStoreManager;
import com.android.server.locksettings.recoverablekeystore.RecoverySnapshotListenersStorage;
import com.android.server.locksettings.recoverablekeystore.TestOnlyInsecureCertificateHelper;
import com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException;
import com.android.server.locksettings.recoverablekeystore.certificate.CertUtils;
import com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException;
import com.android.server.locksettings.recoverablekeystore.certificate.SigXml;
import com.android.server.locksettings.recoverablekeystore.storage.ApplicationKeyStorage;
import com.android.server.locksettings.recoverablekeystore.storage.CleanupManager;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDbHelper;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage;
import com.android.server.locksettings.recoverablekeystore.storage.RecoverySnapshotStorage;
import com.android.server.locksettings.recoverablekeystore.storage.RemoteLockscreenValidationSessionStorage;
import com.android.server.pdb.PersistentDataBlockManagerInternal;
import com.android.server.pdb.PersistentDataBlockService;
import com.android.server.pm.PersonaManagerService;
import com.android.server.pm.PersonaServiceHelper;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
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
import com.samsung.android.lock.LsConstants;
import com.samsung.android.lock.LsLog;
import com.samsung.android.lock.SPBnRManager;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.service.HermesService.IHermesService;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import javax.crypto.AEADBadTagException;
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

public final class LockSettingsService extends ILockSettings.Stub {
    public static final boolean DEBUG_DUMP;
    public static final boolean FIX_UNLOCKED_DEVICE_REQUIRED_KEYS = Flags.fixUnlockedDeviceRequiredKeysV2();
    public static final int[] SYSTEM_CREDENTIAL_UIDS;
    public static boolean mIsDbMigrated;
    public static boolean mIsSpblobMigrated;
    public final String[] RemoteLockType;
    public final IActivityManager mActivityManager;
    protected byte[] mAuthSecret;
    public IAuthSecret mAuthSecretService;
    public final BiometricDeferredQueue mBiometricDeferredQueue;
    public final AnonymousClass3 mBroadcastReceiver;
    public final HashMap mCallbacks;
    public final AnonymousClass4 mCeStorageLockEventListener;
    public final Context mContext;
    public final DarLockSettings mDarLockSettings;
    public DarManagerService mDarManagerService;
    public final Injector.AnonymousClass1 mDarVirtualLock;
    public final DualDarAuthUtils mDualDarAuthUtils;
    public final Injector.AnonymousClass1 mDualDarLockSettings;
    public IGateKeeperService mGateKeeperService;
    public final LongSparseArray mGatekeeperPasswords;
    protected final Handler mHandler;
    protected boolean mHasSecureLockScreen;
    public IHermesService mHermesService;
    public final Injector mInjector;
    public final KeyStore mKeyStore;
    public final KeyStoreAuthorization mKeyStoreAuthorization;
    public int mLockTypeForPasswordCheck;
    public final NotificationManager mNotificationManager;
    public byte[] mPassword;
    public final SparseArray mPendingVerifiedResults;
    public final RebootEscrowManager mRebootEscrowManager;
    public final RecoverableKeyStoreManager mRecoverableKeyStoreManager;
    public IRemoteCallback mRemoteCallback;
    public final AnonymousClass9 mResetDebugLevel;
    public final Injector.AnonymousClass1 mSdpLockSettings;
    public IRemoteCallback mShellCommandCallback;
    public boolean mShouldUnbind;
    public final AnonymousClass9 mSpBackup;
    public final SyntheticPasswordDump mSpDump;
    public final AnonymousClass3 mSpDumpReceiver;
    public final SyntheticPasswordManager mSpManager;
    protected final LockSettingsStorage mStorage;
    public final IStorageManager mStorageManager;
    public final StorageManagerInternal mStorageManagerInternal;
    public final LockSettingsStrongAuth mStrongAuth;
    public final SynchronizedStrongAuthTracker mStrongAuthTracker;
    public boolean mThirdPartyAppsStarted;
    public final UnifiedProfilePasswordCache mUnifiedProfilePasswordCache;
    public final UserManager mUserManager;
    public final Object mSeparateChallengeLock = new Object();
    public final DeviceProvisionedObserver mDeviceProvisionedObserver = new DeviceProvisionedObserver();
    public final Object mUserCreationAndRemovalLock = new Object();
    public SparseIntArray mEarlyCreatedUsers = new SparseIntArray();
    public SparseIntArray mEarlyRemovedUsers = new SparseIntArray();
    public final SparseArray mUserPasswordMetrics = new SparseArray();
    protected final Object mHeadlessAuthSecretLock = new Object();
    public final HashMap mUserManagerCache = new HashMap();
    public final CopyOnWriteArrayList mLockSettingsStateListeners = new CopyOnWriteArrayList();
    public Consumer mMaintenanceModeCallback = null;
    public final AnonymousClass1 mMaintenanceModeListener = new AnonymousClass1();

    /* renamed from: com.android.server.locksettings.LockSettingsService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    /* renamed from: com.android.server.locksettings.LockSettingsService$4, reason: invalid class name */
    public final class AnonymousClass4 implements ICeStorageLockEventListener {
        public AnonymousClass4() {
        }

        public final void onStorageLocked(int i) {
            Slog.i("LockSettingsService", "Storage lock event received for " + i);
            if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()) {
                LockSettingsService.this.mHandler.post(new LockSettingsService$$ExternalSyntheticLambda10(i, 2, this));
            }
        }
    }

    public final class DarLockSettings {
        public final LockSettingsService service;

        public DarLockSettings(LockSettingsService lockSettingsService) {
            this.service = lockSettingsService;
        }

        public final int getSecureMode(int i) {
            int i2;
            synchronized (this.service.mSpManager) {
                try {
                    i2 = -1;
                    if (this.service.isSyntheticPasswordBasedCredentialLocked(i)) {
                        byte[] readSyntheticPasswordState = this.service.mSpManager.mStorage.readSyntheticPasswordState(i, "pwd", this.service.getCurrentLskfBasedProtectorId(i));
                        if (readSyntheticPasswordState == null) {
                            Slog.w("SyntheticPasswordManager.SDP", "getSecureMode: encountered empty password data for user " + i);
                        } else {
                            i2 = SyntheticPasswordManager.PasswordData.fromBytes(readSyntheticPasswordState).secureMode;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return i2;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void restoreEscrowDataIfNeededLocked(int r13) {
            /*
                Method dump skipped, instructions count: 312
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.locksettings.LockSettingsService.DarLockSettings.restoreEscrowDataIfNeededLocked(int):void");
        }

        public final void saveEscrowDataIfNeededLocked(int i) {
            LockSettingsService lockSettingsService = LockSettingsService.this;
            boolean z = false;
            boolean z2 = lockSettingsService.getLong("sdp-mdfppmode-for-system", 0L, 0) == 1 || lockSettingsService.getLong("sdp-mdfppmode-for-system", 0L, 0) >= 2;
            if (lockSettingsService.isEnterpriseUserId(i) && !SemPersonaManager.isDarDualEncryptionEnabled(i)) {
                z = true;
            }
            if (z2 || z || i != 0) {
                return;
            }
            SDPLog.d(null, "saveEscrowDataIfNeededLocked for system");
            LockSettingsService.m642$$Nest$mgetDarManagerService(this.service).ifPresent(new LockSettingsService$$ExternalSyntheticLambda11(i, 1));
        }

        public final VerifyCredentialResponse setSecureFolderLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
            LockSettingsService lockSettingsService = this.service;
            SDPLog.i("Set credential for secure folder user " + i);
            SDPLog.p("credential", lockscreenCredential == null ? null : lockscreenCredential.getCredential(), "credentialType", lockscreenCredential == null ? null : Integer.valueOf(lockscreenCredential.getType()), "savedCredential", lockscreenCredential2 == null ? null : lockscreenCredential2.getCredential(), "userId", Integer.valueOf(i));
            long clearCallingIdentity = Binder.clearCallingIdentity();
            VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
            try {
                if (lockscreenCredential != null) {
                    try {
                    } catch (Exception e) {
                        SDPLog.e(e, null, "Unexpected exception while set sf credential");
                    }
                    if (!lockscreenCredential.isNone() && (lockscreenCredential2 == null || lockscreenCredential2.isNone())) {
                        if (lockSettingsService.isSyntheticPasswordBasedCredentialLocked(i)) {
                            SDPLog.d(null, "Secure Folder already has sp based credential");
                            if (lockSettingsService.mDarLockSettings.setSecureFolderLockViaProtector(lockscreenCredential, i)) {
                                verifyCredentialResponse = VerifyCredentialResponse.OK;
                            }
                        } else if (!lockSettingsService.isSyntheticPasswordBasedCredentialLocked(i)) {
                            SDPLog.d(null, "Secure Folder seems to need sp initialization first");
                            verifyCredentialResponse = VerifyCredentialResponse.SKIP;
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        SDPLog.d(null, String.format("Result of setting credential for secure folder user %d : %s", Integer.valueOf(i), verifyCredentialResponse.toString()));
                        return verifyCredentialResponse;
                    }
                }
                SDPLog.d(null, "Seems normal case, skip handling");
                verifyCredentialResponse = VerifyCredentialResponse.SKIP;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                SDPLog.d(null, String.format("Result of setting credential for secure folder user %d : %s", Integer.valueOf(i), verifyCredentialResponse.toString()));
                return verifyCredentialResponse;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final boolean setSecureFolderLockViaProtector(LockscreenCredential lockscreenCredential, int i) {
            long j;
            boolean m643$$Nest$msetLockCredentialWithToken;
            long j2;
            SDPLog.i("Set credential via trusted domain for SecureFolder user " + i);
            SDPLog.p("credential,", (lockscreenCredential == null ? LockscreenCredential.createNone() : lockscreenCredential).getCredential(), "credentialType", Integer.valueOf((lockscreenCredential == null ? LockscreenCredential.createNone() : lockscreenCredential).getType()), "userId", Integer.valueOf(i));
            LockSettingsService lockSettingsService = this.service;
            if (LockSettingsService.m642$$Nest$mgetDarManagerService(lockSettingsService).isPresent()) {
                DarManagerService darManagerService = lockSettingsService.mDarManagerService;
                darManagerService.checkSystemPermission();
                byte[] specificKeyViaProtector = darManagerService.getSpecificKeyViaProtector("SdpTokenHandle", i);
                if (specificKeyViaProtector != null) {
                    ByteOrder byteOrder = BytesUtil.DEFAULT_BYTE_ORDER;
                    ByteBuffer allocate = ByteBuffer.allocate(8);
                    allocate.order(BytesUtil.DEFAULT_BYTE_ORDER);
                    allocate.put(specificKeyViaProtector);
                    allocate.flip();
                    j2 = allocate.getLong();
                } else {
                    Log.d("DarManagerService", "get SecureFolder Token Handle Failed");
                    j2 = 0;
                }
                j = j2;
            } else {
                j = 0;
            }
            if (j == 0) {
                SDPLog.d(null, "Failed to get SecureFolder token handle");
            } else {
                DarManagerService darManagerService2 = lockSettingsService.mDarManagerService;
                darManagerService2.checkSystemPermission();
                byte[] specificKeyViaProtector2 = darManagerService2.getSpecificKeyViaProtector("SdpResetToken", i);
                if (!SecureUtil.isEmpty(specificKeyViaProtector2)) {
                    m643$$Nest$msetLockCredentialWithToken = LockSettingsService.m643$$Nest$msetLockCredentialWithToken(this.service, lockscreenCredential, j, specificKeyViaProtector2, i);
                    SecureUtil.clear(specificKeyViaProtector2);
                    SDPLog.d(null, String.format("Result of setting credential via protector for SecureFolder user %d : [ res : %s ]", Integer.valueOf(i), Boolean.valueOf(m643$$Nest$msetLockCredentialWithToken)));
                    return m643$$Nest$msetLockCredentialWithToken;
                }
                SDPLog.d(null, "Failed to get SecureFolder reset token");
            }
            m643$$Nest$msetLockCredentialWithToken = false;
            SDPLog.d(null, String.format("Result of setting credential via protector for SecureFolder user %d : [ res : %s ]", Integer.valueOf(i), Boolean.valueOf(m643$$Nest$msetLockCredentialWithToken)));
            return m643$$Nest$msetLockCredentialWithToken;
        }
    }

    public final class DeviceProvisionedObserver extends ContentObserver {
        public final Uri mDeviceProvisionedUri;
        public boolean mRegistered;

        public DeviceProvisionedObserver() {
            super(null);
            this.mDeviceProvisionedUri = Settings.Global.getUriFor("device_provisioned");
        }

        public final boolean isProvisioned() {
            return Settings.Global.getInt(LockSettingsService.this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            if (this.mDeviceProvisionedUri.equals(uri)) {
                updateRegistration();
                if (isProvisioned()) {
                    Slog.i("LockSettingsService", "Reporting device setup complete to IGateKeeperService");
                    try {
                        LockSettingsService.this.getGateKeeperService().reportDeviceSetupComplete();
                    } catch (RemoteException e) {
                        Slog.e("LockSettingsService", "Failure reporting to IGateKeeperService", e);
                    }
                    for (UserInfo userInfo : LockSettingsService.this.mUserManager.getUsers()) {
                        if (LockPatternUtils.userOwnsFrpCredential(LockSettingsService.this.mContext, userInfo)) {
                            if (LockSettingsService.this.isUserSecure(userInfo.id)) {
                                return;
                            }
                            Slogf.d("LockSettingsService", "Clearing FRP credential tied to user %d", Integer.valueOf(userInfo.id));
                            LockSettingsService.this.mStorage.writePersistentDataBlock(0, userInfo.id, 0, null);
                            return;
                        }
                    }
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
    }

    public final class DualDARCallback implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId = 0;
        public Object mCallback;

        public DualDARCallback(LockSettingsService lockSettingsService) {
            this.mCallback = lockSettingsService;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            switch (this.$r8$classId) {
                case 0:
                    IDualDarAuthProgressCallback iDualDarAuthProgressCallback = (IDualDarAuthProgressCallback) this.mCallback;
                    Optional.ofNullable(iDualDarAuthProgressCallback == null ? null : iDualDarAuthProgressCallback.asBinder()).ifPresent(new LockSettingsService$DualDARCallback$$ExternalSyntheticLambda0(this, 1));
                    break;
                default:
                    ((LockSettingsService) this.mCallback).mGateKeeperService.asBinder().unlinkToDeath(this, 0);
                    ((LockSettingsService) this.mCallback).mGateKeeperService = null;
                    break;
            }
        }
    }

    public final class Injector {
        public Context mContext;
        public Handler mHandler;
        public ServiceThread mHandlerThread;
        public PersonaManagerService mPersonaManagerService;

        /* renamed from: com.android.server.locksettings.LockSettingsService$Injector$1, reason: invalid class name */
        public final class AnonymousClass1 implements RebootEscrowManager.Callbacks {
            public Object val$storage;

            public /* synthetic */ AnonymousClass1(Object obj) {
                this.val$storage = obj;
            }

            public static void prepare(int i) {
                File file = new File(new File(Environment.getDataSystemDirectory(), "users"), Integer.toString(i));
                if (file.exists()) {
                    return;
                }
                if (file.mkdir()) {
                    FileUtils.setPermissions(file.getPath(), 505, -1, -1);
                } else {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "prepare - failed to create sp state path for user ", "LockSettingsService.DarVirtualLock");
                }
            }

            public void activateEscrowTokenForDualDAR(long j, int i, byte[] bArr, byte[] bArr2) {
                LockSettingsService lockSettingsService = (LockSettingsService) this.val$storage;
                if (lockSettingsService.isDualDarAuthUserId(i)) {
                    if (DualDARController.getInstance(lockSettingsService.mContext).setResetPasswordToken(lockSettingsService.mDualDarAuthUtils.getMainUserId(i), bArr2, j, bArr)) {
                        DDLog.d("LockSettingsService", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Successfully activated reset pwd token for Dual DAR user: "), new Object[0]);
                    } else {
                        DDLog.e("LockSettingsService", BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Failed to activate escrow token for Dual DAR user: ", " because client failed to activate reset pwd token"), new Object[0]);
                        lockSettingsService.removeEscrowToken(j, i);
                    }
                }
            }

            public void activateEscrowTokensForDualDAR(SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i, byte[] bArr) {
                DDLog.d("LockSettingsService", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "activateEscrowTokens: user="), new Object[0]);
                synchronized (((LockSettingsService) this.val$storage).mSpManager) {
                    try {
                        ((LockSettingsService) this.val$storage).disableEscrowTokenOnNonManagedDevicesIfNeeded(i);
                        for (Long l : ((LockSettingsService) this.val$storage).mSpManager.getPendingTokensForUser(i)) {
                            long longValue = l.longValue();
                            DDLog.i("LockSettingsService", String.format("activateEscrowTokens: %x %d ", l, Integer.valueOf(i)), new Object[0]);
                            byte[] pendingTokenForDualDAR = getPendingTokenForDualDAR(i, longValue);
                            if (((LockSettingsService) this.val$storage).mSpManager.createTokenBasedProtector(i, longValue, syntheticPassword)) {
                                activateEscrowTokenForDualDAR(longValue, i, pendingTokenForDualDAR, bArr);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public VerifyCredentialResponse doVerifyCredentialForDualDAR(LockscreenCredential lockscreenCredential, int i) {
                VerifyCredentialResponse verifyCredentialResponse;
                if (!isDualDARUser(i) || !SemPersonaManager.isDarDualEncrypted(i) || DualDarManager.isOnDeviceOwnerEnabled()) {
                    return VerifyCredentialResponse.OK;
                }
                synchronized (((LockSettingsService) this.val$storage).mSpManager) {
                    try {
                        if (!((LockSettingsService) this.val$storage).isSyntheticPasswordBasedCredentialLocked(i)) {
                            return null;
                        }
                        long currentLskfBasedProtectorId = ((LockSettingsService) this.val$storage).getCurrentLskfBasedProtectorId(i);
                        LockSettingsService lockSettingsService = (LockSettingsService) this.val$storage;
                        SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = lockSettingsService.mSpManager.unlockLskfBasedProtector(lockSettingsService.getGateKeeperService(), currentLskfBasedProtectorId, lockscreenCredential, i, null);
                        if (((LockSettingsService) this.val$storage).getCredentialType(i) != lockscreenCredential.getType()) {
                            DDLog.e("LockSettingsService", "Credential type mismatch.", new Object[0]);
                            return VerifyCredentialResponse.ERROR;
                        }
                        VerifyCredentialResponse verifyCredentialResponse2 = unlockLskfBasedProtector.gkResponse;
                        if (verifyCredentialResponse2.getResponseCode() == 0) {
                            byte[] credential = lockscreenCredential.getCredential();
                            Slog.d("LockSettingsService", "onPassword2Auth()");
                            if (!isDualDARUser(i)) {
                                AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "User is not DualDAR eligible. so no need to verify DualDAR Passwords", "LockSettingsService");
                                verifyCredentialResponse = VerifyCredentialResponse.OK;
                            } else if (DualDARController.getInstance(((LockSettingsService) this.val$storage).mContext).onPassword2Auth(i, credential)) {
                                Slog.d("LockSettingsService", "onPassword2Auth completed sucessfully");
                                StateMachine.processEvent(i, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
                                verifyCredentialResponse = VerifyCredentialResponse.OK;
                            } else {
                                Slog.e("LockSettingsService", "Authentication Failure by dual dar client");
                                verifyCredentialResponse = VerifyCredentialResponse.ERROR;
                            }
                            verifyCredentialResponse2 = verifyCredentialResponse;
                            if (verifyCredentialResponse2.getResponseCode() != 0) {
                                DDLog.e("LockSettingsService", "verifyChallenge for DualDAR failed.", new Object[0]);
                                return VerifyCredentialResponse.ERROR;
                            }
                        }
                        if (verifyCredentialResponse2.isMatched()) {
                            return !((LockSettingsService) this.val$storage).mDarVirtualLock.onCredentialVerifiedForInner(lockscreenCredential, i) ? VerifyCredentialResponse.ERROR : verifyCredentialResponse2;
                        }
                        ((LockSettingsService) this.val$storage).mDarVirtualLock.onCredentialMismatchedForInner(i);
                        return verifyCredentialResponse2;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public byte[] getPendingTokenForDualDAR(int i, long j) {
                byte[] pendingTokenForDualDar;
                synchronized (((LockSettingsService) this.val$storage).mSpManager) {
                    pendingTokenForDualDar = ((LockSettingsService) this.val$storage).mSpManager.getPendingTokenForDualDar(i, j);
                }
                if (((LockSettingsService) this.val$storage).mDarLockSettings.getSecureMode(i) > 0) {
                    return pendingTokenForDualDar;
                }
                int length = pendingTokenForDualDar != null ? pendingTokenForDualDar.length - 64 : 0;
                if (length <= 0) {
                    return pendingTokenForDualDar;
                }
                byte[] bArr = new byte[length];
                System.arraycopy(pendingTokenForDualDar, 0, bArr, 0, length);
                SparseArray sparseArray = SyntheticPasswordMdfpp.mSecureModeCache;
                if (length == 0) {
                    return bArr;
                }
                Charset charset = StandardCharsets.UTF_8;
                return new SP800Derive(bArr).withContext("KeyEncryptionKey".getBytes(charset), "ForResetPasswordToken".getBytes(charset));
            }

            public SyntheticPasswordManager.SyntheticPassword initializeSyntheticPasswordForVirtualUser(int i) {
                SyntheticPasswordManager.SyntheticPassword newSyntheticPassword;
                SDPLog.i("Initialize sp for virtual user " + i);
                synchronized (((LockSettingsService) this.val$storage).mSpManager) {
                    newSyntheticPassword = ((LockSettingsService) this.val$storage).mSpManager.newSyntheticPassword(i);
                    LockSettingsService lockSettingsService = (LockSettingsService) this.val$storage;
                    ((LockSettingsService) this.val$storage).setCurrentLskfBasedProtectorId(i, lockSettingsService.mSpManager.createLskfBasedProtector(lockSettingsService.getGateKeeperService(), null, 0L, LockscreenCredential.createNone(), newSyntheticPassword, i));
                    ((LockSettingsService) this.val$storage).onSyntheticPasswordKnown(i, newSyntheticPassword, true);
                    SDPLog.i("Successfully initialized sp for virtual user " + i);
                }
                return newSyntheticPassword;
            }

            public boolean isDualDARUser(int i) {
                ((LockSettingsService) this.val$storage).mInjector.getClass();
                UserInfo userInfo = Injector.getUserManagerInternal().getUserInfo(i);
                return userInfo != null && (userInfo.flags & 100663296) > 0;
            }

            public boolean isInnerAuthUserForDualDarDo(int i) {
                return DualDarManager.isOnDeviceOwnerEnabled() && ((LockSettingsService) this.val$storage).mDualDarAuthUtils.isInnerAuthUserForDo(i);
            }

            public void migrateWithAuthToken(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
                if (syntheticPassword == null) {
                    return;
                }
                boolean isVirtualUserId = UserManager.isVirtualUserId(i);
                LockSettingsService lockSettingsService = (LockSettingsService) this.val$storage;
                if (isVirtualUserId || (lockSettingsService.isEnterpriseUserId(i) && !lockSettingsService.mDualDarLockSettings.isDualDARUser(i))) {
                    lockSettingsService.mSpManager.getClass();
                    if (SystemProperties.getBoolean("security.securehw.available", false)) {
                        DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(LocalServices.getService(SdpManagerInternal.class));
                        Optional.ofNullable(null).ifPresent(new LockSettingsService$Lifecycle$$ExternalSyntheticLambda0(2));
                    }
                }
            }

            public void onCredentialMismatchedForInner(int i) {
                DualDARCallback dualDARCallback;
                if (isInnerAuthUserForDualDarDo(i)) {
                    synchronized (((LockSettingsService) this.val$storage).mPendingVerifiedResults) {
                        PendingVerifiedResult pendingVerifiedResult = (PendingVerifiedResult) ((LockSettingsService) this.val$storage).mPendingVerifiedResults.get(i);
                        if (pendingVerifiedResult != null && (dualDARCallback = pendingVerifiedResult.mDualDARCallback) != null) {
                            try {
                                IDualDarAuthProgressCallback iDualDarAuthProgressCallback = (IDualDarAuthProgressCallback) dualDARCallback.mCallback;
                                if (iDualDarAuthProgressCallback != null) {
                                    iDualDarAuthProgressCallback.onInnerLayerUnlockFailed();
                                }
                            } catch (Exception e) {
                                SDPLog.e(e, "LockSettingsService.DarVirtualLock", "Callback might be dead...");
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
                int mainUserId = ((LockSettingsService) this.val$storage).mDualDarAuthUtils.getMainUserId(i);
                if (!DualDARController.getInstance(((LockSettingsService) this.val$storage).mContext).onPassword2Auth(mainUserId, lockscreenCredential.isNone() ? null : lockscreenCredential.getCredential())) {
                    DDLog.e("LockSettingsService.DarVirtualLock", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Failed in inner-layer authentication with auth user "), new Object[0]);
                    return false;
                }
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Inner-layer authenticated with auth user ", "LockSettingsService.DarVirtualLock");
                ((LockSettingsService) this.val$storage).setUserPasswordMetrics(lockscreenCredential, i);
                synchronized (((LockSettingsService) this.val$storage).mPendingVerifiedResults) {
                    PendingVerifiedResult pendingVerifiedResult = (PendingVerifiedResult) ((LockSettingsService) this.val$storage).mPendingVerifiedResults.get(mainUserId);
                    if (pendingVerifiedResult != null) {
                        ((LockSettingsService) this.val$storage).onCredentialVerified(pendingVerifiedResult.mSyntheticPassword, pendingVerifiedResult.mPasswordMetrics, mainUserId);
                        StateMachine.processEvent(mainUserId, Event.DEVICE_AUTH_SUCCESS);
                        StateMachine.processEvent(mainUserId, Event.DDAR_WORKSPACE_AUTH_SUCCESS);
                        DualDARCallback dualDARCallback = pendingVerifiedResult.mDualDARCallback;
                        try {
                            if (dualDARCallback != null) {
                                try {
                                    IDualDarAuthProgressCallback iDualDarAuthProgressCallback = (IDualDarAuthProgressCallback) dualDARCallback.mCallback;
                                    if (iDualDarAuthProgressCallback != null) {
                                        iDualDarAuthProgressCallback.onInnerLayerUnlocked();
                                    }
                                    dualDARCallback.mCallback = null;
                                    sparseArray = ((LockSettingsService) this.val$storage).mPendingVerifiedResults;
                                } catch (Exception e) {
                                    SDPLog.e(e, "LockSettingsService.DarVirtualLock", "Callback might be dead...");
                                    dualDARCallback.mCallback = null;
                                    sparseArray = ((LockSettingsService) this.val$storage).mPendingVerifiedResults;
                                }
                                sparseArray.remove(mainUserId);
                            }
                        } catch (Throwable th) {
                            dualDARCallback.mCallback = null;
                            ((LockSettingsService) this.val$storage).mPendingVerifiedResults.remove(mainUserId);
                            throw th;
                        }
                    }
                }
                return true;
            }

            public void onRebootEscrowRestored(byte b, byte[] bArr, int i) {
                SyntheticPasswordManager.SyntheticPassword syntheticPassword = new SyntheticPasswordManager.SyntheticPassword(b);
                syntheticPassword.mSyntheticPassword = Arrays.copyOf(bArr, bArr.length);
                synchronized (((LockSettingsService) this.val$storage).mSpManager) {
                    LockSettingsService lockSettingsService = (LockSettingsService) this.val$storage;
                    lockSettingsService.mSpManager.verifyChallenge(lockSettingsService.getGateKeeperService(), syntheticPassword, i);
                }
                Slogf.i("LockSettingsService", "Restored synthetic password for user %d using reboot escrow", Integer.valueOf(i));
                LockSettingsService lockSettingsService2 = (LockSettingsService) this.val$storage;
                lockSettingsService2.onCredentialVerified(syntheticPassword, lockSettingsService2.loadPasswordMetrics(i, syntheticPassword), i);
            }

            public void performDualDARPasswordChange(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) {
                boolean z2;
                boolean z3 = false;
                int i2 = 0;
                z3 = false;
                DDLog.d("LockSettingsService", "performDualDARPasswordChange ", new Object[0]);
                if (UserManager.isVirtualUserId(i)) {
                    AnonymousClass1 anonymousClass1 = ((LockSettingsService) this.val$storage).mDarVirtualLock;
                    if (anonymousClass1.isInnerAuthUserForDualDarDo(i)) {
                        LockSettingsService lockSettingsService = (LockSettingsService) anonymousClass1.val$storage;
                        z2 = DualDARController.getInstance(lockSettingsService.mContext).onPassword2Change(lockSettingsService.mDualDarAuthUtils.getMainUserId(i), lockscreenCredential2.getCredential(), lockscreenCredential.getCredential());
                        if (z2) {
                            lockSettingsService.setUserPasswordMetrics(lockscreenCredential, i);
                        }
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        return;
                    }
                    DDLog.e("LockSettingsService", "Unexpected failure while change password", new Object[0]);
                    return;
                }
                if (i == 0) {
                    if (DualDarManager.isOnDeviceOwner(i)) {
                        DDLog.d("LockSettingsService", "Skipped in DualDAR on DO case", new Object[0]);
                        return;
                    }
                    DualDARController dualDARController = DualDARController.getInstance(((LockSettingsService) this.val$storage).mContext);
                    if (lockscreenCredential != null && !lockscreenCredential.isNone()) {
                        z3 = true;
                    }
                    dualDARController.onPassword1Change(i, z3);
                    return;
                }
                if (isDualDARUser(i)) {
                    byte[] credential = lockscreenCredential2.isNone() ? null : lockscreenCredential2.getCredential();
                    byte[] credential2 = lockscreenCredential.isNone() ? null : lockscreenCredential.getCredential();
                    Slog.d("LockSettingsService", "onPassword2Change()");
                    boolean onPassword2Change = DualDARController.getInstance(((LockSettingsService) this.val$storage).mContext).onPassword2Change(i, credential, credential2);
                    if (onPassword2Change) {
                        Slog.d("LockSettingsService", "Authentication Change to DualDAR Client Successful");
                    } else {
                        Slog.e("LockSettingsService", "Authentication Change Failure by dual dar client");
                    }
                    if (onPassword2Change) {
                        return;
                    }
                    DDLog.e("LockSettingsService", "Change profile password failed by DualDAR Client", new Object[0]);
                    try {
                        i2 = LockPatternUtils.credentialTypeToPasswordQuality(lockscreenCredential2.getType());
                    } catch (IllegalStateException e) {
                        DDLog.d("LockSettingsService", "IllegalStateException : get credential quality : " + e.getMessage(), new Object[0]);
                    }
                    synchronized (((LockSettingsService) this.val$storage).mSpManager) {
                        ((LockSettingsService) this.val$storage).setLong("lockscreen.password_type", i2, i);
                        ((LockSettingsService) this.val$storage).setLockCredentialInternal(lockscreenCredential2, lockscreenCredential, i, z);
                    }
                    throw new RemoteException("Change profile password failed by DualDAR Client");
                }
            }

            public boolean setLockCredentialWithTokenInternalForDualDAR(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
                byte[] bArr2;
                if (!((LockSettingsService) this.val$storage).isDualDarAuthUserId(i)) {
                    return true;
                }
                synchronized (((LockSettingsService) this.val$storage).mSpManager) {
                    try {
                        LockSettingsService lockSettingsService = (LockSettingsService) this.val$storage;
                        if (lockSettingsService.mSpManager.unlockTokenBasedProtector(i, j, lockSettingsService.getGateKeeperService(), bArr).syntheticPassword == null) {
                            String str = "";
                            try {
                                str = String.format("Invalid escrow token supplied", new Object[0]);
                            } catch (Exception e) {
                                Log.w("DDLog", "format error. reason = " + e.getMessage() + ", format = Invalid escrow token supplied");
                            }
                            DDLog.Logger.enqueLog("LockSettingsService: " + str + "\n");
                            Log.w("LockSettingsService", str);
                            return false;
                        }
                        ((LockSettingsService) this.val$storage).mSpManager.getClass();
                        SparseArray sparseArray = SyntheticPasswordMdfpp.mSecureModeCache;
                        if (bArr == null || bArr.length == 0) {
                            bArr2 = bArr;
                        } else {
                            Charset charset = StandardCharsets.UTF_8;
                            bArr2 = new SP800Derive(bArr).withContext("KeyEncryptionKey".getBytes(charset), "ForResetPasswordToken".getBytes(charset));
                        }
                        if (bArr2 == null) {
                            DDLog.e("LockSettingsService", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Dual DAR Client failed to get reset token for user: "), new Object[0]);
                            return false;
                        }
                        if (DualDARController.getInstance(((LockSettingsService) this.val$storage).mContext).resetPasswordWithToken(((LockSettingsService) this.val$storage).mDualDarAuthUtils.getMainUserId(i), lockscreenCredential.getCredential(), j, bArr2)) {
                            DDLog.d("LockSettingsService", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Dual DAR Client successfully reset password with token for user: "), new Object[0]);
                            return true;
                        }
                        DDLog.e("LockSettingsService", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Dual DAR Client failed to reset password with token for user: "), new Object[0]);
                        return false;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public static UserManagerInternal getUserManagerInternal() {
            return (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }

        public final DevicePolicyManager getDevicePolicyManager() {
            return (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        }

        public final Optional getPersonaService() {
            if (this.mPersonaManagerService == null) {
                this.mPersonaManagerService = ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
            }
            return Optional.ofNullable(this.mPersonaManagerService);
        }
    }

    public final class Lifecycle extends SystemService {
        public LockSettingsService mLockSettingsService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            LockSettingsService lockSettingsService;
            ?? arrayList;
            super.onBootPhase(i);
            if (i == 550) {
                this.mLockSettingsService.migrateOldDataAfterSystemReady();
                this.mLockSettingsService.deleteRepairModePersistentDataIfNeeded();
                return;
            }
            if (i == 520) {
                LockSettingsService lockSettingsService2 = this.mLockSettingsService;
                synchronized (lockSettingsService2.mSpManager) {
                    lockSettingsService2.mSpManager.checkWeaverStatus();
                }
                LockSettingsService lockSettingsService3 = this.mLockSettingsService;
                lockSettingsService3.getClass();
                SDPLog.d(null, "Refresh weaver slots");
                synchronized (lockSettingsService3.mSpManager) {
                    SyntheticPasswordManager syntheticPasswordManager = lockSettingsService3.mSpManager;
                    syntheticPasswordManager.mPasswordSlotManager.refreshActiveSlots(syntheticPasswordManager.getUsedWeaverSlots());
                }
                this.mLockSettingsService.mInjector.getClass();
                if (PersonaServiceHelper.isDualDAREnabled()) {
                    LockSettingsService.m642$$Nest$mgetDarManagerService(this.mLockSettingsService).ifPresent(new LockSettingsService$Lifecycle$$ExternalSyntheticLambda0(1));
                }
                if (SemPersonaManager.getSecureFolderId(getContext()) > 0) {
                    LockSettingsService.m642$$Nest$mgetDarManagerService(this.mLockSettingsService).ifPresent(new LockSettingsService$Lifecycle$$ExternalSyntheticLambda0(0));
                    return;
                }
                return;
            }
            if (i == 1000) {
                LockSettingsService lockSettingsService4 = this.mLockSettingsService;
                final RebootEscrowManager rebootEscrowManager = lockSettingsService4.mRebootEscrowManager;
                final Handler handler = lockSettingsService4.mHandler;
                final List<UserInfo> users = rebootEscrowManager.mUserManager.getUsers();
                Injector.AnonymousClass1 anonymousClass1 = (Injector.AnonymousClass1) rebootEscrowManager.mCallbacks;
                boolean isUserSecure = ((LockSettingsService) anonymousClass1.val$storage).isUserSecure(0);
                RebootEscrowManager.Injector injector = rebootEscrowManager.mInjector;
                LockSettingsStorage lockSettingsStorage = rebootEscrowManager.mStorage;
                if (!isUserSecure || lockSettingsStorage.hasFile(lockSettingsStorage.getRebootEscrowFile(0))) {
                    HashSet hashSet = new HashSet();
                    Iterator it = users.iterator();
                    while (true) {
                        boolean hasNext = it.hasNext();
                        lockSettingsService = (LockSettingsService) anonymousClass1.val$storage;
                        if (!hasNext) {
                            break;
                        }
                        UserInfo userInfo = (UserInfo) it.next();
                        if (lockSettingsService.isUserSecure(userInfo.id) && !lockSettingsStorage.hasFile(lockSettingsStorage.getRebootEscrowFile(userInfo.id))) {
                            Slog.d("RebootEscrowManager", "No reboot escrow data found for user " + userInfo);
                            hashSet.add(Integer.valueOf(userInfo.id));
                        }
                    }
                    arrayList = new ArrayList();
                    for (UserInfo userInfo2 : users) {
                        if (lockSettingsService.isUserSecure(userInfo2.id)) {
                            int i2 = userInfo2.id;
                            if (!hashSet.contains(Integer.valueOf(i2)) && !hashSet.contains(Integer.valueOf(injector.mUserManagerInternal.getProfileParentId(i2)))) {
                                arrayList.add(userInfo2);
                            }
                        }
                    }
                } else {
                    Slog.i("RebootEscrowManager", "No reboot escrow data found for system user");
                    arrayList = Collections.emptyList();
                }
                if (arrayList.isEmpty()) {
                    Slog.i("RebootEscrowManager", "No reboot escrow data found for users, skipping loading escrow data");
                    rebootEscrowManager.setLoadEscrowDataErrorCode(10, handler);
                    rebootEscrowManager.reportMetricOnRestoreComplete(false, 1, handler);
                    rebootEscrowManager.clearMetricsStorage();
                    return;
                }
                PowerManager.WakeLock newWakeLock = ((PowerManager) injector.mContext.getSystemService(PowerManager.class)).newWakeLock(1, "RebootEscrowManager");
                rebootEscrowManager.mWakeLock = newWakeLock;
                if (newWakeLock != null) {
                    newWakeLock.setReferenceCounted(false);
                    rebootEscrowManager.mWakeLock.acquire(injector.getWakeLockTimeoutMillis());
                }
                if (!DeviceConfig.getBoolean("ota", "wait_for_internet_ror", false)) {
                    final int i3 = 1;
                    final List list = arrayList;
                    handler.post(new Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i3) {
                                case 0:
                                    final RebootEscrowManager rebootEscrowManager2 = rebootEscrowManager;
                                    final Handler handler2 = handler;
                                    final List list2 = users;
                                    final List list3 = list;
                                    RebootEscrowManager.Injector injector2 = rebootEscrowManager2.mInjector;
                                    if (!injector2.serverBasedResumeOnReboot()) {
                                        rebootEscrowManager2.loadRebootEscrowDataWithRetry(handler2, 0, list2, list3);
                                        break;
                                    } else {
                                        ?? r4 = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.locksettings.RebootEscrowManager.1
                                            @Override // android.net.ConnectivityManager.NetworkCallback
                                            public final void onAvailable(Network network) {
                                                RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(handler2, 8, 0);
                                                RebootEscrowManager rebootEscrowManager3 = RebootEscrowManager.this;
                                                if (rebootEscrowManager3.mLoadEscrowDataWithRetry) {
                                                    return;
                                                }
                                                rebootEscrowManager3.mLoadEscrowDataWithRetry = true;
                                                rebootEscrowManager3.loadRebootEscrowDataWithRetry(handler2, 0, list2, list3);
                                            }

                                            @Override // android.net.ConnectivityManager.NetworkCallback
                                            public final void onLost(Network network) {
                                                Slog.w("RebootEscrowManager", "Network lost, still attempting to load escrow key.");
                                                RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(handler2, 0, 8);
                                            }

                                            @Override // android.net.ConnectivityManager.NetworkCallback
                                            public final void onUnavailable() {
                                                Slog.w("RebootEscrowManager", "Failed to connect to network within timeout");
                                                RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(handler2, 0, 8);
                                                RebootEscrowManager.this.onGetRebootEscrowKeyFailed(list2, 0, handler2);
                                            }
                                        };
                                        rebootEscrowManager2.mNetworkCallback = r4;
                                        ConnectivityManager connectivityManager = (ConnectivityManager) injector2.mContext.getSystemService(ConnectivityManager.class);
                                        if (connectivityManager != 0) {
                                            connectivityManager.requestNetwork(new NetworkRequest.Builder().addCapability(12).build(), (ConnectivityManager.NetworkCallback) r4, injector2.getLoadEscrowTimeoutMillis());
                                            break;
                                        } else {
                                            rebootEscrowManager2.loadRebootEscrowDataWithRetry(handler2, 0, list2, list3);
                                            break;
                                        }
                                    }
                                default:
                                    rebootEscrowManager.loadRebootEscrowDataWithRetry(handler, 0, users, list);
                                    break;
                            }
                        }
                    });
                } else {
                    handler.postDelayed(new Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RebootEscrowManager.this.mRebootEscrowTimedOut = true;
                        }
                    }, injector.getLoadEscrowTimeoutMillis());
                    final int i4 = 0;
                    final List list2 = arrayList;
                    handler.post(new Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i4) {
                                case 0:
                                    final RebootEscrowManager rebootEscrowManager2 = rebootEscrowManager;
                                    final Handler handler2 = handler;
                                    final List list22 = users;
                                    final List list3 = list2;
                                    RebootEscrowManager.Injector injector2 = rebootEscrowManager2.mInjector;
                                    if (!injector2.serverBasedResumeOnReboot()) {
                                        rebootEscrowManager2.loadRebootEscrowDataWithRetry(handler2, 0, list22, list3);
                                        break;
                                    } else {
                                        ?? r4 = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.locksettings.RebootEscrowManager.1
                                            @Override // android.net.ConnectivityManager.NetworkCallback
                                            public final void onAvailable(Network network) {
                                                RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(handler2, 8, 0);
                                                RebootEscrowManager rebootEscrowManager3 = RebootEscrowManager.this;
                                                if (rebootEscrowManager3.mLoadEscrowDataWithRetry) {
                                                    return;
                                                }
                                                rebootEscrowManager3.mLoadEscrowDataWithRetry = true;
                                                rebootEscrowManager3.loadRebootEscrowDataWithRetry(handler2, 0, list22, list3);
                                            }

                                            @Override // android.net.ConnectivityManager.NetworkCallback
                                            public final void onLost(Network network) {
                                                Slog.w("RebootEscrowManager", "Network lost, still attempting to load escrow key.");
                                                RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(handler2, 0, 8);
                                            }

                                            @Override // android.net.ConnectivityManager.NetworkCallback
                                            public final void onUnavailable() {
                                                Slog.w("RebootEscrowManager", "Failed to connect to network within timeout");
                                                RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(handler2, 0, 8);
                                                RebootEscrowManager.this.onGetRebootEscrowKeyFailed(list22, 0, handler2);
                                            }
                                        };
                                        rebootEscrowManager2.mNetworkCallback = r4;
                                        ConnectivityManager connectivityManager = (ConnectivityManager) injector2.mContext.getSystemService(ConnectivityManager.class);
                                        if (connectivityManager != 0) {
                                            connectivityManager.requestNetwork(new NetworkRequest.Builder().addCapability(12).build(), (ConnectivityManager.NetworkCallback) r4, injector2.getLoadEscrowTimeoutMillis());
                                            break;
                                        } else {
                                            rebootEscrowManager2.loadRebootEscrowDataWithRetry(handler2, 0, list22, list3);
                                            break;
                                        }
                                    }
                                default:
                                    rebootEscrowManager.loadRebootEscrowDataWithRetry(handler, 0, users, list2);
                                    break;
                            }
                        }
                    });
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            AndroidKeyStoreProvider.install();
            Context context = getContext();
            Injector injector = new Injector();
            injector.mContext = context;
            ?? lockSettingsService = new LockSettingsService(injector);
            this.mLockSettingsService = lockSettingsService;
            publishBinderService("lock_settings", lockSettingsService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            LockSettingsService lockSettingsService = this.mLockSettingsService;
            int userIdentifier = targetUser.getUserIdentifier();
            boolean z = LockSettingsService.FIX_UNLOCKED_DEVICE_REQUIRED_KEYS;
            lockSettingsService.maybeShowEncryptionNotificationForUser(userIdentifier, "user started");
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            this.mLockSettingsService.onUserStopped(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            final LockSettingsService lockSettingsService = this.mLockSettingsService;
            final int userIdentifier = targetUser.getUserIdentifier();
            lockSettingsService.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService.2
                @Override // java.lang.Runnable
                public final void run() {
                    LockSettingsService lockSettingsService2 = LockSettingsService.this;
                    UserHandle userHandle = new UserHandle(userIdentifier);
                    lockSettingsService2.getClass();
                    Slogf.d("LockSettingsService", "Hiding encryption notification for user %d", Integer.valueOf(userHandle.getIdentifier()));
                    lockSettingsService2.mNotificationManager.cancelAsUser(null, 9, userHandle);
                    if (LockSettingsService.this.isCredentialSharableWithParent(userIdentifier)) {
                        LockSettingsService.this.tieProfileLockIfNecessary(LockscreenCredential.createNone(), userIdentifier);
                    }
                    LockSettingsService lockSettingsService3 = LockSettingsService.this;
                    if (userIdentifier != 77) {
                        lockSettingsService3.getClass();
                        Slog.i("LockSettingsService", "Not MaintenanceMode");
                    } else {
                        lockSettingsService3.mInjector.getClass();
                        Injector.getUserManagerInternal().addMaintenanceModeLifecycleListener(lockSettingsService3.mMaintenanceModeListener);
                        Slog.i("LockSettingsService", "addListener for MaintenanceMode");
                    }
                    LockSettingsService lockSettingsService4 = LockSettingsService.this;
                    int i = userIdentifier;
                    if (lockSettingsService4.mDualDarLockSettings.isDualDARUser(i)) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "Unlocking user - Dualdar user ", "LockSettingsService.SDP");
                        return;
                    }
                    if (i == 0 && StorageManager.isFileEncrypted() && !lockSettingsService4.isUserSecure(i)) {
                        for (UserInfo userInfo : lockSettingsService4.mUserManager.getProfiles(i)) {
                            if (userInfo.isSecureFolder()) {
                                lockSettingsService4.UnlockSecureFolderIfAutoDataDecryption(userInfo);
                            }
                        }
                    }
                }
            });
        }
    }

    public final class LocalService extends LockSettingsInternal {
        public LocalService() {
        }

        public final long addEscrowToken(byte[] bArr, int i, LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
            return LockSettingsService.this.addEscrowToken(bArr, 0, i, escrowTokenStateChangeCallback);
        }

        public final int armRebootEscrow() {
            RebootEscrowKey rebootEscrowKey;
            SecretKey keyStoreEncryptionKeyLocked;
            RebootEscrowManager rebootEscrowManager = LockSettingsService.this.mRebootEscrowManager;
            if (!rebootEscrowManager.mRebootEscrowReady) {
                return 2;
            }
            RebootEscrowManager.Injector injector = rebootEscrowManager.mInjector;
            RebootEscrowProviderInterface rebootEscrowProviderInterface = injector.mRebootEscrowProvider;
            if (rebootEscrowProviderInterface == null) {
                Slog.w("RebootEscrowManager", "Not storing escrow key, RebootEscrowProvider is unavailable");
                rebootEscrowManager.clearRebootEscrowIfNeeded();
                return 3;
            }
            boolean serverBasedResumeOnReboot = injector.serverBasedResumeOnReboot();
            int type = rebootEscrowProviderInterface.getType();
            if (serverBasedResumeOnReboot != type) {
                Slog.w("RebootEscrowManager", DualAppManagerService$$ExternalSyntheticOutline0.m(serverBasedResumeOnReboot ? 1 : 0, type, "Expect reboot escrow provider ", ", but the RoR is prepared with ", ". Please prepare the RoR again."));
                rebootEscrowManager.clearRebootEscrowIfNeeded();
                return 4;
            }
            synchronized (rebootEscrowManager.mKeyGenerationLock) {
                rebootEscrowKey = rebootEscrowManager.mPendingRebootEscrowKey;
            }
            if (rebootEscrowKey == null) {
                Slog.e("RebootEscrowManager", "Escrow key is null, but escrow was marked as ready");
                rebootEscrowManager.clearRebootEscrowIfNeeded();
                return 5;
            }
            synchronized (rebootEscrowManager.mKeyStoreManager.mKeyStoreLock) {
                keyStoreEncryptionKeyLocked = RebootEscrowKeyStoreManager.getKeyStoreEncryptionKeyLocked();
            }
            if (keyStoreEncryptionKeyLocked == null) {
                Slog.e("RebootEscrowManager", "Failed to get encryption key from keystore.");
                rebootEscrowManager.clearRebootEscrowIfNeeded();
                return 6;
            }
            if (!rebootEscrowProviderInterface.storeRebootEscrowKey(rebootEscrowKey, keyStoreEncryptionKeyLocked)) {
                return 7;
            }
            LockSettingsStorage lockSettingsStorage = rebootEscrowManager.mStorage;
            int i = Settings.Global.getInt(rebootEscrowManager.mInjector.mContext.getContentResolver(), "boot_count", 0);
            lockSettingsStorage.getClass();
            lockSettingsStorage.setString(RebootEscrowManager.REBOOT_ESCROW_ARMED_KEY, Integer.toString(i), 0);
            LockSettingsStorage lockSettingsStorage2 = rebootEscrowManager.mStorage;
            rebootEscrowManager.mInjector.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            lockSettingsStorage2.getClass();
            lockSettingsStorage2.setString("reboot_escrow_key_stored_timestamp", Long.toString(currentTimeMillis), 0);
            LockSettingsStorage lockSettingsStorage3 = rebootEscrowManager.mStorage;
            rebootEscrowManager.mInjector.getClass();
            lockSettingsStorage3.setString("reboot_escrow_key_vbmeta_digest", SystemProperties.get("ro.boot.vbmeta.digest"), 0);
            LockSettingsStorage lockSettingsStorage4 = rebootEscrowManager.mStorage;
            rebootEscrowManager.mInjector.getClass();
            lockSettingsStorage4.setString("reboot_escrow_key_other_vbmeta_digest", SystemProperties.get("ota.other.vbmeta_digest"), 0);
            LockSettingsStorage lockSettingsStorage5 = rebootEscrowManager.mStorage;
            lockSettingsStorage5.getClass();
            lockSettingsStorage5.setString("reboot_escrow_key_provider", Integer.toString(type), 0);
            RebootEscrowManager.RebootEscrowEventLog rebootEscrowEventLog = rebootEscrowManager.mEventLog;
            rebootEscrowEventLog.getClass();
            RebootEscrowManager.RebootEscrowEvent rebootEscrowEvent = new RebootEscrowManager.RebootEscrowEvent(2, null);
            int i2 = rebootEscrowEventLog.mNextIndex;
            RebootEscrowManager.RebootEscrowEvent[] rebootEscrowEventArr = rebootEscrowEventLog.mEntries;
            rebootEscrowEventArr[i2] = rebootEscrowEvent;
            rebootEscrowEventLog.mNextIndex = (i2 + 1) % rebootEscrowEventArr.length;
            return 0;
        }

        public final boolean clearRebootEscrow() {
            LockSettingsService.this.mRebootEscrowManager.clearRebootEscrowIfNeeded();
            LockSettingsStrongAuth lockSettingsStrongAuth = LockSettingsService.this.mStrongAuth;
            lockSettingsStrongAuth.getClass();
            lockSettingsStrongAuth.mHandler.obtainMessage(6, 64, -1).sendToTarget();
            return true;
        }

        public final void clearStorageForUser(int i) {
            DarLockSettings darLockSettings = LockSettingsService.this.mDarLockSettings;
            darLockSettings.getClass();
            boolean isSecureFolderId = SemPersonaManager.isSecureFolderId(i);
            LockSettingsService lockSettingsService = darLockSettings.service;
            if (isSecureFolderId || lockSettingsService.isEnterpriseUserId(i)) {
                lockSettingsService.mStorage.removeUser(i);
            }
        }

        public final void createNewUser(int i, int i2) {
            LockSettingsService lockSettingsService = LockSettingsService.this;
            if (LockSettingsService.FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                lockSettingsService.getClass();
                AndroidKeyStoreMaintenance.onUserAdded(i);
            }
            synchronized (lockSettingsService.mUserCreationAndRemovalLock) {
                try {
                    if (lockSettingsService.mThirdPartyAppsStarted) {
                        lockSettingsService.removeStateForReusedUserIdIfNecessary(i, i2);
                        lockSettingsService.initializeSyntheticPassword(i);
                    } else {
                        Slogf.i("LockSettingsService", "Delaying locksettings state creation for user %d until third-party apps are started", Integer.valueOf(i));
                        lockSettingsService.mEarlyCreatedUsers.put(i, i2);
                        lockSettingsService.mEarlyRemovedUsers.delete(i);
                    }
                } finally {
                }
            }
        }

        public final int getCredentialType(int i) {
            return LockSettingsService.this.getCredentialType(i);
        }

        public final int getSecureMode(int i) {
            return LockSettingsService.this.mDarLockSettings.getSecureMode(i);
        }

        public final PasswordMetrics getUserPasswordMetrics(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (LockSettingsService.this.isProfileWithUnifiedLock(i)) {
                    Slog.w("LockSettingsService", "Querying password metrics for unified challenge profile: " + i);
                }
                PasswordMetrics userPasswordMetrics = LockSettingsService.this.getUserPasswordMetrics(i);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return userPasswordMetrics;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final boolean isEscrowTokenActive(long j, int i) {
            return LockSettingsService.this.isEscrowTokenActive(j, i);
        }

        public final void onThirdPartyAppsStarted() {
            LockSettingsService lockSettingsService = LockSettingsService.this;
            lockSettingsService.getClass();
            Slog.w("LockSettingsService", "!@ onThirdPartyAppsStarted()");
            lockSettingsService.migrateLockSettingsDB();
            lockSettingsService.migrateSpblob();
            synchronized (lockSettingsService.mUserCreationAndRemovalLock) {
                for (int i = 0; i < lockSettingsService.mEarlyRemovedUsers.size(); i++) {
                    try {
                        int keyAt = lockSettingsService.mEarlyRemovedUsers.keyAt(i);
                        Slogf.i("LockSettingsService", "Removing locksettings state for removed user %d now that boot is complete", Integer.valueOf(keyAt));
                        lockSettingsService.removeUserState(keyAt);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                lockSettingsService.mEarlyRemovedUsers = null;
                for (int i2 = 0; i2 < lockSettingsService.mEarlyCreatedUsers.size(); i2++) {
                    int keyAt2 = lockSettingsService.mEarlyCreatedUsers.keyAt(i2);
                    lockSettingsService.removeStateForReusedUserIdIfNecessary(keyAt2, lockSettingsService.mEarlyCreatedUsers.valueAt(i2));
                    Slogf.i("LockSettingsService", "Creating locksettings state for user %d now that boot is complete", Integer.valueOf(keyAt2));
                    lockSettingsService.initializeSyntheticPassword(keyAt2);
                }
                lockSettingsService.mEarlyCreatedUsers = null;
                if (LockSettingsService.FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                    if (!lockSettingsService.getBoolean("migrated_all_users_to_sp_and_bound_keys", false, 0)) {
                        for (UserInfo userInfo : lockSettingsService.mUserManager.getAliveUsers()) {
                            lockSettingsService.removeStateForReusedUserIdIfNecessary(userInfo.id, userInfo.serialNumber);
                            synchronized (lockSettingsService.mSpManager) {
                                lockSettingsService.migrateUserToSpWithBoundKeysLocked(userInfo.id);
                            }
                        }
                        lockSettingsService.setBoolean("migrated_all_users_to_sp_and_bound_keys", true, 0);
                    }
                    lockSettingsService.mThirdPartyAppsStarted = true;
                } else {
                    if (lockSettingsService.getString("migrated_all_users_to_sp_and_bound_ce", null, 0) == null) {
                        for (UserInfo userInfo2 : lockSettingsService.mUserManager.getAliveUsers()) {
                            lockSettingsService.removeStateForReusedUserIdIfNecessary(userInfo2.id, userInfo2.serialNumber);
                            if (!lockSettingsService.mDualDarLockSettings.isDualDARUser(userInfo2.id) || DualDarManager.isOnDeviceOwner(userInfo2.id) || lockSettingsService.isUserSecure(userInfo2.id)) {
                                synchronized (lockSettingsService.mSpManager) {
                                    lockSettingsService.migrateUserToSpWithBoundCeKeyLocked(userInfo2.id);
                                }
                            } else {
                                lockSettingsService.setString("migrated_dualdar_user_to_sp_and_bound_ce", "false", userInfo2.id);
                                DDLog.d("LockSettingsService", "need to migrate unsecured DualDar user.", new Object[0]);
                            }
                        }
                        lockSettingsService.setString("migrated_all_users_to_sp_and_bound_ce", "true", 0);
                    }
                    if (lockSettingsService.getBoolean("migrated_all_users_to_sp_and_bound_keys", false, 0)) {
                        lockSettingsService.setBoolean("migrated_all_users_to_sp_and_bound_keys", false, 0);
                    }
                    lockSettingsService.mThirdPartyAppsStarted = true;
                }
            }
        }

        public final boolean prepareRebootEscrow() {
            RebootEscrowManager rebootEscrowManager = LockSettingsService.this.mRebootEscrowManager;
            rebootEscrowManager.clearRebootEscrowIfNeeded();
            if (rebootEscrowManager.mInjector.createRebootEscrowProviderIfNeeded() == null) {
                Slog.w("RebootEscrowManager", "No reboot escrow provider, skipping resume on reboot preparation.");
                return false;
            }
            rebootEscrowManager.mRebootEscrowWanted = true;
            RebootEscrowManager.RebootEscrowEventLog rebootEscrowEventLog = rebootEscrowManager.mEventLog;
            rebootEscrowEventLog.getClass();
            RebootEscrowManager.RebootEscrowEvent rebootEscrowEvent = new RebootEscrowManager.RebootEscrowEvent(5, null);
            int i = rebootEscrowEventLog.mNextIndex;
            RebootEscrowManager.RebootEscrowEvent[] rebootEscrowEventArr = rebootEscrowEventLog.mEntries;
            rebootEscrowEventArr[i] = rebootEscrowEvent;
            rebootEscrowEventLog.mNextIndex = (i + 1) % rebootEscrowEventArr.length;
            LockSettingsService.this.mStrongAuth.requireStrongAuth(64, -1);
            return true;
        }

        public final void refreshStrongAuthTimeout(int i) {
            LockSettingsService.this.mStrongAuth.mHandler.obtainMessage(10, i, 0).sendToTarget();
        }

        public final void registerLockSettingsStateListener(LockSettingsStateListener lockSettingsStateListener) {
            Objects.requireNonNull(lockSettingsStateListener, "listener cannot be null");
            LockSettingsService.this.mLockSettingsStateListeners.add(lockSettingsStateListener);
        }

        public final boolean removeEscrowToken(long j, int i) {
            return LockSettingsService.this.removeEscrowToken(j, i);
        }

        public final void removeUser(int i) {
            LockSettingsService lockSettingsService = LockSettingsService.this;
            synchronized (lockSettingsService.mUserCreationAndRemovalLock) {
                try {
                    if (lockSettingsService.mThirdPartyAppsStarted) {
                        Slogf.i("LockSettingsService", "Removing state for user %d", Integer.valueOf(i));
                        lockSettingsService.removeUserState(i);
                    } else {
                        Slogf.i("LockSettingsService", "Delaying locksettings state removal for user %d until third-party apps are started", Integer.valueOf(i));
                        if (lockSettingsService.mEarlyCreatedUsers.indexOfKey(i) >= 0) {
                            lockSettingsService.mEarlyCreatedUsers.delete(i);
                        } else {
                            lockSettingsService.mEarlyRemovedUsers.put(i, -1);
                        }
                    }
                } finally {
                }
            }
        }

        public final boolean setLockCredentialWithToken(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
            if (!LockSettingsService.this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
                throw new UnsupportedOperationException("This operation requires secure lock screen feature.");
            }
            if (!LockSettingsService.m643$$Nest$msetLockCredentialWithToken(LockSettingsService.this, lockscreenCredential, j, bArr, i)) {
                return false;
            }
            LockSettingsService.this.onPostPasswordChanged(lockscreenCredential, i);
            return true;
        }

        public final void setRebootEscrowListener(RebootEscrowListener rebootEscrowListener) {
            LockSettingsService.this.mRebootEscrowManager.mRebootEscrowListener = rebootEscrowListener;
        }

        public final boolean unlockUserWithToken(long j, byte[] bArr, int i) {
            boolean z;
            LockSettingsService lockSettingsService = LockSettingsService.this;
            synchronized (lockSettingsService.mSpManager) {
                try {
                    Slogf.i("LockSettingsService", "Unlocking user %d using escrow token %016x", Integer.valueOf(i), Long.valueOf(j));
                    z = false;
                    if (lockSettingsService.mSpManager.hasEscrowData(i)) {
                        SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = lockSettingsService.mSpManager.unlockTokenBasedProtector(i, j, lockSettingsService.getGateKeeperService(), bArr);
                        if (unlockTokenBasedProtector.syntheticPassword == null) {
                            Slog.w("LockSettingsService", "Invalid escrow token supplied");
                        } else {
                            z = true;
                            if (i != 0 && SemPersonaManager.isSecureFolderId(i)) {
                                unlockTokenBasedProtector.syntheticPassword.mSecureFolderAuthToken = true;
                            }
                            Slogf.i("LockSettingsService", "Unlocked synthetic password for user %d using escrow token", Integer.valueOf(i));
                            SyntheticPasswordManager.SyntheticPassword syntheticPassword = unlockTokenBasedProtector.syntheticPassword;
                            lockSettingsService.onCredentialVerified(syntheticPassword, lockSettingsService.loadPasswordMetrics(i, syntheticPassword), i);
                        }
                    } else {
                        Slogf.w("LockSettingsService", "Escrow token support is disabled on user %d", Integer.valueOf(i));
                    }
                } finally {
                }
            }
            return z;
        }

        public final void unregisterLockSettingsStateListener(LockSettingsStateListener lockSettingsStateListener) {
            LockSettingsService.this.mLockSettingsStateListeners.remove(lockSettingsStateListener);
        }
    }

    public final class PendingVerifiedResult {
        public final DualDARCallback mDualDARCallback;
        public final PasswordMetrics mPasswordMetrics;
        public final SyntheticPasswordManager.SyntheticPassword mSyntheticPassword;

        public PendingVerifiedResult(SyntheticPasswordManager.SyntheticPassword syntheticPassword, PasswordMetrics passwordMetrics, DualDARCallback dualDARCallback) {
            this.mSyntheticPassword = syntheticPassword;
            this.mPasswordMetrics = passwordMetrics;
            this.mDualDARCallback = dualDARCallback;
        }
    }

    public class SynchronizedStrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public final int getStrongAuthForUser(int i) {
            int strongAuthForUser;
            synchronized (this) {
                strongAuthForUser = super.getStrongAuthForUser(i);
            }
            return strongAuthForUser;
        }

        public final void handleStrongAuthRequiredChanged(int i, int i2) {
            synchronized (this) {
                super.handleStrongAuthRequiredChanged(i, i2);
            }
        }
    }

    public final class UpdateVerifierServiceConnection implements ServiceConnection {
        public final byte[] mCredential;
        public final int mVerifierType;
        public final BigInteger g = BigInteger.valueOf(5);
        public final BigInteger N = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AAAC42DAD33170D04507A33A85521ABDF1CBA64ECFB850458DBEF0A8AEA71575D060C7DB3970F85A6E1E4C7ABF5AE8CDB0933D71E8C94E04A25619DCEE3D2261AD2EE6BF12FFA06D98A0864D87602733EC86A64521F2B18177B200CBBE117577A615D6C770988C0BAD946E208E24FA074E5AB3143DB5BFCE0FD108E4B82D120A93AD2CAFFFFFFFFFFFFFFFF", 16);

        public UpdateVerifierServiceConnection(byte[] bArr, int i) {
            this.mCredential = bArr;
            this.mVerifierType = i;
        }

        public static BigInteger hash(byte[]... bArr) {
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

        public static BigInteger makeX(String str, String str2, byte[] bArr) {
            try {
                return hash(bArr, hash(str.getBytes(), ":".getBytes(), pbkdf2((str + str2).toCharArray(), str.getBytes())).toByteArray());
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(e, "exception to make X = ", "LockSettingsService");
                return null;
            }
        }

        public static byte[] pbkdf2(char[] cArr, byte[] bArr) {
            try {
                return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(new PBEKeySpec(cArr, bArr, 100000, 256)).getEncoded();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                Slog.e("LockSettingsService", "pbkdf2 error = " + e);
                return null;
            }
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            Slog.i("LockSettingsService", "onBindingDied");
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            Slog.i("LockSettingsService", "onNullBinding");
            unbindService();
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Slog.i("LockSettingsService", "onServiceConnected");
            final IUpdateVerifierInterface asInterface = IUpdateVerifierInterface.Stub.asInterface(iBinder);
            if (asInterface == null) {
                Slog.i("LockSettingsService", "bind failed");
                unbindService();
                return;
            }
            try {
                asInterface.requestSaGuid(new IUpdateVerifierCallback.Stub() { // from class: com.android.server.locksettings.LockSettingsService.UpdateVerifierServiceConnection.1
                    public final void onReceiveSaGuid(String str) {
                        byte[] bArr;
                        if (TextUtils.isEmpty(str)) {
                            UpdateVerifierServiceConnection.this.unbindService();
                            return;
                        }
                        UpdateVerifierServiceConnection.this.getClass();
                        byte[] randomBytes = SecureRandomUtils.randomBytes(32);
                        UpdateVerifierServiceConnection updateVerifierServiceConnection = UpdateVerifierServiceConnection.this;
                        byte[] bArr2 = null;
                        try {
                            bArr = updateVerifierServiceConnection.g.modPow(UpdateVerifierServiceConnection.makeX(str, new String(updateVerifierServiceConnection.mCredential, StandardCharsets.UTF_8), randomBytes), updateVerifierServiceConnection.N).toByteArray();
                        } catch (Exception e) {
                            BootReceiver$$ExternalSyntheticOutline0.m(e, "exception to make verifier = ", "LockSettingsService");
                            bArr = null;
                        }
                        try {
                            String str2 = new String(UpdateVerifierServiceConnection.this.mCredential, StandardCharsets.UTF_8);
                            bArr2 = UpdateVerifierServiceConnection.pbkdf2(str2.toCharArray(), UpdateVerifierServiceConnection.hash(UpdateVerifierServiceConnection.makeX(str, str2, randomBytes).toByteArray()).toByteArray());
                        } catch (Exception e2) {
                            BootReceiver$$ExternalSyntheticOutline0.m(e2, "exception to make wrapKey = ", "LockSettingsService");
                        }
                        asInterface.updateVerifierWithWk(bArr, randomBytes, bArr2, UpdateVerifierServiceConnection.this.mVerifierType);
                        UpdateVerifierServiceConnection.this.unbindService();
                    }
                });
            } catch (RemoteException | SecurityException e) {
                Slog.e("LockSettingsService", "onServiceConnected exception : " + e.getMessage());
                unbindService();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Slog.i("LockSettingsService", "onServiceDisconnected");
        }

        public final void unbindService() {
            try {
                LockSettingsService lockSettingsService = LockSettingsService.this;
                if (lockSettingsService.mShouldUnbind) {
                    lockSettingsService.mContext.unbindService(this);
                    LockSettingsService.this.mShouldUnbind = false;
                }
            } catch (Exception e) {
                NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("unbindService exception : "), "LockSettingsService");
            }
        }
    }

    /* renamed from: -$$Nest$mgetDarManagerService, reason: not valid java name */
    public static Optional m642$$Nest$mgetDarManagerService(LockSettingsService lockSettingsService) {
        if (lockSettingsService.mDarManagerService == null) {
            lockSettingsService.mInjector.getClass();
            lockSettingsService.mDarManagerService = (DarManagerService) ServiceManager.getService("dar");
        }
        return Optional.ofNullable(lockSettingsService.mDarManagerService);
    }

    /* renamed from: -$$Nest$msetLockCredentialWithToken, reason: not valid java name */
    public static boolean m643$$Nest$msetLockCredentialWithToken(LockSettingsService lockSettingsService, LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
        boolean z;
        lockSettingsService.getClass();
        SDPLog.i("Set lock credential with token for user " + i);
        SDPLog.p("credential", lockscreenCredential.getCredential(), "type", Integer.valueOf(lockscreenCredential.getType()), "tokenHandle", Long.valueOf(j), KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "userId", Integer.valueOf(i));
        boolean z2 = true;
        if (!UserManager.isVirtualUserId(i)) {
            lockscreenCredential.validateBasicRequirements();
            synchronized (lockSettingsService.mSpManager) {
                try {
                    lockSettingsService.mDarLockSettings.restoreEscrowDataIfNeededLocked(i);
                    if (!lockSettingsService.mSpManager.hasEscrowData(i)) {
                        throw new SecurityException("Escrow token is disabled on the current user");
                    }
                    if (!lockSettingsService.isEscrowTokenActive(j, i)) {
                        Slog.e("LockSettingsService", "Unknown or unactivated token: " + Long.toHexString(j));
                        return false;
                    }
                    boolean lockCredentialWithTokenInternalLocked = lockSettingsService.setLockCredentialWithTokenInternalLocked(lockscreenCredential, j, bArr, i);
                    if (lockCredentialWithTokenInternalLocked) {
                        synchronized (lockSettingsService.mSeparateChallengeLock) {
                            lockSettingsService.setSeparateProfileChallengeEnabledLocked(i, true, null);
                        }
                        if (lockscreenCredential.isNone()) {
                            lockSettingsService.mHandler.post(new LockSettingsService$$ExternalSyntheticLambda10(i, 1, lockSettingsService));
                        }
                        lockSettingsService.notifyPasswordChanged(lockscreenCredential, i);
                        lockSettingsService.notifySeparateProfileChallengeChanged(i);
                    }
                    SDPLog.d(null, String.format("Result of setting credential with token for user %d : %s", Integer.valueOf(i), Boolean.valueOf(lockCredentialWithTokenInternalLocked)));
                    return lockCredentialWithTokenInternalLocked;
                } finally {
                }
            }
        }
        Injector.AnonymousClass1 anonymousClass1 = lockSettingsService.mDarVirtualLock;
        if (anonymousClass1.isInnerAuthUserForDualDarDo(i)) {
            try {
                z = ((LockSettingsService) anonymousClass1.val$storage).mDualDarLockSettings.setLockCredentialWithTokenInternalForDualDAR(lockscreenCredential, j, bArr, i);
            } catch (RemoteException e) {
                DDLog.e("LockSettingsService.DarVirtualLock", "Exception : " + e.getMessage(), new Object[0]);
                z = false;
            }
            if (!z) {
                DDLog.e("LockSettingsService.DarVirtualLock", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Dual DAR Client failed to reset password with token for user: "), new Object[0]);
                z2 = false;
                return z2;
            }
        }
        synchronized (((LockSettingsService) anonymousClass1.val$storage).mSpManager) {
            try {
                LockSettingsService lockSettingsService2 = (LockSettingsService) anonymousClass1.val$storage;
                SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = lockSettingsService2.mSpManager.unlockTokenBasedProtector(i, j, lockSettingsService2.getGateKeeperService(), bArr);
                if (unlockTokenBasedProtector.syntheticPassword == null) {
                    SDPLog.d(null, "Invalid escrow token supplied");
                    z2 = false;
                } else {
                    long currentLskfBasedProtectorId = ((LockSettingsService) anonymousClass1.val$storage).getCurrentLskfBasedProtectorId(i);
                    ((LockSettingsService) anonymousClass1.val$storage).setLockCredentialWithSpLocked(lockscreenCredential, null, 0L, unlockTokenBasedProtector.syntheticPassword, i);
                    ((LockSettingsService) anonymousClass1.val$storage).mSpManager.destroyLskfBasedProtector(i, currentLskfBasedProtectorId);
                }
            } finally {
            }
        }
        return z2;
    }

    static {
        DEBUG_DUMP = "userdebug".equals(SystemProperties.get("ro.build.type")) || "eng".equals(SystemProperties.get("ro.build.type"));
        SYSTEM_CREDENTIAL_UIDS = new int[]{1016, 0, 1000};
        mIsDbMigrated = false;
        mIsSpblobMigrated = false;
    }

    public LockSettingsService(Injector injector) {
        RecoverableKeyStoreManager recoverableKeyStoreManager;
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.locksettings.LockSettingsService.3
            public final /* synthetic */ LockSettingsService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                boolean z;
                switch (i) {
                    case 0:
                        if ("android.intent.action.USER_ADDED".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                            if (!LockSettingsService.FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                                AndroidKeyStoreMaintenance.onUserAdded(intExtra);
                            }
                            if (SemPersonaManager.isDarDualEncryptionEnabled(intExtra)) {
                                AnyMotionDetector$$ExternalSyntheticOutline0.m(intExtra, "OnUserAdded(): Setup SeparateCredential for DualDAR User : ", "LockSettingsService");
                                this.this$0.setSeparateProfileChallengeEnabled(intExtra, true, null);
                                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("OnUserAdded(): Successfully added OLK to vold for DualDAR User : "), intExtra, "LockSettingsService");
                                break;
                            }
                        } else if ("android.intent.action.USER_STARTING".equals(intent.getAction())) {
                            this.this$0.mStorage.prefetchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                            break;
                        } else if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                            LockSettingsService lockSettingsService = this.this$0;
                            for (UserInfo userInfo : lockSettingsService.mUserManager.getUsers()) {
                                StatusBarNotification[] activeNotifications = ((NotificationManager) lockSettingsService.mContext.createContextAsUser(UserHandle.of(userInfo.id), 0).getSystemService("notification")).getActiveNotifications();
                                int length = activeNotifications.length;
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= length) {
                                        break;
                                    } else if (activeNotifications[i2].getId() == 9) {
                                        lockSettingsService.maybeShowEncryptionNotificationForUser(userInfo.id, "locale changed");
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                            break;
                        } else if (DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED.equals(intent.getAction())) {
                            int identifier = ((UserHandle) intent.getExtra("android.intent.extra.USER")).getIdentifier();
                            if (SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
                                AnyMotionDetector$$ExternalSyntheticOutline0.m(identifier, "reportEnabledTrustAgentsChanged for DualDAR User : ", "LockSettingsService");
                                ((TrustManager) this.this$0.mContext.getSystemService(TrustManager.class)).reportEnabledTrustAgentsChanged(identifier);
                                break;
                            }
                        }
                        break;
                    default:
                        if ("android.intent.action.DUMP_SYNTHETIC_PASSWORD".equals(intent.getAction())) {
                            Log.d("LockSettingsService.SDP", "onReceive :: android.intent.action.DUMP_SYNTHETIC_PASSWORD");
                            intent.getStringExtra("cred");
                            intent.getStringExtra(KnoxCustomManagerService.SPCM_KEY_TOKEN);
                            intent.getIntExtra("urid", -1);
                            this.this$0.mSpDump.getClass();
                            Log.d("SyntheticPasswordDump_SDP", "Dump not supported for this device");
                            break;
                        } else if ("android.intent.action.DUMP_DUALDAR_PASSWORD".equals(intent.getAction())) {
                            DDLog.d("LockSettingsService.DUALDAR", "onReceive :: android.intent.action.DUMP_DUALDAR_PASSWORD", new Object[0]);
                            int intExtra2 = intent.getIntExtra("urid", -1);
                            String stringExtra = intent.getStringExtra("path");
                            DualDARDaemonProxy dualDARDaemonProxy = DualDARDaemonProxy.getInstance(this.this$0.mContext);
                            if (dualDARDaemonProxy.mConnector == null) {
                                DDLog.e("DualDARDaemonProxy", "dumpSecret failed! Error: native interface not yet connected failed", new Object[0]);
                                break;
                            } else {
                                DDLog.d("DualDARDaemonProxy.DUALDAR", AccessibilityManagerService$$ExternalSyntheticOutline0.m(intExtra2, "dumpSecret() - userId : ", ", filePath : ", stringExtra), new Object[0]);
                                try {
                                    NativeDaemonEvent executeSync = dualDARDaemonProxy.mConnector.executeSync("key", "key_dump", Integer.valueOf(intExtra2), stringExtra);
                                    dualDARDaemonProxy.mEvent = executeSync;
                                    if (!executeSync.isClassOk()) {
                                        dualDARDaemonProxy.mEvent.getClass();
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                        } else if ("android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES".equals(intent.getAction())) {
                            DDLog.d("LockSettingsService.DUALDAR", "onReceive :: android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES", new Object[0]);
                            int intExtra3 = intent.getIntExtra("urid", -1);
                            DualDarDoPolicyChecker dualDarDoPolicyChecker = DualDarDoPolicyChecker.getInstance(this.this$0.mContext);
                            dualDarDoPolicyChecker.getClass();
                            if (!DualDarManager.isOnDeviceOwner(intExtra3)) {
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(intExtra3, "Not a DualDAR at DO user - ", "DualDarDoPolicyChecker");
                                break;
                            } else {
                                ArrayList arrayList = new ArrayList();
                                ArrayList arrayList2 = new ArrayList();
                                EnterprisePartitionManager enterprisePartitionManager = EnterprisePartitionManager.getInstance(DualDarDoPolicyChecker.sContext);
                                try {
                                    File[] listFiles = new File("/data/user/" + intExtra3).listFiles();
                                    int length2 = listFiles.length;
                                    for (int i3 = 0; i3 < length2; i3++) {
                                        File file = listFiles[i3];
                                        if (file.isDirectory()) {
                                            String name = file.getName();
                                            List list = dualDarDoPolicyChecker.skippedPackages;
                                            if (list != null && !((ArrayList) list).isEmpty()) {
                                                z = ((ArrayList) dualDarDoPolicyChecker.skippedPackages).contains(name);
                                                if (!z && !enterprisePartitionManager.hasDualDARPolicyRecursively(file.getCanonicalPath(), arrayList)) {
                                                    Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on user directory : " + name);
                                                }
                                            }
                                            z = false;
                                            if (!z) {
                                                Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on user directory : " + name);
                                            }
                                        }
                                    }
                                    if (!enterprisePartitionManager.hasDualDARPolicyRecursively("/data/media/" + intExtra3 + "/Android/data", arrayList2)) {
                                        Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on media directory");
                                        break;
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                        break;
                }
            }
        };
        this.mCeStorageLockEventListener = new AnonymousClass4();
        this.mCallbacks = new HashMap();
        this.RemoteLockType = new String[]{"FMM", "CarrierLock", "RmmLock", "KnoxGuard"};
        this.mResetDebugLevel = new Runnable(this) { // from class: com.android.server.locksettings.LockSettingsService.9
            public final /* synthetic */ LockSettingsService this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i) {
                    case 0:
                        Slog.d("LockSettingsService", "!@ Reset SecurityDebugLevel");
                        int failureCount = LsLog.getFailureCount(0);
                        if (failureCount < LsConstants.SECURITY_DEBUG_ON_COUNT && failureCount >= 0) {
                            this.this$0.setSecurityDebugLevel(0);
                            break;
                        } else {
                            this.this$0.setSecurityDebugLevel(1);
                            break;
                        }
                    default:
                        Slog.d("LockSettingsService", "Start backup spblob dir");
                        SPBnRManager.setProtectorIdForBackup(0, this.this$0.getCurrentLskfBasedProtectorId(0), this.this$0.getBackupLskfBasedProtectorId(0));
                        if (!SPBnRManager.startBackup()) {
                            LsLog.restore("SPblobBNR, Failed backup spblob dir");
                            LsLog.restore(SPBnRManager.getPWFilelist(0));
                            LsLog.restore(SPBnRManager.getBackupPWFilelist(0), true);
                            break;
                        } else {
                            this.this$0.setLong("locksettings_spblob_backup", System.currentTimeMillis(), 0);
                            LsLog.restore("SPblobBNR, Success backup spblob dir");
                            LsLog.restore(SPBnRManager.getPWFilelist(0));
                            LsLog.restore(SPBnRManager.getBackupPWFilelist(0));
                            break;
                        }
                }
            }
        };
        final int i2 = 1;
        this.mSpBackup = new Runnable(this) { // from class: com.android.server.locksettings.LockSettingsService.9
            public final /* synthetic */ LockSettingsService this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (i2) {
                    case 0:
                        Slog.d("LockSettingsService", "!@ Reset SecurityDebugLevel");
                        int failureCount = LsLog.getFailureCount(0);
                        if (failureCount < LsConstants.SECURITY_DEBUG_ON_COUNT && failureCount >= 0) {
                            this.this$0.setSecurityDebugLevel(0);
                            break;
                        } else {
                            this.this$0.setSecurityDebugLevel(1);
                            break;
                        }
                    default:
                        Slog.d("LockSettingsService", "Start backup spblob dir");
                        SPBnRManager.setProtectorIdForBackup(0, this.this$0.getCurrentLskfBasedProtectorId(0), this.this$0.getBackupLskfBasedProtectorId(0));
                        if (!SPBnRManager.startBackup()) {
                            LsLog.restore("SPblobBNR, Failed backup spblob dir");
                            LsLog.restore(SPBnRManager.getPWFilelist(0));
                            LsLog.restore(SPBnRManager.getBackupPWFilelist(0), true);
                            break;
                        } else {
                            this.this$0.setLong("locksettings_spblob_backup", System.currentTimeMillis(), 0);
                            LsLog.restore("SPblobBNR, Success backup spblob dir");
                            LsLog.restore(SPBnRManager.getPWFilelist(0));
                            LsLog.restore(SPBnRManager.getBackupPWFilelist(0));
                            break;
                        }
                }
            }
        };
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.android.server.locksettings.LockSettingsService.3
            public final /* synthetic */ LockSettingsService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                boolean z;
                switch (i2) {
                    case 0:
                        if ("android.intent.action.USER_ADDED".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                            if (!LockSettingsService.FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                                AndroidKeyStoreMaintenance.onUserAdded(intExtra);
                            }
                            if (SemPersonaManager.isDarDualEncryptionEnabled(intExtra)) {
                                AnyMotionDetector$$ExternalSyntheticOutline0.m(intExtra, "OnUserAdded(): Setup SeparateCredential for DualDAR User : ", "LockSettingsService");
                                this.this$0.setSeparateProfileChallengeEnabled(intExtra, true, null);
                                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("OnUserAdded(): Successfully added OLK to vold for DualDAR User : "), intExtra, "LockSettingsService");
                                break;
                            }
                        } else if ("android.intent.action.USER_STARTING".equals(intent.getAction())) {
                            this.this$0.mStorage.prefetchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                            break;
                        } else if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction())) {
                            LockSettingsService lockSettingsService = this.this$0;
                            for (UserInfo userInfo : lockSettingsService.mUserManager.getUsers()) {
                                StatusBarNotification[] activeNotifications = ((NotificationManager) lockSettingsService.mContext.createContextAsUser(UserHandle.of(userInfo.id), 0).getSystemService("notification")).getActiveNotifications();
                                int length = activeNotifications.length;
                                int i22 = 0;
                                while (true) {
                                    if (i22 >= length) {
                                        break;
                                    } else if (activeNotifications[i22].getId() == 9) {
                                        lockSettingsService.maybeShowEncryptionNotificationForUser(userInfo.id, "locale changed");
                                    } else {
                                        i22++;
                                    }
                                }
                            }
                            break;
                        } else if (DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED.equals(intent.getAction())) {
                            int identifier = ((UserHandle) intent.getExtra("android.intent.extra.USER")).getIdentifier();
                            if (SemPersonaManager.isDarDualEncryptionEnabled(identifier)) {
                                AnyMotionDetector$$ExternalSyntheticOutline0.m(identifier, "reportEnabledTrustAgentsChanged for DualDAR User : ", "LockSettingsService");
                                ((TrustManager) this.this$0.mContext.getSystemService(TrustManager.class)).reportEnabledTrustAgentsChanged(identifier);
                                break;
                            }
                        }
                        break;
                    default:
                        if ("android.intent.action.DUMP_SYNTHETIC_PASSWORD".equals(intent.getAction())) {
                            Log.d("LockSettingsService.SDP", "onReceive :: android.intent.action.DUMP_SYNTHETIC_PASSWORD");
                            intent.getStringExtra("cred");
                            intent.getStringExtra(KnoxCustomManagerService.SPCM_KEY_TOKEN);
                            intent.getIntExtra("urid", -1);
                            this.this$0.mSpDump.getClass();
                            Log.d("SyntheticPasswordDump_SDP", "Dump not supported for this device");
                            break;
                        } else if ("android.intent.action.DUMP_DUALDAR_PASSWORD".equals(intent.getAction())) {
                            DDLog.d("LockSettingsService.DUALDAR", "onReceive :: android.intent.action.DUMP_DUALDAR_PASSWORD", new Object[0]);
                            int intExtra2 = intent.getIntExtra("urid", -1);
                            String stringExtra = intent.getStringExtra("path");
                            DualDARDaemonProxy dualDARDaemonProxy = DualDARDaemonProxy.getInstance(this.this$0.mContext);
                            if (dualDARDaemonProxy.mConnector == null) {
                                DDLog.e("DualDARDaemonProxy", "dumpSecret failed! Error: native interface not yet connected failed", new Object[0]);
                                break;
                            } else {
                                DDLog.d("DualDARDaemonProxy.DUALDAR", AccessibilityManagerService$$ExternalSyntheticOutline0.m(intExtra2, "dumpSecret() - userId : ", ", filePath : ", stringExtra), new Object[0]);
                                try {
                                    NativeDaemonEvent executeSync = dualDARDaemonProxy.mConnector.executeSync("key", "key_dump", Integer.valueOf(intExtra2), stringExtra);
                                    dualDARDaemonProxy.mEvent = executeSync;
                                    if (!executeSync.isClassOk()) {
                                        dualDARDaemonProxy.mEvent.getClass();
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }
                        } else if ("android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES".equals(intent.getAction())) {
                            DDLog.d("LockSettingsService.DUALDAR", "onReceive :: android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES", new Object[0]);
                            int intExtra3 = intent.getIntExtra("urid", -1);
                            DualDarDoPolicyChecker dualDarDoPolicyChecker = DualDarDoPolicyChecker.getInstance(this.this$0.mContext);
                            dualDarDoPolicyChecker.getClass();
                            if (!DualDarManager.isOnDeviceOwner(intExtra3)) {
                                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(intExtra3, "Not a DualDAR at DO user - ", "DualDarDoPolicyChecker");
                                break;
                            } else {
                                ArrayList arrayList = new ArrayList();
                                ArrayList arrayList2 = new ArrayList();
                                EnterprisePartitionManager enterprisePartitionManager = EnterprisePartitionManager.getInstance(DualDarDoPolicyChecker.sContext);
                                try {
                                    File[] listFiles = new File("/data/user/" + intExtra3).listFiles();
                                    int length2 = listFiles.length;
                                    for (int i3 = 0; i3 < length2; i3++) {
                                        File file = listFiles[i3];
                                        if (file.isDirectory()) {
                                            String name = file.getName();
                                            List list = dualDarDoPolicyChecker.skippedPackages;
                                            if (list != null && !((ArrayList) list).isEmpty()) {
                                                z = ((ArrayList) dualDarDoPolicyChecker.skippedPackages).contains(name);
                                                if (!z && !enterprisePartitionManager.hasDualDARPolicyRecursively(file.getCanonicalPath(), arrayList)) {
                                                    Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on user directory : " + name);
                                                }
                                            }
                                            z = false;
                                            if (!z) {
                                                Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on user directory : " + name);
                                            }
                                        }
                                    }
                                    if (!enterprisePartitionManager.hasDualDARPolicyRecursively("/data/media/" + intExtra3 + "/Android/data", arrayList2)) {
                                        Log.d("DualDarDoPolicyChecker", "ddar policy mismatch on media directory");
                                        break;
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                        break;
                }
            }
        };
        this.mPendingVerifiedResults = new SparseArray();
        this.mInjector = injector;
        Context context = injector.mContext;
        this.mContext = context;
        try {
            byte[] bArr = SyntheticPasswordCrypto.PROTECTOR_SECRET_PERSONALIZATION;
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(new AndroidKeyStoreLoadStoreParameter(103));
            this.mKeyStore = keyStore;
            this.mKeyStoreAuthorization = KeyStoreAuthorization.getInstance();
            Context context2 = injector.mContext;
            synchronized (RecoverableKeyStoreManager.class) {
                if (RecoverableKeyStoreManager.mInstance == null) {
                    RecoverableKeyStoreDbHelper recoverableKeyStoreDbHelper = new RecoverableKeyStoreDbHelper(context2, "recoverablekeystore.db", null, 7);
                    recoverableKeyStoreDbHelper.setWriteAheadLoggingEnabled(true);
                    recoverableKeyStoreDbHelper.setIdleConnectionTimeout(30L);
                    RecoverableKeyStoreDb recoverableKeyStoreDb = new RecoverableKeyStoreDb(recoverableKeyStoreDbHelper);
                    RemoteLockscreenValidationSessionStorage remoteLockscreenValidationSessionStorage = FeatureFlagUtils.isEnabled(context2, "settings_enable_lockscreen_transfer_api") ? new RemoteLockscreenValidationSessionStorage() : null;
                    try {
                        PlatformKeyManager platformKeyManager = PlatformKeyManager.getInstance(context2, recoverableKeyStoreDb);
                        ApplicationKeyStorage applicationKeyStorage = ApplicationKeyStorage.getInstance();
                        RecoverySnapshotStorage recoverySnapshotStorage = new RecoverySnapshotStorage(new File(Environment.getDataDirectory(), "system"));
                        CleanupManager cleanupManager = new CleanupManager(recoverySnapshotStorage, recoverableKeyStoreDb, UserManager.get(context2.getApplicationContext()), applicationKeyStorage);
                        Context applicationContext = context2.getApplicationContext();
                        RecoverySessionStorage recoverySessionStorage = new RecoverySessionStorage();
                        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1);
                        RecoverySnapshotListenersStorage recoverySnapshotListenersStorage = new RecoverySnapshotListenersStorage();
                        recoverySnapshotListenersStorage.mAgentIntents = new SparseArray();
                        recoverySnapshotListenersStorage.mAgentsWithPendingSnapshots = new ArraySet();
                        RecoverableKeyStoreManager.mInstance = new RecoverableKeyStoreManager(applicationContext, recoverableKeyStoreDb, recoverySessionStorage, newScheduledThreadPool, recoverySnapshotStorage, recoverySnapshotListenersStorage, platformKeyManager, applicationKeyStorage, new TestOnlyInsecureCertificateHelper(), cleanupManager, remoteLockscreenValidationSessionStorage);
                    } catch (KeyStoreException e) {
                        throw new ServiceSpecificException(22, e.getMessage());
                    } catch (NoSuchAlgorithmException e2) {
                        throw new RuntimeException(e2);
                    }
                }
                recoverableKeyStoreManager = RecoverableKeyStoreManager.mInstance;
            }
            this.mRecoverableKeyStoreManager = recoverableKeyStoreManager;
            if (injector.mHandlerThread == null) {
                ServiceThread serviceThread = new ServiceThread(10, "LockSettingsService", true);
                injector.mHandlerThread = serviceThread;
                serviceThread.start();
            }
            ServiceThread serviceThread2 = injector.mHandlerThread;
            if (injector.mHandler == null) {
                injector.mHandler = new Handler(serviceThread2.getLooper());
            }
            this.mHandler = injector.mHandler;
            LockSettingsStrongAuth lockSettingsStrongAuth = new LockSettingsStrongAuth(injector.mContext, new LockSettingsStrongAuth.Injector());
            this.mStrongAuth = lockSettingsStrongAuth;
            this.mActivityManager = ActivityManager.getService();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_ADDED");
            intentFilter.addAction("android.intent.action.USER_STARTING");
            intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
            intentFilter.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
            injector.mContext.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
            LockSettingsStorage lockSettingsStorage = new LockSettingsStorage(injector.mContext);
            Injector.AnonymousClass1 anonymousClass1 = new Injector.AnonymousClass1(lockSettingsStorage);
            lockSettingsStorage.mCallback = anonymousClass1;
            lockSettingsStorage.mOpenHelper.mCallback = anonymousClass1;
            this.mStorage = lockSettingsStorage;
            this.mNotificationManager = (NotificationManager) injector.mContext.getSystemService("notification");
            this.mUserManager = (UserManager) injector.mContext.getSystemService("user");
            IBinder service = ServiceManager.getService("mount");
            this.mStorageManager = service != null ? IStorageManager.Stub.asInterface(service) : null;
            this.mStorageManagerInternal = (StorageManagerInternal) LocalServices.getService(StorageManagerInternal.class);
            SynchronizedStrongAuthTracker synchronizedStrongAuthTracker = new SynchronizedStrongAuthTracker(injector.mContext);
            this.mStrongAuthTracker = synchronizedStrongAuthTracker;
            lockSettingsStrongAuth.mHandler.obtainMessage(2, synchronizedStrongAuthTracker.getStub()).sendToTarget();
            this.mGatekeeperPasswords = new LongSparseArray();
            Context context3 = injector.mContext;
            SyntheticPasswordManager syntheticPasswordManager = new SyntheticPasswordManager(context3, lockSettingsStorage, (UserManager) context3.getSystemService("user"), new PasswordSlotManager());
            this.mSpManager = syntheticPasswordManager;
            this.mUnifiedProfilePasswordCache = new UnifiedProfilePasswordCache(keyStore);
            this.mBiometricDeferredQueue = new BiometricDeferredQueue(syntheticPasswordManager);
            Injector.AnonymousClass1 anonymousClass12 = new Injector.AnonymousClass1(this);
            Context context4 = injector.mContext;
            if (injector.mHandlerThread == null) {
                ServiceThread serviceThread3 = new ServiceThread(10, "LockSettingsService", true);
                injector.mHandlerThread = serviceThread3;
                serviceThread3.start();
            }
            ServiceThread serviceThread4 = injector.mHandlerThread;
            if (injector.mHandler == null) {
                injector.mHandler = new Handler(serviceThread4.getLooper());
            }
            this.mRebootEscrowManager = new RebootEscrowManager(new RebootEscrowManager.Injector(context4, lockSettingsStorage, Injector.getUserManagerInternal()), anonymousClass12, lockSettingsStorage, injector.mHandler);
            if (DEBUG_DUMP) {
                injector.mContext.registerReceiver(broadcastReceiver2, GmsAlarmManager$$ExternalSyntheticOutline0.m("android.intent.action.DUMP_SYNTHETIC_PASSWORD", "android.intent.action.DUMP_DUALDAR_PASSWORD", "android.intent.action.CHECK_DUALDAR_POLICY_PACKAGES"));
            }
            this.mSpDump = new SyntheticPasswordDump();
            this.mDarLockSettings = new DarLockSettings(this);
            Injector.AnonymousClass1 anonymousClass13 = new Injector.AnonymousClass1();
            anonymousClass13.val$storage = this;
            this.mSdpLockSettings = anonymousClass13;
            Injector.AnonymousClass1 anonymousClass14 = new Injector.AnonymousClass1();
            anonymousClass14.val$storage = this;
            this.mDualDarLockSettings = anonymousClass14;
            this.mDarVirtualLock = new Injector.AnonymousClass1(this);
            this.mDualDarAuthUtils = new DualDarAuthUtils(context);
            LocalServices.addService(LockSettingsInternal.class, new LocalService());
        } catch (Exception e3) {
            throw new IllegalStateException("Cannot load keystore", e3);
        }
    }

    public static boolean isEnablePrevCredential() {
        String str = SystemProperties.get("ro.organization_owned", (String) null);
        return str == null || !str.equals("true");
    }

    public static void scheduleGc() {
        BackgroundThread.getHandler().postDelayed(new LockSettingsService$$ExternalSyntheticLambda2(), 2000L);
    }

    public static String timestampToString(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(j));
    }

    public final void UnlockSecureFolderIfAutoDataDecryption(UserInfo userInfo) {
        if (userInfo.isSecureFolder()) {
            int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "automatic_data_decryption", 0, userInfo.id);
            if (!this.mUserManager.isUserRunning(userInfo.id)) {
                SDPLog.d(null, "Secure folder user " + userInfo + " is not running yet when on unlock system user");
                return;
            }
            if (this.mUserManager.isUserUnlockingOrUnlocked(userInfo.id)) {
                return;
            }
            if (!isUserSecure(userInfo.id) || intForUser == 1) {
                SDPLog.d(null, "Unlock secure folder user " + userInfo.id);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        this.mInjector.getClass();
                        if (((DarManagerService) ServiceManager.getService("dar")) != null) {
                            this.mInjector.getClass();
                            DarManagerService darManagerService = (DarManagerService) ServiceManager.getService("dar");
                            int i = userInfo.id;
                            darManagerService.checkSystemPermission();
                            darManagerService.mDarHandler.sendMessage(darManagerService.mDarHandler.obtainMessage(150, i, 0));
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

    public final long addEscrowToken(byte[] bArr, int i, int i2, LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        long addPendingToken;
        long addPendingToken2;
        SDPLog.i("add escrow token for user " + i2);
        SDPLog.p(KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "userId", Integer.valueOf(i2));
        SyntheticPasswordManager.SyntheticPassword syntheticPassword = null;
        if (UserManager.isVirtualUserId(i2)) {
            SDPLog.i("add escrow token for virtual user " + i2);
            Injector.AnonymousClass1 anonymousClass1 = this.mDarVirtualLock;
            synchronized (((LockSettingsService) anonymousClass1.val$storage).mSpManager) {
                try {
                    if (!((LockSettingsService) anonymousClass1.val$storage).isUserSecure(i2)) {
                        if (!((LockSettingsService) anonymousClass1.val$storage).isSyntheticPasswordBasedCredentialLocked(i2)) {
                            Injector.AnonymousClass1.prepare(i2);
                            syntheticPassword = anonymousClass1.initializeSyntheticPasswordForVirtualUser(i2);
                        } else {
                            long currentLskfBasedProtectorId = ((LockSettingsService) anonymousClass1.val$storage).getCurrentLskfBasedProtectorId(i2);
                            LockSettingsService lockSettingsService = (LockSettingsService) anonymousClass1.val$storage;
                            syntheticPassword = lockSettingsService.mSpManager.unlockLskfBasedProtector(lockSettingsService.getGateKeeperService(), currentLskfBasedProtectorId, LockscreenCredential.createNone(), i2, null).syntheticPassword;
                        }
                    }
                    addPendingToken2 = ((LockSettingsService) anonymousClass1.val$storage).mSpManager.addPendingToken(bArr, i, i2, escrowTokenStateChangeCallback);
                    if (syntheticPassword != null) {
                        if (((LockSettingsService) anonymousClass1.val$storage).mDualDarAuthUtils.isInnerAuthUserForDo(i2)) {
                            byte[] pendingTokenForDualDAR = ((LockSettingsService) anonymousClass1.val$storage).mDualDarLockSettings.getPendingTokenForDualDAR(i2, addPendingToken2);
                            if (((LockSettingsService) anonymousClass1.val$storage).mSpManager.createTokenBasedProtector(i2, addPendingToken2, syntheticPassword)) {
                                ((LockSettingsService) anonymousClass1.val$storage).mDualDarLockSettings.activateEscrowTokenForDualDAR(addPendingToken2, i2, pendingTokenForDualDAR, null);
                            }
                        } else {
                            ((LockSettingsService) anonymousClass1.val$storage).mSpManager.createTokenBasedProtector(i2, addPendingToken2, syntheticPassword);
                        }
                    }
                } finally {
                }
            }
            return addPendingToken2;
        }
        Slogf.i("LockSettingsService", "Adding escrow token for user %d", Integer.valueOf(i2));
        synchronized (this.mSpManager) {
            try {
                if (!isUserSecure(i2)) {
                    syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i2), LockscreenCredential.createNone(), i2, null).syntheticPassword;
                    SDPLog.i("addEscrowToken: saveEscrowDataIfNeededLocked");
                    this.mDarLockSettings.saveEscrowDataIfNeededLocked(i2);
                }
                disableEscrowTokenOnNonManagedDevicesIfNeeded(i2);
                SDPLog.i("addEscrowToken: restoreEscrowDataIfNeededLocked");
                this.mDarLockSettings.restoreEscrowDataIfNeededLocked(i2);
                if (!this.mSpManager.hasEscrowData(i2)) {
                    throw new SecurityException("Escrow token is disabled on the current user");
                }
                addPendingToken = this.mSpManager.addPendingToken(bArr, i, i2, escrowTokenStateChangeCallback);
                if (syntheticPassword == null) {
                    Slogf.i("LockSettingsService", "Escrow token %016x will be activated when user is unlocked", Long.valueOf(addPendingToken));
                } else if (isDualDarAuthUserId(i2)) {
                    byte[] pendingTokenForDualDAR2 = this.mDualDarLockSettings.getPendingTokenForDualDAR(i2, addPendingToken);
                    if (this.mSpManager.createTokenBasedProtector(i2, addPendingToken, syntheticPassword)) {
                        this.mDualDarLockSettings.activateEscrowTokenForDualDAR(addPendingToken, i2, pendingTokenForDualDAR2, null);
                    }
                } else {
                    Slogf.i("LockSettingsService", "Immediately activating escrow token %016x", Long.valueOf(addPendingToken));
                    this.mSpManager.createTokenBasedProtector(i2, addPendingToken, syntheticPassword);
                }
            } finally {
            }
        }
        return addPendingToken;
    }

    public final long addWeakEscrowToken(byte[] bArr, int i, final IWeakEscrowTokenActivatedListener iWeakEscrowTokenActivatedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        Objects.requireNonNull(iWeakEscrowTokenActivatedListener, "Listener can not be null.");
        LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback = new LockPatternUtils.EscrowTokenStateChangeCallback() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda1
            public final void onEscrowTokenActivated(long j, int i2) {
                try {
                    iWeakEscrowTokenActivatedListener.onWeakEscrowTokenActivated(j, i2);
                } catch (RemoteException e) {
                    Slog.e("LockSettingsService", "Exception while notifying weak escrow token has been activated", e);
                }
            }
        };
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return addEscrowToken(bArr, 1, i, escrowTokenStateChangeCallback);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
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
            setString(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "message"), str2, i);
            setString(i2 + "phonenumber", str3, i);
            setBoolean(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "enableemergencycall"), z4, i);
            setString(i2 + "clientname", str4, i);
            setString(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "emailaddress"), str5, i);
            setLong(i2 + "allowfailcount", j, i);
            setLong(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "locktime"), j2, i);
            setLong(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "permanentblockcount"), j3, i);
            setBoolean(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "skippin"), z5, i);
            setBoolean(i2 + "skipsupport", z6, i);
            remoteLockInfo2 = remoteLockInfo;
            if (remoteLockInfo2.bundle != null) {
                setString(i2 + "appname", str7, i);
                setString(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "packagename"), r19, i);
            }
            z = false;
        } else {
            remoteLockInfo2 = remoteLockInfo;
            z = false;
            setBoolean(i2 + "locked", false, i);
            setLong(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "remotelockscreen.lockoutdeadline"), 0L, i);
            setLong(AmFmBandRange$$ExternalSyntheticOutline0.m(i2, new StringBuilder(), "remotelockscreen.failedunlockcount"), 0L, i);
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

    public final void callToAuthSecretIfNeeded(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        byte[] decrypt;
        byte[] bArr;
        byte[] bArr2;
        if (this.mAuthSecretService == null) {
            return;
        }
        this.mInjector.getClass();
        UserInfo userInfo = Injector.getUserManagerInternal().getUserInfo(i);
        if (userInfo == null) {
            return;
        }
        this.mInjector.getClass();
        if (UserManager.isHeadlessSystemUserMode()) {
            this.mInjector.getClass();
            if (!Resources.getSystem().getBoolean(R.bool.config_letterboxIsDisplayAspectRatioForFixedOrientationLetterboxEnabled) || !userInfo.isFull()) {
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
                        FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "Creating non-main user ", " but vendor auth secret is not in memory", "LockSettingsService");
                        return;
                    }
                    bArr2 = bArr;
                }
                SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
                syntheticPasswordManager.getClass();
                syntheticPassword.getClass();
                syntheticPasswordManager.saveState("vendor_auth_secret", SyntheticPasswordCrypto.encrypt(syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_AUTHSECRET_ENCRYPTION_KEY), new byte[0], bArr2), 0L, i);
                syntheticPasswordManager.syncState(i);
                decrypt = bArr2;
            } else {
                byte[] readSyntheticPasswordState = this.mSpManager.mStorage.readSyntheticPasswordState(i, "vendor_auth_secret", 0L);
                if (readSyntheticPasswordState == null) {
                    decrypt = null;
                } else {
                    syntheticPassword.getClass();
                    decrypt = SyntheticPasswordCrypto.decrypt(syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_AUTHSECRET_ENCRYPTION_KEY), new byte[0], readSyntheticPasswordState);
                }
                if (decrypt == null) {
                    NandswapManager$$ExternalSyntheticOutline0.m(i, "Unable to read vendor auth secret for user: ", "LockSettingsService");
                    return;
                } else {
                    synchronized (this.mHeadlessAuthSecretLock) {
                        this.mAuthSecret = decrypt;
                    }
                }
            }
        } else {
            if (i != 0) {
                return;
            }
            syntheticPassword.getClass();
            decrypt = syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_AUTHSECRET_KEY);
        }
        HermesService$3$$ExternalSyntheticOutline0.m(i, "Sending vendor auth secret to IAuthSecret HAL as user: ", "LockSettingsService");
        try {
            this.mAuthSecretService.setPrimaryUserCredential(decrypt);
        } catch (RemoteException e) {
            Slog.w("LockSettingsService", "Failed to send vendor auth secret to IAuthSecret HAL", e);
        }
    }

    public final boolean changeToken(byte[] bArr, long j, byte[] bArr2, long j2, int i) {
        SDPLog.i("Change token for user " + i);
        SDPLog.p("newToken", bArr, "newHandle", Long.valueOf(j), ", oldToken : " + bArr2 + ", oldHandle : " + j2);
        boolean z = false;
        try {
            synchronized (this.mSpManager) {
                try {
                    if (!this.mSpManager.hasEscrowData(i)) {
                        throw new SecurityException("Excrow token is disabled for current user");
                    }
                    SyntheticPasswordManager.SyntheticPassword syntheticPassword = this.mSpManager.unlockTokenBasedProtector(i, j2, getGateKeeperService(), bArr2).syntheticPassword;
                    if (syntheticPassword == null) {
                        SDPLog.d(null, "Failed due to invalid token");
                    } else {
                        z = this.mSpManager.createTokenBasedProtector(i, j, syntheticPassword);
                        if (!z) {
                            SDPLog.d(null, "Failed in new token activation");
                        }
                        if (!z || !removeEscrowToken(j2, i)) {
                            SDPLog.d(null, "Failed in old token elimination");
                        }
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            SDPLog.e(e, null, "Unexpected exception while change token");
        }
        SDPLog.d(null, "Final result of change token : " + z);
        return z;
    }

    public final boolean checkAppLockBackupPin(String str, int i) {
        checkPasswordReadPermission();
        Charset charset = StandardCharsets.UTF_8;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(charset));
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        byte[] readFile = lockSettingsStorage.readFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockbackuppin.key"));
        if (readFile == null || readFile.length <= 0) {
            readFile = null;
        }
        if (readFile == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readFile);
        return (equals || readFile.length != 40) ? equals : Arrays.equals(passwordToHash(i, str.getBytes(charset)), readFile);
    }

    public final boolean checkAppLockFingerprintPassword(String str, int i) {
        checkPasswordReadPermission();
        Charset charset = StandardCharsets.UTF_8;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(charset));
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        byte[] readFile = lockSettingsStorage.readFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockfingerprintpassword.key"));
        if (readFile == null || readFile.length <= 0) {
            readFile = null;
        }
        if (readFile == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readFile);
        return (equals || readFile.length != 40) ? equals : Arrays.equals(passwordToHash(i, str.getBytes(charset)), readFile);
    }

    public final boolean checkAppLockPassword(String str, int i) {
        checkPasswordReadPermission();
        Charset charset = StandardCharsets.UTF_8;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(charset));
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        byte[] readFile = lockSettingsStorage.readFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpassword.key"));
        if (readFile == null || readFile.length <= 0) {
            readFile = null;
        }
        if (readFile == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readFile);
        return (equals || readFile.length != 40) ? equals : Arrays.equals(passwordToHash(i, str.getBytes(charset)), readFile);
    }

    public final boolean checkAppLockPatternWithHash(String str, int i, byte[] bArr) {
        checkPasswordReadPermission();
        byte[] patternToByteArray = LockPatternUtils.patternToByteArray(LockPatternUtils.byteArrayToPattern(str.getBytes(StandardCharsets.UTF_8)));
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        byte[] readFile = lockSettingsStorage.readFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpattern.key"));
        if (readFile == null || readFile.length <= 0) {
            readFile = null;
        }
        return readFile == null || Arrays.equals(patternToByteArray, readFile) || Arrays.equals(bArr, readFile);
    }

    public final boolean checkAppLockPin(String str, int i) {
        checkPasswordReadPermission();
        Charset charset = StandardCharsets.UTF_8;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(charset));
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        byte[] readFile = lockSettingsStorage.readFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpin.key"));
        if (readFile == null || readFile.length <= 0) {
            readFile = null;
        }
        if (readFile == null) {
            return true;
        }
        boolean equals = Arrays.equals(passwordToHash, readFile);
        return (equals || readFile.length != 40) ? equals : Arrays.equals(passwordToHash(i, str.getBytes(charset)), readFile);
    }

    public final boolean checkCarrierPassword(byte[] bArr, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(i, bArr);
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        byte[] readFile = lockSettingsStorage.readFile(LockSettingsStorage.getLockCredentialFileForUser(i, "sktpassword.key"));
        if (readFile == null || readFile.length <= 0) {
            readFile = null;
        }
        if (readFile == null) {
            return true;
        }
        return Arrays.equals(passwordToHash, readFile);
    }

    public final VerifyCredentialResponse checkCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        VerifyCredentialResponse doVerifyCredential;
        checkPasswordReadPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isEnterpriseUserId(i)) {
                try {
                    lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
                    doVerifyCredential = doVerifyCredential(lockscreenCredential, i, iCheckCredentialProgressCallback, 0);
                    lockscreenCredential.zeroize();
                } catch (Throwable th) {
                    if (lockscreenCredential != null) {
                        lockscreenCredential.zeroize();
                    }
                    throw th;
                }
            } else {
                doVerifyCredential = doVerifyCredential(lockscreenCredential, i, iCheckCredentialProgressCallback, 0);
            }
            return doVerifyCredential;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    public final VerifyCredentialResponse checkCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) {
        checkPasswordReadPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return doVerifyCredentialForDualDarDo(lockscreenCredential, i, i2, iDualDarAuthProgressCallback);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    public final void checkDatabaseReadPermission(int i, String str) {
        if (hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE")) {
            return;
        }
        throw new SecurityException("uid=" + ILockSettings.Stub.getCallingUid() + " needs permission android.permission.ACCESS_KEYGUARD_SECURE_STORAGE to read " + str + " for user " + i);
    }

    public final boolean checkFMMPassword(byte[] bArr, int i) {
        checkPasswordReadPermission();
        byte[] passwordToHash = passwordToHash(i, bArr);
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        byte[] readFile = lockSettingsStorage.readFile(LockSettingsStorage.getLockCredentialFileForUser(i, "fmmpassword.key"));
        if (readFile == null || readFile.length <= 0) {
            readFile = null;
        }
        if (readFile == null) {
            return true;
        }
        return Arrays.equals(passwordToHash, readFile);
    }

    public final void checkManageWeakEscrowTokenMethodUsage() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_WEAK_ESCROW_TOKEN", "Requires MANAGE_WEAK_ESCROW_TOKEN permission.");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new IllegalArgumentException("Weak escrow token are only for automotive devices.");
        }
    }

    public final void checkPasswordReadPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsRead");
    }

    public final void checkRemoteLockPassword(int i, byte[] bArr, int i2, IRemoteCallback iRemoteCallback) {
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
        Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(checkRemoteLockPassword, KnoxCustomManagerService.SPCM_KEY_RESULT);
        if (checkRemoteLockPassword > 0) {
            m.putLong("timeout", getLong(NandswapManager$$ExternalSyntheticOutline0.m(i, "locktime"), 0L, i2));
        }
        try {
            iRemoteCallback.sendResult(m);
        } catch (RemoteException e2) {
            Log.d("LockSettingsService", "failed sendResult callback!");
            e2.printStackTrace();
        }
    }

    public final void checkWritePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsWrite");
    }

    public final void closeSession(final String str) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        Objects.requireNonNull(str, "invalid session");
        int callingUid = Binder.getCallingUid();
        RecoverySessionStorage recoverySessionStorage = recoverableKeyStoreManager.mRecoverySessionStorage;
        if (recoverySessionStorage.mSessionsByUid.get(callingUid) == null) {
            return;
        }
        ((ArrayList) recoverySessionStorage.mSessionsByUid.get(callingUid)).removeIf(new Predicate() { // from class: com.android.server.locksettings.recoverablekeystore.storage.RecoverySessionStorage$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((RecoverySessionStorage.Entry) obj).mSessionId.equals(str);
            }
        });
    }

    public void deleteRepairModePersistentDataIfNeeded() {
        if (!LockPatternUtils.isRepairModeSupported(this.mContext) || LockPatternUtils.isRepairModeActive(this.mContext)) {
            return;
        }
        this.mInjector.getClass();
        if (LockPatternUtils.isGsiRunning()) {
            return;
        }
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.deleteFile(lockSettingsStorage.getRepairModePersistentDataFile());
    }

    public final void disableEscrowTokenOnNonManagedDevicesIfNeeded(int i) {
        if (UserManager.isVirtualUserId(i)) {
            Slog.i("LockSettingsService.SDP", "Virtual user can have escrow token");
            return;
        }
        SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
        if (syntheticPasswordManager.hasState(i, "e0", 0L) || syntheticPasswordManager.hasState(i, "p1", 0L)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    this.mInjector.getClass();
                    if (DeviceStateCache.getInstance().isUserOrganizationManaged(i)) {
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
                } else {
                    this.mInjector.getClass();
                    UserManagerInternal userManagerInternal = Injector.getUserManagerInternal();
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
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (isKnoxAdminActivated(i)) {
                    Slog.i("LockSettingsService", "User with knox admin can have escrow token");
                    return;
                }
                this.mInjector.getClass();
                if (!DeviceStateCache.getInstance().isDeviceProvisioned()) {
                    Slog.i("LockSettingsService", "Postpone disabling escrow tokens until device is provisioned");
                } else {
                    if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                        return;
                    }
                    Slogf.i("LockSettingsService", "Permanently disabling support for escrow tokens on user %d", Integer.valueOf(i));
                    SyntheticPasswordManager syntheticPasswordManager2 = this.mSpManager;
                    syntheticPasswordManager2.destroyState(i, "e0", 0L);
                    syntheticPasswordManager2.destroyState(i, "p1", 0L);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final VerifyCredentialResponse doVerifyCredential(LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback, int i2) {
        long j;
        int i3;
        final long j2;
        SyntheticPasswordManager.AuthenticationResult authenticationResult;
        if (lockscreenCredential == null || lockscreenCredential.isNone()) {
            throw new IllegalArgumentException("Credential can't be null or empty");
        }
        SDPLog.i("Verify credential for user " + i);
        SDPLog.p("credential", lockscreenCredential.getCredential(), "credentialType", Integer.valueOf(lockscreenCredential.getType()), "userId", Integer.valueOf(i));
        if (UserManager.isVirtualUserId(i)) {
            Injector.AnonymousClass1 anonymousClass1 = this.mDarVirtualLock;
            anonymousClass1.getClass();
            if (lockscreenCredential.isNone()) {
                throw new IllegalArgumentException("Credential can't be null or empty");
            }
            VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.OK;
            LockSettingsService lockSettingsService = (LockSettingsService) anonymousClass1.val$storage;
            if (lockSettingsService.isSyntheticPasswordBasedCredentialLocked(i)) {
                SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = lockSettingsService.mSpManager.unlockLskfBasedProtector(lockSettingsService.getGateKeeperService(), lockSettingsService.getCurrentLskfBasedProtectorId(i), lockscreenCredential, i, iCheckCredentialProgressCallback);
                authenticationResult = unlockLskfBasedProtector;
                verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
            } else {
                SDPLog.d(null, "Sp not yet applied to user " + i);
                authenticationResult = null;
            }
            if (verifyCredentialResponse == null) {
                verifyCredentialResponse = VerifyCredentialResponse.ERROR;
            }
            SDPLog.d(null, String.format("Result of verification for user %d : %s", Integer.valueOf(i), verifyCredentialResponse));
            if (!verifyCredentialResponse.isMatched()) {
                anonymousClass1.onCredentialMismatchedForInner(i);
            } else if (!anonymousClass1.onCredentialVerifiedForInner(lockscreenCredential, i)) {
                verifyCredentialResponse = VerifyCredentialResponse.ERROR;
            }
            if (verifyCredentialResponse.isMatched() && authenticationResult != null && lockSettingsService.isDualDarAuthUserId(i)) {
                lockSettingsService.mDualDarLockSettings.activateEscrowTokensForDualDAR(authenticationResult.syntheticPassword, i, lockscreenCredential.getCredential());
            }
            return verifyCredentialResponse;
        }
        if (i == -9999 && Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
            Slog.e("LockSettingsService", "FRP credential can only be verified prior to provisioning.");
            return VerifyCredentialResponse.ERROR;
        }
        if (i == -9998 && !LockPatternUtils.isRepairModeActive(this.mContext)) {
            Slog.e("LockSettingsService", "Repair mode is not active on the device.");
            return VerifyCredentialResponse.ERROR;
        }
        Slogf.i("LockSettingsService", "Verifying lockscreen credential for user %d", Integer.valueOf(i));
        try {
            VerifyCredentialResponse doVerifyCredentialForDualDAR = this.mDualDarLockSettings.doVerifyCredentialForDualDAR(lockscreenCredential, i);
            if (doVerifyCredentialForDualDAR.getResponseCode() != 0) {
                Slog.e("LockSettingsService", "verifyChallenge for DualDAR failed.");
                return doVerifyCredentialForDualDAR;
            }
        } catch (RemoteException e) {
            Slog.e("LockSettingsService", "RemoteException : " + e.getMessage());
        }
        synchronized (this.mSpManager) {
            try {
                if (LockPatternUtils.isSpecialUserId(i)) {
                    VerifyCredentialResponse verifySpecialUserCredential = this.mSpManager.verifySpecialUserCredential(i, getGateKeeperService(), lockscreenCredential);
                    if (Flags.frpEnforcement() && verifySpecialUserCredential.isMatched() && i == -9999) {
                        PersistentDataBlockManagerInternal persistentDataBlockManager = this.mStorage.getPersistentDataBlockManager();
                        if (persistentDataBlockManager != null) {
                            PersistentDataBlockService.InternalService internalService = (PersistentDataBlockService.InternalService) persistentDataBlockManager;
                            synchronized (PersistentDataBlockService.this.mLock) {
                                PersistentDataBlockService persistentDataBlockService = PersistentDataBlockService.this;
                                persistentDataBlockService.mFrpActive = false;
                                persistentDataBlockService.setOldSettingForBackworkCompatibility(false);
                            }
                        } else {
                            Slog.wtf("LockSettingsStorage", "Failed to get PersistentDataBlockManagerInternal");
                        }
                    }
                    return verifySpecialUserCredential;
                }
                long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
                if (isEnablePrevCredential() && i == -9899) {
                    Slog.e("LockSettingsService", "!@ try unlock with prevCredential!!!");
                    j = getBackupLskfBasedProtectorId(0);
                    i3 = 0;
                } else {
                    j = currentLskfBasedProtectorId;
                    i3 = i;
                }
                SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector2 = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), j, lockscreenCredential, i3, iCheckCredentialProgressCallback);
                VerifyCredentialResponse verifyCredentialResponse2 = unlockLskfBasedProtector2.gkResponse;
                if (verifyCredentialResponse2.getResponseCode() == 0) {
                    if ((i2 & 2) != 0 && !this.mSpManager.writeRepairModeCredentialLocked(i3, j)) {
                        Slog.e("LockSettingsService", "Failed to write repair mode credential");
                        LsLog.verify("Verify success. But failed to write repair mode credential");
                        return VerifyCredentialResponse.ERROR;
                    }
                    BiometricDeferredQueue biometricDeferredQueue = this.mBiometricDeferredQueue;
                    SyntheticPasswordManager.SyntheticPassword syntheticPassword = unlockLskfBasedProtector2.syntheticPassword;
                    syntheticPassword.getClass();
                    byte[] deriveSubkey = syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_SP_GK_AUTH);
                    biometricDeferredQueue.getClass();
                    biometricDeferredQueue.mHandler.post(new BiometricDeferredQueue$$ExternalSyntheticLambda0(biometricDeferredQueue, i3, deriveSubkey));
                }
                if (verifyCredentialResponse2.getResponseCode() == 0) {
                    Slogf.i("LockSettingsService", "Successfully verified lockscreen credential for user %d", Integer.valueOf(i3));
                    LsLog.setFailureCount(i3, 0);
                    onCredentialVerified(unlockLskfBasedProtector2.syntheticPassword, PasswordMetrics.computeForCredential(lockscreenCredential), i3);
                    if (isDualDarAuthUserId(i3)) {
                        this.mDualDarLockSettings.activateEscrowTokensForDualDAR(unlockLskfBasedProtector2.syntheticPassword, i3, lockscreenCredential.getCredential());
                    }
                    if ((i2 & 1) != 0) {
                        SyntheticPasswordManager.SyntheticPassword syntheticPassword2 = unlockLskfBasedProtector2.syntheticPassword;
                        syntheticPassword2.getClass();
                        byte[] deriveSubkey2 = syntheticPassword2.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_SP_GK_AUTH);
                        synchronized (this.mGatekeeperPasswords) {
                            j2 = 0;
                            while (true) {
                                if (j2 != 0) {
                                    if (this.mGatekeeperPasswords.get(j2) == null) {
                                        break;
                                    }
                                }
                                j2 = SecureRandomUtils.RNG.nextLong();
                            }
                            this.mGatekeeperPasswords.put(j2, deriveSubkey2);
                        }
                        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                LockSettingsService lockSettingsService2 = LockSettingsService.this;
                                long j3 = j2;
                                synchronized (lockSettingsService2.mGatekeeperPasswords) {
                                    try {
                                        if (lockSettingsService2.mGatekeeperPasswords.get(j3) != null) {
                                            Slogf.d("LockSettingsService", "Cached Gatekeeper password with handle %016x has expired", Long.valueOf(j3));
                                            lockSettingsService2.mGatekeeperPasswords.remove(j3);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }, 600000L);
                        verifyCredentialResponse2 = new VerifyCredentialResponse.Builder().setGatekeeperPasswordHandle(j2).build();
                    }
                    sendCredentialsOnUnlockIfRequired(lockscreenCredential, i3);
                    if (DualDarManager.isOnDeviceOwner(i3)) {
                        DualDarManager.getInstance(this.mContext).cancelDataLock(i3);
                    }
                    boolean z = SystemProperties.getBoolean("persist.escrowvault.sa.signed", false);
                    Slog.i("LockSettingsService", "isSaSignedIn : " + z);
                    if (z) {
                        updateVerifier(lockscreenCredential, i3);
                    }
                } else if (verifyCredentialResponse2.getResponseCode() == 1 && verifyCredentialResponse2.getTimeout() > 0) {
                    requireStrongAuth(8, i3);
                }
                if (Flags.reportPrimaryAuthAttempts()) {
                    boolean z2 = verifyCredentialResponse2.getResponseCode() == 0;
                    Iterator it = this.mLockSettingsStateListeners.iterator();
                    while (it.hasNext()) {
                        LockSettingsStateListener lockSettingsStateListener = (LockSettingsStateListener) it.next();
                        if (z2) {
                            lockSettingsStateListener.onAuthenticationSucceeded(i3);
                        } else {
                            lockSettingsStateListener.onAuthenticationFailed(i3);
                        }
                    }
                }
                return verifyCredentialResponse2;
            } finally {
            }
        }
    }

    public final VerifyCredentialResponse doVerifyCredentialForDualDarDo(LockscreenCredential lockscreenCredential, int i, int i2, IDualDarAuthProgressCallback iDualDarAuthProgressCallback) {
        SDPLog.i("Verify credential for dual-dar user " + i);
        SDPLog.p("credential", lockscreenCredential != null ? lockscreenCredential.getCredential() : "null", "credentialType", Integer.valueOf(lockscreenCredential != null ? lockscreenCredential.getType() : -1), "userId", Integer.valueOf(i), "opiotn", Integer.valueOf(i2));
        if (lockscreenCredential == null || lockscreenCredential.isNone()) {
            throw new IllegalArgumentException("Credential can't be null or empty");
        }
        Slogf.i("LockSettingsService", "Verifying lockscreen credential for user %d", Integer.valueOf(i));
        synchronized (this.mSpManager) {
            try {
                SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), lockscreenCredential, i, null);
                VerifyCredentialResponse verifyCredentialResponse = unlockLskfBasedProtector.gkResponse;
                if (verifyCredentialResponse.getResponseCode() == 0) {
                    BiometricDeferredQueue biometricDeferredQueue = this.mBiometricDeferredQueue;
                    SyntheticPasswordManager.SyntheticPassword syntheticPassword = unlockLskfBasedProtector.syntheticPassword;
                    syntheticPassword.getClass();
                    byte[] deriveSubkey = syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_SP_GK_AUTH);
                    biometricDeferredQueue.getClass();
                    biometricDeferredQueue.mHandler.post(new BiometricDeferredQueue$$ExternalSyntheticLambda0(biometricDeferredQueue, i, deriveSubkey));
                    verifyCredentialResponse = this.mSpManager.verifyChallenge(getGateKeeperService(), unlockLskfBasedProtector.syntheticPassword, i);
                    if (verifyCredentialResponse != null) {
                        if (verifyCredentialResponse.getResponseCode() != 0) {
                        }
                    }
                    Slog.wtf("LockSettingsService", "verifyChallenge with SP failed.");
                    return VerifyCredentialResponse.ERROR;
                }
                if (verifyCredentialResponse.getResponseCode() == 0) {
                    Slogf.i("LockSettingsService", "Successfully verified lockscreen credential for user %d", Integer.valueOf(i));
                    if ((i2 & 1) != 0) {
                        SyntheticPasswordManager.SyntheticPassword syntheticPassword2 = unlockLskfBasedProtector.syntheticPassword;
                        PasswordMetrics computeForCredential = PasswordMetrics.computeForCredential(lockscreenCredential);
                        if (syntheticPassword2 != null) {
                            SDPLog.d(null, "Postpone credential verified event for user " + i);
                            synchronized (this.mPendingVerifiedResults) {
                                DualDARCallback dualDARCallback = new DualDARCallback();
                                dualDARCallback.mCallback = iDualDarAuthProgressCallback;
                                Optional.ofNullable(iDualDarAuthProgressCallback == null ? null : iDualDarAuthProgressCallback.asBinder()).ifPresent(new LockSettingsService$DualDARCallback$$ExternalSyntheticLambda0(dualDARCallback, 0));
                                this.mPendingVerifiedResults.put(i, new PendingVerifiedResult(syntheticPassword2, computeForCredential, dualDARCallback));
                            }
                            int innerAuthUserId = this.mDualDarAuthUtils.getInnerAuthUserId(i);
                            if (!isUserSecure(innerAuthUserId)) {
                                SDPLog.d(null, "Auth user " + innerAuthUserId + " has no credential");
                                this.mDarVirtualLock.onCredentialVerifiedForInner(LockscreenCredential.createNone(), innerAuthUserId);
                            }
                        }
                    } else {
                        onCredentialVerified(unlockLskfBasedProtector.syntheticPassword, PasswordMetrics.computeForCredential(lockscreenCredential), i);
                    }
                    sendCredentialsOnUnlockIfRequired(lockscreenCredential, i);
                    if (DualDarManager.isOnDeviceOwner(i)) {
                        DualDarManager.getInstance(this.mContext).cancelDataLock(i);
                    }
                } else if (verifyCredentialResponse.getResponseCode() == 1 && verifyCredentialResponse.getTimeout() > 0) {
                    requireStrongAuth(8, i);
                }
                return verifyCredentialResponse;
            } finally {
            }
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
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
        String str;
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("Current lock settings service state:");
        indentingPrintWriter.println();
        int i = 0;
        boolean z = true;
        indentingPrintWriter.println("DO Enabled: " + (getLong("knox.device_owner", 0L, 0) != 0));
        indentingPrintWriter.println();
        indentingPrintWriter.println("User State:");
        indentingPrintWriter.increaseIndent();
        List users = this.mUserManager.getUsers();
        for (int i2 = 0; i2 < users.size(); i2++) {
            int i3 = ((UserInfo) users.get(i2)).id;
            indentingPrintWriter.println("User " + i3);
            indentingPrintWriter.increaseIndent();
            synchronized (this.mSpManager) {
                try {
                    indentingPrintWriter.println(TextUtils.formatSimple("LSKF-based SP protector ID: %016x", new Object[]{Long.valueOf(getCurrentLskfBasedProtectorId(i3))}));
                    indentingPrintWriter.println(TextUtils.formatSimple("LSKF last changed: %s (previous protector: %016x)", new Object[]{timestampToString(getLong("sp-handle-ts", 0L, i3)), Long.valueOf(getLong("prev-sp-handle", 0L, i3))}));
                    if (i3 == 0) {
                        indentingPrintWriter.println(TextUtils.formatSimple("Backup protector: %016x (set : %s, expire : %s)", new Object[]{Long.valueOf(getLong("backup-protector-id", 0L, i3)), timestampToString(getLong("backup-protector-ts", 0L, i3)), timestampToString(getLong("backup-expiration-ts", 0L, i3))}));
                    }
                } finally {
                }
            }
            indentingPrintWriter.println(String.format("Secure Mode: %d", Integer.valueOf(this.mDarLockSettings.getSecureMode(i3))));
            try {
                indentingPrintWriter.println(TextUtils.formatSimple("SID: %016x", new Object[]{Long.valueOf(getGateKeeperService().getSecureUserId(i3))}));
            } catch (RemoteException unused) {
            }
            StringBuilder sb = new StringBuilder("Quality: ");
            String string = this.mStorage.getString("lockscreen.password_type", null, i3);
            sb.append((int) (TextUtils.isEmpty(string) ? 0L : Long.parseLong(string)));
            indentingPrintWriter.println(sb.toString());
            indentingPrintWriter.println("CredentialType: " + LockPatternUtils.credentialTypeToString(getCredentialTypeInternal(i3)));
            indentingPrintWriter.println("SeparateChallenge: " + getSeparateProfileChallengeEnabledInternal(i3));
            indentingPrintWriter.println(TextUtils.formatSimple("Metrics: %s", new Object[]{getUserPasswordMetrics(i3) != null ? "known" : "unknown"}));
            indentingPrintWriter.println("failed attempt = " + this.mInjector.getDevicePolicyManager().getCurrentFailedPasswordAttempts(i3));
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Keys in namespace:");
        indentingPrintWriter.increaseIndent();
        try {
            Enumeration<String> aliases = this.mKeyStore.aliases();
            while (aliases.hasMoreElements()) {
                indentingPrintWriter.println(aliases.nextElement());
            }
        } catch (KeyStoreException e) {
            indentingPrintWriter.println("Unable to get keys: " + e.toString());
            Slog.d("LockSettingsService", "Dump error", e);
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Storage:");
        indentingPrintWriter.increaseIndent();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        for (UserInfo userInfo : UserManager.get(lockSettingsStorage.mContext).getUsers()) {
            File syntheticPasswordDirectoryForUser = lockSettingsStorage.getSyntheticPasswordDirectoryForUser(userInfo.id);
            indentingPrintWriter.println(TextUtils.formatSimple("User %d [%s]:", new Object[]{Integer.valueOf(userInfo.id), syntheticPasswordDirectoryForUser}));
            indentingPrintWriter.increaseIndent();
            File[] listFiles = syntheticPasswordDirectoryForUser.listFiles();
            if (listFiles != null) {
                Arrays.sort(listFiles);
                for (File file : listFiles) {
                    indentingPrintWriter.println(TextUtils.formatSimple("%6d %s %s", new Object[]{Long.valueOf(file.length()), timestampToString(file.lastModified()), file.getName()}));
                }
            } else {
                indentingPrintWriter.println("[Not found]");
            }
            indentingPrintWriter.decreaseIndent();
            SPBnRManager.dump(indentingPrintWriter, userInfo.id);
        }
        File repairModePersistentDataFile = lockSettingsStorage.getRepairModePersistentDataFile();
        if (repairModePersistentDataFile.exists()) {
            indentingPrintWriter.println(TextUtils.formatSimple("Repair Mode [%s]:", new Object[]{repairModePersistentDataFile.getParent()}));
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(TextUtils.formatSimple("%6d %s %s", new Object[]{Long.valueOf(repairModePersistentDataFile.length()), timestampToString(repairModePersistentDataFile.lastModified()), repairModePersistentDataFile.getName()}));
            byte[] readFile = lockSettingsStorage.readFile(lockSettingsStorage.getRepairModePersistentDataFile());
            LockSettingsStorage.PersistentData fromBytes = readFile == null ? LockSettingsStorage.PersistentData.NONE : LockSettingsStorage.PersistentData.fromBytes(readFile);
            Integer valueOf = Integer.valueOf(fromBytes.type);
            Integer valueOf2 = Integer.valueOf(fromBytes.userId);
            byte[] bArr = fromBytes.payload;
            indentingPrintWriter.println(TextUtils.formatSimple("type: %d, user id: %d, payload size: %d", new Object[]{valueOf, valueOf2, Integer.valueOf(bArr != null ? bArr.length : 0)}));
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("StrongAuth:");
        indentingPrintWriter.increaseIndent();
        LockSettingsStrongAuth lockSettingsStrongAuth = this.mStrongAuth;
        lockSettingsStrongAuth.getClass();
        indentingPrintWriter.println("PrimaryAuthFlags state:");
        indentingPrintWriter.increaseIndent();
        for (int i4 = 0; i4 < lockSettingsStrongAuth.mStrongAuthForUser.size(); i4++) {
            int keyAt = lockSettingsStrongAuth.mStrongAuthForUser.keyAt(i4);
            int valueAt = lockSettingsStrongAuth.mStrongAuthForUser.valueAt(i4);
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(keyAt, "userId=", ", primaryAuthFlags=");
            m.append(Integer.toHexString(valueAt));
            indentingPrintWriter.println(m.toString());
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("NonStrongBiometricAllowed state:");
        indentingPrintWriter.increaseIndent();
        for (int i5 = 0; i5 < lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.size(); i5++) {
            indentingPrintWriter.println("userId=" + lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.keyAt(i5) + ", allowed=" + lockSettingsStrongAuth.mIsNonStrongBiometricAllowedForUser.valueAt(i5));
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("strong auth timeout state:");
        indentingPrintWriter.increaseIndent();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        long biometricStrongAuthTimeout = lockSettingsStrongAuth.mLockPatternUtils.getBiometricStrongAuthTimeout("lockscreen.strong_bio_timeout", 0);
        if (biometricStrongAuthTimeout != 0) {
            indentingPrintWriter.println("strong biometric timeout:" + simpleDateFormat.format(new Date(biometricStrongAuthTimeout)));
        }
        long biometricStrongAuthTimeout2 = lockSettingsStrongAuth.mLockPatternUtils.getBiometricStrongAuthTimeout("lockscreen.non_strong_bio_timeout", 0);
        if (biometricStrongAuthTimeout2 != 0) {
            indentingPrintWriter.println("non strong biometric timeout:" + simpleDateFormat.format(new Date(biometricStrongAuthTimeout2)));
        }
        long biometricStrongAuthTimeout3 = lockSettingsStrongAuth.mLockPatternUtils.getBiometricStrongAuthTimeout("lockscreen.non_strong_bio_idle_timeout", 0);
        if (biometricStrongAuthTimeout3 != 0) {
            indentingPrintWriter.println("non strong biometric idle timeout:" + simpleDateFormat.format(new Date(biometricStrongAuthTimeout3)));
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("RebootEscrow:");
        indentingPrintWriter.increaseIndent();
        RebootEscrowManager rebootEscrowManager = this.mRebootEscrowManager;
        rebootEscrowManager.getClass();
        indentingPrintWriter.print("mRebootEscrowWanted=");
        indentingPrintWriter.println(rebootEscrowManager.mRebootEscrowWanted);
        indentingPrintWriter.print("mRebootEscrowReady=");
        indentingPrintWriter.println(rebootEscrowManager.mRebootEscrowReady);
        indentingPrintWriter.print("mRebootEscrowListener=");
        indentingPrintWriter.println(rebootEscrowManager.mRebootEscrowListener);
        indentingPrintWriter.print("mLoadEscrowDataErrorCode=");
        indentingPrintWriter.println(rebootEscrowManager.mLoadEscrowDataErrorCode);
        synchronized (rebootEscrowManager.mKeyGenerationLock) {
            if (rebootEscrowManager.mPendingRebootEscrowKey == null) {
                z = false;
            }
        }
        indentingPrintWriter.print("mPendingRebootEscrowKey is ");
        indentingPrintWriter.println(z ? "set" : "not set");
        RebootEscrowProviderInterface rebootEscrowProviderInterface = rebootEscrowManager.mInjector.mRebootEscrowProvider;
        indentingPrintWriter.print("RebootEscrowProvider type is " + (rebootEscrowProviderInterface == null ? "null" : String.valueOf(rebootEscrowProviderInterface.getType())));
        indentingPrintWriter.println();
        indentingPrintWriter.println("Event log:");
        indentingPrintWriter.increaseIndent();
        RebootEscrowManager.RebootEscrowEventLog rebootEscrowEventLog = rebootEscrowManager.mEventLog;
        rebootEscrowEventLog.getClass();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        while (true) {
            RebootEscrowManager.RebootEscrowEvent[] rebootEscrowEventArr = rebootEscrowEventLog.mEntries;
            if (i >= rebootEscrowEventArr.length) {
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("PasswordHandleCount: " + this.mGatekeeperPasswords.size());
                synchronized (this.mUserCreationAndRemovalLock) {
                    indentingPrintWriter.println("ThirdPartyAppsStarted: " + this.mThirdPartyAppsStarted);
                }
                indentingPrintWriter.println("LSSLog:");
                LsLog.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                return;
            }
            RebootEscrowManager.RebootEscrowEvent rebootEscrowEvent = rebootEscrowEventArr[(rebootEscrowEventLog.mNextIndex + i) % rebootEscrowEventArr.length];
            if (rebootEscrowEvent != null) {
                indentingPrintWriter.print("Event #");
                indentingPrintWriter.println(i);
                indentingPrintWriter.println(" time=" + simpleDateFormat2.format(new Date(rebootEscrowEvent.mWallTime)) + " (timestamp=" + rebootEscrowEvent.mTimestamp + ")");
                indentingPrintWriter.print(" event=");
                int i6 = rebootEscrowEvent.mEventId;
                switch (i6) {
                    case 1:
                        str = "Found escrow data";
                        break;
                    case 2:
                        str = "Set armed status";
                        break;
                    case 3:
                        str = "Cleared request for LSKF";
                        break;
                    case 4:
                        str = "Retrieved stored KEK";
                        break;
                    case 5:
                        str = "Requested LSKF";
                        break;
                    case 6:
                        str = "Stored LSKF for user";
                        break;
                    case 7:
                        str = "Retrieved LSKF for user";
                        break;
                    default:
                        str = VibrationParam$1$$ExternalSyntheticOutline0.m(i6, "Unknown event ID ");
                        break;
                }
                indentingPrintWriter.println(str);
                Integer num = rebootEscrowEvent.mUserId;
                if (num != null) {
                    indentingPrintWriter.print(" user=");
                    indentingPrintWriter.println(num);
                }
            }
            i++;
        }
    }

    public final void enforceFrpNotActive() {
        this.mInjector.getClass();
        int mainUserId = Injector.getUserManagerInternal().getMainUserId();
        if (mainUserId < 0) {
            Slog.d("LockSettingsService", "No Main user on device; skipping enforceFrpNotActive");
            return;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = false;
        boolean z2 = Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, mainUserId) == 0;
        if (Flags.frpEnforcement()) {
            PersistentDataBlockManager persistentDataBlockManager = (PersistentDataBlockManager) this.mStorage.mContext.getSystemService(PersistentDataBlockManager.class);
            if (persistentDataBlockManager != null) {
                z = persistentDataBlockManager.isFactoryResetProtectionActive();
            } else {
                Slog.wtf("LockSettingsStorage", "Failed to get PersistentDataBlockManager");
            }
        } else if (Settings.Global.getInt(contentResolver, "secure_frp_mode", 0) == 1 && z2) {
            z = true;
        }
        if (z) {
            throw new SecurityException("Cannot change credential while factory reset protection is active");
        }
    }

    public final void expirePreviousData() {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("expirePreviousData requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        long backupLskfBasedProtectorId = getBackupLskfBasedProtectorId(0);
        if (0 == backupLskfBasedProtectorId) {
            return;
        }
        synchronized (this.mSpManager) {
            setBackupLskfBasedProtectorId(0, 0L);
            this.mSpManager.destroyLskfBasedProtector(0, backupLskfBasedProtectorId);
        }
        LockPatternUtils.invalidateCredentialTypeCache();
    }

    public final void gateKeeperClearSecureUserId(int i) {
        try {
            getGateKeeperService().clearSecureUserId(i);
        } catch (RemoteException e) {
            Slog.w("LockSettingsService", "Failed to clear SID", e);
        }
    }

    public final String generateKey(String str) {
        return this.mRecoverableKeyStoreManager.generateKeyWithMetadata(str, null);
    }

    public final String generateKeyWithMetadata(String str, byte[] bArr) {
        return this.mRecoverableKeyStoreManager.generateKeyWithMetadata(str, bArr);
    }

    public final long getBackupLskfBasedProtectorId(int i) {
        return getLong("backup-protector-id", 0L, i);
    }

    public final boolean getBoolean(String str, boolean z, int i) {
        checkDatabaseReadPermission(i, str);
        return this.mStorage.getBoolean(str, z, i);
    }

    public final boolean getCarrierLock(int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        int i2 = lockSettingsStorage.mSKTLockState;
        if (i2 != 0) {
            if (i2 != 1) {
                return false;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("getCarrierLock#mSKTLockState = "), lockSettingsStorage.mSKTLockState, "LockSettingsStorage");
        } else {
            if (!lockSettingsStorage.getCarrierLockFromFile()) {
                lockSettingsStorage.mSKTLockState = 2;
                return false;
            }
            lockSettingsStorage.mSKTLockState = 1;
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("getCarrierLock#mSKTLockState = "), lockSettingsStorage.mSKTLockState, "LockSettingsStorage");
        }
        return true;
    }

    public final int getCredentialType(int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsHave");
        return getCredentialTypeInternal(i);
    }

    public final int getCredentialTypeInternal(int i) {
        byte[] bArr;
        int i2 = -1;
        if (LockPatternUtils.isSpecialUserId(i)) {
            LockSettingsStorage.PersistentData specialUserPersistentData = this.mSpManager.getSpecialUserPersistentData(i);
            int i3 = specialUserPersistentData.type;
            if ((i3 != 1 && i3 != 2) || (bArr = specialUserPersistentData.payload) == null) {
                return -1;
            }
            int i4 = SyntheticPasswordManager.PasswordData.fromBytes(bArr).credentialType;
            return i4 != 2 ? i4 : LockPatternUtils.pinOrPasswordQualityToCredentialType(specialUserPersistentData.qualityForUi);
        }
        long j = 0;
        if (i == -9899 && isEnablePrevCredential()) {
            synchronized (this.mSpManager) {
                try {
                    long backupLskfBasedProtectorId = getBackupLskfBasedProtectorId(0);
                    if (backupLskfBasedProtectorId == 0) {
                        return -1;
                    }
                    byte[] readSyntheticPasswordState = this.mSpManager.mStorage.readSyntheticPasswordState(0, "pwd", backupLskfBasedProtectorId);
                    int i5 = readSyntheticPasswordState == null ? -1 : SyntheticPasswordManager.PasswordData.fromBytes(readSyntheticPasswordState).credentialType;
                    if (i5 != 2) {
                        return i5;
                    }
                    return -1;
                } finally {
                }
            }
        }
        String string = this.mStorage.getString("lockscreen.password_type", null, i);
        if (LockPatternUtils.isQualitySmartCard((int) (TextUtils.isEmpty(string) ? 0L : Long.parseLong(string)))) {
            return 6;
        }
        synchronized (this.mSpManager) {
            try {
                long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
                if (currentLskfBasedProtectorId == 0) {
                    return -1;
                }
                byte[] readSyntheticPasswordState2 = this.mSpManager.mStorage.readSyntheticPasswordState(i, "pwd", currentLskfBasedProtectorId);
                if (readSyntheticPasswordState2 != null) {
                    i2 = SyntheticPasswordManager.PasswordData.fromBytes(readSyntheticPasswordState2).credentialType;
                }
                if (i2 != 2) {
                    return i2;
                }
                String string2 = this.mStorage.getString("lockscreen.password_type", null, i);
                if (!TextUtils.isEmpty(string2)) {
                    j = Long.parseLong(string2);
                }
                return LockPatternUtils.pinOrPasswordQualityToCredentialType((int) j);
            } finally {
            }
        }
    }

    public long getCurrentLskfBasedProtectorId(int i) {
        return getLong("sp-handle", 0L, i);
    }

    public LockscreenCredential getDecryptedPasswordForTiedProfile(int i) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, CertificateException, IOException {
        Slogf.d("LockSettingsService", "Decrypting password for tied profile %d", Integer.valueOf(i));
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] readFile = lockSettingsStorage.readFile(lockSettingsStorage.getChildProfileLockFile(i));
        if (readFile == null) {
            throw new FileNotFoundException("Child profile lock file not found");
        }
        byte[] copyOfRange = Arrays.copyOfRange(readFile, 0, 12);
        byte[] copyOfRange2 = Arrays.copyOfRange(readFile, 12, readFile.length);
        SecretKey secretKey = (SecretKey) this.mKeyStore.getKey("profile_key_name_decrypt_" + i, null);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, secretKey, new GCMParameterSpec(128, copyOfRange));
        byte[] doFinal = cipher.doFinal(copyOfRange2);
        LockscreenCredential createUnifiedProfilePassword = LockscreenCredential.createUnifiedProfilePassword(doFinal);
        Arrays.fill(doFinal, (byte) 0);
        try {
            this.mUnifiedProfilePasswordCache.storePassword(i, createUnifiedProfilePassword, getGateKeeperService().getSecureUserId(this.mUserManager.getProfileParent(i).id));
        } catch (RemoteException e) {
            Slogf.w("LockSettingsService", "Failed to talk to GateKeeper service", e);
        }
        return createUnifiedProfilePassword;
    }

    public final long getExpireTimeForPrev() {
        if (hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") || hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            return updateExpireTimeForPrev(false);
        }
        throw new SecurityException("expirePreviousData requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
    }

    public final int getFailureCount(int i) {
        int failureCount;
        synchronized (this.mSpManager) {
            try {
                failureCount = isSupportWeaver() ? this.mSpManager.getFailureCount(getHermesService(), getCurrentLskfBasedProtectorId(i), i) : this.mSpManager.getFailureCount(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), i);
            } catch (Throwable th) {
                throw th;
            }
        }
        LsLog.setFailureCount(i, failureCount);
        if (failureCount < 0) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(failureCount, "getFailureCount error ", "LockSettingsService");
            this.mHandler.removeCallbacks(this.mResetDebugLevel);
            this.mHandler.post(this.mResetDebugLevel);
            return 0;
        }
        if (failureCount < LsConstants.SECURITY_DEBUG_ON_COUNT) {
            return failureCount;
        }
        this.mHandler.removeCallbacks(this.mResetDebugLevel);
        this.mHandler.post(this.mResetDebugLevel);
        return failureCount;
    }

    public final synchronized IGateKeeperService getGateKeeperService() {
        IGateKeeperService iGateKeeperService = this.mGateKeeperService;
        if (iGateKeeperService != null) {
            return iGateKeeperService;
        }
        IBinder waitForService = ServiceManager.waitForService("android.service.gatekeeper.IGateKeeperService");
        if (waitForService == null) {
            Slog.e("LockSettingsService", "Unable to acquire GateKeeperService");
            return null;
        }
        try {
            waitForService.linkToDeath(new DualDARCallback(this), 0);
        } catch (RemoteException e) {
            Slog.w("LockSettingsService", " Unable to register death recipient", e);
        }
        IGateKeeperService asInterface = IGateKeeperService.Stub.asInterface(waitForService);
        this.mGateKeeperService = asInterface;
        return asInterface;
    }

    public final byte[] getHashFactor(LockscreenCredential lockscreenCredential, int i) {
        int i2;
        checkPasswordReadPermission();
        try {
            Slogf.d("LockSettingsService", "Getting password history hash factor for user %d", Integer.valueOf(i));
            if (isEnterpriseUserId(i)) {
                lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
            }
            if (isProfileWithUnifiedLock(i)) {
                try {
                    lockscreenCredential = getDecryptedPasswordForTiedProfile(i);
                } catch (Exception e) {
                    Slog.e("LockSettingsService", "Failed to get unified profile password", e);
                    scheduleGc();
                    return null;
                }
            }
            LockscreenCredential lockscreenCredential2 = lockscreenCredential;
            synchronized (this.mSpManager) {
                boolean z = false;
                if (i == -9899) {
                    try {
                        Slog.e("LockSettingsService", "!@ getHashFactor with prev!!!");
                        i2 = 0;
                        z = true;
                    } finally {
                    }
                } else {
                    i2 = i;
                }
                SyntheticPasswordManager.SyntheticPassword syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), z ? getBackupLskfBasedProtectorId(i2) : getCurrentLskfBasedProtectorId(i2), lockscreenCredential2, i2, null).syntheticPassword;
                if (syntheticPassword == null) {
                    Slog.w("LockSettingsService", "Current credential is incorrect");
                    scheduleGc();
                    return null;
                }
                byte[] deriveSubkey = syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_PASSWORD_HASH);
                scheduleGc();
                return deriveSubkey;
            }
        } catch (Throwable th) {
            scheduleGc();
            throw th;
        }
    }

    public final synchronized IHermesService getHermesService() {
        IHermesService iHermesService = this.mHermesService;
        if (iHermesService != null) {
            return iHermesService;
        }
        this.mHermesService = IHermesService.Stub.asInterface(((SemUnionManager) this.mContext.getSystemService("sepunion")).getSemSystemService("HermesService"));
        Slog.w("LockSettingsService", "getHermesService() is " + this.mHermesService);
        return this.mHermesService;
    }

    public final String getKey(String str) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        Objects.requireNonNull(str, "alias is null");
        return recoverableKeyStoreManager.getAlias(UserHandle.getCallingUserId(), Binder.getCallingUid(), str);
    }

    public final KeyChainSnapshot getKeyChainSnapshot() {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        KeyChainSnapshot keyChainSnapshot = recoverableKeyStoreManager.mSnapshotStorage.get(Binder.getCallingUid());
        if (keyChainSnapshot != null) {
            return keyChainSnapshot;
        }
        throw new ServiceSpecificException(21);
    }

    public final long getLong(String str, long j, int i) {
        checkDatabaseReadPermission(i, str);
        String string = this.mStorage.getString(str, null, i);
        return TextUtils.isEmpty(string) ? j : Long.parseLong(string);
    }

    public final int getPinLength(int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsHave");
        PasswordMetrics userPasswordMetrics = getUserPasswordMetrics(i);
        if (userPasswordMetrics != null && userPasswordMetrics.credType == 3) {
            return userPasswordMetrics.length;
        }
        synchronized (this.mSpManager) {
            try {
                long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
                int i2 = -1;
                if (currentLskfBasedProtectorId == 0) {
                    return -1;
                }
                byte[] readSyntheticPasswordState = this.mSpManager.mStorage.readSyntheticPasswordState(i, "pwd", currentLskfBasedProtectorId);
                if (readSyntheticPasswordState != null) {
                    i2 = SyntheticPasswordManager.PasswordData.fromBytes(readSyntheticPasswordState).pinLength;
                }
                return i2;
            } finally {
            }
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

    public final int[] getRecoverySecretTypes() {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        return recoverableKeyStoreManager.mDatabase.getRecoverySecretTypes(UserHandle.getCallingUserId(), Binder.getCallingUid());
    }

    public final Map getRecoveryStatus() {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        Cursor query = recoverableKeyStoreManager.mDatabase.mKeyStoreDbHelper.getReadableDatabase().query("keys", new String[]{KnoxCustomManagerService.ID, "alias", "recovery_status"}, "uid = ?", new String[]{Integer.toString(Binder.getCallingUid())}, null, null, null);
        try {
            HashMap hashMap = new HashMap();
            while (query.moveToNext()) {
                hashMap.put(query.getString(query.getColumnIndexOrThrow("alias")), Integer.valueOf(query.getInt(query.getColumnIndexOrThrow("recovery_status"))));
            }
            query.close();
            return hashMap;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final boolean getSeparateProfileChallengeEnabled(int i) {
        checkDatabaseReadPermission(i, "lockscreen.profilechallenge");
        return getSeparateProfileChallengeEnabledInternal(i);
    }

    public final boolean getSeparateProfileChallengeEnabledInternal(int i) {
        boolean z;
        synchronized (this.mSeparateChallengeLock) {
            z = this.mStorage.getBoolean("lockscreen.profilechallenge", false, i);
        }
        return z;
    }

    public final String getString(String str, String str2, int i) {
        checkDatabaseReadPermission(i, str);
        return this.mStorage.getString(str, str2, i);
    }

    public final int getStrongAuthForUser(int i) {
        checkPasswordReadPermission();
        return this.mStrongAuthTracker.getStrongAuthForUser(i);
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

    public final boolean hasPendingEscrowToken(int i) {
        boolean z;
        checkPasswordReadPermission();
        synchronized (this.mSpManager) {
            z = !this.mSpManager.getPendingTokensForUser(i).isEmpty();
        }
        return z;
    }

    public final boolean hasPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    public final boolean hasSecureLockScreen() {
        return this.mHasSecureLockScreen;
    }

    public final boolean haveAppLockBackupPin(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        return lockSettingsStorage.hasFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockbackuppin.key"));
    }

    public final boolean haveAppLockFingerprintPassword(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        return lockSettingsStorage.hasFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockfingerprintpassword.key"));
    }

    public final boolean haveAppLockPassword(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        return lockSettingsStorage.hasFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpassword.key"));
    }

    public final boolean haveAppLockPattern(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        return lockSettingsStorage.hasFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpattern.key"));
    }

    public final boolean haveAppLockPin(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        return lockSettingsStorage.hasFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpin.key"));
    }

    public final boolean haveCarrierPassword(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        return lockSettingsStorage.hasFile(LockSettingsStorage.getLockCredentialFileForUser(i, "sktpassword.key"));
    }

    public final boolean haveFMMPassword(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        return lockSettingsStorage.hasFile(LockSettingsStorage.getLockCredentialFileForUser(i, "fmmpassword.key"));
    }

    public final String importKey(String str, byte[] bArr) {
        return this.mRecoverableKeyStoreManager.importKeyWithMetadata(str, bArr, null);
    }

    public final String importKeyWithMetadata(String str, byte[] bArr, byte[] bArr2) {
        return this.mRecoverableKeyStoreManager.importKeyWithMetadata(str, bArr, bArr2);
    }

    public void initKeystoreSuperKeys(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        syntheticPassword.getClass();
        byte[] bytesToHex = SyntheticPasswordManager.bytesToHex(syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_KEY_STORE_PASSWORD));
        try {
            if (AndroidKeyStoreMaintenance.initUserSuperKeys(i, bytesToHex, z) == 0) {
                return;
            }
            throw new IllegalStateException("Failed to initialize Keystore super keys for user " + i);
        } finally {
            Arrays.fill(bytesToHex, (byte) 0);
        }
    }

    public final void initRecoveryServiceWithSigFile(String str, byte[] bArr, byte[] bArr2) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        recoverableKeyStoreManager.mTestCertHelper.getClass();
        String defaultCertificateAliasIfEmpty = TestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str);
        Objects.requireNonNull(bArr, "recoveryServiceCertFile is null");
        Objects.requireNonNull(bArr2, "recoveryServiceSigFile is null");
        try {
            try {
                SigXml.parse(bArr2).verifyFileSignature(TestOnlyInsecureCertificateHelper.getRootCertificate(defaultCertificateAliasIfEmpty), bArr, TestOnlyInsecureCertificateHelper.getValidationDate(defaultCertificateAliasIfEmpty));
                recoverableKeyStoreManager.initRecoveryService(defaultCertificateAliasIfEmpty, bArr);
            } catch (CertValidationException e) {
                Log.e("RecoverableKeyStoreMgr", "The signature over the cert file is invalid. Cert: " + HexDump.toHexString(bArr) + " Sig: " + HexDump.toHexString(bArr2));
                throw new ServiceSpecificException(28, e.getMessage());
            }
        } catch (CertParsingException e2) {
            Log.d("RecoverableKeyStoreMgr", "Failed to parse the sig file: " + HexDump.toHexString(bArr2));
            throw new ServiceSpecificException(25, e2.getMessage());
        }
    }

    public SyntheticPasswordManager.SyntheticPassword initializeSyntheticPassword(int i) {
        SyntheticPasswordManager.SyntheticPassword newSyntheticPassword;
        SDPLog.i("Initialize sp for user " + i);
        LsLog.enrollRequest(i, Process.myPid(), "initializeSP");
        LsLog.enroll(String.format("Enroll [User %d NONE][initializeSP]", Integer.valueOf(i)));
        synchronized (this.mSpManager) {
            try {
                Slogf.i("LockSettingsService", "Initializing synthetic password for user %d", Integer.valueOf(i));
                Preconditions.checkState(getCurrentLskfBasedProtectorId(i) == 0, "Cannot reinitialize SP");
                newSyntheticPassword = this.mSpManager.newSyntheticPassword(i);
                setCurrentLskfBasedProtectorId(i, this.mSpManager.createLskfBasedProtector(getGateKeeperService(), null, 0L, LockscreenCredential.createNone(), newSyntheticPassword, i));
                setCeStorageProtection(i, newSyntheticPassword);
                if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                    initKeystoreSuperKeys(i, newSyntheticPassword, false);
                }
                onSyntheticPasswordKnown(i, newSyntheticPassword, true);
                Slogf.i("LockSettingsService", "Successfully initialized synthetic password for user %d", Integer.valueOf(i));
                LsLog.enroll(SPBnRManager.getPWFilelist(i), true);
            } catch (Throwable th) {
                throw th;
            }
        }
        return newSyntheticPassword;
    }

    public final boolean isCeStorageUnlocked(int i) {
        try {
            return this.mStorageManager.isCeStorageUnlocked(i);
        } catch (RemoteException e) {
            Slog.e("LockSettingsService", "Error checking whether CE storage is unlocked", e);
            return false;
        }
    }

    public boolean isCredentialSharableWithParent(int i) {
        UserManager userManager;
        UserHandle of = UserHandle.of(i);
        if (this.mUserManagerCache.containsKey(of)) {
            userManager = (UserManager) this.mUserManagerCache.get(of);
        } else {
            try {
                UserManager userManager2 = (UserManager) this.mContext.createPackageContextAsUser("system", 0, of).getSystemService(UserManager.class);
                this.mUserManagerCache.put(of, userManager2);
                userManager = userManager2;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException("Failed to create context for user " + of, e);
            }
        }
        return userManager.isCredentialSharableWithParent();
    }

    public final boolean isDualDarAuthUserId(int i) {
        return VirtualLockUtils.isVirtualUserId(i) ? this.mDualDarAuthUtils.isInnerAuthUserForDo(i) : SemPersonaManager.isDarDualEncryptionEnabled(i) && !SemPersonaManager.isDoEnabled(i);
    }

    public final boolean isEnterpriseUserId(int i) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mInjector.getClass();
            UserInfo userInfo = Injector.getUserManagerInternal().getUserInfo(i);
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

    public final boolean isEscrowTokenActive(long j, int i) {
        boolean hasState;
        synchronized (this.mSpManager) {
            hasState = this.mSpManager.hasState(i, "spblob", j);
        }
        return hasState;
    }

    public final boolean isKnoxAdminActivated(int i) {
        this.mInjector.getClass();
        IEnterpriseDeviceManager asInterface = IEnterpriseDeviceManager.Stub.asInterface(ServiceManager.getService("enterprise_policy"));
        if (asInterface == null) {
            return false;
        }
        try {
            return asInterface.isMdmAdminPresentAsUser(i);
        } catch (RemoteException e) {
            Log.w("LockSettingsService", "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public final boolean isProfileWithUnifiedLock(int i) {
        return isCredentialSharableWithParent(i) && !getSeparateProfileChallengeEnabledInternal(i);
    }

    public final boolean isRemoteLock(int i) {
        if (!haveFMMPassword(i) && !getCarrierLock(i)) {
            for (int i2 = 0; i2 < 4; i2++) {
                if (!getBoolean(i2 + "locked", false, i)) {
                }
            }
            return false;
        }
        return true;
    }

    public final boolean isSupportWeaver() {
        return SystemProperties.getBoolean("security.securehw.available", false);
    }

    public final boolean isSyntheticPasswordBasedCredentialLocked(int i) {
        return getCurrentLskfBasedProtectorId(i) != 0;
    }

    public final boolean isUserSecure(int i) {
        return getCredentialTypeInternal(i) != -1;
    }

    public final boolean isWeakEscrowTokenActive(long j, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return isEscrowTokenActive(j, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isWeakEscrowTokenValid(long j, byte[] bArr, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSpManager) {
                if (!this.mSpManager.hasEscrowData(i)) {
                    Slog.w("LockSettingsService", "Escrow token is disabled on the current user");
                    return false;
                }
                if (this.mSpManager.unlockTokenBasedProtectorInternal(getGateKeeperService(), j, (byte) 2, bArr, i).syntheticPassword == null) {
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

    public final PasswordMetrics loadPasswordMetrics(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        synchronized (this.mSpManager) {
            try {
                if (!isUserSecure(i)) {
                    return null;
                }
                return this.mSpManager.getPasswordMetrics(i, getCurrentLskfBasedProtectorId(i), syntheticPassword);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void maybeShowEncryptionNotificationForUser(int r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 679
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.locksettings.LockSettingsService.maybeShowEncryptionNotificationForUser(int, java.lang.String):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void migrateLockSettingsDB() {
        /*
            r7 = this;
            java.lang.String r0 = "locksettings_db_backup"
            java.lang.String r1 = "locksettings_db_restore"
            boolean r2 = com.android.server.locksettings.LockSettingsService.mIsDbMigrated
            java.lang.String r3 = "LockSettingsService"
            if (r2 == 0) goto L12
            java.lang.String r7 = "!@ migrateLockSettingsDB already finish"
            android.util.Slog.w(r3, r7)
            return
        L12:
            r2 = 0
            com.android.server.locksettings.LockSettingsStorage r4 = r7.mStorage     // Catch: java.lang.Exception -> Lad
            com.android.server.locksettings.LockSettingsStorage$DatabaseHelper r4 = r4.mOpenHelper     // Catch: java.lang.Exception -> Lad
            android.database.sqlite.SQLiteDatabase r4 = r4.getWritableDatabase()     // Catch: java.lang.Exception -> Lad
            java.lang.String r5 = "PRAGMA quick_check(1)"
            r6 = 0
            android.database.Cursor r4 = r4.rawQuery(r5, r6)     // Catch: java.lang.Exception -> L37
            boolean r5 = r4.moveToFirst()     // Catch: java.lang.Throwable -> L39
            if (r5 == 0) goto L3b
            java.lang.String r5 = "ok"
            java.lang.String r6 = r4.getString(r2)     // Catch: java.lang.Throwable -> L39
            boolean r5 = r5.equals(r6)     // Catch: java.lang.Throwable -> L39
            r4.close()     // Catch: java.lang.Exception -> L37
            goto L4e
        L37:
            r4 = move-exception
            goto L4a
        L39:
            r5 = move-exception
            goto L3f
        L3b:
            r4.close()     // Catch: java.lang.Exception -> L37
            goto L4d
        L3f:
            if (r4 == 0) goto L49
            r4.close()     // Catch: java.lang.Throwable -> L45
            goto L49
        L45:
            r4 = move-exception
            r5.addSuppressed(r4)     // Catch: java.lang.Exception -> L37
        L49:
            throw r5     // Catch: java.lang.Exception -> L37
        L4a:
            r4.printStackTrace()     // Catch: java.lang.Exception -> Lad
        L4d:
            r5 = r2
        L4e:
            if (r5 == 0) goto L92
            java.lang.String r4 = "!@ LockSettingsDB, checkIntegrity pass."
            android.util.Slog.w(r3, r4)     // Catch: java.lang.Exception -> Lad
            r3 = 0
            long r5 = r7.getLong(r0, r3, r2)     // Catch: java.lang.Exception -> Lad
            int r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r5 != 0) goto Lc7
            long r5 = r7.getCurrentLskfBasedProtectorId(r2)     // Catch: java.lang.Exception -> Lad
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 == 0) goto L75
            java.lang.String r3 = "migrate backup locksettings.db! Maybe FOTA updated or backup failed. try to backup DB!"
            com.samsung.android.lock.LsLog.restore(r3)     // Catch: java.lang.Exception -> Lad
            long r3 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> Lad
            r7.setLong(r0, r3, r2)     // Catch: java.lang.Exception -> Lad
            goto Lc7
        L75:
            com.android.server.locksettings.LockSettingsStorage r0 = r7.mStorage     // Catch: java.lang.Exception -> Lad
            boolean r0 = r0.hasLockSettingsBackup()     // Catch: java.lang.Exception -> Lad
            if (r0 == 0) goto L8b
            java.lang.String r0 = "migrate backup locksettings.db! Maybe DB removed. try to restore DB!"
            com.samsung.android.lock.LsLog.restore(r0)     // Catch: java.lang.Exception -> Lad
            long r3 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> Lad
            r7.setLong(r1, r3, r2)     // Catch: java.lang.Exception -> Lad
            goto Lc7
        L8b:
            java.lang.String r0 = "migrate backup locksettings.db! Maybe 1st boot!!"
            com.samsung.android.lock.LsLog.restore(r0)     // Catch: java.lang.Exception -> Lad
            goto Lc7
        L92:
            com.android.server.locksettings.LockSettingsStorage r0 = r7.mStorage     // Catch: java.lang.Exception -> Lad
            boolean r0 = r0.hasLockSettingsBackup()     // Catch: java.lang.Exception -> Lad
            if (r0 == 0) goto La7
            java.lang.String r0 = "LockSettingsDB, DB Integrity failed! restore locksettings.db!"
            com.samsung.android.lock.LsLog.restore(r0)     // Catch: java.lang.Exception -> Lad
            long r3 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> Lad
            r7.setLong(r1, r3, r2)     // Catch: java.lang.Exception -> Lad
            goto Lac
        La7:
            java.lang.String r0 = "LockSettingsDB, DB Integrity failed! but do not have backup!"
            com.samsung.android.lock.LsLog.restore(r0)     // Catch: java.lang.Exception -> Lad
        Lac:
            return
        Lad:
            com.android.server.locksettings.LockSettingsStorage r0 = r7.mStorage
            boolean r0 = r0.hasLockSettingsBackup()
            if (r0 == 0) goto Lc2
            java.lang.String r0 = "LockSettingsDB, Found corrupted! restore locksettings.db!"
            com.samsung.android.lock.LsLog.restore(r0)
            long r3 = java.lang.System.currentTimeMillis()
            r7.setLong(r1, r3, r2)
            goto Lc7
        Lc2:
            java.lang.String r7 = "LockSettingsDB, Found corrupted! but do not have backup!"
            com.samsung.android.lock.LsLog.restore(r7)
        Lc7:
            r7 = 1
            com.android.server.locksettings.LockSettingsService.mIsDbMigrated = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.locksettings.LockSettingsService.migrateLockSettingsDB():void");
    }

    public final void migrateMdfppPwdData(int i) {
        synchronized (this.mSpManager) {
            try {
                if (isSyntheticPasswordBasedCredentialLocked(i)) {
                    this.mSpManager.migratePwdDataForKnox(i, getCurrentLskfBasedProtectorId(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void migrateOldDataAfterSystemReady() {
        int i;
        byte[] bArr;
        if (LockPatternUtils.frpCredentialEnabled(this.mContext) && !getBoolean("migrated_frp2", false, 0)) {
            LockSettingsStorage.PersistentData readPersistentDataBlock = this.mStorage.readPersistentDataBlock();
            if (readPersistentDataBlock == LockSettingsStorage.PersistentData.NONE || (((i = readPersistentDataBlock.type) == 1 || i == 2) && (bArr = readPersistentDataBlock.payload) != null && bArr.length >= 2 && bArr[0] == 0 && bArr[1] == 2)) {
                Iterator it = this.mUserManager.getUsers().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    UserInfo userInfo = (UserInfo) it.next();
                    if (LockPatternUtils.userOwnsFrpCredential(this.mContext, userInfo) && isUserSecure(userInfo.id)) {
                        synchronized (this.mSpManager) {
                            int i2 = (int) getLong("lockscreen.password_type", 0L, userInfo.id);
                            SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
                            long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(userInfo.id);
                            int i3 = 131072;
                            if (i2 != 131072 && i2 != 196608) {
                                i3 = 262144;
                                if (i2 != 262144 && i2 != 327680 && i2 != 393216) {
                                    syntheticPasswordManager.migrateFrpPasswordLocked(currentLskfBasedProtectorId, userInfo, i2);
                                }
                            }
                            i2 = i3;
                            syntheticPasswordManager.migrateFrpPasswordLocked(currentLskfBasedProtectorId, userInfo, i2);
                        }
                    }
                }
            }
            setBoolean("migrated_frp2", true, 0);
        }
        String str = "";
        String string = getString("lockscreen.pwdata.ver", "", 0);
        String str2 = LsConstants.SECURITY_LOG_VERSION;
        if (!str2.equals(string)) {
            if (TextUtils.isEmpty(string)) {
                string = "empty";
            }
            LsLog.enroll("SecurityLog ver updated! " + string + " -> " + str2);
            setString("lockscreen.pwdata.ver", str2, 0);
        }
        int i4 = this.mStorage.getInt(0, 0, "locksettings.log.ver");
        if (i4 < 1) {
            LsLog.migrate(i4);
            List users = this.mUserManager.getUsers();
            int size = users.size();
            int i5 = 0;
            while (i5 < size) {
                int i6 = ((UserInfo) users.get(i5)).id;
                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i6, "User ", " list updated!\n"));
                m.append(SPBnRManager.getPWFilelist(i6));
                String sb = m.toString();
                this.mStorage.removeKey("lockscreen.token", i6);
                i5++;
                str = sb;
            }
            LsLog.enroll(str);
            LockSettingsStorage lockSettingsStorage = this.mStorage;
            lockSettingsStorage.getClass();
            lockSettingsStorage.setString("locksettings.log.ver", Integer.toString(1), 0);
        }
        try {
            if (IWindowManager.Stub.asInterface(ServiceManager.getService("window")).isSafeModeEnabled()) {
                Log.d("LockSettingsService", "!@ safe mode on");
                setSecurityDebugLevel(1);
            } else if (LsLog.getFailureCount(0) < LsConstants.SECURITY_DEBUG_ON_COUNT) {
                Log.d("LockSettingsService", "!@ safe mode off");
            } else {
                Log.d("LockSettingsService", "!@ debug mode on, Too many failed");
                setSecurityDebugLevel(1);
            }
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("SAFEMODE Exception occurs! "), "LockSettingsService");
        }
    }

    public final void migrateSpblob() {
        if (mIsSpblobMigrated) {
            Slog.w("LockSettingsService", "!@ migrateSpblob already finish");
            return;
        }
        Slog.w("LockSettingsService", "!@ migrateSpblob()");
        SPBnRManager.init(isSupportWeaver());
        SPBnRManager.resetMode();
        if (getLong("locksettings_spblob_backup", 0L, 0) == 0) {
            long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(0);
            if (currentLskfBasedProtectorId != 0) {
                LsLog.restore("migrate backup SPBLOB! Maybe FOTA updated or backup failed. try to backup SPBLOB!");
                SPBnRManager.setProtectorIdForBackup(0, currentLskfBasedProtectorId, getBackupLskfBasedProtectorId(0));
                if (SPBnRManager.startBackup()) {
                    setLong("locksettings_spblob_backup", System.currentTimeMillis(), 0);
                    LsLog.restore("SPblobBNR, Success backup spblob dir");
                } else {
                    LsLog.restore("SPblobBNR, Failed backup spblob dir");
                }
            } else {
                LsLog.restore("migrate backup SPBLOB! Maybe 1st boot!!");
            }
        } else {
            SPBnRManager.setProtectorIdForBackup(0, getCurrentLskfBasedProtectorId(0), getBackupLskfBasedProtectorId(0));
            synchronized (this.mSpManager) {
                try {
                    if (SPBnRManager.checkIntegrity()) {
                        Slog.w("LockSettingsService", "!@ SPblobBNR, checkIntegrity pass.");
                    } else {
                        setLong("locksettings_spblob_backup", System.currentTimeMillis(), 0);
                        LsLog.restore("SPblobBNR, File corruption detected. Backed up or restored.");
                        LsLog.restore(SPBnRManager.getPWFilelist(0));
                        LsLog.restore(SPBnRManager.getBackupPWFilelist(0), true);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        mIsSpblobMigrated = true;
    }

    public final void migrateUserToSpWithBoundCeKeyLocked(int i) {
        if (isUserSecure(i)) {
            Slogf.d("LockSettingsService", "User %d is secured; no migration needed", Integer.valueOf(i));
            return;
        }
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        if (currentLskfBasedProtectorId == 0) {
            Slogf.i("LockSettingsService", "Migrating unsecured user %d to SP-based credential", Integer.valueOf(i));
            initializeSyntheticPassword(i);
            return;
        }
        Slogf.i("LockSettingsService", "Existing unsecured user %d has a synthetic password; re-encrypting CE key with it", Integer.valueOf(i));
        SyntheticPasswordManager.SyntheticPassword syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, LockscreenCredential.createNone(), i, null).syntheticPassword;
        if (syntheticPassword == null) {
            Slogf.wtf("LockSettingsService", "Failed to unwrap synthetic password for unsecured user %d", Integer.valueOf(i));
        } else {
            setCeStorageProtection(i, syntheticPassword);
        }
    }

    public final void migrateUserToSpWithBoundKeysLocked(int i) {
        if (isUserSecure(i)) {
            Slogf.d("LockSettingsService", "User %d is secured; no migration needed", Integer.valueOf(i));
            return;
        }
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        if (currentLskfBasedProtectorId == 0) {
            Slogf.i("LockSettingsService", "Migrating unsecured user %d to SP-based credential", Integer.valueOf(i));
            initializeSyntheticPassword(i);
            return;
        }
        Slogf.i("LockSettingsService", "Existing unsecured user %d has a synthetic password", Integer.valueOf(i));
        SyntheticPasswordManager.SyntheticPassword syntheticPassword = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), currentLskfBasedProtectorId, LockscreenCredential.createNone(), i, null).syntheticPassword;
        if (syntheticPassword == null) {
            Slogf.wtf("LockSettingsService", "Failed to unwrap synthetic password for unsecured user %d", Integer.valueOf(i));
            return;
        }
        if (getString("migrated_all_users_to_sp_and_bound_ce", null, 0) == null) {
            Slogf.i("LockSettingsService", "Encrypting CE key of user %d with synthetic password", Integer.valueOf(i));
            setCeStorageProtection(i, syntheticPassword);
        }
        Slogf.i("LockSettingsService", "Initializing Keystore super keys for user %d", Integer.valueOf(i));
        initKeystoreSuperKeys(i, syntheticPassword, true);
    }

    public final void notifyPasswordChanged(LockscreenCredential lockscreenCredential, int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        if (!isEnterpriseUserId(i)) {
            this.mHandler.post(new LockSettingsService$$ExternalSyntheticLambda3(this, lockscreenCredential, i));
        } else {
            this.mHandler.post(new LockSettingsService$$ExternalSyntheticLambda3(this, PasswordMetrics.computeForCredential(lockscreenCredential), i));
        }
    }

    public final void notifyPasswordChangedForEnterpriseUser(LockscreenCredential lockscreenCredential, int i) {
        if (isEnterpriseUserId(i)) {
            notifyPasswordChanged(lockscreenCredential, i);
        }
    }

    public final void notifySeparateProfileChallengeChanged(final int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                DevicePolicyManagerInternal devicePolicyManagerInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
                if (devicePolicyManagerInternal != null) {
                    devicePolicyManagerInternal.reportSeparateProfileChallengeChanged(i2);
                }
            }
        });
    }

    public final void onCredentialVerified(SyntheticPasswordManager.SyntheticPassword syntheticPassword, PasswordMetrics passwordMetrics, int i) {
        if (UserManager.isVirtualUserId(i)) {
            this.mSdpLockSettings.migrateWithAuthToken(i, syntheticPassword);
            return;
        }
        if (passwordMetrics != null) {
            synchronized (this) {
                this.mUserPasswordMetrics.put(i, passwordMetrics);
            }
        }
        unlockKeystore(i, syntheticPassword);
        unlockCeStorage(i, syntheticPassword);
        unlockUser(i);
        if (!this.mDualDarLockSettings.isDualDARUser(i) || DualDarManager.isOnDeviceOwner(i)) {
            synchronized (this.mSpManager) {
                try {
                    disableEscrowTokenOnNonManagedDevicesIfNeeded(i);
                    for (Long l : this.mSpManager.getPendingTokensForUser(i)) {
                        long longValue = l.longValue();
                        Slogf.i("LockSettingsService", "Activating escrow token %016x for user %d", l, Integer.valueOf(i));
                        this.mSpManager.createTokenBasedProtector(i, longValue, syntheticPassword);
                    }
                } finally {
                }
            }
        }
        if (syntheticPassword.mSecureFolderAuthToken) {
            SDPLog.d(null, "onCredentialVerified : unlockSecureFolderWithToken is true.");
            onSyntheticPasswordKnown(i, syntheticPassword, false);
            return;
        }
        if (isCredentialSharableWithParent(i)) {
            if (getSeparateProfileChallengeEnabledInternal(i)) {
                try {
                    IPasswordPolicy asInterface = IPasswordPolicy.Stub.asInterface(ServiceManager.getService("password_policy"));
                    if (asInterface != null) {
                        if (asInterface.isChangeRequestedAsUser(i) > 0) {
                            Log.d("LockSettingsService", "Password change requested so avoid setDeviceLockedForUser false");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((TrustManager) this.mContext.getSystemService(TrustManager.class)).setDeviceLockedForUser(i, false);
            } else {
                this.mStrongAuth.requireStrongAuth(0, i);
            }
        }
        this.mStrongAuth.mHandler.obtainMessage(5, i, 0).sendToTarget();
        onSyntheticPasswordKnown(i, syntheticPassword, false);
    }

    public final void onPostPasswordChanged(LockscreenCredential lockscreenCredential, int i) {
        Settings.Secure.putLongForUser(this.mContext.getContentResolver(), "ppp_enroll_timestamp", System.currentTimeMillis(), i);
        if (!lockscreenCredential.isNone() && !lockscreenCredential.isPattern()) {
            String string = getString("lockscreen.passwordhistory", null, i);
            String str = "";
            if (string == null) {
                string = "";
            }
            int passwordHistoryLength = this.mInjector.getDevicePolicyManager().getPasswordHistoryLength(null, this.mDualDarAuthUtils.isInnerAuthUserForDo(i) ? this.mDualDarAuthUtils.getMainUserId(i) : i);
            if (passwordHistoryLength != 0) {
                Slogf.d("LockSettingsService", "Adding new password to password history for user %d", Integer.valueOf(i));
                byte[] hashFactor = getHashFactor(lockscreenCredential, i);
                long j = getLong("lockscreen.password_salt", 0L, i);
                if (j == 0) {
                    j = SecureRandomUtils.RNG.nextLong();
                    setLong("lockscreen.password_salt", j, i);
                }
                str = lockscreenCredential.passwordToHistoryHash(Long.toHexString(j).getBytes(), hashFactor);
                if (str == null) {
                    Slog.e("LockSettingsService", "Failed to compute password hash; password history won't be updated");
                } else if (!TextUtils.isEmpty(string)) {
                    String[] split = string.split(",");
                    StringJoiner stringJoiner = new StringJoiner(",");
                    stringJoiner.add(str);
                    for (int i2 = 0; i2 < passwordHistoryLength - 1 && i2 < split.length; i2++) {
                        stringJoiner.add(split[i2]);
                    }
                    str = stringJoiner.toString();
                }
            }
            setString("lockscreen.passwordhistory", str, i);
        }
        ((TrustManager) this.mContext.getSystemService(TrustManager.class)).reportEnabledTrustAgentsChanged(i);
        if (Flags.frpEnforcement()) {
            this.mInjector.getClass();
            if (i == Injector.getUserManagerInternal().getMainUserId()) {
                sendBroadcast(new Intent("android.intent.action.MAIN_USER_LOCKSCREEN_KNOWLEDGE_FACTOR_CHANGED"), UserHandle.of(i), "android.permission.CONFIGURE_FACTORY_RESET_PROTECTION");
            }
        }
        updateVerifier(lockscreenCredential, i);
    }

    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new SecurityException("Caller must be shell");
        }
        int callingPid = Binder.getCallingPid();
        int callingUid2 = Binder.getCallingUid();
        Slogf.i("LockSettingsService", "Executing shell command '%s'; callingPid=%d, callingUid=%d", ArrayUtils.isEmpty(strArr) ? "" : strArr[0], Integer.valueOf(callingPid), Integer.valueOf(callingUid2));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            LockSettingsShellCommand lockSettingsShellCommand = new LockSettingsShellCommand(new LockPatternUtils(this.mContext), this.mContext, callingPid, callingUid2);
            lockSettingsShellCommand.mCallback = this.mShellCommandCallback;
            lockSettingsShellCommand.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onSyntheticPasswordKnown(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword, boolean z) {
        RebootEscrowKey rebootEscrowKey;
        SecretKey secretKey;
        this.mInjector.getClass();
        if (LockPatternUtils.isGsiRunning()) {
            Slog.w("LockSettingsService", "Running in GSI; skipping calls to AuthSecret and RebootEscrow");
            return;
        }
        RebootEscrowManager rebootEscrowManager = this.mRebootEscrowManager;
        byte b = syntheticPassword.mVersion;
        byte[] bArr = syntheticPassword.mSyntheticPassword;
        if (rebootEscrowManager.mRebootEscrowWanted) {
            if (rebootEscrowManager.mInjector.createRebootEscrowProviderIfNeeded() == null) {
                Slog.w("RebootEscrowManager", "Not storing escrow data, RebootEscrowProvider is unavailable");
            } else {
                synchronized (rebootEscrowManager.mKeyGenerationLock) {
                    rebootEscrowKey = rebootEscrowManager.mPendingRebootEscrowKey;
                    secretKey = null;
                    if (rebootEscrowKey == null) {
                        try {
                            rebootEscrowKey = RebootEscrowKey.generate();
                            rebootEscrowManager.mPendingRebootEscrowKey = rebootEscrowKey;
                        } catch (IOException unused) {
                            Slog.w("RebootEscrowManager", "Could not generate reboot escrow key");
                            rebootEscrowKey = null;
                        }
                    }
                }
                if (rebootEscrowKey == null) {
                    Slog.e("RebootEscrowManager", "Could not generate escrow key");
                } else {
                    synchronized (rebootEscrowManager.mKeyStoreManager.mKeyStoreLock) {
                        SecretKey keyStoreEncryptionKeyLocked = RebootEscrowKeyStoreManager.getKeyStoreEncryptionKeyLocked();
                        if (keyStoreEncryptionKeyLocked != null) {
                            secretKey = keyStoreEncryptionKeyLocked;
                        } else {
                            try {
                                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                                KeyGenParameterSpec.Builder encryptionPaddings = new KeyGenParameterSpec.Builder("reboot_escrow_key_store_encryption_key", 3).setKeySize(256).setBlockModes("GCM").setEncryptionPaddings("NoPadding");
                                encryptionPaddings.setNamespace(120);
                                keyGenerator.init(encryptionPaddings.build());
                                secretKey = keyGenerator.generateKey();
                            } catch (GeneralSecurityException e) {
                                Slog.e("RebootEscrowKeyStoreManager", "Unable to generate key from keystore.", e);
                            }
                        }
                    }
                    if (secretKey == null) {
                        Slog.e("RebootEscrowManager", "Failed to generate encryption key from keystore.");
                    } else {
                        try {
                            Objects.requireNonNull(bArr);
                            byte[] encrypt = AesEncryptionUtil.encrypt(AesEncryptionUtil.encrypt(bArr, rebootEscrowKey.mKey), secretKey);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                            dataOutputStream.writeInt(2);
                            dataOutputStream.writeByte(b);
                            dataOutputStream.write(encrypt);
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            LockSettingsStorage lockSettingsStorage = rebootEscrowManager.mStorage;
                            lockSettingsStorage.writeFile(lockSettingsStorage.getRebootEscrowFile(i), byteArray, true);
                            RebootEscrowManager.RebootEscrowEventLog rebootEscrowEventLog = rebootEscrowManager.mEventLog;
                            rebootEscrowEventLog.getClass();
                            RebootEscrowManager.RebootEscrowEvent rebootEscrowEvent = new RebootEscrowManager.RebootEscrowEvent(6, Integer.valueOf(i));
                            int i2 = rebootEscrowEventLog.mNextIndex;
                            RebootEscrowManager.RebootEscrowEvent[] rebootEscrowEventArr = rebootEscrowEventLog.mEntries;
                            rebootEscrowEventArr[i2] = rebootEscrowEvent;
                            rebootEscrowEventLog.mNextIndex = (i2 + 1) % rebootEscrowEventArr.length;
                            rebootEscrowManager.setRebootEscrowReady(true);
                        } catch (IOException e2) {
                            rebootEscrowManager.setRebootEscrowReady(false);
                            Slog.w("RebootEscrowManager", "Could not escrow reboot data", e2);
                        }
                    }
                }
            }
        }
        callToAuthSecretIfNeeded(i, syntheticPassword, z);
    }

    public void onUserStopped(int i) {
        UserProperties userProperties;
        UserHandle userHandle = new UserHandle(i);
        Slogf.d("LockSettingsService", "Hiding encryption notification for user %d", Integer.valueOf(userHandle.getIdentifier()));
        this.mNotificationManager.cancelAsUser(null, 9, userHandle);
        if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace() && android.multiuser.Flags.enablePrivateSpaceFeatures() && (userProperties = this.mUserManager.getUserProperties(UserHandle.of(i))) != null && userProperties.getAllowStoppingUserWithDelayedLocking()) {
            return;
        }
        requireStrongAuth(LockPatternUtils.StrongAuthTracker.getDefaultFlags(this.mContext), i);
        synchronized (this) {
            this.mUserPasswordMetrics.remove(i);
        }
    }

    public final byte[] passwordToHash(int i, byte[] bArr) {
        long j = getLong("lockscreen.password_salt", 0L, i);
        if (j == 0) {
            j = SecureRandomUtils.RNG.nextLong();
            setLong("lockscreen.password_salt", j, i);
        }
        String legacyPasswordToHash = LockscreenCredential.legacyPasswordToHash(bArr, Long.toHexString(j).getBytes());
        if (legacyPasswordToHash == null) {
            return null;
        }
        return legacyPasswordToHash.getBytes(StandardCharsets.UTF_8);
    }

    public final Map recoverKeyChainSnapshot(String str, byte[] bArr, List list) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        int callingUserId = UserHandle.getCallingUserId();
        int callingUid = Binder.getCallingUid();
        RecoverySessionStorage recoverySessionStorage = recoverableKeyStoreManager.mRecoverySessionStorage;
        ArrayList arrayList = (ArrayList) recoverySessionStorage.mSessionsByUid.get(callingUid);
        RecoverySessionStorage.Entry entry = null;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                RecoverySessionStorage.Entry entry2 = (RecoverySessionStorage.Entry) it.next();
                if (str.equals(entry2.mSessionId)) {
                    entry = entry2;
                    break;
                }
            }
        }
        if (entry == null) {
            Locale locale = Locale.US;
            throw new ServiceSpecificException(24, AccountManagerService$$ExternalSyntheticOutline0.m(callingUid, "Application uid=", " does not have pending session '", str, "'"));
        }
        try {
            try {
                return recoverableKeyStoreManager.importKeyMaterials(callingUserId, callingUid, RecoverableKeyStoreManager.recoverApplicationKeys(RecoverableKeyStoreManager.decryptRecoveryKey(entry, bArr), list));
            } catch (KeyStoreException e) {
                throw new ServiceSpecificException(22, e.getMessage());
            }
        } finally {
            entry.destroy();
            recoverySessionStorage.remove(callingUid);
        }
    }

    public final boolean refreshStoredPinLength(int i) {
        byte[] readSyntheticPasswordState;
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "LockSettingsHave");
        synchronized (this.mSpManager) {
            try {
                PasswordMetrics userPasswordMetrics = getUserPasswordMetrics(i);
                boolean z = false;
                if (userPasswordMetrics == null) {
                    Log.w("LockSettingsService", "PasswordMetrics is not available");
                    return false;
                }
                long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
                SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
                if (syntheticPasswordManager.isAutoPinConfirmationFeatureAvailable() && (readSyntheticPasswordState = syntheticPasswordManager.mStorage.readSyntheticPasswordState(i, "pwd", currentLskfBasedProtectorId)) != null) {
                    SyntheticPasswordManager.PasswordData fromBytes = SyntheticPasswordManager.PasswordData.fromBytes(readSyntheticPasswordState);
                    int i2 = userPasswordMetrics.length;
                    if (userPasswordMetrics.credType != 3 || !syntheticPasswordManager.mStorage.isAutoPinConfirmSettingEnabled(i) || i2 < 6) {
                        i2 = -1;
                    }
                    if (fromBytes.pinLength != i2) {
                        fromBytes.pinLength = i2;
                        syntheticPasswordManager.saveState("pwd", fromBytes.toBytes(), currentLskfBasedProtectorId, i);
                        syntheticPasswordManager.syncState(i);
                    }
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    public final void registerRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) {
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
        try {
            this.mRemoteCallback.sendResult(SystemUpdateManagerService$$ExternalSyntheticOutline0.m(checkRemoteLockPassword, KnoxCustomManagerService.SPCM_KEY_RESULT));
        } catch (RemoteException e2) {
            Log.d("LockSettingsService", "failed sendResult callback!! after register");
            e2.printStackTrace();
        }
        this.mRemoteCallback = null;
        this.mPassword = null;
    }

    public final void registerStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
        checkPasswordReadPermission();
        this.mStrongAuth.mHandler.obtainMessage(2, iStrongAuthTracker).sendToTarget();
    }

    public final boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mSpManager.mListeners.register(iWeakEscrowTokenRemovedListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void removeCachedUnifiedChallenge(int i) {
        checkWritePermission();
        this.mUnifiedProfilePasswordCache.removePassword(i);
    }

    public final boolean removeEscrowToken(long j, int i) {
        synchronized (this.mSpManager) {
            try {
                if (j == getCurrentLskfBasedProtectorId(i)) {
                    Slog.w("LockSettingsService", "Escrow token handle equals LSKF-based protector ID");
                    return false;
                }
                SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
                if (syntheticPasswordManager.tokenMap.containsKey(Integer.valueOf(i)) && ((ArrayMap) syntheticPasswordManager.tokenMap.get(Integer.valueOf(i))).remove(Long.valueOf(j)) != null) {
                    return true;
                }
                if (!this.mSpManager.hasState(i, "spblob", j)) {
                    return false;
                }
                this.mSpManager.destroyTokenBasedProtector(i, j);
                if (isDualDarAuthUserId(i)) {
                    Slog.d("LockSettingsService", "Clearing activated reset pwd token for DualDAR user: " + i);
                    DualDARController.getInstance(this.mContext).clearResetPasswordToken(this.mDualDarAuthUtils.getMainUserId(i), j);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeGatekeeperPasswordHandle(long j) {
        checkPasswordReadPermission();
        synchronized (this.mGatekeeperPasswords) {
            this.mGatekeeperPasswords.remove(j);
        }
    }

    public final void removeKey(String str) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        Objects.requireNonNull(str, "alias is null");
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        RecoverableKeyStoreDb recoverableKeyStoreDb = recoverableKeyStoreManager.mDatabase;
        if (recoverableKeyStoreDb.mKeyStoreDbHelper.getWritableDatabase().delete("keys", "uid = ? AND alias = ?", new String[]{Integer.toString(callingUid), str}) > 0) {
            recoverableKeyStoreDb.setShouldCreateSnapshot(callingUserId, callingUid);
            recoverableKeyStoreManager.mApplicationKeyStorage.deleteEntry(callingUserId, callingUid, str);
        }
    }

    public final void removeKeystoreProfileKey(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        String m = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "profile_key_name_encrypt_");
        String m2 = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "profile_key_name_decrypt_");
        try {
            if (!this.mKeyStore.containsAlias(m) && !this.mKeyStore.containsAlias(m2)) {
                return;
            }
            Slogf.i("LockSettingsService", "Removing keystore profile key for user %d", Integer.valueOf(i));
            this.mKeyStore.deleteEntry(m);
            this.mKeyStore.deleteEntry(m2);
        } catch (KeyStoreException e) {
            Slogf.e("LockSettingsService", e, "Error removing keystore profile key for user %d", Integer.valueOf(i));
        }
    }

    public final void removeStateForReusedUserIdIfNecessary(int i, int i2) {
        int i3;
        if (i == 0 || (i3 = this.mStorage.getInt(-1, i, "serial-number")) == i2) {
            return;
        }
        if (i3 != -1) {
            Slogf.i("LockSettingsService", "Removing stale state for reused userId %d (serial %d => %d)", Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(i2));
            removeUserState(i);
        }
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        lockSettingsStorage.setString("serial-number", Integer.toString(i2), i);
    }

    public final void removeUserState(int i) {
        this.mHandler.post(new LockSettingsService$$ExternalSyntheticLambda10(i, 0, this));
        this.mSpManager.removeUser(getGateKeeperService(), i);
        this.mStrongAuth.mHandler.obtainMessage(4, i, 0).sendToTarget();
        AndroidKeyStoreMaintenance.onUserRemoved(i);
        this.mUnifiedProfilePasswordCache.removePassword(i);
        gateKeeperClearSecureUserId(i);
        removeKeystoreProfileKey(i);
        this.mStorage.removeUser(i);
    }

    public final boolean removeWeakEscrowToken(long j, int i) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return removeEscrowToken(j, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void reportSuccessfulBiometricUnlock(boolean z, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC", "LockSettingsBiometric");
        LockSettingsStrongAuth lockSettingsStrongAuth = this.mStrongAuth;
        if (LockSettingsStrongAuth.DEBUG) {
            lockSettingsStrongAuth.getClass();
            Slog.d("LockSettingsStrongAuth", "reportSuccessfulBiometricUnlock for isStrongBiometric=" + z + ", userId=" + i);
        }
        if (z) {
            lockSettingsStrongAuth.mHandler.obtainMessage(8, i, 0).sendToTarget();
        } else {
            lockSettingsStrongAuth.mHandler.obtainMessage(7, i, 0).sendToTarget();
        }
    }

    public final void requestRemoteLockInfo(int i) {
        checkPasswordReadPermission();
        boolean z = false;
        int i2 = 0;
        while (i2 < 4) {
            Bundle bundle = new Bundle();
            boolean z2 = getBoolean(i2 + "locked", z, i);
            if (z2) {
                String string = getString(i2 + "message", null, i);
                String string2 = getString(i2 + "phonenumber", null, i);
                boolean z3 = getBoolean(i2 + "enableemergencycall", z, i);
                String string3 = getString(i2 + "clientname", null, i);
                String string4 = getString(i2 + "emailaddress", null, i);
                int i3 = (int) getLong(NandswapManager$$ExternalSyntheticOutline0.m(i2, "allowfailcount"), 0L, i);
                long j = getLong(NandswapManager$$ExternalSyntheticOutline0.m(i2, "locktime"), 0L, i);
                int i4 = (int) getLong(NandswapManager$$ExternalSyntheticOutline0.m(i2, "permanentblockcount"), 0L, i);
                boolean z4 = getBoolean(i2 + "skippin", z, i);
                boolean z5 = getBoolean(i2 + "skipsupport", false, i);
                bundle.putCharSequence("customer_app_name", getString(i2 + "appname", null, i));
                bundle.putCharSequence("customer_package_name", getString(i2 + "packagename", null, i));
                RemoteLockInfo build = new RemoteLockInfo.Builder(i2, z2).setMessage(string).setPhoneNumber(string2).setEnableEmergencyCall(z3).setClientName(string3).setEmailAddress(string4).setAllowFailCount(i3).setLockTimeOut(j).setBlockCount(i4).setSkipPinContainer(z4).setSkipSupportContainer(z5).setBundle(bundle).build();
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
            }
            i2++;
            z = false;
        }
    }

    public final void requireStrongAuth(int i, int i2) {
        checkWritePermission();
        this.mStrongAuth.requireStrongAuth(i, i2);
    }

    public final void resetKeyStore(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        checkWritePermission();
        Slogf.d("LockSettingsService", "Resetting keystore for user %d", Integer.valueOf(i));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
            if (isCredentialSharableWithParent(userInfo.id) && !getSeparateProfileChallengeEnabledInternal(userInfo.id)) {
                LockSettingsStorage lockSettingsStorage = this.mStorage;
                if (lockSettingsStorage.hasFile(lockSettingsStorage.getChildProfileLockFile(userInfo.id))) {
                    try {
                        arrayList2.add(getDecryptedPasswordForTiedProfile(userInfo.id));
                        arrayList.add(Integer.valueOf(userInfo.id));
                    } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                        Slog.e("LockSettingsService", "Failed to decrypt child profile key", e);
                    }
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
        } catch (Throwable th) {
            while (i2 < arrayList.size()) {
                int intValue2 = ((Integer) arrayList.get(i2)).intValue();
                LockscreenCredential lockscreenCredential2 = (LockscreenCredential) arrayList2.get(i2);
                if (intValue2 != -1 && lockscreenCredential2 != null) {
                    tieProfileLockToParent(intValue2, i, lockscreenCredential2);
                }
                if (lockscreenCredential2 != null) {
                    lockscreenCredential2.zeroize();
                }
                i2++;
            }
            throw th;
        }
    }

    public final void scheduleNonStrongBiometricIdleTimeout(int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC", "LockSettingsBiometric");
        LockSettingsStrongAuth lockSettingsStrongAuth = this.mStrongAuth;
        if (LockSettingsStrongAuth.DEBUG) {
            lockSettingsStrongAuth.getClass();
            Slog.d("LockSettingsStrongAuth", "scheduleNonStrongBiometricIdleTimeout for userId=" + i);
        }
        lockSettingsStrongAuth.mHandler.obtainMessage(9, i, 0).sendToTarget();
    }

    public void sendBroadcast(Intent intent, UserHandle userHandle, String str) {
        this.mContext.sendBroadcastAsUser(intent, userHandle, str, null);
    }

    public final void sendCredentialsOnChangeIfRequired(int i, boolean z, LockscreenCredential lockscreenCredential) {
        byte[] bArr;
        ScheduledExecutorService scheduledExecutorService;
        Context context;
        RecoverableKeyStoreDb recoverableKeyStoreDb;
        if (z) {
            return;
        }
        byte[] credential = lockscreenCredential.isNone() ? null : lockscreenCredential.getCredential();
        Iterator it = ((ArraySet) getProfilesWithSameLockScreen(i)).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
            int type = lockscreenCredential.getType();
            recoverableKeyStoreManager.getClass();
            try {
                scheduledExecutorService = recoverableKeyStoreManager.mExecutorService;
                context = recoverableKeyStoreManager.mContext;
                recoverableKeyStoreDb = recoverableKeyStoreManager.mDatabase;
                bArr = credential;
            } catch (InsecureUserException e) {
                e = e;
                bArr = credential;
            } catch (KeyStoreException e2) {
                e = e2;
                bArr = credential;
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                bArr = credential;
            }
            try {
                scheduledExecutorService.schedule(new KeySyncTask(recoverableKeyStoreDb, recoverableKeyStoreManager.mSnapshotStorage, recoverableKeyStoreManager.mListenersStorage, intValue, type, credential, true, PlatformKeyManager.getInstance(context, recoverableKeyStoreDb), new TestOnlyInsecureCertificateHelper(), new Scrypt()), 2000L, TimeUnit.MILLISECONDS);
            } catch (InsecureUserException e4) {
                e = e4;
                Log.e("RecoverableKeyStoreMgr", "InsecureUserException during lock screen secret update", e);
                credential = bArr;
            } catch (KeyStoreException e5) {
                e = e5;
                Log.e("RecoverableKeyStoreMgr", "Key store error encountered during recoverable key sync", e);
                credential = bArr;
            } catch (NoSuchAlgorithmException e6) {
                e = e6;
                Log.wtf("RecoverableKeyStoreMgr", "Should never happen - algorithm unavailable for KeySync", e);
                credential = bArr;
            }
            credential = bArr;
        }
    }

    public final void sendCredentialsOnUnlockIfRequired(LockscreenCredential lockscreenCredential, int i) {
        if (UserManager.isVirtualUserId(i) || LockPatternUtils.isSpecialUserId(i) || lockscreenCredential.isNone() || isProfileWithUnifiedLock(i)) {
            return;
        }
        Iterator it = ((ArraySet) getProfilesWithSameLockScreen(i)).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
            int type = lockscreenCredential.getType();
            byte[] credential = lockscreenCredential.getCredential();
            recoverableKeyStoreManager.getClass();
            try {
                ScheduledExecutorService scheduledExecutorService = recoverableKeyStoreManager.mExecutorService;
                Context context = recoverableKeyStoreManager.mContext;
                RecoverableKeyStoreDb recoverableKeyStoreDb = recoverableKeyStoreManager.mDatabase;
                scheduledExecutorService.schedule(new KeySyncTask(recoverableKeyStoreDb, recoverableKeyStoreManager.mSnapshotStorage, recoverableKeyStoreManager.mListenersStorage, intValue, type, credential, false, PlatformKeyManager.getInstance(context, recoverableKeyStoreDb), new TestOnlyInsecureCertificateHelper(), new Scrypt()), 2000L, TimeUnit.MILLISECONDS);
            } catch (InsecureUserException e) {
                Log.wtf("RecoverableKeyStoreMgr", "Impossible - insecure user, but user just entered lock screen", e);
            } catch (KeyStoreException e2) {
                Log.e("RecoverableKeyStoreMgr", "Key store error encountered during recoverable key sync", e2);
            } catch (NoSuchAlgorithmException e3) {
                Log.wtf("RecoverableKeyStoreMgr", "Should never happen - algorithm unavailable for KeySync", e3);
            }
        }
    }

    public final void sendLockTypeChangedInfo(int i) {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("sendLockTypeChangedInfo requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        this.mStorage.sendLockTypeChangedInfo(i);
    }

    public final void setAppLockBackupPin(String str, int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(StandardCharsets.UTF_8));
        lockSettingsStorage.getClass();
        lockSettingsStorage.writeFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockbackuppin.key"), passwordToHash, true);
    }

    public final void setAppLockFingerprintPassword(String str, int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(StandardCharsets.UTF_8));
        lockSettingsStorage.getClass();
        lockSettingsStorage.writeFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockfingerprintpassword.key"), passwordToHash, true);
    }

    public final void setAppLockPassword(String str, int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(StandardCharsets.UTF_8));
        lockSettingsStorage.getClass();
        lockSettingsStorage.writeFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpassword.key"), passwordToHash, true);
    }

    public final void setAppLockPattern(String str, int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] patternToByteArray = LockPatternUtils.patternToByteArray(LockPatternUtils.byteArrayToPattern(str.getBytes(StandardCharsets.UTF_8)));
        lockSettingsStorage.getClass();
        lockSettingsStorage.writeFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpattern.key"), patternToByteArray, true);
    }

    public final void setAppLockPin(String str, int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] passwordToHash = passwordToHash(i, str.getBytes(StandardCharsets.UTF_8));
        lockSettingsStorage.getClass();
        lockSettingsStorage.writeFile(LockSettingsStorage.getLockCredentialFileForUser(i, "applockpin.key"), passwordToHash, true);
    }

    public final void setBackupLskfBasedProtectorId(int i, long j) {
        Slog.w("LockSettingsService", String.format("!@ setBackupLskfBasedProtectorId : %016x", Long.valueOf(j)));
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
        LsLog.restore(String.format("LockSettingsDB, setBackupLskfBasedProtectorId User %d [%016x]", Integer.valueOf(i), Long.valueOf(j)));
        setLong("locksettings_db_backup", System.currentTimeMillis(), 0);
        if (i != 0) {
            Slog.w("LockSettingsService", "SPblobBNR, Backups are only supported for system users");
        } else {
            this.mHandler.removeCallbacks(this.mSpBackup);
            this.mHandler.postDelayed(this.mSpBackup, 500L);
        }
    }

    public final void setBoolean(String str, boolean z, int i) {
        checkWritePermission();
        Objects.requireNonNull(str);
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        lockSettingsStorage.setString(str, z ? "1" : "0", i);
    }

    public final void setCarrierLockEnabled(int i) {
    }

    public final void setCeStorageProtection(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        syntheticPassword.getClass();
        byte[] deriveSubkey = syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_FBE_KEY);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (this.mDualDarLockSettings.isDualDARUser(i)) {
                    if (!DualDarManager.isOnDeviceOwner(i)) {
                        Log.d("LockSettingsService", "need to get secret for DualDAR user");
                        Injector.AnonymousClass1 anonymousClass1 = this.mDualDarLockSettings;
                        anonymousClass1.getClass();
                        Slog.d("LockSettingsService", "getChangedStorageSecretIfDualDAR");
                        Slog.d("LockSettingsService", "fetchOuterLayerKey()");
                        byte[] fetchOuterLayerKey = DualDARController.getInstance(((LockSettingsService) anonymousClass1.val$storage).mContext).fetchOuterLayerKey(i);
                        Slog.e("LockSettingsService", "fetchOuterLayerKey Finished");
                        this.mStorageManager.setCeStorageProtection(i, fetchOuterLayerKey);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                    Log.d("LockSettingsService", "Do not substitute outer-layer-key in case of DualDAR on DO!");
                }
                this.mStorageManager.setCeStorageProtection(i, deriveSubkey);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (RemoteException e) {
                throw new IllegalStateException("Failed to protect CE key for user " + i, e);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setCurrentLskfBasedProtectorId(int i, long j) {
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        setLong("sp-handle", j, i);
        setLong("prev-sp-handle", currentLskfBasedProtectorId, i);
        setLong("sp-handle-ts", System.currentTimeMillis(), i);
        LsLog.restore(String.format("LockSettingsDB, setCurrentLskfBasedProtectorId User %d [%016x]", Integer.valueOf(i), Long.valueOf(j)));
        setLong("locksettings_db_backup", System.currentTimeMillis(), 0);
        if (i != 0) {
            Slog.w("LockSettingsService", "SPblobBNR, Backups are only supported for system users");
        } else {
            this.mHandler.removeCallbacks(this.mSpBackup);
            this.mHandler.postDelayed(this.mSpBackup, 500L);
        }
    }

    public void setKeystorePassword(byte[] bArr, int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        LsLog.enroll("setKeystore, user " + i + ", ret = " + AndroidKeyStoreMaintenance.onUserPasswordChanged(i, bArr));
    }

    public final boolean setKnoxGuard(int i, RemoteLockInfo remoteLockInfo) {
        checkWritePermission();
        try {
            return applyRemoteLock(i, remoteLockInfo);
        } catch (RemoteException unused) {
            Log.d("LockSettingsService", "failed applyRemoteLock!");
            return false;
        }
    }

    public final void setLockCarrierPassword(byte[] bArr, int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] passwordToHash = passwordToHash(i, bArr);
        lockSettingsStorage.getClass();
        lockSettingsStorage.writeFile(LockSettingsStorage.getLockCredentialFileForUser(i, "sktpassword.key"), passwordToHash, true);
    }

    public final boolean setLockCredential(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i) {
        return setLockCredentialWithIgnoreNotifyIfNeeded(lockscreenCredential, lockscreenCredential2, i, false);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setLockCredentialInternal(com.android.internal.widget.LockscreenCredential r25, com.android.internal.widget.LockscreenCredential r26, int r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 725
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.locksettings.LockSettingsService.setLockCredentialInternal(com.android.internal.widget.LockscreenCredential, com.android.internal.widget.LockscreenCredential, int, boolean):boolean");
    }

    public final boolean setLockCredentialWithIgnoreNotifyIfNeeded(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, int i, boolean z) {
        boolean z2;
        if (!this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
            throw new UnsupportedOperationException("This operation requires secure lock screen feature");
        }
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS") && (!hasPermission("android.permission.SET_INITIAL_LOCK") || !lockscreenCredential2.isNone())) {
            throw new SecurityException("setLockCredential requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        lockscreenCredential.validateBasicRequirements();
        if (i == -9899) {
            i = 0;
            z2 = true;
        } else {
            z2 = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            enforceFrpNotActive();
            if (!lockscreenCredential2.isNone() && isProfileWithUnifiedLock(i)) {
                verifyCredential(lockscreenCredential2, this.mUserManager.getProfileParent(i).id, 0);
                lockscreenCredential2.zeroize();
                lockscreenCredential2 = LockscreenCredential.createNone();
            }
            boolean isSdpNotSupportedSecureFolder = this.mUserManager.getUserInfo(i).isSdpNotSupportedSecureFolder();
            if (isSdpNotSupportedSecureFolder) {
                SDPLog.d(null, String.format("User %d identified as sdp not-supported secure folder user", Integer.valueOf(i)));
            }
            if (isEnterpriseUserId(i)) {
                lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
                lockscreenCredential2 = StreamCipher.decryptStream(lockscreenCredential2);
            }
            if (SemPersonaManager.isSecureFolderId(i) || isSdpNotSupportedSecureFolder) {
                SDPLog.d(null, String.format("SEP-Lite User %d identified as SecureFolder user", Integer.valueOf(i)));
                if (this.mDarLockSettings.setSecureFolderLockCredential(lockscreenCredential, lockscreenCredential2, i).getResponseCode() != 1) {
                    SDPLog.d(null, "sdp not supported : setSecureFolderLockCredential success");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            synchronized (this.mSeparateChallengeLock) {
                if (!setLockCredentialInternal(lockscreenCredential, lockscreenCredential2, z2 ? -9899 : i, false)) {
                    scheduleGc();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                setSeparateProfileChallengeEnabledLocked(i, true, null);
                if (!z || !isEnterpriseUserId(i)) {
                    notifyPasswordChanged(lockscreenCredential, i);
                }
                if (isCredentialSharableWithParent(i)) {
                    ((TrustManager) this.mContext.getSystemService(TrustManager.class)).setDeviceLockedForUser(i, false);
                }
                notifySeparateProfileChallengeChanged(i);
                onPostPasswordChanged(lockscreenCredential, i);
                if (isEnterpriseUserId(i)) {
                    lockscreenCredential.zeroize();
                    lockscreenCredential2.zeroize();
                }
                LsLog.setFailureCount(i, 0);
                LsLog.enroll(SPBnRManager.getPWFilelist(i), true);
                scheduleGc();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final long setLockCredentialWithSpLocked(LockscreenCredential lockscreenCredential, LockscreenCredential lockscreenCredential2, long j, SyntheticPasswordManager.SyntheticPassword syntheticPassword, int i) {
        long j2;
        ArrayMap arrayMap;
        String str;
        Slogf.i("LockSettingsService", "Changing lockscreen credential of user %d; newCredentialType=%s\n", Integer.valueOf(i), LockPatternUtils.credentialTypeToString(lockscreenCredential.getType()));
        int credentialTypeInternal = getCredentialTypeInternal(i);
        long currentLskfBasedProtectorId = getCurrentLskfBasedProtectorId(i);
        long createLskfBasedProtector = this.mSpManager.createLskfBasedProtector(getGateKeeperService(), lockscreenCredential2, j, lockscreenCredential, syntheticPassword, i);
        if (lockscreenCredential.isNone()) {
            j2 = currentLskfBasedProtectorId;
            if (UserManager.isVirtualUserId(i) || isCredentialSharableWithParent(i)) {
                arrayMap = null;
            } else {
                arrayMap = new ArrayMap();
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
            }
            this.mSpManager.destroyState(i, "handle", 0L);
            SPBnRManager.deleteBackup(i, 0L, "handle");
            gateKeeperClearSecureUserId(i);
            unlockCeStorage(i, syntheticPassword);
            unlockKeystore(i, syntheticPassword);
            if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                AndroidKeyStoreMaintenance.onUserLskfRemoved(i);
                str = null;
            } else {
                str = null;
                setKeystorePassword(null, i);
            }
            this.mHandler.post(new LockSettingsService$$ExternalSyntheticLambda10(i, 0, this));
        } else {
            if (this.mSpManager.hasState(i, "handle", 0L)) {
                j2 = currentLskfBasedProtectorId;
            } else {
                SyntheticPasswordManager syntheticPasswordManager = this.mSpManager;
                IGateKeeperService gateKeeperService = getGateKeeperService();
                syntheticPasswordManager.getClass();
                try {
                    GateKeeperResponse enroll = gateKeeperService.enroll(i, (byte[]) null, (byte[]) null, syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_SP_GK_AUTH));
                    if (enroll.getResponseCode() != 0) {
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Fail to create new SID for user ", " response: ");
                        m.append(enroll.getResponseCode());
                        throw new IllegalStateException(m.toString());
                    }
                    j2 = currentLskfBasedProtectorId;
                    syntheticPasswordManager.saveState("handle", enroll.getPayload(), 0L, i);
                    syntheticPasswordManager.syncState(i);
                    this.mSpManager.verifyChallenge(getGateKeeperService(), syntheticPassword, i);
                    if (!FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                        setKeystorePassword(SyntheticPasswordManager.bytesToHex(syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_KEY_STORE_PASSWORD)), i);
                    }
                } catch (RemoteException e2) {
                    throw new IllegalStateException("Failed to create new SID for user", e2);
                }
            }
            str = null;
            arrayMap = null;
        }
        setCurrentLskfBasedProtectorId(i, createLskfBasedProtector);
        LockPatternUtils.invalidateCredentialTypeCache();
        if (!UserManager.isVirtualUserId(i) && !isCredentialSharableWithParent(i)) {
            boolean isUserSecure = isUserSecure(i);
            List profiles2 = this.mUserManager.getProfiles(i);
            int size2 = profiles2.size();
            SDPLog.i("Synchronize challenge along with user " + i);
            SDPLog.d(str, String.format("Feasible for profiles(%d) ? %b", Integer.valueOf(size2), Boolean.valueOf(isUserSecure)));
            for (int i4 = 0; i4 < size2; i4++) {
                int i5 = ((UserInfo) profiles2.get(i4)).id;
                if (isCredentialSharableWithParent(i5) && !getSeparateProfileChallengeEnabledInternal(i5)) {
                    if (isUserSecure) {
                        tieProfileLockIfNecessary(LockscreenCredential.createNone(), i5);
                    } else if (arrayMap == null || !arrayMap.containsKey(Integer.valueOf(i5))) {
                        SDPLog.d(str, "Profile(" + i5 + ") credential not found... Clear profile credential.");
                        Slog.wtf("LockSettingsService", "Attempt to clear tied challenge, but no password supplied.");
                    } else {
                        SDPLog.d(str, "Profile(" + i5 + ") credential found! Clear profile credential.");
                        setLockCredentialInternal(LockscreenCredential.createNone(), (LockscreenCredential) arrayMap.get(Integer.valueOf(i5)), i5, true);
                        LockSettingsStorage lockSettingsStorage = this.mStorage;
                        lockSettingsStorage.deleteFile(lockSettingsStorage.getChildProfileLockFile(i5));
                        removeKeystoreProfileKey(i5);
                    }
                }
            }
        }
        setUserPasswordMetrics(lockscreenCredential, i);
        this.mUnifiedProfilePasswordCache.removePassword(i);
        if (credentialTypeInternal != -1) {
            SyntheticPasswordManager syntheticPasswordManager2 = this.mSpManager;
            LockSettingsStorage lockSettingsStorage2 = syntheticPasswordManager2.mStorage;
            Iterator it = ((ArrayList) lockSettingsStorage2.listSyntheticPasswordProtectorsForUser(i, "spblob")).iterator();
            while (it.hasNext()) {
                long longValue = ((Long) it.next()).longValue();
                if (SyntheticPasswordManager.SyntheticPasswordBlob.fromBytes(lockSettingsStorage2.readSyntheticPasswordState(i, "spblob", longValue)).mProtectorType == 2) {
                    syntheticPasswordManager2.destroyTokenBasedProtector(i, longValue);
                }
            }
        }
        if (arrayMap != null) {
            Iterator it2 = arrayMap.entrySet().iterator();
            while (it2.hasNext()) {
                ((LockscreenCredential) ((Map.Entry) it2.next()).getValue()).zeroize();
            }
        }
        if (isEnablePrevCredential() && credentialTypeInternal != -1 && lockscreenCredential.getType() != -1 && i == 0 && getBackupLskfBasedProtectorId(i) == 0) {
            setBackupLskfBasedProtectorId(i, j2);
        } else {
            this.mSpManager.destroyLskfBasedProtector(i, j2);
        }
        Slogf.i("LockSettingsService", "Successfully changed lockscreen credential of user %d", Integer.valueOf(i));
        return createLskfBasedProtector;
    }

    public final boolean setLockCredentialWithTokenInternalLocked(LockscreenCredential lockscreenCredential, long j, byte[] bArr, int i) {
        Slogf.i("LockSettingsService", "Resetting lockscreen credential of user %d using escrow token %016x", Integer.valueOf(i), Long.valueOf(j));
        if (isDualDarAuthUserId(i)) {
            try {
                if (!this.mDualDarLockSettings.setLockCredentialWithTokenInternalForDualDAR(lockscreenCredential, j, bArr, i)) {
                    Log.e("LockSettingsService", "Dual DAR Client failed to reset password with token for user: " + i);
                    return false;
                }
            } catch (RemoteException e) {
                DDLog.e("LockSettingsService", "Exception : " + e.getMessage(), new Object[0]);
            }
        }
        SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(i, j, getGateKeeperService(), bArr);
        if (unlockTokenBasedProtector.syntheticPassword == null) {
            Slog.w("LockSettingsService", "Invalid escrow token supplied");
            return false;
        }
        if (unlockTokenBasedProtector.gkResponse.getResponseCode() != 0) {
            Slog.e("LockSettingsService", "Obsolete token: synthetic password decrypted but it fails GK verification.");
            return false;
        }
        onSyntheticPasswordKnown(i, unlockTokenBasedProtector.syntheticPassword, false);
        setLockCredentialWithSpLocked(lockscreenCredential, null, 0L, unlockTokenBasedProtector.syntheticPassword, i);
        return true;
    }

    public final void setLockFMMPassword(byte[] bArr, int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] passwordToHash = passwordToHash(i, bArr);
        if (passwordToHash != null) {
            lockSettingsStorage.getClass();
            if (passwordToHash.length != 0) {
                lockSettingsStorage.writeFile(LockSettingsStorage.getLockCredentialFileForUser(i, "fmmpassword.key"), passwordToHash, true);
                lockSettingsStorage.sendLockTypeChangedInfo(4);
            }
        }
        lockSettingsStorage.getClass();
        lockSettingsStorage.deleteFile(LockSettingsStorage.getLockCredentialFileForUser(i, "fmmpassword.key"));
        lockSettingsStorage.sendLockTypeChangedInfo(4);
    }

    public final void setLockModeChangedCallback(IRemoteCallback iRemoteCallback) {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("setLockModeChangedCallback requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        this.mStorage.mLockTypeCallback = iRemoteCallback;
    }

    public final void setLong(String str, long j, int i) {
        checkWritePermission();
        Objects.requireNonNull(str);
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        lockSettingsStorage.setString(str, Long.toString(j), i);
        if (i == 0 || !SemPersonaManager.isKnoxId(i) || SemPersonaManager.isSecureFolderId(i) || !TextUtils.equals("lockscreen.samsung_biometric", str)) {
            return;
        }
        this.mInjector.getPersonaService().ifPresent(new LockSettingsService$$ExternalSyntheticLambda11(i, 0));
    }

    public final void setRecoverySecretTypes(int[] iArr) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        Objects.requireNonNull(iArr, "secretTypes is null");
        int callingUserId = UserHandle.getCallingUserId();
        int callingUid = Binder.getCallingUid();
        RecoverableKeyStoreDb recoverableKeyStoreDb = recoverableKeyStoreManager.mDatabase;
        int[] recoverySecretTypes = recoverableKeyStoreDb.getRecoverySecretTypes(callingUserId, callingUid);
        if (Arrays.equals(iArr, recoverySecretTypes)) {
            Log.v("RecoverableKeyStoreMgr", "Not updating secret types - same as old value.");
            return;
        }
        SQLiteDatabase writableDatabase = recoverableKeyStoreDb.mKeyStoreDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        final StringJoiner stringJoiner = new StringJoiner(",");
        Arrays.stream(iArr).forEach(new IntConsumer() { // from class: com.android.server.locksettings.recoverablekeystore.storage.RecoverableKeyStoreDb$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                stringJoiner.add(Integer.toString(i));
            }
        });
        contentValues.put("secret_types", stringJoiner.toString());
        recoverableKeyStoreDb.ensureRecoveryServiceMetadataEntryExists(callingUserId, callingUid);
        if (writableDatabase.update("recovery_service_metadata", contentValues, "user_id = ? AND uid = ?", new String[]{String.valueOf(callingUserId), String.valueOf(callingUid)}) < 0) {
            throw new ServiceSpecificException(22, "Database error trying to set secret types.");
        }
        if (recoverySecretTypes.length == 0) {
            Log.i("RecoverableKeyStoreMgr", "Initialized secret types.");
            return;
        }
        Log.i("RecoverableKeyStoreMgr", "Updated secret types. Snapshot pending.");
        if (recoverableKeyStoreDb.getLong(callingUserId, callingUid, "snapshot_version") == null) {
            Log.i("RecoverableKeyStoreMgr", "Updated secret types. Snapshot didn't exist");
        } else {
            recoverableKeyStoreDb.setShouldCreateSnapshot(callingUserId, callingUid);
            Log.i("RecoverableKeyStoreMgr", "Updated secret types. Snapshot must be updated");
        }
    }

    public final void setRecoveryStatus(String str, int i) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        Objects.requireNonNull(str, "alias is null");
        int callingUid = Binder.getCallingUid();
        SQLiteDatabase writableDatabase = recoverableKeyStoreManager.mDatabase.mKeyStoreDbHelper.getWritableDatabase();
        new ContentValues().put("recovery_status", Integer.valueOf(i));
        if (writableDatabase.update("keys", r1, "uid = ? AND alias = ?", new String[]{String.valueOf(callingUid), str}) < 0) {
            throw new ServiceSpecificException(22, "Failed to set the key recovery status in the local DB.");
        }
    }

    public final void setRemoteLock(int i, RemoteLockInfo remoteLockInfo) {
        checkWritePermission();
        try {
            applyRemoteLock(i, remoteLockInfo);
        } catch (RemoteException unused) {
            Log.d("LockSettingsService", "failed applyRemoteLock!");
        }
    }

    public final void setSecurityDebugLevel(int i) {
        Slog.d("LockSettingsService", "!@ setSecurityDebugLevel = " + i);
        LsLog.setSecurityDebugLevel(i);
        if (i >= 1) {
            LsLog.summary("lock state (0)");
            LsLog.summary(TextUtils.formatSimple("SP ID: %016x (prev: %016x)", new Object[]{Long.valueOf(getCurrentLskfBasedProtectorId(0)), Long.valueOf(getLong("prev-sp-handle", 0L, 0))}));
            LsLog.summary(TextUtils.formatSimple("LSKF last changed: %s ", new Object[]{timestampToString(getLong("sp-handle-ts", 0L, 0))}));
            long j = getLong("backup-protector-id", 0L, 0);
            if (j == 0) {
                LsLog.summary("N-1 : None");
            } else {
                LsLog.summary(TextUtils.formatSimple("N-1 ID: %016x (expire : %s)", new Object[]{Long.valueOf(j), timestampToString(getLong("backup-expiration-ts", 0L, 0))}));
            }
            StringBuilder sb = new StringBuilder("LockType: ");
            StringBuilder sb2 = new StringBuilder();
            int credentialTypeInternal = getCredentialTypeInternal(0);
            if (credentialTypeInternal != -1) {
                sb2.append(LockPatternUtils.credentialTypeToString(credentialTypeInternal));
                if (credentialTypeInternal == 1) {
                    String string = getString("lock_pattern_visible_pattern", null, 0);
                    if (string != null) {
                        sb2.append(":V=".concat(string));
                    }
                } else if (credentialTypeInternal == 3) {
                    if (this.mStorage.isAutoPinConfirmSettingEnabled(0)) {
                        sb2.append(":A=" + getPinLength(0));
                    } else {
                        sb2.append(":A=F");
                    }
                }
            } else if (getBoolean("lockscreen.disabled", false, 0)) {
                sb2.append("NONE");
            } else {
                sb2.append("SWIPE");
            }
            int i2 = (int) getLong("lockscreen.samsung_biometric", 0L, 0);
            if ((i2 & 1) != 0) {
                sb2.append(", Fingerprint");
            }
            if ((i2 & 256) != 0) {
                sb2.append(", Face");
            }
            try {
                if (haveFMMPassword(0)) {
                    sb2.append(", FMM");
                }
                if (getCarrierLock(0)) {
                    sb2.append(", CarrierLock");
                }
            } catch (RemoteException e) {
                ActivityManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("!@ RemoteException occurs! "), "LockSettingsService");
            }
            for (int i3 = 0; i3 < 4; i3++) {
                if (getBoolean(i3 + "locked", false, 0)) {
                    sb2.append(", ");
                    sb2.append(this.RemoteLockType[i3]);
                }
            }
            sb.append(sb2.toString());
            LsLog.summary(sb.toString());
            LsLog.summary("FailureCount: " + this.mInjector.getDevicePolicyManager().getCurrentFailedPasswordAttempts(0) + "/" + LsLog.getFailureCount(0));
            this.mHandler.removeCallbacks(this.mResetDebugLevel);
            this.mHandler.postDelayed(this.mResetDebugLevel, 60000L);
        }
    }

    public final void setSeparateProfileChallengeEnabled(int i, boolean z, LockscreenCredential lockscreenCredential) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        checkWritePermission();
        if (!this.mHasSecureLockScreen && lockscreenCredential != null && lockscreenCredential.getType() != -1) {
            throw new UnsupportedOperationException("This operation requires secure lock screen feature.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(z ? "Enable" : "Disable");
        sb.append(" separate challenge for user ");
        sb.append(i);
        SDPLog.i(sb.toString());
        SDPLog.p("userId", Integer.valueOf(i), "enabled", Boolean.valueOf(z), "profileUserPassword", lockscreenCredential);
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
        boolean z3 = !z;
        PasswordPolicy passwordPolicy = EnterpriseDeviceManager.getInstance().getPasswordPolicy();
        if (passwordPolicy != null) {
            passwordPolicy.notifyPasswordPolicyOneLockChanged(z3, i);
        }
        try {
            if (z) {
                LockSettingsStorage lockSettingsStorage = this.mStorage;
                lockSettingsStorage.deleteFile(lockSettingsStorage.getChildProfileLockFile(i));
                removeKeystoreProfileKey(i);
            } else {
                tieProfileLockIfNecessary(lockscreenCredential, i);
            }
        } catch (IllegalStateException e) {
            setBoolean("lockscreen.profilechallenge", z2, i);
            throw e;
        }
    }

    public final void setServerParams(byte[] bArr) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        int callingUserId = UserHandle.getCallingUserId();
        int callingUid = Binder.getCallingUid();
        RecoverableKeyStoreDb recoverableKeyStoreDb = recoverableKeyStoreManager.mDatabase;
        byte[] bytes = recoverableKeyStoreDb.getBytes(callingUserId, callingUid, "server_params");
        if (Arrays.equals(bArr, bytes)) {
            Log.v("RecoverableKeyStoreMgr", "Not updating server params - same as old value.");
            return;
        }
        SQLiteDatabase writableDatabase = recoverableKeyStoreDb.mKeyStoreDbHelper.getWritableDatabase();
        new ContentValues().put("server_params", bArr);
        String[] strArr = {Integer.toString(callingUserId), Integer.toString(callingUid)};
        recoverableKeyStoreDb.ensureRecoveryServiceMetadataEntryExists(callingUserId, callingUid);
        if (writableDatabase.update("recovery_service_metadata", r6, "user_id = ? AND uid = ?", strArr) < 0) {
            throw new ServiceSpecificException(22, "Database failure trying to set server params.");
        }
        if (bytes == null) {
            Log.i("RecoverableKeyStoreMgr", "Initialized server params.");
        } else if (recoverableKeyStoreDb.getLong(callingUserId, callingUid, "snapshot_version") == null) {
            Log.i("RecoverableKeyStoreMgr", "Updated server params. Snapshot didn't exist");
        } else {
            recoverableKeyStoreDb.setShouldCreateSnapshot(callingUserId, callingUid);
            Log.i("RecoverableKeyStoreMgr", "Updated server params. Snapshot must be updated");
        }
    }

    public final void setShellCommandCallback(IRemoteCallback iRemoteCallback) {
        checkPasswordReadPermission();
        this.mShellCommandCallback = iRemoteCallback;
    }

    public final void setSnapshotCreatedPendingIntent(PendingIntent pendingIntent) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        int callingUid = Binder.getCallingUid();
        RecoverySnapshotListenersStorage recoverySnapshotListenersStorage = recoverableKeyStoreManager.mListenersStorage;
        synchronized (recoverySnapshotListenersStorage) {
            Log.i("RecoverySnapshotLstnrs", "Registered listener for agent with uid " + callingUid);
            recoverySnapshotListenersStorage.mAgentIntents.put(callingUid, pendingIntent);
            if (recoverySnapshotListenersStorage.mAgentsWithPendingSnapshots.contains(Integer.valueOf(callingUid))) {
                Log.i("RecoverySnapshotLstnrs", "Snapshot already created for agent. Immediately triggering intent.");
                recoverySnapshotListenersStorage.tryToSendIntent(callingUid, pendingIntent);
            }
        }
    }

    public final void setString(String str, String str2, int i) {
        checkWritePermission();
        Objects.requireNonNull(str);
        this.mStorage.setString(str, str2, i);
    }

    public final void setUserPasswordMetrics(LockscreenCredential lockscreenCredential, int i) {
        synchronized (this) {
            this.mUserPasswordMetrics.put(i, PasswordMetrics.computeForCredential(lockscreenCredential));
        }
    }

    public final byte[] startRecoverySessionWithCertPath(String str, String str2, RecoveryCertPath recoveryCertPath, byte[] bArr, byte[] bArr2, List list) {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        recoverableKeyStoreManager.checkRecoverKeyStorePermission();
        recoverableKeyStoreManager.mTestCertHelper.getClass();
        String defaultCertificateAliasIfEmpty = TestOnlyInsecureCertificateHelper.getDefaultCertificateAliasIfEmpty(str2);
        Objects.requireNonNull(str, "invalid session");
        Objects.requireNonNull(recoveryCertPath, "verifierCertPath is null");
        Objects.requireNonNull(bArr, "vaultParams is null");
        Objects.requireNonNull(bArr2, "vaultChallenge is null");
        Objects.requireNonNull(list, "secrets is null");
        try {
            CertPath certPath = recoveryCertPath.getCertPath();
            try {
                CertUtils.validateCertPath(TestOnlyInsecureCertificateHelper.getValidationDate(defaultCertificateAliasIfEmpty), TestOnlyInsecureCertificateHelper.getRootCertificate(defaultCertificateAliasIfEmpty), certPath);
                byte[] encoded = certPath.getCertificates().get(0).getPublicKey().getEncoded();
                if (encoded != null) {
                    return recoverableKeyStoreManager.startRecoverySession(str, encoded, bArr, bArr2, list);
                }
                Log.e("RecoverableKeyStoreMgr", "Failed to encode verifierPublicKey");
                throw new ServiceSpecificException(25, "Failed to encode verifierPublicKey");
            } catch (CertValidationException e) {
                Log.e("RecoverableKeyStoreMgr", "Failed to validate the given cert path", e);
                throw new ServiceSpecificException(28, e.getMessage());
            }
        } catch (CertificateException e2) {
            throw new ServiceSpecificException(25, e2.getMessage());
        }
    }

    public final RemoteLockscreenValidationSession startRemoteLockscreenValidation() {
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        RemoteLockscreenValidationSessionStorage remoteLockscreenValidationSessionStorage = recoverableKeyStoreManager.mRemoteLockscreenValidationSessionStorage;
        if (remoteLockscreenValidationSessionStorage == null) {
            throw new UnsupportedOperationException("Under development");
        }
        recoverableKeyStoreManager.checkVerifyRemoteLockscreenPermission();
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int credentialType = getCredentialType(callingUserId);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int lockPatternUtilsToKeyguardType = RecoverableKeyStoreManager.lockPatternUtilsToKeyguardType(credentialType);
            return new RemoteLockscreenValidationSession.Builder().setLockType(lockPatternUtilsToKeyguardType).setRemainingAttempts(Math.max(5 - recoverableKeyStoreManager.mDatabase.getBadRemoteGuessCounter(callingUserId), 0)).setSourcePublicKey(SecureBox.encodePublicKey(remoteLockscreenValidationSessionStorage.startSession(callingUserId).mKeyPair.getPublic())).build();
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void systemReady() {
        IAuthSecret$Stub$Proxy iAuthSecret$Stub$Proxy;
        boolean migrateKeyNamespace;
        checkWritePermission();
        this.mHasSecureLockScreen = this.mContext.getPackageManager().hasSystemFeature("android.software.secure_lock_screen");
        Slog.w("LockSettingsService", "!@ migrateOldData");
        LsLog.prepare();
        LsLog.setFailureCount(0, this.mInjector.getDevicePolicyManager().getCurrentFailedPasswordAttempts(0));
        migrateLockSettingsDB();
        migrateSpblob();
        if (getString("migrated_keystore_namespace", null, 0) == null) {
            synchronized (this.mSpManager) {
                migrateKeyNamespace = this.mSpManager.migrateKeyNamespace();
            }
            List users = this.mUserManager.getUsers();
            int size = users.size();
            boolean z = true;
            for (int i = 0; i < size; i++) {
                UserInfo userInfo = (UserInfo) users.get(i);
                if (isCredentialSharableWithParent(userInfo.id) && !getSeparateProfileChallengeEnabledInternal(userInfo.id)) {
                    z = z & SyntheticPasswordCrypto.migrateLockSettingsKey("profile_key_name_encrypt_" + userInfo.id) & SyntheticPasswordCrypto.migrateLockSettingsKey("profile_key_name_decrypt_" + userInfo.id);
                }
            }
            if (migrateKeyNamespace && z) {
                setString("migrated_keystore_namespace", "true", 0);
                Slog.i("LockSettingsService", "Migrated keys to LSS namespace");
            } else {
                Slog.w("LockSettingsService", "Failed to migrate keys to LSS namespace");
            }
        }
        StringBuilder sb = new StringBuilder();
        String str = IAuthSecret.DESCRIPTOR;
        sb.append(str);
        sb.append("/default");
        IBinder waitForDeclaredService = ServiceManager.waitForDeclaredService(sb.toString());
        if (waitForDeclaredService == null) {
            iAuthSecret$Stub$Proxy = null;
        } else {
            IInterface queryLocalInterface = waitForDeclaredService.queryLocalInterface(str);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IAuthSecret)) {
                IAuthSecret$Stub$Proxy iAuthSecret$Stub$Proxy2 = new IAuthSecret$Stub$Proxy();
                iAuthSecret$Stub$Proxy2.mRemote = waitForDeclaredService;
                iAuthSecret$Stub$Proxy = iAuthSecret$Stub$Proxy2;
            } else {
                iAuthSecret$Stub$Proxy = (IAuthSecret) queryLocalInterface;
            }
        }
        this.mAuthSecretService = iAuthSecret$Stub$Proxy;
        if (iAuthSecret$Stub$Proxy != null) {
            Slog.i("LockSettingsService", "Device implements AIDL AuthSecret HAL");
        } else {
            try {
                this.mAuthSecretService = new AuthSecretHidlAdapter(IAuthSecret$Proxy.getService());
                Slog.i("LockSettingsService", "Device implements HIDL AuthSecret HAL");
            } catch (RemoteException e) {
                Slog.w("LockSettingsService", "Failed to get AuthSecret HAL(hidl)", e);
            } catch (NoSuchElementException unused) {
                Slog.i("LockSettingsService", "Device doesn't implement AuthSecret HAL");
            }
        }
        DeviceProvisionedObserver deviceProvisionedObserver = this.mDeviceProvisionedObserver;
        if (LockPatternUtils.frpCredentialEnabled(LockSettingsService.this.mContext)) {
            deviceProvisionedObserver.updateRegistration();
        } else if (!deviceProvisionedObserver.isProvisioned()) {
            Slog.i("LockSettingsService", "FRP credential disabled, reporting device setup complete to Gatekeeper immediately");
            try {
                LockSettingsService.this.getGateKeeperService().reportDeviceSetupComplete();
            } catch (RemoteException e2) {
                Slog.e("LockSettingsService", "Failure reporting to IGateKeeperService", e2);
            }
        }
        LockPatternUtils.invalidateCredentialTypeCache();
        this.mStorage.prefetchUser(0);
        BiometricDeferredQueue biometricDeferredQueue = this.mBiometricDeferredQueue;
        Injector injector = this.mInjector;
        FingerprintManager fingerprintManager = injector.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint") ? (FingerprintManager) injector.mContext.getSystemService("fingerprint") : null;
        Injector injector2 = this.mInjector;
        FaceManager faceManager = injector2.mContext.getPackageManager().hasSystemFeature("android.hardware.biometrics.face") ? (FaceManager) injector2.mContext.getSystemService("face") : null;
        BiometricManager biometricManager = (BiometricManager) this.mInjector.mContext.getSystemService("biometric");
        biometricDeferredQueue.mFingerprintManager = fingerprintManager;
        biometricDeferredQueue.mFaceManager = faceManager;
        biometricDeferredQueue.mBiometricManager = biometricManager;
        if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()) {
            this.mStorageManagerInternal.registerStorageLockEventListener(this.mCeStorageLockEventListener);
        }
        if (SystemProperties.getInt("ro.product.first_api_level", 0) < 34) {
            boolean z2 = getBoolean("migrated_mdfpp_pwd_data", false, 0);
            Log.d("LockSettingsService", "Need pwdData migration ? ".concat(z2 ? "No" : "YES"));
            if (!z2) {
                List users2 = this.mUserManager.getUsers();
                for (int i2 = 0; i2 < users2.size(); i2++) {
                    migrateMdfppPwdData(((UserInfo) users2.get(i2)).id);
                }
                for (int i3 : new VirtualLockUtils().getVirtualUsers()) {
                    migrateMdfppPwdData(i3);
                }
                setBoolean("migrated_mdfpp_pwd_data", true, 0);
            }
        }
    }

    public final void tieProfileLockIfNecessary(LockscreenCredential lockscreenCredential, int i) {
        UserInfo profileParent;
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        SDPLog.i("Tie lock if necessary for user " + i);
        SDPLog.p("profileUserId", Integer.valueOf(i), "profileUserPassword", lockscreenCredential);
        if (isCredentialSharableWithParent(i) && !getSeparateProfileChallengeEnabledInternal(i)) {
            LockSettingsStorage lockSettingsStorage = this.mStorage;
            if (lockSettingsStorage.hasFile(lockSettingsStorage.getChildProfileLockFile(i)) || this.mDualDarLockSettings.isDualDARUser(i) || (profileParent = this.mUserManager.getProfileParent(i)) == null) {
                return;
            }
            if (!isUserSecure(profileParent.id) && !lockscreenCredential.isNone()) {
                Slogf.i("LockSettingsService", "Clearing password for profile user %d to match parent", Integer.valueOf(i));
                setLockCredentialInternal(LockscreenCredential.createNone(), lockscreenCredential, i, true);
                return;
            }
            try {
                long secureUserId = getGateKeeperService().getSecureUserId(profileParent.id);
                if (secureUserId == 0) {
                    return;
                }
                byte[] randomBytes = SecureRandomUtils.randomBytes(40);
                char[] encode = HexEncoding.encode(randomBytes);
                byte[] bArr = new byte[encode.length];
                for (int i2 = 0; i2 < encode.length; i2++) {
                    bArr[i2] = (byte) encode[i2];
                }
                LockscreenCredential createUnifiedProfilePassword = LockscreenCredential.createUnifiedProfilePassword(bArr);
                Arrays.fill(encode, (char) 0);
                Arrays.fill(bArr, (byte) 0);
                Arrays.fill(randomBytes, (byte) 0);
                try {
                    setLockCredentialInternal(createUnifiedProfilePassword, lockscreenCredential, i, true);
                    tieProfileLockToParent(i, profileParent.id, createUnifiedProfilePassword);
                    this.mUnifiedProfilePasswordCache.storePassword(i, createUnifiedProfilePassword, secureUserId);
                    if (isProfileWithUnifiedLock(i)) {
                        doVerifyCredential(createUnifiedProfilePassword, i, null, 0);
                    }
                    createUnifiedProfilePassword.close();
                } catch (Throwable th) {
                    if (createUnifiedProfilePassword != null) {
                        try {
                            createUnifiedProfilePassword.close();
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
    }

    public void tieProfileLockToParent(int i, int i2, LockscreenCredential lockscreenCredential) {
        Slogf.i("LockSettingsService", "Tying lock for profile user %d to parent user %d", Integer.valueOf(i), Integer.valueOf(i2));
        try {
            getGateKeeperService().getSecureUserId(i2);
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(new SecureRandom());
                SecretKey generateKey = keyGenerator.generateKey();
                try {
                    this.mKeyStore.setEntry("profile_key_name_encrypt_" + i, new KeyStore.SecretKeyEntry(generateKey), new KeyProtection.Builder(1).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
                    this.mKeyStore.setEntry("profile_key_name_decrypt_" + i, new KeyStore.SecretKeyEntry(generateKey), new KeyProtection.Builder(2).setBlockModes("GCM").setEncryptionPaddings("NoPadding").build());
                    SecretKey secretKey = (SecretKey) this.mKeyStore.getKey("profile_key_name_encrypt_" + i, null);
                    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                    cipher.init(1, secretKey);
                    byte[] doFinal = cipher.doFinal(lockscreenCredential.getCredential());
                    byte[] iv = cipher.getIV();
                    if (iv.length != 12) {
                        throw new IllegalArgumentException("Invalid iv length: " + iv.length);
                    }
                    LockSettingsStorage lockSettingsStorage = this.mStorage;
                    lockSettingsStorage.writeFile(lockSettingsStorage.getChildProfileLockFile(i), ArrayUtils.concat(new byte[][]{iv, doFinal}), true);
                } finally {
                    this.mKeyStore.deleteEntry("profile_key_name_encrypt_" + i);
                }
            } catch (InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                throw new IllegalStateException("Failed to encrypt key", e);
            }
        } catch (RemoteException e2) {
            throw new IllegalStateException("Failed to talk to GateKeeper service", e2);
        }
    }

    public final boolean tryUnlockWithCachedUnifiedChallenge(int i) {
        LockscreenCredential createUnifiedProfilePassword;
        checkPasswordReadPermission();
        UnifiedProfilePasswordCache unifiedProfilePasswordCache = this.mUnifiedProfilePasswordCache;
        synchronized (unifiedProfilePasswordCache.mEncryptedPasswords) {
            byte[] bArr = (byte[]) unifiedProfilePasswordCache.mEncryptedPasswords.get(i);
            if (bArr != null) {
                try {
                    Key key = unifiedProfilePasswordCache.mKeyStore.getKey("com.android.server.locksettings.unified_profile_cache_v2_" + i, null);
                    if (key != null) {
                        byte[] copyOf = Arrays.copyOf(bArr, 12);
                        byte[] copyOfRange = Arrays.copyOfRange(bArr, 12, bArr.length);
                        try {
                            try {
                                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                                cipher.init(2, key, new GCMParameterSpec(128, copyOf));
                                byte[] doFinal = cipher.doFinal(copyOfRange);
                                createUnifiedProfilePassword = LockscreenCredential.createUnifiedProfilePassword(doFinal);
                                Arrays.fill(doFinal, (byte) 0);
                            } catch (GeneralSecurityException e) {
                                Slog.d("UnifiedProfilePasswordCache", "Cannot decrypt", e);
                            }
                        } catch (UserNotAuthenticatedException unused) {
                            Slog.i("UnifiedProfilePasswordCache", "Device not unlocked for more than 7 days");
                        }
                    }
                } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e2) {
                    Slog.d("UnifiedProfilePasswordCache", "Cannot get key", e2);
                }
            }
            createUnifiedProfilePassword = null;
        }
        if (createUnifiedProfilePassword == null) {
            if (createUnifiedProfilePassword != null) {
                createUnifiedProfilePassword.close();
            }
            return false;
        }
        try {
            boolean z = doVerifyCredential(createUnifiedProfilePassword, i, null, 0).getResponseCode() == 0;
            createUnifiedProfilePassword.close();
            return z;
        } catch (Throwable th) {
            try {
                createUnifiedProfilePassword.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void unlockCeStorage(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        if (isCeStorageUnlocked(i)) {
            Slogf.d("LockSettingsService", "CE storage for user %d is already unlocked", Integer.valueOf(i));
            return;
        }
        String str = isUserSecure(i) ? "secured" : "unsecured";
        syntheticPassword.getClass();
        byte[] deriveSubkey = syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_FBE_KEY);
        try {
            try {
            } catch (RemoteException e) {
                Slogf.wtf("LockSettingsService", e, "Failed to unlock CE storage for %s user %d", str, Integer.valueOf(i));
            }
            if (this.mDualDarLockSettings.isDualDARUser(i)) {
                if (!DualDarManager.isOnDeviceOwner(i)) {
                    Log.d("LockSettingsService", "need to get secret for DualDAR user");
                    Injector.AnonymousClass1 anonymousClass1 = this.mDualDarLockSettings;
                    anonymousClass1.getClass();
                    Slog.d("LockSettingsService", "getChangedStorageSecretIfDualDAR");
                    Slog.d("LockSettingsService", "fetchOuterLayerKey()");
                    byte[] fetchOuterLayerKey = DualDARController.getInstance(((LockSettingsService) anonymousClass1.val$storage).mContext).fetchOuterLayerKey(i);
                    Slog.e("LockSettingsService", "fetchOuterLayerKey Finished");
                    this.mStorageManager.unlockCeStorage(i, fetchOuterLayerKey);
                    Slogf.i("LockSettingsService", "!@Unlocked CE storage for %s user %d", str, Integer.valueOf(i));
                    Arrays.fill(deriveSubkey, (byte) 0);
                }
                Log.d("LockSettingsService", "Do not substitute outer-layer-key in case of DualDAR on DO!");
            }
            this.mStorageManager.unlockCeStorage(i, deriveSubkey);
            Slogf.i("LockSettingsService", "!@Unlocked CE storage for %s user %d", str, Integer.valueOf(i));
            Arrays.fill(deriveSubkey, (byte) 0);
        } catch (Throwable th) {
            Arrays.fill(deriveSubkey, (byte) 0);
            throw th;
        }
    }

    public final void unlockKeystore(int i, SyntheticPasswordManager.SyntheticPassword syntheticPassword) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        KeyStoreAuthorization keyStoreAuthorization = this.mKeyStoreAuthorization;
        syntheticPassword.getClass();
        int onDeviceUnlocked = keyStoreAuthorization.onDeviceUnlocked(i, SyntheticPasswordManager.bytesToHex(syntheticPassword.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_KEY_STORE_PASSWORD)));
        if (onDeviceUnlocked != 0) {
            LsLog.keyErr("unlockKeystore fail, user " + i + ", ret = " + onDeviceUnlocked, true);
        }
    }

    public final void unlockUser(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        Slogf.i("LockSettingsService", "!@BOOT: Unlocking user %d", Integer.valueOf(i));
        boolean isUserUnlockingOrUnlocked = this.mUserManager.isUserUnlockingOrUnlocked(i);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            this.mActivityManager.unlockUser2(i, new IProgressListener.Stub() { // from class: com.android.server.locksettings.LockSettingsService.5
                public final void onFinished(int i2, Bundle bundle) {
                    Slog.d("LockSettingsService", "unlockUser finished");
                    countDownLatch.countDown();
                }

                public final void onProgress(int i2, int i3, Bundle bundle) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(i3, "unlockUser progress ", "LockSettingsService");
                }

                public final void onStarted(int i2, Bundle bundle) {
                    Slog.d("LockSettingsService", "unlockUser started");
                }
            });
            try {
                countDownLatch.await(15L, TimeUnit.SECONDS);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            if (isCredentialSharableWithParent(i)) {
                if (!getSeparateProfileChallengeEnabledInternal(i)) {
                    LockSettingsStorage lockSettingsStorage = this.mStorage;
                    if (lockSettingsStorage.hasFile(lockSettingsStorage.getChildProfileLockFile(i))) {
                        return;
                    }
                }
                final BiometricDeferredQueue biometricDeferredQueue = this.mBiometricDeferredQueue;
                biometricDeferredQueue.getClass();
                biometricDeferredQueue.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricDeferredQueue biometricDeferredQueue2 = BiometricDeferredQueue.this;
                        boolean isEmpty = biometricDeferredQueue2.mPendingResetLockoutsForFace.isEmpty();
                        SyntheticPasswordManager syntheticPasswordManager = biometricDeferredQueue2.mSpManager;
                        if (!isEmpty) {
                            Slog.d("BiometricDeferredQueue", "Processing pending resetLockout for face");
                            ArrayList arrayList = new ArrayList(biometricDeferredQueue2.mPendingResetLockoutsForFace);
                            if (biometricDeferredQueue2.mFaceManager != null) {
                                if (biometricDeferredQueue2.mFaceResetLockoutTask != null) {
                                    Slog.w("BiometricDeferredQueue", "mFaceGenerateChallengeCallback not null, previous operation may be stuck");
                                }
                                List<FaceSensorPropertiesInternal> sensorPropertiesInternal = biometricDeferredQueue2.mFaceManager.getSensorPropertiesInternal();
                                ArraySet arraySet = new ArraySet();
                                Iterator it = sensorPropertiesInternal.iterator();
                                while (it.hasNext()) {
                                    arraySet.add(Integer.valueOf(((FaceSensorPropertiesInternal) it.next()).sensorId));
                                }
                                FaceManager faceManager = biometricDeferredQueue2.mFaceManager;
                                BiometricDeferredQueue.FaceResetLockoutTask faceResetLockoutTask = new BiometricDeferredQueue.FaceResetLockoutTask();
                                faceResetLockoutTask.finishCallback = biometricDeferredQueue2.mFaceFinishCallback;
                                faceResetLockoutTask.faceManager = faceManager;
                                faceResetLockoutTask.spManager = syntheticPasswordManager;
                                faceResetLockoutTask.sensorIds = arraySet;
                                faceResetLockoutTask.pendingResetLockuts = arrayList;
                                biometricDeferredQueue2.mFaceResetLockoutTask = faceResetLockoutTask;
                                for (FaceSensorPropertiesInternal faceSensorPropertiesInternal : sensorPropertiesInternal) {
                                    if (faceSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
                                        Iterator it2 = arrayList.iterator();
                                        while (it2.hasNext()) {
                                            BiometricDeferredQueue.UserAuthInfo userAuthInfo = (BiometricDeferredQueue.UserAuthInfo) it2.next();
                                            if (faceSensorPropertiesInternal.resetLockoutRequiresChallenge) {
                                                StringBuilder sb = new StringBuilder("Generating challenge for sensor: ");
                                                sb.append(faceSensorPropertiesInternal.sensorId);
                                                sb.append(", user: ");
                                                DeviceIdleController$$ExternalSyntheticOutline0.m(sb, userAuthInfo.userId, "BiometricDeferredQueue");
                                                biometricDeferredQueue2.mFaceManager.generateChallenge(faceSensorPropertiesInternal.sensorId, userAuthInfo.userId, biometricDeferredQueue2.mFaceResetLockoutTask);
                                            } else {
                                                StringBuilder sb2 = new StringBuilder("Resetting face lockout for sensor: ");
                                                sb2.append(faceSensorPropertiesInternal.sensorId);
                                                sb2.append(", user: ");
                                                DeviceIdleController$$ExternalSyntheticOutline0.m(sb2, userAuthInfo.userId, "BiometricDeferredQueue");
                                                byte[] requestHatFromGatekeeperPassword = BiometricDeferredQueue.requestHatFromGatekeeperPassword(syntheticPasswordManager, userAuthInfo, 0L);
                                                if (requestHatFromGatekeeperPassword != null) {
                                                    biometricDeferredQueue2.mFaceManager.resetLockout(faceSensorPropertiesInternal.sensorId, userAuthInfo.userId, requestHatFromGatekeeperPassword);
                                                }
                                            }
                                        }
                                    } else {
                                        Iterator it3 = arrayList.iterator();
                                        while (it3.hasNext()) {
                                            BiometricDeferredQueue.UserAuthInfo userAuthInfo2 = (BiometricDeferredQueue.UserAuthInfo) it3.next();
                                            StringBuilder sb3 = new StringBuilder("Resetting face lockout for sensor: ");
                                            sb3.append(faceSensorPropertiesInternal.sensorId);
                                            sb3.append(", user: ");
                                            DeviceIdleController$$ExternalSyntheticOutline0.m(sb3, userAuthInfo2.userId, "BiometricDeferredQueue");
                                            biometricDeferredQueue2.mFaceManager.resetLockout(faceSensorPropertiesInternal.sensorId, userAuthInfo2.userId, (byte[]) null);
                                        }
                                    }
                                }
                            }
                            biometricDeferredQueue2.mPendingResetLockoutsForFace.clear();
                        }
                        if (!biometricDeferredQueue2.mPendingResetLockoutsForFingerprint.isEmpty()) {
                            Slog.d("BiometricDeferredQueue", "Processing pending resetLockout for fingerprint");
                            ArrayList arrayList2 = new ArrayList(biometricDeferredQueue2.mPendingResetLockoutsForFingerprint);
                            FingerprintManager fingerprintManager = biometricDeferredQueue2.mFingerprintManager;
                            if (fingerprintManager != null) {
                                for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : fingerprintManager.getSensorPropertiesInternal()) {
                                    if (!fingerprintSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
                                        Iterator it4 = arrayList2.iterator();
                                        while (it4.hasNext()) {
                                            biometricDeferredQueue2.mFingerprintManager.resetLockout(fingerprintSensorPropertiesInternal.sensorId, ((BiometricDeferredQueue.UserAuthInfo) it4.next()).userId, null);
                                        }
                                    } else if (fingerprintSensorPropertiesInternal.resetLockoutRequiresChallenge) {
                                        HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("No fingerprint HAL interface requires HAT with challenge, sensorId: "), fingerprintSensorPropertiesInternal.sensorId, "BiometricDeferredQueue");
                                    } else {
                                        Iterator it5 = arrayList2.iterator();
                                        while (it5.hasNext()) {
                                            BiometricDeferredQueue.UserAuthInfo userAuthInfo3 = (BiometricDeferredQueue.UserAuthInfo) it5.next();
                                            StringBuilder sb4 = new StringBuilder("Resetting fingerprint lockout for sensor: ");
                                            sb4.append(fingerprintSensorPropertiesInternal.sensorId);
                                            sb4.append(", user: ");
                                            DeviceIdleController$$ExternalSyntheticOutline0.m(sb4, userAuthInfo3.userId, "BiometricDeferredQueue");
                                            byte[] requestHatFromGatekeeperPassword2 = BiometricDeferredQueue.requestHatFromGatekeeperPassword(syntheticPasswordManager, userAuthInfo3, 0L);
                                            if (requestHatFromGatekeeperPassword2 != null) {
                                                biometricDeferredQueue2.mFingerprintManager.resetLockout(fingerprintSensorPropertiesInternal.sensorId, userAuthInfo3.userId, requestHatFromGatekeeperPassword2);
                                            }
                                        }
                                    }
                                }
                            }
                            biometricDeferredQueue2.mPendingResetLockoutsForFingerprint.clear();
                        }
                        if (biometricDeferredQueue2.mPendingResetLockouts.isEmpty()) {
                            return;
                        }
                        Slog.d("BiometricDeferredQueue", "Processing pending resetLockouts(Generic)");
                        Iterator it6 = new ArrayList(biometricDeferredQueue2.mPendingResetLockouts).iterator();
                        while (it6.hasNext()) {
                            BiometricDeferredQueue.UserAuthInfo userAuthInfo4 = (BiometricDeferredQueue.UserAuthInfo) it6.next();
                            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Resetting biometric lockout for user: "), userAuthInfo4.userId, "BiometricDeferredQueue");
                            byte[] requestHatFromGatekeeperPassword3 = BiometricDeferredQueue.requestHatFromGatekeeperPassword(syntheticPasswordManager, userAuthInfo4, 0L);
                            if (requestHatFromGatekeeperPassword3 != null) {
                                biometricDeferredQueue2.mBiometricManager.resetLockout(userAuthInfo4.userId, requestHatFromGatekeeperPassword3);
                            }
                        }
                        biometricDeferredQueue2.mPendingResetLockouts.clear();
                    }
                });
                return;
            }
            for (UserInfo userInfo : this.mUserManager.getProfiles(i)) {
                int i2 = userInfo.id;
                if (i2 != i && isCredentialSharableWithParent(i2)) {
                    int i3 = userInfo.id;
                    if (!getSeparateProfileChallengeEnabledInternal(i3)) {
                        LockSettingsStorage lockSettingsStorage2 = this.mStorage;
                        if (lockSettingsStorage2.hasFile(lockSettingsStorage2.getChildProfileLockFile(i3))) {
                            if (this.mUserManager.isUserRunning(userInfo.id)) {
                                int i4 = userInfo.id;
                                try {
                                    doVerifyCredential(getDecryptedPasswordForTiedProfile(i4), i4, null, 0);
                                } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                                    if (e instanceof FileNotFoundException) {
                                        Slog.i("LockSettingsService", "Child profile key not found");
                                    } else {
                                        Slog.e("LockSettingsService", "Failed to decrypt child profile key", e);
                                    }
                                }
                            } else {
                                try {
                                    getDecryptedPasswordForTiedProfile(userInfo.id);
                                } catch (IOException | GeneralSecurityException e2) {
                                    Slog.d("LockSettingsService", "Cache unified profile password failed", e2);
                                }
                            }
                        }
                    }
                    if (StorageManager.isFileEncrypted() && userInfo.isSecureFolder()) {
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
            final BiometricDeferredQueue biometricDeferredQueue2 = this.mBiometricDeferredQueue;
            biometricDeferredQueue2.getClass();
            biometricDeferredQueue2.mHandler.post(new Runnable() { // from class: com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricDeferredQueue biometricDeferredQueue22 = BiometricDeferredQueue.this;
                    boolean isEmpty = biometricDeferredQueue22.mPendingResetLockoutsForFace.isEmpty();
                    SyntheticPasswordManager syntheticPasswordManager = biometricDeferredQueue22.mSpManager;
                    if (!isEmpty) {
                        Slog.d("BiometricDeferredQueue", "Processing pending resetLockout for face");
                        ArrayList arrayList = new ArrayList(biometricDeferredQueue22.mPendingResetLockoutsForFace);
                        if (biometricDeferredQueue22.mFaceManager != null) {
                            if (biometricDeferredQueue22.mFaceResetLockoutTask != null) {
                                Slog.w("BiometricDeferredQueue", "mFaceGenerateChallengeCallback not null, previous operation may be stuck");
                            }
                            List<FaceSensorPropertiesInternal> sensorPropertiesInternal = biometricDeferredQueue22.mFaceManager.getSensorPropertiesInternal();
                            ArraySet arraySet = new ArraySet();
                            Iterator it = sensorPropertiesInternal.iterator();
                            while (it.hasNext()) {
                                arraySet.add(Integer.valueOf(((FaceSensorPropertiesInternal) it.next()).sensorId));
                            }
                            FaceManager faceManager = biometricDeferredQueue22.mFaceManager;
                            BiometricDeferredQueue.FaceResetLockoutTask faceResetLockoutTask = new BiometricDeferredQueue.FaceResetLockoutTask();
                            faceResetLockoutTask.finishCallback = biometricDeferredQueue22.mFaceFinishCallback;
                            faceResetLockoutTask.faceManager = faceManager;
                            faceResetLockoutTask.spManager = syntheticPasswordManager;
                            faceResetLockoutTask.sensorIds = arraySet;
                            faceResetLockoutTask.pendingResetLockuts = arrayList;
                            biometricDeferredQueue22.mFaceResetLockoutTask = faceResetLockoutTask;
                            for (FaceSensorPropertiesInternal faceSensorPropertiesInternal : sensorPropertiesInternal) {
                                if (faceSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
                                    Iterator it2 = arrayList.iterator();
                                    while (it2.hasNext()) {
                                        BiometricDeferredQueue.UserAuthInfo userAuthInfo = (BiometricDeferredQueue.UserAuthInfo) it2.next();
                                        if (faceSensorPropertiesInternal.resetLockoutRequiresChallenge) {
                                            StringBuilder sb = new StringBuilder("Generating challenge for sensor: ");
                                            sb.append(faceSensorPropertiesInternal.sensorId);
                                            sb.append(", user: ");
                                            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, userAuthInfo.userId, "BiometricDeferredQueue");
                                            biometricDeferredQueue22.mFaceManager.generateChallenge(faceSensorPropertiesInternal.sensorId, userAuthInfo.userId, biometricDeferredQueue22.mFaceResetLockoutTask);
                                        } else {
                                            StringBuilder sb2 = new StringBuilder("Resetting face lockout for sensor: ");
                                            sb2.append(faceSensorPropertiesInternal.sensorId);
                                            sb2.append(", user: ");
                                            DeviceIdleController$$ExternalSyntheticOutline0.m(sb2, userAuthInfo.userId, "BiometricDeferredQueue");
                                            byte[] requestHatFromGatekeeperPassword = BiometricDeferredQueue.requestHatFromGatekeeperPassword(syntheticPasswordManager, userAuthInfo, 0L);
                                            if (requestHatFromGatekeeperPassword != null) {
                                                biometricDeferredQueue22.mFaceManager.resetLockout(faceSensorPropertiesInternal.sensorId, userAuthInfo.userId, requestHatFromGatekeeperPassword);
                                            }
                                        }
                                    }
                                } else {
                                    Iterator it3 = arrayList.iterator();
                                    while (it3.hasNext()) {
                                        BiometricDeferredQueue.UserAuthInfo userAuthInfo2 = (BiometricDeferredQueue.UserAuthInfo) it3.next();
                                        StringBuilder sb3 = new StringBuilder("Resetting face lockout for sensor: ");
                                        sb3.append(faceSensorPropertiesInternal.sensorId);
                                        sb3.append(", user: ");
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(sb3, userAuthInfo2.userId, "BiometricDeferredQueue");
                                        biometricDeferredQueue22.mFaceManager.resetLockout(faceSensorPropertiesInternal.sensorId, userAuthInfo2.userId, (byte[]) null);
                                    }
                                }
                            }
                        }
                        biometricDeferredQueue22.mPendingResetLockoutsForFace.clear();
                    }
                    if (!biometricDeferredQueue22.mPendingResetLockoutsForFingerprint.isEmpty()) {
                        Slog.d("BiometricDeferredQueue", "Processing pending resetLockout for fingerprint");
                        ArrayList arrayList2 = new ArrayList(biometricDeferredQueue22.mPendingResetLockoutsForFingerprint);
                        FingerprintManager fingerprintManager = biometricDeferredQueue22.mFingerprintManager;
                        if (fingerprintManager != null) {
                            for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : fingerprintManager.getSensorPropertiesInternal()) {
                                if (!fingerprintSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
                                    Iterator it4 = arrayList2.iterator();
                                    while (it4.hasNext()) {
                                        biometricDeferredQueue22.mFingerprintManager.resetLockout(fingerprintSensorPropertiesInternal.sensorId, ((BiometricDeferredQueue.UserAuthInfo) it4.next()).userId, null);
                                    }
                                } else if (fingerprintSensorPropertiesInternal.resetLockoutRequiresChallenge) {
                                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("No fingerprint HAL interface requires HAT with challenge, sensorId: "), fingerprintSensorPropertiesInternal.sensorId, "BiometricDeferredQueue");
                                } else {
                                    Iterator it5 = arrayList2.iterator();
                                    while (it5.hasNext()) {
                                        BiometricDeferredQueue.UserAuthInfo userAuthInfo3 = (BiometricDeferredQueue.UserAuthInfo) it5.next();
                                        StringBuilder sb4 = new StringBuilder("Resetting fingerprint lockout for sensor: ");
                                        sb4.append(fingerprintSensorPropertiesInternal.sensorId);
                                        sb4.append(", user: ");
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(sb4, userAuthInfo3.userId, "BiometricDeferredQueue");
                                        byte[] requestHatFromGatekeeperPassword2 = BiometricDeferredQueue.requestHatFromGatekeeperPassword(syntheticPasswordManager, userAuthInfo3, 0L);
                                        if (requestHatFromGatekeeperPassword2 != null) {
                                            biometricDeferredQueue22.mFingerprintManager.resetLockout(fingerprintSensorPropertiesInternal.sensorId, userAuthInfo3.userId, requestHatFromGatekeeperPassword2);
                                        }
                                    }
                                }
                            }
                        }
                        biometricDeferredQueue22.mPendingResetLockoutsForFingerprint.clear();
                    }
                    if (biometricDeferredQueue22.mPendingResetLockouts.isEmpty()) {
                        return;
                    }
                    Slog.d("BiometricDeferredQueue", "Processing pending resetLockouts(Generic)");
                    Iterator it6 = new ArrayList(biometricDeferredQueue22.mPendingResetLockouts).iterator();
                    while (it6.hasNext()) {
                        BiometricDeferredQueue.UserAuthInfo userAuthInfo4 = (BiometricDeferredQueue.UserAuthInfo) it6.next();
                        DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("Resetting biometric lockout for user: "), userAuthInfo4.userId, "BiometricDeferredQueue");
                        byte[] requestHatFromGatekeeperPassword3 = BiometricDeferredQueue.requestHatFromGatekeeperPassword(syntheticPasswordManager, userAuthInfo4, 0L);
                        if (requestHatFromGatekeeperPassword3 != null) {
                            biometricDeferredQueue22.mBiometricManager.resetLockout(userAuthInfo4.userId, requestHatFromGatekeeperPassword3);
                        }
                    }
                    biometricDeferredQueue22.mPendingResetLockouts.clear();
                }
            });
        } catch (RemoteException e3) {
            throw e3.rethrowAsRuntimeException();
        }
    }

    public final void unlockUserKeyIfUnsecured(int i) {
        if (UserManager.isVirtualUserId(i)) {
            return;
        }
        checkPasswordReadPermission();
        if (Objects.equals(getString("migrated_dualdar_user_to_sp_and_bound_ce", "true", i), "false")) {
            synchronized (this.mSpManager) {
                DDLog.d("LockSettingsService", "Migrating unsecured DualDar user.", new Object[0]);
                migrateUserToSpWithBoundCeKeyLocked(i);
            }
            DDLog.d("LockSettingsService", "Migrated unsecured DualDar user.", new Object[0]);
            setString("migrated_dualdar_user_to_sp_and_bound_ce", "true", i);
        }
        synchronized (this.mSpManager) {
            try {
                if (isCeStorageUnlocked(i)) {
                    Slogf.d("LockSettingsService", "CE storage for user %d is already unlocked", Integer.valueOf(i));
                    return;
                }
                if (isUserSecure(i)) {
                    Slogf.d("LockSettingsService", "Not unlocking CE storage for user %d yet because user is secured", Integer.valueOf(i));
                    return;
                }
                boolean z = true;
                if (this.mDualDarLockSettings.isDualDARUser(i) && !DualDarManager.isOnDeviceOwner(i)) {
                    if (StateMachine.getCurrentState(i) == State.DEVICE_UNLOCK_DATA_UNLOCK) {
                        DDLog.d("LockSettingsService", VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Password2Auth has already been completed for: "), new Object[0]);
                    } else {
                        DDLog.e("LockSettingsService", "dualdar data is not unlocked yet!", new Object[0]);
                        z = false;
                    }
                }
                if (!z) {
                    Slogf.d("LockSettingsService", "unlocking CE storage for dualdar user %d is failed", Integer.valueOf(i));
                    return;
                }
                Slogf.i("LockSettingsService", "Unwrapping synthetic password for unsecured user %d", Integer.valueOf(i));
                SyntheticPasswordManager.AuthenticationResult unlockLskfBasedProtector = this.mSpManager.unlockLskfBasedProtector(getGateKeeperService(), getCurrentLskfBasedProtectorId(i), LockscreenCredential.createNone(), i, null);
                SyntheticPasswordManager.SyntheticPassword syntheticPassword = unlockLskfBasedProtector.syntheticPassword;
                if (syntheticPassword == null) {
                    Slogf.wtf("LockSettingsService", "Failed to unwrap synthetic password for unsecured user %d", Integer.valueOf(i));
                    return;
                }
                onSyntheticPasswordKnown(i, syntheticPassword, false);
                if (FIX_UNLOCKED_DEVICE_REQUIRED_KEYS) {
                    unlockKeystore(i, unlockLskfBasedProtector.syntheticPassword);
                }
                unlockCeStorage(i, unlockLskfBasedProtector.syntheticPassword);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterRemoteLockCallback(int i, IRemoteLockMonitorCallback iRemoteLockMonitorCallback) {
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("registerRemoteLockCallback requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        if (this.mCallbacks.containsKey(Integer.valueOf(i))) {
            this.mCallbacks.remove(Integer.valueOf(i));
            return;
        }
        Log.e("LockSettingsService", i + " hasn't been registered!!");
    }

    public final void unregisterStrongAuthTracker(IStrongAuthTracker iStrongAuthTracker) {
        checkPasswordReadPermission();
        this.mStrongAuth.mHandler.obtainMessage(3, iStrongAuthTracker).sendToTarget();
    }

    public final boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        checkManageWeakEscrowTokenMethodUsage();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mSpManager.mListeners.unregister(iWeakEscrowTokenRemovedListener);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updateCarrierLock(int i) {
        checkWritePermission();
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.getClass();
        Slog.d("LockSettingsStorage", "updateCarrierLock!!");
        if (lockSettingsStorage.getCarrierLockFromFile()) {
            lockSettingsStorage.mSKTLockState = 1;
        } else {
            lockSettingsStorage.mSKTLockState = 2;
        }
        return lockSettingsStorage.mSKTLockState == 1;
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

    public final void updateSdpMdfppForSystem(int i, long j) {
    }

    public final void updateVerifier(LockscreenCredential lockscreenCredential, int i) {
        try {
            Slog.i("LockSettingsService", "updateVerifier");
            if (i != 0) {
                Slog.i("LockSettingsService", "user is not owner");
                return;
            }
            if (lockscreenCredential.isNone()) {
                Slog.i("LockSettingsService", "credential is none");
                return;
            }
            final Intent intent = new Intent("com.samsung.android.kmxservice.escrowvault.UPDATE_VERIFIER");
            intent.setPackage("com.samsung.android.kmxservice");
            intent.putExtra("service_name", "KmxService");
            final UpdateVerifierServiceConnection updateVerifierServiceConnection = new UpdateVerifierServiceConnection(lockscreenCredential.getCredential(), LockPatternUtils.credentialTypeToPasswordQuality(lockscreenCredential.getType()));
            new Thread(new Runnable() { // from class: com.android.server.locksettings.LockSettingsService$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    LockSettingsService lockSettingsService = LockSettingsService.this;
                    lockSettingsService.mShouldUnbind = lockSettingsService.mContext.bindService(intent, updateVerifierServiceConnection, 1);
                }
            }, "KMX_UPDATE_VERIFIER_SERVICE_CONNECTION").start();
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("updateVerifier exception : "), "LockSettingsService");
        }
    }

    public final void userPresent(int i) {
        checkWritePermission();
        this.mStrongAuth.requireStrongAuth(0, i);
        if (isEnablePrevCredential() && i == 0) {
            updateExpireTimeForPrev(true);
        }
        LsLog.tryUpload(this.mContext);
    }

    public final RemoteLockscreenValidationResult validateRemoteLockscreen(byte[] bArr) {
        LockscreenCredential createPassword;
        RemoteLockscreenValidationResult handleVerifyCredentialResponse;
        RecoverableKeyStoreManager recoverableKeyStoreManager = this.mRecoverableKeyStoreManager;
        synchronized (recoverableKeyStoreManager) {
            recoverableKeyStoreManager.checkVerifyRemoteLockscreenPermission();
            int callingUserId = UserHandle.getCallingUserId();
            RemoteLockscreenValidationSessionStorage.LockscreenVerificationSession lockscreenVerificationSession = recoverableKeyStoreManager.mRemoteLockscreenValidationSessionStorage.get(callingUserId);
            if (5 - recoverableKeyStoreManager.mDatabase.getBadRemoteGuessCounter(callingUserId) <= 0) {
                handleVerifyCredentialResponse = new RemoteLockscreenValidationResult.Builder().setResultCode(4).build();
            } else if (lockscreenVerificationSession == null) {
                handleVerifyCredentialResponse = new RemoteLockscreenValidationResult.Builder().setResultCode(5).build();
            } else {
                try {
                    byte[] decrypt = SecureBox.decrypt(lockscreenVerificationSession.mKeyPair.getPrivate(), null, LockPatternUtils.ENCRYPTED_REMOTE_CREDENTIALS_HEADER, bArr);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        int lockPatternUtilsToKeyguardType = RecoverableKeyStoreManager.lockPatternUtilsToKeyguardType(getCredentialType(callingUserId));
                        if (lockPatternUtilsToKeyguardType == 0) {
                            createPassword = LockscreenCredential.createPassword(new String(decrypt, StandardCharsets.UTF_8));
                        } else if (lockPatternUtilsToKeyguardType == 1) {
                            createPassword = LockscreenCredential.createPin(new String(decrypt));
                        } else {
                            if (lockPatternUtilsToKeyguardType != 2) {
                                throw new IllegalStateException("Lockscreen is not set");
                            }
                            createPassword = LockscreenCredential.createPattern(LockPatternUtils.byteArrayToPattern(decrypt));
                        }
                        try {
                            Arrays.fill(decrypt, (byte) 0);
                            handleVerifyCredentialResponse = recoverableKeyStoreManager.handleVerifyCredentialResponse(verifyCredential(createPassword, callingUserId, 0), callingUserId);
                            if (createPassword != null) {
                                createPassword.close();
                            }
                        } finally {
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (InvalidKeyException e) {
                    Log.e("RecoverableKeyStoreMgr", "Got InvalidKeyException during lock screen credentials decryption");
                    throw new IllegalStateException(e);
                } catch (NoSuchAlgorithmException e2) {
                    Log.wtf("RecoverableKeyStoreMgr", "Missing SecureBox algorithm. AOSP required to support this.", e2);
                    throw new IllegalStateException(e2);
                } catch (AEADBadTagException e3) {
                    throw new IllegalStateException("Could not decrypt credentials guess", e3);
                }
            }
        }
        return handleVerifyCredentialResponse;
    }

    public final VerifyCredentialResponse verifyCredential(LockscreenCredential lockscreenCredential, int i, int i2) {
        VerifyCredentialResponse doVerifyCredential;
        if (!hasPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE") && !hasPermission("android.permission.SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS")) {
            throw new SecurityException("verifyCredential requires SET_AND_VERIFY_LOCKSCREEN_CREDENTIALS or android.permission.ACCESS_KEYGUARD_SECURE_STORAGE");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isEnterpriseUserId(i)) {
                try {
                    lockscreenCredential = StreamCipher.decryptStream(lockscreenCredential);
                    doVerifyCredential = doVerifyCredential(lockscreenCredential, i, null, i2);
                    lockscreenCredential.zeroize();
                } catch (Throwable th) {
                    if (lockscreenCredential != null) {
                        lockscreenCredential.zeroize();
                    }
                    throw th;
                }
            } else {
                doVerifyCredential = doVerifyCredential(lockscreenCredential, i, null, i2);
            }
            return doVerifyCredential;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            scheduleGc();
        }
    }

    public final VerifyCredentialResponse verifyGatekeeperPasswordHandle(long j, long j2, int i) {
        byte[] bArr;
        VerifyCredentialResponse verifyChallengeInternal;
        checkPasswordReadPermission();
        synchronized (this.mGatekeeperPasswords) {
            bArr = (byte[]) this.mGatekeeperPasswords.get(j);
        }
        synchronized (this.mSpManager) {
            try {
                if (bArr == null) {
                    Slog.d("LockSettingsService", "No gatekeeper password for handle");
                    verifyChallengeInternal = VerifyCredentialResponse.ERROR;
                } else {
                    verifyChallengeInternal = this.mSpManager.verifyChallengeInternal(i, j2, getGateKeeperService(), bArr);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return verifyChallengeInternal;
    }

    public final VerifyCredentialResponse verifyTiedProfileChallenge(LockscreenCredential lockscreenCredential, int i, int i2) {
        checkPasswordReadPermission();
        Slogf.i("LockSettingsService", "Verifying tied profile challenge for user %d", Integer.valueOf(i));
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

    public final VerifyCredentialResponse verifyToken(byte[] bArr, long j, int i) {
        SDPLog.i("Verify token for user " + i);
        SDPLog.p(KnoxCustomManagerService.SPCM_KEY_TOKEN, bArr, "tokenHandle", Long.valueOf(j), "userId", Integer.valueOf(i));
        VerifyCredentialResponse verifyCredentialResponse = VerifyCredentialResponse.ERROR;
        try {
            synchronized (this.mSpManager) {
                try {
                    if (!this.mSpManager.hasEscrowData(i)) {
                        throw new SecurityException("Escrow token is disabled on the current user");
                    }
                    SyntheticPasswordManager.AuthenticationResult unlockTokenBasedProtector = this.mSpManager.unlockTokenBasedProtector(i, j, getGateKeeperService(), bArr);
                    SyntheticPasswordManager.SyntheticPassword syntheticPassword = unlockTokenBasedProtector.syntheticPassword;
                    if (syntheticPassword == null) {
                        SDPLog.d(null, "Failed due to invalid escrow token supplied");
                    } else {
                        this.mSdpLockSettings.migrateWithAuthToken(i, syntheticPassword);
                        verifyCredentialResponse = new VerifyCredentialResponse.Builder().build();
                        SyntheticPasswordManager.SyntheticPassword syntheticPassword2 = unlockTokenBasedProtector.syntheticPassword;
                        syntheticPassword2.getClass();
                        verifyCredentialResponse.setSecret(syntheticPassword2.deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_SDP_MASTER_KEY));
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            SDPLog.e(e, null, "Unexpected exception while verify token");
        }
        SDPLog.d(null, "Result of token verification : " + verifyCredentialResponse.toString());
        return verifyCredentialResponse;
    }
}
