package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.AnnotatedString;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.CategoryAndName;
import com.android.systemui.qs.shared.model.TileCategory;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class EditTileViewModel implements CategoryAndName {
    public final AnnotatedString appName;
    public final Set availableEditActions;
    public final TileCategory category;
    public final Icon icon;
    public final boolean isCurrent;
    public final AnnotatedString label;
    public final TileSpec tileSpec;

    public EditTileViewModel(TileSpec tileSpec, Icon icon, AnnotatedString annotatedString, AnnotatedString annotatedString2, boolean z, Set<? extends AvailableEditActions> set, TileCategory tileCategory) {
        this.tileSpec = tileSpec;
        this.icon = icon;
        this.label = annotatedString;
        this.appName = annotatedString2;
        this.isCurrent = z;
        this.availableEditActions = set;
        this.category = tileCategory;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EditTileViewModel)) {
            return false;
        }
        EditTileViewModel editTileViewModel = (EditTileViewModel) obj;
        return Intrinsics.areEqual(this.tileSpec, editTileViewModel.tileSpec) && Intrinsics.areEqual(this.icon, editTileViewModel.icon) && Intrinsics.areEqual(this.label, editTileViewModel.label) && Intrinsics.areEqual(this.appName, editTileViewModel.appName) && this.isCurrent == editTileViewModel.isCurrent && Intrinsics.areEqual(this.availableEditActions, editTileViewModel.availableEditActions) && this.category == editTileViewModel.category;
    }

    @Override // com.android.systemui.qs.shared.model.CategoryAndName
    public final TileCategory getCategory() {
        return this.category;
    }

    @Override // com.android.systemui.qs.shared.model.CategoryAndName
    public final String getName() {
        return this.label.text;
    }

    public final int hashCode() {
        int iHashCode = (this.label.hashCode() + ((this.icon.hashCode() + (this.tileSpec.hashCode() * 31)) * 31)) * 31;
        AnnotatedString annotatedString = this.appName;
        return this.category.hashCode() + ((this.availableEditActions.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((iHashCode + (annotatedString == null ? 0 : annotatedString.hashCode())) * 31, 31, this.isCurrent)) * 31);
    }

    public final String toString() {
        return "EditTileViewModel(tileSpec=" + this.tileSpec + ", icon=" + this.icon + ", label=" + ((Object) this.label) + ", appName=" + ((Object) this.appName) + ", isCurrent=" + this.isCurrent + ", availableEditActions=" + this.availableEditActions + ", category=" + this.category + ")";
    }
}
