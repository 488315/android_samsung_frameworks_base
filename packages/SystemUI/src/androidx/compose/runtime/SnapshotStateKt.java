package androidx.compose.runtime;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.SnapshotThreadLocal;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes.dex */
public abstract class SnapshotStateKt {
    public static final MutableState collectAsState(ReadonlyStateFlow readonlyStateFlow, Composer composer) {
        CoroutineContext coroutineContext = (CoroutineContext) ((ComposerImpl) composer).consume(SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.collectAsState (SnapshotFlow.kt:58)");
        }
        MutableState mutableStateCollectAsState = collectAsState(readonlyStateFlow, readonlyStateFlow.$$delegate_0.getValue(), coroutineContext, composer, 0, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableStateCollectAsState;
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
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        Unit unit = Unit.INSTANCE;
        boolean zChangedInstance = composerImpl.changedInstance(function2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$1$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
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
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            objRememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final MutableState collectAsState(Flow flow, Object obj, CoroutineContext coroutineContext, Composer composer, int i, int i2) {
        if ((i2 & 2) != 0) {
            coroutineContext = (CoroutineContext) ((ComposerImpl) composer).consume(SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext);
        } else {
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = SnapshotStateKt__SnapshotFlowKt.LocalCollectAsStateCoroutineContext;
        }
        CoroutineContext coroutineContext2 = coroutineContext;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.collectAsState (SnapshotFlow.kt:74)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        boolean zChangedInstance = composerImpl.changedInstance(coroutineContext2) | composerImpl.changedInstance(flow);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new SnapshotStateKt__SnapshotFlowKt$collectAsState$1$1(coroutineContext2, flow, null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        MutableState mutableStateProduceState = produceState(obj, flow, coroutineContext2, (Function2) objRememberedValue, composerImpl, ((i >> 3) & 14) | (i & 896));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableStateProduceState;
    }

    public static final MutableState produceState(Object obj, Object obj2, Function2 function2, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.produceState (ProduceState.kt:104)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        boolean zChangedInstance = composerImpl.changedInstance(function2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$2$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        EffectsKt.LaunchedEffect(composerImpl, obj2, (Function2) objRememberedValue2);
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
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        boolean zChangedInstance = composerImpl.changedInstance(function2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$3$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        EffectsKt.LaunchedEffect(obj2, obj3, (Function2) objRememberedValue2, composerImpl);
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
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = mutableStateOf$default(obj);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        boolean zChangedInstance = composerImpl.changedInstance(function2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new SnapshotStateKt__ProduceStateKt$produceState$5$1(function2, mutableState, null);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        EffectsKt.LaunchedEffect(objArrCopyOf, (Function2) objRememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }
}
