package com.android.settingslib.bluetooth;

import com.android.settingslib.utils.ThreadUtils;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeUiController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AmbientVolumeUiController f$0;

    public /* synthetic */ AmbientVolumeUiController$$ExternalSyntheticLambda0(AmbientVolumeUiController ambientVolumeUiController, int i) {
        this.$r8$classId = i;
        this.f$0 = ambientVolumeUiController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        AmbientVolumeUiController ambientVolumeUiController = this.f$0;
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        ambientVolumeUiController.getClass();
        switch (i) {
            case 0:
                ambientVolumeUiController.mVolumeController.registerCallback(ThreadUtils.getBackgroundExecutor(), cachedBluetoothDevice.mDevice);
                break;
            case 1:
                cachedBluetoothDevice.unregisterCallback(ambientVolumeUiController);
                ambientVolumeUiController.mVolumeController.unregisterCallback(cachedBluetoothDevice.mDevice);
                break;
            case 2:
                cachedBluetoothDevice.unregisterCallback(ambientVolumeUiController);
                ambientVolumeUiController.mVolumeController.unregisterCallback(cachedBluetoothDevice.mDevice);
                break;
            case 3:
                ListeningExecutorService backgroundExecutor = ThreadUtils.getBackgroundExecutor();
                cachedBluetoothDevice.getClass();
                Objects.requireNonNull(backgroundExecutor, "executor cannot be null");
                ((ConcurrentHashMap) cachedBluetoothDevice.mCallbackExecutorMap).put(ambientVolumeUiController, backgroundExecutor);
                ambientVolumeUiController.mVolumeController.registerCallback(ThreadUtils.getBackgroundExecutor(), cachedBluetoothDevice.mDevice);
                break;
            default:
                ListeningExecutorService backgroundExecutor2 = ThreadUtils.getBackgroundExecutor();
                cachedBluetoothDevice.getClass();
                Objects.requireNonNull(backgroundExecutor2, "executor cannot be null");
                ((ConcurrentHashMap) cachedBluetoothDevice.mCallbackExecutorMap).put(ambientVolumeUiController, backgroundExecutor2);
                ambientVolumeUiController.mVolumeController.registerCallback(ThreadUtils.getBackgroundExecutor(), cachedBluetoothDevice.mDevice);
                break;
        }
    }
}
