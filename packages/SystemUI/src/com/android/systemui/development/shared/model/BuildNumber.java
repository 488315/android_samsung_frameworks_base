package com.android.systemui.development.shared.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BuildNumber {
    public final String value;

    private /* synthetic */ BuildNumber(String str) {
        this.value = str;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BuildNumber m2549boximpl(String str) {
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
