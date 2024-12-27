package com.android.systemui.statusbar.policy;

import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.internal.view.RotationPolicy;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class RotationLockControllerImpl implements RotationLockController {
    public final CopyOnWriteArrayList mCallbacks;
    public final RotationPolicyWrapper mRotationPolicy;
    public final AnonymousClass1 mRotationPolicyListener;

    public RotationLockControllerImpl(RotationPolicyWrapper rotationPolicyWrapper, final DeviceStateRotationLockSettingController deviceStateRotationLockSettingController, String[] strArr) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.mCallbacks = copyOnWriteArrayList;
        RotationPolicy.RotationPolicyListener rotationPolicyListener = new RotationPolicy.RotationPolicyListener() { // from class: com.android.systemui.statusbar.policy.RotationLockControllerImpl.1
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
        this.mRotationPolicy = rotationPolicyWrapper;
        boolean z = strArr.length > 0;
        if (z) {
            copyOnWriteArrayList.add(deviceStateRotationLockSettingController);
        }
        rotationPolicyWrapper.registerRotationPolicyListener(rotationPolicyListener, -1);
        if (z) {
            DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger = deviceStateRotationLockSettingController.mLogger;
            deviceStateRotationLockSettingControllerLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            DeviceStateRotationLockSettingControllerLogger$logListeningChange$2 deviceStateRotationLockSettingControllerLogger$logListeningChange$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingControllerLogger$logListeningChange$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("setListening: ", ((LogMessage) obj).getBool1());
                }
            };
            LogBuffer logBuffer = deviceStateRotationLockSettingControllerLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("DSRotateLockSettingCon", logLevel, deviceStateRotationLockSettingControllerLogger$logListeningChange$2, null);
            ((LogMessageImpl) obtain).bool1 = true;
            logBuffer.commit(obtain);
            DeviceStateManager.DeviceStateCallback deviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController$$ExternalSyntheticLambda0
                public final void onDeviceStateChanged(DeviceState deviceState) {
                    DeviceStateRotationLockSettingController deviceStateRotationLockSettingController2 = DeviceStateRotationLockSettingController.this;
                    int i = deviceStateRotationLockSettingController2.mDeviceState;
                    int identifier = deviceState.getIdentifier();
                    final DeviceStateRotationLockSettingControllerLogger deviceStateRotationLockSettingControllerLogger2 = deviceStateRotationLockSettingController2.mLogger;
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
                            StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "updateDeviceState: current=", " [", access$toDevicePostureString, "], new=");
                            m.append(int2);
                            m.append(" [");
                            m.append(access$toDevicePostureString2);
                            m.append("]");
                            return m.toString();
                        }
                    };
                    LogBuffer logBuffer2 = deviceStateRotationLockSettingControllerLogger2.logBuffer;
                    LogMessage obtain2 = logBuffer2.obtain("DSRotateLockSettingCon", logLevel2, function1, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
                    logMessageImpl.int1 = i;
                    logMessageImpl.int2 = identifier;
                    logBuffer2.commit(obtain2);
                    try {
                        if (Trace.isEnabled()) {
                            Trace.traceBegin(4096L, "updateDeviceState [state=" + deviceState.getIdentifier() + "]");
                        }
                        if (deviceStateRotationLockSettingController2.mDeviceState != deviceState.getIdentifier()) {
                            deviceStateRotationLockSettingController2.readPersistedSetting(deviceState.getIdentifier(), "updateDeviceState");
                        }
                        Trace.endSection();
                    } catch (Throwable th) {
                        Trace.endSection();
                        throw th;
                    }
                }
            };
            deviceStateRotationLockSettingController.getClass();
            deviceStateRotationLockSettingController.mDeviceStateManager.registerCallback(deviceStateRotationLockSettingController.mMainExecutor, deviceStateCallback);
            DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1 deviceStateRotationLockSettingController$$ExternalSyntheticLambda1 = new DeviceStateRotationLockSettingController$$ExternalSyntheticLambda1(deviceStateRotationLockSettingController);
            deviceStateRotationLockSettingController.getClass();
            ((HashSet) deviceStateRotationLockSettingController.mDeviceStateRotationLockSettingsManager.mListeners).add(deviceStateRotationLockSettingController$$ExternalSyntheticLambda1);
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
