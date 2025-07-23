package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RingerButtonUiModel {
    public static final Companion Companion = new Companion(null);
    public final int backgroundColor;
    public final int cornerRadius;
    public final int tintColor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public RingerButtonUiModel(int i, int i2, int i3) {
        this.tintColor = i;
        this.backgroundColor = i2;
        this.cornerRadius = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RingerButtonUiModel)) {
            return false;
        }
        RingerButtonUiModel ringerButtonUiModel = (RingerButtonUiModel) obj;
        return this.tintColor == ringerButtonUiModel.tintColor && this.backgroundColor == ringerButtonUiModel.backgroundColor && this.cornerRadius == ringerButtonUiModel.cornerRadius;
    }

    public final int hashCode() {
        return Integer.hashCode(this.cornerRadius) + ReorderTile$$ExternalSyntheticOutline0.m(this.backgroundColor, Integer.hashCode(this.tintColor) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RingerButtonUiModel(tintColor=");
        sb.append(this.tintColor);
        sb.append(", backgroundColor=");
        sb.append(this.backgroundColor);
        sb.append(", cornerRadius=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.cornerRadius, ")", sb);
    }
}
