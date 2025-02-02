package com.android.systemui.people.widget;

import android.app.people.ConversationChannel;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PeopleSpaceWidgetManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PeopleSpaceWidgetManager peopleSpaceWidgetManager = (PeopleSpaceWidgetManager) this.f$0;
                int[] iArr = (int[]) this.f$1;
                peopleSpaceWidgetManager.getClass();
                try {
                    if (iArr.length == 0) {
                        return;
                    }
                    synchronized (peopleSpaceWidgetManager.mLock) {
                        peopleSpaceWidgetManager.updateSingleConversationWidgets(iArr);
                    }
                    return;
                } catch (Exception e) {
                    Log.e("PeopleSpaceWidgetMgr", "failed to update widgets", e);
                    return;
                }
            case 1:
                PeopleSpaceWidgetManager.C19141 c19141 = (PeopleSpaceWidgetManager.C19141) this.f$0;
                UserHandle userHandle = (UserHandle) this.f$1;
                PeopleSpaceWidgetManager peopleSpaceWidgetManager2 = c19141.this$0;
                if (peopleSpaceWidgetManager2.mUserManager.isUserUnlocked(userHandle)) {
                    int i = 0;
                    peopleSpaceWidgetManager2.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(i, peopleSpaceWidgetManager2, peopleSpaceWidgetManager2.mAppWidgetManager.getAppWidgetIds(new ComponentName(peopleSpaceWidgetManager2.mContext, (Class<?>) PeopleSpaceWidgetProvider.class))));
                    return;
                }
                return;
            case 2:
                ((PeopleSpaceWidgetManager.C19152) this.f$0).this$0.updateWidgetsFromBroadcastInBackground(((Intent) this.f$1).getAction());
                return;
            default:
                PeopleSpaceWidgetManager.TileConversationListener tileConversationListener = (PeopleSpaceWidgetManager.TileConversationListener) this.f$0;
                ConversationChannel conversationChannel = (ConversationChannel) this.f$1;
                PeopleSpaceWidgetManager peopleSpaceWidgetManager3 = tileConversationListener.this$0;
                peopleSpaceWidgetManager3.getClass();
                ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
                synchronized (peopleSpaceWidgetManager3.mLock) {
                    Iterator it = ((HashSet) peopleSpaceWidgetManager3.getMatchingKeyWidgetIds(new PeopleTileKey(shortcutInfo.getId(), shortcutInfo.getUserId(), shortcutInfo.getPackage()))).iterator();
                    while (it.hasNext()) {
                        peopleSpaceWidgetManager3.updateStorageAndViewWithConversationData(conversationChannel, Integer.parseInt((String) it.next()));
                    }
                }
                return;
        }
    }
}
