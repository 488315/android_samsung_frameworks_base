package com.android.server.wallpaper;

import android.app.ILocalWallpaperColorConsumer;
import android.graphics.RectF;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LocalColorRepository {
    public RemoteCallbackList mCallbacks;
    public ArrayMap mLocalColorAreas;

    public final void addAreas(
            final ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List list, int i) {
        ArraySet arraySet;
        IBinder asBinder = iLocalWallpaperColorConsumer.asBinder();
        SparseArray sparseArray = (SparseArray) this.mLocalColorAreas.get(asBinder);
        if (sparseArray == null) {
            try {
                iLocalWallpaperColorConsumer
                        .asBinder()
                        .linkToDeath(
                                new IBinder
                                        .DeathRecipient() { // from class:
                                                            // com.android.server.wallpaper.LocalColorRepository$$ExternalSyntheticLambda0
                                    @Override // android.os.IBinder.DeathRecipient
                                    public final void binderDied() {
                                        LocalColorRepository.this.mLocalColorAreas.remove(
                                                iLocalWallpaperColorConsumer.asBinder());
                                    }
                                },
                                0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            sparseArray = new SparseArray();
            this.mLocalColorAreas.put(asBinder, sparseArray);
            arraySet = null;
        } else {
            arraySet = (ArraySet) sparseArray.get(i);
        }
        if (arraySet == null) {
            arraySet = new ArraySet(list);
            sparseArray.put(i, arraySet);
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            arraySet.add((RectF) list.get(i2));
        }
        this.mCallbacks.register(iLocalWallpaperColorConsumer);
    }

    public boolean isCallbackAvailable(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
        return this.mLocalColorAreas.get(iLocalWallpaperColorConsumer.asBinder()) != null;
    }

    public final List removeAreas(
            ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer, List list, int i) {
        IBinder asBinder = iLocalWallpaperColorConsumer.asBinder();
        SparseArray sparseArray = (SparseArray) this.mLocalColorAreas.get(asBinder);
        if (sparseArray != null) {
            ArraySet arraySet = (ArraySet) sparseArray.get(i);
            if (arraySet == null) {
                this.mCallbacks.unregister(iLocalWallpaperColorConsumer);
            } else {
                for (int i2 = 0; i2 < list.size(); i2++) {
                    arraySet.remove(list.get(i2));
                }
                if (arraySet.size() == 0) {
                    sparseArray.remove(i);
                }
            }
            if (sparseArray.size() == 0) {
                this.mLocalColorAreas.remove(asBinder);
                this.mCallbacks.unregister(iLocalWallpaperColorConsumer);
            }
        } else {
            this.mCallbacks.unregister(iLocalWallpaperColorConsumer);
        }
        ArraySet arraySet2 = new ArraySet(list);
        for (int i3 = 0; i3 < this.mLocalColorAreas.size(); i3++) {
            for (int i4 = 0; i4 < ((SparseArray) this.mLocalColorAreas.valueAt(i3)).size(); i4++) {
                for (int i5 = 0;
                        i5
                                < ((ArraySet)
                                                ((SparseArray) this.mLocalColorAreas.valueAt(i3))
                                                        .valueAt(i4))
                                        .size();
                        i5++) {
                    arraySet2.remove(
                            ((ArraySet)
                                            ((SparseArray) this.mLocalColorAreas.valueAt(i3))
                                                    .valueAt(i4))
                                    .valueAt(i5));
                }
            }
        }
        return new ArrayList(arraySet2);
    }
}
