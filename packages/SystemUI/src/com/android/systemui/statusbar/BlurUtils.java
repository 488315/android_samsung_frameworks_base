package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.SystemProperties;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.CrossWindowBlurListeners;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.systemui.CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BlurUtils implements Dumpable {
    public SyncRtSurfaceTransactionApplier _transactionApplier = new SyncRtSurfaceTransactionApplier((View) null);
    public final CrossWindowBlurListeners crossWindowBlurListeners;
    public boolean earlyWakeupEnabled;
    public ViewRootImpl lastTargetViewRootImpl;
    public final float maxBlurRadius;
    public final float minBlurRadius;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Build.isDebuggable();
    }

    public BlurUtils(Resources resources, BlurConfig blurConfig, CrossWindowBlurListeners crossWindowBlurListeners, DumpManager dumpManager) {
        this.crossWindowBlurListeners = crossWindowBlurListeners;
        this.minBlurRadius = resources.getDimensionPixelSize(R.dimen.min_window_blur_radius);
        this.maxBlurRadius = resources.getDimensionPixelSize(R.dimen.max_window_blur_radius);
        dumpManager.registerDumpable(this);
    }

    public final float blurRadiusOfRatio(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        return MathUtils.lerp(this.minBlurRadius, this.maxBlurRadius, f);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BlurUtils:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("minBlurRadius: " + this.minBlurRadius);
        indentingPrintWriter.println("maxBlurRadius: " + this.maxBlurRadius);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("supportsBlursOnWindows: ", supportsBlursOnWindows(), indentingPrintWriter);
        indentingPrintWriter.println("CROSS_WINDOW_BLUR_SUPPORTED: " + CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED);
        CoverScreenDecorHwcLayer$$ExternalSyntheticOutline0.m("isHighEndGfx: ", ActivityManager.isHighEndGfx(), indentingPrintWriter);
    }

    public final boolean supportsBlursOnWindows() {
        return CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED && ActivityManager.isHighEndGfx() && !SystemProperties.getBoolean("persist.sysui.disableBlur", false) && this.crossWindowBlurListeners.isCrossWindowBlurEnabled();
    }

    public static /* synthetic */ void getTransactionApplier$annotations() {
    }
}
