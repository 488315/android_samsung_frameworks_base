package com.android.systemui.accessibility.hearingaid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.slider.Slider;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AmbientVolumeSlider extends LinearLayout {
    public final List mChangeListeners;
    public final Slider mSlider;
    public final AnonymousClass2 mSliderChangeListener;
    public final AnonymousClass1 mSliderTouchListener;
    public final TextView mTitle;
    public boolean mTrackingTouch;

    public AmbientVolumeSlider(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final boolean isEnabled() {
        return this.mSlider.isEnabled();
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        this.mSlider.setEnabled(z);
    }

    public AmbientVolumeSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AmbientVolumeSlider(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.accessibility.hearingaid.AmbientVolumeSlider$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.accessibility.hearingaid.AmbientVolumeSlider$2, java.lang.Object] */
    public AmbientVolumeSlider(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mChangeListeners = new ArrayList();
        ?? r3 = new Slider.OnSliderTouchListener() { // from class: com.android.systemui.accessibility.hearingaid.AmbientVolumeSlider.1
            @Override // com.google.android.material.slider.Slider.OnSliderTouchListener
            public final void onStartTrackingTouch(BaseSlider baseSlider) {
                AmbientVolumeSlider.this.mTrackingTouch = true;
            }

            @Override // com.google.android.material.slider.Slider.OnSliderTouchListener
            public final void onStopTrackingTouch(BaseSlider baseSlider) {
                AmbientVolumeSlider ambientVolumeSlider = AmbientVolumeSlider.this;
                int i3 = 0;
                ambientVolumeSlider.mTrackingTouch = false;
                int iRound = Math.round(((Slider) baseSlider).getValue());
                ArrayList arrayList = (ArrayList) ambientVolumeSlider.mChangeListeners;
                int size = arrayList.size();
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    ((AmbientVolumeLayout$$ExternalSyntheticLambda0) obj).onValueChange(ambientVolumeSlider, iRound);
                }
            }
        };
        this.mSliderTouchListener = r3;
        ?? r4 = new Slider.OnChangeListener() { // from class: com.android.systemui.accessibility.hearingaid.AmbientVolumeSlider.2
            @Override // com.google.android.material.slider.Slider.OnChangeListener
            public final void onValueChange(BaseSlider baseSlider, float f, boolean z) {
                if (z) {
                    AmbientVolumeSlider ambientVolumeSlider = AmbientVolumeSlider.this;
                    if (ambientVolumeSlider.mTrackingTouch) {
                        return;
                    }
                    int iRound = Math.round(f);
                    ArrayList arrayList = (ArrayList) ambientVolumeSlider.mChangeListeners;
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        ((AmbientVolumeLayout$$ExternalSyntheticLambda0) obj).onValueChange(ambientVolumeSlider, iRound);
                    }
                }
            }
        };
        this.mSliderChangeListener = r4;
        this.mTrackingTouch = false;
        LinearLayout.inflate(context, R.layout.hearing_device_ambient_volume_slider, this);
        this.mTitle = (TextView) requireViewById(R.id.ambient_volume_slider_title);
        Slider slider = (Slider) requireViewById(R.id.ambient_volume_slider);
        this.mSlider = slider;
        ((ArrayList) slider.touchListeners).add(r3);
        ((ArrayList) slider.changeListeners).add(r4);
        setFocusable(false);
        setClickable(false);
        slider.setFocusable(false);
        slider.setClickable(false);
    }
}
