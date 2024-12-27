package com.android.systemui.statusbar.notification.stack;

import android.util.IndentingPrintWriter;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.util.DumpUtilsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationStackScrollLayout f$0;
    public final /* synthetic */ IndentingPrintWriter f$1;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda8(NotificationStackScrollLayout notificationStackScrollLayout, IndentingPrintWriter indentingPrintWriter, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayout;
        this.f$1 = indentingPrintWriter;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                final NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                final IndentingPrintWriter indentingPrintWriter = this.f$1;
                boolean z2 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.getClass();
                FooterViewRefactor.assertInLegacyMode();
                FooterViewRefactor.assertInLegacyMode();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationStackScrollLayout.mController;
                if (notificationStackScrollLayoutController != null) {
                    FooterViewRefactor.assertInLegacyMode();
                    z = notificationStackScrollLayoutController.hasNotifications(0, true);
                }
                indentingPrintWriter.println("showFooterView: " + notificationStackScrollLayout.shouldShowFooterView(z));
                DumpUtilsKt.withIncreasedIndent(indentingPrintWriter, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationStackScrollLayout notificationStackScrollLayout2 = NotificationStackScrollLayout.this;
                        IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                        boolean z3 = z;
                        boolean z4 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                        notificationStackScrollLayout2.getClass();
                        indentingPrintWriter2.println("showDismissView: " + z3);
                        DumpUtilsKt.withIncreasedIndent(indentingPrintWriter2, new NotificationStackScrollLayout$$ExternalSyntheticLambda8(notificationStackScrollLayout2, indentingPrintWriter2, 1));
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.println("showHistory: " + notificationStackScrollLayout2.mController.isHistoryEnabled());
                        indentingPrintWriter2.println();
                        indentingPrintWriter2.println("visibleNotificationCount: " + notificationStackScrollLayout2.mController.getVisibleNotificationCount());
                        indentingPrintWriter2.println("mIsCurrentUserSetup: " + notificationStackScrollLayout2.mIsCurrentUserSetup);
                        indentingPrintWriter2.println("onKeyguard: " + notificationStackScrollLayout2.onKeyguard());
                        indentingPrintWriter2.println("mUpcomingStatusBarState: " + notificationStackScrollLayout2.mUpcomingStatusBarState);
                        indentingPrintWriter2.println("mQsExpansionFraction: " + notificationStackScrollLayout2.mQsExpansionFraction);
                        indentingPrintWriter2.println("mQsFullScreen: " + notificationStackScrollLayout2.mQsFullScreen);
                        indentingPrintWriter2.println("mScreenOffAnimationController.shouldHideNotificationsFooter: " + notificationStackScrollLayout2.mScreenOffAnimationController.shouldHideNotificationsFooter());
                        indentingPrintWriter2.println("mIsRemoteInputActive: " + notificationStackScrollLayout2.mIsRemoteInputActive);
                    }
                });
                break;
            default:
                NotificationStackScrollLayout notificationStackScrollLayout2 = this.f$0;
                IndentingPrintWriter indentingPrintWriter2 = this.f$1;
                boolean z3 = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                StringBuilder sb = new StringBuilder("hasActiveClearableNotifications: ");
                NotificationStackScrollLayoutController notificationStackScrollLayoutController2 = notificationStackScrollLayout2.mController;
                notificationStackScrollLayoutController2.getClass();
                FooterViewRefactor.assertInLegacyMode();
                sb.append(notificationStackScrollLayoutController2.hasNotifications(0, true));
                indentingPrintWriter2.println(sb.toString());
                break;
        }
    }
}
