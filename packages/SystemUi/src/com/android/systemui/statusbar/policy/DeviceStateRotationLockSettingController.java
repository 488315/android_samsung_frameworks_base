package com.android.systemui.statusbar.policy;

import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import com.android.internal.view.RotationPolicy;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import com.android.systemui.util.wrapper.RotationPolicyWrapperImpl;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DeviceStateRotationLockSettingController implements RotationLockController.RotationLockControllerCallback, Dumpable {
    public int mDeviceState = -1;
    public C3382x3b31e417 mDeviceStateCallback;
    public final DeviceStateManager mDeviceStateManager;
    public C3383x3b31e418 mDeviceStateRotationLockSettingsListener;
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
    public final void onRotationLockStateChanged(boolean z) {
        int i = this.mDeviceState;
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager = this.mDeviceStateRotationLockSettingsManager;
        boolean z2 = deviceStateRotationLockSettingsManager.getRotationLockSetting(i) == 1;
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
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(AbstractC0662xaf167275.m61m("onRotationLockStateChanged: state=", int1, " [", access$toDevicePostureString, "], newRotationLocked="), logMessage.getBool1(), ", currentRotationLocked=", logMessage.getBool2());
            }
        };
        LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, function1, null);
        obtain.setInt1(i);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
        if (i == -1 || z == z2) {
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
        obtain2.setBool1(z);
        obtain2.setInt1(i2);
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
        RotationPolicyWrapperImpl rotationPolicyWrapperImpl = (RotationPolicyWrapperImpl) this.mRotationPolicyWrapper;
        boolean isRotationLocked = RotationPolicy.isRotationLocked(rotationPolicyWrapperImpl.context);
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
                StringBuilder m92m = AbstractC0950x8906c950.m92m("readPersistedSetting: caller=", str1, ", state=", int1, " [");
                AppOpItem$$ExternalSyntheticOutline0.m97m(m92m, access$toDevicePostureString, "], rotationLockSettingForState: ", str2, ", shouldBeLocked=");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(m92m, bool1, ", isLocked=", bool2);
            }
        };
        LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, function1, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        obtain.setInt2(rotationLockSetting);
        obtain.setBool1(z);
        obtain.setBool2(isRotationLocked);
        logBuffer.commit(obtain);
        if (rotationLockSetting == 0) {
            return;
        }
        this.mDeviceState = i;
        if (z != isRotationLocked) {
            rotationPolicyWrapperImpl.setRotationLock(z);
        }
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [android.hardware.devicestate.DeviceStateManager$DeviceStateCallback, com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda0] */
    public final void setListening(boolean z) {
        DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = this.mLogger;
        deviceStateRotationLockSettingControllerLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        C3384x10a6d361 c3384x10a6d361 = new Function1() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingControllerLogger$logListeningChange$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("setListening: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, c3384x10a6d361, null);
        obtain.setBool1(true);
        logBuffer.commit(obtain);
        ?? r5 = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda0
            public final void onStateChanged(int i) {
                DeviceStateRotationLockSettingController deviceStateRotationLockSettingController = DeviceStateRotationLockSettingController.this;
                int i2 = deviceStateRotationLockSettingController.mDeviceState;
                final DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger2 = deviceStateRotationLockSettingController.mLogger;
                deviceStateRotationLockSettingControllerLogger2.getClass();
                LogLevel logLevel2 = LogLevel.VERBOSE;
                Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingControllerLogger$logUpdateDeviceState$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        int int1 = logMessage.getInt1();
                        String access$toDevicePostureString = DeviceStateRotationLockSettingControllerLogger.access$toDevicePostureString(DeviceStateRotationLockSettingControllerLogger.this, logMessage.getInt1());
                        int int2 = logMessage.getInt2();
                        String access$toDevicePostureString2 = DeviceStateRotationLockSettingControllerLogger.access$toDevicePostureString(DeviceStateRotationLockSettingControllerLogger.this, logMessage.getInt2());
                        StringBuilder m61m = AbstractC0662xaf167275.m61m("updateDeviceState: current=", int1, " [", access$toDevicePostureString, "], new=");
                        m61m.append(int2);
                        m61m.append(" [");
                        m61m.append(access$toDevicePostureString2);
                        m61m.append("]");
                        return m61m.toString();
                    }
                };
                LogBuffer logBuffer2 = deviceStateRotationLockSettingControllerLogger2.logBuffer;
                LogMessage obtain2 = logBuffer2.obtain("DSRotateLockSettingCon", logLevel2, function1, null);
                obtain2.setInt1(i2);
                obtain2.setInt2(i);
                logBuffer2.commit(obtain2);
                if (Trace.isEnabled()) {
                    Trace.traceBegin(4096L, "updateDeviceState [state=" + i + "]");
                }
                try {
                    if (deviceStateRotationLockSettingController.mDeviceState != i) {
                        deviceStateRotationLockSettingController.readPersistedSetting(i, "updateDeviceState");
                    }
                } finally {
                    Trace.endSection();
                }
            }
        };
        this.mDeviceStateCallback = r5;
        this.mDeviceStateManager.registerCallback(this.mMainExecutor, (DeviceStateManager.DeviceStateCallback) r5);
        C3383x3b31e418 c3383x3b31e418 = new C3383x3b31e418(this);
        this.mDeviceStateRotationLockSettingsListener = c3383x3b31e418;
        ((HashSet) this.mDeviceStateRotationLockSettingsManager.mListeners).add(c3383x3b31e418);
    }
}
