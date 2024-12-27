package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CoordinatorScope
public class NotifCounterCoordinator implements Coordinator {
    public static final long MINIMUM_STATUS_UPDATE_PERIOD_MS = 604800000;
    private static final String TAG = "NotifCounterCoordinator";
    private SharedPreferences.Editor mEditor;
    private long mLastUpdateTime;
    private int mMaxCount;
    private SharedPreferences mNotifCounterPrefs;

    public NotifCounterCoordinator(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.NOTIFICATION_PREF_NAME, 0);
        this.mNotifCounterPrefs = sharedPreferences;
        if (sharedPreferences != null) {
            this.mMaxCount = sharedPreferences.getInt(SystemUIAnalytics.STID_NOTIFICATION_TOTAL_COUNT, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAfterRenderList(List<ListEntry> list, NotifStackController notifStackController) {
        int i;
        refreshMaxCount();
        if (this.mMaxCount < list.size()) {
            this.mMaxCount = list.size();
            SharedPreferences sharedPreferences = this.mNotifCounterPrefs;
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                this.mEditor = edit;
                edit.putInt(SystemUIAnalytics.STID_NOTIFICATION_TOTAL_COUNT, this.mMaxCount);
                this.mEditor.commit();
            }
        }
        if (!NotiRune.NOTI_INSIGNIFICANT || this.mNotifCounterPrefs == null) {
            return;
        }
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificant()) {
            SharedPreferences.Editor edit2 = this.mNotifCounterPrefs.edit();
            this.mEditor = edit2;
            edit2.putString(SystemUIAnalytics.STID_FILTERED_NOTIFICATION_COUNT, "OFF");
            this.mEditor.commit();
            return;
        }
        Iterator<ListEntry> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                i = 0;
                break;
            }
            NotificationEntry representativeEntry = it.next().getRepresentativeEntry();
            if (representativeEntry.isInsignificant() && representativeEntry.getAttachedNotifChildren() != null) {
                i = ((ArrayList) representativeEntry.getAttachedNotifChildren()).size();
                break;
            }
        }
        SharedPreferences.Editor edit3 = this.mNotifCounterPrefs.edit();
        this.mEditor = edit3;
        edit3.putInt(SystemUIAnalytics.STID_FILTERED_NOTIFICATION_COUNT, i);
        this.mEditor.commit();
    }

    private void refreshMaxCount() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastUpdateTime > MINIMUM_STATUS_UPDATE_PERIOD_MS) {
            this.mLastUpdateTime = currentTimeMillis;
            this.mMaxCount = 0;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.NotifCounterCoordinator$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                NotifCounterCoordinator.this.onAfterRenderList(list, notifStackController);
            }
        });
    }
}
