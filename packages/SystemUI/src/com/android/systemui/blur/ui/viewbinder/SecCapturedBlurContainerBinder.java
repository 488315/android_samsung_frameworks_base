package com.android.systemui.blur.ui.viewbinder;

import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.DejankUtils;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepositoryImpl;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.blur.di.SecPanelCapturedBlurBinding;
import com.android.systemui.blur.domain.interactor.SecBlurSettingsInteractor;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurBitmapGenerator;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurCollapseShaderInteractor;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurInfoInteractor;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurInteractor;
import com.android.systemui.blur.ui.viewmodel.SecCapturedBlurContainerViewModel;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.CapturedBlurContainer;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.Optional;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecCapturedBlurContainerBinder implements SecPanelCapturedBlurBinding {
    public static final String TAG;
    public final SecCapturedBlurBitmapGenerator secCapturedBlurBitmapGenerator;
    public final SecCapturedBlurInteractor secCapturedBlurInteractor;
    public final SecBlurSettingsInteractor settingsInteractor;
    public final CapturedBlurContainer view;
    public final SecCapturedBlurContainerViewModel viewModel;
    public final Handler mainUIHandler = new Handler(Looper.getMainLooper());
    public final PathInterpolator captureBlurInterpolator = new PathInterpolator(0.29f, 0.08f, 0.69f, 0.98f);
    public final SecCapturedBlurContainerBinder$retryBouncerBlur$1 retryBouncerBlur = new Runnable() { // from class: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder$retryBouncerBlur$1
        @Override // java.lang.Runnable
        public final void run() {
            String str = SecCapturedBlurContainerBinder.TAG;
            Log.d(str, "Retry wallpaper screenshot");
            BitmapDrawable blurredBitmapWithEffect = SecCapturedBlurContainerBinder.this.secCapturedBlurBitmapGenerator.getBlurredBitmapWithEffect(SecPanelBlurBinding.BlurType.BOUNCER);
            if (blurredBitmapWithEffect != null) {
                SecCapturedBlurContainerBinder secCapturedBlurContainerBinder = SecCapturedBlurContainerBinder.this;
                secCapturedBlurContainerBinder.view.setBackgroundDrawable(blurredBitmapWithEffect);
                secCapturedBlurContainerBinder.view.setAlpha(1.0f);
                Log.d(str, "applied bitmap from retryBouncerBlur");
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ Optional<SecCapturedBlurCollapseShaderInteractor> $secCapturedBlurCollapseShaderInteractor;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder$1$1, reason: invalid class name and collision with other inner class name */
        final class C00541 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ Optional<SecCapturedBlurCollapseShaderInteractor> $secCapturedBlurCollapseShaderInteractor;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SecCapturedBlurContainerBinder this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C00551 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SecCapturedBlurContainerBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00551(SecCapturedBlurContainerBinder secCapturedBlurContainerBinder, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secCapturedBlurContainerBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00551(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00551) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final SecCapturedBlurContainerBinder secCapturedBlurContainerBinder = this.this$0;
                        ReadonlyStateFlow readonlyStateFlow = secCapturedBlurContainerBinder.viewModel.shouldBeGone;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                boolean booleanValue = ((Boolean) obj2).booleanValue();
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldBeGone = ", SecCapturedBlurContainerBinder.TAG, booleanValue);
                                SecCapturedBlurContainerBinder secCapturedBlurContainerBinder2 = SecCapturedBlurContainerBinder.this;
                                secCapturedBlurContainerBinder2.getClass();
                                secCapturedBlurContainerBinder2.view.setVisibility(booleanValue ? 8 : 0);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ CoroutineScope $$this$repeatOnLifecycle;
                final /* synthetic */ Optional<SecCapturedBlurCollapseShaderInteractor> $secCapturedBlurCollapseShaderInteractor;
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ SecCapturedBlurContainerBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(SecCapturedBlurContainerBinder secCapturedBlurContainerBinder, Optional<SecCapturedBlurCollapseShaderInteractor> optional, CoroutineScope coroutineScope, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secCapturedBlurContainerBinder;
                    this.$secCapturedBlurCollapseShaderInteractor = optional;
                    this.$$this$repeatOnLifecycle = coroutineScope;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, this.$secCapturedBlurCollapseShaderInteractor, this.$$this$repeatOnLifecycle, continuation);
                    anonymousClass3.L$0 = obj;
                    return anonymousClass3;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass3) create((SecPanelBlurBinding.BlurType) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    SecPanelBlurBinding.BlurType blurType = (SecPanelBlurBinding.BlurType) this.L$0;
                    if (this.this$0.view.getVisibility() != 0) {
                        if (blurType == SecPanelBlurBinding.BlurType.QUICK_PANEL) {
                            this.$secCapturedBlurCollapseShaderInteractor.get().sendCollapseQsWhileCapturedViewInvisible();
                        }
                        return Unit.INSTANCE;
                    }
                    CapturedBlurContainer capturedBlurContainer = this.this$0.view;
                    if (capturedBlurContainer.getAlpha() > 0.0f && capturedBlurContainer.getBackground() == null) {
                        BitmapDrawable blurredBitmapWithEffect = this.this$0.secCapturedBlurBitmapGenerator.getBlurredBitmapWithEffect(blurType);
                        if (blurredBitmapWithEffect != null) {
                            this.this$0.view.setBackgroundDrawable(blurredBitmapWithEffect);
                            Boxing.boxInt(Log.d(SecCapturedBlurContainerBinder.TAG, "applied bitmap non blurinfo"));
                        } else {
                            SecCapturedBlurContainerBinder secCapturedBlurContainerBinder = this.this$0;
                            if (blurType == SecPanelBlurBinding.BlurType.BOUNCER && !((Boolean) secCapturedBlurContainerBinder.settingsInteractor.minimalBatteryUse.$$delegate_0.getValue()).booleanValue() && WallpaperUtils.sWallpaperType != 7) {
                                Log.d(SecCapturedBlurContainerBinder.TAG, "Send to retry wallpaper screenshot");
                                DejankUtils.postAfterTraversal(secCapturedBlurContainerBinder.retryBouncerBlur);
                            }
                        }
                    }
                    Log.d(SecCapturedBlurContainerBinder.TAG, "requestCaptureBlur blurType = " + blurType);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00541(SecCapturedBlurContainerBinder secCapturedBlurContainerBinder, LifecycleOwner lifecycleOwner, Optional<SecCapturedBlurCollapseShaderInteractor> optional, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secCapturedBlurContainerBinder;
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$secCapturedBlurCollapseShaderInteractor = optional;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00541 c00541 = new C00541(this.this$0, this.$$this$repeatWhenAttached, this.$secCapturedBlurCollapseShaderInteractor, continuation);
                c00541.L$0 = obj;
                return c00541;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00541) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new C00551(this.this$0, null), 3);
                SecCapturedBlurContainerBinder secCapturedBlurContainerBinder = this.this$0;
                FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(secCapturedBlurContainerBinder.viewModel.requestCaptureBlur, new AnonymousClass3(secCapturedBlurContainerBinder, this.$secCapturedBlurCollapseShaderInteractor, coroutineScope, null)), LifecycleOwnerKt.getLifecycleScope(this.$$this$repeatWhenAttached));
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Optional<SecCapturedBlurCollapseShaderInteractor> optional, Continuation continuation) {
            super(3, continuation);
            this.$secCapturedBlurCollapseShaderInteractor = optional;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = SecCapturedBlurContainerBinder.this.new AnonymousClass1(this.$secCapturedBlurCollapseShaderInteractor, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C00541 c00541 = new C00541(SecCapturedBlurContainerBinder.this, lifecycleOwner, this.$secCapturedBlurCollapseShaderInteractor, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c00541, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(SecCapturedBlurContainerBinder.class).getSimpleName();
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder$retryBouncerBlur$1] */
    public SecCapturedBlurContainerBinder(CapturedBlurContainer capturedBlurContainer, SecCapturedBlurContainerViewModel.Factory factory, SecCapturedBlurBitmapGenerator secCapturedBlurBitmapGenerator, SecCapturedBlurInteractor secCapturedBlurInteractor, SecBlurSettingsInteractor secBlurSettingsInteractor, final PrimaryBouncerInteractor primaryBouncerInteractor, Optional<SecCapturedBlurCollapseShaderInteractor> optional, Optional<SecCapturedBlurInfoInteractor> optional2) {
        this.view = capturedBlurContainer;
        this.secCapturedBlurBitmapGenerator = secCapturedBlurBitmapGenerator;
        this.secCapturedBlurInteractor = secCapturedBlurInteractor;
        this.settingsInteractor = secBlurSettingsInteractor;
        this.viewModel = factory.create();
        RepeatWhenAttachedKt.repeatWhenAttached(capturedBlurContainer, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(optional, null));
        capturedBlurContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder.2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (i == i5 && i3 == i7 && i2 == i6 && i4 == i8) {
                    return;
                }
                if (view != null) {
                    ViewVisibilityUtil.INSTANCE.getClass();
                    if (view.getVisibility() != 0) {
                        return;
                    }
                }
                final SecCapturedBlurContainerBinder secCapturedBlurContainerBinder = SecCapturedBlurContainerBinder.this;
                Handler handler = secCapturedBlurContainerBinder.mainUIHandler;
                final PrimaryBouncerInteractor primaryBouncerInteractor2 = primaryBouncerInteractor;
                handler.post(new Runnable() { // from class: com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder.2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BitmapDrawable blurredBitmapWithEffect;
                        BitmapDrawable blurredBitmapWithEffect2;
                        if (PrimaryBouncerInteractor.this.isBouncerShowing() && (blurredBitmapWithEffect2 = secCapturedBlurContainerBinder.secCapturedBlurBitmapGenerator.getBlurredBitmapWithEffect(SecPanelBlurBinding.BlurType.BOUNCER)) != null) {
                            secCapturedBlurContainerBinder.view.setBackgroundDrawable(blurredBitmapWithEffect2);
                        }
                        if (!((Boolean) secCapturedBlurContainerBinder.viewModel.fullScreenBlurShowing.$$delegate_0.getValue()).booleanValue() || (blurredBitmapWithEffect = secCapturedBlurContainerBinder.secCapturedBlurBitmapGenerator.getBlurredBitmapWithEffect(SecPanelBlurBinding.BlurType.FULL_SCREEN)) == null) {
                            return;
                        }
                        secCapturedBlurContainerBinder.view.setBackgroundDrawable(blurredBitmapWithEffect);
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.blur.di.SecPanelBlurBinding
    public final void doBlur(SecPanelBlurBinding.BlurType blurType) {
        ((SecCapturedBlurRepositoryImpl) this.secCapturedBlurInteractor.secCapturedBlurRepository).requestCaptureBlur.tryEmit(blurType);
    }

    @Override // com.android.systemui.blur.di.SecPanelBlurBinding
    public final float getInterpolation(float f) {
        return this.captureBlurInterpolator.getInterpolation(f);
    }

    @Override // com.android.systemui.blur.di.SecPanelBlurBinding
    public final void setFraction(float f) {
        CapturedBlurContainer capturedBlurContainer = this.view;
        SecCapturedBlurContainerViewModel secCapturedBlurContainerViewModel = this.viewModel;
        if (f == 0.0f && !((Boolean) secCapturedBlurContainerViewModel.secCapturedBlurInteractor.mirrorShowing.$$delegate_0.getValue()).booleanValue()) {
            capturedBlurContainer.setAlpha(0.0f);
            capturedBlurContainer.setBackgroundDrawable(null);
        }
        capturedBlurContainer.setAlpha(f);
        Log.d(TAG, "setFraction fraction = " + f + " , mirrorShowing = " + ((Boolean) secCapturedBlurContainerViewModel.secCapturedBlurInteractor.mirrorShowing.$$delegate_0.getValue()).booleanValue());
    }
}
