package com.android.systemui.media.controls.domain.pipeline;

import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.UserHandle;
import android.util.Log;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.util.LocalMediaManagerFactory;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaDeviceManager implements MediaDataManager.Listener {
    public static final MediaDeviceData EMPTY_AND_DISABLED_MEDIA_DEVICE_DATA;
    public final Executor bgExecutor;
    public final ConfigurationController configurationController;
    public final Context context;
    public final MediaControllerFactory controllerFactory;
    public final Executor fgExecutor;
    public final LocalMediaManagerFactory localMediaManagerFactory;
    public final MediaDeviceLogger logger;
    public final Lazy mr2manager;
    public final MediaMuteAwaitConnectionManagerFactory muteAwaitConnectionManagerFactory;
    public final Set listeners = new LinkedHashSet();
    public final Map entries = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        EMPTY_AND_DISABLED_MEDIA_DEVICE_DATA = new MediaDeviceData(false, null, null, null, null, false, null, 88, null);
    }

    public MediaDeviceManager(Context context, MediaControllerFactory mediaControllerFactory, LocalMediaManagerFactory localMediaManagerFactory, Lazy lazy, MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory, ConfigurationController configurationController, Lazy lazy2, Executor executor, Executor executor2, MediaDeviceLogger mediaDeviceLogger, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.controllerFactory = mediaControllerFactory;
        this.localMediaManagerFactory = localMediaManagerFactory;
        this.mr2manager = lazy;
        this.muteAwaitConnectionManagerFactory = mediaMuteAwaitConnectionManagerFactory;
        this.configurationController = configurationController;
        this.fgExecutor = executor;
        this.bgExecutor = executor2;
        this.logger = mediaDeviceLogger;
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, new MediaDeviceManager$intentReceiver$1(this), new IntentFilter("android.media.STREAM_DEVICES_CHANGED_ACTION"), null, UserHandle.ALL, 0, null, 48);
    }

    public final void dump(PrintWriter printWriter) {
        MediaController.PlaybackInfo playbackInfo;
        MediaController.PlaybackInfo playbackInfo2;
        printWriter.println("MediaDeviceManager state:");
        for (Map.Entry entry : ((LinkedHashMap) this.entries).entrySet()) {
            String str = (String) entry.getKey();
            Entry entry2 = (Entry) entry.getValue();
            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "  key=", str);
            MediaController mediaController = entry2.controller;
            String str2 = null;
            RoutingSessionInfo routingSessionForMediaController = mediaController != null ? ((MediaRouter2Manager) MediaDeviceManager.this.mr2manager.get()).getRoutingSessionForMediaController(mediaController) : null;
            List selectedRoutes = routingSessionForMediaController != null ? ((MediaRouter2Manager) MediaDeviceManager.this.mr2manager.get()).getSelectedRoutes(routingSessionForMediaController) : null;
            MediaDeviceData mediaDeviceData = entry2.current;
            printWriter.println("    current device is " + ((Object) (mediaDeviceData != null ? mediaDeviceData.name : null)));
            MediaController mediaController2 = entry2.controller;
            Integer valueOf = (mediaController2 == null || (playbackInfo2 = mediaController2.getPlaybackInfo()) == null) ? null : Integer.valueOf(playbackInfo2.getPlaybackType());
            printWriter.println("    PlaybackType=" + valueOf + " (1 for local, 2 for remote) cached=" + entry2.playbackType);
            MediaController mediaController3 = entry2.controller;
            if (mediaController3 != null && (playbackInfo = mediaController3.getPlaybackInfo()) != null) {
                str2 = playbackInfo.getVolumeControlId();
            }
            printWriter.println("    volumeControlId=" + str2 + " cached= " + entry2.playbackVolumeControlId);
            StringBuilder sb = new StringBuilder("    routingSession=");
            sb.append(routingSessionForMediaController);
            printWriter.println(sb.toString());
            printWriter.println("    selectedRoutes=" + selectedRoutes);
            printWriter.println("    currentConnectedDevice=" + entry2.localMediaManager.getCurrentConnectedDevice());
        }
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        Entry entry;
        if (str2 != null && !str2.equals(str) && (entry = (Entry) this.entries.remove(str2)) != null) {
            entry.stop();
        }
        Entry entry2 = (Entry) ((LinkedHashMap) this.entries).get(str);
        if (entry2 != null) {
            MediaController mediaController = entry2.controller;
            if (Intrinsics.areEqual(mediaController != null ? mediaController.getSessionToken() : null, mediaData.token)) {
                return;
            }
        }
        if (entry2 != null) {
            entry2.stop();
        }
        MediaDeviceData mediaDeviceData = mediaData.device;
        if (mediaDeviceData != null) {
            processDevice(str, str2, mediaDeviceData);
            return;
        }
        MediaSession.Token token = mediaData.token;
        MediaController create = token != null ? this.controllerFactory.create(token) : null;
        MediaSession.Token sessionToken = create != null ? create.getSessionToken() : null;
        LocalMediaManagerFactory localMediaManagerFactory = this.localMediaManagerFactory;
        Context context = localMediaManagerFactory.context;
        String str3 = mediaData.packageName;
        LocalBluetoothManager localBluetoothManager = localMediaManagerFactory.localBluetoothManager;
        LocalMediaManager localMediaManager = new LocalMediaManager(localMediaManagerFactory.context, localBluetoothManager, InfoMediaManager.createInstance(context, str3, null, localBluetoothManager, sessionToken), str3);
        MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory = this.muteAwaitConnectionManagerFactory;
        Entry entry3 = new Entry(str, str2, create, localMediaManager, new MediaMuteAwaitConnectionManager(mediaMuteAwaitConnectionManagerFactory.mainExecutor, localMediaManager, mediaMuteAwaitConnectionManagerFactory.context, mediaMuteAwaitConnectionManagerFactory.deviceIconUtil, mediaMuteAwaitConnectionManagerFactory.logger));
        this.entries.put(str, entry3);
        MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
        mediaDeviceManager.bgExecutor.execute(new MediaDeviceManager$Entry$start$1(entry3, mediaDeviceManager));
    }

    @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str, boolean z) {
        Entry entry = (Entry) this.entries.remove(str);
        if (entry != null) {
            entry.stop();
        }
        if (entry != null) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((MediaDataCombineLatest) it.next()).remove(str, z);
            }
        }
    }

    public final void processDevice(String str, String str2, MediaDeviceData mediaDeviceData) {
        for (MediaDataCombineLatest mediaDataCombineLatest : this.listeners) {
            if (str2 != null) {
                mediaDataCombineLatest.getClass();
                if (!str2.equals(str) && mediaDataCombineLatest.entries.containsKey(str2)) {
                    Map map = mediaDataCombineLatest.entries;
                    Pair pair = (Pair) map.remove(str2);
                    map.put(str, new Pair(pair != null ? (MediaData) pair.getFirst() : null, mediaDeviceData));
                    mediaDataCombineLatest.update(str, str2);
                }
            }
            Map map2 = mediaDataCombineLatest.entries;
            Pair pair2 = (Pair) ((LinkedHashMap) map2).get(str);
            map2.put(str, new Pair(pair2 != null ? (MediaData) pair2.getFirst() : null, mediaDeviceData));
            mediaDataCombineLatest.update(str, str);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Entry extends MediaController.Callback implements LocalMediaManager.DeviceCallback, BluetoothLeBroadcast.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public AboutToConnectDevice aboutToConnectDeviceOverride;
        public final MediaDeviceManager$Entry$configListener$1 configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$Entry$configListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLocaleListChanged() {
                int i = MediaDeviceManager.Entry.$r8$clinit;
                MediaDeviceManager.Entry.this.updateCurrent();
            }
        };
        public final MediaController controller;
        public MediaDeviceData current;
        public final String key;
        public final LocalMediaManager localMediaManager;
        public final MediaMuteAwaitConnectionManager muteAwaitConnectionManager;
        public final String oldKey;
        public int playbackType;
        public String playbackVolumeControlId;
        public boolean started;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$Entry$configListener$1] */
        public Entry(String str, String str2, MediaController mediaController, LocalMediaManager localMediaManager, MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager) {
            this.key = str;
            this.oldKey = str2;
            this.controller = mediaController;
            this.localMediaManager = localMediaManager;
            this.muteAwaitConnectionManager = mediaMuteAwaitConnectionManager;
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public final void onAboutToConnectDeviceAdded(String str, Drawable drawable, String str2) {
            MediaDevice mediaDeviceById = this.localMediaManager.getMediaDeviceById(str);
            MediaDeviceData mediaDeviceData = new MediaDeviceData(true, drawable, str2, null, null, false, null, 88, null);
            MediaDevice mediaDeviceById2 = this.localMediaManager.getMediaDeviceById(str);
            mediaDeviceData.customMediaDeviceData.deviceType = mediaDeviceById2 != null ? Integer.valueOf(mediaDeviceById2.getDeviceType()) : null;
            Unit unit = Unit.INSTANCE;
            AboutToConnectDevice aboutToConnectDevice = new AboutToConnectDevice(mediaDeviceById, mediaDeviceData);
            this.aboutToConnectDeviceOverride = aboutToConnectDevice;
            Log.d("MediaDeviceManager", "onAboutToConnectDeviceAdded backupMediaDeviceData=" + aboutToConnectDevice.backupMediaDeviceData);
            updateCurrent();
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public final void onAboutToConnectDeviceRemoved() {
            Log.d("MediaDeviceManager", "onAboutToConnectDeviceRemoved");
            this.aboutToConnectDeviceOverride = null;
            updateCurrent();
        }

        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            int playbackType = playbackInfo.getPlaybackType();
            String volumeControlId = playbackInfo.getVolumeControlId();
            if (playbackType == this.playbackType && Intrinsics.areEqual(volumeControlId, this.playbackVolumeControlId)) {
                return;
            }
            this.playbackType = playbackType;
            this.playbackVolumeControlId = volumeControlId;
            updateCurrent();
        }

        public final void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            MediaDeviceLogger mediaDeviceLogger = MediaDeviceManager.this.logger;
            String bluetoothLeBroadcastMetadata2 = bluetoothLeBroadcastMetadata.toString();
            mediaDeviceLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda0 = new MediaDeviceLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = mediaDeviceLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.int1 = i;
            logMessageImpl.str1 = bluetoothLeBroadcastMetadata2;
            logBuffer.commit(obtain);
            updateCurrent();
        }

        public final void onBroadcastStartFailed(int i) {
            MediaDeviceLogger mediaDeviceLogger = MediaDeviceManager.this.logger;
            mediaDeviceLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda0 = new MediaDeviceLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = mediaDeviceLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = "onBroadcastStartFailed";
            logMessageImpl.int1 = i;
            logBuffer.commit(obtain);
        }

        public final void onBroadcastStarted(int i, int i2) {
            MediaDeviceManager.this.logger.logBroadcastEvent(i, i2, "onBroadcastStarted");
            updateCurrent();
        }

        public final void onBroadcastStopFailed(int i) {
            MediaDeviceLogger mediaDeviceLogger = MediaDeviceManager.this.logger;
            mediaDeviceLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda0 = new MediaDeviceLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = mediaDeviceLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = "onBroadcastStopFailed";
            logMessageImpl.int1 = i;
            logBuffer.commit(obtain);
        }

        public final void onBroadcastStopped(int i, int i2) {
            MediaDeviceManager.this.logger.logBroadcastEvent(i, i2, "onBroadcastStopped");
            updateCurrent();
        }

        public final void onBroadcastUpdateFailed(int i, int i2) {
            MediaDeviceManager.this.logger.logBroadcastEvent(i, i2, "onBroadcastUpdateFailed");
        }

        public final void onBroadcastUpdated(int i, int i2) {
            MediaDeviceManager.this.logger.logBroadcastEvent(i, i2, "onBroadcastUpdated");
            updateCurrent();
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public final void onDeviceListUpdate(List list) {
            MediaDeviceManager.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$Entry$onDeviceListUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("MediaDeviceManager", "onDeviceListUpdate()");
                    MediaDeviceManager.Entry entry = MediaDeviceManager.Entry.this;
                    int i = MediaDeviceManager.Entry.$r8$clinit;
                    entry.updateCurrent();
                }
            });
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public final void onSelectedDeviceStateChanged(MediaDevice mediaDevice) {
            MediaDeviceManager.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$Entry$onSelectedDeviceStateChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("MediaDeviceManager", "onSelectedDeviceStateChanged()");
                    MediaDeviceManager.Entry entry = MediaDeviceManager.Entry.this;
                    int i = MediaDeviceManager.Entry.$r8$clinit;
                    entry.updateCurrent();
                }
            });
        }

        public final void stop() {
            final MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
            mediaDeviceManager.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$Entry$stop$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (MediaDeviceManager.Entry.this.started) {
                        Log.d("MediaDeviceManager", "stopScan()");
                        MediaDeviceManager.Entry entry = MediaDeviceManager.Entry.this;
                        entry.started = false;
                        MediaController mediaController = entry.controller;
                        if (mediaController != null) {
                            mediaController.unregisterCallback(entry);
                        }
                        MediaDeviceManager.Entry entry2 = MediaDeviceManager.Entry.this;
                        entry2.localMediaManager.unregisterCallback(entry2);
                        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = MediaDeviceManager.Entry.this.muteAwaitConnectionManager;
                        mediaMuteAwaitConnectionManager.audioManager.unregisterMuteAwaitConnectionCallback(mediaMuteAwaitConnectionManager.muteAwaitConnectionChangeListener);
                        ((ConfigurationControllerImpl) mediaDeviceManager.configurationController).removeCallback(MediaDeviceManager.Entry.this.configListener);
                    }
                }
            });
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x008f, code lost:
        
            if (r8 != null) goto L34;
         */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0075  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x008e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateCurrent() {
            /*
                Method dump skipped, instructions count: 473
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager.Entry.updateCurrent():void");
        }

        public final void onPlaybackStarted(int i, int i2) {
        }

        public final void onPlaybackStopped(int i, int i2) {
        }
    }
}
