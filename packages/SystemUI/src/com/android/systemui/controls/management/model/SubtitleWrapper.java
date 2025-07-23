package com.android.systemui.controls.management.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("SubtitleWrapper(subtitle="), this.subtitle, ")");
    }
}
