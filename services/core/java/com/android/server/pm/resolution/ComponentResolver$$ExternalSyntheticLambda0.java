package com.android.server.pm.resolution;

import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;

import java.util.Comparator;

public final /* synthetic */ class ComponentResolver$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ResolveInfo resolveInfo = (ResolveInfo) obj;
        ResolveInfo resolveInfo2 = (ResolveInfo) obj2;
        int i = resolveInfo.priority;
        int i2 = resolveInfo2.priority;
        if (i == i2) {
            int i3 = resolveInfo.preferredOrder;
            int i4 = resolveInfo2.preferredOrder;
            if (i3 == i4) {
                boolean z = resolveInfo.isDefault;
                if (z == resolveInfo2.isDefault) {
                    int i5 = resolveInfo.match;
                    int i6 = resolveInfo2.match;
                    if (i5 == i6) {
                        boolean z2 = resolveInfo.system;
                        if (z2 == resolveInfo2.system) {
                            ActivityInfo activityInfo = resolveInfo.activityInfo;
                            if (activityInfo != null) {
                                return activityInfo.packageName.compareTo(
                                        resolveInfo2.activityInfo.packageName);
                            }
                            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                            if (serviceInfo != null) {
                                return serviceInfo.packageName.compareTo(
                                        resolveInfo2.serviceInfo.packageName);
                            }
                            ProviderInfo providerInfo = resolveInfo.providerInfo;
                            if (providerInfo != null) {
                                return providerInfo.packageName.compareTo(
                                        resolveInfo2.providerInfo.packageName);
                            }
                            return 0;
                        }
                        if (!z2) {
                            return 1;
                        }
                    } else if (i5 <= i6) {
                        return 1;
                    }
                } else if (!z) {
                    return 1;
                }
            } else if (i3 <= i4) {
                return 1;
            }
        } else if (i <= i2) {
            return 1;
        }
        return -1;
    }
}
