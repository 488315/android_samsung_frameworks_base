package androidx.picker.model.viewdata;

import androidx.picker.model.AppData;
import androidx.picker.model.appdata.GroupAppData;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GroupTitleViewData implements AppSideViewData, ViewData {
    public final GroupAppData appData;
    public final String label;

    public GroupTitleViewData(GroupAppData groupAppData, String str) {
        this.appData = groupAppData;
        this.label = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GroupTitleViewData)) {
            return false;
        }
        GroupTitleViewData groupTitleViewData = (GroupTitleViewData) obj;
        return Intrinsics.areEqual(this.appData, groupTitleViewData.appData) && Intrinsics.areEqual(this.label, groupTitleViewData.label);
    }

    @Override // androidx.picker.model.viewdata.AppSideViewData
    public final AppData getAppData() {
        return this.appData;
    }

    @Override // androidx.picker.model.viewdata.ViewData
    public final Object getKey() {
        return this.appData.appInfo;
    }

    public final int hashCode() {
        return this.label.hashCode() + (this.appData.hashCode() * 31);
    }

    public final String toString() {
        return "GroupTitleViewData(appData=" + this.appData + ", label=" + this.label + ')';
    }

    public /* synthetic */ GroupTitleViewData(GroupAppData groupAppData, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(groupAppData, (i & 2) != 0 ? "" : str);
    }
}
