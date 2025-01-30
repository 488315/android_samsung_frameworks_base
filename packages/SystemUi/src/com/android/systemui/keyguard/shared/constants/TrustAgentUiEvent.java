package com.android.systemui.keyguard.shared.constants;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum TrustAgentUiEvent implements UiEventLogger.UiEventEnum {
    TRUST_AGENT_NEWLY_UNLOCKED(1361);

    private final int metricId;

    TrustAgentUiEvent(int i) {
        this.metricId = i;
    }

    public final int getId() {
        return this.metricId;
    }
}
