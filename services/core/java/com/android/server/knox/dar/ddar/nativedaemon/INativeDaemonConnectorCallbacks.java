package com.android.server.knox.dar.ddar.nativedaemon;

/* loaded from: classes2.dex */
public interface INativeDaemonConnectorCallbacks {
  void onDaemonConnected();

  boolean onDaemonDisconnected();

  boolean onEvent(int i, String str, String[] strArr);
}
