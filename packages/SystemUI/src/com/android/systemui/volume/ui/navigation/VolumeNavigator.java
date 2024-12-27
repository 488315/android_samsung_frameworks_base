package com.android.systemui.volume.ui.navigation;

import android.content.Intent;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.volume.VolumePanelDialog;
import com.android.systemui.volume.VolumePanelFactory;
import com.android.systemui.volume.domain.model.VolumePanelRoute;
import com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumeNavigator {
    public final ActivityStarter activityStarter;
    public final SystemUIDialogFactory dialogFactory;
    public final CoroutineContext mainContext;
    public final UiEventLogger uiEventLogger;
    public final VolumePanelViewModel.Factory viewModelFactory;
    public final VolumePanelFactory volumePanelFactory;
    public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelRoute.values().length];
            try {
                iArr[VolumePanelRoute.COMPOSE_VOLUME_PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelRoute.SETTINGS_VOLUME_PANEL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelRoute.SYSTEM_UI_VOLUME_PANEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public VolumeNavigator(CoroutineScope coroutineScope, CoroutineContext coroutineContext, VolumePanelFactory volumePanelFactory, ActivityStarter activityStarter, VolumePanelViewModel.Factory factory, SystemUIDialogFactory systemUIDialogFactory, UiEventLogger uiEventLogger, VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor) {
        this.mainContext = coroutineContext;
        this.volumePanelFactory = volumePanelFactory;
        this.activityStarter = activityStarter;
        this.viewModelFactory = factory;
        this.dialogFactory = systemUIDialogFactory;
        this.uiEventLogger = uiEventLogger;
        this.volumePanelGlobalStateInteractor = volumePanelGlobalStateInteractor;
        final ReadonlyStateFlow readonlyStateFlow = volumePanelGlobalStateInteractor.repository.globalState;
        FlowKt.launchIn(FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.panel.shared.model.VolumePanelGlobalState r5 = (com.android.systemui.volume.panel.shared.model.VolumePanelGlobalState) r5
                        boolean r5 = r5.isVisible
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new VolumeNavigator$special$$inlined$flatMapLatest$1(null, this)), coroutineScope);
    }

    public final void openVolumePanel(VolumePanelRoute volumePanelRoute) {
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelRoute.ordinal()];
        ActivityStarter activityStarter = this.activityStarter;
        if (i == 1) {
            activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$showNewVolumePanel$1
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    VolumeNavigator.this.volumePanelGlobalStateInteractor.setVisible(true);
                    return false;
                }
            }, new Runnable() { // from class: com.android.systemui.volume.ui.navigation.VolumeNavigator$showNewVolumePanel$2
                @Override // java.lang.Runnable
                public final void run() {
                }
            }, true);
            return;
        }
        if (i == 2) {
            activityStarter.startActivity(new Intent("android.settings.panel.action.VOLUME"), true);
            return;
        }
        if (i != 3) {
            return;
        }
        VolumePanelFactory volumePanelFactory = this.volumePanelFactory;
        volumePanelFactory.getClass();
        VolumePanelDialog volumePanelDialog = VolumePanelFactory.volumePanelDialog;
        if (volumePanelDialog == null || !volumePanelDialog.isShowing()) {
            VolumePanelDialog volumePanelDialog2 = new VolumePanelDialog(volumePanelFactory.context, volumePanelFactory.activityStarter, true);
            VolumePanelFactory.volumePanelDialog = volumePanelDialog2;
            volumePanelDialog2.show();
        }
    }
}
