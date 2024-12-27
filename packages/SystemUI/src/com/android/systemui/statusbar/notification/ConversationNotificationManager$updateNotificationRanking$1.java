package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.row.NotificationContentView;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class ConversationNotificationManager$updateNotificationRanking$1 extends FunctionReferenceImpl implements Function1 {
    public static final ConversationNotificationManager$updateNotificationRanking$1 INSTANCE = new ConversationNotificationManager$updateNotificationRanking$1();

    public ConversationNotificationManager$updateNotificationRanking$1() {
        super(1, Intrinsics.Kotlin.class, "getLayouts", "updateNotificationRanking$getLayouts(Lcom/android/systemui/statusbar/notification/row/NotificationContentView;)Lkotlin/sequences/Sequence;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        NotificationContentView notificationContentView = (NotificationContentView) obj;
        int i = ConversationNotificationManager.$r8$clinit;
        return SequencesKt__SequencesKt.sequenceOf(notificationContentView.mContractedChild, notificationContentView.mExpandedChild, notificationContentView.mHeadsUpChild);
    }
}
