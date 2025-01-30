package com.samsung.android.knox;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/* loaded from: classes5.dex */
public abstract class ContainerCoreService extends Service {
  public abstract Bundle onCommandReceived(String str, Bundle bundle);

  public abstract Bundle onEvent(String str, Bundle bundle);

  public abstract Bundle onPolicyUpdated(String str, Bundle bundle);

  public abstract Bundle onTestMessage(String str, Bundle bundle);

  @Override // android.app.Service
  public final IBinder onBind(Intent intent) {
    return new ServiceImpl();
  }

  private final class ServiceImpl extends IContainerService.Stub {
    private ServiceImpl() {}

    @Override // com.samsung.android.knox.IContainerService
    public Bundle onMessage(String action, Bundle args) {
      if (action.startsWith("knox.container.proxy.COMMAND_")) {
        return ContainerCoreService.this.onCommandReceived(action, args);
      }
      if (action.startsWith("knox.container.proxy.POLICY_")) {
        return ContainerCoreService.this.onPolicyUpdated(action, args);
      }
      if (action.startsWith("knox.container.proxy.EVENT_")) {
        return ContainerCoreService.this.onEvent(action, args);
      }
      if (action.startsWith(ContainerProxy.TEST_BASE)) {
        return ContainerCoreService.this.onTestMessage(action, args);
      }
      Bundle b = new Bundle();
      b.putInt("android.intent.extra.RETURN_RESULT", 2);
      return b;
    }
  }
}
