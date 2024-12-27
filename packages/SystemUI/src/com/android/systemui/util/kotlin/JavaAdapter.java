package com.android.systemui.util.kotlin;

import java.util.function.Consumer;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class JavaAdapter {
    public static final int $stable = 8;
    private final CoroutineScope scope;

    public JavaAdapter(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
    }

    public final <T> Job alwaysCollectFlow(Flow flow, Consumer<T> consumer) {
        return BuildersKt.launch$default(this.scope, null, null, new JavaAdapter$alwaysCollectFlow$1(flow, consumer, null), 3);
    }
}
