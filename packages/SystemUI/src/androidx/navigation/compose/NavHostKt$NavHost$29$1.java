package androidx.navigation.compose;

import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.navigation.NavBackStackEntry;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NavHostKt$NavHost$29$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NavBackStackEntry $backStackEntry;
    final /* synthetic */ SeekableTransitionState<NavBackStackEntry> $transitionState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NavHostKt$NavHost$29$1(SeekableTransitionState<NavBackStackEntry> seekableTransitionState, NavBackStackEntry navBackStackEntry, Continuation continuation) {
        super(2, continuation);
        this.$transitionState = seekableTransitionState;
        this.$backStackEntry = navBackStackEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NavHostKt$NavHost$29$1(this.$transitionState, this.$backStackEntry, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NavHostKt$NavHost$29$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (!Intrinsics.areEqual(((SnapshotMutableStateImpl) this.$transitionState.currentState$delegate).getValue(), this.$backStackEntry)) {
                SeekableTransitionState<NavBackStackEntry> seekableTransitionState = this.$transitionState;
                NavBackStackEntry navBackStackEntry = this.$backStackEntry;
                this.label = 1;
                if (SeekableTransitionState.animateTo$default(seekableTransitionState, navBackStackEntry, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
