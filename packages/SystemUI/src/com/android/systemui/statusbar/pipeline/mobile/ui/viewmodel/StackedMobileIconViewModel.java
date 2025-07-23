package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface StackedMobileIconViewModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DualSim {
        public final SignalIconModel.Cellular primary;
        public final SignalIconModel.Cellular secondary;

        public DualSim(SignalIconModel.Cellular cellular, SignalIconModel.Cellular cellular2) {
            this.primary = cellular;
            this.secondary = cellular2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DualSim)) {
                return false;
            }
            DualSim dualSim = (DualSim) obj;
            return Intrinsics.areEqual(this.primary, dualSim.primary) && Intrinsics.areEqual(this.secondary, dualSim.secondary);
        }

        public final int hashCode() {
            return this.secondary.hashCode() + (this.primary.hashCode() * 31);
        }

        public final String toString() {
            return "DualSim(primary=" + this.primary + ", secondary=" + this.secondary + ")";
        }
    }

    DualSim getDualSim();

    Icon.Resource getNetworkTypeIcon();
}
