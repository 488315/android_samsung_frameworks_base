package android.media;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.IMediaRoute2ProviderService;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.MediaRoute2ProviderService;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.AudioPolicy;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongSparseArray;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.media.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public abstract class MediaRoute2ProviderService extends Service {
    public static final String CATEGORY_SELF_SCAN_ONLY = "android.media.MediaRoute2ProviderService.SELF_SCAN_ONLY";
    public static final String CATEGORY_SYSTEM_MEDIA = "android.media.MediaRoute2ProviderService.SYSTEM_MEDIA";
    private static final int MAX_REQUEST_IDS_SIZE = 500;
    public static final int REASON_FAILED_TO_REROUTE_SYSTEM_MEDIA = 6;
    public static final int REASON_INVALID_COMMAND = 4;
    public static final int REASON_NETWORK_ERROR = 2;
    public static final int REASON_REJECTED = 1;
    public static final int REASON_ROUTE_NOT_AVAILABLE = 3;
    public static final int REASON_UNIMPLEMENTED = 5;
    public static final int REASON_UNKNOWN_ERROR = 0;
    public static final long REQUEST_ID_NONE = 0;
    public static final String SERVICE_INTERFACE = "android.media.MediaRoute2ProviderService";
    private volatile MediaRoute2ProviderInfo mProviderInfo;
    private IMediaRoute2ProviderServiceCallback mRemoteCallback;
    private MediaRoute2ProviderServiceStub mStub;
    private static final String TAG = "MR2ProviderService";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private final Object mSessionLock = new Object();
    private final Object mRequestIdsLock = new Object();
    private final AtomicBoolean mStatePublishScheduled = new AtomicBoolean(false);
    private final AtomicBoolean mSessionUpdateScheduled = new AtomicBoolean(false);
    private final Deque<Long> mRequestIds = new ArrayDeque(500);
    private final LongSparseArray<Integer> mSystemRoutingSessionCreationRequests = new LongSparseArray<>();
    private final ArrayMap<String, RoutingSessionInfo> mSessionInfos = new ArrayMap<>();
    private final ArrayMap<String, MediaStreams> mOngoingMediaStreams = new ArrayMap<>();
    private final ArrayMap<String, RoutingSessionInfo> mPendingSystemSessionReleases = new ArrayMap<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public abstract void onCreateSession(long j, String str, String str2, Bundle bundle);

    public abstract void onDeselectRoute(long j, String str, String str2);

    public void onDiscoveryPreferenceChanged(RouteDiscoveryPreference routeDiscoveryPreference) {
    }

    public abstract void onReleaseSession(long j, String str);

    public abstract void onSelectRoute(long j, String str, String str2);

    public abstract void onSetRouteVolume(long j, String str, int i);

    public abstract void onSetSessionVolume(long j, String str, int i);

    public abstract void onTransferToRoute(long j, String str, String str2);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (this.mStub == null) {
            this.mStub = new MediaRoute2ProviderServiceStub();
        }
        return this.mStub;
    }

    public final RoutingSessionInfo getSessionInfo(String str) {
        RoutingSessionInfo routingSessionInfo;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("sessionId must not be empty");
        }
        synchronized (this.mSessionLock) {
            routingSessionInfo = this.mSessionInfos.get(str);
        }
        return routingSessionInfo;
    }

    public final List<RoutingSessionInfo> getAllSessionInfo() {
        ArrayList arrayList;
        synchronized (this.mSessionLock) {
            arrayList = new ArrayList(this.mSessionInfos.values());
        }
        return arrayList;
    }

    public final void notifySessionCreated(long j, RoutingSessionInfo routingSessionInfo) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        if (DEBUG) {
            Log.d(TAG, "notifySessionCreated: Creating a session. requestId=" + j + ", sessionInfo=" + routingSessionInfo);
        }
        if (j != 0 && !removeRequestId(j)) {
            Log.w(TAG, "notifySessionCreated: The requestId doesn't exist. requestId=" + j);
            return;
        }
        String id = routingSessionInfo.getId();
        synchronized (this.mSessionLock) {
            if (this.mSessionInfos.containsKey(id)) {
                Log.w(TAG, "notifySessionCreated: Ignoring duplicate session id.");
                return;
            }
            this.mSessionInfos.put(routingSessionInfo.getId(), routingSessionInfo);
            IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback = this.mRemoteCallback;
            if (iMediaRoute2ProviderServiceCallback == null) {
                return;
            }
            try {
                iMediaRoute2ProviderServiceCallback.notifySessionCreated(j, routingSessionInfo);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to notify session created.");
            }
        }
    }

    public final MediaStreams notifySystemRoutingSessionCreated(long j, final RoutingSessionInfo routingSessionInfo, MediaStreamsFormats mediaStreamsFormats) {
        Integer num;
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        Objects.requireNonNull(mediaStreamsFormats, "formats must not be null");
        if (DEBUG) {
            Log.d(TAG, "notifySystemRoutingSessionCreated: Creating a session. requestId=" + j + ", sessionInfo=" + routingSessionInfo);
        }
        synchronized (this.mRequestIdsLock) {
            num = this.mSystemRoutingSessionCreationRequests.get(j);
            this.mSystemRoutingSessionCreationRequests.remove(j);
        }
        if (num == null) {
            throw new IllegalStateException("Unexpected system routing session created (request id=" + j + "):" + routingSessionInfo);
        }
        if (this.mRemoteCallback == null) {
            throw new IllegalStateException("Unexpected: remote callback is null.");
        }
        MediaRoute2ProviderInfo mediaRoute2ProviderInfo = this.mProviderInfo;
        int i = 0;
        for (String str : routingSessionInfo.getSelectedRoutes()) {
            MediaRoute2Info mediaRoute2Info = mediaRoute2ProviderInfo.mRoutes.get(str);
            if (mediaRoute2Info == null) {
                throw new IllegalArgumentException("Invalid selected route with id: " + str);
            }
            i |= mediaRoute2Info.getSupportedRoutingTypes();
        }
        if ((i & 1) == 0) {
            throw new IllegalArgumentException("Selected routes for system media don't support any system media routing types.");
        }
        AudioFormat audioFormat = mediaStreamsFormats.mAudioFormat;
        MediaStreams.Builder builder = new MediaStreams.Builder(routingSessionInfo);
        if (audioFormat != null) {
            populateAudioStream(audioFormat, num.intValue(), builder);
        }
        MediaStreams build = builder.build();
        if (build.mAudioRecord == null) {
            Log.e(TAG, "Audio record is not populated. Returning an empty stream and scheduling the session release for: " + routingSessionInfo);
            this.mHandler.post(new Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MediaRoute2ProviderService.this.lambda$notifySystemRoutingSessionCreated$0(routingSessionInfo);
                }
            });
            notifyRequestFailed(j, 6);
            return null;
        }
        synchronized (this.mSessionLock) {
            try {
                this.mRemoteCallback.notifySessionCreated(j, routingSessionInfo);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            this.mOngoingMediaStreams.put(routingSessionInfo.getOriginalId(), build);
        }
        return build;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifySystemRoutingSessionCreated$0(RoutingSessionInfo routingSessionInfo) {
        onReleaseSession(0L, routingSessionInfo.getOriginalId());
    }

    private void populateAudioStream(AudioFormat audioFormat, int i, MediaStreams.Builder builder) {
        AudioMixingRule.Builder addRule = new AudioMixingRule.Builder().addRule(new AudioAttributes.Builder().setUsage(1).build(), 1);
        if (i != -1) {
            addRule.addMixRule(4, Integer.valueOf(i));
        }
        android.media.audiopolicy.AudioMix build = new AudioMix.Builder(addRule.build()).setFormat(audioFormat).setRouteFlags(2).build();
        AudioPolicy build2 = new AudioPolicy.Builder(this).setLooper(this.mHandler.getLooper()).addMix(build).build();
        AudioManager audioManager = (AudioManager) getSystemService(AudioManager.class);
        if (audioManager == null) {
            Log.e(TAG, "Couldn't fetch the audio manager.");
            return;
        }
        if (audioManager.registerAudioPolicy(build2) != 0) {
            Log.e(TAG, "Failed to register the audio policy.");
            return;
        }
        AudioRecord createAudioRecordSink = build2.createAudioRecordSink(build);
        if (createAudioRecordSink == null) {
            Log.e(TAG, "Audio record creation failed.");
            audioManager.unregisterAudioPolicy(build2);
        } else {
            builder.setAudioStream(build2, createAudioRecordSink);
        }
    }

    public final void notifySessionUpdated(RoutingSessionInfo routingSessionInfo) {
        Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        if (DEBUG) {
            Log.d(TAG, "notifySessionUpdated: Updating session id=" + routingSessionInfo);
        }
        String id = routingSessionInfo.getId();
        synchronized (this.mSessionLock) {
            MediaStreams mediaStreams = this.mOngoingMediaStreams.get(id);
            if (Flags.enableMirroringInMediaRouter2() && mediaStreams != null) {
                mediaStreams.mSessionInfo = routingSessionInfo;
            } else if (this.mSessionInfos.containsKey(id)) {
                this.mSessionInfos.put(id, routingSessionInfo);
            } else {
                Log.w(TAG, "notifySessionUpdated: Ignoring unknown session info.");
                return;
            }
            scheduleUpdateSessions();
        }
    }

    public final void notifySessionReleased(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("sessionId must not be empty");
        }
        if (DEBUG) {
            Log.d(TAG, "notifySessionReleased: Releasing session id=" + str);
        }
        synchronized (this.mSessionLock) {
            RoutingSessionInfo remove = this.mSessionInfos.remove(str);
            if (Flags.enableMirroringInMediaRouter2()) {
                if (remove == null) {
                    remove = maybeReleaseMediaStreams(str);
                }
                if (remove == null) {
                    remove = this.mPendingSystemSessionReleases.remove(str);
                }
            }
            if (remove == null) {
                Log.w(TAG, "notifySessionReleased: Ignoring unknown session info.");
                return;
            }
            IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback = this.mRemoteCallback;
            if (iMediaRoute2ProviderServiceCallback == null) {
                return;
            }
            try {
                iMediaRoute2ProviderServiceCallback.notifySessionReleased(remove);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed to notify session released.", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RoutingSessionInfo maybeReleaseMediaStreams(String str) {
        if (!Flags.enableMirroringInMediaRouter2()) {
            return null;
        }
        synchronized (this.mSessionLock) {
            MediaStreams remove = this.mOngoingMediaStreams.remove(str);
            if (remove == null) {
                return null;
            }
            releaseAudioStream(remove.mAudioPolicy, remove.mAudioRecord);
            return remove.mSessionInfo;
        }
    }

    private void releaseAudioStream(AudioPolicy audioPolicy, AudioRecord audioRecord) {
        AudioManager audioManager;
        if (audioPolicy == null || (audioManager = (AudioManager) getSystemService(AudioManager.class)) == null) {
            return;
        }
        audioRecord.stop();
        audioManager.unregisterAudioPolicy(audioPolicy);
    }

    public final void notifyRequestFailed(long j, int i) {
        if (this.mRemoteCallback == null) {
            return;
        }
        if (!removeRequestId(j)) {
            Log.w(TAG, "notifyRequestFailed: The requestId doesn't exist. requestId=" + j);
        } else {
            try {
                this.mRemoteCallback.notifyRequestFailed(j, i);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to notify that the request has failed.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateSystemRoutingSession$1(long j) {
        notifyRequestFailed(j, 5);
    }

    public void onCreateSystemRoutingSession(final long j, String str, SystemRoutingSessionParams systemRoutingSessionParams) {
        this.mHandler.post(new Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MediaRoute2ProviderService.this.lambda$onCreateSystemRoutingSession$1(j);
            }
        });
    }

    public final void notifyRoutes(Collection<MediaRoute2Info> collection) {
        Objects.requireNonNull(collection, "routes must not be null");
        ArrayList arrayList = new ArrayList(collection.size());
        for (MediaRoute2Info mediaRoute2Info : collection) {
            if (Flags.enableMirroringInMediaRouter2() && mediaRoute2Info.supportsRemoteRouting() && mediaRoute2Info.supportsSystemMediaRouting() && mediaRoute2Info.getDeduplicationIds().isEmpty()) {
                throw new IllegalArgumentException(TextUtils.formatSimple("Route with id='%s' name='%s' supports both system media and remote type routing, but doesn't contain a deduplication id, which it needs. You can add the route id as a deduplication id.", mediaRoute2Info.getOriginalId(), mediaRoute2Info.getName()));
            }
            if (mediaRoute2Info.isSystemRouteType()) {
                Log.w(TAG, "Attempting to add a system route type from a non-system route provider. Overriding type to TYPE_UNKNOWN. Route: " + mediaRoute2Info);
                arrayList.add(new MediaRoute2Info.Builder(mediaRoute2Info).setType(0).build());
            } else {
                arrayList.add(mediaRoute2Info);
            }
        }
        this.mProviderInfo = new MediaRoute2ProviderInfo.Builder().addRoutes(arrayList).build();
        schedulePublishState();
    }

    void setCallback(IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) {
        this.mRemoteCallback = iMediaRoute2ProviderServiceCallback;
        schedulePublishState();
        scheduleUpdateSessions();
    }

    void schedulePublishState() {
        if (this.mStatePublishScheduled.compareAndSet(false, true)) {
            this.mHandler.post(new Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MediaRoute2ProviderService.this.publishState();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishState() {
        if (!this.mStatePublishScheduled.compareAndSet(true, false) || this.mRemoteCallback == null || this.mProviderInfo == null) {
            return;
        }
        try {
            this.mRemoteCallback.notifyProviderUpdated(this.mProviderInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to publish provider state.", e);
        }
    }

    void scheduleUpdateSessions() {
        if (this.mSessionUpdateScheduled.compareAndSet(false, true)) {
            this.mHandler.post(new Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaRoute2ProviderService.this.updateSessions();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSessions() {
        final ArrayList arrayList;
        if (this.mSessionUpdateScheduled.compareAndSet(true, false) && this.mRemoteCallback != null) {
            synchronized (this.mSessionLock) {
                arrayList = new ArrayList(this.mSessionInfos.values());
                if (Flags.enableMirroringInMediaRouter2()) {
                    this.mOngoingMediaStreams.values().forEach(new Consumer() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            arrayList.add(((MediaRoute2ProviderService.MediaStreams) obj).mSessionInfo);
                        }
                    });
                }
            }
            try {
                this.mRemoteCallback.notifySessionsUpdated(arrayList);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to notify session info changed.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRequestId(long j) {
        synchronized (this.mRequestIdsLock) {
            if (this.mRequestIds.size() >= 500) {
                this.mRequestIds.removeFirst();
            }
            this.mRequestIds.addLast(Long.valueOf(j));
        }
    }

    private boolean removeRequestId(long j) {
        boolean removeFirstOccurrence;
        synchronized (this.mRequestIdsLock) {
            removeFirstOccurrence = this.mRequestIds.removeFirstOccurrence(Long.valueOf(j));
        }
        return removeFirstOccurrence;
    }

    final class MediaRoute2ProviderServiceStub extends IMediaRoute2ProviderService.Stub {
        MediaRoute2ProviderServiceStub() {
        }

        private boolean checkCallerIsSystem() {
            return Binder.getCallingUid() == 1000;
        }

        private boolean checkSessionIdIsValid(String str, String str2) {
            boolean z;
            if (TextUtils.isEmpty(str)) {
                Log.w(MediaRoute2ProviderService.TAG, str2 + ": Ignoring empty sessionId from system service.");
                return false;
            }
            if (Flags.enableMirroringInMediaRouter2()) {
                synchronized (MediaRoute2ProviderService.this.mSessionLock) {
                    z = MediaRoute2ProviderService.this.mOngoingMediaStreams.containsKey(str);
                }
            } else {
                z = false;
            }
            if (z || MediaRoute2ProviderService.this.getSessionInfo(str) != null) {
                return true;
            }
            Log.w(MediaRoute2ProviderService.TAG, str2 + ": Ignoring unknown session from system service. sessionId=" + str);
            return false;
        }

        private boolean checkRouteIdIsValid(String str, String str2) {
            if (TextUtils.isEmpty(str)) {
                Log.w(MediaRoute2ProviderService.TAG, str2 + ": Ignoring empty routeId from system service.");
                return false;
            }
            if (MediaRoute2ProviderService.this.mProviderInfo != null && MediaRoute2ProviderService.this.mProviderInfo.getRoute(str) != null) {
                return true;
            }
            Log.w(MediaRoute2ProviderService.TAG, str2 + ": Ignoring unknown route from system service. routeId=" + str);
            return false;
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setCallback(IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) {
            if (checkCallerIsSystem()) {
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda9
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((MediaRoute2ProviderService) obj).setCallback((IMediaRoute2ProviderServiceCallback) obj2);
                    }
                }, MediaRoute2ProviderService.this, iMediaRoute2ProviderServiceCallback));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void updateDiscoveryPreference(RouteDiscoveryPreference routeDiscoveryPreference) {
            if (checkCallerIsSystem()) {
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((MediaRoute2ProviderService) obj).onDiscoveryPreferenceChanged((RouteDiscoveryPreference) obj2);
                    }
                }, MediaRoute2ProviderService.this, routeDiscoveryPreference));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setRouteVolume(long j, String str, int i) {
            if (checkCallerIsSystem() && checkRouteIdIsValid(str, "setRouteVolume")) {
                MediaRoute2ProviderService.this.addRequestId(j);
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((MediaRoute2ProviderService) obj).onSetRouteVolume(((Long) obj2).longValue(), (String) obj3, ((Integer) obj4).intValue());
                    }
                }, MediaRoute2ProviderService.this, Long.valueOf(j), str, Integer.valueOf(i)));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void requestCreateSession(long j, String str, String str2, Bundle bundle) {
            if (checkCallerIsSystem() && checkRouteIdIsValid(str2, "requestCreateSession")) {
                MediaRoute2ProviderService.this.addRequestId(j);
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.function.QuintConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                        ((MediaRoute2ProviderService) obj).onCreateSession(((Long) obj2).longValue(), (String) obj3, (String) obj4, (Bundle) obj5);
                    }
                }, MediaRoute2ProviderService.this, Long.valueOf(j), str, str2, bundle));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void requestCreateSystemMediaSession(long j, int i, String str, String str2, Bundle bundle) {
            if (Flags.enableMirroringInMediaRouter2() && checkCallerIsSystem() && checkRouteIdIsValid(str2, "requestCreateSession")) {
                synchronized (MediaRoute2ProviderService.this.mRequestIdsLock) {
                    MediaRoute2ProviderService.this.mSystemRoutingSessionCreationRequests.put(j, Integer.valueOf(i));
                }
                SystemRoutingSessionParams.Builder packageName = new SystemRoutingSessionParams.Builder().setPackageName(str);
                if (bundle != null) {
                    packageName.setExtras(bundle);
                }
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda7
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((MediaRoute2ProviderService) obj).onCreateSystemRoutingSession(((Long) obj2).longValue(), (String) obj3, (MediaRoute2ProviderService.SystemRoutingSessionParams) obj4);
                    }
                }, MediaRoute2ProviderService.this, Long.valueOf(j), str2, packageName.build()));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void selectRoute(long j, String str, String str2) {
            if (checkCallerIsSystem() && checkSessionIdIsValid(str, "selectRoute") && checkRouteIdIsValid(str2, "selectRoute")) {
                MediaRoute2ProviderService.this.addRequestId(j);
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda6
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((MediaRoute2ProviderService) obj).onSelectRoute(((Long) obj2).longValue(), (String) obj3, (String) obj4);
                    }
                }, MediaRoute2ProviderService.this, Long.valueOf(j), str, str2));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void deselectRoute(long j, String str, String str2) {
            if (checkCallerIsSystem() && checkSessionIdIsValid(str, "deselectRoute") && checkRouteIdIsValid(str2, "deselectRoute")) {
                MediaRoute2ProviderService.this.addRequestId(j);
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda2
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((MediaRoute2ProviderService) obj).onDeselectRoute(((Long) obj2).longValue(), (String) obj3, (String) obj4);
                    }
                }, MediaRoute2ProviderService.this, Long.valueOf(j), str, str2));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void transferToRoute(long j, String str, String str2) {
            if (checkCallerIsSystem() && checkSessionIdIsValid(str, "transferToRoute") && checkRouteIdIsValid(str2, "transferToRoute")) {
                MediaRoute2ProviderService.this.addRequestId(j);
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda4
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((MediaRoute2ProviderService) obj).onTransferToRoute(((Long) obj2).longValue(), (String) obj3, (String) obj4);
                    }
                }, MediaRoute2ProviderService.this, Long.valueOf(j), str, str2));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setSessionVolume(long j, String str, int i) {
            if (checkCallerIsSystem() && checkSessionIdIsValid(str, "setSessionVolume")) {
                MediaRoute2ProviderService.this.addRequestId(j);
                MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.function.QuadConsumer
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                        ((MediaRoute2ProviderService) obj).onSetSessionVolume(((Long) obj2).longValue(), (String) obj3, ((Integer) obj4).intValue());
                    }
                }, MediaRoute2ProviderService.this, Long.valueOf(j), str, Integer.valueOf(i)));
            }
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void releaseSession(long j, String str) {
            if (checkCallerIsSystem()) {
                synchronized (MediaRoute2ProviderService.this.mSessionLock) {
                    RoutingSessionInfo maybeReleaseMediaStreams = MediaRoute2ProviderService.this.maybeReleaseMediaStreams(str);
                    if (maybeReleaseMediaStreams != null) {
                        MediaRoute2ProviderService.this.mPendingSystemSessionReleases.put(str, maybeReleaseMediaStreams);
                    } else if (!checkSessionIdIsValid(str, "releaseSession")) {
                        return;
                    }
                    MediaRoute2ProviderService.this.addRequestId(j);
                    MediaRoute2ProviderService.this.mHandler.sendMessage(PooledLambda.obtainMessage(new TriConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda8
                        @Override // com.android.internal.util.function.TriConsumer
                        public final void accept(Object obj, Object obj2, Object obj3) {
                            ((MediaRoute2ProviderService) obj).onReleaseSession(((Long) obj2).longValue(), (String) obj3);
                        }
                    }, MediaRoute2ProviderService.this, Long.valueOf(j), str));
                }
            }
        }
    }

    public static final class MediaStreams {
        private final AudioPolicy mAudioPolicy;
        private final AudioRecord mAudioRecord;
        private RoutingSessionInfo mSessionInfo;

        private MediaStreams(Builder builder) {
            this.mSessionInfo = builder.mSessionInfo;
            this.mAudioPolicy = builder.mAudioPolicy;
            this.mAudioRecord = builder.mAudioRecord;
        }

        public AudioRecord getAudioRecord() {
            return this.mAudioRecord;
        }

        public static final class Builder {
            private AudioPolicy mAudioPolicy;
            private AudioRecord mAudioRecord;
            private RoutingSessionInfo mSessionInfo;

            Builder(RoutingSessionInfo routingSessionInfo) {
                this.mSessionInfo = (RoutingSessionInfo) Objects.requireNonNull(routingSessionInfo);
            }

            public Builder setAudioStream(AudioPolicy audioPolicy, AudioRecord audioRecord) {
                this.mAudioPolicy = (AudioPolicy) Objects.requireNonNull(audioPolicy);
                this.mAudioRecord = (AudioRecord) Objects.requireNonNull(audioRecord);
                return this;
            }

            public MediaStreams build() {
                return new MediaStreams(this);
            }
        }
    }

    public static final class SystemRoutingSessionParams {
        private final Bundle mExtras;
        private final String mPackageName;

        private SystemRoutingSessionParams(Builder builder) {
            this.mPackageName = builder.mPackageName;
            this.mExtras = builder.mExtras;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private String mPackageName = "";
            private Bundle mExtras = Bundle.EMPTY;

            public Builder setExtras(Bundle bundle) {
                this.mExtras = (Bundle) Objects.requireNonNull(bundle);
                return this;
            }

            public Builder setPackageName(String str) {
                this.mPackageName = (String) Objects.requireNonNull(str);
                return this;
            }

            public SystemRoutingSessionParams build() {
                return new SystemRoutingSessionParams(this);
            }
        }
    }

    public static final class MediaStreamsFormats {
        private final AudioFormat mAudioFormat;

        private MediaStreamsFormats(Builder builder) {
            this.mAudioFormat = builder.mAudioFormat;
        }

        public AudioFormat getAudioFormat() {
            return this.mAudioFormat;
        }

        public static final class Builder {
            private AudioFormat mAudioFormat;

            public Builder setAudioFormat(AudioFormat audioFormat) {
                this.mAudioFormat = (AudioFormat) Objects.requireNonNull(audioFormat);
                return this;
            }

            public MediaStreamsFormats build() {
                return new MediaStreamsFormats(this);
            }
        }
    }
}
