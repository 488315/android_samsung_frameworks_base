package com.android.systemui.statusbar.notification.footer.ui.viewbinder;

import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class FooterViewBinder$bindMessage$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FooterView $footer;
    final /* synthetic */ FooterViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindMessage$2$1, reason: invalid class name */
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
                StateFlow stateFlow = this.$viewModel.message.isVisible;
                final FooterView footerView = this.$footer;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder.bindMessage.2.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        FooterView footerView2 = FooterView.this;
                        footerView2.getClass();
                        int i2 = FooterViewRefactor.$r8$clinit;
                        Flags.notificationsFooterViewRefactor();
                        if (booleanValue) {
                            footerView2.mManageOrHistoryButton.setVisibility(8);
                            footerView2.mClearAllButton.setVisibility(8);
                            footerView2.mSeenNotifsFooterTextView.setVisibility(0);
                        } else {
                            footerView2.mManageOrHistoryButton.setVisibility(0);
                            footerView2.mClearAllButton.setVisibility(0);
                            footerView2.mSeenNotifsFooterTextView.setVisibility(8);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
    public FooterViewBinder$bindMessage$2(FooterView footerView, FooterViewModel footerViewModel, Continuation continuation) {
        super(2, continuation);
        this.$footer = footerView;
        this.$viewModel = footerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FooterViewBinder$bindMessage$2 footerViewBinder$bindMessage$2 = new FooterViewBinder$bindMessage$2(this.$footer, this.$viewModel, continuation);
        footerViewBinder$bindMessage$2.L$0 = obj;
        return footerViewBinder$bindMessage$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FooterViewBinder$bindMessage$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        int i = this.$viewModel.message.messageId;
        footerView.getClass();
        FooterViewRefactor.isUnexpectedlyInLegacyMode();
        FooterView footerView2 = this.$footer;
        int i2 = this.$viewModel.message.iconId;
        footerView2.getClass();
        FooterViewRefactor.isUnexpectedlyInLegacyMode();
        return BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$footer, null), 3);
    }
}
