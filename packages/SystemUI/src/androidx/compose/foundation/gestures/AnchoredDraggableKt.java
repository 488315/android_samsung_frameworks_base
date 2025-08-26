package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.DecayAnimationSpecKt;
import androidx.compose.animation.core.FloatDecayAnimationSpec;
import androidx.compose.animation.core.SuspendAnimationKt;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.qs.panels.ui.compose.selection.QSDragAnchor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt__ArraysJVMKt;
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
    public static final Function1 AlwaysDrag = new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$AlwaysDrag$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
            return Boolean.TRUE;
        }
    };
    public static final Function1 GetOrNan = AnchoredDraggableKt$GetOrNan$1.INSTANCE;
    public static final DecayAnimationSpec NoOpDecayAnimationSpec;

    /* renamed from: androidx.compose.foundation.gestures.AnchoredDraggableKt$snapTo$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function4 {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        /* synthetic */ Object L$2;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(4, continuation);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2((Continuation) obj4);
            anonymousClass2.L$0 = (AnchoredDragScope) obj;
            anonymousClass2.L$1 = (DraggableAnchors) obj2;
            anonymousClass2.L$2 = obj3;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AnchoredDragScope anchoredDragScope = (AnchoredDragScope) this.L$0;
            float fPositionOf = ((DefaultDraggableAnchors) ((DraggableAnchors) this.L$1)).positionOf(this.L$2);
            if (!Float.isNaN(fPositionOf)) {
                ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope).dragTo(fPositionOf, 0.0f);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        Dp.Companion companion = Dp.Companion;
        NoOpDecayAnimationSpec = DecayAnimationSpecKt.generateDecayAnimationSpec(new FloatDecayAnimationSpec() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$NoOpDecayAnimationSpec$1
            @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
            public final float getAbsVelocityThreshold() {
                return 0.0f;
            }

            @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
            public final long getDurationNanos(float f) {
                return 0L;
            }

            @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
            public final float getTargetValue(float f, float f2) {
                return 0.0f;
            }

            @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
            public final float getValueFromNanos(float f, float f2, long j) {
                return 0.0f;
            }

            @Override // androidx.compose.animation.core.FloatDecayAnimationSpec
            public final float getVelocityFromNanos(float f, long j) {
                return 0.0f;
            }
        });
    }

    public static final DraggableAnchors DraggableAnchors(Function1 function1) {
        DraggableAnchorsConfig draggableAnchorsConfig = new DraggableAnchorsConfig();
        function1.mo781invoke(draggableAnchorsConfig);
        List list = draggableAnchorsConfig.keys;
        float[] fArr = draggableAnchorsConfig.positions;
        int size = ((ArrayList) list).size();
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(size, fArr.length);
        return new DefaultDraggableAnchors(list, Arrays.copyOfRange(fArr, 0, size));
    }

    public static final Object access$animateTo(AnchoredDraggableState anchoredDraggableState, float f, final AnchoredDragScope anchoredDragScope, DraggableAnchors draggableAnchors, Object obj, AnimationSpec animationSpec, SuspendLambda suspendLambda) {
        Object objAnimate;
        float fPositionOf = ((DefaultDraggableAnchors) draggableAnchors).positionOf(obj);
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = Float.isNaN(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue()) ? 0.0f : ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue();
        if (!Float.isNaN(fPositionOf)) {
            float f2 = ref$FloatRef.element;
            if (f2 != fPositionOf && (objAnimate = SuspendAnimationKt.animate(f2, fPositionOf, f, animationSpec, new Function2() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$animateTo$2$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    float fFloatValue = ((Number) obj2).floatValue();
                    ((AnchoredDraggableState$anchoredDragScope$1) anchoredDragScope).dragTo(fFloatValue, ((Number) obj3).floatValue());
                    ref$FloatRef.element = fFloatValue;
                    return Unit.INSTANCE;
                }
            }, suspendLambda)) == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return objAnimate;
            }
        }
        return Unit.INSTANCE;
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

    public static Modifier anchoredDraggable$default(Modifier modifier, AnchoredDraggableState anchoredDraggableState, Orientation orientation, boolean z, int i) {
        if ((i & 4) != 0) {
            z = true;
        }
        return modifier.then(new AnchoredDraggableElement(anchoredDraggableState, orientation, z, null, null, null, null, null, 32, null));
    }

    public static Object animateTo$default(AnchoredDraggableState anchoredDraggableState, QSDragAnchor qSDragAnchor, SuspendLambda suspendLambda) {
        anchoredDraggableState.getClass();
        AnchoredDraggableDefaults.INSTANCE.getClass();
        AnchoredDraggableKt$animateTo$4 anchoredDraggableKt$animateTo$4 = new AnchoredDraggableKt$animateTo$4(anchoredDraggableState, AnchoredDraggableDefaults.SnapAnimationSpec, null);
        int i = AnchoredDraggableState.$r8$clinit;
        Object objAnchoredDrag = anchoredDraggableState.anchoredDrag(qSDragAnchor, MutatePriority.Default, anchoredDraggableKt$animateTo$4, suspendLambda);
        return objAnchoredDrag == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnchoredDrag : Unit.INSTANCE;
    }

    public static final Object snapTo(AnchoredDraggableState anchoredDraggableState, Object obj, SuspendLambda suspendLambda) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
        int i = AnchoredDraggableState.$r8$clinit;
        Object objAnchoredDrag = anchoredDraggableState.anchoredDrag(obj, MutatePriority.Default, anonymousClass2, suspendLambda);
        return objAnchoredDrag == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnchoredDrag : Unit.INSTANCE;
    }
}
