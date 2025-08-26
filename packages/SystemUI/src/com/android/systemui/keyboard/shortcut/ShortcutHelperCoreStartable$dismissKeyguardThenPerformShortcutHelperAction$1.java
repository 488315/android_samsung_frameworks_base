package com.android.systemui.keyboard.shortcut;

import com.android.systemui.plugins.ActivityStarter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1 implements ActivityStarter.OnDismissAction {
    public final /* synthetic */ Function1 $action;
    public final /* synthetic */ ShortcutHelperCoreStartable this$0;

    /* renamed from: com.android.systemui.keyboard.shortcut.ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $action;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$action = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$action, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Function1 function1 = this.$action;
                this.label = 1;
                if (function1.mo781invoke(this) == coroutineSingletons) {
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

    public ShortcutHelperCoreStartable$dismissKeyguardThenPerformShortcutHelperAction$1(ShortcutHelperCoreStartable shortcutHelperCoreStartable, Function1 function1) {
        this.this$0 = shortcutHelperCoreStartable;
        this.$action = function1;
    }

    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
    public final boolean onDismiss() {
        BuildersKt.launch$default(this.this$0.backgroundScope, null, null, new AnonymousClass1(this.$action, null), 3);
        return false;
    }
}
