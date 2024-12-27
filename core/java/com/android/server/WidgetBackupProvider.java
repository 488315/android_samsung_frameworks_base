package com.android.server;

import java.util.List;

public interface WidgetBackupProvider {
    List<String> getWidgetParticipants(int i);

    byte[] getWidgetState(String str, int i);

    void restoreWidgetState(String str, byte[] bArr, int i);

    void systemRestoreFinished(int i);

    void systemRestoreStarting(int i);
}
