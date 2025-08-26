package androidx.datastore.core;

import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public final class SingleProcessCoordinator implements InterProcessCoordinator {
    public final MutexImpl mutex = MutexKt.Mutex$default();
    public final AtomicInt version = new AtomicInt(0);
    public final SafeFlow updateNotifications = new SafeFlow(new SingleProcessCoordinator$updateNotifications$1(null));

    /* renamed from: androidx.datastore.core.SingleProcessCoordinator$lock$1, reason: invalid class name */
    final class AnonymousClass1<T> extends ContinuationImpl {
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
            return SingleProcessCoordinator.this.lock(null, this);
        }
    }

    /* renamed from: androidx.datastore.core.SingleProcessCoordinator$tryLock$1, reason: invalid class name and case insensitive filesystem */
    final class C07601<T> extends ContinuationImpl {
        Object L$0;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public C07601(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SingleProcessCoordinator.this.tryLock(null, this);
        }
    }

    public SingleProcessCoordinator(String str) {
    }

    public final Object getVersion() {
        return new Integer(this.version.delegate.get());
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0061, code lost:
    
        if (r8 == r1) goto L25;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r6v0, types: [androidx.datastore.core.SingleProcessCoordinator] */
    /* JADX WARN: Type inference failed for: r6v1, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v4, types: [kotlinx.coroutines.sync.Mutex] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object lock(Function1 function1, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Object obj;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objMo781invoke = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objMo781invoke);
                MutexImpl mutexImpl = this.mutex;
                anonymousClass1.L$0 = function1;
                anonymousClass1.L$1 = mutexImpl;
                anonymousClass1.label = 1;
                Object objLock = mutexImpl.lock(anonymousClass1);
                obj = mutexImpl;
                if (objLock != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                Mutex mutex = (Mutex) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objMo781invoke);
                this = mutex;
                return objMo781invoke;
            }
            Object obj2 = (Mutex) anonymousClass1.L$1;
            function1 = (Function1) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objMo781invoke);
            obj = obj2;
            anonymousClass1.L$0 = obj;
            anonymousClass1.L$1 = null;
            anonymousClass1.label = 2;
            objMo781invoke = function1.mo781invoke(anonymousClass1);
            this = obj;
        } finally {
            this.unlock(null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object tryLock(Function2 function2, ContinuationImpl continuationImpl) {
        C07601 c07601;
        Mutex mutex;
        boolean z;
        Throwable th;
        if (continuationImpl instanceof C07601) {
            c07601 = (C07601) continuationImpl;
            int i = c07601.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07601.label = i - Integer.MIN_VALUE;
            } else {
                c07601 = new C07601(continuationImpl);
            }
        }
        Object obj = c07601.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07601.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            z = c07601.Z$0;
            mutex = (Mutex) c07601.L$0;
            try {
                ResultKt.throwOnFailure(obj);
                if (z) {
                    mutex.unlock(null);
                }
                return obj;
            } catch (Throwable th2) {
                th = th2;
                if (z) {
                }
                throw th;
            }
        }
        ResultKt.throwOnFailure(obj);
        MutexImpl mutexImpl = this.mutex;
        boolean zTryLock = mutexImpl.tryLock();
        try {
            Object objValueOf = Boolean.valueOf(zTryLock);
            c07601.L$0 = mutexImpl;
            c07601.Z$0 = zTryLock;
            c07601.label = 1;
            Object objInvoke = function2.invoke(objValueOf, c07601);
            if (objInvoke == obj2) {
                return obj2;
            }
            mutex = mutexImpl;
            z = zTryLock;
            obj = objInvoke;
            if (z) {
            }
            return obj;
        } catch (Throwable th3) {
            mutex = mutexImpl;
            z = zTryLock;
            th = th3;
            if (z) {
                mutex.unlock(null);
            }
            throw th;
        }
    }
}
