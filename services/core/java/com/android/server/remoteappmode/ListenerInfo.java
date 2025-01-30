package com.android.server.remoteappmode;

import android.os.IBinder;

/* loaded from: classes3.dex */
public abstract class ListenerInfo implements IBinder.DeathRecipient {
  public final String name;
  public final int pid;
  public final int uid;

  @Override // android.os.IBinder.DeathRecipient
  public void binderDied() {}

  public ListenerInfo(String str, int i, int i2) {
    this.name = str;
    this.pid = i;
    this.uid = i2;
  }

  public String toString() {
    return getClass().getSimpleName()
        + "(name="
        + this.name
        + ", pid="
        + this.pid
        + ", uid="
        + this.uid
        + ")";
  }
}
