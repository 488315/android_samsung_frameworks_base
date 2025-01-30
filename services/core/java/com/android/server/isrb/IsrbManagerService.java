package com.android.server.isrb;

import android.content.Context;
import com.android.server.SystemService;

/* loaded from: classes2.dex */
public class IsrbManagerService extends SystemService {
  public final IsrbManagerServiceImpl mIsrbManagerServiceImpl;

  public IsrbManagerService(Context context) {
    super(context);
    this.mIsrbManagerServiceImpl = new IsrbManagerServiceImpl(context);
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("isrb", this.mIsrbManagerServiceImpl);
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (i == 600) {
      this.mIsrbManagerServiceImpl.systemRunning();
    } else if (i == 1000) {
      this.mIsrbManagerServiceImpl.systemBootComplete();
    }
  }
}
