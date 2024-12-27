package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;
import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import com.android.systemui.statusbar.notification.shared.NotificationViewFlipperPausing;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationViewFlipperFactory implements NotifRemoteViewsFactory {
    public final NotificationViewFlipperViewModel viewModel;

    public NotificationViewFlipperFactory(NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        this.viewModel = notificationViewFlipperViewModel;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationViewFlipperPausing.$r8$clinit;
        Flags.FEATURE_FLAGS.getClass();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, String str, Context context, AttributeSet attributeSet) {
        if (!(str.equals(ViewFlipper.class.getName()) ? true : str.equals("ViewFlipper"))) {
            return null;
        }
        ViewFlipper viewFlipper = new ViewFlipper(context, attributeSet);
        NotificationViewFlipperBinder.INSTANCE.getClass();
        NotificationViewFlipperBinder.bindWhileAttached(viewFlipper, this.viewModel);
        return viewFlipper;
    }
}
