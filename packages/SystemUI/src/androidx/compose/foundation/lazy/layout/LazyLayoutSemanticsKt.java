package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.Modifier;
import kotlin.reflect.KProperty0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutSemanticsKt {
    public static final Modifier lazyLayoutSemantics(Modifier modifier, KProperty0 kProperty0, LazyLayoutSemanticState lazyLayoutSemanticState, Orientation orientation, boolean z, boolean z2) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.lazyLayoutSemantics (LazyLayoutSemantics.kt:48)");
        }
        Modifier then = modifier.then(new LazyLayoutSemanticsModifier(kProperty0, lazyLayoutSemanticState, orientation, z, z2));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return then;
    }
}
