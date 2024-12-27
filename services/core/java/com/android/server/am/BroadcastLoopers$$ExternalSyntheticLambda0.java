package com.android.server.am;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastLoopers$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        Looper looper = (Looper) obj;
        final CountDownLatch countDownLatch = (CountDownLatch) obj2;
        switch (this.$r8$classId) {
            case 0:
                looper.getQueue().addIdleHandler(new MessageQueue.IdleHandler() { // from class: com.android.server.am.BroadcastLoopers$$ExternalSyntheticLambda2
                    @Override // android.os.MessageQueue.IdleHandler
                    public final boolean queueIdle() {
                        countDownLatch.countDown();
                        return false;
                    }
                });
                break;
            default:
                new Handler(looper).post(new Runnable() { // from class: com.android.server.am.BroadcastLoopers$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        countDownLatch.countDown();
                    }
                });
                break;
        }
    }
}
