package com.android.systemui.accessibility;

import android.util.Size;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class WindowMagnificationFrameSpec {
    public static final Companion Companion = new Companion(null);
    public final int index;
    public final Size size;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public WindowMagnificationFrameSpec(int i, Size size) {
        this.index = i;
        this.size = size;
    }

    public static final WindowMagnificationFrameSpec deserialize(String str) {
        Companion.getClass();
        int iIndexOf$default = StringsKt__StringsKt.indexOf$default(str, ',', 0, 6);
        if (iIndexOf$default < 0) {
            throw new NumberFormatException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid WindowMagnificationFrameSpec: \"", str, "\""));
        }
        try {
            return new WindowMagnificationFrameSpec(Integer.parseInt(str.substring(0, iIndexOf$default)), Size.parseSize(str.substring(iIndexOf$default + 1)));
        } catch (NumberFormatException unused) {
            throw new NumberFormatException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Invalid WindowMagnificationFrameSpec: \"", str, "\""));
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WindowMagnificationFrameSpec)) {
            return false;
        }
        WindowMagnificationFrameSpec windowMagnificationFrameSpec = (WindowMagnificationFrameSpec) obj;
        return this.index == windowMagnificationFrameSpec.index && Intrinsics.areEqual(this.size, windowMagnificationFrameSpec.size);
    }

    public final int hashCode() {
        return this.size.hashCode() + (Integer.hashCode(this.index) * 31);
    }

    public final String toString() {
        return "WindowMagnificationFrameSpec(index=" + this.index + ", size=" + this.size + ")";
    }
}
