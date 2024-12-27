package com.android.server.audio;

import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioSystem;
import android.text.TextUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AudioDeviceInventory$$ExternalSyntheticLambda34
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioDeviceInventory$$ExternalSyntheticLambda34(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((AudioDeviceInfo) obj).getInternalType()
                        == ((AudioDeviceAttributes) obj2).getInternalType();
            case 1:
                AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) obj;
                return !AudioSystem.isBluetoothDevice(audioDeviceInfo.getInternalType())
                        || audioDeviceInfo
                                .getAddress()
                                .equals(((AudioDeviceAttributes) obj2).getAddress());
            default:
                AudioDeviceInventory audioDeviceInventory = (AudioDeviceInventory) obj2;
                audioDeviceInventory.getClass();
                return TextUtils.equals(
                        ((AudioDeviceInventory.DeviceInfo) obj).mDeviceName,
                        audioDeviceInventory.mCurAudioRoutes.bluetoothName);
        }
    }
}
