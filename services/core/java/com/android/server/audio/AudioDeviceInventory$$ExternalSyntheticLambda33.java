package com.android.server.audio;

import android.media.AudioDeviceAttributes;

import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AudioDeviceInventory$$ExternalSyntheticLambda33
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioDeviceInventory$$ExternalSyntheticLambda33(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                AudioDeviceInventory audioDeviceInventory = (AudioDeviceInventory) obj2;
                String str = (String) obj;
                audioDeviceInventory.getClass();
                AudioDeviceAttributes audioDeviceAttributes =
                        new AudioDeviceAttributes(-2147352576, str);
                audioDeviceInventory.mAudioSystem.setDeviceConnectionState(
                        audioDeviceAttributes, 0, 0);
                audioDeviceInventory.mConnectedDevices.remove(
                        AudioDeviceInventory.DeviceInfo.makeDeviceListKey(-2147352576, str));
                audioDeviceInventory.mDeviceBroker.postCheckCommunicationDeviceRemoval(
                        audioDeviceAttributes);
                break;
            case 1:
                ((AudioDeviceInventory) obj2).makeHearingAidDeviceUnavailable((String) obj);
                break;
            default:
                ((PrintWriter) obj2).print(" 0x" + Integer.toHexString(((Integer) obj).intValue()));
                break;
        }
    }
}
