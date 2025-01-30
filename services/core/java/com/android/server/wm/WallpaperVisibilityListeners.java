package com.android.server.wm;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.SparseArray;
import android.view.IWallpaperVisibilityListener;

/* loaded from: classes3.dex */
public class WallpaperVisibilityListeners {
  public final SparseArray mDisplayListeners = new SparseArray();

  public void registerWallpaperVisibilityListener(
      IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
    RemoteCallbackList remoteCallbackList = (RemoteCallbackList) this.mDisplayListeners.get(i);
    if (remoteCallbackList == null) {
      remoteCallbackList = new RemoteCallbackList();
      this.mDisplayListeners.append(i, remoteCallbackList);
    }
    remoteCallbackList.register(iWallpaperVisibilityListener);
  }

  public void unregisterWallpaperVisibilityListener(
      IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) {
    RemoteCallbackList remoteCallbackList = (RemoteCallbackList) this.mDisplayListeners.get(i);
    if (remoteCallbackList == null) {
      return;
    }
    remoteCallbackList.unregister(iWallpaperVisibilityListener);
  }

  public void notifyWallpaperVisibilityChanged(DisplayContent displayContent) {
    int displayId = displayContent.getDisplayId();
    boolean isWallpaperVisible = displayContent.mWallpaperController.isWallpaperVisible();
    RemoteCallbackList remoteCallbackList =
        (RemoteCallbackList) this.mDisplayListeners.get(displayId);
    if (remoteCallbackList == null) {
      return;
    }
    int beginBroadcast = remoteCallbackList.beginBroadcast();
    while (beginBroadcast > 0) {
      beginBroadcast--;
      try {
        remoteCallbackList
            .getBroadcastItem(beginBroadcast)
            .onWallpaperVisibilityChanged(isWallpaperVisible, displayId);
      } catch (RemoteException unused) {
      }
    }
    remoteCallbackList.finishBroadcast();
  }
}
