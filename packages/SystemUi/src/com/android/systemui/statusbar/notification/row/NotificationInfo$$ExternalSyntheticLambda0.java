package com.android.systemui.statusbar.notification.row;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        switch (this.$r8$classId) {
            case 0:
                NotificationInfo notificationInfo = this.f$0;
                notificationInfo.mIsAutomaticChosen = true;
                notificationInfo.applyAlertingBehavior(2, true);
                return;
            case 1:
                NotificationInfo notificationInfo2 = this.f$0;
                notificationInfo2.mChosenImportance = 3;
                notificationInfo2.mIsAutomaticChosen = false;
                notificationInfo2.applyAlertingBehavior(0, true);
                return;
            case 2:
                NotificationInfo notificationInfo3 = this.f$0;
                notificationInfo3.mChosenImportance = 2;
                notificationInfo3.mIsAutomaticChosen = false;
                notificationInfo3.applyAlertingBehavior(1, true);
                return;
            case 3:
                NotificationInfo notificationInfo4 = this.f$0;
                notificationInfo4.mPressedApply = true;
                notificationInfo4.mGutsContainer.closeControls(view, true);
                return;
            default:
                final NotificationInfo notificationInfo5 = this.f$0;
                if (notificationInfo5.mPresentingChannelEditorDialog || (channelEditorDialogController = notificationInfo5.mChannelEditorDialogController) == null) {
                    return;
                }
                notificationInfo5.mPresentingChannelEditorDialog = true;
                channelEditorDialogController.prepareDialogForApp(notificationInfo5.mAppName, notificationInfo5.mPackageName, notificationInfo5.mAppUid, notificationInfo5.mUniqueChannelsInRow, notificationInfo5.mPkgIcon, notificationInfo5.mOnSettingsClickListener);
                ChannelEditorDialogController channelEditorDialogController2 = notificationInfo5.mChannelEditorDialogController;
                channelEditorDialogController2.onFinishListener = new OnChannelEditorDialogFinishedListener() { // from class: com.android.systemui.statusbar.notification.row.NotificationInfo$$ExternalSyntheticLambda3
                    @Override // com.android.systemui.statusbar.notification.row.OnChannelEditorDialogFinishedListener
                    public final void onChannelEditorDialogFinished() {
                        NotificationInfo notificationInfo6 = NotificationInfo.this;
                        notificationInfo6.mPresentingChannelEditorDialog = false;
                        notificationInfo6.mGutsContainer.closeControls(notificationInfo6, false);
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
