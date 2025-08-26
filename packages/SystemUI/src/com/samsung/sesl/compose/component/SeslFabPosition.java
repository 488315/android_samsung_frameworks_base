package com.samsung.sesl.compose.component;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslFabPosition {
    public static final Companion Companion = new Companion(null);
    public static final int End = 1;
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof SeslFabPosition) {
            return this.value == ((SeslFabPosition) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return this.value == 0 ? "SeslFabPosition.Center" : "SeslFabPosition.End";
    }
}
