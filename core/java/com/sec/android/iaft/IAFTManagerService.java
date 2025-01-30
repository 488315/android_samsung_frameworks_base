package com.sec.android.iaft;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/* loaded from: classes6.dex */
public class IAFTManagerService extends Service {
  private static final String TAG = "IAFTManager";
  private CallBack mCallBack;
  private Context mContext = this;
  private IAFTManagerServiceImpl mIAFTManagerServiceImpl =
      new IAFTManagerServiceImpl(this.mContext);

  public interface CallBack {
    void traceResult(String str, int i, int i2);

    void traceStopped();
  }

  public CallBack getCallBack() {
    return this.mCallBack;
  }

  public void setCallBack(CallBack callBack) {
    this.mCallBack = callBack;
  }

  @Override // android.app.Service
  public void onCreate() {
    super.onCreate();
  }

  @Override // android.app.Service
  public int onStartCommand(Intent intent, int flags, int startId) {
    return 3;
  }

  @Override // android.app.Service
  public IBinder onBind(Intent intent) {
    return this.mIAFTManagerServiceImpl;
  }

  @Override // android.app.Service
  public boolean onUnbind(Intent intent) {
    Log.m94d(TAG, "called onUnbind.");
    return true;
  }
}
