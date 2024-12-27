package com.android.systemui.statusbar.phone;

import android.os.Handler;
import com.android.systemui.R;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.ScrimController;

public final class DozeScrimController implements StatusBarStateController.StateListener {
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public boolean mDozing;
    public DozeHost.PulseCallback mPulseCallback;
    public int mPulseReason;
    public final Handler mHandler = new Handler();
    public final AnonymousClass1 mScrimCallback = new ScrimController.Callback() { // from class: com.android.systemui.statusbar.phone.DozeScrimController.1
        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public final void onCancelled() {
            DozeScrimController.this.pulseFinished();
        }

        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public final void onDisplayBlanked() {
            DozeScrimController dozeScrimController = DozeScrimController.this;
            if (!dozeScrimController.mDozing) {
                dozeScrimController.mDozeLog.tracePulseDropped("onDisplayBlanked - not dozing");
            } else if (dozeScrimController.mPulseCallback != null) {
                dozeScrimController.mDozeLog.tracePulseStart(dozeScrimController.mPulseReason);
                dozeScrimController.mPulseCallback.onPulseStarted();
            }
        }

        @Override // com.android.systemui.statusbar.phone.ScrimController.Callback
        public final void onFinished() {
            int i;
            DozeScrimController dozeScrimController = DozeScrimController.this;
            dozeScrimController.mDozeLog.tracePulseEvent(dozeScrimController.mPulseReason, "scrimCallback-onFinished", dozeScrimController.mDozing);
            if (!dozeScrimController.mDozing || (i = dozeScrimController.mPulseReason) == 1 || i == 6) {
                return;
            }
            Handler handler = dozeScrimController.mHandler;
            DozeParameters dozeParameters = dozeScrimController.mDozeParameters;
            handler.postDelayed(dozeScrimController.mPulseOut, dozeParameters.getInt$1(R.integer.doze_pulse_duration_visible, "doze.pulse.duration.visible"));
            handler.postDelayed(dozeScrimController.mPulseOutExtended, dozeParameters.getInt$1(R.integer.doze_pulse_duration_visible, "doze.pulse.duration.visible") * 2);
        }
    };
    public final AnonymousClass2 mPulseOutExtended = new Runnable() { // from class: com.android.systemui.statusbar.phone.DozeScrimController.2
        @Override // java.lang.Runnable
        public final void run() {
            DozeScrimController dozeScrimController = DozeScrimController.this;
            dozeScrimController.mHandler.removeCallbacks(dozeScrimController.mPulseOut);
            DozeScrimController.this.mPulseOut.run();
        }
    };
    public final AnonymousClass3 mPulseOut = new AnonymousClass3();

    /* renamed from: com.android.systemui.statusbar.phone.DozeScrimController$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            DozeScrimController dozeScrimController = DozeScrimController.this;
            dozeScrimController.mHandler.removeCallbacks(dozeScrimController.mPulseOut);
            DozeScrimController dozeScrimController2 = DozeScrimController.this;
            dozeScrimController2.mHandler.removeCallbacks(dozeScrimController2.mPulseOutExtended);
            DozeScrimController dozeScrimController3 = DozeScrimController.this;
            dozeScrimController3.mDozeLog.tracePulseEvent(dozeScrimController3.mPulseReason, "out", dozeScrimController3.mDozing);
            DozeScrimController dozeScrimController4 = DozeScrimController.this;
            if (dozeScrimController4.mDozing) {
                dozeScrimController4.pulseFinished();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.phone.DozeScrimController$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.phone.DozeScrimController$2] */
    public DozeScrimController(DozeParameters dozeParameters, DozeLog dozeLog, StatusBarStateController statusBarStateController) {
        this.mDozeParameters = dozeParameters;
        statusBarStateController.addCallback(this);
        this.mDozeLog = dozeLog;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozingChanged(boolean z) {
        if (this.mDozing != z) {
            this.mDozeLog.traceDozingChanged(z);
        }
        setDozing(z);
    }

    public final void pulseFinished() {
        if (this.mPulseCallback != null) {
            this.mDozeLog.tracePulseFinish();
            this.mPulseCallback.onPulseFinished();
            this.mPulseCallback = null;
        }
    }

    public void setDozing(boolean z) {
        if (this.mDozing == z) {
            return;
        }
        this.mDozing = z;
        if (z || this.mPulseCallback == null) {
            return;
        }
        this.mDozeLog.tracePulseEvent(this.mPulseReason, "cancel", z);
        Handler handler = this.mHandler;
        handler.removeCallbacks(this.mPulseOut);
        handler.removeCallbacks(this.mPulseOutExtended);
        pulseFinished();
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
    }
}
