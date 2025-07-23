package com.android.systemui.keyboard.backlight.ui;

import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyboard.backlight.ui.view.KeyboardBacklightDialog;
import com.android.systemui.keyboard.backlight.ui.viewmodel.BacklightDialogViewModel;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardBacklightDialogCoordinator {
    public final CoroutineScope applicationScope;
    public final Function2 createDialog;
    public KeyboardBacklightDialog dialog;
    public final BacklightDialogViewModel viewModel;

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, final Context context, BacklightDialogViewModel backlightDialogViewModel) {
        this(coroutineScope, backlightDialogViewModel, new Function2() { // from class: com.android.systemui.keyboard.backlight.ui.KeyboardBacklightDialogCoordinatorKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new KeyboardBacklightDialog(context, ((Integer) obj).intValue(), ((Integer) obj2).intValue(), 0, 8, null);
            }
        });
    }

    public final void startListening() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new KeyboardBacklightDialogCoordinator$startListening$1(this, null), 7);
    }

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, BacklightDialogViewModel backlightDialogViewModel, Function2 function2) {
        this.applicationScope = coroutineScope;
        this.viewModel = backlightDialogViewModel;
        this.createDialog = function2;
    }
}
