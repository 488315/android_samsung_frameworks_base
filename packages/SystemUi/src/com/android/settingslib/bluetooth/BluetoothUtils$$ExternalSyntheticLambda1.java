package com.android.settingslib.bluetooth;

import android.util.Log;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                AudioCastProfile audioCastProfile = this.f$0;
                SemBluetoothCastDevice semBluetoothCastDevice = (SemBluetoothCastDevice) obj;
                Log.d(audioCastProfile.TAG, "getConnectionState");
                SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                if ((semBluetoothAudioCast == null ? 0 : semBluetoothAudioCast.getConnectionState(semBluetoothCastDevice)) != 2) {
                    break;
                }
                break;
            default:
                AudioCastProfile audioCastProfile2 = this.f$0;
                SemBluetoothCastDevice semBluetoothCastDevice2 = (SemBluetoothCastDevice) obj;
                Log.d(audioCastProfile2.TAG, "getConnectionState");
                SemBluetoothAudioCast semBluetoothAudioCast2 = audioCastProfile2.mService;
                if ((semBluetoothAudioCast2 == null ? 0 : semBluetoothAudioCast2.getConnectionState(semBluetoothCastDevice2)) != 2) {
                    break;
                }
                break;
        }
        return false;
    }
}
