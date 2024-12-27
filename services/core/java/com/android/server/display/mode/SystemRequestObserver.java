package com.android.server.display.mode;

import android.os.IBinder;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemRequestObserver {
    public final VotesStorage mVotesStorage;
    public final AnonymousClass1 mDeathRecipient =
            new IBinder
                    .DeathRecipient() { // from class:
                                        // com.android.server.display.mode.SystemRequestObserver.1
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {}

                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied(IBinder iBinder) {
                    SystemRequestObserver.this.removeSystemRequestedVotes(iBinder);
                    iBinder.unlinkToDeath(SystemRequestObserver.this.mDeathRecipient, 0);
                }
            };
    public final Object mLock = new Object();
    public final Map mDisplaysRestrictions = new HashMap();

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.display.mode.SystemRequestObserver$1] */
    public SystemRequestObserver(VotesStorage votesStorage) {
        this.mVotesStorage = votesStorage;
    }

    public final void removeSystemRequestedVotes(IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray =
                        (SparseArray) ((HashMap) this.mDisplaysRestrictions).remove(iBinder);
                if (sparseArray != null) {
                    for (int i = 0; i < sparseArray.size(); i++) {
                        updateStorageLocked(sparseArray.keyAt(i));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateStorageLocked(final int i) {
        final ArrayList arrayList = new ArrayList();
        final boolean[] zArr = new boolean[1];
        ((HashMap) this.mDisplaysRestrictions)
                .forEach(
                        new BiConsumer() { // from class:
                                           // com.android.server.display.mode.SystemRequestObserver$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                int i2 = i;
                                boolean[] zArr2 = zArr;
                                List list = arrayList;
                                List list2 = (List) ((SparseArray) obj2).get(i2);
                                if (list2 != null) {
                                    if (zArr2[0]) {
                                        list.retainAll(list2);
                                    } else {
                                        list.addAll(list2);
                                        zArr2[0] = true;
                                    }
                                }
                            }
                        });
        this.mVotesStorage.updateVote(i, 18, zArr[0] ? new SupportedModesVote(arrayList) : null);
    }
}
