package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BatteryMeterDrawableBase extends Drawable {
    public final Paint mBatteryPaint;
    public final Paint mBoltPaint;
    public final RectF mButtonFrame;
    public float mButtonHeightFraction;
    public final int[] mColors;
    public final Context mContext;
    public final int mCriticalLevel;
    public final RectF mFrame;
    public final Paint mFramePaint;
    public int mHeight;
    public final int mIntrinsicHeight;
    public final int mIntrinsicWidth;
    public final Path mOutlinePath;
    public final Rect mPadding;
    public final Paint mPlusPaint;
    public final Path mShapePath;
    public final String mWarningString;
    public float mWarningTextHeight;
    public final Paint mWarningTextPaint;
    public int mWidth;
    public int mLevel = -1;
    public final int mIconTint = -1;

    public BatteryMeterDrawableBase(Context context, int i) {
        new Path();
        new Path();
        this.mPadding = new Rect();
        this.mFrame = new RectF();
        this.mButtonFrame = new RectF();
        new RectF();
        new RectF();
        this.mShapePath = new Path();
        this.mOutlinePath = new Path();
        new Path();
        this.mContext = context;
        Resources resources = context.getResources();
        TypedArray obtainTypedArray = resources.obtainTypedArray(R.array.batterymeter_color_levels);
        TypedArray obtainTypedArray2 = resources.obtainTypedArray(R.array.batterymeter_color_values);
        int length = obtainTypedArray.length();
        this.mColors = new int[length * 2];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            this.mColors[i3] = obtainTypedArray.getInt(i2, 0);
            if (obtainTypedArray2.getType(i2) == 2) {
                this.mColors[i3 + 1] = Utils.getColorAttrDefaultColor(obtainTypedArray2.getThemeAttributeId(i2, 0), context, 0);
            } else {
                this.mColors[i3 + 1] = obtainTypedArray2.getColor(i2, 0);
            }
        }
        obtainTypedArray.recycle();
        obtainTypedArray2.recycle();
        this.mWarningString = context.getString(R.string.battery_meter_very_low_overlay_symbol);
        this.mCriticalLevel = this.mContext.getResources().getInteger(android.R.integer.config_defaultNotificationLedOn);
        this.mButtonHeightFraction = context.getResources().getFraction(R.fraction.battery_button_height_fraction, 1, 1);
        context.getResources().getFraction(R.fraction.battery_subpixel_smoothing_left, 1, 1);
        context.getResources().getFraction(R.fraction.battery_subpixel_smoothing_right, 1, 1);
        Paint paint = new Paint(1);
        this.mFramePaint = paint;
        paint.setColor(i);
        paint.setDither(true);
        paint.setStrokeWidth(0.0f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint paint2 = new Paint(1);
        this.mBatteryPaint = paint2;
        paint2.setDither(true);
        paint2.setStrokeWidth(0.0f);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint paint3 = new Paint(1);
        paint3.setTypeface(Typeface.create("sans-serif-condensed", 1));
        paint3.setTextAlign(Paint.Align.CENTER);
        Paint paint4 = new Paint(1);
        this.mWarningTextPaint = paint4;
        paint4.setTypeface(Typeface.create("sans-serif", 1));
        paint4.setTextAlign(Paint.Align.CENTER);
        int[] iArr = this.mColors;
        if (iArr.length > 1) {
            paint4.setColor(iArr[1]);
        }
        Utils.getColorStateListDefaultColor(R.color.meter_consumed_color, this.mContext);
        Paint paint5 = new Paint(1);
        this.mBoltPaint = paint5;
        paint5.setColor(Utils.getColorStateListDefaultColor(R.color.batterymeter_bolt_color, this.mContext));
        loadPoints(R.array.batterymeter_bolt_points, resources);
        Paint paint6 = new Paint(1);
        this.mPlusPaint = paint6;
        paint6.setColor(Utils.getColorStateListDefaultColor(R.color.batterymeter_plus_color, this.mContext));
        loadPoints(R.array.batterymeter_plus_points, resources);
        Paint paint7 = new Paint(1);
        paint7.setColor(paint6.getColor());
        paint7.setStyle(Paint.Style.STROKE);
        paint7.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.battery_powersave_outline_thickness));
        this.mIntrinsicWidth = context.getResources().getDimensionPixelSize(R.dimen.battery_width);
        this.mIntrinsicHeight = context.getResources().getDimensionPixelSize(R.dimen.battery_height);
    }

    public static float[] loadPoints(int i, Resources resources) {
        int[] intArray = resources.getIntArray(i);
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < intArray.length; i4 += 2) {
            i2 = Math.max(i2, intArray[i4]);
            i3 = Math.max(i3, intArray[i4 + 1]);
        }
        float[] fArr = new float[intArray.length];
        for (int i5 = 0; i5 < intArray.length; i5 += 2) {
            fArr[i5] = intArray[i5] / i2;
            fArr[i5 + 1] = intArray[r3] / i3;
        }
        return fArr;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        float m20m;
        int i = this.mLevel;
        Rect bounds = getBounds();
        if (i == -1) {
            return;
        }
        float f = i / 100.0f;
        int i2 = this.mHeight;
        int aspectRatio = (int) (getAspectRatio() * this.mHeight);
        int i3 = (this.mWidth - aspectRatio) / 2;
        int round = Math.round(i2 * this.mButtonHeightFraction);
        Rect rect = this.mPadding;
        int i4 = rect.left + bounds.left;
        float f2 = i4;
        float f3 = (bounds.bottom - rect.bottom) - i2;
        this.mFrame.set(f2, f3, i4 + aspectRatio, i2 + r1);
        this.mFrame.offset(i3, 0.0f);
        RectF rectF = this.mButtonFrame;
        float f4 = aspectRatio * 0.28f;
        float round2 = this.mFrame.left + Math.round(f4);
        RectF rectF2 = this.mFrame;
        float f5 = round;
        rectF.set(round2, rectF2.top, rectF2.right - Math.round(f4), this.mFrame.top + f5);
        this.mFrame.top += f5;
        Paint paint = this.mBatteryPaint;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            int[] iArr = this.mColors;
            if (i5 >= iArr.length) {
                break;
            }
            int i7 = iArr[i5];
            int i8 = iArr[i5 + 1];
            if (i <= i7) {
                i6 = i5 == iArr.length + (-2) ? this.mIconTint : i8;
            } else {
                i5 += 2;
                i6 = i8;
            }
        }
        paint.setColor(i6);
        if (i >= 96) {
            f = 1.0f;
        } else if (i <= this.mCriticalLevel) {
            f = 0.0f;
        }
        if (f == 1.0f) {
            m20m = this.mButtonFrame.top;
        } else {
            RectF rectF3 = this.mFrame;
            m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, f, rectF3.height(), rectF3.top);
        }
        this.mShapePath.reset();
        this.mOutlinePath.reset();
        float height = (this.mFrame.height() + f5) * getRadiusRatio();
        this.mShapePath.setFillType(Path.FillType.WINDING);
        this.mShapePath.addRoundRect(this.mFrame, height, height, Path.Direction.CW);
        this.mShapePath.addRect(this.mButtonFrame, Path.Direction.CW);
        this.mOutlinePath.addRoundRect(this.mFrame, height, height, Path.Direction.CW);
        Path path = new Path();
        path.addRect(this.mButtonFrame, Path.Direction.CW);
        this.mOutlinePath.op(path, Path.Op.XOR);
        canvas.drawPath(this.mShapePath, this.mFramePaint);
        this.mFrame.top = m20m;
        canvas.save();
        canvas.clipRect(this.mFrame);
        canvas.drawPath(this.mShapePath, this.mBatteryPaint);
        canvas.restore();
        if (i <= this.mCriticalLevel) {
            canvas.drawText(this.mWarningString, (this.mWidth * 0.5f) + f2, ((this.mHeight + this.mWarningTextHeight) * 0.48f) + f3, this.mWarningTextPaint);
        }
    }

    public float getAspectRatio() {
        return 0.58f;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        Rect rect2 = this.mPadding;
        if (rect2.left == 0 && rect2.top == 0 && rect2.right == 0 && rect2.bottom == 0) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    public float getRadiusRatio() {
        return 0.05882353f;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        Rect bounds = getBounds();
        int i5 = bounds.bottom;
        Rect rect = this.mPadding;
        int i6 = (i5 - rect.bottom) - (bounds.top + rect.top);
        this.mHeight = i6;
        this.mWidth = (bounds.right - rect.right) - (bounds.left + rect.left);
        this.mWarningTextPaint.setTextSize(i6 * 0.75f);
        this.mWarningTextHeight = -this.mWarningTextPaint.getFontMetrics().ascent;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mFramePaint.setColorFilter(colorFilter);
        this.mBatteryPaint.setColorFilter(colorFilter);
        this.mWarningTextPaint.setColorFilter(colorFilter);
        this.mBoltPaint.setColorFilter(colorFilter);
        this.mPlusPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
    }
}
