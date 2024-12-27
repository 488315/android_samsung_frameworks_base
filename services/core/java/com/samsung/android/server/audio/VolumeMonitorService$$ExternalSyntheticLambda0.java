package com.samsung.android.server.audio;

import android.os.Bundle;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.media.audiofx.SemVolumeMonitor;
import com.samsung.android.server.audio.VolumeMonitorService;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumeMonitorService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ VolumeMonitorService$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                VolumeMonitorService volumeMonitorService = (VolumeMonitorService) obj;
                volumeMonitorService.getClass();
                try {
                    volumeMonitorService.getUserContext().getContentResolver().call(VolumeMonitorService.authority, "resetData()", "", (Bundle) null);
                    break;
                } catch (Exception e) {
                    RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("resetData() "), "AS.VolumeMonitor");
                    return;
                }
            default:
                VolumeMonitorService volumeMonitorService2 = ((VolumeMonitorService.VolumeMonitorThread.VolumeMonitorHandler) obj).this$1.this$0;
                SemVolumeMonitor semVolumeMonitor = volumeMonitorService2.mSemVolumeMonitor;
                if (semVolumeMonitor == null) {
                    Log.e("AS.VolumeMonitor", "SemVolumeMonitor is null");
                    break;
                } else {
                    try {
                        volumeMonitorService2.getUserContext().getContentResolver().call(VolumeMonitorService.authority, "setVolumeData()", new String(semVolumeMonitor.getOneMinScoreStatus(FrameworkStatsLog.NOTIFICATION_REPORTED, 60), StandardCharsets.UTF_8), (Bundle) null);
                        break;
                    } catch (Exception e2) {
                        RCPManagerService$$ExternalSyntheticOutline0.m(e2, new StringBuilder("sendVolumeData() "), "AS.VolumeMonitor");
                    }
                }
        }
    }
}
