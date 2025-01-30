package android.p009os;

/* loaded from: classes3.dex */
public class SemHqmManager {
  Handler mHandler;
  ISemHqmManager mService;
  private static final String TAG = SemHqmManager.class.getSimpleName();
  private static final boolean DEBUG = "eng".equals(Build.TYPE);
  private static final Object BDlock = new Object();
  private static final Object BDlock_sys = new Object();

  public SemHqmManager(ISemHqmManager service, Handler handler) {
    this.mService = null;
    this.mService = service;
    this.mHandler = handler;
  }

  public boolean sendHWParamServer(
      int type,
      String Id,
      String Ver,
      String Manufacture,
      String HitType,
      String Feature,
      String logMaps,
      String envlogMaps) {
    boolean sendHWParamServer;
    if (this.mService == null) {
      return false;
    }
    synchronized (BDlock) {
      try {
        try {
          sendHWParamServer =
              this.mService.sendHWParamServer(
                  type, Id, Ver, Manufacture, HitType, Feature, logMaps, envlogMaps);
        } catch (Exception e) {
          printExceptionTrace(e);
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return sendHWParamServer;
  }

  public boolean sendHWParamToHQM(
      int type,
      String id,
      String feature,
      String hitType,
      String ver,
      String manufacture,
      String dev_custom_dataset,
      String custom_dataset,
      String pri_custom_dataset) {
    boolean sendHWParamToHQM;
    if (this.mService == null) {
      return false;
    }
    synchronized (BDlock) {
      try {
        try {
          sendHWParamToHQM =
              this.mService.sendHWParamToHQM(
                  type,
                  id,
                  feature,
                  hitType,
                  ver,
                  manufacture,
                  dev_custom_dataset,
                  custom_dataset,
                  pri_custom_dataset);
        } catch (Exception e) {
          printExceptionTrace(e);
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return sendHWParamToHQM;
  }

  public boolean sendHWParamToHQMwithAppId(
      int type,
      String id,
      String feature,
      String hitType,
      String ver,
      String manufacture,
      String dev_custom_dataset,
      String custom_dataset,
      String pri_custom_dataset,
      String appID) {
    boolean sendHWParamToHQMwithAppId;
    if (this.mService == null) {
      return false;
    }
    synchronized (BDlock) {
      try {
        try {
          sendHWParamToHQMwithAppId =
              this.mService.sendHWParamToHQMwithAppId(
                  type,
                  id,
                  feature,
                  hitType,
                  ver,
                  manufacture,
                  dev_custom_dataset,
                  custom_dataset,
                  pri_custom_dataset,
                  appID);
        } catch (Exception e) {
          printExceptionTrace(e);
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return sendHWParamToHQMwithAppId;
  }

  public boolean sendHWParamToHQMwithFile(
      int type,
      String id,
      String feature,
      String hitType,
      String ver,
      String manufacture,
      String dev_custom_dataset,
      String custom_dataset,
      String pri_custom_dataset,
      String appID,
      String filePath) {
    boolean sendHWParamToHQMwithFile;
    if (this.mService == null) {
      return false;
    }
    synchronized (BDlock) {
      try {
        try {
          sendHWParamToHQMwithFile =
              this.mService.sendHWParamToHQMwithFile(
                  type,
                  id,
                  feature,
                  hitType,
                  ver,
                  manufacture,
                  dev_custom_dataset,
                  custom_dataset,
                  pri_custom_dataset,
                  appID,
                  filePath);
        } catch (Exception e) {
          printExceptionTrace(e);
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return sendHWParamToHQMwithFile;
  }

  public void sendSystemInfoToHQM(int type, String dataset, String sub_dataset) {
    if (this.mService == null) {
      return;
    }
    synchronized (BDlock_sys) {
      try {
        this.mService.sendSystemInfoToHQM(type, dataset, sub_dataset);
      } catch (Exception e) {
        printExceptionTrace(e);
      }
    }
  }

  public boolean getHqmEnable() {
    boolean hqmEnable;
    if (this.mService == null) {
      return false;
    }
    synchronized (BDlock) {
      try {
        try {
          hqmEnable = this.mService.getHqmEnable();
        } catch (Exception e) {
          printExceptionTrace(e);
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return hqmEnable;
  }

  public boolean getDVServerEnable() {
    boolean dVServerEnable;
    if (this.mService == null) {
      return false;
    }
    synchronized (BDlock) {
      try {
        try {
          dVServerEnable = this.mService.getDVServerEnable();
        } catch (Exception e) {
          printExceptionTrace(e);
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return dVServerEnable;
  }

  public boolean getCFServerEnable() {
    boolean cFServerEnable;
    if (this.mService == null) {
      return false;
    }
    synchronized (BDlock) {
      try {
        try {
          cFServerEnable = this.mService.getCFServerEnable();
        } catch (Exception e) {
          printExceptionTrace(e);
          return false;
        }
      } catch (Throwable th) {
        throw th;
      }
    }
    return cFServerEnable;
  }

  private static void printExceptionTrace(Exception e) {
    if (DEBUG) {
      e.printStackTrace();
    }
  }
}
