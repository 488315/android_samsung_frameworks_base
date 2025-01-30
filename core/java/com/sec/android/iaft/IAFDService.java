package com.sec.android.iaft;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

/* loaded from: classes6.dex */
public class IAFDService extends Service {
  static final int CMD_ADDB_HOTFIXDB_UPDATE = 11;
  static final int CMD_HIGHBDB_HOTFIXDB_UPDATE = 12;
  static final int CMD_HOTFIX_DATA_GET = 9;
  static final int CMD_IAFDDB_HOTFIXDB_UPDATE = 10;
  static final int CMD_IAFD_DETECT = 13;
  static final int CMD_TYPE_GETUPDATESTATUS = 5;
  static final int CMD_TYPE_GETUPDATESTATUS_RESULT = 6;
  static final int CMD_TYPE_PARSE = 3;
  static final int CMD_TYPE_REPAIR = 2;
  static final int CMD_TYPE_SHOW = 4;
  static final int CMD_TYPE_START = 1;
  static final int CMD_TYPE_START_SmartManagerApp = 7;
  static final int CMD_TYPE_START_VocApp = 8;
  private static final String TAG = "IAFDService";
  private IAFDBinder mBinder;
  private Context mContext;
  private IAFDServiceImpl mIAFDServiceImpl;

  public class IAFDBinder extends IIAFDService.Stub {
    public IAFDBinder() {}

    @Override // com.sec.android.iaft.IIAFDService
    public boolean IAFDParse(
        String packageName,
        String nativeLibraryDir,
        int puserId,
        int appuid,
        int flags,
        String exceptionClassName,
        String exceptionMessage,
        String stackTrace) {
      return IAFDDiagnosis.getInstance()
          .parseExpType(
              packageName,
              nativeLibraryDir,
              puserId,
              appuid,
              flags,
              exceptionClassName,
              exceptionMessage,
              stackTrace);
    }

    @Override // com.sec.android.iaft.IIAFDService
    public void IAFDShow(int puserId, int appuid, String packageName) {
      IAFDDiagnosis.getInstance().showIAFDCrashDialogs(puserId, appuid, packageName);
    }
  }

  public void IAFDServiceInit(Context ct) {
    this.mContext = ct;
  }

  @Override // android.app.Service
  public void onCreate() {
    super.onCreate();
    IAFDServiceInit(this);
    this.mBinder = new IAFDBinder();
    this.mIAFDServiceImpl = new IAFDServiceImpl(this.mContext, null);
  }

  @Override // android.app.Service
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent == null) {
      return 1;
    }
    Bundle bundle = intent.getExtras();
    Message msg = new Message();
    if (bundle != null) {
      bundle.getInt("pkgUserId", -1);
      bundle.getString("checkSum", "");
      int commandtype = bundle.getInt("commandType", 0);
      if (1 == 0) {
        return 1;
      }
      switch (commandtype) {
        case 1:
        case 2:
        case 9:
        case 10:
        case 11:
        case 12:
          msg.what = commandtype;
          msg.setData(bundle);
          this.mIAFDServiceImpl.IAFDServiceHandlerMessage(msg);
        default:
          return 1;
      }
    }
    return 1;
  }

  @Override // android.app.Service
  public IBinder onBind(Intent intent) {
    return this.mBinder;
  }

  @Override // android.app.Service
  public boolean onUnbind(Intent intent) {
    return super.onUnbind(intent);
  }
}
