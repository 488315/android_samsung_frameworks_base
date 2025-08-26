package com.samsung.android.sdk.routines.v3.data;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class TargetInstanceInfoExtra {
    private int isNegative;

    public TargetInstanceInfoExtra() {
        this(0, 1, null);
    }

    public static /* synthetic */ TargetInstanceInfoExtra copy$default(TargetInstanceInfoExtra targetInstanceInfoExtra, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = targetInstanceInfoExtra.isNegative;
        }
        return targetInstanceInfoExtra.copy(i);
    }

    public final int component1() {
        return this.isNegative;
    }

    public final TargetInstanceInfoExtra copy(int i) {
        return new TargetInstanceInfoExtra(i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TargetInstanceInfoExtra) && this.isNegative == ((TargetInstanceInfoExtra) obj).isNegative;
    }

    public int hashCode() {
        return Integer.hashCode(this.isNegative);
    }

    public final int isNegative() {
        return this.isNegative;
    }

    public final void setNegative(int i) {
        this.isNegative = i;
    }

    public String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("TargetInstanceInfoExtra(isNegative="), this.isNegative, ')');
    }

    public TargetInstanceInfoExtra(int i) {
        this.isNegative = i;
    }

    public /* synthetic */ TargetInstanceInfoExtra(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i);
    }
}
