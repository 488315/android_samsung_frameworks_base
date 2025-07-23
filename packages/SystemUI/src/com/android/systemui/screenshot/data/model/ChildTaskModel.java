package com.android.systemui.screenshot.data.model;

import android.graphics.Rect;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ChildTaskModel {
    public final Rect bounds;
    public final int id;
    public final String name;
    public final int userId;

    public ChildTaskModel(int i, String str, Rect rect, int i2) {
        this.id = i;
        this.name = str;
        this.bounds = rect;
        this.userId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChildTaskModel)) {
            return false;
        }
        ChildTaskModel childTaskModel = (ChildTaskModel) obj;
        return this.id == childTaskModel.id && Intrinsics.areEqual(this.name, childTaskModel.name) && Intrinsics.areEqual(this.bounds, childTaskModel.bounds) && this.userId == childTaskModel.userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId) + ((this.bounds.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.id) * 31, 31, this.name)) * 31);
    }

    public final String toString() {
        Rect rect = this.bounds;
        StringBuilder sb = new StringBuilder("ChildTaskModel(id=");
        sb.append(this.id);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", userId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
    }
}
