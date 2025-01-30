package com.android.systemui.controls.management.model;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("SubtitleWrapper(subtitle="), this.subtitle, ")");
    }
}
