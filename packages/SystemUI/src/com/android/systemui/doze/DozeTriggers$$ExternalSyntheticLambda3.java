package com.android.systemui.doze;

import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DozeTriggers f$0;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda3(DozeTriggers dozeTriggers, int i) {
        this.$r8$classId = i;
        this.f$0 = dozeTriggers;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        DozeTriggers dozeTriggers = this.f$0;
        switch (i) {
            case 0:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                boolean isExecutingTransition = dozeTriggers.mMachine.isExecutingTransition();
                DozeLog dozeLog = dozeTriggers.mDozeLog;
                if (!isExecutingTransition) {
                    boolean z = !booleanValue;
                    DozeMachine.State state = dozeTriggers.mMachine.getState();
                    boolean z2 = state == DozeMachine.State.DOZE_AOD_PAUSED;
                    DozeMachine.State state2 = DozeMachine.State.DOZE_AOD_PAUSING;
                    boolean z3 = state == state2;
                    DozeMachine.State state3 = DozeMachine.State.DOZE_AOD;
                    boolean z4 = state == state3;
                    if (state == DozeMachine.State.DOZE_PULSING || state == DozeMachine.State.DOZE_PULSING_BRIGHT) {
                        DozeLogger dozeLogger = dozeLog.mLogger;
                        dozeLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        DozeLogger$logSetIgnoreTouchWhilePulsing$2 dozeLogger$logSetIgnoreTouchWhilePulsing$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logSetIgnoreTouchWhilePulsing$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Prox changed while pulsing. setIgnoreTouchWhilePulsing=", ((LogMessage) obj2).getBool1());
                            }
                        };
                        LogBuffer logBuffer = dozeLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSetIgnoreTouchWhilePulsing$2, null);
                        ((LogMessageImpl) obtain).bool1 = z;
                        logBuffer.commit(obtain);
                        DozeServiceHost dozeServiceHost = (DozeServiceHost) dozeTriggers.mDozeHost;
                        if (z != dozeServiceHost.mIgnoreTouchWhilePulsing) {
                            DozeLogger dozeLogger2 = dozeServiceHost.mDozeLog.mLogger;
                            dozeLogger2.getClass();
                            LogLevel logLevel2 = LogLevel.DEBUG;
                            DozeLogger$logPulseTouchDisabledByProx$2 dozeLogger$logPulseTouchDisabledByProx$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logPulseTouchDisabledByProx$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Pulse touch modified by prox, disabled=", ((LogMessage) obj2).getBool1());
                                }
                            };
                            LogBuffer logBuffer2 = dozeLogger2.buffer;
                            LogMessage obtain2 = logBuffer2.obtain("DozeLog", logLevel2, dozeLogger$logPulseTouchDisabledByProx$2, null);
                            ((LogMessageImpl) obtain2).bool1 = z;
                            logBuffer2.commit(obtain2);
                        }
                        dozeServiceHost.mIgnoreTouchWhilePulsing = z;
                        if (((StatusBarStateControllerImpl) dozeServiceHost.mStatusBarStateController).mIsDozing && z) {
                            dozeServiceHost.mNotificationShadeWindowViewController.cancelCurrentTouch();
                        }
                    }
                    if (!booleanValue || (!z2 && !z3)) {
                        if (z && z4) {
                            DozeLogger dozeLogger3 = dozeLog.mLogger;
                            dozeLogger3.getClass();
                            dozeLogger3.buffer.log("DozeLog", LogLevel.DEBUG, "Prox NEAR, starting pausing AOD countdown", null);
                            dozeTriggers.mMachine.requestState(state2);
                            break;
                        }
                    } else {
                        DozeLogger dozeLogger4 = dozeLog.mLogger;
                        dozeLogger4.getClass();
                        dozeLogger4.buffer.log("DozeLog", LogLevel.DEBUG, "Prox FAR, unpausing AOD", null);
                        dozeTriggers.mMachine.requestState(state3);
                        break;
                    }
                } else {
                    DozeLogger dozeLogger5 = dozeLog.mLogger;
                    dozeLogger5.getClass();
                    dozeLogger5.buffer.log("DozeLog", LogLevel.DEBUG, "onProximityFar called during transition. Ignoring sensor response.", null);
                    break;
                }
                break;
            case 1:
                dozeTriggers.mUiEventLogger.log((DozeTriggers.DozingUpdateUiEvent) obj, dozeTriggers.mSessionTracker.getSessionId(1));
                break;
            case 2:
                dozeTriggers.mUiEventLogger.log((DozeTriggers.DozingUpdateUiEvent) obj, dozeTriggers.mSessionTracker.getSessionId(1));
                break;
            default:
                dozeTriggers.mUiEventLogger.log((DozeTriggers.DozingUpdateUiEvent) obj, dozeTriggers.mSessionTracker.getSessionId(1));
                break;
        }
    }
}
