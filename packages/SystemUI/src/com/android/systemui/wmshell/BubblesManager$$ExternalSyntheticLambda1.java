package com.android.systemui.wmshell;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda1 {
    public final /* synthetic */ Executor f$0;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda1(Executor executor) {
        this.f$0 = executor;
    }

    public final void sendEventCDLog(String str, String str2, String str3) {
        this.f$0.execute(new BubblesManager$$ExternalSyntheticLambda2(str, str2, 0, str3));
    }
}
