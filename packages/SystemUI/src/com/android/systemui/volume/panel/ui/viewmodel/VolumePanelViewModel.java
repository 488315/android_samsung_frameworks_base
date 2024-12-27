package com.android.systemui.volume.panel.ui.viewmodel;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.volume.domain.startable.AudioModeLoggerStartable;
import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.dagger.factory.VolumePanelComponentFactory;
import com.android.systemui.volume.panel.domain.interactor.ComponentsInteractorImpl;
import com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumePanelViewModel {
    public final ReadonlySharedFlow componentsLayout;
    public final VolumePanelComponent volumePanelComponent;
    public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;
    public final ReadonlyStateFlow volumePanelState;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumePanelViewModel.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            VolumePanelViewModel.this.volumePanelGlobalStateInteractor.setVisible(false);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final ConfigurationController configurationController;
        public final Context context;
        public final VolumePanelComponentFactory daggerComponentFactory;
        public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;

        public Factory(Context context, VolumePanelComponentFactory volumePanelComponentFactory, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor) {
            this.context = context;
            this.daggerComponentFactory = volumePanelComponentFactory;
            this.configurationController = configurationController;
            this.broadcastDispatcher = broadcastDispatcher;
            this.volumePanelGlobalStateInteractor = volumePanelGlobalStateInteractor;
        }
    }

    public VolumePanelViewModel(final Resources resources, CoroutineScope coroutineScope, VolumePanelComponentFactory volumePanelComponentFactory, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor) {
        this.volumePanelGlobalStateInteractor = volumePanelGlobalStateInteractor;
        VolumePanelComponent create = volumePanelComponentFactory.create(this, coroutineScope);
        this.volumePanelComponent = create;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new VolumePanelViewModel$volumePanelState$1(resources, null), ConfigurationControllerExtKt.getOnConfigChanged(configurationController));
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Resources $resources$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Resources resources) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$resources$inlined = resources;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        android.content.res.Configuration r6 = (android.content.res.Configuration) r6
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState r7 = new com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState
                        int r6 = r6.orientation
                        android.content.res.Resources r2 = r5.$resources$inlined
                        r4 = 2131034261(0x7f050095, float:1.7679035E38)
                        boolean r2 = r2.getBoolean(r4)
                        r7.<init>(r6, r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, resources), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        CoroutineScope coroutineScope2 = create.coroutineScope();
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope2, startedEagerly, new VolumePanelState(resources.getConfiguration().orientation, resources.getBoolean(R.bool.volume_panel_is_large_screen)));
        this.volumePanelState = stateIn;
        this.componentsLayout = FlowKt.shareIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ComponentsInteractorImpl) create.componentsInteractor()).components, stateIn, new VolumePanelViewModel$componentsLayout$1(this, null)), create.coroutineScope(), startedEagerly, 1);
        Iterator it = create.volumePanelStartables().iterator();
        while (it.hasNext()) {
            ((AudioModeLoggerStartable) it.next()).start();
        }
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.action.DISMISS_VOLUME_PANEL_DIALOG"), null, 0, null, 14), new AnonymousClass2(null)), this.volumePanelComponent.coroutineScope());
    }
}
