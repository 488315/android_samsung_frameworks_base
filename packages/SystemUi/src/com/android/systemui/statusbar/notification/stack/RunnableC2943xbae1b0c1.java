package com.android.systemui.statusbar.notification.stack;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda2 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC2943xbae1b0c1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RunnableC2943xbae1b0c1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((NotificationStackScrollLayoutController) this.f$0).updateFooter();
                break;
            default:
                ((NotificationStackScrollLayout) this.f$0).updateBackgroundDimming();
                break;
        }
    }
}
