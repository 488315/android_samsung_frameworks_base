package com.android.systemui.samsung.quicksetting.ui.panel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.android.settingslib.display.BrightnessUtils;
import com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecBrightBarViewModel extends ViewModel {
    public final StateFlowImpl _brightLevel;
    public final ReadonlyStateFlow brightLevel;
    public final ScreenBrightnessInteractor screenBrightnessInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ int I$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SecBrightBarViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.I$0 = ((GammaBrightness) obj).value;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(GammaBrightness.m1064boximpl(((GammaBrightness) obj).value), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SecBrightBarViewModel.this._brightLevel.updateState(null, new Float(BrightnessUtils.convertGammaToLinearFloat(0.0f, 1.0f, this.I$0)));
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.samsung.quicksetting.ui.panel.SecBrightBarViewModel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ float F$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = SecBrightBarViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.F$0 = ((Number) obj).floatValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                float f = this.F$0;
                SecBrightBarViewModel.this.getClass();
                int convertLinearToGammaFloat = BrightnessUtils.convertLinearToGammaFloat(f, 0.0f, 1.0f);
                ScreenBrightnessInteractor screenBrightnessInteractor = SecBrightBarViewModel.this.screenBrightnessInteractor;
                this.label = 1;
                if (screenBrightnessInteractor.m1062setTemporaryBrightnesssaDbZGg(convertLinearToGammaFloat, this) == coroutineSingletons) {
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

    public SecBrightBarViewModel(ScreenBrightnessInteractor screenBrightnessInteractor) {
        this.screenBrightnessInteractor = screenBrightnessInteractor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Float.valueOf(0.5f));
        this._brightLevel = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.brightLevel = asStateFlow;
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(screenBrightnessInteractor.gammaBrightness, new AnonymousClass1(null)), ViewModelKt.getViewModelScope(this));
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(asStateFlow, new AnonymousClass2(null)), ViewModelKt.getViewModelScope(this));
    }
}
