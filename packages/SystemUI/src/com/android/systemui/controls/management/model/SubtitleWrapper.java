package com.android.systemui.controls.management.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class SubtitleWrapper extends StructureElementWrapper {
    public final String subtitle;

    public SubtitleWrapper(String str) {
        super(null);
        this.subtitle = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SubtitleWrapper) && Intrinsics.areEqual(this.subtitle, ((SubtitleWrapper) obj).subtitle);
    }

    public final int hashCode() {
        return this.subtitle.hashCode();
    }

    public final String toString() {
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("SubtitleWrapper(subtitle="), this.subtitle, ")");
    }
}
