package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.controls.ui.view.ControlsSpinner;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsSelectionItem extends ControlsSpinner.SelectionItem {
    public final CharSequence appName;
    public final ComponentName componentName;
    public final Drawable icon;
    public final boolean isPanel;
    public final ComponentName panelComponentName;
    public final int uid;

    public ControlsSelectionItem(CharSequence charSequence, Drawable drawable, ComponentName componentName, int i, ComponentName componentName2) {
        super(charSequence, drawable, componentName);
        this.appName = charSequence;
        this.icon = drawable;
        this.componentName = componentName;
        this.uid = i;
        this.panelComponentName = componentName2;
        this.isPanel = componentName2 != null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlsSelectionItem)) {
            return false;
        }
        ControlsSelectionItem controlsSelectionItem = (ControlsSelectionItem) obj;
        return Intrinsics.areEqual(this.appName, controlsSelectionItem.appName) && Intrinsics.areEqual(this.icon, controlsSelectionItem.icon) && Intrinsics.areEqual(this.componentName, controlsSelectionItem.componentName) && this.uid == controlsSelectionItem.uid && Intrinsics.areEqual(this.panelComponentName, controlsSelectionItem.panelComponentName);
    }

    @Override // com.android.systemui.controls.ui.view.ControlsSpinner.SelectionItem
    public final CharSequence getAppName() {
        return this.appName;
    }

    @Override // com.android.systemui.controls.ui.view.ControlsSpinner.SelectionItem
    public final ComponentName getComponentName() {
        return this.componentName;
    }

    @Override // com.android.systemui.controls.ui.view.ControlsSpinner.SelectionItem
    public final Drawable getIcon() {
        return this.icon;
    }

    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.uid, (this.componentName.hashCode() + ((this.icon.hashCode() + (this.appName.hashCode() * 31)) * 31)) * 31, 31);
        ComponentName componentName = this.panelComponentName;
        return m42m + (componentName == null ? 0 : componentName.hashCode());
    }

    public final String toString() {
        return "SelectionItem{" + this.componentName + ", isPanel = " + this.isPanel + ", appName = " + ((Object) this.appName) + ", panelComponent = " + this.panelComponentName + "}";
    }
}
