package com.android.server.selinux;

import java.util.concurrent.atomic.AtomicBoolean;

public final class SelinuxAuditLogsJob {
    public final SelinuxAuditLogsCollector mAuditLogsCollector;
    public final AtomicBoolean mIsRunning = new AtomicBoolean(false);

    public SelinuxAuditLogsJob(SelinuxAuditLogsCollector selinuxAuditLogsCollector) {
        this.mAuditLogsCollector = selinuxAuditLogsCollector;
    }
}
