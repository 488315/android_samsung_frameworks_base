package com.android.systemui.plugins.keyguardstatusview;

import android.os.Bundle;
import com.android.systemui.plugins.annotations.VersionCheck;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
