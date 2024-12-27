package com.android.server.enterprise.application;

import com.android.server.enterprise.application.ApplicationPolicy;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ApplicationPolicy$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ApplicationPolicy$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((Map) obj2).put(((ApplicationPolicy.AppControlStateEnum) obj).getAdminMapKey(), new TreeSet());
                break;
            default:
                CompletableFuture completableFuture = (CompletableFuture) obj2;
                if (!((Boolean) obj).booleanValue()) {
                    completableFuture.completeExceptionally(new RuntimeException());
                    break;
                } else {
                    completableFuture.complete(null);
                    break;
                }
        }
    }
}
