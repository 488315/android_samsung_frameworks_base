package com.android.systemui.controls.ui.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BlurFacadeImpl implements BlurFacade {
    public final SemMultiWindowManager multiWindowManager = new SemMultiWindowManager();
    private final SettingsHelper settingsHelper;

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

    public BlurFacadeImpl(SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }

    public static void addView(ViewGroup viewGroup, String str, int i, int i2) {
        View findViewWithTag = viewGroup.findViewWithTag(str);
        if (findViewWithTag != null) {
            Log.d("BlurFacadeImpl", str.concat(" is already done"));
        } else {
            findViewWithTag = new View(viewGroup.getContext());
            findViewWithTag.setTag(str);
            findViewWithTag.setBackgroundColor(Color.rgb((i >> 16) & 255, (i >> 8) & 255, i & 255));
            findViewWithTag.setAlpha(((i >> 24) & 255) / 255.0f);
        }
        viewGroup.addView(findViewWithTag, i2);
    }

    public static void removeCustomBackgroundView(ViewGroup viewGroup) {
        View findViewWithTag;
        View findViewWithTag2 = viewGroup.findViewWithTag("SolidColorViewTag");
        if (findViewWithTag2 != null) {
            viewGroup.removeView(findViewWithTag2);
        }
        View findViewWithTag3 = viewGroup.findViewWithTag("DimViewTag");
        if (findViewWithTag3 != null) {
            viewGroup.removeView(findViewWithTag3);
        }
        if (!BasicRune.CONTROLS_CAPTURED_BLUR || (findViewWithTag = viewGroup.findViewWithTag("BlurViewTag")) == null) {
            return;
        }
        viewGroup.removeView(findViewWithTag);
    }

    public final void setWindowBackground(Context context, Window window) {
        if (this.settingsHelper.isReduceTransparencyEnabled() || BasicRune.CONTROLS_CAPTURED_BLUR) {
            ViewGroup viewGroup = (ViewGroup) window.getDecorView();
            removeCustomBackgroundView(viewGroup);
            addView(viewGroup, "SolidColorViewTag", context.getColor(R.color.open_theme_qp_bg_color), 0);
        } else if (BasicRune.CONTROLS_WINDOW_BLUR) {
            SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
            QSColorCurve qSColorCurve = new QSColorCurve(context);
            qSColorCurve.setFraction(1.0f);
            builder.setRadius((int) qSColorCurve.radius).setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY);
            SemBlurInfo build = builder.build();
            if (!CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
                window.getAttributes().flags = this.multiWindowManager.getMode() == 2 ? window.getAttributes().flags | QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING : window.getAttributes().flags & (-1048577);
            }
            window.getDecorView().getRootView().semSetBlurInfo(build);
            ViewGroup viewGroup2 = (ViewGroup) window.getDecorView();
            removeCustomBackgroundView(viewGroup2);
            int color = context.getColor(R.color.open_theme_qp_bg_color);
            boolean z = (context.getResources().getConfiguration().uiMode & 32) != 0;
            if (!Intrinsics.areEqual(Integer.toHexString(color), "ff5d5d5d") && !z) {
                addView(viewGroup2, "SolidColorViewTag", context.getColor(R.color.open_theme_qp_bg_color), 0);
                addView(viewGroup2, "DimViewTag", Color.argb(13, 0, 0, 0), 1);
            }
        } else {
            window.setBackgroundDrawableResource(R.color.control_activity_background_blur_no_blur_model);
        }
        window.setNavigationBarColor(0);
        window.setStatusBarColor(0);
    }
}
