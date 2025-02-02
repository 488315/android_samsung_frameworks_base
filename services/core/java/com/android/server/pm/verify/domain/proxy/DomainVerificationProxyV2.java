package com.android.server.pm.verify.domain.proxy;

import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.verify.domain.DomainVerificationRequest;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import java.util.Set;

/* loaded from: classes3.dex */
public class DomainVerificationProxyV2 implements DomainVerificationProxy {
  public final Connection mConnection;
  public final Context mContext;
  public final ComponentName mVerifierComponent;

  public interface Connection extends DomainVerificationProxy.BaseConnection {}

  public DomainVerificationProxyV2(
      Context context, Connection connection, ComponentName componentName) {
    this.mContext = context;
    this.mConnection = connection;
    this.mVerifierComponent = componentName;
  }

  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public void sendBroadcastForPackages(Set set) {
    this.mConnection.schedule(1, set);
  }

  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public boolean runMessage(int i, Object obj) {
    if (i != 1) {
      return false;
    }
    Parcelable domainVerificationRequest = new DomainVerificationRequest((Set) obj);
    long powerSaveTempWhitelistAppDuration =
        this.mConnection.getPowerSaveTempWhitelistAppDuration();
    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
    makeBasic.setTemporaryAppAllowlist(powerSaveTempWhitelistAppDuration, 0, 308, "");
    this.mConnection
        .getDeviceIdleInternal()
        .addPowerSaveTempWhitelistApp(
            Process.myUid(),
            this.mVerifierComponent.getPackageName(),
            powerSaveTempWhitelistAppDuration,
            0,
            true,
            308,
            "domain verification agent");
    this.mContext.sendBroadcastAsUser(
        new Intent("android.intent.action.DOMAINS_NEED_VERIFICATION")
            .setComponent(this.mVerifierComponent)
            .putExtra(
                "android.content.pm.verify.domain.extra.VERIFICATION_REQUEST",
                domainVerificationRequest)
            .addFlags(268435456),
        UserHandle.SYSTEM,
        null,
        makeBasic.toBundle());
    return true;
  }

  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public boolean isCallerVerifier(int i) {
    return this.mConnection.isCallerPackage(i, this.mVerifierComponent.getPackageName());
  }

  @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
  public ComponentName getComponentName() {
    return this.mVerifierComponent;
  }
}
