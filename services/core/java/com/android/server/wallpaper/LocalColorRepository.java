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
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class LocalColorRepository {
  public ArrayMap mLocalColorAreas = new ArrayMap();
  public RemoteCallbackList mCallbacks = new RemoteCallbackList();

  public void addAreas(
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
                    LocalColorRepository.this.lambda$addAreas$0(iLocalWallpaperColorConsumer);
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

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$addAreas$0(
      ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
    this.mLocalColorAreas.remove(iLocalWallpaperColorConsumer.asBinder());
  }

  public List removeAreas(
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
            i5 < ((ArraySet) ((SparseArray) this.mLocalColorAreas.valueAt(i3)).valueAt(i4)).size();
            i5++) {
          arraySet2.remove(
              ((ArraySet) ((SparseArray) this.mLocalColorAreas.valueAt(i3)).valueAt(i4))
                  .valueAt(i5));
        }
      }
    }
    return new ArrayList(arraySet2);
  }

  public List getAreasByDisplayId(int i) {
    ArraySet arraySet;
    ArrayList arrayList = new ArrayList();
    for (int i2 = 0; i2 < this.mLocalColorAreas.size(); i2++) {
      SparseArray sparseArray = (SparseArray) this.mLocalColorAreas.valueAt(i2);
      if (sparseArray != null && (arraySet = (ArraySet) sparseArray.get(i)) != null) {
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
          arrayList.add((RectF) arraySet.valueAt(i3));
        }
      }
    }
    return arrayList;
  }

  public void forEachCallback(final Consumer consumer, final RectF rectF, final int i) {
    this.mCallbacks.broadcast(
        new Consumer() { // from class:
                         // com.android.server.wallpaper.LocalColorRepository$$ExternalSyntheticLambda1
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            LocalColorRepository.this.lambda$forEachCallback$1(
                i, rectF, consumer, (ILocalWallpaperColorConsumer) obj);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$forEachCallback$1(
      int i,
      RectF rectF,
      Consumer consumer,
      ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
    ArraySet arraySet;
    SparseArray sparseArray =
        (SparseArray) this.mLocalColorAreas.get(iLocalWallpaperColorConsumer.asBinder());
    if (sparseArray == null
        || (arraySet = (ArraySet) sparseArray.get(i)) == null
        || !arraySet.contains(rectF)) {
      return;
    }
    consumer.accept(iLocalWallpaperColorConsumer);
  }

  public boolean isCallbackAvailable(ILocalWallpaperColorConsumer iLocalWallpaperColorConsumer) {
    return this.mLocalColorAreas.get(iLocalWallpaperColorConsumer.asBinder()) != null;
  }
}
