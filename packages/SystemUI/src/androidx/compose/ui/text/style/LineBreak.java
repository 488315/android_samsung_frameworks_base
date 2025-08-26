package androidx.compose.ui.text.style;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LineBreak {
    public static final Companion Companion = new Companion(null);
    public static final int Simple;
    public final int mask;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Strategy {
        public final int value;
        public static final Companion Companion = new Companion(null);
        public static final int Simple = 1;
        public static final int HighQuality = 2;
        public static final int Balanced = 3;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        /* renamed from: toString-impl, reason: not valid java name */
        public static String m799toStringimpl(int i) {
            return i == Simple ? "Strategy.Simple" : i == HighQuality ? "Strategy.HighQuality" : i == Balanced ? "Strategy.Balanced" : i == 0 ? "Strategy.Unspecified" : "Invalid";
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Strategy) {
                return this.value == ((Strategy) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            return m799toStringimpl(this.value);
        }
    }

    public final class Strictness {
        public static final Companion Companion = new Companion(null);
        public static final int Default = 1;
        public static final int Loose = 2;
        public static final int Normal = 3;
        public static final int Strict = 4;
        public final int value;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        /* renamed from: toString-impl, reason: not valid java name */
        public static String m800toStringimpl(int i) {
            return i == Default ? "Strictness.None" : i == Loose ? "Strictness.Loose" : i == Normal ? "Strictness.Normal" : i == Strict ? "Strictness.Strict" : i == 0 ? "Strictness.Unspecified" : "Invalid";
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Strictness) {
                return this.value == ((Strictness) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            return m800toStringimpl(this.value);
        }
    }

    public final class WordBreak {
        public static final Companion Companion = new Companion(null);
        public static final int Default = 1;
        public static final int Phrase = 2;
        public final int value;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public final boolean equals(Object obj) {
            if (obj instanceof WordBreak) {
                return this.value == ((WordBreak) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            int i = Default;
            int i2 = this.value;
            return i2 == i ? "WordBreak.None" : i2 == Phrase ? "WordBreak.Phrase" : i2 == 0 ? "WordBreak.Unspecified" : "Invalid";
        }
    }

    static {
        Strategy.Companion.getClass();
        int i = Strategy.Simple;
        Strictness.Companion.getClass();
        int i2 = Strictness.Normal;
        WordBreak.Companion.getClass();
        Simple = i | (i2 << 8) | (WordBreak.Default << 16);
    }

    private /* synthetic */ LineBreak(int i) {
        this.mask = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LineBreak m797boximpl(int i) {
        return new LineBreak(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m798toStringimpl(int i) {
        StringBuilder sb = new StringBuilder("LineBreak(strategy=");
        sb.append((Object) Strategy.m799toStringimpl(i & 255));
        sb.append(", strictness=");
        sb.append((Object) Strictness.m800toStringimpl((i >> 8) & 255));
        sb.append(", wordBreak=");
        int i2 = (i >> 16) & 255;
        sb.append((Object) (i2 == WordBreak.Default ? "WordBreak.None" : i2 == WordBreak.Phrase ? "WordBreak.Phrase" : i2 == 0 ? "WordBreak.Unspecified" : "Invalid"));
        sb.append(')');
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof LineBreak) {
            return this.mask == ((LineBreak) obj).mask;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.mask);
    }

    public final String toString() {
        return m798toStringimpl(this.mask);
    }
}
