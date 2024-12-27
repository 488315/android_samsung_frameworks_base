package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.controls.ui.view.ControlsSpinner;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecSelectionItem extends ControlsSpinner.SelectionItem {
    public final CharSequence appName;
    public final ComponentName componentName;
    public final Drawable icon;
    public final boolean isPanel;
    public final ComponentName panelComponentName;
    public final int uid;

    public SecSelectionItem(CharSequence charSequence, Drawable drawable, ComponentName componentName, int i, ComponentName componentName2) {
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
        if (!(obj instanceof SecSelectionItem)) {
            return false;
        }
        SecSelectionItem secSelectionItem = (SecSelectionItem) obj;
        return Intrinsics.areEqual(this.appName, secSelectionItem.appName) && Intrinsics.areEqual(this.icon, secSelectionItem.icon) && Intrinsics.areEqual(this.componentName, secSelectionItem.componentName) && this.uid == secSelectionItem.uid && Intrinsics.areEqual(this.panelComponentName, secSelectionItem.panelComponentName);
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
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.uid, (this.componentName.hashCode() + ((this.icon.hashCode() + (this.appName.hashCode() * 31)) * 31)) * 31, 31);
        ComponentName componentName = this.panelComponentName;
        return m + (componentName == null ? 0 : componentName.hashCode());
    }

    public final String toString() {
        ComponentName componentName = this.componentName;
        CharSequence charSequence = this.appName;
        return "SelectionItem{" + componentName + ", isPanel = " + this.isPanel + ", appName = " + ((Object) charSequence) + ", panelComponent = " + this.panelComponentName + "}";
    }
}
