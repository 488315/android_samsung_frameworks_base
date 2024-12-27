package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.content.Context;
import android.net.Uri;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.SummarizeController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SystemSettings;
import dagger.Lazy;

@CoordinatorScope
public class SummarizeCoordinator implements Coordinator {
    private Lazy mCommonNotifCollectionLazy;
    private Context mContext;
    private NotifPipeline mNotifPipeline;
    private int mPanelState;
    private final StatusBarStateController mStatusBarStateController;
    private final SummarizeController mSummarizeController;
    private boolean mSummarizeEnabled;
    private final String TAG = "SummarizeCoordinator";
    private boolean DEBUG = false;
    private final SettingsHelper.OnChangedCallback mSettingsChangedListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SummarizeCoordinator.2
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public void onChanged(Uri uri) {
            SummarizeCoordinator.this.mSummarizeEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableSummarize();
            Log.d("SummarizeCoordinator", " mSummarizeEnabled: " + SummarizeCoordinator.this.mSummarizeEnabled);
            if (SummarizeCoordinator.this.mSummarizeEnabled) {
                return;
            }
            for (NotificationEntry notificationEntry : ((NotifPipeline) ((CommonNotifCollection) SummarizeCoordinator.this.mCommonNotifCollectionLazy.get())).getAllNotifs()) {
                if (notificationEntry.mSummarizeDone) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(" update original contents for "), notificationEntry.mKey, "SummarizeCoordinator");
                    notificationEntry.row.updateSummarize("", false);
                    notificationEntry.mSummarizeDone = false;
                }
            }
            SummarizeCoordinator.this.mSummarizeController.mPromptProcessor.releaseSummarizer();
        }
    };
    private final NotifCollectionListener mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SummarizeCoordinator.3
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            if (SummarizeCoordinator.this.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("onEntryAdded :"), notificationEntry.mKey, "SummarizeCoordinator");
            }
            notificationEntry.mSummarizeDone = false;
            notificationEntry.mInflateDone = false;
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry) {
            if (SummarizeCoordinator.this.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("onEntryUpdated :"), notificationEntry.mKey, "SummarizeCoordinator");
            }
            notificationEntry.mSummarizeDone = false;
            notificationEntry.mInflateDone = false;
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onRankingApplied() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryCleanUp(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryInit(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        @Deprecated
        public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    };

    public SummarizeCoordinator(Context context, SystemSettings systemSettings, Lazy lazy, ShadeExpansionStateManager shadeExpansionStateManager, SummarizeController summarizeController, StatusBarStateController statusBarStateController) {
        this.mStatusBarStateController = statusBarStateController;
        this.mSummarizeController = summarizeController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0154  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onPanelStateChanged(int r21) {
        /*
            Method dump skipped, instructions count: 675
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SummarizeCoordinator.onPanelStateChanged(int):void");
    }
}
