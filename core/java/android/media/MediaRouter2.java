package android.media;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.media.flags.Flags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public final class MediaRouter2 {
    private static final long MANAGER_REQUEST_ID_NONE = 0;
    public static final int SCANNING_STATE_NOT_SCANNING = 0;
    public static final int SCANNING_STATE_SCANNING_FULL = 2;
    public static final int SCANNING_STATE_WHILE_INTERACTIVE = 1;
    private static final int TRANSFER_TIMEOUT_MS = 30000;
    private static MediaRouter2 sInstance;
    private final Context mContext;
    private final CopyOnWriteArrayList<ControllerCallbackRecord> mControllerCallbackRecords;
    private final CopyOnWriteArrayList<ControllerCreationRequest> mControllerCreationRequests;
    private RouteDiscoveryPreference mDiscoveryPreference;
    private volatile List<MediaRoute2Info> mFilteredRoutes;
    private final Handler mHandler;
    private final MediaRouter2Impl mImpl;
    private final CopyOnWriteArrayList<RouteListingPreferenceCallbackRecord>
            mListingPreferenceCallbackRecords;
    private final Object mLock;
    private final IMediaRouterService mMediaRouterService;
    private final AtomicInteger mNextRequestId;
    private final Map<String, RoutingController> mNonSystemRoutingControllers;
    private volatile OnGetControllerHintsListener mOnGetControllerHintsListener;
    private volatile ArrayMap<String, MediaRoute2Info> mPreviousFilteredRoutes;
    private final Map<String, MediaRoute2Info> mPreviousUnfilteredRoutes;
    private final CopyOnWriteArrayList<RouteCallbackRecord> mRouteCallbackRecords;
    private RouteListingPreference mRouteListingPreference;
    private final Map<String, MediaRoute2Info> mRoutes;
    private final SparseArray<ScanRequest> mScanRequestsMap;
    private int mScreenOffScanRequestCount;
    private int mScreenOnScanRequestCount;
    private MediaRouter2Stub mStub;
    private final RoutingController mSystemController;
    private final CopyOnWriteArrayList<TransferCallbackRecord> mTransferCallbackRecords;
    private static final String TAG = "MR2";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final Object sSystemRouterLock = new Object();
    private static final Object sRouterLock = new Object();
    private static final Map<PackageNameUserHandlePair, MediaRouter2> sAppToProxyRouterMap =
            new ArrayMap();

    private interface MediaRouter2Impl {
        RouteCallbackRecord createRouteCallbackRecord(
                Executor executor,
                RouteCallback routeCallback,
                RouteDiscoveryPreference routeDiscoveryPreference);

        void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo);

        List<MediaRoute2Info> filterRoutesWithIndividualPreference(
                List<MediaRoute2Info> list, RouteDiscoveryPreference routeDiscoveryPreference);

        List<MediaRoute2Info> getAllRoutes();

        String getClientPackageName();

        List<RoutingController> getControllers();

        String getPackageName();

        RoutingSessionInfo getSystemSessionInfo();

        void registerRouteCallback();

        void releaseSession(boolean z, boolean z2, RoutingController routingController);

        void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo);

        void setOnGetControllerHintsListener(
                OnGetControllerHintsListener onGetControllerHintsListener);

        void setRouteListingPreference(RouteListingPreference routeListingPreference);

        void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i);

        void setSessionVolume(int i, RoutingSessionInfo routingSessionInfo);

        boolean showSystemOutputSwitcher();

        void startScan();

        void stop();

        void stopScan();

        void transfer(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info);

        void transferTo(MediaRoute2Info mediaRoute2Info);

        void unregisterRouteCallback();

        void updateScanningState(int i) throws RemoteException;

        boolean wasTransferredBySelf(RoutingSessionInfo routingSessionInfo);
    }

    public interface OnGetControllerHintsListener {
        Bundle onGetControllerHints(MediaRoute2Info mediaRoute2Info);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanningState {}

    private static final class PackageNameUserHandlePair extends Record {
        private final String packageName;
        private final UserHandle user;

        private PackageNameUserHandlePair(String packageName, UserHandle user) {
            this.packageName = packageName;
            this.user = user;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(
                                            Boolean.TYPE,
                                            PackageNameUserHandlePair.class,
                                            Object.class),
                                    PackageNameUserHandlePair.class,
                                    "packageName;user",
                                    "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->packageName:Ljava/lang/String;",
                                    "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->user:Landroid/os/UserHandle;")
                            .dynamicInvoker()
                            .invoke(this, o) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(
                                            Integer.TYPE, PackageNameUserHandlePair.class),
                                    PackageNameUserHandlePair.class,
                                    "packageName;user",
                                    "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->packageName:Ljava/lang/String;",
                                    "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->user:Landroid/os/UserHandle;")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        public String packageName() {
            return this.packageName;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "toString",
                                    MethodType.methodType(
                                            String.class, PackageNameUserHandlePair.class),
                                    PackageNameUserHandlePair.class,
                                    "packageName;user",
                                    "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->packageName:Ljava/lang/String;",
                                    "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->user:Landroid/os/UserHandle;")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        public UserHandle user() {
            return this.user;
        }
    }

    private static final class InstanceInvalidatedCallbackRecord extends Record {
        private final Executor executor;
        private final Runnable runnable;

        private InstanceInvalidatedCallbackRecord(Executor executor, Runnable runnable) {
            this.executor = executor;
            this.runnable = runnable;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(
                                            Boolean.TYPE,
                                            InstanceInvalidatedCallbackRecord.class,
                                            Object.class),
                                    InstanceInvalidatedCallbackRecord.class,
                                    "executor;runnable",
                                    "FIELD:Landroid/media/MediaRouter2$InstanceInvalidatedCallbackRecord;->executor:Ljava/util/concurrent/Executor;",
                                    "FIELD:Landroid/media/MediaRouter2$InstanceInvalidatedCallbackRecord;->runnable:Ljava/lang/Runnable;")
                            .dynamicInvoker()
                            .invoke(this, o) /* invoke-custom */;
        }

        public Executor executor() {
            return this.executor;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(
                                            Integer.TYPE, InstanceInvalidatedCallbackRecord.class),
                                    InstanceInvalidatedCallbackRecord.class,
                                    "executor;runnable",
                                    "FIELD:Landroid/media/MediaRouter2$InstanceInvalidatedCallbackRecord;->executor:Ljava/util/concurrent/Executor;",
                                    "FIELD:Landroid/media/MediaRouter2$InstanceInvalidatedCallbackRecord;->runnable:Ljava/lang/Runnable;")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }

        public Runnable runnable() {
            return this.runnable;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "toString",
                                    MethodType.methodType(
                                            String.class, InstanceInvalidatedCallbackRecord.class),
                                    InstanceInvalidatedCallbackRecord.class,
                                    "executor;runnable",
                                    "FIELD:Landroid/media/MediaRouter2$InstanceInvalidatedCallbackRecord;->executor:Ljava/util/concurrent/Executor;",
                                    "FIELD:Landroid/media/MediaRouter2$InstanceInvalidatedCallbackRecord;->runnable:Ljava/lang/Runnable;")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
        }
    }

    public static MediaRouter2 getInstance(Context context) {
        MediaRouter2 mediaRouter2;
        Objects.requireNonNull(context, "context must not be null");
        synchronized (sRouterLock) {
            if (sInstance == null) {
                sInstance = new MediaRouter2(context.getApplicationContext());
            }
            mediaRouter2 = sInstance;
        }
        return mediaRouter2;
    }

    @SystemApi
    public static MediaRouter2 getInstance(Context context, String clientPackageName) {
        try {
            return findOrCreateProxyInstanceForCallingUser(
                    context, clientPackageName, context.getUser(), null, null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Package " + clientPackageName + " not found. Ignoring.");
            return null;
        }
    }

    public static MediaRouter2 getInstance(
            Context context,
            String clientPackageName,
            Executor executor,
            Runnable onInstanceInvalidatedListener) {
        Objects.requireNonNull(executor, "Executor must not be null");
        Objects.requireNonNull(
                onInstanceInvalidatedListener, "onInstanceInvalidatedListener must not be null.");
        return findOrCreateProxyInstanceForCallingUser(
                context,
                clientPackageName,
                context.getUser(),
                executor,
                onInstanceInvalidatedListener);
    }

    public static MediaRouter2 getInstance(
            Context context, String clientPackageName, UserHandle user) {
        return findOrCreateProxyInstanceForCallingUser(
                context, clientPackageName, user, null, null);
    }

    private static MediaRouter2 findOrCreateProxyInstanceForCallingUser(
            Context context,
            String clientPackageName,
            UserHandle user,
            Executor executor,
            Runnable onInstanceInvalidatedListener) {
        MediaRouter2 instance;
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(user, "user must not be null");
        if (TextUtils.isEmpty(clientPackageName)) {
            throw new IllegalArgumentException("clientPackageName must not be null or empty");
        }
        if ((executor == null || onInstanceInvalidatedListener == null)
                && checkCallerHasOnlyRevocablePermissions(context)) {
            throw new IllegalStateException(
                    "Use getInstance(Context, String, Executor, Runnable) to obtain a proxy"
                            + " MediaRouter2 instance.");
        }
        PackageNameUserHandlePair key = new PackageNameUserHandlePair(clientPackageName, user);
        synchronized (sSystemRouterLock) {
            instance = sAppToProxyRouterMap.get(key);
            if (instance == null) {
                instance =
                        new MediaRouter2(context, Looper.getMainLooper(), clientPackageName, user);
                ((ProxyMediaRouter2Impl) instance.mImpl).registerProxyRouter();
                sAppToProxyRouterMap.put(key, instance);
            }
            ((ProxyMediaRouter2Impl) instance.mImpl)
                    .registerInstanceInvalidatedCallback(executor, onInstanceInvalidatedListener);
        }
        return instance;
    }

    private static boolean checkCallerHasOnlyRevocablePermissions(Context context) {
        boolean hasMediaContentControl =
                context.checkSelfPermission(Manifest.permission.MEDIA_CONTENT_CONTROL) == 0;
        boolean hasRegularMediaRoutingControl =
                context.checkSelfPermission(Manifest.permission.MEDIA_ROUTING_CONTROL) == 0;
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        boolean hasAppOpMediaRoutingControl =
                appOpsManager.unsafeCheckOp(
                                AppOpsManager.OPSTR_MEDIA_ROUTING_CONTROL,
                                context.getApplicationInfo().uid,
                                context.getOpPackageName())
                        == 0;
        return (hasMediaContentControl
                        || hasRegularMediaRoutingControl
                        || !hasAppOpMediaRoutingControl)
                ? false
                : true;
    }

    @SystemApi
    public void startScan() {
        this.mImpl.startScan();
    }

    @SystemApi
    public void stopScan() {
        this.mImpl.stopScan();
    }

    public ScanToken requestScan(ScanRequest scanRequest) {
        int i;
        Objects.requireNonNull(scanRequest, "scanRequest must not be null.");
        ScanToken token = new ScanToken(this.mNextRequestId.getAndIncrement());
        synchronized (this.mLock) {
            boolean shouldUpdate =
                    this.mScreenOffScanRequestCount == 0
                            && (scanRequest.isScreenOffScan()
                                    || this.mScreenOnScanRequestCount == 0);
            if (shouldUpdate) {
                try {
                    MediaRouter2Impl mediaRouter2Impl = this.mImpl;
                    if (scanRequest.isScreenOffScan()) {
                        i = 2;
                    } else {
                        i = 1;
                    }
                    mediaRouter2Impl.updateScanningState(i);
                } catch (RemoteException ex) {
                    throw ex.rethrowFromSystemServer();
                }
            }
            if (scanRequest.isScreenOffScan()) {
                this.mScreenOffScanRequestCount++;
            } else {
                this.mScreenOnScanRequestCount++;
            }
            this.mScanRequestsMap.put(token.mId, scanRequest);
        }
        return token;
    }

    public void cancelScanRequest(ScanToken token) {
        boolean shouldUpdate;
        Objects.requireNonNull(token, "token must not be null");
        synchronized (this.mLock) {
            ScanRequest request = this.mScanRequestsMap.get(token.mId);
            if (request == null) {
                throw new IllegalArgumentException(
                        "The token does not match any active scan request");
            }
            if (request.isScreenOffScan()) {
                shouldUpdate = this.mScreenOffScanRequestCount == 1;
            } else {
                shouldUpdate =
                        this.mScreenOnScanRequestCount == 1 && this.mScreenOffScanRequestCount == 0;
            }
            if (shouldUpdate) {
                try {
                    if (request.isScreenOffScan() && this.mScreenOnScanRequestCount != 0) {
                        this.mImpl.updateScanningState(1);
                    }
                    this.mImpl.updateScanningState(0);
                } catch (RemoteException ex) {
                    ex.rethrowFromSystemServer();
                }
            }
            if (request.isScreenOffScan()) {
                this.mScreenOffScanRequestCount--;
            } else {
                this.mScreenOnScanRequestCount--;
            }
            this.mScanRequestsMap.remove(token.mId);
        }
    }

    private MediaRouter2(Context appContext) {
        this.mLock = new Object();
        this.mRouteCallbackRecords = new CopyOnWriteArrayList<>();
        this.mListingPreferenceCallbackRecords = new CopyOnWriteArrayList<>();
        this.mTransferCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCreationRequests = new CopyOnWriteArrayList<>();
        this.mRoutes = new ArrayMap();
        this.mNonSystemRoutingControllers = new ArrayMap();
        this.mScreenOffScanRequestCount = 0;
        this.mScreenOnScanRequestCount = 0;
        this.mScanRequestsMap = new SparseArray<>();
        this.mNextRequestId = new AtomicInteger(1);
        this.mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
        this.mPreviousFilteredRoutes = new ArrayMap<>();
        this.mPreviousUnfilteredRoutes = new ArrayMap();
        this.mFilteredRoutes = Collections.emptyList();
        this.mContext = appContext;
        this.mMediaRouterService =
                IMediaRouterService.Stub.asInterface(
                        ServiceManager.getService(Context.MEDIA_ROUTER_SERVICE));
        this.mImpl = new LocalMediaRouter2Impl(this.mContext.getPackageName());
        this.mHandler = new Handler(Looper.getMainLooper());
        loadSystemRoutes(false);
        RoutingSessionInfo currentSystemSessionInfo = this.mImpl.getSystemSessionInfo();
        if (currentSystemSessionInfo == null) {
            throw new RuntimeException("Null currentSystemSessionInfo. Something is wrong.");
        }
        this.mSystemController = new SystemRoutingController(currentSystemSessionInfo);
    }

    private MediaRouter2(
            Context context, Looper looper, String clientPackageName, UserHandle user) {
        this.mLock = new Object();
        this.mRouteCallbackRecords = new CopyOnWriteArrayList<>();
        this.mListingPreferenceCallbackRecords = new CopyOnWriteArrayList<>();
        this.mTransferCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCreationRequests = new CopyOnWriteArrayList<>();
        this.mRoutes = new ArrayMap();
        this.mNonSystemRoutingControllers = new ArrayMap();
        this.mScreenOffScanRequestCount = 0;
        this.mScreenOnScanRequestCount = 0;
        this.mScanRequestsMap = new SparseArray<>();
        this.mNextRequestId = new AtomicInteger(1);
        this.mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
        this.mPreviousFilteredRoutes = new ArrayMap<>();
        this.mPreviousUnfilteredRoutes = new ArrayMap();
        this.mFilteredRoutes = Collections.emptyList();
        this.mContext = context;
        this.mHandler = new Handler(looper);
        this.mMediaRouterService =
                IMediaRouterService.Stub.asInterface(
                        ServiceManager.getService(Context.MEDIA_ROUTER_SERVICE));
        loadSystemRoutes(true);
        this.mSystemController =
                new SystemRoutingController(
                        ProxyMediaRouter2Impl.getSystemSessionInfoImpl(
                                this.mMediaRouterService,
                                this.mContext.getPackageName(),
                                clientPackageName));
        this.mImpl = new ProxyMediaRouter2Impl(context, clientPackageName, user);
    }

    private void loadSystemRoutes(boolean isProxyRouter) {
        List<MediaRoute2Info> currentSystemRoutes = null;
        try {
            currentSystemRoutes =
                    this.mMediaRouterService.getSystemRoutes(
                            this.mContext.getPackageName(), isProxyRouter);
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
        }
        if (currentSystemRoutes == null || currentSystemRoutes.isEmpty()) {
            throw new RuntimeException("Null or empty currentSystemRoutes. Something is wrong.");
        }
        for (MediaRoute2Info route : currentSystemRoutes) {
            this.mRoutes.put(route.getId(), route);
        }
    }

    @SystemApi
    public String getClientPackageName() {
        return this.mImpl.getClientPackageName();
    }

    public void registerRouteCallback(
            Executor executor, RouteCallback routeCallback, RouteDiscoveryPreference preference) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(routeCallback, "callback must not be null");
        Objects.requireNonNull(preference, "preference must not be null");
        RouteCallbackRecord record =
                this.mImpl.createRouteCallbackRecord(executor, routeCallback, preference);
        this.mRouteCallbackRecords.remove(record);
        this.mRouteCallbackRecords.addIfAbsent(record);
        this.mImpl.registerRouteCallback();
    }

    public void unregisterRouteCallback(RouteCallback routeCallback) {
        Objects.requireNonNull(routeCallback, "callback must not be null");
        if (!this.mRouteCallbackRecords.remove(
                new RouteCallbackRecord(null, routeCallback, null))) {
            Log.w(TAG, "unregisterRouteCallback: Ignoring unknown callback");
        } else {
            this.mImpl.unregisterRouteCallback();
        }
    }

    public void registerRouteListingPreferenceUpdatedCallback(
            Executor executor, Consumer<RouteListingPreference> routeListingPreferenceCallback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(routeListingPreferenceCallback, "callback must not be null");
        RouteListingPreferenceCallbackRecord record =
                new RouteListingPreferenceCallbackRecord(executor, routeListingPreferenceCallback);
        this.mListingPreferenceCallbackRecords.remove(record);
        this.mListingPreferenceCallbackRecords.add(record);
    }

    public void unregisterRouteListingPreferenceUpdatedCallback(
            Consumer<RouteListingPreference> callback) {
        Objects.requireNonNull(callback, "callback must not be null");
        if (!this.mListingPreferenceCallbackRecords.remove(
                new RouteListingPreferenceCallbackRecord(null, callback))) {
            Log.w(
                    TAG,
                    "unregisterRouteListingPreferenceUpdatedCallback: Ignoring an unknown"
                            + " callback");
        }
    }

    public boolean showSystemOutputSwitcher() {
        return this.mImpl.showSystemOutputSwitcher();
    }

    public void setRouteListingPreference(RouteListingPreference routeListingPreference) {
        this.mImpl.setRouteListingPreference(routeListingPreference);
    }

    public RouteListingPreference getRouteListingPreference() {
        RouteListingPreference routeListingPreference;
        synchronized (this.mLock) {
            routeListingPreference = this.mRouteListingPreference;
        }
        return routeListingPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateDiscoveryPreferenceIfNeededLocked() {
        RouteDiscoveryPreference newDiscoveryPreference =
                new RouteDiscoveryPreference.Builder(
                                (Collection<RouteDiscoveryPreference>)
                                        this.mRouteCallbackRecords.stream()
                                                .map(
                                                        new Function() { // from class:
                                                            // android.media.MediaRouter2$$ExternalSyntheticLambda17
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                RouteDiscoveryPreference
                                                                        routeDiscoveryPreference;
                                                                routeDiscoveryPreference =
                                                                        ((MediaRouter2
                                                                                                .RouteCallbackRecord)
                                                                                        obj)
                                                                                .mPreference;
                                                                return routeDiscoveryPreference;
                                                            }
                                                        })
                                                .collect(Collectors.toList()))
                        .build();
        if (Objects.equals(this.mDiscoveryPreference, newDiscoveryPreference)) {
            return false;
        }
        this.mDiscoveryPreference = newDiscoveryPreference;
        updateFilteredRoutesLocked();
        return true;
    }

    @SystemApi
    public List<MediaRoute2Info> getAllRoutes() {
        return this.mImpl.getAllRoutes();
    }

    public List<MediaRoute2Info> getRoutes() {
        List<MediaRoute2Info> list;
        synchronized (this.mLock) {
            list = this.mFilteredRoutes;
        }
        return list;
    }

    public void registerTransferCallback(Executor executor, TransferCallback callback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        TransferCallbackRecord record = new TransferCallbackRecord(executor, callback);
        if (!this.mTransferCallbackRecords.addIfAbsent(record)) {
            Log.w(TAG, "registerTransferCallback: Ignoring the same callback");
        }
    }

    public void unregisterTransferCallback(TransferCallback callback) {
        Objects.requireNonNull(callback, "callback must not be null");
        if (!this.mTransferCallbackRecords.remove(new TransferCallbackRecord(null, callback))) {
            Log.w(TAG, "unregisterTransferCallback: Ignoring an unknown callback");
        }
    }

    public void registerControllerCallback(Executor executor, ControllerCallback callback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        ControllerCallbackRecord record = new ControllerCallbackRecord(executor, callback);
        if (!this.mControllerCallbackRecords.addIfAbsent(record)) {
            Log.w(TAG, "registerControllerCallback: Ignoring the same callback");
        }
    }

    public void unregisterControllerCallback(ControllerCallback callback) {
        Objects.requireNonNull(callback, "callback must not be null");
        if (!this.mControllerCallbackRecords.remove(new ControllerCallbackRecord(null, callback))) {
            Log.w(TAG, "unregisterControllerCallback: Ignoring an unknown callback");
        }
    }

    public void setOnGetControllerHintsListener(OnGetControllerHintsListener listener) {
        this.mImpl.setOnGetControllerHintsListener(listener);
    }

    public void transferTo(MediaRoute2Info route) {
        this.mImpl.transferTo(route);
    }

    public void stop() {
        this.mImpl.stop();
    }

    @SystemApi
    public void transfer(RoutingController controller, MediaRoute2Info route) {
        this.mImpl.transfer(controller.getRoutingSessionInfo(), route);
    }

    void requestCreateController(
            RoutingController controller, MediaRoute2Info route, long managerRequestId) {
        Bundle controllerHints;
        MediaRouter2Stub stub;
        int requestId = this.mNextRequestId.getAndIncrement();
        ControllerCreationRequest request =
                new ControllerCreationRequest(requestId, managerRequestId, route, controller);
        this.mControllerCreationRequests.add(request);
        OnGetControllerHintsListener listener = this.mOnGetControllerHintsListener;
        if (listener == null) {
            controllerHints = null;
        } else {
            Bundle controllerHints2 = listener.onGetControllerHints(route);
            if (controllerHints2 == null) {
                controllerHints = controllerHints2;
            } else {
                controllerHints = new Bundle(controllerHints2);
            }
        }
        synchronized (this.mLock) {
            stub = this.mStub;
        }
        if (stub != null) {
            try {
                this.mMediaRouterService.requestCreateSessionWithRouter2(
                        stub,
                        requestId,
                        managerRequestId,
                        controller.getRoutingSessionInfo(),
                        route,
                        controllerHints);
            } catch (RemoteException ex) {
                Log.e(
                        TAG,
                        "createControllerForTransfer: Failed to request for creating a controller.",
                        ex);
                this.mControllerCreationRequests.remove(request);
                if (managerRequestId == 0) {
                    notifyTransferFailure(route);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RoutingController getCurrentController() {
        List<RoutingController> controllers = getControllers();
        return controllers.get(controllers.size() - 1);
    }

    public RoutingController getSystemController() {
        return this.mSystemController;
    }

    public RoutingController getController(String id) {
        Objects.requireNonNull(id, "id must not be null");
        for (RoutingController controller : getControllers()) {
            if (TextUtils.equals(id, controller.getId())) {
                return controller;
            }
        }
        return null;
    }

    public List<RoutingController> getControllers() {
        return this.mImpl.getControllers();
    }

    public void setRouteVolume(MediaRoute2Info route, int volume) {
        Objects.requireNonNull(route, "route must not be null");
        this.mImpl.setRouteVolume(route, volume);
    }

    void syncRoutesOnHandler(
            List<MediaRoute2Info> currentRoutes, RoutingSessionInfo currentSystemSessionInfo) {
        if (currentRoutes == null || currentRoutes.isEmpty() || currentSystemSessionInfo == null) {
            Log.e(
                    TAG,
                    "syncRoutesOnHandler: Received wrong data. currentRoutes="
                            + currentRoutes
                            + ", currentSystemSessionInfo="
                            + currentSystemSessionInfo);
            return;
        }
        updateRoutesOnHandler(currentRoutes);
        RoutingSessionInfo oldInfo = this.mSystemController.getRoutingSessionInfo();
        this.mSystemController.setRoutingSessionInfo(
                ensureClientPackageNameForSystemSession(
                        currentSystemSessionInfo, this.mContext.getPackageName()));
        if (!oldInfo.equals(currentSystemSessionInfo)) {
            notifyControllerUpdated(this.mSystemController);
        }
    }

    void dispatchFilteredRoutesUpdatedOnHandler(List<MediaRoute2Info> newRoutes) {
        List<MediaRoute2Info> addedRoutes = new ArrayList<>();
        List<MediaRoute2Info> removedRoutes = new ArrayList<>();
        List<MediaRoute2Info> changedRoutes = new ArrayList<>();
        Set<String> newRouteIds =
                (Set)
                        newRoutes.stream()
                                .map(new MediaRouter2$$ExternalSyntheticLambda16())
                                .collect(Collectors.toSet());
        for (MediaRoute2Info route : newRoutes) {
            MediaRoute2Info prevRoute = this.mPreviousFilteredRoutes.get(route.getId());
            if (prevRoute == null) {
                addedRoutes.add(route);
            } else if (!prevRoute.equals(route)) {
                changedRoutes.add(route);
            }
        }
        for (int i = 0; i < this.mPreviousFilteredRoutes.size(); i++) {
            if (!newRouteIds.contains(this.mPreviousFilteredRoutes.keyAt(i))) {
                removedRoutes.add(this.mPreviousFilteredRoutes.valueAt(i));
            }
        }
        Iterator<MediaRoute2Info> it = removedRoutes.iterator();
        while (it.hasNext()) {
            this.mPreviousFilteredRoutes.remove(it.next().getId());
        }
        for (MediaRoute2Info route2 : addedRoutes) {
            this.mPreviousFilteredRoutes.put(route2.getId(), route2);
        }
        for (MediaRoute2Info route3 : changedRoutes) {
            this.mPreviousFilteredRoutes.put(route3.getId(), route3);
        }
        if (!addedRoutes.isEmpty()) {
            notifyRoutesAdded(addedRoutes);
        }
        if (!removedRoutes.isEmpty()) {
            notifyRoutesRemoved(removedRoutes);
        }
        if (!changedRoutes.isEmpty()) {
            notifyRoutesChanged(changedRoutes);
        }
        if (!addedRoutes.isEmpty() || !removedRoutes.isEmpty() || !changedRoutes.isEmpty()) {
            notifyRoutesUpdated(newRoutes);
        }
    }

    void dispatchControllerUpdatedIfNeededOnHandler(Map<String, MediaRoute2Info> routesMap) {
        List<RoutingController> controllers = getControllers();
        for (RoutingController controller : controllers) {
            Iterator<String> it = controller.getRoutingSessionInfo().getSelectedRoutes().iterator();
            while (true) {
                if (it.hasNext()) {
                    String selectedRoute = it.next();
                    if (routesMap.containsKey(selectedRoute)
                            && this.mPreviousUnfilteredRoutes.containsKey(selectedRoute)) {
                        MediaRoute2Info currentRoute = routesMap.get(selectedRoute);
                        MediaRoute2Info oldRoute =
                                this.mPreviousUnfilteredRoutes.get(selectedRoute);
                        if (!currentRoute.equals(oldRoute)) {
                            notifyControllerUpdated(controller);
                            break;
                        }
                    }
                }
            }
        }
        this.mPreviousUnfilteredRoutes.clear();
        this.mPreviousUnfilteredRoutes.putAll(routesMap);
    }

    void updateRoutesOnHandler(List<MediaRoute2Info> newRoutes) {
        synchronized (this.mLock) {
            this.mRoutes.clear();
            for (MediaRoute2Info route : newRoutes) {
                this.mRoutes.put(route.getId(), route);
            }
            updateFilteredRoutesLocked();
        }
    }

    void updateFilteredRoutesLocked() {
        this.mFilteredRoutes =
                Collections.unmodifiableList(
                        filterRoutesWithCompositePreferenceLocked(
                                List.copyOf(this.mRoutes.values())));
        this.mHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new BiConsumer() { // from class:
                            // android.media.MediaRouter2$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ((MediaRouter2) obj)
                                        .dispatchFilteredRoutesUpdatedOnHandler((List) obj2);
                            }
                        },
                        this,
                        this.mFilteredRoutes));
        this.mHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new BiConsumer() { // from class:
                            // android.media.MediaRouter2$$ExternalSyntheticLambda4
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ((MediaRouter2) obj)
                                        .dispatchControllerUpdatedIfNeededOnHandler((HashMap) obj2);
                            }
                        },
                        this,
                        new HashMap(this.mRoutes)));
    }

    void createControllerOnHandler(int requestId, RoutingSessionInfo sessionInfo) {
        ControllerCreationRequest matchingRequest = null;
        Iterator<ControllerCreationRequest> it = this.mControllerCreationRequests.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ControllerCreationRequest request = it.next();
            if (request.mRequestId == requestId) {
                matchingRequest = request;
                break;
            }
        }
        if (matchingRequest == null) {
            Log.w(TAG, "createControllerOnHandler: Ignoring an unknown request.");
            return;
        }
        this.mControllerCreationRequests.remove(matchingRequest);
        MediaRoute2Info requestedRoute = matchingRequest.mRoute;
        if (sessionInfo == null) {
            notifyTransferFailure(requestedRoute);
            return;
        }
        if (!TextUtils.equals(requestedRoute.getProviderId(), sessionInfo.getProviderId())) {
            Log.w(
                    TAG,
                    "The session's provider ID does not match the requested route's. (requested"
                            + " route's providerId="
                            + requestedRoute.getProviderId()
                            + ", actual providerId="
                            + sessionInfo.getProviderId()
                            + NavigationBarInflaterView.KEY_CODE_END);
            notifyTransferFailure(requestedRoute);
            return;
        }
        RoutingController oldController = matchingRequest.mOldController;
        if (!oldController.scheduleRelease()) {
            Log.w(
                    TAG,
                    "createControllerOnHandler: Ignoring controller creation for released old"
                            + " controller. oldController="
                            + oldController);
            if (!sessionInfo.isSystemSession()) {
                new RoutingController(sessionInfo).release();
            }
            notifyTransferFailure(requestedRoute);
            return;
        }
        RoutingController newController = addRoutingController(sessionInfo);
        notifyTransfer(oldController, newController);
    }

    private RoutingController addRoutingController(RoutingSessionInfo session) {
        if (session.isSystemSession()) {
            this.mSystemController.setRoutingSessionInfo(session);
            return this.mSystemController;
        }
        RoutingController controller = new RoutingController(session);
        synchronized (this.mLock) {
            this.mNonSystemRoutingControllers.put(controller.getId(), controller);
        }
        return controller;
    }

    void updateControllerOnHandler(RoutingSessionInfo sessionInfo) {
        if (sessionInfo == null) {
            Log.w(TAG, "updateControllerOnHandler: Ignoring null sessionInfo.");
            return;
        }
        RoutingController controller =
                getMatchingController(sessionInfo, "updateControllerOnHandler");
        if (controller != null) {
            controller.setRoutingSessionInfo(sessionInfo);
            notifyControllerUpdated(controller);
        }
    }

    void releaseControllerOnHandler(RoutingSessionInfo sessionInfo) {
        if (sessionInfo == null) {
            Log.w(TAG, "releaseControllerOnHandler: Ignoring null sessionInfo.");
            return;
        }
        RoutingController matchingController =
                getMatchingController(sessionInfo, "releaseControllerOnHandler");
        if (matchingController != null) {
            matchingController.releaseInternal(false);
        }
    }

    private RoutingController getMatchingController(
            RoutingSessionInfo sessionInfo, String logPrefix) {
        RoutingController controller;
        if (sessionInfo.isSystemSession()) {
            return getSystemController();
        }
        synchronized (this.mLock) {
            controller = this.mNonSystemRoutingControllers.get(sessionInfo.getId());
        }
        if (controller == null) {
            Log.w(
                    TAG,
                    logPrefix
                            + ": Matching controller not found. uniqueSessionId="
                            + sessionInfo.getId());
            return null;
        }
        RoutingSessionInfo oldInfo = controller.getRoutingSessionInfo();
        if (!TextUtils.equals(oldInfo.getProviderId(), sessionInfo.getProviderId())) {
            Log.w(
                    TAG,
                    logPrefix
                            + ": Provider IDs are not matched. old="
                            + oldInfo.getProviderId()
                            + ", new="
                            + sessionInfo.getProviderId());
            return null;
        }
        return controller;
    }

    void onRequestCreateControllerByManagerOnHandler(
            RoutingSessionInfo oldSession, MediaRoute2Info route, long managerRequestId) {
        RoutingController controller;
        RoutingController controller2;
        Log.i(
                TAG,
                TextUtils.formatSimple(
                        "requestCreateSessionByManager | requestId: %d, oldSession: %s, route: %s",
                        Long.valueOf(managerRequestId), oldSession, route));
        if (oldSession.isSystemSession()) {
            controller2 = getSystemController();
        } else {
            synchronized (this.mLock) {
                controller = this.mNonSystemRoutingControllers.get(oldSession.getId());
            }
            controller2 = controller;
        }
        if (controller2 == null) {
            return;
        }
        requestCreateController(controller2, route, managerRequestId);
    }

    private List<MediaRoute2Info> getSortedRoutes(
            List<MediaRoute2Info> routes, List<String> packageOrder) {
        if (packageOrder.isEmpty()) {
            return routes;
        }
        final Map<String, Integer> packagePriority = new ArrayMap<>();
        int count = packageOrder.size();
        for (int i = 0; i < count; i++) {
            packagePriority.put(packageOrder.get(i), Integer.valueOf(count - i));
        }
        ArrayList<MediaRoute2Info> sortedRoutes = new ArrayList<>(routes);
        sortedRoutes.sort(
                Comparator.comparingInt(
                        new ToIntFunction() { // from class:
                            // android.media.MediaRouter2$$ExternalSyntheticLambda2
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                return MediaRouter2.lambda$getSortedRoutes$1(
                                        packagePriority, (MediaRoute2Info) obj);
                            }
                        }));
        return sortedRoutes;
    }

    static /* synthetic */ int lambda$getSortedRoutes$1(Map packagePriority, MediaRoute2Info r) {
        return -((Integer) packagePriority.getOrDefault(r.getPackageName(), 0)).intValue();
    }

    private List<MediaRoute2Info> filterRoutesWithCompositePreferenceLocked(
            List<MediaRoute2Info> routes) {
        Set<String> deduplicationIdSet = new ArraySet<>();
        List<MediaRoute2Info> filteredRoutes = new ArrayList<>();
        for (MediaRoute2Info route :
                getSortedRoutes(routes, this.mDiscoveryPreference.getDeduplicationPackageOrder())) {
            if (route.hasAnyFeatures(this.mDiscoveryPreference.getPreferredFeatures())
                    && (this.mDiscoveryPreference.getAllowedPackages().isEmpty()
                            || (route.getPackageName() != null
                                    && this.mDiscoveryPreference
                                            .getAllowedPackages()
                                            .contains(route.getPackageName())))) {
                if (this.mDiscoveryPreference.shouldRemoveDuplicates()) {
                    if (Collections.disjoint(deduplicationIdSet, route.getDeduplicationIds())) {
                        deduplicationIdSet.addAll(route.getDeduplicationIds());
                    }
                }
                filteredRoutes.add(route);
            }
        }
        return filteredRoutes;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MediaRoute2Info> getRoutesWithIds(List<String> routeIds) {
        List<MediaRoute2Info> list;
        synchronized (this.mLock) {
            Stream<String> stream = routeIds.stream();
            Map<String, MediaRoute2Info> map = this.mRoutes;
            Objects.requireNonNull(map);
            list =
                    (List)
                            stream.map(new MediaRouter2$$ExternalSyntheticLambda10(map))
                                    .filter(new MediaRouter2$$ExternalSyntheticLambda11())
                                    .collect(Collectors.toList());
        }
        return list;
    }

    private void notifyRoutesAdded(List<MediaRoute2Info> routes) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord record = it.next();
            final List<MediaRoute2Info> filteredRoutes =
                    this.mImpl.filterRoutesWithIndividualPreference(routes, record.mPreference);
            if (!filteredRoutes.isEmpty()) {
                record.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.MediaRouter2$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaRouter2.RouteCallbackRecord.this.mRouteCallback.onRoutesAdded(
                                        filteredRoutes);
                            }
                        });
            }
        }
    }

    private void notifyRoutesRemoved(List<MediaRoute2Info> routes) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord record = it.next();
            final List<MediaRoute2Info> filteredRoutes =
                    this.mImpl.filterRoutesWithIndividualPreference(routes, record.mPreference);
            if (!filteredRoutes.isEmpty()) {
                record.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.MediaRouter2$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaRouter2.RouteCallbackRecord.this.mRouteCallback
                                        .onRoutesRemoved(filteredRoutes);
                            }
                        });
            }
        }
    }

    private void notifyRoutesChanged(List<MediaRoute2Info> routes) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord record = it.next();
            final List<MediaRoute2Info> filteredRoutes =
                    this.mImpl.filterRoutesWithIndividualPreference(routes, record.mPreference);
            if (!filteredRoutes.isEmpty()) {
                record.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.media.MediaRouter2$$ExternalSyntheticLambda12
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaRouter2.RouteCallbackRecord.this.mRouteCallback
                                        .onRoutesChanged(filteredRoutes);
                            }
                        });
            }
        }
    }

    private void notifyRoutesUpdated(List<MediaRoute2Info> routes) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord record = it.next();
            final List<MediaRoute2Info> filteredRoutes =
                    this.mImpl.filterRoutesWithIndividualPreference(routes, record.mPreference);
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.RouteCallbackRecord.this.mRouteCallback.onRoutesUpdated(
                                    filteredRoutes);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPreferredFeaturesChanged(final List<String> features) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord record = it.next();
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.RouteCallbackRecord.this.mRouteCallback
                                    .onPreferredFeaturesChanged(features);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRouteListingPreferenceUpdated(final RouteListingPreference preference) {
        Iterator<RouteListingPreferenceCallbackRecord> it =
                this.mListingPreferenceCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteListingPreferenceCallbackRecord record = it.next();
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.RouteListingPreferenceCallbackRecord.this
                                    .mRouteListingPreferenceCallback.accept(preference);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTransfer(
            final RoutingController oldController, final RoutingController newController) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord record = it.next();
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.TransferCallbackRecord.this.mTransferCallback.onTransfer(
                                    oldController, newController);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTransferFailure(final MediaRoute2Info route) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord record = it.next();
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.TransferCallbackRecord.this.mTransferCallback
                                    .onTransferFailure(route);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRequestFailed(final int reason) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord record = it.next();
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.TransferCallbackRecord.this.mTransferCallback
                                    .onRequestFailed(reason);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyStop(final RoutingController controller) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord record = it.next();
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.TransferCallbackRecord.this.mTransferCallback.onStop(
                                    controller);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyControllerUpdated(final RoutingController controller) {
        Iterator<ControllerCallbackRecord> it = this.mControllerCallbackRecords.iterator();
        while (it.hasNext()) {
            final ControllerCallbackRecord record = it.next();
            record.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.media.MediaRouter2$$ExternalSyntheticLambda14
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouter2.ControllerCallbackRecord.this.mCallback
                                    .onControllerUpdated(controller);
                        }
                    });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static RoutingSessionInfo ensureClientPackageNameForSystemSession(
            RoutingSessionInfo sessionInfo, String packageName) {
        if (!sessionInfo.isSystemSession()
                || !TextUtils.isEmpty(sessionInfo.getClientPackageName())) {
            return sessionInfo;
        }
        return new RoutingSessionInfo.Builder(sessionInfo)
                .setClientPackageName(packageName)
                .build();
    }

    public abstract static class RouteCallback {
        @Deprecated
        public void onRoutesAdded(List<MediaRoute2Info> routes) {}

        @Deprecated
        public void onRoutesRemoved(List<MediaRoute2Info> routes) {}

        @Deprecated
        public void onRoutesChanged(List<MediaRoute2Info> routes) {}

        public void onRoutesUpdated(List<MediaRoute2Info> routes) {}

        @SystemApi
        public void onPreferredFeaturesChanged(List<String> preferredFeatures) {}
    }

    public abstract static class TransferCallback {
        public void onTransfer(RoutingController oldController, RoutingController newController) {}

        public void onTransferFailure(MediaRoute2Info requestedRoute) {}

        public void onStop(RoutingController controller) {}

        public void onRequestFailed(int reason) {}
    }

    public abstract static class ControllerCallback {
        public void onControllerUpdated(RoutingController controller) {}
    }

    public static final class ScanToken {
        private final int mId;

        private ScanToken(int id) {
            this.mId = id;
        }
    }

    public static final class ScanRequest {
        private final boolean mIsScreenOffScan;

        private ScanRequest(boolean isScreenOffScan) {
            this.mIsScreenOffScan = isScreenOffScan;
        }

        public boolean isScreenOffScan() {
            return this.mIsScreenOffScan;
        }

        public static final class Builder {
            boolean mIsScreenOffScan;

            public Builder setScreenOffScan(boolean isScreenOffScan) {
                this.mIsScreenOffScan = isScreenOffScan;
                return this;
            }

            public ScanRequest build() {
                return new ScanRequest(this.mIsScreenOffScan);
            }
        }
    }

    public class RoutingController {
        private static final int CONTROLLER_STATE_ACTIVE = 1;
        private static final int CONTROLLER_STATE_RELEASED = 3;
        private static final int CONTROLLER_STATE_RELEASING = 2;
        private static final int CONTROLLER_STATE_UNKNOWN = 0;
        private final Object mControllerLock;
        private RoutingSessionInfo mSessionInfo;
        private int mState;

        RoutingController(RoutingSessionInfo sessionInfo) {
            this.mControllerLock = new Object();
            this.mSessionInfo = sessionInfo;
            this.mState = 1;
        }

        RoutingController(RoutingSessionInfo sessionInfo, int state) {
            this.mControllerLock = new Object();
            this.mSessionInfo = sessionInfo;
            this.mState = state;
        }

        public String getId() {
            String id;
            synchronized (this.mControllerLock) {
                id = this.mSessionInfo.getId();
            }
            return id;
        }

        public String getOriginalId() {
            String originalId;
            synchronized (this.mControllerLock) {
                originalId = this.mSessionInfo.getOriginalId();
            }
            return originalId;
        }

        public Bundle getControlHints() {
            Bundle controlHints;
            synchronized (this.mControllerLock) {
                controlHints = this.mSessionInfo.getControlHints();
            }
            return controlHints;
        }

        public List<MediaRoute2Info> getSelectedRoutes() {
            List<String> selectedRouteIds;
            synchronized (this.mControllerLock) {
                selectedRouteIds = this.mSessionInfo.getSelectedRoutes();
            }
            return MediaRouter2.this.getRoutesWithIds(selectedRouteIds);
        }

        public List<MediaRoute2Info> getSelectableRoutes() {
            List<String> selectableRouteIds;
            synchronized (this.mControllerLock) {
                selectableRouteIds = this.mSessionInfo.getSelectableRoutes();
            }
            return MediaRouter2.this.getRoutesWithIds(selectableRouteIds);
        }

        public List<MediaRoute2Info> getDeselectableRoutes() {
            List<String> deselectableRouteIds;
            synchronized (this.mControllerLock) {
                deselectableRouteIds = this.mSessionInfo.getDeselectableRoutes();
            }
            return MediaRouter2.this.getRoutesWithIds(deselectableRouteIds);
        }

        public List<MediaRoute2Info> getTransferableRoutes() {
            List<String> transferableRoutes;
            synchronized (this.mControllerLock) {
                transferableRoutes = this.mSessionInfo.getTransferableRoutes();
            }
            return MediaRouter2.this.getRoutesWithIds(transferableRoutes);
        }

        public boolean wasTransferInitiatedBySelf() {
            return MediaRouter2.this.mImpl.wasTransferredBySelf(getRoutingSessionInfo());
        }

        public RoutingSessionInfo getRoutingSessionInfo() {
            RoutingSessionInfo routingSessionInfo;
            synchronized (this.mControllerLock) {
                routingSessionInfo = this.mSessionInfo;
            }
            return routingSessionInfo;
        }

        public int getVolumeHandling() {
            int volumeHandling;
            synchronized (this.mControllerLock) {
                volumeHandling = this.mSessionInfo.getVolumeHandling();
            }
            return volumeHandling;
        }

        public int getVolumeMax() {
            int volumeMax;
            synchronized (this.mControllerLock) {
                volumeMax = this.mSessionInfo.getVolumeMax();
            }
            return volumeMax;
        }

        public int getVolume() {
            int volume;
            synchronized (this.mControllerLock) {
                volume = this.mSessionInfo.getVolume();
            }
            return volume;
        }

        public boolean isReleased() {
            boolean z;
            synchronized (this.mControllerLock) {
                z = this.mState == 3;
            }
            return z;
        }

        public void selectRoute(MediaRoute2Info route) {
            Objects.requireNonNull(route, "route must not be null");
            if (isReleased()) {
                Log.w(MediaRouter2.TAG, "selectRoute: Called on released controller. Ignoring.");
                return;
            }
            List<MediaRoute2Info> selectedRoutes = getSelectedRoutes();
            if (containsRouteInfoWithId(selectedRoutes, route.getId())) {
                Log.w(
                        MediaRouter2.TAG,
                        "Ignoring selecting a route that is already selected. route=" + route);
                return;
            }
            List<MediaRoute2Info> selectableRoutes = getSelectableRoutes();
            if (!containsRouteInfoWithId(selectableRoutes, route.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring selecting a non-selectable route=" + route);
            } else {
                MediaRouter2.this.mImpl.selectRoute(route, getRoutingSessionInfo());
            }
        }

        public void deselectRoute(MediaRoute2Info route) {
            Objects.requireNonNull(route, "route must not be null");
            if (isReleased()) {
                Log.w(MediaRouter2.TAG, "deselectRoute: called on released controller. Ignoring.");
                return;
            }
            List<MediaRoute2Info> selectedRoutes = getSelectedRoutes();
            if (!containsRouteInfoWithId(selectedRoutes, route.getId())) {
                Log.w(
                        MediaRouter2.TAG,
                        "Ignoring deselecting a route that is not selected. route=" + route);
                return;
            }
            List<MediaRoute2Info> deselectableRoutes = getDeselectableRoutes();
            if (!containsRouteInfoWithId(deselectableRoutes, route.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring deselecting a non-deselectable route=" + route);
            } else {
                MediaRouter2.this.mImpl.deselectRoute(route, getRoutingSessionInfo());
            }
        }

        boolean tryTransferWithinProvider(MediaRoute2Info route) {
            MediaRouter2Stub stub;
            Objects.requireNonNull(route, "route must not be null");
            synchronized (this.mControllerLock) {
                if (isReleased()) {
                    Log.w(
                            MediaRouter2.TAG,
                            "tryTransferWithinProvider: Called on released controller. Ignoring.");
                    return true;
                }
                boolean isSystemRouteReselection =
                        Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()
                                && this.mSessionInfo.isSystemSession()
                                && route.isSystemRoute()
                                && this.mSessionInfo.getSelectedRoutes().contains(route.getId());
                if (!isSystemRouteReselection
                        && !this.mSessionInfo.getTransferableRoutes().contains(route.getId())) {
                    Log.i(
                            MediaRouter2.TAG,
                            "Transferring to a non-transferable route="
                                    + route
                                    + " session= "
                                    + this.mSessionInfo.getId());
                    return false;
                }
                synchronized (MediaRouter2.this.mLock) {
                    stub = MediaRouter2.this.mStub;
                }
                if (stub != null) {
                    try {
                        MediaRouter2.this.mMediaRouterService.transferToRouteWithRouter2(
                                stub, getId(), route);
                    } catch (RemoteException ex) {
                        Log.e(MediaRouter2.TAG, "Unable to transfer to route for session.", ex);
                    }
                }
                return true;
            }
        }

        public void setVolume(int volume) {
            if (getVolumeHandling() == 0) {
                Log.w(
                        MediaRouter2.TAG,
                        "setVolume: The routing session has fixed volume. Ignoring.");
                return;
            }
            if (volume < 0 || volume > getVolumeMax()) {
                Log.w(MediaRouter2.TAG, "setVolume: The target volume is out of range. Ignoring");
            } else if (isReleased()) {
                Log.w(MediaRouter2.TAG, "setVolume: Called on released controller. Ignoring.");
            } else {
                MediaRouter2.this.mImpl.setSessionVolume(volume, getRoutingSessionInfo());
            }
        }

        public void release() {
            releaseInternal(true);
        }

        boolean scheduleRelease() {
            synchronized (this.mControllerLock) {
                if (this.mState != 1) {
                    return false;
                }
                this.mState = 2;
                synchronized (MediaRouter2.this.mLock) {
                    if (!MediaRouter2.this.mNonSystemRoutingControllers.remove(getId(), this)) {
                        return true;
                    }
                    MediaRouter2.this.mHandler.postDelayed(
                            new Runnable() { // from class:
                                // android.media.MediaRouter2$RoutingController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MediaRouter2.RoutingController.this.release();
                                }
                            },
                            30000L);
                    return true;
                }
            }
        }

        void releaseInternal(boolean shouldReleaseSession) {
            synchronized (this.mControllerLock) {
                if (this.mState == 3) {
                    if (MediaRouter2.DEBUG) {
                        Log.d(
                                MediaRouter2.TAG,
                                "releaseInternal: Called on released controller. Ignoring.");
                    }
                    return;
                }
                boolean z = true;
                if (this.mState != 1) {
                    z = false;
                }
                boolean shouldNotifyStop = z;
                this.mState = 3;
                MediaRouter2.this.mImpl.releaseSession(
                        shouldReleaseSession, shouldNotifyStop, this);
            }
        }

        public String toString() {
            List<String> selectedRoutes =
                    (List)
                            getSelectedRoutes().stream()
                                    .map(new MediaRouter2$$ExternalSyntheticLambda16())
                                    .collect(Collectors.toList());
            List<String> selectableRoutes =
                    (List)
                            getSelectableRoutes().stream()
                                    .map(new MediaRouter2$$ExternalSyntheticLambda16())
                                    .collect(Collectors.toList());
            List<String> deselectableRoutes =
                    (List)
                            getDeselectableRoutes().stream()
                                    .map(new MediaRouter2$$ExternalSyntheticLambda16())
                                    .collect(Collectors.toList());
            StringBuilder result =
                    new StringBuilder()
                            .append("RoutingController{ ")
                            .append("id=")
                            .append(getId())
                            .append(", selectedRoutes={")
                            .append(selectedRoutes)
                            .append("}")
                            .append(", selectableRoutes={")
                            .append(selectableRoutes)
                            .append("}")
                            .append(", deselectableRoutes={")
                            .append(deselectableRoutes)
                            .append("}")
                            .append(" }");
            return result.toString();
        }

        void setRoutingSessionInfo(RoutingSessionInfo info) {
            synchronized (this.mControllerLock) {
                this.mSessionInfo = info;
            }
        }

        private static boolean containsRouteInfoWithId(
                List<MediaRoute2Info> routeList, String routeId) {
            for (MediaRoute2Info info : routeList) {
                if (TextUtils.equals(routeId, info.getId())) {
                    return true;
                }
            }
            return false;
        }
    }

    class SystemRoutingController extends RoutingController {
        SystemRoutingController(RoutingSessionInfo sessionInfo) {
            super(sessionInfo);
        }

        @Override // android.media.MediaRouter2.RoutingController
        public boolean isReleased() {
            return false;
        }

        @Override // android.media.MediaRouter2.RoutingController
        boolean scheduleRelease() {
            return true;
        }

        @Override // android.media.MediaRouter2.RoutingController
        void releaseInternal(boolean shouldReleaseSession) {}
    }

    static final class RouteCallbackRecord {
        public final Executor mExecutor;
        public final RouteDiscoveryPreference mPreference;
        public final RouteCallback mRouteCallback;

        RouteCallbackRecord(
                Executor executor,
                RouteCallback routeCallback,
                RouteDiscoveryPreference preference) {
            this.mRouteCallback = routeCallback;
            this.mExecutor = executor;
            this.mPreference = preference;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RouteCallbackRecord)
                    && this.mRouteCallback == ((RouteCallbackRecord) obj).mRouteCallback;
        }

        public int hashCode() {
            return this.mRouteCallback.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RouteListingPreferenceCallbackRecord {
        public final Executor mExecutor;
        public final Consumer<RouteListingPreference> mRouteListingPreferenceCallback;

        RouteListingPreferenceCallbackRecord(
                Executor executor,
                Consumer<RouteListingPreference> routeListingPreferenceCallback) {
            this.mExecutor = executor;
            this.mRouteListingPreferenceCallback = routeListingPreferenceCallback;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RouteListingPreferenceCallbackRecord)
                    && this.mRouteListingPreferenceCallback
                            == ((RouteListingPreferenceCallbackRecord) obj)
                                    .mRouteListingPreferenceCallback;
        }

        public int hashCode() {
            return this.mRouteListingPreferenceCallback.hashCode();
        }
    }

    static final class TransferCallbackRecord {
        public final Executor mExecutor;
        public final TransferCallback mTransferCallback;

        TransferCallbackRecord(Executor executor, TransferCallback transferCallback) {
            this.mTransferCallback = transferCallback;
            this.mExecutor = executor;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TransferCallbackRecord)
                    && this.mTransferCallback == ((TransferCallbackRecord) obj).mTransferCallback;
        }

        public int hashCode() {
            return this.mTransferCallback.hashCode();
        }
    }

    static final class ControllerCallbackRecord {
        public final ControllerCallback mCallback;
        public final Executor mExecutor;

        ControllerCallbackRecord(Executor executor, ControllerCallback callback) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ControllerCallbackRecord)
                    && this.mCallback == ((ControllerCallbackRecord) obj).mCallback;
        }

        public int hashCode() {
            return this.mCallback.hashCode();
        }
    }

    static final class ControllerCreationRequest {
        public final long mManagerRequestId;
        public final RoutingController mOldController;
        public final int mRequestId;
        public final MediaRoute2Info mRoute;

        ControllerCreationRequest(
                int requestId,
                long managerRequestId,
                MediaRoute2Info route,
                RoutingController oldController) {
            this.mRequestId = requestId;
            this.mManagerRequestId = managerRequestId;
            this.mRoute = (MediaRoute2Info) Objects.requireNonNull(route, "route must not be null");
            this.mOldController =
                    (RoutingController)
                            Objects.requireNonNull(oldController, "oldController must not be null");
        }
    }

    class MediaRouter2Stub extends IMediaRouter2.Stub {
        MediaRouter2Stub() {}

        @Override // android.media.IMediaRouter2
        public void notifyRouterRegistered(
                List<MediaRoute2Info> currentRoutes, RoutingSessionInfo currentSystemSessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                // android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda2
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((MediaRouter2) obj)
                                            .syncRoutesOnHandler(
                                                    (List) obj2, (RoutingSessionInfo) obj3);
                                }
                            },
                            MediaRouter2.this,
                            currentRoutes,
                            currentSystemSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifyRoutesUpdated(List<MediaRoute2Info> routes) {
            MediaRouter2.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda3(),
                            MediaRouter2.this,
                            routes));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionCreated(int requestId, RoutingSessionInfo sessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                // android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda1
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((MediaRouter2) obj)
                                            .createControllerOnHandler(
                                                    ((Integer) obj2).intValue(),
                                                    (RoutingSessionInfo) obj3);
                                }
                            },
                            MediaRouter2.this,
                            Integer.valueOf(requestId),
                            sessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionInfoChanged(RoutingSessionInfo sessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                // android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda5
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((MediaRouter2) obj)
                                            .updateControllerOnHandler((RoutingSessionInfo) obj2);
                                }
                            },
                            MediaRouter2.this,
                            sessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionReleased(RoutingSessionInfo sessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                // android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((MediaRouter2) obj)
                                            .releaseControllerOnHandler((RoutingSessionInfo) obj2);
                                }
                            },
                            MediaRouter2.this,
                            sessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void requestCreateSessionByManager(
                long managerRequestId, RoutingSessionInfo oldSession, MediaRoute2Info route) {
            MediaRouter2.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new QuadConsumer() { // from class:
                                // android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda4
                                @Override // com.android.internal.util.function.QuadConsumer
                                public final void accept(
                                        Object obj, Object obj2, Object obj3, Object obj4) {
                                    ((MediaRouter2) obj)
                                            .onRequestCreateControllerByManagerOnHandler(
                                                    (RoutingSessionInfo) obj2,
                                                    (MediaRoute2Info) obj3,
                                                    ((Long) obj4).longValue());
                                }
                            },
                            MediaRouter2.this,
                            oldSession,
                            route,
                            Long.valueOf(managerRequestId)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ProxyMediaRouter2Impl implements MediaRouter2Impl {
        private final String mClientPackageName;
        private final UserHandle mClientUser;
        private final CopyOnWriteArrayList<MediaRouter2Manager.TransferRequest> mTransferRequests =
                new CopyOnWriteArrayList<>();
        private final AtomicInteger mScanRequestCount = new AtomicInteger(0);
        private final AtomicBoolean mIsScanning = new AtomicBoolean(false);
        private final List<InstanceInvalidatedCallbackRecord> mInstanceInvalidatedCallbackRecords =
                new ArrayList();
        private final IMediaRouter2Manager.Stub mClient = new Client();

        ProxyMediaRouter2Impl(Context context, String clientPackageName, UserHandle user) {
            this.mClientUser = user;
            this.mClientPackageName = clientPackageName;
            MediaRouter2.this.mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
        }

        public void registerProxyRouter() {
            try {
                MediaRouter2.this.mMediaRouterService.registerProxyRouter(
                        this.mClient,
                        MediaRouter2.this.mContext.getApplicationContext().getPackageName(),
                        this.mClientPackageName,
                        this.mClientUser);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        public void registerInstanceInvalidatedCallback(
                Executor executor, Runnable onInstanceInvalidatedListener) {
            if (executor == null || onInstanceInvalidatedListener == null) {
                return;
            }
            InstanceInvalidatedCallbackRecord record =
                    new InstanceInvalidatedCallbackRecord(executor, onInstanceInvalidatedListener);
            synchronized (MediaRouter2.this.mLock) {
                if (!this.mInstanceInvalidatedCallbackRecords.contains(record)) {
                    this.mInstanceInvalidatedCallbackRecords.add(record);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void updateScanningState(int scanningState) throws RemoteException {
            MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, scanningState);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void startScan() {
            if (!this.mIsScanning.getAndSet(true)
                    && this.mScanRequestCount.getAndIncrement() == 0) {
                try {
                    MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, 1);
                } catch (RemoteException ex) {
                    throw ex.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stopScan() {
            if (this.mIsScanning.getAndSet(false)
                    && this.mScanRequestCount.updateAndGet(
                                    new IntUnaryOperator() { // from class:
                                        // android.media.MediaRouter2$ProxyMediaRouter2Impl$$ExternalSyntheticLambda0
                                        @Override // java.util.function.IntUnaryOperator
                                        public final int applyAsInt(int i) {
                                            return MediaRouter2.ProxyMediaRouter2Impl
                                                    .lambda$stopScan$0(i);
                                        }
                                    })
                            == 0) {
                try {
                    MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, 0);
                } catch (RemoteException ex) {
                    throw ex.rethrowFromSystemServer();
                }
            }
        }

        static /* synthetic */ int lambda$stopScan$0(int count) {
            if (count == 0) {
                throw new IllegalStateException("No active scan requests to unregister.");
            }
            return count - 1;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getClientPackageName() {
            return this.mClientPackageName;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getPackageName() {
            return null;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RoutingSessionInfo getSystemSessionInfo() {
            return getSystemSessionInfoImpl(
                    MediaRouter2.this.mMediaRouterService,
                    MediaRouter2.this.mContext.getPackageName(),
                    this.mClientPackageName);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RouteCallbackRecord createRouteCallbackRecord(
                Executor executor,
                RouteCallback routeCallback,
                RouteDiscoveryPreference preference) {
            return new RouteCallbackRecord(executor, routeCallback, RouteDiscoveryPreference.EMPTY);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void registerRouteCallback() {}

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void unregisterRouteCallback() {}

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteListingPreference(RouteListingPreference preference) {
            throw new UnsupportedOperationException(
                    "RouteListingPreference cannot be set by a privileged MediaRouter2 instance.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean showSystemOutputSwitcher() {
            try {
                return MediaRouter2.this.mMediaRouterService.showMediaOutputSwitcherWithProxyRouter(
                        this.mClient);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<MediaRoute2Info> getAllRoutes() {
            ArrayList arrayList;
            synchronized (MediaRouter2.this.mLock) {
                arrayList = new ArrayList(MediaRouter2.this.mRoutes.values());
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setOnGetControllerHintsListener(OnGetControllerHintsListener listener) {}

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transferTo(MediaRoute2Info route) {
            Objects.requireNonNull(route, "route must not be null");
            List<RoutingSessionInfo> sessionInfos = getRoutingSessions();
            RoutingSessionInfo targetSession = sessionInfos.get(sessionInfos.size() - 1);
            transfer(targetSession, route);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stop() {
            List<RoutingSessionInfo> sessionInfos = getRoutingSessions();
            RoutingSessionInfo sessionToRelease = sessionInfos.get(sessionInfos.size() - 1);
            releaseSession(sessionToRelease);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transfer(RoutingSessionInfo sessionInfo, MediaRoute2Info route) {
            boolean isUnknownRoute;
            Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
            Objects.requireNonNull(route, "route must not be null");
            Log.v(
                    MediaRouter2.TAG,
                    "Transferring routing session. session= " + sessionInfo + ", route=" + route);
            synchronized (MediaRouter2.this.mLock) {
                isUnknownRoute = !MediaRouter2.this.mRoutes.containsKey(route.getId());
            }
            if (isUnknownRoute) {
                Log.w(MediaRouter2.TAG, "transfer: Ignoring an unknown route id=" + route.getId());
                onTransferFailed(sessionInfo, route);
                return;
            }
            boolean isSystemRouteReselection =
                    Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()
                            && sessionInfo.isSystemSession()
                            && route.isSystemRoute()
                            && sessionInfo.getSelectedRoutes().contains(route.getId());
            if (sessionInfo.getTransferableRoutes().contains(route.getId())
                    || isSystemRouteReselection) {
                transferToRoute(sessionInfo, route, this.mClientUser, this.mClientPackageName);
            } else {
                requestCreateSession(sessionInfo, route, this.mClientUser, this.mClientPackageName);
            }
        }

        private void transferToRoute(
                RoutingSessionInfo session,
                MediaRoute2Info route,
                UserHandle transferInitiatorUserHandle,
                String transferInitiatorPackageName) {
            int requestId = createTransferRequest(session, route);
            try {
                MediaRouter2.this.mMediaRouterService.transferToRouteWithManager(
                        this.mClient,
                        requestId,
                        session.getId(),
                        route,
                        transferInitiatorUserHandle,
                        transferInitiatorPackageName);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        private void requestCreateSession(
                RoutingSessionInfo oldSession,
                MediaRoute2Info route,
                UserHandle transferInitiatorUserHandle,
                String transferInitiatorPackageName) {
            if (TextUtils.isEmpty(oldSession.getClientPackageName())) {
                Log.w(
                        MediaRouter2.TAG,
                        "requestCreateSession: Can't create a session without package name.");
                onTransferFailed(oldSession, route);
            } else {
                int requestId = createTransferRequest(oldSession, route);
                try {
                    MediaRouter2.this.mMediaRouterService.requestCreateSessionWithManager(
                            this.mClient,
                            requestId,
                            oldSession,
                            route,
                            transferInitiatorUserHandle,
                            transferInitiatorPackageName);
                } catch (RemoteException ex) {
                    throw ex.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<RoutingController> getControllers() {
            RoutingController controller;
            List<RoutingController> result = new ArrayList<>();
            List<RoutingSessionInfo> sessions = getRoutingSessions();
            for (RoutingSessionInfo session : sessions) {
                if (session.isSystemSession()) {
                    MediaRouter2.this.mSystemController.setRoutingSessionInfo(session);
                    controller = MediaRouter2.this.mSystemController;
                } else {
                    controller = MediaRouter2.this.new RoutingController(session);
                }
                result.add(controller);
            }
            return result;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteVolume(MediaRoute2Info route, int volume) {
            if (route.getVolumeHandling() == 0) {
                Log.w(MediaRouter2.TAG, "setRouteVolume: the route has fixed volume. Ignoring.");
                return;
            }
            if (volume < 0 || volume > route.getVolumeMax()) {
                Log.w(
                        MediaRouter2.TAG,
                        "setRouteVolume: the target volume is out of range. Ignoring");
                return;
            }
            try {
                int requestId = MediaRouter2.this.mNextRequestId.getAndIncrement();
                MediaRouter2.this.mMediaRouterService.setRouteVolumeWithManager(
                        this.mClient, requestId, route, volume);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setSessionVolume(int volume, RoutingSessionInfo sessionInfo) {
            Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
            if (sessionInfo.getVolumeHandling() == 0) {
                Log.w(MediaRouter2.TAG, "setSessionVolume: the route has fixed volume. Ignoring.");
                return;
            }
            if (volume < 0 || volume > sessionInfo.getVolumeMax()) {
                Log.w(
                        MediaRouter2.TAG,
                        "setSessionVolume: the target volume is out of range. Ignoring");
                return;
            }
            try {
                int requestId = MediaRouter2.this.mNextRequestId.getAndIncrement();
                MediaRouter2.this.mMediaRouterService.setSessionVolumeWithManager(
                        this.mClient, requestId, sessionInfo.getId(), volume);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<MediaRoute2Info> filterRoutesWithIndividualPreference(
                List<MediaRoute2Info> routes, RouteDiscoveryPreference discoveryPreference) {
            return new ArrayList(routes);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void selectRoute(MediaRoute2Info route, RoutingSessionInfo sessionInfo) {
            Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
            Objects.requireNonNull(route, "route must not be null");
            if (sessionInfo.getSelectedRoutes().contains(route.getId())) {
                Log.w(
                        MediaRouter2.TAG,
                        "Ignoring selecting a route that is already selected. route=" + route);
                return;
            }
            if (!sessionInfo.getSelectableRoutes().contains(route.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring selecting a non-selectable route=" + route);
                return;
            }
            try {
                int requestId = MediaRouter2.this.mNextRequestId.getAndIncrement();
                MediaRouter2.this.mMediaRouterService.selectRouteWithManager(
                        this.mClient, requestId, sessionInfo.getId(), route);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void deselectRoute(MediaRoute2Info route, RoutingSessionInfo sessionInfo) {
            Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
            Objects.requireNonNull(route, "route must not be null");
            if (!sessionInfo.getSelectedRoutes().contains(route.getId())) {
                Log.w(
                        MediaRouter2.TAG,
                        "Ignoring deselecting a route that is not selected. route=" + route);
                return;
            }
            if (!sessionInfo.getDeselectableRoutes().contains(route.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring deselecting a non-deselectable route=" + route);
                return;
            }
            try {
                int requestId = MediaRouter2.this.mNextRequestId.getAndIncrement();
                MediaRouter2.this.mMediaRouterService.deselectRouteWithManager(
                        this.mClient, requestId, sessionInfo.getId(), route);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void releaseSession(
                boolean shouldReleaseSession,
                boolean shouldNotifyStop,
                RoutingController controller) {
            releaseSession(controller.getRoutingSessionInfo());
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean wasTransferredBySelf(RoutingSessionInfo sessionInfo) {
            UserHandle transferInitiatorUserHandle = sessionInfo.getTransferInitiatorUserHandle();
            String transferInitiatorPackageName = sessionInfo.getTransferInitiatorPackageName();
            return Objects.equals(this.mClientUser, transferInitiatorUserHandle)
                    && Objects.equals(this.mClientPackageName, transferInitiatorPackageName);
        }

        static RoutingSessionInfo getSystemSessionInfoImpl(
                IMediaRouterService service, String callerPackageName, String clientPackageName) {
            try {
                return service.getSystemSessionInfoForPackage(callerPackageName, clientPackageName);
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        private void releaseSession(RoutingSessionInfo sessionInfo) {
            Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
            try {
                int requestId = MediaRouter2.this.mNextRequestId.getAndIncrement();
                MediaRouter2.this.mMediaRouterService.releaseSessionWithManager(
                        this.mClient, requestId, sessionInfo.getId());
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        private int createTransferRequest(RoutingSessionInfo session, MediaRoute2Info route) {
            int requestId = MediaRouter2.this.mNextRequestId.getAndIncrement();
            MediaRouter2Manager.TransferRequest transferRequest =
                    new MediaRouter2Manager.TransferRequest(requestId, session, route);
            this.mTransferRequests.add(transferRequest);
            Message timeoutMessage =
                    PooledLambda.obtainMessage(
                            new BiConsumer() { // from class:
                                // android.media.MediaRouter2$ProxyMediaRouter2Impl$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                            .handleTransferTimeout(
                                                    (MediaRouter2Manager.TransferRequest) obj2);
                                }
                            },
                            this,
                            transferRequest);
            MediaRouter2.this.mHandler.sendMessageDelayed(timeoutMessage, 30000L);
            return requestId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleTransferTimeout(MediaRouter2Manager.TransferRequest request) {
            boolean removed = this.mTransferRequests.remove(request);
            if (removed) {
                onTransferFailed(request.mOldSessionInfo, request.mTargetRoute);
            }
        }

        private List<RoutingSessionInfo> getRoutingSessions() {
            List<RoutingSessionInfo> sessions = new ArrayList<>();
            sessions.add(getSystemSessionInfo());
            try {
                List<RoutingSessionInfo> remoteSessions =
                        MediaRouter2.this.mMediaRouterService.getRemoteSessions(this.mClient);
                for (RoutingSessionInfo sessionInfo : remoteSessions) {
                    if (TextUtils.equals(
                            sessionInfo.getClientPackageName(), this.mClientPackageName)) {
                        sessions.add(sessionInfo);
                    }
                }
                return sessions;
            } catch (RemoteException ex) {
                throw ex.rethrowFromSystemServer();
            }
        }

        private void onTransferred(RoutingSessionInfo oldSession, RoutingSessionInfo newSession) {
            RoutingController oldController;
            RoutingController newController;
            if (!isSessionRelatedToTargetPackageName(oldSession)
                    || !isSessionRelatedToTargetPackageName(newSession)) {
                return;
            }
            if (oldSession.isSystemSession()) {
                MediaRouter2.this.mSystemController.setRoutingSessionInfo(
                        MediaRouter2.ensureClientPackageNameForSystemSession(
                                oldSession, this.mClientPackageName));
                oldController = MediaRouter2.this.mSystemController;
            } else {
                oldController = MediaRouter2.this.new RoutingController(oldSession);
            }
            if (newSession.isSystemSession()) {
                MediaRouter2.this.mSystemController.setRoutingSessionInfo(
                        MediaRouter2.ensureClientPackageNameForSystemSession(
                                newSession, this.mClientPackageName));
                newController = MediaRouter2.this.mSystemController;
            } else {
                newController = MediaRouter2.this.new RoutingController(newSession);
            }
            MediaRouter2.this.notifyTransfer(oldController, newController);
        }

        private void onTransferFailed(RoutingSessionInfo session, MediaRoute2Info route) {
            if (!isSessionRelatedToTargetPackageName(session)) {
                return;
            }
            MediaRouter2.this.notifyTransferFailure(route);
        }

        private void onSessionUpdated(RoutingSessionInfo session) {
            RoutingController controller;
            if (!isSessionRelatedToTargetPackageName(session)) {
                return;
            }
            if (session.isSystemSession()) {
                MediaRouter2.this.mSystemController.setRoutingSessionInfo(
                        MediaRouter2.ensureClientPackageNameForSystemSession(
                                session, this.mClientPackageName));
                controller = MediaRouter2.this.mSystemController;
            } else {
                controller = MediaRouter2.this.new RoutingController(session);
            }
            MediaRouter2.this.notifyControllerUpdated(controller);
        }

        private boolean isSessionRelatedToTargetPackageName(RoutingSessionInfo session) {
            return session.isSystemSession()
                    || TextUtils.equals(getClientPackageName(), session.getClientPackageName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionCreatedOnHandler(int requestId, RoutingSessionInfo sessionInfo) {
            MediaRouter2Manager.TransferRequest matchingRequest = null;
            Iterator<MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MediaRouter2Manager.TransferRequest request = it.next();
                if (request.mRequestId == requestId) {
                    matchingRequest = request;
                    break;
                }
            }
            if (matchingRequest == null) {
                return;
            }
            this.mTransferRequests.remove(matchingRequest);
            MediaRoute2Info requestedRoute = matchingRequest.mTargetRoute;
            if (!sessionInfo.getSelectedRoutes().contains(requestedRoute.getId())) {
                Log.w(
                        MediaRouter2.TAG,
                        "The session does not contain the requested route. (requestedRouteId="
                                + requestedRoute.getId()
                                + ", actualRoutes="
                                + sessionInfo.getSelectedRoutes()
                                + NavigationBarInflaterView.KEY_CODE_END);
                onTransferFailed(matchingRequest.mOldSessionInfo, requestedRoute);
            } else if (!TextUtils.equals(
                    requestedRoute.getProviderId(), sessionInfo.getProviderId())) {
                Log.w(
                        MediaRouter2.TAG,
                        "The session's provider ID does not match the requested route's. (requested"
                                + " route's providerId="
                                + requestedRoute.getProviderId()
                                + ", actual providerId="
                                + sessionInfo.getProviderId()
                                + NavigationBarInflaterView.KEY_CODE_END);
                onTransferFailed(matchingRequest.mOldSessionInfo, requestedRoute);
            } else {
                onTransferred(matchingRequest.mOldSessionInfo, sessionInfo);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionUpdatedOnHandler(RoutingSessionInfo updatedSession) {
            Iterator<MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MediaRouter2Manager.TransferRequest request = it.next();
                String sessionId = request.mOldSessionInfo.getId();
                if (TextUtils.equals(sessionId, updatedSession.getId())
                        && updatedSession
                                .getSelectedRoutes()
                                .contains(request.mTargetRoute.getId())) {
                    this.mTransferRequests.remove(request);
                    break;
                }
            }
            onSessionUpdated(updatedSession);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionReleasedOnHandler(RoutingSessionInfo session) {
            if (session.isSystemSession()) {
                Log.e(
                        MediaRouter2.TAG,
                        "onSessionReleasedOnHandler: Called on system session. Ignoring.");
            } else {
                if (!TextUtils.equals(getClientPackageName(), session.getClientPackageName())) {
                    return;
                }
                MediaRouter2.this.notifyStop(MediaRouter2.this.new RoutingController(session, 3));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDiscoveryPreferenceChangedOnHandler(
                String packageName, RouteDiscoveryPreference preference) {
            if (!TextUtils.equals(getClientPackageName(), packageName) || preference == null) {
                return;
            }
            synchronized (MediaRouter2.this.mLock) {
                if (Objects.equals(preference, MediaRouter2.this.mDiscoveryPreference)) {
                    return;
                }
                MediaRouter2.this.mDiscoveryPreference = preference;
                MediaRouter2.this.updateFilteredRoutesLocked();
                MediaRouter2.this.notifyPreferredFeaturesChanged(preference.getPreferredFeatures());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRouteListingPreferenceChangedOnHandler(
                String packageName, RouteListingPreference routeListingPreference) {
            if (!TextUtils.equals(getClientPackageName(), packageName)) {
                return;
            }
            synchronized (MediaRouter2.this.mLock) {
                if (Objects.equals(
                        MediaRouter2.this.mRouteListingPreference, routeListingPreference)) {
                    return;
                }
                MediaRouter2.this.mRouteListingPreference = routeListingPreference;
                MediaRouter2.this.notifyRouteListingPreferenceUpdated(routeListingPreference);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRequestFailedOnHandler(int requestId, int reason) {
            MediaRouter2Manager.TransferRequest matchingRequest = null;
            Iterator<MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MediaRouter2Manager.TransferRequest request = it.next();
                if (request.mRequestId == requestId) {
                    matchingRequest = request;
                    break;
                }
            }
            if (matchingRequest != null) {
                this.mTransferRequests.remove(matchingRequest);
                onTransferFailed(matchingRequest.mOldSessionInfo, matchingRequest.mTargetRoute);
            } else {
                MediaRouter2.this.notifyRequestFailed(reason);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onInvalidateInstanceOnHandler() {
            Log.w(
                    MediaRouter2.TAG,
                    "MEDIA_ROUTING_CONTROL has been revoked for this package. Invalidating"
                            + " instance.");
            synchronized (MediaRouter2.sSystemRouterLock) {
                PackageNameUserHandlePair key =
                        new PackageNameUserHandlePair(this.mClientPackageName, this.mClientUser);
                MediaRouter2.sAppToProxyRouterMap.remove(key);
            }
            synchronized (MediaRouter2.this.mLock) {
                for (InstanceInvalidatedCallbackRecord record :
                        this.mInstanceInvalidatedCallbackRecords) {
                    record.executor.execute(record.runnable);
                }
            }
            MediaRouter2.this.mRouteCallbackRecords.clear();
            MediaRouter2.this.mControllerCallbackRecords.clear();
            MediaRouter2.this.mTransferCallbackRecords.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        class Client extends IMediaRouter2Manager.Stub {
            private Client() {}

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionCreated(int requestId, RoutingSessionInfo routingSessionInfo) {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new TriConsumer() { // from class:
                                    // android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda2
                                    @Override // com.android.internal.util.function.TriConsumer
                                    public final void accept(Object obj, Object obj2, Object obj3) {
                                        ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                                .onSessionCreatedOnHandler(
                                                        ((Integer) obj2).intValue(),
                                                        (RoutingSessionInfo) obj3);
                                    }
                                },
                                ProxyMediaRouter2Impl.this,
                                Integer.valueOf(requestId),
                                routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionUpdated(RoutingSessionInfo routingSessionInfo) {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new BiConsumer() { // from class:
                                    // android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda1
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                                .onSessionUpdatedOnHandler(
                                                        (RoutingSessionInfo) obj2);
                                    }
                                },
                                ProxyMediaRouter2Impl.this,
                                routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new BiConsumer() { // from class:
                                    // android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda5
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                                .onSessionReleasedOnHandler(
                                                        (RoutingSessionInfo) obj2);
                                    }
                                },
                                ProxyMediaRouter2Impl.this,
                                routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyDiscoveryPreferenceChanged(
                    String packageName, RouteDiscoveryPreference routeDiscoveryPreference) {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new TriConsumer() { // from class:
                                    // android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda6
                                    @Override // com.android.internal.util.function.TriConsumer
                                    public final void accept(Object obj, Object obj2, Object obj3) {
                                        ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                                .onDiscoveryPreferenceChangedOnHandler(
                                                        (String) obj2,
                                                        (RouteDiscoveryPreference) obj3);
                                    }
                                },
                                ProxyMediaRouter2Impl.this,
                                packageName,
                                routeDiscoveryPreference));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRouteListingPreferenceChange(
                    String packageName, RouteListingPreference routeListingPreference) {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new TriConsumer() { // from class:
                                    // android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda4
                                    @Override // com.android.internal.util.function.TriConsumer
                                    public final void accept(Object obj, Object obj2, Object obj3) {
                                        ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                                .onRouteListingPreferenceChangedOnHandler(
                                                        (String) obj2,
                                                        (RouteListingPreference) obj3);
                                    }
                                },
                                ProxyMediaRouter2Impl.this,
                                packageName,
                                routeListingPreference));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRoutesUpdated(List<MediaRoute2Info> routes) {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda3(),
                                MediaRouter2.this,
                                routes));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRequestFailed(int requestId, int reason) {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new TriConsumer() { // from class:
                                    // android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda3
                                    @Override // com.android.internal.util.function.TriConsumer
                                    public final void accept(Object obj, Object obj2, Object obj3) {
                                        ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                                .onRequestFailedOnHandler(
                                                        ((Integer) obj2).intValue(),
                                                        ((Integer) obj3).intValue());
                                    }
                                },
                                ProxyMediaRouter2Impl.this,
                                Integer.valueOf(requestId),
                                Integer.valueOf(reason)));
            }

            @Override // android.media.IMediaRouter2Manager
            public void invalidateInstance() {
                MediaRouter2.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new Consumer() { // from class:
                                    // android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ((MediaRouter2.ProxyMediaRouter2Impl) obj)
                                                .onInvalidateInstanceOnHandler();
                                    }
                                },
                                ProxyMediaRouter2Impl.this));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LocalMediaRouter2Impl implements MediaRouter2Impl {
        private final String mPackageName;

        LocalMediaRouter2Impl(String packageName) {
            this.mPackageName = packageName;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void startScan() {}

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stopScan() {}

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void updateScanningState(int scanningState) throws RemoteException {
            if (scanningState != 0) {
                registerRouterStubIfNeededLocked();
            }
            MediaRouter2.this.mMediaRouterService.updateScanningStateWithRouter2(
                    MediaRouter2.this.mStub, scanningState);
            if (scanningState == 0) {
                unregisterRouterStubIfNeededLocked(true);
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getClientPackageName() {
            return null;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getPackageName() {
            return this.mPackageName;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RoutingSessionInfo getSystemSessionInfo() {
            try {
                RoutingSessionInfo currentSystemSessionInfo =
                        MediaRouter2.ensureClientPackageNameForSystemSession(
                                MediaRouter2.this.mMediaRouterService.getSystemSessionInfo(),
                                MediaRouter2.this.mContext.getPackageName());
                return currentSystemSessionInfo;
            } catch (RemoteException ex) {
                ex.rethrowFromSystemServer();
                return null;
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RouteCallbackRecord createRouteCallbackRecord(
                Executor executor,
                RouteCallback routeCallback,
                RouteDiscoveryPreference preference) {
            return new RouteCallbackRecord(executor, routeCallback, preference);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void registerRouteCallback() {
            synchronized (MediaRouter2.this.mLock) {
                try {
                    registerRouterStubIfNeededLocked();
                    if (MediaRouter2.this.updateDiscoveryPreferenceIfNeededLocked()) {
                        MediaRouter2.this.mMediaRouterService.setDiscoveryRequestWithRouter2(
                                MediaRouter2.this.mStub, MediaRouter2.this.mDiscoveryPreference);
                    }
                } catch (RemoteException ex) {
                    ex.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void unregisterRouteCallback() {
            synchronized (MediaRouter2.this.mLock) {
                if (MediaRouter2.this.mStub == null) {
                    return;
                }
                try {
                    if (MediaRouter2.this.updateDiscoveryPreferenceIfNeededLocked()) {
                        MediaRouter2.this.mMediaRouterService.setDiscoveryRequestWithRouter2(
                                MediaRouter2.this.mStub, MediaRouter2.this.mDiscoveryPreference);
                    }
                    unregisterRouterStubIfNeededLocked(false);
                } catch (RemoteException ex) {
                    Log.e(
                            MediaRouter2.TAG,
                            "unregisterRouteCallback: Unable to set discovery request.",
                            ex);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteListingPreference(RouteListingPreference preference) {
            synchronized (MediaRouter2.this.mLock) {
                if (Objects.equals(MediaRouter2.this.mRouteListingPreference, preference)) {
                    return;
                }
                MediaRouter2.this.mRouteListingPreference = preference;
                try {
                    registerRouterStubIfNeededLocked();
                    MediaRouter2.this.mMediaRouterService.setRouteListingPreference(
                            MediaRouter2.this.mStub, MediaRouter2.this.mRouteListingPreference);
                } catch (RemoteException ex) {
                    ex.rethrowFromSystemServer();
                }
                MediaRouter2.this.notifyRouteListingPreferenceUpdated(preference);
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean showSystemOutputSwitcher() {
            boolean showMediaOutputSwitcherWithRouter2;
            synchronized (MediaRouter2.this.mLock) {
                try {
                    try {
                        showMediaOutputSwitcherWithRouter2 =
                                MediaRouter2.this.mMediaRouterService
                                        .showMediaOutputSwitcherWithRouter2(this.mPackageName);
                    } catch (RemoteException ex) {
                        ex.rethrowFromSystemServer();
                        return false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return showMediaOutputSwitcherWithRouter2;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<MediaRoute2Info> getAllRoutes() {
            return Collections.emptyList();
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setOnGetControllerHintsListener(OnGetControllerHintsListener listener) {
            MediaRouter2.this.mOnGetControllerHintsListener = listener;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transferTo(MediaRoute2Info route) {
            boolean routeFound;
            Log.v(MediaRouter2.TAG, "Transferring to route: " + route);
            synchronized (MediaRouter2.this.mLock) {
                routeFound = MediaRouter2.this.mRoutes.containsKey(route.getId());
            }
            if (!routeFound) {
                MediaRouter2.this.notifyTransferFailure(route);
                return;
            }
            RoutingController controller = MediaRouter2.this.getCurrentController();
            if (!controller.tryTransferWithinProvider(route)) {
                MediaRouter2.this.requestCreateController(controller, route, 0L);
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stop() {
            MediaRouter2.this.getCurrentController().release();
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transfer(RoutingSessionInfo sessionInfo, MediaRoute2Info route) {}

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<RoutingController> getControllers() {
            List<RoutingController> result = new ArrayList<>();
            result.add(0, MediaRouter2.this.mSystemController);
            synchronized (MediaRouter2.this.mLock) {
                result.addAll(MediaRouter2.this.mNonSystemRoutingControllers.values());
            }
            return result;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteVolume(MediaRoute2Info route, int volume) {
            throw new UnsupportedOperationException(
                    "setRouteVolume is only supported by proxy routers. See javadoc.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setSessionVolume(int volume, RoutingSessionInfo sessionInfo) {
            MediaRouter2Stub stub;
            synchronized (MediaRouter2.this.mLock) {
                stub = MediaRouter2.this.mStub;
            }
            if (stub != null) {
                try {
                    MediaRouter2.this.mMediaRouterService.setSessionVolumeWithRouter2(
                            stub, sessionInfo.getId(), volume);
                } catch (RemoteException ex) {
                    Log.e(MediaRouter2.TAG, "setVolume: Failed to deliver request.", ex);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<MediaRoute2Info> filterRoutesWithIndividualPreference(
                List<MediaRoute2Info> routes, RouteDiscoveryPreference discoveryPreference) {
            List<MediaRoute2Info> filteredRoutes = new ArrayList<>();
            for (MediaRoute2Info route : routes) {
                if (route.hasAnyFeatures(discoveryPreference.getPreferredFeatures())
                        && (discoveryPreference.getAllowedPackages().isEmpty()
                                || (route.getPackageName() != null
                                        && discoveryPreference
                                                .getAllowedPackages()
                                                .contains(route.getPackageName())))) {
                    filteredRoutes.add(route);
                }
            }
            return filteredRoutes;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void selectRoute(MediaRoute2Info route, RoutingSessionInfo sessionInfo) {
            MediaRouter2Stub stub;
            synchronized (MediaRouter2.this.mLock) {
                stub = MediaRouter2.this.mStub;
            }
            if (stub != null) {
                try {
                    MediaRouter2.this.mMediaRouterService.selectRouteWithRouter2(
                            stub, sessionInfo.getId(), route);
                } catch (RemoteException ex) {
                    Log.e(MediaRouter2.TAG, "Unable to select route for session.", ex);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void deselectRoute(MediaRoute2Info route, RoutingSessionInfo sessionInfo) {
            MediaRouter2Stub stub;
            synchronized (MediaRouter2.this.mLock) {
                stub = MediaRouter2.this.mStub;
            }
            if (stub != null) {
                try {
                    MediaRouter2.this.mMediaRouterService.deselectRouteWithRouter2(
                            stub, sessionInfo.getId(), route);
                } catch (RemoteException ex) {
                    Log.e(MediaRouter2.TAG, "Unable to deselect route from session.", ex);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void releaseSession(
                boolean shouldReleaseSession,
                boolean shouldNotifyStop,
                RoutingController controller) {
            synchronized (MediaRouter2.this.mLock) {
                MediaRouter2.this.mNonSystemRoutingControllers.remove(
                        controller.getId(), controller);
                if (shouldReleaseSession && MediaRouter2.this.mStub != null) {
                    try {
                        MediaRouter2.this.mMediaRouterService.releaseSessionWithRouter2(
                                MediaRouter2.this.mStub, controller.getId());
                    } catch (RemoteException ex) {
                        ex.rethrowFromSystemServer();
                    }
                }
                if (shouldNotifyStop) {
                    MediaRouter2.this.mHandler.sendMessage(
                            PooledLambda.obtainMessage(
                                    new BiConsumer() { // from class:
                                        // android.media.MediaRouter2$LocalMediaRouter2Impl$$ExternalSyntheticLambda0
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            ((MediaRouter2) obj)
                                                    .notifyStop(
                                                            (MediaRouter2.RoutingController) obj2);
                                        }
                                    },
                                    MediaRouter2.this,
                                    controller));
                }
                try {
                    unregisterRouterStubIfNeededLocked(false);
                } catch (RemoteException ex2) {
                    ex2.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean wasTransferredBySelf(RoutingSessionInfo sessionInfo) {
            UserHandle transferInitiatorUserHandle = sessionInfo.getTransferInitiatorUserHandle();
            String transferInitiatorPackageName = sessionInfo.getTransferInitiatorPackageName();
            return Objects.equals(Process.myUserHandle(), transferInitiatorUserHandle)
                    && Objects.equals(
                            MediaRouter2.this.mContext.getPackageName(),
                            transferInitiatorPackageName);
        }

        private void registerRouterStubIfNeededLocked() throws RemoteException {
            if (MediaRouter2.this.mStub == null) {
                MediaRouter2Stub stub = MediaRouter2.this.new MediaRouter2Stub();
                MediaRouter2.this.mMediaRouterService.registerRouter2(stub, this.mPackageName);
                MediaRouter2.this.mStub = stub;
            }
        }

        private void unregisterRouterStubIfNeededLocked(boolean isScanningStopping)
                throws RemoteException {
            if (MediaRouter2.this.mStub != null
                    && MediaRouter2.this.mRouteCallbackRecords.isEmpty()
                    && MediaRouter2.this.mNonSystemRoutingControllers.isEmpty()) {
                if (MediaRouter2.this.mScanRequestsMap.size() == 0 || isScanningStopping) {
                    MediaRouter2.this.mMediaRouterService.unregisterRouter2(
                            MediaRouter2.this.mStub);
                    MediaRouter2.this.mStub = null;
                }
            }
        }
    }
}
