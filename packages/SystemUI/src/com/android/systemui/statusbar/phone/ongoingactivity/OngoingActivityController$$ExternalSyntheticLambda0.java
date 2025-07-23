package com.android.systemui.statusbar.phone.ongoingactivity;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingActivityController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ NotificationEntry f$0;
    public final /* synthetic */ OngoingActivityController f$1;

    public /* synthetic */ OngoingActivityController$$ExternalSyntheticLambda0(NotificationEntry notificationEntry, OngoingActivityController ongoingActivityController) {
        this.f$0 = notificationEntry;
        this.f$1 = ongoingActivityController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = OngoingActivityController.$r8$clinit;
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        StatusBarNotification statusBarNotification = this.f$0.mSbn;
        ongoingActivityDataHelper.getClass();
        OngoingActivityDataHelper.removeOngoingActivityByKey(statusBarNotification.getKey());
        this.f$1.updateParentViewVisibility(true);
        return Unit.INSTANCE;
    }
}
