package com.android.settingslib.core.instrumentation;

import com.android.internal.jank.InteractionJankMonitor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
