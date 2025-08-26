package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.material3.SnackbarDuration;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.ext.PackageManagerExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaCardKt$ControlArea$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<List<MediaAction>> $actions$delegate;
    final /* synthetic */ Context $context;
    final /* synthetic */ SessionController $sessionController;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ MediaInteraction $viewModel;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public MediaCardKt$ControlArea$2$1(State<? extends List<MediaAction>> state, Context context, SessionController sessionController, SnackbarHostState snackbarHostState, MediaInteraction mediaInteraction, Continuation continuation) {
        super(2, continuation);
        this.$actions$delegate = state;
        this.$context = context;
        this.$sessionController = sessionController;
        this.$snackbarHostState = snackbarHostState;
        this.$viewModel = mediaInteraction;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$ControlArea$2$1(this.$actions$delegate, this.$context, this.$sessionController, this.$snackbarHostState, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$ControlArea$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object next;
        SessionController sessionController;
        MediaInteraction mediaInteraction;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Iterator it = ((List) this.$actions$delegate.getValue()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (((MediaAction) next).id == -4) {
                    break;
                }
            }
            MediaAction mediaAction = (MediaAction) next;
            if (mediaAction != null) {
                Context context = this.$context;
                SessionController sessionController2 = this.$sessionController;
                SnackbarHostState snackbarHostState = this.$snackbarHostState;
                MediaInteraction mediaInteraction2 = this.$viewModel;
                String appLabel = PackageManagerExtKt.getAppLabel(context.getPackageManager(), sessionController2.getPackageName());
                String string = context.getString(R.string.toast_app_launch_in_ps, appLabel);
                String string2 = context.getString(R.string.action_open_ps_app, appLabel);
                SnackbarDuration snackbarDuration = SnackbarDuration.Short;
                this.L$0 = mediaAction;
                this.L$1 = sessionController2;
                this.L$2 = mediaInteraction2;
                this.label = 1;
                obj = SnackbarHostState.showSnackbar$default(snackbarHostState, string, string2, snackbarDuration, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                sessionController = sessionController2;
                mediaInteraction = mediaInteraction2;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        mediaInteraction = (MediaInteraction) this.L$2;
        sessionController = (SessionController) this.L$1;
        ResultKt.throwOnFailure(obj);
        if (((SnackbarResult) obj) == SnackbarResult.ActionPerformed) {
            mediaInteraction.openCpApp(sessionController.getPackageName());
        }
        sessionController.execute(-4L, 0L);
        return Unit.INSTANCE;
    }
}
