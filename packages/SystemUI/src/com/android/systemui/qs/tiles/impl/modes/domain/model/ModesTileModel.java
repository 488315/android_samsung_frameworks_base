package com.android.systemui.qs.tiles.impl.modes.domain.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ModesTileModel {
    public final List activeModes;
    public final Icon.Loaded icon;
    public final boolean isActivated;

    public ModesTileModel(boolean z, List<String> list, Icon.Loaded loaded) {
        this.isActivated = z;
        this.activeModes = list;
        this.icon = loaded;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ModesTileModel)) {
            return false;
        }
        ModesTileModel modesTileModel = (ModesTileModel) obj;
        return this.isActivated == modesTileModel.isActivated && Intrinsics.areEqual(this.activeModes, modesTileModel.activeModes) && Intrinsics.areEqual(this.icon, modesTileModel.icon);
    }

    public final int hashCode() {
        return this.icon.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.activeModes, Boolean.hashCode(this.isActivated) * 31, 31);
    }

    public final String toString() {
        return "ModesTileModel(isActivated=" + this.isActivated + ", activeModes=" + this.activeModes + ", icon=" + this.icon + ")";
    }
}
