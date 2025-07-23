package com.android.systemui.volume.ui.compose.slider;

import androidx.compose.foundation.gestures.Orientation;
import com.android.systemui.haptics.slider.SliderHapticFeedbackFilter;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface Haptics {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Disabled implements Haptics {
        public static final Disabled INSTANCE = new Disabled();

        private Disabled() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Disabled);
        }

        public final int hashCode() {
            return -141871110;
        }

        public final String toString() {
            return KnoxVpnPolicyConstants.VPN_CERT_TYPE_DISABLED;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Enabled implements Haptics {
        public final SliderHapticFeedbackFilter hapticFilter;
        public final SliderHapticsViewModel.Factory hapticsViewModelFactory;
        public final Orientation orientation;

        public Enabled(SliderHapticsViewModel.Factory factory, SliderHapticFeedbackFilter sliderHapticFeedbackFilter, Orientation orientation) {
            this.hapticsViewModelFactory = factory;
            this.hapticFilter = sliderHapticFeedbackFilter;
            this.orientation = orientation;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Enabled)) {
                return false;
            }
            Enabled enabled = (Enabled) obj;
            return Intrinsics.areEqual(this.hapticsViewModelFactory, enabled.hapticsViewModelFactory) && Intrinsics.areEqual(this.hapticFilter, enabled.hapticFilter) && this.orientation == enabled.orientation;
        }

        public final int hashCode() {
            return this.orientation.hashCode() + ((this.hapticFilter.hashCode() + (this.hapticsViewModelFactory.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "Enabled(hapticsViewModelFactory=" + this.hapticsViewModelFactory + ", hapticFilter=" + this.hapticFilter + ", orientation=" + this.orientation + ")";
        }
    }
}
