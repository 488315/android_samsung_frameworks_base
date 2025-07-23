package com.android.systemui.statusbar.notification;

import android.app.INotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AllowedOngoingActivityListManager {
    public List allowedList;
    public final Context context;
    public final INotificationManager notificationManager;

    public AllowedOngoingActivityListManager(Context context, INotificationManager iNotificationManager) {
        this.context = context;
        this.notificationManager = iNotificationManager;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.AllowedOngoingActivityListManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final AllowedOngoingActivityListManager allowedOngoingActivityListManager = AllowedOngoingActivityListManager.this;
                return new BroadcastReceiver() { // from class: com.android.systemui.statusbar.notification.AllowedOngoingActivityListManager$receiver$2$1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "com.samsung.intent.action.ONGOING_ACTIVITY_LIST_CHANGED")) {
                            AllowedOngoingActivityListManager allowedOngoingActivityListManager2 = AllowedOngoingActivityListManager.this;
                            allowedOngoingActivityListManager2.allowedList = allowedOngoingActivityListManager2.notificationManager.getAllowedOngoingActivityAppList();
                        }
                    }
                };
            }
        });
        context.registerReceiver((AllowedOngoingActivityListManager$receiver$2$1) lazy.getValue(), new IntentFilter("com.samsung.intent.action.ONGOING_ACTIVITY_LIST_CHANGED"), 2);
        this.allowedList = iNotificationManager.getAllowedOngoingActivityAppList();
    }
}
