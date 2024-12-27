package com.android.systemui.keyboard.shared.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BacklightModel {
    public final int level;
    public final int maxLevel;

    public BacklightModel(int i, int i2) {
        this.level = i;
        this.maxLevel = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BacklightModel)) {
            return false;
        }
        BacklightModel backlightModel = (BacklightModel) obj;
        return this.level == backlightModel.level && this.maxLevel == backlightModel.maxLevel;
    }

    public final int hashCode() {
        return Integer.hashCode(this.maxLevel) + (Integer.hashCode(this.level) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BacklightModel(level=");
        sb.append(this.level);
        sb.append(", maxLevel=");
        return Anchor$$ExternalSyntheticOutline0.m(this.maxLevel, ")", sb);
    }
}
