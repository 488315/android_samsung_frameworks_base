package com.android.server.chimera;

import android.util.ArraySet;
import com.samsung.android.game.IGameManagerService;
import com.samsung.android.game.SemGameManager;
import java.util.Set;

/* compiled from: ChimeraDataCache.java */
/* loaded from: classes.dex */
public class ICollectionCache$GameAppsCache {
    public final SystemRepository mSystemRepository;
    public final Set gameApps = new ArraySet();
    public final Set notGameApps = new ArraySet();
    public IGameManagerService gms = IGameManagerService.Stub.asInterface(SemGameManager.getGMSBinder());

    public ICollectionCache$GameAppsCache(SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
    }

    public boolean contains(String str) {
        if (this.gameApps.contains(str)) {
            return true;
        }
        if (this.notGameApps.contains(str)) {
            return false;
        }
        return update(str);
    }

    public boolean update(String str) {
        if (this.gms == null) {
            IGameManagerService asInterface = IGameManagerService.Stub.asInterface(SemGameManager.getGMSBinder());
            this.gms = asInterface;
            if (asInterface == null) {
                return false;
            }
        }
        try {
            if (this.gms.identifyGamePackage(str) == 1) {
                this.gameApps.add(str);
                return true;
            }
            this.notGameApps.add(str);
            return false;
        } catch (Exception e) {
            this.mSystemRepository.logDebug("ChimeraDataCache", "GameManagerService RemoteException! " + e.getMessage());
            return false;
        }
    }
}
