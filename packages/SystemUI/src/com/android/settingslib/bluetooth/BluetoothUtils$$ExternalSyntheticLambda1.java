package com.android.settingslib.bluetooth;

import android.util.Log;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class BluetoothUtils$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioCastProfile f$0;

    public /* synthetic */ BluetoothUtils$$ExternalSyntheticLambda1(AudioCastProfile audioCastProfile, int i) {
        this.$r8$classId = i;
        this.f$0 = audioCastProfile;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        AudioCastProfile audioCastProfile = this.f$0;
        SemBluetoothCastDevice semBluetoothCastDevice = (SemBluetoothCastDevice) obj;
        switch (i) {
            case 0:
                boolean z = BluetoothUtils.DEBUG;
                Log.d(audioCastProfile.TAG, "getConnectionState");
                SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                if ((semBluetoothAudioCast == null ? 0 : semBluetoothAudioCast.getConnectionState(semBluetoothCastDevice)) != 2) {
                    break;
                }
                break;
            default:
                boolean z2 = BluetoothUtils.DEBUG;
                Log.d(audioCastProfile.TAG, "getConnectionState");
                SemBluetoothAudioCast semBluetoothAudioCast2 = audioCastProfile.mService;
                if ((semBluetoothAudioCast2 == null ? 0 : semBluetoothAudioCast2.getConnectionState(semBluetoothCastDevice)) != 2) {
                    break;
                }
                break;
        }
        return false;
    }
}
