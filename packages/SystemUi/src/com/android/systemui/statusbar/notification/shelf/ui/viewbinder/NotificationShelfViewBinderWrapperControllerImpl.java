package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import com.android.systemui.statusbar.LockscreenShadeTransitionController$bindController$1;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShelfViewBinderWrapperControllerImpl implements NotificationShelfController {
    public static void getUnsupported() {
        NotificationShelfController.Companion.getClass();
        throw new IllegalStateException("Code path not supported when Flags.NOTIFICATION_SHELF_REFACTOR is ".concat("disabled").toString());
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final void bind(AmbientState ambientState, NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final boolean canModifyColorOfNotifications() {
        getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final int getIntrinsicHeight() {
        getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final NotificationIconContainer getShelfIcons() {
        getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final NotificationShelf getView() {
        getUnsupported();
        throw null;
    }

    @Override // com.android.systemui.statusbar.NotificationShelfController
    public final void setOnClickListener(LockscreenShadeTransitionController$bindController$1 lockscreenShadeTransitionController$bindController$1) {
        getUnsupported();
        throw null;
    }
}
