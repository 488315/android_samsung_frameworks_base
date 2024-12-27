package com.android.server.appop;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.AppOpsManagerInternal;
import android.app.AsyncNotedAppOp;
import android.app.RuntimeAppOpAccessMessage;
import android.app.SyncNotedAppOp;
import android.app.admin.DevicePolicyManagerInternal;
import android.content.AttributionSource;
import android.content.AttributionSourceState;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PermissionInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.SensorPrivacyManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.PackageTagsList;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.KeyValueListParser;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.Xml;
import com.android.internal.app.IAppOpsActiveCallback;
import com.android.internal.app.IAppOpsAsyncNotedCallback;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsNotedCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.app.IAppOpsStartedCallback;
import com.android.internal.app.MessageSamplingConfig;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.os.Clock;
import com.android.internal.pm.pkg.component.ParsedAttribution;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.LockGuard;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.appop.AppOpsUidStateTrackerImpl;
import com.android.server.appop.AttributedOp;
import com.android.server.appop.AudioRestrictionManager;
import com.android.server.appop.DiscreteRegistry;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.companion.virtual.VirtualDeviceManagerService;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.AccessCheckDelegate$AccessCheckDelegateImpl;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageState;
import com.samsung.android.knox.analytics.service.KnoxAnalyticsSystemService;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.privacydashboard.PermissionAccessInformationController;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppOpsService extends IAppOpsService.Stub {
    public static final boolean DEBUG;
    public static final boolean DEBUG_MID;
    public static final int[] NON_PACKAGE_UIDS;
    public static final int[] OPS_RESTRICTED_ON_SUSPEND;
    public static final long WRITE_DELAY;
    public final DevicePolicyManagerInternal dpmi;
    public int mAcceptableLeftDistance;
    public final ArrayMap mActiveWatchers;
    AppOpsCheckingServiceInterface mAppOpsCheckingService;
    public final AppOpsManagerInternalImpl mAppOpsManagerInternal;
    AppOpsRestrictions mAppOpsRestrictions;
    public final ArrayMap mAsyncOpWatchers;
    public final AudioRestrictionManager mAudioRestrictionManager;
    public volatile CheckOpsDelegateDispatcher mCheckOpsDelegateDispatcher;
    public RuntimeAppOpAccessMessage mCollectedRuntimePermissionMessage;
    final Constants mConstants;
    public final Context mContext;
    public boolean mFastWriteScheduled;
    public final Handler mHandler;
    public volatile HistoricalRegistry mHistoricalRegistry;
    public IAppOpsCallback mIgnoredCallback;
    public final AttributedOp.InProgressStartOpEventPool mInProgressStartOpEventPool;
    public final SparseArray mKnownDeviceIds;
    public float mMessagesCollectedCount;
    public final ArrayMap mModeWatchers;
    public final ArrayMap mNotedWatchers;
    public final AnonymousClass3 mOnPackageUpdatedReceiver;
    public final AttributedOp.OpEventProxyInfoPool mOpEventProxyInfoPool;
    public final ArrayMap mOpGlobalRestrictions;
    public final SparseArray mOpModeWatchers;
    public final ArrayMap mOpUserRestrictions;
    public PackageManagerInternal mPackageManagerInternal;
    public PackageManagerLocal mPackageManagerLocal;
    public final ArrayMap mPackageModeWatchers;
    public final PermissionAccessInformationController mPermissionAccessInformationController;
    public final IPlatformCompat mPlatformCompat;
    public SparseIntArray mProfileOwners;
    public ArraySet mRarelyUsedPackages;
    public final AppOpsRecentAccessPersistence mRecentAccessPersistence;
    public final AtomicFile mRecentAccessesFile;
    public int mSampledAppOpCode;
    public String mSampledPackage;
    public int mSamplingStrategy;
    public SensorPrivacyManager mSensorPrivacyManager;
    public final ArrayMap mStartedWatchers;
    public final AtomicFile mStorageFile;
    public final SparseArray mSwitchedOps;
    public AppOpsUidStateTrackerImpl mUidStateTracker;
    final SparseArray mUidStates;
    public final ArrayMap mUnforwardedAsyncNotedOps;
    public UserManagerInternal mUserManagerInternal;
    public VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;
    public final AnonymousClass1 mWriteRunner;
    public boolean mWriteScheduled;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.appop.AppOpsService$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppOpsService this$0;

        public /* synthetic */ AnonymousClass1(AppOpsService appOpsService, int i) {
            this.$r8$classId = i;
            this.this$0 = appOpsService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0) {
                        AppOpsService appOpsService = this.this$0;
                        appOpsService.mWriteScheduled = false;
                        appOpsService.mFastWriteScheduled = false;
                        new AsyncTask() { // from class: com.android.server.appop.AppOpsService.1.1
                            @Override // android.os.AsyncTask
                            public final Object doInBackground(Object[] objArr) {
                                AnonymousClass1.this.this$0.writeRecentAccesses();
                                return null;
                            }
                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                    }
                    return;
                default:
                    List packageListAndResample = this.this$0.getPackageListAndResample();
                    final AppOpsService appOpsService2 = this.this$0;
                    final ArraySet arraySet = new ArraySet(packageListAndResample);
                    AppOpsManager appOpsManager = (AppOpsManager) appOpsService2.mContext.getSystemService(AppOpsManager.class);
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < 149; i++) {
                        if (appOpsService2.shouldCollectNotes(i)) {
                            arrayList.add(AppOpsManager.opToPublicName(i));
                        }
                    }
                    appOpsManager.getHistoricalOps(new AppOpsManager.HistoricalOpsRequest.Builder(Math.max(Instant.now().minus(7L, (TemporalUnit) ChronoUnit.DAYS).toEpochMilli(), 0L), Long.MAX_VALUE).setOpNames(arrayList).setFlags(9).build(), AsyncTask.THREAD_POOL_EXECUTOR, new Consumer() { // from class: com.android.server.appop.AppOpsService.9
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            AppOpsManager.HistoricalOps historicalOps = (AppOpsManager.HistoricalOps) obj;
                            int uidCount = historicalOps.getUidCount();
                            for (int i2 = 0; i2 < uidCount; i2++) {
                                AppOpsManager.HistoricalUidOps uidOpsAt = historicalOps.getUidOpsAt(i2);
                                int packageCount = uidOpsAt.getPackageCount();
                                for (int i3 = 0; i3 < packageCount; i3++) {
                                    String packageName = uidOpsAt.getPackageOpsAt(i3).getPackageName();
                                    if (arraySet.contains(packageName) && uidOpsAt.getPackageOpsAt(i3).getOpCount() != 0) {
                                        arraySet.remove(packageName);
                                    }
                                }
                            }
                            synchronized (this) {
                                try {
                                    int size = AppOpsService.this.mRarelyUsedPackages.size();
                                    for (int i4 = 0; i4 < size; i4++) {
                                        arraySet.add((String) AppOpsService.this.mRarelyUsedPackages.valueAt(i4));
                                    }
                                    AppOpsService.this.mRarelyUsedPackages = arraySet;
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    });
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.appop.AppOpsService$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }

        public final void onUidModeChanged(int i, int i2, String str) {
            AppOpsService appOpsService = AppOpsService.this;
            appOpsService.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda15(2), appOpsService, Integer.valueOf(i2), Integer.valueOf(i), Boolean.FALSE, str));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.appop.AppOpsService$3, reason: invalid class name */
    public final class AnonymousClass3 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppOpsService this$0;

        public /* synthetic */ AnonymousClass3(AppOpsService appOpsService, int i) {
            this.$r8$classId = i;
            this.this$0 = appOpsService;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            AndroidPackage androidPackage;
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                    int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                    if (!action.equals("android.intent.action.PACKAGE_ADDED") || intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        if (action.equals("android.intent.action.PACKAGE_REMOVED") && !intent.hasExtra("android.intent.extra.REPLACING")) {
                            synchronized (this.this$0) {
                                this.this$0.packageRemovedLocked(intExtra, encodedSchemeSpecificPart);
                            }
                            return;
                        } else {
                            if (!action.equals("android.intent.action.PACKAGE_REPLACED") || (androidPackage = this.this$0.getPackageManagerInternal().getPackage(encodedSchemeSpecificPart)) == null) {
                                return;
                            }
                            synchronized (this.this$0) {
                                this.this$0.refreshAttributionsLocked(androidPackage, intExtra);
                            }
                            return;
                        }
                    }
                    boolean isSamplingTarget = this.this$0.isSamplingTarget(this.this$0.getPackageManagerInternal().getPackageInfo(Process.myUid(), UserHandle.getUserId(intExtra), 4096L, encodedSchemeSpecificPart));
                    synchronized (this.this$0) {
                        if (isSamplingTarget) {
                            try {
                                this.this$0.mRarelyUsedPackages.add(encodedSchemeSpecificPart);
                            } finally {
                            }
                        }
                        UidState uidStateLocked = this.this$0.getUidStateLocked(intExtra, true);
                        if (!uidStateLocked.pkgOps.containsKey(encodedSchemeSpecificPart)) {
                            uidStateLocked.pkgOps.put(encodedSchemeSpecificPart, new Ops(encodedSchemeSpecificPart, uidStateLocked));
                        }
                        AppOpsService appOpsService = this.this$0;
                        appOpsService.getClass();
                        if (UserHandle.getAppId(intExtra) >= 10000) {
                            appOpsService.getUidStateLocked(Process.toSdkSandboxUid(intExtra), true);
                        }
                    }
                    return;
                default:
                    int[] intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                    String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                    for (int i : AppOpsService.OPS_RESTRICTED_ON_SUSPEND) {
                        synchronized (this.this$0) {
                            try {
                                ArraySet arraySet = (ArraySet) this.this$0.mOpModeWatchers.get(i);
                                if (arraySet != null) {
                                    ArraySet arraySet2 = new ArraySet(arraySet);
                                    for (int i2 = 0; i2 < intArrayExtra.length; i2++) {
                                        int i3 = intArrayExtra[i2];
                                        String str = stringArrayExtra[i2];
                                        ArraySet arraySet3 = new ArraySet();
                                        arraySet3.add("default:0");
                                        VirtualDeviceManagerInternal virtualDeviceManagerInternal = this.this$0.mVirtualDeviceManagerInternal;
                                        if (virtualDeviceManagerInternal != null) {
                                            arraySet3.addAll(virtualDeviceManagerInternal.getAllPersistentDeviceIds());
                                        }
                                        Iterator it = arraySet3.iterator();
                                        while (it.hasNext()) {
                                            this.this$0.notifyOpChanged(arraySet2, i, i3, str, (String) it.next());
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.appop.AppOpsService$6, reason: invalid class name */
    public final class AnonymousClass6 implements AppOpsManagerLocal {
        public /* synthetic */ AnonymousClass6() {
        }

        @Override // com.android.server.appop.AppOpsManagerLocal
        public boolean isUidInForeground(int i) {
            boolean z;
            synchronized (AppOpsService.this) {
                z = AppOpsService.this.mUidStateTracker.evalMode(i, -1, 4) == 0;
            }
            return z;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ActiveCallback implements IBinder.DeathRecipient {
        public final IAppOpsActiveCallback mCallback;
        public final int mCallingPid;
        public final int mCallingUid;
        public final int mWatchingUid;

        public ActiveCallback(IAppOpsActiveCallback iAppOpsActiveCallback, int i, int i2, int i3) {
            this.mCallback = iAppOpsActiveCallback;
            this.mWatchingUid = i;
            this.mCallingUid = i2;
            this.mCallingPid = i3;
            try {
                iAppOpsActiveCallback.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            AppOpsService.this.stopWatchingActive(this.mCallback);
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ActiveCallback{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" watchinguid=");
            UserHandle.formatUid(m, this.mWatchingUid);
            m.append(" from uid=");
            UserHandle.formatUid(m, this.mCallingUid);
            m.append(" pid=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(m, this.mCallingPid, '}');
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppOpsManagerInternalImpl extends AppOpsManagerInternal {
        public AppOpsManagerInternalImpl() {
        }

        public final int getOpRestrictionCount(int i, UserHandle userHandle, String str, String str2) {
            int i2;
            synchronized (AppOpsService.this) {
                try {
                    int size = AppOpsService.this.mOpUserRestrictions.size();
                    i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        if (((ClientUserRestrictionState) AppOpsService.this.mOpUserRestrictions.valueAt(i3)).hasRestriction(str, i, false, userHandle.getIdentifier(), str2)) {
                            i2++;
                        }
                    }
                    int size2 = AppOpsService.this.mOpGlobalRestrictions.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        ClientGlobalRestrictionState clientGlobalRestrictionState = (ClientGlobalRestrictionState) AppOpsService.this.mOpGlobalRestrictions.valueAt(i4);
                        SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) ((AppOpsRestrictionsImpl) AppOpsService.this.mAppOpsRestrictions).mGlobalRestrictions.get(clientGlobalRestrictionState.mToken);
                        if (sparseBooleanArray == null ? false : sparseBooleanArray.get(i)) {
                            i2++;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return i2;
        }

        public final void setDeviceAndProfileOwners(SparseIntArray sparseIntArray) {
            synchronized (AppOpsService.this) {
                AppOpsService.this.mProfileOwners = sparseIntArray;
            }
        }

        public final void setGlobalRestriction(int i, boolean z, IBinder iBinder) {
            if (Binder.getCallingPid() != Process.myPid()) {
                throw new SecurityException("Only the system can set global restrictions");
            }
            synchronized (AppOpsService.this) {
                try {
                    ClientGlobalRestrictionState clientGlobalRestrictionState = (ClientGlobalRestrictionState) AppOpsService.this.mOpGlobalRestrictions.get(iBinder);
                    if (clientGlobalRestrictionState == null) {
                        try {
                            clientGlobalRestrictionState = AppOpsService.this.new ClientGlobalRestrictionState(iBinder);
                            Slog.w("AppOps", "setGlobalRestriction: code " + i + "by Pid: " + Binder.getCallingPid());
                            AppOpsService.this.mOpGlobalRestrictions.put(iBinder, clientGlobalRestrictionState);
                        } catch (RemoteException unused) {
                            return;
                        }
                    }
                    if (clientGlobalRestrictionState.setRestriction(i, z)) {
                        AppOpsService appOpsService = AppOpsService.this;
                        appOpsService.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda1(2), appOpsService, Integer.valueOf(i), -2));
                        AppOpsService appOpsService2 = AppOpsService.this;
                        appOpsService2.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda5(1), appOpsService2, Integer.valueOf(i), Boolean.valueOf(z), -1));
                    }
                    AppOpsRestrictions appOpsRestrictions = AppOpsService.this.mAppOpsRestrictions;
                    if (!((AppOpsRestrictionsImpl) appOpsRestrictions).mGlobalRestrictions.containsKey(clientGlobalRestrictionState.mToken)) {
                        AppOpsService.this.mOpGlobalRestrictions.remove(iBinder);
                        clientGlobalRestrictionState.mToken.unlinkToDeath(clientGlobalRestrictionState, 0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setModeFromPermissionPolicy(int i, int i2, String str, int i3, IAppOpsCallback iAppOpsCallback) {
            AppOpsService.this.setMode(i, i2, str, i3, iAppOpsCallback);
        }

        public final void setUidModeFromPermissionPolicy(int i, int i2, int i3, IAppOpsCallback iAppOpsCallback) {
            AppOpsService.this.setUidMode(i, i2, i3, iAppOpsCallback);
        }

        public final void updateAppWidgetVisibility(SparseArray sparseArray, boolean z) {
            AppOpsService appOpsService = AppOpsService.this;
            synchronized (appOpsService) {
                AppOpsUidStateTrackerImpl appOpsUidStateTrackerImpl = (AppOpsUidStateTrackerImpl) appOpsService.getUidStateTracker();
                appOpsUidStateTrackerImpl.getClass();
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = sparseArray.keyAt(i);
                    appOpsUidStateTrackerImpl.mPendingAppWidgetVisible.put(keyAt, z);
                    appOpsUidStateTrackerImpl.commitUidPendingState(keyAt);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ChangeRec {
        public final int op;
        public final String pkg;
        public final int previous_mode;
        public final int uid;

        public ChangeRec(int i, int i2, int i3, String str) {
            this.op = i;
            this.uid = i2;
            this.pkg = str;
            this.previous_mode = i3;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CheckOpsDelegateDispatcher {
        public final AppOpsManagerInternal.CheckOpsDelegate mCheckOpsDelegate;
        public final AppOpsManagerInternal.CheckOpsDelegate mPolicy;

        public CheckOpsDelegateDispatcher(AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate, AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2) {
            this.mPolicy = checkOpsDelegate;
            this.mCheckOpsDelegate = checkOpsDelegate2;
        }

        public final int checkOperation(int i, int i2, String str, String str2, int i3, boolean z) {
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = this.mPolicy;
            AppOpsService appOpsService = AppOpsService.this;
            if (checkOpsDelegate != null) {
                return this.mCheckOpsDelegate != null ? checkOpsDelegate.checkOperation(i, i2, str, str2, i3, z, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda8(this, 0)) : checkOpsDelegate.checkOperation(i, i2, str, str2, i3, z, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9(appOpsService, 0));
            }
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = this.mCheckOpsDelegate;
            return checkOpsDelegate2 != null ? checkOpsDelegate2.checkOperation(i, i2, str, str2, i3, z, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9(appOpsService, 0)) : AppOpsService.m231$$Nest$mcheckOperationImpl(AppOpsService.this, i, i2, str, str2, i3, z);
        }

        public final void finishOperation(IBinder iBinder, int i, int i2, String str, String str2, int i3) {
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = this.mPolicy;
            AppOpsService appOpsService = AppOpsService.this;
            if (checkOpsDelegate != null) {
                if (this.mCheckOpsDelegate != null) {
                    checkOpsDelegate.finishOperation(iBinder, i, i2, str, str2, i3, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10(0, this));
                    return;
                } else {
                    checkOpsDelegate.finishOperation(iBinder, i, i2, str, str2, i3, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10(1, appOpsService));
                    return;
                }
            }
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = this.mCheckOpsDelegate;
            if (checkOpsDelegate2 != null) {
                checkOpsDelegate2.finishOperation(iBinder, i, i2, str, str2, i3, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda10(1, appOpsService));
            } else {
                AppOpsService.m232$$Nest$mfinishOperationImpl(AppOpsService.this, iBinder, i, i2, str, str2, i3);
            }
        }

        public final void finishProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z) {
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = this.mPolicy;
            AppOpsService appOpsService = AppOpsService.this;
            if (checkOpsDelegate != null) {
                if (this.mCheckOpsDelegate != null) {
                    checkOpsDelegate.finishProxyOperation(iBinder, i, attributionSource, z, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda4(this, 1));
                    return;
                } else {
                    checkOpsDelegate.finishProxyOperation(iBinder, i, attributionSource, z, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(appOpsService, 1));
                    return;
                }
            }
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = this.mCheckOpsDelegate;
            if (checkOpsDelegate2 != null) {
                checkOpsDelegate2.finishProxyOperation(iBinder, i, attributionSource, z, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(appOpsService, 1));
            } else {
                AppOpsService.m233$$Nest$mfinishProxyOperationImpl(appOpsService, iBinder, i, attributionSource, z);
            }
        }

        public final SyncNotedAppOp noteOperation(int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2) {
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = this.mPolicy;
            AppOpsService appOpsService = AppOpsService.this;
            if (checkOpsDelegate != null) {
                return this.mCheckOpsDelegate != null ? checkOpsDelegate.noteOperation(i, i2, str, str2, i3, z, str3, z2, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0(0, this)) : checkOpsDelegate.noteOperation(i, i2, str, str2, i3, z, str3, z2, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0(1, appOpsService));
            }
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = this.mCheckOpsDelegate;
            return checkOpsDelegate2 != null ? checkOpsDelegate2.noteOperation(i, i2, str, str2, i3, z, str3, z2, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda0(1, appOpsService)) : AppOpsService.m234$$Nest$mnoteOperationImpl(AppOpsService.this, i, i2, str, str2, i3, z, str3, z2);
        }

        public final SyncNotedAppOp noteProxyOperation(int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3) {
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = this.mPolicy;
            AppOpsService appOpsService = AppOpsService.this;
            if (checkOpsDelegate != null) {
                return this.mCheckOpsDelegate != null ? checkOpsDelegate.noteProxyOperation(i, attributionSource, z, str, z2, z3, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda8(this, 1)) : checkOpsDelegate.noteProxyOperation(i, attributionSource, z, str, z2, z3, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9(appOpsService, 1));
            }
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = this.mCheckOpsDelegate;
            return checkOpsDelegate2 != null ? checkOpsDelegate2.noteProxyOperation(i, attributionSource, z, str, z2, z3, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda9(appOpsService, 1)) : AppOpsService.m235$$Nest$mnoteProxyOperationImpl(AppOpsService.this, i, attributionSource, z, str, z2, z3);
        }

        public final SyncNotedAppOp startOperation(IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5) {
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = this.mPolicy;
            AppOpsService appOpsService = AppOpsService.this;
            if (checkOpsDelegate != null) {
                return this.mCheckOpsDelegate != null ? checkOpsDelegate.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda6(0, this)) : checkOpsDelegate.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda6(1, appOpsService));
            }
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = this.mCheckOpsDelegate;
            return checkOpsDelegate2 != null ? checkOpsDelegate2.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda6(1, appOpsService)) : AppOpsService.m236$$Nest$mstartOperationImpl(AppOpsService.this, iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5);
        }

        public final SyncNotedAppOp startProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) {
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = this.mPolicy;
            AppOpsService appOpsService = AppOpsService.this;
            if (checkOpsDelegate != null) {
                return this.mCheckOpsDelegate != null ? checkOpsDelegate.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda2(0, this)) : checkOpsDelegate.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda2(1, appOpsService));
            }
            AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = this.mCheckOpsDelegate;
            return checkOpsDelegate2 != null ? checkOpsDelegate2.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda2(1, appOpsService)) : AppOpsService.m237$$Nest$mstartProxyOperationImpl(AppOpsService.this, iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClientGlobalRestrictionState implements IBinder.DeathRecipient {
        public final IBinder mToken;

        public ClientGlobalRestrictionState(IBinder iBinder) {
            iBinder.linkToDeath(this, 0);
            this.mToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            AppOpsRestrictions appOpsRestrictions = AppOpsService.this.mAppOpsRestrictions;
            ((AppOpsRestrictionsImpl) appOpsRestrictions).mGlobalRestrictions.remove(this.mToken);
            AppOpsService.this.mOpGlobalRestrictions.remove(this.mToken);
            this.mToken.unlinkToDeath(this, 0);
        }

        public final boolean setRestriction(int i, boolean z) {
            AppOpsRestrictions appOpsRestrictions = AppOpsService.this.mAppOpsRestrictions;
            IBinder iBinder = this.mToken;
            AppOpsRestrictionsImpl appOpsRestrictionsImpl = (AppOpsRestrictionsImpl) appOpsRestrictions;
            if (z) {
                if (!appOpsRestrictionsImpl.mGlobalRestrictions.containsKey(iBinder)) {
                    appOpsRestrictionsImpl.mGlobalRestrictions.put(iBinder, new SparseBooleanArray());
                }
                SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) appOpsRestrictionsImpl.mGlobalRestrictions.get(iBinder);
                Objects.requireNonNull(sparseBooleanArray);
                boolean z2 = !sparseBooleanArray.get(i);
                sparseBooleanArray.put(i, true);
                return z2;
            }
            SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) appOpsRestrictionsImpl.mGlobalRestrictions.get(iBinder);
            if (sparseBooleanArray2 == null) {
                return false;
            }
            boolean z3 = sparseBooleanArray2.get(i);
            sparseBooleanArray2.delete(i);
            if (sparseBooleanArray2.size() == 0) {
                appOpsRestrictionsImpl.mGlobalRestrictions.remove(iBinder);
            }
            return z3;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClientUserRestrictionState implements IBinder.DeathRecipient {
        public int callingRestriction;
        public final IBinder token;

        public ClientUserRestrictionState(IBinder iBinder) {
            iBinder.linkToDeath(this, 0);
            this.token = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (AppOpsService.this) {
                ((AppOpsRestrictionsImpl) AppOpsService.this.mAppOpsRestrictions).clearUserRestrictions(this.token);
                AppOpsService.this.mOpUserRestrictions.remove(this.token);
                this.token.unlinkToDeath(this, 0);
            }
        }

        public final boolean hasRestriction(String str, int i, boolean z, int i2, String str2) {
            SparseBooleanArray sparseBooleanArray;
            AppOpsRestrictions appOpsRestrictions = AppOpsService.this.mAppOpsRestrictions;
            IBinder iBinder = this.token;
            AppOpsRestrictionsImpl appOpsRestrictionsImpl = (AppOpsRestrictionsImpl) appOpsRestrictions;
            SparseArray sparseArray = (SparseArray) appOpsRestrictionsImpl.mUserRestrictions.get(iBinder);
            if (!((sparseArray == null || (sparseBooleanArray = (SparseBooleanArray) sparseArray.get(i2)) == null) ? false : sparseBooleanArray.get(i))) {
                return false;
            }
            SparseArray sparseArray2 = (SparseArray) appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.get(iBinder);
            PackageTagsList packageTagsList = sparseArray2 == null ? null : (PackageTagsList) sparseArray2.get(i2);
            if (packageTagsList == null) {
                return true;
            }
            return true ^ (z ? packageTagsList.includes(str) : packageTagsList.contains(str, str2));
        }

        public final void removeUser(int i) {
            AppOpsRestrictions appOpsRestrictions = AppOpsService.this.mAppOpsRestrictions;
            IBinder iBinder = this.token;
            AppOpsRestrictionsImpl appOpsRestrictionsImpl = (AppOpsRestrictionsImpl) appOpsRestrictions;
            SparseArray sparseArray = (SparseArray) appOpsRestrictionsImpl.mUserRestrictions.get(iBinder);
            if (sparseArray != null) {
                sparseArray.contains(i);
                sparseArray.remove(i);
                if (sparseArray.size() == 0) {
                    appOpsRestrictionsImpl.mUserRestrictions.remove(iBinder);
                }
            }
            SparseArray sparseArray2 = (SparseArray) appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.get(iBinder);
            if (sparseArray2 != null) {
                sparseArray2.contains(i);
                sparseArray2.remove(i);
                if (sparseArray2.size() == 0) {
                    appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.remove(iBinder);
                }
            }
        }

        public final boolean setRestriction(int i, boolean z, PackageTagsList packageTagsList, int i2) {
            int[] iArr;
            SparseBooleanArray sparseBooleanArray;
            boolean z2;
            boolean z3;
            if (this.callingRestriction == 0) {
                int callingUid = Binder.getCallingUid();
                this.callingRestriction = callingUid;
                if (callingUid == 0) {
                    this.callingRestriction = 1;
                    if (AppOpsService.DEBUG_MID || AppOpsService.DEBUG) {
                        Slog.i("AppOps", "setRestriction() :: CallingUid is SS_SHELL_UID");
                    }
                }
            }
            AppOpsRestrictions appOpsRestrictions = AppOpsService.this.mAppOpsRestrictions;
            IBinder iBinder = this.token;
            AppOpsRestrictionsImpl appOpsRestrictionsImpl = (AppOpsRestrictionsImpl) appOpsRestrictions;
            if (i2 == -1) {
                List users = UserManager.get(appOpsRestrictionsImpl.mContext).getUsers();
                iArr = new int[users.size()];
                for (int i3 = 0; i3 < users.size(); i3++) {
                    iArr[i3] = ((UserInfo) users.get(i3)).id;
                }
            } else {
                appOpsRestrictionsImpl.getClass();
                iArr = new int[]{i2};
            }
            boolean z4 = false;
            for (int i4 = 0; i4 < iArr.length; i4++) {
                int i5 = iArr[i4];
                if (z) {
                    if (!appOpsRestrictionsImpl.mUserRestrictions.containsKey(iBinder)) {
                        appOpsRestrictionsImpl.mUserRestrictions.put(iBinder, new SparseArray());
                    }
                    SparseArray sparseArray = (SparseArray) appOpsRestrictionsImpl.mUserRestrictions.get(iBinder);
                    Objects.requireNonNull(sparseArray);
                    if (!sparseArray.contains(i5)) {
                        sparseArray.put(i5, new SparseBooleanArray());
                    }
                    SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) sparseArray.get(i5);
                    z2 = !sparseBooleanArray2.get(i);
                    sparseBooleanArray2.put(i, z);
                } else {
                    SparseArray sparseArray2 = (SparseArray) appOpsRestrictionsImpl.mUserRestrictions.get(iBinder);
                    if (sparseArray2 == null || (sparseBooleanArray = (SparseBooleanArray) sparseArray2.get(i5)) == null) {
                        z2 = false;
                    } else {
                        boolean z5 = sparseBooleanArray.get(i);
                        sparseBooleanArray.delete(i);
                        if (sparseBooleanArray.size() == 0) {
                            sparseArray2.remove(i5);
                        }
                        if (sparseArray2.size() == 0) {
                            appOpsRestrictionsImpl.mUserRestrictions.remove(iBinder);
                        }
                        z2 = z5;
                    }
                }
                boolean z6 = z4 | z2;
                int i6 = iArr[i4];
                if (packageTagsList == null || packageTagsList.isEmpty()) {
                    SparseArray sparseArray3 = (SparseArray) appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.get(iBinder);
                    if (sparseArray3 == null) {
                        z3 = false;
                    } else {
                        z3 = sparseArray3.get(i6) != null;
                        sparseArray3.remove(i6);
                        if (sparseArray3.size() == 0) {
                            appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.remove(iBinder);
                        }
                    }
                } else {
                    if (!appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.containsKey(iBinder)) {
                        appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.put(iBinder, new SparseArray());
                    }
                    SparseArray sparseArray4 = (SparseArray) appOpsRestrictionsImpl.mUserRestrictionExcludedPackageTags.get(iBinder);
                    Objects.requireNonNull(sparseArray4);
                    sparseArray4.put(i6, packageTagsList);
                    z3 = true;
                }
                z4 = z6 | z3;
            }
            return z4;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Constants extends ContentObserver {
        public long BG_STATE_SETTLE_TIME;
        public long FG_SERVICE_STATE_SETTLE_TIME;
        public long TOP_STATE_SETTLE_TIME;
        public final KeyValueListParser mParser;
        public ContentResolver mResolver;

        public Constants(Handler handler) {
            super(handler);
            this.mParser = new KeyValueListParser(',');
            updateConstants();
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("  Settings:");
            printWriter.print("    ");
            printWriter.print("top_state_settle_time");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.TOP_STATE_SETTLE_TIME, printWriter, "    ", "fg_service_state_settle_time");
            printWriter.print("=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(this.FG_SERVICE_STATE_SETTLE_TIME, printWriter, "    ", "bg_state_settle_time");
            printWriter.print("=");
            TimeUtils.formatDuration(this.BG_STATE_SETTLE_TIME, printWriter);
            printWriter.println();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            updateConstants();
        }

        public final void updateConstants() {
            ContentResolver contentResolver = this.mResolver;
            String string = contentResolver != null ? Settings.Global.getString(contentResolver, "app_ops_constants") : "";
            synchronized (AppOpsService.this) {
                try {
                    this.mParser.setString(string);
                } catch (IllegalArgumentException e) {
                    Slog.e("AppOps", "Bad app ops settings", e);
                }
                this.TOP_STATE_SETTLE_TIME = this.mParser.getDurationMillis("top_state_settle_time", 5000L);
                this.FG_SERVICE_STATE_SETTLE_TIME = this.mParser.getDurationMillis("fg_service_state_settle_time", 5000L);
                this.BG_STATE_SETTLE_TIME = this.mParser.getDurationMillis("bg_state_settle_time", 1000L);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ModeCallback implements IBinder.DeathRecipient {
        public final IAppOpsCallback mCallback;
        public final int mCallingPid;
        public final int mCallingUid;
        public final int mFlags;
        public final int mWatchedOpCode;

        public ModeCallback(IAppOpsCallback iAppOpsCallback, int i, int i2, int i3, int i4) {
            this.mFlags = i;
            this.mWatchedOpCode = i2;
            this.mCallingUid = i3;
            this.mCallingPid = i4;
            this.mCallback = iAppOpsCallback;
            try {
                iAppOpsCallback.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            AppOpsService.this.stopWatchingMode(this.mCallback);
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ModeCallback{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" watchinguid=");
            UserHandle.formatUid(m, -1);
            m.append(" flags=0x");
            m.append(Integer.toHexString(this.mFlags));
            int i = this.mWatchedOpCode;
            if (i == -2) {
                m.append(" op=(all)");
            } else if (i != -1) {
                m.append(" op=");
                m.append(AppOpsManager.opToName(this.mWatchedOpCode));
            }
            m.append(" from uid=");
            UserHandle.formatUid(m, this.mCallingUid);
            m.append(" pid=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(m, this.mCallingPid, '}');
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotedCallback implements IBinder.DeathRecipient {
        public final IAppOpsNotedCallback mCallback;
        public final int mCallingPid;
        public final int mCallingUid;
        public final int mWatchingUid;

        public NotedCallback(IAppOpsNotedCallback iAppOpsNotedCallback, int i, int i2, int i3) {
            this.mCallback = iAppOpsNotedCallback;
            this.mWatchingUid = i;
            this.mCallingUid = i2;
            this.mCallingPid = i3;
            try {
                iAppOpsNotedCallback.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            AppOpsService.this.stopWatchingNoted(this.mCallback);
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "NotedCallback{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" watchinguid=");
            UserHandle.formatUid(m, this.mWatchingUid);
            m.append(" from uid=");
            UserHandle.formatUid(m, this.mCallingUid);
            m.append(" pid=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(m, this.mCallingPid, '}');
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Op {
        public final ArrayMap mDeviceAttributedOps;
        public final int op;
        public final String packageName;
        public final int uid;
        public final UidState uidState;

        public Op(UidState uidState, String str, int i, int i2) {
            ArrayMap arrayMap = new ArrayMap(1);
            this.mDeviceAttributedOps = arrayMap;
            this.op = i;
            this.uid = i2;
            this.uidState = uidState;
            this.packageName = str;
            arrayMap.put("default:0", new ArrayMap());
        }

        public final AttributedOp getOrCreateAttribution(Op op, String str, String str2) {
            ArrayMap arrayMap = (ArrayMap) this.mDeviceAttributedOps.get(str2);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                this.mDeviceAttributedOps.put(str2, arrayMap);
            }
            AttributedOp attributedOp = (AttributedOp) arrayMap.get(str);
            if (attributedOp != null) {
                return attributedOp;
            }
            AttributedOp attributedOp2 = new AttributedOp(AppOpsService.this, str, op);
            arrayMap.put(str, attributedOp2);
            return attributedOp2;
        }

        public final boolean isRunning() {
            for (int i = 0; i < this.mDeviceAttributedOps.size(); i++) {
                ArrayMap arrayMap = (ArrayMap) this.mDeviceAttributedOps.valueAt(i);
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    if (((AttributedOp) arrayMap.valueAt(i2)).isRunning()) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final void removeAttributionsWithNoTime() {
            LongSparseArray longSparseArray;
            for (int size = this.mDeviceAttributedOps.size() - 1; size >= 0; size--) {
                ArrayMap arrayMap = (ArrayMap) this.mDeviceAttributedOps.valueAt(size);
                for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                    AttributedOp attributedOp = (AttributedOp) arrayMap.valueAt(size2);
                    LongSparseArray longSparseArray2 = attributedOp.mAccessEvents;
                    if ((longSparseArray2 == null || longSparseArray2.size() <= 0) && ((longSparseArray = attributedOp.mRejectEvents) == null || longSparseArray.size() <= 0)) {
                        arrayMap.removeAt(size2);
                    }
                }
                if (!"default:0".equals(this.mDeviceAttributedOps.keyAt(size)) && arrayMap.isEmpty()) {
                    this.mDeviceAttributedOps.removeAt(size);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Ops extends SparseArray {
        public AppOpsManager.RestrictionBypass bypass;
        public final String packageName;
        public final UidState uidState;
        public final ArraySet knownAttributionTags = new ArraySet();
        public final ArraySet validAttributionTags = new ArraySet();

        public Ops(String str, UidState uidState) {
            this.packageName = str;
            this.uidState = uidState;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageVerificationResult {
        public final AppOpsManager.RestrictionBypass bypass;
        public final boolean isAttributionTagValid;

        public PackageVerificationResult(AppOpsManager.RestrictionBypass restrictionBypass, boolean z) {
            this.bypass = restrictionBypass;
            this.isAttributionTagValid = z;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Shell extends ShellCommand {
        public String attributionTag;
        public final IAppOpsService mInterface;
        public final AppOpsService mInternal;
        public int mode;
        public String modeStr;
        public int nonpackageUid;
        public int op;
        public String opStr;
        public String packageName;
        public int packageUid;
        public boolean targetsUid;
        public int userId = 0;
        public final IBinder mToken = AppOpsManager.getClientId();

        static {
            new Binder();
        }

        public Shell(IAppOpsService iAppOpsService, AppOpsService appOpsService) {
            this.mInterface = iAppOpsService;
            this.mInternal = appOpsService;
        }

        public static int strModeToMode(PrintWriter printWriter, String str) {
            for (int length = AppOpsManager.MODE_NAMES.length - 1; length >= 0; length--) {
                if (AppOpsManager.MODE_NAMES[length].equals(str)) {
                    return length;
                }
            }
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, "Error: Mode ", str, " is not valid");
                return -1;
            }
        }

        public static int strOpToOp(PrintWriter printWriter, String str) {
            try {
                try {
                    try {
                        return AppOpsManager.strOpToOp(str);
                    } catch (NumberFormatException unused) {
                        return AppOpsManager.strDebugOpToOp(str);
                    }
                } catch (IllegalArgumentException e) {
                    printWriter.println("Error: " + e.getMessage());
                    return -1;
                }
            } catch (IllegalArgumentException unused2) {
                return Integer.parseInt(str);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v8, types: [java.util.List] */
        public final int onCommand(String str) {
            int i;
            char c;
            int parseUserPackageOp;
            String str2;
            ArrayList arrayList;
            int i2;
            String str3;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            PrintWriter outPrintWriter = getOutPrintWriter();
            PrintWriter errPrintWriter = getErrPrintWriter();
            try {
                i = 0;
                switch (str.hashCode()) {
                    case -1703718319:
                        if (str.equals("write-settings")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1166702330:
                        if (str.equals("query-op")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 102230:
                        if (str.equals("get")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 113762:
                        if (str.equals("set")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3387378:
                        if (str.equals("note")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3540994:
                        if (str.equals("stop")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 108404047:
                        if (str.equals("reset")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 109757538:
                        if (str.equals("start")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2085703290:
                        if (str.equals("read-settings")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                str3 = null;
            } catch (RemoteException e) {
                UiModeManagerService$13$$ExternalSyntheticOutline0.m("Remote exception: ", e, outPrintWriter);
            }
            switch (c) {
                case 0:
                    parseUserPackageOp = parseUserPackageOp(errPrintWriter, true);
                    if (parseUserPackageOp < 0) {
                        return parseUserPackageOp;
                    }
                    String nextArg = getNextArg();
                    if (nextArg == null) {
                        errPrintWriter.println("Error: Mode not specified.");
                    } else {
                        int strModeToMode = strModeToMode(errPrintWriter, nextArg);
                        if (strModeToMode >= 0) {
                            boolean z = this.targetsUid;
                            if (!z && (str2 = this.packageName) != null) {
                                this.mInterface.setMode(this.op, this.packageUid, str2, strModeToMode);
                            } else if (!z || this.packageName == null) {
                                this.mInterface.setUidMode(this.op, this.nonpackageUid, strModeToMode);
                            } else {
                                try {
                                    this.mInterface.setUidMode(this.op, this.mInternal.mContext.getPackageManager().getPackageUidAsUser(this.packageName, this.userId), strModeToMode);
                                } catch (PackageManager.NameNotFoundException unused) {
                                }
                            }
                            return 0;
                        }
                    }
                    return -1;
                case 1:
                    parseUserPackageOp = parseUserPackageOp(errPrintWriter, false);
                    if (parseUserPackageOp < 0) {
                        return parseUserPackageOp;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    if (this.packageName != null) {
                        IAppOpsService iAppOpsService = this.mInterface;
                        int i3 = this.packageUid;
                        int i4 = this.op;
                        List uidOps = iAppOpsService.getUidOps(i3, i4 != -1 ? new int[]{i4} : null);
                        if (uidOps != null) {
                            arrayList2.addAll(uidOps);
                        }
                        IAppOpsService iAppOpsService2 = this.mInterface;
                        int i5 = this.packageUid;
                        String str4 = this.packageName;
                        int i6 = this.op;
                        List opsForPackage = iAppOpsService2.getOpsForPackage(i5, str4, i6 != -1 ? new int[]{i6} : null);
                        arrayList = arrayList2;
                        if (opsForPackage != null) {
                            arrayList2.addAll(opsForPackage);
                            arrayList = arrayList2;
                        }
                    } else {
                        IAppOpsService iAppOpsService3 = this.mInterface;
                        int i7 = this.nonpackageUid;
                        int i8 = this.op;
                        arrayList = iAppOpsService3.getUidOps(i7, i8 != -1 ? new int[]{i8} : null);
                    }
                    if (arrayList != null && arrayList.size() > 0) {
                        long currentTimeMillis = System.currentTimeMillis();
                        int i9 = 0;
                        while (i9 < arrayList.size()) {
                            AppOpsManager.PackageOps packageOps = (AppOpsManager.PackageOps) arrayList.get(i9);
                            if (packageOps.getPackageName() == null) {
                                outPrintWriter.print("Uid mode: ");
                            }
                            List ops = packageOps.getOps();
                            for (int i10 = i; i10 < ops.size(); i10 = i2 + 1) {
                                AppOpsManager.OpEntry opEntry = (AppOpsManager.OpEntry) ops.get(i10);
                                outPrintWriter.print(AppOpsManager.opToName(opEntry.getOp()));
                                outPrintWriter.print(": ");
                                outPrintWriter.print(AppOpsManager.modeToName(opEntry.getMode()));
                                if (this.attributionTag == null) {
                                    if (opEntry.getLastAccessTime(31) != -1) {
                                        outPrintWriter.print("; time=");
                                        i2 = i10;
                                        TimeUtils.formatDuration(currentTimeMillis - opEntry.getLastAccessTime(31), outPrintWriter);
                                        outPrintWriter.print(" ago");
                                    } else {
                                        i2 = i10;
                                    }
                                    if (opEntry.getLastRejectTime(31) != -1) {
                                        outPrintWriter.print("; rejectTime=");
                                        TimeUtils.formatDuration(currentTimeMillis - opEntry.getLastRejectTime(31), outPrintWriter);
                                        outPrintWriter.print(" ago");
                                    }
                                    if (opEntry.isRunning()) {
                                        outPrintWriter.print(" (running)");
                                    } else if (opEntry.getLastDuration(31) != -1) {
                                        outPrintWriter.print("; duration=");
                                        TimeUtils.formatDuration(opEntry.getLastDuration(31), outPrintWriter);
                                    }
                                } else {
                                    i2 = i10;
                                    AppOpsManager.AttributedOpEntry attributedOpEntry = (AppOpsManager.AttributedOpEntry) opEntry.getAttributedOpEntries().get(this.attributionTag);
                                    if (attributedOpEntry != null) {
                                        if (attributedOpEntry.getLastAccessTime(31) != -1) {
                                            outPrintWriter.print("; time=");
                                            TimeUtils.formatDuration(currentTimeMillis - attributedOpEntry.getLastAccessTime(31), outPrintWriter);
                                            outPrintWriter.print(" ago");
                                        }
                                        if (attributedOpEntry.getLastRejectTime(31) != -1) {
                                            outPrintWriter.print("; rejectTime=");
                                            TimeUtils.formatDuration(currentTimeMillis - attributedOpEntry.getLastRejectTime(31), outPrintWriter);
                                            outPrintWriter.print(" ago");
                                        }
                                        if (attributedOpEntry.isRunning()) {
                                            outPrintWriter.print(" (running)");
                                        } else if (attributedOpEntry.getLastDuration(31) != -1) {
                                            outPrintWriter.print("; duration=");
                                            TimeUtils.formatDuration(attributedOpEntry.getLastDuration(31), outPrintWriter);
                                        }
                                    }
                                }
                                outPrintWriter.println();
                            }
                            i9++;
                            i = 0;
                        }
                        return 0;
                    }
                    outPrintWriter.println("No operations.");
                    int i11 = this.op;
                    if (i11 > -1 && i11 < 149) {
                        outPrintWriter.println("Default mode: " + AppOpsManager.modeToName(AppOpsManager.opToDefaultMode(this.op)));
                    }
                    return 0;
                case 2:
                    parseUserPackageOp = parseUserOpMode(errPrintWriter);
                    if (parseUserPackageOp < 0) {
                        return parseUserPackageOp;
                    }
                    List packagesForOps = this.mInterface.getPackagesForOps(new int[]{this.op});
                    if (packagesForOps != null && packagesForOps.size() > 0) {
                        for (int i12 = 0; i12 < packagesForOps.size(); i12++) {
                            AppOpsManager.PackageOps packageOps2 = (AppOpsManager.PackageOps) packagesForOps.get(i12);
                            List ops2 = ((AppOpsManager.PackageOps) packagesForOps.get(i12)).getOps();
                            int i13 = 0;
                            while (true) {
                                if (i13 < ops2.size()) {
                                    AppOpsManager.OpEntry opEntry2 = (AppOpsManager.OpEntry) ops2.get(i13);
                                    if (opEntry2.getOp() == this.op && opEntry2.getMode() == this.mode) {
                                        outPrintWriter.println(packageOps2.getPackageName());
                                    } else {
                                        i13++;
                                    }
                                }
                            }
                        }
                        return 0;
                    }
                    outPrintWriter.println("No operations.");
                    return 0;
                case 3:
                    int i14 = -2;
                    while (true) {
                        String nextArg2 = getNextArg();
                        if (nextArg2 == null) {
                            if (i14 == -2) {
                                i14 = ActivityManager.getCurrentUser();
                            }
                            this.mInterface.resetAllModes(i14, str3);
                            outPrintWriter.print("Reset all modes for: ");
                            if (i14 == -1) {
                                outPrintWriter.print("all users");
                            } else {
                                outPrintWriter.print("user ");
                                outPrintWriter.print(i14);
                            }
                            outPrintWriter.print(", ");
                            if (str3 == null) {
                                outPrintWriter.println("all packages");
                                break;
                            } else {
                                outPrintWriter.print("package ");
                                outPrintWriter.println(str3);
                                break;
                            }
                        } else if ("--user".equals(nextArg2)) {
                            i14 = UserHandle.parseUserArg(getNextArgRequired());
                        } else if (str3 == null) {
                            str3 = nextArg2;
                        } else {
                            errPrintWriter.println("Error: Unsupported argument: " + nextArg2);
                        }
                    }
                    return -1;
                case 4:
                    this.mInternal.enforceManageAppOpsModes(Binder.getCallingPid(), Binder.getCallingUid(), -1);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        synchronized (this.mInternal) {
                            AppOpsService appOpsService = this.mInternal;
                            appOpsService.mHandler.removeCallbacks(appOpsService.mWriteRunner);
                        }
                        this.mInternal.writeRecentAccesses();
                        this.mInternal.mAppOpsCheckingService.writeState();
                        outPrintWriter.println("Current settings written.");
                        return 0;
                    } finally {
                    }
                case 5:
                    this.mInternal.enforceManageAppOpsModes(Binder.getCallingPid(), Binder.getCallingUid(), -1);
                    long clearCallingIdentity2 = Binder.clearCallingIdentity();
                    try {
                        this.mInternal.readRecentAccesses();
                        this.mInternal.mAppOpsCheckingService.readState();
                        outPrintWriter.println("Last settings read.");
                        Binder.restoreCallingIdentity(clearCallingIdentity2);
                        return 0;
                    } finally {
                    }
                case 6:
                    parseUserPackageOp = parseUserPackageOp(errPrintWriter, true);
                    if (parseUserPackageOp < 0) {
                        return parseUserPackageOp;
                    }
                    String str5 = this.packageName;
                    if (str5 != null) {
                        this.mInterface.noteOperation(this.op, this.packageUid, str5, this.attributionTag, true, "appops note shell command", true);
                        return 0;
                    }
                    return -1;
                case 7:
                    parseUserPackageOp = parseUserPackageOp(errPrintWriter, true);
                    if (parseUserPackageOp < 0) {
                        return parseUserPackageOp;
                    }
                    String str6 = this.packageName;
                    if (str6 != null) {
                        this.mInterface.startOperation(this.mToken, this.op, this.packageUid, str6, this.attributionTag, true, true, "appops start shell command", true, 1, -1);
                        return 0;
                    }
                    return -1;
                case '\b':
                    parseUserPackageOp = parseUserPackageOp(errPrintWriter, true);
                    if (parseUserPackageOp >= 0) {
                        String str7 = this.packageName;
                        if (str7 != null) {
                            this.mInterface.finishOperation(this.mToken, this.op, this.packageUid, str7, this.attributionTag);
                            return 0;
                        }
                        return -1;
                    }
                    return parseUserPackageOp;
                default:
                    return handleDefaultCommands(str);
            }
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("AppOps service (appops) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  start [--user <USER_ID>] [--attribution <ATTRIBUTION_TAG>] <PACKAGE | UID> <OP> ");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Starts a given operation for a particular application.", "  stop [--user <USER_ID>] [--attribution <ATTRIBUTION_TAG>] <PACKAGE | UID> <OP> ", "    Stops a given operation for a particular application.", "  set [--user <USER_ID>] <[--uid] PACKAGE | UID> <OP> <MODE>");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Set the mode for a particular application and operation.", "  get [--user <USER_ID>] [--attribution <ATTRIBUTION_TAG>] <PACKAGE | UID> [<OP>]", "    Return the mode for a particular application and optional operation.", "  query-op [--user <USER_ID>] <OP> [<MODE>]");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Print all packages that currently have the given op in the given mode.", "  reset [--user <USER_ID>] [<PACKAGE>]", "    Reset the given application or all applications to default modes.", "  write-settings");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Immediately write pending changes to storage.", "  read-settings", "    Read the last written settings, replacing current state in RAM.", "  options:");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    <PACKAGE> an Android package name or its UID if prefixed by --uid", "    <OP>      an AppOps operation.", "    <MODE>    one of allow, ignore, deny, or default", "    <USER_ID> the user id under which the package is installed. If --user is");
            outPrintWriter.println("              not specified, the current user is assumed.");
        }

        public final int parseUserOpMode(PrintWriter printWriter) {
            this.userId = -2;
            this.opStr = null;
            this.modeStr = null;
            while (true) {
                String nextArg = getNextArg();
                if (nextArg == null) {
                    break;
                }
                if ("--user".equals(nextArg)) {
                    this.userId = UserHandle.parseUserArg(getNextArgRequired());
                } else if (this.opStr == null) {
                    this.opStr = nextArg;
                } else if (this.modeStr == null) {
                    this.modeStr = nextArg;
                    break;
                }
            }
            String str = this.opStr;
            if (str == null) {
                printWriter.println("Error: Operation not specified.");
                return -1;
            }
            int strOpToOp = strOpToOp(printWriter, str);
            this.op = strOpToOp;
            if (strOpToOp < 0) {
                return -1;
            }
            String str2 = this.modeStr;
            if (str2 == null) {
                this.mode = 1;
                return 0;
            }
            int strModeToMode = strModeToMode(printWriter, str2);
            this.mode = strModeToMode;
            return strModeToMode < 0 ? -1 : 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:59:0x00ca, code lost:
        
            if (r11 >= r9.packageName.length()) goto L74;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00d2, code lost:
        
            r2 = java.lang.Integer.parseInt(r9.packageName.substring(1, r11));
            r7 = r9.packageName.charAt(r11);
            r11 = r11 + 1;
            r3 = r11;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x00e4, code lost:
        
            if (r3 >= r9.packageName.length()) goto L110;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x00ec, code lost:
        
            if (r9.packageName.charAt(r3) < '0') goto L111;
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:0x00f4, code lost:
        
            if (r9.packageName.charAt(r3) > '9') goto L109;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x00f6, code lost:
        
            r3 = r3 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x00f9, code lost:
        
            if (r3 <= r11) goto L74;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x00fb, code lost:
        
            r11 = java.lang.Integer.parseInt(r9.packageName.substring(r11, r3));
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x0107, code lost:
        
            if (r7 != 'a') goto L71;
         */
        /* JADX WARN: Code restructure failed: missing block: B:74:0x0109, code lost:
        
            r9.nonpackageUid = android.os.UserHandle.getUid(r2, r11 + 10000);
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x0114, code lost:
        
            if (r7 != 's') goto L74;
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x0116, code lost:
        
            r9.nonpackageUid = android.os.UserHandle.getUid(r2, r11);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int parseUserPackageOp(java.io.PrintWriter r10, boolean r11) {
            /*
                Method dump skipped, instructions count: 345
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.Shell.parseUserPackageOp(java.io.PrintWriter, boolean):int");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StartedCallback implements IBinder.DeathRecipient {
        public final IAppOpsStartedCallback mCallback;
        public final int mCallingPid;
        public final int mCallingUid;
        public final int mWatchingUid;

        public StartedCallback(IAppOpsStartedCallback iAppOpsStartedCallback, int i, int i2, int i3) {
            this.mCallback = iAppOpsStartedCallback;
            this.mWatchingUid = i;
            this.mCallingUid = i2;
            this.mCallingPid = i3;
            try {
                iAppOpsStartedCallback.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            AppOpsService.this.stopWatchingStarted(this.mCallback);
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "StartedCallback{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" watchinguid=");
            UserHandle.formatUid(m, this.mWatchingUid);
            m.append(" from uid=");
            UserHandle.formatUid(m, this.mCallingUid);
            m.append(" pid=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(m, this.mCallingPid, '}');
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class UidState {
        public final ArrayMap pkgOps = new ArrayMap();
        public final int uid;

        public UidState(int i) {
            this.uid = i;
        }

        public final void clear() {
            AppOpsService appOpsService = AppOpsService.this;
            AppOpsCheckingServiceInterface appOpsCheckingServiceInterface = appOpsService.mAppOpsCheckingService;
            int i = this.uid;
            appOpsCheckingServiceInterface.removeUid(i);
            for (int i2 = 0; i2 < this.pkgOps.size(); i2++) {
                appOpsService.mAppOpsCheckingService.removePackage(UserHandle.getUserId(i), (String) this.pkgOps.keyAt(i2));
            }
        }

        public final void dump(PrintWriter printWriter, long j) {
            AppOpsUidStateTrackerImpl appOpsUidStateTrackerImpl = (AppOpsUidStateTrackerImpl) AppOpsService.this.getUidStateTracker();
            SparseIntArray sparseIntArray = appOpsUidStateTrackerImpl.mUidStates;
            int i = this.uid;
            int i2 = sparseIntArray.get(i, 700);
            int i3 = appOpsUidStateTrackerImpl.mPendingUidStates.get(i, i2);
            printWriter.print("    state=");
            printWriter.println(AppOpsManager.getUidStateName(i2));
            if (i2 != i3) {
                printWriter.print("    pendingState=");
                printWriter.println(AppOpsManager.getUidStateName(i3));
            }
            int i4 = appOpsUidStateTrackerImpl.mCapability.get(i, 0);
            int i5 = appOpsUidStateTrackerImpl.mPendingCapability.get(i, i4);
            printWriter.print("    capability=");
            ActivityManager.printCapabilitiesFull(printWriter, i4);
            printWriter.println();
            if (i4 != i5) {
                printWriter.print("    pendingCapability=");
                ActivityManager.printCapabilitiesFull(printWriter, i5);
                printWriter.println();
            }
            boolean z = appOpsUidStateTrackerImpl.mAppWidgetVisible.get(i, false);
            boolean z2 = appOpsUidStateTrackerImpl.mPendingAppWidgetVisible.get(i, z);
            printWriter.print("    appWidgetVisible=");
            printWriter.println(z);
            if (z != z2) {
                printWriter.print("    pendingAppWidgetVisible=");
                printWriter.println(z2);
            }
            long j2 = appOpsUidStateTrackerImpl.mPendingCommitTime.get(i, 0L);
            if (j2 != 0) {
                printWriter.print("    pendingStateCommitTime=");
                TimeUtils.formatDuration(j2, j, printWriter);
                printWriter.println();
            }
        }

        public final int evalMode(int i, int i2) {
            return ((AppOpsUidStateTrackerImpl) AppOpsService.this.getUidStateTracker()).evalMode(this.uid, i, i2);
        }

        public final int getState() {
            AppOpsUidStateTrackerImpl appOpsUidStateTrackerImpl = (AppOpsUidStateTrackerImpl) AppOpsService.this.getUidStateTracker();
            int i = this.uid;
            appOpsUidStateTrackerImpl.updateUidPendingStateIfNeeded(i);
            return appOpsUidStateTrackerImpl.mUidStates.get(i, 700);
        }
    }

    /* renamed from: -$$Nest$mcheckAudioOperationImpl, reason: not valid java name */
    public static int m230$$Nest$mcheckAudioOperationImpl(AppOpsService appOpsService, int i, int i2, int i3, String str) {
        int i4;
        AudioRestrictionManager.Restriction restriction;
        SparseBooleanArray sparseBooleanArray;
        AudioRestrictionManager audioRestrictionManager = appOpsService.mAudioRestrictionManager;
        synchronized (audioRestrictionManager) {
            try {
                int i5 = audioRestrictionManager.mCameraAudioRestriction;
                if (i5 == 0 || !((i == 3 || (i == 28 && i5 == 3)) && (sparseBooleanArray = (SparseBooleanArray) AudioRestrictionManager.CAMERA_AUDIO_RESTRICTIONS.get(i)) != null && sparseBooleanArray.get(i2))) {
                    SparseArray sparseArray = (SparseArray) audioRestrictionManager.mZenModeAudioRestrictions.get(i);
                    i4 = (sparseArray == null || (restriction = (AudioRestrictionManager.Restriction) sparseArray.get(i2)) == null || restriction.exceptionPackages.contains(str)) ? 0 : restriction.mode;
                    if (i4 != 0) {
                        Slog.w("AudioRestriction", "checkZenModeRestrictionLocked return mode: " + i4);
                    } else {
                        i4 = 0;
                    }
                } else {
                    Slog.w("AudioRestriction", "checkAudioOperation: MODE_IGNORED camera audio restrictions");
                    i4 = 1;
                }
            } finally {
            }
        }
        return i4 != 0 ? i4 : appOpsService.checkOperation(i, i3, str);
    }

    /* renamed from: -$$Nest$mcheckOperationImpl, reason: not valid java name */
    public static int m231$$Nest$mcheckOperationImpl(AppOpsService appOpsService, int i, int i2, String str, String str2, int i3, boolean z) {
        int evalMode;
        int uidMode;
        appOpsService.verifyIncomingOp(i);
        if (!appOpsService.isValidVirtualDeviceId(i3)) {
            StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i3, "checkOperationImpl returned MODE_IGNORED as virtualDeviceId ", " is invalid : packageName ", str, " uid ");
            m.append(i2);
            m.append(" code ");
            m.append(i);
            Slog.w("AppOps", m.toString());
        } else {
            if (!appOpsService.isIncomingPackageValid(UserHandle.getUserId(i2), str)) {
                if (DEBUG_MID || DEBUG) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "checkOperationImpl::isIncomingPackageValid return opToDefaultMode(code): packageName ", str, " uid ", " code "), i, "AppOps");
                }
                return AppOpsManager.opToDefaultMode(i);
            }
            String resolvePackageName = AppOpsManager.resolvePackageName(i2, str);
            if (resolvePackageName != null) {
                try {
                    PackageVerificationResult verifyAndGetBypass = appOpsService.verifyAndGetBypass(i2, resolvePackageName, null, null, false);
                    if (!(!ArrayUtils.contains(OPS_RESTRICTED_ON_SUSPEND, i) ? false : ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).isPackageSuspended(UserHandle.getUserId(i2), resolvePackageName))) {
                        synchronized (appOpsService) {
                            try {
                                if (!appOpsService.isOpRestrictedLocked(i2, i, resolvePackageName, str2, i3, verifyAndGetBypass.bypass, true)) {
                                    if (isOpAllowedForUid(i2)) {
                                        return 0;
                                    }
                                    int opToSwitch = AppOpsManager.opToSwitch(i);
                                    UidState uidStateLocked = appOpsService.getUidStateLocked(i2, false);
                                    if (uidStateLocked != null && (uidMode = appOpsService.mAppOpsCheckingService.getUidMode(uidStateLocked.uid, opToSwitch, appOpsService.getPersistentId(i3))) != AppOpsManager.opToDefaultMode(opToSwitch)) {
                                        return z ? uidMode : uidStateLocked.evalMode(opToSwitch, uidMode);
                                    }
                                    Ops opsLocked = appOpsService.getOpsLocked(i2, resolvePackageName, null, false, verifyAndGetBypass.bypass, false);
                                    Op opLocked = opsLocked == null ? null : appOpsService.getOpLocked(opsLocked, opToSwitch, i2, false);
                                    if (opLocked == null) {
                                        return AppOpsManager.opToDefaultMode(opToSwitch);
                                    }
                                    if (z) {
                                        evalMode = appOpsService.mAppOpsCheckingService.getPackageMode(opLocked.op, UserHandle.getUserId(opLocked.uid), opLocked.packageName);
                                    } else {
                                        UidState uidState = opLocked.uidState;
                                        int i4 = opLocked.op;
                                        evalMode = uidState.evalMode(i4, appOpsService.mAppOpsCheckingService.getPackageMode(i4, UserHandle.getUserId(opLocked.uid), opLocked.packageName));
                                    }
                                    return evalMode;
                                }
                                if (!DEBUG_MID) {
                                    if (DEBUG) {
                                    }
                                }
                                Slog.w("AppOps", "checkOperationUnchecked return MODE_IGNORED because isOpRestrictedLocked is true: packageName " + resolvePackageName + " uid " + i2 + " code " + i);
                            } finally {
                            }
                        }
                    } else if (DEBUG_MID || DEBUG) {
                        HeapdumpWatcher$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "checkOperationUnchecked return MODE_IGNORED because isOpRestrictedDueToSuspend is true: packageName ", resolvePackageName, " uid ", " code "), i, "AppOps");
                    }
                } catch (SecurityException e) {
                    logVerifyAndGetBypassFailure(i2, e, "checkOperation");
                    return AppOpsManager.opToDefaultMode(i);
                }
            } else if (DEBUG_MID || DEBUG) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "checkOperationImpl return MODE+IGNORE because resolvedPackageName is null: packageName ", str, " uid ", " code "), i, "AppOps");
            }
        }
        return 1;
    }

    /* renamed from: -$$Nest$mfinishOperationImpl, reason: not valid java name */
    public static void m232$$Nest$mfinishOperationImpl(AppOpsService appOpsService, IBinder iBinder, int i, int i2, String str, String str2, int i3) {
        String resolvePackageName;
        appOpsService.verifyIncomingUid(i2);
        appOpsService.verifyIncomingOp(i);
        if (!appOpsService.isValidVirtualDeviceId(i3)) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i3, "finishOperationImpl was a no-op as virtualDeviceId ", " is invalid", "AppOps");
        } else if (appOpsService.isIncomingPackageValid(UserHandle.getUserId(i2), str) && (resolvePackageName = AppOpsManager.resolvePackageName(i2, str)) != null) {
            appOpsService.finishOperationUnchecked(iBinder, i, i2, resolvePackageName, str2, i3);
        }
    }

    /* renamed from: -$$Nest$mfinishProxyOperationImpl, reason: not valid java name */
    public static void m233$$Nest$mfinishProxyOperationImpl(AppOpsService appOpsService, IBinder iBinder, int i, AttributionSource attributionSource, boolean z) {
        String resolvePackageName;
        appOpsService.getClass();
        int uid = attributionSource.getUid();
        String packageName = attributionSource.getPackageName();
        String attributionTag = attributionSource.getAttributionTag();
        int nextUid = attributionSource.getNextUid();
        int deviceId = attributionSource.getDeviceId();
        String nextPackageName = attributionSource.getNextPackageName();
        String nextAttributionTag = attributionSource.getNextAttributionTag();
        boolean z2 = z && appOpsService.isCallerAndAttributionTrusted(attributionSource);
        appOpsService.verifyIncomingProxyUid(attributionSource);
        appOpsService.verifyIncomingOp(i);
        if (!appOpsService.isValidVirtualDeviceId(deviceId)) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(deviceId, "finishProxyOperationImpl was a no-op as virtualDeviceId ", " is invalid", "AppOps");
            return;
        }
        if (appOpsService.isIncomingPackageValid(UserHandle.getUserId(uid), packageName) && appOpsService.isIncomingPackageValid(UserHandle.getUserId(nextUid), nextPackageName) && (resolvePackageName = AppOpsManager.resolvePackageName(uid, packageName)) != null) {
            if (!z2) {
                appOpsService.finishOperationUnchecked(iBinder, i, uid, resolvePackageName, attributionTag, deviceId);
            }
            String resolvePackageName2 = AppOpsManager.resolvePackageName(nextUid, nextPackageName);
            if (resolvePackageName2 == null) {
                return;
            }
            appOpsService.finishOperationUnchecked(iBinder, i, nextUid, resolvePackageName2, nextAttributionTag, deviceId);
        }
    }

    /* renamed from: -$$Nest$mnoteOperationImpl, reason: not valid java name */
    public static SyncNotedAppOp m234$$Nest$mnoteOperationImpl(AppOpsService appOpsService, int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2) {
        appOpsService.verifyIncomingUid(i2);
        appOpsService.verifyIncomingOp(i);
        if (!appOpsService.isValidVirtualDeviceId(i3)) {
            StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i3, "noteOperationImpl returned MODE_IGNORED as virtualDeviceId ", " is invalid package: ", str, " and uid: ");
            m.append(i2);
            m.append(" code ");
            m.append(i);
            Slog.w("AppOps", m.toString());
            return new SyncNotedAppOp(1, i, str2, str);
        }
        if (!appOpsService.isIncomingPackageValid(UserHandle.getUserId(i2), str)) {
            if (i == 111) {
                Slog.e("AppOps", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i2, "noting OP_BLUETOOTH_CONNECT returned MODE_ERRORED as incoming package: ", str, " and uid: ", " is invalid"));
            }
            if (DEBUG_MID || DEBUG) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "noteOperationImpl return MODE_ERROR because isIncomingPackageValid is false: packageName ", str, " uid ", " code "), i, "AppOps");
            }
            return new SyncNotedAppOp(2, i, str2, str);
        }
        String resolvePackageName = AppOpsManager.resolvePackageName(i2, str);
        if (resolvePackageName != null) {
            return appOpsService.noteOperationUnchecked(i, i2, resolvePackageName, str2, i3, -1, null, null, 0, 1, z, str3, z2);
        }
        if (DEBUG_MID || DEBUG) {
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "noteOperationImpl return MODE_IGNORED because resolvedPackageName is null: packageName ", str, " uid ", " code "), i, "AppOps");
        }
        return new SyncNotedAppOp(1, i, str2, str);
    }

    /* renamed from: -$$Nest$mnoteProxyOperationImpl, reason: not valid java name */
    public static SyncNotedAppOp m235$$Nest$mnoteProxyOperationImpl(AppOpsService appOpsService, int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3) {
        String str2;
        String str3;
        int i2;
        int i3;
        int i4;
        appOpsService.getClass();
        int uid = attributionSource.getUid();
        String packageName = attributionSource.getPackageName();
        String attributionTag = attributionSource.getAttributionTag();
        int deviceId = attributionSource.getDeviceId();
        int nextUid = attributionSource.getNextUid();
        String nextPackageName = attributionSource.getNextPackageName();
        String nextAttributionTag = attributionSource.getNextAttributionTag();
        int nextDeviceId = attributionSource.getNextDeviceId();
        appOpsService.verifyIncomingProxyUid(attributionSource);
        appOpsService.verifyIncomingOp(i);
        if (!appOpsService.isValidVirtualDeviceId(deviceId)) {
            Slog.w("AppOps", "noteProxyOperationImpl returned MODE_IGNORED as virtualDeviceId " + deviceId + " is invalid");
            return new SyncNotedAppOp(1, i, nextAttributionTag, nextPackageName);
        }
        if (!appOpsService.isIncomingPackageValid(UserHandle.getUserId(nextUid), nextPackageName) || !appOpsService.isIncomingPackageValid(UserHandle.getUserId(uid), packageName)) {
            return new SyncNotedAppOp(2, i, nextAttributionTag, nextPackageName);
        }
        boolean z4 = z3 && appOpsService.isCallerAndAttributionTrusted(attributionSource);
        String resolvePackageName = AppOpsManager.resolvePackageName(uid, packageName);
        if (resolvePackageName == null) {
            return new SyncNotedAppOp(1, i, nextAttributionTag, nextPackageName);
        }
        boolean z5 = appOpsService.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", -1, uid) == 0 || (Binder.getCallingUid() == nextUid);
        if (z4) {
            str2 = nextAttributionTag;
            str3 = nextPackageName;
            i2 = deviceId;
            i3 = uid;
            i4 = nextUid;
        } else {
            i2 = deviceId;
            i3 = uid;
            SyncNotedAppOp noteOperationUnchecked = appOpsService.noteOperationUnchecked(i, uid, resolvePackageName, attributionTag, deviceId, -1, null, null, 0, z5 ? 2 : 4, !z5, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("proxy ", str), z2);
            if (noteOperationUnchecked.getOpMode() != 0) {
                return new SyncNotedAppOp(noteOperationUnchecked.getOpMode(), i, nextAttributionTag, nextPackageName);
            }
            str2 = nextAttributionTag;
            str3 = nextPackageName;
            i4 = nextUid;
        }
        String resolvePackageName2 = AppOpsManager.resolvePackageName(i4, str3);
        if (resolvePackageName2 == null) {
            return new SyncNotedAppOp(1, i, str2, str3);
        }
        return appOpsService.noteOperationUnchecked(i, i4, resolvePackageName2, str2, nextDeviceId, i3, resolvePackageName, attributionTag, i2, z5 ? 8 : 16, z, str, z2);
    }

    /* renamed from: -$$Nest$mstartOperationImpl, reason: not valid java name */
    public static SyncNotedAppOp m236$$Nest$mstartOperationImpl(AppOpsService appOpsService, IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5) {
        int checkOperation;
        int checkOperation2;
        appOpsService.verifyIncomingUid(i2);
        appOpsService.verifyIncomingOp(i);
        if (!appOpsService.isValidVirtualDeviceId(i3)) {
            Slog.w("AppOps", "startOperationImpl returned MODE_IGNORED as virtualDeviceId " + i3 + " is invalid");
            return new SyncNotedAppOp(1, i, str2, str);
        }
        if (!appOpsService.isIncomingPackageValid(UserHandle.getUserId(i2), str)) {
            if (DEBUG_MID || DEBUG) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "startOperationImpl::isIncomingPackageValid is false: packageName ", str, " uid ", " code "), i, "AppOps");
            }
            return new SyncNotedAppOp(2, i, str2, str);
        }
        if (AppOpsManager.resolvePackageName(i2, str) == null) {
            if (DEBUG_MID || DEBUG) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i2, "startOperationImpl::resolvedPackageName is null: packageName ", str, " uid ", " code "), i, "AppOps");
            }
            return new SyncNotedAppOp(1, i, str2, str);
        }
        if ((i == 102 || i == 120 || i == 135) && (checkOperation = appOpsService.checkOperation(27, i2, str)) != 0) {
            if (DEBUG_MID || DEBUG) {
                StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i2, "startOperationImpl::checkOperation(OP_RECORD_AUDIO: packageName ", str, " uid ", " code ");
                m.append(i);
                m.append(" result ");
                m.append(checkOperation);
                Slog.w("AppOps", m.toString());
            }
            return new SyncNotedAppOp(checkOperation, i, str2, str);
        }
        if (i != 134 || (checkOperation2 = appOpsService.checkOperation(26, i2, str)) == 0) {
            return appOpsService.startOperationUnchecked(iBinder, i, i2, str, str2, i3, -1, null, null, 0, 1, z, z2, str3, z3, i4, i5);
        }
        if (DEBUG_MID || DEBUG) {
            StringBuilder m2 = StorageManagerService$$ExternalSyntheticOutline0.m(i2, "startOperationImpl::checkOperation(OP_CAMERA: packageName ", str, " uid ", " code ");
            m2.append(i);
            m2.append(" result ");
            m2.append(checkOperation2);
            Slog.w("AppOps", m2.toString());
        }
        return new SyncNotedAppOp(checkOperation2, i, str2, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x02ad A[Catch: all -> 0x017d, TryCatch #0 {all -> 0x017d, blocks: (B:54:0x0127, B:56:0x0149, B:58:0x014d, B:59:0x0180, B:60:0x0187, B:73:0x018c, B:75:0x01ce, B:77:0x01e4, B:79:0x01e8, B:80:0x0227, B:81:0x022e, B:85:0x02a9, B:87:0x02ad, B:88:0x02e4, B:92:0x023b, B:93:0x0240, B:98:0x025f, B:100:0x0263, B:101:0x02a2, B:102:0x02a7), top: B:53:0x0127 }] */
    /* renamed from: -$$Nest$mstartProxyOperationImpl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.app.SyncNotedAppOp m237$$Nest$mstartProxyOperationImpl(com.android.server.appop.AppOpsService r30, android.os.IBinder r31, int r32, android.content.AttributionSource r33, boolean r34, boolean r35, java.lang.String r36, boolean r37, boolean r38, int r39, int r40, int r41) {
        /*
            Method dump skipped, instructions count: 945
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.m237$$Nest$mstartProxyOperationImpl(com.android.server.appop.AppOpsService, android.os.IBinder, int, android.content.AttributionSource, boolean, boolean, java.lang.String, boolean, boolean, int, int, int):android.app.SyncNotedAppOp");
    }

    static {
        boolean equals = "0x4948".equals(SystemProperties.get("ro.boot.debug_level", "unknown"));
        DEBUG = equals;
        DEBUG_MID = "0x494d".equals(SystemProperties.get("ro.boot.debug_level", "unknown"));
        WRITE_DELAY = equals ? 1000L : 1800000L;
        OPS_RESTRICTED_ON_SUSPEND = new int[]{28, 27, 26, 3};
        NON_PACKAGE_UIDS = new int[]{0, 1001, 1002, 1041, 1027, 1073, 2000};
    }

    public AppOpsService(File file, File file2, Handler handler, Context context) {
        new ArraySet();
        AttributedOp.OpEventProxyInfoPool opEventProxyInfoPool = new AttributedOp.OpEventProxyInfoPool(3);
        this.mOpEventProxyInfoPool = opEventProxyInfoPool;
        AttributedOp.InProgressStartOpEventPool inProgressStartOpEventPool = new AttributedOp.InProgressStartOpEventPool(3);
        inProgressStartOpEventPool.mOpEventProxyInfoPool = opEventProxyInfoPool;
        this.mInProgressStartOpEventPool = inProgressStartOpEventPool;
        this.mAppOpsManagerInternal = new AppOpsManagerInternalImpl();
        this.dpmi = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        SparseArray sparseArray = new SparseArray();
        this.mKnownDeviceIds = sparseArray;
        this.mPlatformCompat = IPlatformCompat.Stub.asInterface(ServiceManager.getService("platform_compat"));
        this.mAsyncOpWatchers = new ArrayMap();
        this.mUnforwardedAsyncNotedOps = new ArrayMap();
        this.mOpModeWatchers = new SparseArray();
        this.mPackageModeWatchers = new ArrayMap();
        this.mWriteRunner = new AnonymousClass1(this, 0);
        this.mUidStates = new SparseArray();
        this.mHistoricalRegistry = new HistoricalRegistry(this);
        this.mOpUserRestrictions = new ArrayMap();
        this.mOpGlobalRestrictions = new ArrayMap();
        this.mCheckOpsDelegateDispatcher = new CheckOpsDelegateDispatcher(null, null);
        this.mSwitchedOps = new SparseArray();
        this.mSampledPackage = null;
        this.mSampledAppOpCode = -1;
        this.mAcceptableLeftDistance = 0;
        this.mRarelyUsedPackages = new ArraySet();
        this.mIgnoredCallback = null;
        this.mModeWatchers = new ArrayMap();
        this.mActiveWatchers = new ArrayMap();
        this.mStartedWatchers = new ArrayMap();
        this.mNotedWatchers = new ArrayMap();
        this.mAudioRestrictionManager = new AudioRestrictionManager();
        this.mOnPackageUpdatedReceiver = new AnonymousClass3(this, 0);
        this.mContext = context;
        sparseArray.put(0, "default:0");
        this.mPermissionAccessInformationController = new PermissionAccessInformationController(context);
        for (int i = 0; i < 149; i++) {
            int opToSwitch = AppOpsManager.opToSwitch(i);
            SparseArray sparseArray2 = this.mSwitchedOps;
            sparseArray2.put(opToSwitch, ArrayUtils.appendInt((int[]) sparseArray2.get(opToSwitch), i));
        }
        if (PermissionManager.USE_ACCESS_CHECKING_SERVICE) {
            this.mAppOpsCheckingService = new AppOpsCheckingServiceTracingDecorator((AppOpsCheckingServiceInterface) LocalServices.getService(AppOpsCheckingServiceInterface.class));
        } else {
            this.mAppOpsCheckingService = new AppOpsCheckingServiceTracingDecorator(new AppOpsCheckingServiceImpl(file2, this, handler, context));
        }
        this.mAppOpsCheckingService.addAppOpsModeChangedListener(new AnonymousClass2());
        this.mAppOpsRestrictions = new AppOpsRestrictionsImpl(context, handler, new AppOpsService$$ExternalSyntheticLambda6(this));
        LockGuard.installLock(this, 0, false);
        this.mStorageFile = new AtomicFile(file2, "appops_legacy");
        AtomicFile atomicFile = new AtomicFile(file, "appops_accesses");
        this.mRecentAccessesFile = atomicFile;
        this.mRecentAccessPersistence = new AppOpsRecentAccessPersistence(atomicFile, this);
        this.mHandler = handler;
        this.mConstants = new Constants(handler);
        readRecentAccesses();
        this.mAppOpsCheckingService.readState();
    }

    public static HashMap addCallbacks(HashMap hashMap, int i, int i2, String str, int i3, ArraySet arraySet) {
        if (arraySet == null) {
            return hashMap;
        }
        if (hashMap == null) {
            hashMap = new HashMap();
        }
        int size = arraySet.size();
        for (int i4 = 0; i4 < size; i4++) {
            ModeCallback modeCallback = (ModeCallback) arraySet.valueAt(i4);
            ArrayList arrayList = (ArrayList) hashMap.get(modeCallback);
            ArrayList addChange = addChange(str, i, i2, i3, arrayList);
            if (addChange != arrayList) {
                hashMap.put(modeCallback, addChange);
            }
        }
        return hashMap;
    }

    public static ArrayList addChange(String str, int i, int i2, int i3, ArrayList arrayList) {
        boolean z = false;
        if (arrayList != null) {
            int size = arrayList.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    break;
                }
                ChangeRec changeRec = (ChangeRec) arrayList.get(i4);
                if (changeRec.op == i && changeRec.pkg.equals(str)) {
                    z = true;
                    break;
                }
                i4++;
            }
        } else {
            arrayList = new ArrayList();
        }
        if (!z) {
            arrayList.add(new ChangeRec(i, i2, i3, str));
        }
        return arrayList;
    }

    public static void checkSystemUid(String str) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException(str.concat(" must by called by the system"));
        }
    }

    public static void dumpHelp(PrintWriter printWriter) {
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "AppOps service (appops) dump options:", "  -h", "    Print this help text.", "  --op [OP]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Limit output to data associated with the given app op code.", "  --mode [MODE]", "    Limit output to data associated with the given app op mode.", "  --package [PACKAGE]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Limit output to data associated with the given package name.", "  --attributionTag [attributionTag]", "    Limit output to data associated with the given attribution tag.", "  --include-discrete [n]");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Include discrete ops limited to n per dimension. Use zero for no limit.", "  --watchers", "    Only output the watcher sections.", "  --history");
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, "    Only output history.", "  --uid-state-changes", "    Include logs about uid state changes.");
    }

    public static void dumpStatesLocked(PrintWriter printWriter, String str, int i, long j, Op op, long j2, SimpleDateFormat simpleDateFormat, Date date) {
        ArrayMap arrayMap;
        int i2;
        int i3;
        String str2;
        String str3;
        int i4;
        int i5;
        String str4;
        long j3;
        String str5;
        String str6;
        Op op2 = op;
        String str7 = "default:0";
        ArrayMap arrayMap2 = (ArrayMap) op2.mDeviceAttributedOps.get("default:0");
        int size = arrayMap2.size();
        int i6 = 0;
        while (i6 < size) {
            if ((i & 4) != 0 && !Objects.equals(arrayMap2.keyAt(i6), str)) {
                arrayMap = arrayMap2;
                i2 = size;
                i3 = i6;
                str2 = str7;
                i6 = i3 + 1;
                str7 = str2;
                arrayMap2 = arrayMap;
                size = i2;
            }
            printWriter.print("        " + ((String) arrayMap2.keyAt(i6)) + "=[\n");
            String str8 = (String) arrayMap2.keyAt(i6);
            ArrayMap arrayMap3 = (ArrayMap) op2.mDeviceAttributedOps.get(str7);
            if (arrayMap3 == null) {
                arrayMap3 = new ArrayMap();
            }
            ArrayMap arrayMap4 = new ArrayMap(1);
            if (arrayMap3.get(str8) != null) {
                arrayMap4.put(str8, ((AttributedOp) arrayMap3.get(str8)).createAttributedOpEntryLocked());
            }
            AppOpsCheckingServiceInterface appOpsCheckingServiceInterface = AppOpsService.this.mAppOpsCheckingService;
            int userId = UserHandle.getUserId(op2.uid);
            int i7 = op2.op;
            AppOpsManager.AttributedOpEntry attributedOpEntry = (AppOpsManager.AttributedOpEntry) new AppOpsManager.OpEntry(i7, appOpsCheckingServiceInterface.getPackageMode(i7, userId, op2.packageName), arrayMap4).getAttributedOpEntries().get(str8);
            ArraySet collectKeys = attributedOpEntry.collectKeys();
            int size2 = collectKeys.size();
            int i8 = 0;
            while (i8 < size2) {
                long longValue = ((Long) collectKeys.valueAt(i8)).longValue();
                int extractUidStateFromKey = AppOpsManager.extractUidStateFromKey(longValue);
                int extractFlagsFromKey = AppOpsManager.extractFlagsFromKey(longValue);
                ArraySet arraySet = collectKeys;
                int i9 = size2;
                long lastAccessTime = attributedOpEntry.getLastAccessTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                int i10 = i6;
                String str9 = str8;
                long lastRejectTime = attributedOpEntry.getLastRejectTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                ArrayMap arrayMap5 = arrayMap2;
                String str10 = str7;
                long lastDuration = attributedOpEntry.getLastDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                AppOpsManager.OpEventProxyInfo lastProxyInfo = attributedOpEntry.getLastProxyInfo(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                if (lastProxyInfo != null) {
                    str3 = lastProxyInfo.getPackageName();
                    String attributionTag = lastProxyInfo.getAttributionTag();
                    i4 = lastProxyInfo.getUid();
                    j3 = 0;
                    i5 = size;
                    str4 = attributionTag;
                } else {
                    str3 = null;
                    i4 = -1;
                    i5 = size;
                    str4 = null;
                    j3 = 0;
                }
                AppOpsManager.AttributedOpEntry attributedOpEntry2 = attributedOpEntry;
                int i11 = i8;
                if (lastAccessTime > j3) {
                    printWriter.print("          ");
                    str5 = "          ";
                    printWriter.print("Access: ");
                    printWriter.print(AppOpsManager.keyToString(longValue));
                    printWriter.print(" ");
                    date.setTime(lastAccessTime);
                    printWriter.print(simpleDateFormat.format(date));
                    printWriter.print(" (");
                    TimeUtils.formatDuration(lastAccessTime - j2, printWriter);
                    printWriter.print(")");
                    if (lastDuration > 0) {
                        printWriter.print(" duration=");
                        TimeUtils.formatDuration(lastDuration, printWriter);
                    }
                    if (i4 >= 0) {
                        printWriter.print(" proxy[");
                        printWriter.print("uid=");
                        printWriter.print(i4);
                        printWriter.print(", pkg=");
                        printWriter.print(str3);
                        printWriter.print(", attributionTag=");
                        printWriter.print(str4);
                        str6 = "]";
                        printWriter.print(str6);
                    } else {
                        str6 = "]";
                    }
                    printWriter.println();
                } else {
                    str5 = "          ";
                    str6 = "]";
                }
                if (lastRejectTime > 0) {
                    printWriter.print(str5);
                    printWriter.print("Reject: ");
                    printWriter.print(AppOpsManager.keyToString(longValue));
                    date.setTime(lastRejectTime);
                    printWriter.print(simpleDateFormat.format(date));
                    printWriter.print(" (");
                    TimeUtils.formatDuration(lastRejectTime - j2, printWriter);
                    printWriter.print(")");
                    if (i4 >= 0) {
                        printWriter.print(" proxy[");
                        printWriter.print("uid=");
                        printWriter.print(i4);
                        printWriter.print(", pkg=");
                        printWriter.print(str3);
                        printWriter.print(", attributionTag=");
                        printWriter.print(str4);
                        printWriter.print(str6);
                    }
                    printWriter.println();
                }
                i8 = i11 + 1;
                op2 = op;
                size2 = i9;
                i6 = i10;
                str8 = str9;
                str7 = str10;
                arrayMap2 = arrayMap5;
                size = i5;
                attributedOpEntry = attributedOpEntry2;
                collectKeys = arraySet;
            }
            arrayMap = arrayMap2;
            i2 = size;
            i3 = i6;
            str2 = str7;
            AttributedOp attributedOp = (AttributedOp) ((ArrayMap) op2.mDeviceAttributedOps.getOrDefault(str2, new ArrayMap())).get(str8);
            if (attributedOp.isRunning()) {
                int size3 = attributedOp.mInProgressEvents.size();
                long j4 = Long.MAX_VALUE;
                long j5 = 0;
                for (int i12 = 0; i12 < size3; i12++) {
                    j4 = Math.min(j4, ((AttributedOp.InProgressStartOpEvent) attributedOp.mInProgressEvents.valueAt(i12)).mStartElapsedTime);
                    j5 = Math.max(j5, r9.mNumUnfinishedStarts);
                }
                printWriter.print("          Running start at: ");
                TimeUtils.formatDuration(j - j4, printWriter);
                printWriter.println();
                if (j5 > 1) {
                    printWriter.print("          startNesting=");
                    printWriter.println(j5);
                }
            }
            printWriter.print("        ]\n");
            i6 = i3 + 1;
            str7 = str2;
            arrayMap2 = arrayMap;
            size = i2;
        }
    }

    public static void ensureHistoricalOpRequestIsValid(int i, String str, String str2, List list, int i2, long j, long j2, int i3) {
        if ((i2 & 1) != 0) {
            Preconditions.checkArgument(i != -1);
        } else {
            Preconditions.checkArgument(i == -1);
        }
        if ((i2 & 2) != 0) {
            Objects.requireNonNull(str);
        } else {
            Preconditions.checkArgument(str == null);
        }
        if ((i2 & 4) == 0) {
            Preconditions.checkArgument(str2 == null);
        }
        if ((i2 & 8) != 0) {
            Objects.requireNonNull(list);
        } else {
            Preconditions.checkArgument(list == null);
        }
        Preconditions.checkFlagsArgument(i2, 15);
        Preconditions.checkArgumentNonnegative(j);
        Preconditions.checkArgument(j2 > j);
        Preconditions.checkFlagsArgument(i3, 31);
    }

    public static AppOpsManager.OpEntry getOpEntryForResult(Op op, String str) {
        ArrayMap arrayMap = (ArrayMap) op.mDeviceAttributedOps.get(str);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
        }
        ArrayMap arrayMap2 = new ArrayMap(arrayMap.size());
        for (int i = 0; i < arrayMap.size(); i++) {
            arrayMap2.put((String) arrayMap.keyAt(i), ((AttributedOp) arrayMap.valueAt(i)).createAttributedOpEntryLocked());
        }
        AppOpsCheckingServiceInterface appOpsCheckingServiceInterface = AppOpsService.this.mAppOpsCheckingService;
        int userId = UserHandle.getUserId(op.uid);
        int i2 = op.op;
        return new AppOpsManager.OpEntry(i2, appOpsCheckingServiceInterface.getPackageMode(i2, userId, op.packageName), arrayMap2);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0012  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] getPackagesForUid(int r1) {
        /*
            android.content.pm.IPackageManager r0 = android.app.AppGlobals.getPackageManager()
            if (r0 == 0) goto Lf
            android.content.pm.IPackageManager r0 = android.app.AppGlobals.getPackageManager()     // Catch: android.os.RemoteException -> Lf
            java.lang.String[] r1 = r0.getPackagesForUid(r1)     // Catch: android.os.RemoteException -> Lf
            goto L10
        Lf:
            r1 = 0
        L10:
            if (r1 != 0) goto L14
            java.lang.String[] r1 = libcore.util.EmptyArray.STRING
        L14:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.getPackagesForUid(int):java.lang.String[]");
    }

    public static boolean isAttributionInPackage(AndroidPackage androidPackage, String str) {
        if (androidPackage == null) {
            return false;
        }
        if (str == null) {
            return true;
        }
        if (androidPackage.getAttributions() != null) {
            int size = androidPackage.getAttributions().size();
            for (int i = 0; i < size; i++) {
                if (((ParsedAttribution) androidPackage.getAttributions().get(i)).getTag().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAttributionTagDefined(String str, String str2, String str3) {
        AndroidPackage androidPackage;
        if (str == null) {
            return false;
        }
        if (str3 == null) {
            return true;
        }
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        if (str2 == null || (androidPackage = packageManagerInternal.getPackage(str2)) == null || !isAttributionInPackage(androidPackage, str3)) {
            return isAttributionInPackage(packageManagerInternal.getPackage(str), str3);
        }
        return true;
    }

    public static boolean isOpAllowedForUid(int i) {
        int appId = UserHandle.getAppId(i);
        return Flags.runtimePermissionAppopsMappingEnabled() && (appId == 0 || appId == 1000);
    }

    public static void logVerifyAndGetBypassFailure(int i, SecurityException securityException, String str) {
        if (Process.isIsolated(i)) {
            Slog.e("AppOps", "Cannot " + str + ": isolated UID");
            return;
        }
        if (UserHandle.getAppId(i) >= 10000) {
            Slog.e("AppOps", "Cannot ".concat(str), securityException);
            return;
        }
        Slog.e("AppOps", "Cannot " + str + ": non-application UID " + i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int resolveUid(String str) {
        char c;
        if (str == null) {
            return -1;
        }
        switch (str.hashCode()) {
            case -1336564963:
                if (str.equals("dumpstate")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -31178072:
                if (str.equals("cameraserver")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3506402:
                if (str.equals("root")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 103772132:
                if (str.equals("media")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 109403696:
                if (str.equals("shell")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1344606873:
                if (str.equals("audioserver")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return -1;
    }

    public static boolean shouldStartForMode(int i, boolean z) {
        return i == 0 || (i == 3 && z);
    }

    public final void addHistoricalOps(AppOpsManager.HistoricalOps historicalOps) {
        addHistoricalOps_enforcePermission();
        HistoricalRegistry historicalRegistry = this.mHistoricalRegistry;
        synchronized (historicalRegistry.mInMemoryLock) {
            try {
                if (!historicalRegistry.isPersistenceInitializedMLocked()) {
                    Slog.d(HistoricalRegistry.LOG_TAG, "Interaction before persistence initialized");
                    return;
                }
                historicalOps.offsetBeginAndEndTime(historicalRegistry.mBaseSnapshotInterval);
                historicalRegistry.mPendingWrites.offerFirst(historicalOps);
                ArrayList arrayList = new ArrayList(historicalRegistry.mPendingWrites);
                historicalRegistry.mPendingWrites.clear();
                historicalRegistry.persistPendingHistory(arrayList);
            } finally {
            }
        }
    }

    public final int checkAudioOperation(int i, int i2, int i3, String str) {
        CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = this.mCheckOpsDelegateDispatcher;
        AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate = checkOpsDelegateDispatcher.mPolicy;
        AppOpsService appOpsService = AppOpsService.this;
        if (checkOpsDelegate != null) {
            return checkOpsDelegateDispatcher.mCheckOpsDelegate != null ? checkOpsDelegate.checkAudioOperation(i, i2, i3, str, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda4(checkOpsDelegateDispatcher, 0)) : checkOpsDelegate.checkAudioOperation(i, i2, i3, str, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(appOpsService, 0));
        }
        AppOpsManagerInternal.CheckOpsDelegate checkOpsDelegate2 = checkOpsDelegateDispatcher.mCheckOpsDelegate;
        return checkOpsDelegate2 != null ? checkOpsDelegate2.checkAudioOperation(i, i2, i3, str, new AppOpsService$CheckOpsDelegateDispatcher$$ExternalSyntheticLambda5(appOpsService, 0)) : m230$$Nest$mcheckAudioOperationImpl(appOpsService, i, i2, i3, str);
    }

    public final int checkOperation(int i, int i2, String str) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, null, 0, false);
    }

    public final int checkOperationForDevice(int i, int i2, String str, int i3) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, null, i3, false);
    }

    public final int checkOperationRaw(int i, int i2, String str, String str2) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, str2, 0, true);
    }

    public final int checkOperationRawForDevice(int i, int i2, String str, String str2, int i3) {
        return this.mCheckOpsDelegateDispatcher.checkOperation(i, i2, str, str2, i3, true);
    }

    public final int checkPackage(int i, String str) {
        Objects.requireNonNull(str);
        try {
            verifyAndGetBypass(i, str, null, null, true);
            if (resolveUid(str) == i) {
                return 0;
            }
            if (getPackageManagerInternal().getPackageStateInternal(str) != null) {
                int userId = UserHandle.getUserId(i);
                if (!((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).filterAppAccess(Binder.getCallingUid(), userId, str, true)) {
                    return 0;
                }
            }
            return 2;
        } catch (SecurityException unused) {
            return 2;
        }
    }

    public final void clearHistory() {
        clearHistory_enforcePermission();
        HistoricalRegistry historicalRegistry = this.mHistoricalRegistry;
        historicalRegistry.clearHistoricalRegistry();
        historicalRegistry.mDiscreteRegistry.clearHistory();
    }

    public final void collectAsyncNotedOp(final int i, final int i2, final String str, final String str2, String str3, boolean z, int i3) {
        boolean[] zArr;
        Objects.requireNonNull(str3);
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this) {
                try {
                    Pair pair = new Pair(str, Integer.valueOf(i));
                    RemoteCallbackList remoteCallbackList = (RemoteCallbackList) this.mAsyncOpWatchers.get(pair);
                    final AsyncNotedAppOp asyncNotedAppOp = new AsyncNotedAppOp(i2, callingUid, str2, str3, System.currentTimeMillis());
                    final boolean[] zArr2 = {false};
                    if ((i3 & 9) != 0 && z) {
                        switchPackageIfBootTimeOrRarelyUsedLocked(str);
                        if (Objects.equals(this.mSampledPackage, str)) {
                            reportRuntimeAppOpAccessMessageInternalLocked(i, i2, str, str2, str3);
                        }
                    }
                    if (remoteCallbackList != null) {
                        zArr = zArr2;
                        remoteCallbackList.broadcast(new Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda14
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AsyncNotedAppOp asyncNotedAppOp2 = asyncNotedAppOp;
                                boolean[] zArr3 = zArr2;
                                int i4 = i2;
                                String str4 = str;
                                int i5 = i;
                                String str5 = str2;
                                try {
                                    ((IAppOpsAsyncNotedCallback) obj).opNoted(asyncNotedAppOp2);
                                    zArr3[0] = true;
                                } catch (RemoteException e) {
                                    StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i4, "Could not forward noteOp of ", " to ", str4, "/");
                                    m.append(i5);
                                    m.append("(");
                                    m.append(str5);
                                    m.append(")");
                                    Slog.e("AppOps", m.toString(), e);
                                }
                            }
                        });
                    } else {
                        zArr = zArr2;
                    }
                    if (!zArr[0]) {
                        ArrayList arrayList = (ArrayList) this.mUnforwardedAsyncNotedOps.get(pair);
                        if (arrayList == null) {
                            arrayList = new ArrayList(1);
                            this.mUnforwardedAsyncNotedOps.put(pair, arrayList);
                        }
                        arrayList.add(asyncNotedAppOp);
                        if (arrayList.size() > 10) {
                            arrayList.remove(0);
                        }
                    }
                } finally {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void collectNoteOpCallsForValidation(String str, int i, String str2, long j) {
    }

    public final ArrayList collectOps(Ops ops, int[] iArr, String str) {
        int i = 0;
        boolean z = this.mContext.checkPermission("android.permission.GET_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid()) == 0;
        if (iArr == null) {
            ArrayList arrayList = new ArrayList();
            while (i < ops.size()) {
                Op op = (Op) ops.valueAt(i);
                if (!AppOpsManager.opRestrictsRead(op.op) || z) {
                    arrayList.add(getOpEntryForResult(op, str));
                }
                i++;
            }
            return arrayList;
        }
        ArrayList arrayList2 = null;
        while (i < iArr.length) {
            Op op2 = (Op) ops.get(iArr[i]);
            if (op2 != null) {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                }
                if (!AppOpsManager.opRestrictsRead(op2.op) || z) {
                    arrayList2.add(getOpEntryForResult(op2, str));
                }
            }
            i++;
        }
        return arrayList2;
    }

    public final RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() {
        RuntimeAppOpAccessMessage runtimeAppOpAccessMessage;
        boolean z = ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getInstrumentationSourceUid(Binder.getCallingUid()) != -1;
        if (Binder.getCallingPid() != Process.myPid() && !z) {
            return null;
        }
        this.mContext.enforcePermission("android.permission.GET_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
        synchronized (this) {
            runtimeAppOpAccessMessage = this.mCollectedRuntimePermissionMessage;
            this.mCollectedRuntimePermissionMessage = null;
        }
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda3(), this));
        return runtimeAppOpAccessMessage;
    }

    public final ArrayList collectUidOps(UidState uidState, int[] iArr) {
        int size;
        SparseIntArray nonDefaultUidModes = this.mAppOpsCheckingService.getNonDefaultUidModes(uidState.uid);
        ArrayList arrayList = null;
        if (nonDefaultUidModes == null || (size = nonDefaultUidModes.size()) == 0) {
            return null;
        }
        int i = 0;
        if (iArr == null) {
            arrayList = new ArrayList();
            while (i < size) {
                int keyAt = nonDefaultUidModes.keyAt(i);
                arrayList.add(new AppOpsManager.OpEntry(keyAt, nonDefaultUidModes.get(keyAt), Collections.emptyMap()));
                i++;
            }
        } else {
            while (i < iArr.length) {
                int i2 = iArr[i];
                if (nonDefaultUidModes.indexOfKey(i2) >= 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(new AppOpsManager.OpEntry(i2, nonDefaultUidModes.get(i2), Collections.emptyMap()));
                }
                i++;
            }
        }
        return arrayList;
    }

    public final void doForAllAttributedOpsInUidLocked(int i, Consumer consumer) {
        UidState uidState = (UidState) this.mUidStates.get(i);
        if (uidState == null) {
            return;
        }
        int size = uidState.pkgOps.size();
        for (int i2 = 0; i2 < size; i2++) {
            Ops ops = (Ops) uidState.pkgOps.valueAt(i2);
            int size2 = ops.size();
            for (int i3 = 0; i3 < size2; i3++) {
                Op op = (Op) ops.valueAt(i3);
                int size3 = op.mDeviceAttributedOps.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    ArrayMap arrayMap = (ArrayMap) op.mDeviceAttributedOps.valueAt(i4);
                    int size4 = arrayMap.size();
                    for (int i5 = 0; i5 < size4; i5++) {
                        consumer.accept((AttributedOp) arrayMap.valueAt(i5));
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:389:0x0768, code lost:
    
        if (r10 != r38.mAppOpsCheckingService.getPackageMode(r3, android.os.UserHandle.getUserId(r7.uid), r7.packageName)) goto L399;
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x05be, code lost:
    
        if (r3 == null) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x05c4, code lost:
    
        if (r3.indexOfKey(r11) < 0) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x05c7, code lost:
    
        r7 = 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:347:0x06d6 A[Catch: all -> 0x019e, TryCatch #2 {all -> 0x019e, blocks: (B:111:0x018f, B:114:0x0198, B:115:0x01a1, B:118:0x01b3, B:122:0x01bb, B:123:0x01c1, B:125:0x01c9, B:127:0x01eb, B:128:0x01ee, B:132:0x01fb, B:135:0x0205, B:139:0x0272, B:140:0x020e, B:141:0x0219, B:143:0x021f, B:145:0x0227, B:149:0x026d, B:151:0x0239, B:153:0x0242, B:154:0x025b, B:160:0x0277, B:165:0x0285, B:168:0x028f, B:172:0x02e2, B:174:0x029e, B:175:0x02a5, B:176:0x02c3, B:178:0x02c9, B:183:0x02e5, B:188:0x02f3, B:190:0x02fb, B:192:0x0305, B:196:0x033a, B:198:0x0313, B:199:0x031a, B:203:0x033d, B:207:0x0349, B:209:0x0351, B:213:0x03e5, B:214:0x0361, B:216:0x036c, B:221:0x0376, B:226:0x0381, B:227:0x0388, B:230:0x03b1, B:231:0x03bb, B:233:0x03ca, B:235:0x03cf, B:239:0x03d4, B:243:0x03eb, B:246:0x03f5, B:248:0x03ff, B:253:0x0493, B:254:0x040e, B:256:0x0417, B:260:0x0420, B:263:0x042d, B:264:0x0434, B:267:0x045f, B:268:0x0469, B:270:0x0478, B:272:0x047d, B:276:0x0482, B:280:0x049b, B:284:0x04a7, B:286:0x04af, B:291:0x0548, B:292:0x04bf, B:294:0x04c8, B:298:0x04d1, B:301:0x04e0, B:302:0x04e7, B:305:0x050f, B:306:0x051b, B:308:0x052a, B:310:0x0532, B:315:0x0537, B:319:0x0550, B:325:0x0560, B:333:0x0572, B:335:0x0576, B:337:0x057e, B:345:0x06c2, B:347:0x06d6, B:349:0x06dd, B:354:0x0709, B:359:0x06f1, B:367:0x0828, B:369:0x071b, B:371:0x0721, B:373:0x072c, B:376:0x0819, B:378:0x0740, B:380:0x0746, B:386:0x080f, B:388:0x0758, B:391:0x0778, B:392:0x078c, B:394:0x07ba, B:396:0x07ce, B:397:0x07e3, B:398:0x07d3, B:399:0x07ef, B:407:0x05c0, B:412:0x05cd, B:422:0x05ee, B:424:0x05f6, B:428:0x05fe, B:446:0x0627, B:448:0x062d, B:451:0x0637, B:458:0x0646, B:460:0x064e, B:464:0x066e, B:470:0x0688, B:474:0x0692, B:492:0x0844, B:493:0x0847, B:496:0x0857, B:498:0x0860, B:500:0x0868, B:501:0x0872, B:504:0x088c, B:513:0x087b), top: B:110:0x018f }] */
    /* JADX WARN: Removed duplicated region for block: B:368:0x071a  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x05e0  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x060b  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x06a2  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x05e3  */
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.FileDescriptor r39, java.io.PrintWriter r40, java.lang.String[] r41) {
        /*
            Method dump skipped, instructions count: 2237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void enforceManageAppOpsModes(int i, int i2, int i3) {
        if (i == Process.myPid()) {
            return;
        }
        int userId = UserHandle.getUserId(i2);
        synchronized (this) {
            try {
                SparseIntArray sparseIntArray = this.mProfileOwners;
                if (sparseIntArray == null || sparseIntArray.get(userId, -1) != i2 || i3 < 0 || userId != UserHandle.getUserId(i3)) {
                    this.mContext.enforcePermission("android.permission.MANAGE_APP_OPS_MODES", Binder.getCallingPid(), Binder.getCallingUid(), null);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List extractAsyncOps(String str) {
        List list;
        Objects.requireNonNull(str);
        int callingUid = Binder.getCallingUid();
        verifyAndGetBypass(callingUid, str, null, null, false);
        synchronized (this) {
            list = (List) this.mUnforwardedAsyncNotedOps.remove(new Pair(str, Integer.valueOf(callingUid)));
        }
        return list;
    }

    public final void finishOperation(IBinder iBinder, int i, int i2, String str, String str2) {
        this.mCheckOpsDelegateDispatcher.finishOperation(iBinder, i, i2, str, str2, 0);
    }

    public final void finishOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3) {
        this.mCheckOpsDelegateDispatcher.finishOperation(iBinder, i, i2, str, str2, i3);
    }

    public final void finishOperationUnchecked(IBinder iBinder, int i, int i2, String str, String str2, int i3) {
        try {
            PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, str2, null, false);
            Op op = null;
            String str3 = !verifyAndGetBypass.isAttributionTagValid ? null : str2;
            synchronized (this) {
                try {
                    Ops opsLocked = getOpsLocked(i2, str, str3, verifyAndGetBypass.isAttributionTagValid, verifyAndGetBypass.bypass, true);
                    if (opsLocked != null) {
                        op = getOpLocked(opsLocked, i, i2, true);
                    }
                    if (op == null) {
                        Slog.e("AppOps", "Operation not found: uid=" + i2 + " pkg=" + str + "(" + str3 + ") op=" + AppOpsManager.opToName(i));
                        return;
                    }
                    AttributedOp attributedOp = (AttributedOp) ((ArrayMap) op.mDeviceAttributedOps.getOrDefault(getPersistentId(i3), new ArrayMap())).get(str3);
                    if (attributedOp == null) {
                        Slog.e("AppOps", "Attribution not found: uid=" + i2 + " pkg=" + str + "(" + str3 + ") op=" + AppOpsManager.opToName(i));
                        return;
                    }
                    if (!attributedOp.isRunning() && !attributedOp.isPaused()) {
                        Slog.e("AppOps", "Operation not started: uid=" + i2 + " pkg=" + str + "(" + str3 + ") op=" + AppOpsManager.opToName(i));
                    }
                    attributedOp.finishOrPause(iBinder, false, false);
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (SecurityException e) {
            logVerifyAndGetBypassFailure(i2, e, "finishOperation");
        }
    }

    public final void finishProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z) {
        this.mCheckOpsDelegateDispatcher.finishProxyOperation(iBinder, i, attributionSource, z);
    }

    public final void finishProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z) {
        this.mCheckOpsDelegateDispatcher.finishProxyOperation(iBinder, i, new AttributionSource(attributionSourceState), z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0034, code lost:
    
        if (r15 == android.os.Binder.getCallingUid()) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getHistoricalOps(int r15, java.lang.String r16, java.lang.String r17, java.util.List r18, int r19, int r20, long r21, long r23, int r25, final android.os.RemoteCallback r26) {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.getHistoricalOps(int, java.lang.String, java.lang.String, java.util.List, int, int, long, long, int, android.os.RemoteCallback):void");
    }

    public final void getHistoricalOpsFromDiskRaw(int i, String str, String str2, List list, int i2, int i3, long j, long j2, int i4, RemoteCallback remoteCallback) {
        ensureHistoricalOpRequestIsValid(i, str, str2, list, i3, j, j2, i4);
        Objects.requireNonNull(remoteCallback, "callback cannot be null");
        this.mContext.enforcePermission("android.permission.MANAGE_APPOPS", Binder.getCallingPid(), Binder.getCallingUid(), "getHistoricalOps");
        String[] strArr = list != null ? (String[]) list.toArray(new String[list.size()]) : null;
        Set indicatorExemptedPackages = (i2 & 4) != 0 ? PermissionManager.getIndicatorExemptedPackages(this.mContext) : null;
        this.mHandler.post(PooledLambda.obtainRunnable(new AppOpsService$$ExternalSyntheticLambda0(0), this.mHistoricalRegistry, Integer.valueOf(i), str, str2, strArr, Integer.valueOf(i2), Integer.valueOf(i3), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i4), indicatorExemptedPackages != null ? (String[]) indicatorExemptedPackages.toArray(new String[indicatorExemptedPackages.size()]) : null, remoteCallback).recycleOnUse());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r6v0 ??, still in use, count: 1, list:
          (r6v0 ?? I:java.lang.Object) from 0x0019: INVOKE (r8v0 ?? I:android.util.SparseArray), (r9v0 ?? I:int), (r6v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArray.put(int, java.lang.Object):void A[MD:(int, E):void (c)]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    public final com.android.server.appop.AppOpsService.Op getOpLocked(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r6v0 ??, still in use, count: 1, list:
          (r6v0 ?? I:java.lang.Object) from 0x0019: INVOKE (r8v0 ?? I:android.util.SparseArray), (r9v0 ?? I:int), (r6v0 ?? I:java.lang.Object) VIRTUAL call: android.util.SparseArray.put(int, java.lang.Object):void A[MD:(int, E):void (c)]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r8v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */

    public final List getOpsForPackage(int i, String str, int[] iArr) {
        int callingUid;
        int callingPid = Binder.getCallingPid();
        if (callingPid != Process.myPid() && (i != (callingUid = Binder.getCallingUid()) || str == null || checkPackage(i, str) != 0)) {
            this.mContext.enforcePermission("android.permission.GET_APP_OPS_STATS", callingPid, callingUid, null);
        }
        String resolvePackageName = AppOpsManager.resolvePackageName(i, str);
        if (resolvePackageName == null) {
            return Collections.emptyList();
        }
        synchronized (this) {
            try {
                Ops opsLocked = getOpsLocked(i, resolvePackageName, null, false, null, false);
                if (opsLocked == null) {
                    return null;
                }
                ArrayList collectOps = collectOps(opsLocked, iArr, "default:0");
                if (collectOps != null && collectOps.size() != 0) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new AppOpsManager.PackageOps(opsLocked.packageName, opsLocked.uidState.uid, collectOps));
                    return arrayList;
                }
                return null;
            } finally {
            }
        }
    }

    public final Ops getOpsLocked(int i, String str, String str2, boolean z, AppOpsManager.RestrictionBypass restrictionBypass, boolean z2) {
        UidState uidStateLocked = getUidStateLocked(i, false);
        if (uidStateLocked == null) {
            return null;
        }
        Ops ops = (Ops) uidStateLocked.pkgOps.get(str);
        if (ops == null) {
            if (!z2) {
                return null;
            }
            ops = new Ops(str, uidStateLocked);
            uidStateLocked.pkgOps.put(str, ops);
        }
        if (z2) {
            if (restrictionBypass != null) {
                ops.bypass = restrictionBypass;
            }
            if (str2 != null) {
                ops.knownAttributionTags.add(str2);
                if (z) {
                    ops.validAttributionTags.add(str2);
                } else {
                    ops.validAttributionTags.remove(str2);
                }
            }
        }
        return ops;
    }

    public final List getPackageListAndResample() {
        ArrayList arrayList = new ArrayList();
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        Iterator it = packageManagerInternal.getPackageList(null).mPackageNames.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = packageManagerInternal.getPackageInfo(Process.myUid(), this.mContext.getUserId(), 4096L, (String) it.next());
            if (isSamplingTarget(packageInfo)) {
                arrayList.add(packageInfo.packageName);
            }
        }
        synchronized (this) {
            resamplePackageAndAppOpLocked(arrayList);
        }
        return arrayList;
    }

    public final PackageManagerInternal getPackageManagerInternal() {
        if (this.mPackageManagerInternal == null) {
            this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }
        PackageManagerInternal packageManagerInternal = this.mPackageManagerInternal;
        if (packageManagerInternal != null) {
            return packageManagerInternal;
        }
        throw new IllegalStateException("PackageManagerInternal not loaded");
    }

    public final List getPackagesForOps(int[] iArr) {
        return getPackagesForOpsForDevice(iArr, "default:0");
    }

    public final List getPackagesForOpsForDevice(int[] iArr, String str) {
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList = null;
        boolean z = this.mContext.checkPermission("android.permission.GET_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null) == 0;
        synchronized (this) {
            try {
                int size = this.mUidStates.size();
                for (int i = 0; i < size; i++) {
                    UidState uidState = (UidState) this.mUidStates.valueAt(i);
                    if (!uidState.pkgOps.isEmpty() && (z || callingUid == uidState.uid)) {
                        ArrayMap arrayMap = uidState.pkgOps;
                        int size2 = arrayMap.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            Ops ops = (Ops) arrayMap.valueAt(i2);
                            ArrayList collectOps = collectOps(ops, iArr, str);
                            if (collectOps != null) {
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                arrayList.add(new AppOpsManager.PackageOps(ops.packageName, ops.uidState.uid, collectOps));
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final String getPersistentId(int i) {
        VirtualDeviceManagerInternal virtualDeviceManagerInternal;
        if (i == 0 || (virtualDeviceManagerInternal = this.mVirtualDeviceManagerInternal) == null) {
            return "default:0";
        }
        String persistentIdForDevice = virtualDeviceManagerInternal.getPersistentIdForDevice(i);
        if (persistentIdForDevice == null) {
            persistentIdForDevice = (String) this.mKnownDeviceIds.get(i);
        }
        if (persistentIdForDevice != null) {
            return persistentIdForDevice;
        }
        throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Requested persistentId for invalid virtualDeviceId: "));
    }

    public final List getUidOps(int i, int[] iArr) {
        this.mContext.enforcePermission("android.permission.GET_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
        synchronized (this) {
            try {
                UidState uidStateLocked = getUidStateLocked(i, false);
                if (uidStateLocked == null) {
                    return null;
                }
                ArrayList collectUidOps = collectUidOps(uidStateLocked, iArr);
                if (collectUidOps == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(new AppOpsManager.PackageOps((String) null, uidStateLocked.uid, collectUidOps));
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final UidState getUidStateLocked(int i, boolean z) {
        UidState uidState = (UidState) this.mUidStates.get(i);
        if (uidState != null) {
            return uidState;
        }
        if (!z) {
            return null;
        }
        UidState uidState2 = new UidState(i);
        this.mUidStates.put(i, uidState2);
        return uidState2;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.appop.AppOpsService$$ExternalSyntheticLambda11] */
    public final AppOpsUidStateTracker getUidStateTracker() {
        if (this.mUidStateTracker == null) {
            ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
            final Handler handler = this.mHandler;
            final ?? r3 = new Executor() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda11
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    synchronized (AppOpsService.this) {
                        runnable.run();
                    }
                }
            };
            AppOpsUidStateTrackerImpl appOpsUidStateTrackerImpl = new AppOpsUidStateTrackerImpl(activityManagerInternal, new AppOpsUidStateTrackerImpl.DelayableExecutor() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    handler.post(new AppOpsUidStateTrackerImpl$1$$ExternalSyntheticLambda0((AppOpsService$$ExternalSyntheticLambda11) r3, runnable, 0));
                }

                @Override // com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor
                public final void executeDelayed(Runnable runnable, long j) {
                    handler.postDelayed(new AppOpsUidStateTrackerImpl$1$$ExternalSyntheticLambda0((AppOpsService$$ExternalSyntheticLambda11) r3, runnable, 1), j);
                }
            }, Clock.SYSTEM_CLOCK, this.mConstants, handler.getLooper().getThread());
            this.mUidStateTracker = appOpsUidStateTrackerImpl;
            HandlerExecutor handlerExecutor = new HandlerExecutor(this.mHandler);
            AppOpsService$$ExternalSyntheticLambda12 appOpsService$$ExternalSyntheticLambda12 = new AppOpsService$$ExternalSyntheticLambda12(this);
            if (appOpsUidStateTrackerImpl.mUidStateChangedCallbacks.containsKey(appOpsService$$ExternalSyntheticLambda12)) {
                throw new IllegalStateException("Callback is already registered.");
            }
            appOpsUidStateTrackerImpl.mUidStateChangedCallbacks.put(appOpsService$$ExternalSyntheticLambda12, handlerExecutor);
        }
        return this.mUidStateTracker;
    }

    /* JADX WARN: Finally extract failed */
    public void initializeUidStates() {
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        UserManagerInternal userManagerInternal = this.mUserManagerInternal;
        if (userManagerInternal == null) {
            throw new IllegalStateException("UserManagerInternal not loaded");
        }
        synchronized (this) {
            try {
                SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                for (int i : NON_PACKAGE_UIDS) {
                    if (!this.mUidStates.contains(i)) {
                        this.mUidStates.put(i, new UidState(i));
                    }
                    sparseBooleanArray.put(i, true);
                }
                int[] userIds = userManagerInternal.getUserIds();
                if (this.mPackageManagerLocal == null) {
                    this.mPackageManagerLocal = (PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class);
                }
                PackageManagerLocal packageManagerLocal = this.mPackageManagerLocal;
                if (packageManagerLocal == null) {
                    throw new IllegalStateException("PackageManagerLocal not loaded");
                }
                PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = packageManagerLocal.withUnfilteredSnapshot();
                try {
                    Map packageStates = withUnfilteredSnapshot.getPackageStates();
                    for (int i2 : userIds) {
                        initializeUserUidStatesLocked(i2, packageStates, sparseBooleanArray);
                    }
                    trimUidStatesLocked(sparseBooleanArray, packageStates);
                    withUnfilteredSnapshot.close();
                } catch (Throwable th) {
                    if (withUnfilteredSnapshot != null) {
                        try {
                            withUnfilteredSnapshot.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final void initializeUserUidStatesLocked(int i, Map map, SparseBooleanArray sparseBooleanArray) {
        int i2;
        for (Map.Entry entry : map.entrySet()) {
            PackageState packageState = (PackageState) entry.getValue();
            if (!packageState.isApex()) {
                int appId = packageState.getAppId();
                String str = (String) entry.getKey();
                int uid = UserHandle.getUid(i, appId);
                if (sparseBooleanArray != null) {
                    sparseBooleanArray.put(uid, true);
                }
                UidState uidStateLocked = getUidStateLocked(uid, true);
                Ops ops = (Ops) uidStateLocked.pkgOps.get(str);
                if (ops == null) {
                    ops = new Ops(str, uidStateLocked);
                    uidStateLocked.pkgOps.put(str, ops);
                }
                Ops ops2 = ops;
                SparseIntArray nonDefaultPackageModes = this.mAppOpsCheckingService.getNonDefaultPackageModes(i, str);
                int i3 = 0;
                while (i3 < nonDefaultPackageModes.size()) {
                    int keyAt = nonDefaultPackageModes.keyAt(i3);
                    if (ops2.indexOfKey(keyAt) < 0) {
                        i2 = i3;
                        ops2.put(keyAt, new Op(uidStateLocked, str, keyAt, uid));
                    } else {
                        i2 = i3;
                    }
                    i3 = i2 + 1;
                }
                if (UserHandle.getAppId(uid) >= 10000) {
                    int sdkSandboxUid = Process.toSdkSandboxUid(uid);
                    if (sparseBooleanArray != null) {
                        sparseBooleanArray.put(sdkSandboxUid, true);
                    }
                    getUidStateLocked(sdkSandboxUid, true);
                }
            }
        }
    }

    public final boolean isCallerAndAttributionTrusted(AttributionSource attributionSource) {
        return (attributionSource.getUid() != Binder.getCallingUid() && attributionSource.isTrusted(this.mContext)) || this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null) == 0;
    }

    public final boolean isIncomingPackageValid(int i, String str) {
        int callingUid = Binder.getCallingUid();
        if (str != null) {
            String resolvePackageName = AppOpsManager.resolvePackageName(callingUid, str);
            if (callingUid != 1000 && resolveUid(resolvePackageName) == -1) {
                if (!(getPackageManagerInternal().getPackageStateInternal(str) != null)) {
                    return false;
                }
                if (getPackageManagerInternal().filterAppAccess(callingUid, i, str, true)) {
                    Slog.w("AppOps", str + " not found from " + callingUid);
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean isOpRestrictedLocked(int i, int i2, String str, String str2, int i3, AppOpsManager.RestrictionBypass restrictionBypass, boolean z) {
        if (i3 != 0) {
            return false;
        }
        int size = this.mOpGlobalRestrictions.size();
        for (int i4 = 0; i4 < size; i4++) {
            ClientGlobalRestrictionState clientGlobalRestrictionState = (ClientGlobalRestrictionState) this.mOpGlobalRestrictions.valueAt(i4);
            SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) ((AppOpsRestrictionsImpl) AppOpsService.this.mAppOpsRestrictions).mGlobalRestrictions.get(clientGlobalRestrictionState.mToken);
            if (sparseBooleanArray == null ? false : sparseBooleanArray.get(i2)) {
                if (DEBUG_MID || DEBUG) {
                    NandswapManager$$ExternalSyntheticOutline0.m(i2, "mOpGlobalRestrictions ", "AppOps");
                }
                return true;
            }
        }
        if (i2 == 26 && this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist()) {
                    if (this.mSensorPrivacyManager.isCameraPrivacyEnabled(str)) {
                        return true;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        int userId = UserHandle.getUserId(i);
        int size2 = this.mOpUserRestrictions.size();
        for (int i5 = 0; i5 < size2; i5++) {
            ClientUserRestrictionState clientUserRestrictionState = (ClientUserRestrictionState) this.mOpUserRestrictions.valueAt(i5);
            if (clientUserRestrictionState.hasRestriction(str, i2, z, userId, str2)) {
                AppOpsManager.RestrictionBypass opAllowSystemBypassRestriction = AppOpsManager.opAllowSystemBypassRestriction(i2);
                if (opAllowSystemBypassRestriction != null) {
                    synchronized (this) {
                        try {
                            if (opAllowSystemBypassRestriction.isSystemUid && restrictionBypass != null && restrictionBypass.isSystemUid) {
                                return false;
                            }
                            if (opAllowSystemBypassRestriction.isPrivileged && restrictionBypass != null && restrictionBypass.isPrivileged) {
                                return false;
                            }
                            if (opAllowSystemBypassRestriction.isRecordAudioRestrictionExcept && restrictionBypass != null && restrictionBypass.isRecordAudioRestrictionExcept) {
                                return false;
                            }
                        } finally {
                        }
                    }
                }
                if (clientUserRestrictionState.callingRestriction > 0 && (DEBUG_MID || DEBUG)) {
                    VaultKeeperService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(userId, i2, "User ", ", OP ", " is restricted by "), clientUserRestrictionState.callingRestriction, "AppOps");
                }
                return true;
            }
        }
        return false;
    }

    public final boolean isOperationActive(int i, int i2, String str) {
        String resolvePackageName;
        if (Binder.getCallingUid() != i2 && this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") != 0) {
            return false;
        }
        verifyIncomingOp(i);
        if (!isIncomingPackageValid(UserHandle.getUserId(i2), str) || (resolvePackageName = AppOpsManager.resolvePackageName(i2, str)) == null) {
            return false;
        }
        synchronized (this) {
            try {
                Ops opsLocked = getOpsLocked(i2, resolvePackageName, null, false, null, false);
                if (opsLocked == null) {
                    return false;
                }
                Op op = (Op) opsLocked.get(i);
                if (op == null) {
                    return false;
                }
                return op.isRunning();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isProxying(int i, String str, String str2, int i2, String str3) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(str3);
        long callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List opsForPackage = getOpsForPackage(i2, str3, new int[]{i});
            boolean z = false;
            if (opsForPackage != null && !opsForPackage.isEmpty()) {
                List ops = ((AppOpsManager.PackageOps) opsForPackage.get(0)).getOps();
                if (ops.isEmpty()) {
                    return false;
                }
                AppOpsManager.OpEntry opEntry = (AppOpsManager.OpEntry) ops.get(0);
                if (!opEntry.isRunning()) {
                    return false;
                }
                AppOpsManager.OpEventProxyInfo lastProxyInfo = opEntry.getLastProxyInfo(24);
                if (lastProxyInfo != null && callingUid == lastProxyInfo.getUid() && str.equals(lastProxyInfo.getPackageName())) {
                    if (Objects.equals(str2, lastProxyInfo.getAttributionTag())) {
                        z = true;
                    }
                }
                return z;
            }
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isSamplingTarget(PackageInfo packageInfo) {
        String[] strArr;
        if (packageInfo == null || (strArr = packageInfo.requestedPermissions) == null) {
            return false;
        }
        for (String str : strArr) {
            if (this.mContext.getPackageManager().getPermissionInfo(str, 0).getProtection() == 1) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUidOpGrantedByRole(int i, int i2) {
        if (!AppOpsManager.opIsUidAppOpPermission(i2)) {
            return false;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String str = (String) ArrayUtils.firstOrNull(ArrayUtils.defeatNullable(packageManager.getPackagesForUid(i)));
            if (str == null) {
                return false;
            }
            return (packageManager.getPermissionFlags(AppOpsManager.opToPermission(i2), str, UserHandle.getUserHandleForUid(i)) & 32768) != 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isValidVirtualDeviceId(int i) {
        VirtualDeviceManagerInternal virtualDeviceManagerInternal;
        if (i == 0 || (virtualDeviceManagerInternal = this.mVirtualDeviceManagerInternal) == null) {
            return true;
        }
        if (!VirtualDeviceManagerService.this.mImpl.isValidVirtualDeviceId(i)) {
            return false;
        }
        this.mKnownDeviceIds.put(i, this.mVirtualDeviceManagerInternal.getPersistentIdForDevice(i));
        return true;
    }

    public final SyncNotedAppOp noteOperation(int i, int i2, String str, String str2, boolean z, String str3, boolean z2) {
        return this.mCheckOpsDelegateDispatcher.noteOperation(i, i2, str, str2, 0, z, str3, z2);
    }

    public final SyncNotedAppOp noteOperationForDevice(int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2) {
        return this.mCheckOpsDelegateDispatcher.noteOperation(i, i2, str, str2, i3, z, str3, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00de A[Catch: all -> 0x00a7, TryCatch #0 {all -> 0x00a7, blocks: (B:14:0x0069, B:16:0x008a, B:18:0x00a2, B:21:0x00da, B:23:0x00de, B:26:0x0112, B:27:0x016f, B:28:0x0175, B:32:0x00aa, B:33:0x0177, B:35:0x0190, B:36:0x01cf, B:38:0x01f8, B:40:0x0215, B:42:0x023c, B:43:0x0244, B:45:0x0219, B:46:0x0246, B:49:0x038c, B:51:0x0390, B:53:0x03d1, B:55:0x03ff, B:56:0x0411, B:57:0x0417, B:59:0x0394, B:62:0x03bb, B:63:0x03b5, B:64:0x0250, B:66:0x0264, B:68:0x0276, B:70:0x027a, B:72:0x02bd, B:76:0x02de, B:77:0x02e6, B:78:0x02eb, B:80:0x027e, B:83:0x02f3, B:84:0x02fd, B:86:0x0315, B:88:0x0319, B:90:0x035c, B:93:0x037c, B:94:0x0384, B:95:0x0389, B:97:0x031d), top: B:13:0x0069 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0112 A[Catch: all -> 0x00a7, TryCatch #0 {all -> 0x00a7, blocks: (B:14:0x0069, B:16:0x008a, B:18:0x00a2, B:21:0x00da, B:23:0x00de, B:26:0x0112, B:27:0x016f, B:28:0x0175, B:32:0x00aa, B:33:0x0177, B:35:0x0190, B:36:0x01cf, B:38:0x01f8, B:40:0x0215, B:42:0x023c, B:43:0x0244, B:45:0x0219, B:46:0x0246, B:49:0x038c, B:51:0x0390, B:53:0x03d1, B:55:0x03ff, B:56:0x0411, B:57:0x0417, B:59:0x0394, B:62:0x03bb, B:63:0x03b5, B:64:0x0250, B:66:0x0264, B:68:0x0276, B:70:0x027a, B:72:0x02bd, B:76:0x02de, B:77:0x02e6, B:78:0x02eb, B:80:0x027e, B:83:0x02f3, B:84:0x02fd, B:86:0x0315, B:88:0x0319, B:90:0x035c, B:93:0x037c, B:94:0x0384, B:95:0x0389, B:97:0x031d), top: B:13:0x0069 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x016d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.SyncNotedAppOp noteOperationUnchecked(int r32, int r33, java.lang.String r34, java.lang.String r35, int r36, int r37, java.lang.String r38, java.lang.String r39, int r40, int r41, boolean r42, java.lang.String r43, boolean r44) {
        /*
            Method dump skipped, instructions count: 1096
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.noteOperationUnchecked(int, int, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String, int, int, boolean, java.lang.String, boolean):android.app.SyncNotedAppOp");
    }

    public final SyncNotedAppOp noteProxyOperation(int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3) {
        return this.mCheckOpsDelegateDispatcher.noteProxyOperation(i, attributionSource, z, str, z2, z3);
    }

    public final SyncNotedAppOp noteProxyOperationWithState(int i, AttributionSourceState attributionSourceState, boolean z, String str, boolean z2, boolean z3) {
        return this.mCheckOpsDelegateDispatcher.noteProxyOperation(i, new AttributionSource(attributionSourceState), z, str, z2, z3);
    }

    public final void notifyOpChanged(ArraySet arraySet, int i, int i2, String str, String str2) {
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            notifyOpChanged((ModeCallback) arraySet.valueAt(i3), i, i2, str, str2);
        }
    }

    public final void notifyOpChanged(ModeCallback modeCallback, int i, int i2, String str, String str2) {
        Objects.requireNonNull(modeCallback);
        int i3 = modeCallback.mWatchedOpCode;
        for (int i4 : i3 == -2 ? (int[]) this.mSwitchedOps.get(i) : i3 == -1 ? new int[]{i} : new int[]{i3}) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!shouldIgnoreCallback(i4, modeCallback.mCallingPid, modeCallback.mCallingUid)) {
                    modeCallback.mCallback.opChanged(i4, i2, str, str2);
                }
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void notifyOpChangedForAllPkgsInUid(int i, int i2, String str, boolean z) {
        ArrayMap arrayMap;
        IAppOpsCallback iAppOpsCallback;
        String[] packagesForUid = getPackagesForUid(i2);
        synchronized (this) {
            try {
                ArraySet arraySet = (ArraySet) this.mOpModeWatchers.get(i);
                ArrayMap arrayMap2 = null;
                if (arraySet != null) {
                    int size = arraySet.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        ModeCallback modeCallback = (ModeCallback) arraySet.valueAt(i3);
                        modeCallback.getClass();
                        if (!z || (modeCallback.mFlags & 1) != 0) {
                            ArraySet arraySet2 = new ArraySet();
                            Collections.addAll(arraySet2, packagesForUid);
                            if (arrayMap2 == null) {
                                arrayMap2 = new ArrayMap();
                            }
                            arrayMap2.put(modeCallback, arraySet2);
                        }
                    }
                }
                arrayMap = arrayMap2;
                for (String str2 : packagesForUid) {
                    ArraySet arraySet3 = (ArraySet) this.mPackageModeWatchers.get(str2);
                    if (arraySet3 != null) {
                        if (arrayMap == null) {
                            arrayMap = new ArrayMap();
                        }
                        int size2 = arraySet3.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            ModeCallback modeCallback2 = (ModeCallback) arraySet3.valueAt(i4);
                            if (!z || (modeCallback2.mFlags & 1) != 0) {
                                ArraySet arraySet4 = (ArraySet) arrayMap.get(modeCallback2);
                                if (arraySet4 == null) {
                                    arraySet4 = new ArraySet();
                                    arrayMap.put(modeCallback2, arraySet4);
                                }
                                arraySet4.add(str2);
                            }
                        }
                    }
                }
                if (arrayMap != null && (iAppOpsCallback = this.mIgnoredCallback) != null) {
                    arrayMap.remove(this.mModeWatchers.get(iAppOpsCallback.asBinder()));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (arrayMap == null) {
            return;
        }
        for (int i5 = 0; i5 < arrayMap.size(); i5++) {
            ModeCallback modeCallback3 = (ModeCallback) arrayMap.keyAt(i5);
            ArraySet arraySet5 = (ArraySet) arrayMap.valueAt(i5);
            if (arraySet5 == null) {
                this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda7(0), this, modeCallback3, Integer.valueOf(i), Integer.valueOf(i2), (Object) null, str));
            } else {
                int size3 = arraySet5.size();
                for (int i6 = 0; i6 < size3; i6++) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda7(0), this, modeCallback3, Integer.valueOf(i), Integer.valueOf(i2), (String) arraySet5.valueAt(i6), str));
                }
            }
        }
    }

    public final void notifyWatchersOnDefaultDevice(int i, int i2) {
        synchronized (this) {
            try {
                ArraySet arraySet = (ArraySet) this.mOpModeWatchers.get(i);
                if (arraySet == null) {
                    return;
                }
                notifyOpChanged(new ArraySet(arraySet), i, i2, (String) null, "default:0");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void offsetHistory(long j) {
        DiscreteRegistry.DiscreteOps allDiscreteOps;
        offsetHistory_enforcePermission();
        this.mHistoricalRegistry.offsetHistory(j);
        DiscreteRegistry discreteRegistry = this.mHistoricalRegistry.mDiscreteRegistry;
        synchronized (discreteRegistry.mOnDiskLock) {
            synchronized (discreteRegistry.mInMemoryLock) {
                allDiscreteOps = discreteRegistry.getAllDiscreteOps();
                discreteRegistry.clearHistory();
            }
            DiscreteRegistry.DiscreteOps.m242$$Nest$moffsetHistory(allDiscreteOps, j);
            discreteRegistry.persistDiscreteOpsLocked(allDiscreteOps);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new Shell(this, this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void onUidProcessDeathLocked(int i) {
        if (this.mUidStates.contains(i) && Flags.finishRunningOpsForKilledPackages()) {
            final SparseLongArray sparseLongArray = new SparseLongArray();
            final int i2 = 0;
            doForAllAttributedOpsInUidLocked(i, new Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda17
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i3 = i2;
                    final SparseLongArray sparseLongArray2 = sparseLongArray;
                    final AttributedOp attributedOp = (AttributedOp) obj;
                    switch (i3) {
                        case 0:
                            final int i4 = 0;
                            attributedOp.doForAllInProgressStartOpEvents(new Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda19
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    switch (i4) {
                                        case 0:
                                            SparseLongArray sparseLongArray3 = sparseLongArray2;
                                            AttributedOp attributedOp2 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (AttributedOp.InProgressStartOpEvent) obj2;
                                            if (inProgressStartOpEvent != null) {
                                                int i5 = inProgressStartOpEvent.mAttributionChainId;
                                                if (i5 != -1) {
                                                    long j = sparseLongArray3.get(i5, Long.MAX_VALUE);
                                                    long j2 = inProgressStartOpEvent.mStartTime;
                                                    if (j2 < j) {
                                                        sparseLongArray3.put(i5, j2);
                                                    }
                                                }
                                                attributedOp2.finishOrPause(inProgressStartOpEvent.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                        default:
                                            SparseLongArray sparseLongArray4 = sparseLongArray2;
                                            AttributedOp attributedOp3 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent2 = (AttributedOp.InProgressStartOpEvent) obj2;
                                            int i6 = inProgressStartOpEvent2.mAttributionChainId;
                                            long j3 = sparseLongArray4.get(i6, Long.MAX_VALUE);
                                            if (i6 != -1 && inProgressStartOpEvent2.mStartTime >= j3) {
                                                attributedOp3.finishOrPause(inProgressStartOpEvent2.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                        default:
                            final int i5 = 1;
                            attributedOp.doForAllInProgressStartOpEvents(new Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda19
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    switch (i5) {
                                        case 0:
                                            SparseLongArray sparseLongArray3 = sparseLongArray2;
                                            AttributedOp attributedOp2 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (AttributedOp.InProgressStartOpEvent) obj2;
                                            if (inProgressStartOpEvent != null) {
                                                int i52 = inProgressStartOpEvent.mAttributionChainId;
                                                if (i52 != -1) {
                                                    long j = sparseLongArray3.get(i52, Long.MAX_VALUE);
                                                    long j2 = inProgressStartOpEvent.mStartTime;
                                                    if (j2 < j) {
                                                        sparseLongArray3.put(i52, j2);
                                                    }
                                                }
                                                attributedOp2.finishOrPause(inProgressStartOpEvent.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                        default:
                                            SparseLongArray sparseLongArray4 = sparseLongArray2;
                                            AttributedOp attributedOp3 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent2 = (AttributedOp.InProgressStartOpEvent) obj2;
                                            int i6 = inProgressStartOpEvent2.mAttributionChainId;
                                            long j3 = sparseLongArray4.get(i6, Long.MAX_VALUE);
                                            if (i6 != -1 && inProgressStartOpEvent2.mStartTime >= j3) {
                                                attributedOp3.finishOrPause(inProgressStartOpEvent2.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                    }
                }
            });
            final int i3 = 1;
            Consumer consumer = new Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda17
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i32 = i3;
                    final SparseLongArray sparseLongArray2 = sparseLongArray;
                    final AttributedOp attributedOp = (AttributedOp) obj;
                    switch (i32) {
                        case 0:
                            final int i4 = 0;
                            attributedOp.doForAllInProgressStartOpEvents(new Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda19
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    switch (i4) {
                                        case 0:
                                            SparseLongArray sparseLongArray3 = sparseLongArray2;
                                            AttributedOp attributedOp2 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (AttributedOp.InProgressStartOpEvent) obj2;
                                            if (inProgressStartOpEvent != null) {
                                                int i52 = inProgressStartOpEvent.mAttributionChainId;
                                                if (i52 != -1) {
                                                    long j = sparseLongArray3.get(i52, Long.MAX_VALUE);
                                                    long j2 = inProgressStartOpEvent.mStartTime;
                                                    if (j2 < j) {
                                                        sparseLongArray3.put(i52, j2);
                                                    }
                                                }
                                                attributedOp2.finishOrPause(inProgressStartOpEvent.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                        default:
                                            SparseLongArray sparseLongArray4 = sparseLongArray2;
                                            AttributedOp attributedOp3 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent2 = (AttributedOp.InProgressStartOpEvent) obj2;
                                            int i6 = inProgressStartOpEvent2.mAttributionChainId;
                                            long j3 = sparseLongArray4.get(i6, Long.MAX_VALUE);
                                            if (i6 != -1 && inProgressStartOpEvent2.mStartTime >= j3) {
                                                attributedOp3.finishOrPause(inProgressStartOpEvent2.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                        default:
                            final int i5 = 1;
                            attributedOp.doForAllInProgressStartOpEvents(new Consumer() { // from class: com.android.server.appop.AppOpsService$$ExternalSyntheticLambda19
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    switch (i5) {
                                        case 0:
                                            SparseLongArray sparseLongArray3 = sparseLongArray2;
                                            AttributedOp attributedOp2 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (AttributedOp.InProgressStartOpEvent) obj2;
                                            if (inProgressStartOpEvent != null) {
                                                int i52 = inProgressStartOpEvent.mAttributionChainId;
                                                if (i52 != -1) {
                                                    long j = sparseLongArray3.get(i52, Long.MAX_VALUE);
                                                    long j2 = inProgressStartOpEvent.mStartTime;
                                                    if (j2 < j) {
                                                        sparseLongArray3.put(i52, j2);
                                                    }
                                                }
                                                attributedOp2.finishOrPause(inProgressStartOpEvent.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                        default:
                                            SparseLongArray sparseLongArray4 = sparseLongArray2;
                                            AttributedOp attributedOp3 = attributedOp;
                                            AttributedOp.InProgressStartOpEvent inProgressStartOpEvent2 = (AttributedOp.InProgressStartOpEvent) obj2;
                                            int i6 = inProgressStartOpEvent2.mAttributionChainId;
                                            long j3 = sparseLongArray4.get(i6, Long.MAX_VALUE);
                                            if (i6 != -1 && inProgressStartOpEvent2.mStartTime >= j3) {
                                                attributedOp3.finishOrPause(inProgressStartOpEvent2.mClientId, false, false);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                    }
                }
            };
            int size = this.mUidStates.size();
            for (int i4 = 0; i4 < size; i4++) {
                doForAllAttributedOpsInUidLocked(this.mUidStates.keyAt(i4), consumer);
            }
        }
    }

    public void packageRemoved(int i, String str) {
        synchronized (this) {
            packageRemovedLocked(i, str);
        }
    }

    public final void packageRemovedLocked(int i, String str) {
        this.mHandler.post(PooledLambda.obtainRunnable(new AppOpsService$$ExternalSyntheticLambda1(1), this.mHistoricalRegistry, Integer.valueOf(i), str));
        UidState uidState = (UidState) this.mUidStates.get(i);
        if (uidState == null) {
            return;
        }
        Ops ops = (Ops) uidState.pkgOps.remove(str);
        this.mAppOpsCheckingService.removePackage(UserHandle.getUserId(i), str);
        if (ops != null) {
            scheduleFastWriteLocked();
            int size = ops.size();
            for (int i2 = 0; i2 < size; i2++) {
                Op op = (Op) ops.valueAt(i2);
                for (int i3 = 0; i3 < op.mDeviceAttributedOps.size(); i3++) {
                    ArrayMap arrayMap = (ArrayMap) op.mDeviceAttributedOps.valueAt(i3);
                    for (int i4 = 0; i4 < arrayMap.size(); i4++) {
                        AttributedOp attributedOp = (AttributedOp) arrayMap.valueAt(i4);
                        while (attributedOp.isRunning()) {
                            attributedOp.finishOrPause((IBinder) attributedOp.mInProgressEvents.keyAt(0), false, false);
                        }
                        while (attributedOp.isPaused()) {
                            attributedOp.finishOrPause((IBinder) attributedOp.mPausedInProgressEvents.keyAt(0), false, false);
                        }
                    }
                }
            }
        }
    }

    public final int permissionToOpCode(String str) {
        if (str == null) {
            return -1;
        }
        return AppOpsManager.permissionToOpCode(str);
    }

    public void prepareInternalCallbacks() {
        if (this.mUserManagerInternal == null) {
            this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        UserManagerInternal userManagerInternal = this.mUserManagerInternal;
        if (userManagerInternal == null) {
            throw new IllegalStateException("UserManagerInternal not loaded");
        }
        userManagerInternal.addUserLifecycleListener(new UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.appop.AppOpsService.7
            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public final void onUserCreated(UserInfo userInfo, Object obj) {
                AppOpsService appOpsService = AppOpsService.this;
                int i = userInfo.id;
                synchronized (appOpsService) {
                    if (appOpsService.mPackageManagerLocal == null) {
                        appOpsService.mPackageManagerLocal = (PackageManagerLocal) LocalManagerRegistry.getManager(PackageManagerLocal.class);
                    }
                    PackageManagerLocal packageManagerLocal = appOpsService.mPackageManagerLocal;
                    if (packageManagerLocal == null) {
                        throw new IllegalStateException("PackageManagerLocal not loaded");
                    }
                    PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = packageManagerLocal.withUnfilteredSnapshot();
                    try {
                        appOpsService.initializeUserUidStatesLocked(i, withUnfilteredSnapshot.getPackageStates(), null);
                        withUnfilteredSnapshot.close();
                    } finally {
                    }
                }
            }
        });
    }

    public final void pruneOpLocked(Op op, int i, String str) {
        Ops opsLocked;
        UidState uidState;
        ArrayMap arrayMap;
        op.removeAttributionsWithNoTime();
        if (!op.mDeviceAttributedOps.isEmpty() || (opsLocked = getOpsLocked(i, str, null, false, null, false)) == null) {
            return;
        }
        int i2 = op.op;
        opsLocked.remove(i2);
        this.mAppOpsCheckingService.setPackageMode(i2, AppOpsManager.opToDefaultMode(i2), UserHandle.getUserId(op.uid), str);
        if (opsLocked.size() > 0 || (arrayMap = (uidState = opsLocked.uidState).pkgOps) == null) {
            return;
        }
        arrayMap.remove(opsLocked.packageName);
        this.mAppOpsCheckingService.removePackage(UserHandle.getUserId(uidState.uid), opsLocked.packageName);
    }

    public final void readPackage(TypedXmlPullParser typedXmlPullParser) {
        String str;
        String str2;
        int i;
        UidState uidState;
        String str3;
        String str4;
        String str5;
        String str6 = null;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "n");
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            int i2 = 1;
            if (next == 1) {
                return;
            }
            int i3 = 3;
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3) {
                int i4 = 4;
                if (next != 4) {
                    String str7 = "Unknown element under <pkg>: ";
                    if (typedXmlPullParser.getName().equals("uid")) {
                        UidState uidStateLocked = getUidStateLocked(typedXmlPullParser.getAttributeInt(str6, "n"), true);
                        int depth2 = typedXmlPullParser.getDepth();
                        while (true) {
                            int next2 = typedXmlPullParser.next();
                            if (next2 == i2 || (next2 == i3 && typedXmlPullParser.getDepth() <= depth2)) {
                                break;
                            }
                            if (next2 != i3 && next2 != i4) {
                                if (typedXmlPullParser.getName().equals("op")) {
                                    i = depth2;
                                    UidState uidState2 = uidStateLocked;
                                    Op op = new Op(uidStateLocked, attributeValue, typedXmlPullParser.getAttributeInt(str6, "n"), uidStateLocked.uid);
                                    int depth3 = typedXmlPullParser.getDepth();
                                    while (true) {
                                        int next3 = typedXmlPullParser.next();
                                        if (next3 == i2) {
                                            str4 = str7;
                                            str2 = null;
                                            uidState = uidState2;
                                            break;
                                        }
                                        if (next3 == i3 && typedXmlPullParser.getDepth() <= depth3) {
                                            str4 = str7;
                                            uidState = uidState2;
                                            str2 = null;
                                            break;
                                        }
                                        if (next3 != i3 && next3 != i4) {
                                            if (typedXmlPullParser.getName().equals(KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME)) {
                                                AttributedOp orCreateAttribution = op.getOrCreateAttribution(op, XmlUtils.readStringAttribute(typedXmlPullParser, "id"), "default:0");
                                                long attributeLong = typedXmlPullParser.getAttributeLong((String) null, "n");
                                                int extractUidStateFromKey = AppOpsManager.extractUidStateFromKey(attributeLong);
                                                int extractFlagsFromKey = AppOpsManager.extractFlagsFromKey(attributeLong);
                                                long attributeLong2 = typedXmlPullParser.getAttributeLong((String) null, KnoxAnalyticsDataConverter.TIMESTAMP, 0L);
                                                str5 = str7;
                                                long attributeLong3 = typedXmlPullParser.getAttributeLong((String) null, "r", 0L);
                                                long attributeLong4 = typedXmlPullParser.getAttributeLong((String) null, "d", -1L);
                                                String readStringAttribute = XmlUtils.readStringAttribute(typedXmlPullParser, "pp");
                                                int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "pu", -1);
                                                String readStringAttribute2 = XmlUtils.readStringAttribute(typedXmlPullParser, "pc");
                                                if (attributeLong2 > 0) {
                                                    orCreateAttribution.accessed(attributeLong2, attributeLong4, attributeInt, readStringAttribute, readStringAttribute2, "default:0", extractUidStateFromKey, extractFlagsFromKey);
                                                }
                                                if (attributeLong3 > 0) {
                                                    orCreateAttribution.rejected(extractUidStateFromKey, extractFlagsFromKey, attributeLong3);
                                                }
                                            } else {
                                                str5 = str7;
                                                Slog.w("AppOps", "Unknown element under <op>: " + typedXmlPullParser.getName());
                                                XmlUtils.skipCurrentTag(typedXmlPullParser);
                                            }
                                            str7 = str5;
                                            i2 = 1;
                                            i3 = 3;
                                            i4 = 4;
                                        }
                                    }
                                    Ops ops = (Ops) uidState.pkgOps.get(attributeValue);
                                    if (ops == null) {
                                        ops = new Ops(attributeValue, uidState);
                                        uidState.pkgOps.put(attributeValue, ops);
                                    }
                                    ops.put(op.op, op);
                                    str3 = str4;
                                } else {
                                    str2 = str6;
                                    i = depth2;
                                    uidState = uidStateLocked;
                                    str3 = str7;
                                    Slog.w("AppOps", str3 + typedXmlPullParser.getName());
                                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                                }
                                uidStateLocked = uidState;
                                str7 = str3;
                                str6 = str2;
                                depth2 = i;
                                i2 = 1;
                                i3 = 3;
                                i4 = 4;
                            }
                        }
                        str = str6;
                    } else {
                        str = str6;
                        Slog.w("AppOps", "Unknown element under <pkg>: " + typedXmlPullParser.getName());
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                    str6 = str;
                }
            }
        }
    }

    public final void readRecentAccesses() {
        if (!this.mRecentAccessesFile.exists()) {
            readRecentAccesses(this.mStorageFile);
        } else if (!Flags.deviceAwareAppOpNewSchemaEnabled()) {
            readRecentAccesses(this.mRecentAccessesFile);
        } else {
            synchronized (this) {
                this.mRecentAccessPersistence.readRecentAccesses(this.mUidStates);
            }
        }
    }

    public final void readRecentAccesses(AtomicFile atomicFile) {
        TypedXmlPullParser resolvePullParser;
        int next;
        synchronized (atomicFile) {
            synchronized (this) {
                try {
                    FileInputStream openRead = atomicFile.openRead();
                    try {
                        this.mUidStates.clear();
                        this.mAppOpsCheckingService.clearAllModes();
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                resolvePullParser = Xml.resolvePullParser(openRead);
                                                do {
                                                    next = resolvePullParser.next();
                                                    if (next == 2) {
                                                        break;
                                                    }
                                                } while (next != 1);
                                            } catch (IOException e) {
                                                Slog.w("AppOps", "Failed parsing " + e);
                                                this.mUidStates.clear();
                                                this.mAppOpsCheckingService.clearAllModes();
                                            }
                                        } catch (IllegalStateException e2) {
                                            Slog.w("AppOps", "Failed parsing " + e2);
                                            this.mUidStates.clear();
                                            this.mAppOpsCheckingService.clearAllModes();
                                        }
                                    } catch (NullPointerException e3) {
                                        Slog.w("AppOps", "Failed parsing " + e3);
                                        this.mUidStates.clear();
                                        this.mAppOpsCheckingService.clearAllModes();
                                    }
                                } catch (XmlPullParserException e4) {
                                    Slog.w("AppOps", "Failed parsing " + e4);
                                    this.mUidStates.clear();
                                    this.mAppOpsCheckingService.clearAllModes();
                                }
                            } catch (NumberFormatException e5) {
                                Slog.w("AppOps", "Failed parsing " + e5);
                                this.mUidStates.clear();
                                this.mAppOpsCheckingService.clearAllModes();
                            }
                        } catch (IndexOutOfBoundsException e6) {
                            Slog.w("AppOps", "Failed parsing " + e6);
                            this.mUidStates.clear();
                            this.mAppOpsCheckingService.clearAllModes();
                        }
                        if (next != 2) {
                            throw new IllegalStateException("no start tag found");
                        }
                        int depth = resolvePullParser.getDepth();
                        while (true) {
                            int next2 = resolvePullParser.next();
                            if (next2 != 1) {
                                if (next2 == 3) {
                                    if (resolvePullParser.getDepth() > depth) {
                                    }
                                }
                                if (next2 != 3 && next2 != 4) {
                                    String name = resolvePullParser.getName();
                                    if (name.equals("pkg")) {
                                        readPackage(resolvePullParser);
                                    } else if (name.equals("uid")) {
                                        XmlUtils.skipCurrentTag(resolvePullParser);
                                    } else {
                                        Slog.w("AppOps", "Unknown element under <app-ops>: " + resolvePullParser.getName());
                                        XmlUtils.skipCurrentTag(resolvePullParser);
                                    }
                                }
                            }
                            try {
                                openRead.close();
                            } catch (IOException unused) {
                            }
                        }
                    } catch (Throwable th) {
                        this.mUidStates.clear();
                        this.mAppOpsCheckingService.clearAllModes();
                        try {
                            openRead.close();
                        } catch (IOException unused2) {
                        }
                        throw th;
                    }
                } catch (FileNotFoundException unused3) {
                    Slog.i("AppOps", "No existing app ops " + atomicFile.getBaseFile() + "; starting empty");
                }
            }
        }
    }

    public void readState() {
        this.mAppOpsCheckingService.readState();
    }

    public final void rebootHistory(long j) {
        rebootHistory_enforcePermission();
        Preconditions.checkArgument(j >= 0);
        HistoricalRegistry historicalRegistry = this.mHistoricalRegistry;
        synchronized (historicalRegistry.mInMemoryLock) {
            try {
                if (historicalRegistry.mMode != 0) {
                    historicalRegistry.persistPendingHistory();
                }
            } finally {
            }
        }
        if (j > 0) {
            SystemClock.sleep(j);
        }
        HistoricalRegistry historicalRegistry2 = this.mHistoricalRegistry;
        HistoricalRegistry historicalRegistry3 = new HistoricalRegistry(historicalRegistry2.mInMemoryLock);
        historicalRegistry3.mMode = historicalRegistry2.mMode;
        historicalRegistry3.mBaseSnapshotInterval = historicalRegistry2.mBaseSnapshotInterval;
        historicalRegistry3.mIntervalCompressionMultiplier = historicalRegistry2.mIntervalCompressionMultiplier;
        historicalRegistry3.mDiscreteRegistry = historicalRegistry2.mDiscreteRegistry;
        this.mHistoricalRegistry = historicalRegistry3;
        this.mHistoricalRegistry.systemReady(this.mContext.getContentResolver());
        this.mHistoricalRegistry.persistPendingHistory();
    }

    public final void refreshAttributionsLocked(AndroidPackage androidPackage, int i) {
        Ops ops;
        ArraySet arraySet;
        Ops ops2;
        ArrayMap arrayMap;
        ArraySet arraySet2;
        String packageName = androidPackage.getPackageName();
        ArrayMap arrayMap2 = new ArrayMap();
        ArraySet arraySet3 = new ArraySet();
        arraySet3.add(null);
        if (androidPackage.getAttributions() != null) {
            int size = androidPackage.getAttributions().size();
            for (int i2 = 0; i2 < size; i2++) {
                ParsedAttribution parsedAttribution = (ParsedAttribution) androidPackage.getAttributions().get(i2);
                arraySet3.add(parsedAttribution.getTag());
                int size2 = parsedAttribution.getInheritFrom().size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayMap2.put((String) parsedAttribution.getInheritFrom().get(i3), parsedAttribution.getTag());
                }
            }
        }
        UidState uidState = (UidState) this.mUidStates.get(i);
        if (uidState == null || (ops = (Ops) uidState.pkgOps.get(packageName)) == null) {
            return;
        }
        ops.bypass = null;
        ops.knownAttributionTags.clear();
        int size3 = ops.size();
        int i4 = 0;
        while (i4 < size3) {
            Op op = (Op) ops.valueAt(i4);
            int size4 = op.mDeviceAttributedOps.size() - 1;
            while (size4 >= 0) {
                ArrayMap arrayMap3 = (ArrayMap) op.mDeviceAttributedOps.valueAt(size4);
                int size5 = arrayMap3.size() - 1;
                while (size5 >= 0) {
                    String str = (String) arrayMap3.keyAt(size5);
                    if (arraySet3.contains(str)) {
                        ops2 = ops;
                        arrayMap = arrayMap2;
                        arraySet2 = arraySet3;
                    } else {
                        AttributedOp orCreateAttribution = op.getOrCreateAttribution(op, (String) arrayMap2.get(str), (String) op.mDeviceAttributedOps.keyAt(size4));
                        AttributedOp attributedOp = (AttributedOp) arrayMap3.get(str);
                        if (attributedOp.isRunning() || attributedOp.isPaused()) {
                            ArrayMap arrayMap4 = attributedOp.isRunning() ? attributedOp.mInProgressEvents : attributedOp.mPausedInProgressEvents;
                            Slog.w("AppOps", "Ignoring " + arrayMap4.size() + " app-ops, running: " + attributedOp.isRunning());
                            int size6 = arrayMap4.size();
                            int i5 = 0;
                            while (i5 < size6) {
                                Ops ops3 = ops;
                                AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (AttributedOp.InProgressStartOpEvent) arrayMap4.valueAt(i5);
                                inProgressStartOpEvent.getClass();
                                ArrayMap arrayMap5 = arrayMap2;
                                try {
                                    arraySet = arraySet3;
                                    try {
                                        inProgressStartOpEvent.mClientId.unlinkToDeath(inProgressStartOpEvent, 0);
                                    } catch (NoSuchElementException unused) {
                                    }
                                } catch (NoSuchElementException unused2) {
                                    arraySet = arraySet3;
                                }
                                orCreateAttribution.mAppOpsService.mInProgressStartOpEventPool.release(inProgressStartOpEvent);
                                i5++;
                                ops = ops3;
                                arrayMap2 = arrayMap5;
                                arraySet3 = arraySet;
                            }
                        }
                        ops2 = ops;
                        arrayMap = arrayMap2;
                        arraySet2 = arraySet3;
                        orCreateAttribution.mAccessEvents = AttributedOp.add(orCreateAttribution.mAccessEvents, attributedOp.mAccessEvents);
                        orCreateAttribution.mRejectEvents = AttributedOp.add(orCreateAttribution.mRejectEvents, attributedOp.mRejectEvents);
                        arrayMap3.remove(str);
                        scheduleFastWriteLocked();
                    }
                    size5--;
                    ops = ops2;
                    arrayMap2 = arrayMap;
                    arraySet3 = arraySet2;
                }
                size4--;
                arraySet3 = arraySet3;
            }
            i4++;
            arraySet3 = arraySet3;
        }
    }

    public final void reloadNonHistoricalState() {
        this.mContext.enforcePermission("android.permission.MANAGE_APPOPS", Binder.getCallingPid(), Binder.getCallingUid(), "reloadNonHistoricalState");
        this.mAppOpsCheckingService.writeState();
        this.mAppOpsCheckingService.readState();
    }

    public final void removeUser(int i) {
        checkSystemUid("removeUser");
        synchronized (this) {
            try {
                for (int size = this.mOpUserRestrictions.size() - 1; size >= 0; size--) {
                    ((ClientUserRestrictionState) this.mOpUserRestrictions.valueAt(size)).removeUser(i);
                }
                for (int size2 = this.mUidStates.size() - 1; size2 >= 0; size2--) {
                    if (UserHandle.getUserId(this.mUidStates.keyAt(size2)) == i) {
                        ((UidState) this.mUidStates.valueAt(size2)).clear();
                        this.mUidStates.removeAt(size2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final MessageSamplingConfig reportRuntimeAppOpAccessMessageAndGetConfig(String str, SyncNotedAppOp syncNotedAppOp, String str2) {
        int callingUid = Binder.getCallingUid();
        if (DEBUG_MID || DEBUG) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(Binder.getCallingPid(), callingUid, "reportRuntimeAppOpAccessMessageAndGetConfig pid: ", " uid: ", " packageName: ");
            m.append(str);
            m.append(" message: ");
            m.append(str2);
            Slog.w("AppOps", m.toString());
        }
        Objects.requireNonNull(str);
        synchronized (this) {
            try {
                switchPackageIfBootTimeOrRarelyUsedLocked(str);
                if (!str.equals(this.mSampledPackage)) {
                    return new MessageSamplingConfig(-1, 0, Instant.now().plus(1L, (TemporalUnit) ChronoUnit.HOURS).toEpochMilli());
                }
                Objects.requireNonNull(syncNotedAppOp);
                Objects.requireNonNull(str2);
                reportRuntimeAppOpAccessMessageInternalLocked(callingUid, AppOpsManager.strOpToOp(syncNotedAppOp.getOp()), str, syncNotedAppOp.getAttributionTag(), str2);
                return new MessageSamplingConfig(this.mSampledAppOpCode, this.mAcceptableLeftDistance, Instant.now().plus(1L, (TemporalUnit) ChronoUnit.HOURS).toEpochMilli());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportRuntimeAppOpAccessMessageInternalLocked(int i, int i2, String str, String str2, String str3) {
        int leftCircularDistance = AppOpsManager.leftCircularDistance(i2, this.mSampledAppOpCode, 149);
        int i3 = this.mAcceptableLeftDistance;
        if (i3 >= leftCircularDistance || this.mSamplingStrategy == 4) {
            if (i3 > leftCircularDistance && this.mSamplingStrategy != 4) {
                this.mAcceptableLeftDistance = leftCircularDistance;
                this.mMessagesCollectedCount = FullScreenMagnificationGestureHandler.MAX_SCALE;
            }
            this.mMessagesCollectedCount += 1.0f;
            if (ThreadLocalRandom.current().nextFloat() <= 1.0f / this.mMessagesCollectedCount) {
                this.mCollectedRuntimePermissionMessage = new RuntimeAppOpAccessMessage(i, i2, str, str2, str3, this.mSamplingStrategy);
            }
        }
    }

    public final void requestPermissionAccessInformation() {
        Slog.w("AppOps", "requestPermissionAccessInformation");
        try {
            this.mPermissionAccessInformationController.flush();
        } catch (Exception e) {
            Slog.w("AppOps", e.getMessage(), e);
        }
    }

    public final void resampleAppOpForPackageLocked(String str, boolean z) {
        this.mMessagesCollectedCount = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mSampledAppOpCode = z ? ThreadLocalRandom.current().nextInt(149) : -1;
        this.mAcceptableLeftDistance = 148;
        this.mSampledPackage = str;
    }

    public final void resamplePackageAndAppOpLocked(List list) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return;
        }
        if (ThreadLocalRandom.current().nextFloat() < 0.5f) {
            this.mSamplingStrategy = 1;
            resampleAppOpForPackageLocked((String) arrayList.get(ThreadLocalRandom.current().nextInt(arrayList.size())), true);
        } else {
            this.mSamplingStrategy = 4;
            resampleAppOpForPackageLocked((String) arrayList.get(ThreadLocalRandom.current().nextInt(arrayList.size())), false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0032 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resetAllModes(int r25, java.lang.String r26) {
        /*
            Method dump skipped, instructions count: 745
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.resetAllModes(int, java.lang.String):void");
    }

    public final void resetHistoryParameters() {
        resetHistoryParameters_enforcePermission();
        HistoricalRegistry historicalRegistry = this.mHistoricalRegistry;
        if (!historicalRegistry.isPersistenceInitializedMLocked()) {
            Slog.d(HistoricalRegistry.LOG_TAG, "Interaction before persistence initialized");
        } else {
            historicalRegistry.setHistoryParameters(1, HistoricalRegistry.DEFAULT_SNAPSHOT_INTERVAL_MILLIS, 10L);
            historicalRegistry.mDiscreteRegistry.mDebugMode = false;
        }
    }

    public final void resetPackageOpsNoHistory(String str) {
        resetPackageOpsNoHistory_enforcePermission();
        synchronized (this) {
            try {
                int packageUid = this.mPackageManagerInternal.getPackageUid(str, 0L, UserHandle.getCallingUserId());
                if (packageUid == -1) {
                    return;
                }
                UidState uidState = (UidState) this.mUidStates.get(packageUid);
                if (uidState == null) {
                    return;
                }
                Ops ops = (Ops) uidState.pkgOps.remove(str);
                this.mAppOpsCheckingService.removePackage(UserHandle.getUserId(packageUid), str);
                if (ops != null) {
                    scheduleFastWriteLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void scheduleFastWriteLocked() {
        if (this.mFastWriteScheduled) {
            return;
        }
        this.mWriteScheduled = true;
        this.mFastWriteScheduled = true;
        this.mHandler.removeCallbacks(this.mWriteRunner);
        this.mHandler.postDelayed(this.mWriteRunner, 10000L);
    }

    public final void scheduleOpActiveChangedIfNeededLocked(int i, int i2, String str, String str2, int i3, boolean z, int i4, int i5) {
        int i6;
        int size = this.mActiveWatchers.size();
        ArraySet arraySet = null;
        for (int i7 = 0; i7 < size; i7++) {
            ActiveCallback activeCallback = (ActiveCallback) ((SparseArray) this.mActiveWatchers.valueAt(i7)).get(i);
            if (activeCallback != null && ((i6 = activeCallback.mWatchingUid) < 0 || i6 == i2)) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                }
                arraySet.add(activeCallback);
            }
        }
        if (arraySet == null) {
            return;
        }
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda2(), this, arraySet, Integer.valueOf(i), Integer.valueOf(i2), str, str2, Integer.valueOf(i3), Boolean.valueOf(z), Integer.valueOf(i4), Integer.valueOf(i5)));
    }

    public final void scheduleOpNotedIfNeededLocked(int i, int i2, int i3, String str, String str2, int i4, int i5) {
        int i6;
        int size = this.mNotedWatchers.size();
        ArraySet arraySet = null;
        for (int i7 = 0; i7 < size; i7++) {
            NotedCallback notedCallback = (NotedCallback) ((SparseArray) this.mNotedWatchers.valueAt(i7)).get(i);
            if (notedCallback != null && ((i6 = notedCallback.mWatchingUid) < 0 || i6 == i2)) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                }
                arraySet.add(notedCallback);
            }
        }
        if (arraySet == null) {
            return;
        }
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda13(), this, arraySet, Integer.valueOf(i), Integer.valueOf(i2), str, str2, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)));
    }

    public final void scheduleOpStartedIfNeededLocked(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9;
        int size = this.mStartedWatchers.size();
        ArraySet arraySet = null;
        for (int i10 = 0; i10 < size; i10++) {
            StartedCallback startedCallback = (StartedCallback) ((SparseArray) this.mStartedWatchers.valueAt(i10)).get(i);
            if (startedCallback != null && ((i9 = startedCallback.mWatchingUid) < 0 || i9 == i2)) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                }
                arraySet.add(startedCallback);
            }
        }
        if (arraySet == null) {
            return;
        }
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda0(2), this, arraySet, Integer.valueOf(i), Integer.valueOf(i2), str, str2, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), Integer.valueOf(i7), Integer.valueOf(i8)));
    }

    public final void setAudioRestriction(int i, int i2, int i3, int i4, String[] strArr) {
        enforceManageAppOpsModes(Binder.getCallingPid(), Binder.getCallingUid(), i3);
        verifyIncomingUid(i3);
        verifyIncomingOp(i);
        AudioRestrictionManager audioRestrictionManager = this.mAudioRestrictionManager;
        synchronized (audioRestrictionManager) {
            try {
                SparseArray sparseArray = (SparseArray) audioRestrictionManager.mZenModeAudioRestrictions.get(i);
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    audioRestrictionManager.mZenModeAudioRestrictions.put(i, sparseArray);
                }
                sparseArray.remove(i2);
                if (i4 != 0) {
                    AudioRestrictionManager.Restriction restriction = new AudioRestrictionManager.Restriction();
                    restriction.exceptionPackages = AudioRestrictionManager.Restriction.NO_EXCEPTIONS;
                    restriction.mode = i4;
                    if (strArr != null) {
                        restriction.exceptionPackages = new ArraySet(strArr.length);
                        for (String str : strArr) {
                            if (str != null) {
                                restriction.exceptionPackages.add(str.trim());
                            }
                        }
                    }
                    sparseArray.put(i2, restriction);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda1(0), this, Integer.valueOf(i), -2));
    }

    public final void setCameraAudioRestriction(int i) {
        enforceManageAppOpsModes(Binder.getCallingPid(), Binder.getCallingUid(), -1);
        AudioRestrictionManager audioRestrictionManager = this.mAudioRestrictionManager;
        synchronized (audioRestrictionManager) {
            audioRestrictionManager.mCameraAudioRestriction = i;
        }
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda1(0), this, 28, -2));
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda1(0), this, 3, -2));
    }

    public final void setCheckOpsDelegate(AccessCheckDelegate$AccessCheckDelegateImpl accessCheckDelegate$AccessCheckDelegateImpl) {
        synchronized (this) {
            CheckOpsDelegateDispatcher checkOpsDelegateDispatcher = this.mCheckOpsDelegateDispatcher;
            this.mCheckOpsDelegateDispatcher = new CheckOpsDelegateDispatcher(checkOpsDelegateDispatcher != null ? checkOpsDelegateDispatcher.mPolicy : null, accessCheckDelegate$AccessCheckDelegateImpl);
        }
    }

    public final void setHistoryParameters(int i, long j, int i2) {
        setHistoryParameters_enforcePermission();
        this.mHistoricalRegistry.setHistoryParameters(i, j, i2);
    }

    public final void setMode(int i, int i2, String str, int i3) {
        setMode(i, i2, str, i3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setMode(int r11, int r12, java.lang.String r13, int r14, com.android.internal.app.IAppOpsCallback r15) {
        /*
            r10 = this;
            int r0 = android.os.Binder.getCallingPid()
            int r1 = android.os.Binder.getCallingUid()
            r10.enforceManageAppOpsModes(r0, r1, r12)
            r10.verifyIncomingOp(r11)
            r0 = 58
            if (r11 != r0) goto L2d
            java.lang.String r0 = "restriction_policy"
            android.os.IBinder r0 = android.os.ServiceManager.getService(r0)     // Catch: android.os.RemoteException -> L2d
            com.samsung.android.knox.restriction.IRestrictionPolicy r0 = com.samsung.android.knox.restriction.IRestrictionPolicy.Stub.asInterface(r0)     // Catch: android.os.RemoteException -> L2d
            if (r0 == 0) goto L2d
            com.samsung.android.knox.ContextInfo r1 = new com.samsung.android.knox.ContextInfo     // Catch: android.os.RemoteException -> L2d
            int r2 = android.os.Binder.getCallingUid()     // Catch: android.os.RemoteException -> L2d
            r1.<init>(r2)     // Catch: android.os.RemoteException -> L2d
            boolean r0 = r0.isMockLocationEnabled(r1)     // Catch: android.os.RemoteException -> L2d
            goto L2e
        L2d:
            r0 = 1
        L2e:
            if (r0 != 0) goto L31
            return
        L31:
            int r0 = android.os.UserHandle.getUserId(r12)
            boolean r0 = r10.isIncomingPackageValid(r0, r13)
            if (r0 != 0) goto L3c
            return
        L3c:
            int r2 = android.app.AppOpsManager.opToSwitch(r11)
            r7 = 0
            r6 = 0
            r8 = 0
            r3 = r10
            r4 = r12
            r5 = r13
            com.android.server.appop.AppOpsService$PackageVerificationResult r11 = r3.verifyAndGetBypass(r4, r5, r6, r7, r8)     // Catch: java.lang.SecurityException -> Lb0
            monitor-enter(r10)
            android.app.AppOpsManager$RestrictionBypass r8 = r11.bypass     // Catch: java.lang.Throwable -> L97
            r11 = 1
            r6 = 0
            r7 = 0
            r3 = r10
            r4 = r12
            r5 = r13
            r9 = r11
            com.android.server.appop.AppOpsService$Ops r0 = r3.getOpsLocked(r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L97
            if (r0 != 0) goto L5c
            r11 = 0
            goto L60
        L5c:
            com.android.server.appop.AppOpsService$Op r11 = r10.getOpLocked(r0, r2, r12, r11)     // Catch: java.lang.Throwable -> L97
        L60:
            if (r11 == 0) goto L99
            com.android.server.appop.AppOpsCheckingServiceInterface r0 = r10.mAppOpsCheckingService     // Catch: java.lang.Throwable -> L97
            java.lang.String r1 = r11.packageName     // Catch: java.lang.Throwable -> L97
            int r3 = r11.op     // Catch: java.lang.Throwable -> L97
            int r4 = r11.uid     // Catch: java.lang.Throwable -> L97
            int r4 = android.os.UserHandle.getUserId(r4)     // Catch: java.lang.Throwable -> L97
            int r0 = r0.getPackageMode(r3, r4, r1)     // Catch: java.lang.Throwable -> L97
            if (r0 == r14) goto L99
            com.android.server.appop.AppOpsCheckingServiceInterface r0 = r10.mAppOpsCheckingService     // Catch: java.lang.Throwable -> L97
            java.lang.String r1 = r11.packageName     // Catch: java.lang.Throwable -> L97
            int r3 = r11.op     // Catch: java.lang.Throwable -> L97
            int r4 = r11.uid     // Catch: java.lang.Throwable -> L97
            int r4 = android.os.UserHandle.getUserId(r4)     // Catch: java.lang.Throwable -> L97
            int r0 = r0.getPackageMode(r3, r4, r1)     // Catch: java.lang.Throwable -> L97
            r10.mIgnoredCallback = r15     // Catch: java.lang.Throwable -> L97
            com.android.server.appop.AppOpsCheckingServiceInterface r15 = r10.mAppOpsCheckingService     // Catch: java.lang.Throwable -> L97
            java.lang.String r1 = r11.packageName     // Catch: java.lang.Throwable -> L97
            int r3 = r11.op     // Catch: java.lang.Throwable -> L97
            int r11 = r11.uid     // Catch: java.lang.Throwable -> L97
            int r11 = android.os.UserHandle.getUserId(r11)     // Catch: java.lang.Throwable -> L97
            r15.setPackageMode(r3, r14, r11, r1)     // Catch: java.lang.Throwable -> L97
            r6 = r0
            goto L9b
        L97:
            r11 = move-exception
            goto Lae
        L99:
            r11 = 3
            r6 = r11
        L9b:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L97
            java.lang.Class<android.os.storage.StorageManagerInternal> r10 = android.os.storage.StorageManagerInternal.class
            java.lang.Object r10 = com.android.server.LocalServices.getService(r10)
            r1 = r10
            android.os.storage.StorageManagerInternal r1 = (android.os.storage.StorageManagerInternal) r1
            if (r1 == 0) goto Lad
            r3 = r12
            r4 = r13
            r5 = r14
            r1.onAppOpsChanged(r2, r3, r4, r5, r6)
        Lad:
            return
        Lae:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L97
            throw r11
        Lb0:
            r10 = move-exception
            java.lang.String r11 = "setMode"
            logVerifyAndGetBypassFailure(r12, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.setMode(int, int, java.lang.String, int, com.android.internal.app.IAppOpsCallback):void");
    }

    public final void setUidMode(int i, int i2, int i3) {
        setUidMode(i, i2, i3, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0123, code lost:
    
        if (r29 != 4) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0125, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0133, code lost:
    
        if (r29 != 0) goto L54;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setUidMode(int r27, int r28, int r29, com.android.internal.app.IAppOpsCallback r30) {
        /*
            Method dump skipped, instructions count: 541
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.setUidMode(int, int, int, com.android.internal.app.IAppOpsCallback):void");
    }

    public final void setUserRestriction(int i, boolean z, IBinder iBinder, int i2, PackageTagsList packageTagsList) {
        if (Binder.getCallingPid() != Process.myPid()) {
            this.mContext.enforcePermission("android.permission.MANAGE_APP_OPS_RESTRICTIONS", Binder.getCallingPid(), Binder.getCallingUid(), null);
        }
        if (i2 != UserHandle.getCallingUserId() && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS") != 0) {
            throw new SecurityException("Need INTERACT_ACROSS_USERS_FULL or INTERACT_ACROSS_USERS to interact cross user ");
        }
        verifyIncomingOp(i);
        Objects.requireNonNull(iBinder);
        setUserRestrictionNoCheck(i, z, iBinder, i2, packageTagsList);
    }

    public final void setUserRestrictionNoCheck(int i, boolean z, IBinder iBinder, int i2, PackageTagsList packageTagsList) {
        synchronized (this) {
            try {
                ClientUserRestrictionState clientUserRestrictionState = (ClientUserRestrictionState) this.mOpUserRestrictions.get(iBinder);
                if (clientUserRestrictionState == null) {
                    try {
                        clientUserRestrictionState = new ClientUserRestrictionState(iBinder);
                        Slog.w("AppOps", "setUserRestrictionNoCheck code: " + i + "Calling Pid:" + Binder.getCallingPid());
                        this.mOpUserRestrictions.put(iBinder, clientUserRestrictionState);
                    } catch (RemoteException unused) {
                        return;
                    }
                }
                if (clientUserRestrictionState.setRestriction(i, z, packageTagsList, i2)) {
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda1(0), this, Integer.valueOf(i), -2));
                    this.mHandler.sendMessage(PooledLambda.obtainMessage(new AppOpsService$$ExternalSyntheticLambda5(0), this, Integer.valueOf(i), Boolean.valueOf(z), Integer.valueOf(i2)));
                }
                AppOpsRestrictions appOpsRestrictions = AppOpsService.this.mAppOpsRestrictions;
                if (!((AppOpsRestrictionsImpl) appOpsRestrictions).mUserRestrictions.containsKey(clientUserRestrictionState.token)) {
                    this.mOpUserRestrictions.remove(iBinder);
                    clientUserRestrictionState.token.unlinkToDeath(clientUserRestrictionState, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setUserRestrictions(Bundle bundle, IBinder iBinder, int i) {
        checkSystemUid("setUserRestrictions");
        Objects.requireNonNull(bundle);
        Objects.requireNonNull(iBinder);
        for (int i2 = 0; i2 < 149; i2++) {
            String opToRestriction = AppOpsManager.opToRestriction(i2);
            if (opToRestriction != null) {
                setUserRestrictionNoCheck(i2, bundle.getBoolean(opToRestriction, false), iBinder, i, null);
            }
        }
    }

    public final boolean shouldCollectNotes(int i) {
        Preconditions.checkArgumentInRange(i, 0, 148, "opCode");
        if (AppOpsManager.shouldForceCollectNoteForOp(i)) {
            return true;
        }
        String opToPermission = AppOpsManager.opToPermission(i);
        if (opToPermission == null) {
            return false;
        }
        try {
            PermissionInfo permissionInfo = this.mContext.getPackageManager().getPermissionInfo(opToPermission, 0);
            return permissionInfo.getProtection() == 1 || (permissionInfo.getProtectionFlags() & 64) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean shouldIgnoreCallback(int i, int i2, int i3) {
        return AppOpsManager.opRestrictsRead(i) && this.mContext.checkPermission("android.permission.MANAGE_APPOPS", i2, i3) != 0;
    }

    public final void shutdown() {
        boolean z;
        Slog.w("AppOps", "Writing app ops before shutdown...");
        synchronized (this) {
            try {
                z = false;
                if (this.mWriteScheduled) {
                    this.mWriteScheduled = false;
                    this.mFastWriteScheduled = false;
                    this.mHandler.removeCallbacks(this.mWriteRunner);
                    z = true;
                }
            } finally {
            }
        }
        if (z) {
            writeRecentAccesses();
        }
        this.mAppOpsCheckingService.shutdown();
        HistoricalRegistry historicalRegistry = this.mHistoricalRegistry;
        synchronized (historicalRegistry.mInMemoryLock) {
            try {
                if (historicalRegistry.mMode == 0) {
                    return;
                }
                historicalRegistry.persistPendingHistory();
            } finally {
            }
        }
    }

    public final SyncNotedAppOp startOperation(IBinder iBinder, int i, int i2, String str, String str2, boolean z, boolean z2, String str3, boolean z3, int i3, int i4) {
        return this.mCheckOpsDelegateDispatcher.startOperation(iBinder, i, i2, str, str2, 0, z, z2, str3, z3, i3, i4);
    }

    public final SyncNotedAppOp startOperationForDevice(IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5) {
        return this.mCheckOpsDelegateDispatcher.startOperation(iBinder, i, i2, str, str2, i3, z, z2, str3, z3, i4, i5);
    }

    public final SyncNotedAppOp startOperationUnchecked(IBinder iBinder, int i, int i2, String str, String str2, int i3, int i4, String str3, String str4, int i5, int i6, boolean z, boolean z2, String str5, boolean z3, int i7, int i8) {
        AttributedOp attributedOp;
        String str6;
        int i9;
        try {
            PackageVerificationResult verifyAndGetBypass = verifyAndGetBypass(i2, str, str2, str3, false);
            String str7 = !verifyAndGetBypass.isAttributionTagValid ? null : str2;
            String str8 = (str4 == null || isAttributionTagDefined(str, str3, str4)) ? str4 : null;
            synchronized (this) {
                try {
                    String str9 = str7;
                    Ops opsLocked = getOpsLocked(i2, str, str7, verifyAndGetBypass.isAttributionTagValid, verifyAndGetBypass.bypass, true);
                    if (opsLocked == null) {
                        scheduleOpStartedIfNeededLocked(i, i2, str, str9, i3, i6, 1, 0, i7, i8);
                        if (!DEBUG_MID) {
                            if (DEBUG) {
                            }
                            return new SyncNotedAppOp(2, i, str9, str);
                        }
                        Slog.d("AppOps", "startOperationUnchecked: no op for code " + i + " uid " + i2 + " package " + str + " flags: " + AppOpsManager.flagsToString(i6));
                        return new SyncNotedAppOp(2, i, str9, str);
                    }
                    Op opLocked = getOpLocked(opsLocked, i, i2, true);
                    AttributedOp orCreateAttribution = opLocked.getOrCreateAttribution(opLocked, str9, getPersistentId(i3));
                    UidState uidState = opsLocked.uidState;
                    boolean isOpRestrictedLocked = isOpRestrictedLocked(i2, i, str, str9, i3, verifyAndGetBypass.bypass, false);
                    int opToSwitch = AppOpsManager.opToSwitch(i);
                    if (isOpAllowedForUid(i2)) {
                        str6 = str9;
                        attributedOp = orCreateAttribution;
                    } else {
                        int uidMode = this.mAppOpsCheckingService.getUidMode(uidState.uid, opToSwitch, getPersistentId(i3));
                        if (uidMode == AppOpsManager.opToDefaultMode(opToSwitch)) {
                            attributedOp = orCreateAttribution;
                            Op opLocked2 = opToSwitch != i ? getOpLocked(opsLocked, opToSwitch, i2, true) : opLocked;
                            UidState uidState2 = opLocked2.uidState;
                            int i10 = opLocked2.op;
                            int evalMode = uidState2.evalMode(i10, this.mAppOpsCheckingService.getPackageMode(i10, UserHandle.getUserId(opLocked2.uid), opLocked2.packageName));
                            if (evalMode == 0 || (z && evalMode == 3)) {
                                str6 = str9;
                            }
                            if (DEBUG_MID || DEBUG) {
                                Slog.d("AppOps", "startOperationUnchecked: reject #" + evalMode + " for code " + opToSwitch + " (" + i + ") uid " + i2 + " package " + str + " flags: " + AppOpsManager.flagsToString(i6));
                            }
                            attributedOp.rejected(uidState.getState(), i6);
                            scheduleOpStartedIfNeededLocked(i, i2, str, str9, i3, i6, evalMode, 0, i7, i8);
                            return new SyncNotedAppOp(evalMode, i, str9, str);
                        }
                        int evalMode2 = uidState.evalMode(i, uidMode);
                        if (!shouldStartForMode(evalMode2, z)) {
                            if (DEBUG_MID || DEBUG) {
                                Slog.d("AppOps", "startOperationUnchecked: uid reject #" + evalMode2 + " for code " + opToSwitch + " (" + i + ") uid " + i2 + " package " + str + " flags: " + AppOpsManager.flagsToString(i6));
                            }
                            orCreateAttribution.rejected(uidState.getState(), i6);
                            scheduleOpStartedIfNeededLocked(i, i2, str, str9, i3, i6, evalMode2, 0, i7, i8);
                            return new SyncNotedAppOp(evalMode2, i, str9, str);
                        }
                        attributedOp = orCreateAttribution;
                        str6 = str9;
                    }
                    if (DEBUG_MID || DEBUG) {
                        Slog.d("AppOps", "startOperationUnchecked: allowing code " + i + " uid " + i2 + " package " + str + " restricted: " + isOpRestrictedLocked + " flags: " + AppOpsManager.flagsToString(i6));
                    }
                    try {
                        if (isOpRestrictedLocked) {
                            attributedOp.startedOrPaused(iBinder, i3, i4, str3, str8, getPersistentId(i5), uidState.getState(), i6, i7, i8, false, false);
                            i9 = 0;
                        } else {
                            attributedOp.startedOrPaused(iBinder, i3, i4, str3, str8, getPersistentId(i5), uidState.getState(), i6, i7, i8, false, true);
                            i9 = 1;
                        }
                        scheduleOpStartedIfNeededLocked(i, i2, str, str6, i3, i6, isOpRestrictedLocked ? 1 : 0, i9, i7, i8);
                        if (z2 && !isOpRestrictedLocked) {
                            collectAsyncNotedOp(i2, i, str, str6, str5, z3, 1);
                        }
                        return new SyncNotedAppOp(isOpRestrictedLocked ? 1 : 0, i, str6, str);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (SecurityException e2) {
            logVerifyAndGetBypassFailure(i2, e2, "startOperation");
            return new SyncNotedAppOp(2, i, str2, str);
        }
    }

    public final SyncNotedAppOp startProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) {
        return this.mCheckOpsDelegateDispatcher.startProxyOperation(iBinder, i, attributionSource, z, z2, str, z3, z4, i2, i3, i4);
    }

    public final SyncNotedAppOp startProxyOperationWithState(IBinder iBinder, int i, AttributionSourceState attributionSourceState, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4) {
        return this.mCheckOpsDelegateDispatcher.startProxyOperation(iBinder, i, new AttributionSource(attributionSourceState), z, z2, str, z3, z4, i2, i3, i4);
    }

    public final void startWatchingActive(int[] iArr, IAppOpsActiveCallback iAppOpsActiveCallback) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int i = this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") != 0 ? callingUid : -1;
        if (iArr != null) {
            Preconditions.checkArrayElementsInRange(iArr, 0, 148, "Invalid op code in: " + Arrays.toString(iArr));
        }
        if (iAppOpsActiveCallback == null) {
            return;
        }
        synchronized (this) {
            try {
                SparseArray sparseArray = (SparseArray) this.mActiveWatchers.get(iAppOpsActiveCallback.asBinder());
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    this.mActiveWatchers.put(iAppOpsActiveCallback.asBinder(), sparseArray);
                }
                SparseArray sparseArray2 = sparseArray;
                ActiveCallback activeCallback = new ActiveCallback(iAppOpsActiveCallback, i, callingUid, callingPid);
                for (int i2 : iArr) {
                    sparseArray2.put(i2, activeCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(iAppOpsAsyncNotedCallback);
        int callingUid = Binder.getCallingUid();
        final Pair pair = new Pair(str, Integer.valueOf(callingUid));
        verifyAndGetBypass(callingUid, str, null, null, false);
        synchronized (this) {
            try {
                RemoteCallbackList remoteCallbackList = (RemoteCallbackList) this.mAsyncOpWatchers.get(pair);
                if (remoteCallbackList == null) {
                    remoteCallbackList = new RemoteCallbackList() { // from class: com.android.server.appop.AppOpsService.8
                        @Override // android.os.RemoteCallbackList
                        public final void onCallbackDied(IInterface iInterface) {
                            synchronized (AppOpsService.this) {
                                try {
                                    if (getRegisteredCallbackCount() == 0) {
                                        AppOpsService.this.mAsyncOpWatchers.remove(pair);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    };
                    this.mAsyncOpWatchers.put(pair, remoteCallbackList);
                }
                remoteCallbackList.register(iAppOpsAsyncNotedCallback);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startWatchingMode(int i, String str, IAppOpsCallback iAppOpsCallback) {
        startWatchingModeWithFlags(i, str, 0, iAppOpsCallback);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0036, code lost:
    
        if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).filterAppAccess(android.os.Binder.getCallingUid(), android.os.UserHandle.getUserId(r5), r12, true) == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startWatchingModeWithFlags(int r11, java.lang.String r12, int r13, com.android.internal.app.IAppOpsCallback r14) {
        /*
            r10 = this;
            int r5 = android.os.Binder.getCallingUid()
            int r6 = android.os.Binder.getCallingPid()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Invalid op code: "
            r0.<init>(r1)
            r0.append(r11)
            java.lang.String r0 = r0.toString()
            r1 = 148(0x94, float:2.07E-43)
            r7 = -1
            com.android.internal.util.Preconditions.checkArgumentInRange(r11, r7, r1, r0)
            if (r14 != 0) goto L1f
            return
        L1f:
            if (r12 == 0) goto L3a
            int r0 = android.os.UserHandle.getUserId(r5)
            int r1 = android.os.Binder.getCallingUid()
            java.lang.Class<android.content.pm.PackageManagerInternal> r2 = android.content.pm.PackageManagerInternal.class
            java.lang.Object r2 = com.android.server.LocalServices.getService(r2)
            android.content.pm.PackageManagerInternal r2 = (android.content.pm.PackageManagerInternal) r2
            r3 = 1
            boolean r0 = r2.filterAppAccess(r1, r0, r12, r3)
            if (r0 != 0) goto L3a
        L38:
            r8 = r3
            goto L3c
        L3a:
            r3 = 0
            goto L38
        L3c:
            monitor-enter(r10)
            if (r11 == r7) goto L47
            int r0 = android.app.AppOpsManager.opToSwitch(r11)     // Catch: java.lang.Throwable -> L45
            r9 = r0
            goto L48
        L45:
            r11 = move-exception
            goto La6
        L47:
            r9 = r11
        L48:
            r0 = r13 & 2
            if (r0 != 0) goto L51
            if (r11 != r7) goto L4f
            r11 = -2
        L4f:
            r4 = r11
            goto L52
        L51:
            r4 = r9
        L52:
            android.util.ArrayMap r11 = r10.mModeWatchers     // Catch: java.lang.Throwable -> L45
            android.os.IBinder r0 = r14.asBinder()     // Catch: java.lang.Throwable -> L45
            java.lang.Object r11 = r11.get(r0)     // Catch: java.lang.Throwable -> L45
            com.android.server.appop.AppOpsService$ModeCallback r11 = (com.android.server.appop.AppOpsService.ModeCallback) r11     // Catch: java.lang.Throwable -> L45
            if (r11 != 0) goto L72
            com.android.server.appop.AppOpsService$ModeCallback r11 = new com.android.server.appop.AppOpsService$ModeCallback     // Catch: java.lang.Throwable -> L45
            r0 = r11
            r1 = r10
            r2 = r14
            r3 = r13
            r0.<init>(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L45
            android.util.ArrayMap r13 = r10.mModeWatchers     // Catch: java.lang.Throwable -> L45
            android.os.IBinder r14 = r14.asBinder()     // Catch: java.lang.Throwable -> L45
            r13.put(r14, r11)     // Catch: java.lang.Throwable -> L45
        L72:
            if (r9 == r7) goto L8b
            android.util.SparseArray r13 = r10.mOpModeWatchers     // Catch: java.lang.Throwable -> L45
            java.lang.Object r13 = r13.get(r9)     // Catch: java.lang.Throwable -> L45
            android.util.ArraySet r13 = (android.util.ArraySet) r13     // Catch: java.lang.Throwable -> L45
            if (r13 != 0) goto L88
            android.util.ArraySet r13 = new android.util.ArraySet     // Catch: java.lang.Throwable -> L45
            r13.<init>()     // Catch: java.lang.Throwable -> L45
            android.util.SparseArray r14 = r10.mOpModeWatchers     // Catch: java.lang.Throwable -> L45
            r14.put(r9, r13)     // Catch: java.lang.Throwable -> L45
        L88:
            r13.add(r11)     // Catch: java.lang.Throwable -> L45
        L8b:
            if (r8 == 0) goto La4
            android.util.ArrayMap r13 = r10.mPackageModeWatchers     // Catch: java.lang.Throwable -> L45
            java.lang.Object r13 = r13.get(r12)     // Catch: java.lang.Throwable -> L45
            android.util.ArraySet r13 = (android.util.ArraySet) r13     // Catch: java.lang.Throwable -> L45
            if (r13 != 0) goto La1
            android.util.ArraySet r13 = new android.util.ArraySet     // Catch: java.lang.Throwable -> L45
            r13.<init>()     // Catch: java.lang.Throwable -> L45
            android.util.ArrayMap r14 = r10.mPackageModeWatchers     // Catch: java.lang.Throwable -> L45
            r14.put(r12, r13)     // Catch: java.lang.Throwable -> L45
        La1:
            r13.add(r11)     // Catch: java.lang.Throwable -> L45
        La4:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L45
            return
        La6:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L45
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.startWatchingModeWithFlags(int, java.lang.String, int, com.android.internal.app.IAppOpsCallback):void");
    }

    public final void startWatchingNoted(int[] iArr, IAppOpsNotedCallback iAppOpsNotedCallback) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int i = this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") != 0 ? callingUid : -1;
        Preconditions.checkArgument(!ArrayUtils.isEmpty(iArr), "Ops cannot be null or empty");
        Preconditions.checkArrayElementsInRange(iArr, 0, 148, "Invalid op code in: " + Arrays.toString(iArr));
        Objects.requireNonNull(iAppOpsNotedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                SparseArray sparseArray = (SparseArray) this.mNotedWatchers.get(iAppOpsNotedCallback.asBinder());
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    this.mNotedWatchers.put(iAppOpsNotedCallback.asBinder(), sparseArray);
                }
                SparseArray sparseArray2 = sparseArray;
                NotedCallback notedCallback = new NotedCallback(iAppOpsNotedCallback, i, callingUid, callingPid);
                for (int i2 : iArr) {
                    sparseArray2.put(i2, notedCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startWatchingStarted(int[] iArr, IAppOpsStartedCallback iAppOpsStartedCallback) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int i = this.mContext.checkCallingOrSelfPermission("android.permission.WATCH_APPOPS") != 0 ? callingUid : -1;
        Preconditions.checkArgument(!ArrayUtils.isEmpty(iArr), "Ops cannot be null or empty");
        Preconditions.checkArrayElementsInRange(iArr, 0, 148, "Invalid op code in: " + Arrays.toString(iArr));
        Objects.requireNonNull(iAppOpsStartedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                SparseArray sparseArray = (SparseArray) this.mStartedWatchers.get(iAppOpsStartedCallback.asBinder());
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    this.mStartedWatchers.put(iAppOpsStartedCallback.asBinder(), sparseArray);
                }
                SparseArray sparseArray2 = sparseArray;
                StartedCallback startedCallback = new StartedCallback(iAppOpsStartedCallback, i, callingUid, callingPid);
                for (int i2 : iArr) {
                    sparseArray2.put(i2, startedCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopWatchingActive(IAppOpsActiveCallback iAppOpsActiveCallback) {
        if (iAppOpsActiveCallback == null) {
            return;
        }
        synchronized (this) {
            try {
                SparseArray sparseArray = (SparseArray) this.mActiveWatchers.remove(iAppOpsActiveCallback.asBinder());
                if (sparseArray == null) {
                    return;
                }
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    ActiveCallback activeCallback = (ActiveCallback) sparseArray.valueAt(i);
                    activeCallback.mCallback.asBinder().unlinkToDeath(activeCallback, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopWatchingAsyncNoted(String str, IAppOpsAsyncNotedCallback iAppOpsAsyncNotedCallback) {
        Objects.requireNonNull(str);
        Objects.requireNonNull(iAppOpsAsyncNotedCallback);
        int callingUid = Binder.getCallingUid();
        Pair pair = new Pair(str, Integer.valueOf(callingUid));
        verifyAndGetBypass(callingUid, str, null, null, false);
        synchronized (this) {
            try {
                RemoteCallbackList remoteCallbackList = (RemoteCallbackList) this.mAsyncOpWatchers.get(pair);
                if (remoteCallbackList != null) {
                    remoteCallbackList.unregister(iAppOpsAsyncNotedCallback);
                    if (remoteCallbackList.getRegisteredCallbackCount() == 0) {
                        this.mAsyncOpWatchers.remove(pair);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopWatchingMode(IAppOpsCallback iAppOpsCallback) {
        if (iAppOpsCallback == null) {
            return;
        }
        synchronized (this) {
            try {
                ModeCallback modeCallback = (ModeCallback) this.mModeWatchers.remove(iAppOpsCallback.asBinder());
                if (modeCallback != null) {
                    modeCallback.mCallback.asBinder().unlinkToDeath(modeCallback, 0);
                    for (int size = this.mOpModeWatchers.size() - 1; size >= 0; size--) {
                        ArraySet arraySet = (ArraySet) this.mOpModeWatchers.valueAt(size);
                        arraySet.remove(modeCallback);
                        if (arraySet.size() <= 0) {
                            this.mOpModeWatchers.removeAt(size);
                        }
                    }
                    for (int size2 = this.mPackageModeWatchers.size() - 1; size2 >= 0; size2--) {
                        ArraySet arraySet2 = (ArraySet) this.mPackageModeWatchers.valueAt(size2);
                        arraySet2.remove(modeCallback);
                        if (arraySet2.size() <= 0) {
                            this.mPackageModeWatchers.removeAt(size2);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopWatchingNoted(IAppOpsNotedCallback iAppOpsNotedCallback) {
        Objects.requireNonNull(iAppOpsNotedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                SparseArray sparseArray = (SparseArray) this.mNotedWatchers.remove(iAppOpsNotedCallback.asBinder());
                if (sparseArray == null) {
                    return;
                }
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    NotedCallback notedCallback = (NotedCallback) sparseArray.valueAt(i);
                    notedCallback.mCallback.asBinder().unlinkToDeath(notedCallback, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopWatchingStarted(IAppOpsStartedCallback iAppOpsStartedCallback) {
        Objects.requireNonNull(iAppOpsStartedCallback, "Callback cannot be null");
        synchronized (this) {
            try {
                SparseArray sparseArray = (SparseArray) this.mStartedWatchers.remove(iAppOpsStartedCallback.asBinder());
                if (sparseArray == null) {
                    return;
                }
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    StartedCallback startedCallback = (StartedCallback) sparseArray.valueAt(i);
                    startedCallback.mCallback.asBinder().unlinkToDeath(startedCallback, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void switchPackageIfBootTimeOrRarelyUsedLocked(String str) {
        if (this.mSampledPackage == null) {
            if (ThreadLocalRandom.current().nextFloat() < 0.5f) {
                this.mSamplingStrategy = 3;
                resampleAppOpForPackageLocked(str, true);
                return;
            }
            return;
        }
        if (this.mRarelyUsedPackages.contains(str)) {
            this.mRarelyUsedPackages.remove(str);
            if (ThreadLocalRandom.current().nextFloat() < 0.5f) {
                this.mSamplingStrategy = 2;
                resampleAppOpForPackageLocked(str, true);
            }
        }
    }

    public final void systemReady() {
        this.mVirtualDeviceManagerInternal = (VirtualDeviceManagerInternal) LocalServices.getService(VirtualDeviceManagerInternal.class);
        this.mAppOpsCheckingService.systemReady();
        initializeUidStates();
        Constants constants = this.mConstants;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        constants.mResolver = contentResolver;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("app_ops_constants"), false, constants);
        constants.updateConstants();
        this.mHistoricalRegistry.systemReady(this.mContext.getContentResolver());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        Context context = this.mContext;
        AnonymousClass3 anonymousClass3 = this.mOnPackageUpdatedReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context.registerReceiverAsUser(anonymousClass3, userHandle, intentFilter, null, null);
        prepareInternalCallbacks();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        intentFilter2.addAction("android.intent.action.PACKAGES_SUSPENDED");
        this.mContext.registerReceiverAsUser(new AnonymousClass3(this, 1), userHandle, intentFilter2, null, null);
        this.mHandler.postDelayed(new AnonymousClass1(this, 1), 300000L);
        PackageManagerService.this.mExternalSourcesPolicy = new AnonymousClass6();
        this.mSensorPrivacyManager = SensorPrivacyManager.getInstance(this.mContext);
    }

    public final void trimUidStatesLocked(SparseBooleanArray sparseBooleanArray, Map map) {
        synchronized (this) {
            try {
                for (int size = this.mUidStates.size() - 1; size >= 0; size--) {
                    int keyAt = this.mUidStates.keyAt(size);
                    if (sparseBooleanArray.get(keyAt, false)) {
                        int appId = UserHandle.getAppId(keyAt);
                        if (appId >= 10000 && appId <= 19999) {
                            ArrayMap arrayMap = ((UidState) this.mUidStates.valueAt(size)).pkgOps;
                            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                                String str = (String) arrayMap.keyAt(size2);
                                if (map.containsKey(str)) {
                                    AndroidPackage androidPackage = ((PackageState) map.get(str)).getAndroidPackage();
                                    if (androidPackage != null) {
                                        refreshAttributionsLocked(androidPackage, keyAt);
                                    }
                                } else {
                                    arrayMap.removeAt(size2);
                                }
                            }
                            if (arrayMap.isEmpty()) {
                                this.mUidStates.removeAt(size);
                            }
                        }
                    } else {
                        this.mUidStates.removeAt(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void uidRemoved(int i) {
        synchronized (this) {
            try {
                if (this.mUidStates.indexOfKey(i) >= 0) {
                    ((UidState) this.mUidStates.get(i)).clear();
                    this.mUidStates.remove(i);
                    scheduleFastWriteLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateStartedOpModeForUidForDefaultDeviceLocked(int i, int i2, boolean z) {
        UidState uidState;
        int i3;
        int i4;
        ArrayMap arrayMap;
        AppOpsService appOpsService = this;
        UidState uidState2 = (UidState) appOpsService.mUidStates.get(i2);
        if (uidState2 == null) {
            return;
        }
        int size = uidState2.pkgOps.size();
        boolean z2 = false;
        int i5 = 0;
        while (i5 < size) {
            Ops ops = (Ops) uidState2.pkgOps.valueAt(i5);
            Op op = ops != null ? (Op) ops.get(i) : null;
            if (op != null) {
                int packageMode = appOpsService.mAppOpsCheckingService.getPackageMode(op.op, UserHandle.getUserId(op.uid), op.packageName);
                if (packageMode == 0 || packageMode == 4) {
                    ArrayMap arrayMap2 = (ArrayMap) op.mDeviceAttributedOps.get("default:0");
                    int i6 = z2 ? 1 : 0;
                    while (i6 < arrayMap2.size()) {
                        AttributedOp attributedOp = (AttributedOp) arrayMap2.valueAt(i6);
                        boolean z3 = true;
                        if (z && attributedOp.isRunning()) {
                            if (attributedOp.isRunning()) {
                                if (attributedOp.mPausedInProgressEvents == null) {
                                    attributedOp.mPausedInProgressEvents = new ArrayMap(1);
                                }
                                int i7 = z2 ? 1 : 0;
                                while (i7 < attributedOp.mInProgressEvents.size()) {
                                    AttributedOp.InProgressStartOpEvent inProgressStartOpEvent = (AttributedOp.InProgressStartOpEvent) attributedOp.mInProgressEvents.valueAt(i7);
                                    attributedOp.mPausedInProgressEvents.put(inProgressStartOpEvent.mClientId, inProgressStartOpEvent);
                                    attributedOp.finishOrPause(inProgressStartOpEvent.mClientId, z2, z3);
                                    Op op2 = attributedOp.parent;
                                    attributedOp.mAppOpsService.scheduleOpActiveChangedIfNeededLocked(op2.op, op2.uid, op2.packageName, attributedOp.tag, inProgressStartOpEvent.mVirtualDeviceId, false, inProgressStartOpEvent.mAttributionFlags, inProgressStartOpEvent.mAttributionChainId);
                                    i7++;
                                    z2 = false;
                                    z3 = true;
                                }
                                attributedOp.mInProgressEvents = null;
                            }
                        } else if (attributedOp.isPaused() && attributedOp.isPaused()) {
                            if (attributedOp.mInProgressEvents == null) {
                                attributedOp.mInProgressEvents = new ArrayMap(attributedOp.mPausedInProgressEvents.size());
                            }
                            boolean z4 = !attributedOp.mPausedInProgressEvents.isEmpty() && attributedOp.mInProgressEvents.isEmpty();
                            long currentTimeMillis = System.currentTimeMillis();
                            int i8 = 0;
                            while (i8 < attributedOp.mPausedInProgressEvents.size()) {
                                AttributedOp.InProgressStartOpEvent inProgressStartOpEvent2 = (AttributedOp.InProgressStartOpEvent) attributedOp.mPausedInProgressEvents.valueAt(i8);
                                attributedOp.mInProgressEvents.put(inProgressStartOpEvent2.mClientId, inProgressStartOpEvent2);
                                inProgressStartOpEvent2.mStartElapsedTime = SystemClock.elapsedRealtime();
                                inProgressStartOpEvent2.mStartTime = currentTimeMillis;
                                HistoricalRegistry historicalRegistry = attributedOp.mAppOpsService.mHistoricalRegistry;
                                Op op3 = attributedOp.parent;
                                UidState uidState3 = uidState2;
                                int i9 = size;
                                ArrayMap arrayMap3 = arrayMap2;
                                int i10 = i5;
                                long j = currentTimeMillis;
                                historicalRegistry.incrementOpAccessedCount(op3.op, op3.uid, op3.packageName, attributedOp.tag, inProgressStartOpEvent2.mUidState, inProgressStartOpEvent2.mFlags, j, inProgressStartOpEvent2.mAttributionFlags, inProgressStartOpEvent2.mAttributionChainId);
                                if (z4) {
                                    AppOpsService appOpsService2 = attributedOp.mAppOpsService;
                                    Op op4 = attributedOp.parent;
                                    appOpsService2.scheduleOpActiveChangedIfNeededLocked(op4.op, op4.uid, op4.packageName, attributedOp.tag, inProgressStartOpEvent2.mVirtualDeviceId, true, inProgressStartOpEvent2.mAttributionFlags, inProgressStartOpEvent2.mAttributionChainId);
                                }
                                AppOpsService appOpsService3 = attributedOp.mAppOpsService;
                                Op op5 = attributedOp.parent;
                                appOpsService3.scheduleOpStartedIfNeededLocked(op5.op, op5.uid, op5.packageName, attributedOp.tag, inProgressStartOpEvent2.mVirtualDeviceId, inProgressStartOpEvent2.mFlags, 0, 2, inProgressStartOpEvent2.mAttributionFlags, inProgressStartOpEvent2.mAttributionChainId);
                                i8++;
                                uidState2 = uidState3;
                                size = i9;
                                arrayMap2 = arrayMap3;
                                i5 = i10;
                                currentTimeMillis = j;
                            }
                            uidState = uidState2;
                            i3 = size;
                            i4 = i5;
                            arrayMap = arrayMap2;
                            attributedOp.mPausedInProgressEvents = null;
                            i6++;
                            uidState2 = uidState;
                            size = i3;
                            arrayMap2 = arrayMap;
                            i5 = i4;
                            z2 = false;
                        }
                        uidState = uidState2;
                        i3 = size;
                        i4 = i5;
                        arrayMap = arrayMap2;
                        i6++;
                        uidState2 = uidState;
                        size = i3;
                        arrayMap2 = arrayMap;
                        i5 = i4;
                        z2 = false;
                    }
                }
            }
            i5++;
            appOpsService = this;
            uidState2 = uidState2;
            size = size;
            z2 = false;
        }
    }

    public final void updateStartedOpModeForUserForDefaultDevice(int i, int i2, boolean z) {
        synchronized (this) {
            try {
                int size = this.mUidStates.size();
                for (int i3 = 0; i3 < size; i3++) {
                    int keyAt = this.mUidStates.keyAt(i3);
                    if (i2 == -1 || UserHandle.getUserId(keyAt) == i2) {
                        updateStartedOpModeForUidForDefaultDeviceLocked(i, keyAt, z);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0221  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.appop.AppOpsService.PackageVerificationResult verifyAndGetBypass(int r25, java.lang.String r26, java.lang.String r27, java.lang.String r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 557
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.appop.AppOpsService.verifyAndGetBypass(int, java.lang.String, java.lang.String, java.lang.String, boolean):com.android.server.appop.AppOpsService$PackageVerificationResult");
    }

    public final void verifyIncomingOp(int i) {
        if (i < 0 || i >= 149) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad operation #"));
        }
        if (!AppOpsManager.opRestrictsRead(i) || this.mContext.checkPermission("android.permission.MANAGE_APPOPS", Binder.getCallingPid(), Binder.getCallingUid()) == 0 || this.mContext.checkPermission("android.permission.GET_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid()) == 0 || this.mContext.checkPermission("android.permission.MANAGE_APP_OPS_MODES", Binder.getCallingPid(), Binder.getCallingUid()) == 0) {
            return;
        }
        throw new SecurityException("verifyIncomingOp: uid " + Binder.getCallingUid() + " does not have any of {MANAGE_APPOPS, GET_APP_OPS_STATS, MANAGE_APP_OPS_MODES}");
    }

    public final void verifyIncomingProxyUid(AttributionSource attributionSource) {
        if (attributionSource.getUid() == Binder.getCallingUid() || Binder.getCallingPid() == Process.myPid() || attributionSource.isTrusted(this.mContext)) {
            return;
        }
        this.mContext.enforcePermission("android.permission.UPDATE_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
    }

    public final void verifyIncomingUid(int i) {
        if (i == Binder.getCallingUid() || Binder.getCallingPid() == Process.myPid()) {
            return;
        }
        this.mContext.enforcePermission("android.permission.UPDATE_APP_OPS_STATS", Binder.getCallingPid(), Binder.getCallingUid(), null);
    }

    public final void writePermissionAccessInformation(int i, int i2, String str, String str2, String str3, int i3) {
        try {
            this.mPermissionAccessInformationController.write(i, i2, str, str2, str3, i3);
        } catch (Exception e) {
            Slog.w("AppOps", e.getMessage(), e);
        }
    }

    public void writeRecentAccesses() {
        AtomicFile atomicFile;
        FileOutputStream fileOutputStream;
        String packageName;
        int uid;
        ArraySet arraySet;
        AppOpsManager.AttributedOpEntry attributedOpEntry;
        String str;
        int i;
        AppOpsService appOpsService = this;
        if (Flags.deviceAwareAppOpNewSchemaEnabled()) {
            synchronized (this) {
                appOpsService.mRecentAccessPersistence.writeRecentAccesses(appOpsService.mUidStates);
            }
            appOpsService.mHistoricalRegistry.mDiscreteRegistry.writeAndClearAccessHistory();
            return;
        }
        AtomicFile atomicFile2 = appOpsService.mRecentAccessesFile;
        synchronized (atomicFile2) {
            try {
                try {
                    try {
                        FileOutputStream startWrite = appOpsService.mRecentAccessesFile.startWrite();
                        try {
                            appOpsService.mPermissionAccessInformationController.flushAsync();
                        } catch (Exception e) {
                            Slog.w("AppOps", e.getMessage(), e);
                        }
                        String str2 = null;
                        List packagesForOpsForDevice = appOpsService.getPackagesForOpsForDevice(null, "default:0");
                        try {
                            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                            resolveSerializer.startDocument((String) null, Boolean.TRUE);
                            resolveSerializer.startTag((String) null, "app-ops");
                            resolveSerializer.attributeInt((String) null, "v", 1);
                            if (packagesForOpsForDevice != null) {
                                String str3 = null;
                                int i2 = 0;
                                while (i2 < packagesForOpsForDevice.size()) {
                                    try {
                                        AppOpsManager.PackageOps packageOps = (AppOpsManager.PackageOps) packagesForOpsForDevice.get(i2);
                                        if (!Objects.equals(packageOps.getPackageName(), str3)) {
                                            if (str3 != null) {
                                                resolveSerializer.endTag(str2, "pkg");
                                            }
                                            str3 = packageOps.getPackageName();
                                            if (str3 != null) {
                                                resolveSerializer.startTag(str2, "pkg");
                                                resolveSerializer.attribute(str2, "n", str3);
                                            }
                                        }
                                        resolveSerializer.startTag(str2, "uid");
                                        resolveSerializer.attributeInt(str2, "n", packageOps.getUid());
                                        List ops = packageOps.getOps();
                                        int i3 = 0;
                                        while (i3 < ops.size()) {
                                            AppOpsManager.OpEntry opEntry = (AppOpsManager.OpEntry) ops.get(i3);
                                            resolveSerializer.startTag(str2, "op");
                                            resolveSerializer.attributeInt(str2, "n", opEntry.getOp());
                                            if (opEntry.getMode() != AppOpsManager.opToDefaultMode(opEntry.getOp())) {
                                                resolveSerializer.attributeInt(str2, "m", opEntry.getMode());
                                            }
                                            Iterator it = opEntry.getAttributedOpEntries().keySet().iterator();
                                            while (it.hasNext()) {
                                                String str4 = (String) it.next();
                                                AppOpsManager.AttributedOpEntry attributedOpEntry2 = (AppOpsManager.AttributedOpEntry) opEntry.getAttributedOpEntries().get(str4);
                                                ArraySet collectKeys = attributedOpEntry2.collectKeys();
                                                int size = collectKeys.size();
                                                int i4 = 0;
                                                while (i4 < size) {
                                                    String str5 = str3;
                                                    List list = ops;
                                                    long longValue = ((Long) collectKeys.valueAt(i4)).longValue();
                                                    List list2 = packagesForOpsForDevice;
                                                    int extractUidStateFromKey = AppOpsManager.extractUidStateFromKey(longValue);
                                                    int i5 = size;
                                                    int extractFlagsFromKey = AppOpsManager.extractFlagsFromKey(longValue);
                                                    AppOpsManager.OpEntry opEntry2 = opEntry;
                                                    Iterator it2 = it;
                                                    long lastAccessTime = attributedOpEntry2.getLastAccessTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                    fileOutputStream = startWrite;
                                                    atomicFile = atomicFile2;
                                                    try {
                                                        long lastRejectTime = attributedOpEntry2.getLastRejectTime(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                        int i6 = i2;
                                                        long lastDuration = attributedOpEntry2.getLastDuration(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                        AppOpsManager.OpEventProxyInfo lastProxyInfo = attributedOpEntry2.getLastProxyInfo(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                                                        if (lastAccessTime > 0 || lastRejectTime > 0 || lastDuration > 0 || lastProxyInfo != null) {
                                                            if (lastProxyInfo != null) {
                                                                try {
                                                                    packageName = lastProxyInfo.getPackageName();
                                                                    String attributionTag = lastProxyInfo.getAttributionTag();
                                                                    uid = lastProxyInfo.getUid();
                                                                    arraySet = collectKeys;
                                                                    attributedOpEntry = attributedOpEntry2;
                                                                    str = attributionTag;
                                                                } catch (IOException e2) {
                                                                    e = e2;
                                                                    startWrite = fileOutputStream;
                                                                    Slog.w("AppOps", "Failed to write state, restoring backup.", e);
                                                                    appOpsService.mRecentAccessesFile.failWrite(startWrite);
                                                                    appOpsService.mHistoricalRegistry.mDiscreteRegistry.writeAndClearAccessHistory();
                                                                }
                                                            } else {
                                                                uid = -1;
                                                                attributedOpEntry = attributedOpEntry2;
                                                                arraySet = collectKeys;
                                                                packageName = null;
                                                                str = null;
                                                            }
                                                            i = i3;
                                                            resolveSerializer.startTag((String) null, KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
                                                            if (str4 != null) {
                                                                resolveSerializer.attribute((String) null, "id", str4);
                                                            }
                                                            int i7 = uid;
                                                            resolveSerializer.attributeLong((String) null, "n", longValue);
                                                            if (lastAccessTime > 0) {
                                                                resolveSerializer.attributeLong((String) null, KnoxAnalyticsDataConverter.TIMESTAMP, lastAccessTime);
                                                            }
                                                            if (lastRejectTime > 0) {
                                                                resolveSerializer.attributeLong((String) null, "r", lastRejectTime);
                                                            }
                                                            if (lastDuration > 0) {
                                                                resolveSerializer.attributeLong((String) null, "d", lastDuration);
                                                            }
                                                            if (packageName != null) {
                                                                resolveSerializer.attribute((String) null, "pp", packageName);
                                                            }
                                                            if (str != null) {
                                                                resolveSerializer.attribute((String) null, "pc", str);
                                                            }
                                                            if (i7 >= 0) {
                                                                resolveSerializer.attributeInt((String) null, "pu", i7);
                                                            }
                                                            resolveSerializer.endTag((String) null, KnoxAnalyticsSystemService.STATUS_PARAMETER_NAME);
                                                        } else {
                                                            i = i3;
                                                            attributedOpEntry = attributedOpEntry2;
                                                            arraySet = collectKeys;
                                                        }
                                                        i4++;
                                                        appOpsService = this;
                                                        packagesForOpsForDevice = list2;
                                                        str3 = str5;
                                                        ops = list;
                                                        size = i5;
                                                        opEntry = opEntry2;
                                                        it = it2;
                                                        atomicFile2 = atomicFile;
                                                        startWrite = fileOutputStream;
                                                        i2 = i6;
                                                        attributedOpEntry2 = attributedOpEntry;
                                                        collectKeys = arraySet;
                                                        i3 = i;
                                                    } catch (IOException e3) {
                                                        e = e3;
                                                        appOpsService = this;
                                                        startWrite = fileOutputStream;
                                                        Slog.w("AppOps", "Failed to write state, restoring backup.", e);
                                                        appOpsService.mRecentAccessesFile.failWrite(startWrite);
                                                        appOpsService.mHistoricalRegistry.mDiscreteRegistry.writeAndClearAccessHistory();
                                                    }
                                                }
                                                appOpsService = this;
                                            }
                                            resolveSerializer.endTag((String) null, "op");
                                            i3++;
                                            appOpsService = this;
                                            packagesForOpsForDevice = packagesForOpsForDevice;
                                            str3 = str3;
                                            ops = ops;
                                            atomicFile2 = atomicFile2;
                                            startWrite = startWrite;
                                            i2 = i2;
                                            str2 = null;
                                        }
                                        resolveSerializer.endTag((String) null, "uid");
                                        i2++;
                                        appOpsService = this;
                                        packagesForOpsForDevice = packagesForOpsForDevice;
                                        str3 = str3;
                                        atomicFile2 = atomicFile2;
                                        startWrite = startWrite;
                                        str2 = null;
                                    } catch (IOException e4) {
                                        e = e4;
                                        atomicFile = atomicFile2;
                                        appOpsService = this;
                                        Slog.w("AppOps", "Failed to write state, restoring backup.", e);
                                        appOpsService.mRecentAccessesFile.failWrite(startWrite);
                                        appOpsService.mHistoricalRegistry.mDiscreteRegistry.writeAndClearAccessHistory();
                                    }
                                }
                                fileOutputStream = startWrite;
                                atomicFile = atomicFile2;
                                if (str3 != null) {
                                    resolveSerializer.endTag((String) null, "pkg");
                                }
                            } else {
                                fileOutputStream = startWrite;
                                atomicFile = atomicFile2;
                            }
                            resolveSerializer.endTag((String) null, "app-ops");
                            resolveSerializer.endDocument();
                            appOpsService = this;
                            startWrite = fileOutputStream;
                        } catch (IOException e5) {
                            e = e5;
                            atomicFile = atomicFile2;
                        }
                        try {
                            appOpsService.mRecentAccessesFile.finishWrite(startWrite);
                        } catch (IOException e6) {
                            e = e6;
                            Slog.w("AppOps", "Failed to write state, restoring backup.", e);
                            appOpsService.mRecentAccessesFile.failWrite(startWrite);
                            appOpsService.mHistoricalRegistry.mDiscreteRegistry.writeAndClearAccessHistory();
                        }
                        appOpsService.mHistoricalRegistry.mDiscreteRegistry.writeAndClearAccessHistory();
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    AtomicFile atomicFile3 = atomicFile2;
                    throw th;
                }
            } catch (IOException e7) {
                Slog.w("AppOps", "Failed to write state: " + e7);
            }
        }
    }
}
