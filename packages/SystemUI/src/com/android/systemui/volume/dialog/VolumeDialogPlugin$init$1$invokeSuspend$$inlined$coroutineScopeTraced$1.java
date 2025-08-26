package com.android.systemui.volume.dialog;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.volume.dialog.dagger.VolumeDialogPluginComponent;
import com.android.systemui.volume.dialog.ui.viewmodel.VolumeDialogPluginViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $traceName$inlined;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialogPlugin this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1(Continuation continuation, String str, VolumeDialogPlugin volumeDialogPlugin) {
        super(2, continuation);
        this.$traceName$inlined = str;
        this.this$0 = volumeDialogPlugin;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1 volumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1 = new VolumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1(continuation, this.$traceName$inlined, this.this$0);
        volumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1.L$0 = obj;
        return volumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialogPlugin$init$1$invokeSuspend$$inlined$coroutineScopeTraced$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        VolumeDialogPluginComponent volumeDialogPluginComponentCreate = this.this$0.volumeDialogPluginComponentFactory.create(coroutineScope);
        VolumeDialogPlugin volumeDialogPlugin = this.this$0;
        VolumeDialogPluginViewModel volumeDialogPluginViewModelViewModel = ((DaggerReferenceGlobalRootComponent.VolumeDialogPluginComponentImpl) volumeDialogPluginComponentCreate).viewModel();
        volumeDialogPlugin.getClass();
        volumeDialogPluginViewModelViewModel.launchVolumeDialog();
        FlowKt.launchIn(FlowKt.mapLatest(volumeDialogPluginViewModelViewModel.isShowingSafetyWarning, new VolumeDialogPlugin$bindPlugin$1(volumeDialogPluginViewModelViewModel, volumeDialogPlugin, null)), coroutineScope);
        FlowKt.launchIn(FlowKt.mapLatest(volumeDialogPluginViewModelViewModel.csdWarning, new VolumeDialogPlugin$bindPlugin$2(volumeDialogPluginViewModelViewModel, volumeDialogPlugin, null)), coroutineScope);
        return Unit.INSTANCE;
    }
}
