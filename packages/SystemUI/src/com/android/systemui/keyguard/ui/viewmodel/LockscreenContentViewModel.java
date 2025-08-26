package com.android.systemui.keyguard.ui.viewmodel;

import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.compose.runtime.State;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardBlueprint;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallback;
import com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallbackDelegator;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class LockscreenContentViewModel extends ExclusiveActivatable {
    public final State areNotificationsVisible$delegate;
    public final AuthController authController;
    public final State blueprintId$delegate;
    public final KeyguardClockInteractor clockInteractor;
    public final Hydrator hydrator;
    public final State isBypassEnabled$delegate;
    public final State isContentVisible$delegate;
    public final KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback;
    public final KeyguardTransitionAnimationCallbackDelegator keyguardTransitionAnimationCallbackDelegator;
    public final State notificationsPlacement$delegate;
    public final KeyguardTouchHandlingViewModel touchHandling;
    public final State unfoldTranslations$delegate;

    public interface Factory {
    }

    public final class UnfoldTranslations {
        public final float end;
        public final float start;

        /* JADX WARN: Illegal instructions before constructor call */
        public UnfoldTranslations() {
            float f = 0.0f;
            this(f, f, 3, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UnfoldTranslations)) {
                return false;
            }
            UnfoldTranslations unfoldTranslations = (UnfoldTranslations) obj;
            return Float.compare(this.start, unfoldTranslations.start) == 0 && Float.compare(this.end, unfoldTranslations.end) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.end) + (Float.hashCode(this.start) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("UnfoldTranslations(start=");
            sb.append(this.start);
            sb.append(", end=");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.end, ")", sb);
        }

        public UnfoldTranslations(float f, float f2) {
            this.start = f;
            this.end = f2;
        }

        public /* synthetic */ UnfoldTranslations(float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2);
        }
    }

    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return LockscreenContentViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ LockscreenContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(LockscreenContentViewModel lockscreenContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = lockscreenContentViewModel;
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = LockscreenContentViewModel.this.new AnonymousClass2(continuation);
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
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(LockscreenContentViewModel.this, null), 7);
                    LockscreenContentViewModel lockscreenContentViewModel = LockscreenContentViewModel.this;
                    lockscreenContentViewModel.keyguardTransitionAnimationCallbackDelegator.delegate = lockscreenContentViewModel.keyguardTransitionAnimationCallback;
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
                LockscreenContentViewModel.this.keyguardTransitionAnimationCallbackDelegator.delegate = null;
                throw th;
            }
        }
    }

    public LockscreenContentViewModel(KeyguardClockInteractor keyguardClockInteractor, KeyguardBlueprintInteractor keyguardBlueprintInteractor, AuthController authController, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, ShadeModeInteractor shadeModeInteractor, UnfoldTransitionInteractor unfoldTransitionInteractor, DeviceEntryInteractor deviceEntryInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionAnimationCallbackDelegator keyguardTransitionAnimationCallbackDelegator, KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback) {
        this.clockInteractor = keyguardClockInteractor;
        this.authController = authController;
        this.touchHandling = keyguardTouchHandlingViewModel;
        this.keyguardTransitionAnimationCallbackDelegator = keyguardTransitionAnimationCallbackDelegator;
        this.keyguardTransitionAnimationCallback = keyguardTransitionAnimationCallback;
        Hydrator hydrator = new Hydrator("LockscreenContentViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        ShadeModeInteractorImpl shadeModeInteractorImpl = (ShadeModeInteractorImpl) shadeModeInteractor;
        this.notificationsPlacement$delegate = hydrator.hydratedStateOf("notificationsPlacement", LockscreenContentViewModel$NotificationsPlacement$BelowClock.INSTANCE, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeModeInteractorImpl.shadeMode, keyguardClockInteractor.clockSize, new LockscreenContentViewModel$notificationsPlacement$2(null)));
        float f = 0.0f;
        this.unfoldTranslations$delegate = hydrator.hydratedStateOf("unfoldTranslations", new UnfoldTranslations(f, f, 3, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(unfoldTransitionInteractor.unfoldTranslationX(true), unfoldTransitionInteractor.unfoldTranslationX(false), LockscreenContentViewModel$unfoldTranslations$4.INSTANCE));
        Boolean bool = Boolean.TRUE;
        final MutableSharedFlow transitionValueFlow = keyguardTransitionInteractor.getTransitionValueFlow(KeyguardState.OCCLUDED);
        this.isContentVisible$delegate = hydrator.hydratedStateOf("isContentVisible", bool, new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() == 0.0f);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = transitionValueFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.areNotificationsVisible$delegate = hydrator.hydratedStateOf("areNotificationsVisible", Boolean.FALSE, new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardClockInteractor.clockSize, shadeModeInteractorImpl.isShadeLayoutWide, new LockscreenContentViewModel$areNotificationsVisible$2(null)));
        this.isBypassEnabled$delegate = hydrator.hydratedStateOf(deviceEntryInteractor.isBypassEnabled, "isBypassEnabled");
        String id = ((KeyguardBlueprint) keyguardBlueprintInteractor.keyguardBlueprintRepository.blueprint.getValue()).getId();
        final StateFlowImpl stateFlowImpl = keyguardBlueprintInteractor.blueprint;
        this.blueprintId$delegate = hydrator.hydratedStateOf("blueprintId", id, FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        String id = ((KeyguardBlueprint) obj).getId();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(id, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }));
    }

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
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
