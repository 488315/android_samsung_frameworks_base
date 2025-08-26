package com.android.systemui.notifications.ui.composable;

import androidx.compose.animation.core.SpringSpec;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.gestures.ScrollExtensionsKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class NotificationsKt$scrollNotificationStack$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $remainingDelta;
    final /* synthetic */ ScrollState $scrollState;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.notifications.ui.composable.NotificationsKt$scrollNotificationStack$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $remainingDelta;
        final /* synthetic */ ScrollState $scrollState;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ScrollState scrollState, int i, Continuation continuation) {
            super(2, continuation);
            this.$scrollState = scrollState;
            this.$remainingDelta = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$scrollState, this.$remainingDelta, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ScrollState scrollState = this.$scrollState;
                int i2 = this.$remainingDelta;
                this.label = 1;
                ScrollState.Companion companion = ScrollState.Companion;
                Object objAnimateScrollBy = ScrollExtensionsKt.animateScrollBy(scrollState, i2 - scrollState.getValue(), new SpringSpec(0.0f, 0.0f, null, 7, null), this);
                if (objAnimateScrollBy != obj2) {
                    objAnimateScrollBy = Unit.INSTANCE;
                }
                if (objAnimateScrollBy == obj2) {
                    return obj2;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationsKt$scrollNotificationStack$2(ScrollState scrollState, int i, Continuation continuation) {
        super(2, continuation);
        this.$scrollState = scrollState;
        this.$remainingDelta = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationsKt$scrollNotificationStack$2 notificationsKt$scrollNotificationStack$2 = new NotificationsKt$scrollNotificationStack$2(this.$scrollState, this.$remainingDelta, continuation);
        notificationsKt$scrollNotificationStack$2.L$0 = obj;
        return notificationsKt$scrollNotificationStack$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationsKt$scrollNotificationStack$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$scrollState, this.$remainingDelta, null), 3);
    }
}
