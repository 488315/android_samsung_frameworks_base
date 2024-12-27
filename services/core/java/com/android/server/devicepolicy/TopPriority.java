package com.android.server.devicepolicy;

import android.app.admin.DeviceAdminAuthority;
import android.app.admin.DpcAuthority;
import android.app.admin.PolicyValue;
import android.app.admin.RoleAuthority;
import android.app.admin.UnknownAuthority;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TopPriority extends ResolutionMechanism {
    public final List mHighestToLowestPriorityAuthorities;

    public TopPriority(List list) {
        Objects.requireNonNull(list);
        this.mHighestToLowestPriorityAuthorities = list;
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final android.app.admin.ResolutionMechanism getParcelableResolutionMechanism() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.mHighestToLowestPriorityAuthorities) {
            arrayList.add((str == null || str.isEmpty()) ? UnknownAuthority.UNKNOWN_AUTHORITY : "enterprise".equals(str) ? DpcAuthority.DPC_AUTHORITY : "device_admin".equals(str) ? DeviceAdminAuthority.DEVICE_ADMIN_AUTHORITY : str.startsWith("role:") ? new RoleAuthority(Set.of(str.substring(5))) : UnknownAuthority.UNKNOWN_AUTHORITY);
        }
        return new android.app.admin.TopPriority(arrayList);
    }

    @Override // com.android.server.devicepolicy.ResolutionMechanism
    public final PolicyValue resolve(LinkedHashMap linkedHashMap) {
        if (linkedHashMap.isEmpty()) {
            return null;
        }
        for (final String str : this.mHighestToLowestPriorityAuthorities) {
            Optional findFirst = linkedHashMap.keySet().stream().filter(new Predicate() { // from class: com.android.server.devicepolicy.TopPriority$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((EnforcingAdmin) obj).hasAuthority(str);
                }
            }).findFirst();
            if (findFirst.isPresent()) {
                return (PolicyValue) linkedHashMap.get(findFirst.get());
            }
        }
        return (PolicyValue) ((Map.Entry) linkedHashMap.entrySet().stream().findFirst().get()).getValue();
    }

    public final String toString() {
        return "TopPriority { mHighestToLowestPriorityAuthorities= " + this.mHighestToLowestPriorityAuthorities + " }";
    }
}
