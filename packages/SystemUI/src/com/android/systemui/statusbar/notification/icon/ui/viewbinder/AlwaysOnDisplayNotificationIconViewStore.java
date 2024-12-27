package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.icon.IconPack;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AlwaysOnDisplayNotificationIconViewStore {
    public final /* synthetic */ NotificationIconContainerViewBinderKt$iconViewStoreBy$1 $$delegate_0;

    public AlwaysOnDisplayNotificationIconViewStore(NotifCollection notifCollection) {
        new NotificationIconContainerViewBinderKt$iconViewStoreBy$1(notifCollection, new Function1() { // from class: com.android.systemui.statusbar.notification.icon.ui.viewbinder.AlwaysOnDisplayNotificationIconViewStore.1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((IconPack) obj).mAodIcon;
            }
        });
    }
}
