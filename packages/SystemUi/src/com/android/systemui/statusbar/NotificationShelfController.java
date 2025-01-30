package com.android.systemui.statusbar;

import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NotificationShelfController {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    static void assertRefactorFlagDisabled() {
        Companion.getClass();
        Flags flags = Flags.INSTANCE;
    }

    static void checkRefactorFlagEnabled() {
        Companion.getClass();
        Flags flags = Flags.INSTANCE;
    }

    void bind(AmbientState ambientState, NotificationStackScrollLayoutController notificationStackScrollLayoutController);

    boolean canModifyColorOfNotifications();

    int getIntrinsicHeight();

    NotificationIconContainer getShelfIcons();

    NotificationShelf getView();

    void setOnClickListener(LockscreenShadeTransitionController$bindController$1 lockscreenShadeTransitionController$bindController$1);
}
