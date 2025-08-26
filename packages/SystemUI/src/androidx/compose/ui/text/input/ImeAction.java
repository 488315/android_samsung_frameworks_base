package androidx.compose.ui.text.input;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ImeAction {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Unspecified = -1;
    public static final int Default = 1;
    public static final int Go = 2;
    public static final int Search = 3;
    public static final int Send = 4;
    public static final int Previous = 5;
    public static final int Next = 6;
    public static final int Done = 7;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ ImeAction(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ImeAction m774boximpl(int i) {
        return new ImeAction(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m775toStringimpl(int i) {
        return i == Unspecified ? "Unspecified" : i == 0 ? "None" : i == Default ? SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT : i == Go ? "Go" : i == Search ? "Search" : i == Send ? "Send" : i == Previous ? "Previous" : i == Next ? "Next" : i == Done ? "Done" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ImeAction) {
            return this.value == ((ImeAction) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m775toStringimpl(this.value);
    }
}
