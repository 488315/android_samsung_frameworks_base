package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.common.ui.view.LongPressHandlingView;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceEntryIconViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ CoroutineScope $applicationScope;
    final /* synthetic */ ImageView $bgView;
    final /* synthetic */ LongPressHandlingView $longPressHandlingView;
    final /* synthetic */ VibratorHelper $vibratorHelper;
    final /* synthetic */ DeviceEntryIconView $view;
    final /* synthetic */ DeviceEntryIconViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ CoroutineScope $applicationScope;
        final /* synthetic */ ImageView $bgView;
        final /* synthetic */ LongPressHandlingView $longPressHandlingView;
        final /* synthetic */ VibratorHelper $vibratorHelper;
        final /* synthetic */ DeviceEntryIconView $view;
        final /* synthetic */ DeviceEntryIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DeviceEntryIconViewModel deviceEntryIconViewModel, LongPressHandlingView longPressHandlingView, DeviceEntryIconView deviceEntryIconView, VibratorHelper vibratorHelper, CoroutineScope coroutineScope, ImageView imageView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = deviceEntryIconViewModel;
            this.$longPressHandlingView = longPressHandlingView;
            this.$view = deviceEntryIconView;
            this.$vibratorHelper = vibratorHelper;
            this.$applicationScope = coroutineScope;
            this.$bgView = imageView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$longPressHandlingView, this.$view, this.$vibratorHelper, this.$applicationScope, this.$bgView, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            DeviceEntryIconViewModel deviceEntryIconViewModel = this.$viewModel;
            LongPressHandlingView longPressHandlingView = this.$longPressHandlingView;
            DeviceEntryIconView deviceEntryIconView = this.$view;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$1("DeviceEntryIconViewBinder#viewModel.isVisible", null, deviceEntryIconViewModel, longPressHandlingView, deviceEntryIconView), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$2("DeviceEntryIconViewBinder#viewModel.isLongPressEnabled", null, this.$viewModel, this.$longPressHandlingView), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$3("DeviceEntryIconViewBinder#viewModel.isUdfpsSupported", null, this.$viewModel, this.$longPressHandlingView, this.$view), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$4("DeviceEntryIconViewBinder#viewModel.accessibilityDelegateHint", null, this.$viewModel, this.$view, this.$vibratorHelper, this.$applicationScope), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$5("DeviceEntryIconViewBinder#viewModel.useBackgroundProtection", null, this.$viewModel, this.$bgView), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$6("DeviceEntryIconViewBinder#viewModel.burnInOffsets", null, this.$viewModel, this.$view), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$2$1$invokeSuspend$$inlined$launch$default$7("DeviceEntryIconViewBinder#viewModel.deviceEntryViewAlpha", null, this.$viewModel, this.$view), 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewBinder$bind$2(DeviceEntryIconViewModel deviceEntryIconViewModel, LongPressHandlingView longPressHandlingView, DeviceEntryIconView deviceEntryIconView, VibratorHelper vibratorHelper, CoroutineScope coroutineScope, ImageView imageView, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = deviceEntryIconViewModel;
        this.$longPressHandlingView = longPressHandlingView;
        this.$view = deviceEntryIconView;
        this.$vibratorHelper = vibratorHelper;
        this.$applicationScope = coroutineScope;
        this.$bgView = imageView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryIconViewBinder$bind$2 deviceEntryIconViewBinder$bind$2 = new DeviceEntryIconViewBinder$bind$2(this.$viewModel, this.$longPressHandlingView, this.$view, this.$vibratorHelper, this.$applicationScope, this.$bgView, (Continuation) obj3);
        deviceEntryIconViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return deviceEntryIconViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$longPressHandlingView, this.$view, this.$vibratorHelper, this.$applicationScope, this.$bgView, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
