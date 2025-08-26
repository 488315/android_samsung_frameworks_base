package com.android.systemui.user.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class UserLogoutInteractor {
    public final CoroutineScope applicationScope;
    public final ReadonlyStateFlow isLogoutEnabled;
    public final UserRepository userRepository;

    /* renamed from: com.android.systemui.user.domain.interactor.UserLogoutInteractor$logOut$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserLogoutInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003e, code lost:
        
            if (((com.android.systemui.user.data.repository.UserRepositoryImpl) r5).logOutSecondaryUser(r4) == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0063, code lost:
        
            if (((com.android.systemui.user.data.repository.UserRepositoryImpl) r5).logOutToSystemUser(r4) == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0065, code lost:
        
            return r0;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (((Boolean) ((UserRepositoryImpl) UserLogoutInteractor.this.userRepository).isSecondaryUserLogoutEnabled.$$delegate_0.getValue()).booleanValue()) {
                    UserRepository userRepository = UserLogoutInteractor.this.userRepository;
                    this.label = 1;
                } else if (((Boolean) ((UserRepositoryImpl) UserLogoutInteractor.this.userRepository).isLogoutToSystemUserEnabled.$$delegate_0.getValue()).booleanValue()) {
                    UserRepository userRepository2 = UserLogoutInteractor.this.userRepository;
                    this.label = 2;
                }
            } else {
                if (i != 1 && i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public UserLogoutInteractor(UserRepository userRepository, CoroutineScope coroutineScope) {
        this.userRepository = userRepository;
        this.applicationScope = coroutineScope;
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(userRepositoryImpl.isSecondaryUserLogoutEnabled, userRepositoryImpl.isLogoutToSystemUserEnabled, UserLogoutInteractor$isLogoutEnabled$1.INSTANCE);
        SharingStarted.Companion.getClass();
        this.isLogoutEnabled = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }

    public final void logOut() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(null), 7);
    }
}
