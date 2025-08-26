package com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel;

import android.app.ActivityManager;
import android.content.Context;
import android.view.View;
import com.android.internal.logging.InstanceId;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor;
import com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel;
import com.android.systemui.statusbar.chips.screenrecord.ui.view.EndScreenRecordingDialogDelegate;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
import com.android.systemui.statusbar.chips.ui.model.ColorsModel;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.ChipTransitionHelper;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1;
import com.android.systemui.statusbar.chips.ui.viewmodel.TimeSource;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.time.SystemClock;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedLazily;

/* loaded from: classes3.dex */
public final class ScreenRecordChipViewModel implements OngoingActivityChipViewModel {
    public static final String TAG;
    public final ReadonlyStateFlow chip;
    public final ChipTransitionHelper chipTransitionHelper;
    public final ReadonlyStateFlow chipWithConsistentTimer;
    public final Context context;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final InstanceId instanceId;
    public final ScreenRecordChipInteractor interactor;
    public final LogBuffer logger;
    public final ShareToAppChipViewModel shareToAppChipViewModel;
    public final ReadonlyStateFlow simpleChip;
    public final SystemClock systemClock;
    public final StatusBarChipsUiEventLogger uiEventLogger;
    public static final Companion Companion = new Companion(null);
    public static final int ICON = R.drawable.ic_screenrecord;
    public static final DialogCuj DIALOG_CUJ = new DialogCuj(111, "Screen record");

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "ScreenRecordVM");
    }

    public ScreenRecordChipViewModel(CoroutineScope coroutineScope, Context context, ScreenRecordChipInteractor screenRecordChipInteractor, ShareToAppChipViewModel shareToAppChipViewModel, SystemClock systemClock, EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, DialogTransitionAnimator dialogTransitionAnimator, LogBuffer logBuffer, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger) {
        this.context = context;
        this.interactor = screenRecordChipInteractor;
        this.shareToAppChipViewModel = shareToAppChipViewModel;
        this.systemClock = systemClock;
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.logger = logBuffer;
        this.uiEventLogger = statusBarChipsUiEventLogger;
        this.instanceId = statusBarChipsUiEventLogger.instanceIdSequence.newInstanceId();
        final ReadonlyStateFlow readonlyStateFlow = screenRecordChipInteractor.screenRecordState;
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ScreenRecordChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ScreenRecordChipViewModel screenRecordChipViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = screenRecordChipViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object timer;
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
                        ScreenRecordChipModel screenRecordChipModel = (ScreenRecordChipModel) obj;
                        if (screenRecordChipModel instanceof ScreenRecordChipModel.DoingNothing) {
                            timer = new OngoingActivityChipModel.Inactive(false, null, 3, null);
                            i = 1;
                        } else {
                            boolean z = screenRecordChipModel instanceof ScreenRecordChipModel.Starting;
                            ScreenRecordChipViewModel screenRecordChipViewModel = this.this$0;
                            if (z) {
                                ColorsModel.Red red = ColorsModel.Red.INSTANCE;
                                ScreenRecordModel.Starting.Companion companion = ScreenRecordModel.Starting.Companion;
                                long j = ((ScreenRecordChipModel.Starting) screenRecordChipModel).millisUntilStarted;
                                companion.getClass();
                                i = 1;
                                timer = new OngoingActivityChipModel.Active.Countdown("ScreenRecord", true, red, Math.floorDiv(j + 500, 1000), null, false, false, screenRecordChipViewModel.instanceId, 112, null);
                            } else {
                                if (!(screenRecordChipModel instanceof ScreenRecordChipModel.Recording)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                OngoingActivityChipModel.ChipIcon.SingleColorIcon singleColorIcon = new OngoingActivityChipModel.ChipIcon.SingleColorIcon(new Icon.Resource(ScreenRecordChipViewModel.ICON, new ContentDescription.Resource(R.string.screenrecord_ongoing_screen_only)));
                                ColorsModel.Red red2 = ColorsModel.Red.INSTANCE;
                                long jElapsedRealtime = screenRecordChipViewModel.systemClock.elapsedRealtime();
                                OngoingActivityChipViewModel.Companion companion2 = OngoingActivityChipViewModel.Companion;
                                ScreenRecordChipModel.Recording recording = (ScreenRecordChipModel.Recording) screenRecordChipModel;
                                ActivityManager.RunningTaskInfo runningTaskInfo = recording.recordedTask;
                                Context context = screenRecordChipViewModel.context;
                                ScreenRecordChipViewModel$createDelegate$1 screenRecordChipViewModel$createDelegate$1 = new ScreenRecordChipViewModel$createDelegate$1(screenRecordChipViewModel);
                                EndMediaProjectionDialogHelper endMediaProjectionDialogHelper = screenRecordChipViewModel.endMediaProjectionDialogHelper;
                                EndScreenRecordingDialogDelegate endScreenRecordingDialogDelegate = new EndScreenRecordingDialogDelegate(endMediaProjectionDialogHelper, context, screenRecordChipViewModel$createDelegate$1, runningTaskInfo);
                                DialogCuj dialogCuj = ScreenRecordChipViewModel.DIALOG_CUJ;
                                InstanceId instanceId = screenRecordChipViewModel.instanceId;
                                String str = ScreenRecordChipViewModel.TAG;
                                companion2.getClass();
                                OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 = new OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1(screenRecordChipViewModel.logger, str, screenRecordChipViewModel.uiEventLogger, instanceId, endScreenRecordingDialogDelegate, screenRecordChipViewModel.dialogTransitionAnimator, dialogCuj);
                                new EndScreenRecordingDialogDelegate(endMediaProjectionDialogHelper, screenRecordChipViewModel.context, new ScreenRecordChipViewModel$createDelegate$1(screenRecordChipViewModel), recording.recordedTask);
                                timer = new OngoingActivityChipModel.Active.Timer("ScreenRecord", true, singleColorIcon, red2, jElapsedRealtime, null, false, ongoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1, new OngoingActivityChipModel.ClickBehavior.ExpandAction(new OngoingActivityChipViewModel$Companion$$ExternalSyntheticLambda0()), null, false, false, screenRecordChipViewModel.instanceId, 3680, null);
                                i = 1;
                            }
                        }
                        anonymousClass1.label = i;
                        if (this.$this_unsafeFlow.emit(timer, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
        };
        SharingStarted.Companion.getClass();
        StartedLazily startedLazily = SharingStarted.Companion.Lazily;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, startedLazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.simpleChip = readonlyStateFlowStateIn;
        final Flow flowPairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(readonlyStateFlowStateIn, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
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
                        WithPrev withPrev = (WithPrev) obj;
                        OngoingActivityChipModel ongoingActivityChipModel = (OngoingActivityChipModel) withPrev.component1();
                        OngoingActivityChipModel timer = (OngoingActivityChipModel) withPrev.component2();
                        if ((ongoingActivityChipModel instanceof OngoingActivityChipModel.Active.Timer) && (timer instanceof OngoingActivityChipModel.Active.Timer)) {
                            OngoingActivityChipModel.Active.Timer timer2 = (OngoingActivityChipModel.Active.Timer) timer;
                            long j = ((OngoingActivityChipModel.Active.Timer) ongoingActivityChipModel).startTimeMs;
                            String str = timer2.key;
                            boolean z = timer2.isImportantForPrivacy;
                            OngoingActivityChipModel.ChipIcon chipIcon = timer2.icon;
                            ColorsModel colorsModel = timer2.colors;
                            TimeSource timeSource = timer2.timeSource;
                            boolean z2 = timer2.isEventInFuture;
                            View.OnClickListener onClickListener = timer2.onClickListenerLegacy;
                            OngoingActivityChipModel.ClickBehavior clickBehavior = timer2.clickBehavior;
                            OngoingActivityChipModel.TransitionManager transitionManager = timer2.transitionManager;
                            boolean z3 = timer2.isHidden;
                            boolean z4 = timer2.shouldAnimate;
                            InstanceId instanceId = timer2.instanceId;
                            timer2.getClass();
                            timer = new OngoingActivityChipModel.Active.Timer(str, z, chipIcon, colorsModel, j, timeSource, z2, onClickListener, clickBehavior, transitionManager, z3, z4, instanceId);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(timer, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedLazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.chipWithConsistentTimer = readonlyStateFlowStateIn2;
        ChipTransitionHelper chipTransitionHelper = new ChipTransitionHelper(coroutineScope);
        this.chipTransitionHelper = chipTransitionHelper;
        this.chip = chipTransitionHelper.createChipFlow(readonlyStateFlowStateIn2);
    }
}
