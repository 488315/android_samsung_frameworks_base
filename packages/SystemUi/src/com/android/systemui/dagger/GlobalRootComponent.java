package com.android.systemui.dagger;

import android.content.Context;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.util.InitializationChecker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface GlobalRootComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Builder {
        GlobalRootComponent build();

        Builder context(Context context);

        Builder instrumentationTest(boolean z);
    }

    InitializationChecker getInitializationChecker();

    SysUIComponent.Builder getSysUIComponent();

    WMComponent.Builder getWMComponentBuilder();
}
