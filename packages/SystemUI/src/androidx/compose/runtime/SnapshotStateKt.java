package androidx.compose.runtime;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes.dex */
public abstract class SnapshotStateKt {
    public static final MutableState collectAsState(ReadonlyStateFlow readonlyStateFlow, Composer composer) {
        CoroutineContext coroutineContext = (CoroutineContext) ((ComposerImpl) composer).consume(SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.collectAsState (SnapshotFlow.kt:58)");
        }
        MutableState collectAsState = collectAsState(readonlyStateFlow, readonlyStateFlow.$$delegate_0.getValue(), coroutineContext, composer, 0, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return collectAsState;
    }

    public static final MutableVector derivedStateObservers() {
        SnapshotThreadLocal snapshotThreadLocal = SnapshotStateKt__DerivedStateKt.derivedStateObservers;
        MutableVector mutableVector = (MutableVector) snapshotThreadLocal.get();
        if (mutableVector != null) {
            return mutableVector;
        }
        MutableVector mutableVector2 = new MutableVector(new DerivedStateObserver[0], 0);
        snapshotThreadLocal.set(mutableVector2);
        return mutableVector2;
    }

    public static final State derivedStateOf(SnapshotMutationPolicy snapshotMutationPolicy, Function0 function0) {
        SnapshotThreadLocal snapshotThreadLocal = SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel;
        return new DerivedSnapshotState(function0, snapshotMutationPolicy);
    }

    public static final MutableState mutableStateOf(Object obj, SnapshotMutationPolicy snapshotMutationPolicy) {
        return new ParcelableSnapshotMutableState(obj, snapshotMutationPolicy);
    }

    public static MutableState mutableStateOf$default(Object obj) {
        return new ParcelableSnapshotMutableState(obj, StructuralEqualityPolicy.INSTANCE);
    }

    public static final SnapshotMutationPolicy neverEqualPolicy() {
        return NeverEqualPolicy.INSTANCE;
    }

    public static final MutableState produceState(Composer composer, Object obj, Function2 function2) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.produceState (ProduceState.kt:74)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        Unit unit = Unit.INSTANCE;
        boolean changedInstance = composerImpl.changedInstance(function2);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$1$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) rememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }

    public static final SnapshotMutationPolicy referentialEqualityPolicy() {
        return ReferentialEqualityPolicy.INSTANCE;
    }

    public static final MutableState rememberUpdatedState(Object obj, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.rememberUpdatedState (SnapshotState.kt:329)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        mutableState.setValue(obj);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }

    public static final SafeFlow snapshotFlow(Function0 function0) {
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext;
        return new SafeFlow(new SnapshotStateKt__SnapshotFlowKt$snapshotFlow$1(function0, null));
    }

    public static final SnapshotMutationPolicy structuralEqualityPolicy() {
        return StructuralEqualityPolicy.INSTANCE;
    }

    public static final State derivedStateOf(Function0 function0) {
        SnapshotThreadLocal snapshotThreadLocal = SnapshotStateKt__DerivedStateKt.calculationBlockNestedLevel;
        return new DerivedSnapshotState(function0, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0038, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.runtime.MutableState collectAsState(kotlinx.coroutines.flow.Flow r6, java.lang.Object r7, kotlin.coroutines.CoroutineContext r8, androidx.compose.runtime.Composer r9, int r10, int r11) {
        /*
            r11 = r11 & 2
            if (r11 == 0) goto L11
            androidx.compose.runtime.StaticProvidableCompositionLocal r8 = androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext
            r11 = r9
            androidx.compose.runtime.ComposerImpl r11 = (androidx.compose.runtime.ComposerImpl) r11
            java.lang.Object r8 = r11.consume(r8)
            kotlin.coroutines.CoroutineContext r8 = (kotlin.coroutines.CoroutineContext) r8
        Lf:
            r2 = r8
            goto L14
        L11:
            androidx.compose.runtime.StaticProvidableCompositionLocal r11 = androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext
            goto Lf
        L14:
            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r8 == 0) goto L1f
            java.lang.String r8 = "androidx.compose.runtime.collectAsState (SnapshotFlow.kt:74)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r8)
        L1f:
            r4 = r9
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            boolean r8 = r4.changedInstance(r2)
            boolean r9 = r4.changedInstance(r6)
            r8 = r8 | r9
            java.lang.Object r9 = r4.rememberedValue()
            if (r8 != 0) goto L3a
            androidx.compose.runtime.Composer$Companion r8 = androidx.compose.runtime.Composer.Companion
            r8.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r8 = androidx.compose.runtime.Composer.Companion.Empty
            if (r9 != r8) goto L43
        L3a:
            androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1 r9 = new androidx.compose.runtime.SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1
            r8 = 0
            r9.<init>(r2, r6, r8)
            r4.updateRememberedValue(r9)
        L43:
            r3 = r9
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
            int r8 = r10 >> 3
            r8 = r8 & 14
            r9 = r10 & 896(0x380, float:1.256E-42)
            r5 = r8 | r9
            r1 = r6
            r0 = r7
            androidx.compose.runtime.MutableState r6 = produceState(r0, r1, r2, r3, r4, r5)
            boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r7 == 0) goto L5d
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L5d:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.SnapshotStateKt.collectAsState(kotlinx.coroutines.flow.Flow, java.lang.Object, kotlin.coroutines.CoroutineContext, androidx.compose.runtime.Composer, int, int):androidx.compose.runtime.MutableState");
    }

    public static final MutableState produceState(Object obj, Object obj2, Function2 function2, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.produceState (ProduceState.kt:104)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        boolean changedInstance = composerImpl.changedInstance(function2);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$2$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        EffectsKt.LaunchedEffect(composerImpl, obj2, (Function2) rememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }

    public static final MutableState produceState(Object obj, Object obj2, Object obj3, Function2 function2, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.produceState (ProduceState.kt:135)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        boolean changedInstance = composerImpl.changedInstance(function2);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$3$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        EffectsKt.LaunchedEffect(obj2, obj3, (Function2) rememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }

    public static final MutableState produceState(Object obj, Object[] objArr, Function2 function2, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.produceState (ProduceState.kt:197)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
        boolean changedInstance = composerImpl.changedInstance(function2);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$5$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        EffectsKt.LaunchedEffect(copyOf, (Function2) rememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }
}
