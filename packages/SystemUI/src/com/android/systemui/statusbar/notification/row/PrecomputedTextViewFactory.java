package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.internal.widget.MessagingLayout;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class PrecomputedTextViewFactory implements NotifRemoteViewsFactory {
    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, String str, Context context, AttributeSet attributeSet) {
        if (Intrinsics.areEqual(str, ImageFloatingTextView.class.getName())) {
            return new PrecomputedImageFloatingTextView(context, attributeSet, 0, 4, null);
        }
        if (Intrinsics.areEqual(str, MessagingLayout.class.getName())) {
            MessagingLayout messagingLayout = new MessagingLayout(context, attributeSet);
            messagingLayout.setPrecomputedTextEnabled(true);
            return messagingLayout;
        }
        if (!Intrinsics.areEqual(str, ConversationLayout.class.getName())) {
            return null;
        }
        ConversationLayout conversationLayout = new ConversationLayout(context, attributeSet);
        conversationLayout.setPrecomputedTextEnabled(true);
        return conversationLayout;
    }
}
