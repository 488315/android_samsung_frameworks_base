package com.android.server.media;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.RouteDiscoveryPreference;
import android.media.RoutingSessionInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.server.media.BluetoothRouteController;
import com.android.server.media.DeviceRouteController;
import com.android.server.media.MediaRoute2Provider;
import com.android.server.media.SystemMediaRoute2Provider;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public class SystemMediaRoute2Provider extends MediaRoute2Provider {
    public final AudioManager mAudioManager;
    public final AudioManagerBroadcastReceiver mAudioReceiver;
    public final BluetoothRouteController mBluetoothRouteController;
    public final Context mContext;
    public MediaRoute2Info mDefaultRoute;
    public RoutingSessionInfo mDefaultSessionInfo;
    public final DeviceRouteController mDeviceRouteController;
    public final Handler mHandler;
    public MediaRoute2Info mMusicShareDeviceRoute;
    public final AudioManager.OnDevicesForAttributesChangedListener mOnDevicesForAttributesChangedListener;
    public volatile SessionCreationRequest mPendingSessionCreationRequest;
    public DeviceRouteController mRemoteSubmixRouteController;
    public final Object mRequestLock;
    public MediaRoute2Info mScreenMirroringRoute;
    public String mSelectedRouteId;
    public final UserHandle mUser;
    public static final boolean DEBUG = Log.isLoggable("MR2SystemProvider", 3);
    public static final ComponentName COMPONENT_NAME = new ComponentName(SystemMediaRoute2Provider.class.getPackage().getName(), SystemMediaRoute2Provider.class.getName());

    @Override // com.android.server.media.MediaRoute2Provider
    public void deselectRoute(long j, String str, String str2) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void prepareReleaseSession(String str) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void releaseSession(long j, String str) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void selectRoute(long j, String str, String str2) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setSessionVolume(long j, String str, int i) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void updateDiscoveryPreference(Set set, RouteDiscoveryPreference routeDiscoveryPreference) {
    }

    /* renamed from: com.android.server.media.SystemMediaRoute2Provider$1 */
    public class C18991 implements AudioManager.OnDevicesForAttributesChangedListener {
        public C18991() {
        }

        public void onDevicesForAttributesChanged(AudioAttributes audioAttributes, final List list) {
            Log.i("MR2SystemProvider", "onDevicesForAttributesChanged AudioDeviceAttributes = " + audioAttributes.toString() + " devices = " + list.toString());
            if (audioAttributes.getUsage() != 1) {
                return;
            }
            SystemMediaRoute2Provider.this.mHandler.post(new Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemMediaRoute2Provider.C18991.this.lambda$onDevicesForAttributesChanged$0(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDevicesForAttributesChanged$0(List list) {
            SystemMediaRoute2Provider.this.updateSelectedAudioDevice(list);
            SystemMediaRoute2Provider.this.notifyProviderState();
            if (SystemMediaRoute2Provider.this.updateSessionInfosIfNeeded()) {
                SystemMediaRoute2Provider.this.notifySessionInfoUpdated();
            }
        }
    }

    public SystemMediaRoute2Provider(Context context, UserHandle userHandle) {
        super(COMPONENT_NAME);
        this.mAudioReceiver = new AudioManagerBroadcastReceiver();
        C18991 c18991 = new C18991();
        this.mOnDevicesForAttributesChangedListener = c18991;
        this.mRequestLock = new Object();
        this.mRemoteSubmixRouteController = null;
        this.mMusicShareDeviceRoute = null;
        this.mScreenMirroringRoute = null;
        this.mIsSystemRouteProvider = true;
        this.mContext = context;
        this.mUser = userHandle;
        this.mHandler = new Handler(Looper.getMainLooper());
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        this.mAudioManager = audioManager;
        this.mBluetoothRouteController = BluetoothRouteController.createInstance(context, new BluetoothRouteController.BluetoothRoutesUpdatedListener() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda3
            @Override // com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener
            public final void onBluetoothRoutesUpdated(List list) {
                SystemMediaRoute2Provider.this.lambda$new$0(list);
            }
        });
        this.mDeviceRouteController = DeviceRouteController.createInstance(context, new DeviceRouteController.OnDeviceRouteChangedListener() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda4
            @Override // com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener
            public final void onDeviceRouteChanged(MediaRoute2Info mediaRoute2Info) {
                SystemMediaRoute2Provider.this.lambda$new$2(mediaRoute2Info);
            }
        });
        AudioAttributes audioAttributes = AudioAttributesUtils.ATTRIBUTES_MEDIA;
        audioManager.addOnDevicesForAttributesChangedListener(audioAttributes, context.getMainExecutor(), c18991);
        updateSelectedAudioDevice(audioManager.getDevicesForAttributes(audioAttributes));
        updateProviderState();
        updateSessionInfosIfNeeded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(List list) {
        publishProviderState();
        if (updateSessionInfosIfNeeded()) {
            notifySessionInfoUpdated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(MediaRoute2Info mediaRoute2Info) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SystemMediaRoute2Provider.this.lambda$new$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        publishProviderState();
        if (updateSessionInfosIfNeeded()) {
            notifySessionInfoUpdated();
        }
    }

    public void start() {
        IntentFilter intentFilter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        intentFilter.addAction("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
        this.mContext.registerReceiverAsUser(this.mAudioReceiver, this.mUser, intentFilter, null, null);
        this.mHandler.post(new Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SystemMediaRoute2Provider.this.lambda$start$3();
            }
        });
        updateVolume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$3() {
        this.mBluetoothRouteController.start(this.mUser);
        notifyProviderState();
    }

    public void stop() {
        this.mContext.unregisterReceiver(this.mAudioReceiver);
        this.mHandler.post(new Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SystemMediaRoute2Provider.this.lambda$stop$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stop$4() {
        this.mBluetoothRouteController.stop();
        notifyProviderState();
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setCallback(MediaRoute2Provider.Callback callback) {
        super.setCallback(callback);
        notifyProviderState();
        notifySessionInfoUpdated();
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void requestCreateSession(long j, String str, String str2, Bundle bundle) {
        if (TextUtils.equals(str2, "DEFAULT_ROUTE")) {
            this.mCallback.onSessionCreated(this, j, this.mDefaultSessionInfo);
            return;
        }
        if (TextUtils.equals(str2, this.mSelectedRouteId)) {
            this.mCallback.onSessionCreated(this, j, (RoutingSessionInfo) this.mSessionInfos.get(0));
            return;
        }
        synchronized (this.mRequestLock) {
            if (this.mPendingSessionCreationRequest != null) {
                this.mCallback.onRequestFailed(this, this.mPendingSessionCreationRequest.mRequestId, 0);
            }
            this.mPendingSessionCreationRequest = new SessionCreationRequest(j, str2);
        }
        transferToRoute(j, "SYSTEM_SESSION", str2);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void transferToRoute(long j, String str, String str2) {
        if (TextUtils.equals(str2, "DEFAULT_ROUTE")) {
            return;
        }
        if (TextUtils.equals(str2, this.mDeviceRouteController.getDeviceRoute().getId())) {
            this.mBluetoothRouteController.transferTo(null);
        } else {
            this.mBluetoothRouteController.transferTo(str2);
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setRouteVolume(long j, String str, int i) {
        if (TextUtils.equals(str, this.mSelectedRouteId)) {
            this.mAudioManager.setStreamVolume(3, i, 0);
        }
    }

    public MediaRoute2Info getDefaultRoute() {
        return this.mDefaultRoute;
    }

    public RoutingSessionInfo getDefaultSessionInfo() {
        return this.mDefaultSessionInfo;
    }

    public RoutingSessionInfo generateDeviceRouteSelectedSessionInfo(String str) {
        synchronized (this.mLock) {
            if (this.mSessionInfos.isEmpty()) {
                return null;
            }
            MediaRoute2Info deviceRoute = this.mDeviceRouteController.getDeviceRoute();
            RoutingSessionInfo.Builder systemSession = new RoutingSessionInfo.Builder("SYSTEM_SESSION", str).setSystemSession(true);
            systemSession.addSelectedRoute(deviceRoute.getId());
            Iterator it = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
            while (it.hasNext()) {
                systemSession.addTransferableRoute(((MediaRoute2Info) it.next()).getId());
            }
            return systemSession.setProviderId(this.mUniqueId).build();
        }
    }

    public final void updateSelectedAudioDevice(List list) {
        if (list.isEmpty()) {
            Slog.w("MR2SystemProvider", "The list of preferred devices was empty.");
            return;
        }
        AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) list.get(0);
        if (AudioAttributesUtils.isDeviceOutputAttributes(audioDeviceAttributes)) {
            this.mDeviceRouteController.selectRoute(Integer.valueOf(AudioAttributesUtils.mapToMediaRouteType(audioDeviceAttributes)));
            this.mBluetoothRouteController.selectRoute(null);
        } else if (AudioAttributesUtils.isBluetoothOutputAttributes(audioDeviceAttributes)) {
            this.mDeviceRouteController.selectRoute(null);
            this.mBluetoothRouteController.selectRoute(audioDeviceAttributes.getAddress());
        } else {
            Slog.w("MR2SystemProvider", "Unknown audio attributes: " + audioDeviceAttributes);
        }
    }

    public final void updateProviderState() {
        MediaRoute2ProviderInfo.Builder builder = new MediaRoute2ProviderInfo.Builder();
        builder.addRoute(this.mDeviceRouteController.getDeviceRoute());
        MediaRoute2Info mediaRoute2Info = this.mMusicShareDeviceRoute;
        if (mediaRoute2Info != null) {
            builder.addRoute(mediaRoute2Info);
        }
        MediaRoute2Info mediaRoute2Info2 = this.mScreenMirroringRoute;
        if (mediaRoute2Info2 != null) {
            builder.addRoute(mediaRoute2Info2);
        }
        Iterator it = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
        while (it.hasNext()) {
            builder.addRoute((MediaRoute2Info) it.next());
        }
        MediaRoute2ProviderInfo build = builder.build();
        setProviderState(build);
        if (DEBUG) {
            Slog.d("MR2SystemProvider", "Updating system provider info : " + build);
        }
    }

    public boolean updateSessionInfosIfNeeded() {
        SessionCreationRequest sessionCreationRequest;
        synchronized (this.mLock) {
            RoutingSessionInfo routingSessionInfo = this.mSessionInfos.isEmpty() ? null : (RoutingSessionInfo) this.mSessionInfos.get(0);
            RoutingSessionInfo.Builder systemSession = new RoutingSessionInfo.Builder("SYSTEM_SESSION", "").setSystemSession(true);
            MediaRoute2Info deviceRoute = this.mDeviceRouteController.getDeviceRoute();
            MediaRoute2Info selectedRoute = this.mBluetoothRouteController.getSelectedRoute();
            if (selectedRoute != null) {
                systemSession.addTransferableRoute(deviceRoute.getId());
                deviceRoute = selectedRoute;
            }
            this.mSelectedRouteId = deviceRoute.getId();
            this.mDefaultRoute = new MediaRoute2Info.Builder("DEFAULT_ROUTE", deviceRoute).setSystemRoute(true).setProviderId(this.mUniqueId).build();
            systemSession.addSelectedRoute(this.mSelectedRouteId);
            Iterator it = this.mBluetoothRouteController.getTransferableRoutes().iterator();
            while (it.hasNext()) {
                systemSession.addTransferableRoute(((MediaRoute2Info) it.next()).getId());
            }
            RoutingSessionInfo build = systemSession.setProviderId(this.mUniqueId).build();
            if (this.mPendingSessionCreationRequest != null) {
                synchronized (this.mRequestLock) {
                    sessionCreationRequest = this.mPendingSessionCreationRequest;
                    this.mPendingSessionCreationRequest = null;
                }
                if (sessionCreationRequest != null) {
                    if (TextUtils.equals(this.mSelectedRouteId, sessionCreationRequest.mRouteId)) {
                        this.mCallback.onSessionCreated(this, sessionCreationRequest.mRequestId, build);
                    } else {
                        this.mCallback.onRequestFailed(this, sessionCreationRequest.mRequestId, 0);
                    }
                }
            }
            if (Objects.equals(routingSessionInfo, build)) {
                return false;
            }
            if (DEBUG) {
                Slog.d("MR2SystemProvider", "Updating system routing session info : " + build);
            }
            this.mSessionInfos.clear();
            this.mSessionInfos.add(build);
            this.mDefaultSessionInfo = new RoutingSessionInfo.Builder("SYSTEM_SESSION", "").setProviderId(this.mUniqueId).setSystemSession(true).addSelectedRoute("DEFAULT_ROUTE").build();
            return true;
        }
    }

    public void publishProviderState() {
        updateProviderState();
        notifyProviderState();
    }

    public void notifySessionInfoUpdated() {
        RoutingSessionInfo routingSessionInfo;
        if (this.mCallback == null) {
            return;
        }
        synchronized (this.mLock) {
            routingSessionInfo = (RoutingSessionInfo) this.mSessionInfos.get(0);
        }
        this.mCallback.onSessionUpdated(this, routingSessionInfo);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public String getDebugString() {
        return TextUtils.formatSimple("SystemMR2Provider - package: %s, selected route id: %s, bluetooth impl: %s", new Object[]{this.mComponentName.getPackageName(), this.mSelectedRouteId, this.mBluetoothRouteController.getClass().getSimpleName()});
    }

    public class SessionCreationRequest {
        public final long mRequestId;
        public final String mRouteId;

        public SessionCreationRequest(long j, String str) {
            this.mRequestId = j;
            this.mRouteId = str;
        }
    }

    public void updateVolume() {
        int devicesForStream = this.mAudioManager.getDevicesForStream(3);
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        if (this.mDefaultRoute.getVolume() != streamVolume) {
            this.mDefaultRoute = new MediaRoute2Info.Builder(this.mDefaultRoute).setVolume(streamVolume).build();
        }
        if (this.mBluetoothRouteController.updateVolumeForDevices(devicesForStream, streamVolume)) {
            return;
        }
        this.mDeviceRouteController.updateVolume(streamVolume);
        publishProviderState();
    }

    public class AudioManagerBroadcastReceiver extends BroadcastReceiver {
        public AudioManagerBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
                if (intent.getIntExtra("com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0) == 2) {
                    SystemMediaRoute2Provider.this.postDeviceRoute("MUSIC_SHARE", intExtra == 2, 25);
                }
            } else if (intent.getAction().equals("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE")) {
                SystemMediaRoute2Provider.this.postDeviceRoute("SCREEN_MIRRORING", intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0) == 1, 25);
            }
            if ((intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION") || intent.getAction().equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                SystemMediaRoute2Provider.this.updateVolume();
            }
        }
    }

    public final MediaRoute2Info buildDeviceRoute(String str, boolean z, int i) {
        Log.i("MR2SystemProvider", "buildDeviceRoute id:" + str + " state:" + z + " type:" + i);
        MediaRoute2Info build = new MediaRoute2Info.Builder(str, this.mContext.getResources().getText(R.string.fingerprint_error_vendor_unknown).toString()).setVolumeHandling(!this.mAudioManager.isVolumeFixed() ? 1 : 0).setVolume(this.mAudioManager.getStreamVolume(3)).setVolumeMax(this.mAudioManager.getStreamMaxVolume(3)).setType(i).addFeature("android.media.route.feature.LIVE_AUDIO").addFeature("android.media.route.feature.LIVE_VIDEO").addFeature("android.media.route.feature.LOCAL_PLAYBACK").setConnectionState(2).build();
        if ("MUSIC_SHARE".equals(str)) {
            if (z) {
                this.mMusicShareDeviceRoute = build;
            } else {
                this.mMusicShareDeviceRoute = null;
            }
        } else if ("SCREEN_MIRRORING".equals(str)) {
            if (z) {
                this.mScreenMirroringRoute = build;
            } else {
                this.mScreenMirroringRoute = null;
            }
        }
        return build;
    }

    public final void postDeviceRoute(final String str, final boolean z, final int i) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SystemMediaRoute2Provider.this.lambda$postDeviceRoute$5(str, z, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postDeviceRoute$5(String str, boolean z, int i) {
        buildDeviceRoute(str, z, i);
        updateProviderState();
        notifyProviderState();
    }
}
