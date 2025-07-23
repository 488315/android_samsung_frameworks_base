package com.android.systemui.statusbar.notification.domain.interactor;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ActiveNotificationsStoreBuilder {
    public final ActiveNotificationsStore.Builder builder = new ActiveNotificationsStore.Builder();
    public final Context context;
    public final ActiveNotificationsStore existingModels;
    public final SectionStyleProvider sectionStyleProvider;

    public ActiveNotificationsStoreBuilder(ActiveNotificationsStore activeNotificationsStore, SectionStyleProvider sectionStyleProvider, Context context) {
        this.existingModels = activeNotificationsStore;
        this.sectionStyleProvider = sectionStyleProvider;
        this.context = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x02cf A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.statusbar.notification.shared.ActiveNotificationModel toModel(com.android.systemui.statusbar.notification.collection.NotificationEntry r35) {
        /*
            Method dump skipped, instructions count: 766
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsStoreBuilder.toModel(com.android.systemui.statusbar.notification.collection.NotificationEntry):com.android.systemui.statusbar.notification.shared.ActiveNotificationModel");
    }
}
