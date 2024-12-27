package com.android.systemui.statusbar;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import com.android.systemui.R;
import com.android.systemui.bixby2.actionresult.ActionResults;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ScreenRecordDrawable extends DrawableWrapper {
    public Drawable mFillDrawable;
    public int mHeightPx;
    public int mHorizontalPadding;
    public Drawable mIconDrawable;
    public int mIconRadius;
    public int mLevel;
    public Paint mPaint;
    public float mTextSize;
    public int mWidthPx;

    public ScreenRecordDrawable() {
        super(null);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        this.mFillDrawable.applyTheme(theme);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean canApplyTheme() {
        return this.mFillDrawable.canApplyTheme() || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        this.mFillDrawable.draw(canvas);
        Rect bounds = this.mFillDrawable.getBounds();
        int i = this.mLevel;
        if (i <= 0) {
            this.mIconDrawable.setBounds(new Rect(bounds.centerX() - this.mIconRadius, bounds.centerY() - this.mIconRadius, bounds.centerX() + this.mIconRadius, bounds.centerY() + this.mIconRadius));
            this.mIconDrawable.draw(canvas);
        } else {
            String valueOf = String.valueOf(i);
            this.mPaint.getTextBounds(valueOf, 0, valueOf.length(), new Rect());
            canvas.drawText(valueOf, bounds.centerX(), (r2.height() / 2) + bounds.centerY(), this.mPaint);
        }
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mHeightPx;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mWidthPx;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        int i = rect.left;
        int i2 = this.mHorizontalPadding;
        rect.left = i + i2;
        rect.right += i2;
        return true;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        setDrawable(resources.getDrawable(R.drawable.ic_screen_record_background, theme).mutate());
        this.mFillDrawable = resources.getDrawable(R.drawable.ic_screen_record_background, theme).mutate();
        this.mIconDrawable = resources.getDrawable(R.drawable.ic_screenrecord, theme).mutate();
        this.mHorizontalPadding = resources.getDimensionPixelSize(R.dimen.status_bar_horizontal_padding);
        this.mTextSize = resources.getDimensionPixelSize(R.dimen.screenrecord_status_text_size);
        this.mIconRadius = resources.getDimensionPixelSize(R.dimen.screenrecord_status_icon_radius);
        this.mLevel = attributeSet.getAttributeIntValue(null, ActionResults.RESULT_SET_VOLUME_SUCCESS, 0);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(-1);
        this.mPaint.setTextSize(this.mTextSize);
        this.mPaint.setFakeBoldText(true);
        this.mWidthPx = resources.getDimensionPixelSize(R.dimen.screenrecord_status_icon_width);
        this.mHeightPx = resources.getDimensionPixelSize(R.dimen.screenrecord_status_icon_height);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final Drawable mutate() {
        this.mFillDrawable.mutate();
        return super.mutate();
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mFillDrawable.setBounds(rect);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i) {
        this.mFillDrawable.setLayoutDirection(i);
        return super.onLayoutDirectionChanged(i);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        super.setAlpha(i);
        this.mFillDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        this.mFillDrawable.setVisible(z, z2);
        return super.setVisible(z, z2);
    }
}
