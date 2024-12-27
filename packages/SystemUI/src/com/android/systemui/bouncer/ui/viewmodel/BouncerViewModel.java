package com.android.systemui.bouncer.ui.viewmodel;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.data.repository.BouncerRepository;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$2;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlagsImpl;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BouncerViewModel {
    public final ReadonlyStateFlow actionButton;
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final ReadonlyStateFlow authMethodViewModel;
    public final AuthenticationInteractor authenticationInteractor;
    public final BouncerInteractor bouncerInteractor;
    public ContextScope childViewModelScope;
    public final ReadonlyStateFlow destinationScenes;
    public final DevicePolicyManager devicePolicyManager;
    public final ReadonlyStateFlow dialogViewModel;
    public final InputMethodInteractor inputMethodInteractor;
    public final ReadonlyStateFlow isFoldSplitRequired;
    public final ReadonlyStateFlow isInputEnabled;
    public final ReadonlyStateFlow isSideBySideSupported;
    public final StateFlowImpl lockoutDialogMessage;
    public final CoroutineDispatcher mainDispatcher;
    public final BouncerMessageViewModel message;
    public final ReadonlyStateFlow selectedUserImage;
    public final SelectedUserInteractor selectedUserInteractor;
    public final SimBouncerInteractor simBouncerInteractor;
    public final ReadonlyStateFlow userSwitcherDropdown;
    public final StateFlowImpl wipeDialogMessage;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public BouncerViewModel(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, BouncerInteractor bouncerInteractor, InputMethodInteractor inputMethodInteractor, SimBouncerInteractor simBouncerInteractor, AuthenticationInteractor authenticationInteractor, SelectedUserInteractor selectedUserInteractor, DevicePolicyManager devicePolicyManager, BouncerMessageViewModel bouncerMessageViewModel, ComposeBouncerFlags composeBouncerFlags, final Flow flow, Flow flow2, Flow flow3, Flow flow4) {
        this.applicationContext = context;
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.bouncerInteractor = bouncerInteractor;
        this.inputMethodInteractor = inputMethodInteractor;
        this.simBouncerInteractor = simBouncerInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        Flow flow5 = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.user.ui.viewmodel.UserViewModel r5 = (com.android.systemui.user.ui.viewmodel.UserViewModel) r5
                        android.graphics.drawable.Drawable r5 = r5.image
                        android.graphics.Bitmap r5 = androidx.core.graphics.drawable.DrawableKt.toBitmap$default(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        FlowKt.stateIn(flow5, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        final BouncerInteractor$special$$inlined$map$2 bouncerInteractor$special$$inlined$map$2 = bouncerInteractor.dismissDestination;
        this.destinationScenes = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerViewModel receiver$inlined;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BouncerViewModel bouncerViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.receiver$inlined = bouncerViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel r6 = r4.receiver$inlined
                        r6.getClass()
                        java.util.Map r5 = com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel.destinationSceneMap(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), destinationSceneMap(Scenes.Lockscreen));
        this.message = bouncerMessageViewModel;
        FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow2, flow3, new BouncerViewModel$userSwitcherDropdown$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), EmptyList.INSTANCE);
        final Flow flow6 = authenticationInteractor.authenticationMethod;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerViewModel receiver$inlined;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BouncerViewModel bouncerViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.receiver$inlined = bouncerViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                    /*
                        Method dump skipped, instructions count: 222
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.authMethodViewModel = stateIn;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.lockoutDialogMessage = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this.wipeDialogMessage = MutableStateFlow2;
        FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow2, MutableStateFlow, new BouncerViewModel$dialogViewModel$1(this, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), createDialogViewModel());
        FlowKt.stateIn(flow4, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        Flow flow7 = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerViewModel this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BouncerViewModel bouncerViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = bouncerViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L60
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel r5 = (com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel) r5
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel r6 = r4.this$0
                        com.android.systemui.bouncer.domain.interactor.BouncerInteractor r6 = r6.bouncerInteractor
                        com.android.systemui.bouncer.data.repository.BouncerRepository r6 = r6.repository
                        r6.getClass()
                        com.android.systemui.flags.ResourceBooleanFlag r2 = com.android.systemui.flags.Flags.FULL_SCREEN_USER_SWITCHER
                        com.android.systemui.flags.FeatureFlagsClassic r6 = r6.flags
                        com.android.systemui.flags.FeatureFlagsClassicRelease r6 = (com.android.systemui.flags.FeatureFlagsClassicRelease) r6
                        boolean r6 = r6.isEnabled(r2)
                        if (r6 != 0) goto L50
                        boolean r5 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel
                        if (r5 != 0) goto L4e
                        goto L50
                    L4e:
                        r5 = 0
                        goto L51
                    L50:
                        r5 = r3
                    L51:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L60
                        return r1
                    L60:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        AuthMethodBouncerViewModel authMethodBouncerViewModel = (AuthMethodBouncerViewModel) stateIn.$$delegate_0.getValue();
        BouncerRepository bouncerRepository = bouncerInteractor.repository;
        bouncerRepository.getClass();
        FlowKt.stateIn(flow7, coroutineScope, WhileSubscribed$default, Boolean.valueOf(((FeatureFlagsClassicRelease) bouncerRepository.flags).isEnabled(Flags.FULL_SCREEN_USER_SWITCHER) || !(authMethodBouncerViewModel instanceof PasswordBouncerViewModel)));
        FlowKt.stateIn(new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BouncerViewModel this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BouncerViewModel bouncerViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = bouncerViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel r5 = (com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel) r5
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel r6 = r4.this$0
                        r6.getClass()
                        boolean r5 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(!(((AuthMethodBouncerViewModel) r12.getValue()) instanceof PasswordBouncerViewModel)));
        final BouncerMessageViewModel$special$$inlined$map$1 bouncerMessageViewModel$special$$inlined$map$1 = bouncerMessageViewModel.isLockoutMessagePresent;
        Flow flow8 = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StartedWhileSubscribed WhileSubscribed$default2 = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        AuthenticationRepositoryImpl authenticationRepositoryImpl = (AuthenticationRepositoryImpl) authenticationInteractor.repository;
        long lockoutAttemptDeadline = authenticationRepositoryImpl.lockPatternUtils.getLockoutAttemptDeadline(authenticationRepositoryImpl.getSelectedUserId());
        this.isInputEnabled = FlowKt.stateIn(flow8, coroutineScope, WhileSubscribed$default2, Boolean.valueOf((authenticationRepositoryImpl.clock.elapsedRealtime() < lockoutAttemptDeadline ? Long.valueOf(lockoutAttemptDeadline) : null) == null));
        ((ComposeBouncerFlagsImpl) composeBouncerFlags).getClass();
        com.android.systemui.Flags.sceneContainer();
        com.android.systemui.Flags.FEATURE_FLAGS.getClass();
    }

    public static final void access$onIntentionalUserInput(BouncerViewModel bouncerViewModel) {
        bouncerViewModel.message.resetToDefault.tryEmit(Unit.INSTANCE);
        BouncerInteractor bouncerInteractor = bouncerViewModel.bouncerInteractor;
        bouncerInteractor.deviceEntryFaceAuthInteractor.onPrimaryBouncerUserInput();
        PowerInteractor.onUserTouch$default(bouncerInteractor.powerInteractor);
        bouncerInteractor.falsingInteractor.collector.updateFalseConfidence(FalsingClassifier.Result.passed(0.6d));
    }

    public static Map destinationSceneMap(SceneKey sceneKey) {
        return MapsKt__MapsKt.mapOf(new Pair(Back.INSTANCE, new UserActionResult(sceneKey, null, 2, null)), new Pair(new Swipe(SwipeDirection.Down, 0, null, 6, null), new UserActionResult(sceneKey, null, 2, null)));
    }

    public final DialogViewModel createDialogViewModel() {
        String str = (String) this.wipeDialogMessage.getValue();
        String str2 = (String) this.lockoutDialogMessage.getValue();
        if (str != null) {
            return new DialogViewModel(str, new Function0() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$createDialogViewModel$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BouncerViewModel.this.wipeDialogMessage.setValue(null);
                    return Unit.INSTANCE;
                }
            });
        }
        if (str2 != null) {
            return new DialogViewModel(str2, new Function0() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$createDialogViewModel$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BouncerViewModel.this.lockoutDialogMessage.setValue(null);
                    return Unit.INSTANCE;
                }
            });
        }
        return null;
    }
}
