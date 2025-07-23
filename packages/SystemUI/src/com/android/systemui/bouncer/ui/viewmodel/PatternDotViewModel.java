package com.android.systemui.bouncer.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PatternDotViewModel {
    public final int x;
    public final int y;

    public PatternDotViewModel(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PatternDotViewModel)) {
            return false;
        }
        PatternDotViewModel patternDotViewModel = (PatternDotViewModel) obj;
        return this.x == patternDotViewModel.x && this.y == patternDotViewModel.y;
    }

    public final int hashCode() {
        return Integer.hashCode(this.y) + (Integer.hashCode(this.x) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PatternDotViewModel(x=");
        sb.append(this.x);
        sb.append(", y=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.y, ")", sb);
    }
}
