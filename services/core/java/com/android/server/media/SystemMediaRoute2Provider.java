package com.android.server.media;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.IAudioService;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.RouteDiscoveryPreference;
import android.media.RoutingSessionInfo;
import android.media.audiopolicy.AudioProductStrategy;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.media.flags.Flags;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemMediaRoute2Provider extends MediaRoute2Provider {
    public static final ComponentName COMPONENT_NAME =
            new ComponentName(
                    SystemMediaRoute2Provider.class.getPackage().getName(),
                    SystemMediaRoute2Provider.class.getName());
    public final AudioManager mAudioManager;
    public final AudioManagerBroadcastReceiver mAudioReceiver;
    public final BluetoothRouteController mBluetoothRouteController;
    public final Context mContext;
    public MediaRoute2Info mDefaultRoute;
    public RoutingSessionInfo mDefaultSessionInfo;
    public final DeviceRouteController mDeviceRouteController;
    public final Handler mHandler;
    public MediaRoute2Info mMusicShareDeviceRoute;
    public volatile MediaRoute2Provider.SessionCreationOrTransferRequest
            mPendingSessionCreationOrTransferRequest;
    public volatile MediaRoute2Provider.SessionCreationOrTransferRequest mPendingTransferRequest;
    public final Object mRequestLock;
    public MediaRoute2Info mScreenMirroringRoute;
    public String mSelectedRouteId;
    public final Object mTransferLock;
    public final UserHandle mUser;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioManagerBroadcastReceiver extends BroadcastReceiver {
        public AudioManagerBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            final boolean z;
            if ("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED"
                    .equals(intent.getAction())) {
                int intExtra =
                        intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
                if (intent.getIntExtra(
                                "com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0)
                        == 2) {
                    final SystemMediaRoute2Provider systemMediaRoute2Provider =
                            SystemMediaRoute2Provider.this;
                    z = intExtra == 2;
                    final String str = "MUSIC_SHARE";
                    systemMediaRoute2Provider.mHandler.post(
                            new Runnable() { // from class:
                                // com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda6
                                public final /* synthetic */ int f$3 = 25;

                                @Override // java.lang.Runnable
                                public final void run() {
                                    SystemMediaRoute2Provider systemMediaRoute2Provider2 =
                                            SystemMediaRoute2Provider.this;
                                    String str2 = str;
                                    boolean z2 = z;
                                    int i = this.f$3;
                                    systemMediaRoute2Provider2.getClass();
                                    Log.i(
                                            "MR2SystemProvider",
                                            "buildDeviceRoute id:"
                                                    + str2
                                                    + " state:"
                                                    + z2
                                                    + " type:"
                                                    + i);
                                    MediaRoute2Info build =
                                            new MediaRoute2Info.Builder(
                                                            str2,
                                                            systemMediaRoute2Provider2
                                                                    .mContext
                                                                    .getResources()
                                                                    .getText(
                                                                            R.string
                                                                                    .global_action_power_off)
                                                                    .toString())
                                                    .setVolumeHandling(
                                                            !systemMediaRoute2Provider2
                                                                            .mAudioManager
                                                                            .isVolumeFixed()
                                                                    ? 1
                                                                    : 0)
                                                    .setVolume(
                                                            systemMediaRoute2Provider2.mAudioManager
                                                                    .getStreamVolume(3))
                                                    .setVolumeMax(
                                                            systemMediaRoute2Provider2.mAudioManager
                                                                    .getStreamMaxVolume(3))
                                                    .setType(i)
                                                    .addFeature(
                                                            "android.media.route.feature.LIVE_AUDIO")
                                                    .addFeature(
                                                            "android.media.route.feature.LIVE_VIDEO")
                                                    .addFeature(
                                                            "android.media.route.feature.LOCAL_PLAYBACK")
                                                    .setConnectionState(2)
                                                    .build();
                                    if ("MUSIC_SHARE".equals(str2)) {
                                        if (z2) {
                                            systemMediaRoute2Provider2.mMusicShareDeviceRoute =
                                                    build;
                                        } else {
                                            systemMediaRoute2Provider2.mMusicShareDeviceRoute =
                                                    null;
                                        }
                                    } else if ("SCREEN_MIRRORING".equals(str2)) {
                                        if (z2) {
                                            systemMediaRoute2Provider2.mScreenMirroringRoute =
                                                    build;
                                        } else {
                                            systemMediaRoute2Provider2.mScreenMirroringRoute = null;
                                        }
                                    }
                                    systemMediaRoute2Provider2.updateProviderState();
                                    systemMediaRoute2Provider2.notifyProviderState();
                                }
                            });
                }
            } else if ("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE"
                    .equals(intent.getAction())) {
                int intExtra2 =
                        intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0);
                final SystemMediaRoute2Provider systemMediaRoute2Provider2 =
                        SystemMediaRoute2Provider.this;
                z = intExtra2 == 1;
                final String str2 = "SCREEN_MIRRORING";
                systemMediaRoute2Provider2.mHandler.post(
                        new Runnable() { // from class:
                            // com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda6
                            public final /* synthetic */ int f$3 = 25;

                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemMediaRoute2Provider systemMediaRoute2Provider22 =
                                        SystemMediaRoute2Provider.this;
                                String str22 = str2;
                                boolean z2 = z;
                                int i = this.f$3;
                                systemMediaRoute2Provider22.getClass();
                                Log.i(
                                        "MR2SystemProvider",
                                        "buildDeviceRoute id:"
                                                + str22
                                                + " state:"
                                                + z2
                                                + " type:"
                                                + i);
                                MediaRoute2Info build =
                                        new MediaRoute2Info.Builder(
                                                        str22,
                                                        systemMediaRoute2Provider22
                                                                .mContext
                                                                .getResources()
                                                                .getText(
                                                                        R.string
                                                                                .global_action_power_off)
                                                                .toString())
                                                .setVolumeHandling(
                                                        !systemMediaRoute2Provider22.mAudioManager
                                                                        .isVolumeFixed()
                                                                ? 1
                                                                : 0)
                                                .setVolume(
                                                        systemMediaRoute2Provider22.mAudioManager
                                                                .getStreamVolume(3))
                                                .setVolumeMax(
                                                        systemMediaRoute2Provider22.mAudioManager
                                                                .getStreamMaxVolume(3))
                                                .setType(i)
                                                .addFeature(
                                                        "android.media.route.feature.LIVE_AUDIO")
                                                .addFeature(
                                                        "android.media.route.feature.LIVE_VIDEO")
                                                .addFeature(
                                                        "android.media.route.feature.LOCAL_PLAYBACK")
                                                .setConnectionState(2)
                                                .build();
                                if ("MUSIC_SHARE".equals(str22)) {
                                    if (z2) {
                                        systemMediaRoute2Provider22.mMusicShareDeviceRoute = build;
                                    } else {
                                        systemMediaRoute2Provider22.mMusicShareDeviceRoute = null;
                                    }
                                } else if ("SCREEN_MIRRORING".equals(str22)) {
                                    if (z2) {
                                        systemMediaRoute2Provider22.mScreenMirroringRoute = build;
                                    } else {
                                        systemMediaRoute2Provider22.mScreenMirroringRoute = null;
                                    }
                                }
                                systemMediaRoute2Provider22.updateProviderState();
                                systemMediaRoute2Provider22.notifyProviderState();
                            }
                        });
            }
            if (("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction())
                            || intent.getAction()
                                    .equals("android.media.STREAM_DEVICES_CHANGED_ACTION"))
                    && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                if (!Flags.enableMr2ServiceNonMainBgThread()) {
                    SystemMediaRoute2Provider.this.updateVolume();
                } else {
                    SystemMediaRoute2Provider systemMediaRoute2Provider3 =
                            SystemMediaRoute2Provider.this;
                    systemMediaRoute2Provider3.mHandler.post(
                            new SystemMediaRoute2Provider$$ExternalSyntheticLambda1(
                                    systemMediaRoute2Provider3, 3));
                }
            }
        }
    }

    public SystemMediaRoute2Provider(Context context, UserHandle userHandle, Looper looper) {
        super(COMPONENT_NAME);
        AudioProductStrategy audioProductStrategy;
        this.mAudioReceiver = new AudioManagerBroadcastReceiver();
        this.mRequestLock = new Object();
        this.mTransferLock = new Object();
        this.mMusicShareDeviceRoute = null;
        this.mScreenMirroringRoute = null;
        this.mIsSystemRouteProvider = true;
        this.mContext = context;
        this.mUser = userHandle;
        this.mHandler = new Handler(looper);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        SystemMediaRoute2Provider$$ExternalSyntheticLambda2
                systemMediaRoute2Provider$$ExternalSyntheticLambda2 =
                        new SystemMediaRoute2Provider$$ExternalSyntheticLambda2(this);
        BluetoothAdapter adapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
        this.mBluetoothRouteController =
                (adapter == null || Flags.enableAudioPoliciesDeviceAndBluetoothController())
                        ? new BluetoothRouteController.NoOpBluetoothRouteController()
                        : new LegacyBluetoothRouteController(
                                context,
                                adapter,
                                systemMediaRoute2Provider$$ExternalSyntheticLambda2);
        SystemMediaRoute2Provider$$ExternalSyntheticLambda2
                systemMediaRoute2Provider$$ExternalSyntheticLambda22 =
                        new SystemMediaRoute2Provider$$ExternalSyntheticLambda2(this);
        AudioManager audioManager = (AudioManager) context.getSystemService(AudioManager.class);
        AudioAttributes audioAttributes = AudioRoutingUtils.ATTRIBUTES_MEDIA;
        Iterator it = AudioManager.getAudioProductStrategies().iterator();
        while (true) {
            if (!it.hasNext()) {
                audioProductStrategy = null;
                break;
            }
            AudioProductStrategy audioProductStrategy2 = (AudioProductStrategy) it.next();
            if (audioProductStrategy2.supportsAudioAttributes(AudioRoutingUtils.ATTRIBUTES_MEDIA)) {
                audioProductStrategy = audioProductStrategy2;
                break;
            }
        }
        BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(BluetoothManager.class);
        BluetoothAdapter adapter2 = bluetoothManager != null ? bluetoothManager.getAdapter() : null;
        this.mDeviceRouteController =
                (audioProductStrategy == null
                                || adapter2 == null
                                || !Flags.enableAudioPoliciesDeviceAndBluetoothController())
                        ? new LegacyDeviceRouteController(
                                context,
                                audioManager,
                                IAudioService.Stub.asInterface(ServiceManager.getService("audio")),
                                systemMediaRoute2Provider$$ExternalSyntheticLambda22)
                        : new AudioManagerRouteController(
                                context,
                                audioManager,
                                looper,
                                audioProductStrategy,
                                adapter2,
                                systemMediaRoute2Provider$$ExternalSyntheticLambda22);
        updateProviderState();
        updateSessionInfosIfNeeded();
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void deselectRoute(String str, long j, String str2) {}

    public final RoutingSessionInfo generateDeviceRouteSelectedSessionInfo(String str) {
        synchronized (this.mLock) {
            try {
                if (((ArrayList) this.mSessionInfos).isEmpty()) {
                    return null;
                }
                MediaRoute2Info selectedRoute = this.mDeviceRouteController.getSelectedRoute();
                RoutingSessionInfo.Builder systemSession =
                        new RoutingSessionInfo.Builder("SYSTEM_SESSION", str)
                                .setSystemSession(true);
                systemSession.addSelectedRoute(selectedRoute.getId());
                Iterator it = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
                while (it.hasNext()) {
                    systemSession.addTransferableRoute(((MediaRoute2Info) it.next()).getId());
                }
                if (Flags.enableAudioPoliciesDeviceAndBluetoothController()) {
                    for (MediaRoute2Info mediaRoute2Info :
                            this.mDeviceRouteController.getAvailableRoutes()) {
                        if (!TextUtils.equals(selectedRoute.getId(), mediaRoute2Info.getId())) {
                            systemSession.addTransferableRoute(mediaRoute2Info.getId());
                        }
                    }
                }
                if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                    RoutingSessionInfo routingSessionInfo =
                            (RoutingSessionInfo) ((ArrayList) this.mSessionInfos).get(0);
                    systemSession
                            .setTransferReason(routingSessionInfo.getTransferReason())
                            .setTransferInitiator(
                                    routingSessionInfo.getTransferInitiatorUserHandle(),
                                    routingSessionInfo.getTransferInitiatorPackageName());
                }
                return systemSession.setProviderId(this.mUniqueId).build();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final String getDebugString() {
        return TextUtils.formatSimple(
                "SystemMR2Provider - package: %s, selected route id: %s, bluetooth impl: %s",
                new Object[] {
                    this.mComponentName.getPackageName(),
                    this.mSelectedRouteId,
                    this.mBluetoothRouteController.getClass().getSimpleName()
                });
    }

    public final void notifySessionInfoUpdated() {
        RoutingSessionInfo routingSessionInfo;
        if (this.mCallback == null) {
            return;
        }
        synchronized (this.mLock) {
            routingSessionInfo = (RoutingSessionInfo) ((ArrayList) this.mSessionInfos).get(0);
        }
        MediaRouter2ServiceImpl.UserHandler userHandler =
                (MediaRouter2ServiceImpl.UserHandler) this.mCallback;
        userHandler.getClass();
        userHandler.sendMessage(
                PooledLambda.obtainMessage(
                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(5),
                        userHandler,
                        this,
                        routingSessionInfo));
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void prepareReleaseSession(String str) {}

    @Override // com.android.server.media.MediaRoute2Provider
    public final void releaseSession(long j, String str) {}

    public final void reportPendingSessionRequestResultLockedIfNeeded(
            RoutingSessionInfo routingSessionInfo) {
        boolean z;
        if (this.mPendingSessionCreationOrTransferRequest == null) {
            return;
        }
        long j = this.mPendingSessionCreationOrTransferRequest.mRequestId;
        if (this.mPendingSessionCreationOrTransferRequest.mTargetOriginalRouteId.equals(
                this.mSelectedRouteId)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Session creation success to route "),
                    this.mPendingSessionCreationOrTransferRequest.mTargetOriginalRouteId,
                    "MR2SystemProvider");
            this.mPendingSessionCreationOrTransferRequest = null;
            ((MediaRouter2ServiceImpl.UserHandler) this.mCallback)
                    .onSessionCreated(this, j, routingSessionInfo);
            return;
        }
        Iterator it = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (TextUtils.equals(
                    ((MediaRoute2Info) it.next()).getId(),
                    this.mPendingSessionCreationOrTransferRequest.mTargetOriginalRouteId)) {
                z = true;
                break;
            }
        }
        if (Flags.enableWaitingStateForSystemSessionCreationRequest() && z) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Session creation waiting state to route "),
                    this.mPendingSessionCreationOrTransferRequest.mTargetOriginalRouteId,
                    "MR2SystemProvider");
            return;
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("Session creation failed to route "),
                this.mPendingSessionCreationOrTransferRequest.mTargetOriginalRouteId,
                "MR2SystemProvider");
        this.mPendingSessionCreationOrTransferRequest = null;
        ((MediaRouter2ServiceImpl.UserHandler) this.mCallback).onRequestFailed(this, j, 0);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void requestCreateSession(
            long j,
            String str,
            String str2,
            Bundle bundle,
            int i,
            UserHandle userHandle,
            String str3) {
        RoutingSessionInfo routingSessionInfo;
        if (TextUtils.equals(str2, "DEFAULT_ROUTE")) {
            ((MediaRouter2ServiceImpl.UserHandler) this.mCallback)
                    .onSessionCreated(this, j, this.mDefaultSessionInfo);
            return;
        }
        if (!Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()
                && TextUtils.equals(str2, this.mSelectedRouteId)) {
            synchronized (this.mLock) {
                routingSessionInfo = (RoutingSessionInfo) ((ArrayList) this.mSessionInfos).get(0);
            }
            ((MediaRouter2ServiceImpl.UserHandler) this.mCallback)
                    .onSessionCreated(this, j, routingSessionInfo);
            return;
        }
        synchronized (this.mRequestLock) {
            try {
                if (this.mPendingSessionCreationOrTransferRequest != null) {
                    ((MediaRouter2ServiceImpl.UserHandler) this.mCallback)
                            .onRequestFailed(
                                    this,
                                    this.mPendingSessionCreationOrTransferRequest.mRequestId,
                                    0);
                }
                this.mPendingSessionCreationOrTransferRequest =
                        new MediaRoute2Provider.SessionCreationOrTransferRequest(
                                j, str2, 0, userHandle, str3);
            } catch (Throwable th) {
                throw th;
            }
        }
        transferToRoute(j, userHandle, str3, "SYSTEM_SESSION", str2, i);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void selectRoute(String str, long j, String str2) {}

    @Override // com.android.server.media.MediaRoute2Provider
    public final void setRouteVolume(int i, String str, long j) {
        if (TextUtils.equals(str, this.mSelectedRouteId)) {
            this.mAudioManager.setStreamVolume(3, i, 0);
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void setSessionVolume(int i, String str, long j) {}

    @Override // com.android.server.media.MediaRoute2Provider
    public final void transferToRoute(
            long j, UserHandle userHandle, String str, String str2, String str3, int i) {
        final String str4;
        String id = this.mDeviceRouteController.getSelectedRoute().getId();
        if (!TextUtils.equals(str3, "DEFAULT_ROUTE")) {
            str4 = str3;
        } else {
            if (!Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                Log.w("MR2SystemProvider", "Ignoring transfer to DEFAULT_ROUTE");
                return;
            }
            str4 = id;
        }
        if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
            synchronized (this.mTransferLock) {
                this.mPendingTransferRequest =
                        new MediaRoute2Provider.SessionCreationOrTransferRequest(
                                j, str4, i, userHandle, str);
            }
        }
        boolean anyMatch =
                this.mDeviceRouteController.getAvailableRoutes().stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((MediaRoute2Info) obj).getId().equals(str4);
                                    }
                                });
        if (TextUtils.equals(str4, id) || anyMatch) {
            this.mDeviceRouteController.transferTo(str4);
            this.mBluetoothRouteController.transferTo(null);
        } else {
            this.mDeviceRouteController.transferTo(null);
            this.mBluetoothRouteController.transferTo(str4);
        }
        if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses() && updateSessionInfosIfNeeded()) {
            notifySessionInfoUpdated();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public final void updateDiscoveryPreference(
            Set set, RouteDiscoveryPreference routeDiscoveryPreference) {}

    public final void updateProviderState() {
        MediaRoute2ProviderInfo.Builder builder = new MediaRoute2ProviderInfo.Builder();
        boolean enableAudioPoliciesDeviceAndBluetoothController =
                Flags.enableAudioPoliciesDeviceAndBluetoothController();
        DeviceRouteController deviceRouteController = this.mDeviceRouteController;
        if (enableAudioPoliciesDeviceAndBluetoothController) {
            Iterator it = deviceRouteController.getAvailableRoutes().iterator();
            while (it.hasNext()) {
                builder.addRoute((MediaRoute2Info) it.next());
            }
            setProviderState(builder.build());
        } else {
            builder.addRoute(deviceRouteController.getSelectedRoute());
        }
        MediaRoute2Info mediaRoute2Info = this.mMusicShareDeviceRoute;
        if (mediaRoute2Info != null) {
            builder.addRoute(mediaRoute2Info);
        }
        MediaRoute2Info mediaRoute2Info2 = this.mScreenMirroringRoute;
        if (mediaRoute2Info2 != null) {
            builder.addRoute(mediaRoute2Info2);
        }
        Iterator it2 = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
        while (it2.hasNext()) {
            builder.addRoute((MediaRoute2Info) it2.next());
        }
        MediaRoute2ProviderInfo build = builder.build();
        setProviderState(build);
        Slog.d("MR2SystemProvider", "Updating system provider info : " + build);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x010b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateSessionInfosIfNeeded() {
        /*
            Method dump skipped, instructions count: 459
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.media.SystemMediaRoute2Provider.updateSessionInfosIfNeeded():boolean");
    }

    public final void updateVolume() {
        int devicesForStream = this.mAudioManager.getDevicesForStream(3);
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        if (this.mDefaultRoute.getVolume() != streamVolume) {
            this.mDefaultRoute =
                    new MediaRoute2Info.Builder(this.mDefaultRoute).setVolume(streamVolume).build();
        }
        if (this.mBluetoothRouteController.updateVolumeForDevices(devicesForStream, streamVolume)) {
            return;
        }
        this.mDeviceRouteController.updateVolume(streamVolume);
        updateProviderState();
        notifyProviderState();
    }
}
