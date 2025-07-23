package com.android.systemui.communal.data.db;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalWidgetItem {
    public final String componentName;
    public final long itemId;
    public final int spanY;
    public final int spanYNew;
    public final long uid;
    public final int userSerialNumber;
    public final int widgetId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CommunalWidgetItem(long j, int i, String str, long j2, int i2, int i3, int i4) {
        this.uid = j;
        this.widgetId = i;
        this.componentName = str;
        this.itemId = j2;
        this.userSerialNumber = i2;
        this.spanY = i3;
        this.spanYNew = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommunalWidgetItem)) {
            return false;
        }
        CommunalWidgetItem communalWidgetItem = (CommunalWidgetItem) obj;
        return this.uid == communalWidgetItem.uid && this.widgetId == communalWidgetItem.widgetId && Intrinsics.areEqual(this.componentName, communalWidgetItem.componentName) && this.itemId == communalWidgetItem.itemId && this.userSerialNumber == communalWidgetItem.userSerialNumber && this.spanY == communalWidgetItem.spanY && this.spanYNew == communalWidgetItem.spanYNew;
    }

    public final int hashCode() {
        return Integer.hashCode(this.spanYNew) + ReorderTile$$ExternalSyntheticOutline0.m(this.spanY, ReorderTile$$ExternalSyntheticOutline0.m(this.userSerialNumber, MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.widgetId, Long.hashCode(this.uid) * 31, 31), 31, this.componentName), 31, this.itemId), 31), 31);
    }

    public final String toString() {
        return "CommunalWidgetItem(uid=" + this.uid + ", widgetId=" + this.widgetId + ", componentName=" + this.componentName + ", itemId=" + this.itemId + ", userSerialNumber=" + this.userSerialNumber + ", spanY=" + this.spanY + ", spanYNew=" + this.spanYNew + ")";
    }
}
