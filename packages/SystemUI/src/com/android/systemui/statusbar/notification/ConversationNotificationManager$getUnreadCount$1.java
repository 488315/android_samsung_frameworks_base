package com.android.systemui.statusbar.notification;

import android.app.Notification;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ConversationNotificationManager$getUnreadCount$1 implements BiFunction {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ Notification.Builder $recoveredBuilder;
    public final /* synthetic */ ConversationNotificationManager this$0;

    public ConversationNotificationManager$getUnreadCount$1(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager, Notification.Builder builder) {
        this.$entry = notificationEntry;
        this.this$0 = conversationNotificationManager;
        this.$recoveredBuilder = builder;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        int i = 1;
        ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
        if (conversationState != null) {
            ConversationNotificationManager conversationNotificationManager = this.this$0;
            Notification.Builder builder = this.$recoveredBuilder;
            int i2 = ConversationNotificationManager.$r8$clinit;
            conversationNotificationManager.getClass();
            Notification notification2 = conversationState.f98notification;
            boolean areStyledNotificationsVisiblyDifferent = (notification2.flags & 8) != 0 ? false : Notification.areStyledNotificationsVisiblyDifferent(Notification.Builder.recoverBuilder(conversationNotificationManager.context, notification2), builder);
            int i3 = conversationState.unreadCount;
            if (areStyledNotificationsVisiblyDifferent) {
                i3++;
            }
            i = i3;
        }
        return new ConversationNotificationManager.ConversationState(i, this.$entry.mSbn.getNotification());
    }
}
