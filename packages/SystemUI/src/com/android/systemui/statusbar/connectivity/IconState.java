package com.android.systemui.statusbar.connectivity;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IconState {
    public final String contentDescription;
    public final int icon;
    public final boolean visible;

    public IconState(boolean z, int i, String str) {
        this.visible = z;
        this.icon = i;
        this.contentDescription = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IconState)) {
            return false;
        }
        IconState iconState = (IconState) obj;
        return this.visible == iconState.visible && this.icon == iconState.icon && Intrinsics.areEqual(this.contentDescription, iconState.contentDescription);
    }

    public final int hashCode() {
        return this.contentDescription.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.icon, Boolean.hashCode(this.visible) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[visible=");
        sb.append(this.visible);
        sb.append(",icon=");
        sb.append(this.icon);
        sb.append(",contentDescription=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.contentDescription, ']');
    }
}
