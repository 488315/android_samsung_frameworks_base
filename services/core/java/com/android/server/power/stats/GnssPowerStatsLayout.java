package com.android.server.power.stats;

import android.os.PersistableBundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GnssPowerStatsLayout extends BinaryStatePowerStatsLayout {
    public int mDeviceSignalLevelTimePosition = addDeviceSection(2, 0, "level");
    public int mUidSignalLevelTimePosition = addUidSection(2, 0, "level");

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void fromExtras(PersistableBundle persistableBundle) {
        super.fromExtras(persistableBundle);
        this.mDeviceSignalLevelTimePosition = persistableBundle.getInt("dt-sig");
        this.mUidSignalLevelTimePosition = persistableBundle.getInt("ut-sig");
    }

    @Override // com.android.server.power.stats.PowerStatsLayout
    public final void toExtras(PersistableBundle persistableBundle) {
        super.toExtras(persistableBundle);
        persistableBundle.putInt("dt-sig", this.mDeviceSignalLevelTimePosition);
        persistableBundle.putInt("ut-sig", this.mUidSignalLevelTimePosition);
    }
}
