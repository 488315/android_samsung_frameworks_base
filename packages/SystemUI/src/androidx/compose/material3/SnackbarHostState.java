package androidx.compose.material3;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.Result;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnackbarHostState {
    public final MutexImpl mutex = MutexKt.Mutex$default();
    public final MutableState currentSnackbarData$delegate = SnapshotStateKt.mutableStateOf$default(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = this.message.hashCode() * 31;
            String str = this.actionLabel;
            return this.duration.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode + (str != null ? str.hashCode() : 0)) * 31, 31, this.withDismissAction);
        }
    }

    public static Object showSnackbar$default(SnackbarHostState snackbarHostState, String str, String str2, SnackbarDuration snackbarDuration, SuspendLambda suspendLambda) {
        snackbarHostState.getClass();
        return snackbarHostState.showSnackbar(new SnackbarVisualsImpl(str, str2, false, snackbarDuration), suspendLambda);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0069, code lost:
    
        if (r9.lock(r0) == r1) goto L25;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /* JADX WARN: Type inference failed for: r7v0, types: [androidx.compose.material3.SnackbarHostState, java.lang.Object, kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v1, types: [androidx.compose.material3.SnackbarHostState, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v6, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object showSnackbar(androidx.compose.material3.SnackbarVisuals r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof androidx.compose.material3.SnackbarHostState$showSnackbar$2
            if (r0 == 0) goto L13
            r0 = r9
            androidx.compose.material3.SnackbarHostState$showSnackbar$2 r0 = (androidx.compose.material3.SnackbarHostState$showSnackbar$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.material3.SnackbarHostState$showSnackbar$2 r0 = new androidx.compose.material3.SnackbarHostState$showSnackbar$2
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L58
            if (r2 == r4) goto L46
            if (r2 != r3) goto L3e
            java.lang.Object r7 = r0.L$3
            androidx.compose.material3.SnackbarHostState$showSnackbar$2 r7 = (androidx.compose.material3.SnackbarHostState$showSnackbar$2) r7
            java.lang.Object r7 = r0.L$2
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r8 = r0.L$1
            androidx.compose.material3.SnackbarVisuals r8 = (androidx.compose.material3.SnackbarVisuals) r8
            java.lang.Object r8 = r0.L$0
            androidx.compose.material3.SnackbarHostState r8 = (androidx.compose.material3.SnackbarHostState) r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L3b
            goto L99
        L3b:
            r9 = move-exception
            goto La9
        L3e:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L46:
            java.lang.Object r7 = r0.L$2
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r8 = r0.L$1
            androidx.compose.material3.SnackbarVisuals r8 = (androidx.compose.material3.SnackbarVisuals) r8
            java.lang.Object r2 = r0.L$0
            androidx.compose.material3.SnackbarHostState r2 = (androidx.compose.material3.SnackbarHostState) r2
            kotlin.ResultKt.throwOnFailure(r9)
            r9 = r7
            r7 = r2
            goto L6c
        L58:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.sync.MutexImpl r9 = r7.mutex
            r0.L$0 = r7
            r0.L$1 = r8
            r0.L$2 = r9
            r0.label = r4
            java.lang.Object r2 = r9.lock(r0)
            if (r2 != r1) goto L6c
            goto L94
        L6c:
            r0.L$0 = r7     // Catch: java.lang.Throwable -> La4
            r0.L$1 = r8     // Catch: java.lang.Throwable -> La4
            r0.L$2 = r9     // Catch: java.lang.Throwable -> La4
            r0.L$3 = r0     // Catch: java.lang.Throwable -> La4
            r0.label = r3     // Catch: java.lang.Throwable -> La4
            kotlinx.coroutines.CancellableContinuationImpl r2 = new kotlinx.coroutines.CancellableContinuationImpl     // Catch: java.lang.Throwable -> La4
            kotlin.coroutines.Continuation r0 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r0)     // Catch: java.lang.Throwable -> La4
            r2.<init>(r0, r4)     // Catch: java.lang.Throwable -> La4
            r2.initCancellability()     // Catch: java.lang.Throwable -> La4
            androidx.compose.material3.SnackbarHostState$SnackbarDataImpl r0 = new androidx.compose.material3.SnackbarHostState$SnackbarDataImpl     // Catch: java.lang.Throwable -> La4
            r0.<init>(r8, r2)     // Catch: java.lang.Throwable -> La4
            androidx.compose.runtime.MutableState r8 = r7.currentSnackbarData$delegate     // Catch: java.lang.Throwable -> La4
            androidx.compose.runtime.SnapshotMutableStateImpl r8 = (androidx.compose.runtime.SnapshotMutableStateImpl) r8     // Catch: java.lang.Throwable -> La4
            r8.setValue(r0)     // Catch: java.lang.Throwable -> La4
            java.lang.Object r8 = r2.getResult()     // Catch: java.lang.Throwable -> La4
            if (r8 != r1) goto L95
        L94:
            return r1
        L95:
            r6 = r8
            r8 = r7
            r7 = r9
            r9 = r6
        L99:
            androidx.compose.runtime.MutableState r8 = r8.currentSnackbarData$delegate     // Catch: java.lang.Throwable -> Lb1
            androidx.compose.runtime.SnapshotMutableStateImpl r8 = (androidx.compose.runtime.SnapshotMutableStateImpl) r8     // Catch: java.lang.Throwable -> Lb1
            r8.setValue(r5)     // Catch: java.lang.Throwable -> Lb1
            r7.unlock(r5)
            return r9
        La4:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r9
            r9 = r6
        La9:
            androidx.compose.runtime.MutableState r8 = r8.currentSnackbarData$delegate     // Catch: java.lang.Throwable -> Lb1
            androidx.compose.runtime.SnapshotMutableStateImpl r8 = (androidx.compose.runtime.SnapshotMutableStateImpl) r8     // Catch: java.lang.Throwable -> Lb1
            r8.setValue(r5)     // Catch: java.lang.Throwable -> Lb1
            throw r9     // Catch: java.lang.Throwable -> Lb1
        Lb1:
            r8 = move-exception
            r7.unlock(r5)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SnackbarHostState.showSnackbar(androidx.compose.material3.SnackbarVisuals, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
