package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.sharetoapp.ui.view.EndGenericShareToAppDialogDelegate;
import com.android.systemui.statusbar.chips.sharetoapp.ui.view.EndShareScreenToAppDialogDelegate;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import com.android.systemui.statusbar.chips.ui.model.ColorsModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.ChipTransitionHelper;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.util.time.SystemClock;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class ShareToAppChipViewModel implements OngoingActivityChipViewModel, CoreStartable {
    public static final String TAG;
    public final StateFlowImpl _stopDialogToShow;
    public final ReadonlyStateFlow chip;
    public final ChipTransitionHelper chipTransitionHelper;
    public final Context context;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final InstanceId instanceId;
    public final ReadonlyStateFlow internalChip;
    public final LogBuffer logger;
    public final MediaProjectionChipInteractor mediaProjectionChipInteractor;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow stopDialogDueToCallEndedState;
    public final ReadonlyStateFlow stopDialogToShow;
    public final SystemClock systemClock;
    public final StatusBarChipsUiEventLogger uiEventLogger;
    public static final Companion Companion = new Companion(null);
    public static final int SHARE_TO_APP_ICON = R.drawable.ic_present_to_all;
    public static final DialogCuj DIALOG_CUJ = new DialogCuj(111, "Share to app");
    public static final DialogCuj DIALOG_CUJ_AUDIO_ONLY = new DialogCuj(111, "Share to app audio only");

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ProjectionChipModel.ContentType.values().length];
            try {
                iArr[ProjectionChipModel.ContentType.Screen.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionChipModel.ContentType.Audio.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProjectionChipModel.Receiver.values().length];
            try {
                iArr2[ProjectionChipModel.Receiver.ShareToApp.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ProjectionChipModel.Receiver.CastToOtherDevice.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* renamed from: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShareToAppChipViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ShareToAppChipViewModel shareToAppChipViewModel = ShareToAppChipViewModel.this;
                ReadonlyStateFlow readonlyStateFlow = shareToAppChipViewModel.stopDialogDueToCallEndedState;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        shareToAppChipViewModel._stopDialogToShow.setValue((MediaProjectionStopDialogModel) obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    static {
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "ShareToAppVM");
    }

    public ShareToAppChipViewModel(CoroutineScope coroutineScope, Context context, MediaProjectionChipInteractor mediaProjectionChipInteractor, SystemClock systemClock, EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, DialogTransitionAnimator dialogTransitionAnimator, LogBuffer logBuffer, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger) {
        this.scope = coroutineScope;
        this.context = context;
        this.mediaProjectionChipInteractor = mediaProjectionChipInteractor;
        this.systemClock = systemClock;
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.logger = logBuffer;
        this.uiEventLogger = statusBarChipsUiEventLogger;
        this.instanceId = statusBarChipsUiEventLogger.instanceIdSequence.newInstanceId();
        MediaProjectionStopDialogModel.Hidden hidden = MediaProjectionStopDialogModel.Hidden.INSTANCE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(hidden);
        this._stopDialogToShow = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.stopDialogToShow = readonlyStateFlowAsStateFlow;
        MediaProjectionManagerRepository$special$$inlined$map$1 mediaProjectionManagerRepository$special$$inlined$map$1 = mediaProjectionChipInteractor.projectionStartedDuringCallAndActivePostCallEvent;
        ShareToAppChipViewModel$stopDialogDueToCallEndedState$1 shareToAppChipViewModel$stopDialogDueToCallEndedState$1 = new ShareToAppChipViewModel$stopDialogDueToCallEndedState$1(this, null);
        final ReadonlyStateFlow readonlyStateFlow = mediaProjectionChipInteractor.projection;
        Flow flowSample = com.android.systemui.util.kotlin.FlowKt.sample(mediaProjectionManagerRepository$special$$inlined$map$1, readonlyStateFlow, shareToAppChipViewModel$stopDialogDueToCallEndedState$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.stopDialogDueToCallEndedState = FlowKt.stateIn(flowSample, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), hidden);
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ShareToAppChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ShareToAppChipViewModel shareToAppChipViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = shareToAppChipViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:37:0x0182 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object timer;
                    Object inactive;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i2 - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i3 = anonymousClass1.label;
                    if (i3 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ProjectionChipModel projectionChipModel = (ProjectionChipModel) obj;
                        if (projectionChipModel instanceof ProjectionChipModel.NotProjecting) {
                            inactive = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                        } else {
                            if (!(projectionChipModel instanceof ProjectionChipModel.Projecting)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            ProjectionChipModel.Projecting projecting = (ProjectionChipModel.Projecting) projectionChipModel;
                            int i4 = ShareToAppChipViewModel.WhenMappings.$EnumSwitchMapping$1[projecting.receiver.ordinal()];
                            if (i4 == 1) {
                                int i5 = ShareToAppChipViewModel.WhenMappings.$EnumSwitchMapping$0[projecting.contentType.ordinal()];
                                ShareToAppChipViewModel shareToAppChipViewModel = this.this$0;
                                if (i5 == 1) {
                                    ShareToAppChipViewModel.Companion companion = ShareToAppChipViewModel.Companion;
                                    shareToAppChipViewModel.getClass();
                                    OngoingActivityChipModel.ChipIcon.SingleColorIcon singleColorIcon = new OngoingActivityChipModel.ChipIcon.SingleColorIcon(new Icon.Resource(ShareToAppChipViewModel.SHARE_TO_APP_ICON, new ContentDescription.Resource(R.string.share_to_app_chip_accessibility_label)));
                                    ColorsModel.Red red = ColorsModel.Red.INSTANCE;
                                    long jElapsedRealtime = shareToAppChipViewModel.systemClock.elapsedRealtime();
                                    Context context = shareToAppChipViewModel.context;
                                    ShareToAppChipViewModel$createShareScreenToAppDialogDelegate$1 shareToAppChipViewModel$createShareScreenToAppDialogDelegate$1 = new ShareToAppChipViewModel$createShareScreenToAppDialogDelegate$1(shareToAppChipViewModel);
                                    EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = shareToAppChipViewModel.endMediaProjectionDialogHelper;
                                    EndShareScreenToAppDialogDelegate endShareScreenToAppDialogDelegate = new EndShareScreenToAppDialogDelegate(endMediaProjectionDialogHelper, context, shareToAppChipViewModel$createShareScreenToAppDialogDelegate$1, projecting);
                                    InstanceId instanceId = shareToAppChipViewModel.instanceId;
                                    OngoingActivityChipViewModel.Companion.getClass();
                                    OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 = new OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1(shareToAppChipViewModel.logger, ShareToAppChipViewModel.TAG, shareToAppChipViewModel.uiEventLogger, instanceId, endShareScreenToAppDialogDelegate, shareToAppChipViewModel.dialogTransitionAnimator, ShareToAppChipViewModel.DIALOG_CUJ);
                                    new EndShareScreenToAppDialogDelegate(endMediaProjectionDialogHelper, shareToAppChipViewModel.context, new ShareToAppChipViewModel$createShareScreenToAppDialogDelegate$1(shareToAppChipViewModel), projecting);
                                    timer = new OngoingActivityChipModel.Active.Timer("ShareToApp", true, singleColorIcon, red, jElapsedRealtime, null, false, ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1, new OngoingActivityChipModel.ClickBehavior.ExpandAction(new OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0()), null, false, false, shareToAppChipViewModel.instanceId, 3680, null);
                                } else {
                                    if (i5 != 2) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    ShareToAppChipViewModel.Companion companion2 = ShareToAppChipViewModel.Companion;
                                    shareToAppChipViewModel.getClass();
                                    OngoingActivityChipModel.ChipIcon.SingleColorIcon singleColorIcon2 = new OngoingActivityChipModel.ChipIcon.SingleColorIcon(new Icon.Resource(ShareToAppChipViewModel.SHARE_TO_APP_ICON, new ContentDescription.Resource(R.string.share_to_app_chip_accessibility_label_generic)));
                                    ColorsModel.Red red2 = ColorsModel.Red.INSTANCE;
                                    Context context2 = shareToAppChipViewModel.context;
                                    ShareToAppChipViewModel$createGenericShareToAppDialogDelegate$1 shareToAppChipViewModel$createGenericShareToAppDialogDelegate$1 = new ShareToAppChipViewModel$createGenericShareToAppDialogDelegate$1(shareToAppChipViewModel);
                                    EndMediaProjectionDialogHelper endMediaProjectionDialogHelper2 = shareToAppChipViewModel.endMediaProjectionDialogHelper;
                                    EndGenericShareToAppDialogDelegate endGenericShareToAppDialogDelegate = new EndGenericShareToAppDialogDelegate(endMediaProjectionDialogHelper2, context2, shareToAppChipViewModel$createGenericShareToAppDialogDelegate$1);
                                    InstanceId instanceId2 = shareToAppChipViewModel.instanceId;
                                    OngoingActivityChipViewModel.Companion.getClass();
                                    OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$12 = new OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1(shareToAppChipViewModel.logger, ShareToAppChipViewModel.TAG, shareToAppChipViewModel.uiEventLogger, instanceId2, endGenericShareToAppDialogDelegate, shareToAppChipViewModel.dialogTransitionAnimator, ShareToAppChipViewModel.DIALOG_CUJ_AUDIO_ONLY);
                                    new EndGenericShareToAppDialogDelegate(endMediaProjectionDialogHelper2, shareToAppChipViewModel.context, new ShareToAppChipViewModel$createGenericShareToAppDialogDelegate$1(shareToAppChipViewModel));
                                    timer = new OngoingActivityChipModel.Active.IconOnly("ShareToApp", true, singleColorIcon2, red2, ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$12, new OngoingActivityChipModel.ClickBehavior.ExpandAction(new OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0()), null, false, false, shareToAppChipViewModel.instanceId, 448, null);
                                }
                                inactive = timer;
                                i = 1;
                                anonymousClass1.label = i;
                                if (this.$this_unsafeFlow.emit(inactive, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i4 != 2) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                inactive = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                            }
                        }
                        i = 1;
                        anonymousClass1.label = i;
                        if (this.$this_unsafeFlow.emit(inactive, anonymousClass1) == coroutineSingletons) {
                        }
                    } else {
                        if (i3 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.Lazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.internalChip = readonlyStateFlowStateIn;
        ChipTransitionHelper chipTransitionHelper = new ChipTransitionHelper(coroutineScope);
        this.chipTransitionHelper = chipTransitionHelper;
        this.chip = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(chipTransitionHelper.createChipFlow(readonlyStateFlowStateIn), readonlyStateFlowAsStateFlow, new ShareToAppChipViewModel$chip$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
    }

    public static final void access$onStopDialogDismissed(ShareToAppChipViewModel shareToAppChipViewModel) {
        shareToAppChipViewModel.getClass();
        LogLevel logLevel = LogLevel.INFO;
        ShareToAppChipViewModel$$ExternalSyntheticLambda0 shareToAppChipViewModel$$ExternalSyntheticLambda0 = new ShareToAppChipViewModel$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = shareToAppChipViewModel.logger;
        logBuffer.commit(logBuffer.obtain(TAG, logLevel, shareToAppChipViewModel$$ExternalSyntheticLambda0, null));
        shareToAppChipViewModel._stopDialogToShow.setValue(MediaProjectionStopDialogModel.Hidden.INSTANCE);
    }

    public static final void access$stopProjectingFromDialog(ShareToAppChipViewModel shareToAppChipViewModel) {
        shareToAppChipViewModel.getClass();
        LogLevel logLevel = LogLevel.INFO;
        ShareToAppChipViewModel$$ExternalSyntheticLambda0 shareToAppChipViewModel$$ExternalSyntheticLambda0 = new ShareToAppChipViewModel$$ExternalSyntheticLambda0(1);
        String str = TAG;
        LogBuffer logBuffer = shareToAppChipViewModel.logger;
        logBuffer.commit(logBuffer.obtain(str, logLevel, shareToAppChipViewModel$$ExternalSyntheticLambda0, null));
        shareToAppChipViewModel.chipTransitionHelper.onActivityStoppedFromDialog();
        shareToAppChipViewModel.mediaProjectionChipInteractor.stopProjecting();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3);
    }
}
