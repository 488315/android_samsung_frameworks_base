package android.media;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.IMediaRouter2;
import android.media.IMediaRouter2Manager;
import android.media.IMediaRouterService;
import android.media.MediaRouter2;
import android.media.MediaRouter2Manager;
import android.media.RouteDiscoveryPreference;
import android.media.RoutingSessionInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private final CopyOnWriteArrayList<DeviceSuggestionsCallbackRecord> mDeviceSuggestionsCallbackRecords;
    private RouteDiscoveryPreference mDiscoveryPreference;
    private volatile List<MediaRoute2Info> mFilteredRoutes;
    private final Handler mHandler;
    private final MediaRouter2Impl mImpl;
    private final CopyOnWriteArrayList<RouteListingPreferenceCallbackRecord> mListingPreferenceCallbackRecords;
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
    private Map<String, List<SuggestedDeviceInfo>> mSuggestedDeviceInfo;
    private final RoutingController mSystemController;
    private final CopyOnWriteArrayList<TransferCallbackRecord> mTransferCallbackRecords;
    private static final String TAG = "MR2";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final Object sSystemRouterLock = new Object();
    private static final Object sRouterLock = new Object();
    private static final Map<PackageNameUserHandlePair, MediaRouter2> sAppToProxyRouterMap = new ArrayMap();

    public static abstract class ControllerCallback {
        public void onControllerUpdated(RoutingController routingController) {
        }
    }

    public interface DeviceSuggestionsCallback {
        void onSuggestionUpdated(String str, List<SuggestedDeviceInfo> list);
    }

    private interface MediaRouter2Impl {
        RouteCallbackRecord createRouteCallbackRecord(Executor executor, RouteCallback routeCallback, RouteDiscoveryPreference routeDiscoveryPreference);

        void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo);

        List<MediaRoute2Info> filterRoutesWithIndividualPreference(List<MediaRoute2Info> list, RouteDiscoveryPreference routeDiscoveryPreference);

        List<MediaRoute2Info> getAllRoutes();

        String getClientPackageName();

        List<RoutingController> getControllers();

        Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestions();

        String getPackageName();

        RoutingSessionInfo getSystemSessionInfo();

        void registerRouteCallback();

        void releaseSession(boolean z, boolean z2, RoutingController routingController);

        void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo);

        void setDeviceSuggestions(List<SuggestedDeviceInfo> list);

        void setOnGetControllerHintsListener(OnGetControllerHintsListener onGetControllerHintsListener);

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

    public static abstract class RouteCallback {
        @SystemApi
        public void onPreferredFeaturesChanged(List<String> list) {
        }

        @Deprecated
        public void onRoutesAdded(List<MediaRoute2Info> list) {
        }

        @Deprecated
        public void onRoutesChanged(List<MediaRoute2Info> list) {
        }

        @Deprecated
        public void onRoutesRemoved(List<MediaRoute2Info> list) {
        }

        public void onRoutesUpdated(List<MediaRoute2Info> list) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanningState {
    }

    public static abstract class TransferCallback {
        public void onRequestFailed(int i) {
        }

        public void onStop(RoutingController routingController) {
        }

        public void onTransfer(RoutingController routingController, RoutingController routingController2) {
        }

        public void onTransferFailure(MediaRoute2Info mediaRoute2Info) {
        }
    }

    private static final class PackageNameUserHandlePair extends Record {
        private final String packageName;
        private final UserHandle user;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof PackageNameUserHandlePair)) {
                return false;
            }
            PackageNameUserHandlePair packageNameUserHandlePair = (PackageNameUserHandlePair) obj;
            return Objects.equals(this.packageName, packageNameUserHandlePair.packageName) && Objects.equals(this.user, packageNameUserHandlePair.user);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.packageName, this.user};
        }

        private PackageNameUserHandlePair(String packageName, UserHandle user) {
            this.packageName = packageName;
            this.user = user;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.packageName, this.user);
        }

        public String packageName() {
            return this.packageName;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), PackageNameUserHandlePair.class, "packageName;user");
        }

        public UserHandle user() {
            return this.user;
        }
    }

    private static final class InstanceInvalidatedCallbackRecord extends Record {
        private final Executor executor;
        private final Runnable runnable;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof InstanceInvalidatedCallbackRecord)) {
                return false;
            }
            InstanceInvalidatedCallbackRecord instanceInvalidatedCallbackRecord = (InstanceInvalidatedCallbackRecord) obj;
            return Objects.equals(this.executor, instanceInvalidatedCallbackRecord.executor) && Objects.equals(this.runnable, instanceInvalidatedCallbackRecord.runnable);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.executor, this.runnable};
        }

        private InstanceInvalidatedCallbackRecord(Executor executor, Runnable runnable) {
            this.executor = executor;
            this.runnable = runnable;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public Executor executor() {
            return this.executor;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.executor, this.runnable);
        }

        public Runnable runnable() {
            return this.runnable;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), InstanceInvalidatedCallbackRecord.class, "executor;runnable");
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
    public static MediaRouter2 getInstance(Context context, String str) {
        try {
            return findOrCreateProxyInstanceForCallingUser(context, str, context.getUser(), null, null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to create proxy router for package '" + str + "'", e);
            return null;
        }
    }

    public static MediaRouter2 getInstance(Context context, String str, Executor executor, Runnable runnable) {
        Objects.requireNonNull(executor, "Executor must not be null");
        Objects.requireNonNull(runnable, "onInstanceInvalidatedListener must not be null.");
        return findOrCreateProxyInstanceForCallingUser(context, str, context.getUser(), executor, runnable);
    }

    public static MediaRouter2 getInstance(Context context, String str, UserHandle userHandle) {
        return findOrCreateProxyInstanceForCallingUser(context, str, userHandle, null, null);
    }

    private static MediaRouter2 findOrCreateProxyInstanceForCallingUser(Context context, String str, UserHandle userHandle, Executor executor, Runnable runnable) {
        MediaRouter2 mediaRouter2;
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(userHandle, "user must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("clientPackageName must not be null or empty");
        }
        if ((executor == null || runnable == null) && checkCallerHasOnlyRevocablePermissions(context)) {
            throw new IllegalStateException("Use getInstance(Context, String, Executor, Runnable) to obtain a proxy MediaRouter2 instance.");
        }
        PackageNameUserHandlePair packageNameUserHandlePair = new PackageNameUserHandlePair(str, userHandle);
        synchronized (sSystemRouterLock) {
            Map<PackageNameUserHandlePair, MediaRouter2> map = sAppToProxyRouterMap;
            mediaRouter2 = map.get(packageNameUserHandlePair);
            if (mediaRouter2 == null) {
                mediaRouter2 = new MediaRouter2(context, Looper.getMainLooper(), str, userHandle);
                ((ProxyMediaRouter2Impl) mediaRouter2.mImpl).registerProxyRouter();
                map.put(packageNameUserHandlePair, mediaRouter2);
            }
            ((ProxyMediaRouter2Impl) mediaRouter2.mImpl).registerInstanceInvalidatedCallback(executor, runnable);
        }
        return mediaRouter2;
    }

    private static boolean checkCallerHasOnlyRevocablePermissions(Context context) {
        return ((context.checkSelfPermission(Manifest.permission.MEDIA_CONTENT_CONTROL) == 0) || (context.checkSelfPermission(Manifest.permission.MEDIA_ROUTING_CONTROL) == 0) || !(((AppOpsManager) context.getSystemService(AppOpsManager.class)).unsafeCheckOp(AppOpsManager.OPSTR_MEDIA_ROUTING_CONTROL, context.getApplicationInfo().uid, context.getOpPackageName()) == 0)) ? false : true;
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
        Objects.requireNonNull(scanRequest, "scanRequest must not be null.");
        ScanToken scanToken = new ScanToken(this.mNextRequestId.getAndIncrement());
        synchronized (this.mLock) {
            if (this.mScreenOffScanRequestCount == 0 && (scanRequest.isScreenOffScan() || this.mScreenOnScanRequestCount == 0)) {
                try {
                    this.mImpl.updateScanningState(scanRequest.isScreenOffScan() ? 2 : 1);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            if (scanRequest.isScreenOffScan()) {
                this.mScreenOffScanRequestCount++;
            } else {
                this.mScreenOnScanRequestCount++;
            }
            this.mScanRequestsMap.put(scanToken.mId, scanRequest);
        }
        return scanToken;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x004c A[Catch: all -> 0x006a, TryCatch #1 {, blocks: (B:4:0x0008, B:6:0x0016, B:8:0x001d, B:15:0x002a, B:17:0x0030, B:20:0x0035, B:21:0x003b, B:24:0x0043, B:25:0x0046, B:27:0x004c, B:29:0x0057, B:30:0x0060, B:28:0x0052, B:11:0x0022, B:13:0x0026, B:32:0x0062, B:33:0x0069), top: B:40:0x0008, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052 A[Catch: all -> 0x006a, TryCatch #1 {, blocks: (B:4:0x0008, B:6:0x0016, B:8:0x001d, B:15:0x002a, B:17:0x0030, B:20:0x0035, B:21:0x003b, B:24:0x0043, B:25:0x0046, B:27:0x004c, B:29:0x0057, B:30:0x0060, B:28:0x0052, B:11:0x0022, B:13:0x0026, B:32:0x0062, B:33:0x0069), top: B:40:0x0008, inners: #0 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0043 -> B:37:0x0046). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void cancelScanRequest(ScanToken scanToken) {
        Objects.requireNonNull(scanToken, "token must not be null");
        synchronized (this.mLock) {
            ScanRequest scanRequest = this.mScanRequestsMap.get(scanToken.mId);
            if (scanRequest == null) {
                throw new IllegalArgumentException("The token does not match any active scan request");
            }
            if (scanRequest.isScreenOffScan()) {
                if (this.mScreenOffScanRequestCount == 1) {
                    try {
                        if (scanRequest.isScreenOffScan() || this.mScreenOnScanRequestCount == 0) {
                            this.mImpl.updateScanningState(0);
                        } else {
                            this.mImpl.updateScanningState(1);
                        }
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
                if (!scanRequest.isScreenOffScan()) {
                    this.mScreenOffScanRequestCount--;
                } else {
                    this.mScreenOnScanRequestCount--;
                }
                this.mScanRequestsMap.remove(scanToken.mId);
            } else {
                if (this.mScreenOnScanRequestCount == 1 && this.mScreenOffScanRequestCount == 0) {
                    if (scanRequest.isScreenOffScan()) {
                    }
                    this.mImpl.updateScanningState(0);
                }
                if (!scanRequest.isScreenOffScan()) {
                }
                this.mScanRequestsMap.remove(scanToken.mId);
            }
        }
    }

    private MediaRouter2(Context context) {
        this.mLock = new Object();
        this.mRouteCallbackRecords = new CopyOnWriteArrayList<>();
        this.mListingPreferenceCallbackRecords = new CopyOnWriteArrayList<>();
        this.mTransferCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCallbackRecords = new CopyOnWriteArrayList<>();
        this.mDeviceSuggestionsCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCreationRequests = new CopyOnWriteArrayList<>();
        this.mRoutes = new ArrayMap();
        this.mNonSystemRoutingControllers = new ArrayMap();
        this.mScreenOffScanRequestCount = 0;
        this.mScreenOnScanRequestCount = 0;
        this.mScanRequestsMap = new SparseArray<>();
        this.mNextRequestId = new AtomicInteger(1);
        this.mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
        this.mSuggestedDeviceInfo = new HashMap();
        this.mPreviousFilteredRoutes = new ArrayMap<>();
        this.mPreviousUnfilteredRoutes = new ArrayMap();
        this.mFilteredRoutes = Collections.EMPTY_LIST;
        this.mContext = context;
        this.mMediaRouterService = IMediaRouterService.Stub.asInterface(ServiceManager.getService(Context.MEDIA_ROUTER_SERVICE));
        LocalMediaRouter2Impl localMediaRouter2Impl = new LocalMediaRouter2Impl(context.getPackageName());
        this.mImpl = localMediaRouter2Impl;
        this.mHandler = new Handler(Looper.getMainLooper());
        loadSystemRoutes(false);
        RoutingSessionInfo systemSessionInfo = localMediaRouter2Impl.getSystemSessionInfo();
        if (systemSessionInfo == null) {
            throw new RuntimeException("Null currentSystemSessionInfo. Something is wrong.");
        }
        this.mSystemController = new SystemRoutingController(this, systemSessionInfo);
    }

    private MediaRouter2(Context context, Looper looper, String str, UserHandle userHandle) {
        this.mLock = new Object();
        this.mRouteCallbackRecords = new CopyOnWriteArrayList<>();
        this.mListingPreferenceCallbackRecords = new CopyOnWriteArrayList<>();
        this.mTransferCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCallbackRecords = new CopyOnWriteArrayList<>();
        this.mDeviceSuggestionsCallbackRecords = new CopyOnWriteArrayList<>();
        this.mControllerCreationRequests = new CopyOnWriteArrayList<>();
        this.mRoutes = new ArrayMap();
        this.mNonSystemRoutingControllers = new ArrayMap();
        this.mScreenOffScanRequestCount = 0;
        this.mScreenOnScanRequestCount = 0;
        this.mScanRequestsMap = new SparseArray<>();
        this.mNextRequestId = new AtomicInteger(1);
        this.mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
        this.mSuggestedDeviceInfo = new HashMap();
        this.mPreviousFilteredRoutes = new ArrayMap<>();
        this.mPreviousUnfilteredRoutes = new ArrayMap();
        this.mFilteredRoutes = Collections.EMPTY_LIST;
        this.mContext = context;
        this.mHandler = new Handler(looper);
        IMediaRouterService iMediaRouterServiceAsInterface = IMediaRouterService.Stub.asInterface(ServiceManager.getService(Context.MEDIA_ROUTER_SERVICE));
        this.mMediaRouterService = iMediaRouterServiceAsInterface;
        loadSystemRoutes(true);
        this.mSystemController = new SystemRoutingController(this, ProxyMediaRouter2Impl.getSystemSessionInfoImpl(iMediaRouterServiceAsInterface, context.getPackageName(), str));
        this.mImpl = new ProxyMediaRouter2Impl(context, str, userHandle);
    }

    private void loadSystemRoutes(boolean z) {
        List<MediaRoute2Info> systemRoutes;
        try {
            systemRoutes = this.mMediaRouterService.getSystemRoutes(this.mContext.getPackageName(), z);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            systemRoutes = null;
        }
        if (systemRoutes == null || systemRoutes.isEmpty()) {
            throw new RuntimeException("Null or empty currentSystemRoutes. Something is wrong.");
        }
        for (MediaRoute2Info mediaRoute2Info : systemRoutes) {
            this.mRoutes.put(mediaRoute2Info.getId(), mediaRoute2Info);
        }
    }

    @SystemApi
    public String getClientPackageName() {
        return this.mImpl.getClientPackageName();
    }

    public void registerRouteCallback(Executor executor, RouteCallback routeCallback, RouteDiscoveryPreference routeDiscoveryPreference) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(routeCallback, "callback must not be null");
        Objects.requireNonNull(routeDiscoveryPreference, "preference must not be null");
        RouteCallbackRecord routeCallbackRecordCreateRouteCallbackRecord = this.mImpl.createRouteCallbackRecord(executor, routeCallback, routeDiscoveryPreference);
        this.mRouteCallbackRecords.remove(routeCallbackRecordCreateRouteCallbackRecord);
        this.mRouteCallbackRecords.addIfAbsent(routeCallbackRecordCreateRouteCallbackRecord);
        this.mImpl.registerRouteCallback();
    }

    public void unregisterRouteCallback(RouteCallback routeCallback) {
        Objects.requireNonNull(routeCallback, "callback must not be null");
        if (!this.mRouteCallbackRecords.remove(new RouteCallbackRecord(null, routeCallback, null))) {
            Log.w(TAG, "unregisterRouteCallback: Ignoring unknown callback");
        } else {
            this.mImpl.unregisterRouteCallback();
        }
    }

    public void registerRouteListingPreferenceUpdatedCallback(Executor executor, Consumer<RouteListingPreference> consumer) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(consumer, "callback must not be null");
        RouteListingPreferenceCallbackRecord routeListingPreferenceCallbackRecord = new RouteListingPreferenceCallbackRecord(executor, consumer);
        this.mListingPreferenceCallbackRecords.remove(routeListingPreferenceCallbackRecord);
        this.mListingPreferenceCallbackRecords.add(routeListingPreferenceCallbackRecord);
    }

    public void registerDeviceSuggestionsCallback(Executor executor, DeviceSuggestionsCallback deviceSuggestionsCallback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(deviceSuggestionsCallback, "callback must not be null");
        DeviceSuggestionsCallbackRecord deviceSuggestionsCallbackRecord = new DeviceSuggestionsCallbackRecord(executor, deviceSuggestionsCallback);
        this.mDeviceSuggestionsCallbackRecords.remove(deviceSuggestionsCallbackRecord);
        this.mDeviceSuggestionsCallbackRecords.add(deviceSuggestionsCallbackRecord);
    }

    public void unregisterRouteListingPreferenceUpdatedCallback(Consumer<RouteListingPreference> consumer) {
        Objects.requireNonNull(consumer, "callback must not be null");
        if (this.mListingPreferenceCallbackRecords.remove(new RouteListingPreferenceCallbackRecord(null, consumer))) {
            return;
        }
        Log.w(TAG, "unregisterRouteListingPreferenceUpdatedCallback: Ignoring an unknown callback");
    }

    public void unregisterDeviceSuggestionsCallback(DeviceSuggestionsCallback deviceSuggestionsCallback) {
        Objects.requireNonNull(deviceSuggestionsCallback, "callback must not be null");
        if (this.mDeviceSuggestionsCallbackRecords.remove(new DeviceSuggestionsCallbackRecord(null, deviceSuggestionsCallback))) {
            return;
        }
        Log.w(TAG, "unregisterDeviceSuggestionsCallback: Ignoring an unknown callback");
    }

    public boolean showSystemOutputSwitcher() {
        return this.mImpl.showSystemOutputSwitcher();
    }

    public void setRouteListingPreference(RouteListingPreference routeListingPreference) {
        this.mImpl.setRouteListingPreference(routeListingPreference);
    }

    public void setDeviceSuggestions(List<SuggestedDeviceInfo> list) {
        this.mImpl.setDeviceSuggestions(list);
    }

    public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestions() {
        return this.mImpl.getDeviceSuggestions();
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
        RouteDiscoveryPreference routeDiscoveryPreferenceBuild = new RouteDiscoveryPreference.Builder((Collection<RouteDiscoveryPreference>) this.mRouteCallbackRecords.stream().map(new Function() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MediaRouter2.RouteCallbackRecord) obj).mPreference;
            }
        }).collect(Collectors.toList())).build();
        if (Objects.equals(this.mDiscoveryPreference, routeDiscoveryPreferenceBuild)) {
            return false;
        }
        this.mDiscoveryPreference = routeDiscoveryPreferenceBuild;
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

    public void registerTransferCallback(Executor executor, TransferCallback transferCallback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(transferCallback, "callback must not be null");
        if (this.mTransferCallbackRecords.addIfAbsent(new TransferCallbackRecord(executor, transferCallback))) {
            return;
        }
        Log.w(TAG, "registerTransferCallback: Ignoring the same callback");
    }

    public void unregisterTransferCallback(TransferCallback transferCallback) {
        Objects.requireNonNull(transferCallback, "callback must not be null");
        if (this.mTransferCallbackRecords.remove(new TransferCallbackRecord(null, transferCallback))) {
            return;
        }
        Log.w(TAG, "unregisterTransferCallback: Ignoring an unknown callback");
    }

    public void registerControllerCallback(Executor executor, ControllerCallback controllerCallback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(controllerCallback, "callback must not be null");
        if (this.mControllerCallbackRecords.addIfAbsent(new ControllerCallbackRecord(executor, controllerCallback))) {
            return;
        }
        Log.w(TAG, "registerControllerCallback: Ignoring the same callback");
    }

    public void unregisterControllerCallback(ControllerCallback controllerCallback) {
        Objects.requireNonNull(controllerCallback, "callback must not be null");
        if (this.mControllerCallbackRecords.remove(new ControllerCallbackRecord(null, controllerCallback))) {
            return;
        }
        Log.w(TAG, "unregisterControllerCallback: Ignoring an unknown callback");
    }

    public void setOnGetControllerHintsListener(OnGetControllerHintsListener onGetControllerHintsListener) {
        this.mImpl.setOnGetControllerHintsListener(onGetControllerHintsListener);
    }

    public void transferTo(MediaRoute2Info mediaRoute2Info) {
        this.mImpl.transferTo(mediaRoute2Info);
    }

    public void stop() {
        this.mImpl.stop();
    }

    @SystemApi
    public void transfer(RoutingController routingController, MediaRoute2Info mediaRoute2Info) {
        this.mImpl.transfer(routingController.getRoutingSessionInfo(), mediaRoute2Info);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void requestCreateController(RoutingController routingController, MediaRoute2Info mediaRoute2Info, long j) {
        Bundle bundleOnGetControllerHints;
        Bundle bundle;
        MediaRouter2Stub mediaRouter2Stub;
        int andIncrement = this.mNextRequestId.getAndIncrement();
        ControllerCreationRequest controllerCreationRequest = new ControllerCreationRequest(andIncrement, j, mediaRoute2Info, routingController);
        this.mControllerCreationRequests.add(controllerCreationRequest);
        OnGetControllerHintsListener onGetControllerHintsListener = this.mOnGetControllerHintsListener;
        if (onGetControllerHintsListener != null) {
            bundleOnGetControllerHints = onGetControllerHintsListener.onGetControllerHints(mediaRoute2Info);
            if (bundleOnGetControllerHints != null) {
                bundle = new Bundle(bundleOnGetControllerHints);
            }
            synchronized (this.mLock) {
                mediaRouter2Stub = this.mStub;
            }
            if (mediaRouter2Stub != null) {
                try {
                    this.mMediaRouterService.requestCreateSessionWithRouter2(mediaRouter2Stub, andIncrement, j, routingController.getRoutingSessionInfo(), mediaRoute2Info, bundle);
                    return;
                } catch (RemoteException e) {
                    Log.e(TAG, "createControllerForTransfer: Failed to request for creating a controller.", e);
                    this.mControllerCreationRequests.remove(controllerCreationRequest);
                    if (j == 0) {
                        notifyTransferFailure(mediaRoute2Info);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        bundleOnGetControllerHints = null;
        bundle = bundleOnGetControllerHints;
        synchronized (this.mLock) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RoutingController getCurrentController() {
        return getControllers().get(r1.size() - 1);
    }

    public RoutingController getSystemController() {
        return this.mSystemController;
    }

    public RoutingController getController(String str) {
        Objects.requireNonNull(str, "id must not be null");
        for (RoutingController routingController : getControllers()) {
            if (TextUtils.equals(str, routingController.getId())) {
                return routingController;
            }
        }
        return null;
    }

    public List<RoutingController> getControllers() {
        return this.mImpl.getControllers();
    }

    public void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i) {
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        this.mImpl.setRouteVolume(mediaRoute2Info, i);
    }

    void syncRoutesOnHandler(List<MediaRoute2Info> list, RoutingSessionInfo routingSessionInfo) {
        if (list == null || list.isEmpty() || routingSessionInfo == null) {
            Log.e(TAG, "syncRoutesOnHandler: Received wrong data. currentRoutes=" + list + ", currentSystemSessionInfo=" + routingSessionInfo);
            return;
        }
        updateRoutesOnHandler(list);
        RoutingSessionInfo routingSessionInfo2 = this.mSystemController.getRoutingSessionInfo();
        this.mSystemController.setRoutingSessionInfo(ensureClientPackageNameForSystemSession(routingSessionInfo, this.mContext.getPackageName()));
        if (routingSessionInfo2.equals(routingSessionInfo)) {
            return;
        }
        notifyControllerUpdated(this.mSystemController);
    }

    void dispatchFilteredRoutesUpdatedOnHandler(List<MediaRoute2Info> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Set set = (Set) list.stream().map(new MediaRouter2$$ExternalSyntheticLambda16()).collect(Collectors.toSet());
        for (MediaRoute2Info mediaRoute2Info : list) {
            MediaRoute2Info mediaRoute2Info2 = this.mPreviousFilteredRoutes.get(mediaRoute2Info.getId());
            if (mediaRoute2Info2 == null) {
                arrayList.add(mediaRoute2Info);
            } else if (!mediaRoute2Info2.equals(mediaRoute2Info)) {
                arrayList3.add(mediaRoute2Info);
            }
        }
        for (int i = 0; i < this.mPreviousFilteredRoutes.size(); i++) {
            if (!set.contains(this.mPreviousFilteredRoutes.keyAt(i))) {
                arrayList2.add(this.mPreviousFilteredRoutes.valueAt(i));
            }
        }
        Iterator<MediaRoute2Info> it = arrayList2.iterator();
        while (it.hasNext()) {
            this.mPreviousFilteredRoutes.remove(it.next().getId());
        }
        for (MediaRoute2Info mediaRoute2Info3 : arrayList) {
            this.mPreviousFilteredRoutes.put(mediaRoute2Info3.getId(), mediaRoute2Info3);
        }
        for (MediaRoute2Info mediaRoute2Info4 : arrayList3) {
            this.mPreviousFilteredRoutes.put(mediaRoute2Info4.getId(), mediaRoute2Info4);
        }
        if (!arrayList.isEmpty()) {
            notifyRoutesAdded(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            notifyRoutesRemoved(arrayList2);
        }
        if (!arrayList3.isEmpty()) {
            notifyRoutesChanged(arrayList3);
        }
        if (arrayList.isEmpty() && arrayList2.isEmpty() && arrayList3.isEmpty()) {
            return;
        }
        notifyRoutesUpdated(list);
    }

    void dispatchControllerUpdatedIfNeededOnHandler(Map<String, MediaRoute2Info> map) {
        for (RoutingController routingController : getControllers()) {
            Iterator<String> it = routingController.getRoutingSessionInfo().getSelectedRoutes().iterator();
            while (true) {
                if (it.hasNext()) {
                    String next = it.next();
                    if (map.containsKey(next) && this.mPreviousUnfilteredRoutes.containsKey(next) && !map.get(next).equals(this.mPreviousUnfilteredRoutes.get(next))) {
                        notifyControllerUpdated(routingController);
                        break;
                    }
                }
            }
        }
        this.mPreviousUnfilteredRoutes.clear();
        this.mPreviousUnfilteredRoutes.putAll(map);
    }

    void updateRoutesOnHandler(List<MediaRoute2Info> list) {
        synchronized (this.mLock) {
            this.mRoutes.clear();
            for (MediaRoute2Info mediaRoute2Info : list) {
                this.mRoutes.put(mediaRoute2Info.getId(), mediaRoute2Info);
            }
            updateFilteredRoutesLocked();
        }
    }

    void updateFilteredRoutesLocked() {
        this.mFilteredRoutes = Collections.unmodifiableList(filterRoutesWithCompositePreferenceLocked(List.copyOf(this.mRoutes.values())));
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((MediaRouter2) obj).dispatchFilteredRoutesUpdatedOnHandler((List) obj2);
            }
        }, this, this.mFilteredRoutes));
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((MediaRouter2) obj).dispatchControllerUpdatedIfNeededOnHandler((HashMap) obj2);
            }
        }, this, new HashMap(this.mRoutes)));
    }

    void createControllerOnHandler(int i, RoutingSessionInfo routingSessionInfo) {
        ControllerCreationRequest next;
        Iterator<ControllerCreationRequest> it = this.mControllerCreationRequests.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            } else {
                next = it.next();
                if (next.mRequestId == i) {
                    break;
                }
            }
        }
        if (next == null) {
            Log.w(TAG, "createControllerOnHandler: Ignoring an unknown request.");
            return;
        }
        this.mControllerCreationRequests.remove(next);
        MediaRoute2Info mediaRoute2Info = next.mRoute;
        if (routingSessionInfo == null) {
            notifyTransferFailure(mediaRoute2Info);
            return;
        }
        if (!TextUtils.equals(mediaRoute2Info.getProviderId(), routingSessionInfo.getProviderId())) {
            Log.w(TAG, "The session's provider ID does not match the requested route's. (requested route's providerId=" + mediaRoute2Info.getProviderId() + ", actual providerId=" + routingSessionInfo.getProviderId() + NavigationBarInflaterView.KEY_CODE_END);
            notifyTransferFailure(mediaRoute2Info);
            return;
        }
        RoutingController routingController = next.mOldController;
        if (!routingController.scheduleRelease()) {
            Log.w(TAG, "createControllerOnHandler: Ignoring controller creation for released old controller. oldController=" + routingController);
            if (!routingSessionInfo.isSystemSession()) {
                new RoutingController(routingSessionInfo).release();
            }
            notifyTransferFailure(mediaRoute2Info);
            return;
        }
        notifyTransfer(routingController, addRoutingController(routingSessionInfo));
    }

    private RoutingController addRoutingController(RoutingSessionInfo routingSessionInfo) {
        if (routingSessionInfo.isSystemSession()) {
            this.mSystemController.setRoutingSessionInfo(routingSessionInfo);
            return this.mSystemController;
        }
        RoutingController routingController = new RoutingController(routingSessionInfo);
        synchronized (this.mLock) {
            this.mNonSystemRoutingControllers.put(routingController.getId(), routingController);
        }
        return routingController;
    }

    void updateControllerOnHandler(RoutingSessionInfo routingSessionInfo) {
        if (routingSessionInfo == null) {
            Log.w(TAG, "updateControllerOnHandler: Ignoring null sessionInfo.");
            return;
        }
        RoutingController matchingController = getMatchingController(routingSessionInfo, "updateControllerOnHandler");
        if (matchingController != null) {
            matchingController.setRoutingSessionInfo(routingSessionInfo);
            notifyControllerUpdated(matchingController);
        }
    }

    void releaseControllerOnHandler(RoutingSessionInfo routingSessionInfo) {
        if (routingSessionInfo == null) {
            Log.w(TAG, "releaseControllerOnHandler: Ignoring null sessionInfo.");
            return;
        }
        RoutingController matchingController = getMatchingController(routingSessionInfo, "releaseControllerOnHandler");
        if (matchingController != null) {
            matchingController.releaseInternal(false);
        }
    }

    private RoutingController getMatchingController(RoutingSessionInfo routingSessionInfo, String str) {
        RoutingController routingController;
        if (routingSessionInfo.isSystemSession()) {
            return getSystemController();
        }
        synchronized (this.mLock) {
            routingController = this.mNonSystemRoutingControllers.get(routingSessionInfo.getId());
        }
        if (routingController == null) {
            Log.w(TAG, str + ": Matching controller not found. uniqueSessionId=" + routingSessionInfo.getId());
            return null;
        }
        RoutingSessionInfo routingSessionInfo2 = routingController.getRoutingSessionInfo();
        if (TextUtils.equals(routingSessionInfo2.getProviderId(), routingSessionInfo.getProviderId())) {
            return routingController;
        }
        Log.w(TAG, str + ": Provider IDs are not matched. old=" + routingSessionInfo2.getProviderId() + ", new=" + routingSessionInfo.getProviderId());
        return null;
    }

    void onRequestCreateControllerByManagerOnHandler(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, long j) {
        RoutingController routingController;
        RoutingController systemController;
        Log.i(TAG, TextUtils.formatSimple("requestCreateSessionByManager | requestId: %d, oldSession: %s, route: %s", Long.valueOf(j), routingSessionInfo, mediaRoute2Info));
        String id = routingSessionInfo.getId();
        if (routingSessionInfo.isSystemSession()) {
            systemController = getSystemController();
        } else {
            synchronized (this.mLock) {
                routingController = this.mNonSystemRoutingControllers.get(id);
            }
            systemController = routingController;
        }
        if (systemController == null) {
            Log.w(TAG, TextUtils.formatSimple("Ignoring requestCreateSessionByManager (requestId: %d) because no controller for old session (id: %s) was found.", Long.valueOf(j), id));
        } else {
            requestCreateController(systemController, mediaRoute2Info, j);
        }
    }

    private List<MediaRoute2Info> getSortedRoutes(List<MediaRoute2Info> list, List<String> list2) {
        if (list2.isEmpty()) {
            return list;
        }
        final ArrayMap arrayMap = new ArrayMap();
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            arrayMap.put(list2.get(i), Integer.valueOf(size - i));
        }
        ArrayList arrayList = new ArrayList(list);
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return MediaRouter2.lambda$getSortedRoutes$1(arrayMap, (MediaRoute2Info) obj);
            }
        }));
        return arrayList;
    }

    static /* synthetic */ int lambda$getSortedRoutes$1(Map map, MediaRoute2Info mediaRoute2Info) {
        return -((Integer) map.getOrDefault(mediaRoute2Info.getProviderPackageName(), 0)).intValue();
    }

    private List<MediaRoute2Info> filterRoutesWithCompositePreferenceLocked(List<MediaRoute2Info> list) {
        ArraySet arraySet = new ArraySet();
        ArrayList arrayList = new ArrayList();
        for (MediaRoute2Info mediaRoute2Info : getSortedRoutes(list, this.mDiscoveryPreference.getDeduplicationPackageOrder())) {
            if (mediaRoute2Info.hasAnyFeatures(this.mDiscoveryPreference.getPreferredFeatures()) && (this.mDiscoveryPreference.getAllowedPackages().isEmpty() || (mediaRoute2Info.getProviderPackageName() != null && this.mDiscoveryPreference.getAllowedPackages().contains(mediaRoute2Info.getProviderPackageName())))) {
                if (this.mDiscoveryPreference.shouldRemoveDuplicates()) {
                    if (Collections.disjoint(arraySet, mediaRoute2Info.getDeduplicationIds())) {
                        arraySet.addAll(mediaRoute2Info.getDeduplicationIds());
                    }
                }
                arrayList.add(mediaRoute2Info);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MediaRoute2Info> getRoutesWithIds(List<String> list) {
        List<MediaRoute2Info> list2;
        synchronized (this.mLock) {
            Stream<String> stream = list.stream();
            Map<String, MediaRoute2Info> map = this.mRoutes;
            Objects.requireNonNull(map);
            list2 = (List) stream.map(new MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new MediaRouter2$$ExternalSyntheticLambda11()).collect(Collectors.toList());
        }
        return list2;
    }

    private void notifyRoutesAdded(List<MediaRoute2Info> list) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord next = it.next();
            final List<MediaRoute2Info> listFilterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            if (!listFilterRoutesWithIndividualPreference.isEmpty()) {
                next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        next.mRouteCallback.onRoutesAdded(listFilterRoutesWithIndividualPreference);
                    }
                });
            }
        }
    }

    private void notifyRoutesRemoved(List<MediaRoute2Info> list) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord next = it.next();
            final List<MediaRoute2Info> listFilterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            if (!listFilterRoutesWithIndividualPreference.isEmpty()) {
                next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        next.mRouteCallback.onRoutesRemoved(listFilterRoutesWithIndividualPreference);
                    }
                });
            }
        }
    }

    private void notifyRoutesChanged(List<MediaRoute2Info> list) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord next = it.next();
            final List<MediaRoute2Info> listFilterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            if (!listFilterRoutesWithIndividualPreference.isEmpty()) {
                next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        next.mRouteCallback.onRoutesChanged(listFilterRoutesWithIndividualPreference);
                    }
                });
            }
        }
    }

    private void notifyRoutesUpdated(List<MediaRoute2Info> list) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord next = it.next();
            final List<MediaRoute2Info> listFilterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    next.mRouteCallback.onRoutesUpdated(listFilterRoutesWithIndividualPreference);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPreferredFeaturesChanged(final List<String> list) {
        Iterator<RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    next.mRouteCallback.onPreferredFeaturesChanged(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRouteListingPreferenceUpdated(final RouteListingPreference routeListingPreference) {
        Iterator<RouteListingPreferenceCallbackRecord> it = this.mListingPreferenceCallbackRecords.iterator();
        while (it.hasNext()) {
            final RouteListingPreferenceCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    next.mRouteListingPreferenceCallback.accept(routeListingPreference);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDeviceSuggestionsUpdated(final String str, final List<SuggestedDeviceInfo> list) {
        Iterator<DeviceSuggestionsCallbackRecord> it = this.mDeviceSuggestionsCallbackRecords.iterator();
        while (it.hasNext()) {
            final DeviceSuggestionsCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    next.mDeviceSuggestionsCallback.onSuggestionUpdated(str, list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTransfer(final RoutingController routingController, final RoutingController routingController2) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    next.mTransferCallback.onTransfer(routingController, routingController2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTransferFailure(final MediaRoute2Info mediaRoute2Info) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    next.mTransferCallback.onTransferFailure(mediaRoute2Info);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRequestFailed(final int i) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    next.mTransferCallback.onRequestFailed(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyStop(final RoutingController routingController) {
        Iterator<TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    next.mTransferCallback.onStop(routingController);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyControllerUpdated(final RoutingController routingController) {
        Iterator<ControllerCallbackRecord> it = this.mControllerCallbackRecords.iterator();
        while (it.hasNext()) {
            final ControllerCallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onControllerUpdated(routingController);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static RoutingSessionInfo ensureClientPackageNameForSystemSession(RoutingSessionInfo routingSessionInfo, String str) {
        return (routingSessionInfo.isSystemSession() && TextUtils.isEmpty(routingSessionInfo.getClientPackageName())) ? new RoutingSessionInfo.Builder(routingSessionInfo).setClientPackageName(str).build() : routingSessionInfo;
    }

    public static final class ScanToken {
        private final int mId;

        private ScanToken(int i) {
            this.mId = i;
        }
    }

    public static final class ScanRequest {
        private final boolean mIsScreenOffScan;

        private ScanRequest(boolean z) {
            this.mIsScreenOffScan = z;
        }

        public boolean isScreenOffScan() {
            return this.mIsScreenOffScan;
        }

        public static final class Builder {
            boolean mIsScreenOffScan;

            public Builder setScreenOffScan(boolean z) {
                this.mIsScreenOffScan = z;
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

        RoutingController(RoutingSessionInfo routingSessionInfo) {
            this.mControllerLock = new Object();
            this.mSessionInfo = routingSessionInfo;
            this.mState = 1;
        }

        RoutingController(RoutingSessionInfo routingSessionInfo, int i) {
            this.mControllerLock = new Object();
            this.mSessionInfo = routingSessionInfo;
            this.mState = i;
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
            List<String> selectedRoutes;
            synchronized (this.mControllerLock) {
                selectedRoutes = this.mSessionInfo.getSelectedRoutes();
            }
            return MediaRouter2.this.getRoutesWithIds(selectedRoutes);
        }

        public List<MediaRoute2Info> getSelectableRoutes() {
            List<String> selectableRoutes;
            synchronized (this.mControllerLock) {
                selectableRoutes = this.mSessionInfo.getSelectableRoutes();
            }
            return MediaRouter2.this.getRoutesWithIds(selectableRoutes);
        }

        public List<MediaRoute2Info> getDeselectableRoutes() {
            List<String> deselectableRoutes;
            synchronized (this.mControllerLock) {
                deselectableRoutes = this.mSessionInfo.getDeselectableRoutes();
            }
            return MediaRouter2.this.getRoutesWithIds(deselectableRoutes);
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

        public void selectRoute(MediaRoute2Info mediaRoute2Info) {
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (isReleased()) {
                Log.w(MediaRouter2.TAG, "selectRoute: Called on released controller. Ignoring.");
                return;
            }
            if (containsRouteInfoWithId(getSelectedRoutes(), mediaRoute2Info.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring selecting a route that is already selected. route=" + mediaRoute2Info);
            } else {
                if (!containsRouteInfoWithId(getSelectableRoutes(), mediaRoute2Info.getId())) {
                    Log.w(MediaRouter2.TAG, "Ignoring selecting a non-selectable route=" + mediaRoute2Info);
                    return;
                }
                MediaRouter2.this.mImpl.selectRoute(mediaRoute2Info, getRoutingSessionInfo());
            }
        }

        public void deselectRoute(MediaRoute2Info mediaRoute2Info) {
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (isReleased()) {
                Log.w(MediaRouter2.TAG, "deselectRoute: called on released controller. Ignoring.");
                return;
            }
            if (!containsRouteInfoWithId(getSelectedRoutes(), mediaRoute2Info.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring deselecting a route that is not selected. route=" + mediaRoute2Info);
            } else {
                if (!containsRouteInfoWithId(getDeselectableRoutes(), mediaRoute2Info.getId())) {
                    Log.w(MediaRouter2.TAG, "Ignoring deselecting a non-deselectable route=" + mediaRoute2Info);
                    return;
                }
                MediaRouter2.this.mImpl.deselectRoute(mediaRoute2Info, getRoutingSessionInfo());
            }
        }

        boolean tryTransferWithinProvider(MediaRoute2Info mediaRoute2Info) {
            MediaRouter2Stub mediaRouter2Stub;
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            synchronized (this.mControllerLock) {
                if (isReleased()) {
                    Log.w(MediaRouter2.TAG, "tryTransferWithinProvider: Called on released controller. Ignoring.");
                    return true;
                }
                if ((!Flags.enableBuiltInSpeakerRouteSuitabilityStatuses() || !this.mSessionInfo.isSystemSession() || !mediaRoute2Info.isSystemRoute() || !this.mSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) && !this.mSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId())) {
                    Log.i(MediaRouter2.TAG, "Transferring to a non-transferable route=" + mediaRoute2Info + " session= " + this.mSessionInfo.getId());
                    return false;
                }
                synchronized (MediaRouter2.this.mLock) {
                    mediaRouter2Stub = MediaRouter2.this.mStub;
                }
                if (mediaRouter2Stub != null) {
                    try {
                        MediaRouter2.this.mMediaRouterService.transferToRouteWithRouter2(mediaRouter2Stub, getId(), mediaRoute2Info);
                    } catch (RemoteException e) {
                        Log.e(MediaRouter2.TAG, "Unable to transfer to route for session.", e);
                    }
                }
                return true;
            }
        }

        public void setVolume(int i) {
            if (getVolumeHandling() == 0) {
                Log.w(MediaRouter2.TAG, "setVolume: The routing session has fixed volume. Ignoring.");
                return;
            }
            if (i < 0 || i > getVolumeMax()) {
                Log.w(MediaRouter2.TAG, "setVolume: The target volume is out of range. Ignoring");
            } else if (isReleased()) {
                Log.w(MediaRouter2.TAG, "setVolume: Called on released controller. Ignoring.");
            } else {
                MediaRouter2.this.mImpl.setSessionVolume(i, getRoutingSessionInfo());
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
                    MediaRouter2.this.mHandler.postDelayed(new Runnable() { // from class: android.media.MediaRouter2$RoutingController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.release();
                        }
                    }, 30000L);
                    return true;
                }
            }
        }

        void releaseInternal(boolean z) {
            synchronized (this.mControllerLock) {
                int i = this.mState;
                if (i == 3) {
                    if (MediaRouter2.DEBUG) {
                        Log.d(MediaRouter2.TAG, "releaseInternal: Called on released controller. Ignoring.");
                    }
                    return;
                }
                boolean z2 = true;
                if (i != 1) {
                    z2 = false;
                }
                this.mState = 3;
                MediaRouter2.this.mImpl.releaseSession(z, z2, this);
            }
        }

        public String toString() {
            return "RoutingController{ id=" + getId() + ", selectedRoutes={" + ((List) getSelectedRoutes().stream().map(new MediaRouter2$$ExternalSyntheticLambda16()).collect(Collectors.toList())) + "}, selectableRoutes={" + ((List) getSelectableRoutes().stream().map(new MediaRouter2$$ExternalSyntheticLambda16()).collect(Collectors.toList())) + "}, deselectableRoutes={" + ((List) getDeselectableRoutes().stream().map(new MediaRouter2$$ExternalSyntheticLambda16()).collect(Collectors.toList())) + "} }";
        }

        void setRoutingSessionInfo(RoutingSessionInfo routingSessionInfo) {
            synchronized (this.mControllerLock) {
                this.mSessionInfo = routingSessionInfo;
            }
        }

        private static boolean containsRouteInfoWithId(List<MediaRoute2Info> list, String str) {
            Iterator<MediaRoute2Info> it = list.iterator();
            while (it.hasNext()) {
                if (TextUtils.equals(str, it.next().getId())) {
                    return true;
                }
            }
            return false;
        }
    }

    class SystemRoutingController extends RoutingController {
        @Override // android.media.MediaRouter2.RoutingController
        public boolean isReleased() {
            return false;
        }

        @Override // android.media.MediaRouter2.RoutingController
        void releaseInternal(boolean z) {
        }

        @Override // android.media.MediaRouter2.RoutingController
        boolean scheduleRelease() {
            return true;
        }

        SystemRoutingController(MediaRouter2 mediaRouter2, RoutingSessionInfo routingSessionInfo) {
            super(routingSessionInfo);
        }
    }

    static final class RouteCallbackRecord {
        public final Executor mExecutor;
        public final RouteDiscoveryPreference mPreference;
        public final RouteCallback mRouteCallback;

        RouteCallbackRecord(Executor executor, RouteCallback routeCallback, RouteDiscoveryPreference routeDiscoveryPreference) {
            this.mRouteCallback = routeCallback;
            this.mExecutor = executor;
            this.mPreference = routeDiscoveryPreference;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RouteCallbackRecord) && this.mRouteCallback == ((RouteCallbackRecord) obj).mRouteCallback;
        }

        public int hashCode() {
            return this.mRouteCallback.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RouteListingPreferenceCallbackRecord {
        public final Executor mExecutor;
        public final Consumer<RouteListingPreference> mRouteListingPreferenceCallback;

        RouteListingPreferenceCallbackRecord(Executor executor, Consumer<RouteListingPreference> consumer) {
            this.mExecutor = executor;
            this.mRouteListingPreferenceCallback = consumer;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RouteListingPreferenceCallbackRecord) && this.mRouteListingPreferenceCallback == ((RouteListingPreferenceCallbackRecord) obj).mRouteListingPreferenceCallback;
        }

        public int hashCode() {
            return this.mRouteListingPreferenceCallback.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DeviceSuggestionsCallbackRecord {
        public final DeviceSuggestionsCallback mDeviceSuggestionsCallback;
        public final Executor mExecutor;

        DeviceSuggestionsCallbackRecord(Executor executor, DeviceSuggestionsCallback deviceSuggestionsCallback) {
            this.mExecutor = executor;
            this.mDeviceSuggestionsCallback = deviceSuggestionsCallback;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DeviceSuggestionsCallbackRecord) && this.mDeviceSuggestionsCallback == ((DeviceSuggestionsCallbackRecord) obj).mDeviceSuggestionsCallback;
        }

        public int hashCode() {
            return this.mDeviceSuggestionsCallback.hashCode();
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
            return (obj instanceof TransferCallbackRecord) && this.mTransferCallback == ((TransferCallbackRecord) obj).mTransferCallback;
        }

        public int hashCode() {
            return this.mTransferCallback.hashCode();
        }
    }

    static final class ControllerCallbackRecord {
        public final ControllerCallback mCallback;
        public final Executor mExecutor;

        ControllerCallbackRecord(Executor executor, ControllerCallback controllerCallback) {
            this.mCallback = controllerCallback;
            this.mExecutor = executor;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ControllerCallbackRecord) && this.mCallback == ((ControllerCallbackRecord) obj).mCallback;
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

        ControllerCreationRequest(int i, long j, MediaRoute2Info mediaRoute2Info, RoutingController routingController) {
            this.mRequestId = i;
            this.mManagerRequestId = j;
            this.mRoute = (MediaRoute2Info) Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            this.mOldController = (RoutingController) Objects.requireNonNull(routingController, "oldController must not be null");
        }
    }

    class MediaRouter2Stub extends IMediaRouter2.Stub {
        MediaRouter2Stub() {
        }

        @Override // android.media.IMediaRouter2
        public void notifyRouterRegistered(List<MediaRoute2Info> list, RoutingSessionInfo routingSessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2) obj).syncRoutesOnHandler((List) obj2, (RoutingSessionInfo) obj3);
                }
            }, MediaRouter2.this, list, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifyRoutesUpdated(List<MediaRoute2Info> list) {
            MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda4(), MediaRouter2.this, list));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2) obj).createControllerOnHandler(((Integer) obj2).intValue(), (RoutingSessionInfo) obj3);
                }
            }, MediaRouter2.this, Integer.valueOf(i), routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionInfoChanged(RoutingSessionInfo routingSessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRouter2) obj).updateControllerOnHandler((RoutingSessionInfo) obj2);
                }
            }, MediaRouter2.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) {
            MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRouter2) obj).releaseControllerOnHandler((RoutingSessionInfo) obj2);
                }
            }, MediaRouter2.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifyDeviceSuggestionsUpdated(String str, List<SuggestedDeviceInfo> list) {
            MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2) obj).notifyDeviceSuggestionsUpdated((String) obj2, (List) obj3);
                }
            }, MediaRouter2.this, str, list));
        }

        @Override // android.media.IMediaRouter2
        public void requestCreateSessionByManager(long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
            MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((MediaRouter2) obj).onRequestCreateControllerByManagerOnHandler((RoutingSessionInfo) obj2, (MediaRoute2Info) obj3, ((Long) obj4).longValue());
                }
            }, MediaRouter2.this, routingSessionInfo, mediaRoute2Info, Long.valueOf(j)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ProxyMediaRouter2Impl implements MediaRouter2Impl {
        private final String mClientPackageName;
        private final UserHandle mClientUser;
        private final CopyOnWriteArrayList<MediaRouter2Manager.TransferRequest> mTransferRequests = new CopyOnWriteArrayList<>();
        private final AtomicInteger mScanRequestCount = new AtomicInteger(0);
        private final AtomicBoolean mIsScanning = new AtomicBoolean(false);
        private final List<InstanceInvalidatedCallbackRecord> mInstanceInvalidatedCallbackRecords = new ArrayList();
        private final IMediaRouter2Manager.Stub mClient = new Client();

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getPackageName() {
            return null;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void registerRouteCallback() {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setOnGetControllerHintsListener(OnGetControllerHintsListener onGetControllerHintsListener) {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void unregisterRouteCallback() {
        }

        ProxyMediaRouter2Impl(Context context, String str, UserHandle userHandle) {
            this.mClientUser = userHandle;
            this.mClientPackageName = str;
            MediaRouter2.this.mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
        }

        public void registerProxyRouter() {
            try {
                MediaRouter2.this.mMediaRouterService.registerProxyRouter(this.mClient, MediaRouter2.this.mContext.getApplicationContext().getPackageName(), this.mClientPackageName, this.mClientUser);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void registerInstanceInvalidatedCallback(Executor executor, Runnable runnable) {
            if (executor == null || runnable == null) {
                return;
            }
            InstanceInvalidatedCallbackRecord instanceInvalidatedCallbackRecord = new InstanceInvalidatedCallbackRecord(executor, runnable);
            synchronized (MediaRouter2.this.mLock) {
                if (!this.mInstanceInvalidatedCallbackRecords.contains(instanceInvalidatedCallbackRecord)) {
                    this.mInstanceInvalidatedCallbackRecords.add(instanceInvalidatedCallbackRecord);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void updateScanningState(int i) throws RemoteException {
            MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, i);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void startScan() {
            if (this.mIsScanning.getAndSet(true) || this.mScanRequestCount.getAndIncrement() != 0) {
                return;
            }
            try {
                MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, 1);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stopScan() {
            if (this.mIsScanning.getAndSet(false) && this.mScanRequestCount.updateAndGet(new IntUnaryOperator() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$$ExternalSyntheticLambda0
                @Override // java.util.function.IntUnaryOperator
                public final int applyAsInt(int i) {
                    return MediaRouter2.ProxyMediaRouter2Impl.lambda$stopScan$0(i);
                }
            }) == 0) {
                try {
                    MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, 0);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        static /* synthetic */ int lambda$stopScan$0(int i) {
            if (i != 0) {
                return i - 1;
            }
            throw new IllegalStateException("No active scan requests to unregister.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getClientPackageName() {
            return this.mClientPackageName;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RoutingSessionInfo getSystemSessionInfo() {
            return getSystemSessionInfoImpl(MediaRouter2.this.mMediaRouterService, MediaRouter2.this.mContext.getPackageName(), this.mClientPackageName);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RouteCallbackRecord createRouteCallbackRecord(Executor executor, RouteCallback routeCallback, RouteDiscoveryPreference routeDiscoveryPreference) {
            return new RouteCallbackRecord(executor, routeCallback, RouteDiscoveryPreference.EMPTY);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteListingPreference(RouteListingPreference routeListingPreference) {
            throw new UnsupportedOperationException("RouteListingPreference cannot be set by a proxy MediaRouter2 instance.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setDeviceSuggestions(List<SuggestedDeviceInfo> list) {
            synchronized (MediaRouter2.this.mLock) {
                try {
                    MediaRouter2.this.mMediaRouterService.setDeviceSuggestionsWithManager(this.mClient, list);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestions() {
            Map<String, List<SuggestedDeviceInfo>> deviceSuggestionsWithManager;
            synchronized (MediaRouter2.this.mLock) {
                try {
                    try {
                        deviceSuggestionsWithManager = MediaRouter2.this.mMediaRouterService.getDeviceSuggestionsWithManager(this.mClient);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return deviceSuggestionsWithManager;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean showSystemOutputSwitcher() {
            try {
                return MediaRouter2.this.mMediaRouterService.showMediaOutputSwitcherWithProxyRouter(this.mClient);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
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
        public void transferTo(MediaRoute2Info mediaRoute2Info) {
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            transfer(getRoutingSessions().get(r0.size() - 1), mediaRoute2Info);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stop() {
            releaseSession(getRoutingSessions().get(r0.size() - 1));
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transfer(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
            boolean zContainsKey;
            Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            Log.v(MediaRouter2.TAG, "Transferring routing session. session= " + routingSessionInfo + ", route=" + mediaRoute2Info);
            synchronized (MediaRouter2.this.mLock) {
                zContainsKey = MediaRouter2.this.mRoutes.containsKey(mediaRoute2Info.getId());
            }
            if (!zContainsKey) {
                Log.w(MediaRouter2.TAG, "transfer: Ignoring an unknown route id=" + mediaRoute2Info.getId());
                onTransferFailed(routingSessionInfo, mediaRoute2Info);
                return;
            }
            boolean z = Flags.enableBuiltInSpeakerRouteSuitabilityStatuses() && routingSessionInfo.isSystemSession() && mediaRoute2Info.isSystemRoute() && routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId());
            if (routingSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId()) || z) {
                transferToRoute(routingSessionInfo, mediaRoute2Info, this.mClientUser, this.mClientPackageName);
            } else {
                requestCreateSession(routingSessionInfo, mediaRoute2Info);
            }
        }

        private void transferToRoute(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, UserHandle userHandle, String str) {
            try {
                MediaRouter2.this.mMediaRouterService.transferToRouteWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo.getId(), mediaRoute2Info, userHandle, str);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void requestCreateSession(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
            if (TextUtils.isEmpty(routingSessionInfo.getClientPackageName())) {
                Log.w(MediaRouter2.TAG, "requestCreateSession: Can't create a session without package name.");
                onTransferFailed(routingSessionInfo, mediaRoute2Info);
            } else {
                try {
                    MediaRouter2.this.mMediaRouterService.requestCreateSessionWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo, mediaRoute2Info);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<RoutingController> getControllers() {
            RoutingController routingController;
            ArrayList arrayList = new ArrayList();
            for (RoutingSessionInfo routingSessionInfo : getRoutingSessions()) {
                if (routingSessionInfo.isSystemSession()) {
                    MediaRouter2.this.mSystemController.setRoutingSessionInfo(routingSessionInfo);
                    routingController = MediaRouter2.this.mSystemController;
                } else {
                    routingController = MediaRouter2.this.new RoutingController(routingSessionInfo);
                }
                arrayList.add(routingController);
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i) {
            if (mediaRoute2Info.getVolumeHandling() == 0) {
                Log.w(MediaRouter2.TAG, "setRouteVolume: the route has fixed volume. Ignoring.");
                return;
            }
            if (i < 0 || i > mediaRoute2Info.getVolumeMax()) {
                Log.w(MediaRouter2.TAG, "setRouteVolume: the target volume is out of range. Ignoring");
                return;
            }
            try {
                MediaRouter2.this.mMediaRouterService.setRouteVolumeWithManager(this.mClient, MediaRouter2.this.mNextRequestId.getAndIncrement(), mediaRoute2Info, i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setSessionVolume(int i, RoutingSessionInfo routingSessionInfo) {
            Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            if (routingSessionInfo.getVolumeHandling() == 0) {
                Log.w(MediaRouter2.TAG, "setSessionVolume: the route has fixed volume. Ignoring.");
                return;
            }
            if (i < 0 || i > routingSessionInfo.getVolumeMax()) {
                Log.w(MediaRouter2.TAG, "setSessionVolume: the target volume is out of range. Ignoring");
                return;
            }
            try {
                MediaRouter2.this.mMediaRouterService.setSessionVolumeWithManager(this.mClient, MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<MediaRoute2Info> filterRoutesWithIndividualPreference(List<MediaRoute2Info> list, RouteDiscoveryPreference routeDiscoveryPreference) {
            return new ArrayList(list);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
            Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring selecting a route that is already selected. route=" + mediaRoute2Info);
            } else if (!routingSessionInfo.getSelectableRoutes().contains(mediaRoute2Info.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring selecting a non-selectable route=" + mediaRoute2Info);
            } else {
                try {
                    MediaRouter2.this.mMediaRouterService.selectRouteWithManager(this.mClient, MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
            Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring deselecting a route that is not selected. route=" + mediaRoute2Info);
            } else if (!routingSessionInfo.getDeselectableRoutes().contains(mediaRoute2Info.getId())) {
                Log.w(MediaRouter2.TAG, "Ignoring deselecting a non-deselectable route=" + mediaRoute2Info);
            } else {
                try {
                    MediaRouter2.this.mMediaRouterService.deselectRouteWithManager(this.mClient, MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void releaseSession(boolean z, boolean z2, RoutingController routingController) {
            releaseSession(routingController.getRoutingSessionInfo());
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean wasTransferredBySelf(RoutingSessionInfo routingSessionInfo) {
            return Objects.equals(this.mClientUser, routingSessionInfo.getTransferInitiatorUserHandle()) && Objects.equals(this.mClientPackageName, routingSessionInfo.getTransferInitiatorPackageName());
        }

        static RoutingSessionInfo getSystemSessionInfoImpl(IMediaRouterService iMediaRouterService, String str, String str2) {
            try {
                return iMediaRouterService.getSystemSessionInfoForPackage(str, str2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void releaseSession(RoutingSessionInfo routingSessionInfo) {
            Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            try {
                MediaRouter2.this.mMediaRouterService.releaseSessionWithManager(this.mClient, MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private int createTransferRequest(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
            int andIncrement = MediaRouter2.this.mNextRequestId.getAndIncrement();
            MediaRouter2Manager.TransferRequest transferRequest = new MediaRouter2Manager.TransferRequest(andIncrement, routingSessionInfo, mediaRoute2Info);
            this.mTransferRequests.add(transferRequest);
            MediaRouter2.this.mHandler.sendMessageDelayed(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRouter2.ProxyMediaRouter2Impl) obj).handleTransferTimeout((MediaRouter2Manager.TransferRequest) obj2);
                }
            }, this, transferRequest), 30000L);
            return andIncrement;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleTransferTimeout(MediaRouter2Manager.TransferRequest transferRequest) {
            if (this.mTransferRequests.remove(transferRequest)) {
                onTransferFailed(transferRequest.mOldSessionInfo, transferRequest.mTargetRoute);
            }
        }

        private List<RoutingSessionInfo> getRoutingSessions() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(getSystemSessionInfo());
            try {
                for (RoutingSessionInfo routingSessionInfo : MediaRouter2.this.mMediaRouterService.getRemoteSessions(this.mClient)) {
                    if (TextUtils.equals(routingSessionInfo.getClientPackageName(), this.mClientPackageName)) {
                        arrayList.add(routingSessionInfo);
                    }
                }
                return arrayList;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void onTransferred(RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
            RoutingController routingController;
            RoutingController routingController2;
            if (isSessionRelatedToTargetPackageName(routingSessionInfo) && isSessionRelatedToTargetPackageName(routingSessionInfo2)) {
                if (routingSessionInfo.isSystemSession()) {
                    MediaRouter2.this.mSystemController.setRoutingSessionInfo(MediaRouter2.ensureClientPackageNameForSystemSession(routingSessionInfo, this.mClientPackageName));
                    routingController = MediaRouter2.this.mSystemController;
                } else {
                    routingController = MediaRouter2.this.new RoutingController(routingSessionInfo);
                }
                if (routingSessionInfo2.isSystemSession()) {
                    MediaRouter2.this.mSystemController.setRoutingSessionInfo(MediaRouter2.ensureClientPackageNameForSystemSession(routingSessionInfo2, this.mClientPackageName));
                    routingController2 = MediaRouter2.this.mSystemController;
                } else {
                    routingController2 = MediaRouter2.this.new RoutingController(routingSessionInfo2);
                }
                MediaRouter2.this.notifyTransfer(routingController, routingController2);
            }
        }

        private void onTransferFailed(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
            if (isSessionRelatedToTargetPackageName(routingSessionInfo)) {
                MediaRouter2.this.notifyTransferFailure(mediaRoute2Info);
            }
        }

        private void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
            RoutingController routingController;
            if (isSessionRelatedToTargetPackageName(routingSessionInfo)) {
                if (routingSessionInfo.isSystemSession()) {
                    MediaRouter2.this.mSystemController.setRoutingSessionInfo(MediaRouter2.ensureClientPackageNameForSystemSession(routingSessionInfo, this.mClientPackageName));
                    routingController = MediaRouter2.this.mSystemController;
                } else {
                    routingController = MediaRouter2.this.new RoutingController(routingSessionInfo);
                }
                MediaRouter2.this.notifyControllerUpdated(routingController);
            }
        }

        private boolean isSessionRelatedToTargetPackageName(RoutingSessionInfo routingSessionInfo) {
            return routingSessionInfo.isSystemSession() || TextUtils.equals(getClientPackageName(), routingSessionInfo.getClientPackageName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionCreatedOnHandler(int i, RoutingSessionInfo routingSessionInfo) {
            MediaRouter2Manager.TransferRequest next;
            Iterator<MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (next.mRequestId == i) {
                        break;
                    }
                }
            }
            if (next == null) {
                return;
            }
            this.mTransferRequests.remove(next);
            MediaRoute2Info mediaRoute2Info = next.mTargetRoute;
            if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
                Log.w(MediaRouter2.TAG, "The session does not contain the requested route. (requestedRouteId=" + mediaRoute2Info.getId() + ", actualRoutes=" + routingSessionInfo.getSelectedRoutes() + NavigationBarInflaterView.KEY_CODE_END);
                onTransferFailed(next.mOldSessionInfo, mediaRoute2Info);
                return;
            }
            if (!TextUtils.equals(mediaRoute2Info.getProviderId(), routingSessionInfo.getProviderId())) {
                Log.w(MediaRouter2.TAG, "The session's provider ID does not match the requested route's. (requested route's providerId=" + mediaRoute2Info.getProviderId() + ", actual providerId=" + routingSessionInfo.getProviderId() + NavigationBarInflaterView.KEY_CODE_END);
                onTransferFailed(next.mOldSessionInfo, mediaRoute2Info);
                return;
            }
            onTransferred(next.mOldSessionInfo, routingSessionInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionUpdatedOnHandler(RoutingSessionInfo routingSessionInfo) {
            Iterator<MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MediaRouter2Manager.TransferRequest next = it.next();
                if (TextUtils.equals(next.mOldSessionInfo.getId(), routingSessionInfo.getId()) && routingSessionInfo.getSelectedRoutes().contains(next.mTargetRoute.getId())) {
                    this.mTransferRequests.remove(next);
                    break;
                }
            }
            onSessionUpdated(routingSessionInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionReleasedOnHandler(RoutingSessionInfo routingSessionInfo) {
            if (routingSessionInfo.isSystemSession()) {
                Log.e(MediaRouter2.TAG, "onSessionReleasedOnHandler: Called on system session. Ignoring.");
            } else if (TextUtils.equals(getClientPackageName(), routingSessionInfo.getClientPackageName())) {
                MediaRouter2.this.notifyStop(MediaRouter2.this.new RoutingController(routingSessionInfo, 3));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDiscoveryPreferenceChangedOnHandler(String str, RouteDiscoveryPreference routeDiscoveryPreference) {
            if (TextUtils.equals(getClientPackageName(), str) && routeDiscoveryPreference != null) {
                synchronized (MediaRouter2.this.mLock) {
                    if (Objects.equals(routeDiscoveryPreference, MediaRouter2.this.mDiscoveryPreference)) {
                        return;
                    }
                    MediaRouter2.this.mDiscoveryPreference = routeDiscoveryPreference;
                    MediaRouter2.this.updateFilteredRoutesLocked();
                    MediaRouter2.this.notifyPreferredFeaturesChanged(routeDiscoveryPreference.getPreferredFeatures());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRouteListingPreferenceChangedOnHandler(String str, RouteListingPreference routeListingPreference) {
            if (TextUtils.equals(getClientPackageName(), str)) {
                synchronized (MediaRouter2.this.mLock) {
                    if (Objects.equals(MediaRouter2.this.mRouteListingPreference, routeListingPreference)) {
                        return;
                    }
                    MediaRouter2.this.mRouteListingPreference = routeListingPreference;
                    MediaRouter2.this.notifyRouteListingPreferenceUpdated(routeListingPreference);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDeviceSuggestionsChangeHandler(String str, String str2, List<SuggestedDeviceInfo> list) {
            if (TextUtils.equals(getClientPackageName(), str)) {
                synchronized (MediaRouter2.this.mLock) {
                    if (Objects.equals(MediaRouter2.this.mSuggestedDeviceInfo.get(str2), list)) {
                        return;
                    }
                    MediaRouter2.this.mSuggestedDeviceInfo.put(str2, list);
                    MediaRouter2.this.notifyDeviceSuggestionsUpdated(str2, list);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRequestFailedOnHandler(int i, int i2) {
            MediaRouter2Manager.TransferRequest next;
            Iterator<MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (next.mRequestId == i) {
                        break;
                    }
                }
            }
            if (next != null) {
                this.mTransferRequests.remove(next);
                onTransferFailed(next.mOldSessionInfo, next.mTargetRoute);
            } else {
                MediaRouter2.this.notifyRequestFailed(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onInvalidateInstanceOnHandler() {
            Log.w(MediaRouter2.TAG, "MEDIA_ROUTING_CONTROL has been revoked for this package. Invalidating instance.");
            synchronized (MediaRouter2.sSystemRouterLock) {
                MediaRouter2.sAppToProxyRouterMap.remove(new PackageNameUserHandlePair(this.mClientPackageName, this.mClientUser));
            }
            synchronized (MediaRouter2.this.mLock) {
                for (InstanceInvalidatedCallbackRecord instanceInvalidatedCallbackRecord : this.mInstanceInvalidatedCallbackRecords) {
                    instanceInvalidatedCallbackRecord.executor.execute(instanceInvalidatedCallbackRecord.runnable);
                }
            }
            MediaRouter2.this.mRouteCallbackRecords.clear();
            MediaRouter2.this.mControllerCallbackRecords.clear();
            MediaRouter2.this.mTransferCallbackRecords.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        class Client extends IMediaRouter2Manager.Stub {
            private Client() {
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda2
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onSessionCreatedOnHandler(((Integer) obj2).intValue(), (RoutingSessionInfo) obj3);
                    }
                }, ProxyMediaRouter2Impl.this, Integer.valueOf(i), routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionUpdated(RoutingSessionInfo routingSessionInfo) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onSessionUpdatedOnHandler((RoutingSessionInfo) obj2);
                    }
                }, ProxyMediaRouter2Impl.this, routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onSessionReleasedOnHandler((RoutingSessionInfo) obj2);
                    }
                }, ProxyMediaRouter2Impl.this, routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyDiscoveryPreferenceChanged(String str, RouteDiscoveryPreference routeDiscoveryPreference) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda7
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onDiscoveryPreferenceChangedOnHandler((String) obj2, (RouteDiscoveryPreference) obj3);
                    }
                }, ProxyMediaRouter2Impl.this, str, routeDiscoveryPreference));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRouteListingPreferenceChange(String str, RouteListingPreference routeListingPreference) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda4
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onRouteListingPreferenceChangedOnHandler((String) obj2, (RouteListingPreference) obj3);
                    }
                }, ProxyMediaRouter2Impl.this, str, routeListingPreference));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyDeviceSuggestionsUpdated(String str, String str2, List<SuggestedDeviceInfo> list) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onDeviceSuggestionsChangeHandler((String) obj2, (String) obj3, (List) obj4);
                    }
                }, ProxyMediaRouter2Impl.this, str, str2, list));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRoutesUpdated(List<MediaRoute2Info> list) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda4(), MediaRouter2.this, list));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRequestFailed(int i, int i2) {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda3
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onRequestFailedOnHandler(((Integer) obj2).intValue(), ((Integer) obj3).intValue());
                    }
                }, ProxyMediaRouter2Impl.this, Integer.valueOf(i), Integer.valueOf(i2)));
            }

            @Override // android.media.IMediaRouter2Manager
            public void invalidateInstance() {
                MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((MediaRouter2.ProxyMediaRouter2Impl) obj).onInvalidateInstanceOnHandler();
                    }
                }, ProxyMediaRouter2Impl.this));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LocalMediaRouter2Impl implements MediaRouter2Impl {
        private final String mPackageName;

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getClientPackageName() {
            return null;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void startScan() {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stopScan() {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transfer(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        }

        LocalMediaRouter2Impl(String str) {
            this.mPackageName = str;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void updateScanningState(int i) throws RemoteException {
            if (i != 0) {
                registerRouterStubIfNeededLocked();
            }
            MediaRouter2.this.mMediaRouterService.updateScanningStateWithRouter2(MediaRouter2.this.mStub, i);
            if (i == 0) {
                unregisterRouterStubIfNeededLocked(true);
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public String getPackageName() {
            return this.mPackageName;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RoutingSessionInfo getSystemSessionInfo() {
            try {
                return MediaRouter2.ensureClientPackageNameForSystemSession(MediaRouter2.this.mMediaRouterService.getSystemSessionInfo(), MediaRouter2.this.mContext.getPackageName());
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return null;
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public RouteCallbackRecord createRouteCallbackRecord(Executor executor, RouteCallback routeCallback, RouteDiscoveryPreference routeDiscoveryPreference) {
            return new RouteCallbackRecord(executor, routeCallback, routeDiscoveryPreference);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void registerRouteCallback() {
            synchronized (MediaRouter2.this.mLock) {
                try {
                    registerRouterStubIfNeededLocked();
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                if (MediaRouter2.this.updateDiscoveryPreferenceIfNeededLocked()) {
                    MediaRouter2.this.mMediaRouterService.setDiscoveryRequestWithRouter2(MediaRouter2.this.mStub, MediaRouter2.this.mDiscoveryPreference);
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
                        MediaRouter2.this.mMediaRouterService.setDiscoveryRequestWithRouter2(MediaRouter2.this.mStub, MediaRouter2.this.mDiscoveryPreference);
                    }
                    unregisterRouterStubIfNeededLocked(false);
                } catch (RemoteException e) {
                    Log.e(MediaRouter2.TAG, "unregisterRouteCallback: Unable to set discovery request.", e);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteListingPreference(RouteListingPreference routeListingPreference) {
            synchronized (MediaRouter2.this.mLock) {
                if (Objects.equals(MediaRouter2.this.mRouteListingPreference, routeListingPreference)) {
                    return;
                }
                MediaRouter2.this.mRouteListingPreference = routeListingPreference;
                try {
                    registerRouterStubIfNeededLocked();
                    MediaRouter2.this.mMediaRouterService.setRouteListingPreference(MediaRouter2.this.mStub, MediaRouter2.this.mRouteListingPreference);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                MediaRouter2.this.notifyRouteListingPreferenceUpdated(routeListingPreference);
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setDeviceSuggestions(List<SuggestedDeviceInfo> list) {
            synchronized (MediaRouter2.this.mLock) {
                try {
                    registerRouterStubIfNeededLocked();
                    MediaRouter2.this.mMediaRouterService.setDeviceSuggestionsWithRouter2(MediaRouter2.this.mStub, list);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestions() {
            Map<String, List<SuggestedDeviceInfo>> deviceSuggestionsWithRouter2;
            synchronized (MediaRouter2.this.mLock) {
                try {
                    try {
                        deviceSuggestionsWithRouter2 = MediaRouter2.this.mMediaRouterService.getDeviceSuggestionsWithRouter2(MediaRouter2.this.mStub);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return deviceSuggestionsWithRouter2;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean showSystemOutputSwitcher() {
            boolean zShowMediaOutputSwitcherWithRouter2;
            synchronized (MediaRouter2.this.mLock) {
                try {
                    try {
                        zShowMediaOutputSwitcherWithRouter2 = MediaRouter2.this.mMediaRouterService.showMediaOutputSwitcherWithRouter2(this.mPackageName);
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                        return false;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return zShowMediaOutputSwitcherWithRouter2;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<MediaRoute2Info> getAllRoutes() {
            return Collections.EMPTY_LIST;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setOnGetControllerHintsListener(OnGetControllerHintsListener onGetControllerHintsListener) {
            MediaRouter2.this.mOnGetControllerHintsListener = onGetControllerHintsListener;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transferTo(MediaRoute2Info mediaRoute2Info) {
            boolean zContainsKey;
            Log.v(MediaRouter2.TAG, "Transferring to route: " + mediaRoute2Info);
            synchronized (MediaRouter2.this.mLock) {
                zContainsKey = MediaRouter2.this.mRoutes.containsKey(mediaRoute2Info.getId());
            }
            if (!zContainsKey) {
                MediaRouter2.this.notifyTransferFailure(mediaRoute2Info);
                return;
            }
            RoutingController currentController = MediaRouter2.this.getCurrentController();
            if (currentController.tryTransferWithinProvider(mediaRoute2Info)) {
                return;
            }
            MediaRouter2.this.requestCreateController(currentController, mediaRoute2Info, 0L);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stop() {
            MediaRouter2.this.getCurrentController().release();
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<RoutingController> getControllers() {
            ArrayList arrayList = new ArrayList();
            arrayList.add(0, MediaRouter2.this.mSystemController);
            synchronized (MediaRouter2.this.mLock) {
                arrayList.addAll(MediaRouter2.this.mNonSystemRoutingControllers.values());
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i) {
            throw new UnsupportedOperationException("setRouteVolume is only supported by proxy routers. See javadoc.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setSessionVolume(int i, RoutingSessionInfo routingSessionInfo) {
            MediaRouter2Stub mediaRouter2Stub;
            synchronized (MediaRouter2.this.mLock) {
                mediaRouter2Stub = MediaRouter2.this.mStub;
            }
            if (mediaRouter2Stub != null) {
                try {
                    MediaRouter2.this.mMediaRouterService.setSessionVolumeWithRouter2(mediaRouter2Stub, routingSessionInfo.getId(), i);
                } catch (RemoteException e) {
                    Log.e(MediaRouter2.TAG, "setVolume: Failed to deliver request.", e);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public List<MediaRoute2Info> filterRoutesWithIndividualPreference(List<MediaRoute2Info> list, RouteDiscoveryPreference routeDiscoveryPreference) {
            ArrayList arrayList = new ArrayList();
            for (MediaRoute2Info mediaRoute2Info : list) {
                if (mediaRoute2Info.hasAnyFeatures(routeDiscoveryPreference.getPreferredFeatures()) && (routeDiscoveryPreference.getAllowedPackages().isEmpty() || (mediaRoute2Info.getProviderPackageName() != null && routeDiscoveryPreference.getAllowedPackages().contains(mediaRoute2Info.getProviderPackageName())))) {
                    arrayList.add(mediaRoute2Info);
                }
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void selectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
            MediaRouter2Stub mediaRouter2Stub;
            synchronized (MediaRouter2.this.mLock) {
                mediaRouter2Stub = MediaRouter2.this.mStub;
            }
            if (mediaRouter2Stub != null) {
                try {
                    MediaRouter2.this.mMediaRouterService.selectRouteWithRouter2(mediaRouter2Stub, routingSessionInfo.getId(), mediaRoute2Info);
                } catch (RemoteException e) {
                    Log.e(MediaRouter2.TAG, "Unable to select route for session.", e);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void deselectRoute(MediaRoute2Info mediaRoute2Info, RoutingSessionInfo routingSessionInfo) {
            MediaRouter2Stub mediaRouter2Stub;
            synchronized (MediaRouter2.this.mLock) {
                mediaRouter2Stub = MediaRouter2.this.mStub;
            }
            if (mediaRouter2Stub != null) {
                try {
                    MediaRouter2.this.mMediaRouterService.deselectRouteWithRouter2(mediaRouter2Stub, routingSessionInfo.getId(), mediaRoute2Info);
                } catch (RemoteException e) {
                    Log.e(MediaRouter2.TAG, "Unable to deselect route from session.", e);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0038 A[Catch: all -> 0x0057, TRY_LEAVE, TryCatch #2 {, blocks: (B:4:0x0007, B:6:0x0016, B:8:0x001e, B:11:0x0033, B:13:0x0038, B:15:0x004d, B:19:0x0055, B:18:0x0052), top: B:28:0x0007, inners: #0, #1 }] */
        @Override // android.media.MediaRouter2.MediaRouter2Impl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void releaseSession(boolean z, boolean z2, RoutingController routingController) {
            synchronized (MediaRouter2.this.mLock) {
                MediaRouter2.this.mNonSystemRoutingControllers.remove(routingController.getId(), routingController);
                if (z && MediaRouter2.this.mStub != null) {
                    try {
                        MediaRouter2.this.mMediaRouterService.releaseSessionWithRouter2(MediaRouter2.this.mStub, routingController.getId());
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                    if (z2) {
                    }
                    unregisterRouterStubIfNeededLocked(false);
                } else {
                    if (z2) {
                        MediaRouter2.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2$LocalMediaRouter2Impl$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                ((MediaRouter2) obj).notifyStop((MediaRouter2.RoutingController) obj2);
                            }
                        }, MediaRouter2.this, routingController));
                    }
                    try {
                        unregisterRouterStubIfNeededLocked(false);
                    } catch (RemoteException e2) {
                        e2.rethrowFromSystemServer();
                    }
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean wasTransferredBySelf(RoutingSessionInfo routingSessionInfo) {
            return Objects.equals(Process.myUserHandle(), routingSessionInfo.getTransferInitiatorUserHandle()) && Objects.equals(MediaRouter2.this.mContext.getPackageName(), routingSessionInfo.getTransferInitiatorPackageName());
        }

        private void registerRouterStubIfNeededLocked() throws RemoteException {
            if (MediaRouter2.this.mStub == null) {
                MediaRouter2Stub mediaRouter2Stub = MediaRouter2.this.new MediaRouter2Stub();
                MediaRouter2.this.mMediaRouterService.registerRouter2(mediaRouter2Stub, this.mPackageName);
                MediaRouter2.this.mStub = mediaRouter2Stub;
            }
        }

        private void unregisterRouterStubIfNeededLocked(boolean z) throws RemoteException {
            if (MediaRouter2.this.mStub != null && MediaRouter2.this.mRouteCallbackRecords.isEmpty() && MediaRouter2.this.mNonSystemRoutingControllers.isEmpty()) {
                if (MediaRouter2.this.mScanRequestsMap.size() == 0 || z) {
                    MediaRouter2.this.mMediaRouterService.unregisterRouter2(MediaRouter2.this.mStub);
                    MediaRouter2.this.mStub = null;
                }
            }
        }
    }
}
