package com.android.systemui.user.ui.binder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.Utils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.binder.TextViewBinder;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.user.UserSwitcherPopupMenu;
import com.android.systemui.user.UserSwitcherRootView;
import com.android.systemui.user.shared.model.UserActionModel;
import com.android.systemui.user.ui.viewmodel.UserActionViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$2;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$4;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$5;
import com.android.systemui.user.ui.viewmodel.UserViewModel;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class UserSwitcherViewBinder {
    public static final UserSwitcherViewBinder INSTANCE = new UserSwitcherViewBinder();

    public final class MenuAdapter extends BaseAdapter {
        public final LayoutInflater layoutInflater;
        public List sections = EmptyList.INSTANCE;

        public MenuAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.sections.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return (List) this.sections.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            List list = (List) this.sections.get(i);
            Context context = viewGroup.getContext();
            LinearLayout linearLayout = view instanceof LinearLayout ? (LinearLayout) view : null;
            if (linearLayout == null) {
                linearLayout = new LinearLayout(context, null);
                linearLayout.setOrientation(1);
                linearLayout.setBackground(viewGroup.getResources().getDrawable(R.drawable.bouncer_user_switcher_popup_bg, context.getTheme()));
                linearLayout.setShowDividers(2);
                linearLayout.setDividerDrawable(context.getDrawable(R.drawable.fullscreen_userswitcher_menu_item_divider));
            }
            linearLayout.removeAllViewsInLayout();
            int i2 = 0;
            for (Object obj : list) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                final UserActionViewModel userActionViewModel = (UserActionViewModel) obj;
                final View viewInflate = this.layoutInflater.inflate(R.layout.user_switcher_fullscreen_popup_item, (ViewGroup) null);
                ((ImageView) viewInflate.requireViewById(R.id.icon)).setImageResource(userActionViewModel.iconResourceId);
                ((TextView) viewInflate.requireViewById(R.id.text)).setText(viewInflate.getResources().getString(userActionViewModel.textResourceId));
                viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$MenuAdapter$getView$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        userActionViewModel.onClicked.invoke();
                    }
                });
                linearLayout.addView(viewInflate);
                if (i2 == 0 && i == 0) {
                    viewInflate.postDelayed(new Runnable() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$MenuAdapter$getView$1$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            viewInflate.requestAccessibilityFocus();
                        }
                    }, 200L);
                }
                i2 = i3;
            }
            return linearLayout;
        }
    }

    /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$1, reason: invalid class name */
    public final class AnonymousClass1 implements Gefingerpoken {
        public final /* synthetic */ FalsingCollector $falsingCollector;

        public AnonymousClass1(FalsingCollector falsingCollector) {
            this.$falsingCollector = falsingCollector;
        }
    }

    /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function3 {
        final /* synthetic */ View $addButton;
        final /* synthetic */ Flow $flowWidget;
        final /* synthetic */ UserSwitcherRootView $gridContainerView;
        final /* synthetic */ LayoutInflater $layoutInflater;
        final /* synthetic */ Function0 $onFinish;
        final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
        final /* synthetic */ MenuAdapter $popupMenuAdapter;
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
            final class C06201 extends SuspendLambda implements Function2 {
                final /* synthetic */ Function0 $onFinish;
                final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                private /* synthetic */ Object L$0;
                int label;

                /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C06211 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Function0 $onFinish;
                    final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
                    final /* synthetic */ UserSwitcherViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C06211(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, Function0 function0, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = userSwitcherViewModel;
                        this.$popupMenu = ref$ObjectRef;
                        this.$onFinish = function0;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C06211(this.$viewModel, this.$popupMenu, this.$onFinish, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C06211) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                    Object objCollect = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1.collect(new AnonymousClass2(flowCollector), continuation);
                                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                                }
                            };
                            final Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef = this.$popupMenu;
                            final Function0 function0 = this.$onFinish;
                            final UserSwitcherViewModel userSwitcherViewModel = this.$viewModel;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.1.1.1.2
                                /* JADX WARN: Multi-variable type inference failed */
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

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C06201(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, Function0 function0, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = userSwitcherViewModel;
                    this.$popupMenu = ref$ObjectRef;
                    this.$onFinish = function0;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C06201 c06201 = new C06201(this.$viewModel, this.$popupMenu, this.$onFinish, continuation);
                    c06201.L$0 = obj;
                    return c06201;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C06201) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C06211(this.$viewModel, this.$popupMenu, this.$onFinish, null), 7);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                    C06201 c06201 = new C06201(this.$viewModel, this.$popupMenu, this.$onFinish, null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c06201, this) == coroutineSingletons) {
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
            final /* synthetic */ MenuAdapter $popupMenuAdapter;
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
                final /* synthetic */ MenuAdapter $popupMenuAdapter;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ UserSwitcherViewModel $viewModel;
                private /* synthetic */ Object L$0;
                int label;

                /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$1, reason: invalid class name and collision with other inner class name */
                final class C06221 extends SuspendLambda implements Function2 {
                    final /* synthetic */ View $addButton;
                    final /* synthetic */ UserSwitcherViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C06221(UserSwitcherViewModel userSwitcherViewModel, View view, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = userSwitcherViewModel;
                        this.$addButton = view;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C06221(this.$viewModel, this.$addButton, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C06221) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                final class C06242 extends SuspendLambda implements Function2 {
                    final /* synthetic */ View $addButton;
                    final /* synthetic */ Ref$ObjectRef<UserSwitcherPopupMenu> $popupMenu;
                    final /* synthetic */ MenuAdapter $popupMenuAdapter;
                    final /* synthetic */ ViewGroup $view;
                    final /* synthetic */ UserSwitcherViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C06242(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, ViewGroup viewGroup, View view, MenuAdapter menuAdapter, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = userSwitcherViewModel;
                        this.$popupMenu = ref$ObjectRef;
                        this.$view = viewGroup;
                        this.$addButton = view;
                        this.$popupMenuAdapter = menuAdapter;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C06242(this.$viewModel, this.$popupMenu, this.$view, this.$addButton, this.$popupMenuAdapter, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C06242) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                            final MenuAdapter menuAdapter = this.$popupMenuAdapter;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.2.1
                                /* JADX WARN: Multi-variable type inference failed */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    UserSwitcherPopupMenu userSwitcherPopupMenu;
                                    UserSwitcherPopupMenu userSwitcherPopupMenu2;
                                    boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                                    if (zBooleanValue && ((userSwitcherPopupMenu2 = (UserSwitcherPopupMenu) ref$ObjectRef2.element) == null || !userSwitcherPopupMenu2.isShowing())) {
                                        UserSwitcherPopupMenu userSwitcherPopupMenu3 = (UserSwitcherPopupMenu) ref$ObjectRef2.element;
                                        if (userSwitcherPopupMenu3 != null) {
                                            userSwitcherPopupMenu3.dismiss();
                                        }
                                        final ViewGroup viewGroup2 = viewGroup;
                                        final View view2 = view;
                                        final UserSwitcherViewModel userSwitcherViewModel2 = userSwitcherViewModel;
                                        final Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                                        final MenuAdapter menuAdapter2 = menuAdapter;
                                        viewGroup2.post(new Runnable() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.2.1.1

                                            /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$2$1$1$1, reason: invalid class name and collision with other inner class name */
                                            final /* synthetic */ class C06271 extends FunctionReferenceImpl implements Function0 {
                                                public C06271(Object obj) {
                                                    super(0, obj, UserSwitcherViewModel.class, "onMenuClosed", "onMenuClosed()V", 0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    ((UserSwitcherViewModel) this.receiver)._isMenuVisible.updateState(null, Boolean.FALSE);
                                                    return Unit.INSTANCE;
                                                }
                                            }

                                            /* JADX WARN: Type inference failed for: r6v2, types: [T, android.widget.ListPopupWindow, com.android.systemui.user.UserSwitcherPopupMenu] */
                                            @Override // java.lang.Runnable
                                            public final void run() throws Resources.NotFoundException {
                                                Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef3;
                                                UserSwitcherViewBinder userSwitcherViewBinder = UserSwitcherViewBinder.INSTANCE;
                                                Context context = viewGroup2.getContext();
                                                View view3 = view2;
                                                MenuAdapter menuAdapter3 = menuAdapter2;
                                                final C06271 c06271 = new C06271(userSwitcherViewModel2);
                                                userSwitcherViewBinder.getClass();
                                                ?? userSwitcherPopupMenu4 = new UserSwitcherPopupMenu(context);
                                                userSwitcherPopupMenu4.setDropDownGravity(8388613);
                                                userSwitcherPopupMenu4.setAnchorView(view3);
                                                userSwitcherPopupMenu4.setAdapter(menuAdapter3);
                                                userSwitcherPopupMenu4.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$createAndShowPopupMenu$1$1
                                                    @Override // android.widget.PopupWindow.OnDismissListener
                                                    public final void onDismiss() {
                                                        c06271.invoke();
                                                    }
                                                });
                                                userSwitcherPopupMenu4.show();
                                                ref$ObjectRef4.element = userSwitcherPopupMenu4;
                                            }
                                        });
                                    } else if (!zBooleanValue && (userSwitcherPopupMenu = (UserSwitcherPopupMenu) ref$ObjectRef2.element) != null && userSwitcherPopupMenu.isShowing()) {
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
                    final /* synthetic */ MenuAdapter $popupMenuAdapter;
                    final /* synthetic */ UserSwitcherViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass3(UserSwitcherViewModel userSwitcherViewModel, MenuAdapter menuAdapter, Continuation continuation) {
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
                            final MenuAdapter menuAdapter = this.$popupMenuAdapter;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.4.2.1.3.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    MenuAdapter menuAdapter2 = menuAdapter;
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
                                    menuAdapter2.sections = Arrays.asList(arrayList, arrayList2);
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

                /* renamed from: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$4$2$1$4, reason: invalid class name and collision with other inner class name */
                final class C06294 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Flow $flowWidget;
                    final /* synthetic */ UserSwitcherViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C06294(UserSwitcherViewModel userSwitcherViewModel, Flow flow, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = userSwitcherViewModel;
                        this.$flowWidget = flow;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C06294(this.$viewModel, this.$flowWidget, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C06294) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                    int iIntValue = ((Number) obj2).intValue();
                                    Flow flow2 = flow;
                                    flow2.mFlow.mMaxElementsWrap = iIntValue;
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

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                                public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                                    Flow flow2;
                                    View viewInflate;
                                    List<UserViewModel> list = (List) obj2;
                                    UserSwitcherRootView userSwitcherRootView2 = userSwitcherRootView;
                                    ArrayList arrayList = (ArrayList) SequencesKt___SequencesKt.toMutableList(SequencesKt___SequencesKt.filter(ConvenienceExtensionsKt.getChildren(userSwitcherRootView2), new UserSwitcherViewBinder$bind$4$2$1$5$1$$ExternalSyntheticLambda0()));
                                    Iterator it = arrayList.iterator();
                                    while (true) {
                                        boolean zHasNext = it.hasNext();
                                        flow2 = flow;
                                        if (!zHasNext) {
                                            break;
                                        }
                                        View view = (View) it.next();
                                        userSwitcherRootView2.removeView(view);
                                        flow2.removeView(view);
                                    }
                                    LayoutInflater layoutInflater2 = layoutInflater;
                                    ViewGroup viewGroup2 = viewGroup;
                                    for (final UserViewModel userViewModel : list) {
                                        if (arrayList.isEmpty()) {
                                            viewInflate = layoutInflater2.inflate(R.layout.user_switcher_fullscreen_item, viewGroup2, false);
                                            viewInflate.setTag("user_view");
                                        } else {
                                            viewInflate = (View) arrayList.remove(0);
                                        }
                                        viewInflate.setId(View.generateViewId());
                                        userSwitcherRootView2.addView(viewInflate);
                                        flow2.addView(viewInflate);
                                        UserViewBinder.INSTANCE.getClass();
                                        TextViewBinder textViewBinder = TextViewBinder.INSTANCE;
                                        TextView textView = (TextView) viewInflate.requireViewById(R.id.user_switcher_text);
                                        Text text = userViewModel.name;
                                        textViewBinder.getClass();
                                        TextViewBinder.bind(textView, text);
                                        ImageView imageView = (ImageView) viewInflate.requireViewById(R.id.user_switcher_icon);
                                        Context context = viewInflate.getContext();
                                        Resources resources = context.getResources();
                                        Resources.Theme theme = context.getTheme();
                                        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
                                        Drawable drawable = resources.getDrawable(R.drawable.user_switcher_icon_large, theme);
                                        if (drawable == null) {
                                            throw new IllegalStateException("Required value was null.");
                                        }
                                        LayerDrawable layerDrawable = (LayerDrawable) drawable.mutate();
                                        if (userViewModel.isSelectionMarkerVisible) {
                                            ((GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.ring)).setStroke(context.getResources().getDimensionPixelSize(R.dimen.user_switcher_icon_selected_width), Utils.getColorAttrDefaultColor(context, android.R.^attr-private.closeItemLayout, 0));
                                        }
                                        layerDrawable.setDrawableByLayerId(R.id.user_avatar, userViewModel.image);
                                        imageView.setImageDrawable(layerDrawable);
                                        viewInflate.setAlpha(userViewModel.alpha);
                                        if (userViewModel.onClicked != null) {
                                            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserViewBinder$bind$1
                                                @Override // android.view.View.OnClickListener
                                                public final void onClick(View view2) {
                                                    userViewModel.onClicked.invoke();
                                                }
                                            });
                                        } else {
                                            viewInflate.setOnClickListener(null);
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

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(UserSwitcherViewModel userSwitcherViewModel, View view, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, ViewGroup viewGroup, MenuAdapter menuAdapter, Flow flow, UserSwitcherRootView userSwitcherRootView, LayoutInflater layoutInflater, Continuation continuation) {
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
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C06221(this.$viewModel, this.$addButton, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C06242(this.$viewModel, this.$popupMenu, this.$view, this.$addButton, this.$popupMenuAdapter, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$popupMenuAdapter, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C06294(this.$viewModel, this.$flowWidget, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$gridContainerView, this.$flowWidget, this.$layoutInflater, this.$view, null), 7);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(LifecycleOwner lifecycleOwner, UserSwitcherViewModel userSwitcherViewModel, View view, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, ViewGroup viewGroup, MenuAdapter menuAdapter, Flow flow, UserSwitcherRootView userSwitcherRootView, LayoutInflater layoutInflater, Continuation continuation) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(UserSwitcherViewModel userSwitcherViewModel, Ref$ObjectRef<UserSwitcherPopupMenu> ref$ObjectRef, Function0 function0, View view, ViewGroup viewGroup, MenuAdapter menuAdapter, Flow flow, UserSwitcherRootView userSwitcherRootView, LayoutInflater layoutInflater, Continuation continuation) {
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
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$viewModel, this.$popupMenu, this.$onFinish, this.$addButton, this.$view, this.$popupMenuAdapter, this.$flowWidget, this.$gridContainerView, this.$layoutInflater, (Continuation) obj3);
            anonymousClass4.L$0 = (LifecycleOwner) obj;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$viewModel, this.$popupMenu, this.$onFinish, null), 7);
            CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(lifecycleOwner, this.$viewModel, this.$addButton, this.$popupMenu, this.$view, this.$popupMenuAdapter, this.$flowWidget, this.$gridContainerView, this.$layoutInflater, null), 7);
            return Unit.INSTANCE;
        }
    }

    private UserSwitcherViewBinder() {
    }

    public static void bind(ViewGroup viewGroup, final UserSwitcherViewModel userSwitcherViewModel, LayoutInflater layoutInflater, FalsingCollector falsingCollector, Function0 function0) {
        UserSwitcherRootView userSwitcherRootView = (UserSwitcherRootView) viewGroup.requireViewById(R.id.user_switcher_grid_container);
        Flow flow = (Flow) userSwitcherRootView.requireViewById(R.id.flow);
        View viewRequireViewById = viewGroup.requireViewById(R.id.add);
        View viewRequireViewById2 = viewGroup.requireViewById(R.id.cancel);
        MenuAdapter menuAdapter = new MenuAdapter(layoutInflater);
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        userSwitcherRootView.touchHandler = new AnonymousClass1(falsingCollector);
        viewRequireViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                userSwitcherViewModel._isMenuVisible.updateState(null, Boolean.TRUE);
            }
        });
        viewRequireViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder.bind.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                userSwitcherViewModel.hasCancelButtonBeenClicked.updateState(null, Boolean.TRUE);
            }
        });
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass4(userSwitcherViewModel, ref$ObjectRef, function0, viewRequireViewById, viewGroup, menuAdapter, flow, userSwitcherRootView, layoutInflater, null));
    }
}
