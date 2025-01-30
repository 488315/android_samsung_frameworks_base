package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.selects.SelectBuilderImpl;
import kotlinx.coroutines.selects.SelectInstance;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SelectAwaitOnCompletion extends JobNode {
    public final Function2 block;
    public final SelectInstance select;

    public SelectAwaitOnCompletion(SelectInstance selectInstance, Function2 function2) {
        this.select = selectInstance;
        this.block = function2;
    }

    @Override // kotlin.jvm.functions.Function1
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.CompletionHandlerBase
    public final void invoke(Throwable th) {
        SelectBuilderImpl selectBuilderImpl = (SelectBuilderImpl) this.select;
        if (selectBuilderImpl.trySelect()) {
            JobSupport job = getJob();
            Function2 function2 = this.block;
            Object m293x8adbf455 = job.m293x8adbf455();
            if (m293x8adbf455 instanceof CompletedExceptionally) {
                selectBuilderImpl.resumeSelectWithException(((CompletedExceptionally) m293x8adbf455).cause);
                return;
            }
            try {
                Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(selectBuilderImpl, function2, JobSupportKt.unboxState(m293x8adbf455)));
                int i = Result.$r8$clinit;
                DispatchedContinuationKt.resumeCancellableWith(intercepted, Unit.INSTANCE, null);
            } catch (Throwable th2) {
                int i2 = Result.$r8$clinit;
                selectBuilderImpl.resumeWith(new Result.Failure(th2));
                throw th2;
            }
        }
    }
}
