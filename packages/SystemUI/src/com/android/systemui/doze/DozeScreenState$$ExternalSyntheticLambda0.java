package com.android.systemui.doze;

import android.view.Display;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes2.dex */
public final /* synthetic */ class DozeScreenState$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DozeScreenState f$0;

    public /* synthetic */ DozeScreenState$$ExternalSyntheticLambda0(DozeScreenState dozeScreenState, int i) {
        this.$r8$classId = i;
        this.f$0 = dozeScreenState;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DozeScreenState dozeScreenState = this.f$0;
        switch (i) {
            case 0:
                ((PluginAODManager) dozeScreenState.mPluginAODManagerLazy.get()).enableTouch(true);
                break;
            default:
                UdfpsController udfpsController = dozeScreenState.mUdfpsController;
                if (udfpsController != null && udfpsController.mOnFingerDown) {
                    int i2 = dozeScreenState.mPendingScreenState;
                    DozeLogger dozeLogger = dozeScreenState.mDozeLog.mLogger;
                    dozeLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(9);
                    LogBuffer logBuffer = dozeLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) logMessageObtain).str1 = Display.stateToString(i2);
                    logBuffer.commit(logMessageObtain);
                    dozeScreenState.mHandler.postDelayed(dozeScreenState.mApplyPendingScreenState, 1200L);
                    break;
                } else {
                    dozeScreenState.applyScreenState(dozeScreenState.mPendingScreenState, false);
                    dozeScreenState.mPendingScreenState = 0;
                    break;
                }
                break;
        }
    }
}
