package com.android.systemui.keyguard.shared.constants;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class TrustAgentUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ TrustAgentUiEvent[] $VALUES;
    public static final TrustAgentUiEvent TRUST_AGENT_NEWLY_UNLOCKED;
    private final int metricId;

    static {
        TrustAgentUiEvent trustAgentUiEvent = new TrustAgentUiEvent("TRUST_AGENT_NEWLY_UNLOCKED", 0, 1361);
        TRUST_AGENT_NEWLY_UNLOCKED = trustAgentUiEvent;
        TrustAgentUiEvent[] trustAgentUiEventArr = {trustAgentUiEvent};
        $VALUES = trustAgentUiEventArr;
        EnumEntriesKt.enumEntries(trustAgentUiEventArr);
    }

    private TrustAgentUiEvent(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static TrustAgentUiEvent valueOf(String str) {
        return (TrustAgentUiEvent) Enum.valueOf(TrustAgentUiEvent.class, str);
    }

    public static TrustAgentUiEvent[] values() {
        return (TrustAgentUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.metricId;
    }
}
