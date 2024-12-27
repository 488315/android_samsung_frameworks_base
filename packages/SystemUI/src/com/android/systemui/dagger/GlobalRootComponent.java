package com.android.systemui.dagger;

import android.os.Looper;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.util.InitializationChecker;

public interface GlobalRootComponent {

    public interface Builder {
    }

    InitializationChecker getInitializationChecker();

    Looper getMainLooper();

    ProcessWrapper getProcessWrapper();

    SystemPropertiesHelper getSystemPropertiesHelper();

    WMComponent.Builder getWMComponentBuilder();
}
