package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.ColorVectorConverterKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.colorspace.ColorSpace;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class ColorPropertyValues extends PropertyValues<Color> {
    public ColorPropertyValues() {
        super(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00de  */
    @Override // androidx.compose.animation.graphics.vector.PropertyValues
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final State createState(final Transition transition, String str, int i, Composer composer, int i2) {
        Object currentState;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-2133734837);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.ColorPropertyValues.createState (Animator.kt:200)");
        }
        PropertyValues$createAnimationSpec$1 propertyValues$createAnimationSpec$1 = new PropertyValues$createAnimationSpec$1(this, i);
        int i3 = (i2 & 14) | ((i2 << 3) & 896);
        boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue()).booleanValue();
        composerImpl.startReplaceGroup(1880460593);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.ColorPropertyValues.createState.<anonymous> (Animator.kt:205)");
        }
        long j = zBooleanValue ? ((Color) ((Keyframe) CollectionsKt___CollectionsKt.last(((PropertyValuesHolderColor) ((Timestamp) CollectionsKt___CollectionsKt.last(this.timestamps)).holder).animatorKeyframes)).value).value : ((Color) ((Keyframe) CollectionsKt___CollectionsKt.first(((PropertyValuesHolderColor) ((Timestamp) CollectionsKt___CollectionsKt.first(this.timestamps)).holder).animatorKeyframes)).value).value;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        ColorSpace colorSpaceM461getColorSpaceimpl = Color.m461getColorSpaceimpl(j);
        boolean zChanged = composerImpl.changed(colorSpaceM461getColorSpaceimpl);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = (TwoWayConverter) ColorVectorConverterKt.ColorToVector.mo781invoke(colorSpaceM461getColorSpaceimpl);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        TwoWayConverter twoWayConverter = (TwoWayConverter) objRememberedValue;
        int i4 = ((i3 << 3) & 7168) | (i3 & 14);
        boolean zIsSeeking = transition.isSeeking();
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
        boolean zBooleanValue2 = ((Boolean) currentState).booleanValue();
        composerImpl.startReplaceGroup(1880460593);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.ColorPropertyValues.createState.<anonymous> (Animator.kt:205)");
        }
        long j2 = zBooleanValue2 ? ((Color) ((Keyframe) CollectionsKt___CollectionsKt.last(((PropertyValuesHolderColor) ((Timestamp) CollectionsKt___CollectionsKt.last(this.timestamps)).holder).animatorKeyframes)).value).value : ((Color) ((Keyframe) CollectionsKt___CollectionsKt.first(((PropertyValuesHolderColor) ((Timestamp) CollectionsKt___CollectionsKt.first(this.timestamps)).holder).animatorKeyframes)).value).value;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        Color colorM456boximpl = Color.m456boximpl(j2);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.graphics.vector.ColorPropertyValues$createState$$inlined$animateColor$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue();
                }
            });
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        boolean zBooleanValue3 = ((Boolean) ((State) objRememberedValue2).getValue()).booleanValue();
        composerImpl.startReplaceGroup(1880460593);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.ColorPropertyValues.createState.<anonymous> (Animator.kt:205)");
        }
        long j3 = zBooleanValue3 ? ((Color) ((Keyframe) CollectionsKt___CollectionsKt.last(((PropertyValuesHolderColor) ((Timestamp) CollectionsKt___CollectionsKt.last(this.timestamps)).holder).animatorKeyframes)).value).value : ((Color) ((Keyframe) CollectionsKt___CollectionsKt.first(((PropertyValuesHolderColor) ((Timestamp) CollectionsKt___CollectionsKt.first(this.timestamps)).holder).animatorKeyframes)).value).value;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        Color colorM456boximpl2 = Color.m456boximpl(j3);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (objRememberedValue3 == composer$Companion$Empty$1) {
            objRememberedValue3 = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.animation.graphics.vector.ColorPropertyValues$createState$$inlined$animateColor$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return transition.getSegment();
                }
            });
            composerImpl.updateRememberedValue(objRememberedValue3);
        }
        Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition, colorM456boximpl, colorM456boximpl2, (FiniteAnimationSpec) propertyValues$createAnimationSpec$1.invoke(((State) objRememberedValue3).getValue(), composerImpl, 0), twoWayConverter, str, composerImpl, (i4 & 14) | ((i4 << 6) & 458752));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return transitionAnimationStateCreateTransitionAnimation;
    }
}
