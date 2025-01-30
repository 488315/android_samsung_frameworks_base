package com.android.systemui.blur;

import android.content.Context;
import android.view.Display;
import com.android.systemui.QpRune;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public QSColorCurve(Context context) {
        this.context = context;
    }

    public final boolean isCoverDisplay() {
        if (!QpRune.QUICK_PANEL_SUBSCREEN_QUICK_PANEL_WINDOW) {
            return false;
        }
        Display display = this.context.getDisplay();
        return display != null && display.getDisplayId() == 1;
    }

    public final void setFraction(float f) {
        float f2;
        this.fraction = f;
        float f3 = 200.0f;
        this.radius = (((isCoverDisplay() ? 348.0f : QpRune.QUICK_PANEL_BLUR_MASSIVE ? 70.0f : QpRune.QUICK_TABLET ? 200.0f : 400.0f) - 0.0f) * this.fraction) + 0.0f;
        isCoverDisplay();
        float f4 = this.fraction;
        this.saturation = 0.2f * f4;
        this.curve = f4 * (-1.0f);
        boolean isCoverDisplay = isCoverDisplay();
        Context context = this.context;
        if (isCoverDisplay) {
            f2 = 25.0f;
        } else {
            f2 = (context.getResources().getConfiguration().uiMode & 48) == 32 ? 15.0f : 0.0f;
        }
        this.minX = f2 * this.fraction;
        float f5 = isCoverDisplay() ? 0.0f : QpRune.QUICK_TABLET ? 20.0f : 38.0f;
        float f6 = this.fraction;
        this.minY = f5 * f6;
        this.maxX = 255.0f - (f6 * 55.0f);
        if (isCoverDisplay()) {
            f3 = 165.0f;
        } else if (QpRune.QUICK_TABLET) {
            f3 = 180.0f;
        } else {
            if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
                f3 = 150.0f;
            }
        }
        this.maxY = 255.0f - ((255.0f - f3) * this.fraction);
    }
}
