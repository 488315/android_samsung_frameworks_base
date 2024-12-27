package android.permission;

import com.android.internal.infra.AndroidFuture;

import java.util.function.IntConsumer;

public final /* synthetic */ class PermissionControllerService$1$$ExternalSyntheticLambda4
        implements IntConsumer {
    public final /* synthetic */ AndroidFuture f$0;

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.f$0.complete(Integer.valueOf(i));
    }
}
