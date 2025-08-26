package com.android.systemui.biometrics.ui.binder;

import android.content.res.Resources;
import android.util.Log;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.widget.LottieColorUtils;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Quint;
import com.android.systemui.util.kotlin.Utils;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes.dex */
final class PromptIconViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ LottieAnimationView $iconView;
    final /* synthetic */ PromptIconViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LottieAnimationView $iconView;
        final /* synthetic */ PromptIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00951 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptIconViewModel $viewModel;
            int label;

            /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$1$3, reason: invalid class name */
            final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function5 {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                public AnonymousClass3() {
                    super(5, Quad.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function5
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    Boolean bool = (Boolean) obj2;
                    bool.booleanValue();
                    Boolean bool2 = (Boolean) obj3;
                    bool2.booleanValue();
                    Boolean bool3 = (Boolean) obj4;
                    bool3.booleanValue();
                    return new Quad((PromptIconViewModel.AuthType) obj, bool, bool2, bool3);
                }
            }

            /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$1$4, reason: invalid class name */
            final /* synthetic */ class AnonymousClass4 extends AdaptedFunctionReference implements Function3 {
                public AnonymousClass4(Object obj) {
                    super(3, obj, Utils.Companion.class, "toQuint", "toQuint(Ljava/lang/Object;Lcom/android/systemui/util/kotlin/Quad;)Lcom/android/systemui/util/kotlin/Quint;", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    int iIntValue = ((Number) obj).intValue();
                    return ((Utils.Companion) this.receiver).toQuint(new Integer(iIntValue), (Quad) obj2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00951(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptIconViewModel;
                this.$iconView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00951(this.$viewModel, this.$iconView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00951) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    PromptIconViewModel promptIconViewModel = this.$viewModel;
                    Flow flowSample = FlowKt.sample(promptIconViewModel.iconAsset, kotlinx.coroutines.flow.FlowKt.combine(promptIconViewModel.activeAuthType, promptIconViewModel.shouldAnimateIconView, promptIconViewModel.shouldLoopIconView, promptIconViewModel.showingError, AnonymousClass3.INSTANCE), new AnonymousClass4(Utils.Companion));
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    final PromptIconViewModel promptIconViewModel2 = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder.bind.1.1.1.5
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                            final String strM;
                            Quint quint = (Quint) obj2;
                            final int iIntValue = ((Number) quint.component1()).intValue();
                            final PromptIconViewModel.AuthType authType = (PromptIconViewModel.AuthType) quint.component2();
                            boolean zBooleanValue = ((Boolean) quint.component3()).booleanValue();
                            boolean zBooleanValue2 = ((Boolean) quint.component4()).booleanValue();
                            Boolean bool = (Boolean) quint.component5();
                            bool.getClass();
                            if (iIntValue != -1) {
                                LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                                List list = PromptIconViewBinderKt.sfpsFpToErrorAssets;
                                try {
                                    strM = lottieAnimationView2.getContext().getResources().getResourceEntryName(iIntValue);
                                } catch (Resources.NotFoundException unused) {
                                    strM = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(iIntValue, "Asset ", " not found");
                                }
                                final String str = "iconAsset";
                                lottieAnimationView2.setFailureListener(new LottieListener() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinderKt$setFailureListener$1
                                    @Override // com.airbnb.lottie.LottieListener
                                    public final void onResult(Object obj3) {
                                        Log.d("PromptIconViewBinder", "Collecting " + str + " | activeAuthType = " + authType + " | Invalid resource id: " + iIntValue + ", name " + strM, (Throwable) obj3);
                                    }
                                });
                                lottieAnimationView2.pauseAnimation();
                                lottieAnimationView2.setAnimation(iIntValue);
                                if (PromptIconViewBinderKt.sfpsFpToErrorAssets.contains(Integer.valueOf(iIntValue)) || PromptIconViewBinderKt.sfpsFpToUnlockAssets.contains(Integer.valueOf(iIntValue)) || PromptIconViewBinderKt.sfpsFpToSuccessAssets.contains(Integer.valueOf(iIntValue))) {
                                    lottieAnimationView2.setMinFrame(158);
                                } else {
                                    lottieAnimationView2.setFrame(0);
                                }
                                if (zBooleanValue) {
                                    lottieAnimationView2.loop(zBooleanValue2);
                                    lottieAnimationView2.playAnimation();
                                }
                                LottieColorUtils.applyDynamicColors(lottieAnimationView2.getContext(), lottieAnimationView2);
                                promptIconViewModel2._previousIconWasError.updateState(null, bool);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowSample.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptIconViewModel;
                this.$iconView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$iconView, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.iconViewRotation;
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            lottieAnimationView.setRotation(((Number) obj2).floatValue());
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

        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptIconViewModel;
                this.$iconView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$iconView, continuation);
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
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.contentDescriptionId;
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int iIntValue = ((Number) obj2).intValue();
                            if (iIntValue != -1) {
                                LottieAnimationView lottieAnimationView2 = lottieAnimationView;
                                lottieAnimationView2.setContentDescription(lottieAnimationView2.getContext().getString(iIntValue));
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptIconViewModel;
            this.$iconView = lottieAnimationView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$iconView, continuation);
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
            PromptIconViewModel promptIconViewModel = this.$viewModel;
            ((DisplayStateInteractorImpl) promptIconViewModel.displayStateInteractor).screenSizeFoldProvider.onConfigurationChange(this.$iconView.getContext().getResources().getConfiguration());
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C00951(this.$viewModel, this.$iconView, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$iconView, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$iconView, null), 7);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewBinder$bind$1(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = promptIconViewModel;
        this.$iconView = lottieAnimationView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewBinder$bind$1 promptIconViewBinder$bind$1 = new PromptIconViewBinder$bind$1(this.$viewModel, this.$iconView, (Continuation) obj3);
        promptIconViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return promptIconViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$iconView, null);
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
