package com.android.settingslib.core.instrumentation;

import com.android.internal.jank.InteractionJankMonitor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
