package com.android.systemui.statusbar.connectivity;

import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public final int hashCode() {
        boolean z = this.visible;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return this.contentDescription.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.icon, r0 * 31, 31);
    }

    public final String toString() {
        return "[visible=" + this.visible + ",icon=" + this.icon + ",contentDescription=" + this.contentDescription + ']';
    }
}
