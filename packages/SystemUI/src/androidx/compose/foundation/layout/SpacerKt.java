package androidx.compose.foundation.layout;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SpacerKt {
    public static final void Spacer(Composer composer, Modifier modifier) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.Spacer (Spacer.kt:37)");
        }
        SpacerMeasurePolicy spacerMeasurePolicy = SpacerMeasurePolicy.INSTANCE;
        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer, modifier);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (composerImpl.applier == null) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m336setimpl(composer, spacerMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m336setimpl(composer, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Updater.m336setimpl(composer, materializeModifier, ComposeUiNode.Companion.SetModifier);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
        }
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }
}
