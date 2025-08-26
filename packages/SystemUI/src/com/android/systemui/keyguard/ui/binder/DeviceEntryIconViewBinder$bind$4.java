package com.android.systemui.keyguard.ui.binder;

import android.content.res.ColorStateList;
import android.util.Log;
import android.util.StateSet;
import android.widget.ImageView;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;

/* loaded from: classes2.dex */
final class DeviceEntryIconViewBinder$bind$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ ImageView $fgIconView;
    final /* synthetic */ DeviceEntryForegroundViewModel $fgViewModel;
    final /* synthetic */ Color $overrideColor;
    final /* synthetic */ DeviceEntryIconView $view;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$4$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ImageView $fgIconView;
        final /* synthetic */ DeviceEntryForegroundViewModel $fgViewModel;
        final /* synthetic */ Color $overrideColor;
        final /* synthetic */ DeviceEntryIconView $view;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$4$1$1, reason: invalid class name and collision with other inner class name */
        final class C02591 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $fgIconView;
            final /* synthetic */ DeviceEntryForegroundViewModel $fgViewModel;
            final /* synthetic */ Color $overrideColor;
            final /* synthetic */ DeviceEntryIconView $view;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02591(DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, ImageView imageView, Color color, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                super(2, continuation);
                this.$fgViewModel = deviceEntryForegroundViewModel;
                this.$fgIconView = imageView;
                this.$overrideColor = color;
                this.$view = deviceEntryIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02591(this.$fgViewModel, this.$fgIconView, this.$overrideColor, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02591) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 = this.$fgViewModel.viewModel;
                    final ImageView imageView = this.$fgIconView;
                    final Color color = this.$overrideColor;
                    final DeviceEntryIconView deviceEntryIconView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder.bind.4.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            DeviceEntryForegroundViewModel.ForegroundIconViewModel foregroundIconViewModel = (DeviceEntryForegroundViewModel.ForegroundIconViewModel) obj2;
                            Log.d("DeviceEntryIconViewBinder", "Updating device entry icon image state " + foregroundIconViewModel);
                            int contentDescriptionResId = foregroundIconViewModel.type.getContentDescriptionResId();
                            DeviceEntryIconView.IconType iconType = foregroundIconViewModel.type;
                            if (contentDescriptionResId != -1) {
                                ImageView imageView2 = imageView;
                                imageView2.setContentDescription(imageView2.getResources().getString(iconType.getContentDescriptionResId()));
                            }
                            ImageView imageView3 = imageView;
                            Color color2 = color;
                            imageView3.setImageTintList(ColorStateList.valueOf(color2 != null ? ColorKt.m469toArgb8_81llA(color2.value) : foregroundIconViewModel.tint));
                            ImageView imageView4 = imageView;
                            int i2 = foregroundIconViewModel.padding;
                            imageView4.setPadding(i2, i2, i2, i2);
                            ImageView imageView5 = imageView;
                            deviceEntryIconView.getClass();
                            imageView5.setImageState(DeviceEntryIconView.getIconState(iconType, foregroundIconViewModel.useAodVariant), false);
                            imageView.invalidate();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2.collect(flowCollector, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ImageView imageView, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, Color color, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
            super(2, continuation);
            this.$fgIconView = imageView;
            this.$fgViewModel = deviceEntryForegroundViewModel;
            this.$overrideColor = color;
            this.$view = deviceEntryIconView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$fgIconView, this.$fgViewModel, this.$overrideColor, this.$view, continuation);
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
            Log.d("DeviceEntryIconViewBinder", "Initializing device entry fgIconView");
            this.$fgIconView.setImageState(StateSet.NOTHING, false);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02591(this.$fgViewModel, this.$fgIconView, this.$overrideColor, this.$view, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewBinder$bind$4(ImageView imageView, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, Color color, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
        super(3, continuation);
        this.$fgIconView = imageView;
        this.$fgViewModel = deviceEntryForegroundViewModel;
        this.$overrideColor = color;
        this.$view = deviceEntryIconView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryIconViewBinder$bind$4 deviceEntryIconViewBinder$bind$4 = new DeviceEntryIconViewBinder$bind$4(this.$fgIconView, this.$fgViewModel, this.$overrideColor, this.$view, (Continuation) obj3);
        deviceEntryIconViewBinder$bind$4.L$0 = (LifecycleOwner) obj;
        return deviceEntryIconViewBinder$bind$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$fgIconView, this.$fgViewModel, this.$overrideColor, this.$view, null);
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
