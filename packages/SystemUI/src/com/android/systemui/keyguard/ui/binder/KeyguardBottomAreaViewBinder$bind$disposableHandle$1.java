package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.widget.ImageView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.animation.view.LaunchableLinearLayout;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

final class KeyguardBottomAreaViewBinder$bind$disposableHandle$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ ActivityStarter $activityStarter;
    final /* synthetic */ View $ambientIndicationArea;
    final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
    final /* synthetic */ ImageView $endButton;
    final /* synthetic */ FalsingManager $falsingManager;
    final /* synthetic */ Function1 $messageDisplayer;
    final /* synthetic */ View $overlayContainer;
    final /* synthetic */ LaunchableLinearLayout $settingsMenu;
    final /* synthetic */ ImageView $startButton;
    final /* synthetic */ VibratorHelper $vibratorHelper;
    final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardBottomAreaViewBinder this$0;

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ActivityStarter $activityStarter;
        final /* synthetic */ View $ambientIndicationArea;
        final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
        final /* synthetic */ ImageView $endButton;
        final /* synthetic */ FalsingManager $falsingManager;
        final /* synthetic */ Function1 $messageDisplayer;
        final /* synthetic */ View $overlayContainer;
        final /* synthetic */ LaunchableLinearLayout $settingsMenu;
        final /* synthetic */ ImageView $startButton;
        final /* synthetic */ VibratorHelper $vibratorHelper;
        final /* synthetic */ KeyguardBottomAreaViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ KeyguardBottomAreaViewBinder this$0;

        public AnonymousClass1(KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder, ActivityStarter activityStarter, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, ImageView imageView, FalsingManager falsingManager, Function1 function1, VibratorHelper vibratorHelper, ImageView imageView2, View view, View view2, MutableStateFlow mutableStateFlow, LaunchableLinearLayout launchableLinearLayout, Continuation continuation) {
            super(2, continuation);
            this.this$0 = keyguardBottomAreaViewBinder;
            this.$activityStarter = activityStarter;
            this.$viewModel = keyguardBottomAreaViewModel;
            this.$startButton = imageView;
            this.$falsingManager = falsingManager;
            this.$messageDisplayer = function1;
            this.$vibratorHelper = vibratorHelper;
            this.$endButton = imageView2;
            this.$overlayContainer = view;
            this.$ambientIndicationArea = view2;
            this.$configurationBasedDimensions = mutableStateFlow;
            this.$settingsMenu = launchableLinearLayout;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$activityStarter, this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, this.$endButton, this.$overlayContainer, this.$ambientIndicationArea, this.$configurationBasedDimensions, this.$settingsMenu, continuation);
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
            String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.startButton");
            KeyguardBottomAreaViewModel keyguardBottomAreaViewModel = this.$viewModel;
            KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder = this.this$0;
            ImageView imageView = this.$startButton;
            FalsingManager falsingManager = this.$falsingManager;
            Function1 function1 = this.$messageDisplayer;
            VibratorHelper vibratorHelper = this.$vibratorHelper;
            EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$1(m, null, keyguardBottomAreaViewModel, keyguardBottomAreaViewBinder, imageView, falsingManager, function1, vibratorHelper), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$2(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.endButton"), null, this.$viewModel, this.this$0, this.$endButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$3(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.isOverlayContainerVisible"), null, this.$viewModel, this.$overlayContainer), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$4(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.alpha"), null, this.$viewModel, this.$ambientIndicationArea), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$5(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#updateButtonAlpha"), null, this.this$0, this.$startButton, this.$viewModel), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$6(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#updateButtonAlpha"), null, this.this$0, this.$endButton, this.$viewModel), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$7(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.indicationAreaTranslationX"), null, this.$viewModel, this.$ambientIndicationArea), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$8(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.indicationAreaTranslationY"), null, this.$configurationBasedDimensions, this.$viewModel, this.$ambientIndicationArea), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$9(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#startButton.updateLayoutParams<ViewGroup"), null, this.$configurationBasedDimensions, this.$startButton, this.$endButton), 2);
            BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$10(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.settingsMenuViewModel"), null, this.$viewModel, this.this$0, this.$settingsMenu, this.$vibratorHelper), 2);
            if (this.$activityStarter != null) {
                BuildersKt.launch$default(coroutineScope, emptyCoroutineContext, null, new KeyguardBottomAreaViewBinder$bind$disposableHandle$1$1$invokeSuspend$$inlined$launch$default$11(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(this.this$0.TAG, "#viewModel.settingsMenuViewModel"), null, this.$viewModel, this.this$0, this.$activityStarter, this.$settingsMenu), 2);
            }
            return Unit.INSTANCE;
        }
    }

    public KeyguardBottomAreaViewBinder$bind$disposableHandle$1(KeyguardBottomAreaViewBinder keyguardBottomAreaViewBinder, ActivityStarter activityStarter, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, ImageView imageView, FalsingManager falsingManager, Function1 function1, VibratorHelper vibratorHelper, ImageView imageView2, View view, View view2, MutableStateFlow mutableStateFlow, LaunchableLinearLayout launchableLinearLayout, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardBottomAreaViewBinder;
        this.$activityStarter = activityStarter;
        this.$viewModel = keyguardBottomAreaViewModel;
        this.$startButton = imageView;
        this.$falsingManager = falsingManager;
        this.$messageDisplayer = function1;
        this.$vibratorHelper = vibratorHelper;
        this.$endButton = imageView2;
        this.$overlayContainer = view;
        this.$ambientIndicationArea = view2;
        this.$configurationBasedDimensions = mutableStateFlow;
        this.$settingsMenu = launchableLinearLayout;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBottomAreaViewBinder$bind$disposableHandle$1 keyguardBottomAreaViewBinder$bind$disposableHandle$1 = new KeyguardBottomAreaViewBinder$bind$disposableHandle$1(this.this$0, this.$activityStarter, this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, this.$endButton, this.$overlayContainer, this.$ambientIndicationArea, this.$configurationBasedDimensions, this.$settingsMenu, (Continuation) obj3);
        keyguardBottomAreaViewBinder$bind$disposableHandle$1.L$0 = (LifecycleOwner) obj;
        return keyguardBottomAreaViewBinder$bind$disposableHandle$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$activityStarter, this.$viewModel, this.$startButton, this.$falsingManager, this.$messageDisplayer, this.$vibratorHelper, this.$endButton, this.$overlayContainer, this.$ambientIndicationArea, this.$configurationBasedDimensions, this.$settingsMenu, null);
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
