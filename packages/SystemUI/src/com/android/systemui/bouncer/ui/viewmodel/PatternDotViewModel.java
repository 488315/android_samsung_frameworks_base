package com.android.systemui.bouncer.ui.viewmodel;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return Anchor$$ExternalSyntheticOutline0.m(this.y, ")", sb);
    }
}
