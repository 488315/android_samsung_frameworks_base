package com.android.server.pm;

import java.util.concurrent.CompletableFuture;

public final /* synthetic */ class GentleUpdateHelper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ GentleUpdateHelper$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((GentleUpdateHelper) obj).runIdleJob();
                break;
            case 1:
                int i2 = GentleUpdateHelper.Service.$r8$clinit;
                int i3 = GentleUpdateHelper.$r8$clinit;
                ((GentleUpdateHelper) obj).runIdleJob();
                break;
            default:
                ((CompletableFuture) obj).complete(Boolean.FALSE);
                break;
        }
    }
}
