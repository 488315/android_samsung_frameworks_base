package com.android.systemui.animation;

import android.graphics.fonts.Font;
import android.graphics.fonts.FontVariationAxis;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VarFontKey {
    public final int index;
    public final List sortedAxes;
    public final int sourceId;

    public VarFontKey(int i, int i2, List<FontVariationAxis> list) {
        this.sourceId = i;
        this.index = i2;
        this.sortedAxes = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VarFontKey)) {
            return false;
        }
        VarFontKey varFontKey = (VarFontKey) obj;
        return this.sourceId == varFontKey.sourceId && this.index == varFontKey.index && Intrinsics.areEqual(this.sortedAxes, varFontKey.sortedAxes);
    }

    public final int hashCode() {
        return this.sortedAxes.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.index, Integer.hashCode(this.sourceId) * 31, 31);
    }

    public final String toString() {
        return "VarFontKey(sourceId=" + this.sourceId + ", index=" + this.index + ", sortedAxes=" + this.sortedAxes + ")";
    }

    public VarFontKey(Font font, List<FontVariationAxis> list) {
        this(font.getSourceIdentifier(), font.getTtcIndex(), CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: com.android.systemui.animation.VarFontKey$special$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt__ComparisonsKt.compareValues(((FontVariationAxis) obj).getTag(), ((FontVariationAxis) obj2).getTag());
            }
        }));
    }
}
