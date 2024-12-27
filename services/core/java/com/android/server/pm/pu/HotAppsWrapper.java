package com.android.server.pm.pu;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class HotAppsWrapper {
    public final List mApps;

    public HotAppsWrapper(List list) {
        this.mApps = list;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.mApps.isEmpty()) {
            sb.append("Hot apps list:");
            Iterator it = this.mApps.iterator();
            while (it.hasNext()) {
                sb.append("\n  " + ((ProfileUtilizationService.App) it.next()));
            }
        }
        return sb.toString();
    }
}
