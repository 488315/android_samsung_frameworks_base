package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class NotificationScrollViewModel$currentGestureOverscrollConsumer$1 extends FunctionReferenceImpl implements Function1 {
    public NotificationScrollViewModel$currentGestureOverscrollConsumer$1(Object obj) {
        super(1, obj, NotificationStackAppearanceInteractor.class, "setCurrentGestureOverscroll", "setCurrentGestureOverscroll(Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Boolean bool = (Boolean) obj;
        bool.getClass();
        ((NotificationStackAppearanceInteractor) this.receiver).viewHeightRepository.isCurrentGestureOverscroll.updateState(null, bool);
        return Unit.INSTANCE;
    }
}
