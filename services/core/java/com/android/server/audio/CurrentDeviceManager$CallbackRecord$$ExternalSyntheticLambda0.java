package com.android.server.audio;

import com.samsung.android.server.audio.SensorHandleThread;
import com.samsung.android.server.audio.SensorHandleThread$$ExternalSyntheticLambda0;
import com.samsung.android.server.audio.SensorHandleThread$$ExternalSyntheticLambda2;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class CurrentDeviceManager$CallbackRecord$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ CurrentDeviceManager.CallbackRecord f$0;
    public final /* synthetic */ Set f$1;

    public /* synthetic */ CurrentDeviceManager$CallbackRecord$$ExternalSyntheticLambda0(
            CurrentDeviceManager.CallbackRecord callbackRecord, Set set) {
        this.f$0 = callbackRecord;
        this.f$1 = set;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean anyMatch;
        CurrentDeviceManager.CallbackRecord callbackRecord = this.f$0;
        Set set = this.f$1;
        SensorHandleThread$$ExternalSyntheticLambda0 sensorHandleThread$$ExternalSyntheticLambda0 =
                callbackRecord.callback;
        switch (sensorHandleThread$$ExternalSyntheticLambda0.$r8$classId) {
            case 0:
                SensorHandleThread sensorHandleThread =
                        sensorHandleThread$$ExternalSyntheticLambda0.f$0;
                sensorHandleThread.mVoiceDevices = set;
                if (set == null) {
                    anyMatch = false;
                } else {
                    Stream stream = set.stream();
                    Set set2 = SensorHandleThread.sBluetoothCommunicationDevices;
                    Objects.requireNonNull(set2);
                    anyMatch =
                            stream.anyMatch(new SensorHandleThread$$ExternalSyntheticLambda2(set2));
                }
                if (!anyMatch) {
                    sensorHandleThread.startProximate();
                    break;
                } else {
                    sensorHandleThread.stopProximate();
                    break;
                }
            default:
                sensorHandleThread$$ExternalSyntheticLambda0.f$0.mMusicDevices = set;
                break;
        }
    }
}
