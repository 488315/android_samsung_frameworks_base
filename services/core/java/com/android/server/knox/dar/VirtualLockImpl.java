package com.android.server.knox.dar;

import android.database.sqlite.SQLiteDatabase;
import android.net.util.NetworkConstants;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.gatekeeper.IGateKeeperService;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;

import java.security.SecureRandom;

public final class VirtualLockImpl {
    public static final SecureRandom sSecureRandom = new SecureRandom();
    public final DarDatabaseCache mDarDatabaseCache;
    public IGateKeeperService mGateKeeperService;
    public final DarManagerService.Injector mInjector;
    public final LockPatternUtils mLockPatternUtils;
    public LockSettingsInternal mLockSettingsInternal;

    public final class GateKeeperDiedRecipient implements IBinder.DeathRecipient {
        public GateKeeperDiedRecipient() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            VirtualLockImpl.this.mGateKeeperService.asBinder().unlinkToDeath(this, 0);
            VirtualLockImpl.this.mGateKeeperService = null;
        }
    }

    public VirtualLockImpl(DarManagerService.Injector injector) {
        this.mInjector = injector;
        injector.getClass();
        this.mDarDatabaseCache = injector.mDarDatabaseCache;
        this.mLockPatternUtils = injector.mLockPatternUtils;
    }

    public final boolean clearResetPasswordTokenInternal(int i, long j) {
        if (j == 0 || !this.mLockPatternUtils.removeEscrowToken(j, i)) {
            return false;
        }
        DarDatabaseCache darDatabaseCache = this.mDarDatabaseCache;
        darDatabaseCache.getClass();
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                sQLiteDatabase = darDatabaseCache.mDatabaseHelper.getWritableDatabase();
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.delete(
                        "dar_info",
                        "name=? AND user=?",
                        new String[] {"vl.rst.token.handle", Integer.toString(i)});
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                sQLiteDatabase.close();
                String makeTag = DarDatabaseCache.makeTag(i, "vl.rst.token.handle");
                synchronized (darDatabaseCache.mCache) {
                    try {
                        if (darDatabaseCache.mCache.containsKey(makeTag)) {
                            darDatabaseCache.mCache.remove(makeTag);
                        }
                    } finally {
                    }
                }
                return true;
            } catch (Exception e) {
                DarDatabaseCache.reportError(e, "del");
                if (sQLiteDatabase == null) {
                    return true;
                }
                sQLiteDatabase.endTransaction();
                sQLiteDatabase.close();
                return true;
            }
        } catch (Throwable th) {
            if (sQLiteDatabase != null) {
                sQLiteDatabase.endTransaction();
                sQLiteDatabase.close();
            }
            throw th;
        }
    }

    public final int getAvailableUserId() {
        int reservedUserIdForSystem = getReservedUserIdForSystem();
        for (int i = 1000; i < i + 10; i++) {
            if (i != reservedUserIdForSystem) {
                try {
                    if (getGateKeeperService().getSecureUserId(i) == 0) {
                        return i;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sSecureRandom.nextInt(500) + NetworkConstants.ETHER_MTU;
    }

    public final synchronized IGateKeeperService getGateKeeperService() {
        IGateKeeperService iGateKeeperService = this.mGateKeeperService;
        if (iGateKeeperService != null) {
            return iGateKeeperService;
        }
        IBinder service =
                ServiceManager.getService("android.service.gatekeeper.IGateKeeperService");
        if (service == null) {
            Log.e("VirtualLockImpl", "Unable to acquire GateKeeperService");
            return null;
        }
        try {
            service.linkToDeath(new GateKeeperDiedRecipient(), 0);
        } catch (RemoteException e) {
            Log.w("VirtualLockImpl", " Unable to register death recipient", e);
        }
        IGateKeeperService asInterface = IGateKeeperService.Stub.asInterface(service);
        this.mGateKeeperService = asInterface;
        return asInterface;
    }

    public final int getReservedUserIdForSystem() {
        this.mInjector.getClass();
        DarManagerService.Injector.enforceCallerKnoxCoreOrSelf("getReservedUserIdForSystem");
        DarDatabaseCache darDatabaseCache = this.mDarDatabaseCache;
        darDatabaseCache.getClass();
        try {
            String internal = darDatabaseCache.getInternal(0, "vl.reserved.userid");
            if (internal != null) {
                return Integer.parseInt(internal);
            }
            return -10000;
        } catch (NumberFormatException unused) {
            return -10000;
        }
    }
}
