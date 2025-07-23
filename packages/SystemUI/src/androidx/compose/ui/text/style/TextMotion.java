package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextMotion {
    public static final TextMotion Animated;
    public static final Companion Companion;
    public static final TextMotion Static;
    public final int linearity;
    public final boolean subpixelTextPositioning;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Linearity {
        public final int value;
        public static final Companion Companion = new Companion(null);
        public static final int Linear = 1;
        public static final int FontHinting = 2;
        public static final int None = 3;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        private /* synthetic */ Linearity(int i) {
            this.value = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Linearity m811boximpl(int i) {
            return new Linearity(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Linearity) {
                return this.value == ((Linearity) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            int i = Linear;
            int i2 = this.value;
            return i2 == i ? "Linearity.Linear" : i2 == FontHinting ? "Linearity.FontHinting" : i2 == None ? "Linearity.None" : "Invalid";
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        Linearity.Companion companion = Linearity.Companion;
        companion.getClass();
        Static = new TextMotion(Linearity.FontHinting, false, defaultConstructorMarker);
        companion.getClass();
        Animated = new TextMotion(Linearity.Linear, true, defaultConstructorMarker);
    }

    public /* synthetic */ TextMotion(int i, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextMotion)) {
            return false;
        }
        TextMotion textMotion = (TextMotion) obj;
        int i = textMotion.linearity;
        Linearity.Companion companion = Linearity.Companion;
        return this.linearity == i && this.subpixelTextPositioning == textMotion.subpixelTextPositioning;
    }

    public final int hashCode() {
        Linearity.Companion companion = Linearity.Companion;
        return Boolean.hashCode(this.subpixelTextPositioning) + (Integer.hashCode(this.linearity) * 31);
    }

    public final String toString() {
        return equals(Static) ? "TextMotion.Static" : equals(Animated) ? "TextMotion.Animated" : "Invalid";
    }

    private TextMotion(int i, boolean z) {
        this.linearity = i;
        this.subpixelTextPositioning = z;
    }
}
