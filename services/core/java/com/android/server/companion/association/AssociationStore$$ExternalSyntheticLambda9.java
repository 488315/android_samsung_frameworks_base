package com.android.server.companion.association;

import android.util.AtomicFile;
import android.util.Slog;

import com.android.internal.util.CollectionUtils;
import com.android.server.companion.utils.DataStoreUtils;

import java.util.HashMap;
import java.util.List;

public final /* synthetic */ class AssociationStore$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ AssociationStore f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AssociationStore$$ExternalSyntheticLambda9(
            AssociationStore associationStore, int i) {
        this.f$0 = associationStore;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AssociationStore associationStore = this.f$0;
        int i = this.f$1;
        associationStore.getClass();
        Associations associations = new Associations();
        synchronized (associationStore.mLock) {
            associations.mMaxId = associationStore.mMaxId;
            associations.mAssociations =
                    List.copyOf(
                            CollectionUtils.filter(
                                    ((HashMap) associationStore.mIdToAssociationMap)
                                            .values().stream().toList(),
                                    new AssociationStore$$ExternalSyntheticLambda4(i, 2)));
        }
        AssociationDiskStore associationDiskStore = associationStore.mDiskStore;
        associationDiskStore.getClass();
        Slog.i("CDM_AssociationDiskStore", "Writing associations for user " + i + " to disk");
        AtomicFile storageFileForUser = associationDiskStore.getStorageFileForUser(i);
        synchronized (storageFileForUser) {
            DataStoreUtils.writeToFileSafely(
                    storageFileForUser,
                    new AssociationDiskStore$$ExternalSyntheticLambda0(associations));
        }
    }
}
