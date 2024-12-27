package com.android.systemui.statusbar.phone;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SystemUIDialogManagerExtKt {
    public static final Flow getHideAffordancesRequest(SystemUIDialogManager systemUIDialogManager) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SystemUIDialogManagerExtKt$hideAffordancesRequest$1 systemUIDialogManagerExtKt$hideAffordancesRequest$1 = new SystemUIDialogManagerExtKt$hideAffordancesRequest$1(systemUIDialogManager, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(systemUIDialogManagerExtKt$hideAffordancesRequest$1);
    }
}
