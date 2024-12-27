package com.android.server.rollback;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.rollback.IRollbackManager;
import android.content.rollback.PackageRollbackInfo;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.ext.SdkExtensions;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArrayQueue;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PackageWatchdog;
import com.android.server.RescueParty;
import com.android.server.Watchdog;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;
import com.android.server.pm.Installer;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RollbackManagerServiceImpl extends IRollbackManager.Stub
        implements RollbackManagerInternal {
    public final AppDataRollbackHelper mAppDataRollbackHelper;
    public final Context mContext;
    public final Executor mExecutor;
    public final Handler mHandler;
    public final Installer mInstaller;
    public final RollbackPackageHealthObserver mPackageHealthObserver;
    public final RollbackStore mRollbackStore;
    public final ArrayMap mUserBroadcastReceivers;
    public static final boolean LOCAL_LOGV = Log.isLoggable("RollbackManager", 2);
    public static final long DEFAULT_ROLLBACK_LIFETIME_DURATION_MILLIS = TimeUnit.DAYS.toMillis(14);
    public static final long HANDLER_THREAD_TIMEOUT_DURATION_MILLIS = TimeUnit.MINUTES.toMillis(10);
    public long mRollbackLifetimeDurationInMillis = DEFAULT_ROLLBACK_LIFETIME_DURATION_MILLIS;
    public final Random mRandom = new SecureRandom();
    public final SparseBooleanArray mAllocatedRollbackIds = new SparseBooleanArray();
    public final List mRollbacks = new ArrayList();
    public final RollbackManagerServiceImpl$$ExternalSyntheticLambda7 mRunExpiration =
            new RollbackManagerServiceImpl$$ExternalSyntheticLambda7(this, 0);
    public final LongArrayQueue mSleepDuration = new LongArrayQueue();
    public long mRelativeBootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.rollback.RollbackManagerServiceImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ RollbackManagerServiceImpl this$0;

        public /* synthetic */ AnonymousClass1(
                RollbackManagerServiceImpl rollbackManagerServiceImpl, int i) {
            this.$r8$classId = i;
            this.this$0 = rollbackManagerServiceImpl;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intExtra;
            Object obj;
            Object obj2;
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.assertInWorkerThread();
                    if ("android.intent.action.PACKAGE_ENABLE_ROLLBACK"
                            .equals(intent.getAction())) {
                        final int intExtra2 =
                                intent.getIntExtra(
                                        "android.content.pm.extra.ENABLE_ROLLBACK_TOKEN", -1);
                        final int intExtra3 =
                                intent.getIntExtra(
                                        "android.content.pm.extra.ENABLE_ROLLBACK_SESSION_ID", -1);
                        RollbackManagerServiceImpl rollbackManagerServiceImpl = this.this$0;
                        rollbackManagerServiceImpl.assertInWorkerThread();
                        if (rollbackManagerServiceImpl.mSleepDuration.size() != 0) {
                            long removeFirst =
                                    rollbackManagerServiceImpl.mSleepDuration.removeFirst();
                            if (removeFirst > 0) {
                                rollbackManagerServiceImpl.mHandler.post(
                                        new RollbackManagerServiceImpl$$ExternalSyntheticLambda9(
                                                rollbackManagerServiceImpl, removeFirst, 1));
                            }
                        }
                        this.this$0.mHandler.post(
                                new Runnable() { // from class:
                                    // com.android.server.rollback.RollbackManagerServiceImpl$1$$ExternalSyntheticLambda0
                                    /* JADX WARN: Code restructure failed: missing block: B:101:0x0266, code lost:

                                       if (r13.hasNext() == false) goto L138;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:102:0x0268, code lost:

                                       r14 = (java.lang.String) r13.next();
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:105:0x0272, code lost:

                                       r24 = r5;
                                       r5 = r0.getPackageInfo(r14).getLongVersionCode();
                                       r8.assertInWorkerThread();
                                       r8.info.getPackages().add(new android.content.rollback.PackageRollbackInfo(new android.content.pm.VersionedPackage(r14, r7), new android.content.pm.VersionedPackage(r14, r5), new java.util.ArrayList(), new java.util.ArrayList(), false, true, new java.util.ArrayList(), r12));
                                       r6 = r6;
                                       r5 = r24;
                                       r13 = r13;
                                       r7 = 0;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:107:0x02b9, code lost:

                                       r23 = r6;
                                       android.util.Slog.e("RollbackManager", r14 + " is not installed");
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:109:0x02cf, code lost:

                                       r24 = r5;
                                       r23 = r6;
                                       r1 = r9.applicationInfo;
                                       r5 = r10.getVersionCode();
                                       r9 = r9.getLongVersionCode();
                                       r7 = r1.sourceDir;
                                       r1 = r1.splitSourceDirs;
                                       r2 = r2.rollbackImpactLevel;
                                       r8.assertInWorkerThread();
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:111:0x02e7, code lost:

                                       com.android.server.rollback.RollbackStore.backupPackageCodePath(r8, r11, r7);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:112:0x02ee, code lost:

                                       if (com.android.internal.util.ArrayUtils.isEmpty(r1) != false) goto L112;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:113:0x02f0, code lost:

                                       r7 = r1.length;
                                       r13 = 0;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:114:0x02f2, code lost:

                                       if (r13 >= r7) goto L139;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:115:0x02f4, code lost:

                                       com.android.server.rollback.RollbackStore.backupPackageCodePath(r8, r11, r1[r13]);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:116:0x02f9, code lost:

                                       r13 = r13 + 1;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:119:0x02fe, code lost:

                                       r8.info.getPackages().add(new android.content.rollback.PackageRollbackInfo(new android.content.pm.VersionedPackage(r11, r5), new android.content.pm.VersionedPackage(r11, r9), new java.util.ArrayList(), new java.util.ArrayList(), r24, false, new java.util.ArrayList(), r12));
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:120:0x0334, code lost:

                                       if (r8.info.getRollbackImpactLevel() >= r2) goto L115;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:121:0x0336, code lost:

                                       r8.info.setRollbackImpactLevel(r2);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:123:0x033f, code lost:

                                       if (r8.allPackagesEnabled() == false) goto L118;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:124:0x0341, code lost:

                                       r7 = r0.completeEnableRollback(r8);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:125:0x0347, code lost:

                                       r7 = true;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:126:0x02fc, code lost:

                                       r0 = move-exception;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:127:0x0349, code lost:

                                       android.util.Slog.e("RollbackManager", "Unable to copy package for rollback for " + r11, r0);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:129:0x035b, code lost:

                                       r23 = r6;
                                       android.util.Slog.e("RollbackManager", r11 + " is not installed");
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:130:0x0247, code lost:

                                       r5 = false;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:133:0x0220, code lost:

                                       if (r14 == false) goto L87;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:90:0x021e, code lost:

                                       if (r0.mContext.getPackageManager().getModuleInfo(r11, 0) != null) goto L86;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:91:0x0222, code lost:

                                       if (r13 != false) goto L88;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:94:0x0243, code lost:

                                       if ((131072 & r9) == 0) goto L93;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:95:0x0245, code lost:

                                       r5 = true;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:97:0x0248, code lost:

                                       r9 = r0.getPackageInfo(r11);
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:98:0x024c, code lost:

                                       if (r5 == false) goto L103;
                                    */
                                    /* JADX WARN: Code restructure failed: missing block: B:99:0x024e, code lost:

                                       r13 = com.android.server.pm.PackageManagerService.this.mApexManager.getApksInApex(r11).iterator();
                                    */
                                    /* JADX WARN: Removed duplicated region for block: B:13:0x0374  */
                                    /* JADX WARN: Removed duplicated region for block: B:9:0x0372  */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final void run() {
                                        /*
                                            Method dump skipped, instructions count: 911
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.rollback.RollbackManagerServiceImpl$1$$ExternalSyntheticLambda0.run():void");
                                    }
                                });
                        break;
                    }
                    break;
                case 1:
                    this.this$0.assertInWorkerThread();
                    if ("android.intent.action.CANCEL_ENABLE_ROLLBACK".equals(intent.getAction())) {
                        int intExtra4 =
                                intent.getIntExtra(
                                        "android.content.pm.extra.ENABLE_ROLLBACK_SESSION_ID", -1);
                        if (RollbackManagerServiceImpl.LOCAL_LOGV) {
                            ProxyManager$$ExternalSyntheticOutline0.m(
                                    intExtra4,
                                    "broadcast=ACTION_CANCEL_ENABLE_ROLLBACK id=",
                                    "RollbackManager");
                        }
                        Rollback rollbackForSession = this.this$0.getRollbackForSession(intExtra4);
                        if (rollbackForSession != null && rollbackForSession.isEnabling()) {
                            ((ArrayList) this.this$0.mRollbacks).remove(rollbackForSession);
                            this.this$0.deleteRollback(rollbackForSession, "Rollback canceled");
                            break;
                        }
                    }
                    break;
                case 2:
                    this.this$0.assertInWorkerThread();
                    if (!"android.intent.action.USER_ADDED".equals(intent.getAction())) {
                        if ("android.intent.action.USER_REMOVED".equals(intent.getAction())
                                && (intExtra =
                                                intent.getIntExtra(
                                                        "android.intent.extra.user_handle", -1))
                                        != -1) {
                            RollbackManagerServiceImpl rollbackManagerServiceImpl2 = this.this$0;
                            UserHandle of = UserHandle.of(intExtra);
                            Pair pair =
                                    (Pair)
                                            rollbackManagerServiceImpl2.mUserBroadcastReceivers.get(
                                                    Integer.valueOf(of.getIdentifier()));
                            if (pair != null
                                    && (obj = pair.first) != null
                                    && (obj2 = pair.second) != null) {
                                ((Context) obj).unregisterReceiver((BroadcastReceiver) obj2);
                                rollbackManagerServiceImpl2.mUserBroadcastReceivers.remove(
                                        Integer.valueOf(of.getIdentifier()));
                                break;
                            } else {
                                Slog.e("RollbackManager", "No receiver found for the user" + of);
                                break;
                            }
                        }
                    } else {
                        int intExtra5 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra5 != -1) {
                            this.this$0.registerUserCallbacks(UserHandle.of(intExtra5));
                            break;
                        }
                    }
                    break;
                case 3:
                    this.this$0.assertInWorkerThread();
                    String action = intent.getAction();
                    if ("android.intent.action.PACKAGE_REPLACED".equals(action)) {
                        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                        if (RollbackManagerServiceImpl.LOCAL_LOGV) {
                            Slog.v(
                                    "RollbackManager",
                                    "broadcast=ACTION_PACKAGE_REPLACED pkg=" + schemeSpecificPart);
                        }
                        this.this$0.onPackageReplaced(schemeSpecificPart);
                    }
                    if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(action)) {
                        String schemeSpecificPart2 = intent.getData().getSchemeSpecificPart();
                        Slog.i(
                                "RollbackManager",
                                "broadcast=ACTION_PACKAGE_FULLY_REMOVED pkg="
                                        + schemeSpecificPart2);
                        RollbackManagerServiceImpl rollbackManagerServiceImpl3 = this.this$0;
                        rollbackManagerServiceImpl3.assertInWorkerThread();
                        rollbackManagerServiceImpl3.expireRollbackForPackageInternal(
                                schemeSpecificPart2, "Package " + schemeSpecificPart2 + " removed");
                        break;
                    }
                    break;
                default:
                    this.this$0.assertInWorkerThread();
                    RollbackManagerServiceImpl rollbackManagerServiceImpl4 = this.this$0;
                    long j = rollbackManagerServiceImpl4.mRelativeBootTime;
                    rollbackManagerServiceImpl4.mRelativeBootTime =
                            System.currentTimeMillis() - SystemClock.elapsedRealtime();
                    RollbackManagerServiceImpl rollbackManagerServiceImpl5 = this.this$0;
                    long j2 = rollbackManagerServiceImpl5.mRelativeBootTime - j;
                    Iterator it = ((ArrayList) rollbackManagerServiceImpl5.mRollbacks).iterator();
                    while (it.hasNext()) {
                        Rollback rollback = (Rollback) it.next();
                        rollback.assertInWorkerThread();
                        Instant plusMillis = rollback.mTimestamp.plusMillis(j2);
                        rollback.assertInWorkerThread();
                        rollback.mTimestamp = plusMillis;
                        RollbackStore.saveRollback(rollback, rollback.mBackupDir);
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionCallback extends PackageInstaller.SessionCallback {
        public SessionCallback() {}

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public final void onActiveChanged(int i, boolean z) {}

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public final void onBadgingChanged(int i) {}

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public final void onCreated(int i) {}

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public final void onFinished(int i, boolean z) {
            RollbackManagerServiceImpl.this.assertInWorkerThread();
            if (RollbackManagerServiceImpl.LOCAL_LOGV) {
                Slog.v("RollbackManager", "SessionCallback.onFinished id=" + i + " success=" + z);
            }
            Rollback rollbackForSession = RollbackManagerServiceImpl.this.getRollbackForSession(i);
            if (rollbackForSession != null
                    && rollbackForSession.isEnabling()
                    && i == rollbackForSession.mOriginalSessionId) {
                if (z) {
                    if (rollbackForSession.info.isStaged()
                            || !RollbackManagerServiceImpl.this.completeEnableRollback(
                                    rollbackForSession)) {
                        return;
                    }
                    RollbackManagerServiceImpl.this.makeRollbackAvailable(rollbackForSession);
                    return;
                }
                Slog.w(
                        "RollbackManager",
                        "Delete rollback id="
                                + rollbackForSession.info.getRollbackId()
                                + " for failed session id="
                                + i);
                ((ArrayList) RollbackManagerServiceImpl.this.mRollbacks).remove(rollbackForSession);
                RollbackManagerServiceImpl.this.deleteRollback(
                        rollbackForSession, "Session " + i + " failed");
            }
        }

        @Override // android.content.pm.PackageInstaller.SessionCallback
        public final void onProgressChanged(int i, float f) {}
    }

    public RollbackManagerServiceImpl(Context context) {
        this.mContext = context;
        Installer installer = new Installer(context);
        this.mInstaller = installer;
        installer.onStart();
        this.mRollbackStore =
                new RollbackStore(
                        new File(Environment.getDataDirectory(), "rollback"),
                        new File(Environment.getDataDirectory(), "rollback-history"));
        this.mPackageHealthObserver =
                new RollbackPackageHealthObserver(context, ApexManager.getInstance());
        this.mAppDataRollbackHelper = new AppDataRollbackHelper(installer);
        Handler handler =
                new Handler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                        "RollbackManagerServiceHandler")
                                .getLooper());
        this.mHandler = handler;
        Watchdog.getInstance().addThread(handler, HANDLER_THREAD_TIMEOUT_DURATION_MILLIS);
        this.mExecutor = new HandlerExecutor(handler);
        handler.post(new RollbackManagerServiceImpl$$ExternalSyntheticLambda3(this, context, 2));
        this.mUserBroadcastReceivers = new ArrayMap();
        Iterator it =
                ((UserManager) context.getSystemService(UserManager.class))
                        .getUserHandles(true)
                        .iterator();
        while (it.hasNext()) {
            registerUserCallbacks((UserHandle) it.next());
        }
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.PACKAGE_ENABLE_ROLLBACK");
        try {
            m.addDataType("application/vnd.android.package-archive");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Slog.e("RollbackManager", "addDataType", e);
        }
        this.mContext.registerReceiver(new AnonymousClass1(this, 0), m, null, this.mHandler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CANCEL_ENABLE_ROLLBACK");
        this.mContext.registerReceiver(
                new AnonymousClass1(this, 1), intentFilter, null, this.mHandler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_ADDED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(
                new AnonymousClass1(this, 2), intentFilter2, null, this.mHandler);
        this.mContext.registerReceiver(
                new AnonymousClass1(this, 4),
                BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.TIME_SET"),
                null,
                this.mHandler);
    }

    public static SparseIntArray getExtensionVersions() {
        Map<Integer, Integer> allExtensionVersions = SdkExtensions.getAllExtensionVersions();
        SparseIntArray sparseIntArray = new SparseIntArray(allExtensionVersions.size());
        for (Integer num : allExtensionVersions.keySet()) {
            sparseIntArray.put(num.intValue(), allExtensionVersions.get(num).intValue());
        }
        return sparseIntArray;
    }

    public static void sendFailure(int i, Context context, IntentSender intentSender, String str) {
        Slog.e("RollbackManager", str);
        try {
            Intent intent = new Intent();
            intent.putExtra("android.content.rollback.extra.STATUS", i);
            intent.putExtra("android.content.rollback.extra.STATUS_MESSAGE", str);
            intentSender.sendIntent(context, 0, intent, null, null);
        } catch (IntentSender.SendIntentException unused) {
        }
    }

    public final void assertInWorkerThread() {
        Preconditions.checkState(this.mHandler.getLooper().isCurrentThread());
    }

    public final void assertNotInWorkerThread() {
        Preconditions.checkState(!this.mHandler.getLooper().isCurrentThread());
    }

    public final Object awaitResult(Supplier supplier) {
        assertNotInWorkerThread();
        try {
            return CompletableFuture.supplyAsync(supplier, this.mExecutor).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public final void awaitResult(Runnable runnable) {
        assertNotInWorkerThread();
        try {
            CompletableFuture.runAsync(runnable, this.mExecutor).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public final void blockRollbackManager(long j) {
        assertNotInWorkerThread();
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.TEST_MANAGE_ROLLBACKS", "blockRollbackManager");
        this.mHandler.post(new RollbackManagerServiceImpl$$ExternalSyntheticLambda9(this, j, 0));
    }

    public final void commitRollback(
            final int i,
            final ParceledListSlice parceledListSlice,
            final String str,
            final IntentSender intentSender) {
        assertNotInWorkerThread();
        enforceManageRollbacks("commitRollback");
        ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class))
                .checkPackage(Binder.getCallingUid(), str);
        this.mHandler.post(
                new Runnable() { // from class:
                    // com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        final Rollback rollback;
                        boolean z;
                        RollbackManagerServiceImpl rollbackManagerServiceImpl =
                                RollbackManagerServiceImpl.this;
                        int i2 = i;
                        ParceledListSlice parceledListSlice2 = parceledListSlice;
                        String str2 = str;
                        final IntentSender intentSender2 = intentSender;
                        rollbackManagerServiceImpl.getClass();
                        final List list = parceledListSlice2.getList();
                        rollbackManagerServiceImpl.assertInWorkerThread();
                        Slog.i("RollbackManager", "commitRollback id=" + i2 + " caller=" + str2);
                        rollbackManagerServiceImpl.assertInWorkerThread();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= ((ArrayList) rollbackManagerServiceImpl.mRollbacks).size()) {
                                rollback = null;
                                break;
                            }
                            rollback =
                                    (Rollback)
                                            ((ArrayList) rollbackManagerServiceImpl.mRollbacks)
                                                    .get(i3);
                            if (rollback.info.getRollbackId() == i2) {
                                break;
                            } else {
                                i3++;
                            }
                        }
                        if (rollback == null) {
                            RollbackManagerServiceImpl.sendFailure(
                                    2,
                                    rollbackManagerServiceImpl.mContext,
                                    intentSender2,
                                    "Rollback unavailable");
                            return;
                        }
                        final Context context = rollbackManagerServiceImpl.mContext;
                        rollback.assertInWorkerThread();
                        if (!rollback.isAvailable()) {
                            RollbackManagerServiceImpl.sendFailure(
                                    2, context, intentSender2, "Rollback unavailable");
                            return;
                        }
                        Iterator it = rollback.info.getPackages().iterator();
                        try {
                            do {
                                z = true;
                                if (it.hasNext()) {}
                                break;
                            } while (!((PackageRollbackInfo) it.next()).isApex());
                            break;
                            PackageManager packageManager =
                                    context.createPackageContextAsUser(
                                                    str2, 0, UserHandle.of(rollback.mUserId))
                                            .getPackageManager();
                            try {
                                PackageInstaller packageInstaller =
                                        packageManager.getPackageInstaller();
                                PackageInstaller.SessionParams sessionParams =
                                        new PackageInstaller.SessionParams(1);
                                sessionParams.setRequestDowngrade(true);
                                sessionParams.setMultiPackage();
                                if (rollback.info.isStaged()) {
                                    sessionParams.setStaged();
                                }
                                sessionParams.setInstallReason(5);
                                int createSession = packageInstaller.createSession(sessionParams);
                                PackageInstaller.Session openSession =
                                        packageInstaller.openSession(createSession);
                                ArrayList arrayList =
                                        new ArrayList(rollback.info.getPackages().size());
                                for (PackageRollbackInfo packageRollbackInfo :
                                        rollback.info.getPackages()) {
                                    arrayList.add(packageRollbackInfo.getPackageName());
                                    if (!packageRollbackInfo.isApkInApex()) {
                                        PackageInstaller.SessionParams sessionParams2 =
                                                new PackageInstaller.SessionParams(z ? 1 : 0);
                                        String str3 = rollback.mInstallerPackageName;
                                        if (TextUtils.isEmpty(str3)) {
                                            str3 =
                                                    packageManager.getInstallerPackageName(
                                                            packageRollbackInfo.getPackageName());
                                        }
                                        if (str3 != null) {
                                            sessionParams2.setInstallerPackageName(str3);
                                        }
                                        sessionParams2.setRequestDowngrade(z);
                                        sessionParams2.setRequiredInstalledVersionCode(
                                                packageRollbackInfo
                                                        .getVersionRolledBackFrom()
                                                        .getLongVersionCode());
                                        sessionParams2.setInstallReason(5);
                                        if (rollback.info.isStaged()) {
                                            sessionParams2.setStaged();
                                        }
                                        if (packageRollbackInfo.isApex()) {
                                            sessionParams2.setInstallAsApex();
                                        }
                                        int createSession2 =
                                                packageInstaller.createSession(sessionParams2);
                                        PackageInstaller.Session openSession2 =
                                                packageInstaller.openSession(createSession2);
                                        PackageManager packageManager2 = packageManager;
                                        File[] listFiles =
                                                new File(
                                                                rollback.mBackupDir,
                                                                packageRollbackInfo
                                                                        .getPackageName())
                                                        .listFiles();
                                        if (listFiles == null || listFiles.length == 0) {
                                            listFiles = null;
                                        }
                                        if (listFiles == null) {
                                            RollbackManagerServiceImpl.sendFailure(
                                                    1,
                                                    context,
                                                    intentSender2,
                                                    "Backup copy of package: "
                                                            + packageRollbackInfo.getPackageName()
                                                            + " is inaccessible");
                                            return;
                                        }
                                        int length = listFiles.length;
                                        int i4 = 0;
                                        while (i4 < length) {
                                            File file = listFiles[i4];
                                            File[] fileArr = listFiles;
                                            ParcelFileDescriptor open =
                                                    ParcelFileDescriptor.open(file, 268435456);
                                            try {
                                                long clearCallingIdentity =
                                                        Binder.clearCallingIdentity();
                                                PackageInstaller packageInstaller2 =
                                                        packageInstaller;
                                                try {
                                                    try {
                                                        openSession2.stageViaHardLink(
                                                                file.getAbsolutePath());
                                                    } catch (Throwable th) {
                                                        Binder.restoreCallingIdentity(
                                                                clearCallingIdentity);
                                                        throw th;
                                                    }
                                                } catch (Exception unused) {
                                                    openSession2.write(
                                                            file.getName(),
                                                            0L,
                                                            file.length(),
                                                            open);
                                                }
                                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                                if (open != null) {
                                                    open.close();
                                                }
                                                i4++;
                                                listFiles = fileArr;
                                                packageInstaller = packageInstaller2;
                                            } finally {
                                            }
                                        }
                                        openSession.addChildSessionId(createSession2);
                                        packageManager = packageManager2;
                                        packageInstaller = packageInstaller;
                                        z = true;
                                    }
                                }
                                RescueParty.resetDeviceConfigForPackages(arrayList);
                                LocalIntentReceiver localIntentReceiver =
                                        new LocalIntentReceiver(
                                                new Consumer() { // from class:
                                                    // com.android.server.rollback.Rollback$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        final Rollback rollback2 = Rollback.this;
                                                        final Context context2 = context;
                                                        final IntentSender intentSender3 =
                                                                intentSender2;
                                                        final List list2 = list;
                                                        final Intent intent = (Intent) obj;
                                                        rollback2.getClass();
                                                        rollback2.mHandler.post(
                                                                new Runnable() { // from class:
                                                                    // com.android.server.rollback.Rollback$$ExternalSyntheticLambda1
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        Rollback rollback3 =
                                                                                Rollback.this;
                                                                        Intent intent2 = intent;
                                                                        Context context3 = context2;
                                                                        IntentSender intentSender4 =
                                                                                intentSender3;
                                                                        List list3 = list2;
                                                                        rollback3
                                                                                .assertInWorkerThread();
                                                                        if (intent2.getIntExtra(
                                                                                        "android.content.pm.extra.STATUS",
                                                                                        1)
                                                                                != 0) {
                                                                            rollback3.setState(
                                                                                    1,
                                                                                    "Commit"
                                                                                        + " failed");
                                                                            rollback3
                                                                                            .mRestoreUserDataInProgress =
                                                                                    false;
                                                                            rollback3.info
                                                                                    .setCommittedSessionId(
                                                                                            -1);
                                                                            RollbackManagerServiceImpl
                                                                                    .sendFailure(
                                                                                            3,
                                                                                            context3,
                                                                                            intentSender4,
                                                                                            "Rollback"
                                                                                                + " downgrade"
                                                                                                + " install"
                                                                                                + " failed:"
                                                                                                + " "
                                                                                                    + intent2
                                                                                                            .getStringExtra(
                                                                                                                    "android.content.pm.extra.STATUS_MESSAGE"));
                                                                            return;
                                                                        }
                                                                        if (!rollback3.info
                                                                                .isStaged()) {
                                                                            rollback3
                                                                                            .mRestoreUserDataInProgress =
                                                                                    false;
                                                                        }
                                                                        rollback3
                                                                                .info
                                                                                .getCausePackages()
                                                                                .addAll(list3);
                                                                        Iterator it2 =
                                                                                rollback3
                                                                                        .info
                                                                                        .getPackages()
                                                                                        .iterator();
                                                                        while (it2.hasNext()) {
                                                                            RollbackStore
                                                                                    .removeFile(
                                                                                            new File(
                                                                                                    rollback3
                                                                                                            .mBackupDir,
                                                                                                    ((PackageRollbackInfo)
                                                                                                                    it2
                                                                                                                            .next())
                                                                                                            .getPackageName()));
                                                                        }
                                                                        RollbackStore.saveRollback(
                                                                                rollback3,
                                                                                rollback3
                                                                                        .mBackupDir);
                                                                        try {
                                                                            Intent intent3 =
                                                                                    new Intent();
                                                                            intent3.putExtra(
                                                                                    "android.content.rollback.extra.STATUS",
                                                                                    0);
                                                                            intentSender4
                                                                                    .sendIntent(
                                                                                            context3,
                                                                                            0,
                                                                                            intent3,
                                                                                            null,
                                                                                            null);
                                                                        } catch (
                                                                                IntentSender
                                                                                                .SendIntentException
                                                                                        unused2) {
                                                                        }
                                                                        Intent intent4 =
                                                                                new Intent(
                                                                                        "android.intent.action.ROLLBACK_COMMITTED");
                                                                        Iterator it3 =
                                                                                ((UserManager)
                                                                                                context3
                                                                                                        .getSystemService(
                                                                                                                UserManager
                                                                                                                        .class))
                                                                                        .getUserHandles(
                                                                                                true)
                                                                                        .iterator();
                                                                        while (it3.hasNext()) {
                                                                            context3
                                                                                    .sendBroadcastAsUser(
                                                                                            intent4,
                                                                                            (UserHandle)
                                                                                                    it3
                                                                                                            .next(),
                                                                                            "android.permission.MANAGE_ROLLBACKS");
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                rollback.setState(3, "");
                                rollback.info.setCommittedSessionId(createSession);
                                rollback.mRestoreUserDataInProgress = true;
                                openSession.commit(
                                        new IntentSender(localIntentReceiver.mLocalSender));
                                return;
                            } catch (IOException e) {
                                Slog.e("RollbackManager", "Rollback failed", e);
                                RollbackManagerServiceImpl.sendFailure(
                                        1, context, intentSender2, "IOException: " + e.toString());
                                return;
                            }
                        } catch (PackageManager.NameNotFoundException unused2) {
                            RollbackManagerServiceImpl.sendFailure(
                                    1, context, intentSender2, "Invalid callerPackageName");
                            return;
                        }
                        for (int i5 = 0; i5 < rollback.mExtensionVersions.size(); i5++) {
                            if (SdkExtensions.getExtensionVersion(
                                            rollback.mExtensionVersions.keyAt(i5))
                                    > rollback.mExtensionVersions.valueAt(i5)) {
                                if (Rollback.extensionVersionReductionWouldViolateConstraint(
                                        rollback.mExtensionVersions,
                                        (PackageManagerInternal)
                                                LocalServices.getService(
                                                        PackageManagerInternal.class))) {
                                    RollbackManagerServiceImpl.sendFailure(
                                            1,
                                            context,
                                            intentSender2,
                                            "Rollback may violate a minExtensionVersion"
                                                + " constraint");
                                    return;
                                }
                            }
                        }
                    }
                });
    }

    public final boolean completeEnableRollback(Rollback rollback) {
        assertInWorkerThread();
        if (LOCAL_LOGV) {
            Slog.v("RollbackManager", "completeEnableRollback id=" + rollback.info.getRollbackId());
        }
        if (rollback.allPackagesEnabled()) {
            rollback.assertInWorkerThread();
            RollbackStore.saveRollback(rollback, rollback.mBackupDir);
            return true;
        }
        Slog.e("RollbackManager", "Failed to enable rollback for all packages in session.");
        ((ArrayList) this.mRollbacks).remove(rollback);
        deleteRollback(rollback, "Failed to enable rollback for all packages in session");
        return false;
    }

    public final void deleteRollback(Rollback rollback, String str) {
        assertInWorkerThread();
        AppDataRollbackHelper appDataRollbackHelper = this.mAppDataRollbackHelper;
        rollback.assertInWorkerThread();
        ArraySet arraySet = new ArraySet();
        boolean z = false;
        for (PackageRollbackInfo packageRollbackInfo : rollback.info.getPackages()) {
            List snapshottedUsers = packageRollbackInfo.getSnapshottedUsers();
            if (packageRollbackInfo.isApex()) {
                arraySet.addAll(snapshottedUsers);
                z = true;
            } else {
                for (int i = 0; i < snapshottedUsers.size(); i++) {
                    int intValue = ((Integer) snapshottedUsers.get(i)).intValue();
                    int rollbackId = rollback.info.getRollbackId();
                    appDataRollbackHelper.getClass();
                    try {
                        Installer installer = appDataRollbackHelper.mInstaller;
                        String packageName = packageRollbackInfo.getPackageName();
                        if (installer.checkBeforeRemote()) {
                            try {
                                installer.mInstalld.destroyAppDataSnapshot(
                                        null, packageName, intValue, 0L, rollbackId, 3);
                            } catch (Exception e) {
                                Installer.InstallerException.from(e);
                                throw null;
                            }
                        } else {
                            continue;
                        }
                    } catch (Installer.InstallerException e2) {
                        Slog.e(
                                "RollbackManager",
                                "Unable to delete app data snapshot for "
                                        + packageRollbackInfo.getPackageName(),
                                e2);
                    }
                }
            }
        }
        if (z) {
            int rollbackId2 = rollback.info.getRollbackId();
            ApexManager.ApexManagerImpl apexManagerImpl =
                    (ApexManager.ApexManagerImpl) appDataRollbackHelper.mApexManager;
            apexManagerImpl.getClass();
            try {
                apexManagerImpl.waitForApexService().destroyDeSnapshots(rollbackId2);
            } catch (Exception e3) {
                Slog.e("ApexManager", e3.getMessage(), e3);
            }
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                int intValue2 = ((Integer) it.next()).intValue();
                int rollbackId3 = rollback.info.getRollbackId();
                if (!appDataRollbackHelper.isUserCredentialLocked(intValue2)) {
                    ApexManager.ApexManagerImpl apexManagerImpl2 =
                            (ApexManager.ApexManagerImpl) appDataRollbackHelper.mApexManager;
                    apexManagerImpl2.getClass();
                    try {
                        apexManagerImpl2
                                .waitForApexService()
                                .destroyCeSnapshots(intValue2, rollbackId3);
                    } catch (Exception e4) {
                        Slog.e("ApexManager", e4.getMessage(), e4);
                    }
                }
            }
        }
        RollbackStore.removeFile(rollback.mBackupDir);
        rollback.setState(4, str);
        RollbackStore rollbackStore = this.mRollbackStore;
        rollbackStore.getClass();
        rollback.assertInWorkerThread();
        RollbackStore.saveRollback(
                rollback,
                new File(
                        rollbackStore.mRollbackHistoryDir,
                        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                Integer.toString(rollback.info.getRollbackId()),
                                PackageManagerShellCommandDataLoader.STDIN_PATH,
                                Long.toHexString(rollback.mTimestamp.getEpochSecond()))));
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        assertNotInWorkerThread();
        if (DumpUtils.checkDumpPermission(this.mContext, "RollbackManager", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            awaitResult(
                    new RollbackManagerServiceImpl$$ExternalSyntheticLambda3(
                            this, indentingPrintWriter, 1));
            PackageWatchdog packageWatchdog = PackageWatchdog.getInstance(this.mContext);
            packageWatchdog.getClass();
            indentingPrintWriter.println("Package Watchdog status");
            indentingPrintWriter.increaseIndent();
            synchronized (packageWatchdog.mLock) {
                try {
                    for (String str : packageWatchdog.mAllObservers.keySet()) {
                        indentingPrintWriter.println("Observer name: " + str);
                        indentingPrintWriter.increaseIndent();
                        ((PackageWatchdog.ObserverInternal) packageWatchdog.mAllObservers.get(str))
                                .dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void enforceManageRollbacks(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_ROLLBACKS") != 0
                && this.mContext.checkCallingOrSelfPermission(
                                "android.permission.TEST_MANAGE_ROLLBACKS")
                        != 0) {
            throw new SecurityException(
                    str.concat(
                            " requires android.permission.MANAGE_ROLLBACKS or"
                                + " android.permission.TEST_MANAGE_ROLLBACKS"));
        }
    }

    public final void expireRollbackForPackage(String str) {
        assertNotInWorkerThread();
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.TEST_MANAGE_ROLLBACKS", "expireRollbackForPackage");
        awaitResult(new RollbackManagerServiceImpl$$ExternalSyntheticLambda3(this, str, 0));
    }

    public final void expireRollbackForPackageInternal(String str, String str2) {
        assertInWorkerThread();
        Iterator it = ((ArrayList) this.mRollbacks).iterator();
        while (it.hasNext()) {
            Rollback rollback = (Rollback) it.next();
            rollback.assertInWorkerThread();
            Iterator it2 = rollback.info.getPackages().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                if (((PackageRollbackInfo) it2.next()).getPackageName().equals(str)) {
                    it.remove();
                    deleteRollback(rollback, str2);
                    break;
                }
            }
        }
    }

    public final ParceledListSlice getAvailableRollbacks() {
        assertNotInWorkerThread();
        enforceManageRollbacks("getAvailableRollbacks");
        return (ParceledListSlice)
                awaitResult(new RollbackManagerServiceImpl$$ExternalSyntheticLambda2(this, 1));
    }

    public final PackageInfo getPackageInfo(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            return packageManager.getPackageInfo(str, 4194304);
        } catch (PackageManager.NameNotFoundException unused) {
            return packageManager.getPackageInfo(str, 1073741824);
        }
    }

    public final ParceledListSlice getRecentlyCommittedRollbacks() {
        assertNotInWorkerThread();
        enforceManageRollbacks("getRecentlyCommittedRollbacks");
        return (ParceledListSlice)
                awaitResult(new RollbackManagerServiceImpl$$ExternalSyntheticLambda2(this, 0));
    }

    public final Rollback getRollbackForSession(int i) {
        assertInWorkerThread();
        for (int i2 = 0; i2 < ((ArrayList) this.mRollbacks).size(); i2++) {
            Rollback rollback = (Rollback) ((ArrayList) this.mRollbacks).get(i2);
            if (rollback.mOriginalSessionId != i) {
                for (int i3 : rollback.mPackageSessionIds) {
                    if (i3 != i) {}
                }
            }
            return rollback;
        }
        return null;
    }

    public final void makeRollbackAvailable(Rollback rollback) {
        assertInWorkerThread();
        Slog.i("RollbackManager", "makeRollbackAvailable id=" + rollback.info.getRollbackId());
        rollback.assertInWorkerThread();
        rollback.assertInWorkerThread();
        if (rollback.mState == 4) {
            Slog.w("RollbackManager", "Cannot make deleted rollback available.");
        } else {
            rollback.setState(1, "");
            rollback.mTimestamp = Instant.now();
            RollbackStore.saveRollback(rollback, rollback.mBackupDir);
        }
        RollbackPackageHealthObserver rollbackPackageHealthObserver = this.mPackageHealthObserver;
        rollbackPackageHealthObserver.mHandler.post(
                new RollbackPackageHealthObserver$$ExternalSyntheticLambda0(
                        rollbackPackageHealthObserver, rollback.info));
        if (!Flags.recoverabilityDetection()) {
            RollbackPackageHealthObserver rollbackPackageHealthObserver2 =
                    this.mPackageHealthObserver;
            PackageWatchdog.getInstance(rollbackPackageHealthObserver2.mContext)
                    .startObservingHealth(
                            rollbackPackageHealthObserver2,
                            rollback.getPackageNames(),
                            this.mRollbackLifetimeDurationInMillis);
        } else if (rollback.info.getRollbackImpactLevel() == 0) {
            RollbackPackageHealthObserver rollbackPackageHealthObserver3 =
                    this.mPackageHealthObserver;
            PackageWatchdog.getInstance(rollbackPackageHealthObserver3.mContext)
                    .startObservingHealth(
                            rollbackPackageHealthObserver3,
                            rollback.getPackageNames(),
                            this.mRollbackLifetimeDurationInMillis);
        }
        runExpiration();
    }

    public final int notifyStagedSession(final int i) {
        assertNotInWorkerThread();
        if (Binder.getCallingUid() == 1000) {
            return ((Integer)
                            awaitResult(
                                    new Supplier() { // from class:
                                                     // com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda5
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            RollbackManagerServiceImpl rollbackManagerServiceImpl =
                                                    RollbackManagerServiceImpl.this;
                                            int i2 = i;
                                            rollbackManagerServiceImpl.assertInWorkerThread();
                                            Rollback rollbackForSession =
                                                    rollbackManagerServiceImpl
                                                            .getRollbackForSession(i2);
                                            return Integer.valueOf(
                                                    rollbackForSession != null
                                                            ? rollbackForSession.info
                                                                    .getRollbackId()
                                                            : -1);
                                        }
                                    }))
                    .intValue();
        }
        throw new SecurityException("notifyStagedSession may only be called by the system.");
    }

    public final void onPackageReplaced(String str) {
        long j;
        assertInWorkerThread();
        try {
            j = getPackageInfo(str).getLongVersionCode();
        } catch (PackageManager.NameNotFoundException unused) {
            j = -1;
        }
        Iterator it = ((ArrayList) this.mRollbacks).iterator();
        while (it.hasNext()) {
            Rollback rollback = (Rollback) it.next();
            if (rollback.isAvailable()) {
                rollback.assertInWorkerThread();
                Iterator it2 = rollback.info.getPackages().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        PackageRollbackInfo packageRollbackInfo = (PackageRollbackInfo) it2.next();
                        if (packageRollbackInfo.getPackageName().equals(str)
                                && packageRollbackInfo
                                                .getVersionRolledBackFrom()
                                                .getLongVersionCode()
                                        != j) {
                            it.remove();
                            deleteRollback(rollback, "Package " + str + " replaced");
                            break;
                        }
                    }
                }
            }
        }
    }

    public final void registerUserCallbacks(UserHandle userHandle) {
        Context context;
        try {
            Context context2 = this.mContext;
            context = context2.createPackageContextAsUser(context2.getPackageName(), 0, userHandle);
        } catch (PackageManager.NameNotFoundException unused) {
            context = null;
        }
        if (context == null) {
            Slog.e("RollbackManager", "Unable to register user callbacks for user " + userHandle);
            return;
        }
        context.getPackageManager()
                .getPackageInstaller()
                .registerSessionCallback(new SessionCallback(), this.mHandler);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme("package");
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, 3);
        context.registerReceiver(anonymousClass1, intentFilter, null, this.mHandler);
        this.mUserBroadcastReceivers.put(
                Integer.valueOf(userHandle.getIdentifier()), new Pair(context, anonymousClass1));
    }

    public final void reloadPersistedData() {
        assertNotInWorkerThread();
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.TEST_MANAGE_ROLLBACKS", "reloadPersistedData");
        awaitResult(new RollbackManagerServiceImpl$$ExternalSyntheticLambda7(this, 1));
    }

    public final void runExpiration() {
        if (!Flags.rollbackLifetime()) {
            this.mHandler.removeCallbacks(this.mRunExpiration);
            assertInWorkerThread();
            Instant now = Instant.now();
            Iterator it = ((ArrayList) this.mRollbacks).iterator();
            Instant instant = null;
            while (it.hasNext()) {
                Rollback rollback = (Rollback) it.next();
                if (rollback.isAvailable() || rollback.isCommitted()) {
                    rollback.assertInWorkerThread();
                    Instant instant2 = rollback.mTimestamp;
                    if (!now.isBefore(
                            instant2.plusMillis(this.mRollbackLifetimeDurationInMillis))) {
                        Slog.i(
                                "RollbackManager",
                                "runExpiration id=" + rollback.info.getRollbackId());
                        it.remove();
                        deleteRollback(rollback, "Expired by timeout");
                    } else if (instant == null || instant.isAfter(instant2)) {
                        instant = instant2;
                    }
                }
            }
            if (instant != null) {
                this.mHandler.postDelayed(
                        this.mRunExpiration,
                        now.until(
                                instant.plusMillis(this.mRollbackLifetimeDurationInMillis),
                                ChronoUnit.MILLIS));
                return;
            }
            return;
        }
        this.mHandler.removeCallbacks(this.mRunExpiration);
        assertInWorkerThread();
        Instant now2 = Instant.now();
        Iterator it2 = ((ArrayList) this.mRollbacks).iterator();
        long j = 0;
        while (it2.hasNext()) {
            Rollback rollback2 = (Rollback) it2.next();
            if (rollback2.isAvailable() || rollback2.isCommitted()) {
                rollback2.assertInWorkerThread();
                long j2 = rollback2.mRollbackLifetimeMillis;
                if (j2 <= 0) {
                    j2 = this.mRollbackLifetimeDurationInMillis;
                }
                rollback2.assertInWorkerThread();
                Instant plusMillis = rollback2.mTimestamp.plusMillis(j2);
                if (now2.isBefore(plusMillis)) {
                    long until = now2.until(plusMillis, ChronoUnit.MILLIS);
                    if (j == 0 || until < j) {
                        j = until;
                    }
                } else {
                    Slog.i("RollbackManager", "runExpiration id=" + rollback2.info.getRollbackId());
                    it2.remove();
                    deleteRollback(rollback2, "Expired by timeout");
                }
            }
        }
        if (j != 0) {
            this.mHandler.postDelayed(this.mRunExpiration, j);
        }
    }

    public final void snapshotAndRestoreUserData(
            final String str,
            final int[] iArr,
            final int i,
            long j,
            final String str2,
            final int i2) {
        assertNotInWorkerThread();
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException(
                    "snapshotAndRestoreUserData may only be called by the system.");
        }
        this.mHandler.post(
                new Runnable() { // from class:
                    // com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i3;
                        boolean z;
                        int i4;
                        int i5;
                        int i6;
                        int i7;
                        RollbackManagerServiceImpl rollbackManagerServiceImpl =
                                RollbackManagerServiceImpl.this;
                        String str3 = str;
                        int[] iArr2 = iArr;
                        int i8 = i;
                        String str4 = str2;
                        int i9 = i2;
                        rollbackManagerServiceImpl.assertInWorkerThread();
                        rollbackManagerServiceImpl.assertInWorkerThread();
                        if (RollbackManagerServiceImpl.LOCAL_LOGV) {
                            StringBuilder m =
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "snapshotUserData pkg=", str3, " users=");
                            m.append(Arrays.toString(iArr2));
                            Slog.v("RollbackManager", m.toString());
                        }
                        int i10 = 0;
                        while (i10 < ((ArrayList) rollbackManagerServiceImpl.mRollbacks).size()) {
                            Rollback rollback =
                                    (Rollback)
                                            ((ArrayList) rollbackManagerServiceImpl.mRollbacks)
                                                    .get(i10);
                            AppDataRollbackHelper appDataRollbackHelper =
                                    rollbackManagerServiceImpl.mAppDataRollbackHelper;
                            rollback.assertInWorkerThread();
                            if (rollback.isEnabling()) {
                                Iterator it = rollback.info.getPackages().iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    PackageRollbackInfo packageRollbackInfo =
                                            (PackageRollbackInfo) it.next();
                                    if (packageRollbackInfo.getPackageName().equals(str3)) {
                                        if (packageRollbackInfo.getRollbackDataPolicy() == 0) {
                                            int rollbackId = rollback.info.getRollbackId();
                                            appDataRollbackHelper.getClass();
                                            int length = iArr2.length;
                                            int i11 = 0;
                                            while (i11 < length) {
                                                int i12 = iArr2[i11];
                                                if (appDataRollbackHelper.isUserCredentialLocked(
                                                        i12)) {
                                                    i6 = length;
                                                    i5 = i9;
                                                    Slog.v(
                                                            "RollbackManager",
                                                            "User: "
                                                                    + i12
                                                                    + " isn't unlocked, skipping CE"
                                                                    + " userdata backup.");
                                                    packageRollbackInfo.addPendingBackup(i12);
                                                    i7 = 1;
                                                } else {
                                                    i5 = i9;
                                                    i6 = length;
                                                    i7 = 3;
                                                }
                                                appDataRollbackHelper.doSnapshot(
                                                        packageRollbackInfo, i12, rollbackId, i7);
                                                i11++;
                                                length = i6;
                                                i9 = i5;
                                            }
                                            i4 = i9;
                                            List snapshottedUsers =
                                                    packageRollbackInfo.getSnapshottedUsers();
                                            for (int i13 : iArr2) {
                                                snapshottedUsers.add(Integer.valueOf(i13));
                                            }
                                            RollbackStore.saveRollback(
                                                    rollback, rollback.mBackupDir);
                                        }
                                    }
                                }
                            }
                            i4 = i9;
                            i10++;
                            i9 = i4;
                        }
                        int i14 = i9;
                        rollbackManagerServiceImpl.assertInWorkerThread();
                        if (RollbackManagerServiceImpl.LOCAL_LOGV) {
                            StringBuilder m2 =
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "restoreUserData pkg=", str3, " users=");
                            m2.append(Arrays.toString(iArr2));
                            Slog.v("RollbackManager", m2.toString());
                        }
                        int i15 = 0;
                        while (true) {
                            if (i15 >= ((ArrayList) rollbackManagerServiceImpl.mRollbacks).size()) {
                                break;
                            }
                            Rollback rollback2 =
                                    (Rollback)
                                            ((ArrayList) rollbackManagerServiceImpl.mRollbacks)
                                                    .get(i15);
                            AppDataRollbackHelper appDataRollbackHelper2 =
                                    rollbackManagerServiceImpl.mAppDataRollbackHelper;
                            rollback2.assertInWorkerThread();
                            rollback2.assertInWorkerThread();
                            if (rollback2.mRestoreUserDataInProgress) {
                                for (PackageRollbackInfo packageRollbackInfo2 :
                                        rollback2.info.getPackages()) {
                                    if (packageRollbackInfo2.getPackageName().equals(str3)) {
                                        boolean z2 = false;
                                        for (int i16 : iArr2) {
                                            int rollbackId2 = rollback2.info.getRollbackId();
                                            appDataRollbackHelper2.getClass();
                                            List pendingBackups =
                                                    packageRollbackInfo2.getPendingBackups();
                                            ArrayList pendingRestores =
                                                    packageRollbackInfo2.getPendingRestores();
                                            if (pendingBackups != null
                                                    && pendingBackups.indexOf(Integer.valueOf(i16))
                                                            != -1) {
                                                pendingBackups.remove(
                                                        pendingBackups.indexOf(
                                                                Integer.valueOf(i16)));
                                            } else if (appDataRollbackHelper2
                                                    .isUserCredentialLocked(i16)) {
                                                pendingRestores.add(
                                                        new PackageRollbackInfo.RestoreInfo(
                                                                i16, i8, str4));
                                            } else {
                                                i3 = 3;
                                                z = false;
                                                appDataRollbackHelper2.doRestoreOrWipe(
                                                        packageRollbackInfo2,
                                                        i16,
                                                        rollbackId2,
                                                        i8,
                                                        str4,
                                                        i3);
                                                z2 |= z;
                                            }
                                            i3 = 1;
                                            z = true;
                                            appDataRollbackHelper2.doRestoreOrWipe(
                                                    packageRollbackInfo2,
                                                    i16,
                                                    rollbackId2,
                                                    i8,
                                                    str4,
                                                    i3);
                                            z2 |= z;
                                        }
                                        if (z2) {
                                            RollbackStore.saveRollback(
                                                    rollback2, rollback2.mBackupDir);
                                        }
                                    }
                                }
                            }
                            i15++;
                        }
                        if (i14 > 0) {
                            PackageManagerService packageManagerService =
                                    ((PackageManagerService.PackageManagerInternalImpl)
                                                    ((PackageManagerInternal)
                                                            LocalServices.getService(
                                                                    PackageManagerInternal.class)))
                                            .mService;
                            packageManagerService.getClass();
                            PackageManagerServiceUtils.enforceSystemOrRoot(
                                    "Only the system is allowed to finish installs");
                            Trace.asyncTraceEnd(262144L, "restore", i14);
                            Handler handler = packageManagerService.mHandler;
                            handler.sendMessage(handler.obtainMessage(9, i14, 0));
                        }
                    }
                });
    }

    public final void updateRollbackLifetimeDurationInMillis() {
        assertInWorkerThread();
        long j = DEFAULT_ROLLBACK_LIFETIME_DURATION_MILLIS;
        long j2 = DeviceConfig.getLong("rollback_boot", "rollback_lifetime_in_millis", j);
        this.mRollbackLifetimeDurationInMillis = j2;
        if (j2 < 0) {
            this.mRollbackLifetimeDurationInMillis = j;
        }
        Slog.d(
                "RollbackManager",
                "mRollbackLifetimeDurationInMillis=" + this.mRollbackLifetimeDurationInMillis);
        runExpiration();
    }
}
