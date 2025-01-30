package com.android.server.tv;

import android.content.Context;
import com.android.server.SystemService;
import com.android.server.Watchdog;

/* loaded from: classes3.dex */
public class TvRemoteService extends SystemService implements Watchdog.Monitor {
  public final Object mLock;
  public final TvRemoteProviderWatcher mWatcher;

  @Override // com.android.server.SystemService
  public void onStart() {}

  public TvRemoteService(Context context) {
    super(context);
    Object obj = new Object();
    this.mLock = obj;
    this.mWatcher = new TvRemoteProviderWatcher(context, obj);
    Watchdog.getInstance().addMonitor(this);
  }

  @Override // com.android.server.Watchdog.Monitor
  public void monitor() {
    synchronized (this.mLock) {
    }
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (i == 600) {
      this.mWatcher.start();
    }
  }
}
