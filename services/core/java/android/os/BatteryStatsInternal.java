package android.os;

import android.net.Network;
import com.android.server.power.stats.SystemServerCpuThreadReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BatteryStatsInternal {
  public static final int CPU_WAKEUP_SUBSYSTEM_ALARM = 1;
  public static final int CPU_WAKEUP_SUBSYSTEM_CELLULAR_DATA = 5;
  public static final int CPU_WAKEUP_SUBSYSTEM_SENSOR = 4;
  public static final int CPU_WAKEUP_SUBSYSTEM_SOUND_TRIGGER = 3;
  public static final int CPU_WAKEUP_SUBSYSTEM_UNKNOWN = -1;
  public static final int CPU_WAKEUP_SUBSYSTEM_WIFI = 2;

  @Retention(RetentionPolicy.SOURCE)
  public @interface CpuWakeupSubsystem {}

  public abstract List getBatteryUsageStats(List list);

  public abstract String[] getMobileIfaces();

  public abstract SystemServerCpuThreadReader.SystemServiceCpuThreadTimes
      getSystemServiceCpuThreadTimes();

  public abstract String[] getWifiIfaces();

  public abstract void noteBinderCallStats(int i, long j, Collection collection);

  public abstract void noteBinderThreadNativeIds(int[] iArr);

  public abstract void noteCpuWakingNetworkPacket(Network network, long j, int i);

  public abstract void noteJobsDeferred(int i, int i2, long j);

  public abstract void noteWakingAlarmBatch(long j, int... iArr);

  public abstract void noteWakingSoundTrigger(long j, int i);
}
