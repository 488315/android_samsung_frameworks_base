package com.android.systemui.accessibility.hearingaid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ToolItem {
    public final boolean isCustomIcon;
    public final Drawable toolIcon;
    public final Intent toolIntent;
    public final String toolName;

    public ToolItem(String str, Drawable drawable, Intent intent, boolean z) {
        this.toolName = str;
        this.toolIcon = drawable;
        this.toolIntent = intent;
        this.isCustomIcon = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ToolItem)) {
            return false;
        }
        ToolItem toolItem = (ToolItem) obj;
        return Intrinsics.areEqual(this.toolName, toolItem.toolName) && Intrinsics.areEqual(this.toolIcon, toolItem.toolIcon) && Intrinsics.areEqual(this.toolIntent, toolItem.toolIntent) && this.isCustomIcon == toolItem.isCustomIcon;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isCustomIcon) + ((this.toolIntent.hashCode() + ((this.toolIcon.hashCode() + (this.toolName.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "ToolItem(toolName=" + this.toolName + ", toolIcon=" + this.toolIcon + ", toolIntent=" + this.toolIntent + ", isCustomIcon=" + this.isCustomIcon + ")";
    }

    public /* synthetic */ ToolItem(String str, Drawable drawable, Intent intent, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, drawable, intent, z);
    }
}
