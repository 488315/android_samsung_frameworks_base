package com.android.wm.shell.bubbles;

import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$BubblesImpl$$ExternalSyntheticLambda11 implements IntConsumer {
    public final /* synthetic */ Executor f$0;
    public final /* synthetic */ BubblesManager$$ExternalSyntheticLambda3 f$1;

    public /* synthetic */ BubbleController$BubblesImpl$$ExternalSyntheticLambda11(Executor executor, BubblesManager$$ExternalSyntheticLambda3 bubblesManager$$ExternalSyntheticLambda3) {
        this.f$0 = executor;
        this.f$1 = bubblesManager$$ExternalSyntheticLambda3;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.f$0.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda15(this.f$1, i, 0));
    }
}
