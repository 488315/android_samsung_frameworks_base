package com.android.systemui.media.mediaoutput.compose;

import android.util.Log;
import androidx.compose.runtime.State;
import androidx.navigation.NavBackStackEntry;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class MediaOutputHostKt$MediaOutputHost$1$2$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State $backStackEntry$delegate;
    int label;

    public MediaOutputHostKt$MediaOutputHost$1$2$2$1(State state, Continuation continuation) {
        super(2, continuation);
        this.$backStackEntry$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaOutputHostKt$MediaOutputHost$1$2$2$1(this.$backStackEntry$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputHostKt$MediaOutputHost$1$2$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) this.$backStackEntry$delegate.getValue();
        if (navBackStackEntry != null) {
            Log.d("MediaOutputHost", "current entry : " + navBackStackEntry + ", " + navBackStackEntry.getArguments());
        }
        return Unit.INSTANCE;
    }
}
