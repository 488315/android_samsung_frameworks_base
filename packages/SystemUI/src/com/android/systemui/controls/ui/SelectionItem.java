package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SelectionItem {
    public final CharSequence appName;
    public final ComponentName componentName;
    public final Drawable icon;
    public final ComponentName panelComponentName;
    public final CharSequence structure;
    public final int uid;

    public SelectionItem(CharSequence charSequence, CharSequence charSequence2, Drawable drawable, ComponentName componentName, int i, ComponentName componentName2) {
        this.appName = charSequence;
        this.structure = charSequence2;
        this.icon = drawable;
        this.componentName = componentName;
        this.uid = i;
        this.panelComponentName = componentName2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SelectionItem)) {
            return false;
        }
        SelectionItem selectionItem = (SelectionItem) obj;
        return Intrinsics.areEqual(this.appName, selectionItem.appName) && Intrinsics.areEqual(this.structure, selectionItem.structure) && Intrinsics.areEqual(this.icon, selectionItem.icon) && Intrinsics.areEqual(this.componentName, selectionItem.componentName) && this.uid == selectionItem.uid && Intrinsics.areEqual(this.panelComponentName, selectionItem.panelComponentName);
    }

    public final int hashCode() {
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.uid, (this.componentName.hashCode() + ((this.icon.hashCode() + ControlInfo$$ExternalSyntheticOutline0.m(this.appName.hashCode() * 31, 31, this.structure)) * 31)) * 31, 31);
        ComponentName componentName = this.panelComponentName;
        return m + (componentName == null ? 0 : componentName.hashCode());
    }

    public final String toString() {
        CharSequence charSequence = this.appName;
        CharSequence charSequence2 = this.structure;
        return "SelectionItem(appName=" + ((Object) charSequence) + ", structure=" + ((Object) charSequence2) + ", icon=" + this.icon + ", componentName=" + this.componentName + ", uid=" + this.uid + ", panelComponentName=" + this.panelComponentName + ")";
    }
}
