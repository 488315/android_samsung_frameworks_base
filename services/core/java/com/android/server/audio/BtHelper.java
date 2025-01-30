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
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceAttributes;
import android.media.AudioSystem;
import android.media.BluetoothProfileConnectionInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.server.LocalServices;
import com.android.server.audio.AudioDeviceBroker;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import com.android.server.vibrator.VibratorManagerInternal;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.DualA2dpVolumeManager;
import com.samsung.android.server.audio.ScreenSharingHelper;
import com.samsung.android.server.audio.utils.BtUtils;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class BtHelper {
    public BluetoothA2dp mA2dp;
    public BluetoothHeadset mBluetoothHeadset;
    public BluetoothDevice mBluetoothHeadsetDevice;
    public final AudioDeviceBroker mDeviceBroker;
    public BluetoothHearingAid mHearingAid;
    public BluetoothLeAudio mLeAudio;
    public BluetoothLeBroadcast mLeBroadcast;
    public int mScoAudioMode;
    public int mScoAudioState;
    public int mScoConnectionState;
    public VibratorManagerInternal mVibratorManagerInternal;
    public boolean mAvrcpAbsVolSupported = false;
    public BluetoothProfile.ServiceListener mBluetoothProfileServiceListener = new BluetoothProfile.ServiceListener() { // from class: com.android.server.audio.BtHelper.1
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (i == 1 || i == 2 || i == 21 || i == 22 || i == 26) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("BT profile service: connecting " + BluetoothProfile.getProfileName(i) + " profile"));
                BtHelper.this.mDeviceBroker.postBtProfileConnected(i, bluetoothProfile);
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (i == 1 || i == 2 || i == 21 || i == 22 || i == 26) {
                AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("BT profile service: disconnecting " + BluetoothProfile.getProfileName(i) + " profile"));
                BtHelper.this.mDeviceBroker.postBtProfileDisconnected(i);
            }
        }
    };
    public int mIsBtOffloadEnabled = 0;

    public BtHelper(AudioDeviceBroker audioDeviceBroker) {
        this.mDeviceBroker = audioDeviceBroker;
    }

    public static String scoAudioModeToString(int i) {
        if (i == -1) {
            return "SCO_MODE_UNDEFINED";
        }
        if (i == 0) {
            return "SCO_MODE_VIRTUAL_CALL";
        }
        if (i == 2) {
            return "SCO_MODE_VR";
        }
        return "SCO_MODE_(" + i + ")";
    }

    public static String scoAudioStateToString(int i) {
        if (i == 0) {
            return "SCO_STATE_INACTIVE";
        }
        if (i == 1) {
            return "SCO_STATE_ACTIVATE_REQ";
        }
        if (i == 2) {
            return "SCO_STATE_ACTIVE_EXTERNAL";
        }
        if (i == 3) {
            return "SCO_STATE_ACTIVE_INTERNAL";
        }
        if (i == 5) {
            return "SCO_STATE_DEACTIVATING";
        }
        return "SCO_STATE_(" + i + ")";
    }

    public static String deviceEventToString(int i) {
        if (i == 0) {
            return "DEVICE_CONFIG_CHANGE";
        }
        return new String("invalid event:" + i);
    }

    public static String getName(BluetoothDevice bluetoothDevice) {
        String name = bluetoothDevice.getName();
        return name == null ? "" : name;
    }

    public synchronized void onSystemReady() {
        this.mScoConnectionState = -1;
        resetBluetoothSco();
        getBluetoothHeadset();
        Intent intent = new Intent("android.media.SCO_AUDIO_STATE_CHANGED");
        intent.putExtra("android.media.extra.SCO_AUDIO_STATE", 0);
        sendStickyBroadcastToAll(intent);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 2);
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 21);
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 22);
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 26);
        }
        this.mVibratorManagerInternal = (VibratorManagerInternal) LocalServices.getService(VibratorManagerInternal.class);
    }

    public synchronized void onAudioServerDiedRestoreA2dp() {
        this.mDeviceBroker.setForceUse_Async(1, this.mDeviceBroker.getBluetoothA2dpEnabled() ? 0 : 10, "onAudioServerDied()");
    }

    public synchronized void setAvrcpAbsoluteVolumeSupported(boolean z) {
        this.mAvrcpAbsVolSupported = z;
        Log.i("AS.BtHelper", "setAvrcpAbsoluteVolumeSupported supported=" + z);
    }

    public synchronized void setAvrcpAbsoluteVolumeIndex(int i) {
        if (this.mA2dp == null) {
            AudioService.sVolumeLogger.enqueue(new EventLogger.StringEvent("setAvrcpAbsoluteVolumeIndex: bailing due to null mA2dp").printLog("AS.BtHelper"));
            return;
        }
        if (!this.mAvrcpAbsVolSupported) {
            AudioService.sVolumeLogger.enqueue(new EventLogger.StringEvent("setAvrcpAbsoluteVolumeIndex: abs vol not supported ").printLog("AS.BtHelper"));
            return;
        }
        Log.i("AS.BtHelper", "setAvrcpAbsoluteVolumeIndex index=" + i);
        AudioService.sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(4, i));
        setAvrcpAbsoluteVolumeIndexExt(i);
    }

    public synchronized int getA2dpCodec(BluetoothDevice bluetoothDevice) {
        BluetoothCodecStatus bluetoothCodecStatus;
        if (this.mIsBtOffloadEnabled == 1) {
            return 2097152;
        }
        BluetoothA2dp bluetoothA2dp = this.mA2dp;
        if (bluetoothA2dp == null) {
            return 0;
        }
        try {
            bluetoothCodecStatus = bluetoothA2dp.getCodecStatus(bluetoothDevice);
        } catch (Exception e) {
            Log.e("AS.BtHelper", "Exception while getting status of " + bluetoothDevice, e);
            bluetoothCodecStatus = null;
        }
        if (bluetoothCodecStatus == null) {
            return 0;
        }
        BluetoothCodecConfig codecConfig = bluetoothCodecStatus.getCodecConfig();
        if (codecConfig == null) {
            return 0;
        }
        return AudioSystem.bluetoothCodecToAudioFormat(codecConfig.getCodecType());
    }

    public synchronized void receiveBtEvent(Intent intent) {
        String action = intent.getAction();
        Log.i("AS.BtHelper", "receiveBtEvent action: " + action + " mScoAudioState: " + this.mScoAudioState);
        if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED")) {
            setBtScoActiveDevice((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class));
        } else if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
            Log.i("AS.BtHelper", "receiveBtEvent ACTION_AUDIO_STATE_CHANGED: " + intExtra);
            this.mDeviceBroker.postScoAudioStateChanged(intExtra);
            if (intExtra == 12) {
                int intExtra2 = intent.getIntExtra("com.samsung.bt.hfp.intent.extra.HEADSET_SCO_VOLUME", 7) * 10;
                this.mDeviceBroker.postSetVolumeIndexOnDevice(6, intExtra2, 32, "BtHelper.receiveBtEvent");
                Log.i("AS.BtHelper", "SCO volume set to index " + intExtra2 + " by BT intent");
            }
        } else if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
            int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
            int intExtra4 = intent.getIntExtra("android.bluetooth.device.extra.DEVICE_TYPE", 0);
            if (intExtra3 == 2 && intExtra4 != 1) {
                setBtScoActiveDevice((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onScoAudioStateChanged(int i) {
        BluetoothHeadset bluetoothHeadset;
        BluetoothDevice bluetoothDevice;
        int i2 = 2;
        boolean z = false;
        switch (i) {
            case 10:
                this.mDeviceBroker.muteRingtoneDuringVibration();
                this.mDeviceBroker.setBluetoothScoOn(false, "BtHelper.receiveBtEvent");
                if (Rune.SEC_AUDIO_REMOTE_MIC && Settings.System.getIntForUser(this.mDeviceBroker.getContext().getContentResolver(), "run_amplify_ambient_sound", 0, -2) == 2) {
                    Intent intent = new Intent("android.samsung.media.action.ACTION_AUDIO_REMOTEMIC_SCO_RESUME");
                    intent.addFlags(67108864);
                    sendStickyBroadcastToAll(intent);
                    Log.i("AS.BtHelper", "broadcast remote mic resume intent");
                }
                if (this.mScoAudioState == 1 && (bluetoothHeadset = this.mBluetoothHeadset) != null && (bluetoothDevice = this.mBluetoothHeadsetDevice) != null && connectBluetoothScoAudioHelper(bluetoothHeadset, bluetoothDevice, this.mScoAudioMode)) {
                    this.mScoAudioState = 3;
                    break;
                } else {
                    r5 = this.mScoAudioState != 2;
                    this.mScoAudioState = 0;
                    i2 = 0;
                    break;
                }
                break;
            case 11:
                int i3 = this.mScoAudioState;
                if (i3 != 3 && i3 != 4 && i3 != 5) {
                    this.mScoAudioState = 2;
                }
                i2 = -1;
                r5 = z;
                break;
            case 12:
                int i4 = this.mScoAudioState;
                if (i4 != 3 && i4 != 4 && i4 != 5) {
                    this.mScoAudioState = 2;
                } else if (this.mDeviceBroker.isBluetoothScoRequested()) {
                    z = true;
                }
                this.mDeviceBroker.setBluetoothScoOn(true, "BtHelper.receiveBtEvent");
                i2 = 1;
                r5 = z;
                break;
            default:
                i2 = -1;
                r5 = z;
                break;
        }
        if (r5) {
            broadcastScoConnectionState(i2);
            Intent intent2 = new Intent("android.media.SCO_AUDIO_STATE_CHANGED");
            intent2.putExtra("android.media.extra.SCO_AUDIO_STATE", i2);
            sendStickyBroadcastToAll(intent2);
        }
    }

    public synchronized boolean isBluetoothScoOn() {
        BluetoothDevice bluetoothDevice;
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
        if (bluetoothHeadset != null && (bluetoothDevice = this.mBluetoothHeadsetDevice) != null) {
            return bluetoothHeadset.getAudioState(bluetoothDevice) == 12;
        }
        return false;
    }

    public synchronized boolean startBluetoothSco(int i, String str) {
        AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent(str));
        return requestScoState(12, i);
    }

    public synchronized boolean stopBluetoothSco(String str) {
        AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent(str));
        return requestScoState(10, 0);
    }

    public synchronized void setLeAudioVolume(int i, int i2, int i3) {
        if (this.mLeAudio == null) {
            Log.i("AS.BtHelper", "setLeAudioVolume: null mLeAudio");
            return;
        }
        int round = (int) Math.round((i * 255.0d) / i2);
        Log.i("AS.BtHelper", "setLeAudioVolume: calling mLeAudio.setVolume idx=" + i + " volume=" + round);
        AudioService.sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(10, i, i2));
        try {
            this.mLeAudio.setVolume(round);
        } catch (Exception e) {
            Log.e("AS.BtHelper", "Exception while setting LE volume", e);
        }
    }

    public synchronized void setHearingAidVolume(int i, int i2, boolean z) {
        if (this.mHearingAid == null) {
            Log.i("AS.BtHelper", "setHearingAidVolume: null mHearingAid");
            return;
        }
        int streamVolumeDB = (int) AudioSystem.getStreamVolumeDB(i2, i / 10, 134217728);
        if (streamVolumeDB < -128) {
            streamVolumeDB = -128;
        }
        Log.i("AS.BtHelper", "setHearingAidVolume: calling mHearingAid.setVolume idx=" + i + " gain=" + streamVolumeDB);
        if (z) {
            AudioService.sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(3, i, streamVolumeDB));
        }
        try {
            this.mHearingAid.setVolume(streamVolumeDB);
        } catch (Exception e) {
            Log.i("AS.BtHelper", "Exception while setting hearing aid volume", e);
        }
    }

    public synchronized void onBroadcastScoConnectionState(int i) {
        if (i == this.mScoConnectionState) {
            return;
        }
        Intent intent = new Intent("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
        intent.putExtra("android.media.extra.SCO_AUDIO_STATE", i);
        intent.putExtra("android.media.extra.SCO_AUDIO_PREVIOUS_STATE", this.mScoConnectionState);
        sendStickyBroadcastToAll(intent);
        this.mScoConnectionState = i;
    }

    public synchronized void resetBluetoothSco() {
        this.mScoAudioState = 0;
        broadcastScoConnectionState(0);
        this.mDeviceBroker.clearA2dpSuspended(false);
        this.mDeviceBroker.clearLeAudioSuspended(false);
        this.mDeviceBroker.setBluetoothScoOn(false, "resetBluetoothSco");
    }

    public synchronized void disconnectHeadset() {
        setBtScoActiveDevice(null);
        this.mBluetoothHeadset = null;
    }

    public synchronized void onBtProfileDisconnected(int i) {
        if (i == 2) {
            this.mA2dp = null;
        } else if (i == 11) {
            Log.e("AS.BtHelper", "onBtProfileDisconnected: Not a profile handled by BtHelper " + BluetoothProfile.getProfileName(i));
        } else if (i == 26) {
            this.mLeBroadcast = null;
        } else if (i == 21) {
            this.mHearingAid = null;
        } else if (i == 22) {
            this.mLeAudio = null;
        } else {
            Log.e("AS.BtHelper", "onBtProfileDisconnected: Not a valid profile to disconnect " + BluetoothProfile.getProfileName(i));
        }
    }

    public synchronized void onBtProfileConnected(int i, BluetoothProfile bluetoothProfile) {
        if (i == 1) {
            onHeadsetProfileConnected((BluetoothHeadset) bluetoothProfile);
            return;
        }
        if (i == 2) {
            this.mA2dp = (BluetoothA2dp) bluetoothProfile;
        } else if (i == 21) {
            this.mHearingAid = (BluetoothHearingAid) bluetoothProfile;
        } else if (i == 22) {
            this.mLeAudio = (BluetoothLeAudio) bluetoothProfile;
        } else if (i == 26) {
            this.mLeBroadcast = (BluetoothLeBroadcast) bluetoothProfile;
            return;
        }
        List<BluetoothDevice> connectedDevices = bluetoothProfile.getConnectedDevices();
        if (connectedDevices.isEmpty()) {
            return;
        }
        BluetoothDevice bluetoothDevice = connectedDevices.get(0);
        if (bluetoothProfile.getConnectionState(bluetoothDevice) == 2) {
            this.mDeviceBroker.queueOnBluetoothActiveDeviceChanged(new AudioDeviceBroker.BtDeviceChangedData(bluetoothDevice, null, new BluetoothProfileConnectionInfo(i), "mBluetoothProfileServiceListener"));
        } else {
            this.mDeviceBroker.queueOnBluetoothActiveDeviceChanged(new AudioDeviceBroker.BtDeviceChangedData(null, bluetoothDevice, new BluetoothProfileConnectionInfo(i), "mBluetoothProfileServiceListener"));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x005d A[Catch: all -> 0x0064, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0013, B:6:0x0017, B:8:0x001e, B:9:0x0026, B:16:0x0035, B:18:0x0039, B:23:0x0042, B:25:0x004a, B:27:0x005d, B:31:0x004e, B:33:0x0056), top: B:2:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void onHeadsetProfileConnected(BluetoothHeadset bluetoothHeadset) {
        boolean z;
        BluetoothDevice bluetoothDevice;
        this.mDeviceBroker.handleCancelFailureToConnectToBtHeadsetService();
        this.mBluetoothHeadset = bluetoothHeadset;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        List emptyList = Collections.emptyList();
        if (defaultAdapter != null) {
            emptyList = defaultAdapter.getActiveDevices(1);
        }
        setBtScoActiveDevice(emptyList.size() > 0 ? (BluetoothDevice) emptyList.get(0) : null);
        checkScoAudioState();
        int i = this.mScoAudioState;
        if (i == 1 || i == 4) {
            BluetoothHeadset bluetoothHeadset2 = this.mBluetoothHeadset;
            if (bluetoothHeadset2 != null && (bluetoothDevice = this.mBluetoothHeadsetDevice) != null) {
                if (i == 1) {
                    z = connectBluetoothScoAudioHelper(bluetoothHeadset2, bluetoothDevice, this.mScoAudioMode);
                    if (z) {
                        this.mScoAudioState = 3;
                    }
                } else if (i == 4) {
                    z = disconnectBluetoothScoAudioHelper(bluetoothHeadset2, bluetoothDevice, this.mScoAudioMode);
                    if (z) {
                        this.mScoAudioState = 5;
                    }
                }
                if (!z) {
                    this.mScoAudioState = 0;
                    broadcastScoConnectionState(0);
                }
            }
            z = false;
            if (!z) {
            }
        }
    }

    public final void broadcastScoConnectionState(int i) {
        this.mDeviceBroker.postBroadcastScoConnectionState(i);
    }

    public AudioDeviceAttributes getHeadsetAudioDevice() {
        BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
        if (bluetoothDevice == null) {
            return null;
        }
        return btHeadsetDeviceToAudioDevice(bluetoothDevice);
    }

    public static AudioDeviceAttributes btHeadsetDeviceToAudioDevice(BluetoothDevice bluetoothDevice) {
        int i = 16;
        if (bluetoothDevice == null) {
            return new AudioDeviceAttributes(16, "");
        }
        String address = bluetoothDevice.getAddress();
        String name = getName(bluetoothDevice);
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
        StringBuilder sb = new StringBuilder();
        sb.append("btHeadsetDeviceToAudioDevice btDevice: ");
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

    public final boolean handleBtScoActiveDeviceChange(BluetoothDevice bluetoothDevice, boolean z) {
        boolean z2;
        if (bluetoothDevice == null) {
            return true;
        }
        AudioDeviceAttributes btHeadsetDeviceToAudioDevice = btHeadsetDeviceToAudioDevice(bluetoothDevice);
        if (z) {
            z2 = this.mDeviceBroker.handleDeviceConnection(btHeadsetDeviceToAudioDevice, z, bluetoothDevice) | false;
        } else {
            int[] iArr = {16, 32, 64};
            boolean z3 = false;
            for (int i = 0; i < 3; i++) {
                z3 |= this.mDeviceBroker.handleDeviceConnection(new AudioDeviceAttributes(iArr[i], btHeadsetDeviceToAudioDevice.getAddress(), btHeadsetDeviceToAudioDevice.getName()), z, bluetoothDevice);
            }
            z2 = z3;
        }
        return this.mDeviceBroker.handleDeviceConnection(new AudioDeviceAttributes(-2147483640, btHeadsetDeviceToAudioDevice.getAddress(), btHeadsetDeviceToAudioDevice.getName()), z, bluetoothDevice) && z2;
    }

    public final String getAnonymizedAddress(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice == null ? "(null)" : bluetoothDevice.getAnonymizedAddress();
    }

    public final void setBtScoActiveDevice(BluetoothDevice bluetoothDevice) {
        Log.i("AS.BtHelper", "setBtScoActiveDevice: " + getAnonymizedAddress(this.mBluetoothHeadsetDevice) + " -> " + getAnonymizedAddress(bluetoothDevice));
        BluetoothDevice bluetoothDevice2 = this.mBluetoothHeadsetDevice;
        if (Objects.equals(bluetoothDevice, bluetoothDevice2)) {
            return;
        }
        if (!handleBtScoActiveDeviceChange(bluetoothDevice2, false)) {
            Log.w("AS.BtHelper", "setBtScoActiveDevice() failed to remove previous device " + getAnonymizedAddress(bluetoothDevice2));
        }
        if (!handleBtScoActiveDeviceChange(bluetoothDevice, true)) {
            Log.e("AS.BtHelper", "setBtScoActiveDevice() failed to add new device " + getAnonymizedAddress(bluetoothDevice));
            bluetoothDevice = null;
        }
        this.mBluetoothHeadsetDevice = bluetoothDevice;
        if (bluetoothDevice == null) {
            resetBluetoothSco();
        }
        if (bluetoothDevice2 == null && this.mBluetoothHeadsetDevice != null) {
            this.mDeviceBroker.resetBtScoOnByApp();
        }
        if (this.mBluetoothHeadsetDevice == null || !ScreenSharingHelper.isSplitSoundEnabled()) {
            return;
        }
        ScreenSharingHelper.setSplitSoundEnabled(false);
        SemAudioSystem.setPolicyParameters("l_smart_view_split_sound_enable=" + ScreenSharingHelper.isSplitSoundEnabled());
        this.mDeviceBroker.postBroadcastBecomingNoisy(32);
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
                        int i4 = Settings.Global.getInt(this.mDeviceBroker.getContentResolver(), "bluetooth_sco_channel_" + this.mBluetoothHeadsetDevice.getAddress(), 0);
                        this.mScoAudioMode = i4;
                        if (i4 > 2 || i4 < 0) {
                            this.mScoAudioMode = 0;
                        }
                    }
                }
                BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
                if (bluetoothHeadset == null) {
                    if (getBluetoothHeadset()) {
                        this.mScoAudioState = 1;
                    } else {
                        Log.w("AS.BtHelper", "requestScoState: getBluetoothHeadset failed during connection, mScoAudioMode=" + this.mScoAudioMode);
                        broadcastScoConnectionState(0);
                        return false;
                    }
                } else {
                    BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
                    if (bluetoothDevice == null) {
                        Log.w("AS.BtHelper", "requestScoState: no active device while connecting, mScoAudioMode=" + this.mScoAudioMode);
                        broadcastScoConnectionState(0);
                        return false;
                    }
                    if (connectBluetoothScoAudioHelper(bluetoothHeadset, bluetoothDevice, this.mScoAudioMode)) {
                        this.mScoAudioState = 3;
                    } else {
                        Log.w("AS.BtHelper", "requestScoState: connect to " + getAnonymizedAddress(this.mBluetoothHeadsetDevice) + " failed, mScoAudioMode=" + this.mScoAudioMode);
                        broadcastScoConnectionState(0);
                        return false;
                    }
                }
            } else if (i3 == 2) {
                broadcastScoConnectionState(1);
            } else if (i3 != 3) {
                if (i3 == 4) {
                    this.mScoAudioState = 3;
                    broadcastScoConnectionState(1);
                } else if (i3 == 5) {
                    this.mScoAudioState = 1;
                } else {
                    Log.w("AS.BtHelper", "requestScoState: failed to connect in state " + this.mScoAudioState + ", scoAudioMode=" + i2);
                    broadcastScoConnectionState(0);
                    return false;
                }
            }
        } else if (i == 10) {
            int i5 = this.mScoAudioState;
            if (i5 == 1) {
                this.mScoAudioState = 0;
                broadcastScoConnectionState(0);
            } else if (i5 == 3) {
                BluetoothHeadset bluetoothHeadset2 = this.mBluetoothHeadset;
                if (bluetoothHeadset2 == null) {
                    if (getBluetoothHeadset()) {
                        this.mScoAudioState = 4;
                    } else {
                        Log.w("AS.BtHelper", "requestScoState: getBluetoothHeadset failed during disconnection, mScoAudioMode=" + this.mScoAudioMode);
                        this.mScoAudioState = 0;
                        broadcastScoConnectionState(0);
                        return false;
                    }
                } else {
                    BluetoothDevice bluetoothDevice2 = this.mBluetoothHeadsetDevice;
                    if (bluetoothDevice2 == null) {
                        this.mScoAudioState = 0;
                        broadcastScoConnectionState(0);
                    } else if (disconnectBluetoothScoAudioHelper(bluetoothHeadset2, bluetoothDevice2, this.mScoAudioMode)) {
                        this.mScoAudioState = 5;
                    } else {
                        this.mScoAudioState = 0;
                        broadcastScoConnectionState(0);
                    }
                }
            } else {
                Log.w("AS.BtHelper", "requestScoState: failed to disconnect in state " + this.mScoAudioState + ", scoAudioMode=" + i2);
                broadcastScoConnectionState(0);
                return false;
            }
        }
        return true;
    }

    public final void sendStickyBroadcastToAll(Intent intent) {
        intent.addFlags(268435456);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.getContext().sendStickyBroadcastAsUser(intent, UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean disconnectBluetoothScoAudioHelper(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice, int i) {
        if (i == 0) {
            return bluetoothHeadset.stopScoUsingVirtualVoiceCall();
        }
        if (i != 2) {
            return false;
        }
        return bluetoothHeadset.stopVoiceRecognition(bluetoothDevice);
    }

    public static boolean connectBluetoothScoAudioHelper(BluetoothHeadset bluetoothHeadset, BluetoothDevice bluetoothDevice, int i) {
        if (i == 0) {
            return bluetoothHeadset.startScoUsingVirtualVoiceCall();
        }
        if (i != 2) {
            return false;
        }
        return bluetoothHeadset.startVoiceRecognition(bluetoothDevice);
    }

    public final void checkScoAudioState() {
        BluetoothDevice bluetoothDevice;
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
        if (bluetoothHeadset == null || (bluetoothDevice = this.mBluetoothHeadsetDevice) == null || this.mScoAudioState != 0 || bluetoothHeadset.getAudioState(bluetoothDevice) == 10) {
            return;
        }
        this.mScoAudioState = 2;
    }

    public final boolean getBluetoothHeadset() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean profileProxy = defaultAdapter != null ? defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 1) : false;
        this.mDeviceBroker.handleFailureToConnectToBtHeadsetService(profileProxy ? 3000 : 0);
        return profileProxy;
    }

    public static int getProfileFromType(int i) {
        if (AudioSystem.isBluetoothA2dpOutDevice(i)) {
            return 2;
        }
        if (AudioSystem.isBluetoothScoDevice(i)) {
            return 1;
        }
        return AudioSystem.isBluetoothLeDevice(i) ? 22 : 0;
    }

    public static Bundle getPreferredAudioProfiles(String str) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter.getPreferredAudioProfiles(defaultAdapter.getRemoteDevice(str));
    }

    public static void onNotifyPreferredAudioProfileApplied(BluetoothDevice bluetoothDevice) {
        BluetoothAdapter.getDefaultAdapter().notifyActiveDeviceChangeApplied(bluetoothDevice);
    }

    public static String btDeviceClassToString(int i) {
        switch (i) {
            case 1024:
                return "AUDIO_VIDEO_UNCATEGORIZED";
            case 1028:
                return "AUDIO_VIDEO_WEARABLE_HEADSET";
            case 1032:
                return "AUDIO_VIDEO_HANDSFREE";
            case 1036:
                return "AUDIO_VIDEO_RESERVED_0x040C";
            case 1040:
                return "AUDIO_VIDEO_MICROPHONE";
            case 1044:
                return "AUDIO_VIDEO_LOUDSPEAKER";
            case 1048:
                return "AUDIO_VIDEO_HEADPHONES";
            case 1052:
                return "AUDIO_VIDEO_PORTABLE_AUDIO";
            case 1056:
                return "AUDIO_VIDEO_CAR_AUDIO";
            case 1060:
                return "AUDIO_VIDEO_SET_TOP_BOX";
            case 1064:
                return "AUDIO_VIDEO_HIFI_AUDIO";
            case 1068:
                return "AUDIO_VIDEO_VCR";
            case 1072:
                return "AUDIO_VIDEO_VIDEO_CAMERA";
            case 1076:
                return "AUDIO_VIDEO_CAMCORDER";
            case 1080:
                return "AUDIO_VIDEO_VIDEO_MONITOR";
            case 1084:
                return "AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER";
            case 1088:
                return "AUDIO_VIDEO_VIDEO_CONFERENCING";
            case 1092:
                return "AUDIO_VIDEO_RESERVED_0x0444";
            case 1096:
                return "AUDIO_VIDEO_VIDEO_GAMING_TOY";
            default:
                return TextUtils.formatSimple("0x%04x", new Object[]{Integer.valueOf(i)});
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        BluetoothClass bluetoothClass;
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "mBluetoothHeadset: " + this.mBluetoothHeadset);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("mBluetoothHeadsetDevice: ");
        sb.append(this.mBluetoothHeadsetDevice);
        printWriter.println(sb.toString());
        BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
        if (bluetoothDevice != null && (bluetoothClass = bluetoothDevice.getBluetoothClass()) != null) {
            printWriter.println(str + "mBluetoothHeadsetDevice.DeviceClass: " + btDeviceClassToString(bluetoothClass.getDeviceClass()));
        }
        printWriter.println(str + "mScoAudioState: " + scoAudioStateToString(this.mScoAudioState));
        printWriter.println(str + "mScoAudioMode: " + scoAudioModeToString(this.mScoAudioMode));
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "mHearingAid: " + this.mHearingAid);
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str + "mLeAudio: " + this.mLeAudio);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("mA2dp: ");
        sb2.append(this.mA2dp);
        printWriter.println(sb2.toString());
        printWriter.println(str + "mAvrcpAbsVolSupported: " + this.mAvrcpAbsVolSupported);
    }

    public synchronized boolean isBluetoothScoSupported() {
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
        if (bluetoothHeadset != null && !bluetoothHeadset.getDevicesMatchingConnectionStates(new int[]{2, 1, 3}).isEmpty()) {
            BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
            if (bluetoothDevice == null) {
                return false;
            }
            int audioState = this.mBluetoothHeadset.getAudioState(bluetoothDevice);
            if (audioState != 11 && audioState != 12) {
                Log.e("AS.BtHelper", "setBluetoothScoOn() wrong sco state:" + audioState + " mScoAudioState:" + this.mScoAudioState);
                return false;
            }
        }
        return true;
    }

    public synchronized boolean isScoStateInternal() {
        return this.mScoAudioState == 3;
    }

    public void forceCloseSco() {
        BluetoothDevice bluetoothDevice;
        BluetoothHeadset bluetoothHeadset = this.mBluetoothHeadset;
        if (bluetoothHeadset == null || (bluetoothDevice = this.mBluetoothHeadsetDevice) == null) {
            return;
        }
        disconnectBluetoothScoAudioHelper(bluetoothHeadset, bluetoothDevice, this.mScoAudioMode);
        this.mScoAudioState = 5;
    }

    public boolean isWatchOrBudsWearingOff() {
        BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
        if (bluetoothDevice == null) {
            return false;
        }
        return BtUtils.isSamsungWatch(bluetoothDevice, this.mBluetoothHeadset) || BtUtils.isBudsWearingOff(this.mBluetoothHeadsetDevice);
    }

    public synchronized BluetoothA2dp getA2dp() {
        return this.mA2dp;
    }

    public synchronized BluetoothLeAudio getLeAudio() {
        return this.mLeAudio;
    }

    public synchronized BluetoothHeadset getBTHeadset() {
        return this.mBluetoothHeadset;
    }

    public synchronized boolean isRemoteVolumeControlSupported() {
        boolean z;
        BluetoothDevice bluetoothDevice = this.mBluetoothHeadsetDevice;
        if (bluetoothDevice != null) {
            z = bluetoothDevice.getVolumeControlSupport();
        }
        return z;
    }

    public synchronized void setBtOffloadEnable(int i) {
        this.mIsBtOffloadEnabled = i;
    }

    public final void setAvrcpAbsoluteVolumeIndexExt(int i) {
        ArrayMap a2dpDevices = this.mDeviceBroker.getA2dpDevices();
        if (a2dpDevices.size() == 0) {
            Log.i("AS.BtHelper", "No a2dp volume info");
            this.mA2dp.setA2dpMediaVolume(null, DualA2dpVolumeManager.FINE_VOLUME_TABLE[i], i);
            return;
        }
        for (int i2 = 0; i2 < a2dpDevices.size(); i2++) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) a2dpDevices.keyAt(i2);
            int intValue = ((Integer) a2dpDevices.valueAt(i2)).intValue();
            this.mA2dp.setA2dpMediaVolume(bluetoothDevice, DualA2dpVolumeManager.FINE_VOLUME_TABLE[intValue], intValue);
        }
    }

    public synchronized BluetoothDevice getActiveA2dpDevice() {
        BluetoothA2dp bluetoothA2dp = this.mA2dp;
        if (bluetoothA2dp == null) {
            return null;
        }
        return bluetoothA2dp.getActiveDevice();
    }

    public void updateBtAppList(Context context) {
        EventLogger eventLogger = BtUtils.sAuracastLogger;
        eventLogger.enqueue(new EventLogger.StringEvent("updateBtAppList for BLE broadcast").printLog("AS.BtHelper"));
        try {
            BluetoothLeBroadcast bluetoothLeBroadcast = this.mLeBroadcast;
            if (bluetoothLeBroadcast != null) {
                long auracastDbVersion = bluetoothLeBroadcast.getAuracastDbVersion();
                long j = BtUtils.sBtAppPackageListVersion;
                if (auracastDbVersion <= j && j != 0) {
                    if (auracastDbVersion == 0) {
                        eventLogger.enqueue(new EventLogger.StringEvent("updateBtAppList skipped version update : DB version is 0").printLog("AS.BtHelper"));
                    }
                }
                List blockPlayerList = this.mLeBroadcast.getBlockPlayerList();
                if (blockPlayerList != null) {
                    eventLogger.enqueue(new EventLogger.StringEvent("updateBtAppList get list from BT. version = " + auracastDbVersion + " list size = " + blockPlayerList.size()).printLog("AS.BtHelper"));
                    BtUtils.updateBtAppList(context, blockPlayerList, auracastDbVersion);
                } else {
                    eventLogger.enqueue(new EventLogger.StringEvent("updateBtAppList list is null").printLog("AS.BtHelper"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
