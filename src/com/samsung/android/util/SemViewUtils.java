package com.samsung.android.util;

import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.os.Debug;
import android.util.Log;
import android.util.TypedValue;
import com.android.internal.R;

/* loaded from: classes6.dex */
public class SemViewUtils {
    private static final String TAG = "SemViewUtils";

    @Deprecated
    public static Path getSmoothCornerRectPath(float f, float f2, float f3) {
        Log.w(TAG, "This method is deprecated. Use getSmoothCornerRectPath(float, float, float, float, float) instead.");
        return getSmoothCornerRectPath(f, 0.0f, 0.0f, f2, f3);
    }

    public static Path getSmoothCornerRectPath(float f, float f2, float f3, float f4, float f5) {
        return getSmoothCornerRectPath(null, f, f2, f3, f4, f5);
    }

    public static Path getSmoothCornerRectPath(Path path, float f, float f2, float f3, float f4, float f5) {
        Path path2 = path == null ? new Path() : path;
        path2.reset();
        if (f4 <= 0.0f || f5 <= 0.0f) {
            Path path3 = path2;
            if (!Build.IS_USER) {
                Log.w(TAG, "IllegalArguments : width=" + f4 + ", height=" + f5 + ", Callers=" + Debug.getCallers(10));
            }
            return path3;
        }
        float f6 = f4 / 2.0f;
        float f7 = f5 / 2.0f;
        float fMin = Math.min(f6, f7);
        float fMin2 = Math.min(Math.max(f, 0.0f), fMin);
        float f8 = fMin2 / fMin;
        float fMin3 = f8 > 0.5f ? 1.0f - (Math.min(1.0f, (f8 - 0.5f) / 0.4f) * 0.13877845f) : 1.0f;
        float fMin4 = ((double) f8) > 0.6d ? 1.0f + (Math.min(1.0f, (f8 - 0.6f) / 0.3f) * 0.042454004f) : 1.0f;
        path2.moveTo(f2 + f6, f3);
        float f9 = fMin2 / 100.0f;
        float f10 = 128.19f * f9 * fMin3;
        float f11 = f4 - f10;
        path2.lineTo(f2 + Math.max(f6, f11), f3);
        float f12 = f2 + f4;
        float f13 = 83.62f * f9 * fMin4;
        float f14 = f12 - f13;
        float f15 = f9 * 67.45f;
        float f16 = f12 - f15;
        float f17 = f9 * 4.64f;
        float f18 = f3 + f17;
        float f19 = f9 * 51.16f;
        float f20 = f12 - f19;
        float f21 = f9 * 13.36f;
        float f22 = f3 + f21;
        Path path4 = path2;
        path4.cubicTo(f14, f3, f16, f18, f20, f22);
        float f23 = f9 * 34.86f;
        float f24 = f12 - f23;
        float f25 = f9 * 22.07f;
        float f26 = f3 + f25;
        float f27 = f12 - f25;
        float f28 = f3 + f23;
        float f29 = f12 - f21;
        float f30 = f3 + f19;
        path4.cubicTo(f24, f26, f27, f28, f29, f30);
        float f31 = f12 - f17;
        float f32 = f3 + f15;
        float f33 = f3 + f13;
        path4.cubicTo(f31, f32, f12, f33, f12, f3 + Math.min(f7, f10));
        float f34 = f5 - f10;
        path4.lineTo(f12, f3 + Math.max(f7, f34));
        float f35 = f3 + f5;
        float f36 = f35 - f13;
        float f37 = f35 - f15;
        float f38 = f35 - f19;
        path4.cubicTo(f12, f36, f31, f37, f29, f38);
        float f39 = f35 - f23;
        float f40 = f35 - f25;
        float f41 = f35 - f21;
        path4.cubicTo(f27, f39, f24, f40, f20, f41);
        float f42 = f35 - f17;
        path4.cubicTo(f16, f42, f14, f35, f2 + Math.max(f6, f11), f35);
        path4.lineTo(f2 + Math.min(f6, f10), f35);
        float f43 = f2 + f13;
        float f44 = f2 + f15;
        float f45 = f2 + f19;
        path4.cubicTo(f43, f35, f44, f42, f45, f41);
        float f46 = f2 + f23;
        float f47 = f2 + f25;
        float f48 = f2 + f21;
        path4.cubicTo(f46, f40, f47, f39, f48, f38);
        float f49 = f2 + f17;
        path4.cubicTo(f49, f37, f2, f36, f2, f3 + Math.max(f7, f34));
        path4.lineTo(f2, f3 + Math.min(f7, f10));
        path4.cubicTo(f2, f33, f49, f32, f48, f30);
        path4.cubicTo(f47, f28, f46, f26, f45, f22);
        path4.cubicTo(f44, f18, f43, f3, f2 + Math.min(f6, f10), f3);
        path4.close();
        return path4;
    }

    public static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16844176, typedValue, true);
        return typedValue.data != 0;
    }

    public static boolean isDeviceDefaultFamily(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
        return typedValue.data != 0;
    }
}
