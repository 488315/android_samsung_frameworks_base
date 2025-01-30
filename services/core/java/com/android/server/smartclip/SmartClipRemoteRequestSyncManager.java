package com.android.server.smartclip;

import android.util.Log;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestResult;
import java.util.HashMap;

/* compiled from: SpenGestureManagerService.java */
/* loaded from: classes3.dex */
public class SmartClipRemoteRequestSyncManager {
  public static final String TAG = SpenGestureManagerService.TAG;
  public int mNextRequestId = 0;
  public HashMap mRequestMap = new HashMap();

  /* compiled from: SpenGestureManagerService.java */
  public class RequestInfo {
    public int mRequestId;
    public SmartClipRemoteRequestResult mResultData;
    public Object mWaitObj = new Object();
    public boolean mResponseArrived = false;
  }

  public SmartClipRemoteRequestResult waitResult(int i, int i2) {
    RequestInfo requestInfo;
    RequestInfo requestItem = getRequestItem(i);
    if (requestItem == null) {
      Log.e(TAG, "waitResult : Could not find request info!! id = " + i);
      return null;
    }
    long currentTimeMillis = System.currentTimeMillis();
    synchronized (requestItem.mWaitObj) {
      if (!requestItem.mResponseArrived) {
        try {
          requestItem.mWaitObj.wait(i2);
        } catch (InterruptedException e) {
          Log.e(TAG, "waitResult : e=" + e);
        }
      }
    }
    Log.i(
        TAG,
        "waitResult : Unlocked. Id = "
            + i
            + " Elapsed = "
            + (System.currentTimeMillis() - currentTimeMillis)
            + "ms");
    synchronized (this.mRequestMap) {
      requestInfo = (RequestInfo) this.mRequestMap.remove(Integer.valueOf(i));
    }
    return requestInfo.mResultData;
  }

  public void notifyResult(SmartClipRemoteRequestResult smartClipRemoteRequestResult) {
    RequestInfo requestItem = getRequestItem(smartClipRemoteRequestResult.mRequestId);
    if (requestItem == null) {
      Log.e(
          TAG,
          "notifyResult : Could not find request information. id="
              + smartClipRemoteRequestResult.mRequestId);
      return;
    }
    synchronized (requestItem.mWaitObj) {
      requestItem.mResultData = smartClipRemoteRequestResult;
      requestItem.mResponseArrived = true;
      requestItem.mWaitObj.notify();
    }
  }

  public RequestInfo getRequestItem(int i) {
    RequestInfo requestInfo;
    synchronized (this.mRequestMap) {
      requestInfo = (RequestInfo) this.mRequestMap.get(Integer.valueOf(i));
    }
    return requestInfo;
  }

  public int allocateNewRequestId(boolean z) {
    int i = this.mNextRequestId;
    this.mNextRequestId = i + 1;
    if (z) {
      RequestInfo requestInfo = new RequestInfo();
      requestInfo.mRequestId = i;
      synchronized (this.mRequestMap) {
        this.mRequestMap.put(Integer.valueOf(i), requestInfo);
      }
    }
    return i;
  }
}
