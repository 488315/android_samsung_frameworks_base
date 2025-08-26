package com.android.systemui.scene.ui.viewmodel;

import android.view.View;
import androidx.compose.runtime.State;
import androidx.compose.ui.unit.Dp;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.EdgeDetectorKt;
import com.android.compose.animation.scene.FixedSizeEdgeDetector;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LightRevealScrimViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.scene.ui.viewmodel.SceneContainerHapticsViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperViewModel;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class SceneContainerViewModel extends ExclusiveActivatable {
    public final List allContentKeys;
    public final AodBurnInViewModel burnIn;
    public final KeyguardClockViewModel clock;
    public final StateFlow currentScene;
    public final FalsingInteractor falsingInteractor;
    public final SceneContainerHapticsViewModel hapticsViewModel;
    public final Hydrator hydrator;
    public final State isVisible$delegate;
    public final LightRevealScrimViewModel lightRevealScrim;
    public final SceneLogger logger;
    public final Function1 motionEventHandlerReceiver;
    public final PowerInteractor powerInteractor;
    public final RemoteInputInteractor remoteInputInteractor;
    public final State ribbonColorSaturation$delegate;
    public final SceneInteractor sceneInteractor;
    public final State swipeSourceDetector$delegate;
    public final WallpaperViewModel wallpaperViewModel;

    /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return SceneContainerViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SceneContainerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(SceneContainerViewModel sceneContainerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = sceneContainerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    Hydrator hydrator = this.this$0.hydrator;
                    this.label = 1;
                    if (hydrator.activate(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$onActivated$3$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SceneContainerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(SceneContainerViewModel sceneContainerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = sceneContainerViewModel;
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
                    SceneContainerHapticsViewModel sceneContainerHapticsViewModel = this.this$0.hapticsViewModel;
                    this.label = 1;
                    if (sceneContainerHapticsViewModel.activate(this) == coroutineSingletons) {
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

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = SceneContainerViewModel.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(SceneContainerViewModel.this, null), 7);
            return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(SceneContainerViewModel.this, null), 6);
        }
    }

    public SceneContainerViewModel(SceneInteractor sceneInteractor, FalsingInteractor falsingInteractor, PowerInteractor powerInteractor, ShadeModeInteractor shadeModeInteractor, RemoteInputInteractor remoteInputInteractor, SceneLogger sceneLogger, SceneContainerHapticsViewModel.Factory factory, LightRevealScrimViewModel lightRevealScrimViewModel, WallpaperViewModel wallpaperViewModel, KeyguardInteractor keyguardInteractor, AodBurnInViewModel aodBurnInViewModel, KeyguardClockViewModel keyguardClockViewModel, View view, Function1 function1) {
        this.sceneInteractor = sceneInteractor;
        this.falsingInteractor = falsingInteractor;
        this.powerInteractor = powerInteractor;
        this.remoteInputInteractor = remoteInputInteractor;
        this.logger = sceneLogger;
        this.lightRevealScrim = lightRevealScrimViewModel;
        this.wallpaperViewModel = wallpaperViewModel;
        this.burnIn = aodBurnInViewModel;
        this.clock = keyguardClockViewModel;
        this.motionEventHandlerReceiver = function1;
        this.currentScene = sceneInteractor.currentScene;
        Hydrator hydrator = new Hydrator("SceneContainerViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.isVisible$delegate = hydrator.hydratedStateOf(sceneInteractor.isVisible, "isVisible");
        this.allContentKeys = sceneInteractor.allContentKeys;
        this.hapticsViewModel = factory.create(view);
        FixedSizeEdgeDetector fixedSizeEdgeDetector = EdgeDetectorKt.DefaultEdgeDetector;
        final ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode;
        this.swipeSourceDetector$delegate = hydrator.hydratedStateOf("swipeSourceDetector", fixedSizeEdgeDetector, new Flow() { // from class: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                    Object sceneContainerSwipeDetector;
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
                        if (((ShadeMode) obj) instanceof ShadeMode.Dual) {
                            Dp.Companion companion = Dp.Companion;
                            sceneContainerSwipeDetector = new SceneContainerSwipeDetector(40, null);
                        } else {
                            sceneContainerSwipeDetector = EdgeDetectorKt.DefaultEdgeDetector;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(sceneContainerSwipeDetector, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        final Flow flow = keyguardInteractor.dozeAmount;
        this.ribbonColorSaturation$delegate = hydrator.hydratedStateOf("ribbonColorSaturation", Float.valueOf(1.0f), new Flow() { // from class: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Float f = new Float(1 - ((Number) obj).floatValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(f, anonymousClass1) == coroutineSingletons) {
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
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0066, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                this.motionEventHandlerReceiver.mo781invoke(new Object() { // from class: com.android.systemui.scene.ui.viewmodel.SceneContainerViewModel.onActivated.2
                });
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(null);
                anonymousClass1.L$0 = this;
                anonymousClass1.label = 1;
                if (CoroutineScopeKt.coroutineScope(anonymousClass3, anonymousClass1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (SceneContainerViewModel) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            this = (SceneContainerViewModel) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 2;
        } catch (Throwable th) {
            this.motionEventHandlerReceiver.mo781invoke(null);
            throw th;
        }
    }
}
