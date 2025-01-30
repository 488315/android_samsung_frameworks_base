package com.android.systemui.keyguard.p009ui.binder;

import android.text.TextUtils;
import android.util.MathUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.KeyguardConstants$KeyguardDismissActionType;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardSecSecurityContainerController;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticLambda5;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.biometrics.SideFpsController;
import com.android.systemui.biometrics.SideFpsUiRequestSource;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.data.BouncerViewImpl;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$filter$2;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$1;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor$special$$inlined$map$2;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.shared.model.BouncerShowMessageModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.lang.ref.WeakReference;
import java.util.Optional;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1", m277f = "KeyguardBouncerViewBinder.kt", m278l = {174}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardBouncerViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
    final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
    final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ KeyguardBouncerViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1", m277f = "KeyguardBouncerViewBinder.kt", m278l = {315}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1 */
    final class C17391 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardBouncerViewBinder$bind$delegate$1 $delegate;
        final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
        final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardBouncerViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$1", m277f = "KeyguardBouncerViewBinder.kt", m278l = {178}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$view = viewGroup;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$view, this.$securityContainerController, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.isShowing;
                    final ViewGroup viewGroup = this.$view;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            viewGroup.setVisibility(booleanValue ? 0 : 4);
                            final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = keyguardSecSecurityContainerController;
                            if (booleanValue) {
                                keyguardSecSecurityContainerController2.reinflateViewFlipper(new KeyguardSecurityViewFlipperController.OnViewInflatedCallback() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.1.1.1
                                    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
                                    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
                                        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController3 = KeyguardSecSecurityContainerController.this;
                                        keyguardSecSecurityContainerController3.getClass();
                                        keyguardSecSecurityContainerController3.showPrimarySecurityScreen();
                                        ViewMediatorCallback viewMediatorCallback = keyguardSecSecurityContainerController3.mViewMediatorCallback;
                                        CharSequence consumeCustomMessage = viewMediatorCallback.consumeCustomMessage();
                                        if (TextUtils.isEmpty(consumeCustomMessage)) {
                                            keyguardSecSecurityContainerController3.showPromptReason(viewMediatorCallback.getBouncerPromptReason());
                                        } else {
                                            keyguardSecSecurityContainerController3.showMessage(consumeCustomMessage, null, false);
                                        }
                                        if (!LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                                            ((KeyguardSecurityContainer) keyguardSecSecurityContainerController3.mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController.6
                                                public ViewTreeObserverOnPreDrawListenerC07666() {
                                                }

                                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                                public final boolean onPreDraw() {
                                                    ((KeyguardSecurityContainer) KeyguardSecurityContainerController.this.mView).getViewTreeObserver().removeOnPreDrawListener(this);
                                                    KeyguardSecurityContainerController.this.startAppearAnimation();
                                                    return true;
                                                }
                                            });
                                            ((KeyguardSecurityContainer) keyguardSecSecurityContainerController3.mView).requestLayout();
                                        } else if (!((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isBouncerOnFoldOpened() || keyguardSecSecurityContainerController3.mIsDisappearAnimation) {
                                            ((KeyguardSecurityContainer) keyguardSecSecurityContainerController3.mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController.6
                                                public ViewTreeObserverOnPreDrawListenerC07666() {
                                                }

                                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                                public final boolean onPreDraw() {
                                                    ((KeyguardSecurityContainer) KeyguardSecurityContainerController.this.mView).getViewTreeObserver().removeOnPreDrawListener(this);
                                                    KeyguardSecurityContainerController.this.startAppearAnimation();
                                                    return true;
                                                }
                                            });
                                            ((KeyguardSecurityContainer) keyguardSecSecurityContainerController3.mView).requestLayout();
                                        } else {
                                            keyguardSecSecurityContainerController3.getCurrentSecurityController(new KeyguardSecSecurityContainerController$$ExternalSyntheticLambda5());
                                        }
                                        keyguardSecSecurityContainerController3.onResume(1);
                                    }
                                });
                            } else {
                                KeyguardSecurityContainer keyguardSecurityContainer = (KeyguardSecurityContainer) keyguardSecSecurityContainerController2.mView;
                                keyguardSecurityContainer.setScaleX(1.0f);
                                keyguardSecurityContainer.setScaleY(1.0f);
                                keyguardSecSecurityContainerController2.mUpdateMonitor.setDismissActionType(KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT);
                                keyguardSecSecurityContainerController2.setOnDismissAction(null, null);
                                keyguardSecSecurityContainerController2.reset();
                                keyguardSecSecurityContainerController2.onPause();
                            }
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$10", m277f = "KeyguardBouncerViewBinder.kt", m278l = {268}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass10> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass10(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.bouncerShowMessage;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.10.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj2;
                            KeyguardSecSecurityContainerController.this.showMessage(bouncerShowMessageModel.message, bouncerShowMessageModel.colorStateList, false);
                            ((KeyguardBouncerRepositoryImpl) keyguardBouncerViewModel.interactor.repository)._showMessage.setValue(null);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$11", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setForceSingleView}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$11, reason: invalid class name */
        final class AnonymousClass11 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass11(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass11> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass11(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass11) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.keyguardAuthenticated;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.11.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            KeyguardSecSecurityContainerController.this.mKeyguardSecurityCallback.finish(KeyguardUpdateMonitor.getCurrentUser(), booleanValue);
                            ((KeyguardBouncerRepositoryImpl) keyguardBouncerViewModel.interactor.repository)._keyguardAuthenticated.setValue(null);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$12", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getBsohUnbiased}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$12, reason: invalid class name */
        final class AnonymousClass12 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass12(KeyguardBouncerViewModel keyguardBouncerViewModel, ViewGroup viewGroup, Continuation<? super AnonymousClass12> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass12(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass12) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    final ViewGroup viewGroup = this.$view;
                    final Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.12.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return Integer.valueOf(viewGroup.getSystemUiVisibility());
                        }
                    };
                    final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = keyguardBouncerViewModel.interactor.isBackButtonEnabled;
                    Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ Function0 $systemUiVisibility$inlined;
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2", m277f = "KeyguardBouncerViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel$observeOnIsBackButtonEnabled$$inlined$map$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector, Function0 function0) {
                                this.$this_unsafeFlow = flowCollector;
                                this.$systemUiVisibility$inlined = function0;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass1.label;
                                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                        anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                        Object obj2 = anonymousClass1.result;
                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            boolean booleanValue = ((Boolean) obj).booleanValue();
                                            int intValue = ((Number) this.$systemUiVisibility$inlined.invoke()).intValue();
                                            Integer num = new Integer(booleanValue ? (-4194305) & intValue : 4194304 | intValue);
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function0), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final ViewGroup viewGroup2 = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.12.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            viewGroup2.setSystemUiVisibility(((Number) obj2).intValue());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$13", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_readFile}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$13, reason: invalid class name */
        final class AnonymousClass13 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass13(KeyguardBouncerViewModel keyguardBouncerViewModel, Continuation<? super AnonymousClass13> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass13(this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass13) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    ChannelLimitedFlowMerge channelLimitedFlowMerge = keyguardBouncerViewModel.shouldUpdateSideFps;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.13.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardBouncerViewModel.this.interactor.updateSideFpsVisibility();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelLimitedFlowMerge.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$14", m277f = "KeyguardBouncerViewBinder.kt", m278l = {301}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$14, reason: invalid class name */
        final class AnonymousClass14 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass14(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass14> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass14(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass14) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.sideFpsShowing;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.14.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            Optional optional = KeyguardSecSecurityContainerController.this.mSideFpsController;
                            if (optional.isPresent()) {
                                if (booleanValue) {
                                    ((SideFpsController) optional.get()).show(SideFpsUiRequestSource.PRIMARY_BOUNCER, 4);
                                } else {
                                    ((SideFpsController) optional.get()).hide(SideFpsUiRequestSource.PRIMARY_BOUNCER);
                                }
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
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$15", m277f = "KeyguardBouncerViewBinder.kt", m278l = {308}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$15, reason: invalid class name */
        final class AnonymousClass15 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass15(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass15> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass15(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass15) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.isInflated;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.15.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            if (((Boolean) obj2).booleanValue()) {
                                KeyguardSecSecurityContainerController.this.reinflateViewFlipper(null);
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
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$2", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberAnswerMode}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    PrimaryBouncerInteractor$special$$inlined$map$1 primaryBouncerInteractor$special$$inlined$map$1 = this.$viewModel.startingToHide;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecSecurityContainerController.this.onStartingToHide();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (primaryBouncerInteractor$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$3", m277f = "KeyguardBouncerViewBinder.kt", m278l = {222}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = this.$viewModel.startDisappearAnimation;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardSecSecurityContainerController.this.startDisappearAnimation((Runnable) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$4", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getForceAutoShutDownState}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.bouncerExpansionAmount;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float floatValue = ((Number) obj2).floatValue();
                            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = KeyguardSecSecurityContainerController.this;
                            keyguardSecSecurityContainerController2.getClass();
                            float showBouncerProgress = BouncerPanelExpansionCalculator.showBouncerProgress(floatValue);
                            ((KeyguardSecurityContainer) keyguardSecSecurityContainerController2.mView).setAlpha(MathUtils.constrain(1.0f - showBouncerProgress, 0.0f, 1.0f));
                            ((KeyguardSecurityContainer) keyguardSecSecurityContainerController2.mView).setTranslationY(showBouncerProgress * keyguardSecSecurityContainerController2.mTranslationY);
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
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$5", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel $primaryBouncerToGoneTransitionViewModel;
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass5> continuation) {
                super(2, continuation);
                this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$primaryBouncerToGoneTransitionViewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = this.$primaryBouncerToGoneTransitionViewModel.bouncerAlpha;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((KeyguardSecurityContainer) KeyguardSecSecurityContainerController.this.mView).setAlpha(((Number) obj2).floatValue());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, ViewGroup viewGroup, Continuation<? super AnonymousClass6> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$viewModel, this.$securityContainerController, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final ReadonlyStateFlow readonlyStateFlow = this.$viewModel.bouncerExpansionAmount;
                    Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$6$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                Object L$1;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass1.label;
                                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                        anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                        Object obj2 = anonymousClass1.result;
                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            if (((Number) obj).floatValue() == 0.0f) {
                                                anonymousClass1.label = 1;
                                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.6.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Number) obj2).floatValue();
                            KeyguardSecSecurityContainerController keyguardSecSecurityContainerController2 = KeyguardSecSecurityContainerController.this;
                            keyguardSecSecurityContainerController2.onResume(1);
                            viewGroup.announceForAccessibility(keyguardSecSecurityContainerController2.getTitle());
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7", m277f = "KeyguardBouncerViewBinder.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass7> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    PrimaryBouncerInteractor$special$$inlined$map$2 primaryBouncerInteractor$special$$inlined$map$2 = this.$viewModel.isInteractable;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).booleanValue();
                            ((KeyguardSecurityContainer) KeyguardSecSecurityContainerController.this.mView).getClass();
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (primaryBouncerInteractor$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$8", m277f = "KeyguardBouncerViewBinder.kt", m278l = {255}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass8> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass8(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ReadonlyStateFlow readonlyStateFlow = this.$viewModel.keyguardPosition;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.8.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((KeyguardSecurityContainer) KeyguardSecSecurityContainerController.this.mView).mViewMode.updatePositionByTouchX(((Number) obj2).floatValue());
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
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9", m277f = "KeyguardBouncerViewBinder.kt", m278l = {261}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$1$1$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardSecSecurityContainerController $securityContainerController;
            final /* synthetic */ KeyguardBouncerViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, Continuation<? super AnonymousClass9> continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBouncerViewModel;
                this.$securityContainerController = keyguardSecSecurityContainerController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.$viewModel, this.$securityContainerController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    PrimaryBouncerInteractor$special$$inlined$filter$2 primaryBouncerInteractor$special$$inlined$filter$2 = keyguardBouncerViewModel.updateResources;
                    final KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.$securityContainerController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder.bind.1.1.9.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).booleanValue();
                            KeyguardSecSecurityContainerController.this.updateResources();
                            ((KeyguardBouncerRepositoryImpl) keyguardBouncerViewModel.interactor.repository)._resourceUpdateRequests.setValue(Boolean.FALSE);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (primaryBouncerInteractor$special$$inlined$filter$2.collect(flowCollector, this) == coroutineSingletons) {
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
        public C17391(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, Continuation<? super C17391> continuation) {
            super(2, continuation);
            this.$viewModel = keyguardBouncerViewModel;
            this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
            this.$view = viewGroup;
            this.$securityContainerController = keyguardSecSecurityContainerController;
            this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C17391 c17391 = new C17391(this.$viewModel, this.$delegate, this.$view, this.$securityContainerController, this.$primaryBouncerToGoneTransitionViewModel, continuation);
            c17391.L$0 = obj;
            return c17391;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C17391) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineContext coroutineContext;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    KeyguardBouncerViewModel keyguardBouncerViewModel = this.$viewModel;
                    KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1 = this.$delegate;
                    BouncerViewImpl bouncerViewImpl = (BouncerViewImpl) keyguardBouncerViewModel.view;
                    bouncerViewImpl.getClass();
                    bouncerViewImpl._delegate = new WeakReference(keyguardBouncerViewBinder$bind$delegate$1);
                    PrimaryBouncerInteractor primaryBouncerInteractor = keyguardBouncerViewModel.interactor;
                    if (primaryBouncerInteractor.pendingBouncerViewDelegate) {
                        primaryBouncerInteractor.show(true);
                        primaryBouncerInteractor.pendingBouncerViewDelegate = false;
                    }
                    if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                        DefaultScheduler defaultScheduler = Dispatchers.Default;
                        coroutineContext = MainDispatcherLoader.dispatcher.getImmediate();
                    } else {
                        coroutineContext = EmptyCoroutineContext.INSTANCE;
                    }
                    BuildersKt.launch$default(coroutineScope, coroutineContext, null, new AnonymousClass1(this.$viewModel, this.$view, this.$securityContainerController, null), 2);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, MainDispatcherLoader.dispatcher.getImmediate(), null, new AnonymousClass3(this.$viewModel, this.$securityContainerController, null), 2);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$primaryBouncerToGoneTransitionViewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$securityContainerController, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass8(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass9(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass10(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass11(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass12(this.$viewModel, this.$view, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass13(this.$viewModel, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass14(this.$viewModel, this.$securityContainerController, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass15(this.$viewModel, this.$securityContainerController, null), 3);
                    this.label = 1;
                    if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            } catch (Throwable th) {
                KeyguardBouncerViewModel keyguardBouncerViewModel2 = this.$viewModel;
                BouncerViewImpl bouncerViewImpl2 = (BouncerViewImpl) keyguardBouncerViewModel2.view;
                bouncerViewImpl2.getClass();
                bouncerViewImpl2._delegate = new WeakReference(null);
                PrimaryBouncerInteractor primaryBouncerInteractor2 = keyguardBouncerViewModel2.interactor;
                if (primaryBouncerInteractor2.pendingBouncerViewDelegate) {
                    primaryBouncerInteractor2.show(true);
                    primaryBouncerInteractor2.pendingBouncerViewDelegate = false;
                }
                throw th;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBouncerViewBinder$bind$1(KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerViewBinder$bind$delegate$1 keyguardBouncerViewBinder$bind$delegate$1, ViewGroup viewGroup, KeyguardSecSecurityContainerController keyguardSecSecurityContainerController, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, Continuation<? super KeyguardBouncerViewBinder$bind$1> continuation) {
        super(3, continuation);
        this.$viewModel = keyguardBouncerViewModel;
        this.$delegate = keyguardBouncerViewBinder$bind$delegate$1;
        this.$view = viewGroup;
        this.$securityContainerController = keyguardSecSecurityContainerController;
        this.$primaryBouncerToGoneTransitionViewModel = primaryBouncerToGoneTransitionViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBouncerViewBinder$bind$1 keyguardBouncerViewBinder$bind$1 = new KeyguardBouncerViewBinder$bind$1(this.$viewModel, this.$delegate, this.$view, this.$securityContainerController, this.$primaryBouncerToGoneTransitionViewModel, (Continuation) obj3);
        keyguardBouncerViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return keyguardBouncerViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            C17391 c17391 = new C17391(this.$viewModel, this.$delegate, this.$view, this.$securityContainerController, this.$primaryBouncerToGoneTransitionViewModel, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c17391, this) == coroutineSingletons) {
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
