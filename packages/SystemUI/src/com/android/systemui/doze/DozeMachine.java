package com.android.systemui.doze;

import android.content.res.Configuration;
import android.hardware.display.AmbientDisplayConfiguration;
import android.util.Log;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.util.Preconditions;
import com.android.systemui.LsRune;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.util.Assert;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class DozeMachine {
    public static final boolean DEBUG = DozeService.DEBUG;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final Service mDozeService;
    public int mMODReason;
    public final Part[] mParts;
    public int mPulseReason;
    public final ArrayList mQueuedRequests = new ArrayList();
    public State mState;
    public State mStateBeforeMOD;
    public int mUiModeType;
    public final WakeLock mWakeLock;
    public boolean mWakeLockHeldForCurrentState;

    public interface Service {

        public class Delegate implements Service {
            public final Service mDelegate;

            public Delegate(Service service) {
                this.mDelegate = service;
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public final void finish() {
                this.mDelegate.finish();
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public final void requestWakeUp(int i) {
                this.mDelegate.requestWakeUp(i);
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public final void semSetDozeScreenBrightness(int i, int i2) {
                this.mDelegate.semSetDozeScreenBrightness(i, i2);
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void setDozeScreenBrightness(int i) {
                this.mDelegate.setDozeScreenBrightness(i);
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void setDozeScreenBrightnessFloat(float f) {
                this.mDelegate.setDozeScreenBrightnessFloat(f);
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public final void setDozeScreenState(int i, boolean z) {
                this.mDelegate.setDozeScreenState(i, z);
            }
        }

        void finish();

        void requestWakeUp(int i);

        void semSetDozeScreenBrightness(int i, int i2);

        void setDozeScreenBrightness(int i);

        void setDozeScreenBrightnessFloat(float f);

        void setDozeScreenState(int i, boolean z);
    }

    public enum State {
        UNINITIALIZED,
        INITIALIZED,
        DOZE,
        DOZE_SUSPEND_TRIGGERS,
        DOZE_AOD,
        DOZE_REQUEST_PULSE,
        DOZE_PULSING,
        DOZE_PULSING_BRIGHT,
        DOZE_PULSE_DONE,
        FINISH,
        DOZE_AOD_PAUSED,
        DOZE_AOD_PAUSING,
        DOZE_AOD_DOCKED,
        DOZE_MOD,
        DOZE_TRANSITION_ENDED,
        DOZE_DISPLAY_STATE_ON
    }

    public DozeMachine(Service service, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, WakefulnessLifecycle wakefulnessLifecycle, DozeLog dozeLog, DockManager dockManager, DozeHost dozeHost, Part[] partArr, UserTracker userTracker) {
        State state = State.UNINITIALIZED;
        this.mState = state;
        this.mWakeLockHeldForCurrentState = false;
        this.mUiModeType = 1;
        this.mStateBeforeMOD = state;
        this.mDozeService = service;
        this.mWakeLock = wakeLock;
        this.mDozeLog = dozeLog;
        this.mDozeHost = dozeHost;
        this.mParts = partArr;
        for (Part part : partArr) {
            part.setDozeMachine(this);
        }
    }

    public final State getState() {
        Assert.isMainThread();
        if (!isExecutingTransition()) {
            return this.mState;
        }
        throw new IllegalStateException("Cannot get state because there were pending transitions: " + this.mQueuedRequests);
    }

    public final boolean isExecutingTransition() {
        return !this.mQueuedRequests.isEmpty();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        int i = configuration.uiMode & 15;
        if (this.mUiModeType == i) {
            return;
        }
        this.mUiModeType = i;
        for (Part part : this.mParts) {
            part.getClass();
        }
    }

    public final void requestState(State state) {
        Preconditions.checkArgument(state != State.DOZE_REQUEST_PULSE);
        requestState(state, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transitionTo(State state, int i) {
        State state2;
        int iOrdinal;
        int iOrdinal2;
        int iOrdinal3;
        State state3 = this.mState;
        State state4 = State.FINISH;
        DozeLog dozeLog = this.mDozeLog;
        if (state3 == state4) {
            state2 = state4;
        } else if (this.mUiModeType == 3) {
            int iOrdinal4 = state.ordinal();
            if (iOrdinal4 != 2 && iOrdinal4 != 4) {
                switch (iOrdinal4) {
                    default:
                        int iOrdinal5 = state.ordinal();
                        if (iOrdinal5 == 5 || iOrdinal5 == 6 || iOrdinal5 == 7 || iOrdinal5 == 12) {
                        }
                    case 10:
                    case 11:
                    case 12:
                        Log.i("DozeMachine", "Doze is suppressed with all triggers disabled as car mode is active");
                        DozeLogger dozeLogger = dozeLog.mLogger;
                        dozeLogger.getClass();
                        LogLevel logLevel = LogLevel.INFO;
                        DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda3 = new DozeLogger$$ExternalSyntheticLambda3(6);
                        LogBuffer logBuffer = dozeLogger.buffer;
                        logBuffer.commit(logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda3, null));
                        state2 = State.DOZE_SUSPEND_TRIGGERS;
                        break;
                }
            }
            Log.i("DozeMachine", "Doze is suppressed with all triggers disabled as car mode is active");
            DozeLogger dozeLogger2 = dozeLog.mLogger;
            dozeLogger2.getClass();
            LogLevel logLevel2 = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda32 = new DozeLogger$$ExternalSyntheticLambda3(6);
            LogBuffer logBuffer2 = dozeLogger2.buffer;
            logBuffer2.commit(logBuffer2.obtain("DozeLog", logLevel2, dozeLogger$$ExternalSyntheticLambda32, null));
            state2 = State.DOZE_SUSPEND_TRIGGERS;
        } else if (((DozeServiceHost) this.mDozeHost).mAlwaysOnSuppressed) {
            state.getClass();
            if (state == State.DOZE_AOD || state == State.DOZE_AOD_DOCKED) {
                Log.i("DozeMachine", "Doze is suppressed by an app. Suppressing state: " + state);
                DozeLogger dozeLogger3 = dozeLog.mLogger;
                dozeLogger3.getClass();
                LogLevel logLevel3 = LogLevel.INFO;
                DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda33 = new DozeLogger$$ExternalSyntheticLambda3(5);
                LogBuffer logBuffer3 = dozeLogger3.buffer;
                LogMessage logMessageObtain = logBuffer3.obtain("DozeLog", logLevel3, dozeLogger$$ExternalSyntheticLambda33, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = state.name();
                logMessageImpl.str2 = SystemUIAnalytics.QPNE_KEY_APP;
                logBuffer3.commit(logMessageObtain);
                state2 = State.DOZE;
            } else {
                State state5 = this.mState;
                State state6 = State.DOZE_AOD_PAUSED;
                if ((state5 == state6 || state5 == State.DOZE_AOD_PAUSING || state5 == State.DOZE_AOD || state5 == State.DOZE || state5 == State.DOZE_AOD_DOCKED || state5 == State.DOZE_SUSPEND_TRIGGERS) && state == State.DOZE_PULSE_DONE) {
                    Log.i("DozeMachine", "Dropping pulse done because current state is already done: " + this.mState);
                    state2 = this.mState;
                } else if (state == State.DOZE_REQUEST_PULSE && (iOrdinal3 = state5.ordinal()) != 2 && iOrdinal3 != 4) {
                    switch (iOrdinal3) {
                        case 10:
                        case 11:
                        case 12:
                            break;
                        default:
                            Log.i("DozeMachine", "Dropping pulse request because current state can't pulse: " + this.mState);
                            state2 = this.mState;
                            break;
                    }
                } else {
                    State state7 = State.DOZE_MOD;
                    if (state == state7 && (iOrdinal2 = this.mState.ordinal()) != 2 && iOrdinal2 != 10) {
                        Log.i("DozeMachine", "Dropping MOD because current state is " + this.mState);
                        state2 = this.mState;
                    } else if ((state == State.DOZE || state == state6) && this.mState == state7 && this.mMODReason != 0) {
                        StringBuilder sb = new StringBuilder("Dropping ");
                        sb.append(state);
                        sb.append(" because current state is MOD : ");
                        TooltipPopup$$ExternalSyntheticOutline0.m(this.mMODReason, "DozeMachine", sb);
                        this.mStateBeforeMOD = state;
                        state2 = this.mState;
                    } else if (state != State.DOZE_TRANSITION_ENDED || (iOrdinal = this.mState.ordinal()) == 1 || iOrdinal == 14) {
                        state2 = state;
                    } else {
                        Log.i("DozeMachine", "Dropping clockTransition because current state is " + this.mState);
                        state2 = this.mState;
                    }
                }
            }
        }
        Log.i("DozeMachine", "transition: old=" + this.mState + " req=" + state + " new=" + state2);
        State state8 = this.mState;
        if (state2 == state8) {
            return;
        }
        try {
            int iOrdinal6 = state8.ordinal();
            if (iOrdinal6 == 0) {
                Preconditions.checkState(state2 == State.INITIALIZED);
            } else if (iOrdinal6 == 9) {
                Preconditions.checkState(state2 == state4);
            }
            int iOrdinal7 = state2.ordinal();
            if (iOrdinal7 == 0) {
                throw new IllegalArgumentException("can't transition to UNINITIALIZED");
            }
            if (iOrdinal7 == 1) {
                Preconditions.checkState(this.mState == State.UNINITIALIZED);
            } else if (iOrdinal7 == 6) {
                Preconditions.checkState(this.mState == State.DOZE_REQUEST_PULSE);
            } else if (iOrdinal7 == 8) {
                State state9 = this.mState;
                Preconditions.checkState(state9 == State.DOZE_REQUEST_PULSE || state9 == State.DOZE_PULSING || state9 == State.DOZE_PULSING_BRIGHT);
            } else if (iOrdinal7 == 13) {
                State state10 = this.mState;
                Preconditions.checkState(state10 == State.DOZE || state10 == State.DOZE_AOD_PAUSED);
            }
            State state11 = this.mState;
            this.mState = state2;
            if (state2 == State.DOZE_MOD) {
                this.mStateBeforeMOD = state11;
            } else {
                this.mStateBeforeMOD = State.UNINITIALIZED;
            }
            DozeLogger dozeLogger4 = dozeLog.mLogger;
            dozeLogger4.getClass();
            LogLevel logLevel4 = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda34 = new DozeLogger$$ExternalSyntheticLambda3(7);
            LogBuffer logBuffer4 = dozeLogger4.buffer;
            LogMessage logMessageObtain2 = logBuffer4.obtain("DozeLog", logLevel4, dozeLogger$$ExternalSyntheticLambda34, null);
            ((LogMessageImpl) logMessageObtain2).str1 = state2.name();
            logBuffer4.commit(logMessageObtain2);
            TrackTracer.instantForGroup(state2.ordinal(), "keyguard", "doze_machine_state");
            if (state2 == State.DOZE_REQUEST_PULSE) {
                this.mPulseReason = i;
            } else if (state11 == State.DOZE_PULSE_DONE) {
                this.mPulseReason = -1;
            }
            for (Part part : this.mParts) {
                part.transitionTo(state11, state2);
            }
            DozeLogger dozeLogger5 = dozeLog.mLogger;
            dozeLogger5.getClass();
            LogLevel logLevel5 = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer5 = dozeLogger5.buffer;
            LogMessage logMessageObtain3 = logBuffer5.obtain("DozeLog", logLevel5, dozeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain3).str1 = state2.name();
            logBuffer5.commit(logMessageObtain3);
            if (state2 == State.FINISH) {
                this.mDozeService.finish();
            }
            int iOrdinal8 = state2.ordinal();
            boolean z = iOrdinal8 == 5 || iOrdinal8 == 6 || iOrdinal8 == 7 || iOrdinal8 == 12;
            boolean z2 = this.mWakeLockHeldForCurrentState;
            WakeLock wakeLock = this.mWakeLock;
            if (z2 && !z) {
                wakeLock.release("DozeMachine#heldForState");
                this.mWakeLockHeldForCurrentState = false;
            } else if (!z2 && z) {
                wakeLock.acquire("DozeMachine#heldForState");
                this.mWakeLockHeldForCurrentState = true;
            }
            int iOrdinal9 = state2.ordinal();
            if ((iOrdinal9 == 1 || iOrdinal9 == 8) && !LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
                transitionTo(State.DOZE, -1);
            }
        } catch (RuntimeException e) {
            throw new IllegalStateException("Illegal Transition: " + this.mState + " -> " + state2, e);
        }
    }

    public final void requestState(State state, int i) {
        Assert.isMainThread();
        if (DEBUG) {
            Log.i("DozeMachine", "request: current=" + this.mState + " req=" + state, new Throwable("here"));
        }
        boolean zIsExecutingTransition = isExecutingTransition();
        this.mQueuedRequests.add(state);
        if (zIsExecutingTransition) {
            return;
        }
        WakeLock wakeLock = this.mWakeLock;
        wakeLock.acquire("DozeMachine#requestState");
        for (int i2 = 0; i2 < this.mQueuedRequests.size(); i2++) {
            transitionTo((State) this.mQueuedRequests.get(i2), i);
        }
        this.mQueuedRequests.clear();
        wakeLock.release("DozeMachine#requestState");
    }

    public interface Part {
        void transitionTo(State state, State state2);

        default void dump(PrintWriter printWriter) {
        }

        default void onScreenState(int i) {
        }

        default void setDozeMachine(DozeMachine dozeMachine) {
        }

        default void destroy() {
        }
    }
}
