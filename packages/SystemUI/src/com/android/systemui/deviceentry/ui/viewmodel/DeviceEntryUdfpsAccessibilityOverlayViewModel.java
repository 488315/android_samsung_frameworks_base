package com.android.systemui.deviceentry.ui.viewmodel;

import com.android.systemui.accessibility.domain.interactor.AccessibilityInteractor;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class DeviceEntryUdfpsAccessibilityOverlayViewModel extends UdfpsAccessibilityOverlayViewModel {
    public final DeviceEntryForegroundViewModel deviceEntryFgIconViewModel;
    public final DeviceEntryIconViewModel deviceEntryIconViewModel;

    /* renamed from: com.android.systemui.deviceentry.ui.viewmodel.DeviceEntryUdfpsAccessibilityOverlayViewModel$isVisibleWhenTouchExplorationEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        /* synthetic */ float F$0;
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            float fFloatValue = ((Number) obj2).floatValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (DeviceEntryForegroundViewModel.ForegroundIconViewModel) obj;
            anonymousClass1.F$0 = fFloatValue;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DeviceEntryForegroundViewModel.ForegroundIconViewModel foregroundIconViewModel = (DeviceEntryForegroundViewModel.ForegroundIconViewModel) this.L$0;
            return Boolean.valueOf(foregroundIconViewModel.type == DeviceEntryIconView.IconType.FINGERPRINT && !foregroundIconViewModel.useAodVariant && this.F$0 == 1.0f);
        }
    }

    public DeviceEntryUdfpsAccessibilityOverlayViewModel(UdfpsOverlayInteractor udfpsOverlayInteractor, AccessibilityInteractor accessibilityInteractor, DeviceEntryIconViewModel deviceEntryIconViewModel, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel) {
        super(udfpsOverlayInteractor, accessibilityInteractor);
        this.deviceEntryIconViewModel = deviceEntryIconViewModel;
        this.deviceEntryFgIconViewModel = deviceEntryForegroundViewModel;
    }

    @Override // com.android.systemui.deviceentry.ui.viewmodel.UdfpsAccessibilityOverlayViewModel
    public final Flow isVisibleWhenTouchExplorationEnabled() {
        return new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.deviceEntryFgIconViewModel.viewModel, this.deviceEntryIconViewModel.deviceEntryViewAlpha, new AnonymousClass1(null));
    }
}
