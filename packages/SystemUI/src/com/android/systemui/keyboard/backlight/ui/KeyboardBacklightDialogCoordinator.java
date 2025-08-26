package com.android.systemui.keyboard.backlight.ui;

import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyboard.backlight.ui.view.KeyboardBacklightDialog;
import com.android.systemui.keyboard.backlight.ui.viewmodel.BacklightDialogContentViewModel;
import com.android.systemui.keyboard.backlight.ui.viewmodel.BacklightDialogViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class KeyboardBacklightDialogCoordinator {
    public final CoroutineScope applicationScope;
    public final Function2 createDialog;
    public KeyboardBacklightDialog dialog;
    public final BacklightDialogViewModel viewModel;

    /* renamed from: com.android.systemui.keyboard.backlight.ui.KeyboardBacklightDialogCoordinator$startListening$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardBacklightDialogCoordinator.this.new AnonymousClass1(continuation);
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
                final KeyboardBacklightDialogCoordinator keyboardBacklightDialogCoordinator = KeyboardBacklightDialogCoordinator.this;
                ChannelFlowTransformLatest channelFlowTransformLatest = keyboardBacklightDialogCoordinator.viewModel.dialogContent;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyboard.backlight.ui.KeyboardBacklightDialogCoordinator.startListening.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        BacklightDialogContentViewModel backlightDialogContentViewModel = (BacklightDialogContentViewModel) obj2;
                        KeyboardBacklightDialogCoordinator keyboardBacklightDialogCoordinator2 = keyboardBacklightDialogCoordinator;
                        if (backlightDialogContentViewModel != null) {
                            KeyboardBacklightDialog keyboardBacklightDialog = keyboardBacklightDialogCoordinator2.dialog;
                            int i2 = backlightDialogContentViewModel.maxValue;
                            int i3 = backlightDialogContentViewModel.currentValue;
                            if (keyboardBacklightDialog == null) {
                                keyboardBacklightDialogCoordinator2.dialog = (KeyboardBacklightDialog) keyboardBacklightDialogCoordinator2.createDialog.invoke(Integer.valueOf(i3), Integer.valueOf(i2));
                            } else {
                                int i4 = KeyboardBacklightDialog.BACKLIGHT_ICON_ID;
                                keyboardBacklightDialog.updateState(i3, i2, false);
                            }
                            KeyboardBacklightDialog keyboardBacklightDialog2 = keyboardBacklightDialogCoordinator2.dialog;
                            if (keyboardBacklightDialog2 != null) {
                                keyboardBacklightDialog2.show();
                            }
                        } else {
                            KeyboardBacklightDialog keyboardBacklightDialog3 = keyboardBacklightDialogCoordinator2.dialog;
                            if (keyboardBacklightDialog3 != null) {
                                keyboardBacklightDialog3.dismiss();
                            }
                            keyboardBacklightDialogCoordinator2.dialog = null;
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, final Context context, BacklightDialogViewModel backlightDialogViewModel) {
        this(coroutineScope, backlightDialogViewModel, new Function2() { // from class: com.android.systemui.keyboard.backlight.ui.KeyboardBacklightDialogCoordinatorKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new KeyboardBacklightDialog(context, ((Integer) obj).intValue(), ((Integer) obj2).intValue(), 0, 8, null);
            }
        });
    }

    public final void startListening() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(null), 7);
    }

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, BacklightDialogViewModel backlightDialogViewModel, Function2 function2) {
        this.applicationScope = coroutineScope;
        this.viewModel = backlightDialogViewModel;
        this.createDialog = function2;
    }
}
