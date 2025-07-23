package com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor;
import com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedLazily;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r25, kotlin.coroutines.Continuation r26) {
                    /*
                        Method dump skipped, instructions count: 278
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedLazily startedLazily = SharingStarted.Companion.Lazily;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedLazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.simpleChip = stateIn;
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(stateIn, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r22, kotlin.coroutines.Continuation r23) {
                    /*
                        r21 = this;
                        r0 = r21
                        r1 = r23
                        boolean r2 = r1 instanceof com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r2 == 0) goto L17
                        r2 = r1
                        com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2$2$1 r2 = (com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r2
                        int r3 = r2.label
                        r4 = -2147483648(0xffffffff80000000, float:-0.0)
                        r5 = r3 & r4
                        if (r5 == 0) goto L17
                        int r3 = r3 - r4
                        r2.label = r3
                        goto L1c
                    L17:
                        com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2$2$1 r2 = new com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2$2$1
                        r2.<init>(r1)
                    L1c:
                        java.lang.Object r1 = r2.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r4 = r2.label
                        r5 = 1
                        if (r4 == 0) goto L33
                        if (r4 != r5) goto L2b
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto L8b
                    L2b:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L33:
                        kotlin.ResultKt.throwOnFailure(r1)
                        r1 = r22
                        com.android.systemui.util.kotlin.WithPrev r1 = (com.android.systemui.util.kotlin.WithPrev) r1
                        java.lang.Object r4 = r1.component1()
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel r4 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel) r4
                        java.lang.Object r1 = r1.component2()
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel r1 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel) r1
                        boolean r6 = r4 instanceof com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer
                        if (r6 == 0) goto L7f
                        boolean r6 = r1 instanceof com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer
                        if (r6 == 0) goto L7f
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer r1 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer) r1
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer r4 = (com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer) r4
                        long r11 = r4.startTimeMs
                        java.lang.String r7 = r1.key
                        boolean r8 = r1.isImportantForPrivacy
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ChipIcon r9 = r1.icon
                        com.android.systemui.statusbar.chips.ui.model.ColorsModel r10 = r1.colors
                        com.android.systemui.statusbar.chips.ui.viewmodel.TimeSource r13 = r1.timeSource
                        boolean r14 = r1.isEventInFuture
                        android.view.View$OnClickListener r15 = r1.onClickListenerLegacy
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ClickBehavior r4 = r1.clickBehavior
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager r6 = r1.transitionManager
                        boolean r5 = r1.isHidden
                        r16 = r4
                        boolean r4 = r1.shouldAnimate
                        r19 = r4
                        com.android.internal.logging.InstanceId r4 = r1.instanceId
                        r1.getClass()
                        r17 = r6
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer r6 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer
                        r20 = r4
                        r18 = r5
                        r6.<init>(r7, r8, r9, r10, r11, r13, r14, r15, r16, r17, r18, r19, r20)
                        r1 = r6
                    L7f:
                        r4 = 1
                        r2.label = r4
                        kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                        java.lang.Object r0 = r0.emit(r1, r2)
                        if (r0 != r3) goto L8b
                        return r3
                    L8b:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedLazily, new OngoingActivityChipModel.Inactive(false, null, 3, null));
        this.chipWithConsistentTimer = stateIn2;
        ChipTransitionHelper chipTransitionHelper = new ChipTransitionHelper(coroutineScope);
        this.chipTransitionHelper = chipTransitionHelper;
        this.chip = chipTransitionHelper.createChipFlow(stateIn2);
    }
}
