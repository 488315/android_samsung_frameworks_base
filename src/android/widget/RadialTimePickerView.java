package android.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntArray;
import android.util.Log;
import android.util.MathUtils;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.flags.Flags;
import com.android.internal.R;
import com.android.internal.widget.ExploreByTouchHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Locale;

/* loaded from: classes5.dex */
public class RadialTimePickerView extends View {
    private static final int AM = 0;
    private static final int ANIM_DURATION_NORMAL = 500;
    private static final int ANIM_DURATION_TOUCH = 60;
    private static final int DEGREES_FOR_ONE_HOUR = 30;
    private static final int DEGREES_FOR_ONE_MINUTE = 6;
    public static final int HOURS = 0;
    private static final int HOURS_INNER = 2;
    private static final int HOURS_IN_CIRCLE = 12;
    public static final int MINUTES = 1;
    private static final int MINUTES_IN_CIRCLE = 60;
    private static final int MISSING_COLOR = -65281;
    private static final int NUM_POSITIONS = 12;
    private static final int PM = 1;
    private static final int SELECTOR_CIRCLE = 0;
    private static final int SELECTOR_DOT = 1;
    private static final int SELECTOR_LINE = 2;
    private static final String TAG = "RadialTimePickerView";
    private final FloatProperty<RadialTimePickerView> HOURS_TO_MINUTES;
    private int mAmOrPm;
    private int mCenterDotRadius;
    boolean mChangedDuringTouch;
    private int mCircleRadius;
    private float mDisabledAlpha;
    private int mHalfwayDist;
    private final String[] mHours12Texts;
    private float mHoursToMinutes;
    private ObjectAnimator mHoursToMinutesAnimator;
    private final String[] mInnerHours24Texts;
    private String[] mInnerTextHours;
    private final float[] mInnerTextX;
    private final float[] mInnerTextY;
    private boolean mInputEnabled;
    private boolean mIs24HourMode;
    private boolean mIsOnInnerCircle;
    private OnValueSelectedListener mListener;
    private int mMaxDistForOuterNumber;
    private int mMinDistForInnerNumber;
    private String[] mMinutesText;
    private final String[] mMinutesTexts;
    private final String[] mOuterHours24Texts;
    private String[] mOuterTextHours;
    private final float[][] mOuterTextX;
    private final float[][] mOuterTextY;
    private final Paint[] mPaint;
    private final Paint mPaintBackground;
    private final Paint mPaintCenter;
    private final Paint[] mPaintSelector;
    private final int[] mSelectionDegrees;
    private int mSelectorColor;
    private int mSelectorDotColor;
    private int mSelectorDotRadius;
    private final Path mSelectorPath;
    private int mSelectorRadius;
    private int mSelectorStroke;
    private boolean mShowHours;
    private final ColorStateList[] mTextColor;
    private final int[] mTextInset;
    private final int[] mTextSize;
    private final RadialPickerTouchHelper mTouchHelper;
    private final Typeface mTypeface;
    private int mXCenter;
    private int mYCenter;
    private static final int[] HOURS_NUMBERS = {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static final int[] HOURS_NUMBERS_24 = {0, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private static final int[] MINUTES_NUMBERS = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
    private static final int[] SNAP_PREFER_30S_MAP = new int[361];
    private static final float[] COS_30 = new float[12];
    private static final float[] SIN_30 = new float[12];

    interface OnValueSelectedListener {
        void onValueSelected(int i, int i2, boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface PickerType {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDegreesForMinute(int i) {
        return i * 6;
    }

    static {
        preparePrefer30sMap();
        double d = 1.5707963267948966d;
        for (int i = 0; i < 12; i++) {
            COS_30[i] = (float) Math.cos(d);
            SIN_30[i] = (float) Math.sin(d);
            d += 0.5235987755982988d;
        }
    }

    private static void preparePrefer30sMap() {
        int i = 1;
        int i2 = 8;
        int i3 = 0;
        for (int i4 = 0; i4 < 361; i4++) {
            SNAP_PREFER_30S_MAP[i4] = i3;
            if (i == i2) {
                i3 += 6;
                if (i3 == 360) {
                    i2 = 7;
                } else {
                    i2 = i3 % 30 == 0 ? 14 : 4;
                }
                i = 1;
            } else {
                i++;
            }
        }
    }

    private static int snapPrefer30s(int i) {
        int[] iArr = SNAP_PREFER_30S_MAP;
        if (iArr == null) {
            return -1;
        }
        return iArr[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int snapOnly30s(int i, int i2) {
        int i3 = (i / 30) * 30;
        int i4 = i3 + 30;
        if (i2 != 1) {
            if (i2 == -1) {
                if (i == i3) {
                    return i3 - 30;
                }
            } else if (i - i3 < i4 - i) {
            }
            return i3;
        }
        return i4;
    }

    public RadialTimePickerView(Context context) {
        this(context, null);
    }

    public RadialTimePickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public RadialTimePickerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RadialTimePickerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet);
        this.HOURS_TO_MINUTES = new FloatProperty<RadialTimePickerView>(this, "hoursToMinutes") { // from class: android.widget.RadialTimePickerView.1
            @Override // android.util.Property
            public Float get(RadialTimePickerView radialTimePickerView) {
                return Float.valueOf(radialTimePickerView.mHoursToMinutes);
            }

            @Override // android.util.FloatProperty
            public void setValue(RadialTimePickerView radialTimePickerView, float f) {
                radialTimePickerView.mHoursToMinutes = f;
                radialTimePickerView.invalidate();
            }
        };
        this.mHours12Texts = new String[12];
        this.mOuterHours24Texts = new String[12];
        this.mInnerHours24Texts = new String[12];
        this.mMinutesTexts = new String[12];
        Paint[] paintArr = {r13, r13};
        this.mPaint = paintArr;
        Paint paint = new Paint();
        this.mPaintCenter = paint;
        Paint[] paintArr2 = {r13, r13, r13};
        this.mPaintSelector = paintArr2;
        Paint paint2 = new Paint();
        this.mPaintBackground = paint2;
        this.mTextColor = new ColorStateList[3];
        this.mTextSize = new int[]{r13.getDimensionPixelSize(R.dimen.timepicker_text_size_normal), r13.getDimensionPixelSize(R.dimen.timepicker_text_size_normal), r13.getDimensionPixelSize(R.dimen.timepicker_text_size_inner)};
        this.mTextInset = new int[]{r13.getDimensionPixelSize(R.dimen.timepicker_text_inset_normal), r13.getDimensionPixelSize(R.dimen.timepicker_text_inset_normal), r13.getDimensionPixelSize(R.dimen.timepicker_text_inset_inner)};
        this.mOuterTextX = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 12);
        this.mOuterTextY = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 12);
        this.mInnerTextX = new float[12];
        this.mInnerTextY = new float[12];
        this.mSelectionDegrees = new int[2];
        this.mSelectorPath = new Path();
        this.mInputEnabled = true;
        this.mChangedDuringTouch = false;
        applyAttributes(attributeSet, i, i2);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842803, typedValue, true);
        this.mDisabledAlpha = typedValue.getFloat();
        this.mTypeface = Typeface.create(Typeface.DEFAULT_FAMILY, 0);
        Paint paint3 = new Paint();
        paint3.setAntiAlias(true);
        paintArr[0].setTextAlign(Paint.Align.CENTER);
        Paint paint4 = new Paint();
        paint4.setAntiAlias(true);
        paintArr[1].setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        Paint paint5 = new Paint();
        paint5.setAntiAlias(true);
        Paint paint6 = new Paint();
        paint6.setAntiAlias(true);
        Paint paint7 = new Paint();
        paint7.setAntiAlias(true);
        paintArr2[2].setStrokeWidth(2.0f);
        paint2.setAntiAlias(true);
        Resources resources = getResources();
        this.mSelectorRadius = resources.getDimensionPixelSize(R.dimen.timepicker_selector_radius);
        this.mSelectorStroke = resources.getDimensionPixelSize(R.dimen.timepicker_selector_stroke);
        this.mSelectorDotRadius = resources.getDimensionPixelSize(R.dimen.timepicker_selector_dot_radius);
        this.mCenterDotRadius = resources.getDimensionPixelSize(R.dimen.timepicker_center_dot_radius);
        this.mShowHours = true;
        this.mHoursToMinutes = 0.0f;
        this.mIs24HourMode = false;
        this.mAmOrPm = 0;
        RadialPickerTouchHelper radialPickerTouchHelper = new RadialPickerTouchHelper();
        this.mTouchHelper = radialPickerTouchHelper;
        setAccessibilityDelegate(radialPickerTouchHelper);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        initHoursAndMinutesText();
        initData();
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int i3 = calendar.get(11);
        int i4 = calendar.get(12);
        setCurrentHourInternal(i3, false, false);
        setCurrentMinuteInternal(i4, false);
        setHapticFeedbackEnabled(true);
    }

    void applyAttributes(AttributeSet attributeSet, int i, int i2) {
        Context context = getContext();
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.TimePicker, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.TimePicker, attributeSet, obtainStyledAttributes, i, i2);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(3);
        ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(9);
        ColorStateList[] colorStateListArr = this.mTextColor;
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(-65281);
        }
        colorStateListArr[0] = colorStateList;
        ColorStateList[] colorStateListArr2 = this.mTextColor;
        if (colorStateList2 == null) {
            colorStateList2 = ColorStateList.valueOf(-65281);
        }
        colorStateListArr2[2] = colorStateList2;
        ColorStateList[] colorStateListArr3 = this.mTextColor;
        colorStateListArr3[1] = colorStateListArr3[0];
        ColorStateList colorStateList3 = obtainStyledAttributes.getColorStateList(5);
        int colorForState = colorStateList3 != null ? colorStateList3.getColorForState(StateSet.get(40), 0) : -65281;
        this.mPaintCenter.setColor(colorForState);
        int[] iArr = StateSet.get(40);
        this.mSelectorColor = colorForState;
        this.mSelectorDotColor = this.mTextColor[0].getColorForState(iArr, 0);
        this.mPaintBackground.setColor(obtainStyledAttributes.getColor(4, context.getColor(R.color.timepicker_default_numbers_background_color_material)));
        obtainStyledAttributes.recycle();
    }

    public void initialize(int i, int i2, boolean z) {
        if (this.mIs24HourMode != z) {
            this.mIs24HourMode = z;
            initData();
        }
        setCurrentHourInternal(i, false, false);
        setCurrentMinuteInternal(i2, false);
    }

    public void setCurrentItemShowing(int i, boolean z) {
        if (i == 0) {
            showHours(z);
        } else {
            if (i == 1) {
                showMinutes(z);
                return;
            }
            Log.e(TAG, "ClockView does not support showing item " + i);
        }
    }

    public int getCurrentItemShowing() {
        return !this.mShowHours ? 1 : 0;
    }

    public void setOnValueSelectedListener(OnValueSelectedListener onValueSelectedListener) {
        this.mListener = onValueSelectedListener;
    }

    public void setCurrentHour(int i) {
        setCurrentHourInternal(i, true, false);
    }

    private void setCurrentHourInternal(int i, boolean z, boolean z2) {
        OnValueSelectedListener onValueSelectedListener;
        this.mSelectionDegrees[0] = (i % 12) * 30;
        int i2 = (i == 0 || i % 24 < 12) ? 0 : 1;
        boolean innerCircleForHour = getInnerCircleForHour(i);
        if (this.mAmOrPm != i2 || this.mIsOnInnerCircle != innerCircleForHour) {
            this.mAmOrPm = i2;
            this.mIsOnInnerCircle = innerCircleForHour;
            initData();
            this.mTouchHelper.invalidateRoot();
        }
        invalidate();
        if (!z || (onValueSelectedListener = this.mListener) == null) {
            return;
        }
        onValueSelectedListener.onValueSelected(0, i, z2);
    }

    public int getCurrentHour() {
        return getHourForDegrees(this.mSelectionDegrees[0], this.mIsOnInnerCircle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getHourForDegrees(int i, boolean z) {
        int i2 = (i / 30) % 12;
        if (!this.mIs24HourMode) {
            return this.mAmOrPm == 1 ? i2 + 12 : i2;
        }
        if (z || i2 != 0) {
            return (!z || i2 == 0) ? i2 : i2 + 12;
        }
        return 12;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDegreesForHour(int i) {
        if (this.mIs24HourMode) {
            if (i >= 12) {
                i -= 12;
            }
        } else if (i == 12) {
            i = 0;
        }
        return i * 30;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getInnerCircleForHour(int i) {
        if (this.mIs24HourMode) {
            return i == 0 || i > 12;
        }
        return false;
    }

    public void setCurrentMinute(int i) {
        setCurrentMinuteInternal(i, true);
    }

    private void setCurrentMinuteInternal(int i, boolean z) {
        OnValueSelectedListener onValueSelectedListener;
        this.mSelectionDegrees[1] = (i % 60) * 6;
        invalidate();
        if (!z || (onValueSelectedListener = this.mListener) == null) {
            return;
        }
        onValueSelectedListener.onValueSelected(1, i, false);
    }

    public int getCurrentMinute() {
        return getMinuteForDegrees(this.mSelectionDegrees[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMinuteForDegrees(int i) {
        return i / 6;
    }

    public boolean setAmOrPm(int i) {
        if (this.mAmOrPm == i || this.mIs24HourMode) {
            return false;
        }
        this.mAmOrPm = i;
        invalidate();
        this.mTouchHelper.invalidateRoot();
        return true;
    }

    public int getAmOrPm() {
        return this.mAmOrPm;
    }

    public void showHours(boolean z) {
        showPicker(true, z);
    }

    public void showMinutes(boolean z) {
        showPicker(false, z);
    }

    private void initHoursAndMinutesText() {
        for (int i = 0; i < 12; i++) {
            String[] strArr = this.mHours12Texts;
            int[] iArr = HOURS_NUMBERS;
            strArr[i] = String.format("%d", Integer.valueOf(iArr[i]));
            this.mInnerHours24Texts[i] = String.format("%02d", Integer.valueOf(HOURS_NUMBERS_24[i]));
            this.mOuterHours24Texts[i] = String.format("%d", Integer.valueOf(iArr[i]));
            this.mMinutesTexts[i] = String.format("%02d", Integer.valueOf(MINUTES_NUMBERS[i]));
        }
    }

    private void initData() {
        if (this.mIs24HourMode) {
            this.mOuterTextHours = this.mOuterHours24Texts;
            this.mInnerTextHours = this.mInnerHours24Texts;
        } else {
            String[] strArr = this.mHours12Texts;
            this.mOuterTextHours = strArr;
            this.mInnerTextHours = strArr;
        }
        this.mMinutesText = this.mMinutesTexts;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.mXCenter = getWidth() / 2;
            int height = getHeight() / 2;
            this.mYCenter = height;
            int min = Math.min(this.mXCenter, height);
            this.mCircleRadius = min;
            int[] iArr = this.mTextInset;
            int i5 = iArr[2];
            int i6 = this.mSelectorRadius;
            this.mMinDistForInnerNumber = (min - i5) - i6;
            int i7 = iArr[0];
            this.mMaxDistForOuterNumber = (min - i7) + i6;
            this.mHalfwayDist = min - ((i7 + i5) / 2);
            calculatePositionsHours();
            calculatePositionsMinutes();
            this.mTouchHelper.invalidateRoot();
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f = this.mInputEnabled ? 1.0f : this.mDisabledAlpha;
        drawCircleBackground(canvas);
        Path path = this.mSelectorPath;
        drawSelector(canvas, path);
        drawHours(canvas, path, f);
        drawMinutes(canvas, path, f);
        drawCenter(canvas, f);
    }

    private void showPicker(boolean z, boolean z2) {
        if (this.mShowHours == z) {
            return;
        }
        this.mShowHours = z;
        if (z2) {
            animatePicker(z, 500L);
        } else {
            ObjectAnimator objectAnimator = this.mHoursToMinutesAnimator;
            if (objectAnimator != null && objectAnimator.isStarted()) {
                this.mHoursToMinutesAnimator.cancel();
                this.mHoursToMinutesAnimator = null;
            }
            this.mHoursToMinutes = z ? 0.0f : 1.0f;
        }
        initData();
        invalidate();
        this.mTouchHelper.invalidateRoot();
    }

    private void animatePicker(boolean z, long j) {
        float f = z ? 0.0f : 1.0f;
        if (this.mHoursToMinutes == f) {
            ObjectAnimator objectAnimator = this.mHoursToMinutesAnimator;
            if (objectAnimator == null || !objectAnimator.isStarted()) {
                return;
            }
            this.mHoursToMinutesAnimator.cancel();
            this.mHoursToMinutesAnimator = null;
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, this.HOURS_TO_MINUTES, f);
        this.mHoursToMinutesAnimator = ofFloat;
        ofFloat.setAutoCancel(true);
        this.mHoursToMinutesAnimator.setDuration(j);
        this.mHoursToMinutesAnimator.start();
    }

    private void drawCircleBackground(Canvas canvas) {
        canvas.drawCircle(this.mXCenter, this.mYCenter, this.mCircleRadius, this.mPaintBackground);
    }

    private void drawHours(Canvas canvas, Path path, float f) {
        int i = (int) (((1.0f - this.mHoursToMinutes) * 255.0f * f) + 0.5f);
        if (i > 0) {
            canvas.save(2);
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            drawHoursClipped(canvas, i, false);
            canvas.restore();
            canvas.save(2);
            canvas.clipPath(path, Region.Op.INTERSECT);
            drawHoursClipped(canvas, i, true);
            canvas.restore();
        }
    }

    private void drawHoursClipped(Canvas canvas, int i, boolean z) {
        String[] strArr;
        drawTextElements(canvas, this.mTextSize[0], this.mTypeface, this.mTextColor[0], this.mOuterTextHours, this.mOuterTextX[0], this.mOuterTextY[0], this.mPaint[0], i, z && !this.mIsOnInnerCircle, this.mSelectionDegrees[0], z);
        if (!this.mIs24HourMode || (strArr = this.mInnerTextHours) == null) {
            return;
        }
        drawTextElements(canvas, this.mTextSize[2], this.mTypeface, this.mTextColor[2], strArr, this.mInnerTextX, this.mInnerTextY, this.mPaint[0], i, z && this.mIsOnInnerCircle, this.mSelectionDegrees[0], z);
    }

    private void drawMinutes(Canvas canvas, Path path, float f) {
        int i = (int) ((this.mHoursToMinutes * 255.0f * f) + 0.5f);
        if (i > 0) {
            canvas.save(2);
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            drawMinutesClipped(canvas, i, false);
            canvas.restore();
            canvas.save(2);
            canvas.clipPath(path, Region.Op.INTERSECT);
            drawMinutesClipped(canvas, i, true);
            canvas.restore();
        }
    }

    private void drawMinutesClipped(Canvas canvas, int i, boolean z) {
        drawTextElements(canvas, this.mTextSize[1], this.mTypeface, this.mTextColor[1], this.mMinutesText, this.mOuterTextX[1], this.mOuterTextY[1], this.mPaint[1], i, z, this.mSelectionDegrees[1], z);
    }

    private void drawCenter(Canvas canvas, float f) {
        this.mPaintCenter.setAlpha((int) ((f * 255.0f) + 0.5f));
        canvas.drawCircle(this.mXCenter, this.mYCenter, this.mCenterDotRadius, this.mPaintCenter);
    }

    private int getMultipliedAlpha(int i, int i2) {
        return (int) ((Color.alpha(i) * (i2 / 255.0d)) + 0.5d);
    }

    private void drawSelector(Canvas canvas, Path path) {
        int i = this.mIsOnInnerCircle ? 2 : 0;
        int[] iArr = this.mTextInset;
        int i2 = iArr[i];
        int[] iArr2 = this.mSelectionDegrees;
        float f = iArr2[i % 2] % 30 != 0 ? 1.0f : 0.0f;
        int i3 = iArr[1];
        float f2 = iArr2[1] % 30 == 0 ? 0.0f : 1.0f;
        int i4 = this.mSelectorRadius;
        float lerp = this.mCircleRadius - MathUtils.lerp(i2, i3, this.mHoursToMinutes);
        double radians = Math.toRadians(MathUtils.lerpDeg(r3, r8, this.mHoursToMinutes));
        float sin = this.mXCenter + (((float) Math.sin(radians)) * lerp);
        float cos = this.mYCenter - (((float) Math.cos(radians)) * lerp);
        Paint paint = this.mPaintSelector[0];
        paint.setColor(this.mSelectorColor);
        float f3 = i4;
        canvas.drawCircle(sin, cos, f3, paint);
        if (path != null) {
            path.reset();
            path.addCircle(sin, cos, f3, Path.Direction.CCW);
        }
        float lerp2 = MathUtils.lerp(f, f2, this.mHoursToMinutes);
        if (lerp2 > 0.0f) {
            Paint paint2 = this.mPaintSelector[1];
            paint2.setColor(this.mSelectorDotColor);
            canvas.drawCircle(sin, cos, this.mSelectorDotRadius * lerp2, paint2);
        }
        double sin2 = Math.sin(radians);
        double cos2 = Math.cos(radians);
        int i5 = this.mXCenter;
        float f4 = (this.mYCenter - ((int) (this.mCenterDotRadius * cos2))) - ((int) ((lerp - f3) * cos2));
        Paint paint3 = this.mPaintSelector[2];
        paint3.setColor(this.mSelectorColor);
        paint3.setStrokeWidth(this.mSelectorStroke);
        canvas.drawLine(this.mXCenter, this.mYCenter, i5 + ((int) (r7 * sin2)) + ((int) (sin2 * r9)), f4, paint3);
    }

    private void calculatePositionsHours() {
        calculatePositions(this.mPaint[0], this.mCircleRadius - this.mTextInset[0], this.mXCenter, this.mYCenter, this.mTextSize[0], this.mOuterTextX[0], this.mOuterTextY[0]);
        if (this.mIs24HourMode) {
            calculatePositions(this.mPaint[0], this.mCircleRadius - this.mTextInset[2], this.mXCenter, this.mYCenter, this.mTextSize[2], this.mInnerTextX, this.mInnerTextY);
        }
    }

    private void calculatePositionsMinutes() {
        calculatePositions(this.mPaint[1], this.mCircleRadius - this.mTextInset[1], this.mXCenter, this.mYCenter, this.mTextSize[1], this.mOuterTextX[1], this.mOuterTextY[1]);
    }

    private static void calculatePositions(Paint paint, float f, float f2, float f3, float f4, float[] fArr, float[] fArr2) {
        paint.setTextSize(f4);
        float descent = f3 - ((paint.descent() + paint.ascent()) / 2.0f);
        for (int i = 0; i < 12; i++) {
            fArr[i] = f2 - (COS_30[i] * f);
            fArr2[i] = descent - (SIN_30[i] * f);
        }
    }

    private void drawTextElements(Canvas canvas, float f, Typeface typeface, ColorStateList colorStateList, String[] strArr, float[] fArr, float[] fArr2, Paint paint, int i, boolean z, int i2, boolean z2) {
        paint.setTextSize(f);
        paint.setTypeface(typeface);
        float f2 = i2 / 30.0f;
        int i3 = (int) f2;
        int ceil = ((int) Math.ceil(f2)) % 12;
        int i4 = 0;
        while (i4 < 12) {
            boolean z3 = i3 == i4 || ceil == i4;
            if (!z2 || z3) {
                int colorForState = colorStateList.getColorForState(StateSet.get(((z && z3) ? 32 : 0) | 8), 0);
                paint.setColor(colorForState);
                paint.setAlpha(getMultipliedAlpha(colorForState, i));
                canvas.drawText(strArr[i4], fArr[i4], fArr2[i4], paint);
            }
            i4++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDegreesFromXY(float f, float f2, boolean z) {
        int i;
        int i2;
        if (this.mIs24HourMode && this.mShowHours) {
            i2 = this.mMinDistForInnerNumber;
            i = this.mMaxDistForOuterNumber;
        } else {
            int i3 = this.mCircleRadius - this.mTextInset[!this.mShowHours ? 1 : 0];
            int i4 = this.mSelectorRadius;
            int i5 = i3 - i4;
            i = i3 + i4;
            i2 = i5;
        }
        double d = f - this.mXCenter;
        double d2 = f2 - this.mYCenter;
        double sqrt = Math.sqrt((d * d) + (d2 * d2));
        if (sqrt < i2) {
            return -1;
        }
        if (z && sqrt > i) {
            return -1;
        }
        int degrees = (int) (Math.toDegrees(Math.atan2(d2, d) + 1.5707963267948966d) + 0.5d);
        return degrees < 0 ? degrees + 360 : degrees;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getInnerCircleFromXY(float f, float f2) {
        if (this.mIs24HourMode && this.mShowHours) {
            double d = f - this.mXCenter;
            double d2 = f2 - this.mYCenter;
            if (Math.sqrt((d * d) + (d2 * d2)) <= this.mHalfwayDist) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (!this.mInputEnabled) {
            return true;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 2 || actionMasked == 1 || actionMasked == 0) {
            boolean z2 = false;
            if (actionMasked == 0) {
                this.mChangedDuringTouch = false;
            } else if (actionMasked == 1) {
                if (this.mChangedDuringTouch) {
                    z = true;
                } else {
                    z = true;
                    z2 = true;
                }
                this.mChangedDuringTouch = handleTouchInput(motionEvent.getX(), motionEvent.getY(), z2, z) | this.mChangedDuringTouch;
            }
            z = false;
            this.mChangedDuringTouch = handleTouchInput(motionEvent.getX(), motionEvent.getY(), z2, z) | this.mChangedDuringTouch;
        }
        return true;
    }

    private boolean handleTouchInput(float f, float f2, boolean z, boolean z2) {
        int currentMinute;
        boolean z3;
        int i;
        boolean innerCircleFromXY = getInnerCircleFromXY(f, f2);
        int degreesFromXY = getDegreesFromXY(f, f2, false);
        if (degreesFromXY == -1) {
            return false;
        }
        animatePicker(this.mShowHours, 60L);
        if (this.mShowHours) {
            int snapOnly30s = snapOnly30s(degreesFromXY, 0) % 360;
            z3 = (this.mIsOnInnerCircle == innerCircleFromXY && this.mSelectionDegrees[0] == snapOnly30s) ? false : true;
            this.mIsOnInnerCircle = innerCircleFromXY;
            this.mSelectionDegrees[0] = snapOnly30s;
            currentMinute = getCurrentHour();
            i = 0;
        } else {
            int snapPrefer30s = snapPrefer30s(degreesFromXY) % 360;
            int[] iArr = this.mSelectionDegrees;
            boolean z4 = iArr[1] != snapPrefer30s;
            iArr[1] = snapPrefer30s;
            currentMinute = getCurrentMinute();
            z3 = z4;
            i = 1;
        }
        if (!z3 && !z && !z2) {
            return false;
        }
        OnValueSelectedListener onValueSelectedListener = this.mListener;
        if (onValueSelectedListener != null) {
            onValueSelectedListener.onValueSelected(i, currentMinute, z2);
        }
        if (z3 || z) {
            performHapticFeedback(4);
            invalidate();
        }
        return true;
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mTouchHelper.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    public void setInputEnabled(boolean z) {
        this.mInputEnabled = z;
        invalidate();
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        if (!isEnabled()) {
            return null;
        }
        if (motionEvent.isFromSource(8194) && getDegreesFromXY(motionEvent.getX(), motionEvent.getY(), false) != -1) {
            return PointerIcon.getSystemIcon(getContext(), Flags.enableArrowIconOnHoverWhenClickable() ? 1000 : 1002);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    private class RadialPickerTouchHelper extends ExploreByTouchHelper {
        private final int MASK_TYPE;
        private final int MASK_VALUE;
        private final int MINUTE_INCREMENT;
        private final int SHIFT_TYPE;
        private final int SHIFT_VALUE;
        private final int TYPE_HOUR;
        private final int TYPE_MINUTE;
        private final Rect mTempRect;

        private int getTypeFromId(int i) {
            return i & 15;
        }

        private int getValueFromId(int i) {
            return (i >>> 8) & 255;
        }

        private int hour12To24(int i, int i2) {
            if (i != 12) {
                return i2 == 1 ? i + 12 : i;
            }
            if (i2 == 0) {
                return 0;
            }
            return i;
        }

        private int hour24To12(int i) {
            if (i == 0) {
                return 12;
            }
            return i > 12 ? i - 12 : i;
        }

        private int makeId(int i, int i2) {
            return (i2 << 8) | i;
        }

        public RadialPickerTouchHelper() {
            super(RadialTimePickerView.this);
            this.mTempRect = new Rect();
            this.TYPE_HOUR = 1;
            this.TYPE_MINUTE = 2;
            this.SHIFT_TYPE = 0;
            this.MASK_TYPE = 15;
            this.SHIFT_VALUE = 8;
            this.MASK_VALUE = 255;
            this.MINUTE_INCREMENT = 5;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (i == 4096) {
                adjustPicker(1);
                return true;
            }
            if (i != 8192) {
                return false;
            }
            adjustPicker(-1);
            return true;
        }

        private void adjustPicker(int i) {
            int i2;
            int currentMinute;
            int i3;
            int i4 = 0;
            if (RadialTimePickerView.this.mShowHours) {
                currentMinute = RadialTimePickerView.this.getCurrentHour();
                i2 = 1;
                if (RadialTimePickerView.this.mIs24HourMode) {
                    i3 = 23;
                } else {
                    currentMinute = hour24To12(currentMinute);
                    i3 = 12;
                    i4 = 1;
                }
            } else {
                i2 = 5;
                currentMinute = RadialTimePickerView.this.getCurrentMinute() / 5;
                i3 = 55;
            }
            int constrain = MathUtils.constrain((currentMinute + i) * i2, i4, i3);
            if (RadialTimePickerView.this.mShowHours) {
                RadialTimePickerView.this.setCurrentHour(constrain);
            } else {
                RadialTimePickerView.this.setCurrentMinute(constrain);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            int degreesFromXY = RadialTimePickerView.this.getDegreesFromXY(f, f2, true);
            if (degreesFromXY == -1) {
                return Integer.MIN_VALUE;
            }
            int snapOnly30s = RadialTimePickerView.snapOnly30s(degreesFromXY, 0) % 360;
            if (RadialTimePickerView.this.mShowHours) {
                int hourForDegrees = RadialTimePickerView.this.getHourForDegrees(snapOnly30s, RadialTimePickerView.this.getInnerCircleFromXY(f, f2));
                if (!RadialTimePickerView.this.mIs24HourMode) {
                    hourForDegrees = hour24To12(hourForDegrees);
                }
                return makeId(1, hourForDegrees);
            }
            int currentMinute = RadialTimePickerView.this.getCurrentMinute();
            int minuteForDegrees = RadialTimePickerView.this.getMinuteForDegrees(degreesFromXY);
            int minuteForDegrees2 = RadialTimePickerView.this.getMinuteForDegrees(snapOnly30s);
            if (getCircularDiff(currentMinute, minuteForDegrees, 60) >= getCircularDiff(minuteForDegrees2, minuteForDegrees, 60)) {
                currentMinute = minuteForDegrees2;
            }
            return makeId(2, currentMinute);
        }

        private int getCircularDiff(int i, int i2, int i3) {
            int abs = Math.abs(i - i2);
            return abs > i3 / 2 ? i3 - abs : abs;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(IntArray intArray) {
            if (RadialTimePickerView.this.mShowHours) {
                int i = RadialTimePickerView.this.mIs24HourMode ? 23 : 12;
                for (int i2 = !RadialTimePickerView.this.mIs24HourMode ? 1 : 0; i2 <= i; i2++) {
                    intArray.add(makeId(1, i2));
                }
                return;
            }
            int currentMinute = RadialTimePickerView.this.getCurrentMinute();
            for (int i3 = 0; i3 < 60; i3 += 5) {
                intArray.add(makeId(2, i3));
                if (currentMinute > i3 && currentMinute < i3 + 5) {
                    intArray.add(makeId(2, currentMinute));
                }
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setClassName(getClass().getName());
            accessibilityEvent.setContentDescription(getVirtualViewDescription(getTypeFromId(i), getValueFromId(i)));
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setClassName(getClass().getName());
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            int typeFromId = getTypeFromId(i);
            int valueFromId = getValueFromId(i);
            accessibilityNodeInfo.setContentDescription(getVirtualViewDescription(typeFromId, valueFromId));
            getBoundsForVirtualView(i, this.mTempRect);
            accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
            accessibilityNodeInfo.setSelected(isVirtualViewSelected(typeFromId, valueFromId));
            int virtualViewIdAfter = getVirtualViewIdAfter(typeFromId, valueFromId);
            if (virtualViewIdAfter != Integer.MIN_VALUE) {
                accessibilityNodeInfo.setTraversalBefore(RadialTimePickerView.this, virtualViewIdAfter);
            }
        }

        private int getVirtualViewIdAfter(int i, int i2) {
            if (i == 1) {
                int i3 = i2 + 1;
                if (i3 <= (RadialTimePickerView.this.mIs24HourMode ? 23 : 12)) {
                    return makeId(i, i3);
                }
                return Integer.MIN_VALUE;
            }
            if (i != 2) {
                return Integer.MIN_VALUE;
            }
            int currentMinute = RadialTimePickerView.this.getCurrentMinute();
            int i4 = (i2 - (i2 % 5)) + 5;
            if (i2 < currentMinute && i4 > currentMinute) {
                return makeId(i, currentMinute);
            }
            if (i4 < 60) {
                return makeId(i, i4);
            }
            return Integer.MIN_VALUE;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            int typeFromId = getTypeFromId(i);
            int valueFromId = getValueFromId(i);
            if (typeFromId == 1) {
                if (!RadialTimePickerView.this.mIs24HourMode) {
                    valueFromId = hour12To24(valueFromId, RadialTimePickerView.this.mAmOrPm);
                }
                RadialTimePickerView.this.setCurrentHour(valueFromId);
                return true;
            }
            if (typeFromId != 2) {
                return false;
            }
            RadialTimePickerView.this.setCurrentMinute(valueFromId);
            return true;
        }

        private void getBoundsForVirtualView(int i, Rect rect) {
            float f;
            float f2;
            float f3;
            int i2;
            int typeFromId = getTypeFromId(i);
            int valueFromId = getValueFromId(i);
            if (typeFromId == 1) {
                if (RadialTimePickerView.this.getInnerCircleForHour(valueFromId)) {
                    f = RadialTimePickerView.this.mCircleRadius - RadialTimePickerView.this.mTextInset[2];
                    i2 = RadialTimePickerView.this.mSelectorRadius;
                } else {
                    f = RadialTimePickerView.this.mCircleRadius - RadialTimePickerView.this.mTextInset[0];
                    i2 = RadialTimePickerView.this.mSelectorRadius;
                }
                f3 = i2;
                f2 = RadialTimePickerView.this.getDegreesForHour(valueFromId);
            } else if (typeFromId == 2) {
                f = RadialTimePickerView.this.mCircleRadius - RadialTimePickerView.this.mTextInset[1];
                f2 = RadialTimePickerView.this.getDegreesForMinute(valueFromId);
                f3 = RadialTimePickerView.this.mSelectorRadius;
            } else {
                f = 0.0f;
                f2 = 0.0f;
                f3 = 0.0f;
            }
            double radians = Math.toRadians(f2);
            float sin = RadialTimePickerView.this.mXCenter + (((float) Math.sin(radians)) * f);
            float cos = RadialTimePickerView.this.mYCenter - (f * ((float) Math.cos(radians)));
            rect.set((int) (sin - f3), (int) (cos - f3), (int) (sin + f3), (int) (cos + f3));
        }

        private CharSequence getVirtualViewDescription(int i, int i2) {
            if (i == 1 || i == 2) {
                return Integer.toString(i2);
            }
            return null;
        }

        private boolean isVirtualViewSelected(int i, int i2) {
            return i == 1 ? RadialTimePickerView.this.getCurrentHour() == i2 : i == 2 && RadialTimePickerView.this.getCurrentMinute() == i2;
        }
    }
}
