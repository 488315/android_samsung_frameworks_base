package androidx.compose.ui.text;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlaceholderVerticalAlign {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int AboveBaseline = 1;
    public static final int Top = 2;
    public static final int Bottom = 3;
    public static final int Center = 4;
    public static final int TextTop = 5;
    public static final int TextBottom = 6;
    public static final int TextCenter = 7;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m740toStringimpl(int i) {
        return i == AboveBaseline ? "AboveBaseline" : i == Top ? "Top" : i == Bottom ? "Bottom" : i == Center ? SystemUIAnalytics.DT_BOUNCER_POSITION_CENTER : i == TextTop ? "TextTop" : i == TextBottom ? "TextBottom" : i == TextCenter ? "TextCenter" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof PlaceholderVerticalAlign) {
            return this.value == ((PlaceholderVerticalAlign) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m740toStringimpl(this.value);
    }
}
