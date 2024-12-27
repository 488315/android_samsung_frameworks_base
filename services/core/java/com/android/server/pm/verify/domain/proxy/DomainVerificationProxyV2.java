package com.android.server.pm.verify.domain.proxy;

import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.verify.domain.DomainVerificationRequest;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.server.DeviceIdleInternal;
import com.android.server.pm.DomainVerificationConnection;

import java.util.Set;

public final class DomainVerificationProxyV2 implements DomainVerificationProxy {
    public final DomainVerificationConnection mConnection;
    public final Context mContext;
    public final ComponentName mVerifierComponent;

    public DomainVerificationProxyV2(
            Context context,
            DomainVerificationConnection domainVerificationConnection,
            ComponentName componentName) {
        this.mContext = context;
        this.mConnection = domainVerificationConnection;
        this.mVerifierComponent = componentName;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final ComponentName getComponentName() {
        return this.mVerifierComponent;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final boolean isCallerVerifier(int i) {
        String packageName = this.mVerifierComponent.getPackageName();
        DomainVerificationConnection domainVerificationConnection = this.mConnection;
        domainVerificationConnection.getClass();
        return i
                == domainVerificationConnection
                        .mPm
                        .snapshotComputer()
                        .getPackageUid(packageName, 0L, UserHandle.getUserId(i));
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final boolean runMessage(int i, Object obj) {
        if (i != 1) {
            return false;
        }
        Parcelable domainVerificationRequest = new DomainVerificationRequest((Set) obj);
        DomainVerificationConnection domainVerificationConnection = this.mConnection;
        long max =
                Math.max(
                        Settings.Global.getLong(
                                domainVerificationConnection.mPm.mContext.getContentResolver(),
                                "verifier_timeout",
                                10000L),
                        10000L);
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(max, 0, 308, "");
        ((DeviceIdleInternal)
                        domainVerificationConnection.mPm.mInjector.mGetLocalServiceProducer.produce(
                                DeviceIdleInternal.class))
                .addPowerSaveTempWhitelistApp(
                        Process.myUid(),
                        this.mVerifierComponent.getPackageName(),
                        max,
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
    public final void sendBroadcastForPackages(Set set) {
        this.mConnection.schedule(1, set);
    }
}
