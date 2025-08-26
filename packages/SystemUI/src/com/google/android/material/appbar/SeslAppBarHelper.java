package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslAppBarHelper {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static float getAppBarProPortion(Context context) {
            Configuration configuration = context.getResources().getConfiguration();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int iHeight = windowManager.getCurrentWindowMetrics().getBounds().height();
            float fDeriveDimension = TypedValue.deriveDimension(1, iHeight, displayMetrics);
            Log.d("SeslAppBarHelper", "fullWindowHeight(dp)=" + fDeriveDimension + ", fullWindowHeight(px)=" + iHeight + ", screenHeightDp=" + context.getResources().getConfiguration().screenHeightDp);
            StringBuilder sb = new StringBuilder("orientation=");
            sb.append(configuration.orientation);
            sb.append(", fullWindowHeightDp=");
            sb.append(fDeriveDimension);
            Log.d("SeslAppBarHelper", sb.toString());
            if (configuration.orientation != 2) {
                if (fDeriveDimension < 639.0f) {
                    return 0.0f;
                }
                if (fDeriveDimension < 696.0f) {
                    return 0.48f;
                }
                if (fDeriveDimension < 780.0f) {
                    return 0.43f;
                }
                return fDeriveDimension < 960.0f ? 0.38f : 0.305f;
            }
            if (fDeriveDimension < 580.0f) {
                return 0.0f;
            }
            if (fDeriveDimension < 640.0f) {
                return 0.51f;
            }
            if (fDeriveDimension < 670.0f) {
                return 0.475f;
            }
            if (fDeriveDimension < 710.0f) {
                return 0.45f;
            }
            if (fDeriveDimension < 750.0f) {
                return 0.425f;
            }
            if (fDeriveDimension < 800.0f) {
                return 0.4f;
            }
            return fDeriveDimension < 1080.0f ? 0.37f : 0.27f;
        }

        private Companion() {
        }
    }
}
