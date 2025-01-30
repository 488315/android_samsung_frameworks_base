package androidx.picker.model.viewdata;

import android.graphics.drawable.Drawable;
import androidx.picker.features.observable.ObservableProperty;
import androidx.picker.features.observable.StringState;
import androidx.picker.features.observable.UpdateMutableState;
import androidx.picker.features.observable.UpdateObservableProperty;
import androidx.picker.loader.AppIconFlow;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.AppData;
import androidx.picker.model.AppInfo;
import androidx.picker.model.AppInfoData;
import androidx.picker.model.Selectable;
import androidx.picker.model.SpanData;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppInfoViewData implements SearchableViewData, AppInfoData, AppSideViewData, SpanData, Selectable {
    public final AppInfoData appInfoData;
    public final UpdateObservableProperty dimmedItem;
    public final ObservableProperty highlightText;
    public final AppIconFlow iconFlow;
    public final Function1 onActionClick;
    public final SelectableItem selectableItem;
    public final int spanCount;

    /* JADX WARN: Multi-variable type inference failed */
    public AppInfoViewData(final AppInfoData appInfoData, AppIconFlow appIconFlow, SelectableItem selectableItem, int i, Function1 function1) {
        this.appInfoData = appInfoData;
        this.iconFlow = appIconFlow;
        this.selectableItem = selectableItem;
        this.spanCount = i;
        this.onActionClick = function1;
        int i2 = 2;
        this.dimmedItem = new UpdateObservableProperty(new UpdateMutableState(appInfoData) { // from class: androidx.picker.model.viewdata.AppInfoViewData$dimmedItem$1
            @Override // androidx.picker.features.observable.MutableState
            public final Object getValue() {
                return Boolean.valueOf(((AppInfoData) this.base).getDimmed());
            }

            @Override // androidx.picker.features.observable.MutableState
            public final void setValue(Object obj) {
                ((AppInfoData) this.base).setDimmed(((Boolean) obj).booleanValue());
            }
        }, null, i2, 0 == true ? 1 : 0);
        this.highlightText = new ObservableProperty(new StringState(""), 0 == true ? 1 : 0, i2, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppInfoViewData)) {
            return false;
        }
        AppInfoViewData appInfoViewData = (AppInfoViewData) obj;
        return Intrinsics.areEqual(this.appInfoData, appInfoViewData.appInfoData) && Intrinsics.areEqual(this.iconFlow, appInfoViewData.iconFlow) && Intrinsics.areEqual(this.selectableItem, appInfoViewData.selectableItem) && this.spanCount == appInfoViewData.spanCount && Intrinsics.areEqual(this.onActionClick, appInfoViewData.onActionClick);
    }

    @Override // androidx.picker.model.AppInfoData
    public final Drawable getActionIcon() {
        return this.appInfoData.getActionIcon();
    }

    @Override // androidx.picker.model.viewdata.AppSideViewData
    public final AppData getAppData() {
        return this.appInfoData;
    }

    @Override // androidx.picker.model.AppData
    public final AppInfo getAppInfo() {
        return this.appInfoData.getAppInfo();
    }

    @Override // androidx.picker.model.AppInfoData
    public final boolean getDimmed() {
        return this.appInfoData.getDimmed();
    }

    @Override // androidx.picker.model.AppInfoData
    public final String getExtraLabel() {
        return this.appInfoData.getExtraLabel();
    }

    @Override // androidx.picker.model.AppInfoData
    public final Drawable getIcon() {
        return this.appInfoData.getIcon();
    }

    @Override // androidx.picker.model.AppInfoData
    public final int getItemType() {
        return this.appInfoData.getItemType();
    }

    @Override // androidx.picker.model.viewdata.ViewData
    public final Object getKey() {
        return this.appInfoData.getAppInfo();
    }

    @Override // androidx.picker.model.AppInfoData
    public final String getLabel() {
        return this.appInfoData.getLabel();
    }

    @Override // androidx.picker.model.viewdata.SearchableViewData
    public final List getSearchable() {
        return Collections.singletonList(this.appInfoData.getLabel());
    }

    @Override // androidx.picker.model.Selectable
    public final SelectableItem getSelectableItem() {
        return this.selectableItem;
    }

    @Override // androidx.picker.model.AppInfoData
    public final boolean getSelected() {
        return this.appInfoData.getSelected();
    }

    @Override // androidx.picker.model.SpanData
    public final int getSpanCount() {
        return this.spanCount;
    }

    @Override // androidx.picker.model.AppInfoData
    public final Drawable getSubIcon() {
        return this.appInfoData.getSubIcon();
    }

    @Override // androidx.picker.model.AppInfoData
    public final String getSubLabel() {
        return this.appInfoData.getSubLabel();
    }

    public final int hashCode() {
        int hashCode = (this.iconFlow.hashCode() + (this.appInfoData.hashCode() * 31)) * 31;
        SelectableItem selectableItem = this.selectableItem;
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.spanCount, (hashCode + (selectableItem == null ? 0 : selectableItem.hashCode())) * 31, 31);
        Function1 function1 = this.onActionClick;
        return m42m + (function1 != null ? function1.hashCode() : 0);
    }

    @Override // androidx.picker.model.AppInfoData
    public final boolean isValueInSubLabel() {
        return this.appInfoData.isValueInSubLabel();
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setDimmed(boolean z) {
        this.appInfoData.setDimmed(z);
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setIcon(Drawable drawable) {
        this.appInfoData.setIcon(drawable);
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setLabel(String str) {
        this.appInfoData.setLabel(str);
    }

    @Override // androidx.picker.model.AppInfoData
    public final void setSelected(boolean z) {
        this.appInfoData.setSelected(z);
    }

    public final String toString() {
        return "AppInfoViewData(appInfoData=" + this.appInfoData + ", iconFlow=" + this.iconFlow + ", selectableItem=" + this.selectableItem + ", spanCount=" + this.spanCount + ", onActionClick=" + this.onActionClick + ')';
    }

    public /* synthetic */ AppInfoViewData(AppInfoData appInfoData, AppIconFlow appIconFlow, SelectableItem selectableItem, int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(appInfoData, appIconFlow, (i2 & 4) != 0 ? null : selectableItem, (i2 & 8) != 0 ? 1 : i, (i2 & 16) != 0 ? null : function1);
    }
}
