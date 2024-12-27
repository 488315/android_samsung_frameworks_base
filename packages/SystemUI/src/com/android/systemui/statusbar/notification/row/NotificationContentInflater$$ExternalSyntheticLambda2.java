package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.content.Context;
import android.widget.RemoteViews;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import kotlin.jvm.functions.Function0;

public final /* synthetic */ class NotificationContentInflater$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ ExpandableNotificationRow f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Context f$10;
    public final /* synthetic */ NotificationRowContentBinderLogger f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ Notification.Builder f$4;
    public final /* synthetic */ boolean f$5;
    public final /* synthetic */ HeadsUpStyleProvider f$6;
    public final /* synthetic */ boolean f$7;
    public final /* synthetic */ boolean f$8;
    public final /* synthetic */ NotifLayoutInflaterFactory.Provider f$9;

    public /* synthetic */ NotificationContentInflater$$ExternalSyntheticLambda2(ExpandableNotificationRow expandableNotificationRow, int i, NotificationRowContentBinderLogger notificationRowContentBinderLogger, boolean z, Notification.Builder builder, boolean z2, HeadsUpStyleProvider headsUpStyleProvider, boolean z3, boolean z4, NotifLayoutInflaterFactory.Provider provider, Context context) {
        this.f$0 = expandableNotificationRow;
        this.f$1 = i;
        this.f$2 = notificationRowContentBinderLogger;
        this.f$3 = z;
        this.f$4 = builder;
        this.f$5 = z2;
        this.f$6 = headsUpStyleProvider;
        this.f$7 = z3;
        this.f$8 = z4;
        this.f$9 = provider;
        this.f$10 = context;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        RemoteViews createBigContentView;
        RemoteViews makeLowPriorityContentView;
        Notification.Builder builder = this.f$4;
        Context context = this.f$10;
        NotificationContentInflater.InflationProgress inflationProgress = new NotificationContentInflater.InflationProgress();
        ExpandableNotificationRow expandableNotificationRow = this.f$0;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        int i = this.f$1;
        int i2 = i & 1;
        NotificationRowContentBinderLogger notificationRowContentBinderLogger = this.f$2;
        boolean z = this.f$3;
        if (i2 != 0) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "creating contracted remote view");
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            String str = expandableNotificationRow.mEntry.mKey;
            ongoingActivityDataHelper.getClass();
            OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
            if (!expandableNotificationRow.mEntry.isOngoingAcitivty() || ongoingActivityDataByKey == null) {
                makeLowPriorityContentView = z ? builder.makeLowPriorityContentView(false) : builder.createContentView(this.f$5);
            } else if (z) {
                builder.setContentTitle(ongoingActivityDataByKey.mPrimaryInfo).setContentText(ongoingActivityDataByKey.mSecondaryInfo);
                makeLowPriorityContentView = builder.makeLowPriorityContentView(false);
            } else {
                makeLowPriorityContentView = ongoingActivityDataByKey.mOngoingCollapsedView;
            }
            inflationProgress.newContentView = makeLowPriorityContentView;
        }
        if ((i & 2) != 0) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "creating expanded remote view");
            OngoingActivityDataHelper ongoingActivityDataHelper2 = OngoingActivityDataHelper.INSTANCE;
            String str2 = expandableNotificationRow.mEntry.mKey;
            ongoingActivityDataHelper2.getClass();
            OngoingActivityData ongoingActivityDataByKey2 = OngoingActivityDataHelper.getOngoingActivityDataByKey(str2);
            if (!expandableNotificationRow.mEntry.isOngoingAcitivty() || ongoingActivityDataByKey2 == null) {
                createBigContentView = builder.createBigContentView();
                if (createBigContentView == null) {
                    if (z) {
                        createBigContentView = builder.createContentView();
                        Notification.Builder.makeHeaderExpanded(createBigContentView);
                    } else {
                        createBigContentView = null;
                    }
                }
            } else {
                createBigContentView = ongoingActivityDataByKey2.mOngoingExpandView;
            }
            inflationProgress.newExpandedView = createBigContentView;
        }
        if ((i & 4) != 0) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "creating heads up remote view");
            if (((HeadsUpStyleProviderImpl) this.f$6).shouldApplyCompactStyle()) {
                inflationProgress.newHeadsUpView = builder.createCompactHeadsUpContentView();
            } else {
                inflationProgress.newHeadsUpView = builder.createHeadsUpContentView(this.f$7);
            }
        }
        if ((i & 8) != 0) {
            notificationRowContentBinderLogger.logAsyncTaskProgress(notificationEntry, "creating public remote view");
            inflationProgress.newPublicView = builder.makePublicContentView(z, this.f$8);
        }
        int i3 = AsyncGroupHeaderViewInflation.$r8$clinit;
        Flags.notificationAsyncGroupHeaderInflation();
        RemoteViews remoteViews = inflationProgress.newContentView;
        NotifLayoutInflaterFactory.Provider provider = this.f$9;
        NotifLayoutInflaterFactory provide = provider.provide(expandableNotificationRow, 1);
        if (remoteViews != null) {
            remoteViews.setLayoutInflaterFactory(provide);
        }
        RemoteViews remoteViews2 = inflationProgress.newExpandedView;
        NotifLayoutInflaterFactory provide2 = provider.provide(expandableNotificationRow, 2);
        if (remoteViews2 != null) {
            remoteViews2.setLayoutInflaterFactory(provide2);
        }
        RemoteViews remoteViews3 = inflationProgress.newHeadsUpView;
        NotifLayoutInflaterFactory provide3 = provider.provide(expandableNotificationRow, 4);
        if (remoteViews3 != null) {
            remoteViews3.setLayoutInflaterFactory(provide3);
        }
        RemoteViews remoteViews4 = inflationProgress.newPublicView;
        NotifLayoutInflaterFactory provide4 = provider.provide(expandableNotificationRow, 8);
        if (remoteViews4 != null) {
            remoteViews4.setLayoutInflaterFactory(provide4);
        }
        inflationProgress.packageContext = context;
        inflationProgress.headsUpStatusBarText = builder.getHeadsUpStatusBarText(false);
        inflationProgress.headsUpStatusBarTextPublic = builder.getHeadsUpStatusBarText(true);
        return inflationProgress;
    }
}
