package com.android.server.notification;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NotificationHighlightCore {
    public static NotificationHighlightCore sNotificationHighlightCore;
    public final Uri AUTO_GROUPING_URI;
    public final Uri PRIVACY_CONVERSATION_URI;
    public boolean mAutoGroupingEnabled;
    public final Context mContext;
    public Handler mHandler;
    public NotificationClassifier mNotificationClassifier;
    public boolean mPrivacyConverstionEnabled;
    public AnonymousClass2 mSettingsObserver;
    public final HashSet mConversationList = new HashSet();
    public final HashSet mNonImportantList = new HashSet();

    public NotificationHighlightCore(Context context) {
        new HashSet();
        new ArrayMap();
        new ArrayList();
        new ArrayList();
        this.mNotificationClassifier = null;
        this.PRIVACY_CONVERSATION_URI =
                Settings.System.getUriFor("noti_intelligence_priority_conversation");
        this.AUTO_GROUPING_URI = Settings.System.getUriFor("noti_auto_more_grouping");
        this.mPrivacyConverstionEnabled = false;
        this.mAutoGroupingEnabled = false;
        new ArrayList();
        this.mContext = context;
    }

    public final void setConversationList(List list) {
        if (list == null) {
            return;
        }
        this.mConversationList.clear();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            this.mConversationList.add(list.get(i).toString());
        }
    }
}
