package com.android.systemui.statusbar;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.util.IndentingPrintWriter;
import android.util.MathUtils;
import android.view.CrossWindowBlurListeners;
import android.view.SurfaceControl;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BlurUtils implements Dumpable {
    public final CrossWindowBlurListeners crossWindowBlurListeners;
    public final int maxBlurRadius;
    public final int minBlurRadius;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BlurUtils(Resources resources, CrossWindowBlurListeners crossWindowBlurListeners, DumpManager dumpManager) {
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

    public SurfaceControl.Transaction createTransaction() {
        return new SurfaceControl.Transaction();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("BlurUtils:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("minBlurRadius: " + this.minBlurRadius);
        indentingPrintWriter.println("maxBlurRadius: " + this.maxBlurRadius);
        indentingPrintWriter.println("supportsBlursOnWindows: " + supportsBlursOnWindows());
        indentingPrintWriter.println("CROSS_WINDOW_BLUR_SUPPORTED: " + CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED);
        indentingPrintWriter.println("isHighEndGfx: " + ActivityManager.isHighEndGfx());
    }

    public final boolean supportsBlursOnWindows() {
        return CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED && ActivityManager.isHighEndGfx() && this.crossWindowBlurListeners.isCrossWindowBlurEnabled() && !SystemProperties.getBoolean("persist.sysui.disableBlur", false);
    }
}
