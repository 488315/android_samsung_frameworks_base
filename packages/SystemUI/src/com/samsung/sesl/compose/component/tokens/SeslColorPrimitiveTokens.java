package com.samsung.sesl.compose.component.tokens;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslColorPrimitiveTokens implements SeslColorSchemeKeyTokens {
    public final long color;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ SeslColorPrimitiveTokens(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslColorPrimitiveTokens)) {
            return false;
        }
        long j = ((SeslColorPrimitiveTokens) obj).color;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.color, j);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.color);
    }

    public final String toString() {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("SeslColorPrimitiveTokens(color=", Color.m464toStringimpl(this.color), ")");
    }

    private SeslColorPrimitiveTokens(long j) {
        this.color = j;
    }
}
