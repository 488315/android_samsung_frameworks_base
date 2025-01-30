package com.android.server.power.stats;

import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import com.android.internal.os.PowerProfile;

/* loaded from: classes3.dex */
public class PowerSharingCalculator extends PowerCalculator {
  @Override // com.android.server.power.stats.PowerCalculator
  public boolean isPowerComponentSupported(int i) {
    return i == 18;
  }

  public PowerSharingCalculator(PowerProfile powerProfile) {}

  @Override // com.android.server.power.stats.PowerCalculator
  public void calculate(
      BatteryUsageStats.Builder builder,
      BatteryStats batteryStats,
      long j,
      long j2,
      BatteryUsageStatsQuery batteryUsageStatsQuery) {
    long txPowerSharingTime = batteryStats.getTxPowerSharingTime(j, 0) / 1000;
    double txSharingDischargeAmount = batteryStats.getTxSharingDischargeAmount(0);
    if (txSharingDischargeAmount != 0.0d) {
      builder
          .getAggregateBatteryConsumerBuilder(0)
          .setConsumedPower(18, txSharingDischargeAmount, 1)
          .setUsageDurationMillis(18, txPowerSharingTime);
    }
  }
}
