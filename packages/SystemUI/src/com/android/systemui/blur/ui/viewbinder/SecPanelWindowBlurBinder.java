package com.android.systemui.blur.ui.viewbinder;

import android.util.Log;
import android.util.MathUtils;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.R;
import com.android.systemui.blur.QSColorCurve;
import com.android.systemui.blur.SecQSBlurShadowView;
import com.android.systemui.blur.SecQSNewBlurView;
import com.android.systemui.blur.data.repository.SecPanelWindowBlurRepository;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor;
import com.android.systemui.blur.domain.interactor.SecPanelBackgroundDisplayInteractor;
import com.android.systemui.blur.domain.interactor.SecPanelWindowBlurInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SecPanelWindowBlurBinder implements SecPanelBlurBinding, PanelScreenShotLogger.LogProvider {
    public static final String TAG;
    public final SecPanelWindowBlurInteractor secPanelWindowBlurInteractor;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final NotificationShadeWindowView view;
    public final PathInterpolator windowBlurInterpolator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelWindowBlurBinder$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelWindowBlurBinder$1$1, reason: invalid class name and collision with other inner class name */
        final class C00621 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ View $it;
            int label;
            final /* synthetic */ SecPanelWindowBlurBinder this$0;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelWindowBlurBinder$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C00631 extends SuspendLambda implements Function2 {
                final /* synthetic */ View $it;
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ SecPanelWindowBlurBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00631(SecPanelWindowBlurBinder secPanelWindowBlurBinder, View view, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secPanelWindowBlurBinder;
                    this.$it = view;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00631 c00631 = new C00631(this.this$0, this.$it, continuation);
                    c00631.L$0 = obj;
                    return c00631;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00631) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    Pair pair = (Pair) this.L$0;
                    SemBlurInfo.Builder builder = (SemBlurInfo.Builder) pair.component1();
                    ListPopupWindow$$ExternalSyntheticOutline0.m(((Number) pair.component2()).intValue(), "setBlurInfo ", SecPanelWindowBlurBinder.TAG);
                    SecQSNewBlurView secQSNewBlurView = (SecQSNewBlurView) this.this$0.view.requireViewById(R.id.qs_new_blur);
                    SecQSBlurShadowView secQSBlurShadowView = (SecQSBlurShadowView) this.this$0.view.requireViewById(R.id.qs_large_shadow_view);
                    SecQSBlurShadowView secQSBlurShadowView2 = (SecQSBlurShadowView) this.this$0.view.requireViewById(R.id.qs_small_shadow_view);
                    if (this.this$0.secQsUiDisplayModeInteractor.isTablet()) {
                        this.$it.semSetBlurInfo(null);
                        secQSNewBlurView.setVisibility(0);
                        secQSBlurShadowView.setVisibility(0);
                        secQSBlurShadowView2.setVisibility(0);
                        secQSNewBlurView.semSetBlurInfo(builder.build());
                    } else {
                        builder.setBackgroundCornerRadius(0.0f);
                        this.$it.semSetBlurInfo(builder.build());
                        secQSNewBlurView.setVisibility(8);
                        secQSBlurShadowView.setVisibility(8);
                        secQSBlurShadowView2.setVisibility(8);
                        secQSNewBlurView.semSetBlurInfo(null);
                    }
                    return Unit.INSTANCE;
                }
            }

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelWindowBlurBinder$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                /* synthetic */ boolean Z$0;
                int label;
                final /* synthetic */ SecPanelWindowBlurBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(SecPanelWindowBlurBinder secPanelWindowBlurBinder, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secPanelWindowBlurBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
                    anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
                    return anonymousClass2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    if (!this.Z$0) {
                        if ((r3.context.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level) / 100.0f) * this.this$0.secPanelWindowBlurInteractor.secPanelWindowBlurRepository.qsColorCurve.radius > 0.0f) {
                            this.this$0.doBlur(SecPanelBlurBinding.BlurType.QUICK_PANEL);
                        }
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00621(SecPanelWindowBlurBinder secPanelWindowBlurBinder, LifecycleOwner lifecycleOwner, View view, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secPanelWindowBlurBinder;
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$it = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00621(this.this$0, this.$$this$repeatWhenAttached, this.$it, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00621) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                SecPanelWindowBlurBinder secPanelWindowBlurBinder = this.this$0;
                FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(secPanelWindowBlurBinder.secPanelWindowBlurInteractor.blurInfoData, new C00631(secPanelWindowBlurBinder, this.$it, null)), LifecycleOwnerKt.getLifecycleScope(this.$$this$repeatWhenAttached));
                SecPanelWindowBlurBinder secPanelWindowBlurBinder2 = this.this$0;
                FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(secPanelWindowBlurBinder2.secPanelWindowBlurInteractor.shouldBlockWindowBlur, new AnonymousClass2(secPanelWindowBlurBinder2, null)), LifecycleOwnerKt.getLifecycleScope(this.$$this$repeatWhenAttached));
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = SecPanelWindowBlurBinder.this.new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            anonymousClass1.L$1 = (View) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                View view = (View) this.L$1;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C00621 c00621 = new C00621(SecPanelWindowBlurBinder.this, lifecycleOwner, view, null);
                this.L$0 = null;
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c00621, this) == coroutineSingletons) {
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
        String simpleName = Reflection.getOrCreateKotlinClass(SecPanelWindowBlurBinder.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    public SecPanelWindowBlurBinder(NotificationShadeWindowView notificationShadeWindowView, SecPanelWindowBlurInteractor secPanelWindowBlurInteractor, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor, SecBlurCustomColorInteractor secBlurCustomColorInteractor, SecPanelBackgroundDisplayInteractor secPanelBackgroundDisplayInteractor) {
        this.view = notificationShadeWindowView;
        this.secPanelWindowBlurInteractor = secPanelWindowBlurInteractor;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        secBlurCustomColorInteractor.getClass();
        this.windowBlurInterpolator = new PathInterpolator(0.42f, 0.22f, 0.18f, 1.0f);
        RepeatWhenAttachedKt.repeatWhenAttached(notificationShadeWindowView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
        PanelScreenShotLogger.INSTANCE.addLogProvider(TAG, this);
    }

    @Override // com.android.systemui.blur.di.SecPanelBlurBinding
    public final void doBlur(SecPanelBlurBinding.BlurType blurType) {
        SecPanelWindowBlurInteractor secPanelWindowBlurInteractor = this.secPanelWindowBlurInteractor;
        int integer = (int) ((r13.context.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level) / 100.0f) * secPanelWindowBlurInteractor.secPanelWindowBlurRepository.qsColorCurve.radius);
        SecPanelWindowBlurRepository secPanelWindowBlurRepository = secPanelWindowBlurInteractor.secPanelWindowBlurRepository;
        if (secPanelWindowBlurRepository.windowBlurRadius != integer || ((Boolean) secPanelWindowBlurInteractor.shouldShow.$$delegate_0.getValue()).booleanValue()) {
            SharedFlowImpl sharedFlowImpl = secPanelWindowBlurInteractor.blurInfoData;
            SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
            StateFlow stateFlow = secPanelWindowBlurInteractor.backgroundVisible;
            if (!((Boolean) stateFlow.getValue()).booleanValue()) {
                QSColorCurve qSColorCurve = secPanelWindowBlurRepository.qsColorCurve;
                builder.setColorCurve(qSColorCurve.saturation, qSColorCurve.curve, qSColorCurve.minX, qSColorCurve.maxX, qSColorCurve.minY, qSColorCurve.maxY);
            }
            builder.setBackgroundCornerRadius(secPanelWindowBlurRepository.context.getResources().getDimensionPixelSize(R.dimen.qs_pop_over_corner_radius));
            ReadonlyStateFlow readonlyStateFlow = secPanelWindowBlurInteractor.shouldBlockWindowBlur;
            builder.setRadius(((Boolean) readonlyStateFlow.$$delegate_0.getValue()).booleanValue() ? 0 : integer);
            StateFlow stateFlow2 = readonlyStateFlow.$$delegate_0;
            secPanelWindowBlurRepository.windowBlurRadius = ((Boolean) stateFlow2.getValue()).booleanValue() ? 0 : integer;
            Log.d(SecPanelWindowBlurInteractor.TAG, "Window Blur:  " + integer + "  shouldBlockBlur: " + stateFlow2.getValue() + " isBlurReduced: " + secPanelWindowBlurInteractor.secBlurSettingsInteractor.blurReduced.$$delegate_0.getValue() + "  isBackgroundVisible: " + stateFlow.getValue());
            sharedFlowImpl.tryEmit(new Pair(builder, Integer.valueOf(integer)));
        }
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(TAG + " =================================================================================== ");
        SecPanelWindowBlurInteractor secPanelWindowBlurInteractor = this.secPanelWindowBlurInteractor;
        SecPanelWindowBlurRepository secPanelWindowBlurRepository = secPanelWindowBlurInteractor.secPanelWindowBlurRepository;
        float integer = (((float) secPanelWindowBlurRepository.context.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level)) / 100.0f) * secPanelWindowBlurRepository.qsColorCurve.radius;
        float integer2 = r6.context.getResources().getInteger(R.integer.theme_designer_quick_star_blur_level) / 100.0f;
        int i = secPanelWindowBlurInteractor.secPanelWindowBlurRepository.windowBlurRadius;
        StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("  radius =  ", integer, "  custom_blur_level =  ", integer2, "  windowBlurRadius =  ");
        m.append(i);
        arrayList.add(m.toString());
        arrayList.add("======================================================================================================= ");
        return arrayList;
    }

    @Override // com.android.systemui.blur.di.SecPanelBlurBinding
    public final float getInterpolation(float f) {
        return this.secQsUiDisplayModeInteractor.isTablet() ? f : this.windowBlurInterpolator.getInterpolation(f);
    }

    @Override // com.android.systemui.blur.di.SecPanelBlurBinding
    public final void setFraction(float f) {
        boolean isTablet = this.secQsUiDisplayModeInteractor.isTablet();
        SecPanelWindowBlurInteractor secPanelWindowBlurInteractor = this.secPanelWindowBlurInteractor;
        if (!isTablet) {
            secPanelWindowBlurInteractor.secPanelWindowBlurRepository.qsColorCurve.setFraction(f);
            return;
        }
        SecPanelSplitHelper.Companion companion = SecPanelSplitHelper.Companion;
        companion.getClass();
        float f2 = f - (SecPanelSplitHelper.isEnabled ? 0.5f : 0.0f);
        float f3 = 1;
        companion.getClass();
        float constrain = MathUtils.constrain(f2 / (f3 - (!SecPanelSplitHelper.isEnabled ? 0.0f : 0.5f)), 0.0f, 1.0f);
        NotificationShadeWindowView notificationShadeWindowView = this.view;
        SecQSNewBlurView secQSNewBlurView = (SecQSNewBlurView) notificationShadeWindowView.findViewById(R.id.qs_new_blur);
        if (secQSNewBlurView != null) {
            secQSNewBlurView.setAlpha(constrain);
        }
        SecQSBlurShadowView secQSBlurShadowView = (SecQSBlurShadowView) notificationShadeWindowView.findViewById(R.id.qs_large_shadow_view);
        if (secQSBlurShadowView != null) {
            secQSBlurShadowView.setAlpha(constrain);
        }
        SecQSBlurShadowView secQSBlurShadowView2 = (SecQSBlurShadowView) notificationShadeWindowView.findViewById(R.id.qs_small_shadow_view);
        if (secQSBlurShadowView2 != null) {
            secQSBlurShadowView2.setAlpha(constrain);
        }
        secPanelWindowBlurInteractor.secPanelWindowBlurRepository.qsColorCurve.setFraction(constrain);
    }
}
