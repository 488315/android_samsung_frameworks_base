package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.material3.SnackbarHostState;
import androidx.compose.material3.SnackbarResult;
import androidx.compose.runtime.MutableState;
import com.android.systemui.media.mediaoutput.compose.widget.SnackbarDialogImpl;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaCardKt$DeviceListItem$5 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $body;
    final /* synthetic */ MutableState $checkStopBroadcasting$delegate;
    final /* synthetic */ AudioDevice $device;
    final /* synthetic */ String $negative;
    final /* synthetic */ String $positive;
    final /* synthetic */ SnackbarHostState $snackbarHostState;
    final /* synthetic */ String $title;
    final /* synthetic */ AudioPathInteraction $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$DeviceListItem$5(SnackbarHostState snackbarHostState, String str, String str2, String str3, String str4, AudioPathInteraction audioPathInteraction, AudioDevice audioDevice, MutableState mutableState, Continuation continuation) {
        super(2, continuation);
        this.$snackbarHostState = snackbarHostState;
        this.$title = str;
        this.$body = str2;
        this.$positive = str3;
        this.$negative = str4;
        this.$viewModel = audioPathInteraction;
        this.$device = audioDevice;
        this.$checkStopBroadcasting$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$DeviceListItem$5(this.$snackbarHostState, this.$title, this.$body, this.$positive, this.$negative, this.$viewModel, this.$device, this.$checkStopBroadcasting$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$DeviceListItem$5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        AudioPathInteraction audioPathInteraction = this.$viewModel;
        AudioDevice audioDevice = this.$device;
        MutableState mutableState = this.$checkStopBroadcasting$delegate;
        if (((SnackbarResult) obj) == SnackbarResult.ActionPerformed) {
            audioPathInteraction.transfer(audioDevice);
        }
        mutableState.setValue(Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
