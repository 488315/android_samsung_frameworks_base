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
import android.app.usage.StorageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.ObbInfo;
import android.database.Cursor;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInstalld;
import android.os.IStoraged;
import android.os.IVold;
import android.os.IVoldListener;
import android.os.IVoldMountCallback;
import android.os.IVoldTaskListener;
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
import android.os.storage.ICeStorageLockEventListener;
import android.os.storage.IObbActionListener;
import android.os.storage.IStorageEventListener;
import android.os.storage.IStorageManager;
import android.os.storage.IStorageShutdownObserver;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
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
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.os.AppFuseMount;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.FuseUnavailableMountException;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.storage.AppFuseBridge;
import com.android.server.storage.StorageSessionController;
import com.android.server.storage.StorageUserConnection;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.jdsms.Sender;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.security.SemSdCardEncryption;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StorageManagerService extends IStorageManager.Stub implements Watchdog.Monitor, ActivityTaskManagerInternal.ScreenObserver {
    public static String sMediaStoreAuthorityProcessName;
    public static StorageManagerService sSelf;
    public AppFuseBridge mAppFuseBridge;
    public final Object mAppFuseLock;
    public final HashSet mAsecMountSet;
    public final CountDownLatch mAsecsScanned;
    public volatile boolean mBootCompleted;
    public final Callbacks mCallbacks;
    public final CopyOnWriteArrayList mCeStorageEventCallbacks;
    public final WatchedUnlockedUsers mCeUnlockedUsers;
    public final SparseArray mCloudMediaProviders;
    public final Context mContext;
    public volatile int mCurrentUserId;
    public volatile boolean mDaemonConnected;
    public final ArrayMap mDiskScanLatches;
    public final ArrayMap mDisks;
    public volatile int mDownloadsAuthorityAppId;
    public volatile int mExternalStorageAuthorityAppId;
    public final Callbacks mHandler;
    public HeimdAllFsService mHeimdAllFs;
    public IAppOpsService mIAppOpsService;
    public IPackageManager mIPackageManager;
    public final Installer mInstaller;
    public final HashMap mIsMediaSharedWithParent;
    public long mLastMaintenance;
    public final File mLastMaintenanceFile;
    public final AnonymousClass4 mListener;
    public volatile int mMaxWriteRecords;
    public volatile int mMediaStoreAuthorityAppId;
    public IPackageMoveObserver mMoveCallback;
    public String mMoveTargetUuid;
    public int mNextAppFuseName;
    public final Callbacks mObbActionHandler;
    public final Map mObbMounts;
    public final Map mObbPathToStateMap;
    public final SparseArray mPackageMonitorsForUser;
    public final AnonymousClass1 mPassReceiver;
    public volatile boolean mPassedLifetimeThresh;
    public PackageManagerInternal mPmInternal;
    public final AnonymousClass1 mPolicyReceiver;
    public String mPrimaryStorageUuid;
    public final ArrayMap mRecords;
    public volatile boolean mRemountCurrentUserVolumesOnUnlock;
    public volatile boolean mSecureKeyguardShowing;
    public final AtomicFile mSettingsFile;
    public final StorageManagerInternalImpl mStorageManagerInternal;
    public final StorageSessionController mStorageSessionController;
    public volatile int[] mStorageWriteRecords;
    public volatile IStoraged mStoraged;
    public int[] mSystemUnlockedUsers;
    public final Set mUidsWithLegacyExternalStorage;
    public final Object mUnmountLock;
    public CountDownLatch mUnmountSignal;
    public final AnonymousClass1 mUserReceiver;
    public final SparseIntArray mUserSharesMediaWith;
    public volatile IVold mVold;
    public final boolean mVoldAppDataIsolationEnabled;
    public final ArrayMap mVolumes;
    public final AtomicFile mWriteRecordFile;
    public boolean preSDPolicy;
    public static final boolean LOCAL_LOGV = Log.isLoggable("StorageManagerService", 2);
    public static volatile int sSmartIdleMaintPeriod = 60;
    public static final Pattern KNOWN_APP_DIR_PATHS = Pattern.compile("(?i)(^/storage/[^/]+/(?:([0-9]+)/)?Android/(?:data|media|obb|sandbox)/)([^/]+)(/.*)?");
    public static final List PASS_CLIENTS = Arrays.asList("com.samsung.android.smartsuggestions", "com.samsung.android.moneta", "com.samsung.sept.Security", "com.samsung.android.privateaccesstokens", "com.samsung.android.mcfds");
    public final Set mFuseMountedUser = new ArraySet();
    public final Set mCeStoragePreparedUsers = new ArraySet();
    public volatile long mInternalStorageSize = 0;
    public volatile boolean mNeedGC = true;
    public final Object mLock = LockGuard.installNewLock(4, false);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.StorageManagerService$11, reason: invalid class name */
    public final class AnonymousClass11 extends IVoldTaskListener.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ StorageManagerService this$0;
        public final /* synthetic */ Runnable val$callback;

        public /* synthetic */ AnonymousClass11(StorageManagerService storageManagerService, Runnable runnable, int i) {
            this.$r8$classId = i;
            this.this$0 = storageManagerService;
            this.val$callback = runnable;
        }

        private final void onStatus$com$android$server$StorageManagerService$11(int i, PersistableBundle persistableBundle) {
        }

        private final void onStatus$com$android$server$StorageManagerService$12(int i, PersistableBundle persistableBundle) {
        }

        @Override // android.os.IVoldTaskListener
        public final void onFinished(int i, PersistableBundle persistableBundle) {
            switch (this.$r8$classId) {
                case 0:
                    HeimdAllFsService heimdAllFsService = this.this$0.mHeimdAllFs;
                    if (heimdAllFsService != null) {
                        heimdAllFsService.waitForFinished();
                    }
                    this.this$0.mHeimdAllFs = null;
                    if (this.val$callback != null) {
                        BackgroundThread.getHandler().post(this.val$callback);
                        break;
                    }
                    break;
                default:
                    HeimdAllFsService heimdAllFsService2 = this.this$0.mHeimdAllFs;
                    if (heimdAllFsService2 != null) {
                        heimdAllFsService2.waitForFinished();
                    }
                    this.this$0.mHeimdAllFs = null;
                    if (this.val$callback != null) {
                        BackgroundThread.getHandler().post(this.val$callback);
                        break;
                    }
                    break;
            }
        }

        @Override // android.os.IVoldTaskListener
        public final void onStatus(int i, PersistableBundle persistableBundle) {
            int i2 = this.$r8$classId;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.StorageManagerService$15, reason: invalid class name */
    public final class AnonymousClass15 extends IVoldTaskListener.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object val$result;

        public /* synthetic */ AnonymousClass15(int i, Object obj) {
            this.$r8$classId = i;
            this.val$result = obj;
        }

        private final void onFinished$com$android$server$StorageManagerService$13(int i, PersistableBundle persistableBundle) {
        }

        private final void onStatus$com$android$server$StorageManagerService$15(int i, PersistableBundle persistableBundle) {
        }

        private final void onStatus$com$android$server$StorageManagerService$16(int i, PersistableBundle persistableBundle) {
        }

        @Override // android.os.IVoldTaskListener
        public final void onFinished(int i, PersistableBundle persistableBundle) {
            switch (this.$r8$classId) {
                case 0:
                    ((CompletableFuture) this.val$result).complete(persistableBundle);
                    break;
                case 1:
                    ((CompletableFuture) this.val$result).complete(persistableBundle);
                    break;
            }
        }

        @Override // android.os.IVoldTaskListener
        public final void onStatus(int i, PersistableBundle persistableBundle) {
            switch (this.$r8$classId) {
                case 0:
                case 1:
                    return;
                default:
                    synchronized (((StorageManagerService) this.val$result).mLock) {
                        ((StorageManagerService) this.val$result).onMoveStatusLocked(i);
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.StorageManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ StorageManagerService this$0;

        public /* synthetic */ AnonymousClass5(StorageManagerService storageManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = storageManagerService;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            switch (this.$r8$classId) {
                case 0:
                    Slog.w("StorageManagerService", "storaged died; reconnecting");
                    this.this$0.mStoraged = null;
                    this.this$0.connectStoraged();
                    break;
                default:
                    Slog.w("StorageManagerService", "vold died; reconnecting");
                    this.this$0.mVold = null;
                    this.this$0.connectVold();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.StorageManagerService$9, reason: invalid class name */
    public final class AnonymousClass9 extends IVoldTaskListener.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ StorageManagerService this$0;
        public final /* synthetic */ IVoldTaskListener val$listener;

        public /* synthetic */ AnonymousClass9(StorageManagerService storageManagerService, IVoldTaskListener iVoldTaskListener, int i) {
            this.$r8$classId = i;
            this.this$0 = storageManagerService;
            this.val$listener = iVoldTaskListener;
        }

        @Override // android.os.IVoldTaskListener
        public final void onFinished(int i, PersistableBundle persistableBundle) {
            switch (this.$r8$classId) {
                case 0:
                    StorageManagerService.m88$$Nest$mdispatchOnFinished(this.this$0, this.val$listener, i, persistableBundle);
                    String string = persistableBundle.getString("path");
                    String string2 = persistableBundle.getString("ident");
                    long j = persistableBundle.getLong("create");
                    long j2 = persistableBundle.getLong("run");
                    long j3 = persistableBundle.getLong("destroy");
                    DropBoxManager dropBoxManager = (DropBoxManager) this.this$0.mContext.getSystemService(DropBoxManager.class);
                    StringBuilder sb = new StringBuilder();
                    sb.append(StorageManagerService.m93$$Nest$mscrubPath(this.this$0, string));
                    sb.append(" ");
                    sb.append(string2);
                    BootReceiver$$ExternalSyntheticOutline0.m(sb, " ", j, " ");
                    sb.append(j2);
                    sb.append(" ");
                    sb.append(j3);
                    dropBoxManager.addText("storage_benchmark", sb.toString());
                    synchronized (this.this$0.mLock) {
                        try {
                            VolumeRecord findRecordForPath = this.this$0.findRecordForPath(string);
                            if (findRecordForPath != null) {
                                findRecordForPath.lastBenchMillis = System.currentTimeMillis();
                                this.this$0.writeSettingsLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    StorageManagerService.m88$$Nest$mdispatchOnFinished(this.this$0, this.val$listener, i, persistableBundle);
                    return;
            }
        }

        @Override // android.os.IVoldTaskListener
        public final void onStatus(int i, PersistableBundle persistableBundle) {
            switch (this.$r8$classId) {
                case 0:
                    StorageManagerService.m89$$Nest$mdispatchOnStatus(this.this$0, this.val$listener, i, persistableBundle);
                    return;
                default:
                    StorageManagerService.m89$$Nest$mdispatchOnStatus(this.this$0, this.val$listener, i, persistableBundle);
                    if (i != 0) {
                        return;
                    }
                    String string = persistableBundle.getString("path");
                    long j = persistableBundle.getLong("bytes");
                    long j2 = persistableBundle.getLong("time");
                    ((DropBoxManager) this.this$0.mContext.getSystemService(DropBoxManager.class)).addText("storage_trim", StorageManagerService.m93$$Nest$mscrubPath(this.this$0, string) + " " + j + " " + j2);
                    synchronized (this.this$0.mLock) {
                        try {
                            VolumeRecord findRecordForPath = this.this$0.findRecordForPath(string);
                            if (findRecordForPath != null) {
                                findRecordForPath.lastTrimMillis = System.currentTimeMillis();
                                this.this$0.writeSettingsLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppFuseMountScope extends AppFuseBridge.MountScope {
        public boolean mMounted;

        public AppFuseMountScope(int i, int i2) {
            super(i, i2);
            this.mMounted = false;
        }

        @Override // java.lang.AutoCloseable
        public final void close() {
            StorageManagerService.this.getClass();
            StorageManagerService.extendWatchdogTimeout("#close might be slow");
            if (this.mMounted) {
                BackgroundThread.getHandler().post(new StorageManagerService$$ExternalSyntheticLambda1(2, this));
                this.mMounted = false;
            }
        }

        @Override // com.android.server.storage.AppFuseBridge.MountScope
        public final ParcelFileDescriptor open() {
            StorageManagerService.this.getClass();
            StorageManagerService.extendWatchdogTimeout("#open might be slow");
            try {
                FileDescriptor mountAppFuse = StorageManagerService.this.mVold.mountAppFuse(this.uid, this.mountId);
                this.mMounted = true;
                return new ParcelFileDescriptor(mountAppFuse);
            } catch (Exception e) {
                throw new AppFuseMountException("Failed to mount", e);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Callbacks extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ int $r8$classId;
        public final Object mCallbacks;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Callbacks(Looper looper) {
            super(looper);
            this.$r8$classId = 0;
            this.mCallbacks = new RemoteCallbackList();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ Callbacks(StorageManagerService storageManagerService, Looper looper, int i) {
            super(looper);
            this.$r8$classId = i;
            this.mCallbacks = storageManagerService;
        }

        private final void handleMessage$com$android$server$StorageManagerService$ObbActionHandler(Message message) {
            Iterator it;
            ObbState obbState;
            int i = message.what;
            if (i == 1) {
                ObbAction obbAction = (ObbAction) message.obj;
                obbAction.getClass();
                try {
                    obbAction.handleExecute();
                    return;
                } catch (ObbException e) {
                    Slog.w("StorageManagerService", e);
                    obbAction.notifyObbStateChange(e.status);
                    return;
                }
            }
            if (i != 2) {
                return;
            }
            String str = (String) message.obj;
            synchronized (((StorageManagerService) this.mCallbacks).mObbMounts) {
                try {
                    ArrayList arrayList = new ArrayList();
                    for (ObbState obbState2 : ((HashMap) ((StorageManagerService) this.mCallbacks).mObbPathToStateMap).values()) {
                        if (obbState2.canonicalPath.startsWith(str)) {
                            arrayList.add(obbState2);
                        }
                    }
                    it = arrayList.iterator();
                } catch (RemoteException unused) {
                    Slog.i("StorageManagerService", "Couldn't send unmount notification for  OBB: " + obbState.rawPath);
                } finally {
                }
                while (it.hasNext()) {
                    obbState = (ObbState) it.next();
                    StorageManagerService.m92$$Nest$mremoveObbStateLocked((StorageManagerService) this.mCallbacks, obbState);
                    obbState.token.onObbResult(obbState.rawPath, obbState.nonce, 2);
                }
            }
        }

        public static void invokeCallback(IStorageEventListener iStorageEventListener, int i, SomeArgs someArgs) {
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

        /* JADX WARN: Code restructure failed: missing block: B:126:0x021e, code lost:
        
            if (r0.isUsb() != false) goto L111;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r18) {
            /*
                Method dump skipped, instructions count: 1804
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.StorageManagerService.Callbacks.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExternalStorageServiceAnrController implements AnrController {
        public ExternalStorageServiceAnrController() {
        }

        public final long getAnrDelayMillis(String str, int i) {
            if (!StorageManagerService.this.isAppIoBlocked(i)) {
                return 0L;
            }
            int i2 = DeviceConfig.getInt("storage_native_boot", "anr_delay_millis", 5000);
            Slog.v("StorageManagerService", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i2, "getAnrDelayMillis for ", str, ". ", "ms"));
            return i2;
        }

        public final boolean onAnrDelayCompleted(String str, int i) {
            if (StorageManagerService.this.isAppIoBlocked(i)) {
                Slog.d("StorageManagerService", "onAnrDelayCompleted for " + str + ". Showing ANR dialog...");
                return true;
            }
            Slog.d("StorageManagerService", "onAnrDelayCompleted for " + str + ". Skipping ANR dialog...");
            return false;
        }

        public final void onAnrDelayStarted(String str, int i) {
            if (StorageManagerService.this.isAppIoBlocked(i) && DeviceConfig.getBoolean("storage_native_boot", "anr_delay_notify_external_storage_service", true)) {
                Slog.d("StorageManagerService", "onAnrDelayStarted for " + str + ". Notifying external storage service");
                try {
                    StorageManagerService.this.mStorageSessionController.notifyAnrDelayStarted(i, str);
                } catch (StorageSessionController.ExternalStorageServiceException e) {
                    Slog.e("StorageManagerService", "Failed to notify ANR delay started for " + str, e);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public StorageManagerService mStorageManagerService;

        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i != 500) {
                if (i == 550) {
                    StorageManagerService storageManagerService = this.mStorageManagerService;
                    StorageManagerService storageManagerService2 = StorageManagerService.sSelf;
                    storageManagerService.getClass();
                    ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(storageManagerService);
                    storageManagerService.mHandler.obtainMessage(1).sendToTarget();
                    return;
                }
                if (i == 1000) {
                    StorageManagerService storageManagerService3 = this.mStorageManagerService;
                    storageManagerService3.mBootCompleted = true;
                    storageManagerService3.mHandler.obtainMessage(13).sendToTarget();
                    return;
                }
                return;
            }
            StorageManagerService storageManagerService4 = this.mStorageManagerService;
            StorageManagerService storageManagerService5 = StorageManagerService.sSelf;
            storageManagerService4.getClass();
            storageManagerService4.mPmInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            storageManagerService4.mIPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
            storageManagerService4.mIAppOpsService = IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
            PackageManagerInternal packageManagerInternal = storageManagerService4.mPmInternal;
            ProviderInfo resolveContentProvider = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal).mService.snapshotComputer().resolveContentProvider(UserHandle.getUserId(0), 1000, 786432L, "media");
            if (resolveContentProvider != null) {
                storageManagerService4.mMediaStoreAuthorityAppId = UserHandle.getAppId(resolveContentProvider.applicationInfo.uid);
                StorageManagerService.sMediaStoreAuthorityProcessName = resolveContentProvider.applicationInfo.processName;
            }
            PackageManagerInternal packageManagerInternal2 = storageManagerService4.mPmInternal;
            ProviderInfo resolveContentProvider2 = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal2).mService.snapshotComputer().resolveContentProvider(UserHandle.getUserId(0), 1000, 786432L, "downloads");
            if (resolveContentProvider2 != null) {
                storageManagerService4.mDownloadsAuthorityAppId = UserHandle.getAppId(resolveContentProvider2.applicationInfo.uid);
            }
            PackageManagerInternal packageManagerInternal3 = storageManagerService4.mPmInternal;
            ProviderInfo resolveContentProvider3 = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal3).mService.snapshotComputer().resolveContentProvider(UserHandle.getUserId(0), 1000, 786432L, "com.android.externalstorage.documents");
            if (resolveContentProvider3 != null) {
                storageManagerService4.mExternalStorageAuthorityAppId = UserHandle.getAppId(resolveContentProvider3.applicationInfo.uid);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.StorageManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? storageManagerService = new StorageManagerService(getContext());
            this.mStorageManagerService = storageManagerService;
            publishBinderService("mount", storageManagerService);
            StorageManagerService storageManagerService2 = this.mStorageManagerService;
            storageManagerService2.connectStoraged();
            storageManagerService2.connectVold();
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            final StorageManagerService storageManagerService = this.mStorageManagerService;
            UserHandle userHandle = targetUser.getUserHandle();
            StorageManagerService storageManagerService2 = StorageManagerService.sSelf;
            storageManagerService.getClass();
            int identifier = userHandle.getIdentifier();
            for (ApplicationInfo applicationInfo : storageManagerService.mPmInternal.getInstalledApplications(identifier, Process.myUid(), 4988928L)) {
                try {
                    storageManagerService.updateLegacyStorageApps(applicationInfo.uid, applicationInfo.packageName, storageManagerService.mIAppOpsService.checkOperation(87, applicationInfo.uid, applicationInfo.packageName) == 0);
                } catch (RemoteException e) {
                    Slog.e("StorageManagerService", "Failed to check legacy op for package " + applicationInfo.packageName, e);
                }
            }
            if (storageManagerService.mPackageMonitorsForUser.get(identifier) == null) {
                PackageMonitor packageMonitor = new PackageMonitor() { // from class: com.android.server.StorageManagerService.7
                    public final void onPackageRemoved(String str, int i) {
                        StorageManagerService.this.updateLegacyStorageApps(i, str, false);
                    }
                };
                packageMonitor.register(storageManagerService.mContext, userHandle, storageManagerService.mHandler);
                storageManagerService.mPackageMonitorsForUser.put(identifier, packageMonitor);
            } else {
                Slog.w("StorageManagerService", "PackageMonitor is already registered for: " + identifier);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            StorageManagerService storageManagerService = this.mStorageManagerService;
            int userIdentifier = targetUser.getUserIdentifier();
            StorageManagerService storageManagerService2 = StorageManagerService.sSelf;
            storageManagerService.getClass();
            Slog.d("StorageManagerService", "onUserStopped " + userIdentifier);
            StorageManagerService.extendWatchdogTimeout("#onUserStopped might be slow");
            try {
                storageManagerService.mVold.onUserStopped(userIdentifier);
                storageManagerService.mStoraged.onUserStopped(userIdentifier);
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
            }
            synchronized (storageManagerService.mLock) {
                storageManagerService.mSystemUnlockedUsers = ArrayUtils.removeInt(storageManagerService.mSystemUnlockedUsers, userIdentifier);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            StorageManagerService storageManagerService = this.mStorageManagerService;
            int userIdentifier = targetUser.getUserIdentifier();
            StorageManagerService storageManagerService2 = StorageManagerService.sSelf;
            storageManagerService.getClass();
            Slog.i("StorageManagerService", "onUserStopping " + userIdentifier);
            try {
                storageManagerService.mStorageSessionController.onUserStopping(userIdentifier);
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
            }
            PackageMonitor packageMonitor = (PackageMonitor) storageManagerService.mPackageMonitorsForUser.removeReturnOld(userIdentifier);
            if (packageMonitor != null) {
                packageMonitor.unregister();
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
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
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            StorageManagerService storageManagerService = this.mStorageManagerService;
            int userIdentifier = targetUser.getUserIdentifier();
            StorageManagerService storageManagerService2 = StorageManagerService.sSelf;
            storageManagerService.getClass();
            Slog.d("StorageManagerService", "onUserUnlocking: userId=" + userIdentifier);
            if (userIdentifier != 0) {
                try {
                    UserManager userManager = (UserManager) storageManagerService.mContext.createPackageContextAsUser("system", 0, UserHandle.of(userIdentifier)).getSystemService(UserManager.class);
                    if (userManager != null) {
                        storageManagerService.mIsMediaSharedWithParent.put(Integer.valueOf(userIdentifier), Boolean.valueOf(userManager.isMediaSharedWithParent()));
                    } else {
                        Slog.d("StorageManagerService", "onUserUnlocking: um is null, so just put userId(" + userIdentifier + ") with false");
                        storageManagerService.mIsMediaSharedWithParent.put(Integer.valueOf(userIdentifier), Boolean.FALSE);
                    }
                    if (userManager != null && userManager.isMediaSharedWithParent()) {
                        int i = userManager.getProfileParent(userIdentifier).id;
                        storageManagerService.mUserSharesMediaWith.put(userIdentifier, i);
                        storageManagerService.mUserSharesMediaWith.put(i, userIdentifier);
                    } else if (SemDualAppManager.isDualAppId(userIdentifier)) {
                        storageManagerService.mUserSharesMediaWith.put(userIdentifier, 0);
                        storageManagerService.mUserSharesMediaWith.put(0, userIdentifier);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(userIdentifier, "Failed to create user context for user ", "StorageManagerService");
                }
            }
            try {
                storageManagerService.mStorageSessionController.onUnlockUser(userIdentifier);
                storageManagerService.mVold.onUserStarted(userIdentifier);
                storageManagerService.mStoraged.onUserStarted(userIdentifier);
            } catch (Exception e) {
                Slog.wtf("StorageManagerService", e);
            }
            storageManagerService.mHandler.obtainMessage(14, userIdentifier, 0).sendToTarget();
            if (storageManagerService.mRemountCurrentUserVolumesOnUnlock && userIdentifier == storageManagerService.mCurrentUserId) {
                storageManagerService.maybeRemountVolumes(userIdentifier);
                storageManagerService.mRemountCurrentUserVolumesOnUnlock = false;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MountObbAction extends ObbAction {
        public final int mCallingUid;
        public final ObbInfo mObbInfo;

        public MountObbAction(ObbState obbState, int i, ObbInfo obbInfo) {
            super(obbState);
            this.mCallingUid = i;
            this.mObbInfo = obbInfo;
        }

        @Override // com.android.server.StorageManagerService.ObbAction
        public final void handleExecute() {
            boolean isSameApp;
            boolean containsKey;
            StorageManagerService.this.warnOnNotMounted();
            StorageManagerService storageManagerService = StorageManagerService.this;
            String str = this.mObbInfo.packageName;
            int i = this.mCallingUid;
            if (i == 1000) {
                storageManagerService.getClass();
                isSameApp = true;
            } else {
                isSameApp = ((PackageManagerService.PackageManagerInternalImpl) storageManagerService.mPmInternal).isSameApp(i, UserHandle.getUserId(i), 0L, str);
            }
            if (!isSameApp) {
                throw new ObbException(25, "Denied attempt to mount OBB " + this.mObbInfo.filename + " which is owned by " + this.mObbInfo.packageName);
            }
            synchronized (StorageManagerService.this.mObbMounts) {
                containsKey = ((HashMap) StorageManagerService.this.mObbPathToStateMap).containsKey(this.mObbState.rawPath);
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
                    StorageManagerService.m86$$Nest$maddObbStateLocked(StorageManagerService.this, this.mObbState);
                }
                notifyObbStateChange(1);
            } catch (Exception e) {
                throw new ObbException(21, e);
            }
        }

        public final String toString() {
            return "MountObbAction{" + this.mObbState + '}';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ObbAction {
        public final ObbState mObbState;

        public ObbAction(ObbState obbState) {
            this.mObbState = obbState;
        }

        public abstract void handleExecute();

        public final void notifyObbStateChange(int i) {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ObbState implements IBinder.DeathRecipient {
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

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            StorageManagerService storageManagerService = StorageManagerService.this;
            UnmountObbAction unmountObbAction = storageManagerService.new UnmountObbAction(this, true);
            Callbacks callbacks = storageManagerService.mObbActionHandler;
            callbacks.sendMessage(callbacks.obtainMessage(1, unmountObbAction));
        }

        public final String toString() {
            return "ObbState{rawPath=" + this.rawPath + ",canonicalPath=" + this.canonicalPath + ",ownerGid=" + this.ownerGid + ",token=" + this.token + ",binder=" + this.token.asBinder() + ",volId=" + this.volId + '}';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageInstalledMap {
        public final boolean external;
        public final String id;

        public PackageInstalledMap(String str) {
            this.id = str;
            this.external = true;
        }

        public PackageInstalledMap(String str, boolean z) {
            this.id = str;
            this.external = z;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof PackageInstalledMap) {
                return this.id.equals(((PackageInstalledMap) obj).id);
            }
            return false;
        }

        public final int hashCode() {
            return this.id.hashCode();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class StorageBadRemovalReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra("msg");
            Slog.d("StorageManagerService", "StorageBadRemovalReceiver msg : " + stringExtra);
            if ("reboot".equals(stringExtra)) {
                ((PowerManager) context.getSystemService(PowerManager.class)).reboot("RESTART_OF_SDCARDBADREMOVED_HASAPK");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StorageManagerInternalImpl extends StorageManagerInternal {
        public final List mResetListeners = new ArrayList();
        public final CopyOnWriteArraySet mCloudProviderChangeListeners = new CopyOnWriteArraySet();

        public StorageManagerInternalImpl() {
        }

        public static void killAppForOpChange(int i, int i2) {
            IActivityManager service = ActivityManager.getService();
            try {
                service.killUid(UserHandle.getAppId(i2), -1, AppOpsManager.opToName(i) + " changed.");
            } catch (RemoteException unused) {
            }
        }

        public final void addResetListener(StorageManagerInternal.ResetListener resetListener) {
            synchronized (this.mResetListeners) {
                ((ArrayList) this.mResetListeners).add(resetListener);
            }
        }

        public final IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(ParcelFileDescriptor parcelFileDescriptor, int i) {
            try {
                Installer installer = StorageManagerService.this.mInstaller;
                if (!installer.checkBeforeRemote()) {
                    return null;
                }
                try {
                    return installer.mInstalld.createFsveritySetupAuthToken(parcelFileDescriptor, i);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            } catch (Installer.InstallerException e2) {
                throw new IOException(e2);
            }
        }

        public final int enableFsverity(IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, String str, String str2) {
            try {
                Installer installer = StorageManagerService.this.mInstaller;
                if (!installer.checkBeforeRemote()) {
                    throw new Installer.InstallerException("fs-verity wasn't enabled with an isolated installer");
                }
                BlockGuard.getVmPolicy().onPathAccess(str);
                try {
                    return installer.mInstalld.enableFsverity(iFsveritySetupAuthToken, str, str2);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            } catch (Installer.InstallerException e2) {
                throw new IOException(e2);
            }
        }

        public final void freeCache(String str, long j) {
            try {
                StorageManagerService.this.mStorageSessionController.freeCache(str, j);
            } catch (StorageSessionController.ExternalStorageServiceException e) {
                Log.e("StorageManagerService", "Failed to free cache of vol : " + str, e);
            }
        }

        public final int getExternalStorageMountMode(int i, String str) {
            ApplicationInfo applicationInfo;
            StorageManagerService storageManagerService = StorageManagerService.this;
            storageManagerService.getClass();
            int i2 = 0;
            try {
                if (!Process.isIsolated(i) && !Process.isSdkSandboxUid(i)) {
                    String[] packagesForUid = storageManagerService.mIPackageManager.getPackagesForUid(i);
                    if (!ArrayUtils.isEmpty(packagesForUid)) {
                        String str2 = str == null ? packagesForUid[0] : str;
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (storageManagerService.mPmInternal.isInstantApp(str2, UserHandle.getUserId(i))) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            } else {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                if (storageManagerService.mStorageManagerInternal.isExternalStorageService(i)) {
                                    i2 = 3;
                                } else {
                                    if (storageManagerService.mDownloadsAuthorityAppId != UserHandle.getAppId(i) && storageManagerService.mExternalStorageAuthorityAppId != UserHandle.getAppId(i) && (storageManagerService.mIPackageManager.checkUidPermission("android.permission.ACCESS_MTP", i) != 0 || (applicationInfo = storageManagerService.mIPackageManager.getApplicationInfo(str2, 0L, UserHandle.getUserId(i))) == null || !applicationInfo.isSignedWithPlatformKey())) {
                                        boolean z = storageManagerService.mIPackageManager.checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0;
                                        int length = packagesForUid.length;
                                        int i3 = 0;
                                        while (true) {
                                            if (i3 >= length) {
                                                break;
                                            }
                                            if (storageManagerService.mIAppOpsService.checkOperation(66, i, packagesForUid[i3]) == 0) {
                                                i2 = 1;
                                                break;
                                            }
                                            i3++;
                                        }
                                        if (!z && i2 == 0) {
                                            i2 = 1;
                                        }
                                        i2 = 2;
                                    }
                                    i2 = 4;
                                }
                            }
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                }
            } catch (RemoteException unused) {
            }
            if (StorageManagerService.LOCAL_LOGV) {
                StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i2, "Resolved mode ", " for ", str, "/");
                m.append(UserHandle.formatUid(i));
                Slog.v("StorageManagerService", m.toString());
            }
            return i2;
        }

        public final List getPrimaryVolumeIds() {
            ArrayList arrayList = new ArrayList();
            synchronized (StorageManagerService.this.mLock) {
                for (int i = 0; i < StorageManagerService.this.mVolumes.size(); i++) {
                    try {
                        VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.valueAt(i);
                        if (volumeInfo.isPrimary()) {
                            arrayList.add(volumeInfo.getId());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            return arrayList;
        }

        public final boolean hasExternalStorageAccess(int i, String str) {
            try {
                int checkOperation = StorageManagerService.this.mIAppOpsService.checkOperation(92, i, str);
                return checkOperation == 3 ? StorageManagerService.this.mIPackageManager.checkUidPermission("android.permission.MANAGE_EXTERNAL_STORAGE", i) == 0 : checkOperation == 0;
            } catch (RemoteException e) {
                Slog.w("Failed to check MANAGE_EXTERNAL_STORAGE access for " + str, e);
                return false;
            }
        }

        public final boolean hasLegacyExternalStorage(int i) {
            boolean contains;
            synchronized (StorageManagerService.this.mLock) {
                contains = ((ArraySet) StorageManagerService.this.mUidsWithLegacyExternalStorage).contains(Integer.valueOf(i));
            }
            return contains;
        }

        public final boolean isCeStoragePrepared(int i) {
            boolean contains;
            synchronized (StorageManagerService.this.mLock) {
                contains = ((ArraySet) StorageManagerService.this.mCeStoragePreparedUsers).contains(Integer.valueOf(i));
            }
            return contains;
        }

        public final boolean isExternalStorageService(int i) {
            return StorageManagerService.this.mMediaStoreAuthorityAppId == UserHandle.getAppId(i);
        }

        public final boolean isFuseMounted(int i) {
            boolean contains;
            synchronized (StorageManagerService.this.mLock) {
                contains = ((ArraySet) StorageManagerService.this.mFuseMountedUser).contains(Integer.valueOf(i));
            }
            return contains;
        }

        public final void markCeStoragePrepared(int i) {
            synchronized (StorageManagerService.this.mLock) {
                ((ArraySet) StorageManagerService.this.mCeStoragePreparedUsers).add(Integer.valueOf(i));
            }
        }

        public final void onAppOpsChanged(int i, int i2, String str, int i3, int i4) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i == 66) {
                    if (i4 == 0 && i3 != 0) {
                        killAppForOpChange(i, i2);
                    }
                } else if (i == 87) {
                    StorageManagerService.this.updateLegacyStorageApps(i2, str, i3 == 0);
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

        public final void onReset(IVold iVold) {
            synchronized (this.mResetListeners) {
                try {
                    Iterator it = ((ArrayList) this.mResetListeners).iterator();
                    while (it.hasNext()) {
                        ((StorageManagerInternal.ResetListener) it.next()).onReset(iVold);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void prepareAppDataAfterInstall(String str, int i) {
            for (File file : new Environment.UserEnvironment(UserHandle.getUserId(i)).buildExternalStorageAppObbDirs(str)) {
                if (file.getPath().startsWith(Environment.getDataPreloadsMediaDirectory().getPath())) {
                    Slog.i("StorageManagerService", "Skipping app data preparation for " + file);
                } else {
                    try {
                        StorageManagerService.this.mVold.fixupAppDir(file.getCanonicalPath() + "/", i);
                    } catch (RemoteException | ServiceSpecificException e) {
                        Log.e("StorageManagerService", "Failed to fixup app dir for " + str, e);
                    } catch (IOException unused) {
                        StorageManagerService$$ExternalSyntheticOutline0.m("Failed to get canonical path for ", str, "StorageManagerService");
                    }
                }
            }
        }

        public final boolean prepareStorageDirs(int i, Set set, String str) {
            synchronized (StorageManagerService.this.mLock) {
                try {
                    if (!((ArraySet) StorageManagerService.this.mFuseMountedUser).contains(Integer.valueOf(i))) {
                        Slog.w("StorageManagerService", "User " + i + " is not unlocked yet so skip mounting obb");
                        return false;
                    }
                    try {
                        IVold asInterface = IVold.Stub.asInterface(ServiceManager.getServiceOrThrow("vold"));
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            String str2 = (String) it.next();
                            Locale locale = Locale.US;
                            asInterface.ensureAppDirsCreated(new String[]{"/storage/emulated/" + i + "/Android/obb/" + str2 + "/", "/storage/emulated/" + i + "/Android/data/" + str2 + "/"}, UserHandle.getUid(i, StorageManagerService.this.mPmInternal.getPackage(str2).getUid()));
                        }
                        return true;
                    } catch (ServiceManager.ServiceNotFoundException | RemoteException e) {
                        Slog.e("StorageManagerService", "Unable to create obb and data directories for " + str, e);
                        return false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void prepareUserStorageForMove(String str, String str2, List list) {
            try {
                StorageManagerService storageManagerService = StorageManagerService.this;
                storageManagerService.getClass();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    UserInfo userInfo = (UserInfo) it.next();
                    storageManagerService.prepareUserStorageInternal(userInfo.id, 3, str);
                    storageManagerService.prepareUserStorageInternal(userInfo.id, 3, str2);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public final void registerCloudProviderChangeListener(StorageManagerInternal.CloudProviderChangeListener cloudProviderChangeListener) {
            this.mCloudProviderChangeListeners.add(cloudProviderChangeListener);
            StorageManagerService.this.mHandler.obtainMessage(16, cloudProviderChangeListener).sendToTarget();
        }

        public final void registerStorageLockEventListener(ICeStorageLockEventListener iCeStorageLockEventListener) {
            if (StorageManagerService.this.mCeStorageEventCallbacks.add(iCeStorageLockEventListener)) {
                return;
            }
            Slog.w("StorageManagerService", "Failed to register listener: " + iCeStorageLockEventListener);
        }

        public final void resetUser(int i) {
            StorageManagerService.this.mHandler.obtainMessage(10).sendToTarget();
        }

        public final void unregisterStorageLockEventListener(ICeStorageLockEventListener iCeStorageLockEventListener) {
            if (StorageManagerService.this.mCeStorageEventCallbacks.remove(iCeStorageLockEventListener)) {
                return;
            }
            Slog.w("StorageManagerService", "Unregistering " + iCeStorageLockEventListener + " that was not registered");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UnmountObbAction extends ObbAction {
        public final boolean mForceUnmount;

        public UnmountObbAction(ObbState obbState, boolean z) {
            super(obbState);
            this.mForceUnmount = z;
        }

        @Override // com.android.server.StorageManagerService.ObbAction
        public final void handleExecute() {
            ObbState obbState;
            StorageManagerService storageManagerService = StorageManagerService.this;
            StorageManagerService storageManagerService2 = StorageManagerService.sSelf;
            storageManagerService.warnOnNotMounted();
            synchronized (StorageManagerService.this.mObbMounts) {
                obbState = (ObbState) ((HashMap) StorageManagerService.this.mObbPathToStateMap).get(this.mObbState.rawPath);
            }
            if (obbState == null) {
                throw new ObbException(23, "Missing existingState");
            }
            if (obbState.ownerGid != this.mObbState.ownerGid) {
                StringBuilder sb = new StringBuilder("Permission denied to unmount OBB ");
                sb.append(obbState.rawPath);
                sb.append(" (owned by GID ");
                ObbException obbException = new ObbException(25, AmFmBandRange$$ExternalSyntheticOutline0.m(obbState.ownerGid, sb, ")"));
                Slog.w("StorageManagerService", obbException);
                notifyObbStateChange(obbException.status);
                return;
            }
            try {
                StorageManagerService.this.mVold.unmount(this.mObbState.volId);
                StorageManagerService.this.mVold.destroyObb(this.mObbState.volId);
                this.mObbState.volId = null;
                synchronized (StorageManagerService.this.mObbMounts) {
                    StorageManagerService.m92$$Nest$mremoveObbStateLocked(StorageManagerService.this, obbState);
                }
                notifyObbStateChange(2);
            } catch (Exception e) {
                throw new ObbException(22, e);
            }
        }

        public final String toString() {
            return "UnmountObbAction{" + this.mObbState + ",force=" + this.mForceUnmount + '}';
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WatchedUnlockedUsers {
        public int[] users;

        public final String toString() {
            return Arrays.toString(this.users);
        }
    }

    /* renamed from: -$$Nest$maddObbStateLocked, reason: not valid java name */
    public static void m86$$Nest$maddObbStateLocked(StorageManagerService storageManagerService, ObbState obbState) {
        storageManagerService.getClass();
        IBinder asBinder = obbState.token.asBinder();
        List list = (List) ((HashMap) storageManagerService.mObbMounts).get(asBinder);
        if (list == null) {
            list = new ArrayList();
            ((HashMap) storageManagerService.mObbMounts).put(asBinder, list);
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
            obbState.token.asBinder().linkToDeath(obbState, 0);
            ((HashMap) storageManagerService.mObbPathToStateMap).put(obbState.rawPath, obbState);
        } catch (RemoteException e) {
            list.remove(obbState);
            if (list.isEmpty()) {
                ((HashMap) storageManagerService.mObbMounts).remove(asBinder);
            }
            throw e;
        }
    }

    /* renamed from: -$$Nest$mcompleteUnlockUser, reason: not valid java name */
    public static void m87$$Nest$mcompleteUnlockUser(StorageManagerService storageManagerService, int i) {
        storageManagerService.getClass();
        Slog.d("StorageManagerService", "completeUnlockUser: userId=" + i);
        storageManagerService.onKeyguardStateChanged(false);
        synchronized (storageManagerService.mLock) {
            try {
                for (int i2 : storageManagerService.mSystemUnlockedUsers) {
                    if (i2 == i) {
                        Log.i("StorageManagerService", "completeUnlockUser called for already unlocked user:" + i);
                        return;
                    }
                }
                for (int i3 = 0; i3 < storageManagerService.mVolumes.size(); i3++) {
                    VolumeInfo volumeInfo = (VolumeInfo) storageManagerService.mVolumes.valueAt(i3);
                    if (volumeInfo.isVisibleForUser(i) && volumeInfo.isMountedReadable()) {
                        StorageVolume buildStorageVolume = volumeInfo.buildStorageVolume(storageManagerService.mContext, i, false);
                        storageManagerService.mHandler.obtainMessage(6, buildStorageVolume).sendToTarget();
                        String environmentForState = VolumeInfo.getEnvironmentForState(volumeInfo.getState());
                        Callbacks callbacks = storageManagerService.mCallbacks;
                        String path = buildStorageVolume.getPath();
                        int i4 = Callbacks.$r8$clinit;
                        callbacks.getClass();
                        SomeArgs obtain = SomeArgs.obtain();
                        obtain.arg1 = path;
                        obtain.arg2 = environmentForState;
                        obtain.arg3 = environmentForState;
                        callbacks.obtainMessage(1, obtain).sendToTarget();
                    }
                }
                storageManagerService.mSystemUnlockedUsers = ArrayUtils.appendInt(storageManagerService.mSystemUnlockedUsers, i);
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mdispatchOnFinished, reason: not valid java name */
    public static void m88$$Nest$mdispatchOnFinished(StorageManagerService storageManagerService, IVoldTaskListener iVoldTaskListener, int i, PersistableBundle persistableBundle) {
        storageManagerService.getClass();
        if (iVoldTaskListener != null) {
            try {
                iVoldTaskListener.onFinished(i, persistableBundle);
            } catch (RemoteException unused) {
            }
        }
    }

    /* renamed from: -$$Nest$mdispatchOnStatus, reason: not valid java name */
    public static void m89$$Nest$mdispatchOnStatus(StorageManagerService storageManagerService, IVoldTaskListener iVoldTaskListener, int i, PersistableBundle persistableBundle) {
        storageManagerService.getClass();
        if (iVoldTaskListener != null) {
            try {
                iVoldTaskListener.onStatus(i, persistableBundle);
            } catch (RemoteException unused) {
            }
        }
    }

    /* renamed from: -$$Nest$monDiskScannedLocked, reason: not valid java name */
    public static void m90$$Nest$monDiskScannedLocked(StorageManagerService storageManagerService, DiskInfo diskInfo) {
        int i = 0;
        for (int i2 = 0; i2 < storageManagerService.mVolumes.size(); i2++) {
            if (Objects.equals(diskInfo.id, ((VolumeInfo) storageManagerService.mVolumes.valueAt(i2)).getDiskId())) {
                i++;
            }
        }
        int i3 = diskInfo.flags;
        String str = (i3 & 4) != 0 ? "SD" : (i3 & 8) != 0 ? "USB" : "null";
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1("DiskInfo".concat("{" + diskInfo.id + "}:"), "\n"));
        m.append(String.format("  flags=%s size=%d volumeCount=%d", str, Long.valueOf(diskInfo.size), Integer.valueOf(diskInfo.volumeCount)));
        StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m.toString(), "\n"));
        m2.append("  sysPath=" + diskInfo.sysPath);
        Slog.d("StorageManagerService", "onDiskScannedLocked: disk=" + ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m2.toString(), "\n") + " volumeCount=" + i);
        Intent m3 = BatteryService$$ExternalSyntheticOutline0.m(83886080, "android.os.storage.action.DISK_SCANNED");
        m3.putExtra("android.os.storage.extra.DISK_ID", diskInfo.id);
        m3.putExtra("android.os.storage.extra.VOLUME_COUNT", i);
        storageManagerService.mHandler.obtainMessage(7, m3).sendToTarget();
        CountDownLatch countDownLatch = (CountDownLatch) storageManagerService.mDiskScanLatches.remove(diskInfo.id);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
        diskInfo.volumeCount = i;
        Callbacks callbacks = storageManagerService.mCallbacks;
        int i4 = Callbacks.$r8$clinit;
        callbacks.getClass();
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = diskInfo.clone();
        obtain.argi2 = i;
        callbacks.obtainMessage(5, obtain).sendToTarget();
    }

    /* renamed from: -$$Nest$monVolumeCreatedLocked, reason: not valid java name */
    public static void m91$$Nest$monVolumeCreatedLocked(StorageManagerService storageManagerService, VolumeInfo volumeInfo) {
        storageManagerService.getClass();
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        int i = volumeInfo.mountUserId;
        if (i >= 0 && !activityManagerInternal.isUserRunning(i, 0)) {
            Slog.d("StorageManagerService", "Ignoring volume " + volumeInfo.getId() + " because user " + Integer.toString(volumeInfo.mountUserId) + " is no longer running.");
            return;
        }
        int i2 = volumeInfo.type;
        if (i2 == 2) {
            Context createContextAsUser = storageManagerService.mContext.createContextAsUser(UserHandle.of(volumeInfo.mountUserId), 0);
            if (!(createContextAsUser != null ? ((UserManager) createContextAsUser.getSystemService(UserManager.class)).isMediaSharedWithParent() : false)) {
                StorageSessionController storageSessionController = storageManagerService.mStorageSessionController;
                int i3 = volumeInfo.mountUserId;
                storageSessionController.getClass();
                Intent intent = new Intent("android.service.storage.ExternalStorageService");
                intent.setPackage(storageSessionController.mExternalStorageServicePackageName);
                ResolveInfo resolveServiceAsUser = storageSessionController.mContext.getPackageManager().resolveServiceAsUser(intent, 786564, i3);
                if (!((resolveServiceAsUser == null ? null : resolveServiceAsUser.serviceInfo) != null)) {
                    Slog.d("StorageManagerService", "Ignoring volume " + volumeInfo.getId() + " because user " + Integer.toString(volumeInfo.mountUserId) + " does not support external storage.");
                    return;
                }
            }
            VolumeInfo findPrivateForEmulated = ((StorageManager) storageManagerService.mContext.getSystemService(StorageManager.class)).findPrivateForEmulated(volumeInfo);
            if ((Objects.equals(StorageManager.UUID_PRIVATE_INTERNAL, storageManagerService.mPrimaryStorageUuid) && "private".equals(findPrivateForEmulated.id)) || Objects.equals(findPrivateForEmulated.fsUuid, storageManagerService.mPrimaryStorageUuid)) {
                Slog.v("StorageManagerService", "Found primary storage at #1 " + volumeInfo);
                volumeInfo.mountFlags = volumeInfo.mountFlags | 5;
                storageManagerService.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
                return;
            }
            return;
        }
        if (i2 == 0) {
            if ("primary_physical".equals(storageManagerService.mPrimaryStorageUuid) && volumeInfo.disk.isDefaultPrimary()) {
                Slog.v("StorageManagerService", "Found primary storage at #2 " + volumeInfo);
                volumeInfo.mountFlags = volumeInfo.mountFlags | 5;
            }
            if (volumeInfo.disk.isAdoptable() && volumeInfo.disk.isSd()) {
                volumeInfo.mountFlags |= 4;
            }
            volumeInfo.mountUserId = storageManagerService.mCurrentUserId;
            storageManagerService.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        if (i2 == 1) {
            storageManagerService.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        if (i2 != 5) {
            Slog.d("StorageManagerService", "Skipping automatic mounting of " + volumeInfo);
        } else {
            if (volumeInfo.disk.isStubVisible()) {
                volumeInfo.mountFlags |= 4;
            } else {
                volumeInfo.mountFlags |= 2;
            }
            volumeInfo.mountUserId = storageManagerService.mCurrentUserId;
            storageManagerService.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
        }
    }

    /* renamed from: -$$Nest$mremoveObbStateLocked, reason: not valid java name */
    public static void m92$$Nest$mremoveObbStateLocked(StorageManagerService storageManagerService, ObbState obbState) {
        storageManagerService.getClass();
        IBinder asBinder = obbState.token.asBinder();
        List list = (List) ((HashMap) storageManagerService.mObbMounts).get(asBinder);
        if (list != null) {
            if (list.remove(obbState)) {
                obbState.token.asBinder().unlinkToDeath(obbState, 0);
            }
            if (list.isEmpty()) {
                ((HashMap) storageManagerService.mObbMounts).remove(asBinder);
            }
        }
        ((HashMap) storageManagerService.mObbPathToStateMap).remove(obbState.rawPath);
    }

    /* renamed from: -$$Nest$mscrubPath, reason: not valid java name */
    public static String m93$$Nest$mscrubPath(StorageManagerService storageManagerService, String str) {
        storageManagerService.getClass();
        if (str.startsWith(Environment.getDataDirectory().getAbsolutePath())) {
            return "internal";
        }
        VolumeRecord findRecordForPath = storageManagerService.findRecordForPath(str);
        if (findRecordForPath == null || findRecordForPath.createdMillis == 0) {
            return "unknown";
        }
        return AmFmBandRange$$ExternalSyntheticOutline0.m((int) ((System.currentTimeMillis() - findRecordForPath.createdMillis) / 604800000), new StringBuilder("ext:"), "w");
    }

    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.server.StorageManagerService$1] */
    /* JADX WARN: Type inference failed for: r3v13, types: [com.android.server.StorageManagerService$1] */
    /* JADX WARN: Type inference failed for: r3v14, types: [com.android.server.StorageManagerService$4] */
    /* JADX WARN: Type inference failed for: r3v15, types: [com.android.server.StorageManagerService$1] */
    public StorageManagerService(Context context) {
        WatchedUnlockedUsers watchedUnlockedUsers = new WatchedUnlockedUsers();
        int[] iArr = EmptyArray.INT;
        watchedUnlockedUsers.users = iArr;
        UserManager.invalidateIsUserUnlockedCache();
        this.mCeUnlockedUsers = watchedUnlockedUsers;
        this.mSystemUnlockedUsers = iArr;
        this.mDisks = new ArrayMap();
        this.mVolumes = new ArrayMap();
        this.mRecords = new ArrayMap();
        this.mDiskScanLatches = new ArrayMap();
        this.mCloudMediaProviders = new SparseArray();
        this.mMediaStoreAuthorityAppId = -1;
        this.mDownloadsAuthorityAppId = -1;
        this.mExternalStorageAuthorityAppId = -1;
        this.mCurrentUserId = 0;
        this.mRemountCurrentUserVolumesOnUnlock = false;
        this.mAppFuseLock = new Object();
        this.mNextAppFuseName = 0;
        this.mAppFuseBridge = null;
        this.mIsMediaSharedWithParent = new HashMap();
        this.mAsecMountSet = new HashSet();
        this.mUnmountLock = new Object();
        this.mAsecsScanned = new CountDownLatch(1);
        this.preSDPolicy = true;
        this.mUserSharesMediaWith = new SparseIntArray();
        this.mBootCompleted = false;
        this.mDaemonConnected = false;
        this.mSecureKeyguardShowing = true;
        this.mObbMounts = new HashMap();
        this.mObbPathToStateMap = new HashMap();
        this.mStorageManagerInternal = new StorageManagerInternalImpl();
        this.mUidsWithLegacyExternalStorage = new ArraySet();
        this.mPackageMonitorsForUser = new SparseArray();
        this.mCeStorageEventCallbacks = new CopyOnWriteArrayList();
        final int i = 0;
        this.mUserReceiver = new BroadcastReceiver(this) { // from class: com.android.server.StorageManagerService.1
            public final /* synthetic */ StorageManagerService this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Removed duplicated region for block: B:45:0x00e1 A[Catch: IllegalArgumentException -> 0x00d6, TryCatch #3 {IllegalArgumentException -> 0x00d6, blocks: (B:29:0x00bb, B:32:0x00c1, B:38:0x00d1, B:42:0x00d9, B:45:0x00e1, B:48:0x00e6), top: B:28:0x00bb, inners: #4 }] */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r7, android.content.Intent r8) {
                /*
                    Method dump skipped, instructions count: 404
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.StorageManagerService.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        final int i2 = 2;
        this.mPassReceiver = new BroadcastReceiver(this) { // from class: com.android.server.StorageManagerService.1
            public final /* synthetic */ StorageManagerService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                /*
                    Method dump skipped, instructions count: 404
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.StorageManagerService.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        this.mListener = new IVoldListener.Stub() { // from class: com.android.server.StorageManagerService.4
            /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
            /* JADX WARN: Removed duplicated region for block: B:19:0x003d A[Catch: all -> 0x0027, TryCatch #0 {all -> 0x0027, blocks: (B:4:0x0005, B:13:0x003f, B:14:0x004b, B:18:0x003a, B:19:0x003d, B:20:0x001c, B:23:0x0029), top: B:3:0x0005 }] */
            @Override // android.os.IVoldListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onDiskCreated(java.lang.String r6, int r7) {
                /*
                    r5 = this;
                    com.android.server.StorageManagerService r0 = com.android.server.StorageManagerService.this
                    java.lang.Object r0 = r0.mLock
                    monitor-enter(r0)
                    java.lang.String r1 = "persist.sys.adoptable"
                    java.lang.String r1 = android.os.SystemProperties.get(r1)     // Catch: java.lang.Throwable -> L27
                    int r2 = r1.hashCode()     // Catch: java.lang.Throwable -> L27
                    r3 = 464944051(0x1bb67bb3, float:3.0189313E-22)
                    r4 = 1
                    if (r2 == r3) goto L29
                    r3 = 1528363547(0x5b18fa1b, float:4.305919E16)
                    if (r2 == r3) goto L1c
                    goto L34
                L1c:
                    java.lang.String r2 = "force_off"
                    boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L27
                    if (r1 == 0) goto L34
                    r1 = r4
                    goto L35
                L27:
                    r5 = move-exception
                    goto L4d
                L29:
                    java.lang.String r2 = "force_on"
                    boolean r1 = r1.equals(r2)     // Catch: java.lang.Throwable -> L27
                    if (r1 == 0) goto L34
                    r1 = 0
                    goto L35
                L34:
                    r1 = -1
                L35:
                    if (r1 == 0) goto L3d
                    if (r1 == r4) goto L3a
                    goto L3f
                L3a:
                    r7 = r7 & (-2)
                    goto L3f
                L3d:
                    r7 = r7 | 1
                L3f:
                    com.android.server.StorageManagerService r5 = com.android.server.StorageManagerService.this     // Catch: java.lang.Throwable -> L27
                    android.util.ArrayMap r5 = r5.mDisks     // Catch: java.lang.Throwable -> L27
                    android.os.storage.DiskInfo r1 = new android.os.storage.DiskInfo     // Catch: java.lang.Throwable -> L27
                    r1.<init>(r6, r7)     // Catch: java.lang.Throwable -> L27
                    r5.put(r6, r1)     // Catch: java.lang.Throwable -> L27
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L27
                    return
                L4d:
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L27
                    throw r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.StorageManagerService.AnonymousClass4.onDiskCreated(java.lang.String, int):void");
            }

            @Override // android.os.IVoldListener
            public final void onDiskDestroyed(String str) {
                synchronized (StorageManagerService.this.mLock) {
                    try {
                        DiskInfo diskInfo = (DiskInfo) StorageManagerService.this.mDisks.remove(str);
                        if (diskInfo != null) {
                            Callbacks callbacks = StorageManagerService.this.mCallbacks;
                            int i3 = Callbacks.$r8$clinit;
                            callbacks.getClass();
                            SomeArgs obtain = SomeArgs.obtain();
                            obtain.arg1 = diskInfo.clone();
                            callbacks.obtainMessage(6, obtain).sendToTarget();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void onDiskMetadataChanged(String str, long j, String str2, String str3) {
                synchronized (StorageManagerService.this.mLock) {
                    try {
                        DiskInfo diskInfo = (DiskInfo) StorageManagerService.this.mDisks.get(str);
                        if (diskInfo != null) {
                            diskInfo.size = j;
                            diskInfo.label = str2;
                            diskInfo.sysPath = str3;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void onDiskScanned(String str) {
                synchronized (StorageManagerService.this.mLock) {
                    try {
                        DiskInfo diskInfo = (DiskInfo) StorageManagerService.this.mDisks.get(str);
                        if (diskInfo != null) {
                            StorageManagerService.m90$$Nest$monDiskScannedLocked(StorageManagerService.this, diskInfo);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void onEncryptionStateChanged(String str, String str2, String str3) {
                synchronized (StorageManagerService.this.mLock) {
                    Slog.d("StorageManagerService", "onEncryptionStateChanged state = " + str2 + ", type : " + str3);
                    if (((VolumeInfo) StorageManagerService.this.mVolumes.get(str)) != null) {
                        if ("encryptable".equals(str2)) {
                            SemSdCardEncryption semSdCardEncryption = new SemSdCardEncryption(StorageManagerService.this.mContext);
                            PersistableBundle persistableBundle = new PersistableBundle();
                            persistableBundle.putInt(Constants.JSON_CLIENT_DATA_STATUS, 0);
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

            @Override // android.os.IVoldListener
            public final void onVolumeCreated(String str, int i3, String str2, String str3, int i4) {
                synchronized (StorageManagerService.this.mLock) {
                    VolumeInfo volumeInfo = new VolumeInfo(str, i3, (DiskInfo) StorageManagerService.this.mDisks.get(str2), str3);
                    volumeInfo.mountUserId = i4;
                    StorageManagerService.this.mVolumes.put(str, volumeInfo);
                    StorageManagerService.m91$$Nest$monVolumeCreatedLocked(StorageManagerService.this, volumeInfo);
                }
            }

            @Override // android.os.IVoldListener
            public final void onVolumeDestroyed(String str) {
                VolumeInfo volumeInfo;
                synchronized (StorageManagerService.this.mLock) {
                    volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.remove(str);
                }
                if (volumeInfo != null) {
                    StorageManagerService.this.mStorageSessionController.onVolumeRemove(volumeInfo);
                    try {
                        if (volumeInfo.type == 1) {
                            Installer installer = StorageManagerService.this.mInstaller;
                            String fsUuid = volumeInfo.getFsUuid();
                            if (installer.checkBeforeRemote()) {
                                try {
                                    installer.mInstalld.onPrivateVolumeRemoved(fsUuid);
                                } catch (Exception e) {
                                    Installer.InstallerException.from(e);
                                    throw null;
                                }
                            }
                        }
                    } catch (Installer.InstallerException e2) {
                        Slog.i("StorageManagerService", "Failed when private volume unmounted " + volumeInfo, e2);
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void onVolumeInternalPathChanged(String str, String str2) {
                synchronized (StorageManagerService.this.mLock) {
                    try {
                        VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                        if (volumeInfo != null) {
                            volumeInfo.internalPath = str2;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void onVolumeMetadataChanged(String str, String str2, String str3, String str4) {
                synchronized (StorageManagerService.this.mLock) {
                    try {
                        VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                        if (volumeInfo != null) {
                            volumeInfo.fsType = str2;
                            volumeInfo.fsUuid = str3;
                            volumeInfo.fsLabel = str4;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void onVolumePathChanged(String str, String str2) {
                synchronized (StorageManagerService.this.mLock) {
                    try {
                        VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                        if (volumeInfo != null) {
                            volumeInfo.path = str2;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void onVolumeStateChanged(String str, int i3, int i4) {
                synchronized (StorageManagerService.this.mLock) {
                    try {
                        VolumeInfo volumeInfo = (VolumeInfo) StorageManagerService.this.mVolumes.get(str);
                        if (volumeInfo != null) {
                            int i5 = volumeInfo.state;
                            volumeInfo.state = i3;
                            final VolumeInfo volumeInfo2 = new VolumeInfo(volumeInfo);
                            volumeInfo2.mountUserId = i4;
                            SomeArgs obtain = SomeArgs.obtain();
                            obtain.arg1 = volumeInfo2;
                            obtain.argi1 = i5;
                            obtain.argi2 = i3;
                            StorageManagerService.this.mHandler.obtainMessage(15, obtain).sendToTarget();
                            final StorageManagerService storageManagerService = StorageManagerService.this;
                            storageManagerService.getClass();
                            if (volumeInfo2.type == 2) {
                                if (i3 != 2) {
                                    ((ArraySet) storageManagerService.mFuseMountedUser).remove(Integer.valueOf(volumeInfo2.getMountUserId()));
                                } else if (storageManagerService.mVoldAppDataIsolationEnabled) {
                                    final int mountUserId = volumeInfo2.getMountUserId();
                                    new Thread(new Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda4
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            StorageManagerService storageManagerService2 = StorageManagerService.this;
                                            int i6 = mountUserId;
                                            VolumeInfo volumeInfo3 = volumeInfo2;
                                            Map map = null;
                                            if (i6 == 0) {
                                                storageManagerService2.getClass();
                                                if (Build.VERSION.DEVICE_INITIAL_SDK_INT < 29) {
                                                    PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl = (PackageManagerService.PackageManagerInternalImpl) storageManagerService2.mPmInternal;
                                                    packageManagerInternalImpl.getClass();
                                                    try {
                                                        Installer installer = PackageManagerService.this.mInstaller;
                                                        if (installer.checkBeforeRemote()) {
                                                            try {
                                                                installer.mInstalld.migrateLegacyObbData();
                                                            } catch (Exception e) {
                                                                Installer.InstallerException.from(e);
                                                                throw null;
                                                            }
                                                        }
                                                    } catch (Exception e2) {
                                                        android.util.Slog.wtf("PackageManager", e2);
                                                    }
                                                }
                                            }
                                            synchronized (storageManagerService2.mLock) {
                                                ((ArraySet) storageManagerService2.mFuseMountedUser).add(Integer.valueOf(i6));
                                            }
                                            for (int i7 = 0; i7 < 5; i7++) {
                                                try {
                                                    map = ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getProcessesWithPendingBindMounts(volumeInfo3.getMountUserId());
                                                    break;
                                                } catch (IllegalStateException unused) {
                                                    Slog.i("StorageManagerService", "Some processes are starting, retry");
                                                    SystemClock.sleep(100L);
                                                }
                                            }
                                            if (map == null) {
                                                Slog.wtf("StorageManagerService", "Not able to getStorageNotOptimizedProcesses() after 5 retries");
                                                return;
                                            }
                                            for (Map.Entry entry : map.entrySet()) {
                                                int intValue = ((Integer) entry.getKey()).intValue();
                                                String str2 = (String) entry.getValue();
                                                Slog.i("StorageManagerService", "Remounting storage for pid: " + intValue);
                                                String[] sharedUserPackagesForPackage = storageManagerService2.mPmInternal.getSharedUserPackagesForPackage(i6, str2);
                                                int packageUid = storageManagerService2.mPmInternal.getPackageUid(str2, 0L, i6);
                                                if (sharedUserPackagesForPackage.length == 0) {
                                                    sharedUserPackagesForPackage = new String[]{str2};
                                                }
                                                try {
                                                    storageManagerService2.mVold.remountAppStorageDirs(packageUid, intValue, sharedUserPackagesForPackage);
                                                } catch (RemoteException e3) {
                                                    throw e3.rethrowAsRuntimeException();
                                                }
                                            }
                                        }
                                    }).start();
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.IVoldListener
            public final void sendVoldMessage(String str) {
                Intent intent = new Intent("com.samsung.intent.action.EXTERNAL_STORAGE_WARNING_SEC");
                if (str == null) {
                    Slog.d("StorageManagerService", "Intent[" + intent + "] cannot be sent");
                    return;
                }
                intent.putExtra("message", str);
                if ("BAD_REMOVAL_SDCARD".equals(str)) {
                    StorageManagerService.this.getClass();
                    try {
                        File file = new File("/sys/class/sec/sdcard/status");
                        if (!file.exists()) {
                            Slog.d("StorageManagerService", "Ext SD Card Tray State File Not Exist");
                        } else if (!FileUtils.readTextFile(file, 0, null).trim().equals("Notray")) {
                            Slog.d("StorageManagerService", "Ext SD Card Tray State is not proper");
                        }
                    } catch (Exception e) {
                        Slog.e("StorageManagerService", "Could't read Ext SD Card Tray State File : ", e);
                    }
                    StorageManagerService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                    Slog.d("StorageManagerService", "[" + str + "] Bad Removal broadcasting " + intent + " extras: " + intent.getExtras());
                }
                if (!"BAD_REMOVAL_USB".equals(str) && !"REBOOT".equals(str)) {
                    Slog.d("StorageManagerService", "Don't send the Intent[" + intent + "] extras: " + intent.getExtras());
                    return;
                }
                StorageManagerService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                Slog.d("StorageManagerService", "[" + str + "] Bad Removal broadcasting " + intent + " extras: " + intent.getExtras());
            }
        };
        final int i3 = 1;
        this.mPolicyReceiver = new BroadcastReceiver(this) { // from class: com.android.server.StorageManagerService.1
            public final /* synthetic */ StorageManagerService this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                /*
                    Method dump skipped, instructions count: 404
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.StorageManagerService.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        sSelf = this;
        this.mVoldAppDataIsolationEnabled = SystemProperties.getBoolean("persist.sys.vold_app_data_isolation_enabled", false);
        this.mContext = context;
        this.mCallbacks = new Callbacks(FgThread.get().getLooper());
        this.mHandler = new Callbacks(this, KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("StorageManagerService").getLooper(), 2);
        this.mObbActionHandler = new Callbacks(this, IoThread.get().getLooper(), 1);
        this.mStorageSessionController = new StorageSessionController(context);
        Installer installer = new Installer(context);
        this.mInstaller = installer;
        installer.onStart();
        File file = new File(new File(Environment.getDataDirectory(), "system"), "last-fstrim");
        this.mLastMaintenanceFile = file;
        if (file.exists()) {
            this.mLastMaintenance = file.lastModified();
        } else {
            try {
                new FileOutputStream(file).close();
            } catch (IOException unused) {
                Slog.e("StorageManagerService", "Unable to create fstrim record " + this.mLastMaintenanceFile.getPath());
            }
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
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiver(this.mPassReceiver, intentFilter2, null, this.mHandler);
        synchronized (this.mLock) {
            VolumeInfo volumeInfo = new VolumeInfo("private", 1, (DiskInfo) null, (String) null);
            volumeInfo.state = 2;
            volumeInfo.path = Environment.getDataDirectory().getAbsolutePath();
            this.mVolumes.put(volumeInfo.id, volumeInfo);
        }
        Watchdog.getInstance().addMonitor(this);
        this.mContext.registerReceiver(this.mPolicyReceiver, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), null, null);
    }

    public static boolean chkMountUnmountFormatCaller(String str) {
        if ("com.sec.android.app.myfiles".equalsIgnoreCase(str)) {
            return true;
        }
        Slog.d("StorageManagerService", "Caller is not MyFiles");
        return false;
    }

    public static String createVolumeInfoStrForPulbicVolume(VolumeInfo volumeInfo) {
        String valueToString = DebugUtils.valueToString(VolumeInfo.class, "TYPE_", volumeInfo.type);
        String diskId = volumeInfo.getDiskId() != null ? volumeInfo.getDiskId() : "null";
        String str = volumeInfo.partGuid;
        if (str == null) {
            str = "";
        }
        String flagsToString = DebugUtils.flagsToString(VolumeInfo.class, "MOUNT_FLAG_", volumeInfo.mountFlags);
        String valueToString2 = DebugUtils.valueToString(VolumeInfo.class, "STATE_", volumeInfo.state);
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1("VolumeInfo".concat("{" + volumeInfo.id + "}:"), "\n"));
        StringBuilder m2 = InitialConfiguration$$ExternalSyntheticOutline0.m("type=", valueToString, " diskId=", diskId, " partGuid=");
        m2.append(str);
        m.append(m2.toString());
        StringBuilder m3 = BootReceiver$$ExternalSyntheticOutline0.m(m.toString());
        m3.append(String.format(" mountFlags=%s mountUserId=%d state=%s", flagsToString, Integer.valueOf(volumeInfo.getMountUserId()), valueToString2));
        StringBuilder m4 = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m3.toString(), "\n"));
        String str2 = volumeInfo.fsType;
        String str3 = volumeInfo.fsUuid;
        String str4 = volumeInfo.path;
        String str5 = volumeInfo.internalPath;
        StringBuilder m5 = InitialConfiguration$$ExternalSyntheticOutline0.m(" fsType=", str2, " fsUuid=", str3, " path=");
        m5.append(str4);
        m5.append(" internalPath=");
        m5.append(str5);
        m4.append(m5.toString());
        return m4.toString();
    }

    public static void extendWatchdogTimeout(String str) {
        Watchdog watchdog = Watchdog.getInstance();
        watchdog.mMonitorChecker.pauseForLocked(str);
        watchdog.pauseWatchingCurrentThreadFor(str);
    }

    public static boolean isMediaPath(String str) {
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

    public static boolean isRootedDevice() {
        String str = SystemProperties.get("ro.boot.flash.locked");
        Slog.d("StorageManagerService", "phone status : " + str);
        if ("0".equalsIgnoreCase(str)) {
            return true;
        }
        String[] strArr = {"/sbin/su", "/system/su", "/system/bin/su", "/system/xbin/su", "/system/bin/.ext/.su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su", "/system/app/Superuser.apk", "/system/usr/we-need-root/su-backup", "/system/xbin/mu"};
        String[] strArr2 = {"/data", "/system", "/system/bin", "/system/sbin", "/system/xbin", "/vendor/bin", "/sys", "/sbin", "/etc", "/proc", "/dev"};
        for (int i = 0; i < 13; i++) {
            String str2 = strArr[i];
            if (TextUtils.isEmpty(str2)) {
                break;
            }
            if (BatteryService$$ExternalSyntheticOutline0.m45m(str2)) {
                Slog.d("StorageManagerService", "rooting:su located at : " + str2);
                return true;
            }
        }
        for (int i2 = 0; i2 < 11; i2++) {
            String str3 = strArr2[i2];
            if (TextUtils.isEmpty(str3)) {
                break;
            }
            if (new File(str3).canWrite()) {
                Slog.d("StorageManagerService", "rooting:read only changed as writable : " + str3);
            }
        }
        return false;
    }

    public static boolean isValidPath(String str) {
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

    public static void waitForLatch(CountDownLatch countDownLatch, long j, String str) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        while (!countDownLatch.await(5000L, TimeUnit.MILLISECONDS)) {
            try {
                Slog.w("StorageManagerService", "Thread " + Thread.currentThread().getName() + " still waiting for " + str + "...");
            } catch (InterruptedException unused) {
                Slog.w("StorageManagerService", "Interrupt while waiting for ".concat(str));
            }
            if (j > 0 && SystemClock.elapsedRealtime() > elapsedRealtime + j) {
                StringBuilder sb = new StringBuilder("Thread ");
                sb.append(Thread.currentThread().getName());
                sb.append(" gave up waiting for ");
                sb.append(str);
                sb.append(" after ");
                throw new TimeoutException(AudioConfig$$ExternalSyntheticOutline0.m(sb, j, "ms"));
            }
        }
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

    public final void abortChanges(String str, boolean z) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to commit checkpoint changes");
        }
        this.mVold.abortChanges(str, z);
    }

    public final void abortIdleMaint(Runnable runnable) {
        enforcePermission$1("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            this.mVold.abortIdleMaint(new AnonymousClass11(this, runnable, 1));
            Slog.d("StorageManagerService", "abortIdleMaint, HeimdAllFSThread = null");
            HeimdAllFsService heimdAllFsService = this.mHeimdAllFs;
            if (heimdAllFsService != null) {
                android.util.Slog.w("HeimdAllFS", "Abort()");
                heimdAllFsService.mHeimdallFsThread = null;
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final void abortIdleMaintenance() {
        abortIdleMaint(null);
    }

    public final int adjustAllocateFlags(int i, int i2, String str) {
        if ((i & 1) != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.ALLOCATE_AGGRESSIVE", "StorageManagerService");
        }
        int i3 = i & (-7);
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

    public final void allocateBytes(String str, long j, int i, String str2) {
        int adjustAllocateFlags = adjustAllocateFlags(i, Binder.getCallingUid(), str2);
        long allocatableBytes = getAllocatableBytes(str, adjustAllocateFlags | 8, str2);
        if (j > allocatableBytes) {
            long allocatableBytes2 = getAllocatableBytes(str, adjustAllocateFlags | 16, str2) + allocatableBytes;
            if (j > allocatableBytes2) {
                throw new ParcelableException(new IOException(AudioConfig$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m("Failed to allocate ", j, " because only "), allocatableBytes2, " allocatable")));
            }
        }
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(StorageManager.class);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                File findPathForUuid = storageManager.findPathForUuid(str);
                ((PackageManagerService.PackageManagerInternalImpl) this.mPmInternal).mService.freeStorage(adjustAllocateFlags, str, j + ((adjustAllocateFlags & 1) != 0 ? storageManager.getStorageFullBytes(findPathForUuid) : storageManager.getStorageLowBytes(findPathForUuid)));
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (IOException e) {
                throw new ParcelableException(e);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void benchmark(String str, IVoldTaskListener iVoldTaskListener) {
        benchmark_enforcePermission();
        try {
            this.mVold.benchmark(str, new AnonymousClass9(this, iVoldTaskListener, 0));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public final boolean checkChargeStatus() {
        Intent registerReceiver = this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        float intExtra = (registerReceiver.getIntExtra("level", -1) * 100.0f) / registerReceiver.getIntExtra("scale", -1);
        if (intExtra >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return true;
        }
        Slog.w("StorageManagerService", "Battery level is " + intExtra + ", which is lower than threshold: 0.0");
        return false;
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

    public final void cleanupSercureContainerList() {
        synchronized (this.mAsecMountSet) {
            try {
                Iterator it = this.mAsecMountSet.iterator();
                while (it.hasNext()) {
                    if (((PackageInstalledMap) it.next()).external) {
                        it.remove();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void commitChanges() {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("no permission to commit checkpoint changes");
        }
        this.mVold.commitChanges();
    }

    public final void connectStoraged() {
        IBinder service = ServiceManager.getService("storaged");
        if (service != null) {
            try {
                service.linkToDeath(new AnonymousClass5(this, 0), 0);
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
            BackgroundThread.getHandler().postDelayed(new StorageManagerService$$ExternalSyntheticLambda1(0, this), 1000L);
        } else {
            this.mDaemonConnected = true;
            this.mHandler.obtainMessage(2).sendToTarget();
        }
    }

    public final void connectVold() {
        IBinder service = ServiceManager.getService("vold");
        if (service != null) {
            try {
                service.linkToDeath(new AnonymousClass5(this, 1), 0);
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
            BackgroundThread.getHandler().postDelayed(new StorageManagerService$$ExternalSyntheticLambda1(1, this), 1000L);
            return;
        }
        try {
            int[] unlockedUsers = this.mVold.getUnlockedUsers();
            if (!ArrayUtils.isEmpty(unlockedUsers)) {
                Slog.d("StorageManagerService", "CE storage for users " + Arrays.toString(unlockedUsers) + " is already unlocked");
                synchronized (this.mLock) {
                    WatchedUnlockedUsers watchedUnlockedUsers = this.mCeUnlockedUsers;
                    watchedUnlockedUsers.getClass();
                    for (int i : unlockedUsers) {
                        watchedUnlockedUsers.users = ArrayUtils.appendInt(watchedUnlockedUsers.users, i);
                    }
                    UserManager.invalidateIsUserUnlockedCache();
                }
            }
        } catch (Exception e2) {
            Slog.e("StorageManagerService", "Failed to get unlocked users from vold", e2);
        }
        this.mDaemonConnected = true;
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    public final boolean cpFileAtData(String str, String str2) {
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
            CompletableFuture completableFuture = new CompletableFuture();
            this.mVold.cpFileAtData(str, str2, this.mMediaStoreAuthorityAppId, Process.myUid(), new AnonymousClass15(1, completableFuture));
            boolean z = ((PersistableBundle) completableFuture.get()).getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT, false);
            Slog.d("StorageManagerService", "File copy in media path result ".concat(z ? "true" : "false"));
            return z;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public final int createPassStorage() {
        int callingUid = Binder.getCallingUid();
        String packageName = getPackageName(callingUid);
        if (packageName.equals("Unknown")) {
            Slog.d("StorageManagerService", "Package name of Client not allowed ".concat(packageName));
            throw new SecurityException(" calling package is not allowed");
        }
        if (isRootedDevice()) {
            Slog.d("StorageManagerService", "isDetectedRoot: true ");
            throw new SecurityException(" Root detected");
        }
        if (!isPlatformSignedApp(packageName)) {
            throw new SecurityException(" Signature of the calling package is not match");
        }
        try {
            return this.mVold.createPassStorage(packageName, this.mCurrentUserId, callingUid);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -12;
        }
    }

    public final int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) {
        int i3;
        enforcePermission$1("android.permission.ASEC_CREATE");
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
                try {
                    if (z) {
                        this.mAsecMountSet.add(new PackageInstalledMap(str, true));
                    } else {
                        this.mAsecMountSet.add(new PackageInstalledMap(str, false));
                    }
                } finally {
                }
            }
        }
        return i3;
    }

    public final void createUserStorageKeys(int i, boolean z) {
        createUserStorageKeys_enforcePermission();
        try {
            this.mVold.createUserStorageKeys(i, z);
            synchronized (this.mLock) {
                WatchedUnlockedUsers watchedUnlockedUsers = this.mCeUnlockedUsers;
                watchedUnlockedUsers.users = ArrayUtils.appendInt(watchedUnlockedUsers.users, i);
                UserManager.invalidateIsUserUnlockedCache();
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final int destroyPassStorage() {
        int callingUid = Binder.getCallingUid();
        String packageName = getPackageName(callingUid);
        if (packageName.equals("Unknown")) {
            throw new SecurityException(" calling package is not allowed");
        }
        if (!isPlatformSignedApp(packageName)) {
            throw new SecurityException(" Signature of the calling package is not match");
        }
        try {
            return this.mVold.destroyPassStorage(packageName, this.mCurrentUserId, callingUid);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -12;
        }
    }

    public final int destroySecureContainer(String str, boolean z) {
        int i;
        enforcePermission$1("android.permission.ASEC_DESTROY");
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
                try {
                    if (this.mAsecMountSet.contains(new PackageInstalledMap(str))) {
                        this.mAsecMountSet.remove(new PackageInstalledMap(str));
                    }
                } finally {
                }
            }
        }
        return i;
    }

    public final void destroyUserStorage(String str, int i, int i2) {
        destroyUserStorage_enforcePermission();
        try {
            this.mVold.destroyUserStorage(str, i, i2);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final void destroyUserStorageKeys(int i) {
        destroyUserStorageKeys_enforcePermission();
        try {
            this.mVold.destroyUserStorageKeys(i);
            synchronized (this.mLock) {
                WatchedUnlockedUsers watchedUnlockedUsers = this.mCeUnlockedUsers;
                watchedUnlockedUsers.users = ArrayUtils.removeInt(watchedUnlockedUsers.users, i);
                UserManager.invalidateIsUserUnlockedCache();
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final void disableAppDataIsolation(String str, int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            throw new SecurityException("no permission to enable app visibility");
        }
        String[] sharedUserPackagesForPackage = this.mPmInternal.getSharedUserPackagesForPackage(i2, str);
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

    public void dispatchCeStorageLockedEvent(int i) {
        Iterator it = this.mCeStorageEventCallbacks.iterator();
        while (it.hasNext()) {
            ((ICeStorageLockEventListener) it.next()).onStorageLocked(i);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "StorageManagerService", printWriter)) {
            final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ", 160);
            ArrayList arrayList = new ArrayList();
            try {
                Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), "android.SecVold.StorageManagerService", FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_FORCED_BY_SYSTEM));
            } catch (Exception e) {
                Slog.e("StorageManagerService", "Failed to get StorageVolume List ", e);
                arrayList = null;
            }
            if (arrayList != null) {
                indentingPrintWriter.println();
                indentingPrintWriter.println("StorageVolumes:");
                indentingPrintWriter.println("Size:" + arrayList.size());
                indentingPrintWriter.increaseIndent();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((StorageVolume) it.next()).dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.println();
            synchronized (this.mLock) {
                try {
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
                        indentingPrintWriter.print(((Long) primaryStoragePathAndSize.second).longValue() / DataUnit.MEBIBYTES.toBytes(1L));
                        indentingPrintWriter.println(" MiB)");
                    }
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("CE unlocked users: " + this.mCeUnlockedUsers);
                    indentingPrintWriter.println("System unlocked users: " + Arrays.toString(this.mSystemUnlockedUsers));
                    indentingPrintWriter.print("Sharing media with parent : ");
                    this.mIsMediaSharedWithParent.forEach(new BiConsumer() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda2
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            indentingPrintWriter.print(" [" + ((Integer) obj) + ", " + ((Boolean) obj2) + "] ");
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
                } finally {
                }
            }
            synchronized (this.mObbMounts) {
                try {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("mObbMounts:");
                    indentingPrintWriter.increaseIndent();
                    for (Map.Entry entry : ((HashMap) this.mObbMounts).entrySet()) {
                        indentingPrintWriter.println(entry.getKey() + ":");
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
                    for (Map.Entry entry2 : ((HashMap) this.mObbPathToStateMap).entrySet()) {
                        indentingPrintWriter.print((String) entry2.getKey());
                        indentingPrintWriter.print(" -> ");
                        indentingPrintWriter.println(entry2.getValue());
                    }
                    indentingPrintWriter.decreaseIndent();
                } finally {
                }
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

    public final int encryptExternalStorage(boolean z) {
        VolumeInfo volumeInfo;
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
        }
        Slog.i("StorageManagerService", "state : " + volumeInfo.getState());
        return volumeInfo.getState() == 0 ? 202 : 0;
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

    public final void enforceExternalStorageService() {
        enforcePermission$1("android.permission.WRITE_MEDIA_STORAGE");
        if (UserHandle.getAppId(Binder.getCallingUid()) != this.mMediaStoreAuthorityAppId) {
            throw new SecurityException("Only the ExternalStorageService is permitted");
        }
    }

    public final void enforcePermission$1(String str) {
        this.mContext.enforceCallingOrSelfPermission(str, str);
    }

    public final int finalizeSecureContainer(String str) {
        enforcePermission$1("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            this.mVold.asecFinalize(str);
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    public final CountDownLatch findOrCreateDiskScanLatch(String str) {
        CountDownLatch countDownLatch;
        synchronized (this.mLock) {
            try {
                countDownLatch = (CountDownLatch) this.mDiskScanLatches.get(str);
                if (countDownLatch == null) {
                    countDownLatch = new CountDownLatch(1);
                    this.mDiskScanLatches.put(str, countDownLatch);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return countDownLatch;
    }

    public final VolumeRecord findRecordForPath(String str) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                try {
                    VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                    String str2 = volumeInfo.path;
                    if (str2 != null && str.startsWith(str2)) {
                        return (VolumeRecord) this.mRecords.get(volumeInfo.fsUuid);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return null;
        }
    }

    public final VolumeInfo findStorageForUuidAsUser(int i, String str) {
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
        return storageManager.findVolumeById(findVolumeByUuid.getId().replace("private", "emulated") + ";" + i);
    }

    public final VolumeInfo findVolumeByIdOrThrow(String str) {
        synchronized (this.mLock) {
            try {
                VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.get(str);
                if (volumeInfo != null) {
                    return volumeInfo;
                }
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("No volume found for ID ", str));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String findVolumeIdForPathOrThrow(String str) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                try {
                    VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                    String str2 = volumeInfo.path;
                    if (str2 != null && str.startsWith(str2)) {
                        return volumeInfo.id;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("No volume found for path ", str));
        }
    }

    public final void finishMediaUpdate() {
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

    public final int fixPermissionsSecureContainer(String str, int i, String str2) {
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "fixPermissionsSecureContainer: id=", str, " gid=", " filename=");
        m.append(str2);
        Slog.d("StorageManagerService", m.toString());
        enforcePermission$1("android.permission.ASEC_CREATE");
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

    public final void fixupAppDir(String str) {
        Matcher matcher = KNOWN_APP_DIR_PATHS.matcher(str);
        if (!matcher.matches()) {
            Log.e("StorageManagerService", "Path " + str + " is not a valid application-specific directory");
            return;
        }
        if (matcher.group(2) == null) {
            StorageManagerService$$ExternalSyntheticOutline0.m("Asked to fixup an app dir without a userId: ", str, "StorageManagerService");
            return;
        }
        try {
            int parseInt = Integer.parseInt(matcher.group(2));
            String group = matcher.group(3);
            int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(group, parseInt);
            try {
                this.mVold.fixupAppDir(str + "/", packageUidAsUser);
            } catch (RemoteException | ServiceSpecificException e) {
                Log.e("StorageManagerService", "Failed to fixup app dir for " + group, e);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("StorageManagerService", "Couldn't find package to fixup app dir " + str, e2);
        } catch (NumberFormatException e3) {
            Log.e("StorageManagerService", "Invalid userId in path: " + str, e3);
        }
    }

    public final void forgetAllVolumes() {
        forgetAllVolumes_enforcePermission();
        Slog.d("StorageManagerService", "forgetAllVolumes");
        synchronized (this.mLock) {
            for (int i = 0; i < this.mRecords.size(); i++) {
                try {
                    String str = (String) this.mRecords.keyAt(i);
                    VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.valueAt(i);
                    if (!TextUtils.isEmpty(volumeRecord.partGuid)) {
                        this.mHandler.obtainMessage(9, volumeRecord).sendToTarget();
                    }
                    Callbacks callbacks = this.mCallbacks;
                    int i2 = Callbacks.$r8$clinit;
                    callbacks.getClass();
                    SomeArgs obtain = SomeArgs.obtain();
                    obtain.arg1 = str;
                    callbacks.obtainMessage(4, obtain).sendToTarget();
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mRecords.clear();
            String str2 = StorageManager.UUID_PRIVATE_INTERNAL;
            if (!Objects.equals(str2, this.mPrimaryStorageUuid)) {
                if (SystemProperties.getBoolean("ro.vold.primary_physical", false)) {
                    str2 = "primary_physical";
                }
                this.mPrimaryStorageUuid = str2;
            }
            writeSettingsLocked();
            this.mHandler.obtainMessage(10).sendToTarget();
        }
    }

    public final void forgetVolume(String str) {
        forgetVolume_enforcePermission();
        Slog.d("StorageManagerService", "forgetVolume: fsUuid=" + str);
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            try {
                VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.remove(str);
                if (volumeRecord != null && !TextUtils.isEmpty(volumeRecord.partGuid)) {
                    this.mHandler.obtainMessage(9, volumeRecord).sendToTarget();
                }
                Callbacks callbacks = this.mCallbacks;
                int i = Callbacks.$r8$clinit;
                callbacks.getClass();
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = str;
                callbacks.obtainMessage(4, obtain).sendToTarget();
                if (Objects.equals(this.mPrimaryStorageUuid, str)) {
                    this.mPrimaryStorageUuid = SystemProperties.getBoolean("ro.vold.primary_physical", false) ? "primary_physical" : StorageManager.UUID_PRIVATE_INTERNAL;
                    this.mHandler.obtainMessage(10).sendToTarget();
                }
                writeSettingsLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void format(String str) {
        format_enforcePermission();
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

    public final void formatBySecApp(String str, String str2) {
        if (!chkMountUnmountFormatCaller(str2)) {
            throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Format call denied to [", str2, "]"));
        }
        Slog.d("StorageManagerService", "Format call accepted");
        String str3 = null;
        for (VolumeInfo volumeInfo : ((StorageManager) this.mContext.getSystemService(StorageManager.class)).getVolumes()) {
            if (Objects.equals(volumeInfo.id, str)) {
                str3 = volumeInfo.getDiskId();
            }
        }
        if (str3 == null) {
            Slog.d("StorageManagerService", "No matched storage to format by SecApp, so just return");
        } else {
            partitionPublic(str3);
        }
    }

    public final void fstrim(int i, IVoldTaskListener iVoldTaskListener) {
        fstrim_enforcePermission();
        try {
            if (needsCheckpoint()) {
                enforcePermission$1("android.permission.MOUNT_FORMAT_FILESYSTEMS");
                if (this.mVold.supportsBlockCheckpoint()) {
                    Slog.i("StorageManagerService", "Skipping fstrim - block based checkpoint in progress");
                    return;
                }
            }
            this.mVold.fstrim(i, new AnonymousClass9(this, iVoldTaskListener, 1));
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public final long getAllocatableBytes(String str, int i, String str2) {
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
                    long max2 = Math.max(0L, (j + max) - j3);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return max2;
                }
                long max3 = Math.max(0L, (j + max) - j2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return max3;
            } catch (IOException e) {
                throw new ParcelableException(e);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final long getCacheQuotaBytes(String str, int i) {
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

    public final long getCacheSizeBytes(String str, int i) {
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

    public CopyOnWriteArrayList getCeStorageEventCallbacks() {
        return this.mCeStorageEventCallbacks;
    }

    public final String getCloudMediaProvider() {
        String str;
        ProviderInfo resolveContentProvider;
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        synchronized (this.mCloudMediaProviders) {
            str = (String) this.mCloudMediaProviders.get(userId);
        }
        if (str == null || (resolveContentProvider = ((PackageManagerService.PackageManagerInternalImpl) this.mPmInternal).mService.snapshotComputer().resolveContentProvider(userId, callingUid, 0L, str)) == null || this.mPmInternal.filterAppAccess(callingUid, userId, resolveContentProvider.packageName, true)) {
            return null;
        }
        return str;
    }

    public final DiskInfo[] getDisks() {
        DiskInfo[] diskInfoArr;
        synchronized (this.mLock) {
            try {
                diskInfoArr = new DiskInfo[this.mDisks.size()];
                for (int i = 0; i < this.mDisks.size(); i++) {
                    diskInfoArr[i] = (DiskInfo) this.mDisks.valueAt(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return diskInfoArr;
    }

    public final int getExternalStorageMountMode(int i, String str) {
        getExternalStorageMountMode_enforcePermission();
        return this.mStorageManagerInternal.getExternalStorageMountMode(i, str);
    }

    public final long getInternalStorageBlockDeviceSize() {
        if (this.mInternalStorageSize == 0) {
            this.mInternalStorageSize = this.mVold.getStorageSize();
        }
        return this.mInternalStorageSize;
    }

    public final int getInternalStorageRemainingLifetime() {
        getInternalStorageRemainingLifetime_enforcePermission();
        return this.mVold.getStorageRemainingLifetime();
    }

    public final PendingIntent getManageSpaceActivityIntent(String str, int i) {
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
            throw new SecurityException(VibrationParam$1$$ExternalSyntheticOutline0.m(callingUidOrThrow, "Unknown uid "), e);
        }
    }

    public final String getMountedObbPath(String str) {
        ObbState obbState;
        Objects.requireNonNull(str, "rawPath cannot be null");
        warnOnNotMounted();
        synchronized (this.mObbMounts) {
            obbState = (ObbState) ((HashMap) this.mObbPathToStateMap).get(str);
        }
        if (obbState != null) {
            return findVolumeByIdOrThrow(obbState.volId).getPath().getAbsolutePath();
        }
        Slog.w("StorageManagerService", "Failed to find OBB mounted at ".concat(str));
        return null;
    }

    public final String getPackageName(int i) {
        Slog.d("StorageManagerService", "ID of Client " + i);
        String str = "Unknown";
        for (String str2 : this.mContext.getPackageManager().getPackagesForUid(i)) {
            Slog.d("StorageManagerService", "Package name of Client " + str2);
            Iterator it = PASS_CLIENTS.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((String) it.next()).equals(str2)) {
                    str = str2;
                    break;
                }
            }
        }
        return str;
    }

    public final String getPassStorage() {
        int callingUid = Binder.getCallingUid();
        String packageName = getPackageName(callingUid);
        if (packageName.equals("Unknown")) {
            Slog.d("StorageManagerService", "Package name of Client not allowed ".concat(packageName));
            throw new SecurityException(" calling package is not allowed");
        }
        if (!isPlatformSignedApp(packageName)) {
            throw new SecurityException(" Signature of the calling package is not match");
        }
        try {
            return this.mVold.getPassStorage(packageName, this.mCurrentUserId, callingUid);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return null;
        }
    }

    public final String getPrimaryStorageUuid() {
        String str;
        synchronized (this.mLock) {
            str = this.mPrimaryStorageUuid;
        }
        return str;
    }

    public final String getSecureContainerFilesystemPath(String str) {
        enforcePermission$1("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        try {
            return this.mVold.asecFsPath(str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return null;
        }
    }

    public final String[] getSecureContainerList() {
        String[] strArr;
        enforcePermission$1("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        try {
            strArr = this.mVold.asecList();
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            strArr = new String[0];
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        synchronized (this.mAsecMountSet) {
            try {
                if (!checkExternalSecureContainerMounted()) {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = this.mAsecMountSet.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        PackageInstalledMap packageInstalledMap = (PackageInstalledMap) it.next();
                        if (packageInstalledMap.external) {
                            arrayList2.add(packageInstalledMap.id);
                            i++;
                        }
                    }
                    if (i > 0) {
                        Slog.d("StorageManagerService", "getSecureContainerList : (no media) lists = " + i);
                    }
                    arrayList.addAll(arrayList2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public final String getSecureContainerPath(String str) {
        Slog.d("StorageManagerService", "getSecureContainerPath: id=" + str);
        enforcePermission$1("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        if (isExternalSecureContainer(str) && !checkExternalSecureContainerMounted() && this.mAsecMountSet.contains(new PackageInstalledMap(str))) {
            return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("/mnt/asec/", str);
        }
        try {
            return this.mVold.asecPath(str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return null;
        }
    }

    public final long getUsedF2fsFileNode() {
        try {
            return this.mVold.getUsedF2fsFileNode();
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1L;
        }
    }

    public final int getUsedSpaceSecureContainer(String str) {
        enforcePermission$1("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            return this.mVold.asecGetUsedSpace(str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:118:0x019a, code lost:
    
        if (r8.getPath() == null) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01a6, code lost:
    
        if (r8.isVisibleForUser(r3) == false) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0141, code lost:
    
        if (r14 != 5) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x016b, code lost:
    
        if (r8.isVisibleForWrite(r39) != false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x016d, code lost:
    
        if (r2 == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0173, code lost:
    
        if (r8.isVisibleForWrite(r3) != false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x017b, code lost:
    
        if (r8.isVisibleForWrite(77) == false) goto L85;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x00ef A[Catch: all -> 0x0353, TryCatch #0 {all -> 0x0353, blocks: (B:43:0x00d4, B:45:0x00ea, B:47:0x00f7, B:162:0x00ef), top: B:42:0x00d4 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ea A[Catch: all -> 0x0353, TryCatch #0 {all -> 0x0353, blocks: (B:43:0x00d4, B:45:0x00ea, B:47:0x00f7, B:162:0x00ef), top: B:42:0x00d4 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0238 A[Catch: all -> 0x0157, TryCatch #2 {all -> 0x0157, blocks: (B:52:0x0119, B:54:0x0121, B:62:0x026d, B:63:0x0147, B:67:0x0150, B:70:0x015a, B:75:0x0167, B:78:0x016f, B:80:0x0175, B:86:0x0232, B:88:0x0238, B:90:0x023e, B:91:0x0244, B:93:0x0250, B:95:0x0256, B:96:0x0264, B:98:0x025f, B:101:0x01c1, B:103:0x01e0, B:106:0x01eb, B:109:0x0214, B:110:0x0180, B:112:0x0186, B:114:0x018e, B:117:0x0196, B:119:0x019c, B:121:0x01a2, B:124:0x01aa, B:130:0x027f, B:131:0x0288, B:133:0x0290, B:137:0x02bd, B:138:0x02a1, B:142:0x02ad, B:147:0x02c0), top: B:51:0x0119 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0250 A[Catch: all -> 0x0157, TryCatch #2 {all -> 0x0157, blocks: (B:52:0x0119, B:54:0x0121, B:62:0x026d, B:63:0x0147, B:67:0x0150, B:70:0x015a, B:75:0x0167, B:78:0x016f, B:80:0x0175, B:86:0x0232, B:88:0x0238, B:90:0x023e, B:91:0x0244, B:93:0x0250, B:95:0x0256, B:96:0x0264, B:98:0x025f, B:101:0x01c1, B:103:0x01e0, B:106:0x01eb, B:109:0x0214, B:110:0x0180, B:112:0x0186, B:114:0x018e, B:117:0x0196, B:119:0x019c, B:121:0x01a2, B:124:0x01aa, B:130:0x027f, B:131:0x0288, B:133:0x0290, B:137:0x02bd, B:138:0x02a1, B:142:0x02ad, B:147:0x02c0), top: B:51:0x0119 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.storage.StorageVolume[] getVolumeList(int r39, java.lang.String r40, int r41) {
        /*
            Method dump skipped, instructions count: 868
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.StorageManagerService.getVolumeList(int, java.lang.String, int):android.os.storage.StorageVolume[]");
    }

    public final VolumeRecord[] getVolumeRecords(int i) {
        VolumeRecord[] volumeRecordArr;
        synchronized (this.mLock) {
            try {
                volumeRecordArr = new VolumeRecord[this.mRecords.size()];
                for (int i2 = 0; i2 < this.mRecords.size(); i2++) {
                    volumeRecordArr[i2] = (VolumeRecord) this.mRecords.valueAt(i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return volumeRecordArr;
    }

    public final String getVolumeState(String str) {
        if (str == null) {
            return "unknown";
        }
        synchronized (this.mLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final VolumeInfo[] getVolumes(int i) {
        VolumeInfo[] volumeInfoArr;
        synchronized (this.mLock) {
            try {
                volumeInfoArr = new VolumeInfo[this.mVolumes.size()];
                for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                    volumeInfoArr[i2] = (VolumeInfo) this.mVolumes.valueAt(i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return volumeInfoArr;
    }

    public final boolean hasDeviceRestriction(String str) {
        Slog.w("StorageManagerService", "hasDeviceRestriction: ".concat(str));
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
                    Slog.e("StorageManagerService", str.concat(" policy not yet?"));
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

    public final boolean isAppIoBlocked(int i) {
        StorageUserConnection storageUserConnection;
        boolean contains;
        StorageSessionController storageSessionController = this.mStorageSessionController;
        storageSessionController.getClass();
        int userId = UserHandle.getUserId(i);
        synchronized (storageSessionController.mLock) {
            storageUserConnection = (StorageUserConnection) storageSessionController.mConnections.get(userId);
        }
        if (storageUserConnection == null) {
            return false;
        }
        synchronized (storageUserConnection.mSessionsLock) {
            contains = storageUserConnection.mUidsBlockedOnIo.contains(i);
        }
        return contains;
    }

    public final boolean isAppIoBlocked(String str, int i, int i2, int i3) {
        return isAppIoBlocked(i);
    }

    public final boolean isCeStorageUnlocked(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = ArrayUtils.contains(this.mCeUnlockedUsers.users, i);
        }
        return contains;
    }

    public final boolean isExternalSecureContainer(String str) {
        boolean z;
        synchronized (this.mAsecMountSet) {
            try {
                Iterator it = this.mAsecMountSet.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    PackageInstalledMap packageInstalledMap = (PackageInstalledMap) it.next();
                    if (packageInstalledMap.id.equals(str)) {
                        z = packageInstalledMap.external;
                        break;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Slog.d("StorageManagerService", "isExternalSecureContainer: id=" + str + " result=" + z);
        return z;
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

    public final boolean isMountDisallowedByEAS(boolean z) {
        Slog.w("StorageManagerService", "isMountDisallowedByEAS: storage_itpolicy from_intent " + z);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Slog.w("StorageManagerService", "isMountDisallowedByEAS: DevicePolicyManager is NULL");
            return false;
        }
        boolean semGetAllowStorageCard = devicePolicyManager.semGetAllowStorageCard(null);
        if (semGetAllowStorageCard) {
            Slog.w("StorageManagerService", "isMountDisallowedByEAS: check is true");
            this.preSDPolicy = true;
            return false;
        }
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

    public final boolean isObbMounted(String str) {
        boolean containsKey;
        Objects.requireNonNull(str, "rawPath cannot be null");
        synchronized (this.mObbMounts) {
            containsKey = ((HashMap) this.mObbPathToStateMap).containsKey(str);
        }
        return containsKey;
    }

    public final boolean isPassUnlocked() {
        int callingUid = Binder.getCallingUid();
        String packageName = getPackageName(callingUid);
        if (packageName.equals("Unknown")) {
            throw new SecurityException(" calling package is not allowed");
        }
        if (!isPlatformSignedApp(packageName)) {
            throw new SecurityException(" Signature of the calling package is not match");
        }
        try {
            return this.mVold.isPassUnlocked(packageName, this.mCurrentUserId, callingUid);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public final boolean isPlatformSignedApp(String str) {
        PackageInfo packageInfo;
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfoAsUser(str, 0, this.mCurrentUserId);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("StorageManagerService", "package not found: ".concat(str));
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        return packageInfo.applicationInfo.isSignedWithPlatformKey();
    }

    public final boolean isSecureContainerMounted(String str) {
        boolean contains;
        enforcePermission$1("android.permission.ASEC_ACCESS");
        warnOnNotMounted();
        synchronized (this.mAsecMountSet) {
            contains = this.mAsecMountSet.contains(new PackageInstalledMap(str));
            Slog.d("StorageManagerService", "isSecureContainerMounted: id=" + str + " ret=" + contains);
        }
        return contains;
    }

    public final boolean isSensitive(String str) {
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

    public final long lastMaintenance() {
        return this.mLastMaintenance;
    }

    public final void lockCeStorage(int i) {
        lockCeStorage_enforcePermission();
        if (i == 0 && UserManager.isHeadlessSystemUserMode()) {
            throw new IllegalArgumentException("Headless system user data cannot be locked..");
        }
        if (!isCeStorageUnlocked(i)) {
            Slog.d("StorageManagerService", "User " + i + "'s CE storage is already locked");
            return;
        }
        try {
            this.mVold.lockCeStorage(i);
            synchronized (this.mLock) {
                WatchedUnlockedUsers watchedUnlockedUsers = this.mCeUnlockedUsers;
                watchedUnlockedUsers.users = ArrayUtils.removeInt(watchedUnlockedUsers.users, i);
                UserManager.invalidateIsUserUnlockedCache();
            }
            if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace()) {
                dispatchCeStorageLockedEvent(i);
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final int lockPassStorage() {
        int callingUid = Binder.getCallingUid();
        String packageName = getPackageName(callingUid);
        if (packageName.equals("Unknown")) {
            throw new SecurityException(" calling package is not allowed");
        }
        if (!isPlatformSignedApp(packageName)) {
            throw new SecurityException(" Signature of the calling package is not match");
        }
        try {
            return this.mVold.lockPassStorage(packageName, this.mCurrentUserId, callingUid);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -12;
        }
    }

    public final void maybeRemountVolumes(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                try {
                    VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i2);
                    if (!volumeInfo.isPrimary() && volumeInfo.isMountedWritable() && volumeInfo.isVisible() && volumeInfo.getMountUserId() != this.mCurrentUserId) {
                        volumeInfo.mountUserId = this.mCurrentUserId;
                        arrayList.add(volumeInfo);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            VolumeInfo volumeInfo2 = (VolumeInfo) it.next();
            Slog.i("StorageManagerService", "Remounting volume for user: " + i + ". Volume: " + createVolumeInfoStrForPulbicVolume(volumeInfo2));
            this.mHandler.obtainMessage(8, volumeInfo2).sendToTarget();
            this.mHandler.obtainMessage(5, volumeInfo2).sendToTarget();
        }
    }

    public final void mkdirs(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        int userId = UserHandle.getUserId(callingUid);
        String m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(userId, "sys.user.", ".ce_available");
        if (!isCeStorageUnlocked(userId)) {
            throw new IllegalStateException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Failed to prepare ", str2));
        }
        if (userId == 0 && !SystemProperties.getBoolean(m, false)) {
            throw new IllegalStateException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Failed to prepare ", str2));
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
                absolutePath = absolutePath.concat("/");
            }
            Matcher matcher = KNOWN_APP_DIR_PATHS.matcher(absolutePath);
            if (!matcher.matches()) {
                throw new SecurityException("Invalid mkdirs path: " + canonicalFile + " is not a known app path.");
            }
            if (!matcher.group(3).equals(str)) {
                throw new SecurityException("Invalid mkdirs path: " + canonicalFile + " does not contain calling package " + str);
            }
            if ((matcher.group(2) != null && !matcher.group(2).equals(Integer.toString(userId))) || (matcher.group(2) == null && userId != this.mCurrentUserId)) {
                throw new SecurityException("Invalid mkdirs path: " + canonicalFile + " does not match calling user id " + userId);
            }
            try {
                this.mVold.setupAppDir(absolutePath, callingUid);
            } catch (RemoteException e) {
                throw new IllegalStateException("Failed to prepare " + absolutePath + ": " + e);
            }
        } catch (IOException e2) {
            throw new IllegalStateException("Failed to resolve " + str2 + ": " + e2);
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        try {
            this.mVold.monitor();
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
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
            extendWatchdogTimeout("#mount might be slow");
            if (((DevicePolicyManager) this.mContext.getSystemService("device_policy")).semGetRequireStorageCardEncryption(null)) {
                Slog.i("StorageManagerService", "Admin Stirage Card Encryption Policy is Set");
                i2 = 536870912;
            } else {
                i2 = 0;
            }
            this.mVold.mount(volumeInfo.id, i | volumeInfo.mountFlags | i2, volumeInfo.mountUserId, new IVoldMountCallback.Stub() { // from class: com.android.server.StorageManagerService.8
                @Override // android.os.IVoldMountCallback
                public final boolean onVolumeChecking(FileDescriptor fileDescriptor, String str, String str2) {
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
                        } catch (StorageSessionController.ExternalStorageServiceException e2) {
                            VolumeInfo volumeInfo3 = volumeInfo;
                            if (volumeInfo3.type == 0) {
                                StorageManagerService.this.getClass();
                                Slog.e("StorageManagerService", "Failed to mount volume " + StorageManagerService.createVolumeInfoStrForPulbicVolume(volumeInfo3), e2);
                            } else {
                                Slog.e("StorageManagerService", "Failed to mount volume " + volumeInfo, e2);
                            }
                            Slog.i("StorageManagerService", "Scheduling reset in 10s");
                            StorageManagerService.this.mHandler.removeMessages(10);
                            Callbacks callbacks = StorageManagerService.this.mHandler;
                            callbacks.sendMessageDelayed(callbacks.obtainMessage(10), TimeUnit.SECONDS.toMillis(10));
                            try {
                                parcelFileDescriptor.close();
                                return false;
                            } catch (Exception e3) {
                                Slog.e("StorageManagerService", "Failed to close FUSE device fd", e3);
                                return false;
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (Exception e4) {
                            Slog.e("StorageManagerService", "Failed to close FUSE device fd", e4);
                        }
                        throw th;
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

    public final void mount(String str) {
        mount_enforcePermission();
        Slog.d("StorageManagerService", "Begin mount: volId=" + str);
        VolumeInfo findVolumeByIdOrThrow = findVolumeByIdOrThrow(str);
        if (isMountDisallowed(findVolumeByIdOrThrow)) {
            throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Mounting ", str, " restricted by policy"));
        }
        synchronized (this.mLock) {
            try {
                if (!findVolumeByIdOrThrow.isPrimary() && findVolumeByIdOrThrow.isVisible() && findVolumeByIdOrThrow.getMountUserId() != this.mCurrentUserId) {
                    findVolumeByIdOrThrow.mountUserId = this.mCurrentUserId;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        mount(findVolumeByIdOrThrow);
    }

    public final void mountBySecApp(String str, String str2) {
        if (!chkMountUnmountFormatCaller(str2)) {
            throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Mount call denied to [", str2, "]"));
        }
        Slog.d("StorageManagerService", "Mount call accepted");
        mount(str);
    }

    public final void mountObb(String str, String str2, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo) {
        Objects.requireNonNull(str, "rawPath cannot be null");
        Objects.requireNonNull(str2, "canonicalPath cannot be null");
        Objects.requireNonNull(iObbActionListener, "token cannot be null");
        Objects.requireNonNull(obbInfo, "obbIfno cannot be null");
        int callingUid = Binder.getCallingUid();
        MountObbAction mountObbAction = new MountObbAction(new ObbState(str, str2, callingUid, iObbActionListener, i, null), callingUid, obbInfo);
        Callbacks callbacks = this.mObbActionHandler;
        callbacks.sendMessage(callbacks.obtainMessage(1, mountObbAction));
    }

    public final AppFuseMount mountProxyFileDescriptorBridge() {
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
                    throw new IllegalStateException(e2.getMessage(), e2);
                }
            }
            return appFuseMount;
        }
    }

    public final boolean mountSdpMediaStorageCmd(int i) {
        try {
            return this.mVold.mountSdpMediaStorageCmd(i);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public final int mountSecureContainer(String str, String str2, int i, boolean z) {
        int i2;
        enforcePermission$1("android.permission.ASEC_MOUNT_UNMOUNT");
        warnOnNotMounted();
        synchronized (this.mAsecMountSet) {
            try {
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
                            try {
                                if (secureContainerFilesystemPath.startsWith("/data/app-asec/")) {
                                    this.mAsecMountSet.add(new PackageInstalledMap(str, false));
                                }
                            } finally {
                            }
                        }
                        this.mAsecMountSet.add(new PackageInstalledMap(str, true));
                    }
                }
                return i2;
            } finally {
            }
        }
    }

    public final int mountVolume(String str) {
        Slog.i("StorageManagerService", "mountVolume : " + str);
        mount(findVolumeIdForPathOrThrow(str));
        return 0;
    }

    public final boolean mvFileAtData(String str, String str2) {
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
            CompletableFuture completableFuture = new CompletableFuture();
            this.mVold.mvFileAtData(str, str2, this.mMediaStoreAuthorityAppId, Process.myUid(), new AnonymousClass15(0, completableFuture));
            boolean z = ((PersistableBundle) completableFuture.get()).getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT, false);
            Slog.d("StorageManagerService", "Rename in media path result ".concat(z ? "true" : "false"));
            return z;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public final boolean needsCheckpoint() {
        needsCheckpoint_enforcePermission();
        return this.mVold.needsCheckpoint();
    }

    public final void notifyAppIoBlocked(String str, int i, int i2, int i3) {
        StorageUserConnection storageUserConnection;
        enforceExternalStorageService();
        StorageSessionController storageSessionController = this.mStorageSessionController;
        storageSessionController.getClass();
        int userId = UserHandle.getUserId(i);
        synchronized (storageSessionController.mLock) {
            storageUserConnection = (StorageUserConnection) storageSessionController.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            synchronized (storageUserConnection.mSessionsLock) {
                storageUserConnection.mUidsBlockedOnIo.put(i, Integer.valueOf(((Integer) storageUserConnection.mUidsBlockedOnIo.get(i, 0)).intValue() + 1));
            }
        }
    }

    public final void notifyAppIoResumed(String str, int i, int i2, int i3) {
        StorageUserConnection storageUserConnection;
        enforceExternalStorageService();
        StorageSessionController storageSessionController = this.mStorageSessionController;
        storageSessionController.getClass();
        int userId = UserHandle.getUserId(i);
        synchronized (storageSessionController.mLock) {
            storageUserConnection = (StorageUserConnection) storageSessionController.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            synchronized (storageUserConnection.mSessionsLock) {
                try {
                    int intValue = ((Integer) storageUserConnection.mUidsBlockedOnIo.get(i, 0)).intValue();
                    if (intValue == 0) {
                        Slog.w("StorageUserConnection", "Unexpected app IO resumption for uid: " + i);
                    }
                    if (intValue <= 1) {
                        storageUserConnection.mUidsBlockedOnIo.remove(i);
                    } else {
                        storageUserConnection.mUidsBlockedOnIo.put(i, Integer.valueOf(intValue - 1));
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onAwakeStateChanged(boolean z) {
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onKeyguardStateChanged(boolean z) {
        boolean z2 = z && ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(this.mCurrentUserId);
        if (this.mSecureKeyguardShowing != z2) {
            this.mSecureKeyguardShowing = z2;
            this.mHandler.obtainMessage(17, Boolean.valueOf(this.mSecureKeyguardShowing)).sendToTarget();
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
            this.mHandler.obtainMessage(18).sendToTarget();
        }
        if (PackageManager.isMoveStatusFinished(i)) {
            Slog.d("StorageManagerService", "Move to " + this.mMoveTargetUuid + " finished with status " + i);
            this.mMoveCallback = null;
            this.mMoveTargetUuid = null;
        }
    }

    public final ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) {
        Slog.v("StorageManagerService", "mountProxyFileDescriptor");
        int i4 = i3 & 805306368;
        try {
            synchronized (this.mAppFuseLock) {
                try {
                    AppFuseBridge appFuseBridge = this.mAppFuseBridge;
                    if (appFuseBridge == null) {
                        Slog.e("StorageManagerService", "FuseBridge has not been created");
                        return null;
                    }
                    return appFuseBridge.openFile(i, i2, i4);
                } finally {
                }
            }
        } catch (FuseUnavailableMountException | InterruptedException e) {
            Slog.v("StorageManagerService", "The mount point has already been invalid", e);
            return null;
        }
    }

    public final void partitionMixed(String str, int i) {
        partitionMixed_enforcePermission();
        Slog.d("StorageManagerService", "partitionMixed: diskId=" + str);
        enforceAdminUser();
        CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        extendWatchdogTimeout("#partition might be slow");
        try {
            this.mVold.partition(str, 2, i);
            waitForLatch(findOrCreateDiskScanLatch, 180000L, "partitionMixed");
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final void partitionPrivate(String str) {
        partitionPrivate_enforcePermission();
        enforceAdminUser();
        Slog.d("StorageManagerService", "partitionPrivate: diskId=" + str);
        CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        extendWatchdogTimeout("#partition might be slow");
        try {
            this.mVold.partition(str, 1, -1);
            waitForLatch(findOrCreateDiskScanLatch, 180000L, "partitionPrivate");
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final void partitionPublic(String str) {
        partitionPublic_enforcePermission();
        Slog.d("StorageManagerService", "partitionPublic: diskId=" + str);
        CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        extendWatchdogTimeout("#partition might be slow");
        try {
            this.mVold.partition(str, 0, -1);
            waitForLatch(findOrCreateDiskScanLatch, 600000L, "partitionPublic");
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final void prepareUserStorage(String str, int i, int i2) {
        prepareUserStorage_enforcePermission();
        try {
            prepareUserStorageInternal(i, i2, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            prepareUserStorageInternal(userInfo.id, i, volumeInfo.fsUuid);
        }
    }

    public final void prepareUserStorageInternal(int i, int i2, String str) {
        VolumeInfo findVolumeByUuid;
        try {
            this.mVold.prepareUserStorage(str, i, i2);
            if (str != null && (findVolumeByUuid = ((StorageManager) this.mContext.getSystemService(StorageManager.class)).findVolumeByUuid(str)) != null && i == 0 && findVolumeByUuid.type == 1) {
                Installer installer = this.mInstaller;
                if (installer.checkBeforeRemote()) {
                    try {
                        installer.mInstalld.tryMountDataMirror(str);
                    } catch (Exception e) {
                        Installer.InstallerException.from(e);
                        throw null;
                    }
                }
            }
            if (i == 0) {
                try {
                    new Sender(this.mContext).send("FBE4", "encryption policy success");
                    Slog.i("StorageManagerService", "encryption policy for SA logging");
                } catch (Exception e2) {
                    Slog.e("StorageManagerService", "encryption policy for SA logging : " + e2.toString());
                }
            }
        } catch (Exception e3) {
            EventLog.writeEvent(1397638484, "224585613", -1, "");
            Slog.wtf("StorageManagerService", e3);
            String str2 = SystemProperties.get("security.fbe.global_de");
            UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            if (!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK", false) && !"loaded".equals(str2) && !userManagerInternal.shouldIgnorePrepareStorageErrors(i)) {
                throw new RuntimeException(e3);
            }
            Slog.wtf("StorageManagerService", "ignoring error preparing storage for existing user " + i + "; device may be insecure!");
            if (i != 0 || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK", false)) {
                return;
            }
            try {
                new Sender(this.mContext).send("FBE5", SystemProperties.get("security.fbe.fail_cause"));
                Slog.i("StorageManagerService", "encryption policy for SA logging");
            } catch (Exception e4) {
                Slog.e("StorageManagerService", "encryption policy for SA logging : " + e4.toString());
            }
        }
    }

    public final void readSettingsLocked() {
        this.mRecords.clear();
        this.mPrimaryStorageUuid = SystemProperties.getBoolean("ro.vold.primary_physical", false) ? "primary_physical" : StorageManager.UUID_PRIVATE_INTERNAL;
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream openRead = this.mSettingsFile.openRead();
                try {
                    Slog.d("StorageManagerService", "readSettingsLocked: " + this.mSettingsFile.getBaseFile().getAbsolutePath());
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            IoUtils.closeQuietly(openRead);
                            return;
                        }
                        if (next == 2) {
                            String name = resolvePullParser.getName();
                            if ("volumes".equals(name)) {
                                int attributeInt = resolvePullParser.getAttributeInt((String) null, "version", 1);
                                boolean z = SystemProperties.getBoolean("ro.vold.primary_physical", false);
                                if (attributeInt >= 3 || (attributeInt >= 2 && !z)) {
                                    this.mPrimaryStorageUuid = XmlUtils.readStringAttribute(resolvePullParser, "primaryStorageUuid");
                                }
                            } else if ("volume".equals(name)) {
                                VolumeRecord readVolumeRecord = readVolumeRecord(resolvePullParser);
                                this.mRecords.put(readVolumeRecord.fsUuid, readVolumeRecord);
                                Slog.d("StorageManagerService", "readSettingsLocked: TAG_VOLUME rec=" + readVolumeRecord + " rec.fsUuid=" + readVolumeRecord.fsUuid);
                            }
                        }
                    }
                } catch (FileNotFoundException unused) {
                    fileInputStream = openRead;
                    IoUtils.closeQuietly(fileInputStream);
                } catch (IOException e) {
                    e = e;
                    fileInputStream = openRead;
                    Slog.wtf("StorageManagerService", "Failed reading metadata", e);
                    IoUtils.closeQuietly(fileInputStream);
                } catch (XmlPullParserException e2) {
                    e = e2;
                    fileInputStream = openRead;
                    Slog.wtf("StorageManagerService", "Failed reading metadata", e);
                    IoUtils.closeQuietly(fileInputStream);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = openRead;
                    IoUtils.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (FileNotFoundException unused2) {
                IoUtils.closeQuietly(fileInputStream);
            } catch (IOException e3) {
                e = e3;
            } catch (XmlPullParserException e4) {
                e = e4;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final boolean refreshLifetimeConstraint() {
        try {
            int storageLifeTime = this.mVold.getStorageLifeTime();
            if (storageLifeTime == -1) {
                Slog.w("StorageManagerService", "Failed to get storage lifetime");
                return false;
            }
            if (storageLifeTime <= 0) {
                Slog.i("StorageManagerService", "Storage lifetime: " + storageLifeTime);
                return true;
            }
            Slog.w("StorageManagerService", "Ended smart idle maintenance, because of lifetime(" + storageLifeTime + "), lifetime threshold(0)");
            this.mPassedLifetimeThresh = true;
            return false;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
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
        if (str2.equals("1") && this.mContext.getResources().getBoolean(R.bool.skipHoldBeforeMerge)) {
            ZramWriteback.scheduleZramWriteback(this.mContext);
        }
    }

    public final void registerListener(IStorageEventListener iStorageEventListener) {
        ((RemoteCallbackList) this.mCallbacks.mCallbacks).register(iStorageEventListener);
    }

    public final int renameSecureContainer(String str, String str2) {
        enforcePermission$1("android.permission.ASEC_RENAME");
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

    public final int reserveDataBlocks(long j) {
        return -1;
    }

    public final void resetIfBootedAndConnected() {
        int[] copyOf;
        HashSet hashSet;
        Slog.d("StorageManagerService", "Thinking about reset, mBootCompleted=" + this.mBootCompleted + ", mDaemonConnected=" + this.mDaemonConnected);
        if (this.mBootCompleted && this.mDaemonConnected) {
            UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
            List<UserInfo> users = userManager.getUsers();
            extendWatchdogTimeout("#onReset might be slow");
            StorageSessionController storageSessionController = this.mStorageSessionController;
            IVold iVold = this.mVold;
            if (storageSessionController.shouldHandle(null)) {
                SparseArray sparseArray = new SparseArray();
                synchronized (storageSessionController.mLock) {
                    try {
                        storageSessionController.mIsResetting = true;
                        Slog.i("StorageSessionController", "Started resetting external storage service...");
                        for (int i = 0; i < storageSessionController.mConnections.size(); i++) {
                            sparseArray.put(storageSessionController.mConnections.keyAt(i), (StorageUserConnection) storageSessionController.mConnections.valueAt(i));
                        }
                    } finally {
                    }
                }
                for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                    StorageUserConnection storageUserConnection = (StorageUserConnection) sparseArray.valueAt(i2);
                    synchronized (storageUserConnection.mSessionsLock) {
                        hashSet = new HashSet(((HashMap) storageUserConnection.mSessions).keySet());
                    }
                    Iterator it = hashSet.iterator();
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        try {
                            Slog.i("StorageSessionController", "Unmounting " + str);
                            iVold.unmount(str);
                            Slog.i("StorageSessionController", "Unmounted " + str);
                        } catch (ServiceSpecificException | RemoteException e) {
                            Slog.e("StorageSessionController", "Failed to unmount volume: " + str, e);
                        }
                        try {
                            Slog.i("StorageSessionController", "Exiting " + str);
                            storageUserConnection.removeSessionAndWait(str);
                            Slog.i("StorageSessionController", "Exited " + str);
                        } catch (StorageSessionController.ExternalStorageServiceException | IllegalStateException e2) {
                            Slog.e("StorageSessionController", "Failed to exit session: " + str + ". Killing MediaProvider...", e2);
                            int keyAt = sparseArray.keyAt(i2);
                            try {
                                ActivityManager.getService().killApplication(storageSessionController.mExternalStorageServicePackageName, storageSessionController.mExternalStorageServiceAppId, keyAt, "storage_session_controller reset", 13);
                            } catch (RemoteException unused) {
                                Slog.i("StorageSessionController", "Failed to kill the ExtenalStorageService for user " + keyAt);
                            }
                        }
                    }
                    storageUserConnection.mActiveConnection.close();
                    storageUserConnection.mHandlerThread.quit();
                }
                this.mHandler.removeCallbacksAndMessages(null);
                synchronized (storageSessionController.mLock) {
                    storageSessionController.mConnections.clear();
                    storageSessionController.mIsResetting = false;
                    Slog.i("StorageSessionController", "Finished resetting external storage service");
                }
            }
            synchronized (this.mLock) {
                int[] iArr = this.mSystemUnlockedUsers;
                copyOf = Arrays.copyOf(iArr, iArr.length);
                this.mDisks.clear();
                this.mVolumes.clear();
                VolumeInfo volumeInfo = new VolumeInfo("private", 1, (DiskInfo) null, (String) null);
                volumeInfo.state = 2;
                volumeInfo.path = Environment.getDataDirectory().getAbsolutePath();
                this.mVolumes.put(volumeInfo.id, volumeInfo);
            }
            try {
                Slog.i("StorageManagerService", "Resetting vold...");
                this.mVold.reset();
                Slog.i("StorageManagerService", "Reset vold");
                cleanupSercureContainerList();
                for (UserInfo userInfo : users) {
                    if (userInfo.isCloneProfile()) {
                        this.mVold.onUserAdded(userInfo.id, userInfo.serialNumber, userInfo.profileGroupId);
                    } else {
                        this.mVold.onUserAdded(userInfo.id, userInfo.serialNumber, -1);
                    }
                }
                for (int i3 : copyOf) {
                    this.mVold.onUserStarted(i3);
                    this.mStoraged.onUserStarted(i3);
                }
                restoreSystemUnlockedUsers(userManager, users, copyOf);
                this.mVold.onSecureKeyguardStateChanged(this.mSecureKeyguardShowing);
                this.mStorageManagerInternal.onReset(this.mVold);
            } catch (Exception e3) {
                Slog.wtf("StorageManagerService", e3);
            }
        }
    }

    public final int resizeSecureContainer(String str, int i, String str2) {
        enforcePermission$1("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            this.mVold.asecResize(str, i, str2);
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
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

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.server.HeimdAllFsService$1, java.lang.Thread] */
    public final void runIdleMaint(Runnable runnable) {
        enforcePermission$1("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            this.mHeimdAllFs = new HeimdAllFsService(this.mContext);
            if (needsCheckpoint()) {
                enforcePermission$1("android.permission.MOUNT_FORMAT_FILESYSTEMS");
                if (this.mVold.supportsBlockCheckpoint()) {
                    Slog.i("StorageManagerService", "Skipping idle maintenance - block based checkpoint in progress");
                }
            }
            this.mVold.runIdleMaint(this.mNeedGC, new AnonymousClass11(this, runnable, 0));
            final HeimdAllFsService heimdAllFsService = this.mHeimdAllFs;
            if (heimdAllFsService != null) {
                ?? anonymousClass1 = new Thread() { // from class: com.android.server.HeimdAllFsService.1
                    public AnonymousClass1() {
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
                    public final void run() {
                        CountDownLatch countDownLatch;
                        String format;
                        try {
                            try {
                                android.util.Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread Start");
                                HeimdAllFsService.this.mHeimdallFsLatch = new CountDownLatch(1);
                                format = new SimpleDateFormat("yyMMdd").format(new Date());
                                if (HeimdAllFsService.mIsUserTrial) {
                                    HeimdAllFsService.this.getClass();
                                    HeimdAllFsService.setTriggerThreshold();
                                }
                            } catch (Exception e) {
                                android.util.Slog.e("HeimdAllFS", "Exception!!");
                                android.util.Slog.wtf("HeimdAllFS", e);
                                android.util.Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                                countDownLatch = HeimdAllFsService.this.mHeimdallFsLatch;
                                if (countDownLatch == null) {
                                    return;
                                }
                            }
                            if (!HeimdAllFsService.mForceService && SystemProperties.get("sys.heimdallfs.todayinfo").equals(format)) {
                                android.util.Slog.i("HeimdAllFS", "Once a day, bye bye");
                                android.util.Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                                CountDownLatch countDownLatch2 = HeimdAllFsService.this.mHeimdallFsLatch;
                                if (countDownLatch2 != null) {
                                    countDownLatch2.countDown();
                                    return;
                                }
                                return;
                            }
                            SystemProperties.set("sys.heimdallfs.todayinfo", format);
                            HeimdAllFsService heimdAllFsService2 = HeimdAllFsService.this;
                            heimdAllFsService2.mPackagesInfo = heimdAllFsService2.getPackagesOnUserdata();
                            if (HeimdAllFsService.isServiceActivate(FunctionOrder.DEFRAG)) {
                                android.util.Slog.i("HeimdAllFS", "Defrag!!");
                            }
                            if (HeimdAllFsService.isServiceActivate(FunctionOrder.COMPRESS)) {
                                HeimdAllFsService heimdAllFsService3 = HeimdAllFsService.this;
                                heimdAllFsService3.startCompress((List) heimdAllFsService3.mPackagesInfo.get(0));
                            }
                            android.util.Slog.i("HeimdAllFS", "HeimdAllFS Thread End Normally");
                            android.util.Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                            countDownLatch = HeimdAllFsService.this.mHeimdallFsLatch;
                            if (countDownLatch == null) {
                                return;
                            }
                            countDownLatch.countDown();
                        } catch (Throwable th) {
                            android.util.Slog.i("HeimdAllFS", "runIdleMaint, HeimdAllFS Thread End, latch down");
                            CountDownLatch countDownLatch3 = HeimdAllFsService.this.mHeimdallFsLatch;
                            if (countDownLatch3 != null) {
                                countDownLatch3.countDown();
                            }
                            throw th;
                        }
                    }
                };
                heimdAllFsService.mHeimdallFsThread = anonymousClass1;
                anonymousClass1.start();
            }
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
        }
    }

    public final void runIdleMaintenance() {
        runIdleMaint(null);
    }

    public final void runMaintenance() {
        runMaintenance_enforcePermission();
        Callbacks callbacks = this.mHandler;
        callbacks.sendMessage(callbacks.obtainMessage(4, null));
    }

    public final int semGetExternalSdCardHealthState() {
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

    public final String semGetExternalSdCardId() {
        String str;
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager.checkSignatures("android", packageManager.getNameForUid(Binder.getCallingUid())) != 0) {
            Slog.e("StorageManagerService", "The caller is not qualified.");
            return null;
        }
        File file = new File("/sys/class/sec/sdinfo/data");
        if (!file.exists()) {
            Slog.d("StorageManagerService", "EXTERNAL SD CARD CID VALUE FILE does not exist!!");
            return null;
        }
        try {
            str = FileUtils.readTextFile(file, 0, null).trim();
        } catch (Exception e) {
            Slog.e("StorageManagerService", "Error at reading SD Card CID Value", e);
            str = "empty";
        }
        if (!"empty".equals(str) && !"No Card".equalsIgnoreCase(str)) {
            return str;
        }
        Slog.d("StorageManagerService", "External SD Card CID value -> return as null");
        return null;
    }

    public final void setCeStorageProtection(int i, byte[] bArr) {
        setCeStorageProtection_enforcePermission();
        this.mVold.setCeStorageProtection(i, bArr);
    }

    public final void setCloudMediaProvider(String str) {
        enforceExternalStorageService();
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        synchronized (this.mCloudMediaProviders) {
            try {
                if (!Objects.equals(str, (String) this.mCloudMediaProviders.get(userId))) {
                    this.mCloudMediaProviders.put(userId, str);
                    this.mHandler.obtainMessage(16, userId, 0, str).sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDebugFlags(int i, int i2) {
        long clearCallingIdentity;
        setDebugFlags_enforcePermission();
        String str = "";
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
            } else if ((i & 8) != 0) {
                str = "force_off";
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

    public final boolean setDualDARPolicyCmd(int i, int i2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0) {
            throw new SecurityException("System can call this API");
        }
        try {
            return this.mVold.setDualDARPolicyCmd(i, i2);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public final void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) {
        setPrimaryStorageUuid_enforcePermission();
        synchronized (this.mLock) {
            try {
                if (Objects.equals(this.mPrimaryStorageUuid, str)) {
                    throw new IllegalArgumentException("Primary storage already at " + str);
                }
                if (this.mMoveCallback != null) {
                    throw new IllegalStateException("Move already in progress");
                }
                this.mMoveCallback = iPackageMoveObserver;
                this.mMoveTargetUuid = str;
                List<UserInfo> users = ((UserManager) this.mContext.getSystemService(UserManager.class)).getUsers();
                for (UserInfo userInfo : users) {
                    if (StorageManager.isFileEncrypted() && !isCeStorageUnlocked(userInfo.id)) {
                        Slog.w("StorageManagerService", "Failing move due to locked user " + userInfo.id);
                        onMoveStatusLocked(-10);
                        return;
                    }
                }
                if (!"primary_physical".equals(this.mPrimaryStorageUuid) && !"primary_physical".equals(str)) {
                    int i = this.mCurrentUserId;
                    VolumeInfo findStorageForUuidAsUser = findStorageForUuidAsUser(i, this.mPrimaryStorageUuid);
                    VolumeInfo findStorageForUuidAsUser2 = findStorageForUuidAsUser(i, str);
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
                        String str2 = this.mPrimaryStorageUuid;
                        for (UserInfo userInfo2 : users) {
                            prepareUserStorageInternal(userInfo2.id, 3, str2);
                            prepareUserStorageInternal(userInfo2.id, 3, str);
                        }
                        try {
                            this.mVold.moveStorage(findStorageForUuidAsUser.id, findStorageForUuidAsUser2.id, new AnonymousClass15(2, this));
                            return;
                        } catch (Exception e) {
                            Slog.wtf("StorageManagerService", e);
                            return;
                        }
                    } catch (Exception e2) {
                        Slog.w("StorageManagerService", "Failing move due to failure on prepare user data", e2);
                        synchronized (this.mLock) {
                            onMoveStatusLocked(-6);
                            return;
                        }
                    }
                }
                Slog.d("StorageManagerService", "Skipping move to/from primary physical");
                onMoveStatusLocked(82);
                onMoveStatusLocked(-100);
                this.mHandler.obtainMessage(10).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setSdpPolicyCmd(int i) {
        try {
            return this.mVold.setSdpPolicyCmd(i);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public final boolean setSdpPolicyToPathCmd(int i, String str) {
        try {
            return this.mVold.setSdpPolicyToPathCmd(i, str);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return false;
        }
    }

    public final boolean setSensitive(int i, String str) {
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

    public final void setVolumeNickname(String str, String str2) {
        setVolumeNickname_enforcePermission();
        Slog.d("StorageManagerService", "setVolumeNickname: fsUuid=" + str + " nickname=" + str2);
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.get(str);
            volumeRecord.nickname = str2;
            Callbacks callbacks = this.mCallbacks;
            int i = Callbacks.$r8$clinit;
            callbacks.getClass();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = volumeRecord.clone();
            callbacks.obtainMessage(3, obtain).sendToTarget();
            writeSettingsLocked();
        }
    }

    public final void setVolumeUserFlags(String str, int i, int i2) {
        setVolumeUserFlags_enforcePermission();
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "setVolumeUserFlags: fsUuid=", str, " flags=", " mask=");
        m.append(i2);
        Slog.d("StorageManagerService", m.toString());
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            VolumeRecord volumeRecord = (VolumeRecord) this.mRecords.get(str);
            volumeRecord.userFlags = (i & i2) | (volumeRecord.userFlags & (~i2));
            Callbacks callbacks = this.mCallbacks;
            int i3 = Callbacks.$r8$clinit;
            callbacks.getClass();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = volumeRecord.clone();
            callbacks.obtainMessage(3, obtain).sendToTarget();
            writeSettingsLocked();
        }
    }

    public final boolean shrinkDataDdp(long j) {
        return false;
    }

    public final void shutdown(IStorageShutdownObserver iStorageShutdownObserver) {
        shutdown_enforcePermission();
        Slog.i("StorageManagerService", "Shutting down");
        this.mHandler.obtainMessage(3, iStorageShutdownObserver).sendToTarget();
    }

    public final void startCheckpoint(int i) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0 && callingUid != 2000) {
            throw new SecurityException("no permission to start filesystem checkpoint");
        }
        this.mVold.startCheckpoint(i);
    }

    public final boolean supportsCheckpoint() {
        return this.mVold.supportsCheckpoint();
    }

    public final int trimSecureContainer(String str, int i, String str2) {
        enforcePermission$1("android.permission.ASEC_CREATE");
        warnOnNotMounted();
        try {
            this.mVold.asecTrim(str, i, str2);
            return 0;
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -1;
        }
    }

    public final void unlockCeStorage(int i, byte[] bArr) {
        unlockCeStorage_enforcePermission();
        if (StorageManager.isFileEncrypted()) {
            this.mVold.unlockCeStorage(i, bArr);
        }
        synchronized (this.mLock) {
            WatchedUnlockedUsers watchedUnlockedUsers = this.mCeUnlockedUsers;
            watchedUnlockedUsers.users = ArrayUtils.appendInt(watchedUnlockedUsers.users, i);
            UserManager.invalidateIsUserUnlockedCache();
        }
    }

    public final int unlockPassStorage() {
        int callingUid = Binder.getCallingUid();
        String packageName = getPackageName(callingUid);
        if (packageName.equals("Unknown")) {
            throw new SecurityException(" calling package is not allowed");
        }
        if (isRootedDevice()) {
            Slog.d("StorageManagerService", "isDetectedRoot: true ");
            throw new SecurityException(" Root detected");
        }
        if (!isPlatformSignedApp(packageName)) {
            throw new SecurityException(" Signature of the calling package is not match");
        }
        try {
            return this.mVold.unlockPassStorage(packageName, this.mCurrentUserId, callingUid);
        } catch (Exception e) {
            Slog.wtf("StorageManagerService", e);
            return -12;
        }
    }

    public final void unmount(VolumeInfo volumeInfo) {
        try {
            try {
                if (volumeInfo.type == 1) {
                    Installer installer = this.mInstaller;
                    String fsUuid = volumeInfo.getFsUuid();
                    if (installer.checkBeforeRemote()) {
                        try {
                            installer.mInstalld.onPrivateVolumeRemoved(fsUuid);
                        } catch (Exception e) {
                            Installer.InstallerException.from(e);
                            throw null;
                        }
                    }
                }
            } catch (Installer.InstallerException e2) {
                Slog.e("StorageManagerService", "Failed unmount mirror data", e2);
            }
            this.mVold.unmount(volumeInfo.id);
            StorageSessionController storageSessionController = this.mStorageSessionController;
            storageSessionController.getClass();
            String id = volumeInfo.getId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    StorageUserConnection onVolumeRemove = storageSessionController.onVolumeRemove(volumeInfo);
                    if (volumeInfo.type == 0) {
                        Slog.i("StorageSessionController", "On volume unmount " + StorageSessionController.createVolumeInfoStrForPulbicVolume(volumeInfo));
                    } else {
                        Slog.i("StorageSessionController", "On volume unmount " + volumeInfo);
                    }
                    if (onVolumeRemove != null) {
                        onVolumeRemove.removeSessionAndWait(id);
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (StorageSessionController.ExternalStorageServiceException e3) {
                Slog.e("StorageSessionController", "Failed to end session for vol with id: " + id, e3);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Exception e4) {
            Slog.wtf("StorageManagerService", e4);
        }
    }

    public final void unmount(String str) {
        DiskInfo diskInfo;
        unmount_enforcePermission();
        Slog.d("StorageManagerService", "Begin unmount: volId=" + str);
        VolumeInfo findVolumeByIdOrThrow = findVolumeByIdOrThrow(str);
        if (findVolumeByIdOrThrow.isPrimaryPhysical() || ((diskInfo = findVolumeByIdOrThrow.disk) != null && diskInfo.isSd())) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (this.mUnmountLock) {
                    this.mUnmountSignal = new CountDownLatch(1);
                    PackageManagerService.this.mCustomInjector.getAsecInstallHelper().updateExternalMediaStatus(false, true);
                    try {
                        waitForLatch(this.mUnmountSignal, -1L, "mUnmountSignal");
                    } catch (TimeoutException e) {
                        Slog.e("StorageManagerService", "TimeoutException in ".concat("mUnmountSignal"), e);
                    }
                    this.mUnmountSignal = null;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        unmount(findVolumeByIdOrThrow);
    }

    public final void unmountBySecApp(String str, String str2) {
        if (!chkMountUnmountFormatCaller(str2)) {
            throw new SecurityException(XmlUtils$$ExternalSyntheticOutline0.m("Unmount call denied to [", str2, "]"));
        }
        Slog.d("StorageManagerService", "Unmount call accepted");
        unmount(str);
    }

    public final void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) {
        ObbState obbState;
        Objects.requireNonNull(str, "rawPath cannot be null");
        synchronized (this.mObbMounts) {
            obbState = (ObbState) ((HashMap) this.mObbPathToStateMap).get(str);
        }
        if (obbState == null) {
            Slog.w("StorageManagerService", "Unknown OBB mount at ".concat(str));
            return;
        }
        UnmountObbAction unmountObbAction = new UnmountObbAction(new ObbState(str, obbState.canonicalPath, Binder.getCallingUid(), iObbActionListener, i, obbState.volId), z);
        Callbacks callbacks = this.mObbActionHandler;
        callbacks.sendMessage(callbacks.obtainMessage(1, unmountObbAction));
    }

    public final int unmountSecureContainer(String str, boolean z) {
        Slog.d("StorageManagerService", "unmountSecureContainer: id=" + str + " force=" + z);
        enforcePermission$1("android.permission.ASEC_MOUNT_UNMOUNT");
        warnOnNotMounted();
        synchronized (this.mAsecMountSet) {
            try {
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
            } finally {
            }
        }
    }

    public final void unmountVolume(String str, boolean z, boolean z2) {
        Slog.i("StorageManagerService", "unmountVolume :" + str);
        unmount(findVolumeIdForPathOrThrow(str));
    }

    public final void unregisterListener(IStorageEventListener iStorageEventListener) {
        ((RemoteCallbackList) this.mCallbacks.mCallbacks).unregister(iStorageEventListener);
    }

    public final void updateLegacyStorageApps(int i, String str, boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    Slog.v("StorageManagerService", "Package " + str + " has legacy storage");
                    ((ArraySet) this.mUidsWithLegacyExternalStorage).add(Integer.valueOf(i));
                } else {
                    Slog.v("StorageManagerService", "Package " + str + " does not have legacy storage");
                    ((ArraySet) this.mUidsWithLegacyExternalStorage).remove(Integer.valueOf(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
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

    public final void waitForAsecScan() {
        try {
            waitForLatch(this.mAsecsScanned, -1L, "mAsecsScanned");
        } catch (TimeoutException e) {
            Slog.e("StorageManagerService", "TimeoutException in ".concat("mAsecsScanned"), e);
        }
    }

    public final void warnOnNotMounted() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                try {
                    VolumeInfo volumeInfo = (VolumeInfo) this.mVolumes.valueAt(i);
                    if (volumeInfo.isPrimary() && volumeInfo.isMountedWritable()) {
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Slog.w("StorageManagerService", "No primary storage mounted!");
        }
    }

    public final void writeSettingsLocked() {
        FileOutputStream startWrite;
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mSettingsFile.startWrite();
        } catch (IOException unused) {
        }
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
        } catch (IOException unused2) {
            fileOutputStream = startWrite;
            if (fileOutputStream != null) {
                this.mSettingsFile.failWrite(fileOutputStream);
            }
        }
    }
}
