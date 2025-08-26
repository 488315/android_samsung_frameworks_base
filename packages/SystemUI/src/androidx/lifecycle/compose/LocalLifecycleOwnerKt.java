package androidx.lifecycle.compose;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class LocalLifecycleOwnerKt {
    public static final StaticProvidableCompositionLocal LocalLifecycleOwner = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.lifecycle.compose.LocalLifecycleOwnerKt$LocalLifecycleOwner$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            throw new IllegalStateException("CompositionLocal LocalLifecycleOwner not present");
        }
    });
}
