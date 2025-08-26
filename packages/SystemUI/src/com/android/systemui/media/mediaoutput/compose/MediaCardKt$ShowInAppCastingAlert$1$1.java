package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.material3.SnackbarDuration;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaCardKt$ShowInAppCastingAlert$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ Function0 $onDismiss;
    final /* synthetic */ SessionController $sessionController;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ MediaInteraction $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$ShowInAppCastingAlert$1$1(Context context, SessionController sessionController, SnackbarHostState snackbarHostState, Function0 function0, MediaInteraction mediaInteraction, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$sessionController = sessionController;
        this.$snackbarHostState = snackbarHostState;
        this.$onDismiss = function0;
        this.$viewModel = mediaInteraction;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$ShowInAppCastingAlert$1$1(this.$context, this.$sessionController, this.$snackbarHostState, this.$onDismiss, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$ShowInAppCastingAlert$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            String appLabel = PackageManagerExtKt.getAppLabel(this.$context.getPackageManager(), this.$sessionController.getPackageName());
            String string = this.$context.getString(R.string.toast_in_app_casting_ps_ps, appLabel, appLabel);
            String string2 = this.$context.getString(R.string.action_open_ps_app, appLabel);
            SnackbarHostState snackbarHostState = this.$snackbarHostState;
            SnackbarDuration snackbarDuration = SnackbarDuration.Short;
            this.label = 1;
            obj = SnackbarHostState.showSnackbar$default(snackbarHostState, string, string2, snackbarDuration, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        SessionController sessionController = this.$sessionController;
        Function0 function0 = this.$onDismiss;
        MediaInteraction mediaInteraction = this.$viewModel;
        if (((SnackbarResult) obj) == SnackbarResult.ActionPerformed) {
            mediaInteraction.openCpApp(sessionController.getPackageName());
        }
        function0.invoke();
        return Unit.INSTANCE;
    }
}
