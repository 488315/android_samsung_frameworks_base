package androidx.picker.model.viewdata;

import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.Selectable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AllAppsViewData implements ViewData, Selectable {
    public final SelectableItem selectableItem;

    public AllAppsViewData(SelectableItem selectableItem) {
        this.selectableItem = selectableItem;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AllAppsViewData) {
            return Intrinsics.areEqual(this.selectableItem, ((AllAppsViewData) obj).selectableItem);
        }
        return false;
    }

    @Override // androidx.picker.model.Selectable
    public final SelectableItem getSelectableItem() {
        return this.selectableItem;
    }

    public final int hashCode() {
        return this.selectableItem.hashCode();
    }

    public final String toString() {
        return "AllAppsViewData(selectableItem=" + this.selectableItem + ')';
    }

    @Override // androidx.picker.model.viewdata.ViewData
    public final Object getKey() {
        return this;
    }
}
