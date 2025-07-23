package com.android.compose.gesture;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.util.VelocityTracker;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NestedDraggableNode$detectDrags$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ VelocityTracker $velocityTracker;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ NestedDraggableNode this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedDraggableNode$detectDrags$2(VelocityTracker velocityTracker, NestedDraggableNode nestedDraggableNode, Continuation continuation) {
        super(2, continuation);
        this.$velocityTracker = velocityTracker;
        this.this$0 = nestedDraggableNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NestedDraggableNode$detectDrags$2 nestedDraggableNode$detectDrags$2 = new NestedDraggableNode$detectDrags$2(this.$velocityTracker, this.this$0, continuation);
        nestedDraggableNode$detectDrags$2.L$0 = obj;
        return nestedDraggableNode$detectDrags$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NestedDraggableNode$detectDrags$2) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0173, code lost:
    
        if (r2 == r0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x009f, code lost:
    
        if (r12 == r0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0066, code lost:
    
        if (r10 == r0) goto L76;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x019d  */
    /* JADX WARN: Type inference failed for: r10v8, types: [com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1, kotlin.jvm.functions.Function1] */
    /* JADX WARN: Type inference failed for: r13v0, types: [com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.compose.gesture.NestedDraggable$Controller] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 583
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.gesture.NestedDraggableNode$detectDrags$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
