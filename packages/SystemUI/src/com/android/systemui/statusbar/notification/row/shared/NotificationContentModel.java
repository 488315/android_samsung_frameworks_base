package com.android.systemui.statusbar.notification.row.shared;

import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class NotificationContentModel {
    public final HeadsUpStatusBarModel headsUpStatusBarModel;
    public final SingleLineViewModel publicSingleLineViewModel;
    public final SingleLineViewModel singleLineViewModel;

    public NotificationContentModel(HeadsUpStatusBarModel headsUpStatusBarModel, SingleLineViewModel singleLineViewModel, SingleLineViewModel singleLineViewModel2) {
        this.headsUpStatusBarModel = headsUpStatusBarModel;
        this.singleLineViewModel = singleLineViewModel;
        this.publicSingleLineViewModel = singleLineViewModel2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationContentModel)) {
            return false;
        }
        NotificationContentModel notificationContentModel = (NotificationContentModel) obj;
        return Intrinsics.areEqual(this.headsUpStatusBarModel, notificationContentModel.headsUpStatusBarModel) && Intrinsics.areEqual(this.singleLineViewModel, notificationContentModel.singleLineViewModel) && Intrinsics.areEqual(this.publicSingleLineViewModel, notificationContentModel.publicSingleLineViewModel);
    }

    public final int hashCode() {
        int iHashCode = this.headsUpStatusBarModel.hashCode() * 31;
        SingleLineViewModel singleLineViewModel = this.singleLineViewModel;
        int iHashCode2 = (iHashCode + (singleLineViewModel == null ? 0 : singleLineViewModel.hashCode())) * 31;
        SingleLineViewModel singleLineViewModel2 = this.publicSingleLineViewModel;
        return iHashCode2 + (singleLineViewModel2 != null ? singleLineViewModel2.hashCode() : 0);
    }

    public final String toString() {
        return "NotificationContentModel(headsUpStatusBarModel=" + this.headsUpStatusBarModel + ", singleLineViewModel=" + this.singleLineViewModel + ", publicSingleLineViewModel=" + this.publicSingleLineViewModel + ")";
    }

    public /* synthetic */ NotificationContentModel(HeadsUpStatusBarModel headsUpStatusBarModel, SingleLineViewModel singleLineViewModel, SingleLineViewModel singleLineViewModel2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(headsUpStatusBarModel, (i & 2) != 0 ? null : singleLineViewModel, (i & 4) != 0 ? null : singleLineViewModel2);
    }
}
