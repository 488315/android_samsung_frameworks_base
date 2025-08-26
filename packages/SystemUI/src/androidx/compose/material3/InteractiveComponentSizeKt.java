package androidx.compose.material3;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class InteractiveComponentSizeKt {
    public static final StaticProvidableCompositionLocal LocalMinimumInteractiveComponentSize;

    static {
        new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.InteractiveComponentSizeKt$LocalMinimumInteractiveComponentEnforcement$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.TRUE;
            }
        });
        LocalMinimumInteractiveComponentSize = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.InteractiveComponentSizeKt$LocalMinimumInteractiveComponentSize$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Dp.m837boximpl(48);
            }
        });
    }
}
