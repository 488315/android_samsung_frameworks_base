package com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.Expandable;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputActionsInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel;
import com.android.systemui.volume.panel.shared.model.Result;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import com.android.systemui.volume.panel.shared.model.ResultKt$filterData$$inlined$map$1;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaOutputViewModel {
    public final MediaOutputActionsInteractor actionsInteractor;
    public final ReadonlyStateFlow connectedDeviceViewModel;
    public final Context context;
    public final ReadonlyStateFlow deviceIconViewModel;
    public final ReadonlyStateFlow enabled;
    public final MediaOutputComponentInteractor mediaOutputComponentInteractor;
    public final UiEventLogger uiEventLogger;

    public MediaOutputViewModel(Context context, CoroutineScope coroutineScope, MediaOutputActionsInteractor mediaOutputActionsInteractor, MediaOutputComponentInteractor mediaOutputComponentInteractor, UiEventLogger uiEventLogger) {
        this.context = context;
        this.actionsInteractor = mediaOutputActionsInteractor;
        this.mediaOutputComponentInteractor = mediaOutputComponentInteractor;
        this.uiEventLogger = uiEventLogger;
        final ResultKt$filterData$$inlined$map$1 filterData = ResultKt.filterData(mediaOutputComponentInteractor.mediaOutputModel);
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaOutputViewModel this$0;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaOutputViewModel mediaOutputViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaOutputViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto Ld2
                    L28:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L30:
                        kotlin.ResultKt.throwOnFailure(r10)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel r9 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel) r9
                        boolean r10 = r9 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.Idle
                        r2 = 2131954943(0x7f130cff, float:1.95464E38)
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel r4 = r8.this$0
                        if (r10 == 0) goto L45
                        android.content.Context r10 = r4.context
                        java.lang.String r10 = r10.getString(r2)
                        goto L76
                    L45:
                        boolean r10 = r9 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.MediaSession
                        if (r10 == 0) goto L69
                        r10 = r9
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel$MediaSession r10 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.MediaSession) r10
                        boolean r5 = r10.isPlaybackActive
                        if (r5 == 0) goto L62
                        android.content.Context r2 = r4.context
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r10 = r10.session
                        java.lang.CharSequence r10 = r10.appLabel
                        java.lang.Object[] r10 = new java.lang.Object[]{r10}
                        r5 = 2131954934(0x7f130cf6, float:1.9546381E38)
                        java.lang.String r10 = r2.getString(r5, r10)
                        goto L76
                    L62:
                        android.content.Context r10 = r4.context
                        java.lang.String r10 = r10.getString(r2)
                        goto L76
                    L69:
                        boolean r10 = r9 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.Calling
                        if (r10 == 0) goto Ld5
                        android.content.Context r10 = r4.context
                        r2 = 2131954942(0x7f130cfe, float:1.9546397E38)
                        java.lang.String r10 = r10.getString(r2)
                    L76:
                        r10.getClass()
                        com.android.systemui.common.shared.model.Color$Resource r2 = new com.android.systemui.common.shared.model.Color$Resource
                        r5 = 17171179(0x10602eb, float:2.4614007E-38)
                        r2.<init>(r5)
                        boolean r6 = r9.isInAudioSharing()
                        if (r6 == 0) goto L91
                        android.content.Context r4 = r4.context
                        r6 = 2131952120(0x7f1301f8, float:1.9540674E38)
                        java.lang.String r4 = r4.getString(r6)
                        goto Lae
                    L91:
                        com.android.systemui.volume.domain.model.AudioOutputDevice r6 = r9.getDevice()
                        boolean r7 = r6 instanceof com.android.systemui.volume.domain.model.AudioOutputDevice.Unknown
                        if (r7 != 0) goto L9a
                        goto L9b
                    L9a:
                        r6 = 0
                    L9b:
                        if (r6 == 0) goto La5
                        java.lang.String r6 = r6.getName()
                        if (r6 == 0) goto La5
                        r4 = r6
                        goto Lae
                    La5:
                        android.content.Context r4 = r4.context
                        r6 = 2131954975(0x7f130d1f, float:1.9546464E38)
                        java.lang.String r4 = r4.getString(r6)
                    Lae:
                        boolean r9 = r9.getCanOpenAudioSwitcher()
                        if (r9 == 0) goto Lbd
                        com.android.systemui.common.shared.model.Color$Resource r9 = new com.android.systemui.common.shared.model.Color$Resource
                        r5 = 17171178(0x10602ea, float:2.4614004E-38)
                        r9.<init>(r5)
                        goto Lc2
                    Lbd:
                        com.android.systemui.common.shared.model.Color$Resource r9 = new com.android.systemui.common.shared.model.Color$Resource
                        r9.<init>(r5)
                    Lc2:
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.ConnectedDeviceViewModel r5 = new com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.ConnectedDeviceViewModel
                        r5.<init>(r10, r2, r4, r9)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r5, r0)
                        if (r8 != r1) goto Ld2
                        return r1
                    Ld2:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    Ld5:
                        kotlin.NoWhenBranchMatchedException r8 = new kotlin.NoWhenBranchMatchedException
                        r8.<init>()
                        throw r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.connectedDeviceViewModel = FlowKt.stateIn(flow, coroutineScope, startedEagerly, null);
        ReadonlyStateFlow readonlyStateFlow = mediaOutputComponentInteractor.mediaOutputModel;
        final ResultKt$filterData$$inlined$map$1 filterData2 = ResultKt.filterData(readonlyStateFlow);
        this.deviceIconViewModel = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        Method dump skipped, instructions count: 210
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, null);
        final ResultKt$filterData$$inlined$map$1 filterData3 = ResultKt.filterData(readonlyStateFlow);
        this.enabled = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel r5 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel) r5
                        boolean r5 = r5.getCanOpenAudioSwitcher()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, Boolean.TRUE);
    }

    public final void onBarClick(Expandable expandable) {
        this.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_MEDIA_OUTPUT_CLICKED);
        Result result = (Result) this.mediaOutputComponentInteractor.mediaOutputModel.$$delegate_0.getValue();
        Result.Data data = result instanceof Result.Data ? (Result.Data) result : null;
        MediaOutputComponentModel mediaOutputComponentModel = data != null ? (MediaOutputComponentModel) data.data : null;
        MediaOutputActionsInteractor mediaOutputActionsInteractor = this.actionsInteractor;
        mediaOutputActionsInteractor.getClass();
        if (mediaOutputComponentModel instanceof MediaOutputComponentModel.MediaSession) {
            MediaOutputDialogManager.createAndShowWithController$default(mediaOutputActionsInteractor.mediaOutputDialogManager, ((MediaOutputComponentModel.MediaSession) mediaOutputComponentModel).session.packageName, false, expandable != null ? expandable.dialogTransitionController(new DialogCuj(58, "media_output")) : null, null, 24);
        } else {
            mediaOutputActionsInteractor.mediaOutputDialogManager.createAndShow(null, false, expandable != null ? expandable.dialogTransitionController(new DialogCuj(58, "media_output")) : null, false, null, null);
        }
    }
}
