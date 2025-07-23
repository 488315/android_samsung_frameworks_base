package com.android.systemui.blur;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    }

    public QSColorCurve(Context context) {
        this.context = context;
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
            f2 = 250.0f;
        }
        float f3 = this.fraction;
        this.radius = ((f2 - 0.0f) * f3) + 0.0f;
        this.saturation = 0.25f * f3;
        this.curve = 10.0f * f3;
        this.minX = 15.0f * f3;
        float f4 = ((this.context.getResources().getConfiguration().uiMode & 48) == 32 || (QpRune.QUICK_SUBSCREEN_PANEL_WINDOW && this.context.getDisplay().getDisplayId() == 1)) ? 34.4f : 40.2f;
        float f5 = this.fraction;
        this.minY = f4 * f5;
        this.maxX = 255.0f - (0.0f * f5);
        this.maxY = 255.0f - ((255.0f - (((this.context.getResources().getConfiguration().uiMode & 48) == 32 || (QpRune.QUICK_SUBSCREEN_PANEL_WINDOW && this.context.getDisplay().getDisplayId() == 1)) ? 174.7f : 205.9f)) * this.fraction);
    }
}
