package com.android.server.companion.association;

import android.companion.AssociationInfo;
import android.content.pm.UserInfo;

import com.android.internal.util.FunctionalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final /* synthetic */ class AssociationStore$$ExternalSyntheticLambda3
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ AssociationStore f$0;

    public final void runOrThrow() {
        AssociationStore associationStore = this.f$0;
        associationStore.getClass();
        ArrayList arrayList = new ArrayList();
        Iterator it = associationStore.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
        }
        synchronized (associationStore.mLock) {
            try {
                associationStore.mPersisted = false;
                ((HashMap) associationStore.mIdToAssociationMap).clear();
                associationStore.mMaxId = 0;
                for (Map.Entry entry :
                        ((HashMap) associationStore.mDiskStore.readAssociationsByUsers(arrayList))
                                .entrySet()) {
                    for (AssociationInfo associationInfo :
                            ((Associations) entry.getValue()).mAssociations) {
                        ((HashMap) associationStore.mIdToAssociationMap)
                                .put(Integer.valueOf(associationInfo.getId()), associationInfo);
                    }
                    associationStore.mMaxId =
                            Math.max(
                                    associationStore.mMaxId,
                                    ((Associations) entry.getValue()).mMaxId);
                }
                associationStore.mPersisted = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
