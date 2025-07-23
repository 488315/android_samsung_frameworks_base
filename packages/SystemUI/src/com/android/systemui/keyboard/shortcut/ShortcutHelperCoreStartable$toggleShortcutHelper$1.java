package com.android.systemui.keyboard.shortcut;

import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShortcutHelperCoreStartable$toggleShortcutHelper$1 extends SuspendLambda implements Function1 {
    final /* synthetic */ Integer $deviceId;
    int label;
    final /* synthetic */ ShortcutHelperCoreStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperCoreStartable$toggleShortcutHelper$1(ShortcutHelperCoreStartable shortcutHelperCoreStartable, Integer num, Continuation continuation) {
        super(1, continuation);
        this.this$0 = shortcutHelperCoreStartable;
        this.$deviceId = num;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new ShortcutHelperCoreStartable$toggleShortcutHelper$1(this.this$0, this.$deviceId, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((ShortcutHelperCoreStartable$toggleShortcutHelper$1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ShortcutHelperStateRepository shortcutHelperStateRepository = this.this$0.stateRepository;
            Integer num = this.$deviceId;
            this.label = 1;
            if (shortcutHelperStateRepository._state.getValue() instanceof ShortcutHelperState.Inactive) {
                obj2 = shortcutHelperStateRepository.show(num, this);
                if (obj2 != coroutineSingletons) {
                    obj2 = Unit.INSTANCE;
                }
            } else {
                shortcutHelperStateRepository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
                obj2 = Unit.INSTANCE;
            }
            if (obj2 == coroutineSingletons) {
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
