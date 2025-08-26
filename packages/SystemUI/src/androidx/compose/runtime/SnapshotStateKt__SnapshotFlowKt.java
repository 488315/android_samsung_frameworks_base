package androidx.compose.runtime;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
abstract /* synthetic */ class SnapshotStateKt__SnapshotFlowKt {
    public static final StaticProvidableCompositionLocal LocalCollectAsStateCoroutineContext = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$LocalCollectAsStateCoroutineContext$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return EmptyCoroutineContext.INSTANCE;
        }
    });
}
