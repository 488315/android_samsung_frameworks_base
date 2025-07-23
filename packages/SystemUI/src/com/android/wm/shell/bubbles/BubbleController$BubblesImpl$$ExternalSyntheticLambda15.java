package com.android.wm.shell.bubbles;

import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda15(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((BubblesManager$$ExternalSyntheticLambda3) this.f$0).accept(this.f$1);
                break;
            default:
                BubbleController.this.mBubblePositioner.mBubbleBarTopOnScreen = this.f$1;
                break;
        }
    }
}
