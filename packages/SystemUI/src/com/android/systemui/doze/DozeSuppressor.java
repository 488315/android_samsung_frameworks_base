package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import android.text.TextUtils;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import dagger.Lazy;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DozeSuppressor implements DozeMachine.Part {
    public final Lazy mBiometricUnlockControllerLazy;
    public final AmbientDisplayConfiguration mConfig;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public DozeMachine mMachine;
    public final UserTracker mUserTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.doze.DozeSuppressor$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public DozeSuppressor(DozeHost dozeHost, AmbientDisplayConfiguration ambientDisplayConfiguration, DozeLog dozeLog, Lazy lazy, UserTracker userTracker) {
        new DozeHost.Callback() { // from class: com.android.systemui.doze.DozeSuppressor.1
            @Override // com.android.systemui.doze.DozeHost.Callback
            public final void onAlwaysOnSuppressedChanged(boolean z) {
                DozeSuppressor dozeSuppressor = DozeSuppressor.this;
                DozeMachine.State state = (!dozeSuppressor.mConfig.alwaysOnEnabled(((UserTrackerImpl) dozeSuppressor.mUserTracker).getUserId()) || z) ? DozeMachine.State.DOZE : DozeMachine.State.DOZE_AOD;
                DozeLogger dozeLogger = dozeSuppressor.mDozeLog.mLogger;
                dozeLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(28);
                LogBuffer logBuffer = dozeLogger.buffer;
                LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.bool1 = z;
                logMessageImpl.str1 = state.name();
                logBuffer.commit(obtain);
                dozeSuppressor.mMachine.requestState(state);
            }

            @Override // com.android.systemui.doze.DozeHost.Callback
            public final void onPowerSaveChanged() {
                DozeSuppressor dozeSuppressor = DozeSuppressor.this;
                DozeMachine.State state = ((BatteryControllerImpl) ((DozeServiceHost) dozeSuppressor.mDozeHost).mBatteryController).mAodPowerSave ? DozeMachine.State.DOZE : (dozeSuppressor.mMachine.getState() == DozeMachine.State.DOZE && dozeSuppressor.mConfig.alwaysOnEnabled(((UserTrackerImpl) dozeSuppressor.mUserTracker).getUserId())) ? DozeMachine.State.DOZE_AOD : null;
                if (state != null) {
                    boolean z = ((BatteryControllerImpl) ((DozeServiceHost) dozeSuppressor.mDozeHost).mBatteryController).mAodPowerSave;
                    DozeLogger dozeLogger = dozeSuppressor.mDozeLog.mLogger;
                    dozeLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(27);
                    LogBuffer logBuffer = dozeLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.bool1 = z;
                    logMessageImpl.str1 = state.name();
                    logBuffer.commit(obtain);
                    dozeSuppressor.mMachine.requestState(state);
                }
            }
        };
        this.mDozeHost = dozeHost;
        this.mConfig = ambientDisplayConfiguration;
        this.mDozeLog = dozeLog;
        this.mBiometricUnlockControllerLazy = lazy;
        this.mUserTracker = userTracker;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void dump(PrintWriter printWriter) {
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, " isCarModeEnabled=false", " hasPendingAuth=");
        m.append(((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).hasPendingAuthentication());
        printWriter.println(m.toString());
        StringBuilder sb = new StringBuilder(" isProvisioned=");
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mDozeHost;
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) dozeServiceHost.mDeviceProvisionedController;
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, deviceProvisionedControllerImpl.deviceProvisioned.get() && deviceProvisionedControllerImpl.isCurrentUserSetup(), printWriter, " isAlwaysOnSuppressed="), dozeServiceHost.mAlwaysOnSuppressed, printWriter, " aodPowerSaveActive="), ((BatteryControllerImpl) dozeServiceHost.mBatteryController).mAodPowerSave, printWriter);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        if (AnonymousClass2.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()] != 1) {
            return;
        }
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) ((DozeServiceHost) this.mDozeHost).mDeviceProvisionedController;
        String str = !(deviceProvisionedControllerImpl.deviceProvisioned.get() && deviceProvisionedControllerImpl.isCurrentUserSetup()) ? "device_unprovisioned" : ((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).hasPendingAuthentication() ? "has_pending_auth" : null;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        DozeLogger dozeLogger = this.mDozeLog.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
        this.mMachine.requestState(DozeMachine.State.FINISH);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void destroy() {
    }
}
