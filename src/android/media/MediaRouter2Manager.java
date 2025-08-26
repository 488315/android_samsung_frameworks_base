package android.media;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.IMediaRouter2Manager;
import android.media.IMediaRouterService;
import android.media.MediaRouter2Manager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public final class MediaRouter2Manager {
    public static final int REQUEST_ID_NONE = 0;
    private static final String TAG = "MR2Manager";
    public static final int TRANSFER_TIMEOUT_MS = 30000;
    private static MediaRouter2Manager sInstance;
    private static final Object sLock = new Object();
    private final Client mClient;
    private final Context mContext;
    final Handler mHandler;
    private final IMediaRouterService mMediaRouterService;
    private final MediaSessionManager mMediaSessionManager;
    private final AtomicInteger mScanRequestCount = new AtomicInteger(0);
    final CopyOnWriteArrayList<CallbackRecord> mCallbackRecords = new CopyOnWriteArrayList<>();
    private final Object mRoutesLock = new Object();
    private final Map<String, MediaRoute2Info> mRoutes = new HashMap();
    final ConcurrentMap<String, RouteDiscoveryPreference> mDiscoveryPreferenceMap = new ConcurrentHashMap();
    private final ConcurrentMap<String, RouteListingPreference> mPackageToRouteListingPreferenceMap = new ConcurrentHashMap();
    private final AtomicInteger mNextRequestId = new AtomicInteger(1);
    private final CopyOnWriteArrayList<TransferRequest> mTransferRequests = new CopyOnWriteArrayList<>();

    public static MediaRouter2Manager getInstance(Context context) {
        MediaRouter2Manager mediaRouter2Manager;
        Objects.requireNonNull(context, "context must not be null");
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new MediaRouter2Manager(context);
            }
            mediaRouter2Manager = sInstance;
        }
        return mediaRouter2Manager;
    }

    private MediaRouter2Manager(Context context) {
        this.mContext = context.getApplicationContext();
        IMediaRouterService iMediaRouterServiceAsInterface = IMediaRouterService.Stub.asInterface(ServiceManager.getService(Context.MEDIA_ROUTER_SERVICE));
        this.mMediaRouterService = iMediaRouterServiceAsInterface;
        this.mMediaSessionManager = (MediaSessionManager) context.getSystemService(Context.MEDIA_SESSION_SERVICE);
        this.mHandler = new Handler(context.getMainLooper());
        Client client = new Client();
        this.mClient = client;
        try {
            iMediaRouterServiceAsInterface.registerManager(client, context.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Executor executor, Callback callback) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(callback, "callback must not be null");
        if (this.mCallbackRecords.addIfAbsent(new CallbackRecord(this, executor, callback))) {
            return;
        }
        Log.w(TAG, "Ignoring to register the same callback twice.");
    }

    public void unregisterCallback(Callback callback) {
        Objects.requireNonNull(callback, "callback must not be null");
        if (this.mCallbackRecords.remove(new CallbackRecord(this, null, callback))) {
            return;
        }
        Log.w(TAG, "unregisterCallback: Ignore unknown callback. " + callback);
    }

    public void registerScanRequest() {
        if (this.mScanRequestCount.getAndIncrement() == 0) {
            try {
                this.mMediaRouterService.updateScanningState(this.mClient, 1);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterScanRequest() {
        if (this.mScanRequestCount.updateAndGet(new IntUnaryOperator() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda12
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return MediaRouter2Manager.lambda$unregisterScanRequest$0(i);
            }
        }) == 0) {
            try {
                this.mMediaRouterService.updateScanningState(this.mClient, 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    static /* synthetic */ int lambda$unregisterScanRequest$0(int i) {
        if (i != 0) {
            return i - 1;
        }
        throw new IllegalStateException("No active scan requests to unregister.");
    }

    public MediaController getMediaControllerForRoutingSession(RoutingSessionInfo routingSessionInfo) {
        for (MediaController mediaController : this.mMediaSessionManager.getActiveSessions(null)) {
            if (areSessionsMatched(mediaController, routingSessionInfo)) {
                return mediaController;
            }
        }
        return null;
    }

    public List<MediaRoute2Info> getAvailableRoutes(String str) {
        Objects.requireNonNull(str, "packageName must not be null");
        return getAvailableRoutes(getRoutingSessions(str).get(r2.size() - 1));
    }

    public List<MediaRoute2Info> getTransferableRoutes(String str) {
        Objects.requireNonNull(str, "packageName must not be null");
        return getTransferableRoutes(getRoutingSessions(str).get(r2.size() - 1));
    }

    public List<MediaRoute2Info> getAvailableRoutes(RoutingSessionInfo routingSessionInfo) {
        return getFilteredRoutes(routingSessionInfo, true, null);
    }

    public List<MediaRoute2Info> getTransferableRoutes(final RoutingSessionInfo routingSessionInfo) {
        return getFilteredRoutes(routingSessionInfo, false, new Predicate() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaRouter2Manager.lambda$getTransferableRoutes$1(routingSessionInfo, (MediaRoute2Info) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getTransferableRoutes$1(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        return routingSessionInfo.isSystemSession() ^ mediaRoute2Info.isSystemRoute();
    }

    private List<MediaRoute2Info> getSortedRoutes(RouteDiscoveryPreference routeDiscoveryPreference) {
        ArrayList arrayList;
        List<MediaRoute2Info> listCopyOf;
        if (!routeDiscoveryPreference.shouldRemoveDuplicates()) {
            synchronized (this.mRoutesLock) {
                listCopyOf = List.copyOf(this.mRoutes.values());
            }
            return listCopyOf;
        }
        final ArrayMap arrayMap = new ArrayMap();
        int size = routeDiscoveryPreference.getDeduplicationPackageOrder().size();
        for (int i = 0; i < size; i++) {
            arrayMap.put(routeDiscoveryPreference.getDeduplicationPackageOrder().get(i), Integer.valueOf(size - i));
        }
        synchronized (this.mRoutesLock) {
            arrayList = new ArrayList(this.mRoutes.values());
        }
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return MediaRouter2Manager.lambda$getSortedRoutes$2(arrayMap, (MediaRoute2Info) obj);
            }
        }));
        return arrayList;
    }

    static /* synthetic */ int lambda$getSortedRoutes$2(Map map, MediaRoute2Info mediaRoute2Info) {
        return -((Integer) map.getOrDefault(mediaRoute2Info.getProviderPackageName(), 0)).intValue();
    }

    private List<MediaRoute2Info> getFilteredRoutes(RoutingSessionInfo routingSessionInfo, boolean z, Predicate<MediaRoute2Info> predicate) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        String clientPackageName = routingSessionInfo.getClientPackageName();
        RouteDiscoveryPreference orDefault = this.mDiscoveryPreferenceMap.getOrDefault(clientPackageName, RouteDiscoveryPreference.EMPTY);
        for (MediaRoute2Info mediaRoute2Info : getSortedRoutes(orDefault)) {
            if (mediaRoute2Info.isVisibleTo(clientPackageName)) {
                boolean zContains = routingSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId());
                boolean zContains2 = routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId());
                if (zContains || (z && zContains2)) {
                    arrayList.add(mediaRoute2Info);
                } else if (mediaRoute2Info.hasAnyFeatures(orDefault.getPreferredFeatures()) && (orDefault.getAllowedPackages().isEmpty() || (mediaRoute2Info.getProviderPackageName() != null && orDefault.getAllowedPackages().contains(mediaRoute2Info.getProviderPackageName())))) {
                    if (predicate == null || predicate.test(mediaRoute2Info)) {
                        if (orDefault.shouldRemoveDuplicates()) {
                            if (Collections.disjoint(arraySet, mediaRoute2Info.getDeduplicationIds())) {
                                arraySet.addAll(mediaRoute2Info.getDeduplicationIds());
                            }
                        }
                        arrayList.add(mediaRoute2Info);
                    }
                }
            }
        }
        return arrayList;
    }

    public RouteDiscoveryPreference getDiscoveryPreference(String str) {
        Objects.requireNonNull(str, "packageName must not be null");
        return this.mDiscoveryPreferenceMap.getOrDefault(str, RouteDiscoveryPreference.EMPTY);
    }

    public RouteListingPreference getRouteListingPreference(String str) {
        Preconditions.checkArgument(!TextUtils.isEmpty(str));
        return this.mPackageToRouteListingPreferenceMap.get(str);
    }

    public RoutingSessionInfo getSystemRoutingSession(String str) {
        try {
            return this.mMediaRouterService.getSystemSessionInfoForPackage(this.mContext.getPackageName(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public RoutingSessionInfo getRoutingSessionForMediaController(MediaController mediaController) {
        if (mediaController.getPlaybackInfo().getPlaybackType() == 1) {
            return getSystemRoutingSession(mediaController.getPackageName());
        }
        for (RoutingSessionInfo routingSessionInfo : getRemoteSessions()) {
            if (areSessionsMatched(mediaController, routingSessionInfo)) {
                return routingSessionInfo;
            }
        }
        return null;
    }

    public List<RoutingSessionInfo> getRoutingSessions(String str) {
        Objects.requireNonNull(str, "packageName must not be null");
        ArrayList arrayList = new ArrayList();
        arrayList.add(getSystemRoutingSession(str));
        for (RoutingSessionInfo routingSessionInfo : getRemoteSessions()) {
            if (TextUtils.equals(routingSessionInfo.getClientPackageName(), str)) {
                arrayList.add(routingSessionInfo);
            }
        }
        return arrayList;
    }

    public List<RoutingSessionInfo> getRemoteSessions() {
        try {
            return this.mMediaRouterService.getRemoteSessions(this.mClient);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<MediaRoute2Info> getAllRoutes() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRoutesLock) {
            arrayList.addAll(this.mRoutes.values());
        }
        return arrayList;
    }

    public void transfer(String str, MediaRoute2Info mediaRoute2Info, UserHandle userHandle) {
        Objects.requireNonNull(str, "packageName must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        transfer(getRoutingSessions(str).get(r0.size() - 1), mediaRoute2Info, userHandle, str);
    }

    public void transfer(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, UserHandle userHandle, String str) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        Objects.requireNonNull(userHandle);
        Objects.requireNonNull(str);
        Log.v(TAG, "Transferring routing session. session= " + routingSessionInfo + ", route=" + mediaRoute2Info);
        synchronized (this.mRoutesLock) {
            if (!this.mRoutes.containsKey(mediaRoute2Info.getId())) {
                Log.w(TAG, "transfer: Ignoring an unknown route id=" + mediaRoute2Info.getId());
                notifyTransferFailed(routingSessionInfo, mediaRoute2Info);
                return;
            }
            if (routingSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId())) {
                transferToRoute(routingSessionInfo, mediaRoute2Info, userHandle, str);
            } else {
                requestCreateSession(routingSessionInfo, mediaRoute2Info);
            }
        }
    }

    public void setRouteVolume(MediaRoute2Info mediaRoute2Info, int i) {
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (mediaRoute2Info.getVolumeHandling() == 0) {
            Log.w(TAG, "setRouteVolume: the route has fixed volume. Ignoring.");
            return;
        }
        if (i < 0 || i > mediaRoute2Info.getVolumeMax()) {
            Log.w(TAG, "setRouteVolume: the target volume is out of range. Ignoring");
            return;
        }
        try {
            this.mMediaRouterService.setRouteVolumeWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), mediaRoute2Info, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSessionVolume(RoutingSessionInfo routingSessionInfo, int i) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        if (routingSessionInfo.getVolumeHandling() == 0) {
            Log.w(TAG, "setSessionVolume: the route has fixed volume. Ignoring.");
            return;
        }
        if (i < 0 || i > routingSessionInfo.getVolumeMax()) {
            Log.w(TAG, "setSessionVolume: the target volume is out of range. Ignoring");
            return;
        }
        try {
            this.mMediaRouterService.setSessionVolumeWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void updateRoutesOnHandler(List<MediaRoute2Info> list) {
        synchronized (this.mRoutesLock) {
            this.mRoutes.clear();
            for (MediaRoute2Info mediaRoute2Info : list) {
                this.mRoutes.put(mediaRoute2Info.getId(), mediaRoute2Info);
            }
        }
        notifyRoutesUpdated();
    }

    void createSessionOnHandler(int i, RoutingSessionInfo routingSessionInfo) {
        TransferRequest next;
        Iterator<TransferRequest> it = this.mTransferRequests.iterator();
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
        if (routingSessionInfo == null) {
            notifyTransferFailed(next.mOldSessionInfo, mediaRoute2Info);
            return;
        }
        if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
            Log.w(TAG, "The session does not contain the requested route. (requestedRouteId=" + mediaRoute2Info.getId() + ", actualRoutes=" + routingSessionInfo.getSelectedRoutes() + NavigationBarInflaterView.KEY_CODE_END);
            notifyTransferFailed(next.mOldSessionInfo, mediaRoute2Info);
            return;
        }
        if (!TextUtils.equals(mediaRoute2Info.getProviderId(), routingSessionInfo.getProviderId())) {
            Log.w(TAG, "The session's provider ID does not match the requested route's. (requested route's providerId=" + mediaRoute2Info.getProviderId() + ", actual providerId=" + routingSessionInfo.getProviderId() + NavigationBarInflaterView.KEY_CODE_END);
            notifyTransferFailed(next.mOldSessionInfo, mediaRoute2Info);
            return;
        }
        notifyTransferred(next.mOldSessionInfo, routingSessionInfo);
    }

    void handleFailureOnHandler(int i, int i2) {
        TransferRequest next;
        Iterator<TransferRequest> it = this.mTransferRequests.iterator();
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
            notifyTransferFailed(next.mOldSessionInfo, next.mTargetRoute);
        } else {
            notifyRequestFailed(i2);
        }
    }

    void handleSessionsUpdatedOnHandler(RoutingSessionInfo routingSessionInfo) {
        Iterator<TransferRequest> it = this.mTransferRequests.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TransferRequest next = it.next();
            if (TextUtils.equals(next.mOldSessionInfo.getId(), routingSessionInfo.getId()) && routingSessionInfo.getSelectedRoutes().contains(next.mTargetRoute.getId())) {
                this.mTransferRequests.remove(next);
                notifyTransferred(next.mOldSessionInfo, routingSessionInfo);
                break;
            }
        }
        notifySessionUpdated(routingSessionInfo);
    }

    private void notifyRoutesUpdated() {
        Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final CallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onRoutesUpdated();
                }
            });
        }
    }

    void notifySessionUpdated(final RoutingSessionInfo routingSessionInfo) {
        Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final CallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onSessionUpdated(routingSessionInfo);
                }
            });
        }
    }

    void notifySessionReleased(final RoutingSessionInfo routingSessionInfo) {
        Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final CallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onSessionReleased(routingSessionInfo);
                }
            });
        }
    }

    void notifyRequestFailed(final int i) {
        Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final CallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onRequestFailed(i);
                }
            });
        }
    }

    void notifyTransferred(final RoutingSessionInfo routingSessionInfo, final RoutingSessionInfo routingSessionInfo2) {
        Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final CallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onTransferred(routingSessionInfo, routingSessionInfo2);
                }
            });
        }
    }

    void notifyTransferFailed(final RoutingSessionInfo routingSessionInfo, final MediaRoute2Info mediaRoute2Info) {
        Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final CallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onTransferFailed(routingSessionInfo, mediaRoute2Info);
                }
            });
        }
    }

    void updateDiscoveryPreference(final String str, final RouteDiscoveryPreference routeDiscoveryPreference) {
        if (routeDiscoveryPreference == null) {
            this.mDiscoveryPreferenceMap.remove(str);
        } else {
            if (Objects.equals(routeDiscoveryPreference, this.mDiscoveryPreferenceMap.put(str, routeDiscoveryPreference))) {
                return;
            }
            Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
            while (it.hasNext()) {
                final CallbackRecord next = it.next();
                next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        next.mCallback.onDiscoveryPreferenceChanged(str, routeDiscoveryPreference);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRouteListingPreference(final String str, final RouteListingPreference routeListingPreference) {
        RouteListingPreference routeListingPreferencePut;
        if (routeListingPreference == null) {
            routeListingPreferencePut = this.mPackageToRouteListingPreferenceMap.remove(str);
        } else {
            routeListingPreferencePut = this.mPackageToRouteListingPreferenceMap.put(str, routeListingPreference);
        }
        if (Objects.equals(routeListingPreferencePut, routeListingPreference)) {
            return;
        }
        Iterator<CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final CallbackRecord next = it.next();
            next.mExecutor.execute(new Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    next.mCallback.onRouteListingPreferenceUpdated(str, routeListingPreference);
                }
            });
        }
    }

    public List<MediaRoute2Info> getSelectedRoutes(RoutingSessionInfo routingSessionInfo) {
        List<MediaRoute2Info> list;
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        synchronized (this.mRoutesLock) {
            Stream<String> stream = routingSessionInfo.getSelectedRoutes().stream();
            Map<String, MediaRoute2Info> map = this.mRoutes;
            Objects.requireNonNull(map);
            list = (List) stream.map(new MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new MediaRouter2$$ExternalSyntheticLambda11()).collect(Collectors.toList());
        }
        return list;
    }

    public List<MediaRoute2Info> getSelectableRoutes(RoutingSessionInfo routingSessionInfo) {
        List<MediaRoute2Info> list;
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        final List<String> selectedRoutes = routingSessionInfo.getSelectedRoutes();
        synchronized (this.mRoutesLock) {
            Stream<String> streamFilter = routingSessionInfo.getSelectableRoutes().stream().filter(new Predicate() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return MediaRouter2Manager.lambda$getSelectableRoutes$11(selectedRoutes, (String) obj);
                }
            });
            Map<String, MediaRoute2Info> map = this.mRoutes;
            Objects.requireNonNull(map);
            list = (List) streamFilter.map(new MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new MediaRouter2$$ExternalSyntheticLambda11()).collect(Collectors.toList());
        }
        return list;
    }

    static /* synthetic */ boolean lambda$getSelectableRoutes$11(List list, String str) {
        return !list.contains(str);
    }

    public List<MediaRoute2Info> getDeselectableRoutes(RoutingSessionInfo routingSessionInfo) {
        List<MediaRoute2Info> list;
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        final List<String> selectedRoutes = routingSessionInfo.getSelectedRoutes();
        synchronized (this.mRoutesLock) {
            Stream<String> streamFilter = routingSessionInfo.getDeselectableRoutes().stream().filter(new Predicate() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return selectedRoutes.contains((String) obj);
                }
            });
            Map<String, MediaRoute2Info> map = this.mRoutes;
            Objects.requireNonNull(map);
            list = (List) streamFilter.map(new MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new MediaRouter2$$ExternalSyntheticLambda11()).collect(Collectors.toList());
        }
        return list;
    }

    public void selectRoute(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
            Log.w(TAG, "Ignoring selecting a route that is already selected. route=" + mediaRoute2Info);
        } else if (!routingSessionInfo.getSelectableRoutes().contains(mediaRoute2Info.getId())) {
            Log.w(TAG, "Ignoring selecting a non-selectable route=" + mediaRoute2Info);
        } else {
            try {
                this.mMediaRouterService.selectRouteWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void deselectRoute(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
            Log.w(TAG, "Ignoring deselecting a route that is not selected. route=" + mediaRoute2Info);
        } else if (!routingSessionInfo.getDeselectableRoutes().contains(mediaRoute2Info.getId())) {
            Log.w(TAG, "Ignoring deselecting a non-deselectable route=" + mediaRoute2Info);
        } else {
            try {
                this.mMediaRouterService.deselectRouteWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void releaseSession(RoutingSessionInfo routingSessionInfo) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        try {
            this.mMediaRouterService.releaseSessionWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void transferToRoute(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, UserHandle userHandle, String str) {
        try {
            this.mMediaRouterService.transferToRouteWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo.getId(), mediaRoute2Info, userHandle, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void requestCreateSession(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        if (TextUtils.isEmpty(routingSessionInfo.getClientPackageName())) {
            Log.w(TAG, "requestCreateSession: Can't create a session without package name.");
            notifyTransferFailed(routingSessionInfo, mediaRoute2Info);
        } else {
            try {
                this.mMediaRouterService.requestCreateSessionWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo, mediaRoute2Info);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private int createTransferRequest(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        int andIncrement = this.mNextRequestId.getAndIncrement();
        TransferRequest transferRequest = new TransferRequest(andIncrement, routingSessionInfo, mediaRoute2Info);
        this.mTransferRequests.add(transferRequest);
        this.mHandler.sendMessageDelayed(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((MediaRouter2Manager) obj).handleTransferTimeout((MediaRouter2Manager.TransferRequest) obj2);
            }
        }, this, transferRequest), 30000L);
        return andIncrement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTransferTimeout(TransferRequest transferRequest) {
        if (this.mTransferRequests.remove(transferRequest)) {
            notifyTransferFailed(transferRequest.mOldSessionInfo, transferRequest.mTargetRoute);
        }
    }

    private boolean areSessionsMatched(MediaController mediaController, RoutingSessionInfo routingSessionInfo) {
        String volumeControlId = mediaController.getPlaybackInfo().getVolumeControlId();
        if (volumeControlId == null) {
            return false;
        }
        if (TextUtils.equals(volumeControlId, routingSessionInfo.getId())) {
            return true;
        }
        return TextUtils.equals(volumeControlId, routingSessionInfo.getOriginalId()) && TextUtils.equals(mediaController.getPackageName(), routingSessionInfo.getOwnerPackageName());
    }

    public interface Callback {
        default void onPreferredFeaturesChanged(String str, List<String> list) {
        }

        default void onRequestFailed(int i) {
        }

        default void onRouteListingPreferenceUpdated(String str, RouteListingPreference routeListingPreference) {
        }

        default void onRoutesUpdated() {
        }

        default void onSessionReleased(RoutingSessionInfo routingSessionInfo) {
        }

        default void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
        }

        default void onTransferFailed(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
        }

        default void onTransferred(RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
        }

        default void onDiscoveryPreferenceChanged(String str, RouteDiscoveryPreference routeDiscoveryPreference) {
            onPreferredFeaturesChanged(str, routeDiscoveryPreference.getPreferredFeatures());
        }
    }

    final class CallbackRecord {
        public final Callback mCallback;
        public final Executor mExecutor;

        CallbackRecord(MediaRouter2Manager mediaRouter2Manager, Executor executor, Callback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof CallbackRecord) && this.mCallback == ((CallbackRecord) obj).mCallback;
        }

        public int hashCode() {
            return this.mCallback.hashCode();
        }
    }

    static final class TransferRequest {
        public final RoutingSessionInfo mOldSessionInfo;
        public final int mRequestId;
        public final MediaRoute2Info mTargetRoute;

        TransferRequest(int i, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
            this.mRequestId = i;
            this.mOldSessionInfo = routingSessionInfo;
            this.mTargetRoute = mediaRoute2Info;
        }
    }

    class Client extends IMediaRouter2Manager.Stub {
        @Override // android.media.IMediaRouter2Manager
        public void invalidateInstance() {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyDeviceSuggestionsUpdated(String str, String str2, List<SuggestedDeviceInfo> list) {
        }

        Client() {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionCreated(int i, RoutingSessionInfo routingSessionInfo) {
            MediaRouter2Manager.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2Manager) obj).createSessionOnHandler(((Integer) obj2).intValue(), (RoutingSessionInfo) obj3);
                }
            }, MediaRouter2Manager.this, Integer.valueOf(i), routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionUpdated(RoutingSessionInfo routingSessionInfo) {
            MediaRouter2Manager.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRouter2Manager) obj).handleSessionsUpdatedOnHandler((RoutingSessionInfo) obj2);
                }
            }, MediaRouter2Manager.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionReleased(RoutingSessionInfo routingSessionInfo) {
            MediaRouter2Manager.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRouter2Manager) obj).notifySessionReleased((RoutingSessionInfo) obj2);
                }
            }, MediaRouter2Manager.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRequestFailed(int i, int i2) {
            MediaRouter2Manager.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2Manager) obj).handleFailureOnHandler(((Integer) obj2).intValue(), ((Integer) obj3).intValue());
                }
            }, MediaRouter2Manager.this, Integer.valueOf(i), Integer.valueOf(i2)));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyDiscoveryPreferenceChanged(String str, RouteDiscoveryPreference routeDiscoveryPreference) {
            MediaRouter2Manager.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2Manager) obj).updateDiscoveryPreference((String) obj2, (RouteDiscoveryPreference) obj3);
                }
            }, MediaRouter2Manager.this, str, routeDiscoveryPreference));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRouteListingPreferenceChange(String str, RouteListingPreference routeListingPreference) {
            MediaRouter2Manager.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2Manager) obj).updateRouteListingPreference((String) obj2, (RouteListingPreference) obj3);
                }
            }, MediaRouter2Manager.this, str, routeListingPreference));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRoutesUpdated(List<MediaRoute2Info> list) {
            MediaRouter2Manager.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((MediaRouter2Manager) obj).updateRoutesOnHandler((List) obj2);
                }
            }, MediaRouter2Manager.this, list));
        }
    }
}
