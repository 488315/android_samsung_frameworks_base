package com.android.systemui.statusbar.policy;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.util.SparseIntArray;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DevicePostureControllerImpl implements DevicePostureController {
    public final List mListeners = new ArrayList();
    public int mCurrentDevicePosture = 0;
    public final SparseIntArray mDeviceStateToPostureMap = new SparseIntArray();

    public DevicePostureControllerImpl(Context context, DeviceStateManager deviceStateManager, Executor executor) {
        for (String str : context.getResources().getStringArray(R.array.config_system_condition_providers)) {
            String[] split = str.split(":");
            if (split.length == 2) {
                try {
                    this.mDeviceStateToPostureMap.put(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                } catch (NumberFormatException unused) {
                }
            }
        }
        deviceStateManager.registerCallback(executor, new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerImpl$$ExternalSyntheticLambda0
            public final void onStateChanged(int i) {
                final DevicePostureControllerImpl devicePostureControllerImpl = DevicePostureControllerImpl.this;
                devicePostureControllerImpl.getClass();
                Assert.isMainThread();
                devicePostureControllerImpl.mCurrentDevicePosture = devicePostureControllerImpl.mDeviceStateToPostureMap.get(i, 0);
                ((ArrayList) devicePostureControllerImpl.mListeners).forEach(new Consumer() { // from class: com.android.systemui.statusbar.policy.DevicePostureControllerImpl$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((DevicePostureController.Callback) obj).onPostureChanged(DevicePostureControllerImpl.this.mCurrentDevicePosture);
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        Assert.isMainThread();
        ((ArrayList) this.mListeners).add((DevicePostureController.Callback) obj);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        Assert.isMainThread();
        ((ArrayList) this.mListeners).remove((DevicePostureController.Callback) obj);
    }
}
