package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.view.KeyEvent;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.input.key.KeyEventType;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.keyguard.PinShapeAdapter;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class PinBouncerViewModel extends AuthMethodBouncerViewModel {
    public final StateFlowImpl _backspaceButtonAppearance;
    public final StateFlowImpl _confirmButtonAppearance;
    public final StateFlowImpl _digitButtonBackgroundColor;
    public final StateFlowImpl _digitButtonBackgroundColorAlpha;
    public final StateFlowImpl _digitButtonContentColor;
    public final StateFlowImpl _dotColor;
    public final StateFlowImpl _hintedPinLength;
    public final StateFlowImpl _isDigitButtonAnimationEnabled;
    public final StateFlowImpl _isWhiteBg;
    public final Context applicationContext;
    public final AuthenticationMethodModel authenticationMethod;
    public final ReadonlyStateFlow backspaceButtonAppearance;
    public final ReadonlyStateFlow confirmButtonAppearance;
    public final PinBouncerViewModel$special$$inlined$map$1 confirmButtonEnabled;
    public final ReadonlyStateFlow digitButtonBackgroundColor;
    public final ReadonlyStateFlow digitButtonBackgroundColorAlpha;
    public final ReadonlyStateFlow digitButtonContentColor;
    public final StateFlowImpl dotColor;
    public final ReadonlyStateFlow errorDialogMessage;
    public final ReadonlyStateFlow hintedPinLength;
    public final ReadonlyStateFlow isDigitButtonAnimationEnabled;
    public final ReadonlyStateFlow isLockedEsim;
    public final boolean isSimAreaVisible;
    public final StateFlowImpl isSimUnlockingDialogVisible;
    public final ReadonlyStateFlow isWhiteBg;
    public final StateFlowImpl mutablePinInput;
    public final Function0 onIntentionalUserInput;
    public final StateFlowImpl pinInput;
    public final PinShapeAdapter pinShapes;
    public final BufferedChannel requests;
    public final SimBouncerInteractor simBouncerInteractor;
    public final WallpaperManager wallpaperManager;

    public interface Factory {
        PinBouncerViewModel create(StateFlow stateFlow, Function0 function0, AuthenticationMethodModel authenticationMethodModel, BouncerHapticPlayer bouncerHapticPlayer);
    }

    public final class OnAuthenticateButtonClickedForSim implements Request {
        public static final OnAuthenticateButtonClickedForSim INSTANCE = new OnAuthenticateButtonClickedForSim();

        private OnAuthenticateButtonClickedForSim() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OnAuthenticateButtonClickedForSim);
        }

        public final int hashCode() {
            return 1881040527;
        }

        public final String toString() {
            return "OnAuthenticateButtonClickedForSim";
        }
    }

    public final class OnErrorDialogDismissed implements Request {
        public static final OnErrorDialogDismissed INSTANCE = new OnErrorDialogDismissed();

        private OnErrorDialogDismissed() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OnErrorDialogDismissed);
        }

        public final int hashCode() {
            return 533298264;
        }

        public final String toString() {
            return "OnErrorDialogDismissed";
        }
    }

    public interface Request {
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$1, reason: invalid class name */
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
            return PinBouncerViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
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
                    PinBouncerViewModel pinBouncerViewModel = this.this$0;
                    this.label = 1;
                    pinBouncerViewModel.getClass();
                    if (AuthMethodBouncerViewModel.onActivated$suspendImpl(pinBouncerViewModel, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass10(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C01562 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1, reason: invalid class name */
            public final class AnonymousClass1 implements FlowCollector {
                public final /* synthetic */ PinBouncerViewModel this$0;

                public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel) {
                    this.this$0 = pinBouncerViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Request request, Continuation continuation) throws IOException {
                    PinBouncerViewModel$onActivated$2$2$1$emit$1 pinBouncerViewModel$onActivated$2$2$1$emit$1;
                    Object objVerifySimPin;
                    if (continuation instanceof PinBouncerViewModel$onActivated$2$2$1$emit$1) {
                        pinBouncerViewModel$onActivated$2$2$1$emit$1 = (PinBouncerViewModel$onActivated$2$2$1$emit$1) continuation;
                        int i = pinBouncerViewModel$onActivated$2$2$1$emit$1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            pinBouncerViewModel$onActivated$2$2$1$emit$1.label = i - Integer.MIN_VALUE;
                        } else {
                            pinBouncerViewModel$onActivated$2$2$1$emit$1 = new PinBouncerViewModel$onActivated$2$2$1$emit$1(this, continuation);
                        }
                    }
                    Object obj = pinBouncerViewModel$onActivated$2$2$1$emit$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = pinBouncerViewModel$onActivated$2$2$1$emit$1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj);
                        boolean z = request instanceof OnErrorDialogDismissed;
                        PinBouncerViewModel pinBouncerViewModel = this.this$0;
                        if (z) {
                            ((SimBouncerRepositoryImpl) pinBouncerViewModel.simBouncerInteractor.repository).simVerificationErrorMessage.setValue(null);
                            return Unit.INSTANCE;
                        }
                        if (!(request instanceof OnAuthenticateButtonClickedForSim)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        pinBouncerViewModel.isSimUnlockingDialogVisible.updateState(null, Boolean.TRUE);
                        List input = pinBouncerViewModel.getInput();
                        pinBouncerViewModel$onActivated$2$2$1$emit$1.L$0 = this;
                        pinBouncerViewModel$onActivated$2$2$1$emit$1.label = 1;
                        SimBouncerInteractor simBouncerInteractor = pinBouncerViewModel.simBouncerInteractor;
                        simBouncerInteractor.getClass();
                        String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(input, "", null, null, null, 62);
                        if (((Boolean) ((SimBouncerRepositoryImpl) simBouncerInteractor.repository).isSimPukLocked.$$delegate_0.getValue()).booleanValue()) {
                            objVerifySimPin = simBouncerInteractor.verifySimPuk(strJoinToString$default, pinBouncerViewModel$onActivated$2$2$1$emit$1);
                            if (objVerifySimPin != coroutineSingletons) {
                                objVerifySimPin = Unit.INSTANCE;
                            }
                        } else {
                            objVerifySimPin = simBouncerInteractor.verifySimPin(strJoinToString$default, pinBouncerViewModel$onActivated$2$2$1$emit$1);
                            if (objVerifySimPin != coroutineSingletons) {
                                objVerifySimPin = Unit.INSTANCE;
                            }
                        }
                        if (objVerifySimPin == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        this = (AnonymousClass1) pinBouncerViewModel$onActivated$2$2$1$emit$1.L$0;
                        ResultKt.throwOnFailure(obj);
                    }
                    this.this$0.isSimUnlockingDialogVisible.updateState(null, Boolean.FALSE);
                    this.this$0.clearInput();
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01562(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01562(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01562) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelAsFlow channelAsFlowReceiveAsFlow = FlowKt.receiveAsFlow(this.this$0.requests);
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
                    this.label = 1;
                    if (channelAsFlowReceiveAsFlow.collect(anonymousClass1, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
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
                    final PinBouncerViewModel pinBouncerViewModel = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = pinBouncerViewModel.simBouncerInteractor.subId;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Number) obj2).intValue();
                            PinBouncerViewModel pinBouncerViewModel2 = pinBouncerViewModel;
                            pinBouncerViewModel2.simBouncerInteractor.resetSimPukUserInput();
                            pinBouncerViewModel2.clearInput();
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
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
                    PinBouncerViewModel pinBouncerViewModel = this.this$0;
                    Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = pinBouncerViewModel.isSimAreaVisible ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null) : pinBouncerViewModel.interactor.hintedPinLength;
                    final PinBouncerViewModel pinBouncerViewModel2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            pinBouncerViewModel2._hintedPinLength.setValue((Integer) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$5$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function3 {
                /* synthetic */ Object L$0;
                /* synthetic */ boolean Z$0;
                int label;
                final /* synthetic */ PinBouncerViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                    super(3, continuation);
                    this.this$0 = pinBouncerViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                    anonymousClass1.L$0 = (PinInputViewModel) obj;
                    anonymousClass1.Z$0 = zBooleanValue;
                    return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    PinInputViewModel pinInputViewModel = (PinInputViewModel) this.L$0;
                    this.this$0.getClass();
                    boolean z = CollectionsKt___CollectionsKt.last(pinInputViewModel.input) instanceof EntryToken.ClearAll;
                    return ActionButtonAppearance.Shown;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
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
                    PinBouncerViewModel pinBouncerViewModel = this.this$0;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(pinBouncerViewModel.mutablePinInput, pinBouncerViewModel.interactor.isAutoConfirmEnabled, new AnonymousClass1(pinBouncerViewModel, null));
                    final PinBouncerViewModel pinBouncerViewModel2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.5.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            pinBouncerViewModel2._backspaceButtonAppearance.setValue((ActionButtonAppearance) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$6$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function3 {
                /* synthetic */ Object L$0;
                /* synthetic */ boolean Z$0;
                int label;
                final /* synthetic */ PinBouncerViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                    super(3, continuation);
                    this.this$0 = pinBouncerViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                    anonymousClass1.L$0 = (PinInputViewModel) obj;
                    anonymousClass1.Z$0 = zBooleanValue;
                    return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    PinInputViewModel pinInputViewModel = (PinInputViewModel) this.L$0;
                    boolean z = this.Z$0;
                    this.this$0.getClass();
                    return ((CollectionsKt___CollectionsKt.last(pinInputViewModel.input) instanceof EntryToken.ClearAll) || z) ? ActionButtonAppearance.Disable : ActionButtonAppearance.Shown;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
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
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    PinBouncerViewModel pinBouncerViewModel = this.this$0;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(pinBouncerViewModel.mutablePinInput, pinBouncerViewModel.interactor.isAutoConfirmEnabled, new AnonymousClass1(pinBouncerViewModel, null));
                    final PinBouncerViewModel pinBouncerViewModel2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.6.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            pinBouncerViewModel2._confirmButtonAppearance.setValue((ActionButtonAppearance) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
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
                    final ReadonlyStateFlow readonlyStateFlow = this.this$0.interactor.isPinEnhancedPrivacyEnabled;
                    Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
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
                            Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final PinBouncerViewModel pinBouncerViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.8.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Boolean bool = (Boolean) obj2;
                            bool.getClass();
                            pinBouncerViewModel._isDigitButtonAnimationEnabled.updateState(null, bool);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = pinBouncerViewModel;
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
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final PinBouncerViewModel pinBouncerViewModel = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = pinBouncerViewModel.isWhiteBg;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.9.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).getClass();
                            PinBouncerViewModel pinBouncerViewModel2 = pinBouncerViewModel;
                            boolean zBooleanValue = ((Boolean) pinBouncerViewModel2.isWhiteBg.$$delegate_0.getValue()).booleanValue();
                            StateFlowImpl stateFlowImpl = pinBouncerViewModel2._dotColor;
                            StateFlowImpl stateFlowImpl2 = pinBouncerViewModel2._digitButtonContentColor;
                            StateFlowImpl stateFlowImpl3 = pinBouncerViewModel2._digitButtonBackgroundColorAlpha;
                            StateFlowImpl stateFlowImpl4 = pinBouncerViewModel2._digitButtonBackgroundColor;
                            if (zBooleanValue) {
                                stateFlowImpl4.updateState(null, Color.m456boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_whitebg_color))));
                                stateFlowImpl3.updateState(null, Float.valueOf(0.1f));
                                stateFlowImpl2.updateState(null, Color.m456boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_whitebg_color))));
                                stateFlowImpl.updateState(null, Color.m456boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_whitebg_color))));
                            } else {
                                stateFlowImpl4.updateState(null, Color.m456boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_color))));
                                stateFlowImpl3.updateState(null, Float.valueOf(0.2f));
                                stateFlowImpl2.updateState(null, Color.m456boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_color))));
                                stateFlowImpl.updateState(null, Color.m456boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_color))));
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = PinBouncerViewModel.this.new AnonymousClass2(continuation);
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
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01562(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(PinBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(PinBouncerViewModel.this, null), 7);
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

    /* JADX WARN: Type inference failed for: r9v8, types: [com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1] */
    public PinBouncerViewModel(Context context, BouncerInteractor bouncerInteractor, SimBouncerInteractor simBouncerInteractor, BouncerHapticPlayer bouncerHapticPlayer, StateFlow stateFlow, Function0 function0, AuthenticationMethodModel authenticationMethodModel, UpdateInteractor updateInteractor, WallpaperManager wallpaperManager) {
        super(bouncerInteractor, stateFlow, "PinBouncerViewModel", bouncerHapticPlayer, null);
        this.applicationContext = context;
        this.simBouncerInteractor = simBouncerInteractor;
        this.onIntentionalUserInput = function0;
        this.authenticationMethod = authenticationMethodModel;
        this.wallpaperManager = wallpaperManager;
        this.isSimAreaVisible = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Sim.INSTANCE);
        this.isLockedEsim = simBouncerInteractor.isLockedEsim;
        this.errorDialogMessage = simBouncerInteractor.errorDialogMessage;
        Boolean bool = Boolean.FALSE;
        this.isSimUnlockingDialogVisible = StateFlowKt.MutableStateFlow(bool);
        this.pinShapes = new PinShapeAdapter(context);
        PinInputViewModel.Companion.getClass();
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(new PinInputViewModel(Collections.singletonList(new EntryToken.ClearAll(0, 1, null))));
        this.mutablePinInput = stateFlowImplMutableStateFlow;
        this.pinInput = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._hintedPinLength = stateFlowImplMutableStateFlow2;
        this.hintedPinLength = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        ActionButtonAppearance actionButtonAppearance = ActionButtonAppearance.Hidden;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(actionButtonAppearance);
        this._backspaceButtonAppearance = stateFlowImplMutableStateFlow3;
        this.backspaceButtonAppearance = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(actionButtonAppearance);
        this._confirmButtonAppearance = stateFlowImplMutableStateFlow4;
        this.confirmButtonAppearance = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.confirmButtonEnabled = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(!((PinInputViewModel) obj).getPin().isEmpty());
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
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._isWhiteBg = stateFlowImplMutableStateFlow5;
        this.isWhiteBg = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(Color.m456boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pattern_dot_color))));
        this._dotColor = stateFlowImplMutableStateFlow6;
        this.dotColor = stateFlowImplMutableStateFlow6;
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(Color.m456boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pin_background_color))));
        this._digitButtonBackgroundColor = stateFlowImplMutableStateFlow7;
        this.digitButtonBackgroundColor = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(Float.valueOf(0.2f));
        this._digitButtonBackgroundColorAlpha = stateFlowImplMutableStateFlow8;
        this.digitButtonBackgroundColorAlpha = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(Color.m456boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pin_background_color))));
        this._digitButtonContentColor = stateFlowImplMutableStateFlow9;
        this.digitButtonContentColor = FlowKt.asStateFlow(stateFlowImplMutableStateFlow9);
        this.requests = ChannelKt.Channel$default(-2, null, null, 6);
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(Boolean.valueOf(!((Boolean) bouncerInteractor.isPinEnhancedPrivacyEnabled.$$delegate_0.getValue()).booleanValue()));
        this._isDigitButtonAnimationEnabled = stateFlowImplMutableStateFlow10;
        this.isDigitButtonAnimationEnabled = FlowKt.asStateFlow(stateFlowImplMutableStateFlow10);
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void clearInput() {
        StateFlowImpl stateFlowImpl = this.mutablePinInput;
        stateFlowImpl.updateState(null, ((PinInputViewModel) stateFlowImpl.getValue()).clearAll());
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final List getInput() {
        return ((PinInputViewModel) this.mutablePinInput.getValue()).getPin();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel, com.android.systemui.lifecycle.ExclusiveActivatable
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

    public final void onAuthenticateButtonClicked() {
        if (Intrinsics.areEqual(this.authenticationMethod, AuthenticationMethodModel.Sim.INSTANCE)) {
            ChannelResult.m3476boximpl(this.requests.mo3475trySendJP2dKIU(OnAuthenticateButtonClickedForSim.INSTANCE));
        } else {
            AuthMethodBouncerViewModel.tryAuthenticate$default(this, null, false, 1);
        }
    }

    public final void onBackspaceButtonClicked() {
        StateFlowImpl stateFlowImpl = this.mutablePinInput;
        PinInputViewModel pinInputViewModel = (PinInputViewModel) stateFlowImpl.getValue();
        if (!(CollectionsKt___CollectionsKt.last(pinInputViewModel.input) instanceof EntryToken.ClearAll)) {
            pinInputViewModel = new PinInputViewModel(CollectionsKt___CollectionsKt.take(pinInputViewModel.input, r0.size() - 1));
        }
        stateFlowImpl.updateState(null, pinInputViewModel);
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    /* renamed from: onKeyEvent-uiMRsoQ */
    public final boolean mo1058onKeyEventuiMRsoQ(int i, int i2) {
        KeyEventType.Companion companion = KeyEventType.Companion;
        companion.getClass();
        if (i == KeyEventType.KeyUp) {
            if (!KeyEvent.isConfirmKey(i2)) {
                return false;
            }
            onAuthenticateButtonClicked();
            return true;
        }
        companion.getClass();
        if (i == KeyEventType.KeyDown) {
            if (i2 == 67) {
                onBackspaceButtonClicked();
                return true;
            }
            if (7 <= i2 && i2 < 17) {
                onPinButtonClicked(i2 - 7);
                return true;
            }
            if (144 <= i2 && i2 < 154) {
                onPinButtonClicked(i2 - 144);
                return true;
            }
        }
        return false;
    }

    public final void onPinButtonClicked(int i) {
        StateFlowImpl stateFlowImpl = this.mutablePinInput;
        PinInputViewModel pinInputViewModel = (PinInputViewModel) stateFlowImpl.getValue();
        this.onIntentionalUserInput.invoke();
        Integer num = (Integer) this.hintedPinLength.$$delegate_0.getValue();
        if (((ArrayList) pinInputViewModel.getPin()).size() < (num != null ? num.intValue() : Integer.MAX_VALUE)) {
            stateFlowImpl.updateState(null, pinInputViewModel.append(i));
            AuthMethodBouncerViewModel.tryAuthenticate$default(this, null, true, 1);
        }
    }
}
