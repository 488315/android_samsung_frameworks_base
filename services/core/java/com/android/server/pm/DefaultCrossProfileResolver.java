package com.android.server.pm;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.android.internal.util.CollectionUtils;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.resolution.ComponentResolverApi;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DefaultCrossProfileResolver extends CrossProfileResolver {
    public final DomainVerificationManagerInternal mDomainVerificationManager;

    public DefaultCrossProfileResolver(ComponentResolverApi componentResolverApi, UserManagerService userManagerService, DomainVerificationManagerInternal domainVerificationManagerInternal) {
        super(componentResolverApi, userManagerService);
        this.mDomainVerificationManager = domainVerificationManagerInternal;
    }

    public final CrossProfileDomainInfo createForwardingResolveInfo(Computer computer, CrossProfileIntentFilter crossProfileIntentFilter, Intent intent, String str, long j, int i, Function function) {
        ResolveInfo resolveInfo;
        PackageStateInternal packageStateInternal;
        int i2 = crossProfileIntentFilter.mTargetUserId;
        if (!isUserEnabled(i2)) {
            return null;
        }
        List queryActivities = this.mComponentResolver.queryActivities(computer, intent, str, j, i2);
        if (CollectionUtils.isEmpty(queryActivities)) {
            return null;
        }
        int size = queryActivities.size() - 1;
        while (true) {
            if (size < 0) {
                resolveInfo = null;
                break;
            }
            if ((((ResolveInfo) queryActivities.get(size)).activityInfo.applicationInfo.flags & 1073741824) == 0) {
                resolveInfo = computer.createForwardingResolveInfoUnchecked(crossProfileIntentFilter, i, i2);
                break;
            }
            size--;
        }
        if (resolveInfo == null) {
            return null;
        }
        int size2 = queryActivities.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size2; i4++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) queryActivities.get(i4);
            if (!resolveInfo2.handleAllWebDataURI && (packageStateInternal = (PackageStateInternal) function.apply(resolveInfo2.activityInfo.packageName)) != null) {
                i3 = Math.max(i3, ((DomainVerificationService) this.mDomainVerificationManager).approvalLevelForDomain(packageStateInternal, intent, j, i2));
            }
        }
        return new CrossProfileDomainInfo(resolveInfo, i3, i2);
    }

    @Override // com.android.server.pm.CrossProfileResolver
    public final List filterResolveInfoWithDomainPreferredActivity(int i, List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                CrossProfileDomainInfo crossProfileDomainInfo = (CrossProfileDomainInfo) list.get(i2);
                if (crossProfileDomainInfo.mHighestApprovalLevel > i) {
                    arrayList.add(crossProfileDomainInfo);
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00fd  */
    @Override // com.android.server.pm.CrossProfileResolver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List resolveIntent(com.android.server.pm.Computer r19, android.content.Intent r20, java.lang.String r21, int r22, int r23, long r24, java.lang.String r26, java.util.List r27, boolean r28, java.util.function.Function r29) {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.DefaultCrossProfileResolver.resolveIntent(com.android.server.pm.Computer, android.content.Intent, java.lang.String, int, int, long, java.lang.String, java.util.List, boolean, java.util.function.Function):java.util.List");
    }
}
