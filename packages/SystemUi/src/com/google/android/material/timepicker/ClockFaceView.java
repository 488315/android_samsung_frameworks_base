package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.timepicker.ClockHandView;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class ClockFaceView extends RadialViewGroup implements ClockHandView.OnRotateListener {
    public final int clockHandPadding;
    public final ClockHandView clockHandView;
    public final int clockSize;
    public float currentHandRotation;
    public final int[] gradientColors;
    public final float[] gradientPositions;
    public final int minimumHeight;
    public final int minimumWidth;
    public final RectF scratch;
    public final ColorStateList textColor;
    public final SparseArray textViewPool;
    public final Rect textViewRect;
    public final C43982 valueAccessibilityDelegate;
    public String[] values;

    public ClockFaceView(Context context) {
        this(context, null);
    }

    public final void findIntersectingTextView() {
        RectF rectF = this.clockHandView.selectorBox;
        for (int i = 0; i < this.textViewPool.size(); i++) {
            TextView textView = (TextView) this.textViewPool.get(i);
            if (textView != null) {
                textView.getDrawingRect(this.textViewRect);
                offsetDescendantRectToMyCoords(textView, this.textViewRect);
                textView.setSelected(rectF.contains(this.textViewRect.centerX(), this.textViewRect.centerY()));
                this.scratch.set(this.textViewRect);
                this.scratch.offset(textView.getPaddingLeft(), textView.getPaddingTop());
                textView.getPaint().setShader(!RectF.intersects(rectF, this.scratch) ? null : new RadialGradient(rectF.centerX() - this.scratch.left, rectF.centerY() - this.scratch.top, 0.5f * rectF.width(), this.gradientColors, this.gradientPositions, Shader.TileMode.CLAMP));
                textView.invalidate();
            }
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.values.length, 1));
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        findIntersectingTextView();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int max = (int) (this.clockSize / Math.max(Math.max(this.minimumHeight / displayMetrics.heightPixels, this.minimumWidth / displayMetrics.widthPixels), 1.0f));
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(max, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        setMeasuredDimension(max, max);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }

    public ClockFaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialClockStyle);
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [com.google.android.material.timepicker.ClockFaceView$2] */
    public ClockFaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textViewRect = new Rect();
        this.scratch = new RectF();
        SparseArray sparseArray = new SparseArray();
        this.textViewPool = sparseArray;
        this.gradientPositions = new float[]{0.0f, 0.9f, 1.0f};
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ClockFaceView, i, 2132019175);
        Resources resources = getResources();
        ColorStateList colorStateList = MaterialResources.getColorStateList(context, obtainStyledAttributes, 1);
        this.textColor = colorStateList;
        LayoutInflater.from(context).inflate(R.layout.material_clockface_view, (ViewGroup) this, true);
        ClockHandView clockHandView = (ClockHandView) findViewById(R.id.material_clock_hand);
        this.clockHandView = clockHandView;
        this.clockHandPadding = resources.getDimensionPixelSize(R.dimen.material_clock_hand_padding);
        int colorForState = colorStateList.getColorForState(new int[]{android.R.attr.state_selected}, colorStateList.getDefaultColor());
        this.gradientColors = new int[]{colorForState, colorForState, colorStateList.getDefaultColor()};
        ((ArrayList) clockHandView.listeners).add(this);
        int defaultColor = ContextCompat.getColorStateList(R.color.material_timepicker_clockface, context).getDefaultColor();
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(context, obtainStyledAttributes, 0);
        setBackgroundColor(colorStateList2 != null ? colorStateList2.getDefaultColor() : defaultColor);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.timepicker.ClockFaceView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                if (!ClockFaceView.this.isShown()) {
                    return true;
                }
                ClockFaceView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                int height = ClockFaceView.this.getHeight() / 2;
                ClockFaceView clockFaceView = ClockFaceView.this;
                int i2 = (height - clockFaceView.clockHandView.selectorRadius) - clockFaceView.clockHandPadding;
                if (i2 != clockFaceView.radius) {
                    clockFaceView.radius = i2;
                    clockFaceView.updateLayoutParams();
                    ClockHandView clockHandView2 = clockFaceView.clockHandView;
                    clockHandView2.circleRadius = clockFaceView.radius;
                    clockHandView2.invalidate();
                }
                return true;
            }
        });
        setFocusable(true);
        obtainStyledAttributes.recycle();
        this.valueAccessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.google.android.material.timepicker.ClockFaceView.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
                AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
                accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                int intValue = ((Integer) view.getTag(R.id.material_value_index)).intValue();
                if (intValue > 0) {
                    accessibilityNodeInfo.setTraversalAfter((View) ClockFaceView.this.textViewPool.get(intValue - 1));
                }
                accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, 0, 1, intValue, 1, view.isSelected()));
                accessibilityNodeInfoCompat.setClickable(true);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                if (i2 != 16) {
                    return super.performAccessibilityAction(view, i2, bundle);
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                float x = view.getX() + (view.getWidth() / 2.0f);
                float height = (view.getHeight() / 2.0f) + view.getY();
                ClockFaceView clockFaceView = ClockFaceView.this;
                clockFaceView.clockHandView.onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, x, height, 0));
                clockFaceView.clockHandView.onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 1, x, height, 0));
                return true;
            }
        };
        String[] strArr = new String[12];
        Arrays.fill(strArr, "");
        this.values = strArr;
        LayoutInflater from = LayoutInflater.from(getContext());
        int size = sparseArray.size();
        for (int i2 = 0; i2 < Math.max(this.values.length, size); i2++) {
            TextView textView = (TextView) this.textViewPool.get(i2);
            if (i2 >= this.values.length) {
                removeView(textView);
                this.textViewPool.remove(i2);
            } else {
                if (textView == null) {
                    textView = (TextView) from.inflate(R.layout.material_clockface_textview, (ViewGroup) this, false);
                    this.textViewPool.put(i2, textView);
                    addView(textView);
                }
                textView.setVisibility(0);
                textView.setText(this.values[i2]);
                textView.setTag(R.id.material_value_index, Integer.valueOf(i2));
                ViewCompat.setAccessibilityDelegate(textView, this.valueAccessibilityDelegate);
                textView.setTextColor(this.textColor);
            }
        }
        this.minimumHeight = resources.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_height);
        this.minimumWidth = resources.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_width);
        this.clockSize = resources.getDimensionPixelSize(R.dimen.material_clock_size);
    }
}
