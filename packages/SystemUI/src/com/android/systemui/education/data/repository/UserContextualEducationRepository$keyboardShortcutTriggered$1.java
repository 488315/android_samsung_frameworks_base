package com.android.systemui.education.data.repository;

import android.hardware.input.InputManager;
import android.hardware.input.KeyGestureEvent;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.contextualeducation.GestureType;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class UserContextualEducationRepository$keyboardShortcutTriggered$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserContextualEducationRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserContextualEducationRepository$keyboardShortcutTriggered$1(UserContextualEducationRepository userContextualEducationRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userContextualEducationRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserContextualEducationRepository$keyboardShortcutTriggered$1 userContextualEducationRepository$keyboardShortcutTriggered$1 = new UserContextualEducationRepository$keyboardShortcutTriggered$1(this.this$0, continuation);
        userContextualEducationRepository$keyboardShortcutTriggered$1.L$0 = obj;
        return userContextualEducationRepository$keyboardShortcutTriggered$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserContextualEducationRepository$keyboardShortcutTriggered$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.input.InputManager$KeyGestureEventListener, com.android.systemui.education.data.repository.UserContextualEducationRepository$keyboardShortcutTriggered$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new InputManager.KeyGestureEventListener() { // from class: com.android.systemui.education.data.repository.UserContextualEducationRepository$keyboardShortcutTriggered$1$listener$1
                public final void onKeyGestureEvent(KeyGestureEvent keyGestureEvent) {
                    if (keyGestureEvent.getKeyGestureType() == 21) {
                        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, GestureType.ALL_APPS, "UserContextualEducationRepository");
                    }
                }
            };
            this.this$0.inputManager.registerKeyGestureEventListener(new Executor() { // from class: com.android.systemui.education.data.repository.UserContextualEducationRepository$keyboardShortcutTriggered$1.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            }, r1);
            final UserContextualEducationRepository userContextualEducationRepository = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.education.data.repository.UserContextualEducationRepository$keyboardShortcutTriggered$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    userContextualEducationRepository.inputManager.unregisterKeyGestureEventListener(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
