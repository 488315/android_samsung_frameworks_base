package com.android.systemui.statusbar.notification.stack.domain.interactor;

import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.stack.data.repository.NotificationPlaceholderRepository;
import com.android.systemui.statusbar.notification.stack.data.repository.NotificationViewHeightRepository;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationStackAppearanceInteractor {
    public final ReadonlyStateFlow alphaForBrightnessMirror;
    public final ReadonlyStateFlow constrainedAvailableSpace;
    public final ReadonlyStateFlow isCurrentGestureOverscroll;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isExpandingFromHeadsUp;
    public final NotificationPlaceholderRepository placeholderRepository;
    public final ReadonlyStateFlow scrolledToTop;
    public final ReadonlyStateFlow shadeScrimBounds;
    public final Flow shadeScrimRounding;
    public final ReadonlyStateFlow syntheticScroll;
    public final NotificationViewHeightRepository viewHeightRepository;

    public NotificationStackAppearanceInteractor(NotificationViewHeightRepository notificationViewHeightRepository, NotificationPlaceholderRepository notificationPlaceholderRepository, ShadeInteractor shadeInteractor) {
        this.viewHeightRepository = notificationViewHeightRepository;
        this.shadeScrimBounds = FlowKt.asStateFlow(notificationPlaceholderRepository.shadeScrimBounds);
        this.shadeScrimRounding = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getShadeMode(), new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE), new NotificationStackAppearanceInteractor$shadeScrimRounding$1(null)));
        this.alphaForBrightnessMirror = FlowKt.asStateFlow(notificationPlaceholderRepository.alphaForBrightnessMirror);
        FlowKt.asStateFlow(notificationPlaceholderRepository.constrainedAvailableSpace);
        this.scrolledToTop = FlowKt.asStateFlow(notificationPlaceholderRepository.scrolledToTop);
        this.syntheticScroll = FlowKt.asStateFlow(notificationViewHeightRepository.syntheticScroll);
        this.isCurrentGestureOverscroll = FlowKt.asStateFlow(notificationViewHeightRepository.isCurrentGestureOverscroll);
    }
}
