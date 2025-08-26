package com.android.systemui.blur;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.data.repository.SecCapturedBlurRepositoryImpl;
import com.android.systemui.blur.di.SecPanelBackgroundBinding;
import com.android.systemui.blur.di.SecPanelBlurBinding;
import com.android.systemui.blur.di.SecPanelCapturedBlurBinding;
import com.android.systemui.blur.domain.interactor.SecBlurCustomColorInteractor;
import com.android.systemui.blur.domain.interactor.SecBlurSettingsInteractor;
import com.android.systemui.blur.domain.interactor.SecCapturedBlurBitmapGenerator;
import com.android.systemui.blur.ui.viewbinder.SecCapturedBlurContainerBinder;
import com.android.systemui.blur.ui.viewbinder.SecPanelBackgroundBinder;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.phone.SecPanelBackground;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
public final class SecQpBlurController implements PanelScreenShotLogger.LogProvider {
    public static final String TAG;
    public float animatedFraction;
    public final Choreographer choreographer;
    public final Flow configurationChanged;
    public final boolean isBlurAnimatorRunning;
    public boolean isBouncerShowing;
    public boolean isMirrorVisible;
    public final KeyguardInteractor keyguardInteractor;
    public final Lazy lazyUnlockedScreenOffAnimationController;
    public final SecPanelBackgroundBinding panelBackgroundBinding;
    public final SecPanelBlurBinding panelBlurBinding;
    public float panelExpandedFraction;
    public final SecBlurCustomColorInteractor secBlurCustomColorInteractor;
    public final SecCapturedBlurBitmapGenerator secCapturedBlurBitmapGenerator;
    public final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor;
    public final SecBlurSettingsInteractor settingsInteractor;
    public final ShadeControllerImpl shadeController;
    public final Function1 updateBlurCallback = new SecQpBlurController$updateBlurCallback$1(this);
    public ValueAnimator blurAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);

    /* renamed from: com.android.systemui.blur.SecQpBlurController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1, reason: invalid class name and collision with other inner class name */
        final class C01011 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                /* synthetic */ float F$0;
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
                    anonymousClass2.F$0 = ((Number) obj).floatValue();
                    return anonymousClass2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass2) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    float f = this.F$0;
                    SecQpBlurController secQpBlurController = this.this$0;
                    if (secQpBlurController.panelExpandedFraction != f) {
                        secQpBlurController.panelExpandedFraction = f;
                        Log.d(SecQpBlurController.TAG, "setTransitionToFullShadeAmount : " + f);
                        SecQpBlurController secQpBlurController2 = this.this$0;
                        secQpBlurController2.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController2.updateBlurCallback));
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01011(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01011(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01011) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final SecQpBlurController secQpBlurController = this.this$0;
                    final StateFlowImpl stateFlowImpl = secQpBlurController.secPanelExpansionStateInteractor.lockscreenShadeFraction;
                    Flow flow = new Flow() { // from class: com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1

                        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ SecQpBlurController this$0;

                            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$1$invokeSuspend$$inlined$filterNot$1$2$1, reason: invalid class name */
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
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector, SecQpBlurController secQpBlurController) {
                                this.$this_unsafeFlow = flowCollector;
                                this.this$0 = secQpBlurController;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i = anonymousClass1.label;
                                    if ((i & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = new AnonymousClass1(continuation);
                                    }
                                }
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i2 = anonymousClass1.label;
                                if (i2 == 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    ((Number) obj).floatValue();
                                    if (this.this$0.secPanelExpansionStateInteractor.getstatusBarState() == 1) {
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                } else {
                                    if (i2 != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector, secQpBlurController), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, anonymousClass2, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2$2, reason: invalid class name and collision with other inner class name */
            final class C01022 extends SuspendLambda implements Function2 {
                /* synthetic */ float F$0;
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01022(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C01022 c01022 = new C01022(this.this$0, continuation);
                    c01022.F$0 = ((Number) obj).floatValue();
                    return c01022;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01022) create(Float.valueOf(((Number) obj).floatValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    float f = this.F$0;
                    SecQpBlurController secQpBlurController = this.this$0;
                    if (secQpBlurController.panelExpandedFraction != f) {
                        secQpBlurController.panelExpandedFraction = f;
                        Log.d(SecQpBlurController.TAG, "onPanelExpansionChanged : " + f);
                        SecQpBlurController.access$doFrame(this.this$0);
                    }
                    SecQpBlurController secQpBlurController2 = this.this$0;
                    secQpBlurController2.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController2.updateBlurCallback));
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
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
                    final SecQpBlurController secQpBlurController = this.this$0;
                    final StateFlowImpl stateFlowImpl = secQpBlurController.secPanelExpansionStateInteractor.shadeFraction;
                    Flow flow = new Flow() { // from class: com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1

                        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ SecQpBlurController this$0;

                            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$2$invokeSuspend$$inlined$filterNot$1$2$1, reason: invalid class name */
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
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector, SecQpBlurController secQpBlurController) {
                                this.$this_unsafeFlow = flowCollector;
                                this.this$0 = secQpBlurController;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i = anonymousClass1.label;
                                    if ((i & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = new AnonymousClass1(continuation);
                                    }
                                }
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i2 = anonymousClass1.label;
                                if (i2 == 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    ((Number) obj).floatValue();
                                    if (this.this$0.secPanelExpansionStateInteractor.getstatusBarState() != 1) {
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    }
                                } else {
                                    if (i2 != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector, secQpBlurController), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    C01022 c01022 = new C01022(this.this$0, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, c01022, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$3$1, reason: invalid class name and collision with other inner class name */
            final class C01031 extends SuspendLambda implements Function2 {
                /* synthetic */ int I$0;
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01031(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C01031 c01031 = new C01031(this.this$0, continuation);
                    c01031.I$0 = ((Number) obj).intValue();
                    return c01031;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01031) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    int i = this.I$0;
                    SecQpBlurController secQpBlurController = this.this$0;
                    secQpBlurController.panelExpandedFraction = i != 1 ? i != 2 ? ((Number) secQpBlurController.secPanelExpansionStateInteractor.shadeFraction.getValue()).floatValue() : 1.0f : 0.0f;
                    Log.d(SecQpBlurController.TAG, "statusBarState : " + StatusBarState.toString(i) + " , panelExpandedFraction : " + this.this$0.panelExpandedFraction);
                    SecQpBlurController secQpBlurController2 = this.this$0;
                    secQpBlurController2.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController2.updateBlurCallback));
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
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
                    SecQpBlurController secQpBlurController = this.this$0;
                    StateFlowImpl stateFlowImpl = secQpBlurController.secPanelExpansionStateInteractor.statusBarState;
                    C01031 c01031 = new C01031(secQpBlurController, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(stateFlowImpl, c01031, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$4$1, reason: invalid class name and collision with other inner class name */
            final class C01041 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01041(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C01041(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01041) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    Log.d(SecQpBlurController.TAG, "configurationChanged");
                    SecQpBlurController secQpBlurController = this.this$0;
                    float calculatedFraction = secQpBlurController.getCalculatedFraction();
                    SecPanelBlurBinding.BlurType blurType = SecPanelBlurBinding.BlurType.QUICK_PANEL;
                    SecPanelBlurBinding secPanelBlurBinding = secQpBlurController.panelBlurBinding;
                    secPanelBlurBinding.setFraction(calculatedFraction, blurType);
                    secPanelBlurBinding.updateConfigurationChanged();
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.this$0, continuation);
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
                    SecQpBlurController secQpBlurController = this.this$0;
                    Flow flow = secQpBlurController.secBlurCustomColorInteractor.configurationChanged;
                    C01041 c01041 = new C01041(secQpBlurController, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, c01041, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.blur.SecQpBlurController$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SecQpBlurController this$0;

            /* renamed from: com.android.systemui.blur.SecQpBlurController$1$5$1, reason: invalid class name and collision with other inner class name */
            final class C01051 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SecQpBlurController this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01051(SecQpBlurController secQpBlurController, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = secQpBlurController;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C01051(this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01051) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    SecQpBlurController secQpBlurController = this.this$0;
                    if (secQpBlurController.panelExpandedFraction > 0.0f && !secQpBlurController.shadeController.isExpandingOrCollapsing()) {
                        if (this.this$0.secPanelExpansionStateInteractor.getstatusBarState() == 1) {
                            this.this$0.shadeController.getNpvc().animateCollapseQs(true);
                        } else {
                            this.this$0.shadeController.instantCollapseShade();
                        }
                    }
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(SecQpBlurController secQpBlurController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = secQpBlurController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.this$0, continuation);
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
                    SecQpBlurController secQpBlurController = this.this$0;
                    Flow flow = secQpBlurController.configurationChanged;
                    C01051 c01051 = new C01051(secQpBlurController, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow, c01051, this) == coroutineSingletons) {
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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = SecQpBlurController.this.new AnonymousClass1(continuation);
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
            BuildersKt.launch$default(coroutineScope, null, null, new C01011(SecQpBlurController.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(SecQpBlurController.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(SecQpBlurController.this, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(SecQpBlurController.this, null), 3);
            if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(SecQpBlurController.this, null), 3);
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
        String simpleName = Reflection.getOrCreateKotlinClass(SecQpBlurController.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }

    public SecQpBlurController(CoroutineScope coroutineScope, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor, SecPanelBackgroundBinding secPanelBackgroundBinding, KeyguardInteractor keyguardInteractor, Choreographer choreographer, Lazy lazy, SecPanelBlurBinding secPanelBlurBinding, SecCapturedBlurBitmapGenerator secCapturedBlurBitmapGenerator, SecBlurCustomColorInteractor secBlurCustomColorInteractor, ShadeControllerImpl shadeControllerImpl, ConfigurationInteractor configurationInteractor, SecBlurSettingsInteractor secBlurSettingsInteractor) {
        this.secPanelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.panelBackgroundBinding = secPanelBackgroundBinding;
        this.keyguardInteractor = keyguardInteractor;
        this.choreographer = choreographer;
        this.lazyUnlockedScreenOffAnimationController = lazy;
        this.panelBlurBinding = secPanelBlurBinding;
        this.secCapturedBlurBitmapGenerator = secCapturedBlurBitmapGenerator;
        this.secBlurCustomColorInteractor = secBlurCustomColorInteractor;
        this.shadeController = shadeControllerImpl;
        this.settingsInteractor = secBlurSettingsInteractor;
        final Flow flow = ((ConfigurationInteractorImpl) configurationInteractor).configurationValues;
        this.configurationChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.blur.SecQpBlurController$special$$inlined$map$1

            /* renamed from: com.android.systemui.blur.SecQpBlurController$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.blur.SecQpBlurController$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Configuration configuration = (Configuration) obj;
                        Pair pair = new Pair(new Integer(configuration.uiMode), new Integer(configuration.orientation));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(pair, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        PanelScreenShotLogger.INSTANCE.addLogProvider(TAG, this);
        this.isBlurAnimatorRunning = this.blurAnimator.isRunning();
    }

    public static final void access$doFrame(SecQpBlurController secQpBlurController) {
        float calculatedFraction = secQpBlurController.getCalculatedFraction();
        secQpBlurController.doBlur(calculatedFraction, SecPanelBlurBinding.BlurType.QUICK_PANEL);
        SecPanelBackgroundBinder secPanelBackgroundBinder = (SecPanelBackgroundBinder) secQpBlurController.panelBackgroundBinding;
        if (secPanelBackgroundBinder.view.getVisibility() != 0 || ((UnlockedScreenOffAnimationController) secQpBlurController.lazyUnlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying) {
            return;
        }
        boolean zIsTablet = ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
        SecPanelBackground secPanelBackground = secPanelBackgroundBinder.view;
        NotificationShadeWindowView notificationShadeWindowView = secPanelBackgroundBinder.shadeWindowView;
        if (zIsTablet) {
            SecPanelSplitHelper.Companion companion = SecPanelSplitHelper.Companion;
            companion.getClass();
            float f = calculatedFraction - (SecPanelSplitHelper.isEnabled ? 0.5f : 0.1f);
            float f2 = 1;
            companion.getClass();
            float f3 = f2 - (SecPanelSplitHelper.isEnabled ? 0.5f : 0.1f);
            companion.getClass();
            float fConstrain = MathUtils.constrain(f / (f3 - (SecPanelSplitHelper.isEnabled ? 0.0f : 0.7f)), 0.0f, 1.0f);
            SecPanelBackground secPanelBackground2 = (SecPanelBackground) notificationShadeWindowView.findViewById(R.id.qs_new_blur_background);
            if (secPanelBackground2 != null) {
                secPanelBackground2.setAlpha(fConstrain);
            }
            secPanelBackground.setAlpha(0.0f);
        } else {
            secPanelBackground.setAlpha(calculatedFraction);
            SecQSNewBlurView secQSNewBlurView = (SecQSNewBlurView) notificationShadeWindowView.findViewById(R.id.qs_new_blur);
            if (secQSNewBlurView != null) {
                secQSNewBlurView.setAlpha(0.0f);
            }
        }
        Log.d(SecPanelBackgroundBinder.TAG, "setAlpha = " + (((Number) secPanelBackgroundBinder.viewModel.maxAlpha.$$delegate_0.getValue()).floatValue() * calculatedFraction));
    }

    public final void doBlur(float f, SecPanelBlurBinding.BlurType blurType) {
        SecPanelBlurBinding secPanelBlurBinding = this.panelBlurBinding;
        secPanelBlurBinding.setFraction(f, blurType);
        secPanelBlurBinding.doBlur(blurType);
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(TAG + " =================================================================================== ");
        arrayList.add("  mIsMirrorVisible = " + this.isMirrorVisible + " mIsBouncerShowing = " + this.isBouncerShowing);
        arrayList.add("  animatedFraction = " + this.animatedFraction + " panelExpandedFraction = " + this.panelExpandedFraction);
        arrayList.add("======================================================================================================= ");
        return arrayList;
    }

    public final float getCalculatedFraction() {
        if (((UnlockedScreenOffAnimationController) this.lazyUnlockedScreenOffAnimationController.get()).lightRevealAnimationPlaying) {
            return 0.0f;
        }
        if (this.isMirrorVisible) {
            return this.animatedFraction;
        }
        if (!this.isBouncerShowing) {
            return this.panelBlurBinding.getInterpolation(this.panelExpandedFraction);
        }
        if (this.panelExpandedFraction != 1.0f || ((Boolean) this.settingsInteractor.blurReduced.$$delegate_0.getValue()).booleanValue()) {
            return this.animatedFraction;
        }
        return 1.0f;
    }

    public final void makeAnimationAndRun(float f, float f2, int i) {
        if (this.isBlurAnimatorRunning) {
            this.blurAnimator.cancel();
            Log.d(TAG, "Cancel Blur Animator");
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        this.blurAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(i);
        this.blurAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.blur.SecQpBlurController.makeAnimationAndRun.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SecQpBlurController.this.animatedFraction = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                SecQpBlurController secQpBlurController = SecQpBlurController.this;
                secQpBlurController.choreographer.postFrameCallback(new SecQpBlurController$sam$android_view_Choreographer_FrameCallback$0(secQpBlurController.updateBlurCallback));
            }
        });
        this.blurAnimator.start();
    }

    public final void setBrightnessMirrorVisible(boolean z) {
        Log.d(TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("setBrightnessMirrorVisible: ", z));
        this.isMirrorVisible = z;
        if (QpRune.QUICK_PANEL_BLUR_MASSIVE) {
            ((SecCapturedBlurRepositoryImpl) ((SecCapturedBlurContainerBinder) ((SecPanelCapturedBlurBinding) this.panelBlurBinding)).secCapturedBlurInteractor.secCapturedBlurRepository)._mirrorShowing.updateState(null, Boolean.valueOf(z));
        }
        if (z) {
            makeAnimationAndRun(1.0f, 0.0f, 150);
        } else {
            makeAnimationAndRun(0.0f, 1.0f, 200);
        }
    }
}
