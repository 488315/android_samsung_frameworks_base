package com.android.systemui.navigationbar.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import com.android.systemui.R;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.samsung.android.graphics.spr.SemPathRenderingDrawable;

/* loaded from: classes2.dex */
public class IconDrawableUtil {
    public static Drawable flipIconDrawable(Resources resources, Drawable drawable) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        Bitmap bitmap = getBitmap(drawable);
        return new BitmapDrawable(resources, Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable instanceof SemPathRenderingDrawable) {
            return ((SemPathRenderingDrawable) drawable).getBitmap();
        }
        if (!(drawable instanceof VectorDrawable) && !(drawable instanceof GradientDrawable)) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static Bitmap[] getBitmapFromDrawable(Context context, GestureHintDrawable gestureHintDrawable) {
        Drawable drawableMutate = gestureHintDrawable.getDrawable(0).mutate();
        if (drawableMutate instanceof GradientDrawable) {
            ((GradientDrawable) drawableMutate).setCornerRadius(context.getResources().getDimension(R.dimen.samsung_taskbar_gesture_hint_corner_radius));
        }
        Drawable drawableMutate2 = gestureHintDrawable.getDrawable(1).mutate();
        if (drawableMutate2 instanceof GradientDrawable) {
            ((GradientDrawable) drawableMutate2).setCornerRadius(context.getResources().getDimension(R.dimen.samsung_taskbar_gesture_hint_corner_radius));
        }
        drawableMutate.setAlpha(255);
        drawableMutate2.setAlpha(255);
        return new Bitmap[]{getBitmap(drawableMutate), getBitmap(drawableMutate2)};
    }
}
