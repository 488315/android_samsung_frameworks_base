package com.android.systemui.communal.util;

import android.view.MotionEvent;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class UserTouchActivityNotifier {
    public Long lastNotification;
    public final PowerInteractor powerInteractor;
    public final int rateLimitMs;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.communal.util.UserTouchActivityNotifier$notifyActivity$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return UserTouchActivityNotifier.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            PowerInteractor.onUserTouch$default(UserTouchActivityNotifier.this.powerInteractor);
            return Unit.INSTANCE;
        }
    }

    public UserTouchActivityNotifier(CoroutineScope coroutineScope, PowerInteractor powerInteractor, int i) {
        this.scope = coroutineScope;
        this.powerInteractor = powerInteractor;
        this.rateLimitMs = i;
    }

    public final void notifyActivity(MotionEvent motionEvent) {
        Long l;
        int action = motionEvent.getAction();
        if (action != 0 && action != 1 && action != 3 && (l = this.lastNotification) != null) {
            if (motionEvent.getEventTime() - l.longValue() < this.rateLimitMs) {
                return;
            }
        }
        this.lastNotification = Long.valueOf(motionEvent.getEventTime());
        BuildersKt.launch$default(this.scope, null, null, new AnonymousClass2(null), 3);
    }
}
