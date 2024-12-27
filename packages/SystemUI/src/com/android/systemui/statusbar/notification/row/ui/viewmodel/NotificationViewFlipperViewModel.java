package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.shared.NotificationViewFlipperPausing;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationViewFlipperViewModel extends FlowDumperImpl {
    public final Flow isPaused;

    public NotificationViewFlipperViewModel(DumpManager dumpManager, NotificationStackInteractor notificationStackInteractor) {
        super(dumpManager, null, 2, null);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationViewFlipperPausing.$r8$clinit;
        Flags.FEATURE_FLAGS.getClass();
        this.isPaused = dumpWhileCollecting(notificationStackInteractor.isShowingOnLockscreen, "isPaused");
    }
}
