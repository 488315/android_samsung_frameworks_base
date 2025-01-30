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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class SeslColorSpectrumView extends View {
    public final int[] HUE_COLORS;
    public final int ROUNDED_CORNER_RADIUS_IN_Px;
    public Drawable cursorDrawable;
    public Paint mBackgroundPaint;
    public float mCurrentXPos;
    public Paint mCursorPaint;
    public final int mCursorPaintSize;
    public float mCursorPosX;
    public float mCursorPosY;
    public boolean mFromSwatchTouch;
    public Paint mHuePaint;
    public SeslColorPicker.C03926 mListener;
    public final Resources mResources;
    public Paint mSaturationPaint;
    public int mSaturationProgress;
    public int mSelectedVirtualViewId;
    public final Rect mSpectrumRect;
    public final Rect mSpectrumRectBackground;
    public final int mStartMargin;
    public Paint mStrokePaint;
    public final int mTopMargin;
    public SeslColorSpectrumViewTouchHelper mTouchHelper;
    public final int mVirtualItemHeight;
    public final int mVirtualItemWidth;

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

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
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
        LinearGradient linearGradient = new LinearGradient(f5, i2, rect2.left, i2, this.HUE_COLORS, (float[]) null, Shader.TileMode.CLAMP);
        Paint paint = new Paint(1);
        this.mHuePaint = paint;
        paint.setShader(linearGradient);
        this.mHuePaint.setStyle(Paint.Style.FILL);
        int i3 = this.mSpectrumRect.left;
        LinearGradient linearGradient2 = new LinearGradient(i3, r2.top, i3, r2.bottom, -1, 0, Shader.TileMode.CLAMP);
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
        SeslColorPicker.C03926 c03926 = this.mListener;
        if (c03926 != null) {
            c03926.onSpectrumColorChanged(f2, height);
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
            String substring = String.format("%08x", Integer.valueOf(i & (-1))).substring(2);
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
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m44m(sb, this.mCursorPosY, "SeslColorSpectrumView");
        }
        invalidate();
    }

    public final void updateCursorColor(int i) {
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("updateCursorColor color ", i, "SeslColorSpectrumView");
        if (!String.format("%08x", Integer.valueOf(i & (-1))).substring(2).equals(getResources().getString(R.string.sesl_color_black_000000))) {
            this.mCursorPaint.setColor(ColorUtils.setAlphaComponent(i, 255));
        } else {
            this.mCursorPaint.setColor(Color.parseColor("#" + getResources().getString(R.string.sesl_color_white_ffffff)));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SeslColorSpectrumViewTouchHelper extends ExploreByTouchHelper {
        public final Integer[] mBrightnessNumber;
        public final String[] mColorName;
        public final Integer[] mHueNumber;
        public final String[][] mSBTable;
        public final Integer[] mSaturationNumber;
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
            this.mColorName = new String[]{SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_red), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_red_orange), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_orange), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_orange_yellow), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_yellow), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_yellow_green), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_green), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_emerald_green), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_cyan), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_cerulean_blue), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_blue), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_purple), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_magenta), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_crimson)};
            this.mHueNumber = new Integer[]{15, 27, 45, 54, 66, 84, 138, 171, 189, Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberAnswerMode), 255, 270, 318, 342};
            this.mSaturationNumber = new Integer[]{20, 40, 60, 80, 100};
            this.mBrightnessNumber = new Integer[]{20, 40, 60, 80, 100};
            this.mSBTable = new String[][]{new String[]{SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish_light), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish_light)}, new String[]{SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish_light), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_light)}, new String[]{SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_grayish), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_light), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_light)}, new String[]{SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_hue_name), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_hue_name)}, new String[]{SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_dark), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_hue_name), SeslColorSpectrumView.this.mResources.getString(R.string.sesl_color_picker_hue_name)}};
            this.mVirtualViewRect = new Rect();
        }

        public static int getIndex(Integer[] numArr, int i) {
            int length = numArr.length - 1;
            int i2 = 0;
            int i3 = 0;
            while (i2 <= length) {
                int i4 = (i2 + length) / 2;
                if (numArr[i4].intValue() <= i) {
                    i2 = i4 + 1;
                } else {
                    length = i4 - 1;
                    i3 = i4;
                }
            }
            return i3;
        }

        public final StringBuilder getItemDescription(int i) {
            String str;
            setVirtualCursorIndexAt(i);
            StringBuilder sb = new StringBuilder();
            int i2 = (int) this.mVirtualHue;
            int i3 = (int) this.mVirtualValue;
            int i4 = (int) this.mVirtualSaturation;
            int i5 = (int) this.mVirtualBrightness;
            int index = getIndex(this.mSaturationNumber, i4);
            int index2 = getIndex(this.mBrightnessNumber, i5);
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            if (i2 >= 343) {
                str = seslColorSpectrumView.mResources.getString(R.string.sesl_color_picker_red);
            } else {
                str = this.mColorName[getIndex(this.mHueNumber, i2)];
            }
            String num = Integer.toString(i3);
            String str2 = this.mSBTable[index][index2];
            if (i3 == 0 || i3 == 1) {
                sb.append(seslColorSpectrumView.mResources.getString(R.string.sesl_color_picker_black) + " " + num);
            } else if (i3 >= 95 && i3 <= 100) {
                sb.append(seslColorSpectrumView.mResources.getString(R.string.sesl_color_picker_white) + " " + num);
            } else if (i4 <= 3) {
                if (i3 >= 2 && i3 <= 35) {
                    sb.append(seslColorSpectrumView.mResources.getString(R.string.sesl_color_picker_dark_gray) + " " + num);
                } else if (i3 >= 36 && i3 <= 80) {
                    sb.append(seslColorSpectrumView.mResources.getString(R.string.sesl_color_picker_gray) + " " + num);
                } else if (i3 >= 81 && i3 <= 98) {
                    sb.append(seslColorSpectrumView.mResources.getString(R.string.sesl_color_picker_light_gray) + " " + num);
                }
            } else if (i4 > 3) {
                if (str2.equals(seslColorSpectrumView.mResources.getString(R.string.sesl_color_picker_hue_name))) {
                    sb.append(str + " " + num);
                } else {
                    sb.append(String.format(str2, str) + " " + num);
                }
            }
            return sb;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            setVirtualCursorIndexAt(f - seslColorSpectrumView.mStartMargin, f2 - seslColorSpectrumView.mTopMargin);
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
            setVirtualCursorIndexAt(i);
            float f = this.mVirtualHue;
            float f2 = this.mVirtualSaturation;
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            SeslColorPicker.C03926 c03926 = seslColorSpectrumView.mListener;
            if (c03926 != null) {
                c03926.onSpectrumColorChanged(f, f2);
            }
            seslColorSpectrumView.mTouchHelper.sendEventForVirtualView(seslColorSpectrumView.mSelectedVirtualViewId, 1);
            return false;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(AccessibilityEvent accessibilityEvent, int i) {
            accessibilityEvent.setContentDescription(getItemDescription(i));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            setVirtualCursorIndexAt(i);
            int i2 = this.mVirtualCursorPosX;
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            int i3 = seslColorSpectrumView.mVirtualItemWidth;
            int i4 = seslColorSpectrumView.mStartMargin;
            int i5 = this.mVirtualCursorPosY;
            int i6 = seslColorSpectrumView.mVirtualItemHeight;
            float f = seslColorSpectrumView.mTopMargin;
            Rect rect = this.mVirtualViewRect;
            rect.set((i2 * i3) + i4, (int) (((i5 * i6) - 4.5f) + f), ((i2 + 1) * i3) + i4, (int) ((((i5 + 1) * i6) - 4.5f) + f));
            accessibilityNodeInfoCompat.setContentDescription(getItemDescription(i));
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat.addAction(16);
            int i7 = seslColorSpectrumView.mSelectedVirtualViewId;
            if (i7 == -1 || i != i7) {
                return;
            }
            accessibilityNodeInfoCompat.addAction(4);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setSelected(true);
        }

        public final void setVirtualCursorIndexAt(float f, float f2) {
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

        public final void setVirtualCursorIndexAt(int i) {
            this.mVirtualCursorPosX = i % 30;
            this.mVirtualCursorPosY = i / 30;
            SeslColorSpectrumView seslColorSpectrumView = SeslColorSpectrumView.this;
            setVirtualCursorIndexAt(r0 * seslColorSpectrumView.mVirtualItemWidth, r4 * seslColorSpectrumView.mVirtualItemHeight);
        }
    }
}
