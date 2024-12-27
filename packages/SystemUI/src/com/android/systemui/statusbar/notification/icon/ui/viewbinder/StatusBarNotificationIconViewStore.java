package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.icon.IconPack;
import kotlin.jvm.functions.Function1;

public final class StatusBarNotificationIconViewStore {
    public final /* synthetic */ NotificationIconContainerViewBinderKt$iconViewStoreBy$1 $$delegate_0;

    public StatusBarNotificationIconViewStore(NotifCollection notifCollection) {
        new NotificationIconContainerViewBinderKt$iconViewStoreBy$1(notifCollection, new Function1() { // from class: com.android.systemui.statusbar.notification.icon.ui.viewbinder.StatusBarNotificationIconViewStore.1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((IconPack) obj).mStatusBarIcon;
            }
        });
    }
}
