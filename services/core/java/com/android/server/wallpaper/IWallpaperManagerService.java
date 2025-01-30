package com.android.server.wallpaper;

import android.app.IWallpaperManager;
import android.os.IBinder;

/* loaded from: classes3.dex */
public interface IWallpaperManagerService extends IWallpaperManager, IBinder {
  void onBootPhase(int i);

  void onUnlockUser(int i);
}
