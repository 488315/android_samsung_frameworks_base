package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;
import com.android.systemui.R;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.slider.Slider;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SliderPreference extends Preference {
    public final boolean mAdjustable;
    public final AnonymousClass3 mChangeListener;
    public final ColorStateList mHaloColor;
    public final int mIconEndContentDescriptionId;
    public final int mIconEndId;
    public final int mIconStartContentDescriptionId;
    public final int mIconStartId;
    public int mMax;
    public int mMin;
    public final boolean mShowSliderValue;
    public Slider mSlider;
    public int mSliderIncrement;
    public final AnonymousClass1 mSliderKeyListener;
    public int mSliderValue;
    public final int mTextEndId;
    public final int mTextStartId;
    public final ColorStateList mThumbColor;
    public final int mThumbElevation;
    public final int mThumbStrokeWidth;
    public final int mTickRadius;
    public final AnonymousClass2 mTouchListener;
    public final ColorStateList mTrackActiveColor;
    public final int mTrackHeight;
    public final ColorStateList mTrackInactiveColor;
    public boolean mTrackingTouch;
    public final boolean mUpdatesContinuously;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settingslib.widget.SliderPreference$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settingslib.widget.SliderPreference$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settingslib.widget.SliderPreference$3] */
    public SliderPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSliderKeyListener = new View.OnKeyListener() { // from class: com.android.settingslib.widget.SliderPreference.1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                SliderPreference sliderPreference = SliderPreference.this;
                if ((!sliderPreference.mAdjustable && (i2 == 21 || i2 == 22)) || i2 == 23 || i2 == 66) {
                    return false;
                }
                Slider slider = sliderPreference.mSlider;
                if (slider != null) {
                    return slider.onKeyDown(i2, keyEvent);
                }
                Log.e("SliderPreference", "Slider view is null and hence cannot be adjusted.");
                return false;
            }
        };
        this.mTouchListener = new Slider.OnSliderTouchListener() { // from class: com.android.settingslib.widget.SliderPreference.2
            @Override // com.google.android.material.slider.Slider.OnSliderTouchListener
            public final void onStartTrackingTouch(BaseSlider baseSlider) {
                SliderPreference.this.mTrackingTouch = true;
            }

            @Override // com.google.android.material.slider.Slider.OnSliderTouchListener
            public final void onStopTrackingTouch(BaseSlider baseSlider) {
                Slider slider = (Slider) baseSlider;
                SliderPreference sliderPreference = SliderPreference.this;
                sliderPreference.mTrackingTouch = false;
                if (((int) slider.getValue()) != sliderPreference.mSliderValue) {
                    sliderPreference.syncValueInternal(slider);
                }
            }
        };
        this.mChangeListener = new Slider.OnChangeListener() { // from class: com.android.settingslib.widget.SliderPreference.3
            @Override // com.google.android.material.slider.Slider.OnChangeListener
            public final void onValueChange(BaseSlider baseSlider, float f, boolean z) {
                Slider slider = (Slider) baseSlider;
                if (z) {
                    SliderPreference sliderPreference = SliderPreference.this;
                    if (sliderPreference.mUpdatesContinuously || !sliderPreference.mTrackingTouch) {
                        sliderPreference.syncValueInternal(slider);
                    }
                }
            }
        };
        this.mLayoutResId = R.layout.settingslib_expressive_preference_slider;
        setSelectable(false);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SeekBarPreference, i, 0);
        this.mMin = obtainStyledAttributes.getInt(3, 0);
        int i2 = obtainStyledAttributes.getInt(1, 100);
        int i3 = this.mMin;
        i2 = i2 < i3 ? i3 : i2;
        if (i2 != this.mMax) {
            this.mMax = i2;
            notifyChanged();
        }
        int i4 = obtainStyledAttributes.getInt(4, 0);
        if (i4 != this.mSliderIncrement) {
            this.mSliderIncrement = Math.min(this.mMax - this.mMin, Math.abs(i4));
            notifyChanged();
        }
        this.mAdjustable = obtainStyledAttributes.getBoolean(2, true);
        this.mShowSliderValue = obtainStyledAttributes.getBoolean(5, false);
        this.mUpdatesContinuously = obtainStyledAttributes.getBoolean(6, false);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.settingslib.widget.preference.slider.R$styleable.SliderPreference);
        this.mTextStartId = obtainStyledAttributes2.getResourceId(5, 0);
        this.mTextEndId = obtainStyledAttributes2.getResourceId(4, 0);
        this.mIconStartId = obtainStyledAttributes2.getResourceId(2, 0);
        this.mIconEndId = obtainStyledAttributes2.getResourceId(0, 0);
        this.mIconStartContentDescriptionId = obtainStyledAttributes2.getResourceId(3, 0);
        this.mIconEndContentDescriptionId = obtainStyledAttributes2.getResourceId(1, 0);
        obtainStyledAttributes2.recycle();
        this.mTrackActiveColor = context.getColorStateList(R.color.settingslib_expressive_color_slider_track_active);
        this.mTrackInactiveColor = context.getColorStateList(R.color.settingslib_expressive_color_slider_track_inactive);
        this.mThumbColor = context.getColorStateList(R.color.settingslib_expressive_color_slider_thumb);
        this.mHaloColor = context.getColorStateList(R.color.settingslib_expressive_color_slider_halo);
        Resources resources = context.getResources();
        this.mTrackHeight = resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_track_height);
        resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_track_inside_corner_size);
        resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_track_stop_indicator_size);
        resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_thumb_width);
        resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_thumb_height);
        this.mThumbElevation = resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_thumb_elevation);
        this.mThumbStrokeWidth = resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_thumb_stroke_width);
        resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_thumb_track_gap_size);
        this.mTickRadius = resources.getDimensionPixelSize(R.dimen.settingslib_expressive_slider_tick_radius);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this.mSliderKeyListener);
        Slider slider = (Slider) preferenceViewHolder.findViewById(R.id.slider);
        this.mSlider = slider;
        if (slider == null) {
            Log.e("SliderPreference", "Slider is null in onBindViewHolder.");
            return;
        }
        if (this.mShowSliderValue) {
            if (slider.labelBehavior != 0) {
                slider.labelBehavior = 0;
                slider.requestLayout();
            }
        } else if (slider.labelBehavior != 2) {
            slider.labelBehavior = 2;
            slider.requestLayout();
        }
        int i = this.mSliderIncrement;
        if (i != 0) {
            Slider slider2 = this.mSlider;
            float f = i;
            if (f < 0.0f) {
                slider2.getClass();
                throw new IllegalArgumentException(DpCornerSize$$ExternalSyntheticOutline0.m(slider2.valueTo, ") range", CubicBezierEasing$$ExternalSyntheticOutline0.m("The stepSize(", f, ") must be 0, or a factor of the valueFrom(", slider2.valueFrom, ")-valueTo(")));
            }
            if (slider2.stepSize != f) {
                slider2.stepSize = f;
                slider2.dirtyConfig = true;
                slider2.postInvalidate();
            }
            Slider slider3 = this.mSlider;
            if (slider3.tickVisible) {
                slider3.tickVisible = false;
                slider3.postInvalidate();
            }
        } else {
            this.mSliderIncrement = (int) this.mSlider.stepSize;
        }
        CharSequence charSequence = this.mTitle;
        if (!TextUtils.isEmpty(null)) {
            this.mSlider.setContentDescription(null);
        } else if (TextUtils.isEmpty(charSequence)) {
            this.mSlider.setContentDescription(null);
        } else {
            this.mSlider.setContentDescription(charSequence);
        }
        Slider slider4 = this.mSlider;
        slider4.valueFrom = this.mMin;
        slider4.dirtyConfig = true;
        slider4.postInvalidate();
        Slider slider5 = this.mSlider;
        slider5.valueTo = this.mMax;
        slider5.dirtyConfig = true;
        slider5.postInvalidate();
        this.mSlider.setValues(Float.valueOf(this.mSliderValue));
        ((ArrayList) this.mSlider.touchListeners).clear();
        ((ArrayList) this.mSlider.touchListeners).add(this.mTouchListener);
        ((ArrayList) this.mSlider.changeListeners).clear();
        ((ArrayList) this.mSlider.changeListeners).add(this.mChangeListener);
        this.mSlider.setEnabled(isEnabled());
        this.mSlider.setFocusable(this.mSelectable);
        this.mSlider.setClickable(this.mSelectable);
        this.mSlider.setTrackActiveTintList(this.mTrackActiveColor);
        this.mSlider.setTrackInactiveTintList(this.mTrackInactiveColor);
        Slider slider6 = this.mSlider;
        ColorStateList colorStateList = this.mThumbColor;
        if (!colorStateList.equals(slider6.defaultThumbDrawable.drawableState.fillColor)) {
            slider6.defaultThumbDrawable.setFillColor(colorStateList);
            slider6.invalidate();
        }
        this.mSlider.setHaloTintList(this.mHaloColor);
        Slider slider7 = this.mSlider;
        ColorStateList colorStateList2 = this.mTrackInactiveColor;
        if (!colorStateList2.equals(slider7.tickColorActive)) {
            slider7.tickColorActive = colorStateList2;
            slider7.activeTicksPaint.setColor(slider7.getColorForState(colorStateList2));
            slider7.invalidate();
        }
        Slider slider8 = this.mSlider;
        ColorStateList colorStateList3 = this.mTrackActiveColor;
        if (!colorStateList3.equals(slider8.tickColorInactive)) {
            slider8.tickColorInactive = colorStateList3;
            slider8.inactiveTicksPaint.setColor(slider8.getColorForState(colorStateList3));
            slider8.invalidate();
        }
        if (SettingsThemeHelper.isExpressiveTheme(this.mContext)) {
            Slider slider9 = this.mSlider;
            int i2 = this.mTrackHeight;
            if (slider9.trackHeight != i2) {
                slider9.trackHeight = i2;
                slider9.inactiveTrackPaint.setStrokeWidth(i2);
                slider9.activeTrackPaint.setStrokeWidth(slider9.trackHeight);
                slider9.updateWidgetLayout();
            }
            this.mSlider.defaultThumbDrawable.setElevation(this.mThumbElevation);
            Slider slider10 = this.mSlider;
            float f2 = this.mThumbStrokeWidth;
            MaterialShapeDrawable materialShapeDrawable = slider10.defaultThumbDrawable;
            materialShapeDrawable.drawableState.strokeWidth = f2;
            materialShapeDrawable.invalidateSelf();
            slider10.postInvalidate();
            Slider slider11 = this.mSlider;
            int i3 = this.mTickRadius;
            if (slider11.tickActiveRadius != i3) {
                slider11.tickActiveRadius = i3;
                slider11.activeTicksPaint.setStrokeWidth(i3 * 2);
                slider11.updateWidgetLayout();
            }
            Slider slider12 = this.mSlider;
            int i4 = this.mTickRadius;
            if (slider12.tickInactiveRadius != i4) {
                slider12.tickInactiveRadius = i4;
                slider12.inactiveTicksPaint.setStrokeWidth(i4 * 2);
                slider12.updateWidgetLayout();
            }
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.text1);
        int i5 = this.mTextStartId;
        if (i5 > 0 && textView != null) {
            textView.setText(i5);
        }
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(android.R.id.text2);
        int i6 = this.mTextEndId;
        if (i6 > 0 && textView2 != null) {
            textView2.setText(i6);
        }
        View findViewById = preferenceViewHolder.findViewById(R.id.label_frame);
        if (findViewById != null) {
            findViewById.setVisibility((this.mTextStartId > 0 || this.mTextEndId > 0) ? 0 : 8);
        }
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.icon_start);
        if (imageView != null && (viewGroup2 = (ViewGroup) imageView.getParent()) != null) {
            if (this.mIconStartId == 0 || this.mSliderIncrement == 0) {
                viewGroup2.setVisibility(8);
            } else {
                if (imageView.getDrawable() == null) {
                    imageView.setImageResource(this.mIconStartId);
                }
                if (this.mIconStartContentDescriptionId != 0) {
                    viewGroup2.setContentDescription(viewGroup2.getContext().getString(this.mIconStartContentDescriptionId));
                }
                final int i7 = 0;
                viewGroup2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.widget.SliderPreference$$ExternalSyntheticLambda0
                    public final /* synthetic */ SliderPreference f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i8 = i7;
                        SliderPreference sliderPreference = this.f$0;
                        switch (i8) {
                            case 0:
                                int i9 = sliderPreference.mSliderValue;
                                if (i9 > 0) {
                                    sliderPreference.setValueInternal$1(i9 - sliderPreference.mSliderIncrement, true);
                                    break;
                                }
                                break;
                            default:
                                int i10 = sliderPreference.mSliderValue;
                                if (i10 < sliderPreference.mMax) {
                                    sliderPreference.setValueInternal$1(i10 + sliderPreference.mSliderIncrement, true);
                                    break;
                                }
                                break;
                        }
                    }
                });
                viewGroup2.setVisibility(0);
                boolean z = this.mSliderValue > this.mMin;
                imageView.setEnabled(z);
                viewGroup2.setEnabled(z);
            }
        }
        ImageView imageView2 = (ImageView) preferenceViewHolder.findViewById(R.id.icon_end);
        if (imageView2 == null || (viewGroup = (ViewGroup) imageView2.getParent()) == null) {
            return;
        }
        if (this.mIconEndId == 0 || this.mSliderIncrement == 0) {
            viewGroup.setVisibility(8);
            return;
        }
        if (imageView2.getDrawable() == null) {
            imageView2.setImageResource(this.mIconEndId);
        }
        if (this.mIconEndContentDescriptionId != 0) {
            viewGroup.setContentDescription(viewGroup.getContext().getString(this.mIconEndContentDescriptionId));
        }
        final int i8 = 1;
        viewGroup.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settingslib.widget.SliderPreference$$ExternalSyntheticLambda0
            public final /* synthetic */ SliderPreference f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i82 = i8;
                SliderPreference sliderPreference = this.f$0;
                switch (i82) {
                    case 0:
                        int i9 = sliderPreference.mSliderValue;
                        if (i9 > 0) {
                            sliderPreference.setValueInternal$1(i9 - sliderPreference.mSliderIncrement, true);
                            break;
                        }
                        break;
                    default:
                        int i10 = sliderPreference.mSliderValue;
                        if (i10 < sliderPreference.mMax) {
                            sliderPreference.setValueInternal$1(i10 + sliderPreference.mSliderIncrement, true);
                            break;
                        }
                        break;
                }
            }
        });
        viewGroup.setVisibility(0);
        boolean z2 = this.mSliderValue < this.mMax;
        imageView2.setEnabled(z2);
        viewGroup.setEnabled(z2);
    }

    @Override // androidx.preference.Preference
    public final Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSliderValue = savedState.mSliderValue;
        this.mMin = savedState.mMin;
        this.mMax = savedState.mMax;
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
        AbsSavedState absSavedState = AbsSavedState.EMPTY_STATE;
        if (this.mPersistent) {
            return absSavedState;
        }
        SavedState savedState = new SavedState(absSavedState);
        savedState.mSliderValue = this.mSliderValue;
        savedState.mMin = this.mMin;
        savedState.mMax = this.mMax;
        return savedState;
    }

    @Override // androidx.preference.Preference
    public final void onSetInitialValue(Object obj) {
        if (obj == null) {
            obj = 0;
        }
        setValueInternal$1(getPersistedInt(((Integer) obj).intValue()), true);
    }

    public final void setValueInternal$1(int i, boolean z) {
        int i2 = this.mMin;
        if (i < i2) {
            i = i2;
        }
        int i3 = this.mMax;
        if (i > i3) {
            i = i3;
        }
        if (i != this.mSliderValue) {
            this.mSliderValue = i;
            persistInt(i);
            if (z) {
                notifyChanged();
            }
        }
    }

    public final void syncValueInternal(Slider slider) {
        int value = (int) slider.getValue();
        if (value != this.mSliderValue) {
            if (callChangeListener(Integer.valueOf(value))) {
                setValueInternal$1(value, false);
            } else {
                slider.setValues(Float.valueOf(this.mSliderValue));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: com.android.settingslib.widget.SliderPreference.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int mMax;
        public int mMin;
        public int mSliderValue;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mSliderValue = parcel.readInt();
            this.mMin = parcel.readInt();
            this.mMax = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSliderValue);
            parcel.writeInt(this.mMin);
            parcel.writeInt(this.mMax);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public SliderPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SliderPreference(Context context) {
        this(context, null);
    }
}
