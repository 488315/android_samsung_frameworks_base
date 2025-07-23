package com.android.systemui.statusbar.policy;

import android.hardware.devicestate.DeviceStateManager;
import android.util.IndentingPrintWriter;
import com.android.settingslib.devicestate.DeviceStateAutoRotateSettingManager;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceStateRotationLockSettingController implements RotationLockController.RotationLockControllerCallback, Dumpable {
    public int mDeviceState = -1;
    public DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 mDeviceStateAutoRotateSettingListener;
    public final DeviceStateAutoRotateSettingManager mDeviceStateAutoRotateSettingManager;
    public DeviceStateRotationLockSettingController$$ExternalSyntheticLambda0 mDeviceStateCallback;
    public final DeviceStateManager mDeviceStateManager;
    public final DeviceStateRotationLockSettingControllerLogger mLogger;
    public final Executor mMainExecutor;
    public final RotationPolicyWrapper mRotationPolicyWrapper;

    public DeviceStateRotationLockSettingController(RotationPolicyWrapper rotationPolicyWrapper, DeviceStateManager deviceStateManager, Executor executor, DeviceStateAutoRotateSettingManager deviceStateAutoRotateSettingManager, DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger, DumpManager dumpManager) {
        this.mRotationPolicyWrapper = rotationPolicyWrapper;
        this.mDeviceStateManager = deviceStateManager;
        this.mMainExecutor = executor;
        this.mDeviceStateAutoRotateSettingManager = deviceStateAutoRotateSettingManager;
        this.mLogger = deviceStateRotationLockSettingControllerLogger;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        this.mDeviceStateAutoRotateSettingManager.dump(printWriter, null);
        indentingPrintWriter.println("DeviceStateRotationLockSettingController");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mDeviceState: " + this.mDeviceState);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
    public final void onRotationLockStateChanged(boolean z, boolean z2) {
        int i = this.mDeviceState;
        DeviceStateAutoRotateSettingManager deviceStateAutoRotateSettingManager = this.mDeviceStateAutoRotateSettingManager;
        boolean isRotationLocked = deviceStateAutoRotateSettingManager.isRotationLocked(i);
        DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = this.mLogger;
        deviceStateRotationLockSettingControllerLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0 deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0 = new DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0(deviceStateRotationLockSettingControllerLogger, 0);
        LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = isRotationLocked;
        logBuffer.commit(obtain);
        if (i == -1 || z == isRotationLocked) {
            return;
        }
        int i2 = this.mDeviceState;
        LogMessage obtain2 = logBuffer.obtain("DSRotateLockSettingCon", logLevel, new DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda1(0), null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
        logMessageImpl2.bool1 = z;
        logMessageImpl2.int1 = i2;
        logBuffer.commit(obtain2);
        deviceStateAutoRotateSettingManager.updateSetting(i2, z);
    }

    public final void readPersistedSetting(int i, String str) {
        int rotationLockSetting = this.mDeviceStateAutoRotateSettingManager.getRotationLockSetting(i);
        boolean z = rotationLockSetting == 1;
        RotationPolicyWrapper rotationPolicyWrapper = this.mRotationPolicyWrapper;
        boolean isRotationLocked = rotationPolicyWrapper.isRotationLocked();
        DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = this.mLogger;
        deviceStateRotationLockSettingControllerLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0 deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0 = new DeviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0(deviceStateRotationLockSettingControllerLogger, 1);
        LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, deviceStateRotationLockSettingControllerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = rotationLockSetting;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = isRotationLocked;
        logBuffer.commit(obtain);
        if (rotationLockSetting == 0) {
            return;
        }
        this.mDeviceState = i;
        if (z != isRotationLocked) {
            rotationPolicyWrapper.setRotationLock(z, "DeviceStateRotationLockSettingController#readPersistedSetting");
        }
    }
}
