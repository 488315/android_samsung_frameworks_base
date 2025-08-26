package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class ConversationNotificationManager$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
        int i = ConversationNotificationManager.$r8$clinit;
        if (conversationState != null) {
            return new ConversationNotificationManager.ConversationState(0, conversationState.f134notification);
        }
        return null;
    }
}
