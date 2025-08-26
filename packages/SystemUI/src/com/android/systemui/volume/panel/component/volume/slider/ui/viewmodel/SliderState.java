package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;

/* loaded from: classes3.dex */
public interface SliderState {

    public final class Empty implements SliderState {
        public static final Empty INSTANCE = new Empty();
        public static final ClosedFloatRange valueRange = new ClosedFloatRange(0.0f, 1.0f);
        public static final SliderHapticFeedbackFilter hapticFilter = new SliderHapticFeedbackFilter(false, false, 3, null);
        public static final String label = "";
        public static final boolean isEnabled = true;

        private Empty() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Empty);
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getA11yClickDescription() {
            return null;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getA11yStateDescription() {
            return null;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getDisabledMessage() {
            return null;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final SliderHapticFeedbackFilter getHapticFilter() {
            return hapticFilter;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final Icon.Loaded getIcon() {
            return null;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final String getLabel() {
            return label;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final float getStep() {
            return 0.0f;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final float getValue() {
            return 0.0f;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final ClosedFloatingPointRange getValueRange() {
            return valueRange;
        }

        public final int hashCode() {
            return -1291628716;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isEnabled() {
            return isEnabled;
        }

        @Override // com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState
        public final boolean isMutable() {
            return false;
        }

        public final String toString() {
            return "Empty";
        }
    }

    String getA11yClickDescription();

    String getA11yStateDescription();

    String getDisabledMessage();

    SliderHapticFeedbackFilter getHapticFilter();

    Icon.Loaded getIcon();

    String getLabel();

    float getStep();

    float getValue();

    ClosedFloatingPointRange getValueRange();

    boolean isEnabled();

    boolean isMutable();
}
