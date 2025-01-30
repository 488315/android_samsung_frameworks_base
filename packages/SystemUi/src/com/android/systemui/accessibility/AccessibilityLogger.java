package com.android.systemui.accessibility;

import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityLogger {
    public final UiEventLogger uiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum MagnificationSettingsEvent implements UiEventLogger.UiEventEnum {
        MAGNIFICATION_SETTINGS_PANEL_OPENED(1381),
        MAGNIFICATION_SETTINGS_PANEL_CLOSED(1382),
        MAGNIFICATION_SETTINGS_SIZE_EDITING_ACTIVATED(1383),
        /* JADX INFO: Fake field, exist only in values array */
        MAGNIFICATION_SETTINGS_SIZE_EDITING_DEACTIVATED(1384),
        MAGNIFICATION_SETTINGS_WINDOW_SIZE_SELECTED(1386);


        /* renamed from: id */
        private final int f224id;

        MagnificationSettingsEvent(int i) {
            this.f224id = i;
        }

        public final int getId() {
            return this.f224id;
        }
    }

    public AccessibilityLogger(UiEventLogger uiEventLogger) {
        this.uiEventLogger = uiEventLogger;
    }
}
