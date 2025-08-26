package com.android.systemui.volume.ui.navigation;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class VolumeNavigator$2$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeNavigator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeNavigator$2$1(VolumeNavigator volumeNavigator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = volumeNavigator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeNavigator$2$1 volumeNavigator$2$1 = new VolumeNavigator$2$1(this.this$0, continuation);
        volumeNavigator$2$1.L$0 = obj;
        return volumeNavigator$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeNavigator$2$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final VolumeNavigator volumeNavigator = this.this$0;
            volumeNavigator.getClass();
            Dp.Companion companion = Dp.Companion;
            final ComponentSystemUIDialog componentSystemUIDialogM3095createBottomSheet6ZxE2Lo$default = SystemUIDialogFactoryExtKt.m3095createBottomSheet6ZxE2Lo$default(volumeNavigator.dialogFactory, new ComposableLambdaImpl(746853774, true, new Function3() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$createNewVolumePanelDialog$1
                /* JADX WARN: Removed duplicated region for block: B:9:0x0037  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                    SystemUIDialog systemUIDialog = (SystemUIDialog) obj2;
                    Composer composer = (Composer) obj3;
                    ((Number) obj4).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.ui.navigation.VolumeNavigator.createNewVolumePanelDialog.<anonymous> (VolumeNavigator.kt:111)");
                    }
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    composerImpl.startReplaceGroup(-1150216311);
                    boolean zChangedInstance = composerImpl.changedInstance(systemUIDialog);
                    VolumeNavigator volumeNavigator2 = volumeNavigator;
                    boolean zChangedInstance2 = zChangedInstance | composerImpl.changedInstance(volumeNavigator2);
                    Object objRememberedValue = composerImpl.rememberedValue();
                    Composer.Companion companion2 = Composer.Companion;
                    if (!zChangedInstance2) {
                        companion2.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new VolumeNavigator$createNewVolumePanelDialog$1$1$1(systemUIDialog, volumeNavigator2, null);
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(composerImpl, systemUIDialog, (Function2) objRememberedValue);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    companion2.getClass();
                    Object obj5 = Composer.Companion.Empty;
                    if (objRememberedValue2 == obj5) {
                        objRememberedValue2 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue2;
                    composerImpl.startReplaceGroup(-1150204873);
                    boolean zChanged = composerImpl.changed(coroutineScope);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (zChanged || objRememberedValue3 == obj5) {
                        VolumePanelViewModel.Factory factory = volumeNavigator2.viewModelFactory;
                        objRememberedValue3 = new VolumePanelViewModel(factory.context.getResources(), coroutineScope, factory.daggerComponentFactory, factory.configurationController, factory.broadcastDispatcher, factory.dumpManager, factory.logger, factory.volumePanelGlobalStateInteractor);
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(false);
                    VolumePanelRootKt.VolumePanelRoot((VolumePanelViewModel) objRememberedValue3, null, composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }), false, 800, 7);
            this.this$0.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_SHOWN);
            componentSystemUIDialogM3095createBottomSheet6ZxE2Lo$default.show();
            Function0 function0 = new Function0() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$2$1.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    componentSystemUIDialogM3095createBottomSheet6ZxE2Lo$default.dismiss();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
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
