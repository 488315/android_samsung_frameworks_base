package androidx.compose.ui.text;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class PlatformSpanStyle {
    public static final Companion Companion = new Companion(null);
    public static final PlatformSpanStyle Default = new PlatformSpanStyle();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof PlatformSpanStyle);
    }

    public final String toString() {
        return "PlatformSpanStyle()";
    }
}
