package androidx.compose.material3;

import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.focus.FocusOwnerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SearchBarDefaults$InputField$24$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FocusManager $focusManager;
    final /* synthetic */ boolean $shouldClearFocus;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SearchBarDefaults$InputField$24$1(boolean z, FocusManager focusManager, Continuation continuation) {
        super(2, continuation);
        this.$shouldClearFocus = z;
        this.$focusManager = focusManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SearchBarDefaults$InputField$24$1(this.$shouldClearFocus, this.$focusManager, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SearchBarDefaults$InputField$24$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$shouldClearFocus) {
                this.label = 1;
                if (DelayKt.delay(100L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        FocusOwnerImpl focusOwnerImpl = (FocusOwnerImpl) this.$focusManager;
        focusOwnerImpl.getClass();
        FocusDirection.Companion.getClass();
        focusOwnerImpl.m370clearFocusI7lrPNg(FocusDirection.Exit, false, true);
        return Unit.INSTANCE;
    }
}
