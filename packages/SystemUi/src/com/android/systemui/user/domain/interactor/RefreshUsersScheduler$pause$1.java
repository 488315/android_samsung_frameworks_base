package com.android.systemui.user.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.RefreshUsersScheduler$pause$1", m277f = "RefreshUsersScheduler.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class RefreshUsersScheduler$pause$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ RefreshUsersScheduler this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.RefreshUsersScheduler$pause$1$1", m277f = "RefreshUsersScheduler.kt", m278l = {49}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.user.domain.interactor.RefreshUsersScheduler$pause$1$1 */
    final class C35481 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ RefreshUsersScheduler this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C35481(RefreshUsersScheduler refreshUsersScheduler, Continuation<? super C35481> continuation) {
            super(2, continuation);
            this.this$0 = refreshUsersScheduler;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C35481(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C35481) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(3000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            RefreshUsersScheduler refreshUsersScheduler = this.this$0;
            refreshUsersScheduler.getClass();
            BuildersKt.launch$default(refreshUsersScheduler.applicationScope, refreshUsersScheduler.mainDispatcher, null, new RefreshUsersScheduler$unpauseAndRefresh$1(refreshUsersScheduler, null), 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RefreshUsersScheduler$pause$1(RefreshUsersScheduler refreshUsersScheduler, Continuation<? super RefreshUsersScheduler$pause$1> continuation) {
        super(2, continuation);
        this.this$0 = refreshUsersScheduler;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RefreshUsersScheduler$pause$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RefreshUsersScheduler$pause$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        RefreshUsersScheduler refreshUsersScheduler = this.this$0;
        refreshUsersScheduler.isPaused = true;
        Job job = refreshUsersScheduler.scheduledUnpauseJob;
        if (job != null) {
            job.cancel(null);
        }
        RefreshUsersScheduler refreshUsersScheduler2 = this.this$0;
        refreshUsersScheduler2.scheduledUnpauseJob = BuildersKt.launch$default(refreshUsersScheduler2.applicationScope, null, null, new C35481(refreshUsersScheduler2, null), 3);
        return Unit.INSTANCE;
    }
}
