package com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
import com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class ScreenRecordChipViewModel implements OngoingActivityChipViewModel {
    public static final Companion Companion = new Companion(null);
    public static final int ICON = R.drawable.ic_screenrecord;
    public final ReadonlyStateFlow chip;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final ScreenRecordChipInteractor interactor;
    public final SystemClock systemClock;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ScreenRecordChipViewModel(CoroutineScope coroutineScope, ScreenRecordChipInteractor screenRecordChipInteractor, SystemClock systemClock, EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, DialogTransitionAnimator dialogTransitionAnimator) {
        this.interactor = screenRecordChipInteractor;
        this.systemClock = systemClock;
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        final ReadonlyStateFlow readonlyStateFlow = screenRecordChipInteractor.screenRecordState;
        this.chip = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        r11 = this;
                        boolean r0 = r13 instanceof com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r13
                        com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r13)
                    L18:
                        java.lang.Object r13 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r13)
                        goto L88
                    L27:
                        java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                        java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                        r11.<init>(r12)
                        throw r11
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r13)
                        com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel r12 = (com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel) r12
                        boolean r13 = r12 instanceof com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel.DoingNothing
                        if (r13 == 0) goto L3b
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Hidden r12 = com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Hidden.INSTANCE
                        goto L7d
                    L3b:
                        boolean r13 = r12 instanceof com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel.Starting
                        if (r13 == 0) goto L42
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Hidden r12 = com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Hidden.INSTANCE
                        goto L7d
                    L42:
                        boolean r13 = r12 instanceof com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel.Recording
                        if (r13 == 0) goto L8b
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Shown r13 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Shown
                        com.android.systemui.common.shared.model.Icon$Resource r2 = new com.android.systemui.common.shared.model.Icon$Resource
                        int r4 = com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel.ICON
                        r5 = 0
                        r2.<init>(r4, r5)
                        com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel r4 = r11.this$0
                        com.android.systemui.util.time.SystemClock r5 = r4.systemClock
                        long r5 = r5.elapsedRealtime()
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion r7 = com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel.Companion
                        com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel$Recording r12 = (com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel.Recording) r12
                        android.app.ActivityManager$RunningTaskInfo r12 = r12.recordedTask
                        r4.getClass()
                        com.android.systemui.statusbar.chips.screenrecord.ui.view.EndScreenRecordingDialogDelegate r8 = new com.android.systemui.statusbar.chips.screenrecord.ui.view.EndScreenRecordingDialogDelegate
                        com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$createDelegate$1 r9 = new com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$createDelegate$1
                        com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor r10 = r4.interactor
                        r9.<init>(r10)
                        com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper r10 = r4.endMediaProjectionDialogHelper
                        r8.<init>(r10, r9, r12)
                        com.android.systemui.animation.DialogTransitionAnimator r12 = r4.dialogTransitionAnimator
                        r7.getClass()
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 r4 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1
                        r4.<init>(r8, r12)
                        r13.<init>(r2, r5, r4)
                        r12 = r13
                    L7d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                        java.lang.Object r11 = r11.emit(r12, r0)
                        if (r11 != r1) goto L88
                        return r1
                    L88:
                        kotlin.Unit r11 = kotlin.Unit.INSTANCE
                        return r11
                    L8b:
                        kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
                        r11.<init>()
                        throw r11
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.screenrecord.ui.viewmodel.ScreenRecordChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), OngoingActivityChipModel.Hidden.INSTANCE);
    }
}
