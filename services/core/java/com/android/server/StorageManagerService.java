package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AnrController;
import android.app.AppOpsManager;
import android.app.IActivityManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.app.admin.SecurityLog;
import android.app.usage.StorageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.UserInfo;
import android.content.res.ObbInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.DiskInfo;
import android.os.storage.IObbActionListener;
import android.os.storage.IStorageEventListener;
import android.os.storage.IStorageManager;
import android.os.storage.IStorageShutdownObserver;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.os.IInstalld;
import android.os.IStoraged;
import android.os.IVold;
import android.os.IVoldListener;
import android.os.IVoldMountCallback;
import android.os.IVoldTaskListener;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.DataUnit;
import android.util.DebugUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TimeUtils;
import android.util.Xml;
import android.util.sysfwutil.Slog;
import com.android.internal.app.IAppOpsService;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.AppFuseMount;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.FuseUnavailableMountException;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.HexDump;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.StorageManagerService;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.Installer;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.storage.AppFuseBridge;
import com.android.server.storage.StorageSessionController;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.jdsms.Sender;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.security.IDirEncryptServiceListener;
import com.samsung.android.security.SemSdCardEncryption;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.io.IoUtils;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class StorageManagerService extends IStorageManager.Stub implements Watchdog.Monitor, ActivityTaskManagerInternal.ScreenObserver {
    public static String sMediaStoreAuthorityProcessName;
    public static StorageManagerService sSelf;
    public final Callbacks mCallbacks;
    public volatile boolean mChargingRequired;
    public final Context mContext;
    public volatile float mDirtyReclaimRate;
    public final StorageManagerServiceHandler mHandler;
    public HeimdAllFsService mHeimdAllFs;
    public IAppOpsService mIAppOpsService;
    public IPackageManager mIPackageManager;
    public final Installer mInstaller;
    public long mLastMaintenance;
    public final File mLastMaintenanceFile;
    public volatile int mLifetimePercentThreshold;
    public volatile float mLowBatteryLevel;
    public volatile int mMaxWriteRecords;
    public volatile int mMinGCSleepTime;
    public volatile int mMinSegmentsThreshold;
    public IPackageMoveObserver mMoveCallback;
    public String mMoveTargetUuid;
    public final ObbActionHandler mObbActionHandler;
    public volatile boolean mPassedLifetimeThresh;
    public PackageManagerInternal mPmInternal;
    public String mPrimaryStorageUuid;
    public volatile float mSegmentReclaimWeight;
    public final AtomicFile mSettingsFile;
    public final StorageSessionController mStorageSessionController;
    public volatile int[] mStorageWriteRecords;
    public volatile IStoraged mStoraged;
    public volatile int mTargetDirtyRatio;
    public CountDownLatch mUnmountSignal;
    public volatile IVold mVold;
    public final boolean mVoldAppDataIsolationEnabled;
    public final AtomicFile mWriteRecordFile;
    public static final boolean LOCAL_LOGV = Log.isLoggable("StorageManagerService", 2);
    public static volatile int sSmartIdleMaintPeriod = 60;
    public static final Pattern KNOWN_APP_DIR_PATHS = Pattern.compile("(?i)(^/storage/[^/]+/(?:([0-9]+)/)?Android/(?:data|media|obb|sandbox)/)([^/]+)(/.*)?");
    public final Set mFuseMountedUser = new ArraySet();
    public final Set mCeStoragePreparedUsers = new ArraySet();
    public volatile boolean mNeedGC = true;
    public final Object mLock = LockGuard.installNewLock(4);
    public WatchedLockedUsers mLocalUnlockedUsers = new WatchedLockedUsers();
    public int[] mSystemUnlockedUsers = EmptyArray.INT;
    public ArrayMap mDisks = new ArrayMap();
    public final ArrayMap mVolumes = new ArrayMap();
    public ArrayMap mRecords = new ArrayMap();
    public ArrayMap mDiskScanLatches = new ArrayMap();
    public final SparseArray mCloudMediaProviders = new SparseArray();
    public volatile int mMediaStoreAuthorityAppId = -1;
    public volatile int mDownloadsAuthorityAppId = -1;
    public volatile int mExternalStorageAuthorityAppId = -1;
    public volatile int mCurrentUserId = 0;
    public volatile boolean mRemountCurrentUserVolumesOnUnlock = false;
    public final Object mAppFuseLock = new Object();
    public int mNextAppFuseName = 0;
    public AppFuseBridge mAppFuseBridge = null;
    public HashMap mIsMediaSharedWithParent = new HashMap();
    public final HashSet mAsecMountSet = new HashSet();
    public final Object mUnmountLock = new Object();
    public final CountDownLatch mAsecsScanned = new CountDownLatch(1);
    public boolean preSDPolicy = true;
    public final SparseIntArray mUserSharesMediaWith = new SparseIntArray();
    public volatile boolean mBootCompleted = false;
    public volatile boolean mDaemonConnected = false;
    public volatile boolean mSecureKeyguardShowing = true;
    public final Map mObbMounts = new HashMap();
    public final Map mObbPathToStateMap = new HashMap();
    public final StorageManagerInternalImpl mStorageManagerInternal = new StorageManagerInternalImpl();
    public final Set mUidsWithLegacyExternalStorage = new ArraySet();
    public final SparseArray mPackageMonitorsForUser = new SparseArray();
    public BroadcastReceiver mUserReceiver = new BroadcastReceiver() { // from class: com.android.server.StorageManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            Preconditions.checkArgument(intExtra >= 0);
            try {
                if ("android.intent.action.USER_ADDED".equals(action)) {
                    UserManager userManager = (UserManager) StorageManagerService.this.mContext.getSystemService(UserManager.class);
                    int userSerialNumber = userManager.getUserSerialNumber(intExtra);
                    UserInfo userInfo = userManager.getUserInfo(intExtra);
                    if (userInfo.isCloneProfile()) {
                        StorageManagerService.this.mVold.onUserAdded(intExtra, userSerialNumber, userInfo.profileGroupId);
                        return;
                    } else {
                        StorageManagerService.this.mVold.onUserAdded(intExtra, userSerialNumber, -1);
                        return;
                    }
                }
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    synchronized (StorageManagerService.this.mLock) {
                        int size = StorageManagerService.this.mVolumes.size();
                        for (int i = 0; i < size; i++) {
                            VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.valueAt(i);
                            if (volumeInfo.mountUserId == intExtra) {
                                volumeInfo.mountUserId = -10000;
                                StorageManagerService.this.mHandler.obtainMessage(8, volumeInfo).sendToTarget();
                            }
                        }
                    }
                    StorageManagerService.this.mVold.onUserRemoved(intExtra);
                }
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
            }
        }
    };
    public final IVoldListener mListener = new IVoldListener.Stub() { // from class: com.android.server.StorageManagerService.3
        /* JADX WARN: Removed duplicated region for block: B:11:0x0035  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x003b A[Catch: all -> 0x004d, TryCatch #0 {, blocks: (B:4:0x0007, B:13:0x003d, B:14:0x004b, B:18:0x0038, B:19:0x003b, B:20:0x001e, B:23:0x0028), top: B:3:0x0007 }] */
        @Override // android.os.IVoldListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onDiskCreated(String str, int i) {
            char c;
            synchronized (StorageManagerService.this.mLock) {
                String str2 = SystemProperties.get("persist.sys.adoptable");
                int hashCode = str2.hashCode();
                if (hashCode != 464944051) {
                    if (hashCode == 1528363547 && str2.equals("force_off")) {
                        c = 1;
                        if (c != 0) {
                            i |= 1;
                        } else if (c == 1) {
                            i &= -2;
                        }
                        StorageManagerService.this.mDisks.put(str, new DiskInfo(str, i));
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                    StorageManagerService.this.mDisks.put(str, new DiskInfo(str, i));
                } else {
                    if (str2.equals("force_on")) {
                        c = 0;
                        if (c != 0) {
                        }
                        StorageManagerService.this.mDisks.put(str, new DiskInfo(str, i));
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                    StorageManagerService.this.mDisks.put(str, new DiskInfo(str, i));
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onDiskScanned(String str) {
            synchronized (StorageManagerService.this.mLock) {
                DiskInfo diskInfo = (DiskInfo) StorageManagerService.this.mDisks.get(str);
                if (diskInfo != null) {
                    StorageManagerService.this.onDiskScannedLocked(diskInfo);
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onDiskMetadataChanged(String str, long j, String str2, String str3) {
            synchronized (StorageManagerService.this.mLock) {
                DiskInfo diskInfo = (DiskInfo) StorageManagerService.this.mDisks.get(str);
                if (diskInfo != null) {
                    diskInfo.size = j;
                    diskInfo.label = str2;
                    diskInfo.sysPath = str3;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onDiskDestroyed(String str) {
            synchronized (StorageManagerService.this.mLock) {
                DiskInfo diskInfo = (DiskInfo) StorageManagerService.this.mDisks.remove(str);
                if (diskInfo != null) {
                    StorageManagerService.this.mCallbacks.notifyDiskDestroyed(diskInfo);
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeCreated(String str, int i, String str2, String str3, int i2) {
            synchronized (StorageManagerService.this.mLock) {
                VolumeInfo volumeInfo = new VolumeInfo(str, i, (DiskInfo) StorageManagerService.this.mDisks.get(str2), str3);
                volumeInfo.mountUserId = i2;
                StorageManagerService.this.mVolumes.put(str, volumeInfo);
                StorageManagerService.this.onVolumeCreatedLocked(volumeInfo);
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeStateChanged(String str, int i) {
            synchronized (StorageManagerService.this.mLock) {
                VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                if (volumeInfo != null) {
                    int i2 = volumeInfo.state;
                    volumeInfo.state = i;
                    VolumeInfo volumeInfo2 = new VolumeInfo(volumeInfo);
                    SomeArgs obtain = SomeArgs.obtain();
                    obtain.arg1 = volumeInfo2;
                    obtain.argi1 = i2;
                    obtain.argi2 = i;
                    StorageManagerService.this.mHandler.obtainMessage(15, obtain).sendToTarget();
                    StorageManagerService.this.onVolumeStateChangedLocked(volumeInfo2, i);
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeMetadataChanged(String str, String str2, String str3, String str4) {
            synchronized (StorageManagerService.this.mLock) {
                VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                if (volumeInfo != null) {
                    volumeInfo.fsType = str2;
                    volumeInfo.fsUuid = str3;
                    volumeInfo.fsLabel = str4;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumePathChanged(String str, String str2) {
            synchronized (StorageManagerService.this.mLock) {
                VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                if (volumeInfo != null) {
                    volumeInfo.path = str2;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeInternalPathChanged(String str, String str2) {
            synchronized (StorageManagerService.this.mLock) {
                VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                if (volumeInfo != null) {
                    volumeInfo.internalPath = str2;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeDestroyed(String str) {
            VolumeInfo volumeInfo;
            synchronized (StorageManagerService.this.mLock) {
                volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.remove(str);
            }
            if (volumeInfo != null) {
                StorageManagerService.this.mStorageSessionController.onVolumeRemove(volumeInfo);
                try {
                    if (volumeInfo.type == 1) {
                        StorageManagerService.this.mInstaller.onPrivateVolumeRemoved(volumeInfo.getFsUuid());
                    }
                } catch (Installer.InstallerException e) {
                    Slog.i("StorageManagerService", "Failed when private volume unmounted " + volumeInfo, e);
                }
            }
        }

        @Override // android.os.IVoldListener
        public void sendVoldMessage(String str) {
            Intent intent = new Intent("com.samsung.intent.action.EXTERNAL_STORAGE_WARNING_SEC");
            if (str == null) {
                Slog.d("StorageManagerService", "Intent[" + intent + "] cannot be sent");
                return;
            }
            intent.putExtra("message", str);
            if (("BAD_REMOVAL_SDCARD".equals(str) && StorageManagerService.this.isExtSDCardTrayRemoved()) || "BAD_REMOVAL_USB".equals(str) || "REBOOT".equals(str)) {
                StorageManagerService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                Slog.d("StorageManagerService", "[" + str + "] Bad Removal broadcasting " + intent + " extras: " + intent.getExtras());
                return;
            }
            Slog.d("StorageManagerService", "Don't send the Intent[" + intent + "] extras: " + intent.getExtras());
        }

        @Override // android.os.IVoldListener
        public void onEncryptionStateChanged(String str, String str2, String str3) {
            synchronized (StorageManagerService.this.mLock) {
                Slog.d("StorageManagerService", "onEncryptionStateChanged state = " + str2 + ", type : " + str3);
                if (((VolumeInfo) StorageManagerService.this.mVolumes.get(str)) != null) {
                    if ("encryptable".equals(str2)) {
                        SemSdCardEncryption semSdCardEncryption = new SemSdCardEncryption(StorageManagerService.this.mContext);
                        PersistableBundle persistableBundle = new PersistableBundle();
                        persistableBundle.putInt("status", 0);
                        persistableBundle.putString("description", str3);
                        try {
                            semSdCardEncryption.getListener().onStatus(692, persistableBundle);
                        } catch (RemoteException e) {
                            Slog.e("StorageManagerService", "failed to send onStatus()", e);
                        }
                        str3 = "block";
                    }
                    SystemProperties.set("sec.fle.encryption.status", str2);
                    SystemProperties.set("sec.vold.ext_encrypted_type", str3);
                    Slog.d("StorageManagerService", "onEncryptionStateChanged status updated ");
                }
            }
        }
    };
    public DirEncryptListner mDirEncryptListner = null;
    public SemSdCardEncryption mDem = null;
    public BroadcastReceiver mRestartSdcardBadremoveReceiver = new C033216();
    public BroadcastReceiver mPolicyReceiver = new BroadcastReceiver() { // from class: com.android.server.StorageManagerService.17
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.d("StorageManagerService", "mPolicyReceiver :: ");
            StorageManagerService storageManagerService = StorageManagerService.this;
            storageManagerService.applyCurrentSdCardPolicy(storageManagerService.isMountDisallowedByEAS(true));
        }
    };

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onAwakeStateChanged(boolean z) {
    }

    public final boolean prepareSmartIdleMaint() {
        return false;
    }

    public class Lifecycle extends SystemService {
        public StorageManagerService mStorageManagerService;

        public Lifecycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.StorageManagerService] */
        @Override // com.android.server.SystemService
        public void onStart() {
            ?? storageManagerService = new StorageManagerService(getContext());
            this.mStorageManagerService = storageManagerService;
            publishBinderService("mount", storageManagerService);
            this.mStorageManagerService.start();
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mStorageManagerService.servicesReady();
            } else if (i == 550) {
                this.mStorageManagerService.systemReady();
            } else if (i == 1000) {
                this.mStorageManagerService.bootCompleted();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
            int userIdentifier = targetUser2.getUserIdentifier();
            this.mStorageManagerService.mCurrentUserId = userIdentifier;
            if (((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).isUserUnlocked(userIdentifier)) {
                Slog.d("StorageManagerService", "Attempt remount volumes for user: " + userIdentifier);
                this.mStorageManagerService.maybeRemountVolumes(userIdentifier);
                this.mStorageManagerService.mRemountCurrentUserVolumesOnUnlock = false;
                return;
            }
            Slog.d("StorageManagerService", "Attempt remount volumes for user: " + userIdentifier + " on unlock");
            this.mStorageManagerService.mRemountCurrentUserVolumesOnUnlock = true;
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(SystemService.TargetUser targetUser) {
            this.mStorageManagerService.onUnlockUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(SystemService.TargetUser targetUser) {
            this.mStorageManagerService.onCleanupUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(SystemService.TargetUser targetUser) {
            this.mStorageManagerService.onStopUser(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(SystemService.TargetUser targetUser) {
            this.mStorageManagerService.snapshotAndMonitorLegacyStorageAppOp(targetUser.getUserHandle());
        }
    }

    public class WatchedLockedUsers {
        public int[] users = EmptyArray.INT;

        public WatchedLockedUsers() {
            invalidateIsUserUnlockedCache();
        }

        public void append(int i) {
            this.users = ArrayUtils.appendInt(this.users, i);
            invalidateIsUserUnlockedCache();
        }

        public void appendAll(int[] iArr) {
            for (int i : iArr) {
                this.users = ArrayUtils.appendInt(this.users, i);
            }
            invalidateIsUserUnlockedCache();
        }

        public void remove(int i) {
            this.users = ArrayUtils.removeInt(this.users, i);
            invalidateIsUserUnlockedCache();
        }

        public boolean contains(int i) {
            return ArrayUtils.contains(this.users, i);
        }

        public String toString() {
            return Arrays.toString(this.users);
        }

        public final void invalidateIsUserUnlockedCache() {
            UserManager.invalidateIsUserUnlockedCache();
        }
    }

    public class PackageInstalledMap {
        public final boolean external;

        /* renamed from: id */
        public final String f1619id;

        public PackageInstalledMap(String str, boolean z) {
            this.f1619id = str;
            this.external = z;
        }

        public PackageInstalledMap(String str) {
            this.f1619id = str;
            this.external = true;
        }

        public boolean equals(Object obj) {
            if (obj instanceof PackageInstalledMap) {
                return this.f1619id.equals(((PackageInstalledMap) obj).f1619id);
            }
            return false;
        }

        public int hashCode() {
            return this.f1619id.hashCode();
        }
    }

    public final VolumeInfo findVolumeByIdOrThrow(String str) {
        synchronized (this.mLock) {
            VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.get(str);
            if (volumeInfo != null) {
                return volumeInfo;
            }
            throw new IllegalArgumentException("No volume found for ID " + str);
        }
    }

    public final String findVolumeIdForPathOrThrow(String str) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                String str2 = volumeInfo.path;
                if (str2 != null && str.startsWith(str2)) {
                    return volumeInfo.id;
                }
            }
            throw new IllegalArgumentException("No volume found for path " + str);
        }
    }

    public final VolumeRecord findRecordForPath(String str) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                String str2 = volumeInfo.path;
                if (str2 != null && str.startsWith(str2)) {
                    return (VolumeRecord) this.mRecords.get(volumeInfo.fsUuid);
                }
            }
            return null;
        }
    }

    public final String scrubPath(String str) {
        if (str.startsWith(Environment.getDataDirectory().getAbsolutePath())) {
            return "internal";
        }
        VolumeRecord findRecordForPath = findRecordForPath(str);
        if (findRecordForPath == null || findRecordForPath.createdMillis == 0) {
            return "unknown";
        }
        return "ext:" + ((int) ((System.currentTimeMillis() - findRecordForPath.createdMillis) / 604800000)) + "w";
    }

    public final VolumeInfo findStorageForUuidAsUser(String str, int i) {
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(StorageManager.class);
        if (Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, str)) {
            return storageManager.findVolumeById("emulated;" + i);
        }
        if ("primary_physical".equals(str)) {
            return storageManager.getPrimaryPhysicalVolume();
        }
        VolumeInfo findVolumeByUuid = storageManager.findVolumeByUuid(str);
        if (findVolumeByUuid == null) {
            Slog.w("StorageManagerService", "findStorageForUuidAsUser cannot find volumeUuid:" + str);
            return null;
        }
        return storageManager.findVolumeById(findVolumeByUuid.getId().replace("private", "emulated") + KnoxVpnFirewallHelper.DELIMITER + i);
    }

    public final CountDownLatch findOrCreateDiskScanLatch(String str) {
        CountDownLatch countDownLatch;
        synchronized (this.mLock) {
            countDownLatch = (CountDownLatch) this.mDiskScanLatches.get(str);
            if (countDownLatch == null) {
                countDownLatch = new CountDownLatch(1);
                this.mDiskScanLatches.put(str, countDownLatch);
            }
        }
        return countDownLatch;
    }

    public class ObbState implements IBinder.DeathRecipient {
        public final String canonicalPath;
        public final int nonce;
        public final int ownerGid;
        public final String rawPath;
        public final IObbActionListener token;
        public String volId;

        public ObbState(String str, String str2, int i, IObbActionListener iObbActionListener, int i2, String str3) {
            this.rawPath = str;
            this.canonicalPath = str2;
            this.ownerGid = UserHandle.getSharedAppGid(i);
            this.token = iObbActionListener;
            this.nonce = i2;
            this.volId = str3;
        }

        public IBinder getBinder() {
            return this.token.asBinder();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            StorageManagerService.this.mObbActionHandler.sendMessage(StorageManagerService.this.mObbActionHandler.obtainMessage(1, StorageManagerService.this.new UnmountObbAction(this, true)));
        }

        public void link() {
            getBinder().linkToDeath(this, 0);
        }

        public void unlink() {
            getBinder().unlinkToDeath(this, 0);
        }

        public String toString() {
            return "ObbState{rawPath=" + this.rawPath + ",canonicalPath=" + this.canonicalPath + ",ownerGid=" + this.ownerGid + ",token=" + this.token + ",binder=" + getBinder() + ",volId=" + this.volId + '}';
        }
    }

    public class StorageManagerServiceHandler extends Handler {
        public StorageManagerServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            switch (message.what) {
                case 1:
                    Slog.d("StorageManagerService", "handleMessage -> H_SYSTEM_READY");
                    StorageManagerService.this.handleSystemReady();
                    break;
                case 2:
                    Slog.d("StorageManagerService", "handleMessage -> H_DAEMON_CONNECTED");
                    StorageManagerService.this.handleDaemonConnected();
                    break;
                case 3:
                    Slog.d("StorageManagerService", "handleMessage -> H_SHUTDOWN");
                    IStorageShutdownObserver iStorageShutdownObserver = (IStorageShutdownObserver) message.obj;
                    try {
                        StorageManagerService.this.mVold.shutdown();
                        z = true;
                    } catch (Exception e) {
                        Slog.wtf("StorageManagerService", e);
                        z = false;
                    }
                    if (iStorageShutdownObserver != null) {
                        try {
                            iStorageShutdownObserver.onShutDownComplete(z ? 0 : -1);
                            break;
                        } catch (Exception unused) {
                            return;
                        }
                    }
                    break;
                case 4:
                    Slog.d("StorageManagerService", "handleMessage -> H_FSTRIM");
                    Slog.i("StorageManagerService", "Running fstrim idle maintenance");
                    try {
                        StorageManagerService.this.mLastMaintenance = System.currentTimeMillis();
                        StorageManagerService.this.mLastMaintenanceFile.setLastModified(StorageManagerService.this.mLastMaintenance);
                    } catch (Exception unused2) {
                        Slog.e("StorageManagerService", "Unable to record last fstrim!");
                    }
                    StorageManagerService.this.fstrim(0, null);
                    Runnable runnable = (Runnable) message.obj;
                    if (runnable != null) {
                        runnable.run();
                        break;
                    }
                    break;
                case 5:
                    Slog.d("StorageManagerService", "handleMessage -> H_VOLUME_MOUNT");
                    VolumeInfo volumeInfo = (VolumeInfo) message.obj;
                    if (StorageManagerService.this.isMountDisallowed(volumeInfo)) {
                        Slog.i("StorageManagerService", "Ignoring mount " + volumeInfo.getId() + " due to policy");
                        break;
                    } else {
                        StorageManagerService.this.mount(volumeInfo);
                        break;
                    }
                case 6:
                    Slog.d("StorageManagerService", "handleMessage -> H_VOLUME_BROADCAST");
                    StorageVolume storageVolume = (StorageVolume) message.obj;
                    String state = storageVolume.getState();
                    Slog.d("StorageManagerService", "Volume " + storageVolume.getId() + " broadcasting " + state + " to " + storageVolume.getOwner());
                    String broadcastForEnvironment = VolumeInfo.getBroadcastForEnvironment(state);
                    if (broadcastForEnvironment != null) {
                        Intent intent = new Intent(broadcastForEnvironment, Uri.fromFile(storageVolume.getPathFile()));
                        intent.putExtra("android.os.storage.extra.STORAGE_VOLUME", storageVolume);
                        intent.addFlags(83886080);
                        StorageManagerService.this.mContext.sendBroadcastAsUser(intent, storageVolume.getOwner());
                        break;
                    }
                    break;
                case 7:
                    Slog.d("StorageManagerService", "handleMessage -> H_INTERNAL_BROADCAST");
                    Intent intent2 = (Intent) message.obj;
                    Slog.d("StorageManagerService", "broadcasting (" + intent2 + ") state=" + intent2.getIntExtra("android.os.storage.extra.VOLUME_STATE", -1) + ", fsUuid=" + intent2.getStringExtra("android.os.storage.extra.FS_UUID"));
                    StorageManagerService.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL, "android.permission.WRITE_MEDIA_STORAGE");
                    break;
                case 8:
                    Slog.d("StorageManagerService", "handleMessage -> H_VOLUME_UNMOUNT");
                    StorageManagerService.this.unmount((VolumeInfo) message.obj);
                    break;
                case 9:
                    Slog.d("StorageManagerService", "handleMessage -> H_PARTITION_FORGET rec=" + ((VolumeRecord) message.obj));
                    VolumeRecord volumeRecord = (VolumeRecord) message.obj;
                    StorageManagerService.this.forgetPartition(volumeRecord.partGuid, volumeRecord.fsUuid);
                    break;
                case 10:
                    Slog.d("StorageManagerService", "handleMessage -> H_RESET");
                    StorageManagerService.this.resetIfBootedAndConnected();
                    break;
                case 11:
                    Slog.d("StorageManagerService", "handleMessage -> H_RUN_IDLE_MAINT");
                    Slog.i("StorageManagerService", "Running idle maintenance");
                    StorageManagerService.this.runIdleMaint((Runnable) message.obj);
                    break;
                case 12:
                    Slog.d("StorageManagerService", "handleMessage -> H_ABORT_IDLE_MAINT");
                    Slog.i("StorageManagerService", "Aborting idle maintenance");
                    StorageManagerService.this.abortIdleMaint((Runnable) message.obj);
                    break;
                case 13:
                    Slog.d("StorageManagerService", "handleMessage -> H_BOOT_COMPLETED");
                    StorageManagerService.this.handleBootCompleted();
                    break;
                case 14:
                    Slog.d("StorageManagerService", "handleMessage -> H_COMPLETE_UNLOCK_USER");
                    StorageManagerService.this.completeUnlockUser(message.arg1);
                    break;
                case 15:
                    Slog.d("StorageManagerService", "handleMessage -> H_VOLUME_STATE_CHANGED");
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    StorageManagerService.this.onVolumeStateChangedAsync((VolumeInfo) someArgs.arg1, someArgs.argi1, someArgs.argi2);
                    someArgs.recycle();
                    break;
                case 16:
                    Object obj = message.obj;
                    if (obj instanceof StorageManagerInternal.CloudProviderChangeListener) {
                        StorageManagerService.this.notifyCloudMediaProviderChangedAsync((StorageManagerInternal.CloudProviderChangeListener) obj);
                        break;
                    } else {
                        StorageManagerService.this.onCloudMediaProviderChangedAsync(message.arg1, (String) obj);
                        break;
                    }
                case 17:
                    Slog.d("StorageManagerService", "handleMessage -> H_SECURE_KEYGUARD_STATE_CHANGED isShowing=" + ((Boolean) message.obj).booleanValue());
                    try {
                        StorageManagerService.this.mVold.onSecureKeyguardStateChanged(((Boolean) message.obj).booleanValue());
                        break;
                    } catch (Exception e2) {
                        Slog.wtf("StorageManagerService", e2);
                        return;
                    }
            }
        }
    }

    public void waitForAsecScan() {
        waitForLatch(this.mAsecsScanned, "mAsecsScanned");
    }

    public final void waitForLatch(CountDownLatch countDownLatch, String str) {
        try {
            waitForLatch(countDownLatch, str, -1L);
        } catch (TimeoutException e) {
            Slog.e("StorageManagerService", "TimeoutException in " + str, e);
        }
    }

    public final void waitForLatch(CountDownLatch countDownLatch, String str, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        while (!countDownLatch.await(5000L, TimeUnit.MILLISECONDS)) {
            try {
                Slog.w("StorageManagerService", "Thread " + Thread.currentThread().getName() + " still waiting for " + str + "...");
            } catch (InterruptedException unused) {
                Slog.w("StorageManagerService", "Interrupt while waiting for " + str);
            }
            if (j > 0 && SystemClock.elapsedRealtime() > elapsedRealtime + j) {
                throw new TimeoutException("Thread " + Thread.currentThread().getName() + " gave up waiting for " + str + " after " + j + "ms");
            }
        }
    }

    public final void handleSystemReady() {
        if (prepareSmartIdleMaint()) {
            SmartStorageMaintIdler.scheduleSmartIdlePass(this.mContext, sSmartIdleMaintPeriod);
        }
        MountServiceIdler.scheduleIdlePass(this.mContext);
        NandswapManager.scheduleNandswapManager(this.mContext);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("zram_enabled"), false, new ContentObserver(null) { // from class: com.android.server.StorageManagerService.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                StorageManagerService.this.refreshZramSettings();
            }
        });
        refreshZramSettings();
        if (!SystemProperties.get("persist.sys.zram_enabled").equals("0") && this.mContext.getResources().getBoolean(R.bool.config_useAssistantVolume)) {
            ZramWriteback.scheduleZramWriteback(this.mContext);
        }
        configureTranscoding();
    }

    public final void refreshZramSettings() {
        String str = SystemProperties.get("persist.sys.zram_enabled");
        if ("".equals(str)) {
            return;
        }
        String str2 = Settings.Global.getInt(this.mContext.getContentResolver(), "zram_enabled", 1) != 0 ? "1" : "0";
        if (str2.equals(str)) {
            return;
        }
        SystemProperties.set("persist.sys.zram_enabled", str2);
        if (str2.equals("1") && this.mContext.getResources().getBoolean(R.bool.config_useAssistantVolume)) {
            ZramWriteback.scheduleZramWriteback(this.mContext);
        }
    }

    public final boolean isHevcDecoderSupported() {
        for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(0).getCodecInfos()) {
            if (!mediaCodecInfo.isEncoder()) {
                for (String str : mediaCodecInfo.getSupportedTypes()) {
                    if (str.equalsIgnoreCase("video/hevc")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void configureTranscoding() {
        boolean z;
        boolean isHevcDecoderSupported = isHevcDecoderSupported();
        if (SystemProperties.getBoolean("persist.sys.fuse.transcode_user_control", false)) {
            z = SystemProperties.getBoolean("persist.sys.fuse.transcode_enabled", isHevcDecoderSupported);
        } else {
            z = DeviceConfig.getBoolean("storage_native_boot", "transcode_enabled", isHevcDecoderSupported);
        }
        SystemProperties.set("sys.fuse.transcode_enabled", String.valueOf(z));
        if (z) {
            ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).registerAnrController(new ExternalStorageServiceAnrController());
        }
    }

    public class ExternalStorageServiceAnrController implements AnrController {
        public ExternalStorageServiceAnrController() {
        }

        public long getAnrDelayMillis(String str, int i) {
            if (!StorageManagerService.this.isAppIoBlocked(i)) {
                return 0L;
            }
            int i2 = DeviceConfig.getInt("storage_native_boot", "anr_delay_millis", 5000);
            Slog.v("StorageManagerService", "getAnrDelayMillis for " + str + ". " + i2 + "ms");
            return i2;
        }

        public void onAnrDelayStarted(String str, int i) {
            if (StorageManagerService.this.isAppIoBlocked(i) && DeviceConfig.getBoolean("storage_native_boot", "anr_delay_notify_external_storage_service", true)) {
                Slog.d("StorageManagerService", "onAnrDelayStarted for " + str + ". Notifying external storage service");
                try {
                    StorageManagerService.this.mStorageSessionController.notifyAnrDelayStarted(str, i, 0, 1);
                } catch (StorageSessionController.ExternalStorageServiceException e) {
                    Slog.e("StorageManagerService", "Failed to notify ANR delay started for " + str, e);
                }
            }
        }

        public boolean onAnrDelayCompleted(String str, int i) {
            if (StorageManagerService.this.isAppIoBlocked(i)) {
                Slog.d("StorageManagerService", "onAnrDelayCompleted for " + str + ". Showing ANR dialog...");
                return true;
            }
            Slog.d("StorageManagerService", "onAnrDelayCompleted for " + str + ". Skipping ANR dialog...");
            return false;
        }
    }

    public final void addInternalVolumeLocked() {
        VolumeInfo volumeInfo = new VolumeInfo("private", 1, (DiskInfo) null, (String) null);
        volumeInfo.state = 2;
        volumeInfo.path = Environment.getDataDirectory().getAbsolutePath();
        this.mVolumes.put(volumeInfo.id, volumeInfo);
    }

    public final void resetIfBootedAndConnected() {
        int[] copyOf;
        Slog.d("StorageManagerService", "Thinking about reset, mBootCompleted=" + this.mBootCompleted + ", mDaemonConnected=" + this.mDaemonConnected);
        if (this.mBootCompleted && this.mDaemonConnected) {
            UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
            List<UserInfo> users = userManager.getUsers();
            this.mStorageSessionController.onReset(this.mVold, new Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManagerService.this.lambda$resetIfBootedAndConnected$0();
                }
            });
            synchronized (this.mLock) {
                int[] iArr = this.mSystemUnlockedUsers;
                copyOf = Arrays.copyOf(iArr, iArr.length);
                this.mDisks.clear();
                this.mVolumes.clear();
                addInternalVolumeLocked();
            }
            try {
                Slog.i("StorageManagerService", "Resetting vold...");
                this.mVold.reset();
                Slog.i("StorageManagerService", "Reset vold");
                cleanupSercureContainerList("external");
                for (UserInfo userInfo : users) {
                    if (userInfo.isCloneProfile()) {
                        this.mVold.onUserAdded(userInfo.id, userInfo.serialNumber, userInfo.profileGroupId);
                    } else {
                        this.mVold.onUserAdded(userInfo.id, userInfo.serialNumber, -1);
                    }
                }
                for (int i : copyOf) {
                    this.mVold.onUserStarted(i);
                    this.mStoraged.onUserStarted(i);
                }
                restoreSystemUnlockedUsers(userManager, users, copyOf);
                this.mVold.onSecureKeyguardStateChanged(this.mSecureKeyguardShowing);
                this.mStorageManagerInternal.onReset(this.mVold);
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetIfBootedAndConnected$0() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public final void restoreSystemUnlockedUsers(UserManager userManager, List list, int[] iArr) {
        Arrays.sort(iArr);
        UserManager.invalidateIsUserUnlockedCache();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int i = ((UserInfo) it.next()).id;
            if (userManager.isUserRunning(i) && Arrays.binarySearch(iArr, i) < 0 && userManager.isUserUnlockingOrUnlocked(i)) {
                Slog.w("StorageManagerService", "UNLOCK_USER lost from vold reset, will retry, user:" + i);
                this.mVold.onUserStarted(i);
                this.mStoraged.onUserStarted(i);
                this.mHandler.obtainMessage(14, i, 0).sendToTarget();
            }
        }
    }

    public final void restoreLocalUnlockedUsers() {
        try {
            int[] unlockedUsers = this.mVold.getUnlockedUsers();
            if (ArrayUtils.isEmpty(unlockedUsers)) {
                return;
            }
            Slog.d("StorageManagerService", "CE storage for users " + Arrays.toString(unlockedUsers) + " is already unlocked");
            synchronized (this.mLock) {
                this.mLocalUnlockedUsers.appendAll(unlockedUsers);
            }
        } catch (Exception e) {
            Slog.e("StorageManagerService", "Failed to get unlocked users from vold", e);
        }
    }

    public final void onUnlockUser(int i) {
        Slog.d("StorageManagerService", "onUnlockUser: userId=" + i);
        if (i != 0) {
            try {
                UserManager userManager = (UserManager) this.mContext.createPackageContextAsUser("system", 0, UserHandle.of(i)).getSystemService(UserManager.class);
                if (userManager != null) {
                    this.mIsMediaSharedWithParent.put(Integer.valueOf(i), Boolean.valueOf(userManager.isMediaSharedWithParent()));
                } else {
                    Slog.d("StorageManagerService", "onUnlockUser: um is null, so just put userId(" + i + ") with false");
                    this.mIsMediaSharedWithParent.put(Integer.valueOf(i), Boolean.FALSE);
                }
                if (userManager != null && userManager.isMediaSharedWithParent()) {
                    int i2 = userManager.getProfileParent(i).id;
                    this.mUserSharesMediaWith.put(i, i2);
                    this.mUserSharesMediaWith.put(i2, i);
                } else if (SemDualAppManager.isDualAppId(i)) {
                    this.mUserSharesMediaWith.put(i, 0);
                    this.mUserSharesMediaWith.put(0, i);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("StorageManagerService", "Failed to create user context for user " + i);
            }
        }
        try {
            this.mStorageSessionController.onUnlockUser(i);
            this.mVold.onUserStarted(i);
            this.mStoraged.onUserStarted(i);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
        if (SemSdCardEncryption.isEncryptionFeatureEnabled() && i == 0) {
            initDirEncryptService();
        }
        this.mHandler.obtainMessage(14, i, 0).sendToTarget();
        if (this.mRemountCurrentUserVolumesOnUnlock && i == this.mCurrentUserId) {
            maybeRemountVolumes(i);
            this.mRemountCurrentUserVolumesOnUnlock = false;
        }
    }

    public final void completeUnlockUser(int i) {
        Slog.d("StorageManagerService", "completeUnlockUser: userId=" + i);
        onKeyguardStateChanged(false);
        synchronized (this.mLock) {
            for (int i2 : this.mSystemUnlockedUsers) {
                if (i2 == i) {
                    Log.i("StorageManagerService", "completeUnlockUser called for already unlocked user:" + i);
                    return;
                }
            }
            for (int i3 = 0; i3 < this.mVolumes.size(); i3++) {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i3);
                if (volumeInfo.isVisibleForUser(i) && volumeInfo.isMountedReadable()) {
                    StorageVolume buildStorageVolume = volumeInfo.buildStorageVolume(this.mContext, i, false);
                    this.mHandler.obtainMessage(6, buildStorageVolume).sendToTarget();
                    String environmentForState = VolumeInfo.getEnvironmentForState(volumeInfo.getState());
                    this.mCallbacks.notifyStorageStateChanged(buildStorageVolume.getPath(), environmentForState, environmentForState);
                }
            }
            this.mSystemUnlockedUsers = ArrayUtils.appendInt(this.mSystemUnlockedUsers, i);
        }
    }

    public final void onCleanupUser(int i) {
        Slog.d("StorageManagerService", "onCleanupUser " + i);
        try {
            this.mVold.onUserStopped(i);
            this.mStoraged.onUserStopped(i);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
        synchronized (this.mLock) {
            this.mSystemUnlockedUsers = ArrayUtils.removeInt(this.mSystemUnlockedUsers, i);
        }
    }

    public final void onStopUser(int i) {
        Slog.i("StorageManagerService", "onStopUser " + i);
        try {
            this.mStorageSessionController.onUserStopping(i);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
        PackageMonitor packageMonitor = (PackageMonitor) this.mPackageMonitorsForUser.removeReturnOld(i);
        if (packageMonitor != null) {
            packageMonitor.unregister();
        }
    }

    public final String createVolumeInfoStrForPulbicVolume(VolumeInfo volumeInfo) {
        String valueToString = DebugUtils.valueToString(VolumeInfo.class, "TYPE_", volumeInfo.type);
        String diskId = volumeInfo.getDiskId() != null ? volumeInfo.getDiskId() : "null";
        String str = volumeInfo.partGuid;
        if (str == null) {
            str = "";
        }
        String flagsToString = DebugUtils.flagsToString(VolumeInfo.class, "MOUNT_FLAG_", volumeInfo.mountFlags);
        String valueToString2 = DebugUtils.valueToString(VolumeInfo.class, "STATE_", volumeInfo.state);
        return ((((("VolumeInfo" + String.format("{%s}:", volumeInfo.id)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + String.format("type=%s diskId=%s partGuid=%s", valueToString, diskId, str)) + String.format(" mountFlags=%s mountUserId=%d state=%s", flagsToString, Integer.valueOf(volumeInfo.getMountUserId()), valueToString2)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + String.format(" fsType=%s fsUuid=%s path=%s internalPath=%s", volumeInfo.fsType, volumeInfo.fsUuid, volumeInfo.path, volumeInfo.internalPath);
    }

    public final void maybeRemountVolumes(int i) {
        ArrayList<VolumeInfo> arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i2);
                if (!volumeInfo.isPrimary() && volumeInfo.isMountedWritable() && volumeInfo.isVisible() && volumeInfo.getMountUserId() != this.mCurrentUserId) {
                    volumeInfo.mountUserId = this.mCurrentUserId;
                    arrayList.add(volumeInfo);
                }
            }
        }
        for (VolumeInfo volumeInfo2 : arrayList) {
            Slog.i("StorageManagerService", "Remounting volume for user: " + i + ". Volume: " + createVolumeInfoStrForPulbicVolume(volumeInfo2));
            this.mHandler.obtainMessage(8, volumeInfo2).sendToTarget();
            this.mHandler.obtainMessage(5, volumeInfo2).sendToTarget();
        }
    }

    public final boolean supportsBlockCheckpoint() {
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        return this.mVold.supportsBlockCheckpoint();
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onKeyguardStateChanged(boolean z) {
        boolean z2 = z && ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(this.mCurrentUserId);
        if (this.mSecureKeyguardShowing != z2) {
            this.mSecureKeyguardShowing = z2;
            this.mHandler.obtainMessage(17, Boolean.valueOf(this.mSecureKeyguardShowing)).sendToTarget();
        }
    }

    public void runIdleMaintenance(Runnable runnable) {
        StorageManagerServiceHandler storageManagerServiceHandler = this.mHandler;
        storageManagerServiceHandler.sendMessage(storageManagerServiceHandler.obtainMessage(4, runnable));
    }

    public void runMaintenance() {
        super.runMaintenance_enforcePermission();
        runIdleMaintenance(null);
    }

    public long lastMaintenance() {
        return this.mLastMaintenance;
    }

    public void onDaemonConnected() {
        this.mDaemonConnected = true;
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    public final void handleDaemonConnected() {
        resetIfBootedAndConnected();
    }

    public final boolean isExtSDCardTrayRemoved() {
        try {
            File file = new File("/sys/class/sec/sdcard/status");
            if (file.exists()) {
                if (FileUtils.readTextFile(file, 0, null).trim().equals("Notray")) {
                    return true;
                }
                Slog.d("StorageManagerService", "Ext SD Card Tray State is not proper");
                return false;
            }
            Slog.d("StorageManagerService", "Ext SD Card Tray State File Not Exist");
            return true;
        } catch (Exception e) {
            Slog.e("StorageManagerService", "Could't read Ext SD Card Tray State File : ", e);
            return false;
        }
    }

    public final void onDiskScannedLocked(DiskInfo diskInfo) {
        int i = 0;
        for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
            if (Objects.equals(diskInfo.id, ((VolumeInfo) this.mVolumes.valueAt(i2)).getDiskId())) {
                i++;
            }
        }
        int i3 = diskInfo.flags;
        String str = (i3 & 4) != 0 ? "SD" : (i3 & 8) != 0 ? "USB" : "null";
        Slog.d("StorageManagerService", "onDiskScannedLocked: disk=" + (((((("DiskInfo" + String.format("{%s}:", diskInfo.id)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + String.format("  flags=%s size=%d volumeCount=%d", str, Long.valueOf(diskInfo.size), Integer.valueOf(diskInfo.volumeCount))) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + String.format("  sysPath=%s", diskInfo.sysPath)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + " volumeCount=" + i);
        Intent intent = new Intent("android.os.storage.action.DISK_SCANNED");
        intent.addFlags(83886080);
        intent.putExtra("android.os.storage.extra.DISK_ID", diskInfo.id);
        intent.putExtra("android.os.storage.extra.VOLUME_COUNT", i);
        this.mHandler.obtainMessage(7, intent).sendToTarget();
        CountDownLatch countDownLatch = (CountDownLatch) this.mDiskScanLatches.remove(diskInfo.id);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
        diskInfo.volumeCount = i;
        this.mCallbacks.notifyDiskScanned(diskInfo, i);
    }

    public final void onVolumeCreatedLocked(VolumeInfo volumeInfo) {
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        int i = volumeInfo.mountUserId;
        if (i >= 0 && !activityManagerInternal.isUserRunning(i, 0)) {
            Slog.d("StorageManagerService", "Ignoring volume " + volumeInfo.getId() + " because user " + Integer.toString(volumeInfo.mountUserId) + " is no longer running.");
            return;
        }
        int i2 = volumeInfo.type;
        if (i2 == 2) {
            Context createContextAsUser = this.mContext.createContextAsUser(UserHandle.of(volumeInfo.mountUserId), 0);
            if (!(createContextAsUser != null ? ((UserManager) createContextAsUser.getSystemService(UserManager.class)).isMediaSharedWithParent() : false) && !this.mStorageSessionController.supportsExternalStorage(volumeInfo.mountUserId)) {
                Slog.d("StorageManagerService", "Ignoring volume " + volumeInfo.getId() + " because user " + Integer.toString(volumeInfo.mountUserId) + " does not support external storage.");
                return;
            }
            VolumeInfo findPrivateForEmulated = ((StorageManager) this.mContext.getSystemService(StorageManager.class)).findPrivateForEmulated(volumeInfo);
            if ((Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, this.mPrimaryStorageUuid) && "private".equals(findPrivateForEmulated.id)) || Objects.equals(findPrivateForEmulated.fsUuid, this.mPrimaryStorageUuid)) {
                Slog.v("StorageManagerService", "Found primary storage at #1 " + volumeInfo);
                volumeInfo.mountFlags = volumeInfo.mountFlags | 1 | 4;
                this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
                return;
            }
            return;
        }
        if (i2 == 0) {
            if ("primary_physical".equals(this.mPrimaryStorageUuid) && volumeInfo.disk.isDefaultPrimary()) {
                Slog.v("StorageManagerService", "Found primary storage at #2 " + volumeInfo);
                volumeInfo.mountFlags = volumeInfo.mountFlags | 1 | 4;
            }
            if (volumeInfo.disk.isAdoptable() && volumeInfo.disk.isSd()) {
                volumeInfo.mountFlags |= 4;
            }
            volumeInfo.mountUserId = this.mCurrentUserId;
            this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        if (i2 == 1) {
            this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        if (i2 == 5) {
            if (volumeInfo.disk.isStubVisible()) {
                volumeInfo.mountFlags |= 4;
            } else {
                volumeInfo.mountFlags |= 2;
            }
            volumeInfo.mountUserId = this.mCurrentUserId;
            this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        Slog.d("StorageManagerService", "Skipping automatic mounting of " + volumeInfo);
    }

    public final boolean isBroadcastWorthy(VolumeInfo volumeInfo) {
        int type = volumeInfo.getType();
        if (type != 0 && type != 1 && type != 2 && type != 5) {
            return false;
        }
        int state = volumeInfo.getState();
        if (state != 0 && state != 2 && state != 3 && state != 5 && state != 6) {
            if (state == 7) {
                DiskInfo disk = volumeInfo.getDisk();
                if (disk == null || !disk.isUsb()) {
                    return false;
                }
            } else if (state != 8) {
                return false;
            }
        }
        return true;
    }

    public final void onVolumeStateChangedLocked(final VolumeInfo volumeInfo, int i) {
        if (volumeInfo.type == 2) {
            if (i != 2) {
                this.mFuseMountedUser.remove(Integer.valueOf(volumeInfo.getMountUserId()));
            } else if (this.mVoldAppDataIsolationEnabled) {
                final int mountUserId = volumeInfo.getMountUserId();
                new Thread(new Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        StorageManagerService.this.lambda$onVolumeStateChangedLocked$1(mountUserId, volumeInfo);
                    }
                }).start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVolumeStateChangedLocked$1(int i, VolumeInfo volumeInfo) {
        Map map;
        if (i == 0 && Build.VERSION.DEVICE_INITIAL_SDK_INT < 29) {
            this.mPmInternal.migrateLegacyObbData();
        }
        synchronized (this.mLock) {
            this.mFuseMountedUser.add(Integer.valueOf(i));
        }
        int i2 = 0;
        while (true) {
            if (i2 >= 5) {
                map = null;
                break;
            }
            try {
                map = ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getProcessesWithPendingBindMounts(volumeInfo.getMountUserId());
                break;
            } catch (IllegalStateException unused) {
                Slog.i("StorageManagerService", "Some processes are starting, retry");
                SystemClock.sleep(100L);
                i2++;
            }
        }
        if (map != null) {
            remountAppStorageDirs(map, i);
        } else {
            Slog.wtf("StorageManagerService", "Not able to getStorageNotOptimizedProcesses() after 5 retries");
        }
    }

    public final void onVolumeStateChangedAsync(VolumeInfo volumeInfo, int i, int i2) {
        if (i2 == 2) {
            try {
                try {
                    prepareUserStorageIfNeeded(volumeInfo);
                } catch (Exception e) {
                    Slog.wtf("StorageManagerService", e);
                    return;
                }
            } catch (Exception unused) {
                this.mVold.unmount(volumeInfo.id);
                return;
            }
        }
        synchronized (this.mLock) {
            if (!TextUtils.isEmpty(volumeInfo.fsUuid)) {
                VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.get(volumeInfo.fsUuid);
                if (volumeRecord == null) {
                    volumeRecord = new VolumeRecord(volumeInfo.type, volumeInfo.fsUuid);
                    volumeRecord.partGuid = volumeInfo.partGuid;
                    volumeRecord.createdMillis = System.currentTimeMillis();
                    if (volumeInfo.type == 1) {
                        volumeRecord.nickname = volumeInfo.disk.getDescription();
                    }
                    this.mRecords.put(volumeRecord.fsUuid, volumeRecord);
                } else if (TextUtils.isEmpty(volumeRecord.partGuid)) {
                    volumeRecord.partGuid = volumeInfo.partGuid;
                }
                volumeRecord.lastSeenMillis = System.currentTimeMillis();
                writeSettingsLocked();
            }
        }
        try {
            this.mStorageSessionController.notifyVolumeStateChanged(volumeInfo);
        } catch (StorageSessionController.ExternalStorageServiceException e2) {
            Log.e("StorageManagerService", "Failed to notify volume state changed to the Storage Service", e2);
        }
        synchronized (this.mLock) {
            this.mCallbacks.notifyVolumeStateChanged(volumeInfo, i, i2);
            if (this.mBootCompleted && isBroadcastWorthy(volumeInfo)) {
                Intent intent = new Intent("android.os.storage.action.VOLUME_STATE_CHANGED");
                intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.id);
                intent.putExtra("android.os.storage.extra.VOLUME_STATE", i2);
                intent.putExtra("android.os.storage.extra.FS_UUID", volumeInfo.fsUuid);
                intent.addFlags(83886080);
                this.mHandler.obtainMessage(7, intent).sendToTarget();
            }
            String environmentForState = VolumeInfo.getEnvironmentForState(i);
            String environmentForState2 = VolumeInfo.getEnvironmentForState(i2);
            if (!Objects.equals(environmentForState, environmentForState2)) {
                for (int i3 : this.mSystemUnlockedUsers) {
                    if (volumeInfo.isVisibleForUser(i3)) {
                        StorageVolume buildStorageVolume = volumeInfo.buildStorageVolume(this.mContext, i3, false);
                        this.mHandler.obtainMessage(6, buildStorageVolume).sendToTarget();
                        this.mCallbacks.notifyStorageStateChanged(buildStorageVolume.getPath(), environmentForState, environmentForState2);
                    }
                }
            }
            if (volumeInfo.type == 0) {
                DiskInfo disk = volumeInfo.getDisk();
                if (disk != null && disk.isSd()) {
                    if ("mounted".equals(environmentForState2)) {
                        String str = volumeInfo.fsType;
                        Slog.d("StorageManagerService", "Set Ext SD Card FsType Property by Ext SD Card Info");
                        SystemProperties.set("sys.ext_sdcard.fstype", str);
                    } else {
                        Slog.d("StorageManagerService", "Set Ext SD Card FsType Property to default");
                        SystemProperties.set("sys.ext_sdcard.fstype", "none");
                    }
                } else if (disk != null && disk.isUsb()) {
                    if ("mounted".equals(environmentForState2)) {
                        String str2 = volumeInfo.fsType;
                        Slog.d("StorageManagerService", "Set USB Storage FsType Property by USB Storage Info");
                        SystemProperties.set("sys.usb_storage.fstype", str2);
                    } else {
                        Slog.d("StorageManagerService", "Set USB Storage FsType Property to default");
                        SystemProperties.set("sys.usb_storage.fstype", "none");
                    }
                }
            }
            int i4 = volumeInfo.type;
            if ((i4 == 0 || i4 == 5) && volumeInfo.state == 5) {
                ObbActionHandler obbActionHandler = this.mObbActionHandler;
                obbActionHandler.sendMessage(obbActionHandler.obtainMessage(2, volumeInfo.path));
            }
            maybeLogMediaMount(volumeInfo, i2);
        }
    }

    public final void notifyCloudMediaProviderChangedAsync(StorageManagerInternal.CloudProviderChangeListener cloudProviderChangeListener) {
        synchronized (this.mCloudMediaProviders) {
            for (int size = this.mCloudMediaProviders.size() - 1; size >= 0; size--) {
                cloudProviderChangeListener.onCloudProviderChanged(this.mCloudMediaProviders.keyAt(size), (String) this.mCloudMediaProviders.valueAt(size));
            }
        }
    }

    public final void onCloudMediaProviderChangedAsync(int i, String str) {
        Iterator it = this.mStorageManagerInternal.mCloudProviderChangeListeners.iterator();
        while (it.hasNext()) {
            ((StorageManagerInternal.CloudProviderChangeListener) it.next()).onCloudProviderChanged(i, str);
        }
    }

    public final void maybeLogMediaMount(VolumeInfo volumeInfo, int i) {
        DiskInfo disk;
        if (!SecurityLog.isLoggingEnabled() || (disk = volumeInfo.getDisk()) == null || (disk.flags & 12) == 0) {
            return;
        }
        String str = disk.label;
        String trim = str != null ? str.trim() : "";
        if (i == 2 || i == 3) {
            SecurityLog.writeEvent(210013, new Object[]{volumeInfo.path, trim});
        } else if (i == 0 || i == 8) {
            SecurityLog.writeEvent(210014, new Object[]{volumeInfo.path, trim});
        }
    }

    public final void onMoveStatusLocked(int i) {
        IPackageMoveObserver iPackageMoveObserver = this.mMoveCallback;
        if (iPackageMoveObserver == null) {
            Slog.w("StorageManagerService", "Odd, status but no move requested");
            return;
        }
        try {
            iPackageMoveObserver.onStatusChanged(-1, i, -1L);
        } catch (RemoteException unused) {
        }
        if (i == 82) {
            Slog.d("StorageManagerService", "Move to " + this.mMoveTargetUuid + " copy phase finshed; persisting");
            this.mPrimaryStorageUuid = this.mMoveTargetUuid;
            writeSettingsLocked();
        }
        if (PackageManager.isMoveStatusFinished(i)) {
            Slog.d("StorageManagerService", "Move to " + this.mMoveTargetUuid + " finished with status " + i);
            this.mMoveCallback = null;
            this.mMoveTargetUuid = null;
        }
    }

    public final void enforcePermission(String str) {
        this.mContext.enforceCallingOrSelfPermission(str, str);
    }

    public final boolean isMountDisallowed(VolumeInfo volumeInfo) {
        IRestrictionPolicy asInterface;
        ContextInfo contextInfo;
        DiskInfo diskInfo;
        if (volumeInfo.getType() == 2) {
            return false;
        }
        if (isMountDisallowedByEAS(false)) {
            Slog.w("StorageManagerService", "Policy has restriction 'storage_itpolicy_ui'; cannot mount volume.");
            return true;
        }
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        try {
            asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
            contextInfo = new ContextInfo();
            diskInfo = volumeInfo.disk;
        } catch (RemoteException unused) {
        }
        if (diskInfo != null && diskInfo.isSd() && asInterface != null && !asInterface.isSdCardEnabled(contextInfo)) {
            Slog.d("StorageManagerService", "SDcard is restricted in MDM");
            this.mContext.sendBroadcastAsUser(new Intent("com.samsung.intent.action.SDCARD_ITPOLICY_TOAST_EVENT"), UserHandle.CURRENT);
            return true;
        }
        DiskInfo diskInfo2 = volumeInfo.disk;
        if (diskInfo2 != null && diskInfo2.isUsb() && asInterface != null && !asInterface.isUsbHostStorageAllowed(contextInfo, true)) {
            Slog.d("StorageManagerService", "USB MEMORY is restricted in MDM");
            return true;
        }
        DiskInfo diskInfo3 = volumeInfo.disk;
        boolean z = diskInfo3 != null && diskInfo3.isSd();
        DiskInfo diskInfo4 = volumeInfo.disk;
        boolean z2 = diskInfo4 != null && diskInfo4.isUsb();
        if (z && hasDeviceRestriction("sdcard_storage")) {
            Slog.w("StorageManagerService", "Policy has restriction 'sdcard'; cannot mount volume.");
            return true;
        }
        if (z2 && hasDeviceRestriction("usbhost_storage")) {
            Slog.w("StorageManagerService", "Policy has restriction 'usbhost_storage'; cannot mount volume.");
            return true;
        }
        DiskInfo diskInfo5 = volumeInfo.disk;
        boolean hasUserRestriction = (diskInfo5 == null || !diskInfo5.isUsb()) ? false : userManager.hasUserRestriction("no_usb_file_transfer", Binder.getCallingUserHandle());
        int i = volumeInfo.type;
        return hasUserRestriction || ((i == 0 || i == 1 || i == 5) ? userManager.hasUserRestriction("no_physical_media", Binder.getCallingUserHandle()) : false);
    }

    public final void enforceAdminUser() {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!userManager.getUserInfo(callingUserId).isAdmin()) {
                throw new SecurityException("Only admin users can adopt sd cards");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public StorageManagerService(Context context) {
        sSelf = this;
        this.mVoldAppDataIsolationEnabled = SystemProperties.getBoolean("persist.sys.vold_app_data_isolation_enabled", false);
        this.mContext = context;
        this.mCallbacks = new Callbacks(FgThread.get().getLooper());
        HandlerThread handlerThread = new HandlerThread("StorageManagerService");
        handlerThread.start();
        this.mHandler = new StorageManagerServiceHandler(handlerThread.getLooper());
        this.mObbActionHandler = new ObbActionHandler(IoThread.get().getLooper());
        this.mStorageSessionController = new StorageSessionController(context);
        Installer installer = new Installer(context);
        this.mInstaller = installer;
        installer.onStart();
        File file = new File(new File(Environment.getDataDirectory(), "system"), "last-fstrim");
        this.mLastMaintenanceFile = file;
        if (!file.exists()) {
            try {
                new FileOutputStream(file).close();
            } catch (IOException unused) {
                Slog.e("StorageManagerService", "Unable to create fstrim record " + this.mLastMaintenanceFile.getPath());
            }
        } else {
            this.mLastMaintenance = file.lastModified();
        }
        this.mSettingsFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), "storage.xml"), "storage-settings");
        this.mWriteRecordFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), "storage-write-records"));
        sSmartIdleMaintPeriod = DeviceConfig.getInt("storage_native_boot", "smart_idle_maint_period", 60);
        if (sSmartIdleMaintPeriod < 10) {
            sSmartIdleMaintPeriod = 10;
        } else if (sSmartIdleMaintPeriod > 1440) {
            sSmartIdleMaintPeriod = 1440;
        }
        this.mMaxWriteRecords = 4320 / sSmartIdleMaintPeriod;
        this.mStorageWriteRecords = new int[this.mMaxWriteRecords];
        synchronized (this.mLock) {
            readSettingsLocked();
        }
        LocalServices.addService(StorageManagerInternal.class, this.mStorageManagerInternal);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter, null, this.mHandler);
        synchronized (this.mLock) {
            addInternalVolumeLocked();
        }
        Watchdog.getInstance().addMonitor(this);
        this.mContext.registerReceiver(this.mRestartSdcardBadremoveReceiver, new IntentFilter("com.samsung.intent.action.RESTART_OF_SDCARDBADREMOVED_HASAPK"));
        this.mContext.registerReceiver(this.mPolicyReceiver, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), null, null);
    }

    public final void start() {
        lambda$connectStoraged$2();
        lambda$connectVold$3();
    }

    /* renamed from: connectStoraged, reason: merged with bridge method [inline-methods] */
    public final void lambda$connectStoraged$2() {
        IBinder service = ServiceManager.getService("storaged");
        if (service != null) {
            try {
                service.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.StorageManagerService.4
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        Slog.w("StorageManagerService", "storaged died; reconnecting");
                        StorageManagerService.this.mStoraged = null;
                        StorageManagerService.this.lambda$connectStoraged$2();
                    }
                }, 0);
            } catch (RemoteException unused) {
                service = null;
            }
        }
        if (service != null) {
            this.mStoraged = IStoraged.Stub.asInterface(service);
        } else {
            Slog.w("StorageManagerService", "storaged not found; trying again");
        }
        if (this.mStoraged == null) {
            BackgroundThread.getHandler().postDelayed(new Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManagerService.this.lambda$connectStoraged$2();
                }
            }, 1000L);
        } else {
            onDaemonConnected();
        }
    }

    /* renamed from: connectVold, reason: merged with bridge method [inline-methods] */
    public final void lambda$connectVold$3() {
        IBinder service = ServiceManager.getService("vold");
        if (service != null) {
            try {
                service.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.StorageManagerService.5
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        Slog.w("StorageManagerService", "vold died; reconnecting");
                        StorageManagerService.this.mVold = null;
                        StorageManagerService.this.lambda$connectVold$3();
                    }
                }, 0);
            } catch (RemoteException unused) {
                service = null;
            }
        }
        if (service != null) {
            this.mVold = IVold.Stub.asInterface(service);
            try {
                this.mVold.setListener(this.mListener);
            } catch (RemoteException e) {
                this.mVold = null;
                Slog.w("StorageManagerService", "vold listener rejected; trying again", e);
            }
        } else {
            Slog.w("StorageManagerService", "vold not found; trying again");
        }
        if (this.mVold == null) {
            BackgroundThread.getHandler().postDelayed(new Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    StorageManagerService.this.lambda$connectVold$3();
                }
            }, 1000L);
        } else {
            restoreLocalUnlockedUsers();
            onDaemonConnected();
        }
    }

    public final void servicesReady() {
        this.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mIPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        this.mIAppOpsService = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
        ProviderInfo providerInfo = getProviderInfo("media");
        if (providerInfo != null) {
            this.mMediaStoreAuthorityAppId = UserHandle.getAppId(providerInfo.applicationInfo.uid);
            sMediaStoreAuthorityProcessName = providerInfo.applicationInfo.processName;
        }
        ProviderInfo providerInfo2 = getProviderInfo("downloads");
        if (providerInfo2 != null) {
            this.mDownloadsAuthorityAppId = UserHandle.getAppId(providerInfo2.applicationInfo.uid);
        }
        ProviderInfo providerInfo3 = getProviderInfo("com.android.externalstorage.documents");
        if (providerInfo3 != null) {
            this.mExternalStorageAuthorityAppId = UserHandle.getAppId(providerInfo3.applicationInfo.uid);
        }
    }

    public final ProviderInfo getProviderInfo(String str) {
        return this.mPmInternal.resolveContentProvider(str, 786432L, UserHandle.getUserId(0), 1000);
    }

    public final void updateLegacyStorageApps(String str, int i, boolean z) {
        synchronized (this.mLock) {
            if (z) {
                Slog.v("StorageManagerService", "Package " + str + " has legacy storage");
                this.mUidsWithLegacyExternalStorage.add(Integer.valueOf(i));
            } else {
                Slog.v("StorageManagerService", "Package " + str + " does not have legacy storage");
                this.mUidsWithLegacyExternalStorage.remove(Integer.valueOf(i));
            }
        }
    }

    public final void snapshotAndMonitorLegacyStorageAppOp(UserHandle userHandle) {
        int identifier = userHandle.getIdentifier();
        Iterator it = this.mPmInternal.getInstalledApplications(4988928L, identifier, Process.myUid()).iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            ApplicationInfo applicationInfo = (ApplicationInfo) it.next();
            try {
                if (this.mIAppOpsService.checkOperation(87, applicationInfo.uid, applicationInfo.packageName) != 0) {
                    z = false;
                }
                updateLegacyStorageApps(applicationInfo.packageName, applicationInfo.uid, z);
            } catch (RemoteException e) {
                Slog.e("StorageManagerService", "Failed to check legacy op for package " + applicationInfo.packageName, e);
            }
        }
        if (this.mPackageMonitorsForUser.get(identifier) == null) {
            PackageMonitor packageMonitor = new PackageMonitor() { // from class: com.android.server.StorageManagerService.6
                public void onPackageRemoved(String str, int i) {
                    StorageManagerService.this.updateLegacyStorageApps(str, i, false);
                }
            };
            packageMonitor.register(this.mContext, userHandle, true, this.mHandler);
            this.mPackageMonitorsForUser.put(identifier, packageMonitor);
        } else {
            Slog.w("StorageManagerService", "PackageMonitor is already registered for: " + identifier);
        }
    }

    public final void systemReady() {
        ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(this);
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public final void bootCompleted() {
        this.mBootCompleted = true;
        this.mHandler.obtainMessage(13).sendToTarget();
    }

    public final void handleBootCompleted() {
        resetIfBootedAndConnected();
    }

    public final String getDefaultPrimaryStorageUuid() {
        return SystemProperties.getBoolean("ro.vold.primary_physical", false) ? "primary_physical" : StorageManager.UUID_PRIVATE_INTERNAL;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x006a, code lost:
    
        r11.mPrimaryStorageUuid = com.android.internal.util.XmlUtils.readStringAttribute(r4, "primaryStorageUuid");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void readSettingsLocked() {
        FileInputStream openRead;
        this.mRecords.clear();
        this.mPrimaryStorageUuid = getDefaultPrimaryStorageUuid();
        FileInputStream fileInputStream = null;
        try {
            try {
                openRead = this.mSettingsFile.openRead();
            } catch (Throwable th) {
                th = th;
            }
        } catch (FileNotFoundException unused) {
            IoUtils.closeQuietly(fileInputStream);
        } catch (IOException e) {
            e = e;
        } catch (XmlPullParserException e2) {
            e = e2;
        }
        try {
            Slog.d("StorageManagerService", "readSettingsLocked: " + this.mSettingsFile.getBaseFile().getAbsolutePath());
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
            while (true) {
                int next = resolvePullParser.next();
                boolean z = true;
                if (next == 1) {
                    IoUtils.closeQuietly(openRead);
                    return;
                }
                if (next == 2) {
                    String name = resolvePullParser.getName();
                    if ("volumes".equals(name)) {
                        int attributeInt = resolvePullParser.getAttributeInt((String) null, "version", 1);
                        boolean z2 = SystemProperties.getBoolean("ro.vold.primary_physical", false);
                        if (attributeInt < 3 && (attributeInt < 2 || z2)) {
                            z = false;
                        }
                    } else if ("volume".equals(name)) {
                        VolumeRecord readVolumeRecord = readVolumeRecord(resolvePullParser);
                        this.mRecords.put(readVolumeRecord.fsUuid, readVolumeRecord);
                        Slog.d("StorageManagerService", "readSettingsLocked: TAG_VOLUME rec=" + readVolumeRecord + " rec.fsUuid=" + readVolumeRecord.fsUuid);
                    }
                }
            }
        } catch (FileNotFoundException unused2) {
            fileInputStream = openRead;
            IoUtils.closeQuietly(fileInputStream);
        } catch (IOException e3) {
            e = e3;
            fileInputStream = openRead;
            Slog.wtf("StorageManagerService", "Failed reading metadata", e);
            IoUtils.closeQuietly(fileInputStream);
        } catch (XmlPullParserException e4) {
            e = e4;
            fileInputStream = openRead;
            Slog.wtf("StorageManagerService", "Failed reading metadata", e);
            IoUtils.closeQuietly(fileInputStream);
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = openRead;
            IoUtils.closeQuietly(fileInputStream);
            throw th;
        }
    }

    public final void writeSettingsLocked() {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = this.mSettingsFile.startWrite();
            try {
                Slog.d("StorageManagerService", "writeSettingsLocked: " + this.mSettingsFile.getBaseFile().getAbsolutePath());
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.startTag((String) null, "volumes");
                resolveSerializer.attributeInt((String) null, "version", 3);
                XmlUtils.writeStringAttribute(resolveSerializer, "primaryStorageUuid", this.mPrimaryStorageUuid);
                int size = this.mRecords.size();
                for (int i = 0; i < size; i++) {
                    writeVolumeRecord(resolveSerializer, (VolumeRecord) this.mRecords.valueAt(i));
                }
                resolveSerializer.endTag((String) null, "volumes");
                resolveSerializer.endDocument();
                this.mSettingsFile.finishWrite(startWrite);
            } catch (IOException unused) {
                fileOutputStream = startWrite;
                if (fileOutputStream != null) {
                    this.mSettingsFile.failWrite(fileOutputStream);
                }
            }
        } catch (IOException unused2) {
        }
    }

    public static VolumeRecord readVolumeRecord(TypedXmlPullParser typedXmlPullParser) {
        VolumeRecord volumeRecord = new VolumeRecord(typedXmlPullParser.getAttributeInt((String) null, "type"), XmlUtils.readStringAttribute(typedXmlPullParser, "fsUuid"));
        volumeRecord.partGuid = XmlUtils.readStringAttribute(typedXmlPullParser, "partGuid");
        volumeRecord.nickname = XmlUtils.readStringAttribute(typedXmlPullParser, "nickname");
        volumeRecord.userFlags = typedXmlPullParser.getAttributeInt((String) null, "userFlags");
        volumeRecord.createdMillis = typedXmlPullParser.getAttributeLong((String) null, "createdMillis", 0L);
        volumeRecord.lastSeenMillis = typedXmlPullParser.getAttributeLong((String) null, "lastSeenMillis", 0L);
        volumeRecord.lastTrimMillis = typedXmlPullParser.getAttributeLong((String) null, "lastTrimMillis", 0L);
        volumeRecord.lastBenchMillis = typedXmlPullParser.getAttributeLong((String) null, "lastBenchMillis", 0L);
        return volumeRecord;
    }

    public static void writeVolumeRecord(TypedXmlSerializer typedXmlSerializer, VolumeRecord volumeRecord) {
        typedXmlSerializer.startTag((String) null, "volume");
        typedXmlSerializer.attributeInt((String) null, "type", volumeRecord.type);
        XmlUtils.writeStringAttribute(typedXmlSerializer, "fsUuid", volumeRecord.fsUuid);
        XmlUtils.writeStringAttribute(typedXmlSerializer, "partGuid", volumeRecord.partGuid);
        XmlUtils.writeStringAttribute(typedXmlSerializer, "nickname", volumeRecord.nickname);
        typedXmlSerializer.attributeInt((String) null, "userFlags", volumeRecord.userFlags);
        typedXmlSerializer.attributeLong((String) null, "createdMillis", volumeRecord.createdMillis);
        typedXmlSerializer.attributeLong((String) null, "lastSeenMillis", volumeRecord.lastSeenMillis);
        typedXmlSerializer.attributeLong((String) null, "lastTrimMillis", volumeRecord.lastTrimMillis);
        typedXmlSerializer.attributeLong((String) null, "lastBenchMillis", volumeRecord.lastBenchMillis);
        typedXmlSerializer.endTag((String) null, "volume");
    }

    public void registerListener(IStorageEventListener iStorageEventListener) {
        this.mCallbacks.register(iStorageEventListener);
    }

    public void unregisterListener(IStorageEventListener iStorageEventListener) {
        this.mCallbacks.unregister(iStorageEventListener);
    }

    public void shutdown(IStorageShutdownObserver iStorageShutdownObserver) {
        super.shutdown_enforcePermission();
        Slog.i("StorageManagerService", "Shutting down");
        this.mHandler.obtainMessage(3, iStorageShutdownObserver).sendToTarget();
    }

    public void mount(String str) {
        super.mount_enforcePermission();
        Slog.d("StorageManagerService", "Begin mount: volId=" + str);
        VolumeInfo findVolumeByIdOrThrow = findVolumeByIdOrThrow(str);
        if (isMountDisallowed(findVolumeByIdOrThrow)) {
            throw new SecurityException("Mounting " + str + " restricted by policy");
        }
        mount(findVolumeByIdOrThrow);
    }

    public final void remountAppStorageDirs(Map map, int i) {
        for (Map.Entry entry : map.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            String str = (String) entry.getValue();
            Slog.i("StorageManagerService", "Remounting storage for pid: " + intValue);
            String[] sharedUserPackagesForPackage = this.mPmInternal.getSharedUserPackagesForPackage(str, i);
            int packageUid = this.mPmInternal.getPackageUid(str, 0L, i);
            if (sharedUserPackagesForPackage.length == 0) {
                sharedUserPackagesForPackage = new String[]{str};
            }
            try {
                this.mVold.remountAppStorageDirs(packageUid, intValue, sharedUserPackagesForPackage);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    public final void mount(final VolumeInfo volumeInfo) {
        int i;
        int i2;
        int i3;
        try {
            if (volumeInfo.type == 0) {
                Slog.i("StorageManagerService", "Mounting volume " + createVolumeInfoStrForPulbicVolume(volumeInfo));
            } else {
                Slog.i("StorageManagerService", "Mounting volume " + volumeInfo);
            }
            if (hasDeviceRestriction("readonly_storage")) {
                Slog.w("StorageManagerService", "Policy has restriction 'readonly_storage'; readonly");
                i = Integer.MIN_VALUE;
            } else {
                i = 0;
            }
            if (((DevicePolicyManager) this.mContext.getSystemService("device_policy")).semGetRequireStorageCardEncryption(null)) {
                Slog.i("StorageManagerService", "Admin Stirage Card Encryption Policy is Set");
                i2 = 536870912;
            } else {
                i2 = 0;
            }
            this.mVold.mount(volumeInfo.id, i | volumeInfo.mountFlags | i2, volumeInfo.mountUserId, new IVoldMountCallback.Stub() { // from class: com.android.server.StorageManagerService.7
                @Override // android.os.IVoldMountCallback
                public boolean onVolumeChecking(FileDescriptor fileDescriptor, String str, String str2) {
                    VolumeInfo volumeInfo2 = volumeInfo;
                    volumeInfo2.path = str;
                    volumeInfo2.internalPath = str2;
                    ParcelFileDescriptor parcelFileDescriptor = new ParcelFileDescriptor(fileDescriptor);
                    try {
                        try {
                            StorageManagerService.this.mStorageSessionController.onVolumeMount(parcelFileDescriptor, volumeInfo);
                            try {
                                parcelFileDescriptor.close();
                                return true;
                            } catch (Exception e) {
                                Slog.e("StorageManagerService", "Failed to close FUSE device fd", e);
                                return true;
                            }
                        } catch (Throwable th) {
                            try {
                                parcelFileDescriptor.close();
                            } catch (Exception e2) {
                                Slog.e("StorageManagerService", "Failed to close FUSE device fd", e2);
                            }
                            throw th;
                        }
                    } catch (StorageSessionController.ExternalStorageServiceException e3) {
                        VolumeInfo volumeInfo3 = volumeInfo;
                        if (volumeInfo3.type == 0) {
                            Slog.e("StorageManagerService", "Failed to mount volume " + StorageManagerService.this.createVolumeInfoStrForPulbicVolume(volumeInfo3), e3);
                        } else {
                            Slog.e("StorageManagerService", "Failed to mount volume " + volumeInfo, e3);
                        }
                        Slog.i("StorageManagerService", "Scheduling reset in 10s");
                        StorageManagerService.this.mHandler.removeMessages(10);
                        StorageManagerService.this.mHandler.sendMessageDelayed(StorageManagerService.this.mHandler.obtainMessage(10), TimeUnit.SECONDS.toMillis((long) 10));
                        try {
                            parcelFileDescriptor.close();
                            return false;
                        } catch (Exception e4) {
                            Slog.e("StorageManagerService", "Failed to close FUSE device fd", e4);
                            return false;
                        }
                    }
                }
            });
            if (volumeInfo.type == 0) {
                volumeInfo.mountUserId = this.mCurrentUserId;
            }
            if (volumeInfo.type == 0) {
                Slog.i("StorageManagerService", "Mounted volume " + createVolumeInfoStrForPulbicVolume(volumeInfo));
            } else {
                Slog.i("StorageManagerService", "Mounted volume " + volumeInfo);
            }
            if (!Objects.equals(volumeInfo.internalPath, "/data/media") || (i3 = this.mContext.getPackageManager().getPackageInfo("com.google.android.providers.media.module", 0).applicationInfo.uid) == 0) {
                return;
            }
            this.mVold.setMpUidForFileSystem(i3);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void unmount(String str) {
        DiskInfo diskInfo;
        super.unmount_enforcePermission();
        Slog.d("StorageManagerService", "Begin unmount: volId=" + str);
        VolumeInfo findVolumeByIdOrThrow = findVolumeByIdOrThrow(str);
        if (findVolumeByIdOrThrow.isPrimaryPhysical() || ((diskInfo = findVolumeByIdOrThrow.disk) != null && diskInfo.isSd())) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (this.mUnmountLock) {
                    this.mUnmountSignal = new CountDownLatch(1);
                    this.mPmInternal.updateExternalMediaStatus(false, true);
                    waitForLatch(this.mUnmountSignal, "mUnmountSignal");
                    this.mUnmountSignal = null;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        unmount(findVolumeByIdOrThrow);
    }

    public final void unmount(VolumeInfo volumeInfo) {
        try {
            try {
                if (volumeInfo.type == 1) {
                    this.mInstaller.onPrivateVolumeRemoved(volumeInfo.getFsUuid());
                }
            } catch (Installer.InstallerException e) {
                Slog.e("StorageManagerService", "Failed unmount mirror data", e);
            }
            this.mVold.unmount(volumeInfo.id);
            this.mStorageSessionController.onVolumeUnmount(volumeInfo);
        } catch (Exception e2) {
            Slog.wtf("StorageManagerService", e2);
        }
    }

    public void format(String str) {
        super.format_enforcePermission();
        Slog.d("StorageManagerService", "Begin format: volId=" + str);
        VolumeInfo findVolumeByIdOrThrow = findVolumeByIdOrThrow(str);
        String str2 = findVolumeByIdOrThrow.fsUuid;
        try {
            this.mVold.format(findVolumeByIdOrThrow.id, "auto");
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            forgetVolume(str2);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void benchmark(String str, final IVoldTaskListener iVoldTaskListener) {
        super.benchmark_enforcePermission();
        try {
            this.mVold.benchmark(str, new IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.8
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i, PersistableBundle persistableBundle) {
                    StorageManagerService.this.dispatchOnStatus(iVoldTaskListener, i, persistableBundle);
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i, PersistableBundle persistableBundle) {
                    StorageManagerService.this.dispatchOnFinished(iVoldTaskListener, i, persistableBundle);
                    String string = persistableBundle.getString("path");
                    String string2 = persistableBundle.getString("ident");
                    long j = persistableBundle.getLong("create");
                    long j2 = persistableBundle.getLong("run");
                    long j3 = persistableBundle.getLong("destroy");
                    ((DropBoxManager) StorageManagerService.this.mContext.getSystemService(DropBoxManager.class)).addText("storage_benchmark", StorageManagerService.this.scrubPath(string) + " " + string2 + " " + j + " " + j2 + " " + j3);
                    synchronized (StorageManagerService.this.mLock) {
                        VolumeRecord findRecordForPath = StorageManagerService.this.findRecordForPath(string);
                        if (findRecordForPath != null) {
                            findRecordForPath.lastBenchMillis = System.currentTimeMillis();
                            StorageManagerService.this.writeSettingsLocked();
                        }
                    }
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void partitionPublic(String str) {
        super.partitionPublic_enforcePermission();
        Slog.d("StorageManagerService", "partitionPublic: diskId=" + str);
        CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        try {
            this.mVold.partition(str, 0, -1);
            waitForLatch(findOrCreateDiskScanLatch, "partitionPublic", 600000L);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void partitionPrivate(String str) {
        super.partitionPrivate_enforcePermission();
        enforceAdminUser();
        Slog.d("StorageManagerService", "partitionPrivate: diskId=" + str);
        CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        try {
            this.mVold.partition(str, 1, -1);
            waitForLatch(findOrCreateDiskScanLatch, "partitionPrivate", 180000L);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void partitionMixed(String str, int i) {
        super.partitionMixed_enforcePermission();
        enforceAdminUser();
        Slog.d("StorageManagerService", "partitionMixed: diskId=" + str);
        CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        try {
            this.mVold.partition(str, 2, i);
            waitForLatch(findOrCreateDiskScanLatch, "partitionMixed", 180000L);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void setVolumeNickname(String str, String str2) {
        super.setVolumeNickname_enforcePermission();
        Slog.d("StorageManagerService", "setVolumeNickname: fsUuid=" + str + " nickname=" + str2);
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.get(str);
            volumeRecord.nickname = str2;
            this.mCallbacks.notifyVolumeRecordChanged(volumeRecord);
            writeSettingsLocked();
        }
    }

    public void setVolumeUserFlags(String str, int i, int i2) {
        super.setVolumeUserFlags_enforcePermission();
        Slog.d("StorageManagerService", "setVolumeUserFlags: fsUuid=" + str + " flags=" + i + " mask=" + i2);
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.get(str);
            volumeRecord.userFlags = (i & i2) | (volumeRecord.userFlags & (~i2));
            this.mCallbacks.notifyVolumeRecordChanged(volumeRecord);
            writeSettingsLocked();
        }
    }

    public void forgetVolume(String str) {
        super.forgetVolume_enforcePermission();
        Slog.d("StorageManagerService", "forgetVolume: fsUuid=" + str);
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.remove(str);
            if (volumeRecord != null && !TextUtils.isEmpty(volumeRecord.partGuid)) {
                this.mHandler.obtainMessage(9, volumeRecord).sendToTarget();
            }
            this.mCallbacks.notifyVolumeForgotten(str);
            if (Objects.equals(this.mPrimaryStorageUuid, str)) {
                this.mPrimaryStorageUuid = getDefaultPrimaryStorageUuid();
                this.mHandler.obtainMessage(10).sendToTarget();
            }
            writeSettingsLocked();
        }
    }

    public void forgetAllVolumes() {
        super.forgetAllVolumes_enforcePermission();
        Slog.d("StorageManagerService", "forgetAllVolumes");
        synchronized (this.mLock) {
            for (int i = 0; i < this.mRecords.size(); i++) {
                String str = (String) this.mRecords.keyAt(i);
                VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.valueAt(i);
                if (!TextUtils.isEmpty(volumeRecord.partGuid)) {
                    this.mHandler.obtainMessage(9, volumeRecord).sendToTarget();
                }
                this.mCallbacks.notifyVolumeForgotten(str);
            }
            this.mRecords.clear();
            if (!Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, this.mPrimaryStorageUuid)) {
                this.mPrimaryStorageUuid = getDefaultPrimaryStorageUuid();
            }
            writeSettingsLocked();
            this.mHandler.obtainMessage(10).sendToTarget();
        }
    }

    public final void forgetPartition(String str, String str2) {
        try {
            this.mVold.forgetPartition(str, str2);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void fstrim(int i, final IVoldTaskListener iVoldTaskListener) {
        super.fstrim_enforcePermission();
        try {
            if (needsCheckpoint() && supportsBlockCheckpoint()) {
                Slog.i("StorageManagerService", "Skipping fstrim - block based checkpoint in progress");
                return;
            }
            this.mVold.fstrim(i, new IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.9
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i2, PersistableBundle persistableBundle) {
                    StorageManagerService.this.dispatchOnStatus(iVoldTaskListener, i2, persistableBundle);
                    if (i2 != 0) {
                        return;
                    }
                    String string = persistableBundle.getString("path");
                    long j = persistableBundle.getLong("bytes");
                    long j2 = persistableBundle.getLong("time");
                    ((DropBoxManager) StorageManagerService.this.mContext.getSystemService(DropBoxManager.class)).addText("storage_trim", StorageManagerService.this.scrubPath(string) + " " + j + " " + j2);
                    synchronized (StorageManagerService.this.mLock) {
                        VolumeRecord findRecordForPath = StorageManagerService.this.findRecordForPath(string);
                        if (findRecordForPath != null) {
                            findRecordForPath.lastTrimMillis = System.currentTimeMillis();
                            StorageManagerService.this.writeSettingsLocked();
                        }
                    }
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i2, PersistableBundle persistableBundle) {
                    StorageManagerService.this.dispatchOnFinished(iVoldTaskListener, i2, persistableBundle);
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void runIdleMaint(final Runnable runnable) {
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            this.mHeimdAllFs = new HeimdAllFsService(this.mContext);
            if (needsCheckpoint() && supportsBlockCheckpoint()) {
                Slog.i("StorageManagerService", "Skipping idle maintenance - block based checkpoint in progress");
            }
            this.mVold.runIdleMaint(this.mNeedGC, new IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.10
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i, PersistableBundle persistableBundle) {
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i, PersistableBundle persistableBundle) {
                    if (StorageManagerService.this.mHeimdAllFs != null) {
                        StorageManagerService.this.mHeimdAllFs.waitForFinished();
                    }
                    StorageManagerService.this.mHeimdAllFs = null;
                    if (runnable != null) {
                        BackgroundThread.getHandler().post(runnable);
                    }
                }
            });
            HeimdAllFsService heimdAllFsService = this.mHeimdAllFs;
            if (heimdAllFsService != null) {
                heimdAllFsService.start();
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void runIdleMaintenance() {
        runIdleMaint(null);
    }

    public void abortIdleMaint(final Runnable runnable) {
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            this.mVold.abortIdleMaint(new IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.11
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i, PersistableBundle persistableBundle) {
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i, PersistableBundle persistableBundle) {
                    if (StorageManagerService.this.mHeimdAllFs != null) {
                        StorageManagerService.this.mHeimdAllFs.waitForFinished();
                    }
                    StorageManagerService.this.mHeimdAllFs = null;
                    if (runnable != null) {
                        BackgroundThread.getHandler().post(runnable);
                    }
                }
            });
            Slog.d("StorageManagerService", "abortIdleMaint, HeimdAllFSThread = null");
            HeimdAllFsService heimdAllFsService = this.mHeimdAllFs;
            if (heimdAllFsService != null) {
                heimdAllFsService.abort();
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void abortIdleMaintenance() {
        abortIdleMaint(null);
    }

    public boolean isPassedLifetimeThresh() {
        return this.mPassedLifetimeThresh;
    }

    public final int getAverageWriteAmount() {
        return Arrays.stream(this.mStorageWriteRecords).sum() / this.mMaxWriteRecords;
    }

    public final void updateStorageWriteRecords(int i) {
        System.arraycopy(this.mStorageWriteRecords, 0, this.mStorageWriteRecords, 1, this.mMaxWriteRecords - 1);
        this.mStorageWriteRecords[0] = i;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mWriteRecordFile.startWrite();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeInt(sSmartIdleMaintPeriod);
            objectOutputStream.writeObject(this.mStorageWriteRecords);
            this.mWriteRecordFile.finishWrite(fileOutputStream);
        } catch (IOException unused) {
            if (fileOutputStream != null) {
                this.mWriteRecordFile.failWrite(fileOutputStream);
            }
        }
    }

    public final boolean checkChargeStatus() {
        int intExtra;
        Intent registerReceiver = this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (this.mChargingRequired && (intExtra = registerReceiver.getIntExtra("status", -1)) != 2 && intExtra != 5) {
            Slog.w("StorageManagerService", "Battery is not being charged");
            return false;
        }
        float intExtra2 = (registerReceiver.getIntExtra("level", -1) * 100.0f) / registerReceiver.getIntExtra("scale", -1);
        if (intExtra2 >= this.mLowBatteryLevel) {
            return true;
        }
        Slog.w("StorageManagerService", "Battery level is " + intExtra2 + ", which is lower than threshold: " + this.mLowBatteryLevel);
        return false;
    }

    public final boolean refreshLifetimeConstraint() {
        try {
            int storageLifeTime = this.mVold.getStorageLifeTime();
            if (storageLifeTime == -1) {
                Slog.w("StorageManagerService", "Failed to get storage lifetime");
                return false;
            }
            if (storageLifeTime > this.mLifetimePercentThreshold) {
                Slog.w("StorageManagerService", "Ended smart idle maintenance, because of lifetime(" + storageLifeTime + "), lifetime threshold(" + this.mLifetimePercentThreshold + ")");
                this.mPassedLifetimeThresh = true;
                return false;
            }
            Slog.i("StorageManagerService", "Storage lifetime: " + storageLifeTime);
            return true;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00bd, code lost:
    
        if (r12 != null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00bf, code lost:
    
        r12.run();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00cf, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00cb, code lost:
    
        if (r12 == null) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void runSmartIdleMaint(Runnable runnable) {
        int i;
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            try {
                int i2 = this.mTargetDirtyRatio;
                int writeAmount = this.mVold.getWriteAmount();
                if (writeAmount == -1) {
                    Slog.w("StorageManagerService", "Failed to get storage write record");
                    return;
                }
                updateStorageWriteRecords(writeAmount);
                if (needsCheckpoint() && supportsBlockCheckpoint()) {
                    Slog.i("StorageManagerService", "Skipping smart idle maintenance - block based checkpoint in progress");
                }
                if (refreshLifetimeConstraint() && checkChargeStatus()) {
                    i = getAverageWriteAmount();
                    int i3 = i2;
                    int i4 = i;
                    Slog.i("StorageManagerService", "Set smart idle maintenance: latest write amount: " + writeAmount + ", average write amount: " + i4 + ", min segment threshold: " + this.mMinSegmentsThreshold + ", dirty reclaim rate: " + this.mDirtyReclaimRate + ", segment reclaim weight: " + this.mSegmentReclaimWeight + ", period(min): " + sSmartIdleMaintPeriod + ", min gc sleep time(ms): " + this.mMinGCSleepTime + ", target dirty ratio: " + i3);
                    this.mVold.setGCUrgentPace(i4, this.mMinSegmentsThreshold, this.mDirtyReclaimRate, this.mSegmentReclaimWeight, sSmartIdleMaintPeriod, this.mMinGCSleepTime, i3);
                }
                Slog.i("StorageManagerService", "Turn off gc_urgent based on checking lifetime and charge status");
                i = 0;
                i2 = 100;
                int i32 = i2;
                int i42 = i;
                Slog.i("StorageManagerService", "Set smart idle maintenance: latest write amount: " + writeAmount + ", average write amount: " + i42 + ", min segment threshold: " + this.mMinSegmentsThreshold + ", dirty reclaim rate: " + this.mDirtyReclaimRate + ", segment reclaim weight: " + this.mSegmentReclaimWeight + ", period(min): " + sSmartIdleMaintPeriod + ", min gc sleep time(ms): " + this.mMinGCSleepTime + ", target dirty ratio: " + i32);
                this.mVold.setGCUrgentPace(i42, this.mMinSegmentsThreshold, this.mDirtyReclaimRate, this.mSegmentReclaimWeight, sSmartIdleMaintPeriod, this.mMinGCSleepTime, i32);
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
            }
        } finally {
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public void setDebugFlags(int i, int i2) {
        long clearCallingIdentity;
        super.setDebugFlags_enforcePermission();
        String str = "force_off";
        if ((i2 & 3) != 0) {
            String str2 = (i & 1) != 0 ? "force_on" : (i & 2) != 0 ? "force_off" : "";
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SystemProperties.set("persist.sys.adoptable", str2);
                this.mHandler.obtainMessage(10).sendToTarget();
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } finally {
            }
        }
        if ((i2 & 12) != 0) {
            if ((i & 4) != 0) {
                str = "force_on";
            } else if ((i & 8) == 0) {
                str = "";
            }
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SystemProperties.set("persist.sys.sdcardfs", str);
                this.mHandler.obtainMessage(10).sendToTarget();
            } finally {
            }
        }
        if ((i2 & 16) != 0) {
            boolean z = (i & 16) != 0;
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SystemProperties.set("persist.sys.virtual_disk", Boolean.toString(z));
                this.mHandler.obtainMessage(10).sendToTarget();
            } finally {
            }
        }
    }

    public String getPrimaryStorageUuid() {
        String str;
        synchronized (this.mLock) {
            str = this.mPrimaryStorageUuid;
        }
        return str;
    }

    public void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) {
        super.setPrimaryStorageUuid_enforcePermission();
        synchronized (this.mLock) {
            if (Objects.equals(this.mPrimaryStorageUuid, str)) {
                throw new IllegalArgumentException("Primary storage already at " + str);
            }
            if (this.mMoveCallback != null) {
                throw new IllegalStateException("Move already in progress");
            }
            this.mMoveCallback = iPackageMoveObserver;
            this.mMoveTargetUuid = str;
            for (UserInfo userInfo : ((UserManager) this.mContext.getSystemService(UserManager.class)).getUsers()) {
                if (StorageManager.isFileEncrypted() && !isUserKeyUnlocked(userInfo.id)) {
                    Slog.w("StorageManagerService", "Failing move due to locked user " + userInfo.id);
                    onMoveStatusLocked(-10);
                    return;
                }
            }
            if (!"primary_physical".equals(this.mPrimaryStorageUuid) && !"primary_physical".equals(str)) {
                int i = this.mCurrentUserId;
                VolumeInfo findStorageForUuidAsUser = findStorageForUuidAsUser(this.mPrimaryStorageUuid, i);
                VolumeInfo findStorageForUuidAsUser2 = findStorageForUuidAsUser(str, i);
                if (findStorageForUuidAsUser == null) {
                    Slog.w("StorageManagerService", "Failing move due to missing from volume " + this.mPrimaryStorageUuid);
                    onMoveStatusLocked(-6);
                    return;
                }
                if (findStorageForUuidAsUser2 == null) {
                    Slog.w("StorageManagerService", "Failing move due to missing to volume " + str);
                    onMoveStatusLocked(-6);
                    return;
                }
                try {
                    this.mVold.moveStorage(findStorageForUuidAsUser.id, findStorageForUuidAsUser2.id, new IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.12
                        @Override // android.os.IVoldTaskListener
                        public void onFinished(int i2, PersistableBundle persistableBundle) {
                        }

                        @Override // android.os.IVoldTaskListener
                        public void onStatus(int i2, PersistableBundle persistableBundle) {
                            synchronized (StorageManagerService.this.mLock) {
                                StorageManagerService.this.onMoveStatusLocked(i2);
                            }
                        }
                    });
                    return;
                } catch (Exception e) {
                    Slog.wtf("StorageManagerService", e);
                    return;
                }
            }
            Slog.d("StorageManagerService", "Skipping move to/from primary physical");
            onMoveStatusLocked(82);
            onMoveStatusLocked(-100);
            this.mHandler.obtainMessage(10).sendToTarget();
        }
    }

    public final void warnOnNotMounted() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                if (volumeInfo.isPrimary() && volumeInfo.isMountedWritable()) {
                    return;
                }
            }
            Slog.w("StorageManagerService", "No primary storage mounted!");
        }
    }

    public final boolean isUidOwnerOfPackageOrSystem(String str, int i) {
        if (i == 1000) {
            return true;
        }
        return this.mPmInternal.isSameApp(str, i, UserHandle.getUserId(i));
    }

    public String getMountedObbPath(String str) {
        ObbState obbState;
        Objects.requireNonNull(str, "rawPath cannot be null");
        warnOnNotMounted();
        synchronized (this.mObbMounts) {
            obbState = (ObbState) this.mObbPathToStateMap.get(str);
        }
        if (obbState == null) {
            Slog.w("StorageManagerService", "Failed to find OBB mounted at " + str);
            return null;
        }
        return findVolumeByIdOrThrow(obbState.volId).getPath().getAbsolutePath();
    }

    public boolean isObbMounted(String str) {
        boolean containsKey;
        Objects.requireNonNull(str, "rawPath cannot be null");
        synchronized (this.mObbMounts) {
            containsKey = this.mObbPathToStateMap.containsKey(str);
        }
        return containsKey;
    }

    public void mountObb(String str, String str2, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo) {
        Objects.requireNonNull(str, "rawPath cannot be null");
        Objects.requireNonNull(str2, "canonicalPath cannot be null");
        Objects.requireNonNull(iObbActionListener, "token cannot be null");
        Objects.requireNonNull(obbInfo, "obbIfno cannot be null");
        int callingUid = Binder.getCallingUid();
        MountObbAction mountObbAction = new MountObbAction(new ObbState(str, str2, callingUid, iObbActionListener, i, null), callingUid, obbInfo);
        ObbActionHandler obbActionHandler = this.mObbActionHandler;
        obbActionHandler.sendMessage(obbActionHandler.obtainMessage(1, mountObbAction));
    }

    public void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) {
        ObbState obbState;
        Objects.requireNonNull(str, "rawPath cannot be null");
        synchronized (this.mObbMounts) {
            obbState = (ObbState) this.mObbPathToStateMap.get(str);
        }
        if (obbState != null) {
            UnmountObbAction unmountObbAction = new UnmountObbAction(new ObbState(str, obbState.canonicalPath, Binder.getCallingUid(), iObbActionListener, i, obbState.volId), z);
            ObbActionHandler obbActionHandler = this.mObbActionHandler;
            obbActionHandler.sendMessage(obbActionHandler.obtainMessage(1, unmountObbAction));
            return;
        }
        Slog.w("StorageManagerService", "Unknown OBB mount at " + str);
    }

    public boolean supportsCheckpoint() {
        return this.mVold.supportsCheckpoint();
    }

    public void startCheckpoint(int i) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0 && callingUid != 2000) {
            throw new SecurityException("no permission to start filesystem checkpoint");
        }
        this.mVold.startCheckpoint(i);
    }

    public void commitChanges() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to commit checkpoint changes");
        }
        this.mVold.commitChanges();
    }

    public boolean needsCheckpoint() {
        super.needsCheckpoint_enforcePermission();
        return this.mVold.needsCheckpoint();
    }

    public void abortChanges(String str, boolean z) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to commit checkpoint changes");
        }
        this.mVold.abortChanges(str, z);
    }

    public void createUserKey(int i, int i2, boolean z) {
        super.createUserKey_enforcePermission();
        try {
            this.mVold.createUserKey(i, i2, z);
            synchronized (this.mLock) {
                this.mLocalUnlockedUsers.append(i);
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void destroyUserKey(int i) {
        super.destroyUserKey_enforcePermission();
        try {
            this.mVold.destroyUserKey(i);
            synchronized (this.mLock) {
                this.mLocalUnlockedUsers.remove(i);
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void setUserKeyProtection(int i, byte[] bArr) {
        super.setUserKeyProtection_enforcePermission();
        this.mVold.setUserKeyProtection(i, HexDump.toHexString(bArr));
    }

    public void unlockUserKey(int i, int i2, byte[] bArr, byte[] bArr2) {
        super.unlockUserKey_enforcePermission();
        if (StorageManager.isFileEncrypted()) {
            this.mVold.unlockUserKey(i, i2, encodeBytes(bArr), HexDump.toHexString(bArr2));
        }
        synchronized (this.mLock) {
            this.mLocalUnlockedUsers.append(i);
        }
    }

    public final String encodeBytes(byte[] bArr) {
        return ArrayUtils.isEmpty(bArr) ? "!" : HexDump.toHexString(bArr);
    }

    public void lockUserKey(int i) {
        super.lockUserKey_enforcePermission();
        if (i == 0 && UserManager.isHeadlessSystemUserMode()) {
            throw new IllegalArgumentException("Headless system user data cannot be locked..");
        }
        if (!isUserKeyUnlocked(i)) {
            Slog.d("StorageManagerService", "User " + i + "'s CE storage is already locked");
            return;
        }
        try {
            this.mVold.lockUserKey(i);
            synchronized (this.mLock) {
                this.mLocalUnlockedUsers.remove(i);
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public boolean isUserKeyUnlocked(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mLocalUnlockedUsers.contains(i);
        }
        return contains;
    }

    public final boolean isSystemUnlocked(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = ArrayUtils.contains(this.mSystemUnlockedUsers, i);
        }
        return contains;
    }

    public final void prepareUserStorageIfNeeded(VolumeInfo volumeInfo) {
        int i;
        if (volumeInfo.type != 1) {
            return;
        }
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        for (UserInfo userInfo : userManager.getUsers()) {
            if (userManagerInternal.isUserUnlockingOrUnlocked(userInfo.id)) {
                i = 3;
            } else if (userManagerInternal.isUserRunning(userInfo.id)) {
                i = 1;
            }
            prepareUserStorageInternal(volumeInfo.fsUuid, userInfo.id, userInfo.serialNumber, i);
        }
    }

    public void prepareUserStorage(String str, int i, int i2, int i3) {
        super.prepareUserStorage_enforcePermission();
        try {
            prepareUserStorageInternal(str, i, i2, i3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final void prepareUserStorageInternal(String str, int i, int i2, int i3) {
        VolumeInfo findVolumeByUuid;
        try {
            this.mVold.prepareUserStorage(str, i, i2, i3);
            if (str != null && (findVolumeByUuid = ((StorageManager) this.mContext.getSystemService(StorageManager.class)).findVolumeByUuid(str)) != null && i == 0 && findVolumeByUuid.type == 1) {
                this.mInstaller.tryMountDataMirror(str);
            }
            if (i == 0) {
                try {
                    new Sender(this.mContext).send("FBE4", "encryption policy success");
                    Slog.i("StorageManagerService", "encryption policy for SA logging");
                } catch (Exception e) {
                    Slog.e("StorageManagerService", "encryption policy for SA logging : " + e.toString());
                }
            }
        } catch (Exception e2) {
            EventLog.writeEvent(1397638484, "224585613", -1, "");
            Slog.wtf("StorageManagerService", e2);
            String str2 = SystemProperties.get("security.fbe.global_de");
            UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK", false) || "loaded".equals(str2) || userManagerInternal.shouldIgnorePrepareStorageErrors(i)) {
                Slog.wtf("StorageManagerService", "ignoring error preparing storage for existing user " + i + "; device may be insecure!");
                if (i != 0 || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK", false)) {
                    return;
                }
                try {
                    new Sender(this.mContext).send("FBE5", SystemProperties.get("security.fbe.fail_cause"));
                    Slog.i("StorageManagerService", "encryption policy for SA logging");
                    return;
                } catch (Exception e3) {
                    Slog.e("StorageManagerService", "encryption policy for SA logging : " + e3.toString());
                    return;
                }
            }
            throw new RuntimeException(e2);
        }
    }

    public void destroyUserStorage(String str, int i, int i2) {
        super.destroyUserStorage_enforcePermission();
        try {
            this.mVold.destroyUserStorage(str, i, i2);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public void fixupAppDir(String str) {
        Matcher matcher = KNOWN_APP_DIR_PATHS.matcher(str);
        if (matcher.matches()) {
            if (matcher.group(2) == null) {
                Log.e("StorageManagerService", "Asked to fixup an app dir without a userId: " + str);
                return;
            }
            try {
                int parseInt = Integer.parseInt(matcher.group(2));
                String group = matcher.group(3);
                int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(group, parseInt);
                try {
                    this.mVold.fixupAppDir(str + "/", packageUidAsUser);
                    return;
                } catch (RemoteException | ServiceSpecificException e) {
                    Log.e("StorageManagerService", "Failed to fixup app dir for " + group, e);
                    return;
                }
            } catch (PackageManager.NameNotFoundException e2) {
                Log.e("StorageManagerService", "Couldn't find package to fixup app dir " + str, e2);
                return;
            } catch (NumberFormatException e3) {
                Log.e("StorageManagerService", "Invalid userId in path: " + str, e3);
                return;
            }
        }
        Log.e("StorageManagerService", "Path " + str + " is not a valid application-specific directory");
    }

    public void disableAppDataIsolation(String str, int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            throw new SecurityException("no permission to enable app visibility");
        }
        String[] sharedUserPackagesForPackage = this.mPmInternal.getSharedUserPackagesForPackage(str, i2);
        int packageUid = this.mPmInternal.getPackageUid(str, 0L, i2);
        if (sharedUserPackagesForPackage.length == 0) {
            sharedUserPackagesForPackage = new String[]{str};
        }
        try {
            this.mVold.unmountAppStorageDirs(packageUid, i, sharedUserPackagesForPackage);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public PendingIntent getManageSpaceActivityIntent(String str, int i) {
        int callingUidOrThrow = Binder.getCallingUidOrThrow();
        try {
            String[] packagesForUid = this.mIPackageManager.getPackagesForUid(callingUidOrThrow);
            if (packagesForUid == null) {
                throw new SecurityException("Unknown uid " + callingUidOrThrow);
            }
            if (!this.mStorageManagerInternal.hasExternalStorageAccess(callingUidOrThrow, packagesForUid[0])) {
                throw new SecurityException("Only File Manager Apps permitted");
            }
            try {
                ApplicationInfo applicationInfo = this.mIPackageManager.getApplicationInfo(str, 0L, UserHandle.getUserId(callingUidOrThrow));
                if (applicationInfo == null) {
                    throw new IllegalArgumentException("Invalid packageName");
                }
                if (applicationInfo.manageSpaceActivityName == null) {
                    Log.i("StorageManagerService", str + " doesn't have a manageSpaceActivity");
                    return null;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        Context createPackageContext = this.mContext.createPackageContext(str, 0);
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setClassName(str, applicationInfo.manageSpaceActivityName);
                        intent.setFlags(268435456);
                        return PendingIntent.getActivity(createPackageContext, i, intent, 1409286144, ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2).toBundle());
                    } catch (PackageManager.NameNotFoundException unused) {
                        throw new IllegalArgumentException("packageName not found");
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException unused2) {
                throw new SecurityException("Only File Manager Apps permitted");
            }
        } catch (RemoteException e) {
            throw new SecurityException("Unknown uid " + callingUidOrThrow, e);
        }
    }

    public void notifyAppIoBlocked(String str, int i, int i2, int i3) {
        enforceExternalStorageService();
        this.mStorageSessionController.notifyAppIoBlocked(str, i, i2, i3);
    }

    public void notifyAppIoResumed(String str, int i, int i2, int i3) {
        enforceExternalStorageService();
        this.mStorageSessionController.notifyAppIoResumed(str, i, i2, i3);
    }

    public boolean isAppIoBlocked(String str, int i, int i2, int i3) {
        return isAppIoBlocked(i);
    }

    public final boolean isAppIoBlocked(int i) {
        return this.mStorageSessionController.isAppIoBlocked(i);
    }

    public void setCloudMediaProvider(String str) {
        enforceExternalStorageService();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mCloudMediaProviders) {
            if (!Objects.equals(str, (String) this.mCloudMediaProviders.get(userId))) {
                this.mCloudMediaProviders.put(userId, str);
                this.mHandler.obtainMessage(16, userId, 0, str).sendToTarget();
            }
        }
    }

    public String getCloudMediaProvider() {
        String str;
        ProviderInfo resolveContentProvider;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        synchronized (this.mCloudMediaProviders) {
            str = (String) this.mCloudMediaProviders.get(userId);
        }
        if (str == null || (resolveContentProvider = this.mPmInternal.resolveContentProvider(str, 0L, userId, callingUid)) == null || this.mPmInternal.filterAppAccess(resolveContentProvider.packageName, callingUid, userId)) {
            return null;
        }
        return str;
    }

    public final void enforceExternalStorageService() {
        enforcePermission("android.permission.WRITE_MEDIA_STORAGE");
        if (UserHandle.getAppId(Binder.getCallingUid()) != this.mMediaStoreAuthorityAppId) {
            throw new SecurityException("Only the ExternalStorageService is permitted");
        }
    }

    public class AppFuseMountScope extends AppFuseBridge.MountScope {
        public boolean mMounted;

        public AppFuseMountScope(int i, int i2) {
            super(i, i2);
            this.mMounted = false;
        }

        @Override // com.android.server.storage.AppFuseBridge.MountScope
        public ParcelFileDescriptor open() {
            try {
                FileDescriptor mountAppFuse = StorageManagerService.this.mVold.mountAppFuse(this.uid, this.mountId);
                this.mMounted = true;
                return new ParcelFileDescriptor(mountAppFuse);
            } catch (Exception e) {
                throw new AppFuseMountException("Failed to mount", e);
            }
        }

        @Override // com.android.server.storage.AppFuseBridge.MountScope
        public ParcelFileDescriptor openFile(int i, int i2, int i3) {
            try {
                return new ParcelFileDescriptor(StorageManagerService.this.mVold.openAppFuseFile(this.uid, i, i2, i3));
            } catch (Exception e) {
                throw new AppFuseMountException("Failed to open", e);
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (this.mMounted) {
                BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.StorageManagerService$AppFuseMountScope$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        StorageManagerService.AppFuseMountScope.this.lambda$close$0();
                    }
                });
                this.mMounted = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$close$0() {
            try {
                StorageManagerService.this.mVold.unmountAppFuse(this.uid, this.mountId);
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    public AppFuseMount mountProxyFileDescriptorBridge() {
        boolean z;
        AppFuseMount appFuseMount;
        Slog.v("StorageManagerService", "mountProxyFileDescriptorBridge");
        int callingUid = Binder.getCallingUid();
        while (true) {
            synchronized (this.mAppFuseLock) {
                if (this.mAppFuseBridge == null) {
                    this.mAppFuseBridge = new AppFuseBridge();
                    new Thread(this.mAppFuseBridge, AppFuseBridge.TAG).start();
                    z = true;
                } else {
                    z = false;
                }
                try {
                    int i = this.mNextAppFuseName;
                    this.mNextAppFuseName = i + 1;
                    try {
                        appFuseMount = new AppFuseMount(i, this.mAppFuseBridge.addBridge(new AppFuseMountScope(callingUid, i)));
                    } catch (FuseUnavailableMountException e) {
                        if (z) {
                            Slog.e("StorageManagerService", "", e);
                            return null;
                        }
                        this.mAppFuseBridge = null;
                    }
                } catch (AppFuseMountException e2) {
                    throw e2.rethrowAsParcelableException();
                }
            }
            return appFuseMount;
        }
    }

    public ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) {
        Slog.v("StorageManagerService", "mountProxyFileDescriptor");
        int i4 = i3 & 805306368;
        try {
            synchronized (this.mAppFuseLock) {
                AppFuseBridge appFuseBridge = this.mAppFuseBridge;
                if (appFuseBridge == null) {
                    Slog.e("StorageManagerService", "FuseBridge has not been created");
                    return null;
                }
                return appFuseBridge.openFile(i, i2, i4);
            }
        } catch (FuseUnavailableMountException | InterruptedException e) {
            Slog.v("StorageManagerService", "The mount point has already been invalid", e);
            return null;
        }
    }

    public void mkdirs(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String str3 = "sys.user." + userId + ".ce_available";
        if (!isUserKeyUnlocked(userId)) {
            throw new IllegalStateException("Failed to prepare " + str2);
        }
        if (userId == 0 && !SystemProperties.getBoolean(str3, false)) {
            throw new IllegalStateException("Failed to prepare " + str2);
        }
        ((AppOpsManager) this.mContext.getSystemService("appops")).checkPackage(callingUid, str);
        try {
            PackageManager.Property propertyAsUser = this.mContext.getPackageManager().getPropertyAsUser("android.internal.PROPERTY_NO_APP_DATA_STORAGE", str, null, userId);
            if (propertyAsUser != null && propertyAsUser.getBoolean()) {
                throw new SecurityException(str + " should not have " + str2);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        try {
            File canonicalFile = new File(str2).getCanonicalFile();
            String absolutePath = canonicalFile.getAbsolutePath();
            if (!absolutePath.endsWith("/")) {
                absolutePath = absolutePath + "/";
            }
            Matcher matcher = KNOWN_APP_DIR_PATHS.matcher(absolutePath);
            if (matcher.matches()) {
                if (!matcher.group(3).equals(str)) {
                    throw new SecurityException("Invalid mkdirs path: " + canonicalFile + " does not contain calling package " + str);
                }
                if ((matcher.group(2) != null && !matcher.group(2).equals(Integer.toString(userId))) || (matcher.group(2) == null && userId != this.mCurrentUserId)) {
                    throw new SecurityException("Invalid mkdirs path: " + canonicalFile + " does not match calling user id " + userId);
                }
                try {
                    this.mVold.setupAppDir(absolutePath, callingUid);
                    return;
                } catch (RemoteException e) {
                    throw new IllegalStateException("Failed to prepare " + absolutePath + ": " + e);
                }
            }
            throw new SecurityException("Invalid mkdirs path: " + canonicalFile + " is not a known app path.");
        } catch (IOException e2) {
            throw new IllegalStateException("Failed to resolve " + str2 + ": " + e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x014e, code lost:
    
        if (r14.isVisibleForWrite(r38) != false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0150, code lost:
    
        if (r2 == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0156, code lost:
    
        if (r14.isVisibleForWrite(r13) != false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x015e, code lost:
    
        if (r14.isVisibleForWrite(77) == false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0161, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0164, code lost:
    
        r7 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0163, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public StorageVolume[] getVolumeList(int i, String str, int i2) {
        int callingUid;
        int userId;
        boolean z;
        ArrayList arrayList;
        boolean z2;
        boolean z3;
        boolean z4;
        StorageVolume buildStorageVolume;
        ArrayList arrayList2;
        ArraySet arraySet;
        String str2 = str;
        if ("android.SecVold.StorageManagerService".equals(str2)) {
            callingUid = 1000;
            userId = UserHandle.getUserId(1000);
            str2 = "android";
        } else {
            callingUid = Binder.getCallingUid();
            userId = UserHandle.getUserId(callingUid);
        }
        if (!isUidOwnerOfPackageOrSystem(str2, callingUid)) {
            throw new SecurityException("callingPackage does not match UID");
        }
        if (userId != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Need INTERACT_ACROSS_USERS to get volumes for another user");
        }
        boolean z5 = (i2 & 256) != 0;
        boolean z6 = (i2 & 512) != 0;
        boolean z7 = (i2 & 1024) != 0;
        boolean z8 = (i2 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
        boolean z9 = (i2 & IInstalld.FLAG_USE_QUOTA) != 0;
        boolean isSameApp = UserHandle.isSameApp(callingUid, this.mMediaStoreAuthorityAppId);
        if (z9) {
            try {
                String[] packagesForUid = this.mIPackageManager.getPackagesForUid(callingUid);
                if (packagesForUid == null) {
                    throw new SecurityException("Unknown uid " + callingUid);
                }
                if (!isSameApp && !this.mStorageManagerInternal.hasExternalStorageAccess(callingUid, packagesForUid[0])) {
                    throw new SecurityException("Only File Manager Apps permitted");
                }
            } catch (RemoteException e) {
                throw new SecurityException("Unknown uid " + callingUid, e);
            }
        }
        boolean z10 = isSystemUnlocked(0) || isSystemUnlocked(77);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean isDemo = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserInfo(i).isDemo();
            boolean hasExternalStorage = this.mStorageManagerInternal.hasExternalStorage(callingUid, str2);
            boolean isUserKeyUnlocked = isUserKeyUnlocked(i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            ArrayList arrayList3 = new ArrayList();
            ArraySet arraySet2 = new ArraySet();
            int i3 = this.mUserSharesMediaWith.get(i, -1);
            synchronized (this.mLock) {
                boolean z11 = z8;
                int i4 = 0;
                z = false;
                while (i4 < this.mVolumes.size()) {
                    String str3 = (String) this.mVolumes.keyAt(i4);
                    ArraySet arraySet3 = arraySet2;
                    VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i4);
                    int i5 = i4;
                    int type = volumeInfo.getType();
                    ArrayList arrayList4 = arrayList3;
                    if (type != 0) {
                        if (type != 2) {
                            if (type != 5) {
                                z3 = z9;
                                arraySet = arraySet3;
                                arrayList2 = arrayList4;
                                arraySet2 = arraySet;
                                arrayList3 = arrayList2;
                                i4 = i5 + 1;
                                z9 = z3;
                            }
                        } else if (volumeInfo.getMountUserId() != i && ((!z9 || volumeInfo.getMountUserId() != i3) && volumeInfo.getMountUserId() != 77)) {
                            z3 = z9;
                            arraySet = arraySet3;
                            arrayList2 = arrayList4;
                            arraySet2 = arraySet;
                            arrayList3 = arrayList2;
                            i4 = i5 + 1;
                            z9 = z3;
                        }
                    }
                    if (!volumeInfo.isVisibleForUser(i) && !volumeInfo.isVisibleForUser(77) && ((volumeInfo.isVisible() || !z7 || volumeInfo.getPath() == null) && ((volumeInfo.getType() != 0 || !volumeInfo.isVisibleForUser(i3)) && (!z9 || !volumeInfo.isVisibleForUser(i3))))) {
                        z2 = false;
                        if (z2) {
                            if (isSameApp) {
                                z3 = z9;
                            } else {
                                if (!z10) {
                                    StringBuilder sb = new StringBuilder();
                                    z3 = z9;
                                    sb.append("Reporting ");
                                    sb.append(str3);
                                    sb.append(" unmounted due to system locked");
                                    Slog.w("StorageManagerService", sb.toString());
                                } else {
                                    z3 = z9;
                                    if (volumeInfo.getType() == 2 && !isUserKeyUnlocked) {
                                        Slog.w("StorageManagerService", "Reporting " + str3 + "unmounted due to " + i + " locked");
                                    } else if (!hasExternalStorage && !z6) {
                                        Slog.w("StorageManagerService", "Reporting " + str3 + "unmounted due to missing permissions");
                                    }
                                }
                                z4 = true;
                                buildStorageVolume = volumeInfo.buildStorageVolume(this.mContext, (i != volumeInfo.getMountUserId() || volumeInfo.getMountUserId() < 0) ? i : volumeInfo.getMountUserId(), z4);
                                if (!volumeInfo.isPrimary() && volumeInfo.getMountUserId() == i) {
                                    arrayList2 = arrayList4;
                                    arrayList2.add(0, buildStorageVolume);
                                    z = true;
                                } else {
                                    arrayList2 = arrayList4;
                                    arrayList2.add(buildStorageVolume);
                                }
                                arraySet = arraySet3;
                                arraySet.add(buildStorageVolume.getUuid());
                                arraySet2 = arraySet;
                                arrayList3 = arrayList2;
                                i4 = i5 + 1;
                                z9 = z3;
                            }
                            z4 = false;
                            buildStorageVolume = volumeInfo.buildStorageVolume(this.mContext, (i != volumeInfo.getMountUserId() || volumeInfo.getMountUserId() < 0) ? i : volumeInfo.getMountUserId(), z4);
                            if (!volumeInfo.isPrimary()) {
                            }
                            arrayList2 = arrayList4;
                            arrayList2.add(buildStorageVolume);
                            arraySet = arraySet3;
                            arraySet.add(buildStorageVolume.getUuid());
                            arraySet2 = arraySet;
                            arrayList3 = arrayList2;
                            i4 = i5 + 1;
                            z9 = z3;
                        }
                        z3 = z9;
                        arraySet = arraySet3;
                        arrayList2 = arrayList4;
                        arraySet2 = arraySet;
                        arrayList3 = arrayList2;
                        i4 = i5 + 1;
                        z9 = z3;
                    }
                    z2 = true;
                    if (z2) {
                    }
                    z3 = z9;
                    arraySet = arraySet3;
                    arrayList2 = arrayList4;
                    arraySet2 = arraySet;
                    arrayList3 = arrayList2;
                    i4 = i5 + 1;
                    z9 = z3;
                }
                arrayList = arrayList3;
                ArraySet arraySet4 = arraySet2;
                if (z11) {
                    long currentTimeMillis = System.currentTimeMillis() - 604800000;
                    for (int i6 = 0; i6 < this.mRecords.size(); i6++) {
                        VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.valueAt(i6);
                        if (!arraySet4.contains(volumeRecord.fsUuid)) {
                            long j = volumeRecord.lastSeenMillis;
                            if (j > 0 && j < currentTimeMillis) {
                                StorageVolume buildStorageVolume2 = volumeRecord.buildStorageVolume(this.mContext);
                                arrayList.add(buildStorageVolume2);
                                arraySet4.add(buildStorageVolume2.getUuid());
                            }
                        }
                    }
                }
            }
            if (isDemo) {
                File dataPreloadsMediaDirectory = Environment.getDataPreloadsMediaDirectory();
                arrayList.add(new StorageVolume("demo", dataPreloadsMediaDirectory, dataPreloadsMediaDirectory, this.mContext.getString(R.string.unknownName), false, false, true, false, false, 0L, new UserHandle(i), null, "demo", "mounted_ro"));
            }
            if (!z) {
                Slog.w("StorageManagerService", "No primary storage defined yet; hacking together a stub");
                boolean z12 = SystemProperties.getBoolean("ro.vold.primary_physical", false);
                File legacyExternalStorageDirectory = Environment.getLegacyExternalStorageDirectory();
                arrayList.add(0, new StorageVolume("stub_primary", legacyExternalStorageDirectory, legacyExternalStorageDirectory, this.mContext.getString(R.string.unknownName), true, z12, !z12, false, false, 0L, new UserHandle(i), null, null, "removed", 0, "fuse", false));
            }
            return (StorageVolume[]) arrayList.toArray(new StorageVolume[arrayList.size()]);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public DiskInfo[] getDisks() {
        DiskInfo[] diskInfoArr;
        synchronized (this.mLock) {
            diskInfoArr = new DiskInfo[this.mDisks.size()];
            for (int i = 0; i < this.mDisks.size(); i++) {
                diskInfoArr[i] = (DiskInfo) this.mDisks.valueAt(i);
            }
        }
        return diskInfoArr;
    }

    public VolumeInfo[] getVolumes(int i) {
        VolumeInfo[] volumeInfoArr;
        synchronized (this.mLock) {
            volumeInfoArr = new VolumeInfo[this.mVolumes.size()];
            for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                volumeInfoArr[i2] = (VolumeInfo) this.mVolumes.valueAt(i2);
            }
        }
        return volumeInfoArr;
    }

    public VolumeRecord[] getVolumeRecords(int i) {
        VolumeRecord[] volumeRecordArr;
        synchronized (this.mLock) {
            volumeRecordArr = new VolumeRecord[this.mRecords.size()];
            for (int i2 = 0; i2 < this.mRecords.size(); i2++) {
                volumeRecordArr[i2] = (VolumeRecord) this.mRecords.valueAt(i2);
            }
        }
        return volumeRecordArr;
    }

    public long getCacheQuotaBytes(String str, int i) {
        if (i != Binder.getCallingUid()) {
            this.mContext.enforceCallingPermission("android.permission.STORAGE_INTERNAL", "StorageManagerService");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((StorageStatsManager) this.mContext.getSystemService(StorageStatsManager.class)).getCacheQuotaBytes(str, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long getCacheSizeBytes(String str, int i) {
        if (i != Binder.getCallingUid()) {
            this.mContext.enforceCallingPermission("android.permission.STORAGE_INTERNAL", "StorageManagerService");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return ((StorageStatsManager) this.mContext.getSystemService(StorageStatsManager.class)).queryStatsForUid(str, i).getCacheBytes();
            } catch (IOException e) {
                throw new ParcelableException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int adjustAllocateFlags(int i, int i2, String str) {
        if ((i & 1) != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.ALLOCATE_AGGRESSIVE", "StorageManagerService");
        }
        int i3 = i & (-3) & (-5);
        AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (appOpsManager.isOperationActive(26, i2, str)) {
                Slog.d("StorageManagerService", "UID " + i2 + " is actively using camera; letting them defy reserved cached data");
                i3 |= 4;
            }
            return i3;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long getAllocatableBytes(String str, int i, String str2) {
        long j;
        long j2;
        long j3;
        int adjustAllocateFlags = adjustAllocateFlags(i, Binder.getCallingUid(), str2);
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(StorageManager.class);
        StorageStatsManager storageStatsManager = (StorageStatsManager) this.mContext.getSystemService(StorageStatsManager.class);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                File findPathForUuid = storageManager.findPathForUuid(str);
                if ((adjustAllocateFlags & 16) == 0) {
                    j = findPathForUuid.getUsableSpace();
                    j2 = storageManager.getStorageLowBytes(findPathForUuid);
                    j3 = storageManager.getStorageFullBytes(findPathForUuid);
                } else {
                    j = 0;
                    j2 = 0;
                    j3 = 0;
                }
                long max = ((adjustAllocateFlags & 8) == 0 && storageStatsManager.isQuotaSupported(str)) ? Math.max(0L, storageStatsManager.getCacheBytes(str) - storageManager.getStorageCacheBytes(findPathForUuid, adjustAllocateFlags)) : 0L;
                if ((adjustAllocateFlags & 1) != 0) {
                    return Math.max(0L, (j + max) - j3);
                }
                return Math.max(0L, (j + max) - j2);
            } catch (IOException e) {
                throw new ParcelableException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void allocateBytes(String str, long j, int i, String str2) {
        long storageLowBytes;
        int adjustAllocateFlags = adjustAllocateFlags(i, Binder.getCallingUid(), str2);
        long allocatableBytes = getAllocatableBytes(str, adjustAllocateFlags | 8, str2);
        if (j > allocatableBytes) {
            long allocatableBytes2 = allocatableBytes + getAllocatableBytes(str, adjustAllocateFlags | 16, str2);
            if (j > allocatableBytes2) {
                throw new ParcelableException(new IOException("Failed to allocate " + j + " because only " + allocatableBytes2 + " allocatable"));
            }
        }
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(StorageManager.class);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                File findPathForUuid = storageManager.findPathForUuid(str);
                if ((adjustAllocateFlags & 1) != 0) {
                    storageLowBytes = storageManager.getStorageFullBytes(findPathForUuid);
                } else {
                    storageLowBytes = storageManager.getStorageLowBytes(findPathForUuid);
                }
                this.mPmInternal.freeStorage(str, j + storageLowBytes, adjustAllocateFlags);
            } catch (IOException e) {
                throw new ParcelableException(e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void addObbStateLocked(ObbState obbState) {
        IBinder binder = obbState.getBinder();
        List list = (List) this.mObbMounts.get(binder);
        if (list == null) {
            list = new ArrayList();
            this.mObbMounts.put(binder, list);
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((ObbState) it.next()).rawPath.equals(obbState.rawPath)) {
                    throw new IllegalStateException("Attempt to add ObbState twice. This indicates an error in the StorageManagerService logic.");
                }
            }
        }
        list.add(obbState);
        try {
            obbState.link();
            this.mObbPathToStateMap.put(obbState.rawPath, obbState);
        } catch (RemoteException e) {
            list.remove(obbState);
            if (list.isEmpty()) {
                this.mObbMounts.remove(binder);
            }
            throw e;
        }
    }

    public final void removeObbStateLocked(ObbState obbState) {
        IBinder binder = obbState.getBinder();
        List list = (List) this.mObbMounts.get(binder);
        if (list != null) {
            if (list.remove(obbState)) {
                obbState.unlink();
            }
            if (list.isEmpty()) {
                this.mObbMounts.remove(binder);
            }
        }
        this.mObbPathToStateMap.remove(obbState.rawPath);
    }

    public class ObbActionHandler extends Handler {
        public ObbActionHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                ((ObbAction) message.obj).execute(this);
                return;
            }
            if (i != 2) {
                return;
            }
            String str = (String) message.obj;
            synchronized (StorageManagerService.this.mObbMounts) {
                ArrayList<ObbState> arrayList = new ArrayList();
                for (ObbState obbState : StorageManagerService.this.mObbPathToStateMap.values()) {
                    if (obbState.canonicalPath.startsWith(str)) {
                        arrayList.add(obbState);
                    }
                }
                for (ObbState obbState2 : arrayList) {
                    StorageManagerService.this.removeObbStateLocked(obbState2);
                    try {
                        obbState2.token.onObbResult(obbState2.rawPath, obbState2.nonce, 2);
                    } catch (RemoteException unused) {
                        Slog.i("StorageManagerService", "Couldn't send unmount notification for  OBB: " + obbState2.rawPath);
                    }
                }
            }
        }
    }

    class ObbException extends Exception {
        public final int status;

        public ObbException(int i, String str) {
            super(str);
            this.status = i;
        }

        public ObbException(int i, Throwable th) {
            super(th.getMessage(), th);
            this.status = i;
        }
    }

    public abstract class ObbAction {
        public ObbState mObbState;

        public abstract void handleExecute();

        public ObbAction(ObbState obbState) {
            this.mObbState = obbState;
        }

        public void execute(ObbActionHandler obbActionHandler) {
            try {
                handleExecute();
            } catch (ObbException e) {
                notifyObbStateChange(e);
            }
        }

        public void notifyObbStateChange(ObbException obbException) {
            Slog.w("StorageManagerService", obbException);
            notifyObbStateChange(obbException.status);
        }

        public void notifyObbStateChange(int i) {
            IObbActionListener iObbActionListener;
            ObbState obbState = this.mObbState;
            if (obbState == null || (iObbActionListener = obbState.token) == null) {
                return;
            }
            try {
                iObbActionListener.onObbResult(obbState.rawPath, obbState.nonce, i);
            } catch (RemoteException unused) {
                Slog.w("StorageManagerService", "StorageEventListener went away while calling onObbStateChanged");
            }
        }
    }

    public class MountObbAction extends ObbAction {
        public final int mCallingUid;
        public ObbInfo mObbInfo;

        public MountObbAction(ObbState obbState, int i, ObbInfo obbInfo) {
            super(obbState);
            this.mCallingUid = i;
            this.mObbInfo = obbInfo;
        }

        @Override // com.android.server.StorageManagerService.ObbAction
        public void handleExecute() {
            boolean containsKey;
            StorageManagerService.this.warnOnNotMounted();
            if (!StorageManagerService.this.isUidOwnerOfPackageOrSystem(this.mObbInfo.packageName, this.mCallingUid)) {
                throw new ObbException(25, "Denied attempt to mount OBB " + this.mObbInfo.filename + " which is owned by " + this.mObbInfo.packageName);
            }
            synchronized (StorageManagerService.this.mObbMounts) {
                containsKey = StorageManagerService.this.mObbPathToStateMap.containsKey(this.mObbState.rawPath);
            }
            if (containsKey) {
                throw new ObbException(24, "Attempt to mount OBB which is already mounted: " + this.mObbInfo.filename);
            }
            try {
                ObbState obbState = this.mObbState;
                IVold iVold = StorageManagerService.this.mVold;
                ObbState obbState2 = this.mObbState;
                obbState.volId = iVold.createObb(obbState2.canonicalPath, obbState2.ownerGid);
                StorageManagerService.this.mVold.mount(this.mObbState.volId, 0, -1, null);
                synchronized (StorageManagerService.this.mObbMounts) {
                    StorageManagerService.this.addObbStateLocked(this.mObbState);
                }
                notifyObbStateChange(1);
            } catch (Exception e) {
                throw new ObbException(21, e);
            }
        }

        public String toString() {
            return "MountObbAction{" + this.mObbState + '}';
        }
    }

    public class UnmountObbAction extends ObbAction {
        public final boolean mForceUnmount;

        public UnmountObbAction(ObbState obbState, boolean z) {
            super(obbState);
            this.mForceUnmount = z;
        }

        @Override // com.android.server.StorageManagerService.ObbAction
        public void handleExecute() {
            ObbState obbState;
            StorageManagerService.this.warnOnNotMounted();
            synchronized (StorageManagerService.this.mObbMounts) {
                obbState = (ObbState) StorageManagerService.this.mObbPathToStateMap.get(this.mObbState.rawPath);
            }
            if (obbState == null) {
                throw new ObbException(23, "Missing existingState");
            }
            if (obbState.ownerGid != this.mObbState.ownerGid) {
                notifyObbStateChange(new ObbException(25, "Permission denied to unmount OBB " + obbState.rawPath + " (owned by GID " + obbState.ownerGid + ")"));
                return;
            }
            try {
                StorageManagerService.this.mVold.unmount(this.mObbState.volId);
                StorageManagerService.this.mVold.destroyObb(this.mObbState.volId);
                this.mObbState.volId = null;
                synchronized (StorageManagerService.this.mObbMounts) {
                    StorageManagerService.this.removeObbStateLocked(obbState);
                }
                notifyObbStateChange(2);
            } catch (Exception e) {
                throw new ObbException(22, e);
            }
        }

        public String toString() {
            return "UnmountObbAction{" + this.mObbState + ",force=" + this.mForceUnmount + '}';
        }
    }

    public final void dispatchOnStatus(IVoldTaskListener iVoldTaskListener, int i, PersistableBundle persistableBundle) {
        if (iVoldTaskListener != null) {
            try {
                iVoldTaskListener.onStatus(i, persistableBundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void dispatchOnFinished(IVoldTaskListener iVoldTaskListener, int i, PersistableBundle persistableBundle) {
        if (iVoldTaskListener != null) {
            try {
                iVoldTaskListener.onFinished(i, persistableBundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public int getExternalStorageMountMode(int i, String str) {
        super.getExternalStorageMountMode_enforcePermission();
        return this.mStorageManagerInternal.getExternalStorageMountMode(i, str);
    }

    public final int getMountModeInternal(int i, String str) {
        ApplicationInfo applicationInfo;
        boolean z = false;
        try {
            if (!Process.isIsolated(i) && !Process.isSdkSandboxUid(i)) {
                String[] packagesForUid = this.mIPackageManager.getPackagesForUid(i);
                if (ArrayUtils.isEmpty(packagesForUid)) {
                    return 0;
                }
                if (str == null) {
                    str = packagesForUid[0];
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (this.mPmInternal.isInstantApp(str, UserHandle.getUserId(i))) {
                        return 0;
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (this.mStorageManagerInternal.isExternalStorageService(i)) {
                        return 3;
                    }
                    if (this.mDownloadsAuthorityAppId != UserHandle.getAppId(i) && this.mExternalStorageAuthorityAppId != UserHandle.getAppId(i)) {
                        if ((this.mIPackageManager.checkUidPermission("android.permission.ACCESS_MTP", i) == 0) && (applicationInfo = this.mIPackageManager.getApplicationInfo(str, 0L, UserHandle.getUserId(i))) != null && applicationInfo.isSignedWithPlatformKey()) {
                            return 4;
                        }
                        boolean z2 = this.mIPackageManager.checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0;
                        int length = packagesForUid.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            if (this.mIAppOpsService.checkOperation(66, i, packagesForUid[i2]) == 0) {
                                z = true;
                                break;
                            }
                            i2++;
                        }
                        return (z2 || z) ? 2 : 1;
                    }
                    return 4;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } catch (RemoteException unused) {
        }
        return 0;
    }

    public class Callbacks extends Handler {
        public final RemoteCallbackList mCallbacks;

        public Callbacks(Looper looper) {
            super(looper);
            this.mCallbacks = new RemoteCallbackList();
        }

        public void register(IStorageEventListener iStorageEventListener) {
            this.mCallbacks.register(iStorageEventListener);
        }

        public void unregister(IStorageEventListener iStorageEventListener) {
            this.mCallbacks.unregister(iStorageEventListener);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            SomeArgs someArgs = (SomeArgs) message.obj;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    invokeCallback((IStorageEventListener) this.mCallbacks.getBroadcastItem(i), message.what, someArgs);
                } catch (RemoteException unused) {
                }
            }
            this.mCallbacks.finishBroadcast();
            someArgs.recycle();
        }

        public final void invokeCallback(IStorageEventListener iStorageEventListener, int i, SomeArgs someArgs) {
            switch (i) {
                case 1:
                    iStorageEventListener.onStorageStateChanged((String) someArgs.arg1, (String) someArgs.arg2, (String) someArgs.arg3);
                    break;
                case 2:
                    iStorageEventListener.onVolumeStateChanged((VolumeInfo) someArgs.arg1, someArgs.argi2, someArgs.argi3);
                    break;
                case 3:
                    iStorageEventListener.onVolumeRecordChanged((VolumeRecord) someArgs.arg1);
                    break;
                case 4:
                    iStorageEventListener.onVolumeForgotten((String) someArgs.arg1);
                    break;
                case 5:
                    iStorageEventListener.onDiskScanned((DiskInfo) someArgs.arg1, someArgs.argi2);
                    break;
                case 6:
                    iStorageEventListener.onDiskDestroyed((DiskInfo) someArgs.arg1);
                    break;
            }
        }

        public final void notifyStorageStateChanged(String str, String str2, String str3) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            obtain.arg3 = str3;
            obtainMessage(1, obtain).sendToTarget();
        }

        public final void notifyVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = volumeInfo.clone();
            obtain.argi2 = i;
            obtain.argi3 = i2;
            obtainMessage(2, obtain).sendToTarget();
        }

        public final void notifyVolumeRecordChanged(VolumeRecord volumeRecord) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = volumeRecord.clone();
            obtainMessage(3, obtain).sendToTarget();
        }

        public final void notifyVolumeForgotten(String str) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = str;
            obtainMessage(4, obtain).sendToTarget();
        }

        public final void notifyDiskScanned(DiskInfo diskInfo, int i) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = diskInfo.clone();
            obtain.argi2 = i;
            obtainMessage(5, obtain).sendToTarget();
        }

        public final void notifyDiskDestroyed(DiskInfo diskInfo) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = diskInfo.clone();
            obtainMessage(6, obtain).sendToTarget();
        }
    }

    public final List getStorageVolumes() {
        ArrayList arrayList = new ArrayList();
        try {
            Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), "android.SecVold.StorageManagerService", FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM));
            return arrayList;
        } catch (Exception e) {
            Slog.e("StorageManagerService", "Failed to get StorageVolume List ", e);
            return null;
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "StorageManagerService", printWriter)) {
            final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ", 160);
            List storageVolumes = getStorageVolumes();
            if (storageVolumes != null) {
                indentingPrintWriter.println();
                indentingPrintWriter.println("StorageVolumes:");
                indentingPrintWriter.println("Size:" + storageVolumes.size());
                indentingPrintWriter.increaseIndent();
                Iterator it = storageVolumes.iterator();
                while (it.hasNext()) {
                    ((StorageVolume) it.next()).dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println();
            synchronized (this.mLock) {
                indentingPrintWriter.println("Disks:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mDisks.size(); i++) {
                    ((DiskInfo) this.mDisks.valueAt(i)).dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Volumes:");
                indentingPrintWriter.println("Size:" + this.mVolumes.size());
                indentingPrintWriter.increaseIndent();
                for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                    VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i2);
                    if (!"private".equals(volumeInfo.id)) {
                        volumeInfo.dump(indentingPrintWriter);
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Records:");
                indentingPrintWriter.increaseIndent();
                for (int i3 = 0; i3 < this.mRecords.size(); i3++) {
                    ((VolumeRecord) this.mRecords.valueAt(i3)).dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Primary storage UUID: " + this.mPrimaryStorageUuid);
                indentingPrintWriter.println();
                Pair primaryStoragePathAndSize = StorageManager.getPrimaryStoragePathAndSize();
                if (primaryStoragePathAndSize == null) {
                    indentingPrintWriter.println("Internal storage total size: N/A");
                } else {
                    indentingPrintWriter.print("Internal storage (");
                    indentingPrintWriter.print((String) primaryStoragePathAndSize.first);
                    indentingPrintWriter.print(") total size: ");
                    indentingPrintWriter.print(primaryStoragePathAndSize.second);
                    indentingPrintWriter.print(" (");
                    indentingPrintWriter.print(DataUnit.MEBIBYTES.toBytes(((Long) primaryStoragePathAndSize.second).longValue()));
                    indentingPrintWriter.println(" MiB)");
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Local unlocked users: " + this.mLocalUnlockedUsers);
                indentingPrintWriter.println("System unlocked users: " + Arrays.toString(this.mSystemUnlockedUsers));
                indentingPrintWriter.print("Sharing media with parent : ");
                this.mIsMediaSharedWithParent.forEach(new BiConsumer() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        StorageManagerService.lambda$dump$4(indentingPrintWriter, (Integer) obj, (Boolean) obj2);
                    }
                });
                indentingPrintWriter.println();
                indentingPrintWriter.print("User pairs for sharing media : ");
                for (int i4 = 0; i4 < this.mUserSharesMediaWith.size(); i4++) {
                    int keyAt = this.mUserSharesMediaWith.keyAt(i4);
                    indentingPrintWriter.print(" [" + keyAt + ", " + this.mUserSharesMediaWith.get(keyAt, -1) + "] ");
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                for (int i5 = 0; i5 < 20; i5++) {
                    String format = String.format("/data/log/vold-dump_%02d", Integer.valueOf(i5));
                    try {
                        indentingPrintWriter.println("VOLD LAST LOG BUF#" + i5);
                        indentingPrintWriter.println(IoUtils.readFileAsString(format));
                    } catch (IOException unused) {
                    }
                }
            }
            synchronized (this.mObbMounts) {
                indentingPrintWriter.println();
                indentingPrintWriter.println("mObbMounts:");
                indentingPrintWriter.increaseIndent();
                for (Map.Entry entry : this.mObbMounts.entrySet()) {
                    indentingPrintWriter.println(entry.getKey() + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR);
                    indentingPrintWriter.increaseIndent();
                    Iterator it2 = ((List) entry.getValue()).iterator();
                    while (it2.hasNext()) {
                        indentingPrintWriter.println((ObbState) it2.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("mObbPathToStateMap:");
                indentingPrintWriter.increaseIndent();
                for (Map.Entry entry2 : this.mObbPathToStateMap.entrySet()) {
                    indentingPrintWriter.print((String) entry2.getKey());
                    indentingPrintWriter.print(" -> ");
                    indentingPrintWriter.println(entry2.getValue());
                }
                indentingPrintWriter.decreaseIndent();
            }
            synchronized (this.mCloudMediaProviders) {
                indentingPrintWriter.println();
                indentingPrintWriter.print("Media cloud providers: ");
                indentingPrintWriter.println(this.mCloudMediaProviders);
            }
            indentingPrintWriter.println();
            indentingPrintWriter.print("Last maintenance: ");
            indentingPrintWriter.println(TimeUtils.formatForLogging(this.mLastMaintenance));
        }
    }

    public static /* synthetic */ void lambda$dump$4(IndentingPrintWriter indentingPrintWriter, Integer num, Boolean bool) {
        indentingPrintWriter.print(" [" + num + ", " + bool + "] ");
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        try {
            this.mVold.monitor();
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final class StorageManagerInternalImpl extends StorageManagerInternal {
        public final CopyOnWriteArraySet mCloudProviderChangeListeners;
        public final List mResetListeners;

        public StorageManagerInternalImpl() {
            this.mResetListeners = new ArrayList();
            this.mCloudProviderChangeListeners = new CopyOnWriteArraySet();
        }

        public boolean isFuseMounted(int i) {
            boolean contains;
            synchronized (StorageManagerService.this.mLock) {
                contains = StorageManagerService.this.mFuseMountedUser.contains(Integer.valueOf(i));
            }
            return contains;
        }

        public boolean prepareStorageDirs(int i, Set set, String str) {
            synchronized (StorageManagerService.this.mLock) {
                if (!StorageManagerService.this.mFuseMountedUser.contains(Integer.valueOf(i))) {
                    Slog.w("StorageManagerService", "User " + i + " is not unlocked yet so skip mounting obb");
                    return false;
                }
                try {
                    IVold asInterface = IVold.Stub.asInterface(ServiceManager.getServiceOrThrow("vold"));
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        String str2 = (String) it.next();
                        Locale locale = Locale.US;
                        asInterface.ensureAppDirsCreated(new String[]{String.format(locale, "/storage/emulated/%d/Android/obb/%s/", Integer.valueOf(i), str2), String.format(locale, "/storage/emulated/%d/Android/data/%s/", Integer.valueOf(i), str2)}, UserHandle.getUid(i, StorageManagerService.this.mPmInternal.getPackage(str2).getUid()));
                    }
                    return true;
                } catch (ServiceManager.ServiceNotFoundException | RemoteException e) {
                    Slog.e("StorageManagerService", "Unable to create obb and data directories for " + str, e);
                    return false;
                }
            }
        }

        public int getExternalStorageMountMode(int i, String str) {
            int mountModeInternal = StorageManagerService.this.getMountModeInternal(i, str);
            if (StorageManagerService.LOCAL_LOGV) {
                Slog.v("StorageManagerService", "Resolved mode " + mountModeInternal + " for " + str + "/" + UserHandle.formatUid(i));
            }
            return mountModeInternal;
        }

        public boolean hasExternalStorageAccess(int i, String str) {
            try {
                int checkOperation = StorageManagerService.this.mIAppOpsService.checkOperation(92, i, str);
                return checkOperation == 3 ? StorageManagerService.this.mIPackageManager.checkUidPermission("android.permission.MANAGE_EXTERNAL_STORAGE", i) == 0 : checkOperation == 0;
            } catch (RemoteException e) {
                Slog.w("Failed to check MANAGE_EXTERNAL_STORAGE access for " + str, e);
                return false;
            }
        }

        public void addResetListener(StorageManagerInternal.ResetListener resetListener) {
            synchronized (this.mResetListeners) {
                this.mResetListeners.add(resetListener);
            }
        }

        public void onReset(IVold iVold) {
            synchronized (this.mResetListeners) {
                Iterator it = this.mResetListeners.iterator();
                while (it.hasNext()) {
                    ((StorageManagerInternal.ResetListener) it.next()).onReset(iVold);
                }
            }
        }

        public void resetUser(int i) {
            StorageManagerService.this.mHandler.obtainMessage(10).sendToTarget();
        }

        public boolean hasLegacyExternalStorage(int i) {
            boolean contains;
            synchronized (StorageManagerService.this.mLock) {
                contains = StorageManagerService.this.mUidsWithLegacyExternalStorage.contains(Integer.valueOf(i));
            }
            return contains;
        }

        public void prepareAppDataAfterInstall(String str, int i) {
            for (File file : new Environment.UserEnvironment(UserHandle.getUserId(i)).buildExternalStorageAppObbDirs(str)) {
                if (file.getPath().startsWith(Environment.getDataPreloadsMediaDirectory().getPath())) {
                    Slog.i("StorageManagerService", "Skipping app data preparation for " + file);
                } else {
                    try {
                        StorageManagerService.this.mVold.fixupAppDir(file.getCanonicalPath() + "/", i);
                    } catch (RemoteException | ServiceSpecificException e) {
                        Log.e("StorageManagerService", "Failed to fixup app dir for " + str, e);
                    } catch (IOException unused) {
                        Log.e("StorageManagerService", "Failed to get canonical path for " + str);
                    }
                }
            }
        }

        public boolean isExternalStorageService(int i) {
            return StorageManagerService.this.mMediaStoreAuthorityAppId == UserHandle.getAppId(i);
        }

        public void freeCache(String str, long j) {
            try {
                StorageManagerService.this.mStorageSessionController.freeCache(str, j);
            } catch (StorageSessionController.ExternalStorageServiceException e) {
                Log.e("StorageManagerService", "Failed to free cache of vol : " + str, e);
            }
        }

        public boolean hasExternalStorage(int i, String str) {
            return i == 1000 || getExternalStorageMountMode(i, str) != 0;
        }

        public final void killAppForOpChange(int i, int i2) {
            IActivityManager service = ActivityManager.getService();
            try {
                service.killUid(UserHandle.getAppId(i2), -1, AppOpsManager.opToName(i) + " changed.");
            } catch (RemoteException unused) {
            }
        }

        public void onAppOpsChanged(int i, int i2, String str, int i3, int i4) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i == 66) {
                    if (i4 == 0 && i3 != 0) {
                        killAppForOpChange(i, i2);
                    }
                } else if (i == 87) {
                    StorageManagerService.this.updateLegacyStorageApps(str, i2, i3 == 0);
                } else {
                    if (i != 92) {
                        return;
                    }
                    if (i3 != 0) {
                        killAppForOpChange(i, i2);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getPrimaryVolumeIds() {
            ArrayList arrayList = new ArrayList();
            synchronized (StorageManagerService.this.mLock) {
                for (int i = 0; i < StorageManagerService.this.mVolumes.size(); i++) {
                    VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.valueAt(i);
                    if (volumeInfo.isPrimary()) {
                        arrayList.add(volumeInfo.getId());
                    }
                }
            }
            return arrayList;
        }

        public void markCeStoragePrepared(int i) {
            synchronized (StorageManagerService.this.mLock) {
                StorageManagerService.this.mCeStoragePreparedUsers.add(Integer.valueOf(i));
            }
        }

        public boolean isCeStoragePrepared(int i) {
            boolean contains;
            synchronized (StorageManagerService.this.mLock) {
                contains = StorageManagerService.this.mCeStoragePreparedUsers.contains(Integer.valueOf(i));
            }
            return contains;
        }

        public void registerCloudProviderChangeListener(StorageManagerInternal.CloudProviderChangeListener cloudProviderChangeListener) {
            this.mCloudProviderChangeListeners.add(cloudProviderChangeListener);
            StorageManagerService.this.mHandler.obtainMessage(16, cloudProviderChangeListener).sendToTarget();
        }
    }

    public class DirEncryptListner extends IDirEncryptServiceListener.Stub {
        public DirEncryptListner() {
        }

        public void onEncryptionStatusChanged(String str, int i, String str2, int i2, int i3) {
            final VolumeInfo findVolumeByIdOrThrow = StorageManagerService.this.findVolumeByIdOrThrow(str);
            if (findVolumeByIdOrThrow.getType() == 0 && findVolumeByIdOrThrow.disk.isSd() && "done".equals(str2)) {
                try {
                    StorageManagerService.this.mStorageSessionController.onVolumeUnmount(findVolumeByIdOrThrow);
                    StorageManagerService.this.mVold.sdeMoveMountHidden(findVolumeByIdOrThrow.id, findVolumeByIdOrThrow.mountFlags, findVolumeByIdOrThrow.mountUserId, new IVoldMountCallback.Stub() { // from class: com.android.server.StorageManagerService.DirEncryptListner.1
                        @Override // android.os.IVoldMountCallback
                        public boolean onVolumeChecking(FileDescriptor fileDescriptor, String str3, String str4) {
                            VolumeInfo volumeInfo = findVolumeByIdOrThrow;
                            volumeInfo.path = str3;
                            volumeInfo.internalPath = str4;
                            ParcelFileDescriptor parcelFileDescriptor = new ParcelFileDescriptor(fileDescriptor);
                            try {
                                try {
                                    StorageManagerService.this.mStorageSessionController.onVolumeMount(parcelFileDescriptor, findVolumeByIdOrThrow);
                                    try {
                                        parcelFileDescriptor.close();
                                        return true;
                                    } catch (Exception e) {
                                        Slog.e("StorageManagerService", "Failed to close FUSE device fd", e);
                                        return true;
                                    }
                                } catch (Throwable th) {
                                    try {
                                        parcelFileDescriptor.close();
                                    } catch (Exception e2) {
                                        Slog.e("StorageManagerService", "Failed to close FUSE device fd", e2);
                                    }
                                    throw th;
                                }
                            } catch (StorageSessionController.ExternalStorageServiceException e3) {
                                Slog.e("StorageManagerService", "Failed to mount volume " + findVolumeByIdOrThrow, e3);
                                Slog.i("StorageManagerService", "Scheduling reset in 10s");
                                StorageManagerService.this.mHandler.removeMessages(10);
                                StorageManagerService.this.mHandler.sendMessageDelayed(StorageManagerService.this.mHandler.obtainMessage(10), TimeUnit.SECONDS.toMillis((long) 10));
                                try {
                                    parcelFileDescriptor.close();
                                    return false;
                                } catch (Exception e4) {
                                    Slog.e("StorageManagerService", "Failed to close FUSE device fd", e4);
                                    return false;
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    Slog.wtf("StorageManagerService", e);
                }
            }
        }
    }

    public final boolean initDirEncryptService() {
        Slog.i("StorageManagerService", "initDirEncryptService");
        if (this.mDem == null) {
            this.mDem = new SemSdCardEncryption(this.mContext);
        }
        if (this.mDirEncryptListner != null) {
            return true;
        }
        DirEncryptListner dirEncryptListner = new DirEncryptListner();
        this.mDirEncryptListner = dirEncryptListner;
        this.mDem.registerListener(dirEncryptListner);
        Slog.d("StorageManagerService", "register direncryption listener");
        return true;
    }

    public int encryptExternalStorage(boolean z) {
        final VolumeInfo volumeInfo;
        int i;
        List volumes = ((StorageManager) this.mContext.getSystemService(StorageManager.class)).getVolumes();
        Collections.sort(volumes, VolumeInfo.getDescriptionComparator());
        Iterator it = volumes.iterator();
        while (true) {
            if (!it.hasNext()) {
                volumeInfo = null;
                break;
            }
            volumeInfo = (VolumeInfo) it.next();
            if (volumeInfo != null && volumeInfo.getType() == 0 && volumeInfo.getDisk() != null && volumeInfo.getDisk().isSd()) {
                break;
            }
        }
        if (volumeInfo == null) {
            Slog.e("StorageManagerService", "sdVolume == null");
            return -1;
        }
        if (hasDeviceRestriction("readonly_storage")) {
            Slog.w("StorageManagerService", "Policy has restriction 'readonly_storage'; readonly");
            i = Integer.MIN_VALUE;
        } else {
            i = 0;
        }
        Slog.i("StorageManagerService", "state : " + volumeInfo.getState());
        volumeInfo.getState();
        try {
            if (this.mDem != null) {
                this.mStorageSessionController.onVolumeUnmount(volumeInfo);
                this.mVold.sdeEnable(volumeInfo.id, volumeInfo.mountFlags | i, volumeInfo.mountUserId, z, this.mDem.getListener(), new IVoldMountCallback.Stub() { // from class: com.android.server.StorageManagerService.13
                    @Override // android.os.IVoldMountCallback
                    public boolean onVolumeChecking(FileDescriptor fileDescriptor, String str, String str2) {
                        VolumeInfo volumeInfo2 = volumeInfo;
                        volumeInfo2.path = str;
                        volumeInfo2.internalPath = str2;
                        ParcelFileDescriptor parcelFileDescriptor = new ParcelFileDescriptor(fileDescriptor);
                        try {
                            try {
                                StorageManagerService.this.mStorageSessionController.onVolumeMount(parcelFileDescriptor, volumeInfo);
                                try {
                                    parcelFileDescriptor.close();
                                    return true;
                                } catch (Exception e) {
                                    Slog.e("StorageManagerService", "Failed to close FUSE device fd", e);
                                    return true;
                                }
                            } catch (Throwable th) {
                                try {
                                    parcelFileDescriptor.close();
                                } catch (Exception e2) {
                                    Slog.e("StorageManagerService", "Failed to close FUSE device fd", e2);
                                }
                                throw th;
                            }
                        } catch (StorageSessionController.ExternalStorageServiceException e3) {
                            Slog.e("StorageManagerService", "Failed to mount volume " + volumeInfo, e3);
                            Slog.i("StorageManagerService", "Scheduling reset in 10s");
                            StorageManagerService.this.mHandler.removeMessages(10);
                            StorageManagerService.this.mHandler.sendMessageDelayed(StorageManagerService.this.mHandler.obtainMessage(10), TimeUnit.SECONDS.toMillis((long) 10));
                            try {
                                parcelFileDescriptor.close();
                                return false;
                            } catch (Exception e4) {
                                Slog.e("StorageManagerService", "Failed to close FUSE device fd", e4);
                                return false;
                            }
                        }
                    }
                });
            }
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    public int semGetExternalSdCardHealthState() {
        File file = new File("/sys/class/sec/sdinfo/fc");
        int i = -1;
        if (!file.exists()) {
            Slog.d("StorageManagerService", "EXTERNAL SD CARD HEALTH CHECK FILE does not exist!!");
            return -1;
        }
        String str = "empty";
        try {
            str = FileUtils.readTextFile(file, 0, null).trim();
            Slog.d("StorageManagerService", "External SD Card Health State [" + str + "]");
        } catch (Exception e) {
            Slog.e("StorageManagerService", "Error at reading SD Card Health State", e);
        }
        if ("GOOD".equals(str)) {
            i = 0;
        } else if ("BAD".equals(str)) {
            i = 1;
        }
        Slog.d("StorageManagerService", "semGetExternalSdCardHealthState returns [" + i + "]");
        return i;
    }

    public String semGetExternalSdCardId() {
        String str;
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager.checkSignatures("android", packageManager.getNameForUid(Binder.getCallingUid())) != 0) {
            Slog.e("StorageManagerService", "The caller is not qualified.");
            return null;
        }
        File file = new File("/sys/class/sec/sdinfo/data");
        String trim = SystemProperties.get("ro.build.type").trim();
        if (!file.exists()) {
            Slog.d("StorageManagerService", "EXTERNAL SD CARD CID VALUE FILE does not exist!!");
            return null;
        }
        try {
            str = FileUtils.readTextFile(file, 0, null).trim();
        } catch (Exception e) {
            e = e;
            str = "empty";
        }
        try {
            if (trim.equalsIgnoreCase("eng") || trim.equalsIgnoreCase("userdebug")) {
                Slog.d("StorageManagerService", "External SD Card CID [" + str + "]");
            }
        } catch (Exception e2) {
            e = e2;
            Slog.e("StorageManagerService", "Error at reading SD Card CID Value", e);
            if ("empty".equals(str)) {
            }
            Slog.d("StorageManagerService", "External SD Card CID value -> return as null");
            return null;
        }
        if ("empty".equals(str) && !"No Card".equalsIgnoreCase(str)) {
            return str;
        }
        Slog.d("StorageManagerService", "External SD Card CID value -> return as null");
        return null;
    }

    public long getUsedF2fsFileNode() {
        try {
            return this.mVold.getUsedF2fsFileNode();
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1L;
        }
    }

    public final boolean isValidPath(String str) {
        try {
            String canonicalPath = new File(str).getCanonicalPath();
            if (canonicalPath == null) {
                return false;
            }
            if (!canonicalPath.startsWith("/data/media") && !canonicalPath.startsWith("/data/sec")) {
                Slog.d("StorageManagerService", "input path is not supported");
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isMediaPath(String str) {
        try {
            String canonicalPath = new File(str).getCanonicalPath();
            if (canonicalPath != null) {
                return canonicalPath.startsWith("/data/media");
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean mvFileAtData(String str, String str2) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.SEM_VOLD_DATA_MOVE", null);
        if (!isValidPath(str) || !isValidPath(str2)) {
            Slog.d("StorageManagerService", "mvFileAtData not support other than media or sec dir, so return false");
            return false;
        }
        if (isMediaPath(str) && isMediaPath(str2)) {
            Slog.d("StorageManagerService", "mvFileAtData not support from media to media dir, so return false");
            return false;
        }
        try {
            final CompletableFuture completableFuture = new CompletableFuture();
            this.mVold.mvFileAtData(str, str2, this.mMediaStoreAuthorityAppId, Process.myUid(), new IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.14
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i, PersistableBundle persistableBundle) {
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i, PersistableBundle persistableBundle) {
                    completableFuture.complete(persistableBundle);
                }
            });
            boolean z = ((PersistableBundle) completableFuture.get()).getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT, false);
            StringBuilder sb = new StringBuilder();
            sb.append("Rename in media path result ");
            sb.append(z ? "true" : "false");
            Slog.d("StorageManagerService", sb.toString());
            return z;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public boolean cpFileAtData(String str, String str2) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.SEM_VOLD_DATA_MOVE", null);
        if (!isValidPath(str) || !isValidPath(str2)) {
            Slog.d("StorageManagerService", "cpFileAtData not support other than media or sec dir, so return false");
            return false;
        }
        if (isMediaPath(str) && isMediaPath(str2)) {
            Slog.d("StorageManagerService", "cpFileAtData not support from media to media dir, so return false");
            return false;
        }
        try {
            final CompletableFuture completableFuture = new CompletableFuture();
            this.mVold.cpFileAtData(str, str2, this.mMediaStoreAuthorityAppId, Process.myUid(), new IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.15
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i, PersistableBundle persistableBundle) {
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i, PersistableBundle persistableBundle) {
                    completableFuture.complete(persistableBundle);
                }
            });
            boolean z = ((PersistableBundle) completableFuture.get()).getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT, false);
            StringBuilder sb = new StringBuilder();
            sb.append("File copy in media path result ");
            sb.append(z ? "true" : "false");
            Slog.d("StorageManagerService", sb.toString());
            return z;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    /* renamed from: com.android.server.StorageManagerService$16 */
    public class C033216 extends BroadcastReceiver {
        public C033216() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.d("StorageManagerService", "mRestartSdcardBadremoveReceiver :: get Intent RESTART_OF_SDCARDBADREMOVED_HASAPK");
            if ("com.samsung.intent.action.RESTART_OF_SDCARDBADREMOVED_HASAPK".equals(action)) {
                new Thread(new Runnable() { // from class: com.android.server.StorageManagerService$16$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        StorageManagerService.C033216.this.lambda$onReceive$0();
                    }
                }).start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0() {
            ((PowerManager) StorageManagerService.this.mContext.getSystemService(PowerManager.class)).reboot("RESTART_OF_SDCARDBADREMOVED_HASAPK");
        }
    }

    public final boolean isMountDisallowedByEAS(boolean z) {
        Slog.w("StorageManagerService", "isMountDisallowedByEAS: storage_itpolicy from_intent " + z);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Slog.w("StorageManagerService", "isMountDisallowedByEAS: DevicePolicyManager is NULL");
            return false;
        }
        boolean semGetAllowStorageCard = devicePolicyManager.semGetAllowStorageCard(null);
        if (!semGetAllowStorageCard) {
            if (z && semGetAllowStorageCard == this.preSDPolicy) {
                Slog.w("StorageManagerService", "policy has restriction but not show noti storage_itpolicy");
                return true;
            }
            this.preSDPolicy = false;
            Slog.d("StorageManagerService", "policy has restriction storage_itpolicy");
            Slog.d("StorageManagerService", "Send ACTION_SDCARD_ITPOLICY_TOAST_EVENT INTENT!!");
            this.mContext.sendBroadcastAsUser(new Intent("com.samsung.intent.action.SDCARD_ITPOLICY_TOAST_EVENT"), UserHandle.ALL);
            return true;
        }
        Slog.w("StorageManagerService", "isMountDisallowedByEAS: check is true");
        this.preSDPolicy = true;
        return false;
    }

    public final void applyCurrentSdCardPolicy(boolean z) {
        Slog.d("StorageManagerService", "applyCurrentSdCardPolicy :: " + z);
        if (z) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                String str = volumeInfo.path;
                String id = volumeInfo.getId();
                if (volumeInfo.getType() != 2 && volumeInfo.getDiskId() != null) {
                    Slog.d("StorageManagerService", "removable storage path :: " + str);
                    try {
                        if (volumeInfo.state == 1) {
                            int i2 = 30;
                            while (findVolumeByIdOrThrow(id).getState() == 1) {
                                int i3 = i2 - 1;
                                if (i2 >= 0) {
                                    try {
                                        Thread.sleep(1000L);
                                        i2 = i3;
                                    } catch (InterruptedException e) {
                                        Slog.e("StorageManagerService", "Interrupted while waiting for media", e);
                                    }
                                }
                                i2 = i3;
                            }
                            if (i2 == 0) {
                                Slog.e("StorageManagerService", "Timed out waiting for media to check");
                            }
                        }
                        unmount(id);
                    } catch (IllegalArgumentException e2) {
                        Slog.e("StorageManagerService", "volume removed, while waiting ", e2);
                    }
                }
            }
        }
    }

    public void mountBySecApp(String str, String str2) {
        if (chkMountUnmountFormatCaller(str2)) {
            Slog.d("StorageManagerService", "Mount call accepted");
            mount(str);
        } else {
            throw new SecurityException("Mount call denied to [" + str2 + "]");
        }
    }

    public void unmountBySecApp(String str, String str2) {
        if (chkMountUnmountFormatCaller(str2)) {
            Slog.d("StorageManagerService", "Unmount call accepted");
            unmount(str);
        } else {
            throw new SecurityException("Unmount call denied to [" + str2 + "]");
        }
    }

    public void formatBySecApp(String str, String str2) {
        if (chkMountUnmountFormatCaller(str2)) {
            Slog.d("StorageManagerService", "Format call accepted");
            String str3 = null;
            for (VolumeInfo volumeInfo : ((StorageManager) this.mContext.getSystemService(StorageManager.class)).getVolumes()) {
                if (Objects.equals(volumeInfo.id, str)) {
                    str3 = volumeInfo.getDiskId();
                }
            }
            if (str3 == null) {
                Slog.d("StorageManagerService", "No matched storage to format by SecApp, so just return");
                return;
            } else {
                partitionPublic(str3);
                return;
            }
        }
        throw new SecurityException("Format call denied to [" + str2 + "]");
    }

    public final boolean chkMountUnmountFormatCaller(String str) {
        if ("com.sec.android.app.myfiles".equalsIgnoreCase(str)) {
            return true;
        }
        Slog.d("StorageManagerService", "Caller is not MyFiles");
        return false;
    }

    public int mountVolume(String str) {
        Slog.i("StorageManagerService", "mountVolume : " + str);
        mount(findVolumeIdForPathOrThrow(str));
        return 0;
    }

    public void unmountVolume(String str, boolean z, boolean z2) {
        Slog.i("StorageManagerService", "unmountVolume :" + str);
        unmount(findVolumeIdForPathOrThrow(str));
    }

    public final boolean hasDeviceRestriction(String str) {
        Slog.w("StorageManagerService", "hasDeviceRestriction: " + str);
        if ("usbhost_storage".equals(str)) {
            return hasSecRestriction(str, "content://com.sec.knox.provider/RestrictionPolicy4", "isUsbHostStorageAllowed", new String[]{"true"});
        }
        if ("sdcard_storage".equals(str)) {
            return hasSecRestriction(str, "content://com.sec.knox.provider/RestrictionPolicy3", "isSdCardEnabled", null);
        }
        if ("mass_storage".equals(str)) {
            return hasSecRestriction(str, "content://com.sec.knox.provider/RestrictionPolicy4", "isUsbMassStorageEnabled", new String[]{"true"});
        }
        if ("readonly_storage".equals(str)) {
            return hasSecRestriction(str, "content://com.sec.knox.provider/RestrictionPolicy4", "isSDCardWriteAllowed", null);
        }
        Slog.d("StorageManagerService", "not define policy !!!");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean hasSecRestriction(String str, String str2, String str3, String[] strArr) {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse(str2), null, str3, strArr, null);
        try {
            try {
                if (query != null) {
                    query.moveToFirst();
                    String string = query.getString(query.getColumnIndex(str3));
                    Slog.d("StorageManagerService", "policy : " + str + ", restrict(allow) = " + string);
                    boolean equals = "false".equals(string);
                    str = equals;
                    if (equals != 0) {
                        query.close();
                        return true;
                    }
                } else {
                    Slog.e("StorageManagerService", str + " policy not yet?");
                    str = str;
                }
                if (query == null) {
                    return false;
                }
            } catch (Exception e) {
                Slog.e("StorageManagerService", "policy : " + str + ", return exception", e);
                if (query == null) {
                    return false;
                }
            }
            query.close();
            return false;
        } catch (Throwable th) {
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    public final void checkCallerPermissionIsSystemOrRoot() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0) {
            throw new SecurityException("System can call this API");
        }
    }

    public boolean setSensitive(int i, String str) {
        if (str == null) {
            return false;
        }
        try {
            if (str.isEmpty()) {
                return false;
            }
            return this.mVold.setSensitive(i, str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public boolean isSensitive(String str) {
        if (str == null) {
            return false;
        }
        try {
            if (str.isEmpty()) {
                return false;
            }
            return this.mVold.isSensitive(str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public boolean mountSdpMediaStorageCmd(int i) {
        try {
            return this.mVold.mountSdpMediaStorageCmd(i);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public boolean setSdpPolicyCmd(int i) {
        try {
            return this.mVold.setSdpPolicyCmd(i);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public boolean setSdpPolicyToPathCmd(int i, String str) {
        try {
            return this.mVold.setSdpPolicyToPathCmd(i, str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public boolean setDualDARPolicyCmd(int i, int i2) {
        checkCallerPermissionIsSystemOrRoot();
        try {
            return this.mVold.setDualDARPolicyCmd(i, i2);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public boolean shrinkDataDdp(long j) {
        try {
            return this.mVold.shrinkDataDdp(j);
        } catch (Exception unused) {
            Slog.e("StorageManagerService", "[DDP] Failed to shrink data partition");
            return false;
        }
    }

    public int reserveDataBlocks(long j) {
        try {
            return this.mVold.reserveDataBlocks(j);
        } catch (Exception unused) {
            Slog.e("StorageManagerService", "[DDP] Failed to set reserved_blocks");
            return -1;
        }
    }

    public String getVolumeState(String str) {
        if (str == null) {
            return "unknown";
        }
        synchronized (this.mLock) {
            VolumeInfo[] volumeInfoArr = new VolumeInfo[this.mVolumes.size()];
            for (int i = 0; i < this.mVolumes.size(); i++) {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                volumeInfoArr[i] = volumeInfo;
                if (str.equals(volumeInfo.path)) {
                    Slog.d("StorageManagerService", "getVolumeState: path=" + str + ", state=" + volumeInfoArr[i].state + " From pid=" + Binder.getCallingPid());
                    return VolumeInfo.getEnvironmentForState(volumeInfoArr[i].state);
                }
            }
            Slog.d("StorageManagerService", "getVolumeState: No matched Volume");
            return "unknown";
        }
    }

    public final boolean checkExternalSecureContainerMounted() {
        DiskInfo disk;
        for (VolumeInfo volumeInfo : ((StorageManager) this.mContext.getSystemService(StorageManager.class)).getVolumes()) {
            if (volumeInfo.getState() == 2 && (disk = volumeInfo.getDisk()) != null && disk.isSd()) {
                Slog.d("StorageManagerService", "Description : " + volumeInfo.getDescription() + " state : " + volumeInfo.getState());
                return true;
            }
        }
        return false;
    }

    public String[] getSecureContainerList() {
        String[] strArr;
        enforcePermission("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        try {
            strArr = this.mVold.asecList();
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            strArr = new String[0];
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        synchronized (this.mAsecMountSet) {
            if (!checkExternalSecureContainerMounted()) {
                ArrayList arrayList2 = new ArrayList();
                Iterator it = this.mAsecMountSet.iterator();
                int i = 0;
                while (it.hasNext()) {
                    PackageInstalledMap packageInstalledMap = (PackageInstalledMap) it.next();
                    if (packageInstalledMap.external) {
                        arrayList2.add(packageInstalledMap.f1619id);
                        i++;
                    }
                }
                if (i > 0) {
                    Slog.d("StorageManagerService", "getSecureContainerList : (no media) lists = " + i);
                }
                arrayList.addAll(arrayList2);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) {
        int i3;
        enforcePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            this.mVold.asecCreate(str, i, str2, str3, i2, z);
            i3 = 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            i3 = -1;
        }
        if (i3 == 0) {
            synchronized (this.mAsecMountSet) {
                if (z) {
                    this.mAsecMountSet.add(new PackageInstalledMap(str, true));
                } else {
                    this.mAsecMountSet.add(new PackageInstalledMap(str, false));
                }
            }
        }
        return i3;
    }

    public int resizeSecureContainer(String str, int i, String str2) {
        enforcePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            this.mVold.asecResize(str, i, str2);
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    public int finalizeSecureContainer(String str) {
        enforcePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            this.mVold.asecFinalize(str);
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    public int fixPermissionsSecureContainer(String str, int i, String str2) {
        Slog.d("StorageManagerService", "fixPermissionsSecureContainer: id=" + str + " gid=" + i + " filename=" + str2);
        enforcePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        if (str2 == null) {
            str2 = "";
        }
        try {
            this.mVold.asecFixperms(str, i, str2);
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    public int destroySecureContainer(String str, boolean z) {
        int i;
        enforcePermission("android.permission.ASEC_DESTROY");
        warnOnNotMounted();
        Runtime.getRuntime().gc();
        try {
            this.mVold.asecDestroy(str, z);
            i = 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            i = -1;
        }
        if (i == 0) {
            synchronized (this.mAsecMountSet) {
                if (this.mAsecMountSet.contains(new PackageInstalledMap(str))) {
                    this.mAsecMountSet.remove(new PackageInstalledMap(str));
                }
            }
        }
        return i;
    }

    public int mountSecureContainer(String str, String str2, int i, boolean z) {
        int i2;
        enforcePermission("android.permission.ASEC_MOUNT_UNMOUNT");
        warnOnNotMounted();
        synchronized (this.mAsecMountSet) {
            if (this.mAsecMountSet.contains(new PackageInstalledMap(str))) {
                return -6;
            }
            try {
                this.mVold.asecMount(str, str2, i, z);
                i2 = 0;
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
                i2 = -1;
            }
            String secureContainerFilesystemPath = getSecureContainerFilesystemPath(str);
            if (i2 == 0) {
                synchronized (this.mAsecMountSet) {
                    if (secureContainerFilesystemPath != null) {
                        if (secureContainerFilesystemPath.startsWith("/data/app-asec/")) {
                            this.mAsecMountSet.add(new PackageInstalledMap(str, false));
                        }
                    }
                    this.mAsecMountSet.add(new PackageInstalledMap(str, true));
                }
            }
            return i2;
        }
    }

    public final void cleanupSercureContainerList(String str) {
        synchronized (this.mAsecMountSet) {
            if ("all".equals(str)) {
                this.mAsecMountSet.clear();
            } else {
                Iterator it = this.mAsecMountSet.iterator();
                while (it.hasNext()) {
                    PackageInstalledMap packageInstalledMap = (PackageInstalledMap) it.next();
                    if (packageInstalledMap.external && "external".equals(str)) {
                        it.remove();
                    } else if (!packageInstalledMap.external && "internal".equals(str)) {
                        it.remove();
                    }
                }
            }
        }
    }

    public final boolean isExternalSecureContainer(String str) {
        boolean z;
        synchronized (this.mAsecMountSet) {
            Iterator it = this.mAsecMountSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                PackageInstalledMap packageInstalledMap = (PackageInstalledMap) it.next();
                if (packageInstalledMap.f1619id.equals(str)) {
                    z = packageInstalledMap.external;
                    break;
                }
            }
        }
        Slog.d("StorageManagerService", "isExternalSecureContainer: id=" + str + " result=" + z);
        return z;
    }

    public int unmountSecureContainer(String str, boolean z) {
        Slog.d("StorageManagerService", "unmountSecureContainer: id=" + str + " force=" + z);
        enforcePermission("android.permission.ASEC_MOUNT_UNMOUNT");
        warnOnNotMounted();
        synchronized (this.mAsecMountSet) {
            if (!this.mAsecMountSet.contains(new PackageInstalledMap(str))) {
                Slog.d("StorageManagerService", "unmountSecureContainer: fail");
                return -5;
            }
            int i = 0;
            if (isExternalSecureContainer(str) && !checkExternalSecureContainerMounted()) {
                Slog.w("StorageManagerService", "unmountSecureContainer: no media");
                this.mAsecMountSet.remove(new PackageInstalledMap(str));
                return 0;
            }
            Runtime.getRuntime().gc();
            try {
                this.mVold.asecUnmount(str, z);
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
                i = -1;
            }
            if (i == 0) {
                synchronized (this.mAsecMountSet) {
                    this.mAsecMountSet.remove(new PackageInstalledMap(str));
                }
            }
            return i;
        }
    }

    public boolean isSecureContainerMounted(String str) {
        boolean contains;
        enforcePermission("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        synchronized (this.mAsecMountSet) {
            contains = this.mAsecMountSet.contains(new PackageInstalledMap(str));
            Slog.d("StorageManagerService", "isSecureContainerMounted: id=" + str + " ret=" + contains);
        }
        return contains;
    }

    public int renameSecureContainer(String str, String str2) {
        enforcePermission("android.permission.ASEC_RENAME");
        warnOnNotMounted();
        synchronized (this.mAsecMountSet) {
            if (!this.mAsecMountSet.contains(new PackageInstalledMap(str)) && !this.mAsecMountSet.contains(new PackageInstalledMap(str2))) {
                try {
                    this.mVold.asecRename(str, str2);
                    return 0;
                } catch (Exception e) {
                    Slog.wtf("StorageManagerService", e);
                    return -1;
                }
            }
            return -6;
        }
    }

    public String getSecureContainerPath(String str) {
        Slog.d("StorageManagerService", "getSecureContainerPath: id=" + str);
        enforcePermission("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        if (isExternalSecureContainer(str) && !checkExternalSecureContainerMounted() && this.mAsecMountSet.contains(new PackageInstalledMap(str))) {
            return "/mnt/asec/" + str;
        }
        try {
            return this.mVold.asecPath(str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return null;
        }
    }

    public String getSecureContainerFilesystemPath(String str) {
        enforcePermission("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        try {
            return this.mVold.asecFsPath(str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return null;
        }
    }

    public void finishMediaUpdate() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to call finishMediaUpdate()");
        }
        CountDownLatch countDownLatch = this.mUnmountSignal;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        } else {
            Slog.w("StorageManagerService", "Odd, nobody asked to unmount?");
        }
    }

    public int trimSecureContainer(String str, int i, String str2) {
        enforcePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            this.mVold.asecTrim(str, i, str2);
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    public int getUsedSpaceSecureContainer(String str) {
        enforcePermission("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            return this.mVold.asecGetUsedSpace(str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }
}
