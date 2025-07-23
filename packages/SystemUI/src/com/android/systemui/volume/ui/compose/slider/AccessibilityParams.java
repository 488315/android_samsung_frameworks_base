package com.android.systemui.volume.ui.compose.slider;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AccessibilityParams {
    public final String contentDescription;
    public final String stateDescription;

    public AccessibilityParams(String str, String str2) {
        this.contentDescription = str;
        this.stateDescription = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccessibilityParams)) {
            return false;
        }
        AccessibilityParams accessibilityParams = (AccessibilityParams) obj;
        return Intrinsics.areEqual(this.contentDescription, accessibilityParams.contentDescription) && Intrinsics.areEqual(this.stateDescription, accessibilityParams.stateDescription);
    }

    public final int hashCode() {
        int hashCode = this.contentDescription.hashCode() * 31;
        String str = this.stateDescription;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AccessibilityParams(contentDescription=");
        sb.append(this.contentDescription);
        sb.append(", stateDescription=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.stateDescription, ")");
    }

    public /* synthetic */ AccessibilityParams(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2);
    }
}
