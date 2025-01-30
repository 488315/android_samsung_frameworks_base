package com.android.p038wm.shell.common;

import android.R;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.util.SparseIntArray;
import com.android.p038wm.shell.common.DevicePostureController;
import com.android.p038wm.shell.sysui.ShellInit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DevicePostureController {
    public final Context mContext;
    public final ShellExecutor mMainExecutor;
    public final List mListeners = new ArrayList();
    public final SparseIntArray mDeviceStateToPostureMap = new SparseIntArray();
    public int mDevicePosture = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnDevicePostureChangedListener {
    }

    public DevicePostureController(Context context, ShellInit shellInit, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.common.DevicePostureController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                final DevicePostureController devicePostureController = DevicePostureController.this;
                Context context2 = devicePostureController.mContext;
                for (String str : context2.getResources().getStringArray(R.array.config_system_condition_providers)) {
                    String[] split = str.split(":");
                    if (split.length == 2) {
                        try {
                            devicePostureController.mDeviceStateToPostureMap.put(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        } catch (NumberFormatException unused) {
                        }
                    }
                }
                DeviceStateManager deviceStateManager = (DeviceStateManager) context2.getSystemService(DeviceStateManager.class);
                if (deviceStateManager != null) {
                    deviceStateManager.registerCallback(devicePostureController.mMainExecutor, new DeviceStateManager.DeviceStateCallback() { // from class: com.android.wm.shell.common.DevicePostureController$$ExternalSyntheticLambda2
                        public final void onStateChanged(int i) {
                            DevicePostureController devicePostureController2 = DevicePostureController.this;
                            devicePostureController2.onDevicePostureChanged(devicePostureController2.mDeviceStateToPostureMap.get(i, 0));
                        }
                    });
                }
            }
        }, this);
    }

    public void onDevicePostureChanged(int i) {
        if (i == this.mDevicePosture) {
            return;
        }
        this.mDevicePosture = i;
        ((ArrayList) this.mListeners).forEach(new Consumer() { // from class: com.android.wm.shell.common.DevicePostureController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = DevicePostureController.this.mDevicePosture;
                TabletopModeController tabletopModeController = (TabletopModeController) ((DevicePostureController.OnDevicePostureChangedListener) obj);
                if (tabletopModeController.mDevicePosture != i2) {
                    tabletopModeController.onDevicePostureOrDisplayRotationChanged(i2, tabletopModeController.mDisplayRotation);
                }
            }
        });
    }
}
