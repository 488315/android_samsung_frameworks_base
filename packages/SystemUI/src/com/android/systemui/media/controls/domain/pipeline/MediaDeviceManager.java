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
import com.android.systemui.R;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.shared.MediaControlDrawables;
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
            String volumeControlId = null;
            RoutingSessionInfo routingSessionForMediaController = mediaController != null ? ((MediaRouter2Manager) MediaDeviceManager.this.mr2manager.get()).getRoutingSessionForMediaController(mediaController) : null;
            List selectedRoutes = routingSessionForMediaController != null ? ((MediaRouter2Manager) MediaDeviceManager.this.mr2manager.get()).getSelectedRoutes(routingSessionForMediaController) : null;
            MediaDeviceData mediaDeviceData = entry2.current;
            printWriter.println("    current device is " + ((Object) (mediaDeviceData != null ? mediaDeviceData.name : null)));
            MediaController mediaController2 = entry2.controller;
            Integer numValueOf = (mediaController2 == null || (playbackInfo2 = mediaController2.getPlaybackInfo()) == null) ? null : Integer.valueOf(playbackInfo2.getPlaybackType());
            printWriter.println("    PlaybackType=" + numValueOf + " (1 for local, 2 for remote) cached=" + entry2.playbackType);
            MediaController mediaController3 = entry2.controller;
            if (mediaController3 != null && (playbackInfo = mediaController3.getPlaybackInfo()) != null) {
                volumeControlId = playbackInfo.getVolumeControlId();
            }
            printWriter.println("    volumeControlId=" + volumeControlId + " cached= " + entry2.playbackVolumeControlId);
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
        MediaController mediaControllerCreate = token != null ? this.controllerFactory.create(token) : null;
        MediaSession.Token sessionToken = mediaControllerCreate != null ? mediaControllerCreate.getSessionToken() : null;
        LocalMediaManagerFactory localMediaManagerFactory = this.localMediaManagerFactory;
        Context context = localMediaManagerFactory.context;
        String str3 = mediaData.packageName;
        LocalBluetoothManager localBluetoothManager = localMediaManagerFactory.localBluetoothManager;
        LocalMediaManager localMediaManager = new LocalMediaManager(localMediaManagerFactory.context, localBluetoothManager, InfoMediaManager.createInstance(context, str3, null, localBluetoothManager, sessionToken), str3);
        MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory = this.muteAwaitConnectionManagerFactory;
        Entry entry3 = new Entry(str, str2, mediaControllerCreate, localMediaManager, new MediaMuteAwaitConnectionManager(mediaMuteAwaitConnectionManagerFactory.mainExecutor, localMediaManager, mediaMuteAwaitConnectionManagerFactory.context, mediaMuteAwaitConnectionManagerFactory.deviceIconUtil, mediaMuteAwaitConnectionManagerFactory.logger));
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

    public final class Entry extends MediaController.Callback implements LocalMediaManager.DeviceCallback, BluetoothLeBroadcast.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public AboutToConnectDevice aboutToConnectDeviceOverride;
        public final MediaDeviceManager$Entry$configListener$1 configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$Entry$configListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLocaleListChanged() {
                int i = MediaDeviceManager.Entry.$r8$clinit;
                this.this$0.updateCurrent();
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
            String string = bluetoothLeBroadcastMetadata.toString();
            mediaDeviceLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda0 = new MediaDeviceLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = mediaDeviceLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = i;
            logMessageImpl.str1 = string;
            logBuffer.commit(logMessageObtain);
            updateCurrent();
        }

        public final void onBroadcastStartFailed(int i) {
            MediaDeviceLogger mediaDeviceLogger = MediaDeviceManager.this.logger;
            mediaDeviceLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda0 = new MediaDeviceLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = mediaDeviceLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = "onBroadcastStartFailed";
            logMessageImpl.int1 = i;
            logBuffer.commit(logMessageObtain);
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
            LogMessage logMessageObtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = "onBroadcastStopFailed";
            logMessageImpl.int1 = i;
            logBuffer.commit(logMessageObtain);
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
                    MediaDeviceManager.Entry entry = this.this$0;
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
                    MediaDeviceManager.Entry entry = this.this$0;
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
                    if (this.this$0.started) {
                        Log.d("MediaDeviceManager", "stopScan()");
                        MediaDeviceManager.Entry entry = this.this$0;
                        entry.started = false;
                        MediaController mediaController = entry.controller;
                        if (mediaController != null) {
                            mediaController.unregisterCallback(entry);
                        }
                        MediaDeviceManager.Entry entry2 = this.this$0;
                        entry2.localMediaManager.unregisterCallback(entry2);
                        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = this.this$0.muteAwaitConnectionManager;
                        mediaMuteAwaitConnectionManager.audioManager.unregisterMuteAwaitConnectionCallback(mediaMuteAwaitConnectionManager.muteAwaitConnectionChangeListener);
                        ((ConfigurationControllerImpl) mediaDeviceManager.configurationController).removeCallback(this.this$0.configListener);
                    }
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x0075  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x008e  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0091  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00ce  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x00e2  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00e7  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00f8  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateCurrent() {
            MediaDeviceData mediaDeviceData;
            final MediaDeviceData mediaDeviceData2;
            CharSequence charSequence;
            CharSequence charSequence2;
            CharSequence charSequence3;
            MediaDeviceData mediaDeviceData3;
            MediaController.PlaybackInfo playbackInfo;
            CharSequence charSequence4;
            Drawable drawable;
            Drawable drawable2;
            MediaDevice currentConnectedDevice = this.localMediaManager.getCurrentConnectedDevice();
            MediaDeviceData mediaDeviceData4 = currentConnectedDevice != null ? new MediaDeviceData(true, currentConnectedDevice.getIconWithoutBackground(), currentConnectedDevice.getName(), null, currentConnectedDevice.getId(), false, null, 72, null) : null;
            MediaController mediaController = this.controller;
            if (mediaController == null || (playbackInfo = mediaController.getPlaybackInfo()) == null || playbackInfo.getPlaybackType() != 2) {
                AboutToConnectDevice aboutToConnectDevice = this.aboutToConnectDeviceOverride;
                if (aboutToConnectDevice == null) {
                    mediaDeviceData = null;
                } else {
                    MediaDevice mediaDevice = aboutToConnectDevice.fullMediaDevice;
                    mediaDeviceData = mediaDevice != null ? new MediaDeviceData(true, mediaDevice.getIconWithoutBackground(), mediaDevice.getName(), null, mediaDevice.getId(), false, null, 72, null) : aboutToConnectDevice.backupMediaDeviceData;
                }
                mediaDeviceData2 = mediaDeviceData == null ? mediaDeviceData4 : mediaDeviceData;
                MediaDeviceLogger mediaDeviceLogger = MediaDeviceManager.this.logger;
                mediaDeviceLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda0 = new MediaDeviceLogger$$ExternalSyntheticLambda0(5);
                LogBuffer logBuffer = mediaDeviceLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("MediaDeviceLog", logLevel, mediaDeviceLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = (mediaDeviceData == null || (charSequence2 = mediaDeviceData.name) == null) ? null : charSequence2.toString();
                logMessageImpl.str2 = (mediaDeviceData4 == null || (charSequence = mediaDeviceData4.name) == null) ? null : charSequence.toString();
                logBuffer.commit(logMessageObtain);
            } else {
                RoutingSessionInfo routingSessionForMediaController = ((MediaRouter2Manager) MediaDeviceManager.this.mr2manager.get()).getRoutingSessionForMediaController(this.controller);
                if (routingSessionForMediaController != null) {
                    MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
                    if (routingSessionForMediaController.getSelectedRoutes().size() > 1) {
                        MediaControlDrawables mediaControlDrawables = MediaControlDrawables.INSTANCE;
                        Context context = mediaDeviceManager.context;
                        mediaControlDrawables.getClass();
                        drawable2 = MediaControlDrawables.groupDevice;
                        if (drawable2 == null) {
                            drawable2 = context.getDrawable(R.drawable.ic_media_group_device);
                            MediaControlDrawables.groupDevice = drawable2;
                        }
                    } else if (mediaDeviceData4 != null) {
                        drawable2 = mediaDeviceData4.icon;
                    } else {
                        drawable = null;
                        if (mediaDeviceData4 == null) {
                            CharSequence name = routingSessionForMediaController.getName();
                            if (name == null) {
                                name = mediaDeviceData4.name;
                            }
                            mediaDeviceData2 = new MediaDeviceData(mediaDeviceData4.enabled, drawable, name, mediaDeviceData4.intent, mediaDeviceData4.id, mediaDeviceData4.showBroadcastButton, mediaDeviceData4.customMediaDeviceData);
                        } else {
                            mediaDeviceData2 = null;
                        }
                        if (mediaDeviceData2 == null) {
                            MediaControlDrawables mediaControlDrawables2 = MediaControlDrawables.INSTANCE;
                            Context context2 = MediaDeviceManager.this.context;
                            mediaControlDrawables2.getClass();
                            Drawable drawable3 = MediaControlDrawables.homeDevices;
                            if (drawable3 == null) {
                                drawable3 = context2.getDrawable(R.drawable.ic_media_home_devices);
                                MediaControlDrawables.homeDevices = drawable3;
                            }
                            mediaDeviceData2 = new MediaDeviceData(false, drawable3, MediaDeviceManager.this.context.getString(R.string.media_seamless_other_device), null, null, false, null, 88, null);
                        }
                        MediaDeviceLogger mediaDeviceLogger2 = MediaDeviceManager.this.logger;
                        CharSequence name2 = routingSessionForMediaController == null ? routingSessionForMediaController.getName() : null;
                        mediaDeviceLogger2.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda02 = new MediaDeviceLogger$$ExternalSyntheticLambda0(4);
                        LogBuffer logBuffer2 = mediaDeviceLogger2.buffer;
                        LogMessage logMessageObtain2 = logBuffer2.obtain("MediaDeviceLog", logLevel2, mediaDeviceLogger$$ExternalSyntheticLambda02, null);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                        logMessageImpl2.str1 = name2 == null ? name2.toString() : null;
                        logMessageImpl2.str2 = (mediaDeviceData4 != null || (charSequence4 = mediaDeviceData4.name) == null) ? null : charSequence4.toString();
                        logBuffer2.commit(logMessageObtain2);
                    }
                    drawable = drawable2;
                    if (mediaDeviceData4 == null) {
                    }
                    if (mediaDeviceData2 == null) {
                    }
                    MediaDeviceLogger mediaDeviceLogger22 = MediaDeviceManager.this.logger;
                    if (routingSessionForMediaController == null) {
                    }
                    mediaDeviceLogger22.getClass();
                    LogLevel logLevel22 = LogLevel.DEBUG;
                    MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda022 = new MediaDeviceLogger$$ExternalSyntheticLambda0(4);
                    LogBuffer logBuffer22 = mediaDeviceLogger22.buffer;
                    LogMessage logMessageObtain22 = logBuffer22.obtain("MediaDeviceLog", logLevel22, mediaDeviceLogger$$ExternalSyntheticLambda022, null);
                    LogMessageImpl logMessageImpl22 = (LogMessageImpl) logMessageObtain22;
                    logMessageImpl22.str1 = name2 == null ? name2.toString() : null;
                    if (mediaDeviceData4 != null) {
                        logMessageImpl22.str2 = (mediaDeviceData4 != null || (charSequence4 = mediaDeviceData4.name) == null) ? null : charSequence4.toString();
                        logBuffer22.commit(logMessageObtain22);
                    }
                }
            }
            if (mediaDeviceData2 == null) {
                mediaDeviceData2 = MediaDeviceManager.EMPTY_AND_DISABLED_MEDIA_DEVICE_DATA;
            }
            boolean z = mediaDeviceData2 != null && (mediaDeviceData3 = this.current) != null && mediaDeviceData2.enabled == mediaDeviceData3.enabled && Intrinsics.areEqual(mediaDeviceData2.name, mediaDeviceData3.name) && Intrinsics.areEqual(mediaDeviceData2.intent, mediaDeviceData3.intent) && Intrinsics.areEqual(mediaDeviceData2.id, mediaDeviceData3.id) && mediaDeviceData2.showBroadcastButton == mediaDeviceData3.showBroadcastButton;
            if (!this.started || !z) {
                this.current = mediaDeviceData2;
                final MediaDeviceManager mediaDeviceManager2 = MediaDeviceManager.this;
                mediaDeviceManager2.fgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager$Entry$current$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaDeviceManager mediaDeviceManager3 = mediaDeviceManager2;
                        MediaDeviceManager.Entry entry = this;
                        String str = entry.key;
                        String str2 = entry.oldKey;
                        MediaDeviceData mediaDeviceData5 = mediaDeviceData2;
                        MediaDeviceData mediaDeviceData6 = MediaDeviceManager.EMPTY_AND_DISABLED_MEDIA_DEVICE_DATA;
                        mediaDeviceManager3.processDevice(str, str2, mediaDeviceData5);
                    }
                });
            }
            MediaDeviceLogger mediaDeviceLogger3 = MediaDeviceManager.this.logger;
            MediaDeviceData mediaDeviceData5 = this.current;
            String string = (mediaDeviceData5 == null || (charSequence3 = mediaDeviceData5.name) == null) ? null : charSequence3.toString();
            mediaDeviceLogger3.getClass();
            LogLevel logLevel3 = LogLevel.DEBUG;
            MediaDeviceLogger$$ExternalSyntheticLambda0 mediaDeviceLogger$$ExternalSyntheticLambda03 = new MediaDeviceLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer3 = mediaDeviceLogger3.buffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("MediaDeviceLog", logLevel3, mediaDeviceLogger$$ExternalSyntheticLambda03, null);
            ((LogMessageImpl) logMessageObtain3).str1 = string;
            logBuffer3.commit(logMessageObtain3);
        }

        public final void onPlaybackStarted(int i, int i2) {
        }

        public final void onPlaybackStopped(int i, int i2) {
        }
    }
}
