package com.android.systemui.statusbar.notification;

import android.view.View;
import com.android.internal.widget.ConversationLayout;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ConversationNotificationManager$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ConversationNotificationManager$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = ConversationNotificationManager.$r8$clinit;
                return ArraysKt___ArraysKt.asSequence(((NotificationContentView) obj).getAllViews());
            case 1:
                ConversationLayout conversationLayout = (View) obj;
                int i2 = ConversationNotificationManager.$r8$clinit;
                if (conversationLayout instanceof ConversationLayout) {
                    return conversationLayout;
                }
                return null;
            case 2:
                int i3 = ConversationNotificationManager.$r8$clinit;
                return ((NotificationEntry) obj).row;
            default:
                ConversationLayout conversationLayout2 = (View) obj;
                int i4 = ConversationNotificationManager.$r8$clinit;
                if (conversationLayout2 instanceof ConversationLayout) {
                    return conversationLayout2;
                }
                return null;
        }
    }
}
