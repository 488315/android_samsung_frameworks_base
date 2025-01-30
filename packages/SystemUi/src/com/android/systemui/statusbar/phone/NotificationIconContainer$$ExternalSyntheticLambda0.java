package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.phone.NotificationIconContainer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        NotificationIconContainer.C30781 c30781 = NotificationIconContainer.DOT_ANIMATION_PROPERTIES;
        notificationIconContainer.removeTransientView(statusBarIconView);
    }
}
