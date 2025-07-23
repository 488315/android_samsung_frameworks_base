package com.android.systemui.keyboard.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        return ReorderTile$$ExternalSyntheticOutline0.m(this.maxLevel, ")", sb);
    }
}
