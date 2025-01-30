package com.android.systemui.keyguard.ui.viewmodel;

import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.shared.model.ScrimAlpha;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PrimaryBouncerToGoneTransitionViewModel {
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 bouncerAlpha;
    public boolean leaveShadeOpen;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final PrimaryBouncerToGoneTransitionViewModel$special$$inlined$map$1 scrimAlpha;
    public final SysuiStatusBarStateController statusBarStateController;
    public boolean willRunDismissFromKeyguard;

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$special$$inlined$map$1] */
    public PrimaryBouncerToGoneTransitionViewModel(KeyguardTransitionInteractor keyguardTransitionInteractor, SysuiStatusBarStateController sysuiStatusBarStateController, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION;
        KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow = new KeyguardTransitionAnimationFlow(j, keyguardTransitionInteractor.primaryBouncerToGoneTransition, null);
        Duration.Companion companion = Duration.Companion;
        this.bouncerAlpha = KeyguardTransitionAnimationFlow.m1579createFlow53AowQI$default(keyguardTransitionAnimationFlow, DurationKt.toDuration(200, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$bouncerAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(PrimaryBouncerToGoneTransitionViewModel.this.willRunDismissFromKeyguard ? 0.0f : 1.0f - ((Number) obj).floatValue());
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$bouncerAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = PrimaryBouncerToGoneTransitionViewModel.this;
                primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard = primaryBouncerToGoneTransitionViewModel.primaryBouncerInteractor.willRunDismissFromKeyguard();
                return Unit.INSTANCE;
            }
        }, null, null, null, 116);
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 m1579createFlow53AowQI$default = KeyguardTransitionAnimationFlow.m1579createFlow53AowQI$default(keyguardTransitionAnimationFlow, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$scrimAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1.0f - ((Number) obj).floatValue());
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$scrimAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = PrimaryBouncerToGoneTransitionViewModel.this;
                primaryBouncerToGoneTransitionViewModel.leaveShadeOpen = ((StatusBarStateControllerImpl) primaryBouncerToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard = primaryBouncerToGoneTransitionViewModel.primaryBouncerInteractor.willRunDismissFromKeyguard();
                return Unit.INSTANCE;
            }
        }, null, null, Interpolators.EMPHASIZED_ACCELERATE, 52);
        this.scrimAlpha = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$special$$inlined$map$1$2 */
            public final class C17632 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$special$$inlined$map$1$2", m277f = "PrimaryBouncerToGoneTransitionViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C17632.this.emit(null, this);
                    }
                }

                public C17632(FlowCollector flowCollector, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = primaryBouncerToGoneTransitionViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                ((Number) obj).floatValue();
                                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.this$0;
                                ScrimAlpha scrimAlpha = primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard ? new ScrimAlpha(0.0f, 0.0f, 0.0f, 7, null) : primaryBouncerToGoneTransitionViewModel.leaveShadeOpen ? new ScrimAlpha(0.0f, 0.0f, 0.0f, 1, null) : new ScrimAlpha(0.0f, 0.0f, 0.0f, 5, null);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(scrimAlpha, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C17632(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
