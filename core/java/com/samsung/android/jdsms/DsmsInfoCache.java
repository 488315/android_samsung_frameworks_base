package com.samsung.android.jdsms;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import com.samsung.android.dsms.aidl.IDsmsInfoService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes5.dex */
public final class DsmsInfoCache {
  private static final String SUBTAG = "DsmsInfoCache";
  private static final long TIMEOUT = TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
  private static DsmsInfoCache sInstance;
  private Context mContext;
  private boolean mIsCommercializedDevice;
  private boolean mIsCommercializedDeviceCached;

  public static synchronized DsmsInfoCache getInstance() {
    DsmsInfoCache dsmsInfoCache;
    synchronized (DsmsInfoCache.class) {
      if (sInstance == null) {
        sInstance = new DsmsInfoCache();
      }
      dsmsInfoCache = sInstance;
    }
    return dsmsInfoCache;
  }

  private DsmsInfoCache() {}

  public void setContext(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("context is null");
    }
    this.mContext = context;
  }

  public boolean isCommercializedDeviceCached() {
    return this.mIsCommercializedDeviceCached;
  }

  public void clearCommercializedDeviceCache() {
    this.mIsCommercializedDeviceCached = false;
  }

  public boolean isCommercializedDevice() {
    if (this.mIsCommercializedDeviceCached) {
      return this.mIsCommercializedDevice;
    }
    updateCommercializedDeviceCache();
    return this.mIsCommercializedDevice;
  }

  public void updateCommercializedDeviceCache() {
    DsmsInfoServiceClient service = new DsmsInfoServiceClient();
    try {
      try {
        service.bind();
        if (service.isBound()) {
          service.waitConnection(TIMEOUT);
          if (service.isConnected()) {
            this.mIsCommercializedDevice = service.isCommercializedDevice();
            this.mIsCommercializedDeviceCached = true;
            DsmsLog.m279d(SUBTAG, "Updated commercialized device cache");
          }
        }
        if (!service.isBound()) {
          return;
        }
      } catch (RemoteException | IllegalStateException | SecurityException | TimeoutException e) {
        DsmsLog.m281e(SUBTAG, e.getMessage());
        if (!service.isBound()) {
          return;
        }
      }
      service.unbind();
    } catch (Throwable th) {
      if (service.isBound()) {
        service.unbind();
      }
      throw th;
    }
  }

  private final class DsmsInfoServiceClient {
    private static final String ACTION_INFO = "com.samsung.android.dsms.action.INFO";
    private static final String DSMS_PACKAGE = "com.samsung.android.dsms";
    private static final String SUBTAG = "DsmsInfoServiceClient";
    private final ServiceConnection mConnection;
    private IDsmsInfoService mIDsmsInfoService;
    private boolean mIsBound;
    private Object mLock;

    private DsmsInfoServiceClient() {
      this.mLock = new Object();
      this.mIsBound = false;
      this.mIDsmsInfoService = null;
      this.mConnection =
          new ServiceConnection() { // from class:
                                    // com.samsung.android.jdsms.DsmsInfoCache.DsmsInfoServiceClient.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
              synchronized (DsmsInfoServiceClient.this.mLock) {
                DsmsInfoServiceClient.this.mIDsmsInfoService =
                    IDsmsInfoService.Stub.asInterface(service);
                DsmsInfoServiceClient.this.mLock.notifyAll();
              }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
              synchronized (DsmsInfoServiceClient.this.mLock) {
                DsmsInfoServiceClient.this.mIDsmsInfoService = null;
              }
            }
          };
    }

    public boolean isBound() {
      return this.mIsBound;
    }

    public boolean isConnected() {
      boolean z;
      synchronized (this.mLock) {
        z = this.mIDsmsInfoService != null;
      }
      return z;
    }

    public boolean bind() {
      if (!this.mIsBound) {
        DsmsLog.m279d(SUBTAG, "Binding to service");
        Intent intent = new Intent();
        intent.setPackage(DSMS_PACKAGE);
        intent.setAction(ACTION_INFO);
        boolean bindServiceAsUser =
            DsmsInfoCache.this.mContext.bindServiceAsUser(
                intent, this.mConnection, 1, UserHandle.SYSTEM);
        this.mIsBound = bindServiceAsUser;
        if (bindServiceAsUser) {
          DsmsLog.m279d(SUBTAG, "Service is bound");
        } else {
          DsmsLog.m281e(SUBTAG, "Could not bind to service");
        }
      }
      return this.mIsBound;
    }

    public void waitConnection(long timeout) throws TimeoutException {
      DsmsLog.m279d(SUBTAG, "Wait service connection");
      if (timeout < 0) {
        throw new IllegalArgumentException("Timeout is invalid");
      }
      long startTime = System.currentTimeMillis() + timeout;
      while (true) {
        long currentTime = System.currentTimeMillis();
        long remainingTime = startTime - currentTime;
        if (remainingTime <= 0) {
          throw new TimeoutException("Time waiting connection is over");
        }
        synchronized (this.mLock) {
          if (this.mIDsmsInfoService != null) {
            DsmsLog.m279d(SUBTAG, "Service is connected");
            return;
          } else {
            try {
              this.mLock.wait(remainingTime);
            } catch (InterruptedException e) {
              DsmsLog.m279d(SUBTAG, "Interrupted while waiting remaining time");
            }
          }
        }
      }
    }

    public void unbind() {
      if (this.mIsBound) {
        DsmsInfoCache.this.mContext.unbindService(this.mConnection);
        this.mIsBound = false;
        DsmsLog.m279d(SUBTAG, "Service unbound");
      }
    }

    public boolean isCommercializedDevice() throws RemoteException {
      boolean isCommercializedDevice;
      synchronized (this.mLock) {
        IDsmsInfoService iDsmsInfoService = this.mIDsmsInfoService;
        if (iDsmsInfoService == null) {
          throw new IllegalStateException("Service is not connected");
        }
        isCommercializedDevice = iDsmsInfoService.isCommercializedDevice();
      }
      return isCommercializedDevice;
    }
  }
}
