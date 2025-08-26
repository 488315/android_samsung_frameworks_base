package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import android.content.Intent;
import androidx.compose.material3.SnackbarDuration;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaCardKt$ShowCastingErrorAlert$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ RouteDevice $device;
    final /* synthetic */ Function1 $onAction;
    final /* synthetic */ Function0 $onDismiss;
    final /* synthetic */ SessionController $sessionController;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ String $text;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$ShowCastingErrorAlert$1$1(RouteDevice routeDevice, SnackbarHostState snackbarHostState, String str, Context context, SessionController sessionController, Function0 function0, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$device = routeDevice;
        this.$snackbarHostState = snackbarHostState;
        this.$text = str;
        this.$context = context;
        this.$sessionController = sessionController;
        this.$onDismiss = function0;
        this.$onAction = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$ShowCastingErrorAlert$1$1(this.$device, this.$snackbarHostState, this.$text, this.$context, this.$sessionController, this.$onDismiss, this.$onAction, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$ShowCastingErrorAlert$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0044  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Intent intent;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Intent targetIntent = this.$device.getTargetIntent();
            if (targetIntent != null) {
                String appLabel = PackageManagerExtKt.getAppLabel(this.$context.getPackageManager(), this.$sessionController.getPackageName());
                String string = appLabel != null ? this.$context.getString(R.string.action_open_ps_app, appLabel) : null;
                SnackbarHostState snackbarHostState = this.$snackbarHostState;
                String str = this.$text;
                SnackbarDuration snackbarDuration = SnackbarDuration.Short;
                this.L$0 = targetIntent;
                this.label = 1;
                Object objShowSnackbar$default = SnackbarHostState.showSnackbar$default(snackbarHostState, str, string, snackbarDuration, this);
                if (objShowSnackbar$default == coroutineSingletons) {
                    return coroutineSingletons;
                }
                intent = targetIntent;
                obj = objShowSnackbar$default;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            intent = (Intent) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Function0 function0 = this.$onDismiss;
        Function1 function1 = this.$onAction;
        if (((SnackbarResult) obj) == SnackbarResult.ActionPerformed && intent != null) {
            function1.mo781invoke(intent);
        }
        function0.invoke();
        return Unit.INSTANCE;
    }
}
