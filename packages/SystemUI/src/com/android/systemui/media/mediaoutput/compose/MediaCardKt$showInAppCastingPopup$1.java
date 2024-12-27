package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.material3.SnackbarDuration;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
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

final class MediaCardKt$showInAppCastingPopup$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ State $mediaInfo$delegate;
    final /* synthetic */ Function0 $onDismiss;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ MediaInteraction $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$showInAppCastingPopup$1(Context context, SnackbarHostState snackbarHostState, State state, MediaInteraction mediaInteraction, Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$snackbarHostState = snackbarHostState;
        this.$mediaInfo$delegate = state;
        this.$viewModel = mediaInteraction;
        this.$onDismiss = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$showInAppCastingPopup$1(this.$context, this.$snackbarHostState, this.$mediaInfo$delegate, this.$viewModel, this.$onDismiss, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$showInAppCastingPopup$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            String appLabel = PackageManagerExtKt.getAppLabel(this.$context.getPackageManager(), ((MediaInfo) this.$mediaInfo$delegate.getValue()).getPackageName());
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
        MediaInteraction mediaInteraction = this.$viewModel;
        Function0 function0 = this.$onDismiss;
        State state = this.$mediaInfo$delegate;
        if (((SnackbarResult) obj) == SnackbarResult.ActionPerformed) {
            mediaInteraction.execute((MediaInfo) state.getValue(), -2L, 0L);
        }
        function0.invoke();
        return Unit.INSTANCE;
    }
}
