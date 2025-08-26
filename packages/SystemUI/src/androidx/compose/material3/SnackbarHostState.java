package androidx.compose.material3;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public final class SnackbarHostState {
    public final MutexImpl mutex = MutexKt.Mutex$default();
    public final MutableState currentSnackbarData$delegate = SnapshotStateKt.mutableStateOf$default(null);

    final class SnackbarDataImpl implements SnackbarData {
        public final CancellableContinuation continuation;
        public final SnackbarVisuals visuals;

        public SnackbarDataImpl(SnackbarVisuals snackbarVisuals, CancellableContinuation cancellableContinuation) {
            this.visuals = snackbarVisuals;
            this.continuation = cancellableContinuation;
        }

        @Override // androidx.compose.material3.SnackbarData
        public final void dismiss() {
            CancellableContinuation cancellableContinuation = this.continuation;
            if (cancellableContinuation.isActive()) {
                int i = Result.$r8$clinit;
                cancellableContinuation.resumeWith(SnackbarResult.Dismissed);
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SnackbarDataImpl.class != obj.getClass()) {
                return false;
            }
            SnackbarDataImpl snackbarDataImpl = (SnackbarDataImpl) obj;
            return Intrinsics.areEqual(this.visuals, snackbarDataImpl.visuals) && Intrinsics.areEqual(this.continuation, snackbarDataImpl.continuation);
        }

        @Override // androidx.compose.material3.SnackbarData
        public final SnackbarVisuals getVisuals() {
            return this.visuals;
        }

        public final int hashCode() {
            return this.continuation.hashCode() + (this.visuals.hashCode() * 31);
        }

        @Override // androidx.compose.material3.SnackbarData
        public final void performAction() {
            CancellableContinuation cancellableContinuation = this.continuation;
            if (cancellableContinuation.isActive()) {
                int i = Result.$r8$clinit;
                cancellableContinuation.resumeWith(SnackbarResult.ActionPerformed);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final class SnackbarVisualsImpl implements SnackbarVisuals {
        public final String actionLabel;
        public final SnackbarDuration duration;
        public final String message;
        public final boolean withDismissAction;

        public SnackbarVisualsImpl(String str, String str2, boolean z, SnackbarDuration snackbarDuration) {
            this.message = str;
            this.actionLabel = str2;
            this.withDismissAction = z;
            this.duration = snackbarDuration;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SnackbarVisualsImpl.class != obj.getClass()) {
                return false;
            }
            SnackbarVisualsImpl snackbarVisualsImpl = (SnackbarVisualsImpl) obj;
            return Intrinsics.areEqual(this.message, snackbarVisualsImpl.message) && Intrinsics.areEqual(this.actionLabel, snackbarVisualsImpl.actionLabel) && this.withDismissAction == snackbarVisualsImpl.withDismissAction && this.duration == snackbarVisualsImpl.duration;
        }

        @Override // androidx.compose.material3.SnackbarVisuals
        public final String getActionLabel() {
            return this.actionLabel;
        }

        @Override // androidx.compose.material3.SnackbarVisuals
        public final SnackbarDuration getDuration() {
            return this.duration;
        }

        @Override // androidx.compose.material3.SnackbarVisuals
        public final String getMessage() {
            return this.message;
        }

        @Override // androidx.compose.material3.SnackbarVisuals
        public final boolean getWithDismissAction() {
            return this.withDismissAction;
        }

        public final int hashCode() {
            int iHashCode = this.message.hashCode() * 31;
            String str = this.actionLabel;
            return this.duration.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((iHashCode + (str != null ? str.hashCode() : 0)) * 31, 31, this.withDismissAction);
        }
    }

    /* renamed from: androidx.compose.material3.SnackbarHostState$showSnackbar$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SnackbarHostState.this.showSnackbar(null, this);
        }
    }

    public static Object showSnackbar$default(SnackbarHostState snackbarHostState, String str, String str2, SnackbarDuration snackbarDuration, SuspendLambda suspendLambda) {
        snackbarHostState.getClass();
        return snackbarHostState.showSnackbar(new SnackbarVisualsImpl(str, str2, false, snackbarDuration), suspendLambda);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r7v0, types: [androidx.compose.material3.SnackbarHostState, java.lang.Object, kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v1, types: [androidx.compose.material3.SnackbarHostState, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v6, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object showSnackbar(SnackbarVisuals snackbarVisuals, ContinuationImpl continuationImpl) {
        AnonymousClass2 anonymousClass2;
        MutexImpl mutexImpl;
        ?? r7;
        SnackbarHostState snackbarHostState;
        Throwable th;
        Mutex mutex;
        if (continuationImpl instanceof AnonymousClass2) {
            anonymousClass2 = (AnonymousClass2) continuationImpl;
            int i = anonymousClass2.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass2.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass2 = new AnonymousClass2(continuationImpl);
            }
        }
        Object obj = anonymousClass2.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass2.label;
        try {
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    mutexImpl = this.mutex;
                    anonymousClass2.L$0 = this;
                    anonymousClass2.L$1 = snackbarVisuals;
                    anonymousClass2.L$2 = mutexImpl;
                    anonymousClass2.label = 1;
                    this = this;
                    if (mutexImpl.lock(anonymousClass2) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    mutex = (Mutex) anonymousClass2.L$2;
                    snackbarHostState = (SnackbarHostState) anonymousClass2.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                        ((SnapshotMutableStateImpl) snackbarHostState.currentSnackbarData$delegate).setValue(null);
                        mutex.unlock(null);
                        return obj;
                    } catch (Throwable th2) {
                        th = th2;
                        ((SnapshotMutableStateImpl) snackbarHostState.currentSnackbarData$delegate).setValue(null);
                        throw th;
                    }
                }
                ?? r72 = (Mutex) anonymousClass2.L$2;
                snackbarVisuals = (SnackbarVisuals) anonymousClass2.L$1;
                SnackbarHostState snackbarHostState2 = (SnackbarHostState) anonymousClass2.L$0;
                ResultKt.throwOnFailure(obj);
                mutexImpl = r72;
                r7 = snackbarHostState2;
                anonymousClass2.L$0 = r7;
                anonymousClass2.L$1 = snackbarVisuals;
                anonymousClass2.L$2 = mutexImpl;
                anonymousClass2.L$3 = anonymousClass2;
                anonymousClass2.label = 2;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(anonymousClass2), 1);
                cancellableContinuationImpl.initCancellability();
                ((SnapshotMutableStateImpl) r7.currentSnackbarData$delegate).setValue(new SnackbarDataImpl(snackbarVisuals, cancellableContinuationImpl));
                Object result = cancellableContinuationImpl.getResult();
                if (result != coroutineSingletons) {
                    snackbarHostState = r7;
                    mutex = mutexImpl;
                    obj = result;
                    ((SnapshotMutableStateImpl) snackbarHostState.currentSnackbarData$delegate).setValue(null);
                    mutex.unlock(null);
                    return obj;
                }
                return coroutineSingletons;
            } catch (Throwable th3) {
                snackbarHostState = r7;
                th = th3;
                ((SnapshotMutableStateImpl) snackbarHostState.currentSnackbarData$delegate).setValue(null);
                throw th;
            }
        } catch (Throwable th4) {
            unlock(null);
            throw th4;
        }
    }
}
