package com.android.systemui.plugins.keyguardstatusview;

import android.os.Bundle;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginFaceWidgetLockManager {
    void addLockStarStateCallback(PluginLockStarStateCallback pluginLockStarStateCallback);

    @VersionCheck(version = 1043)
    Consumer<?> getModifier(String str);

    @VersionCheck(version = 1043)
    Supplier<?> getSupplier(String str);

    @VersionCheck(version = 2014)
    Bundle onSendExtraData(Bundle bundle);

    void removeLockStarStateCallback(PluginLockStarStateCallback pluginLockStarStateCallback);
}
