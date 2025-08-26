package androidx.compose.foundation;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class OverscrollConfiguration_androidKt {
    public static final DynamicProvidableCompositionLocal LocalOverscrollConfiguration = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.foundation.OverscrollConfiguration_androidKt$LocalOverscrollConfiguration$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new OverscrollConfiguration(0L, null, 3, null);
        }
    });
}
