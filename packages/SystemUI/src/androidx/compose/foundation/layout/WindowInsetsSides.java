package androidx.compose.foundation.layout;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class WindowInsetsSides {
    public static final int Horizontal;
    public static final int Left;
    public static final int Right;
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int AllowLeftInLtr = 8;
    public static final int AllowRightInLtr = 4;
    public static final int AllowLeftInRtl = 2;
    public static final int AllowRightInRtl = 1;
    public static final int Start = 8 | 1;
    public static final int End = 4 | 2;
    public static final int Top = 16;
    public static final int Bottom = 32;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        int i = 8 | 2;
        Left = i;
        int i2 = 4 | 1;
        Right = i2;
        Horizontal = i | i2;
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m150toStringimpl(int i) {
        StringBuilder sb = new StringBuilder("WindowInsetsSides(");
        StringBuilder sb2 = new StringBuilder();
        int i2 = Start;
        if ((i & i2) == i2) {
            valueToString_impl$lambda$0$appendPlus(sb2, "Start");
        }
        int i3 = Left;
        if ((i & i3) == i3) {
            valueToString_impl$lambda$0$appendPlus(sb2, SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT);
        }
        int i4 = Top;
        if ((i & i4) == i4) {
            valueToString_impl$lambda$0$appendPlus(sb2, "Top");
        }
        int i5 = End;
        if ((i & i5) == i5) {
            valueToString_impl$lambda$0$appendPlus(sb2, "End");
        }
        int i6 = Right;
        if ((i & i6) == i6) {
            valueToString_impl$lambda$0$appendPlus(sb2, SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT);
        }
        int i7 = Bottom;
        if ((i & i7) == i7) {
            valueToString_impl$lambda$0$appendPlus(sb2, "Bottom");
        }
        sb.append(sb2.toString());
        sb.append(')');
        return sb.toString();
    }

    public static final void valueToString_impl$lambda$0$appendPlus(StringBuilder sb, String str) {
        if (sb.length() > 0) {
            sb.append('+');
        }
        sb.append(str);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof WindowInsetsSides) {
            return this.value == ((WindowInsetsSides) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m150toStringimpl(this.value);
    }
}
