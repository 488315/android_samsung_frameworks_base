package com.android.server.audio;

import android.util.ArraySet;
import android.util.Pair;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AudioDeviceInventory$$ExternalSyntheticLambda28
        implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AudioDeviceInventory$$ExternalSyntheticLambda28(
            int i, ArraySet arraySet) {
        this.f$0 = i;
        this.f$1 = arraySet;
    }

    public /* synthetic */ AudioDeviceInventory$$ExternalSyntheticLambda28(
            AudioDeviceInventory audioDeviceInventory, int i) {
        this.f$1 = audioDeviceInventory;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                ArraySet arraySet = (ArraySet) this.f$1;
                AudioDeviceInventory.DeviceInfo deviceInfo = (AudioDeviceInventory.DeviceInfo) obj;
                if (deviceInfo.mDeviceType == i) {
                    arraySet.add(
                            new Pair(
                                    deviceInfo.mDeviceAddress,
                                    Integer.valueOf(deviceInfo.mDeviceCodecFormat)));
                    break;
                }
                break;
            default:
                AudioDeviceInventory audioDeviceInventory = (AudioDeviceInventory) this.f$1;
                int i2 = this.f$0;
                String str = (String) obj;
                audioDeviceInventory.mDeviceBroker.setA2dpSuspended(
                        "makeA2dpDeviceUnavailableLater", true, true);
                String makeDeviceListKey =
                        AudioDeviceInventory.DeviceInfo.makeDeviceListKey(128, str);
                AudioDeviceInventory.DeviceInfo deviceInfo2 =
                        (AudioDeviceInventory.DeviceInfo)
                                audioDeviceInventory.mConnectedDevices.get(makeDeviceListKey);
                int i3 = deviceInfo2 != null ? deviceInfo2.mDeviceCodecFormat : 0;
                audioDeviceInventory.mConnectedDevices.remove(makeDeviceListKey);
                audioDeviceInventory.mDeviceBroker.sendIILMsg(10, 2, i3, 0, str, i2);
                break;
        }
    }
}
