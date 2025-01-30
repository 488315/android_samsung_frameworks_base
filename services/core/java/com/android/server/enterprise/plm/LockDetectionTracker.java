package com.android.server.enterprise.plm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.android.internal.widget.VerifyCredentialResponse;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class LockDetectionTracker {
  public static volatile LockDetectionTracker sInstance;
  public final Context mContext;
  public final Handler mHandler =
      new Handler(
          Looper
              .getMainLooper()) { // from class:
                                  // com.android.server.enterprise.plm.LockDetectionTracker.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
          if (message.what == 1) {
            LockDetectionTracker.this.handleLockDetection(message.arg1, message.arg2);
            return;
          }
          super.handleMessage(message);
        }
      };
  public final List mLockDetectionEventCallbacks = new ArrayList();

  public interface LockDetectionEventCallback {
    void onLockStateChange(String str, boolean z, int i, String str2);
  }

  public LockDetectionTracker(Context context) {
    this.mContext = context;
  }

  public static LockDetectionTracker getInstance(Context context) {
    synchronized (LockDetectionTracker.class) {
      if (sInstance == null) {
        sInstance = new LockDetectionTracker(context);
      }
    }
    return sInstance;
  }

  public void reportLockDetection(VerifyCredentialResponse verifyCredentialResponse, int i) {
    Message obtain = Message.obtain();
    obtain.what = 1;
    obtain.arg1 = verifyCredentialResponse.getResponseCode();
    obtain.arg2 = i;
    this.mHandler.sendMessage(obtain);
  }

  public final void handleLockDetection(int i, int i2) {
    boolean z;
    String localTime = LocalTime.now().toString();
    if (i == 0) {
      Log.d("LockDetection:", "verify credential success");
      z = true;
    } else {
      if (i != -1) {
        return;
      }
      Log.d("LockDetection:", "verify credential failure");
      z = false;
    }
    Iterator it = this.mLockDetectionEventCallbacks.iterator();
    while (it.hasNext()) {
      ((LockDetectionEventCallback) it.next())
          .onLockStateChange(
              "com.samsung.android.knox.intent.action.LOCK_STATE_CHANGE", z, i2, localTime);
    }
  }

  public void registerLockDetectionEventCallback(
      LockDetectionEventCallback lockDetectionEventCallback) {
    this.mLockDetectionEventCallbacks.add(lockDetectionEventCallback);
  }

  public void unregisterLockDetectionEventCallback(
      LockDetectionEventCallback lockDetectionEventCallback) {
    Iterator it = this.mLockDetectionEventCallbacks.iterator();
    while (it.hasNext()) {
      if (((LockDetectionEventCallback) it.next()) == lockDetectionEventCallback) {
        it.remove();
        return;
      }
    }
  }
}
