package androidx.compose.foundation;

import android.content.Context;
import android.widget.EdgeEffect;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerId;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0057, code lost:
    
        if (r17.invoke(r14, r2) == r3) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToFling-BMRW4eQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo19applyToFlingBMRW4eQ(long j, Function2 function2, ContinuationImpl continuationImpl) {
        AndroidEdgeEffectOverscrollEffect$applyToFling$1 androidEdgeEffectOverscrollEffect$applyToFling$1;
        float fAbsorbToRelaxIfNeeded;
        float fAbsorbToRelaxIfNeeded2;
        long j2;
        long jM882minusAH228Gc;
        float fM880getXimpl;
        if (continuationImpl instanceof AndroidEdgeEffectOverscrollEffect$applyToFling$1) {
            androidEdgeEffectOverscrollEffect$applyToFling$1 = (AndroidEdgeEffectOverscrollEffect$applyToFling$1) continuationImpl;
            int i = androidEdgeEffectOverscrollEffect$applyToFling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                androidEdgeEffectOverscrollEffect$applyToFling$1.label = i - Integer.MIN_VALUE;
            } else {
                androidEdgeEffectOverscrollEffect$applyToFling$1 = new AndroidEdgeEffectOverscrollEffect$applyToFling$1(this, continuationImpl);
            }
        }
        Object objInvoke = androidEdgeEffectOverscrollEffect$applyToFling$1.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = androidEdgeEffectOverscrollEffect$applyToFling$1.label;
        if (i2 != 0) {
            if (i2 == 1) {
                ResultKt.throwOnFailure(objInvoke);
                return Unit.INSTANCE;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j2 = androidEdgeEffectOverscrollEffect$applyToFling$1.J$0;
            this = (AndroidEdgeEffectOverscrollEffect) androidEdgeEffectOverscrollEffect$applyToFling$1.L$0;
            ResultKt.throwOnFailure(objInvoke);
            jM882minusAH228Gc = Velocity.m882minusAH228Gc(j2, ((Velocity) objInvoke).packedValue);
            this.scrollCycleInProgress = false;
            fM880getXimpl = Velocity.m880getXimpl(jM882minusAH228Gc);
            EdgeEffectWrapper edgeEffectWrapper = this.edgeEffectWrapper;
            if (fM880getXimpl <= 0.0f) {
                EdgeEffectCompat edgeEffectCompat = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateLeftEffect = edgeEffectWrapper.getOrCreateLeftEffect();
                int iRoundToInt = MathKt__MathJVMKt.roundToInt(Velocity.m880getXimpl(jM882minusAH228Gc));
                edgeEffectCompat.getClass();
                orCreateLeftEffect.onAbsorb(iRoundToInt);
            } else if (Velocity.m880getXimpl(jM882minusAH228Gc) < 0.0f) {
                EdgeEffectCompat edgeEffectCompat2 = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateRightEffect = edgeEffectWrapper.getOrCreateRightEffect();
                int i3 = -MathKt__MathJVMKt.roundToInt(Velocity.m880getXimpl(jM882minusAH228Gc));
                edgeEffectCompat2.getClass();
                orCreateRightEffect.onAbsorb(i3);
            }
            if (Velocity.m881getYimpl(jM882minusAH228Gc) <= 0.0f) {
                EdgeEffectCompat edgeEffectCompat3 = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateTopEffect = edgeEffectWrapper.getOrCreateTopEffect();
                int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(Velocity.m881getYimpl(jM882minusAH228Gc));
                edgeEffectCompat3.getClass();
                orCreateTopEffect.onAbsorb(iRoundToInt2);
            } else if (Velocity.m881getYimpl(jM882minusAH228Gc) < 0.0f) {
                EdgeEffectCompat edgeEffectCompat4 = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateBottomEffect = edgeEffectWrapper.getOrCreateBottomEffect();
                int i4 = -MathKt__MathJVMKt.roundToInt(Velocity.m881getYimpl(jM882minusAH228Gc));
                edgeEffectCompat4.getClass();
                orCreateBottomEffect.onAbsorb(i4);
            }
            this.animateToReleaseIfNeeded();
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(objInvoke);
        if (Size.m420isEmptyimpl(this.containerSize)) {
            Object objM878boximpl = Velocity.m878boximpl(j);
            androidEdgeEffectOverscrollEffect$applyToFling$1.label = 1;
        } else {
            EdgeEffectWrapper edgeEffectWrapper2 = this.edgeEffectWrapper;
            boolean zIsStretched = EdgeEffectWrapper.isStretched(edgeEffectWrapper2.leftEffect);
            Density density = this.density;
            if (zIsStretched && Velocity.m880getXimpl(j) < 0.0f) {
                EdgeEffectCompat edgeEffectCompat5 = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateLeftEffect2 = edgeEffectWrapper2.getOrCreateLeftEffect();
                float fM880getXimpl2 = Velocity.m880getXimpl(j);
                float fIntBitsToFloat = Float.intBitsToFloat((int) (this.containerSize >> 32));
                edgeEffectCompat5.getClass();
                fAbsorbToRelaxIfNeeded = EdgeEffectCompat.absorbToRelaxIfNeeded(orCreateLeftEffect2, fM880getXimpl2, fIntBitsToFloat, density);
            } else if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper2.rightEffect) || Velocity.m880getXimpl(j) <= 0.0f) {
                fAbsorbToRelaxIfNeeded = 0.0f;
            } else {
                EdgeEffectCompat edgeEffectCompat6 = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateRightEffect2 = edgeEffectWrapper2.getOrCreateRightEffect();
                float f = -Velocity.m880getXimpl(j);
                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (this.containerSize >> 32));
                edgeEffectCompat6.getClass();
                fAbsorbToRelaxIfNeeded = -EdgeEffectCompat.absorbToRelaxIfNeeded(orCreateRightEffect2, f, fIntBitsToFloat2, density);
            }
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper2.topEffect) && Velocity.m881getYimpl(j) < 0.0f) {
                EdgeEffectCompat edgeEffectCompat7 = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateTopEffect2 = edgeEffectWrapper2.getOrCreateTopEffect();
                float fM881getYimpl = Velocity.m881getYimpl(j);
                float fIntBitsToFloat3 = Float.intBitsToFloat((int) (4294967295L & this.containerSize));
                edgeEffectCompat7.getClass();
                fAbsorbToRelaxIfNeeded2 = EdgeEffectCompat.absorbToRelaxIfNeeded(orCreateTopEffect2, fM881getYimpl, fIntBitsToFloat3, density);
            } else if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper2.bottomEffect) || Velocity.m881getYimpl(j) <= 0.0f) {
                fAbsorbToRelaxIfNeeded2 = 0.0f;
            } else {
                EdgeEffectCompat edgeEffectCompat8 = EdgeEffectCompat.INSTANCE;
                EdgeEffect orCreateBottomEffect2 = edgeEffectWrapper2.getOrCreateBottomEffect();
                float f2 = -Velocity.m881getYimpl(j);
                float fIntBitsToFloat4 = Float.intBitsToFloat((int) (4294967295L & this.containerSize));
                edgeEffectCompat8.getClass();
                fAbsorbToRelaxIfNeeded2 = -EdgeEffectCompat.absorbToRelaxIfNeeded(orCreateBottomEffect2, f2, fIntBitsToFloat4, density);
            }
            long jVelocity = VelocityKt.Velocity(fAbsorbToRelaxIfNeeded, fAbsorbToRelaxIfNeeded2);
            Velocity.Companion.getClass();
            if (jVelocity != 0) {
                invalidateOverscroll$foundation_release();
            }
            long jM882minusAH228Gc2 = Velocity.m882minusAH228Gc(j, jVelocity);
            Object objM878boximpl2 = Velocity.m878boximpl(jM882minusAH228Gc2);
            androidEdgeEffectOverscrollEffect$applyToFling$1.L$0 = this;
            androidEdgeEffectOverscrollEffect$applyToFling$1.J$0 = jM882minusAH228Gc2;
            androidEdgeEffectOverscrollEffect$applyToFling$1.label = 2;
            objInvoke = function2.invoke(objM878boximpl2, androidEdgeEffectOverscrollEffect$applyToFling$1);
            if (objInvoke != obj) {
                j2 = jM882minusAH228Gc2;
                jM882minusAH228Gc = Velocity.m882minusAH228Gc(j2, ((Velocity) objInvoke).packedValue);
                this.scrollCycleInProgress = false;
                fM880getXimpl = Velocity.m880getXimpl(jM882minusAH228Gc);
                EdgeEffectWrapper edgeEffectWrapper3 = this.edgeEffectWrapper;
                if (fM880getXimpl <= 0.0f) {
                }
                if (Velocity.m881getYimpl(jM882minusAH228Gc) <= 0.0f) {
                }
                this.animateToReleaseIfNeeded();
                return Unit.INSTANCE;
            }
        }
        return obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0252 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0321  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0149 A[PHI: r13 r20
      0x0149: PHI (r13v9 float) = (r13v8 float), (r13v12 float) binds: [B:74:0x017a, B:63:0x0142] A[DONT_GENERATE, DONT_INLINE]
      0x0149: PHI (r20v1 char) = (r20v0 char), (r20v2 char) binds: [B:74:0x017a, B:63:0x0142] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01cb  */
    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToScroll-Rhakbz0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long mo20applyToScrollRhakbz0(int i, long j, Function1 function1) {
        long j2;
        float fIntBitsToFloat;
        int i2;
        char c;
        float fM24pullRightk4lQ0M;
        float fIntBitsToFloat2;
        long jFloatToRawIntBits;
        long jM402minusMKHz9U;
        int i3;
        boolean z;
        boolean zIsAnimating;
        float f;
        float f2;
        boolean z2;
        int i4;
        boolean z3;
        if (Size.m420isEmptyimpl(this.containerSize)) {
            return ((Offset) function1.mo781invoke(Offset.m395boximpl(j))).packedValue;
        }
        boolean z4 = this.scrollCycleInProgress;
        EdgeEffectWrapper edgeEffectWrapper = this.edgeEffectWrapper;
        if (!z4) {
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper.leftEffect)) {
                Offset.Companion.getClass();
                m23pullLeftk4lQ0M(0L);
            }
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper.rightEffect)) {
                Offset.Companion.getClass();
                m24pullRightk4lQ0M(0L);
            }
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper.topEffect)) {
                Offset.Companion.getClass();
                m25pullTopk4lQ0M(0L);
            }
            if (EdgeEffectWrapper.isStretched(edgeEffectWrapper.bottomEffect)) {
                Offset.Companion.getClass();
                m22pullBottomk4lQ0M(0L);
            }
            this.scrollCycleInProgress = true;
        }
        PaddingValuesImpl paddingValuesImpl = AndroidOverscroll_androidKt.DefaultGlowPaddingValues;
        NestedScrollSource.Companion.getClass();
        float f3 = i == NestedScrollSource.SideEffect ? 4.0f : 1.0f;
        long jM404timestuRUvjQ = Offset.m404timestuRUvjQ(f3, j);
        int i5 = (int) (j & 4294967295L);
        if (Float.intBitsToFloat(i5) != 0.0f) {
            if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper.topEffect) || Float.intBitsToFloat(i5) >= 0.0f) {
                j2 = 4294967295L;
                if (EdgeEffectWrapper.isStretched(edgeEffectWrapper.bottomEffect) && Float.intBitsToFloat(i5) > 0.0f) {
                    float fM22pullBottomk4lQ0M = m22pullBottomk4lQ0M(jM404timestuRUvjQ);
                    if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper.bottomEffect)) {
                        edgeEffectWrapper.getOrCreateBottomEffect().finish();
                    }
                    fIntBitsToFloat = fM22pullBottomk4lQ0M == Float.intBitsToFloat((int) (jM404timestuRUvjQ & 4294967295L)) ? Float.intBitsToFloat(i5) : fM22pullBottomk4lQ0M / f3;
                }
            } else {
                float fM25pullTopk4lQ0M = m25pullTopk4lQ0M(jM404timestuRUvjQ);
                if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper.topEffect)) {
                    edgeEffectWrapper.getOrCreateTopEffect().finish();
                }
                j2 = 4294967295L;
                fIntBitsToFloat = fM25pullTopk4lQ0M == Float.intBitsToFloat((int) (jM404timestuRUvjQ & 4294967295L)) ? Float.intBitsToFloat(i5) : fM25pullTopk4lQ0M / f3;
            }
            i2 = (int) (j >> 32);
            if (Float.intBitsToFloat(i2) == 0.0f) {
                if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper.leftEffect) || Float.intBitsToFloat(i2) >= 0.0f) {
                    c = ' ';
                    if (EdgeEffectWrapper.isStretched(edgeEffectWrapper.rightEffect) && Float.intBitsToFloat(i2) > 0.0f) {
                        fM24pullRightk4lQ0M = m24pullRightk4lQ0M(jM404timestuRUvjQ);
                        if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper.rightEffect)) {
                            edgeEffectWrapper.getOrCreateRightEffect().finish();
                        }
                        fIntBitsToFloat2 = fM24pullRightk4lQ0M == Float.intBitsToFloat((int) (jM404timestuRUvjQ >> 32)) ? Float.intBitsToFloat(i2) : fM24pullRightk4lQ0M / f3;
                    }
                } else {
                    fM24pullRightk4lQ0M = m23pullLeftk4lQ0M(jM404timestuRUvjQ);
                    c = ' ';
                    if (!EdgeEffectWrapper.isStretched(edgeEffectWrapper.leftEffect)) {
                        edgeEffectWrapper.getOrCreateLeftEffect().finish();
                    }
                    if (fM24pullRightk4lQ0M == Float.intBitsToFloat((int) (jM404timestuRUvjQ >> 32))) {
                        fIntBitsToFloat2 = Float.intBitsToFloat(i2);
                    }
                }
                jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) & j2) | (Float.floatToRawIntBits(fIntBitsToFloat2) << c);
                Offset.Companion.getClass();
                if (!Offset.m398equalsimpl0(jFloatToRawIntBits, 0L)) {
                    invalidateOverscroll$foundation_release();
                }
                jM402minusMKHz9U = Offset.m402minusMKHz9U(j, jFloatToRawIntBits);
                long j3 = ((Offset) function1.mo781invoke(Offset.m395boximpl(jM402minusMKHz9U))).packedValue;
                long jM402minusMKHz9U2 = Offset.m402minusMKHz9U(jM402minusMKHz9U, j3);
                if ((Float.intBitsToFloat((int) (jM402minusMKHz9U >> c)) != 0.0f || Float.intBitsToFloat((int) (jM402minusMKHz9U & j2)) != 0.0f) && ((Float.intBitsToFloat((int) (j3 >> c)) != 0.0f || Float.intBitsToFloat((int) (j3 & j2)) != 0.0f) && (EdgeEffectWrapper.isStretched(edgeEffectWrapper.leftEffect) || EdgeEffectWrapper.isStretched(edgeEffectWrapper.topEffect) || EdgeEffectWrapper.isStretched(edgeEffectWrapper.rightEffect) || EdgeEffectWrapper.isStretched(edgeEffectWrapper.bottomEffect)))) {
                    animateToReleaseIfNeeded();
                }
                if (i == NestedScrollSource.UserInput) {
                    i3 = i5;
                    int i6 = (int) (jM402minusMKHz9U2 >> c);
                    if (Float.intBitsToFloat(i6) > 0.5f) {
                        m23pullLeftk4lQ0M(jM402minusMKHz9U2);
                    } else {
                        if (Float.intBitsToFloat(i6) >= -0.5f) {
                            f = 0.5f;
                            f2 = -0.5f;
                            z2 = false;
                            i4 = (int) (jM402minusMKHz9U2 & j2);
                            if (Float.intBitsToFloat(i4) <= f) {
                                m25pullTopk4lQ0M(jM402minusMKHz9U2);
                            } else {
                                if (Float.intBitsToFloat(i4) >= f2) {
                                    z3 = false;
                                    z = !z2 || z3;
                                    if (!Offset.m398equalsimpl0(jM402minusMKHz9U, 0L)) {
                                        if (!EdgeEffectWrapper.isAnimating(edgeEffectWrapper.leftEffect) || Float.intBitsToFloat(i2) >= 0.0f) {
                                            zIsAnimating = false;
                                        } else {
                                            EdgeEffectCompat edgeEffectCompat = EdgeEffectCompat.INSTANCE;
                                            EdgeEffect orCreateLeftEffect = edgeEffectWrapper.getOrCreateLeftEffect();
                                            float fIntBitsToFloat3 = Float.intBitsToFloat(i2);
                                            edgeEffectCompat.getClass();
                                            EdgeEffectCompat.onReleaseWithOppositeDelta(orCreateLeftEffect, fIntBitsToFloat3);
                                            zIsAnimating = EdgeEffectWrapper.isAnimating(edgeEffectWrapper.leftEffect);
                                        }
                                        if (EdgeEffectWrapper.isAnimating(edgeEffectWrapper.rightEffect) && Float.intBitsToFloat(i2) > 0.0f) {
                                            EdgeEffectCompat edgeEffectCompat2 = EdgeEffectCompat.INSTANCE;
                                            EdgeEffect orCreateRightEffect = edgeEffectWrapper.getOrCreateRightEffect();
                                            float fIntBitsToFloat4 = Float.intBitsToFloat(i2);
                                            edgeEffectCompat2.getClass();
                                            EdgeEffectCompat.onReleaseWithOppositeDelta(orCreateRightEffect, fIntBitsToFloat4);
                                            zIsAnimating = zIsAnimating || EdgeEffectWrapper.isAnimating(edgeEffectWrapper.rightEffect);
                                        }
                                        if (EdgeEffectWrapper.isAnimating(edgeEffectWrapper.topEffect) && Float.intBitsToFloat(i3) < 0.0f) {
                                            EdgeEffectCompat edgeEffectCompat3 = EdgeEffectCompat.INSTANCE;
                                            EdgeEffect orCreateTopEffect = edgeEffectWrapper.getOrCreateTopEffect();
                                            float fIntBitsToFloat5 = Float.intBitsToFloat(i3);
                                            edgeEffectCompat3.getClass();
                                            EdgeEffectCompat.onReleaseWithOppositeDelta(orCreateTopEffect, fIntBitsToFloat5);
                                            zIsAnimating = zIsAnimating || EdgeEffectWrapper.isAnimating(edgeEffectWrapper.topEffect);
                                        }
                                        if (EdgeEffectWrapper.isAnimating(edgeEffectWrapper.bottomEffect) && Float.intBitsToFloat(i3) > 0.0f) {
                                            EdgeEffectCompat edgeEffectCompat4 = EdgeEffectCompat.INSTANCE;
                                            EdgeEffect orCreateBottomEffect = edgeEffectWrapper.getOrCreateBottomEffect();
                                            float fIntBitsToFloat6 = Float.intBitsToFloat(i3);
                                            edgeEffectCompat4.getClass();
                                            EdgeEffectCompat.onReleaseWithOppositeDelta(orCreateBottomEffect, fIntBitsToFloat6);
                                            zIsAnimating = zIsAnimating || EdgeEffectWrapper.isAnimating(edgeEffectWrapper.bottomEffect);
                                        }
                                        z = zIsAnimating || z;
                                    }
                                    if (z) {
                                        invalidateOverscroll$foundation_release();
                                    }
                                    return Offset.m403plusMKHz9U(jFloatToRawIntBits, j3);
                                }
                                m22pullBottomk4lQ0M(jM402minusMKHz9U2);
                            }
                            z3 = true;
                            if (z2) {
                            }
                            if (!Offset.m398equalsimpl0(jM402minusMKHz9U, 0L)) {
                            }
                            if (z) {
                            }
                            return Offset.m403plusMKHz9U(jFloatToRawIntBits, j3);
                        }
                        m24pullRightk4lQ0M(jM402minusMKHz9U2);
                    }
                    f = 0.5f;
                    f2 = -0.5f;
                    z2 = true;
                    i4 = (int) (jM402minusMKHz9U2 & j2);
                    if (Float.intBitsToFloat(i4) <= f) {
                    }
                    z3 = true;
                    if (z2) {
                    }
                    if (!Offset.m398equalsimpl0(jM402minusMKHz9U, 0L)) {
                    }
                    if (z) {
                    }
                    return Offset.m403plusMKHz9U(jFloatToRawIntBits, j3);
                }
                i3 = i5;
                if (!Offset.m398equalsimpl0(jM402minusMKHz9U, 0L)) {
                }
                if (z) {
                }
                return Offset.m403plusMKHz9U(jFloatToRawIntBits, j3);
            }
            c = ' ';
            fIntBitsToFloat2 = 0.0f;
            jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) & j2) | (Float.floatToRawIntBits(fIntBitsToFloat2) << c);
            Offset.Companion.getClass();
            if (!Offset.m398equalsimpl0(jFloatToRawIntBits, 0L)) {
            }
            jM402minusMKHz9U = Offset.m402minusMKHz9U(j, jFloatToRawIntBits);
            long j32 = ((Offset) function1.mo781invoke(Offset.m395boximpl(jM402minusMKHz9U))).packedValue;
            long jM402minusMKHz9U22 = Offset.m402minusMKHz9U(jM402minusMKHz9U, j32);
            if (Float.intBitsToFloat((int) (jM402minusMKHz9U >> c)) != 0.0f) {
                animateToReleaseIfNeeded();
            } else {
                animateToReleaseIfNeeded();
            }
            if (i == NestedScrollSource.UserInput) {
            }
            if (!Offset.m398equalsimpl0(jM402minusMKHz9U, 0L)) {
            }
            if (z) {
            }
            return Offset.m403plusMKHz9U(jFloatToRawIntBits, j32);
        }
        j2 = 4294967295L;
        fIntBitsToFloat = 0.0f;
        i2 = (int) (j >> 32);
        if (Float.intBitsToFloat(i2) == 0.0f) {
        }
        fIntBitsToFloat2 = 0.0f;
        jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) & j2) | (Float.floatToRawIntBits(fIntBitsToFloat2) << c);
        Offset.Companion.getClass();
        if (!Offset.m398equalsimpl0(jFloatToRawIntBits, 0L)) {
        }
        jM402minusMKHz9U = Offset.m402minusMKHz9U(j, jFloatToRawIntBits);
        long j322 = ((Offset) function1.mo781invoke(Offset.m395boximpl(jM402minusMKHz9U))).packedValue;
        long jM402minusMKHz9U222 = Offset.m402minusMKHz9U(jM402minusMKHz9U, j322);
        if (Float.intBitsToFloat((int) (jM402minusMKHz9U >> c)) != 0.0f) {
        }
        if (i == NestedScrollSource.UserInput) {
        }
        if (!Offset.m398equalsimpl0(jM402minusMKHz9U, 0L)) {
        }
        if (z) {
        }
        return Offset.m403plusMKHz9U(jFloatToRawIntBits, j322);
    }

    /* renamed from: displacement-F1C5BW0$foundation_release, reason: not valid java name */
    public final long m21displacementF1C5BW0$foundation_release() {
        long jM422getCenteruvyYCjk = this.pointerPosition;
        if ((9223372034707292159L & jM422getCenteruvyYCjk) == 9205357640488583168L) {
            jM422getCenteruvyYCjk = SizeKt.m422getCenteruvyYCjk(this.containerSize);
        }
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jM422getCenteruvyYCjk >> 32)) / Float.intBitsToFloat((int) (this.containerSize >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jM422getCenteruvyYCjk & 4294967295L)) / Float.intBitsToFloat((int) (this.containerSize & 4294967295L));
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
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
        float fIntBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() >> 32));
        int i = (int) (j & 4294967295L);
        float fIntBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize & 4294967295L));
        EdgeEffect orCreateBottomEffect = this.edgeEffectWrapper.getOrCreateBottomEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateBottomEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize & 4294967295L)) * (-EdgeEffectCompat.onPullDistanceCompat(orCreateBottomEffect, -fIntBitsToFloat2, 1 - fIntBitsToFloat)) : Float.intBitsToFloat(i);
    }

    /* renamed from: pullLeft-k-4lQ0M, reason: not valid java name */
    public final float m23pullLeftk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() & 4294967295L));
        int i = (int) (j >> 32);
        float fIntBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize >> 32));
        EdgeEffect orCreateLeftEffect = this.edgeEffectWrapper.getOrCreateLeftEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateLeftEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize >> 32)) * EdgeEffectCompat.onPullDistanceCompat(orCreateLeftEffect, fIntBitsToFloat2, 1 - fIntBitsToFloat) : Float.intBitsToFloat(i);
    }

    /* renamed from: pullRight-k-4lQ0M, reason: not valid java name */
    public final float m24pullRightk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() & 4294967295L));
        int i = (int) (j >> 32);
        float fIntBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize >> 32));
        EdgeEffect orCreateRightEffect = this.edgeEffectWrapper.getOrCreateRightEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateRightEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize >> 32)) * (-EdgeEffectCompat.onPullDistanceCompat(orCreateRightEffect, -fIntBitsToFloat2, fIntBitsToFloat)) : Float.intBitsToFloat(i);
    }

    /* renamed from: pullTop-k-4lQ0M, reason: not valid java name */
    public final float m25pullTopk4lQ0M(long j) {
        float fIntBitsToFloat = Float.intBitsToFloat((int) (m21displacementF1C5BW0$foundation_release() >> 32));
        int i = (int) (j & 4294967295L);
        float fIntBitsToFloat2 = Float.intBitsToFloat(i) / Float.intBitsToFloat((int) (this.containerSize & 4294967295L));
        EdgeEffect orCreateTopEffect = this.edgeEffectWrapper.getOrCreateTopEffect();
        EdgeEffectCompat.INSTANCE.getClass();
        return EdgeEffectCompat.getDistanceCompat(orCreateTopEffect) == 0.0f ? Float.intBitsToFloat((int) (this.containerSize & 4294967295L)) * EdgeEffectCompat.onPullDistanceCompat(orCreateTopEffect, fIntBitsToFloat2, fIntBitsToFloat) : Float.intBitsToFloat(i);
    }

    private AndroidEdgeEffectOverscrollEffect(Context context, Density density, long j, PaddingValues paddingValues) {
        this.density = density;
        Offset.Companion.getClass();
        this.pointerPosition = Offset.Unspecified;
        EdgeEffectWrapper edgeEffectWrapper = new EdgeEffectWrapper(context, ColorKt.m469toArgb8_81llA(j));
        this.edgeEffectWrapper = edgeEffectWrapper;
        this.redrawSignal = SnapshotStateKt.mutableStateOf(Unit.INSTANCE, SnapshotStateKt.neverEqualPolicy());
        this.invalidationEnabled = true;
        Size.Companion.getClass();
        this.containerSize = 0L;
        this.pointerId = -1L;
        SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.AndroidEdgeEffectOverscrollEffect$pointerInputNode$1

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

                /* JADX WARN: Code restructure failed: missing block: B:11:0x0035, code lost:
                
                    if (r13 == r0) goto L16;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:15:0x004c, code lost:
                
                    if (r13 != r0) goto L17;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:16:0x004e, code lost:
                
                    return r0;
                 */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x004c -> B:17:0x004f). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    AwaitPointerEventScope awaitPointerEventScope;
                    Object obj2;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        this.L$0 = awaitPointerEventScope;
                        this.label = 1;
                        obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 2);
                    } else if (i == 1) {
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                    } else {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        List list = ((PointerEvent) obj).changes;
                        ArrayList arrayList = new ArrayList(list.size());
                        int size = list.size();
                        int i2 = 0;
                        for (int i3 = 0; i3 < size; i3++) {
                            Object obj3 = list.get(i3);
                            if (((PointerInputChange) obj3).pressed) {
                                arrayList.add(obj3);
                            }
                        }
                        AndroidEdgeEffectOverscrollEffect androidEdgeEffectOverscrollEffect = this.this$0;
                        int size2 = arrayList.size();
                        while (true) {
                            if (i2 >= size2) {
                                obj2 = null;
                                break;
                            }
                            obj2 = arrayList.get(i2);
                            if (PointerId.m593equalsimpl0(((PointerInputChange) obj2).id, androidEdgeEffectOverscrollEffect.pointerId)) {
                                break;
                            }
                            i2++;
                        }
                        PointerInputChange pointerInputChange = (PointerInputChange) obj2;
                        if (pointerInputChange == null) {
                            pointerInputChange = (PointerInputChange) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList);
                        }
                        if (pointerInputChange != null) {
                            AndroidEdgeEffectOverscrollEffect androidEdgeEffectOverscrollEffect2 = this.this$0;
                            androidEdgeEffectOverscrollEffect2.pointerId = pointerInputChange.id;
                            androidEdgeEffectOverscrollEffect2.pointerPosition = pointerInputChange.position;
                        }
                        if (arrayList.isEmpty()) {
                            this.this$0.pointerId = -1L;
                            return Unit.INSTANCE;
                        }
                        this.L$0 = awaitPointerEventScope;
                        this.label = 2;
                        obj = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, this);
                    }
                    PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
                    AndroidEdgeEffectOverscrollEffect androidEdgeEffectOverscrollEffect3 = this.this$0;
                    androidEdgeEffectOverscrollEffect3.pointerId = pointerInputChange2.id;
                    androidEdgeEffectOverscrollEffect3.pointerPosition = pointerInputChange2.position;
                    this.L$0 = awaitPointerEventScope;
                    this.label = 2;
                    obj = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, this);
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass1(this.this$0, null), continuation);
                return objAwaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitEachGesture : Unit.INSTANCE;
            }
        });
        this.pointerInputNode = suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode;
        this.node = new StretchOverscrollNode(suspendingPointerInputModifierNodeImplSuspendingPointerInputModifierNode, this, edgeEffectWrapper);
    }
}
