package com.android.server.pm.verify.domain.proxy;

import android.app.BroadcastOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationInfo;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.DeviceIdleInternal;
import com.android.server.pm.DomainVerificationConnection;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.verify.domain.DomainVerificationCollector;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.pm.verify.domain.models.DomainVerificationPkgState;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DomainVerificationProxyV1 implements DomainVerificationProxy {
    public final DomainVerificationCollector mCollector;
    public final DomainVerificationConnection mConnection;
    public final Context mContext;
    public final DomainVerificationManagerInternal mManager;
    public final ComponentName mVerifierComponent;
    public final Object mLock = new Object();
    public final ArrayMap mRequests = new ArrayMap();
    public int mVerificationToken = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Response {
        public final int callingUid;
        public final List failedDomains;
        public final int verificationId;

        public Response(int i, int i2, List list) {
            this.callingUid = i;
            this.verificationId = i2;
            this.failedDomains = list == null ? Collections.emptyList() : list;
        }
    }

    public DomainVerificationProxyV1(
            Context context,
            DomainVerificationManagerInternal domainVerificationManagerInternal,
            DomainVerificationCollector domainVerificationCollector,
            DomainVerificationConnection domainVerificationConnection,
            ComponentName componentName) {
        this.mContext = context;
        this.mConnection = domainVerificationConnection;
        this.mVerifierComponent = componentName;
        this.mManager = domainVerificationManagerInternal;
        this.mCollector = domainVerificationCollector;
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
        UUID uuid;
        DomainVerificationInfo domainVerificationInfo;
        boolean z = true;
        int i2 = 0;
        if (i == 2) {
            Set<String> set = (Set) obj;
            ArrayMap arrayMap = new ArrayMap(set.size());
            synchronized (this.mLock) {
                for (String str : set) {
                    DomainVerificationService domainVerificationService =
                            (DomainVerificationService) this.mManager;
                    synchronized (domainVerificationService.mLock) {
                        try {
                            DomainVerificationPkgState domainVerificationPkgState =
                                    (DomainVerificationPkgState)
                                            domainVerificationService.mAttachedPkgStates
                                                    .mPackageNameMap.get(str);
                            uuid =
                                    domainVerificationPkgState != null
                                            ? domainVerificationPkgState.mId
                                            : null;
                        } finally {
                        }
                    }
                    if (uuid != null) {
                        int i3 = this.mVerificationToken;
                        this.mVerificationToken = i3 + 1;
                        arrayMap.put(Integer.valueOf(i3), Pair.create(uuid, str));
                    }
                }
                this.mRequests.putAll(arrayMap);
            }
            DomainVerificationConnection domainVerificationConnection = this.mConnection;
            long max =
                    Math.max(
                            Settings.Global.getLong(
                                    domainVerificationConnection.mPm.mContext.getContentResolver(),
                                    "verifier_timeout",
                                    10000L),
                            10000L);
            ((DeviceIdleInternal)
                            domainVerificationConnection.mPm.mInjector.mGetLocalServiceProducer
                                    .produce(DeviceIdleInternal.class))
                    .addPowerSaveTempWhitelistApp(
                            Process.myUid(),
                            this.mVerifierComponent.getPackageName(),
                            max,
                            0,
                            true,
                            FrameworkStatsLog
                                    .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_DOMAIN_VERIFICATION_V1,
                            "domain verification agent");
            int size = arrayMap.size();
            int i4 = 0;
            while (i4 < size) {
                int intValue = ((Integer) arrayMap.keyAt(i4)).intValue();
                String str2 = (String) ((Pair) arrayMap.valueAt(i4)).second;
                AndroidPackage androidPackage =
                        domainVerificationConnection.mPm.snapshotComputer().getPackage(str2);
                if (androidPackage != null) {
                    ArraySet collectDomains = this.mCollector.collectDomains(androidPackage, z, z);
                    StringBuilder sb = new StringBuilder();
                    int size2 = collectDomains.size();
                    while (i2 < size2) {
                        if (i2 > 0) {
                            sb.append(" ");
                        }
                        String str3 = (String) collectDomains.valueAt(i2);
                        if (str3.startsWith("*.")) {
                            str3 = str3.substring(2);
                        }
                        sb.append(str3);
                        i2++;
                    }
                    Intent addFlags =
                            new Intent("android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION")
                                    .setComponent(this.mVerifierComponent)
                                    .putExtra(
                                            "android.content.pm.extra.INTENT_FILTER_VERIFICATION_ID",
                                            intValue)
                                    .putExtra(
                                            "android.content.pm.extra.INTENT_FILTER_VERIFICATION_URI_SCHEME",
                                            "https")
                                    .putExtra(
                                            "android.content.pm.extra.INTENT_FILTER_VERIFICATION_HOSTS",
                                            sb.toString())
                                    .putExtra(
                                            "android.content.pm.extra.INTENT_FILTER_VERIFICATION_PACKAGE_NAME",
                                            str2)
                                    .addFlags(268435456);
                    BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                    makeBasic.setTemporaryAppAllowlist(
                            max,
                            0,
                            FrameworkStatsLog
                                    .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_DOMAIN_VERIFICATION_V1,
                            "");
                    this.mContext.sendBroadcastAsUser(
                            addFlags, UserHandle.SYSTEM, null, makeBasic.toBundle());
                }
                i4++;
                z = true;
                i2 = 0;
            }
            return z;
        }
        if (i != 3) {
            return false;
        }
        Response response = (Response) obj;
        Pair pair = (Pair) this.mRequests.get(Integer.valueOf(response.verificationId));
        if (pair == null) {
            return true;
        }
        UUID uuid2 = (UUID) pair.first;
        String str4 = (String) pair.second;
        try {
            domainVerificationInfo =
                    ((DomainVerificationService) this.mManager).getDomainVerificationInfo(str4);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        if (domainVerificationInfo == null
                || !Objects.equals(uuid2, domainVerificationInfo.getIdentifier())
                || this.mConnection.mPm.snapshotComputer().getPackage(str4) == null) {
            return true;
        }
        ArraySet arraySet = new ArraySet(response.failedDomains);
        Set keySet = domainVerificationInfo.getHostToStateMap().keySet();
        ArraySet arraySet2 = new ArraySet(keySet);
        arraySet2.removeAll(arraySet);
        for (int size3 = arraySet2.size() - 1; size3 >= 0; size3--) {
            String str5 = (String) arraySet2.valueAt(size3);
            if (str5.startsWith("*.")) {
                String substring = str5.substring(2);
                if (arraySet.contains(substring)) {
                    arraySet.add(str5);
                    arraySet2.removeAt(size3);
                    if (!keySet.contains(substring)) {
                        arraySet.remove(substring);
                    }
                }
            }
        }
        int i5 = response.callingUid;
        if (!arraySet2.isEmpty()) {
            try {
                if (((DomainVerificationService) this.mManager)
                                .setDomainVerificationStatusInternal(i5, uuid2, arraySet2, 1)
                        != 0) {
                    Slog.e(
                            "DomainVerificationProxyV1",
                            "Failure reporting successful domains for " + str4);
                }
            } catch (Exception e) {
                Slog.e(
                        "DomainVerificationProxyV1",
                        "Failure reporting successful domains for " + str4,
                        e);
            }
        }
        if (!arraySet.isEmpty()) {
            try {
                if (((DomainVerificationService) this.mManager)
                                .setDomainVerificationStatusInternal(i5, uuid2, arraySet, 6)
                        != 0) {
                    Slog.e(
                            "DomainVerificationProxyV1",
                            "Failure reporting failed domains for " + str4);
                }
            } catch (Exception e2) {
                Slog.e(
                        "DomainVerificationProxyV1",
                        "Failure reporting failed domains for " + str4,
                        e2);
            }
        }
        return true;
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy
    public final void sendBroadcastForPackages(Set set) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mRequests.size() - 1; size >= 0; size--) {
                    if (set.contains(((Pair) this.mRequests.valueAt(size)).second)) {
                        this.mRequests.removeAt(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mConnection.schedule(2, set);
    }
}
