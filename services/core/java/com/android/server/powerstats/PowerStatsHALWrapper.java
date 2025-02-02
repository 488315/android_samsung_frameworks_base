package com.android.server.powerstats;

import android.hardware.power.stats.Channel;
import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerResult;
import android.hardware.power.stats.EnergyMeasurement;
import android.hardware.power.stats.IPowerStats;
import android.hardware.power.stats.PowerEntity;
import android.hardware.power.stats.StateResidencyResult;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public abstract class PowerStatsHALWrapper {
  public static final String TAG = "PowerStatsHALWrapper";

  public interface IPowerStatsHALWrapper {
    EnergyConsumerResult[] getEnergyConsumed(int[] iArr);

    EnergyConsumer[] getEnergyConsumerInfo();

    Channel[] getEnergyMeterInfo();

    PowerEntity[] getPowerEntityInfo();

    StateResidencyResult[] getStateResidency(int[] iArr);

    boolean isInitialized();

    EnergyMeasurement[] readEnergyMeter(int[] iArr);
  }

  public final class PowerStatsHAL20WrapperImpl implements IPowerStatsHALWrapper {
    public static Supplier sVintfPowerStats;

    public PowerStatsHAL20WrapperImpl() {
      VintfHalCache vintfHalCache = new VintfHalCache();
      sVintfPowerStats = null;
      if (vintfHalCache.get() == null) {
        sVintfPowerStats = null;
      } else {
        sVintfPowerStats = vintfHalCache;
      }
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public PowerEntity[] getPowerEntityInfo() {
      Supplier supplier = sVintfPowerStats;
      if (supplier != null) {
        try {
          return ((IPowerStats) supplier.get()).getPowerEntityInfo();
        } catch (RemoteException e) {
          Slog.w(PowerStatsHALWrapper.TAG, "Failed to get power entity info: ", e);
        }
      }
      return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public StateResidencyResult[] getStateResidency(int[] iArr) {
      Supplier supplier = sVintfPowerStats;
      if (supplier != null) {
        try {
          return ((IPowerStats) supplier.get()).getStateResidency(iArr);
        } catch (RemoteException e) {
          Slog.w(PowerStatsHALWrapper.TAG, "Failed to get state residency: ", e);
        }
      }
      return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public EnergyConsumer[] getEnergyConsumerInfo() {
      Supplier supplier = sVintfPowerStats;
      if (supplier != null) {
        try {
          return ((IPowerStats) supplier.get()).getEnergyConsumerInfo();
        } catch (RemoteException e) {
          Slog.w(PowerStatsHALWrapper.TAG, "Failed to get energy consumer info: ", e);
        }
      }
      return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public EnergyConsumerResult[] getEnergyConsumed(int[] iArr) {
      Supplier supplier = sVintfPowerStats;
      if (supplier != null) {
        try {
          return ((IPowerStats) supplier.get()).getEnergyConsumed(iArr);
        } catch (RemoteException e) {
          Slog.w(PowerStatsHALWrapper.TAG, "Failed to get energy consumer results: ", e);
        }
      }
      return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public Channel[] getEnergyMeterInfo() {
      Supplier supplier = sVintfPowerStats;
      if (supplier != null) {
        try {
          return ((IPowerStats) supplier.get()).getEnergyMeterInfo();
        } catch (RemoteException e) {
          Slog.w(PowerStatsHALWrapper.TAG, "Failed to get energy meter info: ", e);
        }
      }
      return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public EnergyMeasurement[] readEnergyMeter(int[] iArr) {
      Supplier supplier = sVintfPowerStats;
      if (supplier != null) {
        try {
          return ((IPowerStats) supplier.get()).readEnergyMeter(iArr);
        } catch (RemoteException e) {
          Slog.w(PowerStatsHALWrapper.TAG, "Failed to get energy measurements: ", e);
        }
      }
      return null;
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public boolean isInitialized() {
      return sVintfPowerStats != null;
    }
  }

  public final class PowerStatsHAL10WrapperImpl implements IPowerStatsHALWrapper {
    public boolean mIsInitialized;

    private static native Channel[] nativeGetEnergyMeterInfo();

    private static native PowerEntity[] nativeGetPowerEntityInfo();

    private static native StateResidencyResult[] nativeGetStateResidency(int[] iArr);

    private static native boolean nativeInit();

    private static native EnergyMeasurement[] nativeReadEnergyMeters(int[] iArr);

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public EnergyConsumerResult[] getEnergyConsumed(int[] iArr) {
      return new EnergyConsumerResult[0];
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public EnergyConsumer[] getEnergyConsumerInfo() {
      return new EnergyConsumer[0];
    }

    public PowerStatsHAL10WrapperImpl() {
      if (nativeInit()) {
        this.mIsInitialized = true;
      } else {
        this.mIsInitialized = false;
      }
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public PowerEntity[] getPowerEntityInfo() {
      return nativeGetPowerEntityInfo();
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public StateResidencyResult[] getStateResidency(int[] iArr) {
      return nativeGetStateResidency(iArr);
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public Channel[] getEnergyMeterInfo() {
      return nativeGetEnergyMeterInfo();
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public EnergyMeasurement[] readEnergyMeter(int[] iArr) {
      return nativeReadEnergyMeters(iArr);
    }

    @Override // com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper
    public boolean isInitialized() {
      return this.mIsInitialized;
    }
  }

  public static IPowerStatsHALWrapper getPowerStatsHalImpl() {
    PowerStatsHAL20WrapperImpl powerStatsHAL20WrapperImpl = new PowerStatsHAL20WrapperImpl();
    return powerStatsHAL20WrapperImpl.isInitialized()
        ? powerStatsHAL20WrapperImpl
        : new PowerStatsHAL10WrapperImpl();
  }

  public class VintfHalCache implements Supplier, IBinder.DeathRecipient {
    public IPowerStats mInstance;

    public VintfHalCache() {
      this.mInstance = null;
    }

    @Override // java.util.function.Supplier
    public synchronized IPowerStats get() {
      IBinder allowBlocking;
      if (this.mInstance == null
          && (allowBlocking =
                  Binder.allowBlocking(
                      ServiceManager.waitForDeclaredService(
                          "android.hardware.power.stats.IPowerStats/default")))
              != null) {
        this.mInstance = IPowerStats.Stub.asInterface(allowBlocking);
        try {
          allowBlocking.linkToDeath(this, 0);
        } catch (RemoteException unused) {
          Slog.e(
              PowerStatsHALWrapper.TAG, "Unable to register DeathRecipient for " + this.mInstance);
        }
      }
      return this.mInstance;
    }

    @Override // android.os.IBinder.DeathRecipient
    public synchronized void binderDied() {
      Slog.w(PowerStatsHALWrapper.TAG, "PowerStats HAL died");
      this.mInstance = null;
    }
  }
}
