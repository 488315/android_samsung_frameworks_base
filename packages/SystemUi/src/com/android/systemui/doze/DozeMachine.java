package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Trace;
import android.util.Log;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.systemui.LsRune;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.DozeMachine$1 */
    public abstract /* synthetic */ class AbstractC12401 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[State.DOZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD_PAUSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD_PAUSING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD_DOCKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_PULSING.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_PULSING_BRIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.UNINITIALIZED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.INITIALIZED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.SCRIM_AOD_ENDED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_TRANSITION_ENDED.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_DISPLAY_STATE_ON.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_MOD.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.FINISH.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_PULSE_DONE.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Service {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public class Delegate implements Service {
            public final Executor mBgExecutor;
            public final Service mDelegate;

            public Delegate(Service service, Executor executor) {
                this.mDelegate = service;
                this.mBgExecutor = executor;
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
            public void setDozeScreenBrightness(final int i) {
                this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.doze.DozeMachine$Service$Delegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DozeMachine.Service.Delegate delegate = DozeMachine.Service.Delegate.this;
                        delegate.mDelegate.setDozeScreenBrightness(i);
                    }
                });
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

        void setDozeScreenState(int i, boolean z);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        SCRIM_AOD_ENDED,
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

    public final void requestState(State state) {
        Preconditions.checkArgument(state != State.DOZE_REQUEST_PULSE);
        requestState(state, -1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:122:0x004b, code lost:
    
        if ((r2 == 5 || r2 == 6 || r2 == 7 || r2 == 8) != false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transitionTo(State state, int i) {
        State state2;
        boolean z;
        boolean z2;
        State state3 = this.mState;
        State state4 = State.FINISH;
        DozeLog dozeLog = this.mDozeLog;
        if (state3 == state4) {
            state2 = state4;
        } else {
            if (this.mUiModeType == 3) {
                state.getClass();
                int[] iArr = AbstractC12401.$SwitchMap$com$android$systemui$doze$DozeMachine$State;
                int i2 = iArr[state.ordinal()];
                if (!(i2 == 1 || i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5)) {
                    int i3 = iArr[state.ordinal()];
                }
                Log.i("DozeMachine", "Doze is suppressed with all triggers disabled as car mode is active");
                DozeLogger dozeLogger = dozeLog.mLogger;
                dozeLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                DozeLogger$logCarModeStarted$2 dozeLogger$logCarModeStarted$2 = DozeLogger$logCarModeStarted$2.INSTANCE;
                LogBuffer logBuffer = dozeLogger.buffer;
                logBuffer.commit(logBuffer.obtain("DozeLog", logLevel, dozeLogger$logCarModeStarted$2, null));
                state2 = State.DOZE_SUSPEND_TRIGGERS;
            }
            if (((DozeServiceHost) this.mDozeHost).mAlwaysOnSuppressed) {
                state.getClass();
                if (state == State.DOZE_AOD || state == State.DOZE_AOD_DOCKED) {
                    Log.i("DozeMachine", "Doze is suppressed by an app. Suppressing state: " + state);
                    DozeLogger dozeLogger2 = dozeLog.mLogger;
                    dozeLogger2.getClass();
                    LogLevel logLevel2 = LogLevel.INFO;
                    DozeLogger$logAlwaysOnSuppressed$2 dozeLogger$logAlwaysOnSuppressed$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logAlwaysOnSuppressed$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return FontProvider$$ExternalSyntheticOutline0.m32m("Always-on state suppressed, suppressed state=", logMessage.getStr1(), " reason=", logMessage.getStr2());
                        }
                    };
                    LogBuffer logBuffer2 = dozeLogger2.buffer;
                    LogMessage obtain = logBuffer2.obtain("DozeLog", logLevel2, dozeLogger$logAlwaysOnSuppressed$2, null);
                    obtain.setStr1(state.name());
                    obtain.setStr2("app");
                    logBuffer2.commit(obtain);
                    state2 = State.DOZE;
                }
            }
            State state5 = this.mState;
            State state6 = State.DOZE_AOD_PAUSED;
            if ((state5 == state6 || state5 == State.DOZE_AOD_PAUSING || state5 == State.DOZE_AOD || state5 == State.DOZE || state5 == State.DOZE_AOD_DOCKED || state5 == State.DOZE_SUSPEND_TRIGGERS) && state == State.DOZE_PULSE_DONE) {
                Log.i("DozeMachine", "Dropping pulse done because current state is already done: " + this.mState);
                state2 = this.mState;
            } else {
                if (state == State.DOZE_REQUEST_PULSE) {
                    state5.getClass();
                    int i4 = AbstractC12401.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state5.ordinal()];
                    if (!(i4 == 1 || i4 == 2 || i4 == 3 || i4 == 4 || i4 == 5)) {
                        Log.i("DozeMachine", "Dropping pulse request because current state can't pulse: " + this.mState);
                        state2 = this.mState;
                    }
                }
                State state7 = State.DOZE_MOD;
                if (state == state7) {
                    State state8 = this.mState;
                    state8.getClass();
                    int i5 = AbstractC12401.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state8.ordinal()];
                    if (!(i5 == 1 || i5 == 3)) {
                        Log.i("DozeMachine", "Dropping MOD because current state is " + this.mState);
                        state2 = this.mState;
                    }
                }
                if ((state == State.DOZE || state == state6) && this.mState == state7 && this.mMODReason != 0) {
                    StringBuilder sb = new StringBuilder("Dropping ");
                    sb.append(state);
                    sb.append(" because current state is MOD : ");
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mMODReason, "DozeMachine");
                    this.mStateBeforeMOD = state;
                    state2 = this.mState;
                } else {
                    if (state == State.SCRIM_AOD_ENDED || state == State.DOZE_TRANSITION_ENDED) {
                        State state9 = this.mState;
                        state9.getClass();
                        int i6 = AbstractC12401.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state9.ordinal()];
                        if (!(i6 == 10 || i6 == 12 || i6 == 13)) {
                            Log.i("DozeMachine", "Dropping clockTransition because current state is " + this.mState);
                            state2 = this.mState;
                        }
                    }
                    state2 = state;
                }
            }
        }
        Log.i("DozeMachine", "transition: old=" + this.mState + " req=" + state + " new=" + state2);
        State state10 = this.mState;
        if (state2 == state10) {
            return;
        }
        try {
            int[] iArr2 = AbstractC12401.$SwitchMap$com$android$systemui$doze$DozeMachine$State;
            int i7 = iArr2[state10.ordinal()];
            if (i7 == 9) {
                Preconditions.checkState(state2 == State.INITIALIZED);
            } else if (i7 == 16) {
                Preconditions.checkState(state2 == state4);
            }
            int i8 = iArr2[state2.ordinal()];
            if (i8 == 7) {
                Preconditions.checkState(this.mState == State.DOZE_REQUEST_PULSE);
            } else if (i8 == 15) {
                State state11 = this.mState;
                if (state11 != State.DOZE && state11 != State.DOZE_AOD_PAUSED) {
                    z = false;
                    Preconditions.checkState(z);
                }
                z = true;
                Preconditions.checkState(z);
            } else if (i8 == 17) {
                State state12 = this.mState;
                if (state12 != State.DOZE_REQUEST_PULSE && state12 != State.DOZE_PULSING && state12 != State.DOZE_PULSING_BRIGHT) {
                    z2 = false;
                    Preconditions.checkState(z2);
                }
                z2 = true;
                Preconditions.checkState(z2);
            } else {
                if (i8 == 9) {
                    throw new IllegalArgumentException("can't transition to UNINITIALIZED");
                }
                if (i8 == 10) {
                    Preconditions.checkState(this.mState == State.UNINITIALIZED);
                }
            }
            State state13 = this.mState;
            this.mState = state2;
            if (state2 == State.DOZE_MOD) {
                this.mStateBeforeMOD = state13;
            } else {
                this.mStateBeforeMOD = State.UNINITIALIZED;
            }
            DozeLogger dozeLogger3 = dozeLog.mLogger;
            dozeLogger3.getClass();
            LogLevel logLevel3 = LogLevel.INFO;
            DozeLogger$logDozeStateChanged$2 dozeLogger$logDozeStateChanged$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logDozeStateChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Doze state changed to ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer3 = dozeLogger3.buffer;
            LogMessage obtain2 = logBuffer3.obtain("DozeLog", logLevel3, dozeLogger$logDozeStateChanged$2, null);
            obtain2.setStr1(state2.name());
            logBuffer3.commit(obtain2);
            Trace.traceCounter(4096L, "doze_machine_state", state2.ordinal());
            if (state2 == State.DOZE_REQUEST_PULSE) {
                this.mPulseReason = i;
            } else if (state13 == State.DOZE_PULSE_DONE) {
                this.mPulseReason = -1;
            }
            for (Part part : this.mParts) {
                part.transitionTo(state13, state2);
            }
            DozeLogger dozeLogger4 = dozeLog.mLogger;
            dozeLogger4.getClass();
            LogLevel logLevel4 = LogLevel.INFO;
            DozeLogger$logStateChangedSent$2 dozeLogger$logStateChangedSent$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logStateChangedSent$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Doze state sent to all DozeMachineParts stateSent=", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer4 = dozeLogger4.buffer;
            LogMessage obtain3 = logBuffer4.obtain("DozeLog", logLevel4, dozeLogger$logStateChangedSent$2, null);
            obtain3.setStr1(state2.name());
            logBuffer4.commit(obtain3);
            if (state2 == State.FINISH) {
                this.mDozeService.finish();
            }
            int[] iArr3 = AbstractC12401.$SwitchMap$com$android$systemui$doze$DozeMachine$State;
            int i9 = iArr3[state2.ordinal()];
            boolean z3 = i9 == 5 || i9 == 6 || i9 == 7 || i9 == 8;
            boolean z4 = this.mWakeLockHeldForCurrentState;
            WakeLock wakeLock = this.mWakeLock;
            if (z4 && !z3) {
                wakeLock.release("DozeMachine#heldForState");
                this.mWakeLockHeldForCurrentState = false;
            } else if (!z4 && z3) {
                wakeLock.acquire("DozeMachine#heldForState");
                this.mWakeLockHeldForCurrentState = true;
            }
            int i10 = iArr3[state2.ordinal()];
            if ((i10 == 10 || i10 == 17) && !LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
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
        boolean z = !isExecutingTransition();
        ArrayList arrayList = this.mQueuedRequests;
        arrayList.add(state);
        if (z) {
            WakeLock wakeLock = this.mWakeLock;
            wakeLock.acquire("DozeMachine#requestState");
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                transitionTo((State) arrayList.get(i2), i);
            }
            arrayList.clear();
            wakeLock.release("DozeMachine#requestState");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Part {
        void transitionTo(State state, State state2);

        default void dump(PrintWriter printWriter) {
        }

        default void onScreenState(int i) {
        }

        default void onUiModeTypeChanged(int i) {
        }

        default void setDozeMachine(DozeMachine dozeMachine) {
        }

        default void destroy() {
        }
    }
}
