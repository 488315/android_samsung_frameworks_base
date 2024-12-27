package com.android.systemui.people.widget;

import android.service.notification.ConversationChannelWrapper;
import android.text.TextUtils;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return !TextUtils.isEmpty((String) obj);
            case 1:
                ConversationChannelWrapper conversationChannelWrapper = (ConversationChannelWrapper) obj;
                return conversationChannelWrapper.getNotificationChannel() != null && conversationChannelWrapper.getNotificationChannel().isImportantConversation();
            default:
                ConversationChannelWrapper conversationChannelWrapper2 = (ConversationChannelWrapper) obj;
                return conversationChannelWrapper2.getNotificationChannel() == null || !conversationChannelWrapper2.getNotificationChannel().isImportantConversation();
        }
    }
}
