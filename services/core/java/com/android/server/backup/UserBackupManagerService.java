package com.android.server.backup;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.IActivityManager;
import android.app.IBackupAgent;
import android.app.PendingIntent;
import android.app.backup.IBackupManager;
import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IBackupObserver;
import android.app.backup.IFullBackupRestoreObserver;
import android.app.backup.IMemorySaverBackupRestoreObserver;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SELinux;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.WorkSource;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.FeatureFlagUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.fullbackup.FullBackupEntry;
import com.android.server.backup.fullbackup.PerformFullTransportBackupTask;
import com.android.server.backup.internal.BackupHandler;
import com.android.server.backup.internal.ClearDataObserver;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.internal.Operation;
import com.android.server.backup.internal.PerformInitializeTask;
import com.android.server.backup.internal.RunInitializeReceiver;
import com.android.server.backup.internal.SetupObserver;
import com.android.server.backup.keyvalue.BackupRequest;
import com.android.server.backup.params.AdbBackupParams;
import com.android.server.backup.params.AdbParams;
import com.android.server.backup.params.AdbRestoreParams;
import com.android.server.backup.params.BackupParams;
import com.android.server.backup.params.ClearParams;
import com.android.server.backup.params.ClearRetryParams;
import com.android.server.backup.restore.ActiveRestoreSession;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.OnTransportRegisteredListener;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.backup.transport.TransportNotAvailableException;
import com.android.server.backup.transport.TransportNotRegisteredException;
import com.android.server.backup.utils.BackupEligibilityRules;
import com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils;
import com.android.server.backup.utils.BackupManagerMonitorEventSender;
import com.android.server.backup.utils.BackupObserverUtils;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import dalvik.annotation.optimization.NeverCompile;

import com.google.android.collect.Sets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class UserBackupManagerService {
    public static BackupManagerYuva mBackupManagerYuva;
    public static Boolean mSplitBackupFlag;
    public static Boolean mSplitRestoreFlag;
    public ActiveRestoreSession mActiveRestoreSession;
    public final IActivityManager mActivityManager;
    public final ActivityManagerInternal mActivityManagerInternal;
    public AdbBackupParams mAdbBackupParams;
    public final SparseArray mAdbBackupRestoreConfirmations;
    public AdbRestoreParams mAdbRestoreParams;
    public final Object mAgentConnectLock;
    public final BackupAgentTimeoutParameters mAgentTimeoutParameters;
    public final AlarmManager mAlarmManager;
    public volatile long mAncestralBackupDestination;
    public Set mAncestralPackages;
    public File mAncestralSerialNumberFile;
    public long mAncestralToken;
    public boolean mAutoRestore;
    public final BackupHandler mBackupHandler;
    public final IBackupManager mBackupManagerBinder;
    public final SparseArray mBackupParticipants;
    public final BackupPasswordManager mBackupPasswordManager;
    public final UserBackupPreferences mBackupPreferences;
    public final Object mBackupRestoreLock;
    public volatile boolean mBackupRunning;
    public final File mBaseStateDir;
    public final Object mClearDataLock;
    public volatile boolean mClearingData;
    public IBackupAgent mConnectedAgent;
    public volatile boolean mConnecting;
    public final BackupManagerConstants mConstants;
    public final Context mContext;
    public long mCurrentToken;
    public final File mDataDir;
    public boolean mDisableDataExtractionRule;
    public boolean mEnabled;
    public String mEncPassword;
    public int mExtraFlag;
    public final ArrayList mFullBackupQueue;
    public final File mFullBackupScheduleFile;
    public final AnonymousClass1 mFullBackupScheduleWriter;
    public boolean mIsRestoreInProgress;
    public DataChangedJournal mJournal;
    public final File mJournalDir;
    public volatile long mLastBackupPass;
    public final AtomicInteger mNextToken;
    public final LifecycleOperationStorage mOperationStorage;
    public final PackageManager mPackageManager;
    public final IPackageManager mPackageManagerBinder;
    public final AnonymousClass2 mPackageTrackingReceiver;
    public final HashMap mPendingBackups;
    public final ArraySet mPendingInits;
    public final Queue mPendingRestores;
    public PowerManager mPowerManager;
    public final String[] mPrivilegePkgName;
    public final ProcessedPackagesJournal mProcessedPackagesJournal;
    public final Object mQueueLock;
    public final long mRegisterTransportsRequestedTime;
    public final SecureRandom mRng;
    public final PendingIntent mRunInitIntent;
    public final RunInitializeReceiver mRunInitReceiver;
    public PerformFullTransportBackupTask mRunningFullBackupTask;
    public final BackupEligibilityRules mScheduledBackupEligibility;
    public boolean mSepTimeOut;
    public int mSepTimeoutMin;
    public boolean mSepWakeLock;
    public boolean mSetupComplete;
    public final SetupObserver mSetupObserver;
    public String[] mSmartSwitchBackupPath;
    public final File mTokenFile;
    public final Random mTokenGenerator;
    public int mTransportFlagsForAdbBackup;
    public final TransportManager mTransportManager;
    public final int mUserId;
    public final BackupWakeLock mWakelock;

    /* renamed from: com.android.server.backup.UserBackupManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UserBackupManagerService this$0;

        public /* synthetic */ AnonymousClass1(
                int i, UserBackupManagerService userBackupManagerService) {
            this.$r8$classId = i;
            this.this$0 = userBackupManagerService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            UserBackupManagerService userBackupManagerService;
            PerformFullTransportBackupTask performFullTransportBackupTask;
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mQueueLock) {
                        try {
                            ByteArrayOutputStream byteArrayOutputStream =
                                    new ByteArrayOutputStream(4096);
                            DataOutputStream dataOutputStream =
                                    new DataOutputStream(byteArrayOutputStream);
                            dataOutputStream.writeInt(1);
                            int size = this.this$0.mFullBackupQueue.size();
                            dataOutputStream.writeInt(size);
                            for (int i = 0; i < size; i++) {
                                FullBackupEntry fullBackupEntry =
                                        (FullBackupEntry) this.this$0.mFullBackupQueue.get(i);
                                dataOutputStream.writeUTF(fullBackupEntry.packageName);
                                dataOutputStream.writeLong(fullBackupEntry.lastBackup);
                            }
                            dataOutputStream.flush();
                            AtomicFile atomicFile =
                                    new AtomicFile(this.this$0.mFullBackupScheduleFile);
                            FileOutputStream startWrite = atomicFile.startWrite();
                            startWrite.write(byteArrayOutputStream.toByteArray());
                            atomicFile.finishWrite(startWrite);
                        } catch (Exception e) {
                            Slog.e(
                                    "BackupManagerService",
                                    UserBackupManagerService.addUserIdToLogMessage(
                                            this.this$0.mUserId,
                                            "Unable to write backup schedule!"),
                                    e);
                        } finally {
                        }
                    }
                    return;
                default:
                    synchronized (this.this$0.mQueueLock) {
                        userBackupManagerService = this.this$0;
                        performFullTransportBackupTask =
                                userBackupManagerService.mRunningFullBackupTask;
                        if (performFullTransportBackupTask == null) {
                            performFullTransportBackupTask = null;
                        }
                    }
                    if (performFullTransportBackupTask != null) {
                        Slog.i(
                                "BackupManagerService",
                                UserBackupManagerService.addUserIdToLogMessage(
                                        userBackupManagerService.mUserId,
                                        "Telling running backup to stop"));
                        performFullTransportBackupTask.handleCancel(true);
                        return;
                    }
                    return;
            }
        }
    }

    /* renamed from: com.android.server.backup.UserBackupManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        public AnonymousClass2() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String[] strArr;
            boolean z;
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            boolean z2 = true;
            if ("android.intent.action.PACKAGE_ADDED".equals(action)
                    || "android.intent.action.PACKAGE_REMOVED".equals(action)
                    || "android.intent.action.PACKAGE_CHANGED".equals(action)) {
                Uri data = intent.getData();
                if (data == null) {
                    return;
                }
                String schemeSpecificPart = data.getSchemeSpecificPart();
                strArr = schemeSpecificPart != null ? new String[] {schemeSpecificPart} : null;
                if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                    UserBackupManagerService.this.mBackupHandler.post(
                            new UserBackupManagerService$$ExternalSyntheticLambda6(
                                    this,
                                    schemeSpecificPart,
                                    intent.getStringArrayExtra(
                                            "android.intent.extra.changed_component_name_list")));
                    return;
                } else {
                    z2 = "android.intent.action.PACKAGE_ADDED".equals(action);
                    z = extras.getBoolean("android.intent.extra.REPLACING", false);
                }
            } else if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action)) {
                strArr = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                z = false;
            } else {
                strArr =
                        "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)
                                ? intent.getStringArrayExtra(
                                        "android.intent.extra.changed_package_list")
                                : null;
                z = false;
                z2 = false;
            }
            if (strArr == null || strArr.length == 0) {
                return;
            }
            int i = extras.getInt("android.intent.extra.UID");
            if (!z2) {
                if (!z) {
                    synchronized (UserBackupManagerService.this.mBackupParticipants) {
                        UserBackupManagerService.m303$$Nest$mremovePackageParticipantsLocked(
                                UserBackupManagerService.this, strArr, i);
                    }
                }
                for (String str : strArr) {
                    UserBackupManagerService.this.mBackupHandler.post(
                            new UserBackupManagerService$$ExternalSyntheticLambda11(str, 2, this));
                }
                return;
            }
            synchronized (UserBackupManagerService.this.mBackupParticipants) {
                if (z) {
                    try {
                        UserBackupManagerService.m303$$Nest$mremovePackageParticipantsLocked(
                                UserBackupManagerService.this, strArr, i);
                    } finally {
                    }
                }
                UserBackupManagerService userBackupManagerService = UserBackupManagerService.this;
                List allAgentPackages = userBackupManagerService.allAgentPackages();
                for (String str2 : strArr) {
                    userBackupManagerService.addPackageParticipantsLockedInner(
                            str2, allAgentPackages);
                }
            }
            long currentTimeMillis = System.currentTimeMillis();
            for (String str3 : strArr) {
                try {
                    UserBackupManagerService userBackupManagerService2 =
                            UserBackupManagerService.this;
                    PackageInfo packageInfoAsUser =
                            userBackupManagerService2.mPackageManager.getPackageInfoAsUser(
                                    str3, 0, userBackupManagerService2.mUserId);
                    if (UserBackupManagerService.this.mScheduledBackupEligibility.appGetsFullBackup(
                                    packageInfoAsUser)
                            && UserBackupManagerService.this.mScheduledBackupEligibility
                                    .appIsEligibleForBackup(packageInfoAsUser.applicationInfo)) {
                        UserBackupManagerService.this.enqueueFullBackup(currentTimeMillis, str3);
                        UserBackupManagerService.this.scheduleNextFullBackupJob(0L);
                    } else {
                        synchronized (UserBackupManagerService.this.mQueueLock) {
                            UserBackupManagerService.this.dequeueFullBackupLocked(str3);
                        }
                        UserBackupManagerService userBackupManagerService3 =
                                UserBackupManagerService.this;
                        AnonymousClass1 anonymousClass1 =
                                userBackupManagerService3.mFullBackupScheduleWriter;
                        BackupHandler backupHandler = userBackupManagerService3.mBackupHandler;
                        backupHandler.removeCallbacks(anonymousClass1);
                        backupHandler.post(userBackupManagerService3.mFullBackupScheduleWriter);
                    }
                    UserBackupManagerService.this.mBackupHandler.post(
                            new UserBackupManagerService$$ExternalSyntheticLambda11(str3, 1, this));
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.w(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    UserBackupManagerService.this.mUserId,
                                    "Can't resolve new app " + str3));
                }
            }
            UserBackupManagerService.this.dataChangedImpl("@pm@");
        }
    }

    public final class BackupWakeLock {
        public boolean mHasQuit = false;
        public final PowerManager.WakeLock mPowerManagerWakeLock;
        public final int mUserId;

        public BackupWakeLock(PowerManager.WakeLock wakeLock, int i) {
            this.mPowerManagerWakeLock = wakeLock;
            this.mUserId = i;
        }

        public final synchronized void acquire() {
            if (this.mHasQuit) {
                Slog.v(
                        "BackupManagerService",
                        UserBackupManagerService.addUserIdToLogMessage(
                                this.mUserId,
                                "Ignore wakelock acquire after quit: "
                                        + this.mPowerManagerWakeLock.getTag()));
                return;
            }
            this.mPowerManagerWakeLock.acquire();
            Slog.i(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            this.mUserId,
                            "Acquired wakelock:" + this.mPowerManagerWakeLock.getTag()));
        }

        public final synchronized boolean isHeld() {
            return this.mPowerManagerWakeLock.isHeld();
        }

        public final synchronized void quit() {
            while (this.mPowerManagerWakeLock.isHeld()) {
                try {
                    Slog.v(
                            "BackupManagerService",
                            UserBackupManagerService.addUserIdToLogMessage(
                                    this.mUserId,
                                    "Releasing wakelock: " + this.mPowerManagerWakeLock.getTag()));
                    this.mPowerManagerWakeLock.release();
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mHasQuit = true;
        }

        public final synchronized void release() {
            if (this.mHasQuit) {
                Slog.v(
                        "BackupManagerService",
                        UserBackupManagerService.addUserIdToLogMessage(
                                this.mUserId,
                                "Ignore wakelock release after quit: "
                                        + this.mPowerManagerWakeLock.getTag()));
                return;
            }
            if (this.mPowerManagerWakeLock.isHeld()) {
                this.mPowerManagerWakeLock.release();
            }
            Slog.i(
                    "BackupManagerService",
                    UserBackupManagerService.addUserIdToLogMessage(
                            this.mUserId,
                            "Released wakelock:" + this.mPowerManagerWakeLock.getTag()));
        }
    }

    /* renamed from: -$$Nest$mremovePackageParticipantsLocked, reason: not valid java name */
    public static void m303$$Nest$mremovePackageParticipantsLocked(
            UserBackupManagerService userBackupManagerService, String[] strArr, int i) {
        userBackupManagerService.getClass();
        for (String str : strArr) {
            HashSet hashSet = (HashSet) userBackupManagerService.mBackupParticipants.get(i);
            if (hashSet != null && hashSet.contains(str)) {
                if (hashSet.contains(str)) {
                    hashSet.remove(str);
                    userBackupManagerService.mPendingBackups.remove(str);
                }
                if (hashSet.isEmpty()) {
                    userBackupManagerService.mBackupParticipants.remove(i);
                }
            }
        }
    }

    static {
        Boolean bool = Boolean.FALSE;
        mSplitBackupFlag = bool;
        mSplitRestoreFlag = bool;
    }

    public UserBackupManagerService(
            int i,
            Context context,
            BackupManagerService backupManagerService,
            HandlerThread handlerThread,
            File file,
            File file2,
            final TransportManager transportManager) {
        this.mPendingInits = new ArraySet();
        SparseArray sparseArray = new SparseArray();
        this.mBackupParticipants = sparseArray;
        this.mPendingBackups = new HashMap();
        this.mQueueLock = new Object();
        this.mAgentConnectLock = new Object();
        this.mClearDataLock = new Object();
        this.mBackupRestoreLock = new Object();
        this.mEncPassword = "";
        this.mExtraFlag = 0;
        this.mSepWakeLock = false;
        this.mSepTimeOut = false;
        this.mSepTimeoutMin = 30;
        this.mTransportFlagsForAdbBackup = 0;
        this.mPrivilegePkgName =
                new String[] {
                    "com.sec.android.easyMover",
                    "com.sec.android.Kies",
                    "com.samsung.android.da.daagent",
                    "com.samsung.memorysaver",
                    "com.samsung.knox.bnr",
                    "com.samsung.knox.securefolder",
                    "com.samsung.android.se.unit",
                    "com.samsung.android.game.gamehome"
                };
        this.mAdbBackupRestoreConfirmations = new SparseArray();
        SecureRandom secureRandom = new SecureRandom();
        this.mRng = secureRandom;
        this.mPendingRestores = new ArrayDeque();
        this.mTokenGenerator = new Random();
        this.mNextToken = new AtomicInteger();
        this.mAncestralPackages = null;
        this.mAncestralToken = 0L;
        this.mCurrentToken = 0L;
        this.mDisableDataExtractionRule = false;
        this.mFullBackupScheduleWriter = new AnonymousClass1(0, this);
        this.mPackageTrackingReceiver = new AnonymousClass2();
        this.mUserId = i;
        Objects.requireNonNull(context, "context cannot be null");
        this.mContext = context;
        PackageManager packageManager = context.getPackageManager();
        this.mPackageManager = packageManager;
        this.mPackageManagerBinder = AppGlobals.getPackageManager();
        this.mActivityManager = ActivityManager.getService();
        this.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mScheduledBackupEligibility =
                new BackupEligibilityRules(
                        packageManager,
                        (PackageManagerInternal)
                                LocalServices.getService(PackageManagerInternal.class),
                        i,
                        context,
                        0,
                        false);
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        Objects.requireNonNull(backupManagerService, "parent cannot be null");
        this.mBackupManagerBinder =
                IBackupManager.Stub.asInterface(backupManagerService.asBinder());
        BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                new BackupAgentTimeoutParameters(Handler.getMain(), context.getContentResolver());
        this.mAgentTimeoutParameters = backupAgentTimeoutParameters;
        backupAgentTimeoutParameters.start();
        LifecycleOperationStorage lifecycleOperationStorage = new LifecycleOperationStorage(i);
        this.mOperationStorage = lifecycleOperationStorage;
        Objects.requireNonNull(handlerThread, "userBackupThread cannot be null");
        BackupHandler backupHandler =
                new BackupHandler(this, lifecycleOperationStorage, handlerThread);
        this.mBackupHandler = backupHandler;
        ContentResolver contentResolver = context.getContentResolver();
        this.mSetupComplete =
                Settings.Secure.getIntForUser(
                                context.getContentResolver(), "user_setup_complete", 0, i)
                        != 0;
        this.mAutoRestore =
                Settings.Secure.getIntForUser(contentResolver, "backup_auto_restore", 1, i) != 0;
        SetupObserver setupObserver = new SetupObserver(this, backupHandler);
        this.mSetupObserver = setupObserver;
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("user_setup_complete"), false, setupObserver, i);
        Objects.requireNonNull(file, "baseStateDir cannot be null");
        this.mBaseStateDir = file;
        if (i == 0) {
            file.mkdirs();
            if (!SELinux.restorecon(file)) {
                Slog.w(
                        "BackupManagerService",
                        addUserIdToLogMessage(i, "SELinux restorecon failed on " + file));
            }
        }
        Objects.requireNonNull(file2, "dataDir cannot be null");
        this.mDataDir = file2;
        this.mBackupPasswordManager = new BackupPasswordManager(context, file, secureRandom);
        RunInitializeReceiver runInitializeReceiver = new RunInitializeReceiver(this);
        this.mRunInitReceiver = runInitializeReceiver;
        context.registerReceiverAsUser(
                runInitializeReceiver,
                UserHandle.of(i),
                BatteryService$$ExternalSyntheticOutline0.m("android.app.backup.intent.INIT"),
                "android.permission.BACKUP",
                null);
        this.mRunInitIntent =
                PendingIntent.getBroadcastAsUser(
                        context,
                        0,
                        BatteryService$$ExternalSyntheticOutline0.m(
                                1073741824, "android.app.backup.intent.INIT"),
                        67108864,
                        UserHandle.of(i));
        File file3 = new File(file, "pending");
        this.mJournalDir = file3;
        file3.mkdirs();
        this.mJournal = null;
        BackupManagerConstants backupManagerConstants =
                new BackupManagerConstants(
                        backupHandler,
                        context.getContentResolver(),
                        Settings.Secure.getUriFor("backup_manager_constants"));
        this.mConstants = backupManagerConstants;
        backupManagerConstants.start();
        synchronized (sparseArray) {
            addPackageParticipantsLockedInner(null, allAgentPackages());
        }
        Objects.requireNonNull(transportManager, "transportManager cannot be null");
        this.mTransportManager = transportManager;
        transportManager.mOnTransportRegisteredListener =
                new OnTransportRegisteredListener() { // from class:
                                                      // com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda1
                    @Override // com.android.server.backup.transport.OnTransportRegisteredListener
                    public final void onTransportRegistered(String str, String str2) {
                        UserBackupManagerService userBackupManagerService =
                                UserBackupManagerService.this;
                        userBackupManagerService.getClass();
                        long elapsedRealtime =
                                SystemClock.elapsedRealtime()
                                        - userBackupManagerService.mRegisterTransportsRequestedTime;
                        Slog.d(
                                "BackupManagerService",
                                UserBackupManagerService.addUserIdToLogMessage(
                                        userBackupManagerService.mUserId,
                                        "Transport "
                                                + str
                                                + " registered "
                                                + elapsedRealtime
                                                + "ms after first request (delay = 3000ms)"));
                        File file4 = new File(userBackupManagerService.mBaseStateDir, str2);
                        file4.mkdirs();
                        if (new File(file4, "_need_init_").exists()) {
                            synchronized (userBackupManagerService.mQueueLock) {
                                userBackupManagerService.mPendingInits.add(str);
                                userBackupManagerService.mAlarmManager.set(
                                        0,
                                        System.currentTimeMillis() + 60000,
                                        userBackupManagerService.mRunInitIntent);
                            }
                        }
                    }
                };
        this.mRegisterTransportsRequestedTime = SystemClock.elapsedRealtime();
        final int i2 = 0;
        backupHandler.postDelayed(
                new Runnable() { // from class:
                    // com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i3 = i2;
                        Object obj = transportManager;
                        switch (i3) {
                            case 0:
                                TransportManager transportManager2 = (TransportManager) obj;
                                transportManager2.registerTransportsForIntent(
                                        transportManager2.mTransportServiceIntent,
                                        new TransportManager$$ExternalSyntheticLambda1());
                                break;
                            case 1:
                                UserBackupManagerService userBackupManagerService =
                                        (UserBackupManagerService) obj;
                                File file4 = userBackupManagerService.mJournalDir;
                                ArrayList arrayList = new ArrayList();
                                File[] listFiles = file4.listFiles();
                                if (listFiles == null) {
                                    Slog.w("DataChangedJournal", "Failed to read journal files");
                                } else {
                                    for (File file5 : listFiles) {
                                        arrayList.add(new DataChangedJournal(file5));
                                    }
                                }
                                arrayList.removeAll(
                                        Collections.singletonList(
                                                userBackupManagerService.mJournal));
                                boolean isEmpty = arrayList.isEmpty();
                                int i4 = userBackupManagerService.mUserId;
                                if (!isEmpty) {
                                    Slog.i(
                                            "BackupManagerService",
                                            UserBackupManagerService.addUserIdToLogMessage(
                                                    i4,
                                                    "Found "
                                                            + arrayList.size()
                                                            + " stale backup journal(s),"
                                                            + " scheduling."));
                                }
                                LinkedHashSet linkedHashSet = new LinkedHashSet();
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    DataChangedJournal dataChangedJournal =
                                            (DataChangedJournal) it.next();
                                    try {
                                        dataChangedJournal.forEach(
                                                new UserBackupManagerService$$ExternalSyntheticLambda13(
                                                        userBackupManagerService, linkedHashSet));
                                    } catch (IOException e) {
                                        Slog.e(
                                                "BackupManagerService",
                                                UserBackupManagerService.addUserIdToLogMessage(
                                                        i4, "Can't read " + dataChangedJournal),
                                                e);
                                    }
                                }
                                if (!linkedHashSet.isEmpty()) {
                                    Slog.i(
                                            "BackupManagerService",
                                            UserBackupManagerService.addUserIdToLogMessage(
                                                    i4,
                                                    "Stale backup journals: Scheduled "
                                                            + linkedHashSet.size()
                                                            + " package(s) total"));
                                    break;
                                }
                                break;
                            default:
                                ((BackupManagerMonitorDumpsysUtils) obj).deleteExpiredBMMEvents();
                                break;
                        }
                    }
                },
                3000L);
        final int i3 = 1;
        backupHandler.postDelayed(
                new Runnable() { // from class:
                    // com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i32 = i3;
                        Object obj = this;
                        switch (i32) {
                            case 0:
                                TransportManager transportManager2 = (TransportManager) obj;
                                transportManager2.registerTransportsForIntent(
                                        transportManager2.mTransportServiceIntent,
                                        new TransportManager$$ExternalSyntheticLambda1());
                                break;
                            case 1:
                                UserBackupManagerService userBackupManagerService =
                                        (UserBackupManagerService) obj;
                                File file4 = userBackupManagerService.mJournalDir;
                                ArrayList arrayList = new ArrayList();
                                File[] listFiles = file4.listFiles();
                                if (listFiles == null) {
                                    Slog.w("DataChangedJournal", "Failed to read journal files");
                                } else {
                                    for (File file5 : listFiles) {
                                        arrayList.add(new DataChangedJournal(file5));
                                    }
                                }
                                arrayList.removeAll(
                                        Collections.singletonList(
                                                userBackupManagerService.mJournal));
                                boolean isEmpty = arrayList.isEmpty();
                                int i4 = userBackupManagerService.mUserId;
                                if (!isEmpty) {
                                    Slog.i(
                                            "BackupManagerService",
                                            UserBackupManagerService.addUserIdToLogMessage(
                                                    i4,
                                                    "Found "
                                                            + arrayList.size()
                                                            + " stale backup journal(s),"
                                                            + " scheduling."));
                                }
                                LinkedHashSet linkedHashSet = new LinkedHashSet();
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    DataChangedJournal dataChangedJournal =
                                            (DataChangedJournal) it.next();
                                    try {
                                        dataChangedJournal.forEach(
                                                new UserBackupManagerService$$ExternalSyntheticLambda13(
                                                        userBackupManagerService, linkedHashSet));
                                    } catch (IOException e) {
                                        Slog.e(
                                                "BackupManagerService",
                                                UserBackupManagerService.addUserIdToLogMessage(
                                                        i4, "Can't read " + dataChangedJournal),
                                                e);
                                    }
                                }
                                if (!linkedHashSet.isEmpty()) {
                                    Slog.i(
                                            "BackupManagerService",
                                            UserBackupManagerService.addUserIdToLogMessage(
                                                    i4,
                                                    "Stale backup journals: Scheduled "
                                                            + linkedHashSet.size()
                                                            + " package(s) total"));
                                    break;
                                }
                                break;
                            default:
                                ((BackupManagerMonitorDumpsysUtils) obj).deleteExpiredBMMEvents();
                                break;
                        }
                    }
                },
                3000L);
        final BackupManagerMonitorDumpsysUtils backupManagerMonitorDumpsysUtils =
                new BackupManagerMonitorDumpsysUtils();
        final int i4 = 2;
        backupHandler.postDelayed(
                new Runnable() { // from class:
                    // com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i32 = i4;
                        Object obj = backupManagerMonitorDumpsysUtils;
                        switch (i32) {
                            case 0:
                                TransportManager transportManager2 = (TransportManager) obj;
                                transportManager2.registerTransportsForIntent(
                                        transportManager2.mTransportServiceIntent,
                                        new TransportManager$$ExternalSyntheticLambda1());
                                break;
                            case 1:
                                UserBackupManagerService userBackupManagerService =
                                        (UserBackupManagerService) obj;
                                File file4 = userBackupManagerService.mJournalDir;
                                ArrayList arrayList = new ArrayList();
                                File[] listFiles = file4.listFiles();
                                if (listFiles == null) {
                                    Slog.w("DataChangedJournal", "Failed to read journal files");
                                } else {
                                    for (File file5 : listFiles) {
                                        arrayList.add(new DataChangedJournal(file5));
                                    }
                                }
                                arrayList.removeAll(
                                        Collections.singletonList(
                                                userBackupManagerService.mJournal));
                                boolean isEmpty = arrayList.isEmpty();
                                int i42 = userBackupManagerService.mUserId;
                                if (!isEmpty) {
                                    Slog.i(
                                            "BackupManagerService",
                                            UserBackupManagerService.addUserIdToLogMessage(
                                                    i42,
                                                    "Found "
                                                            + arrayList.size()
                                                            + " stale backup journal(s),"
                                                            + " scheduling."));
                                }
                                LinkedHashSet linkedHashSet = new LinkedHashSet();
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    DataChangedJournal dataChangedJournal =
                                            (DataChangedJournal) it.next();
                                    try {
                                        dataChangedJournal.forEach(
                                                new UserBackupManagerService$$ExternalSyntheticLambda13(
                                                        userBackupManagerService, linkedHashSet));
                                    } catch (IOException e) {
                                        Slog.e(
                                                "BackupManagerService",
                                                UserBackupManagerService.addUserIdToLogMessage(
                                                        i42, "Can't read " + dataChangedJournal),
                                                e);
                                    }
                                }
                                if (!linkedHashSet.isEmpty()) {
                                    Slog.i(
                                            "BackupManagerService",
                                            UserBackupManagerService.addUserIdToLogMessage(
                                                    i42,
                                                    "Stale backup journals: Scheduled "
                                                            + linkedHashSet.size()
                                                            + " package(s) total"));
                                    break;
                                }
                                break;
                            default:
                                ((BackupManagerMonitorDumpsysUtils) obj).deleteExpiredBMMEvents();
                                break;
                        }
                    }
                },
                3000L);
        this.mBackupPreferences = new UserBackupPreferences(context, file);
        PowerManager powerManager = this.mPowerManager;
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "*backup*-", PackageManagerShellCommandDataLoader.STDIN_PATH);
        m.append(handlerThread.getThreadId());
        this.mWakelock = new BackupWakeLock(powerManager.newWakeLock(1, m.toString()), i);
        if (isYuvaSupported()) {
            Slog.d("BackupManagerService", "Backup Manager Yuva is Supported");
            mBackupManagerYuva = BackupManagerYuva.getInstanceBackupYuva();
        }
        this.mFullBackupScheduleFile = new File(file, "fb-schedule");
        this.mTokenFile = new File(file, "ancestral");
        try {
            DataInputStream dataInputStream =
                    new DataInputStream(
                            new BufferedInputStream(new FileInputStream(this.mTokenFile)));
            try {
                if (dataInputStream.readInt() == 1) {
                    this.mAncestralToken = dataInputStream.readLong();
                    this.mCurrentToken = dataInputStream.readLong();
                    int readInt = dataInputStream.readInt();
                    if (readInt >= 0) {
                        this.mAncestralPackages = new HashSet();
                        for (int i5 = 0; i5 < readInt; i5++) {
                            this.mAncestralPackages.add(dataInputStream.readUTF());
                        }
                    }
                }
                dataInputStream.close();
            } finally {
            }
        } catch (FileNotFoundException unused) {
            Slog.v(
                    "BackupManagerService",
                    addUserIdToLogMessage(this.mUserId, "No ancestral data"));
        } catch (IOException e) {
            Slog.w(
                    "BackupManagerService",
                    addUserIdToLogMessage(this.mUserId, "Unable to read token file"),
                    e);
        }
        ProcessedPackagesJournal processedPackagesJournal =
                new ProcessedPackagesJournal(this.mBaseStateDir);
        this.mProcessedPackagesJournal = processedPackagesJournal;
        synchronized (processedPackagesJournal.mProcessedPackages) {
            processedPackagesJournal.loadFromDisk();
        }
        synchronized (this.mQueueLock) {
            this.mFullBackupQueue = readFullBackupSchedule();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(
                this.mPackageTrackingReceiver,
                UserHandle.of(this.mUserId),
                intentFilter,
                null,
                null);
        this.mContext.registerReceiverAsUser(
                this.mPackageTrackingReceiver,
                UserHandle.of(this.mUserId),
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE",
                        "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE"),
                null,
                null);
    }

    public UserBackupManagerService(
            Context context,
            PackageManager packageManager,
            LifecycleOperationStorage lifecycleOperationStorage,
            TransportManager transportManager,
            BackupHandler backupHandler,
            BackupManagerConstants backupManagerConstants) {
        this.mPendingInits = new ArraySet();
        this.mBackupParticipants = new SparseArray();
        this.mPendingBackups = new HashMap();
        this.mQueueLock = new Object();
        this.mAgentConnectLock = new Object();
        this.mClearDataLock = new Object();
        this.mBackupRestoreLock = new Object();
        this.mEncPassword = "";
        this.mExtraFlag = 0;
        this.mSepWakeLock = false;
        this.mSepTimeOut = false;
        this.mSepTimeoutMin = 30;
        this.mTransportFlagsForAdbBackup = 0;
        this.mPrivilegePkgName =
                new String[] {
                    "com.sec.android.easyMover",
                    "com.sec.android.Kies",
                    "com.samsung.android.da.daagent",
                    "com.samsung.memorysaver",
                    "com.samsung.knox.bnr",
                    "com.samsung.knox.securefolder",
                    "com.samsung.android.se.unit",
                    "com.samsung.android.game.gamehome"
                };
        this.mAdbBackupRestoreConfirmations = new SparseArray();
        this.mRng = new SecureRandom();
        this.mPendingRestores = new ArrayDeque();
        this.mTokenGenerator = new Random();
        this.mNextToken = new AtomicInteger();
        this.mAncestralPackages = null;
        this.mAncestralToken = 0L;
        this.mCurrentToken = 0L;
        this.mDisableDataExtractionRule = false;
        this.mFullBackupScheduleWriter = new AnonymousClass1(0, this);
        this.mPackageTrackingReceiver = new AnonymousClass2();
        this.mContext = context;
        this.mUserId = 0;
        this.mRegisterTransportsRequestedTime = 0L;
        this.mPackageManager = packageManager;
        this.mOperationStorage = lifecycleOperationStorage;
        this.mTransportManager = transportManager;
        this.mFullBackupQueue = new ArrayList();
        this.mBackupHandler = backupHandler;
        this.mConstants = backupManagerConstants;
        this.mBaseStateDir = null;
        this.mDataDir = null;
        this.mJournalDir = null;
        this.mFullBackupScheduleFile = null;
        this.mSetupObserver = null;
        this.mRunInitReceiver = null;
        this.mRunInitIntent = null;
        this.mAgentTimeoutParameters = null;
        this.mActivityManagerInternal = null;
        this.mAlarmManager = null;
        this.mWakelock = null;
        this.mBackupPreferences = null;
        this.mBackupPasswordManager = null;
        this.mPackageManagerBinder = null;
        this.mActivityManager = null;
        this.mBackupManagerBinder = null;
        this.mScheduledBackupEligibility = null;
    }

    public static String addUserIdToLogMessage(int i, String str) {
        return AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "[UserID:", "] ", str);
    }

    public static UserBackupManagerService createAndInitializeService(
            int i,
            Context context,
            BackupManagerService backupManagerService,
            HandlerThread handlerThread,
            File file,
            File file2,
            TransportManager transportManager) {
        if (new BackupManagerMonitorDumpsysUtils().deleteExpiredBMMEvents()) {
            Slog.d("BackupManagerService", "BMM Events recorded for dumpsys have expired");
        }
        return new UserBackupManagerService(
                i, context, backupManagerService, handlerThread, file, file2, transportManager);
    }

    public static void dumpBMMEvents(PrintWriter printWriter) {
        BackupManagerMonitorDumpsysUtils backupManagerMonitorDumpsysUtils =
                new BackupManagerMonitorDumpsysUtils();
        if (backupManagerMonitorDumpsysUtils.deleteExpiredBMMEvents()) {
            printWriter.println("BACKUP MANAGER MONITOR EVENTS HAVE EXPIRED");
            return;
        }
        File bMMEventsFile = BackupManagerMonitorDumpsysUtils.getBMMEventsFile();
        if (bMMEventsFile.length() == 0) {
            printWriter.println("NO BACKUP MANAGER MONITOR EVENTS");
            return;
        }
        if (!backupManagerMonitorDumpsysUtils.mIsFileLargerThanSizeLimit) {
            backupManagerMonitorDumpsysUtils.mIsFileLargerThanSizeLimit =
                    bMMEventsFile.length()
                            > backupManagerMonitorDumpsysUtils.getBMMEventsFileSizeLimit();
        }
        if (backupManagerMonitorDumpsysUtils.mIsFileLargerThanSizeLimit) {
            printWriter.println(
                    "BACKUP MANAGER MONITOR EVENTS FILE OVER SIZE LIMIT - future events will not be"
                        + " recorded");
        }
        printWriter.println("START OF BACKUP MANAGER MONITOR EVENTS");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(bMMEventsFile));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else {
                        printWriter.println(readLine);
                    }
                } finally {
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            Slog.e("BackupManagerService", "IO Exception when reading BMM events from file: " + e);
            printWriter.println("IO Exception when reading BMM events from file");
        }
        printWriter.println("END OF BACKUP MANAGER MONITOR EVENTS");
    }

    public static void signalAdbBackupRestoreCompletion(AdbParams adbParams) {
        synchronized (adbParams.latch) {
            adbParams.latch.set(true);
            adbParams.latch.notifyAll();
        }
    }

    public static void waitForCompletion(AdbParams adbParams) {
        synchronized (adbParams.latch) {
            while (!adbParams.latch.get()) {
                try {
                    adbParams.latch.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public final void acknowledgeAdbBackupOrRestore(
            int i,
            boolean z,
            String str,
            String str2,
            IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        AdbParams adbParams;
        Slog.d(
                "BackupManagerService",
                addUserIdToLogMessage(
                        this.mUserId,
                        "acknowledgeAdbBackupOrRestore : token=" + i + " allow=" + z));
        synchronized (this.mAdbBackupRestoreConfirmations) {
            adbParams = (AdbParams) this.mAdbBackupRestoreConfirmations.get(i);
        }
        if (adbParams == null || !adbParams.privilegeApp) {
            this.mContext.enforceCallingPermission(
                    "android.permission.BACKUP", "acknowledgeAdbBackupOrRestore");
        } else {
            this.mContext.enforceCallingOrSelfPermission(
                    "android.permission.BACKUP", "acknowledgeAdbBackupOrRestore");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mAdbBackupRestoreConfirmations) {
                try {
                    AdbParams adbParams2 = (AdbParams) this.mAdbBackupRestoreConfirmations.get(i);
                    if (adbParams2 != null) {
                        this.mBackupHandler.removeMessages(9, adbParams2);
                        this.mAdbBackupRestoreConfirmations.delete(i);
                        if (z) {
                            int i2 = adbParams2 instanceof AdbBackupParams ? 2 : 10;
                            adbParams2.observer = iFullBackupRestoreObserver;
                            adbParams2.curPassword = str;
                            adbParams2.encryptPassword = str2;
                            if (this.mWakelock.isHeld() && this.mSepWakeLock) {
                                this.mWakelock.release();
                            }
                            this.mSepWakeLock = true;
                            this.mWakelock.acquire();
                            this.mBackupHandler.sendMessage(
                                    this.mBackupHandler.obtainMessage(i2, adbParams2));
                        } else {
                            Slog.w(
                                    "BackupManagerService",
                                    addUserIdToLogMessage(
                                            this.mUserId,
                                            "User rejected full backup/restore operation"));
                            signalAdbBackupRestoreCompletion(adbParams2);
                        }
                    } else {
                        Slog.w(
                                "BackupManagerService",
                                addUserIdToLogMessage(
                                        this.mUserId,
                                        "Attempted to ack full backup/restore with invalid token"));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void adbBackup(
            ParcelFileDescriptor parcelFileDescriptor,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6,
            boolean z7,
            boolean z8,
            String[] strArr,
            boolean z9) {
        long j;
        Throwable th;
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "adbBackup");
        if (UserHandle.getCallingUserId() != 0) {
            throw new IllegalStateException("Backup supported only for the device owner");
        }
        if (!z5 && !z3 && (strArr == null || strArr.length == 0)) {
            throw new IllegalArgumentException(
                    "Backup requested but neither shared nor any apps named");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if (this.mSetupComplete) {
                    Slog.v(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId,
                                    "Requesting backup: apks="
                                            + z
                                            + " obb="
                                            + z2
                                            + " shared="
                                            + z3
                                            + " all="
                                            + z5
                                            + " system="
                                            + z6
                                            + " includekeyvalue="
                                            + z8
                                            + " pkgs="
                                            + Arrays.toString(strArr)));
                    Slog.i(
                            "BackupManagerService",
                            addUserIdToLogMessage(this.mUserId, "Beginning adb backup..."));
                    BackupEligibilityRules eligibilityRulesForOperation =
                            z9
                                    ? getEligibilityRulesForOperation(2)
                                    : getEligibilityRulesForOperation(1);
                    int i = this.mExtraFlag;
                    String[] strArr2 = this.mSmartSwitchBackupPath;
                    AdbBackupParams adbBackupParams = new AdbBackupParams();
                    j = clearCallingIdentity;
                    try {
                        adbBackupParams.fd = parcelFileDescriptor;
                        adbBackupParams.includeApks = z;
                        adbBackupParams.includeObbs = z2;
                        adbBackupParams.includeShared = z3;
                        adbBackupParams.doWidgets = z4;
                        adbBackupParams.allApps = z5;
                        adbBackupParams.includeSystem = z6;
                        adbBackupParams.doCompress = z7;
                        adbBackupParams.includeKeyValue = z8;
                        adbBackupParams.packages = strArr;
                        adbBackupParams.backupEligibilityRules = eligibilityRulesForOperation;
                        adbBackupParams.extraFlag = i;
                        adbBackupParams.smartswitchBackupPath = strArr2;
                        adbBackupParams.transportFlags = this.mTransportFlagsForAdbBackup;
                        this.mAdbBackupParams = adbBackupParams;
                        this.mTransportFlagsForAdbBackup = 0;
                        adbBackupParams.privilegeApp = isPrivilegeBackupApp(callingUid);
                        int generateRandomIntegerToken = generateRandomIntegerToken();
                        synchronized (this.mAdbBackupRestoreConfirmations) {
                            this.mAdbBackupRestoreConfirmations.put(
                                    generateRandomIntegerToken, adbBackupParams);
                        }
                        if (adbBackupParams.privilegeApp) {
                            acknowledgeAdbBackupOrRestore(
                                    generateRandomIntegerToken, true, "", this.mEncPassword, null);
                        } else {
                            Slog.d(
                                    "BackupManagerService",
                                    addUserIdToLogMessage(
                                            this.mUserId,
                                            "Starting backup confirmation UI, token="
                                                    + generateRandomIntegerToken));
                            if (startConfirmationUi(generateRandomIntegerToken, "fullback")) {
                                this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                                BackupHandler backupHandler = this.mBackupHandler;
                                backupHandler.sendMessageDelayed(
                                        backupHandler.obtainMessage(
                                                9, generateRandomIntegerToken, 0, adbBackupParams),
                                        60000L);
                            } else {
                                Slog.e(
                                        "BackupManagerService",
                                        addUserIdToLogMessage(
                                                this.mUserId,
                                                "Unable to launch backup confirmation UI"));
                                this.mAdbBackupRestoreConfirmations.delete(
                                        generateRandomIntegerToken);
                                try {
                                    parcelFileDescriptor.close();
                                } catch (IOException e) {
                                    Slog.e(
                                            "BackupManagerService",
                                            addUserIdToLogMessage(
                                                    this.mUserId,
                                                    "IO error closing output for adb backup: "
                                                            + e.getMessage()));
                                }
                                Binder.restoreCallingIdentity(j);
                                Slog.d(
                                        "BackupManagerService",
                                        addUserIdToLogMessage(
                                                this.mUserId, "Adb backup processing complete."));
                                this.mSmartSwitchBackupPath = null;
                                this.mSepTimeoutMin = 30;
                            }
                        }
                        Slog.d(
                                "BackupManagerService",
                                addUserIdToLogMessage(
                                        this.mUserId, "Waiting for backup completion..."));
                        startSepTimeout(adbBackupParams);
                        waitForCompletion(adbBackupParams);
                        this.mBackupHandler.removeMessages(101, adbBackupParams);
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException e2) {
                            Slog.e(
                                    "BackupManagerService",
                                    addUserIdToLogMessage(
                                            this.mUserId,
                                            "IO error closing output for adb backup: "
                                                    + e2.getMessage()));
                        }
                        Binder.restoreCallingIdentity(j);
                        Slog.d(
                                "BackupManagerService",
                                addUserIdToLogMessage(
                                        this.mUserId, "Adb backup processing complete."));
                        this.mSmartSwitchBackupPath = null;
                        this.mSepTimeoutMin = 30;
                        this.mSepTimeOut = false;
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        th = th;
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException e3) {
                            Slog.e(
                                    "BackupManagerService",
                                    addUserIdToLogMessage(
                                            this.mUserId,
                                            "IO error closing output for adb backup: "
                                                    + e3.getMessage()));
                        }
                        Binder.restoreCallingIdentity(j);
                        Slog.d(
                                "BackupManagerService",
                                addUserIdToLogMessage(
                                        this.mUserId, "Adb backup processing complete."));
                        this.mSmartSwitchBackupPath = null;
                        this.mSepTimeoutMin = 30;
                        this.mSepTimeOut = false;
                        throw th;
                    }
                }
                Slog.i(
                        "BackupManagerService",
                        addUserIdToLogMessage(this.mUserId, "Backup not supported before setup"));
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e4) {
                    Slog.e(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId,
                                    "IO error closing output for adb backup: " + e4.getMessage()));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                Slog.d(
                        "BackupManagerService",
                        addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                this.mSmartSwitchBackupPath = null;
                this.mSepTimeoutMin = 30;
                this.mSepTimeOut = false;
            } catch (Throwable th3) {
                th = th3;
                j = clearCallingIdentity;
                parcelFileDescriptor.close();
                Binder.restoreCallingIdentity(j);
                Slog.d(
                        "BackupManagerService",
                        addUserIdToLogMessage(this.mUserId, "Adb backup processing complete."));
                this.mSmartSwitchBackupPath = null;
                this.mSepTimeoutMin = 30;
                this.mSepTimeOut = false;
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            j = clearCallingIdentity;
        }
    }

    public final void adbRestore(ParcelFileDescriptor parcelFileDescriptor, boolean z) {
        this.mContext.enforceCallingPermission("android.permission.BACKUP", "adbRestore");
        if (UserHandle.getCallingUserId() != 0) {
            throw new IllegalStateException("Restore supported only for the device owner");
        }
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!this.mSetupComplete) {
                Slog.i(
                        "BackupManagerService",
                        addUserIdToLogMessage(
                                this.mUserId, "Full restore not permitted before setup"));
                return;
            }
            Slog.i(
                    "BackupManagerService",
                    addUserIdToLogMessage(this.mUserId, "Beginning restore..."));
            AdbRestoreParams adbRestoreParams = new AdbRestoreParams();
            adbRestoreParams.fd = parcelFileDescriptor;
            this.mAdbRestoreParams = adbRestoreParams;
            adbRestoreParams.privilegeApp = isPrivilegeBackupApp(callingUid);
            if (z) {
                adbRestoreParams.typeMigration = false;
            } else {
                adbRestoreParams.typeMigration = true;
            }
            Slog.i(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            this.mUserId,
                            "params.typeMigration : " + adbRestoreParams.typeMigration));
            int generateRandomIntegerToken = generateRandomIntegerToken();
            synchronized (this.mAdbBackupRestoreConfirmations) {
                this.mAdbBackupRestoreConfirmations.put(
                        generateRandomIntegerToken, adbRestoreParams);
            }
            if (adbRestoreParams.privilegeApp) {
                acknowledgeAdbBackupOrRestore(
                        generateRandomIntegerToken, true, "", this.mEncPassword, null);
            } else {
                Slog.d(
                        "BackupManagerService",
                        addUserIdToLogMessage(
                                this.mUserId,
                                "Starting restore confirmation UI, token="
                                        + generateRandomIntegerToken));
                if (!startConfirmationUi(generateRandomIntegerToken, "fullrest")) {
                    Slog.e(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId, "Unable to launch restore confirmation"));
                    this.mAdbBackupRestoreConfirmations.delete(generateRandomIntegerToken);
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e) {
                        Slog.w(
                                "BackupManagerService",
                                addUserIdToLogMessage(
                                        this.mUserId,
                                        "Error trying to close fd after adb restore: " + e));
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    Slog.i(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId, "adb restore processing complete."));
                    return;
                }
                this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
                BackupHandler backupHandler = this.mBackupHandler;
                backupHandler.sendMessageDelayed(
                        backupHandler.obtainMessage(
                                9, generateRandomIntegerToken, 0, adbRestoreParams),
                        60000L);
            }
            Slog.d(
                    "BackupManagerService",
                    addUserIdToLogMessage(this.mUserId, "Waiting for restore completion..."));
            startSepTimeout(adbRestoreParams);
            waitForCompletion(adbRestoreParams);
            this.mBackupHandler.removeMessages(101, adbRestoreParams);
            try {
                parcelFileDescriptor.close();
            } catch (IOException e2) {
                Slog.w(
                        "BackupManagerService",
                        addUserIdToLogMessage(
                                this.mUserId, "Error trying to close fd after adb restore: " + e2));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Slog.i(
                    "BackupManagerService",
                    addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
            this.mSepTimeoutMin = 30;
            this.mSepTimeOut = false;
        } finally {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e3) {
                Slog.w(
                        "BackupManagerService",
                        addUserIdToLogMessage(
                                this.mUserId, "Error trying to close fd after adb restore: " + e3));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Slog.i(
                    "BackupManagerService",
                    addUserIdToLogMessage(this.mUserId, "adb restore processing complete."));
        }
    }

    public final void addPackageParticipantsLockedInner(String str, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (str == null || packageInfo.packageName.equals(str)) {
                int i = packageInfo.applicationInfo.uid;
                HashSet hashSet = (HashSet) this.mBackupParticipants.get(i);
                if (hashSet == null) {
                    hashSet = new HashSet();
                    this.mBackupParticipants.put(i, hashSet);
                }
                hashSet.add(packageInfo.packageName);
                String str2 = packageInfo.packageName;
                BackupHandler backupHandler = this.mBackupHandler;
                backupHandler.sendMessage(backupHandler.obtainMessage(16, str2));
            }
        }
    }

    public final List allAgentPackages() {
        ApplicationInfo applicationInfo;
        int i;
        PackageManager packageManager = this.mPackageManager;
        int i2 = this.mUserId;
        List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(134217728, i2);
        for (int size = installedPackagesAsUser.size() - 1; size >= 0; size--) {
            PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(size);
            try {
                applicationInfo = packageInfo.applicationInfo;
                i = applicationInfo.flags;
            } catch (PackageManager.NameNotFoundException unused) {
                installedPackagesAsUser.remove(size);
            }
            if ((32768 & i) != 0
                    && applicationInfo.backupAgentName != null
                    && (67108864 & i) == 0) {
                ApplicationInfo applicationInfoAsUser =
                        this.mPackageManager.getApplicationInfoAsUser(
                                packageInfo.packageName, 1024, i2);
                ApplicationInfo applicationInfo2 = packageInfo.applicationInfo;
                applicationInfo2.sharedLibraryFiles = applicationInfoAsUser.sharedLibraryFiles;
                applicationInfo2.sharedLibraryInfos = applicationInfoAsUser.sharedLibraryInfos;
            }
            installedPackagesAsUser.remove(size);
        }
        return installedPackagesAsUser;
    }

    public final void backupNow() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.BACKUP", "backupNow");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!this.mPowerManager.getPowerSaveState(5).batterySaverEnabled) {
                Slog.v(
                        "BackupManagerService",
                        addUserIdToLogMessage(this.mUserId, "Scheduling immediate backup pass"));
                synchronized (this.mQueueLock) {
                    if (this.mPendingInits.size() > 0) {
                        try {
                            this.mAlarmManager.cancel(this.mRunInitIntent);
                            this.mRunInitIntent.send();
                        } catch (PendingIntent.CanceledException unused) {
                            Slog.w(
                                    "BackupManagerService",
                                    addUserIdToLogMessage(
                                            this.mUserId, "Run init intent cancelled"));
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    if (this.mEnabled && this.mSetupComplete) {
                        this.mBackupHandler.sendMessage(this.mBackupHandler.obtainMessage(1));
                        KeyValueBackupJob.cancel(this.mContext, this.mUserId);
                    }
                    Slog.w(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId,
                                    "Backup pass but enabled="
                                            + this.mEnabled
                                            + " setupComplete="
                                            + this.mSetupComplete));
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return;
                }
            }
            Slog.v(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            this.mUserId, "Not running backup while in battery save mode"));
            KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final IBackupAgent bindToAgentSynchronous(
            ApplicationInfo applicationInfo, int i, int i2) {
        IBackupAgent iBackupAgent;
        synchronized (this.mAgentConnectLock) {
            this.mConnecting = true;
            iBackupAgent = null;
            this.mConnectedAgent = null;
            try {
                if (this.mActivityManager.bindBackupAgent(
                        applicationInfo.packageName, i, this.mUserId, i2)) {
                    Slog.d(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId, "awaiting agent for " + applicationInfo));
                    long currentTimeMillis = System.currentTimeMillis() + 10000;
                    while (this.mConnecting
                            && this.mConnectedAgent == null
                            && System.currentTimeMillis() < currentTimeMillis) {
                        try {
                            this.mAgentConnectLock.wait(5000L);
                        } catch (InterruptedException e) {
                            Slog.w(
                                    "BackupManagerService",
                                    addUserIdToLogMessage(this.mUserId, "Interrupted: " + e));
                            this.mConnecting = false;
                            this.mConnectedAgent = null;
                        }
                    }
                    if (this.mConnecting) {
                        Slog.w(
                                "BackupManagerService",
                                addUserIdToLogMessage(
                                        this.mUserId,
                                        "Timeout waiting for agent " + applicationInfo));
                        this.mConnectedAgent = null;
                    }
                    Slog.i(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId, "got agent " + this.mConnectedAgent));
                    iBackupAgent = this.mConnectedAgent;
                }
            } catch (RemoteException unused) {
            }
        }
        if (iBackupAgent == null) {
            this.mActivityManagerInternal.clearPendingBackup(this.mUserId);
        }
        return iBackupAgent;
    }

    public final void clearApplicationDataBeforeRestore(String str) {
        clearApplicationDataSynchronous(str, false, true);
    }

    public final void clearApplicationDataSynchronous(String str, boolean z, boolean z2) {
        try {
            ApplicationInfo applicationInfo =
                    this.mPackageManager.getPackageInfoAsUser(str, 0, this.mUserId).applicationInfo;
            if (!z || applicationInfo.targetSdkVersion < 29) {
                if ((applicationInfo.flags & 64) == 0) {
                    return;
                }
            } else if ((applicationInfo.privateFlags & 67108864) == 0) {
                return;
            }
            ClearDataObserver clearDataObserver = new ClearDataObserver();
            clearDataObserver.backupManagerService = this;
            synchronized (this.mClearDataLock) {
                this.mClearingData = true;
                this.mActivityManagerInternal.clearApplicationUserData(
                        str, z2, true, clearDataObserver, this.mUserId);
                long currentTimeMillis = System.currentTimeMillis() + 30000;
                while (this.mClearingData && System.currentTimeMillis() < currentTimeMillis) {
                    try {
                        this.mClearDataLock.wait(5000L);
                    } catch (InterruptedException e) {
                        this.mClearingData = false;
                        Slog.w(
                                "BackupManagerService",
                                addUserIdToLogMessage(
                                        this.mUserId,
                                        "Interrupted while waiting for "
                                                + str
                                                + " data to be cleared"),
                                e);
                    }
                }
                if (this.mClearingData) {
                    Slog.w(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId, "Clearing app data for " + str + " timed out"));
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            this.mUserId, "Tried to clear data for " + str + " but not found"));
        }
    }

    public final void clearBackupData(String str, String str2) {
        HashSet hashSet;
        Set set;
        Slog.v(
                "BackupManagerService",
                addUserIdToLogMessage(this.mUserId, "clearBackupData() of " + str2 + " on " + str));
        try {
            PackageInfo packageInfoAsUser =
                    this.mPackageManager.getPackageInfoAsUser(str2, 134217728, this.mUserId);
            if (this.mContext.checkPermission(
                            "android.permission.BACKUP",
                            Binder.getCallingPid(),
                            Binder.getCallingUid())
                    == -1) {
                set = (Set) this.mBackupParticipants.get(Binder.getCallingUid());
            } else {
                ProcessedPackagesJournal processedPackagesJournal = this.mProcessedPackagesJournal;
                synchronized (processedPackagesJournal.mProcessedPackages) {
                    hashSet = new HashSet(processedPackagesJournal.mProcessedPackages);
                }
                set = hashSet;
            }
            if (set.contains(str2)) {
                this.mBackupHandler.removeMessages(12);
                synchronized (this.mQueueLock) {
                    try {
                        TransportConnection transportClient =
                                this.mTransportManager.getTransportClient(
                                        str, "BMS.clearBackupData()");
                        if (transportClient == null) {
                            BackupHandler backupHandler = this.mBackupHandler;
                            ClearRetryParams clearRetryParams = new ClearRetryParams();
                            clearRetryParams.transportName = str;
                            clearRetryParams.packageName = str2;
                            this.mBackupHandler.sendMessageDelayed(
                                    backupHandler.obtainMessage(12, clearRetryParams),
                                    ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                            return;
                        }
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            UserBackupManagerService$$ExternalSyntheticLambda0
                                    userBackupManagerService$$ExternalSyntheticLambda0 =
                                            new UserBackupManagerService$$ExternalSyntheticLambda0(
                                                    this, transportClient, 2);
                            this.mWakelock.acquire();
                            BackupHandler backupHandler2 = this.mBackupHandler;
                            ClearParams clearParams = new ClearParams();
                            clearParams.mTransportConnection = transportClient;
                            clearParams.packageInfo = packageInfoAsUser;
                            clearParams.listener =
                                    userBackupManagerService$$ExternalSyntheticLambda0;
                            this.mBackupHandler.sendMessage(
                                    backupHandler2.obtainMessage(4, clearParams));
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } finally {
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.d(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            this.mUserId,
                            "No such package '" + str2 + "' - not clearing backup data"));
        }
    }

    public final void dataChangedImpl(String str) {
        dataChangedImpl(str, dataChangedTargets(str));
    }

    public final void dataChangedImpl(String str, HashSet hashSet) {
        if (hashSet == null) {
            int i = this.mUserId;
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "dataChanged but no participant pkg='", str, "' uid=");
            m.append(Binder.getCallingUid());
            Slog.w("BackupManagerService", addUserIdToLogMessage(i, m.toString()));
            return;
        }
        synchronized (this.mQueueLock) {
            try {
                if (hashSet.contains(str)) {
                    BackupRequest backupRequest = new BackupRequest();
                    backupRequest.packageName = str;
                    if (this.mPendingBackups.put(str, backupRequest) == null) {
                        writeToJournalLocked(str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
    }

    public final HashSet dataChangedTargets(String str) {
        HashSet hashSet;
        HashSet hashSet2;
        if (this.mContext.checkPermission(
                        "android.permission.BACKUP", Binder.getCallingPid(), Binder.getCallingUid())
                == -1) {
            synchronized (this.mBackupParticipants) {
                hashSet2 = (HashSet) this.mBackupParticipants.get(Binder.getCallingUid());
            }
            return hashSet2;
        }
        if ("@pm@".equals(str)) {
            return Sets.newHashSet(new String[] {"@pm@"});
        }
        synchronized (this.mBackupParticipants) {
            SparseArray sparseArray = this.mBackupParticipants;
            hashSet = new HashSet();
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                HashSet hashSet3 = (HashSet) sparseArray.valueAt(i);
                if (hashSet3 != null) {
                    hashSet.addAll(hashSet3);
                }
            }
        }
        return hashSet;
    }

    public final void dequeueFullBackupLocked(String str) {
        for (int size = this.mFullBackupQueue.size() - 1; size >= 0; size--) {
            if (str.equals(((FullBackupEntry) this.mFullBackupQueue.get(size)).packageName)) {
                this.mFullBackupQueue.remove(size);
            }
        }
    }

    public final void dump(PrintWriter printWriter, String[] strArr) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (strArr != null) {
            try {
                for (String str : strArr) {
                    if ("agents".startsWith(str)) {
                        dumpAgents(printWriter);
                        return;
                    }
                    boolean equals = "transportclients".equals(str.toLowerCase());
                    TransportManager transportManager = this.mTransportManager;
                    if (equals) {
                        transportManager.dumpTransportClients(printWriter);
                        return;
                    } else {
                        if ("transportstats".equals(str.toLowerCase())) {
                            transportManager.dumpTransportStats(printWriter);
                            return;
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        dumpInternal(printWriter);
        dumpBMMEvents(printWriter);
    }

    public final void dumpAgents(PrintWriter printWriter) {
        List<PackageInfo> allAgentPackages = allAgentPackages();
        printWriter.println("Defined backup agents:");
        for (PackageInfo packageInfo : allAgentPackages) {
            printWriter.print("  ");
            printWriter.print(packageInfo.packageName);
            printWriter.println(':');
            printWriter.print("      ");
            printWriter.println(packageInfo.applicationInfo.backupAgentName);
        }
    }

    @NeverCompile
    public final void dumpInternal(PrintWriter printWriter) {
        long j;
        HashSet hashSet;
        long j2;
        long j3;
        String str;
        Intent intent;
        String m =
                this.mUserId == 0
                        ? ""
                        : AmFmBandRange$$ExternalSyntheticOutline0.m(
                                this.mUserId, new StringBuilder("User "), ":");
        synchronized (this.mQueueLock) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(m);
                sb.append("Backup Manager is ");
                sb.append(this.mEnabled ? "enabled" : "disabled");
                sb.append(" / ");
                sb.append(!this.mSetupComplete ? "not " : "");
                sb.append("setup complete / ");
                sb.append(this.mPendingInits.size() == 0 ? "not " : "");
                sb.append("pending init");
                printWriter.println(sb.toString());
                printWriter.println(
                        "Auto-restore is ".concat(this.mAutoRestore ? "enabled" : "disabled"));
                if (this.mBackupRunning) {
                    printWriter.println("Backup currently running");
                }
                printWriter.println(
                        isBackupOperationInProgress()
                                ? "Backup in progress"
                                : "No backups running");
                printWriter.println(
                        "Framework scheduling is "
                                .concat(isFrameworkSchedulingEnabled() ? "enabled" : "disabled"));
                printWriter.println(
                        "Last backup pass started: "
                                + this.mLastBackupPass
                                + " (now = "
                                + System.currentTimeMillis()
                                + ')');
                StringBuilder sb2 = new StringBuilder("  next scheduled: ");
                int i = this.mUserId;
                ComponentName componentName = KeyValueBackupJob.sKeyValueJobService;
                synchronized (KeyValueBackupJob.class) {
                    j = KeyValueBackupJob.sNextScheduledForUserId.get(i);
                }
                sb2.append(j);
                printWriter.println(sb2.toString());
                printWriter.println(m + "Transport whitelist:");
                for (ComponentName componentName2 : this.mTransportManager.mTransportWhitelist) {
                    printWriter.print("    ");
                    printWriter.println(componentName2.flattenToShortString());
                }
                printWriter.println(m + "Available transports:");
                for (String str2 : listAllTransports()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(
                            str2.equals(this.mTransportManager.mCurrentTransportName)
                                    ? "  * "
                                    : "    ");
                    sb3.append(str2);
                    printWriter.println(sb3.toString());
                    try {
                        File file =
                                new File(
                                        this.mBaseStateDir,
                                        this.mTransportManager.getTransportDirName(str2));
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("       destination: ");
                        TransportManager transportManager = this.mTransportManager;
                        synchronized (transportManager.mTransportLock) {
                            str =
                                    transportManager.getRegisteredTransportDescriptionOrThrowLocked(
                                                    str2)
                                            .currentDestinationString;
                        }
                        sb4.append(str);
                        printWriter.println(sb4.toString());
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("       intent: ");
                        TransportManager transportManager2 = this.mTransportManager;
                        synchronized (transportManager2.mTransportLock) {
                            intent =
                                    transportManager2
                                            .getRegisteredTransportDescriptionOrThrowLocked(str2)
                                            .configurationIntent;
                        }
                        sb5.append(intent);
                        printWriter.println(sb5.toString());
                        for (File file2 : file.listFiles()) {
                            printWriter.println(
                                    "       "
                                            + file2.getName()
                                            + " - "
                                            + file2.length()
                                            + " state bytes");
                        }
                    } catch (Exception e) {
                        Slog.e(
                                "BackupManagerService",
                                addUserIdToLogMessage(this.mUserId, "Error in transport"),
                                e);
                        printWriter.println("        Error: " + e);
                    }
                }
                this.mTransportManager.dumpTransportClients(printWriter);
                printWriter.println(m + "Pending init: " + this.mPendingInits.size());
                Iterator it = this.mPendingInits.iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + ((String) it.next()));
                }
                printWriter.print(m + "Ancestral: ");
                printWriter.println(Long.toHexString(this.mAncestralToken));
                printWriter.print(m + "Current:   ");
                printWriter.println(Long.toHexString(this.mCurrentToken));
                int size = this.mBackupParticipants.size();
                printWriter.println(m + "Participants:");
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = this.mBackupParticipants.keyAt(i2);
                    printWriter.print("  uid: ");
                    printWriter.println(keyAt);
                    Iterator it2 = ((HashSet) this.mBackupParticipants.valueAt(i2)).iterator();
                    while (it2.hasNext()) {
                        printWriter.println("    " + ((String) it2.next()));
                    }
                }
                StringBuilder sb6 = new StringBuilder();
                sb6.append(m);
                sb6.append("Ancestral packages: ");
                Set set = this.mAncestralPackages;
                sb6.append(set == null ? "none" : Integer.valueOf(set.size()));
                printWriter.println(sb6.toString());
                Set set2 = this.mAncestralPackages;
                if (set2 != null) {
                    Iterator it3 = set2.iterator();
                    while (it3.hasNext()) {
                        printWriter.println("    " + ((String) it3.next()));
                    }
                }
                ProcessedPackagesJournal processedPackagesJournal = this.mProcessedPackagesJournal;
                synchronized (processedPackagesJournal.mProcessedPackages) {
                    hashSet = new HashSet(processedPackagesJournal.mProcessedPackages);
                }
                printWriter.println(m + "Ever backed up: " + hashSet.size());
                Iterator it4 = hashSet.iterator();
                while (it4.hasNext()) {
                    printWriter.println("    " + ((String) it4.next()));
                }
                printWriter.println(m + "Pending key/value backup: " + this.mPendingBackups.size());
                Iterator it5 = this.mPendingBackups.values().iterator();
                while (it5.hasNext()) {
                    printWriter.println("    " + ((BackupRequest) it5.next()));
                }
                printWriter.println(m + "Full backup queue:" + this.mFullBackupQueue.size());
                Iterator it6 = this.mFullBackupQueue.iterator();
                while (it6.hasNext()) {
                    FullBackupEntry fullBackupEntry = (FullBackupEntry) it6.next();
                    printWriter.print("    ");
                    printWriter.print(fullBackupEntry.lastBackup);
                    printWriter.print(" : ");
                    printWriter.println(fullBackupEntry.packageName);
                }
                printWriter.println(m + "Agent timeouts:");
                printWriter.println(
                        "    KvBackupAgentTimeoutMillis: "
                                + this.mAgentTimeoutParameters.getKvBackupAgentTimeoutMillis());
                printWriter.println(
                        "    FullBackupAgentTimeoutMillis: "
                                + this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis());
                StringBuilder sb7 = new StringBuilder();
                sb7.append("    SharedBackupAgentTimeoutMillis: ");
                BackupAgentTimeoutParameters backupAgentTimeoutParameters =
                        this.mAgentTimeoutParameters;
                synchronized (backupAgentTimeoutParameters.mLock) {
                    j2 = backupAgentTimeoutParameters.mSharedBackupAgentTimeoutMillis;
                }
                sb7.append(j2);
                printWriter.println(sb7.toString());
                printWriter.println(
                        "    RestoreAgentTimeoutMillis (system): "
                                + this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(9999));
                printWriter.println(
                        "    RestoreAgentTimeoutMillis: "
                                + this.mAgentTimeoutParameters.getRestoreAgentTimeoutMillis(10000));
                StringBuilder sb8 = new StringBuilder();
                sb8.append("    RestoreAgentFinishedTimeoutMillis: ");
                BackupAgentTimeoutParameters backupAgentTimeoutParameters2 =
                        this.mAgentTimeoutParameters;
                synchronized (backupAgentTimeoutParameters2.mLock) {
                    j3 = backupAgentTimeoutParameters2.mRestoreAgentFinishedTimeoutMillis;
                }
                sb8.append(j3);
                printWriter.println(sb8.toString());
                printWriter.println(
                        "    QuotaExceededTimeoutMillis: "
                                + this.mAgentTimeoutParameters.getQuotaExceededTimeoutMillis());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enqueueFullBackup(long j, String str) {
        int i;
        FullBackupEntry fullBackupEntry = new FullBackupEntry(j, str);
        synchronized (this.mQueueLock) {
            try {
                dequeueFullBackupLocked(str);
                if (j > 0) {
                    i = this.mFullBackupQueue.size() - 1;
                    while (true) {
                        if (i < 0) {
                            break;
                        }
                        if (((FullBackupEntry) this.mFullBackupQueue.get(i)).lastBackup <= j) {
                            this.mFullBackupQueue.add(i + 1, fullBackupEntry);
                            break;
                        }
                        i--;
                    }
                } else {
                    i = -1;
                }
                if (i < 0) {
                    this.mFullBackupQueue.add(0, fullBackupEntry);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        AnonymousClass1 anonymousClass1 = this.mFullBackupScheduleWriter;
        BackupHandler backupHandler = this.mBackupHandler;
        backupHandler.removeCallbacks(anonymousClass1);
        backupHandler.post(this.mFullBackupScheduleWriter);
    }

    public final List filterUserFacingPackages(List list) {
        if (!shouldSkipUserFacingData()) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            if (shouldSkipPackage(packageInfo.packageName)) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Will skip backup/restore for "),
                        packageInfo.packageName,
                        "BackupManagerService");
            } else {
                arrayList.add(packageInfo);
            }
        }
        return arrayList;
    }

    public final boolean fullBackupAllowable(String str) {
        TransportManager transportManager = this.mTransportManager;
        boolean isTransportRegistered = transportManager.isTransportRegistered(str);
        int i = this.mUserId;
        if (!isTransportRegistered) {
            Slog.w(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            i, "Transport not registered; full data backup not performed"));
            return false;
        }
        try {
            if (new File(
                                    new File(
                                            this.mBaseStateDir,
                                            transportManager.getTransportDirName(str)),
                                    "@pm@")
                            .length()
                    > 0) {
                return true;
            }
            Slog.i(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            i, "Full backup requested but dataset not yet initialized"));
            return false;
        } catch (Exception e) {
            Slog.w(
                    "BackupManagerService",
                    addUserIdToLogMessage(i, "Unable to get transport name: " + e.getMessage()));
            return false;
        }
    }

    public final void fullRestoreCustomized(
            String str,
            String str2,
            IMemorySaverBackupRestoreObserver iMemorySaverBackupRestoreObserver) {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(new File(str), 805306368);
        BackupManagerYuva backupManagerYuva = mBackupManagerYuva;
        if (backupManagerYuva != null) {
            backupManagerYuva.mMemorySaverObserver = iMemorySaverBackupRestoreObserver;
            backupManagerYuva.isMemorySaverRestoreFail = false;
            backupManagerYuva.isMemorySaverBackupFail = false;
            backupManagerYuva.mBackupPackageName = null;
            backupManagerYuva.mRestorePackageName = null;
        }
        String[] split = str.split("/");
        String substring =
                split[split.length - 1].substring(0, split[split.length - 1].length() - 3);
        BackupManagerYuva backupManagerYuva2 = mBackupManagerYuva;
        if (backupManagerYuva2 != null) {
            backupManagerYuva2.mRestorePackageName = substring;
            if (backupManagerYuva2.mMemorySaverObserver != null) {
                try {
                    Slog.d(
                            "BackupManagerYuva",
                            "restore started " + backupManagerYuva2.mRestorePackageName);
                    backupManagerYuva2.mMemorySaverObserver.onRestoreStart(
                            backupManagerYuva2.mRestorePackageName);
                } catch (RemoteException unused) {
                    Slog.w("BackupManagerYuva", "full restore observer went away: startRestore");
                    backupManagerYuva2.mMemorySaverObserver = null;
                }
            }
        }
        this.mEncPassword = str2;
        mSplitRestoreFlag = Boolean.TRUE;
        adbRestore(open, true);
        mSplitRestoreFlag = Boolean.FALSE;
    }

    public final int generateRandomIntegerToken() {
        int nextInt = this.mTokenGenerator.nextInt();
        if (nextInt < 0) {
            nextInt = -nextInt;
        }
        return (this.mNextToken.incrementAndGet()
                        & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                | (nextInt & (-256));
    }

    public final long getAvailableRestoreToken(String str) {
        boolean contains;
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "getAvailableRestoreToken");
        long j = this.mAncestralToken;
        synchronized (this.mQueueLock) {
            if (this.mCurrentToken != 0) {
                ProcessedPackagesJournal processedPackagesJournal = this.mProcessedPackagesJournal;
                synchronized (processedPackagesJournal.mProcessedPackages) {
                    contains =
                            ((HashSet) processedPackagesJournal.mProcessedPackages).contains(str);
                }
                if (contains) {
                    j = this.mCurrentToken;
                }
            }
        }
        return j;
    }

    public BackupManagerMonitorEventSender getBMMEventSender(
            IBackupManagerMonitor iBackupManagerMonitor) {
        return new BackupManagerMonitorEventSender(iBackupManagerMonitor);
    }

    public int getBackupDestinationFromTransport(TransportConnection transportConnection)
            throws TransportNotAvailableException, RemoteException {
        if (!shouldUseNewBackupEligibilityRules()) {
            return 0;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if ((transportConnection
                                    .connectOrThrow("BMS.getBackupDestinationFromTransport")
                                    .getTransportFlags()
                            & 2)
                    == 0) {
                return 0;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 1;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final BackupEligibilityRules getEligibilityRulesForOperation(int i) {
        return new BackupEligibilityRules(
                this.mPackageManager,
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class),
                this.mUserId,
                this.mContext,
                i,
                false);
    }

    public final int getMessageIdForOperationType(int i) {
        if (i == 0) {
            return 17;
        }
        if (i == 1) {
            return 18;
        }
        Slog.wtf(
                "BackupManagerService",
                addUserIdToLogMessage(
                        this.mUserId,
                        "getMessageIdForOperationType called on invalid operation type: " + i));
        return -1;
    }

    public BroadcastReceiver getPackageTrackingReceiver() {
        return this.mPackageTrackingReceiver;
    }

    public final Object getQueueLock() {
        return this.mQueueLock;
    }

    public BackupParams getRequestBackupParams(
            String[] strArr,
            IBackupObserver iBackupObserver,
            IBackupManagerMonitor iBackupManagerMonitor,
            int i,
            BackupEligibilityRules backupEligibilityRules,
            TransportConnection transportConnection,
            String str,
            OnTaskFinishedListener onTaskFinishedListener) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str2 : strArr) {
            if ("@pm@".equals(str2)) {
                arrayList2.add(str2);
            } else {
                try {
                    PackageInfo packageInfoAsUser =
                            this.mPackageManager.getPackageInfoAsUser(
                                    str2, 134217728, this.mUserId);
                    if (!backupEligibilityRules.appIsEligibleForBackup(
                            packageInfoAsUser.applicationInfo)) {
                        BackupObserverUtils.sendBackupOnPackageResult(iBackupObserver, str2, -2001);
                    } else if (backupEligibilityRules.appGetsFullBackup(packageInfoAsUser)) {
                        arrayList.add(packageInfoAsUser.packageName);
                    } else {
                        arrayList2.add(packageInfoAsUser.packageName);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    BackupObserverUtils.sendBackupOnPackageResult(iBackupObserver, str2, -2002);
                }
            }
        }
        EventLog.writeEvent(
                2828,
                Integer.valueOf(strArr.length),
                Integer.valueOf(arrayList2.size()),
                Integer.valueOf(arrayList.size()));
        boolean z = (i & 1) != 0;
        BackupParams backupParams = new BackupParams();
        backupParams.mTransportConnection = transportConnection;
        backupParams.dirName = str;
        backupParams.kvPackages = arrayList2;
        backupParams.fullPackages = arrayList;
        backupParams.observer = iBackupObserver;
        backupParams.monitor = iBackupManagerMonitor;
        backupParams.listener = onTaskFinishedListener;
        backupParams.nonIncrementalBackup = z;
        backupParams.mBackupEligibilityRules = backupEligibilityRules;
        return backupParams;
    }

    public Thread getThreadForAsyncOperation(String str, Runnable runnable) {
        return new Thread(runnable, str);
    }

    public final BackupWakeLock getWakelock() {
        return this.mWakelock;
    }

    public void initializeBackupEnableState() {
        setBackupEnabled(readEnabledState(), false);
    }

    public final void initializeTransports(String[] strArr, IBackupObserver iBackupObserver) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "initializeTransport");
        Slog.v(
                "BackupManagerService",
                addUserIdToLogMessage(
                        this.mUserId, "initializeTransport(): " + Arrays.asList(strArr)));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mWakelock.acquire();
            this.mBackupHandler.post(
                    new PerformInitializeTask(
                            this,
                            this.mTransportManager,
                            strArr,
                            iBackupObserver,
                            new OnTaskFinishedListener() { // from class:
                                                           // com.android.server.backup.UserBackupManagerService$$ExternalSyntheticLambda10
                                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                                public final void onFinished(String str) {
                                    UserBackupManagerService.this.mWakelock.release();
                                }
                            },
                            this.mBaseStateDir));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isBackupOperationInProgress() {
        LifecycleOperationStorage lifecycleOperationStorage = this.mOperationStorage;
        synchronized (lifecycleOperationStorage.mOperationsLock) {
            for (int i = 0; i < lifecycleOperationStorage.mOperations.size(); i++) {
                try {
                    Operation operation =
                            (Operation) lifecycleOperationStorage.mOperations.valueAt(i);
                    if (operation.type == 2 && operation.state == 0) {
                        return true;
                    }
                } finally {
                }
            }
            return false;
        }
    }

    public final synchronized boolean isFrameworkSchedulingEnabled() {
        return Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "backup_scheduling_enabled",
                        1,
                        this.mUserId)
                == 1;
    }

    public final boolean isPrivilegeBackupApp(int i) {
        String nameForUid = this.mPackageManager.getNameForUid(i);
        boolean z = false;
        try {
        } catch (Exception unused) {
            Slog.d("BackupManagerService", "isPrivilegeBackupApp() error");
        }
        if ((!nameForUid.equals("android.uid.system:1000")
                        || this.mPackageManager.checkSignatures(1000, i) != 0)
                && (!nameForUid.equals("android.uid.samsungcloud:5009")
                        || this.mPackageManager.checkSignatures(5009, i) != 0)) {
            for (String str : this.mPrivilegePkgName) {
                if (!str.equals(nameForUid)
                        || this.mPackageManager.checkSignatures("android", nameForUid) != 0) {}
            }
            Slog.d(
                    "BackupManagerService",
                    "isPrivilegeBackupApp() pkg name is " + nameForUid + " value : " + z);
            return z;
        }
        z = true;
        Slog.d(
                "BackupManagerService",
                "isPrivilegeBackupApp() pkg name is " + nameForUid + " value : " + z);
        return z;
    }

    public final boolean isYuvaSupported() {
        if (SystemProperties.getInt("ro.product.first_api_level", 0) < 34) {
            try {
                this.mPackageManager.getPackageInfoAsUser(
                        "com.samsung.memorysaver", 0, this.mUserId);
                Slog.d("BackupManagerService", "Memory Saver is there");
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.d("BackupManagerService", "MemorySaver not found");
            }
        }
        return false;
    }

    public final String[] listAllTransports() {
        String[] strArr;
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "listAllTransports");
        TransportManager transportManager = this.mTransportManager;
        synchronized (transportManager.mTransportLock) {
            try {
                strArr = new String[transportManager.mRegisteredTransportsDescriptionMap.size()];
                Iterator it =
                        transportManager.mRegisteredTransportsDescriptionMap.values().iterator();
                int i = 0;
                while (it.hasNext()) {
                    strArr[i] = ((TransportManager.TransportDescription) it.next()).name;
                    i++;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return strArr;
    }

    public final void logBackupComplete(String str) {
        String[] strArr;
        if (str.equals("@pm@")) {
            return;
        }
        BackupManagerConstants backupManagerConstants = this.mConstants;
        synchronized (backupManagerConstants) {
            Slog.v(
                    "BackupManagerConstants",
                    "getBackupFinishedNotificationReceivers(...) returns "
                            + TextUtils.join(
                                    ", ",
                                    backupManagerConstants.mBackupFinishedNotificationReceivers));
            strArr = backupManagerConstants.mBackupFinishedNotificationReceivers;
        }
        for (String str2 : strArr) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.BACKUP_FINISHED");
            intent.setPackage(str2);
            intent.addFlags(268435488);
            intent.putExtra("packageName", str);
            this.mContext.sendBroadcastAsUser(intent, UserHandle.of(this.mUserId));
        }
        ProcessedPackagesJournal processedPackagesJournal = this.mProcessedPackagesJournal;
        synchronized (processedPackagesJournal.mProcessedPackages) {
            try {
                if (((HashSet) processedPackagesJournal.mProcessedPackages).add(str)) {
                    File file = new File(processedPackagesJournal.mStateDirectory, "processed");
                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rws");
                        try {
                            randomAccessFile.seek(randomAccessFile.length());
                            randomAccessFile.writeUTF(str);
                            randomAccessFile.close();
                        } catch (Throwable th) {
                            try {
                                randomAccessFile.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException unused) {
                        Slog.e(
                                "ProcessedPackagesJournal",
                                "Can't log backup of " + str + " to " + file);
                    }
                }
            } finally {
            }
        }
    }

    public final void prepareOperationTimeout(
            int i, long j, BackupRestoreTask backupRestoreTask, int i2) {
        if (this.mSepTimeOut) {
            j = this.mSepTimeoutMin * 60000;
        }
        if (i2 == 0 || i2 == 1) {
            LifecycleOperationStorage lifecycleOperationStorage = this.mOperationStorage;
            lifecycleOperationStorage.getClass();
            lifecycleOperationStorage.registerOperationForPackages(
                    i, Sets.newHashSet(), backupRestoreTask, i2);
            int messageIdForOperationType = getMessageIdForOperationType(i2);
            BackupHandler backupHandler = this.mBackupHandler;
            backupHandler.sendMessageDelayed(
                    backupHandler.obtainMessage(messageIdForOperationType, i, 0, backupRestoreTask),
                    j);
            return;
        }
        Slog.wtf(
                "BackupManagerService",
                addUserIdToLogMessage(
                        this.mUserId,
                        "prepareOperationTimeout() doesn't support operation "
                                + Integer.toHexString(i)
                                + " of type "
                                + i2));
    }

    public boolean readEnabledState() {
        int i = this.mUserId;
        File file = new File(UserBackupManagerFiles.getBaseStateDir(i), "backup_enabled");
        boolean z = false;
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    int read = fileInputStream.read();
                    if (read != 0 && read != 1) {
                        Slog.e("BackupManagerService", "Unexpected enabled state:" + read);
                    }
                    boolean z2 = read != 0;
                    fileInputStream.close();
                    z = z2;
                } finally {
                }
            } catch (IOException unused) {
                Slog.e("BackupManagerService", "Cannot read enable state; assuming disabled");
            }
        } else {
            Slog.i(
                    "BackupManagerService",
                    "isBackupEnabled() => false due to absent settings file");
        }
        Slog.d("BackupManagerService", "user:" + i + " readBackupEnableState enabled:" + z);
        return z;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList readFullBackupSchedule() {
        /*
            Method dump skipped, instructions count: 417
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.UserBackupManagerService.readFullBackupSchedule():java.util.ArrayList");
    }

    public final void recordInitPending(String str, String str2, boolean z) {
        synchronized (this.mQueueLock) {
            File file = new File(new File(this.mBaseStateDir, str2), "_need_init_");
            if (z) {
                this.mPendingInits.add(str);
                try {
                    new FileOutputStream(file).close();
                } catch (IOException unused) {
                }
            } else {
                file.delete();
                this.mPendingInits.remove(str);
            }
        }
    }

    public final void reportDelayedRestoreResult(String str, List list) {
        String str2 = this.mTransportManager.mCurrentTransportName;
        if (str2 == null) {
            Slog.w(
                    "BackupManagerService",
                    "Failed to send delayed restore logs as no transport selected");
            return;
        }
        TransportConnection transportConnection = null;
        try {
            try {
                PackageInfo packageInfoAsUser =
                        this.mPackageManager.getPackageInfoAsUser(
                                str, PackageManager.PackageInfoFlags.of(0L), this.mUserId);
                transportConnection =
                        this.mTransportManager.getTransportClientOrThrow(
                                str2, "BMS.reportDelayedRestoreResult");
                BackupManagerMonitorEventSender bMMEventSender =
                        getBMMEventSender(
                                transportConnection
                                        .connectOrThrow("BMS.reportDelayedRestoreResult")
                                        .getBackupManagerMonitor());
                bMMEventSender.getClass();
                Bundle bundle = new Bundle();
                bundle.putParcelableList(
                        "android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS", list);
                bundle.putInt("android.app.backup.extra.OPERATION_TYPE", 1);
                bMMEventSender.monitorEvent(52, packageInfoAsUser, 2, bundle);
            } catch (PackageManager.NameNotFoundException
                    | RemoteException
                    | TransportNotAvailableException
                    | TransportNotRegisteredException e) {
                Slog.w("BackupManagerService", "Failed to send delayed restore logs: " + e);
                if (transportConnection == null) {
                    return;
                }
            }
            this = this.mTransportManager.mTransportConnectionManager;
            this.disposeOfTransportClient(transportConnection, "BMS.reportDelayedRestoreResult");
        } catch (Throwable th) {
            if (transportConnection != null) {
                this.mTransportManager.mTransportConnectionManager.disposeOfTransportClient(
                        transportConnection, "BMS.reportDelayedRestoreResult");
            }
            throw th;
        }
    }

    public final void resetBackupState(File file) {
        int i;
        synchronized (this.mQueueLock) {
            try {
                ProcessedPackagesJournal processedPackagesJournal = this.mProcessedPackagesJournal;
                synchronized (processedPackagesJournal.mProcessedPackages) {
                    ((HashSet) processedPackagesJournal.mProcessedPackages).clear();
                    new File(processedPackagesJournal.mStateDirectory, "processed").delete();
                }
                this.mCurrentToken = 0L;
                writeRestoreTokens();
                for (File file2 : file.listFiles()) {
                    if (!file2.getName().equals("_need_init_")) {
                        file2.delete();
                    }
                }
            } finally {
            }
        }
        synchronized (this.mBackupParticipants) {
            try {
                int size = this.mBackupParticipants.size();
                for (i = 0; i < size; i++) {
                    HashSet hashSet = (HashSet) this.mBackupParticipants.valueAt(i);
                    if (hashSet != null) {
                        Iterator it = hashSet.iterator();
                        while (it.hasNext()) {
                            dataChangedImpl((String) it.next());
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final void scheduleNextFullBackupJob(long j) {
        synchronized (this.mQueueLock) {
            try {
                if (this.mFullBackupQueue.size() > 0) {
                    long currentTimeMillis =
                            System.currentTimeMillis()
                                    - ((FullBackupEntry) this.mFullBackupQueue.get(0)).lastBackup;
                    long fullBackupIntervalMilliseconds =
                            this.mConstants.getFullBackupIntervalMilliseconds();
                    FullBackupJob.schedule(
                            this.mUserId,
                            this.mContext,
                            Math.max(
                                    j,
                                    currentTimeMillis < fullBackupIntervalMilliseconds
                                            ? fullBackupIntervalMilliseconds - currentTimeMillis
                                            : 0L),
                            this);
                } else {
                    Slog.i(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    this.mUserId, "Full backup queue empty; not scheduling"));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setAncestralSerialNumberFile(File file) {
        this.mAncestralSerialNumberFile = file;
    }

    public final void setBackupEnabled(boolean z, boolean z2) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "setBackupEnabled");
        Slog.i(
                "BackupManagerService",
                addUserIdToLogMessage(this.mUserId, "Backup enabled => " + z));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean z3 = this.mEnabled;
            synchronized (this) {
                if (z2) {
                    try {
                        writeEnabledState(z);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                this.mEnabled = z;
            }
            updateStateOnBackupEnabled(z3, z);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setPowerManager(PowerManager powerManager) {
        this.mPowerManager = powerManager;
    }

    public final void setRunningFullBackupTask() {
        this.mRunningFullBackupTask = null;
    }

    public final void setSepWakeLock() {
        this.mSepWakeLock = false;
    }

    public void setWorkSource(WorkSource workSource) {
        this.mWakelock.mPowerManagerWakeLock.setWorkSource(workSource);
    }

    public boolean shouldSkipPackage(String str) {
        return "com.android.wallpaperbackup".equals(str);
    }

    public boolean shouldSkipUserFacingData() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "backup_skip_user_facing_packages", 0)
                != 0;
    }

    public boolean shouldUseNewBackupEligibilityRules() {
        return FeatureFlagUtils.isEnabled(
                this.mContext, "settings_use_new_backup_eligibility_rules");
    }

    public final boolean startConfirmationUi(int i, String str) {
        try {
            Intent intent = new Intent(str);
            intent.setClassName(
                    "com.android.backupconfirm",
                    "com.android.backupconfirm.BackupRestoreConfirmation");
            intent.putExtra("conftoken", i);
            intent.addFlags(536870912);
            this.mContext.startActivityAsUser(intent, UserHandle.SYSTEM);
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    public final void startSepTimeout(AdbParams adbParams) {
        int i = this.mSepTimeoutMin * 60000;
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i, "Posting MSG_SEP_TIMEOUT msg, ", "BackupManagerService");
        BackupHandler backupHandler = this.mBackupHandler;
        backupHandler.sendMessageDelayed(backupHandler.obtainMessage(101, adbParams), i);
    }

    public final void tearDownAgentAndKill(ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            return;
        }
        try {
            this.mActivityManager.unbindBackupAgent(applicationInfo);
            if (UserHandle.isCore(applicationInfo.uid)
                    || applicationInfo.packageName.equals("com.android.backupconfirm")) {
                return;
            }
            this.mActivityManager.killApplicationProcess(
                    applicationInfo.processName, applicationInfo.uid);
        } catch (RemoteException unused) {
            Slog.d(
                    "BackupManagerService",
                    addUserIdToLogMessage(this.mUserId, "Lost app trying to shut down"));
        }
    }

    public void tearDownService() {
        this.mAgentTimeoutParameters.stop();
        this.mConstants.stop();
        this.mContext.getContentResolver().unregisterContentObserver(this.mSetupObserver);
        this.mContext.unregisterReceiver(this.mRunInitReceiver);
        this.mContext.unregisterReceiver(this.mPackageTrackingReceiver);
        this.mBackupHandler.stop();
    }

    public final void updateStateForTransport(String str) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mUserId;
        Settings.Secure.putStringForUser(contentResolver, "backup_transport", str, i);
        TransportManager transportManager = this.mTransportManager;
        TransportConnection transportClient =
                transportManager.getTransportClient(str, "BMS.updateStateForTransport()");
        if (transportClient == null) {
            Slog.w(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            i, "Transport " + str + " not registered: current token = 0"));
            this.mCurrentToken = 0L;
            return;
        }
        try {
            BackupTransportClient connectOrThrow =
                    transportClient.connectOrThrow("BMS.updateStateForTransport()");
            AndroidFuture newFuture = connectOrThrow.mTransportFutures.newFuture();
            connectOrThrow.mTransportBinder.getCurrentRestoreSet(newFuture);
            Long l = (Long) connectOrThrow.getFutureResult(newFuture);
            this.mCurrentToken = l == null ? -1000L : l.longValue();
        } catch (Exception unused) {
            this.mCurrentToken = 0L;
            Slog.w(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            i, "Transport " + str + " not available: current token = 0"));
        }
        transportManager.mTransportConnectionManager.disposeOfTransportClient(
                transportClient, "BMS.updateStateForTransport()");
    }

    public void updateStateOnBackupEnabled(boolean z, boolean z2) {
        synchronized (this.mQueueLock) {
            if (z2 && !z) {
                try {
                    if (this.mSetupComplete) {
                        KeyValueBackupJob.schedule(this.mUserId, this.mContext, this);
                        scheduleNextFullBackupJob(0L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (!z2) {
                KeyValueBackupJob.cancel(this.mContext, this.mUserId);
                if (z && this.mSetupComplete) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    this.mTransportManager.forEachRegisteredTransport(
                            new UserBackupManagerService$$ExternalSyntheticLambda7(
                                    this, arrayList, arrayList2, 1));
                    for (int i = 0; i < arrayList.size(); i++) {
                        recordInitPending(
                                (String) arrayList.get(i), (String) arrayList2.get(i), true);
                    }
                    this.mAlarmManager.set(0, System.currentTimeMillis(), this.mRunInitIntent);
                }
            }
        }
    }

    public void updateTransportAttributes(
            int i,
            ComponentName componentName,
            String str,
            Intent intent,
            String str2,
            Intent intent2,
            CharSequence charSequence) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.BACKUP", "updateTransportAttributes");
        Objects.requireNonNull(componentName, "transportComponent can't be null");
        Objects.requireNonNull(str, "name can't be null");
        Objects.requireNonNull(str2, "currentDestinationString can't be null");
        Preconditions.checkArgument(
                (intent2 == null) == (charSequence == null),
                "dataManagementLabel should be null iff dataManagementIntent is null");
        try {
            if (i
                    != this.mContext
                            .getPackageManager()
                            .getPackageUidAsUser(componentName.getPackageName(), 0, this.mUserId)) {
                throw new SecurityException("Only the transport can change its description");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mTransportManager.updateTransportAttributes(
                        componentName, str, intent, str2, intent2, charSequence);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new SecurityException("Transport package not found", e);
        }
    }

    public final boolean waitUntilOperationComplete(int i) {
        Operation operation;
        int i2;
        LifecycleOperationStorage lifecycleOperationStorage = this.mOperationStorage;
        UserBackupManagerService$$ExternalSyntheticLambda14
                userBackupManagerService$$ExternalSyntheticLambda14 =
                        new UserBackupManagerService$$ExternalSyntheticLambda14(1, this);
        synchronized (lifecycleOperationStorage.mOperationsLock) {
            while (true) {
                operation = (Operation) lifecycleOperationStorage.mOperations.get(i);
                if (operation != null) {
                    i2 = operation.state;
                    if (i2 != 0) {
                        break;
                    }
                    try {
                        lifecycleOperationStorage.mOperationsLock.wait();
                    } catch (InterruptedException e) {
                        Slog.w("LifecycleOperationStorage", "Waiting on mOperationsLock: ", e);
                    }
                } else {
                    i2 = 0;
                    break;
                }
            }
        }
        lifecycleOperationStorage.removeOperation(i);
        if (operation != null) {
            userBackupManagerService$$ExternalSyntheticLambda14.accept(operation.type);
        }
        return i2 == 1;
    }

    public void writeEnabledState(boolean z) {
        UserBackupManagerFilePersistedSettings.writeBackupEnableState(this.mUserId, z);
    }

    public final void writeRestoreTokens() {
        int i = this.mUserId;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.mTokenFile, "rwd");
            try {
                randomAccessFile.writeInt(1);
                randomAccessFile.writeLong(this.mAncestralToken);
                randomAccessFile.writeLong(this.mCurrentToken);
                Set set = this.mAncestralPackages;
                if (set == null) {
                    randomAccessFile.writeInt(-1);
                } else {
                    randomAccessFile.writeInt(set.size());
                    Slog.v(
                            "BackupManagerService",
                            addUserIdToLogMessage(
                                    i, "Ancestral packages:  " + this.mAncestralPackages.size()));
                    Iterator it = this.mAncestralPackages.iterator();
                    while (it.hasNext()) {
                        randomAccessFile.writeUTF((String) it.next());
                    }
                }
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.w(
                    "BackupManagerService",
                    addUserIdToLogMessage(i, "Unable to write token file:"),
                    e);
        }
    }

    public final void writeToJournalLocked(String str) {
        try {
            if (this.mJournal == null) {
                File file = this.mJournalDir;
                Objects.requireNonNull(file);
                this.mJournal = new DataChangedJournal(File.createTempFile("journal", null, file));
            }
            DataChangedJournal dataChangedJournal = this.mJournal;
            dataChangedJournal.getClass();
            RandomAccessFile randomAccessFile =
                    new RandomAccessFile(dataChangedJournal.mFile, "rws");
            try {
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.writeUTF(str);
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e(
                    "BackupManagerService",
                    addUserIdToLogMessage(
                            this.mUserId, "Can't write " + str + " to backup journal"),
                    e);
            this.mJournal = null;
        }
    }
}
