package com.android.systemui.user.ui.binder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.binder.TextViewBinder;
import com.android.systemui.user.UserSwitcherPopupMenu;
import com.android.systemui.user.UserSwitcherRootView;
import com.android.systemui.user.shared.model.UserActionModel;
import com.android.systemui.user.ui.binder.UserSwitcherViewBinder;
import com.android.systemui.user.ui.viewmodel.UserActionViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$4;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$5;
import com.android.systemui.user.ui.viewmodel.UserViewModel;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;

final class UserSwitcherViewBinder$bind$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $addButton;
    final /* synthetic */ Flow $flowWidget;
    final /* synthetic */ UserSwitcherRootView $gridContainerView;
    final /* synthetic */ LayoutInflater $layoutInflater;
    final /* synthetic */ Function0 $onFinish;
    final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
    final /* synthetic */ UserSwitcherViewBinder.MenuAdapter $popupMenuAdapter;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ UserSwitcherViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ Function0 $onFinish;
        final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
        final /* synthetic */ UserSwitcherViewModel $viewModel;
        int label;

        /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1, reason: invalid class name and collision with other inner class name */
        final class C02681 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function0 $onFinish;
            final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
            final /* synthetic */ UserSwitcherViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02691 extends SuspendLambda implements Function2 {
                final /* synthetic */ Function0 $onFinish;
                final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                int label;

                public C02691(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, Function0 function0, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = userSwitcherViewModel;
                    this.$popupMenu = ref$ObjectRef;
                    this.$onFinish = function0;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02691(this.$viewModel, this.$popupMenu, this.$onFinish, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02691) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = this.$viewModel.isFinishRequested;
                        kotlinx.coroutines.flow.Flow flow = new kotlinx.coroutines.flow.Flow() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1

                            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                    /*
                                        r4 = this;
                                        boolean r0 = r6 instanceof com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r6
                                        com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1$2$1
                                        r0.<init>(r6)
                                    L18:
                                        java.lang.Object r6 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        goto L46
                                    L27:
                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                        r4.<init>(r5)
                                        throw r4
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        r6 = r5
                                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                                        boolean r6 = r6.booleanValue()
                                        if (r6 == 0) goto L46
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                        java.lang.Object r4 = r4.emit(r5, r0)
                                        if (r4 != r1) goto L46
                                        return r1
                                    L46:
                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                        return r4
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object collect = kotlinx.coroutines.flow.Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        };
                        final Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef = this.$popupMenu;
                        final Function0 function0 = this.$onFinish;
                        final UserSwitcherViewModel userSwitcherViewModel = this.$viewModel;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.1.1.1.2
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Boolean) obj2).getClass();
                                UserSwitcherPopupMenu userSwitcherPopupMenu = (UserSwitcherPopupMenu) ref$ObjectRef.element;
                                if (userSwitcherPopupMenu != null) {
                                    userSwitcherPopupMenu.dismiss();
                                }
                                function0.invoke();
                                UserSwitcherViewModel userSwitcherViewModel2 = userSwitcherViewModel;
                                StateFlowImpl stateFlowImpl = userSwitcherViewModel2.hasCancelButtonBeenClicked;
                                Boolean bool = Boolean.FALSE;
                                stateFlowImpl.updateState(null, bool);
                                userSwitcherViewModel2.isFinishRequiredDueToExecutedAction.updateState(null, bool);
                                userSwitcherViewModel2.userSwitched.updateState(null, bool);
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

            public C02681(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, Function0 function0, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = userSwitcherViewModel;
                this.$popupMenu = ref$ObjectRef;
                this.$onFinish = function0;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02681 c02681 = new C02681(this.$viewModel, this.$popupMenu, this.$onFinish, continuation);
                c02681.L$0 = obj;
                return c02681;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02681) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new C02691(this.$viewModel, this.$popupMenu, this.$onFinish, null), 3);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(LifecycleOwner lifecycleOwner, UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, Function0 function0, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$viewModel = userSwitcherViewModel;
            this.$popupMenu = ref$ObjectRef;
            this.$onFinish = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$repeatWhenAttached, this.$viewModel, this.$popupMenu, this.$onFinish, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C02681 c02681 = new C02681(this.$viewModel, this.$popupMenu, this.$onFinish, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c02681, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ View $addButton;
        final /* synthetic */ Flow $flowWidget;
        final /* synthetic */ UserSwitcherRootView $gridContainerView;
        final /* synthetic */ LayoutInflater $layoutInflater;
        final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
        final /* synthetic */ UserSwitcherViewBinder.MenuAdapter $popupMenuAdapter;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ UserSwitcherViewModel $viewModel;
        int label;

        /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $addButton;
            final /* synthetic */ Flow $flowWidget;
            final /* synthetic */ UserSwitcherRootView $gridContainerView;
            final /* synthetic */ LayoutInflater $layoutInflater;
            final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
            final /* synthetic */ UserSwitcherViewBinder.MenuAdapter $popupMenuAdapter;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ UserSwitcherViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C02701 extends SuspendLambda implements Function2 {
                final /* synthetic */ View $addButton;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                int label;

                public C02701(UserSwitcherViewModel userSwitcherViewModel, View view, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = userSwitcherViewModel;
                    this.$addButton = view;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02701(this.$viewModel, this.$addButton, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02701) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        UserSwitcherViewModel$special$$inlined$map$5 userSwitcherViewModel$special$$inlined$map$5 = this.$viewModel.isOpenMenuButtonVisible;
                        final View view = this.$addButton;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                view.setVisibility(((Boolean) obj2).booleanValue() ? 0 : 8);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (userSwitcherViewModel$special$$inlined$map$5.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C02722 extends SuspendLambda implements Function2 {
                final /* synthetic */ View $addButton;
                final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
                final /* synthetic */ UserSwitcherViewBinder.MenuAdapter $popupMenuAdapter;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                int label;

                public C02722(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, ViewGroup viewGroup, View view, UserSwitcherViewBinder.MenuAdapter menuAdapter, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = userSwitcherViewModel;
                    this.$popupMenu = ref$ObjectRef;
                    this.$view = viewGroup;
                    this.$addButton = view;
                    this.$popupMenuAdapter = menuAdapter;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02722(this.$viewModel, this.$popupMenu, this.$view, this.$addButton, this.$popupMenuAdapter, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02722) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final UserSwitcherViewModel userSwitcherViewModel = this.$viewModel;
                        StateFlowImpl stateFlowImpl = userSwitcherViewModel.isMenuVisible;
                        final Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef = this.$popupMenu;
                        final ViewGroup viewGroup = this.$view;
                        final View view = this.$addButton;
                        final UserSwitcherViewBinder.MenuAdapter menuAdapter = this.$popupMenuAdapter;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                UserSwitcherPopupMenu userSwitcherPopupMenu;
                                UserSwitcherPopupMenu userSwitcherPopupMenu2;
                                boolean booleanValue = ((Boolean) obj2).booleanValue();
                                Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                                if (booleanValue && ((userSwitcherPopupMenu2 = (UserSwitcherPopupMenu) ref$ObjectRef2.element) == null || !userSwitcherPopupMenu2.isShowing())) {
                                    UserSwitcherPopupMenu userSwitcherPopupMenu3 = (UserSwitcherPopupMenu) ref$ObjectRef2.element;
                                    if (userSwitcherPopupMenu3 != null) {
                                        userSwitcherPopupMenu3.dismiss();
                                    }
                                    final ViewGroup viewGroup2 = viewGroup;
                                    final View view2 = view;
                                    final Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                                    final UserSwitcherViewBinder.MenuAdapter menuAdapter2 = menuAdapter;
                                    final UserSwitcherViewModel userSwitcherViewModel2 = userSwitcherViewModel;
                                    viewGroup2.post(new Runnable() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.2.1.1

                                        /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$2$1$1$1, reason: invalid class name and collision with other inner class name */
                                        final /* synthetic */ class C02751 extends FunctionReferenceImpl implements Function0 {
                                            public C02751(Object obj) {
                                                super(0, obj, UserSwitcherViewModel.class, "onMenuClosed", "onMenuClosed()V", 0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                ((UserSwitcherViewModel) this.receiver)._isMenuVisible.updateState(null, Boolean.FALSE);
                                                return Unit.INSTANCE;
                                            }
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef3;
                                            UserSwitcherViewBinder userSwitcherViewBinder = UserSwitcherViewBinder.INSTANCE;
                                            Context context = viewGroup2.getContext();
                                            View view3 = view2;
                                            UserSwitcherViewBinder.MenuAdapter menuAdapter3 = menuAdapter2;
                                            final C02751 c02751 = new C02751(userSwitcherViewModel2);
                                            userSwitcherViewBinder.getClass();
                                            ?? userSwitcherPopupMenu4 = new UserSwitcherPopupMenu(context);
                                            userSwitcherPopupMenu4.setDropDownGravity(8388613);
                                            userSwitcherPopupMenu4.setAnchorView(view3);
                                            userSwitcherPopupMenu4.setAdapter(menuAdapter3);
                                            userSwitcherPopupMenu4.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$createAndShowPopupMenu$1$1
                                                @Override // android.widget.PopupWindow.OnDismissListener
                                                public final void onDismiss() {
                                                    Function0.this.invoke();
                                                }
                                            });
                                            userSwitcherPopupMenu4.show();
                                            ref$ObjectRef4.element = userSwitcherPopupMenu4;
                                        }
                                    });
                                } else if (!booleanValue && (userSwitcherPopupMenu = (UserSwitcherPopupMenu) ref$ObjectRef2.element) != null && userSwitcherPopupMenu.isShowing()) {
                                    UserSwitcherPopupMenu userSwitcherPopupMenu4 = (UserSwitcherPopupMenu) ref$ObjectRef2.element;
                                    if (userSwitcherPopupMenu4 != null) {
                                        userSwitcherPopupMenu4.dismiss();
                                    }
                                    ref$ObjectRef2.element = null;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ UserSwitcherViewBinder.MenuAdapter $popupMenuAdapter;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                int label;

                public AnonymousClass3(UserSwitcherViewModel userSwitcherViewModel, UserSwitcherViewBinder.MenuAdapter menuAdapter, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = userSwitcherViewModel;
                    this.$popupMenuAdapter = menuAdapter;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$viewModel, this.$popupMenuAdapter, continuation);
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
                        UserSwitcherViewModel$special$$inlined$map$4 userSwitcherViewModel$special$$inlined$map$4 = this.$viewModel.menu;
                        final UserSwitcherViewBinder.MenuAdapter menuAdapter = this.$popupMenuAdapter;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                UserSwitcherViewBinder.MenuAdapter menuAdapter2 = UserSwitcherViewBinder.MenuAdapter.this;
                                menuAdapter2.getClass();
                                List list = (List) obj2;
                                ArrayList arrayList = new ArrayList();
                                for (Object obj3 : list) {
                                    if (((UserActionViewModel) obj3).viewKey != UserActionModel.NAVIGATE_TO_USER_MANAGEMENT.ordinal()) {
                                        arrayList.add(obj3);
                                    }
                                }
                                ArrayList arrayList2 = new ArrayList();
                                for (Object obj4 : list) {
                                    if (((UserActionViewModel) obj4).viewKey == UserActionModel.NAVIGATE_TO_USER_MANAGEMENT.ordinal()) {
                                        arrayList2.add(obj4);
                                    }
                                }
                                menuAdapter2.sections = CollectionsKt__CollectionsKt.listOf(arrayList, arrayList2);
                                menuAdapter2.notifyDataSetChanged();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (userSwitcherViewModel$special$$inlined$map$4.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$4, reason: invalid class name */
            final class AnonymousClass4 extends SuspendLambda implements Function2 {
                final /* synthetic */ Flow $flowWidget;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                int label;

                public AnonymousClass4(UserSwitcherViewModel userSwitcherViewModel, Flow flow, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = userSwitcherViewModel;
                    this.$flowWidget = flow;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass4(this.$viewModel, this.$flowWidget, continuation);
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
                        UserSwitcherViewModel$special$$inlined$map$3 userSwitcherViewModel$special$$inlined$map$3 = this.$viewModel.maximumUserColumns;
                        final Flow flow = this.$flowWidget;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.4.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                Flow flow2 = Flow.this;
                                flow2.mFlow.mMaxElementsWrap = intValue;
                                flow2.requestLayout();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (userSwitcherViewModel$special$$inlined$map$3.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ Flow $flowWidget;
                final /* synthetic */ UserSwitcherRootView $gridContainerView;
                final /* synthetic */ LayoutInflater $layoutInflater;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                int label;

                public AnonymousClass5(UserSwitcherViewModel userSwitcherViewModel, UserSwitcherRootView userSwitcherRootView, Flow flow, LayoutInflater layoutInflater, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = userSwitcherViewModel;
                    this.$gridContainerView = userSwitcherRootView;
                    this.$flowWidget = flow;
                    this.$layoutInflater = layoutInflater;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass5(this.$viewModel, this.$gridContainerView, this.$flowWidget, this.$layoutInflater, this.$view, continuation);
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
                        UserSwitcherViewModel$special$$inlined$map$2 userSwitcherViewModel$special$$inlined$map$2 = this.$viewModel.users;
                        final UserSwitcherRootView userSwitcherRootView = this.$gridContainerView;
                        final Flow flow = this.$flowWidget;
                        final LayoutInflater layoutInflater = this.$layoutInflater;
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.5.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Flow flow2;
                                View inflate;
                                List<UserViewModel> list = (List) obj2;
                                UserSwitcherRootView userSwitcherRootView2 = UserSwitcherRootView.this;
                                FilteringSequence filter = SequencesKt___SequencesKt.filter(ConvenienceExtensionsKt.getChildren(userSwitcherRootView2), new Function1() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$5$1$viewPool$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj3) {
                                        return Boolean.valueOf(Intrinsics.areEqual(((View) obj3).getTag(), "user_view"));
                                    }
                                });
                                ArrayList arrayList = new ArrayList();
                                Iterator it = filter.iterator();
                                while (true) {
                                    FilteringSequence$iterator$1 filteringSequence$iterator$1 = (FilteringSequence$iterator$1) it;
                                    if (!filteringSequence$iterator$1.hasNext()) {
                                        break;
                                    }
                                    arrayList.add(filteringSequence$iterator$1.next());
                                }
                                Iterator it2 = arrayList.iterator();
                                while (true) {
                                    boolean hasNext = it2.hasNext();
                                    flow2 = flow;
                                    if (!hasNext) {
                                        break;
                                    }
                                    View view = (View) it2.next();
                                    userSwitcherRootView2.removeView(view);
                                    flow2.removeView(view);
                                }
                                LayoutInflater layoutInflater2 = layoutInflater;
                                ViewGroup viewGroup2 = viewGroup;
                                for (final UserViewModel userViewModel : list) {
                                    if (!arrayList.isEmpty()) {
                                        inflate = (View) arrayList.remove(0);
                                    } else {
                                        inflate = layoutInflater2.inflate(R.layout.user_switcher_fullscreen_item, viewGroup2, false);
                                        inflate.setTag("user_view");
                                    }
                                    inflate.setId(View.generateViewId());
                                    userSwitcherRootView2.addView(inflate);
                                    flow2.addView(inflate);
                                    UserViewBinder.INSTANCE.getClass();
                                    TextViewBinder textViewBinder = TextViewBinder.INSTANCE;
                                    TextView textView = (TextView) inflate.requireViewById(R.id.user_switcher_text);
                                    Text text = userViewModel.name;
                                    textViewBinder.getClass();
                                    TextViewBinder.bind(textView, text);
                                    ImageView imageView = (ImageView) inflate.requireViewById(R.id.user_switcher_icon);
                                    Context context = inflate.getContext();
                                    Resources resources = context.getResources();
                                    Resources.Theme theme = context.getTheme();
                                    ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
                                    Drawable drawable = resources.getDrawable(R.drawable.user_switcher_icon_large, theme);
                                    if (drawable == null) {
                                        throw new IllegalStateException("Required value was null.".toString());
                                    }
                                    LayerDrawable layerDrawable = (LayerDrawable) drawable.mutate();
                                    if (userViewModel.isSelectionMarkerVisible) {
                                        ((GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ring)).setStroke(context.getResources().getDimensionPixelSize(R.dimen.user_switcher_icon_selected_width), Utils.getColorAttrDefaultColor(context, android.R.^attr-private.colorAccentPrimary, 0));
                                    }
                                    layerDrawable.setDrawableByLayerId(R.id.user_avatar, userViewModel.image);
                                    imageView.setImageDrawable(layerDrawable);
                                    inflate.setAlpha(userViewModel.alpha);
                                    if (userViewModel.onClicked != null) {
                                        inflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserViewBinder$bind$1
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view2) {
                                                UserViewModel.this.onClicked.invoke();
                                            }
                                        });
                                    } else {
                                        inflate.setOnClickListener(null);
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (userSwitcherViewModel$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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

            public AnonymousClass1(UserSwitcherViewModel userSwitcherViewModel, View view, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, ViewGroup viewGroup, UserSwitcherViewBinder.MenuAdapter menuAdapter, Flow flow, UserSwitcherRootView userSwitcherRootView, LayoutInflater layoutInflater, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = userSwitcherViewModel;
                this.$addButton = view;
                this.$popupMenu = ref$ObjectRef;
                this.$view = viewGroup;
                this.$popupMenuAdapter = menuAdapter;
                this.$flowWidget = flow;
                this.$gridContainerView = userSwitcherRootView;
                this.$layoutInflater = layoutInflater;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$addButton, this.$popupMenu, this.$view, this.$popupMenuAdapter, this.$flowWidget, this.$gridContainerView, this.$layoutInflater, continuation);
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
                BuildersKt.launch$default(coroutineScope, null, null, new C02701(this.$viewModel, this.$addButton, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new C02722(this.$viewModel, this.$popupMenu, this.$view, this.$addButton, this.$popupMenuAdapter, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$popupMenuAdapter, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$flowWidget, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$gridContainerView, this.$flowWidget, this.$layoutInflater, this.$view, null), 3);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass2(LifecycleOwner lifecycleOwner, UserSwitcherViewModel userSwitcherViewModel, View view, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, ViewGroup viewGroup, UserSwitcherViewBinder.MenuAdapter menuAdapter, Flow flow, UserSwitcherRootView userSwitcherRootView, LayoutInflater layoutInflater, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$viewModel = userSwitcherViewModel;
            this.$addButton = view;
            this.$popupMenu = ref$ObjectRef;
            this.$view = viewGroup;
            this.$popupMenuAdapter = menuAdapter;
            this.$flowWidget = flow;
            this.$gridContainerView = userSwitcherRootView;
            this.$layoutInflater = layoutInflater;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$$this$repeatWhenAttached, this.$viewModel, this.$addButton, this.$popupMenu, this.$view, this.$popupMenuAdapter, this.$flowWidget, this.$gridContainerView, this.$layoutInflater, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$addButton, this.$popupMenu, this.$view, this.$popupMenuAdapter, this.$flowWidget, this.$gridContainerView, this.$layoutInflater, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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

    public UserSwitcherViewBinder$bind$4(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, Function0 function0, View view, ViewGroup viewGroup, UserSwitcherViewBinder.MenuAdapter menuAdapter, Flow flow, UserSwitcherRootView userSwitcherRootView, LayoutInflater layoutInflater, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = userSwitcherViewModel;
        this.$popupMenu = ref$ObjectRef;
        this.$onFinish = function0;
        this.$addButton = view;
        this.$view = viewGroup;
        this.$popupMenuAdapter = menuAdapter;
        this.$flowWidget = flow;
        this.$gridContainerView = userSwitcherRootView;
        this.$layoutInflater = layoutInflater;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UserSwitcherViewBinder$bind$4 userSwitcherViewBinder$bind$4 = new UserSwitcherViewBinder$bind$4(this.$viewModel, this.$popupMenu, this.$onFinish, this.$addButton, this.$view, this.$popupMenuAdapter, this.$flowWidget, this.$gridContainerView, this.$layoutInflater, (Continuation) obj3);
        userSwitcherViewBinder$bind$4.L$0 = (LifecycleOwner) obj;
        return userSwitcherViewBinder$bind$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$viewModel, this.$popupMenu, this.$onFinish, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(lifecycleOwner, this.$viewModel, this.$addButton, this.$popupMenu, this.$view, this.$popupMenuAdapter, this.$flowWidget, this.$gridContainerView, this.$layoutInflater, null), 3);
        return Unit.INSTANCE;
    }
}
