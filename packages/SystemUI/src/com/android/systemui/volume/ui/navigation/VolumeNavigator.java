package com.android.systemui.volume.ui.navigation;

import android.content.Intent;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.volume.VolumePanelDialog;
import com.android.systemui.volume.VolumePanelFactory;
import com.android.systemui.volume.domain.model.VolumePanelRoute;
import com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor;
import com.android.systemui.volume.panel.shared.model.VolumePanelGlobalState;
import com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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

/* loaded from: classes3.dex */
public final class VolumeNavigator {
    public final ActivityStarter activityStarter;
    public final SystemUIDialogFactory dialogFactory;
    public final CoroutineContext mainContext;
    public final UiEventLogger uiEventLogger;
    public final VolumePanelViewModel.Factory viewModelFactory;
    public final VolumePanelFactory volumePanelFactory;
    public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;

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

            /* renamed from: com.android.systemui.volume.ui.navigation.VolumeNavigator$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((VolumePanelGlobalState) obj).isVisible);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
                    this.this$0.volumePanelGlobalStateInteractor.setVisible(true);
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
            throw new NoWhenBranchMatchedException();
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
