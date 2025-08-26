package androidx.compose.animation.core;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.collection.MutableVector;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class InfiniteTransition {
    public final MutableVector _animations = new MutableVector(new TransitionAnimationState[16], 0);
    public final MutableState refreshChildNeeded$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    public long startTimeNanos = Long.MIN_VALUE;
    public final MutableState isRunning$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);

    public final class TransitionAnimationState<T, V extends AnimationVector> implements State<T> {
        public TargetBasedAnimation animation;
        public Object initialValue;
        public boolean isFinished;
        public long playTimeNanosOffset;
        public boolean startOnTheNextFrame;
        public Object targetValue;
        public final TwoWayConverter typeConverter;
        public final MutableState value$delegate;

        public TransitionAnimationState(T t, T t2, TwoWayConverter<T, V> twoWayConverter, AnimationSpec<T> animationSpec, String str) {
            this.initialValue = t;
            this.targetValue = t2;
            this.typeConverter = twoWayConverter;
            this.value$delegate = SnapshotStateKt.mutableStateOf$default(t);
            this.animation = new TargetBasedAnimation(animationSpec, twoWayConverter, this.initialValue, this.targetValue, (AnimationVector) null, 16, (DefaultConstructorMarker) null);
        }

        @Override // androidx.compose.runtime.State
        public final Object getValue() {
            return ((SnapshotMutableStateImpl) this.value$delegate).getValue();
        }
    }

    public InfiniteTransition(String str) {
    }

    public final void run$animation_core(final int i, Composer composer) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-318043801);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(this) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 3) != 2)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.animation.core.InfiniteTransition.run (InfiniteTransition.kt:164)");
            }
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            if (((Boolean) ((SnapshotMutableStateImpl) this.isRunning$delegate).getValue()).booleanValue() || ((Boolean) ((SnapshotMutableStateImpl) this.refreshChildNeeded$delegate).getValue()).booleanValue()) {
                composerImpl.startReplaceGroup(1719883733);
                boolean zChangedInstance = composerImpl.changedInstance(this);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new InfiniteTransition$run$1$1(mutableState, this, null);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                EffectsKt.LaunchedEffect(composerImpl, this, (Function2) objRememberedValue2);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1721270456);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.animation.core.InfiniteTransition$run$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    this.$tmp2_rcvr.run$animation_core(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
