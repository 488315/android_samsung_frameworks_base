package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CoordinatorScope
/* loaded from: classes3.dex */
public class NotifCounterCoordinator implements Coordinator {
    public static final long MINIMUM_STATUS_UPDATE_PERIOD_MS = 604800000;
    private static final String TAG = "NotifCounterCoordinator";
    private SharedPreferences.Editor mEditor;
    private long mLastUpdateTime;
    private int mMaxCount;
    private SharedPreferences mNotifCounterPrefs;
    private final int MAX_COUNT_UPDATE = 0;
    private final int MORE_COUNT_UPDATE = 1;
    private final Handler mMoreCountHandler = new Handler() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifCounterCoordinator.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                NotifCounterCoordinator.this.updateMaxCount();
            } else {
                if (i != 1) {
                    return;
                }
                NotifCounterCoordinator.this.updateMoreCount((List) message.obj);
            }
        }
    };

    public NotifCounterCoordinator(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.NOTIFICATION_PREF_NAME, 0);
        this.mNotifCounterPrefs = sharedPreferences;
        if (sharedPreferences != null) {
            this.mMaxCount = sharedPreferences.getInt(SystemUIAnalytics.STID_NOTIFICATION_TOTAL_COUNT, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAfterRenderList(List<PipelineEntry> list) {
        refreshMaxCount();
        if (this.mMaxCount < list.size()) {
            this.mMaxCount = list.size();
            Message messageObtain = Message.obtain();
            messageObtain.what = 0;
            if (this.mMoreCountHandler.hasMessages(0)) {
                this.mMoreCountHandler.removeMessages(0);
            }
            this.mMoreCountHandler.sendMessage(messageObtain);
        }
    }

    private void refreshMaxCount() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.mLastUpdateTime > MINIMUM_STATUS_UPDATE_PERIOD_MS) {
            this.mLastUpdateTime = jCurrentTimeMillis;
            this.mMaxCount = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMaxCount() {
        SharedPreferences sharedPreferences = this.mNotifCounterPrefs;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            this.mEditor = editorEdit;
            editorEdit.putInt(SystemUIAnalytics.STID_NOTIFICATION_TOTAL_COUNT, this.mMaxCount);
            this.mEditor.commit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMoreCount(List<PipelineEntry> list) {
        int size;
        List list2;
        if (this.mNotifCounterPrefs != null) {
            if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantPromotion() && !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantBgActivities() && !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantMinimized() && !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantOld()) {
                SharedPreferences.Editor editorEdit = this.mNotifCounterPrefs.edit();
                this.mEditor = editorEdit;
                editorEdit.putString(SystemUIAnalytics.STID_FILTERED_NOTIFICATION_COUNT, "OFF");
                this.mEditor.commit();
                return;
            }
            Iterator<PipelineEntry> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                NotificationEntry representativeEntry = it.next().getRepresentativeEntry();
                if (representativeEntry != null && representativeEntry.isInsignificant()) {
                    ExpandableNotificationRow expandableNotificationRow = representativeEntry.row;
                    if (expandableNotificationRow == null || (list2 = expandableNotificationRow.mInsigificantChildrenList) == null) {
                        break;
                    } else {
                        size = ((ArrayList) list2).size();
                    }
                }
            }
            size = 0;
            SharedPreferences.Editor editorEdit2 = this.mNotifCounterPrefs.edit();
            this.mEditor = editorEdit2;
            editorEdit2.putInt(SystemUIAnalytics.STID_FILTERED_NOTIFICATION_COUNT, size);
            this.mEditor.commit();
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifCounterCoordinator$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list) {
                this.f$0.onAfterRenderList(list);
            }
        });
    }
}
