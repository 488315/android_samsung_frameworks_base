package com.android.server.sepunion.eventdelegator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.UserManager;
import com.android.server.sepunion.SemDeviceInfoManagerService;
import com.samsung.android.sepunion.Log;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class UnionContentBroadcastReceiver extends BroadcastReceiver {
  public static final String TAG = SemDeviceInfoManagerService.TAG;
  public final SemDeviceInfoManagerService mService;

  public UnionContentBroadcastReceiver(SemDeviceInfoManagerService semDeviceInfoManagerService) {
    this.mService = semDeviceInfoManagerService;
  }

  @Override // android.content.BroadcastReceiver
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    int i =
        intent.getExtras() != null
            ? intent.getExtras().getInt("android.intent.extra.user_handle")
            : -10000;
    Log.d(TAG, "onReceive : " + action + ", userId = " + i);
    if (i == -1) {
      Iterator it =
          ((UserManager) context.getSystemService(UserManager.class)).getUsers().iterator();
      while (it.hasNext()) {
        sendIntentAsUser(intent, action, ((UserInfo) it.next()).id);
      }
    } else if (i >= 0) {
      sendIntentAsUser(intent, action, i);
    } else if (i == -10000) {
      sendIntentAsUser(intent, action, 0);
    }
  }

  public final void sendIntentAsUser(Intent intent, String str, int i) {
    synchronized (this.mService.mLock) {
      ListenerContainer listenerContainer = this.mService.getListenerContainer(i);
      if (listenerContainer.mIntentActionMap.containsKey(str)) {
        this.mService.sendIntentAction(
            (UnionEventListenerItem) listenerContainer.mIntentActionMap.get(str), intent, i);
      }
    }
  }
}
