package com.android.wm.shell.compatui.api;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class CompatUILayout {
    public final int layoutParamFlags;
    public final Function4 positionFactory;
    public final Function4 viewBinder;
    public final Function3 viewBuilder;
    public final Function0 viewReleaser;
    public final int zOrder;

    public CompatUILayout(int i, int i2, Function3 function3, Function4 function4, Function4 function42, Function0 function0) {
        this.zOrder = i;
        this.layoutParamFlags = i2;
        this.viewBuilder = function3;
        this.viewBinder = function4;
        this.positionFactory = function42;
        this.viewReleaser = function0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompatUILayout)) {
            return false;
        }
        CompatUILayout compatUILayout = (CompatUILayout) obj;
        return this.zOrder == compatUILayout.zOrder && this.layoutParamFlags == compatUILayout.layoutParamFlags && Intrinsics.areEqual(this.viewBuilder, compatUILayout.viewBuilder) && Intrinsics.areEqual(this.viewBinder, compatUILayout.viewBinder) && Intrinsics.areEqual(this.positionFactory, compatUILayout.positionFactory) && Intrinsics.areEqual(this.viewReleaser, compatUILayout.viewReleaser);
    }

    public final int hashCode() {
        return this.viewReleaser.hashCode() + ((this.positionFactory.hashCode() + ((this.viewBinder.hashCode() + ((this.viewBuilder.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.layoutParamFlags, Integer.hashCode(this.zOrder) * 31, 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "CompatUILayout(zOrder=" + this.zOrder + ", layoutParamFlags=" + this.layoutParamFlags + ", viewBuilder=" + this.viewBuilder + ", viewBinder=" + this.viewBinder + ", positionFactory=" + this.positionFactory + ", viewReleaser=" + this.viewReleaser + ")";
    }

    public /* synthetic */ CompatUILayout(int i, int i2, Function3 function3, Function4 function4, Function4 function42, Function0 function0, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 40 : i2, function3, (i3 & 8) != 0 ? new CompatUILayout$$ExternalSyntheticLambda0() : function4, function42, (i3 & 32) != 0 ? new CompatUILayout$$ExternalSyntheticLambda1() : function0);
    }
}
