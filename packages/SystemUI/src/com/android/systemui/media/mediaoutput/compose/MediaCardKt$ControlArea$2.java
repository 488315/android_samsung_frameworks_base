package com.android.systemui.media.mediaoutput.compose;

import android.content.Context;
import androidx.compose.material3.SnackbarDuration;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaCardKt$ControlArea$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ State $actions$delegate;
    final /* synthetic */ Context $context;
    final /* synthetic */ State $mediaInfo$delegate;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ MediaInteraction $viewModel;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$ControlArea$2(State state, State state2, Context context, SnackbarHostState snackbarHostState, MediaInteraction mediaInteraction, Continuation continuation) {
        super(2, continuation);
        this.$actions$delegate = state;
        this.$mediaInfo$delegate = state2;
        this.$context = context;
        this.$snackbarHostState = snackbarHostState;
        this.$viewModel = mediaInteraction;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$ControlArea$2(this.$actions$delegate, this.$mediaInfo$delegate, this.$context, this.$snackbarHostState, this.$viewModel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$ControlArea$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        MediaInfo mediaInfo;
        MediaInfo mediaInfo2;
        MediaInteraction mediaInteraction;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Iterator it = ((List) this.$actions$delegate.getValue()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj2 = null;
                    break;
                }
                obj2 = it.next();
                if (((MediaAction) obj2).id == -4) {
                    break;
                }
            }
            if (((MediaAction) obj2) != null && (mediaInfo = (MediaInfo) this.$mediaInfo$delegate.getValue()) != null) {
                Context context = this.$context;
                SnackbarHostState snackbarHostState = this.$snackbarHostState;
                MediaInteraction mediaInteraction2 = this.$viewModel;
                String appLabel = PackageManagerExtKt.getAppLabel(context.getPackageManager(), mediaInfo.getPackageName());
                String string = context.getString(R.string.toast_app_launch_in_ps, appLabel);
                String string2 = context.getString(R.string.action_open_ps_app, appLabel);
                SnackbarDuration snackbarDuration = SnackbarDuration.Short;
                this.L$0 = mediaInfo;
                this.L$1 = mediaInteraction2;
                this.L$2 = mediaInfo;
                this.label = 1;
                Object showSnackbar$default = SnackbarHostState.showSnackbar$default(snackbarHostState, string, string2, snackbarDuration, this);
                if (showSnackbar$default == coroutineSingletons) {
                    return coroutineSingletons;
                }
                mediaInfo2 = mediaInfo;
                mediaInteraction = mediaInteraction2;
                obj = showSnackbar$default;
            }
            return Unit.INSTANCE;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        mediaInfo2 = (MediaInfo) this.L$2;
        mediaInteraction = (MediaInteraction) this.L$1;
        ResultKt.throwOnFailure(obj);
        if (((SnackbarResult) obj) == SnackbarResult.ActionPerformed) {
            mediaInteraction.execute(mediaInfo2, -2L, 0L);
        }
        mediaInteraction.execute(mediaInfo2, -4L, 0L);
        return Unit.INSTANCE;
    }
}
