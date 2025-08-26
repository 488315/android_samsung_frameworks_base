package androidx.compose.ui.text.font;

import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class FontSynthesis {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Weight = 1;
    public static final int Style = 2;
    public static final int All = CustomDeviceManager.QUICK_PANEL_ALL;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ FontSynthesis(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ FontSynthesis m768boximpl(int i) {
        return new FontSynthesis(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m769toStringimpl(int i) {
        return i == 0 ? "None" : i == Weight ? "Weight" : i == Style ? "Style" : i == All ? "All" : "Invalid";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof FontSynthesis) {
            return this.value == ((FontSynthesis) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m769toStringimpl(this.value);
    }
}
