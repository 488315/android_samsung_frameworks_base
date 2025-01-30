package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import androidx.appcompat.util.SeslMisc;
import androidx.core.content.res.ResourcesCompat;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslMenuDivider extends ImageView {
    public final int mDiameter;
    public final int mInterval;
    public final Paint mPaint;

    public SeslMenuDivider(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        super.onDraw(canvas);
        int width = (getWidth() - getPaddingStart()) - getPaddingEnd();
        int height = getHeight();
        int i3 = this.mDiameter;
        int i4 = ((width - i3) / (this.mInterval + i3)) + 1;
        int i5 = i4 - 1;
        int paddingStart = getPaddingStart() + ((int) ((i3 / 2.0f) + 0.5f));
        int i6 = this.mDiameter;
        int i7 = (width - i6) - ((this.mInterval + i6) * i5);
        if (i6 % 2 != 0) {
            i7--;
        }
        if (i5 > 0) {
            i2 = i7 / i5;
            i = i7 % i5;
        } else {
            i = 0;
            i2 = 0;
        }
        int i8 = 0;
        for (int i9 = 0; i9 < i4; i9++) {
            canvas.drawCircle(paddingStart + i8, height / 2.0f, this.mDiameter / 2.0f, this.mPaint);
            int i10 = this.mDiameter + this.mInterval + i2 + i8;
            if (i9 < i) {
                i10++;
            }
            i8 = i10;
        }
    }

    public SeslMenuDivider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslMenuDivider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDiameter = (int) TypedValue.applyDimension(1, 1.5f, displayMetrics);
        this.mInterval = (int) TypedValue.applyDimension(1, 3.0f, displayMetrics);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setFlags(1);
        Resources resources = context.getResources();
        int i2 = SeslMisc.isLightTheme(context) ? R.color.sesl_popup_menu_divider_color_light : R.color.sesl_popup_menu_divider_color_dark;
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        paint.setColor(resources.getColor(i2, null));
    }
}
