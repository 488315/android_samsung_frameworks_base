package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationsPlaceholderViewModel extends FlowDumperImpl {
    public final StateFlow expandFraction;
    public final NotificationStackAppearanceInteractor interactor;
    public final Flow isCurrentGestureOverscroll;
    public final Flow shadeScrimRounding;
    public final Flow syntheticScroll;

    public NotificationsPlaceholderViewModel(DumpManager dumpManager, NotificationStackAppearanceInteractor notificationStackAppearanceInteractor, ShadeInteractor shadeInteractor, FeatureFlagsClassic featureFlagsClassic, KeyguardInteractor keyguardInteractor) {
        super(dumpManager, null, 2, null);
        Flags flags = Flags.INSTANCE;
        com.android.systemui.Flags.sceneContainer();
        dumpWhileCollecting(notificationStackAppearanceInteractor.shadeScrimRounding, "shadeScrimRounding");
        dumpValue(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getAnyExpansion(), "expandFraction");
        dumpWhileCollecting(notificationStackAppearanceInteractor.syntheticScroll, "syntheticScroll");
        dumpWhileCollecting(notificationStackAppearanceInteractor.isCurrentGestureOverscroll, "isCurrentGestureOverScroll");
    }
}
