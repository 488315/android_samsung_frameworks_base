package com.android.server.blob;

import android.os.Bundle;
import android.os.RemoteCallback;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BlobStoreManagerService$Stub$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BlobStoreManagerService$Stub$$ExternalSyntheticLambda2(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((RemoteCallback) obj2).sendResult((Bundle) obj);
                break;
            default:
                ((AtomicLong) obj2).getAndAdd(((BlobStoreSession) obj).getSize());
                break;
        }
    }
}
