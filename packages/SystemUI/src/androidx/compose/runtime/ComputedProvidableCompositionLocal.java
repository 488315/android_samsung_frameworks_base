package androidx.compose.runtime;

import kotlin.KotlinNothingValueException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class ComputedProvidableCompositionLocal<T> extends ProvidableCompositionLocal<T> {
    public final ComputedValueHolder defaultValueHolder;

    public ComputedProvidableCompositionLocal(Function1 function1) {
        super(new Function0() { // from class: androidx.compose.runtime.ComputedProvidableCompositionLocal.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ComposerKt.composeRuntimeError("Unexpected call to default provider");
                throw new KotlinNothingValueException();
            }
        });
        this.defaultValueHolder = new ComputedValueHolder(function1);
    }

    public final ProvidedValue defaultProvidedValue$runtime_release(Object obj) {
        return new ProvidedValue(this, obj, obj == null, null, null, null, true);
    }

    @Override // androidx.compose.runtime.CompositionLocal
    public final ValueHolder getDefaultValueHolder$runtime_release() {
        return this.defaultValueHolder;
    }
}
