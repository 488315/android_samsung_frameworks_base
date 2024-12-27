package com.android.systemui.statusbar.notification.footer.ui.viewbinder;

import android.view.View;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.util.ui.AnimatedValue;
import java.util.function.Consumer;
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

/* JADX INFO: Access modifiers changed from: package-private */
public final class FooterViewBinder$bindClearAllButton$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ View.OnClickListener $clearAllNotifications;
    final /* synthetic */ FooterView $footer;
    final /* synthetic */ FooterViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ FooterViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FooterViewModel footerViewModel, FooterView footerView, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = footerViewModel;
            this.$footer = footerView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$footer, continuation);
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
                Flow flow = this.$viewModel.clearAllButton.labelId;
                final FooterView footerView = this.$footer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.1.1
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

    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2$2, reason: invalid class name */
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
                Flow flow = this.$viewModel.clearAllButton.accessibilityDescriptionId;
                final FooterView footerView = this.$footer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.2.1
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

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2$3, reason: invalid class name */
    public final class AnonymousClass3 extends SuspendLambda implements Function2 {
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
                Flow flow = this.$viewModel.clearAllButton.isVisible;
                final FooterView footerView = this.$footer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.3.1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindClearAllButton$2$3$1$1] */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object value;
                        Object value2;
                        final AnimatedValue animatedValue = (AnimatedValue) obj2;
                        boolean z = animatedValue instanceof AnimatedValue.Animating;
                        FooterView footerView2 = FooterView.this;
                        if (z) {
                            if (animatedValue instanceof AnimatedValue.Animating) {
                                value2 = ((AnimatedValue.Animating) animatedValue).getValue();
                            } else {
                                if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                value2 = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                            }
                            footerView2.setSecondaryVisible(((Boolean) value2).booleanValue(), true, new Consumer() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindClearAllButton.2.3.1.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj3) {
                                    AnimatedValue animatedValue2 = animatedValue;
                                    if (animatedValue2 instanceof AnimatedValue.Animating) {
                                        ((AnimatedValue.Animating) animatedValue2).getOnStopAnimating().invoke();
                                    }
                                }
                            });
                        } else {
                            if (z) {
                                value = ((AnimatedValue.Animating) animatedValue).getValue();
                            } else {
                                if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
                            }
                            footerView2.setSecondaryVisible(((Boolean) value).booleanValue(), false, null);
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterViewBinder$bindClearAllButton$2(FooterView footerView, View.OnClickListener onClickListener, FooterViewModel footerViewModel, Continuation continuation) {
        super(2, continuation);
        this.$footer = footerView;
        this.$clearAllNotifications = onClickListener;
        this.$viewModel = footerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FooterViewBinder$bindClearAllButton$2 footerViewBinder$bindClearAllButton$2 = new FooterViewBinder$bindClearAllButton$2(this.$footer, this.$clearAllNotifications, this.$viewModel, continuation);
        footerViewBinder$bindClearAllButton$2.L$0 = obj;
        return footerViewBinder$bindClearAllButton$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FooterViewBinder$bindClearAllButton$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        FooterView footerView = this.$footer;
        View.OnClickListener onClickListener = this.$clearAllNotifications;
        footerView.getClass();
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        footerView.mClearAllButton.setOnClickListener(onClickListener);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$footer, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$footer, null), 3);
        return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$footer, null), 3);
    }
}
