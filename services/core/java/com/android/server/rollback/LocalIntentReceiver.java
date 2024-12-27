package com.android.server.rollback;

import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LocalIntentReceiver {
    public final Consumer mConsumer;
    public final AnonymousClass1 mLocalSender =
            new IIntentSender
                    .Stub() { // from class: com.android.server.rollback.LocalIntentReceiver.1
                public final void send(
                        int i,
                        Intent intent,
                        String str,
                        IBinder iBinder,
                        IIntentReceiver iIntentReceiver,
                        String str2,
                        Bundle bundle) {
                    LocalIntentReceiver.this.mConsumer.accept(intent);
                }
            };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.rollback.LocalIntentReceiver$1] */
    public LocalIntentReceiver(Consumer consumer) {
        this.mConsumer = consumer;
    }
}
