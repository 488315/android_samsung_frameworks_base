package com.android.systemui.statusbar.notification.stack.ui.view;

import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class NotificationStatsLoggerImpl$logNotificationExpansionChange$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NotificationStatsLoggerImpl.ExpansionState $expansionState;
    int label;
    final /* synthetic */ NotificationStatsLoggerImpl this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLoggerImpl$logNotificationExpansionChange$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationStatsLoggerImpl.ExpansionState $expansionState;
        int label;
        final /* synthetic */ NotificationStatsLoggerImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(NotificationStatsLoggerImpl notificationStatsLoggerImpl, NotificationStatsLoggerImpl.ExpansionState expansionState, Continuation continuation) {
            super(2, continuation);
            this.this$0 = notificationStatsLoggerImpl;
            this.$expansionState = expansionState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$expansionState, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            IStatusBarService iStatusBarService = this.this$0.statusBarService;
            NotificationStatsLoggerImpl.ExpansionState expansionState = this.$expansionState;
            String str = expansionState.key;
            boolean z = expansionState.isUserAction;
            boolean z2 = expansionState.isExpanded;
            int i = expansionState.location;
            iStatusBarService.onNotificationExpansionChanged(str, z, z2, (i != 1 ? i != 2 ? i != 4 ? i != 8 ? i != 16 ? i != 64 ? NotificationVisibility.NotificationLocation.LOCATION_UNKNOWN : NotificationVisibility.NotificationLocation.LOCATION_GONE : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_HIDDEN : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_PEEKING : NotificationVisibility.NotificationLocation.LOCATION_MAIN_AREA : NotificationVisibility.NotificationLocation.LOCATION_HIDDEN_TOP : NotificationVisibility.NotificationLocation.LOCATION_FIRST_HEADS_UP).ordinal());
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationStatsLoggerImpl$logNotificationExpansionChange$1(NotificationStatsLoggerImpl notificationStatsLoggerImpl, NotificationStatsLoggerImpl.ExpansionState expansionState, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationStatsLoggerImpl;
        this.$expansionState = expansionState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NotificationStatsLoggerImpl$logNotificationExpansionChange$1(this.this$0, this.$expansionState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationStatsLoggerImpl$logNotificationExpansionChange$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NotificationStatsLoggerImpl notificationStatsLoggerImpl = this.this$0;
            CoroutineDispatcher coroutineDispatcher = notificationStatsLoggerImpl.bgDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(notificationStatsLoggerImpl, this.$expansionState, null);
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
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
}
