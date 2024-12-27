package com.android.systemui.qs;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;

public final class FgsManagerControllerImpl$logEvent$1 implements Runnable {
    public final /* synthetic */ int $event;
    public final /* synthetic */ String $packageName;
    public final /* synthetic */ boolean $stopped;
    public final /* synthetic */ long $timeLogged;
    public final /* synthetic */ long $timeStarted;
    public final /* synthetic */ int $userId;
    public final /* synthetic */ FgsManagerControllerImpl this$0;

    public FgsManagerControllerImpl$logEvent$1(FgsManagerControllerImpl fgsManagerControllerImpl, String str, int i, int i2, long j, long j2, boolean z) {
        this.this$0 = fgsManagerControllerImpl;
        this.$packageName = str;
        this.$userId = i;
        this.$event = i2;
        this.$timeLogged = j;
        this.$timeStarted = j2;
        this.$stopped = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int packageUidAsUser = this.this$0.packageManager.getPackageUidAsUser(this.$packageName, this.$userId);
        int i = this.$event;
        long j = this.$timeLogged - this.$timeStarted;
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(450);
        newBuilder.writeInt(packageUidAsUser);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeInt(i);
        newBuilder.writeLong(j);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
        if (this.this$0.secFgsManagerController != null) {
            boolean z = this.$stopped;
            String str = this.$packageName;
            long j2 = this.$timeLogged - this.$timeStarted;
            StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("saLog[stopped:", packageUidAsUser, "]: [uid:", z, "]:[packageName:");
            m.append(str);
            m.append("]:[duration:");
            m.append(j2);
            m.append("]");
            SecFgsManagerController.log(m.toString());
            if (z) {
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_FGS_STOP, str, j2);
            }
        }
    }
}
