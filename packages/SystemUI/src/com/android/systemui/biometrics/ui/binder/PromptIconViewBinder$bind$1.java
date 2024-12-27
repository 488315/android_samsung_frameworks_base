package com.android.systemui.biometrics.ui.binder;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.settingslib.widget.LottieColorUtils;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.Quad;
import com.android.systemui.util.kotlin.Utils;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptIconViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ LottieAnimationView $iconOverlayView;
    final /* synthetic */ LottieAnimationView $iconView;
    final /* synthetic */ Pair<Integer, Integer> $iconViewLayoutParamSizeOverride;
    final /* synthetic */ PromptViewModel $promptViewModel;
    final /* synthetic */ PromptIconViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LottieAnimationView $iconOverlayView;
        final /* synthetic */ LottieAnimationView $iconView;
        final /* synthetic */ Pair<Integer, Integer> $iconViewLayoutParamSizeOverride;
        final /* synthetic */ PromptViewModel $promptViewModel;
        final /* synthetic */ PromptIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ Ref$ObjectRef<AnimatedVectorDrawable> $faceIcon;
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptIconViewModel $viewModel;
            int label;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$2$2, reason: invalid class name and collision with other inner class name */
            final /* synthetic */ class C00462 extends AdaptedFunctionReference implements Function4 {
                public static final C00462 INSTANCE = new C00462();

                public C00462() {
                    super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    Boolean bool = (Boolean) obj2;
                    bool.booleanValue();
                    Boolean bool2 = (Boolean) obj3;
                    bool2.booleanValue();
                    return new Triple((PromptIconViewModel.AuthType) obj, bool, bool2);
                }
            }

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$2$3, reason: invalid class name */
            final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
                public AnonymousClass3(Object obj) {
                    super(3, obj, Utils.Companion.class, "toQuad", "toQuad(Ljava/lang/Object;Lkotlin/Triple;)Lcom/android/systemui/util/kotlin/Quad;", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    int intValue = ((Number) obj).intValue();
                    return ((Utils.Companion) this.receiver).toQuad((Utils.Companion) new Integer(intValue), (Triple) obj2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Ref$ObjectRef<AnimatedVectorDrawable> ref$ObjectRef, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptIconViewModel;
                this.$iconView = lottieAnimationView;
                this.$faceIcon = ref$ObjectRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$iconView, this.$faceIcon, continuation);
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
                    PromptIconViewModel promptIconViewModel = this.$viewModel;
                    Flow sample = FlowKt.sample(promptIconViewModel.iconAsset, kotlinx.coroutines.flow.FlowKt.combine(promptIconViewModel.activeAuthType, promptIconViewModel.shouldAnimateIconView, promptIconViewModel.showingError, C00462.INSTANCE), new AnonymousClass3(Utils.Companion));
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    final PromptIconViewModel promptIconViewModel2 = this.$viewModel;
                    final Ref$ObjectRef<AnimatedVectorDrawable> ref$ObjectRef = this.$faceIcon;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder.bind.1.1.2.4

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$2$4$WhenMappings */
                        public abstract /* synthetic */ class WhenMappings {
                            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                            static {
                                int[] iArr = new int[PromptIconViewModel.AuthType.values().length];
                                try {
                                    iArr[PromptIconViewModel.AuthType.Fingerprint.ordinal()] = 1;
                                } catch (NoSuchFieldError unused) {
                                }
                                try {
                                    iArr[PromptIconViewModel.AuthType.Coex.ordinal()] = 2;
                                } catch (NoSuchFieldError unused2) {
                                }
                                try {
                                    iArr[PromptIconViewModel.AuthType.Face.ordinal()] = 3;
                                } catch (NoSuchFieldError unused3) {
                                }
                                $EnumSwitchMapping$0 = iArr;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Quad quad = (Quad) obj2;
                            int intValue = ((Number) quad.component1()).intValue();
                            PromptIconViewModel.AuthType authType = (PromptIconViewModel.AuthType) quad.component2();
                            boolean booleanValue = ((Boolean) quad.component3()).booleanValue();
                            Boolean bool = (Boolean) quad.component4();
                            bool.getClass();
                            if (intValue != -1) {
                                int i2 = WhenMappings.$EnumSwitchMapping$0[authType.ordinal()];
                                Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                                LottieAnimationView lottieAnimationView2 = LottieAnimationView.this;
                                if (i2 == 1 || i2 == 2) {
                                    if (intValue == R.drawable.face_dialog_dark_to_error) {
                                        AnonymousClass1.access$invokeSuspend$updateXmlIconAsset(ref$ObjectRef2, lottieAnimationView2, intValue, booleanValue, authType);
                                    } else {
                                        Map map = PromptIconViewBinderKt.assetIdToString;
                                        lottieAnimationView2.setFailureListener(new PromptIconViewBinderKt$setIconFailureListener$1(authType, intValue));
                                        lottieAnimationView2.setAnimation(intValue);
                                        lottieAnimationView2.setFrame(0);
                                        if (booleanValue) {
                                            lottieAnimationView2.playAnimation();
                                        }
                                    }
                                } else if (i2 == 3) {
                                    if (intValue == R.raw.face_dialog_authenticating) {
                                        Map map2 = PromptIconViewBinderKt.assetIdToString;
                                        lottieAnimationView2.setFailureListener(new PromptIconViewBinderKt$setIconFailureListener$1(authType, intValue));
                                        lottieAnimationView2.setAnimation(intValue);
                                        lottieAnimationView2.setFrame(0);
                                        if (booleanValue) {
                                            lottieAnimationView2.playAnimation();
                                        }
                                    } else {
                                        AnonymousClass1.access$invokeSuspend$updateXmlIconAsset(ref$ObjectRef2, lottieAnimationView2, intValue, booleanValue, authType);
                                    }
                                }
                                LottieColorUtils.applyDynamicColors(lottieAnimationView2.getContext(), lottieAnimationView2);
                                promptIconViewModel2._previousIconWasError.updateState(null, bool);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (sample.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $iconOverlayView;
            final /* synthetic */ PromptIconViewModel $viewModel;
            int label;

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$3$2, reason: invalid class name */
            final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3 {
                public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

                public AnonymousClass2() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    Boolean bool2 = (Boolean) obj2;
                    bool2.booleanValue();
                    return new Pair(bool, bool2);
                }
            }

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$3$3, reason: invalid class name and collision with other inner class name */
            final /* synthetic */ class C00473 extends AdaptedFunctionReference implements Function3 {
                public C00473(Object obj) {
                    super(3, obj, Utils.Companion.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    int intValue = ((Number) obj).intValue();
                    return ((Utils.Companion) this.receiver).toTriple((Utils.Companion) new Integer(intValue), (Pair) obj2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptIconViewModel;
                this.$iconOverlayView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$iconOverlayView, continuation);
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
                    PromptIconViewModel promptIconViewModel = this.$viewModel;
                    Flow sample = FlowKt.sample(promptIconViewModel.iconOverlayAsset, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptIconViewModel.shouldAnimateIconOverlay, promptIconViewModel.showingError, AnonymousClass2.INSTANCE), new C00473(Utils.Companion));
                    final LottieAnimationView lottieAnimationView = this.$iconOverlayView;
                    final PromptIconViewModel promptIconViewModel2 = this.$viewModel;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder.bind.1.1.3.4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Triple triple = (Triple) obj2;
                            final int intValue = ((Number) triple.component1()).intValue();
                            boolean booleanValue = ((Boolean) triple.component2()).booleanValue();
                            Boolean bool = (Boolean) triple.component3();
                            bool.getClass();
                            if (intValue != -1) {
                                Map map = PromptIconViewBinderKt.assetIdToString;
                                LottieListener lottieListener = new LottieListener() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinderKt$setIconOverlayFailureListener$1
                                    @Override // com.airbnb.lottie.LottieListener
                                    public final void onResult(Object obj3) {
                                        int i2 = intValue;
                                        Log.d("PromptIconViewBinder", BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(i2, "Collecting iconOverlayAsset | Invalid resource id: ", ", name ", PromptIconViewBinderKt.access$getAssetNameFromId(i2)), (Throwable) obj3);
                                    }
                                };
                                LottieAnimationView lottieAnimationView2 = LottieAnimationView.this;
                                lottieAnimationView2.setFailureListener(lottieListener);
                                lottieAnimationView2.setAnimation(intValue);
                                lottieAnimationView2.setFrame(0);
                                LottieColorUtils.applyDynamicColors(lottieAnimationView2.getContext(), lottieAnimationView2);
                                if (booleanValue) {
                                    lottieAnimationView2.playAnimation();
                                }
                                promptIconViewModel2._previousIconOverlayWasError.updateState(null, bool);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (sample.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptIconViewModel;
                this.$iconView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$iconView, continuation);
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
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.shouldFlipIconView;
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder.bind.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            if (((Boolean) obj2).booleanValue()) {
                                LottieAnimationView.this.setRotation(180.0f);
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder$bind$1$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ LottieAnimationView $iconView;
            final /* synthetic */ PromptIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = promptIconViewModel;
                this.$iconView = lottieAnimationView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$iconView, continuation);
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
                    ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.contentDescriptionId;
                    final LottieAnimationView lottieAnimationView = this.$iconView;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.PromptIconViewBinder.bind.1.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            if (intValue != -1) {
                                LottieAnimationView lottieAnimationView2 = LottieAnimationView.this;
                                lottieAnimationView2.setContentDescription(lottieAnimationView2.getContext().getString(intValue));
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
        public AnonymousClass1(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Pair<Integer, Integer> pair, LottieAnimationView lottieAnimationView2, PromptViewModel promptViewModel, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = promptIconViewModel;
            this.$iconView = lottieAnimationView;
            this.$iconViewLayoutParamSizeOverride = pair;
            this.$iconOverlayView = lottieAnimationView2;
            this.$promptViewModel = promptViewModel;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v4, types: [T, android.graphics.drawable.AnimatedVectorDrawable, android.graphics.drawable.Drawable] */
        public static final void access$invokeSuspend$updateXmlIconAsset(Ref$ObjectRef ref$ObjectRef, LottieAnimationView lottieAnimationView, int i, boolean z, PromptIconViewModel.AuthType authType) {
            AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) ref$ObjectRef.element;
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable.stop();
            }
            ?? r0 = (AnimatedVectorDrawable) lottieAnimationView.getContext().getDrawable(i);
            ref$ObjectRef.element = r0;
            Map map = PromptIconViewBinderKt.assetIdToString;
            lottieAnimationView.setFailureListener(new PromptIconViewBinderKt$setIconFailureListener$1(authType, i));
            lottieAnimationView.setImageDrawable(r0);
            if (z) {
                r0.forceAnimationOnUI();
                r0.start();
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$iconView, this.$iconViewLayoutParamSizeOverride, this.$iconOverlayView, this.$promptViewModel, continuation);
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
            if (this.$iconViewLayoutParamSizeOverride != null) {
                this.$iconView.getLayoutParams().width = ((Number) this.$iconViewLayoutParamSizeOverride.getFirst()).intValue();
                this.$iconView.getLayoutParams().height = ((Number) this.$iconViewLayoutParamSizeOverride.getSecond()).intValue();
                this.$iconOverlayView.getLayoutParams().width = ((Number) this.$iconViewLayoutParamSizeOverride.getFirst()).intValue();
                this.$iconOverlayView.getLayoutParams().height = ((Number) this.$iconViewLayoutParamSizeOverride.getSecond()).intValue();
            }
            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            Flags.constraintBp();
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$iconView, ref$ObjectRef, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$iconOverlayView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$iconView, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$iconView, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewBinder$bind$1(PromptIconViewModel promptIconViewModel, LottieAnimationView lottieAnimationView, Pair<Integer, Integer> pair, LottieAnimationView lottieAnimationView2, PromptViewModel promptViewModel, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = promptIconViewModel;
        this.$iconView = lottieAnimationView;
        this.$iconViewLayoutParamSizeOverride = pair;
        this.$iconOverlayView = lottieAnimationView2;
        this.$promptViewModel = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewBinder$bind$1 promptIconViewBinder$bind$1 = new PromptIconViewBinder$bind$1(this.$viewModel, this.$iconView, this.$iconViewLayoutParamSizeOverride, this.$iconOverlayView, this.$promptViewModel, (Continuation) obj3);
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$iconView, this.$iconViewLayoutParamSizeOverride, this.$iconOverlayView, this.$promptViewModel, null);
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
