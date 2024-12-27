package com.android.server.backup;

import com.android.server.backup.UserBackupManagerService;
import com.android.server.backup.internal.LifecycleOperationStorage;
import com.google.android.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserBackupManagerService$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ UserBackupManagerService$$ExternalSyntheticLambda11(String str, int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        HashSet newHashSet;
        switch (this.$r8$classId) {
            case 0:
                UserBackupManagerService userBackupManagerService = (UserBackupManagerService) this.f$0;
                String str = this.f$1;
                LifecycleOperationStorage lifecycleOperationStorage = userBackupManagerService.mOperationStorage;
                synchronized (lifecycleOperationStorage.mOperationsLock) {
                    try {
                        Set set = (Set) ((HashMap) lifecycleOperationStorage.mOpTokensByPackage).get(str);
                        newHashSet = Sets.newHashSet();
                        if (set != null) {
                            newHashSet.addAll(set);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                Iterator it = newHashSet.iterator();
                while (it.hasNext()) {
                    userBackupManagerService.mOperationStorage.cancelOperation(((Integer) it.next()).intValue(), true, new UserBackupManagerService$$ExternalSyntheticLambda14(0, userBackupManagerService));
                }
                return;
            case 1:
                UserBackupManagerService.AnonymousClass2 anonymousClass2 = (UserBackupManagerService.AnonymousClass2) this.f$0;
                String str2 = this.f$1;
                TransportManager transportManager = UserBackupManagerService.this.mTransportManager;
                transportManager.getClass();
                transportManager.registerTransportsFromPackage(str2, new TransportManager$$ExternalSyntheticLambda1());
                return;
            default:
                UserBackupManagerService.AnonymousClass2 anonymousClass22 = (UserBackupManagerService.AnonymousClass2) this.f$0;
                UserBackupManagerService.this.mTransportManager.onPackageRemoved(this.f$1);
                return;
        }
    }
}
