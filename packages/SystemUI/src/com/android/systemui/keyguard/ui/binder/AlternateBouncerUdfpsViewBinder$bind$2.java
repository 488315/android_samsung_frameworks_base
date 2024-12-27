package com.android.systemui.keyguard.ui.binder;

import android.content.res.ColorStateList;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

final class AlternateBouncerUdfpsViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ ImageView $fgIconView;
    final /* synthetic */ DeviceEntryIconView $view;
    final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ImageView $fgIconView;
        final /* synthetic */ DeviceEntryIconView $view;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
        int label;

        public AnonymousClass1(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = alternateBouncerUdfpsIconViewModel;
            this.$fgIconView = imageView;
            this.$view = deviceEntryIconView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$fgIconView, this.$view, continuation);
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
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.fgViewModel;
                final ImageView imageView = this.$fgIconView;
                final DeviceEntryIconView deviceEntryIconView = this.$view;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        DeviceEntryForegroundViewModel.ForegroundIconViewModel foregroundIconViewModel = (DeviceEntryForegroundViewModel.ForegroundIconViewModel) obj2;
                        ImageView imageView2 = imageView;
                        DeviceEntryIconView.IconType iconType = foregroundIconViewModel.type;
                        deviceEntryIconView.getClass();
                        imageView2.setImageState(DeviceEntryIconView.getIconState(iconType, foregroundIconViewModel.useAodVariant), false);
                        imageView.setImageTintList(ColorStateList.valueOf(foregroundIconViewModel.tint));
                        ImageView imageView3 = imageView;
                        int i2 = foregroundIconViewModel.padding;
                        imageView3.setPadding(i2, i2, i2, i2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

    public AlternateBouncerUdfpsViewBinder$bind$2(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = alternateBouncerUdfpsIconViewModel;
        this.$fgIconView = imageView;
        this.$view = deviceEntryIconView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AlternateBouncerUdfpsViewBinder$bind$2 alternateBouncerUdfpsViewBinder$bind$2 = new AlternateBouncerUdfpsViewBinder$bind$2(this.$viewModel, this.$fgIconView, this.$view, (Continuation) obj3);
        alternateBouncerUdfpsViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return alternateBouncerUdfpsViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$fgIconView, this.$view, null);
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
