package com.android.systemui.statusbar.phone.knox.domain.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class KnoxStatusBarCustomTextModel {
    public final String customText;
    public final int textSize;
    public final int textStyle;
    public final int width;

    public KnoxStatusBarCustomTextModel() {
        this(null, 0, 0, 0, 15, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KnoxStatusBarCustomTextModel)) {
            return false;
        }
        KnoxStatusBarCustomTextModel knoxStatusBarCustomTextModel = (KnoxStatusBarCustomTextModel) obj;
        return Intrinsics.areEqual(this.customText, knoxStatusBarCustomTextModel.customText) && this.width == knoxStatusBarCustomTextModel.width && this.textStyle == knoxStatusBarCustomTextModel.textStyle && this.textSize == knoxStatusBarCustomTextModel.textSize;
    }

    public final int hashCode() {
        String str = this.customText;
        return Integer.hashCode(this.textSize) + ReorderTile$$ExternalSyntheticOutline0.m(this.textStyle, ReorderTile$$ExternalSyntheticOutline0.m(this.width, (str == null ? 0 : str.hashCode()) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("KnoxStatusBarCustomTextModel(customText=");
        sb.append(this.customText);
        sb.append(", width=");
        sb.append(this.width);
        sb.append(", textStyle=");
        sb.append(this.textStyle);
        sb.append(", textSize=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.textSize, ")", sb);
    }

    public KnoxStatusBarCustomTextModel(String str, int i, int i2, int i3) {
        this.customText = str;
        this.width = i;
        this.textStyle = i2;
        this.textSize = i3;
    }

    public /* synthetic */ KnoxStatusBarCustomTextModel(String str, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? null : str, (i4 & 2) != 0 ? 0 : i, (i4 & 4) != 0 ? 0 : i2, (i4 & 8) != 0 ? 0 : i3);
    }
}
