package androidx.compose.ui.text.style;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LineHeightStyle {
    public static final Companion Companion;
    public static final LineHeightStyle Default;
    public final float alignment;
    public final int mode;
    public final int trim;

    public final class Alignment {
        public static final float Bottom;
        public static final float Center;
        public static final Companion Companion = new Companion(null);
        public static final float Proportional;
        public final float topRatio;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            m802constructorimpl(0.0f);
            m802constructorimpl(0.5f);
            Center = 0.5f;
            m802constructorimpl(-1.0f);
            Proportional = -1.0f;
            m802constructorimpl(1.0f);
            Bottom = 1.0f;
        }

        private /* synthetic */ Alignment(float f) {
            this.topRatio = f;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Alignment m801boximpl(float f) {
            return new Alignment(f);
        }

        /* renamed from: constructor-impl, reason: not valid java name */
        public static void m802constructorimpl(float f) {
            if ((0.0f > f || f > 1.0f) && f != -1.0f) {
                InlineClassHelperKt.throwIllegalStateException("topRatio should be in [0..1] range or -1");
            }
        }

        /* renamed from: toString-impl, reason: not valid java name */
        public static String m803toStringimpl(float f) {
            if (f == 0.0f) {
                return "LineHeightStyle.Alignment.Top";
            }
            if (f == Center) {
                return "LineHeightStyle.Alignment.Center";
            }
            if (f == Proportional) {
                return "LineHeightStyle.Alignment.Proportional";
            }
            if (f == Bottom) {
                return "LineHeightStyle.Alignment.Bottom";
            }
            return "LineHeightStyle.Alignment(topPercentage = " + f + ')';
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Alignment) {
                return Float.compare(this.topRatio, ((Alignment) obj).topRatio) == 0;
            }
            return false;
        }

        public final int hashCode() {
            return Float.hashCode(this.topRatio);
        }

        public final String toString() {
            return m803toStringimpl(this.topRatio);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Mode {
        public static final Companion Companion = new Companion(null);
        public static final int Minimum = 1;
        public final int value;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        private /* synthetic */ Mode(int i) {
            this.value = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Mode m804boximpl(int i) {
            return new Mode(i);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Mode) {
                return this.value == ((Mode) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("Mode(value="), this.value, ')');
        }
    }

    public final class Trim {
        public final int value;
        public static final Companion Companion = new Companion(null);
        public static final int FirstLineTop = 1;
        public static final int LastLineBottom = 16;
        public static final int Both = 17;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        private /* synthetic */ Trim(int i) {
            this.value = i;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Trim m805boximpl(int i) {
            return new Trim(i);
        }

        /* renamed from: toString-impl, reason: not valid java name */
        public static String m806toStringimpl(int i) {
            return i == FirstLineTop ? "LineHeightStyle.Trim.FirstLineTop" : i == LastLineBottom ? "LineHeightStyle.Trim.LastLineBottom" : i == Both ? "LineHeightStyle.Trim.Both" : i == 0 ? "LineHeightStyle.Trim.None" : "Invalid";
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Trim) {
                return this.value == ((Trim) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            return m806toStringimpl(this.value);
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        Alignment.Companion.getClass();
        float f = Alignment.Proportional;
        Trim.Companion.getClass();
        int i = Trim.Both;
        Mode.Companion.getClass();
        Default = new LineHeightStyle(f, i, 0, defaultConstructorMarker);
    }

    public /* synthetic */ LineHeightStyle(float f, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, i, i2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineHeightStyle)) {
            return false;
        }
        LineHeightStyle lineHeightStyle = (LineHeightStyle) obj;
        float f = lineHeightStyle.alignment;
        Alignment.Companion companion = Alignment.Companion;
        if (Float.compare(this.alignment, f) == 0) {
            int i = lineHeightStyle.trim;
            Trim.Companion companion2 = Trim.Companion;
            if (this.trim == i) {
                int i2 = lineHeightStyle.mode;
                Mode.Companion companion3 = Mode.Companion;
                if (this.mode == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        Alignment.Companion companion = Alignment.Companion;
        int iHashCode = Float.hashCode(this.alignment) * 31;
        Trim.Companion companion2 = Trim.Companion;
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.trim, iHashCode, 31);
        Mode.Companion companion3 = Mode.Companion;
        return Integer.hashCode(this.mode) + iM;
    }

    public final String toString() {
        return "LineHeightStyle(alignment=" + ((Object) Alignment.m803toStringimpl(this.alignment)) + ", trim=" + ((Object) Trim.m806toStringimpl(this.trim)) + ",mode=" + ((Object) ("Mode(value=" + this.mode + ')')) + ')';
    }

    public /* synthetic */ LineHeightStyle(float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, i);
    }

    private LineHeightStyle(float f, int i, int i2) {
        this.alignment = f;
        this.trim = i;
        this.mode = i2;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private LineHeightStyle(float f, int i) {
        this(f, i, 0, null);
        Mode.Companion.getClass();
    }
}
