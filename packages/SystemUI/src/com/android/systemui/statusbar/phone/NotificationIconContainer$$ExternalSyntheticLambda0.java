package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.NotificationIconContainer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationIconContainer$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ NotificationIconContainer f$0;
    public final /* synthetic */ StatusBarIconView f$1;

    public /* synthetic */ NotificationIconContainer$$ExternalSyntheticLambda0(NotificationIconContainer notificationIconContainer, StatusBarIconView statusBarIconView) {
        this.f$0 = notificationIconContainer;
        this.f$1 = statusBarIconView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationIconContainer notificationIconContainer = this.f$0;
        StatusBarIconView statusBarIconView = this.f$1;
        NotificationIconContainer.AnonymousClass1 anonymousClass1 = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
        notificationIconContainer.removeTransientView(statusBarIconView);
    }
}
