package com.android.settingslib.bluetooth;

import android.bluetooth.AudioInputControl;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothVolumeControl;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.settingslib.bluetooth.AmbientVolumeController;
import com.android.settingslib.bluetooth.HearingDeviceLocalDataManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AmbientVolumeController implements LocalBluetoothProfileManager.ServiceListener {
    public final AmbientVolumeControlCallback mCallback;
    public final LocalBluetoothProfileManager mProfileManager;
    public final VolumeControlProfile mVolumeControlProfile;
    public final Map mDeviceAmbientControlsMap = new ArrayMap();
    public final Map mDeviceCallbackMap = new ArrayMap();
    public final Map mDeviceAmbientStateMap = new ArrayMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AmbientCallback implements AudioInputControl.AudioInputCallback {
        public final AmbientVolumeControlCallback mCallback;
        public final BluetoothDevice mDevice;

        public AmbientCallback(BluetoothDevice bluetoothDevice, AmbientVolumeControlCallback ambientVolumeControlCallback) {
            this.mDevice = bluetoothDevice;
            this.mCallback = ambientVolumeControlCallback;
        }

        public final void onGainSettingChanged(int i) {
            if (this.mCallback != null) {
                synchronized (AmbientVolumeController.this.mDeviceAmbientStateMap) {
                    try {
                        if (((RemoteAmbientState) ((ArrayMap) AmbientVolumeController.this.mDeviceAmbientStateMap).get(this.mDevice)).gainSetting != i) {
                            ((AmbientVolumeUiController) this.mCallback).onAmbientChanged(this.mDevice, i);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void onMuteChanged(int i) {
            if (this.mCallback != null) {
                synchronized (AmbientVolumeController.this.mDeviceAmbientStateMap) {
                    try {
                        if (((RemoteAmbientState) ((ArrayMap) AmbientVolumeController.this.mDeviceAmbientStateMap).get(this.mDevice)).mute != i) {
                            ((AmbientVolumeUiController) this.mCallback).onMuteChanged(this.mDevice, i);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void onSetGainSettingFailed() {
            Log.w("AmbientController", "onSetGainSettingFailed, device=" + this.mDevice);
            AmbientVolumeControlCallback ambientVolumeControlCallback = this.mCallback;
            if (ambientVolumeControlCallback != null) {
                AmbientVolumeUiController ambientVolumeUiController = (AmbientVolumeUiController) ambientVolumeControlCallback;
                Log.w("AmbientVolumeUiController", "onCommandFailed, device:" + this.mDevice);
                ambientVolumeUiController.mContext.getMainThreadHandler().post(new AmbientVolumeUiController$$ExternalSyntheticLambda3(ambientVolumeUiController, 1));
            }
        }

        public final void onSetMuteFailed() {
            Log.w("AmbientController", "onSetMuteFailed, device=" + this.mDevice);
            AmbientVolumeControlCallback ambientVolumeControlCallback = this.mCallback;
            if (ambientVolumeControlCallback != null) {
                AmbientVolumeUiController ambientVolumeUiController = (AmbientVolumeUiController) ambientVolumeControlCallback;
                Log.w("AmbientVolumeUiController", "onCommandFailed, device:" + this.mDevice);
                ambientVolumeUiController.mContext.getMainThreadHandler().post(new AmbientVolumeUiController$$ExternalSyntheticLambda3(ambientVolumeUiController, 1));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface AmbientVolumeControlCallback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RemoteAmbientState extends Record {
        public final int gainSetting;
        public final int mute;

        public RemoteAmbientState(int i, int i2) {
            this.gainSetting = i;
            this.mute = i2;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            if (!(obj instanceof RemoteAmbientState)) {
                return false;
            }
            RemoteAmbientState remoteAmbientState = (RemoteAmbientState) obj;
            return this.gainSetting == remoteAmbientState.gainSetting && this.mute == remoteAmbientState.mute;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (this.gainSetting * 31) + this.mute;
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {Integer.valueOf(this.gainSetting), Integer.valueOf(this.mute)};
            String[] split = "gainSetting;mute".length() == 0 ? new String[0] : "gainSetting;mute".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(RemoteAmbientState.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < split.length; i++) {
                sb.append(split[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != split.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public AmbientVolumeController(LocalBluetoothProfileManager localBluetoothProfileManager, AmbientVolumeControlCallback ambientVolumeControlCallback) {
        this.mProfileManager = localBluetoothProfileManager;
        VolumeControlProfile volumeControlProfile = localBluetoothProfileManager.mVolumeControlProfile;
        this.mVolumeControlProfile = volumeControlProfile;
        if (volumeControlProfile != null && !volumeControlProfile.mIsProfileReady) {
            ((CopyOnWriteArrayList) localBluetoothProfileManager.mServiceListeners).add(this);
        }
        this.mCallback = ambientVolumeControlCallback;
    }

    public final List getAmbientControls(BluetoothDevice bluetoothDevice) {
        if (this.mVolumeControlProfile == null) {
            return Collections.EMPTY_LIST;
        }
        synchronized (this.mDeviceAmbientControlsMap) {
            try {
                if (((ArrayMap) this.mDeviceAmbientControlsMap).containsKey(bluetoothDevice)) {
                    return (List) ((ArrayMap) this.mDeviceAmbientControlsMap).get(bluetoothDevice);
                }
                BluetoothVolumeControl bluetoothVolumeControl = this.mVolumeControlProfile.mService;
                List list = (bluetoothVolumeControl == null ? Collections.EMPTY_LIST : bluetoothVolumeControl.getAudioInputControlServices(bluetoothDevice)).stream().filter(new Predicate() { // from class: com.android.settingslib.bluetooth.AmbientVolumeController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        AudioInputControl audioInputControl = (AudioInputControl) obj;
                        AmbientVolumeController.this.getClass();
                        return (audioInputControl.getAudioInputType() == 7) && (audioInputControl.getGainMode() == 2 || audioInputControl.getGainMode() == 0) && (audioInputControl.getAudioInputStatus() == 1);
                    }
                }).toList();
                if (!list.isEmpty()) {
                    ((ArrayMap) this.mDeviceAmbientControlsMap).put(bluetoothDevice, list);
                }
                return list;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        VolumeControlProfile volumeControlProfile = this.mVolumeControlProfile;
        if (volumeControlProfile == null || !volumeControlProfile.mIsProfileReady) {
            return;
        }
        ((CopyOnWriteArrayList) this.mProfileManager.mServiceListeners).remove(this);
        AmbientVolumeControlCallback ambientVolumeControlCallback = this.mCallback;
        if (ambientVolumeControlCallback != null) {
            AmbientVolumeUiController ambientVolumeUiController = (AmbientVolumeUiController) ambientVolumeControlCallback;
            ((ArraySet) ambientVolumeUiController.mCachedDevices).forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda0(ambientVolumeUiController, 0));
        }
    }

    public final RemoteAmbientState refreshAmbientState(BluetoothDevice bluetoothDevice) {
        int i;
        int mute;
        if (bluetoothDevice == null || !bluetoothDevice.isConnected()) {
            return null;
        }
        List ambientControls = getAmbientControls(bluetoothDevice);
        int i2 = HearingDeviceLocalDataManager.Data.$r8$clinit;
        int i3 = 2;
        if (ambientControls.isEmpty()) {
            i = Integer.MIN_VALUE;
        } else {
            synchronized (this.mDeviceAmbientStateMap) {
                i = ((AudioInputControl) ambientControls.getFirst()).getGainSetting();
                ((ArrayMap) this.mDeviceAmbientStateMap).put(bluetoothDevice, new RemoteAmbientState(i, ((RemoteAmbientState) this.mDeviceAmbientStateMap.getOrDefault(bluetoothDevice, new RemoteAmbientState(Integer.MIN_VALUE, 2))).mute));
            }
        }
        List ambientControls2 = getAmbientControls(bluetoothDevice);
        if (!ambientControls2.isEmpty()) {
            synchronized (this.mDeviceAmbientStateMap) {
                mute = ((AudioInputControl) ambientControls2.getFirst()).getMute();
                ((ArrayMap) this.mDeviceAmbientStateMap).put(bluetoothDevice, new RemoteAmbientState(((RemoteAmbientState) this.mDeviceAmbientStateMap.getOrDefault(bluetoothDevice, new RemoteAmbientState(Integer.MIN_VALUE, 2))).gainSetting, mute));
            }
            i3 = mute;
        }
        return new RemoteAmbientState(i, i3);
    }

    public final void registerCallback(final ListeningExecutorService listeningExecutorService, BluetoothDevice bluetoothDevice) {
        final AmbientCallback ambientCallback = new AmbientCallback(bluetoothDevice, this.mCallback);
        synchronized (this.mDeviceCallbackMap) {
            ((ArrayMap) this.mDeviceCallbackMap).put(bluetoothDevice, ambientCallback);
        }
        getAmbientControls(bluetoothDevice).forEach(new Consumer() { // from class: com.android.settingslib.bluetooth.AmbientVolumeController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                try {
                    ((AudioInputControl) obj).registerCallback(ListeningExecutorService.this, ambientCallback);
                } catch (IllegalArgumentException e) {
                    Log.i("AmbientController", "Skip registering the callback, " + e.getMessage());
                }
            }
        });
    }

    public final void setAmbient(BluetoothDevice bluetoothDevice, final int i) {
        Log.d("AmbientController", "setAmbient, value:" + i + ", device:" + bluetoothDevice);
        getAmbientControls(bluetoothDevice).forEach(new Consumer() { // from class: com.android.settingslib.bluetooth.AmbientVolumeController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AudioInputControl) obj).setGainSetting(i);
            }
        });
    }

    public final void setMuted(BluetoothDevice bluetoothDevice, final boolean z) {
        Log.d("AmbientController", "setMuted, muted:" + z + ", device:" + bluetoothDevice);
        getAmbientControls(bluetoothDevice).forEach(new Consumer() { // from class: com.android.settingslib.bluetooth.AmbientVolumeController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                try {
                    ((AudioInputControl) obj).setMute(z ? 1 : 0);
                } catch (IllegalStateException unused) {
                    Log.w("AmbientController", "Remote mute state is currently disabled.");
                }
            }
        });
    }

    public final void unregisterCallback(BluetoothDevice bluetoothDevice) {
        final AmbientCallback ambientCallback;
        synchronized (this.mDeviceCallbackMap) {
            ambientCallback = (AmbientCallback) ((ArrayMap) this.mDeviceCallbackMap).remove(bluetoothDevice);
        }
        if (ambientCallback == null) {
            return;
        }
        getAmbientControls(bluetoothDevice).forEach(new Consumer() { // from class: com.android.settingslib.bluetooth.AmbientVolumeController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                try {
                    ((AudioInputControl) obj).unregisterCallback(AmbientVolumeController.AmbientCallback.this);
                } catch (IllegalArgumentException e) {
                    Log.i("AmbientController", "Skip unregistering the callback, " + e.getMessage());
                }
            }
        });
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {
    }
}
