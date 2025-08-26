package com.android.systemui.statusbar.policy;

import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.internal.view.RotationPolicy;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public final class RotationLockControllerImpl implements RotationLockController {
    public final CopyOnWriteArrayList mCallbacks;
    public final RotationPolicyWrapper mRotationPolicy;
    public final AnonymousClass1 mRotationPolicyListener;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.internal.view.RotationPolicy$RotationPolicyListener, com.android.systemui.statusbar.policy.RotationLockControllerImpl$1] */
    public RotationLockControllerImpl(RotationPolicyWrapper rotationPolicyWrapper, Optional<DeviceStateRotationLockSettingController> optional, String[] strArr) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.mCallbacks = copyOnWriteArrayList;
        ?? r1 = new RotationPolicy.RotationPolicyListener() { // from class: com.android.systemui.statusbar.policy.RotationLockControllerImpl.1
            public final void onChange() {
                RotationLockControllerImpl rotationLockControllerImpl = RotationLockControllerImpl.this;
                Iterator it = rotationLockControllerImpl.mCallbacks.iterator();
                while (it.hasNext()) {
                    RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = (RotationLockController.RotationLockControllerCallback) it.next();
                    RotationPolicyWrapper rotationPolicyWrapper2 = rotationLockControllerImpl.mRotationPolicy;
                    rotationLockControllerCallback.onRotationLockStateChanged(rotationPolicyWrapper2.isRotationLocked(), rotationPolicyWrapper2.isRotationLockToggleVisible());
                }
            }
        };
        this.mRotationPolicyListener = r1;
        this.mRotationPolicy = rotationPolicyWrapper;
        boolean z = strArr.length > 0;
        if (z && optional.isPresent()) {
            copyOnWriteArrayList.add(optional.get());
        }
        rotationPolicyWrapper.registerRotationPolicyListener(r1, -1);
        if (z && optional.isPresent()) {
            final DeviceStateRotationLockSettingController deviceStateRotationLockSettingController = optional.get();
            DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = deviceStateRotationLockSettingController.mLogger;
            deviceStateRotationLockSettingControllerLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda1 deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda1 = new DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda1(1);
            LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda1, null);
            ((LogMessageImpl) logMessageObtain).bool1 = true;
            logBuffer.commit(logMessageObtain);
            DeviceStateManager.DeviceStateCallback deviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda0
                public final void onDeviceStateChanged(DeviceState deviceState) {
                    DeviceStateRotationLockSettingController deviceStateRotationLockSettingController2 = deviceStateRotationLockSettingController;
                    int i = deviceStateRotationLockSettingController2.mDeviceState;
                    int identifier = deviceState.getIdentifier();
                    DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger2 = deviceStateRotationLockSettingController2.mLogger;
                    deviceStateRotationLockSettingControllerLogger2.getClass();
                    LogLevel logLevel2 = LogLevel.VERBOSE;
                    DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0 deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0 = new DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0(deviceStateRotationLockSettingControllerLogger2, 2);
                    LogBuffer logBuffer2 = deviceStateRotationLockSettingControllerLogger2.logBuffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("DSRotateLockSettingCon", logLevel2, deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
                    logMessageImpl.int1 = i;
                    logMessageImpl.int2 = identifier;
                    logBuffer2.commit(logMessageObtain2);
                    try {
                        if (Trace.isEnabled()) {
                            Trace.traceBegin(4096L, "updateDeviceState [state=" + deviceState.getIdentifier() + "]");
                        }
                        if (deviceStateRotationLockSettingController2.mDeviceState == deviceState.getIdentifier()) {
                            return;
                        }
                        deviceStateRotationLockSettingController2.readPersistedSetting(deviceState.getIdentifier(), "updateDeviceState");
                    } finally {
                        Trace.endSection();
                    }
                }
            };
            deviceStateRotationLockSettingController.getClass();
            deviceStateRotationLockSettingController.mDeviceStateManager.registerCallback(deviceStateRotationLockSettingController.mMainExecutor, deviceStateCallback);
            DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 deviceStateRotationLockSettingController$$ExternalSyntheticLambda1 = new DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1(deviceStateRotationLockSettingController);
            deviceStateRotationLockSettingController.getClass();
            deviceStateRotationLockSettingController.mDeviceStateAutoRotateSettingManager.registerListener(deviceStateRotationLockSettingController$$ExternalSyntheticLambda1);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = (RotationLockController.RotationLockControllerCallback) obj;
        this.mCallbacks.add(rotationLockControllerCallback);
        RotationPolicyWrapper rotationPolicyWrapper = this.mRotationPolicy;
        rotationLockControllerCallback.onRotationLockStateChanged(rotationPolicyWrapper.isRotationLocked(), rotationPolicyWrapper.isRotationLockToggleVisible());
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final int getRotationLockOrientation() {
        return this.mRotationPolicy.getRotationLockOrientation();
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final boolean isCameraRotationEnabled() {
        return this.mRotationPolicy.isCameraRotationEnabled();
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final boolean isRotationLocked() {
        return this.mRotationPolicy.isRotationLocked();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((RotationLockController.RotationLockControllerCallback) obj);
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final void setRotationLocked(String str, boolean z) {
        this.mRotationPolicy.setRotationLock(z, str);
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController
    public final void setRotationLockedAtAngle(int i, String str, boolean z) {
        this.mRotationPolicy.setRotationLockAtAngle(z, i, str);
    }
}
