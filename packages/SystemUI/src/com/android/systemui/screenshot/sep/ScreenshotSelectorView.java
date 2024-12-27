package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class ScreenshotSelectorView extends View {
    public final Paint mBlackDashedLine;
    public final Paint mPaintBackground;
    public final Paint mPaintSelection;
    public Rect mSelectionRect;
    public Point mStartPoint;
    public final Paint mWhiteDashedLine;

    public ScreenshotSelectorView(Context context) {
        this(context, null);
    }

    public static Paint createDashedLine(int i, int i2, int i3, int i4) {
        Paint paint = new Paint();
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{i3, i4}, 0.0f);
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(i2);
        paint.setPathEffect(dashPathEffect);
        return paint;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(((View) this).mLeft, ((View) this).mTop, ((View) this).mRight, ((View) this).mBottom, this.mPaintBackground);
        Rect rect = this.mSelectionRect;
        if (rect != null) {
            canvas.drawRect(rect, this.mPaintSelection);
            canvas.drawRect(this.mSelectionRect, this.mBlackDashedLine);
            canvas.drawRect(this.mSelectionRect, this.mWhiteDashedLine);
        }
    }

    public ScreenshotSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint(-16777216);
        this.mPaintBackground = paint;
        paint.setAlpha(160);
        Paint paint2 = new Paint(0);
        this.mPaintSelection = paint2;
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.dashed_line_thickness);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.dashed_line_opaque_length);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.dashed_line_transparent_length);
        this.mWhiteDashedLine = createDashedLine(-1, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
        this.mBlackDashedLine = createDashedLine(-16777216, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize3);
    }
}
