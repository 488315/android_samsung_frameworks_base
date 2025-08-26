package androidx.compose.foundation.lazy.layout;

import androidx.compose.ui.layout.OnGloballyPositionedModifier;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
public final class AwaitFirstLayoutModifier implements OnGloballyPositionedModifier {
    public SafeContinuation continuation;
    public boolean wasPositioned;

    /* renamed from: androidx.compose.foundation.lazy.layout.AwaitFirstLayoutModifier$waitForFirstLayout$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AwaitFirstLayoutModifier.this.waitForFirstLayout(this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object waitForFirstLayout(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Continuation continuation;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (!this.wasPositioned) {
                SafeContinuation safeContinuation = this.continuation;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = safeContinuation;
                anonymousClass1.label = 1;
                SafeContinuation safeContinuation2 = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(anonymousClass1));
                this.continuation = safeContinuation2;
                if (safeContinuation2.getOrThrow() == coroutineSingletons) {
                    return coroutineSingletons;
                }
                continuation = safeContinuation;
            }
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        continuation = (Continuation) anonymousClass1.L$1;
        ResultKt.throwOnFailure(obj);
        if (continuation != null) {
            int i3 = Result.$r8$clinit;
            continuation.resumeWith(Unit.INSTANCE);
        }
        return Unit.INSTANCE;
    }
}
