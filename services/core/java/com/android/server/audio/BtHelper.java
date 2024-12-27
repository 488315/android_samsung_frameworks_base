package com.android.server.audio;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothCodecStatus;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothLeAudioCodecConfig;
import android.bluetooth.BluetoothLeAudioCodecStatus;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceAttributes;
import android.media.AudioSystem;
import android.media.BluetoothProfileConnectionInfo;
import android.media.audio.Flags;
import android.os.Binder;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.utils.EventLogger;
import com.android.server.vibrator.VibratorManagerInternal;

import com.samsung.android.audio.Rune;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.DualA2dpVolumeManager;
import com.samsung.android.server.audio.ScreenSharingHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class BtHelper {
    public BluetoothCodecConfig mA2dpCodecConfig;
    public BluetoothHeadset mBluetoothHeadset;
    public BluetoothDevice mBluetoothHeadsetDevice;
    public final Context mContext;
    public final AudioDeviceBroker mDeviceBroker;
    public BluetoothLeAudioCodecConfig mLeAudioCodecConfig;
    public BluetoothLeBroadcast mLeBroadcast;
    public int mScoAudioMode;
    public int mScoAudioState;
    public int mScoConnectionState;
    public VibratorManagerInternal mVibratorManagerInternal;
    public final Map mResolvedScoAudioDevices = new HashMap();
    public BluetoothHearingAid mHearingAid = null;
    public BluetoothLeAudio mLeAudio = null;
    public BluetoothA2dp mA2dp = null;
    public int mLeAudioBroadcastCodec = 0;
    public boolean mAvrcpAbsVolSupported = false;
    public MyLeAudioCallback mLeAudioCallback = null;
    public final AnonymousClass1 mBluetoothProfileServiceListener =
            new BluetoothProfile
                    .ServiceListener() { // from class: com.android.server.audio.BtHelper.1
                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                    if (i == 1 || i == 2 || i == 11 || i == 26 || i == 21 || i == 22) {
                        EventLogger eventLogger = AudioService.sDeviceLogger;
                        EventLogger.StringEvent stringEvent =
                                new EventLogger.StringEvent(
                                        "BT profile service: connecting "
                                                + BluetoothProfile.getProfileName(i)
                                                + " profile");
                        stringEvent.printLog(0, "AS.BtHelper");
                        eventLogger.enqueue(stringEvent);
                        BtHelper.this.mDeviceBroker.sendILMsgNoDelay(23, i, bluetoothProfile);
                    }
                }

                @Override // android.bluetooth.BluetoothProfile.ServiceListener
                public final void onServiceDisconnected(int i) {
                    if (i == 1 || i == 2 || i == 11 || i == 26 || i == 21 || i == 22) {
                        EventLogger eventLogger = AudioService.sDeviceLogger;
                        EventLogger.StringEvent stringEvent =
                                new EventLogger.StringEvent(
                                        "BT profile service: disconnecting "
                                                + BluetoothProfile.getProfileName(i)
                                                + " profile");
                        stringEvent.printLog(0, "AS.BtHelper");
                        eventLogger.enqueue(stringEvent);
                        BtHelper.this.mDeviceBroker.sendIMsgNoDelay(22, 2, i);
                    }
                }
            };
    public int mIsBtOffloadEnabled = 0;

    public final class MyLeAudioCallback implements BluetoothLeAudio.Callback {
        public MyLeAudioCallback() {}

        public final void onCodecConfigChanged(
                int i, BluetoothLeAudioCodecStatus bluetoothLeAudioCodecStatus) {}

        public final void onGroupNodeAdded(BluetoothDevice bluetoothDevice, int i) {
            BtHelper.this.mDeviceBroker.sendIMsgNoDelay(57, 2, i);
        }

        public final void onGroupNodeRemoved(BluetoothDevice bluetoothDevice, int i) {
            BtHelper.this.mDeviceBroker.sendIMsgNoDelay(57, 2, i);
        }

        public final void onGroupStatusChanged(int i, int i2) {
            BtHelper.this.mDeviceBroker.sendIMsgNoDelay(57, 2, i);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.audio.BtHelper$1] */
    public BtHelper(AudioDeviceBroker audioDeviceBroker, Context context) {
        this.mDeviceBroker = audioDeviceBroker;
        this.mContext = context;
    }

    public static AudioDeviceAttributes btHeadsetDeviceToAudioDevice(
            BluetoothDevice bluetoothDevice) {
        int i = 16;
        if (bluetoothDevice == null) {
            return new AudioDeviceAttributes(16, "");
        }
        String address = bluetoothDevice.getAddress();
        String name = bluetoothDevice.getName();
        if (name == null) {
            name = "";
        }
        String str = BluetoothAdapter.checkBluetoothAddress(address) ? address : "";
        BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            int deviceClass = bluetoothClass.getDeviceClass();
            if (deviceClass == 1028 || deviceClass == 1032) {
                i = 32;
            } else if (deviceClass == 1056) {
                i = 64;
            }
        }
        StringBuilder sb = new StringBuilder("btHeadsetDeviceToAudioDevice btDevice: ");
        sb.append(bluetoothDevice);
        sb.append(" btClass: ");
        Object obj = bluetoothClass;
        if (bluetoothClass == null) {
            obj = "Unknown";
        }
        sb.append(obj);
        sb.append(" nativeType: ");
        sb.append(i);
        sb.append(" address: ");
        sb.append(str);
        Log.i("AS.BtHelper", sb.toString());
        return new AudioDeviceAttributes(i, str, name);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getBtDeviceCategory(String str) {
        byte[] metadata;
        char c;
        if (!Flags.automaticBtDeviceType()) {
            return 0;
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice remoteDevice =
                (defaultAdapter == null || !BluetoothAdapter.checkBluetoothAddress(str))
                        ? null
                        : defaultAdapter.getRemoteDevice(str);
        if (remoteDevice == null || (metadata = remoteDevice.getMetadata(17)) == null) {
            return 0;
        }
        String str2 = new String(metadata);
        switch (str2.hashCode()) {
            case -1834993054:
                if (str2.equals("Headset")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1085510111:
                if (str2.equals("Default")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -672479864:
                if (str2.equals("HearingAid")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -343869473:
                if (str2.equals("Speaker")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 83350703:
                if (str2.equals("Watch")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 104438316:
                if (str2.equals("Untethered Headset")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2011237026:
                if (str2.equals("Carkit")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            return 6;
        }
        if (c == 1) {
            return 4;
        }
        if (c == 2 || c == 3) {
            return 3;
        }
        if (c == 4) {
            return 2;
        }
        if (c == 5) {
            return 5;
        }
        BluetoothClass bluetoothClass = remoteDevice.getBluetoothClass();
        if (bluetoothClass == null) {
            return 0;
        }
        int deviceClass = bluetoothClass.getDeviceClass();
        if (deviceClass != 1028) {
            if (deviceClass != 1044) {
                if (deviceClass != 1048) {
                    if (deviceClass != 1052) {
                        if (deviceClass == 1064) {
                            return 7;
                        }
                        if (deviceClass != 1084) {
                            return deviceClass != 1796 ? 0 : 5;
                        }
                    }
                }
            }
            return 2;
        }
        return 3;
    }

    public static String scoAudioModeToString(int i) {
        return i != -1
                ? i != 0
                        ? i != 2
                                ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        i, "SCO_MODE_(", ")")
                                : "SCO_MODE_VR"
                        : "SCO_MODE_VIRTUAL_CALL"
                : "SCO_MODE_UNDEFINED";
    }

    public final void broadcastScoConnectionState(int i) {
        this.mDeviceBroker.sendIMsgNoDelay(3, 2, i);
    }

    public final void checkScoAudioState() {
        try {
            BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
            if (bluetoothHeadset == null
                    || this.mScoAudioState != 0
                    || bluetoothHeadset.getAudioState(this.mBluetoothHeadsetDevice) == 10) {
                return;
            }
            this.mScoAudioState = 2;
        } catch (Exception e) {
            Log.e(
                    "AS.BtHelper",
                    "Exception while getting audio state of " + this.mBluetoothHeadsetDevice,
                    e);
        }
    }

    public final boolean getBluetoothHeadset() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        boolean profileProxy =
                defaultAdapter != null
                        ? defaultAdapter.getProfileProxy(
                                audioDeviceBroker.mContext,
                                this.mBluetoothProfileServiceListener,
                                1)
                        : false;
        audioDeviceBroker.sendIILMsg(9, 0, 0, 0, null, profileProxy ? 3000 : 0);
        return profileProxy;
    }

    public final synchronized Pair getCodec(BluetoothDevice bluetoothDevice, int i) {
        BluetoothCodecStatus bluetoothCodecStatus;
        BluetoothLeAudioCodecStatus bluetoothLeAudioCodecStatus;
        boolean z = true;
        if (this.mIsBtOffloadEnabled == 1) {
            return new Pair(2097152, Boolean.TRUE);
        }
        if (i == 2) {
            boolean z2 = this.mA2dpCodecConfig != null;
            BluetoothA2dp bluetoothA2dp = this.mA2dp;
            if (bluetoothA2dp == null) {
                this.mA2dpCodecConfig = null;
                return new Pair(0, Boolean.valueOf(z2));
            }
            try {
                bluetoothCodecStatus = bluetoothA2dp.getCodecStatus(bluetoothDevice);
            } catch (Exception e) {
                Log.e("AS.BtHelper", "Exception while getting status of " + bluetoothDevice, e);
                bluetoothCodecStatus = null;
            }
            if (bluetoothCodecStatus == null) {
                Log.e(
                        "AS.BtHelper",
                        "getCodec, null A2DP codec status for device: " + bluetoothDevice);
                this.mA2dpCodecConfig = null;
                return new Pair(0, Boolean.valueOf(z2));
            }
            BluetoothCodecConfig codecConfig = bluetoothCodecStatus.getCodecConfig();
            if (codecConfig == null) {
                this.mA2dpCodecConfig = null;
                return new Pair(0, Boolean.valueOf(z2));
            }
            boolean z3 = !codecConfig.equals(this.mA2dpCodecConfig);
            this.mA2dpCodecConfig = codecConfig;
            return new Pair(
                    Integer.valueOf(
                            AudioSystem.bluetoothA2dpCodecToAudioFormat(
                                    codecConfig.getCodecType())),
                    Boolean.valueOf(z3));
        }
        if (i != 22) {
            if (i != 26) {
                return new Pair(0, Boolean.FALSE);
            }
            if (this.mLeAudioBroadcastCodec == 721420288) {
                z = false;
            }
            this.mLeAudioBroadcastCodec = 721420288;
            return new Pair(Integer.valueOf(this.mLeAudioBroadcastCodec), Boolean.valueOf(z));
        }
        boolean z4 = this.mLeAudioCodecConfig != null;
        BluetoothLeAudio bluetoothLeAudio = this.mLeAudio;
        if (bluetoothLeAudio == null) {
            this.mLeAudioCodecConfig = null;
            return new Pair(0, Boolean.valueOf(z4));
        }
        try {
            bluetoothLeAudioCodecStatus =
                    this.mLeAudio.getCodecStatus(bluetoothLeAudio.getGroupId(bluetoothDevice));
        } catch (Exception e2) {
            Log.e("AS.BtHelper", "Exception while getting status of " + bluetoothDevice, e2);
            bluetoothLeAudioCodecStatus = null;
        }
        if (bluetoothLeAudioCodecStatus == null) {
            Log.e("AS.BtHelper", "getCodec, null LE codec status for device: " + bluetoothDevice);
            this.mLeAudioCodecConfig = null;
            return new Pair(0, Boolean.valueOf(z4));
        }
        BluetoothLeAudioCodecConfig outputCodecConfig =
                bluetoothLeAudioCodecStatus.getOutputCodecConfig();
        if (outputCodecConfig == null) {
            this.mLeAudioCodecConfig = null;
            return new Pair(0, Boolean.valueOf(z4));
        }
        boolean z5 = !outputCodecConfig.equals(this.mLeAudioCodecConfig);
        this.mLeAudioCodecConfig = outputCodecConfig;
        return new Pair(
                Integer.valueOf(
                        AudioSystem.bluetoothLeCodecToAudioFormat(
                                outputCodecConfig.getCodecType())),
                Boolean.valueOf(z5));
    }

    public final synchronized Pair getCodecWithFallback(
            BluetoothDevice bluetoothDevice, int i, boolean z, String str) {
        if (i != 2 && (!z || (i != 22 && i != 26))) {
            return new Pair(0, Boolean.FALSE);
        }
        Pair codec = getCodec(bluetoothDevice, i);
        if (((Integer) codec.first).intValue() != 0) {
            return codec;
        }
        EventLogger eventLogger = AudioService.sDeviceLogger;
        StringBuilder sb = new StringBuilder("getCodec DEFAULT from ");
        sb.append(str);
        sb.append(" fallback to ");
        sb.append(i == 2 ? "SBC" : "LC3");
        eventLogger.enqueue(new EventLogger.StringEvent(sb.toString()));
        return new Pair(Integer.valueOf(i == 2 ? 520093696 : 721420288), Boolean.TRUE);
    }

    public final boolean handleBtScoActiveDeviceChange(BluetoothDevice bluetoothDevice, boolean z) {
        boolean z2;
        if (bluetoothDevice == null) {
            return true;
        }
        AudioDeviceAttributes btHeadsetDeviceToAudioDevice =
                btHeadsetDeviceToAudioDevice(bluetoothDevice);
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        if (z) {
            z2 =
                    audioDeviceBroker.handleDeviceConnection(
                            btHeadsetDeviceToAudioDevice, z, bluetoothDevice);
        } else {
            int[] iArr = {16, 32, 64};
            boolean z3 = false;
            for (int i = 0; i < 3; i++) {
                z3 |=
                        audioDeviceBroker.handleDeviceConnection(
                                new AudioDeviceAttributes(
                                        iArr[i],
                                        btHeadsetDeviceToAudioDevice.getAddress(),
                                        btHeadsetDeviceToAudioDevice.getName()),
                                z,
                                bluetoothDevice);
            }
            z2 = z3;
        }
        boolean z4 =
                audioDeviceBroker.handleDeviceConnection(
                                new AudioDeviceAttributes(
                                        -2147483640,
                                        btHeadsetDeviceToAudioDevice.getAddress(),
                                        btHeadsetDeviceToAudioDevice.getName()),
                                z,
                                bluetoothDevice)
                        && z2;
        if (z4) {
            if (z) {
                ((HashMap) this.mResolvedScoAudioDevices)
                        .put(bluetoothDevice, btHeadsetDeviceToAudioDevice);
            } else {
                ((HashMap) this.mResolvedScoAudioDevices).remove(bluetoothDevice);
            }
        }
        return z4;
    }

    public final synchronized boolean isBluetoothScoOn() {
        BluetoothDevice bluetoothDevice;
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
        if (bluetoothHeadset == null || (bluetoothDevice = this.mBluetoothHeadsetDevice) == null) {
            return false;
        }
        try {
            return bluetoothHeadset.getAudioState(bluetoothDevice) == 12;
        } catch (Exception e) {
            Log.e(
                    "AS.BtHelper",
                    "Exception while getting audio state of " + this.mBluetoothHeadsetDevice,
                    e);
            return false;
        }
    }

    public final synchronized boolean isBluetoothScoSupported() {
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
        if (bluetoothHeadset != null
                && !bluetoothHeadset
                        .getDevicesMatchingConnectionStates(new int[] {2, 1, 3})
                        .isEmpty()) {
            BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
            if (bluetoothDevice == null) {
                return false;
            }
            int audioState = this.mBluetoothHeadset.getAudioState(bluetoothDevice);
            if (audioState != 11 && audioState != 12) {
                Log.e(
                        "AS.BtHelper",
                        "setBluetoothScoOn() wrong sco state:"
                                + audioState
                                + " mScoAudioState:"
                                + this.mScoAudioState);
                return false;
            }
        }
        return true;
    }

    public final synchronized boolean isProfilePoxyConnected(int i) {
        if (i == 1) {
            return this.mBluetoothHeadset != null;
        }
        if (i == 2) {
            return this.mA2dp != null;
        }
        if (i == 21) {
            return this.mHearingAid != null;
        }
        if (i != 22) {
            return true;
        }
        return this.mLeAudio != null;
    }

    public final synchronized void onAudioServerDiedRestoreA2dp() {
        this.mDeviceBroker.setForceUse_Async(
                1, this.mDeviceBroker.mBluetoothA2dpEnabled.get() ? 0 : 10, "onAudioServerDied()");
    }

    public final synchronized void onBroadcastScoConnectionState(int i) {
        if (i == this.mScoConnectionState) {
            return;
        }
        Intent intent = new Intent("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
        intent.putExtra("android.media.extra.SCO_AUDIO_STATE", i);
        intent.putExtra("android.media.extra.SCO_AUDIO_PREVIOUS_STATE", this.mScoConnectionState);
        sendStickyBroadcastToAll(intent);
        this.mScoConnectionState = i;
    }

    public final synchronized void onBtProfileConnected(int i, BluetoothProfile bluetoothProfile) {
        MyLeAudioCallback myLeAudioCallback;
        EventLogger eventLogger = AudioService.sDeviceLogger;
        EventLogger.StringEvent stringEvent =
                new EventLogger.StringEvent(
                        "BT profile "
                                + BluetoothProfile.getProfileName(i)
                                + " connected to proxy "
                                + bluetoothProfile);
        stringEvent.printLog(0, "AS.BtHelper");
        eventLogger.enqueue(stringEvent);
        if (bluetoothProfile == null) {
            Log.e("AS.BtHelper", "onBtProfileConnected: null proxy for profile: " + i);
            return;
        }
        if (i == 1) {
            onHeadsetProfileConnected((BluetoothHeadset) bluetoothProfile);
            return;
        }
        BluetoothLeAudioCodecStatus bluetoothLeAudioCodecStatus = null;
        if (i != 2) {
            if (i == 11 || i == 26) {
                if (i == 26) {
                    this.mLeBroadcast = (BluetoothLeBroadcast) bluetoothProfile;
                }
                return;
            }
            if (i != 21) {
                if (i != 22) {
                    Log.e(
                            "AS.BtHelper",
                            "onBtProfileConnected: Not a valid profile to connect "
                                    + BluetoothProfile.getProfileName(i));
                    return;
                } else {
                    if (((BluetoothLeAudio) bluetoothProfile).equals(this.mLeAudio)) {
                        return;
                    }
                    BluetoothLeAudio bluetoothLeAudio = this.mLeAudio;
                    if (bluetoothLeAudio != null
                            && (myLeAudioCallback = this.mLeAudioCallback) != null) {
                        try {
                            bluetoothLeAudio.unregisterCallback(myLeAudioCallback);
                        } catch (Exception e) {
                            Log.e(
                                    "AS.BtHelper",
                                    "Exception while unregistering callback for LE audio",
                                    e);
                        }
                    }
                    BluetoothLeAudio bluetoothLeAudio2 = (BluetoothLeAudio) bluetoothProfile;
                    this.mLeAudio = bluetoothLeAudio2;
                    this.mLeAudioCallback = new MyLeAudioCallback();
                    try {
                        bluetoothLeAudio2.registerCallback(
                                this.mContext.getMainExecutor(), this.mLeAudioCallback);
                    } catch (Exception e2) {
                        this.mLeAudioCallback = null;
                        Log.e(
                                "AS.BtHelper",
                                "Exception while registering callback for LE audio",
                                e2);
                    }
                }
            } else if (((BluetoothHearingAid) bluetoothProfile).equals(this.mHearingAid)) {
                return;
            } else {
                this.mHearingAid = (BluetoothHearingAid) bluetoothProfile;
            }
        } else if (((BluetoothA2dp) bluetoothProfile).equals(this.mA2dp)) {
            return;
        } else {
            this.mA2dp = (BluetoothA2dp) bluetoothProfile;
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.e(
                    "AS.BtHelper",
                    "onBtProfileConnected: Null BluetoothAdapter when connecting profile: "
                            + BluetoothProfile.getProfileName(i));
            return;
        }
        List activeDevices = defaultAdapter.getActiveDevices(i);
        if (!activeDevices.isEmpty() && activeDevices.get(0) != null) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) activeDevices.get(0);
            if (i == 2) {
                postBluetoothActiveDevice(
                        bluetoothDevice, BluetoothProfileConnectionInfo.createA2dpInfo(false, -1));
            } else if (i == 21) {
                postBluetoothActiveDevice(
                        bluetoothDevice,
                        BluetoothProfileConnectionInfo.createHearingAidInfo(false));
            } else if (i != 22) {
                Log.wtf("AS.BtHelper", "Invalid profile! onBtProfileConnected");
            } else {
                int groupId = this.mLeAudio.getGroupId(bluetoothDevice);
                try {
                    bluetoothLeAudioCodecStatus = this.mLeAudio.getCodecStatus(groupId);
                } catch (Exception e3) {
                    Log.e(
                            "AS.BtHelper",
                            "Exception while getting status of " + bluetoothDevice,
                            e3);
                }
                if (bluetoothLeAudioCodecStatus == null) {
                    Log.i(
                            "AS.BtHelper",
                            "onBtProfileConnected null LE codec status for groupId: "
                                    + groupId
                                    + ", device: "
                                    + bluetoothDevice);
                } else {
                    if (!bluetoothLeAudioCodecStatus
                            .getOutputCodecSelectableCapabilities()
                            .isEmpty()) {
                        postBluetoothActiveDevice(
                                bluetoothDevice,
                                BluetoothProfileConnectionInfo.createLeAudioInfo(false, true));
                    }
                    if (!bluetoothLeAudioCodecStatus
                            .getInputCodecSelectableCapabilities()
                            .isEmpty()) {
                        postBluetoothActiveDevice(
                                bluetoothDevice,
                                BluetoothProfileConnectionInfo.createLeAudioInfo(false, false));
                    }
                }
            }
        }
    }

    public final synchronized void onBtProfileDisconnected(int i) {
        MyLeAudioCallback myLeAudioCallback;
        try {
            EventLogger eventLogger = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent =
                    new EventLogger.StringEvent(
                            "BT profile " + BluetoothProfile.getProfileName(i) + " disconnected");
            stringEvent.printLog(0, "AS.BtHelper");
            eventLogger.enqueue(stringEvent);
            if (i == 1) {
                this.mBluetoothHeadset = null;
            } else if (i == 2) {
                this.mA2dp = null;
                this.mA2dpCodecConfig = null;
            } else if (i != 11) {
                if (i == 26) {
                    this.mLeAudioBroadcastCodec = 0;
                } else if (i == 21) {
                    this.mHearingAid = null;
                } else if (i != 22) {
                    Log.e(
                            "AS.BtHelper",
                            "onBtProfileDisconnected: Not a valid profile to disconnect "
                                    + BluetoothProfile.getProfileName(i));
                } else {
                    BluetoothLeAudio bluetoothLeAudio = this.mLeAudio;
                    if (bluetoothLeAudio != null
                            && (myLeAudioCallback = this.mLeAudioCallback) != null) {
                        try {
                            bluetoothLeAudio.unregisterCallback(myLeAudioCallback);
                        } catch (Exception e) {
                            Log.e(
                                    "AS.BtHelper",
                                    "Exception while unregistering callback for LE audio",
                                    e);
                        }
                    }
                    this.mLeAudio = null;
                    this.mLeAudioCallback = null;
                    this.mLeAudioCodecConfig = null;
                }
            } else if (i == 26) {
                this.mLeBroadcast = null;
            } else if (i == 11) {
                Log.e(
                        "AS.BtHelper",
                        "onBtProfileDisconnected: Not a profile handled by BtHelper "
                                + BluetoothProfile.getProfileName(i));
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onHeadsetProfileConnected(android.bluetooth.BluetoothHeadset r6) {
        /*
            r5 = this;
            com.android.server.audio.AudioDeviceBroker r0 = r5.mDeviceBroker
            com.android.server.audio.AudioDeviceBroker$BrokerHandler r0 = r0.mBrokerHandler
            r1 = 9
            r0.removeMessages(r1)
            r5.mBluetoothHeadset = r6
            android.bluetooth.BluetoothAdapter r6 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            r0 = 1
            if (r6 == 0) goto L2d
            java.util.List r6 = r6.getActiveDevices(r0)
            java.util.Iterator r6 = r6.iterator()
        L1a:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L35
            java.lang.Object r1 = r6.next()
            android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
            if (r1 != 0) goto L29
            goto L1a
        L29:
            r5.onSetBtScoActiveDevice(r1)
            goto L1a
        L2d:
            java.lang.String r6 = "AS.BtHelper"
            java.lang.String r1 = "onHeadsetProfileConnected: Null BluetoothAdapter"
            android.util.Log.e(r6, r1)
        L35:
            r5.checkScoAudioState()
            int r6 = r5.mScoAudioState
            r1 = 4
            if (r6 == r0) goto L40
            if (r6 == r1) goto L40
            return
        L40:
            android.bluetooth.BluetoothDevice r2 = r5.mBluetoothHeadsetDevice
            r3 = 0
            if (r2 == 0) goto L7d
            r4 = 2
            if (r6 == r0) goto L64
            if (r6 == r1) goto L4b
            goto L7d
        L4b:
            android.bluetooth.BluetoothHeadset r6 = r5.mBluetoothHeadset
            int r0 = r5.mScoAudioMode
            if (r0 == 0) goto L5a
            if (r0 == r4) goto L55
            r6 = r3
            goto L5e
        L55:
            boolean r6 = r6.stopVoiceRecognition(r2)
            goto L5e
        L5a:
            boolean r6 = r6.stopScoUsingVirtualVoiceCall()
        L5e:
            if (r6 == 0) goto L7e
            r0 = 5
            r5.mScoAudioState = r0
            goto L7e
        L64:
            android.bluetooth.BluetoothHeadset r6 = r5.mBluetoothHeadset
            int r0 = r5.mScoAudioMode
            if (r0 == 0) goto L73
            if (r0 == r4) goto L6e
            r6 = r3
            goto L77
        L6e:
            boolean r6 = r6.startVoiceRecognition(r2)
            goto L77
        L73:
            boolean r6 = r6.startScoUsingVirtualVoiceCall()
        L77:
            if (r6 == 0) goto L7e
            r0 = 3
            r5.mScoAudioState = r0
            goto L7e
        L7d:
            r6 = r3
        L7e:
            if (r6 != 0) goto L85
            r5.mScoAudioState = r3
            r5.broadcastScoConnectionState(r3)
        L85:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.BtHelper.onHeadsetProfileConnected(android.bluetooth.BluetoothHeadset):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void onScoAudioStateChanged(int i) {
        BluetoothHeadset bluetoothHeadset;
        BluetoothDevice bluetoothDevice;
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        int i2 = 0;
        if (!audioDeviceBroker.mScoManagedByAudio) {
            switch (i) {
                case 10:
                    audioDeviceBroker.setBluetoothScoOn("BtHelper.onScoAudioStateChanged", false);
                    audioDeviceBroker.muteRingtoneDuringVibration();
                    if (this.mScoAudioState == 1
                            && (bluetoothHeadset = this.mBluetoothHeadset) != null
                            && (bluetoothDevice = this.mBluetoothHeadsetDevice) != null) {
                        int i3 = this.mScoAudioMode;
                        if (i3 != 0
                                ? i3 != 2
                                        ? false
                                        : bluetoothHeadset.startVoiceRecognition(bluetoothDevice)
                                : bluetoothHeadset.startScoUsingVirtualVoiceCall()) {
                            this.mScoAudioState = 3;
                            i2 = 1;
                            r4 = 2;
                            break;
                        }
                    }
                    r4 = this.mScoAudioState == 2 ? 0 : 1;
                    this.mScoAudioState = 0;
                    int i4 = r4;
                    r4 = 0;
                    i2 = i4;
                    break;
                case 11:
                    int i5 = this.mScoAudioState;
                    if (i5 != 3 && i5 != 4 && i5 != 5) {
                        this.mScoAudioState = 2;
                    }
                    r4 = -1;
                    break;
                case 12:
                    int i6 = this.mScoAudioState;
                    if (i6 != 3 && i6 != 4) {
                        this.mScoAudioState = 2;
                    } else if (audioDeviceBroker.isDeviceRequestedForCommunication(7)) {
                        if (Rune.SEC_AUDIO_REMOTE_MIC
                                && Settings.System.getIntForUser(
                                                audioDeviceBroker.mContext.getContentResolver(),
                                                "run_amplify_ambient_sound",
                                                0,
                                                -2)
                                        == 2) {
                            Intent intent =
                                    new Intent(
                                            "android.samsung.media.action.ACTION_AUDIO_REMOTEMIC_SCO_RESUME");
                            intent.addFlags(67108864);
                            sendStickyBroadcastToAll(intent);
                            Log.i("AS.BtHelper", "broadcast remote mic resume intent");
                        }
                        i2 = 1;
                    }
                    audioDeviceBroker.setBluetoothScoOn("BtHelper.onScoAudioStateChanged", true);
                    break;
                default:
                    r4 = -1;
                    break;
            }
        } else if (i != 10) {
            if (i == 12) {
                audioDeviceBroker.setBluetoothScoOn("BtHelper.onScoAudioStateChanged", true);
                i2 = 1;
            }
            r4 = -1;
        } else {
            audioDeviceBroker.muteRingtoneDuringVibration();
            audioDeviceBroker.setBluetoothScoOn("BtHelper.onScoAudioStateChanged", false);
            int i42 = r4;
            r4 = 0;
            i2 = i42;
        }
        if (i2 != 0) {
            broadcastScoConnectionState(r4);
            Intent intent2 = new Intent("android.media.SCO_AUDIO_STATE_CHANGED");
            intent2.putExtra("android.media.extra.SCO_AUDIO_STATE", r4);
            sendStickyBroadcastToAll(intent2);
        }
    }

    public final synchronized void onSetBtScoActiveDevice(BluetoothDevice bluetoothDevice) {
        try {
            StringBuilder sb = new StringBuilder("onSetBtScoActiveDevice: ");
            BluetoothDevice bluetoothDevice2 = this.mBluetoothHeadsetDevice;
            sb.append(
                    bluetoothDevice2 == null ? "(null)" : bluetoothDevice2.getAnonymizedAddress());
            sb.append(" -> ");
            sb.append(bluetoothDevice == null ? "(null)" : bluetoothDevice.getAnonymizedAddress());
            Log.i("AS.BtHelper", sb.toString());
            BluetoothDevice bluetoothDevice3 = this.mBluetoothHeadsetDevice;
            if (Objects.equals(bluetoothDevice, bluetoothDevice3)) {
                return;
            }
            if (bluetoothDevice != null && bluetoothDevice3 != null) {
                Log.i("AS.BtHelper", "setBtScoActiveDevice muteRingtoneDuringVibration");
                this.mDeviceBroker.muteRingtoneDuringVibration();
            }
            if (!handleBtScoActiveDeviceChange(bluetoothDevice3, false)) {
                StringBuilder sb2 =
                        new StringBuilder(
                                "onSetBtScoActiveDevice() failed to remove previous device ");
                sb2.append(
                        bluetoothDevice3 == null
                                ? "(null)"
                                : bluetoothDevice3.getAnonymizedAddress());
                Log.w("AS.BtHelper", sb2.toString());
            }
            if (!handleBtScoActiveDeviceChange(bluetoothDevice, true)) {
                StringBuilder sb3 =
                        new StringBuilder("onSetBtScoActiveDevice() failed to add new device ");
                sb3.append(
                        bluetoothDevice == null
                                ? "(null)"
                                : bluetoothDevice.getAnonymizedAddress());
                Log.e("AS.BtHelper", sb3.toString());
                bluetoothDevice = null;
            }
            this.mBluetoothHeadsetDevice = bluetoothDevice;
            if (bluetoothDevice == null) {
                resetBluetoothSco();
            }
            if (bluetoothDevice3 == null && this.mBluetoothHeadsetDevice != null) {
                this.mDeviceBroker.resetBtScoOnByApp();
            }
            if (this.mBluetoothHeadsetDevice != null && ScreenSharingHelper.sSplitSoundEnabled) {
                ScreenSharingHelper.sSplitSoundEnabled = false;
                SemAudioSystem.setPolicyParameters(
                        "l_smart_view_split_sound_enable="
                                + ScreenSharingHelper.sSplitSoundEnabled);
                this.mDeviceBroker.sendIMsgNoDelay(12, 0, 32);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void onSystemReady() {
        try {
            this.mScoConnectionState = -1;
            resetBluetoothSco();
            getBluetoothHeadset();
            Intent intent = new Intent("android.media.SCO_AUDIO_STATE_CHANGED");
            intent.putExtra("android.media.extra.SCO_AUDIO_STATE", 0);
            sendStickyBroadcastToAll(intent);
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                defaultAdapter.getProfileProxy(
                        this.mDeviceBroker.mContext, this.mBluetoothProfileServiceListener, 2);
                defaultAdapter.getProfileProxy(
                        this.mDeviceBroker.mContext, this.mBluetoothProfileServiceListener, 11);
                defaultAdapter.getProfileProxy(
                        this.mDeviceBroker.mContext, this.mBluetoothProfileServiceListener, 21);
                defaultAdapter.getProfileProxy(
                        this.mDeviceBroker.mContext, this.mBluetoothProfileServiceListener, 22);
                defaultAdapter.getProfileProxy(
                        this.mDeviceBroker.mContext, this.mBluetoothProfileServiceListener, 26);
            }
            this.mVibratorManagerInternal =
                    (VibratorManagerInternal)
                            LocalServices.getService(VibratorManagerInternal.class);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void postBluetoothActiveDevice(
            BluetoothDevice bluetoothDevice,
            BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) {
        AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData =
                new AudioDeviceBroker.BtDeviceChangedData(
                        bluetoothDevice,
                        null,
                        bluetoothProfileConnectionInfo,
                        "mBluetoothProfileServiceListener");
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        audioDeviceBroker.getClass();
        audioDeviceBroker.sendIILMsg(
                7,
                2,
                0,
                0,
                AudioDeviceBroker.createBtDeviceInfo(btDeviceChangedData, bluetoothDevice, 2),
                0);
    }

    public final boolean requestScoState(int i, int i2) {
        checkScoAudioState();
        if (i == 12) {
            if (this.mScoAudioState != 3) {
                broadcastScoConnectionState(2);
            }
            int i3 = this.mScoAudioState;
            if (i3 == 0) {
                this.mScoAudioMode = i2;
                if (i2 == -1) {
                    this.mScoAudioMode = 0;
                    if (this.mBluetoothHeadsetDevice != null) {
                        int i4 =
                                Settings.Global.getInt(
                                        this.mDeviceBroker.mAudioService.mContentResolver,
                                        "bluetooth_sco_channel_"
                                                + this.mBluetoothHeadsetDevice.getAddress(),
                                        0);
                        this.mScoAudioMode = i4;
                        if (i4 > 2 || i4 < 0) {
                            this.mScoAudioMode = 0;
                        }
                    }
                }
                BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
                if (bluetoothHeadset != null) {
                    BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
                    if (bluetoothDevice == null) {
                        Log.w(
                                "AS.BtHelper",
                                "requestScoState: no active device while connecting, mScoAudioMode="
                                        + this.mScoAudioMode);
                        broadcastScoConnectionState(0);
                        return false;
                    }
                    int i5 = this.mScoAudioMode;
                    if (!(i5 != 0
                            ? i5 != 2
                                    ? false
                                    : bluetoothHeadset.startVoiceRecognition(bluetoothDevice)
                            : bluetoothHeadset.startScoUsingVirtualVoiceCall())) {
                        StringBuilder sb = new StringBuilder("requestScoState: connect to ");
                        BluetoothDevice bluetoothDevice2 = this.mBluetoothHeadsetDevice;
                        sb.append(
                                bluetoothDevice2 == null
                                        ? "(null)"
                                        : bluetoothDevice2.getAnonymizedAddress());
                        sb.append(" failed, mScoAudioMode=");
                        sb.append(this.mScoAudioMode);
                        Log.w("AS.BtHelper", sb.toString());
                        broadcastScoConnectionState(0);
                        return false;
                    }
                    this.mScoAudioState = 3;
                } else {
                    if (!getBluetoothHeadset()) {
                        Log.w(
                                "AS.BtHelper",
                                "requestScoState: getBluetoothHeadset failed during connection,"
                                    + " mScoAudioMode="
                                        + this.mScoAudioMode);
                        broadcastScoConnectionState(0);
                        return false;
                    }
                    this.mScoAudioState = 1;
                }
            } else if (i3 == 2) {
                broadcastScoConnectionState(1);
            } else if (i3 != 3) {
                if (i3 == 4) {
                    this.mScoAudioState = 3;
                    broadcastScoConnectionState(1);
                } else {
                    if (i3 != 5) {
                        Log.w(
                                "AS.BtHelper",
                                "requestScoState: failed to connect in state "
                                        + this.mScoAudioState
                                        + ", scoAudioMode="
                                        + i2);
                        broadcastScoConnectionState(0);
                        return false;
                    }
                    this.mScoAudioState = 1;
                }
            }
        } else if (i == 10) {
            int i6 = this.mScoAudioState;
            if (i6 == 1) {
                this.mScoAudioState = 0;
                broadcastScoConnectionState(0);
            } else {
                if (i6 != 3) {
                    Log.w(
                            "AS.BtHelper",
                            "requestScoState: failed to disconnect in state "
                                    + this.mScoAudioState
                                    + ", scoAudioMode="
                                    + i2);
                    broadcastScoConnectionState(0);
                    return false;
                }
                BluetoothHeadset bluetoothHeadset2 = this.mBluetoothHeadset;
                if (bluetoothHeadset2 != null) {
                    BluetoothDevice bluetoothDevice3 = this.mBluetoothHeadsetDevice;
                    if (bluetoothDevice3 == null) {
                        this.mScoAudioState = 0;
                        broadcastScoConnectionState(0);
                    } else {
                        int i7 = this.mScoAudioMode;
                        if (i7 != 0
                                ? i7 != 2
                                        ? false
                                        : bluetoothHeadset2.stopVoiceRecognition(bluetoothDevice3)
                                : bluetoothHeadset2.stopScoUsingVirtualVoiceCall()) {
                            this.mScoAudioState = 5;
                        } else {
                            this.mScoAudioState = 0;
                            broadcastScoConnectionState(0);
                        }
                    }
                } else {
                    if (!getBluetoothHeadset()) {
                        AudioService$$ExternalSyntheticOutline0.m(
                                new StringBuilder(
                                        "requestScoState: getBluetoothHeadset failed during"
                                            + " disconnection, mScoAudioMode="),
                                this.mScoAudioMode,
                                "AS.BtHelper");
                        this.mScoAudioState = 0;
                        broadcastScoConnectionState(0);
                        return false;
                    }
                    this.mScoAudioState = 4;
                }
            }
        }
        return true;
    }

    public final synchronized void resetBluetoothSco() {
        this.mScoAudioState = 0;
        broadcastScoConnectionState(0);
        this.mDeviceBroker.clearA2dpSuspended(false);
        this.mDeviceBroker.clearLeAudioSuspended(false);
        this.mDeviceBroker.setBluetoothScoOn("resetBluetoothSco", false);
    }

    public final void sendStickyBroadcastToAll(Intent intent) {
        intent.addFlags(268435456);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final synchronized void setAvrcpAbsoluteVolumeIndex(int i) {
        if (this.mA2dp == null) {
            EventLogger eventLogger = AudioService.sVolumeLogger;
            EventLogger.StringEvent stringEvent =
                    new EventLogger.StringEvent(
                            "setAvrcpAbsoluteVolumeIndex: bailing due to null mA2dp");
            stringEvent.printLog(0, "AS.BtHelper");
            eventLogger.enqueue(stringEvent);
            return;
        }
        if (!this.mAvrcpAbsVolSupported) {
            EventLogger eventLogger2 = AudioService.sVolumeLogger;
            EventLogger.StringEvent stringEvent2 =
                    new EventLogger.StringEvent(
                            "setAvrcpAbsoluteVolumeIndex: abs vol not supported ");
            stringEvent2.printLog(0, "AS.BtHelper");
            eventLogger2.enqueue(stringEvent2);
            return;
        }
        Log.i("AS.BtHelper", "setAvrcpAbsoluteVolumeIndex index=" + i);
        AudioService.sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(i));
        setAvrcpAbsoluteVolumeIndexExt(i);
    }

    public final void setAvrcpAbsoluteVolumeIndexExt(int i) {
        ArrayMap arrayMap;
        DualA2dpVolumeManager dualA2dpVolumeManager = this.mDeviceBroker.mDualA2dpManager;
        synchronized (dualA2dpVolumeManager.mConnectedDevicesVolume) {
            try {
                arrayMap = new ArrayMap();
                if (dualA2dpVolumeManager.mDualModeEnabled) {
                    arrayMap.putAll(dualA2dpVolumeManager.mConnectedDevicesVolume);
                } else {
                    Integer num =
                            (Integer)
                                    dualA2dpVolumeManager.mConnectedDevicesVolume.getOrDefault(
                                            dualA2dpVolumeManager.mActiveDevice, -1);
                    if (num.intValue() != -1) {
                        arrayMap.put(dualA2dpVolumeManager.mActiveDevice, num);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (arrayMap.size() == 0) {
            Log.i("AS.BtHelper", "No a2dp volume info");
            this.mA2dp.setA2dpMediaVolume(null, DualA2dpVolumeManager.FINE_VOLUME_TABLE[i], i);
            return;
        }
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) arrayMap.keyAt(i2);
            int intValue = ((Integer) arrayMap.valueAt(i2)).intValue();
            this.mA2dp.setA2dpMediaVolume(
                    bluetoothDevice, DualA2dpVolumeManager.FINE_VOLUME_TABLE[intValue], intValue);
        }
    }

    public final synchronized void setAvrcpAbsoluteVolumeSupported(boolean z) {
        this.mAvrcpAbsVolSupported = z;
        Log.i("AS.BtHelper", "setAvrcpAbsoluteVolumeSupported supported=" + z);
    }

    public final synchronized void setHearingAidVolume(int i, int i2, boolean z) {
        if (this.mHearingAid == null) {
            Log.i("AS.BtHelper", "setHearingAidVolume: null mHearingAid");
            return;
        }
        int streamVolumeDB = (int) AudioSystem.getStreamVolumeDB(i2, i / 10, 134217728);
        if (streamVolumeDB < -128) {
            streamVolumeDB = -128;
        }
        Log.i(
                "AS.BtHelper",
                "setHearingAidVolume: calling mHearingAid.setVolume idx="
                        + i
                        + " gain="
                        + streamVolumeDB);
        if (z) {
            AudioService.sVolumeLogger.enqueue(
                    new AudioServiceEvents$VolumeEvent(i, streamVolumeDB));
        }
        try {
            this.mHearingAid.setVolume(streamVolumeDB);
        } catch (Exception e) {
            Log.i("AS.BtHelper", "Exception while setting hearing aid volume", e);
        }
    }

    public final synchronized void setLeAudioVolume(int i, int i2, int i3) {
        if (this.mLeAudio == null) {
            Log.i("AS.BtHelper", "setLeAudioVolume: null mLeAudio");
            return;
        }
        int round = (int) Math.round((i * 255.0d) / i2);
        Log.i(
                "AS.BtHelper",
                "setLeAudioVolume: calling mLeAudio.setVolume idx=" + i + " volume=" + round);
        AudioService.sVolumeLogger.enqueue(
                new AudioServiceEvents$VolumeEvent(10, i3, i, i2, (String) null));
        try {
            this.mLeAudio.setVolume(round);
        } catch (Exception e) {
            Log.e("AS.BtHelper", "Exception while setting LE volume", e);
        }
    }

    public final synchronized void stopBluetoothSco(String str) {
        AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent(str));
        requestScoState(10, 0);
    }
}
