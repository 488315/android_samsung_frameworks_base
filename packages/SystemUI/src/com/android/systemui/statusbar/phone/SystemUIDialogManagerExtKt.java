package com.android.systemui.statusbar.phone;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SystemUIDialogManagerExtKt {
    public static final Flow getHideAffordancesRequest(SystemUIDialogManager systemUIDialogManager) {
        return FlowConflatedKt.conflatedCallbackFlow(new SystemUIDialogManagerExtKt$hideAffordancesRequest$1(systemUIDialogManager, null));
    }
}
