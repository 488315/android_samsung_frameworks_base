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
import kotlin.Unit;
import kotlin.collections.ArraysKt__ArraysJVMKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnchoredDraggableKt {
    public static final Function1 AlwaysDrag = new Function1() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$AlwaysDrag$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
            return Boolean.TRUE;
        }
    };
    public static final Function1 GetOrNan = AnchoredDraggableKt$GetOrNan$1.INSTANCE;
    public static final DecayAnimationSpec NoOpDecayAnimationSpec;

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
        function1.mo779invoke(draggableAnchorsConfig);
        List list = draggableAnchorsConfig.keys;
        float[] fArr = draggableAnchorsConfig.positions;
        int size = ((ArrayList) list).size();
        ArraysKt__ArraysJVMKt.copyOfRangeToIndexCheck(size, fArr.length);
        return new DefaultDraggableAnchors(list, Arrays.copyOfRange(fArr, 0, size));
    }

    public static final Object access$animateTo(AnchoredDraggableState anchoredDraggableState, float f, final AnchoredDragScope anchoredDragScope, DraggableAnchors draggableAnchors, Object obj, AnimationSpec animationSpec, SuspendLambda suspendLambda) {
        Object animate;
        float positionOf = ((DefaultDraggableAnchors) draggableAnchors).positionOf(obj);
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = Float.isNaN(((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue()) ? 0.0f : ((SnapshotMutableFloatStateImpl) anchoredDraggableState.offset$delegate).getFloatValue();
        if (!Float.isNaN(positionOf)) {
            float f2 = ref$FloatRef.element;
            if (f2 != positionOf && (animate = SuspendAnimationKt.animate(f2, positionOf, f, animationSpec, new Function2() { // from class: androidx.compose.foundation.gestures.AnchoredDraggableKt$animateTo$2$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    float floatValue = ((Number) obj2).floatValue();
                    ((AnchoredDraggableState$anchoredDragScope$1) AnchoredDragScope.this).dragTo(floatValue, ((Number) obj3).floatValue());
                    ref$FloatRef.element = floatValue;
                    return Unit.INSTANCE;
                }
            }, suspendLambda)) == CoroutineSingletons.COROUTINE_SUSPENDED) {
                return animate;
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:3|(7:5|6|7|(1:(1:10)(2:16|17))(3:18|19|(1:21))|11|12|13))|23|6|7|(0)(0)|11|12|13) */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$restartable(kotlin.jvm.functions.Function0 r4, kotlin.jvm.functions.Function2 r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            boolean r0 = r6 instanceof androidx.compose.foundation.gestures.AnchoredDraggableKt$restartable$1
            if (r0 == 0) goto L13
            r0 = r6
            androidx.compose.foundation.gestures.AnchoredDraggableKt$restartable$1 r0 = (androidx.compose.foundation.gestures.AnchoredDraggableKt$restartable$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.AnchoredDraggableKt$restartable$1 r0 = new androidx.compose.foundation.gestures.AnchoredDraggableKt$restartable$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: androidx.compose.foundation.gestures.AnchoredDragFinishedSignal -> L41
            goto L41
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            androidx.compose.foundation.gestures.AnchoredDraggableKt$restartable$2 r6 = new androidx.compose.foundation.gestures.AnchoredDraggableKt$restartable$2     // Catch: androidx.compose.foundation.gestures.AnchoredDragFinishedSignal -> L41
            r2 = 0
            r6.<init>(r4, r5, r2)     // Catch: androidx.compose.foundation.gestures.AnchoredDragFinishedSignal -> L41
            r0.label = r3     // Catch: androidx.compose.foundation.gestures.AnchoredDragFinishedSignal -> L41
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r6, r0)     // Catch: androidx.compose.foundation.gestures.AnchoredDragFinishedSignal -> L41
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.AnchoredDraggableKt.access$restartable(kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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
        Object anchoredDrag = anchoredDraggableState.anchoredDrag(qSDragAnchor, MutatePriority.Default, anchoredDraggableKt$animateTo$4, suspendLambda);
        return anchoredDrag == CoroutineSingletons.COROUTINE_SUSPENDED ? anchoredDrag : Unit.INSTANCE;
    }

    public static final Object snapTo(AnchoredDraggableState anchoredDraggableState, Object obj, SuspendLambda suspendLambda) {
        AnchoredDraggableKt$snapTo$2 anchoredDraggableKt$snapTo$2 = new AnchoredDraggableKt$snapTo$2(null);
        int i = AnchoredDraggableState.$r8$clinit;
        Object anchoredDrag = anchoredDraggableState.anchoredDrag(obj, MutatePriority.Default, anchoredDraggableKt$snapTo$2, suspendLambda);
        return anchoredDrag == CoroutineSingletons.COROUTINE_SUSPENDED ? anchoredDrag : Unit.INSTANCE;
    }
}
