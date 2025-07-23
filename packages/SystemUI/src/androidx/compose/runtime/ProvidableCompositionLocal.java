package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ProvidableCompositionLocal<T> extends CompositionLocal<T> {
    public ProvidableCompositionLocal(Function0 function0) {
        super(function0, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x0032, code lost:
    
        if (r2 != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0034, code lost:
    
        r0 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0040, code lost:
    
        if (r2 == r1) goto L17;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.runtime.CompositionLocal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.runtime.ValueHolder updatedStateOf$runtime_release(androidx.compose.runtime.ProvidedValue r3, androidx.compose.runtime.ValueHolder r4) {
        /*
            r2 = this;
            boolean r2 = r4 instanceof androidx.compose.runtime.DynamicValueHolder
            r0 = 0
            if (r2 == 0) goto L16
            boolean r2 = r3.isDynamic
            if (r2 == 0) goto L43
            r0 = r4
            androidx.compose.runtime.DynamicValueHolder r0 = (androidx.compose.runtime.DynamicValueHolder) r0
            androidx.compose.runtime.MutableState r2 = r0.state
            java.lang.Object r4 = r3.getEffectiveValue$runtime_release()
            r2.setValue(r4)
            goto L43
        L16:
            boolean r2 = r4 instanceof androidx.compose.runtime.StaticValueHolder
            if (r2 == 0) goto L36
            boolean r2 = r3.explicitNull
            if (r2 != 0) goto L22
            java.lang.Object r2 = r3.providedValue
            if (r2 == 0) goto L43
        L22:
            boolean r2 = r3.isDynamic
            if (r2 != 0) goto L43
            java.lang.Object r2 = r3.getEffectiveValue$runtime_release()
            androidx.compose.runtime.StaticValueHolder r4 = (androidx.compose.runtime.StaticValueHolder) r4
            java.lang.Object r1 = r4.value
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r1)
            if (r2 == 0) goto L43
        L34:
            r0 = r4
            goto L43
        L36:
            boolean r2 = r4 instanceof androidx.compose.runtime.ComputedValueHolder
            if (r2 == 0) goto L43
            kotlin.jvm.functions.Function1 r2 = r3.compute
            androidx.compose.runtime.ComputedValueHolder r4 = (androidx.compose.runtime.ComputedValueHolder) r4
            kotlin.jvm.functions.Function1 r1 = r4.compute
            if (r2 != r1) goto L43
            goto L34
        L43:
            if (r0 != 0) goto L7d
            boolean r2 = r3.isDynamic
            androidx.compose.runtime.MutableState r4 = r3.state
            if (r2 == 0) goto L61
            androidx.compose.runtime.DynamicValueHolder r2 = new androidx.compose.runtime.DynamicValueHolder
            if (r4 != 0) goto L5d
            androidx.compose.runtime.SnapshotMutationPolicy r4 = r3.mutationPolicy
            if (r4 != 0) goto L55
            androidx.compose.runtime.StructuralEqualityPolicy r4 = androidx.compose.runtime.StructuralEqualityPolicy.INSTANCE
        L55:
            androidx.compose.runtime.ParcelableSnapshotMutableState r0 = new androidx.compose.runtime.ParcelableSnapshotMutableState
            java.lang.Object r3 = r3.providedValue
            r0.<init>(r3, r4)
            r4 = r0
        L5d:
            r2.<init>(r4)
            return r2
        L61:
            kotlin.jvm.functions.Function1 r2 = r3.compute
            if (r2 == 0) goto L6b
            androidx.compose.runtime.ComputedValueHolder r3 = new androidx.compose.runtime.ComputedValueHolder
            r3.<init>(r2)
            return r3
        L6b:
            if (r4 == 0) goto L73
            androidx.compose.runtime.DynamicValueHolder r2 = new androidx.compose.runtime.DynamicValueHolder
            r2.<init>(r4)
            return r2
        L73:
            androidx.compose.runtime.StaticValueHolder r2 = new androidx.compose.runtime.StaticValueHolder
            java.lang.Object r3 = r3.getEffectiveValue$runtime_release()
            r2.<init>(r3)
            return r2
        L7d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.runtime.ProvidableCompositionLocal.updatedStateOf$runtime_release(androidx.compose.runtime.ProvidedValue, androidx.compose.runtime.ValueHolder):androidx.compose.runtime.ValueHolder");
    }
}
