package com.android.systemui.statusbar.notification.footer.ui.viewbinder;

import android.view.View;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.util.ui.AnimatedValue;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class FooterViewBinder$bindManageOrHistoryButton$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FooterView $footer;
    final /* synthetic */ View.OnClickListener $launchNotificationHistory;
    final /* synthetic */ View.OnClickListener $launchNotificationSettings;
    final /* synthetic */ FooterViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindManageOrHistoryButton$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ View.OnClickListener $launchNotificationHistory;
        final /* synthetic */ View.OnClickListener $launchNotificationSettings;
        final /* synthetic */ FooterViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FooterViewModel footerViewModel, FooterView footerView, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = footerViewModel;
            this.$footer = footerView;
            this.$launchNotificationHistory = onClickListener;
            this.$launchNotificationSettings = onClickListener2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$footer, this.$launchNotificationHistory, this.$launchNotificationSettings, continuation);
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
                Flow flow = this.$viewModel.manageButtonShouldLaunchHistory;
                final FooterView footerView = this.$footer;
                final View.OnClickListener onClickListener = this.$launchNotificationHistory;
                final View.OnClickListener onClickListener2 = this.$launchNotificationSettings;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindManageOrHistoryButton.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        FooterView footerView2 = FooterView.this;
                        if (booleanValue) {
                            footerView2.mManageOrHistoryButton.setOnClickListener(onClickListener);
                        } else {
                            footerView2.mManageOrHistoryButton.setOnClickListener(onClickListener2);
                        }
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindManageOrHistoryButton$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ FooterViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(FooterViewModel footerViewModel, FooterView footerView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = footerViewModel;
            this.$footer = footerView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$viewModel, this.$footer, continuation);
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
                Flow flow = this.$viewModel.manageOrHistoryButton.labelId;
                final FooterView footerView = this.$footer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindManageOrHistoryButton.2.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Number) obj2).intValue();
                        FooterView.this.getClass();
                        FooterViewRefactor.isUnexpectedlyInLegacyMode();
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindManageOrHistoryButton$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ FooterViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(FooterViewModel footerViewModel, FooterView footerView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = footerViewModel;
            this.$footer = footerView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$viewModel, this.$footer, continuation);
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
                Flow flow = this.$viewModel.manageOrHistoryButton.accessibilityDescriptionId;
                final FooterView footerView = this.$footer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindManageOrHistoryButton.2.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Number) obj2).intValue();
                        FooterView.this.getClass();
                        FooterViewRefactor.isUnexpectedlyInLegacyMode();
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindManageOrHistoryButton$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ FooterViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(FooterViewModel footerViewModel, FooterView footerView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = footerViewModel;
            this.$footer = footerView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.$viewModel, this.$footer, continuation);
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
                Flow flow = this.$viewModel.manageOrHistoryButton.isVisible;
                final FooterView footerView = this.$footer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindManageOrHistoryButton.2.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object value;
                        AnimatedValue animatedValue = (AnimatedValue) obj2;
                        if (animatedValue instanceof AnimatedValue.Animating) {
                            value = ((AnimatedValue.Animating) animatedValue).getValue();
                        } else {
                            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                        }
                        FooterView.this.mManageOrHistoryButton.setVisibility(((Boolean) value).booleanValue() ? 0 : 8);
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
    public FooterViewBinder$bindManageOrHistoryButton$2(FooterViewModel footerViewModel, FooterView footerView, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = footerViewModel;
        this.$footer = footerView;
        this.$launchNotificationHistory = onClickListener;
        this.$launchNotificationSettings = onClickListener2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FooterViewBinder$bindManageOrHistoryButton$2 footerViewBinder$bindManageOrHistoryButton$2 = new FooterViewBinder$bindManageOrHistoryButton$2(this.$viewModel, this.$footer, this.$launchNotificationHistory, this.$launchNotificationSettings, continuation);
        footerViewBinder$bindManageOrHistoryButton$2.L$0 = obj;
        return footerViewBinder$bindManageOrHistoryButton$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FooterViewBinder$bindManageOrHistoryButton$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$footer, this.$launchNotificationHistory, this.$launchNotificationSettings, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$footer, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$footer, null), 3);
        return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$footer, null), 3);
    }
}
