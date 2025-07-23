package androidx.picker3.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.picker3.widget.SeslColorPicker;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class SeslColorSpectrumView extends View {
    public final int[] HUE_COLORS;
    public final int ROUNDED_CORNER_RADIUS_IN_Px;
    public final Drawable cursorDrawable;
    public final Paint mBackgroundPaint;
    public float mCurrentXPos;
    public final Paint mCursorPaint;
    public final int mCursorPaintSize;
    public float mCursorPosX;
    public float mCursorPosY;
    public boolean mFromSwatchTouch;
    public final String[] mHueColorDescriptions;
    public Paint mHuePaint;
    public SeslColorPicker.AnonymousClass6 mListener;
    public final Resources mResources;
    public final String[][] mSaturationBrightnessDescriptions;
    public Paint mSaturationPaint;
    public int mSaturationProgress;
    public int mSelectedVirtualViewId;
    public final Rect mSpectrumRect;
    public final Rect mSpectrumRectBackground;
    public final int mStartMargin;
    public final Paint mStrokePaint;
    public final int mTopMargin;
    public final SeslColorSpectrumViewTouchHelper mTouchHelper;
    public final int mVirtualItemHeight;
    public final int mVirtualItemWidth;
    public static final Integer[] HUE_VALUES = {15, 27, 45, 54, 66, 84, 138, 171, 189, Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberAnswerMode), 255, 270, 318, 342};
    public static final Integer[] SATURATION_LEVELS = {20, 40, 60, 80, 100};
    public static final Integer[] BRIGHTNESS_LEVELS = {20, 40, 60, 80, 100};

    public SeslColorSpectrumView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.HUE_COLORS = new int[]{-65281, -16776961, -16711681, -16711936, -256, -65536};
        this.ROUNDED_CORNER_RADIUS_IN_Px = 0;
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sesl_spectrum_stroke_width);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.sesl_spectrum_rect_starting);
        this.mStartMargin = dimensionPixelSize2;
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.sesl_spectrum_rect_top);
        this.mTopMargin = dimensionPixelSize3;
        this.mFromSwatchTouch = false;
        this.mSelectedVirtualViewId = -1;
        Resources resources = context.getResources();
        this.mResources = resources;
        SeslColorSpectrumViewTouchHelper seslColorSpectrumViewTouchHelper = new SeslColorSpectrumViewTouchHelper(this);
        this.mTouchHelper = seslColorSpectrumViewTouchHelper;
        ViewCompat.setAccessibilityDelegate(this, seslColorSpectrumViewTouchHelper);
        setImportantForAccessibility(1);
        this.mHueColorDescriptions = new String[]{resources.getString(R.string.sesl_color_picker_red), resources.getString(R.string.sesl_color_picker_red_orange), resources.getString(R.string.sesl_color_picker_orange), resources.getString(R.string.sesl_color_picker_orange_yellow), resources.getString(R.string.sesl_color_picker_yellow), resources.getString(R.string.sesl_color_picker_yellow_green), resources.getString(R.string.sesl_color_picker_green), resources.getString(R.string.sesl_color_picker_emerald_green), resources.getString(R.string.sesl_color_picker_cyan), resources.getString(R.string.sesl_color_picker_cerulean_blue), resources.getString(R.string.sesl_color_picker_blue), resources.getString(R.string.sesl_color_picker_purple), resources.getString(R.string.sesl_color_picker_magenta), resources.getString(R.string.sesl_color_picker_crimson)};
        this.mSaturationBrightnessDescriptions = new String[][]{new String[]{resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_grayish_dark), resources.getString(R.string.sesl_color_picker_grayish), resources.getString(R.string.sesl_color_picker_grayish_light), resources.getString(R.string.sesl_color_picker_grayish_light)}, new String[]{resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_grayish_dark), resources.getString(R.string.sesl_color_picker_grayish), resources.getString(R.string.sesl_color_picker_grayish_light), resources.getString(R.string.sesl_color_picker_light)}, new String[]{resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_grayish), resources.getString(R.string.sesl_color_picker_light), resources.getString(R.string.sesl_color_picker_light)}, new String[]{resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_hue_name), resources.getString(R.string.sesl_color_picker_hue_name)}, new String[]{resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_dark), resources.getString(R.string.sesl_color_picker_hue_name), resources.getString(R.string.sesl_color_picker_hue_name)}};
        int dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_spectrum_view_width);
        int dimensionPixelSize5 = resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_spectrum_view_height);
        this.mVirtualItemHeight = (int) (resources.getDimension(R.dimen.sesl_color_picker_oneui_3_color_spectrum_view_height) / 25.0f);
        this.mVirtualItemWidth = (int) (resources.getDimension(R.dimen.sesl_color_picker_oneui_3_color_swatch_view_width) / 30.0f);
        this.mSpectrumRect = new Rect(dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize5);
        this.mSpectrumRectBackground = new Rect(0, 0, resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_spectrum_view_width_background), resources.getDimensionPixelSize(R.dimen.sesl_color_picker_oneui_3_color_spectrum_view_height_background));
        this.mCursorPaintSize = resources.getDimensionPixelSize(R.dimen.sesl_color_picker_spectrum_cursor_paint_size);
        resources.getDimensionPixelSize(R.dimen.sesl_color_picker_spectrum_cursor_paint_size);
        resources.getDimensionPixelSize(R.dimen.sesl_color_picker_spectrum_cursor_out_stroke_size);
        this.ROUNDED_CORNER_RADIUS_IN_Px = (int) (4 * Resources.getSystem().getDisplayMetrics().density);
        this.mCursorPaint = new Paint();
        this.mStrokePaint = new Paint();
        this.mBackgroundPaint = new Paint();
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setColor(resources.getColor(R.color.sesl_color_picker_stroke_color_spectrumview));
        this.mStrokePaint.setStrokeWidth(dimensionPixelSize);
        this.cursorDrawable = resources.getDrawable(R.drawable.sesl_color_picker_gradient_wheel_cursor);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mBackgroundPaint.setColor(resources.getColor(R.color.sesl_color_picker_transparent));
    }

    public static int getIndex(Integer[] numArr, int i) {
        int length = numArr.length - 1;
        int i2 = 0;
        int i3 = 0;
        while (i2 <= length) {
            int m = AbsActionBarView$$ExternalSyntheticOutline0.m(length, i2, 2, i2);
            if (numArr[m].intValue() >= i) {
                length = m - 1;
                i3 = m;
            } else {
                i2 = m + 1;
            }
        }
        return i3;
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    public final StringBuilder getTalkbackDescription(int i, int i2, int i3, int i4) {
        String format;
        StringBuilder sb = new StringBuilder();
        String valueOf = String.valueOf(i4);
        if (i4 <= 1) {
            format = this.mResources.getString(R.string.sesl_color_picker_black);
        } else if (i4 >= 99) {
            format = this.mResources.getString(R.string.sesl_color_picker_white);
        } else if (i2 <= 3) {
            format = i4 <= 35 ? this.mResources.getString(R.string.sesl_color_picker_dark_gray) : i4 <= 80 ? this.mResources.getString(R.string.sesl_color_picker_gray) : this.mResources.getString(R.string.sesl_color_picker_light_gray);
        } else {
            String string = i >= 343 ? this.mResources.getString(R.string.sesl_color_picker_red) : this.mHueColorDescriptions[getIndex(HUE_VALUES, i)];
            String str = this.mSaturationBrightnessDescriptions[getIndex(SATURATION_LEVELS, i2)][getIndex(BRIGHTNESS_LEVELS, i3)];
            format = str.equals(this.mResources.getString(R.string.sesl_color_picker_hue_name)) ? string : String.format(str, string);
        }
        sb.append(format);
        sb.append(" ");
        sb.append(valueOf);
        return sb;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = this.mSpectrumRectBackground;
        float f = rect.left;
        float f2 = rect.top;
        float f3 = rect.right;
        float f4 = rect.bottom;
        int i = this.ROUNDED_CORNER_RADIUS_IN_Px;
        canvas.drawRoundRect(f, f2, f3, f4, i, i, this.mBackgroundPaint);
        Rect rect2 = this.mSpectrumRect;
        float f5 = rect2.right;
        int i2 = rect2.top;
        int[] iArr = this.HUE_COLORS;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        LinearGradient linearGradient = new LinearGradient(f5, i2, rect2.left, i2, iArr, (float[]) null, tileMode);
        Paint paint = new Paint(1);
        this.mHuePaint = paint;
        paint.setShader(linearGradient);
        this.mHuePaint.setStyle(Paint.Style.FILL);
        int i3 = this.mSpectrumRect.left;
        LinearGradient linearGradient2 = new LinearGradient(i3, r1.top, i3, r1.bottom, -1, 0, tileMode);
        Paint paint2 = new Paint(1);
        this.mSaturationPaint = paint2;
        paint2.setShader(linearGradient2);
        Rect rect3 = this.mSpectrumRect;
        float f6 = rect3.left;
        float f7 = rect3.top;
        float f8 = rect3.right;
        float f9 = rect3.bottom;
        int i4 = this.ROUNDED_CORNER_RADIUS_IN_Px;
        canvas.drawRoundRect(f6, f7, f8, f9, i4, i4, this.mHuePaint);
        Rect rect4 = this.mSpectrumRect;
        float f10 = rect4.left;
        float f11 = rect4.top;
        float f12 = rect4.right;
        float f13 = rect4.bottom;
        int i5 = this.ROUNDED_CORNER_RADIUS_IN_Px;
        canvas.drawRoundRect(f10, f11, f12, f13, i5, i5, this.mSaturationPaint);
        Rect rect5 = this.mSpectrumRect;
        float f14 = rect5.left;
        float f15 = rect5.top;
        float f16 = rect5.right;
        float f17 = rect5.bottom;
        int i6 = this.ROUNDED_CORNER_RADIUS_IN_Px;
        canvas.drawRoundRect(f14, f15, f16, f17, i6, i6, this.mStrokePaint);
        float f18 = this.mCursorPosX;
        Rect rect6 = this.mSpectrumRect;
        int i7 = rect6.left;
        if (f18 < i7) {
            this.mCursorPosX = i7;
        }
        float f19 = this.mCursorPosY;
        int i8 = rect6.top;
        if (f19 < i8) {
            this.mCursorPosY = i8;
        }
        float f20 = this.mCursorPosX;
        int i9 = rect6.right;
        int i10 = this.mStartMargin;
        if (f20 > i9 + i10) {
            this.mCursorPosX = i9 + i10;
        }
        float f21 = this.mCursorPosY;
        int i11 = rect6.bottom;
        int i12 = this.mTopMargin;
        if (f21 > i11 + i12) {
            this.mCursorPosY = i11 + i12;
        }
        canvas.drawCircle(this.mCursorPosX, this.mCursorPosY, this.mCursorPaintSize / 2.0f, this.mCursorPaint);
        Drawable drawable = this.cursorDrawable;
        float f22 = this.mCursorPosX;
        int i13 = this.mCursorPaintSize;
        float f23 = this.mCursorPosY;
        drawable.setBounds(((int) f22) - (i13 / 2), ((int) f23) - (i13 / 2), (i13 / 2) + ((int) f22), (i13 / 2) + ((int) f23));
        this.cursorDrawable.draw(canvas);
        setDrawingCacheEnabled(true);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            playSoundEffect(0);
        } else if (action == 2 && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        this.mCurrentXPos = x;
        if (x > this.mSpectrumRect.width() + this.mStartMargin) {
            this.mCurrentXPos = this.mSpectrumRect.width() + this.mStartMargin;
        }
        if (y > this.mSpectrumRect.height() + this.mTopMargin) {
            this.mSpectrumRect.height();
        }
        if (x > this.mSpectrumRect.width() + this.mStartMargin) {
            x = this.mSpectrumRect.width() + this.mStartMargin;
        }
        if (y > this.mSpectrumRect.height() + this.mTopMargin) {
            y = this.mSpectrumRect.height() + this.mTopMargin;
        }
        if (x < 0.0f) {
            x = 0.0f;
        }
        if (y < 0.0f) {
            y = 0.0f;
        }
        this.mCursorPosX = x;
        this.mCursorPosY = y;
        Rect rect = this.mSpectrumRect;
        float width = ((x - rect.left) / rect.width()) * 300.0f;
        float f = this.mCursorPosY;
        Rect rect2 = this.mSpectrumRect;
        float height = (f - rect2.top) / rect2.height();
        float f2 = width >= 0.0f ? width : 0.0f;
        SeslColorPicker.AnonymousClass6 anonymousClass6 = this.mListener;
        if (anonymousClass6 != null) {
            anonymousClass6.onSpectrumColorChanged(f2, height);
        } else {
            Log.d("SeslColorSpectrumView", "Listener is not set.");
        }
        this.mSelectedVirtualViewId = (((int) (this.mCursorPosY / this.mVirtualItemHeight)) * 30) + ((int) (this.mCursorPosX / this.mVirtualItemWidth));
        invalidate();
        return true;
    }

    public final void setColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        if (this.mSpectrumRect != null) {
            String substring = String.format("%08x", Integer.valueOf(i)).substring(2);
            String string = getResources().getString(R.string.sesl_color_white_ffffff);
            if (this.mFromSwatchTouch && substring.equals(string)) {
                this.mCursorPosY = 0.0f;
                this.mCursorPosX = 0.0f;
            } else if (substring.equals(string)) {
                this.mCursorPosY = 0.0f;
                this.mCursorPosX = this.mCurrentXPos;
            } else {
                Rect rect = this.mSpectrumRect;
                this.mCursorPosX = ((rect.width() * fArr[0]) / 300.0f) + rect.left;
                Rect rect2 = this.mSpectrumRect;
                this.mCursorPosY = (rect2.height() * fArr[1]) + rect2.top;
                if (this.mCursorPosX > this.mSpectrumRect.width() + this.mStartMargin) {
                    this.mCursorPosX = this.mSpectrumRect.width() + this.mStartMargin;
                }
                if (this.mCursorPosY > this.mSpectrumRect.height() + this.mTopMargin) {
                    this.mCursorPosY = this.mSpectrumRect.height() + this.mTopMargin;
                }
            }
            StringBuilder sb = new StringBuilder("updateCursorPosition() HSV[");
            sb.append(fArr[0]);
            sb.append(", ");
            sb.append(fArr[1]);
            sb.append(", ");
            sb.append(fArr[1]);
            sb.append("] mCursorPosX=");
            sb.append(this.mCursorPosX);
            sb.append(" mCursorPosY=");
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m(this.mCursorPosY, "SeslColorSpectrumView", sb);
        }
        invalidate();
    }

    public final void updateCursorColor(int i) {
        Log.i("SeslColorSpectrumView", "updateCursorColor color " + i);
        if (!String.format("%08x", Integer.valueOf(i)).substring(2).equals(getResources().getString(R.string.sesl_color_black_000000))) {
            this.mCursorPaint.setColor(ColorUtils.setAlphaComponent(i, 255));
        } else {
            this.mCursorPaint.setColor(Color.parseColor("#" + getResources().getString(R.string.sesl_color_white_ffffff)));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SeslColorSpectrumViewTouchHelper extends ExploreByTouchHelper {
        public float mVirtualBrightness;
        public float mVirtualCurrentCursorX;
        public float mVirtualCurrentCursorY;
        public int mVirtualCursorPosX;
        public int mVirtualCursorPosY;
        public float mVirtualHue;
        public float mVirtualSaturation;
        public float mVirtualValue;
        public final Rect mVirtualViewRect;

        public SeslColorSpectrumViewTouchHelper(View view) {
            super(view);
            this.mVirtualViewRect = new Rect();
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            setVirtualCursorIndexAt$1(f - seslColorSpectrumView.mStartMargin, f2 - seslColorSpectrumView.mTopMargin);
            return (this.mVirtualCursorPosY * 30) + this.mVirtualCursorPosX;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            for (int i = 0; i < 750; i++) {
                ((ArrayList) list).add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            setVirtualCursorIndexAt$1(i);
            float f = this.mVirtualHue;
            float f2 = this.mVirtualSaturation;
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            SeslColorPicker.AnonymousClass6 anonymousClass6 = seslColorSpectrumView.mListener;
            if (anonymousClass6 != null) {
                anonymousClass6.onSpectrumColorChanged(f, f2);
            }
            seslColorSpectrumView.mTouchHelper.sendEventForVirtualView(seslColorSpectrumView.mSelectedVirtualViewId, 1);
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            setVirtualCursorIndexAt$1(i);
            int i2 = (int) this.mVirtualHue;
            int i3 = (int) this.mVirtualValue;
            accessibilityEvent.setContentDescription(SeslColorSpectrumView.this.getTalkbackDescription(i2, (int) this.mVirtualSaturation, (int) this.mVirtualBrightness, i3));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            setVirtualCursorIndexAt$1(i);
            Rect rect = this.mVirtualViewRect;
            int i2 = this.mVirtualCursorPosX;
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            int i3 = seslColorSpectrumView.mVirtualItemWidth;
            int i4 = seslColorSpectrumView.mStartMargin;
            int i5 = this.mVirtualCursorPosY;
            int i6 = seslColorSpectrumView.mVirtualItemHeight;
            float f = seslColorSpectrumView.mTopMargin;
            rect.set((i2 * i3) + i4, (int) (((i5 * i6) - 4.5f) + f), ((i2 + 1) * i3) + i4, (int) ((((i5 + 1) * i6) - 4.5f) + f));
            setVirtualCursorIndexAt$1(i);
            accessibilityNodeInfoCompat.setContentDescription(seslColorSpectrumView.getTalkbackDescription((int) this.mVirtualHue, (int) this.mVirtualSaturation, (int) this.mVirtualBrightness, (int) this.mVirtualValue));
            accessibilityNodeInfoCompat.setBoundsInParent(this.mVirtualViewRect);
            accessibilityNodeInfoCompat.addAction(16);
            int i7 = seslColorSpectrumView.mSelectedVirtualViewId;
            if (i7 == -1 || i != i7) {
                return;
            }
            accessibilityNodeInfoCompat.addAction(4);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.mInfo.setSelected(true);
        }

        public final void setVirtualCursorIndexAt$1(float f, float f2) {
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            this.mVirtualCurrentCursorX = MathUtils.clamp(f, 0.0f, seslColorSpectrumView.mSpectrumRect.width());
            float clamp = MathUtils.clamp(f2, 0.0f, seslColorSpectrumView.mSpectrumRect.height());
            this.mVirtualCurrentCursorY = clamp;
            float f3 = this.mVirtualCurrentCursorX;
            this.mVirtualCursorPosX = (int) (f3 / seslColorSpectrumView.mVirtualItemWidth);
            this.mVirtualCursorPosY = (int) (clamp / seslColorSpectrumView.mVirtualItemHeight);
            Rect rect = seslColorSpectrumView.mSpectrumRect;
            float width = (((f3 - rect.left) + seslColorSpectrumView.mStartMargin) / rect.width()) * 300.0f;
            float f4 = this.mVirtualCurrentCursorY;
            Rect rect2 = seslColorSpectrumView.mSpectrumRect;
            float height = ((f4 - rect2.top) + seslColorSpectrumView.mTopMargin) / rect2.height();
            this.mVirtualHue = width >= 0.0f ? width : 0.0f;
            float f5 = seslColorSpectrumView.mSaturationProgress;
            this.mVirtualBrightness = f5;
            this.mVirtualValue = f5 / (1.0f + height);
            this.mVirtualSaturation = height * 100.0f;
        }

        public final void setVirtualCursorIndexAt$1(int i) {
            this.mVirtualCursorPosX = i % 30;
            this.mVirtualCursorPosY = i / 30;
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            setVirtualCursorIndexAt$1(r0 * seslColorSpectrumView.mVirtualItemWidth, r4 * seslColorSpectrumView.mVirtualItemHeight);
        }
    }
}
