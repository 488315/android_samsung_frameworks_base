package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.view.KeyEvent;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.input.key.KeyEventType;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.bouncer.ui.SettingsInteractor;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl;
import com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl;
import com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class PasswordBouncerViewModel extends AuthMethodBouncerViewModel {
    public static final Companion Companion = new Companion(null);
    public static final long DELAY_TO_FETCH_IMES;
    public final StateFlowImpl _entryBackgroundColor;
    public final StateFlowImpl _isImeSwitcherButtonVisible;
    public final StateFlowImpl _isTextFieldFocusRequested;
    public final StateFlowImpl _isWhiteBg;
    public final StateFlowImpl _password;
    public final StateFlowImpl _selectedUserId;
    public final StateFlowImpl _textColor;
    public final Context applicationContext;
    public final AuthenticationMethodModel.Password authenticationMethod;
    public final ConnectedDisplayInteractor connectedDisplayInteractor;
    public final ReadonlyStateFlow entryBackgroundColor;
    public final InputMethodInteractor inputMethodInteractor;
    public boolean isExternalDesktopWindowing;
    public final ReadonlyStateFlow isImeSwitcherButtonVisible;
    public final ReadonlyStateFlow isShowLastPassword;
    public final ReadonlyStateFlow isTextFieldFocusRequested;
    public final StateFlowImpl isTextFieldFocused;
    public final ReadonlyStateFlow isWhiteBg;
    public final KeyguardViewMediator keyguardViewMediator;
    public final Function0 onIntentionalUserInput;
    public final ReadonlyStateFlow password;
    public final BufferedChannel requests;
    public final ReadonlyStateFlow selectedUserId;
    public final SelectedUserInteractor selectedUserInteractor;
    public final ReadonlyStateFlow textColor;
    public final WallpaperManager wallpaperManager;
    public boolean wasSuccessfullyAuthenticated;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: getDELAY_TO_FETCH_IMES-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m1059getDELAY_TO_FETCH_IMESUwyO8pc$annotations() {
        }
    }

    public interface Factory {
        PasswordBouncerViewModel create(StateFlow stateFlow, Function0 function0);
    }

    public final class OnImeDismissed implements Request {
        public static final OnImeDismissed INSTANCE = new OnImeDismissed();

        private OnImeDismissed() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof OnImeDismissed);
        }

        public final int hashCode() {
            return -1774139777;
        }

        public final String toString() {
            return "OnImeDismissed";
        }
    }

    public final class OnImeSwitcherButtonClicked implements Request {
        public final int displayId;

        public OnImeSwitcherButtonClicked(int i) {
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnImeSwitcherButtonClicked) && this.displayId == ((OnImeSwitcherButtonClicked) obj).displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("OnImeSwitcherButtonClicked(displayId="));
        }
    }

    public interface Request {
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$1, reason: invalid class name */
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
            return PasswordBouncerViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
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
                    PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                    this.label = 1;
                    Companion companion = PasswordBouncerViewModel.Companion;
                    passwordBouncerViewModel.getClass();
                    if (AuthMethodBouncerViewModel.onActivated$suspendImpl(passwordBouncerViewModel, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C01512 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01512(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01512(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01512) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ChannelAsFlow channelAsFlowReceiveAsFlow = FlowKt.receiveAsFlow(this.this$0.requests);
                    final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                            Request request = (Request) obj2;
                            boolean z = request instanceof OnImeSwitcherButtonClicked;
                            PasswordBouncerViewModel passwordBouncerViewModel2 = passwordBouncerViewModel;
                            if (z) {
                                Object objShowInputMethodPicker = ((InputMethodRepositoryImpl) passwordBouncerViewModel2.inputMethodInteractor.repository).showInputMethodPicker(((OnImeSwitcherButtonClicked) request).displayId, continuation);
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                if (objShowInputMethodPicker != coroutineSingletons2) {
                                    objShowInputMethodPicker = Unit.INSTANCE;
                                }
                                return objShowInputMethodPicker == coroutineSingletons2 ? objShowInputMethodPicker : Unit.INSTANCE;
                            }
                            if (!(request instanceof OnImeDismissed)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            SharedFlowImpl sharedFlowImpl = passwordBouncerViewModel2.interactor._onImeHiddenByUser;
                            Unit unit = Unit.INSTANCE;
                            Object objEmit = sharedFlowImpl.emit(unit, continuation);
                            CoroutineSingletons coroutineSingletons3 = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (objEmit != coroutineSingletons3) {
                                objEmit = unit;
                            }
                            return objEmit == coroutineSingletons3 ? objEmit : unit;
                        }
                    };
                    this.label = 1;
                    if (channelAsFlowReceiveAsFlow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$3$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function3 {
                /* synthetic */ boolean Z$0;
                /* synthetic */ boolean Z$1;
                int label;
                final /* synthetic */ PasswordBouncerViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                    super(3, continuation);
                    this.this$0 = passwordBouncerViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean zBooleanValue = ((Boolean) obj).booleanValue();
                    boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                    anonymousClass1.Z$0 = zBooleanValue;
                    anonymousClass1.Z$1 = zBooleanValue2;
                    return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Boolean.valueOf((!this.Z$0 || this.Z$1 || this.this$0.wasSuccessfullyAuthenticated) ? false : true);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
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
                    PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(passwordBouncerViewModel.isInputEnabled, passwordBouncerViewModel.isTextFieldFocused, new AnonymousClass1(passwordBouncerViewModel, null));
                    final PasswordBouncerViewModel passwordBouncerViewModel2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.3.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Boolean bool = (Boolean) obj2;
                            bool.getClass();
                            passwordBouncerViewModel2._isTextFieldFocusRequested.updateState(null, bool);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
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
                    final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                    Flow flow = passwordBouncerViewModel.selectedUserInteractor.selectedUser;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            passwordBouncerViewModel._selectedUserId.updateState(null, new Integer(((Number) obj2).intValue()));
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$5$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                int label;

                public AnonymousClass1(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass1(continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj).intValue();
                    return new AnonymousClass1((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        PasswordBouncerViewModel.Companion.getClass();
                        long j = PasswordBouncerViewModel.DELAY_TO_FETCH_IMES;
                        this.label = 1;
                        if (DelayKt.m3469delayVtjQ1oo(j, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$5$2, reason: invalid class name and collision with other inner class name */
            final class C01532 extends SuspendLambda implements Function3 {
                /* synthetic */ int I$0;
                int label;
                final /* synthetic */ PasswordBouncerViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01532(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                    super(3, continuation);
                    this.this$0 = passwordBouncerViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    int iIntValue = ((Number) obj).intValue();
                    C01532 c01532 = new C01532(this.this$0, (Continuation) obj3);
                    c01532.I$0 = iIntValue;
                    return c01532.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i != 0) {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return obj;
                    }
                    ResultKt.throwOnFailure(obj);
                    int i2 = this.I$0;
                    InputMethodInteractor inputMethodInteractor = this.this$0.inputMethodInteractor;
                    this.label = 1;
                    Object objHasMultipleEnabledImesOrSubtypes = inputMethodInteractor.hasMultipleEnabledImesOrSubtypes(i2, this);
                    return objHasMultipleEnabledImesOrSubtypes == coroutineSingletons ? coroutineSingletons : objHasMultipleEnabledImesOrSubtypes;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.this$0.selectedUserInteractor.selectedUser, new AnonymousClass1(null)), com.android.systemui.util.kotlin.FlowKt.onSubscriberAdded(this.this$0._isImeSwitcherButtonVisible), new C01532(this.this$0, null));
                    final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.5.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Boolean bool = (Boolean) obj2;
                            bool.getClass();
                            passwordBouncerViewModel._isImeSwitcherButtonVisible.updateState(null, bool);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
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
                    final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                    ReadonlyStateFlow readonlyStateFlow = passwordBouncerViewModel.isWhiteBg;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.6.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                            PasswordBouncerViewModel passwordBouncerViewModel2 = passwordBouncerViewModel;
                            StateFlowImpl stateFlowImpl = passwordBouncerViewModel2._textColor;
                            StateFlowImpl stateFlowImpl2 = passwordBouncerViewModel2._entryBackgroundColor;
                            if (zBooleanValue) {
                                stateFlowImpl2.updateState(null, Color.m456boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_entry_background_whitebg_color))));
                                stateFlowImpl.updateState(null, Color.m456boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_text_whitebg_color))));
                            } else {
                                stateFlowImpl2.updateState(null, Color.m456boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_entry_background_color))));
                                stateFlowImpl.updateState(null, Color.m456boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_text_color))));
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
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
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0._isWhiteBg.updateState(null, Boolean.valueOf(this.this$0.wallpaperManager.semGetWallpaperColors(10).get(512L).getFontColor() == 1));
                return Unit.INSTANCE;
            }
        }

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = passwordBouncerViewModel;
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
                    final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                    Flow flow = ((ConnectedDisplayInteractorImpl) passwordBouncerViewModel.connectedDisplayInteractor).isExternalDesktopWindowing;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.8.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            passwordBouncerViewModel.isExternalDesktopWindowing = ((Boolean) obj2).booleanValue();
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
            AnonymousClass2 anonymousClass2 = PasswordBouncerViewModel.this.new AnonymousClass2(continuation);
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(PasswordBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01512(PasswordBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(PasswordBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(PasswordBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(PasswordBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(PasswordBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(PasswordBouncerViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(PasswordBouncerViewModel.this, null), 7);
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
        Duration.Companion companion = Duration.Companion;
        DELAY_TO_FETCH_IMES = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
    }

    public PasswordBouncerViewModel(BouncerInteractor bouncerInteractor, InputMethodInteractor inputMethodInteractor, SelectedUserInteractor selectedUserInteractor, StateFlow stateFlow, Function0 function0, Context context, UpdateInteractor updateInteractor, SettingsInteractor settingsInteractor, WallpaperManager wallpaperManager, KeyguardViewMediator keyguardViewMediator, ConnectedDisplayInteractor connectedDisplayInteractor) {
        super(bouncerInteractor, stateFlow, "PasswordBouncerViewModel", null, 8, null);
        this.inputMethodInteractor = inputMethodInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.onIntentionalUserInput = function0;
        this.applicationContext = context;
        this.wallpaperManager = wallpaperManager;
        this.keyguardViewMediator = keyguardViewMediator;
        this.connectedDisplayInteractor = connectedDisplayInteractor;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow("");
        this._password = stateFlowImplMutableStateFlow;
        this.password = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.authenticationMethod = AuthenticationMethodModel.Password.INSTANCE;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isImeSwitcherButtonVisible = stateFlowImplMutableStateFlow2;
        this.isImeSwitcherButtonVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this.isTextFieldFocused = stateFlowImplMutableStateFlow3;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(Boolean.valueOf(((Boolean) stateFlow.getValue()).booleanValue() && !((Boolean) stateFlowImplMutableStateFlow3.getValue()).booleanValue()));
        this._isTextFieldFocusRequested = stateFlowImplMutableStateFlow4;
        this.isTextFieldFocusRequested = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(Integer.valueOf(selectedUserInteractor.getSelectedUserId()));
        this._selectedUserId = stateFlowImplMutableStateFlow5;
        this.selectedUserId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        this.requests = ChannelKt.Channel$default(-2, null, null, 6);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._isWhiteBg = stateFlowImplMutableStateFlow6;
        this.isWhiteBg = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        this.isShowLastPassword = settingsInteractor.isShowLastPassword;
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(Color.m456boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_password_entry_background_color))));
        this._entryBackgroundColor = stateFlowImplMutableStateFlow7;
        this.entryBackgroundColor = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(Color.m456boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_password_text_color))));
        this._textColor = stateFlowImplMutableStateFlow8;
        this.textColor = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void clearInput() {
        this._password.updateState(null, "");
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final AuthenticationMethodModel getAuthenticationMethod() {
        return this.authenticationMethod;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final List getInput() {
        char[] charArray = ((String) this._password.getValue()).toCharArray();
        int length = charArray.length;
        if (length == 0) {
            return EmptyList.INSTANCE;
        }
        if (length == 1) {
            return Collections.singletonList(Character.valueOf(charArray[0]));
        }
        ArrayList arrayList = new ArrayList(charArray.length);
        for (char c : charArray) {
            arrayList.add(Character.valueOf(c));
        }
        return arrayList;
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
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
                anonymousClass1.L$0 = this;
                anonymousClass1.label = 1;
                if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                this = (PasswordBouncerViewModel) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            this.wasSuccessfullyAuthenticated = false;
            throw th;
        }
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    /* renamed from: onKeyEvent-uiMRsoQ */
    public final boolean mo1058onKeyEventuiMRsoQ(int i, int i2) {
        if (!KeyEvent.isConfirmKey(i2) || i2 == 62) {
            return false;
        }
        KeyEventType.Companion.getClass();
        if (i != KeyEventType.KeyUp) {
            return false;
        }
        ComposeBouncerFlags.INSTANCE.getClass();
        return false;
    }

    @Override // com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel
    public final void onSuccessfulAuthentication() {
        this.wasSuccessfullyAuthenticated = true;
    }
}
