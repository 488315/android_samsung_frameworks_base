package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Density;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PressGestureScopeImpl implements PressGestureScope, Density {
    public final /* synthetic */ Density $$delegate_0;
    public boolean isCanceled;
    public boolean isReleased;
    public final MutexImpl mutex;

    public PressGestureScopeImpl(Density density) {
        this.$$delegate_0 = density;
        Symbol symbol = MutexKt.NO_OWNER;
        this.mutex = new MutexImpl(false);
    }

    public final void cancel() {
        this.isCanceled = true;
        MutexImpl mutexImpl = this.mutex;
        if (mutexImpl.isLocked()) {
            mutexImpl.unlock(null);
        }
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.$$delegate_0.getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.$$delegate_0.getFontScale();
    }

    public final void release() {
        this.isReleased = true;
        MutexImpl mutexImpl = this.mutex;
        if (mutexImpl.isLocked()) {
            mutexImpl.unlock(null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object reset(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof androidx.compose.foundation.gestures.PressGestureScopeImpl$reset$1
            if (r0 == 0) goto L13
            r0 = r5
            androidx.compose.foundation.gestures.PressGestureScopeImpl$reset$1 r0 = (androidx.compose.foundation.gestures.PressGestureScopeImpl$reset$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.PressGestureScopeImpl$reset$1 r0 = new androidx.compose.foundation.gestures.PressGestureScopeImpl$reset$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            androidx.compose.foundation.gestures.PressGestureScopeImpl r4 = (androidx.compose.foundation.gestures.PressGestureScopeImpl) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.sync.MutexImpl r5 = r4.mutex
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r5.lock(r0)
            if (r5 != r1) goto L43
            return r1
        L43:
            r5 = 0
            r4.isReleased = r5
            r4.isCanceled = r5
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.PressGestureScopeImpl.reset(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: roundToPx-0680j_4 */
    public final int mo51roundToPx0680j_4(float f) {
        return this.$$delegate_0.mo51roundToPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toDp-GaN1DYA */
    public final float mo52toDpGaN1DYA(long j) {
        return this.$$delegate_0.mo52toDpGaN1DYA(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo53toDpu2uoSUM(float f) {
        return this.$$delegate_0.mo53toDpu2uoSUM(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDpSize-k-rfVVM */
    public final long mo55toDpSizekrfVVM(long j) {
        return this.$$delegate_0.mo55toDpSizekrfVVM(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx--R2X_6o */
    public final float mo56toPxR2X_6o(long j) {
        return this.$$delegate_0.mo56toPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx-0680j_4 */
    public final float mo57toPx0680j_4(float f) {
        return this.$$delegate_0.mo57toPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSize-XkaWNTQ */
    public final long mo58toSizeXkaWNTQ(long j) {
        return this.$$delegate_0.mo58toSizeXkaWNTQ(j);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toSp-0xMU5do */
    public final long mo59toSp0xMU5do(float f) {
        return this.$$delegate_0.mo59toSp0xMU5do(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSp-kPz2Gy4 */
    public final long mo60toSpkPz2Gy4(float f) {
        return this.$$delegate_0.mo60toSpkPz2Gy4(f);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object tryAwaitRelease(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof androidx.compose.foundation.gestures.PressGestureScopeImpl$tryAwaitRelease$1
            if (r0 == 0) goto L13
            r0 = r5
            androidx.compose.foundation.gestures.PressGestureScopeImpl$tryAwaitRelease$1 r0 = (androidx.compose.foundation.gestures.PressGestureScopeImpl$tryAwaitRelease$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.PressGestureScopeImpl$tryAwaitRelease$1 r0 = new androidx.compose.foundation.gestures.PressGestureScopeImpl$tryAwaitRelease$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            androidx.compose.foundation.gestures.PressGestureScopeImpl r4 = (androidx.compose.foundation.gestures.PressGestureScopeImpl) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4b
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            boolean r5 = r4.isReleased
            if (r5 != 0) goto L51
            boolean r5 = r4.isCanceled
            if (r5 != 0) goto L51
            kotlinx.coroutines.sync.MutexImpl r5 = r4.mutex
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r5.lock(r0)
            if (r5 != r1) goto L4b
            return r1
        L4b:
            kotlinx.coroutines.sync.MutexImpl r5 = r4.mutex
            r0 = 0
            r5.unlock(r0)
        L51:
            boolean r4 = r4.isReleased
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.PressGestureScopeImpl.tryAwaitRelease(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo54toDpu2uoSUM(int i) {
        return this.$$delegate_0.mo54toDpu2uoSUM(i);
    }
}
