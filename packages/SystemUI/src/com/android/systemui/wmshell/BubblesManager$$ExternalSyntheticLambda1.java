package com.android.systemui.wmshell;

import java.util.concurrent.Executor;

public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda1 {
    public final /* synthetic */ Executor f$0;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda1(Executor executor) {
        this.f$0 = executor;
    }

    public final void sendEventCDLog(String str, String str2, String str3) {
        this.f$0.execute(new BubblesManager$$ExternalSyntheticLambda2(str, str2, 0, str3));
    }
}
