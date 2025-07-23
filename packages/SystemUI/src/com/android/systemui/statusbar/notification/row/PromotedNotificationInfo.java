package com.android.systemui.statusbar.notification.row;

import android.app.INotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.PackageDemotionInteractor;
import com.android.systemui.statusbar.notification.row.icon.AppIconProvider;
import com.android.systemui.statusbar.notification.row.icon.NotificationIconStyleProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PromotedNotificationInfo extends NotificationInfo {
    public static final /* synthetic */ int $r8$clinit = 0;
    public NotificationGuts mGutsContainer;
    public INotificationManager mNotificationManager;
    public PackageDemotionInteractor mPackageDemotionInteractor;

    public PromotedNotificationInfo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInfo
    public final void bindNotification(PackageManager packageManager, INotificationManager iNotificationManager, AppIconProvider appIconProvider, NotificationIconStyleProvider notificationIconStyleProvider, OnUserInteractionCallback onUserInteractionCallback, ChannelEditorDialogController channelEditorDialogController, PackageDemotionInteractor packageDemotionInteractor, final String str, NotificationListenerService.Ranking ranking, final StatusBarNotification statusBarNotification, NotificationEntry notificationEntry, NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1, NotificationGutsManager$$ExternalSyntheticLambda2 notificationGutsManager$$ExternalSyntheticLambda2, ViewCompat$$ExternalSyntheticLambda0 viewCompat$$ExternalSyntheticLambda0, UiEventLogger uiEventLogger, boolean z, boolean z2, boolean z3, boolean z4, AssistantFeedbackController assistantFeedbackController, MetricsLogger metricsLogger, ExpandableNotificationRow$$ExternalSyntheticLambda6 expandableNotificationRow$$ExternalSyntheticLambda6) {
        super.bindNotification(packageManager, iNotificationManager, appIconProvider, notificationIconStyleProvider, onUserInteractionCallback, channelEditorDialogController, packageDemotionInteractor, str, ranking, statusBarNotification, notificationEntry, notificationGutsManager$$ExternalSyntheticLambda1, notificationGutsManager$$ExternalSyntheticLambda2, viewCompat$$ExternalSyntheticLambda0, uiEventLogger, z, z3, z2, z4, assistantFeedbackController, metricsLogger, expandableNotificationRow$$ExternalSyntheticLambda6);
        this.mNotificationManager = iNotificationManager;
        this.mPackageDemotionInteractor = packageDemotionInteractor;
        View findViewById = findViewById(R.id.promoted_demote);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.row.PromotedNotificationInfo$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotedNotificationInfo promotedNotificationInfo = PromotedNotificationInfo.this;
                String str2 = str;
                StatusBarNotification statusBarNotification2 = statusBarNotification;
                int i = PromotedNotificationInfo.$r8$clinit;
                promotedNotificationInfo.getClass();
                try {
                    promotedNotificationInfo.mNotificationManager.setCanBePromoted(str2, statusBarNotification2.getUid(), false, true);
                    PackageDemotionInteractor packageDemotionInteractor2 = promotedNotificationInfo.mPackageDemotionInteractor;
                    statusBarNotification2.getUid();
                    packageDemotionInteractor2.getClass();
                    promotedNotificationInfo.mGutsContainer.closeControls(view, true);
                } catch (RemoteException e) {
                    Log.e("PromotedNotifInfoGuts", "Couldn't revoke live update permission", e);
                }
            }
        });
        findViewById.setVisibility(findViewById.hasOnClickListeners() ? 0 : 8);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationInfo, com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
        super.mGutsContainer = notificationGuts;
    }
}
