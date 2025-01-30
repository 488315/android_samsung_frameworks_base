package com.android.systemui.keyboard.shared.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.maxLevel, ")");
    }
}
