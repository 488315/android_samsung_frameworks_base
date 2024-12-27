package com.android.systemui.statusbar.connectivity;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return this.contentDescription.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.icon, Boolean.hashCode(this.visible) * 31, 31);
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
