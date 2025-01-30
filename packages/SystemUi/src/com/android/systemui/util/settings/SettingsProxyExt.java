package com.android.systemui.util.settings;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SettingsProxyExt {
    public static final SettingsProxyExt INSTANCE = new SettingsProxyExt();

    private SettingsProxyExt() {
    }

    public static Flow observerFlow(SettingsProxy settingsProxy, int i, String... strArr) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SettingsProxyExt$observerFlow$1 settingsProxyExt$observerFlow$1 = new SettingsProxyExt$observerFlow$1(strArr, settingsProxy, i, null);
        conflatedCallbackFlow.getClass();
        return ConflatedCallbackFlow.conflatedCallbackFlow(settingsProxyExt$observerFlow$1);
    }
}
