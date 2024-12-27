package com.android.server.chimera;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WakeLockManager {
    public final SystemRepository mSystemRepository;
    public final Set mWakeLockPackages = new HashSet();

    public WakeLockManager(SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = ((HashSet) this.mWakeLockPackages).iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
            sb.append(" ");
        }
        return sb.toString();
    }
}
