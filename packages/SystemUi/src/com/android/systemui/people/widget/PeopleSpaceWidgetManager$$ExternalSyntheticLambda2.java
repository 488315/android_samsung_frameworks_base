package com.android.systemui.people.widget;

import android.service.notification.ConversationChannelWrapper;
import android.text.TextUtils;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda2 implements Predicate {
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
