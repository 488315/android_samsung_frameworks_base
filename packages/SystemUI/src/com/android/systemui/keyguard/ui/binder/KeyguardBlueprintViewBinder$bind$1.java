package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
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
final class KeyguardBlueprintViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardClockViewModel $clockViewModel;
    final /* synthetic */ ConstraintLayout $constraintLayout;
    final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
    final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardClockViewModel $clockViewModel;
        final /* synthetic */ ConstraintLayout $constraintLayout;
        final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
        final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, ConstraintLayout constraintLayout, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardBlueprintViewModel;
            this.$clockViewModel = keyguardClockViewModel;
            this.$smartspaceViewModel = keyguardSmartspaceViewModel;
            this.$constraintLayout = constraintLayout;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$clockViewModel, this.$smartspaceViewModel, this.$constraintLayout, continuation);
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
            KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.$viewModel;
            KeyguardClockViewModel keyguardClockViewModel = this.$clockViewModel;
            KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.$smartspaceViewModel;
            ConstraintLayout constraintLayout = this.$constraintLayout;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$1("KeyguardBlueprintViewBinder#viewModel.blueprint", null, keyguardBlueprintViewModel, keyguardClockViewModel, keyguardSmartspaceViewModel, constraintLayout), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBlueprintViewBinder$bind$1$1$invokeSuspend$$inlined$launch$default$2("KeyguardBlueprintViewBinder#viewModel.refreshTransition", null, this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel), 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBlueprintViewBinder$bind$1(KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, ConstraintLayout constraintLayout, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardBlueprintViewModel;
        this.$clockViewModel = keyguardClockViewModel;
        this.$smartspaceViewModel = keyguardSmartspaceViewModel;
        this.$constraintLayout = constraintLayout;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBlueprintViewBinder$bind$1 keyguardBlueprintViewBinder$bind$1 = new KeyguardBlueprintViewBinder$bind$1(this.$viewModel, this.$clockViewModel, this.$smartspaceViewModel, this.$constraintLayout, (Continuation) obj3);
        keyguardBlueprintViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return keyguardBlueprintViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$clockViewModel, this.$smartspaceViewModel, this.$constraintLayout, null);
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
