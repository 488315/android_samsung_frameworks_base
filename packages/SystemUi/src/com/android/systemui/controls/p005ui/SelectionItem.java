package com.android.systemui.controls.p005ui;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.uid, (this.componentName.hashCode() + ((this.icon.hashCode() + ((this.structure.hashCode() + (this.appName.hashCode() * 31)) * 31)) * 31)) * 31, 31);
        ComponentName componentName = this.panelComponentName;
        return m42m + (componentName == null ? 0 : componentName.hashCode());
    }

    public final String toString() {
        return "SelectionItem(appName=" + ((Object) this.appName) + ", structure=" + ((Object) this.structure) + ", icon=" + this.icon + ", componentName=" + this.componentName + ", uid=" + this.uid + ", panelComponentName=" + this.panelComponentName + ")";
    }
}
