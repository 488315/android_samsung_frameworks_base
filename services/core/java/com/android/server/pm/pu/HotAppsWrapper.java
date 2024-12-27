package com.android.server.pm.pu;

import java.util.Iterator;
import java.util.List;

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
