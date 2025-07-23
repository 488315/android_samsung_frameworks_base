package androidx.compose.ui.text.font;

import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontSynthesis {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Weight = 1;
    public static final int Style = 2;
    public static final int All = CustomDeviceManager.QUICK_PANEL_ALL;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static final /* synthetic */ FontSynthesis m766boximpl(int i) {
        return new FontSynthesis(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m767toStringimpl(int i) {
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
        return m767toStringimpl(this.value);
    }
}
