package com.android.systemui.keyboard.stickykeys.ui;

import android.app.Dialog;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.activity.ComponentDialog;
import com.android.systemui.R;
import com.android.systemui.keyboard.stickykeys.ui.view.StickyKeysIndicatorKt;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class StickyKeysIndicatorCoordinator$startListening$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ StickyKeysIndicatorCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyKeysIndicatorCoordinator$startListening$1(StickyKeysIndicatorCoordinator stickyKeysIndicatorCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = stickyKeysIndicatorCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new StickyKeysIndicatorCoordinator$startListening$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StickyKeysIndicatorCoordinator$startListening$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final StickyKeysIndicatorCoordinator stickyKeysIndicatorCoordinator = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = stickyKeysIndicatorCoordinator.viewModel.indicatorContent;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.stickykeys.ui.StickyKeysIndicatorCoordinator$startListening$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Map map = (Map) obj2;
                    StickyKeysIndicatorCoordinator stickyKeysIndicatorCoordinator2 = StickyKeysIndicatorCoordinator.this;
                    stickyKeysIndicatorCoordinator2.stickyKeysLogger.logNewUiState(map);
                    if (map.isEmpty()) {
                        Dialog dialog = stickyKeysIndicatorCoordinator2.dialog;
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        stickyKeysIndicatorCoordinator2.dialog = null;
                    } else if (stickyKeysIndicatorCoordinator2.dialog == null) {
                        StickyKeyDialogFactory stickyKeyDialogFactory = stickyKeysIndicatorCoordinator2.stickyKeyDialogFactory;
                        stickyKeyDialogFactory.getClass();
                        ComponentDialog componentDialog = new ComponentDialog(stickyKeyDialogFactory.context, R.style.Theme_SystemUI_Dialog_StickyKeys);
                        Window window = componentDialog.getWindow();
                        if (window != null) {
                            window.requestFeature(1);
                            window.setType(2017);
                            window.addFlags(24);
                            window.clearFlags(2);
                            window.setGravity(8388661);
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                            layoutParams.copyFrom(window.getAttributes());
                            layoutParams.receiveInsetsIgnoringZOrder = true;
                            layoutParams.setFitInsetsTypes(WindowInsets.Type.systemBars());
                            layoutParams.setTitle("StickyKeysIndicator");
                            window.setAttributes(layoutParams);
                        }
                        componentDialog.setContentView(StickyKeysIndicatorKt.createStickyKeyIndicatorView(componentDialog.getContext(), stickyKeysIndicatorCoordinator2.viewModel));
                        stickyKeysIndicatorCoordinator2.dialog = componentDialog;
                        componentDialog.show();
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
        return Unit.INSTANCE;
    }
}
