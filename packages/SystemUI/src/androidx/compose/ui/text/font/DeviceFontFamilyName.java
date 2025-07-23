package androidx.compose.ui.text.font;

import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DeviceFontFamilyName {
    public final String name;

    /* renamed from: constructor-impl, reason: not valid java name */
    public static void m759constructorimpl(String str) {
        if (str.length() > 0) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("name may not be empty");
    }

    public final boolean equals(Object obj) {
        if (obj instanceof DeviceFontFamilyName) {
            return Intrinsics.areEqual(this.name, ((DeviceFontFamilyName) obj).name);
        }
        return false;
    }

    public final int hashCode() {
        return this.name.hashCode();
    }

    public final String toString() {
        return "DeviceFontFamilyName(name=" + this.name + ')';
    }
}
