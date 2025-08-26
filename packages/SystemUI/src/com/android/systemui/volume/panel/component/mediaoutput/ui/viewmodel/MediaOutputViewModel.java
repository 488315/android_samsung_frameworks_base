package com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Color;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputActionsInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel;
import com.android.systemui.volume.panel.shared.model.Result;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import com.android.systemui.volume.panel.shared.model.ResultKt$filterData$$inlined$map$1;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.NoWhenBranchMatchedException;
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
        final ResultKt$filterData$$inlined$map$1 resultKt$filterData$$inlined$map$1FilterData = ResultKt.filterData(mediaOutputComponentInteractor.mediaOutputModel);
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    String string;
                    String string2;
                    String name;
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
                        kotlin.ResultKt.throwOnFailure(obj2);
                        MediaOutputComponentModel mediaOutputComponentModel = (MediaOutputComponentModel) obj;
                        boolean z = mediaOutputComponentModel instanceof MediaOutputComponentModel.Idle;
                        MediaOutputViewModel mediaOutputViewModel = this.this$0;
                        if (z) {
                            string = mediaOutputViewModel.context.getString(R.string.media_output_title_without_playing);
                        } else if (mediaOutputComponentModel instanceof MediaOutputComponentModel.MediaSession) {
                            MediaOutputComponentModel.MediaSession mediaSession = (MediaOutputComponentModel.MediaSession) mediaOutputComponentModel;
                            string = mediaSession.isPlaybackActive ? mediaOutputViewModel.context.getString(R.string.media_output_label_title, mediaSession.session.appLabel) : mediaOutputViewModel.context.getString(R.string.media_output_title_without_playing);
                        } else {
                            if (!(mediaOutputComponentModel instanceof MediaOutputComponentModel.Calling)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            string = mediaOutputViewModel.context.getString(R.string.media_output_title_ongoing_call);
                        }
                        string.getClass();
                        Color.Resource resource = new Color.Resource(android.R.color.search_url_text_normal);
                        if (mediaOutputComponentModel.isInAudioSharing()) {
                            string2 = mediaOutputViewModel.context.getString(R.string.audio_sharing_description);
                        } else {
                            AudioOutputDevice device = mediaOutputComponentModel.getDevice();
                            if (device instanceof AudioOutputDevice.Unknown) {
                                device = null;
                            }
                            string2 = (device == null || (name = device.getName()) == null) ? mediaOutputViewModel.context.getString(R.string.media_seamless_other_device) : name;
                        }
                        ConnectedDeviceViewModel connectedDeviceViewModel = new ConnectedDeviceViewModel(string, resource, string2, mediaOutputComponentModel.getCanOpenAudioSwitcher() ? new Color.Resource(android.R.color.search_url_text_material_light) : new Color.Resource(android.R.color.search_url_text_normal));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(connectedDeviceViewModel, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        kotlin.ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = resultKt$filterData$$inlined$map$1FilterData.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.connectedDeviceViewModel = FlowKt.stateIn(flow, coroutineScope, startedEagerly, null);
        ReadonlyStateFlow readonlyStateFlow = mediaOutputComponentInteractor.mediaOutputModel;
        final ResultKt$filterData$$inlined$map$1 resultKt$filterData$$inlined$map$1FilterData2 = ResultKt.filterData(readonlyStateFlow);
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object isPlaying;
                    Drawable icon;
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
                        kotlin.ResultKt.throwOnFailure(obj2);
                        MediaOutputComponentModel mediaOutputComponentModel = (MediaOutputComponentModel) obj;
                        AudioOutputDevice device = mediaOutputComponentModel.getDevice();
                        if (device instanceof AudioOutputDevice.Unknown) {
                            device = null;
                        }
                        Icon resource = (device == null || (icon = device.getIcon()) == null) ? new Icon.Resource(R.drawable.ic_media_home_devices, null) : new Icon.Loaded(icon, null, null, 4, null);
                        MediaOutputComponentModel.MediaSession mediaSession = mediaOutputComponentModel instanceof MediaOutputComponentModel.MediaSession ? (MediaOutputComponentModel.MediaSession) mediaOutputComponentModel : null;
                        boolean z = mediaSession != null && mediaSession.isPlaybackActive;
                        boolean z2 = mediaOutputComponentModel instanceof MediaOutputComponentModel.Calling;
                        if (z || z2) {
                            isPlaying = new DeviceIconViewModel.IsPlaying(resource, mediaOutputComponentModel.getCanOpenAudioSwitcher() ? new Color.Resource(android.R.color.side_fps_text_color) : new Color.Resource(android.R.color.suggestion_highlight_text), mediaOutputComponentModel.getCanOpenAudioSwitcher() ? new Color.Resource(android.R.color.secondary_text_nodisable_holo_light) : new Color.Resource(android.R.color.secondary_device_default_settings_light));
                        } else {
                            isPlaying = new DeviceIconViewModel.IsNotPlaying(resource, mediaOutputComponentModel.getCanOpenAudioSwitcher() ? new Color.Resource(android.R.color.search_url_text_normal) : new Color.Resource(android.R.color.secondary_device_default_settings_light), new Color.Loaded(0));
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(isPlaying, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        kotlin.ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = resultKt$filterData$$inlined$map$1FilterData2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, null);
        final ResultKt$filterData$$inlined$map$1 resultKt$filterData$$inlined$map$1FilterData3 = ResultKt.filterData(readonlyStateFlow);
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
                        kotlin.ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((MediaOutputComponentModel) obj).getCanOpenAudioSwitcher());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        kotlin.ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = resultKt$filterData$$inlined$map$1FilterData3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
