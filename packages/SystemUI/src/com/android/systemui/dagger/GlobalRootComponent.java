package com.android.systemui.dagger;

import android.os.Looper;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.util.InitializationChecker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface GlobalRootComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Builder {
    }

    InitializationChecker getInitializationChecker();

    Looper getMainLooper();

    ProcessWrapper getProcessWrapper();

    SystemPropertiesHelper getSystemPropertiesHelper();

    WMComponent.Builder getWMComponentBuilder();
}
