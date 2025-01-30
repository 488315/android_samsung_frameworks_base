package android.p009os.health;

import android.content.Context;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import com.android.internal.app.IBatteryStats;

/* loaded from: classes3.dex */
public class SystemHealthManager {
    private final IBatteryStats mBatteryStats;

    public SystemHealthManager() {
        this(IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")));
    }

    public SystemHealthManager(IBatteryStats batteryStats) {
        this.mBatteryStats = batteryStats;
    }

    public static SystemHealthManager from(Context context) {
        return (SystemHealthManager) context.getSystemService(Context.SYSTEM_HEALTH_SERVICE);
    }

    public HealthStats takeUidSnapshot(int uid) {
        try {
            HealthStatsParceler parceler = this.mBatteryStats.takeUidSnapshot(uid);
            return parceler.getHealthStats();
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public HealthStats takeMyUidSnapshot() {
        return takeUidSnapshot(Process.myUid());
    }

    public HealthStats[] takeUidSnapshots(int[] uids) {
        try {
            HealthStatsParceler[] parcelers = this.mBatteryStats.takeUidSnapshots(uids);
            HealthStats[] results = new HealthStats[uids.length];
            int N = uids.length;
            for (int i = 0; i < N; i++) {
                results[i] = parcelers[i].getHealthStats();
            }
            return results;
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }
}
