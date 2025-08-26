package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import com.android.systemui.statusbar.notification.shared.NotificationViewFlipperPausing;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class NotificationViewFlipperFactory implements NotifRemoteViewsFactory {
    public final NotificationViewFlipperViewModel viewModel;

    public NotificationViewFlipperFactory(NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        this.viewModel = notificationViewFlipperViewModel;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationViewFlipperPausing.$r8$clinit;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, String str, Context context, AttributeSet attributeSet) {
        if (!Intrinsics.areEqual(str, ViewFlipper.class.getName()) && !Intrinsics.areEqual(str, "ViewFlipper")) {
            return null;
        }
        ViewFlipper viewFlipper = new ViewFlipper(context, attributeSet);
        NotificationViewFlipperBinder.INSTANCE.getClass();
        NotificationViewFlipperBinder.bindWhileAttached(viewFlipper, this.viewModel);
        return viewFlipper;
    }
}
