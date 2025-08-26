package com.android.systemui.blur.ui.viewmodel;

import android.util.Log;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes.dex */
final class SecCapturedBlurContainerViewModel$requestCaptureBlur$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PowerInteractor $powerInteractor;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SecCapturedBlurContainerViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecCapturedBlurContainerViewModel$requestCaptureBlur$1(PowerInteractor powerInteractor, SecCapturedBlurContainerViewModel secCapturedBlurContainerViewModel, Continuation continuation) {
        super(3, continuation);
        this.$powerInteractor = powerInteractor;
        this.this$0 = secCapturedBlurContainerViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SecCapturedBlurContainerViewModel$requestCaptureBlur$1 secCapturedBlurContainerViewModel$requestCaptureBlur$1 = new SecCapturedBlurContainerViewModel$requestCaptureBlur$1(this.$powerInteractor, this.this$0, (Continuation) obj3);
        secCapturedBlurContainerViewModel$requestCaptureBlur$1.L$0 = (FlowCollector) obj;
        secCapturedBlurContainerViewModel$requestCaptureBlur$1.L$1 = (SecPanelBlurBinding.BlurType) obj2;
        return secCapturedBlurContainerViewModel$requestCaptureBlur$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0087, code lost:
    
        if (r2.emit(r1, r8) == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0092, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0061  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SecPanelBlurBinding.BlurType blurType;
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                if (i != 2 && i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            blurType = (SecPanelBlurBinding.BlurType) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            if (((WakefulnessModel) obj).internalWakefulnessState != WakefulnessState.STARTING_TO_WAKE) {
                Boxing.boxInt(Log.d("SecCapturedBlurContainerViewModel", "Skip while STARTING_TO_WAKE QUICK_PANEL captured blur case"));
            } else if (((Boolean) this.this$0.bouncerShowing.$$delegate_0.getValue()).booleanValue()) {
                Boxing.boxInt(Log.d("SecCapturedBlurContainerViewModel", "Skip while BouncerShowing captured blur case"));
            } else {
                this.L$0 = null;
                this.L$1 = null;
                this.label = 2;
            }
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        FlowCollector flowCollector2 = (FlowCollector) this.L$0;
        blurType = (SecPanelBlurBinding.BlurType) this.L$1;
        if (blurType == SecPanelBlurBinding.BlurType.QUICK_PANEL) {
            ReadonlyStateFlow readonlyStateFlow = this.$powerInteractor.detailedWakefulness;
            this.L$0 = flowCollector2;
            this.L$1 = blurType;
            this.label = 1;
            Object objFirst = FlowKt.first(readonlyStateFlow, this);
            if (objFirst != coroutineSingletons) {
                flowCollector = flowCollector2;
                obj = objFirst;
                if (((WakefulnessModel) obj).internalWakefulnessState != WakefulnessState.STARTING_TO_WAKE) {
                }
                return Unit.INSTANCE;
            }
        } else {
            this.L$0 = null;
            this.label = 3;
        }
        return coroutineSingletons;
    }
}
