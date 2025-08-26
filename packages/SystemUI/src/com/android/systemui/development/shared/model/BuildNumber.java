package com.android.systemui.development.shared.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class BuildNumber {
    public final String value;

    private /* synthetic */ BuildNumber(String str) {
        this.value = str;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BuildNumber m2566boximpl(String str) {
        return new BuildNumber(str);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof BuildNumber) {
            return Intrinsics.areEqual(this.value, ((BuildNumber) obj).value);
        }
        return false;
    }

    public final int hashCode() {
        return this.value.hashCode();
    }

    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("BuildNumber(value="), this.value, ")");
    }
}
