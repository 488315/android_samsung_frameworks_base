package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslAppBarHelper {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static float getAppBarProPortion(Context context) {
            Configuration configuration = context.getResources().getConfiguration();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int height = windowManager.getCurrentWindowMetrics().getBounds().height();
            float deriveDimension = TypedValue.deriveDimension(1, height, displayMetrics);
            Log.d("SeslAppBarHelper", "fullWindowHeight(dp)=" + deriveDimension + ", fullWindowHeight(px)=" + height + ", screenHeightDp=" + context.getResources().getConfiguration().screenHeightDp);
            StringBuilder sb = new StringBuilder("orientation=");
            sb.append(configuration.orientation);
            sb.append(", fullWindowHeightDp=");
            sb.append(deriveDimension);
            Log.d("SeslAppBarHelper", sb.toString());
            if (configuration.orientation != 2) {
                if (deriveDimension < 639.0f) {
                    return 0.0f;
                }
                if (deriveDimension < 696.0f) {
                    return 0.48f;
                }
                if (deriveDimension < 780.0f) {
                    return 0.43f;
                }
                return deriveDimension < 960.0f ? 0.38f : 0.305f;
            }
            if (deriveDimension < 580.0f) {
                return 0.0f;
            }
            if (deriveDimension < 640.0f) {
                return 0.51f;
            }
            if (deriveDimension < 670.0f) {
                return 0.475f;
            }
            if (deriveDimension < 710.0f) {
                return 0.45f;
            }
            if (deriveDimension < 750.0f) {
                return 0.425f;
            }
            if (deriveDimension < 800.0f) {
                return 0.4f;
            }
            return deriveDimension < 1080.0f ? 0.37f : 0.27f;
        }

        private Companion() {
        }
    }
}
