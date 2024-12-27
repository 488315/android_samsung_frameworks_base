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
        ReadonlyStateFlow readonlyStateFlow = mediaOutputComponentInteractor.mediaOutputModel;
        final ResultKt$filterData$$inlined$map$1 filterData = ResultKt.filterData(readonlyStateFlow);
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1

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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
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
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto La1
                    L28:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L30:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel r7 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel) r7
                        boolean r8 = r7 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.Idle
                        r2 = 2131954644(0x7f130bd4, float:1.9545793E38)
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel r4 = r6.this$0
                        if (r8 == 0) goto L45
                        android.content.Context r8 = r4.context
                        java.lang.String r8 = r8.getString(r2)
                        goto L76
                    L45:
                        boolean r8 = r7 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.MediaSession
                        if (r8 == 0) goto L69
                        r8 = r7
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel$MediaSession r8 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.MediaSession) r8
                        boolean r5 = r8.isPlaybackActive
                        if (r5 == 0) goto L62
                        android.content.Context r2 = r4.context
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r8 = r8.session
                        java.lang.CharSequence r8 = r8.appLabel
                        java.lang.Object[] r8 = new java.lang.Object[]{r8}
                        r5 = 2131954635(0x7f130bcb, float:1.9545775E38)
                        java.lang.String r8 = r2.getString(r5, r8)
                        goto L76
                    L62:
                        android.content.Context r8 = r4.context
                        java.lang.String r8 = r8.getString(r2)
                        goto L76
                    L69:
                        boolean r8 = r7 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.Calling
                        if (r8 == 0) goto La4
                        android.content.Context r8 = r4.context
                        r2 = 2131954643(0x7f130bd3, float:1.9545791E38)
                        java.lang.String r8 = r8.getString(r2)
                    L76:
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r8)
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.ConnectedDeviceViewModel r2 = new com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.ConnectedDeviceViewModel
                        boolean r5 = r7.isInAudioSharing()
                        if (r5 == 0) goto L8b
                        android.content.Context r7 = r4.context
                        r4 = 2131952074(0x7f1301ca, float:1.954058E38)
                        java.lang.String r7 = r7.getString(r4)
                        goto L93
                    L8b:
                        com.android.systemui.volume.domain.model.AudioOutputDevice r7 = r7.getDevice()
                        java.lang.String r7 = r7.getName()
                    L93:
                        r2.<init>(r8, r7)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r2, r0)
                        if (r6 != r1) goto La1
                        return r1
                    La1:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    La4:
                        kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
                        r6.<init>()
                        throw r6
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
        final ResultKt$filterData$$inlined$map$1 filterData2 = ResultKt.filterData(readonlyStateFlow);
        this.deviceIconViewModel = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2

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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto La0
                    L28:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L30:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel r7 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel) r7
                        com.android.systemui.volume.domain.model.AudioOutputDevice r8 = r7.getDevice()
                        boolean r2 = r8 instanceof com.android.systemui.volume.domain.model.AudioOutputDevice.Unknown
                        r2 = r2 ^ r3
                        r4 = 0
                        if (r2 == 0) goto L40
                        goto L41
                    L40:
                        r8 = r4
                    L41:
                        if (r8 == 0) goto L4f
                        android.graphics.drawable.Drawable r8 = r8.getIcon()
                        if (r8 == 0) goto L4f
                        com.android.systemui.common.shared.model.Icon$Loaded r2 = new com.android.systemui.common.shared.model.Icon$Loaded
                        r2.<init>(r8, r4)
                        goto L57
                    L4f:
                        com.android.systemui.common.shared.model.Icon$Resource r2 = new com.android.systemui.common.shared.model.Icon$Resource
                        r8 = 2131233173(0x7f080995, float:1.8082476E38)
                        r2.<init>(r8, r4)
                    L57:
                        boolean r8 = r7 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.MediaSession
                        if (r8 == 0) goto L5e
                        r4 = r7
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel$MediaSession r4 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.MediaSession) r4
                    L5e:
                        r8 = 0
                        if (r4 == 0) goto L66
                        boolean r4 = r4.isPlaybackActive
                        if (r4 != r3) goto L66
                        r8 = r3
                    L66:
                        boolean r7 = r7 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel.Calling
                        r4 = 17957064(0x11200c8, float:2.6816525E-38)
                        if (r8 != 0) goto L83
                        if (r7 == 0) goto L70
                        goto L83
                    L70:
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel$IsNotPlaying r7 = new com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel$IsNotPlaying
                        com.android.systemui.common.shared.model.Color$Attribute r8 = new com.android.systemui.common.shared.model.Color$Attribute
                        r5 = 17957048(0x11200b8, float:2.681648E-38)
                        r8.<init>(r5)
                        com.android.systemui.common.shared.model.Color$Attribute r5 = new com.android.systemui.common.shared.model.Color$Attribute
                        r5.<init>(r4)
                        r7.<init>(r2, r8, r5)
                        goto L95
                    L83:
                        com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel$IsPlaying r7 = new com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel$IsPlaying
                        com.android.systemui.common.shared.model.Color$Attribute r8 = new com.android.systemui.common.shared.model.Color$Attribute
                        r8.<init>(r4)
                        com.android.systemui.common.shared.model.Color$Attribute r4 = new com.android.systemui.common.shared.model.Color$Attribute
                        r5 = 17957060(0x11200c4, float:2.6816514E-38)
                        r4.<init>(r5)
                        r7.<init>(r2, r8, r4)
                    L95:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto La0
                        return r1
                    La0:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
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
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel r5 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel) r5
                        boolean r5 = r5.isInAudioSharing()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
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
            if (expandable != null) {
                expandable.dialogTransitionController(new DialogCuj(58, "media_output"));
            }
            mediaOutputActionsInteractor.mediaOutputDialogManager.createAndShow(null, false, null, false, null, null);
        }
    }
}
