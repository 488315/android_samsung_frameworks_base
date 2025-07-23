package com.android.systemui.statusbar.events;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.text.NumberFormat;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.R;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BatteryStatusChipClearTextView extends View {
    public final Paint clearTextPaint;
    public boolean isClear;
    public int level;
    public final int shadowColor;
    public final float shadowDx;
    public final float shadowDy;
    public final float shadowRadius;
    public final Paint textPaint;

    public BatteryStatusChipClearTextView(Context context) {
        this(context, null, 0, 6, null);
    }

    public final String getText() {
        Locale locale = getContext().getResources().getConfiguration().getLocales().get(0);
        if (Intrinsics.areEqual(locale.toString(), "my_MM")) {
            return String.valueOf(this.level);
        }
        String format = NumberFormat.getInstance(locale).format(Integer.valueOf(this.level));
        format.getClass();
        return format;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.textPaint.getTextBounds(String.valueOf(this.level), 0, String.valueOf(this.level).length(), new Rect());
        float width = canvas.getWidth() / 2.0f;
        this.textPaint.getTextBounds(String.valueOf(this.level), 0, String.valueOf(this.level).length(), new Rect());
        float height = ((r2.height() / 2.0f) + (getMeasuredHeight() / 2.0f)) - r2.bottom;
        int i = this.shadowColor;
        this.textPaint.setShadowLayer(this.shadowRadius, this.shadowDx, this.shadowDy, Color.argb(MathKt__MathJVMKt.roundToInt(Color.alpha(i) * (this.textPaint.getAlpha() / 255.0f)), Color.red(i), Color.green(i), Color.blue(i)));
        canvas.drawText(getText(), width, height, this.textPaint);
        if (this.isClear) {
            canvas.drawText(getText(), width, height, this.clearTextPaint);
        }
        this.textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        canvas.drawText(getText(), width, height, this.textPaint);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        Rect rect = new Rect();
        this.textPaint.setTextLocale(getContext().getResources().getConfiguration().getLocales().get(0));
        this.textPaint.getTextBounds(String.valueOf(this.level), 0, String.valueOf(this.level).length(), rect);
        setMeasuredDimension(View.resolveSizeAndState(MathKt__MathJVMKt.roundToInt(this.textPaint.measureText(String.valueOf(this.level))), i, 0), View.resolveSizeAndState(View.MeasureSpec.getSize(i2), i2, 0));
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        this.textPaint.setAlpha((int) (255 * f));
        invalidate();
    }

    public BatteryStatusChipClearTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ BatteryStatusChipClearTextView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public BatteryStatusChipClearTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.shadowColor = -16777216;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Paint.Align align = Paint.Align.CENTER;
        paint.setTextAlign(align);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setColor(-16777216);
        this.clearTextPaint = paint;
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextAlign(align);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        paint2.setColor(-65536);
        this.textPaint = paint2;
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.TextViewAppearance, i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        if (resourceId != -1) {
            TypedArray obtainStyledAttributes2 = getContext().getTheme().obtainStyledAttributes(resourceId, R.styleable.TextAppearance);
            int indexCount = obtainStyledAttributes2.getIndexCount();
            if (indexCount >= 0) {
                int i2 = 0;
                while (true) {
                    int index = obtainStyledAttributes2.getIndex(i2);
                    if (index == 0) {
                        float dimension = obtainStyledAttributes2.getDimension(index, 0.0f);
                        this.clearTextPaint.setTextSize(dimension);
                        this.textPaint.setTextSize(dimension);
                    } else if (index == 12) {
                        String string = obtainStyledAttributes2.getString(index);
                        if (string != null) {
                            Typeface create = Typeface.create(string, 0);
                            create = this.textPaint.getTypeface() != null ? Typeface.create(create, this.textPaint.getTypeface().getWeight(), this.textPaint.getTypeface().isItalic()) : create;
                            this.clearTextPaint.setTypeface(create);
                            this.textPaint.setTypeface(create);
                        }
                    } else if (index != 18) {
                        switch (index) {
                            case 7:
                                this.shadowColor = obtainStyledAttributes2.getColor(index, this.shadowColor);
                                break;
                            case 8:
                                this.shadowDx = obtainStyledAttributes2.getFloat(index, this.shadowDx);
                                break;
                            case 9:
                                this.shadowDy = obtainStyledAttributes2.getFloat(index, this.shadowDy);
                                break;
                            case 10:
                                this.shadowRadius = obtainStyledAttributes2.getFloat(index, this.shadowRadius);
                                break;
                        }
                    } else {
                        int i3 = obtainStyledAttributes2.getInt(index, 400);
                        Typeface typeface = this.textPaint.getTypeface();
                        typeface = typeface == null ? Typeface.create("sec", 0) : typeface;
                        Typeface typeface2 = this.textPaint.getTypeface();
                        Typeface create2 = Typeface.create(typeface, i3, typeface2 != null ? typeface2.isItalic() : false);
                        this.clearTextPaint.setTypeface(create2);
                        this.textPaint.setTypeface(create2);
                    }
                    if (i2 != indexCount) {
                        i2++;
                    }
                }
            }
            obtainStyledAttributes2.recycle();
        }
    }
}
