package com.android.systemui.blur.domain.interactor;

import android.util.Log;
import android.view.SemBlurInfo;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.data.repository.SecBouncerColorCurveRepository;
import com.android.systemui.blur.data.repository.SecQsColorCurveRepository;
import com.android.systemui.blur.di.CapturedBlurBlurInfoProvider;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecCapturedBlurInfoInteractor {
    public final SharedFlowImpl blurInfoData = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
    public final CapturedBlurBlurInfoProvider capturedBlurBlurInfoProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.blur.domain.interactor.SecCapturedBlurInfoInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ PowerInteractor $powerInteractor;
        final /* synthetic */ PrimaryBouncerInteractor $primaryBouncerInteractor;
        final /* synthetic */ SecCapturedBlurInteractor $secCapturedBlurInteractor;
        int label;
        final /* synthetic */ SecCapturedBlurInfoInteractor this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.blur.domain.interactor.SecCapturedBlurInfoInteractor$1$1, reason: invalid class name and collision with other inner class name */
        final class C00531 extends SuspendLambda implements Function2 {
            final /* synthetic */ PowerInteractor $powerInteractor;
            final /* synthetic */ PrimaryBouncerInteractor $primaryBouncerInteractor;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SecCapturedBlurInfoInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00531(PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, SecCapturedBlurInfoInteractor secCapturedBlurInfoInteractor, Continuation continuation) {
                super(2, continuation);
                this.$powerInteractor = powerInteractor;
                this.$primaryBouncerInteractor = primaryBouncerInteractor;
                this.this$0 = secCapturedBlurInfoInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00531 c00531 = new C00531(this.$powerInteractor, this.$primaryBouncerInteractor, this.this$0, continuation);
                c00531.L$0 = obj;
                return c00531;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00531) create((SecPanelBlurBinding.BlurType) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                SecPanelBlurBinding.BlurType blurType;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    SecPanelBlurBinding.BlurType blurType2 = (SecPanelBlurBinding.BlurType) this.L$0;
                    if (blurType2 != SecPanelBlurBinding.BlurType.QUICK_PANEL) {
                        SemBlurInfo.Builder semBlurInfoBuilder = this.this$0.getSemBlurInfoBuilder(blurType2);
                        if (semBlurInfoBuilder != null) {
                            this.this$0.blurInfoData.tryEmit(new Pair(semBlurInfoBuilder, blurType2));
                        }
                        return Unit.INSTANCE;
                    }
                    ReadonlyStateFlow readonlyStateFlow = this.$powerInteractor.detailedWakefulness;
                    this.L$0 = blurType2;
                    this.label = 1;
                    Object first = FlowKt.first(readonlyStateFlow, this);
                    if (first == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    blurType = blurType2;
                    obj = first;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    blurType = (SecPanelBlurBinding.BlurType) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (((WakefulnessModel) obj).internalWakefulnessState == WakefulnessState.STARTING_TO_WAKE) {
                    Boxing.boxInt(Log.d("SecCapturedBlurInfoInteractor", "Skip while STARTING_TO_WAKE QUICK_PANEL captured blur case"));
                } else if (this.$primaryBouncerInteractor.isBouncerShowing()) {
                    Boxing.boxInt(Log.d("SecCapturedBlurInfoInteractor", "Skip while BouncerShowing captured blur case"));
                } else {
                    SemBlurInfo.Builder semBlurInfoBuilder2 = this.this$0.getSemBlurInfoBuilder(blurType);
                    if (semBlurInfoBuilder2 != null) {
                        this.this$0.blurInfoData.tryEmit(new Pair(semBlurInfoBuilder2, blurType));
                    }
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SecCapturedBlurInteractor secCapturedBlurInteractor, PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, SecCapturedBlurInfoInteractor secCapturedBlurInfoInteractor, Continuation continuation) {
            super(2, continuation);
            this.$secCapturedBlurInteractor = secCapturedBlurInteractor;
            this.$powerInteractor = powerInteractor;
            this.$primaryBouncerInteractor = primaryBouncerInteractor;
            this.this$0 = secCapturedBlurInfoInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$secCapturedBlurInteractor, this.$powerInteractor, this.$primaryBouncerInteractor, this.this$0, continuation);
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
                SharedFlowImpl sharedFlowImpl = this.$secCapturedBlurInteractor.requestCaptureBlur;
                C00531 c00531 = new C00531(this.$powerInteractor, this.$primaryBouncerInteractor, this.this$0, null);
                this.label = 1;
                if (FlowKt.collectLatest(sharedFlowImpl, c00531, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SecCapturedBlurInfoInteractor(CoroutineScope coroutineScope, CapturedBlurBlurInfoProvider capturedBlurBlurInfoProvider, SecCapturedBlurInteractor secCapturedBlurInteractor, PowerInteractor powerInteractor, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.capturedBlurBlurInfoProvider = capturedBlurBlurInfoProvider;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(secCapturedBlurInteractor, powerInteractor, primaryBouncerInteractor, this, null), 3);
    }

    public final SemBlurInfo.Builder getSemBlurInfoBuilder(SecPanelBlurBinding.BlurType blurType) {
        CapturedBlurBlurInfoProvider capturedBlurBlurInfoProvider = this.capturedBlurBlurInfoProvider;
        capturedBlurBlurInfoProvider.getClass();
        int i = CapturedBlurBlurInfoProvider.WhenMappings.$EnumSwitchMapping$0[blurType.ordinal()];
        if (i != 1) {
            SecBouncerColorCurveRepository secBouncerColorCurveRepository = capturedBlurBlurInfoProvider.secBouncerColorCurveRepository;
            if (i == 2) {
                return secBouncerColorCurveRepository.getBlurInfo();
            }
            if (i == 3) {
                return secBouncerColorCurveRepository.getBlurInfo();
            }
            if (i != 4) {
                return null;
            }
            return secBouncerColorCurveRepository.getBlurInfo();
        }
        SecQsColorCurveRepository secQsColorCurveRepository = capturedBlurBlurInfoProvider.secQsColorCurveRepository;
        secQsColorCurveRepository.getClass();
        SemBlurInfo.Builder builder = new SemBlurInfo.Builder(1);
        QSColorCurve qSColorCurve = secQsColorCurveRepository.qsColorCurve;
        boolean z = secQsColorCurveRepository.hasCustomColorBg;
        builder.setColorCurve(z ? qSColorCurve.saturation : 0.0f, z ? 0.0f : qSColorCurve.curve, z ? 0.0f : qSColorCurve.minX, z ? 255.0f : qSColorCurve.maxX, z ? 0.0f : qSColorCurve.minY, z ? 255.0f : qSColorCurve.maxY);
        builder.setRadius((int) qSColorCurve.radius);
        return builder;
    }
}
