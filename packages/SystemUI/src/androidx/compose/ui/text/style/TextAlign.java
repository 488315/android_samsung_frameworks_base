package androidx.compose.ui.text.style;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextAlign {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Left = 1;
    public static final int Right = 2;
    public static final int Center = 3;
    public static final int Justify = 4;
    public static final int Start = 5;
    public static final int End = 6;
    public static final int Unspecified = Integer.MIN_VALUE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ TextAlign(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ TextAlign m805boximpl(int i) {
        return new TextAlign(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m806toStringimpl(int i) {
        return i == Left ? SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT : i == Right ? SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT : i == Center ? SystemUIAnalytics.DT_BOUNCER_POSITION_CENTER : i == Justify ? "Justify" : i == Start ? "Start" : i == End ? "End" : i == Unspecified ? "Unspecified" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TextAlign) {
            return this.value == ((TextAlign) obj).value;
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
