package com.android.systemui.doze;

import android.view.Display;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DozeScreenState$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DozeScreenState f$0;

    public /* synthetic */ DozeScreenState$$ExternalSyntheticLambda0(DozeScreenState dozeScreenState, int i) {
        this.$r8$classId = i;
        this.f$0 = dozeScreenState;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DozeScreenState dozeScreenState = this.f$0;
                UdfpsController udfpsController = dozeScreenState.mUdfpsController;
                if (udfpsController != null && udfpsController.mOnFingerDown) {
                    int i = dozeScreenState.mPendingScreenState;
                    DozeLogger dozeLogger = dozeScreenState.mDozeLog.mLogger;
                    dozeLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    DozeLogger$logDisplayStateDelayedByUdfps$2 dozeLogger$logDisplayStateDelayedByUdfps$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logDisplayStateDelayedByUdfps$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return PathParser$$ExternalSyntheticOutline0.m29m("Delaying display state change to: ", ((LogMessage) obj).getStr1(), " due to UDFPS activity");
                        }
                    };
                    LogBuffer logBuffer = dozeLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logDisplayStateDelayedByUdfps$2, null);
                    obtain.setStr1(Display.stateToString(i));
                    logBuffer.commit(obtain);
                    dozeScreenState.mHandler.postDelayed(dozeScreenState.mApplyPendingScreenState, 1200L);
                    break;
                } else {
                    dozeScreenState.applyScreenState(dozeScreenState.mPendingScreenState, false);
                    dozeScreenState.mPendingScreenState = 0;
                    break;
                }
                break;
            default:
                ((PluginAODManager) this.f$0.mPluginAODManagerLazy.get()).enableTouch(true);
                break;
        }
    }
}
