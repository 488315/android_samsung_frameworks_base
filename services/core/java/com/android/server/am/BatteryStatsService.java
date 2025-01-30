package com.android.server.am;

import android.R;
import android.app.StatsManager;
import android.app.usage.NetworkStatsManager;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.State;
import android.hardware.power.stats.StateResidency;
import android.hardware.power.stats.StateResidencyResult;
import android.net.ConnectivityManager;
import android.net.INetworkManagementEventObserver;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.BatteryManagerInternal;
import android.os.BatteryStats;
import android.os.BatteryUsageStats;
import android.os.BatteryUsageStatsQuery;
import android.os.Binder;
import android.os.BluetoothBatteryStats;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.INetworkManagementService;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.PowerManagerInternal;
import android.os.PowerSaveState;
import android.os.Process;
import android.os.RemoteException;
import android.os.SemBatterySipper;
import android.os.SemModemActivityInfo;
import android.os.ServiceManager;
import android.os.SpeakerOutEnergyInfo;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.WakeLockStats;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiActivityEnergyInfo;
import android.os.connectivity.WifiBatteryStats;
import android.os.health.HealthStatsParceler;
import android.os.health.HealthStatsWriter;
import android.os.health.UidHealthStats;
import android.os.BatteryStatsInternal;
import android.power.PowerStatsInternal;
import android.provider.Settings;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.internal.app.IBatteryStats;
import com.android.internal.app.IBatteryStatsCallback;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.PowerProfile;
import com.android.internal.os.RailStats;
import com.android.internal.os.RpmStats;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ParseUtils;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.net.module.util.NetworkCapabilitiesUtils;
import com.android.server.LocalServices;
import com.android.server.Watchdog;
import com.android.server.net.BaseNetworkObserver;
import com.android.server.pm.UserManagerInternal;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.stats.BatteryExternalStatsWorker;
import com.android.server.power.stats.BatteryStatsImpl;
import com.android.server.power.stats.BatteryUsageStatsProvider;
import com.android.server.power.stats.BatteryUsageStatsStore;
import com.android.server.power.stats.SemBatteryUsageStatsProvider;
import com.android.server.power.stats.SystemServerCpuThreadReader;
import com.android.server.power.stats.wakeups.CpuWakeupStats;
import com.samsung.android.os.SemCompanionDeviceBatteryInfo;
import com.samsung.android.server.battery.DeviceBatteryInfoService;
import com.samsung.android.server.battery.DeviceBatteryInfoServiceInternal;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class BatteryStatsService extends IBatteryStats.Stub
    implements PowerManagerInternal.LowPowerModeListener,
        BatteryStatsImpl.PlatformIdleStateCallback,
        BatteryStatsImpl.EnergyStatsRetriever,
        Watchdog.Monitor {
  public static IBatteryStats sService;
  public BatteryManagerInternal mBatteryManagerInternal;
  public final BatteryUsageStatsProvider mBatteryUsageStatsProvider;
  public final BatteryUsageStatsStore mBatteryUsageStatsStore;
  public final Context mContext;
  public final CpuWakeupStats mCpuWakeupStats;
  public DeviceBatteryInfoServiceInternal mDeviceBatteryInfoServiceInternal;
  public final Handler mHandler;
  public final HandlerThread mHandlerThread;
  public final PowerProfile mPowerProfile;
  public final SemBatteryUsageStatsProvider mSemBatteryUsageStatsProvider;
  public final BatteryStatsImpl mStats;
  public final BatteryStatsImpl.UserInfoProvider mUserManagerUserInfoProvider;
  public final BatteryExternalStatsWorker mWorker;
  public volatile boolean mMonitorEnabled = true;
  public CharsetDecoder mDecoderStat =
      StandardCharsets.UTF_8
          .newDecoder()
          .onMalformedInput(CodingErrorAction.REPLACE)
          .onUnmappableCharacter(CodingErrorAction.REPLACE)
          .replaceWith("?");
  public final Object mLock = new Object();
  public final Object mPowerStatsLock = new Object();
  public PowerStatsInternal mPowerStatsInternal = null;
  public Map mEntityNames = new HashMap();
  public Map mStateNames = new HashMap();
  public int mLastPowerStateFromRadio = 1;
  public int mLastPowerStateFromWifi = 1;
  public final INetworkManagementEventObserver mActivityChangeObserver =
      new BaseNetworkObserver() { // from class: com.android.server.am.BatteryStatsService.1
        public void interfaceClassDataActivityChanged(int i, boolean z, long j, int i2) {
          int i3 = z ? 3 : 1;
          if (j <= 0) {
            j = SystemClock.elapsedRealtimeNanos();
          }
          if (i == 0) {
            BatteryStatsService.this.noteMobileRadioPowerState(i3, j, i2);
            return;
          }
          if (i == 1) {
            BatteryStatsService.this.noteWifiRadioPowerState(i3, j, i2);
            return;
          }
          Slog.d(
              "BatteryStatsService",
              "Received unexpected transport in interfaceClassDataActivityChanged unexpected type: "
                  + i);
        }
      };
  public boolean mSystemServicesReady = false;
  public ConnectivityManager.NetworkCallback mNetworkCallback =
      new ConnectivityManager
          .NetworkCallback() { // from class: com.android.server.am.BatteryStatsService.2
        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(
            Network network, NetworkCapabilities networkCapabilities) {
          BatteryStatsService.this.noteConnectivityChanged(
              NetworkCapabilitiesUtils.getDisplayTransport(networkCapabilities.getTransportTypes()),
              networkCapabilities.hasCapability(21) ? "CONNECTED" : "SUSPENDED");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
          BatteryStatsService.this.noteConnectivityChanged(-1, "DISCONNECTED");
        }
      };

  private native void getRailEnergyPowerStats(RailStats railStats);

  /* JADX INFO: Access modifiers changed from: private */
  public static native int nativeWaitWakeup(ByteBuffer byteBuffer);

  public int getServiceType() {
    return 9;
  }

  public final void populatePowerEntityMaps() {
    PowerEntity[] powerEntityInfo = this.mPowerStatsInternal.getPowerEntityInfo();
    if (powerEntityInfo == null) {
      return;
    }
    for (PowerEntity powerEntity : powerEntityInfo) {
      HashMap hashMap = new HashMap();
      int i = 0;
      while (true) {
        State[] stateArr = powerEntity.states;
        if (i < stateArr.length) {
          State state = stateArr[i];
          hashMap.put(Integer.valueOf(state.f9id), state.name);
          i++;
        }
      }
      this.mEntityNames.put(Integer.valueOf(powerEntity.f8id), powerEntity.name);
      this.mStateNames.put(Integer.valueOf(powerEntity.f8id), hashMap);
    }
  }

  @Override // com.android.server.power.stats.BatteryStatsImpl.PlatformIdleStateCallback
  public void fillLowPowerStats(RpmStats rpmStats) {
    synchronized (this.mPowerStatsLock) {
      if (this.mPowerStatsInternal != null
          && !this.mEntityNames.isEmpty()
          && !this.mStateNames.isEmpty()) {
        try {
          StateResidencyResult[] stateResidencyResultArr =
              (StateResidencyResult[])
                  this.mPowerStatsInternal
                      .getStateResidencyAsync(new int[0])
                      .get(2000L, TimeUnit.MILLISECONDS);
          if (stateResidencyResultArr == null) {
            return;
          }
          for (StateResidencyResult stateResidencyResult : stateResidencyResultArr) {
            RpmStats.PowerStateSubsystem subsystem =
                rpmStats.getSubsystem(
                    (String) this.mEntityNames.get(Integer.valueOf(stateResidencyResult.f11id)));
            int i = 0;
            while (true) {
              StateResidency[] stateResidencyArr = stateResidencyResult.stateResidencyData;
              if (i < stateResidencyArr.length) {
                StateResidency stateResidency = stateResidencyArr[i];
                subsystem.putState(
                    (String)
                        ((Map) this.mStateNames.get(Integer.valueOf(stateResidencyResult.f11id)))
                            .get(Integer.valueOf(stateResidency.f10id)),
                    stateResidency.totalTimeInStateMs,
                    (int) stateResidency.totalStateEntryCount);
                i++;
              }
            }
          }
        } catch (Exception e) {
          Slog.e("BatteryStatsService", "Failed to getStateResidencyAsync", e);
        }
      }
    }
  }

  @Override // com.android.server.power.stats.BatteryStatsImpl.EnergyStatsRetriever
  public void fillRailDataStats(RailStats railStats) {
    getRailEnergyPowerStats(railStats);
  }

  @Override // com.android.server.power.stats.BatteryStatsImpl.PlatformIdleStateCallback
  public String getSubsystemLowPowerStats() {
    synchronized (this.mPowerStatsLock) {
      if (this.mPowerStatsInternal != null
          && !this.mEntityNames.isEmpty()
          && !this.mStateNames.isEmpty()) {
        try {
          StateResidencyResult[] stateResidencyResultArr =
              (StateResidencyResult[])
                  this.mPowerStatsInternal
                      .getStateResidencyAsync(new int[0])
                      .get(2000L, TimeUnit.MILLISECONDS);
          if (stateResidencyResultArr == null || stateResidencyResultArr.length == 0) {
            return "Empty";
          }
          StringBuilder sb = new StringBuilder("SubsystemPowerState");
          int i = 32768;
          int i2 = 0;
          while (true) {
            if (i2 >= stateResidencyResultArr.length) {
              break;
            }
            StateResidencyResult stateResidencyResult = stateResidencyResultArr[i2];
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" subsystem_" + i2);
            sb2.append(
                " name="
                    + ((String)
                        this.mEntityNames.get(Integer.valueOf(stateResidencyResult.f11id))));
            int i3 = 0;
            while (true) {
              StateResidency[] stateResidencyArr = stateResidencyResult.stateResidencyData;
              if (i3 >= stateResidencyArr.length) {
                break;
              }
              StateResidency stateResidency = stateResidencyArr[i3];
              sb2.append(" state_" + i3);
              sb2.append(
                  " name="
                      + ((String)
                          ((Map) this.mStateNames.get(Integer.valueOf(stateResidencyResult.f11id)))
                              .get(Integer.valueOf(stateResidency.f10id))));
              sb2.append(" time=" + stateResidency.totalTimeInStateMs);
              sb2.append(" count=" + stateResidency.totalStateEntryCount);
              sb2.append(" last entry=" + stateResidency.lastEntryTimestampMs);
              i3++;
            }
            if (sb2.length() <= i) {
              i -= sb2.length();
              sb.append((CharSequence) sb2);
              i2++;
            } else {
              Slog.e("BatteryStatsService", "getSubsystemLowPowerStats: buffer not enough");
              break;
            }
          }
          return sb.toString();
        } catch (Exception e) {
          Slog.e("BatteryStatsService", "Failed to getStateResidencyAsync", e);
          return "Empty";
        }
      }
      return "Empty";
    }
  }

  public BatteryStatsService(Context context, File file, Handler handler) {
    this.mContext = context;
    BatteryStatsImpl.UserInfoProvider userInfoProvider =
        new BatteryStatsImpl
            .UserInfoProvider() { // from class: com.android.server.am.BatteryStatsService.3
          public UserManagerInternal umi;

          @Override // com.android.server.power.stats.BatteryStatsImpl.UserInfoProvider
          public int[] getUserIds() {
            if (this.umi == null) {
              this.umi = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            }
            UserManagerInternal userManagerInternal = this.umi;
            if (userManagerInternal != null) {
              return userManagerInternal.getUserIds();
            }
            return null;
          }
        };
    this.mUserManagerUserInfoProvider = userInfoProvider;
    HandlerThread handlerThread = new HandlerThread("batterystats-handler");
    this.mHandlerThread = handlerThread;
    handlerThread.start();
    Handler handler2 = new Handler(handlerThread.getLooper());
    this.mHandler = handler2;
    PowerProfile powerProfile = new PowerProfile(context);
    this.mPowerProfile = powerProfile;
    BatteryStatsImpl batteryStatsImpl =
        new BatteryStatsImpl(file, handler, this, this, userInfoProvider);
    this.mStats = batteryStatsImpl;
    BatteryExternalStatsWorker batteryExternalStatsWorker =
        new BatteryExternalStatsWorker(context, batteryStatsImpl);
    this.mWorker = batteryExternalStatsWorker;
    batteryStatsImpl.setExternalStatsSyncLocked(batteryExternalStatsWorker);
    batteryStatsImpl.setRadioScanningTimeoutLocked(
        context
                .getResources()
                .getInteger(R.integer.config_satellite_demo_mode_nb_iot_inactivity_timeout_millis)
            * 1000);
    batteryStatsImpl.setPowerProfileLocked(powerProfile);
    batteryStatsImpl.setBatteryStatsConfig(
        new BatteryStatsImpl.BatteryStatsConfig.Builder()
            .setResetOnUnplugHighBatteryLevel(
                context
                    .getResources()
                    .getBoolean(R.bool.config_batterySaverStickyBehaviourDisabled))
            .setResetOnUnplugAfterSignificantCharge(
                context.getResources().getBoolean(R.bool.config_awareSettingAvailable))
            .build());
    batteryStatsImpl.startTrackingSystemServerCpuTime();
    BatteryUsageStatsStore batteryUsageStatsStore =
        new BatteryUsageStatsStore(context, batteryStatsImpl, file, handler2);
    this.mBatteryUsageStatsStore = batteryUsageStatsStore;
    this.mBatteryUsageStatsProvider =
        new BatteryUsageStatsProvider(context, batteryStatsImpl, batteryUsageStatsStore);
    this.mSemBatteryUsageStatsProvider =
        new SemBatteryUsageStatsProvider(context, batteryStatsImpl);
    this.mCpuWakeupStats = new CpuWakeupStats(context, R.xml.password_kbd_qwerty_shifted, handler2);
  }

  public static BatteryStatsService create(
      Context context,
      File file,
      Handler handler,
      BatteryStatsImpl.BatteryCallback batteryCallback) {
    BatteryStatsService batteryStatsService = new BatteryStatsService(context, file, handler);
    batteryStatsService.mStats.setCallback(batteryCallback);
    synchronized (batteryStatsService.mStats) {
      batteryStatsService.mStats.readLocked();
    }
    batteryStatsService.scheduleWriteToDisk();
    return batteryStatsService;
  }

  public void publish() {
    LocalServices.addService(BatteryStatsInternal.class, new LocalService());
    ServiceManager.addService("batterystats", asBinder());
    LocalServices.addService(
        DeviceBatteryInfoServiceInternal.class, new DeviceBatteryInfoService());
  }

  public void systemServicesReady() {
    this.mStats.systemServicesReady(this.mContext);
    this.mCpuWakeupStats.systemServicesReady();
    this.mWorker.systemServicesReady();
    this.mSystemServicesReady = true;
    INetworkManagementService asInterface =
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
    try {
      asInterface.registerObserver(this.mActivityChangeObserver);
      connectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback);
    } catch (RemoteException e) {
      Slog.e("BatteryStatsService", "Could not register INetworkManagement event observer " + e);
    }
    synchronized (this.mPowerStatsLock) {
      PowerStatsInternal powerStatsInternal =
          (PowerStatsInternal) LocalServices.getService(PowerStatsInternal.class);
      this.mPowerStatsInternal = powerStatsInternal;
      if (powerStatsInternal != null) {
        populatePowerEntityMaps();
      } else {
        Slog.e("BatteryStatsService", "Could not register PowerStatsInternal");
      }
    }
    this.mBatteryManagerInternal =
        (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
    DeviceBatteryInfoServiceInternal deviceBatteryInfoServiceInternal =
        (DeviceBatteryInfoServiceInternal)
            LocalServices.getService(DeviceBatteryInfoServiceInternal.class);
    this.mDeviceBatteryInfoServiceInternal = deviceBatteryInfoServiceInternal;
    deviceBatteryInfoServiceInternal.systemServicesReady(this.mContext);
    Watchdog.getInstance().addMonitor(this);
    new DataConnectionStats(this.mContext, this.mHandler).startMonitoring();
    registerStatsCallbacks();
  }

  public void onSystemReady() {
    this.mStats.onSystemReady();
    this.mBatteryUsageStatsStore.onSystemReady();
  }

  public final class LocalService extends BatteryStatsInternal {
    public LocalService() {}

    @Override // android.os.BatteryStatsInternal
    public String[] getWifiIfaces() {
      return (String[]) BatteryStatsService.this.mStats.getWifiIfaces().clone();
    }

    @Override // android.os.BatteryStatsInternal
    public String[] getMobileIfaces() {
      return (String[]) BatteryStatsService.this.mStats.getMobileIfaces().clone();
    }

    @Override // android.os.BatteryStatsInternal
    public SystemServerCpuThreadReader.SystemServiceCpuThreadTimes
        getSystemServiceCpuThreadTimes() {
      return BatteryStatsService.this.mStats.getSystemServiceCpuThreadTimes();
    }

    @Override // android.os.BatteryStatsInternal
    public List getBatteryUsageStats(List list) {
      return BatteryStatsService.this.getBatteryUsageStats(list);
    }

    @Override // android.os.BatteryStatsInternal
    public void noteJobsDeferred(int i, int i2, long j) {
      BatteryStatsService.this.noteJobsDeferred(i, i2, j);
    }

    public final int transportToSubsystem(NetworkCapabilities networkCapabilities) {
      if (networkCapabilities.hasTransport(1)) {
        return 2;
      }
      return networkCapabilities.hasTransport(0) ? 5 : -1;
    }

    @Override // android.os.BatteryStatsInternal
    public void noteCpuWakingNetworkPacket(Network network, long j, int i) {
      if (i < 0) {
        Slog.e("BatteryStatsService", "Invalid uid for waking network packet: " + i);
        return;
      }
      int transportToSubsystem =
          transportToSubsystem(
              ((ConnectivityManager)
                      BatteryStatsService.this.mContext.getSystemService(ConnectivityManager.class))
                  .getNetworkCapabilities(network));
      if (transportToSubsystem == -1) {
        Slog.wtf(
            "BatteryStatsService",
            "Could not map transport for network: "
                + network
                + " while attributing wakeup by packet sent to uid: "
                + i);
        return;
      }
      BatteryStatsService.this.noteCpuWakingActivity(transportToSubsystem, j, i);
    }

    @Override // android.os.BatteryStatsInternal
    public void noteBinderCallStats(int i, long j, Collection collection) {
      synchronized (BatteryStatsService.this.mLock) {
        Handler handler = BatteryStatsService.this.mHandler;
        final BatteryStatsImpl batteryStatsImpl = BatteryStatsService.this.mStats;
        Objects.requireNonNull(batteryStatsImpl);
        handler.sendMessage(
            PooledLambda.obtainMessage(
                new QuintConsumer() { // from class:
                                      // com.android.server.am.BatteryStatsService$LocalService$$ExternalSyntheticLambda0
                  public final void accept(
                      Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    BatteryStatsImpl.this.noteBinderCallStats(
                        ((Integer) obj).intValue(),
                        ((Long) obj2).longValue(),
                        (Collection) obj3,
                        ((Long) obj4).longValue(),
                        ((Long) obj5).longValue());
                  }
                },
                Integer.valueOf(i),
                Long.valueOf(j),
                collection,
                Long.valueOf(SystemClock.elapsedRealtime()),
                Long.valueOf(SystemClock.uptimeMillis())));
      }
    }

    @Override // android.os.BatteryStatsInternal
    public void noteBinderThreadNativeIds(int[] iArr) {
      synchronized (BatteryStatsService.this.mLock) {
        BatteryStatsService.this.mStats.noteBinderThreadNativeIds(iArr);
      }
    }

    @Override // android.os.BatteryStatsInternal
    public void noteWakingSoundTrigger(long j, int i) {
      BatteryStatsService.this.noteCpuWakingActivity(3, j, i);
    }

    @Override // android.os.BatteryStatsInternal
    public void noteWakingAlarmBatch(long j, int... iArr) {
      BatteryStatsService.this.noteCpuWakingActivity(1, j, iArr);
    }
  }

  public void noteCpuWakingActivity(final int i, final long j, final int... iArr) {
    Objects.requireNonNull(iArr);
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda36
          @Override // java.lang.Runnable
          public final void run() {
            BatteryStatsService.this.lambda$noteCpuWakingActivity$0(i, j, iArr);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteCpuWakingActivity$0(int i, long j, int[] iArr) {
    this.mCpuWakeupStats.noteWakingActivity(i, j, iArr);
  }

  @Override // com.android.server.Watchdog.Monitor
  public void monitor() {
    if (this.mMonitorEnabled) {
      synchronized (this.mLock) {
      }
      synchronized (this.mStats) {
      }
    }
  }

  public static void awaitUninterruptibly(Future future) {
    while (true) {
      try {
        future.get();
        return;
      } catch (InterruptedException unused) {
      } catch (ExecutionException unused2) {
        return;
      }
    }
  }

  public final void syncStats(String str, int i) {
    awaitUninterruptibly(this.mWorker.scheduleSync(str, i));
  }

  public final void awaitCompletion() {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda27
          @Override // java.lang.Runnable
          public final void run() {
            countDownLatch.countDown();
          }
        });
    try {
      countDownLatch.await();
    } catch (InterruptedException unused) {
    }
  }

  public void initPowerManagement() {
    PowerManagerInternal powerManagerInternal =
        (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
    powerManagerInternal.registerLowPowerModeObserver(this);
    synchronized (this.mStats) {
      this.mStats.notePowerSaveModeLockedInit(
          powerManagerInternal.getLowPowerState(9).batterySaverEnabled,
          SystemClock.elapsedRealtime(),
          SystemClock.uptimeMillis());
    }
    new WakeupReasonThread().start();
  }

  public void shutdown() {
    Slog.w("BatteryStats", "Writing battery stats before shutdown...");
    awaitCompletion();
    syncStats("shutdown", 127);
    synchronized (this.mStats) {
      this.mStats.shutdownLocked();
    }
    this.mWorker.shutdown();
  }

  public static IBatteryStats getService() {
    IBatteryStats iBatteryStats = sService;
    if (iBatteryStats != null) {
      return iBatteryStats;
    }
    IBatteryStats asInterface =
        IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats"));
    sService = asInterface;
    return asInterface;
  }

  public void onLowPowerModeChanged(final PowerSaveState powerSaveState) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda85
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$onLowPowerModeChanged$2(
                  powerSaveState, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onLowPowerModeChanged$2(
      PowerSaveState powerSaveState, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.notePowerSaveModeLocked(powerSaveState.batterySaverEnabled, j, j2);
    }
  }

  public BatteryStatsImpl getActiveStatistics() {
    return this.mStats;
  }

  public void scheduleWriteToDisk() {
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda105
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$scheduleWriteToDisk$3();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleWriteToDisk$3() {
    this.mWorker.scheduleWrite();
  }

  public void removeUid(final int i) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$removeUid$4(i, elapsedRealtime);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeUid$4(int i, long j) {
    this.mCpuWakeupStats.onUidRemoved(i);
    synchronized (this.mStats) {
      this.mStats.removeUidStatsLocked(i, j);
    }
  }

  public void onCleanupUser(final int i) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda70
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$onCleanupUser$5(i, elapsedRealtime);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onCleanupUser$5(int i, long j) {
    synchronized (this.mStats) {
      this.mStats.onCleanupUserLocked(i, j);
    }
  }

  public void onUserRemoved(final int i) {
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$onUserRemoved$6(i);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onUserRemoved$6(int i) {
    synchronized (this.mStats) {
      this.mStats.onUserRemovedLocked(i);
    }
  }

  public void addIsolatedUid(final int i, final int i2) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda103
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$addIsolatedUid$7(
                  i, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$addIsolatedUid$7(int i, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.addIsolatedUidLocked(i, i2, j, j2);
    }
  }

  public void removeIsolatedUid(final int i, final int i2) {
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda73
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$removeIsolatedUid$8(i, i2);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$removeIsolatedUid$8(int i, int i2) {
    synchronized (this.mStats) {
      this.mStats.scheduleRemoveIsolatedUidLocked(i, i2);
    }
  }

  public void noteProcessStart(final String str, final int i) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda108
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteProcessStart$9(
                  str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(28, i, str, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteProcessStart$9(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteProcessStartLocked(str, i, j, j2);
    }
  }

  public void noteProcessCrash(final String str, final int i) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda106
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteProcessCrash$10(
                  str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(28, i, str, 2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteProcessCrash$10(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteProcessCrashLocked(str, i, j, j2);
    }
  }

  public void noteProcessAnr(final String str, final int i) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda115
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteProcessAnr$11(
                  str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteProcessAnr$11(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteProcessAnrLocked(str, i, j, j2);
    }
  }

  public void noteProcessFinish(final String str, final int i) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteProcessFinish$12(
                  str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(28, i, str, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteProcessFinish$12(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteProcessFinishLocked(str, i, j, j2);
    }
  }

  public void noteUidProcessState(final int i, final int i2) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteUidProcessState$13(
                  i, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteUidProcessState$13(int i, int i2, long j, long j2) {
    this.mCpuWakeupStats.noteUidProcessState(i, i2);
    synchronized (this.mStats) {
      this.mStats.noteUidProcessStateLocked(i, i2, j, j2);
    }
  }

  public List getBatteryUsageStats(List list) {
    super.getBatteryUsageStats_enforcePermission();
    awaitCompletion();
    if (this.mBatteryUsageStatsProvider.shouldUpdateStats(
        list, this.mWorker.getLastCollectionTimeStamp())) {
      syncStats("get-stats", 127);
    }
    return this.mBatteryUsageStatsProvider.getBatteryUsageStats(list);
  }

  public SemBatterySipper getSemBatteryUsageStats() {
    super.getSemBatteryUsageStats_enforcePermission();
    awaitCompletion();
    return this.mSemBatteryUsageStatsProvider.getBatteryUsageStats();
  }

  public final void registerStatsCallbacks() {
    StatsManager statsManager = (StatsManager) this.mContext.getSystemService(StatsManager.class);
    StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new StatsPullAtomCallbackImpl();
    statsManager.setPullAtomCallback(
        FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET,
        (StatsManager.PullAtomMetadata) null,
        BackgroundThread.getExecutor(),
        statsPullAtomCallbackImpl);
    statsManager.setPullAtomCallback(
        FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET_USING_POWER_PROFILE_MODEL,
        (StatsManager.PullAtomMetadata) null,
        BackgroundThread.getExecutor(),
        statsPullAtomCallbackImpl);
    statsManager.setPullAtomCallback(
        FrameworkStatsLog.BATTERY_USAGE_STATS_BEFORE_RESET,
        (StatsManager.PullAtomMetadata) null,
        BackgroundThread.getExecutor(),
        statsPullAtomCallbackImpl);
  }

  public class StatsPullAtomCallbackImpl implements StatsManager.StatsPullAtomCallback {
    public StatsPullAtomCallbackImpl() {}

    public int onPullAtom(int i, List list) {
      long startClockTime;
      BatteryUsageStats batteryUsageStats;
      switch (i) {
        case FrameworkStatsLog.BATTERY_USAGE_STATS_BEFORE_RESET /* 10111 */:
          long lastBatteryUsageStatsBeforeResetAtomPullTimestamp =
              BatteryStatsService.this.mBatteryUsageStatsStore
                  .getLastBatteryUsageStatsBeforeResetAtomPullTimestamp();
          synchronized (BatteryStatsService.this.mStats) {
            startClockTime = BatteryStatsService.this.mStats.getStartClockTime();
          }
          BatteryUsageStats batteryUsageStats2 =
              (BatteryUsageStats)
                  BatteryStatsService.this
                      .getBatteryUsageStats(
                          List.of(
                              new BatteryUsageStatsQuery.Builder()
                                  .setMaxStatsAgeMs(0L)
                                  .includeProcessStateData()
                                  .includeVirtualUids()
                                  .aggregateSnapshots(
                                      lastBatteryUsageStatsBeforeResetAtomPullTimestamp,
                                      startClockTime)
                                  .build()))
                      .get(0);
          BatteryStatsService.this.mBatteryUsageStatsStore
              .setLastBatteryUsageStatsBeforeResetAtomPullTimestamp(startClockTime);
          batteryUsageStats = batteryUsageStats2;
          break;
        case FrameworkStatsLog.BATTERY_USAGE_STATS_SINCE_RESET /* 10112 */:
          batteryUsageStats =
              (BatteryUsageStats)
                  BatteryStatsService.this
                      .getBatteryUsageStats(
                          List.of(
                              new BatteryUsageStatsQuery.Builder()
                                  .setMaxStatsAgeMs(0L)
                                  .includeProcessStateData()
                                  .includeVirtualUids()
                                  .includePowerModels()
                                  .build()))
                      .get(0);
          break;
        case FrameworkStatsLog
            .BATTERY_USAGE_STATS_SINCE_RESET_USING_POWER_PROFILE_MODEL /* 10113 */:
          batteryUsageStats =
              (BatteryUsageStats)
                  BatteryStatsService.this
                      .getBatteryUsageStats(
                          List.of(
                              new BatteryUsageStatsQuery.Builder()
                                  .setMaxStatsAgeMs(0L)
                                  .includeProcessStateData()
                                  .includeVirtualUids()
                                  .powerProfileModeledOnly()
                                  .includePowerModels()
                                  .build()))
                      .get(0);
          break;
        default:
          throw new UnsupportedOperationException("Unknown tagId=" + i);
      }
      list.add(FrameworkStatsLog.buildStatsEvent(i, batteryUsageStats.getStatsProto()));
      return 0;
    }
  }

  public boolean isCharging() {
    boolean isCharging;
    synchronized (this.mStats) {
      isCharging = this.mStats.isCharging();
    }
    return isCharging;
  }

  public long computeBatteryTimeRemaining() {
    long computeBatteryTimeRemaining;
    synchronized (this.mStats) {
      computeBatteryTimeRemaining =
          this.mStats.computeBatteryTimeRemaining(SystemClock.elapsedRealtime());
      if (computeBatteryTimeRemaining >= 0) {
        computeBatteryTimeRemaining /= 1000;
      }
    }
    return computeBatteryTimeRemaining;
  }

  public long computeChargeTimeRemaining() {
    long computeChargeTimeRemaining;
    if (this.mStats.canReadTimeToFullNow()) {
      long computeChargeTimeRemainingFromSysfs =
          this.mStats.computeChargeTimeRemainingFromSysfs(SystemClock.elapsedRealtime());
      return computeChargeTimeRemainingFromSysfs >= 0
          ? computeChargeTimeRemainingFromSysfs / 1000
          : computeChargeTimeRemainingFromSysfs;
    }
    synchronized (this.mStats) {
      computeChargeTimeRemaining =
          this.mStats.computeChargeTimeRemaining(SystemClock.elapsedRealtime());
      if (computeChargeTimeRemaining >= 0) {
        computeChargeTimeRemaining /= 1000;
      }
    }
    return computeChargeTimeRemaining;
  }

  public long computeBatteryScreenOffRealtimeMs() {
    long computeBatteryScreenOffRealtime;
    super.computeBatteryScreenOffRealtimeMs_enforcePermission();
    synchronized (this.mStats) {
      computeBatteryScreenOffRealtime =
          this.mStats.computeBatteryScreenOffRealtime(SystemClock.elapsedRealtimeNanos() / 1000, 0)
              / 1000;
    }
    return computeBatteryScreenOffRealtime;
  }

  public long getScreenOffDischargeMah() {
    long uahDischargeScreenOff;
    super.getScreenOffDischargeMah_enforcePermission();
    synchronized (this.mStats) {
      uahDischargeScreenOff = this.mStats.getUahDischargeScreenOff(0) / 1000;
    }
    return uahDischargeScreenOff;
  }

  public void noteEvent(final int i, final String str, final int i2) {
    super.noteEvent_enforcePermission();
    if (str == null) {
      Slog.wtfStack("BatteryStatsService", "noteEvent called with null name. code = " + i);
      return;
    }
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda99
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteEvent$14(
                  i, str, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteEvent$14(int i, String str, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteEventLocked(i, str, i2, j, j2);
    }
  }

  public void noteSyncStart(final String str, final int i) {
    super.noteSyncStart_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteSyncStart$15(
                  str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(7, i, (String) null, str, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteSyncStart$15(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteSyncStartLocked(str, i, j, j2);
    }
  }

  public void noteSyncFinish(final String str, final int i) {
    super.noteSyncFinish_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteSyncFinish$16(
                  str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(7, i, (String) null, str, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteSyncFinish$16(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteSyncFinishLocked(str, i, j, j2);
    }
  }

  public void noteJobStart(final String str, final int i) {
    super.noteJobStart_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteJobStart$17(
                  str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteJobStart$17(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteJobStartLocked(str, i, j, j2);
    }
  }

  public void noteJobFinish(final String str, final int i, final int i2) {
    super.noteJobFinish_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteJobFinish$18(
                  str, i, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteJobFinish$18(String str, int i, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteJobFinishLocked(str, i, i2, j, j2);
    }
  }

  public void noteJobsDeferred(final int i, final int i2, final long j) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda113
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteJobsDeferred$19(
                  i, i2, j, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteJobsDeferred$19(int i, int i2, long j, long j2, long j3) {
    synchronized (this.mStats) {
      this.mStats.noteJobsDeferredLocked(i, i2, j, j2, j3);
    }
  }

  public void noteWakupAlarm(
      final String str, final int i, WorkSource workSource, final String str2) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.UPDATE_DEVICE_STATS", "noteWakupAlarm");
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda62
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWakupAlarm$20(
                  str, i, workSource2, str2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWakupAlarm$20(
      String str, int i, WorkSource workSource, String str2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWakupAlarmLocked(str, i, workSource, str2, j, j2);
    }
  }

  public void noteAlarmStart(final String str, WorkSource workSource, final int i) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.UPDATE_DEVICE_STATS", "noteAlarmStart");
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda66
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteAlarmStart$21(
                  str, workSource2, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteAlarmStart$21(
      String str, WorkSource workSource, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteAlarmStartLocked(str, workSource, i, j, j2);
    }
  }

  public void noteAlarmFinish(final String str, WorkSource workSource, final int i) {
    this.mContext.enforceCallingOrSelfPermission(
        "android.permission.UPDATE_DEVICE_STATS", "noteAlarmFinish");
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda64
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteAlarmFinish$22(
                  str, workSource2, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteAlarmFinish$22(
      String str, WorkSource workSource, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteAlarmFinishLocked(str, workSource, i, j, j2);
    }
  }

  public void noteStartWakelock(
      final int i,
      final int i2,
      final String str,
      final String str2,
      final int i3,
      final boolean z) {
    super.noteStartWakelock_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStartWakelock$23(
                  i, i2, str, str2, i3, z, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStartWakelock$23(
      int i, int i2, String str, String str2, int i3, boolean z, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteStartWakeLocked(i, i2, null, str, str2, i3, z, j, j2);
    }
  }

  public void noteStopWakelock(
      final int i, final int i2, final String str, final String str2, final int i3) {
    super.noteStopWakelock_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStopWakelock$24(
                  i, i2, str, str2, i3, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStopWakelock$24(
      int i, int i2, String str, String str2, int i3, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteStopWakeLocked(i, i2, null, str, str2, i3, j, j2);
    }
  }

  public void noteStartWakelockFromSource(
      WorkSource workSource,
      final int i,
      final String str,
      final String str2,
      final int i2,
      final boolean z) {
    super.noteStartWakelockFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStartWakelockFromSource$25(
                  workSource2, i, str, str2, i2, z, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStartWakelockFromSource$25(
      WorkSource workSource, int i, String str, String str2, int i2, boolean z, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteStartWakeFromSourceLocked(workSource, i, str, str2, i2, z, j, j2);
    }
  }

  public void noteChangeWakelockFromSource(
      WorkSource workSource,
      final int i,
      final String str,
      final String str2,
      final int i2,
      WorkSource workSource2,
      final int i3,
      final String str3,
      final String str4,
      final int i4,
      final boolean z) {
    super.noteChangeWakelockFromSource_enforcePermission();
    WorkSource workSource3 = workSource != null ? new WorkSource(workSource) : null;
    final WorkSource workSource4 = workSource2 != null ? new WorkSource(workSource2) : null;
    synchronized (this.mLock) {
      try {
        try {
          final long elapsedRealtime = SystemClock.elapsedRealtime();
          final long uptimeMillis = SystemClock.uptimeMillis();
          final WorkSource workSource5 = workSource3;
          this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda60
                @Override // java.lang.Runnable
                public final void run() {
                  BatteryStatsService.this.lambda$noteChangeWakelockFromSource$26(
                      workSource5,
                      i,
                      str,
                      str2,
                      i2,
                      workSource4,
                      i3,
                      str3,
                      str4,
                      i4,
                      z,
                      elapsedRealtime,
                      uptimeMillis);
                }
              });
        } catch (Throwable th) {
          th = th;
          throw th;
        }
      } catch (Throwable th2) {
        th = th2;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteChangeWakelockFromSource$26(
      WorkSource workSource,
      int i,
      String str,
      String str2,
      int i2,
      WorkSource workSource2,
      int i3,
      String str3,
      String str4,
      int i4,
      boolean z,
      long j,
      long j2) {
    synchronized (this.mStats) {
      this.mStats.noteChangeWakelockFromSourceLocked(
          workSource, i, str, str2, i2, workSource2, i3, str3, str4, i4, z, j, j2);
    }
  }

  public void noteStopWakelockFromSource(
      WorkSource workSource, final int i, final String str, final String str2, final int i2) {
    super.noteStopWakelockFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda81
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStopWakelockFromSource$27(
                  workSource2, i, str, str2, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStopWakelockFromSource$27(
      WorkSource workSource, int i, String str, String str2, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteStopWakeFromSourceLocked(workSource, i, str, str2, i2, j, j2);
    }
  }

  public void noteLongPartialWakelockStart(final String str, final String str2, final int i) {
    super.noteLongPartialWakelockStart_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteLongPartialWakelockStart$28(
                  str, str2, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteLongPartialWakelockStart$28(
      String str, String str2, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteLongPartialWakelockStart(str, str2, i, j, j2);
    }
  }

  public void noteLongPartialWakelockStartFromSource(
      final String str, final String str2, WorkSource workSource) {
    super.noteLongPartialWakelockStartFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteLongPartialWakelockStartFromSource$29(
                  str, str2, workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteLongPartialWakelockStartFromSource$29(
      String str, String str2, WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteLongPartialWakelockStartFromSource(str, str2, workSource, j, j2);
    }
  }

  public void noteLongPartialWakelockFinish(final String str, final String str2, final int i) {
    super.noteLongPartialWakelockFinish_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteLongPartialWakelockFinish$30(
                  str, str2, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteLongPartialWakelockFinish$30(
      String str, String str2, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteLongPartialWakelockFinish(str, str2, i, j, j2);
    }
  }

  public void noteLongPartialWakelockFinishFromSource(
      final String str, final String str2, WorkSource workSource) {
    super.noteLongPartialWakelockFinishFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteLongPartialWakelockFinishFromSource$31(
                  str, str2, workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteLongPartialWakelockFinishFromSource$31(
      String str, String str2, WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteLongPartialWakelockFinishFromSource(str, str2, workSource, j, j2);
    }
  }

  public void noteStartSensor(final int i, final int i2) {
    super.noteStartSensor_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda51
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStartSensor$32(
                  i, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(5, i, (String) null, i2, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStartSensor$32(int i, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteStartSensorLocked(i, i2, j, j2);
    }
  }

  public void noteWakeupSensorEvent(long j, int i, int i2) {
    int callingUid = Binder.getCallingUid();
    if (callingUid != 1000) {
      throw new SecurityException("Calling uid " + callingUid + " is not system uid");
    }
    long millis = TimeUnit.NANOSECONDS.toMillis(j);
    Sensor sensorByHandle =
        ((SensorManager) this.mContext.getSystemService(SensorManager.class)).getSensorByHandle(i2);
    if (sensorByHandle == null) {
      Slog.w(
          "BatteryStatsService",
          "Unknown sensor handle " + i2 + " received in noteWakeupSensorEvent");
      return;
    }
    if (i < 0) {
      Slog.wtf(
          "BatteryStatsService",
          "Invalid uid " + i + " for sensor event with sensor: " + sensorByHandle);
      return;
    }
    noteCpuWakingActivity(4, millis, i);
  }

  public void noteStopSensor(final int i, final int i2) {
    super.noteStopSensor_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStopSensor$33(
                  i, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(5, i, (String) null, i2, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStopSensor$33(int i, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteStopSensorLocked(i, i2, j, j2);
    }
  }

  public void noteVibratorOn(final int i, final long j) {
    super.noteVibratorOn_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteVibratorOn$34(
                  i, j, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteVibratorOn$34(int i, long j, long j2, long j3) {
    synchronized (this.mStats) {
      this.mStats.noteVibratorOnLocked(i, j, j2, j3);
    }
  }

  public void noteVibratorOff(final int i) {
    super.noteVibratorOff_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda79
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteVibratorOff$35(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteVibratorOff$35(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteVibratorOffLocked(i, j, j2);
    }
  }

  public void noteGpsChanged(WorkSource workSource, WorkSource workSource2) {
    super.noteGpsChanged_enforcePermission();
    final WorkSource workSource3 = workSource != null ? new WorkSource(workSource) : null;
    final WorkSource workSource4 = workSource2 != null ? new WorkSource(workSource2) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda57
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteGpsChanged$36(
                  workSource3, workSource4, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteGpsChanged$36(
      WorkSource workSource, WorkSource workSource2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteGpsChangedLocked(workSource, workSource2, j, j2);
    }
  }

  public void noteGpsSignalQuality(final int i) {
    super.noteGpsSignalQuality_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda86
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteGpsSignalQuality$37(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteGpsSignalQuality$37(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteGpsSignalQualityLocked(i, j, j2);
    }
  }

  public void noteScreenState(final int i) {
    super.noteScreenState_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      final long currentTimeMillis = System.currentTimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda100
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteScreenState$38(
                  i, elapsedRealtime, uptimeMillis, currentTimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(29, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteScreenState$38(int i, long j, long j2, long j3) {
    synchronized (this.mStats) {
      this.mStats.noteScreenStateLocked(0, i, j, j2, j3);
    }
  }

  public void noteScreenBrightness(final int i) {
    super.noteScreenBrightness_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteScreenBrightness$39(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(9, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteScreenBrightness$39(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteScreenBrightnessLocked(0, i, j, j2);
    }
  }

  public void noteUserActivity(final int i, final int i2) {
    super.noteUserActivity_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteUserActivity$40(
                  i, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteUserActivity$40(int i, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteUserActivityLocked(i, i2, j, j2);
    }
  }

  public void noteWakeUp(final String str, final int i) {
    super.noteWakeUp_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWakeUp$41(str, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWakeUp$41(String str, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWakeUpLocked(str, i, j, j2);
    }
  }

  public void noteInteractive(final boolean z) {
    super.noteInteractive_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteInteractive$42(z, elapsedRealtime);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteInteractive$42(boolean z, long j) {
    synchronized (this.mStats) {
      this.mStats.noteInteractiveLocked(z, j);
    }
  }

  public void noteConnectivityChanged(final int i, final String str) {
    super.noteConnectivityChanged_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteConnectivityChanged$43(
                  i, str, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteConnectivityChanged$43(
      int i, String str, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteConnectivityChangedLocked(i, str, j, j2);
    }
  }

  public void noteMobileRadioPowerState(final int i, final long j, final int i2) {
    super.noteMobileRadioPowerState_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteMobileRadioPowerState$44(
                  i, j, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(12, i2, null, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteMobileRadioPowerState$44(
      int i, long j, int i2, long j2, long j3) {
    synchronized (this.mStats) {
      if (this.mLastPowerStateFromRadio == i) {
        return;
      }
      this.mLastPowerStateFromRadio = i;
      boolean noteMobileRadioPowerStateLocked =
          this.mStats.noteMobileRadioPowerStateLocked(i, j, i2, j2, j3);
      if (noteMobileRadioPowerStateLocked) {
        this.mWorker.scheduleSync("modem-data", 4);
      }
    }
  }

  public void notePhoneOn() {
    super.notePhoneOn_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$notePhoneOn$45(elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notePhoneOn$45(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.notePhoneOnLocked(j, j2);
    }
  }

  public void notePhoneOff() {
    super.notePhoneOff_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda63
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$notePhoneOff$46(elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notePhoneOff$46(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.notePhoneOffLocked(j, j2);
    }
  }

  public void notePhoneSignalStrength(final SignalStrength signalStrength) {
    super.notePhoneSignalStrength_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda42
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$notePhoneSignalStrength$47(
                  signalStrength, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notePhoneSignalStrength$47(
      SignalStrength signalStrength, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.notePhoneSignalStrengthLocked(signalStrength, j, j2);
    }
  }

  public void notePhoneDataConnectionState(
      final int i, final boolean z, final int i2, final int i3) {
    super.notePhoneDataConnectionState_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$notePhoneDataConnectionState$48(
                  i, z, i2, i3, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notePhoneDataConnectionState$48(
      int i, boolean z, int i2, int i3, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.notePhoneDataConnectionStateLocked(i, z, i2, i3, j, j2);
    }
  }

  public void notePhoneState(final int i) {
    super.notePhoneState_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda72
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$notePhoneState$49(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notePhoneState$49(int i, long j, long j2) {
    int simState =
        ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).getSimState();
    synchronized (this.mStats) {
      this.mStats.notePhoneStateLocked(i, simState, j, j2);
    }
  }

  public void noteWifiOn() {
    super.noteWifiOn_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiOn$50(elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(113, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiOn$50(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiOnLocked(j, j2);
    }
  }

  public void noteWifiOff() {
    super.noteWifiOff_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda92
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiOff$51(elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(113, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiOff$51(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiOffLocked(j, j2);
    }
  }

  public void noteStartAudio(final int i) {
    super.noteStartAudio_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStartAudio$52(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(23, i, null, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStartAudio$52(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteAudioOnLocked(i, j, j2);
    }
  }

  public void noteStopAudio(final int i) {
    super.noteStopAudio_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda76
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStopAudio$53(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(23, i, null, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStopAudio$53(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteAudioOffLocked(i, j, j2);
    }
  }

  public void noteStartVideo(final int i) {
    super.noteStartVideo_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda77
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStartVideo$54(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(24, i, null, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStartVideo$54(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteVideoOnLocked(i, j, j2);
    }
  }

  public void noteStopVideo(final int i) {
    super.noteStopVideo_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStopVideo$55(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(24, i, null, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStopVideo$55(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteVideoOffLocked(i, j, j2);
    }
  }

  public void noteResetAudio() {
    super.noteResetAudio_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteResetAudio$56(elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(23, -1, null, 2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteResetAudio$56(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteResetAudioLocked(j, j2);
    }
  }

  public void noteResetVideo() {
    super.noteResetVideo_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda94
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteResetVideo$57(elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(24, -1, null, 2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteResetVideo$57(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteResetVideoLocked(j, j2);
    }
  }

  public void noteFlashlightOn(final int i) {
    super.noteFlashlightOn_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda84
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteFlashlightOn$58(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(26, i, null, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteFlashlightOn$58(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteFlashlightOnLocked(i, j, j2);
    }
  }

  public void noteFlashlightOff(final int i) {
    super.noteFlashlightOff_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda68
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteFlashlightOff$59(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(26, i, null, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteFlashlightOff$59(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteFlashlightOffLocked(i, j, j2);
    }
  }

  public void noteStartCamera(final int i) {
    super.noteStartCamera_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda89
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStartCamera$60(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING
        && i != 1000
        && i != 1001
        && i != 5021) {
      sendWirelessPowerSharingExternelEvent(1, true);
    }
    FrameworkStatsLog.write_non_chained(25, i, null, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStartCamera$60(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteCameraOnLocked(i, j, j2);
    }
  }

  public void noteStopCamera(final int i) {
    super.noteStopCamera_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda87
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStopCamera$61(i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    if (PowerManagerUtil.SEC_FEATURE_USE_WIRELESS_POWER_SHARING
        && i != 1000
        && i != 1001
        && i != 5021) {
      sendWirelessPowerSharingExternelEvent(1, false);
    }
    FrameworkStatsLog.write_non_chained(25, i, null, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStopCamera$61(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteCameraOffLocked(i, j, j2);
    }
  }

  public void noteResetCamera() {
    super.noteResetCamera_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda97
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteResetCamera$62(elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(25, -1, null, 2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteResetCamera$62(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteResetCameraLocked(j, j2);
    }
  }

  public void noteResetFlashlight() {
    super.noteResetFlashlight_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda78
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteResetFlashlight$63(elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(26, -1, null, 2);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteResetFlashlight$63(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteResetFlashlightLocked(j, j2);
    }
  }

  public void noteStartGps(int i) {
    super.noteStartGps_enforcePermission();
    synchronized (this.mStats) {
      this.mStats.noteGpsOnLocked(i);
      FrameworkStatsLog.write(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED, i, 1);
    }
  }

  public void noteStopGps(int i) {
    super.noteStopGps_enforcePermission();
    synchronized (this.mStats) {
      this.mStats.noteGpsOffLocked(i);
      FrameworkStatsLog.write(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED, i, 0);
    }
  }

  public void noteResetGps() {
    super.noteResetGps_enforcePermission();
    synchronized (this.mStats) {
      this.mStats.noteResetGpsLocked();
      FrameworkStatsLog.write(FrameworkStatsLog.GPS_ENGINE_STATE_CHANGED, -1, 2);
    }
  }

  public void noteWifiRadioPowerState(final int i, final long j, final int i2) {
    super.noteWifiRadioPowerState_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda69
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiRadioPowerState$64(
                  i, j, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write_non_chained(13, i2, null, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiRadioPowerState$64(
      int i, long j, int i2, long j2, long j3) {
    String str;
    synchronized (this.mStats) {
      if (this.mLastPowerStateFromWifi == i) {
        return;
      }
      this.mLastPowerStateFromWifi = i;
      if (this.mStats.isOnBattery()) {
        if (i != 3 && i != 2) {
          str = "inactive";
          this.mWorker.scheduleSync("wifi-data: " + str, 2);
        }
        str = "active";
        this.mWorker.scheduleSync("wifi-data: " + str, 2);
      }
      this.mStats.noteWifiRadioPowerState(i, j, i2, j2, j3);
    }
  }

  public void noteWifiRunning(WorkSource workSource) {
    super.noteWifiRunning_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda43
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiRunning$65(
                  workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(114, workSource, 1);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiRunning$65(WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiRunningLocked(workSource, j, j2);
    }
  }

  public void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) {
    super.noteWifiRunningChanged_enforcePermission();
    final WorkSource workSource3 = workSource != null ? new WorkSource(workSource) : null;
    final WorkSource workSource4 = workSource2 != null ? new WorkSource(workSource2) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiRunningChanged$66(
                  workSource3, workSource4, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(114, workSource2, 1);
    FrameworkStatsLog.write(114, workSource, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiRunningChanged$66(
      WorkSource workSource, WorkSource workSource2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiRunningChangedLocked(workSource, workSource2, j, j2);
    }
  }

  public void noteWifiStopped(WorkSource workSource) {
    super.noteWifiStopped_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : workSource;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda102
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiStopped$67(
                  workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(114, workSource, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiStopped$67(WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiStoppedLocked(workSource, j, j2);
    }
  }

  public void noteWifiState(final int i, final String str) {
    super.noteWifiState_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda95
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiState$68(i, str, elapsedRealtime);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiState$68(int i, String str, long j) {
    synchronized (this.mStats) {
      this.mStats.noteWifiStateLocked(i, str, j);
    }
  }

  public void noteWifiSupplicantStateChanged(final int i, final boolean z) {
    super.noteWifiSupplicantStateChanged_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiSupplicantStateChanged$69(
                  i, z, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiSupplicantStateChanged$69(
      int i, boolean z, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiSupplicantStateChangedLocked(i, z, j, j2);
    }
  }

  public void noteWifiRssiChanged(final int i) {
    super.noteWifiRssiChanged_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda61
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiRssiChanged$70(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiRssiChanged$70(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiRssiChangedLocked(i, j, j2);
    }
  }

  public void noteFullWifiLockAcquired(final int i) {
    super.noteFullWifiLockAcquired_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteFullWifiLockAcquired$71(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteFullWifiLockAcquired$71(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteFullWifiLockAcquiredLocked(i, j, j2);
    }
  }

  public void noteFullWifiLockReleased(final int i) {
    super.noteFullWifiLockReleased_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda80
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteFullWifiLockReleased$72(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteFullWifiLockReleased$72(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteFullWifiLockReleasedLocked(i, j, j2);
    }
  }

  public void noteWifiScanStarted(final int i) {
    super.noteWifiScanStarted_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda88
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiScanStarted$73(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiScanStarted$73(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiScanStartedLocked(i, j, j2);
    }
  }

  public void noteWifiScanStopped(final int i) {
    super.noteWifiScanStopped_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda54
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiScanStopped$74(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiScanStopped$74(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiScanStoppedLocked(i, j, j2);
    }
  }

  public void noteWifiMulticastEnabled(final int i) {
    super.noteWifiMulticastEnabled_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiMulticastEnabled$75(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiMulticastEnabled$75(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiMulticastEnabledLocked(i, j, j2);
    }
  }

  public void noteWifiMulticastDisabled(final int i) {
    super.noteWifiMulticastDisabled_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiMulticastDisabled$76(
                  i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiMulticastDisabled$76(int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiMulticastDisabledLocked(i, j, j2);
    }
  }

  public void noteFullWifiLockAcquiredFromSource(WorkSource workSource) {
    super.noteFullWifiLockAcquiredFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteFullWifiLockAcquiredFromSource$77(
                  workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteFullWifiLockAcquiredFromSource$77(
      WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteFullWifiLockAcquiredFromSourceLocked(workSource, j, j2);
    }
  }

  public void noteFullWifiLockReleasedFromSource(WorkSource workSource) {
    super.noteFullWifiLockReleasedFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda98
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteFullWifiLockReleasedFromSource$78(
                  workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteFullWifiLockReleasedFromSource$78(
      WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteFullWifiLockReleasedFromSourceLocked(workSource, j, j2);
    }
  }

  public void noteWifiScanStartedFromSource(WorkSource workSource) {
    super.noteWifiScanStartedFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda83
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiScanStartedFromSource$79(
                  workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiScanStartedFromSource$79(
      WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiScanStartedFromSourceLocked(workSource, j, j2);
    }
  }

  public void noteWifiScanStoppedFromSource(WorkSource workSource) {
    super.noteWifiScanStoppedFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiScanStoppedFromSource$80(
                  workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiScanStoppedFromSource$80(
      WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiScanStoppedFromSourceLocked(workSource, j, j2);
    }
  }

  public void noteWifiBatchedScanStartedFromSource(WorkSource workSource, final int i) {
    super.noteWifiBatchedScanStartedFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiBatchedScanStartedFromSource$81(
                  workSource2, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiBatchedScanStartedFromSource$81(
      WorkSource workSource, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiBatchedScanStartedFromSourceLocked(workSource, i, j, j2);
    }
  }

  public void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) {
    super.noteWifiBatchedScanStoppedFromSource_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda91
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiBatchedScanStoppedFromSource$82(
                  workSource2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiBatchedScanStoppedFromSource$82(
      WorkSource workSource, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteWifiBatchedScanStoppedFromSourceLocked(workSource, j, j2);
    }
  }

  public void noteNetworkInterfaceForTransports(final String str, final int[] iArr) {
    super.noteNetworkInterfaceForTransports_enforcePermission();
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda93
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteNetworkInterfaceForTransports$83(str, iArr);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteNetworkInterfaceForTransports$83(String str, int[] iArr) {
    this.mStats.noteNetworkInterfaceForTransports(str, iArr);
  }

  public void noteNetworkStatsEnabled() {
    super.noteNetworkStatsEnabled_enforcePermission();
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda90
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteNetworkStatsEnabled$84();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteNetworkStatsEnabled$84() {
    this.mWorker.scheduleSync("network-stats-enabled", 6);
  }

  public void noteDeviceIdleMode(final int i, final String str, final int i2) {
    super.noteDeviceIdleMode_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteDeviceIdleMode$85(
                  i, str, i2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteDeviceIdleMode$85(
      int i, String str, int i2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteDeviceIdleModeLocked(i, str, i2, j, j2);
    }
  }

  public void notePackageInstalled(final String str, final long j) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$notePackageInstalled$86(
                  str, j, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notePackageInstalled$86(String str, long j, long j2, long j3) {
    synchronized (this.mStats) {
      this.mStats.notePackageInstalledLocked(str, j, j2, j3);
    }
  }

  public void notePackageUninstalled(final String str) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$notePackageUninstalled$87(
                  str, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$notePackageUninstalled$87(String str, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.notePackageUninstalledLocked(str, j, j2);
    }
  }

  public void noteBleScanStarted(WorkSource workSource, final boolean z) {
    super.noteBleScanStarted_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteBleScanStarted$88(
                  workSource2, z, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteBleScanStarted$88(
      WorkSource workSource, boolean z, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteBluetoothScanStartedFromSourceLocked(workSource, z, j, j2);
    }
  }

  public void noteBleScanStopped(WorkSource workSource, final boolean z) {
    super.noteBleScanStopped_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda65
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteBleScanStopped$89(
                  workSource2, z, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteBleScanStopped$89(
      WorkSource workSource, boolean z, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteBluetoothScanStoppedFromSourceLocked(workSource, z, j, j2);
    }
  }

  public void noteBleScanReset() {
    super.noteBleScanReset_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda74
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteBleScanReset$90(elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteBleScanReset$90(long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteResetBluetoothScanLocked(j, j2);
    }
  }

  public void noteBleScanResults(WorkSource workSource, final int i) {
    super.noteBleScanResults_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda71
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteBleScanResults$91(
                  workSource2, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteBleScanResults$91(
      WorkSource workSource, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteBluetoothScanResultsFromSourceLocked(workSource, i, j, j2);
    }
  }

  public void noteWifiControllerActivity(final WifiActivityEnergyInfo wifiActivityEnergyInfo) {
    super.noteWifiControllerActivity_enforcePermission();
    if (wifiActivityEnergyInfo == null || !wifiActivityEnergyInfo.isValid()) {
      Slog.e("BatteryStatsService", "invalid wifi data given: " + wifiActivityEnergyInfo);
      return;
    }
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      final NetworkStatsManager networkStatsManager =
          (NetworkStatsManager) this.mContext.getSystemService(NetworkStatsManager.class);
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteWifiControllerActivity$92(
                  wifiActivityEnergyInfo, elapsedRealtime, uptimeMillis, networkStatsManager);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteWifiControllerActivity$92(
      WifiActivityEnergyInfo wifiActivityEnergyInfo,
      long j,
      long j2,
      NetworkStatsManager networkStatsManager) {
    this.mStats.updateWifiState(wifiActivityEnergyInfo, -1L, j, j2, networkStatsManager);
  }

  public void noteBluetoothControllerActivity(
      final BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
    super.noteBluetoothControllerActivity_enforcePermission();
    if (bluetoothActivityEnergyInfo == null || !bluetoothActivityEnergyInfo.isValid()) {
      Slog.e("BatteryStatsService", "invalid bluetooth data given: " + bluetoothActivityEnergyInfo);
      return;
    }
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda67
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteBluetoothControllerActivity$93(
                  bluetoothActivityEnergyInfo, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteBluetoothControllerActivity$93(
      BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.updateBluetoothStateLocked(bluetoothActivityEnergyInfo, -1L, j, j2);
    }
  }

  public void noteModemControllerActivity(final ModemActivityInfo modemActivityInfo) {
    super.noteModemControllerActivity_enforcePermission();
    if (modemActivityInfo == null) {
      Slog.e("BatteryStatsService", "invalid modem data given: " + modemActivityInfo);
      return;
    }
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      final NetworkStatsManager networkStatsManager =
          (NetworkStatsManager) this.mContext.getSystemService(NetworkStatsManager.class);
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda75
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteModemControllerActivity$94(
                  modemActivityInfo, elapsedRealtime, uptimeMillis, networkStatsManager);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteModemControllerActivity$94(
      ModemActivityInfo modemActivityInfo,
      long j,
      long j2,
      NetworkStatsManager networkStatsManager) {
    this.mStats.noteModemControllerActivity(modemActivityInfo, -1L, j, j2, networkStatsManager);
  }

  public boolean isOnBattery() {
    return this.mStats.isOnBattery();
  }

  public void setBatteryState(
      final int i,
      final int i2,
      final int i3,
      final int i4,
      final int i5,
      final int i6,
      final int i7,
      final int i8,
      final long j,
      final int i9,
      final int i10,
      final int i11,
      final int i12,
      final boolean z) {
    super.setBatteryState_enforcePermission();
    synchronized (this.mLock) {
      try {
        try {
          this.mHandler.post(
              new Runnable() { // from class:
                               // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  BatteryStatsService.this.lambda$setBatteryState$97(
                      i3, i, i2, i4, i5, i6, i7, i8, j, i9, i10, i11, i12, z);
                }
              });
        } catch (Throwable th) {
          th = th;
          throw th;
        }
      } catch (Throwable th2) {
        th = th2;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setBatteryState$97(
      final int i,
      final int i2,
      final int i3,
      final int i4,
      final int i5,
      final int i6,
      final int i7,
      final int i8,
      final long j,
      final int i9,
      final int i10,
      final int i11,
      final int i12,
      final boolean z) {
    this.mWorker.scheduleRunnable(
        new Runnable() { // from class:
                         // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda110
          @Override // java.lang.Runnable
          public final void run() {
            BatteryStatsService.this.lambda$setBatteryState$96(
                i, i2, i3, i4, i5, i6, i7, i8, j, i9, i10, i11, i12, z);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setBatteryState$96(
      final int i,
      final int i2,
      final int i3,
      final int i4,
      final int i5,
      final int i6,
      final int i7,
      final int i8,
      final long j,
      final int i9,
      final int i10,
      final int i11,
      final int i12,
      final boolean z) {
    synchronized (this.mStats) {
      if (this.mStats.isOnBattery() == BatteryStatsImpl.isOnBattery(i, i2)) {
        this.mStats.setBatteryStateLocked(
            i2,
            i3,
            i,
            i4,
            i5,
            i6,
            i7,
            i8,
            j,
            i9,
            i10,
            i11,
            i12,
            z ? 1 : 0,
            SystemClock.elapsedRealtime(),
            SystemClock.uptimeMillis(),
            System.currentTimeMillis());
      } else {
        this.mWorker.scheduleSync("battery-state", 127);
        this.mWorker.scheduleRunnable(
            new Runnable() { // from class:
                             // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda114
              @Override // java.lang.Runnable
              public final void run() {
                BatteryStatsService.this.lambda$setBatteryState$95(
                    i2, i3, i, i4, i5, i6, i7, i8, j, i9, i10, i11, i12, z);
              }
            });
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setBatteryState$95(
      int i,
      int i2,
      int i3,
      int i4,
      int i5,
      int i6,
      int i7,
      int i8,
      long j,
      int i9,
      int i10,
      int i11,
      int i12,
      boolean z) {
    synchronized (this.mStats) {
      this.mStats.setBatteryStateLocked(
          i,
          i2,
          i3,
          i4,
          i5,
          i6,
          i7,
          i8,
          j,
          i9,
          i10,
          i11,
          i12,
          z ? 1 : 0,
          SystemClock.elapsedRealtime(),
          SystemClock.uptimeMillis(),
          System.currentTimeMillis());
    }
  }

  public long getAwakeTimeBattery() {
    super.getAwakeTimeBattery_enforcePermission();
    return this.mStats.getAwakeTimeBattery();
  }

  public long getAwakeTimePlugged() {
    super.getAwakeTimePlugged_enforcePermission();
    return this.mStats.getAwakeTimePlugged();
  }

  public final class WakeupReasonThread extends Thread {
    public CharsetDecoder mDecoder;
    public CharBuffer mUtf16Buffer;
    public ByteBuffer mUtf8Buffer;

    public WakeupReasonThread() {
      super("BatteryStats_wakeupReason");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      Process.setThreadPriority(-2);
      this.mDecoder =
          StandardCharsets.UTF_8
              .newDecoder()
              .onMalformedInput(CodingErrorAction.REPLACE)
              .onUnmappableCharacter(CodingErrorAction.REPLACE)
              .replaceWith("?");
      this.mUtf8Buffer = ByteBuffer.allocateDirect(512);
      this.mUtf16Buffer = CharBuffer.allocate(512);
      while (true) {
        try {
          String waitWakeup = waitWakeup();
          if (waitWakeup == null) {
            return;
          }
          long elapsedRealtime = SystemClock.elapsedRealtime();
          long uptimeMillis = SystemClock.uptimeMillis();
          Trace.instantForTrack(131072L, "wakeup_reason", elapsedRealtime + " " + waitWakeup);
          BatteryStatsService.this.awaitCompletion();
          BatteryStatsService.this.mCpuWakeupStats.noteWakeupTimeAndReason(
              elapsedRealtime, uptimeMillis, waitWakeup);
          synchronized (BatteryStatsService.this.mStats) {
            BatteryStatsService.this.mStats.noteWakeupReasonLocked(
                waitWakeup, elapsedRealtime, uptimeMillis);
          }
        } catch (RuntimeException e) {
          Slog.e("BatteryStatsService", "Failure reading wakeup reasons", e);
          return;
        }
      }
    }

    public final String waitWakeup() {
      this.mUtf8Buffer.clear();
      this.mUtf16Buffer.clear();
      this.mDecoder.reset();
      int nativeWaitWakeup = BatteryStatsService.nativeWaitWakeup(this.mUtf8Buffer);
      if (nativeWaitWakeup < 0) {
        return null;
      }
      if (nativeWaitWakeup == 0) {
        return "unknown";
      }
      this.mUtf8Buffer.limit(nativeWaitWakeup);
      this.mDecoder.decode(this.mUtf8Buffer, this.mUtf16Buffer, true);
      this.mUtf16Buffer.flip();
      return this.mUtf16Buffer.toString();
    }
  }

  public final void sendWirelessPowerSharingExternelEvent(int i, boolean z) {
    Slog.d("BatteryStatsService", "set setWirelessPowerSharingExternelEvent");
    this.mBatteryManagerInternal.setWirelessPowerSharingExternelEvent(i, z ? i : 0);
  }

  public final void dumpHelp(PrintWriter printWriter) {
    printWriter.println("Battery stats (batterystats) dump options:");
    printWriter.println("  [--checkin] [--proto] [--history] [--history-start] [--charged] [-c]");
    printWriter.println(
        "  [--daily] [--reset] [--reset-all] [--write] [--new-daily] [--read-daily]");
    printWriter.println("  [-h] [<package.name>]");
    printWriter.println(
        "  --checkin: generate output for a checkin report; will write (and clear) the");
    printWriter.println("             last old completed stats when they had been reset.");
    printWriter.println("  -c: write the current stats in checkin format.");
    printWriter.println(
        "  --proto: write the current aggregate stats (without history) in proto format.");
    printWriter.println("  --history: show only history data.");
    printWriter.println(
        "  --history-start <num>: show only history data starting at given time offset.");
    printWriter.println("  --history-create-events <num>: create <num> of battery history events.");
    printWriter.println("  --charged: only output data since last charged.");
    printWriter.println("  --daily: only output full daily data.");
    printWriter.println("  --reset: reset the stats, clearing all current data.");
    printWriter.println("  --reset-all: reset the stats, clearing all current and past data.");
    printWriter.println("  --write: force write current collected stats to disk.");
    printWriter.println("  --new-daily: immediately create and write new daily stats record.");
    printWriter.println("  --read-daily: read-load last written daily stats.");
    printWriter.println("  --settings: dump the settings key/values related to batterystats");
    printWriter.println("  --cpu: dump cpu stats for debugging purpose");
    printWriter.println("  --wakeups: dump CPU wakeup history and attribution.");
    printWriter.println("  --power-profile: dump the power profile constants");
    printWriter.println("  --usage: write battery usage stats. Optional arguments:");
    printWriter.println("     --proto: output as a binary protobuffer");
    printWriter.println(
        "     --model power-profile: use the power profile model even if measured energy is"
            + " available");
    printWriter.println("  <package.name>: optional name of package to filter output by.");
    printWriter.println("  -h: print this help text.");
    printWriter.println("Battery stats (batterystats) commands:");
    printWriter.println("  enable|disable <option>");
    printWriter.println(
        "    Enable or disable a running option.  Option state is not saved across boots.");
    printWriter.println("    Options are:");
    printWriter.println(
        "      full-history: include additional detailed events in battery history:");
    printWriter.println("          wake_lock_in, alarms and proc events");
    printWriter.println("      no-auto-reset: don't automatically reset stats when unplugged");
    printWriter.println(
        "      pretend-screen-off: pretend the screen is off, even if screen state changes");
  }

  public final void dumpSettings(PrintWriter printWriter) {
    awaitCompletion();
    synchronized (this.mStats) {
      this.mStats.dumpConstantsLocked(printWriter);
    }
  }

  public final void dumpCpuStats(PrintWriter printWriter) {
    awaitCompletion();
    synchronized (this.mStats) {
      this.mStats.dumpCpuStatsLocked(printWriter);
    }
  }

  public final void dumpMeasuredEnergyStats(PrintWriter printWriter) {
    awaitCompletion();
    syncStats("dump", 127);
    synchronized (this.mStats) {
      this.mStats.dumpEnergyConsumerStatsLocked(printWriter);
    }
  }

  public final void dumpPowerProfile(PrintWriter printWriter) {
    synchronized (this.mStats) {
      this.mStats.dumpPowerProfileLocked(printWriter);
    }
  }

  public final void dumpUsageStatsToProto(
      FileDescriptor fileDescriptor, PrintWriter printWriter, int i, boolean z) {
    awaitCompletion();
    syncStats("dump", 127);
    BatteryUsageStatsQuery.Builder includePowerModels =
        new BatteryUsageStatsQuery.Builder()
            .setMaxStatsAgeMs(0L)
            .includeProcessStateData()
            .includePowerModels();
    if (i == 1) {
      includePowerModels.powerProfileModeledOnly();
    }
    BatteryUsageStatsQuery build = includePowerModels.build();
    synchronized (this.mStats) {
      this.mStats.prepareForDumpLocked();
      BatteryUsageStats batteryUsageStats =
          this.mBatteryUsageStatsProvider.getBatteryUsageStats(build);
      if (z) {
        batteryUsageStats.dumpToProto(fileDescriptor);
      } else {
        batteryUsageStats.dump(printWriter, "");
      }
    }
  }

  public final int doEnableOrDisable(PrintWriter printWriter, int i, String[] strArr, boolean z) {
    int i2 = i + 1;
    if (i2 >= strArr.length) {
      StringBuilder sb = new StringBuilder();
      sb.append("Missing option argument for ");
      sb.append(z ? "--enable" : "--disable");
      printWriter.println(sb.toString());
      dumpHelp(printWriter);
      return -1;
    }
    if ("full-wake-history".equals(strArr[i2]) || "full-history".equals(strArr[i2])) {
      awaitCompletion();
      synchronized (this.mStats) {
        this.mStats.setRecordAllHistoryLocked(z);
      }
    } else if ("no-auto-reset".equals(strArr[i2])) {
      awaitCompletion();
      synchronized (this.mStats) {
        this.mStats.setNoAutoReset(z);
      }
    } else if ("pretend-screen-off".equals(strArr[i2])) {
      awaitCompletion();
      synchronized (this.mStats) {
        this.mStats.setPretendScreenOff(z);
      }
    } else {
      printWriter.println("Unknown enable/disable option: " + strArr[i2]);
      dumpHelp(printWriter);
      return -1;
    }
    return i2;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    this.mMonitorEnabled = false;
    try {
      dumpUnmonitored(fileDescriptor, printWriter, strArr);
    } finally {
      this.mMonitorEnabled = true;
    }
  }

  public final void dumpUnmonitored(
      FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    long j;
    boolean z;
    int i;
    boolean z2;
    boolean z3;
    boolean z4;
    boolean z5;
    long j2;
    if (DumpUtils.checkDumpAndUsageStatsPermission(
            this.mContext, "BatteryStatsService", printWriter)
        && this.mSystemServicesReady) {
      long j3 = -1;
      int i2 = -1;
      if (strArr != null) {
        int i3 = 0;
        z = false;
        i = 0;
        z2 = false;
        z3 = false;
        z4 = false;
        z5 = false;
        while (i3 < strArr.length) {
          String str = strArr[i3];
          if ("--checkin".equals(str)) {
            z2 = true;
            z4 = true;
          } else if ("--history".equals(str)) {
            i |= 8;
          } else {
            j2 = j3;
            if ("--history-start".equals(str)) {
              i |= 8;
              i3++;
              if (i3 >= strArr.length) {
                printWriter.println("Missing time argument for --history-since");
                dumpHelp(printWriter);
                return;
              } else {
                z5 = true;
                j2 = ParseUtils.parseLong(strArr[i3], 0L);
              }
            } else {
              if ("--history-create-events".equals(str)) {
                i3++;
                if (i3 >= strArr.length) {
                  printWriter.println("Missing events argument for --history-create-events");
                  dumpHelp(printWriter);
                  return;
                }
                long parseLong = ParseUtils.parseLong(strArr[i3], 0L);
                awaitCompletion();
                synchronized (this.mStats) {
                  this.mStats.createFakeHistoryEvents(parseLong);
                  printWriter.println("Battery history create events started.");
                }
              } else if ("-c".equals(str)) {
                i |= 16;
                z2 = true;
              } else if ("--proto".equals(str)) {
                z3 = true;
              } else if ("--charged".equals(str)) {
                i |= 2;
              } else if ("--daily".equals(str)) {
                i |= 4;
              } else if ("--reset-all".equals(str)) {
                awaitCompletion();
                synchronized (this.mStats) {
                  this.mStats.resetAllStatsAndHistoryLocked(2);
                  this.mBatteryUsageStatsStore.removeAllSnapshots();
                  printWriter.println("Battery stats and history reset.");
                }
              } else if ("--reset".equals(str)) {
                awaitCompletion();
                synchronized (this.mStats) {
                  this.mStats.resetAllStatsAndHistoryLocked(2);
                  printWriter.println("Battery stats reset.");
                }
              } else if ("--write".equals(str)) {
                awaitCompletion();
                syncStats("dump", 127);
                synchronized (this.mStats) {
                  this.mStats.writeSyncLocked();
                  printWriter.println("Battery stats written.");
                }
              } else if ("--new-daily".equals(str)) {
                awaitCompletion();
                synchronized (this.mStats) {
                  this.mStats.recordDailyStatsLocked();
                  printWriter.println("New daily stats written.");
                }
              } else if ("--read-daily".equals(str)) {
                awaitCompletion();
                synchronized (this.mStats) {
                  this.mStats.readDailyStatsLocked();
                  printWriter.println("Last daily stats read.");
                }
              } else {
                if ("--enable".equals(str) || "enable".equals(str)) {
                  int doEnableOrDisable = doEnableOrDisable(printWriter, i3, strArr, true);
                  if (doEnableOrDisable < 0) {
                    return;
                  }
                  printWriter.println("Enabled: " + strArr[doEnableOrDisable]);
                  return;
                }
                if ("--disable".equals(str) || "disable".equals(str)) {
                  int doEnableOrDisable2 = doEnableOrDisable(printWriter, i3, strArr, false);
                  if (doEnableOrDisable2 < 0) {
                    return;
                  }
                  printWriter.println("Disabled: " + strArr[doEnableOrDisable2]);
                  return;
                }
                if ("-h".equals(str)) {
                  dumpHelp(printWriter);
                  return;
                }
                if ("--settings".equals(str)) {
                  dumpSettings(printWriter);
                  return;
                }
                if ("--cpu".equals(str)) {
                  dumpCpuStats(printWriter);
                  return;
                }
                if ("--measured-energy".equals(str)) {
                  dumpMeasuredEnergyStats(printWriter);
                  return;
                }
                if ("--power-profile".equals(str)) {
                  dumpPowerProfile(printWriter);
                  return;
                }
                if ("--usage".equals(str)) {
                  int i4 = i3 + 1;
                  int i5 = 0;
                  boolean z6 = false;
                  while (i4 < strArr.length) {
                    String str2 = strArr[i4];
                    str2.hashCode();
                    if (str2.equals("--model")) {
                      i4++;
                      if (i4 < strArr.length) {
                        if (!"power-profile".equals(strArr[i4])) {
                          printWriter.println("Unknown power model: " + strArr[i4]);
                          dumpHelp(printWriter);
                          return;
                        }
                        i5 = 1;
                      } else {
                        printWriter.println("--model without a value");
                        dumpHelp(printWriter);
                        return;
                      }
                    } else if (str2.equals("--proto")) {
                      z6 = true;
                    }
                    i4++;
                  }
                  dumpUsageStatsToProto(fileDescriptor, printWriter, i5, z6);
                  return;
                }
                if ("--wakeups".equals(str)) {
                  this.mCpuWakeupStats.dump(
                      new IndentingPrintWriter(printWriter, "  "), SystemClock.elapsedRealtime());
                  return;
                }
                if ("-a".equals(str)) {
                  i |= 32;
                } else {
                  if (str.length() > 0 && str.charAt(0) == '-') {
                    printWriter.println("Unknown option: " + str);
                    dumpHelp(printWriter);
                    return;
                  }
                  try {
                    i2 =
                        this.mContext
                            .getPackageManager()
                            .getPackageUidAsUser(str, UserHandle.getCallingUserId());
                  } catch (PackageManager.NameNotFoundException unused) {
                    printWriter.println("Unknown package: " + str);
                    dumpHelp(printWriter);
                    return;
                  }
                }
              }
              z = true;
            }
            i3++;
            j3 = j2;
          }
          j2 = j3;
          i3++;
          j3 = j2;
        }
        j = j3;
      } else {
        j = -1;
        z = false;
        i = 0;
        z2 = false;
        z3 = false;
        z4 = false;
        z5 = false;
      }
      if (z) {
        return;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (BatteryStats.checkWifiOnly(this.mContext)) {
          i |= 64;
        }
        awaitCompletion();
        syncStats("dump", 127);
        if (i2 >= 0 && (i & 10) == 0) {
          i = (i | 2) & (-17);
        }
        if (z3) {
          List<ApplicationInfo> installedApplications =
              this.mContext.getPackageManager().getInstalledApplications(4325376);
          if (z4) {
            synchronized (this.mStats.mCheckinFile) {
              if (this.mStats.mCheckinFile.exists()) {
                try {
                  byte[] readFully = this.mStats.mCheckinFile.readFully();
                  if (readFully != null) {
                    Parcel obtain = Parcel.obtain();
                    obtain.unmarshall(readFully, 0, readFully.length);
                    obtain.setDataPosition(0);
                    BatteryStatsImpl batteryStatsImpl =
                        new BatteryStatsImpl(
                            null,
                            this.mStats.mHandler,
                            null,
                            null,
                            this.mUserManagerUserInfoProvider);
                    batteryStatsImpl.setPowerProfileLocked(this.mPowerProfile);
                    batteryStatsImpl.readSummaryFromParcel(obtain);
                    obtain.recycle();
                    batteryStatsImpl.dumpProtoLocked(
                        this.mContext, fileDescriptor, installedApplications, i, j);
                    this.mStats.mCheckinFile.delete();
                    return;
                  }
                } catch (ParcelFormatException | IOException e) {
                  Slog.w(
                      "BatteryStatsService",
                      "Failure reading checkin file " + this.mStats.mCheckinFile.getBaseFile(),
                      e);
                }
              }
            }
          }
          awaitCompletion();
          synchronized (this.mStats) {
            this.mStats.dumpProtoLocked(this.mContext, fileDescriptor, installedApplications, i, j);
            if (z5) {
              this.mStats.writeAsyncLocked();
            }
          }
          return;
        }
        if (z2) {
          List<ApplicationInfo> installedApplications2 =
              this.mContext.getPackageManager().getInstalledApplications(4325376);
          if (z4) {
            synchronized (this.mStats.mCheckinFile) {
              if (this.mStats.mCheckinFile.exists()) {
                try {
                  byte[] readFully2 = this.mStats.mCheckinFile.readFully();
                  if (readFully2 != null) {
                    Parcel obtain2 = Parcel.obtain();
                    obtain2.unmarshall(readFully2, 0, readFully2.length);
                    obtain2.setDataPosition(0);
                    BatteryStatsImpl batteryStatsImpl2 =
                        new BatteryStatsImpl(
                            null,
                            this.mStats.mHandler,
                            null,
                            null,
                            this.mUserManagerUserInfoProvider);
                    batteryStatsImpl2.setPowerProfileLocked(this.mPowerProfile);
                    batteryStatsImpl2.readSummaryFromParcel(obtain2);
                    obtain2.recycle();
                    batteryStatsImpl2.dumpCheckin(
                        this.mContext, printWriter, installedApplications2, i, j);
                    this.mStats.mCheckinFile.delete();
                    return;
                  }
                } catch (ParcelFormatException | IOException e2) {
                  Slog.w(
                      "BatteryStatsService",
                      "Failure reading checkin file " + this.mStats.mCheckinFile.getBaseFile(),
                      e2);
                }
              }
            }
          }
          awaitCompletion();
          this.mStats.dumpCheckin(this.mContext, printWriter, installedApplications2, i, j);
          if (z5) {
            this.mStats.writeAsyncLocked();
            return;
          }
          return;
        }
        awaitCompletion();
        this.mStats.dump(this.mContext, printWriter, i, i2, j);
        if (z5) {
          this.mStats.writeAsyncLocked();
        }
        printWriter.println();
        this.mCpuWakeupStats.dump(
            new IndentingPrintWriter(printWriter, "  "), SystemClock.elapsedRealtime());
        this.mDeviceBatteryInfoServiceInternal.dump(printWriter);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public CellularBatteryStats getCellularBatteryStats() {
    CellularBatteryStats cellularBatteryStats;
    super.getCellularBatteryStats_enforcePermission();
    awaitCompletion();
    synchronized (this.mStats) {
      cellularBatteryStats = this.mStats.getCellularBatteryStats();
    }
    return cellularBatteryStats;
  }

  public WifiBatteryStats getWifiBatteryStats() {
    WifiBatteryStats wifiBatteryStats;
    super.getWifiBatteryStats_enforcePermission();
    awaitCompletion();
    synchronized (this.mStats) {
      wifiBatteryStats = this.mStats.getWifiBatteryStats();
    }
    return wifiBatteryStats;
  }

  public GpsBatteryStats getGpsBatteryStats() {
    GpsBatteryStats gpsBatteryStats;
    super.getGpsBatteryStats_enforcePermission();
    awaitCompletion();
    synchronized (this.mStats) {
      gpsBatteryStats = this.mStats.getGpsBatteryStats();
    }
    return gpsBatteryStats;
  }

  public WakeLockStats getWakeLockStats() {
    WakeLockStats wakeLockStats;
    super.getWakeLockStats_enforcePermission();
    awaitCompletion();
    synchronized (this.mStats) {
      wakeLockStats = this.mStats.getWakeLockStats();
    }
    return wakeLockStats;
  }

  public BluetoothBatteryStats getBluetoothBatteryStats() {
    BluetoothBatteryStats bluetoothBatteryStats;
    super.getBluetoothBatteryStats_enforcePermission();
    awaitCompletion();
    synchronized (this.mStats) {
      bluetoothBatteryStats = this.mStats.getBluetoothBatteryStats();
    }
    return bluetoothBatteryStats;
  }

  public HealthStatsParceler takeUidSnapshot(int i) {
    HealthStatsParceler healthStatsForUidLocked;
    if (i != Binder.getCallingUid()) {
      this.mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        awaitCompletion();
        if (shouldCollectExternalStats()) {
          syncStats("get-health-stats-for-uids", 127);
        }
        synchronized (this.mStats) {
          healthStatsForUidLocked = getHealthStatsForUidLocked(i);
        }
        return healthStatsForUidLocked;
      } catch (Exception e) {
        Slog.w("BatteryStatsService", "Crashed while writing for takeUidSnapshot(" + i + ")", e);
        throw e;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public HealthStatsParceler[] takeUidSnapshots(int[] iArr) {
    HealthStatsParceler[] healthStatsParcelerArr;
    if (!onlyCaller(iArr)) {
      this.mContext.enforceCallingOrSelfPermission("android.permission.BATTERY_STATS", null);
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        awaitCompletion();
        if (shouldCollectExternalStats()) {
          syncStats("get-health-stats-for-uids", 127);
        }
        synchronized (this.mStats) {
          int length = iArr.length;
          healthStatsParcelerArr = new HealthStatsParceler[length];
          for (int i = 0; i < length; i++) {
            healthStatsParcelerArr[i] = getHealthStatsForUidLocked(iArr[i]);
          }
        }
        return healthStatsParcelerArr;
      } catch (Exception e) {
        throw e;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean shouldCollectExternalStats() {
    return SystemClock.elapsedRealtime() - this.mWorker.getLastCollectionTimeStamp()
        > this.mStats.getExternalStatsCollectionRateLimitMs();
  }

  public static boolean onlyCaller(int[] iArr) {
    int callingUid = Binder.getCallingUid();
    for (int i : iArr) {
      if (i != callingUid) {
        return false;
      }
    }
    return true;
  }

  public HealthStatsParceler getHealthStatsForUidLocked(int i) {
    HealthStatsBatteryStatsWriter healthStatsBatteryStatsWriter =
        new HealthStatsBatteryStatsWriter();
    HealthStatsWriter healthStatsWriter = new HealthStatsWriter(UidHealthStats.CONSTANTS);
    BatteryStats.Uid uid = (BatteryStats.Uid) this.mStats.getUidStats().get(i);
    if (uid != null) {
      healthStatsBatteryStatsWriter.writeUid(healthStatsWriter, this.mStats, uid);
    }
    return new HealthStatsParceler(healthStatsWriter);
  }

  public boolean setChargingStateUpdateDelayMillis(int i) {
    super.setChargingStateUpdateDelayMillis_enforcePermission();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return Settings.Global.putLong(
          this.mContext.getContentResolver(), "battery_charging_state_update_delay", i);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void updateForegroundTimeIfOnBattery(final String str, final int i, final long j) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$updateForegroundTimeIfOnBattery$98(
                  i, str, elapsedRealtime, uptimeMillis, j);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateForegroundTimeIfOnBattery$98(
      int i, String str, long j, long j2, long j3) {
    if (isOnBattery()) {
      synchronized (this.mStats) {
        BatteryStatsImpl.Uid.Proc processStatsLocked =
            this.mStats.getProcessStatsLocked(i, str, j, j2);
        if (processStatsLocked != null) {
          processStatsLocked.addForegroundTimeLocked(j3);
        }
      }
    }
  }

  public void noteCurrentTimeChanged() {
    synchronized (this.mLock) {
      final long currentTimeMillis = System.currentTimeMillis();
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteCurrentTimeChanged$99(
                  currentTimeMillis, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteCurrentTimeChanged$99(long j, long j2, long j3) {
    synchronized (this.mStats) {
      this.mStats.noteCurrentTimeChangedLocked(j, j2, j3);
    }
  }

  public void updateBatteryStatsOnActivityUsage(
      String str, String str2, final int i, int i2, final boolean z) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$updateBatteryStatsOnActivityUsage$100(
                  z, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(42, i, str, str2, z ? 1 : 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateBatteryStatsOnActivityUsage$100(
      boolean z, int i, long j, long j2) {
    synchronized (this.mStats) {
      if (z) {
        this.mStats.noteActivityResumedLocked(i, j, j2);
      } else {
        this.mStats.noteActivityPausedLocked(i, j, j2);
      }
    }
  }

  public void noteProcessDied(final int i, final int i2) {
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteProcessDied$101(i, i2);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteProcessDied$101(int i, int i2) {
    synchronized (this.mStats) {
      this.mStats.noteProcessDiedLocked(i, i2);
    }
  }

  public void reportExcessiveCpu(final int i, final String str, final long j, final long j2) {
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda116
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$reportExcessiveCpu$102(i, str, j, j2);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$reportExcessiveCpu$102(int i, String str, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.reportExcessiveCpuLocked(i, str, j, j2);
    }
  }

  public void noteServiceStartRunning(final int i, final String str, final String str2) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda104
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteServiceStartRunning$103(
                  i, str, str2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteServiceStartRunning$103(
      int i, String str, String str2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.getServiceStatsLocked(i, str, str2, j, j2).startRunningLocked(j2);
    }
  }

  public void noteServiceStopRunning(final int i, final String str, final String str2) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda109
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteServiceStopRunning$104(
                  i, str, str2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteServiceStopRunning$104(
      int i, String str, String str2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.getServiceStatsLocked(i, str, str2, j, j2).stopRunningLocked(j2);
    }
  }

  public void noteServiceStartLaunch(final int i, final String str, final String str2) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda112
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteServiceStartLaunch$105(
                  i, str, str2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteServiceStartLaunch$105(
      int i, String str, String str2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.getServiceStatsLocked(i, str, str2, j, j2).startLaunchedLocked(j2);
    }
  }

  public void noteServiceStopLaunch(final int i, final String str, final String str2) {
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda111
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteServiceStopLaunch$106(
                  i, str, str2, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteServiceStopLaunch$106(
      int i, String str, String str2, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.getServiceStatsLocked(i, str, str2, j, j2).stopLaunchedLocked(j2);
    }
  }

  public void setChargerAcOnline(boolean z, boolean z2) {
    super.setChargerAcOnline_enforcePermission();
    this.mBatteryManagerInternal.setChargerAcOnline(z, z2);
  }

  public void setBatteryLevel(int i, boolean z) {
    super.setBatteryLevel_enforcePermission();
    this.mBatteryManagerInternal.setBatteryLevel(i, z);
  }

  public void unplugBattery(boolean z) {
    super.unplugBattery_enforcePermission();
    this.mBatteryManagerInternal.unplugBattery(z);
  }

  public void resetBattery(boolean z) {
    super.resetBattery_enforcePermission();
    this.mBatteryManagerInternal.resetBattery(z);
  }

  public void suspendBatteryInput() {
    super.suspendBatteryInput_enforcePermission();
    this.mBatteryManagerInternal.suspendBatteryInput();
  }

  public void setTemperatureNCurrent(
      final int i, final int i2, final int i3, final int i4, final int i5) {
    super.setTemperatureNCurrent_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda56
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$setTemperatureNCurrent$107(
                  i, i2, i3, i4, i5, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setTemperatureNCurrent$107(
      int i, int i2, int i3, int i4, int i5, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.setTemperatureNCurrentLocked(i, i2, i3, i4, i5, j, j2);
    }
  }

  public void updateForegroundAppEnergyInfo(List list) {
    super.updateForegroundAppEnergyInfo_enforcePermission();
    if (list == null) {
      Slog.e("BatteryStatsService", "Invalid display pmic data");
      return;
    }
    synchronized (this.mStats) {
      this.mStats.updateForegroundAppEnergyInfoLocked(list);
    }
  }

  public void updateSpeakerOutEnergyInfo(final SpeakerOutEnergyInfo speakerOutEnergyInfo) {
    super.updateSpeakerOutEnergyInfo_enforcePermission();
    if (speakerOutEnergyInfo == null) {
      Slog.e("BatteryStatsService", "invalid SpeakerOutEnergyInfo given: " + speakerOutEnergyInfo);
      return;
    }
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$updateSpeakerOutEnergyInfo$108(
                  speakerOutEnergyInfo, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateSpeakerOutEnergyInfo$108(
      SpeakerOutEnergyInfo speakerOutEnergyInfo, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.updateSpeakerOutEnergyInfoLocked(speakerOutEnergyInfo, j, j2);
    }
  }

  public boolean registerBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) {
    boolean registerBatteryStatsCallbackLocked;
    super.registerBatteryStatsCallback_enforcePermission();
    synchronized (this.mStats) {
      registerBatteryStatsCallbackLocked =
          this.mStats.registerBatteryStatsCallbackLocked(iBatteryStatsCallback);
    }
    return registerBatteryStatsCallbackLocked;
  }

  public boolean unregisterBatteryStatsCallback(IBatteryStatsCallback iBatteryStatsCallback) {
    boolean unregisterBatteryStatsCallbackLocked;
    super.unregisterBatteryStatsCallback_enforcePermission();
    synchronized (this.mStats) {
      unregisterBatteryStatsCallbackLocked =
          this.mStats.unregisterBatteryStatsCallbackLocked(iBatteryStatsCallback);
    }
    return unregisterBatteryStatsCallbackLocked;
  }

  public void noteUpdateNetworkStats(final String str) {
    super.noteUpdateNetworkStats_enforcePermission();
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteUpdateNetworkStats$109(str);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteUpdateNetworkStats$109(String str) {
    this.mWorker.scheduleSync("update-network-" + str, 6);
  }

  public void noteBleDutyScanStarted(WorkSource workSource, final boolean z, final int i) {
    super.noteBleDutyScanStarted_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteBleDutyScanStarted$110(
                  workSource2, z, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteBleDutyScanStarted$110(
      WorkSource workSource, boolean z, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteBluetoothDutyScanStartedFromSourceLocked(workSource, z, i, j, j2);
    }
  }

  public void noteBleDutyScanStopped(WorkSource workSource, final boolean z, final int i) {
    super.noteBleDutyScanStopped_enforcePermission();
    final WorkSource workSource2 = workSource != null ? new WorkSource(workSource) : null;
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteBleDutyScanStopped$111(
                  workSource2, z, i, elapsedRealtime, uptimeMillis);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteBleDutyScanStopped$111(
      WorkSource workSource, boolean z, int i, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteBluetoothDutyScanStoppedFromSourceLocked(workSource, z, i, j, j2);
    }
  }

  public void noteStartTxPowerSharing() {
    super.noteStartTxPowerSharing_enforcePermission();
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStartTxPowerSharing$112();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStartTxPowerSharing$112() {
    synchronized (this.mStats) {
      this.mStats.noteStartTxPowerSharingLocked();
    }
  }

  public void noteStopTxPowerSharing() {
    super.noteStopTxPowerSharing_enforcePermission();
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda82
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteStopTxPowerSharing$113();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteStopTxPowerSharing$113() {
    synchronized (this.mStats) {
      this.mStats.noteStopTxPowerSharingLocked();
    }
  }

  public void noteDualScreenState(final int i, final int i2, final int i3) {
    super.noteDualScreenState_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      final long currentTimeMillis = System.currentTimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteDualScreenState$114(
                  i, i2, i3, elapsedRealtime, uptimeMillis, currentTimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(29, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteDualScreenState$114(
      int i, int i2, int i3, long j, long j2, long j3) {
    synchronized (this.mStats) {
      this.mStats.noteScreenStateLocked(0, i, i2, i3, j, j2, j3);
    }
  }

  public void noteDualScreenBrightness(final int i, final int i2, final int i3) {
    super.noteDualScreenBrightness_enforcePermission();
    synchronized (this.mLock) {
      final long elapsedRealtime = SystemClock.elapsedRealtime();
      final long uptimeMillis = SystemClock.uptimeMillis();
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda101
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$noteDualScreenBrightness$115(
                  i, i2, i3, elapsedRealtime, uptimeMillis);
            }
          });
    }
    FrameworkStatsLog.write(9, i);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$noteDualScreenBrightness$115(
      int i, int i2, int i3, long j, long j2) {
    synchronized (this.mStats) {
      this.mStats.noteScreenBrightnessLocked(0, i, i2, i3, j, j2);
    }
  }

  public void updateSemModemActivityInfo(final SemModemActivityInfo semModemActivityInfo) {
    super.updateSemModemActivityInfo_enforcePermission();
    if (semModemActivityInfo == null) {
      Slog.e("BatteryStatsService", "invalid SemModemActivityInfo given: " + semModemActivityInfo);
      return;
    }
    synchronized (this.mLock) {
      this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.server.am.BatteryStatsService$$ExternalSyntheticLambda52
            @Override // java.lang.Runnable
            public final void run() {
              BatteryStatsService.this.lambda$updateSemModemActivityInfo$116(semModemActivityInfo);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateSemModemActivityInfo$116(
      SemModemActivityInfo semModemActivityInfo) {
    synchronized (this.mStats) {
      this.mStats.updateSemModemActivityInfoLocked(semModemActivityInfo);
    }
  }

  public boolean isCpuClusterAvailable() {
    boolean isCpuClusterAvailableLocked;
    synchronized (this.mStats) {
      isCpuClusterAvailableLocked = this.mStats.isCpuClusterAvailableLocked();
    }
    return isCpuClusterAvailableLocked;
  }

  public boolean isCpuFrequencyAvailable() {
    boolean isCpuFrequencyAvailableLocked;
    synchronized (this.mStats) {
      isCpuFrequencyAvailableLocked = this.mStats.isCpuFrequencyAvailableLocked();
    }
    return isCpuFrequencyAvailableLocked;
  }

  public SemCompanionDeviceBatteryInfo[] getDeviceBatteryInfos() {
    Slog.i("BatteryStatsService", "semGetBatteryInfos()");
    return this.mDeviceBatteryInfoServiceInternal.getDeviceBatteryInfos();
  }

  public SemCompanionDeviceBatteryInfo getDeviceBatteryInfo(String str) {
    Slog.i("BatteryStatsService", "getDeviceBatteryInfo address: " + str);
    return this.mDeviceBatteryInfoServiceInternal.getDeviceBatteryInfo(str);
  }

  public void registerDeviceBatteryInfoChanged(String str) {
    Slog.i("BatteryStatsService", "registerDeviceBatteryInfoChanged package: " + str);
    this.mDeviceBatteryInfoServiceInternal.registerDeviceBatteryInfoChanged(str);
  }

  public void unRegisterDeviceBatteryInfoChanged(String str) {
    Slog.i("BatteryStatsService", "unRegisterDeviceBatteryInfoChanged package: " + str);
    this.mDeviceBatteryInfoServiceInternal.unRegisterDeviceBatteryInfoChanged(str);
  }

  public void setDeviceBatteryInfo(
      String str, SemCompanionDeviceBatteryInfo semCompanionDeviceBatteryInfo) {
    Slog.i("BatteryStatsService", "setDeviceBatteryInfo()");
    this.mDeviceBatteryInfoServiceInternal.setDeviceBatteryInfo(str, semCompanionDeviceBatteryInfo);
  }

  public void unsetDeviceBatteryInfo(String str) {
    Slog.i("BatteryStatsService", "removeDeviceBatteryInfo()");
    this.mDeviceBatteryInfoServiceInternal.unsetDeviceBatteryInfo(str);
  }
}
