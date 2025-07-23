package com.android.server;

import java.util.List;

/* loaded from: classes6.dex */
public class AppWidgetBackupBridge {
    private static WidgetBackupProvider sAppWidgetService;

    public static void register(WidgetBackupProvider widgetBackupProvider) {
        sAppWidgetService = widgetBackupProvider;
    }

    public static List<String> getWidgetParticipants(int i) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            return widgetBackupProvider.getWidgetParticipants(i);
        }
        return null;
    }

    public static byte[] getWidgetState(String str, int i) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            return widgetBackupProvider.getWidgetState(str, i);
        }
        return null;
    }

    public static void systemRestoreStarting(int i) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            widgetBackupProvider.systemRestoreStarting(i);
        }
    }

    public static void restoreWidgetState(String str, byte[] bArr, int i) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            widgetBackupProvider.restoreWidgetState(str, bArr, i);
        }
    }

    public static void systemRestoreFinished(int i) {
        WidgetBackupProvider widgetBackupProvider = sAppWidgetService;
        if (widgetBackupProvider != null) {
            widgetBackupProvider.systemRestoreFinished(i);
        }
    }
}
