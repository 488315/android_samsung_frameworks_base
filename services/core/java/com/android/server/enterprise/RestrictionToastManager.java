package com.android.server.enterprise;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class RestrictionToastManager {
  public static RestrictionToastHandler mRestrictionToastHandler;
  public static final ArrayList mRecentlyDisplayedMsgQueue = new ArrayList();
  public static Context mContext = null;

  public static void init(Context context) {
    mContext = context;
    HandlerThread handlerThread = new HandlerThread("RestrictionToastManagerThread");
    handlerThread.start();
    mRestrictionToastHandler = new RestrictionToastHandler(handlerThread.getLooper());
  }

  public static void show(int i) {
    Context context;
    if (i < 0 || (context = mContext) == null) {
      return;
    }
    try {
      show(context.getString(i));
    } catch (Resources.NotFoundException unused) {
      Log.e(
          "RestrictionToastManager",
          "Resource Id not found: will not display any restriction message toast");
    }
  }

  public static synchronized void show(String str) {
    synchronized (RestrictionToastManager.class) {
      if (str != null) {
        if (mContext != null) {
          ArrayList arrayList = mRecentlyDisplayedMsgQueue;
          if (arrayList.contains(str)) {
            return;
          }
          Message message = new Message();
          Bundle bundle = new Bundle();
          bundle.putString("message", str);
          message.setData(bundle);
          message.what = 2;
          mRestrictionToastHandler.sendMessage(message);
          arrayList.add(str);
          Log.d("RestrictionToastManager", "Added message to recently displayed queue: " + str);
          mRestrictionToastHandler.sendMessageDelayed(
              Message.obtain(mRestrictionToastHandler, 1, str), 3500L);
        }
      }
    }
  }

  public final class RestrictionToastHandler extends Handler {
    public RestrictionToastHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      String string;
      int i = message.what;
      if (i == 1) {
        RestrictionToastManager.mRecentlyDisplayedMsgQueue.remove(message.obj);
        Log.d(
            "RestrictionToastManager", "Removed message from recently displayed queue: " + message);
        return;
      }
      if (i != 2
          || (string = message.getData().getString("message")) == null
          || RestrictionToastManager.mContext == null) {
        return;
      }
      try {
        Toast.makeText(
                new ContextThemeWrapper(
                    RestrictionToastManager.mContext, R.style.Theme.DeviceDefault.Light),
                string,
                1)
            .show();
      } catch (Exception e) {
        Log.i("RestrictionToastManager", "displayToast fail : " + e.getMessage());
      }
    }
  }
}
