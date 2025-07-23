package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Continuation continuation) {
            super(2, continuation);
            this.this$0 = originalUnseenKeyguardCoordinator;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object clearUnseenNotificationsWhenShadeIsExpanded;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                this.label = 1;
                clearUnseenNotificationsWhenShadeIsExpanded = originalUnseenKeyguardCoordinator.clearUnseenNotificationsWhenShadeIsExpanded(this);
                if (clearUnseenNotificationsWhenShadeIsExpanded == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ OriginalUnseenKeyguardCoordinator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Continuation continuation) {
            super(2, continuation);
            this.this$0 = originalUnseenKeyguardCoordinator;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object markHeadsUpNotificationsAsSeen;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator = this.this$0;
                this.label = 1;
                markHeadsUpNotificationsAsSeen = originalUnseenKeyguardCoordinator.markHeadsUpNotificationsAsSeen(this);
                if (markHeadsUpNotificationsAsSeen == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2(OriginalUnseenKeyguardCoordinator originalUnseenKeyguardCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = originalUnseenKeyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2 originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2 = new OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2(this.this$0, continuation);
        originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2.L$0 = obj;
        return originalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 7);
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 7);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((OriginalUnseenKeyguardCoordinator$trackSeenNotificationsWhileUnlocked$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
