package com.android.systemui.dagger;

import android.os.Looper;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.process.ProcessWrapper;
import com.android.wm.shell.dagger.WMComponent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface GlobalRootComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Builder {
    }

    Looper getMainLooper();

    ProcessWrapper getProcessWrapper();

    SystemPropertiesHelper getSystemPropertiesHelper();

    WMComponent.Builder getWMComponentBuilder();
}
