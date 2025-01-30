package com.android.systemui.doze;

import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DozeTriggers$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DozeTriggers f$0;

    public /* synthetic */ DozeTriggers$$ExternalSyntheticLambda3(DozeTriggers dozeTriggers, int i) {
        this.$r8$classId = i;
        this.f$0 = dozeTriggers;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DozeTriggers dozeTriggers = this.f$0;
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
                                return AbstractC0866xb1ce8deb.m86m("Prox changed while pulsing. setIgnoreTouchWhilePulsing=", ((LogMessage) obj2).getBool1());
                            }
                        };
                        LogBuffer logBuffer = dozeLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSetIgnoreTouchWhilePulsing$2, null);
                        obtain.setBool1(z);
                        logBuffer.commit(obtain);
                        DozeServiceHost dozeServiceHost = (DozeServiceHost) dozeTriggers.mDozeHost;
                        if (z != dozeServiceHost.mIgnoreTouchWhilePulsing) {
                            dozeServiceHost.mDozeLog.tracePulseTouchDisabledByProx(z);
                        }
                        dozeServiceHost.mIgnoreTouchWhilePulsing = z;
                        if (((StatusBarStateControllerImpl) dozeServiceHost.mStatusBarStateController).mIsDozing && z) {
                            dozeServiceHost.mNotificationShadeWindowViewController.cancelCurrentTouch();
                        }
                    }
                    if (!booleanValue || (!z2 && !z3)) {
                        if (z && z4) {
                            LogBuffer.log$default(dozeLog.mLogger.buffer, "DozeLog", LogLevel.DEBUG, "Prox NEAR, starting pausing AOD countdown", null, 8, null);
                            dozeTriggers.mMachine.requestState(state2);
                            break;
                        }
                    } else {
                        LogBuffer.log$default(dozeLog.mLogger.buffer, "DozeLog", LogLevel.DEBUG, "Prox FAR, unpausing AOD", null, 8, null);
                        dozeTriggers.mMachine.requestState(state3);
                        break;
                    }
                } else {
                    LogBuffer.log$default(dozeLog.mLogger.buffer, "DozeLog", LogLevel.DEBUG, "onProximityFar called during transition. Ignoring sensor response.", null, 8, null);
                    break;
                }
                break;
            case 1:
                DozeTriggers dozeTriggers2 = this.f$0;
                dozeTriggers2.mUiEventLogger.log((DozeTriggers.DozingUpdateUiEvent) obj, dozeTriggers2.mSessionTracker.getSessionId(1));
                break;
            case 2:
                DozeTriggers dozeTriggers3 = this.f$0;
                dozeTriggers3.mUiEventLogger.log((DozeTriggers.DozingUpdateUiEvent) obj, dozeTriggers3.mSessionTracker.getSessionId(1));
                break;
            default:
                DozeTriggers dozeTriggers4 = this.f$0;
                dozeTriggers4.mUiEventLogger.log((DozeTriggers.DozingUpdateUiEvent) obj, dozeTriggers4.mSessionTracker.getSessionId(1));
                break;
        }
    }
}
