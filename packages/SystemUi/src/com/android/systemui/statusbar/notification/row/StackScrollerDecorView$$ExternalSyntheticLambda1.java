package com.android.systemui.statusbar.notification.row;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StackScrollerDecorView$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StackScrollerDecorView$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StackScrollerDecorView stackScrollerDecorView = (StackScrollerDecorView) this.f$0;
                stackScrollerDecorView.mSecondaryAnimating = false;
                if (stackScrollerDecorView.mSecondaryView != null && stackScrollerDecorView.getVisibility() != 8 && stackScrollerDecorView.mSecondaryView.getVisibility() != 8 && !stackScrollerDecorView.mIsSecondaryVisible) {
                    stackScrollerDecorView.mSecondaryView.setVisibility(8);
                    break;
                }
                break;
            default:
                Runnable runnable = (Runnable) this.f$0;
                int i = StackScrollerDecorView.$r8$clinit;
                runnable.run();
                break;
        }
    }
}
