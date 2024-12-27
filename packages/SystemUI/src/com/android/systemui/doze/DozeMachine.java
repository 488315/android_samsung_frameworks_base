package com.android.systemui.doze;

import android.content.res.Configuration;
import android.hardware.display.AmbientDisplayConfiguration;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Service {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            part.onUiModeTypeChanged(this.mUiModeType);
        }
    }

    public final void requestState(State state) {
        Preconditions.checkArgument(state != State.DOZE_REQUEST_PULSE);
        requestState(state, -1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:117:0x0036, code lost:
    
        if (r2 != 12) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void transitionTo(com.android.systemui.doze.DozeMachine.State r17, int r18) {
        /*
            Method dump skipped, instructions count: 762
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeMachine.transitionTo(com.android.systemui.doze.DozeMachine$State, int):void");
    }

    public final void requestState(State state, int i) {
        Assert.isMainThread();
        if (DEBUG) {
            Log.i("DozeMachine", "request: current=" + this.mState + " req=" + state, new Throwable("here"));
        }
        boolean z = !isExecutingTransition();
        this.mQueuedRequests.add(state);
        if (z) {
            WakeLock wakeLock = this.mWakeLock;
            wakeLock.acquire("DozeMachine#requestState");
            for (int i2 = 0; i2 < this.mQueuedRequests.size(); i2++) {
                transitionTo((State) this.mQueuedRequests.get(i2), i);
            }
            this.mQueuedRequests.clear();
            wakeLock.release("DozeMachine#requestState");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Part {
        void transitionTo(State state, State state2);

        default void destroy() {
        }

        default void dump(PrintWriter printWriter) {
        }

        default void onScreenState(int i) {
        }

        default void onUiModeTypeChanged(int i) {
        }

        default void setDozeMachine(DozeMachine dozeMachine) {
        }
    }
}
