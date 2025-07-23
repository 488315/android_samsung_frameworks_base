package androidx.compose.runtime;

import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerImpl.CompositionContextImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ComposablesKt {
    public static final int getCurrentCompositeKeyHash(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.<get-currentCompositeKeyHash> (Composables.kt:213)");
        }
        int i = ((ComposerImpl) composer).compoundKeyHash;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return i;
    }

    public static final RecomposeScopeImpl getCurrentRecomposeScope(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.<get-currentRecomposeScope> (Composables.kt:184)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        RecomposeScopeImpl currentRecomposeScope$runtime_release = composerImpl.getCurrentRecomposeScope$runtime_release();
        if (currentRecomposeScope$runtime_release == null) {
            throw new IllegalStateException("no recompose scope found");
        }
        composerImpl.getClass();
        currentRecomposeScope$runtime_release.flags |= 1;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return currentRecomposeScope$runtime_release;
    }

    public static final void invalidApplier() {
        throw new IllegalStateException("Invalid applier");
    }

    public static final ComposerImpl.CompositionContextImpl rememberCompositionContext(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.rememberCompositionContext (Composables.kt:461)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.getClass();
        composerImpl.startGroup(206, ComposerKt.reference);
        if (composerImpl.inserting) {
            SlotWriter.markGroup$default(composerImpl.writer);
        }
        Object nextSlot = composerImpl.nextSlot();
        ComposerImpl.CompositionContextHolder compositionContextHolder = nextSlot instanceof ComposerImpl.CompositionContextHolder ? (ComposerImpl.CompositionContextHolder) nextSlot : null;
        if (compositionContextHolder == null) {
            CompositionObserverHolder compositionObserverHolder = null;
            int i = composerImpl.compoundKeyHash;
            boolean z = composerImpl.forceRecomposeScopes;
            boolean z2 = composerImpl.sourceMarkersEnabled;
            ControlledComposition controlledComposition = composerImpl.composition;
            CompositionImpl compositionImpl = controlledComposition instanceof CompositionImpl ? (CompositionImpl) controlledComposition : null;
            if (compositionImpl != null) {
                compositionObserverHolder = compositionImpl.observerHolder;
            }
            compositionContextHolder = new ComposerImpl.CompositionContextHolder(composerImpl.new CompositionContextImpl(i, z, z2, compositionObserverHolder));
            composerImpl.updateValue(compositionContextHolder);
        }
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        ComposerImpl.CompositionContextImpl compositionContextImpl = compositionContextHolder.ref;
        ((SnapshotMutableStateImpl) compositionContextImpl.compositionLocalScope$delegate).setValue(currentCompositionLocalScope);
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return compositionContextImpl;
    }
}
