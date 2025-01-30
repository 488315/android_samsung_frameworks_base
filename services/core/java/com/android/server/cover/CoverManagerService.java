package com.android.server.cover;

import android.content.Context;
import com.android.server.SystemService;

/* loaded from: classes.dex */
public class CoverManagerService extends SystemService {
  public final CoverManagerServiceImpl mCoverManagerServiceImpl;

  public CoverManagerService(Context context) {
    super(context);
    this.mCoverManagerServiceImpl = new CoverManagerServiceImpl(context);
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("cover", this.mCoverManagerServiceImpl);
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (i == 600) {
      this.mCoverManagerServiceImpl.systemRunning();
    }
  }
}
