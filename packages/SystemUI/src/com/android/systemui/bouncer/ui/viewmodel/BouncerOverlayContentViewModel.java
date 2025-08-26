package com.android.systemui.bouncer.ui.viewmodel;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.authentication.shared.model.AuthenticationWipeModel;
import com.android.systemui.authentication.shared.model.BouncerInputSide;
import com.android.systemui.bouncer.data.repository.BouncerRepository;
import com.android.systemui.bouncer.domain.interactor.BouncerActionButtonInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1;
import com.android.systemui.bouncer.shared.model.BouncerActionButtonModel;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissActionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardMediaKeyInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.user.ui.viewmodel.UserActionViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1;
import com.android.systemui.user.ui.viewmodel.UserViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class BouncerOverlayContentViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _actionButton;
    public final StateFlowImpl _authMethodViewModel;
    public final StateFlowImpl _dialogViewModel;
    public final StateFlowImpl _isFoldSplitRequired;
    public final StateFlowImpl _isInputEnabled;
    public final StateFlowImpl _isInputPreferredOnLeftSide;
    public final StateFlowImpl _isOneHandedModeSupported;
    public final StateFlowImpl _isUserSwitcherVisible;
    public final StateFlowImpl _selectedUserImage;
    public final StateFlowImpl _userSwitcherDropdown;
    public final ReadonlyStateFlow actionButton;
    public final BouncerActionButtonInteractor actionButtonInteractor;
    public final Context applicationContext;
    public final ReadonlyStateFlow authMethodViewModel;
    public final AuthenticationInteractor authenticationInteractor;
    public final BouncerActionButtonInteractor bouncerActionButtonInteractor;
    public final BouncerHapticPlayer bouncerHapticPlayer;
    public final BouncerInteractor bouncerInteractor;
    public final BouncerMessageViewModel.Factory bouncerMessageViewModelFactory;
    public final DevicePolicyManager devicePolicyManager;
    public final ReadonlyStateFlow dialogViewModel;
    public final ReadonlyStateFlow isFoldSplitRequired;
    public final ReadonlyStateFlow isInputEnabled;
    public final ReadonlyStateFlow isInputPreferredOnLeftSide;
    public final ReadonlyStateFlow isOneHandedModeSupported;
    public final ReadonlyStateFlow isUserSwitcherVisible;
    public final KeyguardDismissActionInteractor keyguardDismissActionInteractor;
    public final KeyguardMediaKeyInteractor keyguardMediaKeyInteractor;
    public final StateFlowImpl lockoutDialogMessage;
    public final Lazy message$delegate;
    public final PasswordBouncerViewModel.Factory passwordViewModelFactory;
    public final PatternBouncerViewModel.Factory patternViewModelFactory;
    public final PinBouncerViewModel.Factory pinViewModelFactory;
    public final ReadonlyStateFlow scale;
    public final ReadonlyStateFlow selectedUserImage;
    public final UserSwitcherViewModel userSwitcher;
    public final ReadonlyStateFlow userSwitcherDropdown;
    public final StateFlowImpl wipeDialogMessage;

    public final class DialogViewModel {
        public final Function0 onDismiss;
        public final String text;

        public DialogViewModel(String str, Function0 function0) {
            this.text = str;
            this.onDismiss = function0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DialogViewModel)) {
                return false;
            }
            DialogViewModel dialogViewModel = (DialogViewModel) obj;
            return Intrinsics.areEqual(this.text, dialogViewModel.text) && Intrinsics.areEqual(this.onDismiss, dialogViewModel.onDismiss);
        }

        public final int hashCode() {
            return this.onDismiss.hashCode() + (this.text.hashCode() * 31);
        }

        public final String toString() {
            return "DialogViewModel(text=" + this.text + ", onDismiss=" + this.onDismiss + ")";
        }
    }

    public interface Factory {
        BouncerOverlayContentViewModel create();
    }

    public final class UserSwitcherDropdownItemViewModel {
        public final Icon icon;
        public final Function0 onClick;
        public final Text text;

        public UserSwitcherDropdownItemViewModel(Icon icon, Text text, Function0 function0) {
            this.icon = icon;
            this.text = text;
            this.onClick = function0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof UserSwitcherDropdownItemViewModel)) {
                return false;
            }
            UserSwitcherDropdownItemViewModel userSwitcherDropdownItemViewModel = (UserSwitcherDropdownItemViewModel) obj;
            return Intrinsics.areEqual(this.icon, userSwitcherDropdownItemViewModel.icon) && Intrinsics.areEqual(this.text, userSwitcherDropdownItemViewModel.text) && Intrinsics.areEqual(this.onClick, userSwitcherDropdownItemViewModel.onClick);
        }

        public final int hashCode() {
            return this.onClick.hashCode() + ((this.text.hashCode() + (this.icon.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "UserSwitcherDropdownItemViewModel(icon=" + this.icon + ", text=" + this.text + ", onClick=" + this.onClick + ")";
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$1, reason: invalid class name */
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
            return BouncerOverlayContentViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    BouncerMessageViewModel message = this.this$0.getMessage();
                    this.label = 1;
                    if (message.activate(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$10, reason: invalid class name */
        final class AnonymousClass10 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass10(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = bouncerOverlayContentViewModel.bouncerInteractor.preferredBouncerInputSide;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.10.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            bouncerOverlayContentViewModel._isInputPreferredOnLeftSide.updateState(null, Boolean.valueOf(((BouncerInputSide) obj2) == BouncerInputSide.LEFT));
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11, reason: invalid class name */
        final class AnonymousClass11 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass11(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass11(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass11) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    final ReadonlyStateFlow readonlyStateFlow = bouncerOverlayContentViewModel.authMethodViewModel;
                    Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ BouncerOverlayContentViewModel this$0;

                            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, BouncerOverlayContentViewModel bouncerOverlayContentViewModel) {
                                this.$this_unsafeFlow = flowCollector;
                                this.this$0 = bouncerOverlayContentViewModel;
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
                                    Boolean boolValueOf = Boolean.valueOf(!(((AuthMethodBouncerViewModel) obj) instanceof PasswordBouncerViewModel));
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
                            Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, bouncerOverlayContentViewModel), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.11.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Boolean bool = (Boolean) obj2;
                            bool.getClass();
                            bouncerOverlayContentViewModel2._isFoldSplitRequired.updateState(null, bool);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12, reason: invalid class name */
        final class AnonymousClass12 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass12(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass12(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass12) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final BouncerMessageViewModel$special$$inlined$map$1 bouncerMessageViewModel$special$$inlined$map$1 = this.this$0.getMessage().isLockoutMessagePresent;
                    Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                            Object objCollect = bouncerMessageViewModel$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.12.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Boolean bool = (Boolean) obj2;
                            bool.getClass();
                            bouncerOverlayContentViewModel._isInputEnabled.updateState(null, bool);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C01462 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$2, reason: invalid class name and collision with other inner class name */
            final class C01472 extends SuspendLambda implements Function2 {
                final /* synthetic */ CoroutineScope $$this$launch;
                /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ BouncerOverlayContentViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C01472(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, CoroutineScope coroutineScope, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = bouncerOverlayContentViewModel;
                    this.$$this$launch = coroutineScope;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C01472 c01472 = new C01472(this.this$0, this.$$this$launch, continuation);
                    c01472.L$0 = obj;
                    return c01472;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C01472) create((AuthMethodBouncerViewModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        AuthMethodBouncerViewModel authMethodBouncerViewModel = (AuthMethodBouncerViewModel) this.L$0;
                        this.this$0._authMethodViewModel.setValue(authMethodBouncerViewModel);
                        if (authMethodBouncerViewModel == null) {
                            return Unit.INSTANCE;
                        }
                        this.label = 1;
                        if (authMethodBouncerViewModel.activate(this) == coroutineSingletons) {
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
            public C01462(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01462 c01462 = new C01462(this.this$0, continuation);
                c01462.L$0 = obj;
                return c01462;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01462) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    final Flow flow = bouncerOverlayContentViewModel.authenticationInteractor.authenticationMethod;
                    Flow flow2 = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ BouncerOverlayContentViewModel receiver$inlined;

                            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, BouncerOverlayContentViewModel bouncerOverlayContentViewModel) {
                                this.$this_unsafeFlow = flowCollector;
                                this.receiver$inlined = bouncerOverlayContentViewModel;
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
                                    AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) obj;
                                    BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.receiver$inlined;
                                    AuthMethodBouncerViewModel authMethodBouncerViewModelCreate = (AuthMethodBouncerViewModel) bouncerOverlayContentViewModel.authMethodViewModel.$$delegate_0.getValue();
                                    if (!Intrinsics.areEqual(authenticationMethodModel, authMethodBouncerViewModelCreate != null ? authMethodBouncerViewModelCreate.getAuthenticationMethod() : null)) {
                                        boolean z = authenticationMethodModel instanceof AuthenticationMethodModel.Pin;
                                        ReadonlyStateFlow readonlyStateFlow = bouncerOverlayContentViewModel.isInputEnabled;
                                        PinBouncerViewModel.Factory factory = bouncerOverlayContentViewModel.pinViewModelFactory;
                                        BouncerHapticPlayer bouncerHapticPlayer = bouncerOverlayContentViewModel.bouncerHapticPlayer;
                                        authMethodBouncerViewModelCreate = z ? factory.create(readonlyStateFlow, new BouncerOverlayContentViewModel$getChildViewModel$1(bouncerOverlayContentViewModel), (AuthenticationMethodModel.Pin) authenticationMethodModel, bouncerHapticPlayer) : authenticationMethodModel instanceof AuthenticationMethodModel.Sim ? factory.create(readonlyStateFlow, new BouncerOverlayContentViewModel$getChildViewModel$2(bouncerOverlayContentViewModel), (AuthenticationMethodModel.Sim) authenticationMethodModel, bouncerHapticPlayer) : authenticationMethodModel instanceof AuthenticationMethodModel.Password ? bouncerOverlayContentViewModel.passwordViewModelFactory.create(readonlyStateFlow, new BouncerOverlayContentViewModel$getChildViewModel$3(bouncerOverlayContentViewModel)) : authenticationMethodModel instanceof AuthenticationMethodModel.Pattern ? bouncerOverlayContentViewModel.patternViewModelFactory.create(bouncerHapticPlayer, readonlyStateFlow, new BouncerOverlayContentViewModel$getChildViewModel$4(bouncerOverlayContentViewModel)) : null;
                                    }
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(authMethodBouncerViewModelCreate, anonymousClass1) == coroutineSingletons) {
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
                            Object objCollect = flow.collect(new AnonymousClass2(flowCollector, bouncerOverlayContentViewModel), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    C01472 c01472 = new C01472(this.this$0, coroutineScope, null);
                    this.label = 1;
                    if (FlowKt.collectLatest(flow2, c01472, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    AuthenticationInteractor$special$$inlined$map$2 authenticationInteractor$special$$inlined$map$2 = bouncerOverlayContentViewModel.authenticationInteractor.upcomingWipe;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.3.1
                        /* JADX WARN: Removed duplicated region for block: B:11:0x004c A[PHI: r6
                          0x004c: PHI (r6v6 java.lang.String) = (r6v5 java.lang.String), (r6v9 java.lang.String) binds: [B:15:0x007f, B:9:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj2, Continuation continuation) {
                            final String string;
                            String string2;
                            AuthenticationWipeModel authenticationWipeModel = (AuthenticationWipeModel) obj2;
                            BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
                            StateFlowImpl stateFlowImpl = bouncerOverlayContentViewModel2.wipeDialogMessage;
                            if (authenticationWipeModel != null) {
                                int i2 = authenticationWipeModel.remainingAttempts;
                                int i3 = authenticationWipeModel.failedAttempts;
                                AuthenticationWipeModel.WipeTarget wipeTarget = authenticationWipeModel.wipeTarget;
                                if (i2 > 0) {
                                    string = bouncerOverlayContentViewModel2.applicationContext.getString(wipeTarget.messageIdForAlmostWipe, Integer.valueOf(i3), Integer.valueOf(i2));
                                    if (wipeTarget.equals(AuthenticationWipeModel.WipeTarget.ManagedProfile.INSTANCE) && (string2 = bouncerOverlayContentViewModel2.devicePolicyManager.getResources().getString("SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ALMOST_ERASING_PROFILE", new Supplier() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getAlmostAtWipeMessage$1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return string;
                                        }
                                    }, Integer.valueOf(i3), Integer.valueOf(i2))) != null) {
                                        string = string2;
                                    }
                                } else {
                                    string = bouncerOverlayContentViewModel2.applicationContext.getString(wipeTarget.messageIdForWipe, Integer.valueOf(i3));
                                    if (wipeTarget.equals(AuthenticationWipeModel.WipeTarget.ManagedProfile.INSTANCE) && (string2 = bouncerOverlayContentViewModel2.devicePolicyManager.getResources().getString("SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ERASING_PROFILE", new Supplier() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getWipeMessage$1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            return string;
                                        }
                                    }, Integer.valueOf(i3))) != null) {
                                    }
                                }
                            } else {
                                string = null;
                            }
                            stateFlowImpl.setValue(string);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (authenticationInteractor$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    final UserSwitcherViewModel$special$$inlined$map$1 userSwitcherViewModel$special$$inlined$map$1 = this.this$0.userSwitcher.selectedUser;
                    Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                    Drawable drawable = ((UserViewModel) obj).image;
                                    Bitmap bitmap = DrawableKt.toBitmap(drawable, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), null);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(bitmap, anonymousClass1) == coroutineSingletons) {
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
                            Object objCollect = userSwitcherViewModel$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.4.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            bouncerOverlayContentViewModel._selectedUserImage.setValue((Bitmap) obj2);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$5$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function3 {
                /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ BouncerOverlayContentViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                    super(3, continuation);
                    this.this$0 = bouncerOverlayContentViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                    anonymousClass1.L$0 = (List) obj;
                    anonymousClass1.L$1 = (List) obj2;
                    return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    List list = (List) this.L$0;
                    List list2 = (List) this.L$1;
                    List<UserViewModel> list3 = list;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                    for (UserViewModel userViewModel : list3) {
                        Icon.Loaded loaded = new Icon.Loaded(userViewModel.image, null, null, 4, null);
                        Function0 bouncerOverlayContentViewModel$onActivated$2$5$1$$ExternalSyntheticLambda0 = userViewModel.onClicked;
                        if (bouncerOverlayContentViewModel$onActivated$2$5$1$$ExternalSyntheticLambda0 == null) {
                            bouncerOverlayContentViewModel$onActivated$2$5$1$$ExternalSyntheticLambda0 = new BouncerOverlayContentViewModel$onActivated$2$5$1$$ExternalSyntheticLambda0();
                        }
                        arrayList.add(new UserSwitcherDropdownItemViewModel(loaded, userViewModel.name, bouncerOverlayContentViewModel$onActivated$2$5$1$$ExternalSyntheticLambda0));
                    }
                    List<UserActionViewModel> list4 = list2;
                    BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                    for (UserActionViewModel userActionViewModel : list4) {
                        arrayList2.add(new UserSwitcherDropdownItemViewModel(new Icon.Loaded(bouncerOverlayContentViewModel.applicationContext.getResources().getDrawable(userActionViewModel.iconResourceId), null, null, 4, null), new Text.Resource(userActionViewModel.textResourceId), userActionViewModel.onClicked));
                    }
                    return CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    UserSwitcherViewModel userSwitcherViewModel = bouncerOverlayContentViewModel.userSwitcher;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(userSwitcherViewModel.users, userSwitcherViewModel.menu, new AnonymousClass1(bouncerOverlayContentViewModel, null));
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.5.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            bouncerOverlayContentViewModel2._userSwitcherDropdown.setValue((List) obj2);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$6$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function3 {
                int label;
                final /* synthetic */ BouncerOverlayContentViewModel this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                    super(3, continuation);
                    this.this$0 = bouncerOverlayContentViewModel;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    return new AnonymousClass1(this.this$0, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return this.this$0.createDialogViewModel();
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(bouncerOverlayContentViewModel.wipeDialogMessage, bouncerOverlayContentViewModel.lockoutDialogMessage, new AnonymousClass1(bouncerOverlayContentViewModel, null));
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.6.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            bouncerOverlayContentViewModel2._dialogViewModel.setValue((DialogViewModel) obj2);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$7, reason: invalid class name */
        final class AnonymousClass7 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass7(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    Flow flow = bouncerOverlayContentViewModel.actionButtonInteractor.actionButton;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.7.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            bouncerOverlayContentViewModel._actionButton.setValue((BouncerActionButtonModel) obj2);
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$8, reason: invalid class name */
        final class AnonymousClass8 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$8$3, reason: invalid class name */
            final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                public AnonymousClass3() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Boolean bool = (Boolean) obj;
                    bool.booleanValue();
                    return new Pair(bool, (Float) obj2);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass8(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    BouncerInteractor bouncerInteractor = this.this$0.bouncerInteractor;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(bouncerInteractor.isOneHandedModeSupported, bouncerInteractor.lastRecordedLockscreenTouchPosition, AnonymousClass3.INSTANCE);
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.8.4
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Pair pair = (Pair) obj2;
                            Boolean bool = (Boolean) pair.component1();
                            boolean zBooleanValue = bool.booleanValue();
                            Float f = (Float) pair.component2();
                            BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
                            bouncerOverlayContentViewModel2._isOneHandedModeSupported.updateState(null, bool);
                            if (zBooleanValue && f != null) {
                                BouncerInputSide bouncerInputSide = f.floatValue() < ((float) (bouncerOverlayContentViewModel2.applicationContext.getResources().getDisplayMetrics().widthPixels / 2)) ? BouncerInputSide.LEFT : BouncerInputSide.RIGHT;
                                BouncerRepository bouncerRepository = bouncerOverlayContentViewModel2.bouncerInteractor.repository;
                                bouncerRepository.getClass();
                                bouncerRepository.globalSettings.putInt("one_handed_keyguard_side", bouncerInputSide.getSettingValue());
                                bouncerRepository.preferredBouncerInputSide.updateState(null, bouncerInputSide);
                            }
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

        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$9, reason: invalid class name */
        final class AnonymousClass9 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass9(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
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
                    final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                    BouncerInteractor$special$$inlined$map$1 bouncerInteractor$special$$inlined$map$1 = bouncerOverlayContentViewModel.bouncerInteractor.isUserSwitcherVisible;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.9.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Boolean bool = (Boolean) obj2;
                            bool.getClass();
                            bouncerOverlayContentViewModel._isUserSwitcherVisible.updateState(null, bool);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (bouncerInteractor$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
            AnonymousClass2 anonymousClass2 = BouncerOverlayContentViewModel.this.new AnonymousClass2(continuation);
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01462(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(BouncerOverlayContentViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass12(BouncerOverlayContentViewModel.this, null), 7);
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

    public BouncerOverlayContentViewModel(Context context, BouncerInteractor bouncerInteractor, AuthenticationInteractor authenticationInteractor, DevicePolicyManager devicePolicyManager, BouncerMessageViewModel.Factory factory, UserSwitcherViewModel userSwitcherViewModel, BouncerActionButtonInteractor bouncerActionButtonInteractor, PinBouncerViewModel.Factory factory2, PatternBouncerViewModel.Factory factory3, PasswordBouncerViewModel.Factory factory4, BouncerHapticPlayer bouncerHapticPlayer, KeyguardMediaKeyInteractor keyguardMediaKeyInteractor, BouncerActionButtonInteractor bouncerActionButtonInteractor2, KeyguardDismissActionInteractor keyguardDismissActionInteractor) {
        this.applicationContext = context;
        this.bouncerInteractor = bouncerInteractor;
        this.authenticationInteractor = authenticationInteractor;
        this.devicePolicyManager = devicePolicyManager;
        this.bouncerMessageViewModelFactory = factory;
        this.userSwitcher = userSwitcherViewModel;
        this.actionButtonInteractor = bouncerActionButtonInteractor;
        this.pinViewModelFactory = factory2;
        this.patternViewModelFactory = factory3;
        this.passwordViewModelFactory = factory4;
        this.bouncerHapticPlayer = bouncerHapticPlayer;
        this.keyguardMediaKeyInteractor = keyguardMediaKeyInteractor;
        this.bouncerActionButtonInteractor = bouncerActionButtonInteractor2;
        this.keyguardDismissActionInteractor = keyguardDismissActionInteractor;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._selectedUserImage = stateFlowImplMutableStateFlow;
        this.selectedUserImage = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.message$delegate = LazyKt__LazyJVMKt.lazy(new BouncerOverlayContentViewModel$$ExternalSyntheticLambda0(this, 2));
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._userSwitcherDropdown = stateFlowImplMutableStateFlow2;
        this.userSwitcherDropdown = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._isUserSwitcherVisible = stateFlowImplMutableStateFlow3;
        this.isUserSwitcherVisible = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._authMethodViewModel = stateFlowImplMutableStateFlow4;
        this.authMethodViewModel = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.lockoutDialogMessage = StateFlowKt.MutableStateFlow(null);
        this.wipeDialogMessage = StateFlowKt.MutableStateFlow(null);
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(createDialogViewModel());
        this._dialogViewModel = stateFlowImplMutableStateFlow5;
        this.dialogViewModel = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(null);
        this._actionButton = stateFlowImplMutableStateFlow6;
        this.actionButton = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._isOneHandedModeSupported = stateFlowImplMutableStateFlow7;
        this.isOneHandedModeSupported = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._isInputPreferredOnLeftSide = stateFlowImplMutableStateFlow8;
        this.isInputPreferredOnLeftSide = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(Boolean.valueOf(!(((AuthMethodBouncerViewModel) r5.$$delegate_0.getValue()) instanceof PasswordBouncerViewModel)));
        this._isFoldSplitRequired = stateFlowImplMutableStateFlow9;
        this.isFoldSplitRequired = FlowKt.asStateFlow(stateFlowImplMutableStateFlow9);
        this.scale = bouncerInteractor.scale;
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) authenticationInteractor.repository;
        long lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(Boolean.valueOf((authenticationRepositoryImpl.clock.elapsedRealtime() < lockoutAttemptDeadline ? Long.valueOf(lockoutAttemptDeadline) : null) == null));
        this._isInputEnabled = stateFlowImplMutableStateFlow10;
        this.isInputEnabled = FlowKt.asStateFlow(stateFlowImplMutableStateFlow10);
    }

    public static final void access$onIntentionalUserInput(BouncerOverlayContentViewModel bouncerOverlayContentViewModel) {
        bouncerOverlayContentViewModel.getMessage().resetToDefault.tryEmit(Boolean.FALSE);
        BouncerInteractor bouncerInteractor = bouncerOverlayContentViewModel.bouncerInteractor;
        bouncerInteractor.deviceEntryFaceAuthInteractor.onPrimaryBouncerUserInput();
        PowerInteractor.onUserTouch$default(bouncerInteractor.powerInteractor);
        bouncerInteractor.falsingInteractor.collector.updateFalseConfidence(FalsingClassifier.Result.passed(0.6d));
    }

    public final DialogViewModel createDialogViewModel() {
        String str = (String) this.wipeDialogMessage.getValue();
        String str2 = (String) this.lockoutDialogMessage.getValue();
        if (str != null) {
            return new DialogViewModel(str, new BouncerOverlayContentViewModel$$ExternalSyntheticLambda0(this, 0));
        }
        if (str2 != null) {
            return new DialogViewModel(str2, new BouncerOverlayContentViewModel$$ExternalSyntheticLambda0(this, 1));
        }
        return null;
    }

    public final BouncerMessageViewModel getMessage() {
        return (BouncerMessageViewModel) this.message$delegate.getValue();
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
            this.bouncerInteractor.repository.scale.updateState(null, Float.valueOf(1.0f));
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
