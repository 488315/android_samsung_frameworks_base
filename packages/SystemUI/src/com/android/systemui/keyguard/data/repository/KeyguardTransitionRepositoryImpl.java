package com.android.systemui.keyguard.data.repository;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.shared.transition.KeyguardTransitionAnimationCallback;
import java.util.UUID;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes2.dex */
public final class KeyguardTransitionRepositoryImpl implements KeyguardTransitionRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _currentTransitionInfo;
    public final SharedFlowImpl _transitions;
    public KeyguardTransitionRepositoryImpl$startTransition$2$2$1 animatorListener;
    public volatile TransitionInfo currentTransitionInfo;
    public final ReadonlyStateFlow currentTransitionInfoInternal;
    public ValueAnimator lastAnimator;
    public TransitionStep lastStep;
    public final CoroutineDispatcher mainDispatcher;
    public final KeyguardTransitionAnimationCallback transitionCallback;
    public final Flow transitions;
    public UUID updateTransitionId;
    public final MutexImpl withContextMutex;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TransitionState.values().length];
            try {
                iArr[TransitionState.STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TransitionState.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TransitionState.CANCELED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return KeyguardTransitionRepositoryImpl.this.startTransition(null, this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ TransitionInfo $info;
        float F$0;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2$WhenMappings */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[TransitionModeOnCanceled.values().length];
                try {
                    iArr[TransitionModeOnCanceled.LAST_VALUE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[TransitionModeOnCanceled.RESET.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[TransitionModeOnCanceled.REVERSE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(TransitionInfo transitionInfo, Continuation continuation) {
            super(2, continuation);
            this.$info = transitionInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = KeyguardTransitionRepositoryImpl.this.new AnonymousClass2(this.$info, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x00ea  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x0116  */
        /* JADX WARN: Type inference failed for: r7v9, types: [android.animation.Animator$AnimatorListener, com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2$2$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            float f;
            final float f2;
            float f3;
            final ValueAnimator valueAnimator;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                KeyguardTransitionRepositoryImpl.this.withContextMutex.unlock(null);
                KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = KeyguardTransitionRepositoryImpl.this;
                TransitionStep transitionStep = keyguardTransitionRepositoryImpl.lastStep;
                KeyguardState keyguardState = transitionStep.from;
                TransitionInfo transitionInfo = this.$info;
                if (keyguardState == transitionInfo.from && transitionStep.to == transitionInfo.to) {
                    Log.i("KeyguardTransitionRepository", "Duplicate call to start the transition, rejecting: " + transitionInfo);
                    return null;
                }
                ValueAnimator valueAnimator2 = keyguardTransitionRepositoryImpl.lastAnimator;
                boolean zIsRunning = valueAnimator2 != null ? valueAnimator2.isRunning() : false;
                KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl2 = KeyguardTransitionRepositoryImpl.this;
                boolean z = (keyguardTransitionRepositoryImpl2.updateTransitionId == null || keyguardTransitionRepositoryImpl2.lastStep.transitionState == TransitionState.FINISHED) ? false : true;
                if (zIsRunning || z) {
                    Log.i("KeyguardTransitionRepository", "Transition still active: " + keyguardTransitionRepositoryImpl2.lastStep + ", canceling");
                    int i2 = WhenMappings.$EnumSwitchMapping$0[this.$info.modeOnCanceled.ordinal()];
                    if (i2 == 1) {
                        f = KeyguardTransitionRepositoryImpl.this.lastStep.value;
                    } else if (i2 == 2) {
                        f = 0.0f;
                    } else {
                        if (i2 != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        f = 1.0f - KeyguardTransitionRepositoryImpl.this.lastStep.value;
                    }
                    ValueAnimator valueAnimator3 = KeyguardTransitionRepositoryImpl.this.lastAnimator;
                    if (valueAnimator3 != null) {
                        valueAnimator3.cancel();
                    }
                    KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl3 = KeyguardTransitionRepositoryImpl.this;
                    keyguardTransitionRepositoryImpl3.lastAnimator = this.$info.animator;
                    UUID uuid = keyguardTransitionRepositoryImpl3.updateTransitionId;
                    if (uuid != null) {
                        float f4 = keyguardTransitionRepositoryImpl3.lastStep.value;
                        TransitionState transitionState = TransitionState.CANCELED;
                        this.L$0 = coroutineScope;
                        this.F$0 = f;
                        this.label = 1;
                        if (KeyguardTransitionRepositoryImpl.access$updateTransitionInternal(keyguardTransitionRepositoryImpl3, uuid, f4, transitionState) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        f3 = f;
                    } else {
                        f2 = f;
                    }
                }
                final TransitionInfo transitionInfo2 = this.$info;
                valueAnimator = transitionInfo2.animator;
                if (valueAnimator != 0) {
                    KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl4 = KeyguardTransitionRepositoryImpl.this;
                    TransitionStep transitionStep2 = new TransitionStep(transitionInfo2, f2, TransitionState.STARTED);
                    int i3 = KeyguardTransitionRepositoryImpl.$r8$clinit;
                    keyguardTransitionRepositoryImpl4.emitTransition(transitionStep2, true);
                    UUID uuidRandomUUID = UUID.randomUUID();
                    keyguardTransitionRepositoryImpl4.updateTransitionId = uuidRandomUUID;
                    return uuidRandomUUID;
                }
                final KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl5 = KeyguardTransitionRepositoryImpl.this;
                valueAnimator.setFloatValues(f2, 1.0f);
                valueAnimator.setDuration((long) ((1.0f - f2) * valueAnimator.getDuration()));
                final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2$2$updateListener$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl6 = keyguardTransitionRepositoryImpl5;
                        TransitionStep transitionStep3 = new TransitionStep(transitionInfo2, ((Float) valueAnimator4.getAnimatedValue()).floatValue(), TransitionState.RUNNING);
                        int i4 = KeyguardTransitionRepositoryImpl.$r8$clinit;
                        keyguardTransitionRepositoryImpl6.emitTransition(transitionStep3, false);
                    }
                };
                ?? r7 = new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$2$2$1
                    public final void endAnimation(float f5, TransitionState transitionState2) {
                        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl6 = keyguardTransitionRepositoryImpl5;
                        TransitionStep transitionStep3 = new TransitionStep(transitionInfo2, f5, transitionState2);
                        int i4 = KeyguardTransitionRepositoryImpl.$r8$clinit;
                        keyguardTransitionRepositoryImpl6.emitTransition(transitionStep3, false);
                        valueAnimator.removeListener(this);
                        valueAnimator.removeUpdateListener(animatorUpdateListener);
                        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl7 = keyguardTransitionRepositoryImpl5;
                        keyguardTransitionRepositoryImpl7.lastAnimator = null;
                        keyguardTransitionRepositoryImpl7.animatorListener = null;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback = keyguardTransitionRepositoryImpl5.transitionCallback;
                        TransitionInfo transitionInfo3 = transitionInfo2;
                        keyguardTransitionAnimationCallback.onAnimationCanceled(transitionInfo3.from, transitionInfo3.to);
                        endAnimation(keyguardTransitionRepositoryImpl5.lastStep.value, TransitionState.CANCELED);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback = keyguardTransitionRepositoryImpl5.transitionCallback;
                        TransitionInfo transitionInfo3 = transitionInfo2;
                        keyguardTransitionAnimationCallback.onAnimationEnded(transitionInfo3.from, transitionInfo3.to);
                        endAnimation(1.0f, TransitionState.FINISHED);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback = keyguardTransitionRepositoryImpl5.transitionCallback;
                        TransitionInfo transitionInfo3 = transitionInfo2;
                        keyguardTransitionAnimationCallback.onAnimationStarted(transitionInfo3.from, transitionInfo3.to);
                        keyguardTransitionRepositoryImpl5.emitTransition(new TransitionStep(transitionInfo2, f2, TransitionState.STARTED), false);
                    }
                };
                keyguardTransitionRepositoryImpl5.animatorListener = r7;
                valueAnimator.addListener(r7);
                valueAnimator.addUpdateListener(animatorUpdateListener);
                valueAnimator.start();
                return null;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            f3 = this.F$0;
            ResultKt.throwOnFailure(obj);
            f2 = f3;
            final TransitionInfo transitionInfo22 = this.$info;
            valueAnimator = transitionInfo22.animator;
            if (valueAnimator != 0) {
            }
        }
    }

    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$updateTransition$1, reason: invalid class name and case insensitive filesystem */
    final class C08991 extends ContinuationImpl {
        float F$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C08991(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardTransitionRepositoryImpl.this.updateTransition(null, 0.0f, null, this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$updateTransition$2, reason: invalid class name and case insensitive filesystem */
    final class C09002 extends SuspendLambda implements Function2 {
        final /* synthetic */ TransitionState $state;
        final /* synthetic */ UUID $transitionId;
        final /* synthetic */ float $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09002(UUID uuid, float f, TransitionState transitionState, Continuation continuation) {
            super(2, continuation);
            this.$transitionId = uuid;
            this.$value = f;
            this.$state = transitionState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardTransitionRepositoryImpl.this.new C09002(this.$transitionId, this.$value, this.$state, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09002) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyguardTransitionRepositoryImpl.this.withContextMutex.unlock(null);
                KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = KeyguardTransitionRepositoryImpl.this;
                UUID uuid = this.$transitionId;
                float f = this.$value;
                TransitionState transitionState = this.$state;
                this.label = 1;
                if (KeyguardTransitionRepositoryImpl.access$updateTransitionInternal(keyguardTransitionRepositoryImpl, uuid, f, transitionState) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public KeyguardTransitionRepositoryImpl(CoroutineDispatcher coroutineDispatcher, KeyguardTransitionAnimationCallback keyguardTransitionAnimationCallback) {
        this.mainDispatcher = coroutineDispatcher;
        this.transitionCallback = keyguardTransitionAnimationCallback;
        SharedFlowImpl sharedFlowImplMutableSharedFlow = SharedFlowKt.MutableSharedFlow(2, 20, BufferOverflow.DROP_OLDEST);
        this._transitions = sharedFlowImplMutableSharedFlow;
        this.transitions = FlowKt.distinctUntilChanged(sharedFlowImplMutableSharedFlow);
        this.lastStep = new TransitionStep(null, null, 0.0f, null, null, 31, null);
        this.withContextMutex = MutexKt.Mutex$default();
        KeyguardState keyguardState = KeyguardState.OFF;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new TransitionInfo("", keyguardState, keyguardState, null, null, 16, null));
        this._currentTransitionInfo = stateFlowImplMutableStateFlow;
        this.currentTransitionInfoInternal = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.currentTransitionInfo = new TransitionInfo("", keyguardState, keyguardState, null, null, 16, null);
        emitTransition(new TransitionStep(keyguardState, keyguardState, 1.0f, TransitionState.FINISHED, null, 16, null), false);
    }

    public static final Unit access$updateTransitionInternal(KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl, UUID uuid, float f, TransitionState transitionState) {
        if (!Intrinsics.areEqual(keyguardTransitionRepositoryImpl.updateTransitionId, uuid)) {
            Log.e("KeyguardTransitionRepository", "Attempting to update with old/invalid transitionId: " + uuid);
            return Unit.INSTANCE;
        }
        if (transitionState == TransitionState.FINISHED || transitionState == TransitionState.CANCELED) {
            keyguardTransitionRepositoryImpl.updateTransitionId = null;
        }
        keyguardTransitionRepositoryImpl.emitTransition(TransitionStep.copy$default(keyguardTransitionRepositoryImpl.lastStep, f, transitionState, 19), true);
        return Unit.INSTANCE;
    }

    public final void emitTransition(TransitionStep transitionStep, boolean z) {
        TransitionState transitionState = TransitionState.RUNNING;
        TransitionState transitionState2 = transitionStep.transitionState;
        if (transitionState2 != transitionState) {
            String str = z ? " (manual)" : "";
            String str2 = "Transition: " + transitionStep.from + " -> " + transitionStep.to + str;
            int iHashCode = str2.hashCode();
            int i = WhenMappings.$EnumSwitchMapping$0[transitionState2.ordinal()];
            if (i == 1) {
                Trace.beginAsyncSection(str2, iHashCode);
            } else if (i == 2 || i == 3) {
                Trace.endAsyncSection(str2, iHashCode);
            }
            Log.i("KeyguardTransitionRepository", transitionState2.name() + " transition: " + transitionStep + str);
        }
        this._transitions.tryEmit(transitionStep);
        this.lastStep = transitionStep;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0072, code lost:
    
        if (r8.lock(r0) == r1) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0090 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object startTransition(TransitionInfo transitionInfo, Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl;
        TransitionInfo transitionInfo2;
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
            this.currentTransitionInfo = transitionInfo;
            Log.d("KeyguardTransitionRepository", "(Internal) Setting current transition info: " + transitionInfo);
            MutexImpl mutexImpl = this.withContextMutex;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = transitionInfo;
            anonymousClass1.label = 1;
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                transitionInfo2 = (TransitionInfo) anonymousClass1.L$1;
                keyguardTransitionRepositoryImpl = (KeyguardTransitionRepositoryImpl) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = keyguardTransitionRepositoryImpl.mainDispatcher;
                AnonymousClass2 anonymousClass2 = keyguardTransitionRepositoryImpl.new AnonymousClass2(transitionInfo2, null);
                anonymousClass1.L$0 = null;
                anonymousClass1.L$1 = null;
                anonymousClass1.label = 3;
                Object objWithContext = BuildersKt.withContext(coroutineDispatcher, anonymousClass2, anonymousClass1);
                return objWithContext != coroutineSingletons ? coroutineSingletons : objWithContext;
            }
            transitionInfo = (TransitionInfo) anonymousClass1.L$1;
            this = (KeyguardTransitionRepositoryImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.getClass();
        TransitionInfo transitionInfo3 = transitionInfo;
        keyguardTransitionRepositoryImpl = this;
        transitionInfo2 = transitionInfo3;
        CoroutineDispatcher coroutineDispatcher2 = keyguardTransitionRepositoryImpl.mainDispatcher;
        AnonymousClass2 anonymousClass22 = keyguardTransitionRepositoryImpl.new AnonymousClass2(transitionInfo2, null);
        anonymousClass1.L$0 = null;
        anonymousClass1.L$1 = null;
        anonymousClass1.label = 3;
        Object objWithContext2 = BuildersKt.withContext(coroutineDispatcher2, anonymousClass22, anonymousClass1);
        if (objWithContext2 != coroutineSingletons) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0075, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r10, r4, r0) == r1) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateTransition(UUID uuid, float f, TransitionState transitionState, Continuation continuation) {
        C08991 c08991;
        if (continuation instanceof C08991) {
            c08991 = (C08991) continuation;
            int i = c08991.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08991.label = i - Integer.MIN_VALUE;
            } else {
                c08991 = new C08991(continuation);
            }
        }
        Object obj = c08991.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08991.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MutexImpl mutexImpl = this.withContextMutex;
            c08991.L$0 = this;
            c08991.L$1 = uuid;
            c08991.L$2 = transitionState;
            c08991.F$0 = f;
            c08991.label = 1;
            if (mutexImpl.lock(c08991) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        f = c08991.F$0;
        transitionState = (TransitionState) c08991.L$2;
        uuid = (UUID) c08991.L$1;
        this = (KeyguardTransitionRepositoryImpl) c08991.L$0;
        ResultKt.throwOnFailure(obj);
        KeyguardTransitionRepositoryImpl keyguardTransitionRepositoryImpl = this;
        UUID uuid2 = uuid;
        float f2 = f;
        TransitionState transitionState2 = transitionState;
        CoroutineDispatcher coroutineDispatcher = keyguardTransitionRepositoryImpl.mainDispatcher;
        C09002 c09002 = keyguardTransitionRepositoryImpl.new C09002(uuid2, f2, transitionState2, null);
        c08991.L$0 = null;
        c08991.L$1 = null;
        c08991.L$2 = null;
        c08991.label = 2;
    }
}
