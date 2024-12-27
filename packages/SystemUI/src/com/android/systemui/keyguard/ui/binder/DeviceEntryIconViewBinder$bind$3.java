package com.android.systemui.keyguard.ui.binder;

import android.util.StateSet;
import android.widget.ImageView;
import androidx.compose.ui.graphics.Color;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
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
/* loaded from: classes2.dex */
final class DeviceEntryIconViewBinder$bind$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ ImageView $fgIconView;
    final /* synthetic */ DeviceEntryForegroundViewModel $fgViewModel;
    final /* synthetic */ Color $overrideColor;
    final /* synthetic */ DeviceEntryIconView $view;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ImageView $fgIconView;
        final /* synthetic */ DeviceEntryForegroundViewModel $fgViewModel;
        final /* synthetic */ Color $overrideColor;
        final /* synthetic */ DeviceEntryIconView $view;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ImageView imageView, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, DeviceEntryIconView deviceEntryIconView, Color color, Continuation continuation) {
            super(2, continuation);
            this.$fgIconView = imageView;
            this.$fgViewModel = deviceEntryForegroundViewModel;
            this.$view = deviceEntryIconView;
            this.$overrideColor = color;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$fgIconView, this.$fgViewModel, this.$view, this.$overrideColor, continuation);
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
            this.$fgIconView.setImageState(StateSet.NOTHING, false);
            BuildersKt.launch$default(coroutineScope, EmptyCoroutineContext.INSTANCE, null, new DeviceEntryIconViewBinder$bind$3$1$invokeSuspend$$inlined$launch$default$1("DeviceEntryIconViewBinder#fpIconView.viewModel", null, this.$fgViewModel, this.$fgIconView, this.$view, this.$overrideColor), 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryIconViewBinder$bind$3(ImageView imageView, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, DeviceEntryIconView deviceEntryIconView, Color color, Continuation continuation) {
        super(3, continuation);
        this.$fgIconView = imageView;
        this.$fgViewModel = deviceEntryForegroundViewModel;
        this.$view = deviceEntryIconView;
        this.$overrideColor = color;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryIconViewBinder$bind$3 deviceEntryIconViewBinder$bind$3 = new DeviceEntryIconViewBinder$bind$3(this.$fgIconView, this.$fgViewModel, this.$view, this.$overrideColor, (Continuation) obj3);
        deviceEntryIconViewBinder$bind$3.L$0 = (LifecycleOwner) obj;
        return deviceEntryIconViewBinder$bind$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$fgIconView, this.$fgViewModel, this.$view, this.$overrideColor, null);
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
