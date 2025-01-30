package androidx.picker.model.viewdata;

import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.AppData;
import androidx.picker.model.Selectable;
import androidx.picker.model.appdata.CategoryAppData;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CategoryViewData implements SearchableViewData, Selectable, AppSideViewData {
    public final CategoryAppData appData;
    public final List invisibleChildren;
    public final List searchable;
    public final SelectableItem selectableItem;

    public CategoryViewData(CategoryAppData categoryAppData, SelectableItem selectableItem, List<ViewData> list) {
        this.appData = categoryAppData;
        this.selectableItem = selectableItem;
        this.invisibleChildren = list;
        this.searchable = Collections.singletonList(categoryAppData.label);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CategoryViewData)) {
            return false;
        }
        CategoryViewData categoryViewData = (CategoryViewData) obj;
        return Intrinsics.areEqual(this.appData, categoryViewData.appData) && Intrinsics.areEqual(this.selectableItem, categoryViewData.selectableItem) && Intrinsics.areEqual(this.invisibleChildren, categoryViewData.invisibleChildren);
    }

    @Override // androidx.picker.model.viewdata.AppSideViewData
    public final AppData getAppData() {
        return this.appData;
    }

    @Override // androidx.picker.model.viewdata.ViewData
    public final Object getKey() {
        return this.appData.appInfo;
    }

    @Override // androidx.picker.model.viewdata.SearchableViewData
    public final List getSearchable() {
        return this.searchable;
    }

    @Override // androidx.picker.model.Selectable
    public final SelectableItem getSelectableItem() {
        return this.selectableItem;
    }

    public final int hashCode() {
        return this.invisibleChildren.hashCode() + ((this.selectableItem.hashCode() + (this.appData.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "CategoryViewData(appData=" + this.appData + ", selectableItem=" + this.selectableItem + ", invisibleChildren=" + this.invisibleChildren + ')';
    }
}
