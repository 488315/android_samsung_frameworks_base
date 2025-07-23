package com.android.systemui.people.widget;

import android.app.people.ConversationChannel;
import android.service.notification.ConversationChannelWrapper;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Map map = PeopleSpaceWidgetManager.mListeners;
                return ((ConversationChannelWrapper) obj).getShortcutInfo();
            case 2:
                Map map2 = PeopleSpaceWidgetManager.mListeners;
                return ((ConversationChannel) obj).getShortcutInfo();
            case 3:
                return Integer.valueOf(Integer.parseInt((String) obj));
            default:
                Map map3 = PeopleSpaceWidgetManager.mListeners;
                return ((ConversationChannelWrapper) obj).getShortcutInfo();
        }
    }
}
