package androidx.lifecycle.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes.dex */
public abstract class FlowExtKt {
    public static final MutableState collectAsStateWithLifecycle(Flow flow, Object obj, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(-1485997211);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
        Lifecycle.State state = Lifecycle.State.STARTED;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.lifecycle.compose.collectAsStateWithLifecycle (FlowExt.kt:133)");
        }
        MutableState mutableStateCollectAsStateWithLifecycle = collectAsStateWithLifecycle(flow, obj, lifecycleOwner.getLifecycle(), state, emptyCoroutineContext, composerImpl, (i & 14) | (((i >> 3) & 8) << 3) | (i & 112) | (i & 7168) | (i & 57344));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableStateCollectAsStateWithLifecycle;
    }

    public static final MutableState collectAsStateWithLifecycle(StateFlow stateFlow, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(743249048);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) composerImpl.consume(LocalLifecycleOwnerKt.LocalLifecycleOwner);
        Lifecycle.State state = Lifecycle.State.STARTED;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.lifecycle.compose.collectAsStateWithLifecycle (FlowExt.kt:60)");
        }
        MutableState mutableStateCollectAsStateWithLifecycle = collectAsStateWithLifecycle(stateFlow, stateFlow.getValue(), lifecycleOwner.getLifecycle(), state, emptyCoroutineContext, composerImpl, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableStateCollectAsStateWithLifecycle;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final MutableState collectAsStateWithLifecycle(Flow flow, Object obj, Lifecycle lifecycle, Lifecycle.State state, CoroutineContext coroutineContext, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceableGroup(1977777920);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.lifecycle.compose.collectAsStateWithLifecycle (FlowExt.kt:169)");
        }
        Object[] objArr = {flow, lifecycle, state, coroutineContext};
        composerImpl.startReplaceableGroup(-669833536);
        boolean zChangedInstance = ((((i & 7168) ^ 3072) > 2048 && composerImpl.changed(state)) || (i & 3072) == 2048) | composerImpl.changedInstance(lifecycle) | composerImpl.changedInstance(coroutineContext) | composerImpl.changedInstance(flow);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                FlowExtKt$collectAsStateWithLifecycle$1$1 flowExtKt$collectAsStateWithLifecycle$1$1 = new FlowExtKt$collectAsStateWithLifecycle$1$1(lifecycle, state, coroutineContext, flow, null);
                composerImpl.updateRememberedValue(flowExtKt$collectAsStateWithLifecycle$1$1);
                objRememberedValue = flowExtKt$collectAsStateWithLifecycle$1$1;
            }
        }
        composerImpl.end(false);
        MutableState mutableStateProduceState = SnapshotStateKt.produceState(obj, objArr, (Function2) objRememberedValue, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableStateProduceState;
    }
}
