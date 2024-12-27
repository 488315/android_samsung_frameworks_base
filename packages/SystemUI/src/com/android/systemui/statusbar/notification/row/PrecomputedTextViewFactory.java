package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.internal.widget.MessagingLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PrecomputedTextViewFactory implements NotifRemoteViewsFactory {
    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, String str, Context context, AttributeSet attributeSet) {
        if (str.equals(ImageFloatingTextView.class.getName())) {
            return new PrecomputedImageFloatingTextView(context, attributeSet, 0, 4, null);
        }
        if (str.equals(MessagingLayout.class.getName())) {
            MessagingLayout messagingLayout = new MessagingLayout(context, attributeSet);
            messagingLayout.setPrecomputedTextEnabled(true);
            return messagingLayout;
        }
        if (!str.equals(ConversationLayout.class.getName())) {
            return null;
        }
        ConversationLayout conversationLayout = new ConversationLayout(context, attributeSet);
        conversationLayout.setPrecomputedTextEnabled(true);
        return conversationLayout;
    }
}
