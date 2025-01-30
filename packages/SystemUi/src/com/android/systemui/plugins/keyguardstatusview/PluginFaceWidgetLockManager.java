package com.android.systemui.plugins.keyguardstatusview;

import android.os.Bundle;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginFaceWidgetLockManager {
    void addLockStarStateCallback(PluginLockStarStateCallback pluginLockStarStateCallback);

    @VersionCheck(version = 1043)
    Consumer<?> getModifier(String str);

    @VersionCheck(version = 1043)
    Supplier<?> getSupplier(String str);

    @VersionCheck(version = PluginKeyguardStatusView.VERSION)
    Bundle onSendExtraData(Bundle bundle);

    void removeLockStarStateCallback(PluginLockStarStateCallback pluginLockStarStateCallback);
}
