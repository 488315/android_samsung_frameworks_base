package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.Snapshot;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FloatCompanionObject;

/* loaded from: classes.dex */
final class FloatPropertyValues extends PropertyValues<Float> {
    public FloatPropertyValues() {
        super(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    @Override // androidx.compose.animation.graphics.vector.PropertyValues
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final State createState(final Transition transition, String str, int i, Composer composer, int i2) {
        Object currentState;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(2006928772);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.FloatPropertyValues.createState (Animator.kt:172)");
        }
        PropertyValues$createAnimationSpec$1 propertyValues$createAnimationSpec$1 = new PropertyValues$createAnimationSpec$1(this, i);
        int i3 = (i2 & 14) | ((i2 << 3) & 896);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
        int i4 = ((i3 << 3) & 7168) | (i3 & 14);
        boolean zIsSeeking = transition.isSeeking();
        Composer.Companion companion = Composer.Companion;
        TransitionState transitionState = transition.transitionState;
        if (zIsSeeking) {
            composerImpl.startReplaceGroup(1888611722);
            composerImpl.end(false);
            currentState = transitionState.getCurrentState();
        } else {
            composerImpl.startReplaceGroup(1888357677);
            boolean z = (((i4 & 14) ^ 6) > 4 && composerImpl.changed(transition)) || (i4 & 6) == 4;
            currentState = composerImpl.rememberedValue();
            if (!z) {
                companion.getClass();
                if (currentState == Composer.Companion.Empty) {
                    Snapshot.Companion.getClass();
                    Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                    Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                    Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                    try {
                        Object currentState2 = transitionState.getCurrentState();
                        Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                        composerImpl.updateRememberedValue(currentState2);
                        currentState = currentState2;
                    } catch (Throwable th) {
                        Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                        throw th;
                    }
                }
                composerImpl.end(false);
            }
        }
        boolean zBooleanValue = ((Boolean) currentState).booleanValue();
        composerImpl.startReplaceGroup(-1743438372);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.FloatPropertyValues.createState.<anonymous> (Animator.kt:177)");
        }
        float fFloatValue = zBooleanValue ? ((Number) ((Keyframe) CollectionsKt___CollectionsKt.last(((PropertyValuesHolderFloat) ((Timestamp) CollectionsKt___CollectionsKt.last(this.timestamps)).holder).animatorKeyframes)).value).floatValue() : ((Number) ((Keyframe) CollectionsKt___CollectionsKt.first(((PropertyValuesHolderFloat) ((Timestamp) CollectionsKt___CollectionsKt.first(this.timestamps)).holder).animatorKeyframes)).value).floatValue();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        Float fValueOf = Float.valueOf(fFloatValue);
        Object objRememberedValue = composerImpl.rememberedValue();
        companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.graphics.vector.FloatPropertyValues$createState$$inlined$animateFloat$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue();
                }
            });
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        boolean zBooleanValue2 = ((Boolean) ((State) objRememberedValue).getValue()).booleanValue();
        composerImpl.startReplaceGroup(-1743438372);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.FloatPropertyValues.createState.<anonymous> (Animator.kt:177)");
        }
        float fFloatValue2 = zBooleanValue2 ? ((Number) ((Keyframe) CollectionsKt___CollectionsKt.last(((PropertyValuesHolderFloat) ((Timestamp) CollectionsKt___CollectionsKt.last(this.timestamps)).holder).animatorKeyframes)).value).floatValue() : ((Number) ((Keyframe) CollectionsKt___CollectionsKt.first(((PropertyValuesHolderFloat) ((Timestamp) CollectionsKt___CollectionsKt.first(this.timestamps)).holder).animatorKeyframes)).value).floatValue();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        Float fValueOf2 = Float.valueOf(fFloatValue2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.graphics.vector.FloatPropertyValues$createState$$inlined$animateFloat$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return transition.getSegment();
                }
            });
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition, fValueOf, fValueOf2, (FiniteAnimationSpec) propertyValues$createAnimationSpec$1.invoke(((State) objRememberedValue2).getValue(), composerImpl, 0), twoWayConverter, str, composerImpl, (i4 & 14) | ((i4 << 6) & 458752));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return transitionAnimationStateCreateTransitionAnimation;
    }
}
