package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.statusbar.chips.mediaprojection.domain.interactor.MediaProjectionChipInteractor;
import com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper;
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

public final class ShareToAppChipViewModel implements OngoingActivityChipViewModel {
    public static final Companion Companion = new Companion(null);
    public static final int SHARE_TO_APP_ICON = R.drawable.ic_screenshot_share;
    public final ReadonlyStateFlow chip;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final EndMediaProjectionDialogHelper endMediaProjectionDialogHelper;
    public final MediaProjectionChipInteractor mediaProjectionChipInteractor;
    public final SystemClock systemClock;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ShareToAppChipViewModel(CoroutineScope coroutineScope, MediaProjectionChipInteractor mediaProjectionChipInteractor, SystemClock systemClock, DialogTransitionAnimator dialogTransitionAnimator, EndMediaProjectionDialogHelper endMediaProjectionDialogHelper) {
        this.mediaProjectionChipInteractor = mediaProjectionChipInteractor;
        this.systemClock = systemClock;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.endMediaProjectionDialogHelper = endMediaProjectionDialogHelper;
        final ReadonlyStateFlow readonlyStateFlow = mediaProjectionChipInteractor.projection;
        this.chip = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L88
                    L27:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r12)
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel r11 = (com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel) r11
                        boolean r12 = r11 instanceof com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.NotProjecting
                        if (r12 == 0) goto L3b
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Hidden r11 = com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Hidden.INSTANCE
                        goto L7d
                    L3b:
                        boolean r12 = r11 instanceof com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Projecting
                        if (r12 == 0) goto L8b
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Projecting r11 = (com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Projecting) r11
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Type r12 = r11.type
                        com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel$Type r2 = com.android.systemui.statusbar.chips.mediaprojection.domain.model.ProjectionChipModel.Type.SHARE_TO_APP
                        if (r12 == r2) goto L4a
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Hidden r11 = com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Hidden.INSTANCE
                        goto L7d
                    L4a:
                        com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$Companion r12 = com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel.Companion
                        com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel r12 = r10.this$0
                        r12.getClass()
                        com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Shown r2 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Shown
                        com.android.systemui.common.shared.model.Icon$Resource r4 = new com.android.systemui.common.shared.model.Icon$Resource
                        int r5 = com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel.SHARE_TO_APP_ICON
                        r6 = 0
                        r4.<init>(r5, r6)
                        com.android.systemui.util.time.SystemClock r5 = r12.systemClock
                        long r5 = r5.elapsedRealtime()
                        com.android.systemui.statusbar.chips.sharetoapp.ui.view.EndShareToAppDialogDelegate r7 = new com.android.systemui.statusbar.chips.sharetoapp.ui.view.EndShareToAppDialogDelegate
                        com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$createShareToAppDialogDelegate$1 r8 = new com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$createShareToAppDialogDelegate$1
                        r8.<init>(r12)
                        com.android.systemui.statusbar.chips.mediaprojection.ui.view.EndMediaProjectionDialogHelper r9 = r12.endMediaProjectionDialogHelper
                        r7.<init>(r9, r8, r11)
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion r11 = com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel.Companion
                        r11.getClass()
                        com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 r11 = new com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1
                        com.android.systemui.animation.DialogTransitionAnimator r12 = r12.dialogTransitionAnimator
                        r11.<init>(r7, r12)
                        r2.<init>(r4, r5, r11)
                        r11 = r2
                    L7d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto L88
                        return r1
                    L88:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    L8b:
                        kotlin.NoWhenBranchMatchedException r10 = new kotlin.NoWhenBranchMatchedException
                        r10.<init>()
                        throw r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel.ShareToAppChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
