package androidx.compose.ui.text;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EmojiSupportMatch {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int None = 1;
    public static final int All = 2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ EmojiSupportMatch(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ EmojiSupportMatch m729boximpl(int i) {
        return new EmojiSupportMatch(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m730toStringimpl(int i) {
        if (i == 0) {
            return "EmojiSupportMatch.Default";
        }
        if (i == None) {
            return "EmojiSupportMatch.None";
        }
        if (i == All) {
            return "EmojiSupportMatch.All";
        }
        return "Invalid(value=" + i + ')';
    }

    public final boolean equals(Object obj) {
        if (obj instanceof EmojiSupportMatch) {
            return this.value == ((EmojiSupportMatch) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m730toStringimpl(this.value);
    }
}
