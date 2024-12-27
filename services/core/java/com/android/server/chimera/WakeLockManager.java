package com.android.server.chimera;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
