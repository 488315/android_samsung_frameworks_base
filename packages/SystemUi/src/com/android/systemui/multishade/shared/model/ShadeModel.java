package com.android.systemui.multishade.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShadeModel {
    public final float expansion;

    /* renamed from: id */
    public final ShadeId f325id;

    public ShadeModel(ShadeId shadeId, float f) {
        this.f325id = shadeId;
        this.expansion = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadeModel)) {
            return false;
        }
        ShadeModel shadeModel = (ShadeModel) obj;
        return this.f325id == shadeModel.f325id && Float.compare(this.expansion, shadeModel.expansion) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.expansion) + (this.f325id.hashCode() * 31);
    }

    public final String toString() {
        return "ShadeModel(id=" + this.f325id + ", expansion=" + this.expansion + ")";
    }

    public /* synthetic */ ShadeModel(ShadeId shadeId, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(shadeId, (i & 2) != 0 ? 0.0f : f);
    }
}
