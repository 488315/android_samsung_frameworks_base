package com.android.systemui.wmshell;

import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda1 {
    public final /* synthetic */ Executor f$0;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda1(Executor executor) {
        this.f$0 = executor;
    }

    public final void sendEventCDLog(String str, String str2, String str3) {
        this.f$0.execute(new BubblesManager$$ExternalSyntheticLambda2(str, str2, 0, str3));
    }
}
