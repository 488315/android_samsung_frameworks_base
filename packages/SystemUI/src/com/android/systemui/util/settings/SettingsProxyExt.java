package com.android.systemui.util.settings;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SettingsProxyExt {
    public static final int $stable = 0;
    public static final SettingsProxyExt INSTANCE = new SettingsProxyExt();

    private SettingsProxyExt() {
    }

    public final Flow observerFlow(UserSettingsProxy userSettingsProxy, int i, String... strArr) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SettingsProxyExt$observerFlow$1 settingsProxyExt$observerFlow$1 = new SettingsProxyExt$observerFlow$1(strArr, userSettingsProxy, i, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(settingsProxyExt$observerFlow$1);
    }

    public final Flow observerFlow(SettingsProxy settingsProxy, String... strArr) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SettingsProxyExt$observerFlow$2 settingsProxyExt$observerFlow$2 = new SettingsProxyExt$observerFlow$2(strArr, settingsProxy, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(settingsProxyExt$observerFlow$2);
    }
}
