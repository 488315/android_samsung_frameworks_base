package com.android.systemui.shade;

import android.graphics.Point;
import android.hardware.display.AmbientDisplayConfiguration;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.DozeInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PulsingGestureListener extends GestureDetector.SimpleOnGestureListener implements Dumpable {
    public final AmbientDisplayConfiguration ambientDisplayConfiguration;
    public final DockManager dockManager;
    public boolean doubleTapEnabled;
    public final DozeInteractor dozeInteractor;
    public final FalsingManager falsingManager;
    public final PowerInteractor powerInteractor;
    public final ShadeLogger shadeLogger;
    public boolean singleTapEnabled;
    public final StatusBarStateController statusBarStateController;

    public PulsingGestureListener(FalsingManager falsingManager, DockManager dockManager, PowerInteractor powerInteractor, AmbientDisplayConfiguration ambientDisplayConfiguration, StatusBarStateController statusBarStateController, ShadeLogger shadeLogger, DozeInteractor dozeInteractor, final UserTracker userTracker, TunerService tunerService, DumpManager dumpManager) {
        this.falsingManager = falsingManager;
        this.dockManager = dockManager;
        this.powerInteractor = powerInteractor;
        this.ambientDisplayConfiguration = ambientDisplayConfiguration;
        this.statusBarStateController = statusBarStateController;
        this.shadeLogger = shadeLogger;
        this.dozeInteractor = dozeInteractor;
        tunerService.addTunable(new TunerService.Tunable() { // from class: com.android.systemui.shade.PulsingGestureListener$tunable$1
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                boolean areEqual = Intrinsics.areEqual(str, "doze_pulse_on_double_tap");
                UserTracker userTracker2 = userTracker;
                PulsingGestureListener pulsingGestureListener = PulsingGestureListener.this;
                if (areEqual) {
                    pulsingGestureListener.doubleTapEnabled = pulsingGestureListener.ambientDisplayConfiguration.doubleTapGestureEnabled(((UserTrackerImpl) userTracker2).getUserId());
                } else if (Intrinsics.areEqual(str, "doze_tap_gesture")) {
                    pulsingGestureListener.singleTapEnabled = pulsingGestureListener.ambientDisplayConfiguration.tapGestureEnabled(((UserTrackerImpl) userTracker2).getUserId());
                }
            }
        }, "doze_pulse_on_double_tap", "doze_tap_gesture");
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "singleTapEnabled=", this.singleTapEnabled);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "doubleTapEnabled=", this.doubleTapEnabled);
        this.dockManager.getClass();
        printWriter.println("isDocked=false");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isProxCovered=", this.falsingManager.isProximityNear());
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
    public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() != 1 || !this.statusBarStateController.isDozing() || ((!this.doubleTapEnabled && !this.singleTapEnabled) || this.falsingManager.isProximityNear() || this.falsingManager.isFalseDoubleTap())) {
            return false;
        }
        this.powerInteractor.wakeUpIfDozing(15, "PULSING_DOUBLE_TAP");
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        this.dockManager.getClass();
        ShadeLogger shadeLogger = this.shadeLogger;
        boolean isDozing = this.statusBarStateController.isDozing();
        boolean z = this.singleTapEnabled;
        shadeLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda0 = new ShadeLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer = shadeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = isDozing;
        logMessageImpl.bool2 = z;
        logMessageImpl.bool3 = true;
        logBuffer.commit(obtain);
        if (!this.statusBarStateController.isDozing() || !this.singleTapEnabled) {
            this.shadeLogger.d("onSingleTapUp event ignored");
            return false;
        }
        boolean isProximityNear = this.falsingManager.isProximityNear();
        boolean isFalseTap = this.falsingManager.isFalseTap(1);
        ShadeLogger shadeLogger2 = this.shadeLogger;
        shadeLogger2.getClass();
        ShadeLogger$$ExternalSyntheticLambda0 shadeLogger$$ExternalSyntheticLambda02 = new ShadeLogger$$ExternalSyntheticLambda0(11);
        LogBuffer logBuffer2 = shadeLogger2.buffer;
        LogMessage obtain2 = logBuffer2.obtain("systemui.shade", logLevel, shadeLogger$$ExternalSyntheticLambda02, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
        logMessageImpl2.bool1 = !isProximityNear;
        logMessageImpl2.bool2 = !isFalseTap;
        logBuffer2.commit(obtain2);
        if (isProximityNear || isFalseTap) {
            return true;
        }
        this.shadeLogger.d("Single tap handled, requesting centralSurfaces.wakeUpIfDozing");
        ((KeyguardRepositoryImpl) this.dozeInteractor.keyguardRepository)._lastDozeTapToWakePosition.updateState(null, new Point((int) x, (int) y));
        this.powerInteractor.wakeUpIfDozing(15, "PULSING_SINGLE_TAP");
        return true;
    }
}
