package com.android.systemui.blur;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class QSColorCurve {
    public final Context context;
    public float curve;
    public float fraction;
    public float minX;
    public float minY;
    public float saturation;
    public float radius = 0.0f;
    public float maxX = 255.0f;
    public float maxY = 255.0f;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public QSColorCurve(Context context) {
        this.context = context;
    }

    public static boolean isPopover() {
        return QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public final boolean isNightMode() {
        return (this.context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final void setFraction(float f) {
        float f2;
        this.fraction = f;
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            if (!QpRune.QUICK_TABLET) {
                ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
            }
            f2 = 70.0f;
        } else {
            f2 = isPopover() ? 300.0f : 250.0f;
        }
        this.radius = ((f2 - 0.0f) * this.fraction) + 0.0f;
        this.saturation = (isPopover() ? !isNightMode() ? 0.45f : 0.65f : 0.25f) * this.fraction;
        this.curve = (isPopover() ? !isNightMode() ? 20.0f : -15.0f : 10.0f) * this.fraction;
        this.minX = ((isPopover() && isNightMode()) ? 0.0f : 15.0f) * this.fraction;
        this.minY = (isPopover() ? !isNightMode() ? 68.8f : 13.5f : (isNightMode() || (QpRune.QUICK_SUBSCREEN_PANEL_WINDOW && this.context.getDisplay().getDisplayId() == 1)) ? 34.4f : 40.2f) * this.fraction;
        this.maxX = 255.0f - ((255.0f - ((!isPopover() || isNightMode()) ? 255.0f : 235.0f)) * this.fraction);
        this.maxY = 255.0f - ((255.0f - (isPopover() ? !isNightMode() ? 247.4f : 133.4f : (isNightMode() || (QpRune.QUICK_SUBSCREEN_PANEL_WINDOW && this.context.getDisplay().getDisplayId() == 1)) ? 174.7f : 205.9f)) * this.fraction);
    }
}
