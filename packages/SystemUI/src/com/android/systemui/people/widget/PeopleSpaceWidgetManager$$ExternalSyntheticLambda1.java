package com.android.systemui.people.widget;

import android.app.people.ConversationChannel;
import android.service.notification.ConversationChannelWrapper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new PeopleTileKey((NotificationEntry) obj);
            case 1:
                return ((ConversationChannelWrapper) obj).getShortcutInfo();
            case 2:
                return ((ConversationChannel) obj).getShortcutInfo();
            case 3:
                return Integer.valueOf(Integer.parseInt((String) obj));
            default:
                return ((ConversationChannelWrapper) obj).getShortcutInfo();
        }
    }
}
