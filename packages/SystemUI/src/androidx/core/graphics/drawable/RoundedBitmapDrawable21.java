package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.Gravity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RoundedBitmapDrawable21 extends RoundedBitmapDrawable {
    public RoundedBitmapDrawable21(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
    }

    @Override // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        updateDstRect();
        outline.setRoundRect(this.mDstRect, this.mCornerRadius);
    }

    @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
    public final void gravityCompatApply(Rect rect, Rect rect2, int i, int i2, int i3) {
        Gravity.apply(i, i2, i3, rect, rect2, 0);
    }
}
