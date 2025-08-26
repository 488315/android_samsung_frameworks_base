package com.android.systemui.statusbar.phone;

import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public abstract class SystemUIDialogManagerExtKt {
    public static final Flow getHideAffordancesRequest(SystemUIDialogManager systemUIDialogManager) {
        return FlowConflatedKt.conflatedCallbackFlow(new SystemUIDialogManagerExtKt$hideAffordancesRequest$1(systemUIDialogManager, null));
    }
}
