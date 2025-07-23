package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaCardKt$ShowStopBroadcastingAlert$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $body;
    final /* synthetic */ String $negative;
    final /* synthetic */ Function0 $onAction;
    final /* synthetic */ Function0 $onDismiss;
    final /* synthetic */ String $positive;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ String $title;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$ShowStopBroadcastingAlert$1$1(SnackbarHostState snackbarHostState, String str, String str2, String str3, String str4, Function0 function0, Function0 function02, Continuation continuation) {
        super(2, continuation);
        this.$snackbarHostState = snackbarHostState;
        this.$title = str;
        this.$body = str2;
        this.$positive = str3;
        this.$negative = str4;
        this.$onAction = function0;
        this.$onDismiss = function02;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$ShowStopBroadcastingAlert$1$1(this.$snackbarHostState, this.$title, this.$body, this.$positive, this.$negative, this.$onAction, this.$onDismiss, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$ShowStopBroadcastingAlert$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SnackbarHostState snackbarHostState = this.$snackbarHostState;
            SnackbarDialogImpl snackbarDialogImpl = new SnackbarDialogImpl(this.$title, this.$body, this.$positive, this.$negative);
            this.label = 1;
            obj = snackbarHostState.showSnackbar(snackbarDialogImpl, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Function0 function0 = this.$onAction;
        Function0 function02 = this.$onDismiss;
        if (((SnackbarResult) obj) == SnackbarResult.ActionPerformed) {
            function0.invoke();
        }
        function02.invoke();
        return Unit.INSTANCE;
    }
}
