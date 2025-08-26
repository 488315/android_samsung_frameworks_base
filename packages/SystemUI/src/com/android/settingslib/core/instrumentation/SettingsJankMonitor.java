package com.android.settingslib.core.instrumentation;

import com.android.internal.jank.InteractionJankMonitor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes.dex */
public final class SettingsJankMonitor {
    public static final InteractionJankMonitor jankMonitor;
    public static final ScheduledExecutorService scheduledExecutorService;

    static {
        new SettingsJankMonitor();
        jankMonitor = InteractionJankMonitor.getInstance();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    private SettingsJankMonitor() {
    }

    public static /* synthetic */ void getMONITORED_ANIMATION_DURATION_MS$annotations() {
    }
}
