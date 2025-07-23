package androidx.compose.runtime;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StaticProvidableCompositionLocal<T> extends ProvidableCompositionLocal<T> {
    public StaticProvidableCompositionLocal(Function0 function0) {
        super(function0);
    }

    public final ProvidedValue defaultProvidedValue$runtime_release(Object obj) {
        return new ProvidedValue(this, obj, obj == null, null, null, null, false);
    }
}
