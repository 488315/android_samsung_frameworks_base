package androidx.compose.foundation.text;

import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class AutofillHighlightKt {
    public static final DynamicProvidableCompositionLocal LocalAutofillHighlightColor = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.foundation.text.AutofillHighlightKt$LocalAutofillHighlightColor$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Color.m456boximpl(ColorKt.Color(1308617531));
        }
    });
}
