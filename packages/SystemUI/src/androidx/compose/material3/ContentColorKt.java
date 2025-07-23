package androidx.compose.material3;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ContentColorKt {
    public static final DynamicProvidableCompositionLocal LocalContentColor = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.material3.ContentColorKt$LocalContentColor$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Color.Companion.getClass();
            return Color.m454boximpl(Color.Black);
        }
    });
}
