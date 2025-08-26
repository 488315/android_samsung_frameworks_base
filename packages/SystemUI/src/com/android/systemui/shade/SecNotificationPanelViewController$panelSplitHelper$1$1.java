package com.android.systemui.shade;

/* loaded from: classes3.dex */
public final class SecNotificationPanelViewController$panelSplitHelper$1$1 implements Runnable {
    public final /* synthetic */ SecNotificationPanelViewController this$0;

    public SecNotificationPanelViewController$panelSplitHelper$1$1(SecNotificationPanelViewController secNotificationPanelViewController) {
        this.this$0 = secNotificationPanelViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SecNotificationPanelViewController secNotificationPanelViewController = this.this$0;
        int i = SecNotificationPanelViewController.$r8$clinit;
        secNotificationPanelViewController.onPanelSplitIntercepted();
    }
}
