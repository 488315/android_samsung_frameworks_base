package com.google.android.material.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Slider extends BaseSlider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnChangeListener {
        void onValueChange(BaseSlider baseSlider, float f, boolean z);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnSliderTouchListener {
        void onStartTrackingTouch(BaseSlider baseSlider);

        void onStopTrackingTouch(BaseSlider baseSlider);
    }

    public Slider(Context context) {
        this(context, null);
    }

    public final float getValue() {
        return ((Float) ((ArrayList) getValues()).get(0)).floatValue();
    }

    @Override // com.google.android.material.slider.BaseSlider
    public final boolean pickActiveThumb() {
        if (this.activeThumbIdx != -1) {
            return true;
        }
        this.activeThumbIdx = 0;
        return true;
    }

    public Slider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sliderStyle);
    }

    public Slider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{android.R.attr.value});
        if (obtainStyledAttributes.hasValue(0)) {
            setValues(Float.valueOf(obtainStyledAttributes.getFloat(0, 0.0f)));
        }
        obtainStyledAttributes.recycle();
    }
}
