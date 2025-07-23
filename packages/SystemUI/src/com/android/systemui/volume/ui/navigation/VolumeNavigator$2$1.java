package com.android.systemui.volume.ui.navigation;

import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            final ComponentSystemUIDialog m3078createBottomSheet6ZxE2Lo$default = SystemUIDialogFactoryExtKt.m3078createBottomSheet6ZxE2Lo$default(volumeNavigator.dialogFactory, new ComposableLambdaImpl(746853774, true, new Function3() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$createNewVolumePanelDialog$1
                /* JADX WARN: Code restructure failed: missing block: B:7:0x0035, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
                    /*
                        r11 = this;
                        com.android.systemui.statusbar.phone.SystemUIDialog r12 = (com.android.systemui.statusbar.phone.SystemUIDialog) r12
                        androidx.compose.runtime.Composer r13 = (androidx.compose.runtime.Composer) r13
                        java.lang.Number r14 = (java.lang.Number) r14
                        r14.intValue()
                        boolean r14 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r14 == 0) goto L14
                        java.lang.String r14 = "com.android.systemui.volume.ui.navigation.VolumeNavigator.createNewVolumePanelDialog.<anonymous> (VolumeNavigator.kt:111)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r14)
                    L14:
                        androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
                        r14 = -1150216311(0xffffffffbb711789, float:-0.003678771)
                        r13.startReplaceGroup(r14)
                        boolean r14 = r13.changedInstance(r12)
                        com.android.systemui.volume.ui.navigation.VolumeNavigator r11 = com.android.systemui.volume.ui.navigation.VolumeNavigator.this
                        boolean r0 = r13.changedInstance(r11)
                        r14 = r14 | r0
                        java.lang.Object r0 = r13.rememberedValue()
                        r1 = 0
                        androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
                        if (r14 != 0) goto L37
                        r2.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r14 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r0 != r14) goto L3f
                    L37:
                        com.android.systemui.volume.ui.navigation.VolumeNavigator$createNewVolumePanelDialog$1$1$1 r0 = new com.android.systemui.volume.ui.navigation.VolumeNavigator$createNewVolumePanelDialog$1$1$1
                        r0.<init>(r12, r11, r1)
                        r13.updateRememberedValue(r0)
                    L3f:
                        kotlin.jvm.functions.Function2 r0 = (kotlin.jvm.functions.Function2) r0
                        r14 = 0
                        r13.end(r14)
                        androidx.compose.runtime.EffectsKt.LaunchedEffect(r13, r12, r0)
                        java.lang.Object r12 = r13.rememberedValue()
                        r2.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r12 != r0) goto L5c
                        kotlin.coroutines.EmptyCoroutineContext r12 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
                        kotlinx.coroutines.CoroutineScope r12 = androidx.compose.runtime.EffectsKt.createCompositionCoroutineScope(r12, r13)
                        r13.updateRememberedValue(r12)
                    L5c:
                        r4 = r12
                        kotlinx.coroutines.CoroutineScope r4 = (kotlinx.coroutines.CoroutineScope) r4
                        r12 = -1150204873(0xffffffffbb714437, float:-0.003681434)
                        r13.startReplaceGroup(r12)
                        boolean r12 = r13.changed(r4)
                        java.lang.Object r2 = r13.rememberedValue()
                        if (r12 != 0) goto L71
                        if (r2 != r0) goto L8d
                    L71:
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$Factory r11 = r11.viewModelFactory
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel r2 = new com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel
                        android.content.Context r12 = r11.context
                        android.content.res.Resources r3 = r12.getResources()
                        com.android.systemui.broadcast.BroadcastDispatcher r7 = r11.broadcastDispatcher
                        com.android.systemui.dump.DumpManager r8 = r11.dumpManager
                        com.android.systemui.volume.panel.dagger.factory.VolumePanelComponentFactory r5 = r11.daggerComponentFactory
                        com.android.systemui.statusbar.policy.ConfigurationController r6 = r11.configurationController
                        com.android.systemui.volume.panel.shared.VolumePanelLogger r9 = r11.logger
                        com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor r10 = r11.volumePanelGlobalStateInteractor
                        r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
                        r13.updateRememberedValue(r2)
                    L8d:
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel r2 = (com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel) r2
                        r13.end(r14)
                        com.android.systemui.volume.panel.ui.composable.VolumePanelRootKt.VolumePanelRoot(r2, r1, r13, r14)
                        boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r11 == 0) goto L9e
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L9e:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.ui.navigation.VolumeNavigator$createNewVolumePanelDialog$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }), false, 800, 7);
            this.this$0.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_SHOWN);
            m3078createBottomSheet6ZxE2Lo$default.show();
            Function0 function0 = new Function0() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$2$1.1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    m3078createBottomSheet6ZxE2Lo$default.dismiss();
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
