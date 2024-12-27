package com.android.server.wm;

import android.animation.AnimatorSet;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public final /* synthetic */ class DragState$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DragState$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((AnimatorSet) obj).start();
                break;
            case 1:
                ((CompletableFuture) obj).complete(null);
                break;
            default:
                ((ArrayList) obj).forEach(new DragState$$ExternalSyntheticLambda8(0));
                break;
        }
    }
}
