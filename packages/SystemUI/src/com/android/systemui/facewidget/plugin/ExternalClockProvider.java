package com.android.systemui.facewidget.plugin;

import com.android.systemui.plugins.keyguardstatusview.PluginClockProvider;
import java.util.ArrayList;
import java.util.List;

public final class ExternalClockProvider {
    public final List mClockCallbacks = new ArrayList();
    public PluginClockProvider mClockProvider;
}
