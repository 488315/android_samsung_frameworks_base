package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.notifications.ui.composable.NotificationsKt$NotificationScrollingStack$6$1$1;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.statusbar.notification.stack.shared.model.AccessibilityScrollEvent;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class NotificationScrollViewModel$accessibilityScrollEventConsumer$1 extends FunctionReferenceImpl implements Function1 {
    public NotificationScrollViewModel$accessibilityScrollEventConsumer$1(Object obj) {
        super(1, obj, NotificationStackAppearanceInteractor.class, "sendAccessibilityScrollEvent", "sendAccessibilityScrollEvent(Lcom/android/systemui/statusbar/notification/stack/shared/model/AccessibilityScrollEvent;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        AccessibilityScrollEvent accessibilityScrollEvent = (AccessibilityScrollEvent) obj;
        NotificationsKt$NotificationScrollingStack$6$1$1 notificationsKt$NotificationScrollingStack$6$1$1 = ((NotificationStackAppearanceInteractor) this.receiver).placeholderRepository.accessibilityScrollEventConsumer;
        if (notificationsKt$NotificationScrollingStack$6$1$1 != null) {
            notificationsKt$NotificationScrollingStack$6$1$1.accept(accessibilityScrollEvent);
        }
        return Unit.INSTANCE;
    }
}
