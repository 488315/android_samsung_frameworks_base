package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;

final class UserSwitcherInteractor$notifyCallbacks$1 extends SuspendLambda implements Function2 {
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ UserSwitcherInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherInteractor$notifyCallbacks$1(UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherInteractor$notifyCallbacks$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherInteractor$notifyCallbacks$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        UserSwitcherInteractor userSwitcherInteractor;
        Mutex mutex;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserSwitcherInteractor userSwitcherInteractor2 = this.this$0;
            MutexImpl mutexImpl = userSwitcherInteractor2.callbackMutex;
            this.L$0 = mutexImpl;
            this.L$1 = userSwitcherInteractor2;
            this.label = 1;
            if (mutexImpl.lock(null, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            userSwitcherInteractor = userSwitcherInteractor2;
            mutex = mutexImpl;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            userSwitcherInteractor = (UserSwitcherInteractor) this.L$1;
            mutex = (Mutex) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        try {
            Iterator it = userSwitcherInteractor.callbacks.iterator();
            while (it.hasNext()) {
                UserSwitcherInteractor.UserCallback userCallback = (UserSwitcherInteractor.UserCallback) it.next();
                if (userCallback.isEvictable()) {
                    it.remove();
                } else {
                    userCallback.onUserStateChanged();
                }
            }
            Unit unit = Unit.INSTANCE;
            mutex.unlock(null);
            return Unit.INSTANCE;
        } catch (Throwable th) {
            mutex.unlock(null);
            throw th;
        }
    }
}
