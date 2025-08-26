package com.android.systemui.volume.ui.navigation;

import android.content.DialogInterface;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class VolumeNavigator$createNewVolumePanelDialog$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SystemUIDialog $dialog;
    int label;
    final /* synthetic */ VolumeNavigator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeNavigator$createNewVolumePanelDialog$1$1$1(SystemUIDialog systemUIDialog, VolumeNavigator volumeNavigator, Continuation continuation) {
        super(2, continuation);
        this.$dialog = systemUIDialog;
        this.this$0 = volumeNavigator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumeNavigator$createNewVolumePanelDialog$1$1$1(this.$dialog, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeNavigator$createNewVolumePanelDialog$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SystemUIDialog systemUIDialog = this.$dialog;
        final VolumeNavigator volumeNavigator = this.this$0;
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$createNewVolumePanelDialog$1$1$1.1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                volumeNavigator.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_GONE);
                volumeNavigator.volumePanelGlobalStateInteractor.setVisible(false);
            }
        });
        return Unit.INSTANCE;
    }
}
