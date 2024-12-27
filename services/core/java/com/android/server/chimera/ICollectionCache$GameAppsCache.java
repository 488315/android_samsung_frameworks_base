package com.android.server.chimera;

import android.util.ArraySet;

import com.samsung.android.game.IGameManagerService;
import com.samsung.android.game.SemGameManager;

import java.util.Set;

public final class ICollectionCache$GameAppsCache {
    public final SystemRepository mSystemRepository;
    public final Set gameApps = new ArraySet();
    public final Set notGameApps = new ArraySet();
    public IGameManagerService gms =
            IGameManagerService.Stub.asInterface(SemGameManager.getGMSBinder());

    public ICollectionCache$GameAppsCache(SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0026, code lost:

       if (r0 == null) goto L12;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean contains(java.lang.String r4) {
        /*
            r3 = this;
            java.util.Set r0 = r3.gameApps
            android.util.ArraySet r0 = (android.util.ArraySet) r0
            boolean r0 = r0.contains(r4)
            r1 = 1
            if (r0 == 0) goto Lc
            return r1
        Lc:
            java.util.Set r0 = r3.notGameApps
            android.util.ArraySet r0 = (android.util.ArraySet) r0
            boolean r0 = r0.contains(r4)
            r2 = 0
            if (r0 == 0) goto L18
            return r2
        L18:
            com.samsung.android.game.IGameManagerService r0 = r3.gms
            if (r0 != 0) goto L2a
            android.os.IBinder r0 = com.samsung.android.game.SemGameManager.getGMSBinder()
            com.samsung.android.game.IGameManagerService r0 = com.samsung.android.game.IGameManagerService.Stub.asInterface(r0)
            r3.gms = r0
            if (r0 != 0) goto L2a
        L28:
            r1 = r2
            goto L60
        L2a:
            com.samsung.android.game.IGameManagerService r0 = r3.gms     // Catch: java.lang.Exception -> L42
            int r0 = r0.identifyGamePackage(r4)     // Catch: java.lang.Exception -> L42
            if (r0 != r1) goto L3a
            java.util.Set r3 = r3.gameApps
            android.util.ArraySet r3 = (android.util.ArraySet) r3
            r3.add(r4)
            goto L60
        L3a:
            java.util.Set r3 = r3.notGameApps
            android.util.ArraySet r3 = (android.util.ArraySet) r3
            r3.add(r4)
            goto L28
        L42:
            r4 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "GameManagerService RemoteException! "
            r0.<init>(r1)
            java.lang.String r4 = r4.getMessage()
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            com.android.server.chimera.SystemRepository r3 = r3.mSystemRepository
            r3.getClass()
            java.lang.String r3 = "ChimeraDataCache"
            com.android.server.chimera.SystemRepository.logDebug(r3, r4)
            goto L28
        L60:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.ICollectionCache$GameAppsCache.contains(java.lang.String):boolean");
    }
}
