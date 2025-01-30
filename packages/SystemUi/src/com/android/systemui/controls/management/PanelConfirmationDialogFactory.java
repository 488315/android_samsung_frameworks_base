package com.android.systemui.controls.management;

import android.content.Context;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PanelConfirmationDialogFactory {
    public final Function1 internalDialogFactory;

    public PanelConfirmationDialogFactory(Function1 function1) {
        this.internalDialogFactory = function1;
    }

    public PanelConfirmationDialogFactory() {
        this(new Function1() { // from class: com.android.systemui.controls.management.PanelConfirmationDialogFactory.1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return new SystemUIDialog((Context) obj);
            }
        });
    }
}
