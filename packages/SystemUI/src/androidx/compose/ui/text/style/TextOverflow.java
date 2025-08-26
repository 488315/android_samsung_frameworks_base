package androidx.compose.ui.text.style;

import com.android.systemui.bixby2.actionresult.ActionResults;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TextOverflow {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Clip = 1;
    public static final int Ellipsis = 2;
    public static final int Visible = 3;
    public static final int StartEllipsis = 4;
    public static final int MiddleEllipsis = 5;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m814toStringimpl(int i) {
        return i == Clip ? "Clip" : i == Ellipsis ? "Ellipsis" : i == MiddleEllipsis ? "MiddleEllipsis" : i == Visible ? ActionResults.RESULT_LAUNCHER_VISIBLE : i == StartEllipsis ? "StartEllipsis" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof TextOverflow) {
            return this.value == ((TextOverflow) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m814toStringimpl(this.value);
    }
}
