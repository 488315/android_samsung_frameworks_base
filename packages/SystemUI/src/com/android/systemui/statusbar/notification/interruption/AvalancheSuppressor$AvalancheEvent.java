package com.android.systemui.statusbar.notification.interruption;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

public final class AvalancheSuppressor$AvalancheEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ AvalancheSuppressor$AvalancheEvent[] $VALUES;
    public static final AvalancheSuppressor$AvalancheEvent AVALANCHE_SUPPRESSOR_RECEIVED_TRIGGERING_EVENT;
    private final int id;

    static {
        AvalancheSuppressor$AvalancheEvent avalancheSuppressor$AvalancheEvent = new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_RECEIVED_TRIGGERING_EVENT", 0, 1824);
        AVALANCHE_SUPPRESSOR_RECEIVED_TRIGGERING_EVENT = avalancheSuppressor$AvalancheEvent;
        AvalancheSuppressor$AvalancheEvent[] avalancheSuppressor$AvalancheEventArr = {avalancheSuppressor$AvalancheEvent, new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_SUPPRESSED", 1, 1825), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_NEW_CONVERSATION", 2, 1826), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_PRIORITY_CONVERSATION", 3, 1827), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_CALL_STYLE", 4, 1828), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_CATEGORY_REMINDER", 5, 1829), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_CATEGORY_EVENT", 6, 1830), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_FSI_WITH_PERMISSION", 7, 1831), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_COLORIZED", 8, 1832), new AvalancheSuppressor$AvalancheEvent("AVALANCHE_SUPPRESSOR_HUN_ALLOWED_EMERGENCY", 9, 1833)};
        $VALUES = avalancheSuppressor$AvalancheEventArr;
        EnumEntriesKt.enumEntries(avalancheSuppressor$AvalancheEventArr);
    }

    private AvalancheSuppressor$AvalancheEvent(String str, int i, int i2) {
        this.id = i2;
    }

    public static AvalancheSuppressor$AvalancheEvent valueOf(String str) {
        return (AvalancheSuppressor$AvalancheEvent) Enum.valueOf(AvalancheSuppressor$AvalancheEvent.class, str);
    }

    public static AvalancheSuppressor$AvalancheEvent[] values() {
        return (AvalancheSuppressor$AvalancheEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.id;
    }
}
