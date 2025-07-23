package androidx.lifecycle.compose;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LocalLifecycleOwnerKt {
    public static final StaticProvidableCompositionLocal LocalLifecycleOwner = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.lifecycle.compose.LocalLifecycleOwnerKt$LocalLifecycleOwner$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("CompositionLocal LocalLifecycleOwner not present");
        }
    });
}
