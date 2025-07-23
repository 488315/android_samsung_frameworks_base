package androidx.compose.animation;

import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface AnimatedVisibilityScope {
    default Modifier animateEnterExit(Modifier modifier, final EnterTransition enterTransition, final ExitTransition exitTransition) {
        final String str = "animateEnterExit";
        return ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.animation.AnimatedVisibilityScope$animateEnterExit$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Modifier modifier2 = (Modifier) obj;
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(1840112047);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.animation.AnimatedVisibilityScope.animateEnterExit.<anonymous> (AnimatedVisibility.kt:654)");
                }
                Modifier then = modifier2.then(EnterExitTransitionKt.createModifier(AnimatedVisibilityScope.this.getTransition(), enterTransition, exitTransition, str, composerImpl, 0));
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return then;
            }
        });
    }

    Transition getTransition();
}
