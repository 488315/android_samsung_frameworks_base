package com.samsung.android.sepunion;

import android.Manifest;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class SemExclusiveTaskManager {
  private static ISemExclusiveTaskManager mService;
  private static SemExclusiveTaskManager sInstance;
  private Context mContext;
  final int mUserId;
  private static final String TAG = SemExclusiveTaskManager.class.getSimpleName();
  static final Object sInstanceSync = new Object();

  public SemExclusiveTaskManager(Context context) {
    this.mContext = context;
    this.mUserId = -2;
  }

  public SemExclusiveTaskManager(Context context, ISemExclusiveTaskManager service, int userId) {
    this.mContext = context;
    this.mUserId = userId;
  }

  public static SemExclusiveTaskManager getInstance(Context context) {
    int userId;
    synchronized (sInstanceSync) {
      if (sInstance == null) {
        if (Binder.getCallingUid() != 1000
            && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS) != 0
            && context.checkCallingOrSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL)
                != 0) {
          userId = UserHandle.myUserId();
          sInstance = new SemExclusiveTaskManager(context, null, userId);
        }
        userId = -2;
        sInstance = new SemExclusiveTaskManager(context, null, userId);
      }
    }
    return sInstance;
  }

  private ISemExclusiveTaskManager getService() {
    if (mService == null) {
      SemUnionManager um =
          (SemUnionManager) this.mContext.getSystemService(Context.SEP_UNION_SERVICE);
      IBinder b = um.getSemSystemService(UnionConstants.SERVICE_EXCLUSIVE_TASK);
      mService = ISemExclusiveTaskManager.Stub.asInterface(b);
    }
    return mService;
  }

  public List<String> getExclusiveTaskList(String taskName) {
    ISemExclusiveTaskManager service = getService();
    if (service != null) {
      try {
        return service.getExclusiveTaskList(taskName);
      } catch (RemoteException e) {
        android.util.Log.m95d(TAG, "Failed to call getExclusiveTaskList()", e);
      }
    }
    return new ArrayList();
  }
}
