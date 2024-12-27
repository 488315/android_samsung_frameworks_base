package com.android.systemui.keyboard.backlight.ui;

import android.content.Context;
import com.android.systemui.keyboard.backlight.ui.view.KeyboardBacklightDialog;
import com.android.systemui.keyboard.backlight.ui.viewmodel.BacklightDialogViewModel;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyboardBacklightDialogCoordinator {
    public final CoroutineScope applicationScope;
    public final Function2 createDialog;
    public KeyboardBacklightDialog dialog;
    public final BacklightDialogViewModel viewModel;

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, final Context context, BacklightDialogViewModel backlightDialogViewModel) {
        this(coroutineScope, backlightDialogViewModel, new Function2() { // from class: com.android.systemui.keyboard.backlight.ui.KeyboardBacklightDialogCoordinatorKt$defaultCreateDialog$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new KeyboardBacklightDialog(context, ((Number) obj).intValue(), ((Number) obj2).intValue(), 0, 8, null);
            }
        });
    }

    public final void startListening() {
        BuildersKt.launch$default(this.applicationScope, null, null, new KeyboardBacklightDialogCoordinator$startListening$1(this, null), 3);
    }

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, BacklightDialogViewModel backlightDialogViewModel, Function2 function2) {
        this.applicationScope = coroutineScope;
        this.viewModel = backlightDialogViewModel;
        this.createDialog = function2;
    }
}
