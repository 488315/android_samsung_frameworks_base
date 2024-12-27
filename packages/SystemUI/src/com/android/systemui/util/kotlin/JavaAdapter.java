package com.android.systemui.util.kotlin;

import java.util.function.Consumer;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;

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
