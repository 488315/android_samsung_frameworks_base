package com.android.systemui.volume.panel.ui.viewmodel;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
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
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.volume.domain.startable.AudioModeLoggerStartable;
import com.android.systemui.volume.panel.dagger.VolumePanelComponent;
import com.android.systemui.volume.panel.dagger.factory.VolumePanelComponentFactory;
import com.android.systemui.volume.panel.domain.interactor.ComponentsInteractorImpl;
import com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.panel.ui.layout.ComponentsLayout;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentState;
import com.android.systemui.volume.panel.ui.viewmodel.ComponentStateKt;
import java.io.IOException;
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

/* loaded from: classes3.dex */
public final class VolumePanelViewModel implements Dumpable {
    public final ReadonlyStateFlow componentsLayout;
    public final DumpManager dumpManager;
    public final VolumePanelLogger logger;
    public final VolumePanelComponent volumePanelComponent;
    public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;
    public final ReadonlyStateFlow volumePanelState;

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
        VolumePanelComponent volumePanelComponentCreate = volumePanelComponentFactory.create(this, coroutineScope);
        this.volumePanelComponent = volumePanelComponentCreate;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new VolumePanelViewModel$volumePanelState$1(resources, null), ConfigurationControllerExtKt.getOnConfigChanged(configurationController));
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.ui.viewmodel.VolumePanelViewModel$special$$inlined$map$1

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
                        VolumePanelState volumePanelState = new VolumePanelState(((Configuration) obj).orientation, this.$resources$inlined.getBoolean(R.bool.volume_panel_is_large_screen));
                        VolumePanelLogger volumePanelLogger = this.this$0.logger;
                        volumePanelLogger.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(4);
                        LogBuffer logBuffer = volumePanelLogger.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) logMessageObtain).str1 = volumePanelState.toString();
                        logBuffer.commit(logMessageObtain);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(volumePanelState, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, resources, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        DaggerReferenceGlobalRootComponent.VolumePanelComponentImpl volumePanelComponentImpl = (DaggerReferenceGlobalRootComponent.VolumePanelComponentImpl) volumePanelComponentCreate;
        CoroutineScope coroutineScope2 = volumePanelComponentImpl.scope;
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope2, startedEagerly, new VolumePanelState(resources.getConfiguration().orientation, resources.getBoolean(R.bool.volume_panel_is_large_screen)));
        this.volumePanelState = readonlyStateFlowStateIn;
        this.componentsLayout = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((ComponentsInteractorImpl) volumePanelComponentImpl.componentsInteractor()).components, readonlyStateFlowStateIn, new VolumePanelViewModel$componentsLayout$1(this, null)), volumePanelComponentImpl.scope, startedEagerly, null);
        BuildersKt.launch(volumePanelComponentImpl.scope, EmptyCoroutineContext.INSTANCE, CoroutineStart.DEFAULT, new VolumePanelViewModel$special$$inlined$launchAndDispose$default$1(null, this));
        Iterator<T> it = volumePanelComponentImpl.volumePanelStartables().iterator();
        while (it.hasNext()) {
            ((AudioModeLoggerStartable) it.next()).start();
        }
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.action.DISMISS_VOLUME_PANEL_DIALOG"), null, 14), new AnonymousClass3(null)), this.volumePanelComponent.coroutineScope());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) throws IOException {
        String strM;
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("volumePanelState=", this.volumePanelState.$$delegate_0.getValue(), printWriter);
        ComponentsLayout componentsLayout = (ComponentsLayout) this.componentsLayout.$$delegate_0.getValue();
        if (componentsLayout != null) {
            final int i = 0;
            String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(componentsLayout.headerComponents, null, null, null, new Function1() { // from class: com.android.systemui.volume.panel.ui.layout.ComponentsLayoutKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ComponentState componentState = (ComponentState) obj;
                    switch (i) {
                    }
                    return ComponentStateKt.toLogString(componentState);
                }
            }, 31);
            final int i2 = 1;
            String strJoinToString$default2 = CollectionsKt___CollectionsKt.joinToString$default(componentsLayout.contentComponents, null, null, null, new Function1() { // from class: com.android.systemui.volume.panel.ui.layout.ComponentsLayoutKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ComponentState componentState = (ComponentState) obj;
                    switch (i2) {
                    }
                    return ComponentStateKt.toLogString(componentState);
                }
            }, 31);
            final int i3 = 2;
            strM = NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("( headerComponents=", strJoinToString$default, " contentComponents=", strJoinToString$default2, " footerComponents="), CollectionsKt___CollectionsKt.joinToString$default(componentsLayout.footerComponents, null, null, null, new Function1() { // from class: com.android.systemui.volume.panel.ui.layout.ComponentsLayoutKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ComponentState componentState = (ComponentState) obj;
                    switch (i3) {
                    }
                    return ComponentStateKt.toLogString(componentState);
                }
            }, 31), " bottomBarComponent=", ComponentStateKt.toLogString(componentsLayout.bottomBarComponent), " )");
        } else {
            strM = null;
        }
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "componentsLayout=", strM);
    }
}
