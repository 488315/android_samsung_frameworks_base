package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryBackgroundViewModel;
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

final class DeviceEntryIconViewBinder$bind$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ ImageView $bgView;
    final /* synthetic */ DeviceEntryBackgroundViewModel $bgViewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$4$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ImageView $bgView;
        final /* synthetic */ DeviceEntryBackgroundViewModel $bgViewModel;
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel, ImageView imageView, Continuation continuation) {
            super(2, continuation);
            this.$bgViewModel = deviceEntryBackgroundViewModel;
            this.$bgView = imageView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$bgViewModel, this.$bgView, continuation);
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
            DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel = this.$bgViewModel;
            ImageView imageView = this.$bgView;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$4$1$invokeSuspend$$inlined$launch$default$1("DeviceEntryIconViewBinder#bgViewModel.alpha", null, deviceEntryBackgroundViewModel, imageView), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new DeviceEntryIconViewBinder$bind$4$1$invokeSuspend$$inlined$launch$default$2("DeviceEntryIconViewBinder#bgViewModel.color", null, this.$bgViewModel, this.$bgView), 2);
            return Unit.INSTANCE;
        }
    }

    public DeviceEntryIconViewBinder$bind$4(DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel, ImageView imageView, Continuation continuation) {
        super(3, continuation);
        this.$bgViewModel = deviceEntryBackgroundViewModel;
        this.$bgView = imageView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryIconViewBinder$bind$4 deviceEntryIconViewBinder$bind$4 = new DeviceEntryIconViewBinder$bind$4(this.$bgViewModel, this.$bgView, (Continuation) obj3);
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
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$bgViewModel, this.$bgView, null);
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
