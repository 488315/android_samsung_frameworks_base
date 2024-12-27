package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class TransitionData {
    public final IntraBlueprintTransition.Config config;
    public final long start;

    public TransitionData(IntraBlueprintTransition.Config config, long j) {
        this.config = config;
        this.start = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransitionData)) {
            return false;
        }
        TransitionData transitionData = (TransitionData) obj;
        return Intrinsics.areEqual(this.config, transitionData.config) && this.start == transitionData.start;
    }

    public final int hashCode() {
        return Long.hashCode(this.start) + (this.config.hashCode() * 31);
    }

    public final String toString() {
        return "TransitionData(config=" + this.config + ", start=" + this.start + ")";
    }

    public /* synthetic */ TransitionData(IntraBlueprintTransition.Config config, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(config, (i & 2) != 0 ? System.currentTimeMillis() : j);
    }
}
