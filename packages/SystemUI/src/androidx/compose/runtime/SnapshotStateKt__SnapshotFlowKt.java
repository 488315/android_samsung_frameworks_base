package androidx.compose.runtime;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
abstract /* synthetic */ class SnapshotStateKt__SnapshotFlowKt {
    public static final StaticProvidableCompositionLocal LocalCollectAsStateCoroutineContext = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$LocalCollectAsStateCoroutineContext$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return EmptyCoroutineContext.INSTANCE;
        }
    });
}
