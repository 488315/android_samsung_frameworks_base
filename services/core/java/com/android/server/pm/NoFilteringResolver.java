package com.android.server.pm;

import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NoFilteringResolver extends CrossProfileResolver {
    @Override // com.android.server.pm.CrossProfileResolver
    public final List filterResolveInfoWithDomainPreferredActivity(int i, List list) {
        return list;
    }

    @Override // com.android.server.pm.CrossProfileResolver
    public final List resolveIntent(
            Computer computer,
            Intent intent,
            String str,
            int i,
            int i2,
            long j,
            String str2,
            List list,
            boolean z,
            Function function) {
        List queryActivities =
                this.mComponentResolver.queryActivities(computer, intent, str, j, i2);
        ArrayList arrayList = new ArrayList();
        if (queryActivities != null) {
            for (int i3 = 0; i3 < queryActivities.size(); i3++) {
                arrayList.add(
                        new CrossProfileDomainInfo((ResolveInfo) queryActivities.get(i3), 0, i2));
            }
        }
        CrossProfileResolver.filterIfNotSystemUser(i, arrayList);
        return arrayList;
    }
}
