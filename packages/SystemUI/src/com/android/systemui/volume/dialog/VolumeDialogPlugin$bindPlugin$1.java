package com.android.systemui.volume.dialog;

import android.content.Context;
import android.media.AudioManager;
import com.android.systemui.volume.SafetyWarningDialog;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogPluginViewModel;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class VolumeDialogPlugin$bindPlugin$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ VolumeDialogPluginViewModel $viewModel;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ VolumeDialogPlugin this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogPlugin$bindPlugin$1(VolumeDialogPluginViewModel volumeDialogPluginViewModel, VolumeDialogPlugin volumeDialogPlugin, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = volumeDialogPluginViewModel;
        this.this$0 = volumeDialogPlugin;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogPlugin$bindPlugin$1 volumeDialogPlugin$bindPlugin$1 = new VolumeDialogPlugin$bindPlugin$1(this.$viewModel, this.this$0, continuation);
        volumeDialogPlugin$bindPlugin$1.Z$0 = ((Boolean) obj).booleanValue();
        return volumeDialogPlugin$bindPlugin$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((VolumeDialogPlugin$bindPlugin$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [android.app.AlertDialog, com.android.systemui.volume.dialog.VolumeDialogPlugin$showSafetyWarningVisibility$2$dialog$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.Z$0) {
                this.$viewModel.dialogVisibilityInteractor.resetDismissTimeout();
                VolumeDialogPlugin volumeDialogPlugin = this.this$0;
                final VolumeDialogPlugin$bindPlugin$1$$ExternalSyntheticLambda0 volumeDialogPlugin$bindPlugin$1$$ExternalSyntheticLambda0 = new VolumeDialogPlugin$bindPlugin$1$$ExternalSyntheticLambda0(this.$viewModel, 0);
                this.label = 1;
                volumeDialogPlugin.getClass();
                final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                final Context context = volumeDialogPlugin.context;
                final AudioManager audioManager = volumeDialogPlugin.audioManager;
                final ?? r2 = new SafetyWarningDialog(context, audioManager) { // from class: com.android.systemui.volume.dialog.VolumeDialogPlugin$showSafetyWarningVisibility$2$dialog$1
                    @Override // com.android.systemui.volume.SafetyWarningDialog
                    public final void cleanUp$1() {
                        Function0.this.invoke();
                        CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                        int i2 = Result.$r8$clinit;
                        cancellableContinuation.resumeWith(Unit.INSTANCE);
                    }
                };
                r2.show();
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.volume.dialog.VolumeDialogPlugin$showSafetyWarningVisibility$2$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        dismiss();
                        return Unit.INSTANCE;
                    }
                });
                Object result = cancellableContinuationImpl.getResult();
                if (result != coroutineSingletons) {
                    result = Unit.INSTANCE;
                }
                if (result == coroutineSingletons) {
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
