package com.android.systemui.navigationbar.gestural;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import com.samsung.android.graphics.spr.SemPathRenderingDrawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GestureHintDrawable extends LayerDrawable {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static Drawable rotateDrawable(Resources resources, Drawable drawable, int i) {
            Bitmap bitmap;
            Bitmap bitmap2;
            Matrix matrix = new Matrix();
            if (i == 1) {
                matrix.postRotate(-90.0f);
            } else if (i == 3) {
                matrix.postRotate(90.0f);
            }
            if (drawable instanceof BitmapDrawable) {
                bitmap2 = ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof SemPathRenderingDrawable) {
                bitmap2 = ((SemPathRenderingDrawable) drawable).getBitmap();
            } else {
                if ((drawable instanceof VectorDrawable) || (drawable instanceof GradientDrawable)) {
                    Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(createBitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                    bitmap = createBitmap;
                    Intrinsics.checkNotNull(bitmap);
                    return new BitmapDrawable(resources, Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
                }
                bitmap2 = null;
            }
            bitmap = bitmap2;
            Intrinsics.checkNotNull(bitmap);
            return new BitmapDrawable(resources, Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureHintDrawable(Drawable[] drawableArr) {
        super(drawableArr);
        Intrinsics.checkNotNull(drawableArr);
    }

    public final void setDarkIntensity(float f) {
        getDrawable(0).mutate().setAlpha((int) ((1 - f) * 255.0f));
        getDrawable(1).mutate().setAlpha((int) (f * 255.0f));
        invalidateSelf();
    }
}
