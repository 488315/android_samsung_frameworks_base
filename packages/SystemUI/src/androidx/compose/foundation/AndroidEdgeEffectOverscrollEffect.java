package androidx.compose.foundation;

import android.content.Context;
import android.widget.EdgeEffect;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.unit.Density;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidEdgeEffectOverscrollEffect implements OverscrollEffect {
    public long containerSize;
    public final Density density;
    public final EdgeEffectWrapper edgeEffectWrapper;
    public final boolean invalidationEnabled;
    public final DelegatableNode node;
    public long pointerId;
    public final SuspendingPointerInputModifierNodeImpl pointerInputNode;
    public long pointerPosition;
    public final MutableState redrawSignal;
    public boolean scrollCycleInProgress;

    public /* synthetic */ AndroidEdgeEffectOverscrollEffect(Context context, Density density, long j, PaddingValues paddingValues, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, density, j, paddingValues);
    }

    public final void animateToReleaseIfNeeded() {
        boolean z;
        EdgeEffectWrapper edgeEffectWrapper = this.edgeEffectWrapper;
        EdgeEffect edgeEffect = edgeEffectWrapper.topEffect;
        boolean z2 = true;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z = !edgeEffect.isFinished();
        } else {
            z = false;
        }
        EdgeEffect edgeEffect2 = edgeEffectWrapper.bottomEffect;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            z = !edgeEffect2.isFinished() || z;
        }
        EdgeEffect edgeEffect3 = edgeEffectWrapper.leftEffect;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            z = !edgeEffect3.isFinished() || z;
        }
        EdgeEffect edgeEffect4 = edgeEffectWrapper.rightEffect;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            if (edgeEffect4.isFinished() && !z) {
                z2 = false;
            }
            z = z2;
        }
        if (z) {
            invalidateOverscroll$foundation_release();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0057, code lost:
    
        if (r17.invoke(r14, r2) == r3) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToFling-BMRW4eQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object mo19applyToFlingBMRW4eQ(long r15, kotlin.jvm.functions.Function2 r17, kotlin.coroutines.jvm.internal.ContinuationImpl r18) {
        /*
            Method dump skipped, instructions count: 457
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect.mo19applyToFlingBMRW4eQ(long, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0252 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0321  */
    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToScroll-Rhakbz0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long mo20applyToScrollRhakbz0(int r22, long r23, kotlin.jvm.functions.Function1 r25) {
        /*
            Method dump skipped, instructions count: 809
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect.mo20applyToScrollRhakbz0(int, long, kotlin.jvm.functions.Function1):long");
    }

    /* renamed from: displacement-F1C5BW0$foundation_release, reason: not valid java name */
    public final long m21displacementF1C5BW0$foundation_release() {
        long j = this.pointerPosition;
        if ((9223372034707292159L & j) == 9205357640488583168L) {
            j = SizeKt.m420getCenteruvyYCjk(this.containerSize);
        }
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) / Float.intBitsToFloat((int) (this.containerSize >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) / Float.intBitsToFloat((int) (this.containerSize & 4294967295L));
        long floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    public final DelegatableNode getNode() {
        return this.node;
    }

    public final void invalidateOverscroll$foundation_release() {
        if (this.invalidationEnabled) {
            ((SnapshotMutableStateImpl) this.redrawSignal).setValue(Unit.INSTANCE);
        }
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    public final boolean isInProgress() {
        EdgeEffectWrapper edgeEffectWrapper = this.edgeEffectWrapper;
        EdgeEffect edgeEffect = edgeEffectWrapper.topEffect;
        if (edgeEffect != null) {
            EdgeEffectCompat.INSTANCE.getClass();
            if (EdgeEffectCompat.getDistanceCompat(edgeEffect) != 0.0f) {
                return true;
            }
        }
        EdgeEffect edgeEffect2 = edgeEffectWrapper.bottomEffect;
        if (edgeEffect2 != null) {
            EdgeEffectCompat.INSTANCE.getClass();
            if (EdgeEffectCompat.getDistanceCompat(edgeEffect2) != 0.0f) {
                return true;
            }
        }
        EdgeEffect edgeEffect3 = edgeEffectWrapper.leftEffect;
        if (edgeEffect3 != null) {
            EdgeEffectCompat.INSTANCE.getClass();
            if (EdgeEffectCompat.getDistanceCompat(edgeEffect3) != 0.0f) {
                return true;
            }
        }
        EdgeEffect edgeEffect4 = edgeEffectWrapper.rightEffect;
        if (edgeEffect4 == null) {
            return false;
        }
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(edgeEffect4) != 0.0f;
    }

    /* renamed from: pullBottom-k-4lQ0M, reason: not valid java name */
    public final float m22pullBottomk4lQ0M(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() >> 32));
        int i = (int) (j & 4294967295L);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize & 4294967295L));
        EdgeEffect orCreateBottomEffect = this.edgeEffectWrapper.getOrCreateBottomEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateBottomEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize & 4294967295L)) * (-EdgeEffectCompat.onPullDistanceCompat(orCreateBottomEffect, -intBitsToFloat2, 1 - intBitsToFloat)) : Float.intBitsToFloat(i);
    }

    /* renamed from: pullLeft-k-4lQ0M, reason: not valid java name */
    public final float m23pullLeftk4lQ0M(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() & 4294967295L));
        int i = (int) (j >> 32);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize >> 32));
        EdgeEffect orCreateLeftEffect = this.edgeEffectWrapper.getOrCreateLeftEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateLeftEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize >> 32)) * EdgeEffectCompat.onPullDistanceCompat(orCreateLeftEffect, intBitsToFloat2, 1 - intBitsToFloat) : Float.intBitsToFloat(i);
    }

    /* renamed from: pullRight-k-4lQ0M, reason: not valid java name */
    public final float m24pullRightk4lQ0M(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() & 4294967295L));
        int i = (int) (j >> 32);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize >> 32));
        EdgeEffect orCreateRightEffect = this.edgeEffectWrapper.getOrCreateRightEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateRightEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize >> 32)) * (-EdgeEffectCompat.onPullDistanceCompat(orCreateRightEffect, -intBitsToFloat2, intBitsToFloat)) : Float.intBitsToFloat(i);
    }

    /* renamed from: pullTop-k-4lQ0M, reason: not valid java name */
    public final float m25pullTopk4lQ0M(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() >> 32));
        int i = (int) (j & 4294967295L);
        float intBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize & 4294967295L));
        EdgeEffect orCreateTopEffect = this.edgeEffectWrapper.getOrCreateTopEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateTopEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize & 4294967295L)) * EdgeEffectCompat.onPullDistanceCompat(orCreateTopEffect, intBitsToFloat2, intBitsToFloat) : Float.intBitsToFloat(i);
    }

    private AndroidEdgeEffectOverscrollEffect(Context context, Density density, long j, PaddingValues paddingValues) {
        this.density = density;
        Offset.Companion.getClass();
        this.pointerPosition = Offset.Unspecified;
        EdgeEffectWrapper edgeEffectWrapper = new EdgeEffectWrapper(context, ColorKt.m467toArgb8_81llA(j));
        this.edgeEffectWrapper = edgeEffectWrapper;
        this.redrawSignal = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.invalidationEnabled = true;
        Size.Companion.getClass();
        this.containerSize = 0L;
        this.pointerId = -1L;
        SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect$pointerInputNode$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect$pointerInputNode$1$1, reason: invalid class name */
            final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
                private /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ AndroidEdgeEffectOverscrollEffect this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(AndroidEdgeEffectOverscrollEffect androidEdgeEffectOverscrollEffect, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = androidEdgeEffectOverscrollEffect;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                    anonymousClass1.L$0 = obj;
                    return anonymousClass1;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:29:0x004c, code lost:
                
                    if (r13 != r0) goto L17;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:30:0x004e, code lost:
                
                    return r0;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:41:0x0035, code lost:
                
                    if (r13 == r0) goto L16;
                 */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x004c -> B:6:0x004f). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r13) {
                    /*
                        r12 = this;
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r12.label
                        r2 = 0
                        r3 = 2
                        r4 = 1
                        if (r1 == 0) goto L25
                        if (r1 == r4) goto L1d
                        if (r1 != r3) goto L15
                        java.lang.Object r1 = r12.L$0
                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L4f
                    L15:
                        java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                        java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                        r12.<init>(r13)
                        throw r12
                    L1d:
                        java.lang.Object r1 = r12.L$0
                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L38
                    L25:
                        kotlin.ResultKt.throwOnFailure(r13)
                        java.lang.Object r13 = r12.L$0
                        r1 = r13
                        androidx.compose.ui.input.pointer.AwaitPointerEventScope r1 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r1
                        r12.L$0 = r1
                        r12.label = r4
                        java.lang.Object r13 = androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown$default(r1, r2, r12, r3)
                        if (r13 != r0) goto L38
                        goto L4e
                    L38:
                        androidx.compose.ui.input.pointer.PointerInputChange r13 = (androidx.compose.ui.input.pointer.PointerInputChange) r13
                        androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect r4 = r12.this$0
                        long r5 = r13.id
                        r4.pointerId = r5
                        long r5 = r13.position
                        r4.pointerPosition = r5
                    L44:
                        r12.L$0 = r1
                        r12.label = r3
                        java.lang.Object r13 = androidx.compose.ui.input.pointer.AwaitPointerEventScope.awaitPointerEvent$default(r1, r12)
                        if (r13 != r0) goto L4f
                    L4e:
                        return r0
                    L4f:
                        androidx.compose.ui.input.pointer.PointerEvent r13 = (androidx.compose.ui.input.pointer.PointerEvent) r13
                        java.util.List r13 = r13.changes
                        java.util.ArrayList r4 = new java.util.ArrayList
                        int r5 = r13.size()
                        r4.<init>(r5)
                        r5 = r13
                        java.util.Collection r5 = (java.util.Collection) r5
                        int r5 = r5.size()
                        r6 = 0
                        r7 = r6
                    L65:
                        if (r7 >= r5) goto L78
                        java.lang.Object r8 = r13.get(r7)
                        r9 = r8
                        androidx.compose.ui.input.pointer.PointerInputChange r9 = (androidx.compose.ui.input.pointer.PointerInputChange) r9
                        boolean r9 = r9.pressed
                        if (r9 == 0) goto L75
                        r4.add(r8)
                    L75:
                        int r7 = r7 + 1
                        goto L65
                    L78:
                        androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect r13 = r12.this$0
                        int r5 = r4.size()
                    L7e:
                        if (r6 >= r5) goto L95
                        java.lang.Object r7 = r4.get(r6)
                        r8 = r7
                        androidx.compose.ui.input.pointer.PointerInputChange r8 = (androidx.compose.ui.input.pointer.PointerInputChange) r8
                        long r8 = r8.id
                        long r10 = r13.pointerId
                        boolean r8 = androidx.compose.ui.input.pointer.PointerId.m591equalsimpl0(r8, r10)
                        if (r8 == 0) goto L92
                        goto L96
                    L92:
                        int r6 = r6 + 1
                        goto L7e
                    L95:
                        r7 = r2
                    L96:
                        androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
                        if (r7 != 0) goto La1
                        java.lang.Object r13 = kotlin.collections.CollectionsKt___CollectionsKt.firstOrNull(r4)
                        r7 = r13
                        androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
                    La1:
                        if (r7 == 0) goto Lad
                        androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect r13 = r12.this$0
                        long r5 = r7.id
                        r13.pointerId = r5
                        long r5 = r7.position
                        r13.pointerPosition = r5
                    Lad:
                        boolean r13 = r4.isEmpty()
                        if (r13 == 0) goto L44
                        androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect r12 = r12.this$0
                        r0 = -1
                        r12.pointerId = r0
                        kotlin.Unit r12 = kotlin.Unit.INSTANCE
                        return r12
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect$pointerInputNode$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object awaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass1(AndroidEdgeEffectOverscrollEffect.this, null), continuation);
                return awaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? awaitEachGesture : Unit.INSTANCE;
            }
        });
        this.pointerInputNode = SuspendingPointerInputModifierNode;
        this.node = new StretchOverscrollNode(SuspendingPointerInputModifierNode, this, edgeEffectWrapper);
    }
}
