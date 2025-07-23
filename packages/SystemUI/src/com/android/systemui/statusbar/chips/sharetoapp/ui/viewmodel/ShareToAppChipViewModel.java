package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.MediaProjectionStopDialogModel;
import com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.ChipTransitionHelper;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(hidden);
        this._stopDialogToShow = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.stopDialogToShow = asStateFlow;
        MediaProjectionManagerRepository$special$$inlined$map$1 mediaProjectionManagerRepository$special$$inlined$map$1 = mediaProjectionChipInteractor.projectionStartedDuringCallAndActivePostCallEvent;
        ShareToAppChipViewModel$stopDialogDueToCallEndedState$1 shareToAppChipViewModel$stopDialogDueToCallEndedState$1 = new ShareToAppChipViewModel$stopDialogDueToCallEndedState$1(this, null);
        final ReadonlyStateFlow readonlyStateFlow = mediaProjectionChipInteractor.projection;
        Flow sample = com.android.systemui.util.kotlin.FlowKt.sample(mediaProjectionManagerRepository$special$$inlined$map$1, readonlyStateFlow, shareToAppChipViewModel$stopDialogDueToCallEndedState$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.stopDialogDueToCallEndedState = FlowKt.stateIn(sample, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), hidden);
        ReadonlyStateFlow stateIn = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:21:0x0182 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r25, kotlin.coroutines.Continuation r26) {
                    /*
                        Method dump skipped, instructions count: 396
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.Lazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.internalChip = stateIn;
        ChipTransitionHelper chipTransitionHelper = new ChipTransitionHelper(coroutineScope);
        this.chipTransitionHelper = chipTransitionHelper;
        this.chip = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(chipTransitionHelper.createChipFlow(stateIn), asStateFlow, new ShareToAppChipViewModel$chip$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new OngoingActivityChipModel.Inactive(false, null, 3, null));
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
        BuildersKt.launch$default(this.scope, null, null, new ShareToAppChipViewModel$start$1(this, null), 3);
    }
}
