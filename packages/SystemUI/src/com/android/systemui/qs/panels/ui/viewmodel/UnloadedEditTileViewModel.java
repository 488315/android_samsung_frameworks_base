package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class UnloadedEditTileViewModel {
    public final Text appName;
    public final Set availableEditActions;
    public final TileCategory category;
    public final Icon icon;
    public final boolean isCurrent;
    public final Text label;
    public final TileSpec tileSpec;

    public UnloadedEditTileViewModel(TileSpec tileSpec, Icon icon, Text text, Text text2, boolean z, Set<? extends AvailableEditActions> set, TileCategory tileCategory) {
        this.tileSpec = tileSpec;
        this.icon = icon;
        this.label = text;
        this.appName = text2;
        this.isCurrent = z;
        this.availableEditActions = set;
        this.category = tileCategory;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnloadedEditTileViewModel)) {
            return false;
        }
        UnloadedEditTileViewModel unloadedEditTileViewModel = (UnloadedEditTileViewModel) obj;
        return Intrinsics.areEqual(this.tileSpec, unloadedEditTileViewModel.tileSpec) && Intrinsics.areEqual(this.icon, unloadedEditTileViewModel.icon) && Intrinsics.areEqual(this.label, unloadedEditTileViewModel.label) && Intrinsics.areEqual(this.appName, unloadedEditTileViewModel.appName) && this.isCurrent == unloadedEditTileViewModel.isCurrent && Intrinsics.areEqual(this.availableEditActions, unloadedEditTileViewModel.availableEditActions) && this.category == unloadedEditTileViewModel.category;
    }

    public final int hashCode() {
        int hashCode = (this.label.hashCode() + ((this.icon.hashCode() + (this.tileSpec.hashCode() * 31)) * 31)) * 31;
        Text text = this.appName;
        return this.category.hashCode() + ((this.availableEditActions.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode + (text == null ? 0 : text.hashCode())) * 31, 31, this.isCurrent)) * 31);
    }

    public final String toString() {
        return "UnloadedEditTileViewModel(tileSpec=" + this.tileSpec + ", icon=" + this.icon + ", label=" + this.label + ", appName=" + this.appName + ", isCurrent=" + this.isCurrent + ", availableEditActions=" + this.availableEditActions + ", category=" + this.category + ")";
    }
}
