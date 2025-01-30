package com.android.server.audio;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioDevicePort;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioPort;
import android.media.AudioRoutesInfo;
import android.media.AudioSystem;
import android.media.IAudioRoutesObserver;
import android.media.ICapturePresetDevicesRoleDispatcher;
import android.media.IStrategyNonDefaultDevicesDispatcher;
import android.media.IStrategyPreferredDevicesDispatcher;
import android.media.MediaMetrics;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.SafeCloseable;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.audio.AudioDeviceBroker;
import com.android.server.audio.AudioDeviceInventory;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import com.google.android.collect.Sets;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.media.AudioFxHelper;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.FactoryUtils;
import com.samsung.android.server.audio.ScreenSharingHelper;
import com.samsung.android.server.audio.utils.AudioUtils;
import com.samsung.android.server.audio.utils.BtUtils;
import com.samsung.android.server.audio.utils.KnoxAudioUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class AudioDeviceInventory {
    public static final Set BECOMING_NOISY_INTENT_DEVICES_SET;
    public static final int[] CAPTURE_PRESETS;
    public static final Set DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET;
    public final ArrayList mApmConnectedDevices;
    public final ArrayMap mAppliedPresetRoles;
    public final ArrayMap mAppliedPresetRolesInt;
    public final ArrayMap mAppliedStrategyRoles;
    public final ArrayMap mAppliedStrategyRolesInt;
    public final AudioSystemAdapter mAudioSystem;
    public final boolean mBluetoothDualModeEnabled;
    public final LinkedHashMap mConnectedDevices;
    public final AudioRoutesInfo mCurAudioRoutes;
    public final RemoteCallbackList mDevRoleCapturePresetDispatchers;
    public AudioDeviceBroker mDeviceBroker;
    public final Object mDevicesLock;
    public String mForcePath;
    public final RemoteCallbackList mNonDefDevDispatchers;
    public final ArrayMap mNonDefaultDevices;
    public final RemoteCallbackList mPrefDevDispatchers;
    public final ArrayMap mPreferredDevices;
    public final ArrayMap mPreferredDevicesForCapturePreset;
    public final RemoteCallbackList mRoutesObservers;
    public final List mStrategies;
    public boolean mSystemReady;

    public interface AudioSystemInterface {
        int deviceRoleAction(int i, int i2, List list);
    }

    public final boolean isBleOutDevice(int i) {
        return i == 536870912 || i == 536870913;
    }

    public AudioDeviceInventory(AudioDeviceBroker audioDeviceBroker) {
        this(audioDeviceBroker, AudioSystemAdapter.getDefaultAdapter());
    }

    public AudioDeviceInventory(AudioDeviceBroker audioDeviceBroker, AudioSystemAdapter audioSystemAdapter) {
        this.mDevicesLock = new Object();
        this.mConnectedDevices = new LinkedHashMap() { // from class: com.android.server.audio.AudioDeviceInventory.1
            @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
            public DeviceInfo put(String str, DeviceInfo deviceInfo) {
                DeviceInfo deviceInfo2 = (DeviceInfo) super.put((C07351) str, (String) deviceInfo);
                record("put", true, str, deviceInfo);
                return deviceInfo2;
            }

            @Override // java.util.HashMap, java.util.Map
            public DeviceInfo putIfAbsent(String str, DeviceInfo deviceInfo) {
                DeviceInfo deviceInfo2 = (DeviceInfo) super.putIfAbsent((C07351) str, (String) deviceInfo);
                if (deviceInfo2 == null) {
                    record("putIfAbsent", true, str, deviceInfo);
                }
                return deviceInfo2;
            }

            @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
            public DeviceInfo remove(Object obj) {
                DeviceInfo deviceInfo = (DeviceInfo) super.remove(obj);
                if (deviceInfo != null) {
                    record("remove", false, (String) obj, deviceInfo);
                }
                return deviceInfo;
            }

            @Override // java.util.HashMap, java.util.Map
            public boolean remove(Object obj, Object obj2) {
                boolean remove = super.remove(obj, obj2);
                if (remove) {
                    record("remove", false, (String) obj, (DeviceInfo) obj2);
                }
                return remove;
            }

            public final void record(String str, boolean z, String str2, DeviceInfo deviceInfo) {
                new MediaMetrics.Item("audio.device." + AudioSystem.getDeviceName(deviceInfo.mDeviceType)).set(MediaMetrics.Property.ADDRESS, deviceInfo.mDeviceAddress).set(MediaMetrics.Property.EVENT, str).set(MediaMetrics.Property.NAME, deviceInfo.mDeviceName).set(MediaMetrics.Property.STATE, z ? "connected" : "disconnected").record();
            }
        };
        this.mApmConnectedDevices = new ArrayList();
        this.mPreferredDevices = new ArrayMap();
        this.mNonDefaultDevices = new ArrayMap();
        this.mPreferredDevicesForCapturePreset = new ArrayMap();
        this.mCurAudioRoutes = new AudioRoutesInfo();
        this.mRoutesObservers = new RemoteCallbackList();
        this.mPrefDevDispatchers = new RemoteCallbackList();
        this.mNonDefDevDispatchers = new RemoteCallbackList();
        this.mDevRoleCapturePresetDispatchers = new RemoteCallbackList();
        this.mAppliedStrategyRoles = new ArrayMap();
        this.mAppliedStrategyRolesInt = new ArrayMap();
        this.mAppliedPresetRoles = new ArrayMap();
        this.mAppliedPresetRolesInt = new ArrayMap();
        this.mSystemReady = false;
        this.mForcePath = "";
        this.mDeviceBroker = audioDeviceBroker;
        this.mAudioSystem = audioSystemAdapter;
        this.mStrategies = AudioProductStrategy.getAudioProductStrategies();
        this.mBluetoothDualModeEnabled = SystemProperties.getBoolean("persist.bluetooth.enable_dual_mode_audio", false);
    }

    public class DeviceInfo {
        public final String mDeviceAddress;
        public int mDeviceCodecFormat;
        public final String mDeviceName;
        public final int mDeviceType;
        public ArraySet mDisabledModes;
        public final UUID mSensorUuid;

        public DeviceInfo(int i, String str, String str2, int i2, UUID uuid) {
            this.mDisabledModes = new ArraySet(0);
            this.mDeviceType = i;
            this.mDeviceName = str == null ? "" : str;
            this.mDeviceAddress = str2 == null ? "" : str2;
            this.mDeviceCodecFormat = i2;
            this.mSensorUuid = uuid;
        }

        public void setModeDisabled(String str) {
            this.mDisabledModes.add(str);
        }

        public void setModeEnabled(String str) {
            this.mDisabledModes.remove(str);
        }

        public boolean isModeEnabled(String str) {
            return !this.mDisabledModes.contains(str);
        }

        public boolean isOutputOnlyModeEnabled() {
            return isModeEnabled("audio_mode_output_only");
        }

        public boolean isDuplexModeEnabled() {
            return isModeEnabled("audio_mode_duplex");
        }

        public DeviceInfo(int i, String str, String str2, int i2) {
            this(i, str, str2, i2, null);
        }

        public DeviceInfo(int i, String str, String str2) {
            this(i, str, str2, 0);
        }

        public String toString() {
            return "[DeviceInfo: type:0x" + Integer.toHexString(this.mDeviceType) + " (" + AudioSystem.getDeviceName(this.mDeviceType) + ") name:" + this.mDeviceName + " addr:" + AudioManagerHelper.getAddressForLog(this.mDeviceAddress) + " codec: " + Integer.toHexString(this.mDeviceCodecFormat) + " sensorUuid: " + Objects.toString(this.mSensorUuid) + " disabled modes: " + this.mDisabledModes + "]";
        }

        public String getKey() {
            return makeDeviceListKey(this.mDeviceType, this.mDeviceAddress);
        }

        public static String makeDeviceListKey(int i, String str) {
            return "0x" + Integer.toHexString(i) + XmlUtils.STRING_ARRAY_SEPARATOR + str;
        }
    }

    public class WiredDeviceConnectionState {
        public final AudioDeviceAttributes mAttributes;
        public final String mCaller;
        public boolean mForTest = false;
        public final int mState;

        public WiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
            this.mAttributes = audioDeviceAttributes;
            this.mState = i;
            this.mCaller = str;
        }
    }

    public void dump(final PrintWriter printWriter, final String str) {
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "BECOMING_NOISY_INTENT_DEVICES_SET=");
        BECOMING_NOISY_INTENT_DEVICES_SET.forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AudioDeviceInventory.lambda$dump$0(printWriter, (Integer) obj);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Preferred devices for strategy:");
        this.mPreferredDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$1(printWriter, str, (Integer) obj, (List) obj2);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Non-default devices for strategy:");
        this.mNonDefaultDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$2(printWriter, str, (Integer) obj, (List) obj2);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Connected devices:");
        this.mConnectedDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda13
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$3(printWriter, str, (String) obj, (AudioDeviceInventory.DeviceInfo) obj2);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "APM Connected device (A2DP sink only):");
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "mBluetoothDualModeEnabled = " + this.mBluetoothDualModeEnabled);
        StringBuilder sb = new StringBuilder();
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append(str);
        sb.append("Preferred devices for capture preset:");
        printWriter.println(sb.toString());
        this.mPreferredDevicesForCapturePreset.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$4(printWriter, str, (Integer) obj, (List) obj2);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Applied devices roles for strategies (from API):");
        this.mAppliedStrategyRoles.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$5(printWriter, str, (Pair) obj, (List) obj2);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Applied devices roles for strategies (internal):");
        this.mAppliedStrategyRolesInt.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda16
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$6(printWriter, str, (Pair) obj, (List) obj2);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Applied devices roles for presets (from API):");
        this.mAppliedPresetRoles.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$7(printWriter, str, (Pair) obj, (List) obj2);
            }
        });
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "Applied devices roles for presets (internal:");
        this.mAppliedPresetRolesInt.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda18
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                AudioDeviceInventory.lambda$dump$8(printWriter, str, (Pair) obj, (List) obj2);
            }
        });
    }

    public static /* synthetic */ void lambda$dump$0(PrintWriter printWriter, Integer num) {
        printWriter.print(" 0x" + Integer.toHexString(num.intValue()));
    }

    public static /* synthetic */ void lambda$dump$1(PrintWriter printWriter, String str, Integer num, List list) {
        printWriter.println("  " + str + "strategy:" + num + " device:" + list);
    }

    public static /* synthetic */ void lambda$dump$2(PrintWriter printWriter, String str, Integer num, List list) {
        printWriter.println("  " + str + "strategy:" + num + " device:" + list);
    }

    public static /* synthetic */ void lambda$dump$3(PrintWriter printWriter, String str, String str2, DeviceInfo deviceInfo) {
        printWriter.println("  " + str + deviceInfo.toString());
    }

    public static /* synthetic */ void lambda$dump$4(PrintWriter printWriter, String str, Integer num, List list) {
        printWriter.println("  " + str + "capturePreset:" + num + " devices:" + list);
    }

    public static /* synthetic */ void lambda$dump$5(PrintWriter printWriter, String str, Pair pair, List list) {
        printWriter.println("  " + str + "strategy: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    public static /* synthetic */ void lambda$dump$6(PrintWriter printWriter, String str, Pair pair, List list) {
        printWriter.println("  " + str + "strategy: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    public static /* synthetic */ void lambda$dump$7(PrintWriter printWriter, String str, Pair pair, List list) {
        printWriter.println("  " + str + "preset: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    public static /* synthetic */ void lambda$dump$8(PrintWriter printWriter, String str, Pair pair, List list) {
        printWriter.println("  " + str + "preset: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    public void onRestoreDevices() {
        synchronized (this.mDevicesLock) {
            for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                if (!AudioUtils.isSkipRestoreDevice(deviceInfo.mDeviceType)) {
                    this.mAudioSystem.setDeviceConnectionState(new AudioDeviceAttributes(deviceInfo.mDeviceType, deviceInfo.mDeviceAddress, deviceInfo.mDeviceName), 1, deviceInfo.mDeviceCodecFormat);
                }
            }
            this.mAppliedStrategyRolesInt.clear();
            this.mAppliedPresetRolesInt.clear();
            applyConnectedDevicesRoles_l();
        }
        reapplyExternalDevicesRoles();
    }

    public void reapplyExternalDevicesRoles() {
        synchronized (this.mDevicesLock) {
            this.mAppliedStrategyRoles.clear();
            this.mAppliedPresetRoles.clear();
        }
        synchronized (this.mPreferredDevices) {
            this.mPreferredDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda27
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    AudioDeviceInventory.this.lambda$reapplyExternalDevicesRoles$9((Integer) obj, (List) obj2);
                }
            });
        }
        synchronized (this.mNonDefaultDevices) {
            this.mNonDefaultDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda28
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    AudioDeviceInventory.this.lambda$reapplyExternalDevicesRoles$10((Integer) obj, (List) obj2);
                }
            });
        }
        synchronized (this.mPreferredDevicesForCapturePreset) {
            this.mPreferredDevicesForCapturePreset.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda29
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    AudioDeviceInventory.this.lambda$reapplyExternalDevicesRoles$11((Integer) obj, (List) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reapplyExternalDevicesRoles$9(Integer num, List list) {
        setPreferredDevicesForStrategy(num.intValue(), list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reapplyExternalDevicesRoles$10(Integer num, List list) {
        addDevicesRoleForStrategy(num.intValue(), 2, list, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reapplyExternalDevicesRoles$11(Integer num, List list) {
        setDevicesRoleForCapturePreset(num.intValue(), 1, list);
    }

    public void onSetBtActiveDevice(AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("onSetBtActiveDevice btDevice=");
        sb.append(btDeviceInfo.mDevice);
        sb.append(" profile=");
        sb.append(BluetoothProfile.getProfileName(btDeviceInfo.mProfile));
        sb.append(" state=");
        int i2 = btDeviceInfo.mState;
        sb.append(i2 == 99 ? "STATE_CONNECTED_IMPLICIT" : BluetoothProfile.getConnectionStateName(i2));
        Log.d("AS.AudioDeviceInventory", sb.toString());
        String address = btDeviceInfo.mDevice.getAddress();
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            address = "";
        }
        AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("BT connected: addr=" + address + " profile=" + btDeviceInfo.mProfile + " state=" + btDeviceInfo.mState + " codec=" + AudioSystem.audioFormatToString(btDeviceInfo.mCodec)));
        new MediaMetrics.Item("audio.device.onSetBtActiveDevice").set(MediaMetrics.Property.STATUS, Integer.valueOf(btDeviceInfo.mProfile)).set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(btDeviceInfo.mAudioSystemDevice)).set(MediaMetrics.Property.ADDRESS, address).set(MediaMetrics.Property.ENCODING, AudioSystem.audioFormatToString(btDeviceInfo.mCodec)).set(MediaMetrics.Property.EVENT, "onSetBtActiveDevice").set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i)).set(MediaMetrics.Property.STATE, isBtStateConnected(btDeviceInfo) ? "connected" : "disconnected").record();
        synchronized (this.mDevicesLock) {
            DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(btDeviceInfo.mAudioSystemDevice, address));
            boolean z = false;
            boolean z2 = deviceInfo != null;
            boolean z3 = z2 && !isBtStateConnected(btDeviceInfo);
            if (!z2 && isBtStateConnected(btDeviceInfo)) {
                z = true;
            }
            int i3 = btDeviceInfo.mProfile;
            if (i3 == 2) {
                this.mDeviceBroker.connectA2dpDevice(btDeviceInfo.mDevice, btDeviceInfo.mState, btDeviceInfo.mVolume);
                if (z3) {
                    makeA2dpDeviceUnavailableNow(address, deviceInfo.mDeviceCodecFormat);
                    if (!checkDeviceConnected(128)) {
                        setCurrentAudioRouteNameIfPossible(null, true);
                    }
                    if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                        AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda36
                            @Override // java.lang.Runnable
                            public final void run() {
                                AudioDeviceInventory.this.lambda$onSetBtActiveDevice$12();
                            }
                        });
                    }
                } else if (z) {
                    int i4 = btDeviceInfo.mVolume;
                    if (i4 != -1) {
                        this.mDeviceBroker.postSetVolumeIndexOnDevice(3, i4 * 10, btDeviceInfo.mAudioSystemDevice, "onSetBtActiveDevice");
                    } else {
                        int a2dpDeviceVolume = this.mDeviceBroker.getA2dpDeviceVolume(btDeviceInfo.mDevice);
                        if (a2dpDeviceVolume != -1) {
                            this.mDeviceBroker.postSetVolumeIndexOnDevice(3, a2dpDeviceVolume, 128, "onSetBtActiveDevice");
                        }
                    }
                    if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                        BtUtils.setBtVolumeMonitor(btDeviceInfo.mDevice);
                    }
                    makeA2dpDeviceAvailable(btDeviceInfo, "onSetBtActiveDevice");
                }
            } else if (i3 != 11) {
                if (i3 != 26) {
                    if (i3 != 21) {
                        if (i3 != 22) {
                            throw new IllegalArgumentException("Invalid profile " + BluetoothProfile.getProfileName(btDeviceInfo.mProfile));
                        }
                    } else if (z3) {
                        lambda$disconnectHearingAid$33(address);
                    } else if (z) {
                        makeHearingAidDeviceAvailable(address, BtHelper.getName(btDeviceInfo.mDevice), i, "onSetBtActiveDevice");
                    }
                }
                if (z3) {
                    makeLeAudioDeviceUnavailableNow(address, btDeviceInfo.mAudioSystemDevice);
                    if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR && isBleOnlyOutDevice(btDeviceInfo.mIsLeOutput)) {
                        AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda37
                            @Override // java.lang.Runnable
                            public final void run() {
                                AudioDeviceInventory.this.lambda$onSetBtActiveDevice$13();
                            }
                        });
                    }
                } else if (z) {
                    if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR && isBleOnlyOutDevice(btDeviceInfo.mIsLeOutput)) {
                        BtUtils.setBtVolumeMonitor(btDeviceInfo.mDevice);
                    }
                    makeLeAudioDeviceAvailable(btDeviceInfo, i, "onSetBtActiveDevice");
                }
            } else if (z3) {
                lambda$disconnectA2dpSink$31(address);
            } else if (z) {
                makeA2dpSrcAvailable(address);
            }
        }
    }

    public void onBluetoothDeviceConfigChange(AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i) {
        boolean z;
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.onBluetoothDeviceConfigChange").set(MediaMetrics.Property.EVENT, BtHelper.deviceEventToString(i));
        BluetoothDevice bluetoothDevice = btDeviceInfo.mDevice;
        if (bluetoothDevice == null) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "btDevice null").record();
            return;
        }
        Log.d("AS.AudioDeviceInventory", "onBluetoothDeviceConfigChange btDevice=" + bluetoothDevice);
        int i2 = btDeviceInfo.mVolume;
        int i3 = btDeviceInfo.mCodec;
        String address = bluetoothDevice.getAddress();
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            address = "";
        }
        EventLogger eventLogger = AudioService.sDeviceLogger;
        eventLogger.enqueue(new EventLogger.StringEvent("onBluetoothDeviceConfigChange addr=" + address + " event=" + BtHelper.deviceEventToString(i)));
        synchronized (this.mDevicesLock) {
            String makeDeviceListKey = DeviceInfo.makeDeviceListKey(128, address);
            DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(makeDeviceListKey);
            if (deviceInfo == null) {
                if (this.mDeviceBroker.hasScheduledA2dpConnection(bluetoothDevice)) {
                    Log.i("AS.AudioDeviceInventory", "DeviceInfo is null. send MSG for handleDeviceConfigChange");
                    this.mDeviceBroker.postBluetoothDeviceConfigChange(btDeviceInfo);
                } else {
                    Log.e("AS.AudioDeviceInventory", "invalid null DeviceInfo in onBluetoothDeviceConfigChange");
                    item.set(MediaMetrics.Property.EARLY_RETURN, "null DeviceInfo").record();
                }
                return;
            }
            item.set(MediaMetrics.Property.ADDRESS, address).set(MediaMetrics.Property.ENCODING, AudioSystem.audioFormatToString(i3)).set(MediaMetrics.Property.INDEX, Integer.valueOf(i2)).set(MediaMetrics.Property.NAME, deviceInfo.mDeviceName);
            if (i == 0) {
                boolean z2 = false;
                if (btDeviceInfo.mProfile == 2) {
                    if (deviceInfo.mDeviceCodecFormat != i3) {
                        deviceInfo.mDeviceCodecFormat = i3;
                        this.mConnectedDevices.replace(makeDeviceListKey, deviceInfo);
                        z = true;
                    } else {
                        z = false;
                    }
                    if (this.mAudioSystem.handleDeviceConfigChange(btDeviceInfo.mAudioSystemDevice, address, BtHelper.getName(bluetoothDevice), i3) != 0) {
                        eventLogger.enqueue(new EventLogger.StringEvent("APM handleDeviceConfigChange failed for A2DP device addr=" + address + " codec=" + AudioSystem.audioFormatToString(i3)).printLog("AS.AudioDeviceInventory"));
                        setBluetoothActiveDevice(new AudioDeviceBroker.BtDeviceInfo(btDeviceInfo, 0));
                    } else {
                        eventLogger.enqueue(new EventLogger.StringEvent("APM handleDeviceConfigChange success for A2DP device addr=" + address + " codec=" + AudioSystem.audioFormatToString(i3)).printLog("AS.AudioDeviceInventory"));
                    }
                    z2 = z;
                }
                if (!z2) {
                    updateBluetoothPreferredModes_l(bluetoothDevice);
                }
            }
            item.record();
        }
    }

    public void onMakeA2dpDeviceUnavailableNow(String str, int i) {
        synchronized (this.mDevicesLock) {
            makeA2dpDeviceUnavailableNow(str, i);
        }
    }

    public void onMakeLeAudioDeviceUnavailableNow(String str, int i) {
        synchronized (this.mDevicesLock) {
            makeLeAudioDeviceUnavailableNow(str, i);
        }
    }

    public void onReportNewRoutes() {
        AudioRoutesInfo audioRoutesInfo;
        int beginBroadcast = this.mRoutesObservers.beginBroadcast();
        if (beginBroadcast > 0) {
            new MediaMetrics.Item("audio.device.onReportNewRoutes").set(MediaMetrics.Property.OBSERVERS, Integer.valueOf(beginBroadcast)).record();
            synchronized (this.mCurAudioRoutes) {
                audioRoutesInfo = new AudioRoutesInfo(this.mCurAudioRoutes);
                if (("BT".equals(this.mForcePath) && this.mCurAudioRoutes.bluetoothName != null) || "OTHERS".equals(this.mForcePath)) {
                    audioRoutesInfo.setForcePath(this.mForcePath);
                    this.mForcePath = "";
                }
                Log.i("AS.AudioDeviceInventory", "dispatchAudioRoutesChanged mainType = " + this.mCurAudioRoutes.mainType + " setForcePath = " + audioRoutesInfo.getSetForcePath());
            }
            while (beginBroadcast > 0) {
                beginBroadcast--;
                try {
                    this.mRoutesObservers.getBroadcastItem(beginBroadcast).dispatchAudioRoutesChanged(audioRoutesInfo);
                } catch (RemoteException unused) {
                }
            }
        }
        this.mRoutesObservers.finishBroadcast();
        this.mDeviceBroker.postObserveDevicesForAllStreams();
    }

    static {
        HashSet hashSet = new HashSet();
        DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET = hashSet;
        hashSet.add(4);
        hashSet.add(8);
        Integer valueOf = Integer.valueOf(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES);
        hashSet.add(valueOf);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_USB_SET);
        CAPTURE_PRESETS = new int[]{1, 5, 6, 7, 9, 10, 1999};
        HashSet hashSet2 = new HashSet();
        BECOMING_NOISY_INTENT_DEVICES_SET = hashSet2;
        hashSet2.add(4);
        hashSet2.add(8);
        hashSet2.add(1024);
        hashSet2.add(Integer.valueOf(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES));
        hashSet2.add(valueOf);
        hashSet2.add(134217728);
        hashSet2.add(536870912);
        hashSet2.add(536870914);
        hashSet2.addAll(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
        hashSet2.addAll(AudioSystem.DEVICE_OUT_ALL_USB_SET);
        hashSet2.addAll(AudioSystem.DEVICE_OUT_ALL_BLE_SET);
    }

    public void onSetWiredDeviceConnectionState(final WiredDeviceConnectionState wiredDeviceConnectionState) {
        AudioDeviceInfo audioDeviceInfo;
        int internalType = wiredDeviceConnectionState.mAttributes.getInternalType();
        AudioService.sDeviceLogger.enqueue(new EventLogger.Event(wiredDeviceConnectionState) { // from class: com.android.server.audio.AudioServiceEvents$WiredDevConnectEvent
            public final AudioDeviceInventory.WiredDeviceConnectionState mState;

            {
                this.mState = wiredDeviceConnectionState;
            }

            @Override // com.android.server.utils.EventLogger.Event
            public String eventToString() {
                return "setWiredDeviceConnectionState( type:" + Integer.toHexString(this.mState.mAttributes.getInternalType()) + " state:" + AudioSystem.deviceStateToString(this.mState.mState) + " addr:" + this.mState.mAttributes.getAddress() + " name:" + this.mState.mAttributes.getName() + ") from " + this.mState.mCaller;
            }
        });
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.onSetWiredDeviceConnectionState").set(MediaMetrics.Property.ADDRESS, wiredDeviceConnectionState.mAttributes.getAddress()).set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(internalType)).set(MediaMetrics.Property.STATE, wiredDeviceConnectionState.mState == 0 ? "disconnected" : "connected");
        if (wiredDeviceConnectionState.mState == 0 && AudioSystem.DEVICE_OUT_ALL_USB_SET.contains(Integer.valueOf(wiredDeviceConnectionState.mAttributes.getInternalType()))) {
            AudioDeviceInfo[] devicesStatic = AudioManager.getDevicesStatic(2);
            int length = devicesStatic.length;
            for (int i = 0; i < length; i++) {
                audioDeviceInfo = devicesStatic[i];
                if (audioDeviceInfo.getInternalType() == wiredDeviceConnectionState.mAttributes.getInternalType()) {
                    break;
                }
            }
        }
        audioDeviceInfo = null;
        synchronized (this.mDevicesLock) {
            boolean z = true;
            if (wiredDeviceConnectionState.mState == 0 && DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.contains(Integer.valueOf(internalType))) {
                this.mDeviceBroker.setBluetoothA2dpOnInt(true, false, "onSetWiredDeviceConnectionState state DISCONNECTED");
            }
            AudioDeviceAttributes audioDeviceAttributes = wiredDeviceConnectionState.mAttributes;
            if (wiredDeviceConnectionState.mState != 1) {
                z = false;
            }
            if (!handleDeviceConnection(audioDeviceAttributes, z, wiredDeviceConnectionState.mForTest, null)) {
                item.set(MediaMetrics.Property.EARLY_RETURN, "change of connection state failed").record();
                return;
            }
            if (wiredDeviceConnectionState.mState != 0) {
                if ((internalType & 12) != 0) {
                    FactoryUtils.increaseEarJackCounter();
                }
                if (DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.contains(Integer.valueOf(internalType))) {
                    this.mDeviceBroker.setBluetoothA2dpOnInt(false, false, "onSetWiredDeviceConnectionState state not DISCONNECTED");
                }
                this.mDeviceBroker.checkMusicActive(internalType, wiredDeviceConnectionState.mCaller);
            }
            if (internalType == 1024) {
                this.mDeviceBroker.checkVolumeCecOnHdmiConnection(wiredDeviceConnectionState.mState, wiredDeviceConnectionState.mCaller);
            }
            AudioUtils.wakeUpDeviceByWiredHeadset(this.mDeviceBroker.getContext(), internalType);
            if (wiredDeviceConnectionState.mState == 0 && AudioSystem.DEVICE_OUT_ALL_USB_SET.contains(Integer.valueOf(wiredDeviceConnectionState.mAttributes.getInternalType()))) {
                if (audioDeviceInfo != null) {
                    this.mDeviceBroker.dispatchPreferredMixerAttributesChangedCausedByDeviceRemoved(audioDeviceInfo);
                } else {
                    Log.e("AS.AudioDeviceInventory", "Didn't find AudioDeviceInfo to notify preferred mixer attributes change for type=" + wiredDeviceConnectionState.mAttributes.getType());
                }
            }
            sendDeviceConnectionIntent(internalType, wiredDeviceConnectionState.mState, wiredDeviceConnectionState.mAttributes.getAddress(), wiredDeviceConnectionState.mAttributes.getName());
            updateAudioRoutes(internalType, wiredDeviceConnectionState.mState);
            item.record();
        }
    }

    public void onToggleHdmi() {
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.onToggleHdmi").set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(1024));
        synchronized (this.mDevicesLock) {
            if (((DeviceInfo) this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(1024, ""))) == null) {
                Log.e("AS.AudioDeviceInventory", "invalid null DeviceInfo in onToggleHdmi");
                item.set(MediaMetrics.Property.EARLY_RETURN, "invalid null DeviceInfo").record();
            } else {
                setWiredDeviceConnectionState(new AudioDeviceAttributes(1024, ""), 0, "android");
                setWiredDeviceConnectionState(new AudioDeviceAttributes(1024, ""), 1, "android");
                item.record();
            }
        }
    }

    public void onSaveSetPreferredDevices(int i, List list) {
        this.mPreferredDevices.put(Integer.valueOf(i), list);
        List list2 = (List) this.mNonDefaultDevices.get(Integer.valueOf(i));
        if (list2 != null) {
            list2.removeAll(list);
            if (list2.isEmpty()) {
                this.mNonDefaultDevices.remove(Integer.valueOf(i));
            } else {
                this.mNonDefaultDevices.put(Integer.valueOf(i), list2);
            }
            dispatchNonDefaultDevice(i, list2);
        }
        dispatchPreferredDevice(i, list);
    }

    public void onSaveRemovePreferredDevices(int i) {
        this.mPreferredDevices.remove(Integer.valueOf(i));
        dispatchPreferredDevice(i, new ArrayList());
    }

    public void onSaveSetDeviceAsNonDefault(int i, AudioDeviceAttributes audioDeviceAttributes) {
        List list = (List) this.mNonDefaultDevices.get(Integer.valueOf(i));
        if (list == null) {
            list = new ArrayList();
        }
        if (!list.contains(audioDeviceAttributes)) {
            list.add(audioDeviceAttributes);
        }
        this.mNonDefaultDevices.put(Integer.valueOf(i), list);
        dispatchNonDefaultDevice(i, list);
        List list2 = (List) this.mPreferredDevices.get(Integer.valueOf(i));
        if (list2 != null) {
            list2.remove(audioDeviceAttributes);
            this.mPreferredDevices.put(Integer.valueOf(i), list2);
            dispatchPreferredDevice(i, list2);
        }
    }

    public void onSaveRemoveDeviceAsNonDefault(int i, AudioDeviceAttributes audioDeviceAttributes) {
        List list = (List) this.mNonDefaultDevices.get(Integer.valueOf(i));
        if (list != null) {
            list.remove(audioDeviceAttributes);
            this.mNonDefaultDevices.put(Integer.valueOf(i), list);
            dispatchNonDefaultDevice(i, list);
        }
    }

    public void onSaveSetPreferredDevicesForCapturePreset(int i, List list) {
        this.mPreferredDevicesForCapturePreset.put(Integer.valueOf(i), list);
        dispatchDevicesRoleForCapturePreset(i, 1, list);
    }

    public void onSaveClearPreferredDevicesForCapturePreset(int i) {
        this.mPreferredDevicesForCapturePreset.remove(Integer.valueOf(i));
        dispatchDevicesRoleForCapturePreset(i, 1, new ArrayList());
    }

    public int setPreferredDevicesForStrategyAndSave(int i, List list) {
        int preferredDevicesForStrategy = setPreferredDevicesForStrategy(i, list);
        if (preferredDevicesForStrategy == 0) {
            this.mDeviceBroker.postSaveSetPreferredDevicesForStrategy(i, list);
        }
        return preferredDevicesForStrategy;
    }

    public int setPreferredDevicesForStrategy(int i, List list) {
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("setPreferredDevicesForStrategy, strategy: " + i + " devices: " + list).printLog("AS.AudioDeviceInventory"));
            int devicesRoleForStrategy = setDevicesRoleForStrategy(i, 1, list, false);
            if (create != null) {
                create.close();
            }
            return devicesRoleForStrategy;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int setPreferredDevicesForStrategyInt(int i, List list) {
        return setDevicesRoleForStrategy(i, 1, list, true);
    }

    public int removePreferredDevicesForStrategyAndSave(int i) {
        int removePreferredDevicesForStrategy = removePreferredDevicesForStrategy(i);
        if (removePreferredDevicesForStrategy == 0) {
            this.mDeviceBroker.postSaveRemovePreferredDevicesForStrategy(i);
        }
        return removePreferredDevicesForStrategy;
    }

    public int removePreferredDevicesForStrategy(int i) {
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("removePreferredDevicesForStrategy, strategy: " + i).printLog("AS.AudioDeviceInventory"));
            int clearDevicesRoleForStrategy = clearDevicesRoleForStrategy(i, 1, false);
            if (create != null) {
                create.close();
            }
            return clearDevicesRoleForStrategy;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int removePreferredDevicesForStrategyInt(int i) {
        return clearDevicesRoleForStrategy(i, 1, true);
    }

    public int setDeviceAsNonDefaultForStrategyAndSave(int i, AudioDeviceAttributes audioDeviceAttributes) {
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(audioDeviceAttributes);
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("setDeviceAsNonDefaultForStrategyAndSave, strategy: " + i + " device: " + audioDeviceAttributes).printLog("AS.AudioDeviceInventory"));
            int addDevicesRoleForStrategy = addDevicesRoleForStrategy(i, 2, arrayList, false);
            if (create != null) {
                create.close();
            }
            if (addDevicesRoleForStrategy == 0) {
                this.mDeviceBroker.postSaveSetDeviceAsNonDefaultForStrategy(i, audioDeviceAttributes);
            }
            return addDevicesRoleForStrategy;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int removeDeviceAsNonDefaultForStrategyAndSave(int i, AudioDeviceAttributes audioDeviceAttributes) {
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(audioDeviceAttributes);
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("removeDeviceAsNonDefaultForStrategyAndSave, strategy: " + i + " devices: " + audioDeviceAttributes).printLog("AS.AudioDeviceInventory"));
            int removeDevicesRoleForStrategy = removeDevicesRoleForStrategy(i, 2, arrayList, false);
            if (create != null) {
                create.close();
            }
            if (removeDevicesRoleForStrategy == 0) {
                this.mDeviceBroker.postSaveRemoveDeviceAsNonDefaultForStrategy(i, audioDeviceAttributes);
            }
            return removeDevicesRoleForStrategy;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        this.mPrefDevDispatchers.register(iStrategyPreferredDevicesDispatcher);
    }

    public void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        this.mPrefDevDispatchers.unregister(iStrategyPreferredDevicesDispatcher);
    }

    public void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        this.mNonDefDevDispatchers.register(iStrategyNonDefaultDevicesDispatcher);
    }

    public void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        this.mNonDefDevDispatchers.unregister(iStrategyNonDefaultDevicesDispatcher);
    }

    public int setPreferredDevicesForCapturePresetAndSave(int i, List list) {
        int preferredDevicesForCapturePreset = setPreferredDevicesForCapturePreset(i, list);
        if (preferredDevicesForCapturePreset == 0) {
            this.mDeviceBroker.postSaveSetPreferredDevicesForCapturePreset(i, list);
        }
        return preferredDevicesForCapturePreset;
    }

    public final int setPreferredDevicesForCapturePreset(int i, List list) {
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            int devicesRoleForCapturePreset = setDevicesRoleForCapturePreset(i, 1, list);
            if (create != null) {
                create.close();
            }
            return devicesRoleForCapturePreset;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int clearPreferredDevicesForCapturePresetAndSave(int i) {
        int clearPreferredDevicesForCapturePreset = clearPreferredDevicesForCapturePreset(i);
        if (clearPreferredDevicesForCapturePreset == 0) {
            this.mDeviceBroker.postSaveClearPreferredDevicesForCapturePreset(i);
        }
        return clearPreferredDevicesForCapturePreset;
    }

    public final int clearPreferredDevicesForCapturePreset(int i) {
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            int clearDevicesRoleForCapturePreset = clearDevicesRoleForCapturePreset(i, 1);
            if (create != null) {
                create.close();
            }
            return clearDevicesRoleForCapturePreset;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final int addDevicesRoleForCapturePresetInt(int i, int i2, List list) {
        return addDevicesRole(this.mAppliedPresetRolesInt, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda19
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$addDevicesRoleForCapturePresetInt$14;
                lambda$addDevicesRoleForCapturePresetInt$14 = AudioDeviceInventory.this.lambda$addDevicesRoleForCapturePresetInt$14(i3, i4, list2);
                return lambda$addDevicesRoleForCapturePresetInt$14;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$addDevicesRoleForCapturePresetInt$14(int i, int i2, List list) {
        return this.mAudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
    }

    public final int removeDevicesRoleForCapturePresetInt(int i, int i2, List list) {
        return removeDevicesRole(this.mAppliedPresetRolesInt, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda22
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$removeDevicesRoleForCapturePresetInt$15;
                lambda$removeDevicesRoleForCapturePresetInt$15 = AudioDeviceInventory.this.lambda$removeDevicesRoleForCapturePresetInt$15(i3, i4, list2);
                return lambda$removeDevicesRoleForCapturePresetInt$15;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$removeDevicesRoleForCapturePresetInt$15(int i, int i2, List list) {
        return this.mAudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
    }

    public final int setDevicesRoleForCapturePreset(int i, int i2, List list) {
        return setDevicesRole(this.mAppliedPresetRoles, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda8
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$setDevicesRoleForCapturePreset$16;
                lambda$setDevicesRoleForCapturePreset$16 = AudioDeviceInventory.this.lambda$setDevicesRoleForCapturePreset$16(i3, i4, list2);
                return lambda$setDevicesRoleForCapturePreset$16;
            }
        }, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda9
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$setDevicesRoleForCapturePreset$17;
                lambda$setDevicesRoleForCapturePreset$17 = AudioDeviceInventory.this.lambda$setDevicesRoleForCapturePreset$17(i3, i4, list2);
                return lambda$setDevicesRoleForCapturePreset$17;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForCapturePreset$16(int i, int i2, List list) {
        return this.mAudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForCapturePreset$17(int i, int i2, List list) {
        return this.mAudioSystem.clearDevicesRoleForCapturePreset(i, i2);
    }

    public final int clearDevicesRoleForCapturePreset(int i, int i2) {
        return clearDevicesRole(this.mAppliedPresetRoles, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda6
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list) {
                int lambda$clearDevicesRoleForCapturePreset$18;
                lambda$clearDevicesRoleForCapturePreset$18 = AudioDeviceInventory.this.lambda$clearDevicesRoleForCapturePreset$18(i3, i4, list);
                return lambda$clearDevicesRoleForCapturePreset$18;
            }
        }, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$clearDevicesRoleForCapturePreset$18(int i, int i2, List list) {
        return this.mAudioSystem.clearDevicesRoleForCapturePreset(i, i2);
    }

    public void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        this.mDevRoleCapturePresetDispatchers.register(iCapturePresetDevicesRoleDispatcher);
    }

    public void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        this.mDevRoleCapturePresetDispatchers.unregister(iCapturePresetDevicesRoleDispatcher);
    }

    public final int addDevicesRoleForStrategy(int i, int i2, List list, boolean z) {
        return addDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda0
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$addDevicesRoleForStrategy$19;
                lambda$addDevicesRoleForStrategy$19 = AudioDeviceInventory.this.lambda$addDevicesRoleForStrategy$19(i3, i4, list2);
                return lambda$addDevicesRoleForStrategy$19;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$addDevicesRoleForStrategy$19(int i, int i2, List list) {
        return this.mAudioSystem.setDevicesRoleForStrategy(i, i2, list);
    }

    public final int removeDevicesRoleForStrategy(int i, int i2, List list, boolean z) {
        return removeDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda5
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$removeDevicesRoleForStrategy$20;
                lambda$removeDevicesRoleForStrategy$20 = AudioDeviceInventory.this.lambda$removeDevicesRoleForStrategy$20(i3, i4, list2);
                return lambda$removeDevicesRoleForStrategy$20;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$removeDevicesRoleForStrategy$20(int i, int i2, List list) {
        return this.mAudioSystem.removeDevicesRoleForStrategy(i, i2, list);
    }

    public final int setDevicesRoleForStrategy(int i, int i2, List list, boolean z) {
        return setDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda3
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$setDevicesRoleForStrategy$21;
                lambda$setDevicesRoleForStrategy$21 = AudioDeviceInventory.this.lambda$setDevicesRoleForStrategy$21(i3, i4, list2);
                return lambda$setDevicesRoleForStrategy$21;
            }
        }, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda4
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list2) {
                int lambda$setDevicesRoleForStrategy$22;
                lambda$setDevicesRoleForStrategy$22 = AudioDeviceInventory.this.lambda$setDevicesRoleForStrategy$22(i3, i4, list2);
                return lambda$setDevicesRoleForStrategy$22;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForStrategy$21(int i, int i2, List list) {
        return this.mAudioSystem.setDevicesRoleForStrategy(i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForStrategy$22(int i, int i2, List list) {
        return this.mAudioSystem.clearDevicesRoleForStrategy(i, i2);
    }

    public final int clearDevicesRoleForStrategy(int i, int i2, boolean z) {
        return clearDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda2
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, List list) {
                int lambda$clearDevicesRoleForStrategy$23;
                lambda$clearDevicesRoleForStrategy$23 = AudioDeviceInventory.this.lambda$clearDevicesRoleForStrategy$23(i3, i4, list);
                return lambda$clearDevicesRoleForStrategy$23;
            }
        }, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$clearDevicesRoleForStrategy$23(int i, int i2, List list) {
        return this.mAudioSystem.clearDevicesRoleForStrategy(i, i2);
    }

    public final int addDevicesRole(ArrayMap arrayMap, AudioSystemInterface audioSystemInterface, int i, int i2, List list) {
        synchronized (arrayMap) {
            Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
            List arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (arrayMap.containsKey(pair)) {
                arrayList = (List) arrayMap.get(pair);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) it.next();
                    if (!arrayList.contains(audioDeviceAttributes)) {
                        arrayList2.add(audioDeviceAttributes);
                    }
                }
            } else {
                arrayList2.addAll(list);
            }
            if (arrayList2.isEmpty()) {
                return 0;
            }
            int deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, arrayList2);
            if (deviceRoleAction == 0) {
                arrayList.addAll(arrayList2);
                arrayMap.put(pair, arrayList);
            }
            return deviceRoleAction;
        }
    }

    public final int removeDevicesRole(ArrayMap arrayMap, AudioSystemInterface audioSystemInterface, int i, int i2, List list) {
        synchronized (arrayMap) {
            Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
            if (!arrayMap.containsKey(pair)) {
                return -2;
            }
            List list2 = (List) arrayMap.get(pair);
            ArrayList arrayList = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) it.next();
                if (list2.contains(audioDeviceAttributes)) {
                    arrayList.add(audioDeviceAttributes);
                }
            }
            if (arrayList.isEmpty()) {
                return 0;
            }
            int deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, arrayList);
            if (deviceRoleAction == 0) {
                list2.removeAll(arrayList);
                if (list2.isEmpty()) {
                    arrayMap.remove(pair);
                } else {
                    arrayMap.put(pair, list2);
                }
            }
            return deviceRoleAction;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0041 A[Catch: all -> 0x0065, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x001f, B:7:0x002f, B:11:0x0041, B:12:0x004f, B:14:0x0055, B:17:0x0057, B:19:0x005d, B:20:0x0063, B:23:0x004c), top: B:2:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int setDevicesRole(ArrayMap arrayMap, AudioSystemInterface audioSystemInterface, AudioSystemInterface audioSystemInterface2, int i, int i2, List list) {
        boolean z;
        synchronized (arrayMap) {
            Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
            List arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (arrayMap.containsKey(pair)) {
                arrayList = (List) arrayMap.get(pair);
                if (arrayList.size() == list.size()) {
                    arrayList.retainAll(list);
                    if (arrayList.size() == list.size()) {
                        z = true;
                        if (!z) {
                            audioSystemInterface2.deviceRoleAction(i, i2, null);
                            arrayList.clear();
                            arrayList2.addAll(list);
                        }
                    }
                }
                z = false;
                if (!z) {
                }
            } else {
                arrayList2.addAll(list);
            }
            if (arrayList2.isEmpty()) {
                return 0;
            }
            int deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, arrayList2);
            if (deviceRoleAction == 0) {
                arrayList.addAll(arrayList2);
                arrayMap.put(pair, arrayList);
            }
            return deviceRoleAction;
        }
    }

    public final int clearDevicesRole(ArrayMap arrayMap, AudioSystemInterface audioSystemInterface, int i, int i2) {
        synchronized (arrayMap) {
            Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
            if (!arrayMap.containsKey(pair)) {
                return -2;
            }
            int deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, null);
            if (deviceRoleAction == 0) {
                arrayMap.remove(pair);
            }
            return deviceRoleAction;
        }
    }

    public final void purgeDevicesRoles_l() {
        purgeRoles(this.mAppliedStrategyRolesInt, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda20
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i, int i2, List list) {
                int lambda$purgeDevicesRoles_l$24;
                lambda$purgeDevicesRoles_l$24 = AudioDeviceInventory.this.lambda$purgeDevicesRoles_l$24(i, i2, list);
                return lambda$purgeDevicesRoles_l$24;
            }
        });
        purgeRoles(this.mAppliedPresetRolesInt, new AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda21
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i, int i2, List list) {
                int lambda$purgeDevicesRoles_l$25;
                lambda$purgeDevicesRoles_l$25 = AudioDeviceInventory.this.lambda$purgeDevicesRoles_l$25(i, i2, list);
                return lambda$purgeDevicesRoles_l$25;
            }
        });
        reapplyExternalDevicesRoles();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$purgeDevicesRoles_l$24(int i, int i2, List list) {
        return this.mAudioSystem.removeDevicesRoleForStrategy(i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$purgeDevicesRoles_l$25(int i, int i2, List list) {
        return this.mAudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
    }

    public final void purgeRoles(ArrayMap arrayMap, AudioSystemInterface audioSystemInterface) {
        synchronized (arrayMap) {
            AudioDeviceInfo[] devicesStatic = AudioManager.getDevicesStatic(3);
            Iterator it = arrayMap.entrySet().iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) ((Map.Entry) it.next()).getKey();
                Iterator it2 = ((List) arrayMap.get(pair)).iterator();
                while (it2.hasNext()) {
                    final AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) it2.next();
                    if (((AudioDeviceInfo) Stream.of((Object[]) devicesStatic).filter(new Predicate() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda34
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$purgeRoles$26;
                            lambda$purgeRoles$26 = AudioDeviceInventory.lambda$purgeRoles$26(audioDeviceAttributes, (AudioDeviceInfo) obj);
                            return lambda$purgeRoles$26;
                        }
                    }).filter(new Predicate() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda35
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            boolean lambda$purgeRoles$27;
                            lambda$purgeRoles$27 = AudioDeviceInventory.lambda$purgeRoles$27(audioDeviceAttributes, (AudioDeviceInfo) obj);
                            return lambda$purgeRoles$27;
                        }
                    }).findFirst().orElse(null)) == null) {
                        Slog.i("AS.AudioDeviceInventory", "purgeRoles() removing device: " + audioDeviceAttributes.toString() + ", for strategy: " + pair.first + " and role: " + pair.second);
                        audioSystemInterface.deviceRoleAction(((Integer) pair.first).intValue(), ((Integer) pair.second).intValue(), Arrays.asList(audioDeviceAttributes));
                        it2.remove();
                    }
                }
                if (((List) arrayMap.get(pair)).isEmpty()) {
                    it.remove();
                }
            }
        }
    }

    public static /* synthetic */ boolean lambda$purgeRoles$26(AudioDeviceAttributes audioDeviceAttributes, AudioDeviceInfo audioDeviceInfo) {
        return audioDeviceInfo.getInternalType() == audioDeviceAttributes.getInternalType();
    }

    public static /* synthetic */ boolean lambda$purgeRoles$27(AudioDeviceAttributes audioDeviceAttributes, AudioDeviceInfo audioDeviceInfo) {
        return !AudioSystem.isBluetoothDevice(audioDeviceInfo.getInternalType()) || audioDeviceInfo.getAddress().equals(audioDeviceAttributes.getAddress());
    }

    public boolean isDeviceConnected(AudioDeviceAttributes audioDeviceAttributes) {
        boolean z;
        String makeDeviceListKey = DeviceInfo.makeDeviceListKey(audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
        synchronized (this.mDevicesLock) {
            z = this.mConnectedDevices.get(makeDeviceListKey) != null;
        }
        return z;
    }

    public boolean handleDeviceConnection(AudioDeviceAttributes audioDeviceAttributes, boolean z, boolean z2, BluetoothDevice bluetoothDevice) {
        int deviceConnectionState;
        int internalType = audioDeviceAttributes.getInternalType();
        String address = audioDeviceAttributes.getAddress();
        String name = audioDeviceAttributes.getName();
        Slog.i("AS.AudioDeviceInventory", "handleDeviceConnection(" + z + " dev:" + Integer.toHexString(internalType) + " address:" + address + " name:" + name + ")");
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.handleDeviceConnection").set(MediaMetrics.Property.ADDRESS, address).set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(internalType)).set(MediaMetrics.Property.MODE, z ? "connect" : "disconnect").set(MediaMetrics.Property.NAME, name);
        synchronized (this.mDevicesLock) {
            String makeDeviceListKey = DeviceInfo.makeDeviceListKey(internalType, address);
            Slog.i("AS.AudioDeviceInventory", "deviceKey:" + makeDeviceListKey);
            DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(makeDeviceListKey);
            boolean z3 = true;
            boolean z4 = deviceInfo != null;
            Slog.i("AS.AudioDeviceInventory", "deviceInfo:" + deviceInfo + " is(already)Connected:" + z4);
            AudioFxHelper.setSoundFxVolume(-1.0f);
            if (z && !z4) {
                if (z2) {
                    deviceConnectionState = 0;
                } else {
                    deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 1, 0);
                    if (deviceConnectionState != 0 && AudioUtils.isWiredDeviceType(internalType)) {
                        Slog.w("AS.AudioDeviceInventory", "retry: not connecting device 0x" + Integer.toHexString(internalType) + " due to command error " + deviceConnectionState);
                        this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
                        deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 1, 0);
                    }
                }
                if (deviceConnectionState != 0) {
                    String str = "not connecting device 0x" + Integer.toHexString(internalType) + " due to command error " + deviceConnectionState;
                    Slog.e("AS.AudioDeviceInventory", str);
                    item.set(MediaMetrics.Property.EARLY_RETURN, str).set(MediaMetrics.Property.STATE, "disconnected").record();
                    return false;
                }
                this.mConnectedDevices.put(makeDeviceListKey, new DeviceInfo(internalType, name, address));
                this.mDeviceBroker.postAccessoryPlugMediaUnmute(internalType);
                if (internalType == 1024) {
                    updateDexState();
                }
            } else if (z || !z4) {
                z3 = false;
            } else {
                this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
                this.mConnectedDevices.remove(makeDeviceListKey);
                this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
            }
            if (z3) {
                if (AudioSystem.isBluetoothScoDevice(internalType)) {
                    updateBluetoothPreferredModes_l(z ? bluetoothDevice : null);
                    if (!z) {
                        purgeDevicesRoles_l();
                    }
                }
                item.set(MediaMetrics.Property.STATE, "connected").record();
            } else {
                Log.w("AS.AudioDeviceInventory", "handleDeviceConnection() failed, deviceKey=" + makeDeviceListKey + ", deviceSpec=" + deviceInfo + ", connect=" + z);
                item.set(MediaMetrics.Property.STATE, "disconnected").record();
            }
            return z3;
        }
    }

    public final void disconnectA2dp() {
        synchronized (this.mDevicesLock) {
            final ArraySet arraySet = new ArraySet();
            this.mConnectedDevices.values().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda30
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioDeviceInventory.lambda$disconnectA2dp$28(arraySet, (AudioDeviceInventory.DeviceInfo) obj);
                }
            });
            new MediaMetrics.Item("audio.device.disconnectA2dp").record();
            if (arraySet.size() > 0) {
                final int checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(128, 0, 0);
                arraySet.stream().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda31
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AudioDeviceInventory.this.lambda$disconnectA2dp$29(checkSendBecomingNoisyIntentInt, (String) obj);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$disconnectA2dp$28(ArraySet arraySet, DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == 128) {
            arraySet.add(deviceInfo.mDeviceAddress);
        }
    }

    public final void disconnectA2dpSink() {
        synchronized (this.mDevicesLock) {
            final ArraySet arraySet = new ArraySet();
            this.mConnectedDevices.values().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda25
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioDeviceInventory.lambda$disconnectA2dpSink$30(arraySet, (AudioDeviceInventory.DeviceInfo) obj);
                }
            });
            new MediaMetrics.Item("audio.device.disconnectA2dpSink").record();
            arraySet.stream().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda26
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioDeviceInventory.this.lambda$disconnectA2dpSink$31((String) obj);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$disconnectA2dpSink$30(ArraySet arraySet, DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == -2147352576) {
            arraySet.add(deviceInfo.mDeviceAddress);
        }
    }

    public final void disconnectHearingAid() {
        synchronized (this.mDevicesLock) {
            final ArraySet arraySet = new ArraySet();
            this.mConnectedDevices.values().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda32
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioDeviceInventory.lambda$disconnectHearingAid$32(arraySet, (AudioDeviceInventory.DeviceInfo) obj);
                }
            });
            new MediaMetrics.Item("audio.device.disconnectHearingAid").record();
            if (arraySet.size() > 0) {
                checkSendBecomingNoisyIntentInt(134217728, 0, 0);
                arraySet.stream().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda33
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AudioDeviceInventory.this.lambda$disconnectHearingAid$33((String) obj);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$disconnectHearingAid$32(ArraySet arraySet, DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == 134217728) {
            arraySet.add(deviceInfo.mDeviceAddress);
        }
    }

    public synchronized void onBtProfileDisconnected(int i) {
        if (i == 2) {
            if (this.mDeviceBroker.isBluetoothCastState()) {
                this.mDeviceBroker.setBluetoothCastState(false);
                updateDeviceQuickConnection(false, 32768, "0", "remote_submix", 0);
            }
            disconnectA2dp();
        } else if (i == 11) {
            disconnectA2dpSink();
        } else if (i == 26) {
            disconnectLeAudioBroadcast();
        } else if (i == 21) {
            disconnectHearingAid();
        } else if (i == 22) {
            disconnectLeAudioUnicast();
        } else {
            Log.e("AS.AudioDeviceInventory", "onBtProfileDisconnected: Not a valid profile to disconnect " + BluetoothProfile.getProfileName(i));
        }
    }

    public void disconnectLeAudio(final int i) {
        if (i != 536870912 && i != 536870914) {
            Log.e("AS.AudioDeviceInventory", "disconnectLeAudio: Can't disconnect not LE Audio device " + i);
            return;
        }
        synchronized (this.mDevicesLock) {
            final ArraySet arraySet = new ArraySet();
            this.mConnectedDevices.values().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda23
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioDeviceInventory.lambda$disconnectLeAudio$34(i, arraySet, (AudioDeviceInventory.DeviceInfo) obj);
                }
            });
            new MediaMetrics.Item("audio.device.disconnectLeAudio").record();
            if (arraySet.size() > 0) {
                final int checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(i, 0, 0);
                arraySet.stream().forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda24
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AudioDeviceInventory.this.lambda$disconnectLeAudio$35(i, checkSendBecomingNoisyIntentInt, (String) obj);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$disconnectLeAudio$34(int i, ArraySet arraySet, DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == i) {
            arraySet.add(deviceInfo.mDeviceAddress);
        }
    }

    public void disconnectLeAudioUnicast() {
        disconnectLeAudio(536870912);
    }

    public void disconnectLeAudioBroadcast() {
        disconnectLeAudio(536870914);
    }

    public int checkSendBecomingNoisyIntent(int i, int i2, int i3) {
        int checkSendBecomingNoisyIntentInt;
        synchronized (this.mDevicesLock) {
            checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(i, i2, i3);
        }
        return checkSendBecomingNoisyIntentInt;
    }

    public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) {
        AudioRoutesInfo audioRoutesInfo;
        synchronized (this.mCurAudioRoutes) {
            audioRoutesInfo = new AudioRoutesInfo(this.mCurAudioRoutes);
            this.mRoutesObservers.register(iAudioRoutesObserver);
        }
        return audioRoutesInfo;
    }

    public AudioRoutesInfo getCurAudioRoutes() {
        return this.mCurAudioRoutes;
    }

    public int setBluetoothActiveDevice(AudioDeviceBroker.BtDeviceInfo btDeviceInfo) {
        int i;
        int i2;
        synchronized (this.mDevicesLock) {
            if (btDeviceInfo.mSupprNoisy || !((((i2 = btDeviceInfo.mProfile) == 22 || i2 == 26) && btDeviceInfo.mIsLeOutput) || i2 == 21 || i2 == 2)) {
                i = 0;
            } else {
                i = checkSendBecomingNoisyIntentInt(btDeviceInfo.mAudioSystemDevice, isBtStateConnected(btDeviceInfo) ? 1 : 0, btDeviceInfo.mMusicDevice);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("setBluetoothActiveDevice device: ");
            sb.append(btDeviceInfo.mDevice);
            sb.append(" profile: ");
            sb.append(BluetoothProfile.getProfileName(btDeviceInfo.mProfile));
            sb.append(" state: ");
            int i3 = btDeviceInfo.mState;
            sb.append(i3 == 99 ? "STATE_CONNECTED_IMPLICIT" : BluetoothProfile.getConnectionStateName(i3));
            sb.append(" delay(ms): ");
            sb.append(i);
            sb.append(" codec:");
            sb.append(Integer.toHexString(btDeviceInfo.mCodec));
            sb.append(" suppressNoisyIntent: ");
            sb.append(btDeviceInfo.mSupprNoisy);
            Log.i("AS.AudioDeviceInventory", sb.toString());
            this.mDeviceBroker.postBluetoothActiveDevice(btDeviceInfo, i);
            if (btDeviceInfo.mProfile == 21 && btDeviceInfo.mState == 2) {
                this.mDeviceBroker.setForceUse_Async(1, 0, "HEARING_AID set to CONNECTED");
            }
        }
        return i;
    }

    public int setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
        synchronized (this.mDevicesLock) {
            int internalType = audioDeviceAttributes.getInternalType();
            String name = audioDeviceAttributes.getName();
            String address = audioDeviceAttributes.getAddress();
            if (i != 0 && AudioUtils.isWiredDeviceType(internalType) && KnoxAudioUtils.isRestrictedHeadphone(this.mDeviceBroker.getContext())) {
                if (!checkDeviceConnected(internalType)) {
                    return 0;
                }
                i = 0;
            }
            int checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(audioDeviceAttributes.getInternalType(), i, 0);
            if ("h2w-before-boot-completed".equals(name)) {
                if ((!this.mSystemReady && internalType == 4) || internalType == 8) {
                    this.mDeviceBroker.postSetWiredDeviceConnectionState(new WiredDeviceConnectionState(new AudioDeviceAttributes(internalType, address, "h2w"), i, str), checkSendBecomingNoisyIntentInt);
                }
            } else {
                this.mDeviceBroker.postSetWiredDeviceConnectionState(new WiredDeviceConnectionState(audioDeviceAttributes, i, str), checkSendBecomingNoisyIntentInt);
            }
            return checkSendBecomingNoisyIntentInt;
        }
    }

    public void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i) {
        WiredDeviceConnectionState wiredDeviceConnectionState = new WiredDeviceConnectionState(audioDeviceAttributes, i, "com.android.server.audio");
        wiredDeviceConnectionState.mForTest = true;
        onSetWiredDeviceConnectionState(wiredDeviceConnectionState);
    }

    public final void makeA2dpDeviceAvailable(AudioDeviceBroker.BtDeviceInfo btDeviceInfo, String str) {
        String address = btDeviceInfo.mDevice.getAddress();
        String name = BtHelper.getName(btDeviceInfo.mDevice);
        int i = btDeviceInfo.mCodec;
        this.mDeviceBroker.setBluetoothA2dpOnInt(true, true, str);
        AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(128, address, name);
        int deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 1, i);
        if (deviceConnectionState != 0) {
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("APM failed to make available A2DP device addr=" + address + " error=" + deviceConnectionState).printLog("AS.AudioDeviceInventory"));
            return;
        }
        AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("A2DP device addr=" + address + " now available").printLog("AS.AudioDeviceInventory"));
        Log.w("AS.AudioDeviceInventory", "makeA2dpDeviceAvailable clearA2dpSuspended(true) skipped");
        DeviceInfo deviceInfo = new DeviceInfo(128, name, address, i, UuidUtils.uuidFromAudioDeviceAttributes(audioDeviceAttributes));
        String key = deviceInfo.getKey();
        this.mConnectedDevices.put(key, deviceInfo);
        this.mApmConnectedDevices.add(key);
        this.mDeviceBroker.postAccessoryPlugMediaUnmute(128);
        setCurrentAudioRouteNameIfPossible(name, true);
        updateBluetoothPreferredModes_l(btDeviceInfo.mDevice);
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x010d, code lost:
    
        if (r3.isDuplexModeEnabled() != false) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x012d, code lost:
    
        if (r3.isOutputOnlyModeEnabled() != false) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyConnectedDevicesRoles_l() {
        boolean isDuplexModeEnabled;
        boolean isOutputOnlyModeEnabled;
        boolean z;
        if (this.mBluetoothDualModeEnabled) {
            DeviceInfo firstConnectedDeviceOfTypes = getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_BLE_UNICAST_SET);
            DeviceInfo firstConnectedDeviceOfTypes2 = getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_IN_ALL_BLE_SET);
            DeviceInfo firstConnectedDeviceOfTypes3 = getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
            DeviceInfo firstConnectedDeviceOfTypes4 = getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_SCO_SET);
            DeviceInfo firstConnectedDeviceOfTypes5 = getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_IN_ALL_SCO_SET);
            boolean z2 = true;
            boolean z3 = firstConnectedDeviceOfTypes != null && firstConnectedDeviceOfTypes.isOutputOnlyModeEnabled();
            boolean z4 = (firstConnectedDeviceOfTypes != null && firstConnectedDeviceOfTypes.isDuplexModeEnabled()) || (firstConnectedDeviceOfTypes2 != null && firstConnectedDeviceOfTypes2.isDuplexModeEnabled());
            AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
            AudioDeviceAttributes audioDeviceAttributes = null;
            if (audioDeviceBroker.mActiveCommunicationDevice != null && audioDeviceBroker.isInCommunication() && this.mDeviceBroker.mActiveCommunicationDevice != null) {
                audioDeviceAttributes = new AudioDeviceAttributes(this.mDeviceBroker.mActiveCommunicationDevice);
            }
            Log.i("AS.AudioDeviceInventory", "applyConnectedDevicesRoles_l\n - leOutDevice: " + firstConnectedDeviceOfTypes + "\n - leInDevice: " + firstConnectedDeviceOfTypes2 + "\n - a2dpDevice: " + firstConnectedDeviceOfTypes3 + "\n - scoOutDevice: " + firstConnectedDeviceOfTypes4 + "\n - scoInDevice: " + firstConnectedDeviceOfTypes5 + "\n - disableA2dp: " + z3 + ", disableSco: " + z4);
            for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                if (AudioSystem.isBluetoothDevice(deviceInfo.mDeviceType)) {
                    AudioDeviceAttributes audioDeviceAttributes2 = new AudioDeviceAttributes(deviceInfo.mDeviceType, deviceInfo.mDeviceAddress, deviceInfo.mDeviceName);
                    if (!audioDeviceAttributes2.equalTypeAddress(audioDeviceAttributes)) {
                        if (AudioSystem.isBluetoothOutDevice(deviceInfo.mDeviceType)) {
                            for (AudioProductStrategy audioProductStrategy : this.mStrategies) {
                                if (audioProductStrategy.getId() == this.mDeviceBroker.mCommunicationStrategyId) {
                                    if (AudioSystem.isBluetoothScoDevice(deviceInfo.mDeviceType)) {
                                        if (!z4) {
                                        }
                                        z = z2;
                                    } else if (AudioSystem.isBluetoothLeDevice(deviceInfo.mDeviceType)) {
                                        isOutputOnlyModeEnabled = deviceInfo.isDuplexModeEnabled();
                                        z = isOutputOnlyModeEnabled ^ z2;
                                    }
                                    z = false;
                                } else {
                                    if (AudioSystem.isBluetoothA2dpOutDevice(deviceInfo.mDeviceType)) {
                                        if (!z3) {
                                        }
                                        z = z2;
                                    } else {
                                        if (AudioSystem.isBluetoothScoDevice(deviceInfo.mDeviceType)) {
                                            isOutputOnlyModeEnabled = deviceInfo.isDuplexModeEnabled();
                                        } else if (AudioSystem.isBluetoothLeDevice(deviceInfo.mDeviceType)) {
                                            isOutputOnlyModeEnabled = deviceInfo.isOutputOnlyModeEnabled();
                                        }
                                        z = isOutputOnlyModeEnabled ^ z2;
                                    }
                                    z = false;
                                }
                                Log.i("AS.AudioDeviceInventory", "     - strategy: " + audioProductStrategy.getId() + ", disable: " + z);
                                if (z) {
                                    addDevicesRoleForStrategy(audioProductStrategy.getId(), 2, Arrays.asList(audioDeviceAttributes2), z2);
                                } else {
                                    removeDevicesRoleForStrategy(audioProductStrategy.getId(), 2, Arrays.asList(audioDeviceAttributes2), z2);
                                }
                            }
                        }
                        if (AudioSystem.isBluetoothInDevice(deviceInfo.mDeviceType)) {
                            int[] iArr = CAPTURE_PRESETS;
                            int length = iArr.length;
                            int i = 0;
                            while (i < length) {
                                int i2 = iArr[i];
                                if (AudioSystem.isBluetoothScoDevice(deviceInfo.mDeviceType)) {
                                    if (z4 || !deviceInfo.isDuplexModeEnabled()) {
                                        isDuplexModeEnabled = z2;
                                        Log.i("AS.AudioDeviceInventory", "      - capturePreset: " + i2 + ", disable: " + isDuplexModeEnabled);
                                        if (!isDuplexModeEnabled) {
                                            addDevicesRoleForCapturePresetInt(i2, 2, Arrays.asList(audioDeviceAttributes2));
                                        } else {
                                            removeDevicesRoleForCapturePresetInt(i2, 2, Arrays.asList(audioDeviceAttributes2));
                                        }
                                        i++;
                                        z2 = true;
                                    }
                                    isDuplexModeEnabled = false;
                                    Log.i("AS.AudioDeviceInventory", "      - capturePreset: " + i2 + ", disable: " + isDuplexModeEnabled);
                                    if (!isDuplexModeEnabled) {
                                    }
                                    i++;
                                    z2 = true;
                                } else {
                                    if (AudioSystem.isBluetoothLeDevice(deviceInfo.mDeviceType)) {
                                        isDuplexModeEnabled = deviceInfo.isDuplexModeEnabled() ^ z2;
                                        Log.i("AS.AudioDeviceInventory", "      - capturePreset: " + i2 + ", disable: " + isDuplexModeEnabled);
                                        if (!isDuplexModeEnabled) {
                                        }
                                        i++;
                                        z2 = true;
                                    }
                                    isDuplexModeEnabled = false;
                                    Log.i("AS.AudioDeviceInventory", "      - capturePreset: " + i2 + ", disable: " + isDuplexModeEnabled);
                                    if (!isDuplexModeEnabled) {
                                    }
                                    i++;
                                    z2 = true;
                                }
                            }
                        }
                        z2 = true;
                    }
                }
            }
        }
    }

    public void applyConnectedDevicesRoles() {
        synchronized (this.mDevicesLock) {
            applyConnectedDevicesRoles_l();
        }
    }

    public int checkProfileIsConnected(int i) {
        if (i == 1) {
            if (getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_SCO_SET) == null && getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_IN_ALL_SCO_SET) == null) {
                return 0;
            }
            return i;
        }
        if (i == 2) {
            if (getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_A2DP_SET) != null) {
                return i;
            }
            return 0;
        }
        if (i != 22 && i != 26) {
            return 0;
        }
        if (getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_OUT_ALL_BLE_SET) == null && getFirstConnectedDeviceOfTypes(AudioSystem.DEVICE_IN_ALL_BLE_SET) == null) {
            return 0;
        }
        return i;
    }

    public final void updateBluetoothPreferredModes_l(BluetoothDevice bluetoothDevice) {
        int profileFromType;
        if (this.mBluetoothDualModeEnabled) {
            HashSet hashSet = new HashSet(0);
            for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                if (AudioSystem.isBluetoothDevice(deviceInfo.mDeviceType) && !hashSet.contains(deviceInfo.mDeviceAddress)) {
                    Bundle preferredAudioProfiles = BtHelper.getPreferredAudioProfiles(deviceInfo.mDeviceAddress);
                    Log.i("AS.AudioDeviceInventory", "updateBluetoothPreferredModes_l processing device address: " + AudioManagerHelper.getAddressForLog(deviceInfo.mDeviceAddress) + ", preferredProfiles: " + preferredAudioProfiles);
                    for (DeviceInfo deviceInfo2 : this.mConnectedDevices.values()) {
                        if (AudioSystem.isBluetoothDevice(deviceInfo2.mDeviceType) && deviceInfo.mDeviceAddress.equals(deviceInfo2.mDeviceAddress) && (profileFromType = BtHelper.getProfileFromType(deviceInfo2.mDeviceType)) != 0) {
                            int checkProfileIsConnected = checkProfileIsConnected(preferredAudioProfiles.getInt("audio_mode_duplex"));
                            if (checkProfileIsConnected == profileFromType || checkProfileIsConnected == 0) {
                                deviceInfo2.setModeEnabled("audio_mode_duplex");
                            } else {
                                deviceInfo2.setModeDisabled("audio_mode_duplex");
                            }
                            int checkProfileIsConnected2 = checkProfileIsConnected(preferredAudioProfiles.getInt("audio_mode_output_only"));
                            if (checkProfileIsConnected2 == profileFromType || checkProfileIsConnected2 == 0) {
                                deviceInfo2.setModeEnabled("audio_mode_output_only");
                            } else {
                                deviceInfo2.setModeDisabled("audio_mode_output_only");
                            }
                        }
                    }
                    hashSet.add(deviceInfo.mDeviceAddress);
                }
            }
            applyConnectedDevicesRoles_l();
            if (bluetoothDevice != null) {
                this.mDeviceBroker.postNotifyPreferredAudioProfileApplied(bluetoothDevice);
            }
        }
    }

    public final void makeA2dpDeviceUnavailableNow(String str, int i) {
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.a2dp." + str).set(MediaMetrics.Property.ENCODING, AudioSystem.audioFormatToString(i)).set(MediaMetrics.Property.EVENT, "makeA2dpDeviceUnavailableNow");
        if (str == null) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "address null").record();
            return;
        }
        String makeDeviceListKey = DeviceInfo.makeDeviceListKey(128, str);
        this.mConnectedDevices.remove(makeDeviceListKey);
        Iterator it = this.mApmConnectedDevices.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (makeDeviceListKey.equals((String) it.next())) {
                z = true;
            }
        }
        if (!z) {
            Log.i("AS.AudioDeviceInventory", "makeA2dpDeviceUnavailableNow return " + makeDeviceListKey + " not connected !!!");
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("A2DP device " + str + " made unavailable, was not used").printLog("AS.AudioDeviceInventory"));
            item.set(MediaMetrics.Property.EARLY_RETURN, "A2DP device made unavailable, was not used").record();
            return;
        }
        AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(128, str);
        int deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, i);
        if (deviceConnectionState != 0) {
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("APM failed to make unavailable A2DP device addr=" + str + " error=" + deviceConnectionState).printLog("AS.AudioDeviceInventory"));
        } else {
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("A2DP device addr=" + str + " made unavailable").printLog("AS.AudioDeviceInventory"));
        }
        this.mApmConnectedDevices.remove(makeDeviceListKey);
        setCurrentAudioRouteNameIfPossible(null, true);
        item.record();
        updateBluetoothPreferredModes_l(null);
        purgeDevicesRoles_l();
        if (!checkDeviceConnected(128)) {
            setCurrentAudioRouteNameIfPossible(null, false);
        }
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    /* renamed from: makeA2dpDeviceUnavailableLater, reason: merged with bridge method [inline-methods] */
    public final void lambda$disconnectA2dp$29(String str, int i) {
        this.mDeviceBroker.setA2dpSuspended(true, true, "makeA2dpDeviceUnavailableLater");
        String makeDeviceListKey = DeviceInfo.makeDeviceListKey(128, str);
        DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(makeDeviceListKey);
        int i2 = deviceInfo != null ? deviceInfo.mDeviceCodecFormat : 0;
        this.mConnectedDevices.remove(makeDeviceListKey);
        this.mDeviceBroker.setA2dpTimeout(str, i2, i);
    }

    public final void makeA2dpSrcAvailable(String str) {
        this.mAudioSystem.setDeviceConnectionState(new AudioDeviceAttributes(-2147352576, str), 1, 0);
        this.mConnectedDevices.put(DeviceInfo.makeDeviceListKey(-2147352576, str), new DeviceInfo(-2147352576, "", str));
    }

    /* renamed from: makeA2dpSrcUnavailable, reason: merged with bridge method [inline-methods] */
    public final void lambda$disconnectA2dpSink$31(String str) {
        AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(-2147352576, str);
        this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
        this.mConnectedDevices.remove(DeviceInfo.makeDeviceListKey(-2147352576, str));
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    public final void makeHearingAidDeviceAvailable(String str, String str2, int i, String str3) {
        this.mDeviceBroker.postSetHearingAidVolumeIndex(this.mDeviceBroker.getVssVolumeForDevice(i, 134217728), i);
        this.mAudioSystem.setDeviceConnectionState(new AudioDeviceAttributes(134217728, str, str2), 1, 0);
        this.mConnectedDevices.put(DeviceInfo.makeDeviceListKey(134217728, str), new DeviceInfo(134217728, str2, str));
        this.mDeviceBroker.postAccessoryPlugMediaUnmute(134217728);
        this.mDeviceBroker.postApplyVolumeOnDevice(i, 134217728, "makeHearingAidDeviceAvailable");
        setCurrentAudioRouteNameIfPossible(str2, false);
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.makeHearingAidDeviceAvailable");
        MediaMetrics.Key key = MediaMetrics.Property.ADDRESS;
        if (str == null) {
            str = "";
        }
        item.set(key, str).set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(134217728)).set(MediaMetrics.Property.NAME, str2).set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(i)).record();
    }

    /* renamed from: makeHearingAidDeviceUnavailable, reason: merged with bridge method [inline-methods] */
    public final void lambda$disconnectHearingAid$33(String str) {
        AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(134217728, str);
        this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
        this.mConnectedDevices.remove(DeviceInfo.makeDeviceListKey(134217728, str));
        setCurrentAudioRouteNameIfPossible(null, false);
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.makeHearingAidDeviceUnavailable");
        MediaMetrics.Key key = MediaMetrics.Property.ADDRESS;
        if (str == null) {
            str = "";
        }
        item.set(key, str).set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(134217728)).record();
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    public boolean isHearingAidConnected() {
        return getFirstConnectedDeviceOfTypes(Sets.newHashSet(new Integer[]{134217728})) != null;
    }

    public final DeviceInfo getFirstConnectedDeviceOfTypes(Set set) {
        List connectedDevicesOfTypes = getConnectedDevicesOfTypes(set);
        if (connectedDevicesOfTypes.isEmpty()) {
            return null;
        }
        return (DeviceInfo) connectedDevicesOfTypes.get(0);
    }

    public final List getConnectedDevicesOfTypes(Set set) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mDevicesLock) {
            for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                if (set.contains(Integer.valueOf(deviceInfo.mDeviceType))) {
                    arrayList.add(deviceInfo);
                }
            }
        }
        return arrayList;
    }

    public AudioDeviceAttributes getDeviceOfType(int i) {
        DeviceInfo firstConnectedDeviceOfTypes = getFirstConnectedDeviceOfTypes(Sets.newHashSet(new Integer[]{Integer.valueOf(i)}));
        if (firstConnectedDeviceOfTypes == null) {
            return null;
        }
        return new AudioDeviceAttributes(firstConnectedDeviceOfTypes.mDeviceType, firstConnectedDeviceOfTypes.mDeviceAddress, firstConnectedDeviceOfTypes.mDeviceName);
    }

    public final void makeLeAudioDeviceAvailable(AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i, String str) {
        int i2 = btDeviceInfo.mVolume;
        int i3 = btDeviceInfo.mAudioSystemDevice;
        if (i3 != 0) {
            String address = btDeviceInfo.mDevice.getAddress();
            String name = BtHelper.getName(btDeviceInfo.mDevice);
            if (i3 == 536870914 && name.equals("")) {
                name = "Broadcast";
            }
            String str2 = name;
            this.mDeviceBroker.setBluetoothA2dpOnInt(true, false, str);
            int checkBleDeviceFormat = checkBleDeviceFormat(i3, btDeviceInfo.mState, btDeviceInfo.mDevice);
            AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(i3, address, str2);
            int deviceConnectionState = AudioSystem.setDeviceConnectionState(audioDeviceAttributes, 1, checkBleDeviceFormat);
            if (deviceConnectionState != 0) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("APM failed to make available LE Audio device addr=" + address + " error=" + deviceConnectionState).printLog("AS.AudioDeviceInventory"));
            } else {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("LE Audio device addr=" + address + " now available").printLog("AS.AudioDeviceInventory"));
                if (i3 == 536870914) {
                    BtUtils.setAuracast(true);
                    this.mDeviceBroker.setLeBroadcasting(true);
                }
            }
            this.mConnectedDevices.put(DeviceInfo.makeDeviceListKey(i3, address), new DeviceInfo(i3, str2, address, checkBleDeviceFormat, UuidUtils.uuidFromAudioDeviceAttributes(audioDeviceAttributes)));
            this.mDeviceBroker.postAccessoryPlugMediaUnmute(i3);
            setCurrentAudioRouteNameIfPossible(str2, false);
        }
        if (i == -1) {
            updateBluetoothPreferredModes_l(btDeviceInfo.mDevice);
            return;
        }
        this.mDeviceBroker.postSetLeAudioVolumeIndex(this.mDeviceBroker.getVssVolumeForDevice(i, i3), this.mDeviceBroker.getMaxVssVolumeForStream(i), i);
        this.mDeviceBroker.postApplyVolumeOnDevice(i, i3, "makeLeAudioDeviceAvailable");
        updateBluetoothPreferredModes_l(btDeviceInfo.mDevice);
    }

    public final void makeLeAudioDeviceUnavailableNow(String str, int i) {
        AudioDeviceAttributes audioDeviceAttributes;
        if (i != 0) {
            audioDeviceAttributes = new AudioDeviceAttributes(i, str);
            int deviceConnectionState = AudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
            if (deviceConnectionState != 0) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("APM failed to make unavailable LE Audio device addr=" + str + " error=" + deviceConnectionState).printLog("AS.AudioDeviceInventory"));
            } else {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("LE Audio device addr=" + str + " made unavailable").printLog("AS.AudioDeviceInventory"));
                if (i == 536870914) {
                    BtUtils.setAuracast(false);
                    this.mDeviceBroker.setLeBroadcasting(false);
                }
            }
            this.mConnectedDevices.remove(DeviceInfo.makeDeviceListKey(i, str));
        } else {
            audioDeviceAttributes = null;
        }
        if (isBleOutDevice(i)) {
            Boolean bool = Boolean.TRUE;
            Iterator it = this.mConnectedDevices.values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (isBleOutDevice(((DeviceInfo) it.next()).mDeviceType)) {
                    bool = Boolean.FALSE;
                    break;
                }
            }
            if (bool.booleanValue()) {
                this.mDeviceBroker.removeBleCommunicationRouteClient();
            }
        }
        setCurrentAudioRouteNameIfPossible(null, false);
        updateBluetoothPreferredModes_l(null);
        purgeDevicesRoles_l();
        if (audioDeviceAttributes != null) {
            this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
        }
    }

    /* renamed from: makeLeAudioDeviceUnavailableLater, reason: merged with bridge method [inline-methods] */
    public final void lambda$disconnectLeAudio$35(String str, int i, int i2) {
        this.mDeviceBroker.setLeAudioSuspended(true, true, "makeLeAudioDeviceUnavailableLater");
        this.mConnectedDevices.remove(DeviceInfo.makeDeviceListKey(i, str));
        this.mDeviceBroker.setLeAudioTimeout(str, i, i2);
    }

    public final void setCurrentAudioRouteNameIfPossible(String str, boolean z) {
        synchronized (this.mCurAudioRoutes) {
            if (TextUtils.equals(this.mCurAudioRoutes.bluetoothName, str)) {
                return;
            }
            if (str != null || !isCurrentDeviceConnected()) {
                this.mCurAudioRoutes.bluetoothName = str;
                this.mDeviceBroker.postReportNewRoutes(z);
            }
        }
    }

    public final boolean isCurrentDeviceConnected() {
        return this.mConnectedDevices.values().stream().anyMatch(new Predicate() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isCurrentDeviceConnected$36;
                lambda$isCurrentDeviceConnected$36 = AudioDeviceInventory.this.lambda$isCurrentDeviceConnected$36((AudioDeviceInventory.DeviceInfo) obj);
                return lambda$isCurrentDeviceConnected$36;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isCurrentDeviceConnected$36(DeviceInfo deviceInfo) {
        return TextUtils.equals(deviceInfo.mDeviceName, this.mCurAudioRoutes.bluetoothName);
    }

    public final int checkSendBecomingNoisyIntentInt(int i, int i2, int i3) {
        MediaMetrics.Item item = new MediaMetrics.Item("audio.device.checkSendBecomingNoisyIntentInt").set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(i)).set(MediaMetrics.Property.STATE, i2 == 1 ? "connected" : "disconnected");
        int i4 = 0;
        if (i2 != 0) {
            Log.i("AS.AudioDeviceInventory", "not sending NOISY: state=" + i2);
            item.set(MediaMetrics.Property.DELAY_MS, 0).record();
            return 0;
        }
        Set set = BECOMING_NOISY_INTENT_DEVICES_SET;
        if (!set.contains(Integer.valueOf(i))) {
            Log.i("AS.AudioDeviceInventory", "not sending NOISY: device=0x" + Integer.toHexString(i) + " not in set " + set);
            item.set(MediaMetrics.Property.DELAY_MS, 0).record();
            return 0;
        }
        HashSet hashSet = new HashSet();
        for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
            int i5 = deviceInfo.mDeviceType;
            if ((Integer.MIN_VALUE & i5) == 0 && BECOMING_NOISY_INTENT_DEVICES_SET.contains(Integer.valueOf(i5))) {
                hashSet.add(Integer.valueOf(deviceInfo.mDeviceType));
                Log.i("AS.AudioDeviceInventory", "NOISY: adding 0x" + Integer.toHexString(deviceInfo.mDeviceType));
            }
        }
        if (i3 == 0) {
            i3 = this.mDeviceBroker.getDeviceForStream(3);
            Log.i("AS.AudioDeviceInventory", "NOISY: musicDevice changing from NONE to 0x" + Integer.toHexString(i3));
        }
        boolean isInCommunication = this.mDeviceBroker.isInCommunication();
        boolean isSingleAudioDeviceType = AudioSystem.isSingleAudioDeviceType(hashSet, i);
        boolean hasMediaDynamicPolicy = this.mDeviceBroker.hasMediaDynamicPolicy();
        boolean checkEnforcedStream = checkEnforcedStream(i);
        int i6 = 500;
        if ((i == i3 || isInCommunication || checkEnforcedStream) && !hasMediaDynamicPolicy && i3 != 32768) {
            if (!this.mAudioSystem.isStreamActive(3, 0) && !this.mDeviceBroker.hasAudioFocusUsers()) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("dropping ACTION_AUDIO_BECOMING_NOISY").printLog("AS.AudioDeviceInventory"));
                item.set(MediaMetrics.Property.DELAY_MS, 0).record();
                return 0;
            }
            if (ScreenSharingHelper.isSplitSoundEnabled() && AudioUtils.isWiredDeviceType(i) && this.mDeviceBroker.isInCommunication()) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("dropping ACTION_AUDIO_BECOMING_NOISY by split sound").printLog("AS.AudioDeviceInventory"));
                item.set(MediaMetrics.Property.DELAY_MS, 0).record();
                return 0;
            }
            this.mDeviceBroker.postBroadcastBecomingNoisy(i);
        } else if (this.mDeviceBroker.isMultiSoundOn() && this.mAudioSystem.isStreamActive(3, 0) && this.mDeviceBroker.getPinDevice() == i) {
            AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("send ACTION_AUDIO_BECOMING_NOISY to pinned apps").printLog("AS.AudioDeviceInventory"));
            this.mDeviceBroker.sendBecomingNoisyToPinnedApp(i);
        } else {
            Log.i("AS.AudioDeviceInventory", "not sending NOISY: device:0x" + Integer.toHexString(i) + " musicDevice:0x" + Integer.toHexString(i3) + " inComm:" + isInCommunication + " mediaPolicy:" + hasMediaDynamicPolicy + " singleDevice:" + isSingleAudioDeviceType);
            i6 = 0;
        }
        if (i6 > 0) {
            if (FactoryUtils.isFactoryMode() && (i == 1024 || i == 67108864)) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("HDMI/USB Headset delay in factory mode").printLog("AS.AudioDeviceInventory"));
            } else if (AudioUtils.isWiredDeviceType(i) && this.mDeviceBroker.isInCommunication()) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("in call wired headset/headphone").printLog("AS.AudioDeviceInventory"));
            }
            item.set(MediaMetrics.Property.DELAY_MS, Integer.valueOf(i4)).record();
            return i4;
        }
        i4 = i6;
        item.set(MediaMetrics.Property.DELAY_MS, Integer.valueOf(i4)).record();
        return i4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x006a, code lost:
    
        if (r11 != 262145) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void sendDeviceConnectionIntent(int i, int i2, String str, String str2) {
        Slog.i("AS.AudioDeviceInventory", "sendDeviceConnectionIntent(dev:0x" + Integer.toHexString(i) + " state:0x" + Integer.toHexString(i2) + " address:" + str + " name:" + str2 + ");");
        Intent intent = new Intent();
        if (i != -2113929216) {
            if (i == 4) {
                intent.setAction("android.intent.action.HEADSET_PLUG");
                intent.putExtra("microphone", 1);
            } else {
                if (i != 8) {
                    if (i != 1024) {
                        if (i != 131072) {
                            if (i == 67108864) {
                                intent.setAction("android.intent.action.HEADSET_PLUG");
                                intent.putExtra("microphone", AudioSystem.getDeviceConnectionState(-2113929216, "") == 1 ? 1 : 0);
                                if (!Rune.SEC_AUDIO_USB_HEADSET_FOR_CAMCORDER) {
                                    intent.putExtra("builtInMic", 1);
                                }
                            } else if (i != 262144) {
                            }
                        }
                    }
                    configureHdmiPlugIntent(intent, i2);
                }
                intent.setAction("android.intent.action.HEADSET_PLUG");
                intent.putExtra("microphone", 0);
            }
        } else {
            if (AudioSystem.getDeviceConnectionState(67108864, "") != 1) {
                return;
            }
            intent.setAction("android.intent.action.HEADSET_PLUG");
            intent.putExtra("microphone", 1);
            if (!Rune.SEC_AUDIO_USB_HEADSET_FOR_CAMCORDER) {
                intent.putExtra("builtInMic", 1);
            }
        }
        if (intent.getAction() == null) {
            return;
        }
        intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, i2);
        intent.putExtra("address", str);
        intent.putExtra("portName", str2);
        intent.addFlags(1073741824);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.broadcastStickyIntentToCurrentProfileGroup(intent);
            if (AudioUtils.isWiredDeviceType(i)) {
                Intent intent2 = new Intent("com.sec.android.contextaware.HEADSET_PLUG");
                intent2.putExtras(intent.getExtras());
                intent2.addFlags(32);
                intent2.addFlags(16777216);
                this.mDeviceBroker.broadcastStickyIntentToCurrentProfileGroup(intent2);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (AudioUtils.isWiredDeviceType(i) && this.mDeviceBroker.isForceSpeakerOn()) {
                sendMsgForForceSpeaker();
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateAudioRoutes(int i, int i2) {
        int i3 = 4;
        if (i != 4) {
            if (i != 8) {
                if (i != 1024) {
                    if (i != 4096) {
                        if (i != 16384) {
                            if (i != 131072) {
                                if (i != 67108864) {
                                    if (i != 262144 && i != 262145) {
                                        i3 = 0;
                                    }
                                }
                            }
                        }
                        i3 = 16;
                    }
                }
                i3 = 8;
            }
            i3 = 2;
        } else {
            i3 = 1;
        }
        synchronized (this.mCurAudioRoutes) {
            if (i3 == 0) {
                return;
            }
            AudioRoutesInfo audioRoutesInfo = this.mCurAudioRoutes;
            int i4 = audioRoutesInfo.mainType;
            int i5 = i2 != 0 ? i4 | i3 : (~i3) & i4;
            if (i5 != i4) {
                audioRoutesInfo.mainType = i5;
                this.mDeviceBroker.postReportNewRoutes(false);
            }
        }
    }

    public final void configureHdmiPlugIntent(Intent intent, int i) {
        intent.setAction("android.media.action.HDMI_AUDIO_PLUG");
        intent.putExtra("android.media.extra.AUDIO_PLUG_STATE", i);
        if (i != 1) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int listAudioPorts = AudioSystem.listAudioPorts(arrayList, new int[1]);
        if (listAudioPorts != 0) {
            Log.e("AS.AudioDeviceInventory", "listAudioPorts error " + listAudioPorts + " in configureHdmiPlugIntent");
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AudioDevicePort audioDevicePort = (AudioPort) it.next();
            if (audioDevicePort instanceof AudioDevicePort) {
                AudioDevicePort audioDevicePort2 = audioDevicePort;
                if (audioDevicePort2.type() == 1024 || audioDevicePort2.type() == 262144 || audioDevicePort2.type() == 262145) {
                    int[] filterPublicFormats = AudioFormat.filterPublicFormats(audioDevicePort2.formats());
                    if (filterPublicFormats.length > 0) {
                        ArrayList arrayList2 = new ArrayList(1);
                        for (int i2 : filterPublicFormats) {
                            if (i2 != 0) {
                                arrayList2.add(Integer.valueOf(i2));
                            }
                        }
                        intent.putExtra("android.media.extra.ENCODINGS", arrayList2.stream().mapToInt(new ToIntFunction() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda7
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                int intValue;
                                intValue = ((Integer) obj).intValue();
                                return intValue;
                            }
                        }).toArray());
                    }
                    int i3 = 0;
                    for (int i4 : audioDevicePort2.channelMasks()) {
                        int channelCountFromOutChannelMask = AudioFormat.channelCountFromOutChannelMask(i4);
                        if (channelCountFromOutChannelMask > i3) {
                            i3 = channelCountFromOutChannelMask;
                        }
                    }
                    intent.putExtra("android.media.extra.MAX_CHANNEL_COUNT", i3);
                }
            }
        }
    }

    public final void dispatchPreferredDevice(int i, List list) {
        int beginBroadcast = this.mPrefDevDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mPrefDevDispatchers.getBroadcastItem(i2).dispatchPrefDevicesChanged(i, list);
            } catch (RemoteException unused) {
            }
        }
        this.mPrefDevDispatchers.finishBroadcast();
    }

    public final void dispatchNonDefaultDevice(int i, List list) {
        int beginBroadcast = this.mNonDefDevDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mNonDefDevDispatchers.getBroadcastItem(i2).dispatchNonDefDevicesChanged(i, list);
            } catch (RemoteException unused) {
            }
        }
        this.mNonDefDevDispatchers.finishBroadcast();
    }

    public final void dispatchDevicesRoleForCapturePreset(int i, int i2, List list) {
        int beginBroadcast = this.mDevRoleCapturePresetDispatchers.beginBroadcast();
        for (int i3 = 0; i3 < beginBroadcast; i3++) {
            try {
                this.mDevRoleCapturePresetDispatchers.getBroadcastItem(i3).dispatchDevicesRoleChanged(i, i2, list);
            } catch (RemoteException unused) {
            }
        }
        this.mDevRoleCapturePresetDispatchers.finishBroadcast();
    }

    public UUID getDeviceSensorUuid(AudioDeviceAttributes audioDeviceAttributes) {
        String makeDeviceListKey = DeviceInfo.makeDeviceListKey(audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
        synchronized (this.mDevicesLock) {
            DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(makeDeviceListKey);
            if (deviceInfo == null) {
                return null;
            }
            return deviceInfo.mSensorUuid;
        }
    }

    public boolean isA2dpDeviceConnected(BluetoothDevice bluetoothDevice) {
        Iterator it = getConnectedDevicesOfTypes(Sets.newHashSet(new Integer[]{128})).iterator();
        while (it.hasNext()) {
            if (((DeviceInfo) it.next()).mDeviceAddress.equals(bluetoothDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDeviceConnected(final int i) {
        boolean anyMatch;
        if (i == 32768 && this.mAudioSystem.getDeviceConnectionState(32768, "0") > 0) {
            return true;
        }
        synchronized (this.mDevicesLock) {
            anyMatch = this.mConnectedDevices.values().stream().anyMatch(new Predicate() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$checkDeviceConnected$38;
                    lambda$checkDeviceConnected$38 = AudioDeviceInventory.lambda$checkDeviceConnected$38(i, (AudioDeviceInventory.DeviceInfo) obj);
                    return lambda$checkDeviceConnected$38;
                }
            });
        }
        return anyMatch;
    }

    public static /* synthetic */ boolean lambda$checkDeviceConnected$38(int i, DeviceInfo deviceInfo) {
        return deviceInfo.mDeviceType == i;
    }

    public void handleFmRadioDeviceConnection(int i, int i2, String str) {
        this.mAudioSystem.setDeviceConnectionState(new AudioDeviceAttributes(i, str, ""), i2, 0);
    }

    public void sendMsgForForceSpeaker() {
        this.mDeviceBroker.sendMsgForForceSpeaker();
    }

    public void updateDeviceQuickConnection(boolean z, int i, String str, String str2, int i2) {
        String makeDeviceListKey = DeviceInfo.makeDeviceListKey(i, str);
        DeviceInfo deviceInfo = new DeviceInfo(i, str2, str, i2);
        if (z) {
            this.mConnectedDevices.put(makeDeviceListKey, deviceInfo);
        } else {
            this.mConnectedDevices.remove(makeDeviceListKey);
        }
    }

    public String getAddressForDevice(int i) {
        BluetoothHeadset bTHeadset;
        List<BluetoothDevice> connectedDevices;
        BluetoothA2dp a2dp;
        BluetoothDevice activeDevice;
        if (AudioDeviceInfo.convertInternalDeviceToDeviceType(i) == 8 && (a2dp = this.mDeviceBroker.getA2dp()) != null && (activeDevice = a2dp.getActiveDevice()) != null) {
            return activeDevice.getAddress();
        }
        if (AudioDeviceInfo.convertInternalDeviceToDeviceType(i) == 7 && (bTHeadset = this.mDeviceBroker.getBTHeadset()) != null && (connectedDevices = bTHeadset.getConnectedDevices()) != null) {
            for (BluetoothDevice bluetoothDevice : connectedDevices) {
                if (bTHeadset.isAudioConnected(bluetoothDevice)) {
                    return bluetoothDevice.getAddress();
                }
            }
        }
        if (i == 32768) {
            if (((DeviceInfo) this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(i, "0"))) != null) {
                return "0";
            }
        }
        DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(i, ""));
        return deviceInfo != null ? deviceInfo.mDeviceAddress : "";
    }

    public int setDeviceToForceByUser(int i, String str, boolean z) {
        String str2;
        int i2;
        AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("setDeviceToForceByUser : device = " + i + " address = " + AudioManagerHelper.getAddressForLog(str) + " force = " + z + " uid = " + Binder.getCallingUid() + " pid = " + Binder.getCallingPid()).printLog("AS.AudioDeviceInventory"));
        if (i == 32768) {
            Log.d("AS.AudioDeviceInventory", "setDeviceToForceByUser: remote submix should use address 0");
            str = "0";
        }
        if (Binder.getCallingUid() == 1002 && !z && this.mDeviceBroker.isMultiSoundOn() && this.mDeviceBroker.getPinDevice() == 128 && this.mDeviceBroker.getDeviceForStream(3) != 128) {
            Log.d("AS.AudioDeviceInventory", "Device does not change while MultiSound On");
            String changeActiveBluetoothDevice = changeActiveBluetoothDevice(str);
            synchronized (this.mCurAudioRoutes) {
                this.mCurAudioRoutes.bluetoothName = changeActiveBluetoothDevice;
            }
            return 0;
        }
        synchronized (this.mDevicesLock) {
            if (((DeviceInfo) this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(i, str))) == null && !AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(i))) {
                Log.e("AS.AudioDeviceInventory", "There is no device spec in connected devices");
                return -1;
            }
            if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
                str2 = changeActiveBluetoothDevice(str);
                this.mDeviceBroker.setBluetoothA2dpOnInt(true, false, "setDeviceToForceByUser(true) from u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid());
            } else if (AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(i))) {
                str2 = changeActiveBleDevice(str);
                if (str2 == null) {
                    return -1;
                }
                this.mDeviceBroker.setBluetoothA2dpOnInt(true, false, "setDeviceToForceByUser(true) BLE address = " + AudioManagerHelper.getAddressForLog(str) + " from u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid());
            } else {
                BluetoothA2dp a2dp = this.mDeviceBroker.getA2dp();
                if (a2dp != null) {
                    int audioPath = a2dp.setAudioPath(i);
                    Log.d("AS.AudioDeviceInventory", "setAudioPath delay : " + audioPath);
                    i2 = audioPath;
                    str2 = null;
                    this.mDeviceBroker.postSetDeviceConnectionStateForceByUser(new SetForceDeviceState(i, str, str2), i2);
                    return 0;
                }
                str2 = null;
            }
            i2 = 0;
            this.mDeviceBroker.postSetDeviceConnectionStateForceByUser(new SetForceDeviceState(i, str, str2), i2);
            return 0;
        }
    }

    public class SetForceDeviceState {
        public final String mActiveBTDeviceName;
        public final String mAddress;
        public final int mDevice;

        public SetForceDeviceState(int i, String str, String str2) {
            this.mDevice = i;
            this.mAddress = str;
            this.mActiveBTDeviceName = str2;
        }
    }

    public void onSetDeviceConnectionStateForceByUser(SetForceDeviceState setForceDeviceState) {
        int i = setForceDeviceState.mDevice;
        String str = setForceDeviceState.mAddress;
        String str2 = setForceDeviceState.mActiveBTDeviceName;
        synchronized (this.mDevicesLock) {
            DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(DeviceInfo.makeDeviceListKey(i, str));
            if (deviceInfo == null) {
                Log.e("AS.AudioDeviceInventory", "There is no device spec in connected devices");
                return;
            }
            Log.d("AS.AudioDeviceInventory", "Device is changed by force ret : " + this.mAudioSystem.setDeviceConnectionState(new AudioDeviceAttributes(deviceInfo.mDeviceType, str, ""), 2, 0));
            synchronized (this.mCurAudioRoutes) {
                Log.i("AS.AudioDeviceInventory", "send NEW_ROUTES MSG, BT Name is " + str2);
                this.mCurAudioRoutes.bluetoothName = str2;
                updateMediaRoutes(false);
            }
        }
    }

    public String changeActiveBluetoothDevice(String str) {
        BluetoothDevice activeDevice;
        BluetoothA2dp a2dp = this.mDeviceBroker.getA2dp();
        if (a2dp == null) {
            return null;
        }
        List<BluetoothDevice> connectedDevices = a2dp.getConnectedDevices();
        if (connectedDevices == null) {
            Log.w("AS.AudioDeviceInventory", "Nothing connected BT devices");
            return null;
        }
        if (connectedDevices.size() == 1 && (activeDevice = a2dp.getActiveDevice()) != null) {
            a2dp.setActiveDevice(activeDevice);
            Log.i("AS.AudioDeviceInventory", "changeActiveBluetoothDevice a2dp setActiveDevice = " + activeDevice.getName());
            if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                BtUtils.setBtVolumeMonitor(activeDevice);
            }
            return activeDevice.semGetAliasName();
        }
        for (BluetoothDevice bluetoothDevice : connectedDevices) {
            if (bluetoothDevice.getAddress().equals(str)) {
                if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                    BtUtils.setBtVolumeMonitor(bluetoothDevice);
                }
                if (a2dp.setActiveDevice(bluetoothDevice)) {
                    if (this.mDeviceBroker.isDualA2dpMode()) {
                        Log.i("AS.AudioDeviceInventory", "Dual Audio is disabled by a2dp active changed.");
                        this.mDeviceBroker.setDualA2dpMode(false, bluetoothDevice);
                    }
                    this.mDeviceBroker.setActiveBluetoothDevice(bluetoothDevice);
                    return bluetoothDevice.semGetAliasName();
                }
            }
        }
        return null;
    }

    public String changeActiveBleDevice(String str) {
        BluetoothDevice bluetoothDevice;
        BluetoothLeAudio leAudio = this.mDeviceBroker.getLeAudio();
        if (leAudio == null) {
            return null;
        }
        List<BluetoothDevice> connectedDevices = leAudio.getConnectedDevices();
        if (connectedDevices == null) {
            Log.w("AS.AudioDeviceInventory", "Nothing connected BLE devices");
            return null;
        }
        Boolean bool = Boolean.FALSE;
        if (str != null && isBleHeadsetConnected(str)) {
            bool = Boolean.TRUE;
        }
        if (!bool.booleanValue()) {
            for (BluetoothDevice bluetoothDevice2 : connectedDevices) {
                if (bluetoothDevice2.getAddress().equals(str)) {
                    leAudio.setActiveDevice(bluetoothDevice2);
                    Log.i("AS.AudioDeviceInventory", "changeActiveBleDevice setActiveDevice for BLE connection address = " + AudioManagerHelper.getAddressForLog(str));
                    return null;
                }
            }
        }
        if (connectedDevices.size() > 0) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    bluetoothDevice = null;
                    break;
                }
                bluetoothDevice = it.next();
                if (bluetoothDevice.getAddress().equals(str)) {
                    break;
                }
            }
            if (bluetoothDevice != null) {
                if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
                    BtUtils.setBtVolumeMonitor(bluetoothDevice);
                }
                String semGetAliasName = bluetoothDevice.semGetAliasName();
                leAudio.setActiveDevice(bluetoothDevice);
                Log.i("AS.AudioDeviceInventory", "changeActiveBleDevice activeDeviceName = " + semGetAliasName);
                return semGetAliasName;
            }
        }
        return null;
    }

    public Set getAvailableDeviceSetForQuickSoundPath() {
        HashSet hashSet = new HashSet();
        synchronized (this.mDevicesLock) {
            if (checkDeviceConnected(-2147352576)) {
                hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
            }
            if (this.mDeviceBroker.isBluetoothScoOn()) {
                hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
            }
            if (checkDeviceConnected(32768)) {
                if (ScreenSharingHelper.isWifiDisplayConnected()) {
                    if (((DisplayManager) this.mDeviceBroker.getContext().getSystemService("display")).semGetActiveDlnaState() == 1) {
                        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
                    }
                } else {
                    hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
                }
            }
            if (Rune.SEC_AUDIO_FM_RADIO && AudioManagerHelper.isFMPlayerActive()) {
                hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
            }
            if (AudioSystem.getForceUse(1) == 10001) {
                hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SET);
            }
        }
        HashSet hashSet2 = new HashSet(AudioSystem.DEVICE_OUT_ALL_SET);
        hashSet2.removeAll(hashSet);
        return hashSet2;
    }

    public void onSystemReady() {
        this.mSystemReady = true;
        setWiredDeviceConnectionStateBeforeBoot();
    }

    public final void setWiredDeviceConnectionStateBeforeBoot() {
        int wiredDeviceIdFromSysFile = AudioUtils.getWiredDeviceIdFromSysFile();
        if (wiredDeviceIdFromSysFile == 1) {
            setWiredDeviceConnectionState(new AudioDeviceAttributes(4, "", "h2w-before-boot-completed"), 1, "AS.AudioDeviceInventory");
        } else {
            if (wiredDeviceIdFromSysFile != 2) {
                return;
            }
            setWiredDeviceConnectionState(new AudioDeviceAttributes(8, "", "h2w-before-boot-completed"), 1, "AS.AudioDeviceInventory");
        }
    }

    public void updateDexState() {
        this.mDeviceBroker.updateDexState();
    }

    public int getPriorityDevice(int i) {
        int i2;
        int[] iArr = {32768, 128, 8, 4, 67108864, IInstalld.FLAG_FORCE, 16384, IInstalld.FLAG_USE_QUOTA, 1024, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, 2};
        synchronized (this.mDevicesLock) {
            int i3 = 0;
            int i4 = 0;
            for (Map.Entry entry : this.mConnectedDevices.entrySet()) {
                String str = (String) entry.getKey();
                int i5 = ((DeviceInfo) this.mConnectedDevices.get(str)).mDeviceType;
                if ((Integer.MIN_VALUE & i5) == 0) {
                    i4 |= i5;
                }
            }
            while (true) {
                if (i3 >= 11) {
                    i2 = 2;
                    break;
                }
                i2 = iArr[i3];
                if (i != i2 && (i4 & i2) != 0) {
                    break;
                }
                i3++;
            }
        }
        return i2;
    }

    public int getConnectedDevice() {
        int i = 0;
        for (Map.Entry entry : this.mConnectedDevices.entrySet()) {
            String str = (String) entry.getKey();
            DeviceInfo deviceInfo = (DeviceInfo) this.mConnectedDevices.get(str);
            int i2 = deviceInfo.mDeviceType;
            if ((Integer.MIN_VALUE & i2) == 0 && SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET.contains(Integer.valueOf(i2))) {
                i |= deviceInfo.mDeviceType;
            }
        }
        return i;
    }

    public final void updateMediaRoutes(boolean z) {
        this.mForcePath = this.mCurAudioRoutes.bluetoothName != null ? "BT" : "OTHERS";
        this.mDeviceBroker.postReportNewRoutes(z);
    }

    public final boolean checkEnforcedStream(int i) {
        return this.mAudioSystem.isStreamActive(7, 0) && this.mDeviceBroker.getDeviceForStream(1) == i;
    }

    public final boolean isBtStateConnected(AudioDeviceBroker.BtDeviceInfo btDeviceInfo) {
        int i = btDeviceInfo.mState;
        return i == 2 || i == 99;
    }

    public final int checkBleDeviceFormat(int i, int i2, BluetoothDevice bluetoothDevice) {
        int i3;
        if (bluetoothDevice == null || !bluetoothDevice.isLeAudioDualMode() || i == 536870914) {
            i3 = (isBleOutDevice(i) && i2 == 99) ? 8388608 : 0;
        } else {
            i3 = 4194304;
        }
        Log.i("AS.AudioDeviceInventory", "checkBleDeviceFormat format = " + i3);
        return i3;
    }

    public final boolean isBleOnlyOutDevice(boolean z) {
        return !this.mDeviceBroker.isDualModeActive() && z;
    }

    public boolean isBleHeadsetConnected(String str) {
        synchronized (this.mDevicesLock) {
            for (DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                if (str.equals(deviceInfo.mDeviceAddress) && deviceInfo.mDeviceType == 536870912) {
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: updateBtVolumeMonitor, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void lambda$onSetBtActiveDevice$13() {
        BluetoothA2dp a2dp = this.mDeviceBroker.getA2dp();
        BluetoothLeAudio leAudio = this.mDeviceBroker.getLeAudio();
        Boolean bool = Boolean.FALSE;
        if (a2dp != null) {
            List<BluetoothDevice> connectedDevices = a2dp.getConnectedDevices();
            if (connectedDevices.size() != 0) {
                Iterator<BluetoothDevice> it = connectedDevices.iterator();
                while (it.hasNext()) {
                    BtUtils.setBtVolumeMonitor(it.next());
                    bool = Boolean.TRUE;
                }
            }
        }
        if (leAudio != null) {
            List<BluetoothDevice> connectedDevices2 = leAudio.getConnectedDevices();
            if (connectedDevices2.size() != 0) {
                for (BluetoothDevice bluetoothDevice : connectedDevices2) {
                    if (isBleHeadsetConnected(bluetoothDevice.getAddress())) {
                        BtUtils.setBtVolumeMonitor(bluetoothDevice);
                        bool = Boolean.TRUE;
                    }
                }
            }
        }
        if (bool.booleanValue()) {
            return;
        }
        BtUtils.setBtVolumeMonitor(false);
    }
}
