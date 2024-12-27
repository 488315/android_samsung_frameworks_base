package com.android.systemui.statusbar.policy;

import android.hardware.devicestate.DeviceStateManager;
import android.util.IndentingPrintWriter;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

public final class DeviceStateRotationLockSettingController implements RotationLockController.RotationLockControllerCallback, Dumpable {
    public int mDeviceState = -1;
    public DeviceStateRotationLockSettingController$$ExternalSyntheticLambda0 mDeviceStateCallback;
    public final DeviceStateManager mDeviceStateManager;
    public DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 mDeviceStateRotationLockSettingsListener;
    public final DeviceStateRotationLockSettingsManager mDeviceStateRotationLockSettingsManager;
    public final DeviceStateRotationLockSettingControllerLogger mLogger;
    public final Executor mMainExecutor;
    public final RotationPolicyWrapper mRotationPolicyWrapper;

    public DeviceStateRotationLockSettingController(RotationPolicyWrapper rotationPolicyWrapper, DeviceStateManager deviceStateManager, Executor executor, DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager, DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger, DumpManager dumpManager) {
        this.mRotationPolicyWrapper = rotationPolicyWrapper;
        this.mDeviceStateManager = deviceStateManager;
        this.mMainExecutor = executor;
        this.mDeviceStateRotationLockSettingsManager = deviceStateRotationLockSettingsManager;
        this.mLogger = deviceStateRotationLockSettingControllerLogger;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager = this.mDeviceStateRotationLockSettingsManager;
        deviceStateRotationLockSettingsManager.getClass();
        indentingPrintWriter.println("DeviceStateRotationLockSettingsManager");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mPostureRotationLockDefaults: " + Arrays.toString(deviceStateRotationLockSettingsManager.mPostureRotationLockDefaults));
        indentingPrintWriter.println("mPostureDefaultRotationLockSettings: " + deviceStateRotationLockSettingsManager.mPostureDefaultRotationLockSettings);
        indentingPrintWriter.println("mDeviceStateRotationLockSettings: " + deviceStateRotationLockSettingsManager.mPostureRotationLockSettings);
        indentingPrintWriter.println("mPostureRotationLockFallbackSettings: " + deviceStateRotationLockSettingsManager.mPostureRotationLockFallbackSettings);
        indentingPrintWriter.println("mSettableDeviceStates: " + deviceStateRotationLockSettingsManager.mSettableDeviceStates);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("DeviceStateRotationLockSettingController");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mDeviceState: " + this.mDeviceState);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
    public final void onRotationLockStateChanged(boolean z, boolean z2) {
        int i = this.mDeviceState;
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager = this.mDeviceStateRotationLockSettingsManager;
        boolean z3 = deviceStateRotationLockSettingsManager.getRotationLockSetting(i) == 1;
        final DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = this.mLogger;
        deviceStateRotationLockSettingControllerLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingControllerLogger$logRotationLockStateChanged$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String access$toDevicePostureString = DeviceStateRotationLockSettingControllerLogger.access$toDevicePostureString(DeviceStateRotationLockSettingControllerLogger.this, logMessage.getInt1());
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "onRotationLockStateChanged: state=", " [", access$toDevicePostureString, "], newRotationLocked="), logMessage.getBool1(), ", currentRotationLocked=", logMessage.getBool2());
            }
        };
        LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z3;
        logBuffer.commit(obtain);
        if (i == -1 || z == z3) {
            return;
        }
        int i2 = this.mDeviceState;
        LogMessage obtain2 = logBuffer.obtain("DSRotateLockSettingCon", logLevel, new Function1() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingControllerLogger$logSaveNewRotationLockSetting$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "saveNewRotationLockSetting: isRotationLocked=" + logMessage.getBool1() + ", state=" + logMessage.getInt1();
            }
        }, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
        logMessageImpl2.bool1 = z;
        logMessageImpl2.int1 = i2;
        logBuffer.commit(obtain2);
        int deviceStateToPosture = deviceStateRotationLockSettingsManager.mPosturesHelper.deviceStateToPosture(i2);
        if (deviceStateRotationLockSettingsManager.mPostureRotationLockFallbackSettings.indexOfKey(deviceStateToPosture) >= 0) {
            deviceStateToPosture = deviceStateRotationLockSettingsManager.mPostureRotationLockFallbackSettings.get(deviceStateToPosture);
        }
        deviceStateRotationLockSettingsManager.mPostureRotationLockSettings.put(deviceStateToPosture, z ? 1 : 2);
        deviceStateRotationLockSettingsManager.persistSettings();
    }

    public final void readPersistedSetting(int i, String str) {
        int rotationLockSetting = this.mDeviceStateRotationLockSettingsManager.getRotationLockSetting(i);
        boolean z = rotationLockSetting == 1;
        RotationPolicyWrapper rotationPolicyWrapper = this.mRotationPolicyWrapper;
        boolean isRotationLocked = rotationPolicyWrapper.isRotationLocked();
        final DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = this.mLogger;
        deviceStateRotationLockSettingControllerLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingControllerLogger$readPersistedSetting$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String access$toDevicePostureString = DeviceStateRotationLockSettingControllerLogger.access$toDevicePostureString(DeviceStateRotationLockSettingControllerLogger.this, logMessage.getInt1());
                int int2 = logMessage.getInt2();
                String str2 = int2 != 0 ? int2 != 1 ? int2 != 2 ? "Unknown" : "UNLOCKED" : "LOCKED" : "IGNORED";
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "readPersistedSetting: caller=", str1, ", state=", " [");
                ConstraintWidget$$ExternalSyntheticOutline0.m(m, access$toDevicePostureString, "], rotationLockSettingForState: ", str2, ", shouldBeLocked=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool1, ", isLocked=", bool2);
            }
        };
        LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, function1, null);
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
