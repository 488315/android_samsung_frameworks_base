package com.android.internal.app;

import com.android.internal.app.ChooserActivityLogger;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes5.dex */
public class ChooserActivityLoggerImpl implements ChooserActivityLogger {
    private static final int SHARESHEET_INSTANCE_ID_MAX = 8192;
    private static InstanceIdSequence sInstanceIdSequence;
    private InstanceId mInstanceId;
    private UiEventLogger mUiEventLogger = new UiEventLoggerImpl();

    @Override // com.android.internal.app.ChooserActivityLogger
    public void logShareStarted(int i, String str, String str2, int i2, int i3, boolean z, int i4, String str3) {
        FrameworkStatsLog.write(259, ChooserActivityLogger.SharesheetStartedEvent.SHARE_STARTED.getId(), str, getInstanceId().getId(), str2, i2, i3, z, typeFromPreviewInt(i4), typeFromIntentString(str3), 0, false);
    }

    @Override // com.android.internal.app.ChooserActivityLogger
    public void logShareTargetSelected(int i, String str, int i2, boolean z) {
        FrameworkStatsLog.write(260, ChooserActivityLogger.SharesheetTargetSelectedEvent.fromTargetType(i).getId(), str, getInstanceId().getId(), i2, z);
    }

    @Override // com.android.internal.app.ChooserActivityLogger
    public void log(UiEventLogger.UiEventEnum uiEventEnum, InstanceId instanceId) {
        this.mUiEventLogger.logWithInstanceId(uiEventEnum, 0, null, instanceId);
    }

    @Override // com.android.internal.app.ChooserActivityLogger
    public InstanceId getInstanceId() {
        if (this.mInstanceId == null) {
            if (sInstanceIdSequence == null) {
                sInstanceIdSequence = new InstanceIdSequence(8192);
            }
            this.mInstanceId = sInstanceIdSequence.newInstanceId();
        }
        return this.mInstanceId;
    }
}
