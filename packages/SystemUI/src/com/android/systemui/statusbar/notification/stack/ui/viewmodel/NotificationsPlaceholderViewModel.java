package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import android.util.IndentingPrintWriter;
import androidx.compose.runtime.State;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor$special$$inlined$mapNotNull$1;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackAppearanceInteractor;
import com.android.systemui.util.kotlin.ActivatableFlowDumper;
import com.android.systemui.util.kotlin.ActivatableFlowDumperImpl;
import java.io.PrintWriter;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class NotificationsPlaceholderViewModel extends ExclusiveActivatable implements ActivatableFlowDumper {
    public final /* synthetic */ ActivatableFlowDumperImpl $$delegate_0;
    public final StateFlow expandFraction;
    public final HeadsUpNotificationInteractor headsUpNotificationInteractor;
    public final Hydrator hydrator;
    public final NotificationStackAppearanceInteractor interactor;
    public final State isCurrentGestureOverscroll$delegate;
    public final Flow isHeadsUpOrAnimatingAway;
    public final Flow isRemoteInputActive;
    public final State notificationsShadeContentKey$delegate;
    public final State quickSettingsShadeContentKey$delegate;
    public final RemoteInputInteractor$special$$inlined$mapNotNull$1 remoteInputRowBottomBound;
    public final SceneInteractor sceneInteractor;
    public final ShadeInteractor shadeInteractor;
    public final Flow shadeScrimRounding;
    public final StateFlow shadeToQsFraction;
    public final Flow syntheticScroll;

    public interface Factory {
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$1, reason: invalid class name */
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
            return NotificationsPlaceholderViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ NotificationsPlaceholderViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationsPlaceholderViewModel;
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

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C05232 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ NotificationsPlaceholderViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05232(NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationsPlaceholderViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C05232(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05232) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final StateFlow stateFlowIsAnyExpanded = ((ShadeInteractorImpl) this.this$0.shadeInteractor).baseShadeInteractor.isAnyExpanded();
                    Flow flow = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$2$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$2$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$2$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                    if (((Boolean) obj).booleanValue()) {
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
                            Object objCollect = stateFlowIsAnyExpanded.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final NotificationsPlaceholderViewModel notificationsPlaceholderViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel.onActivated.2.2.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            ((HeadsUpManagerImpl) notificationsPlaceholderViewModel.headsUpNotificationInteractor.headsUpRepository).unpinAll();
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

        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ NotificationsPlaceholderViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = notificationsPlaceholderViewModel;
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
                    final ReadonlyStateFlow readonlyStateFlow = this.this$0.sceneInteractor.transitionState;
                    Flow flow = new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$3$invokeSuspend$$inlined$filter$1

                        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$3$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$onActivated$2$3$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                    if (((ObservableTransitionState) obj) instanceof ObservableTransitionState.Idle) {
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
                            Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final NotificationsPlaceholderViewModel notificationsPlaceholderViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel.onActivated.2.3.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((HeadsUpManagerImpl) notificationsPlaceholderViewModel.headsUpNotificationInteractor.headsUpRepository).getClass();
                            SceneContainerFlag.isUnexpectedlyInLegacyMode();
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = NotificationsPlaceholderViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(NotificationsPlaceholderViewModel.this, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C05232(NotificationsPlaceholderViewModel.this, null), 7);
            return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(NotificationsPlaceholderViewModel.this, null), 7);
        }
    }

    public NotificationsPlaceholderViewModel(NotificationStackAppearanceInteractor notificationStackAppearanceInteractor, SceneInteractor sceneInteractor, ShadeInteractor shadeInteractor, ShadeModeInteractor shadeModeInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, RemoteInputInteractor remoteInputInteractor, FeatureFlagsClassic featureFlagsClassic, DumpManager dumpManager) {
        Object obj;
        ActivatableFlowDumperImpl activatableFlowDumperImpl = new ActivatableFlowDumperImpl(dumpManager, "NotificationsPlaceholderViewModel");
        this.$$delegate_0 = activatableFlowDumperImpl;
        this.interactor = notificationStackAppearanceInteractor;
        this.sceneInteractor = sceneInteractor;
        this.shadeInteractor = shadeInteractor;
        this.headsUpNotificationInteractor = headsUpNotificationInteractor;
        Hydrator hydrator = new Hydrator("NotificationsPlaceholderViewModel", null, 2, null);
        this.hydrator = hydrator;
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        Object obj2 = ((ShadeMode) shadeModeInteractorImpl.shadeMode.$$delegate_0.getValue()) instanceof ShadeMode.Dual ? Overlays.NotificationsShade : Scenes.Shade;
        final ReadonlyStateFlow readonlyStateFlow = shadeModeInteractorImpl.shadeMode;
        this.notificationsShadeContentKey$delegate = hydrator.hydratedStateOf("notificationsShadeContentKey", obj2, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationsPlaceholderViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationsPlaceholderViewModel;
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
                        this.this$0.getClass();
                        Object obj3 = ((ShadeMode) obj) instanceof ShadeMode.Dual ? Overlays.NotificationsShade : Scenes.Shade;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        ShadeMode shadeMode = (ShadeMode) readonlyStateFlow.$$delegate_0.getValue();
        if (shadeMode instanceof ShadeMode.Single) {
            obj = Scenes.QuickSettings;
        } else if (shadeMode instanceof ShadeMode.Split) {
            obj = Scenes.Shade;
        } else {
            if (!(shadeMode instanceof ShadeMode.Dual)) {
                throw new NoWhenBranchMatchedException();
            }
            obj = Overlays.QuickSettingsShade;
        }
        this.quickSettingsShadeContentKey$delegate = hydrator.hydratedStateOf("quickSettingsShadeContentKey", obj, new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NotificationsPlaceholderViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = notificationsPlaceholderViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj3 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj3);
                        ShadeMode shadeMode = (ShadeMode) obj;
                        this.this$0.getClass();
                        if (shadeMode instanceof ShadeMode.Single) {
                            obj2 = Scenes.QuickSettings;
                        } else if (shadeMode instanceof ShadeMode.Split) {
                            obj2 = Scenes.Shade;
                        } else {
                            if (!(shadeMode instanceof ShadeMode.Dual)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            obj2 = Overlays.QuickSettingsShade;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj2, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj3);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isCurrentGestureOverscroll$delegate = hydrator.hydratedStateOf("isCurrentGestureOverscroll", Boolean.FALSE, notificationStackAppearanceInteractor.isCurrentGestureOverscroll);
        Flags flags = Flags.INSTANCE;
        featureFlagsClassic.getClass();
        this.isHeadsUpOrAnimatingAway = (Flow) headsUpNotificationInteractor.isHeadsUpOrAnimatingAway$delegate.getValue();
        this.shadeScrimRounding = activatableFlowDumperImpl.dumpWhileCollecting(notificationStackAppearanceInteractor.shadeScrimRounding, "shadeScrimRounding");
        ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
        this.expandFraction = activatableFlowDumperImpl.dumpValue(shadeInteractorImpl.baseShadeInteractor.getAnyExpansion(), "expandFraction");
        this.shadeToQsFraction = activatableFlowDumperImpl.dumpValue(shadeInteractorImpl.baseShadeInteractor.getQsExpansion(), "shadeToQsFraction");
        this.syntheticScroll = activatableFlowDumperImpl.dumpWhileCollecting(notificationStackAppearanceInteractor.syntheticScroll, "syntheticScroll");
        this.isRemoteInputActive = remoteInputInteractor.isRemoteInputActive;
        this.remoteInputRowBottomBound = remoteInputInteractor.remoteInputRowBottomBound;
    }

    @Override // com.android.systemui.util.kotlin.ActivatableFlowDumper
    public final Object activateFlowDumper(Continuation continuation) {
        return this.$$delegate_0.activateFlowDumper(continuation);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.$$delegate_0.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        this.$$delegate_0.dumpFlows(indentingPrintWriter);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final SharedFlow dumpReplayCache(SharedFlow sharedFlow, String str) {
        return this.$$delegate_0.dumpReplayCache(sharedFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final StateFlow dumpValue(StateFlow stateFlow, String str) {
        return this.$$delegate_0.dumpValue(stateFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final Flow dumpWhileCollecting(Flow flow, String str) {
        return this.$$delegate_0.dumpWhileCollecting(flow, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (r6.$$delegate_0.activateFlowDumper(r0) == r1) goto L21;
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
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        this = (NotificationsPlaceholderViewModel) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        anonymousClass1.L$0 = null;
        anonymousClass1.label = 2;
    }
}
