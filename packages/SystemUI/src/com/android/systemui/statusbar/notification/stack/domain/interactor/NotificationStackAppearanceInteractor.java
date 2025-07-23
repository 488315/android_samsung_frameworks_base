package com.android.systemui.statusbar.notification.stack.domain.interactor;

import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.statusbar.notification.stack.data.repository.NotificationPlaceholderRepository;
import com.android.systemui.statusbar.notification.stack.data.repository.NotificationViewHeightRepository;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationStackAppearanceInteractor {
    public final ReadonlyStateFlow alphaForBrightnessMirror;
    public final ReadonlyStateFlow alphaForLockscreenFadeIn;
    public final ReadonlyStateFlow constrainedAvailableSpace;
    public final ReadonlyStateFlow isCurrentGestureOverscroll;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isExpandingFromHeadsUp;
    public final ReadonlyStateFlow notificationShadeScrimBounds;
    public final NotificationPlaceholderRepository placeholderRepository;
    public final Flow shadeScrimRounding;
    public final ReadonlyStateFlow shadeScrollState;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 shouldCloseGuts;
    public final ReadonlyStateFlow syntheticScroll;
    public final NotificationViewHeightRepository viewHeightRepository;

    public NotificationStackAppearanceInteractor(NotificationViewHeightRepository notificationViewHeightRepository, NotificationPlaceholderRepository notificationPlaceholderRepository, SceneInteractor sceneInteractor, ShadeModeInteractor shadeModeInteractor) {
        this.viewHeightRepository = notificationViewHeightRepository;
        this.placeholderRepository = notificationPlaceholderRepository;
        this.notificationShadeScrimBounds = FlowKt.asStateFlow(notificationPlaceholderRepository.notificationShadeScrimBounds);
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        this.isExpandingFromHeadsUp = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.shadeScrimRounding = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, new NotificationStackAppearanceInteractor$shadeScrimRounding$1(null)));
        this.alphaForBrightnessMirror = FlowKt.asStateFlow(notificationPlaceholderRepository.alphaForBrightnessMirror);
        this.alphaForLockscreenFadeIn = FlowKt.asStateFlow(notificationPlaceholderRepository.alphaForLockscreenFadeIn);
        this.constrainedAvailableSpace = FlowKt.asStateFlow(notificationPlaceholderRepository.constrainedAvailableSpace);
        this.shadeScrollState = FlowKt.asStateFlow(notificationPlaceholderRepository.shadeScrollState);
        this.syntheticScroll = FlowKt.asStateFlow(notificationViewHeightRepository.syntheticScroll);
        this.isCurrentGestureOverscroll = FlowKt.asStateFlow(notificationViewHeightRepository.isCurrentGestureOverscroll);
        this.shouldCloseGuts = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(sceneInteractor.isSceneContainerUserInputOngoing, notificationViewHeightRepository.isCurrentGestureInGuts, new NotificationStackAppearanceInteractor$shouldCloseGuts$1(null));
    }
}
