package com.samsung.android.os;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.SparseArray;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SemTemperatureManager {
  private static String LOG_TAG = SemTemperatureManager.class.getSimpleName();
  private static final int SDHMS_VALUE_SIOP_LEVEL = 1;
  public static final int UNSUPPORTED = -999;
  private static ISamsungDeviceHealthManager mService;
  private static SparseArray<Thermistor> mThermistorList;

  public static class Thermistor {
    private static final int NUM_OF_TYPE = 12;
    public static final int TYPE_5G_MODEM = 6;
    public static final int TYPE_AP = 0;
    public static final int TYPE_BATTERY = 1;
    public static final int TYPE_CAMERA_FLASH = 7;
    public static final int TYPE_CHARGER = 2;
    public static final int TYPE_PAM = 5;
    public static final int TYPE_PREDICTED_BACK_SURFACE = 11;
    public static final int TYPE_PREDICTED_FRONT_SURFACE = 10;
    public static final int TYPE_PREDICTED_SURFACE = 9;
    public static final int TYPE_TABLET_COOL_AREA = 8;
    public static final int TYPE_USB = 3;
    public static final int TYPE_WIFI = 4;
    private int mType;

    private Thermistor(int type) {
      this.mType = type;
    }

    public int getType() {
      return this.mType;
    }

    public int getTemperature() {
      ISamsungDeviceHealthManager svc = SemTemperatureManager.getService();
      if (svc != null) {
        try {
          return svc.getTemperature(this.mType);
        } catch (Exception e) {
          e.printStackTrace();
          return -999;
        }
      }
      return -999;
    }
  }

  private SemTemperatureManager() {}

  /* JADX INFO: Access modifiers changed from: private */
  public static synchronized ISamsungDeviceHealthManager getService() {
    ISamsungDeviceHealthManager iSamsungDeviceHealthManager;
    IBinder b;
    synchronized (SemTemperatureManager.class) {
      if (mService == null && (b = ServiceManager.getService("sdhms")) != null) {
        ISamsungDeviceHealthManager asInterface = ISamsungDeviceHealthManager.Stub.asInterface(b);
        mService = asInterface;
        if (asInterface != null) {
          try {
            b.linkToDeath(
                new IBinder
                    .DeathRecipient() { // from class:
                                        // com.samsung.android.os.SemTemperatureManager.1
                  @Override // android.os.IBinder.DeathRecipient
                  public void binderDied() {
                    SemTemperatureManager.mService = null;
                  }
                },
                0);
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        }
      }
      iSamsungDeviceHealthManager = mService;
    }
    return iSamsungDeviceHealthManager;
  }

  private static synchronized void initThermistorList() {
    synchronized (SemTemperatureManager.class) {
      mThermistorList = new SparseArray<>();
      int[] results = getAllTemperatures();
      for (int i = 0; i < 12; i++) {
        Thermistor t = new Thermistor(i);
        if (results[i] != -999) {
          mThermistorList.append(i, t);
        }
      }
    }
  }

  private static int[] getAllTemperatures() {
    int[] results = new int[12];
    ISamsungDeviceHealthManager svc = getService();
    if (svc != null) {
      try {
        return svc.getAllTemperatures(12);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    for (int i = 0; i < 12; i++) {
      results[i] = -999;
    }
    return results;
  }

  public static int getOverheatingProtectionLevel(Context context) {
    ISamsungDeviceHealthManager svc = getService();
    if (svc != null) {
      try {
        return svc.getSsrmStatus(1);
      } catch (Exception e) {
        e.printStackTrace();
        return -999;
      }
    }
    return -999;
  }

  public static List<Thermistor> getThermistorList() {
    SparseArray<Thermistor> sparseArray = mThermistorList;
    if (sparseArray == null || sparseArray.size() == 0) {
      initThermistorList();
    }
    ArrayList<Thermistor> list = new ArrayList<>(mThermistorList.size());
    for (int i = 0; i < mThermistorList.size(); i++) {
      if (mThermistorList.keyAt(i) != 8) {
        list.add(mThermistorList.valueAt(i));
      }
    }
    return list;
  }

  public static Thermistor getThermistor(int thermistorType) {
    SparseArray<Thermistor> sparseArray = mThermistorList;
    if (sparseArray == null || sparseArray.size() == 0) {
      initThermistorList();
    }
    return mThermistorList.get(thermistorType);
  }
}
