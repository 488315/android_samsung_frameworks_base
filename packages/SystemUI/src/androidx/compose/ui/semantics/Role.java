package androidx.compose.ui.semantics;

import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Role {
    public final int value;
    public static final Companion Companion = new Companion(null);
    public static final int Checkbox = 1;
    public static final int Switch = 2;
    public static final int RadioButton = 3;
    public static final int Tab = 4;
    public static final int Image = 5;
    public static final int DropdownList = 6;
    public static final int ValuePicker = 7;
    public static final int Carousel = 8;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ Role(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Role m713boximpl(int i) {
        return new Role(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m714toStringimpl(int i) {
        return i == 0 ? "Button" : i == Checkbox ? "Checkbox" : i == Switch ? "Switch" : i == RadioButton ? "RadioButton" : i == Tab ? "Tab" : i == Image ? SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_IMAGE : i == DropdownList ? "DropdownList" : i == ValuePicker ? "Picker" : i == Carousel ? "Carousel" : C2paManifestList.UNKNOWN_VALUE;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Role) {
            return this.value == ((Role) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m714toStringimpl(this.value);
    }
}
