package com.android.systemui.statusbar.chips.ui.view;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysuiMeasureSpec {
    public final int specInt;

    public SysuiMeasureSpec(int i) {
        this.specInt = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SysuiMeasureSpec) && this.specInt == ((SysuiMeasureSpec) obj).specInt;
    }

    public final int hashCode() {
        return Integer.hashCode(this.specInt);
    }

    public final String toString() {
        return ReorderTile$$ExternalSyntheticOutline0.m(this.specInt, ")", new StringBuilder("SysuiMeasureSpec(specInt="));
    }
}
