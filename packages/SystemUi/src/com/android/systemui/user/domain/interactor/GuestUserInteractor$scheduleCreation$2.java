package com.android.systemui.user.domain.interactor;

import android.util.Log;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.GuestUserInteractor$scheduleCreation$2", m277f = "GuestUserInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getApplicationRestrictionsInternal}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class GuestUserInteractor$scheduleCreation$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ GuestUserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuestUserInteractor$scheduleCreation$2(GuestUserInteractor guestUserInteractor, Continuation<? super GuestUserInteractor$scheduleCreation$2> continuation) {
        super(2, continuation);
        this.this$0 = guestUserInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GuestUserInteractor$scheduleCreation$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GuestUserInteractor$scheduleCreation$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            GuestUserInteractor guestUserInteractor = this.this$0;
            this.label = 1;
            int i2 = GuestUserInteractor.$r8$clinit;
            guestUserInteractor.getClass();
            obj = BuildersKt.withContext(guestUserInteractor.backgroundDispatcher, new GuestUserInteractor$createInBackground$2(guestUserInteractor, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        int intValue = ((Number) obj).intValue();
        ((UserRepositoryImpl) this.this$0.repository).isGuestUserCreationScheduled.set(false);
        ((UserRepositoryImpl) this.this$0.repository).isGuestUserResetting = false;
        if (intValue == -10000) {
            Log.w("GuestUserInteractor", "Could not create new guest while exiting existing guest");
            this.this$0.refreshUsersScheduler.refreshIfNotPaused();
        }
        return Unit.INSTANCE;
    }
}
