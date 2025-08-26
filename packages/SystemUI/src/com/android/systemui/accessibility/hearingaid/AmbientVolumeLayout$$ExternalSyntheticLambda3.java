package com.android.systemui.accessibility.hearingaid;

import com.google.android.material.slider.Slider;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeLayout$$ExternalSyntheticLambda3 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AmbientVolumeLayout f$0;

    public /* synthetic */ AmbientVolumeLayout$$ExternalSyntheticLambda3(AmbientVolumeLayout ambientVolumeLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = ambientVolumeLayout;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        AmbientVolumeLayout ambientVolumeLayout = this.f$0;
        Integer num = (Integer) obj;
        switch (i) {
            case 0:
                AmbientVolumeSlider ambientVolumeSlider = (AmbientVolumeSlider) obj2;
                int i2 = AmbientVolumeLayout.$r8$clinit;
                ambientVolumeLayout.getClass();
                if (num.intValue() == 999) {
                    ambientVolumeSlider.setVisibility(ambientVolumeLayout.mExpanded ? 8 : 0);
                } else {
                    ambientVolumeSlider.setVisibility(ambientVolumeLayout.mExpanded ? 0 : 8);
                }
                if (!ambientVolumeSlider.mSlider.isEnabled()) {
                    Slider slider = ambientVolumeSlider.mSlider;
                    slider.setValues(Float.valueOf(slider.valueFrom));
                    break;
                }
                break;
            default:
                int i3 = AmbientVolumeLayout.$r8$clinit;
                ambientVolumeLayout.getClass();
                ambientVolumeLayout.createSlider(num.intValue());
                break;
        }
    }
}
