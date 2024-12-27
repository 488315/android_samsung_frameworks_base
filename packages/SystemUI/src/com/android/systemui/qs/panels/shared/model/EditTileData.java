package com.android.systemui.qs.panels.shared.model;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.Intrinsics;

public final class EditTileData {
    public final Text appName;
    public final Icon icon;
    public final Text label;
    public final TileSpec tileSpec;

    public EditTileData(TileSpec tileSpec, Icon icon, Text text, Text text2) {
        this.tileSpec = tileSpec;
        this.icon = icon;
        this.label = text;
        this.appName = text2;
        if ((tileSpec instanceof TileSpec.PlatformTileSpec) && text2 == null) {
            return;
        }
        if (!(tileSpec instanceof TileSpec.CustomTileSpec) || text2 == null) {
            throw new IllegalStateException(("tileSpec: " + tileSpec + " - appName: " + text2 + ". appName must be non-null for custom tiles and only for custom tiles.").toString());
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EditTileData)) {
            return false;
        }
        EditTileData editTileData = (EditTileData) obj;
        return Intrinsics.areEqual(this.tileSpec, editTileData.tileSpec) && Intrinsics.areEqual(this.icon, editTileData.icon) && Intrinsics.areEqual(this.label, editTileData.label) && Intrinsics.areEqual(this.appName, editTileData.appName);
    }

    public final int hashCode() {
        int hashCode = (this.label.hashCode() + ((this.icon.hashCode() + (this.tileSpec.hashCode() * 31)) * 31)) * 31;
        Text text = this.appName;
        return hashCode + (text == null ? 0 : text.hashCode());
    }

    public final String toString() {
        return "EditTileData(tileSpec=" + this.tileSpec + ", icon=" + this.icon + ", label=" + this.label + ", appName=" + this.appName + ")";
    }
}
