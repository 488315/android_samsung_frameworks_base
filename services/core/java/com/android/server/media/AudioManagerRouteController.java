package com.android.server.media;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.audiopolicy.AudioProductStrategy;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.media.flags.Flags;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class AudioManagerRouteController implements DeviceRouteController {
    public static final SparseArray AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO;
    public static final AudioAttributes MEDIA_USAGE_AUDIO_ATTRIBUTES =
            new AudioAttributes.Builder().setUsage(1).build();
    public final AudioManager mAudioManager;
    public final BluetoothDeviceRoutesManager mBluetoothRouteController;
    public final int mBuiltInSpeakerSuitabilityStatus;
    public final Context mContext;
    public final Handler mHandler;
    public final SystemMediaRoute2Provider$$ExternalSyntheticLambda2 mOnDeviceRouteChangedListener;
    public MediaRoute2Info mSelectedRoute;
    public final AudioProductStrategy mStrategyForMedia;
    public final AudioDeviceCallbackImpl mAudioDeviceCallback = new AudioDeviceCallbackImpl();
    public final AudioManagerRouteController$$ExternalSyntheticLambda3
            mOnDevicesForAttributesChangedListener =
                    new AudioManager
                            .OnDevicesForAttributesChangedListener() { // from class:
                                                                       // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda3
                        public final void onDevicesForAttributesChanged(
                                AudioAttributes audioAttributes, List list) {
                            AudioManagerRouteController audioManagerRouteController =
                                    AudioManagerRouteController.this;
                            audioManagerRouteController.getClass();
                            if (audioAttributes.getUsage() == 1) {
                                audioManagerRouteController.rebuildAvailableRoutesAndNotify();
                            }
                        }
                    };
    public final Map mRouteIdToAvailableDeviceRoutes = new HashMap();

    public final class AudioDeviceCallbackImpl extends AudioDeviceCallback {
        public AudioDeviceCallbackImpl() {}

        @Override // android.media.AudioDeviceCallback
        public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
            for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                if (AudioManagerRouteController.AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.contains(
                        audioDeviceInfo.getType())) {
                    AudioManagerRouteController audioManagerRouteController =
                            AudioManagerRouteController.this;
                    audioManagerRouteController.mAudioManager.removePreferredDeviceForStrategy(
                            audioManagerRouteController.mStrategyForMedia);
                    AudioManagerRouteController.this.rebuildAvailableRoutesAndNotify();
                    return;
                }
            }
        }

        @Override // android.media.AudioDeviceCallback
        public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
            for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                if (AudioManagerRouteController.AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.contains(
                        audioDeviceInfo.getType())) {
                    AudioManagerRouteController.this.rebuildAvailableRoutesAndNotify();
                    return;
                }
            }
        }
    }

    public final class MediaRoute2InfoHolder {
        public final int mAudioDeviceInfoType;
        public final boolean mCorrespondsToInactiveBluetoothRoute;
        public final MediaRoute2Info mMediaRoute2Info;

        public MediaRoute2InfoHolder(MediaRoute2Info mediaRoute2Info, int i, boolean z) {
            this.mMediaRoute2Info = mediaRoute2Info;
            this.mAudioDeviceInfoType = i;
            this.mCorrespondsToInactiveBluetoothRoute = z;
        }
    }

    public final class SystemRouteInfo {
        public final String mDefaultRouteId;
        public final int mMediaRoute2InfoType;
        public final int mNameResource;

        public SystemRouteInfo(int i, String str, int i2) {
            this.mMediaRoute2InfoType = i;
            this.mDefaultRouteId = str;
            this.mNameResource = i2;
        }
    }

    static {
        SparseArray sparseArray = new SparseArray();
        AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO = sparseArray;
        sparseArray.put(
                2,
                new SystemRouteInfo(
                        2, "ROUTE_ID_BUILTIN_SPEAKER", R.string.global_action_power_off));
        sparseArray.put(
                3,
                new SystemRouteInfo(
                        3, "ROUTE_ID_WIRED_HEADSET", R.string.global_action_screenshot));
        sparseArray.put(
                4,
                new SystemRouteInfo(
                        4, "ROUTE_ID_WIRED_HEADPHONES", R.string.global_action_screenshot));
        sparseArray.put(
                8,
                new SystemRouteInfo(
                        8, "ROUTE_ID_BLUETOOTH_A2DP", R.string.config_defaultWellbeingPackage));
        sparseArray.put(9, new SystemRouteInfo(9, "ROUTE_ID_HDMI", R.string.global_action_restart));
        sparseArray.put(
                13, new SystemRouteInfo(13, "ROUTE_ID_DOCK", R.string.global_action_power_options));
        sparseArray.put(
                11,
                new SystemRouteInfo(11, "ROUTE_ID_USB_DEVICE", R.string.global_action_settings));
        sparseArray.put(
                22,
                new SystemRouteInfo(22, "ROUTE_ID_USB_HEADSET", R.string.global_action_settings));
        sparseArray.put(
                10, new SystemRouteInfo(10, "ROUTE_ID_HDMI_ARC", R.string.global_action_restart));
        sparseArray.put(
                29, new SystemRouteInfo(29, "ROUTE_ID_HDMI_EARC", R.string.global_action_restart));
        sparseArray.put(
                23,
                new SystemRouteInfo(
                        23, "ROUTE_ID_HEARING_AID", R.string.config_defaultWellbeingPackage));
        sparseArray.put(
                26,
                new SystemRouteInfo(
                        26, "ROUTE_ID_BLE_HEADSET", R.string.config_defaultWellbeingPackage));
        sparseArray.put(
                27,
                new SystemRouteInfo(
                        26, "ROUTE_ID_BLE_SPEAKER", R.string.config_defaultWellbeingPackage));
        sparseArray.put(
                30,
                new SystemRouteInfo(
                        26, "ROUTE_ID_BLE_BROADCAST", R.string.config_defaultWellbeingPackage));
        sparseArray.put(
                6, new SystemRouteInfo(0, "ROUTE_ID_LINE_DIGITAL", R.string.global_action_restart));
        sparseArray.put(
                5, new SystemRouteInfo(0, "ROUTE_ID_LINE_ANALOG", R.string.global_action_restart));
        sparseArray.put(
                19, new SystemRouteInfo(0, "ROUTE_ID_AUX_LINE", R.string.global_action_restart));
        sparseArray.put(
                31,
                new SystemRouteInfo(
                        13, "ROUTE_ID_DOCK_ANALOG", R.string.global_action_power_options));
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda3] */
    public AudioManagerRouteController(
            Context context,
            AudioManager audioManager,
            Looper looper,
            AudioProductStrategy audioProductStrategy,
            BluetoothAdapter bluetoothAdapter,
            SystemMediaRoute2Provider$$ExternalSyntheticLambda2
                    systemMediaRoute2Provider$$ExternalSyntheticLambda2) {
        int integer;
        Objects.requireNonNull(context);
        this.mContext = context;
        Objects.requireNonNull(audioManager);
        this.mAudioManager = audioManager;
        Objects.requireNonNull(looper);
        Handler handler = new Handler(looper);
        this.mHandler = handler;
        this.mStrategyForMedia = audioProductStrategy;
        this.mOnDeviceRouteChangedListener = systemMediaRoute2Provider$$ExternalSyntheticLambda2;
        int i = 0;
        if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()
                && ((integer =
                                        context.getResources()
                                                .getInteger(
                                                        R.integer.config_ntpPollingIntervalShorter))
                                == 0
                        || integer == 1
                        || integer == 2)) {
            i = integer;
        }
        this.mBuiltInSpeakerSuitabilityStatus = i;
        this.mBluetoothRouteController =
                new BluetoothDeviceRoutesManager(
                        context,
                        handler,
                        bluetoothAdapter,
                        new BluetoothProfileMonitor(context, bluetoothAdapter),
                        new BluetoothRouteController
                                .BluetoothRoutesUpdatedListener() { // from class:
                                                                    // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda4
                            @Override // com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener
                            public final void onBluetoothRoutesUpdated() {
                                AudioManagerRouteController.this.rebuildAvailableRoutesAndNotify();
                            }
                        });
        rebuildAvailableRoutes();
    }

    public final MediaRoute2Info createMediaRoute2Info(
            String str, int i, CharSequence charSequence, String str2) {
        SystemRouteInfo systemRouteInfo =
                (SystemRouteInfo) AUDIO_DEVICE_INFO_TYPE_TO_ROUTE_INFO.get(i);
        if (systemRouteInfo == null) {
            return null;
        }
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = this.mContext.getResources().getText(systemRouteInfo.mNameResource);
        }
        if (str == null) {
            str = systemRouteInfo.mDefaultRouteId;
        }
        MediaRoute2Info.Builder builder = new MediaRoute2Info.Builder(str, charSequence);
        int i2 = systemRouteInfo.mMediaRoute2InfoType;
        MediaRoute2Info.Builder connectionState =
                builder.setType(i2)
                        .setAddress(str2)
                        .setSystemRoute(true)
                        .addFeature("android.media.route.feature.LIVE_AUDIO")
                        .addFeature("android.media.route.feature.LOCAL_PLAYBACK")
                        .setConnectionState(2);
        if (i2 == 2) {
            connectionState.setSuitabilityStatus(this.mBuiltInSpeakerSuitabilityStatus);
        }
        return connectionState.build();
    }

    public final MediaRoute2Info createMediaRoute2InfoFromAudioDeviceInfo(
            AudioDeviceInfo audioDeviceInfo) {
        String str;
        String address = audioDeviceInfo.getAddress();
        String name = audioDeviceInfo.getPort().name();
        if (!TextUtils.isEmpty(address)) {
            BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager =
                    this.mBluetoothRouteController;
            synchronized (bluetoothDeviceRoutesManager) {
                BluetoothDevice bluetoothDevice =
                        (BluetoothDevice)
                                bluetoothDeviceRoutesManager.mAddressToBondedDevice.get(address);
                if (bluetoothDevice != null) {
                    BluetoothProfileMonitor bluetoothProfileMonitor =
                            bluetoothDeviceRoutesManager.mBluetoothProfileMonitor;
                    str =
                            bluetoothDeviceRoutesManager.getRouteIdForType(
                                    bluetoothDevice,
                                    bluetoothProfileMonitor.isProfileSupported(bluetoothDevice, 22)
                                            ? 26
                                            : bluetoothProfileMonitor.isProfileSupported(
                                                            bluetoothDevice, 21)
                                                    ? 23
                                                    : 8);
                } else {
                    str = null;
                }
            }
            BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager2 =
                    this.mBluetoothRouteController;
            synchronized (bluetoothDeviceRoutesManager2) {
                BluetoothDevice bluetoothDevice2 =
                        (BluetoothDevice)
                                bluetoothDeviceRoutesManager2.mAddressToBondedDevice.get(address);
                r3 =
                        bluetoothDevice2 != null
                                ? bluetoothDeviceRoutesManager2.getDeviceName(bluetoothDevice2)
                                : null;
            }
            name = r3;
            r3 = str;
        }
        return createMediaRoute2Info(r3, audioDeviceInfo.getType(), name, address);
    }

    @Override // com.android.server.media.DeviceRouteController
    public final synchronized List getAvailableRoutes() {
        return ((HashMap) this.mRouteIdToAvailableDeviceRoutes)
                .values().stream()
                        .map(new AudioManagerRouteController$$ExternalSyntheticLambda1(1))
                        .toList();
    }

    @Override // com.android.server.media.DeviceRouteController
    public final synchronized MediaRoute2Info getSelectedRoute() {
        return this.mSelectedRoute;
    }

    public final void rebuildAvailableRoutes() {
        int type;
        List devicesForAttributes =
                this.mAudioManager.getDevicesForAttributes(MEDIA_USAGE_AUDIO_ATTRIBUTES);
        boolean z = false;
        if (devicesForAttributes.isEmpty()) {
            Slog.e(
                    "MR2SystemProvider",
                    "Unexpected empty list of output devices for media. Using built-in speakers.");
            type = 2;
        } else {
            if (devicesForAttributes.size() > 1) {
                Slog.w(
                        "MR2SystemProvider",
                        "AudioManager.getDevicesForAttributes returned more than one element. Using"
                            + " the first one.");
            }
            type = ((AudioDeviceAttributes) devicesForAttributes.get(0)).getType();
        }
        AudioDeviceInfo[] devices = this.mAudioManager.getDevices(2);
        BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager = this.mBluetoothRouteController;
        bluetoothDeviceRoutesManager.getClass();
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        synchronized (bluetoothDeviceRoutesManager) {
            try {
                for (BluetoothDeviceRoutesManager.BluetoothRouteInfo bluetoothRouteInfo :
                        ((HashMap) bluetoothDeviceRoutesManager.mBluetoothRoutes).values()) {
                    if (hashSet.add(bluetoothRouteInfo.mRoute.getId())) {
                        arrayList.add(bluetoothRouteInfo.mRoute);
                    }
                }
            } finally {
            }
        }
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
        boolean isVolumeFixed = this.mAudioManager.isVolumeFixed();
        synchronized (this) {
            try {
                ((HashMap) this.mRouteIdToAvailableDeviceRoutes).clear();
                int length = devices.length;
                int i = 0;
                MediaRoute2InfoHolder mediaRoute2InfoHolder = null;
                while (i < length) {
                    AudioDeviceInfo audioDeviceInfo = devices[i];
                    MediaRoute2Info createMediaRoute2InfoFromAudioDeviceInfo =
                            createMediaRoute2InfoFromAudioDeviceInfo(audioDeviceInfo);
                    if (createMediaRoute2InfoFromAudioDeviceInfo != null) {
                        int type2 = audioDeviceInfo.getType();
                        MediaRoute2InfoHolder mediaRoute2InfoHolder2 =
                                new MediaRoute2InfoHolder(
                                        createMediaRoute2InfoFromAudioDeviceInfo, type2, z);
                        ((HashMap) this.mRouteIdToAvailableDeviceRoutes)
                                .put(
                                        createMediaRoute2InfoFromAudioDeviceInfo.getId(),
                                        mediaRoute2InfoHolder2);
                        if (type == type2) {
                            mediaRoute2InfoHolder = mediaRoute2InfoHolder2;
                        }
                    }
                    i++;
                    z = false;
                }
                if (((HashMap) this.mRouteIdToAvailableDeviceRoutes).isEmpty()) {
                    Slog.e(
                            "MR2SystemProvider",
                            "Ended up with an empty list of routes. Creating a placeholder route.");
                    MediaRoute2Info createMediaRoute2Info =
                            createMediaRoute2Info(null, 2, null, null);
                    ((HashMap) this.mRouteIdToAvailableDeviceRoutes)
                            .put(
                                    createMediaRoute2Info.getId(),
                                    new MediaRoute2InfoHolder(createMediaRoute2Info, 2, false));
                }
                if (mediaRoute2InfoHolder == null) {
                    Slog.e(
                            "MR2SystemProvider",
                            "Could not map this selected device attribute type to an available"
                                + " route: "
                                    + type);
                    mediaRoute2InfoHolder =
                            (MediaRoute2InfoHolder)
                                    ((HashMap) this.mRouteIdToAvailableDeviceRoutes)
                                            .values()
                                            .iterator()
                                            .next();
                }
                mediaRoute2InfoHolder.getClass();
                MediaRoute2Info build =
                        new MediaRoute2Info.Builder(mediaRoute2InfoHolder.mMediaRoute2Info)
                                .setVolumeHandling(1 ^ (isVolumeFixed ? 1 : 0))
                                .setVolume(streamVolume)
                                .setVolumeMax(streamMaxVolume)
                                .build();
                ((HashMap) this.mRouteIdToAvailableDeviceRoutes)
                        .put(
                                mediaRoute2InfoHolder.mMediaRoute2Info.getId(),
                                new MediaRoute2InfoHolder(
                                        build,
                                        mediaRoute2InfoHolder.mAudioDeviceInfoType,
                                        mediaRoute2InfoHolder
                                                .mCorrespondsToInactiveBluetoothRoute));
                this.mSelectedRoute = build;
                arrayList.stream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return !((HashMap)
                                                        AudioManagerRouteController.this
                                                                .mRouteIdToAvailableDeviceRoutes)
                                                .containsKey(((MediaRoute2Info) obj).getId());
                                    }
                                })
                        .map(new AudioManagerRouteController$$ExternalSyntheticLambda1(0))
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        AudioManagerRouteController.MediaRoute2InfoHolder
                                                mediaRoute2InfoHolder3 =
                                                        (AudioManagerRouteController
                                                                        .MediaRoute2InfoHolder)
                                                                obj;
                                        ((HashMap)
                                                        AudioManagerRouteController.this
                                                                .mRouteIdToAvailableDeviceRoutes)
                                                .put(
                                                        mediaRoute2InfoHolder3.mMediaRoute2Info
                                                                .getId(),
                                                        mediaRoute2InfoHolder3);
                                    }
                                });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void rebuildAvailableRoutesAndNotify() {
        rebuildAvailableRoutes();
        SystemMediaRoute2Provider systemMediaRoute2Provider =
                this.mOnDeviceRouteChangedListener.f$0;
        systemMediaRoute2Provider.mHandler.post(
                new SystemMediaRoute2Provider$$ExternalSyntheticLambda1(
                        systemMediaRoute2Provider, 2));
    }

    @Override // com.android.server.media.DeviceRouteController
    public final void start(UserHandle userHandle) {
        BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager = this.mBluetoothRouteController;
        BluetoothProfileMonitor bluetoothProfileMonitor =
                bluetoothDeviceRoutesManager.mBluetoothProfileMonitor;
        BluetoothAdapter bluetoothAdapter = bluetoothProfileMonitor.mBluetoothAdapter;
        Context context = bluetoothProfileMonitor.mContext;
        BluetoothProfileMonitor.ProfileListener profileListener =
                bluetoothProfileMonitor.mProfileListener;
        bluetoothAdapter.getProfileProxy(context, profileListener, 2);
        bluetoothProfileMonitor.mBluetoothAdapter.getProfileProxy(
                bluetoothProfileMonitor.mContext, profileListener, 21);
        bluetoothProfileMonitor.mBluetoothAdapter.getProfileProxy(
                bluetoothProfileMonitor.mContext, profileListener, 22);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        bluetoothDeviceRoutesManager.mContext.registerReceiverAsUser(
                bluetoothDeviceRoutesManager.mAdapterStateChangedReceiver,
                userHandle,
                intentFilter,
                null,
                null);
        bluetoothDeviceRoutesManager.mContext.registerReceiverAsUser(
                bluetoothDeviceRoutesManager.mDeviceStateChangedReceiver,
                userHandle,
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED",
                        "android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED",
                        "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED",
                        "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED",
                        "android.bluetooth.device.action.ALIAS_CHANGED"),
                null,
                null);
        bluetoothDeviceRoutesManager.updateBluetoothRoutes();
        AudioManager audioManager = this.mAudioManager;
        Handler handler = this.mHandler;
        audioManager.registerAudioDeviceCallback(this.mAudioDeviceCallback, handler);
        this.mAudioManager.addOnDevicesForAttributesChangedListener(
                AudioRoutingUtils.ATTRIBUTES_MEDIA,
                new android.os.HandlerExecutor(handler),
                this.mOnDevicesForAttributesChangedListener);
    }

    @Override // com.android.server.media.DeviceRouteController
    public final void stop() {
        this.mAudioManager.removeOnDevicesForAttributesChangedListener(
                this.mOnDevicesForAttributesChangedListener);
        this.mAudioManager.unregisterAudioDeviceCallback(this.mAudioDeviceCallback);
        BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager = this.mBluetoothRouteController;
        bluetoothDeviceRoutesManager.mContext.unregisterReceiver(
                bluetoothDeviceRoutesManager.mAdapterStateChangedReceiver);
        bluetoothDeviceRoutesManager.mContext.unregisterReceiver(
                bluetoothDeviceRoutesManager.mDeviceStateChangedReceiver);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.server.media.DeviceRouteController
    public final void transferTo(final String str) {
        MediaRoute2InfoHolder mediaRoute2InfoHolder;
        final Runnable runnable;
        if (str == null) {
            Slog.e(
                    "MR2SystemProvider",
                    "Unexpected call to AudioPoliciesDeviceRouteController#transferTo(null)");
            return;
        }
        synchronized (this) {
            mediaRoute2InfoHolder =
                    (MediaRoute2InfoHolder)
                            ((HashMap) this.mRouteIdToAvailableDeviceRoutes).get(str);
        }
        if (mediaRoute2InfoHolder == null) {
            Slog.w(
                    "MR2SystemProvider",
                    "transferTo: Ignoring transfer request to unknown route id : ".concat(str));
            return;
        }
        if (mediaRoute2InfoHolder.mCorrespondsToInactiveBluetoothRoute) {
            final String address = mediaRoute2InfoHolder.mMediaRoute2Info.getAddress();
            final int i = 0;
            runnable =
                    new Runnable(this) { // from class:
                        // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda7
                        public final /* synthetic */ AudioManagerRouteController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i) {
                                case 0:
                                    AudioManagerRouteController audioManagerRouteController =
                                            this.f$0;
                                    String str2 = (String) address;
                                    BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager =
                                            audioManagerRouteController.mBluetoothRouteController;
                                    synchronized (bluetoothDeviceRoutesManager) {
                                        BluetoothDeviceRoutesManager.BluetoothRouteInfo
                                                bluetoothRouteInfo =
                                                        (BluetoothDeviceRoutesManager
                                                                        .BluetoothRouteInfo)
                                                                ((HashMap)
                                                                                bluetoothDeviceRoutesManager
                                                                                        .mBluetoothRoutes)
                                                                        .get(str2);
                                        if (bluetoothRouteInfo == null) {
                                            Slog.w(
                                                    "MR2SystemProvider",
                                                    "activateBluetoothDeviceWithAddress: Ignoring"
                                                        + " unknown address "
                                                            + str2);
                                        } else {
                                            bluetoothDeviceRoutesManager.mBluetoothAdapter
                                                    .setActiveDevice(
                                                            bluetoothRouteInfo.mBtDevice, 0);
                                        }
                                    }
                                    audioManagerRouteController.mAudioManager
                                            .removePreferredDeviceForStrategy(
                                                    audioManagerRouteController.mStrategyForMedia);
                                    return;
                                default:
                                    AudioManagerRouteController audioManagerRouteController2 =
                                            this.f$0;
                                    audioManagerRouteController2.mAudioManager
                                            .setPreferredDeviceForStrategy(
                                                    audioManagerRouteController2.mStrategyForMedia,
                                                    (AudioDeviceAttributes) address);
                                    return;
                            }
                        }
                    };
        } else {
            final AudioDeviceAttributes audioDeviceAttributes =
                    new AudioDeviceAttributes(2, mediaRoute2InfoHolder.mAudioDeviceInfoType, "");
            final int i2 = 1;
            runnable =
                    new Runnable(this) { // from class:
                        // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda7
                        public final /* synthetic */ AudioManagerRouteController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i2) {
                                case 0:
                                    AudioManagerRouteController audioManagerRouteController =
                                            this.f$0;
                                    String str2 = (String) audioDeviceAttributes;
                                    BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager =
                                            audioManagerRouteController.mBluetoothRouteController;
                                    synchronized (bluetoothDeviceRoutesManager) {
                                        BluetoothDeviceRoutesManager.BluetoothRouteInfo
                                                bluetoothRouteInfo =
                                                        (BluetoothDeviceRoutesManager
                                                                        .BluetoothRouteInfo)
                                                                ((HashMap)
                                                                                bluetoothDeviceRoutesManager
                                                                                        .mBluetoothRoutes)
                                                                        .get(str2);
                                        if (bluetoothRouteInfo == null) {
                                            Slog.w(
                                                    "MR2SystemProvider",
                                                    "activateBluetoothDeviceWithAddress: Ignoring"
                                                        + " unknown address "
                                                            + str2);
                                        } else {
                                            bluetoothDeviceRoutesManager.mBluetoothAdapter
                                                    .setActiveDevice(
                                                            bluetoothRouteInfo.mBtDevice, 0);
                                        }
                                    }
                                    audioManagerRouteController.mAudioManager
                                            .removePreferredDeviceForStrategy(
                                                    audioManagerRouteController.mStrategyForMedia);
                                    return;
                                default:
                                    AudioManagerRouteController audioManagerRouteController2 =
                                            this.f$0;
                                    audioManagerRouteController2.mAudioManager
                                            .setPreferredDeviceForStrategy(
                                                    audioManagerRouteController2.mStrategyForMedia,
                                                    (AudioDeviceAttributes) audioDeviceAttributes);
                                    return;
                            }
                        }
                    };
        }
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        final AudioManagerRouteController audioManagerRouteController =
                                AudioManagerRouteController.this;
                        Runnable runnable2 = runnable;
                        String str2 = str;
                        audioManagerRouteController.getClass();
                        try {
                            runnable2.run();
                        } catch (Throwable th) {
                            Slog.e(
                                    "MR2SystemProvider",
                                    "Unexpected exception while transferring to route id: " + str2,
                                    th);
                            audioManagerRouteController.mHandler.post(
                                    new Runnable() { // from class:
                                                     // com.android.server.media.AudioManagerRouteController$$ExternalSyntheticLambda9
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            AudioManagerRouteController.this
                                                    .rebuildAvailableRoutesAndNotify();
                                        }
                                    });
                        }
                    }
                });
    }

    @Override // com.android.server.media.DeviceRouteController
    public final boolean updateVolume(int i) {
        rebuildAvailableRoutesAndNotify();
        return true;
    }
}
