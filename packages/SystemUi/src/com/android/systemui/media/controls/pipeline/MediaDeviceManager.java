package com.android.systemui.media.controls.pipeline;

import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaDeviceManager implements MediaDataManager.Listener, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor bgExecutor;
    public final ConfigurationController configurationController;
    public final Context context;
    public final MediaControllerFactory controllerFactory;
    public final Map entries;
    public final Executor fgExecutor;
    public final Set listeners;
    public final LocalBluetoothManager localBluetoothManager;
    public final LocalMediaManagerFactory localMediaManagerFactory;
    public final MediaRouter2Manager mr2manager;
    public final MediaMuteAwaitConnectionManagerFactory muteAwaitConnectionManagerFactory;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaDeviceManager(Context context, MediaControllerFactory mediaControllerFactory, LocalMediaManagerFactory localMediaManagerFactory, MediaRouter2Manager mediaRouter2Manager, MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory, ConfigurationController configurationController, LocalBluetoothManager localBluetoothManager, Executor executor, Executor executor2, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.controllerFactory = mediaControllerFactory;
        this.localMediaManagerFactory = localMediaManagerFactory;
        this.mr2manager = mediaRouter2Manager;
        this.muteAwaitConnectionManagerFactory = mediaMuteAwaitConnectionManagerFactory;
        this.configurationController = configurationController;
        this.localBluetoothManager = localBluetoothManager;
        this.fgExecutor = executor;
        this.bgExecutor = executor2;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$intentReceiver$1
            public final LocalMediaManager localMediaManager;

            {
                LocalMediaManagerFactory localMediaManagerFactory2 = MediaDeviceManager.this.localMediaManagerFactory;
                localMediaManagerFactory2.getClass();
                Context context2 = localMediaManagerFactory2.context;
                LocalBluetoothManager localBluetoothManager2 = localMediaManagerFactory2.localBluetoothManager;
                this.localMediaManager = new LocalMediaManager(context2, localBluetoothManager2, new InfoMediaManager(context2, "", null, localBluetoothManager2), "");
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action != null) {
                    int hashCode = action.hashCode();
                    if (hashCode != -1315844839) {
                        if (hashCode == -414878142) {
                            if (action.equals("com.samsung.android.mdx.quickboard.action.ACTION_MEDIA_ROUTER_SCAN_STOP")) {
                                Log.d("MediaDeviceManager", "ACTION_MEDIA_ROUTER_SCAN_STOP");
                                this.localMediaManager.stopScan();
                                return;
                            }
                            return;
                        }
                        if (hashCode == 23666178 && action.equals("com.samsung.android.mdx.quickboard.action.ACTION_MEDIA_ROUTER_SCAN_START")) {
                            Log.d("MediaDeviceManager", "ACTION_MEDIA_ROUTER_SCAN_START");
                            this.localMediaManager.startScan();
                            return;
                        }
                        return;
                    }
                    if (action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION") && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                        Log.d("MediaDeviceManager", "SEM_STREAM_DEVICES_CHANGED_ACTION");
                        MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
                        synchronized (mediaDeviceManager.entries) {
                            ((LinkedHashMap) mediaDeviceManager.entries).forEach(new BiConsumer() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$intentReceiver$1$onReceive$1$1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    MediaDeviceManager.Entry entry = (MediaDeviceManager.Entry) obj2;
                                    entry.stop();
                                    MediaDeviceManager mediaDeviceManager2 = MediaDeviceManager.this;
                                    mediaDeviceManager2.bgExecutor.execute(new MediaDeviceManager$Entry$start$1(entry, mediaDeviceManager2));
                                }
                            });
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                }
            }
        };
        this.listeners = new LinkedHashSet();
        this.entries = new LinkedHashMap();
        DumpManager.registerDumpable$default(dumpManager, MediaDeviceManager.class.getName(), this);
        IntentFilter intentFilter = new IntentFilter("android.media.STREAM_DEVICES_CHANGED_ACTION");
        intentFilter.addAction("com.samsung.android.mdx.quickboard.action.ACTION_MEDIA_ROUTER_SCAN_START");
        intentFilter.addAction("com.samsung.android.mdx.quickboard.action.ACTION_MEDIA_ROUTER_SCAN_STOP");
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, broadcastReceiver, intentFilter, null, UserHandle.ALL, 0, null, 48);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        MediaController.PlaybackInfo playbackInfo;
        MediaController.PlaybackInfo playbackInfo2;
        printWriter.println("MediaDeviceManager state:");
        for (Map.Entry entry : ((LinkedHashMap) this.entries).entrySet()) {
            String str = (String) entry.getKey();
            Entry entry2 = (Entry) entry.getValue();
            FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("  key=", str, printWriter);
            MediaController mediaController = entry2.controller;
            String str2 = null;
            RoutingSessionInfo routingSessionForMediaController = mediaController != null ? MediaDeviceManager.this.mr2manager.getRoutingSessionForMediaController(mediaController) : null;
            List selectedRoutes = routingSessionForMediaController != null ? MediaDeviceManager.this.mr2manager.getSelectedRoutes(routingSessionForMediaController) : null;
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
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        MediaController mediaController;
        LocalMediaManager localMediaManager;
        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager;
        Entry entry;
        Map map = this.entries;
        if (str2 != null && !Intrinsics.areEqual(str2, str) && (entry = (Entry) map.remove(str2)) != null) {
            entry.stop();
        }
        Entry entry2 = (Entry) ((LinkedHashMap) map).get(str);
        MediaSession.Token token = mediaData.token;
        if (entry2 != null) {
            MediaController mediaController2 = entry2.controller;
            if (Intrinsics.areEqual(mediaController2 != null ? mediaController2.getSessionToken() : null, token)) {
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
        if (token != null) {
            MediaControllerFactory mediaControllerFactory = this.controllerFactory;
            mediaControllerFactory.getClass();
            mediaController = new MediaController(mediaControllerFactory.mContext, token);
        } else {
            mediaController = null;
        }
        LocalMediaManagerFactory localMediaManagerFactory = this.localMediaManagerFactory;
        localMediaManagerFactory.getClass();
        Context context = localMediaManagerFactory.context;
        String str3 = mediaData.packageName;
        LocalBluetoothManager localBluetoothManager = localMediaManagerFactory.localBluetoothManager;
        LocalMediaManager localMediaManager2 = new LocalMediaManager(context, localBluetoothManager, new InfoMediaManager(context, str3, null, localBluetoothManager), str3);
        MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory = this.muteAwaitConnectionManagerFactory;
        MediaFlags mediaFlags = mediaMuteAwaitConnectionManagerFactory.mediaFlags;
        mediaFlags.getClass();
        Flags.INSTANCE.getClass();
        if (((FeatureFlagsRelease) mediaFlags.featureFlags).isEnabled(Flags.MEDIA_MUTE_AWAIT)) {
            localMediaManager = localMediaManager2;
            mediaMuteAwaitConnectionManager = new MediaMuteAwaitConnectionManager(mediaMuteAwaitConnectionManagerFactory.mainExecutor, localMediaManager2, mediaMuteAwaitConnectionManagerFactory.context, mediaMuteAwaitConnectionManagerFactory.deviceIconUtil, mediaMuteAwaitConnectionManagerFactory.logger);
        } else {
            mediaMuteAwaitConnectionManager = null;
            localMediaManager = localMediaManager2;
        }
        Entry entry3 = new Entry(str, str2, mediaController, localMediaManager, mediaMuteAwaitConnectionManager);
        map.put(str, entry3);
        MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
        mediaDeviceManager.bgExecutor.execute(new MediaDeviceManager$Entry$start$1(entry3, mediaDeviceManager));
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onMediaDataRemoved(String str) {
        Entry entry = (Entry) this.entries.remove(str);
        if (entry != null) {
            entry.stop();
        }
        if (entry != null) {
            Iterator it = this.listeners.iterator();
            while (it.hasNext()) {
                ((MediaDataCombineLatest) it.next()).remove(str);
            }
        }
    }

    public final void processDevice(String str, String str2, MediaDeviceData mediaDeviceData) {
        for (MediaDataCombineLatest mediaDataCombineLatest : this.listeners) {
            Map map = mediaDataCombineLatest.entries;
            if (str2 == null || Intrinsics.areEqual(str2, str) || !map.containsKey(str2)) {
                Pair pair = (Pair) ((LinkedHashMap) map).get(str);
                map.put(str, new Pair(pair != null ? (MediaData) pair.getFirst() : null, mediaDeviceData));
                mediaDataCombineLatest.update(str, str);
            } else {
                Pair pair2 = (Pair) map.remove(str2);
                map.put(str, new Pair(pair2 != null ? (MediaData) pair2.getFirst() : null, mediaDeviceData));
                mediaDataCombineLatest.update(str, str2);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Entry extends MediaController.Callback implements LocalMediaManager.DeviceCallback, BluetoothLeBroadcast.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public AboutToConnectDevice aboutToConnectDeviceOverride;
        public String broadcastDescription;
        public final MediaDeviceManager$Entry$configListener$1 configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$configListener$1
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

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$configListener$1] */
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
            int playbackType = playbackInfo != null ? playbackInfo.getPlaybackType() : 0;
            String volumeControlId = playbackInfo != null ? playbackInfo.getVolumeControlId() : null;
            if (playbackType == this.playbackType && Intrinsics.areEqual(volumeControlId, this.playbackVolumeControlId)) {
                return;
            }
            this.playbackType = playbackType;
            this.playbackVolumeControlId = volumeControlId;
            updateCurrent();
        }

        public final void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d("MediaDeviceManager", "onBroadcastMetadataChanged(), broadcastId = " + i + " , metadata = " + bluetoothLeBroadcastMetadata);
            updateCurrent();
        }

        public final void onBroadcastStartFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("onBroadcastStartFailed(), reason = ", i, "MediaDeviceManager");
        }

        public final void onBroadcastStarted(int i, int i2) {
            Log.d("MediaDeviceManager", "onBroadcastStarted(), reason = " + i + " , broadcastId = " + i2);
            updateCurrent();
        }

        public final void onBroadcastStopFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("onBroadcastStopFailed(), reason = ", i, "MediaDeviceManager");
        }

        public final void onBroadcastStopped(int i, int i2) {
            Log.d("MediaDeviceManager", "onBroadcastStopped(), reason = " + i + " , broadcastId = " + i2);
            updateCurrent();
        }

        public final void onBroadcastUpdateFailed(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onBroadcastUpdateFailed(), reason = ", i, " , broadcastId = ", i2, "MediaDeviceManager");
        }

        public final void onBroadcastUpdated(int i, int i2) {
            Log.d("MediaDeviceManager", "onBroadcastUpdated(), reason = " + i + " , broadcastId = " + i2);
            updateCurrent();
        }

        @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
        public final void onDeviceListUpdate(List list) {
            MediaDeviceManager.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$onDeviceListUpdate$1
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
            MediaDeviceManager.this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$onSelectedDeviceStateChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("MediaDeviceManager", "onSelectedDeviceStateChanged()");
                    MediaDeviceManager.Entry entry = MediaDeviceManager.Entry.this;
                    int i = MediaDeviceManager.Entry.$r8$clinit;
                    entry.updateCurrent();
                }
            });
        }

        public final void setCurrent(final MediaDeviceData mediaDeviceData) {
            boolean z = false;
            if (mediaDeviceData != null) {
                MediaDeviceData mediaDeviceData2 = this.current;
                if (mediaDeviceData2 != null && mediaDeviceData.enabled == mediaDeviceData2.enabled && Intrinsics.areEqual(mediaDeviceData.name, mediaDeviceData2.name) && Intrinsics.areEqual(mediaDeviceData.intent, mediaDeviceData2.intent) && Intrinsics.areEqual(mediaDeviceData.f306id, mediaDeviceData2.f306id) && mediaDeviceData.showBroadcastButton == mediaDeviceData2.showBroadcastButton) {
                    z = true;
                }
            }
            if (this.started && z) {
                return;
            }
            this.current = mediaDeviceData;
            final MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
            mediaDeviceManager.fgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$current$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaDeviceManager mediaDeviceManager2 = MediaDeviceManager.this;
                    MediaDeviceManager.Entry entry = this;
                    String str = entry.key;
                    String str2 = entry.oldKey;
                    MediaDeviceData mediaDeviceData3 = mediaDeviceData;
                    int i = MediaDeviceManager.$r8$clinit;
                    mediaDeviceManager2.processDevice(str, str2, mediaDeviceData3);
                }
            });
        }

        public final void stop() {
            final MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
            mediaDeviceManager.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.pipeline.MediaDeviceManager$Entry$stop$1
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
                        MediaDeviceManager.Entry.this.localMediaManager.stopScan();
                        MediaDeviceManager.Entry entry2 = MediaDeviceManager.Entry.this;
                        ((CopyOnWriteArrayList) entry2.localMediaManager.mCallbacks).remove(entry2);
                        MediaMuteAwaitConnectionManager mediaMuteAwaitConnectionManager = MediaDeviceManager.Entry.this.muteAwaitConnectionManager;
                        if (mediaMuteAwaitConnectionManager != null) {
                            mediaMuteAwaitConnectionManager.audioManager.unregisterMuteAwaitConnectionCallback(mediaMuteAwaitConnectionManager.muteAwaitConnectionChangeListener);
                        }
                        ((ConfigurationControllerImpl) mediaDeviceManager.configurationController).removeCallback(MediaDeviceManager.Entry.this.configListener);
                    }
                }
            });
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0092  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x00e6  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00f4  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x0130  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x013a  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x014d  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x0140  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0136  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x00f9  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x00ef  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateCurrent() {
            boolean z;
            MediaDevice currentConnectedDevice;
            String name;
            String str;
            CharSequence name2;
            MediaDeviceData mediaDeviceData;
            ApplicationInfo applicationInfo;
            String str2;
            LocalBluetoothManager localBluetoothManager = MediaDeviceManager.this.localBluetoothManager;
            if (localBluetoothManager != null) {
                LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
                if (localBluetoothProfileManager != null) {
                    LocalBluetoothLeBroadcast localBluetoothLeBroadcast = localBluetoothProfileManager.mLeAudioBroadcast;
                    if (localBluetoothLeBroadcast != null && localBluetoothLeBroadcast.isEnabled()) {
                        String str3 = localBluetoothLeBroadcast.mAppSourceName;
                        Context context = MediaDeviceManager.this.context;
                        String str4 = this.localMediaManager.mPackageName;
                        CharSequence string = context.getString(R.string.bt_le_audio_broadcast_dialog_unknown_name);
                        if (TextUtils.isEmpty(str4)) {
                            str2 = null;
                        } else {
                            PackageManager packageManager = context.getPackageManager();
                            try {
                                applicationInfo = packageManager.getApplicationInfo(str4, 0);
                            } catch (PackageManager.NameNotFoundException unused) {
                                applicationInfo = null;
                            }
                            if (applicationInfo != null) {
                                string = packageManager.getApplicationLabel(applicationInfo);
                            }
                            str2 = (String) string;
                        }
                        if (TextUtils.equals(str2, str3)) {
                            this.broadcastDescription = MediaDeviceManager.this.context.getString(R.string.broadcasting_description_is_broadcasting);
                        } else {
                            this.broadcastDescription = str3;
                        }
                        z = true;
                        if (!z) {
                            setCurrent(new MediaDeviceData(true, MediaDeviceManager.this.context.getDrawable(R.drawable.settings_input_antenna), this.broadcastDescription, null, null, true, null, 80, null));
                            return;
                        }
                        AboutToConnectDevice aboutToConnectDevice = this.aboutToConnectDeviceOverride;
                        if (aboutToConnectDevice != null && aboutToConnectDevice.fullMediaDevice == null && (mediaDeviceData = aboutToConnectDevice.backupMediaDeviceData) != null) {
                            setCurrent(mediaDeviceData);
                            return;
                        }
                        if (aboutToConnectDevice == null || (currentConnectedDevice = aboutToConnectDevice.fullMediaDevice) == null) {
                            currentConnectedDevice = this.localMediaManager.getCurrentConnectedDevice();
                        }
                        MediaController mediaController = this.controller;
                        RoutingSessionInfo routingSessionForMediaController = mediaController != null ? MediaDeviceManager.this.mr2manager.getRoutingSessionForMediaController(mediaController) : null;
                        boolean z2 = currentConnectedDevice != null && (this.controller == null || routingSessionForMediaController != null);
                        if (this.controller == null || routingSessionForMediaController != null) {
                            if (routingSessionForMediaController == null || (name2 = routingSessionForMediaController.getName()) == null || (name = name2.toString()) == null) {
                                if (currentConnectedDevice != null) {
                                    name = currentConnectedDevice.getName();
                                }
                            }
                            str = name;
                            Log.d("MediaDeviceManager", "updateCurrent() = " + (currentConnectedDevice == null ? Integer.valueOf(currentConnectedDevice.getDeviceType()) : null) + ", name=" + str + ", controller=" + this.controller + ", route=" + routingSessionForMediaController + ", device=" + (currentConnectedDevice == null ? currentConnectedDevice.getName() : null));
                            MediaDeviceData mediaDeviceData2 = new MediaDeviceData(z2, currentConnectedDevice == null ? currentConnectedDevice.getIconWithoutBackground() : null, str, null, currentConnectedDevice == null ? currentConnectedDevice.getId() : null, false, null, 72, null);
                            mediaDeviceData2.customMediaDeviceData.deviceType = currentConnectedDevice != null ? Integer.valueOf(currentConnectedDevice.getDeviceType()) : null;
                            setCurrent(mediaDeviceData2);
                            return;
                        }
                        str = null;
                        if (currentConnectedDevice == null) {
                        }
                        if (currentConnectedDevice == null) {
                        }
                        Log.d("MediaDeviceManager", "updateCurrent() = " + (currentConnectedDevice == null ? Integer.valueOf(currentConnectedDevice.getDeviceType()) : null) + ", name=" + str + ", controller=" + this.controller + ", route=" + routingSessionForMediaController + ", device=" + (currentConnectedDevice == null ? currentConnectedDevice.getName() : null));
                        MediaDeviceData mediaDeviceData22 = new MediaDeviceData(z2, currentConnectedDevice == null ? currentConnectedDevice.getIconWithoutBackground() : null, str, null, currentConnectedDevice == null ? currentConnectedDevice.getId() : null, false, null, 72, null);
                        mediaDeviceData22.customMediaDeviceData.deviceType = currentConnectedDevice != null ? Integer.valueOf(currentConnectedDevice.getDeviceType()) : null;
                        setCurrent(mediaDeviceData22);
                        return;
                    }
                    Log.d("MediaDeviceManager", "Can not get LocalBluetoothLeBroadcast");
                } else {
                    Log.d("MediaDeviceManager", "Can not get LocalBluetoothProfileManager");
                }
            } else {
                Log.d("MediaDeviceManager", "Can not get LocalBluetoothManager");
            }
            z = false;
            if (!z) {
            }
        }

        public final void onPlaybackStarted(int i, int i2) {
        }

        public final void onPlaybackStopped(int i, int i2) {
        }
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
    }

    @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
    }
}
