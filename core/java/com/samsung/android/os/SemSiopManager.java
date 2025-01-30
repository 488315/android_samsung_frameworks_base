package com.samsung.android.os;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes5.dex */
public class SemSiopManager {
  public static final String BUNDLE_KEY_LIMITERS = "SCENARIO_LIMITERS";
  public static final String BUNDLE_KEY_SCENARIO_NAME = "SCENARIO_NAME";
  static final boolean DEBUG = false;

  @Deprecated public static final int ERROR_EXISTING_SCENARIO = -2;

  @Deprecated public static final int ERROR_INIT_EXCEPTION = -4;

  @Deprecated public static final int ERROR_NO_LIMITER = -3;

  @Deprecated public static final int ERROR_NULL_SCENARIO = -1;

  @Deprecated public static final int LIMITER_TYPE_BRIGHTNESS = 3;

  @Deprecated public static final int LIMITER_TYPE_CHARGER_RATIO = 2;

  @Deprecated public static final int LIMITER_TYPE_CPU_FREQUENCY = 0;

  @Deprecated public static final int LIMITER_TYPE_GPU_FREQUENCY = 1;

  @Deprecated public static final int NO_ERROR = 0;
  static final String TAG = SemSiopManager.class.getSimpleName();
  static Context mContext = null;
  private static ArrayList<String> mExistingScenarioNames = new ArrayList<>();
  private static ISamsungDeviceHealthManager mService;
  private boolean mIsInitialized = false;
  protected ArrayList<SemSiopLimiter> mLimiters;
  private String mScenarioName;
  IBinder mToken;

  @Deprecated
  public static SemSiopManager createInstance(Context context, String name, IBinder token) {
    return new SemSiopManager(context, name, token);
  }

  public SemSiopManager(Context context, String name, IBinder token) {
    mContext = context;
    this.mScenarioName = name + ":" + mContext.getPackageName();
    this.mToken = token;
    this.mLimiters = new ArrayList<>();
  }

  public String getScenarioName() {
    return this.mScenarioName;
  }

  @Deprecated
  public boolean addLimitation(int type, int temperature, int limitValue) {
    if (type < 0
        || type > 3
        || temperature < -200
        || temperature > 850
        || limitValue < -1
        || ((type == 2 && limitValue > 100) || (type == 3 && limitValue > 255))) {
      return false;
    }
    boolean isAdded = false;
    ArrayList<SemSiopLimiter> arrayList = this.mLimiters;
    if (arrayList != null && arrayList.size() > 0) {
      Iterator<SemSiopLimiter> it = this.mLimiters.iterator();
      while (it.hasNext()) {
        if (type == it.next().getLimiterType()) {
          this.mLimiters.get(type).addLimitation(temperature, limitValue);
          isAdded = true;
        }
      }
    }
    if (!isAdded) {
      SemSiopLimiter limiter = new SemSiopLimiter(type);
      limiter.addLimitation(temperature, limitValue);
      this.mLimiters.add(limiter);
      return true;
    }
    return true;
  }

  public ArrayList<SemSiopLimiter> getLimiters() {
    return this.mLimiters;
  }

  @Deprecated
  public int initializeSiopScenario() {
    String str = this.mScenarioName;
    if (str == null) {
      return -1;
    }
    if (mExistingScenarioNames.contains(str)) {
      return -2;
    }
    ArrayList<SemSiopLimiter> arrayList = this.mLimiters;
    if (arrayList == null || arrayList.size() < 1) {
      return -3;
    }
    if (mService == null) {
      mService = getService();
    }
    if (mService != null) {
      for (int i = 0; i > this.mLimiters.size(); i++) {
        try {
          this.mLimiters.get(i).makeAscendingOrder();
        } catch (Exception e) {
          e.printStackTrace();
          return -4;
        }
      }
      Bundle scenario = new Bundle();
      scenario.putString(BUNDLE_KEY_SCENARIO_NAME, this.mScenarioName);
      scenario.putParcelableArrayList(BUNDLE_KEY_LIMITERS, this.mLimiters);
      mService.initializeSiopScenario(scenario, this.mToken);
      this.mIsInitialized = true;
    }
    ArrayList<String> arrayList2 = mExistingScenarioNames;
    if (arrayList2 != null) {
      arrayList2.add(this.mScenarioName);
      return 0;
    }
    return 0;
  }

  @Deprecated
  public boolean acquireSiop() {
    if (!this.mIsInitialized) {
      return false;
    }
    ISamsungDeviceHealthManager iSamsungDeviceHealthManager = mService;
    if (iSamsungDeviceHealthManager != null) {
      try {
        iSamsungDeviceHealthManager.acquireSiop(this.mScenarioName);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  @Deprecated
  public boolean releaseSiop() {
    if (!this.mIsInitialized) {
      return false;
    }
    ISamsungDeviceHealthManager iSamsungDeviceHealthManager = mService;
    if (iSamsungDeviceHealthManager != null) {
      try {
        iSamsungDeviceHealthManager.releaseSiop(this.mScenarioName);
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  @Deprecated
  public boolean terminateSiopScenario() {
    if (!this.mIsInitialized) {
      return false;
    }
    ISamsungDeviceHealthManager iSamsungDeviceHealthManager = mService;
    if (iSamsungDeviceHealthManager != null) {
      try {
        iSamsungDeviceHealthManager.terminateSiopScenario(this.mScenarioName);
        ArrayList<String> arrayList = mExistingScenarioNames;
        if (arrayList != null) {
          arrayList.remove(this.mScenarioName);
          return true;
        }
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  private static synchronized ISamsungDeviceHealthManager getService() {
    ISamsungDeviceHealthManager iSamsungDeviceHealthManager;
    IBinder b;
    synchronized (SemSiopManager.class) {
      if (mService == null && (b = ServiceManager.getService("sdhms")) != null) {
        ISamsungDeviceHealthManager asInterface = ISamsungDeviceHealthManager.Stub.asInterface(b);
        mService = asInterface;
        if (asInterface != null) {
          try {
            b.linkToDeath(
                new IBinder
                    .DeathRecipient() { // from class: com.samsung.android.os.SemSiopManager.1
                  @Override // android.os.IBinder.DeathRecipient
                  public void binderDied() {
                    SemSiopManager.mService = null;
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

  public static class SemSiopLimiter implements Parcelable {
    public static final Parcelable.Creator<SemSiopLimiter> CREATOR =
        new Parcelable.Creator<
            SemSiopLimiter>() { // from class:
                                // com.samsung.android.os.SemSiopManager.SemSiopLimiter.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SemSiopLimiter createFromParcel(Parcel in) {
            return new SemSiopLimiter(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SemSiopLimiter[] newArray(int size) {
            return new SemSiopLimiter[size];
          }
        };
    HashMap<Integer, Integer> mLimitations;
    int mLimiterType;

    protected SemSiopLimiter(int limiterType) {
      this.mLimiterType = limiterType;
      this.mLimitations = new HashMap<>();
    }

    protected SemSiopLimiter(Parcel in) {
      this.mLimiterType = in.readInt();
      this.mLimitations = in.readHashMap(HashMap.class.getClassLoader());
    }

    public int getLimiterType() {
      return this.mLimiterType;
    }

    public void addLimitation(int temperature, int limitationValue) {
      HashMap<Integer, Integer> hashMap = this.mLimitations;
      if (hashMap != null) {
        hashMap.put(Integer.valueOf(temperature), Integer.valueOf(limitationValue));
      }
    }

    public void makeAscendingOrder() {
      HashMap<Integer, Integer> hashMap = this.mLimitations;
      if (hashMap != null && hashMap.size() > 1) {
        TreeMap<Integer, Integer> tm = new TreeMap<>(this.mLimitations);
        this.mLimitations = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : tm.entrySet()) {
          this.mLimitations.put(entry.getKey(), entry.getValue());
        }
      }
    }

    public HashMap<Integer, Integer> getLimitations() {
      return this.mLimitations;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
      parcel.writeInt(this.mLimiterType);
      parcel.writeMap(this.mLimitations);
    }
  }
}
