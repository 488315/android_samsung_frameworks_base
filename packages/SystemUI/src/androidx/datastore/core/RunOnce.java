package androidx.datastore.core;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public abstract class RunOnce {
    public final MutexImpl runMutex = MutexKt.Mutex$default();
    public final CompletableDeferredImpl didRun = CompletableDeferredKt.CompletableDeferred$default();

    /* renamed from: androidx.datastore.core.RunOnce$runIfNeeded$1, reason: invalid class name */
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
            return RunOnce.this.runIfNeeded(this);
        }
    }

    public abstract Object doRun(ContinuationImpl continuationImpl);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r7v11, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object runIfNeeded(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        MutexImpl mutexImpl;
        Throwable th;
        Mutex mutex;
        RunOnce runOnce;
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
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.didRun.isCompleted()) {
                    return Unit.INSTANCE;
                }
                mutexImpl = this.runMutex;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = mutexImpl;
                anonymousClass1.label = 1;
                if (mutexImpl.lock(anonymousClass1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                mutex = (Mutex) anonymousClass1.L$1;
                runOnce = (RunOnce) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    CompletableDeferredImpl completableDeferredImpl = runOnce.didRun;
                    Unit unit = Unit.INSTANCE;
                    completableDeferredImpl.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit);
                    mutex.unlock(null);
                    return unit;
                } catch (Throwable th2) {
                    th = th2;
                    mutex.unlock(null);
                    throw th;
                }
            }
            ?? r7 = (Mutex) anonymousClass1.L$1;
            RunOnce runOnce2 = (RunOnce) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            mutexImpl = r7;
            this = runOnce2;
            if (this.didRun.isCompleted()) {
                Unit unit2 = Unit.INSTANCE;
                mutexImpl.unlock(null);
                return unit2;
            }
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = mutexImpl;
            anonymousClass1.label = 2;
            if (this.doRun(anonymousClass1) != coroutineSingletons) {
                runOnce = this;
                mutex = mutexImpl;
                CompletableDeferredImpl completableDeferredImpl2 = runOnce.didRun;
                Unit unit3 = Unit.INSTANCE;
                completableDeferredImpl2.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(unit3);
                mutex.unlock(null);
                return unit3;
            }
            return coroutineSingletons;
        } catch (Throwable th3) {
            MutexImpl mutexImpl2 = mutexImpl;
            th = th3;
            mutex = mutexImpl2;
            mutex.unlock(null);
            throw th;
        }
    }
}
