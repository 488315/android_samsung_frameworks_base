package com.android.systemui.people.widget;

import android.app.people.ConversationChannel;
import android.service.notification.ConversationChannelWrapper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new PeopleTileKey((NotificationEntry) obj);
            case 1:
                return ((ConversationChannelWrapper) obj).getShortcutInfo();
            case 2:
                return ((ConversationChannelWrapper) obj).getShortcutInfo();
            case 3:
                return ((ConversationChannel) obj).getShortcutInfo();
            default:
                return Integer.valueOf(Integer.parseInt((String) obj));
        }
    }
}
