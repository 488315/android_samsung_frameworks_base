package com.samsung.android.animation;

import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

/* loaded from: classes5.dex */
public class SemAnimatorUtils {
    public static final TypeEvaluator<Rect> BOUNDS_EVALUATOR =
            new TypeEvaluator<
                    Rect>() { // from class: com.samsung.android.animation.SemAnimatorUtils.1
                @Override // android.animation.TypeEvaluator
                public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
                    Rect out =
                            new Rect(
                                    interpolate(startValue.left, endValue.left, fraction),
                                    interpolate(startValue.top, endValue.top, fraction),
                                    interpolate(startValue.right, endValue.right, fraction),
                                    interpolate(startValue.bottom, endValue.bottom, fraction));
                    return out;
                }

                public int interpolate(int start, int end, float fraction) {
                    return (int) (start + ((end - start) * fraction));
                }
            };
    private static final boolean DEBUGGABLE_LOW = true;

    public static BitmapDrawable getBitmapDrawableFromView(View view) {
        Bitmap b =
                Bitmap.createBitmap(
                        view.getResources().getDisplayMetrics(),
                        view.getWidth(),
                        view.getHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        view.draw(canvas);
        return new BitmapDrawable(view.getResources(), b);
    }

    static int getViewCenterX(View view) {
        return (view.getLeft() + view.getRight()) / 2;
    }

    static int getViewCenterY(View view) {
        return (view.getTop() + view.getBottom()) / 2;
    }

    public static boolean isTalkBackEnabled(Context context) {
        boolean isTalkBackEnabled = false;
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(context);
        if (accessibilityManager != null
                && accessibilityManager.isEnabled()
                && accessibilityManager.isTouchExplorationEnabled()) {
            isTalkBackEnabled = true;
        }
        Log.d("SemAnimatorUtils", "isTalkBackEnabled = " + isTalkBackEnabled);
        return isTalkBackEnabled;
    }
}
