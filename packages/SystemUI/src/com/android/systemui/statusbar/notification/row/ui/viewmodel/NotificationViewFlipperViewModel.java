package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.shared.NotificationViewFlipperPausing;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationViewFlipperViewModel extends FlowDumperImpl {
    public final Flow isPaused;

    public NotificationViewFlipperViewModel(DumpManager dumpManager, NotificationStackInteractor notificationStackInteractor) {
        super(dumpManager, null, 2, null);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = NotificationViewFlipperPausing.$r8$clinit;
        this.isPaused = dumpWhileCollecting(notificationStackInteractor.isShowingOnLockscreen, "isPaused");
    }
}
