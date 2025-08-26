package com.samsung.sesl.compose.foundation.scroll;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslScrollingStrategy$Fixed {
    public final Function1 calculateHeight;

    public SeslScrollingStrategy$Fixed(Function1 function1) {
        this.calculateHeight = function1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SeslScrollingStrategy$Fixed) && Intrinsics.areEqual(this.calculateHeight, ((SeslScrollingStrategy$Fixed) obj).calculateHeight);
    }

    public final int hashCode() {
        return this.calculateHeight.hashCode();
    }

    public final String toString() {
        return "Fixed(calculateHeight=" + this.calculateHeight + ")";
    }
}
