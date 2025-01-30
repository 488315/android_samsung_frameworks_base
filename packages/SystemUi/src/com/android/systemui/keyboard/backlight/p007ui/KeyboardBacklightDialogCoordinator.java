package com.android.systemui.keyboard.backlight.p007ui;

import android.content.Context;
import com.android.systemui.keyboard.backlight.p007ui.view.KeyboardBacklightDialog;
import com.android.systemui.keyboard.backlight.p007ui.viewmodel.BacklightDialogViewModel;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyboardBacklightDialogCoordinator {
    public final Function2 createDialog;
    public final BacklightDialogViewModel viewModel;

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, final Context context, BacklightDialogViewModel backlightDialogViewModel) {
        this(coroutineScope, backlightDialogViewModel, new Function2() { // from class: com.android.systemui.keyboard.backlight.ui.KeyboardBacklightDialogCoordinatorKt$defaultCreateDialog$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return new KeyboardBacklightDialog(context, ((Number) obj).intValue(), ((Number) obj2).intValue());
            }
        });
    }

    public KeyboardBacklightDialogCoordinator(CoroutineScope coroutineScope, BacklightDialogViewModel backlightDialogViewModel, Function2 function2) {
        this.viewModel = backlightDialogViewModel;
        this.createDialog = function2;
    }
}
