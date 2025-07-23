package com.android.internal.logging.testing;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes5.dex */
public class UiEventLoggerFake implements UiEventLogger {
    private List<FakeUiEvent> mLogs = new LinkedList();

    public static class FakeUiEvent {
        public final int eventId;
        public final InstanceId instanceId;
        public final String packageName;
        public final int position;
        public final int uid;

        FakeUiEvent(int i, int i2, String str) {
            this.eventId = i;
            this.uid = i2;
            this.packageName = str;
            this.instanceId = null;
            this.position = 0;
        }

        FakeUiEvent(int i, int i2, String str, InstanceId instanceId) {
            this.eventId = i;
            this.uid = i2;
            this.packageName = str;
            this.instanceId = instanceId;
            this.position = 0;
        }

        FakeUiEvent(int i, int i2, String str, InstanceId instanceId, int i3) {
            this.eventId = i;
            this.uid = i2;
            this.packageName = str;
            this.instanceId = instanceId;
            this.position = i3;
        }
    }

    public List<FakeUiEvent> getLogs() {
        return this.mLogs;
    }

    public int numLogs() {
        return this.mLogs.size();
    }

    public FakeUiEvent get(int i) {
        return this.mLogs.get(i);
    }

    public int eventId(int i) {
        return this.mLogs.get(i).eventId;
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum) {
        log(uiEventEnum, 0, null);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum, InstanceId instanceId) {
        logWithInstanceId(uiEventEnum, 0, null, instanceId);
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum, int i, String str) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new FakeUiEvent(id, i, str));
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceId(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new FakeUiEvent(id, i, str, instanceId));
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new FakeUiEvent(id, i, str, null, i2));
        }
    }

    @Override // com.android.internal.logging.UiEventLogger
    public void logWithInstanceIdAndPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId, int i2) {
        int id = uiEventEnum.getId();
        if (id > 0) {
            this.mLogs.add(new FakeUiEvent(id, i, str, instanceId, i2));
        }
    }
}
