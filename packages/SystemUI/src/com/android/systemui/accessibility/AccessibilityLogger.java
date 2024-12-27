package com.android.systemui.accessibility;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.util.time.SystemClock;
import kotlin.enums.EnumEntriesKt;

public final class AccessibilityLogger {
    public final SystemClock clock;
    public UiEventLogger.UiEventEnum lastEventThrottled;
    public long lastTimeThrottledMs;
    public final UiEventLogger uiEventLogger;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class MagnificationSettingsEvent implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ MagnificationSettingsEvent[] $VALUES;
        public static final MagnificationSettingsEvent MAGNIFICATION_SETTINGS_PANEL_CLOSED;
        public static final MagnificationSettingsEvent MAGNIFICATION_SETTINGS_PANEL_OPENED;
        public static final MagnificationSettingsEvent MAGNIFICATION_SETTINGS_SIZE_EDITING_ACTIVATED;
        public static final MagnificationSettingsEvent MAGNIFICATION_SETTINGS_WINDOW_SIZE_SELECTED;
        public static final MagnificationSettingsEvent MAGNIFICATION_SETTINGS_ZOOM_SLIDER_CHANGED;
        private final int id;

        static {
            MagnificationSettingsEvent magnificationSettingsEvent = new MagnificationSettingsEvent("MAGNIFICATION_SETTINGS_PANEL_OPENED", 0, 1381);
            MAGNIFICATION_SETTINGS_PANEL_OPENED = magnificationSettingsEvent;
            MagnificationSettingsEvent magnificationSettingsEvent2 = new MagnificationSettingsEvent("MAGNIFICATION_SETTINGS_PANEL_CLOSED", 1, 1382);
            MAGNIFICATION_SETTINGS_PANEL_CLOSED = magnificationSettingsEvent2;
            MagnificationSettingsEvent magnificationSettingsEvent3 = new MagnificationSettingsEvent("MAGNIFICATION_SETTINGS_SIZE_EDITING_ACTIVATED", 2, 1383);
            MAGNIFICATION_SETTINGS_SIZE_EDITING_ACTIVATED = magnificationSettingsEvent3;
            MagnificationSettingsEvent magnificationSettingsEvent4 = new MagnificationSettingsEvent("MAGNIFICATION_SETTINGS_SIZE_EDITING_DEACTIVATED", 3, 1384);
            MagnificationSettingsEvent magnificationSettingsEvent5 = new MagnificationSettingsEvent("MAGNIFICATION_SETTINGS_ZOOM_SLIDER_CHANGED", 4, 1385);
            MAGNIFICATION_SETTINGS_ZOOM_SLIDER_CHANGED = magnificationSettingsEvent5;
            MagnificationSettingsEvent magnificationSettingsEvent6 = new MagnificationSettingsEvent("MAGNIFICATION_SETTINGS_WINDOW_SIZE_SELECTED", 5, 1386);
            MAGNIFICATION_SETTINGS_WINDOW_SIZE_SELECTED = magnificationSettingsEvent6;
            MagnificationSettingsEvent[] magnificationSettingsEventArr = {magnificationSettingsEvent, magnificationSettingsEvent2, magnificationSettingsEvent3, magnificationSettingsEvent4, magnificationSettingsEvent5, magnificationSettingsEvent6};
            $VALUES = magnificationSettingsEventArr;
            EnumEntriesKt.enumEntries(magnificationSettingsEventArr);
        }

        private MagnificationSettingsEvent(String str, int i, int i2) {
            this.id = i2;
        }

        public static MagnificationSettingsEvent valueOf(String str) {
            return (MagnificationSettingsEvent) Enum.valueOf(MagnificationSettingsEvent.class, str);
        }

        public static MagnificationSettingsEvent[] values() {
            return (MagnificationSettingsEvent[]) $VALUES.clone();
        }

        public final int getId() {
            return this.id;
        }
    }

    public AccessibilityLogger(UiEventLogger uiEventLogger, SystemClock systemClock) {
        this.uiEventLogger = uiEventLogger;
        this.clock = systemClock;
    }
}
