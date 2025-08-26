package com.android.systemui.blur.ui.viewbinder;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.SecQSBlurShadowView;
import com.android.systemui.blur.di.SecPanelBackgroundBinding;
import com.android.systemui.blur.ui.viewmodel.SecPanelBackgroundViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.SecPanelBackground;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.util.ArrayList;
import kotlin.KotlinNothingValueException;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes.dex */
public final class SecPanelBackgroundBinder implements SecPanelBackgroundBinding, PanelScreenShotLogger.LogProvider {
    public static final String TAG;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final NotificationShadeWindowView shadeWindowView;
    public final SecPanelBackground view;
    public final SecPanelBackgroundViewModel viewModel;

    /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder$1$1, reason: invalid class name and collision with other inner class name */
        final class C01101 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SecPanelBackgroundBinder this$0;

            /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C01111 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SecPanelBackgroundBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01111(SecPanelBackgroundBinder secPanelBackgroundBinder, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secPanelBackgroundBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C01111(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01111) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final SecPanelBackgroundBinder secPanelBackgroundBinder = this.this$0;
                        ReadonlyStateFlow readonlyStateFlow = secPanelBackgroundBinder.viewModel.maxAlpha;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                float fFloatValue = ((Number) obj2).floatValue();
                                Log.d("SecPanelBackgroundBinder", "maxAlpha = " + fFloatValue);
                                SecPanelBackgroundBinder secPanelBackgroundBinder2 = secPanelBackgroundBinder;
                                secPanelBackgroundBinder2.view.mMaxAlpha = fFloatValue;
                                SecPanelBackground secPanelBackground = (SecPanelBackground) secPanelBackgroundBinder2.shadeWindowView.findViewById(R.id.qs_new_blur_background);
                                if (secPanelBackground != null) {
                                    secPanelBackground.mMaxAlpha = fFloatValue;
                                }
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

            /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SecPanelBackgroundBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(SecPanelBackgroundBinder secPanelBackgroundBinder, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secPanelBackgroundBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.this$0, continuation);
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
                        final SecPanelBackgroundBinder secPanelBackgroundBinder = this.this$0;
                        StateFlow stateFlow = secPanelBackgroundBinder.viewModel.shouldShow;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldShow = ", "SecPanelBackgroundBinder", zBooleanValue);
                                String str = SecPanelBackgroundBinder.TAG;
                                SecPanelBackgroundBinder secPanelBackgroundBinder2 = secPanelBackgroundBinder;
                                secPanelBackgroundBinder2.getClass();
                                secPanelBackgroundBinder2.view.setVisibility(zBooleanValue ? 0 : 8);
                                NotificationShadeWindowView notificationShadeWindowView = secPanelBackgroundBinder2.shadeWindowView;
                                SecPanelBackground secPanelBackground = (SecPanelBackground) notificationShadeWindowView.findViewById(R.id.qs_new_blur_background);
                                if (secPanelBackground != null) {
                                    secPanelBackground.setVisibility(zBooleanValue ? 0 : 8);
                                }
                                SecQSBlurShadowView secQSBlurShadowView = (SecQSBlurShadowView) notificationShadeWindowView.findViewById(R.id.qs_large_shadow_view);
                                if (secQSBlurShadowView != null) {
                                    secQSBlurShadowView.enabled = !zBooleanValue;
                                    if (zBooleanValue) {
                                        secQSBlurShadowView.setAlpha(0.0f);
                                    }
                                }
                                SecQSBlurShadowView secQSBlurShadowView2 = (SecQSBlurShadowView) notificationShadeWindowView.findViewById(R.id.qs_small_shadow_view);
                                if (secQSBlurShadowView2 != null) {
                                    secQSBlurShadowView2.enabled = !zBooleanValue;
                                    if (zBooleanValue) {
                                        secQSBlurShadowView2.setAlpha(0.0f);
                                    }
                                }
                                Log.d("SecPanelBackgroundBinder", "DIM visibility = ".concat(zBooleanValue ? "VISIBLE" : "GONE"));
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SecPanelBackgroundBinder this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(SecPanelBackgroundBinder secPanelBackgroundBinder, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secPanelBackgroundBinder;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.this$0, continuation);
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
                        final SecPanelBackgroundBinder secPanelBackgroundBinder = this.this$0;
                        SharedFlowImpl sharedFlowImpl = secPanelBackgroundBinder.viewModel.updateBackgroundColor;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("updateBackgroundColor = ", "SecPanelBackgroundBinder", ((Boolean) obj2).booleanValue());
                                String str = SecPanelBackgroundBinder.TAG;
                                secPanelBackgroundBinder.updateBackgroundColor();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        sharedFlowImpl.getClass();
                        if (SharedFlowImpl.collect$suspendImpl(sharedFlowImpl, flowCollector, this) == coroutineSingletons) {
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
            public C01101(SecPanelBackgroundBinder secPanelBackgroundBinder, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secPanelBackgroundBinder;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01101 c01101 = new C01101(this.this$0, continuation);
                c01101.L$0 = obj;
                return c01101;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new C01111(this.this$0, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = SecPanelBackgroundBinder.this.new AnonymousClass1((Continuation) obj3);
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
                C01101 c01101 = new C01101(SecPanelBackgroundBinder.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c01101, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(SecPanelBackgroundBinder.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    public SecPanelBackgroundBinder(NotificationShadeWindowView notificationShadeWindowView, SecPanelBackground secPanelBackground, SecPanelBackgroundViewModel.Factory factory, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.shadeWindowView = notificationShadeWindowView;
        this.view = secPanelBackground;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        this.viewModel = factory.create();
        RepeatWhenAttachedKt.repeatWhenAttached(secPanelBackground, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
        secPanelBackground.animate().translationY(0.0f).setDuration(200L).start();
        updateBackgroundColor();
        PanelScreenShotLogger.INSTANCE.addLogProvider(TAG, this);
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        SecPanelBackground secPanelBackground = this.view;
        int color = secPanelBackground.getContext().getColor(R.color.open_theme_qp_bg_color);
        arrayList.add(TAG + " ============================================= ");
        float fFloatValue = ((Number) this.viewModel.maxAlpha.$$delegate_0.getValue()).floatValue();
        float alpha = secPanelBackground.getAlpha();
        int visibility = secPanelBackground.getVisibility();
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("  mMaxAlpha = ", fFloatValue, "  currentAlpha =  ", alpha, "  visibility =  ");
        sbM.append(visibility);
        arrayList.add(sbM.toString());
        arrayList.add(MotionLayout$$ExternalSyntheticOutline0.m("  DIM color = 0x ", Integer.toHexString(color), " , BOX color = 0x ", Integer.toHexString(secPanelBackground.getContext().getColor(R.color.qs_tile_container_bg)), " "));
        arrayList.add("============================================================== ");
        return arrayList;
    }

    public final void updateBackgroundColor() {
        SecPanelBackground secPanelBackground = this.view;
        int color = secPanelBackground.getContext().getColor((QpRune.QUICK_PANEL_BLUR_MASSIVE && this.secQsUiDisplayModeInteractor.isTablet()) ? (secPanelBackground.getContext().getResources().getConfiguration().uiMode & 32) != 0 ? R.color.tablet_massive_qp_bg_dark_color : R.color.tablet_massive_qp_bg_color : R.color.open_theme_qp_bg_color);
        int i = (color >> 16) & 255;
        int i2 = (color >> 8) & 255;
        int i3 = color & 255;
        secPanelBackground.setBackgroundColor(Color.rgb(i, i2, i3));
        SecPanelBackground secPanelBackground2 = (SecPanelBackground) this.shadeWindowView.findViewById(R.id.qs_new_blur_background);
        if (secPanelBackground2 != null) {
            ((GradientDrawable) secPanelBackground2.getBackground()).setColor(Color.rgb(i, i2, i3));
        }
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "DIM color = ", " ", " ");
        sbM.append(i3);
        Log.d(TAG, sbM.toString());
    }
}
