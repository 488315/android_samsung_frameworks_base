package com.android.systemui.statusbar.notification.row;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationInfo$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationInfo f$0;

    public /* synthetic */ NotificationInfo$$ExternalSyntheticLambda0(NotificationInfo notificationInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationInfo;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ChannelEditorDialogController channelEditorDialogController;
        int i = this.$r8$classId;
        final NotificationInfo notificationInfo = this.f$0;
        switch (i) {
            case 0:
                notificationInfo.mIsAutomaticChosen = true;
                notificationInfo.applyAlertingBehavior(2, true);
                return;
            case 1:
                notificationInfo.mChosenImportance = 3;
                notificationInfo.mIsAutomaticChosen = false;
                notificationInfo.applyAlertingBehavior(0, true);
                return;
            case 2:
                notificationInfo.mChosenImportance = 2;
                notificationInfo.mIsAutomaticChosen = false;
                notificationInfo.applyAlertingBehavior(1, true);
                return;
            case 3:
                notificationInfo.mPressedApply = true;
                notificationInfo.mGutsContainer.closeControls(view, true);
                return;
            default:
                if (notificationInfo.mPresentingChannelEditorDialog || (channelEditorDialogController = notificationInfo.mChannelEditorDialogController) == null) {
                    return;
                }
                notificationInfo.mPresentingChannelEditorDialog = true;
                channelEditorDialogController.prepareDialogForApp(notificationInfo.mAppName, notificationInfo.mPackageName, notificationInfo.mAppUid, notificationInfo.mSingleNotificationChannel, notificationInfo.mPkgIcon, notificationInfo.mOnSettingsClickListener);
                ChannelEditorDialogController channelEditorDialogController2 = notificationInfo.mChannelEditorDialogController;
                channelEditorDialogController2.onFinishListener = new OnChannelEditorDialogFinishedListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda9
                    @Override // com.android.systemui.statusbar.notification.row.OnChannelEditorDialogFinishedListener
                    public final void onChannelEditorDialogFinished() {
                        NotificationInfo notificationInfo2 = NotificationInfo.this;
                        notificationInfo2.mPresentingChannelEditorDialog = false;
                        notificationInfo2.mGutsContainer.closeControls(notificationInfo2, false);
                    }
                };
                if (!channelEditorDialogController2.prepared) {
                    throw new IllegalStateException("Must call prepareDialogForApp() before calling show()");
                }
                ChannelEditorDialog channelEditorDialog = channelEditorDialogController2.dialog;
                if (channelEditorDialog == null) {
                    channelEditorDialog = null;
                }
                channelEditorDialog.show();
                return;
        }
    }
}
