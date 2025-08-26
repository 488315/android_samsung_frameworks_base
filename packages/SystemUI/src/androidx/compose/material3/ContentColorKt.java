package androidx.compose.material3;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class ContentColorKt {
    public static final DynamicProvidableCompositionLocal LocalContentColor = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.material3.ContentColorKt$LocalContentColor$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Color.Companion.getClass();
            return Color.m456boximpl(Color.Black);
        }
    });
}
