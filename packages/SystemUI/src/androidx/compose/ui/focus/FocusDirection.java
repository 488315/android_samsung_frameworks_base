package androidx.compose.ui.focus;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FocusDirection {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Next = 1;
    public static final int Previous = 2;
    public static final int Left = 3;
    public static final int Right = 4;
    public static final int Up = 5;
    public static final int Down = 6;
    public static final int Enter = 7;
    public static final int Exit = 8;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ FocusDirection(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FocusDirection m368boximpl(int i) {
        return new FocusDirection(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m369toStringimpl(int i) {
        return i == Next ? "Next" : i == Previous ? "Previous" : i == Left ? SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT : i == Right ? SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT : i == Up ? "Up" : i == Down ? "Down" : i == Enter ? "Enter" : i == Exit ? "Exit" : "Invalid FocusDirection";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof FocusDirection) {
            return this.value == ((FocusDirection) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m369toStringimpl(this.value);
    }
}
