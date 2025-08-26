package com.android.systemui.dagger;

import android.os.Looper;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.process.ProcessWrapper;
import com.android.wm.shell.dagger.WMComponent;

/* loaded from: classes2.dex */
public interface GlobalRootComponent {

    public interface Builder {
    }

    Looper getMainLooper();

    ProcessWrapper getProcessWrapper();

    SystemPropertiesHelper getSystemPropertiesHelper();

    WMComponent.Builder getWMComponentBuilder();
}
