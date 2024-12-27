package com.android.server.sepunion;

import android.content.Context;
import com.android.server.SystemService;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemUnionManagerLocal;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemUnionMainService extends SystemService {
    public final SemUnionMainServiceImpl mImpl;

    public SemUnionMainService(Context context) {
        super(context);
        this.mImpl = new SemUnionMainServiceImpl(context);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        Log.d("SemUnionMainService", "onBootPhase = " + i);
        SemUnionMainServiceImpl semUnionMainServiceImpl = this.mImpl;
        semUnionMainServiceImpl.getClass();
        synchronized (SemUnionMainServiceImpl.sLock) {
            try {
                Iterator it = SemUnionMainServiceImpl.sSemSystemServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                    if (absSemSystemService != null) {
                        absSemSystemService.onBootPhase(i);
                    }
                }
                if (i == 1000 && !semUnionMainServiceImpl.mIsBootCompleted) {
                    semUnionMainServiceImpl.mIsBootCompleted = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.os.IBinder, com.android.server.sepunion.SemUnionMainServiceImpl] */
    @Override // com.android.server.SystemService
    public final void onStart() {
        Log.d("SemUnionMainService", "onStart");
        ?? r1 = this.mImpl;
        publishBinderService("sepunion", r1);
        publishLocalService(SemUnionManagerLocal.class, r1.mSemUnionManagerLocal);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        SemUnionMainServiceImpl semUnionMainServiceImpl = this.mImpl;
        int userIdentifier = targetUser.getUserIdentifier();
        semUnionMainServiceImpl.getClass();
        Log.d("SemUnionMainServiceImpl", "onUserStarting");
        synchronized (SemUnionMainServiceImpl.sLock) {
            try {
                Iterator it = SemUnionMainServiceImpl.sSemSystemServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                    if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                        ((AbsSemSystemServiceForS) absSemSystemService).onUserStarting(userIdentifier);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopped(SystemService.TargetUser targetUser) {
        SemUnionMainServiceImpl semUnionMainServiceImpl = this.mImpl;
        int userIdentifier = targetUser.getUserIdentifier();
        semUnionMainServiceImpl.getClass();
        Log.d("SemUnionMainServiceImpl", "onUserStopped");
        synchronized (SemUnionMainServiceImpl.sLock) {
            try {
                Iterator it = SemUnionMainServiceImpl.sSemSystemServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                    if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                        ((AbsSemSystemServiceForS) absSemSystemService).onUserStopped(userIdentifier);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        SemUnionMainServiceImpl semUnionMainServiceImpl = this.mImpl;
        int userIdentifier = targetUser.getUserIdentifier();
        semUnionMainServiceImpl.getClass();
        Log.d("SemUnionMainServiceImpl", "onUserStopping");
        synchronized (SemUnionMainServiceImpl.sLock) {
            try {
                Iterator it = SemUnionMainServiceImpl.sSemSystemServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                    if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                        ((AbsSemSystemServiceForS) absSemSystemService).onUserStopping(userIdentifier);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        SemUnionMainServiceImpl semUnionMainServiceImpl = this.mImpl;
        int userIdentifier = targetUser.getUserIdentifier();
        int userIdentifier2 = targetUser2.getUserIdentifier();
        semUnionMainServiceImpl.getClass();
        Log.d("SemUnionMainServiceImpl", "onUserSwitching");
        synchronized (SemUnionMainServiceImpl.sLock) {
            try {
                Iterator it = SemUnionMainServiceImpl.sSemSystemServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                    if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                        ((AbsSemSystemServiceForS) absSemSystemService).onUserSwitching(userIdentifier, userIdentifier2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(SystemService.TargetUser targetUser) {
        SemUnionMainServiceImpl semUnionMainServiceImpl = this.mImpl;
        int userIdentifier = targetUser.getUserIdentifier();
        semUnionMainServiceImpl.getClass();
        Log.d("SemUnionMainServiceImpl", "onUserUnlocked");
        synchronized (SemUnionMainServiceImpl.sLock) {
            try {
                Iterator it = SemUnionMainServiceImpl.sSemSystemServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                    if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                        ((AbsSemSystemServiceForS) absSemSystemService).onUserUnlocked(userIdentifier);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        SemUnionMainServiceImpl semUnionMainServiceImpl = this.mImpl;
        int userIdentifier = targetUser.getUserIdentifier();
        semUnionMainServiceImpl.getClass();
        Log.d("SemUnionMainServiceImpl", "onUserUnlocking");
        synchronized (SemUnionMainServiceImpl.sLock) {
            try {
                Iterator it = SemUnionMainServiceImpl.sSemSystemServiceMap.entrySet().iterator();
                while (it.hasNext()) {
                    AbsSemSystemService absSemSystemService = (AbsSemSystemService) ((Map.Entry) it.next()).getValue();
                    if (absSemSystemService != null && (absSemSystemService instanceof AbsSemSystemServiceForS)) {
                        ((AbsSemSystemServiceForS) absSemSystemService).onUserUnlocking(userIdentifier);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
