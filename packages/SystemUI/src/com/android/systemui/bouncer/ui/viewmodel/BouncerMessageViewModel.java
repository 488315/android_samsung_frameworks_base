package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.bouncer.ui.HintInteractor;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.systemui.R;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1;
import com.android.systemui.util.kotlin.Utils;
import com.android.systemui.util.time.SystemClock;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes.dex */
public final class BouncerMessageViewModel extends ExclusiveActivatable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isWhiteBg;
    public final StateFlowImpl _messageColor;
    public final Context applicationContext;
    public final AuthenticationInteractor authenticationInteractor;
    public final BiometricMessageInteractor biometricMessageInteractor;
    public final BouncerInteractor bouncerInteractor;
    public final SystemClock clock;
    public final DeviceEntryBiometricsAllowedInteractor deviceEntryBiometricsAllowedInteractor;
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final DeviceEntryFaceAuthInteractor faceAuthInteractor;
    public final HintInteractor hintInteractor;
    public final StateFlowImpl hintMessage;
    public final BouncerMessageViewModel$special$$inlined$map$1 isLockoutMessagePresent;
    public final ReadonlyStateFlow isWhiteBg;
    public final StateFlowImpl lockoutMessage;
    public final StateFlowImpl message;
    public final ReadonlyStateFlow messageColor;
    public final SharedFlowImpl resetToDefault;
    public final SimBouncerInteractor simBouncerInteractor;
    public final UserSwitcherViewModel userSwitcherViewModel;
    public final WallpaperManager wallpaperManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        BouncerMessageViewModel create();
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceEntryRestrictionReason.values().length];
            try {
                iArr[DeviceEntryRestrictionReason.UserLockdown.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.DeviceNotUnlockedSinceReboot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.PolicyLockdown.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.UnattendedUpdate.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.DeviceNotUnlockedSinceMainlineUpdate.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.SecurityTimeout.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.StrongBiometricsLockedOut.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.NonStrongFaceLockedOut.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.NonStrongBiometricsSecurityTimeout.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.TrustAgentDisabled.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.AdaptiveAuthRequest.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$1, reason: invalid class name */
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
            return BouncerMessageViewModel.this.listenForHintEvents(this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$1, reason: invalid class name and case insensitive filesystem */
    final class C08201 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C08201(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BouncerMessageViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2, reason: invalid class name and case insensitive filesystem */
    final class C08212 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
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
                    final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    UserSwitcherViewModel$special$$inlined$map$1 userSwitcherViewModel$special$$inlined$map$1 = bouncerMessageViewModel.userSwitcherViewModel.selectedUser;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.onActivated.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Unit unitAccess$startLockoutCountdown = BouncerMessageViewModel.access$startLockoutCountdown(bouncerMessageViewModel);
                            return unitAccess$startLockoutCountdown == CoroutineSingletons.COROUTINE_SUSPENDED ? unitAccess$startLockoutCountdown : Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (userSwitcherViewModel$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C01432 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01432(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01432(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01432) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    this.label = 1;
                    if (BouncerMessageViewModel.access$defaultBouncerMessageInitializer(bouncerMessageViewModel, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
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
                    final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    this.label = 1;
                    Object objCollect = FlowKt.transformLatest(bouncerMessageViewModel.authenticationInteractor.authenticationMethod, new BouncerMessageViewModel$listenForSimBouncerEvents$$inlined$flatMapLatest$1(null, bouncerMessageViewModel)).collect(new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForSimBouncerEvents$3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                            MessageViewModel messageViewModel = (MessageViewModel) obj2;
                            BouncerMessageViewModel bouncerMessageViewModel2 = bouncerMessageViewModel;
                            if (messageViewModel != null) {
                                bouncerMessageViewModel2.message.updateState(null, messageViewModel);
                                return Unit.INSTANCE;
                            }
                            Object objEmit = bouncerMessageViewModel2.resetToDefault.emit(Boolean.TRUE, continuation);
                            return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
                        }
                    }, this);
                    if (objCollect != coroutineSingletons) {
                        objCollect = Unit.INSTANCE;
                    }
                    if (objCollect == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
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
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    this.label = 1;
                    int i2 = BouncerMessageViewModel.$r8$clinit;
                    bouncerMessageViewModel.getClass();
                    Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new BouncerMessageViewModel$listenForBouncerEvents$2(bouncerMessageViewModel, null), this);
                    if (objCoroutineScope != obj2) {
                        objCoroutineScope = Unit.INSTANCE;
                    }
                    if (objCoroutineScope == obj2) {
                        return obj2;
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
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
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    this.label = 1;
                    int i2 = BouncerMessageViewModel.$r8$clinit;
                    bouncerMessageViewModel.getClass();
                    Object objCollectLatest = FlowKt.collectLatest(Utils.Companion.sample(bouncerMessageViewModel.biometricMessageInteractor.faceMessage, bouncerMessageViewModel.authenticationInteractor.authenticationMethod, bouncerMessageViewModel.deviceEntryBiometricsAllowedInteractor.isFingerprintCurrentlyAllowedOnBouncer), new BouncerMessageViewModel$listenForFaceMessages$2(bouncerMessageViewModel, null), this);
                    if (objCollectLatest != obj2) {
                        objCollectLatest = Unit.INSTANCE;
                    }
                    if (objCollectLatest == obj2) {
                        return obj2;
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    this.label = 1;
                    int i2 = BouncerMessageViewModel.$r8$clinit;
                    bouncerMessageViewModel.getClass();
                    Object objCollectLatest = FlowKt.collectLatest(Utils.Companion.sample(bouncerMessageViewModel.biometricMessageInteractor.fingerprintMessage, bouncerMessageViewModel.authenticationInteractor.authenticationMethod, bouncerMessageViewModel.deviceEntryBiometricsAllowedInteractor.isFingerprintCurrentlyAllowedOnBouncer), new BouncerMessageViewModel$listenForFingerprintMessages$2(bouncerMessageViewModel, null), this);
                    if (objCollectLatest != obj2) {
                        objCollectLatest = Unit.INSTANCE;
                    }
                    if (objCollectLatest == obj2) {
                        return obj2;
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass7(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    this.label = 1;
                    if (bouncerMessageViewModel.listenForHintEvents(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass8(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = bouncerMessageViewModel.isWhiteBg;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.onActivated.2.8.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                            BouncerMessageViewModel bouncerMessageViewModel2 = bouncerMessageViewModel;
                            bouncerMessageViewModel2._messageColor.updateState(null, Color.m456boximpl(zBooleanValue ? ColorKt.Color(bouncerMessageViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_whitebg_color)) : ColorKt.Color(bouncerMessageViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_color))));
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerMessageViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerMessageViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass9(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0._isWhiteBg.updateState(null, Boolean.valueOf(this.this$0.wallpaperManager.semGetWallpaperColors(10).get(512L).getFontColor() == 1));
                return Unit.INSTANCE;
            }
        }

        public C08212(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C08212 c08212 = BouncerMessageViewModel.this.new C08212(continuation);
            c08212.L$0 = obj;
            return c08212;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08212) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01432(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(BouncerMessageViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(BouncerMessageViewModel.this, null), 7);
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
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1] */
    public BouncerMessageViewModel(Context context, BouncerInteractor bouncerInteractor, SimBouncerInteractor simBouncerInteractor, AuthenticationInteractor authenticationInteractor, UserSwitcherViewModel userSwitcherViewModel, SystemClock systemClock, BiometricMessageInteractor biometricMessageInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceUnlockedInteractor deviceUnlockedInteractor, DeviceEntryBiometricsAllowedInteractor deviceEntryBiometricsAllowedInteractor, HintInteractor hintInteractor, UpdateInteractor updateInteractor, WallpaperManager wallpaperManager) {
        this.applicationContext = context;
        this.bouncerInteractor = bouncerInteractor;
        this.simBouncerInteractor = simBouncerInteractor;
        this.authenticationInteractor = authenticationInteractor;
        this.userSwitcherViewModel = userSwitcherViewModel;
        this.clock = systemClock;
        this.biometricMessageInteractor = biometricMessageInteractor;
        this.faceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        this.deviceEntryBiometricsAllowedInteractor = deviceEntryBiometricsAllowedInteractor;
        this.hintInteractor = hintInteractor;
        this.wallpaperManager = wallpaperManager;
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.lockoutMessage = stateFlowImplMutableStateFlow;
        this.isLockoutMessagePresent = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((MessageViewModel) obj) != null);
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
                Object objCollect = stateFlowImplMutableStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.message = StateFlowKt.MutableStateFlow(null);
        this.hintMessage = StateFlowKt.MutableStateFlow(null);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isWhiteBg = stateFlowImplMutableStateFlow2;
        this.isWhiteBg = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(Color.m456boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pattern_dot_color))));
        this._messageColor = stateFlowImplMutableStateFlow3;
        this.messageColor = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.resetToDefault = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0071, code lost:
    
        if (r6.collect(r2, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$defaultBouncerMessageInitializer(final BouncerMessageViewModel bouncerMessageViewModel, ContinuationImpl continuationImpl) {
        BouncerMessageViewModel$defaultBouncerMessageInitializer$1 bouncerMessageViewModel$defaultBouncerMessageInitializer$1;
        bouncerMessageViewModel.getClass();
        if (continuationImpl instanceof BouncerMessageViewModel$defaultBouncerMessageInitializer$1) {
            bouncerMessageViewModel$defaultBouncerMessageInitializer$1 = (BouncerMessageViewModel$defaultBouncerMessageInitializer$1) continuationImpl;
            int i = bouncerMessageViewModel$defaultBouncerMessageInitializer$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                bouncerMessageViewModel$defaultBouncerMessageInitializer$1.label = i - Integer.MIN_VALUE;
            } else {
                bouncerMessageViewModel$defaultBouncerMessageInitializer$1 = new BouncerMessageViewModel$defaultBouncerMessageInitializer$1(bouncerMessageViewModel, continuationImpl);
            }
        }
        Object obj = bouncerMessageViewModel$defaultBouncerMessageInitializer$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = bouncerMessageViewModel$defaultBouncerMessageInitializer$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Log.d("BouncerMessageViewModel", "defaultBouncerMessageInitializer()");
            SharedFlowImpl sharedFlowImpl = bouncerMessageViewModel.resetToDefault;
            Boolean bool = Boolean.TRUE;
            bouncerMessageViewModel$defaultBouncerMessageInitializer$1.L$0 = bouncerMessageViewModel;
            bouncerMessageViewModel$defaultBouncerMessageInitializer$1.label = 1;
            if (sharedFlowImpl.emit(bool, bouncerMessageViewModel$defaultBouncerMessageInitializer$1) != coroutineSingletons) {
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
        bouncerMessageViewModel = (BouncerMessageViewModel) bouncerMessageViewModel$defaultBouncerMessageInitializer$1.L$0;
        ResultKt.throwOnFailure(obj);
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(bouncerMessageViewModel.authenticationInteractor.authenticationMethod, new BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1(null, bouncerMessageViewModel));
        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj2, Continuation continuation) {
                this.this$0.message.setValue((MessageViewModel) obj2);
                return Unit.INSTANCE;
            }
        };
        bouncerMessageViewModel$defaultBouncerMessageInitializer$1.L$0 = null;
        bouncerMessageViewModel$defaultBouncerMessageInitializer$1.label = 2;
    }

    public static final Unit access$startLockoutCountdown(BouncerMessageViewModel bouncerMessageViewModel) {
        bouncerMessageViewModel.getClass();
        Log.d("BouncerMessageViewModel", "startLockoutCountdown()");
        CoroutineTracingKt.launchTraced$default(CoroutineScopeKt.CoroutineScope(Dispatchers.Default), null, null, new BouncerMessageViewModel$startLockoutCountdown$2(bouncerMessageViewModel, null), 7);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CoroutineSingletons listenForHintEvents(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlySharedFlow readonlySharedFlow = this.authenticationInteractor.onAuthenticationResult;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2();
            anonymousClass1.label = 1;
            if (readonlySharedFlow.$$delegate_0.collect(anonymousClass2, anonymousClass1) == coroutineSingletons) {
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        C08201 c08201;
        if (continuation instanceof C08201) {
            c08201 = (C08201) continuation;
            int i = c08201.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08201.label = i - Integer.MIN_VALUE;
            } else {
                c08201 = new C08201(continuation);
            }
        }
        Object obj = c08201.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08201.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            C08212 c08212 = new C08212(null);
            c08201.label = 1;
            if (CoroutineScopeKt.coroutineScope(c08212, c08201) == coroutineSingletons) {
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

    public final String setSubMessage(Object obj) {
        boolean z = obj instanceof Integer;
        if (z && !Intrinsics.areEqual(obj, (Object) 0)) {
            return this.applicationContext.getString(((Integer) obj).intValue());
        }
        if (z) {
            if (Intrinsics.areEqual(obj, (Object) 0)) {
                return "";
            }
            return this.applicationContext.getString(((Number) obj).intValue());
        }
        if (!(obj instanceof Bundle)) {
            return "";
        }
        Bundle bundle = (Bundle) obj;
        return this.applicationContext.getString(bundle.getInt("SubMessageResId"), Integer.valueOf(bundle.getInt("SubMessageFormatArgs")));
    }

    public final MessageViewModel toMessage(Pair pair) {
        String string;
        Log.d("BouncerMessageViewModel", "defaultMessageBouncerMessagePair.toMessage()");
        if (((Number) pair.getFirst()).intValue() == 0) {
            string = "";
        } else {
            string = this.applicationContext.getString(((Number) pair.getFirst()).intValue());
        }
        return new MessageViewModel(string, setSubMessage(pair.getSecond()), true);
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public AnonymousClass2() {
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(boolean z, Continuation continuation) {
            BouncerMessageViewModel$listenForHintEvents$2$emit$1 bouncerMessageViewModel$listenForHintEvents$2$emit$1;
            MutableStateFlow mutableStateFlow;
            if (continuation instanceof BouncerMessageViewModel$listenForHintEvents$2$emit$1) {
                bouncerMessageViewModel$listenForHintEvents$2$emit$1 = (BouncerMessageViewModel$listenForHintEvents$2$emit$1) continuation;
                int i = bouncerMessageViewModel$listenForHintEvents$2$emit$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    bouncerMessageViewModel$listenForHintEvents$2$emit$1.label = i - Integer.MIN_VALUE;
                } else {
                    bouncerMessageViewModel$listenForHintEvents$2$emit$1 = new BouncerMessageViewModel$listenForHintEvents$2$emit$1(this, continuation);
                }
            }
            Object hint = bouncerMessageViewModel$listenForHintEvents$2$emit$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = bouncerMessageViewModel$listenForHintEvents$2$emit$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(hint);
                BouncerMessageViewModel bouncerMessageViewModel = BouncerMessageViewModel.this;
                if (z || ((Number) bouncerMessageViewModel.authenticationInteractor.failedAuthenticationAttempts.$$delegate_0.getValue()).intValue() <= 0) {
                    bouncerMessageViewModel.hintMessage.setValue(null);
                    return Unit.INSTANCE;
                }
                StateFlowImpl stateFlowImpl = bouncerMessageViewModel.hintMessage;
                bouncerMessageViewModel$listenForHintEvents$2$emit$1.L$0 = stateFlowImpl;
                bouncerMessageViewModel$listenForHintEvents$2$emit$1.label = 1;
                hint = bouncerMessageViewModel.hintInteractor.getHint(bouncerMessageViewModel$listenForHintEvents$2$emit$1);
                if (hint == coroutineSingletons) {
                    return coroutineSingletons;
                }
                mutableStateFlow = stateFlowImpl;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                mutableStateFlow = (MutableStateFlow) bouncerMessageViewModel$listenForHintEvents$2$emit$1.L$0;
                ResultKt.throwOnFailure(hint);
            }
            mutableStateFlow.setValue(hint);
            return Unit.INSTANCE;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
            return emit(((Boolean) obj).booleanValue(), continuation);
        }
    }
}
