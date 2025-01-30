package com.samsung.android.allshare;

import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import com.sec.android.allshare.iface.CVMessage;

/* loaded from: classes5.dex */
abstract class AllShareEventHandler extends Handler {
  private static final String TAG = "AllShareEventHandler";

  abstract void handleEventMessage(CVMessage cVMessage);

  protected AllShareEventHandler(Looper looper) {
    super(looper);
  }

  @Override // android.p009os.Handler
  public void handleMessage(Message msg) {
    Bundle bundle = msg.getData();
    bundle.setClassLoader(getClass().getClassLoader());
    CVMessage cvm = (CVMessage) bundle.getParcelable(CVMessage.EVT_MSG_KEY);
    handleEventMessage(cvm);
  }
}
