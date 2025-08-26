package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ProvidableCompositionLocal<T> extends CompositionLocal<T> {
    public ProvidableCompositionLocal(Function0 function0) {
        super(function0, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0034 A[PHI: r4
      0x0034: PHI (r4v2 androidx.compose.runtime.DynamicValueHolder) = (r4v11 androidx.compose.runtime.DynamicValueHolder), (r4v12 androidx.compose.runtime.DynamicValueHolder) binds: [B:21:0x0040, B:16:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.compose.runtime.CompositionLocal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ValueHolder updatedStateOf$runtime_release(ProvidedValue providedValue, ValueHolder valueHolder) {
        DynamicValueHolder dynamicValueHolder;
        DynamicValueHolder dynamicValueHolder2 = null;
        if (valueHolder instanceof DynamicValueHolder) {
            if (providedValue.isDynamic) {
                dynamicValueHolder2 = (DynamicValueHolder) valueHolder;
                dynamicValueHolder2.state.setValue(providedValue.getEffectiveValue$runtime_release());
            }
        } else if (valueHolder instanceof StaticValueHolder) {
            if ((providedValue.explicitNull || providedValue.providedValue != null) && !providedValue.isDynamic) {
                StaticValueHolder staticValueHolder = (StaticValueHolder) valueHolder;
                boolean zAreEqual = Intrinsics.areEqual(providedValue.getEffectiveValue$runtime_release(), staticValueHolder.value);
                dynamicValueHolder = staticValueHolder;
                if (zAreEqual) {
                    dynamicValueHolder2 = dynamicValueHolder;
                }
            }
        } else if (valueHolder instanceof ComputedValueHolder) {
            Function1 function1 = providedValue.compute;
            ComputedValueHolder computedValueHolder = (ComputedValueHolder) valueHolder;
            Function1 function12 = computedValueHolder.compute;
            dynamicValueHolder = computedValueHolder;
            if (function1 == function12) {
            }
        }
        if (dynamicValueHolder2 != null) {
            return dynamicValueHolder2;
        }
        boolean z = providedValue.isDynamic;
        MutableState parcelableSnapshotMutableState = providedValue.state;
        if (!z) {
            Function1 function13 = providedValue.compute;
            return function13 != null ? new ComputedValueHolder(function13) : parcelableSnapshotMutableState != null ? new DynamicValueHolder(parcelableSnapshotMutableState) : new StaticValueHolder(providedValue.getEffectiveValue$runtime_release());
        }
        if (parcelableSnapshotMutableState == null) {
            SnapshotMutationPolicy snapshotMutationPolicy = providedValue.mutationPolicy;
            if (snapshotMutationPolicy == null) {
                snapshotMutationPolicy = StructuralEqualityPolicy.INSTANCE;
            }
            parcelableSnapshotMutableState = new ParcelableSnapshotMutableState(providedValue.providedValue, snapshotMutationPolicy);
        }
        return new DynamicValueHolder(parcelableSnapshotMutableState);
    }
}
