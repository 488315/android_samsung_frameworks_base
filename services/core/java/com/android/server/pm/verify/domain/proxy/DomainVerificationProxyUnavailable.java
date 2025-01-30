package com.android.server.pm.verify.domain.proxy;

import android.content.ComponentName;
import java.util.Set;

/* loaded from: classes3.dex */
public class DomainVerificationProxyUnavailable implements DomainVerificationProxy {
  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public ComponentName getComponentName() {
    return null;
  }

  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public boolean isCallerVerifier(int i) {
    return false;
  }

  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public boolean runMessage(int i, Object obj) {
    return false;
  }

  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public void sendBroadcastForPackages(Set set) {}
}
