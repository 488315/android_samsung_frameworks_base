package com.android.server.power.stats;

import android.os.PersistableBundle;

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
