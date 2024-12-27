package com.android.systemui.qs.tiles.impl.custom.domain.entity;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.service.quicksettings.Tile;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class CustomTileDataModel {
    public final int callingAppUid;
    public final ComponentName componentName;
    public final Icon defaultTileIcon;
    public final CharSequence defaultTileLabel;
    public final boolean hasPendingBind;
    public final boolean isToggleable;
    public final Tile tile;
    public final UserHandle user;

    public CustomTileDataModel(UserHandle userHandle, ComponentName componentName, Tile tile, boolean z, int i, boolean z2, CharSequence charSequence, Icon icon) {
        this.user = userHandle;
        this.componentName = componentName;
        this.tile = tile;
        this.isToggleable = z;
        this.callingAppUid = i;
        this.hasPendingBind = z2;
        this.defaultTileLabel = charSequence;
        this.defaultTileIcon = icon;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomTileDataModel)) {
            return false;
        }
        CustomTileDataModel customTileDataModel = (CustomTileDataModel) obj;
        return Intrinsics.areEqual(this.user, customTileDataModel.user) && Intrinsics.areEqual(this.componentName, customTileDataModel.componentName) && Intrinsics.areEqual(this.tile, customTileDataModel.tile) && this.isToggleable == customTileDataModel.isToggleable && this.callingAppUid == customTileDataModel.callingAppUid && this.hasPendingBind == customTileDataModel.hasPendingBind && Intrinsics.areEqual(this.defaultTileLabel, customTileDataModel.defaultTileLabel) && Intrinsics.areEqual(this.defaultTileIcon, customTileDataModel.defaultTileIcon);
    }

    public final int hashCode() {
        return this.defaultTileIcon.hashCode() + ControlInfo$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.callingAppUid, TransitionData$$ExternalSyntheticOutline0.m((this.tile.hashCode() + ((this.componentName.hashCode() + (this.user.hashCode() * 31)) * 31)) * 31, 31, this.isToggleable), 31), 31, this.hasPendingBind), 31, this.defaultTileLabel);
    }

    public final String toString() {
        UserHandle userHandle = this.user;
        ComponentName componentName = this.componentName;
        Tile tile = this.tile;
        CharSequence charSequence = this.defaultTileLabel;
        return "CustomTileDataModel(user=" + userHandle + ", componentName=" + componentName + ", tile=" + tile + ", isToggleable=" + this.isToggleable + ", callingAppUid=" + this.callingAppUid + ", hasPendingBind=" + this.hasPendingBind + ", defaultTileLabel=" + ((Object) charSequence) + ", defaultTileIcon=" + this.defaultTileIcon + ")";
    }
}
