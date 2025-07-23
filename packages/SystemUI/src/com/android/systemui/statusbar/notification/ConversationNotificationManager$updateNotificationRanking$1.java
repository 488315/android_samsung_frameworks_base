package com.android.systemui.statusbar.notification;

import android.view.View;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class ConversationNotificationManager$updateNotificationRanking$1 extends FunctionReferenceImpl implements Function1 {
    public static final ConversationNotificationManager$updateNotificationRanking$1 INSTANCE = new ConversationNotificationManager$updateNotificationRanking$1();

    public ConversationNotificationManager$updateNotificationRanking$1() {
        super(1, Intrinsics.Kotlin.class, "getLayouts", "updateNotificationRanking$getLayouts(Lcom/android/systemui/statusbar/notification/row/NotificationContentView;)Lkotlin/sequences/Sequence;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        NotificationContentView notificationContentView = (NotificationContentView) obj;
        int i = ConversationNotificationManager.$r8$clinit;
        return ArraysKt___ArraysKt.asSequence(new View[]{notificationContentView.mContractedChild, notificationContentView.mExpandedChild, notificationContentView.mHeadsUpChild});
    }
}
