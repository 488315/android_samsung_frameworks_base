package androidx.lifecycle.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        MutableState collectAsStateWithLifecycle = collectAsStateWithLifecycle(flow, obj, lifecycleOwner.getLifecycle(), state, emptyCoroutineContext, composerImpl, (i & 14) | (((i >> 3) & 8) << 3) | (i & 112) | (i & 7168) | (i & 57344));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return collectAsStateWithLifecycle;
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
        MutableState collectAsStateWithLifecycle = collectAsStateWithLifecycle(stateFlow, stateFlow.getValue(), lifecycleOwner.getLifecycle(), state, emptyCoroutineContext, composerImpl, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return collectAsStateWithLifecycle;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0053, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.runtime.MutableState collectAsStateWithLifecycle(kotlinx.coroutines.flow.Flow r11, java.lang.Object r12, androidx.lifecycle.Lifecycle r13, androidx.lifecycle.Lifecycle.State r14, kotlin.coroutines.CoroutineContext r15, androidx.compose.runtime.Composer r16, int r17) {
        /*
            r0 = r17
            r6 = r16
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            r5 = 1977777920(0x75e27f00, float:5.742358E32)
            r6.startReplaceableGroup(r5)
            boolean r5 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r5 == 0) goto L17
            java.lang.String r5 = "androidx.lifecycle.compose.collectAsStateWithLifecycle (FlowExt.kt:169)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r5)
        L17:
            java.lang.Object[] r7 = new java.lang.Object[]{r11, r13, r14, r15}
            r5 = -669833536(0xffffffffd81326c0, float:-6.4717856E14)
            r6.startReplaceableGroup(r5)
            boolean r5 = r6.changedInstance(r13)
            r8 = r0 & 7168(0x1c00, float:1.0045E-41)
            r8 = r8 ^ 3072(0xc00, float:4.305E-42)
            r9 = 2048(0x800, float:2.87E-42)
            r10 = 0
            if (r8 <= r9) goto L34
            boolean r8 = r6.changed(r14)
            if (r8 != 0) goto L38
        L34:
            r0 = r0 & 3072(0xc00, float:4.305E-42)
            if (r0 != r9) goto L3a
        L38:
            r0 = 1
            goto L3b
        L3a:
            r0 = r10
        L3b:
            r0 = r0 | r5
            boolean r5 = r6.changedInstance(r15)
            r0 = r0 | r5
            boolean r5 = r6.changedInstance(r11)
            r0 = r0 | r5
            java.lang.Object r5 = r6.rememberedValue()
            if (r0 != 0) goto L55
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r0) goto L63
        L55:
            androidx.lifecycle.compose.FlowExtKt$collectAsStateWithLifecycle$1$1 r0 = new androidx.lifecycle.compose.FlowExtKt$collectAsStateWithLifecycle$1$1
            r5 = 0
            r4 = r11
            r1 = r13
            r2 = r14
            r3 = r15
            r0.<init>(r1, r2, r3, r4, r5)
            r6.updateRememberedValue(r0)
            r5 = r0
        L63:
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            r6.end(r10)
            androidx.compose.runtime.MutableState r0 = androidx.compose.runtime.SnapshotStateKt.produceState(r12, r7, r5, r6)
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L75
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L75:
            r6.end(r10)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(kotlinx.coroutines.flow.Flow, java.lang.Object, androidx.lifecycle.Lifecycle, androidx.lifecycle.Lifecycle$State, kotlin.coroutines.CoroutineContext, androidx.compose.runtime.Composer, int):androidx.compose.runtime.MutableState");
    }
}
