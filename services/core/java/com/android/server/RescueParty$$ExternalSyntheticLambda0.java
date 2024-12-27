package com.android.server;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemProperties;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RescueParty$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ RescueParty$$ExternalSyntheticLambda0(
            Context context, String str, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = context;
        this.f$1 = str;
        this.f$2 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Context context = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                try {
                    PowerManager powerManager =
                            (PowerManager) context.getSystemService(PowerManager.class);
                    String str2 = "";
                    if (powerManager == null) {
                        StringBuilder sb = new StringBuilder("reboot,RescueParty");
                        if (str != null) {
                            str2 = " by ".concat(str);
                        }
                        sb.append(str2);
                        SystemProperties.set("sys.powerctl", sb.toString());
                        break;
                    } else {
                        StringBuilder sb2 = new StringBuilder("RescueParty");
                        if (str != null) {
                            str2 = " by ".concat(str);
                        }
                        sb2.append(str2);
                        powerManager.reboot(sb2.toString());
                        break;
                    }
                } catch (Throwable th) {
                    RescueParty.logRescueException(i, str, th);
                    return;
                }
            default:
                Context context2 = this.f$0;
                String str3 = this.f$1;
                int i2 = this.f$2;
                try {
                    PowerManager powerManager2 =
                            (PowerManager) context2.getSystemService(PowerManager.class);
                    String str4 = "";
                    if (powerManager2 == null) {
                        StringBuilder sb3 = new StringBuilder("reboot,RescueParty");
                        if (str3 != null) {
                            str4 = " by ".concat(str3);
                        }
                        sb3.append(str4);
                        SystemProperties.set("sys.powerctl", sb3.toString());
                        break;
                    } else {
                        StringBuilder sb4 = new StringBuilder("RescueParty");
                        if (str3 != null) {
                            str4 = " by ".concat(str3);
                        }
                        sb4.append(str4);
                        powerManager2.reboot(sb4.toString());
                        break;
                    }
                } catch (Throwable th2) {
                    RescueParty.logRescueException(i2, str3, th2);
                }
        }
    }
}
