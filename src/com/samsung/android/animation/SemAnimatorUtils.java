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

/* loaded from: classes6.dex */
public class SemAnimatorUtils {
    public static final TypeEvaluator<Rect> BOUNDS_EVALUATOR = new TypeEvaluator<Rect>() { // from class: com.samsung.android.animation.SemAnimatorUtils.1
        public int interpolate(int i, int i2, float f) {
            return (int) (i + (f * (i2 - i)));
        }

        @Override // android.animation.TypeEvaluator
        public Rect evaluate(float f, Rect rect, Rect rect2) {
            return new Rect(interpolate(rect.left, rect2.left, f), interpolate(rect.top, rect2.top, f), interpolate(rect.right, rect2.right, f), interpolate(rect.bottom, rect2.bottom, f));
        }
    };
    private static final boolean DEBUGGABLE_LOW = true;

    public static BitmapDrawable getBitmapDrawableFromView(View view) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmapCreateBitmap));
        return new BitmapDrawable(view.getResources(), bitmapCreateBitmap);
    }

    static int getViewCenterX(View view) {
        return (view.getLeft() + view.getRight()) / 2;
    }

    static int getViewCenterY(View view) {
        return (view.getTop() + view.getBottom()) / 2;
    }

    public static boolean isTalkBackEnabled(Context context) {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(context);
        boolean z = accessibilityManager != null && accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
        Log.d("SemAnimatorUtils", "isTalkBackEnabled = " + z);
        return z;
    }
}
