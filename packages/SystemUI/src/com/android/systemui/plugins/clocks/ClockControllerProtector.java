package com.android.systemui.plugins.clocks;

import android.util.Log;
import com.android.systemui.plugins.PluginWrapper;
import com.android.systemui.plugins.ProtectedPluginListener;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class ClockControllerProtector implements ClockController, PluginWrapper<ClockController> {
    private static final String CLASS = "ClockController";
    private boolean mHasError = false;
    private ClockController mInstance;
    private ProtectedPluginListener mListener;

    private ClockControllerProtector(ClockController clockController, ProtectedPluginListener protectedPluginListener) {
        this.mInstance = clockController;
        this.mListener = protectedPluginListener;
    }

    public static ClockControllerProtector protect(ClockController clockController, ProtectedPluginListener protectedPluginListener) {
        return clockController instanceof ClockControllerProtector ? (ClockControllerProtector) clockController : new ClockControllerProtector(clockController, protectedPluginListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public void dump(PrintWriter printWriter) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.dump(printWriter);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: dump", e);
            this.mHasError = this.mListener.onFail(CLASS, "dump", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: dump", e2);
            this.mHasError = this.mListener.onFail(CLASS, "dump", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public ClockConfig getConfig() {
        return this.mInstance.getConfig();
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public ClockEvents getEvents() {
        return ClockEventsProtector.protect(this.mInstance.getEvents(), this.mListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public ClockFaceController getLargeClock() {
        return ClockFaceControllerProtector.protect(this.mInstance.getLargeClock(), this.mListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public ClockFaceController getSmallClock() {
        return ClockFaceControllerProtector.protect(this.mInstance.getSmallClock(), this.mListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockController
    public void initialize(boolean z, float f, float f2, ClockEventListener clockEventListener) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.initialize(z, f, f2, clockEventListener);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: initialize", e);
            this.mHasError = this.mListener.onFail(CLASS, "initialize", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: initialize", e2);
            this.mHasError = this.mListener.onFail(CLASS, "initialize", e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.plugins.PluginWrapper
    public ClockController getPlugin() {
        return this.mInstance;
    }
}
