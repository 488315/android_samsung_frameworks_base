package androidx.room;

import androidx.room.IMultiInstanceInvalidationCallback;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
public final class MultiInstanceInvalidationClient$invalidationCallback$1 extends IMultiInstanceInvalidationCallback.Stub {
    public final /* synthetic */ MultiInstanceInvalidationClient this$0;

    public MultiInstanceInvalidationClient$invalidationCallback$1(MultiInstanceInvalidationClient multiInstanceInvalidationClient) {
        this.this$0 = multiInstanceInvalidationClient;
    }

    @Override // androidx.room.IMultiInstanceInvalidationCallback
    public final void onInvalidation(String[] strArr) {
        MultiInstanceInvalidationClient multiInstanceInvalidationClient = this.this$0;
        BuildersKt.launch$default(multiInstanceInvalidationClient.coroutineScope, null, null, new MultiInstanceInvalidationClient$invalidationCallback$1$onInvalidation$1(strArr, multiInstanceInvalidationClient, null), 3);
    }
}
