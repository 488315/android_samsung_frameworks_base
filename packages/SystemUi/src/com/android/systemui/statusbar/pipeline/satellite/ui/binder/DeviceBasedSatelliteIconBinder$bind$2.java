package com.android.systemui.statusbar.pipeline.satellite.ui.binder;

import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.common.ui.binder.IconViewBinder;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$bind$2", m277f = "DeviceBasedSatelliteIconBinder.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class DeviceBasedSatelliteIconBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ SingleBindableStatusBarIconView $view;
    final /* synthetic */ DeviceBasedSatelliteViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$bind$2$1", m277f = "DeviceBasedSatelliteIconBinder.kt", m278l = {38}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$bind$2$1 */
    final class C33421 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$withDefaultBinding;
        final /* synthetic */ SingleBindableStatusBarIconView $view;
        final /* synthetic */ DeviceBasedSatelliteViewModel $viewModel;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$bind$2$1$1", m277f = "DeviceBasedSatelliteIconBinder.kt", m278l = {39}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder$bind$2$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ SingleBindableStatusBarIconView $view;
            final /* synthetic */ DeviceBasedSatelliteViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = deviceBasedSatelliteViewModel;
                this.$view = singleBindableStatusBarIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$view, continuation);
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
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.icon;
                    final SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.binder.DeviceBasedSatelliteIconBinder.bind.2.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Icon icon = (Icon) obj2;
                            SingleBindableStatusBarIconView singleBindableStatusBarIconView2 = SingleBindableStatusBarIconView.this;
                            if (icon == null) {
                                ImageView imageView = singleBindableStatusBarIconView2.iconView;
                                if (imageView == null) {
                                    imageView = null;
                                }
                                imageView.setImageDrawable(null);
                            } else {
                                IconViewBinder iconViewBinder = IconViewBinder.INSTANCE;
                                ImageView imageView2 = singleBindableStatusBarIconView2.iconView;
                                ImageView imageView3 = imageView2 != null ? imageView2 : null;
                                iconViewBinder.getClass();
                                IconViewBinder.bind(icon, imageView3);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C33421(LifecycleOwner lifecycleOwner, DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation<? super C33421> continuation) {
            super(2, continuation);
            this.$$this$withDefaultBinding = lifecycleOwner;
            this.$viewModel = deviceBasedSatelliteViewModel;
            this.$view = singleBindableStatusBarIconView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C33421(this.$$this$withDefaultBinding, this.$viewModel, this.$view, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33421) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = this.$$this$withDefaultBinding;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, null);
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteIconBinder$bind$2(DeviceBasedSatelliteViewModel deviceBasedSatelliteViewModel, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation<? super DeviceBasedSatelliteIconBinder$bind$2> continuation) {
        super(3, continuation);
        this.$viewModel = deviceBasedSatelliteViewModel;
        this.$view = singleBindableStatusBarIconView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceBasedSatelliteIconBinder$bind$2 deviceBasedSatelliteIconBinder$bind$2 = new DeviceBasedSatelliteIconBinder$bind$2(this.$viewModel, this.$view, (Continuation) obj3);
        deviceBasedSatelliteIconBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return deviceBasedSatelliteIconBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new C33421(lifecycleOwner, this.$viewModel, this.$view, null), 3);
        return Unit.INSTANCE;
    }
}
