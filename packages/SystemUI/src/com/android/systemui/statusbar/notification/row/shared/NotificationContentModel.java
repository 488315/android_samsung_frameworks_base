package com.android.systemui.statusbar.notification.row.shared;

import com.android.systemui.statusbar.notification.row.ui.viewmodel.SingleLineViewModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationContentModel {
    public final HeadsUpStatusBarModel headsUpStatusBarModel;
    public final SingleLineViewModel singleLineViewModel;

    public NotificationContentModel(HeadsUpStatusBarModel headsUpStatusBarModel, SingleLineViewModel singleLineViewModel) {
        this.headsUpStatusBarModel = headsUpStatusBarModel;
        this.singleLineViewModel = singleLineViewModel;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationContentModel)) {
            return false;
        }
        NotificationContentModel notificationContentModel = (NotificationContentModel) obj;
        return Intrinsics.areEqual(this.headsUpStatusBarModel, notificationContentModel.headsUpStatusBarModel) && Intrinsics.areEqual(this.singleLineViewModel, notificationContentModel.singleLineViewModel);
    }

    public final int hashCode() {
        int hashCode = this.headsUpStatusBarModel.hashCode() * 31;
        SingleLineViewModel singleLineViewModel = this.singleLineViewModel;
        return hashCode + (singleLineViewModel == null ? 0 : singleLineViewModel.hashCode());
    }

    public final String toString() {
        return "NotificationContentModel(headsUpStatusBarModel=" + this.headsUpStatusBarModel + ", singleLineViewModel=" + this.singleLineViewModel + ")";
    }

    public /* synthetic */ NotificationContentModel(HeadsUpStatusBarModel headsUpStatusBarModel, SingleLineViewModel singleLineViewModel, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(headsUpStatusBarModel, (i & 2) != 0 ? null : singleLineViewModel);
    }
}
