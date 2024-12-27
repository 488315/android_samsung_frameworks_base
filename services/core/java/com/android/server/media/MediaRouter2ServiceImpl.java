package com.android.server.media;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.media.IMediaRouter2;
import android.media.IMediaRouter2Manager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Utils;
import android.media.RouteDiscoveryPreference;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.media.flags.Flags;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MediaRouter2ServiceImpl {
    public final ActivityManager mActivityManager;
    public final AppOpsManager mAppOpsManager;
    public final Context mContext;
    public final Looper mLooper;
    public final AnonymousClass2 mOnOpChangedListener;
    public final MediaRouter2ServiceImpl$$ExternalSyntheticLambda9 mOnUidImportanceListener;
    public final PowerManager mPowerManager;
    public final AnonymousClass1 mScreenOnOffReceiver;
    public final StatusBarManagerInternal mStatusBarManagerInternal;
    public final UserManagerInternal mUserManagerInternal;
    public static final boolean DEBUG = Log.isLoggable("MR2ServiceImpl", 3);
    public static final String[] BLUETOOTH_PERMISSIONS_FOR_SYSTEM_ROUTING = {
        "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_SCAN"
    };
    public final Object mLock = new Object();
    public final AtomicInteger mNextRouterOrManagerId = new AtomicInteger(1);
    public final SparseArray mUserRecords = new SparseArray();
    public final ArrayMap mAllRouterRecords = new ArrayMap();
    public final ArrayMap mAllManagerRecords = new ArrayMap();
    public int mCurrentActiveUserId = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ManagerRecord implements IBinder.DeathRecipient {
        public final boolean mHasMediaContentControl;
        public final boolean mHasMediaRoutingControl;
        public SessionCreationRequest mLastSessionCreationRequest;
        public final IMediaRouter2Manager mManager;
        public final int mManagerId;
        public final String mOwnerPackageName;
        public final int mOwnerPid;
        public final int mOwnerUid;
        public int mScanningState = 0;
        public final String mTargetPackageName;
        public final UserRecord mUserRecord;

        public ManagerRecord(
                UserRecord userRecord,
                IMediaRouter2Manager iMediaRouter2Manager,
                int i,
                int i2,
                String str,
                String str2,
                boolean z,
                boolean z2) {
            this.mUserRecord = userRecord;
            this.mManager = iMediaRouter2Manager;
            this.mOwnerUid = i;
            this.mOwnerPid = i2;
            this.mOwnerPackageName = str;
            this.mTargetPackageName = str2;
            this.mManagerId = MediaRouter2ServiceImpl.this.mNextRouterOrManagerId.getAndIncrement();
            this.mHasMediaRoutingControl = z;
            this.mHasMediaContentControl = z2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl = MediaRouter2ServiceImpl.this;
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.unregisterManagerLocked(this.mManager, true);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Manager ");
            sb.append(this.mOwnerPackageName);
            sb.append(" (pid ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mOwnerPid, sb, ")");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RouterRecord implements IBinder.DeathRecipient {
        public final AtomicBoolean mHasBluetoothRoutingPermission;
        public final boolean mHasConfigureWifiDisplayPermission;
        public final boolean mHasMediaContentControlPermission;
        public final boolean mHasMediaRoutingControl;
        public final boolean mHasModifyAudioRoutingPermission;
        public final String mPackageName;
        public final int mPid;
        public RouteListingPreference mRouteListingPreference;
        public final IMediaRouter2 mRouter;
        public final int mRouterId;
        public final int mUid;
        public final UserRecord mUserRecord;
        public int mScanningState = 0;
        public final List mSelectRouteSequenceNumbers = new ArrayList();
        public RouteDiscoveryPreference mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;

        public RouterRecord(
                UserRecord userRecord,
                IMediaRouter2 iMediaRouter2,
                int i,
                int i2,
                String str,
                boolean z,
                boolean z2,
                boolean z3,
                boolean z4) {
            this.mUserRecord = userRecord;
            this.mPackageName = str;
            this.mRouter = iMediaRouter2;
            this.mUid = i;
            this.mPid = i2;
            this.mHasConfigureWifiDisplayPermission = z;
            this.mHasModifyAudioRoutingPermission = z2;
            this.mHasMediaContentControlPermission = z3;
            this.mHasMediaRoutingControl = z4;
            this.mHasBluetoothRoutingPermission =
                    new AtomicBoolean(
                            MediaRouter2ServiceImpl.this.checkCallerHasBluetoothPermissions(i2, i));
            this.mRouterId = MediaRouter2ServiceImpl.this.mNextRouterOrManagerId.getAndIncrement();
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl = MediaRouter2ServiceImpl.this;
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.unregisterRouter2Locked(this.mRouter, true);
            }
        }

        public final List getVisibleRoutes(List list) {
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) it.next();
                if (mediaRoute2Info.isVisibleTo(this.mPackageName)) {
                    arrayList.add(mediaRoute2Info);
                }
            }
            return arrayList;
        }

        public final boolean hasSystemRoutingPermission() {
            return this.mHasModifyAudioRoutingPermission
                    || this.mHasBluetoothRoutingPermission.get();
        }

        public final RoutingSessionInfo maybeClearTransferInitiatorIdentity(
                RoutingSessionInfo routingSessionInfo) {
            return (Objects.equals(
                                    UserHandle.of(this.mUserRecord.mUserId),
                                    routingSessionInfo.getTransferInitiatorUserHandle())
                            && Objects.equals(
                                    this.mPackageName,
                                    routingSessionInfo.getTransferInitiatorPackageName()))
                    ? routingSessionInfo
                    : new RoutingSessionInfo.Builder(routingSessionInfo)
                            .setTransferInitiator(null, null)
                            .build();
        }

        public final void maybeUpdateSystemRoutingPermissionLocked() {
            RoutingSessionInfo routingSessionInfo;
            boolean hasSystemRoutingPermission = hasSystemRoutingPermission();
            this.mHasBluetoothRoutingPermission.set(
                    MediaRouter2ServiceImpl.this.checkCallerHasBluetoothPermissions(
                            this.mPid, this.mUid));
            boolean hasSystemRoutingPermission2 = hasSystemRoutingPermission();
            if (hasSystemRoutingPermission == hasSystemRoutingPermission2) {
                return;
            }
            try {
                this.mRouter.notifyRoutesUpdated(
                        getVisibleRoutes(
                                ((ArrayMap)
                                                (hasSystemRoutingPermission2
                                                        ? this.mUserRecord
                                                                .mHandler
                                                                .mLastNotifiedRoutesToPrivilegedRouters
                                                        : this.mUserRecord
                                                                .mHandler
                                                                .mLastNotifiedRoutesToNonPrivilegedRouters))
                                        .values().stream().toList()));
            } catch (RemoteException e) {
                Slog.w(
                        "MR2ServiceImpl",
                        "Failed to notify routes updated. Router probably died.",
                        e);
            }
            List sessionInfos = this.mUserRecord.mHandler.mSystemProvider.getSessionInfos();
            try {
                if (hasSystemRoutingPermission2) {
                    ArrayList arrayList = (ArrayList) sessionInfos;
                    if (!arrayList.isEmpty()) {
                        routingSessionInfo = (RoutingSessionInfo) arrayList.get(0);
                        this.mRouter.notifySessionInfoChanged(
                                maybeClearTransferInitiatorIdentity(routingSessionInfo));
                        return;
                    }
                }
                this.mRouter.notifySessionInfoChanged(
                        maybeClearTransferInitiatorIdentity(routingSessionInfo));
                return;
            } catch (RemoteException e2) {
                Slog.w(
                        "MR2ServiceImpl",
                        "Failed to notify session info changed. Router probably died.",
                        e2);
                return;
            }
            routingSessionInfo = this.mUserRecord.mHandler.mSystemProvider.mDefaultSessionInfo;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionCreationRequest {
        public final long mManagerRequestId;
        public final RoutingSessionInfo mOldSession;
        public final MediaRoute2Info mRoute;
        public final RouterRecord mRouterRecord;
        public final long mUniqueRequestId;

        public SessionCreationRequest(
                RouterRecord routerRecord,
                long j,
                long j2,
                RoutingSessionInfo routingSessionInfo,
                MediaRoute2Info mediaRoute2Info) {
            this.mRouterRecord = routerRecord;
            this.mUniqueRequestId = j;
            this.mManagerRequestId = j2;
            this.mOldSession = routingSessionInfo;
            this.mRoute = mediaRoute2Info;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserHandler extends Handler
            implements MediaRoute2ProviderWatcher.Callback, MediaRoute2Provider.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Map mLastNotifiedRoutesToNonPrivilegedRouters;
        public final Map mLastNotifiedRoutesToPrivilegedRouters;
        public final List mLastProviderInfos;
        public final ArrayList mRouteProviders;
        public boolean mRunning;
        public final WeakReference mServiceRef;
        public final CopyOnWriteArrayList mSessionCreationRequests;
        public final Map mSessionToRouterMap;
        public final SystemMediaRoute2Provider mSystemProvider;
        public final UserRecord mUserRecord;
        public final MediaRoute2ProviderWatcher mWatcher;

        public UserHandler(
                MediaRouter2ServiceImpl mediaRouter2ServiceImpl,
                UserRecord userRecord,
                Looper looper) {
            super(looper, null, true);
            ArrayList arrayList = new ArrayList();
            this.mRouteProviders = arrayList;
            this.mLastProviderInfos = new ArrayList();
            this.mSessionCreationRequests = new CopyOnWriteArrayList();
            this.mSessionToRouterMap = new ArrayMap();
            this.mLastNotifiedRoutesToPrivilegedRouters = new ArrayMap();
            this.mLastNotifiedRoutesToNonPrivilegedRouters = new ArrayMap();
            this.mServiceRef = new WeakReference(mediaRouter2ServiceImpl);
            this.mUserRecord = userRecord;
            SystemMediaRoute2Provider systemMediaRoute2Provider =
                    new SystemMediaRoute2Provider(
                            mediaRouter2ServiceImpl.mContext,
                            UserHandle.of(userRecord.mUserId),
                            looper);
            this.mSystemProvider = systemMediaRoute2Provider;
            arrayList.add(systemMediaRoute2Provider);
            this.mWatcher =
                    new MediaRoute2ProviderWatcher(
                            mediaRouter2ServiceImpl.mContext, this, this, userRecord.mUserId);
        }

        public static void notifyRequestFailedToManager(
                IMediaRouter2Manager iMediaRouter2Manager, int i, int i2) {
            try {
                iMediaRouter2Manager.notifyRequestFailed(i, i2);
            } catch (RemoteException e) {
                Slog.w(
                        "MR2ServiceImpl",
                        "Failed to notify manager of the request failure. Manager probably died.",
                        e);
            }
        }

        public static void notifyRoutesUpdatedToRouterRecords(List list, List list2) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                RouterRecord routerRecord = (RouterRecord) it.next();
                routerRecord.getClass();
                try {
                    routerRecord.mRouter.notifyRoutesUpdated(routerRecord.getVisibleRoutes(list2));
                } catch (RemoteException e) {
                    Slog.w(
                            "MR2ServiceImpl",
                            "Failed to notify routes updated. Router probably died.",
                            e);
                }
            }
        }

        public static void notifySessionCreationFailedToRouter(RouterRecord routerRecord, int i) {
            try {
                routerRecord.mRouter.notifySessionCreated(i, (RoutingSessionInfo) null);
            } catch (RemoteException e) {
                Slog.w(
                        "MR2ServiceImpl",
                        "Failed to notify router of the session creation failure. Router probably"
                            + " died.",
                        e);
            }
        }

        public static void notifySessionInfoChangedToRouters(
                List list, RoutingSessionInfo routingSessionInfo) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                RouterRecord routerRecord = (RouterRecord) it.next();
                routerRecord.getClass();
                try {
                    routerRecord.mRouter.notifySessionInfoChanged(
                            routerRecord.maybeClearTransferInitiatorIdentity(routingSessionInfo));
                } catch (RemoteException e) {
                    Slog.w(
                            "MR2ServiceImpl",
                            "Failed to notify session info changed. Router probably died.",
                            e);
                }
            }
        }

        public static String toLoggingMessage(String str, String str2, ArrayList arrayList) {
            return TextUtils.formatSimple(
                    "%s | provider: %s, routes: [%s]",
                    new Object[] {
                        str,
                        str2,
                        (String)
                                arrayList.stream()
                                        .map(
                                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda14(
                                                        1))
                                        .collect(Collectors.joining(", "))
                    });
        }

        public final boolean checkArgumentsForSessionControl(
                RouterRecord routerRecord,
                String str,
                MediaRoute2Info mediaRoute2Info,
                String str2) {
            if (findProvider(mediaRoute2Info.getProviderId()) == null) {
                Slog.w(
                        "MR2ServiceImpl",
                        "Ignoring "
                                + str2
                                + " route since no provider found for given route="
                                + mediaRoute2Info);
                return false;
            }
            if (TextUtils.equals(
                    MediaRouter2Utils.getProviderId(str), this.mSystemProvider.mUniqueId)) {
                return true;
            }
            RouterRecord routerRecord2 =
                    (RouterRecord) ((ArrayMap) this.mSessionToRouterMap).get(str);
            if (routerRecord2 == routerRecord) {
                if (MediaRouter2Utils.getOriginalId(str) != null) {
                    return true;
                }
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Failed to get original session id from unique session id."
                            + " uniqueSessionId=",
                        str,
                        "MR2ServiceImpl");
                return false;
            }
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Ignoring ",
                            str2,
                            " route from non-matching router. routerRecordPackageName=");
            m.append(routerRecord != null ? routerRecord.mPackageName : "<null router record>");
            m.append(" matchingRecordPackageName=");
            m.append(routerRecord2 != null ? routerRecord2.mPackageName : "<null router record>");
            m.append(" route=");
            m.append(mediaRoute2Info);
            Slog.w("MR2ServiceImpl", m.toString());
            return false;
        }

        public final ManagerRecord findManagerWithId(int i) {
            for (ManagerRecord managerRecord : getManagerRecords()) {
                if (managerRecord.mManagerId == i) {
                    return managerRecord;
                }
            }
            return null;
        }

        public final MediaRoute2Provider findProvider(String str) {
            Iterator it = this.mRouteProviders.iterator();
            while (it.hasNext()) {
                MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) it.next();
                if (TextUtils.equals(mediaRoute2Provider.mUniqueId, str)) {
                    return mediaRoute2Provider;
                }
            }
            return null;
        }

        public final List getManagerRecords() {
            ArrayList arrayList;
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                    (MediaRouter2ServiceImpl) this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return Collections.emptyList();
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                arrayList = new ArrayList(this.mUserRecord.mManagerRecords);
            }
            return arrayList;
        }

        public final List getManagers() {
            ArrayList arrayList = new ArrayList();
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                    (MediaRouter2ServiceImpl) this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return arrayList;
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    Iterator it = this.mUserRecord.mManagerRecords.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((ManagerRecord) it.next()).mManager);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public final List getRouterRecords(boolean z) {
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                    (MediaRouter2ServiceImpl) this.mServiceRef.get();
            ArrayList arrayList = new ArrayList();
            if (mediaRouter2ServiceImpl == null) {
                return arrayList;
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    Iterator it = this.mUserRecord.mRouterRecords.iterator();
                    while (it.hasNext()) {
                        RouterRecord routerRecord = (RouterRecord) it.next();
                        if (z == routerRecord.hasSystemRoutingPermission()) {
                            arrayList.add(routerRecord);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public final void maybeUpdateDiscoveryPreferenceForUid(int i) {
            boolean anyMatch;
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                    (MediaRouter2ServiceImpl) this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return;
            }
            synchronized (mediaRouter2ServiceImpl.mLock) {
                anyMatch =
                        this.mUserRecord.mManagerRecords.stream()
                                        .anyMatch(
                                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda20(
                                                        i, 2))
                                | this.mUserRecord.mRouterRecords.stream()
                                        .anyMatch(
                                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda20(
                                                        i, 1));
            }
            if (anyMatch) {
                sendMessage(
                        PooledLambda.obtainMessage(
                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(3), this));
            }
        }

        public final void onRequestFailed(MediaRoute2Provider mediaRoute2Provider, long j, int i) {
            sendMessage(
                    PooledLambda.obtainMessage(
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(4),
                            this,
                            mediaRoute2Provider,
                            Long.valueOf(j),
                            Integer.valueOf(i)));
        }

        public final void onSessionCreated(
                MediaRoute2Provider mediaRoute2Provider,
                long j,
                RoutingSessionInfo routingSessionInfo) {
            sendMessage(
                    PooledLambda.obtainMessage(
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(3),
                            this,
                            mediaRoute2Provider,
                            Long.valueOf(j),
                            routingSessionInfo));
        }

        public final void updateDiscoveryPreferenceOnHandler() {
            ArrayList arrayList;
            List<RouterRecord> list;
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                    (MediaRouter2ServiceImpl) this.mServiceRef.get();
            if (mediaRouter2ServiceImpl == null) {
                return;
            }
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl2 =
                    (MediaRouter2ServiceImpl) this.mServiceRef.get();
            if (mediaRouter2ServiceImpl2 == null) {
                list = Collections.emptyList();
            } else {
                synchronized (mediaRouter2ServiceImpl2.mLock) {
                    arrayList = new ArrayList(this.mUserRecord.mRouterRecords);
                }
                list = arrayList;
            }
            boolean anyMatch =
                    (mediaRouter2ServiceImpl.mPowerManager.isInteractive()
                                    || Flags.enableScreenOffScanning())
                            ? getManagerRecords().stream()
                                    .anyMatch(
                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda22(
                                                    1, mediaRouter2ServiceImpl))
                            : false;
            if (!anyMatch) {
                list =
                        (mediaRouter2ServiceImpl.mPowerManager.isInteractive()
                                        || Flags.enableScreenOffScanning())
                                ? (List)
                                        list.stream()
                                                .filter(
                                                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda22(
                                                                2, mediaRouter2ServiceImpl))
                                                .collect(Collectors.toList())
                                : Collections.emptyList();
            }
            Iterator it = this.mRouteProviders.iterator();
            while (it.hasNext()) {
                MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) it.next();
                if (mediaRoute2Provider instanceof MediaRoute2ProviderServiceProxy) {
                    MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy =
                            (MediaRoute2ProviderServiceProxy) mediaRoute2Provider;
                    if (mediaRoute2ProviderServiceProxy.mIsManagerScanning != anyMatch) {
                        mediaRoute2ProviderServiceProxy.mIsManagerScanning = anyMatch;
                        mediaRoute2ProviderServiceProxy.updateBinding();
                    }
                }
            }
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            boolean z = false;
            for (RouterRecord routerRecord : list) {
                RouteDiscoveryPreference routeDiscoveryPreference =
                        routerRecord.mDiscoveryPreference;
                hashSet2.addAll(routeDiscoveryPreference.getPreferredFeatures());
                if (Flags.enablePreventionOfManagerScansWhenNoAppsScan()) {
                    int i = routerRecord.mScanningState;
                    if (i == 1
                            || i == 2
                            || routerRecord.mDiscoveryPreference.shouldPerformActiveScan()
                            || anyMatch) {
                        if (!routeDiscoveryPreference.getPreferredFeatures().isEmpty()) {
                            hashSet.add(routerRecord.mPackageName);
                            z = true;
                        }
                    }
                } else {
                    int i2 = routerRecord.mScanningState;
                    if (i2 != 1
                            && i2 != 2
                            && !routerRecord.mDiscoveryPreference.shouldPerformActiveScan()) {}
                    hashSet.add(routerRecord.mPackageName);
                    z = true;
                }
            }
            RouteDiscoveryPreference build =
                    new RouteDiscoveryPreference.Builder(List.copyOf(hashSet2), z || anyMatch)
                            .build();
            Slog.i(
                    "MR2ServiceImpl",
                    TextUtils.formatSimple(
                            "Updating composite discovery preference | preference: %s, active"
                                + " routers: %s",
                            new Object[] {build, hashSet}));
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    if (build.equals(this.mUserRecord.mCompositeDiscoveryPreference)
                            && hashSet.equals(this.mUserRecord.mActivelyScanningPackages)) {
                        return;
                    }
                    UserRecord userRecord = this.mUserRecord;
                    userRecord.mCompositeDiscoveryPreference = build;
                    userRecord.mActivelyScanningPackages = hashSet;
                    Iterator it2 = this.mRouteProviders.iterator();
                    while (it2.hasNext()) {
                        ((MediaRoute2Provider) it2.next())
                                .updateDiscoveryPreference(
                                        hashSet, this.mUserRecord.mCompositeDiscoveryPreference);
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserRecord {
        public final UserHandler mHandler;
        public final int mUserId;
        public final ArrayList mRouterRecords = new ArrayList();
        public final ArrayList mManagerRecords = new ArrayList();
        public RouteDiscoveryPreference mCompositeDiscoveryPreference =
                RouteDiscoveryPreference.EMPTY;
        public Set mActivelyScanningPackages = Set.of();

        public UserRecord(MediaRouter2ServiceImpl mediaRouter2ServiceImpl, int i, Looper looper) {
            this.mUserId = i;
            this.mHandler = new UserHandler(mediaRouter2ServiceImpl, this, looper);
        }

        public final void dump(final PrintWriter printWriter, String str) {
            printWriter.println(str + "UserRecord");
            final String str2 = str + "  ";
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str2, "mUserId=");
            m.append(this.mUserId);
            printWriter.println(m.toString());
            printWriter.println(str2 + "Router Records:");
            if (this.mRouterRecords.isEmpty()) {
                printWriter.println(str2 + "<no router records>");
            } else {
                Iterator it = this.mRouterRecords.iterator();
                while (it.hasNext()) {
                    RouterRecord routerRecord = (RouterRecord) it.next();
                    String str3 = str2 + "  ";
                    routerRecord.getClass();
                    printWriter.println(str3 + "RouterRecord");
                    String str4 = str3 + "  ";
                    StringBuilder m2 =
                            Preconditions$$ExternalSyntheticOutline0.m(str4, "mPackageName=");
                    m2.append(routerRecord.mPackageName);
                    printWriter.println(m2.toString());
                    printWriter.println(
                            str4
                                    + "mSelectRouteSequenceNumbers="
                                    + routerRecord.mSelectRouteSequenceNumbers);
                    StringBuilder sb = new StringBuilder();
                    sb.append(str4);
                    sb.append("mUid=");
                    StringBuilder m3 =
                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                    .m(
                                                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                                    .m(
                                                                            sb,
                                                                            routerRecord.mUid,
                                                                            printWriter,
                                                                            str4,
                                                                            "mPid="),
                                                            routerRecord.mPid,
                                                            printWriter,
                                                            str4,
                                                            "mHasConfigureWifiDisplayPermission="),
                                            routerRecord.mHasConfigureWifiDisplayPermission,
                                            printWriter,
                                            str4,
                                            "mHasModifyAudioRoutingPermission="),
                                    routerRecord.mHasModifyAudioRoutingPermission,
                                    printWriter,
                                    str4,
                                    "mHasBluetoothRoutingPermission=");
                    m3.append(routerRecord.mHasBluetoothRoutingPermission.get());
                    printWriter.println(m3.toString());
                    printWriter.println(
                            str4
                                    + "hasSystemRoutingPermission="
                                    + routerRecord.hasSystemRoutingPermission());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str4);
                    sb2.append("mRouterId=");
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            sb2, routerRecord.mRouterId, printWriter);
                    routerRecord.mDiscoveryPreference.dump(printWriter, str4);
                }
            }
            printWriter.println(str2 + "Manager Records:");
            if (this.mManagerRecords.isEmpty()) {
                printWriter.println(str2 + "<no manager records>");
            } else {
                Iterator it2 = this.mManagerRecords.iterator();
                while (it2.hasNext()) {
                    ManagerRecord managerRecord = (ManagerRecord) it2.next();
                    String str5 = str2 + "  ";
                    managerRecord.getClass();
                    printWriter.println(str5 + "ManagerRecord");
                    String str6 = str5 + "  ";
                    StringBuilder m4 =
                            Preconditions$$ExternalSyntheticOutline0.m(str6, "mOwnerPackageName=");
                    m4.append(managerRecord.mOwnerPackageName);
                    printWriter.println(m4.toString());
                    printWriter.println(
                            str6 + "mTargetPackageName=" + managerRecord.mTargetPackageName);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str6);
                    sb3.append("mManagerId=");
                    StringBuilder m5 =
                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                    .m(
                                                            sb3,
                                                            managerRecord.mManagerId,
                                                            printWriter,
                                                            str6,
                                                            "mOwnerUid="),
                                            managerRecord.mOwnerUid,
                                            printWriter,
                                            str6,
                                            "mOwnerPid="),
                                    managerRecord.mOwnerPid,
                                    printWriter,
                                    str6,
                                    "mScanningState=");
                    m5.append(
                            MediaRouter2ServiceImpl.getScanningStateString(
                                    managerRecord.mScanningState));
                    printWriter.println(m5.toString());
                    SessionCreationRequest sessionCreationRequest =
                            managerRecord.mLastSessionCreationRequest;
                    if (sessionCreationRequest != null) {
                        printWriter.println(str6 + "SessionCreationRequest");
                        String str7 = str6 + "  ";
                        StringBuilder m6 =
                                Preconditions$$ExternalSyntheticOutline0.m(
                                        str7, "mUniqueRequestId=");
                        m6.append(sessionCreationRequest.mUniqueRequestId);
                        printWriter.println(m6.toString());
                        printWriter.println(
                                str7
                                        + "mManagerRequestId="
                                        + sessionCreationRequest.mManagerRequestId);
                        sessionCreationRequest.mOldSession.dump(printWriter, str7);
                        sessionCreationRequest.mRoute.dump(printWriter, str6);
                    }
                }
            }
            printWriter.println(str2 + "Composite discovery preference:");
            this.mCompositeDiscoveryPreference.dump(printWriter, str2 + "  ");
            printWriter.println(
                    str2
                            + "Packages actively scanning: "
                            + String.join(", ", this.mActivelyScanningPackages));
            if (this.mHandler.runWithScissors(
                    new Runnable() { // from class:
                                     // com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2ServiceImpl.UserRecord userRecord =
                                    MediaRouter2ServiceImpl.UserRecord.this;
                            PrintWriter printWriter2 = printWriter;
                            String str8 = str2;
                            MediaRouter2ServiceImpl.UserHandler userHandler = userRecord.mHandler;
                            userHandler.getClass();
                            printWriter2.println(str8 + "UserHandler");
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    Preconditions$$ExternalSyntheticOutline0.m(
                                            str8 + "  ", "mRunning="),
                                    userHandler.mRunning,
                                    printWriter2);
                            userHandler.mSystemProvider.dump(printWriter2, str8);
                            MediaRoute2ProviderWatcher mediaRoute2ProviderWatcher =
                                    userHandler.mWatcher;
                            mediaRoute2ProviderWatcher.getClass();
                            printWriter2.println(str8 + "MediaRoute2ProviderWatcher");
                            String str9 = str8 + "  ";
                            if (mediaRoute2ProviderWatcher.mProxies.isEmpty()) {
                                printWriter2.println(str9 + "<no provider service proxies>");
                                return;
                            }
                            Iterator it3 = mediaRoute2ProviderWatcher.mProxies.iterator();
                            while (it3.hasNext()) {
                                ((MediaRoute2ProviderServiceProxy) it3.next())
                                        .dump(printWriter2, str9);
                            }
                        }
                    },
                    1000L)) {
                return;
            }
            printWriter.println(str2 + "<could not dump handler state>");
        }
    }

    public static void $r8$lambda$QUOfWBbs9bzeEoCNqoDrNBPHovk(UserHandler userHandler) {
        if (userHandler.mRunning) {
            userHandler.mRunning = false;
            MediaRoute2ProviderWatcher mediaRoute2ProviderWatcher = userHandler.mWatcher;
            if (mediaRoute2ProviderWatcher.mRunning) {
                mediaRoute2ProviderWatcher.mRunning = false;
                mediaRoute2ProviderWatcher.mContext.unregisterReceiver(
                        mediaRoute2ProviderWatcher.mScanPackagesReceiver);
                mediaRoute2ProviderWatcher.mHandler.removeCallbacks(
                        new MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(
                                mediaRoute2ProviderWatcher));
                for (int size = mediaRoute2ProviderWatcher.mProxies.size() - 1; size >= 0; size--) {
                    ((MediaRoute2ProviderServiceProxy)
                                    mediaRoute2ProviderWatcher.mProxies.get(size))
                            .stop();
                }
            }
            SystemMediaRoute2Provider systemMediaRoute2Provider = userHandler.mSystemProvider;
            systemMediaRoute2Provider.mContext.unregisterReceiver(
                    systemMediaRoute2Provider.mAudioReceiver);
            systemMediaRoute2Provider.mHandler.post(
                    new SystemMediaRoute2Provider$$ExternalSyntheticLambda1(
                            systemMediaRoute2Provider, 0));
        }
    }

    public static void $r8$lambda$VwOkF_wuq7S_wu2mL143qz8S5B4(
            UserHandler userHandler,
            long j,
            UserHandle userHandle,
            String str,
            RouterRecord routerRecord,
            String str2,
            MediaRoute2Info mediaRoute2Info,
            int i) {
        int i2 = UserHandler.$r8$clinit;
        if (userHandler.checkArgumentsForSessionControl(
                routerRecord, str2, mediaRoute2Info, "transferring to")) {
            MediaRoute2Provider findProvider =
                    userHandler.findProvider(mediaRoute2Info.getProviderId());
            if (findProvider != null) {
                findProvider.transferToRoute(
                        j,
                        userHandle,
                        str,
                        MediaRouter2Utils.getOriginalId(str2),
                        mediaRoute2Info.getOriginalId(),
                        i);
                return;
            }
            Slog.w(
                    "MR2ServiceImpl",
                    "Ignoring transferToRoute due to lack of matching provider for target: "
                            + mediaRoute2Info);
        }
    }

    /* renamed from: $r8$lambda$_KlJMldyh-EHjCtdEMZwMuezeUQ, reason: not valid java name */
    public static void m652$r8$lambda$_KlJMldyhEHjCtdEMZwMuezeUQ(UserHandler userHandler) {
        if (userHandler.mRunning) {
            return;
        }
        userHandler.mRunning = true;
        SystemMediaRoute2Provider systemMediaRoute2Provider = userHandler.mSystemProvider;
        systemMediaRoute2Provider.getClass();
        IntentFilter intentFilter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        intentFilter.addAction(
                "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
        systemMediaRoute2Provider.mContext.registerReceiverAsUser(
                systemMediaRoute2Provider.mAudioReceiver,
                systemMediaRoute2Provider.mUser,
                intentFilter,
                null,
                null);
        systemMediaRoute2Provider.mHandler.post(
                new SystemMediaRoute2Provider$$ExternalSyntheticLambda1(
                        systemMediaRoute2Provider, 1));
        systemMediaRoute2Provider.updateVolume();
        MediaRoute2ProviderWatcher mediaRoute2ProviderWatcher = userHandler.mWatcher;
        if (mediaRoute2ProviderWatcher.mRunning) {
            return;
        }
        mediaRoute2ProviderWatcher.mRunning = true;
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REPLACED");
        if (!Flags.enablePreventionOfKeepAliveRouteProviders()) {
            intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
        }
        intentFilter2.addDataScheme("package");
        mediaRoute2ProviderWatcher.mContext.registerReceiverAsUser(
                mediaRoute2ProviderWatcher.mScanPackagesReceiver,
                new UserHandle(mediaRoute2ProviderWatcher.mUserId),
                intentFilter2,
                null,
                mediaRoute2ProviderWatcher.mHandler);
        MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0
                mediaRoute2ProviderWatcher$$ExternalSyntheticLambda0 =
                        new MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(
                                mediaRoute2ProviderWatcher);
        Handler handler = mediaRoute2ProviderWatcher.mHandler;
        if (handler.hasCallbacks(mediaRoute2ProviderWatcher$$ExternalSyntheticLambda0)) {
            return;
        }
        handler.post(
                new MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(
                        mediaRoute2ProviderWatcher));
    }

    /* renamed from: $r8$lambda$e9Sk6TFecCbShGZjiI_FsGfvE-s, reason: not valid java name */
    public static void m653$r8$lambda$e9Sk6TFecCbShGZjiI_FsGfvEs(
            UserHandler userHandler, long j, MediaRoute2Info mediaRoute2Info, int i) {
        int i2 = UserHandler.$r8$clinit;
        userHandler.getClass();
        MediaRoute2Provider findProvider =
                userHandler.findProvider(mediaRoute2Info.getProviderId());
        if (findProvider != null) {
            findProvider.setRouteVolume(i, mediaRoute2Info.getOriginalId(), j);
            return;
        }
        Slog.w(
                "MR2ServiceImpl",
                "setRouteVolumeOnHandler: Couldn't find provider for route=" + mediaRoute2Info);
    }

    public static void $r8$lambda$fFTtstVLACy5dqiCA6mMwECkaiQ(
            UserHandler userHandler, long j, RouterRecord routerRecord, String str) {
        RouterRecord routerRecord2 =
                (RouterRecord) ((ArrayMap) userHandler.mSessionToRouterMap).get(str);
        if (routerRecord2 != routerRecord) {
            StringBuilder sb =
                    new StringBuilder(
                            "Ignoring releasing session from non-matching router."
                                + " routerRecordPackageName=");
            sb.append(routerRecord != null ? routerRecord.mPackageName : "<null router record>");
            sb.append(" matchingRecordPackageName=");
            sb.append(routerRecord2 != null ? routerRecord2.mPackageName : "<null router record>");
            sb.append(" uniqueSessionId=");
            sb.append(str);
            Slog.w("MR2ServiceImpl", sb.toString());
            return;
        }
        String providerId = MediaRouter2Utils.getProviderId(str);
        if (providerId == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Ignoring releasing session with invalid unique session ID. uniqueSessionId=",
                    str,
                    "MR2ServiceImpl");
            return;
        }
        String originalId = MediaRouter2Utils.getOriginalId(str);
        if (originalId == null) {
            Slog.w(
                    "MR2ServiceImpl",
                    "Ignoring releasing session with invalid unique session ID. uniqueSessionId="
                            + str
                            + " providerId="
                            + providerId);
            return;
        }
        MediaRoute2Provider findProvider = userHandler.findProvider(providerId);
        if (findProvider == null) {
            Slog.w(
                    "MR2ServiceImpl",
                    "Ignoring releasing session since no provider found for given providerId="
                            .concat(providerId));
        } else {
            findProvider.releaseSession(j, originalId);
        }
    }

    public static void $r8$lambda$gfBNjj_qEN5gYbuwjbOFgMrSMpU(
            UserHandler userHandler, long j, String str, int i) {
        int i2 = UserHandler.$r8$clinit;
        userHandler.getClass();
        MediaRoute2Provider findProvider =
                userHandler.findProvider(MediaRouter2Utils.getProviderId(str));
        if (findProvider == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "setSessionVolumeOnHandler: Couldn't find provider for session id=",
                    str,
                    "MR2ServiceImpl");
        } else {
            findProvider.setSessionVolume(i, MediaRouter2Utils.getOriginalId(str), j);
        }
    }

    public static void $r8$lambda$heOK_4zIzngS8WMr61qD_midgrQ(
            UserHandler userHandler, String str, RouteListingPreference routeListingPreference) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                (MediaRouter2ServiceImpl) userHandler.mServiceRef.get();
        if (mediaRouter2ServiceImpl == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (mediaRouter2ServiceImpl.mLock) {
            try {
                Iterator it = userHandler.mUserRecord.mManagerRecords.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ManagerRecord) it.next()).mManager);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                ((IMediaRouter2Manager) it2.next())
                        .notifyRouteListingPreferenceChange(str, routeListingPreference);
            } catch (RemoteException e) {
                Slog.w(
                        "MR2ServiceImpl",
                        "Failed to notify preferred features changed. Manager probably died.",
                        e);
            }
        }
    }

    /* renamed from: $r8$lambda$l439RW2gb6MOGyg-suamHEVj0Rk, reason: not valid java name */
    public static void m654$r8$lambda$l439RW2gb6MOGygsuamHEVj0Rk(
            UserHandler userHandler,
            String str,
            RouteDiscoveryPreference routeDiscoveryPreference) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                (MediaRouter2ServiceImpl) userHandler.mServiceRef.get();
        if (mediaRouter2ServiceImpl == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        synchronized (mediaRouter2ServiceImpl.mLock) {
            try {
                Iterator it = userHandler.mUserRecord.mManagerRecords.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ManagerRecord) it.next()).mManager);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            try {
                ((IMediaRouter2Manager) it2.next())
                        .notifyDiscoveryPreferenceChanged(str, routeDiscoveryPreference);
            } catch (RemoteException e) {
                Slog.w(
                        "MR2ServiceImpl",
                        "Failed to notify preferred features changed. Manager probably died.",
                        e);
            }
        }
    }

    /* renamed from: -$$Nest$mrevokeManagerRecordAccessIfNeededLocked, reason: not valid java name */
    public static void m655$$Nest$mrevokeManagerRecordAccessIfNeededLocked(
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl, String str, int i) {
        UserRecord userRecord = (UserRecord) mediaRouter2ServiceImpl.mUserRecords.get(i);
        if (userRecord == null) {
            return;
        }
        List<ManagerRecord> list =
                (List)
                        userRecord.mManagerRecords.stream()
                                .filter(new MediaRouter2ServiceImpl$$ExternalSyntheticLambda21())
                                .filter(
                                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda22(
                                                0, str))
                                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return;
        }
        ManagerRecord managerRecord = (ManagerRecord) list.getFirst();
        if (mediaRouter2ServiceImpl.mAppOpsManager.unsafeCheckOpNoThrow(
                        "android:media_routing_control",
                        managerRecord.mOwnerUid,
                        managerRecord.mOwnerPackageName)
                == 0) {
            return;
        }
        for (ManagerRecord managerRecord2 : list) {
            if (mediaRouter2ServiceImpl.mContext.checkPermission(
                            "android.permission.MEDIA_ROUTING_CONTROL",
                            managerRecord2.mOwnerPid,
                            managerRecord2.mOwnerUid)
                    != 0) {
                Log.w(
                        "MR2ServiceImpl",
                        TextUtils.formatSimple(
                                "Revoking access to manager record id: %d, package: %s, userId: %d",
                                new Object[] {
                                    Integer.valueOf(managerRecord2.mManagerId),
                                    managerRecord2.mOwnerPackageName,
                                    Integer.valueOf(userRecord.mUserId)
                                }));
                mediaRouter2ServiceImpl.unregisterManagerLocked(managerRecord2.mManager, false);
                try {
                    managerRecord2.mManager.invalidateInstance();
                } catch (RemoteException unused) {
                    Slog.w(
                            "MR2ServiceImpl",
                            "Failed to notify manager= "
                                    + managerRecord2
                                    + " of permission revocation.");
                }
            }
        }
    }

    public MediaRouter2ServiceImpl(Context context, Looper looper) {
        ActivityManager.OnUidImportanceListener onUidImportanceListener =
                new ActivityManager
                        .OnUidImportanceListener() { // from class:
                                                     // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda9
                    public final void onUidImportance(int i, int i2) {
                        MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                                MediaRouter2ServiceImpl.this;
                        synchronized (mediaRouter2ServiceImpl.mLock) {
                            try {
                                int size = mediaRouter2ServiceImpl.mUserRecords.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    ((MediaRouter2ServiceImpl.UserRecord)
                                                    mediaRouter2ServiceImpl.mUserRecords.valueAt(
                                                            i3))
                                            .mHandler.maybeUpdateDiscoveryPreferenceForUid(i);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.media.MediaRouter2ServiceImpl.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        synchronized (MediaRouter2ServiceImpl.this.mLock) {
                            try {
                                int size = MediaRouter2ServiceImpl.this.mUserRecords.size();
                                for (int i = 0; i < size; i++) {
                                    UserHandler userHandler =
                                            ((UserRecord)
                                                            MediaRouter2ServiceImpl.this
                                                                    .mUserRecords.valueAt(i))
                                                    .mHandler;
                                    userHandler.sendMessage(
                                            PooledLambda.obtainMessage(
                                                    new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(
                                                            0),
                                                    userHandler));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        AppOpsManager.OnOpChangedListener onOpChangedListener =
                new AppOpsManager
                        .OnOpChangedListener() { // from class:
                                                 // com.android.server.media.MediaRouter2ServiceImpl.2
                    @Override // android.app.AppOpsManager.OnOpChangedListener
                    public final void onOpChanged(String str, String str2) {}

                    public final void onOpChanged(String str, String str2, int i) {
                        if (TextUtils.equals(str, "android:media_routing_control")) {
                            synchronized (MediaRouter2ServiceImpl.this.mLock) {
                                MediaRouter2ServiceImpl
                                        .m655$$Nest$mrevokeManagerRecordAccessIfNeededLocked(
                                                MediaRouter2ServiceImpl.this, str2, i);
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mLooper = looper;
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(ActivityManager.class);
        this.mActivityManager = activityManager;
        activityManager.addOnUidImportanceListener(onUidImportanceListener, 100);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mUserManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        this.mAppOpsManager = appOpsManager;
        this.mStatusBarManagerInternal =
                (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(broadcastReceiver, intentFilter);
        appOpsManager.startWatchingMode(139, (String) null, onOpChangedListener);
        context.getPackageManager()
                .addOnPermissionsChangeListener(
                        new PackageManager
                                .OnPermissionsChangedListener() { // from class:
                                                                  // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda10
                            public final void onPermissionsChanged(int i) {
                                MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
                                        MediaRouter2ServiceImpl.this;
                                synchronized (mediaRouter2ServiceImpl.mLock) {
                                    try {
                                        Optional findFirst =
                                                mediaRouter2ServiceImpl
                                                        .mAllRouterRecords
                                                        .values()
                                                        .stream()
                                                        .filter(
                                                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda20(
                                                                        i, 0))
                                                        .findFirst();
                                        if (findFirst.isPresent()) {
                                            ((MediaRouter2ServiceImpl.RouterRecord) findFirst.get())
                                                    .maybeUpdateSystemRoutingPermissionLocked();
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        });
    }

    public static String getScanningStateString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        i, "Invalid scanning state: ")
                                : "FULL"
                        : "SCREEN_ON_ONLY"
                : "NOT_SCANNING";
    }

    public static void setDiscoveryRequestWithRouter2Locked(
            RouterRecord routerRecord, RouteDiscoveryPreference routeDiscoveryPreference) {
        if (routerRecord.mDiscoveryPreference.equals(routeDiscoveryPreference)) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "setDiscoveryRequestWithRouter2 | router: %s(id: %d), discovery request:"
                            + " %s",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            routeDiscoveryPreference.toString()
                        }));
        routerRecord.mDiscoveryPreference = routeDiscoveryPreference;
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(0),
                        userHandler,
                        routerRecord.mPackageName,
                        routeDiscoveryPreference));
        UserHandler userHandler2 = routerRecord.mUserRecord.mHandler;
        userHandler2.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(0), userHandler2));
    }

    public static void setRouteListingPreferenceLocked(
            RouterRecord routerRecord, RouteListingPreference routeListingPreference) {
        routerRecord.mRouteListingPreference = routeListingPreference;
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "setRouteListingPreference | router: %s(id: %d), route listing preference:"
                            + " [%s]",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            routeListingPreference != null
                                    ? (String)
                                            routeListingPreference.getItems().stream()
                                                    .map(
                                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda14(
                                                                    0))
                                                    .collect(Collectors.joining(","))
                                    : null
                        }));
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(3),
                        userHandler,
                        routerRecord.mPackageName,
                        routeListingPreference));
    }

    public static long toUniqueRequestId(int i, int i2) {
        return i2 | (i << 32);
    }

    public final boolean checkCallerHasBluetoothPermissions(int i, int i2) {
        boolean z = true;
        for (String str : BLUETOOTH_PERMISSIONS_FOR_SYSTEM_ROUTING) {
            z &= this.mContext.checkPermission(str, i, i2) == 0;
        }
        return z;
    }

    public final boolean checkMediaContentControlPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.MEDIA_CONTENT_CONTROL", i2, i)
                == 0;
    }

    public final boolean checkMediaRoutingControlPermission(int i, int i2, String str) {
        return Flags.enablePrivilegedRoutingForMediaRoutingControl()
                && PermissionChecker.checkPermissionForDataDelivery(
                                this.mContext,
                                "android.permission.MEDIA_ROUTING_CONTROL",
                                i2,
                                i,
                                str,
                                (String) null,
                                "Checking permissions for registering manager in"
                                    + " MediaRouter2ServiceImpl.")
                        == 0;
    }

    public final void deselectRouteWithManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "deselectRouteWithManager | manager: %d, session: %s, route: %s",
                        new Object[] {
                            Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()
                        }));
        RouterRecord routerRecord =
                (RouterRecord)
                        ((ArrayMap) managerRecord.mUserRecord.mHandler.mSessionToRouterMap)
                                .get(str);
        long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
        UserHandler userHandler = managerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(1),
                        userHandler,
                        Long.valueOf(uniqueRequestId),
                        routerRecord,
                        str,
                        mediaRoute2Info));
    }

    public final void deselectRouteWithRouter2Locked(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "deselectRouteWithRouter2 | router: %s(id: %d), route: %s",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            mediaRoute2Info.getId()
                        }));
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(1),
                        userHandler,
                        -1L,
                        routerRecord,
                        str,
                        mediaRoute2Info));
    }

    public final void disposeUserIfNeededLocked(UserRecord userRecord) {
        UserManagerInternal userManagerInternal = this.mUserManagerInternal;
        int i = userRecord.mUserId;
        if (userManagerInternal.getProfileParentId(i) != this.mCurrentActiveUserId
                && userRecord.mRouterRecords.isEmpty()
                && userRecord.mManagerRecords.isEmpty()) {
            if (DEBUG) {
                Slog.d("MR2ServiceImpl", userRecord + ": Disposed");
            }
            MediaRouter2ServiceImpl$$ExternalSyntheticLambda5
                    mediaRouter2ServiceImpl$$ExternalSyntheticLambda5 =
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(1);
            UserHandler userHandler = userRecord.mHandler;
            userHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            mediaRouter2ServiceImpl$$ExternalSyntheticLambda5, userHandler));
            this.mUserRecords.remove(i);
        }
    }

    public final UserRecord getOrCreateUserRecordLocked(int i) {
        UserRecord userRecord = (UserRecord) this.mUserRecords.get(i);
        if (userRecord == null) {
            userRecord = new UserRecord(this, i, this.mLooper);
            this.mUserRecords.put(i, userRecord);
            UserHandler userHandler = userRecord.mHandler;
            SystemMediaRoute2Provider systemMediaRoute2Provider = userHandler.mSystemProvider;
            systemMediaRoute2Provider.mCallback = userHandler;
            systemMediaRoute2Provider.notifyProviderState();
            systemMediaRoute2Provider.notifySessionInfoUpdated();
            if (this.mUserManagerInternal.getProfileParentId(i) == this.mCurrentActiveUserId) {
                userHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(2),
                                userHandler));
            }
        }
        return userRecord;
    }

    public final List getRemoteSessionsLocked(IMediaRouter2Manager iMediaRouter2Manager) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            Slog.w("MR2ServiceImpl", "getRemoteSessionLocked: Ignoring unknown manager");
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = managerRecord.mUserRecord.mHandler.mRouteProviders.iterator();
        while (it.hasNext()) {
            MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) it.next();
            if (!mediaRoute2Provider.mIsSystemRouteProvider) {
                arrayList.addAll(mediaRoute2Provider.getSessionInfos());
            }
        }
        return arrayList;
    }

    public final RoutingSessionInfo getSystemSessionInfo(String str, String str2, boolean z) {
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
        boolean z2 = true;
        if (str2 != null
                ? !(checkMediaContentControlPermission(callingUid, callingPid)
                        || checkMediaRoutingControlPermission(callingUid, callingPid, str))
                : !(this.mContext.checkPermission(
                                        "android.permission.MODIFY_AUDIO_ROUTING",
                                        callingPid,
                                        callingUid)
                                == 0
                        || checkCallerHasBluetoothPermissions(callingPid, callingUid))) {
            z2 = false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(identifier);
                if (!z2) {
                    return new RoutingSessionInfo.Builder(
                                    orCreateUserRecordLocked
                                            .mHandler
                                            .mSystemProvider
                                            .mDefaultSessionInfo)
                            .setClientPackageName(str2)
                            .build();
                }
                if (z) {
                    return orCreateUserRecordLocked.mHandler.mSystemProvider
                            .generateDeviceRouteSelectedSessionInfo(str2);
                }
                ArrayList arrayList =
                        (ArrayList)
                                orCreateUserRecordLocked.mHandler.mSystemProvider.getSessionInfos();
                if (!arrayList.isEmpty()) {
                    return new RoutingSessionInfo.Builder((RoutingSessionInfo) arrayList.get(0))
                            .setClientPackageName(str2)
                            .build();
                }
                Slog.w("MR2ServiceImpl", "System provider does not have any session info.");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            int i2,
            String str,
            String str2,
            UserHandle userHandle) {
        IBinder asBinder = iMediaRouter2Manager.asBinder();
        if (((ManagerRecord) this.mAllManagerRecords.get(asBinder)) != null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "registerManagerLocked: Same manager already exists. callerPackageName=",
                    str,
                    "MR2ServiceImpl");
            return;
        }
        boolean checkMediaRoutingControlPermission = checkMediaRoutingControlPermission(i, i2, str);
        boolean checkMediaContentControlPermission = checkMediaContentControlPermission(i, i2);
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "registerManager | callerUid: %d, callerPid: %d, callerPackage: %s,"
                            + " targetPackageName: %s, targetUserId: %d, hasMediaRoutingControl:"
                            + " %b",
                        new Object[] {
                            Integer.valueOf(i),
                            Integer.valueOf(i2),
                            str,
                            str2,
                            userHandle,
                            Boolean.valueOf(checkMediaRoutingControlPermission)
                        }));
        UserRecord orCreateUserRecordLocked =
                getOrCreateUserRecordLocked(userHandle.getIdentifier());
        ManagerRecord managerRecord =
                new ManagerRecord(
                        orCreateUserRecordLocked,
                        iMediaRouter2Manager,
                        i,
                        i2,
                        str,
                        str2,
                        checkMediaRoutingControlPermission,
                        checkMediaContentControlPermission);
        try {
            asBinder.linkToDeath(managerRecord, 0);
            orCreateUserRecordLocked.mManagerRecords.add(managerRecord);
            this.mAllManagerRecords.put(asBinder, managerRecord);
            Iterator it = orCreateUserRecordLocked.mRouterRecords.iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                UserHandler userHandler = orCreateUserRecordLocked.mHandler;
                if (!hasNext) {
                    userHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(1),
                                    userHandler,
                                    iMediaRouter2Manager));
                    return;
                }
                RouterRecord routerRecord = (RouterRecord) it.next();
                userHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(3),
                                routerRecord.mUserRecord.mHandler,
                                routerRecord.mPackageName,
                                routerRecord.mRouteListingPreference));
                UserHandler userHandler2 = routerRecord.mUserRecord.mHandler;
                userHandler2.sendMessage(
                        PooledLambda.obtainMessage(
                                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(2),
                                userHandler2,
                                routerRecord,
                                iMediaRouter2Manager));
            }
        } catch (RemoteException e) {
            throw new RuntimeException("Media router manager died prematurely.", e);
        }
    }

    public final void registerRouter2Locked(
            IMediaRouter2 iMediaRouter2,
            int i,
            int i2,
            String str,
            int i3,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4) {
        IBinder asBinder = iMediaRouter2.asBinder();
        if (this.mAllRouterRecords.get(asBinder) != null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "registerRouter2Locked: Same router already exists. packageName=",
                    str,
                    "MR2ServiceImpl");
            return;
        }
        UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(i3);
        RouterRecord routerRecord =
                new RouterRecord(
                        orCreateUserRecordLocked, iMediaRouter2, i, i2, str, z, z2, z3, z4);
        try {
            asBinder.linkToDeath(routerRecord, 0);
            orCreateUserRecordLocked.mRouterRecords.add(routerRecord);
            this.mAllRouterRecords.put(asBinder, routerRecord);
            MediaRouter2ServiceImpl$$ExternalSyntheticLambda7
                    mediaRouter2ServiceImpl$$ExternalSyntheticLambda7 =
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(0);
            UserHandler userHandler = orCreateUserRecordLocked.mHandler;
            userHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            mediaRouter2ServiceImpl$$ExternalSyntheticLambda7,
                            userHandler,
                            routerRecord));
            Slog.i(
                    "MR2ServiceImpl",
                    TextUtils.formatSimple(
                            "registerRouter2 | package: %s, uid: %d, pid: %d, router id: %d,"
                                + " hasMediaRoutingControl: %b",
                            new Object[] {
                                str,
                                Integer.valueOf(i),
                                Integer.valueOf(i2),
                                Integer.valueOf(routerRecord.mRouterId),
                                Boolean.valueOf(z4)
                            }));
        } catch (RemoteException e) {
            throw new RuntimeException("MediaRouter2 died prematurely.", e);
        }
    }

    public final void releaseSessionWithManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager, int i, String str) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "releaseSessionWithManager | manager: %d, session: %s",
                        new Object[] {Integer.valueOf(managerRecord.mManagerId), str}));
        RouterRecord routerRecord =
                (RouterRecord)
                        ((ArrayMap) managerRecord.mUserRecord.mHandler.mSessionToRouterMap)
                                .get(str);
        long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
        UserHandler userHandler = managerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(1),
                        userHandler,
                        Long.valueOf(uniqueRequestId),
                        routerRecord,
                        str));
    }

    public final void releaseSessionWithRouter2Locked(IMediaRouter2 iMediaRouter2, String str) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "releaseSessionWithRouter2 | router: %s(id: %d), session: %s",
                        new Object[] {
                            routerRecord.mPackageName, Integer.valueOf(routerRecord.mRouterId), str
                        }));
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(1),
                        userHandler,
                        -1L,
                        routerRecord,
                        str));
    }

    public final void requestCreateSessionWithManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            RoutingSessionInfo routingSessionInfo,
            MediaRoute2Info mediaRoute2Info,
            UserHandle userHandle,
            String str) {
        RouterRecord routerRecord;
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "requestCreateSessionWithManager | manager: %d, route: %s",
                        new Object[] {
                            Integer.valueOf(managerRecord.mManagerId), mediaRoute2Info.getId()
                        }));
        String clientPackageName = routingSessionInfo.getClientPackageName();
        Iterator it = managerRecord.mUserRecord.mRouterRecords.iterator();
        while (true) {
            if (!it.hasNext()) {
                routerRecord = null;
                break;
            }
            RouterRecord routerRecord2 = (RouterRecord) it.next();
            if (TextUtils.equals(routerRecord2.mPackageName, clientPackageName)) {
                routerRecord = routerRecord2;
                break;
            }
        }
        if (routerRecord == null) {
            Slog.w(
                    "MR2ServiceImpl",
                    "requestCreateSessionWithManagerLocked: Ignoring session creation for unknown"
                        + " router.");
            try {
                managerRecord.mManager.notifyRequestFailed(i, 0);
                return;
            } catch (RemoteException unused) {
                Slog.w(
                        "MR2ServiceImpl",
                        "requestCreateSessionWithManagerLocked: Failed to notify failure. Manager"
                            + " probably died.");
                return;
            }
        }
        long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
        SessionCreationRequest sessionCreationRequest = managerRecord.mLastSessionCreationRequest;
        if (sessionCreationRequest != null) {
            Slog.i(
                    "MR2ServiceImpl",
                    TextUtils.formatSimple(
                            "requestCreateSessionWithManagerLocked: Notifying failure for pending"
                                + " session creation request - oldSession: %s, route: %s",
                            new Object[] {
                                sessionCreationRequest.mOldSession, sessionCreationRequest.mRoute
                            }));
            UserHandler userHandler = managerRecord.mUserRecord.mHandler;
            IMediaRouter2Manager iMediaRouter2Manager2 = managerRecord.mManager;
            int i2 = (int) sessionCreationRequest.mManagerRequestId;
            int i3 = UserHandler.$r8$clinit;
            userHandler.getClass();
            UserHandler.notifyRequestFailedToManager(iMediaRouter2Manager2, i2, 0);
        }
        managerRecord.mLastSessionCreationRequest =
                new SessionCreationRequest(
                        routerRecord, 0L, uniqueRequestId, routingSessionInfo, mediaRoute2Info);
        UserHandler userHandler2 = routerRecord.mUserRecord.mHandler;
        userHandler2.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda12(1),
                        userHandler2,
                        Long.valueOf(uniqueRequestId),
                        routerRecord,
                        managerRecord,
                        routingSessionInfo,
                        mediaRoute2Info,
                        userHandle,
                        str));
    }

    public final void requestCreateSessionWithRouter2Locked(
            IMediaRouter2 iMediaRouter2,
            int i,
            long j,
            RoutingSessionInfo routingSessionInfo,
            MediaRoute2Info mediaRoute2Info,
            Bundle bundle) {
        SessionCreationRequest sessionCreationRequest;
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "requestCreateSessionWithRouter2 | router: %s(id: %d), old session id: %s,"
                            + " new session's route id: %s, request id: %d",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            routingSessionInfo.getId(),
                            mediaRoute2Info.getId(),
                            Integer.valueOf(i)
                        }));
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        if (j != 0) {
            ManagerRecord findManagerWithId = userHandler.findManagerWithId((int) (j >> 32));
            if (findManagerWithId == null
                    || (sessionCreationRequest = findManagerWithId.mLastSessionCreationRequest)
                            == null) {
                Slog.w(
                        "MR2ServiceImpl",
                        "requestCreateSessionWithRouter2Locked: Ignoring unknown request.");
                UserHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                return;
            }
            if (!TextUtils.equals(
                    sessionCreationRequest.mOldSession.getId(), routingSessionInfo.getId())) {
                Slog.w(
                        "MR2ServiceImpl",
                        "requestCreateSessionWithRouter2Locked: Ignoring unmatched routing"
                            + " session.");
                UserHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                return;
            }
            if (!TextUtils.equals(
                    findManagerWithId.mLastSessionCreationRequest.mRoute.getId(),
                    mediaRoute2Info.getId())) {
                if (routerRecord.hasSystemRoutingPermission()
                        || !findManagerWithId.mLastSessionCreationRequest.mRoute.isSystemRoute()
                        || !mediaRoute2Info.isSystemRoute()) {
                    Slog.w(
                            "MR2ServiceImpl",
                            "requestCreateSessionWithRouter2Locked: Ignoring unmatched route.");
                    UserHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                    return;
                }
                mediaRoute2Info = findManagerWithId.mLastSessionCreationRequest.mRoute;
            }
            findManagerWithId.mLastSessionCreationRequest = null;
        } else {
            String id = userHandler.mSystemProvider.mDefaultRoute.getId();
            if (mediaRoute2Info.isSystemRoute()
                    && !routerRecord.hasSystemRoutingPermission()
                    && !TextUtils.equals(mediaRoute2Info.getId(), id)) {
                Slog.w(
                        "MR2ServiceImpl",
                        "MODIFY_AUDIO_ROUTING permission is required to transfer to"
                                + mediaRoute2Info);
                UserHandler.notifySessionCreationFailedToRouter(routerRecord, i);
                return;
            }
        }
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda13(),
                        userHandler,
                        Long.valueOf(toUniqueRequestId(routerRecord.mRouterId, i)),
                        Long.valueOf(j),
                        routerRecord,
                        routingSessionInfo,
                        mediaRoute2Info,
                        bundle));
    }

    public final void selectRouteWithManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "selectRouteWithManager | manager: %d, session: %s, route: %s",
                        new Object[] {
                            Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()
                        }));
        RouterRecord routerRecord =
                (RouterRecord)
                        ((ArrayMap) managerRecord.mUserRecord.mHandler.mSessionToRouterMap)
                                .get(str);
        long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
        UserHandler userHandler = managerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(0),
                        userHandler,
                        Long.valueOf(uniqueRequestId),
                        routerRecord,
                        str,
                        mediaRoute2Info));
    }

    public final void selectRouteWithRouter2Locked(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "selectRouteWithRouter2 | router: %s(id: %d), route: %s",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            mediaRoute2Info.getId()
                        }));
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(0),
                        userHandler,
                        -1L,
                        routerRecord,
                        str,
                        mediaRoute2Info));
    }

    public final void setRouteVolumeWithManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            MediaRoute2Info mediaRoute2Info,
            int i2) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "setRouteVolumeWithManager | manager: %d, route: %s, volume: %d",
                        new Object[] {
                            Integer.valueOf(managerRecord.mManagerId),
                            mediaRoute2Info.getId(),
                            Integer.valueOf(i2)
                        }));
        long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
        UserHandler userHandler = managerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(0),
                        userHandler,
                        Long.valueOf(uniqueRequestId),
                        mediaRoute2Info,
                        Integer.valueOf(i2)));
    }

    public final void setRouteVolumeWithRouter2Locked(
            IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord != null) {
            Slog.i(
                    "MR2ServiceImpl",
                    TextUtils.formatSimple(
                            "setRouteVolumeWithRouter2 | router: %s(id: %d), volume: %d",
                            new Object[] {
                                routerRecord.mPackageName,
                                Integer.valueOf(routerRecord.mRouterId),
                                Integer.valueOf(i)
                            }));
            UserHandler userHandler = routerRecord.mUserRecord.mHandler;
            userHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(0),
                            userHandler,
                            -1L,
                            mediaRoute2Info,
                            Integer.valueOf(i)));
        }
    }

    public final void setSessionVolumeWithManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "setSessionVolumeWithManager | manager: %d, session: %s, volume: %d",
                        new Object[] {
                            Integer.valueOf(managerRecord.mManagerId), str, Integer.valueOf(i2)
                        }));
        long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
        UserHandler userHandler = managerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(2),
                        userHandler,
                        Long.valueOf(uniqueRequestId),
                        str,
                        Integer.valueOf(i2)));
    }

    public final void setSessionVolumeWithRouter2Locked(
            IMediaRouter2 iMediaRouter2, String str, int i) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "setSessionVolumeWithRouter2 | router: %s(id: %d), session: %s, volume: %d",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            str,
                            Integer.valueOf(i)
                        }));
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(2),
                        userHandler,
                        -1L,
                        str,
                        Integer.valueOf(i)));
    }

    public final boolean showOutputSwitcher(String str, UserHandle userHandle) {
        if (this.mActivityManager.getPackageImportance(str) > 100) {
            Slog.w(
                    "MR2ServiceImpl",
                    "showMediaOutputSwitcher only works when called from foreground");
            return false;
        }
        synchronized (this.mLock) {
            IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showMediaOutputSwitcher(str, userHandle);
                } catch (RemoteException unused) {
                }
            }
        }
        return true;
    }

    public final void transferToRouteWithManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info,
            UserHandle userHandle,
            String str2) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "transferToRouteWithManager | manager: %d, session: %s, route: %s",
                        new Object[] {
                            Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()
                        }));
        RouterRecord routerRecord =
                (RouterRecord)
                        ((ArrayMap) managerRecord.mUserRecord.mHandler.mSessionToRouterMap)
                                .get(str);
        long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
        UserHandler userHandler = managerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda12(0),
                        userHandler,
                        Long.valueOf(uniqueRequestId),
                        userHandle,
                        str2,
                        routerRecord,
                        str,
                        mediaRoute2Info,
                        1));
    }

    public final void transferToRouteWithRouter2Locked(
            IMediaRouter2 iMediaRouter2,
            UserHandle userHandle,
            String str,
            MediaRoute2Info mediaRoute2Info) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "transferToRouteWithRouter2 | router: %s(id: %d), route: %s",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            mediaRoute2Info.getId()
                        }));
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        String id = userHandler.mSystemProvider.mDefaultRoute.getId();
        if (!mediaRoute2Info.isSystemRoute()
                || routerRecord.hasSystemRoutingPermission()
                || TextUtils.equals(mediaRoute2Info.getId(), id)) {
            userHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda12(0),
                            userHandler,
                            -1L,
                            userHandle,
                            routerRecord.mPackageName,
                            routerRecord,
                            str,
                            mediaRoute2Info,
                            2));
        } else {
            userHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(1),
                            userHandler,
                            routerRecord,
                            Integer.valueOf((int) (-1))));
        }
    }

    public final void unregisterManagerLocked(
            IMediaRouter2Manager iMediaRouter2Manager, boolean z) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.remove(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            Slog.w(
                    "MR2ServiceImpl",
                    TextUtils.formatSimple(
                            "Ignoring unregistering unknown manager: %s, died: %b",
                            new Object[] {iMediaRouter2Manager, Boolean.valueOf(z)}));
            return;
        }
        UserRecord userRecord = managerRecord.mUserRecord;
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "unregisterManager | package: %s, user: %d, manager: %d, died: %b",
                        new Object[] {
                            managerRecord.mOwnerPackageName,
                            Integer.valueOf(userRecord.mUserId),
                            Integer.valueOf(managerRecord.mManagerId),
                            Boolean.valueOf(z)
                        }));
        userRecord.mManagerRecords.remove(managerRecord);
        managerRecord.mManager.asBinder().unlinkToDeath(managerRecord, 0);
        disposeUserIfNeededLocked(userRecord);
    }

    public final void unregisterRouter2Locked(IMediaRouter2 iMediaRouter2, boolean z) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.remove(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            Slog.w(
                    "MR2ServiceImpl",
                    TextUtils.formatSimple(
                            "Ignoring unregistering unknown router: %s, died: %b",
                            new Object[] {iMediaRouter2, Boolean.valueOf(z)}));
            return;
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "unregisterRouter2 | package: %s, router id: %d, died: %b",
                        new Object[] {
                            routerRecord.mPackageName,
                            Integer.valueOf(routerRecord.mRouterId),
                            Boolean.valueOf(z)
                        }));
        UserRecord userRecord = routerRecord.mUserRecord;
        userRecord.mRouterRecords.remove(routerRecord);
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(0),
                        userHandler,
                        routerRecord.mPackageName,
                        (Object) null));
        UserHandler userHandler2 = routerRecord.mUserRecord.mHandler;
        userHandler2.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(3),
                        userHandler2,
                        routerRecord.mPackageName,
                        (Object) null));
        MediaRouter2ServiceImpl$$ExternalSyntheticLambda5
                mediaRouter2ServiceImpl$$ExternalSyntheticLambda5 =
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(0);
        UserHandler userHandler3 = userRecord.mHandler;
        userHandler3.sendMessage(
                PooledLambda.obtainMessage(
                        mediaRouter2ServiceImpl$$ExternalSyntheticLambda5, userHandler3));
        routerRecord.mRouter.asBinder().unlinkToDeath(routerRecord, 0);
        disposeUserIfNeededLocked(userRecord);
    }

    public final void updateScanningStateLocked(IMediaRouter2 iMediaRouter2, int i) {
        RouterRecord routerRecord =
                (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
            Slog.w("MR2ServiceImpl", "Router record not found. Ignoring updateScanningState call.");
            return;
        }
        boolean z =
                Flags.enableFullScanWithMediaContentControl()
                        && routerRecord.mHasMediaContentControlPermission;
        if (i == 2 && !z && !routerRecord.mHasMediaRoutingControl) {
            throw new SecurityException("Screen off scan requires MEDIA_ROUTING_CONTROL");
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "updateScanningStateLocked | router: %d, packageName: %s, scanningState:"
                            + " %d",
                        new Object[] {
                            Integer.valueOf(routerRecord.mRouterId),
                            routerRecord.mPackageName,
                            getScanningStateString(i)
                        }));
        if (routerRecord.mScanningState == i) {
            return;
        }
        routerRecord.mScanningState = i;
        UserHandler userHandler = routerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(0), userHandler));
    }

    public final void updateScanningStateLocked(IMediaRouter2Manager iMediaRouter2Manager, int i) {
        ManagerRecord managerRecord =
                (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
        if (managerRecord == null) {
            Slog.w(
                    "MR2ServiceImpl",
                    "Manager record not found. Ignoring updateScanningState call.");
            return;
        }
        boolean z =
                Flags.enableFullScanWithMediaContentControl()
                        && managerRecord.mHasMediaContentControl;
        if (!managerRecord.mHasMediaRoutingControl && !z && i == 2) {
            throw new SecurityException("Screen off scan requires MEDIA_ROUTING_CONTROL");
        }
        Slog.i(
                "MR2ServiceImpl",
                TextUtils.formatSimple(
                        "updateScanningState | manager: %d, ownerPackageName: %s,"
                            + " targetPackageName: %s, scanningState: %d",
                        new Object[] {
                            Integer.valueOf(managerRecord.mManagerId),
                            managerRecord.mOwnerPackageName,
                            managerRecord.mTargetPackageName,
                            getScanningStateString(i)
                        }));
        if (managerRecord.mScanningState == i) {
            return;
        }
        managerRecord.mScanningState = i;
        UserHandler userHandler = managerRecord.mUserRecord.mHandler;
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(0), userHandler));
    }
}
