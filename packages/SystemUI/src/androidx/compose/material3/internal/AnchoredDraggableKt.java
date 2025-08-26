package androidx.compose.material3.internal;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.ui.Modifier;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
public abstract class AnchoredDraggableKt {

    /* renamed from: androidx.compose.material3.internal.AnchoredDraggableKt$animateTo$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function4 {
        final /* synthetic */ AnchoredDraggableState<Object> $this_animateTo;
        final /* synthetic */ float $velocity;
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        /* synthetic */ Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AnchoredDraggableState<Object> anchoredDraggableState, float f, Continuation continuation) {
            super(4, continuation);
            this.$this_animateTo = anchoredDraggableState;
            this.$velocity = f;
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_animateTo, this.$velocity, (Continuation) obj4);
            anonymousClass2.L$0 = (AnchoredDragScope) obj;
            anonymousClass2.L$1 = (DraggableAnchors) obj2;
            anonymousClass2.L$2 = obj3;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
                float fPositionOf = ((MapDraggableAnchors) ((DraggableAnchors) this.L$1)).positionOf(this.L$2);
                if (!Float.isNaN(fPositionOf)) {
                    final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
                    float floatValue = Float.isNaN(((SnapshotMutableFloatStateImpl) this.$this_animateTo.offset$delegate).getFloatValue()) ? 0.0f : ((SnapshotMutableFloatStateImpl) this.$this_animateTo.offset$delegate).getFloatValue();
                    ref$FloatRef.element = floatValue;
                    float f = this.$velocity;
                    AnimationSpec animationSpec = (AnimationSpec) this.$this_animateTo.animationSpec.invoke();
                    Function2 function2 = new Function2() { // from class: androidx.compose.material3.internal.AnchoredDraggableKt.animateTo.2.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            float fFloatValue = ((Number) obj2).floatValue();
                            float fFloatValue2 = ((Number) obj3).floatValue();
                            AnchoredDraggableState$anchoredDragScope$1 anchoredDraggableState$anchoredDragScope$1 = (AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope;
                            anchoredDraggableState$anchoredDragScope$1.getClass();
                            int i2 = AnchoredDraggableState.$r8$clinit;
                            AnchoredDraggableState anchoredDraggableState = anchoredDraggableState$anchoredDragScope$1.this$0;
                            ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).setFloatValue(fFloatValue);
                            ((SnapshotMutableFloatStateImpl) anchoredDraggableState.lastVelocity$delegate).setFloatValue(fFloatValue2);
                            ref$FloatRef.element = fFloatValue;
                            return Unit.INSTANCE;
                        }
                    };
                    this.L$0 = null;
                    this.L$1 = null;
                    this.label = 1;
                    if (SuspendAnimationKt.animate(floatValue, fPositionOf, f, animationSpec, function2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public static final DraggableAnchors DraggableAnchors(Function1 function1) {
        DraggableAnchorsConfig draggableAnchorsConfig = new DraggableAnchorsConfig();
        function1.mo781invoke(draggableAnchorsConfig);
        return new MapDraggableAnchors(draggableAnchorsConfig.anchors);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$restartable(Function0 function0, Function2 function2, ContinuationImpl continuationImpl) {
        AnchoredDraggableKt$restartable$1 anchoredDraggableKt$restartable$1;
        if (continuationImpl instanceof AnchoredDraggableKt$restartable$1) {
            anchoredDraggableKt$restartable$1 = (AnchoredDraggableKt$restartable$1) continuationImpl;
            int i = anchoredDraggableKt$restartable$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anchoredDraggableKt$restartable$1.label = i - Integer.MIN_VALUE;
            } else {
                anchoredDraggableKt$restartable$1 = new AnchoredDraggableKt$restartable$1(continuationImpl);
            }
        }
        Object obj = anchoredDraggableKt$restartable$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anchoredDraggableKt$restartable$1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                AnchoredDraggableKt$restartable$2 anchoredDraggableKt$restartable$2 = new AnchoredDraggableKt$restartable$2(function0, function2, null);
                anchoredDraggableKt$restartable$1.label = 1;
                if (CoroutineScopeKt.coroutineScope(anchoredDraggableKt$restartable$2, anchoredDraggableKt$restartable$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (AnchoredDragFinishedSignal unused) {
        }
        return Unit.INSTANCE;
    }

    public static final Object animateTo(AnchoredDraggableState anchoredDraggableState, Object obj, float f, Continuation continuation) {
        int i = AnchoredDraggableState.$r8$clinit;
        Object objAnchoredDrag = anchoredDraggableState.anchoredDrag(obj, MutatePriority.Default, new AnonymousClass2(anchoredDraggableState, f, null), (SuspendLambda) continuation);
        return objAnchoredDrag == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnchoredDrag : Unit.INSTANCE;
    }

    public static final Modifier draggableAnchors(Modifier modifier, AnchoredDraggableState anchoredDraggableState, Orientation orientation, Function2 function2) {
        return modifier.then(new DraggableAnchorsElement(anchoredDraggableState, function2, orientation));
    }
}
