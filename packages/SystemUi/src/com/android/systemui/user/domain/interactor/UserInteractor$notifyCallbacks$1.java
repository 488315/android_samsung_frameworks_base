package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.domain.interactor.UserInteractor;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$notifyCallbacks$1", m277f = "UserInteractor.kt", m278l = {852}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserInteractor$notifyCallbacks$1 extends SuspendLambda implements Function2 {
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$notifyCallbacks$1(UserInteractor userInteractor, Continuation<? super UserInteractor$notifyCallbacks$1> continuation) {
        super(2, continuation);
        this.this$0 = userInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserInteractor$notifyCallbacks$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserInteractor$notifyCallbacks$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        UserInteractor userInteractor;
        Mutex mutex;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserInteractor userInteractor2 = this.this$0;
            MutexImpl mutexImpl = userInteractor2.callbackMutex;
            this.L$0 = mutexImpl;
            this.L$1 = userInteractor2;
            this.label = 1;
            if (mutexImpl.lock(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            userInteractor = userInteractor2;
            mutex = mutexImpl;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            userInteractor = (UserInteractor) this.L$1;
            mutex = (Mutex) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        try {
            Iterator it = userInteractor.callbacks.iterator();
            while (it.hasNext()) {
                UserInteractor.UserCallback userCallback = (UserInteractor.UserCallback) it.next();
                if (userCallback.isEvictable()) {
                    it.remove();
                } else {
                    userCallback.onUserStateChanged();
                }
            }
            Unit unit = Unit.INSTANCE;
            ((MutexImpl) mutex).unlock(null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            ((MutexImpl) mutex).unlock(null);
            throw th;
        }
    }
}
