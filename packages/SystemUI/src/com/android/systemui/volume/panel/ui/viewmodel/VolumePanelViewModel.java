package com.android.systemui.volume.panel.ui.viewmodel;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.volume.domain.startable.AudioModeLoggerStartable;
import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.dagger.factory.VolumePanelComponentFactory;
import com.android.systemui.volume.panel.domain.interactor.ComponentsInteractorImpl;
import com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayout;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentState;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentStateKt;
import java.io.PrintWriter;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumePanelViewModel implements Dumpable {
    public final ReadonlyStateFlow componentsLayout;
    public final DumpManager dumpManager;
    public final VolumePanelLogger logger;
    public final VolumePanelComponent volumePanelComponent;
    public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;
    public final ReadonlyStateFlow volumePanelState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumePanelViewModel.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
        public final BroadcastDispatcher broadcastDispatcher;
        public final ConfigurationController configurationController;
        public final Context context;
        public final VolumePanelComponentFactory daggerComponentFactory;
        public final DumpManager dumpManager;
        public final VolumePanelLogger logger;
        public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;

        public Factory(Context context, VolumePanelComponentFactory volumePanelComponentFactory, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, VolumePanelLogger volumePanelLogger, VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor) {
            this.context = context;
            this.daggerComponentFactory = volumePanelComponentFactory;
            this.configurationController = configurationController;
            this.broadcastDispatcher = broadcastDispatcher;
            this.dumpManager = dumpManager;
            this.logger = volumePanelLogger;
            this.volumePanelGlobalStateInteractor = volumePanelGlobalStateInteractor;
        }
    }

    public VolumePanelViewModel(final Resources resources, CoroutineScope coroutineScope, VolumePanelComponentFactory volumePanelComponentFactory, ConfigurationController configurationController, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, VolumePanelLogger volumePanelLogger, VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor) {
        this.dumpManager = dumpManager;
        this.logger = volumePanelLogger;
        this.volumePanelGlobalStateInteractor = volumePanelGlobalStateInteractor;
        VolumePanelComponent create = volumePanelComponentFactory.create(this, coroutineScope);
        this.volumePanelComponent = create;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new VolumePanelViewModel$volumePanelState$1(resources, null), ConfigurationControllerExtKt.getOnConfigChanged(configurationController));
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Resources $resources$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumePanelViewModel this$0;

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

                public AnonymousClass2(FlowCollector flowCollector, Resources resources, VolumePanelViewModel volumePanelViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$resources$inlined = resources;
                    this.this$0 = volumePanelViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
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
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L73
                    L27:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r9)
                        android.content.res.Configuration r8 = (android.content.res.Configuration) r8
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState r9 = new com.android.systemui.volume.panel.ui.viewmodel.VolumePanelState
                        int r8 = r8.orientation
                        android.content.res.Resources r2 = r7.$resources$inlined
                        r4 = 2131034269(0x7f05009d, float:1.767905E38)
                        boolean r2 = r2.getBoolean(r4)
                        r9.<init>(r8, r2)
                        com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel r8 = r7.this$0
                        com.android.systemui.volume.panel.shared.VolumePanelLogger r8 = r8.logger
                        r8.getClass()
                        com.android.systemui.log.core.LogLevel r2 = com.android.systemui.log.core.LogLevel.DEBUG
                        com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0 r4 = new com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0
                        r5 = 4
                        r4.<init>(r5)
                        java.lang.String r5 = "SysUI_VolumePanel"
                        com.android.systemui.log.LogBuffer r8 = r8.logBuffer
                        r6 = 0
                        com.android.systemui.log.core.LogMessage r2 = r8.obtain(r5, r2, r4, r6)
                        java.lang.String r4 = r9.toString()
                        r5 = r2
                        com.android.systemui.log.LogMessageImpl r5 = (com.android.systemui.log.LogMessageImpl) r5
                        r5.str1 = r4
                        r8.commit(r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto L73
                        return r1
                    L73:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, resources, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        DaggerReferenceGlobalRootComponent.VolumePanelComponentImpl volumePanelComponentImpl = (DaggerReferenceGlobalRootComponent.VolumePanelComponentImpl) create;
        CoroutineScope coroutineScope2 = volumePanelComponentImpl.scope;
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope2, startedEagerly, new VolumePanelState(resources.getConfiguration().orientation, resources.getBoolean(R.bool.volume_panel_is_large_screen)));
        this.volumePanelState = stateIn;
        this.componentsLayout = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ComponentsInteractorImpl) volumePanelComponentImpl.componentsInteractor()).components, stateIn, new VolumePanelViewModel$componentsLayout$1(this, null)), volumePanelComponentImpl.scope, startedEagerly, null);
        BuildersKt.launch(volumePanelComponentImpl.scope, EmptyCoroutineContext.INSTANCE, CoroutineStart.DEFAULT, new VolumePanelViewModel$special$$inlined$launchAndDispose$default$1(null, this));
        Iterator<T> it = volumePanelComponentImpl.volumePanelStartables().iterator();
        while (it.hasNext()) {
            ((AudioModeLoggerStartable) it.next()).start();
        }
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.action.DISMISS_VOLUME_PANEL_DIALOG"), null, 14), new AnonymousClass3(null)), this.volumePanelComponent.coroutineScope());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("volumePanelState=", this.volumePanelState.$$delegate_0.getValue(), printWriter);
        ComponentsLayout componentsLayout = (ComponentsLayout) this.componentsLayout.$$delegate_0.getValue();
        if (componentsLayout != null) {
            final int i = 0;
            String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default(componentsLayout.headerComponents, null, null, null, new Function1() { // from class: com.android.systemui.volume.panel.ui.layout.ComponentsLayoutKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ComponentState componentState = (ComponentState) obj;
                    switch (i) {
                    }
                    return ComponentStateKt.toLogString(componentState);
                }
            }, 31);
            final int i2 = 1;
            String joinToString$default2 = CollectionsKt___CollectionsKt.joinToString$default(componentsLayout.contentComponents, null, null, null, new Function1() { // from class: com.android.systemui.volume.panel.ui.layout.ComponentsLayoutKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ComponentState componentState = (ComponentState) obj;
                    switch (i2) {
                    }
                    return ComponentStateKt.toLogString(componentState);
                }
            }, 31);
            final int i3 = 2;
            str = NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("( headerComponents=", joinToString$default, " contentComponents=", joinToString$default2, " footerComponents="), CollectionsKt___CollectionsKt.joinToString$default(componentsLayout.footerComponents, null, null, null, new Function1() { // from class: com.android.systemui.volume.panel.ui.layout.ComponentsLayoutKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ComponentState componentState = (ComponentState) obj;
                    switch (i3) {
                    }
                    return ComponentStateKt.toLogString(componentState);
                }
            }, 31), " bottomBarComponent=", ComponentStateKt.toLogString(componentsLayout.bottomBarComponent), " )");
        } else {
            str = null;
        }
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "componentsLayout=", str);
    }
}
